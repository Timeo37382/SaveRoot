package net.mcreator.saferoot.block;

import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.Block;

public class HerbeenrootiumBlock extends Block {
	public HerbeenrootiumBlock() {
		super(BlockBehaviour.Properties.of().sound(SoundType.WET_GRASS).instabreak().lightLevel(blockstate -> 3).hasPostProcess((bs, br, bp) -> true).emissiveRendering((bs, br, bp) -> true));
	}
}