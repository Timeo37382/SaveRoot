package net.mcreator.saferoot.client.renderer;

import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.cache.object.BakedGeoModel;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.MultiBufferSource;

import net.mcreator.saferoot.entity.model.ROOTModel;
import net.mcreator.saferoot.entity.ROOTEntity;

import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.PoseStack;

public class ROOTRenderer extends GeoEntityRenderer<ROOTEntity> {
	public ROOTRenderer(EntityRendererProvider.Context context) {
		super(context, new ROOTModel());
		this.shadowRadius = 0.5f;
	}

	@Override
	public RenderType getRenderType(ROOTEntity animatable, ResourceLocation texture, MultiBufferSource bufferSource, float partialTick) {
		return RenderType.entityTranslucent(getTextureLocation(animatable));
	}

	@Override
	public void preRender(PoseStack poseStack, ROOTEntity animatable, BakedGeoModel model, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, int colour) {
		// Hide Blockbench utility bones so they are never drawn as geometry
		model.getBone("hitbox").ifPresent(bone -> bone.setHidden(true));
		model.getBone("tag_name").ifPresent(bone -> bone.setHidden(true));
		super.preRender(poseStack, animatable, model, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, colour);
	}
}