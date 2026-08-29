package net.mcreator.saferoot.client.renderer;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.CowModel;

import net.mcreator.saferoot.entity.VacheEnRootiumEntity;

public class VacheEnRootiumRenderer extends MobRenderer<VacheEnRootiumEntity, CowModel<VacheEnRootiumEntity>> {
	private final ResourceLocation entityTexture = ResourceLocation.parse("saferoot:textures/entities/vacherootium.png");

	public VacheEnRootiumRenderer(EntityRendererProvider.Context context) {
		super(context, new CowModel<VacheEnRootiumEntity>(context.bakeLayer(ModelLayers.COW)), 0.7f);
	}

	@Override
	public ResourceLocation getTextureLocation(VacheEnRootiumEntity entity) {
		return entityTexture;
	}
}
