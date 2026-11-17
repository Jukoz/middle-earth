package net.sevenstars.middleearth.features.artisan;

import java.util.function.Function;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.sevenstars.middleearth.common.registration.ItemRegistrar;
import net.sevenstars.middleearth.item.utils.ItemGroupsME;

public class ArtisanItems {
    public static final Item ARTISAN_TABLE = registerDecorativeItem("artisan_table",
            (settings) -> new BlockItem(ArtisanBlocks.ARTISAN_TABLE, settings), new Item.Settings());

    public static final Item ORCISH_ARTISAN_TABLE = registerDecorativeItem("orcish_artisan_table",
            (settings) -> new BlockItem(ArtisanBlocks.ORCISH_ARTISAN_TABLE, settings), new Item.Settings());

    private static Item registerDecorativeItem(String name, Function<Item.Settings, Item> factory, Item.Settings settings) {
        return ItemRegistrar.register(name, factory, settings, ItemGroupsME.DECORATIVES_BLOCKS_CONTENT);
    }

    public static void register() {
    }
}
