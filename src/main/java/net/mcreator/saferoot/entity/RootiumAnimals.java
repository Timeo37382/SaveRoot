package net.mcreator.saferoot.entity;

import java.lang.reflect.Field;

import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;

import net.mcreator.saferoot.init.SaferootModBlocks;
import net.mcreator.saferoot.init.SaferootModEntities;

/**
 * Regles communes aux trois animaux de Rootia.
 *
 * Le generateur MCreator declare tous les "livingentity" en MobCategory.MONSTER : la
 * categorie n'est pas exposee pour ce type d'element et SaferootModEntities est
 * regenere a chaque build, donc impossible de la corriger a la source. On la force en
 * CREATURE pendant le chargement du mod, avant que les biomes ne soient assembles, ce
 * qui suffit pour que tout le reste du jeu les traite comme des animaux passifs :
 * spawn passif, plafond de population des creatures, pas de despawn agressif.
 */
@EventBusSubscriber
public class RootiumAnimals {

	private static final int MIN_LIGHT = 8;

	private RootiumAnimals() {
	}

	@SubscribeEvent
	public static void fixSpawnCategories(FMLCommonSetupEvent event) {
		event.enqueueWork(() -> {
			forceCreature(SaferootModEntities.ROOTIUM_COW.get());
			forceCreature(SaferootModEntities.ROOTIUM_PIG.get());
			forceCreature(SaferootModEntities.ROOTIUM_CHICKEN.get());
		});
	}

	private static void forceCreature(EntityType<?> type) {
		if (type == null || type.getCategory() == MobCategory.CREATURE)
			return;
		try {
			Field field = EntityType.class.getDeclaredField("category");
			field.setAccessible(true);
			field.set(type, MobCategory.CREATURE);
		} catch (ReflectiveOperationException | RuntimeException ignored) {
			// Categorie inchangee : les animaux restent des "monstres" pour le spawn,
			// ce qui est le comportement d'avant. On ne plante pas pour autant.
		}
	}

	/**
	 * Meme regle que Animal.checkAnimalSpawnRules, avec le sol de Rootia en plus :
	 * un bloc ou les animaux peuvent apparaitre, et assez de lumiere.
	 */
	public static boolean canSpawnHere(ServerLevelAccessor world, BlockPos pos) {
		BlockPos below = pos.below();
		BlockState ground = world.getBlockState(below);
		boolean spawnable = ground.is(BlockTags.ANIMALS_SPAWNABLE_ON) || ground.is(SaferootModBlocks.ROOTIUM_GRASS_BLOCK.get());
		return spawnable && world.getRawBrightness(pos, 0) > MIN_LIGHT;
	}
}
