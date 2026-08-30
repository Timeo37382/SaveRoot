package net.mcreator.saferoot.entity;

import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;

import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.animal.Cow;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.server.level.ServerLevel;

import net.mcreator.saferoot.init.SaferootModEntities;

public class VacheEnRootiumEntity extends Cow {

	public VacheEnRootiumEntity(EntityType<VacheEnRootiumEntity> type, Level world) {
		super(type, world);
	}

	public static void init(RegisterSpawnPlacementsEvent event) {
		event.register(SaferootModEntities.ROOTIUM_COW.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
				(entityType, world, reason, pos, random) -> RootiumAnimals.canSpawnHere(world, pos), RegisterSpawnPlacementsEvent.Operation.REPLACE);
	}

	public static AttributeSupplier.Builder createAttributes() {
		return Cow.createAttributes();
	}

	@Override
	public VacheEnRootiumEntity getBreedOffspring(ServerLevel level, AgeableMob partner) {
		return SaferootModEntities.ROOTIUM_COW.get().create(level);
	}
}
