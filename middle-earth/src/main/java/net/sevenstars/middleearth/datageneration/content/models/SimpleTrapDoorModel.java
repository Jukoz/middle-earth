package net.sevenstars.middleearth.datageneration.content.models;

import net.sevenstars.middleearth.block.registration.BlockRegistryME;
import net.minecraft.block.Block;

import java.util.ArrayList;
import java.util.List;

public class SimpleTrapDoorModel {
    public record Trapdoor(Block block, Block trapdoor, boolean orientable) {}

    public static List<Trapdoor> trapdoors = new ArrayList<>() {
        {
            add(new Trapdoor(BlockRegistryME.BLACK_WATTLE_AND_WHITE_DAUB, BlockRegistryME.BLACK_WATTLE_TRAPDOOR, true));
            add(new Trapdoor(BlockRegistryME.DARK_WATTLE_AND_DARK_DAUB, BlockRegistryME.DARK_WATTLE_TRAPDOOR, true));
            add(new Trapdoor(BlockRegistryME.GREEN_WATTLE_AND_WHITE_DAUB, BlockRegistryME.GREEN_WATTLE_TRAPDOOR, true));
            add(new Trapdoor(BlockRegistryME.RED_WATTLE_AND_WHITE_DAUB, BlockRegistryME.RED_WATTLE_TRAPDOOR, true));
            add(new Trapdoor(BlockRegistryME.WATTLE_AND_WHITE_DAUB, BlockRegistryME.WATTLE_TRAPDOOR, true));
            add(new Trapdoor(BlockRegistryME.AGED_WOOD_TRAPDOOR, BlockRegistryME.AGED_WOOD_TRAPDOOR, true));
            add(new Trapdoor(BlockRegistryME.BRONZE_BLOCK, BlockRegistryME.BRONZE_TRAPDOOR, true));
            add(new Trapdoor(BlockRegistryME.CRUDE_BLOCK, BlockRegistryME.CRUDE_TRAPDOOR, true));
            add(new Trapdoor(BlockRegistryME.STEEL_BLOCK, BlockRegistryME.TREATED_STEEL_TRAPDOOR, true));
        }
    };
}
