package net.jukoz.me.datageneration.content.models;

import net.jukoz.me.block.ModBlocks;
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
            add(ModBlocks.COBBLY_DIRT);

            add(ModBlocks.DRY_DIRT);

            add(ModBlocks.ASHEN_DIRT);

            add(ModBlocks.ASHEN_SAND);

            add(ModBlocks.ASHEN_GRAVEL);

            add(ModBlocks.COBBLY_ASHEN_DIRT);

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

            add(ModBlocks.BRONZE_BLOCK);
            add(ModBlocks.SLAG_BLOCK);
            add(ModBlocks.STEEL_BLOCK);
            add(ModBlocks.DWARVEN_STEEL_BLOCK);
            add(ModBlocks.ELVEN_STEEL_BLOCK);
            add(ModBlocks.URUK_STEEL_BLOCK);

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
            add(ModBlocks.BLACK_SAND);
            add(ModBlocks.WHITE_SAND);
            add(ModBlocks.ASH_BLOCK);

            add(ModBlocks.WATTLE_AND_BRICK);
            add(ModBlocks.WATTLE_AND_BRICK_CROSS);
            add(ModBlocks.WATTLE_AND_BRICK_RIGHT);
            add(ModBlocks.WATTLE_AND_BRICK_LEFT);
            add(ModBlocks.WATTLE_AND_BRICK_PILLAR);
            add(ModBlocks.WATTLE_AND_BRICK_DIAMOND);

            add(ModBlocks.WATTLE_AND_WHITE_DAUB);
            add(ModBlocks.WATTLE_AND_WHITE_DAUB_CROSS);
            add(ModBlocks.WATTLE_AND_WHITE_DAUB_RIGHT);
            add(ModBlocks.WATTLE_AND_WHITE_DAUB_LEFT);
            add(ModBlocks.WATTLE_AND_WHITE_DAUB_PILLAR);
            add(ModBlocks.WATTLE_AND_WHITE_DAUB_DIAMOND);

            add(ModBlocks.BLACK_WATTLE_AND_WHITE_DAUB);
            add(ModBlocks.BLACK_WATTLE_AND_WHITE_DAUB_CROSS);
            add(ModBlocks.BLACK_WATTLE_AND_WHITE_DAUB_RIGHT);
            add(ModBlocks.BLACK_WATTLE_AND_WHITE_DAUB_LEFT);
            add(ModBlocks.BLACK_WATTLE_AND_WHITE_DAUB_PILLAR);
            add(ModBlocks.BLACK_WATTLE_AND_WHITE_DAUB_DIAMOND);

            add(ModBlocks.GREEN_WATTLE_AND_WHITE_DAUB);
            add(ModBlocks.GREEN_WATTLE_AND_WHITE_DAUB_CROSS);
            add(ModBlocks.GREEN_WATTLE_AND_WHITE_DAUB_RIGHT);
            add(ModBlocks.GREEN_WATTLE_AND_WHITE_DAUB_LEFT);
            add(ModBlocks.GREEN_WATTLE_AND_WHITE_DAUB_PILLAR);
            add(ModBlocks.GREEN_WATTLE_AND_WHITE_DAUB_DIAMOND);

            add(ModBlocks.RED_WATTLE_AND_WHITE_DAUB);
            add(ModBlocks.RED_WATTLE_AND_WHITE_DAUB_CROSS);
            add(ModBlocks.RED_WATTLE_AND_WHITE_DAUB_RIGHT);
            add(ModBlocks.RED_WATTLE_AND_WHITE_DAUB_LEFT);
            add(ModBlocks.RED_WATTLE_AND_WHITE_DAUB_PILLAR);
            add(ModBlocks.RED_WATTLE_AND_WHITE_DAUB_DIAMOND);

            add(ModBlocks.DARK_WATTLE_AND_DARK_DAUB);
            add(ModBlocks.DARK_WATTLE_AND_DARK_DAUB_CROSS);
            add(ModBlocks.DARK_WATTLE_AND_DARK_DAUB_RIGHT);
            add(ModBlocks.DARK_WATTLE_AND_DARK_DAUB_LEFT);
            add(ModBlocks.DARK_WATTLE_AND_DARK_DAUB_PILLAR);
            add(ModBlocks.DARK_WATTLE_AND_DARK_DAUB_DIAMOND);

            add(ModBlocks.WATTLE_AND_YELLOW_DAUB);
            add(ModBlocks.WATTLE_AND_YELLOW_DAUB_CROSS);
            add(ModBlocks.WATTLE_AND_YELLOW_DAUB_RIGHT);
            add(ModBlocks.WATTLE_AND_YELLOW_DAUB_LEFT);
            add(ModBlocks.WATTLE_AND_YELLOW_DAUB_PILLAR);
            add(ModBlocks.WATTLE_AND_YELLOW_DAUB_DIAMOND);
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
            add(new ChiseledBlock(ModBlocks.CHISELED_GRANITE_BRICKS, StoneBlockSets.GRANITE_BRICKS.slab()));
            add(new ChiseledBlock(ModBlocks.CHISELED_BASALT_BRICKS, StoneBlockSets.BASALT_BRICKS.slab()));
            add(new ChiseledBlock(ModBlocks.CHISELED_NURGON_BRICKS, StoneBlockSets.NURGON_BRICKS.slab()));
        }
    };

    public static List<ChiseledBlock> chiseledMainBlockTopBottom = new ArrayList<>() {
        {
            add(new ChiseledBlock(ModBlocks.CHISELED_GONLUIN, StoneBlockSets.GONLUIN.slab()));
            add(new ChiseledBlock(ModBlocks.CHISELED_MEDGON, StoneBlockSets.MEDGON.slab()));
            add(new ChiseledBlock(ModBlocks.CHISELED_IRONSTONE, StoneBlockSets.IRONSTONE.slab()));
            add(new ChiseledBlock(ModBlocks.CHISELED_HEMATITE, StoneBlockSets.HEMATITE.slab()));
            add(new ChiseledBlock(ModBlocks.CHISELED_GNEISS, StoneBlockSets.GNEISS.slab()));
            add(new ChiseledBlock(ModBlocks.CHISELED_ZIGILABAD, StoneBlockSets.ZIGILABAD.slab()));
        }
    };

    public static List<ChiseledBlock> chiseledBlocksTopBottom = new ArrayList<>() {
        {
            add(new ChiseledBlock(ModBlocks.CHISELED_ASHEN_BRICKS, StoneBlockSets.ASHEN_BRICKS.slab()));
            add(new ChiseledBlock(ModBlocks.CHISELED_BLUE_TUFF_BRICKS, StoneBlockSets.BLUE_TUFF_BRICKS.slab()));
            add(new ChiseledBlock(ModBlocks.CHISELED_ANDESITE_BRICKS, StoneBlockSets.ANDESITE_BRICKS.slab()));
            add(new ChiseledBlock(ModBlocks.CHISELED_DIORITE_BRICKS, StoneBlockSets.DIORITE_BRICKS.slab()));
            add(new ChiseledBlock(ModBlocks.CHISELED_DOLOMITE_BRICKS, StoneBlockSets.DOLOMITE_BRICKS.slab()));
            add(new ChiseledBlock(ModBlocks.CHISELED_IRONSTONE_BRICKS, StoneBlockSets.IRONSTONE_BRICKS.slab()));
            //TODO : Waiting for texture
            //add(new ChiseledBlock(ModBlocks.CHISELED_HEMATITE_BRICKS, StoneBlockSets.HEMATITE_BRICKS.slab()));
            add(new ChiseledBlock(ModBlocks.CHISELED_GNEISS_BRICKS, StoneBlockSets.GNEISS_BRICKS.slab()));
            add(new ChiseledBlock(ModBlocks.CHISELED_ZIGILABAD_BRICKS, StoneBlockSets.ZIGILABAD_BRICKS.slab()));
            add(new ChiseledBlock(ModBlocks.CHISELED_MEDGON_BRICKS, StoneBlockSets.MEDGON_BRICKS.slab()));
        }
    };

    public static List<ChiseledPolishedBlock> chiseledPolishedBlocksTopBottom = new ArrayList<>() {
        {
            add(new ChiseledPolishedBlock(ModBlocks.CHISELED_POLISHED_ANDESITE, Blocks.POLISHED_ANDESITE_SLAB));
            add(new ChiseledPolishedBlock(ModBlocks.CHISELED_POLISHED_DIORITE, Blocks.POLISHED_DIORITE_SLAB));
            add(new ChiseledPolishedBlock(ModBlocks.CHISELED_POLISHED_IRONSTONE, StoneBlockSets.POLISHED_IRONSTONE.slab()));
            add(new ChiseledPolishedBlock(ModBlocks.CHISELED_POLISHED_HEMATITE, StoneBlockSets.POLISHED_HEMATITE.slab()));
            add(new ChiseledPolishedBlock(ModBlocks.CHISELED_POLISHED_GNEISS, StoneBlockSets.POLISHED_GNEISS.slab()));
            add(new ChiseledPolishedBlock(ModBlocks.CHISELED_POLISHED_ZIGILABAD, StoneBlockSets.POLISHED_ZIGILABAD.slab()));
            add(new ChiseledPolishedBlock(ModBlocks.CHISELED_POLISHED_BLUE_TUFF, StoneBlockSets.POLISHED_BLUE_TUFF.slab()));
            add(new ChiseledPolishedBlock(ModBlocks.CHISELED_POLISHED_ASHEN_STONE, StoneBlockSets.POLISHED_ASHEN_STONE.slab()));
            add(new ChiseledPolishedBlock(ModBlocks.CHISELED_POLISHED_MEDGON, StoneBlockSets.POLISHED_MEDGON.slab()));
        }
    };

    public static List<ChiseledPolishedBlock> chiseledPolishedBlocks = new ArrayList<>() {
        {
            add(new ChiseledPolishedBlock(ModBlocks.CHISELED_POLISHED_GONLUIN, StoneBlockSets.POLISHED_GONLUIN.slab()));
            add(new ChiseledPolishedBlock(ModBlocks.CHISELED_POLISHED_CALCITE, StoneBlockSets.POLISHED_CALCITE.slab()));
            add(new ChiseledPolishedBlock(ModBlocks.CHISELED_POLISHED_NURGON, StoneBlockSets.POLISHED_NURGON.slab()));
            add(new ChiseledPolishedBlock(ModBlocks.CHISELED_POLISHED_LIMESTONE, StoneBlockSets.POLISHED_LIMESTONE.slab()));
            add(new ChiseledPolishedBlock(ModBlocks.CHISELED_SMOOTH_STONE, Blocks.SMOOTH_STONE_SLAB));
            add(new ChiseledPolishedBlock(ModBlocks.CHISELED_POLISHED_GRANITE, Blocks.POLISHED_GRANITE_SLAB));
        }
    };

    public static List<Block> woodBlocks = new ArrayList<>() {
        {

        }
    };
}
