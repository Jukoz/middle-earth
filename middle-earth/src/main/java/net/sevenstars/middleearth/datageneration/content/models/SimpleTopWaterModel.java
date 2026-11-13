package net.sevenstars.middleearth.datageneration.content.models;

import net.sevenstars.middleearth.block.registration.NatureBlockRegistryME;
import net.minecraft.block.Block;

import java.util.ArrayList;
import java.util.List;

public class SimpleTopWaterModel {
    public static List<Block> topWaterBlocks = new ArrayList<>() {
        {
            add(NatureBlockRegistryME.FLOATING_ICE);
        }
    };
}
