package net.sevenstars.middleearth.features.storage;

import net.minecraft.block.Block;
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

    public static final Item SACK = registerSack("sack", StorageBlocks.SACK);
    public static final Item WHITE_SACK = registerSack("white_sack", StorageBlocks.WHITE_SACK);
    public static final Item BLACK_SACK = registerSack("black_sack", StorageBlocks.BLACK_SACK);
    public static final Item BLUE_SACK = registerSack("blue_sack", StorageBlocks.BLUE_SACK);
    public static final Item BROWN_SACK = registerSack("brown_sack", StorageBlocks.BROWN_SACK);
    public static final Item CYAN_SACK = registerSack("cyan_sack", StorageBlocks.CYAN_SACK);
    public static final Item GREEN_SACK = registerSack("green_sack", StorageBlocks.GREEN_SACK);
    public static final Item GRAY_SACK = registerSack("gray_sack", StorageBlocks.GRAY_SACK);
    public static final Item LIGHT_BLUE_SACK = registerSack("light_blue_sack", StorageBlocks.LIGHT_BLUE_SACK);
    public static final Item LIGHT_GRAY_SACK = registerSack("light_gray_sack", StorageBlocks.LIGHT_GRAY_SACK);
    public static final Item LIME_SACK = registerSack("lime_sack", StorageBlocks.LIME_SACK);
    public static final Item MAGENTA_SACK = registerSack("magenta_sack", StorageBlocks.MAGENTA_SACK);
    public static final Item ORANGE_SACK = registerSack("orange_sack", StorageBlocks.ORANGE_SACK);
    public static final Item PINK_SACK = registerSack("pink_sack", StorageBlocks.PINK_SACK);
    public static final Item PURPLE_SACK = registerSack("purple_sack", StorageBlocks.PURPLE_SACK);
    public static final Item RED_SACK = registerSack("red_sack", StorageBlocks.RED_SACK);
    public static final Item YELLOW_SACK = registerSack("yellow_sack", StorageBlocks.YELLOW_SACK);

    public static final Item WOOD_PILE = DECORATIVES.register("wood_pile",
            (settings) -> new BlockItem(StorageBlocks.WOOD_PILE, settings), new Item.Settings());

    // a full sack can't go in a bundle or another container
    private static Item registerSack(String name, Block block) {
        return DECORATIVES.register(name,
                (settings) -> new BlockItem(block, settings) {
                    @Override
                    public boolean canBeNested() {
                        return false;
                    }
                }, new Item.Settings().maxCount(1));
    }
}
