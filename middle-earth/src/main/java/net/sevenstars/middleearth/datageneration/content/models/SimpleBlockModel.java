package net.sevenstars.middleearth.datageneration.content.models;

import net.sevenstars.middleearth.block.registration.BlockRegistryME;
import net.sevenstars.middleearth.block.registration.DecorativeBlockRegistryME;
import net.minecraft.block.Block;

import java.util.ArrayList;
import java.util.List;

public class SimpleBlockModel {

    public record ChiseledBlock(Block base, Block origin) {}

    public record ChiseledPolishedBlock(Block base, Block origin) {}


    public static List<Block> blocks = new ArrayList<>() {
        {
            add(BlockRegistryME.COBBLY_DIRT);


            add(BlockRegistryME.SNOWY_DIRT);

            add(BlockRegistryME.MIRE);

            add(BlockRegistryME.DIRTY_ROOTS);

            add(BlockRegistryME.DRY_DIRT);

            add(BlockRegistryME.CHALKSOIL);
            add(BlockRegistryME.COARSE_CHALKSOIL);

            add(BlockRegistryME.LOAM);
            add(BlockRegistryME.COARSE_LOAM);

            add(BlockRegistryME.PEAT);
            add(BlockRegistryME.COARSE_PEAT);

            add(BlockRegistryME.SILT);
            add(BlockRegistryME.COARSE_SILT);

            add(BlockRegistryME.FOUL_DIRT);

            add(BlockRegistryME.ASHEN_DIRT);

            add(BlockRegistryME.COBBLY_ASHEN_DIRT);

            //METALS
            add(BlockRegistryME.RAW_MITHRIL_BLOCK);
            add(BlockRegistryME.MITHRIL_BLOCK);
            add(BlockRegistryME.RAW_TIN_BLOCK);
            add(BlockRegistryME.TIN_BLOCK);
            add(BlockRegistryME.RAW_LEAD_BLOCK);
            add(BlockRegistryME.LEAD_BLOCK);
            add(BlockRegistryME.RAW_SILVER_BLOCK);
            add(BlockRegistryME.SILVER_BLOCK);

            add(BlockRegistryME.BRONZE_BLOCK);
            add(BlockRegistryME.CRUDE_BLOCK);
            add(BlockRegistryME.STEEL_BLOCK);
            add(BlockRegistryME.KHAZAD_STEEL_BLOCK);
            add(BlockRegistryME.EDHEL_STEEL_BLOCK);
            add(BlockRegistryME.BURZUM_STEEL_BLOCK);

            add(BlockRegistryME.ADAMANT_BLOCK);
            add(BlockRegistryME.RUBY_BLOCK);
            add(BlockRegistryME.SAPPHIRE_BLOCK);

            //GEMS
            add(BlockRegistryME.QUARTZ_BLOCK);
            add(BlockRegistryME.BUDDING_QUARTZ);
            add(BlockRegistryME.RED_AGATE_BLOCK);
            add(BlockRegistryME.BUDDING_RED_AGATE);
            add(BlockRegistryME.CITRINE_BLOCK);
            add(BlockRegistryME.BUDDING_CITRINE);
            add(BlockRegistryME.GLOWSTONE_BLOCK);
            add(BlockRegistryME.BUDDING_GLOWSTONE);

            add(BlockRegistryME.RIVER_SAND);
            add(BlockRegistryME.ASH_BLOCK);

            add(BlockRegistryME.WATTLE_AND_BRICK);
            add(BlockRegistryME.WATTLE_AND_BRICK_CROSS);
            add(BlockRegistryME.WATTLE_AND_BRICK_RIGHT);
            add(BlockRegistryME.WATTLE_AND_BRICK_LEFT);
            add(BlockRegistryME.WATTLE_AND_BRICK_PILLAR);
            add(BlockRegistryME.WATTLE_AND_BRICK_DIAMOND);

            add(BlockRegistryME.WATTLE_AND_WHITE_DAUB);
            add(BlockRegistryME.WATTLE_AND_WHITE_DAUB_CROSS);
            add(BlockRegistryME.WATTLE_AND_WHITE_DAUB_RIGHT);
            add(BlockRegistryME.WATTLE_AND_WHITE_DAUB_LEFT);
            add(BlockRegistryME.WATTLE_AND_WHITE_DAUB_PILLAR);
            add(BlockRegistryME.WATTLE_AND_WHITE_DAUB_DIAMOND);

            add(BlockRegistryME.BLACK_WATTLE_AND_WHITE_DAUB);
            add(BlockRegistryME.BLACK_WATTLE_AND_WHITE_DAUB_CROSS);
            add(BlockRegistryME.BLACK_WATTLE_AND_WHITE_DAUB_RIGHT);
            add(BlockRegistryME.BLACK_WATTLE_AND_WHITE_DAUB_LEFT);
            add(BlockRegistryME.BLACK_WATTLE_AND_WHITE_DAUB_PILLAR);
            add(BlockRegistryME.BLACK_WATTLE_AND_WHITE_DAUB_DIAMOND);

            add(BlockRegistryME.GREEN_WATTLE_AND_WHITE_DAUB);
            add(BlockRegistryME.GREEN_WATTLE_AND_WHITE_DAUB_CROSS);
            add(BlockRegistryME.GREEN_WATTLE_AND_WHITE_DAUB_RIGHT);
            add(BlockRegistryME.GREEN_WATTLE_AND_WHITE_DAUB_LEFT);
            add(BlockRegistryME.GREEN_WATTLE_AND_WHITE_DAUB_PILLAR);
            add(BlockRegistryME.GREEN_WATTLE_AND_WHITE_DAUB_DIAMOND);

            add(BlockRegistryME.RED_WATTLE_AND_WHITE_DAUB);
            add(BlockRegistryME.RED_WATTLE_AND_WHITE_DAUB_CROSS);
            add(BlockRegistryME.RED_WATTLE_AND_WHITE_DAUB_RIGHT);
            add(BlockRegistryME.RED_WATTLE_AND_WHITE_DAUB_LEFT);
            add(BlockRegistryME.RED_WATTLE_AND_WHITE_DAUB_PILLAR);
            add(BlockRegistryME.RED_WATTLE_AND_WHITE_DAUB_DIAMOND);

            add(BlockRegistryME.DARK_WATTLE_AND_DARK_DAUB);
            add(BlockRegistryME.DARK_WATTLE_AND_DARK_DAUB_CROSS);
            add(BlockRegistryME.DARK_WATTLE_AND_DARK_DAUB_RIGHT);
            add(BlockRegistryME.DARK_WATTLE_AND_DARK_DAUB_LEFT);
            add(BlockRegistryME.DARK_WATTLE_AND_DARK_DAUB_PILLAR);
            add(BlockRegistryME.DARK_WATTLE_AND_DARK_DAUB_DIAMOND);

            add(BlockRegistryME.WATTLE_AND_YELLOW_DAUB);
            add(BlockRegistryME.WATTLE_AND_YELLOW_DAUB_CROSS);
            add(BlockRegistryME.WATTLE_AND_YELLOW_DAUB_RIGHT);
            add(BlockRegistryME.WATTLE_AND_YELLOW_DAUB_LEFT);
            add(BlockRegistryME.WATTLE_AND_YELLOW_DAUB_PILLAR);
            add(BlockRegistryME.WATTLE_AND_YELLOW_DAUB_DIAMOND);

            add(DecorativeBlockRegistryME.WOOD_FRAMED_WINDOW);
            add(DecorativeBlockRegistryME.AGED_WOOD_WINDOW);
            add(DecorativeBlockRegistryME.SIMPLE_OAK_WINDOW);
            add(DecorativeBlockRegistryME.WATTLE_AND_BRICK_WINDOW);
            add(DecorativeBlockRegistryME.WATTLE_FRAMED_WINDOW);
            add(DecorativeBlockRegistryME.DARK_WATTLE_FRAMED_WINDOW);
            add(DecorativeBlockRegistryME.BLACK_WATTLE_FRAMED_WINDOW);
            add(DecorativeBlockRegistryME.GREEN_WATTLE_FRAMED_WINDOW);
            add(DecorativeBlockRegistryME.RED_WATTLE_FRAMED_WINDOW);
            add(DecorativeBlockRegistryME.WHITE_WATTLE_FRAMED_WINDOW);
            add(DecorativeBlockRegistryME.WHITE_DAUB_HOBBIT_WINDOW);
            add(DecorativeBlockRegistryME.PLASTER_HOBBIT_WINDOW);
            add(DecorativeBlockRegistryME.PLASTER_ROUND_WINDOW);
            add(DecorativeBlockRegistryME.YELLOW_DAUB_HOBBIT_WINDOW);
            add(DecorativeBlockRegistryME.MUD_BRICK_ROUND_WINDOW);
            add(DecorativeBlockRegistryME.WHITE_DAUB_ROUND_WINDOW);
            add(DecorativeBlockRegistryME.YELLOW_DAUB_ROUND_WINDOW);

            add(DecorativeBlockRegistryME.LEAD_GLASS);
            add(DecorativeBlockRegistryME.BLUE_STAINED_LEAD_GLASS);
            add(DecorativeBlockRegistryME.BLACK_STAINED_LEAD_GLASS);
            add(DecorativeBlockRegistryME.BROWN_STAINED_LEAD_GLASS);
            add(DecorativeBlockRegistryME.CYAN_STAINED_LEAD_GLASS);
            add(DecorativeBlockRegistryME.GRAY_STAINED_LEAD_GLASS);
            add(DecorativeBlockRegistryME.GREEN_STAINED_LEAD_GLASS);
            add(DecorativeBlockRegistryME.LIGHT_BLUE_STAINED_LEAD_GLASS);
            add(DecorativeBlockRegistryME.LIGHT_GRAY_STAINED_LEAD_GLASS);
            add(DecorativeBlockRegistryME.LIME_STAINED_LEAD_GLASS);
            add(DecorativeBlockRegistryME.MAGENTA_STAINED_LEAD_GLASS);
            add(DecorativeBlockRegistryME.ORANGE_STAINED_LEAD_GLASS);
            add(DecorativeBlockRegistryME.PINK_STAINED_LEAD_GLASS);
            add(DecorativeBlockRegistryME.PURPLE_STAINED_LEAD_GLASS);
            add(DecorativeBlockRegistryME.RED_STAINED_LEAD_GLASS);
            add(DecorativeBlockRegistryME.WHITE_STAINED_LEAD_GLASS);
            add(DecorativeBlockRegistryME.YELLOW_STAINED_LEAD_GLASS);
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
