package net.sevenstars.middleearth.block.utils.setBuilders;

import net.minecraft.block.MapColor;
import net.minecraft.block.Oxidizable;
import net.minecraft.block.enums.NoteBlockInstrument;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.BlockSoundGroup;
import net.sevenstars.middleearth.block.utils.BlockRecordTypes;

import java.util.List;

public class OxidizableBlockSetBuilder extends GenericBlockSetBuilder{

    public Oxidizable.OxidationLevel level;

    public OxidizableBlockSetBuilder(String name, float hardness, float blastResistance,
                                     MapColor mapColor, NoteBlockInstrument instrument, BlockSoundGroup soundGroup, boolean requiresTool,
                                     List<ItemStack> group, Oxidizable.OxidationLevel level){
        super(name, hardness, blastResistance, mapColor, instrument, soundGroup, requiresTool, group);
        this.level = level;
    }
}
