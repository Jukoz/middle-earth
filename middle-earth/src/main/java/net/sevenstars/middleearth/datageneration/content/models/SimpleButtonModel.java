package net.sevenstars.middleearth.datageneration.content.models;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.world.level.block.Block;

public class SimpleButtonModel {
    public record Button(Block block, Block button) {}
    public static List<Button> buttons = new ArrayList<>() {
        {
        }
    };
}
