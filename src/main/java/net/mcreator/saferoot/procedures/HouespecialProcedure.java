package net.mcreator.saferoot.procedures;

import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.core.BlockPos;

import net.mcreator.saferoot.init.SaferootModItems;

@EventBusSubscriber
public class HouespecialProcedure {

	public static void execute() {
	}

	@SubscribeEvent
	public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
		Player player = event.getEntity();
		ItemStack itemstack = event.getItemStack();

		if (!itemstack.is(SaferootModItems.ROOTIUM_HOE.get())) return;

		Level world = event.getLevel();
		BlockPos center = event.getPos();
		BlockState state = world.getBlockState(center);

		if (!state.is(Blocks.GRASS_BLOCK) && !state.is(Blocks.DIRT) && !state.is(Blocks.DIRT_PATH)) return;

		for (int x = -1; x <= 1; x++) {
			for (int z = -1; z <= 1; z++) {
				BlockPos pos = center.offset(x, 0, z);
				BlockPos abovePos = pos.above();
				BlockState targetState = world.getBlockState(pos);
				BlockState aboveState = world.getBlockState(abovePos);

				if ((targetState.is(Blocks.GRASS_BLOCK) || targetState.is(Blocks.DIRT) || targetState.is(Blocks.DIRT_PATH)) && aboveState.isAir()) {
					world.setBlock(pos, Blocks.FARMLAND.defaultBlockState(), 3);

					ItemStack seed = findSeeds(player);
					if (!seed.isEmpty()) {
						BlockState cropState = getCropState(seed);
						if (cropState != null) {
							world.setBlock(abovePos, cropState, 3);
							if (!player.isCreative()) {
								seed.shrink(1);
							}
						}
					}

					itemstack.hurtAndBreak(1, player, EquipmentSlot.MAINHAND);
				}
			}
		}
	}

	private static ItemStack findSeeds(Player player) {
		for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
			ItemStack stack = player.getInventory().getItem(i);
			if (stack.is(Items.WHEAT_SEEDS) || stack.is(Items.CARROT) || stack.is(Items.POTATO) 
					|| stack.is(Items.BEETROOT_SEEDS) || stack.is(Items.PUMPKIN_SEEDS) || stack.is(Items.MELON_SEEDS)) {
				return stack;
			}
		}
		return ItemStack.EMPTY;
	}

	private static BlockState getCropState(ItemStack seed) {
		if (seed.is(Items.WHEAT_SEEDS)) return Blocks.WHEAT.defaultBlockState();
		if (seed.is(Items.CARROT)) return Blocks.CARROTS.defaultBlockState();
		if (seed.is(Items.POTATO)) return Blocks.POTATOES.defaultBlockState();
		if (seed.is(Items.BEETROOT_SEEDS)) return Blocks.BEETROOTS.defaultBlockState();
		if (seed.is(Items.PUMPKIN_SEEDS)) return Blocks.PUMPKIN_STEM.defaultBlockState();
		if (seed.is(Items.MELON_SEEDS)) return Blocks.MELON_STEM.defaultBlockState();
		return null;
	}
}