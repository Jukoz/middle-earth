package net.jukoz.me.datageneration.content.models;

import net.jukoz.me.block.ModBlocks;
import net.jukoz.me.block.ModNatureBlocks;
import net.jukoz.me.block.StoneBlockSets;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;

import java.util.ArrayList;
import java.util.List;

public class SimpleBlockModel {

    public record ChiseledBlock(Block base, Block origin) {}

    public record ChiseledPolishedBlock(Block base, Block origin) {}

    public record CobbleableStoneBlock(Block base, Block origin) {}


    public static List<Block> blocks = new ArrayList<>() {
        {
            add(ModBlocks.DRY_DIRT);
            add(ModBlocks.ASHEN_DIRT);

            //METALS
            add(ModBlocks.RAW_MITHRIL_BLOCK);
            add(ModBlocks.MITHRIL_BLOCK);
            add(ModBlocks.RAW_TIN_BLOCK);
            add(ModBlocks.TIN_BLOCK);
            add(ModBlocks.RAW_LEAD_BLOCK);
            add(ModBlocks.LEAD_BLOCK);
            add(ModBlocks.RAW_SILVER_BLOCK);
            add(ModBlocks.SILVER_BLOCK);
            add(ModBlocks.CUT_LEAD);
            add(ModBlocks.CUT_SILVER);

            //GEMS
            add(ModBlocks.QUARTZ_BLOCK);
            add(ModBlocks.BUDDING_QUARTZ);
            add(ModBlocks.RED_AGATE_BLOCK);
            add(ModBlocks.BUDDING_RED_AGATE);
            add(ModBlocks.CITRINE_BLOCK);
            add(ModBlocks.BUDDING_CITRINE);
            add(ModBlocks.GLOWSTONE_BLOCK);
            add(ModBlocks.BUDDING_GLOWSTONE);

            add(ModBlocks.RIVER_SAND);
            add(ModBlocks.ASH_BLOCK);
        }
    };

    public static List<Block> cobbleableStoneBlocks = new ArrayList<>() {
        {

        }
    };

    public static List<ChiseledBlock> chiseledBlocks = new ArrayList<>() {
        {
            add(new ChiseledBlock(ModBlocks.CHISELED_GONLUIN_BRICKS, StoneBlockSets.GONLUIN_BRICKS.slab()));
            add(new ChiseledBlock(ModBlocks.CHISELED_CALCITE_BRICKS, StoneBlockSets.CALCITE_BRICKS.slab()));
            add(new ChiseledBlock(ModBlocks.CHISELED_LIMESTONE_BRICKS, StoneBlockSets.LIMESTONE_BRICKS.slab()));
            add(new ChiseledBlock(ModBlocks.CHISELED_ANDESITE_BRICKS, StoneBlockSets.ANDESITE_BRICKS.slab()));
            add(new ChiseledBlock(ModBlocks.CHISELED_GRANITE_BRICKS, StoneBlockSets.GRANITE_BRICKS.slab()));
            add(new ChiseledBlock(ModBlocks.CHISELED_DIORITE_BRICKS, StoneBlockSets.DIORITE_BRICKS.slab()));
            add(new ChiseledBlock(ModBlocks.CHISELED_BASALT_BRICKS, StoneBlockSets.BASALT_BRICKS.slab()));
            add(new ChiseledBlock(ModBlocks.CHISELED_DIFTOMIN_BRICKS, StoneBlockSets.DIFTOMIN_BRICKS.slab()));
        }
    };

    public static List<ChiseledBlock> chiseledBlocksTopBottom = new ArrayList<>() {
        {
            add(new ChiseledBlock(ModBlocks.CHISELED_BLUE_TUFF_BRICKS, StoneBlockSets.BLUE_TUFF_BRICKS.slab()));
        }
    };

    public static List<ChiseledPolishedBlock> chiseledPolishedBlocks = new ArrayList<>() {
        {
            add(new ChiseledPolishedBlock(ModBlocks.CHISELED_POLISHED_GONLUIN, StoneBlockSets.POLISHED_GONLUIN.slab()));
            add(new ChiseledPolishedBlock(ModBlocks.CHISELED_POLISHED_BLUE_TUFF, StoneBlockSets.POLISHED_BLUE_TUFF.slab()));
            add(new ChiseledPolishedBlock(ModBlocks.CHISELED_POLISHED_CALCITE, StoneBlockSets.POLISHED_CALCITE.slab()));
            add(new ChiseledPolishedBlock(ModBlocks.CHISELED_POLISHED_DIFTOMIN, StoneBlockSets.POLISHED_DIFTOMIN.slab()));
            add(new ChiseledPolishedBlock(ModBlocks.CHISELED_POLISHED_LIMESTONE, StoneBlockSets.POLISHED_LIMESTONE.slab()));
            add(new ChiseledPolishedBlock(ModBlocks.CHISELED_POLISHED_ANDESITE, Blocks.POLISHED_ANDESITE_SLAB));
            add(new ChiseledPolishedBlock(ModBlocks.CHISELED_SMOOTH_STONE, Blocks.SMOOTH_STONE_SLAB));
            add(new ChiseledPolishedBlock(ModBlocks.CHISELED_POLISHED_GRANITE, Blocks.POLISHED_GRANITE_SLAB));
            add(new ChiseledPolishedBlock(ModBlocks.CHISELED_POLISHED_DIORITE, Blocks.POLISHED_DIORITE_SLAB));
        }
    };

    public static List<Block> woodBlocks = new ArrayList<>() {
        {

        }
    };
}
