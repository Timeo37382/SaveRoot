package net.mcreator.saferoot.entity.model;

import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.cache.GeckoLibCache;

import net.minecraft.resources.ResourceLocation;

import net.mcreator.saferoot.entity.ROOTEntity;

public class ROOTModel extends GeoModel<ROOTEntity> {
	// DefaultedEntityGeoModel-style paths (recommended GeckoLib 4 layout)
	private static final ResourceLocation MODEL_ENTITY = ResourceLocation.parse("saferoot:geo/entity/root_-_converted.geo.json");
	private static final ResourceLocation ANIM_ENTITY = ResourceLocation.parse("saferoot:animations/entity/root_-_converted.animation.json");
	// Flat legacy layout (older plugin imports)
	private static final ResourceLocation MODEL_FLAT = ResourceLocation.parse("saferoot:geo/root_-_converted.geo.json");
	private static final ResourceLocation ANIM_FLAT = ResourceLocation.parse("saferoot:animations/root_-_converted.animation.json");

	@Override
	public ResourceLocation getModelResource(ROOTEntity animatable) {
		if (GeckoLibCache.getBakedModels().containsKey(MODEL_ENTITY))
			return MODEL_ENTITY;
		return MODEL_FLAT;
	}

	@Override
	public ResourceLocation getTextureResource(ROOTEntity animatable) {
		// MCreator entity textures are imported to textures/entities/
		return ResourceLocation.parse("saferoot:textures/entities/" + animatable.getTexture() + ".png");
	}

	@Override
	public ResourceLocation getAnimationResource(ROOTEntity animatable) {
		if (GeckoLibCache.getBakedAnimations().containsKey(ANIM_ENTITY))
			return ANIM_ENTITY;
		return ANIM_FLAT;
	}
}