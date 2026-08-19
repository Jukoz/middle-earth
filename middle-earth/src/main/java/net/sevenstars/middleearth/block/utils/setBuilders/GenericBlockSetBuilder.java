package net.sevenstars.middleearth.block.utils.setBuilders;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.sevenstars.middleearth.block.utils.BlockRecordTypes;
import net.sevenstars.middleearth.block.utils.StoneBlockTypes;

import java.util.ArrayList;
import java.util.List;

public class GenericBlockSetBuilder {

    public BlockRecordTypes.RegularSet blockSet;

    public String setName;
    public float hardness;
    public float blastResistance;
    public MapColor mapColor;
    public NoteBlockInstrument instrument;
    public SoundType soundGroup;
    public boolean requiresTool;
    public List<ItemStack> group;

    public GenericBlockSetBuilder(String name, float hardness, float blastResistance,
                                  MapColor mapColor, NoteBlockInstrument instrument, SoundType soundGroup, boolean requiresTool,
                                  List<ItemStack> group){
        this.setName = name;
        this.hardness = hardness;
        this.blastResistance = blastResistance;
        this.mapColor = mapColor;
        this.instrument = instrument;
        this.soundGroup = soundGroup;
        this.requiresTool = requiresTool;
        this.group = group;
    }
}
