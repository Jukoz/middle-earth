package net.sevenstars.middleearth.datageneration.content.models;

import net.sevenstars.middleearth.block.registration.ModDecorativeBlocks;
import net.sevenstars.middleearth.block.registration.GenericBlockSets;
import net.minecraft.block.Block;

import java.util.ArrayList;
import java.util.List;

public class SimpleLadderModel {

    public record Ladder(Block block, Block ladder) {}

    public static List<Ladder> ladders = new ArrayList<>() {
        {
            add(new Ladder(GenericBlockSets.TREATED_WOOD_PLANKS.blockSet.base(), ModDecorativeBlocks.TREATED_WOOD_LADDER));
        }
    };

    public static List<Ladder> vanillaLadders = new ArrayList<>() {
        {

        }
    };
}
