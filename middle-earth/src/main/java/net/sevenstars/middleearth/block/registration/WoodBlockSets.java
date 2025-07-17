package net.sevenstars.middleearth.block.registration;

import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.fabricmc.fabric.api.registry.StrippableBlockRegistry;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Blocks;
import net.minecraft.block.MapColor;
import net.minecraft.block.TintedParticleLeavesBlock;
import net.minecraft.block.enums.NoteBlockInstrument;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.BlockSoundGroup;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.block.utils.BlockSetRegistration;
import net.sevenstars.middleearth.block.utils.WoodBlockTypes;
import net.sevenstars.middleearth.block.utils.setBuilders.WoodBlockSetBuilder;
import net.sevenstars.middleearth.item.utils.ModItemGroups;

import java.util.ArrayList;
import java.util.List;

import static net.sevenstars.middleearth.block.utils.BlockSetRegistration.getVanillaOrCreateNew;

public class WoodBlockSets {
    public static final float WOOD_STRENGTH = 2.0f;
    public static final float WOOD_BLAST_RESISTANCE = 3.0f;
    public static final float LEAVES_STRENGTH = 0.1f;

    public static List<WoodBlockSetBuilder> woodSetsList = new ArrayList<>();

    public static WoodBlockSetBuilder OAK_SET = registerWoodSet(new WoodBlockSetBuilder("oak",
            WOOD_STRENGTH, WOOD_BLAST_RESISTANCE, MapColor.OAK_TAN, NoteBlockInstrument.BASS, BlockSoundGroup.WOOD, Blocks.OAK_SAPLING)
            .addToSet(WoodBlockTypes.LOG_BLOCKS)
            .addToSet(WoodBlockTypes.STRIPPED_LOG_BLOCKS)
            .addToSet(WoodBlockTypes.PLANK_BLOCKS)
            .addToSet(WoodBlockTypes.REDSTONE_BLOCKS)
            .addToSet(WoodBlockTypes.FURNITURE_BLOCKS)
            .addToSet(WoodBlockTypes.SHINGLE_BLOCKS)
            .addToSet(WoodBlockTypes.ROOFING_BLOCKS)
            .addToSet(WoodBlockTypes.LEAVES));

    public static WoodBlockSetBuilder SPRUCE_SET = registerWoodSet(new WoodBlockSetBuilder("spruce",
            WOOD_STRENGTH, WOOD_BLAST_RESISTANCE, MapColor.SPRUCE_BROWN, NoteBlockInstrument.BASS, BlockSoundGroup.WOOD, Blocks.SPRUCE_SAPLING)
            .addToSet(WoodBlockTypes.LOG_BLOCKS)
            .addToSet(WoodBlockTypes.STRIPPED_LOG_BLOCKS)
            .addToSet(WoodBlockTypes.PLANK_BLOCKS)
            .addToSet(WoodBlockTypes.REDSTONE_BLOCKS)
            .addToSet(WoodBlockTypes.FURNITURE_BLOCKS)
            .addToSet(WoodBlockTypes.SHINGLE_BLOCKS)
            .addToSet(WoodBlockTypes.ROOFING_BLOCKS)
            .addToSet(WoodBlockTypes.LEAVES));

    public static WoodBlockSetBuilder BIRCH_SET = registerWoodSet(new WoodBlockSetBuilder("birch",
            WOOD_STRENGTH, WOOD_BLAST_RESISTANCE, MapColor.PALE_YELLOW, NoteBlockInstrument.BASS, BlockSoundGroup.WOOD, Blocks.BIRCH_SAPLING)
            .addToSet(WoodBlockTypes.LOG_BLOCKS)
            .addToSet(WoodBlockTypes.STRIPPED_LOG_BLOCKS)
            .addToSet(WoodBlockTypes.PLANK_BLOCKS)
            .addToSet(WoodBlockTypes.REDSTONE_BLOCKS)
            .addToSet(WoodBlockTypes.FURNITURE_BLOCKS)
            .addToSet(WoodBlockTypes.SHINGLE_BLOCKS)
            .addToSet(WoodBlockTypes.ROOFING_BLOCKS)
            .addToSet(WoodBlockTypes.LEAVES));

    public static WoodBlockSetBuilder JUNGLE_SET = registerWoodSet(new WoodBlockSetBuilder("jungle",
            WOOD_STRENGTH, WOOD_BLAST_RESISTANCE, MapColor.DIRT_BROWN, NoteBlockInstrument.BASS, BlockSoundGroup.WOOD, Blocks.JUNGLE_SAPLING)
            .addToSet(WoodBlockTypes.LOG_BLOCKS)
            .addToSet(WoodBlockTypes.STRIPPED_LOG_BLOCKS)
            .addToSet(WoodBlockTypes.PLANK_BLOCKS)
            .addToSet(WoodBlockTypes.REDSTONE_BLOCKS)
            .addToSet(WoodBlockTypes.FURNITURE_BLOCKS)
            .addToSet(WoodBlockTypes.SHINGLE_BLOCKS)
            .addToSet(WoodBlockTypes.ROOFING_BLOCKS)
            .addToSet(WoodBlockTypes.LEAVES));

    public static WoodBlockSetBuilder ACACIA_SET = registerWoodSet(new WoodBlockSetBuilder("acacia",
            WOOD_STRENGTH, WOOD_BLAST_RESISTANCE, MapColor.ORANGE, NoteBlockInstrument.BASS, BlockSoundGroup.WOOD, Blocks.ACACIA_SAPLING)
            .addToSet(WoodBlockTypes.LOG_BLOCKS)
            .addToSet(WoodBlockTypes.STRIPPED_LOG_BLOCKS)
            .addToSet(WoodBlockTypes.PLANK_BLOCKS)
            .addToSet(WoodBlockTypes.REDSTONE_BLOCKS)
            .addToSet(WoodBlockTypes.FURNITURE_BLOCKS)
            .addToSet(WoodBlockTypes.SHINGLE_BLOCKS)
            .addToSet(WoodBlockTypes.ROOFING_BLOCKS)
            .addToSet(WoodBlockTypes.LEAVES));

    public static WoodBlockSetBuilder DAK_OAK_SET = registerWoodSet(new WoodBlockSetBuilder("dark_oak",
            WOOD_STRENGTH, WOOD_BLAST_RESISTANCE, MapColor.BROWN, NoteBlockInstrument.BASS, BlockSoundGroup.WOOD, Blocks.DARK_OAK_SAPLING)
            .addToSet(WoodBlockTypes.LOG_BLOCKS)
            .addToSet(WoodBlockTypes.STRIPPED_LOG_BLOCKS)
            .addToSet(WoodBlockTypes.PLANK_BLOCKS)
            .addToSet(WoodBlockTypes.REDSTONE_BLOCKS)
            .addToSet(WoodBlockTypes.FURNITURE_BLOCKS)
            .addToSet(WoodBlockTypes.SHINGLE_BLOCKS)
            .addToSet(WoodBlockTypes.ROOFING_BLOCKS)
            .addToSet(WoodBlockTypes.LEAVES));

    public static WoodBlockSetBuilder MANGROVE_SET = registerWoodSet(new WoodBlockSetBuilder("mangrove",
            WOOD_STRENGTH, WOOD_BLAST_RESISTANCE, MapColor.RED, NoteBlockInstrument.BASS, BlockSoundGroup.WOOD, Blocks.MANGROVE_PROPAGULE)
            .addToSet(WoodBlockTypes.LOG_BLOCKS)
            .addToSet(WoodBlockTypes.STRIPPED_LOG_BLOCKS)
            .addToSet(WoodBlockTypes.PLANK_BLOCKS)
            .addToSet(WoodBlockTypes.REDSTONE_BLOCKS)
            .addToSet(WoodBlockTypes.FURNITURE_BLOCKS)
            .addToSet(WoodBlockTypes.SHINGLE_BLOCKS)
            .addToSet(WoodBlockTypes.ROOFING_BLOCKS)
            .addToSet(WoodBlockTypes.LEAVES));

    public static WoodBlockSetBuilder CHERRY_SET = registerWoodSet(new WoodBlockSetBuilder("cherry",
            WOOD_STRENGTH, WOOD_BLAST_RESISTANCE, MapColor.TERRACOTTA_WHITE, NoteBlockInstrument.BASS, BlockSoundGroup.WOOD, Blocks.CHERRY_SAPLING)
            .addToSet(WoodBlockTypes.LOG_BLOCKS)
            .addToSet(WoodBlockTypes.STRIPPED_LOG_BLOCKS)
            .addToSet(WoodBlockTypes.PLANK_BLOCKS)
            .addToSet(WoodBlockTypes.REDSTONE_BLOCKS)
            .addToSet(WoodBlockTypes.FURNITURE_BLOCKS)
            .addToSet(WoodBlockTypes.SHINGLE_BLOCKS)
            .addToSet(WoodBlockTypes.ROOFING_BLOCKS)
            .addToSet(WoodBlockTypes.LEAVES));

    public static WoodBlockSetBuilder PALE_OAK_SET = registerWoodSet(new WoodBlockSetBuilder("pale_oak",
            WOOD_STRENGTH, WOOD_BLAST_RESISTANCE, MapColor.OFF_WHITE, NoteBlockInstrument.BASS, BlockSoundGroup.WOOD, Blocks.PALE_OAK_SAPLING)
            .addToSet(WoodBlockTypes.LOG_BLOCKS)
            .addToSet(WoodBlockTypes.STRIPPED_LOG_BLOCKS)
            .addToSet(WoodBlockTypes.PLANK_BLOCKS)
            .addToSet(WoodBlockTypes.REDSTONE_BLOCKS)
            .addToSet(WoodBlockTypes.LEAVES));

    public static WoodBlockSetBuilder CRIMSON_SET = registerWoodSet(new WoodBlockSetBuilder("crimson",
            WOOD_STRENGTH, WOOD_BLAST_RESISTANCE, MapColor.DULL_PINK, NoteBlockInstrument.BASS, BlockSoundGroup.WOOD, Blocks.CRIMSON_FUNGUS)
            .addToSet(WoodBlockTypes.STEM_BLOCKS)
            .addToSet(WoodBlockTypes.STRIPPED_STEM_BLOCKS)
            .addToSet(WoodBlockTypes.PLANK_BLOCKS)
            .addToSet(WoodBlockTypes.REDSTONE_BLOCKS)
            .addToSet(WoodBlockTypes.FURNITURE_BLOCKS)
            .addToSet(WoodBlockTypes.SHINGLE_BLOCKS)
            .addToSet(WoodBlockTypes.ROOFING_BLOCKS));

    public static WoodBlockSetBuilder WARPED_SET = registerWoodSet(new WoodBlockSetBuilder("warped",
            WOOD_STRENGTH, WOOD_BLAST_RESISTANCE, MapColor.DARK_AQUA, NoteBlockInstrument.BASS, BlockSoundGroup.WOOD, Blocks.WARPED_FUNGUS)
            .addToSet(WoodBlockTypes.STEM_BLOCKS)
            .addToSet(WoodBlockTypes.STRIPPED_STEM_BLOCKS)
            .addToSet(WoodBlockTypes.PLANK_BLOCKS)
            .addToSet(WoodBlockTypes.REDSTONE_BLOCKS)
            .addToSet(WoodBlockTypes.FURNITURE_BLOCKS)
            .addToSet(WoodBlockTypes.SHINGLE_BLOCKS)
            .addToSet(WoodBlockTypes.ROOFING_BLOCKS));

    public static WoodBlockSetBuilder BEECH_SET = registerWoodSet(new WoodBlockSetBuilder("beech",
            WOOD_STRENGTH, WOOD_BLAST_RESISTANCE, MapColor.LIGHT_GRAY, NoteBlockInstrument.BASS, BlockSoundGroup.WOOD, ModNatureBlocks.BEECH_SAPLING)
            .addToSet(WoodBlockTypes.LOG_BLOCKS)
            .addToSet(WoodBlockTypes.STRIPPED_LOG_BLOCKS)
            .addToSet(WoodBlockTypes.PLANK_BLOCKS)
            .addToSet(WoodBlockTypes.REDSTONE_BLOCKS)
            .addToSet(WoodBlockTypes.FURNITURE_BLOCKS)
            .addToSet(WoodBlockTypes.ROOFING_BLOCKS)
            .addToSet(WoodBlockTypes.SHINGLE_BLOCKS)
            .addToSet(WoodBlockTypes.LEAVES));

    public static WoodBlockSetBuilder LARCH_SET = registerWoodSet(new WoodBlockSetBuilder("larch",
            WOOD_STRENGTH, WOOD_BLAST_RESISTANCE, MapColor.DIRT_BROWN, NoteBlockInstrument.BASS, BlockSoundGroup.WOOD, ModNatureBlocks.LARCH_SAPLING)
            .addToSet(WoodBlockTypes.LOG_BLOCKS)
            .addToSet(WoodBlockTypes.STRIPPED_LOG_BLOCKS)
            .addToSet(WoodBlockTypes.PLANK_BLOCKS)
            .addToSet(WoodBlockTypes.REDSTONE_BLOCKS)
            .addToSet(WoodBlockTypes.FURNITURE_BLOCKS)
            .addToSet(WoodBlockTypes.ROOFING_BLOCKS)
            .addToSet(WoodBlockTypes.SHINGLE_BLOCKS)
            .addToSet(WoodBlockTypes.LEAVES));

    public static WoodBlockSetBuilder BLACK_LEBETHRON_SET = registerWoodSet(new WoodBlockSetBuilder("black_lebethron",
            WOOD_STRENGTH, WOOD_BLAST_RESISTANCE, MapColor.BLACK, NoteBlockInstrument.BASS, BlockSoundGroup.WOOD, ModNatureBlocks.LEBETHRON_SAPLING)
            .addToSet(WoodBlockTypes.LOG_BLOCKS)
            .addToSet(WoodBlockTypes.STRIPPED_LOG_BLOCKS)
            .addToSet(WoodBlockTypes.PLANK_BLOCKS)
            .addToSet(WoodBlockTypes.REDSTONE_BLOCKS)
            .addToSet(WoodBlockTypes.FURNITURE_BLOCKS)
            .addToSet(WoodBlockTypes.ROOFING_BLOCKS)
            .addToSet(WoodBlockTypes.SHINGLE_BLOCKS));

    public static WoodBlockSetBuilder WHITE_LEBETHRON_SET = registerWoodSet(new WoodBlockSetBuilder("white_lebethron",
            WOOD_STRENGTH, WOOD_BLAST_RESISTANCE, MapColor.WHITE, NoteBlockInstrument.BASS, BlockSoundGroup.WOOD, ModNatureBlocks.WHITE_LEBETHRON_SAPLING)
            .addToSet(WoodBlockTypes.LOG_BLOCKS)
            .addToSet(WoodBlockTypes.STRIPPED_LOG_BLOCKS)
            .addToSet(WoodBlockTypes.PLANK_BLOCKS)
            .addToSet(WoodBlockTypes.REDSTONE_BLOCKS)
            .addToSet(WoodBlockTypes.FURNITURE_BLOCKS)
            .addToSet(WoodBlockTypes.ROOFING_BLOCKS)
            .addToSet(WoodBlockTypes.SHINGLE_BLOCKS));

    public static WoodBlockSetBuilder CHESTNUT_SET = registerWoodSet(new WoodBlockSetBuilder("chestnut",
            WOOD_STRENGTH, WOOD_BLAST_RESISTANCE, MapColor.TERRACOTTA_YELLOW, NoteBlockInstrument.BASS, BlockSoundGroup.WOOD, ModNatureBlocks.CHESTNUT_SAPLING)
            .addToSet(WoodBlockTypes.LOG_BLOCKS)
            .addToSet(WoodBlockTypes.STRIPPED_LOG_BLOCKS)
            .addToSet(WoodBlockTypes.PLANK_BLOCKS)
            .addToSet(WoodBlockTypes.REDSTONE_BLOCKS)
            .addToSet(WoodBlockTypes.FURNITURE_BLOCKS)
            .addToSet(WoodBlockTypes.ROOFING_BLOCKS)
            .addToSet(WoodBlockTypes.SHINGLE_BLOCKS)
            .addToSet(WoodBlockTypes.LEAVES));

    public static WoodBlockSetBuilder FIR_SET = registerWoodSet(new WoodBlockSetBuilder("fir",
            WOOD_STRENGTH, WOOD_BLAST_RESISTANCE, MapColor.SPRUCE_BROWN, NoteBlockInstrument.BASS, BlockSoundGroup.WOOD, ModNatureBlocks.FIR_SAPLING)
            .addToSet(WoodBlockTypes.LOG_BLOCKS)
            .addToSet(WoodBlockTypes.STRIPPED_LOG_BLOCKS)
            .addToSet(WoodBlockTypes.PLANK_BLOCKS)
            .addToSet(WoodBlockTypes.REDSTONE_BLOCKS)
            .addToSet(WoodBlockTypes.FURNITURE_BLOCKS)
            .addToSet(WoodBlockTypes.ROOFING_BLOCKS)
            .addToSet(WoodBlockTypes.SHINGLE_BLOCKS)
            .addToSet(WoodBlockTypes.LEAVES));

    public static WoodBlockSetBuilder HOLLY_SET = registerWoodSet(new WoodBlockSetBuilder("holly",
            WOOD_STRENGTH, WOOD_BLAST_RESISTANCE, MapColor.WHITE_GRAY, NoteBlockInstrument.BASS, BlockSoundGroup.WOOD, ModNatureBlocks.HOLLY_SAPLING)
            .addToSet(WoodBlockTypes.LOG_BLOCKS)
            .addToSet(WoodBlockTypes.STRIPPED_LOG_BLOCKS)
            .addToSet(WoodBlockTypes.PLANK_BLOCKS)
            .addToSet(WoodBlockTypes.REDSTONE_BLOCKS)
            .addToSet(WoodBlockTypes.FURNITURE_BLOCKS)
            .addToSet(WoodBlockTypes.ROOFING_BLOCKS)
            .addToSet(WoodBlockTypes.SHINGLE_BLOCKS)
            .addToSet(WoodBlockTypes.LEAVES));

    public static WoodBlockSetBuilder MALLORN_SET = registerWoodSet(new WoodBlockSetBuilder("mallorn",
            WOOD_STRENGTH, WOOD_BLAST_RESISTANCE, MapColor.WHITE, NoteBlockInstrument.BASS, BlockSoundGroup.WOOD, ModNatureBlocks.MALLORN_SAPLING)
            .addToSet(WoodBlockTypes.LOG_BLOCKS)
            .addToSet(WoodBlockTypes.STRIPPED_LOG_BLOCKS)
            .addToSet(WoodBlockTypes.PLANK_BLOCKS)
            .addToSet(WoodBlockTypes.REDSTONE_BLOCKS)
            .addToSet(WoodBlockTypes.FURNITURE_BLOCKS)
            .addToSet(WoodBlockTypes.ROOFING_BLOCKS)
            .addToSet(WoodBlockTypes.SHINGLE_BLOCKS)
            .addToSet(WoodBlockTypes.LEAVES));

    public static WoodBlockSetBuilder MAPLE_SET = registerWoodSet(new WoodBlockSetBuilder("maple",
            WOOD_STRENGTH, WOOD_BLAST_RESISTANCE, MapColor.DULL_RED, NoteBlockInstrument.BASS, BlockSoundGroup.WOOD, ModNatureBlocks.MAPLE_SAPLING)
            .addToSet(WoodBlockTypes.LOG_BLOCKS)
            .addToSet(WoodBlockTypes.STRIPPED_LOG_BLOCKS)
            .addToSet(WoodBlockTypes.PLANK_BLOCKS)
            .addToSet(WoodBlockTypes.REDSTONE_BLOCKS)
            .addToSet(WoodBlockTypes.FURNITURE_BLOCKS)
            .addToSet(WoodBlockTypes.ROOFING_BLOCKS)
            .addToSet(WoodBlockTypes.SHINGLE_BLOCKS)
            .addToSet(WoodBlockTypes.LEAVES));

    public static WoodBlockSetBuilder SILVER_MAPLE_SET = registerWoodSet(new WoodBlockSetBuilder("silver_maple",
            WOOD_STRENGTH, WOOD_BLAST_RESISTANCE, MapColor.OFF_WHITE, NoteBlockInstrument.BASS, BlockSoundGroup.WOOD, ModNatureBlocks.SILVER_MAPLE_SAPLING)
            .addToSet(WoodBlockTypes.LOG_BLOCKS)
            .addToSet(WoodBlockTypes.STRIPPED_LOG_BLOCKS)
            .addToSet(WoodBlockTypes.PLANK_BLOCKS)
            .addToSet(WoodBlockTypes.REDSTONE_BLOCKS)
            .addToSet(WoodBlockTypes.FURNITURE_BLOCKS)
            .addToSet(WoodBlockTypes.ROOFING_BLOCKS)
            .addToSet(WoodBlockTypes.SHINGLE_BLOCKS));

    public static WoodBlockSetBuilder MIRKWOOD_SET = registerWoodSet(new WoodBlockSetBuilder("mirkwood",
            WOOD_STRENGTH, WOOD_BLAST_RESISTANCE, MapColor.PALE_GREEN, NoteBlockInstrument.BASS, BlockSoundGroup.WOOD, ModNatureBlocks.MIRKWOOD_SAPLING)
            .addToSet(WoodBlockTypes.LOG_BLOCKS)
            .addToSet(WoodBlockTypes.STRIPPED_LOG_BLOCKS)
            .addToSet(WoodBlockTypes.PLANK_BLOCKS)
            .addToSet(WoodBlockTypes.REDSTONE_BLOCKS)
            .addToSet(WoodBlockTypes.FURNITURE_BLOCKS)
            .addToSet(WoodBlockTypes.ROOFING_BLOCKS)
            .addToSet(WoodBlockTypes.SHINGLE_BLOCKS)
            .addToSet(WoodBlockTypes.LEAVES));

    public static WoodBlockSetBuilder PALM_SET = registerWoodSet(new WoodBlockSetBuilder("palm",
            WOOD_STRENGTH, WOOD_BLAST_RESISTANCE, MapColor.DIRT_BROWN, NoteBlockInstrument.BASS, BlockSoundGroup.WOOD, ModNatureBlocks.PALM_SAPLING)
            .addToSet(WoodBlockTypes.LOG_BLOCKS)
            .addToSet(WoodBlockTypes.STRIPPED_LOG_BLOCKS)
            .addToSet(WoodBlockTypes.PLANK_BLOCKS)
            .addToSet(WoodBlockTypes.REDSTONE_BLOCKS)
            .addToSet(WoodBlockTypes.FURNITURE_BLOCKS)
            .addToSet(WoodBlockTypes.ROOFING_BLOCKS)
            .addToSet(WoodBlockTypes.SHINGLE_BLOCKS)
            .addToSet(WoodBlockTypes.LEAVES));

    public static WoodBlockSetBuilder WHITE_PALM_SET = registerWoodSet(new WoodBlockSetBuilder("white_palm",
            WOOD_STRENGTH, WOOD_BLAST_RESISTANCE, MapColor.OFF_WHITE, NoteBlockInstrument.BASS, BlockSoundGroup.WOOD, ModNatureBlocks.WHITE_PALM_SAPLING)
            .addToSet(WoodBlockTypes.LOG_BLOCKS)
            .addToSet(WoodBlockTypes.STRIPPED_LOG_BLOCKS)
            .addToSet(WoodBlockTypes.PLANK_BLOCKS)
            .addToSet(WoodBlockTypes.REDSTONE_BLOCKS)
            .addToSet(WoodBlockTypes.FURNITURE_BLOCKS)
            .addToSet(WoodBlockTypes.ROOFING_BLOCKS)
            .addToSet(WoodBlockTypes.SHINGLE_BLOCKS));

    public static WoodBlockSetBuilder PINE_SET = registerWoodSet(new WoodBlockSetBuilder("pine",
            WOOD_STRENGTH, WOOD_BLAST_RESISTANCE, MapColor.BROWN, NoteBlockInstrument.BASS, BlockSoundGroup.WOOD, ModNatureBlocks.PINE_SAPLING)
            .addToSet(WoodBlockTypes.LOG_BLOCKS)
            .addToSet(WoodBlockTypes.STRIPPED_LOG_BLOCKS)
            .addToSet(WoodBlockTypes.PLANK_BLOCKS)
            .addToSet(WoodBlockTypes.REDSTONE_BLOCKS)
            .addToSet(WoodBlockTypes.FURNITURE_BLOCKS)
            .addToSet(WoodBlockTypes.ROOFING_BLOCKS)
            .addToSet(WoodBlockTypes.SHINGLE_BLOCKS)
            .addToSet(WoodBlockTypes.LEAVES));

    public static WoodBlockSetBuilder BLACK_PINE_SET = registerWoodSet(new WoodBlockSetBuilder("black_pine",
            WOOD_STRENGTH, WOOD_BLAST_RESISTANCE, MapColor.TERRACOTTA_ORANGE, NoteBlockInstrument.BASS, BlockSoundGroup.WOOD, ModNatureBlocks.BLACK_PINE_SAPLING)
            .addToSet(WoodBlockTypes.LOG_BLOCKS)
            .addToSet(WoodBlockTypes.STRIPPED_LOG_BLOCKS)
            .addToSet(WoodBlockTypes.PLANK_BLOCKS)
            .addToSet(WoodBlockTypes.REDSTONE_BLOCKS)
            .addToSet(WoodBlockTypes.FURNITURE_BLOCKS)
            .addToSet(WoodBlockTypes.ROOFING_BLOCKS)
            .addToSet(WoodBlockTypes.SHINGLE_BLOCKS)
            .addToSet(WoodBlockTypes.LEAVES));

    public static WoodBlockSetBuilder WILLOW_SET = registerWoodSet(new WoodBlockSetBuilder("willow",
            WOOD_STRENGTH, WOOD_BLAST_RESISTANCE, MapColor.PALE_GREEN, NoteBlockInstrument.BASS, BlockSoundGroup.WOOD, ModNatureBlocks.WILLOW_SAPLING)
            .addToSet(WoodBlockTypes.LOG_BLOCKS)
            .addToSet(WoodBlockTypes.STRIPPED_LOG_BLOCKS)
            .addToSet(WoodBlockTypes.PLANK_BLOCKS)
            .addToSet(WoodBlockTypes.REDSTONE_BLOCKS)
            .addToSet(WoodBlockTypes.FURNITURE_BLOCKS)
            .addToSet(WoodBlockTypes.ROOFING_BLOCKS)
            .addToSet(WoodBlockTypes.SHINGLE_BLOCKS)
            .addToSet(WoodBlockTypes.LEAVES));

    public static WoodBlockSetBuilder ROTTEN_SET = registerWoodSet(new WoodBlockSetBuilder("rotten",
            WOOD_STRENGTH, WOOD_BLAST_RESISTANCE, MapColor.PALE_GREEN, NoteBlockInstrument.BASS, BlockSoundGroup.WOOD, null)
            .addToSet(WoodBlockTypes.LOG_BLOCKS)
            .addToSet(WoodBlockTypes.STRIPPED_LOG_BLOCKS)
            .addToSet(WoodBlockTypes.PLANK_BLOCKS)
            .addToSet(WoodBlockTypes.REDSTONE_BLOCKS)
            .addToSet(WoodBlockTypes.FURNITURE_BLOCKS)
            .addToSet(WoodBlockTypes.ROOFING_BLOCKS)
            .addToSet(WoodBlockTypes.SHINGLE_BLOCKS));

    public static WoodBlockSetBuilder SCORCHED_SET = registerWoodSet(new WoodBlockSetBuilder("scorched",
            WOOD_STRENGTH, WOOD_BLAST_RESISTANCE, MapColor.BLACK, NoteBlockInstrument.BASS, BlockSoundGroup.WOOD, null)
            .addToSet(WoodBlockTypes.LOG_BLOCKS)
            .addToSet(WoodBlockTypes.STRIPPED_LOG_BLOCKS)
            .addToSet(WoodBlockTypes.PLANK_BLOCKS)
            .addToSet(WoodBlockTypes.REDSTONE_BLOCKS)
            .addToSet(WoodBlockTypes.FURNITURE_BLOCKS)
            .addToSet(WoodBlockTypes.ROOFING_BLOCKS)
            .addToSet(WoodBlockTypes.SHINGLE_BLOCKS));

    private static WoodBlockSetBuilder registerWoodSet(WoodBlockSetBuilder set) {

        List<ItemStack> itemGroup = ModItemGroups.WOOD_BLOCKS_CONTENTS;

        set.existingList.forEach((woodStoneTypes) -> {
            switch (woodStoneTypes) {
                case LOG_BLOCKS -> {
                    set.logBlocks = BlockSetRegistration.createWoodSet(woodStoneTypes.getPrefix() + set.setName + woodStoneTypes.getSuffix(), set.hardness, set.blastResistance, set.mapColor, set.instrument, set.soundGroup, itemGroup);
                    ModItemGroups.NATURE_BLOCKS_CONTENTS.add(set.logBlocks.log().asItem().getDefaultStack());
                }
                case STEM_BLOCKS -> {
                    set.logBlocks = BlockSetRegistration.createStemSet(woodStoneTypes.getPrefix() + set.setName + woodStoneTypes.getSuffix(), set.hardness, set.blastResistance, set.mapColor, set.instrument, set.soundGroup, itemGroup);
                    ModItemGroups.NATURE_BLOCKS_CONTENTS.add(set.logBlocks.log().asItem().getDefaultStack());
                }
                case STRIPPED_LOG_BLOCKS -> {
                    set.strippedLogBlocks = BlockSetRegistration.createWoodSet(woodStoneTypes.getPrefix() + set.setName + woodStoneTypes.getSuffix(), set.hardness, set.blastResistance, set.mapColor, set.instrument, set.soundGroup, itemGroup);
                    StrippableBlockRegistry.register(set.logBlocks.log(), set.strippedLogBlocks.log());
                    StrippableBlockRegistry.register(set.logBlocks.wood(), set.strippedLogBlocks.wood());
                }
                case STRIPPED_STEM_BLOCKS -> {
                    set.strippedLogBlocks = BlockSetRegistration.createStemSet(woodStoneTypes.getPrefix() + set.setName + woodStoneTypes.getSuffix(), set.hardness, set.blastResistance, set.mapColor, set.instrument, set.soundGroup, itemGroup);
                    StrippableBlockRegistry.register(set.logBlocks.log(), set.strippedLogBlocks.log());
                    StrippableBlockRegistry.register(set.logBlocks.wood(), set.strippedLogBlocks.wood());
                }
                case PLANK_BLOCKS ->
                        set.planksBlocks = BlockSetRegistration.createPlanksSet(woodStoneTypes.getPrefix() + set.setName + woodStoneTypes.getSuffix(), set.hardness, set.blastResistance, set.mapColor, set.instrument, set.soundGroup, itemGroup);
                case REDSTONE_BLOCKS ->
                        set.redstoneBlocks = BlockSetRegistration.createWoodRedstoneSet(woodStoneTypes.getPrefix() + set.setName + woodStoneTypes.getSuffix(), set.hardness, set.blastResistance, set.mapColor, set.soundGroup, set.planksBlocks.base(), itemGroup);
                case FURNITURE_BLOCKS -> {
                    set.furnitureBlocks = BlockSetRegistration.createWoodFurnitureSet(woodStoneTypes.getPrefix() + set.setName + woodStoneTypes.getSuffix(), set.hardness, set.blastResistance, set.mapColor, set.soundGroup, set.planksBlocks.base(), itemGroup);
                    ModItemGroups.DECORATIVES_BLOCKS_CONTENT.add(set.furnitureBlocks.stool().asItem().getDefaultStack());
                    ModItemGroups.DECORATIVES_BLOCKS_CONTENT.add(set.furnitureBlocks.bench().asItem().getDefaultStack());
                    ModItemGroups.DECORATIVES_BLOCKS_CONTENT.add(set.furnitureBlocks.table().asItem().getDefaultStack());
                    ModItemGroups.DECORATIVES_BLOCKS_CONTENT.add(set.furnitureBlocks.chair().asItem().getDefaultStack());
                    ModItemGroups.DECORATIVES_BLOCKS_CONTENT.add(set.furnitureBlocks.ladder().asItem().getDefaultStack());
                }
                case ROOFING_BLOCKS ->
                        set.roofingBlocks = BlockSetRegistration.createRegularSet(woodStoneTypes.getPrefix() + set.setName + woodStoneTypes.getSuffix(), set.hardness, set.blastResistance, set.mapColor, set.instrument, set.soundGroup, false, itemGroup);
                case SHINGLE_BLOCKS ->
                        set.shinglesBlocks = BlockSetRegistration.createRegularSet(woodStoneTypes.getPrefix() + set.setName + woodStoneTypes.getSuffix(), set.hardness, set.blastResistance, set.mapColor, set.instrument, set.soundGroup, false, itemGroup);
                case LEAVES -> {
                    set.leaves = getVanillaOrCreateNew(woodStoneTypes.getPrefix() + set.setName + woodStoneTypes.getSuffix(),
                            (settings) -> new TintedParticleLeavesBlock(0.01F, settings), AbstractBlock.Settings.copy(Blocks.OAK_LEAVES).strength(LEAVES_STRENGTH).sounds(BlockSoundGroup.GRASS).burnable(), itemGroup);
                    FlammableBlockRegistry.getDefaultInstance().add(set.leaves, 5, 60);
                }
            }
        });

        woodSetsList.add(set);

        return set;
    }

    public static void registerModBlockSets() {
        MiddleEarth.LOGGER.logDebugMsg("Registering Wood Block Sets for " + MiddleEarth.MOD_ID);
    }
}
