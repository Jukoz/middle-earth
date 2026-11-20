package net.sevenstars.middleearth.features.artisan;

import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.sevenstars.middleearth.common.registration.ItemRegistrar;
import net.sevenstars.middleearth.item.utils.ItemGroupsME;

public class ArtisanItems {
    private static final ItemRegistrar DECORATIVES = ItemRegistrar.forGroup(ItemGroupsME.DECORATIVES_BLOCKS_CONTENT);

    public static final Item ARTISAN_TABLE = DECORATIVES.register("artisan_table",
            (settings) -> new BlockItem(ArtisanBlocks.ARTISAN_TABLE, settings), new Item.Settings());

    public static final Item ORCISH_ARTISAN_TABLE = DECORATIVES.register("orcish_artisan_table",
            (settings) -> new BlockItem(ArtisanBlocks.ORCISH_ARTISAN_TABLE, settings), new Item.Settings());
}
