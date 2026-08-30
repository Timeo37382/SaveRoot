package net.mcreator.saferoot.procedures;

import net.neoforged.neoforge.event.level.BlockEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
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
public class TreeFellerHacheProcedure {

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

		if (!itemstack.is(SaferootModItems.ROOTIUM_AXE.get())) return;
		if (player.isCrouching()) return;
		if (player.isCreative()) return;

		// Vérifie si le bloc cassé est un bloc de bois (logs)
		boolean isLog = blockstate.is(BlockTags.LOGS);
		if (!isLog) return;

		BlockPos origin = BlockPos.containing(x, y, z);

		List<BlockPos> result = new ArrayList<>();
		Set<BlockPos> visited = new HashSet<>();
		Queue<BlockPos> queue = new ArrayDeque<>();
		visited.add(origin);
		queue.add(origin);

		// Limite à 64 blocs de bois maximum par arbre
		while (!queue.isEmpty() && result.size() < 64) {
			BlockPos current = queue.poll();
			
			// Vérifie les 26 directions autour du bloc (y compris les diagonales pour remonter l'arbre)
			for (int dx = -1; dx <= 1; dx++) {
				for (int dy = -1; dy <= 1; dy++) {
					for (int dz = -1; dz <= 1; dz++) {
						if (dx == 0 && dy == 0 && dz == 0) continue;
						BlockPos neighbor = current.offset(dx, dy, dz);
						if (visited.contains(neighbor)) continue;
						visited.add(neighbor);

						BlockState neighborState = serverLevel.getBlockState(neighbor);
						if (neighborState.is(BlockTags.LOGS)) {
							result.add(neighbor);
							queue.add(neighbor);
						}
					}
				}
			}
		}

		for (BlockPos pos : result) {
			if (itemstack.getDamageValue() >= itemstack.getMaxDamage() - 1) break;
			BlockState state = serverLevel.getBlockState(pos);
			serverLevel.destroyBlock(pos, false);
			Block.dropResources(state, serverLevel, pos, null, player, itemstack);
			itemstack.hurtAndBreak(1, player, EquipmentSlot.MAINHAND);
		}
	}
}