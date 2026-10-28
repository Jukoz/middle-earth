package net.sevenstars.middleearth.datageneration.content.models;

import net.sevenstars.middleearth.block.registration.BlockRegistryME;
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
            add(new Wall(Blocks.PACKED_MUD, BlockRegistryME.PACKED_MUD_WALL));

            add(new Wall(Blocks.CUT_COPPER, BlockRegistryME.CUT_COPPER_WALL));
            add(new Wall(Blocks.EXPOSED_CUT_COPPER, BlockRegistryME.EXPOSED_CUT_COPPER_WALL));
            add(new Wall(Blocks.WEATHERED_CUT_COPPER, BlockRegistryME.WEATHERED_CUT_COPPER_WALL));
            add(new Wall(Blocks.OXIDIZED_CUT_COPPER, BlockRegistryME.OXIDIZED_CUT_COPPER_WALL));
            add(new Wall(Blocks.WAXED_CUT_COPPER, BlockRegistryME.WAXED_CUT_COPPER_WALL));
            add(new Wall(Blocks.WAXED_EXPOSED_CUT_COPPER, BlockRegistryME.WAXED_EXPOSED_CUT_COPPER_WALL));
            add(new Wall(Blocks.WAXED_WEATHERED_CUT_COPPER, BlockRegistryME.WAXED_WEATHERED_CUT_COPPER_WALL));
            add(new Wall(Blocks.WAXED_OXIDIZED_CUT_COPPER, BlockRegistryME.WAXED_OXIDIZED_CUT_COPPER_WALL));

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
