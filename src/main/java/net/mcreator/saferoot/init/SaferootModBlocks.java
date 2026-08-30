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
	public static final DeferredBlock<Block> ROOTIUM_ORE;
	public static final DeferredBlock<Block> ROOTIUM_BLOCK;
	public static final DeferredBlock<Block> ROOTIUM_GRASS_BLOCK;
	public static final DeferredBlock<Block> ROOTIUM_LEAVES;
	public static final DeferredBlock<Block> ROOTIA_PORTAL;
	public static final DeferredBlock<Block> ROOTIUM_FLOWER;
	public static final DeferredBlock<Block> ROOTIUM_GRASS;
	public static final DeferredBlock<Block> ROOTIUM_LOG;
	public static final DeferredBlock<Block> ROOTIUM_WOOD;
	public static final DeferredBlock<Block> STRIPPED_ROOTIUM_LOG;
	public static final DeferredBlock<Block> STRIPPED_ROOTIUM_WOOD;
	public static final DeferredBlock<Block> ROOTIUM_PLANKS;
	public static final DeferredBlock<Block> ROOTIUM_STAIRS;
	public static final DeferredBlock<Block> ROOTIUM_SLAB;
	public static final DeferredBlock<Block> ROOTIUM_FENCE;
	public static final DeferredBlock<Block> ROOTIUM_FENCE_GATE;
	public static final DeferredBlock<Block> ROOTIUM_DOOR;
	public static final DeferredBlock<Block> ROOTIUM_TRAPDOOR;
	public static final DeferredBlock<Block> ROOTIUM_PRESSURE_PLATE;
	public static final DeferredBlock<Block> ROOTIUM_BUTTON;
	public static final DeferredBlock<Block> ROOTIUM_SIGN;
	public static final DeferredBlock<Block> ROOTIUM_WALL_SIGN;
	public static final DeferredBlock<Block> ROOTIUM_HANGING_SIGN;
	public static final DeferredBlock<Block> ROOTIUM_WALL_HANGING_SIGN;
	public static final DeferredBlock<Block> ROOT_HEART;
	public static final DeferredBlock<Block> ROOTIUM_SAPLING;
	public static final DeferredBlock<Block> DEEPSLATE_ROOTIUM_ORE;
	public static final DeferredBlock<Block> ROOTIUM_DIRT;
	static {
		ROOTIUM_ORE = REGISTRY.register("rootium_ore", RootiumBlock::new);
		ROOTIUM_BLOCK = REGISTRY.register("rootium_block", BlocDeRootiumBlock::new);
		ROOTIUM_GRASS_BLOCK = REGISTRY.register("rootium_grass_block", HerbeenrootiumBlock::new);
		ROOTIUM_LEAVES = REGISTRY.register("rootium_leaves", FeuillagerootiumBlock::new);
		ROOTIA_PORTAL = REGISTRY.register("rootia_portal", RootiaPortalBlock::new);
		ROOTIUM_FLOWER = REGISTRY.register("rootium_flower", FleurEnRootiumBlock::new);
		ROOTIUM_GRASS = REGISTRY.register("rootium_grass", HebreBlock::new);
		ROOTIUM_LOG = REGISTRY.register("rootium_log", BoisEnRootiumLogBlock::new);
		ROOTIUM_WOOD = REGISTRY.register("rootium_wood", BoisEnRootiumWoodBlock::new);
		STRIPPED_ROOTIUM_LOG = REGISTRY.register("stripped_rootium_log", StrippedBoisEnRootiumLogBlock::new);
		STRIPPED_ROOTIUM_WOOD = REGISTRY.register("stripped_rootium_wood", StrippedBoisEnRootiumWoodBlock::new);
		ROOTIUM_PLANKS = REGISTRY.register("rootium_planks", BoisEnRootiumPlanksBlock::new);
		ROOTIUM_STAIRS = REGISTRY.register("rootium_stairs", BoisEnRootiumStairsBlock::new);
		ROOTIUM_SLAB = REGISTRY.register("rootium_slab", BoisEnRootiumSlabBlock::new);
		ROOTIUM_FENCE = REGISTRY.register("rootium_fence", BoisEnRootiumFenceBlock::new);
		ROOTIUM_FENCE_GATE = REGISTRY.register("rootium_fence_gate", BoisEnRootiumFenceGateBlock::new);
		ROOTIUM_DOOR = REGISTRY.register("rootium_door", BoisEnRootiumDoorBlock::new);
		ROOTIUM_TRAPDOOR = REGISTRY.register("rootium_trapdoor", BoisEnRootiumTrapdoorBlock::new);
		ROOTIUM_PRESSURE_PLATE = REGISTRY.register("rootium_pressure_plate", BoisEnRootiumPressurePlateBlock::new);
		ROOTIUM_BUTTON = REGISTRY.register("rootium_button", BoisEnRootiumButtonBlock::new);
		ROOTIUM_SIGN = REGISTRY.register("rootium_sign", BoisEnRootiumSignBlock::new);
		ROOTIUM_WALL_SIGN = REGISTRY.register("rootium_wall_sign", BoisEnRootiumWallSignBlock::new);
		ROOTIUM_HANGING_SIGN = REGISTRY.register("rootium_hanging_sign", BoisEnRootiumHangingSignBlock::new);
		ROOTIUM_WALL_HANGING_SIGN = REGISTRY.register("rootium_wall_hanging_sign", BoisEnRootiumWallHangingSignBlock::new);
		ROOT_HEART = REGISTRY.register("root_heart", CoeurderootBlock::new);
		ROOTIUM_SAPLING = REGISTRY.register("rootium_sapling", SaplingRootimBlock::new);
		DEEPSLATE_ROOTIUM_ORE = REGISTRY.register("deepslate_rootium_ore", RootiumDeepBlock::new);
		ROOTIUM_DIRT = REGISTRY.register("rootium_dirt", RootiumDirtBlock::new);
	}

	// Start of user code block custom blocks
	// End of user code block custom blocks
	@EventBusSubscriber(Dist.CLIENT)
	public static class BlocksClientSideHandler {
		@SubscribeEvent
		public static void clientSetup(FMLClientSetupEvent event) {
			Sheets.addWoodType(SaferootModWoodTypes.ROOTIUM_SIGN_WOOD_TYPE);
			Sheets.addWoodType(SaferootModWoodTypes.ROOTIUM_HANGING_SIGN_WOOD_TYPE);
		}
	}

	@SubscribeEvent
	public static void registerSigns(BlockEntityTypeAddBlocksEvent event) {
		event.modify(BlockEntityType.SIGN, ROOTIUM_SIGN.get(), ROOTIUM_WALL_SIGN.get());
		event.modify(BlockEntityType.HANGING_SIGN, ROOTIUM_HANGING_SIGN.get(), ROOTIUM_WALL_HANGING_SIGN.get());
	}
}