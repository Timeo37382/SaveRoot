/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.saferoot.init;

import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredHolder;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.BlockItem;

import net.mcreator.saferoot.item.*;
import net.mcreator.saferoot.SaferootMod;

public class SaferootModItems {
	public static final DeferredRegister.Items REGISTRY = DeferredRegister.createItems(SaferootMod.MODID);
	public static final DeferredItem<Item> LINGOTDEROOTIUM;
	public static final DeferredItem<Item> ROOTIUM_BRUT;
	public static final DeferredItem<Item> ROOTIUM;
	public static final DeferredItem<Item> ARMURE_EN_ROOTIUM_HELMET;
	public static final DeferredItem<Item> ARMURE_EN_ROOTIUM_CHESTPLATE;
	public static final DeferredItem<Item> ARMURE_EN_ROOTIUM_LEGGINGS;
	public static final DeferredItem<Item> ARMURE_EN_ROOTIUM_BOOTS;
	public static final DeferredItem<Item> PIOCHE;
	public static final DeferredItem<Item> HACHE;
	public static final DeferredItem<Item> BLOC_DE_ROOTIUM;
	public static final DeferredItem<Item> ROOTIA_DIM;
	static {
		LINGOTDEROOTIUM = REGISTRY.register("lingotderootium", LingotderootiumItem::new);
		ROOTIUM_BRUT = REGISTRY.register("rootium_brut", RootiumBrutItem::new);
		ROOTIUM = block(SaferootModBlocks.ROOTIUM, new Item.Properties().rarity(Rarity.RARE).fireResistant());
		ARMURE_EN_ROOTIUM_HELMET = REGISTRY.register("armure_en_rootium_helmet", ArmureEnRootiumItem.Helmet::new);
		ARMURE_EN_ROOTIUM_CHESTPLATE = REGISTRY.register("armure_en_rootium_chestplate", ArmureEnRootiumItem.Chestplate::new);
		ARMURE_EN_ROOTIUM_LEGGINGS = REGISTRY.register("armure_en_rootium_leggings", ArmureEnRootiumItem.Leggings::new);
		ARMURE_EN_ROOTIUM_BOOTS = REGISTRY.register("armure_en_rootium_boots", ArmureEnRootiumItem.Boots::new);
		PIOCHE = REGISTRY.register("pioche", PiocheItem::new);
		HACHE = REGISTRY.register("hache", HacheItem::new);
		BLOC_DE_ROOTIUM = block(SaferootModBlocks.BLOC_DE_ROOTIUM, new Item.Properties().rarity(Rarity.RARE));
		ROOTIA_DIM = REGISTRY.register("rootia_dim", RootiaDIMItem::new);
	}

	// Start of user code block custom items
	// End of user code block custom items
	private static DeferredItem<Item> block(DeferredHolder<Block, Block> block) {
		return block(block, new Item.Properties());
	}

	private static DeferredItem<Item> block(DeferredHolder<Block, Block> block, Item.Properties properties) {
		return REGISTRY.register(block.getId().getPath(), () -> new BlockItem(block.get(), properties));
	}
}