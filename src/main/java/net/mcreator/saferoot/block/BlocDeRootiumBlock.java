package net.mcreator.saferoot.block;

import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.Block;

public class BlocDeRootiumBlock extends Block {
	public BlocDeRootiumBlock() {
		super(BlockBehaviour.Properties.of().sound(SoundType.METAL).strength(1f, 25f).lightLevel(blockstate -> 4).requiresCorrectToolForDrops().hasPostProcess((bs, br, bp) -> true).emissiveRendering((bs, br, bp) -> true));
	}
}