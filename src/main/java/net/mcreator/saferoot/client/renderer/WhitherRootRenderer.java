package net.mcreator.saferoot.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.model.WitherBossModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.WitherArmorLayer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.boss.wither.WitherBoss;

import net.mcreator.saferoot.entity.WhitherRootEntity;

public class WhitherRootRenderer extends MobRenderer<WitherBoss, WitherBossModel<WitherBoss>> {

	private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath("saferoot", "textures/entities/witherorange.png");

	private static final ResourceLocation TEXTURE_CHARGING = ResourceLocation.fromNamespaceAndPath("saferoot", "textures/entities/witherinvulnerableorange.png");

	public WhitherRootRenderer(EntityRendererProvider.Context context) {
		super(context, new WitherBossModel<>(context.bakeLayer(ModelLayers.WITHER)), 1.0F);
		this.addLayer(new WitherArmorLayer(this, context.getModelSet()));
	}

	@Override
	protected int getBlockLightLevel(WitherBoss entity, BlockPos pos) {
		return 15;
	}

	@Override
	protected void scale(WitherBoss entity, PoseStack poseStack, float partialTick) {
		float scale = 2.0F;
		int invulnerableTicks = entity.getInvulnerableTicks();
		if (invulnerableTicks > 0)
			scale -= ((float) invulnerableTicks - partialTick) / (float) WhitherRootEntity.CHARGE_TICKS * 0.5F;
		poseStack.scale(scale, scale, scale);
	}

	@Override
	public ResourceLocation getTextureLocation(WitherBoss entity) {
		int invulnerableTicks = entity.getInvulnerableTicks();
		return invulnerableTicks > 0 && (invulnerableTicks > 120 || invulnerableTicks / 5 % 2 != 1) ? TEXTURE_CHARGING : TEXTURE;
	}
}
