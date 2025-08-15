package net.sevenstars.middleearth.datageneration.content.models;

import net.sevenstars.middleearth.block.registration.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;

import java.util.ArrayList;
import java.util.List;

public class SimpleWallModel {
    public record Wall(Block block, Block wall) {}
    public static List<Wall> blocks = new ArrayList<>() {
        {
        }
    };

    public static List<Wall> columnWalls = new ArrayList<>() {
        {
        }
    };

    public static List<Wall> strippedWalls = new ArrayList<>() {
        {
        }
    };

    public static List<Wall> vanillaWalls = new ArrayList<>() {
        {
            add(new Wall(Blocks.PACKED_MUD, ModBlocks.PACKED_MUD_WALL));

            add(new Wall(Blocks.CUT_COPPER, ModBlocks.CUT_COPPER_WALL));
            add(new Wall(Blocks.EXPOSED_CUT_COPPER, ModBlocks.EXPOSED_CUT_COPPER_WALL));
            add(new Wall(Blocks.WEATHERED_CUT_COPPER, ModBlocks.WEATHERED_CUT_COPPER_WALL));
            add(new Wall(Blocks.OXIDIZED_CUT_COPPER, ModBlocks.OXIDIZED_CUT_COPPER_WALL));
            add(new Wall(Blocks.WAXED_CUT_COPPER, ModBlocks.WAXED_CUT_COPPER_WALL));
            add(new Wall(Blocks.WAXED_EXPOSED_CUT_COPPER, ModBlocks.WAXED_EXPOSED_CUT_COPPER_WALL));
            add(new Wall(Blocks.WAXED_WEATHERED_CUT_COPPER, ModBlocks.WAXED_WEATHERED_CUT_COPPER_WALL));
            add(new Wall(Blocks.WAXED_OXIDIZED_CUT_COPPER, ModBlocks.WAXED_OXIDIZED_CUT_COPPER_WALL));

        }
    };

    public static List<Wall> vanillaStrippedWalls = new ArrayList<>() {
        {
        }
    };

    public static List<Wall> vanillaWoodWalls = new ArrayList<>() {
        {
        }
    };
}
