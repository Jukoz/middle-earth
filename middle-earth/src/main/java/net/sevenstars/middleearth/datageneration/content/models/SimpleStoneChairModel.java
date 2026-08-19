package net.sevenstars.middleearth.datageneration.content.models;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.world.level.block.Block;

public class SimpleStoneChairModel {

    public record Chair(Block base, Block chair) {}

    public static List<Chair> chairs = new ArrayList<>() {
        {
        }
    };
}
