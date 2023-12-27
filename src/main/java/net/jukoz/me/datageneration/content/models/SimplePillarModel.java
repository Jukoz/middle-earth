package net.jukoz.me.datageneration.content.models;

import net.jukoz.me.block.ModBlocks;
import net.jukoz.me.block.StoneBlockSets;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;

import java.util.ArrayList;
import java.util.List;

public class SimplePillarModel {
    public record Pillar(Block base) {}

    public record StonePillar(Block base, Block origin) {}

    public static List<Pillar> blocks = new ArrayList<>() {
        {
            add(new Pillar(ModBlocks.REED_BLOCK));
            add(new Pillar(ModBlocks.STRAW_BLOCK));
        }
    };

    public static List<StonePillar> stonePillars = new ArrayList<>() {
        {
            add(new StonePillar(ModBlocks.LIMESTONE_PILLAR, StoneBlockSets.LIMESTONE_BRICKS.base()));

            add(new StonePillar(ModBlocks.STONE_PILLAR, Blocks.STONE_BRICKS));
            add(new StonePillar(ModBlocks.MOSSY_STONE_PILLAR, ModBlocks.STONE_PILLAR));
            add(new StonePillar(ModBlocks.CRACKED_STONE_PILLAR, ModBlocks.STONE_PILLAR));

            add(new StonePillar(ModBlocks.DEEPSLATE_PILLAR, Blocks.DEEPSLATE_BRICKS));
            add(new StonePillar(ModBlocks.MOSSY_DEEPSLATE_PILLAR, ModBlocks.STONE_PILLAR));
            add(new StonePillar(ModBlocks.CRACKED_DEEPSLATE_PILLAR, ModBlocks.STONE_PILLAR));

            add(new StonePillar(ModBlocks.GONLUIN_PILLAR, StoneBlockSets.GONLUIN_BRICKS.base()));

            add(new StonePillar(ModBlocks.FROZEN_PILLAR, StoneBlockSets.FROZEN_BRICKS.base()));

            add(new StonePillar(ModBlocks.CALCITE_PILLAR, StoneBlockSets.CALCITE_BRICKS.base()));
            add(new StonePillar(ModBlocks.MOSSY_CALCITE_PILLAR, ModBlocks.CALCITE_PILLAR));
            add(new StonePillar(ModBlocks.CRACKED_CALCITE_PILLAR, ModBlocks.CALCITE_PILLAR));

            add(new StonePillar(ModBlocks.ANDESITE_PILLAR, StoneBlockSets.ANDESITE_BRICKS.base()));
            add(new StonePillar(ModBlocks.MOSSY_ANDESITE_PILLAR, ModBlocks.ANDESITE_PILLAR));
            add(new StonePillar(ModBlocks.CRACKED_ANDESITE_PILLAR, ModBlocks.ANDESITE_PILLAR));

            add(new StonePillar(ModBlocks.GRANITE_PILLAR, StoneBlockSets.GRANITE_BRICKS.base()));
            add(new StonePillar(ModBlocks.MOSSY_GRANITE_PILLAR, ModBlocks.GRANITE_PILLAR));
            add(new StonePillar(ModBlocks.CRACKED_GRANITE_PILLAR, ModBlocks.GRANITE_PILLAR));

            add(new StonePillar(ModBlocks.DIORITE_PILLAR, StoneBlockSets.DIORITE_BRICKS.base()));
            add(new StonePillar(ModBlocks.MOSSY_DIORITE_PILLAR, ModBlocks.DIORITE_PILLAR));
            add(new StonePillar(ModBlocks.CRACKED_DIORITE_PILLAR, ModBlocks.DIORITE_PILLAR));

            add(new StonePillar(ModBlocks.JADEITE_PILLAR, StoneBlockSets.JADEITE_BRICKS.base()));
            add(new StonePillar(ModBlocks.CRACKED_JADEITE_PILLAR, ModBlocks.JADEITE_PILLAR));
        }
    };
}
