/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.saferoot.init;

import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.api.distmarker.Dist;

import net.mcreator.saferoot.client.model.Modelrootiumvache;

@EventBusSubscriber(Dist.CLIENT)
public class SaferootModModels {
	@SubscribeEvent
	public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
		event.registerLayerDefinition(Modelrootiumvache.LAYER_LOCATION, Modelrootiumvache::createBodyLayer);
	}
}