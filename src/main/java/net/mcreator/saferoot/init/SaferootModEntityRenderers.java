/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.saferoot.init;

import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.api.distmarker.Dist;

import net.mcreator.saferoot.client.renderer.WhitherRootRenderer;
import net.mcreator.saferoot.client.renderer.VacheEnRootiumRenderer;
import net.mcreator.saferoot.client.renderer.PouleRootiumRenderer;
import net.mcreator.saferoot.client.renderer.CochonRootiumRenderer;

@EventBusSubscriber(Dist.CLIENT)
public class SaferootModEntityRenderers {
	@SubscribeEvent
	public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
		event.registerEntityRenderer(SaferootModEntities.WITHER_ROOT.get(), WhitherRootRenderer::new);
		event.registerEntityRenderer(SaferootModEntities.ROOTIUM_COW.get(), VacheEnRootiumRenderer::new);
		event.registerEntityRenderer(SaferootModEntities.ROOTIUM_PIG.get(), CochonRootiumRenderer::new);
		event.registerEntityRenderer(SaferootModEntities.ROOTIUM_CHICKEN.get(), PouleRootiumRenderer::new);
	}
}