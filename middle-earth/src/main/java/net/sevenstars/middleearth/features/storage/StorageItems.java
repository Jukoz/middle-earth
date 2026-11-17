package net.sevenstars.middleearth.features.storage;

import java.util.function.Function;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.sevenstars.middleearth.common.registration.ItemRegistrar;
import net.sevenstars.middleearth.item.utils.ItemGroupsME;

public class StorageItems {
    public static final Item SMALL_CRATE = registerDecorativeItem("small_crate",
            (settings) -> new BlockItem(StorageBlocks.SMALL_CRATE, settings), new Item.Settings());

    public static final Item THIN_BARREL = registerDecorativeItem("thin_barrel",
            (settings) -> new BlockItem(StorageBlocks.THIN_BARREL, settings), new Item.Settings());

    public static final Item LARCH_COFFER = registerDecorativeItem("larch_coffer",
            (settings) -> new BlockItem(StorageBlocks.LARCH_COFFER, settings), new Item.Settings());

    public static final Item PINE_COFFER = registerDecorativeItem("pine_coffer",
            (settings) -> new BlockItem(StorageBlocks.PINE_COFFER, settings), new Item.Settings());

    public static final Item SPRUCE_COFFER = registerDecorativeItem("spruce_coffer",
            (settings) -> new BlockItem(StorageBlocks.SPRUCE_COFFER, settings), new Item.Settings());

    public static final Item FIR_COFFER = registerDecorativeItem("fir_coffer",
            (settings) -> new BlockItem(StorageBlocks.FIR_COFFER, settings), new Item.Settings());

    public static final Item BEECH_COFFER = registerDecorativeItem("beech_coffer",
            (settings) -> new BlockItem(StorageBlocks.BEECH_COFFER, settings), new Item.Settings());

    public static final Item CHESTNUT_COFFER = registerDecorativeItem("chestnut_coffer",
            (settings) -> new BlockItem(StorageBlocks.CHESTNUT_COFFER, settings), new Item.Settings());

    public static final Item OAK_COFFER = registerDecorativeItem("oak_coffer",
            (settings) -> new BlockItem(StorageBlocks.OAK_COFFER, settings), new Item.Settings());

    public static final Item WILLOW_COFFER = registerDecorativeItem("willow_coffer",
            (settings) -> new BlockItem(StorageBlocks.WILLOW_COFFER, settings), new Item.Settings());

    public static final Item REINFORCED_CHEST = registerDecorativeItem("reinforced_chest",
            (settings) -> new BlockItem(StorageBlocks.REINFORCED_CHEST, settings), new Item.Settings());

    public static final Item SACK = registerDecorativeItem("sack",
            (settings) -> new BlockItem(StorageBlocks.SACK, settings) {
                @Override
                public boolean canBeNested() {
                    return false;
                }
            }, new Item.Settings().maxCount(1));

    public static final Item WOOD_PILE = registerDecorativeItem("wood_pile",
            (settings) -> new BlockItem(StorageBlocks.WOOD_PILE, settings), new Item.Settings());

    private static Item registerDecorativeItem(String name, Function<Item.Settings, Item> factory, Item.Settings settings) {
        return ItemRegistrar.register(name, factory, settings, ItemGroupsME.DECORATIVES_BLOCKS_CONTENT);
    }

    public static void register() {
    }
}
