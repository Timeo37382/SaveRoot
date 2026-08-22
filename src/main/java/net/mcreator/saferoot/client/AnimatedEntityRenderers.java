package net.mcreator.saferoot.client;

import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.api.distmarker.Dist;

import net.mcreator.saferoot.init.SaferootModEntities;
import net.mcreator.saferoot.client.renderer.ROOTRenderer;
import net.mcreator.saferoot.SaferootMod;

@EventBusSubscriber(modid = SaferootMod.MODID, value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
public class AnimatedEntityRenderers {
	@SubscribeEvent
	public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
		event.registerEntityRenderer(SaferootModEntities.ROOT.get(), ROOTRenderer::new);
	}
}