package io.github.cottonmc.cotton.gui.test;

import dev.architectury.registry.menu.MenuRegistry;
import dev.architectury.registry.registries.DeferredRegister;

import dev.architectury.registry.registries.RegistrySupplier;

import io.github.cottonmc.cotton.gui.impl.LibGuiCommon;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.StackReference;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.resource.featuretoggle.FeatureFlags;
import net.minecraft.resource.featuretoggle.FeatureSet;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.ScreenHandlerType;

public class LibGuiTest {
	private static DeferredRegister<Block> BLOCKS = DeferredRegister.create(LibGuiCommon.MOD_ID, RegistryKeys.BLOCK);
	private static DeferredRegister<Item> ITEMS = DeferredRegister.create(LibGuiCommon.MOD_ID, RegistryKeys.ITEM);
	private static DeferredRegister<ScreenHandlerType<?>> SCREEN_HANDLER = DeferredRegister.create(LibGuiCommon.MOD_ID, RegistryKeys.SCREEN_HANDLER);
	private static DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(LibGuiCommon.MOD_ID, RegistryKeys.BLOCK_ENTITY_TYPE);

	public static RegistrySupplier<GuiBlock> GUI_BLOCK;
	public static RegistrySupplier<NoBlockInventoryBlock> NO_BLOCK_INVENTORY_BLOCK;
	public static RegistrySupplier<BlockItem> GUI_BLOCK_ITEM;
	public static RegistrySupplier<BlockEntityType<GuiBlockEntity>> GUI_BLOCKENTITY_TYPE;
	public static RegistrySupplier<ScreenHandlerType<TestDescription>> GUI_SCREEN_HANDLER_TYPE;
	public static RegistrySupplier<ScreenHandlerType<TestItemDescription>> ITEM_SCREEN_HANDLER_TYPE;
	public static RegistrySupplier<ScreenHandlerType<ReallySimpleDescription>> REALLY_SIMPLE_SCREEN_HANDLER_TYPE;

	public static void init() {


		ITEMS.register("client_gui", GuiItem::new);

		GUI_BLOCK = BLOCKS.register("gui", GuiBlock::new);
		GUI_BLOCK_ITEM = ITEMS.register("gui", () -> new BlockItem(GUI_BLOCK.get(), new Item.Settings()));
		NO_BLOCK_INVENTORY_BLOCK = BLOCKS.register("no_block_inventory", () -> new NoBlockInventoryBlock(AbstractBlock.Settings.copy(Blocks.STONE)));
		ITEMS.register("no_block_inventory", () -> new BlockItem(NO_BLOCK_INVENTORY_BLOCK.get(), new Item.Settings()));
		GUI_BLOCKENTITY_TYPE = BLOCK_ENTITIES.register("gui", () -> BlockEntityType.Builder.create(GuiBlockEntity::new, GUI_BLOCK.get()).build(null));
		
		GUI_SCREEN_HANDLER_TYPE = SCREEN_HANDLER.register("gui", () -> new ScreenHandlerType<>((int syncId, PlayerInventory inventory) -> new TestDescription(GUI_SCREEN_HANDLER_TYPE.get(), syncId, inventory, ScreenHandlerContext.EMPTY), FeatureSet.of(FeatureFlags.VANILLA)));
		ITEM_SCREEN_HANDLER_TYPE = SCREEN_HANDLER.register("item_gui", () -> MenuRegistry.ofExtended((syncId, inventory, buf) -> {
			var equipmentSlot = buf.readEnumConstant(EquipmentSlot.class);
			StackReference handStack = StackReference.of(inventory.player, equipmentSlot);
			return new TestItemDescription(syncId, inventory, handStack);
		}));

		REALLY_SIMPLE_SCREEN_HANDLER_TYPE = SCREEN_HANDLER.register("really_simple", () -> new ScreenHandlerType<>(ReallySimpleDescription::new, FeatureSet.of(FeatureFlags.VANILLA)));

		BLOCKS.register();
		ITEMS.register();
		SCREEN_HANDLER.register();
		BLOCK_ENTITIES.register();
	}

}
