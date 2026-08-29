package net.mcreator.saferoot.client.renderer;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.entity.layers.SaddleLayer;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.PigModel;

import net.mcreator.saferoot.entity.CochonRootiumEntity;

public class CochonRootiumRenderer extends MobRenderer<CochonRootiumEntity, PigModel<CochonRootiumEntity>> {
	private final ResourceLocation entityTexture = ResourceLocation.parse("saferoot:textures/entities/cochonrootium.png");

	public CochonRootiumRenderer(EntityRendererProvider.Context context) {
		super(context, new PigModel<CochonRootiumEntity>(context.bakeLayer(ModelLayers.PIG)), 0.7f);
		this.addLayer(new SaddleLayer<>(this, new PigModel<CochonRootiumEntity>(context.bakeLayer(ModelLayers.PIG_SADDLE)),
				ResourceLocation.withDefaultNamespace("textures/entity/pig/pig_saddle.png")));
	}

	@Override
	public ResourceLocation getTextureLocation(CochonRootiumEntity entity) {
		return entityTexture;
	}
}
