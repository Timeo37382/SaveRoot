/*
 *	MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.saferoot.init;

import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.api.distmarker.Dist;

import net.mcreator.saferoot.client.gui.RootGolemGuiScreen;
import net.mcreator.saferoot.client.gui.LoreguiScreen;
import net.mcreator.saferoot.client.gui.Loregui4Screen;
import net.mcreator.saferoot.client.gui.Loregui3Screen;
import net.mcreator.saferoot.client.gui.Loregui2Screen;

@EventBusSubscriber(Dist.CLIENT)
public class SaferootModScreens {
	@SubscribeEvent
	public static void clientLoad(RegisterMenuScreensEvent event) {
		event.register(SaferootModMenus.LOREGUI.get(), LoreguiScreen::new);
		event.register(SaferootModMenus.LOREGUI_2.get(), Loregui2Screen::new);
		event.register(SaferootModMenus.LOREGUI_3.get(), Loregui3Screen::new);
		event.register(SaferootModMenus.LOREGUI_4.get(), Loregui4Screen::new);
		event.register(SaferootModMenus.ROOT_GOLEM_GUI.get(), RootGolemGuiScreen::new);
	}

	public interface ScreenAccessor {
		void updateMenuState(int elementType, String name, Object elementState);
	}
}