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
	public static final DeferredItem<Item> HOUE;
	public static final DeferredItem<Item> EPEE;
	public static final DeferredItem<Item> PELLE;
	public static final DeferredItem<Item> ROOT_SPAWN_EGG;
	public static final DeferredItem<Item> HERBEENROOTIUM;
	public static final DeferredItem<Item> FEUILLAGEROOTIUM;
	public static final DeferredItem<Item> ROOTIA;
	public static final DeferredItem<Item> FLEUR_EN_ROOTIUM;
	public static final DeferredItem<Item> HEBRE;
	public static final DeferredItem<Item> BOIS_EN_ROOTIUM_LOG;
	public static final DeferredItem<Item> BOIS_EN_ROOTIUM_WOOD;
	public static final DeferredItem<Item> STRIPPED_BOIS_EN_ROOTIUM_LOG;
	public static final DeferredItem<Item> STRIPPED_BOIS_EN_ROOTIUM_WOOD;
	public static final DeferredItem<Item> BOIS_EN_ROOTIUM_PLANKS;
	public static final DeferredItem<Item> BOIS_EN_ROOTIUM_STAIRS;
	public static final DeferredItem<Item> BOIS_EN_ROOTIUM_SLAB;
	public static final DeferredItem<Item> BOIS_EN_ROOTIUM_FENCE;
	public static final DeferredItem<Item> BOIS_EN_ROOTIUM_FENCE_GATE;
	public static final DeferredItem<Item> BOIS_EN_ROOTIUM_DOOR;
	public static final DeferredItem<Item> BOIS_EN_ROOTIUM_TRAPDOOR;
	public static final DeferredItem<Item> BOIS_EN_ROOTIUM_PRESSURE_PLATE;
	public static final DeferredItem<Item> BOIS_EN_ROOTIUM_BUTTON;
	public static final DeferredItem<Item> BOIS_EN_ROOTIUM_SIGN;
	public static final DeferredItem<Item> BOIS_EN_ROOTIUM_HANGING_SIGN;
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
		HOUE = REGISTRY.register("houe", HOUEItem::new);
		EPEE = REGISTRY.register("epee", EpeeItem::new);
		PELLE = REGISTRY.register("pelle", PelleItem::new);
		ROOT_SPAWN_EGG = REGISTRY.register("root_spawn_egg", () -> new DeferredSpawnEggItem(SaferootModEntities.ROOT, -39424, -26368, new Item.Properties()));
		HERBEENROOTIUM = block(SaferootModBlocks.HERBEENROOTIUM, new Item.Properties().rarity(Rarity.RARE));
		FEUILLAGEROOTIUM = block(SaferootModBlocks.FEUILLAGEROOTIUM, new Item.Properties().rarity(Rarity.RARE));
		ROOTIA = REGISTRY.register("rootia", RootiaItem::new);
		FLEUR_EN_ROOTIUM = block(SaferootModBlocks.FLEUR_EN_ROOTIUM);
		HEBRE = block(SaferootModBlocks.HEBRE, new Item.Properties().rarity(Rarity.RARE));
		BOIS_EN_ROOTIUM_LOG = block(SaferootModBlocks.BOIS_EN_ROOTIUM_LOG);
		BOIS_EN_ROOTIUM_WOOD = block(SaferootModBlocks.BOIS_EN_ROOTIUM_WOOD);
		STRIPPED_BOIS_EN_ROOTIUM_LOG = block(SaferootModBlocks.STRIPPED_BOIS_EN_ROOTIUM_LOG);
		STRIPPED_BOIS_EN_ROOTIUM_WOOD = block(SaferootModBlocks.STRIPPED_BOIS_EN_ROOTIUM_WOOD);
		BOIS_EN_ROOTIUM_PLANKS = block(SaferootModBlocks.BOIS_EN_ROOTIUM_PLANKS);
		BOIS_EN_ROOTIUM_STAIRS = block(SaferootModBlocks.BOIS_EN_ROOTIUM_STAIRS);
		BOIS_EN_ROOTIUM_SLAB = block(SaferootModBlocks.BOIS_EN_ROOTIUM_SLAB);
		BOIS_EN_ROOTIUM_FENCE = block(SaferootModBlocks.BOIS_EN_ROOTIUM_FENCE);
		BOIS_EN_ROOTIUM_FENCE_GATE = block(SaferootModBlocks.BOIS_EN_ROOTIUM_FENCE_GATE);
		BOIS_EN_ROOTIUM_DOOR = doubleBlock(SaferootModBlocks.BOIS_EN_ROOTIUM_DOOR);
		BOIS_EN_ROOTIUM_TRAPDOOR = block(SaferootModBlocks.BOIS_EN_ROOTIUM_TRAPDOOR);
		BOIS_EN_ROOTIUM_PRESSURE_PLATE = block(SaferootModBlocks.BOIS_EN_ROOTIUM_PRESSURE_PLATE);
		BOIS_EN_ROOTIUM_BUTTON = block(SaferootModBlocks.BOIS_EN_ROOTIUM_BUTTON);
		BOIS_EN_ROOTIUM_SIGN = signBlock(SaferootModBlocks.BOIS_EN_ROOTIUM_SIGN, SaferootModBlocks.BOIS_EN_ROOTIUM_WALL_SIGN, new Item.Properties().stacksTo(16));
		BOIS_EN_ROOTIUM_HANGING_SIGN = hangingSignBlock(SaferootModBlocks.BOIS_EN_ROOTIUM_HANGING_SIGN, SaferootModBlocks.BOIS_EN_ROOTIUM_WALL_HANGING_SIGN, new Item.Properties().stacksTo(16));
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