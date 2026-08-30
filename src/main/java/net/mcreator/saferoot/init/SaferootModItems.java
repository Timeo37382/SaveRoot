package net.mcreator.saferoot.init;

import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.item.*;

import net.mcreator.saferoot.item.*;
import net.mcreator.saferoot.SaferootMod;

public class SaferootModItems {
	public static final DeferredRegister.Items REGISTRY = DeferredRegister.createItems(SaferootMod.MODID);
	public static final DeferredItem<Item> ROOTIUM_INGOT;
	public static final DeferredItem<Item> RAW_ROOTIUM;
	public static final DeferredItem<Item> ROOTIUM_ORE;
	public static final DeferredItem<Item> ROOTIUM_ARMOR_HELMET;
	public static final DeferredItem<Item> ROOTIUM_ARMOR_CHESTPLATE;
	public static final DeferredItem<Item> ROOTIUM_ARMOR_LEGGINGS;
	public static final DeferredItem<Item> ROOTIUM_ARMOR_BOOTS;
	public static final DeferredItem<Item> ROOTIUM_PICKAXE;
	public static final DeferredItem<Item> ROOTIUM_AXE;
	public static final DeferredItem<Item> ROOTIUM_BLOCK;
	public static final DeferredItem<Item> ROOTIUM_HOE;
	public static final DeferredItem<Item> ROOTIUM_SWORD;
	public static final DeferredItem<Item> ROOTIUM_SHOVEL;
	public static final DeferredItem<Item> ROOT_SPAWN_EGG;
	public static final DeferredItem<Item> ROOTIUM_GRASS_BLOCK;
	public static final DeferredItem<Item> ROOTIUM_LEAVES;
	public static final DeferredItem<Item> ROOTIUM_FLOWER;
	public static final DeferredItem<Item> ROOTIUM_GRASS;
	public static final DeferredItem<Item> ROOTIUM_LOG;
	public static final DeferredItem<Item> ROOTIUM_WOOD;
	public static final DeferredItem<Item> STRIPPED_ROOTIUM_LOG;
	public static final DeferredItem<Item> STRIPPED_ROOTIUM_WOOD;
	public static final DeferredItem<Item> ROOTIUM_PLANKS;
	public static final DeferredItem<Item> ROOTIUM_STAIRS;
	public static final DeferredItem<Item> ROOTIUM_SLAB;
	public static final DeferredItem<Item> ROOTIUM_FENCE;
	public static final DeferredItem<Item> ROOTIUM_DOOR;
	public static final DeferredItem<Item> ROOTIUM_TRAPDOOR;
	public static final DeferredItem<Item> ROOTIUM_PRESSURE_PLATE;
	public static final DeferredItem<Item> ROOTIUM_BUTTON;
	public static final DeferredItem<Item> ROOTIUM_SIGN;
	public static final DeferredItem<Item> ROOTIUM_HANGING_SIGN;
	public static final DeferredItem<Item> ROOT_HEART;
	public static final DeferredItem<Item> WITHER_ROOT_SPAWN_EGG;
	public static final DeferredItem<Item> ROOT_COMPASS;
	public static final DeferredItem<Item> ROOTIUM_COW_SPAWN_EGG;
	public static final DeferredItem<Item> ROOTIUM_PIG_SPAWN_EGG;
	public static final DeferredItem<Item> ROOTIUM_CHICKEN_SPAWN_EGG;
	public static final DeferredItem<Item> ROOTIUM_SAPLING;
	public static final DeferredItem<Item> DEEPSLATE_ROOTIUM_ORE;
	public static final DeferredItem<Item> ROOTIUM_DIRT;
	static {
		ROOTIUM_INGOT = REGISTRY.register("rootium_ingot", LingotderootiumItem::new);
		RAW_ROOTIUM = REGISTRY.register("raw_rootium", RootiumBrutItem::new);
		ROOTIUM_ORE = block(SaferootModBlocks.ROOTIUM_ORE, new Item.Properties().rarity(Rarity.RARE).fireResistant());
		ROOTIUM_ARMOR_HELMET = REGISTRY.register("rootium_armor_helmet", ArmureEnRootiumItem.Helmet::new);
		ROOTIUM_ARMOR_CHESTPLATE = REGISTRY.register("rootium_armor_chestplate", ArmureEnRootiumItem.Chestplate::new);
		ROOTIUM_ARMOR_LEGGINGS = REGISTRY.register("rootium_armor_leggings", ArmureEnRootiumItem.Leggings::new);
		ROOTIUM_ARMOR_BOOTS = REGISTRY.register("rootium_armor_boots", ArmureEnRootiumItem.Boots::new);
		ROOTIUM_PICKAXE = REGISTRY.register("rootium_pickaxe", PiocheItem::new);
		ROOTIUM_AXE = REGISTRY.register("rootium_axe", HacheItem::new);
		ROOTIUM_BLOCK = block(SaferootModBlocks.ROOTIUM_BLOCK, new Item.Properties().rarity(Rarity.RARE));
		ROOTIUM_HOE = REGISTRY.register("rootium_hoe", HOUEItem::new);
		ROOTIUM_SWORD = REGISTRY.register("rootium_sword", EpeeItem::new);
		ROOTIUM_SHOVEL = REGISTRY.register("rootium_shovel", PelleItem::new);
		ROOT_SPAWN_EGG = REGISTRY.register("root_spawn_egg", () -> new DeferredSpawnEggItem(SaferootModEntities.ROOT, -39424, -26368, new Item.Properties()));
		ROOTIUM_GRASS_BLOCK = block(SaferootModBlocks.ROOTIUM_GRASS_BLOCK, new Item.Properties().rarity(Rarity.RARE));
		ROOTIUM_LEAVES = block(SaferootModBlocks.ROOTIUM_LEAVES, new Item.Properties().rarity(Rarity.RARE));
		ROOTIUM_FLOWER = block(SaferootModBlocks.ROOTIUM_FLOWER);
		ROOTIUM_GRASS = block(SaferootModBlocks.ROOTIUM_GRASS, new Item.Properties().rarity(Rarity.RARE));
		ROOTIUM_LOG = block(SaferootModBlocks.ROOTIUM_LOG);
		ROOTIUM_WOOD = block(SaferootModBlocks.ROOTIUM_WOOD);
		STRIPPED_ROOTIUM_LOG = block(SaferootModBlocks.STRIPPED_ROOTIUM_LOG);
		STRIPPED_ROOTIUM_WOOD = block(SaferootModBlocks.STRIPPED_ROOTIUM_WOOD);
		ROOTIUM_PLANKS = block(SaferootModBlocks.ROOTIUM_PLANKS);
		ROOTIUM_STAIRS = block(SaferootModBlocks.ROOTIUM_STAIRS);
		ROOTIUM_SLAB = block(SaferootModBlocks.ROOTIUM_SLAB);
		ROOTIUM_FENCE = block(SaferootModBlocks.ROOTIUM_FENCE);
		ROOTIUM_DOOR = doubleBlock(SaferootModBlocks.ROOTIUM_DOOR);
		ROOTIUM_TRAPDOOR = block(SaferootModBlocks.ROOTIUM_TRAPDOOR);
		ROOTIUM_PRESSURE_PLATE = block(SaferootModBlocks.ROOTIUM_PRESSURE_PLATE);
		ROOTIUM_BUTTON = block(SaferootModBlocks.ROOTIUM_BUTTON);
		ROOTIUM_SIGN = signBlock(SaferootModBlocks.ROOTIUM_SIGN, SaferootModBlocks.ROOTIUM_WALL_SIGN, new Item.Properties().stacksTo(16));
		ROOTIUM_HANGING_SIGN = hangingSignBlock(SaferootModBlocks.ROOTIUM_HANGING_SIGN, SaferootModBlocks.ROOTIUM_WALL_HANGING_SIGN, new Item.Properties().stacksTo(16));
		ROOT_HEART = block(SaferootModBlocks.ROOT_HEART, new Item.Properties().rarity(Rarity.EPIC).fireResistant());
		WITHER_ROOT_SPAWN_EGG = REGISTRY.register("wither_root_spawn_egg", () -> new DeferredSpawnEggItem(SaferootModEntities.WITHER_ROOT, -14410730, -1936102, new Item.Properties()));
		ROOT_COMPASS = REGISTRY.register("root_compass", BoussoleRootItem::new);
		ROOTIUM_COW_SPAWN_EGG = REGISTRY.register("rootium_cow_spawn_egg", () -> new DeferredSpawnEggItem(SaferootModEntities.ROOTIUM_COW, -1545712, -15253, new Item.Properties()));
		ROOTIUM_PIG_SPAWN_EGG = REGISTRY.register("rootium_pig_spawn_egg", () -> new DeferredSpawnEggItem(SaferootModEntities.ROOTIUM_PIG, -27561, -11600, new Item.Properties()));
		ROOTIUM_CHICKEN_SPAWN_EGG = REGISTRY.register("rootium_chicken_spawn_egg", () -> new DeferredSpawnEggItem(SaferootModEntities.ROOTIUM_CHICKEN, -20192, -5720, new Item.Properties()));
		ROOTIUM_SAPLING = block(SaferootModBlocks.ROOTIUM_SAPLING);
		DEEPSLATE_ROOTIUM_ORE = block(SaferootModBlocks.DEEPSLATE_ROOTIUM_ORE, new Item.Properties().rarity(Rarity.RARE).fireResistant());
		ROOTIUM_DIRT = block(SaferootModBlocks.ROOTIUM_DIRT);
	}

	// Start of user code block custom items
	// End of user code block custom items
	private static DeferredItem<Item> block(DeferredHolder<Block, Block> block) {
		return block(block, new Item.Properties());
	}

	private static DeferredItem<Item> block(DeferredHolder<Block, Block> block, Item.Properties properties) {
		return REGISTRY.register(block.getId().getPath(), () -> new BlockItem(block.get(), properties));
	}

	private static DeferredItem<Item> doubleBlock(DeferredHolder<Block, Block> block) {
		return doubleBlock(block, new Item.Properties());
	}

	private static DeferredItem<Item> doubleBlock(DeferredHolder<Block, Block> block, Item.Properties properties) {
		return REGISTRY.register(block.getId().getPath(), () -> new DoubleHighBlockItem(block.get(), properties));
	}

	private static DeferredItem<Item> signBlock(DeferredHolder<Block, Block> block, DeferredHolder<Block, Block> wallBlock) {
		return signBlock(block, wallBlock, new Item.Properties());
	}

	private static DeferredItem<Item> signBlock(DeferredHolder<Block, Block> block, DeferredHolder<Block, Block> wallBlock, Item.Properties properties) {
		return REGISTRY.register(block.getId().getPath(), () -> new SignItem(properties, block.get(), wallBlock.get()));
	}

	private static DeferredItem<Item> hangingSignBlock(DeferredHolder<Block, Block> block, DeferredHolder<Block, Block> wallBlock) {
		return hangingSignBlock(block, wallBlock, new Item.Properties());
	}

	private static DeferredItem<Item> hangingSignBlock(DeferredHolder<Block, Block> block, DeferredHolder<Block, Block> wallBlock, Item.Properties properties) {
		return REGISTRY.register(block.getId().getPath(), () -> new HangingSignItem(block.get(), wallBlock.get(), properties));
	}
}