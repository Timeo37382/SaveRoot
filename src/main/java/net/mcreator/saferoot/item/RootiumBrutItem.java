package net.mcreator.saferoot.item;

import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Item;

public class RootiumBrutItem extends Item {
	public RootiumBrutItem() {
		super(new Item.Properties().fireResistant().rarity(Rarity.RARE));
	}
}