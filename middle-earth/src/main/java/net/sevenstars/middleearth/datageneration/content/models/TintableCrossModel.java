package net.sevenstars.middleearth.datageneration.content.models;

import net.sevenstars.middleearth.block.ModNatureBlocks;
import net.minecraft.block.Block;

import java.util.ArrayList;
import java.util.List;

public class TintableCrossModel {
    public static List<Block> notTintedBlocks = new ArrayList<>() {
        {
            add(ModNatureBlocks.WILD_CUCUMBER);
            add(ModNatureBlocks.WILD_LEEK);
            add(ModNatureBlocks.WILD_LETTUCE);
            add(ModNatureBlocks.WILD_GARLIC);
            add(ModNatureBlocks.WILD_ONION);
            add(ModNatureBlocks.WILD_FLAX);
            add(ModNatureBlocks.WILD_WHEAT);
            add(ModNatureBlocks.WILD_BELL_PEPPER);

            add(ModNatureBlocks.BROWN_BOLETE);
            add(ModNatureBlocks.CAVE_AMANITA);
            add(ModNatureBlocks.DEEP_FIRECAP);
            add(ModNatureBlocks.GHOSTSHROOM);
            add(ModNatureBlocks.MORSEL);
            add(ModNatureBlocks.SKY_FIRECAP);
            add(ModNatureBlocks.TRUMPET_SHROOM);
            add(ModNatureBlocks.TUBESHRROM);
            add(ModNatureBlocks.VIOLET_CAPS);
            add(ModNatureBlocks.WHITE_MUSHROOM);
            add(ModNatureBlocks.YELLOW_AMANITA);

            add(ModNatureBlocks.GREEN_JEWEL_CORNFLOWER);
            add(ModNatureBlocks.MALLOS);
            add(ModNatureBlocks.ELANOR);
            add(ModNatureBlocks.YELLOW_FLOWER);

            add(ModNatureBlocks.LIGHT_BLUE_FLOWERS);
            add(ModNatureBlocks.MAGENTA_FLOWERS);
            add(ModNatureBlocks.ORANGE_FLOWERS);
            add(ModNatureBlocks.PINK_FLOWERS);
            add(ModNatureBlocks.PURPLE_FLOWERS);
            add(ModNatureBlocks.RED_FLOWERS);
            add(ModNatureBlocks.WHITE_FLOWERS);
            add(ModNatureBlocks.YELLOW_FLOWERS);

            add(ModNatureBlocks.LAVENDER);
            add(ModNatureBlocks.YELLOW_TROLLIUS);
        }
    };
    public static List<Block> tintedBlocks = new ArrayList<>() {
        {
            add(ModNatureBlocks.GRASS_TUFT);
            add(ModNatureBlocks.WILD_GRASS);
            add(ModNatureBlocks.WHEATGRASS);
        }
    };

    public static List<Block> grassLikeBlocks = new ArrayList<>()
    {
        {
            add(ModNatureBlocks.BROWN_GRASS);
            add(ModNatureBlocks.GREEN_SHRUB);
            add(ModNatureBlocks.SMALL_DRY_SHRUB);
            add(ModNatureBlocks.FROZEN_SHRUB);
            add(ModNatureBlocks.TAN_SHRUB);

            add(ModNatureBlocks.DRY_GRASS);
            add(ModNatureBlocks.DYING_GRASS);
            add(ModNatureBlocks.FROZEN_GRASS);
            add(ModNatureBlocks.GRIM_GRASS);
            add(ModNatureBlocks.TEMPERATE_GRASS);
            add(ModNatureBlocks.FROZEN_TUFT);
            add(ModNatureBlocks.HEATHER);
            add(ModNatureBlocks.RED_HEATHER);
            add(ModNatureBlocks.DEAD_HEATHER);
            add(ModNatureBlocks.DRY_HEATHER);
            add(ModNatureBlocks.HEATH);
            add(ModNatureBlocks.WILDERGRASS);
            add(ModNatureBlocks.BEACH_GRASS);
            add(ModNatureBlocks.COASTAL_PANIC_GRASS);
            add(ModNatureBlocks.SEDUM);
            add(ModNatureBlocks.YELLOW_SEDUM);
            add(ModNatureBlocks.SHORT_CATTAILS);
            add(ModNatureBlocks.SHORT_BULRUSH);

            add(ModNatureBlocks.SHRIVELED_SHRUB);

            add(ModNatureBlocks.SCORCHED_GRASS);
            add(ModNatureBlocks.SCORCHED_TUFT);
            add(ModNatureBlocks.SCORCHED_SHRUB);
        }
    };
}
