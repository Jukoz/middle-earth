package net.sevenstars.middleearth.datageneration.content.models;

import net.sevenstars.middleearth.block.registration.BlockRegistryME;
import net.minecraft.block.Block;

import java.util.ArrayList;
import java.util.List;

public class SimpleDoorModel {
    public record Door(Block block, Block door) {}

    public static List<Door> doors = new ArrayList<>() {
        {
            add(new Door(BlockRegistryME.AGED_WOOD_DOOR, BlockRegistryME.AGED_WOOD_DOOR));
            add(new Door(BlockRegistryME.BRONZE_BLOCK, BlockRegistryME.BRONZE_DOOR));
            add(new Door(BlockRegistryME.CRUDE_BLOCK, BlockRegistryME.CRUDE_DOOR));
            add(new Door(BlockRegistryME.STEEL_BLOCK, BlockRegistryME.TREATED_STEEL_DOOR));
        }
    };
}
