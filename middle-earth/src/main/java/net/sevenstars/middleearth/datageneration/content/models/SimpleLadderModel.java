package net.sevenstars.middleearth.datageneration.content.models;

import net.sevenstars.middleearth.block.registration.DecorativeBlockRegistryME;
import net.sevenstars.middleearth.block.registration.GenericBlockSetRegistryME;
import net.minecraft.block.Block;

import java.util.ArrayList;
import java.util.List;

public class SimpleLadderModel {

    public record Ladder(Block block, Block ladder) {}

    public static List<Ladder> ladders = new ArrayList<>() {
        {
            add(new Ladder(GenericBlockSetRegistryME.TREATED_WOOD_PLANKS.blockSet.base(), DecorativeBlockRegistryME.TREATED_WOOD_LADDER));
        }
    };

    public static List<Ladder> vanillaLadders = new ArrayList<>() {
        {

        }
    };
}
