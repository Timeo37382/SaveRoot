/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.saferoot.init;

import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredBlock;

import net.minecraft.world.level.block.Block;

import net.mcreator.saferoot.block.*;
import net.mcreator.saferoot.SaferootMod;

public class SaferootModBlocks {
	public static final DeferredRegister.Blocks REGISTRY = DeferredRegister.createBlocks(SaferootMod.MODID);
	public static final DeferredBlock<Block> ROOTIUM;
	public static final DeferredBlock<Block> BLOC_DE_ROOTIUM;
	public static final DeferredBlock<Block> HERBEENROOTIUM;
	public static final DeferredBlock<Block> BUCHEDEROOTIUM;
	public static final DeferredBlock<Block> FEUILLAGEROOTIUM;
	public static final DeferredBlock<Block> ROOTIA_PORTAL;
	public static final DeferredBlock<Block> FLEUR_EN_ROOTIUM;
	public static final DeferredBlock<Block> HEBRE;
	static {
		ROOTIUM = REGISTRY.register("rootium", RootiumBlock::new);
		BLOC_DE_ROOTIUM = REGISTRY.register("bloc_de_rootium", BlocDeRootiumBlock::new);
		HERBEENROOTIUM = REGISTRY.register("herbeenrootium", HerbeenrootiumBlock::new);
		BUCHEDEROOTIUM = REGISTRY.register("buchederootium", BuchederootiumBlock::new);
		FEUILLAGEROOTIUM = REGISTRY.register("feuillagerootium", FeuillagerootiumBlock::new);
		ROOTIA_PORTAL = REGISTRY.register("rootia_portal", RootiaPortalBlock::new);
		FLEUR_EN_ROOTIUM = REGISTRY.register("fleur_en_rootium", FleurEnRootiumBlock::new);
		HEBRE = REGISTRY.register("hebre", HebreBlock::new);
	}
	// Start of user code block custom blocks
	// End of user code block custom blocks
}