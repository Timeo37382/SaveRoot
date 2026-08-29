package net.mcreator.saferoot.block;

import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.WallSignBlock;
import net.minecraft.world.level.block.SoundType;

import net.mcreator.saferoot.init.SaferootModWoodTypes;
import net.mcreator.saferoot.init.SaferootModBlocks;

public class BoisEnRootiumWallSignBlock extends WallSignBlock {
	public BoisEnRootiumWallSignBlock() {
		super(SaferootModWoodTypes.BOIS_EN_ROOTIUM_SIGN_WOOD_TYPE,
				BlockBehaviour.Properties.of().sound(SoundType.WOOD).strength(1f).noCollission().ignitedByLava().instrument(NoteBlockInstrument.BASS).forceSolidOn().dropsLike(SaferootModBlocks.BOIS_EN_ROOTIUM_SIGN.get()));
	}
}