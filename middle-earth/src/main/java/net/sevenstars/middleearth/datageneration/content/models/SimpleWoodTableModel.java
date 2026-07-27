package net.sevenstars.middleearth.datageneration.content.models;

import net.minecraft.world.level.block.Block;
import net.sevenstars.middleearth.block.registration.ModDecorativeBlocks;
import java.util.ArrayList;
import java.util.List;

public class SimpleWoodTableModel {
    public record VanillaTable(Block base, Block planks, Block origin) {}


    public static List<Block> tables = new ArrayList<>() {
        {
            add(ModDecorativeBlocks.TREATED_WOOD_TABLE);
        }
    };

    public static List<VanillaTable> vanillaTables = new ArrayList<>() {
        {
        }
    };
}
