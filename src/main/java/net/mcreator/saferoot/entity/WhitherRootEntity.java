package net.mcreator.saferoot.entity;

import java.lang.reflect.Field;
import java.util.function.Consumer;

import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;
import net.neoforged.neoforge.event.entity.ProjectileImpactEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.level.BlockEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.BossEvent;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.WitherSkull;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.pattern.BlockInWorld;
import net.minecraft.world.level.block.state.pattern.BlockPattern;
import net.minecraft.world.level.block.state.pattern.BlockPatternBuilder;
import net.minecraft.world.phys.AABB;

import net.mcreator.saferoot.init.SaferootModBlocks;
import net.mcreator.saferoot.init.SaferootModEntities;

@EventBusSubscriber
public class WhitherRootEntity extends WitherBoss {

	public static final int CHARGE_TICKS = 330;

	private static final double MAX_HEALTH = 330.0;
	private static final float SPAWN_EXPLOSION = 6.0F;
	private static final float SKULL_EXPLOSION = 2.5F;
	private static final int POISON_DURATION = 200;
	private static final int POISON_LEVEL = 1;
	private static final int SIDE_HEADS = 2;

	private static BlockPattern cachedSpawnPattern;

	public WhitherRootEntity(EntityType<WhitherRootEntity> type, Level world) {
		super(type, world);
		recolorBossBar(BossEvent.BossBarColor.YELLOW);
	}

	public static AttributeSupplier.Builder createAttributes() {
		return WitherBoss.createAttributes().add(Attributes.MAX_HEALTH, MAX_HEALTH);
	}

	public static void init(RegisterSpawnPlacementsEvent event) {
		event.register(SaferootModEntities.WHITHER_ROOT.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
				(entityType, world, reason, pos, random) -> false, RegisterSpawnPlacementsEvent.Operation.REPLACE);
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();
		this.targetSelector.removeAllGoals(goal -> true);
		this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
	}

	@Override
	protected void customServerAiStep() {
		int chargeBefore = this.getInvulnerableTicks();
		super.customServerAiStep();
		this.dropNonPlayerHeadTargets();
		if (chargeBefore > 0 && this.getInvulnerableTicks() <= 0 && this.level() instanceof ServerLevel level)
			level.explode(this, this.getX(), this.getEyeY(), this.getZ(), SPAWN_EXPLOSION, false, Level.ExplosionInteraction.MOB);
	}

	@Override
	public void makeInvulnerable() {
		super.makeInvulnerable();
		this.setInvulnerableTicks(CHARGE_TICKS);
	}

	private void dropNonPlayerHeadTargets() {
		for (int head = 0; head < SIDE_HEADS; head++) {
			int targetId = this.getAlternativeTarget(head);
			if (targetId <= 0)
				continue;
			Entity target = this.level().getEntity(targetId);
			if (!(target instanceof Player))
				this.setAlternativeTarget(head, 0);
		}
	}

	private void recolorBossBar(BossEvent.BossBarColor color) {
		try {
			Field field = WitherBoss.class.getDeclaredField("bossEvent");
			field.setAccessible(true);
			((BossEvent) field.get(this)).setColor(color);
		} catch (ReflectiveOperationException | ClassCastException ignored) {
		}
	}

	@SubscribeEvent
	public static void onProjectileImpact(ProjectileImpactEvent event) {
		if (!(event.getProjectile() instanceof WitherSkull skull))
			return;
		if (!(skull.getOwner() instanceof WhitherRootEntity))
			return;
		if (!(skull.level() instanceof ServerLevel level))
			return;

		level.explode(skull, skull.getX(), skull.getY(), skull.getZ(), SKULL_EXPLOSION, false, Level.ExplosionInteraction.MOB);

		for (LivingEntity living : level.getEntitiesOfClass(LivingEntity.class, skull.getBoundingBox().inflate(SKULL_EXPLOSION))) {
			if (living instanceof WhitherRootEntity)
				continue;
			living.addEffect(new MobEffectInstance(MobEffects.POISON, POISON_DURATION, POISON_LEVEL));
		}
	}

	@SubscribeEvent
	public static void onBlockPlaced(BlockEvent.EntityPlaceEvent event) {
		if (!(event.getLevel() instanceof ServerLevel level))
			return;
		if (!isSummoningHead(event.getPlacedBlock()))
			return;
		trySpawn(level, event.getPos());
	}

	@SubscribeEvent
	public static void onLivingDeath(LivingDeathEvent event) {
		if (!(event.getSource().getEntity() instanceof WhitherRootEntity) && !(event.getEntity().getLastHurtByMob() instanceof WhitherRootEntity))
			return;
		if (!(event.getEntity().level() instanceof ServerLevel level))
			return;

		BlockPos pos = event.getEntity().blockPosition();
		level.getServer().execute(() -> {
			if (level.getBlockState(pos).is(Blocks.WITHER_ROSE))
				level.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
			for (ItemEntity item : level.getEntitiesOfClass(ItemEntity.class, new AABB(pos).inflate(2.0))) {
				if (item.getItem().is(Items.WITHER_ROSE))
					item.discard();
			}
		});
	}

	public static void trySpawn(ServerLevel level, BlockPos pos) {
		BlockPattern.BlockPatternMatch match = spawnPattern().find(level, pos);
		if (match == null)
			return;
		WhitherRootEntity boss = SaferootModEntities.WHITHER_ROOT.get().create(level);
		if (boss == null)
			return;

		forEachAltarBlock(match, block -> {
			level.setBlock(block.getPos(), Blocks.AIR.defaultBlockState(), 2);
			level.levelEvent(2001, block.getPos(), Block.getId(block.getState()));
		});

		BlockPos center = match.getBlock(1, 2, 0).getPos();
		float rotation = match.getForwards().getAxis() == Direction.Axis.X ? 0.0F : 90.0F;
		boss.moveTo(center.getX() + 0.5, center.getY() + 0.55, center.getZ() + 0.5, rotation, 0.0F);
		boss.yBodyRot = rotation;
		boss.makeInvulnerable();
		level.addFreshEntity(boss);

		forEachAltarBlock(match, block -> level.blockUpdated(block.getPos(), Blocks.AIR));
	}

	private static void forEachAltarBlock(BlockPattern.BlockPatternMatch match, Consumer<BlockInWorld> action) {
		for (int x = 0; x < match.getWidth(); x++) {
			for (int y = 0; y < match.getHeight(); y++) {
				action.accept(match.getBlock(x, y, 0));
			}
		}
	}

	private static boolean isSummoningHead(BlockState state) {
		return state.is(Blocks.PLAYER_HEAD) || state.is(Blocks.PLAYER_WALL_HEAD);
	}

	private static BlockPattern spawnPattern() {
		if (cachedSpawnPattern == null) {
			cachedSpawnPattern = BlockPatternBuilder.start().aisle("^^^", "###", "~#~")
					.where('#', block -> block.getState().is(SaferootModBlocks.BLOC_DE_ROOTIUM.get()))
					.where('^', block -> isSummoningHead(block.getState()))
					.where('~', block -> block.getState().isAir()).build();
		}
		return cachedSpawnPattern;
	}
}
