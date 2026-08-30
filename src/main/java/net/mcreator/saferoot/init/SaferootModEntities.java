package net.mcreator.saferoot.init;

import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;

import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Entity;
import net.minecraft.core.registries.Registries;

import net.mcreator.saferoot.entity.WhitherRootEntity;
import net.mcreator.saferoot.entity.VacheEnRootiumEntity;
import net.mcreator.saferoot.entity.ROOTEntity;
import net.mcreator.saferoot.entity.PouleRootiumEntity;
import net.mcreator.saferoot.entity.CochonRootiumEntity;
import net.mcreator.saferoot.SaferootMod;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public class SaferootModEntities {
	public static final DeferredRegister<EntityType<?>> REGISTRY = DeferredRegister.create(Registries.ENTITY_TYPE, SaferootMod.MODID);
	public static final DeferredHolder<EntityType<?>, EntityType<ROOTEntity>> ROOT = register("root",
			EntityType.Builder.<ROOTEntity>of(ROOTEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3)

					.sized(0.6f, 1.7f)

	);
	public static final DeferredHolder<EntityType<?>, EntityType<WhitherRootEntity>> WITHER_ROOT = register("wither_root",
			EntityType.Builder.<WhitherRootEntity>of(WhitherRootEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3)

					.sized(0.6f, 1.8f)

	);
	public static final DeferredHolder<EntityType<?>, EntityType<VacheEnRootiumEntity>> ROOTIUM_COW = register("rootium_cow",
			EntityType.Builder.<VacheEnRootiumEntity>of(VacheEnRootiumEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3)

					.sized(0.9f, 1.4f)

	);
	public static final DeferredHolder<EntityType<?>, EntityType<CochonRootiumEntity>> ROOTIUM_PIG = register("rootium_pig",
			EntityType.Builder.<CochonRootiumEntity>of(CochonRootiumEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3)

					.ridingOffset(-0.6f).sized(0.8f, 0.9f)

	);
	public static final DeferredHolder<EntityType<?>, EntityType<PouleRootiumEntity>> ROOTIUM_CHICKEN = register("rootium_chicken",
			EntityType.Builder.<PouleRootiumEntity>of(PouleRootiumEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3)

					.ridingOffset(-0.6f).sized(0.4f, 0.7f)

	);

	// Start of user code block custom entities
	// End of user code block custom entities
	private static <T extends Entity> DeferredHolder<EntityType<?>, EntityType<T>> register(String registryname, EntityType.Builder<T> entityTypeBuilder) {
		return REGISTRY.register(registryname, () -> (EntityType<T>) entityTypeBuilder.build(registryname));
	}

	@SubscribeEvent
	public static void init(RegisterSpawnPlacementsEvent event) {
		ROOTEntity.init(event);
		WhitherRootEntity.init(event);
		VacheEnRootiumEntity.init(event);
		CochonRootiumEntity.init(event);
		PouleRootiumEntity.init(event);
	}

	@SubscribeEvent
	public static void registerAttributes(EntityAttributeCreationEvent event) {
		event.put(ROOT.get(), ROOTEntity.createAttributes().build());
		event.put(WITHER_ROOT.get(), WhitherRootEntity.createAttributes().build());
		event.put(ROOTIUM_COW.get(), VacheEnRootiumEntity.createAttributes().build());
		event.put(ROOTIUM_PIG.get(), CochonRootiumEntity.createAttributes().build());
		event.put(ROOTIUM_CHICKEN.get(), PouleRootiumEntity.createAttributes().build());
	}
}