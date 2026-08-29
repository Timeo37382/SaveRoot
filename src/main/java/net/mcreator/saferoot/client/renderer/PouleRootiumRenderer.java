package net.mcreator.saferoot.client.renderer;

import net.minecraft.util.Mth;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.ChickenModel;

import net.mcreator.saferoot.entity.PouleRootiumEntity;

public class PouleRootiumRenderer extends MobRenderer<PouleRootiumEntity, ChickenModel<PouleRootiumEntity>> {
	private final ResourceLocation entityTexture = ResourceLocation.parse("saferoot:textures/entities/poulerootium.png");

	public PouleRootiumRenderer(EntityRendererProvider.Context context) {
		super(context, new ChickenModel<PouleRootiumEntity>(context.bakeLayer(ModelLayers.CHICKEN)), 0.3f);
	}

	@Override
	protected float getBob(PouleRootiumEntity entity, float partialTick) {
		float flap = Mth.lerp(partialTick, entity.oFlap, entity.flap);
		float speed = Mth.lerp(partialTick, entity.oFlapSpeed, entity.flapSpeed);
		return (Mth.sin(flap) + 1.0F) * speed;
	}

	@Override
	public ResourceLocation getTextureLocation(PouleRootiumEntity entity) {
		return entityTexture;
	}
}
