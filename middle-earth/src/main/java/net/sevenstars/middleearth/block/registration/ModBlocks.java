package net.sevenstars.middleearth.block.registration;

import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.block.special.*;
import net.sevenstars.middleearth.block.special.dirts.CustomFarmlandBlock;
import net.sevenstars.middleearth.block.special.dirts.CustomGrassBlock;
import net.sevenstars.middleearth.block.special.dirts.CustomPathBlock;
import net.sevenstars.middleearth.block.special.gemstones.CustomBuddingGemBlock;
import net.sevenstars.middleearth.block.special.pointedBlocks.PointedDolomiteBlock;
import net.sevenstars.middleearth.block.special.pointedBlocks.PointedGalonnBlock;
import net.sevenstars.middleearth.block.special.pointedBlocks.PointedIzherabanBlock;
import net.sevenstars.middleearth.block.special.pointedBlocks.PointedLimestoneBlock;
import net.sevenstars.middleearth.block.special.verticalSlabs.VerticalSlabBlock;
import net.sevenstars.middleearth.datageneration.content.TranslationEntries;
import net.sevenstars.middleearth.datageneration.content.loot_tables.BlockDrops;
import net.sevenstars.middleearth.item.utils.ModItemGroups;
import net.minecraft.block.*;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.ColorCode;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.registries.RegistryAliases;

import java.util.List;
import java.util.function.Function;

public class ModBlocks {
    public static final float DIRT_STRENGTH = 0.6f;
    public static final float SLAB_RESISTANCE = 6.0f; // From explosions

    public static final Block POINTED_DOLOMITE = registerStoneBlock("pointed_dolomite",
            PointedDolomiteBlock::new,AbstractBlock.Settings.copy(Blocks.POINTED_DRIPSTONE).ticksRandomly().strength(1.5F, 3.0F).dynamicBounds().offset(AbstractBlock.OffsetType.XZ).pistonBehavior(PistonBehavior.DESTROY).solidBlock(Blocks::never), false);
    public static final Block POINTED_LIMESTONE = registerStoneBlock("pointed_limestone",
            PointedLimestoneBlock::new,AbstractBlock.Settings.copy(Blocks.POINTED_DRIPSTONE).ticksRandomly().strength(1.5F, 3.0F).dynamicBounds().offset(AbstractBlock.OffsetType.XZ).pistonBehavior(PistonBehavior.DESTROY).solidBlock(Blocks::never), false);
    public static final Block POINTED_IZHERABAN = registerStoneBlock("pointed_izheraban",
            PointedIzherabanBlock::new,AbstractBlock.Settings.copy(Blocks.POINTED_DRIPSTONE).ticksRandomly().strength(1.5F, 3.0F).dynamicBounds().offset(AbstractBlock.OffsetType.XZ).pistonBehavior(PistonBehavior.DESTROY).solidBlock(Blocks::never), false);
    public static final Block POINTED_GALONN = registerStoneBlock("pointed_galonn",
            PointedGalonnBlock::new,AbstractBlock.Settings.copy(Blocks.POINTED_DRIPSTONE).ticksRandomly().strength(1.5F, 3.0F).dynamicBounds().offset(AbstractBlock.OffsetType.XZ).pistonBehavior(PistonBehavior.DESTROY).solidBlock(Blocks::never), false);

    public static final Block BLACK_WATTLE_TRAPDOOR = registerWoodBlock("black_wattle_trapdoor",
            (settings) -> new TrapdoorBlock(BlockSetType.OAK, settings), AbstractBlock.Settings.copy(Blocks.OAK_TRAPDOOR), true);
    public static final Block DARK_WATTLE_TRAPDOOR = registerWoodBlock("dark_wattle_trapdoor",
            (settings) -> new TrapdoorBlock(BlockSetType.OAK, settings), AbstractBlock.Settings.copy(Blocks.OAK_TRAPDOOR), true);
    public static final Block GREEN_WATTLE_TRAPDOOR = registerWoodBlock("green_wattle_trapdoor",
            (settings) -> new TrapdoorBlock(BlockSetType.OAK, settings), AbstractBlock.Settings.copy(Blocks.OAK_TRAPDOOR), true);
    public static final Block RED_WATTLE_TRAPDOOR = registerWoodBlock("red_wattle_trapdoor",
            (settings) -> new TrapdoorBlock(BlockSetType.OAK, settings), AbstractBlock.Settings.copy(Blocks.OAK_TRAPDOOR), true);
    public static final Block WATTLE_TRAPDOOR = registerWoodBlock("wattle_trapdoor",
            (settings) -> new TrapdoorBlock(BlockSetType.OAK, settings), AbstractBlock.Settings.copy(Blocks.OAK_TRAPDOOR), true);

    public static final Block SNOWY_GRASS_BLOCK = registerMiscBlock("snowy_grass_block",
            Block::new,AbstractBlock.Settings.copy(Blocks.GRASS_BLOCK),false);

    public static final Block SNOWY_DIRT = registerMiscBlock("snowy_dirt",
            Block::new,AbstractBlock.Settings.copy(Blocks.COARSE_DIRT).strength(DIRT_STRENGTH).sounds(BlockSoundGroup.GRAVEL),true);
    public static final Block SNOWY_DIRT_SLAB = registerMiscBlock("snowy_dirt_slab",
            SlabBlock::new,AbstractBlock.Settings.copy(SNOWY_DIRT),true);
    public static final Block SNOWY_DIRT_STAIRS = registerMiscBlock("snowy_dirt_stairs",
            (settings) -> new StairsBlock(SNOWY_DIRT.getDefaultState(), settings), AbstractBlock.Settings.copy(SNOWY_DIRT),true);

    public static final Block DRY_DIRT = registerMiscBlock("dry_dirt",
            Block::new,AbstractBlock.Settings.copy(Blocks.DIRT).strength(DIRT_STRENGTH).sounds(BlockSoundGroup.GRAVEL),true);
    public static final Block DRY_DIRT_SLAB = registerMiscBlock("dry_dirt_slab",
            SlabBlock::new,AbstractBlock.Settings.copy(DRY_DIRT),true);
    public static final Block DRY_DIRT_STAIRS = registerMiscBlock("dry_dirt_stairs",
            (settings) -> new StairsBlock(DRY_DIRT.getDefaultState(), settings), AbstractBlock.Settings.copy(DRY_DIRT),true);

    public static final Block GRASSY_DIRT = registerMiscBlock("grassy_dirt",
            Block::new,AbstractBlock.Settings.copy(Blocks.DIRT).strength(DIRT_STRENGTH).sounds(BlockSoundGroup.GRAVEL),true);
    public static final Block GRASSY_DIRT_SLAB = registerMiscBlock("grassy_dirt_slab",
            SlabBlock::new,AbstractBlock.Settings.copy(GRASSY_DIRT),true);
    public static final Block GRASSY_DIRT_STAIRS = registerMiscBlock("grassy_dirt_stairs",
            (settings) -> new StairsBlock(GRASSY_DIRT.getDefaultState(), settings), AbstractBlock.Settings.copy(GRASSY_DIRT),true);

    public static final Block PEBBLED_GRASS = registerMiscBlock("pebbled_grass",
            Block::new,AbstractBlock.Settings.copy(Blocks.COBBLESTONE).sounds(BlockSoundGroup.STONE),true);
    public static final Block PEBBLED_GRASS_SLAB = registerMiscBlock("pebbled_grass_slab",
            SlabBlock::new,AbstractBlock.Settings.copy(PEBBLED_GRASS),true);
    public static final Block PEBBLED_GRASS_STAIRS = registerMiscBlock("pebbled_grass_stairs",
            (settings) -> new StairsBlock(PEBBLED_GRASS.getDefaultState(), settings), AbstractBlock.Settings.copy(PEBBLED_GRASS),true);

    public static final Block COBBLY_DIRT = registerMiscBlock("cobbly_dirt",
            Block::new,AbstractBlock.Settings.copy(Blocks.COARSE_DIRT).strength(DIRT_STRENGTH).sounds(BlockSoundGroup.GRAVEL),true);
    public static final Block COBBLY_DIRT_SLAB = registerMiscBlock("cobbly_dirt_slab",
            SlabBlock::new,AbstractBlock.Settings.copy(COBBLY_DIRT),true);
    public static final Block COBBLY_DIRT_STAIRS = registerMiscBlock("cobbly_dirt_stairs",
            (settings) -> new StairsBlock(COBBLY_DIRT.getDefaultState(), settings), AbstractBlock.Settings.copy(COBBLY_DIRT),true);

    public static final Block LOAM = registerMiscBlock("loam",
            Block::new,AbstractBlock.Settings.copy(Blocks.DIRT).strength(DIRT_STRENGTH).sounds(BlockSoundGroup.GRAVEL),true);
    public static final Block LOAM_SLAB = registerMiscBlock("loam_slab",
            SlabBlock::new,AbstractBlock.Settings.copy(LOAM),true);
    public static final Block LOAM_STAIRS = registerMiscBlock("loam_stairs",
            (settings) -> new StairsBlock(LOAM.getDefaultState(), settings), AbstractBlock.Settings.copy(LOAM),true);

    public static final Block LOAM_GRASS_BLOCK = registerMiscBlock("loam_grass_block",
            (settings) -> new CustomGrassBlock(settings, LOAM), AbstractBlock.Settings.copy(Blocks.GRASS_BLOCK),false);
    
    public static final Block GRASSY_LOAM = registerMiscBlock("grassy_loam",
            Block::new,AbstractBlock.Settings.copy(Blocks.DIRT).strength(DIRT_STRENGTH).sounds(BlockSoundGroup.GRAVEL),true);
    public static final Block GRASSY_LOAM_SLAB = registerMiscBlock("grassy_loam_slab",
            SlabBlock::new,AbstractBlock.Settings.copy(GRASSY_LOAM),true);
    public static final Block GRASSY_LOAM_STAIRS = registerMiscBlock("grassy_loam_stairs",
            (settings) -> new StairsBlock(GRASSY_LOAM.getDefaultState(), settings), AbstractBlock.Settings.copy(GRASSY_LOAM),true);

    public static final Block LOAM_PATH = registerMiscBlock("loam_path",
            (settings) -> new CustomPathBlock(settings, LOAM), AbstractBlock.Settings.copy(Blocks.DIRT_PATH),true);
    public static final Block LOAM_FARMLAND = registerMiscBlock("loam_farmland",
            (settings) -> new CustomFarmlandBlock(settings, LOAM), AbstractBlock.Settings.copy(Blocks.FARMLAND),true);

    public static final Block PEAT = registerMiscBlock("peat",
            Block::new,AbstractBlock.Settings.copy(Blocks.DIRT).strength(DIRT_STRENGTH).sounds(BlockSoundGroup.GRAVEL),true);
    public static final Block PEAT_SLAB = registerMiscBlock("peat_slab",
            SlabBlock::new,AbstractBlock.Settings.copy(PEAT),true);
    public static final Block PEAT_STAIRS = registerMiscBlock("peat_stairs",
            (settings) -> new StairsBlock(PEAT.getDefaultState(), settings), AbstractBlock.Settings.copy(PEAT),true);

    public static final Block PEAT_GRASS_BLOCK = registerMiscBlock("peat_grass_block",
            (settings) -> new CustomGrassBlock(settings, PEAT), AbstractBlock.Settings.copy(Blocks.GRASS_BLOCK),false);

    public static final Block GRASSY_PEAT = registerMiscBlock("grassy_peat",
            Block::new,AbstractBlock.Settings.copy(Blocks.DIRT).strength(DIRT_STRENGTH).sounds(BlockSoundGroup.GRAVEL),true);
    public static final Block GRASSY_PEAT_SLAB = registerMiscBlock("grassy_peat_slab",
            SlabBlock::new,AbstractBlock.Settings.copy(GRASSY_PEAT),true);
    public static final Block GRASSY_PEAT_STAIRS = registerMiscBlock("grassy_peat_stairs",
            (settings) -> new StairsBlock(GRASSY_PEAT.getDefaultState(), settings), AbstractBlock.Settings.copy(GRASSY_PEAT),true);

    public static final Block PEAT_PATH = registerMiscBlock("peat_path",
            (settings) -> new CustomPathBlock(settings, PEAT), AbstractBlock.Settings.copy(Blocks.DIRT_PATH),true);
    public static final Block PEAT_FARMLAND = registerMiscBlock("peat_farmland",
            (settings) -> new CustomFarmlandBlock(settings, PEAT), AbstractBlock.Settings.copy(Blocks.FARMLAND),true);

    public static final Block SILT = registerMiscBlock("silt",
            Block::new,AbstractBlock.Settings.copy(Blocks.DIRT).strength(DIRT_STRENGTH).sounds(BlockSoundGroup.GRAVEL),true);
    public static final Block SILT_SLAB = registerMiscBlock("silt_slab",
            SlabBlock::new,AbstractBlock.Settings.copy(SILT),true);
    public static final Block SILT_STAIRS = registerMiscBlock("silt_stairs",
            (settings) -> new StairsBlock(SILT.getDefaultState(), settings), AbstractBlock.Settings.copy(SILT),true);

    public static final Block SILT_GRASS_BLOCK = registerMiscBlock("silt_grass_block",
            (settings) -> new CustomGrassBlock(settings, SILT), AbstractBlock.Settings.copy(Blocks.GRASS_BLOCK),false);

    public static final Block GRASSY_SILT = registerMiscBlock("grassy_silt",
            Block::new,AbstractBlock.Settings.copy(Blocks.DIRT).strength(DIRT_STRENGTH).sounds(BlockSoundGroup.GRAVEL),true);
    public static final Block GRASSY_SILT_SLAB = registerMiscBlock("grassy_silt_slab",
            SlabBlock::new,AbstractBlock.Settings.copy(GRASSY_SILT),true);
    public static final Block GRASSY_SILT_STAIRS = registerMiscBlock("grassy_silt_stairs",
            (settings) -> new StairsBlock(GRASSY_SILT.getDefaultState(), settings), AbstractBlock.Settings.copy(GRASSY_SILT),true);

    public static final Block SILT_PATH = registerMiscBlock("silt_path",
            (settings) -> new CustomPathBlock(settings, SILT), AbstractBlock.Settings.copy(Blocks.DIRT_PATH),true);
    public static final Block SILT_FARMLAND = registerMiscBlock("silt_farmland",
            (settings) -> new CustomFarmlandBlock(settings, SILT), AbstractBlock.Settings.copy(Blocks.FARMLAND),true);

    public static final Block MIRE = registerMiscBlock("mire",
            MudBlock::new, AbstractBlock.Settings.copy(Blocks.DIRT).strength(DIRT_STRENGTH).sounds(BlockSoundGroup.MUD),true);
    public static final Block MIRE_SLAB = registerMiscBlock("mire_slab",
            SlabBlock::new,AbstractBlock.Settings.copy(MIRE),true);
    public static final Block MIRE_STAIRS = registerMiscBlock("mire_stairs",
            (settings) -> new StairsBlock(MIRE.getDefaultState(), settings), AbstractBlock.Settings.copy(MIRE),true);

    public static final Block TURF = registerMiscBlock("turf",
            Block::new,AbstractBlock.Settings.copy(Blocks.DIRT).strength(DIRT_STRENGTH).sounds(BlockSoundGroup.GRASS),true);
    public static final Block TURF_SLAB = registerMiscBlock("turf_slab",
            SlabBlock::new,AbstractBlock.Settings.copy(TURF),true);
    public static final Block TURF_STAIRS = registerMiscBlock("turf_stairs",
            (settings) -> new StairsBlock(TURF.getDefaultState(), settings), AbstractBlock.Settings.copy(TURF),true);
    public static final Block TURF_VERTICAL_SLAB = registerMiscBlock("turf_vertical_slab",
            VerticalSlabBlock::new, AbstractBlock.Settings.copy(TURF),true);

    public static final Block DIRTY_ROOTS = registerMiscBlock("dirty_roots",
            Block::new,AbstractBlock.Settings.copy(Blocks.DIRT).strength(DIRT_STRENGTH).sounds(BlockSoundGroup.GRAVEL),true);
    public static final Block DIRTY_ROOTS_SLAB = registerMiscBlock("dirty_roots_slab",
            SlabBlock::new,AbstractBlock.Settings.copy(DIRTY_ROOTS),true);
    public static final Block DIRTY_ROOTS_STAIRS = registerMiscBlock("dirty_roots_stairs",
            (settings) -> new StairsBlock(DIRTY_ROOTS.getDefaultState(), settings), AbstractBlock.Settings.copy(DIRTY_ROOTS),true);

    public static final Block FOUL_DIRT = registerMiscBlock("foul_dirt",
            Block::new,AbstractBlock.Settings.copy(Blocks.COARSE_DIRT).strength(DIRT_STRENGTH).sounds(BlockSoundGroup.GRAVEL),true);
    public static final Block FOUL_DIRT_SLAB = registerMiscBlock("foul_dirt_slab",
            SlabBlock::new,AbstractBlock.Settings.copy(FOUL_DIRT),true);
    public static final Block FOUL_DIRT_STAIRS = registerMiscBlock("foul_dirt_stairs",
            (settings) -> new StairsBlock(FOUL_DIRT.getDefaultState(), settings), AbstractBlock.Settings.copy(FOUL_DIRT),true);

    public static final Block ASHEN_DIRT = registerMiscBlock("ashen_dirt",
            Block::new,AbstractBlock.Settings.copy(Blocks.DIRT).strength(DIRT_STRENGTH).sounds(BlockSoundGroup.GRAVEL),true);
    public static final Block ASHEN_DIRT_SLAB = registerMiscBlock("ashen_dirt_slab",
            SlabBlock::new,AbstractBlock.Settings.copy(ASHEN_DIRT),true);
    public static final Block ASHEN_DIRT_STAIRS = registerMiscBlock("ashen_dirt_stairs",
            (settings) -> new StairsBlock(ASHEN_DIRT.getDefaultState(), settings), AbstractBlock.Settings.copy(ASHEN_DIRT),true);

    public static final Block COBBLY_ASHEN_DIRT = registerMiscBlock("cobbly_ashen_dirt",
            Block::new,AbstractBlock.Settings.copy(Blocks.COARSE_DIRT).strength(DIRT_STRENGTH).sounds(BlockSoundGroup.GRAVEL),true);
    public static final Block COBBLY_ASHEN_DIRT_SLAB = registerMiscBlock("cobbly_ashen_dirt_slab",
            SlabBlock::new,AbstractBlock.Settings.copy(COBBLY_ASHEN_DIRT),true);
    public static final Block COBBLY_ASHEN_DIRT_STAIRS = registerMiscBlock("cobbly_ashen_dirt_stairs",
            (settings) -> new StairsBlock(COBBLY_ASHEN_DIRT.getDefaultState(), settings), AbstractBlock.Settings.copy(ASHEN_DIRT),true);

    public static final Block ASHEN_SAND = registerMiscBlock("ashen_sand",
            (settings) -> new ColoredFallingBlock(new ColorCode(14406560), settings), AbstractBlock.Settings.copy(Blocks.SAND).strength(DIRT_STRENGTH).sounds(BlockSoundGroup.GRAVEL),true);
    public static final Block ASHEN_SAND_LAYER = registerMiscBlock("ashen_sand_layer",
            (settings) -> new LayersBlock(settings, ASHEN_SAND),AbstractBlock.Settings.copy(ASHEN_SAND), false);

    public static final Block ASHEN_GRAVEL = registerMiscBlock("ashen_gravel",
            (settings) -> new ColoredFallingBlock(new ColorCode(14406560), settings), AbstractBlock.Settings.copy(Blocks.GRAVEL).strength(DIRT_STRENGTH).sounds(BlockSoundGroup.GRAVEL),true);
    public static final Block ASHEN_GRAVEL_LAYER = registerMiscBlock("ashen_gravel_layer",
            (settings) -> new LayersBlock(settings, ASHEN_GRAVEL), AbstractBlock.Settings.copy(ASHEN_GRAVEL), false);

    public static final Block EMBERS = registerMiscBlock("embers",
            Block::new, AbstractBlock.Settings.copy(Blocks.NETHERRACK),true);

    public static final Block SKELETAL_PILE = registerMiscBlock("skeletal_pile",
            (settings) -> new ColoredFallingBlock(new ColorCode(14406560), settings), AbstractBlock.Settings.copy(Blocks.BONE_BLOCK).strength(DIRT_STRENGTH).sounds(BlockSoundGroup.BONE),true);
    public static final Block SKELETAL_PILE_LAYER = registerMiscBlock("skeletal_pile_layer",
            (settings) -> new LayersBlock(settings, SKELETAL_PILE),AbstractBlock.Settings.copy(SKELETAL_PILE), false);

    public static final Block WASTE_PILE = registerMiscBlock("waste_pile",
            (settings) -> new ColoredFallingBlock(new ColorCode(14406560), settings), AbstractBlock.Settings.copy(Blocks.MUD).strength(DIRT_STRENGTH).sounds(BlockSoundGroup.MUD),true);
    public static final Block WASTE_PILE_LAYER = registerMiscBlock("waste_pile_layer",
            (settings) -> new LayersBlock(settings, WASTE_PILE),AbstractBlock.Settings.copy(WASTE_PILE), false);

    public static final Block WATTLE_AND_BRICK = registerMiscBlock("wattle_and_brick",
            Block::new,AbstractBlock.Settings.copy(Blocks.PACKED_MUD),true);
    public static final Block WATTLE_AND_BRICK_CROSS = registerMiscBlock("wattle_and_brick_cross",
            Block::new,AbstractBlock.Settings.copy(Blocks.PACKED_MUD),true);
    public static final Block WATTLE_AND_BRICK_RIGHT = registerMiscBlock("wattle_and_brick_right",
            Block::new,AbstractBlock.Settings.copy(Blocks.PACKED_MUD),true);
    public static final Block WATTLE_AND_BRICK_LEFT = registerMiscBlock("wattle_and_brick_left",
            Block::new,AbstractBlock.Settings.copy(Blocks.PACKED_MUD),true);
    public static final Block WATTLE_AND_BRICK_PILLAR = registerMiscBlock("wattle_and_brick_pillar",
            Block::new,AbstractBlock.Settings.copy(Blocks.PACKED_MUD),true);
    public static final Block WATTLE_AND_BRICK_DIAMOND = registerMiscBlock("wattle_and_brick_diamond",
            Block::new,AbstractBlock.Settings.copy(Blocks.PACKED_MUD),true);

    public static final Block WATTLE_AND_WHITE_DAUB = registerMiscBlock("wattle_and_white_daub",
            Block::new,AbstractBlock.Settings.copy(Blocks.PACKED_MUD),true);
    public static final Block WATTLE_AND_WHITE_DAUB_CROSS = registerMiscBlock("wattle_and_white_daub_cross",
            Block::new,AbstractBlock.Settings.copy(Blocks.PACKED_MUD),true);
    public static final Block WATTLE_AND_WHITE_DAUB_RIGHT = registerMiscBlock("wattle_and_white_daub_right",
            Block::new,AbstractBlock.Settings.copy(Blocks.PACKED_MUD),true);
    public static final Block WATTLE_AND_WHITE_DAUB_LEFT = registerMiscBlock("wattle_and_white_daub_left",
            Block::new,AbstractBlock.Settings.copy(Blocks.PACKED_MUD),true);
    public static final Block WATTLE_AND_WHITE_DAUB_PILLAR = registerMiscBlock("wattle_and_white_daub_pillar",
            Block::new,AbstractBlock.Settings.copy(Blocks.PACKED_MUD),true);
    public static final Block WATTLE_AND_WHITE_DAUB_DIAMOND = registerMiscBlock("wattle_and_white_daub_diamond",
            Block::new,AbstractBlock.Settings.copy(Blocks.PACKED_MUD),true);

    public static final Block BLACK_WATTLE_AND_WHITE_DAUB = registerMiscBlock("black_wattle_and_white_daub",
            Block::new,AbstractBlock.Settings.copy(Blocks.PACKED_MUD),true);
    public static final Block BLACK_WATTLE_AND_WHITE_DAUB_CROSS = registerMiscBlock("black_wattle_and_white_daub_cross",
            Block::new,AbstractBlock.Settings.copy(Blocks.PACKED_MUD),true);
    public static final Block BLACK_WATTLE_AND_WHITE_DAUB_RIGHT = registerMiscBlock("black_wattle_and_white_daub_right",
            Block::new,AbstractBlock.Settings.copy(Blocks.PACKED_MUD),true);
    public static final Block BLACK_WATTLE_AND_WHITE_DAUB_LEFT = registerMiscBlock("black_wattle_and_white_daub_left",
            Block::new,AbstractBlock.Settings.copy(Blocks.PACKED_MUD),true);
    public static final Block BLACK_WATTLE_AND_WHITE_DAUB_PILLAR = registerMiscBlock("black_wattle_and_white_daub_pillar",
            Block::new,AbstractBlock.Settings.copy(Blocks.PACKED_MUD),true);
    public static final Block BLACK_WATTLE_AND_WHITE_DAUB_DIAMOND = registerMiscBlock("black_wattle_and_white_daub_diamond",
            Block::new,AbstractBlock.Settings.copy(Blocks.PACKED_MUD),true);

    public static final Block GREEN_WATTLE_AND_WHITE_DAUB = registerMiscBlock("green_wattle_and_white_daub",
            Block::new,AbstractBlock.Settings.copy(Blocks.PACKED_MUD),true);
    public static final Block GREEN_WATTLE_AND_WHITE_DAUB_CROSS = registerMiscBlock("green_wattle_and_white_daub_cross",
            Block::new,AbstractBlock.Settings.copy(Blocks.PACKED_MUD),true);
    public static final Block GREEN_WATTLE_AND_WHITE_DAUB_RIGHT = registerMiscBlock("green_wattle_and_white_daub_right",
            Block::new,AbstractBlock.Settings.copy(Blocks.PACKED_MUD),true);
    public static final Block GREEN_WATTLE_AND_WHITE_DAUB_LEFT = registerMiscBlock("green_wattle_and_white_daub_left",
            Block::new,AbstractBlock.Settings.copy(Blocks.PACKED_MUD),true);
    public static final Block GREEN_WATTLE_AND_WHITE_DAUB_PILLAR = registerMiscBlock("green_wattle_and_white_daub_pillar",
            Block::new,AbstractBlock.Settings.copy(Blocks.PACKED_MUD),true);
    public static final Block GREEN_WATTLE_AND_WHITE_DAUB_DIAMOND = registerMiscBlock("green_wattle_and_white_daub_diamond",
            Block::new,AbstractBlock.Settings.copy(Blocks.PACKED_MUD),true);

    public static final Block RED_WATTLE_AND_WHITE_DAUB = registerMiscBlock("red_wattle_and_white_daub",
            Block::new,AbstractBlock.Settings.copy(Blocks.PACKED_MUD),true);
    public static final Block RED_WATTLE_AND_WHITE_DAUB_CROSS = registerMiscBlock("red_wattle_and_white_daub_cross",
            Block::new,AbstractBlock.Settings.copy(Blocks.PACKED_MUD),true);
    public static final Block RED_WATTLE_AND_WHITE_DAUB_RIGHT = registerMiscBlock("red_wattle_and_white_daub_right",
            Block::new,AbstractBlock.Settings.copy(Blocks.PACKED_MUD),true);
    public static final Block RED_WATTLE_AND_WHITE_DAUB_LEFT = registerMiscBlock("red_wattle_and_white_daub_left",
            Block::new,AbstractBlock.Settings.copy(Blocks.PACKED_MUD),true);
    public static final Block RED_WATTLE_AND_WHITE_DAUB_PILLAR = registerMiscBlock("red_wattle_and_white_daub_pillar",
            Block::new,AbstractBlock.Settings.copy(Blocks.PACKED_MUD),true);
    public static final Block RED_WATTLE_AND_WHITE_DAUB_DIAMOND = registerMiscBlock("red_wattle_and_white_daub_diamond",
            Block::new,AbstractBlock.Settings.copy(Blocks.PACKED_MUD),true);

    public static final Block DARK_WATTLE_AND_DARK_DAUB = registerMiscBlock("dark_wattle_and_dark_daub",
            Block::new,AbstractBlock.Settings.copy(Blocks.PACKED_MUD),true);
    public static final Block DARK_WATTLE_AND_DARK_DAUB_CROSS = registerMiscBlock("dark_wattle_and_dark_daub_cross",
            Block::new,AbstractBlock.Settings.copy(Blocks.PACKED_MUD),true);
    public static final Block DARK_WATTLE_AND_DARK_DAUB_RIGHT = registerMiscBlock("dark_wattle_and_dark_daub_right",
            Block::new,AbstractBlock.Settings.copy(Blocks.PACKED_MUD),true);
    public static final Block DARK_WATTLE_AND_DARK_DAUB_LEFT = registerMiscBlock("dark_wattle_and_dark_daub_left",
            Block::new,AbstractBlock.Settings.copy(Blocks.PACKED_MUD),true);
    public static final Block DARK_WATTLE_AND_DARK_DAUB_PILLAR = registerMiscBlock("dark_wattle_and_dark_daub_pillar",
            Block::new,AbstractBlock.Settings.copy(Blocks.PACKED_MUD),true);
    public static final Block DARK_WATTLE_AND_DARK_DAUB_DIAMOND = registerMiscBlock("dark_wattle_and_dark_daub_diamond",
            Block::new,AbstractBlock.Settings.copy(Blocks.PACKED_MUD),true);

    public static final Block WATTLE_AND_YELLOW_DAUB = registerMiscBlock("wattle_and_yellow_daub",
            Block::new,AbstractBlock.Settings.copy(Blocks.PACKED_MUD),true);
    public static final Block WATTLE_AND_YELLOW_DAUB_CROSS = registerMiscBlock("wattle_and_yellow_daub_cross",
            Block::new,AbstractBlock.Settings.copy(Blocks.PACKED_MUD),true);
    public static final Block WATTLE_AND_YELLOW_DAUB_RIGHT = registerMiscBlock("wattle_and_yellow_daub_right",
            Block::new,AbstractBlock.Settings.copy(Blocks.PACKED_MUD),true);
    public static final Block WATTLE_AND_YELLOW_DAUB_LEFT = registerMiscBlock("wattle_and_yellow_daub_left",
            Block::new,AbstractBlock.Settings.copy(Blocks.PACKED_MUD),true);
    public static final Block WATTLE_AND_YELLOW_DAUB_PILLAR = registerMiscBlock("wattle_and_yellow_daub_pillar",
            Block::new,AbstractBlock.Settings.copy(Blocks.PACKED_MUD),true);
    public static final Block WATTLE_AND_YELLOW_DAUB_DIAMOND = registerMiscBlock("wattle_and_yellow_daub_diamond",
            Block::new,AbstractBlock.Settings.copy(Blocks.PACKED_MUD),true);

    //region METAL AND GEMS
    public static final Block RAW_MITHRIL_BLOCK = registerMiscBlock("raw_mithril_block",
            Block::new,AbstractBlock.Settings.copy(Blocks.RAW_IRON_BLOCK).strength(4f).requiresTool(),true);
    public static final Block MITHRIL_BLOCK = registerMiscBlock("mithril_block",
            Block::new,AbstractBlock.Settings.copy(Blocks.IRON_BLOCK).strength(6f).requiresTool(),true);
    public static final Block RAW_TIN_BLOCK = registerMiscBlock("raw_tin_block",
            Block::new,AbstractBlock.Settings.copy(Blocks.STONE).strength(2f).requiresTool(),true);
    public static final Block TIN_BLOCK = registerMiscBlock("tin_block",
            Block::new,AbstractBlock.Settings.copy(Blocks.IRON_BLOCK).strength(3f).requiresTool(),true);
    public static final Block RAW_LEAD_BLOCK = registerMiscBlock("raw_lead_block",
            Block::new,AbstractBlock.Settings.copy(Blocks.RAW_IRON_BLOCK).strength(3f).requiresTool(),true);
    public static final Block LEAD_BLOCK = registerMiscBlock("lead_block",
            Block::new,AbstractBlock.Settings.copy(Blocks.IRON_BLOCK).strength(2f).requiresTool(),true);
    public static final Block RAW_SILVER_BLOCK = registerMiscBlock("raw_silver_block",
            Block::new,AbstractBlock.Settings.copy(Blocks.RAW_IRON_BLOCK).strength(3f).requiresTool(),true);
    public static final Block SILVER_BLOCK = registerMiscBlock("silver_block",
            Block::new,AbstractBlock.Settings.copy(Blocks.IRON_BLOCK).strength(4f).requiresTool(),true);

    public static final Block BRONZE_BLOCK = registerMiscBlock("bronze_block",
            Block::new,AbstractBlock.Settings.copy(Blocks.IRON_BLOCK).strength(5f).requiresTool(),true);
    public static final Block CRUDE_BLOCK = registerMiscBlock("crude_block",
            Block::new,AbstractBlock.Settings.copy(Blocks.IRON_BLOCK).strength(5.5f).requiresTool(),true);
    public static final Block STEEL_BLOCK = registerMiscBlock("steel_block",
            Block::new,AbstractBlock.Settings.copy(Blocks.IRON_BLOCK).strength(6f).requiresTool(),true);
    public static final Block KHAZAD_STEEL_BLOCK = registerMiscBlock("khazad_steel_block",
            Block::new,AbstractBlock.Settings.copy(Blocks.IRON_BLOCK).strength(7f).requiresTool(),true);
    public static final Block EDHEL_STEEL_BLOCK = registerMiscBlock("edhel_steel_block",
            Block::new,AbstractBlock.Settings.copy(Blocks.IRON_BLOCK).strength(6f).requiresTool(),true);
    public static final Block BURZUM_STEEL_BLOCK = registerMiscBlock("burzum_steel_block",
            Block::new,AbstractBlock.Settings.copy(Blocks.IRON_BLOCK).strength(6f).requiresTool(),true);

    public static final Block QUARTZ_BLOCK = registerMiscBlock("quartz_block",
            AmethystBlock::new,AbstractBlock.Settings.copy(Blocks.AMETHYST_BLOCK), true);
    public static final Block QUARTZ_CLUSTER = registerMiscBlock("quartz_cluster",
            (settings) -> new AmethystClusterBlock(7,3, settings), AbstractBlock.Settings.copy(Blocks.AMETHYST_CLUSTER), false);
    public static final Block LARGE_QUARTZ_BUD = registerMiscBlock("large_quartz_bud",
            (settings) -> new AmethystClusterBlock(5,3, settings), AbstractBlock.Settings.copy(ModBlocks.QUARTZ_CLUSTER).sounds(BlockSoundGroup.LARGE_AMETHYST_BUD), false);
    public static final Block MEDIUM_QUARTZ_BUD = registerMiscBlock("medium_quartz_bud",
            (settings) -> new AmethystClusterBlock(4,3, settings), AbstractBlock.Settings.copy(ModBlocks.QUARTZ_CLUSTER).sounds(BlockSoundGroup.MEDIUM_AMETHYST_BUD), false);
    public static final Block SMALL_QUARTZ_BUD = registerMiscBlock("small_quartz_bud",
            (settings) -> new AmethystClusterBlock(3,4, settings), AbstractBlock.Settings.copy(ModBlocks.QUARTZ_CLUSTER).sounds(BlockSoundGroup.SMALL_AMETHYST_BUD), false);
    public static final Block BUDDING_QUARTZ = registerMiscBlock("budding_quartz",
            (settings) -> new CustomBuddingGemBlock(settings, List.of(SMALL_QUARTZ_BUD,MEDIUM_QUARTZ_BUD,LARGE_QUARTZ_BUD, QUARTZ_CLUSTER)), AbstractBlock.Settings.copy(Blocks.BUDDING_AMETHYST), false);

    public static final Block RED_AGATE_BLOCK = registerMiscBlock("red_agate_block",
            AmethystBlock::new,AbstractBlock.Settings.copy(Blocks.AMETHYST_BLOCK), true);
    public static final Block RED_AGATE_CLUSTER = registerMiscBlock("red_agate_cluster",
            (settings) -> new AmethystClusterBlock(7,3, settings), AbstractBlock.Settings.copy(Blocks.AMETHYST_CLUSTER), false);
    public static final Block LARGE_RED_AGATE_BUD = registerMiscBlock("large_red_agate_bud",
            (settings) -> new AmethystClusterBlock(5,3, settings), AbstractBlock.Settings.copy(ModBlocks.RED_AGATE_CLUSTER).sounds(BlockSoundGroup.LARGE_AMETHYST_BUD), false);
    public static final Block MEDIUM_RED_AGATE_BUD = registerMiscBlock("medium_red_agate_bud",
            (settings) -> new AmethystClusterBlock(4,3, settings), AbstractBlock.Settings.copy(ModBlocks.RED_AGATE_CLUSTER).sounds(BlockSoundGroup.MEDIUM_AMETHYST_BUD), false);
    public static final Block SMALL_RED_AGATE_BUD = registerMiscBlock("small_red_agate_bud",
            (settings) -> new AmethystClusterBlock(3,4, settings), AbstractBlock.Settings.copy(ModBlocks.RED_AGATE_CLUSTER).sounds(BlockSoundGroup.SMALL_AMETHYST_BUD), false);
    public static final Block BUDDING_RED_AGATE = registerMiscBlock("budding_red_agate",
            (settings) -> new CustomBuddingGemBlock(settings, List.of(SMALL_RED_AGATE_BUD,MEDIUM_RED_AGATE_BUD,LARGE_RED_AGATE_BUD, RED_AGATE_CLUSTER)),AbstractBlock.Settings.copy(Blocks.BUDDING_AMETHYST),  false);

    public static final Block CITRINE_BLOCK = registerMiscBlock("citrine_block",
            AmethystBlock::new,AbstractBlock.Settings.copy(Blocks.AMETHYST_BLOCK), true);
    public static final Block CITRINE_CLUSTER = registerMiscBlock("citrine_cluster",
            (settings) -> new AmethystClusterBlock(7,3, settings), AbstractBlock.Settings.copy(Blocks.AMETHYST_CLUSTER), false);
    public static final Block LARGE_CITRINE_BUD = registerMiscBlock("large_citrine_bud",
            (settings) -> new AmethystClusterBlock(5,3, settings), AbstractBlock.Settings.copy(ModBlocks.CITRINE_CLUSTER).sounds(BlockSoundGroup.LARGE_AMETHYST_BUD), false);
    public static final Block MEDIUM_CITRINE_BUD = registerMiscBlock("medium_citrine_bud",
            (settings) -> new AmethystClusterBlock(4,3, settings), AbstractBlock.Settings.copy(ModBlocks.CITRINE_CLUSTER).sounds(BlockSoundGroup.MEDIUM_AMETHYST_BUD), false);
    public static final Block SMALL_CITRINE_BUD = registerMiscBlock("small_citrine_bud",
            (settings) -> new AmethystClusterBlock(3,4, settings), AbstractBlock.Settings.copy(ModBlocks.CITRINE_CLUSTER).sounds(BlockSoundGroup.SMALL_AMETHYST_BUD), false);
    public static final Block BUDDING_CITRINE = registerMiscBlock("budding_citrine",
            (settings) -> new CustomBuddingGemBlock(settings, List.of(SMALL_CITRINE_BUD,MEDIUM_CITRINE_BUD,LARGE_CITRINE_BUD, CITRINE_CLUSTER)), AbstractBlock.Settings.copy(Blocks.BUDDING_AMETHYST),false);

    public static final Block GLOWSTONE_BLOCK = registerMiscBlock("glowstone_block",
            AmethystBlock::new,AbstractBlock.Settings.copy(Blocks.AMETHYST_BLOCK).luminance(state -> 12), true);
    public static final Block GLOWSTONE_CLUSTER = registerMiscBlock("glowstone_cluster",
            (settings) -> new AmethystClusterBlock(7,3, settings), AbstractBlock.Settings.copy(Blocks.AMETHYST_CLUSTER).luminance(state -> 10), false);
    public static final Block LARGE_GLOWSTONE_BUD = registerMiscBlock("large_glowstone_bud",
            (settings) -> new AmethystClusterBlock(5,3, settings), AbstractBlock.Settings.copy(ModBlocks.GLOWSTONE_CLUSTER).sounds(BlockSoundGroup.LARGE_AMETHYST_BUD).luminance(state -> 9), false);
    public static final Block MEDIUM_GLOWSTONE_BUD = registerMiscBlock("medium_glowstone_bud",
            (settings) -> new AmethystClusterBlock(4,3, settings), AbstractBlock.Settings.copy(ModBlocks.GLOWSTONE_CLUSTER).sounds(BlockSoundGroup.MEDIUM_AMETHYST_BUD).luminance(state -> 7), false);
    public static final Block SMALL_GLOWSTONE_BUD = registerMiscBlock("small_glowstone_bud",
            (settings) -> new AmethystClusterBlock(3,4, settings), AbstractBlock.Settings.copy(ModBlocks.GLOWSTONE_CLUSTER).sounds(BlockSoundGroup.SMALL_AMETHYST_BUD).luminance(state -> 5), false);
    public static final Block BUDDING_GLOWSTONE = registerMiscBlock("budding_glowstone",
            (settings) -> new CustomBuddingGemBlock(settings, List.of(SMALL_GLOWSTONE_BUD,MEDIUM_GLOWSTONE_BUD,LARGE_GLOWSTONE_BUD, GLOWSTONE_CLUSTER)), AbstractBlock.Settings.copy(Blocks.BUDDING_AMETHYST),false);

    public static final Block NET = registerMiscBlock("net",
            PaneBlock::new, AbstractBlock.Settings.copy(Blocks.WHITE_WOOL).sounds(BlockSoundGroup.WOOL), true);

    public static final Block COPPER_BARS = registerMiscBlock("copper_bars",
            (settings) -> new OxidizablePaneBlock(Oxidizable.OxidationLevel.UNAFFECTED, settings), AbstractBlock.Settings.copy(Blocks.CUT_COPPER_SLAB), true);
    public static final Block EXPOSED_COPPER_BARS = registerMiscBlock("exposed_copper_bars",
            (settings) -> new OxidizablePaneBlock(Oxidizable.OxidationLevel.EXPOSED, settings), AbstractBlock.Settings.copy(Blocks.EXPOSED_CUT_COPPER_SLAB), true);
    public static final Block WEATHERED_COPPER_BARS = registerMiscBlock("weathered_copper_bars",
            (settings) -> new OxidizablePaneBlock(Oxidizable.OxidationLevel.WEATHERED, settings), AbstractBlock.Settings.copy(Blocks.WEATHERED_CUT_COPPER_SLAB), true);
    public static final Block OXIDIZED_COPPER_BARS = registerMiscBlock("oxidized_copper_bars",
            (settings) -> new OxidizablePaneBlock(Oxidizable.OxidationLevel.OXIDIZED, settings), AbstractBlock.Settings.copy(Blocks.OXIDIZED_CUT_COPPER_SLAB), true);
    public static final Block WAXED_COPPER_BARS = registerMiscBlock("waxed_copper_bars",
            PaneBlock::new, AbstractBlock.Settings.copy(Blocks.WAXED_CUT_COPPER_SLAB), true);
    public static final Block WAXED_EXPOSED_COPPER_BARS = registerMiscBlock("waxed_exposed_copper_bars",
            PaneBlock::new, AbstractBlock.Settings.copy(Blocks.WAXED_EXPOSED_CUT_COPPER_SLAB), true);
    public static final Block WAXED_WEATHERED_COPPER_BARS = registerMiscBlock("waxed_weathered_copper_bars",
            PaneBlock::new, AbstractBlock.Settings.copy(Blocks.WAXED_WEATHERED_CUT_COPPER_SLAB), true);
    public static final Block WAXED_OXIDIZED_COPPER_BARS = registerMiscBlock("waxed_oxidized_copper_bars",
            PaneBlock::new, AbstractBlock.Settings.copy(Blocks.WAXED_OXIDIZED_CUT_COPPER_SLAB), true);

    public static final Block BRONZE_BARS = registerMiscBlock("bronze_bars",
            PaneBlock::new, AbstractBlock.Settings.copy(Blocks.IRON_BARS), true);
    public static final Block CRUDE_BARS = registerMiscBlock("crude_bars",
            PaneBlock::new, AbstractBlock.Settings.copy(Blocks.IRON_BARS), true);
    public static final Block TREATED_STEEL_BARS = registerMiscBlock("treated_steel_bars",
            PaneBlock::new, AbstractBlock.Settings.copy(Blocks.IRON_BARS), true);
    public static final Block SILVER_BARS = registerMiscBlock("silver_bars",
            PaneBlock::new, AbstractBlock.Settings.copy(Blocks.IRON_BARS).sounds(BlockSoundGroup.METAL), true);
    public static final Block GILDED_BARS = registerMiscBlock("gilded_bars",
            PaneBlock::new, AbstractBlock.Settings.copy(Blocks.IRON_BARS).sounds(BlockSoundGroup.COPPER), true);

    public static final Block BRONZE_DOOR = registerMiscBlock("bronze_door",
            (settings) -> new DoorBlock(BlockSetType.COPPER, settings), AbstractBlock.Settings.copy(Blocks.IRON_DOOR), true);
    public static final Block CRUDE_DOOR = registerMiscBlock("crude_door",
            (settings) -> new DoorBlock(BlockSetType.COPPER, settings), AbstractBlock.Settings.copy(Blocks.IRON_DOOR), true);
    public static final Block TREATED_STEEL_DOOR = registerMiscBlock("treated_steel_door",
            (settings) -> new DoorBlock(BlockSetType.COPPER, settings), AbstractBlock.Settings.copy(Blocks.IRON_DOOR), true);
    public static final Block BRONZE_TRAPDOOR = registerMiscBlock("bronze_trapdoor",
            (settings) -> new TrapdoorBlock(BlockSetType.COPPER, settings), AbstractBlock.Settings.copy(Blocks.IRON_TRAPDOOR), true);
    public static final Block CRUDE_TRAPDOOR = registerMiscBlock("crude_trapdoor",
            (settings) -> new TrapdoorBlock(BlockSetType.COPPER, settings), AbstractBlock.Settings.copy(Blocks.IRON_TRAPDOOR), true);
    public static final Block TREATED_STEEL_TRAPDOOR = registerMiscBlock("treated_steel_trapdoor",
            (settings) -> new TrapdoorBlock(BlockSetType.COPPER, settings), AbstractBlock.Settings.copy(Blocks.IRON_TRAPDOOR), true);
    //endregion

    public static final Block RIVER_SAND = registerMiscBlock("river_sand",
            (settings) -> new ColoredFallingBlock(new ColorCode(-8356741), settings), AbstractBlock.Settings.copy(Blocks.SAND).sounds(BlockSoundGroup.SAND), true);
    public static final Block BLACK_SAND = registerMiscBlock("black_sand",
            (settings) -> new ColoredFallingBlock(new ColorCode(-8356741), settings), AbstractBlock.Settings.copy(Blocks.SAND).sounds(BlockSoundGroup.SAND), true);
    public static final Block WHITE_SAND = registerMiscBlock("white_sand",
            (settings) -> new ColoredFallingBlock(new ColorCode(14406560), settings), AbstractBlock.Settings.copy(Blocks.SAND).sounds(BlockSoundGroup.SAND), true);

    public static final Block STONE_MYCELIUM = registerMiscBlock("stone_mycelium",
            StoneMyceliumBlock::new, AbstractBlock.Settings.copy(Blocks.STONE).ticksRandomly(), false);

    public static final Block ASH_BLOCK = registerMiscBlock("ash_block",
            (settings) -> new ColoredFallingBlock(new ColorCode(-8356741), settings), AbstractBlock.Settings.copy(Blocks.SAND).strength(DIRT_STRENGTH).sounds(BlockSoundGroup.SAND), true);

    //region VANILLA SLABS
    public static final Block DIRT_SLAB = registerMiscBlock("dirt_slab",
            SlabBlock::new,AbstractBlock.Settings.copy(Blocks.DIRT), true);
    public static final Block COARSE_DIRT_SLAB = registerMiscBlock("coarse_dirt_slab",
            SlabBlock::new,AbstractBlock.Settings.copy(Blocks.COARSE_DIRT), true);
    public static final Block ROOTED_DIRT_SLAB = registerMiscBlock("rooted_dirt_slab",
            SlabBlock::new,AbstractBlock.Settings.copy(Blocks.ROOTED_DIRT), true);
    public static final Block MUD_SLAB = registerMiscBlock("mud_slab",
            SlabBlock::new,AbstractBlock.Settings.copy(Blocks.MUD), true);
    public static final Block MOSS_SLAB = registerMiscBlock("moss_slab",
            SlabBlock::new,AbstractBlock.Settings.copy(Blocks.MOSS_BLOCK), true);

    public static final Block PACKED_MUD_SLAB = registerStoneBlock("packed_mud_slab",
            SlabBlock::new,AbstractBlock.Settings.copy(Blocks.PACKED_MUD), true);
    public static final Block PACKED_MUD_VERTICAL_SLAB = registerStoneBlock("packed_mud_vertical_slab",
            VerticalSlabBlock::new, AbstractBlock.Settings.copy(Blocks.PACKED_MUD), true);
    public static final Block PACKED_MUD_STAIRS = registerStoneBlock("packed_mud_stairs",
            (settings) -> new StairsBlock(Blocks.PACKED_MUD.getDefaultState(), settings), AbstractBlock.Settings.copy(Blocks.PACKED_MUD), true);
    public static final Block PACKED_MUD_WALL = registerStoneBlock("packed_mud_wall",
            WallBlock::new,AbstractBlock.Settings.copy(Blocks.PACKED_MUD), true);

    public static final Block CUT_COPPER_WALL = registerMiscBlock("cut_copper_wall",
            (settings) -> new OxidizableWallBlock(Oxidizable.OxidationLevel.UNAFFECTED, settings), AbstractBlock.Settings.copy(Blocks.CUT_COPPER_SLAB), true);
    public static final Block EXPOSED_CUT_COPPER_WALL = registerMiscBlock("exposed_cut_copper_wall",
            (settings) -> new OxidizableWallBlock(Oxidizable.OxidationLevel.EXPOSED, settings), AbstractBlock.Settings.copy(Blocks.EXPOSED_CUT_COPPER_SLAB), true);
    public static final Block WEATHERED_CUT_COPPER_WALL = registerMiscBlock("weathered_cut_copper_wall",
            (settings) -> new OxidizableWallBlock(Oxidizable.OxidationLevel.WEATHERED, settings), AbstractBlock.Settings.copy(Blocks.WEATHERED_CUT_COPPER_SLAB), true);
    public static final Block OXIDIZED_CUT_COPPER_WALL = registerMiscBlock("oxidized_cut_copper_wall",
            (settings) -> new OxidizableWallBlock(Oxidizable.OxidationLevel.OXIDIZED, settings), AbstractBlock.Settings.copy(Blocks.OXIDIZED_CUT_COPPER_SLAB), true);
    public static final Block WAXED_CUT_COPPER_WALL = registerMiscBlock("waxed_cut_copper_wall",
            WallBlock::new,AbstractBlock.Settings.copy(Blocks.WAXED_CUT_COPPER_SLAB), true);
    public static final Block WAXED_EXPOSED_CUT_COPPER_WALL = registerMiscBlock("waxed_exposed_cut_copper_wall",
            WallBlock::new,AbstractBlock.Settings.copy(Blocks.WAXED_EXPOSED_CUT_COPPER_SLAB), true);
    public static final Block WAXED_WEATHERED_CUT_COPPER_WALL = registerMiscBlock("waxed_weathered_cut_copper_wall",
            WallBlock::new,AbstractBlock.Settings.copy(Blocks.WAXED_WEATHERED_CUT_COPPER_SLAB), true);
    public static final Block WAXED_OXIDIZED_CUT_COPPER_WALL = registerMiscBlock("waxed_oxidized_cut_copper_wall",
            WallBlock::new,AbstractBlock.Settings.copy(Blocks.WAXED_OXIDIZED_CUT_COPPER_SLAB), true);

    public static final Block BLACK_WOOL_SLAB = registerMiscBlock("black_wool_slab",
            SlabBlock::new,AbstractBlock.Settings.copy(Blocks.WHITE_WOOL).burnable(), true);
    public static final Block BLUE_WOOL_SLAB = registerMiscBlock("blue_wool_slab",
            SlabBlock::new,AbstractBlock.Settings.copy(Blocks.WHITE_WOOL).burnable(), true);
    public static final Block BROWN_WOOL_SLAB = registerMiscBlock("brown_wool_slab",
            SlabBlock::new,AbstractBlock.Settings.copy(Blocks.WHITE_WOOL).burnable(), true);
    public static final Block CYAN_WOOL_SLAB = registerMiscBlock("cyan_wool_slab",
            SlabBlock::new,AbstractBlock.Settings.copy(Blocks.WHITE_WOOL).burnable(), true);
    public static final Block GRAY_WOOL_SLAB = registerMiscBlock("gray_wool_slab",
            SlabBlock::new,AbstractBlock.Settings.copy(Blocks.WHITE_WOOL).burnable(), true);
    public static final Block GREEN_WOOL_SLAB = registerMiscBlock("green_wool_slab",
            SlabBlock::new,AbstractBlock.Settings.copy(Blocks.WHITE_WOOL).burnable(), true);
    public static final Block LIGHT_BLUE_WOOL_SLAB = registerMiscBlock("light_blue_wool_slab",
            SlabBlock::new,AbstractBlock.Settings.copy(Blocks.WHITE_WOOL).burnable(), true);
    public static final Block LIGHT_GRAY_WOOL_SLAB = registerMiscBlock("light_gray_wool_slab",
            SlabBlock::new,AbstractBlock.Settings.copy(Blocks.WHITE_WOOL).burnable(), true);
    public static final Block LIME_WOOL_SLAB = registerMiscBlock("lime_wool_slab",
            SlabBlock::new,AbstractBlock.Settings.copy(Blocks.WHITE_WOOL).burnable(), true);
    public static final Block MAGENTA_WOOL_SLAB = registerMiscBlock("magenta_wool_slab",
            SlabBlock::new,AbstractBlock.Settings.copy(Blocks.WHITE_WOOL).burnable(), true);
    public static final Block ORANGE_WOOL_SLAB = registerMiscBlock("orange_wool_slab",
            SlabBlock::new,AbstractBlock.Settings.copy(Blocks.WHITE_WOOL).burnable(), true);
    public static final Block PINK_WOOL_SLAB = registerMiscBlock("pink_wool_slab",
            SlabBlock::new,AbstractBlock.Settings.copy(Blocks.WHITE_WOOL).burnable(), true);
    public static final Block PURPLE_WOOL_SLAB = registerMiscBlock("purple_wool_slab",
            SlabBlock::new,AbstractBlock.Settings.copy(Blocks.WHITE_WOOL).burnable(), true);
    public static final Block RED_WOOL_SLAB = registerMiscBlock("red_wool_slab",
            SlabBlock::new,AbstractBlock.Settings.copy(Blocks.WHITE_WOOL).burnable(), true);
    public static final Block WHITE_WOOL_SLAB = registerMiscBlock("white_wool_slab",
            SlabBlock::new,AbstractBlock.Settings.copy(Blocks.WHITE_WOOL).burnable(), true);
    public static final Block YELLOW_WOOL_SLAB = registerMiscBlock("yellow_wool_slab",
            SlabBlock::new,AbstractBlock.Settings.copy(Blocks.WHITE_WOOL).burnable(), true);
    //endregion

    //region VANILLA VERTICAL SLABS
    public static final Block BRICK_VERTICAL_SLAB = registerStoneBlock("brick_vertical_slab",
            VerticalSlabBlock::new, AbstractBlock.Settings.copy(Blocks.BRICK_SLAB), true);
    public static final Block MUD_BRICK_VERTICAL_SLAB = registerStoneBlock("mud_brick_vertical_slab",
            VerticalSlabBlock::new, AbstractBlock.Settings.copy(Blocks.MUD_BRICK_SLAB), true);
    public static final Block SANDSTONE_VERTICAL_SLAB = registerStoneBlock("sandstone_vertical_slab",
            VerticalSlabBlock::new, AbstractBlock.Settings.copy(Blocks.SANDSTONE_SLAB), true);
    public static final Block SMOOTH_SANDSTONE_VERTICAL_SLAB = registerStoneBlock("smooth_sandstone_vertical_slab",
            VerticalSlabBlock::new, AbstractBlock.Settings.copy(Blocks.SMOOTH_SANDSTONE_SLAB), true);
    public static final Block CUT_SANDSTONE_VERTICAL_SLAB = registerStoneBlock("cut_sandstone_vertical_slab",
            VerticalSlabBlock::new, AbstractBlock.Settings.copy(Blocks.CUT_SANDSTONE_SLAB), true);
    public static final Block RED_SANDSTONE_VERTICAL_SLAB = registerStoneBlock("red_sandstone_vertical_slab",
            VerticalSlabBlock::new, AbstractBlock.Settings.copy(Blocks.RED_SANDSTONE_SLAB), true);
    public static final Block SMOOTH_RED_SANDSTONE_VERTICAL_SLAB = registerStoneBlock("smooth_red_sandstone_vertical_slab",
            VerticalSlabBlock::new, AbstractBlock.Settings.copy(Blocks.SMOOTH_RED_SANDSTONE_SLAB), true);
    public static final Block CUT_RED_SANDSTONE_VERTICAL_SLAB = registerStoneBlock("cut_red_sandstone_vertical_slab",
            VerticalSlabBlock::new, AbstractBlock.Settings.copy(Blocks.CUT_RED_SANDSTONE_SLAB), true);
    public static final Block PRISMARINE_VERTICAL_SLAB = registerStoneBlock("prismarine_vertical_slab",
            VerticalSlabBlock::new, AbstractBlock.Settings.copy(Blocks.PRISMARINE_SLAB), true);
    public static final Block PRISMARINE_BRICK_VERTICAL_SLAB = registerStoneBlock("prismarine_brick_vertical_slab",
            VerticalSlabBlock::new, AbstractBlock.Settings.copy(Blocks.PRISMARINE_BRICK_SLAB), true);
    public static final Block DARK_PRISMARINE_VERTICAL_SLAB = registerStoneBlock("dark_prismarine_vertical_slab",
            VerticalSlabBlock::new, AbstractBlock.Settings.copy(Blocks.DARK_PRISMARINE_SLAB), true);
    public static final Block NETHER_BRICK_VERTICAL_SLAB = registerStoneBlock("nether_brick_vertical_slab",
            VerticalSlabBlock::new, AbstractBlock.Settings.copy(Blocks.NETHER_BRICK_SLAB), true);
    public static final Block RED_NETHER_BRICK_VERTICAL_SLAB = registerStoneBlock("red_nether_brick_vertical_slab",
            VerticalSlabBlock::new, AbstractBlock.Settings.copy(Blocks.RED_NETHER_BRICK_SLAB), true);
    public static final Block END_STONE_BRICK_VERTICAL_SLAB = registerStoneBlock("end_stone_brick_vertical_slab",
            VerticalSlabBlock::new, AbstractBlock.Settings.copy(Blocks.END_STONE_BRICK_SLAB), true);
    public static final Block PURPUR_VERTICAL_SLAB = registerStoneBlock("purpur_vertical_slab",
            VerticalSlabBlock::new, AbstractBlock.Settings.copy(Blocks.PURPUR_SLAB), true);
    public static final Block QUARTZ_VERTICAL_SLAB = registerStoneBlock("quartz_vertical_slab",
            VerticalSlabBlock::new, AbstractBlock.Settings.copy(Blocks.QUARTZ_SLAB), true);
    public static final Block SMOOTH_QUARTZ_VERTICAL_SLAB = registerStoneBlock("smooth_quartz_vertical_slab",
            VerticalSlabBlock::new, AbstractBlock.Settings.copy(Blocks.SMOOTH_QUARTZ_SLAB), true);
    public static final Block CUT_COPPER_VERTICAL_SLAB = registerMiscBlock("cut_copper_vertical_slab",
            (settings) -> new OxidizableVerticalSlabBlock(Oxidizable.OxidationLevel.UNAFFECTED, settings), AbstractBlock.Settings.copy(Blocks.CUT_COPPER_SLAB), true);
    public static final Block EXPOSED_CUT_COPPER_VERTICAL_SLAB = registerMiscBlock("exposed_cut_copper_vertical_slab",
            (settings) -> new OxidizableVerticalSlabBlock(Oxidizable.OxidationLevel.EXPOSED, settings), AbstractBlock.Settings.copy(Blocks.EXPOSED_CUT_COPPER_SLAB), true);
    public static final Block WEATHERED_CUT_COPPER_VERTICAL_SLAB = registerMiscBlock("weathered_cut_copper_vertical_slab",
            (settings) -> new OxidizableVerticalSlabBlock(Oxidizable.OxidationLevel.WEATHERED, settings), AbstractBlock.Settings.copy(Blocks.WEATHERED_CUT_COPPER_SLAB), true);
    public static final Block OXIDIZED_CUT_COPPER_VERTICAL_SLAB = registerMiscBlock("oxidized_cut_copper_vertical_slab",
            (settings) -> new OxidizableVerticalSlabBlock(Oxidizable.OxidationLevel.OXIDIZED, settings), AbstractBlock.Settings.copy(Blocks.OXIDIZED_CUT_COPPER_SLAB), true);
    public static final Block WAXED_CUT_COPPER_VERTICAL_SLAB = registerMiscBlock("waxed_cut_copper_vertical_slab",
            VerticalSlabBlock::new, AbstractBlock.Settings.copy(Blocks.WAXED_CUT_COPPER_SLAB), true);
    public static final Block WAXED_EXPOSED_CUT_COPPER_VERTICAL_SLAB = registerMiscBlock("waxed_exposed_cut_copper_vertical_slab",
            VerticalSlabBlock::new, AbstractBlock.Settings.copy(Blocks.WAXED_EXPOSED_CUT_COPPER_SLAB), true);
    public static final Block WAXED_WEATHERED_CUT_COPPER_VERTICAL_SLAB = registerMiscBlock("waxed_weathered_cut_copper_vertical_slab",
            VerticalSlabBlock::new, AbstractBlock.Settings.copy(Blocks.WAXED_WEATHERED_CUT_COPPER_SLAB), true);
    public static final Block WAXED_OXIDIZED_CUT_COPPER_VERTICAL_SLAB = registerMiscBlock("waxed_oxidized_cut_copper_vertical_slab",
            VerticalSlabBlock::new, AbstractBlock.Settings.copy(Blocks.WAXED_OXIDIZED_CUT_COPPER_SLAB), true);

    public static final Block BLACK_WOOL_VERTICAL_SLAB = registerMiscBlock("black_wool_vertical_slab",
            VerticalSlabBlock::new, AbstractBlock.Settings.copy(Blocks.WHITE_WOOL).burnable(), true);
    public static final Block BLUE_WOOL_VERTICAL_SLAB = registerMiscBlock("blue_wool_vertical_slab",
            VerticalSlabBlock::new, AbstractBlock.Settings.copy(Blocks.WHITE_WOOL).burnable(), true);
    public static final Block BROWN_WOOL_VERTICAL_SLAB = registerMiscBlock("brown_wool_vertical_slab",
            VerticalSlabBlock::new, AbstractBlock.Settings.copy(Blocks.WHITE_WOOL).burnable(), true);
    public static final Block CYAN_WOOL_VERTICAL_SLAB = registerMiscBlock("cyan_wool_vertical_slab",
            VerticalSlabBlock::new, AbstractBlock.Settings.copy(Blocks.WHITE_WOOL).burnable(), true);
    public static final Block GRAY_WOOL_VERTICAL_SLAB = registerMiscBlock("gray_wool_vertical_slab",
            VerticalSlabBlock::new, AbstractBlock.Settings.copy(Blocks.WHITE_WOOL).burnable(), true);
    public static final Block GREEN_WOOL_VERTICAL_SLAB = registerMiscBlock("green_wool_vertical_slab",
            VerticalSlabBlock::new, AbstractBlock.Settings.copy(Blocks.WHITE_WOOL).burnable(), true);
    public static final Block LIGHT_BLUE_WOOL_VERTICAL_SLAB = registerMiscBlock("light_blue_wool_vertical_slab",
            VerticalSlabBlock::new, AbstractBlock.Settings.copy(Blocks.WHITE_WOOL).burnable(), true);
    public static final Block LIGHT_GRAY_WOOL_VERTICAL_SLAB = registerMiscBlock("light_gray_wool_vertical_slab",
            VerticalSlabBlock::new, AbstractBlock.Settings.copy(Blocks.WHITE_WOOL).burnable(), true);
    public static final Block LIME_WOOL_VERTICAL_SLAB = registerMiscBlock("lime_wool_vertical_slab",
            VerticalSlabBlock::new, AbstractBlock.Settings.copy(Blocks.WHITE_WOOL).burnable(), true);
    public static final Block MAGENTA_WOOL_VERTICAL_SLAB = registerMiscBlock("magenta_wool_vertical_slab",
            VerticalSlabBlock::new, AbstractBlock.Settings.copy(Blocks.WHITE_WOOL).burnable(), true);
    public static final Block ORANGE_WOOL_VERTICAL_SLAB = registerMiscBlock("orange_wool_vertical_slab",
            VerticalSlabBlock::new, AbstractBlock.Settings.copy(Blocks.WHITE_WOOL).burnable(), true);
    public static final Block PINK_WOOL_VERTICAL_SLAB = registerMiscBlock("pink_wool_vertical_slab",
            VerticalSlabBlock::new, AbstractBlock.Settings.copy(Blocks.WHITE_WOOL).burnable(), true);
    public static final Block PURPLE_WOOL_VERTICAL_SLAB = registerMiscBlock("purple_wool_vertical_slab",
            VerticalSlabBlock::new, AbstractBlock.Settings.copy(Blocks.WHITE_WOOL).burnable(), true);
    public static final Block RED_WOOL_VERTICAL_SLAB = registerMiscBlock("red_wool_vertical_slab",
            VerticalSlabBlock::new, AbstractBlock.Settings.copy(Blocks.WHITE_WOOL).burnable(), true);
    public static final Block WHITE_WOOL_VERTICAL_SLAB = registerMiscBlock("white_wool_vertical_slab",
            VerticalSlabBlock::new, AbstractBlock.Settings.copy(Blocks.WHITE_WOOL).burnable(), true);
    public static final Block YELLOW_WOOL_VERTICAL_SLAB = registerMiscBlock("yellow_wool_vertical_slab",
            VerticalSlabBlock::new, AbstractBlock.Settings.copy(Blocks.WHITE_WOOL).burnable(), true);
    //endregion

    //region VANILLA STAIRS
    public static final Block DIRT_STAIRS = registerMiscBlock("dirt_stairs",
            (settings) -> new StairsBlock(Blocks.DIRT.getDefaultState(), settings), AbstractBlock.Settings.copy(Blocks.DIRT), true);
    public static final Block COARSE_DIRT_STAIRS = registerMiscBlock("coarse_dirt_stairs",
            (settings) -> new StairsBlock(Blocks.COARSE_DIRT.getDefaultState(), settings), AbstractBlock.Settings.copy(Blocks.COARSE_DIRT), true);
    public static final Block ROOTED_DIRT_STAIRS = registerMiscBlock("rooted_dirt_stairs",
            (settings) -> new StairsBlock(Blocks.ROOTED_DIRT.getDefaultState(), settings), AbstractBlock.Settings.copy(Blocks.ROOTED_DIRT), true);
    public static final Block MUD_STAIRS = registerMiscBlock("mud_stairs",
            (settings) -> new StairsBlock(Blocks.MUD.getDefaultState(), settings), AbstractBlock.Settings.copy(Blocks.DIRT).sounds(BlockSoundGroup.MUD), true);
    public static final Block MOSS_STAIRS = registerMiscBlock("moss_stairs",
            (settings) -> new StairsBlock(Blocks.MOSS_BLOCK.getDefaultState(), settings), AbstractBlock.Settings.copy(Blocks.MOSS_BLOCK), true);

    public static final Block BLACK_WOOL_STAIRS = registerMiscBlock("black_wool_stairs",
            (settings) -> new StairsBlock(Blocks.BLACK_WOOL.getDefaultState(), settings), AbstractBlock.Settings.copy(Blocks.WHITE_WOOL).burnable(), true);
    public static final Block BLUE_WOOL_STAIRS = registerMiscBlock("blue_wool_stairs",
            (settings) -> new StairsBlock(Blocks.BLACK_WOOL.getDefaultState(), settings), AbstractBlock.Settings.copy(Blocks.WHITE_WOOL).burnable(), true);
    public static final Block BROWN_WOOL_STAIRS = registerMiscBlock("brown_wool_stairs",
            (settings) -> new StairsBlock(Blocks.BLACK_WOOL.getDefaultState(), settings), AbstractBlock.Settings.copy(Blocks.WHITE_WOOL).burnable(), true);
    public static final Block CYAN_WOOL_STAIRS = registerMiscBlock("cyan_wool_stairs",
            (settings) -> new StairsBlock(Blocks.BLACK_WOOL.getDefaultState(), settings), AbstractBlock.Settings.copy(Blocks.WHITE_WOOL).burnable(), true);
    public static final Block GRAY_WOOL_STAIRS = registerMiscBlock("gray_wool_stairs",
            (settings) -> new StairsBlock(Blocks.BLACK_WOOL.getDefaultState(), settings), AbstractBlock.Settings.copy(Blocks.WHITE_WOOL).burnable(), true);
    public static final Block GREEN_WOOL_STAIRS = registerMiscBlock("green_wool_stairs",
            (settings) -> new StairsBlock(Blocks.BLACK_WOOL.getDefaultState(), settings), AbstractBlock.Settings.copy(Blocks.WHITE_WOOL).burnable(), true);
    public static final Block LIGHT_BLUE_WOOL_STAIRS = registerMiscBlock("light_blue_wool_stairs",
            (settings) -> new StairsBlock(Blocks.BLACK_WOOL.getDefaultState(), settings), AbstractBlock.Settings.copy(Blocks.WHITE_WOOL).burnable(), true);
    public static final Block LIGHT_GRAY_WOOL_STAIRS = registerMiscBlock("light_gray_wool_stairs",
            (settings) -> new StairsBlock(Blocks.BLACK_WOOL.getDefaultState(), settings), AbstractBlock.Settings.copy(Blocks.WHITE_WOOL).burnable(), true);
    public static final Block LIME_WOOL_STAIRS = registerMiscBlock("lime_wool_stairs",
            (settings) -> new StairsBlock(Blocks.BLACK_WOOL.getDefaultState(), settings), AbstractBlock.Settings.copy(Blocks.WHITE_WOOL).burnable(), true);
    public static final Block MAGENTA_WOOL_STAIRS = registerMiscBlock("magenta_wool_stairs",
            (settings) -> new StairsBlock(Blocks.BLACK_WOOL.getDefaultState(), settings), AbstractBlock.Settings.copy(Blocks.WHITE_WOOL).burnable(), true);
    public static final Block ORANGE_WOOL_STAIRS = registerMiscBlock("orange_wool_stairs",
            (settings) -> new StairsBlock(Blocks.BLACK_WOOL.getDefaultState(), settings), AbstractBlock.Settings.copy(Blocks.WHITE_WOOL).burnable(), true);
    public static final Block PINK_WOOL_STAIRS = registerMiscBlock("pink_wool_stairs",
            (settings) -> new StairsBlock(Blocks.BLACK_WOOL.getDefaultState(), settings), AbstractBlock.Settings.copy(Blocks.WHITE_WOOL).burnable(), true);
    public static final Block PURPLE_WOOL_STAIRS = registerMiscBlock("purple_wool_stairs",
            (settings) -> new StairsBlock(Blocks.BLACK_WOOL.getDefaultState(), settings), AbstractBlock.Settings.copy(Blocks.WHITE_WOOL).burnable(), true);
    public static final Block RED_WOOL_STAIRS = registerMiscBlock("red_wool_stairs",
            (settings) -> new StairsBlock(Blocks.BLACK_WOOL.getDefaultState(), settings), AbstractBlock.Settings.copy(Blocks.WHITE_WOOL).burnable(), true);
    public static final Block WHITE_WOOL_STAIRS = registerMiscBlock("white_wool_stairs",
            (settings) -> new StairsBlock(Blocks.BLACK_WOOL.getDefaultState(), settings), AbstractBlock.Settings.copy(Blocks.WHITE_WOOL).burnable(), true);
    public static final Block YELLOW_WOOL_STAIRS = registerMiscBlock("yellow_wool_stairs",
            (settings) -> new StairsBlock(Blocks.BLACK_WOOL.getDefaultState(), settings), AbstractBlock.Settings.copy(Blocks.WHITE_WOOL).burnable(), true);
    //endregion

    //region VANILLA LAYERS
    public static final Block GRAVEL_LAYER = registerMiscBlock("gravel_layer",
            (settings) -> new LayersBlock(settings, Blocks.GRAVEL), AbstractBlock.Settings.copy(Blocks.GRAVEL), false);
    public static final Block SAND_LAYER = registerMiscBlock("sand_layer",
            (settings) -> new LayersBlock(settings, Blocks.SAND), AbstractBlock.Settings.copy(Blocks.SAND), false);
    public static final Block BLACK_SAND_LAYER = registerMiscBlock("black_sand_layer",
            (settings) -> new LayersBlock(settings, BLACK_SAND), AbstractBlock.Settings.copy(Blocks.SAND), false);
    public static final Block WHITE_SAND_LAYER = registerMiscBlock("white_sand_layer",
            (settings) -> new LayersBlock(settings, WHITE_SAND), AbstractBlock.Settings.copy(Blocks.SAND), false);
    //endregion

    public static Block registerBlock(String name, Function<AbstractBlock.Settings, Block> factory, AbstractBlock.Settings settings, boolean drop, List<ItemStack> group){
        Block block = (Block)factory.apply(settings.registryKey(keyOfBlock(name)));
        registerBlockItem(name, block);
        if(drop){
            BlockDrops.blocks.add(block);
        }
        group.add(block.asItem().getDefaultStack());
        TranslationEntries.blockEntries.add(block);
        RegistryAliases.aliases.add(new RegistryAliases.Alias(Registries.BLOCK, name));
        return Registry.register(Registries.BLOCK, keyOfBlock(name), block);
    }

    public static Block registerStoneBlock(String name, Function<AbstractBlock.Settings, Block> factory, AbstractBlock.Settings settings, boolean drop) {
        return registerBlock(name, factory, settings, drop, ModItemGroups.STONE_BLOCKS_CONTENTS);
    }

    public static Block registerWoodBlock(String name, Function<AbstractBlock.Settings, Block> factory, AbstractBlock.Settings settings, boolean drop) {
        return registerBlock(name, factory, settings, drop, ModItemGroups.WOOD_BLOCKS_CONTENTS);
    }

    public static Block registerMiscBlock(String name, Function<AbstractBlock.Settings, Block> factory, AbstractBlock.Settings settings, boolean drop) {
        return registerBlock(name, factory, settings, drop, ModItemGroups.MISC_BLOCKS_CONTENTS);
    }

    static void registerBlockItem(String name, Block block) {
        var item =  Registry.register(Registries.ITEM, Identifier.of(MiddleEarth.MOD_ID, name),
                new BlockItem(block, new Item.Settings().registryKey(keyOfItem(name))));
        Item.BLOCK_ITEMS.put(block, item);
        RegistryAliases.aliases.add(new RegistryAliases.Alias(Registries.ITEM, name));
    }

    public static RegistryKey<Block> keyOfBlock(String id) {
        return RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(MiddleEarth.MOD_ID, id));
    }

    public static RegistryKey<Item> keyOfItem(String id) {
        return RegistryKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, id));
    }

    public static void registerModBlocks() {
        MiddleEarth.LOGGER.logDebugMsg("Registering ModBlocks for " + MiddleEarth.MOD_ID);
    }
}
