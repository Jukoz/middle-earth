package net.jukoz.me.block;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.block.special.LayersBlock;
import net.jukoz.me.block.special.StoneMyceliumBlock;
import net.jukoz.me.block.special.VerticalSlabBlock;
import net.jukoz.me.block.special.gemstones.CustomBuddingGemBlock;
import net.jukoz.me.datageneration.content.loot_tables.BlockDrops;
import net.jukoz.me.item.utils.ModItemGroups;
import net.jukoz.me.utils.LoggerUtil;
import net.minecraft.block.*;
import net.minecraft.entity.EntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;

import java.util.List;
import java.util.logging.Logger;

public class ModBlocks {
    public static final float DIRT_STRENGTH = 0.6f;
    public static final float SLAB_RESISTANCE = 6.0f; // From explosions

    //TODO Second vertical slab model for sandstone, pillars, etc
    //TODO Hobbit Window
    //TODO Buds Loot Tables
    //TODO Wheatgrass grayscale
    //TODO Pedestal Block
    //TODO Stone Lectern
    //TODO Tall Shrooms Placement like plant rn no good

    //region STONE PILLARS AND CHISELED
    public static final Block STONE_PILLAR = registerStoneBlock("stone_pillar",
            new PillarBlock(AbstractBlock.Settings.copy(Blocks.QUARTZ_PILLAR).strength(StoneBlockSets.BRICKS_HARDNESS, StoneBlockSets.BRICKS_BLASTRESISTANCE).sounds(BlockSoundGroup.STONE)),true);
    public static final Block MOSSY_STONE_PILLAR = registerStoneBlock("mossy_stone_pillar",
            new PillarBlock(AbstractBlock.Settings.copy(STONE_PILLAR).strength(StoneBlockSets.BRICKS_HARDNESS, StoneBlockSets.BRICKS_BLASTRESISTANCE).sounds(BlockSoundGroup.STONE)),true);
    public static final Block CRACKED_STONE_PILLAR = registerStoneBlock("cracked_stone_pillar",
            new PillarBlock(AbstractBlock.Settings.copy(STONE_PILLAR).strength(StoneBlockSets.BRICKS_HARDNESS, StoneBlockSets.BRICKS_BLASTRESISTANCE).sounds(BlockSoundGroup.STONE)),true);
    
    public static final Block DEEPSLATE_PILLAR = registerStoneBlock("deepslate_pillar",
            new PillarBlock(AbstractBlock.Settings.copy(Blocks.QUARTZ_PILLAR).strength(StoneBlockSets.DEEPSLATE_BRICKS_HARDNESS, StoneBlockSets.DEEPSLATE_BRICKS_BLAST_RESISTANCE).sounds(BlockSoundGroup.DEEPSLATE)),true);
    public static final Block MOSSY_DEEPSLATE_PILLAR = registerStoneBlock("mossy_deepslate_pillar",
            new PillarBlock(AbstractBlock.Settings.copy(DEEPSLATE_PILLAR).strength(StoneBlockSets.DEEPSLATE_BRICKS_HARDNESS, StoneBlockSets.DEEPSLATE_BRICKS_BLAST_RESISTANCE).sounds(BlockSoundGroup.DEEPSLATE)),true);
    public static final Block CRACKED_DEEPSLATE_PILLAR = registerStoneBlock("cracked_deepslate_pillar",
            new PillarBlock(AbstractBlock.Settings.copy(DEEPSLATE_PILLAR).strength(StoneBlockSets.DEEPSLATE_BRICKS_HARDNESS, StoneBlockSets.DEEPSLATE_BRICKS_BLAST_RESISTANCE).sounds(BlockSoundGroup.DEEPSLATE)),true);

    public static final Block GONLUIN_PILLAR = registerStoneBlock("gonluin_pillar",
            new PillarBlock(AbstractBlock.Settings.copy(Blocks.QUARTZ_PILLAR).strength(StoneBlockSets.BRICKS_HARDNESS, StoneBlockSets.BRICKS_BLASTRESISTANCE).sounds(BlockSoundGroup.STONE)),true);
    public static final Block MOSSY_GONLUIN_PILLAR = registerStoneBlock("mossy_gonluin_pillar",
            new PillarBlock(AbstractBlock.Settings.copy(Blocks.QUARTZ_PILLAR).strength(StoneBlockSets.BRICKS_HARDNESS, StoneBlockSets.BRICKS_BLASTRESISTANCE).sounds(BlockSoundGroup.STONE)),true);
    public static final Block CRACKED_GONLUIN_PILLAR = registerStoneBlock("cracked_gonluin_pillar",
            new PillarBlock(AbstractBlock.Settings.copy(Blocks.QUARTZ_PILLAR).strength(StoneBlockSets.BRICKS_HARDNESS, StoneBlockSets.BRICKS_BLASTRESISTANCE).sounds(BlockSoundGroup.STONE)),true);


    public static final Block BLUE_TUFF_PILLAR = registerStoneBlock("blue_tuff_pillar",
            new PillarBlock(AbstractBlock.Settings.copy(Blocks.QUARTZ_PILLAR).strength(StoneBlockSets.BRICKS_HARDNESS, StoneBlockSets.BRICKS_BLASTRESISTANCE).sounds(BlockSoundGroup.STONE)),true);
    public static final Block MOSSY_BLUE_TUFF_PILLAR = registerStoneBlock("mossy_blue_tuff_pillar",
            new PillarBlock(AbstractBlock.Settings.copy(Blocks.QUARTZ_PILLAR).strength(StoneBlockSets.BRICKS_HARDNESS, StoneBlockSets.BRICKS_BLASTRESISTANCE).sounds(BlockSoundGroup.STONE)),true);
    public static final Block CRACKED_BLUE_TUFF_PILLAR = registerStoneBlock("cracked_blue_tuff_pillar",
            new PillarBlock(AbstractBlock.Settings.copy(Blocks.QUARTZ_PILLAR).strength(StoneBlockSets.BRICKS_HARDNESS, StoneBlockSets.BRICKS_BLASTRESISTANCE).sounds(BlockSoundGroup.STONE)),true);

    public static final Block FROZEN_PILLAR = registerStoneBlock("frozen_pillar",
            new PillarBlock(AbstractBlock.Settings.copy(Blocks.QUARTZ_PILLAR).strength(StoneBlockSets.BRICKS_HARDNESS, StoneBlockSets.BRICKS_BLASTRESISTANCE).sounds(BlockSoundGroup.STONE)),true);

    public static final Block DOLOMITE_PILLAR = registerStoneBlock("dolomite_pillar",
            new PillarBlock(AbstractBlock.Settings.copy(Blocks.QUARTZ_PILLAR).strength(StoneBlockSets.BRICKS_HARDNESS, StoneBlockSets.BRICKS_BLASTRESISTANCE).sounds(BlockSoundGroup.STONE)),true);

    public static final Block CALCITE_PILLAR = registerStoneBlock("calcite_pillar",
            new PillarBlock(AbstractBlock.Settings.copy(Blocks.QUARTZ_PILLAR).strength(StoneBlockSets.BRICKS_HARDNESS, StoneBlockSets.BRICKS_BLASTRESISTANCE).sounds(BlockSoundGroup.STONE)),true);
    public static final Block MOSSY_CALCITE_PILLAR = registerStoneBlock("mossy_calcite_pillar",
            new PillarBlock(AbstractBlock.Settings.copy(Blocks.QUARTZ_PILLAR).strength(StoneBlockSets.BRICKS_HARDNESS, StoneBlockSets.BRICKS_BLASTRESISTANCE).sounds(BlockSoundGroup.STONE)),true);
    public static final Block CRACKED_CALCITE_PILLAR = registerStoneBlock("cracked_calcite_pillar",
            new PillarBlock(AbstractBlock.Settings.copy(Blocks.QUARTZ_PILLAR).strength(StoneBlockSets.BRICKS_HARDNESS, StoneBlockSets.BRICKS_BLASTRESISTANCE).sounds(BlockSoundGroup.STONE)),true);

    public static final Block LIMESTONE_PILLAR = registerStoneBlock("limestone_pillar",
            new PillarBlock(AbstractBlock.Settings.copy(Blocks.QUARTZ_PILLAR).strength(StoneBlockSets.BRICKS_HARDNESS, StoneBlockSets.BRICKS_BLASTRESISTANCE).sounds(BlockSoundGroup.STONE)),true);
    public static final Block MOSSY_LIMESTONE_PILLAR = registerStoneBlock("mossy_limestone_pillar",
            new PillarBlock(AbstractBlock.Settings.copy(Blocks.QUARTZ_PILLAR).strength(StoneBlockSets.BRICKS_HARDNESS, StoneBlockSets.BRICKS_BLASTRESISTANCE).sounds(BlockSoundGroup.STONE)),true);
    public static final Block CRACKED_LIMESTONE_PILLAR = registerStoneBlock("cracked_limestone_pillar",
            new PillarBlock(AbstractBlock.Settings.copy(Blocks.QUARTZ_PILLAR).strength(StoneBlockSets.BRICKS_HARDNESS, StoneBlockSets.BRICKS_BLASTRESISTANCE).sounds(BlockSoundGroup.STONE)),true);

    public static final Block ANDESITE_PILLAR = registerStoneBlock("andesite_pillar",
            new PillarBlock(AbstractBlock.Settings.copy(Blocks.QUARTZ_PILLAR).strength(StoneBlockSets.BRICKS_HARDNESS, StoneBlockSets.BRICKS_BLASTRESISTANCE).sounds(BlockSoundGroup.STONE)),true);
    public static final Block MOSSY_ANDESITE_PILLAR = registerStoneBlock("mossy_andesite_pillar",
            new PillarBlock(AbstractBlock.Settings.copy(Blocks.QUARTZ_PILLAR).strength(StoneBlockSets.BRICKS_HARDNESS, StoneBlockSets.BRICKS_BLASTRESISTANCE).sounds(BlockSoundGroup.STONE)),true);
    public static final Block CRACKED_ANDESITE_PILLAR = registerStoneBlock("cracked_andesite_pillar",
            new PillarBlock(AbstractBlock.Settings.copy(Blocks.QUARTZ_PILLAR).strength(StoneBlockSets.BRICKS_HARDNESS, StoneBlockSets.BRICKS_BLASTRESISTANCE).sounds(BlockSoundGroup.STONE)),true);

    public static final Block GRANITE_PILLAR = registerStoneBlock("granite_pillar",
            new PillarBlock(AbstractBlock.Settings.copy(Blocks.QUARTZ_PILLAR).strength(StoneBlockSets.BRICKS_HARDNESS, StoneBlockSets.BRICKS_BLASTRESISTANCE).sounds(BlockSoundGroup.STONE)),true);
    public static final Block MOSSY_GRANITE_PILLAR = registerStoneBlock("mossy_granite_pillar",
            new PillarBlock(AbstractBlock.Settings.copy(Blocks.QUARTZ_PILLAR).strength(StoneBlockSets.BRICKS_HARDNESS, StoneBlockSets.BRICKS_BLASTRESISTANCE).sounds(BlockSoundGroup.STONE)),true);
    public static final Block CRACKED_GRANITE_PILLAR = registerStoneBlock("cracked_granite_pillar",
            new PillarBlock(AbstractBlock.Settings.copy(Blocks.QUARTZ_PILLAR).strength(StoneBlockSets.BRICKS_HARDNESS, StoneBlockSets.BRICKS_BLASTRESISTANCE).sounds(BlockSoundGroup.STONE)),true);

    public static final Block DIORITE_PILLAR = registerStoneBlock("diorite_pillar",
            new PillarBlock(AbstractBlock.Settings.copy(Blocks.QUARTZ_PILLAR).strength(StoneBlockSets.BRICKS_HARDNESS, StoneBlockSets.BRICKS_BLASTRESISTANCE).sounds(BlockSoundGroup.STONE)),true);
    public static final Block MOSSY_DIORITE_PILLAR = registerStoneBlock("mossy_diorite_pillar",
            new PillarBlock(AbstractBlock.Settings.copy(Blocks.QUARTZ_PILLAR).strength(StoneBlockSets.BRICKS_HARDNESS, StoneBlockSets.BRICKS_BLASTRESISTANCE).sounds(BlockSoundGroup.STONE)),true);
    public static final Block CRACKED_DIORITE_PILLAR = registerStoneBlock("cracked_diorite_pillar",
            new PillarBlock(AbstractBlock.Settings.copy(Blocks.QUARTZ_PILLAR).strength(StoneBlockSets.BRICKS_HARDNESS, StoneBlockSets.BRICKS_BLASTRESISTANCE).sounds(BlockSoundGroup.STONE)),true);

    public static final Block JADEITE_PILLAR = registerStoneBlock("jadeite_pillar",
            new PillarBlock(AbstractBlock.Settings.copy(Blocks.QUARTZ_PILLAR).strength(StoneBlockSets.BRICKS_HARDNESS, StoneBlockSets.BRICKS_BLASTRESISTANCE).sounds(BlockSoundGroup.STONE)),true);
    public static final Block CRACKED_JADEITE_PILLAR = registerStoneBlock("cracked_jadeite_pillar",
            new PillarBlock(AbstractBlock.Settings.copy(Blocks.QUARTZ_PILLAR).strength(StoneBlockSets.BRICKS_HARDNESS, StoneBlockSets.BRICKS_BLASTRESISTANCE).sounds(BlockSoundGroup.STONE)),true);

    public static final Block CHISELED_GONLUIN_BRICKS = registerStoneBlock("chiseled_gonluin_bricks",
            new Block(FabricBlockSettings.copyOf(Blocks.CHISELED_STONE_BRICKS).strength(StoneBlockSets.BRICKS_HARDNESS, StoneBlockSets.BRICKS_BLASTRESISTANCE).sounds(BlockSoundGroup.STONE)),true);
    public static final Block CHISELED_POLISHED_GONLUIN = registerStoneBlock("chiseled_polished_gonluin",
            new Block(FabricBlockSettings.copyOf(Blocks.CHISELED_STONE_BRICKS).strength(StoneBlockSets.BRICKS_HARDNESS, StoneBlockSets.BRICKS_BLASTRESISTANCE).sounds(BlockSoundGroup.STONE)),true);

    public static final Block CHISELED_BLUE_TUFF_BRICKS = registerStoneBlock("chiseled_blue_tuff_bricks",
            new PillarBlock(FabricBlockSettings.copyOf(Blocks.CHISELED_STONE_BRICKS).strength(StoneBlockSets.BRICKS_HARDNESS, StoneBlockSets.BRICKS_BLASTRESISTANCE).sounds(BlockSoundGroup.STONE)),true);
    public static final Block CHISELED_POLISHED_BLUE_TUFF = registerStoneBlock("chiseled_polished_blue_tuff",
            new PillarBlock(FabricBlockSettings.copyOf(Blocks.CHISELED_STONE_BRICKS).strength(StoneBlockSets.BRICKS_HARDNESS, StoneBlockSets.BRICKS_BLASTRESISTANCE).sounds(BlockSoundGroup.STONE)),true);

    public static final Block CHISELED_DOLOMITE_BRICKS = registerStoneBlock("chiseled_dolomite_bricks",
            new PillarBlock(FabricBlockSettings.copyOf(Blocks.CHISELED_STONE_BRICKS).strength(StoneBlockSets.BRICKS_HARDNESS, StoneBlockSets.BRICKS_BLASTRESISTANCE).sounds(BlockSoundGroup.STONE)),true);

    public static final Block CHISELED_CALCITE_BRICKS = registerStoneBlock("chiseled_calcite_bricks",
            new Block(FabricBlockSettings.copyOf(Blocks.CHISELED_STONE_BRICKS).strength(StoneBlockSets.BRICKS_HARDNESS, StoneBlockSets.BRICKS_BLASTRESISTANCE).sounds(BlockSoundGroup.STONE)),true);
    public static final Block CHISELED_POLISHED_CALCITE = registerStoneBlock("chiseled_polished_calcite",
            new Block(FabricBlockSettings.copyOf(Blocks.CHISELED_STONE_BRICKS).strength(StoneBlockSets.BRICKS_HARDNESS, StoneBlockSets.BRICKS_BLASTRESISTANCE).sounds(BlockSoundGroup.STONE)),true);

    public static final Block CHISELED_LIMESTONE_BRICKS = registerStoneBlock("chiseled_limestone_bricks",
            new Block(FabricBlockSettings.copyOf(Blocks.CHISELED_STONE_BRICKS).strength(StoneBlockSets.BRICKS_HARDNESS, StoneBlockSets.BRICKS_BLASTRESISTANCE).sounds(BlockSoundGroup.STONE)),true);
    public static final Block CHISELED_POLISHED_LIMESTONE = registerStoneBlock("chiseled_polished_limestone",
            new Block(FabricBlockSettings.copyOf(Blocks.CHISELED_STONE_BRICKS).strength(StoneBlockSets.BRICKS_HARDNESS, StoneBlockSets.BRICKS_BLASTRESISTANCE).sounds(BlockSoundGroup.STONE)),true);
    
    public static final Block CHISELED_SMOOTH_STONE = registerStoneBlock("chiseled_smooth_stone",
            new Block(FabricBlockSettings.copyOf(Blocks.CHISELED_STONE_BRICKS).strength(StoneBlockSets.BRICKS_HARDNESS, StoneBlockSets.BRICKS_BLASTRESISTANCE).sounds(BlockSoundGroup.STONE)),true);

    public static final Block CHISELED_ANDESITE_BRICKS = registerStoneBlock("chiseled_andesite_bricks",
            new PillarBlock(FabricBlockSettings.copyOf(Blocks.CHISELED_STONE_BRICKS).strength(StoneBlockSets.BRICKS_HARDNESS, StoneBlockSets.BRICKS_BLASTRESISTANCE).sounds(BlockSoundGroup.STONE)),true);
    public static final Block CHISELED_POLISHED_ANDESITE = registerStoneBlock("chiseled_polished_andesite",
            new PillarBlock(FabricBlockSettings.copyOf(Blocks.CHISELED_STONE_BRICKS).strength(StoneBlockSets.BRICKS_HARDNESS, StoneBlockSets.BRICKS_BLASTRESISTANCE).sounds(BlockSoundGroup.STONE)),true);
    
    public static final Block CHISELED_GRANITE_BRICKS = registerStoneBlock("chiseled_granite_bricks",
            new Block(FabricBlockSettings.copyOf(Blocks.CHISELED_STONE_BRICKS).strength(StoneBlockSets.BRICKS_HARDNESS, StoneBlockSets.BRICKS_BLASTRESISTANCE).sounds(BlockSoundGroup.STONE)),true);
    public static final Block CHISELED_POLISHED_GRANITE = registerStoneBlock("chiseled_polished_granite",
            new Block(FabricBlockSettings.copyOf(Blocks.CHISELED_STONE_BRICKS).strength(StoneBlockSets.BRICKS_HARDNESS, StoneBlockSets.BRICKS_BLASTRESISTANCE).sounds(BlockSoundGroup.STONE)),true);

    public static final Block CHISELED_DIORITE_BRICKS = registerStoneBlock("chiseled_diorite_bricks",
            new Block(FabricBlockSettings.copyOf(Blocks.CHISELED_STONE_BRICKS).strength(StoneBlockSets.BRICKS_HARDNESS, StoneBlockSets.BRICKS_BLASTRESISTANCE).sounds(BlockSoundGroup.STONE)),true);
    public static final Block CHISELED_POLISHED_DIORITE = registerStoneBlock("chiseled_polished_diorite",
            new Block(FabricBlockSettings.copyOf(Blocks.CHISELED_STONE_BRICKS).strength(StoneBlockSets.BRICKS_HARDNESS, StoneBlockSets.BRICKS_BLASTRESISTANCE).sounds(BlockSoundGroup.STONE)),true);
    
    public static final Block CHISELED_BASALT_BRICKS = registerStoneBlock("chiseled_basalt_bricks",
            new Block(FabricBlockSettings.copyOf(Blocks.CHISELED_STONE_BRICKS).strength(StoneBlockSets.BRICKS_HARDNESS, StoneBlockSets.BRICKS_BLASTRESISTANCE).sounds(BlockSoundGroup.STONE)),true);

    public static final Block DIFTOMIN_PILLAR = registerStoneBlock("diftomin_pillar",
            new PillarBlock(AbstractBlock.Settings.copy(Blocks.QUARTZ_PILLAR).strength(StoneBlockSets.DIFTOMIN_BRICKS_HARDNESS, StoneBlockSets.DIFTOMIN_BLAST_RESISTANCE).sounds(BlockSoundGroup.STONE)),true);
    public static final Block CHISELED_DIFTOMIN_BRICKS = registerStoneBlock("chiseled_diftomin_bricks",
            new Block(FabricBlockSettings.copyOf(Blocks.CHISELED_STONE_BRICKS).strength(StoneBlockSets.DIFTOMIN_BRICKS_HARDNESS, StoneBlockSets.DIFTOMIN_BLAST_RESISTANCE).sounds(BlockSoundGroup.STONE)),true);
    public static final Block CHISELED_POLISHED_DIFTOMIN = registerStoneBlock("chiseled_polished_diftomin",
            new Block(FabricBlockSettings.copyOf(Blocks.CHISELED_STONE_BRICKS).strength(StoneBlockSets.DIFTOMIN_BRICKS_HARDNESS, StoneBlockSets.DIFTOMIN_BLAST_RESISTANCE).sounds(BlockSoundGroup.STONE)),true);


    //endregion

    public static final Block DRY_DIRT = registerMiscBlock("dry_dirt",
            new Block(FabricBlockSettings.copyOf(Blocks.DIRT).strength(DIRT_STRENGTH).sounds(BlockSoundGroup.GRAVEL)),true);
    public static final Block DRY_DIRT_SLAB = registerMiscBlock("dry_dirt_slab",
            new SlabBlock(FabricBlockSettings.copyOf(DRY_DIRT)),true);
    public static final Block DRY_DIRT_STAIRS = registerMiscBlock("dry_dirt_stairs",
            new StairsBlock(DRY_DIRT.getDefaultState(), FabricBlockSettings.copyOf(DRY_DIRT)),true);
    public static final Block ASHEN_DIRT = registerMiscBlock("ashen_dirt",
            new Block(FabricBlockSettings.copyOf(Blocks.DIRT).strength(DIRT_STRENGTH).sounds(BlockSoundGroup.GRAVEL)),true);
    public static final Block ASHEN_DIRT_SLAB = registerMiscBlock("ashen_dirt_slab",
            new SlabBlock(FabricBlockSettings.copyOf(ASHEN_DIRT)),true);
    public static final Block ASHEN_DIRT_STAIRS = registerMiscBlock("ashen_dirt_stairs",
            new StairsBlock(ASHEN_DIRT.getDefaultState(), FabricBlockSettings.copyOf(ASHEN_DIRT)),true);
    
    public static final Block REED_BLOCK = registerMiscBlock("reed_block",
            new HayBlock(FabricBlockSettings.copyOf(Blocks.HAY_BLOCK).strength(DIRT_STRENGTH).sounds(BlockSoundGroup.GRASS)),true);
    public static final Block REED_STAIRS = registerMiscBlock("reed_stairs",
            new StairsBlock(REED_BLOCK.getDefaultState(), FabricBlockSettings.copyOf(Blocks.HAY_BLOCK).strength(DIRT_STRENGTH).sounds(BlockSoundGroup.GRASS)),true);
    public static final Block REED_SLAB = registerMiscBlock("reed_slab",
            new SlabBlock(FabricBlockSettings.copyOf(Blocks.HAY_BLOCK).strength(DIRT_STRENGTH).sounds(BlockSoundGroup.GRASS)),true);
    public static final Block REED_VERTICAL_SLAB = registerMiscBlock("reed_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(ModBlocks.REED_SLAB)),true);
    public static final Block REED_WALL = registerMiscBlock("reed_wall",
            new WallBlock(FabricBlockSettings.copyOf(Blocks.HAY_BLOCK).strength(DIRT_STRENGTH).sounds(BlockSoundGroup.GRASS)),true);
    public static final Block STRAW_BLOCK = registerMiscBlock("straw_block",
            new HayBlock(FabricBlockSettings.copyOf(Blocks.HAY_BLOCK).strength(DIRT_STRENGTH).sounds(BlockSoundGroup.GRASS)),true);
    public static final Block STRAW_STAIRS = registerMiscBlock("straw_stairs",
            new StairsBlock(STRAW_BLOCK.getDefaultState(), FabricBlockSettings.copyOf(Blocks.HAY_BLOCK).strength(DIRT_STRENGTH).sounds(BlockSoundGroup.GRASS)),true);
    public static final Block STRAW_SLAB = registerMiscBlock("straw_slab",
            new SlabBlock(FabricBlockSettings.copyOf(Blocks.HAY_BLOCK).strength(DIRT_STRENGTH).sounds(BlockSoundGroup.GRASS)),true);
    public static final Block STRAW_VERTICAL_SLAB = registerMiscBlock("straw_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(ModBlocks.STRAW_SLAB)),true);
    public static final Block STRAW_WALL = registerMiscBlock("straw_wall",
            new WallBlock(FabricBlockSettings.copyOf(Blocks.HAY_BLOCK).strength(DIRT_STRENGTH).sounds(BlockSoundGroup.GRASS)),true);

    public static final Block DAUB = registerMiscBlock("daub",
            new Block(FabricBlockSettings.copyOf(Blocks.PACKED_MUD)),true);
    public static final Block WATTLE_AND_DAUB = registerMiscBlock("wattle_and_daub",
            new Block(FabricBlockSettings.copyOf(Blocks.PACKED_MUD)),true);
    public static final Block WATTLE_AND_DAUB_CROSS = registerMiscBlock("wattle_and_daub_cross",
            new Block(FabricBlockSettings.copyOf(Blocks.PACKED_MUD)),true);
    public static final Block WATTLE_AND_DAUB_RIGHT = registerMiscBlock("wattle_and_daub_right",
            new Block(FabricBlockSettings.copyOf(Blocks.PACKED_MUD)),true);
    public static final Block WATTLE_AND_DAUB_LEFT = registerMiscBlock("wattle_and_daub_left",
            new Block(FabricBlockSettings.copyOf(Blocks.PACKED_MUD)),true);
    public static final Block WATTLE_AND_DAUB_DIAMOND = registerMiscBlock("wattle_and_daub_diamond",
            new Block(FabricBlockSettings.copyOf(Blocks.PACKED_MUD)),true);

    //region METAL AND GEMS
    public static final Block RAW_MITHRIL_BLOCK = registerMiscBlock("raw_mithril_block",
            new Block(FabricBlockSettings.copyOf(Blocks.RAW_IRON_BLOCK).strength(4f).requiresTool()),true);
    public static final Block MITHRIL_BLOCK = registerMiscBlock("mithril_block",
            new Block(FabricBlockSettings.copyOf(Blocks.IRON_BLOCK).strength(6f).requiresTool()),true);
    public static final Block RAW_TIN_BLOCK = registerMiscBlock("raw_tin_block",
            new Block(FabricBlockSettings.copyOf(Blocks.STONE).strength(2f).requiresTool()),true);
    public static final Block TIN_BLOCK = registerMiscBlock("tin_block",
            new Block(FabricBlockSettings.copyOf(Blocks.IRON_BLOCK).strength(3f).requiresTool()),true);
    public static final Block RAW_LEAD_BLOCK = registerMiscBlock("raw_lead_block",
            new Block(FabricBlockSettings.copyOf(Blocks.RAW_IRON_BLOCK).strength(3f).requiresTool()),true);
    public static final Block LEAD_BLOCK = registerMiscBlock("lead_block",
            new Block(FabricBlockSettings.copyOf(Blocks.IRON_BLOCK).strength(2f).requiresTool()),true);
    public static final Block RAW_SILVER_BLOCK = registerMiscBlock("raw_silver_block",
            new Block(FabricBlockSettings.copyOf(Blocks.RAW_IRON_BLOCK).strength(3f).requiresTool()),true);
    public static final Block SILVER_BLOCK = registerMiscBlock("silver_block",
            new Block(FabricBlockSettings.copyOf(Blocks.IRON_BLOCK).strength(4f).requiresTool()),true);

    public static final Block CUT_LEAD = registerMiscBlock("cut_lead",
            new Block(FabricBlockSettings.copyOf(Blocks.CUT_COPPER).sounds(BlockSoundGroup.COPPER)),true);
    public static final Block CUT_LEAD_STAIRS = registerMiscBlock("cut_lead_stairs",
            new StairsBlock(REED_BLOCK.getDefaultState(), FabricBlockSettings.copyOf(Blocks.CUT_COPPER).sounds(BlockSoundGroup.COPPER)),true);
    public static final Block CUT_LEAD_SLAB = registerMiscBlock("cut_lead_slab",
            new SlabBlock(FabricBlockSettings.copyOf(Blocks.CUT_COPPER).sounds(BlockSoundGroup.COPPER)),true);
    public static final Block CUT_LEAD_VERTICAL_SLAB = registerMiscBlock("cut_lead_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(ModBlocks.CUT_LEAD_SLAB)),true);

    public static final Block CUT_SILVER = registerMiscBlock("cut_silver",
            new Block(FabricBlockSettings.copyOf(Blocks.CUT_COPPER).sounds(BlockSoundGroup.COPPER)),true);
    public static final Block CUT_SILVER_STAIRS = registerMiscBlock("cut_silver_stairs",
            new StairsBlock(REED_BLOCK.getDefaultState(), FabricBlockSettings.copyOf(Blocks.CUT_COPPER).sounds(BlockSoundGroup.COPPER)),true);
    public static final Block CUT_SILVER_SLAB = registerMiscBlock("cut_silver_slab",
            new SlabBlock(FabricBlockSettings.copyOf(Blocks.CUT_COPPER).sounds(BlockSoundGroup.COPPER)),true);
    public static final Block CUT_SILVER_VERTICAL_SLAB = registerMiscBlock("cut_silver_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(ModBlocks.CUT_SILVER_SLAB)),true);

    public static final Block QUARTZ_BLOCK = registerMiscBlock("quartz_block",
            new AmethystBlock(FabricBlockSettings.copyOf(Blocks.AMETHYST_BLOCK)), true);
    public static final Block QUARTZ_CLUSTER = registerMiscBlock("quartz_cluster",
            new AmethystClusterBlock(7,3, FabricBlockSettings.copyOf(Blocks.AMETHYST_CLUSTER)), false);
    public static final Block LARGE_QUARTZ_BUD = registerMiscBlock("large_quartz_bud",
            new AmethystClusterBlock(5,3, FabricBlockSettings.copyOf(ModBlocks.QUARTZ_CLUSTER).sounds(BlockSoundGroup.LARGE_AMETHYST_BUD)), false);
    public static final Block MEDIUM_QUARTZ_BUD = registerMiscBlock("medium_quartz_bud",
            new AmethystClusterBlock(4,3, FabricBlockSettings.copyOf(ModBlocks.QUARTZ_CLUSTER).sounds(BlockSoundGroup.MEDIUM_AMETHYST_BUD)), false);
    public static final Block SMALL_QUARTZ_BUD = registerMiscBlock("small_quartz_bud",
            new AmethystClusterBlock(3,4, FabricBlockSettings.copyOf(ModBlocks.QUARTZ_CLUSTER).sounds(BlockSoundGroup.SMALL_AMETHYST_BUD)), false);
    public static final Block BUDDING_QUARTZ = registerMiscBlock("budding_quartz",
            new CustomBuddingGemBlock(FabricBlockSettings.copyOf(Blocks.BUDDING_AMETHYST), List.of(SMALL_QUARTZ_BUD,MEDIUM_QUARTZ_BUD,LARGE_QUARTZ_BUD, QUARTZ_CLUSTER)), false);

    public static final Block RED_AGATE_BLOCK = registerMiscBlock("red_agate_block",
            new AmethystBlock(FabricBlockSettings.copyOf(Blocks.AMETHYST_BLOCK)), true);
    public static final Block RED_AGATE_CLUSTER = registerMiscBlock("red_agate_cluster",
            new AmethystClusterBlock(7,3, FabricBlockSettings.copyOf(Blocks.AMETHYST_CLUSTER)), false);
    public static final Block LARGE_RED_AGATE_BUD = registerMiscBlock("large_red_agate_bud",
            new AmethystClusterBlock(5,3, FabricBlockSettings.copyOf(ModBlocks.RED_AGATE_CLUSTER).sounds(BlockSoundGroup.LARGE_AMETHYST_BUD)), false);
    public static final Block MEDIUM_RED_AGATE_BUD = registerMiscBlock("medium_red_agate_bud",
            new AmethystClusterBlock(4,3, FabricBlockSettings.copyOf(ModBlocks.RED_AGATE_CLUSTER).sounds(BlockSoundGroup.MEDIUM_AMETHYST_BUD)), false);
    public static final Block SMALL_RED_AGATE_BUD = registerMiscBlock("small_red_agate_bud",
            new AmethystClusterBlock(3,4, FabricBlockSettings.copyOf(ModBlocks.RED_AGATE_CLUSTER).sounds(BlockSoundGroup.SMALL_AMETHYST_BUD)), false);
    public static final Block BUDDING_RED_AGATE = registerMiscBlock("budding_red_agate",
            new CustomBuddingGemBlock(FabricBlockSettings.copyOf(Blocks.BUDDING_AMETHYST), List.of(SMALL_RED_AGATE_BUD,MEDIUM_RED_AGATE_BUD,LARGE_RED_AGATE_BUD, RED_AGATE_CLUSTER)), false);
    
    public static final Block CITRINE_BLOCK = registerMiscBlock("citrine_block",
            new AmethystBlock(FabricBlockSettings.copyOf(Blocks.AMETHYST_BLOCK)), true);
    public static final Block CITRINE_CLUSTER = registerMiscBlock("citrine_cluster",
            new AmethystClusterBlock(7,3, FabricBlockSettings.copyOf(Blocks.AMETHYST_CLUSTER)), false);
    public static final Block LARGE_CITRINE_BUD = registerMiscBlock("large_citrine_bud",
            new AmethystClusterBlock(5,3, FabricBlockSettings.copyOf(ModBlocks.CITRINE_CLUSTER).sounds(BlockSoundGroup.LARGE_AMETHYST_BUD)), false);
    public static final Block MEDIUM_CITRINE_BUD = registerMiscBlock("medium_citrine_bud",
            new AmethystClusterBlock(4,3, FabricBlockSettings.copyOf(ModBlocks.CITRINE_CLUSTER).sounds(BlockSoundGroup.MEDIUM_AMETHYST_BUD)), false);
    public static final Block SMALL_CITRINE_BUD = registerMiscBlock("small_citrine_bud",
            new AmethystClusterBlock(3,4, FabricBlockSettings.copyOf(ModBlocks.CITRINE_CLUSTER).sounds(BlockSoundGroup.SMALL_AMETHYST_BUD)), false);
    public static final Block BUDDING_CITRINE = registerMiscBlock("budding_citrine",
            new CustomBuddingGemBlock(FabricBlockSettings.copyOf(Blocks.BUDDING_AMETHYST), List.of(SMALL_CITRINE_BUD,MEDIUM_CITRINE_BUD,LARGE_CITRINE_BUD, CITRINE_CLUSTER)), false);

    public static final Block GLOWSTONE_BLOCK = registerMiscBlock("glowstone_block",
            new AmethystBlock(FabricBlockSettings.copyOf(Blocks.AMETHYST_BLOCK).luminance(12)), true);
    public static final Block GLOWSTONE_CLUSTER = registerMiscBlock("glowstone_cluster",
            new AmethystClusterBlock(7,3, FabricBlockSettings.copyOf(Blocks.AMETHYST_CLUSTER).luminance(10)), false);
    public static final Block LARGE_GLOWSTONE_BUD = registerMiscBlock("large_glowstone_bud",
            new AmethystClusterBlock(5,3, FabricBlockSettings.copyOf(ModBlocks.GLOWSTONE_CLUSTER).sounds(BlockSoundGroup.LARGE_AMETHYST_BUD).luminance(9)), false);
    public static final Block MEDIUM_GLOWSTONE_BUD = registerMiscBlock("medium_glowstone_bud",
            new AmethystClusterBlock(4,3, FabricBlockSettings.copyOf(ModBlocks.GLOWSTONE_CLUSTER).sounds(BlockSoundGroup.MEDIUM_AMETHYST_BUD).luminance(7)), false);
    public static final Block SMALL_GLOWSTONE_BUD = registerMiscBlock("small_glowstone_bud",
            new AmethystClusterBlock(3,4, FabricBlockSettings.copyOf(ModBlocks.GLOWSTONE_CLUSTER).sounds(BlockSoundGroup.SMALL_AMETHYST_BUD).luminance(5)), false);
    public static final Block BUDDING_GLOWSTONE = registerMiscBlock("budding_glowstone",
            new CustomBuddingGemBlock(FabricBlockSettings.copyOf(Blocks.BUDDING_AMETHYST).luminance(12), List.of(SMALL_GLOWSTONE_BUD,MEDIUM_GLOWSTONE_BUD,LARGE_GLOWSTONE_BUD, GLOWSTONE_CLUSTER)), false);


    public static final Block STONE_TRAPDOOR = registerStoneBlock("stone_trapdoor",
            new TrapdoorBlock(FabricBlockSettings.copyOf(Blocks.OAK_TRAPDOOR).strength(StoneBlockSets.BRICKS_HARDNESS, StoneBlockSets.BRICKS_BLASTRESISTANCE).sounds(BlockSoundGroup.STONE), BlockSetType.STONE), true);
    public static final Block BLACKSTONE_TRAPDOOR = registerStoneBlock("blackstone_trapdoor",
            new TrapdoorBlock(FabricBlockSettings.copyOf(Blocks.OAK_TRAPDOOR).strength(StoneBlockSets.BRICKS_HARDNESS, StoneBlockSets.BRICKS_BLASTRESISTANCE).sounds(BlockSoundGroup.STONE), BlockSetType.POLISHED_BLACKSTONE), true);

    public static final Block SILVERS_BARS = registerMiscBlock("silver_bars",
            new PaneBlock(FabricBlockSettings.copyOf(Blocks.IRON_BARS).sounds(BlockSoundGroup.METAL)), true);

    //endregion

    public static final Block RIVER_SAND = registerMiscBlock("river_sand",
            new FallingBlock(FabricBlockSettings.copyOf(Blocks.SAND).sounds(BlockSoundGroup.SAND)), true);
    public static final Block BLACK_SAND = registerMiscBlock("black_sand",
            new FallingBlock(FabricBlockSettings.copyOf(Blocks.SAND).sounds(BlockSoundGroup.SAND)), true);
    public static final Block WHITE_SAND = registerMiscBlock("white_sand",
            new FallingBlock(FabricBlockSettings.copyOf(Blocks.SAND).sounds(BlockSoundGroup.SAND)), true);

    public static final Block STONE_MYCELIUM = registerMiscBlock("stone_mycelium",
            new StoneMyceliumBlock(FabricBlockSettings.copyOf(Blocks.STONE).ticksRandomly()), false);

    public static final Block ASH_BLOCK = registerMiscBlock("ash_block",
            new FallingBlock(FabricBlockSettings.copyOf(Blocks.SAND).strength(DIRT_STRENGTH).sounds(BlockSoundGroup.SAND)), true);

    //region VANILLA SLABS
    public static final Block DIRT_SLAB = registerMiscBlock("dirt_slab",
            new SlabBlock(FabricBlockSettings.copyOf(Blocks.DIRT)), true);
    public static final Block COARSE_DIRT_SLAB = registerMiscBlock("coarse_dirt_slab",
            new SlabBlock(FabricBlockSettings.copyOf(Blocks.COARSE_DIRT)), true);
    public static final Block ROOTED_DIRT_SLAB = registerMiscBlock("rooted_dirt_slab",
            new SlabBlock(FabricBlockSettings.copyOf(Blocks.ROOTED_DIRT)), true);
    public static final Block MUD_SLAB = registerMiscBlock("mud_slab",
            new SlabBlock(FabricBlockSettings.copyOf(Blocks.ROOTED_DIRT)), true);

    public static final Block PACKED_MUD_SLAB = registerMiscBlock("packed_mud_slab",
            new SlabBlock(FabricBlockSettings.copyOf(Blocks.PACKED_MUD)), true);
    public static final Block PACKED_MUD_VERTICAL_SLAB = registerMiscBlock("packed_mud_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.PACKED_MUD)), true);
    public static final Block PACKED_MUD_STAIRS = registerMiscBlock("packed_mud_stairs",
            new StairsBlock(Blocks.PACKED_MUD.getDefaultState(), FabricBlockSettings.copyOf(Blocks.PACKED_MUD)), true);
    public static final Block PACKED_MUD_WALL = registerMiscBlock("packed_mud_wall",
            new WallBlock(FabricBlockSettings.copyOf(Blocks.PACKED_MUD)), true);

    public static final Block BLACK_WOOL_SLAB = registerMiscBlock("black_wool_slab",
            new SlabBlock(FabricBlockSettings.copyOf(Blocks.WHITE_WOOL).burnable()), true);
    public static final Block BLUE_WOOL_SLAB = registerMiscBlock("blue_wool_slab",
            new SlabBlock(FabricBlockSettings.copyOf(Blocks.WHITE_WOOL).burnable()), true);
    public static final Block BROWN_WOOL_SLAB = registerMiscBlock("brown_wool_slab",
            new SlabBlock(FabricBlockSettings.copyOf(Blocks.WHITE_WOOL).burnable()), true);
    public static final Block CYAN_WOOL_SLAB = registerMiscBlock("cyan_wool_slab",
            new SlabBlock(FabricBlockSettings.copyOf(Blocks.WHITE_WOOL).burnable()), true);
    public static final Block GRAY_WOOL_SLAB = registerMiscBlock("gray_wool_slab",
            new SlabBlock(FabricBlockSettings.copyOf(Blocks.WHITE_WOOL).burnable()), true);
    public static final Block GREEN_WOOL_SLAB = registerMiscBlock("green_wool_slab",
            new SlabBlock(FabricBlockSettings.copyOf(Blocks.WHITE_WOOL).burnable()), true);
    public static final Block LIGHT_BLUE_WOOL_SLAB = registerMiscBlock("light_blue_wool_slab",
            new SlabBlock(FabricBlockSettings.copyOf(Blocks.WHITE_WOOL).burnable()), true);
    public static final Block LIGHT_GRAY_WOOL_SLAB = registerMiscBlock("light_gray_wool_slab",
            new SlabBlock(FabricBlockSettings.copyOf(Blocks.WHITE_WOOL).burnable()), true);
    public static final Block LIME_WOOL_SLAB = registerMiscBlock("lime_wool_slab",
            new SlabBlock(FabricBlockSettings.copyOf(Blocks.WHITE_WOOL).burnable()), true);
    public static final Block MAGENTA_WOOL_SLAB = registerMiscBlock("magenta_wool_slab",
            new SlabBlock(FabricBlockSettings.copyOf(Blocks.WHITE_WOOL).burnable()), true);
    public static final Block ORANGE_WOOL_SLAB = registerMiscBlock("orange_wool_slab",
            new SlabBlock(FabricBlockSettings.copyOf(Blocks.WHITE_WOOL).burnable()), true);
    public static final Block PINK_WOOL_SLAB = registerMiscBlock("pink_wool_slab",
            new SlabBlock(FabricBlockSettings.copyOf(Blocks.WHITE_WOOL).burnable()), true);
    public static final Block PURPLE_WOOL_SLAB = registerMiscBlock("purple_wool_slab",
            new SlabBlock(FabricBlockSettings.copyOf(Blocks.WHITE_WOOL).burnable()), true);
    public static final Block RED_WOOL_SLAB = registerMiscBlock("red_wool_slab",
            new SlabBlock(FabricBlockSettings.copyOf(Blocks.WHITE_WOOL).burnable()), true);
    public static final Block WHITE_WOOL_SLAB = registerMiscBlock("white_wool_slab",
            new SlabBlock(FabricBlockSettings.copyOf(Blocks.WHITE_WOOL).burnable()), true);
    public static final Block YELLOW_WOOL_SLAB = registerMiscBlock("yellow_wool_slab",
            new SlabBlock(FabricBlockSettings.copyOf(Blocks.WHITE_WOOL).burnable()), true);
    //endregion

    //region VANILLA VERTICAL SLABS
    public static final Block OAK_WOOD_SLAB = registerWoodBlock("oak_wood_slab",
            new SlabBlock(FabricBlockSettings.copyOf(Blocks.OAK_SLAB).burnable()), true);
    public static final Block SPRUCE_WOOD_SLAB = registerWoodBlock("spruce_wood_slab",
            new SlabBlock(FabricBlockSettings.copyOf(Blocks.SPRUCE_SLAB).burnable()), true);
    public static final Block BIRCH_WOOD_SLAB = registerWoodBlock("birch_wood_slab",
            new SlabBlock(FabricBlockSettings.copyOf(Blocks.BIRCH_SLAB).burnable()), true);
    public static final Block JUNGLE_WOOD_SLAB = registerWoodBlock("jungle_wood_slab",
            new SlabBlock(FabricBlockSettings.copyOf(Blocks.JUNGLE_SLAB).burnable()), true);
    public static final Block ACACIA_WOOD_SLAB = registerWoodBlock("acacia_wood_slab",
            new SlabBlock(FabricBlockSettings.copyOf(Blocks.ACACIA_SLAB).burnable()), true);
    public static final Block DARK_OAK_WOOD_SLAB = registerWoodBlock("dark_oak_wood_slab",
            new SlabBlock(FabricBlockSettings.copyOf(Blocks.DARK_OAK_SLAB).burnable()), true);
    public static final Block MANGROVE_WOOD_SLAB = registerWoodBlock("mangrove_wood_slab",
            new SlabBlock(FabricBlockSettings.copyOf(Blocks.MANGROVE_SLAB).burnable()), true);
    public static final Block CHERRY_WOOD_SLAB = registerWoodBlock("cherry_wood_slab",
            new SlabBlock(FabricBlockSettings.copyOf(Blocks.CHERRY_SLAB).burnable()), true);

    public static final Block OAK_WOOD_VERTICAL_SLAB = registerWoodBlock("oak_wood_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.OAK_SLAB).burnable()), true);
    public static final Block SPRUCE_WOOD_VERTICAL_SLAB = registerWoodBlock("spruce_wood_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.SPRUCE_SLAB).burnable()), true);
    public static final Block BIRCH_WOOD_VERTICAL_SLAB = registerWoodBlock("birch_wood_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.BIRCH_SLAB).burnable()), true);
    public static final Block JUNGLE_WOOD_VERTICAL_SLAB = registerWoodBlock("jungle_wood_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.JUNGLE_SLAB).burnable()), true);
    public static final Block ACACIA_WOOD_VERTICAL_SLAB = registerWoodBlock("acacia_wood_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.ACACIA_SLAB).burnable()), true);
    public static final Block DARK_OAK_WOOD_VERTICAL_SLAB = registerWoodBlock("dark_oak_wood_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.DARK_OAK_SLAB).burnable()), true);
    public static final Block MANGROVE_WOOD_VERTICAL_SLAB = registerWoodBlock("mangrove_wood_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.MANGROVE_SLAB).burnable()), true);
    public static final Block CHERRY_WOOD_VERTICAL_SLAB = registerWoodBlock("cherry_wood_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.CHERRY_SLAB).burnable()), true);

    public static final Block OAK_WOOD_STAIRS = registerWoodBlock("oak_wood_stairs",
            new StairsBlock(Blocks.OAK_WOOD.getDefaultState(), FabricBlockSettings.copyOf(Blocks.OAK_STAIRS).burnable()), true);
    public static final Block SPRUCE_WOOD_STAIRS = registerWoodBlock("spruce_wood_stairs",
            new StairsBlock(Blocks.OAK_WOOD.getDefaultState(),FabricBlockSettings.copyOf(Blocks.SPRUCE_STAIRS).burnable()), true);
    public static final Block BIRCH_WOOD_STAIRS = registerWoodBlock("birch_wood_stairs",
            new StairsBlock(Blocks.BIRCH_WOOD.getDefaultState(),FabricBlockSettings.copyOf(Blocks.BIRCH_STAIRS).burnable()), true);
    public static final Block JUNGLE_WOOD_STAIRS = registerWoodBlock("jungle_wood_stairs",
            new StairsBlock(Blocks.JUNGLE_WOOD.getDefaultState(),FabricBlockSettings.copyOf(Blocks.JUNGLE_STAIRS).burnable()), true);
    public static final Block ACACIA_WOOD_STAIRS = registerWoodBlock("acacia_wood_stairs",
            new StairsBlock(Blocks.ACACIA_WOOD.getDefaultState(),FabricBlockSettings.copyOf(Blocks.ACACIA_STAIRS).burnable()), true);
    public static final Block DARK_OAK_WOOD_STAIRS = registerWoodBlock("dark_oak_wood_stairs",
            new StairsBlock(Blocks.DARK_OAK_WOOD.getDefaultState(),FabricBlockSettings.copyOf(Blocks.DARK_OAK_STAIRS).burnable()), true);
    public static final Block MANGROVE_WOOD_STAIRS = registerWoodBlock("mangrove_wood_stairs",
            new StairsBlock(Blocks.MANGROVE_WOOD.getDefaultState(),FabricBlockSettings.copyOf(Blocks.MANGROVE_STAIRS).burnable()), true);
    public static final Block CHERRY_WOOD_STAIRS = registerWoodBlock("cherry_wood_stairs",
            new StairsBlock(Blocks.CHERRY_WOOD.getDefaultState(),FabricBlockSettings.copyOf(Blocks.CHERRY_STAIRS).burnable()), true);

    public static final Block OAK_WOOD_WALL = registerWoodBlock("oak_wood_wall",
            new WallBlock(FabricBlockSettings.copyOf(Blocks.OAK_WOOD).burnable()), true);
    public static final Block SPRUCE_WOOD_WALL = registerWoodBlock("spruce_wood_wall",
            new WallBlock(FabricBlockSettings.copyOf(Blocks.SPRUCE_WOOD).burnable()), true);
    public static final Block BIRCH_WOOD_WALL = registerWoodBlock("birch_wood_wall",
            new WallBlock(FabricBlockSettings.copyOf(Blocks.BIRCH_WOOD).burnable()), true);
    public static final Block JUNGLE_WOOD_WALL = registerWoodBlock("jungle_wood_wall",
            new WallBlock(FabricBlockSettings.copyOf(Blocks.JUNGLE_WOOD).burnable()), true);
    public static final Block ACACIA_WOOD_WALL = registerWoodBlock("acacia_wood_wall",
            new WallBlock(FabricBlockSettings.copyOf(Blocks.ACACIA_WOOD).burnable()), true);
    public static final Block DARK_OAK_WOOD_WALL = registerWoodBlock("dark_oak_wood_wall",
            new WallBlock(FabricBlockSettings.copyOf(Blocks.DARK_OAK_WOOD).burnable()), true);
    public static final Block MANGROVE_WOOD_WALL = registerWoodBlock("mangrove_wood_wall",
            new WallBlock(FabricBlockSettings.copyOf(Blocks.MANGROVE_WOOD).burnable()), true);
    public static final Block CHERRY_WOOD_WALL = registerWoodBlock("cherry_wood_wall",
            new WallBlock(FabricBlockSettings.copyOf(Blocks.CHERRY_WOOD).burnable()), true);

    public static final Block OAK_WOOD_FENCE = registerWoodBlock("oak_wood_fence",
            new FenceBlock(FabricBlockSettings.copyOf(Blocks.OAK_WOOD).burnable()), true);
    public static final Block SPRUCE_WOOD_FENCE = registerWoodBlock("spruce_wood_fence",
            new FenceBlock(FabricBlockSettings.copyOf(Blocks.SPRUCE_WOOD).burnable()), true);
    public static final Block BIRCH_WOOD_FENCE = registerWoodBlock("birch_wood_fence",
            new FenceBlock(FabricBlockSettings.copyOf(Blocks.BIRCH_WOOD).burnable()), true);
    public static final Block JUNGLE_WOOD_FENCE = registerWoodBlock("jungle_wood_fence",
            new FenceBlock(FabricBlockSettings.copyOf(Blocks.JUNGLE_WOOD).burnable()), true);
    public static final Block ACACIA_WOOD_FENCE = registerWoodBlock("acacia_wood_fence",
            new FenceBlock(FabricBlockSettings.copyOf(Blocks.ACACIA_WOOD).burnable()), true);
    public static final Block DARK_OAK_WOOD_FENCE = registerWoodBlock("dark_oak_wood_fence",
            new FenceBlock(FabricBlockSettings.copyOf(Blocks.DARK_OAK_WOOD).burnable()), true);
    public static final Block MANGROVE_WOOD_FENCE = registerWoodBlock("mangrove_wood_fence",
            new FenceBlock(FabricBlockSettings.copyOf(Blocks.MANGROVE_WOOD).burnable()), true);
    public static final Block CHERRY_WOOD_FENCE = registerWoodBlock("cherry_wood_fence",
            new FenceBlock(FabricBlockSettings.copyOf(Blocks.CHERRY_WOOD).burnable()), true);

    public static final Block STRIPPED_OAK_WOOD_SLAB = registerWoodBlock("stripped_oak_wood_slab",
            new SlabBlock(FabricBlockSettings.copyOf(Blocks.OAK_SLAB).burnable()), true);
    public static final Block STRIPPED_SPRUCE_WOOD_SLAB = registerWoodBlock("stripped_spruce_wood_slab",
            new SlabBlock(FabricBlockSettings.copyOf(Blocks.SPRUCE_SLAB).burnable()), true);
    public static final Block STRIPPED_BIRCH_WOOD_SLAB = registerWoodBlock("stripped_birch_wood_slab",
            new SlabBlock(FabricBlockSettings.copyOf(Blocks.BIRCH_SLAB).burnable()), true);
    public static final Block STRIPPED_JUNGLE_WOOD_SLAB = registerWoodBlock("stripped_jungle_wood_slab",
            new SlabBlock(FabricBlockSettings.copyOf(Blocks.JUNGLE_SLAB).burnable()), true);
    public static final Block STRIPPED_ACACIA_WOOD_SLAB = registerWoodBlock("stripped_acacia_wood_slab",
            new SlabBlock(FabricBlockSettings.copyOf(Blocks.ACACIA_SLAB).burnable()), true);
    public static final Block STRIPPED_DARK_OAK_WOOD_SLAB = registerWoodBlock("stripped_dark_oak_wood_slab",
            new SlabBlock(FabricBlockSettings.copyOf(Blocks.DARK_OAK_SLAB).burnable()), true);
    public static final Block STRIPPED_MANGROVE_WOOD_SLAB = registerWoodBlock("stripped_mangrove_wood_slab",
            new SlabBlock(FabricBlockSettings.copyOf(Blocks.MANGROVE_SLAB).burnable()), true);
    public static final Block STRIPPED_CHERRY_WOOD_SLAB = registerWoodBlock("stripped_cherry_wood_slab",
            new SlabBlock(FabricBlockSettings.copyOf(Blocks.CHERRY_SLAB).burnable()), true);

    public static final Block STRIPPED_OAK_WOOD_VERTICAL_SLAB = registerWoodBlock("stripped_oak_wood_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.OAK_SLAB).burnable()), true);
    public static final Block STRIPPED_SPRUCE_WOOD_VERTICAL_SLAB = registerWoodBlock("stripped_spruce_wood_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.SPRUCE_SLAB)), true);
    public static final Block STRIPPED_BIRCH_WOOD_VERTICAL_SLAB = registerWoodBlock("stripped_birch_wood_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.BIRCH_SLAB).burnable()), true);
    public static final Block STRIPPED_JUNGLE_WOOD_VERTICAL_SLAB = registerWoodBlock("stripped_jungle_wood_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.JUNGLE_SLAB).burnable()), true);
    public static final Block STRIPPED_ACACIA_WOOD_VERTICAL_SLAB = registerWoodBlock("stripped_acacia_wood_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.ACACIA_SLAB).burnable()), true);
    public static final Block STRIPPED_DARK_OAK_WOOD_VERTICAL_SLAB = registerWoodBlock("stripped_dark_oak_wood_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.DARK_OAK_SLAB).burnable()), true);
    public static final Block STRIPPED_MANGROVE_WOOD_VERTICAL_SLAB = registerWoodBlock("stripped_mangrove_wood_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.MANGROVE_SLAB).burnable()), true);
    public static final Block STRIPPED_CHERRY_WOOD_VERTICAL_SLAB = registerWoodBlock("stripped_cherry_wood_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.CHERRY_SLAB).burnable()), true);

    public static final Block STRIPPED_OAK_WOOD_STAIRS = registerWoodBlock("stripped_oak_wood_stairs",
            new StairsBlock(Blocks.STRIPPED_OAK_WOOD.getDefaultState(), FabricBlockSettings.copyOf(Blocks.OAK_STAIRS).burnable()), true);
    public static final Block STRIPPED_SPRUCE_WOOD_STAIRS = registerWoodBlock("stripped_spruce_wood_stairs",
            new StairsBlock(Blocks.STRIPPED_OAK_WOOD.getDefaultState(),FabricBlockSettings.copyOf(Blocks.SPRUCE_STAIRS).burnable()), true);
    public static final Block STRIPPED_BIRCH_WOOD_STAIRS = registerWoodBlock("stripped_birch_wood_stairs",
            new StairsBlock(Blocks.STRIPPED_BIRCH_WOOD.getDefaultState(),FabricBlockSettings.copyOf(Blocks.BIRCH_STAIRS).burnable()), true);
    public static final Block STRIPPED_JUNGLE_WOOD_STAIRS = registerWoodBlock("stripped_jungle_wood_stairs",
            new StairsBlock(Blocks.STRIPPED_JUNGLE_WOOD.getDefaultState(),FabricBlockSettings.copyOf(Blocks.JUNGLE_STAIRS).burnable()), true);
    public static final Block STRIPPED_ACACIA_WOOD_STAIRS = registerWoodBlock("stripped_acacia_wood_stairs",
            new StairsBlock(Blocks.STRIPPED_ACACIA_WOOD.getDefaultState(),FabricBlockSettings.copyOf(Blocks.ACACIA_STAIRS).burnable()), true);
    public static final Block STRIPPED_DARK_OAK_WOOD_STAIRS = registerWoodBlock("stripped_dark_oak_wood_stairs",
            new StairsBlock(Blocks.STRIPPED_DARK_OAK_WOOD.getDefaultState(),FabricBlockSettings.copyOf(Blocks.DARK_OAK_STAIRS).burnable()), true);
    public static final Block STRIPPED_MANGROVE_WOOD_STAIRS = registerWoodBlock("stripped_mangrove_wood_stairs",
            new StairsBlock(Blocks.STRIPPED_MANGROVE_WOOD.getDefaultState(),FabricBlockSettings.copyOf(Blocks.MANGROVE_STAIRS).burnable()), true);
    public static final Block STRIPPED_CHERRY_WOOD_STAIRS = registerWoodBlock("stripped_cherry_wood_stairs",
            new StairsBlock(Blocks.STRIPPED_CHERRY_WOOD.getDefaultState(),FabricBlockSettings.copyOf(Blocks.CHERRY_STAIRS).burnable()), true);

    public static final Block STRIPPED_OAK_WOOD_WALL = registerWoodBlock("stripped_oak_wood_wall",
            new WallBlock(FabricBlockSettings.copyOf(Blocks.OAK_WOOD).burnable()), true);
    public static final Block STRIPPED_SPRUCE_WOOD_WALL = registerWoodBlock("stripped_spruce_wood_wall",
            new WallBlock(FabricBlockSettings.copyOf(Blocks.SPRUCE_WOOD).burnable()), true);
    public static final Block STRIPPED_BIRCH_WOOD_WALL = registerWoodBlock("stripped_birch_wood_wall",
            new WallBlock(FabricBlockSettings.copyOf(Blocks.BIRCH_WOOD).burnable()), true);
    public static final Block STRIPPED_JUNGLE_WOOD_WALL = registerWoodBlock("stripped_jungle_wood_wall",
            new WallBlock(FabricBlockSettings.copyOf(Blocks.JUNGLE_WOOD).burnable()), true);
    public static final Block STRIPPED_ACACIA_WOOD_WALL = registerWoodBlock("stripped_acacia_wood_wall",
            new WallBlock(FabricBlockSettings.copyOf(Blocks.ACACIA_WOOD).burnable()), true);
    public static final Block STRIPPED_DARK_OAK_WOOD_WALL = registerWoodBlock("stripped_dark_oak_wood_wall",
            new WallBlock(FabricBlockSettings.copyOf(Blocks.DARK_OAK_WOOD).burnable()), true);
    public static final Block STRIPPED_MANGROVE_WOOD_WALL = registerWoodBlock("stripped_mangrove_wood_wall",
            new WallBlock(FabricBlockSettings.copyOf(Blocks.MANGROVE_WOOD).burnable()), true);
    public static final Block STRIPPED_CHERRY_WOOD_WALL = registerWoodBlock("stripped_cherry_wood_wall",
            new WallBlock(FabricBlockSettings.copyOf(Blocks.CHERRY_WOOD).burnable()), true);

    public static final Block STRIPPED_OAK_WOOD_FENCE = registerWoodBlock("stripped_oak_wood_fence",
            new FenceBlock(FabricBlockSettings.copyOf(Blocks.OAK_WOOD).burnable()), true);
    public static final Block STRIPPED_SPRUCE_WOOD_FENCE = registerWoodBlock("stripped_spruce_wood_fence",
            new FenceBlock(FabricBlockSettings.copyOf(Blocks.SPRUCE_WOOD).burnable()), true);
    public static final Block STRIPPED_BIRCH_WOOD_FENCE = registerWoodBlock("stripped_birch_wood_fence",
            new FenceBlock(FabricBlockSettings.copyOf(Blocks.BIRCH_WOOD).burnable()), true);
    public static final Block STRIPPED_JUNGLE_WOOD_FENCE = registerWoodBlock("stripped_jungle_wood_fence",
            new FenceBlock(FabricBlockSettings.copyOf(Blocks.JUNGLE_WOOD).burnable()), true);
    public static final Block STRIPPED_ACACIA_WOOD_FENCE = registerWoodBlock("stripped_acacia_wood_fence",
            new FenceBlock(FabricBlockSettings.copyOf(Blocks.ACACIA_WOOD).burnable()), true);
    public static final Block STRIPPED_DARK_OAK_WOOD_FENCE = registerWoodBlock("stripped_dark_oak_wood_fence",
            new FenceBlock(FabricBlockSettings.copyOf(Blocks.DARK_OAK_WOOD).burnable()), true);
    public static final Block STRIPPED_MANGROVE_WOOD_FENCE = registerWoodBlock("stripped_mangrove_wood_fence",
            new FenceBlock(FabricBlockSettings.copyOf(Blocks.MANGROVE_WOOD).burnable()), true);
    public static final Block STRIPPED_CHERRY_WOOD_FENCE = registerWoodBlock("stripped_cherry_wood_fence",
            new FenceBlock(FabricBlockSettings.copyOf(Blocks.CHERRY_WOOD).burnable()), true);
    
    public static final Block OAK_VERTICAL_SLAB = registerWoodBlock("oak_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.OAK_SLAB).burnable()), true);
    public static final Block SPRUCE_VERTICAL_SLAB = registerWoodBlock("spruce_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.SPRUCE_SLAB).burnable()), true);
    public static final Block BIRCH_VERTICAL_SLAB = registerWoodBlock("birch_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.BIRCH_SLAB).burnable()), true);
    public static final Block JUNGLE_VERTICAL_SLAB = registerWoodBlock("jungle_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.JUNGLE_SLAB).burnable()), true);
    public static final Block ACACIA_VERTICAL_SLAB = registerWoodBlock("acacia_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.ACACIA_SLAB).burnable()), true);
    public static final Block DARK_OAK_VERTICAL_SLAB = registerWoodBlock("dark_oak_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.DARK_OAK_SLAB).burnable()), true);
    public static final Block MANGROVE_VERTICAL_SLAB = registerWoodBlock("mangrove_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.MANGROVE_SLAB).burnable()), true);
    public static final Block CHERRY_VERTICAL_SLAB = registerWoodBlock("cherry_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.CHERRY_SLAB).burnable()), true);
    public static final Block BAMBOO_VERTICAL_SLAB = registerWoodBlock("bamboo_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.BAMBOO_SLAB).burnable()), true);
    public static final Block CRIMSON_VERTICAL_SLAB = registerWoodBlock("crimson_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.CRIMSON_SLAB).burnable()), true);
    public static final Block WARPED_VERTICAL_SLAB = registerWoodBlock("warped_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.WARPED_SLAB).burnable()), true);

    public static final Block STONE_VERTICAL_SLAB = registerStoneBlock("stone_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.STONE_SLAB)), true);
    public static final Block COBBLESTONE_VERTICAL_SLAB = registerStoneBlock("cobblestone_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.COBBLESTONE_SLAB)), true);
    public static final Block MOSSY_COBBLESTONE_VERTICAL_SLAB = registerStoneBlock("mossy_cobblestone_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.MOSSY_COBBLESTONE_SLAB)), true);
    public static final Block SMOOTH_STONE_VERTICAL_SLAB = registerStoneBlock("smooth_stone_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.SMOOTH_STONE_SLAB)), true);
    public static final Block STONE_BRICK_VERTICAL_SLAB = registerStoneBlock("stone_brick_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.STONE_BRICK_SLAB)), true);
    public static final Block MOSSY_STONE_BRICK_VERTICAL_SLAB = registerStoneBlock("mossy_stone_brick_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.MOSSY_STONE_BRICK_SLAB)), true);
    public static final Block GRANITE_VERTICAL_SLAB = registerStoneBlock("granite_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.GRANITE_SLAB)), true);
    public static final Block POLISHED_GRANITE_VERTICAL_SLAB = registerStoneBlock("polished_granite_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.POLISHED_GRANITE_SLAB)), true);
    public static final Block DIORITE_VERTICAL_SLAB = registerStoneBlock("diorite_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.DIORITE_SLAB)), true);
    public static final Block POLISHED_DIORITE_VERTICAL_SLAB = registerStoneBlock("polished_diorite_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.POLISHED_DIORITE_SLAB)), true);
    public static final Block ANDESITE_VERTICAL_SLAB = registerStoneBlock("andesite_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.ANDESITE_SLAB)), true);
    public static final Block POLISHED_ANDESITE_VERTICAL_SLAB = registerStoneBlock("polished_andesite_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.POLISHED_ANDESITE_SLAB)), true);
    public static final Block COBBLED_DEEPSLATE_VERTICAL_SLAB = registerStoneBlock("cobbled_deepslate_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.COBBLED_DEEPSLATE_SLAB)), true);
    public static final Block POLISHED_DEEPSLATE_VERTICAL_SLAB = registerStoneBlock("polished_deepslate_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.POLISHED_DEEPSLATE_SLAB)), true);
    public static final Block DEEPSLATE_BRICK_VERTICAL_SLAB = registerStoneBlock("deepslate_brick_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.DEEPSLATE_BRICK_SLAB)), true);
    public static final Block DEEPSLATE_TILE_VERTICAL_SLAB = registerStoneBlock("deepslate_tile_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.DEEPSLATE_TILE_SLAB)), true);
    public static final Block BRICK_VERTICAL_SLAB = registerStoneBlock("brick_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.BRICK_SLAB)), true);
    public static final Block MUD_BRICK_VERTICAL_SLAB = registerStoneBlock("mud_brick_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.MUD_BRICK_SLAB)), true);
    public static final Block SANDSTONE_VERTICAL_SLAB = registerStoneBlock("sandstone_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.SANDSTONE_SLAB)), true);
    public static final Block SMOOTH_SANDSTONE_VERTICAL_SLAB = registerStoneBlock("smooth_sandstone_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.SMOOTH_SANDSTONE_SLAB)), true);
    public static final Block CUT_SANDSTONE_VERTICAL_SLAB = registerStoneBlock("cut_sandstone_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.CUT_SANDSTONE_SLAB)), true);
    public static final Block RED_SANDSTONE_VERTICAL_SLAB = registerStoneBlock("red_sandstone_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.RED_SANDSTONE_SLAB)), true);
    public static final Block SMOOTH_RED_SANDSTONE_VERTICAL_SLAB = registerStoneBlock("smooth_red_sandstone_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.SMOOTH_RED_SANDSTONE_SLAB)), true);
    public static final Block CUT_RED_SANDSTONE_VERTICAL_SLAB = registerStoneBlock("cut_red_sandstone_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.CUT_RED_SANDSTONE_SLAB)), true);
    public static final Block PRISMARINE_VERTICAL_SLAB = registerStoneBlock("prismarine_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.PRISMARINE_SLAB)), true);
    public static final Block PRISMARINE_BRICK_VERTICAL_SLAB = registerStoneBlock("prismarine_brick_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.PRISMARINE_BRICK_SLAB)), true);
    public static final Block DARK_PRISMARINE_VERTICAL_SLAB = registerStoneBlock("dark_prismarine_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.DARK_PRISMARINE_SLAB)), true);
    public static final Block NETHER_BRICK_VERTICAL_SLAB = registerStoneBlock("nether_brick_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.NETHER_BRICK_SLAB)), true);
    public static final Block RED_NETHER_BRICK_VERTICAL_SLAB = registerStoneBlock("red_nether_brick_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.RED_NETHER_BRICK_SLAB)), true);
    public static final Block BLACKSTONE_VERTICAL_SLAB = registerStoneBlock("blackstone_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.BLACKSTONE_SLAB)), true);
    public static final Block POLISHED_BLACKSTONE_VERTICAL_SLAB = registerStoneBlock("polished_blackstone_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.POLISHED_BLACKSTONE_SLAB)), true);
    public static final Block POLISHED_BLACKSTONE_BRICK_VERTICAL_SLAB = registerStoneBlock("polished_blackstone_brick_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.POLISHED_BLACKSTONE_BRICK_SLAB)), true);
    public static final Block END_STONE_BRICK_VERTICAL_SLAB = registerStoneBlock("end_stone_brick_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.END_STONE_BRICK_SLAB)), true);
    public static final Block PURPUR_VERTICAL_SLAB = registerStoneBlock("purpur_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.PURPUR_SLAB)), true);
    public static final Block QUARTZ_VERTICAL_SLAB = registerStoneBlock("quartz_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.QUARTZ_SLAB)), true);
    public static final Block SMOOTH_QUARTZ_VERTICAL_SLAB = registerStoneBlock("smooth_quartz_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.SMOOTH_QUARTZ_SLAB)), true);
    public static final Block CUT_COPPER_VERTICAL_SLAB = registerMiscBlock("cut_copper_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.CUT_COPPER_SLAB)), true);
    public static final Block EXPOSED_CUT_COPPER_VERTICAL_SLAB = registerMiscBlock("exposed_cut_copper_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.EXPOSED_CUT_COPPER_SLAB)), true);
    public static final Block WEATHERED_CUT_COPPER_VERTICAL_SLAB = registerMiscBlock("weathered_cut_copper_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.WEATHERED_CUT_COPPER_SLAB)), true);
    public static final Block OXIDIZED_CUT_COPPER_VERTICAL_SLAB = registerMiscBlock("oxidized_cut_copper_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.OXIDIZED_CUT_COPPER_SLAB)), true);
    public static final Block WAXED_CUT_COPPER_VERTICAL_SLAB = registerMiscBlock("waxed_cut_copper_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.WAXED_CUT_COPPER_SLAB)), true);
    public static final Block WAXED_EXPOSED_CUT_COPPER_VERTICAL_SLAB = registerMiscBlock("waxed_exposed_cut_copper_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.WAXED_EXPOSED_CUT_COPPER_SLAB)), true);
    public static final Block WAXED_WEATHERED_CUT_COPPER_VERTICAL_SLAB = registerMiscBlock("waxed_weathered_cut_copper_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.WAXED_WEATHERED_CUT_COPPER_SLAB)), true);
    public static final Block WAXED_OXIDIZED_CUT_COPPER_VERTICAL_SLAB = registerMiscBlock("waxed_oxidized_cut_copper_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.WAXED_OXIDIZED_CUT_COPPER_SLAB)), true);

    public static final Block BLACK_WOOL_VERTICAL_SLAB = registerMiscBlock("black_wool_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.WHITE_WOOL).burnable()), true);
    public static final Block BLUE_WOOL_VERTICAL_SLAB = registerMiscBlock("blue_wool_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.WHITE_WOOL).burnable()), true);
    public static final Block BROWN_WOOL_VERTICAL_SLAB = registerMiscBlock("brown_wool_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.WHITE_WOOL).burnable()), true);
    public static final Block CYAN_WOOL_VERTICAL_SLAB = registerMiscBlock("cyan_wool_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.WHITE_WOOL).burnable()), true);
    public static final Block GRAY_WOOL_VERTICAL_SLAB = registerMiscBlock("gray_wool_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.WHITE_WOOL).burnable()), true);
    public static final Block GREEN_WOOL_VERTICAL_SLAB = registerMiscBlock("green_wool_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.WHITE_WOOL).burnable()), true);
    public static final Block LIGHT_BLUE_WOOL_VERTICAL_SLAB = registerMiscBlock("light_blue_wool_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.WHITE_WOOL).burnable()), true);
    public static final Block LIGHT_GRAY_WOOL_VERTICAL_SLAB = registerMiscBlock("light_gray_wool_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.WHITE_WOOL).burnable()), true);
    public static final Block LIME_WOOL_VERTICAL_SLAB = registerMiscBlock("lime_wool_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.WHITE_WOOL).burnable()), true);
    public static final Block MAGENTA_WOOL_VERTICAL_SLAB = registerMiscBlock("magenta_wool_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.WHITE_WOOL).burnable()), true);
    public static final Block ORANGE_WOOL_VERTICAL_SLAB = registerMiscBlock("orange_wool_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.WHITE_WOOL).burnable()), true);
    public static final Block PINK_WOOL_VERTICAL_SLAB = registerMiscBlock("pink_wool_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.WHITE_WOOL).burnable()), true);
    public static final Block PURPLE_WOOL_VERTICAL_SLAB = registerMiscBlock("purple_wool_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.WHITE_WOOL).burnable()), true);
    public static final Block RED_WOOL_VERTICAL_SLAB = registerMiscBlock("red_wool_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.WHITE_WOOL).burnable()), true);
    public static final Block WHITE_WOOL_VERTICAL_SLAB = registerMiscBlock("white_wool_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.WHITE_WOOL).burnable()), true);
    public static final Block YELLOW_WOOL_VERTICAL_SLAB = registerMiscBlock("yellow_wool_vertical_slab",
            new VerticalSlabBlock(FabricBlockSettings.copyOf(Blocks.WHITE_WOOL).burnable()), true);
    //endregion

    //region VANILLA STAIRS
    public static final Block DIRT_STAIRS = registerMiscBlock("dirt_stairs",
            new StairsBlock(Blocks.DIRT.getDefaultState(), FabricBlockSettings.copyOf(Blocks.DIRT)), true);
    public static final Block COARSE_DIRT_STAIRS = registerMiscBlock("coarse_dirt_stairs",
            new StairsBlock(Blocks.COARSE_DIRT.getDefaultState(), FabricBlockSettings.copyOf(Blocks.COARSE_DIRT)), true);
    public static final Block MUD_STAIRS = registerMiscBlock("mud_stairs",
            new StairsBlock(Blocks.MUD.getDefaultState(), FabricBlockSettings.copyOf(Blocks.MUD)), true);

    public static final Block BLACK_WOOL_STAIRS = registerMiscBlock("black_wool_stairs",
            new StairsBlock(Blocks.BLACK_WOOL.getDefaultState(), FabricBlockSettings.copyOf(Blocks.WHITE_WOOL).burnable()), true);
    public static final Block BLUE_WOOL_STAIRS = registerMiscBlock("blue_wool_stairs",
            new StairsBlock(Blocks.BLACK_WOOL.getDefaultState(), FabricBlockSettings.copyOf(Blocks.WHITE_WOOL).burnable()), true);
    public static final Block BROWN_WOOL_STAIRS = registerMiscBlock("brown_wool_stairs",
            new StairsBlock(Blocks.BLACK_WOOL.getDefaultState(), FabricBlockSettings.copyOf(Blocks.WHITE_WOOL).burnable()), true);
    public static final Block CYAN_WOOL_STAIRS = registerMiscBlock("cyan_wool_stairs",
            new StairsBlock(Blocks.BLACK_WOOL.getDefaultState(), FabricBlockSettings.copyOf(Blocks.WHITE_WOOL).burnable()), true);
    public static final Block GRAY_WOOL_STAIRS = registerMiscBlock("gray_wool_stairs",
            new StairsBlock(Blocks.BLACK_WOOL.getDefaultState(), FabricBlockSettings.copyOf(Blocks.WHITE_WOOL).burnable()), true);
    public static final Block GREEN_WOOL_STAIRS = registerMiscBlock("green_wool_stairs",
            new StairsBlock(Blocks.BLACK_WOOL.getDefaultState(), FabricBlockSettings.copyOf(Blocks.WHITE_WOOL).burnable()), true);
    public static final Block LIGHT_BLUE_WOOL_STAIRS = registerMiscBlock("light_blue_wool_stairs",
            new StairsBlock(Blocks.BLACK_WOOL.getDefaultState(), FabricBlockSettings.copyOf(Blocks.WHITE_WOOL).burnable()), true);
    public static final Block LIGHT_GRAY_WOOL_STAIRS = registerMiscBlock("light_gray_wool_stairs",
            new StairsBlock(Blocks.BLACK_WOOL.getDefaultState(), FabricBlockSettings.copyOf(Blocks.WHITE_WOOL).burnable()), true);
    public static final Block LIME_WOOL_STAIRS = registerMiscBlock("lime_wool_stairs",
            new StairsBlock(Blocks.BLACK_WOOL.getDefaultState(), FabricBlockSettings.copyOf(Blocks.WHITE_WOOL).burnable()), true);
    public static final Block MAGENTA_WOOL_STAIRS = registerMiscBlock("magenta_wool_stairs",
            new StairsBlock(Blocks.BLACK_WOOL.getDefaultState(), FabricBlockSettings.copyOf(Blocks.WHITE_WOOL).burnable()), true);
    public static final Block ORANGE_WOOL_STAIRS = registerMiscBlock("orange_wool_stairs",
            new StairsBlock(Blocks.BLACK_WOOL.getDefaultState(), FabricBlockSettings.copyOf(Blocks.WHITE_WOOL).burnable()), true);
    public static final Block PINK_WOOL_STAIRS = registerMiscBlock("pink_wool_stairs",
            new StairsBlock(Blocks.BLACK_WOOL.getDefaultState(), FabricBlockSettings.copyOf(Blocks.WHITE_WOOL).burnable()), true);
    public static final Block PURPLE_WOOL_STAIRS = registerMiscBlock("purple_wool_stairs",
            new StairsBlock(Blocks.BLACK_WOOL.getDefaultState(), FabricBlockSettings.copyOf(Blocks.WHITE_WOOL).burnable()), true);
    public static final Block RED_WOOL_STAIRS = registerMiscBlock("red_wool_stairs",
            new StairsBlock(Blocks.BLACK_WOOL.getDefaultState(), FabricBlockSettings.copyOf(Blocks.WHITE_WOOL).burnable()), true);
    public static final Block WHITE_WOOL_STAIRS = registerMiscBlock("white_wool_stairs",
            new StairsBlock(Blocks.BLACK_WOOL.getDefaultState(), FabricBlockSettings.copyOf(Blocks.WHITE_WOOL).burnable()), true);
    public static final Block YELLOW_WOOL_STAIRS = registerMiscBlock("yellow_wool_stairs",
            new StairsBlock(Blocks.BLACK_WOOL.getDefaultState(), FabricBlockSettings.copyOf(Blocks.WHITE_WOOL).burnable()), true);
    //endregion

    //region VANILLA LAYERS
    public static final Block GRAVEL_LAYER = registerMiscBlock("gravel_layer",
            new LayersBlock(FabricBlockSettings.copyOf(Blocks.GRAVEL), Blocks.GRAVEL), false);
    public static final Block SAND_LAYER = registerMiscBlock("sand_layer",
            new LayersBlock(FabricBlockSettings.copyOf(Blocks.SAND), Blocks.SAND), false);
    public static final Block BLACK_SAND_LAYER = registerMiscBlock("black_sand_layer",
            new LayersBlock(FabricBlockSettings.copyOf(Blocks.SAND), BLACK_SAND), false);
    public static final Block WHITE_SAND_LAYER = registerMiscBlock("white_sand_layer",
            new LayersBlock(FabricBlockSettings.copyOf(Blocks.SAND), WHITE_SAND), false);
    //endregion

    public static Block registerBlock(String name, Block block, boolean drop) {
        if(drop){
            BlockDrops.blocks.add(block);
        }
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, new Identifier(MiddleEarth.MOD_ID, name), block);
    }

    public static Block registerStoneBlock(String name, Block block, boolean drop) {
        if(drop){
            BlockDrops.blocks.add(block);
        }
        registerBlockItem(name, block);
        ModItemGroups.STONE_BLOCKS_CONTENTS.add(block.asItem().getDefaultStack());
        return Registry.register(Registries.BLOCK, new Identifier(MiddleEarth.MOD_ID, name), block);
    }

    public static Block registerWoodBlock(String name, Block block, boolean drop) {
        if(drop){
            BlockDrops.blocks.add(block);
        }
        registerBlockItem(name, block);
        ModItemGroups.WOOD_BLOCKS_CONTENTS.add(block.asItem().getDefaultStack());
        return Registry.register(Registries.BLOCK, new Identifier(MiddleEarth.MOD_ID, name), block);
    }

    public static Block registerMiscBlock(String name, Block block, boolean drop) {
        if(drop){
            BlockDrops.blocks.add(block);
        }
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
        LoggerUtil.getInstance().logDebugMsg("Registering ModBlocks for " + MiddleEarth.MOD_ID);
    }

    public static boolean never(BlockState state, BlockView world, BlockPos pos) {
        return false;
    }

    public static Boolean canSpawnOnLeaves(BlockState state, BlockView world, BlockPos pos, EntityType<?> type) {
        return type == EntityType.OCELOT || type == EntityType.PARROT;
    }

    public static void registerFlammableBlocks(){
        FlammableBlockRegistry.getDefaultInstance().add(WHITE_WOOL_SLAB, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(ORANGE_WOOL_SLAB, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(MAGENTA_WOOL_SLAB, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(LIGHT_BLUE_WOOL_SLAB, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(YELLOW_WOOL_SLAB, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(LIME_WOOL_SLAB, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(PINK_WOOL_SLAB, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(GRAY_WOOL_SLAB, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(LIGHT_GRAY_WOOL_SLAB, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(CYAN_WOOL_SLAB, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(PURPLE_WOOL_SLAB, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(BLUE_WOOL_SLAB, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(BROWN_WOOL_SLAB, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(GREEN_WOOL_SLAB, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(RED_WOOL_SLAB, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(BLACK_WOOL_SLAB, 30, 60);
        
        FlammableBlockRegistry.getDefaultInstance().add(WHITE_WOOL_VERTICAL_SLAB, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(ORANGE_WOOL_VERTICAL_SLAB, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(MAGENTA_WOOL_VERTICAL_SLAB, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(LIGHT_BLUE_WOOL_VERTICAL_SLAB, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(YELLOW_WOOL_VERTICAL_SLAB, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(LIME_WOOL_VERTICAL_SLAB, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(PINK_WOOL_VERTICAL_SLAB, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(GRAY_WOOL_VERTICAL_SLAB, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(LIGHT_GRAY_WOOL_VERTICAL_SLAB, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(CYAN_WOOL_VERTICAL_SLAB, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(PURPLE_WOOL_VERTICAL_SLAB, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(BLUE_WOOL_VERTICAL_SLAB, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(BROWN_WOOL_VERTICAL_SLAB, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(GREEN_WOOL_VERTICAL_SLAB, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(RED_WOOL_VERTICAL_SLAB, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(BLACK_WOOL_VERTICAL_SLAB, 30, 60);

        FlammableBlockRegistry.getDefaultInstance().add(WHITE_WOOL_STAIRS, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(ORANGE_WOOL_STAIRS, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(MAGENTA_WOOL_STAIRS, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(LIGHT_BLUE_WOOL_STAIRS, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(YELLOW_WOOL_STAIRS, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(LIME_WOOL_STAIRS, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(PINK_WOOL_STAIRS, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(GRAY_WOOL_STAIRS, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(LIGHT_GRAY_WOOL_STAIRS, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(CYAN_WOOL_STAIRS, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(PURPLE_WOOL_STAIRS, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(BLUE_WOOL_STAIRS, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(BROWN_WOOL_STAIRS, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(GREEN_WOOL_STAIRS, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(RED_WOOL_STAIRS, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(BLACK_WOOL_STAIRS, 30, 60);
    }
}
