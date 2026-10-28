package net.sevenstars.middleearth.datageneration.content.models;

import net.sevenstars.middleearth.block.registration.NatureBlockRegistryME;
import net.minecraft.block.Block;

import java.util.ArrayList;
import java.util.List;

public class SimpleMushroomBlockModel {
    public static List<Block> mushroomBlocks = new ArrayList<>() {
        {
            add(NatureBlockRegistryME.BROWN_BOLETE_BLOCK);
            add(NatureBlockRegistryME.CAVE_AMANITA_BLOCK);
            add(NatureBlockRegistryME.DEEP_FIRECAP_BLOCK);
            add(NatureBlockRegistryME.SKY_FIRECAP_BLOCK);
            add(NatureBlockRegistryME.YELLOW_AMANITA_BLOCK);
        }
    };
}
