package net.jukoz.me.datageneration.content.models;

import net.jukoz.me.block.ModDecorativeBlocks;
import net.jukoz.me.block.ModNatureBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;

import java.util.ArrayList;
import java.util.List;

public class SimpleFlowerPotModel {

    public record FlowerPot(Block pottedPlant, Block plant) {}

    public static List<FlowerPot> pots = new ArrayList<>() {
        {
            add(new FlowerPot(ModDecorativeBlocks.POTTED_BEECH_SAPLING, ModNatureBlocks.BEECH_SAPLING));
            add(new FlowerPot(ModDecorativeBlocks.POTTED_CHESTNUT_SAPLING, ModNatureBlocks.CHESTNUT_SAPLING));
            add(new FlowerPot(ModDecorativeBlocks.POTTED_HOLLY_SAPLING, ModNatureBlocks.HOLLY_SAPLING));
            add(new FlowerPot(ModDecorativeBlocks.POTTED_FIR_SAPLING, ModNatureBlocks.FIR_SAPLING));
            add(new FlowerPot(ModDecorativeBlocks.POTTED_LARCH_SAPLING, ModNatureBlocks.LARCH_SAPLING));
            add(new FlowerPot(ModDecorativeBlocks.POTTED_LEBETHRON_SAPLING, ModNatureBlocks.LEBETHRON_SAPLING));
            add(new FlowerPot(ModDecorativeBlocks.POTTED_WHITE_LEBETHRON_SAPLING, ModNatureBlocks.WHITE_LEBETHRON_SAPLING));
            add(new FlowerPot(ModDecorativeBlocks.POTTED_MALLORN_SAPLING, ModNatureBlocks.MALLORN_SAPLING));
            add(new FlowerPot(ModDecorativeBlocks.POTTED_MAPLE_SAPLING, ModNatureBlocks.MAPLE_SAPLING));
            add(new FlowerPot(ModDecorativeBlocks.POTTED_SILVER_MAPLE_SAPLING, ModNatureBlocks.SILVER_MAPLE_SAPLING));
            add(new FlowerPot(ModDecorativeBlocks.POTTED_MIRKWOOD_SAPLING, ModNatureBlocks.MIRKWOOD_SAPLING));
            add(new FlowerPot(ModDecorativeBlocks.POTTED_PALM_SAPLING, ModNatureBlocks.PALM_SAPLING));
            add(new FlowerPot(ModDecorativeBlocks.POTTED_WHITE_PALM_SAPLING, ModNatureBlocks.WHITE_PALM_SAPLING));
            add(new FlowerPot(ModDecorativeBlocks.POTTED_PINE_SAPLING, ModNatureBlocks.PINE_SAPLING));
            add(new FlowerPot(ModDecorativeBlocks.POTTED_BLACK_PINE_SAPLING, ModNatureBlocks.BLACK_PINE_SAPLING));
            add(new FlowerPot(ModDecorativeBlocks.POTTED_WILLOW_SAPLING, ModNatureBlocks.WILLOW_SAPLING));
            
            add(new FlowerPot(ModDecorativeBlocks.POTTED_GREEN_SHRUB, ModNatureBlocks.GREEN_SHRUB));
            add(new FlowerPot(ModDecorativeBlocks.POTTED_MALLOS, ModNatureBlocks.MALLOS));
            add(new FlowerPot(ModDecorativeBlocks.POTTED_YELLOW_FLOWER, ModNatureBlocks.YELLOW_FLOWER));
            add(new FlowerPot(ModDecorativeBlocks.POTTED_YELLOW_TROLLIUS, ModNatureBlocks.YELLOW_TROLLIUS));
            add(new FlowerPot(ModDecorativeBlocks.POTTED_TAN_SHRUB, ModNatureBlocks.TAN_SHRUB));
            add(new FlowerPot(ModDecorativeBlocks.POTTED_GREEN_JEWEL_CORNFLOWER, ModNatureBlocks.GREEN_JEWEL_CORNFLOWER));
            add(new FlowerPot(ModDecorativeBlocks.POTTED_SCORCHED_SHRUB, ModNatureBlocks.SCORCHED_SHRUB));
            add(new FlowerPot(ModDecorativeBlocks.POTTED_FROZEN_SHRUB, ModNatureBlocks.FROZEN_SHRUB));
        }
    };
}
