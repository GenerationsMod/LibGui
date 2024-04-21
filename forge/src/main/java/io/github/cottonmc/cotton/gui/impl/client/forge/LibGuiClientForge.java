package io.github.cottonmc.cotton.gui.impl.client.forge;

import io.github.cottonmc.cotton.gui.impl.LibGuiCommon;
import io.github.cottonmc.cotton.gui.impl.client.LibGuiClient;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class LibGuiClientForge {
	@SubscribeEvent
	public static void onClientSetup(FMLClientSetupEvent event) {
		LibGuiClient.onInitializeClient();
	}
}
