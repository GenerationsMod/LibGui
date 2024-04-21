package io.github.cottonmc.cotton.gui.test.client;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.IntegerArgumentType;

import dev.architectury.registry.menu.MenuRegistry;

import io.github.cottonmc.cotton.gui.test.LibGuiTest;

import io.github.cottonmc.cotton.gui.test.ReallySimpleDescription;

import io.github.cottonmc.cotton.gui.test.TestDescription;
import io.github.cottonmc.cotton.gui.test.TestItemDescription;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;

import dev.architectury.event.events.client.ClientCommandRegistrationEvent;
import io.github.cottonmc.cotton.gui.client.CottonClientScreen;
import io.github.cottonmc.cotton.gui.client.CottonHud;
import io.github.cottonmc.cotton.gui.client.CottonInventoryScreen;
import io.github.cottonmc.cotton.gui.client.LightweightGuiDescription;
import io.github.cottonmc.cotton.gui.impl.modmenu.ConfigGui;
import io.github.cottonmc.cotton.gui.widget.WLabel;

import java.util.function.Function;

import static dev.architectury.event.events.client.ClientCommandRegistrationEvent.argument;
import static dev.architectury.event.events.client.ClientCommandRegistrationEvent.literal;

public class LibGuiTestClient {
	public static void init() {
		MenuRegistry.registerScreenFactory(LibGuiTest.GUI_SCREEN_HANDLER_TYPE.get(), new MenuRegistry.ScreenFactory<TestDescription, CottonInventoryScreen<TestDescription>>() {
			@Override
			public  CottonInventoryScreen<TestDescription> create(TestDescription containerMenu, PlayerInventory inventory, Text component) {
				return new CottonInventoryScreen<>(containerMenu, inventory, component);
			}
		});

		MenuRegistry.registerScreenFactory(LibGuiTest.REALLY_SIMPLE_SCREEN_HANDLER_TYPE.get(), new MenuRegistry.ScreenFactory<ReallySimpleDescription, CottonInventoryScreen<ReallySimpleDescription>>() {
			@Override
			public CottonInventoryScreen<ReallySimpleDescription> create(ReallySimpleDescription containerMenu, PlayerInventory inventory, Text component) {
				return new CottonInventoryScreen<>(containerMenu, inventory, component);
			}
		});

			MenuRegistry.registerScreenFactory(LibGuiTest.ITEM_SCREEN_HANDLER_TYPE.get(), new MenuRegistry.ScreenFactory<TestItemDescription, CottonInventoryScreen<TestItemDescription>>() {
				@Override
				public CottonInventoryScreen<TestItemDescription> create(TestItemDescription containerMenu, PlayerInventory inventory, Text component) {
					return new CottonInventoryScreen<>(containerMenu, inventory, component);
				}
			});

		CottonHud.add(new WHudTest(), 10, -20, 10, 10);
		CottonHud.add(new WLabel(Text.literal("Test label")), 10, -30, 10, 10);

		ClientCommandRegistrationEvent.EVENT.register((dispatcher, context) -> dispatcher.register(
				literal("libgui")
						.then(literal("config").executes(openScreen(client -> new ConfigGui(client.currentScreen))))
						.then(literal("tab").executes(openScreen(client -> new TabTestGui())))
						.then(literal("scrolling").executes(openScreen(client -> new ScrollingTestGui())))
						.then(literal("insets").executes(openScreen(client -> new InsetsTestGui())))
						.then(literal("textfield").executes(openScreen(client -> new TextFieldTestGui())))
						.then(literal("paddings")
								.then(argument("horizontal", IntegerArgumentType.integer(0))
										.then(argument("vertical", IntegerArgumentType.integer(0))
												.executes(ctx -> {
													var hori = IntegerArgumentType.getInteger(ctx, "horizontal");
													var vert = IntegerArgumentType.getInteger(ctx, "vertical");
													return openScreen(client -> new PaddingTestGui(hori, vert)).run(ctx);
												}))))
						.then(literal("#182").executes(openScreen(client -> new Issue182TestGui())))
						.then(literal("#196").executes(openScreen(client -> new Issue196TestGui())))
						.then(literal("darkmode").executes(openScreen(client -> new DarkModeTestGui())))
		));
	}

	private static Command<ClientCommandRegistrationEvent.ClientCommandSourceStack> openScreen(Function<MinecraftClient, LightweightGuiDescription> screenFactory) {
		return context -> {
			var client = MinecraftClient.getInstance();
			client.send(() -> client.setScreen(new CottonClientScreen(screenFactory.apply(client))));
			return Command.SINGLE_SUCCESS;
		};
	}



}
