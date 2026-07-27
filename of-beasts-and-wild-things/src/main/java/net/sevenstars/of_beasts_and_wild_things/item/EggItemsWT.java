package net.sevenstars.of_beasts_and_wild_things.item;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import net.sevenstars.of_beasts_and_wild_things.OfBeastsAndWildThings;
import net.sevenstars.of_beasts_and_wild_things.datageneration.content.TranslationEntries;
import net.sevenstars.of_beasts_and_wild_things.datageneration.models.SimpleItemModels;
import net.sevenstars.of_beasts_and_wild_things.entity.EntitiesWT;
import net.sevenstars.api.registries.RegistrationBridge;

import java.util.function.Function;


public class EggItemsWT {

    // Animals
    public static final Item DEER_SPAWN_EGG = registerItem("deer_spawn_egg",
            (settings) -> new SpawnEggItem(EntitiesWT.DEER, 0x9A6A3A, 0xD8C29E, settings), new Item.Properties());
    public static final Item SWAN_SPAWN_EGG = registerItem("swan_spawn_egg",
            (settings) -> new SpawnEggItem(EntitiesWT.SWAN, 0xF2F2F2, 0x252525, settings), new Item.Properties());
    public static final Item PHEASANT_SPAWN_EGG = registerItem("pheasant_spawn_egg",
            (settings) -> new SpawnEggItem(EntitiesWT.PHEASANT, 0x8C4B25, 0x2E5C36, settings), new Item.Properties());
    public static final Item SNAIL_SPAWN_EGG = registerItem("snail_spawn_egg",
            (settings) -> new SpawnEggItem(EntitiesWT.SNAIL, 0x76583B, 0xC5A46C, settings), new Item.Properties());

    public static ResourceKey<Item> keyOfItem(String idPath) {
        return ResourceKey.create(Registries.ITEM, OfBeastsAndWildThings.of(idPath));
    }
    private static Item registerItem(String idPath, Function<Item.Properties, Item> factory, Item.Properties settings) {
        Item item = factory.apply(settings);
        ItemGroupsWT.SPAWN_EGGS_CONTENTS.add(item.getDefaultInstance());
        SimpleItemModels.items.add(item);
        TranslationEntries.itemEntries.add(item);
        return RegistrationBridge.register(BuiltInRegistries.ITEM, OfBeastsAndWildThings.of(idPath), item);
    }

    public static void registerModItems() {
        OfBeastsAndWildThings.LOGGER.logDebugMsg("Registering Mod Egg Items for " + OfBeastsAndWildThings.MOD_ID);
    }
}
