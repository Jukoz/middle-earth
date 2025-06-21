package net.sevenstars.middleearth.datageneration.content.models;

import net.sevenstars.middleearth.block.registration.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;

import java.util.ArrayList;
import java.util.List;

public class SimpleTrapDoorModel {
    public record Trapdoor(Block block, Block trapdoor) {}

    public static List<Trapdoor> trapdoors = new ArrayList<>() {
        {
            add(new Trapdoor(ModBlocks.BLACK_WATTLE_AND_WHITE_DAUB, ModBlocks.BLACK_WATTLE_TRAPDOOR));
            add(new Trapdoor(ModBlocks.DARK_WATTLE_AND_DARK_DAUB, ModBlocks.DARK_WATTLE_TRAPDOOR));
            add(new Trapdoor(ModBlocks.GREEN_WATTLE_AND_WHITE_DAUB, ModBlocks.GREEN_WATTLE_TRAPDOOR));
            add(new Trapdoor(ModBlocks.RED_WATTLE_AND_WHITE_DAUB, ModBlocks.RED_WATTLE_TRAPDOOR));
            add(new Trapdoor(ModBlocks.WATTLE_AND_WHITE_DAUB, ModBlocks.WATTLE_TRAPDOOR));
            add(new Trapdoor(ModBlocks.CRUDE_DOOR, ModBlocks.CRUDE_TRAPDOOR));
            add(new Trapdoor(ModBlocks.STEEL_BLOCK, ModBlocks.TREATED_STEEL_TRAPDOOR));
        }
    };

    public static List<Trapdoor> stoneTrapdoors = new ArrayList<>() {
        {
        }
    };
}
