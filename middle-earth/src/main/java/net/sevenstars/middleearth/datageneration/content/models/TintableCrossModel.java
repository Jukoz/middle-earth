package net.sevenstars.middleearth.datageneration.content.models;

import net.sevenstars.middleearth.block.registration.NatureBlockRegistryME;
import net.minecraft.block.Block;

import java.util.ArrayList;
import java.util.List;

public class TintableCrossModel {
    public static List<Block> notTintedBlocks = new ArrayList<>() {
        {
            add(NatureBlockRegistryME.WILD_CUCUMBER);
            add(NatureBlockRegistryME.WILD_LEEK);
            add(NatureBlockRegistryME.WILD_LETTUCE);
            add(NatureBlockRegistryME.WILD_GARLIC);
            add(NatureBlockRegistryME.WILD_ONION);
            add(NatureBlockRegistryME.WILD_FLAX);
            add(NatureBlockRegistryME.WILD_TOMATO);
            add(NatureBlockRegistryME.WILD_WHEAT);
            add(NatureBlockRegistryME.WILD_BELL_PEPPER);

            add(NatureBlockRegistryME.BROWN_BOLETE);
            add(NatureBlockRegistryME.CAVE_AMANITA);
            add(NatureBlockRegistryME.DEEP_FIRECAP);
            add(NatureBlockRegistryME.GHOSTSHROOM);
            add(NatureBlockRegistryME.MORSEL);
            add(NatureBlockRegistryME.SKY_FIRECAP);
            add(NatureBlockRegistryME.TRUMPET_SHROOM);
            add(NatureBlockRegistryME.TUBESHRROM);
            add(NatureBlockRegistryME.VIOLET_CAPS);
            add(NatureBlockRegistryME.WHITE_MUSHROOM);
            add(NatureBlockRegistryME.YELLOW_AMANITA);

            add(NatureBlockRegistryME.BLUE_GENTIAN);
            add(NatureBlockRegistryME.GREEN_JEWEL_CORNFLOWER);
            add(NatureBlockRegistryME.NOBLEWHITE);

            add(NatureBlockRegistryME.ELANOR);
            add(NatureBlockRegistryME.MALLOS);
            add(NatureBlockRegistryME.NIPHREDIL);
            add(NatureBlockRegistryME.SIMBELMYNE);
            add(NatureBlockRegistryME.YELLOW_FLOWER);

            add(NatureBlockRegistryME.LIGHT_BLUE_FLOWERS);
            add(NatureBlockRegistryME.MAGENTA_FLOWERS);
            add(NatureBlockRegistryME.ORANGE_FLOWERS);
            add(NatureBlockRegistryME.PINK_FLOWERS);
            add(NatureBlockRegistryME.PURPLE_FLOWERS);
            add(NatureBlockRegistryME.RED_FLOWERS);
            add(NatureBlockRegistryME.WHITE_FLOWERS);
            add(NatureBlockRegistryME.YELLOW_FLOWERS);

            add(NatureBlockRegistryME.BLUE_LAVENDER);
            add(NatureBlockRegistryME.LAVENDER);
            add(NatureBlockRegistryME.WHITE_LAVENDER);
            add(NatureBlockRegistryME.YELLOW_TROLLIUS);
        }
    };
    public static List<Block> tintedBlocks = new ArrayList<>() {
        {
            add(NatureBlockRegistryME.GRASS_TUFT);
            add(NatureBlockRegistryME.MEADOWGRASS);
            add(NatureBlockRegistryME.SPARSE_GRASS);
            add(NatureBlockRegistryME.NETTLES);
            add(NatureBlockRegistryME.THISTLE);
            add(NatureBlockRegistryME.WHEATGRASS);
        }
    };

    public static List<Block> grassLikeBlocks = new ArrayList<>()
    {
        {
            add(NatureBlockRegistryME.ATHELAS);

            add(NatureBlockRegistryME.SHORT_HOGWEED);

            add(NatureBlockRegistryME.BROWN_GRASS);
            add(NatureBlockRegistryME.GREEN_SHRUB);
            add(NatureBlockRegistryME.SMALL_DRY_SHRUB);
            add(NatureBlockRegistryME.FROZEN_SHRUB);
            add(NatureBlockRegistryME.TAN_SHRUB);

            add(NatureBlockRegistryME.BLUE_FESCUE);
            add(NatureBlockRegistryME.DYING_GRASS);
            add(NatureBlockRegistryME.FROZEN_GRASS);
            add(NatureBlockRegistryME.GRIM_GRASS);
            add(NatureBlockRegistryME.TEMPERATE_GRASS);
            add(NatureBlockRegistryME.FROZEN_TUFT);
            add(NatureBlockRegistryME.HEATHER);
            add(NatureBlockRegistryME.RED_HEATHER);
            add(NatureBlockRegistryME.DEAD_HEATHER);
            add(NatureBlockRegistryME.DRY_HEATHER);
            add(NatureBlockRegistryME.HEATH);
            add(NatureBlockRegistryME.WILDERGRASS);
            add(NatureBlockRegistryME.BEACH_GRASS);
            add(NatureBlockRegistryME.COASTAL_PANIC_GRASS);
            add(NatureBlockRegistryME.MISTWEED);
            add(NatureBlockRegistryME.SEDUM);
            add(NatureBlockRegistryME.ORANGE_SEDUM);
            add(NatureBlockRegistryME.RED_SEDUM);
            add(NatureBlockRegistryME.YELLOW_SEDUM);
            add(NatureBlockRegistryME.BRAMBLES_OF_MORDOR);
            add(NatureBlockRegistryME.SHORT_DEAD_RUSHES);
            add(NatureBlockRegistryME.SHORT_RUSHES);
            add(NatureBlockRegistryME.SHORT_REEDS);
            add(NatureBlockRegistryME.SHORT_CATTAILS);
            add(NatureBlockRegistryME.SHORT_BULRUSH);

            add(NatureBlockRegistryME.SHRIVELED_SHRUB);

            add(NatureBlockRegistryME.SCORCHED_GRASS);
            add(NatureBlockRegistryME.SCORCHED_TUFT);
            add(NatureBlockRegistryME.SCORCHED_SHRUB);
        }
    };

    public static List<Block> largePlants = new ArrayList<>()
    {
        {
            add(NatureBlockRegistryME.CAMPION);
            add(NatureBlockRegistryME.BLUE_BIGLEAF_HYDRANGEA);
            add(NatureBlockRegistryME.PINK_BIGLEAF_HYDRANGEA);
            add(NatureBlockRegistryME.WHITE_BIGLEAF_HYDRANGEA);
            add(NatureBlockRegistryME.DEAD_HEATHER_BUSH);
            add(NatureBlockRegistryME.DRY_HEATHER_BUSH);
            add(NatureBlockRegistryME.DEAD_RUSHES);
            add(NatureBlockRegistryME.FALSE_OATGRASS);
            add(NatureBlockRegistryME.HEATHER_BUSH);
            add(NatureBlockRegistryME.LARGE_BLUE_FESCUE);
            add(NatureBlockRegistryME.LARGE_SHRIVELED_SHRUB);
            add(NatureBlockRegistryME.RED_HEATHER_BUSH);
            add(NatureBlockRegistryME.RUSHES);
        }
    };
}
