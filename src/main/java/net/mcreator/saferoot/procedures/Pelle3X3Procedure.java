package net.mcreator.saferoot.procedures;

import net.neoforged.neoforge.event.level.BlockEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;

import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Entity;
import net.minecraft.tags.BlockTags;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;

import net.mcreator.saferoot.init.SaferootModItems;

import java.util.*;

@EventBusSubscriber
public class Pelle3X3Procedure {

	public static void execute() {
	}

	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, ItemStack itemstack, BlockState blockstate) {
		execute(null, world, x, y, z, entity, itemstack, blockstate);
	}

	@SubscribeEvent
	public static void onBlockBreak(BlockEvent.BreakEvent event) {
		execute(event, event.getLevel(), event.getPos().getX(), event.getPos().getY(), event.getPos().getZ(), event.getPlayer(), event.getPlayer().getMainHandItem(), event.getState());
	}

	private static void execute(BlockEvent.BreakEvent event, LevelAccessor world, double x, double y, double z, Entity entity, ItemStack itemstack, BlockState blockstate) {
		if (entity == null) return;
		if (!(entity instanceof Player player)) return;
		if (!(world instanceof ServerLevel serverLevel)) return;

		if (!itemstack.is(SaferootModItems.ROOTIUM_SHOVEL.get())) return;
		if (player.isCrouching()) return;
		if (player.isCreative()) return;

		// Vérifie si le bloc se mine à la pelle (MINEABLE_WITH_SHOVEL)
		if (!blockstate.is(BlockTags.MINEABLE_WITH_SHOVEL)) return;

		BlockPos center = BlockPos.containing(x, y, z);
		Direction facing = getTargetDirection(player);

		List<BlockPos> targets = new ArrayList<>();

		for (int a = -1; a <= 1; a++) {
			for (int b = -1; b <= 1; b++) {
				if (a == 0 && b == 0) continue; // Le bloc d'origine est déjà cassé par le joueur

				BlockPos targetPos;
				if (facing == Direction.UP || facing == Direction.DOWN) {
					// Cassé au sol/plafond -> plan horizontal (X, Z)
					targetPos = center.offset(a, 0, b);
				} else if (facing == Direction.NORTH || facing == Direction.SOUTH) {
					// Cassé face Nord/Sud -> plan vertical (X, Y)
					targetPos = center.offset(a, b, 0);
				} else {
					// Cassé face Est/Ouest -> plan vertical (Z, Y)
					targetPos = center.offset(0, b, a);
				}

				BlockState targetState = serverLevel.getBlockState(targetPos);
				if (targetState.is(BlockTags.MINEABLE_WITH_SHOVEL)) {
					targets.add(targetPos);
				}
			}
		}

		for (BlockPos pos : targets) {
			if (itemstack.getDamageValue() >= itemstack.getMaxDamage() - 1) break;
			BlockState state = serverLevel.getBlockState(pos);
			serverLevel.destroyBlock(pos, false);
			Block.dropResources(state, serverLevel, pos, null, player, itemstack);
			itemstack.hurtAndBreak(1, player, EquipmentSlot.MAINHAND);
		}
	}

	private static Direction getTargetDirection(Player player) {
		HitResult rayTrace = player.pick(5.0D, 0.0F, false);
		if (rayTrace instanceof BlockHitResult blockHitResult) {
			return blockHitResult.getDirection();
		}
		return player.getDirection();
	}
}