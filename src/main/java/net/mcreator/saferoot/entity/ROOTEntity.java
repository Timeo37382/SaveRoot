package net.mcreator.saferoot.entity;

import software.bernie.geckolib.util.GeckoLibUtil;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animatable.GeoEntity;

import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;

import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.LeapAtTargetGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.Difficulty;
import net.minecraft.util.Mth;
import net.minecraft.tags.ItemTags;
import net.minecraft.sounds.SoundSource;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.Component;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.particles.ParticleTypes;

import net.mcreator.saferoot.world.inventory.RootGolemGuiMenu;
import net.mcreator.saferoot.init.SaferootModItems;
import net.mcreator.saferoot.init.SaferootModEntities;

import java.util.UUID;
import java.util.List;
import java.util.EnumSet;

public class ROOTEntity extends Chicken implements GeoEntity {
	public static final EntityDataAccessor<Boolean> SHOOT = SynchedEntityData.defineId(ROOTEntity.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<String> ANIMATION = SynchedEntityData.defineId(ROOTEntity.class, EntityDataSerializers.STRING);
	public static final EntityDataAccessor<String> TEXTURE = SynchedEntityData.defineId(ROOTEntity.class, EntityDataSerializers.STRING);
	public static final EntityDataAccessor<Boolean> AWAY = SynchedEntityData.defineId(ROOTEntity.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<Boolean> SITTING = SynchedEntityData.defineId(ROOTEntity.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<Integer> COOLDOWN = SynchedEntityData.defineId(ROOTEntity.class, EntityDataSerializers.INT);
	private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
	private boolean swinging;
	private boolean lastloop;
	private long lastSwing;
	public String animationprocedure = "empty";

	public static final int MODE_NONE = -1, MODE_MINER = 0, MODE_ATTAQUER = 1, MODE_BUCHER = 2, MODE_COUNT = 3;

	public record Ore(Item item, float rarity, String... dimensions) {
		public boolean availableIn(Level level) {
			String id = level.dimension().location().toString();
			for (String allowed : dimensions)
				if (allowed.equals(id))
					return true;
			return false;
		}
	}

	private static final String OVERWORLD = "minecraft:overworld", NETHER = "minecraft:the_nether", ROOTIA = "saferoot:rootia";

	public static final List<Ore> ORES = List.of(new Ore(Items.COAL, 1.0F, OVERWORLD), new Ore(Items.RAW_COPPER, 1.3F, OVERWORLD), new Ore(Items.RAW_IRON, 1.5F, OVERWORLD),
			new Ore(Items.REDSTONE, 2.0F, OVERWORLD), new Ore(Items.LAPIS_LAZULI, 2.2F, OVERWORLD), new Ore(Items.RAW_GOLD, 2.5F, OVERWORLD, NETHER),
			new Ore(Items.DIAMOND, 5.0F, OVERWORLD), new Ore(Items.EMERALD, 6.0F, OVERWORLD), new Ore(Items.QUARTZ, 2.0F, NETHER),
			new Ore(Items.ANCIENT_DEBRIS, 12.0F, NETHER), new Ore(SaferootModItems.ROOTIUM_BRUT.get(), 4.0F, ROOTIA, OVERWORLD));

	private static final float MAX_RARITY = 12.0F;
	private static final float MAX_RISK = 0.05F;
	private static final float LOG_RARITY = 1.0F, ZOMBIE_RARITY = 1.5F;

	public static float rarityOf(int mode, int oreIndex) {
		return switch (mode) {
			case MODE_MINER -> ORES.get(Math.floorMod(oreIndex, ORES.size())).rarity();
			case MODE_ATTAQUER -> ZOMBIE_RARITY;
			default -> LOG_RARITY;
		};
	}

	public static int missionDuration(int mode, int oreIndex, int quantity) {
		return Math.round(quantity * TICKS_PER_UNIT * rarityOf(mode, oreIndex));
	}

	public static float missionRisk(int mode, int oreIndex, int quantity) {
		return MAX_RISK * (quantity / 64.0F) * (rarityOf(mode, oreIndex) / MAX_RARITY);
	}

	public static boolean modeAvailableIn(int mode, Level level) {
		if (mode != MODE_BUCHER)
			return true;
		String id = level.dimension().location().toString();
		return OVERWORLD.equals(id) || ROOTIA.equals(id);
	}
	private static final List<Item> LOGS = List.of(Items.OAK_LOG, Items.BIRCH_LOG, Items.SPRUCE_LOG, Items.JUNGLE_LOG, Items.ACACIA_LOG, Items.DARK_OAK_LOG);

	public static final int STORAGE_SLOTS = 6;
	public static final int TICKS_PER_UNIT = 2500;
	public static final int RETURN_WARNING = 200;
	public static final int WALK_OUT = 100;
	public static final int MISSION_COOLDOWN = 18000;
	private static final double WALK_DISTANCE = 10.0, WALK_SPEED = 0.18;
	private static final int XP_PER_KILL = 5;
	private static final int DURABILITY_PER_UNIT = 6;

	private final SimpleContainer tools = new SimpleContainer(MODE_COUNT);
	private final SimpleContainer storage = new SimpleContainer(STORAGE_SLOTS);
	private int mode = MODE_NONE;

	private int missionTicks;
	private int missionMode = MODE_NONE;
	private int missionOre;
	private int missionQuantity;
	private UUID missionOwner;
	private boolean missionDoomed;
	private int missionTotal;
	private double walkTargetX, walkTargetZ;
	private int busyTicks;
	private UUID ownerUuid;
	private int cooldownTicks;

	public int getCooldownTicks() {
		return this.entityData.get(COOLDOWN);
	}

	public boolean isReady() {
		return !isAway() && !isSitting() && getCooldownTicks() <= 0;
	}

	public boolean isSitting() {
		return this.entityData.get(SITTING);
	}

	public void setSitting(boolean sitting, Player owner) {
		this.entityData.set(SITTING, sitting);
		if (sitting) {
			this.getNavigation().stop();
			this.setTarget(null);
			say(owner, "Je reste ici, je ne te suis plus.");
		} else {
			say(owner, "Je te suis à nouveau.");
		}
	}

	private static final int PREFIX_COLOR = 0xC86E0A;

	private void say(Player target, String message) {
		if (target == null)
			return;
		target.sendSystemMessage(Component.literal("[ROOT]").withStyle(Style.EMPTY.withColor(PREFIX_COLOR)).append(Component.literal(" : " + message)));
	}

	public Player getOwnerPlayer() {
		if (this.ownerUuid != null) {
			Player owner = this.level().getPlayerByUUID(this.ownerUuid);
			if (owner != null)
				return owner;
		}
		return this.level().getNearestPlayer(this, 16.0);
	}

	public void keepBusy() {
		this.busyTicks = 20;
	}

	public SimpleContainer getStorage() {
		return this.storage;
	}

	public boolean isOnMission() {
		return this.missionTicks > 0;
	}

	public int getMissionTicks() {
		return this.missionTicks;
	}

	public boolean startMission(Player owner, int mode, int oreIndex, int quantity) {
		if (isOnMission() || mode < 0 || mode >= MODE_COUNT)
			return false;
		if (isSitting()) {
			say(owner, "Je reste ici, remets-moi debout d'abord.");
			return false;
		}
		if (this.cooldownTicks > 0) {
			say(owner, "Je récupère encore. Reviens dans " + formatDelay(this.cooldownTicks) + ".");
			return false;
		}
		if (getTool(mode).isEmpty()) {
			say(owner, "Il me faut d'abord un outil en rootium.");
			return false;
		}
		this.missionMode = mode;
		this.missionOre = Math.floorMod(oreIndex, ORES.size());
		this.missionQuantity = Math.max(1, Math.min(64, quantity));
		this.missionOwner = owner.getUUID();
		this.missionTotal = WALK_OUT + missionDuration(mode, this.missionOre, this.missionQuantity) + RETURN_WARNING;
		this.missionTicks = this.missionTotal;
		double angle = this.random.nextDouble() * Math.PI * 2;
		this.walkTargetX = this.getX() + Math.cos(angle) * WALK_DISTANCE;
		this.walkTargetZ = this.getZ() + Math.sin(angle) * WALK_DISTANCE;
		this.missionDoomed = this.random.nextFloat() < missionRisk(mode, this.missionOre, this.missionQuantity);
		this.mode = mode;
		say(owner, "Je pars " + verb(mode) + " " + this.missionQuantity + " \u00d7 " + rewardName() + ". \u00c0 tout \u00e0 l'heure.");
		return true;
	}

	public static String formatDelay(int ticks) {
		int seconds = Math.max(1, ticks / 20);
		return seconds < 60 ? seconds + " s" : (seconds / 60) + " min " + (seconds % 60) + " s";
	}

	private static String verb(int mode) {
		return switch (mode) {
			case MODE_MINER -> "chercher";
			case MODE_ATTAQUER -> "chasser";
			default -> "couper";
		};
	}

	private String rewardName() {
		return switch (this.missionMode) {
			case MODE_MINER -> new ItemStack(ORES.get(this.missionOre).item()).getHoverName().getString();
			case MODE_ATTAQUER -> "zombie";
			default -> "b\u00fbche";
		};
	}

	private void puff() {
		if (this.level() instanceof ServerLevel server) {
			server.sendParticles(ParticleTypes.PORTAL, this.getX(), this.getY() + 0.6, this.getZ(), 40, 0.35, 0.7, 0.35, 0.4);
			server.playSound(null, this.blockPosition(), SoundEvents.ENDERMAN_TELEPORT, SoundSource.NEUTRAL, 1.0F, 1.0F);
		}
	}

	@Override
	public boolean isPickable() {
		return !this.entityData.get(AWAY) && super.isPickable();
	}

	public boolean isAway() {
		return this.entityData.get(AWAY);
	}

	private void leave() {
		puff();
		this.entityData.set(AWAY, true);
		this.setInvisible(true);
		this.setInvulnerable(true);
		this.setSilent(true);
		this.setNoAi(true);
		this.setNoGravity(true);
		this.setDeltaMovement(0, 0, 0);
		this.getNavigation().stop();
		this.setPos(this.getX(), this.getY() + 300, this.getZ());
	}

	private void comeBack() {
		this.entityData.set(AWAY, false);
		this.setNoGravity(false);
		this.setInvisible(false);
		this.setInvulnerable(false);
		this.setSilent(false);
		this.setNoAi(false);
	}

	private void updateMonsterAttention() {
		boolean available = !isAway() && !isSitting();
		for (Mob mob : this.level().getEntitiesOfClass(Mob.class, this.getBoundingBox().inflate(12.0), m -> m instanceof Enemy)) {
			if (!available) {
				if (mob.getTarget() == this)
					mob.setTarget(null);
			} else if (mob.getTarget() == null && mob.getSensing().hasLineOfSight(this)) {
				mob.setTarget(this);
			}
		}
	}

	private void stepToward(double tx, double tz) {
		double dx = tx - this.getX(), dz = tz - this.getZ();
		double distance = Math.sqrt(dx * dx + dz * dz);
		if (distance < 1.0)
			return;
		this.setDeltaMovement(dx / distance * WALK_SPEED, this.getDeltaMovement().y, dz / distance * WALK_SPEED);
		float yaw = (float) (Mth.atan2(dz, dx) * (180.0 / Math.PI)) - 90.0F;
		this.setYRot(yaw);
		this.yBodyRot = yaw;
		this.yHeadRot = yaw;
	}

	private void tickMission() {
		if (this.missionTicks <= 0)
			return;
		this.missionTicks--;
		Player owner = this.missionOwner == null ? null : this.level().getPlayerByUUID(this.missionOwner);

		if (this.missionTicks > this.missionTotal - WALK_OUT) {
			stepToward(this.walkTargetX, this.walkTargetZ);
			return;
		}
		if (this.missionTicks == this.missionTotal - WALK_OUT) {
			leave();
			return;
		}
		if (this.missionTicks == RETURN_WARNING) {
			double angle = this.random.nextDouble() * Math.PI * 2;
			if (owner != null) {
				this.teleportTo(owner.getX() + Math.cos(angle) * WALK_DISTANCE, owner.getY(), owner.getZ() + Math.sin(angle) * WALK_DISTANCE);
				say(owner, "J'ai terminé, je rentre.");
			}
			comeBack();
			puff();
			return;
		}
		if (this.missionTicks > 0) {
			if (owner != null)
				stepToward(owner.getX(), owner.getZ());
			return;
		}
		finishMission(owner);
	}

	private void finishMission(Player owner) {
		comeBack();
		if (this.missionDoomed) {
			say(owner, "Je n'ai pas survécu à l'expédition. Tout est resté sur place.");
			for (int i = 0; i < this.tools.getContainerSize(); i++)
				spawnAtDeath(this.tools.removeItemNoUpdate(i));
			for (int i = 0; i < this.storage.getContainerSize(); i++)
				spawnAtDeath(this.storage.removeItemNoUpdate(i));
			this.missionMode = MODE_NONE;
			this.missionOwner = null;
			this.missionDoomed = false;
			this.mode = MODE_NONE;
			this.missionTicks = 0;
			this.hurt(this.damageSources().generic(), Float.MAX_VALUE);
			return;
		}
		if (this.missionMode == MODE_ATTAQUER) {
			if (owner != null && !this.level().isClientSide)
				ExperienceOrb.award((net.minecraft.server.level.ServerLevel) this.level(), owner.position(), this.missionQuantity * XP_PER_KILL);
		} else {
			int left = this.missionQuantity;
			while (left > 0) {
				Item item = this.missionMode == MODE_MINER ? ORES.get(this.missionOre).item() : LOGS.get(this.random.nextInt(LOGS.size()));
				int take = Math.min(left, item.getDefaultMaxStackSize());
				store(new ItemStack(item, take));
				left -= take;
			}
		}
		if (this.missionMode == MODE_ATTAQUER)
			say(owner, "J'ai abattu " + this.missionQuantity + " zombies, voil\u00e0 leur exp\u00e9rience.");
		else
			say(owner, "Je te rapporte " + this.missionQuantity + " \u00d7 " + rewardName() + ".");
		this.missionMode = MODE_NONE;
		this.missionOwner = null;
		this.mode = MODE_NONE;
		this.cooldownTicks = MISSION_COOLDOWN;
		this.entityData.set(COOLDOWN, this.cooldownTicks);
	}

	private void wearTool(Player owner) {
		ItemStack tool = this.tools.getItem(this.missionMode);
		if (tool.isEmpty() || !tool.isDamageableItem())
			return;
		int worn = tool.getDamageValue() + missionDurabilityCost(this.missionQuantity);
		if (worn >= tool.getMaxDamage()) {
			this.tools.setItem(this.missionMode, ItemStack.EMPTY);
			say(owner, "Mon outil s'est brisé, il m'en faut un neuf.");
		} else {
			tool.setDamageValue(worn);
			this.tools.setChanged();
		}
	}

	private void spawnAtDeath(ItemStack stack) {
		if (!stack.isEmpty() && !this.level().isClientSide)
			this.level().addFreshEntity(new ItemEntity(this.level(), this.getX(), this.getY() + 0.5, this.getZ(), stack));
	}

	private void store(ItemStack stack) {
		ItemStack rest = this.storage.addItem(stack);
		if (!rest.isEmpty() && !this.level().isClientSide)
			this.level().addFreshEntity(new ItemEntity(this.level(), this.getX(), this.getY() + 0.5, this.getZ(), rest));
	}

	public SimpleContainer getTools() {
		return this.tools;
	}

	public int getMode() {
		return this.mode;
	}

	public void setMode(int mode) {
		this.mode = mode;
	}

	public ItemStack getTool(int index) {
		return index < 0 || index >= MODE_COUNT ? ItemStack.EMPTY : this.tools.getItem(index);
	}

	public static boolean isToolForSlot(int index, ItemStack stack) {
		return switch (index) {
			case MODE_MINER -> stack.is(SaferootModItems.PIOCHE.get());
			case MODE_ATTAQUER -> stack.is(SaferootModItems.EPEE.get());
			case MODE_BUCHER -> stack.is(SaferootModItems.HACHE.get());
			default -> false;
		};
	}

	public static int missionDurabilityCost(int quantity) {
		return quantity * DURABILITY_PER_UNIT;
	}

	@Override
	public InteractionResult mobInteract(Player player, InteractionHand hand) {
		this.ownerUuid = player.getUUID();
		if (player.isShiftKeyDown()) {
			if (!this.level().isClientSide)
				setSitting(!isSitting(), player);
			return InteractionResult.sidedSuccess(this.level().isClientSide());
		}
		if (isSitting()) {
			if (!this.level().isClientSide)
				say(player, "Je reste ici. Accroupis-toi et fais un clic droit pour me relever.");
			return InteractionResult.sidedSuccess(this.level().isClientSide());
		}
		keepBusy();
		if (player instanceof ServerPlayer serverPlayer)
			serverPlayer.openMenu(new SimpleMenuProvider((id, inventory, viewer) -> new RootGolemGuiMenu(id, inventory, this), Component.literal("Root")),
					buffer -> buffer.writeVarInt(this.getId()));
		return InteractionResult.sidedSuccess(this.level().isClientSide());
	}

	public ROOTEntity(EntityType<ROOTEntity> type, Level world) {
		super(type, world);
		xpReward = 0;
		setNoAi(false);
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder builder) {
		super.defineSynchedData(builder);
		builder.define(SHOOT, false);
		builder.define(ANIMATION, "undefined");
		builder.define(TEXTURE, "roottexturev1");
		builder.define(AWAY, false);
		builder.define(SITTING, false);
		builder.define(COOLDOWN, 0);
	}

	public void setTexture(String texture) {
		this.entityData.set(TEXTURE, texture);
	}

	public String getTexture() {
		return this.entityData.get(TEXTURE);
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();

		this.goalSelector.addGoal(1, new FloatGoal(this));
		this.goalSelector.addGoal(2, new LeapAtTargetGoal(this, 0.4F));
		this.goalSelector.addGoal(3, new MeleeAttackGoal(this, 1.2, true));
		this.goalSelector.addGoal(4, new FollowPlayerGoal(this, 1.1, 3.0F, 12.0F));
		this.goalSelector.addGoal(5, new TemptGoal(this, 1.2, Ingredient.of(SaferootModItems.EPEE.get()), false));
		this.goalSelector.addGoal(6, new WaterAvoidingRandomStrollGoal(this, 0.8));
		this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Mob.class, 8.0F));

		this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
		this.targetSelector.addGoal(2, new HelpOwnerGoal(this));
	}

	@Override
	public SoundEvent getHurtSound(DamageSource ds) {
		return BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("entity.generic.hurt"));
	}

	@Override
	public SoundEvent getDeathSound() {
		return BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("entity.generic.death"));
	}

	@Override
	public void addAdditionalSaveData(CompoundTag compound) {
		super.addAdditionalSaveData(compound);
		compound.putString("Texture", this.getTexture());
		compound.put("RootTools", this.tools.createTag(this.registryAccess()));
		compound.put("RootStorage", this.storage.createTag(this.registryAccess()));
		compound.putInt("RootMode", this.mode);
		compound.putInt("MissionTicks", this.missionTicks);
		compound.putInt("MissionMode", this.missionMode);
		compound.putInt("MissionOre", this.missionOre);
		compound.putInt("MissionQuantity", this.missionQuantity);
		compound.putBoolean("MissionDoomed", this.missionDoomed);
		compound.putInt("MissionTotal", this.missionTotal);
		compound.putBoolean("Sitting", isSitting());
		compound.putInt("Cooldown", this.cooldownTicks);
		if (this.ownerUuid != null)
			compound.putUUID("Owner", this.ownerUuid);
		if (this.missionOwner != null)
			compound.putUUID("MissionOwner", this.missionOwner);
	}

	@Override
	public void readAdditionalSaveData(CompoundTag compound) {
		super.readAdditionalSaveData(compound);
		if (compound.contains("Texture"))
			this.setTexture(compound.getString("Texture"));
		if (compound.contains("RootTools"))
			this.tools.fromTag(compound.getList("RootTools", 10), this.registryAccess());
		if (compound.contains("RootStorage"))
			this.storage.fromTag(compound.getList("RootStorage", 10), this.registryAccess());
		this.mode = compound.contains("RootMode") ? compound.getInt("RootMode") : MODE_NONE;
		this.missionTicks = compound.getInt("MissionTicks");
		this.missionMode = compound.contains("MissionMode") ? compound.getInt("MissionMode") : MODE_NONE;
		this.missionOre = compound.getInt("MissionOre");
		this.missionQuantity = compound.getInt("MissionQuantity");
		this.missionDoomed = compound.getBoolean("MissionDoomed");
		this.missionTotal = compound.contains("MissionTotal") ? compound.getInt("MissionTotal") : this.missionTicks;
		this.entityData.set(SITTING, compound.getBoolean("Sitting"));
		this.cooldownTicks = compound.getInt("Cooldown");
		this.entityData.set(COOLDOWN, this.cooldownTicks);
		this.ownerUuid = compound.hasUUID("Owner") ? compound.getUUID("Owner") : null;
		this.missionOwner = compound.hasUUID("MissionOwner") ? compound.getUUID("MissionOwner") : null;
		if (this.missionTicks > 0 && this.missionTicks < this.missionTotal - WALK_OUT && this.missionTicks > RETURN_WARNING)
			leave();
	}

	@Override
	public void baseTick() {
		super.baseTick();
		this.refreshDimensions();
	}

	@Override
	public EntityDimensions getDefaultDimensions(Pose pose) {
		return super.getDefaultDimensions(pose).scale(1f);
	}

	@Override
	public void aiStep() {
		super.aiStep();
		this.updateSwingTime();
		if (!this.level().isClientSide) {
			if (this.busyTicks > 0) {
				this.busyTicks--;
				if (!isOnMission()) {
					this.getNavigation().stop();
					this.setDeltaMovement(0, this.getDeltaMovement().y, 0);
				}
			}
			if (this.cooldownTicks > 0)
				this.cooldownTicks--;
			tickMission();
			if (this.tickCount % 20 == 0) {
				updateMonsterAttention();
				this.entityData.set(COOLDOWN, this.cooldownTicks);
			}
			if (isSitting()) {
				this.getNavigation().stop();
				this.setDeltaMovement(0, this.getDeltaMovement().y, 0);
			}
		}
	}

	public static void init(RegisterSpawnPlacementsEvent event) {
		event.register(SaferootModEntities.ROOT.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
				(entityType, world, reason, pos, random) -> (world.getDifficulty() != Difficulty.PEACEFUL && Monster.isDarkEnoughToSpawn(world, pos, random) && Mob.checkMobSpawnRules(entityType, world, reason, pos, random)),
				RegisterSpawnPlacementsEvent.Operation.REPLACE);
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.3);
		builder = builder.add(Attributes.MAX_HEALTH, 30);
		builder = builder.add(Attributes.ARMOR, 4);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 6);
		builder = builder.add(Attributes.FOLLOW_RANGE, 24);
		builder = builder.add(Attributes.STEP_HEIGHT, 0.6);
		return builder;
	}

	private PlayState movementPredicate(AnimationState event) {
		if (this.entityData.get(AWAY))
			return PlayState.STOP;
		if (isSitting())
			return PlayState.STOP;
		if (this.animationprocedure.equals("empty")) {
			if ((event.isMoving() || !(event.getLimbSwingAmount() > -0.15F && event.getLimbSwingAmount() < 0.15F))) {
				return event.setAndContinue(RawAnimation.begin().thenLoop("Walk"));
			}
			return PlayState.STOP;
		}
		return PlayState.STOP;
	}

	String prevAnim = "empty";

	private PlayState procedurePredicate(AnimationState event) {
		if (!animationprocedure.equals("empty") && event.getController().getAnimationState() == AnimationController.State.STOPPED || (!this.animationprocedure.equals(prevAnim) && !this.animationprocedure.equals("empty"))) {
			if (!this.animationprocedure.equals(prevAnim))
				event.getController().forceAnimationReset();
			event.getController().setAnimation(RawAnimation.begin().thenPlay(this.animationprocedure));
			if (event.getController().getAnimationState() == AnimationController.State.STOPPED) {
				this.animationprocedure = "empty";
				event.getController().forceAnimationReset();
			}
		} else if (animationprocedure.equals("empty")) {
			prevAnim = "empty";
			return PlayState.STOP;
		}
		prevAnim = this.animationprocedure;
		return PlayState.CONTINUE;
	}

	@Override
	protected void tickDeath() {
		++this.deathTime;
		if (this.deathTime == 20) {
			this.remove(ROOTEntity.RemovalReason.KILLED);
			this.dropExperience(this);
		}
	}

	public String getSyncedAnimation() {
		return this.entityData.get(ANIMATION);
	}

	public void setAnimation(String animation) {
		this.entityData.set(ANIMATION, animation);
	}

	public void applySyncedControllerAnimations() {
	}

	public void setControllerAnimation(String controller, String animation) {
		if (controller == null || controller.isBlank()) {
			this.setAnimation(animation);
			return;
		}
		switch (controller) {
			default -> this.setAnimation(animation);
		}
	}

	public String getControllerAnimation(String controller) {
		if (controller == null || controller.isBlank())
			return this.animationprocedure;
		return switch (controller) {
			default -> this.animationprocedure;
		};
	}

	@Override
	public void registerControllers(AnimatableManager.ControllerRegistrar data) {
		data.add(new AnimationController<>(this, "movement", 4, this::movementPredicate));
		data.add(new AnimationController<>(this, "procedure", 4, this::procedurePredicate));
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return this.cache;
	}

	private static class HelpOwnerGoal extends TargetGoal {
		private final ROOTEntity root;
		private LivingEntity ownerTarget;
		private int timestamp;

		HelpOwnerGoal(ROOTEntity root) {
			super(root, false);
			this.root = root;
			this.setFlags(EnumSet.of(Flag.TARGET));
		}

		@Override
		public boolean canUse() {
			if (this.root.isSitting() || this.root.isAway())
				return false;
			Player owner = this.root.getOwnerPlayer();
			if (owner == null)
				return false;
			this.ownerTarget = owner.getLastHurtMob();
			return owner.getLastHurtMobTimestamp() != this.timestamp && this.canAttack(this.ownerTarget, TargetingConditions.DEFAULT);
		}

		@Override
		public void start() {
			this.mob.setTarget(this.ownerTarget);
			Player owner = this.root.getOwnerPlayer();
			if (owner != null)
				this.timestamp = owner.getLastHurtMobTimestamp();
			super.start();
		}
	}

	private static class FollowPlayerGoal extends net.minecraft.world.entity.ai.goal.Goal {
		private final Chicken mob;
		private final double speed;
		private final float minDistance;
		private final float maxDistance;
		private net.minecraft.world.entity.player.Player targetPlayer;

		public FollowPlayerGoal(Chicken mob, double speed, float minDistance, float maxDistance) {
			this.mob = mob;
			this.speed = speed;
			this.minDistance = minDistance;
			this.maxDistance = maxDistance;
			this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
		}

		@Override
		public boolean canUse() {
			if (this.mob instanceof ROOTEntity root && (root.isSitting() || root.isOnMission()))
				return false;
			net.minecraft.world.entity.player.Player player = this.mob.level().getNearestPlayer(this.mob, this.maxDistance);
			if (player == null || player.isSpectator()) return false;
			if (this.mob.distanceToSqr(player) < (double) (this.minDistance * this.minDistance)) return false;
			this.targetPlayer = player;
			return true;
		}

		@Override
		public boolean canContinueToUse() {
			if (this.targetPlayer == null || !this.targetPlayer.isAlive() || this.targetPlayer.isSpectator()) return false;
			double distSqr = this.mob.distanceToSqr(this.targetPlayer);
			return distSqr >= (double) (this.minDistance * this.minDistance) && distSqr <= (double) (this.maxDistance * this.maxDistance);
		}

		@Override
		public void start() {
		}

		@Override
		public void stop() {
			this.targetPlayer = null;
			this.mob.getNavigation().stop();
		}

		@Override
		public void tick() {
			if (this.targetPlayer == null) return;
			this.mob.getLookControl().setLookAt(this.targetPlayer, 10.0F, (float) this.mob.getMaxHeadXRot());
			if (this.mob.distanceToSqr(this.targetPlayer) >= (double) (this.minDistance * this.minDistance)) {
				this.mob.getNavigation().moveTo(this.targetPlayer, this.speed);
			}
		}
	}
}