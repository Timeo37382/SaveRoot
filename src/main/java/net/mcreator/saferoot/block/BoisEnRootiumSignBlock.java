package net.mcreator.saferoot.block;

import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.StandingSignBlock;
import net.minecraft.world.level.block.SoundType;

import net.mcreator.saferoot.init.SaferootModWoodTypes;

public class BoisEnRootiumSignBlock extends StandingSignBlock {
	public BoisEnRootiumSignBlock() {
		super(SaferootModWoodTypes.ROOTIUM_SIGN_WOOD_TYPE, BlockBehaviour.Properties.of().sound(SoundType.WOOD).strength(1f).noCollission().ignitedByLava().instrument(NoteBlockInstrument.BASS).forceSolidOn());
	}
}