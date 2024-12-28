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

            add(ModBlocks.SNOWY_DIRT);

            add(ModBlocks.MIRE);

            add(ModBlocks.DIRTY_ROOTS);

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
            add(ModBlocks.CRUDE_BLOCK);
            add(ModBlocks.STEEL_BLOCK);
            add(ModBlocks.KHAZAD_STEEL_BLOCK);
            add(ModBlocks.EDHEL_STEEL_BLOCK);
            add(ModBlocks.BURZUM_STEEL_BLOCK);

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
            add(new ChiseledBlock(ModBlocks.CHISELED_NURGON_BRICKS, StoneBlockSets.NURGON_BRICKS.slab()));
        }
    };

    public static List<ChiseledBlock> chiseledMainBlockTopBottom = new ArrayList<>() {
        {
            add(new ChiseledBlock(ModBlocks.CHISELED_STONE, Blocks.STONE_SLAB));
            add(new ChiseledBlock(ModBlocks.CHISELED_ANDESITE, Blocks.ANDESITE_SLAB));
            add(new ChiseledBlock(ModBlocks.CHISELED_GRANITE, Blocks.GRANITE_SLAB));
            add(new ChiseledBlock(ModBlocks.CHISELED_DIORITE, Blocks.DIORITE_SLAB));
            add(new ChiseledBlock(ModBlocks.CHISELED_BASALT, StoneBlockSets.COBBLED_BASALT.slab()));
            add(new ChiseledBlock(ModBlocks.CHISELED_BLACKSTONE, StoneBlockSets.COBBLED_BLACKSTONE.slab()));
            add(new ChiseledBlock(ModBlocks.CHISELED_CALCITE, ModBlocks.CALCITE_SLAB));
            add(new ChiseledBlock(ModBlocks.CHISELED_GONLUIN, StoneBlockSets.GONLUIN.slab()));
            add(new ChiseledBlock(ModBlocks.CHISELED_MEDGON, StoneBlockSets.MEDGON.slab()));
            add(new ChiseledBlock(ModBlocks.CHISELED_DOLOMITE, StoneBlockSets.DOLOMITE.slab()));
            add(new ChiseledBlock(ModBlocks.CHISELED_GREEN_TUFF, StoneBlockSets.GREEN_TUFF.slab()));
            add(new ChiseledBlock(ModBlocks.CHISELED_IRONSTONE, StoneBlockSets.IRONSTONE.slab()));
            add(new ChiseledBlock(ModBlocks.CHISELED_HEMATITE, StoneBlockSets.HEMATITE.slab()));
            add(new ChiseledBlock(ModBlocks.CHISELED_GNEISS, StoneBlockSets.GNEISS.slab()));
            add(new ChiseledBlock(ModBlocks.CHISELED_ZIGILABAN, StoneBlockSets.ZIGILABAN.slab()));
            add(new ChiseledBlock(ModBlocks.CHISELED_IZHERABAN, StoneBlockSets.IZHERABAN.slab()));
            add(new ChiseledBlock(ModBlocks.CHISELED_LIMESTONE, StoneBlockSets.LIMESTONE.slab()));
            add(new ChiseledBlock(ModBlocks.CHISELED_GALONN, StoneBlockSets.GALONN.slab()));
        }
    };

    public static List<ChiseledBlock> chiseledBlocksTopBottom = new ArrayList<>() {
        {
            add(new ChiseledBlock(ModBlocks.CHISELED_ASHEN_BRICKS, StoneBlockSets.ASHEN_BRICKS.slab()));
            add(new ChiseledBlock(ModBlocks.CHISELED_BLUE_TUFF_BRICKS, StoneBlockSets.BLUE_TUFF_BRICKS.slab()));
            add(new ChiseledBlock(ModBlocks.CHISELED_ANDESITE_BRICKS, StoneBlockSets.ANDESITE_BRICKS.slab()));
            add(new ChiseledBlock(ModBlocks.CHISELED_DIORITE_BRICKS, StoneBlockSets.DIORITE_BRICKS.slab()));
            add(new ChiseledBlock(ModBlocks.CHISELED_BASALT_BRICKS, StoneBlockSets.BASALT_BRICKS.slab()));
            add(new ChiseledBlock(ModBlocks.CHISELED_POLISHED_BLACKSTONE_BRICKS, Blocks.POLISHED_BLACKSTONE_BRICK_SLAB));
            add(new ChiseledBlock(ModBlocks.CHISELED_GRANITE_BRICKS, StoneBlockSets.GRANITE_BRICKS.slab()));
            add(new ChiseledBlock(ModBlocks.CHISELED_CALCITE_BRICKS, StoneBlockSets.CALCITE_BRICKS.slab()));
            add(new ChiseledBlock(ModBlocks.CHISELED_DEEPSLATE_BRICKS, Blocks.COBBLED_DEEPSLATE_SLAB));
            add(new ChiseledBlock(ModBlocks.CHISELED_DOLOMITE_BRICKS, StoneBlockSets.DOLOMITE_BRICKS.slab()));
            add(new ChiseledBlock(ModBlocks.CHISELED_GREEN_TUFF_BRICKS, StoneBlockSets.GREEN_TUFF_BRICKS.slab()));
            add(new ChiseledBlock(ModBlocks.CHISELED_IRONSTONE_BRICKS, StoneBlockSets.IRONSTONE_BRICKS.slab()));
            add(new ChiseledBlock(ModBlocks.CHISELED_LIMESTONE_BRICKS, StoneBlockSets.LIMESTONE_BRICKS.slab()));
            add(new ChiseledBlock(ModBlocks.CHISELED_GALONN_BRICKS, StoneBlockSets.GALONN_BRICKS.slab()));
            //TODO : Waiting for texture
            //add(new ChiseledBlock(ModBlocks.CHISELED_HEMATITE_BRICKS, StoneBlockSets.HEMATITE_BRICKS.slab()));
            add(new ChiseledBlock(ModBlocks.CHISELED_GNEISS_BRICKS, StoneBlockSets.GNEISS_BRICKS.slab()));
            add(new ChiseledBlock(ModBlocks.CHISELED_ZIGILABAN_BRICKS, StoneBlockSets.ZIGILABAN_BRICKS.slab()));
            add(new ChiseledBlock(ModBlocks.CHISELED_IZHERABAN_BRICKS, StoneBlockSets.IZHERABAN_BRICKS.slab()));
            add(new ChiseledBlock(ModBlocks.CHISELED_MEDGON_BRICKS, StoneBlockSets.MEDGON_BRICKS.slab()));
        }
    };

    public static List<ChiseledPolishedBlock> chiseledPolishedBlocksTopBottom = new ArrayList<>() {
        {
            add(new ChiseledPolishedBlock(ModBlocks.CHISELED_POLISHED_STONE, StoneBlockSets.POLISHED_STONE.slab()));
            add(new ChiseledPolishedBlock(ModBlocks.CHISELED_POLISHED_ANDESITE, Blocks.POLISHED_ANDESITE_SLAB));
            add(new ChiseledPolishedBlock(ModBlocks.CHISELED_POLISHED_DIORITE, Blocks.POLISHED_DIORITE_SLAB));
            add(new ChiseledPolishedBlock(ModBlocks.CHISELED_POLISHED_BASALT, Blocks.POLISHED_BASALT));
            add(new ChiseledPolishedBlock(ModBlocks.CHISELED_POLISHED_GRANITE, Blocks.POLISHED_GRANITE_SLAB));
            add(new ChiseledPolishedBlock(ModBlocks.CHISELED_POLISHED_CALCITE, StoneBlockSets.POLISHED_CALCITE.slab()));
            add(new ChiseledPolishedBlock(ModBlocks.CHISELED_POLISHED_DEEPSLATE, Blocks.POLISHED_DEEPSLATE_SLAB));
            add(new ChiseledPolishedBlock(ModBlocks.CHISELED_POLISHED_IRONSTONE, StoneBlockSets.POLISHED_IRONSTONE.slab()));
            add(new ChiseledPolishedBlock(ModBlocks.CHISELED_POLISHED_DOLOMITE, StoneBlockSets.POLISHED_DOLOMITE.slab()));
            add(new ChiseledPolishedBlock(ModBlocks.CHISELED_POLISHED_GREEN_TUFF, StoneBlockSets.POLISHED_GREEN_TUFF.slab()));
            add(new ChiseledPolishedBlock(ModBlocks.CHISELED_POLISHED_TUFF, Blocks.POLISHED_TUFF_SLAB));
            add(new ChiseledPolishedBlock(ModBlocks.CHISELED_POLISHED_HEMATITE, StoneBlockSets.POLISHED_HEMATITE.slab()));
            add(new ChiseledPolishedBlock(ModBlocks.CHISELED_POLISHED_GNEISS, StoneBlockSets.POLISHED_GNEISS.slab()));
            add(new ChiseledPolishedBlock(ModBlocks.CHISELED_POLISHED_ZIGILABAN, StoneBlockSets.POLISHED_ZIGILABAN.slab()));
            add(new ChiseledPolishedBlock(ModBlocks.CHISELED_POLISHED_IZHERABAN, StoneBlockSets.POLISHED_IZHERABAN.slab()));
            add(new ChiseledPolishedBlock(ModBlocks.CHISELED_POLISHED_BLUE_TUFF, StoneBlockSets.POLISHED_BLUE_TUFF.slab()));
            add(new ChiseledPolishedBlock(ModBlocks.CHISELED_POLISHED_ASHEN_STONE, StoneBlockSets.POLISHED_ASHEN_STONE.slab()));
            add(new ChiseledPolishedBlock(ModBlocks.CHISELED_POLISHED_MEDGON, StoneBlockSets.POLISHED_MEDGON.slab()));
            add(new ChiseledPolishedBlock(ModBlocks.CHISELED_POLISHED_LIMESTONE, StoneBlockSets.POLISHED_LIMESTONE.slab()));
            add(new ChiseledPolishedBlock(ModBlocks.CHISELED_POLISHED_GALONN, StoneBlockSets.POLISHED_GALONN.slab()));
        }
    };

    public static List<ChiseledPolishedBlock> chiseledPolishedBlocks = new ArrayList<>() {
        {
            add(new ChiseledPolishedBlock(ModBlocks.CHISELED_POLISHED_GONLUIN, StoneBlockSets.POLISHED_GONLUIN.slab()));
            add(new ChiseledPolishedBlock(ModBlocks.CHISELED_POLISHED_NURGON, StoneBlockSets.POLISHED_NURGON.slab()));
        }
    };

    public static List<ChiseledPolishedBlock> chiseledTilesBlocksTopBottom = new ArrayList<>() {
        {
            add(new ChiseledPolishedBlock(ModBlocks.CHISELED_STONE_TILES, StoneBlockSets.STONE_TILES.slab()));
            add(new ChiseledPolishedBlock(ModBlocks.CHISELED_ANDESITE_TILES, StoneBlockSets.ANDESITE_TILES.slab()));
            add(new ChiseledPolishedBlock(ModBlocks.CHISELED_GRANITE_TILES, StoneBlockSets.GRANITE_TILES.slab()));
            add(new ChiseledPolishedBlock(ModBlocks.CHISELED_DIORITE_TILES, StoneBlockSets.DIORITE_TILES.slab()));
            add(new ChiseledPolishedBlock(ModBlocks.CHISELED_BASALT_TILES, StoneBlockSets.BASALT_TILES.slab()));
            add(new ChiseledPolishedBlock(ModBlocks.CHISELED_BLACKSTONE_TILES, StoneBlockSets.BLACKSTONE_TILES.slab()));
            add(new ChiseledPolishedBlock(ModBlocks.CHISELED_CALCITE_TILES, StoneBlockSets.CALCITE_TILES.slab()));
            add(new ChiseledPolishedBlock(ModBlocks.CHISELED_DEEPSLATE_TILES, Blocks.DEEPSLATE_TILE_SLAB));
            add(new ChiseledPolishedBlock(ModBlocks.CHISELED_DOLOMITE_TILES, StoneBlockSets.DOLOMITE_TILES.slab()));
            add(new ChiseledPolishedBlock(ModBlocks.CHISELED_GREEN_TUFF_TILES, StoneBlockSets.GREEN_TUFF_TILES.slab()));
            add(new ChiseledPolishedBlock(ModBlocks.CHISELED_LIMESTONE_TILES, StoneBlockSets.LIMESTONE_TILES.slab()));
            add(new ChiseledPolishedBlock(ModBlocks.CHISELED_TUFF_TILES, StoneBlockSets.TUFF_TILES.slab()));
            add(new ChiseledPolishedBlock(ModBlocks.CHISELED_GALONN_TILES, StoneBlockSets.GALONN_TILES.slab()));
            add(new ChiseledPolishedBlock(ModBlocks.CHISELED_IZHERABAN_TILES, StoneBlockSets.IZHERABAN_TILES.slab()));
        }
    };

    public static List<ChiseledPolishedBlock> chiseledSmoothBlocksTopBottom = new ArrayList<>() {
        {
            add(new ChiseledPolishedBlock(ModBlocks.CHISELED_SMOOTH_STONE, Blocks.SMOOTH_STONE_SLAB));
            add(new ChiseledPolishedBlock(ModBlocks.CHISELED_SMOOTH_ANDESITE, StoneBlockSets.SMOOTH_ANDESITE.slab()));
            add(new ChiseledPolishedBlock(ModBlocks.CHISELED_SMOOTH_GRANITE, StoneBlockSets.SMOOTH_GRANITE.slab()));
            add(new ChiseledPolishedBlock(ModBlocks.CHISELED_SMOOTH_DIORITE, StoneBlockSets.SMOOTH_DIORITE.slab()));
            add(new ChiseledPolishedBlock(ModBlocks.CHISELED_SMOOTH_BASALT, ModBlocks.SMOOTH_BASALT_SLAB));
            add(new ChiseledPolishedBlock(ModBlocks.CHISELED_SMOOTH_BLACKSTONE, StoneBlockSets.SMOOTH_BLACKSTONE.slab()));
            add(new ChiseledPolishedBlock(ModBlocks.CHISELED_SMOOTH_DEEPSLATE, StoneBlockSets.SMOOTH_DEEPSLATE.slab()));
            add(new ChiseledPolishedBlock(ModBlocks.CHISELED_SMOOTH_CALCITE, StoneBlockSets.SMOOTH_CALCITE.slab()));
            add(new ChiseledPolishedBlock(ModBlocks.CHISELED_SMOOTH_DOLOMITE, StoneBlockSets.SMOOTH_DOLOMITE.slab()));
            add(new ChiseledPolishedBlock(ModBlocks.CHISELED_SMOOTH_GREEN_TUFF, StoneBlockSets.SMOOTH_GREEN_TUFF.slab()));
            add(new ChiseledPolishedBlock(ModBlocks.CHISELED_SMOOTH_LIMESTONE, StoneBlockSets.SMOOTH_LIMESTONE.slab()));
            add(new ChiseledPolishedBlock(ModBlocks.CHISELED_SMOOTH_TUFF, StoneBlockSets.SMOOTH_TUFF.slab()));
            add(new ChiseledPolishedBlock(ModBlocks.CHISELED_SMOOTH_GALONN, StoneBlockSets.SMOOTH_GALONN.slab()));
            add(new ChiseledPolishedBlock(ModBlocks.CHISELED_SMOOTH_IZHERABAN, StoneBlockSets.SMOOTH_IZHERABAN.slab()));
        }
    };

    public static List<Block> woodBlocks = new ArrayList<>() {
        {

        }
    };
}
