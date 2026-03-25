package net.sevenstars.middleearth.item;

import net.minecraft.item.Item;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.block.registration.ModBlocks;
import net.sevenstars.middleearth.datageneration.content.TranslationEntries;
import net.sevenstars.middleearth.datageneration.content.models.SimpleItemModel;
import net.sevenstars.middleearth.entity.ModEntities;
import net.sevenstars.middleearth.item.utils.ItemGroupsME;
import net.sevenstars.middleearth.registries.RegistryAliases;

import java.util.function.Function;


public class EggItemsME {

    /**
     * Middle-earth mod Spawn Eggs registry
     */

    public static final Item BARROW_WIGHT_SPAWN_EGG = registerItem("barrow_wight_spawn_egg",
            (settings) -> new SpawnEggItem(ModEntities.BARROW_WIGHT, settings), new Item.Settings());

    public static final Item BROADHOOF_GOAT_SPAWN_EGG = registerItem("broadhoof_goat_spawn_egg",
            (settings) -> new SpawnEggItem(ModEntities.BROADHOOF_GOAT, settings), new Item.Settings());

    public static final Item GREAT_HORN_SPAWN_EGG = registerItem("great_horn_spawn_egg",
            (settings) -> new SpawnEggItem(ModEntities.GREAT_HORN, settings), new Item.Settings());

    public static final Item WARG_SPAWN_EGG = registerItem("warg_spawn_egg",
            (settings) -> new SpawnEggItem(ModEntities.WARG, settings), new Item.Settings());

    public static final Item STONE_TROLL_SPAWN_EGG = registerItem("stone_troll_spawn_egg",
            (settings) -> new SpawnEggItem(ModEntities.STONE_TROLL, settings), new Item.Settings());

    public static final Item SNOW_TROLL_SPAWN_EGG = registerItem("snow_troll_spawn_egg",
            (settings) -> new SpawnEggItem(ModEntities.SNOW_TROLL, settings), new Item.Settings());
    public static final Item CAVE_TROLL_SPAWN_EGG = registerItem("cave_troll_spawn_egg",
            (settings) -> new SpawnEggItem(ModEntities.CAVE_TROLL, settings), new Item.Settings());

    public static final Item SHELOBITE_LARVA_SPAWN_EGG = registerItem("shelobite_larva_spawn_egg",
            (settings) -> new SpawnEggItem(ModEntities.SHELOBITE_LARVA, settings), new Item.Settings());
    public static final Item SHELOBITE_SCUTTLER_SPAWN_EGG = registerItem("shelobite_scuttler_spawn_egg",
            (settings) -> new SpawnEggItem(ModEntities.SHELOBITE_SCUTTLER, settings), new Item.Settings());
    public static final Item SHELOBITE_SPAWN_SPAWN_EGG = registerItem("spawn_of_shelob_spawn_egg",
            (settings) -> new SpawnEggItem(ModEntities.SPAWN_OF_SHELOB, settings), new Item.Settings());

    // Npcs
    public static final Item NPC_SPAWN_EGG = registerSpecialEgg("npc_spawn_egg",
            (settings) -> new SpawnEggItem(ModEntities.NPC, settings), new Item.Settings());

    private static Item registerItem(String name, Function<Item.Settings, Item> factory, Item.Settings settings) {
        Item item = (Item)factory.apply(settings.registryKey(ModBlocks.keyOfItem(name)));
        ItemGroupsME.SPAWN_EGGS_CONTENTS.add(item.getDefaultStack());
        SimpleItemModel.items.add(item);
        TranslationEntries.itemEntries.add(item);
        RegistryAliases.aliases.add(new RegistryAliases.Alias(Registries.ITEM, name));
        return Registry.register(Registries.ITEM, Identifier.of(MiddleEarth.MOD_ID, name), item);
    }
    private static Item registerSpecialEgg(String name, Function<Item.Settings, Item> factory, Item.Settings settings) {
        Item item = (Item)factory.apply(settings.registryKey(ModBlocks.keyOfItem(name)));
        ItemGroupsME.SPAWN_EGGS_CONTENTS.add(item.getDefaultStack());
        TranslationEntries.itemEntries.add(item);
        RegistryAliases.aliases.add(new RegistryAliases.Alias(Registries.ITEM, name));
        return Registry.register(Registries.ITEM, Identifier.of(MiddleEarth.MOD_ID, name), item);
    }

    public static void registerModItems() {
        MiddleEarth.LOGGER.logDebugMsg("Registering Mod Egg Items for " + MiddleEarth.MOD_ID);
    }
}
