package net.sevenstars.of_beasts_and_wild_things.item;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.passive.ChickenVariants;
import net.minecraft.item.EggItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.LazyRegistryEntryReference;
import net.minecraft.util.Identifier;
import net.sevenstars.of_beasts_and_wild_things.OfBeastsAndWildThings;
import net.sevenstars.of_beasts_and_wild_things.datageneration.content.TranslationEntries;
import net.sevenstars.of_beasts_and_wild_things.datageneration.models.SimpleItemModels;

import java.util.function.Function;

public class ModItems {

    public static final Item SWAN_FEATHER = registerItem("swan_feather",
            Item::new, new Item.Settings());

    public static final Item SWAN_EGG = registerItem("swan_egg",
            EggItem::new, new Item.Settings().maxCount(16).component(DataComponentTypes.CHICKEN_VARIANT, new LazyRegistryEntryReference<>(ChickenVariants.COLD)));

    private static Item registerItem(String name, Function<Item.Settings, Item> factory, Item.Settings settings) {
        Item item = factory.apply(settings.registryKey(keyOfItem(name)));
        ModItemGroups.ITEMS_CONTENTS.add(item.getDefaultStack());
        SimpleItemModels.items.add(item);
        TranslationEntries.itemEntries.add(item);
        return Registry.register(Registries.ITEM, Identifier.of(OfBeastsAndWildThings.MOD_ID, name), item);
    }

    public static RegistryKey<Item> keyOfItem(String id) {
        return RegistryKey.of(RegistryKeys.ITEM, Identifier.of(OfBeastsAndWildThings.MOD_ID, id));
    }

    public static void registerModItems() {
        OfBeastsAndWildThings.LOGGER.logDebugMsg("Registering Mod Items for " + OfBeastsAndWildThings.MOD_ID);
    }
}
