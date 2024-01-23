package net.jukoz.me.block;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.block.special.LayersBlock;
import net.jukoz.me.block.special.StoneMyceliumBlock;
import net.jukoz.me.block.special.VerticalSlabBlock;
import net.jukoz.me.block.special.gemstones.CitrineBuddingGemBlock;
import net.jukoz.me.block.special.gemstones.RedAgateBuddingGemBlock;
import net.jukoz.me.block.special.gemstones.SapphireBuddingGemBlock;
import net.jukoz.me.item.utils.ModItemGroups;
import net.minecraft.block.*;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

public class ModBlocks {
    public static final float DIRT_STRENGTH = 0.6f;
    public static final float SLAB_RESISTANCE = 6.0f; // From explosions


    //region STONE PILLARS AND CHISELED
    public static final Block STONE_PILLAR = registerStoneBlock("stone_pillar",
            new PillarBlock(AbstractBlock.Settings.copy(Blocks.QUARTZ_PILLAR).strength(StoneBlockSets.BRICKS_HARDNESS, StoneBlockSets.BRICKS_BLASTRESISTANCE).sounds(BlockSoundGroup.STONE)));
    public static final Block MOSSY_STONE_PILLAR = registerStoneBlock("mossy_stone_pillar",
            new PillarBlock(AbstractBlock.Settings.copy(STONE_PILLAR).strength(StoneBlockSets.BRICKS_HARDNESS, StoneBlockSets.BRICKS_BLASTRESISTANCE).sounds(BlockSoundGroup.STONE)));
    public static final Block CRACKED_STONE_PILLAR = registerStoneBlock("cracked_stone_pillar",
            new PillarBlock(AbstractBlock.Settings.copy(STONE_PILLAR).strength(StoneBlockSets.BRICKS_HARDNESS, StoneBlockSets.BRICKS_BLASTRESISTANCE).sounds(BlockSoundGroup.STONE)));
    
    public static final Block DEEPSLATE_PILLAR = registerStoneBlock("deepslate_pillar",
            new PillarBlock(AbstractBlock.Settings.copy(Blocks.QUARTZ_PILLAR).strength(StoneBlockSets.DEEPSLATE_BRICKS_HARDNESS, StoneBlockSets.DEEPSLATE_BRICKS_BLAST_RESISTANCE).sounds(BlockSoundGroup.DEEPSLATE)));
    public static final Block MOSSY_DEEPSLATE_PILLAR = registerStoneBlock("mossy_deepslate_pillar",
            new PillarBlock(AbstractBlock.Settings.copy(DEEPSLATE_PILLAR).strength(StoneBlockSets.DEEPSLATE_BRICKS_HARDNESS, StoneBlockSets.DEEPSLATE_BRICKS_BLAST_RESISTANCE).sounds(BlockSoundGroup.DEEPSLATE)));
    public static final Block CRACKED_DEEPSLATE_PILLAR = registerStoneBlock("cracked_deepslate_pillar",
            new PillarBlock(AbstractBlock.Settings.copy(DEEPSLATE_PILLAR).strength(StoneBlockSets.DEEPSLATE_BRICKS_HARDNESS, StoneBlockSets.DEEPSLATE_BRICKS_BLAST_RESISTANCE).sounds(BlockSoundGroup.DEEPSLATE)));

    public static final Block GONLUIN_PILLAR = registerStoneBlock("gonluin_pillar",
            new PillarBlock(AbstractBlock.Settings.copy(Blocks.QUARTZ_PILLAR).strength(StoneBlockSets.BRICKS_HARDNESS, StoneBlockSets.BRICKS_BLASTRESISTANCE).sounds(BlockSoundGroup.STONE)));

    public static final Block BLUE_TUFF_PILLAR = registerStoneBlock("blue_tuff_pillar",
            new PillarBlock(AbstractBlock.Settings.copy(Blocks.QUARTZ_PILLAR).strength(StoneBlockSets.BRICKS_HARDNESS, StoneBlockSets.BRICKS_BLASTRESISTANCE).sounds(BlockSoundGroup.STONE)));

    public static final Block FROZEN_PILLAR = registerStoneBlock("frozen_pillar",
            new PillarBlock(AbstractBlock.Settings.copy(Blocks.QUARTZ_PILLAR).strength(StoneBlockSets.BRICKS_HARDNESS, StoneBlockSets.BRICKS_BLASTRESISTANCE).sounds(BlockSoundGroup.STONE)));

    public static final Block CALCITE_PILLAR = registerStoneBlock("calcite_pillar",
            new PillarBlock(AbstractBlock.Settings.copy(Blocks.QUARTZ_PILLAR).strength(StoneBlockSets.BRICKS_HARDNESS, StoneBlockSets.BRICKS_BLASTRESISTANCE).sounds(BlockSoundGroup.STONE)));
    public static final Block MOSSY_CALCITE_PILLAR = registerStoneBlock("mossy_calcite_pillar",
            new PillarBlock(AbstractBlock.Settings.copy(Blocks.QUARTZ_PILLAR).strength(StoneBlockSets.BRICKS_HARDNESS, StoneBlockSets.BRICKS_BLASTRESISTANCE).sounds(BlockSoundGroup.STONE)));
    public static final Block CRACKED_CALCITE_PILLAR = registerStoneBlock("cracked_calcite_pillar",
            new PillarBlock(AbstractBlock.Settings.copy(Blocks.QUARTZ_PILLAR).strength(StoneBlockSets.BRICKS_HARDNESS, StoneBlockSets.BRICKS_BLASTRESISTANCE).sounds(BlockSoundGroup.STONE)));

    public static final Block LIMESTONE_PILLAR = registerStoneBlock("limestone_pillar",
            new PillarBlock(AbstractBlock.Settings.copy(Blocks.QUARTZ_PILLAR).strength(StoneBlockSets.BRICKS_HARDNESS, StoneBlockSets.BRICKS_BLASTRESISTANCE).sounds(BlockSoundGroup.STONE)));
    public static final Block MOSSY_LIMESTONE_PILLAR = registerStoneBlock("mossy_limestone_pillar",
            new PillarBlock(AbstractBlock.Settings.copy(Blocks.QUARTZ_PILLAR).strength(StoneBlockSets.BRICKS_HARDNESS, StoneBlockSets.BRICKS_BLASTRESISTANCE).sounds(BlockSoundGroup.STONE)));
    public static final Block CRACKED_LIMESTONE_PILLAR = registerStoneBlock("cracked_limestone_pillar",
            new PillarBlock(AbstractBlock.Settings.copy(Blocks.QUARTZ_PILLAR).strength(StoneBlockSets.BRICKS_HARDNESS, StoneBlockSets.BRICKS_BLASTRESISTANCE).sounds(BlockSoundGroup.STONE)));

    public static final Block ANDESITE_PILLAR = registerStoneBlock("andesite_pillar",
            new PillarBlock(AbstractBlock.Settings.copy(Blocks.QUARTZ_PILLAR).strength(StoneBlockSets.BRICKS_HARDNESS, StoneBlockSets.BRICKS_BLASTRESISTANCE).sounds(BlockSoundGroup.STONE)));
    public static final Block MOSSY_ANDESITE_PILLAR = registerStoneBlock("mossy_andesite_pillar",
            new PillarBlock(AbstractBlock.Settings.copy(Blocks.QUARTZ_PILLAR).strength(StoneBlockSets.BRICKS_HARDNESS, StoneBlockSets.BRICKS_BLASTRESISTANCE).sounds(BlockSoundGroup.STONE)));
    public static final Block CRACKED_ANDESITE_PILLAR = registerStoneBlock("cracked_andesite_pillar",
            new PillarBlock(AbstractBlock.Settings.copy(Blocks.QUARTZ_PILLAR).strength(StoneBlockSets.BRICKS_HARDNESS, StoneBlockSets.BRICKS_BLASTRESISTANCE).sounds(BlockSoundGroup.STONE)));

    public static final Block GRANITE_PILLAR = registerStoneBlock("granite_pillar",
            new PillarBlock(AbstractBlock.Settings.copy(Blocks.QUARTZ_PILLAR).strength(StoneBlockSets.BRICKS_HARDNESS, StoneBlockSets.BRICKS_BLASTRESISTANCE).sounds(BlockSoundGroup.STONE)));
    public static final Block MOSSY_GRANITE_PILLAR = registerStoneBlock("mossy_granite_pillar",
            new PillarBlock(AbstractBlock.Settings.copy(Blocks.QUARTZ_PILLAR).strength(StoneBlockSets.BRICKS_HARDNESS, StoneBlockSets.BRICKS_BLASTRESISTANCE).sounds(BlockSoundGroup.STONE)));
    public static final Block CRACKED_GRANITE_PILLAR = registerStoneBlock("cracked_granite_pillar",
            new PillarBlock(AbstractBlock.Settings.copy(Blocks.QUARTZ_PILLAR).strength(StoneBlockSets.BRICKS_HARDNESS, StoneBlockSets.BRICKS_BLASTRESISTANCE).sounds(BlockSoundGroup.STONE)));

    public static final Block DIORITE_PILLAR = registerStoneBlock("diorite_pillar",
            new PillarBlock(AbstractBlock.Settings.copy(Blocks.QUARTZ_PILLAR).strength(StoneBlockSets.BRICKS_HARDNESS, StoneBlockSets.BRICKS_BLASTRESISTANCE).sounds(BlockSoundGroup.STONE)));
    public static final Block MOSSY_DIORITE_PILLAR = registerStoneBlock("mossy_diorite_pillar",
            new PillarBlock(AbstractBlock.Settings.copy(Blocks.QUARTZ_PILLAR).strength(StoneBlockSets.BRICKS_HARDNESS, StoneBlockSets.BRICKS_BLASTRESISTANCE).sounds(BlockSoundGroup.STONE)));
    public static final Block CRACKED_DIORITE_PILLAR = registerStoneBlock("cracked_diorite_pillar",
            new PillarBlock(AbstractBlock.Settings.copy(Blocks.QUARTZ_PILLAR).strength(StoneBlockSets.BRICKS_HARDNESS, StoneBlockSets.BRICKS_BLASTRESISTANCE).sounds(BlockSoundGroup.STONE)));

    public static final Block JADEITE_PILLAR = registerStoneBlock("jadeite_pillar",
            new PillarBlock(AbstractBlock.Settings.copy(Blocks.QUARTZ_PILLAR).strength(StoneBlockSets.BRICKS_HARDNESS, StoneBlockSets.BRICKS_BLASTRESISTANCE).sounds(BlockSoundGroup.STONE)));
    public static final Block CRACKED_JADEITE_PILLAR = registerStoneBlock("cracked_jadeite_pillar",
            new PillarBlock(AbstractBlock.Settings.copy(Blocks.QUARTZ_PILLAR).strength(StoneBlockSets.BRICKS_HARDNESS, StoneBlockSets.BRICKS_BLASTRESISTANCE).sounds(BlockSoundGroup.STONE)));

    public static final Block CHISELED_GONLUIN_BRICKS = registerStoneBlock("chiseled_gonluin_bricks",
            new Block(FabricBlockSettings.copyOf(Blocks.CHISELED_STONE_BRICKS).strength(StoneBlockSets.BRICKS_HARDNESS, StoneBlockSets.BRICKS_BLASTRESISTANCE).sounds(BlockSoundGroup.STONE)));
    public static final Block CHISELED_POLISHED_GONLUIN = registerStoneBlock("chiseled_polished_gonluin",
            new Block(FabricBlockSettings.copyOf(Blocks.CHISELED_STONE_BRICKS).strength(StoneBlockSets.BRICKS_HARDNESS, StoneBlockSets.BRICKS_BLASTRESISTANCE).sounds(BlockSoundGroup.STONE)));

    public static final Block CHISELED_BLUE_TUFF_BRICKS = registerStoneBlock("chiseled_blue_tuff_bricks",
            new PillarBlock(FabricBlockSettings.copyOf(Blocks.CHISELED_STONE_BRICKS).strength(StoneBlockSets.BRICKS_HARDNESS, StoneBlockSets.BRICKS_BLASTRESISTANCE).sounds(BlockSoundGroup.STONE)));
    public static final Block CHISELED_POLISHED_BLUE_TUFF = registerStoneBlock("chiseled_polished_blue_tuff",
            new Block(FabricBlockSettings.copyOf(Blocks.CHISELED_STONE_BRICKS).strength(StoneBlockSets.BRICKS_HARDNESS, StoneBlockSets.BRICKS_BLASTRESISTANCE).sounds(BlockSoundGroup.STONE)));

    public static final Block CHISELED_CALCITE_BRICKS = registerStoneBlock("chiseled_calcite_bricks",
            new Block(FabricBlockSettings.copyOf(Blocks.CHISELED_STONE_BRICKS).strength(StoneBlockSets.BRICKS_HARDNESS, StoneBlockSets.BRICKS_BLASTRESISTANCE).sounds(BlockSoundGroup.STONE)));
    public static final Block CHISELED_POLISHED_CALCITE = registerStoneBlock("chiseled_polished_calcite",
            new Block(FabricBlockSettings.copyOf(Blocks.CHISELED_STONE_BRICKS).strength(StoneBlockSets.BRICKS_HARDNESS, StoneBlockSets.BRICKS_BLASTRESISTANCE).sounds(BlockSoundGroup.STONE)));

    public static final Block CHISELED_LIMESTONE_BRICKS = registerStoneBlock("chiseled_limestone_bricks",
            new Block(FabricBlockSettings.copyOf(Blocks.CHISELED_STONE_BRICKS).strength(StoneBlockSets.BRICKS_HARDNESS, StoneBlockSets.BRICKS_BLASTRESISTANCE).sounds(BlockSoundGroup.STONE)));
    public static final Block CHISELED_POLISHED_LIMESTONE = registerStoneBlock("chiseled_polished_limestone",
            new Block(FabricBlockSettings.copyOf(Blocks.CHISELED_STONE_BRICKS).strength(StoneBlockSets.BRICKS_HARDNESS, StoneBlockSets.BRICKS_BLASTRESISTANCE).sounds(BlockSoundGroup.STONE)));
    
    public static final Block CHISELED_SMOOTH_STONE = registerStoneBlock("chiseled_smooth_stone",
            new Block(FabricBlockSettings.copyOf(Blocks.CHISELED_STONE_BRICKS).strength(StoneBlockSets.BRICKS_HARDNESS, StoneBlockSets.BRICKS_BLASTRESISTANCE).sounds(BlockSoundGroup.STONE)));

    public static final Block CHISELED_ANDESITE_BRICKS = registerStoneBlock("chiseled_andesite_bricks",
            new Block(FabricBlockSettings.copyOf(Blocks.CHISELED_STONE_BRICKS).strength(StoneBlockSets.BRICKS_HARDNESS, StoneBlockSets.BRICKS_BLASTRESISTANCE).sounds(BlockSoundGroup.STONE)));
    public static final Block CHISELED_POLISHED_ANDESITE = registerStoneBlock("chiseled_polished_andesite",
            new Block(FabricBlockSettings.copyOf(Blocks.CHISELED_STONE_BRICKS).strength(StoneBlockSets.BRICKS_HARDNESS, StoneBlockSets.BRICKS_BLASTRESISTANCE).sounds(BlockSoundGroup.STONE)));
    
    public static final Block CHISELED_GRANITE_BRICKS = registerStoneBlock("chiseled_granite_bricks",
            new Block(FabricBlockSettings.copyOf(Blocks.CHISELED_STONE_BRICKS).strength(StoneBlockSets.BRICKS_HARDNESS, StoneBlockSets.BRICKS_BLASTRESISTANCE).sounds(BlockSoundGroup.STONE)));
    public static final Block CHISELED_POLISHED_GRANITE = registerStoneBlock("chiseled_polished_granite",
            new Block(FabricBlockSettings.copyOf(Blocks.CHISELED_STONE_BRICKS).strength(StoneBlockSets.BRICKS_HARDNESS, StoneBlockSets.BRICKS_BLASTRESISTANCE).sounds(BlockSoundGroup.STONE)));

    public static final Block CHISELED_DIORITE_BRICKS = registerStoneBlock("chiseled_diorite_bricks",
            new Block(FabricBlockSettings.copyOf(Blocks.CHISELED_STONE_BRICKS).strength(StoneBlockSets.BRICKS_HARDNESS, StoneBlockSets.BRICKS_BLASTRESISTANCE).sounds(BlockSoundGroup.STONE)));
    public static final Block CHISELED_POLISHED_DIORITE = registerStoneBlock("chiseled_polished_diorite",
            new Block(FabricBlockSettings.copyOf(Blocks.CHISELED_STONE_BRICKS).strength(StoneBlockSets.BRICKS_HARDNESS, StoneBlockSets.BRICKS_BLASTRESISTANCE).sounds(BlockSoundGroup.STONE)));
    
    public static final Block CHISELED_BASALT_BRICKS = registerStoneBlock("chiseled_basalt_bricks",
            new Block(FabricBlockSettings.copyOf(Blocks.CHISELED_STONE_BRICKS).strength(StoneBlockSets.BRICKS_HARDNESS, StoneBlockSets.BRICKS_BLASTRESISTANCE).sounds(BlockSoundGroup.STONE)));

    public static final Block DIFTOMIN_PILLAR = registerStoneBlock("diftomin_pillar",
            new PillarBlock(AbstractBlock.Settings.copy(Blocks.QUARTZ_PILLAR).strength(StoneBlockSets.DIFTOMIN_BRICKS_HARDNESS, StoneBlockSets.DIFTOMIN_BLAST_RESISTANCE).sounds(BlockSoundGroup.STONE)));
    public static final Block CHISELED_DIFTOMIN_BRICKS = registerStoneBlock("chiseled_diftomin_bricks",
            new Block(FabricBlockSettings.copyOf(Blocks.CHISELED_STONE_BRICKS).strength(StoneBlockSets.DIFTOMIN_BRICKS_HARDNESS, StoneBlockSets.DIFTOMIN_BLAST_RESISTANCE).sounds(BlockSoundGroup.STONE)));
    public static final Block CHISELED_POLISHED_DIFTOMIN = registerStoneBlock("chiseled_polished_diftomin",
            new Block(FabricBlockSettings.copyOf(Blocks.CHISELED_STONE_BRICKS).strength(StoneBlockSets.DIFTOMIN_BRICKS_HARDNESS, StoneBlockSets.DIFTOMIN_BLAST_RESISTANCE).sounds(BlockSoundGroup.STONE)));


    //endregion

    public static final Block DRY_DIRT = registerMiscBlock("dry_dirt",
            new Block(FabricBlockSettings.copyOf(Blocks.DIRT).strength(DIRT_STRENGTH).sounds(BlockSoundGroup.GRAVEL)));
    public static final Block DRY_DIRT_SLAB = registerMiscBlock("dry_dirt_slab",
            new SlabBlock(FabricBlockSettings.copyOf(DRY_DIRT)));
    public static final Block DRY_DIRT_STAIRS = registerMiscBlock("dry_dirt_stairs",
            new StairsBlock(DRY_DIRT.getDefaultState(), FabricBlockSettings.copyOf(DRY_DIRT)));
    public static final Block ASHEN_DIRT = registerMiscBlock("ashen_dirt",
            new Block(FabricBlockSettings.copyOf(Blocks.DIRT).strength(DIRT_STRENGTH).sounds(BlockSoundGroup.GRAVEL)));
    public static final Block ASHEN_DIRT_SLAB = registerMiscBlock("ashen_dirt_slab",
            new SlabBlock(FabricBlockSettings.copyOf(ASHEN_DIRT)));
    public static final Block ASHEN_DIRT_STAIRS = registerMiscBlock("ashen_dirt_stairs",
            new StairsBlock(ASHEN_DIRT.getDefaultState(), FabricBlockSettings.copyOf(ASHEN_DIRT)));
    
    public static final Block REED_BLOCK = registerMiscBlock("reed_block",
            new HayBlock(FabricBlockSettings.copyOf(Blocks.HAY_BLOCK).strength(DIRT_STRENGTH).sounds(BlockSoundGroup.GRASS)));
    public static final Block REED_STAIRS = registerStoneBlock("reed_stairs",
            new StairsBlock(REED_BLOCK.getDefaultState(), FabricBlockSettings.copyOf(Blocks.HAY_BLOCK).strength(DIRT_STRENGTH).sounds(BlockSoundGroup.GRASS)));
    public static final Block REED_SLAB = registerMiscBlock("reed_slab",
            new SlabBlock(FabricBlockSettings.copyOf(Blocks.HAY_BLOCK).strength(DIRT_STRENGTH).sounds(BlockSoundGroup.GRASS)));
    public static final Block REED_VERTICAL_SLAB = registerMiscBlock("reed_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(ModBlocks.REED_SLAB)));
    public static final Block REED_WALL = registerMiscBlock("reed_wall",
            new WallBlock(FabricBlockSettings.copyOf(Blocks.HAY_BLOCK).strength(DIRT_STRENGTH).sounds(BlockSoundGroup.GRASS)));
    public static final Block STRAW_BLOCK = registerMiscBlock("straw_block",
            new HayBlock(FabricBlockSettings.copyOf(Blocks.HAY_BLOCK).strength(DIRT_STRENGTH).sounds(BlockSoundGroup.GRASS)));
    public static final Block STRAW_STAIRS = registerMiscBlock("straw_stairs",
            new StairsBlock(STRAW_BLOCK.getDefaultState(), FabricBlockSettings.copyOf(Blocks.HAY_BLOCK).strength(DIRT_STRENGTH).sounds(BlockSoundGroup.GRASS)));
    public static final Block STRAW_SLAB = registerMiscBlock("straw_slab",
            new SlabBlock(FabricBlockSettings.copyOf(Blocks.HAY_BLOCK).strength(DIRT_STRENGTH).sounds(BlockSoundGroup.GRASS)));
    public static final Block STRAW_VERTICAL_SLAB = registerMiscBlock("straw_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(ModBlocks.STRAW_SLAB)));
    public static final Block STRAW_WALL = registerMiscBlock("straw_wall",
            new WallBlock(FabricBlockSettings.copyOf(Blocks.HAY_BLOCK).strength(DIRT_STRENGTH).sounds(BlockSoundGroup.GRASS)));

    //region METAL AND GEMS
    public static final Block RAW_MITHRIL_BLOCK = registerMiscBlock("raw_mithril_block",
            new Block(FabricBlockSettings.copyOf(Blocks.RAW_IRON_BLOCK).strength(4f).requiresTool()));
    public static final Block MITHRIL_BLOCK = registerMiscBlock("mithril_block",
            new Block(FabricBlockSettings.copyOf(Blocks.IRON_BLOCK).strength(6f).requiresTool()));
    public static final Block RAW_TIN_BLOCK = registerMiscBlock("raw_tin_block",
            new Block(FabricBlockSettings.copyOf(Blocks.STONE).strength(2f).requiresTool()));
    public static final Block TIN_BLOCK = registerMiscBlock("tin_block",
            new Block(FabricBlockSettings.copyOf(Blocks.IRON_BLOCK).strength(3f).requiresTool()));
    public static final Block RAW_LEAD_BLOCK = registerMiscBlock("raw_lead_block",
            new Block(FabricBlockSettings.copyOf(Blocks.RAW_IRON_BLOCK).strength(3f).requiresTool()));
    public static final Block LEAD_BLOCK = registerMiscBlock("lead_block",
            new Block(FabricBlockSettings.copyOf(Blocks.IRON_BLOCK).strength(2f).requiresTool()));
    public static final Block RAW_SILVER_BLOCK = registerMiscBlock("raw_silver_block",
            new Block(FabricBlockSettings.copyOf(Blocks.RAW_IRON_BLOCK).strength(3f).requiresTool()));
    public static final Block SILVER_BLOCK = registerMiscBlock("silver_block",
            new Block(FabricBlockSettings.copyOf(Blocks.IRON_BLOCK).strength(4f).requiresTool()));

    public static final Block CUT_LEAD = registerMiscBlock("cut_lead",
            new Block(FabricBlockSettings.copyOf(Blocks.CUT_COPPER).sounds(BlockSoundGroup.COPPER)));
    public static final Block CUT_LEAD_STAIRS = registerMiscBlock("cut_lead_stairs",
            new StairsBlock(REED_BLOCK.getDefaultState(), FabricBlockSettings.copyOf(Blocks.CUT_COPPER).sounds(BlockSoundGroup.COPPER)));
    public static final Block CUT_LEAD_SLAB = registerMiscBlock("cut_lead_slab",
            new SlabBlock(FabricBlockSettings.copyOf(Blocks.CUT_COPPER).sounds(BlockSoundGroup.COPPER)));
    public static final Block CUT_LEAD_VERTICAL_SLAB = registerMiscBlock("cut_lead_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(ModBlocks.CUT_LEAD_SLAB)));

    public static final Block SAPPHIRE_BLOCK = registerMiscBlock("sapphire_block",
            new AmethystBlock(FabricBlockSettings.copyOf(Blocks.AMETHYST_BLOCK)));
    public static final Block BUDDING_SAPPHIRE = registerMiscBlock("budding_sapphire",
            new SapphireBuddingGemBlock(FabricBlockSettings.copyOf(Blocks.BUDDING_AMETHYST)));
    public static final Block SAPPHIRE_CLUSTER = registerMiscBlock("sapphire_cluster",
            new AmethystClusterBlock(7,3, FabricBlockSettings.copyOf(Blocks.AMETHYST_CLUSTER)));
    public static final Block LARGE_SAPPHIRE_BUD = registerMiscBlock("large_sapphire_bud",
            new AmethystClusterBlock(5,3, FabricBlockSettings.copyOf(ModBlocks.SAPPHIRE_CLUSTER).sounds(BlockSoundGroup.LARGE_AMETHYST_BUD)));
    public static final Block MEDIUM_SAPPHIRE_BUD = registerMiscBlock("medium_sapphire_bud",
            new AmethystClusterBlock(4,3, FabricBlockSettings.copyOf(ModBlocks.SAPPHIRE_CLUSTER).sounds(BlockSoundGroup.MEDIUM_AMETHYST_BUD)));
    public static final Block SMALL_SAPPHIRE_BUD = registerMiscBlock("small_sapphire_bud",
            new AmethystClusterBlock(3,4, FabricBlockSettings.copyOf(ModBlocks.SAPPHIRE_CLUSTER).sounds(BlockSoundGroup.SMALL_AMETHYST_BUD)));

    public static final Block RED_AGATE_BLOCK = registerMiscBlock("red_agate_block",
            new AmethystBlock(FabricBlockSettings.copyOf(Blocks.AMETHYST_BLOCK)));
    public static final Block BUDDING_RED_AGATE = registerMiscBlock("budding_red_agate",
            new RedAgateBuddingGemBlock(FabricBlockSettings.copyOf(Blocks.BUDDING_AMETHYST)));
    public static final Block RED_AGATE_CLUSTER = registerMiscBlock("red_agate_cluster",
            new AmethystClusterBlock(7,3, FabricBlockSettings.copyOf(Blocks.AMETHYST_CLUSTER)));
    public static final Block LARGE_RED_AGATE_BUD = registerMiscBlock("large_red_agate_bud",
            new AmethystClusterBlock(5,3, FabricBlockSettings.copyOf(ModBlocks.RED_AGATE_CLUSTER).sounds(BlockSoundGroup.LARGE_AMETHYST_BUD)));
    public static final Block MEDIUM_RED_AGATE_BUD = registerMiscBlock("medium_red_agate_bud",
            new AmethystClusterBlock(4,3, FabricBlockSettings.copyOf(ModBlocks.RED_AGATE_CLUSTER).sounds(BlockSoundGroup.MEDIUM_AMETHYST_BUD)));
    public static final Block SMALL_RED_AGATE_BUD = registerMiscBlock("small_red_agate_bud",
            new AmethystClusterBlock(3,4, FabricBlockSettings.copyOf(ModBlocks.RED_AGATE_CLUSTER).sounds(BlockSoundGroup.SMALL_AMETHYST_BUD)));

    public static final Block CITRINE_BLOCK = registerMiscBlock("citrine_block",
            new AmethystBlock(FabricBlockSettings.copyOf(Blocks.AMETHYST_BLOCK)));
    public static final Block BUDDING_CITRINE = registerMiscBlock("budding_citrine",
            new CitrineBuddingGemBlock(FabricBlockSettings.copyOf(Blocks.BUDDING_AMETHYST)));
    public static final Block CITRINE_CLUSTER = registerMiscBlock("citrine_cluster",
            new AmethystClusterBlock(7,3, FabricBlockSettings.copyOf(Blocks.AMETHYST_CLUSTER)));
    public static final Block LARGE_CITRINE_BUD = registerMiscBlock("large_citrine_bud",
            new AmethystClusterBlock(5,3, FabricBlockSettings.copyOf(ModBlocks.CITRINE_CLUSTER).sounds(BlockSoundGroup.LARGE_AMETHYST_BUD)));
    public static final Block MEDIUM_CITRINE_BUD = registerMiscBlock("medium_citrine_bud",
            new AmethystClusterBlock(4,3, FabricBlockSettings.copyOf(ModBlocks.CITRINE_CLUSTER).sounds(BlockSoundGroup.MEDIUM_AMETHYST_BUD)));
    public static final Block SMALL_CITRINE_BUD = registerMiscBlock("small_citrine_bud",
            new AmethystClusterBlock(3,4, FabricBlockSettings.copyOf(ModBlocks.CITRINE_CLUSTER).sounds(BlockSoundGroup.SMALL_AMETHYST_BUD)));

    public static final Block STONE_TRAPDOOR = registerStoneBlock("stone_trapdoor",
            new TrapdoorBlock(FabricBlockSettings.copyOf(Blocks.OAK_TRAPDOOR).strength(StoneBlockSets.BRICKS_HARDNESS, StoneBlockSets.BRICKS_BLASTRESISTANCE).sounds(BlockSoundGroup.STONE), BlockSetType.STONE));
    public static final Block BLACKSTONE_TRAPDOOR = registerStoneBlock("blackstone_trapdoor",
            new TrapdoorBlock(FabricBlockSettings.copyOf(Blocks.OAK_TRAPDOOR).strength(StoneBlockSets.BRICKS_HARDNESS, StoneBlockSets.BRICKS_BLASTRESISTANCE).sounds(BlockSoundGroup.STONE), BlockSetType.POLISHED_BLACKSTONE));

    public static final Block SILVERS_BARS = registerMiscBlock("silver_bars",
            new PaneBlock(FabricBlockSettings.copyOf(Blocks.IRON_BARS).sounds(BlockSoundGroup.METAL)));
    //endregion

    //region GLASS

    //endregion

    public static final Block RIVER_SAND = registerMiscBlock("river_sand",
            new FallingBlock(FabricBlockSettings.copyOf(Blocks.SAND).sounds(BlockSoundGroup.SAND)));

    public static final Block STONE_MYCELIUM = registerMiscBlock("stone_mycelium",
            new StoneMyceliumBlock(FabricBlockSettings.copyOf(Blocks.STONE).ticksRandomly()));

    public static final Block ASH_BLOCK = registerMiscBlock("ash_block",
            new FallingBlock(FabricBlockSettings.copyOf(Blocks.SAND).strength(DIRT_STRENGTH).sounds(BlockSoundGroup.SAND)));

    //region VANILLA SLABS
    public static final Block DIRT_SLAB = registerMiscBlock("dirt_slab",
            new SlabBlock(FabricBlockSettings.copyOf(Blocks.DIRT)));
    public static final Block COARSE_DIRT_SLAB = registerMiscBlock("coarse_dirt_slab",
            new SlabBlock(FabricBlockSettings.copyOf(Blocks.COARSE_DIRT)));
    public static final Block ROOTED_DIRT_SLAB = registerMiscBlock("rooted_dirt_slab",
            new SlabBlock(FabricBlockSettings.copyOf(Blocks.ROOTED_DIRT)));
    public static final Block MUD_SLAB = registerMiscBlock("mud_slab",
            new SlabBlock(FabricBlockSettings.copyOf(Blocks.ROOTED_DIRT)));

    public static final Block BLACK_WOOL_SLAB = registerMiscBlock("black_wool_slab",
            new SlabBlock(FabricBlockSettings.copyOf(Blocks.WHITE_WOOL)));
    public static final Block BLUE_WOOL_SLAB = registerMiscBlock("blue_wool_slab",
            new SlabBlock(FabricBlockSettings.copyOf(Blocks.WHITE_WOOL)));
    public static final Block BROWN_WOOL_SLAB = registerMiscBlock("brown_wool_slab",
            new SlabBlock(FabricBlockSettings.copyOf(Blocks.WHITE_WOOL)));
    public static final Block CYAN_WOOL_SLAB = registerMiscBlock("cyan_wool_slab",
            new SlabBlock(FabricBlockSettings.copyOf(Blocks.WHITE_WOOL)));
    public static final Block GRAY_WOOL_SLAB = registerMiscBlock("gray_wool_slab",
            new SlabBlock(FabricBlockSettings.copyOf(Blocks.WHITE_WOOL)));
    public static final Block GREEN_WOOL_SLAB = registerMiscBlock("green_wool_slab",
            new SlabBlock(FabricBlockSettings.copyOf(Blocks.WHITE_WOOL)));
    public static final Block LIGHT_BLUE_WOOL_SLAB = registerMiscBlock("light_blue_wool_slab",
            new SlabBlock(FabricBlockSettings.copyOf(Blocks.WHITE_WOOL)));
    public static final Block LIGHT_GRAY_WOOL_SLAB = registerMiscBlock("light_gray_wool_slab",
            new SlabBlock(FabricBlockSettings.copyOf(Blocks.WHITE_WOOL)));
    public static final Block LIME_WOOL_SLAB = registerMiscBlock("lime_wool_slab",
            new SlabBlock(FabricBlockSettings.copyOf(Blocks.WHITE_WOOL)));
    public static final Block MAGENTA_WOOL_SLAB = registerMiscBlock("magenta_wool_slab",
            new SlabBlock(FabricBlockSettings.copyOf(Blocks.WHITE_WOOL)));
    public static final Block ORANGE_WOOL_SLAB = registerMiscBlock("orange_wool_slab",
            new SlabBlock(FabricBlockSettings.copyOf(Blocks.WHITE_WOOL)));
    public static final Block PINK_WOOL_SLAB = registerMiscBlock("pink_wool_slab",
            new SlabBlock(FabricBlockSettings.copyOf(Blocks.WHITE_WOOL)));
    public static final Block PURPLE_WOOL_SLAB = registerMiscBlock("purple_wool_slab",
            new SlabBlock(FabricBlockSettings.copyOf(Blocks.WHITE_WOOL)));
    public static final Block RED_WOOL_SLAB = registerMiscBlock("red_wool_slab",
            new SlabBlock(FabricBlockSettings.copyOf(Blocks.WHITE_WOOL)));
    public static final Block WHITE_WOOL_SLAB = registerMiscBlock("white_wool_slab",
            new SlabBlock(FabricBlockSettings.copyOf(Blocks.WHITE_WOOL)));
    public static final Block YELLOW_WOOL_SLAB = registerMiscBlock("yellow_wool_slab",
            new SlabBlock(FabricBlockSettings.copyOf(Blocks.WHITE_WOOL)));
    //endregion

    //region VANILLA VERTICAL SLABS
    public static final Block OAK_VERTICAL_SLAB = registerWoodBlock("oak_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.OAK_SLAB)));
    public static final Block SPRUCE_VERTICAL_SLAB = registerWoodBlock("spruce_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.SPRUCE_SLAB)));
    public static final Block BIRCH_VERTICAL_SLAB = registerWoodBlock("birch_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.BIRCH_SLAB)));
    public static final Block JUNGLE_VERTICAL_SLAB = registerWoodBlock("jungle_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.JUNGLE_SLAB)));
    public static final Block ACACIA_VERTICAL_SLAB = registerWoodBlock("acacia_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.ACACIA_SLAB)));
    public static final Block DARK_OAK_VERTICAL_SLAB = registerWoodBlock("dark_oak_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.DARK_OAK_SLAB)));
    public static final Block MANGROVE_VERTICAL_SLAB = registerWoodBlock("mangrove_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.MANGROVE_SLAB)));
    public static final Block CHERRY_VERTICAL_SLAB = registerWoodBlock("cherry_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.CHERRY_SLAB)));
    public static final Block BAMBOO_VERTICAL_SLAB = registerWoodBlock("bamboo_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.BAMBOO_SLAB)));
    public static final Block CRIMSON_VERTICAL_SLAB = registerWoodBlock("crimson_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.CRIMSON_SLAB)));
    public static final Block WARPED_VERTICAL_SLAB = registerWoodBlock("warped_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.WARPED_SLAB)));

    public static final Block STONE_VERTICAL_SLAB = registerStoneBlock("stone_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.STONE_SLAB)));
    public static final Block COBBLESTONE_VERTICAL_SLAB = registerStoneBlock("cobblestone_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.COBBLESTONE_SLAB)));
    public static final Block MOSSY_COBBLESTONE_VERTICAL_SLAB = registerStoneBlock("mossy_cobblestone_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.MOSSY_COBBLESTONE_SLAB)));
    public static final Block SMOOTH_STONE_VERTICAL_SLAB = registerStoneBlock("smooth_stone_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.SMOOTH_STONE_SLAB)));
    public static final Block STONE_BRICK_VERTICAL_SLAB = registerStoneBlock("stone_brick_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.STONE_BRICK_SLAB)));
    public static final Block MOSSY_STONE_BRICK_VERTICAL_SLAB = registerStoneBlock("mossy_stone_brick_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.MOSSY_STONE_BRICK_SLAB)));
    public static final Block GRANITE_VERTICAL_SLAB = registerStoneBlock("granite_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.GRANITE_SLAB)));
    public static final Block POLISHED_GRANITE_VERTICAL_SLAB = registerStoneBlock("polished_granite_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.POLISHED_GRANITE_SLAB)));
    public static final Block DIORITE_VERTICAL_SLAB = registerStoneBlock("diorite_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.DIORITE_SLAB)));
    public static final Block POLISHED_DIORITE_VERTICAL_SLAB = registerStoneBlock("polished_diorite_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.POLISHED_DIORITE_SLAB)));
    public static final Block ANDESITE_VERTICAL_SLAB = registerStoneBlock("andesite_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.ANDESITE_SLAB)));
    public static final Block POLISHED_ANDESITE_VERTICAL_SLAB = registerStoneBlock("polished_andesite_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.POLISHED_ANDESITE_SLAB)));
    public static final Block COBBLED_DEEPSLATE_VERTICAL_SLAB = registerStoneBlock("cobbled_deepslate_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.COBBLED_DEEPSLATE_SLAB)));
    public static final Block POLISHED_DEEPSLATE_VERTICAL_SLAB = registerStoneBlock("polished_deepslate_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.POLISHED_DEEPSLATE_SLAB)));
    public static final Block DEEPSLATE_BRICK_VERTICAL_SLAB = registerStoneBlock("deepslate_brick_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.DEEPSLATE_BRICK_SLAB)));
    public static final Block DEEPSLATE_TILE_VERTICAL_SLAB = registerStoneBlock("deepslate_tile_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.DEEPSLATE_TILE_SLAB)));
    public static final Block BRICK_VERTICAL_SLAB = registerStoneBlock("brick_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.BRICK_SLAB)));
    public static final Block MUD_BRICK_VERTICAL_SLAB = registerStoneBlock("mud_brick_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.MUD_BRICK_SLAB)));
    public static final Block SANDSTONE_VERTICAL_SLAB = registerStoneBlock("sandstone_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.SANDSTONE_SLAB)));
    public static final Block SMOOTH_SANDSTONE_VERTICAL_SLAB = registerStoneBlock("smooth_sandstone_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.SMOOTH_SANDSTONE_SLAB)));
    public static final Block CUT_SANDSTONE_VERTICAL_SLAB = registerStoneBlock("cut_sandstone_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.CUT_SANDSTONE_SLAB)));
    public static final Block RED_SANDSTONE_VERTICAL_SLAB = registerStoneBlock("red_sandstone_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.RED_SANDSTONE_SLAB)));
    public static final Block SMOOTH_RED_SANDSTONE_VERTICAL_SLAB = registerStoneBlock("smooth_red_sandstone_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.SMOOTH_RED_SANDSTONE_SLAB)));
    public static final Block CUT_RED_SANDSTONE_VERTICAL_SLAB = registerStoneBlock("cut_red_sandstone_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.CUT_RED_SANDSTONE_SLAB)));
    public static final Block PRISMARINE_VERTICAL_SLAB = registerStoneBlock("prismarine_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.PRISMARINE_SLAB)));
    public static final Block PRISMARINE_BRICK_VERTICAL_SLAB = registerStoneBlock("prismarine_brick_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.PRISMARINE_BRICK_SLAB)));
    public static final Block DARK_PRISMARINE_VERTICAL_SLAB = registerStoneBlock("dark_prismarine_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.DARK_PRISMARINE_SLAB)));
    public static final Block NETHER_BRICK_VERTICAL_SLAB = registerStoneBlock("nether_brick_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.NETHER_BRICK_SLAB)));
    public static final Block RED_NETHER_BRICK_VERTICAL_SLAB = registerStoneBlock("red_nether_brick_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.RED_NETHER_BRICK_SLAB)));
    public static final Block BLACKSTONE_VERTICAL_SLAB = registerStoneBlock("blackstone_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.BLACKSTONE_SLAB)));
    public static final Block POLISHED_BLACKSTONE_VERTICAL_SLAB = registerStoneBlock("polished_blackstone_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.POLISHED_BLACKSTONE_SLAB)));
    public static final Block POLISHED_BLACKSTONE_BRICK_VERTICAL_SLAB = registerStoneBlock("polished_blackstone_brick_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.POLISHED_BLACKSTONE_BRICK_SLAB)));
    public static final Block END_STONE_BRICK_VERTICAL_SLAB = registerStoneBlock("end_stone_brick_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.END_STONE_BRICK_SLAB)));
    public static final Block PURPUR_VERTICAL_SLAB = registerStoneBlock("purpur_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.PURPUR_SLAB)));
    public static final Block QUARTZ_VERTICAL_SLAB = registerStoneBlock("quartz_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.QUARTZ_SLAB)));
    public static final Block SMOOTH_QUARTZ_VERTICAL_SLAB = registerStoneBlock("smooth_quartz_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.SMOOTH_QUARTZ_SLAB)));
    public static final Block CUT_COPPER_VERTICAL_SLAB = registerMiscBlock("cut_copper_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.CUT_COPPER_SLAB)));
    public static final Block EXPOSED_CUT_COPPER_VERTICAL_SLAB = registerMiscBlock("exposed_cut_copper_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.EXPOSED_CUT_COPPER_SLAB)));
    public static final Block WEATHERED_CUT_COPPER_VERTICAL_SLAB = registerMiscBlock("weathered_cut_copper_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.WEATHERED_CUT_COPPER_SLAB)));
    public static final Block OXIDIZED_CUT_COPPER_VERTICAL_SLAB = registerMiscBlock("oxidized_cut_copper_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.OXIDIZED_CUT_COPPER_SLAB)));
    public static final Block WAXED_CUT_COPPER_VERTICAL_SLAB = registerMiscBlock("waxed_cut_copper_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.WAXED_CUT_COPPER_SLAB)));
    public static final Block WAXED_EXPOSED_CUT_COPPER_VERTICAL_SLAB = registerMiscBlock("waxed_exposed_cut_copper_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.WAXED_EXPOSED_CUT_COPPER_SLAB)));
    public static final Block WAXED_WEATHERED_CUT_COPPER_VERTICAL_SLAB = registerMiscBlock("waxed_weathered_cut_copper_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.WAXED_WEATHERED_CUT_COPPER_SLAB)));
    public static final Block WAXED_OXIDIZED_CUT_COPPER_VERTICAL_SLAB = registerMiscBlock("waxed_oxidized_cut_copper_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.WAXED_OXIDIZED_CUT_COPPER_SLAB)));

    public static final Block BLACK_WOOL_VERTICAL_SLAB = registerMiscBlock("black_wool_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.WHITE_WOOL)));
    public static final Block BLUE_WOOL_VERTICAL_SLAB = registerMiscBlock("blue_wool_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.WHITE_WOOL)));
    public static final Block BROWN_WOOL_VERTICAL_SLAB = registerMiscBlock("brown_wool_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.WHITE_WOOL)));
    public static final Block CYAN_WOOL_VERTICAL_SLAB = registerMiscBlock("cyan_wool_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.WHITE_WOOL)));
    public static final Block GRAY_WOOL_VERTICAL_SLAB = registerMiscBlock("gray_wool_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.WHITE_WOOL)));
    public static final Block GREEN_WOOL_VERTICAL_SLAB = registerMiscBlock("green_wool_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.WHITE_WOOL)));
    public static final Block LIGHT_BLUE_WOOL_VERTICAL_SLAB = registerMiscBlock("light_blue_wool_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.WHITE_WOOL)));
    public static final Block LIGHT_GRAY_WOOL_VERTICAL_SLAB = registerMiscBlock("light_gray_wool_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.WHITE_WOOL)));
    public static final Block LIME_WOOL_VERTICAL_SLAB = registerMiscBlock("lime_wool_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.WHITE_WOOL)));
    public static final Block MAGENTA_WOOL_VERTICAL_SLAB = registerMiscBlock("magenta_wool_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.WHITE_WOOL)));
    public static final Block ORANGE_WOOL_VERTICAL_SLAB = registerMiscBlock("orange_wool_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.WHITE_WOOL)));
    public static final Block PINK_WOOL_VERTICAL_SLAB = registerMiscBlock("pink_wool_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.WHITE_WOOL)));
    public static final Block PURPLE_WOOL_VERTICAL_SLAB = registerMiscBlock("purple_wool_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.WHITE_WOOL)));
    public static final Block RED_WOOL_VERTICAL_SLAB = registerMiscBlock("red_wool_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.WHITE_WOOL)));
    public static final Block WHITE_WOOL_VERTICAL_SLAB = registerMiscBlock("white_wool_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.WHITE_WOOL)));
    public static final Block YELLOW_WOOL_VERTICAL_SLAB = registerMiscBlock("yellow_wool_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.WHITE_WOOL)));
    //endregion

    //region VANILLA STAIRS
    public static final Block DIRT_STAIRS = registerMiscBlock("dirt_stairs",
            new StairsBlock(Blocks.DIRT.getDefaultState(), FabricBlockSettings.copyOf(Blocks.DIRT)));
    public static final Block COARSE_DIRT_STAIRS = registerMiscBlock("coarse_dirt_stairs",
            new StairsBlock(Blocks.COARSE_DIRT.getDefaultState(), FabricBlockSettings.copyOf(Blocks.COARSE_DIRT)));
    public static final Block MUD_STAIRS = registerMiscBlock("mud_stairs",
            new StairsBlock(Blocks.MUD.getDefaultState(), FabricBlockSettings.copyOf(Blocks.MUD)));

    public static final Block BLACK_WOOL_STAIRS = registerMiscBlock("black_wool_stairs",
            new StairsBlock(Blocks.BLACK_WOOL.getDefaultState(), FabricBlockSettings.copyOf(Blocks.WHITE_WOOL)));
    public static final Block BLUE_WOOL_STAIRS = registerMiscBlock("blue_wool_stairs",
            new StairsBlock(Blocks.BLACK_WOOL.getDefaultState(), FabricBlockSettings.copyOf(Blocks.WHITE_WOOL)));
    public static final Block BROWN_WOOL_STAIRS = registerMiscBlock("brown_wool_stairs",
            new StairsBlock(Blocks.BLACK_WOOL.getDefaultState(), FabricBlockSettings.copyOf(Blocks.WHITE_WOOL)));
    public static final Block CYAN_WOOL_STAIRS = registerMiscBlock("cyan_wool_stairs",
            new StairsBlock(Blocks.BLACK_WOOL.getDefaultState(), FabricBlockSettings.copyOf(Blocks.WHITE_WOOL)));
    public static final Block GRAY_WOOL_STAIRS = registerMiscBlock("gray_wool_stairs",
            new StairsBlock(Blocks.BLACK_WOOL.getDefaultState(), FabricBlockSettings.copyOf(Blocks.WHITE_WOOL)));
    public static final Block GREEN_WOOL_STAIRS = registerMiscBlock("green_wool_stairs",
            new StairsBlock(Blocks.BLACK_WOOL.getDefaultState(), FabricBlockSettings.copyOf(Blocks.WHITE_WOOL)));
    public static final Block LIGHT_BLUE_WOOL_STAIRS = registerMiscBlock("light_blue_wool_stairs",
            new StairsBlock(Blocks.BLACK_WOOL.getDefaultState(), FabricBlockSettings.copyOf(Blocks.WHITE_WOOL)));
    public static final Block LIGHT_GRAY_WOOL_STAIRS = registerMiscBlock("light_gray_wool_stairs",
            new StairsBlock(Blocks.BLACK_WOOL.getDefaultState(), FabricBlockSettings.copyOf(Blocks.WHITE_WOOL)));
    public static final Block LIME_WOOL_STAIRS = registerMiscBlock("lime_wool_stairs",
            new StairsBlock(Blocks.BLACK_WOOL.getDefaultState(), FabricBlockSettings.copyOf(Blocks.WHITE_WOOL)));
    public static final Block MAGENTA_WOOL_STAIRS = registerMiscBlock("magenta_wool_stairs",
            new StairsBlock(Blocks.BLACK_WOOL.getDefaultState(), FabricBlockSettings.copyOf(Blocks.WHITE_WOOL)));
    public static final Block ORANGE_WOOL_STAIRS = registerMiscBlock("orange_wool_stairs",
            new StairsBlock(Blocks.BLACK_WOOL.getDefaultState(), FabricBlockSettings.copyOf(Blocks.WHITE_WOOL)));
    public static final Block PINK_WOOL_STAIRS = registerMiscBlock("pink_wool_stairs",
            new StairsBlock(Blocks.BLACK_WOOL.getDefaultState(), FabricBlockSettings.copyOf(Blocks.WHITE_WOOL)));
    public static final Block PURPLE_WOOL_STAIRS = registerMiscBlock("purple_wool_stairs",
            new StairsBlock(Blocks.BLACK_WOOL.getDefaultState(), FabricBlockSettings.copyOf(Blocks.WHITE_WOOL)));
    public static final Block RED_WOOL_STAIRS = registerMiscBlock("red_wool_stairs",
            new StairsBlock(Blocks.BLACK_WOOL.getDefaultState(), FabricBlockSettings.copyOf(Blocks.WHITE_WOOL)));
    public static final Block WHITE_WOOL_STAIRS = registerMiscBlock("white_wool_stairs",
            new StairsBlock(Blocks.BLACK_WOOL.getDefaultState(), FabricBlockSettings.copyOf(Blocks.WHITE_WOOL)));
    public static final Block YELLOW_WOOL_STAIRS = registerMiscBlock("yellow_wool_stairs",
            new StairsBlock(Blocks.BLACK_WOOL.getDefaultState(), FabricBlockSettings.copyOf(Blocks.WHITE_WOOL)));
    //endregion

    //region VANILLA LAYERS
    public static final Block GRAVEL_LAYER = registerMiscBlock("gravel_layer",
            new LayersBlock(FabricBlockSettings.copyOf(Blocks.GRAVEL), Blocks.GRAVEL));
    public static final Block SAND_LAYER = registerMiscBlock("sand_layer",
            new LayersBlock(FabricBlockSettings.copyOf(Blocks.SAND), Blocks.SAND));
    //endregion

    public static Block registerBlock(String name, Block block) {
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, new Identifier(MiddleEarth.MOD_ID, name), block);
    }

    public static Block registerStoneBlock(String name, Block block) {
        registerBlockItem(name, block);
        ModItemGroups.STONE_BLOCKS_CONTENTS.add(block.asItem().getDefaultStack());
        return Registry.register(Registries.BLOCK, new Identifier(MiddleEarth.MOD_ID, name), block);
    }

    public static Block registerWoodBlock(String name, Block block) {
        registerBlockItem(name, block);
        ModItemGroups.WOOD_BLOCKS_CONTENTS.add(block.asItem().getDefaultStack());
        return Registry.register(Registries.BLOCK, new Identifier(MiddleEarth.MOD_ID, name), block);
    }

    public static Block registerMiscBlock(String name, Block block) {
        registerBlockItem(name, block);
        ModItemGroups.MISC_BLOCKS_CONTENTS.add(block.asItem().getDefaultStack());
        return Registry.register(Registries.BLOCK, new Identifier(MiddleEarth.MOD_ID, name), block);
    }

    static void registerBlockItem(String name, Block block) {
        var item =  Registry.register(Registries.ITEM, new Identifier(MiddleEarth.MOD_ID, name),
                new BlockItem(block, new FabricItemSettings()));
        Item.BLOCK_ITEMS.put(block, item);
    }

    public static void registerModBlocks() {
        MiddleEarth.LOGGER.debug("Registering ModBlocks for " + MiddleEarth.MOD_ID);
    }
}
