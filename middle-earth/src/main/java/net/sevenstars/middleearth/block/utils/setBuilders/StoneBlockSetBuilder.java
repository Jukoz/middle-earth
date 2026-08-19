package net.sevenstars.middleearth.block.utils.setBuilders;

import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.sevenstars.middleearth.block.utils.BlockRecordTypes;
import net.sevenstars.middleearth.block.utils.StoneBlockTypes;

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
    public float hardness;
    public float blastResistance;
    public MapColor mapColor;
    public NoteBlockInstrument instrument;
    public SoundType soundGroup;
    public boolean hasMossy;
    public boolean hasCracked;
    public boolean isVanilla;
    public boolean hasVanillaCobble;
    public boolean hasVanillaPolished;

    public StoneBlockSetBuilder(String name, float hardness, float blastResistance,
                                MapColor mapColor, NoteBlockInstrument instrument, SoundType soundGroup, boolean hasMossy, boolean hasCracked){
        this(name, hardness, blastResistance, mapColor, instrument, soundGroup, hasMossy, hasCracked,
                false, false, false);
    }

    public StoneBlockSetBuilder(String name, float hardness, float blastResistance,
                                MapColor mapColor, NoteBlockInstrument instrument, SoundType soundGroup,
                                boolean hasMossy, boolean hasCracked, boolean isVanilla,
                                boolean hasVanillaCobble, boolean hasVanillaPolished) {
        this.setName = name;
        this.hardness = hardness;
        this.blastResistance = blastResistance;
        this.mapColor = mapColor;
        this.instrument = instrument;
        this.soundGroup = soundGroup;
        this.hasMossy = hasMossy;
        this.hasCracked = hasCracked;
        this.isVanilla = isVanilla;
        this.hasVanillaCobble = hasVanillaCobble;
        this.hasVanillaPolished = hasVanillaPolished;

        this.existingList = new ArrayList<>();
    }

    public StoneBlockSetBuilder addToSet(StoneBlockTypes type) {
        this.existingList.add(type);
        return this;
    }
}
