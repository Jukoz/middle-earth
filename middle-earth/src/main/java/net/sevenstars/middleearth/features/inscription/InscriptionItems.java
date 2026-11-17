package net.sevenstars.middleearth.features.inscription;

import java.util.function.Function;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.sevenstars.middleearth.common.registration.ItemRegistrar;
import net.sevenstars.middleearth.item.utils.ItemGroupsME;

public class InscriptionItems {
    public static final Item INSCRIPTION_TABLE = registerDecorativeItem("inscription_table",
            (settings) -> new BlockItem(InscriptionBlocks.INSCRIPTION_TABLE, settings), new Item.Settings());

    private static Item registerDecorativeItem(String name, Function<Item.Settings, Item> factory, Item.Settings settings) {
        return ItemRegistrar.register(name, factory, settings, ItemGroupsME.DECORATIVES_BLOCKS_CONTENT);
    }

    public static void register() {
    }
}
