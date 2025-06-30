package net.sevenstars.of_beasts_and_wild_things.item;

import net.minecraft.item.Item;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.sevenstars.of_beasts_and_wild_things.OfBeastsAndWildThings;
import net.sevenstars.of_beasts_and_wild_things.datageneration.content.TranslationEntries;
import net.sevenstars.of_beasts_and_wild_things.datageneration.models.SimpleItemModels;
import net.sevenstars.of_beasts_and_wild_things.entity.ModEntities;

import java.util.function.Function;


public class ModEggItems {

    // Animals
    public static final Item DEER_SPAWN_EGG = registerItem("deer_spawn_egg",
            (settings) -> new SpawnEggItem(ModEntities.DEER, settings), new Item.Settings());
    public static final Item PHEASANT_SPAWN_EGG = registerItem("pheasant_spawn_egg",
            (settings) -> new SpawnEggItem(ModEntities.PHEASANT, settings), new Item.Settings());
    public static final Item SNAIL_SPAWN_EGG = registerItem("snail_spawn_egg",
            (settings) -> new SpawnEggItem(ModEntities.SNAIL, settings), new Item.Settings());

    public static RegistryKey<Item> keyOfItem(String id) {
        return RegistryKey.of(RegistryKeys.ITEM, Identifier.of(OfBeastsAndWildThings.MOD_ID, id));
    }
    private static Item registerItem(String name, Function<Item.Settings, Item> factory, Item.Settings settings) {
        Item item = factory.apply(settings.registryKey(keyOfItem(name)));
        ModItemGroups.SPAWN_EGGS_CONTENTS.add(item.getDefaultStack());
        SimpleItemModels.items.add(item);
        TranslationEntries.itemEntries.add(item);
        return Registry.register(Registries.ITEM, Identifier.of(OfBeastsAndWildThings.MOD_ID, name), item);
    }

    public static void registerModItems() {
        OfBeastsAndWildThings.LOGGER.logDebugMsg("Registering Mod Egg Items for " + OfBeastsAndWildThings.MOD_ID);
    }
}
