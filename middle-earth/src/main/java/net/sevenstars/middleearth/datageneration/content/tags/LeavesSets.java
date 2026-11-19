package net.sevenstars.middleearth.datageneration.content.tags;

import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.sevenstars.middleearth.block.registration.NatureBlockRegistryME;
import net.minecraft.block.Block;
import net.sevenstars.middleearth.block.registration.WoodBlockSetRegistryME;
import net.sevenstars.middleearth.block.utils.setBuilders.WoodBlockSetBuilder;

import java.util.ArrayList;
import java.util.List;

public class LeavesSets {
    public static List<Block> leaves = new ArrayList<>() {
        {
            add(NatureBlockRegistryME.LEBETHRON_LEAVES);

            add(NatureBlockRegistryME.BERRY_HOLLY_LEAVES);

            add(NatureBlockRegistryME.DRY_LARCH_LEAVES);

            add(NatureBlockRegistryME.FLOWERING_MALLORN_LEAVES);

            add(NatureBlockRegistryME.ORANGE_MAPLE_LEAVES);
            add(NatureBlockRegistryME.RED_MAPLE_LEAVES);
            add(NatureBlockRegistryME.YELLOW_MAPLE_LEAVES);

            add(NatureBlockRegistryME.DRY_PINE_LEAVES);
        }
    };

    public static List<Block> grayscaleLeaves = new ArrayList<>() {
        {

        }
    };

    // The lists above only receive the wood set leaves during datagen
    public static List<Block> getWoodSetLeaves() {
        List<Block> woodSetLeaves = new ArrayList<>();
        for (WoodBlockSetBuilder set : WoodBlockSetRegistryME.woodSetsList) {
            if (set.leaves != null && !Registries.BLOCK.getId(set.leaves).getNamespace().equals("minecraft")) {
                woodSetLeaves.add(set.leaves);
            }
        }
        return woodSetLeaves;
    }

    public static List<Item> getItemLeaves() {
        ArrayList<Item> newList = new ArrayList<>();
        for (Block block : leaves) {
            newList.add(block.asItem());
        }
        for (Block block : grayscaleLeaves) {
            newList.add(block.asItem());
        }
        return newList;
    }
}
