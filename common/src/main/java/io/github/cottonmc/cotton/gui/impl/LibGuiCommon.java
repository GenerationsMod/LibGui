package io.github.cottonmc.cotton.gui.impl;

import io.github.cottonmc.cotton.gui.impl.ScreenNetworkingImpl;

import io.github.cottonmc.cotton.gui.impl.client.LibGuiClient;

import io.github.cottonmc.cotton.gui.test.LibGuiTest;

import net.fabricmc.api.ModInitializer;

public final class LibGuiCommon {
	public static final String MOD_ID = "libgui";

	public static void init() {
		LibGuiTest.init();
		ScreenNetworkingImpl.init();
	}
}
