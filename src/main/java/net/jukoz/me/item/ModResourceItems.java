package net.jukoz.me.item;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.block.ModNatureBlocks;
import net.jukoz.me.block.special.CustomTallPlantBlock;
import net.jukoz.me.item.items.*;
import net.jukoz.me.item.utils.ModBannerPatternTags;
import net.jukoz.me.item.utils.ModItemGroups;
import net.jukoz.me.utils.LoggerUtil;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;

public class ModResourceItems {
    public static final Item MIDDLE_EARTH_MAP = registerItem("middle_earth_map",
            new MiddleEarthMapItem(new Item.Settings().maxCount(1)));

    public static final Item EMPTY_PHIAL = registerItem("empty_phial",
            new EmptyPhialItem(new Item.Settings()));
    public static final Item WATER_PHIAL = registerItem("water_phial",
            new Item(new Item.Settings().maxCount(1)));
    public static final Item STARLIGHT_PHIAL = registerItem("starlight_phial",
            new StarlightPhialItem(new Item.Settings().maxCount(1)));

    public static final Item RAW_TIN = registerItem("raw_tin",
            new Item(new Item.Settings()));
    public static final Item TIN_INGOT = registerItem("tin_ingot",
            new Item(new Item.Settings()));
    public static final Item TIN_NUGGET = registerItem("tin_nugget",
            new Item(new Item.Settings()));

    public static final Item RAW_LEAD = registerItem("raw_lead",
            new Item(new Item.Settings()));
    public static final Item LEAD_INGOT = registerItem("lead_ingot",
            new Item(new Item.Settings()));
    public static final Item LEAD_NUGGET = registerItem("lead_nugget",
            new Item(new Item.Settings()));

    public static final Item BRONZE_INGOT = registerItem("bronze_ingot",
            new Item(new Item.Settings()));
    public static final Item BRONZE_NUGGET = registerItem("bronze_nugget",
            new Item(new Item.Settings()));

    public static final Item RAW_SILVER = registerItem("raw_silver",
            new Item(new Item.Settings()));
    public static final Item SILVER_INGOT = registerItem("silver_ingot",
            new Item(new Item.Settings()));
    public static final Item SILVER_NUGGET = registerItem("silver_nugget",
            new Item(new Item.Settings()));

    public static final Item ORC_STEEL_INGOT = registerItem("orc_steel_ingot",
            new Item(new Item.Settings()));
    public static final Item ORC_STEEL_NUGGET = registerItem("orc_steel_nugget",
            new Item(new Item.Settings()));

    public static final Item URUK_STEEL_INGOT = registerItem("uruk_steel_ingot",
            new Item(new Item.Settings()));
    public static final Item URUK_STEEL_NUGGET = registerItem("uruk_steel_nugget",
            new Item(new Item.Settings()));

    public static final Item STEEL_INGOT = registerItem("steel_ingot",
            new Item(new Item.Settings()));
    public static final Item STEEL_NUGGET = registerItem("steel_nugget",
            new Item(new Item.Settings()));

    public static final Item ELVEN_STEEL_INGOT = registerItem("elven_steel_ingot",
            new Item(new Item.Settings()));
    public static final Item ELVEN_STEEL_NUGGET = registerItem("elven_steel_nugget",
            new Item(new Item.Settings()));

    public static final Item DWARVEN_STEEL_INGOT = registerItem("dwarven_steel_ingot",
            new Item(new Item.Settings()));
    public static final Item DWARVEN_STEEL_NUGGET = registerItem("dwarven_steel_nugget",
            new Item(new Item.Settings()));

    public static final Item MORGUL_STEEL_INGOT = registerItem("morgul_steel_ingot",
            new Item(new Item.Settings()));
    public static final Item MORGUL_STEEL_NUGGET = registerItem("morgul_steel_nugget",
            new Item(new Item.Settings()));


    public static final Item RAW_MITHRIL = registerItem("raw_mithril",
            new Item(new Item.Settings().fireproof()));
    public static final Item MITHRIL_INGOT = registerItem("mithril_ingot",
            new Item(new Item.Settings().fireproof()));
    public static final Item MITHRIL_NUGGET = registerItem("mithril_nugget",
            new Item(new Item.Settings().fireproof()));

    public static final Item COPPER_ROD = registerItem("copper_rod",
            new Item(new Item.Settings()));
    public static final Item TIN_ROD = registerItem("tin_rod",
            new Item(new Item.Settings()));
    public static final Item LEAD_ROD = registerItem("lead_rod",
            new Item(new Item.Settings()));
    public static final Item BRONZE_ROD = registerItem("bronze_rod",
            new Item(new Item.Settings()));
    public static final Item SILVER_ROD = registerItem("silver_rod",
            new Item(new Item.Settings()));
    public static final Item GOLD_ROD = registerItem("gold_rod",
            new Item(new Item.Settings()));
    public static final Item IRON_ROD = registerItem("iron_rod",
            new Item(new Item.Settings()));
    public static final Item ORC_STEEL_ROD = registerItem("orc_steel_rod",
            new Item(new Item.Settings()));
    public static final Item URUK_STEEL_ROD = registerItem("uruk_steel_rod",
            new Item(new Item.Settings()));
    public static final Item STEEL_ROD = registerItem("steel_rod",
            new Item(new Item.Settings()));
    public static final Item ELVEN_STEEL_ROD = registerItem("elven_steel_rod",
            new Item(new Item.Settings()));
    public static final Item DWARVEN_STEEL_ROD = registerItem("dwarven_steel_rod",
            new Item(new Item.Settings()));
    public static final Item MORGUL_STEEL_ROD = registerItem("morgul_steel_rod",
            new Item(new Item.Settings()));
    public static final Item MITHRIL_ROD = registerItem("mithril_rod",
            new Item(new Item.Settings().fireproof()));

    public static final Item IRON_CHAINMAIL = registerItem("iron_chainmail",
            new Item(new Item.Settings()));

    public static final Item RED_AGATE_SHARD = registerItem("red_agate_shard",
            new Item(new Item.Settings()));
    public static final Item CITRINE_SHARD = registerItem("citrine_shard",
            new Item(new Item.Settings()));
    public static final Item QUARTZ_SHARD = registerItem("quartz_shard",
            new Item(new Item.Settings()));

    public static final Item ORC_BONE = registerItem("orc_bone",
            new Item(new Item.Settings()));
    public static final Item WARG_BONE = registerItem("warg_bone",
            new Item(new Item.Settings()));

    public static final Item ASH = registerItem("ash",
            new Item(new Item.Settings()));

    public static final Item PEBBLE = registerItem("pebble",
            new PebbleItem(new Item.Settings()));
    public static final Item PINECONE = registerItem("pinecone",
            new PineconeItem(new Item.Settings()));
    public static final Item LIT_PINECONE = registerItem("lit_pinecone",
            new LitPineconeItem(new Item.Settings().maxCount(16)));

    public static final Item STRAW = registerItem("straw",
            new Item(new Item.Settings()));
    public static final Block REEDS = registerBlock("reeds",
            new CustomTallPlantBlock(FabricBlockSettings.copyOf(Blocks.TALL_GRASS).breakInstantly(), false), false);

    public static final Item DUCK_FEATHER = registerItem("duck_feather",
            new Item(new Item.Settings()));
    public static final Item SWAN_FEATHER = registerItem("swan_feather",
            new Item(new Item.Settings()));
            
    public static final Item GLOWWORM_BOTTLE = registerItem("glowworm_bottle",
            new GlowwormBottle(new Item.Settings().maxCount(1).food(
                    new FoodComponent.Builder().nutrition(2).saturationModifier(0.3f).alwaysEdible().build()).recipeRemainder(Items.GLASS_BOTTLE)));

    public static final Item COPPER_COIN = registerItem("copper_coin", new Item(new Item.Settings()));
    public static final Item SILVER_COIN = registerItem("silver_coin", new Item(new Item.Settings()));


    public static final Item FLAX = registerItem("flax", new Item(new Item.Settings()));
    public static final Item FLAX_SEEDS = registerItem("flax_seeds", new AliasedBlockItem(ModNatureBlocks.FLAX_CROP, new Item.Settings()));
    public static final Item PIPEWEED = registerItem("pipeweed", new Item(new Item.Settings()));
    public static final Item PIPEWEED_SEEDS = registerItem("pipeweed_seeds", new AliasedBlockItem(ModNatureBlocks.PIPEWEED_CROP, new Item.Settings()));
    public static final Item TOMATO_SEEDS = registerItem("tomato_seeds", new AliasedBlockItem(ModNatureBlocks.TOMATO_CROP, new Item.Settings()));
    public static final Item BELL_PEPPER_SEEDS = registerItem("bell_pepper_seeds", new AliasedBlockItem(ModNatureBlocks.BELL_PEPPER_CROP, new Item.Settings()));
    public static final Item CUCUMBER_SEEDS = registerItem("cucumber_seeds", new AliasedBlockItem(ModNatureBlocks.CUCUMBER_CROP, new Item.Settings()));
    public static final Item LETTUCE_SEEDS = registerItem("lettuce_seeds", new AliasedBlockItem(ModNatureBlocks.LETTUCE_CROP, new Item.Settings()));

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

    public static Block registerBlock(String name, Block block, boolean absent) {
        if(!absent) registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, Identifier.of(MiddleEarth.MOD_ID, name), block);
    }
    static Item registerBlockItem(String name, Block block) {
        var item =  Registry.register(Registries.ITEM, Identifier.of(MiddleEarth.MOD_ID, name),
                new BlockItem(block, new Item.Settings()));

        Item.BLOCK_ITEMS.put(block, item);
        ModItemGroups.RESOURCES_CONTENTS.add(item.getDefaultStack());
        return item;
    }

    private static Item registerItem(String name, Item item) {
        ModItemGroups.RESOURCES_CONTENTS.add(item.getDefaultStack());
        return Registry.register(Registries.ITEM, Identifier.of(MiddleEarth.MOD_ID, name), item);
    }

    public static void registerModItems() {
        LoggerUtil.getInstance().logDebugMsg("Registering Mod Resource Items for " + MiddleEarth.MOD_ID);
    }
}
