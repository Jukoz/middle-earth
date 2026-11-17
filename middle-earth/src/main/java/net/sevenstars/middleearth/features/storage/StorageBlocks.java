package net.sevenstars.middleearth.features.storage;

import net.minecraft.block.*;
import net.minecraft.block.enums.NoteBlockInstrument;
import net.minecraft.sound.BlockSoundGroup;
import net.sevenstars.middleearth.block.registration.DecorativeBlockRegistryME;
import net.sevenstars.middleearth.features.storage.block.CrateBlock;
import net.sevenstars.middleearth.features.storage.block.ThinBarrelBlock;
import net.sevenstars.middleearth.features.storage.block.coffers.BeechCofferBlock;
import net.sevenstars.middleearth.features.storage.block.coffers.ChestnutCofferBlock;
import net.sevenstars.middleearth.features.storage.block.coffers.FirCofferBlock;
import net.sevenstars.middleearth.features.storage.block.coffers.LarchCofferBlock;
import net.sevenstars.middleearth.features.storage.block.coffers.OakCofferBlock;
import net.sevenstars.middleearth.features.storage.block.coffers.PineCofferBlock;
import net.sevenstars.middleearth.features.storage.block.coffers.SpruceCofferBlock;
import net.sevenstars.middleearth.features.storage.block.coffers.WillowCofferBlock;
import net.sevenstars.middleearth.features.storage.block.reinforcedChest.ReinforcedChestBlock;
import net.sevenstars.middleearth.features.storage.block.sack.SackBlock;
import net.sevenstars.middleearth.features.storage.block.wood_pile.WoodPileBlock;

public class StorageBlocks {
    public static final Block SMALL_CRATE = DecorativeBlockRegistryME.registerBlock("small_crate",
            CrateBlock::new, AbstractBlock.Settings.create().mapColor(MapColor.OAK_TAN).instrument(NoteBlockInstrument.BASS).strength(2.0F, 3.0F).sounds(BlockSoundGroup.WOOD).nonOpaque());

    public static final Block THIN_BARREL = DecorativeBlockRegistryME.registerBlock("thin_barrel",
            ThinBarrelBlock::new, AbstractBlock.Settings.create().mapColor(MapColor.OAK_TAN).instrument(NoteBlockInstrument.BASS).strength(2.0F, 3.0F).sounds(BlockSoundGroup.WOOD).nonOpaque());

    public static final Block LARCH_COFFER = DecorativeBlockRegistryME.registerBlock("larch_coffer",
            LarchCofferBlock::new, AbstractBlock.Settings.create().mapColor(MapColor.OAK_TAN)
                    .instrument(NoteBlockInstrument.BASS).strength(2.0f, 3.0F).sounds(BlockSoundGroup.WOOD).nonOpaque());

    public static final Block PINE_COFFER = DecorativeBlockRegistryME.registerBlock("pine_coffer",
            PineCofferBlock::new, AbstractBlock.Settings.create().mapColor(MapColor.OAK_TAN)
                    .instrument(NoteBlockInstrument.BASS).strength(2.0f, 3.0F).sounds(BlockSoundGroup.WOOD).nonOpaque());

    public static final Block SPRUCE_COFFER = DecorativeBlockRegistryME.registerBlock("spruce_coffer",
            SpruceCofferBlock::new, AbstractBlock.Settings.create().mapColor(MapColor.SPRUCE_BROWN)
                    .instrument(NoteBlockInstrument.BASS).strength(2.0f, 3.0F).sounds(BlockSoundGroup.WOOD).nonOpaque());

    public static final Block FIR_COFFER = DecorativeBlockRegistryME.registerBlock("fir_coffer",
            FirCofferBlock::new, AbstractBlock.Settings.create().mapColor(MapColor.OAK_TAN)
                    .instrument(NoteBlockInstrument.BASS).strength(2.0f, 3.0F).sounds(BlockSoundGroup.WOOD).nonOpaque());

    public static final Block BEECH_COFFER = DecorativeBlockRegistryME.registerBlock("beech_coffer",
            BeechCofferBlock::new, AbstractBlock.Settings.create().mapColor(MapColor.OAK_TAN)
                    .instrument(NoteBlockInstrument.BASS).strength(2.0f, 3.0F).sounds(BlockSoundGroup.WOOD).nonOpaque());

    public static final Block CHESTNUT_COFFER = DecorativeBlockRegistryME.registerBlock("chestnut_coffer",
            ChestnutCofferBlock::new, AbstractBlock.Settings.create().mapColor(MapColor.OAK_TAN)
                    .instrument(NoteBlockInstrument.BASS).strength(2.0f, 3.0F).sounds(BlockSoundGroup.WOOD).nonOpaque());

    public static final Block OAK_COFFER = DecorativeBlockRegistryME.registerBlock("oak_coffer",
            OakCofferBlock::new, AbstractBlock.Settings.create().mapColor(MapColor.OAK_TAN)
                    .instrument(NoteBlockInstrument.BASS).strength(2.0f, 3.0F).sounds(BlockSoundGroup.WOOD).nonOpaque());

    public static final Block WILLOW_COFFER = DecorativeBlockRegistryME.registerBlock("willow_coffer",
            WillowCofferBlock::new, AbstractBlock.Settings.create().mapColor(MapColor.OAK_TAN)
                    .instrument(NoteBlockInstrument.BASS).strength(2.0f, 3.0F).sounds(BlockSoundGroup.WOOD).nonOpaque());

    public static final Block REINFORCED_CHEST = DecorativeBlockRegistryME.registerBlock("reinforced_chest",
            ReinforcedChestBlock::new, AbstractBlock.Settings.create().mapColor(MapColor.OAK_TAN).instrument(NoteBlockInstrument.BASS).strength(5.0f).sounds(BlockSoundGroup.WOOD).nonOpaque().requiresTool());

    public static final Block SACK = DecorativeBlockRegistryME.registerBlock("sack",
            SackBlock::new, AbstractBlock.Settings.create().mapColor(MapColor.TERRACOTTA_WHITE).sounds(BlockSoundGroup.WOOL).nonOpaque());

    public static final Block WOOD_PILE = DecorativeBlockRegistryME.registerBlock("wood_pile",
            WoodPileBlock::new, AbstractBlock.Settings.copy(Blocks.OAK_PLANKS).strength(1.0f).nonOpaque());

    public static void register() {
    }
}
