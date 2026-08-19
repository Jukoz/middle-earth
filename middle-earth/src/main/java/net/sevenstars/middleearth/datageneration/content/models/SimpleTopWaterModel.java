package net.sevenstars.middleearth.datageneration.content.models;

import net.minecraft.world.level.block.Block;
import net.sevenstars.middleearth.block.registration.ModNatureBlocks;
import java.util.ArrayList;
import java.util.List;

public class SimpleTopWaterModel {
    public static List<Block> topWaterBlocks = new ArrayList<>() {
        {
            add(ModNatureBlocks.FLOATING_ICE);
        }
    };
}
