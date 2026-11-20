package net.sevenstars.middleearth.features.inscription;

import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.sevenstars.middleearth.common.registration.ItemRegistrar;
import net.sevenstars.middleearth.item.utils.ItemGroupsME;

public class InscriptionItems {
    private static final ItemRegistrar DECORATIVES = ItemRegistrar.forGroup(ItemGroupsME.DECORATIVES_BLOCKS_CONTENT);

    public static final Item INSCRIPTION_TABLE = DECORATIVES.register("inscription_table",
            (settings) -> new BlockItem(InscriptionBlocks.INSCRIPTION_TABLE, settings), new Item.Settings());
}
