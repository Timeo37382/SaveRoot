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
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;

import net.mcreator.saferoot.init.SaferootModItems;
import net.mcreator.saferoot.init.SaferootModBlocks;

import java.util.*;

@EventBusSubscriber
public class VeinMinerPiocheProcedure {

	// Cette méthode vide sert à satisfaire l'appel généré par MCreator dans PiocheItem.java
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

		if (!itemstack.is(SaferootModItems.ROOTIUM_PICKAXE.get())) return;
		if (player.isCrouching()) return;
		if (player.isCreative()) return;

		String blockName = blockstate.getBlock().getDescriptionId();
		boolean isOre = blockName.contains("ore") || blockstate.is(SaferootModBlocks.ROOTIUM_ORE.get())
				|| blockstate.is(SaferootModBlocks.DEEPSLATE_ROOTIUM_ORE.get());

		if (!isOre) return;

		BlockPos origin = BlockPos.containing(x, y, z);
		Random rand = new Random();

		List<BlockPos> result = new ArrayList<>();
		Set<BlockPos> visited = new HashSet<>();
		Queue<BlockPos> queue = new ArrayDeque<>();
		visited.add(origin);
		queue.add(origin);

		while (!queue.isEmpty() && result.size() < 32) {
			BlockPos current = queue.poll();
			for (Direction dir : Direction.values()) {
				BlockPos neighbor = current.relative(dir);
				if (visited.contains(neighbor)) continue;
				visited.add(neighbor);
				if (serverLevel.getBlockState(neighbor).is(blockstate.getBlock())) {
					result.add(neighbor);
					queue.add(neighbor);
				}
			}
		}

		for (BlockPos pos : result) {
			if (itemstack.getDamageValue() >= itemstack.getMaxDamage() - 1) break;
			BlockState state = serverLevel.getBlockState(pos);
			serverLevel.destroyBlock(pos, false);
			Block.dropResources(state, serverLevel, pos, null, player, itemstack);
			if (rand.nextDouble() < 0.02) Block.dropResources(state, serverLevel, pos, null, player, itemstack);
			itemstack.hurtAndBreak(1, player, EquipmentSlot.MAINHAND);
		}

		if (rand.nextDouble() < 0.02) Block.dropResources(blockstate, serverLevel, origin, null, player, itemstack);
	}
}