package io.github.cottonmc.cotton.gui.forge;

import dev.architectury.forge.ArchitecturyForge;
import dev.architectury.platform.forge.EventBuses;
import dev.architectury.registry.registries.RegistrarManager;
import io.github.cottonmc.cotton.gui.impl.LibGuiCommon;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(LibGuiCommon.MOD_ID)
public class LibGuiCommonForge {
    public LibGuiCommonForge() {
		EventBuses.registerModEventBus(LibGuiCommon.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());
		FMLJavaModLoadingContext.get().getModEventBus().addListener(LibGuiCommonForge::onInitialize);
	}

    public static void onInitialize(FMLCommonSetupEvent event) {
        LibGuiCommon.init();
    }
}
