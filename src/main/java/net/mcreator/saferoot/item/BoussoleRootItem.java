package net.mcreator.saferoot.item;

import java.util.Optional;

import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.api.distmarker.Dist;

import net.minecraft.client.renderer.item.CompassItemPropertyFunction;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.component.LodestoneTracker;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.structure.Structure;

import net.mcreator.saferoot.init.SaferootModItems;

public class BoussoleRootItem extends Item {

	private static final ResourceKey<Level> ROOTIA = ResourceKey.create(Registries.DIMENSION, ResourceLocation.fromNamespaceAndPath("saferoot", "rootia"));

	private static final TagKey<Structure> ALTARS = TagKey.create(Registries.STRUCTURE, ResourceLocation.fromNamespaceAndPath("saferoot", "spawnboss"));

	private static final int SEARCH_RADIUS = 80;
	private static final int LOCKED_INTERVAL = 200;
	private static final int SEEKING_INTERVAL = 20;

	public BoussoleRootItem() {
		super(new Item.Properties().stacksTo(1).rarity(Rarity.RARE));
	}

	@Override
	public void inventoryTick(ItemStack stack, Level level, Entity entity, int slot, boolean selected) {
		if (!(level instanceof ServerLevel serverLevel) || !(entity instanceof Player player))
			return;
		if (!selected && player.getOffhandItem() != stack)
			return;

		LodestoneTracker tracker = stack.get(DataComponents.LODESTONE_TRACKER);
		int interval = tracker != null && tracker.target().isPresent() ? LOCKED_INTERVAL : SEEKING_INTERVAL;
		if ((serverLevel.getGameTime() + player.getId()) % interval != 0)
			return;

		if (!level.dimension().equals(ROOTIA)) {
			stack.remove(DataComponents.LODESTONE_TRACKER);
			return;
		}

		BlockPos altar = serverLevel.findNearestMapStructure(ALTARS, player.blockPosition(), SEARCH_RADIUS, false);
		if (altar == null) {
			stack.remove(DataComponents.LODESTONE_TRACKER);
			return;
		}

		stack.set(DataComponents.LODESTONE_TRACKER, new LodestoneTracker(Optional.of(GlobalPos.of(ROOTIA, altar)), false));
	}

	@EventBusSubscriber(Dist.CLIENT)
	public static class Needle {

		@SubscribeEvent
		public static void register(FMLClientSetupEvent event) {
			event.enqueueWork(() -> ItemProperties.register(SaferootModItems.BOUSSOLE_ROOT.get(), ResourceLocation.withDefaultNamespace("angle"),
					new CompassItemPropertyFunction((world, stack, holder) -> {
						LodestoneTracker tracker = stack.get(DataComponents.LODESTONE_TRACKER);
						return tracker != null ? tracker.target().orElse(null) : null;
					})));
		}
	}
}
