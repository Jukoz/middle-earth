package net.sevenstars.middleearth.item;

import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.block.ModBlocks;
import net.sevenstars.middleearth.datageneration.content.models.SimpleSpawnEggItemModel;
import net.sevenstars.middleearth.entity.ModEntities;
import net.sevenstars.middleearth.item.utils.ModItemGroups;
import net.minecraft.item.Item;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.function.Function;

public class ModEggItems {

    public static final Item BARROW_WIGHT_SPAWN_EGG = registerItem("barrow_wight_spawn_egg",
            (settings) -> new SpawnEggItem(ModEntities.BARROW_WIGHT, settings), new Item.Settings());

    public static final Item BROADHOOF_GOAT_SPAWN_EGG = registerItem("broadhoof_goat_spawn_egg",
            (settings) -> new SpawnEggItem(ModEntities.BROADHOOF_GOAT, settings), new Item.Settings());

    public static final Item WARG_SPAWN_EGG = registerItem("warg_spawn_egg",
            (settings) -> new SpawnEggItem(ModEntities.WARG, settings), new Item.Settings());

    public static final Item STONE_TROLL_SPAWN_EGG = registerItem("stone_troll_spawn_egg",
            (settings) -> new SpawnEggItem(ModEntities.STONE_TROLL, settings), new Item.Settings());

    public static final Item SNOW_TROLL_SPAWN_EGG = registerItem("snow_troll_spawn_egg",
            (settings) -> new SpawnEggItem(ModEntities.SNOW_TROLL, settings), new Item.Settings());

    public static final Item MIRKWOOD_SPIDER_SPAWN_EGG = registerItem("mirkwood_spider_spawn_egg",
            (settings) -> new SpawnEggItem(ModEntities.MIRKWOOD_SPIDER, settings), new Item.Settings());

    // Animals
    public static final Item DEER_SPAWN_EGG = registerItem("deer_spawn_egg",
            (settings) -> new SpawnEggItem(ModEntities.DEER, settings), new Item.Settings());
    public static final Item SWAN_SPAWN_EGG = registerItem("swan_spawn_egg",
            (settings) -> new SpawnEggItem(ModEntities.SWAN, settings), new Item.Settings());
    public static final Item PHEASANT_SPAWN_EGG = registerItem("pheasant_spawn_egg",
            (settings) -> new SpawnEggItem(ModEntities.PHEASANT, settings), new Item.Settings());
    public static final Item SNAIL_SPAWN_EGG = registerItem("snail_spawn_egg",
            (settings) -> new SpawnEggItem(ModEntities.SNAIL, settings), new Item.Settings());


    private static Item registerItem(String name, Function<Item.Settings, Item> factory, Item.Settings settings) {
        Item item = (Item)factory.apply(settings.registryKey(ModBlocks.keyOfItem(name)));
        ModItemGroups.SPAWN_EGGS_CONTENTS.add(item.getDefaultStack());
        SimpleSpawnEggItemModel.items.add(item);
        return Registry.register(Registries.ITEM, Identifier.of(MiddleEarth.MOD_ID, name), item);
    }

    public static void registerModItems() {
        MiddleEarth.LOGGER.logDebugMsg("Registering Mod Egg Items for " + MiddleEarth.MOD_ID);
    }
}
