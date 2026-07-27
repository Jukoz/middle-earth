package net.sevenstars.middleearth.datageneration.content.models;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import net.minecraft.world.level.block.Block;
import net.sevenstars.middleearth.block.registration.ModNatureBlocks;

public final class TintableCrossModel {
    private static final List<Block> registeredNotTintedBlocks = new ArrayList<>();

    private TintableCrossModel() {
    }

    public static void addNotTintedBlock(Block block) {
        registeredNotTintedBlocks.add(block);
    }

    public static List<Block> notTintedBlocks() {
        LinkedHashSet<Block> blocks = new LinkedHashSet<>(registeredNotTintedBlocks);
        blocks.addAll(List.of(
                ModNatureBlocks.WILD_CUCUMBER,
                ModNatureBlocks.WILD_LEEK,
                ModNatureBlocks.WILD_LETTUCE,
                ModNatureBlocks.WILD_GARLIC,
                ModNatureBlocks.WILD_ONION,
                ModNatureBlocks.WILD_FLAX,
                ModNatureBlocks.WILD_TOMATO,
                ModNatureBlocks.WILD_WHEAT,
                ModNatureBlocks.WILD_BELL_PEPPER,
                ModNatureBlocks.BROWN_BOLETE,
                ModNatureBlocks.CAVE_AMANITA,
                ModNatureBlocks.DEEP_FIRECAP,
                ModNatureBlocks.GHOSTSHROOM,
                ModNatureBlocks.MORSEL,
                ModNatureBlocks.SKY_FIRECAP,
                ModNatureBlocks.TRUMPET_SHROOM,
                ModNatureBlocks.TUBESHRROM,
                ModNatureBlocks.VIOLET_CAPS,
                ModNatureBlocks.WHITE_MUSHROOM,
                ModNatureBlocks.YELLOW_AMANITA,
                ModNatureBlocks.BLUE_GENTIAN,
                ModNatureBlocks.GREEN_JEWEL_CORNFLOWER,
                ModNatureBlocks.NOBLEWHITE,
                ModNatureBlocks.ELANOR,
                ModNatureBlocks.MALLOS,
                ModNatureBlocks.NIPHREDIL,
                ModNatureBlocks.SIMBELMYNE,
                ModNatureBlocks.YELLOW_FLOWER,
                ModNatureBlocks.LIGHT_BLUE_FLOWERS,
                ModNatureBlocks.MAGENTA_FLOWERS,
                ModNatureBlocks.ORANGE_FLOWERS,
                ModNatureBlocks.PINK_FLOWERS,
                ModNatureBlocks.PURPLE_FLOWERS,
                ModNatureBlocks.RED_FLOWERS,
                ModNatureBlocks.WHITE_FLOWERS,
                ModNatureBlocks.YELLOW_FLOWERS,
                ModNatureBlocks.BLUE_LAVENDER,
                ModNatureBlocks.LAVENDER,
                ModNatureBlocks.WHITE_LAVENDER,
                ModNatureBlocks.YELLOW_TROLLIUS
        ));
        return List.copyOf(blocks);
    }

    public static List<Block> tintedBlocks() {
        return List.of(
                ModNatureBlocks.GRASS_TUFT,
                ModNatureBlocks.MEADOWGRASS,
                ModNatureBlocks.SPARSE_GRASS,
                ModNatureBlocks.NETTLES,
                ModNatureBlocks.THISTLE,
                ModNatureBlocks.WHEATGRASS
        );
    }

    public static List<Block> grassLikeBlocks() {
        return List.of(
                ModNatureBlocks.ATHELAS,
                ModNatureBlocks.SHORT_HOGWEED,
                ModNatureBlocks.BROWN_GRASS,
                ModNatureBlocks.GREEN_SHRUB,
                ModNatureBlocks.SMALL_DRY_SHRUB,
                ModNatureBlocks.FROZEN_SHRUB,
                ModNatureBlocks.TAN_SHRUB,
                ModNatureBlocks.BLUE_FESCUE,
                ModNatureBlocks.DYING_GRASS,
                ModNatureBlocks.FROZEN_GRASS,
                ModNatureBlocks.GRIM_GRASS,
                ModNatureBlocks.TEMPERATE_GRASS,
                ModNatureBlocks.FROZEN_TUFT,
                ModNatureBlocks.HEATHER,
                ModNatureBlocks.RED_HEATHER,
                ModNatureBlocks.DEAD_HEATHER,
                ModNatureBlocks.DRY_HEATHER,
                ModNatureBlocks.HEATH,
                ModNatureBlocks.WILDERGRASS,
                ModNatureBlocks.BEACH_GRASS,
                ModNatureBlocks.COASTAL_PANIC_GRASS,
                ModNatureBlocks.MISTWEED,
                ModNatureBlocks.SEDUM,
                ModNatureBlocks.ORANGE_SEDUM,
                ModNatureBlocks.RED_SEDUM,
                ModNatureBlocks.YELLOW_SEDUM,
                ModNatureBlocks.BRAMBLES_OF_MORDOR,
                ModNatureBlocks.SHORT_DEAD_RUSHES,
                ModNatureBlocks.SHORT_RUSHES,
                ModNatureBlocks.SHORT_REEDS,
                ModNatureBlocks.SHORT_CATTAILS,
                ModNatureBlocks.SHORT_BULRUSH,
                ModNatureBlocks.SHRIVELED_SHRUB,
                ModNatureBlocks.SCORCHED_GRASS,
                ModNatureBlocks.SCORCHED_TUFT,
                ModNatureBlocks.SCORCHED_SHRUB
        );
    }

    public static List<Block> largePlants() {
        return List.of(
                ModNatureBlocks.CAMPION,
                ModNatureBlocks.BLUE_BIGLEAF_HYDRANGEA,
                ModNatureBlocks.PINK_BIGLEAF_HYDRANGEA,
                ModNatureBlocks.WHITE_BIGLEAF_HYDRANGEA,
                ModNatureBlocks.DEAD_HEATHER_BUSH,
                ModNatureBlocks.DRY_HEATHER_BUSH,
                ModNatureBlocks.DEAD_RUSHES,
                ModNatureBlocks.FALSE_OATGRASS,
                ModNatureBlocks.HEATHER_BUSH,
                ModNatureBlocks.LARGE_BLUE_FESCUE,
                ModNatureBlocks.LARGE_SHRIVELED_SHRUB,
                ModNatureBlocks.RED_HEATHER_BUSH,
                ModNatureBlocks.RUSHES
        );
    }
}
