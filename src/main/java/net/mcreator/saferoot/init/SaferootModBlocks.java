/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.saferoot.init;

import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredBlock;

import net.minecraft.world.level.block.Block;

import net.mcreator.saferoot.block.RootiumBlock;
import net.mcreator.saferoot.block.BlocDeRootiumBlock;
import net.mcreator.saferoot.SaferootMod;

public class SaferootModBlocks {
	public static final DeferredRegister.Blocks REGISTRY = DeferredRegister.createBlocks(SaferootMod.MODID);
	public static final DeferredBlock<Block> ROOTIUM;
	public static final DeferredBlock<Block> BLOC_DE_ROOTIUM;
	static {
		ROOTIUM = REGISTRY.register("rootium", RootiumBlock::new);
		BLOC_DE_ROOTIUM = REGISTRY.register("bloc_de_rootium", BlocDeRootiumBlock::new);
	}
	// Start of user code block custom blocks
	// End of user code block custom blocks
}