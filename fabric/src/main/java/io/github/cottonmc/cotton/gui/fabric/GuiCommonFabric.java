package io.github.cottonmc.cotton.gui.fabric;

import io.github.cottonmc.cotton.gui.impl.LibGuiCommon;
import net.fabricmc.api.ModInitializer;

public class GuiCommonFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        LibGuiCommon.init();
    }
}
