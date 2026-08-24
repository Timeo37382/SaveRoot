package net.mcreator.saferoot.block;

import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.WallHangingSignBlock;
import net.minecraft.world.level.block.SoundType;

import net.mcreator.saferoot.init.SaferootModWoodTypes;
import net.mcreator.saferoot.init.SaferootModBlocks;

public class BoisEnRootiumWallHangingSignBlock extends WallHangingSignBlock {
	public BoisEnRootiumWallHangingSignBlock() {
		super(SaferootModWoodTypes.BOIS_EN_ROOTIUM_HANGING_SIGN_WOOD_TYPE,
				BlockBehaviour.Properties.of().sound(SoundType.HANGING_SIGN).strength(1f).noCollission().ignitedByLava().instrument(NoteBlockInstrument.BASS).forceSolidOn().dropsLike(SaferootModBlocks.BOIS_EN_ROOTIUM_HANGING_SIGN.get()));
	}
}