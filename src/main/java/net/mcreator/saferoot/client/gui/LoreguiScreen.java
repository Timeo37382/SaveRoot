package net.mcreator.saferoot.client.gui;

import net.neoforged.neoforge.network.PacketDistributor;

import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.GuiGraphics;

import net.mcreator.saferoot.world.inventory.LoreguiMenu;
import net.mcreator.saferoot.network.LoreguiButtonMessage;
import net.mcreator.saferoot.init.SaferootModScreens;

import com.mojang.blaze3d.systems.RenderSystem;

public class LoreguiScreen extends AbstractContainerScreen<LoreguiMenu> implements SaferootModScreens.ScreenAccessor {
	private final Level world;
	private final int x, y, z;
	private final Player entity;
	private boolean menuStateUpdateActive = false;
	private ImageButton imagebutton_flechepng;
	private static final ResourceLocation BACKGROUND = ResourceLocation.parse("saferoot:textures/screens/loregui.png");
	private static final ResourceLocation IMAGE_0 = ResourceLocation.parse("saferoot:textures/screens/pixelrootprojet.png");

	public LoreguiScreen(LoreguiMenu container, Inventory inventory, Component text) {
		super(container, inventory, text);
		this.world = container.world;
		this.x = container.x;
		this.y = container.y;
		this.z = container.z;
		this.entity = container.entity;
		this.imageWidth = 338;
		this.imageHeight = 119;
	}

	@Override
	public void updateMenuState(int elementType, String name, Object elementState) {
		menuStateUpdateActive = true;
		menuStateUpdateActive = false;
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
		guiGraphics.blit(IMAGE_0, this.leftPos + -7, this.topPos + -23, 0, 0, 64, 64, 64, 64);
		RenderSystem.disableBlend();
	}

	@Override
	public boolean keyPressed(int key, int b, int c) {
		if (key == 256) {
			this.minecraft.player.closeContainer();
			return true;
		}
		return super.keyPressed(key, b, c);
	}

	@Override
	protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
		guiGraphics.drawString(this.font, Component.translatable("gui.saferoot.loregui.label_bien_avant_notre_ere_une_civili"), 7, 44, -12829636, false);
		guiGraphics.drawString(this.font, Component.translatable("gui.saferoot.loregui.label_perca_le_voile_des_dimensions_et"), 7, 57, -12829636, false);
		guiGraphics.drawString(this.font, Component.translatable("gui.saferoot.loregui.label_rootia_un_monde_regi_par_une_en"), 7, 70, -12829636, false);
		guiGraphics.drawString(this.font, Component.translatable("gui.saferoot.loregui.label_pouvoir_incommensurable"), 7, 83, -12829636, false);
		guiGraphics.drawString(this.font, Component.translatable("gui.saferoot.loregui.label_root"), 8, 96, -10092544, true);
		guiGraphics.drawString(this.font, Component.translatable("gui.saferoot.loregui.label_14"), 316, 6, -12829636, false);
	}

	@Override
	public void init() {
		super.init();
		imagebutton_flechepng = new ImageButton(this.leftPos + 300, this.topPos + 81, 32, 32, new WidgetSprites(ResourceLocation.parse("saferoot:textures/screens/flechepng.png"), ResourceLocation.parse("saferoot:textures/screens/flechepng.png")),
				e -> {
					int x = LoreguiScreen.this.x;
					int y = LoreguiScreen.this.y;
					if (true) {
						PacketDistributor.sendToServer(new LoreguiButtonMessage(0, x, y, z));
						LoreguiButtonMessage.handleButtonAction(entity, 0, x, y, z);
					}
				}) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
				guiGraphics.blit(sprites.get(isActive(), isHoveredOrFocused()), getX(), getY(), 0, 0, width, height, width, height);
			}
		};
		this.addRenderableWidget(imagebutton_flechepng);
	}
}