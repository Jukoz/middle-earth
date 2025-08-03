package net.sevenstars.middleearth.datageneration.content.models;

import net.minecraft.block.Block;

import java.util.ArrayList;
import java.util.List;

public class SimpleStoneStoolModel {

    public record Stool(Block base, Block stool) {}

    public static List<Stool> stools = new ArrayList<>() {
        {
        }
    };
}
