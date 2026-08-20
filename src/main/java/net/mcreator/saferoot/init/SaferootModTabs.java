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
		} else if (tabData.getTabKey() == CreativeModeTabs.COMBAT) {
			tabData.accept(SaferootModItems.ARMURE_EN_ROOTIUM_HELMET.get());
			tabData.accept(SaferootModItems.ARMURE_EN_ROOTIUM_CHESTPLATE.get());
			tabData.accept(SaferootModItems.ARMURE_EN_ROOTIUM_LEGGINGS.get());
			tabData.accept(SaferootModItems.ARMURE_EN_ROOTIUM_BOOTS.get());
		} else if (tabData.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
			tabData.accept(SaferootModItems.PIOCHE.get());
			tabData.accept(SaferootModItems.HACHE.get());
			tabData.accept(SaferootModItems.HOUE.get());
			tabData.accept(SaferootModItems.EPEE.get());
			tabData.accept(SaferootModItems.PELLE.get());
		}
	}
}