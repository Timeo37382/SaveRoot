package net.mcreator.saferoot.item;

import java.util.Optional;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.component.LodestoneTracker;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.structure.Structure;

public class BoussoleRootItem extends Item {

	private static final ResourceKey<Level> ROOTIA = ResourceKey.create(Registries.DIMENSION, ResourceLocation.fromNamespaceAndPath("saferoot", "rootia"));

	private static final TagKey<Structure> ALTARS = TagKey.create(Registries.STRUCTURE, ResourceLocation.fromNamespaceAndPath("saferoot", "spawnboss"));

	private static final int SEARCH_RADIUS = 100;

	public BoussoleRootItem() {
		super(new Item.Properties().stacksTo(1).rarity(Rarity.RARE));
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
		ItemStack held = player.getItemInHand(hand);

		if (!(level instanceof ServerLevel serverLevel))
			return InteractionResultHolder.success(held);

		if (!level.dimension().equals(ROOTIA)) {
			player.displayClientMessage(Component.literal("Cette boussole ne s'oriente que dans Rootia.").withStyle(ChatFormatting.RED), true);
			return InteractionResultHolder.fail(held);
		}

		BlockPos altar = serverLevel.findNearestMapStructure(ALTARS, player.blockPosition(), SEARCH_RADIUS, false);
		if (altar == null) {
			player.displayClientMessage(Component.literal("Aucun autel à portée.").withStyle(ChatFormatting.RED), true);
			return InteractionResultHolder.fail(held);
		}

		ItemStack compass = new ItemStack(Items.COMPASS);
		compass.set(DataComponents.LODESTONE_TRACKER, new LodestoneTracker(Optional.of(GlobalPos.of(ROOTIA, altar)), false));
		compass.set(DataComponents.CUSTOM_NAME, Component.literal("Boussole de Root").withStyle(ChatFormatting.GOLD));

		held.shrink(1);
		if (!player.getInventory().add(compass))
			player.drop(compass, false);

		level.playSound(null, player.blockPosition(), SoundEvents.LODESTONE_COMPASS_LOCK, SoundSource.PLAYERS, 1.0F, 1.0F);
		player.displayClientMessage(Component.literal("Autel localisé : X " + altar.getX() + "  Z " + altar.getZ()).withStyle(ChatFormatting.GOLD), true);
		return InteractionResultHolder.consume(held);
	}
}
