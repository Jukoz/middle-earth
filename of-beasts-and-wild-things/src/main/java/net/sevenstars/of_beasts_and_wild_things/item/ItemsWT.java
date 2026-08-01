package net.sevenstars.of_beasts_and_wild_things.item;

import net.minecraft.component.type.ConsumableComponents;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.sevenstars.of_beasts_and_wild_things.OfBeastsAndWildThings;
import net.sevenstars.of_beasts_and_wild_things.datageneration.content.TranslationEntries;
import net.sevenstars.of_beasts_and_wild_things.datageneration.models.SimpleItemModels;
import net.sevenstars.of_beasts_and_wild_things.item.items.SwanEggItem;

import java.util.function.Function;

public class ItemsWT {
    public static final Item RAW_VENISON = registerItem("raw_venison",
            Item::new,new Item.Settings()
                    .food(new FoodComponent.Builder().nutrition(2).saturationModifier(0.4F).build()));
    public static final Item COOKED_VENISON = registerItem("cooked_venison",
            Item::new,new Item.Settings()
                    .food(new FoodComponent.Builder().nutrition(6).saturationModifier(0.8F).build()));
    public static final Item RAW_POULTRY = registerItem("raw_poultry",
            Item::new,new Item.Settings()
                    .food(new FoodComponent.Builder().nutrition(2).saturationModifier(0.3F).build(), ConsumableComponents.RAW_CHICKEN));
    public static final Item COOKED_POULTRY = registerItem("cooked_poultry",
            Item::new,new Item.Settings()
                    .food(new FoodComponent.Builder().nutrition(6).saturationModifier(0.6F).build()));
    public static final Item RAW_SWAN = registerItem("raw_swan",
            Item::new,new Item.Settings()
                    .food(new FoodComponent.Builder().nutrition(3).saturationModifier(0.4F).build(), ConsumableComponents.RAW_CHICKEN));
    public static final Item COOKED_SWAN = registerItem("cooked_swan",
            Item::new,new Item.Settings()
                    .food(new FoodComponent.Builder().nutrition(8).saturationModifier(0.8F).build()));
    public static final Item SWAN_FEATHER = registerItem("swan_feather",
            Item::new, new Item.Settings());

    public static final Item SWAN_EGG = registerItem("swan_egg",
            SwanEggItem::new, new Item.Settings().maxCount(16));

    private static Item registerItem(String idPath, Function<Item.Settings, Item> factory, Item.Settings settings) {
        Item item = factory.apply(settings.registryKey(keyOfItem(idPath)));
        ItemGroupsWT.ITEMS_CONTENTS.add(item.getDefaultStack());
        SimpleItemModels.items.add(item);
        TranslationEntries.itemEntries.add(item);
        return Registry.register(Registries.ITEM, OfBeastsAndWildThings.of(idPath), item);
    }

    public static RegistryKey<Item> keyOfItem(String idPath) {
        return RegistryKey.of(RegistryKeys.ITEM, OfBeastsAndWildThings.of(idPath));
    }

    public static void registerModItems() {
        OfBeastsAndWildThings.LOGGER.logDebugMsg("Registering Mod Items for " + OfBeastsAndWildThings.MOD_ID);
    }
}
