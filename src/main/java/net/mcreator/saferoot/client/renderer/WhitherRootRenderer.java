package net.mcreator.saferoot.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;

import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.api.distmarker.Dist;

import net.minecraft.client.model.WitherBossModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.SmokeParticle;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.WitherSkullRenderer;
import net.minecraft.client.renderer.entity.layers.WitherArmorLayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.projectile.WitherSkull;

import net.mcreator.saferoot.entity.WhitherRootEntity;

@EventBusSubscriber(Dist.CLIENT)
public class WhitherRootRenderer extends MobRenderer<WitherBoss, WitherBossModel<WitherBoss>> {

	private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath("saferoot", "textures/entities/witherorange.png");

	private static final ResourceLocation TEXTURE_CHARGING = ResourceLocation.fromNamespaceAndPath("saferoot", "textures/entities/witherinvulnerableorange.png");

	private static final float AURA_RED = 0.85F;
	private static final float AURA_GREEN = 0.34F;
	private static final float AURA_BLUE = 0.05F;

	public WhitherRootRenderer(EntityRendererProvider.Context context) {
		super(context, new WitherBossModel<>(context.bakeLayer(ModelLayers.WITHER)), 1.0F);
		this.addLayer(new WitherArmorLayer(this, context.getModelSet()));
	}

	@SubscribeEvent
	public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
		event.registerEntityRenderer(EntityType.WITHER_SKULL, SkullRenderer::new);
	}

	@SubscribeEvent
	public static void registerParticles(RegisterParticleProvidersEvent event) {
		event.registerSpriteSet(ParticleTypes.SMOKE, sprites -> {
			SmokeParticle.Provider smoke = new SmokeParticle.Provider(sprites);
			return (type, world, x, y, z, xd, yd, zd) -> {
				Particle particle = smoke.createParticle(type, world, x, y, z, xd, yd, zd);
				if (particle != null && WhitherRootEntity.isAuraArea(x, y, z))
					particle.setColor(AURA_RED, AURA_GREEN, AURA_BLUE);
				return particle;
			};
		});
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
		return invulnerableTicks > 0 && (invulnerableTicks > 60 || invulnerableTicks / 5 % 2 != 1) ? TEXTURE_CHARGING : TEXTURE;
	}

	public static class SkullRenderer extends WitherSkullRenderer {

		private static final ResourceLocation SKULL = ResourceLocation.fromNamespaceAndPath("saferoot", "textures/entities/witherskullorange.png");

		private static final ResourceLocation SKULL_DANGEROUS = ResourceLocation.fromNamespaceAndPath("saferoot", "textures/entities/witherskullinvulnerableorange.png");

		public SkullRenderer(EntityRendererProvider.Context context) {
			super(context);
		}

		@Override
		public ResourceLocation getTextureLocation(WitherSkull skull) {
			if (skull.getOwner() instanceof WhitherRootEntity)
				return skull.isDangerous() ? SKULL_DANGEROUS : SKULL;
			return super.getTextureLocation(skull);
		}
	}
}
