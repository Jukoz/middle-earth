package net.sevenstars.middleearth.datageneration.content.models;

import net.sevenstars.middleearth.block.registration.DecorativeBlockRegistryME;
import net.sevenstars.middleearth.block.registration.NatureBlockRegistryME;
import net.minecraft.block.Block;

import java.util.ArrayList;
import java.util.List;

public class SimpleFlowerPotModel {

    public record FlowerPot(Block pottedPlant, Block plant) {}

    public static List<FlowerPot> pots = new ArrayList<>() {
        {
            add(new FlowerPot(DecorativeBlockRegistryME.POTTED_ASPEN_SAPLING, NatureBlockRegistryME.ASPEN_SAPLING));
            add(new FlowerPot(DecorativeBlockRegistryME.POTTED_BEECH_SAPLING, NatureBlockRegistryME.BEECH_SAPLING));
            add(new FlowerPot(DecorativeBlockRegistryME.POTTED_CHESTNUT_SAPLING, NatureBlockRegistryME.CHESTNUT_SAPLING));
            add(new FlowerPot(DecorativeBlockRegistryME.POTTED_HOLLY_SAPLING, NatureBlockRegistryME.HOLLY_SAPLING));
            add(new FlowerPot(DecorativeBlockRegistryME.POTTED_FIR_SAPLING, NatureBlockRegistryME.FIR_SAPLING));
            add(new FlowerPot(DecorativeBlockRegistryME.POTTED_LARCH_SAPLING, NatureBlockRegistryME.LARCH_SAPLING));
            add(new FlowerPot(DecorativeBlockRegistryME.POTTED_LEBETHRON_SAPLING, NatureBlockRegistryME.LEBETHRON_SAPLING));
            add(new FlowerPot(DecorativeBlockRegistryME.POTTED_WHITE_LEBETHRON_SAPLING, NatureBlockRegistryME.WHITE_LEBETHRON_SAPLING));
            add(new FlowerPot(DecorativeBlockRegistryME.POTTED_MALLORN_SAPLING, NatureBlockRegistryME.MALLORN_SAPLING));
            add(new FlowerPot(DecorativeBlockRegistryME.POTTED_MAPLE_SAPLING, NatureBlockRegistryME.MAPLE_SAPLING));
            add(new FlowerPot(DecorativeBlockRegistryME.POTTED_SILVER_MAPLE_SAPLING, NatureBlockRegistryME.SILVER_MAPLE_SAPLING));
            add(new FlowerPot(DecorativeBlockRegistryME.POTTED_MIRKWOOD_SAPLING, NatureBlockRegistryME.MIRKWOOD_SAPLING));
            add(new FlowerPot(DecorativeBlockRegistryME.POTTED_PALM_SAPLING, NatureBlockRegistryME.PALM_SAPLING));
            add(new FlowerPot(DecorativeBlockRegistryME.POTTED_WHITE_PALM_SAPLING, NatureBlockRegistryME.WHITE_PALM_SAPLING));
            add(new FlowerPot(DecorativeBlockRegistryME.POTTED_PINE_SAPLING, NatureBlockRegistryME.PINE_SAPLING));
            add(new FlowerPot(DecorativeBlockRegistryME.POTTED_BLACK_PINE_SAPLING, NatureBlockRegistryME.BLACK_PINE_SAPLING));
            add(new FlowerPot(DecorativeBlockRegistryME.POTTED_WHITE_SPRUCE_SAPLING, NatureBlockRegistryME.WHITE_SPRUCE_SAPLING));
            add(new FlowerPot(DecorativeBlockRegistryME.POTTED_WILLOW_SAPLING, NatureBlockRegistryME.WILLOW_SAPLING));

            add(new FlowerPot(DecorativeBlockRegistryME.POTTED_GREEN_SHRUB, NatureBlockRegistryME.GREEN_SHRUB));
            add(new FlowerPot(DecorativeBlockRegistryME.POTTED_ELANOR, NatureBlockRegistryME.ELANOR));
            add(new FlowerPot(DecorativeBlockRegistryME.POTTED_MALLOS, NatureBlockRegistryME.MALLOS));
            add(new FlowerPot(DecorativeBlockRegistryME.POTTED_NIPHREDIL, NatureBlockRegistryME.NIPHREDIL));
            add(new FlowerPot(DecorativeBlockRegistryME.POTTED_SIMBELMYNE, NatureBlockRegistryME.SIMBELMYNE));
            add(new FlowerPot(DecorativeBlockRegistryME.POTTED_YELLOW_FLOWER, NatureBlockRegistryME.YELLOW_FLOWER));
            add(new FlowerPot(DecorativeBlockRegistryME.POTTED_YELLOW_TROLLIUS, NatureBlockRegistryME.YELLOW_TROLLIUS));
            add(new FlowerPot(DecorativeBlockRegistryME.POTTED_TAN_SHRUB, NatureBlockRegistryME.TAN_SHRUB));
            add(new FlowerPot(DecorativeBlockRegistryME.POTTED_BLUE_GENTIAN, NatureBlockRegistryME.BLUE_GENTIAN));
            add(new FlowerPot(DecorativeBlockRegistryME.POTTED_GREEN_JEWEL_CORNFLOWER, NatureBlockRegistryME.GREEN_JEWEL_CORNFLOWER));
            add(new FlowerPot(DecorativeBlockRegistryME.POTTED_NOBLEWHITE, NatureBlockRegistryME.NOBLEWHITE));
            add(new FlowerPot(DecorativeBlockRegistryME.POTTED_SCORCHED_SHRUB, NatureBlockRegistryME.SCORCHED_SHRUB));
            add(new FlowerPot(DecorativeBlockRegistryME.POTTED_FROZEN_SHRUB, NatureBlockRegistryME.FROZEN_SHRUB));

            add(new FlowerPot(DecorativeBlockRegistryME.POTTED_CAVE_AMANITA, NatureBlockRegistryME.CAVE_AMANITA));
            add(new FlowerPot(DecorativeBlockRegistryME.POTTED_DEEP_FIRECAP, NatureBlockRegistryME.DEEP_FIRECAP));
            add(new FlowerPot(DecorativeBlockRegistryME.POTTED_GHOSTSHROOM, NatureBlockRegistryME.GHOSTSHROOM));
            add(new FlowerPot(DecorativeBlockRegistryME.POTTED_MORSEL, NatureBlockRegistryME.MORSEL));
            add(new FlowerPot(DecorativeBlockRegistryME.POTTED_SKYFIRECAP, NatureBlockRegistryME.SKY_FIRECAP));
            add(new FlowerPot(DecorativeBlockRegistryME.POTTED_TRUMPET_SHROOM, NatureBlockRegistryME.TRUMPET_SHROOM));
            add(new FlowerPot(DecorativeBlockRegistryME.POTTED_TUBESHROOM, NatureBlockRegistryME.TUBESHRROM));
            add(new FlowerPot(DecorativeBlockRegistryME.POTTED_VIOLET_CAPS, NatureBlockRegistryME.VIOLET_CAPS));
            add(new FlowerPot(DecorativeBlockRegistryME.POTTED_WHITE_MUSHROOM, NatureBlockRegistryME.WHITE_MUSHROOM));
            add(new FlowerPot(DecorativeBlockRegistryME.POTTED_YELLOW_AMANITA, NatureBlockRegistryME.YELLOW_AMANITA));
        }
    };
}
