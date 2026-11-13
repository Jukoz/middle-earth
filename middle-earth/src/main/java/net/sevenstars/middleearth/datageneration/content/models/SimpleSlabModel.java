package net.sevenstars.middleearth.datageneration.content.models;

import net.sevenstars.middleearth.block.registration.BlockRegistryME;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;

import java.util.ArrayList;
import java.util.List;

public class SimpleSlabModel {
    public record Slab(Block origin, Block slab) {}
    public static List<Slab> slabs = new ArrayList<>() {
        {
            add(new Slab(BlockRegistryME.MIRE, BlockRegistryME.MIRE_SLAB));
            add(new Slab(BlockRegistryME.DRY_DIRT, BlockRegistryME.DRY_DIRT_SLAB));
            add(new Slab(BlockRegistryME.CHALKSOIL, BlockRegistryME.CHALKSOIL_SLAB));
            add(new Slab(BlockRegistryME.COARSE_CHALKSOIL, BlockRegistryME.COARSE_CHALKSOIL_SLAB));
            add(new Slab(BlockRegistryME.LOAM, BlockRegistryME.LOAM_SLAB));
            add(new Slab(BlockRegistryME.COARSE_LOAM, BlockRegistryME.COARSE_LOAM_SLAB));
            add(new Slab(BlockRegistryME.PEAT, BlockRegistryME.PEAT_SLAB));
            add(new Slab(BlockRegistryME.COARSE_PEAT, BlockRegistryME.COARSE_PEAT_SLAB));
            add(new Slab(BlockRegistryME.SILT, BlockRegistryME.SILT_SLAB));
            add(new Slab(BlockRegistryME.COARSE_SILT, BlockRegistryME.COARSE_SILT_SLAB));
            add(new Slab(BlockRegistryME.FOUL_DIRT, BlockRegistryME.FOUL_DIRT_SLAB));
            add(new Slab(BlockRegistryME.DIRTY_ROOTS, BlockRegistryME.DIRTY_ROOTS_SLAB));
            add(new Slab(BlockRegistryME.ASHEN_DIRT, BlockRegistryME.ASHEN_DIRT_SLAB));
            add(new Slab(BlockRegistryME.COBBLY_ASHEN_DIRT, BlockRegistryME.COBBLY_ASHEN_DIRT_SLAB));
            add(new Slab(BlockRegistryME.COBBLY_DIRT, BlockRegistryME.COBBLY_DIRT_SLAB));
            add(new Slab(BlockRegistryME.SNOWY_DIRT, BlockRegistryME.SNOWY_DIRT_SLAB));
        }
    };

    public static List<Slab> woodSlabs = new ArrayList<>() {
        {

        }
    };

    public static List<Slab> strippedSlabs = new ArrayList<>() {
        {

        }
    };

    public static List<Slab> vanillaWoodSlabs = new ArrayList<>() {
        {
        }
    };

    public static List<Slab> vanillaStrippedSlab = new ArrayList<>() {
        {
        }
    };

    public static List<Slab> vanillaSlabs = new ArrayList<>() {
        {
            add(new Slab(Blocks.DIRT, BlockRegistryME.DIRT_SLAB));
            add(new Slab(Blocks.COARSE_DIRT, BlockRegistryME.COARSE_DIRT_SLAB));
            add(new Slab(Blocks.ROOTED_DIRT, BlockRegistryME.ROOTED_DIRT_SLAB));
            add(new Slab(Blocks.MUD, BlockRegistryME.MUD_SLAB));
            add(new Slab(Blocks.MOSS_BLOCK, BlockRegistryME.MOSS_SLAB));

            add(new Slab(Blocks.PACKED_MUD, BlockRegistryME.PACKED_MUD_SLAB));

            add(new Slab(Blocks.BLACK_WOOL, BlockRegistryME.BLACK_WOOL_SLAB));
            add(new Slab(Blocks.BLUE_WOOL, BlockRegistryME.BLUE_WOOL_SLAB));
            add(new Slab(Blocks.BROWN_WOOL, BlockRegistryME.BROWN_WOOL_SLAB));
            add(new Slab(Blocks.CYAN_WOOL, BlockRegistryME.CYAN_WOOL_SLAB));
            add(new Slab(Blocks.GRAY_WOOL, BlockRegistryME.GRAY_WOOL_SLAB));
            add(new Slab(Blocks.GREEN_WOOL, BlockRegistryME.GREEN_WOOL_SLAB));
            add(new Slab(Blocks.LIGHT_BLUE_WOOL, BlockRegistryME.LIGHT_BLUE_WOOL_SLAB));
            add(new Slab(Blocks.LIGHT_GRAY_WOOL, BlockRegistryME.LIGHT_GRAY_WOOL_SLAB));
            add(new Slab(Blocks.LIME_WOOL, BlockRegistryME.LIME_WOOL_SLAB));
            add(new Slab(Blocks.MAGENTA_WOOL, BlockRegistryME.MAGENTA_WOOL_SLAB));
            add(new Slab(Blocks.ORANGE_WOOL, BlockRegistryME.ORANGE_WOOL_SLAB));
            add(new Slab(Blocks.PINK_WOOL, BlockRegistryME.PINK_WOOL_SLAB));
            add(new Slab(Blocks.PURPLE_WOOL, BlockRegistryME.PURPLE_WOOL_SLAB));
            add(new Slab(Blocks.RED_WOOL, BlockRegistryME.RED_WOOL_SLAB));
            add(new Slab(Blocks.WHITE_WOOL, BlockRegistryME.WHITE_WOOL_SLAB));
            add(new Slab(Blocks.YELLOW_WOOL, BlockRegistryME.YELLOW_WOOL_SLAB));
        }
    };
}
