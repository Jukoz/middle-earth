package net.sevenstars.middleearth.datageneration.content.models;

import net.minecraft.block.Block;

import java.util.ArrayList;
import java.util.List;

public class SimplePressurePlateModel {
    public record PressurePlate(Block block, Block pressurePlate) {}
    public static List<PressurePlate> pressurePlates = new ArrayList<>() {
        {
        }
    };
}
