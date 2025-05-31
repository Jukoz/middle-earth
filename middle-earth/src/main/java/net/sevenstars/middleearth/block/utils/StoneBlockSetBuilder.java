package net.sevenstars.middleearth.block.utils;

import net.minecraft.block.MapColor;
import net.minecraft.block.enums.NoteBlockInstrument;
import net.minecraft.sound.BlockSoundGroup;

public class StoneBlockSetBuilder {

    public boolean cobblestoneBlocks;
    public boolean brickBlocks;
    public boolean tileBlocks;
    public boolean smoothBlocks;
    public boolean polishedBlocks;
    public boolean polishedBlocksPillar;
    public boolean brickworkBlocks;
    public boolean pillarBlocks;
    public boolean chiseledBlocks;
    public boolean oldBlocks;
    public boolean oldBlocksPillar;

    String setName;
    float hardness;
    float blastResistance;
    MapColor mapColor;
    NoteBlockInstrument instrument;
    BlockSoundGroup soundGroup;

    public StoneBlockSetBuilder(String name, float hardness, float blastResistance, MapColor mapColor, NoteBlockInstrument instrument, BlockSoundGroup soundGroup){
        this.setName = name;
        this.hardness = hardness;
        this.blastResistance = blastResistance;
        this.mapColor = mapColor;
        this.instrument = instrument;
        this.soundGroup = soundGroup;
    }

    public StoneBlockSetBuilder add(StoneBlockTypes type){
        switch (type){
            case COBBLESTONE_BLOCKS -> cobblestoneBlocks = true;
            case BRICK_BLOCKS -> brickBlocks = true;
            case TILE_BLOCKS -> tileBlocks = true;
            case SMOOTH_BLOCKS -> smoothBlocks = true;
            case POLISHED_BLOCKS -> polishedBlocks = true;
            case POLISHED_BLOCKS_PILLAR -> polishedBlocksPillar = true;
            case BRICKWORK_BLOCKS -> brickworkBlocks = true;
            case PILLAR_BLOCKS -> pillarBlocks = true;
            case CHISELED_BLOCKS -> chiseledBlocks = true;
            case OLD_BLOCKS -> oldBlocks = true;
            case OLD_BLOCKS_PILLAR -> oldBlocksPillar = true;
        }
        return this;
    }

    public enum StoneBlockTypes{
        COBBLESTONE_BLOCKS,
        BRICK_BLOCKS,
        TILE_BLOCKS,
        SMOOTH_BLOCKS,
        POLISHED_BLOCKS,
        POLISHED_BLOCKS_PILLAR,
        BRICKWORK_BLOCKS,
        PILLAR_BLOCKS,
        CHISELED_BLOCKS,
        OLD_BLOCKS,
        OLD_BLOCKS_PILLAR,
        ;
    }
}
