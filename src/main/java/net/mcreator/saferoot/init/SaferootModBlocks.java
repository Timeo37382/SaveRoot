/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.saferoot.init;

import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.event.BlockEntityTypeAddBlocksEvent;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.api.distmarker.Dist;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.Block;
import net.minecraft.client.renderer.Sheets;

import net.mcreator.saferoot.block.*;
import net.mcreator.saferoot.SaferootMod;

@EventBusSubscriber
public class SaferootModBlocks {
	public static final DeferredRegister.Blocks REGISTRY = DeferredRegister.createBlocks(SaferootMod.MODID);
	public static final DeferredBlock<Block> ROOTIUM;
	public static final DeferredBlock<Block> BLOC_DE_ROOTIUM;
	public static final DeferredBlock<Block> HERBEENROOTIUM;
	public static final DeferredBlock<Block> FEUILLAGEROOTIUM;
	public static final DeferredBlock<Block> ROOTIA_PORTAL;
	public static final DeferredBlock<Block> FLEUR_EN_ROOTIUM;
	public static final DeferredBlock<Block> HEBRE;
	public static final DeferredBlock<Block> BOIS_EN_ROOTIUM_LOG;
	public static final DeferredBlock<Block> BOIS_EN_ROOTIUM_WOOD;
	public static final DeferredBlock<Block> STRIPPED_BOIS_EN_ROOTIUM_LOG;
	public static final DeferredBlock<Block> STRIPPED_BOIS_EN_ROOTIUM_WOOD;
	public static final DeferredBlock<Block> BOIS_EN_ROOTIUM_PLANKS;
	public static final DeferredBlock<Block> BOIS_EN_ROOTIUM_STAIRS;
	public static final DeferredBlock<Block> BOIS_EN_ROOTIUM_SLAB;
	public static final DeferredBlock<Block> BOIS_EN_ROOTIUM_FENCE;
	public static final DeferredBlock<Block> BOIS_EN_ROOTIUM_FENCE_GATE;
	public static final DeferredBlock<Block> BOIS_EN_ROOTIUM_DOOR;
	public static final DeferredBlock<Block> BOIS_EN_ROOTIUM_TRAPDOOR;
	public static final DeferredBlock<Block> BOIS_EN_ROOTIUM_PRESSURE_PLATE;
	public static final DeferredBlock<Block> BOIS_EN_ROOTIUM_BUTTON;
	public static final DeferredBlock<Block> BOIS_EN_ROOTIUM_SIGN;
	public static final DeferredBlock<Block> BOIS_EN_ROOTIUM_WALL_SIGN;
	public static final DeferredBlock<Block> BOIS_EN_ROOTIUM_HANGING_SIGN;
	public static final DeferredBlock<Block> BOIS_EN_ROOTIUM_WALL_HANGING_SIGN;
	public static final DeferredBlock<Block> COEURDEROOT;
	static {
		ROOTIUM = REGISTRY.register("rootium", RootiumBlock::new);
		BLOC_DE_ROOTIUM = REGISTRY.register("bloc_de_rootium", BlocDeRootiumBlock::new);
		HERBEENROOTIUM = REGISTRY.register("herbeenrootium", HerbeenrootiumBlock::new);
		FEUILLAGEROOTIUM = REGISTRY.register("feuillagerootium", FeuillagerootiumBlock::new);
		ROOTIA_PORTAL = REGISTRY.register("rootia_portal", RootiaPortalBlock::new);
		FLEUR_EN_ROOTIUM = REGISTRY.register("fleur_en_rootium", FleurEnRootiumBlock::new);
		HEBRE = REGISTRY.register("hebre", HebreBlock::new);
		BOIS_EN_ROOTIUM_LOG = REGISTRY.register("bois_en_rootium_log", BoisEnRootiumLogBlock::new);
		BOIS_EN_ROOTIUM_WOOD = REGISTRY.register("bois_en_rootium_wood", BoisEnRootiumWoodBlock::new);
		STRIPPED_BOIS_EN_ROOTIUM_LOG = REGISTRY.register("stripped_bois_en_rootium_log", StrippedBoisEnRootiumLogBlock::new);
		STRIPPED_BOIS_EN_ROOTIUM_WOOD = REGISTRY.register("stripped_bois_en_rootium_wood", StrippedBoisEnRootiumWoodBlock::new);
		BOIS_EN_ROOTIUM_PLANKS = REGISTRY.register("bois_en_rootium_planks", BoisEnRootiumPlanksBlock::new);
		BOIS_EN_ROOTIUM_STAIRS = REGISTRY.register("bois_en_rootium_stairs", BoisEnRootiumStairsBlock::new);
		BOIS_EN_ROOTIUM_SLAB = REGISTRY.register("bois_en_rootium_slab", BoisEnRootiumSlabBlock::new);
		BOIS_EN_ROOTIUM_FENCE = REGISTRY.register("bois_en_rootium_fence", BoisEnRootiumFenceBlock::new);
		BOIS_EN_ROOTIUM_FENCE_GATE = REGISTRY.register("bois_en_rootium_fence_gate", BoisEnRootiumFenceGateBlock::new);
		BOIS_EN_ROOTIUM_DOOR = REGISTRY.register("bois_en_rootium_door", BoisEnRootiumDoorBlock::new);
		BOIS_EN_ROOTIUM_TRAPDOOR = REGISTRY.register("bois_en_rootium_trapdoor", BoisEnRootiumTrapdoorBlock::new);
		BOIS_EN_ROOTIUM_PRESSURE_PLATE = REGISTRY.register("bois_en_rootium_pressure_plate", BoisEnRootiumPressurePlateBlock::new);
		BOIS_EN_ROOTIUM_BUTTON = REGISTRY.register("bois_en_rootium_button", BoisEnRootiumButtonBlock::new);
		BOIS_EN_ROOTIUM_SIGN = REGISTRY.register("bois_en_rootium_sign", BoisEnRootiumSignBlock::new);
		BOIS_EN_ROOTIUM_WALL_SIGN = REGISTRY.register("bois_en_rootium_wall_sign", BoisEnRootiumWallSignBlock::new);
		BOIS_EN_ROOTIUM_HANGING_SIGN = REGISTRY.register("bois_en_rootium_hanging_sign", BoisEnRootiumHangingSignBlock::new);
		BOIS_EN_ROOTIUM_WALL_HANGING_SIGN = REGISTRY.register("bois_en_rootium_wall_hanging_sign", BoisEnRootiumWallHangingSignBlock::new);
		COEURDEROOT = REGISTRY.register("coeurderoot", CoeurderootBlock::new);
	}

	// Start of user code block custom blocks
	// End of user code block custom blocks
	@EventBusSubscriber(Dist.CLIENT)
	public static class BlocksClientSideHandler {
		@SubscribeEvent
		public static void clientSetup(FMLClientSetupEvent event) {
			Sheets.addWoodType(SaferootModWoodTypes.BOIS_EN_ROOTIUM_SIGN_WOOD_TYPE);
			Sheets.addWoodType(SaferootModWoodTypes.BOIS_EN_ROOTIUM_HANGING_SIGN_WOOD_TYPE);
		}
	}

	@SubscribeEvent
	public static void registerSigns(BlockEntityTypeAddBlocksEvent event) {
		event.modify(BlockEntityType.SIGN, BOIS_EN_ROOTIUM_SIGN.get(), BOIS_EN_ROOTIUM_WALL_SIGN.get());
		event.modify(BlockEntityType.HANGING_SIGN, BOIS_EN_ROOTIUM_HANGING_SIGN.get(), BOIS_EN_ROOTIUM_WALL_HANGING_SIGN.get());
	}
}