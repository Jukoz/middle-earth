package net.jukoz.me.datageneration.content.models;

import net.jukoz.me.block.ModBlocks;
import net.minecraft.block.Block;

import java.util.ArrayList;
import java.util.List;

public class SimplePillarModel {
    public record Pillar(Block base) {}

    public static List<Pillar> blocks = new ArrayList<>() {
        {
            add(new Pillar(ModBlocks.LIMESTONE_PILLAR));

            add(new Pillar(ModBlocks.CALCITE_PILLAR));

            add(new Pillar(ModBlocks.REED_BLOCK));
            add(new Pillar(ModBlocks.STRAW_BLOCK));

            add(new Pillar(ModBlocks.ANDESITE_PILLAR));
            add(new Pillar(ModBlocks.MOSSY_ANDESITE_PILLAR));
            add(new Pillar(ModBlocks.CRACKED_ANDESITE_PILLAR));

            add(new Pillar(ModBlocks.GRANITE_PILLAR));
            add(new Pillar(ModBlocks.MOSSY_GRANITE_PILLAR));
            add(new Pillar(ModBlocks.CRACKED_GRANITE_PILLAR));

            add(new Pillar(ModBlocks.DIORITE_PILLAR));
            add(new Pillar(ModBlocks.MOSSY_DIORITE_PILLAR));
            add(new Pillar(ModBlocks.CRACKED_DIORITE_PILLAR));
            
            add(new Pillar(ModBlocks.JADEITE_PILLAR));
        }
    };
}
