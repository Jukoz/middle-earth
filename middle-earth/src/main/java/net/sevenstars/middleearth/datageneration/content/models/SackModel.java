package net.sevenstars.middleearth.datageneration.content.models;

import net.minecraft.block.Block;
import net.sevenstars.middleearth.block.registration.DecorativeBlockRegistryME;

import java.util.ArrayList;
import java.util.List;

public class SackModel {
    public static List<Block> sacks = new ArrayList<>(){
        {
            add(DecorativeBlockRegistryME.BLACK_SACK);
            add(DecorativeBlockRegistryME.BLUE_SACK);
            add(DecorativeBlockRegistryME.BROWN_SACK);
            add(DecorativeBlockRegistryME.CYAN_SACK);
            add(DecorativeBlockRegistryME.GREEN_SACK);
            add(DecorativeBlockRegistryME.GRAY_SACK);
            add(DecorativeBlockRegistryME.LIGHT_BLUE_SACK);
            add(DecorativeBlockRegistryME.LIGHT_GRAY_SACK);
            add(DecorativeBlockRegistryME.LIME_SACK);
            add(DecorativeBlockRegistryME.MAGENTA_SACK);
            add(DecorativeBlockRegistryME.ORANGE_SACK);
            add(DecorativeBlockRegistryME.PINK_SACK);
            add(DecorativeBlockRegistryME.PURPLE_SACK);
            add(DecorativeBlockRegistryME.RED_SACK);
            add(DecorativeBlockRegistryME.YELLOW_SACK);
            add(DecorativeBlockRegistryME.WHITE_SACK);
        }
    };
}
