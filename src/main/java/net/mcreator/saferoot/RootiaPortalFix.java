package net.mcreator.saferoot;

import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;

import java.util.Optional;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import net.mcreator.saferoot.block.RootiaPortalBlock;
import net.mcreator.saferoot.init.SaferootModBlocks;
import net.mcreator.saferoot.world.teleporter.RootiaPortalShape;

@EventBusSubscriber
public class RootiaPortalFix {

	private static final ResourceKey<Level> ROOTIA = ResourceKey.create(Registries.DIMENSION, ResourceLocation.fromNamespaceAndPath("saferoot", "rootia"));

	private static final int FRAME_RADIUS = 4;
	private static final int CLEAR_RADIUS = 2;
	private static final int CLEAR_HEIGHT = 3;

	@SubscribeEvent
	public static void onFlintAndSteel(PlayerInteractEvent.RightClickBlock event) {
		ItemStack stack = event.getItemStack();
		Level level = event.getLevel();
		if (!stack.is(Items.FLINT_AND_STEEL) || level.isClientSide() || event.getFace() == null)
			return;

		BlockPos target = event.getPos().relative(event.getFace());
		if (!level.isEmptyBlock(target))
			return;
		if (RootiaPortalShape.findEmptyPortalShape(level, target, Direction.Axis.X).isEmpty())
			return;

		RootiaPortalBlock.portalSpawn(level, target);
		level.playSound(null, target, SoundEvents.FLINTANDSTEEL_USE, SoundSource.BLOCKS, 1.0F, 1.0F);

		Player player = event.getEntity();
		if (!player.getAbilities().instabuild)
			stack.hurtAndBreak(1, player, LivingEntity.getSlotForHand(event.getHand()));
		event.setCanceled(true);
	}

	@SubscribeEvent
	public static void onDimensionChanged(PlayerEvent.PlayerChangedDimensionEvent event) {
		boolean enteringRootia = ROOTIA.equals(event.getTo());
		if (!enteringRootia && !ROOTIA.equals(event.getFrom()))
			return;
		if (!(event.getEntity().level() instanceof ServerLevel level))
			return;

		BlockPos arrival = event.getEntity().blockPosition();
		replaceLegacyFrame(level, arrival);
		relightPortal(level, arrival);
		if (enteringRootia)
			clearArrivalPocket(level, arrival);
	}

	private static void replaceLegacyFrame(ServerLevel level, BlockPos arrival) {
		BlockState rootium = SaferootModBlocks.BLOC_DE_ROOTIUM.get().defaultBlockState();
		for (int dx = -FRAME_RADIUS; dx <= FRAME_RADIUS; dx++) {
			for (int dy = -FRAME_RADIUS; dy <= FRAME_RADIUS + CLEAR_HEIGHT; dy++) {
				for (int dz = -FRAME_RADIUS; dz <= FRAME_RADIUS; dz++) {
					BlockPos pos = arrival.offset(dx, dy, dz);
					if (level.getBlockState(pos).is(Blocks.CRIMSON_PLANKS))
						level.setBlock(pos, rootium, 3);
				}
			}
		}
	}

	private static void relightPortal(ServerLevel level, BlockPos arrival) {
		for (int dy = -1; dy <= 2; dy++) {
			Optional<RootiaPortalShape> shape = RootiaPortalShape.findEmptyPortalShape(level, arrival.above(dy), Direction.Axis.X);
			if (shape.isPresent()) {
				shape.get().createPortalBlocks();
				return;
			}
		}
	}

	private static void clearArrivalPocket(ServerLevel level, BlockPos arrival) {
		for (int dx = -CLEAR_RADIUS; dx <= CLEAR_RADIUS; dx++) {
			for (int dz = -CLEAR_RADIUS; dz <= CLEAR_RADIUS; dz++) {
				for (int dy = 0; dy <= CLEAR_HEIGHT; dy++) {
					BlockPos pos = arrival.offset(dx, dy, dz);
					if (isCarvable(level, pos))
						level.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
				}
				BlockPos floor = arrival.offset(dx, -1, dz);
				if (level.getBlockState(floor).isAir())
					level.setBlock(floor, Blocks.DIRT.defaultBlockState(), 3);
			}
		}
	}

	private static boolean isCarvable(ServerLevel level, BlockPos pos) {
		BlockState state = level.getBlockState(pos);
		if (state.isAir() || state.getBlock() instanceof RootiaPortalBlock)
			return false;
		if (state.is(SaferootModBlocks.BLOC_DE_ROOTIUM.get()))
			return false;
		return state.getDestroySpeed(level, pos) >= 0;
	}
}
