package net.jukoz.me.datageneration.content.models;

import net.jukoz.me.block.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;

import java.util.ArrayList;
import java.util.List;

public class SimpleLadderModel {

    public record Ladder(Block block, Block ladder) {}

    public static List<Ladder> ladders = new ArrayList<>() {
        {
        }
    };

    public static List<Ladder> vanillaLadders = new ArrayList<>() {
        {
            add(new SimpleLadderModel.Ladder(Blocks.OAK_PLANKS, ModBlocks.OAK_LADDER));
            add(new SimpleLadderModel.Ladder(Blocks.SPRUCE_PLANKS, ModBlocks.SPRUCE_LADDER));
            add(new SimpleLadderModel.Ladder(Blocks.BIRCH_PLANKS, ModBlocks.BIRCH_LADDER));
            add(new SimpleLadderModel.Ladder(Blocks.JUNGLE_PLANKS, ModBlocks.JUNGLE_LADDER));
            add(new SimpleLadderModel.Ladder(Blocks.ACACIA_PLANKS, ModBlocks.ACACIA_LADDER));
            add(new SimpleLadderModel.Ladder(Blocks.DARK_OAK_PLANKS, ModBlocks.DARK_OAK_LADDER));
            add(new SimpleLadderModel.Ladder(Blocks.MANGROVE_PLANKS, ModBlocks.MANGROVE_LADDER));
            add(new SimpleLadderModel.Ladder(Blocks.CHERRY_PLANKS, ModBlocks.CHERRY_LADDER));
            add(new SimpleLadderModel.Ladder(Blocks.BAMBOO_PLANKS, ModBlocks.BAMBOO_LADDER));
            add(new SimpleLadderModel.Ladder(Blocks.CRIMSON_PLANKS, ModBlocks.CRIMSON_LADDER));
            add(new SimpleLadderModel.Ladder(Blocks.WARPED_PLANKS, ModBlocks.WARPED_LADDER));
        }
    };
}
