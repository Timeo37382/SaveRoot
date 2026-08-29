package net.mcreator.saferoot.block;

import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.ButtonBlock;

public class BoisEnRootiumButtonBlock extends ButtonBlock {
	public BoisEnRootiumButtonBlock() {
		super(BlockSetType.OAK, 30, BlockBehaviour.Properties.of().sound(SoundType.WOOD).strength(0.5f).noCollission().pushReaction(PushReaction.DESTROY));
	}
}