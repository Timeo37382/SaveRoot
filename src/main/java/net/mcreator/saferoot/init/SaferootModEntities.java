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
	public static final DeferredHolder<EntityType<?>, EntityType<WhitherRootEntity>> WHITHER_ROOT = register("whither_root",
			EntityType.Builder.<WhitherRootEntity>of(WhitherRootEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3)

					.sized(0.6f, 1.8f)

	);
	public static final DeferredHolder<EntityType<?>, EntityType<VacheEnRootiumEntity>> VACHE_EN_ROOTIUM = register("vache_en_rootium",
			EntityType.Builder.<VacheEnRootiumEntity>of(VacheEnRootiumEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3)

					.sized(0.9f, 1.4f)

	);
	public static final DeferredHolder<EntityType<?>, EntityType<CochonRootiumEntity>> COCHON_ROOTIUM = register("cochon_rootium",
			EntityType.Builder.<CochonRootiumEntity>of(CochonRootiumEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3)

					.ridingOffset(-0.6f).sized(0.8f, 0.9f)

	);
	public static final DeferredHolder<EntityType<?>, EntityType<PouleRootiumEntity>> POULE_ROOTIUM = register("poule_rootium",
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
		event.put(WHITHER_ROOT.get(), WhitherRootEntity.createAttributes().build());
		event.put(VACHE_EN_ROOTIUM.get(), VacheEnRootiumEntity.createAttributes().build());
		event.put(COCHON_ROOTIUM.get(), CochonRootiumEntity.createAttributes().build());
		event.put(POULE_ROOTIUM.get(), PouleRootiumEntity.createAttributes().build());
	}
}