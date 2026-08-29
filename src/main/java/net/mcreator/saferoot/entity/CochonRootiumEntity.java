package net.mcreator.saferoot.entity;

import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;

import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.animal.Pig;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.server.level.ServerLevel;

import net.mcreator.saferoot.init.SaferootModEntities;

public class CochonRootiumEntity extends Pig {

	public CochonRootiumEntity(EntityType<CochonRootiumEntity> type, Level world) {
		super(type, world);
	}

	public static void init(RegisterSpawnPlacementsEvent event) {
		event.register(SaferootModEntities.COCHON_ROOTIUM.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
				(entityType, world, reason, pos, random) -> world.getBlockState(pos.below()).isSolidRender(world, pos.below()) && world.getRawBrightness(pos, 0) > 8,
				RegisterSpawnPlacementsEvent.Operation.REPLACE);
	}

	public static AttributeSupplier.Builder createAttributes() {
		return Pig.createAttributes();
	}

	@Override
	public CochonRootiumEntity getBreedOffspring(ServerLevel level, AgeableMob partner) {
		return SaferootModEntities.COCHON_ROOTIUM.get().create(level);
	}
}
