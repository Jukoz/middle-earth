package net.sevenstars.middleearth.datageneration.content.tags;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.sevenstars.middleearth.block.registration.ModBlocks;
import net.sevenstars.middleearth.block.registration.ModDecorativeBlocks;
import net.sevenstars.middleearth.block.registration.ModNatureBlocks;
import net.sevenstars.middleearth.world.features.tree.ModTreeConfiguredFeatures;

import java.util.ArrayList;
import java.util.List;

public class BlockTagCollectionsME {
    public static final List<Block> BUTTONS = new ArrayList<>();
    public static final List<Block> CROPS = new ArrayList<>(List.of(
            ModNatureBlocks.TOMATO_CROP,
            ModNatureBlocks.BELL_PEPPER_CROP,
            ModNatureBlocks.CUCUMBER_CROP,
            ModNatureBlocks.FLAX_CROP,
            ModNatureBlocks.GARLIC_CROP,
            ModNatureBlocks.LEEK_CROP,
            ModNatureBlocks.LETTUCE_CROP,
            ModNatureBlocks.ONION_CROP,
            ModNatureBlocks.PIPEWEED_CROP
    ));
    public static final List<Block> DOORS = new ArrayList<>(List.of(
            ModBlocks.AGED_WOOD_DOOR,
            ModBlocks.BRONZE_DOOR,
            ModBlocks.CRUDE_DOOR,
            ModBlocks.TREATED_STEEL_DOOR
    ));
    public static final List<Block> FENCE_GATES = new ArrayList<>();
    public static final List<Block> FENCES = new ArrayList<>(List.of(
            ModBlocks.TREATED_WOOD_ROPE_FENCE
    ));
    public static final List<Block> LEAVES = new ArrayList<>(List.of(
            ModNatureBlocks.LEBETHRON_LEAVES,
            ModNatureBlocks.BERRY_HOLLY_LEAVES,
            ModNatureBlocks.DRY_LARCH_LEAVES,
            ModNatureBlocks.FLOWERING_MALLORN_LEAVES,
            ModNatureBlocks.ORANGE_MAPLE_LEAVES,
            ModNatureBlocks.RED_MAPLE_LEAVES,
            ModNatureBlocks.YELLOW_MAPLE_LEAVES,
            ModNatureBlocks.DRY_PINE_LEAVES
    ));
    public static final List<Block> GRAYSCALE_LEAVES = new ArrayList<>();
    public static final List<Block> LOGS = new ArrayList<>(List.of(
            ModNatureBlocks.PINE_BRANCHES
    ));
    public static final List<Block> LOGS_THAT_BURN = new ArrayList<>();
    public static final List<Block> STRIPPED_LOGS = new ArrayList<>();
    public static final List<Block> PLANKS = new ArrayList<>();
    public static final List<Block> PRESSURE_PLATES = new ArrayList<>();
    public static final List<Block> SAPLINGS = new ArrayList<>(List.of(
            ModNatureBlocks.ASPEN_SAPLING,
            ModNatureBlocks.BEECH_SAPLING,
            ModNatureBlocks.CHESTNUT_SAPLING,
            ModNatureBlocks.HOLLY_SAPLING,
            ModNatureBlocks.FIR_SAPLING,
            ModNatureBlocks.LARCH_SAPLING,
            // ModNatureBlocks.LEBETHRON_SAPLING, // variant
            ModNatureBlocks.WHITE_LEBETHRON_SAPLING,
            ModNatureBlocks.MALLORN_SAPLING,
            // ModNatureBlocks.MAPLE_SAPLING, // variant
            // ModNatureBlocks.SILVER_MAPLE_SAPLING, // variant
            ModNatureBlocks.MIRKWOOD_SAPLING,
            // ModNatureBlocks.PALM_SAPLING, // variant
            ModNatureBlocks.WHITE_PALM_SAPLING,
            ModNatureBlocks.PINE_SAPLING,
            ModNatureBlocks.BLACK_PINE_SAPLING,
            ModNatureBlocks.WHITE_SPRUCE_SAPLING,
            ModNatureBlocks.WILLOW_SAPLING
    ));
    public static final List<Block> shingles = new ArrayList<>();
    public static final List<Block> STONES = new ArrayList<>();
    public static final List<Block> TRAPDOORS = new ArrayList<>();
    public static final List<Block> WALLS = new ArrayList<>();
    public static final List<Block> WOODEN_SLABS = new ArrayList<>();
    public static final List<Block> WOODEN_VERTICAL_SLABS = new ArrayList<>();
    public static final List<Block> WOOL = new ArrayList<>(List.of(
            ModBlocks.WHITE_WOOL_SLAB,
            ModBlocks.ORANGE_WOOL_SLAB,
            ModBlocks.MAGENTA_WOOL_SLAB,
            ModBlocks.LIGHT_BLUE_WOOL_SLAB,
            ModBlocks.YELLOW_WOOL_SLAB,
            ModBlocks.LIME_WOOL_SLAB,
            ModBlocks.PINK_WOOL_SLAB,
            ModBlocks.GRAY_WOOL_SLAB,
            ModBlocks.LIGHT_GRAY_WOOL_SLAB,
            ModBlocks.CYAN_WOOL_SLAB,
            ModBlocks.PURPLE_WOOL_SLAB,
            ModBlocks.BLUE_WOOL_SLAB,
            ModBlocks.BROWN_WOOL_SLAB,
            ModBlocks.GREEN_WOOL_SLAB,
            ModBlocks.RED_WOOL_SLAB,
            ModBlocks.BLACK_WOOL_SLAB,

            ModBlocks.WHITE_WOOL_VERTICAL_SLAB,
            ModBlocks.ORANGE_WOOL_VERTICAL_SLAB,
            ModBlocks.MAGENTA_WOOL_VERTICAL_SLAB,
            ModBlocks.LIGHT_BLUE_WOOL_VERTICAL_SLAB,
            ModBlocks.YELLOW_WOOL_VERTICAL_SLAB,
            ModBlocks.LIME_WOOL_VERTICAL_SLAB,
            ModBlocks.PINK_WOOL_VERTICAL_SLAB,
            ModBlocks.GRAY_WOOL_VERTICAL_SLAB,
            ModBlocks.LIGHT_GRAY_WOOL_VERTICAL_SLAB,
            ModBlocks.CYAN_WOOL_VERTICAL_SLAB,
            ModBlocks.PURPLE_WOOL_VERTICAL_SLAB,
            ModBlocks.BLUE_WOOL_VERTICAL_SLAB,
            ModBlocks.BROWN_WOOL_VERTICAL_SLAB,
            ModBlocks.GREEN_WOOL_VERTICAL_SLAB,
            ModBlocks.RED_WOOL_VERTICAL_SLAB,
            ModBlocks.BLACK_WOOL_VERTICAL_SLAB,

            ModBlocks.WHITE_WOOL_STAIRS,
            ModBlocks.ORANGE_WOOL_STAIRS,
            ModBlocks.MAGENTA_WOOL_STAIRS,
            ModBlocks.LIGHT_BLUE_WOOL_STAIRS,
            ModBlocks.YELLOW_WOOL_STAIRS,
            ModBlocks.LIME_WOOL_STAIRS,
            ModBlocks.PINK_WOOL_STAIRS,
            ModBlocks.GRAY_WOOL_STAIRS,
            ModBlocks.LIGHT_GRAY_WOOL_STAIRS,
            ModBlocks.CYAN_WOOL_STAIRS,
            ModBlocks.PURPLE_WOOL_STAIRS,
            ModBlocks.BLUE_WOOL_STAIRS,
            ModBlocks.BROWN_WOOL_STAIRS,
            ModBlocks.GREEN_WOOL_STAIRS,
            ModBlocks.RED_WOOL_STAIRS,
            ModBlocks.BLACK_WOOL_STAIRS,

            ModDecorativeBlocks.SMALL_BLACK_CURTAIN,
            ModDecorativeBlocks.SMALL_BLUE_CURTAIN,
            ModDecorativeBlocks.SMALL_BROWN_CURTAIN,
            ModDecorativeBlocks.SMALL_BURNT_CURTAIN,
            ModDecorativeBlocks.SMALL_DARK_BLUE_CURTAIN,
            ModDecorativeBlocks.SMALL_DARK_BROWN_CURTAIN,
            ModDecorativeBlocks.SMALL_DARK_GREEN_CURTAIN,
            ModDecorativeBlocks.SMALL_DARK_RED_CURTAIN,
            ModDecorativeBlocks.SMALL_FANCY_BLUE_CURTAIN,
            ModDecorativeBlocks.SMALL_FANCY_GREEN_CURTAIN,
            ModDecorativeBlocks.SMALL_FANCY_RED_CURTAIN,
            ModDecorativeBlocks.SMALL_GRAY_CURTAIN,
            ModDecorativeBlocks.SMALL_GREEN_CURTAIN,
            ModDecorativeBlocks.SMALL_PURPLE_CURTAIN,
            ModDecorativeBlocks.SMALL_RED_CURTAIN,
            ModDecorativeBlocks.SMALL_ROTTEN_CURTAIN,
            ModDecorativeBlocks.SMALL_WHITE_CURTAIN,
            ModDecorativeBlocks.SMALL_YELLOW_CURTAIN,

            ModDecorativeBlocks.BLACK_CURTAIN,
            ModDecorativeBlocks.BLUE_CURTAIN,
            ModDecorativeBlocks.BROWN_CURTAIN,
            ModDecorativeBlocks.BURNT_CURTAIN,
            ModDecorativeBlocks.DARK_BLUE_CURTAIN,
            ModDecorativeBlocks.DARK_BROWN_CURTAIN,
            ModDecorativeBlocks.DARK_GREEN_CURTAIN,
            ModDecorativeBlocks.DARK_RED_CURTAIN,
            ModDecorativeBlocks.FANCY_BLUE_CURTAIN,
            ModDecorativeBlocks.FANCY_GREEN_CURTAIN,
            ModDecorativeBlocks.FANCY_RED_CURTAIN,
            ModDecorativeBlocks.GRAY_CURTAIN,
            ModDecorativeBlocks.GREEN_CURTAIN,
            ModDecorativeBlocks.PURPLE_CURTAIN,
            ModDecorativeBlocks.RED_CURTAIN,
            ModDecorativeBlocks.ROTTEN_CURTAIN,
            ModDecorativeBlocks.WHITE_CURTAIN,
            ModDecorativeBlocks.YELLOW_CURTAIN
    ));

    public static List<Item> getItemPlanks() {
        return PLANKS.stream().map(Block::asItem).toList();
    }
    public static List<Item> getItemLogs() {
        return LOGS.stream().map(Block::asItem).toList();
    }
}
