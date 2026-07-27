package net.sevenstars.middleearth.block.utils.setBuilders;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.WeatheringCopper;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.sevenstars.middleearth.block.utils.BlockRecordTypes;

import java.util.List;

public class OxidizableBlockSetBuilder extends GenericBlockSetBuilder{

    public WeatheringCopper.WeatherState level;

    public OxidizableBlockSetBuilder(String name, float hardness, float blastResistance,
                                     MapColor mapColor, NoteBlockInstrument instrument, SoundType soundGroup, boolean requiresTool,
                                     List<ItemStack> group, WeatheringCopper.WeatherState level){
        super(name, hardness, blastResistance, mapColor, instrument, soundGroup, requiresTool, group);
        this.level = level;
    }
}
