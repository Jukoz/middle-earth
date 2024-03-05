package net.jukoz.me.item;

import net.jukoz.me.MiddleEarth;
import net.jukoz.me.block.ModDecorativeBlocks;
import net.jukoz.me.item.utils.ModBannerPatternTags;
import net.jukoz.me.item.utils.ModItemGroups;
import net.jukoz.me.item.utils.ModVerticallyAttachableBlockItem;
import net.minecraft.item.BannerPatternItem;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.math.Direction;

public class ModDecorativeItems {
    public static final Item DWARVEN_LANTERN = registerItem("dwarven_lantern",
            new ModVerticallyAttachableBlockItem(ModDecorativeBlocks.DWARVEN_LANTERN, ModDecorativeBlocks.WALL_DWARVEN_LANTERN, new Item.Settings(), new Direction[]{Direction.DOWN, Direction.UP}));
    public static final Item SILVER_LANTERN = registerItem("silver_lantern",
            new ModVerticallyAttachableBlockItem(ModDecorativeBlocks.SILVER_LANTERN, ModDecorativeBlocks.WALL_SILVER_LANTERN, new Item.Settings(), new Direction[]{Direction.DOWN, Direction.UP}));
    public static final Item ALLOY_FURNACE = registerItem("alloy_furnace",
            new BlockItem(ModDecorativeBlocks.ALLOY_FURNACE, new Item.Settings()));
    public static final Item WOOD_PILE = registerItem("wood_pile",
            new BlockItem(ModDecorativeBlocks.WOOD_PILE, new Item.Settings()));

    public static final Item GONDOR_BANNER_PATTERN = registerItem("gondor_banner_pattern",
            new BannerPatternItem(ModBannerPatternTags.GONDOR_PATTERN_ITEM, new Item.Settings().maxCount(1).rarity(Rarity.RARE)));
    public static final Item ROHAN_BANNER_PATTERN = registerItem("rohan_banner_pattern",
            new BannerPatternItem(ModBannerPatternTags.ROHAN_PATTERN_ITEM, new Item.Settings().maxCount(1).rarity(Rarity.RARE)));
    public static final Item LONGBEARD_BANNER_PATTERN = registerItem("longbeard_banner_pattern",
            new BannerPatternItem(ModBannerPatternTags.LONGBEARD_PATTERN_ITEM, new Item.Settings().maxCount(1).rarity(Rarity.RARE)));
    public static final Item LOTHLORIEN_BANNER_PATTERN = registerItem("lothlorien_banner_pattern",
            new BannerPatternItem(ModBannerPatternTags.LOTHLORIEN_PATTERN_ITEM, new Item.Settings().maxCount(1).rarity(Rarity.RARE)));
    public static final Item MORDOR_GREAT_EYE_BANNER_PATTERN = registerItem("mordor_great_eye_banner_pattern",
            new BannerPatternItem(ModBannerPatternTags.MORDOR_GREAT_EYE_PATTERN_ITEM, new Item.Settings().maxCount(1).rarity(Rarity.RARE)));
    public static final Item MORDOR_EYE_BANNER_PATTERN = registerItem("mordor_eye_banner_pattern",
            new BannerPatternItem(ModBannerPatternTags.MORDOR_EYE_PATTERN_ITEM, new Item.Settings().maxCount(1).rarity(Rarity.RARE)));
    public static final Item MISTY_ORCS_EYE_BANNER_PATTERN = registerItem("misty_orcs_eye_banner_pattern",
            new BannerPatternItem(ModBannerPatternTags.MISTY_ORCS_EYE_PATTERN_ITEM, new Item.Settings().maxCount(1).rarity(Rarity.RARE)));
    public static final Item MISTY_ORCS_PEAKS_BANNER_PATTERN = registerItem("misty_orcs_peaks_banner_pattern",
            new BannerPatternItem(ModBannerPatternTags.MISTY_ORCS_PEAKS_PATTERN_ITEM, new Item.Settings().maxCount(1).rarity(Rarity.RARE)));
    

    private static Item registerItem(String name, Item item) {
        ModItemGroups.DECORATIVES_BLOCKS_CONTENT.add(item.getDefaultStack());
        return Registry.register(Registries.ITEM, new Identifier(MiddleEarth.MOD_ID, name), item);
    }

    public static void registerModItems() {
        MiddleEarth.LOGGER.debug("Registering Mod Decorative Items for " + MiddleEarth.MOD_ID);
    }
}
