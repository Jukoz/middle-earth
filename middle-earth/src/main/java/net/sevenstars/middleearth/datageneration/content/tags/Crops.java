package net.sevenstars.middleearth.datageneration.content.tags;

import net.sevenstars.middleearth.block.registration.NatureBlockRegistryME;
import net.minecraft.block.Block;

import java.util.ArrayList;
import java.util.List;

public class Crops {
    public static List<Block> crops = new ArrayList<>() {
        {
            add(NatureBlockRegistryME.TOMATO_CROP);
            add(NatureBlockRegistryME.BELL_PEPPER_CROP);
            add(NatureBlockRegistryME.CUCUMBER_CROP);
            add(NatureBlockRegistryME.FLAX_CROP);
            add(NatureBlockRegistryME.GARLIC_CROP);
            add(NatureBlockRegistryME.LEEK_CROP);
            add(NatureBlockRegistryME.LETTUCE_CROP);
            add(NatureBlockRegistryME.ONION_CROP);
            add(NatureBlockRegistryME.PIPEWEED_CROP);
        }
    };
}
