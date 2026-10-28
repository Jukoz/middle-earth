package net.sevenstars.middleearth.datageneration.content.models;

import net.sevenstars.middleearth.block.registration.NatureBlockRegistryME;
import net.minecraft.block.Block;

import java.util.ArrayList;
import java.util.List;

public class SimpleFlowerBedModel {
    public static List<Block> flowerBeds = new ArrayList<>() {
        {
            add(NatureBlockRegistryME.BROWN_BOLETE_TILLER);
            add(NatureBlockRegistryME.CAVE_AMANITA_TILLER);
            add(NatureBlockRegistryME.DEEP_FIRECAP_TILLER);
            add(NatureBlockRegistryME.GHOSTSHROOM_TILLER);
            add(NatureBlockRegistryME.MORSEL_TILLER);
            add(NatureBlockRegistryME.SKY_FIRECAP_TILLER);
            add(NatureBlockRegistryME.VIOLET_CAPS_TILLER);
            add(NatureBlockRegistryME.WHITE_MUSHROOM_TILLER);
            add(NatureBlockRegistryME.YELLOW_AMANITA_TILLER);
        }
    };
}
