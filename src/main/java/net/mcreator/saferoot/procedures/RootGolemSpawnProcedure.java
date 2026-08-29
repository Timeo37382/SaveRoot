package net.mcreator.saferoot.procedures;

import net.neoforged.neoforge.event.level.BlockEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.BlockPos;

import net.mcreator.saferoot.init.SaferootModEntities;
import net.mcreator.saferoot.init.SaferootModBlocks;

@EventBusSubscriber
public class RootGolemSpawnProcedure {
	public static void execute() {
	}

	@SubscribeEvent
	public static void onBlockPlace(BlockEvent.EntityPlaceEvent event) {
		if (!(event.getLevel() instanceof ServerLevel world)) return;

		BlockPos headPos = event.getPos();
		BlockState headState = event.getPlacedBlock();

		if (!isPumpkin(headState)) return;

		BlockPos bodyPos = headPos.below();
		BlockPos basePos = bodyPos.below();
		BlockPos arm1Pos = bodyPos.east();
		BlockPos arm2Pos = bodyPos.west();
		BlockPos arm3Pos = bodyPos.north();
		BlockPos arm4Pos = bodyPos.south();

		boolean isRootiumBody = world.getBlockState(bodyPos).is(SaferootModBlocks.COEURDEROOT.get())
				&& world.getBlockState(basePos).is(SaferootModBlocks.BLOC_DE_ROOTIUM.get());

		if (!isRootiumBody) return;

		boolean eastWestArms = world.getBlockState(arm1Pos).is(SaferootModBlocks.BLOC_DE_ROOTIUM.get())
				&& world.getBlockState(arm2Pos).is(SaferootModBlocks.BLOC_DE_ROOTIUM.get());

		boolean northSouthArms = world.getBlockState(arm3Pos).is(SaferootModBlocks.BLOC_DE_ROOTIUM.get())
				&& world.getBlockState(arm4Pos).is(SaferootModBlocks.BLOC_DE_ROOTIUM.get());

		if (!eastWestArms && !northSouthArms) return;

		world.setBlock(headPos, Blocks.AIR.defaultBlockState(), 3);
		world.setBlock(bodyPos, Blocks.AIR.defaultBlockState(), 3);
		world.setBlock(basePos, Blocks.AIR.defaultBlockState(), 3);

		if (eastWestArms) {
			world.setBlock(arm1Pos, Blocks.AIR.defaultBlockState(), 3);
			world.setBlock(arm2Pos, Blocks.AIR.defaultBlockState(), 3);
		} else {
			world.setBlock(arm3Pos, Blocks.AIR.defaultBlockState(), 3);
			world.setBlock(arm4Pos, Blocks.AIR.defaultBlockState(), 3);
		}

		Entity rootEntity = SaferootModEntities.ROOT.get().spawn(world, headPos, MobSpawnType.TRIGGERED);
		if (rootEntity != null) {
			rootEntity.moveTo(headPos.getX() + 0.5, basePos.getY(), headPos.getZ() + 0.5, 0, 0);
		}
	}

	private static boolean isPumpkin(BlockState state) {
		return state.is(Blocks.CARVED_PUMPKIN) || state.is(Blocks.JACK_O_LANTERN) || state.is(Blocks.PUMPKIN);
	}
}
