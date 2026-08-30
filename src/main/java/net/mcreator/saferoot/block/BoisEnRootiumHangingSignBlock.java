package net.mcreator.saferoot.block;

import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.CeilingHangingSignBlock;

import net.mcreator.saferoot.init.SaferootModWoodTypes;

public class BoisEnRootiumHangingSignBlock extends CeilingHangingSignBlock {
	public BoisEnRootiumHangingSignBlock() {
		super(SaferootModWoodTypes.ROOTIUM_HANGING_SIGN_WOOD_TYPE, BlockBehaviour.Properties.of().sound(SoundType.HANGING_SIGN).strength(1f).noCollission().ignitedByLava().instrument(NoteBlockInstrument.BASS).forceSolidOn());
	}
}