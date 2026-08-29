package net.mcreator.saferoot.client.gui;

import net.neoforged.neoforge.network.PacketDistributor;

import net.minecraft.world.item.ItemStack;
import net.minecraft.network.chat.Component;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.AbstractSliderButton;
import net.minecraft.client.gui.GuiGraphics;

import net.mcreator.saferoot.entity.ROOTEntity;
import net.mcreator.saferoot.RootGolemNetwork;

import java.util.List;
import java.util.ArrayList;

public class RootMissionScreen extends Screen {
	private static final int PANEL_W = 176, GRID_COLS = 6, GRID_X = 34, GRID_Y = 22;
	private static final int BORDER = 0xFF000000, LIGHT = 0xFFFFFFFF, FILL = 0xFFC6C6C6, SHADE = 0xFF555555;
	private static final int CELL = 0xFF8B8B8B, CELL_SELECTED = 0xFFC86E0A, TEXT = 0xFF3F3F3F;

	private final Screen parent;
	private final int mode;
	private final List<Integer> available = new ArrayList<>();

	private int selected = 0;
	private int quantity = 64;
	private int left, top, panelHeight, infoY, sliderY, buttonY;

	public RootMissionScreen(Screen parent, int mode) {
		super(Component.literal(title(mode)));
		this.parent = parent;
		this.mode = mode;
	}

	private static String title(int mode) {
		return switch (mode) {
			case ROOTEntity.MODE_MINER -> "Miner";
			case ROOTEntity.MODE_ATTAQUER -> "Attaquer";
			default -> "Bûcher";
		};
	}

	private boolean picksOre() {
		return this.mode == ROOTEntity.MODE_MINER;
	}

	private int oreIndex() {
		return available.isEmpty() ? 0 : available.get(Math.min(selected, available.size() - 1));
	}

	@Override
	protected void init() {
		available.clear();
		if (picksOre() && this.minecraft != null && this.minecraft.level != null) {
			for (int i = 0; i < ROOTEntity.ORES.size(); i++)
				if (ROOTEntity.ORES.get(i).availableIn(this.minecraft.level))
					available.add(i);
		}

		int rows = picksOre() ? Math.max(1, (available.size() + GRID_COLS - 1) / GRID_COLS) : 0;
		this.infoY = picksOre() ? GRID_Y + rows * 18 + 8 : 26;
		this.sliderY = this.infoY + 52;
		this.buttonY = this.sliderY + 26;
		this.panelHeight = this.buttonY + 28;
		this.left = (this.width - PANEL_W) / 2;
		this.top = (this.height - this.panelHeight) / 2;

		this.addRenderableWidget(new AbstractSliderButton(left + 8, top + sliderY, 160, 20, Component.empty(), (quantity - 1) / 63.0) {
			{
				updateMessage();
			}

			@Override
			protected void updateMessage() {
				setMessage(Component.literal("Quantité : " + RootMissionScreen.this.quantity));
			}

			@Override
			protected void applyValue() {
				RootMissionScreen.this.quantity = 1 + (int) Math.round(this.value * 63);
			}
		});

		boolean possible = !picksOre() || !available.isEmpty();
		Button launch = Button.builder(Component.literal("Lancer"), b -> {
			PacketDistributor.sendToServer(new RootGolemNetwork(this.mode, oreIndex(), this.quantity));
			if (this.minecraft != null && this.minecraft.player != null)
				this.minecraft.player.closeContainer();
			this.minecraft.setScreen(null);
		}).bounds(left + 8, top + buttonY, 76, 20).build();
		launch.active = possible;
		this.addRenderableWidget(launch);
		this.addRenderableWidget(Button.builder(Component.literal("Retour"), b -> this.onClose())
				.bounds(left + 92, top + buttonY, 76, 20).build());
	}

	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int button) {
		if (picksOre()) {
			for (int i = 0; i < available.size(); i++) {
				int cx = left + GRID_X + (i % GRID_COLS) * 18;
				int cy = top + GRID_Y + (i / GRID_COLS) * 18;
				if (mouseX >= cx && mouseX < cx + 18 && mouseY >= cy && mouseY < cy + 18) {
					this.selected = i;
					return true;
				}
			}
		}
		return super.mouseClicked(mouseX, mouseY, button);
	}

	private static String formatDuration(int ticks) {
		int seconds = Math.max(1, ticks / 20);
		return seconds < 60 ? seconds + " s" : (seconds / 60) + " min " + (seconds % 60) + " s";
	}

	@Override
	protected void renderBlurredBackground(float partialTick) {
	}

	@Override
	public void render(GuiGraphics g, int mouseX, int mouseY, float partialTicks) {
		this.renderTransparentBackground(g);
		g.fill(left, top, left + PANEL_W, top + panelHeight, BORDER);
		g.fill(left + 1, top + 1, left + PANEL_W - 1, top + panelHeight - 1, LIGHT);
		g.fill(left + 3, top + 3, left + PANEL_W - 1, top + panelHeight - 1, SHADE);
		g.fill(left + 3, top + 3, left + PANEL_W - 3, top + panelHeight - 3, FILL);

		Component t = Component.literal(title(this.mode));
		g.drawString(this.font, t, left + (PANEL_W - this.font.width(t)) / 2, top + 6, 0xC86E0A, true);

		String subject;
		if (picksOre()) {
			for (int i = 0; i < available.size(); i++) {
				int cx = left + GRID_X + (i % GRID_COLS) * 18;
				int cy = top + GRID_Y + (i / GRID_COLS) * 18;
				g.fill(cx, cy, cx + 18, cy + 18, i == this.selected ? CELL_SELECTED : CELL);
				g.renderItem(new ItemStack(ROOTEntity.ORES.get(available.get(i)).item()), cx + 1, cy + 1);
			}
			subject = available.isEmpty() ? "Aucun minerai ici" : new ItemStack(ROOTEntity.ORES.get(oreIndex()).item()).getHoverName().getString();
		} else {
			subject = this.mode == ROOTEntity.MODE_ATTAQUER ? "Zombies à abattre" : "Bûches à rapporter";
		}
		g.drawString(this.font, Component.literal(subject), left + 8, top + infoY, TEXT, false);
		g.drawString(this.font, Component.literal("Durée estimée : " + formatDuration(ROOTEntity.missionDuration(this.mode, oreIndex(), this.quantity))),
				left + 8, top + infoY + 12, TEXT, false);
		g.drawString(this.font, Component.literal(String.format("Risque de perte : %.2f %%", ROOTEntity.missionRisk(this.mode, oreIndex(), this.quantity) * 100)),
				left + 8, top + infoY + 24, TEXT, false);
		g.drawString(this.font, Component.literal("Usure de l'outil : " + ROOTEntity.missionDurabilityCost(this.quantity)),
				left + 8, top + infoY + 36, TEXT, false);
		super.render(g, mouseX, mouseY, partialTicks);
	}

	@Override
	public void onClose() {
		this.minecraft.setScreen(this.parent);
	}

	@Override
	public boolean isPauseScreen() {
		return false;
	}
}
