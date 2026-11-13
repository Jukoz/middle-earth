package net.sevenstars.middleearth.datageneration.content.models;

import net.sevenstars.middleearth.block.registration.NatureBlockRegistryME;
import net.sevenstars.middleearth.item.ResourceItemsME;
import net.minecraft.block.Block;

import java.util.ArrayList;
import java.util.List;

public class SimpleDoubleBlockModel {
    public static List<Block> doubleBlocks = new ArrayList<>() {
        {
            add(NatureBlockRegistryME.WILD_PIPEWEED);
            add(NatureBlockRegistryME.TALL_WILD_WHEAT);

            add(NatureBlockRegistryME.TALL_TUBESHROOM);
            add(NatureBlockRegistryME.TALL_TRUMPET_SHROOM);

            add(NatureBlockRegistryME.HOGWEED);

            add(NatureBlockRegistryME.TALL_CATTAILS);
            add(NatureBlockRegistryME.TALL_BULRUSH);
        }
    };

    public static List<Block> doubleBlocksItems = new ArrayList<>() {
        {
            add(ResourceItemsME.REEDS);
        }
    };
}
