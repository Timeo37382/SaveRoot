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

import net.mcreator.saferoot.world.inventory.Loregui4Menu;
import net.mcreator.saferoot.network.Loregui4ButtonMessage;
import net.mcreator.saferoot.init.SaferootModScreens;

import com.mojang.blaze3d.systems.RenderSystem;

public class Loregui4Screen extends AbstractContainerScreen<Loregui4Menu> implements SaferootModScreens.ScreenAccessor {
	private final Level world;
	private final int x, y, z;
	private final Player entity;
	private boolean menuStateUpdateActive = false;
	private ImageButton imagebutton_croixgui1;
	private static final ResourceLocation BACKGROUND = ResourceLocation.parse("saferoot:textures/screens/loregui_4.png");
	private static final ResourceLocation IMAGE_0 = ResourceLocation.parse("saferoot:textures/screens/pixelrootprojet.png");

	public Loregui4Screen(Loregui4Menu container, Inventory inventory, Component text) {
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
		guiGraphics.drawString(this.font, Component.translatable("gui.saferoot.loregui_4.label_bien_avant_notre_ere_une_civili"), 7, 44, -12829636, false);
		guiGraphics.drawString(this.font, Component.translatable("gui.saferoot.loregui_4.label_perca_le_voile_des_dimensions_et"), 7, 57, -12829636, false);
		guiGraphics.drawString(this.font, Component.translatable("gui.saferoot.loregui_4.label_rootia_un_monde_regi_par_une_en"), 7, 70, -12829636, false);
		guiGraphics.drawString(this.font, Component.translatable("gui.saferoot.loregui_4.label_34"), 316, 5, -12829636, false);
		guiGraphics.drawString(this.font, Component.translatable("gui.saferoot.loregui_4.label_du_gardien_et_penetrer_dans_roo"), 6, 98, -12829636, false);
	}

	@Override
	public void init() {
		super.init();
		imagebutton_croixgui1 = new ImageButton(this.leftPos + 290, this.topPos + 73, 50, 50, new WidgetSprites(ResourceLocation.parse("saferoot:textures/screens/croixgui1.png"), ResourceLocation.parse("saferoot:textures/screens/croixgui1.png")),
				e -> {
					int x = Loregui4Screen.this.x;
					int y = Loregui4Screen.this.y;
					if (true) {
						PacketDistributor.sendToServer(new Loregui4ButtonMessage(0, x, y, z));
						Loregui4ButtonMessage.handleButtonAction(entity, 0, x, y, z);
					}
				}) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
				guiGraphics.blit(sprites.get(isActive(), isHoveredOrFocused()), getX(), getY(), 0, 0, width, height, width, height);
			}
		};
		this.addRenderableWidget(imagebutton_croixgui1);
	}
}