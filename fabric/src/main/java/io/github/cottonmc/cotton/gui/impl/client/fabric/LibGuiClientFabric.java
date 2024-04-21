package io.github.cottonmc.cotton.gui.impl.client.fabric;

import dev.architectury.event.EventResult;
import dev.architectury.event.events.client.ClientRawInputEvent;
import io.github.cottonmc.cotton.gui.client.CottonHud;
import io.github.cottonmc.cotton.gui.client.ScreenDrawing;
import io.github.cottonmc.cotton.gui.impl.client.LibGuiClient;

import io.github.cottonmc.cotton.gui.widget.WLabel;

import io.github.cottonmc.cotton.gui.widget.WWidget;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.keybinding.FabricKeyBinding;
import net.fabricmc.fabric.api.client.keybinding.KeyBindingRegistry;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.impl.client.keybinding.KeyBindingRegistryImpl;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static net.minecraft.client.util.InputUtil.GLFW_KEY_SLASH;

public class LibGuiClientFabric implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		LibGuiClient.onInitializeClient();
	}

	@Environment(EnvType.CLIENT)
	public class WHudTest extends WWidget {
		private static final Logger LOGGER = LogManager.getLogger();

		@Override
		public void paint(DrawContext context, int x, int y, int mouseX, int mouseY) {
			ScreenDrawing.coloredRect(context, x, y, width, height, 0xFF_00FF00);
		}

		@Override
		public void tick() {
			LOGGER.debug("tick!");
		}
	}

}
