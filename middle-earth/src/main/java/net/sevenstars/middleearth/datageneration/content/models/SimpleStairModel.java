package net.sevenstars.middleearth.datageneration.content.models;

import net.sevenstars.middleearth.block.registration.BlockRegistryME;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;

import java.util.ArrayList;
import java.util.List;

public class SimpleStairModel {
    public record Stair(Block origin, Block stairs) {}
    public static List<Stair> stairs = new ArrayList<>() {
        {
            add(new Stair(BlockRegistryME.MIRE, BlockRegistryME.MIRE_STAIRS));
            add(new Stair(BlockRegistryME.DRY_DIRT, BlockRegistryME.DRY_DIRT_STAIRS));
            add(new Stair(BlockRegistryME.CHALKSOIL, BlockRegistryME.CHALKSOIL_STAIRS));
            add(new Stair(BlockRegistryME.COARSE_CHALKSOIL, BlockRegistryME.COARSE_CHALKSOIL_STAIRS));
            add(new Stair(BlockRegistryME.LOAM, BlockRegistryME.LOAM_STAIRS));
            add(new Stair(BlockRegistryME.COARSE_LOAM, BlockRegistryME.COARSE_LOAM_STAIRS));
            add(new Stair(BlockRegistryME.PEAT, BlockRegistryME.PEAT_STAIRS));
            add(new Stair(BlockRegistryME.COARSE_PEAT, BlockRegistryME.COARSE_PEAT_STAIRS));
            add(new Stair(BlockRegistryME.SILT, BlockRegistryME.SILT_STAIRS));
            add(new Stair(BlockRegistryME.COARSE_SILT, BlockRegistryME.COARSE_SILT_STAIRS));
            add(new Stair(BlockRegistryME.FOUL_DIRT, BlockRegistryME.FOUL_DIRT_STAIRS));
            add(new Stair(BlockRegistryME.DIRTY_ROOTS, BlockRegistryME.DIRTY_ROOTS_STAIRS));
            add(new Stair(BlockRegistryME.ASHEN_DIRT, BlockRegistryME.ASHEN_DIRT_STAIRS));
            add(new Stair(BlockRegistryME.COBBLY_ASHEN_DIRT, BlockRegistryME.COBBLY_ASHEN_DIRT_STAIRS));
            add(new Stair(BlockRegistryME.COBBLY_DIRT, BlockRegistryME.COBBLY_DIRT_STAIRS));
            add(new Stair(BlockRegistryME.SNOWY_DIRT, BlockRegistryME.SNOWY_DIRT_STAIRS));
        }
    };

    public static List<Stair> strippedStairs = new ArrayList<>() {
        {
        }
    };

    public static List<Stair> woodStairs = new ArrayList<>() {
        {
        }
    };

    public static List<Stair> vanillaStairs = new ArrayList<>() {
        {
            add(new Stair(Blocks.DIRT, BlockRegistryME.DIRT_STAIRS));
            add(new Stair(Blocks.MOSS_BLOCK, BlockRegistryME.MOSS_STAIRS));
            add(new Stair(Blocks.ROOTED_DIRT, BlockRegistryME.ROOTED_DIRT_STAIRS));
            add(new Stair(Blocks.COARSE_DIRT, BlockRegistryME.COARSE_DIRT_STAIRS));
            add(new Stair(Blocks.MUD, BlockRegistryME.MUD_STAIRS));

            add(new Stair(Blocks.PACKED_MUD, BlockRegistryME.PACKED_MUD_STAIRS));

            add(new Stair(Blocks.BLACK_WOOL, BlockRegistryME.BLACK_WOOL_STAIRS));
            add(new Stair(Blocks.BLUE_WOOL, BlockRegistryME.BLUE_WOOL_STAIRS));
            add(new Stair(Blocks.BROWN_WOOL, BlockRegistryME.BROWN_WOOL_STAIRS));
            add(new Stair(Blocks.CYAN_WOOL, BlockRegistryME.CYAN_WOOL_STAIRS));
            add(new Stair(Blocks.GRAY_WOOL, BlockRegistryME.GRAY_WOOL_STAIRS));
            add(new Stair(Blocks.GREEN_WOOL, BlockRegistryME.GREEN_WOOL_STAIRS));
            add(new Stair(Blocks.LIGHT_BLUE_WOOL, BlockRegistryME.LIGHT_BLUE_WOOL_STAIRS));
            add(new Stair(Blocks.LIGHT_GRAY_WOOL, BlockRegistryME.LIGHT_GRAY_WOOL_STAIRS));
            add(new Stair(Blocks.LIME_WOOL, BlockRegistryME.LIME_WOOL_STAIRS));
            add(new Stair(Blocks.MAGENTA_WOOL, BlockRegistryME.MAGENTA_WOOL_STAIRS));
            add(new Stair(Blocks.ORANGE_WOOL, BlockRegistryME.ORANGE_WOOL_STAIRS));
            add(new Stair(Blocks.PINK_WOOL, BlockRegistryME.PINK_WOOL_STAIRS));
            add(new Stair(Blocks.PURPLE_WOOL, BlockRegistryME.PURPLE_WOOL_STAIRS));
            add(new Stair(Blocks.RED_WOOL, BlockRegistryME.RED_WOOL_STAIRS));
            add(new Stair(Blocks.WHITE_WOOL, BlockRegistryME.WHITE_WOOL_STAIRS));
            add(new Stair(Blocks.YELLOW_WOOL, BlockRegistryME.YELLOW_WOOL_STAIRS));
        }
    };

    public static List<Stair> vanillaWoodStairs = new ArrayList<>() {
        {
        }
    };

    public static List<Stair> vanillaStrippedStairs = new ArrayList<>() {
        {
        }
    };
}
