package net.mcreator.saferoot.procedures;

import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.bus.api.Event;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.MenuProvider;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.BlockPos;

import net.mcreator.saferoot.world.inventory.LoreguiMenu;

import javax.annotation.Nullable;

import io.netty.buffer.Unpooled;

@EventBusSubscriber
public class OuvertureloreProcedure {
	/** Marqueur persistant : le prologue n'est joue qu'a la toute premiere connexion. */
	private static final String SEEN_TAG = "saferoot_prologue_seen";

	@SubscribeEvent
	public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
		if (!(event.getEntity() instanceof ServerPlayer player))
			return;
		if (hasSeenPrologue(player))
			return;
		markPrologueSeen(player);
		execute(event, player.level(), player.getX(), player.getY(), player.getZ(), player);
	}

	public static boolean hasSeenPrologue(Player player) {
		return player.getPersistentData().getCompound(Player.PERSISTED_NBT_TAG).getBoolean(SEEN_TAG);
	}

	public static void markPrologueSeen(Player player) {
		CompoundTag root = player.getPersistentData();
		CompoundTag persisted = root.getCompound(Player.PERSISTED_NBT_TAG);
		persisted.putBoolean(SEEN_TAG, true);
		root.put(Player.PERSISTED_NBT_TAG, persisted);
	}

	/** Remet le marqueur a zero : la prochaine connexion rejouera le prologue. */
	public static void resetPrologue(Player player) {
		CompoundTag root = player.getPersistentData();
		CompoundTag persisted = root.getCompound(Player.PERSISTED_NBT_TAG);
		persisted.remove(SEEN_TAG);
		root.put(Player.PERSISTED_NBT_TAG, persisted);
	}

	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		execute(null, world, x, y, z, entity);
	}

	private static void execute(@Nullable Event event, LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		if (entity instanceof Player _player)
			_player.closeContainer();
		if (entity instanceof ServerPlayer _ent) {
			BlockPos _bpos = BlockPos.containing(x, y, z);
			_ent.openMenu(new MenuProvider() {
				@Override
				public Component getDisplayName() {
					return Component.literal("Loregui");
				}

				@Override
				public boolean shouldTriggerClientSideContainerClosingOnOpen() {
					return false;
				}

				@Override
				public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
					return new LoreguiMenu(id, inventory, new FriendlyByteBuf(Unpooled.buffer()).writeBlockPos(_bpos));
				}
			}, _bpos);
		}
	}
}