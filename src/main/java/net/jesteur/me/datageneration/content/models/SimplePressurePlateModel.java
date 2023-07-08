package net.jesteur.me.datageneration.content.models;

import net.minecraft.block.Block;

import java.util.ArrayList;
import java.util.List;

public class SimplePressurePlateModel {
    public record PressurePlate(Block block, Block pressurePlate) {}
    public static List<PressurePlate> blocks = new ArrayList<>() {
        {
        }
    };
}
