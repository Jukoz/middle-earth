package net.sevenstars.middleearth.block.utils.setBuilders;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.sevenstars.middleearth.block.utils.BlockRecordTypes;
import net.sevenstars.middleearth.block.utils.WoodBlockTypes;

import java.util.ArrayList;
import java.util.List;

public class WoodBlockSetBuilder {
    public boolean vanilla = false;
    public final List<WoodBlockTypes> existingList;

    public BlockRecordTypes.WoodSet logBlocks;
    public BlockRecordTypes.WoodSet strippedLogBlocks;

    public BlockRecordTypes.MushroomStemSet mushroomStemBlocks;

    public BlockRecordTypes.PlanksSet planksBlocks;

    public BlockRecordTypes.WoodRedstoneBlocks redstoneBlocks;

    public BlockRecordTypes.WoodFurnitureBlocks furnitureBlocks;

    public BlockRecordTypes.RegularSet roofingBlocks;
    public BlockRecordTypes.RegularSet shinglesBlocks;

    public Block leaves;
    public Block sapling;

    public String setName;
    public float hardness;
    public float blastResistance;
    public MapColor mapColor;
    public MapColor leavesMapColor;
    public NoteBlockInstrument instrument;
    public SoundType soundGroup;

    public WoodBlockSetBuilder(String name, float hardness, float blastResistance,
                               MapColor mapColor, NoteBlockInstrument instrument, SoundType soundGroup, Block sapling){
        this.setName = name;
        this.hardness = hardness;
        this.blastResistance = blastResistance;
        this.mapColor = mapColor;
        this.instrument = instrument;
        this.soundGroup = soundGroup;
        this.sapling = sapling;

        this.existingList = new ArrayList<>();
    }

    public WoodBlockSetBuilder addToSet(WoodBlockTypes type) {
        this.existingList.add(type);
        return this;
    }

    public WoodBlockSetBuilder vanilla(boolean vanilla) {
        this.vanilla = vanilla;
        return this;
    }

    public WoodBlockSetBuilder leavesColor(MapColor leavesMapColor) {
        this.leavesMapColor = leavesMapColor;
        return this;
    }
}
