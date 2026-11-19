package net.sevenstars.middleearth.features.storage;

import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.sevenstars.middleearth.common.registration.ItemRegistrar;
import net.sevenstars.middleearth.item.utils.ItemGroupsME;

public class StorageItems {
    private static final ItemRegistrar DECORATIVES = ItemRegistrar.forGroup(ItemGroupsME.DECORATIVES_BLOCKS_CONTENT);

    public static final Item SMALL_CRATE = DECORATIVES.register("small_crate",
            (settings) -> new BlockItem(StorageBlocks.SMALL_CRATE, settings), new Item.Settings());

    public static final Item THIN_BARREL = DECORATIVES.register("thin_barrel",
            (settings) -> new BlockItem(StorageBlocks.THIN_BARREL, settings), new Item.Settings());

    public static final Item LARCH_COFFER = DECORATIVES.register("larch_coffer",
            (settings) -> new BlockItem(StorageBlocks.LARCH_COFFER, settings), new Item.Settings());

    public static final Item PINE_COFFER = DECORATIVES.register("pine_coffer",
            (settings) -> new BlockItem(StorageBlocks.PINE_COFFER, settings), new Item.Settings());

    public static final Item SPRUCE_COFFER = DECORATIVES.register("spruce_coffer",
            (settings) -> new BlockItem(StorageBlocks.SPRUCE_COFFER, settings), new Item.Settings());

    public static final Item FIR_COFFER = DECORATIVES.register("fir_coffer",
            (settings) -> new BlockItem(StorageBlocks.FIR_COFFER, settings), new Item.Settings());

    public static final Item BEECH_COFFER = DECORATIVES.register("beech_coffer",
            (settings) -> new BlockItem(StorageBlocks.BEECH_COFFER, settings), new Item.Settings());

    public static final Item CHESTNUT_COFFER = DECORATIVES.register("chestnut_coffer",
            (settings) -> new BlockItem(StorageBlocks.CHESTNUT_COFFER, settings), new Item.Settings());

    public static final Item OAK_COFFER = DECORATIVES.register("oak_coffer",
            (settings) -> new BlockItem(StorageBlocks.OAK_COFFER, settings), new Item.Settings());

    public static final Item WILLOW_COFFER = DECORATIVES.register("willow_coffer",
            (settings) -> new BlockItem(StorageBlocks.WILLOW_COFFER, settings), new Item.Settings());

    public static final Item REINFORCED_CHEST = DECORATIVES.register("reinforced_chest",
            (settings) -> new BlockItem(StorageBlocks.REINFORCED_CHEST, settings), new Item.Settings());

    public static final Item SACK = DECORATIVES.register("sack",
            (settings) -> new BlockItem(StorageBlocks.SACK, settings) {
                @Override
                public boolean canBeNested() {
                    return false;
                }
            }, new Item.Settings().maxCount(1));

    public static final Item WOOD_PILE = DECORATIVES.register("wood_pile",
            (settings) -> new BlockItem(StorageBlocks.WOOD_PILE, settings), new Item.Settings());
}
