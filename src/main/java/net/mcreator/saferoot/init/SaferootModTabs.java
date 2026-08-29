/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.saferoot.init;

import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;

import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.core.registries.Registries;

import net.mcreator.saferoot.SaferootMod;

@EventBusSubscriber
public class SaferootModTabs {
	public static final DeferredRegister<CreativeModeTab> REGISTRY = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, SaferootMod.MODID);

	@SubscribeEvent
	public static void buildTabContentsVanilla(BuildCreativeModeTabContentsEvent tabData) {
		if (tabData.getTabKey() == CreativeModeTabs.INGREDIENTS) {
			tabData.accept(SaferootModItems.LINGOTDEROOTIUM.get());
		} else if (tabData.getTabKey() == CreativeModeTabs.NATURAL_BLOCKS) {
			tabData.accept(SaferootModBlocks.ROOTIUM.get().asItem());
			tabData.accept(SaferootModBlocks.FEUILLAGEROOTIUM.get().asItem());
			tabData.accept(SaferootModBlocks.FLEUR_EN_ROOTIUM.get().asItem());
			tabData.accept(SaferootModBlocks.HEBRE.get().asItem());
			tabData.accept(SaferootModBlocks.SAPLING_ROOTIM.get().asItem());
		} else if (tabData.getTabKey() == CreativeModeTabs.COMBAT) {
			tabData.accept(SaferootModItems.ARMURE_EN_ROOTIUM_HELMET.get());
			tabData.accept(SaferootModItems.ARMURE_EN_ROOTIUM_CHESTPLATE.get());
			tabData.accept(SaferootModItems.ARMURE_EN_ROOTIUM_LEGGINGS.get());
			tabData.accept(SaferootModItems.ARMURE_EN_ROOTIUM_BOOTS.get());
			tabData.accept(SaferootModItems.BOUSSOLE_ROOT.get());
		} else if (tabData.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
			tabData.accept(SaferootModItems.PIOCHE.get());
			tabData.accept(SaferootModItems.HACHE.get());
			tabData.accept(SaferootModItems.HOUE.get());
			tabData.accept(SaferootModItems.EPEE.get());
			tabData.accept(SaferootModItems.PELLE.get());
		} else if (tabData.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS) {
			tabData.accept(SaferootModBlocks.BOIS_EN_ROOTIUM_LOG.get().asItem());
			tabData.accept(SaferootModBlocks.BOIS_EN_ROOTIUM_WOOD.get().asItem());
			tabData.accept(SaferootModBlocks.STRIPPED_BOIS_EN_ROOTIUM_LOG.get().asItem());
			tabData.accept(SaferootModBlocks.STRIPPED_BOIS_EN_ROOTIUM_WOOD.get().asItem());
			tabData.accept(SaferootModBlocks.BOIS_EN_ROOTIUM_PLANKS.get().asItem());
			tabData.accept(SaferootModBlocks.BOIS_EN_ROOTIUM_STAIRS.get().asItem());
			tabData.accept(SaferootModBlocks.BOIS_EN_ROOTIUM_SLAB.get().asItem());
			tabData.accept(SaferootModBlocks.BOIS_EN_ROOTIUM_FENCE.get().asItem());
			tabData.accept(SaferootModBlocks.BOIS_EN_ROOTIUM_FENCE_GATE.get().asItem());
			tabData.accept(SaferootModBlocks.BOIS_EN_ROOTIUM_DOOR.get().asItem());
			tabData.accept(SaferootModBlocks.BOIS_EN_ROOTIUM_TRAPDOOR.get().asItem());
			tabData.accept(SaferootModBlocks.BOIS_EN_ROOTIUM_PRESSURE_PLATE.get().asItem());
			tabData.accept(SaferootModBlocks.BOIS_EN_ROOTIUM_BUTTON.get().asItem());
			tabData.accept(SaferootModBlocks.COEURDEROOT.get().asItem());
		} else if (tabData.getTabKey() == CreativeModeTabs.FUNCTIONAL_BLOCKS) {
			tabData.accept(SaferootModBlocks.BOIS_EN_ROOTIUM_SIGN.get().asItem());
			tabData.accept(SaferootModBlocks.BOIS_EN_ROOTIUM_HANGING_SIGN.get().asItem());
		} else if (tabData.getTabKey() == CreativeModeTabs.SPAWN_EGGS) {
			tabData.accept(SaferootModItems.WHITHER_ROOT_SPAWN_EGG.get());
			tabData.accept(SaferootModItems.VACHE_EN_ROOTIUM_SPAWN_EGG.get());
			tabData.accept(SaferootModItems.COCHON_ROOTIUM_SPAWN_EGG.get());
			tabData.accept(SaferootModItems.POULE_ROOTIUM_SPAWN_EGG.get());
		}
	}
}