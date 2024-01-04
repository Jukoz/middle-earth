package net.jukoz.me.block;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.block.special.StoneMyceliumBlock;
import net.jukoz.me.block.special.StoolBlock;
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
    public static final Block STONE_PILLAR = registerBlock("stone_pillar",
            new PillarBlock(AbstractBlock.Settings.copy(Blocks.QUARTZ_PILLAR).strength(StoneBlockSets.BRICKS_HARDNESS, StoneBlockSets.BRICKS_BLASTRESISTANCE).sounds(BlockSoundGroup.STONE)));
    public static final Block MOSSY_STONE_PILLAR = registerBlock("mossy_stone_pillar",
            new PillarBlock(AbstractBlock.Settings.copy(STONE_PILLAR).strength(StoneBlockSets.BRICKS_HARDNESS, StoneBlockSets.BRICKS_BLASTRESISTANCE).sounds(BlockSoundGroup.STONE)));
    public static final Block CRACKED_STONE_PILLAR = registerBlock("cracked_stone_pillar",
            new PillarBlock(AbstractBlock.Settings.copy(STONE_PILLAR).strength(StoneBlockSets.BRICKS_HARDNESS, StoneBlockSets.BRICKS_BLASTRESISTANCE).sounds(BlockSoundGroup.STONE)));

    public static final Block DEEPSLATE_PILLAR = registerBlock("deepslate_pillar",
            new PillarBlock(AbstractBlock.Settings.copy(Blocks.QUARTZ_PILLAR).strength(StoneBlockSets.DEEPSLATE_BRICKS_HARDNESS, StoneBlockSets.DEEPSLATE_BRICKS_BLAST_RESISTANCE).sounds(BlockSoundGroup.DEEPSLATE)));
    public static final Block MOSSY_DEEPSLATE_PILLAR = registerBlock("mossy_deepslate_pillar",
            new PillarBlock(AbstractBlock.Settings.copy(DEEPSLATE_PILLAR).strength(StoneBlockSets.DEEPSLATE_BRICKS_HARDNESS, StoneBlockSets.DEEPSLATE_BRICKS_BLAST_RESISTANCE).sounds(BlockSoundGroup.DEEPSLATE)));
    public static final Block CRACKED_DEEPSLATE_PILLAR = registerBlock("cracked_deepslate_pillar",
            new PillarBlock(AbstractBlock.Settings.copy(DEEPSLATE_PILLAR).strength(StoneBlockSets.DEEPSLATE_BRICKS_HARDNESS, StoneBlockSets.DEEPSLATE_BRICKS_BLAST_RESISTANCE).sounds(BlockSoundGroup.DEEPSLATE)));

    public static final Block GONLUIN_PILLAR = registerBlock("gonluin_pillar",
            new PillarBlock(AbstractBlock.Settings.copy(Blocks.QUARTZ_PILLAR).strength(StoneBlockSets.BRICKS_HARDNESS, StoneBlockSets.BRICKS_BLASTRESISTANCE).sounds(BlockSoundGroup.STONE)));

    public static final Block FROZEN_PILLAR = registerBlock("frozen_pillar",
            new PillarBlock(AbstractBlock.Settings.copy(Blocks.QUARTZ_PILLAR).strength(StoneBlockSets.BRICKS_HARDNESS, StoneBlockSets.BRICKS_BLASTRESISTANCE).sounds(BlockSoundGroup.STONE)));

    public static final Block CALCITE_PILLAR = registerBlock("calcite_pillar",
            new PillarBlock(AbstractBlock.Settings.copy(Blocks.QUARTZ_PILLAR).strength(StoneBlockSets.BRICKS_HARDNESS, StoneBlockSets.BRICKS_BLASTRESISTANCE).sounds(BlockSoundGroup.STONE)));
    public static final Block MOSSY_CALCITE_PILLAR = registerBlock("mossy_calcite_pillar",
            new PillarBlock(AbstractBlock.Settings.copy(Blocks.QUARTZ_PILLAR).strength(StoneBlockSets.BRICKS_HARDNESS, StoneBlockSets.BRICKS_BLASTRESISTANCE).sounds(BlockSoundGroup.STONE)));
    public static final Block CRACKED_CALCITE_PILLAR = registerBlock("cracked_calcite_pillar",
            new PillarBlock(AbstractBlock.Settings.copy(Blocks.QUARTZ_PILLAR).strength(StoneBlockSets.BRICKS_HARDNESS, StoneBlockSets.BRICKS_BLASTRESISTANCE).sounds(BlockSoundGroup.STONE)));

    public static final Block LIMESTONE_PILLAR = registerBlock("limestone_pillar",
            new PillarBlock(AbstractBlock.Settings.copy(Blocks.QUARTZ_PILLAR).strength(StoneBlockSets.BRICKS_HARDNESS, StoneBlockSets.BRICKS_BLASTRESISTANCE).sounds(BlockSoundGroup.STONE)));

    public static final Block ANDESITE_PILLAR = registerBlock("andesite_pillar",
            new PillarBlock(AbstractBlock.Settings.copy(Blocks.QUARTZ_PILLAR).strength(StoneBlockSets.BRICKS_HARDNESS, StoneBlockSets.BRICKS_BLASTRESISTANCE).sounds(BlockSoundGroup.STONE)));
    public static final Block MOSSY_ANDESITE_PILLAR = registerBlock("mossy_andesite_pillar",
            new PillarBlock(AbstractBlock.Settings.copy(Blocks.QUARTZ_PILLAR).strength(StoneBlockSets.BRICKS_HARDNESS, StoneBlockSets.BRICKS_BLASTRESISTANCE).sounds(BlockSoundGroup.STONE)));
    public static final Block CRACKED_ANDESITE_PILLAR = registerBlock("cracked_andesite_pillar",
            new PillarBlock(AbstractBlock.Settings.copy(Blocks.QUARTZ_PILLAR).strength(StoneBlockSets.BRICKS_HARDNESS, StoneBlockSets.BRICKS_BLASTRESISTANCE).sounds(BlockSoundGroup.STONE)));

    public static final Block GRANITE_PILLAR = registerBlock("granite_pillar",
            new PillarBlock(AbstractBlock.Settings.copy(Blocks.QUARTZ_PILLAR).strength(StoneBlockSets.BRICKS_HARDNESS, StoneBlockSets.BRICKS_BLASTRESISTANCE).sounds(BlockSoundGroup.STONE)));
    public static final Block MOSSY_GRANITE_PILLAR = registerBlock("mossy_granite_pillar",
            new PillarBlock(AbstractBlock.Settings.copy(Blocks.QUARTZ_PILLAR).strength(StoneBlockSets.BRICKS_HARDNESS, StoneBlockSets.BRICKS_BLASTRESISTANCE).sounds(BlockSoundGroup.STONE)));
    public static final Block CRACKED_GRANITE_PILLAR = registerBlock("cracked_granite_pillar",
            new PillarBlock(AbstractBlock.Settings.copy(Blocks.QUARTZ_PILLAR).strength(StoneBlockSets.BRICKS_HARDNESS, StoneBlockSets.BRICKS_BLASTRESISTANCE).sounds(BlockSoundGroup.STONE)));

    public static final Block DIORITE_PILLAR = registerBlock("diorite_pillar",
            new PillarBlock(AbstractBlock.Settings.copy(Blocks.QUARTZ_PILLAR).strength(StoneBlockSets.BRICKS_HARDNESS, StoneBlockSets.BRICKS_BLASTRESISTANCE).sounds(BlockSoundGroup.STONE)));
    public static final Block MOSSY_DIORITE_PILLAR = registerBlock("mossy_diorite_pillar",
            new PillarBlock(AbstractBlock.Settings.copy(Blocks.QUARTZ_PILLAR).strength(StoneBlockSets.BRICKS_HARDNESS, StoneBlockSets.BRICKS_BLASTRESISTANCE).sounds(BlockSoundGroup.STONE)));
    public static final Block CRACKED_DIORITE_PILLAR = registerBlock("cracked_diorite_pillar",
            new PillarBlock(AbstractBlock.Settings.copy(Blocks.QUARTZ_PILLAR).strength(StoneBlockSets.BRICKS_HARDNESS, StoneBlockSets.BRICKS_BLASTRESISTANCE).sounds(BlockSoundGroup.STONE)));

    public static final Block JADEITE_PILLAR = registerBlock("jadeite_pillar",
            new PillarBlock(AbstractBlock.Settings.copy(Blocks.QUARTZ_PILLAR).strength(StoneBlockSets.BRICKS_HARDNESS, StoneBlockSets.BRICKS_BLASTRESISTANCE).sounds(BlockSoundGroup.STONE)));
    public static final Block CRACKED_JADEITE_PILLAR = registerBlock("cracked_jadeite_pillar",
            new PillarBlock(AbstractBlock.Settings.copy(Blocks.QUARTZ_PILLAR).strength(StoneBlockSets.BRICKS_HARDNESS, StoneBlockSets.BRICKS_BLASTRESISTANCE).sounds(BlockSoundGroup.STONE)));

    public static final Block CHISELED_GONLUIN_BRICKS = registerBlock("chiseled_gonluin_bricks",
            new Block(FabricBlockSettings.copyOf(Blocks.CHISELED_STONE_BRICKS).strength(StoneBlockSets.BRICKS_HARDNESS, StoneBlockSets.BRICKS_BLASTRESISTANCE).sounds(BlockSoundGroup.STONE)));
    public static final Block CHISELED_POLISHED_GONLUIN = registerBlock("chiseled_polished_gonluin",
            new Block(FabricBlockSettings.copyOf(Blocks.CHISELED_STONE_BRICKS).strength(StoneBlockSets.BRICKS_HARDNESS, StoneBlockSets.BRICKS_BLASTRESISTANCE).sounds(BlockSoundGroup.STONE)));

    public static final Block CHISELED_CALCITE_BRICKS = registerBlock("chiseled_calcite_bricks",
            new Block(FabricBlockSettings.copyOf(Blocks.CHISELED_STONE_BRICKS).strength(StoneBlockSets.BRICKS_HARDNESS, StoneBlockSets.BRICKS_BLASTRESISTANCE).sounds(BlockSoundGroup.STONE)));
    public static final Block CHISELED_POLISHED_CALCITE = registerBlock("chiseled_polished_calcite",
            new Block(FabricBlockSettings.copyOf(Blocks.CHISELED_STONE_BRICKS).strength(StoneBlockSets.BRICKS_HARDNESS, StoneBlockSets.BRICKS_BLASTRESISTANCE).sounds(BlockSoundGroup.STONE)));

    public static final Block CHISELED_LIMESTONE_BRICKS = registerBlock("chiseled_limestone_bricks",
            new Block(FabricBlockSettings.copyOf(Blocks.CHISELED_STONE_BRICKS).strength(StoneBlockSets.BRICKS_HARDNESS, StoneBlockSets.BRICKS_BLASTRESISTANCE).sounds(BlockSoundGroup.STONE)));

    public static final Block CHISELED_SMOOTH_STONE = registerBlock("chiseled_smooth_stone",
            new Block(FabricBlockSettings.copyOf(Blocks.CHISELED_STONE_BRICKS).strength(StoneBlockSets.BRICKS_HARDNESS, StoneBlockSets.BRICKS_BLASTRESISTANCE).sounds(BlockSoundGroup.STONE)));

    public static final Block CHISELED_ANDESITE_BRICKS = registerBlock("chiseled_andesite_bricks",
            new Block(FabricBlockSettings.copyOf(Blocks.CHISELED_STONE_BRICKS).strength(StoneBlockSets.BRICKS_HARDNESS, StoneBlockSets.BRICKS_BLASTRESISTANCE).sounds(BlockSoundGroup.STONE)));
    public static final Block CHISELED_POLISHED_ANDESITE = registerBlock("chiseled_polished_andesite",
            new Block(FabricBlockSettings.copyOf(Blocks.CHISELED_STONE_BRICKS).strength(StoneBlockSets.BRICKS_HARDNESS, StoneBlockSets.BRICKS_BLASTRESISTANCE).sounds(BlockSoundGroup.STONE)));
    
    public static final Block CHISELED_GRANITE_BRICKS = registerBlock("chiseled_granite_bricks",
            new Block(FabricBlockSettings.copyOf(Blocks.CHISELED_STONE_BRICKS).strength(StoneBlockSets.BRICKS_HARDNESS, StoneBlockSets.BRICKS_BLASTRESISTANCE).sounds(BlockSoundGroup.STONE)));
    public static final Block CHISELED_POLISHED_GRANITE = registerBlock("chiseled_polished_granite",
            new Block(FabricBlockSettings.copyOf(Blocks.CHISELED_STONE_BRICKS).strength(StoneBlockSets.BRICKS_HARDNESS, StoneBlockSets.BRICKS_BLASTRESISTANCE).sounds(BlockSoundGroup.STONE)));

    public static final Block CHISELED_DIORITE_BRICKS = registerBlock("chiseled_diorite_bricks",
            new Block(FabricBlockSettings.copyOf(Blocks.CHISELED_STONE_BRICKS).strength(StoneBlockSets.BRICKS_HARDNESS, StoneBlockSets.BRICKS_BLASTRESISTANCE).sounds(BlockSoundGroup.STONE)));
    public static final Block CHISELED_POLISHED_DIORITE = registerBlock("chiseled_polished_diorite",
            new Block(FabricBlockSettings.copyOf(Blocks.CHISELED_STONE_BRICKS).strength(StoneBlockSets.BRICKS_HARDNESS, StoneBlockSets.BRICKS_BLASTRESISTANCE).sounds(BlockSoundGroup.STONE)));
    
    public static final Block CHISELED_BASALT_BRICKS = registerBlock("chiseled_basalt_bricks",
            new Block(FabricBlockSettings.copyOf(Blocks.CHISELED_STONE_BRICKS).strength(StoneBlockSets.BRICKS_HARDNESS, StoneBlockSets.BRICKS_BLASTRESISTANCE).sounds(BlockSoundGroup.STONE)));

    //endregion

    public static final Block DRY_DIRT = registerBlock("dry_dirt",
            new Block(FabricBlockSettings.copyOf(Blocks.DIRT).strength(DIRT_STRENGTH).sounds(BlockSoundGroup.GRAVEL)));
    public static final Block DRY_DIRT_SLAB = registerBlock("dry_dirt_slab",
            new SlabBlock(FabricBlockSettings.copyOf(DRY_DIRT)));
    public static final Block DRY_DIRT_STAIRS = registerBlock("dry_dirt_stairs",
            new StairsBlock(DRY_DIRT.getDefaultState(), FabricBlockSettings.copyOf(DRY_DIRT)));
    public static final Block ASHEN_DIRT = registerBlock("ashen_dirt",
            new Block(FabricBlockSettings.copyOf(Blocks.DIRT).strength(DIRT_STRENGTH).sounds(BlockSoundGroup.GRAVEL)));
    public static final Block ASHEN_DIRT_SLAB = registerBlock("ashen_dirt_slab",
            new SlabBlock(FabricBlockSettings.copyOf(ASHEN_DIRT)));
    public static final Block ASHEN_DIRT_STAIRS = registerBlock("ashen_dirt_stairs",
            new StairsBlock(ASHEN_DIRT.getDefaultState(), FabricBlockSettings.copyOf(ASHEN_DIRT)));
    
    public static final Block REED_BLOCK = registerBlock("reed_block",
            new HayBlock(FabricBlockSettings.copyOf(Blocks.HAY_BLOCK).strength(DIRT_STRENGTH).sounds(BlockSoundGroup.GRASS)));
    public static final Block REED_STAIRS = registerBlock("reed_stairs",
            new StairsBlock(REED_BLOCK.getDefaultState(), FabricBlockSettings.copyOf(Blocks.HAY_BLOCK).strength(DIRT_STRENGTH).sounds(BlockSoundGroup.GRASS)));
    public static final Block REED_SLAB = registerBlock("reed_slab",
            new SlabBlock(FabricBlockSettings.copyOf(Blocks.HAY_BLOCK).strength(DIRT_STRENGTH).sounds(BlockSoundGroup.GRASS)));
    public static final Block REED_VERTICAL_SLAB = registerBlock("reed_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(ModBlocks.REED_SLAB)));
    public static final Block REED_WALL = registerBlock("reed_wall",
            new WallBlock(FabricBlockSettings.copyOf(Blocks.HAY_BLOCK).strength(DIRT_STRENGTH).sounds(BlockSoundGroup.GRASS)));
    public static final Block STRAW_BLOCK = registerBlock("straw_block",
            new HayBlock(FabricBlockSettings.copyOf(Blocks.HAY_BLOCK).strength(DIRT_STRENGTH).sounds(BlockSoundGroup.GRASS)));
    public static final Block STRAW_STAIRS = registerBlock("straw_stairs",
            new StairsBlock(STRAW_BLOCK.getDefaultState(), FabricBlockSettings.copyOf(Blocks.HAY_BLOCK).strength(DIRT_STRENGTH).sounds(BlockSoundGroup.GRASS)));
    public static final Block STRAW_SLAB = registerBlock("straw_slab",
            new SlabBlock(FabricBlockSettings.copyOf(Blocks.HAY_BLOCK).strength(DIRT_STRENGTH).sounds(BlockSoundGroup.GRASS)));
    public static final Block STRAW_VERTICAL_SLAB = registerBlock("straw_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(ModBlocks.STRAW_SLAB)));
    public static final Block STRAW_WALL = registerBlock("straw_wall",
            new WallBlock(FabricBlockSettings.copyOf(Blocks.HAY_BLOCK).strength(DIRT_STRENGTH).sounds(BlockSoundGroup.GRASS)));

    //region METAL AND GEMS
    public static final Block RAW_MITHRIL_BLOCK = registerBlock("raw_mithril_block",
            new Block(FabricBlockSettings.copyOf(Blocks.RAW_IRON_BLOCK).strength(4f).requiresTool()));
    public static final Block MITHRIL_BLOCK = registerBlock("mithril_block",
            new Block(FabricBlockSettings.copyOf(Blocks.IRON_BLOCK).strength(6f).requiresTool()));
    public static final Block RAW_TIN_BLOCK = registerBlock("raw_tin_block",
            new Block(FabricBlockSettings.copyOf(Blocks.STONE).strength(2f).requiresTool()));
    public static final Block TIN_BLOCK = registerBlock("tin_block",
            new Block(FabricBlockSettings.copyOf(Blocks.IRON_BLOCK).strength(3f).requiresTool()));
    public static final Block RAW_LEAD_BLOCK = registerBlock("raw_lead_block",
            new Block(FabricBlockSettings.copyOf(Blocks.RAW_IRON_BLOCK).strength(3f).requiresTool()));
    public static final Block LEAD_BLOCK = registerBlock("lead_block",
            new Block(FabricBlockSettings.copyOf(Blocks.IRON_BLOCK).strength(2f).requiresTool()));
    public static final Block RAW_SILVER_BLOCK = registerBlock("raw_silver_block",
            new Block(FabricBlockSettings.copyOf(Blocks.RAW_IRON_BLOCK).strength(3f).requiresTool()));
    public static final Block SILVER_BLOCK = registerBlock("silver_block",
            new Block(FabricBlockSettings.copyOf(Blocks.IRON_BLOCK).strength(4f).requiresTool()));

    public static final Block CUT_LEAD = registerBlock("cut_lead",
            new Block(FabricBlockSettings.copyOf(Blocks.CUT_COPPER).sounds(BlockSoundGroup.COPPER)));
    public static final Block CUT_LEAD_STAIRS = registerBlock("cut_lead_stairs",
            new StairsBlock(REED_BLOCK.getDefaultState(), FabricBlockSettings.copyOf(Blocks.CUT_COPPER).sounds(BlockSoundGroup.COPPER)));
    public static final Block CUT_LEAD_SLAB = registerBlock("cut_lead_slab",
            new SlabBlock(FabricBlockSettings.copyOf(Blocks.CUT_COPPER).sounds(BlockSoundGroup.COPPER)));
    public static final Block CUT_LEAD_VERTICAL_SLAB = registerBlock("cut_lead_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(ModBlocks.CUT_LEAD_SLAB)));

    public static final Block SAPPHIRE_BLOCK = registerBlock("sapphire_block",
            new AmethystBlock(FabricBlockSettings.copyOf(Blocks.AMETHYST_BLOCK)));
    public static final Block BUDDING_SAPPHIRE = registerBlock("budding_sapphire",
            new SapphireBuddingGemBlock(FabricBlockSettings.copyOf(Blocks.BUDDING_AMETHYST)));
    public static final Block SAPPHIRE_CLUSTER = registerBlock("sapphire_cluster",
            new AmethystClusterBlock(7,3, FabricBlockSettings.copyOf(Blocks.AMETHYST_CLUSTER)));
    public static final Block LARGE_SAPPHIRE_BUD = registerBlock("large_sapphire_bud",
            new AmethystClusterBlock(5,3, FabricBlockSettings.copyOf(ModBlocks.SAPPHIRE_CLUSTER).sounds(BlockSoundGroup.LARGE_AMETHYST_BUD)));
    public static final Block MEDIUM_SAPPHIRE_BUD = registerBlock("medium_sapphire_bud",
            new AmethystClusterBlock(4,3, FabricBlockSettings.copyOf(ModBlocks.SAPPHIRE_CLUSTER).sounds(BlockSoundGroup.MEDIUM_AMETHYST_BUD)));
    public static final Block SMALL_SAPPHIRE_BUD = registerBlock("small_sapphire_bud",
            new AmethystClusterBlock(3,4, FabricBlockSettings.copyOf(ModBlocks.SAPPHIRE_CLUSTER).sounds(BlockSoundGroup.SMALL_AMETHYST_BUD)));

    public static final Block RED_AGATE_BLOCK = registerBlock("red_agate_block",
            new AmethystBlock(FabricBlockSettings.copyOf(Blocks.AMETHYST_BLOCK)));
    public static final Block BUDDING_RED_AGATE = registerBlock("budding_red_agate",
            new RedAgateBuddingGemBlock(FabricBlockSettings.copyOf(Blocks.BUDDING_AMETHYST)));
    public static final Block RED_AGATE_CLUSTER = registerBlock("red_agate_cluster",
            new AmethystClusterBlock(7,3, FabricBlockSettings.copyOf(Blocks.AMETHYST_CLUSTER)));
    public static final Block LARGE_RED_AGATE_BUD = registerBlock("large_red_agate_bud",
            new AmethystClusterBlock(5,3, FabricBlockSettings.copyOf(ModBlocks.RED_AGATE_CLUSTER).sounds(BlockSoundGroup.LARGE_AMETHYST_BUD)));
    public static final Block MEDIUM_RED_AGATE_BUD = registerBlock("medium_red_agate_bud",
            new AmethystClusterBlock(4,3, FabricBlockSettings.copyOf(ModBlocks.RED_AGATE_CLUSTER).sounds(BlockSoundGroup.MEDIUM_AMETHYST_BUD)));
    public static final Block SMALL_RED_AGATE_BUD = registerBlock("small_red_agate_bud",
            new AmethystClusterBlock(3,4, FabricBlockSettings.copyOf(ModBlocks.RED_AGATE_CLUSTER).sounds(BlockSoundGroup.SMALL_AMETHYST_BUD)));

    public static final Block CITRINE_BLOCK = registerBlock("citrine_block",
            new AmethystBlock(FabricBlockSettings.copyOf(Blocks.AMETHYST_BLOCK)));
    public static final Block BUDDING_CITRINE = registerBlock("budding_citrine",
            new CitrineBuddingGemBlock(FabricBlockSettings.copyOf(Blocks.BUDDING_AMETHYST)));
    public static final Block CITRINE_CLUSTER = registerBlock("citrine_cluster",
            new AmethystClusterBlock(7,3, FabricBlockSettings.copyOf(Blocks.AMETHYST_CLUSTER)));
    public static final Block LARGE_CITRINE_BUD = registerBlock("large_citrine_bud",
            new AmethystClusterBlock(5,3, FabricBlockSettings.copyOf(ModBlocks.CITRINE_CLUSTER).sounds(BlockSoundGroup.LARGE_AMETHYST_BUD)));
    public static final Block MEDIUM_CITRINE_BUD = registerBlock("medium_citrine_bud",
            new AmethystClusterBlock(4,3, FabricBlockSettings.copyOf(ModBlocks.CITRINE_CLUSTER).sounds(BlockSoundGroup.MEDIUM_AMETHYST_BUD)));
    public static final Block SMALL_CITRINE_BUD = registerBlock("small_citrine_bud",
            new AmethystClusterBlock(3,4, FabricBlockSettings.copyOf(ModBlocks.CITRINE_CLUSTER).sounds(BlockSoundGroup.SMALL_AMETHYST_BUD)));

    public static final Block STONE_TRAPDOOR = registerBlock("stone_trapdoor",
            new TrapdoorBlock(FabricBlockSettings.copyOf(Blocks.OAK_TRAPDOOR).strength(StoneBlockSets.BRICKS_HARDNESS, StoneBlockSets.BRICKS_BLASTRESISTANCE).sounds(BlockSoundGroup.STONE), BlockSetType.STONE));
    public static final Block BLACKSTONE_TRAPDOOR = registerBlock("blackstone_trapdoor",
            new TrapdoorBlock(FabricBlockSettings.copyOf(Blocks.OAK_TRAPDOOR).strength(StoneBlockSets.BRICKS_HARDNESS, StoneBlockSets.BRICKS_BLASTRESISTANCE).sounds(BlockSoundGroup.STONE), BlockSetType.POLISHED_BLACKSTONE));

    public static final Block SILVERS_BARS = registerBlock("silver_bars",
            new PaneBlock(FabricBlockSettings.copyOf(Blocks.IRON_BARS).sounds(BlockSoundGroup.METAL)));
    //endregion

    //region GLASS
    public static final Block LEAD_GLASS = registerBlock("lead_glass",
            new GlassBlock(FabricBlockSettings.copyOf(Blocks.GLASS)));
    public static final Block BLACK_STAINED_LEAD_GLASS = registerBlock("black_stained_lead_glass",
            new GlassBlock(FabricBlockSettings.copyOf(Blocks.GLASS)));
    public static final Block BLUE_STAINED_LEAD_GLASS = registerBlock("blue_stained_lead_glass",
            new GlassBlock(FabricBlockSettings.copyOf(Blocks.GLASS)));
    public static final Block BROWN_STAINED_LEAD_GLASS = registerBlock("brown_stained_lead_glass",
            new GlassBlock(FabricBlockSettings.copyOf(Blocks.GLASS)));
    public static final Block CYAN_STAINED_LEAD_GLASS = registerBlock("cyan_stained_lead_glass",
            new GlassBlock(FabricBlockSettings.copyOf(Blocks.GLASS)));
    public static final Block GRAY_STAINED_LEAD_GLASS = registerBlock("gray_stained_lead_glass",
            new GlassBlock(FabricBlockSettings.copyOf(Blocks.GLASS)));
    public static final Block GREEN_STAINED_LEAD_GLASS = registerBlock("green_stained_lead_glass",
            new GlassBlock(FabricBlockSettings.copyOf(Blocks.GLASS)));
    public static final Block LIGHT_BLUE_STAINED_LEAD_GLASS = registerBlock("light_blue_stained_lead_glass",
            new GlassBlock(FabricBlockSettings.copyOf(Blocks.GLASS)));
    public static final Block LIGHT_GRAY_STAINED_LEAD_GLASS = registerBlock("light_gray_stained_lead_glass",
            new GlassBlock(FabricBlockSettings.copyOf(Blocks.GLASS)));
    public static final Block LIME_STAINED_LEAD_GLASS = registerBlock("lime_stained_lead_glass",
            new GlassBlock(FabricBlockSettings.copyOf(Blocks.GLASS)));
    public static final Block MAGENTA_STAINED_LEAD_GLASS = registerBlock("magenta_stained_lead_glass",
            new GlassBlock(FabricBlockSettings.copyOf(Blocks.GLASS)));
    public static final Block ORANGE_STAINED_LEAD_GLASS = registerBlock("orange_stained_lead_glass",
            new GlassBlock(FabricBlockSettings.copyOf(Blocks.GLASS)));
    public static final Block PINK_STAINED_LEAD_GLASS = registerBlock("pink_stained_lead_glass",
            new GlassBlock(FabricBlockSettings.copyOf(Blocks.GLASS)));
    public static final Block PURPLE_STAINED_LEAD_GLASS = registerBlock("purple_stained_lead_glass",
            new GlassBlock(FabricBlockSettings.copyOf(Blocks.GLASS)));
    public static final Block RED_STAINED_LEAD_GLASS = registerBlock("red_stained_lead_glass",
            new GlassBlock(FabricBlockSettings.copyOf(Blocks.GLASS)));
    public static final Block WHITE_STAINED_LEAD_GLASS = registerBlock("white_stained_lead_glass",
            new GlassBlock(FabricBlockSettings.copyOf(Blocks.GLASS)));
    public static final Block YELLOW_STAINED_LEAD_GLASS = registerBlock("yellow_stained_lead_glass",
            new GlassBlock(FabricBlockSettings.copyOf(Blocks.GLASS)));
    public static final Block LEAD_GLASS_PANE = registerBlock("lead_glass_pane",
            new PaneBlock(FabricBlockSettings.copyOf(Blocks.GLASS_PANE)));
    public static final Block BLACK_STAINED_LEAD_GLASS_PANE = registerBlock("black_stained_lead_glass_pane",
            new PaneBlock(FabricBlockSettings.copyOf(Blocks.GLASS_PANE)));
    public static final Block BLUE_STAINED_LEAD_GLASS_PANE = registerBlock("blue_stained_lead_glass_pane",
            new PaneBlock(FabricBlockSettings.copyOf(Blocks.GLASS_PANE)));
    public static final Block BROWN_STAINED_LEAD_GLASS_PANE = registerBlock("brown_stained_lead_glass_pane",
            new PaneBlock(FabricBlockSettings.copyOf(Blocks.GLASS_PANE)));
    public static final Block CYAN_STAINED_LEAD_GLASS_PANE = registerBlock("cyan_stained_lead_glass_pane",
            new PaneBlock(FabricBlockSettings.copyOf(Blocks.GLASS_PANE)));
    public static final Block GRAY_STAINED_LEAD_GLASS_PANE = registerBlock("gray_stained_lead_glass_pane",
            new PaneBlock(FabricBlockSettings.copyOf(Blocks.GLASS_PANE)));
    public static final Block GREEN_STAINED_LEAD_GLASS_PANE = registerBlock("green_stained_lead_glass_pane",
            new PaneBlock(FabricBlockSettings.copyOf(Blocks.GLASS_PANE)));
    public static final Block LIGHT_BLUE_STAINED_LEAD_GLASS_PANE = registerBlock("light_blue_stained_lead_glass_pane",
            new PaneBlock(FabricBlockSettings.copyOf(Blocks.GLASS_PANE)));
    public static final Block LIGHT_GRAY_STAINED_LEAD_GLASS_PANE = registerBlock("light_gray_stained_lead_glass_pane",
            new PaneBlock(FabricBlockSettings.copyOf(Blocks.GLASS_PANE)));
    public static final Block LIME_STAINED_LEAD_GLASS_PANE = registerBlock("lime_stained_lead_glass_pane",
            new PaneBlock(FabricBlockSettings.copyOf(Blocks.GLASS_PANE)));
    public static final Block MAGENTA_STAINED_LEAD_GLASS_PANE = registerBlock("magenta_stained_lead_glass_pane",
            new PaneBlock(FabricBlockSettings.copyOf(Blocks.GLASS_PANE)));
    public static final Block ORANGE_STAINED_LEAD_GLASS_PANE = registerBlock("orange_stained_lead_glass_pane",
            new PaneBlock(FabricBlockSettings.copyOf(Blocks.GLASS_PANE)));
    public static final Block PINK_STAINED_LEAD_GLASS_PANE = registerBlock("pink_stained_lead_glass_pane",
            new PaneBlock(FabricBlockSettings.copyOf(Blocks.GLASS_PANE)));
    public static final Block PURPLE_STAINED_LEAD_GLASS_PANE = registerBlock("purple_stained_lead_glass_pane",
            new PaneBlock(FabricBlockSettings.copyOf(Blocks.GLASS_PANE)));
    public static final Block RED_STAINED_LEAD_GLASS_PANE = registerBlock("red_stained_lead_glass_pane",
            new PaneBlock(FabricBlockSettings.copyOf(Blocks.GLASS_PANE)));
    public static final Block WHITE_STAINED_LEAD_GLASS_PANE = registerBlock("white_stained_lead_glass_pane",
            new PaneBlock(FabricBlockSettings.copyOf(Blocks.GLASS_PANE)));
    public static final Block YELLOW_STAINED_LEAD_GLASS_PANE = registerBlock("yellow_stained_lead_glass_pane",
            new PaneBlock(FabricBlockSettings.copyOf(Blocks.GLASS_PANE)));
    //endregion

    public static final Block RIVER_SAND = registerBlock("river_sand",
            new FallingBlock(FabricBlockSettings.copyOf(Blocks.SAND).sounds(BlockSoundGroup.SAND)));

    public static final Block STONE_MYCELIUM = registerBlock("stone_mycelium",
            new StoneMyceliumBlock(FabricBlockSettings.copyOf(Blocks.STONE).ticksRandomly()));

    public static final Block ASH_BLOCK = registerBlock("ash_block",
            new FallingBlock(FabricBlockSettings.copyOf(Blocks.SAND).strength(DIRT_STRENGTH).sounds(BlockSoundGroup.SAND)));

    //region VANILLA SLABS
    public static final Block DIRT_SLAB = registerBlock("dirt_slab",
            new SlabBlock(FabricBlockSettings.copyOf(Blocks.DIRT)));
    public static final Block COARSE_DIRT_SLAB = registerBlock("coarse_dirt_slab",
            new SlabBlock(FabricBlockSettings.copyOf(Blocks.COARSE_DIRT)));
    public static final Block ROOTED_DIRT_SLAB = registerBlock("rooted_dirt_slab",
            new SlabBlock(FabricBlockSettings.copyOf(Blocks.ROOTED_DIRT)));
    public static final Block MUD_SLAB = registerBlock("mud_slab",
            new SlabBlock(FabricBlockSettings.copyOf(Blocks.ROOTED_DIRT)));

    public static final Block BLACK_WOOL_SLAB = registerBlock("black_wool_slab",
            new SlabBlock(FabricBlockSettings.copyOf(Blocks.WHITE_WOOL)));
    public static final Block BLUE_WOOL_SLAB = registerBlock("blue_wool_slab",
            new SlabBlock(FabricBlockSettings.copyOf(Blocks.WHITE_WOOL)));
    public static final Block BROWN_WOOL_SLAB = registerBlock("brown_wool_slab",
            new SlabBlock(FabricBlockSettings.copyOf(Blocks.WHITE_WOOL)));
    public static final Block CYAN_WOOL_SLAB = registerBlock("cyan_wool_slab",
            new SlabBlock(FabricBlockSettings.copyOf(Blocks.WHITE_WOOL)));
    public static final Block GRAY_WOOL_SLAB = registerBlock("gray_wool_slab",
            new SlabBlock(FabricBlockSettings.copyOf(Blocks.WHITE_WOOL)));
    public static final Block GREEN_WOOL_SLAB = registerBlock("green_wool_slab",
            new SlabBlock(FabricBlockSettings.copyOf(Blocks.WHITE_WOOL)));
    public static final Block LIGHT_BLUE_WOOL_SLAB = registerBlock("light_blue_wool_slab",
            new SlabBlock(FabricBlockSettings.copyOf(Blocks.WHITE_WOOL)));
    public static final Block LIGHT_GRAY_WOOL_SLAB = registerBlock("light_gray_wool_slab",
            new SlabBlock(FabricBlockSettings.copyOf(Blocks.WHITE_WOOL)));
    public static final Block LIME_WOOL_SLAB = registerBlock("lime_wool_slab",
            new SlabBlock(FabricBlockSettings.copyOf(Blocks.WHITE_WOOL)));
    public static final Block MAGENTA_WOOL_SLAB = registerBlock("magenta_wool_slab",
            new SlabBlock(FabricBlockSettings.copyOf(Blocks.WHITE_WOOL)));
    public static final Block ORANGE_WOOL_SLAB = registerBlock("orange_wool_slab",
            new SlabBlock(FabricBlockSettings.copyOf(Blocks.WHITE_WOOL)));
    public static final Block PINK_WOOL_SLAB = registerBlock("pink_wool_slab",
            new SlabBlock(FabricBlockSettings.copyOf(Blocks.WHITE_WOOL)));
    public static final Block PURPLE_WOOL_SLAB = registerBlock("purple_wool_slab",
            new SlabBlock(FabricBlockSettings.copyOf(Blocks.WHITE_WOOL)));
    public static final Block RED_WOOL_SLAB = registerBlock("red_wool_slab",
            new SlabBlock(FabricBlockSettings.copyOf(Blocks.WHITE_WOOL)));
    public static final Block WHITE_WOOL_SLAB = registerBlock("white_wool_slab",
            new SlabBlock(FabricBlockSettings.copyOf(Blocks.WHITE_WOOL)));
    public static final Block YELLOW_WOOL_SLAB = registerBlock("yellow_wool_slab",
            new SlabBlock(FabricBlockSettings.copyOf(Blocks.WHITE_WOOL)));
    //endregion

    //region VANILLA VERTICAL SLABS
    public static final Block OAK_VERTICAL_SLAB = registerBlock("oak_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.OAK_SLAB)));
    public static final Block SPRUCE_VERTICAL_SLAB = registerBlock("spruce_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.SPRUCE_SLAB)));
    public static final Block BIRCH_VERTICAL_SLAB = registerBlock("birch_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.BIRCH_SLAB)));
    public static final Block JUNGLE_VERTICAL_SLAB = registerBlock("jungle_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.JUNGLE_SLAB)));
    public static final Block ACACIA_VERTICAL_SLAB = registerBlock("acacia_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.ACACIA_SLAB)));
    public static final Block DARK_OAK_VERTICAL_SLAB = registerBlock("dark_oak_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.DARK_OAK_SLAB)));
    public static final Block MANGROVE_VERTICAL_SLAB = registerBlock("mangrove_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.MANGROVE_SLAB)));
    public static final Block CHERRY_VERTICAL_SLAB = registerBlock("cherry_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.CHERRY_SLAB)));
    public static final Block BAMBOO_VERTICAL_SLAB = registerBlock("bamboo_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.BAMBOO_SLAB)));
    public static final Block CRIMSON_VERTICAL_SLAB = registerBlock("crimson_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.CRIMSON_SLAB)));
    public static final Block WARPED_VERTICAL_SLAB = registerBlock("warped_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.WARPED_SLAB)));

    public static final Block STONE_VERTICAL_SLAB = registerBlock("stone_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.STONE_SLAB)));
    public static final Block COBBLESTONE_VERTICAL_SLAB = registerBlock("cobblestone_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.COBBLESTONE_SLAB)));
    public static final Block MOSSY_COBBLESTONE_VERTICAL_SLAB = registerBlock("mossy_cobblestone_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.MOSSY_COBBLESTONE_SLAB)));
    public static final Block SMOOTH_STONE_VERTICAL_SLAB = registerBlock("smooth_stone_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.SMOOTH_STONE_SLAB)));
    public static final Block STONE_BRICK_VERTICAL_SLAB = registerBlock("stone_brick_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.STONE_BRICK_SLAB)));
    public static final Block MOSSY_STONE_BRICK_VERTICAL_SLAB = registerBlock("mossy_stone_brick_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.MOSSY_STONE_BRICK_SLAB)));
    public static final Block GRANITE_VERTICAL_SLAB = registerBlock("granite_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.GRANITE_SLAB)));
    public static final Block POLISHED_GRANITE_VERTICAL_SLAB = registerBlock("polished_granite_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.POLISHED_GRANITE_SLAB)));
    public static final Block DIORITE_VERTICAL_SLAB = registerBlock("diorite_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.DIORITE_SLAB)));
    public static final Block POLISHED_DIORITE_VERTICAL_SLAB = registerBlock("polished_diorite_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.POLISHED_DIORITE_SLAB)));
    public static final Block ANDESITE_VERTICAL_SLAB = registerBlock("andesite_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.ANDESITE_SLAB)));
    public static final Block POLISHED_ANDESITE_VERTICAL_SLAB = registerBlock("polished_andesite_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.POLISHED_ANDESITE_SLAB)));
    public static final Block COBBLED_DEEPSLATE_VERTICAL_SLAB = registerBlock("cobbled_deepslate_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.COBBLED_DEEPSLATE_SLAB)));
    public static final Block POLISHED_DEEPSLATE_VERTICAL_SLAB = registerBlock("polished_deepslate_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.POLISHED_DEEPSLATE_SLAB)));
    public static final Block DEEPSLATE_BRICK_VERTICAL_SLAB = registerBlock("deepslate_brick_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.DEEPSLATE_BRICK_SLAB)));
    public static final Block DEEPSLATE_TILE_VERTICAL_SLAB = registerBlock("deepslate_tile_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.DEEPSLATE_TILE_SLAB)));
    public static final Block BRICK_VERTICAL_SLAB = registerBlock("brick_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.BRICK_SLAB)));
    public static final Block MUD_BRICK_VERTICAL_SLAB = registerBlock("mud_brick_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.MUD_BRICK_SLAB)));
    public static final Block SANDSTONE_VERTICAL_SLAB = registerBlock("sandstone_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.SANDSTONE_SLAB)));
    public static final Block SMOOTH_SANDSTONE_VERTICAL_SLAB = registerBlock("smooth_sandstone_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.SMOOTH_SANDSTONE_SLAB)));
    public static final Block CUT_SANDSTONE_VERTICAL_SLAB = registerBlock("cut_sandstone_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.CUT_SANDSTONE_SLAB)));
    public static final Block RED_SANDSTONE_VERTICAL_SLAB = registerBlock("red_sandstone_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.RED_SANDSTONE_SLAB)));
    public static final Block SMOOTH_RED_SANDSTONE_VERTICAL_SLAB = registerBlock("smooth_red_sandstone_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.SMOOTH_RED_SANDSTONE_SLAB)));
    public static final Block CUT_RED_SANDSTONE_VERTICAL_SLAB = registerBlock("cut_red_sandstone_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.CUT_RED_SANDSTONE_SLAB)));
    public static final Block PRISMARINE_VERTICAL_SLAB = registerBlock("prismarine_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.PRISMARINE_SLAB)));
    public static final Block PRISMARINE_BRICK_VERTICAL_SLAB = registerBlock("prismarine_brick_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.PRISMARINE_BRICK_SLAB)));
    public static final Block DARK_PRISMARINE_VERTICAL_SLAB = registerBlock("dark_prismarine_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.DARK_PRISMARINE_SLAB)));
    public static final Block NETHER_BRICK_VERTICAL_SLAB = registerBlock("nether_brick_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.NETHER_BRICK_SLAB)));
    public static final Block RED_NETHER_BRICK_VERTICAL_SLAB = registerBlock("red_nether_brick_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.RED_NETHER_BRICK_SLAB)));
    public static final Block BLACKSTONE_VERTICAL_SLAB = registerBlock("blackstone_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.BLACKSTONE_SLAB)));
    public static final Block POLISHED_BLACKSTONE_VERTICAL_SLAB = registerBlock("polished_blackstone_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.POLISHED_BLACKSTONE_SLAB)));
    public static final Block POLISHED_BLACKSTONE_BRICK_VERTICAL_SLAB = registerBlock("polished_blackstone_brick_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.POLISHED_BLACKSTONE_BRICK_SLAB)));
    public static final Block END_STONE_BRICK_VERTICAL_SLAB = registerBlock("end_stone_brick_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.END_STONE_BRICK_SLAB)));
    public static final Block PURPUR_VERTICAL_SLAB = registerBlock("purpur_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.PURPUR_SLAB)));
    public static final Block QUARTZ_VERTICAL_SLAB = registerBlock("quartz_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.QUARTZ_SLAB)));
    public static final Block SMOOTH_QUARTZ_VERTICAL_SLAB = registerBlock("smooth_quartz_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.SMOOTH_QUARTZ_SLAB)));
    public static final Block CUT_COPPER_VERTICAL_SLAB = registerBlock("cut_copper_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.CUT_COPPER_SLAB)));
    public static final Block EXPOSED_CUT_COPPER_VERTICAL_SLAB = registerBlock("exposed_cut_copper_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.EXPOSED_CUT_COPPER_SLAB)));
    public static final Block WEATHERED_CUT_COPPER_VERTICAL_SLAB = registerBlock("weathered_cut_copper_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.WEATHERED_CUT_COPPER_SLAB)));
    public static final Block OXIDIZED_CUT_COPPER_VERTICAL_SLAB = registerBlock("oxidized_cut_copper_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.OXIDIZED_CUT_COPPER_SLAB)));
    public static final Block WAXED_CUT_COPPER_VERTICAL_SLAB = registerBlock("waxed_cut_copper_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.WAXED_CUT_COPPER_SLAB)));
    public static final Block WAXED_EXPOSED_CUT_COPPER_VERTICAL_SLAB = registerBlock("waxed_exposed_cut_copper_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.WAXED_EXPOSED_CUT_COPPER_SLAB)));
    public static final Block WAXED_WEATHERED_CUT_COPPER_VERTICAL_SLAB = registerBlock("waxed_weathered_cut_copper_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.WAXED_WEATHERED_CUT_COPPER_SLAB)));
    public static final Block WAXED_OXIDIZED_CUT_COPPER_VERTICAL_SLAB = registerBlock("waxed_oxidized_cut_copper_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.WAXED_OXIDIZED_CUT_COPPER_SLAB)));

    public static final Block BLACK_WOOL_VERTICAL_SLAB = registerBlock("black_wool_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.WHITE_WOOL)));
    public static final Block BLUE_WOOL_VERTICAL_SLAB = registerBlock("blue_wool_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.WHITE_WOOL)));
    public static final Block BROWN_WOOL_VERTICAL_SLAB = registerBlock("brown_wool_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.WHITE_WOOL)));
    public static final Block CYAN_WOOL_VERTICAL_SLAB = registerBlock("cyan_wool_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.WHITE_WOOL)));
    public static final Block GRAY_WOOL_VERTICAL_SLAB = registerBlock("gray_wool_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.WHITE_WOOL)));
    public static final Block GREEN_WOOL_VERTICAL_SLAB = registerBlock("green_wool_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.WHITE_WOOL)));
    public static final Block LIGHT_BLUE_WOOL_VERTICAL_SLAB = registerBlock("light_blue_wool_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.WHITE_WOOL)));
    public static final Block LIGHT_GRAY_WOOL_VERTICAL_SLAB = registerBlock("light_gray_wool_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.WHITE_WOOL)));
    public static final Block LIME_WOOL_VERTICAL_SLAB = registerBlock("lime_wool_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.WHITE_WOOL)));
    public static final Block MAGENTA_WOOL_VERTICAL_SLAB = registerBlock("magenta_wool_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.WHITE_WOOL)));
    public static final Block ORANGE_WOOL_VERTICAL_SLAB = registerBlock("orange_wool_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.WHITE_WOOL)));
    public static final Block PINK_WOOL_VERTICAL_SLAB = registerBlock("pink_wool_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.WHITE_WOOL)));
    public static final Block PURPLE_WOOL_VERTICAL_SLAB = registerBlock("purple_wool_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.WHITE_WOOL)));
    public static final Block RED_WOOL_VERTICAL_SLAB = registerBlock("red_wool_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.WHITE_WOOL)));
    public static final Block WHITE_WOOL_VERTICAL_SLAB = registerBlock("white_wool_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.WHITE_WOOL)));
    public static final Block YELLOW_WOOL_VERTICAL_SLAB = registerBlock("yellow_wool_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.WHITE_WOOL)));
    //endregion

    //region VANILLA STAIRS
    public static final Block DIRT_STAIRS = registerBlock("dirt_stairs",
            new StairsBlock(Blocks.DIRT.getDefaultState(), FabricBlockSettings.copyOf(Blocks.DIRT)));
    public static final Block COARSE_DIRT_STAIRS = registerBlock("coarse_dirt_stairs",
            new StairsBlock(Blocks.COARSE_DIRT.getDefaultState(), FabricBlockSettings.copyOf(Blocks.COARSE_DIRT)));
    public static final Block MUD_STAIRS = registerBlock("mud_stairs",
            new StairsBlock(Blocks.MUD.getDefaultState(), FabricBlockSettings.copyOf(Blocks.MUD)));

    public static final Block BLACK_WOOL_STAIRS = registerBlock("black_wool_stairs",
            new StairsBlock(Blocks.BLACK_WOOL.getDefaultState(), FabricBlockSettings.copyOf(Blocks.WHITE_WOOL)));
    public static final Block BLUE_WOOL_STAIRS = registerBlock("blue_wool_stairs",
            new StairsBlock(Blocks.BLACK_WOOL.getDefaultState(), FabricBlockSettings.copyOf(Blocks.WHITE_WOOL)));
    public static final Block BROWN_WOOL_STAIRS = registerBlock("brown_wool_stairs",
            new StairsBlock(Blocks.BLACK_WOOL.getDefaultState(), FabricBlockSettings.copyOf(Blocks.WHITE_WOOL)));
    public static final Block CYAN_WOOL_STAIRS = registerBlock("cyan_wool_stairs",
            new StairsBlock(Blocks.BLACK_WOOL.getDefaultState(), FabricBlockSettings.copyOf(Blocks.WHITE_WOOL)));
    public static final Block GRAY_WOOL_STAIRS = registerBlock("gray_wool_stairs",
            new StairsBlock(Blocks.BLACK_WOOL.getDefaultState(), FabricBlockSettings.copyOf(Blocks.WHITE_WOOL)));
    public static final Block GREEN_WOOL_STAIRS = registerBlock("green_wool_stairs",
            new StairsBlock(Blocks.BLACK_WOOL.getDefaultState(), FabricBlockSettings.copyOf(Blocks.WHITE_WOOL)));
    public static final Block LIGHT_BLUE_WOOL_STAIRS = registerBlock("light_blue_wool_stairs",
            new StairsBlock(Blocks.BLACK_WOOL.getDefaultState(), FabricBlockSettings.copyOf(Blocks.WHITE_WOOL)));
    public static final Block LIGHT_GRAY_WOOL_STAIRS = registerBlock("light_gray_wool_stairs",
            new StairsBlock(Blocks.BLACK_WOOL.getDefaultState(), FabricBlockSettings.copyOf(Blocks.WHITE_WOOL)));
    public static final Block LIME_WOOL_STAIRS = registerBlock("lime_wool_stairs",
            new StairsBlock(Blocks.BLACK_WOOL.getDefaultState(), FabricBlockSettings.copyOf(Blocks.WHITE_WOOL)));
    public static final Block MAGENTA_WOOL_STAIRS = registerBlock("magenta_wool_stairs",
            new StairsBlock(Blocks.BLACK_WOOL.getDefaultState(), FabricBlockSettings.copyOf(Blocks.WHITE_WOOL)));
    public static final Block ORANGE_WOOL_STAIRS = registerBlock("orange_wool_stairs",
            new StairsBlock(Blocks.BLACK_WOOL.getDefaultState(), FabricBlockSettings.copyOf(Blocks.WHITE_WOOL)));
    public static final Block PINK_WOOL_STAIRS = registerBlock("pink_wool_stairs",
            new StairsBlock(Blocks.BLACK_WOOL.getDefaultState(), FabricBlockSettings.copyOf(Blocks.WHITE_WOOL)));
    public static final Block PURPLE_WOOL_STAIRS = registerBlock("purple_wool_stairs",
            new StairsBlock(Blocks.BLACK_WOOL.getDefaultState(), FabricBlockSettings.copyOf(Blocks.WHITE_WOOL)));
    public static final Block RED_WOOL_STAIRS = registerBlock("red_wool_stairs",
            new StairsBlock(Blocks.BLACK_WOOL.getDefaultState(), FabricBlockSettings.copyOf(Blocks.WHITE_WOOL)));
    public static final Block WHITE_WOOL_STAIRS = registerBlock("white_wool_stairs",
            new StairsBlock(Blocks.BLACK_WOOL.getDefaultState(), FabricBlockSettings.copyOf(Blocks.WHITE_WOOL)));
    public static final Block YELLOW_WOOL_STAIRS = registerBlock("yellow_wool_stairs",
            new StairsBlock(Blocks.BLACK_WOOL.getDefaultState(), FabricBlockSettings.copyOf(Blocks.WHITE_WOOL)));
    //endregion

    //region VANILLA LAYERS
    public static final Block GRAVEL_LAYERS = registerBlock("gravel_layers",
            new SnowBlock(FabricBlockSettings.copyOf(Blocks.GRAVEL)));
    public static final Block SAND_LAYERS = registerBlock("sand_layers",
            new SnowBlock(FabricBlockSettings.copyOf(Blocks.GRAVEL)));
    //endregion


    public static Block registerBlock(String name, Block block) {
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, new Identifier(MiddleEarth.MOD_ID, name), block);
    }

    static void registerBlockItem(String name, Block block) {
        var item =  Registry.register(Registries.ITEM, new Identifier(MiddleEarth.MOD_ID, name),
                new BlockItem(block, new FabricItemSettings()));
        Item.BLOCK_ITEMS.put(block, item);
        ModItemGroups.BLOCKS_CONTENTS.add(item.getDefaultStack());
    }

    public static void registerModBlocks() {
        MiddleEarth.LOGGER.debug("Registering ModBlocks for " + MiddleEarth.MOD_ID);
    }
}
