package net.sevenstars.middleearth.datageneration.content.models;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.world.level.block.Block;

public class SimpleStoneTableModel {
    public record Table(Block base, Block table) {}


    public static List<Table> tables = new ArrayList<>() {
        {
        }
    };
}
