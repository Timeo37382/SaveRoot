package net.mcreator.saferoot.client.gui;

import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.GuiGraphics;

import net.mcreator.saferoot.world.inventory.RootGolemGuiMenu;
import net.mcreator.saferoot.init.SaferootModScreens;
import net.mcreator.saferoot.entity.ROOTEntity;

import com.mojang.blaze3d.systems.RenderSystem;

public class RootGolemGuiScreen extends AbstractContainerScreen<RootGolemGuiMenu> implements SaferootModScreens.ScreenAccessor {
	private static final ResourceLocation BACKGROUND = ResourceLocation.parse("saferoot:textures/screens/root_golem_gui.png");

	private static final int BUTTON_Y = 42, BUTTON_W = 52, BUTTON_H = 20;
	private static final int[] BUTTON_X = {6, 62, 118};
	private static final String[] LABELS = {"Miner", "Attaquer", "Bûcher"};
	private static final String[] MISSING = {"Donnez-lui une pioche en rootium", "Donnez-lui une épée en rootium", "Donnez-lui une hache en rootium"};

	private final Level world;
	private final int x, y, z;
	private final Player entity;
	private final Button[] modeButtons = new Button[3];

	public RootGolemGuiScreen(RootGolemGuiMenu container, Inventory inventory, Component text) {
		super(container, inventory, text);
		this.world = container.world;
		this.x = container.x;
		this.y = container.y;
		this.z = container.z;
		this.entity = container.entity;
		this.imageWidth = 176;
		this.imageHeight = 196;
	}

	@Override
	public void updateMenuState(int elementType, String name, Object elementState) {
	}

	@Override
	public void init() {
		super.init();
		for (int i = 0; i < modeButtons.length; i++) {
			final int mode = i;
			modeButtons[i] = Button.builder(Component.literal(LABELS[i]), b -> this.minecraft.setScreen(new RootMissionScreen(this, mode)))
					.bounds(this.leftPos + BUTTON_X[i], this.topPos + BUTTON_Y, BUTTON_W, BUTTON_H).build();
			this.addRenderableWidget(modeButtons[i]);
		}
		refreshButtons();
	}

	@Override
	protected void containerTick() {
		super.containerTick();
		refreshButtons();
	}

	private void refreshButtons() {
		for (int i = 0; i < modeButtons.length; i++) {
			if (modeButtons[i] == null)
				continue;
			ROOTEntity golem = this.menu.getGolem();
			boolean hasTool = this.menu.hasTool(i);
			boolean hereOk = this.minecraft != null && this.minecraft.level != null && ROOTEntity.modeAvailableIn(i, this.minecraft.level);
			int cooldown = golem == null ? 0 : golem.getCooldownTicks();
			boolean ready = golem == null || golem.isReady();

			String refusal = null;
			if (!hasTool)
				refusal = MISSING[i];
			else if (!hereOk)
				refusal = "Impossible dans cette dimension";
			else if (cooldown > 0)
				refusal = "Root récupère encore : " + ROOTEntity.formatDelay(cooldown);
			else if (!ready)
				refusal = "Root n'est pas disponible";

			modeButtons[i].active = refusal == null;
			modeButtons[i].setTooltip(refusal == null ? null : Tooltip.create(Component.literal(refusal)));
		}
	}

	@Override
	protected void renderBlurredBackground(float partialTick) {
	}

	@Override
	public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
		super.render(guiGraphics, mouseX, mouseY, partialTicks);
		this.renderTooltip(guiGraphics, mouseX, mouseY);
	}

	@Override
	protected void renderBg(GuiGraphics guiGraphics, float partialTicks, int mouseX, int mouseY) {
		RenderSystem.setShaderColor(1, 1, 1, 1);
		RenderSystem.enableBlend();
		RenderSystem.defaultBlendFunc();
		guiGraphics.blit(BACKGROUND, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight, this.imageWidth, this.imageHeight);
		RenderSystem.disableBlend();
	}

	@Override
	protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
		Component title = Component.literal("Root");
		guiGraphics.drawString(this.font, title, (this.imageWidth - this.font.width(title)) / 2, 6, 0xC86E0A, true);
		guiGraphics.drawString(this.font, Component.literal("Butin"), 8, 66, 0x3F3F3F, false);
		guiGraphics.drawString(this.font, Component.literal("Inventaire"), 8, 100, 0x3F3F3F, false);
	}

	@Override
	public boolean keyPressed(int key, int b, int c) {
		if (key == 256) {
			this.minecraft.player.closeContainer();
			return true;
		}
		return super.keyPressed(key, b, c);
	}
}
