package net.sevenstars.middleearth.features.farming;

import net.minecraft.block.*;
import net.sevenstars.middleearth.block.registration.NatureBlockRegistryME;
import net.sevenstars.middleearth.features.farming.block.crop.BellpepperCropBlock;
import net.sevenstars.middleearth.features.farming.block.crop.CucumberCropBlock;
import net.sevenstars.middleearth.features.farming.block.crop.FlaxCropBlock;
import net.sevenstars.middleearth.features.farming.block.crop.GarlicCropBlock;
import net.sevenstars.middleearth.features.farming.block.crop.LeekCropBlock;
import net.sevenstars.middleearth.features.farming.block.crop.LettuceCropBlock;
import net.sevenstars.middleearth.features.farming.block.crop.OnionCropBlock;
import net.sevenstars.middleearth.features.farming.block.crop.PipeweedCropBlock;
import net.sevenstars.middleearth.features.farming.block.crop.TomatoCropBlock;

public class FarmingBlocks {
    public static final Block TOMATO_CROP = NatureBlockRegistryME.registerBlock("tomato_crop",
            TomatoCropBlock::new, AbstractBlock.Settings.copy(Blocks.POTATOES), false);

    public static final Block BELL_PEPPER_CROP = NatureBlockRegistryME.registerBlock("bell_pepper_crop",
            BellpepperCropBlock::new, AbstractBlock.Settings.copy(Blocks.POTATOES), false);

    public static final Block CUCUMBER_CROP = NatureBlockRegistryME.registerBlock("cucumber_crop",
            CucumberCropBlock::new, AbstractBlock.Settings.copy(Blocks.POTATOES), false);

    public static final Block FLAX_CROP = NatureBlockRegistryME.registerBlock("flax_crop",
            FlaxCropBlock::new, AbstractBlock.Settings.copy(Blocks.POTATOES), false);

    public static final Block GARLIC_CROP = NatureBlockRegistryME.registerBlock("garlic_crop",
            GarlicCropBlock::new, AbstractBlock.Settings.copy(Blocks.POTATOES), false);

    public static final Block LEEK_CROP = NatureBlockRegistryME.registerBlock("leek_crop",
            LeekCropBlock::new, AbstractBlock.Settings.copy(Blocks.POTATOES), false);

    public static final Block LETTUCE_CROP = NatureBlockRegistryME.registerBlock("lettuce_crop",
            LettuceCropBlock::new, AbstractBlock.Settings.copy(Blocks.POTATOES), false);

    public static final Block ONION_CROP = NatureBlockRegistryME.registerBlock("onion_crop",
            OnionCropBlock::new, AbstractBlock.Settings.copy(Blocks.POTATOES), false);

    public static final Block PIPEWEED_CROP = NatureBlockRegistryME.registerBlock("pipeweed_crop",
            PipeweedCropBlock::new, AbstractBlock.Settings.copy(Blocks.POTATOES), false);
}
