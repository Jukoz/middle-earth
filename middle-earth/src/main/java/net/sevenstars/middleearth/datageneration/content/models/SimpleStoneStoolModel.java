package net.sevenstars.middleearth.datageneration.content.models;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.world.level.block.Block;

public class SimpleStoneStoolModel {

    public record Stool(Block base, Block stool) {}

    public static List<Stool> stools = new ArrayList<>() {
        {
        }
    };
}
