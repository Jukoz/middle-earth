package net.sevenstars.middleearth.datageneration.content.models;

import net.sevenstars.middleearth.block.registration.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;

import java.util.ArrayList;
import java.util.List;

public class SimpleDoorModel {
    public record Door(Block block, Block door) {}

    public static List<Door> doors = new ArrayList<>() {
        {
            add(new Door(ModBlocks.BRONZE_BLOCK, ModBlocks.BRONZE_DOOR));
            add(new Door(ModBlocks.CRUDE_BLOCK, ModBlocks.CRUDE_DOOR));
            add(new Door(ModBlocks.STEEL_BLOCK, ModBlocks.TREATED_STEEL_DOOR));
        }
    };
}
