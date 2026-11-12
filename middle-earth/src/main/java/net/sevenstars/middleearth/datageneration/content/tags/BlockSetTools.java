package net.sevenstars.middleearth.datageneration.content.tags;

import static net.sevenstars.middleearth.block.registration.GenericBlockSetRegistryME.*;
import net.sevenstars.middleearth.block.utils.setBuilders.GenericBlockSetBuilder;
import net.sevenstars.middleearth.block.utils.setBuilders.SimpleBlockSetBuilder;

import java.util.IdentityHashMap;
import java.util.Map;

public class BlockSetTools {
    private static final Map<GenericBlockSetBuilder, MiningTool> GENERIC_SETS = new IdentityHashMap<>();
    private static final Map<SimpleBlockSetBuilder, MiningTool> SIMPLE_SETS = new IdentityHashMap<>();

    static {
        pickaxe(
                WHITE_DAUB, DARK_DAUB, YELLOW_DAUB, STUCCO,
                PACKED_MIRE, MIRE_BRICKS, FRAMED_DRYSTONE, OLD_BRICKS,
                MIXED_STONES, MOSSY_MIXED_STONES, CRACKED_MIXED_STONES, MIXED_STONES_BRICKWORK,
                CLAY_TILING, BLACK_CLAY_TILING, BLUE_CLAY_TILING, BROWN_CLAY_TILING,
                CYAN_CLAY_TILING, GRAY_CLAY_TILING, GREEN_CLAY_TILING, LIGHT_BLUE_CLAY_TILING,
                LIGHT_GRAY_CLAY_TILING, LIME_CLAY_TILING, MAGENTA_CLAY_TILING, ORANGE_CLAY_TILING,
                PINK_CLAY_TILING, PURPLE_CLAY_TILING, RED_CLAY_TILING, WHITE_CLAY_TILING,
                YELLOW_CLAY_TILING, BLUE_ROOF_TILES, BRIGHT_BLUE_ROOF_TILES, DARK_BLUE_ROOF_TILES,
                LIGHT_BLUE_ROOF_TILES, OFF_BLUE_ROOF_TILES, BROWN_ROOF_TILES, DARK_BROWN_ROOF_TILES,
                OFF_BROWN_ROOF_TILES, CYAN_ROOF_TILES, BRIGHT_CYAN_ROOF_TILES, DARK_CYAN_ROOF_TILES,
                LIGHT_CYAN_ROOF_TILES, OFF_CYAN_ROOF_TILES, GRAY_ROOF_TILES, DARK_GRAY_ROOF_TILES,
                LIGHT_GRAY_ROOF_TILES, OFF_GRAY_ROOF_TILES, GREEN_ROOF_TILES, BRIGHT_GREEN_ROOF_TILES,
                DARK_GREEN_ROOF_TILES, LIGHT_GREEN_ROOF_TILES, OFF_GREEN_ROOF_TILES, RED_ROOF_TILES,
                BRIGHT_RED_ROOF_TILES, DARK_RED_ROOF_TILES, LIGHT_RED_ROOF_TILES, OFF_RED_ROOF_TILES,
                YELLOW_ROOF_TILES, BRIGHT_YELLOW_ROOF_TILES, DARK_YELLOW_ROOF_TILES, LIGHT_YELLOW_ROOF_TILES,
                OFF_YELLOW_ROOF_TILES, CUT_BRONZE, CUT_CRUDE_PLATES, CUT_LEAD,
                CUT_SILVER);

        axe(
                WEATHERED_SHINGLES, TREATED_WOOD, TREATED_WOOD_PLANKS, TREATED_WOOD_BEAM,
                TREATED_WOOD_CARVED_BEAM, TREATED_WOOD_PANELS, TREATED_WOOD_TILING, AGED_WOOD,
                AGED_WOOD_BOARDS, AGED_WOOD_CARVING, AGED_WOOD_PLANKS, AGED_WOOD_BEAM,
                AGED_WOOD_FISH_CARVING, AGED_WOOD_PANELS, AGED_WOOD_SHINGLES, AGED_WOOD_CARVED_BEAM,
                AGED_WOOD_KNOTTED_BEAM, AGED_WOOD_REDDISH_BEAM, AGED_WOOD_GILDED_CARVED_PILLAR, AGED_WOOD_GILDED_CARVING,
                AGED_WOOD_GILDED_HORSES, AGED_WOOD_GILDED_TRIM);

        shovel(
                PLASTER);

        none(
                THATCH, WEATHERED_THATCH, AGED_THATCH, OLD_THATCH,
                ROTTEN_THATCH, WAXED_THATCH, WAXED_WEATHERED_THATCH, WAXED_AGED_THATCH,
                WAXED_OLD_THATCH, WAXED_ROTTEN_THATCH, REED_THATCH, WEATHERED_REED_THATCH,
                AGED_REED_THATCH, OLD_REED_THATCH, ROTTEN_REED_THATCH, WAXED_REED_THATCH,
                WAXED_WEATHERED_REED_THATCH, WAXED_AGED_REED_THATCH, WAXED_OLD_REED_THATCH, WAXED_ROTTEN_REED_THATCH,
                REED, STRAW);

        noneSimple(
                CANVAS, BLACK_CANVAS, BLUE_CANVAS, BROWN_CANVAS,
                CYAN_CANVAS, GRAY_CANVAS, GREEN_CANVAS, LIGHT_BLUE_CANVAS,
                LIGHT_GRAY_CANVAS, MAGENTA_CANVAS, ORANGE_CANVAS, PINK_CANVAS,
                PURPLE_CANVAS, RED_CANVAS, WHITE_CANVAS, YELLOW_CANVAS,
                DARK_BLUE_CANVAS, DARK_BROWN_CANVAS, DARK_GRAY_CANVAS, DARK_GREEN_CANVAS,
                DARK_RED_CANVAS, DARK_YELLOW_CANVAS);
    }

    public static MiningTool of(GenericBlockSetBuilder set) {
        MiningTool tool = GENERIC_SETS.get(set);
        if (tool == null) throw new IllegalStateException("No mining tool declared for block set " + set.setName);
        return tool;
    }

    public static MiningTool of(SimpleBlockSetBuilder set) {
        MiningTool tool = SIMPLE_SETS.get(set);
        if (tool == null) throw new IllegalStateException("No mining tool declared for block set " + set.setName);
        return tool;
    }

    private static void pickaxe(GenericBlockSetBuilder... sets) {
        put(MiningTool.PICKAXE, sets);
    }

    private static void axe(GenericBlockSetBuilder... sets) {
        put(MiningTool.AXE, sets);
    }

    private static void shovel(GenericBlockSetBuilder... sets) {
        put(MiningTool.SHOVEL, sets);
    }

    private static void none(GenericBlockSetBuilder... sets) {
        put(MiningTool.NONE, sets);
    }

    private static void noneSimple(SimpleBlockSetBuilder... sets) {
        for (SimpleBlockSetBuilder set : sets) {
            if (SIMPLE_SETS.put(set, MiningTool.NONE) != null) throw new IllegalStateException("Block set declared twice: " + set.setName);
        }
    }

    private static void put(MiningTool tool, GenericBlockSetBuilder[] sets) {
        for (GenericBlockSetBuilder set : sets) {
            if (GENERIC_SETS.put(set, tool) != null) throw new IllegalStateException("Block set declared twice: " + set.setName);
        }
    }
}
