
package net.sevenstars.middleearth.block.registration;

import net.sevenstars.api.registries.RegistrationBridge;

import net.minecraft.world.item.ItemStack;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.block.special.palemoss.PaleOakLeavesBlock;
import net.sevenstars.middleearth.block.special.plants.BerryHollyLeavesBlock;
import net.sevenstars.middleearth.block.special.plants.ModLeavesBlock;
import net.sevenstars.middleearth.block.special.verticalSlabs.VerticalSlabBlock;
import net.sevenstars.middleearth.block.utils.BlockDataMapCollector;
import net.sevenstars.middleearth.block.utils.BlockRecordTypes;
import net.sevenstars.middleearth.block.utils.BlockSetRegistration;
import net.sevenstars.middleearth.block.utils.WoodBlockTypes;
import net.sevenstars.middleearth.block.utils.setBuilders.WoodBlockSetBuilder;
import net.sevenstars.middleearth.datageneration.content.tags.ModdedStrippedLogs;
import net.sevenstars.middleearth.item.utils.ItemGroupsME;

import java.util.ArrayList;
import java.util.List;

import static net.sevenstars.middleearth.block.utils.BlockSetRegistration.getVanillaOrCreateNew;

public class WoodBlockSets {
    public static final float WOOD_STRENGTH = 2.0f;
    public static final float WOOD_BLAST_RESISTANCE = 3.0f;
    public static final float LEAVES_STRENGTH = 0.1f;

    public static List<WoodBlockSetBuilder> woodSetsList = new ArrayList<>();

    public static WoodBlockSetBuilder OAK_SET = registerWoodSet(new WoodBlockSetBuilder("oak",
            WOOD_STRENGTH, WOOD_BLAST_RESISTANCE, MapColor.WOOD, NoteBlockInstrument.BASS, SoundType.WOOD, Blocks.OAK_SAPLING)
            .vanilla(true)
            .addToSet(WoodBlockTypes.LOG_BLOCKS)
            .addToSet(WoodBlockTypes.STRIPPED_LOG_BLOCKS)
            .addToSet(WoodBlockTypes.PLANK_BLOCKS)
            .addToSet(WoodBlockTypes.REDSTONE_BLOCKS)
            .addToSet(WoodBlockTypes.FURNITURE_BLOCKS)
            .addToSet(WoodBlockTypes.SHINGLE_BLOCKS)
            .addToSet(WoodBlockTypes.ROOFING_BLOCKS)
            .addToSet(WoodBlockTypes.LEAVES));

    public static WoodBlockSetBuilder SPRUCE_SET = registerWoodSet(new WoodBlockSetBuilder("spruce",
            WOOD_STRENGTH, WOOD_BLAST_RESISTANCE, MapColor.PODZOL, NoteBlockInstrument.BASS, SoundType.WOOD, Blocks.SPRUCE_SAPLING)
            .vanilla(true)
            .addToSet(WoodBlockTypes.LOG_BLOCKS)
            .addToSet(WoodBlockTypes.STRIPPED_LOG_BLOCKS)
            .addToSet(WoodBlockTypes.PLANK_BLOCKS)
            .addToSet(WoodBlockTypes.REDSTONE_BLOCKS)
            .addToSet(WoodBlockTypes.FURNITURE_BLOCKS)
            .addToSet(WoodBlockTypes.SHINGLE_BLOCKS)
            .addToSet(WoodBlockTypes.ROOFING_BLOCKS)
            .addToSet(WoodBlockTypes.LEAVES));

    public static WoodBlockSetBuilder BIRCH_SET = registerWoodSet(new WoodBlockSetBuilder("birch",
            WOOD_STRENGTH, WOOD_BLAST_RESISTANCE, MapColor.SAND, NoteBlockInstrument.BASS, SoundType.WOOD, Blocks.BIRCH_SAPLING)
            .vanilla(true)
            .addToSet(WoodBlockTypes.LOG_BLOCKS)
            .addToSet(WoodBlockTypes.STRIPPED_LOG_BLOCKS)
            .addToSet(WoodBlockTypes.PLANK_BLOCKS)
            .addToSet(WoodBlockTypes.REDSTONE_BLOCKS)
            .addToSet(WoodBlockTypes.FURNITURE_BLOCKS)
            .addToSet(WoodBlockTypes.SHINGLE_BLOCKS)
            .addToSet(WoodBlockTypes.ROOFING_BLOCKS)
            .addToSet(WoodBlockTypes.LEAVES));

    public static WoodBlockSetBuilder JUNGLE_SET = registerWoodSet(new WoodBlockSetBuilder("jungle",
            WOOD_STRENGTH, WOOD_BLAST_RESISTANCE, MapColor.DIRT, NoteBlockInstrument.BASS, SoundType.WOOD, Blocks.JUNGLE_SAPLING)
            .vanilla(true)
            .addToSet(WoodBlockTypes.LOG_BLOCKS)
            .addToSet(WoodBlockTypes.STRIPPED_LOG_BLOCKS)
            .addToSet(WoodBlockTypes.PLANK_BLOCKS)
            .addToSet(WoodBlockTypes.REDSTONE_BLOCKS)
            .addToSet(WoodBlockTypes.FURNITURE_BLOCKS)
            .addToSet(WoodBlockTypes.SHINGLE_BLOCKS)
            .addToSet(WoodBlockTypes.ROOFING_BLOCKS)
            .addToSet(WoodBlockTypes.LEAVES));

    public static WoodBlockSetBuilder ACACIA_SET = registerWoodSet(new WoodBlockSetBuilder("acacia",
            WOOD_STRENGTH, WOOD_BLAST_RESISTANCE, MapColor.COLOR_ORANGE, NoteBlockInstrument.BASS, SoundType.WOOD, Blocks.ACACIA_SAPLING)
            .vanilla(true)
            .addToSet(WoodBlockTypes.LOG_BLOCKS)
            .addToSet(WoodBlockTypes.STRIPPED_LOG_BLOCKS)
            .addToSet(WoodBlockTypes.PLANK_BLOCKS)
            .addToSet(WoodBlockTypes.REDSTONE_BLOCKS)
            .addToSet(WoodBlockTypes.FURNITURE_BLOCKS)
            .addToSet(WoodBlockTypes.SHINGLE_BLOCKS)
            .addToSet(WoodBlockTypes.ROOFING_BLOCKS)
            .addToSet(WoodBlockTypes.LEAVES));

    public static WoodBlockSetBuilder DAK_OAK_SET = registerWoodSet(new WoodBlockSetBuilder("dark_oak",
            WOOD_STRENGTH, WOOD_BLAST_RESISTANCE, MapColor.COLOR_BROWN, NoteBlockInstrument.BASS, SoundType.WOOD, Blocks.DARK_OAK_SAPLING)
            .vanilla(true)
            .addToSet(WoodBlockTypes.LOG_BLOCKS)
            .addToSet(WoodBlockTypes.STRIPPED_LOG_BLOCKS)
            .addToSet(WoodBlockTypes.PLANK_BLOCKS)
            .addToSet(WoodBlockTypes.REDSTONE_BLOCKS)
            .addToSet(WoodBlockTypes.FURNITURE_BLOCKS)
            .addToSet(WoodBlockTypes.SHINGLE_BLOCKS)
            .addToSet(WoodBlockTypes.ROOFING_BLOCKS)
            .addToSet(WoodBlockTypes.LEAVES));

    public static WoodBlockSetBuilder MANGROVE_SET = registerWoodSet(new WoodBlockSetBuilder("mangrove",
            WOOD_STRENGTH, WOOD_BLAST_RESISTANCE, MapColor.COLOR_RED, NoteBlockInstrument.BASS, SoundType.WOOD, Blocks.MANGROVE_PROPAGULE)
            .vanilla(true)
            .addToSet(WoodBlockTypes.LOG_BLOCKS)
            .addToSet(WoodBlockTypes.STRIPPED_LOG_BLOCKS)
            .addToSet(WoodBlockTypes.PLANK_BLOCKS)
            .addToSet(WoodBlockTypes.REDSTONE_BLOCKS)
            .addToSet(WoodBlockTypes.FURNITURE_BLOCKS)
            .addToSet(WoodBlockTypes.SHINGLE_BLOCKS)
            .addToSet(WoodBlockTypes.ROOFING_BLOCKS)
            .addToSet(WoodBlockTypes.LEAVES));

    public static WoodBlockSetBuilder CHERRY_SET = registerWoodSet(new WoodBlockSetBuilder("cherry",
            WOOD_STRENGTH, WOOD_BLAST_RESISTANCE, MapColor.TERRACOTTA_WHITE, NoteBlockInstrument.BASS, SoundType.WOOD, Blocks.CHERRY_SAPLING)
            .vanilla(true)
            .addToSet(WoodBlockTypes.LOG_BLOCKS)
            .addToSet(WoodBlockTypes.STRIPPED_LOG_BLOCKS)
            .addToSet(WoodBlockTypes.PLANK_BLOCKS)
            .addToSet(WoodBlockTypes.REDSTONE_BLOCKS)
            .addToSet(WoodBlockTypes.FURNITURE_BLOCKS)
            .addToSet(WoodBlockTypes.SHINGLE_BLOCKS)
            .addToSet(WoodBlockTypes.ROOFING_BLOCKS)
            .addToSet(WoodBlockTypes.LEAVES));

    public static WoodBlockSetBuilder PALE_OAK_SET = registerWoodSet(new WoodBlockSetBuilder("pale_oak",
            WOOD_STRENGTH, WOOD_BLAST_RESISTANCE, MapColor.QUARTZ, NoteBlockInstrument.BASS, SoundType.WOOD, ModNatureBlocks.PALE_OAK_SAPLING)
            .vanilla(true)
            .addToSet(WoodBlockTypes.LOG_BLOCKS)
            .addToSet(WoodBlockTypes.STRIPPED_LOG_BLOCKS)
            .addToSet(WoodBlockTypes.PLANK_BLOCKS)
            .addToSet(WoodBlockTypes.REDSTONE_BLOCKS)
            .addToSet(WoodBlockTypes.LEAVES)
            .leavesColor(MapColor.COLOR_LIGHT_GRAY));

    public static WoodBlockSetBuilder BAMBOO = registerWoodSet(new WoodBlockSetBuilder("bamboo",
            WOOD_STRENGTH, WOOD_BLAST_RESISTANCE, MapColor.QUARTZ, NoteBlockInstrument.BASS, SoundType.BAMBOO_WOOD, Blocks.BAMBOO_SAPLING)
            .vanilla(true)
            .addToSet(WoodBlockTypes.PLANK_BLOCKS)
            .addToSet(WoodBlockTypes.FURNITURE_BLOCKS)
            .addToSet(WoodBlockTypes.SHINGLE_BLOCKS)
            .addToSet(WoodBlockTypes.ROOFING_BLOCKS));

    public static WoodBlockSetBuilder CRIMSON_SET = registerWoodSet(new WoodBlockSetBuilder("crimson",
            WOOD_STRENGTH, WOOD_BLAST_RESISTANCE, MapColor.CRIMSON_STEM, NoteBlockInstrument.BASS, SoundType.WOOD, Blocks.CRIMSON_FUNGUS)
            .vanilla(true)
            .addToSet(WoodBlockTypes.NETHER_STEM_BLOCKS)
            .addToSet(WoodBlockTypes.STRIPPED_STEM_BLOCKS)
            .addToSet(WoodBlockTypes.PLANK_BLOCKS)
            .addToSet(WoodBlockTypes.REDSTONE_BLOCKS)
            .addToSet(WoodBlockTypes.FURNITURE_BLOCKS)
            .addToSet(WoodBlockTypes.SHINGLE_BLOCKS)
            .addToSet(WoodBlockTypes.ROOFING_BLOCKS));

    public static WoodBlockSetBuilder WARPED_SET = registerWoodSet(new WoodBlockSetBuilder("warped",
            WOOD_STRENGTH, WOOD_BLAST_RESISTANCE, MapColor.WARPED_STEM, NoteBlockInstrument.BASS, SoundType.WOOD, Blocks.WARPED_FUNGUS)
            .vanilla(true)
            .addToSet(WoodBlockTypes.NETHER_STEM_BLOCKS)
            .addToSet(WoodBlockTypes.STRIPPED_STEM_BLOCKS)
            .addToSet(WoodBlockTypes.PLANK_BLOCKS)
            .addToSet(WoodBlockTypes.REDSTONE_BLOCKS)
            .addToSet(WoodBlockTypes.FURNITURE_BLOCKS)
            .addToSet(WoodBlockTypes.SHINGLE_BLOCKS)
            .addToSet(WoodBlockTypes.ROOFING_BLOCKS));

    public static WoodBlockSetBuilder ASPEN_SET = registerWoodSet(new WoodBlockSetBuilder("aspen",
            WOOD_STRENGTH, WOOD_BLAST_RESISTANCE, MapColor.PODZOL, NoteBlockInstrument.BASS, SoundType.WOOD, ModNatureBlocks.ASPEN_SAPLING)
            .addToSet(WoodBlockTypes.LOG_BLOCKS)
            .addToSet(WoodBlockTypes.STRIPPED_LOG_BLOCKS)
            .addToSet(WoodBlockTypes.PLANK_BLOCKS)
            .addToSet(WoodBlockTypes.REDSTONE_BLOCKS)
            .addToSet(WoodBlockTypes.SHINGLE_BLOCKS)
            .addToSet(WoodBlockTypes.ROOFING_BLOCKS)
            .addToSet(WoodBlockTypes.LEAVES)
            .leavesColor(MapColor.COLOR_YELLOW));

    public static WoodBlockSetBuilder BEECH_SET = registerWoodSet(new WoodBlockSetBuilder("beech",
            WOOD_STRENGTH, WOOD_BLAST_RESISTANCE, MapColor.COLOR_LIGHT_GRAY, NoteBlockInstrument.BASS, SoundType.WOOD, ModNatureBlocks.BEECH_SAPLING)
            .addToSet(WoodBlockTypes.LOG_BLOCKS)
            .addToSet(WoodBlockTypes.STRIPPED_LOG_BLOCKS)
            .addToSet(WoodBlockTypes.PLANK_BLOCKS)
            .addToSet(WoodBlockTypes.REDSTONE_BLOCKS)
            .addToSet(WoodBlockTypes.FURNITURE_BLOCKS)
            .addToSet(WoodBlockTypes.ROOFING_BLOCKS)
            .addToSet(WoodBlockTypes.SHINGLE_BLOCKS)
            .addToSet(WoodBlockTypes.LEAVES)
            .leavesColor(MapColor.PLANT));

    public static WoodBlockSetBuilder DEADWOOD_SET = registerWoodSet(new WoodBlockSetBuilder("deadwood",
            WOOD_STRENGTH, WOOD_BLAST_RESISTANCE, MapColor.COLOR_LIGHT_GRAY, NoteBlockInstrument.BASS, SoundType.WOOD, null)
            .addToSet(WoodBlockTypes.LOG_BLOCKS)
            .addToSet(WoodBlockTypes.STRIPPED_LOG_BLOCKS)
            .addToSet(WoodBlockTypes.PLANK_BLOCKS));

    public static WoodBlockSetBuilder LARCH_SET = registerWoodSet(new WoodBlockSetBuilder("larch",
            WOOD_STRENGTH, WOOD_BLAST_RESISTANCE, MapColor.DIRT, NoteBlockInstrument.BASS, SoundType.WOOD, ModNatureBlocks.LARCH_SAPLING)
            .addToSet(WoodBlockTypes.LOG_BLOCKS)
            .addToSet(WoodBlockTypes.STRIPPED_LOG_BLOCKS)
            .addToSet(WoodBlockTypes.PLANK_BLOCKS)
            .addToSet(WoodBlockTypes.REDSTONE_BLOCKS)
            .addToSet(WoodBlockTypes.FURNITURE_BLOCKS)
            .addToSet(WoodBlockTypes.ROOFING_BLOCKS)
            .addToSet(WoodBlockTypes.SHINGLE_BLOCKS)
            .addToSet(WoodBlockTypes.LEAVES));

    public static WoodBlockSetBuilder BLACK_LEBETHRON_SET = registerWoodSet(new WoodBlockSetBuilder("black_lebethron",
            WOOD_STRENGTH, WOOD_BLAST_RESISTANCE, MapColor.COLOR_BLACK, NoteBlockInstrument.BASS, SoundType.WOOD, ModNatureBlocks.LEBETHRON_SAPLING)
            .addToSet(WoodBlockTypes.LOG_BLOCKS)
            .addToSet(WoodBlockTypes.STRIPPED_LOG_BLOCKS)
            .addToSet(WoodBlockTypes.PLANK_BLOCKS)
            .addToSet(WoodBlockTypes.REDSTONE_BLOCKS)
            .addToSet(WoodBlockTypes.FURNITURE_BLOCKS)
            .addToSet(WoodBlockTypes.ROOFING_BLOCKS)
            .addToSet(WoodBlockTypes.SHINGLE_BLOCKS));

    public static WoodBlockSetBuilder WHITE_LEBETHRON_SET = registerWoodSet(new WoodBlockSetBuilder("white_lebethron",
            WOOD_STRENGTH, WOOD_BLAST_RESISTANCE, MapColor.SNOW, NoteBlockInstrument.BASS, SoundType.WOOD, ModNatureBlocks.WHITE_LEBETHRON_SAPLING)
            .addToSet(WoodBlockTypes.LOG_BLOCKS)
            .addToSet(WoodBlockTypes.STRIPPED_LOG_BLOCKS)
            .addToSet(WoodBlockTypes.PLANK_BLOCKS)
            .addToSet(WoodBlockTypes.REDSTONE_BLOCKS)
            .addToSet(WoodBlockTypes.FURNITURE_BLOCKS)
            .addToSet(WoodBlockTypes.ROOFING_BLOCKS)
            .addToSet(WoodBlockTypes.SHINGLE_BLOCKS));

    public static WoodBlockSetBuilder CHESTNUT_SET = registerWoodSet(new WoodBlockSetBuilder("chestnut",
            WOOD_STRENGTH, WOOD_BLAST_RESISTANCE, MapColor.TERRACOTTA_YELLOW, NoteBlockInstrument.BASS, SoundType.WOOD, ModNatureBlocks.CHESTNUT_SAPLING)
            .addToSet(WoodBlockTypes.LOG_BLOCKS)
            .addToSet(WoodBlockTypes.STRIPPED_LOG_BLOCKS)
            .addToSet(WoodBlockTypes.PLANK_BLOCKS)
            .addToSet(WoodBlockTypes.REDSTONE_BLOCKS)
            .addToSet(WoodBlockTypes.FURNITURE_BLOCKS)
            .addToSet(WoodBlockTypes.ROOFING_BLOCKS)
            .addToSet(WoodBlockTypes.SHINGLE_BLOCKS)
            .addToSet(WoodBlockTypes.LEAVES)
            .leavesColor(MapColor.GRASS));

    public static WoodBlockSetBuilder FIR_SET = registerWoodSet(new WoodBlockSetBuilder("fir",
            WOOD_STRENGTH, WOOD_BLAST_RESISTANCE, MapColor.PODZOL, NoteBlockInstrument.BASS, SoundType.WOOD, ModNatureBlocks.FIR_SAPLING)
            .addToSet(WoodBlockTypes.LOG_BLOCKS)
            .addToSet(WoodBlockTypes.STRIPPED_LOG_BLOCKS)
            .addToSet(WoodBlockTypes.PLANK_BLOCKS)
            .addToSet(WoodBlockTypes.REDSTONE_BLOCKS)
            .addToSet(WoodBlockTypes.FURNITURE_BLOCKS)
            .addToSet(WoodBlockTypes.ROOFING_BLOCKS)
            .addToSet(WoodBlockTypes.SHINGLE_BLOCKS)
            .addToSet(WoodBlockTypes.LEAVES));

    public static WoodBlockSetBuilder HOLLY_SET = registerWoodSet(new WoodBlockSetBuilder("holly",
            WOOD_STRENGTH, WOOD_BLAST_RESISTANCE, MapColor.WOOL, NoteBlockInstrument.BASS, SoundType.WOOD, ModNatureBlocks.HOLLY_SAPLING)
            .addToSet(WoodBlockTypes.LOG_BLOCKS)
            .addToSet(WoodBlockTypes.STRIPPED_LOG_BLOCKS)
            .addToSet(WoodBlockTypes.PLANK_BLOCKS)
            .addToSet(WoodBlockTypes.REDSTONE_BLOCKS)
            .addToSet(WoodBlockTypes.FURNITURE_BLOCKS)
            .addToSet(WoodBlockTypes.ROOFING_BLOCKS)
            .addToSet(WoodBlockTypes.SHINGLE_BLOCKS)
            .addToSet(WoodBlockTypes.LEAVES));

    public static WoodBlockSetBuilder MALLORN_SET = registerWoodSet(new WoodBlockSetBuilder("mallorn",
            WOOD_STRENGTH, WOOD_BLAST_RESISTANCE, MapColor.SNOW, NoteBlockInstrument.BASS, SoundType.WOOD, ModNatureBlocks.MALLORN_SAPLING)
            .addToSet(WoodBlockTypes.LOG_BLOCKS)
            .addToSet(WoodBlockTypes.STRIPPED_LOG_BLOCKS)
            .addToSet(WoodBlockTypes.PLANK_BLOCKS)
            .addToSet(WoodBlockTypes.REDSTONE_BLOCKS)
            .addToSet(WoodBlockTypes.FURNITURE_BLOCKS)
            .addToSet(WoodBlockTypes.ROOFING_BLOCKS)
            .addToSet(WoodBlockTypes.SHINGLE_BLOCKS)
            .addToSet(WoodBlockTypes.LEAVES));

    public static WoodBlockSetBuilder MAPLE_SET = registerWoodSet(new WoodBlockSetBuilder("maple",
            WOOD_STRENGTH, WOOD_BLAST_RESISTANCE, MapColor.COLOR_BROWN, NoteBlockInstrument.BASS, SoundType.WOOD, ModNatureBlocks.MAPLE_SAPLING)
            .addToSet(WoodBlockTypes.LOG_BLOCKS)
            .addToSet(WoodBlockTypes.STRIPPED_LOG_BLOCKS)
            .addToSet(WoodBlockTypes.PLANK_BLOCKS)
            .addToSet(WoodBlockTypes.REDSTONE_BLOCKS)
            .addToSet(WoodBlockTypes.FURNITURE_BLOCKS)
            .addToSet(WoodBlockTypes.ROOFING_BLOCKS)
            .addToSet(WoodBlockTypes.SHINGLE_BLOCKS)
            .addToSet(WoodBlockTypes.LEAVES)
            .leavesColor(MapColor.TERRACOTTA_LIGHT_GREEN));

    public static WoodBlockSetBuilder SILVER_MAPLE_SET = registerWoodSet(new WoodBlockSetBuilder("silver_maple",
            WOOD_STRENGTH, WOOD_BLAST_RESISTANCE, MapColor.QUARTZ, NoteBlockInstrument.BASS, SoundType.WOOD, ModNatureBlocks.SILVER_MAPLE_SAPLING)
            .addToSet(WoodBlockTypes.LOG_BLOCKS)
            .addToSet(WoodBlockTypes.STRIPPED_LOG_BLOCKS)
            .addToSet(WoodBlockTypes.PLANK_BLOCKS)
            .addToSet(WoodBlockTypes.REDSTONE_BLOCKS)
            .addToSet(WoodBlockTypes.FURNITURE_BLOCKS)
            .addToSet(WoodBlockTypes.ROOFING_BLOCKS)
            .addToSet(WoodBlockTypes.SHINGLE_BLOCKS));

    public static WoodBlockSetBuilder MIRKWOOD_SET = registerWoodSet(new WoodBlockSetBuilder("mirkwood",
            WOOD_STRENGTH, WOOD_BLAST_RESISTANCE, MapColor.TERRACOTTA_BROWN, NoteBlockInstrument.BASS, SoundType.WOOD, ModNatureBlocks.MIRKWOOD_SAPLING)
            .addToSet(WoodBlockTypes.LOG_BLOCKS)
            .addToSet(WoodBlockTypes.STRIPPED_LOG_BLOCKS)
            .addToSet(WoodBlockTypes.PLANK_BLOCKS)
            .addToSet(WoodBlockTypes.REDSTONE_BLOCKS)
            .addToSet(WoodBlockTypes.FURNITURE_BLOCKS)
            .addToSet(WoodBlockTypes.ROOFING_BLOCKS)
            .addToSet(WoodBlockTypes.SHINGLE_BLOCKS)
            .addToSet(WoodBlockTypes.LEAVES)
            .leavesColor(MapColor.PLANT));

    public static WoodBlockSetBuilder PALM_SET = registerWoodSet(new WoodBlockSetBuilder("palm",
            WOOD_STRENGTH, WOOD_BLAST_RESISTANCE, MapColor.DIRT, NoteBlockInstrument.BASS, SoundType.WOOD, ModNatureBlocks.PALM_SAPLING)
            .addToSet(WoodBlockTypes.LOG_BLOCKS)
            .addToSet(WoodBlockTypes.STRIPPED_LOG_BLOCKS)
            .addToSet(WoodBlockTypes.PLANK_BLOCKS)
            .addToSet(WoodBlockTypes.REDSTONE_BLOCKS)
            .addToSet(WoodBlockTypes.FURNITURE_BLOCKS)
            .addToSet(WoodBlockTypes.ROOFING_BLOCKS)
            .addToSet(WoodBlockTypes.SHINGLE_BLOCKS)
            .addToSet(WoodBlockTypes.LEAVES));

    public static WoodBlockSetBuilder WHITE_PALM_SET = registerWoodSet(new WoodBlockSetBuilder("white_palm",
            WOOD_STRENGTH, WOOD_BLAST_RESISTANCE, MapColor.QUARTZ, NoteBlockInstrument.BASS, SoundType.WOOD, ModNatureBlocks.WHITE_PALM_SAPLING)
            .addToSet(WoodBlockTypes.LOG_BLOCKS)
            .addToSet(WoodBlockTypes.STRIPPED_LOG_BLOCKS)
            .addToSet(WoodBlockTypes.PLANK_BLOCKS)
            .addToSet(WoodBlockTypes.REDSTONE_BLOCKS)
            .addToSet(WoodBlockTypes.FURNITURE_BLOCKS)
            .addToSet(WoodBlockTypes.ROOFING_BLOCKS)
            .addToSet(WoodBlockTypes.SHINGLE_BLOCKS));

    public static WoodBlockSetBuilder PINE_SET = registerWoodSet(new WoodBlockSetBuilder("pine",
            WOOD_STRENGTH, WOOD_BLAST_RESISTANCE, MapColor.COLOR_BROWN, NoteBlockInstrument.BASS, SoundType.WOOD, ModNatureBlocks.PINE_SAPLING)
            .addToSet(WoodBlockTypes.LOG_BLOCKS)
            .addToSet(WoodBlockTypes.STRIPPED_LOG_BLOCKS)
            .addToSet(WoodBlockTypes.PLANK_BLOCKS)
            .addToSet(WoodBlockTypes.REDSTONE_BLOCKS)
            .addToSet(WoodBlockTypes.FURNITURE_BLOCKS)
            .addToSet(WoodBlockTypes.ROOFING_BLOCKS)
            .addToSet(WoodBlockTypes.SHINGLE_BLOCKS)
            .addToSet(WoodBlockTypes.LEAVES));

    public static WoodBlockSetBuilder BLACK_PINE_SET = registerWoodSet(new WoodBlockSetBuilder("black_pine",
            WOOD_STRENGTH, WOOD_BLAST_RESISTANCE, MapColor.TERRACOTTA_ORANGE, NoteBlockInstrument.BASS, SoundType.WOOD, ModNatureBlocks.BLACK_PINE_SAPLING)
            .addToSet(WoodBlockTypes.LOG_BLOCKS)
            .addToSet(WoodBlockTypes.STRIPPED_LOG_BLOCKS)
            .addToSet(WoodBlockTypes.PLANK_BLOCKS)
            .addToSet(WoodBlockTypes.REDSTONE_BLOCKS)
            .addToSet(WoodBlockTypes.FURNITURE_BLOCKS)
            .addToSet(WoodBlockTypes.ROOFING_BLOCKS)
            .addToSet(WoodBlockTypes.SHINGLE_BLOCKS)
            .addToSet(WoodBlockTypes.LEAVES));

    public static WoodBlockSetBuilder WHITE_SPRUCE_SET = registerWoodSet(new WoodBlockSetBuilder("white_spruce",
            WOOD_STRENGTH, WOOD_BLAST_RESISTANCE, MapColor.PODZOL, NoteBlockInstrument.BASS, SoundType.WOOD, ModNatureBlocks.WHITE_SPRUCE_SAPLING)
            .addToSet(WoodBlockTypes.LOG_BLOCKS)
            .addToSet(WoodBlockTypes.STRIPPED_LOG_BLOCKS)
            .addToSet(WoodBlockTypes.PLANK_BLOCKS)
            .addToSet(WoodBlockTypes.ROOFING_BLOCKS)
            .addToSet(WoodBlockTypes.SHINGLE_BLOCKS)
            .addToSet(WoodBlockTypes.LEAVES));

    public static WoodBlockSetBuilder WILLOW_SET = registerWoodSet(new WoodBlockSetBuilder("willow",
            WOOD_STRENGTH, WOOD_BLAST_RESISTANCE, MapColor.COLOR_BROWN, NoteBlockInstrument.BASS, SoundType.WOOD, ModNatureBlocks.WILLOW_SAPLING)
            .addToSet(WoodBlockTypes.LOG_BLOCKS)
            .addToSet(WoodBlockTypes.STRIPPED_LOG_BLOCKS)
            .addToSet(WoodBlockTypes.PLANK_BLOCKS)
            .addToSet(WoodBlockTypes.REDSTONE_BLOCKS)
            .addToSet(WoodBlockTypes.FURNITURE_BLOCKS)
            .addToSet(WoodBlockTypes.ROOFING_BLOCKS)
            .addToSet(WoodBlockTypes.SHINGLE_BLOCKS)
            .addToSet(WoodBlockTypes.LEAVES)
            .leavesColor(MapColor.TERRACOTTA_GREEN));

    public static WoodBlockSetBuilder ROTTEN_SET = registerWoodSet(new WoodBlockSetBuilder("rotten",
            WOOD_STRENGTH, WOOD_BLAST_RESISTANCE, MapColor.GRASS, NoteBlockInstrument.BASS, SoundType.WOOD, null)
            .addToSet(WoodBlockTypes.LOG_BLOCKS)
            .addToSet(WoodBlockTypes.STRIPPED_LOG_BLOCKS)
            .addToSet(WoodBlockTypes.PLANK_BLOCKS)
            .addToSet(WoodBlockTypes.REDSTONE_BLOCKS)
            .addToSet(WoodBlockTypes.FURNITURE_BLOCKS)
            .addToSet(WoodBlockTypes.ROOFING_BLOCKS)
            .addToSet(WoodBlockTypes.SHINGLE_BLOCKS));

    public static WoodBlockSetBuilder SCORCHED_SET = registerWoodSet(new WoodBlockSetBuilder("scorched",
            WOOD_STRENGTH, WOOD_BLAST_RESISTANCE, MapColor.COLOR_BLACK, NoteBlockInstrument.BASS, SoundType.WOOD, null)
            .addToSet(WoodBlockTypes.LOG_BLOCKS)
            .addToSet(WoodBlockTypes.STRIPPED_LOG_BLOCKS)
            .addToSet(WoodBlockTypes.PLANK_BLOCKS)
            .addToSet(WoodBlockTypes.REDSTONE_BLOCKS)
            .addToSet(WoodBlockTypes.FURNITURE_BLOCKS)
            .addToSet(WoodBlockTypes.ROOFING_BLOCKS)
            .addToSet(WoodBlockTypes.SHINGLE_BLOCKS));

    public static WoodBlockSetBuilder MUSHROOM_SET = registerWoodSet(new WoodBlockSetBuilder("mushroom",
            2f, 0f, MapColor.WOOL, NoteBlockInstrument.BASS, SoundType.WOOD, null)
            .addToSet(WoodBlockTypes.MUSHROOM_STEM_BLOCKS)
            .addToSet(WoodBlockTypes.PLANK_BLOCKS)
            .addToSet(WoodBlockTypes.REDSTONE_BLOCKS)
            .addToSet(WoodBlockTypes.FURNITURE_BLOCKS)
            .addToSet(WoodBlockTypes.SHINGLE_BLOCKS));

    public static WoodBlockSetBuilder DARK_MUSHROOM_SET = registerWoodSet(new WoodBlockSetBuilder("dark_mushroom",
            2f, 0f, MapColor.TERRACOTTA_BLACK, NoteBlockInstrument.BASS, SoundType.WOOD, null)
            .addToSet(WoodBlockTypes.MUSHROOM_STEM_BLOCKS)
            .addToSet(WoodBlockTypes.PLANK_BLOCKS)
            .addToSet(WoodBlockTypes.REDSTONE_BLOCKS)
            .addToSet(WoodBlockTypes.FURNITURE_BLOCKS)
            .addToSet(WoodBlockTypes.SHINGLE_BLOCKS));

    public static WoodBlockSetBuilder GRAY_MUSHROOM_SET = registerWoodSet(new WoodBlockSetBuilder("gray_mushroom",
            2f, 0f, MapColor.COLOR_GRAY, NoteBlockInstrument.BASS, SoundType.WOOD, null)
            .addToSet(WoodBlockTypes.MUSHROOM_STEM_BLOCKS)
            .addToSet(WoodBlockTypes.PLANK_BLOCKS)
            .addToSet(WoodBlockTypes.REDSTONE_BLOCKS)
            .addToSet(WoodBlockTypes.FURNITURE_BLOCKS)
            .addToSet(WoodBlockTypes.SHINGLE_BLOCKS));

    private static WoodBlockSetBuilder registerWoodSet(WoodBlockSetBuilder set) {
        List<ItemStack> itemGroup = ItemGroupsME.WOOD_BLOCKS_CONTENTS;

        set.existingList.forEach((woodStoneTypes) -> {
            switch (woodStoneTypes) {
                case LOG_BLOCKS -> {
                    set.logBlocks = set.setName.equals("pale_oak")
                            ? createPaleOakWoodSet(woodStoneTypes.getPrefix() + set.setName + woodStoneTypes.getSuffix(), false, itemGroup)
                            : BlockSetRegistration.createWoodSet(woodStoneTypes.getPrefix() + set.setName + woodStoneTypes.getSuffix(), set.hardness, set.blastResistance, set.mapColor, set.instrument, set.soundGroup, itemGroup);
                    ItemGroupsME.NATURE_BLOCKS_CONTENTS.add(set.logBlocks.log().asItem().getDefaultInstance());
                }
                case MUSHROOM_STEM_BLOCKS -> {
                    set.mushroomStemBlocks = BlockSetRegistration.createMushroomStemSet(woodStoneTypes.getPrefix() + set.setName + woodStoneTypes.getSuffix(), set.hardness, set.blastResistance, set.mapColor, set.instrument, set.soundGroup, itemGroup);
                    ItemGroupsME.NATURE_BLOCKS_CONTENTS.add(set.mushroomStemBlocks.stem().asItem().getDefaultInstance());
                }
                case NETHER_STEM_BLOCKS -> {
                    set.logBlocks = BlockSetRegistration.createStemSet(woodStoneTypes.getPrefix() + set.setName + woodStoneTypes.getSuffix(), set.hardness, set.blastResistance, set.mapColor, set.instrument, set.soundGroup, itemGroup);
                    ItemGroupsME.NATURE_BLOCKS_CONTENTS.add(set.logBlocks.log().asItem().getDefaultInstance());
                }
                case STRIPPED_LOG_BLOCKS -> {
                    set.strippedLogBlocks = set.setName.equals("pale_oak")
                            ? createPaleOakWoodSet(woodStoneTypes.getPrefix() + set.setName + woodStoneTypes.getSuffix(), true, itemGroup)
                            : BlockSetRegistration.createWoodSet(woodStoneTypes.getPrefix() + set.setName + woodStoneTypes.getSuffix(), set.hardness, set.blastResistance, set.mapColor, set.instrument, set.soundGroup, itemGroup);
                    BlockDataMapCollector.registerStrippable(set.logBlocks.log(), set.strippedLogBlocks.log());
                    BlockDataMapCollector.registerStrippable(set.logBlocks.wood(), set.strippedLogBlocks.wood());
                    ModdedStrippedLogs.strippedLogs.add(set.strippedLogBlocks.log());
                }
                case STRIPPED_STEM_BLOCKS -> {
                    set.strippedLogBlocks = BlockSetRegistration.createStemSet(woodStoneTypes.getPrefix() + set.setName + woodStoneTypes.getSuffix(), set.hardness, set.blastResistance, set.mapColor, set.instrument, set.soundGroup, itemGroup);
                    BlockDataMapCollector.registerStrippable(set.logBlocks.log(), set.strippedLogBlocks.log());
                    BlockDataMapCollector.registerStrippable(set.logBlocks.wood(), set.strippedLogBlocks.wood());
                    ModdedStrippedLogs.strippedLogs.add(set.strippedLogBlocks.log());
                }
                case PLANK_BLOCKS ->
                        set.planksBlocks = BlockSetRegistration.createPlanksSet(woodStoneTypes.getPrefix() + set.setName + woodStoneTypes.getSuffix(), set.hardness, set.blastResistance, set.mapColor, set.instrument, set.soundGroup, itemGroup);
                case REDSTONE_BLOCKS ->
                        set.redstoneBlocks = BlockSetRegistration.createWoodRedstoneSet(woodStoneTypes.getPrefix() + set.setName + woodStoneTypes.getSuffix(), set.hardness, set.blastResistance, set.mapColor, set.soundGroup, set.planksBlocks.base(), itemGroup);
                case FURNITURE_BLOCKS -> {
                    set.furnitureBlocks = BlockSetRegistration.createWoodFurnitureSet(woodStoneTypes.getPrefix() + set.setName + woodStoneTypes.getSuffix(), set.hardness, set.blastResistance, set.mapColor, set.soundGroup, set.planksBlocks.base(), itemGroup);
                    ItemGroupsME.DECORATIVES_BLOCKS_CONTENT.add(set.furnitureBlocks.stool().asItem().getDefaultInstance());
                    ItemGroupsME.DECORATIVES_BLOCKS_CONTENT.add(set.furnitureBlocks.bench().asItem().getDefaultInstance());
                    ItemGroupsME.DECORATIVES_BLOCKS_CONTENT.add(set.furnitureBlocks.table().asItem().getDefaultInstance());
                    ItemGroupsME.DECORATIVES_BLOCKS_CONTENT.add(set.furnitureBlocks.chair().asItem().getDefaultInstance());
                    ItemGroupsME.DECORATIVES_BLOCKS_CONTENT.add(set.furnitureBlocks.ladder().asItem().getDefaultInstance());
                }
                case ROOFING_BLOCKS ->
                        set.roofingBlocks = BlockSetRegistration.createRegularSet(woodStoneTypes.getPrefix() + set.setName + woodStoneTypes.getSuffix(), set.hardness, set.blastResistance, set.mapColor, set.instrument, set.soundGroup, false, itemGroup, false);
                case SHINGLE_BLOCKS ->
                        set.shinglesBlocks = BlockSetRegistration.createRegularSet(woodStoneTypes.getPrefix() + set.setName + woodStoneTypes.getSuffix(), set.hardness, set.blastResistance, set.mapColor, set.instrument, set.soundGroup, false, itemGroup, false);
                case LEAVES -> {
                    if(set.setName.equals("mallorn")){
                        set.leaves = getVanillaOrCreateNew(woodStoneTypes.getPrefix() + set.setName + woodStoneTypes.getSuffix(),
                                (settings) -> new ModLeavesBlock(0.01F, settings, false), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LEAVES)
                                        .strength(LEAVES_STRENGTH).mapColor(MapColor.COLOR_YELLOW).sound(SoundType.GRASS).ignitedByLava(), itemGroup);
                    } else if (set.setName.equals("holly")) {
                        set.leaves = getVanillaOrCreateNew(woodStoneTypes.getPrefix() + set.setName + woodStoneTypes.getSuffix(),
                                (settings) -> new BerryHollyLeavesBlock(0.01F, settings), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LEAVES)
                                        .strength(LEAVES_STRENGTH).mapColor(MapColor.COLOR_YELLOW).sound(SoundType.GRASS).ignitedByLava(), itemGroup);
                    } else if (set.setName.equals("pale_oak")) {
                        set.leaves = getVanillaOrCreateNew(woodStoneTypes.getPrefix() + set.setName + woodStoneTypes.getSuffix(),
                                PaleOakLeavesBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LEAVES)
                                        .strength(0.2F).mapColor(MapColor.COLOR_LIGHT_GRAY).sound(SoundType.GRASS).ignitedByLava(), itemGroup);
                        BlockDataMapCollector.registerCompostable(set.leaves, 0.3F);
                    } else {
                        set.leaves = getVanillaOrCreateNew(woodStoneTypes.getPrefix() + set.setName + woodStoneTypes.getSuffix(),
                                LeavesBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LEAVES)
                                        .strength(LEAVES_STRENGTH).mapColor(set.leavesMapColor).sound(SoundType.GRASS).ignitedByLava(), itemGroup);
                    }
                    BlockDataMapCollector.registerFlammable(set.leaves, 5, 60);
                }
            }
        });

        woodSetsList.add(set);

        return set;
    }

    private static BlockRecordTypes.WoodSet createPaleOakWoodSet(String name, boolean stripped, List<ItemStack> group) {
        MapColor endColor = MapColor.QUARTZ;
        MapColor barkColor = stripped ? MapColor.QUARTZ : MapColor.STONE;
        BlockBehaviour.Properties logProperties = BlockBehaviour.Properties.of()
                .mapColor(state -> state.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? endColor : barkColor)
                .instrument(NoteBlockInstrument.BASS).sound(SoundType.WOOD).strength(2.0F).ignitedByLava();
        BlockBehaviour.Properties woodProperties = BlockBehaviour.Properties.of()
                .mapColor(barkColor).instrument(NoteBlockInstrument.BASS).sound(SoundType.WOOD)
                .strength(2.0F).ignitedByLava();

        Block log = getVanillaOrCreateNew(name + "_log", RotatedPillarBlock::new, logProperties, group);
        Block wood = getVanillaOrCreateNew(name + "_wood", RotatedPillarBlock::new, woodProperties, group);
        BlockBehaviour.Properties derived = BlockBehaviour.Properties.ofFullCopy(wood)
                .mapColor(barkColor).strength(2.0F);
        Block slab = getVanillaOrCreateNew(name + "_wood_slab", SlabBlock::new, derived, group);
        Block verticalSlab = getVanillaOrCreateNew(name + "_wood_vertical_slab", VerticalSlabBlock::new,
                BlockBehaviour.Properties.ofFullCopy(wood).mapColor(barkColor).strength(2.0F), group);
        Block stairs = getVanillaOrCreateNew(name + "_wood_stairs",
                properties -> new StairBlock(wood.defaultBlockState(), properties),
                BlockBehaviour.Properties.ofFullCopy(wood).mapColor(barkColor).strength(2.0F), group);
        Block wall = getVanillaOrCreateNew(name + "_wood_wall", WallBlock::new,
                BlockBehaviour.Properties.ofFullCopy(wood).mapColor(barkColor).strength(2.0F), group);
        Block fence = getVanillaOrCreateNew(name + "_wood_fence", FenceBlock::new,
                BlockBehaviour.Properties.ofFullCopy(wood).mapColor(barkColor).strength(2.0F), group);

        for (Block block : List.of(log, wood, slab, verticalSlab, stairs, wall, fence)) {
            BlockDataMapCollector.registerFlammable(block, 5, 5);
        }
        BlockDataMapCollector.registerFuel(slab, 150);
        BlockDataMapCollector.registerFuel(verticalSlab, 150);
        BlockDataMapCollector.registerFuel(stairs, 300);
        BlockDataMapCollector.registerFuel(wall, 300);
        BlockDataMapCollector.registerFuel(fence, 300);
        return new BlockRecordTypes.WoodSet(log, wood, slab, verticalSlab, stairs, wall, fence);
    }

    public static void registerModBlockSets() {
        MiddleEarth.LOGGER.logDebugMsg("Registering Wood Block Sets for " + MiddleEarth.MOD_ID);
    }
}
