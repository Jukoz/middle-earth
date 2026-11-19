package net.sevenstars.middleearth.features.storage;

import net.sevenstars.middleearth.features.storage.block.coffers.CofferShapes;
import net.sevenstars.middleearth.features.storage.block.coffers.CofferBlock;
import net.minecraft.block.*;
import net.minecraft.block.enums.NoteBlockInstrument;
import net.minecraft.sound.BlockSoundGroup;
import net.sevenstars.middleearth.block.registration.DecorativeBlockRegistryME;
import net.sevenstars.middleearth.features.storage.block.CrateBlock;
import net.sevenstars.middleearth.features.storage.block.ThinBarrelBlock;
import net.sevenstars.middleearth.features.storage.block.reinforcedChest.ReinforcedChestBlock;
import net.sevenstars.middleearth.features.storage.block.sack.SackBlock;
import net.sevenstars.middleearth.features.storage.block.wood_pile.WoodPileBlock;

public class StorageBlocks {
    public static final Block SMALL_CRATE = DecorativeBlockRegistryME.registerBlock("small_crate",
            CrateBlock::new, AbstractBlock.Settings.create().mapColor(MapColor.OAK_TAN).instrument(NoteBlockInstrument.BASS).strength(2.0F, 3.0F).sounds(BlockSoundGroup.WOOD).nonOpaque());

    public static final Block THIN_BARREL = DecorativeBlockRegistryME.registerBlock("thin_barrel",
            ThinBarrelBlock::new, AbstractBlock.Settings.create().mapColor(MapColor.OAK_TAN).instrument(NoteBlockInstrument.BASS).strength(2.0F, 3.0F).sounds(BlockSoundGroup.WOOD).nonOpaque());

    public static final Block LARCH_COFFER = DecorativeBlockRegistryME.registerBlock("larch_coffer",
            settings -> new CofferBlock(() -> StorageBlockEntities.LARCH_COFFER, CofferShapes::larch, settings), AbstractBlock.Settings.create().mapColor(MapColor.OAK_TAN)
                    .instrument(NoteBlockInstrument.BASS).strength(2.0f, 3.0F).sounds(BlockSoundGroup.WOOD).nonOpaque());

    public static final Block PINE_COFFER = DecorativeBlockRegistryME.registerBlock("pine_coffer",
            settings -> new CofferBlock(() -> StorageBlockEntities.PINE_COFFER, CofferShapes::pine, settings), AbstractBlock.Settings.create().mapColor(MapColor.OAK_TAN)
                    .instrument(NoteBlockInstrument.BASS).strength(2.0f, 3.0F).sounds(BlockSoundGroup.WOOD).nonOpaque());

    public static final Block SPRUCE_COFFER = DecorativeBlockRegistryME.registerBlock("spruce_coffer",
            settings -> new CofferBlock(() -> StorageBlockEntities.SPRUCE_COFFER, CofferShapes::spruce, settings), AbstractBlock.Settings.create().mapColor(MapColor.SPRUCE_BROWN)
                    .instrument(NoteBlockInstrument.BASS).strength(2.0f, 3.0F).sounds(BlockSoundGroup.WOOD).nonOpaque());

    public static final Block FIR_COFFER = DecorativeBlockRegistryME.registerBlock("fir_coffer",
            settings -> new CofferBlock(() -> StorageBlockEntities.FIR_COFFER, CofferShapes::fir, settings), AbstractBlock.Settings.create().mapColor(MapColor.OAK_TAN)
                    .instrument(NoteBlockInstrument.BASS).strength(2.0f, 3.0F).sounds(BlockSoundGroup.WOOD).nonOpaque());

    public static final Block BEECH_COFFER = DecorativeBlockRegistryME.registerBlock("beech_coffer",
            settings -> new CofferBlock(() -> StorageBlockEntities.BEECH_COFFER, CofferShapes::beech, settings), AbstractBlock.Settings.create().mapColor(MapColor.OAK_TAN)
                    .instrument(NoteBlockInstrument.BASS).strength(2.0f, 3.0F).sounds(BlockSoundGroup.WOOD).nonOpaque());

    public static final Block CHESTNUT_COFFER = DecorativeBlockRegistryME.registerBlock("chestnut_coffer",
            settings -> new CofferBlock(() -> StorageBlockEntities.CHESTNUT_COFFER, CofferShapes::chestnut, settings), AbstractBlock.Settings.create().mapColor(MapColor.OAK_TAN)
                    .instrument(NoteBlockInstrument.BASS).strength(2.0f, 3.0F).sounds(BlockSoundGroup.WOOD).nonOpaque());

    public static final Block OAK_COFFER = DecorativeBlockRegistryME.registerBlock("oak_coffer",
            settings -> new CofferBlock(() -> StorageBlockEntities.OAK_COFFER, CofferShapes::oak, settings), AbstractBlock.Settings.create().mapColor(MapColor.OAK_TAN)
                    .instrument(NoteBlockInstrument.BASS).strength(2.0f, 3.0F).sounds(BlockSoundGroup.WOOD).nonOpaque());

    public static final Block WILLOW_COFFER = DecorativeBlockRegistryME.registerBlock("willow_coffer",
            settings -> new CofferBlock(() -> StorageBlockEntities.WILLOW_COFFER, CofferShapes::willow, settings), AbstractBlock.Settings.create().mapColor(MapColor.OAK_TAN)
                    .instrument(NoteBlockInstrument.BASS).strength(2.0f, 3.0F).sounds(BlockSoundGroup.WOOD).nonOpaque());

    public static final Block REINFORCED_CHEST = DecorativeBlockRegistryME.registerBlock("reinforced_chest",
            ReinforcedChestBlock::new, AbstractBlock.Settings.create().mapColor(MapColor.OAK_TAN).instrument(NoteBlockInstrument.BASS).strength(5.0f).sounds(BlockSoundGroup.WOOD).nonOpaque().requiresTool());

    public static final Block SACK = DecorativeBlockRegistryME.registerBlock("sack",
            SackBlock::new, AbstractBlock.Settings.create().mapColor(MapColor.TERRACOTTA_WHITE).sounds(BlockSoundGroup.WOOL).nonOpaque());

    public static final Block WOOD_PILE = DecorativeBlockRegistryME.registerBlock("wood_pile",
            WoodPileBlock::new, AbstractBlock.Settings.copy(Blocks.OAK_PLANKS).strength(1.0f).nonOpaque());
}
