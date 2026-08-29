package net.mcreator.saferoot.block;

import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.Block;

public class CoeurderootBlock extends Block {
	public CoeurderootBlock() {
		super(BlockBehaviour.Properties.of().sound(SoundType.GRAVEL).strength(50f, 1200f).requiresCorrectToolForDrops());
	}
}