package net.mcreator.saferoot.world.inventory;

import net.minecraft.world.level.Level;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.Container;
import net.minecraft.network.FriendlyByteBuf;

import net.mcreator.saferoot.init.SaferootModMenus;
import net.mcreator.saferoot.entity.ROOTEntity;

import java.util.Map;
import java.util.HashMap;
import java.util.Collections;

public class RootGolemGuiMenu extends AbstractContainerMenu implements SaferootModMenus.MenuAccessor {
	public static final int TOOL_SLOTS = 3;
	private static final int[] TOOL_X = {24, 80, 136};
	private static final int TOOL_Y = 19;
	private static final int STORAGE_SLOTS = 6, STORAGE_X = 35, STORAGE_Y = 77;
	private static final int INV_X = 9, INV_Y = 111, HOTBAR_Y = 171;

	public final Map<String, Object> menuState = new HashMap<>();
	private final Map<Integer, Slot> customSlots = new HashMap<>();

	public final Level world;
	public final Player entity;
	public int x, y, z;

	private final ROOTEntity golem;
	private final Container tools;
	private final Container storage;

	public RootGolemGuiMenu(int id, Inventory inv, FriendlyByteBuf extraData) {
		this(id, inv, extraData == null ? null : findGolem(inv, extraData.readVarInt()));
	}

	public RootGolemGuiMenu(int id, Inventory inv, ROOTEntity golem) {
		super(SaferootModMenus.ROOT_GOLEM_GUI.get(), id);
		this.entity = inv.player;
		this.world = inv.player.level();
		this.golem = golem;
		this.tools = golem != null ? golem.getTools() : new SimpleContainer(TOOL_SLOTS);
		this.storage = golem != null ? golem.getStorage() : new SimpleContainer(STORAGE_SLOTS);
		if (golem != null) {
			this.x = golem.getBlockX();
			this.y = golem.getBlockY();
			this.z = golem.getBlockZ();
		}

		for (int i = 0; i < TOOL_SLOTS; i++) {
			Slot slot = addSlot(new ToolSlot(this.tools, i, TOOL_X[i], TOOL_Y));
			customSlots.put(i, slot);
		}
		for (int i = 0; i < STORAGE_SLOTS; i++)
			addSlot(new Slot(this.storage, i, STORAGE_X + i * 18, STORAGE_Y));
		for (int row = 0; row < 3; row++)
			for (int col = 0; col < 9; col++)
				addSlot(new Slot(inv, col + row * 9 + 9, INV_X + col * 18, INV_Y + row * 18));
		for (int col = 0; col < 9; col++)
			addSlot(new Slot(inv, col, INV_X + col * 18, HOTBAR_Y));
	}

	private static ROOTEntity findGolem(Inventory inv, int entityId) {
		return inv.player.level().getEntity(entityId) instanceof ROOTEntity found ? found : null;
	}

	public ROOTEntity getGolem() {
		return this.golem;
	}

	public boolean hasTool(int index) {
		return index >= 0 && index < TOOL_SLOTS && !this.tools.getItem(index).isEmpty();
	}

	@Override
	public boolean stillValid(Player player) {
		if (this.golem == null || !this.golem.isAlive() || this.golem.distanceToSqr(player) >= 64.0)
			return false;
		this.golem.keepBusy();
		return true;
	}

	@Override
	public ItemStack quickMoveStack(Player player, int index) {
		Slot slot = this.slots.get(index);
		if (slot == null || !slot.hasItem())
			return ItemStack.EMPTY;
		ItemStack stack = slot.getItem();
		ItemStack original = stack.copy();
		int golemEnd = TOOL_SLOTS + STORAGE_SLOTS;
		if (index < golemEnd) {
			if (!moveItemStackTo(stack, golemEnd, this.slots.size(), true))
				return ItemStack.EMPTY;
		} else if (!moveItemStackTo(stack, 0, golemEnd, false)) {
			return ItemStack.EMPTY;
		}
		if (stack.isEmpty())
			slot.set(ItemStack.EMPTY);
		else
			slot.setChanged();
		return original;
	}

	@Override
	public void removed(Player player) {
		super.removed(player);
		this.tools.setChanged();
		this.storage.setChanged();
	}

	@Override
	public Map<Integer, Slot> getSlots() {
		return Collections.unmodifiableMap(customSlots);
	}

	@Override
	public Map<String, Object> getMenuState() {
		return menuState;
	}

	private static class ToolSlot extends Slot {
		private final int toolIndex;

		ToolSlot(Container container, int index, int x, int y) {
			super(container, index, x, y);
			this.toolIndex = index;
		}

		@Override
		public boolean mayPlace(ItemStack stack) {
			return ROOTEntity.isToolForSlot(this.toolIndex, stack);
		}

		@Override
		public int getMaxStackSize() {
			return 1;
		}
	}
}
