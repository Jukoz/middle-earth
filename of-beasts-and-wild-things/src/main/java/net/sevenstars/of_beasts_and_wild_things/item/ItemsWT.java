package net.sevenstars.of_beasts_and_wild_things.item;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.sevenstars.api.registries.RegistrationBridge;
import net.sevenstars.of_beasts_and_wild_things.OfBeastsAndWildThings;
import net.sevenstars.of_beasts_and_wild_things.datageneration.content.TranslationEntries;
import net.sevenstars.of_beasts_and_wild_things.datageneration.models.SimpleItemModels;
import net.sevenstars.of_beasts_and_wild_things.item.items.SwanEggItem;

import java.util.function.Function;

public class ItemsWT {
    public static final Item RAW_VENISON = registerItem("raw_venison",
            Item::new,new Item.Properties()
                    .food(new FoodProperties.Builder().nutrition(2).saturationModifier(0.4F).build()));
    public static final Item COOKED_VENISON = registerItem("cooked_venison",
            Item::new,new Item.Properties()
                    .food(new FoodProperties.Builder().nutrition(6).saturationModifier(0.8F).build()));
    public static final Item RAW_POULTRY = registerItem("raw_poultry",
            Item::new,new Item.Properties()
                    .food(new FoodProperties.Builder().nutrition(2).saturationModifier(0.3F).build()));
    public static final Item COOKED_POULTRY = registerItem("cooked_poultry",
            Item::new,new Item.Properties()
                    .food(new FoodProperties.Builder().nutrition(6).saturationModifier(0.6F).build()));
    public static final Item RAW_SWAN = registerItem("raw_swan",
            Item::new,new Item.Properties()
                    .food(new FoodProperties.Builder().nutrition(3).saturationModifier(0.4F).build()));
    public static final Item COOKED_SWAN = registerItem("cooked_swan",
            Item::new,new Item.Properties()
                    .food(new FoodProperties.Builder().nutrition(8).saturationModifier(0.8F).build()));
    public static final Item SWAN_FEATHER = registerItem("swan_feather",
            Item::new, new Item.Properties());

    public static final Item SWAN_EGG = registerItem("swan_egg",
            SwanEggItem::new, new Item.Properties().stacksTo(16));

    private static Item registerItem(String idPath, Function<Item.Properties, Item> factory, Item.Properties settings) {
        Item item = factory.apply(settings);
        ItemGroupsWT.ITEMS_CONTENTS.add(item.getDefaultInstance());
        SimpleItemModels.items.add(item);
        TranslationEntries.itemEntries.add(item);
        return RegistrationBridge.register(BuiltInRegistries.ITEM, OfBeastsAndWildThings.of(idPath), item);
    }

    public static ResourceKey<Item> keyOfItem(String idPath) {
        return ResourceKey.create(Registries.ITEM, OfBeastsAndWildThings.of(idPath));
    }

    public static void registerModItems() {
        OfBeastsAndWildThings.LOGGER.logDebugMsg("Registering Mod Items for " + OfBeastsAndWildThings.MOD_ID);
    }
}
