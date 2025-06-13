package net.sevenstars.middleearth.datageneration.content.models;

import net.minecraft.block.Block;

import java.util.ArrayList;
import java.util.List;

public class SimpleStoneChairModel {

    public record Chair(Block base, Block chair) {}

    public static List<Chair> chairs = new ArrayList<>() {
        {
        }
    };
}
