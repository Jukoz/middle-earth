package net.sevenstars.middleearth.datageneration.content.models;

import net.minecraft.block.Block;

import java.util.ArrayList;
import java.util.List;

public class SimpleButtonModel {
    public record Button(Block block, Block button) {}
    public static List<Button> buttons = new ArrayList<>() {
        {
        }
    };
}
