package net.sevenstars.middleearth.block.utils;

import net.minecraft.block.Block;
import net.minecraft.block.MapColor;
import net.minecraft.block.enums.NoteBlockInstrument;
import net.minecraft.sound.BlockSoundGroup;

import java.util.ArrayList;
import java.util.List;

public class StoneBlockSetBuilder {

    public final List<StoneBlockTypes> existingList;

    public BlockRecordTypes.BaseStoneSet baseBlocks;

    public BlockRecordTypes.RegularSet cobblestoneBlocks;
    public BlockRecordTypes.RegularSet mossyCobblestoneBlocks;

    public BlockRecordTypes.RegularSet brickBlocks;
    public BlockRecordTypes.RegularSet mossyBrickBlocks;
    public BlockRecordTypes.RegularSet crackedBrickBlocks;

    public BlockRecordTypes.RegularSet tileBlocks;
    public BlockRecordTypes.RegularSet mossyTileBlocks;
    public BlockRecordTypes.RegularSet crackedTileBlocks;

    public BlockRecordTypes.RegularSet smoothBlocks;
    public BlockRecordTypes.RegularSet mossySmoothBlocks;
    public BlockRecordTypes.RegularSet crackedSmoothBlocks;

    public BlockRecordTypes.RegularSet polishedBlocks;
    public BlockRecordTypes.RegularSet mossyPolishedBlocks;
    public BlockRecordTypes.RegularSet crackedPolishedBlocks;

    public BlockRecordTypes.RegularSet brickworkBlocks;

    public BlockRecordTypes.PillarSet pillarBlocks;
    public BlockRecordTypes.PillarSet mossyPillarBlocks;
    public BlockRecordTypes.PillarSet crackedPillarBlocks;

    public BlockRecordTypes.PillarSet chiseledBlocks;
    public BlockRecordTypes.PillarSet chiseledBricksBlocks;
    public BlockRecordTypes.PillarSet chiseledPolishedBlocks;
    public BlockRecordTypes.PillarSet chiseledTilesBlocks;
    public BlockRecordTypes.PillarSet chiseledSmoothBlocks;

    public BlockRecordTypes.RegularSet oldBlocks;

    public BlockRecordTypes.CarvedWindow carvedWindows;

    public String setName;
    public Block source;
    public float hardness;
    public float blastResistance;
    public MapColor mapColor;
    public NoteBlockInstrument instrument;
    public BlockSoundGroup soundGroup;
    public boolean hasMossy;
    public boolean hasCracked;

    public StoneBlockSetBuilder(String name, Block source, float hardness, float blastResistance,
                                MapColor mapColor, NoteBlockInstrument instrument, BlockSoundGroup soundGroup, boolean hasMossy, boolean hasCracked){
        this.setName = name;
        this.source = source;
        this.hardness = hardness;
        this.blastResistance = blastResistance;
        this.mapColor = mapColor;
        this.instrument = instrument;
        this.soundGroup = soundGroup;
        this.hasMossy = hasMossy;
        this.hasCracked = hasCracked;

        this.existingList = new ArrayList<>();
    }

    public StoneBlockSetBuilder addToSet(StoneBlockTypes type) {
        this.existingList.add(type);
        return this;
    }
}
