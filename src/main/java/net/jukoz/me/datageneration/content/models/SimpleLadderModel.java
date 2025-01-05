package net.jukoz.me.datageneration.content.models;

import net.jukoz.me.block.ModBlocks;
import net.jukoz.me.block.ModDecorativeBlocks;
import net.jukoz.me.block.OtherBlockSets;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;

import java.util.ArrayList;
import java.util.List;

public class SimpleLadderModel {

    public record Ladder(Block block, Block ladder) {}

    public static List<Ladder> ladders = new ArrayList<>() {
        {
            add(new Ladder(OtherBlockSets.TREATED_WOOD_PLANKS.block(), ModDecorativeBlocks.TREATED_WOOD_LADDER));
        }
    };

    public static List<Ladder> vanillaLadders = new ArrayList<>() {
        {
            add(new SimpleLadderModel.Ladder(Blocks.OAK_PLANKS, ModDecorativeBlocks.OAK_LADDER));
            add(new SimpleLadderModel.Ladder(Blocks.SPRUCE_PLANKS, ModDecorativeBlocks.SPRUCE_LADDER));
            add(new SimpleLadderModel.Ladder(Blocks.BIRCH_PLANKS, ModDecorativeBlocks.BIRCH_LADDER));
            add(new SimpleLadderModel.Ladder(Blocks.JUNGLE_PLANKS, ModDecorativeBlocks.JUNGLE_LADDER));
            add(new SimpleLadderModel.Ladder(Blocks.ACACIA_PLANKS, ModDecorativeBlocks.ACACIA_LADDER));
            add(new SimpleLadderModel.Ladder(Blocks.DARK_OAK_PLANKS, ModDecorativeBlocks.DARK_OAK_LADDER));
            add(new SimpleLadderModel.Ladder(Blocks.MANGROVE_PLANKS, ModDecorativeBlocks.MANGROVE_LADDER));
            add(new SimpleLadderModel.Ladder(Blocks.CHERRY_PLANKS, ModDecorativeBlocks.CHERRY_LADDER));
            add(new SimpleLadderModel.Ladder(Blocks.BAMBOO_PLANKS, ModDecorativeBlocks.BAMBOO_LADDER));
            add(new SimpleLadderModel.Ladder(Blocks.CRIMSON_PLANKS, ModDecorativeBlocks.CRIMSON_LADDER));
            add(new SimpleLadderModel.Ladder(Blocks.WARPED_PLANKS, ModDecorativeBlocks.WARPED_LADDER));
        }
    };
}
