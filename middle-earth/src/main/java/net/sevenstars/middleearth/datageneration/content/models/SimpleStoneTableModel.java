package net.sevenstars.middleearth.datageneration.content.models;

import net.minecraft.block.Block;

import java.util.ArrayList;
import java.util.List;

public class SimpleStoneTableModel {
    public record Table(Block base, Block table) {}


    public static List<Table> tables = new ArrayList<>() {
        {
        }
    };
}
