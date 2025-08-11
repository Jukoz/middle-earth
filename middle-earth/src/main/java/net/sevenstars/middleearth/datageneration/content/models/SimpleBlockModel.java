package net.sevenstars.middleearth.datageneration.content.models;

import net.sevenstars.middleearth.block.registration.ModBlocks;
import net.sevenstars.middleearth.block.registration.ModDecorativeBlocks;
import net.minecraft.block.Block;

import java.util.ArrayList;
import java.util.List;

public class SimpleBlockModel {

    public record ChiseledBlock(Block base, Block origin) {}

    public record ChiseledPolishedBlock(Block base, Block origin) {}


    public static List<Block> blocks = new ArrayList<>() {
        {
            add(ModBlocks.COBBLY_DIRT);

            add(ModBlocks.SNOWY_DIRT);

            add(ModBlocks.MIRE);

            add(ModBlocks.DIRTY_ROOTS);

            add(ModBlocks.DRY_DIRT);

            add(ModBlocks.ASHEN_DIRT);

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

            add(ModDecorativeBlocks.WOOD_FRAMED_WINDOW);
            add(ModDecorativeBlocks.WATTLE_AND_BRICK_WINDOW);
            add(ModDecorativeBlocks.WATTLE_FRAMED_WINDOW);
            add(ModDecorativeBlocks.DARK_WATTLE_FRAMED_WINDOW);
            add(ModDecorativeBlocks.BLACK_WATTLE_FRAMED_WINDOW);
            add(ModDecorativeBlocks.GREEN_WATTLE_FRAMED_WINDOW);
            add(ModDecorativeBlocks.RED_WATTLE_FRAMED_WINDOW);
            add(ModDecorativeBlocks.WHITE_WATTLE_FRAMED_WINDOW);
            add(ModDecorativeBlocks.WHITE_DAUB_HOBBIT_WINDOW);
            add(ModDecorativeBlocks.PLASTER_HOBBIT_WINDOW);
            add(ModDecorativeBlocks.PLASTER_ROUND_WINDOW);
            add(ModDecorativeBlocks.YELLOW_DAUB_HOBBIT_WINDOW);
            add(ModDecorativeBlocks.MUD_BRICK_ROUND_WINDOW);
            add(ModDecorativeBlocks.WHITE_DAUB_ROUND_WINDOW);
            add(ModDecorativeBlocks.YELLOW_DAUB_ROUND_WINDOW);

            add(ModDecorativeBlocks.LEAD_GLASS);
            add(ModDecorativeBlocks.BLUE_STAINED_LEAD_GLASS);
            add(ModDecorativeBlocks.BLACK_STAINED_LEAD_GLASS);
            add(ModDecorativeBlocks.BROWN_STAINED_LEAD_GLASS);
            add(ModDecorativeBlocks.CYAN_STAINED_LEAD_GLASS);
            add(ModDecorativeBlocks.GRAY_STAINED_LEAD_GLASS);
            add(ModDecorativeBlocks.GREEN_STAINED_LEAD_GLASS);
            add(ModDecorativeBlocks.LIGHT_BLUE_STAINED_LEAD_GLASS);
            add(ModDecorativeBlocks.LIGHT_GRAY_STAINED_LEAD_GLASS);
            add(ModDecorativeBlocks.LIME_STAINED_LEAD_GLASS);
            add(ModDecorativeBlocks.MAGENTA_STAINED_LEAD_GLASS);
            add(ModDecorativeBlocks.ORANGE_STAINED_LEAD_GLASS);
            add(ModDecorativeBlocks.PINK_STAINED_LEAD_GLASS);
            add(ModDecorativeBlocks.PURPLE_STAINED_LEAD_GLASS);
            add(ModDecorativeBlocks.RED_STAINED_LEAD_GLASS);
            add(ModDecorativeBlocks.WHITE_STAINED_LEAD_GLASS);
            add(ModDecorativeBlocks.YELLOW_STAINED_LEAD_GLASS);
        }
    };

    public static List<Block> cobbleableStoneBlocks = new ArrayList<>() {
        {

        }
    };

    public static List<ChiseledBlock> chiseledMainBlockTopBottom = new ArrayList<>() {
        {

        }
    };

    public static List<ChiseledBlock> chiseledBlocksTopBottom = new ArrayList<>() {
        {

        }
    };

    public static List<ChiseledPolishedBlock> chiseledPolishedBlocksTopBottom = new ArrayList<>() {
        {

        }
    };

    public static List<ChiseledPolishedBlock> chiseledPolishedBlocks = new ArrayList<>() {
        {

        }
    };

    public static List<ChiseledPolishedBlock> chiseledTilesBlocksTopBottom = new ArrayList<>() {
        {

        }
    };

    public static List<ChiseledPolishedBlock> chiseledSmoothBlocksTopBottom = new ArrayList<>() {
        {

        }
    };

    public static List<Block> woodBlocks = new ArrayList<>() {
        {

        }
    };
}
