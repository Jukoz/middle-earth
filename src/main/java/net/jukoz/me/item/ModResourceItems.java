package net.jukoz.me.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.block.ModNatureBlocks;
import net.jukoz.me.item.items.*;
import net.jukoz.me.item.utils.ModItemGroups;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.TallPlantBlock;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModResourceItems {
    public static final Item MIDDLE_EARTH_MAP = registerItem("middle_earth_map",
            new MiddleEarthMapItem(new FabricItemSettings().maxCount(1)));

    public static final Item EMPTY_PHIAL = registerItem("empty_phial",
            new EmptyPhialItem(new FabricItemSettings()));
    public static final Item WATER_PHIAL = registerItem("water_phial",
            new Item(new FabricItemSettings().maxCount(1)));
    public static final Item STARLIGHT_PHIAL = registerItem("starlight_phial",
            new StarlightPhialItem(new FabricItemSettings().maxCount(1)));

    public static final Item RAW_TIN = registerItem("raw_tin",
            new Item(new FabricItemSettings()));
    public static final Item TIN_INGOT = registerItem("tin_ingot",
            new Item(new FabricItemSettings()));
    public static final Item TIN_NUGGET = registerItem("tin_nugget",
            new Item(new FabricItemSettings()));

    public static final Item RAW_LEAD = registerItem("raw_lead",
            new Item(new FabricItemSettings()));
    public static final Item LEAD_INGOT = registerItem("lead_ingot",
            new Item(new FabricItemSettings()));
    public static final Item LEAD_NUGGET = registerItem("lead_nugget",
            new Item(new FabricItemSettings()));

    public static final Item BRONZE_INGOT = registerItem("bronze_ingot",
            new Item(new FabricItemSettings()));
    public static final Item BRONZE_NUGGET = registerItem("bronze_nugget",
            new Item(new FabricItemSettings()));

    public static final Item RAW_SILVER = registerItem("raw_silver",
            new Item(new FabricItemSettings()));
    public static final Item SILVER_INGOT = registerItem("silver_ingot",
            new Item(new FabricItemSettings()));
    public static final Item SILVER_NUGGET = registerItem("silver_nugget",
            new Item(new FabricItemSettings()));

    public static final Item ORC_STEEL_INGOT = registerItem("orc_steel_ingot",
            new Item(new FabricItemSettings()));
    public static final Item ORC_STEEL_NUGGET = registerItem("orc_steel_nugget",
            new Item(new FabricItemSettings()));

    public static final Item URUK_STEEL_INGOT = registerItem("uruk_steel_ingot",
            new Item(new FabricItemSettings()));
    public static final Item URUK_STEEL_NUGGET = registerItem("uruk_steel_nugget",
            new Item(new FabricItemSettings()));

    public static final Item STEEL_INGOT = registerItem("steel_ingot",
            new Item(new FabricItemSettings()));
    public static final Item STEEL_NUGGET = registerItem("steel_nugget",
            new Item(new FabricItemSettings()));

    public static final Item ELVEN_STEEL_INGOT = registerItem("elven_steel_ingot",
            new Item(new FabricItemSettings()));
    public static final Item ELVEN_STEEL_NUGGET = registerItem("elven_steel_nugget",
            new Item(new FabricItemSettings()));

    public static final Item DWARVEN_STEEL_INGOT = registerItem("dwarven_steel_ingot",
            new Item(new FabricItemSettings()));
    public static final Item DWARVEN_STEEL_NUGGET = registerItem("dwarven_steel_nugget",
            new Item(new FabricItemSettings()));

    public static final Item MORGUL_STEEL_INGOT = registerItem("morgul_steel_ingot",
            new Item(new FabricItemSettings()));

    public static final Item RAW_MITHRIL = registerItem("raw_mithril",
            new Item(new FabricItemSettings().fireproof()));
    public static final Item MITHRIL_INGOT = registerItem("mithril_ingot",
            new Item(new FabricItemSettings().fireproof()));
    public static final Item MITHRIL_NUGGET = registerItem("mithril_nugget",
            new Item(new FabricItemSettings().fireproof()));

    public static final Item COPPER_ROD = registerItem("copper_rod",
            new Item(new FabricItemSettings()));
    public static final Item TIN_ROD = registerItem("tin_rod",
            new Item(new FabricItemSettings()));
    public static final Item LEAD_ROD = registerItem("lead_rod",
            new Item(new FabricItemSettings()));
    public static final Item BRONZE_ROD = registerItem("bronze_rod",
            new Item(new FabricItemSettings()));
    public static final Item SILVER_ROD = registerItem("silver_rod",
            new Item(new FabricItemSettings()));
    public static final Item GOLD_ROD = registerItem("gold_rod",
            new Item(new FabricItemSettings()));
    public static final Item IRON_ROD = registerItem("iron_rod",
            new Item(new FabricItemSettings()));
    public static final Item ORC_STEEL_ROD = registerItem("orc_steel_rod",
            new Item(new FabricItemSettings()));
    public static final Item URUK_STEEL_ROD = registerItem("uruk_steel_rod",
            new Item(new FabricItemSettings()));
    public static final Item STEEL_ROD = registerItem("steel_rod",
            new Item(new FabricItemSettings()));
    public static final Item ELVEN_STEEL_ROD = registerItem("elven_steel_rod",
            new Item(new FabricItemSettings()));
    public static final Item DWARVEN_STEEL_ROD = registerItem("dwarven_steel_rod",
            new Item(new FabricItemSettings()));
    public static final Item MORGUL_STEEL_ROD = registerItem("morgul_steel_rod",
            new Item(new FabricItemSettings()));
    public static final Item MITHRIL_ROD = registerItem("mithril_rod",
            new Item(new FabricItemSettings().fireproof()));

    public static final Item RED_AGATE_SHARD = registerItem("red_agate_shard",
            new Item(new FabricItemSettings()));
    public static final Item SAPPHIRE_SHARD = registerItem("sapphire_shard",
            new Item(new FabricItemSettings()));
    public static final Item CITRINE_SHARD = registerItem("citrine_shard",
            new Item(new FabricItemSettings()));

    public static final Item ORC_BONE = registerItem("orc_bone",
            new Item(new FabricItemSettings()));
    public static final Item WARG_BONE = registerItem("warg_bone",
            new Item(new FabricItemSettings()));

    public static final Item ASH = registerItem("ash",
            new Item(new FabricItemSettings()));

    public static final Item PEBBLE = registerItem("pebble",
            new PebbleItem(new FabricItemSettings()));

    public static final Item STRAW = registerItem("straw",
            new Item(new FabricItemSettings()));
    public static final Block REEDS = registerBlock("reeds",
            new TallPlantBlock(FabricBlockSettings.copyOf(Blocks.TALL_GRASS).strength(0.2f)), false);

    public static final Item DUCK_FEATHER = registerItem("duck_feather",
            new Item(new FabricItemSettings()));
    public static final Item SWAN_FEATHER = registerItem("swan_feather",
            new Item(new FabricItemSettings()));
            
    public static final Item GLOWWORM_BOTTLE = registerItem("glowworm_bottle",
            new GlowwormBottle(new FabricItemSettings().maxCount(1).food(
                    new FoodComponent.Builder().hunger(2).saturationModifier(0.3f).alwaysEdible().build())));

    public static final Item COPPER_COIN = registerItem("copper_coin", new Item(new FabricItemSettings()));
    public static final Item SILVER_COIN = registerItem("silver_coin", new Item(new FabricItemSettings()));


    public static final Item FLAX = registerItem("flax", new Item(new FabricItemSettings()));
    public static final Item FLAX_SEEDS = registerItem("flax_seeds", new AliasedBlockItem(ModNatureBlocks.FLAX_CROP, new FabricItemSettings()));
    public static final Item PIPEWEED = registerItem("pipeweed", new Item(new FabricItemSettings()));
    public static final Item PIPEWEED_SEEDS = registerItem("pipeweed_seeds", new AliasedBlockItem(ModNatureBlocks.PIPEWEED_CROP, new FabricItemSettings()));
    public static final Item TOMATO_SEEDS = registerItem("tomato_seeds", new AliasedBlockItem(ModNatureBlocks.TOMATO_CROP, new FabricItemSettings()));
    public static final Item BELL_PEPPER_SEEDS = registerItem("bell_pepper_seeds", new AliasedBlockItem(ModNatureBlocks.BELL_PEPPER_CROP, new FabricItemSettings()));
    public static final Item CUCUMBER_SEEDS = registerItem("cucumber_seeds", new AliasedBlockItem(ModNatureBlocks.CUCUMBER_CROP, new FabricItemSettings()));
    public static final Item LEEK_SEEDS = registerItem("leek_seeds", new AliasedBlockItem(ModNatureBlocks.LEEK_CROP, new FabricItemSettings()));
    public static final Item LETTUCE_SEEDS = registerItem("lettuce_seeds", new AliasedBlockItem(ModNatureBlocks.LETTUCE_CROP, new FabricItemSettings()));

    public static Block registerBlock(String name, Block block, boolean absent) {
        if(!absent) registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, new Identifier(MiddleEarth.MOD_ID, name), block);
    }
    static Item registerBlockItem(String name, Block block) {
        var item =  Registry.register(Registries.ITEM, new Identifier(MiddleEarth.MOD_ID, name),
                new BlockItem(block, new FabricItemSettings()));

        Item.BLOCK_ITEMS.put(block, item);
        ModItemGroups.RESOURCES_CONTENTS.add(item.getDefaultStack());
        return item;
    }

    private static Item registerItem(String name, Item item) {
        ModItemGroups.RESOURCES_CONTENTS.add(item.getDefaultStack());
        return Registry.register(Registries.ITEM, new Identifier(MiddleEarth.MOD_ID, name), item);
    }

    public static void registerModItems() {
        MiddleEarth.LOGGER.debug("Registering Mod Resource Items for " + MiddleEarth.MOD_ID);
    }
}
