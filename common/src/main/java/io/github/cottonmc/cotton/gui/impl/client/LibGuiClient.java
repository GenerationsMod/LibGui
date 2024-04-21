package io.github.cottonmc.cotton.gui.impl.client;

import dev.architectury.platform.Mod;
import dev.architectury.platform.Platform;

import io.github.cottonmc.cotton.gui.client.CottonClientScreen;
import io.github.cottonmc.cotton.gui.client.CottonHud;
import io.github.cottonmc.cotton.gui.client.LibGui;

import io.github.cottonmc.cotton.gui.impl.LibGuiCommon;

import io.github.cottonmc.cotton.gui.impl.modmenu.ConfigGui;

import io.github.cottonmc.cotton.gui.test.client.LibGuiTestClient;
import io.github.cottonmc.cotton.gui.widget.WLabel;

import net.fabricmc.api.ClientModInitializer;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import dev.architectury.networking.NetworkManager;
import io.github.cottonmc.cotton.gui.impl.ScreenNetworkingImpl;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;

import net.minecraft.text.Text;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class LibGuiClient {
	public static final Logger logger = LogManager.getLogger();
	public static volatile LibGuiConfig config;

	public static final Gson jankson = new Gson();

	public static void onInitializeClient() {
		config = loadConfig();

		MinecraftClient.getInstance().submit(LibGuiTestClient::init);

		NetworkManager.registerReceiver(NetworkManager.s2c(), ScreenNetworkingImpl.SCREEN_MESSAGE_S2C, (buf, context) -> ScreenNetworkingImpl.handle(context::queue, context.getPlayer(), buf));

		LibGuiShaders.register();

		Platform.getMod(LibGuiCommon.MOD_ID).registerConfigurationScreen(new Mod.ConfigurationScreenProvider() {
			@Override
			public Screen provide(Screen parent) {
				return new CottonClientScreen(Text.translatable("options.libgui.libgui_settings"), new ConfigGui(parent)) {
					@Override
					public void close() {
						this.client.setScreen(parent);
					}
				};
			}
		});



		if(Platform.isDevelopmentEnvironment()) {
//			CottonHud.add(new WHudTest(), 10, -20, 10, 10);
			CottonHud.add(new WLabel(Text.literal("Test label")), 10, -30, 10, 10);
		}
	}

	public static LibGuiConfig loadConfig() {
		try {
			Path file = Platform.getConfigFolder().resolve("libgui.json5");
			
			if (Files.notExists(file)) saveConfig(new LibGuiConfig());
			
			JsonObject json;
			try (BufferedReader in = Files.newBufferedReader(file)) {
				json = jankson.fromJson(in, JsonObject.class);
			}

			config =  jankson.fromJson(json, LibGuiConfig.class);
			
			/*
			JsonElement jsonElementNew = jankson.toJson(new LibGuiConfig());
			if(jsonElementNew instanceof JsonObject) {
				JsonObject jsonNew = (JsonObject) jsonElementNew;
				if(json.getDelta(jsonNew).size()>= 0) { //TODO: Insert new keys as defaults into `json` IR object instead of writing the config out, so comments are preserved
					saveConfig(config);
				}
			}*/
		} catch (Exception e) {
			logger.error("[LibGui] Error loading config: {}", e.getMessage());
		}
		return config;
	}

	public static void saveConfig(LibGuiConfig config) {
		try {
			Path file = Platform.getConfigFolder().resolve("libgui.json5");

			String result = jankson.toJson(config);
			Files.write(file, result.getBytes(StandardCharsets.UTF_8));
		} catch (Exception e) {
			logger.error("[LibGui] Error saving config: {}", e.getMessage());
		}
	}
}
