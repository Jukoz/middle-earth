package net.sevenstars.middleearth.datageneration.content.models;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.world.level.block.Block;

public class SimplePressurePlateModel {
    public record PressurePlate(Block block, Block pressurePlate) {}
    public static List<PressurePlate> pressurePlates = new ArrayList<>() {
        {
        }
    };
}
