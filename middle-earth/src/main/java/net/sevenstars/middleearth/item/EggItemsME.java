package net.sevenstars.middleearth.item;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.api.registries.RegistrationBridge;
import net.sevenstars.middleearth.block.registration.ModBlocks;
import net.sevenstars.middleearth.datageneration.content.TranslationEntries;
import net.sevenstars.middleearth.datageneration.content.models.SimpleItemModel;
import net.sevenstars.middleearth.entity.EntitiesME;
import net.sevenstars.middleearth.item.items.CustomSpawnEggItem;
import net.sevenstars.middleearth.item.utils.ItemGroupsME;
import net.sevenstars.middleearth.registries.RegistryAliasesME;

import java.util.function.Function;


public class EggItemsME {

    /**
     * Middle-earth mod Spawn Eggs registry
     */


    public static final Item BROADHOOF_GOAT_SPAWN_EGG = registerItem("broadhoof_goat_spawn_egg",
            (settings) -> new CustomSpawnEggItem(EntitiesME.BROADHOOF_GOAT, settings), new Item.Properties());

    public static final Item GREAT_HORN_SPAWN_EGG = registerItem("great_horn_spawn_egg",
            (settings) -> new CustomSpawnEggItem(EntitiesME.GREAT_HORN, settings), new Item.Properties());

    public static final Item WARG_SPAWN_EGG = registerItem("warg_spawn_egg",
            (settings) -> new CustomSpawnEggItem(EntitiesME.WARG, settings), new Item.Properties());

    public static final Item STONE_TROLL_SPAWN_EGG = registerItem("stone_troll_spawn_egg",
            (settings) -> new CustomSpawnEggItem(EntitiesME.STONE_TROLL, settings), new Item.Properties());

    public static final Item SNOW_TROLL_SPAWN_EGG = registerItem("snow_troll_spawn_egg",
            (settings) -> new CustomSpawnEggItem(EntitiesME.SNOW_TROLL, settings), new Item.Properties());
    public static final Item CAVE_TROLL_SPAWN_EGG = registerItem("cave_troll_spawn_egg",
            (settings) -> new CustomSpawnEggItem(EntitiesME.CAVE_TROLL, settings), new Item.Properties());

    public static final Item SHELOBITE_LARVA_SPAWN_EGG = registerItem("shelobite_larva_spawn_egg",
            (settings) -> new CustomSpawnEggItem(EntitiesME.SHELOBITE_LARVA, settings), new Item.Properties());
    public static final Item SHELOBITE_SCUTTLER_SPAWN_EGG = registerItem("shelobite_scuttler_spawn_egg",
            (settings) -> new CustomSpawnEggItem(EntitiesME.SHELOBITE_SCUTTLER, settings), new Item.Properties());
    public static final Item SHELOBITE_SPAWN_SPAWN_EGG = registerItem("spawn_of_shelob_spawn_egg",
            (settings) -> new CustomSpawnEggItem(EntitiesME.SPAWN_OF_SHELOB, settings), new Item.Properties());

    // Npcs
    public static final Item NPC_SPAWN_EGG = registerSpecialEgg("npc_spawn_egg",
            (settings) -> new CustomSpawnEggItem(EntitiesME.NPC, settings), new Item.Properties());

    private static Item registerItem(String name, Function<Item.Properties, Item> factory, Item.Properties settings) {
        Item item = factory.apply(settings);
        ItemGroupsME.SPAWN_EGGS_CONTENTS.add(item.getDefaultInstance());
        SimpleItemModel.items.add(item);
        TranslationEntries.itemEntries.add(item);
        RegistryAliasesME.aliases.add(new RegistryAliasesME.Alias(BuiltInRegistries.ITEM, name));
        return RegistrationBridge.register(BuiltInRegistries.ITEM, ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, name), item);
    }
    private static Item registerSpecialEgg(String name, Function<Item.Properties, Item> factory, Item.Properties settings) {
        Item item = factory.apply(settings);
        ItemGroupsME.SPAWN_EGGS_CONTENTS.add(item.getDefaultInstance());
        TranslationEntries.itemEntries.add(item);
        RegistryAliasesME.aliases.add(new RegistryAliasesME.Alias(BuiltInRegistries.ITEM, name));
        return RegistrationBridge.register(BuiltInRegistries.ITEM, ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, name), item);
    }

    public static void registerModItems() {
        MiddleEarth.LOGGER.logDebugMsg("Registering Mod Egg Items for " + MiddleEarth.MOD_ID);

        TranslationEntries.spawnEggEntries.add(MiddleEarth.of("npc_random_spawn_egg"));
    }
}
