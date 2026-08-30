/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.saferoot.init;

import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.network.chat.Component;
import net.minecraft.core.registries.Registries;

import net.mcreator.saferoot.SaferootMod;

public class SaferootModTabs {
	public static final DeferredRegister<CreativeModeTab> REGISTRY = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, SaferootMod.MODID);
	public static final DeferredHolder<CreativeModeTab, CreativeModeTab> SAVE_ROOT_TAB = REGISTRY.register("save_root_tab",
			() -> CreativeModeTab.builder().title(Component.translatable("item_group.saferoot.save_root_tab")).icon(() -> new ItemStack(SaferootModItems.ROOTIUM_INGOT.get())).displayItems((parameters, tabData) -> {
				tabData.accept(SaferootModItems.ROOTIUM_INGOT.get());
				tabData.accept(SaferootModItems.RAW_ROOTIUM.get());
				tabData.accept(SaferootModBlocks.ROOTIUM_ORE.get().asItem());
				tabData.accept(SaferootModItems.ROOTIUM_ARMOR_HELMET.get());
				tabData.accept(SaferootModItems.ROOTIUM_ARMOR_CHESTPLATE.get());
				tabData.accept(SaferootModItems.ROOTIUM_ARMOR_LEGGINGS.get());
				tabData.accept(SaferootModItems.ROOTIUM_ARMOR_BOOTS.get());
				tabData.accept(SaferootModItems.ROOTIUM_PICKAXE.get());
				tabData.accept(SaferootModItems.ROOTIUM_AXE.get());
				tabData.accept(SaferootModBlocks.ROOTIUM_BLOCK.get().asItem());
				tabData.accept(SaferootModItems.ROOTIUM_HOE.get());
				tabData.accept(SaferootModItems.ROOTIUM_SWORD.get());
				tabData.accept(SaferootModItems.ROOTIUM_SHOVEL.get());
				tabData.accept(SaferootModItems.ROOT_SPAWN_EGG.get());
				tabData.accept(SaferootModBlocks.ROOTIUM_GRASS_BLOCK.get().asItem());
				tabData.accept(SaferootModBlocks.ROOTIUM_LEAVES.get().asItem());
				tabData.accept(SaferootModBlocks.ROOTIUM_FLOWER.get().asItem());
				tabData.accept(SaferootModBlocks.ROOTIUM_GRASS.get().asItem());
				tabData.accept(SaferootModBlocks.ROOTIUM_LOG.get().asItem());
				tabData.accept(SaferootModBlocks.ROOTIUM_WOOD.get().asItem());
				tabData.accept(SaferootModBlocks.STRIPPED_ROOTIUM_LOG.get().asItem());
				tabData.accept(SaferootModBlocks.STRIPPED_ROOTIUM_WOOD.get().asItem());
				tabData.accept(SaferootModBlocks.ROOTIUM_PLANKS.get().asItem());
				tabData.accept(SaferootModBlocks.ROOTIUM_STAIRS.get().asItem());
				tabData.accept(SaferootModBlocks.ROOTIUM_SLAB.get().asItem());
				tabData.accept(SaferootModBlocks.ROOTIUM_FENCE.get().asItem());
				tabData.accept(SaferootModBlocks.ROOTIUM_DOOR.get().asItem());
				tabData.accept(SaferootModBlocks.ROOTIUM_TRAPDOOR.get().asItem());
				tabData.accept(SaferootModBlocks.ROOTIUM_PRESSURE_PLATE.get().asItem());
				tabData.accept(SaferootModBlocks.ROOTIUM_BUTTON.get().asItem());
				tabData.accept(SaferootModBlocks.ROOTIUM_SIGN.get().asItem());
				tabData.accept(SaferootModBlocks.ROOTIUM_HANGING_SIGN.get().asItem());
				tabData.accept(SaferootModBlocks.ROOT_HEART.get().asItem());
				tabData.accept(SaferootModItems.WITHER_ROOT_SPAWN_EGG.get());
				tabData.accept(SaferootModItems.ROOT_COMPASS.get());
				tabData.accept(SaferootModItems.ROOTIUM_COW_SPAWN_EGG.get());
				tabData.accept(SaferootModItems.ROOTIUM_PIG_SPAWN_EGG.get());
				tabData.accept(SaferootModItems.ROOTIUM_CHICKEN_SPAWN_EGG.get());
				tabData.accept(SaferootModBlocks.ROOTIUM_SAPLING.get().asItem());
				tabData.accept(SaferootModBlocks.DEEPSLATE_ROOTIUM_ORE.get().asItem());
				tabData.accept(SaferootModBlocks.ROOTIUM_DIRT.get().asItem());
			}).build());
}