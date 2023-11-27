package net.jukoz.me.block;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.jukoz.me.MiddleEarth;
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

    public static final Block CALCITE_PILLAR = registerBlock("calcite_pillar",
            new PillarBlock(AbstractBlock.Settings.copy(Blocks.QUARTZ_PILLAR).strength(SimpleBlockSets.STONE_STRENGTH).sounds(BlockSoundGroup.STONE)));

    public static final Block LIMESTONE_PILLAR = registerBlock("limestone_pillar",
            new PillarBlock(AbstractBlock.Settings.copy(Blocks.QUARTZ_PILLAR).strength(SimpleBlockSets.STONE_STRENGTH).sounds(BlockSoundGroup.STONE)));

    public static final Block ANDESITE_PILLAR = registerBlock("andesite_pillar",
            new PillarBlock(AbstractBlock.Settings.copy(Blocks.QUARTZ_PILLAR).strength(SimpleBlockSets.STONE_STRENGTH).sounds(BlockSoundGroup.STONE)));
    public static final Block MOSSY_ANDESITE_PILLAR = registerBlock("mossy_andesite_pillar",
            new PillarBlock(AbstractBlock.Settings.copy(Blocks.QUARTZ_PILLAR).strength(SimpleBlockSets.STONE_STRENGTH).sounds(BlockSoundGroup.STONE)));
    public static final Block CRACKED_ANDESITE_PILLAR = registerBlock("cracked_andesite_pillar",
            new PillarBlock(AbstractBlock.Settings.copy(Blocks.QUARTZ_PILLAR).strength(SimpleBlockSets.STONE_STRENGTH).sounds(BlockSoundGroup.STONE)));

    public static final Block GRANITE_PILLAR = registerBlock("granite_pillar",
            new PillarBlock(AbstractBlock.Settings.copy(Blocks.QUARTZ_PILLAR).strength(SimpleBlockSets.STONE_STRENGTH).sounds(BlockSoundGroup.STONE)));
    public static final Block MOSSY_GRANITE_PILLAR = registerBlock("mossy_granite_pillar",
            new PillarBlock(AbstractBlock.Settings.copy(Blocks.QUARTZ_PILLAR).strength(SimpleBlockSets.STONE_STRENGTH).sounds(BlockSoundGroup.STONE)));
    public static final Block CRACKED_GRANITE_PILLAR = registerBlock("cracked_granite_pillar",
            new PillarBlock(AbstractBlock.Settings.copy(Blocks.QUARTZ_PILLAR).strength(SimpleBlockSets.STONE_STRENGTH).sounds(BlockSoundGroup.STONE)));

    public static final Block DIORITE_PILLAR = registerBlock("diorite_pillar",
            new PillarBlock(AbstractBlock.Settings.copy(Blocks.QUARTZ_PILLAR).strength(SimpleBlockSets.STONE_STRENGTH).sounds(BlockSoundGroup.STONE)));
    public static final Block MOSSY_DIORITE_PILLAR = registerBlock("mossy_diorite_pillar",
            new PillarBlock(AbstractBlock.Settings.copy(Blocks.QUARTZ_PILLAR).strength(SimpleBlockSets.STONE_STRENGTH).sounds(BlockSoundGroup.STONE)));
    public static final Block CRACKED_DIORITE_PILLAR = registerBlock("cracked_diorite_pillar",
            new PillarBlock(AbstractBlock.Settings.copy(Blocks.QUARTZ_PILLAR).strength(SimpleBlockSets.STONE_STRENGTH).sounds(BlockSoundGroup.STONE)));

    public static final Block JADEITE_PILLAR = registerBlock("jadeite_pillar",
            new PillarBlock(AbstractBlock.Settings.copy(Blocks.QUARTZ_PILLAR).strength(SimpleBlockSets.STONE_STRENGTH).sounds(BlockSoundGroup.STONE)));

    public static final Block CHISELED_BLUE_ROCK_BRICKS = registerBlock("chiseled_gonluin_bricks",
            new Block(FabricBlockSettings.copyOf(Blocks.CHISELED_STONE_BRICKS).strength(SimpleBlockSets.STONE_STRENGTH).sounds(BlockSoundGroup.STONE)));

    public static final Block CHISELED_CALCITE_BRICKS = registerBlock("chiseled_calcite_bricks",
            new Block(FabricBlockSettings.copyOf(Blocks.CHISELED_STONE_BRICKS).strength(SimpleBlockSets.STONE_STRENGTH).sounds(BlockSoundGroup.STONE)));

    public static final Block CHISELED_LIMESTONE_BRICKS = registerBlock("chiseled_limestone_bricks",
            new Block(FabricBlockSettings.copyOf(Blocks.CHISELED_STONE_BRICKS).strength(SimpleBlockSets.STONE_STRENGTH).sounds(BlockSoundGroup.STONE)));

    public static final Block CHISELED_ANDESITE_BRICKS = registerBlock("chiseled_andesite_bricks",
            new Block(FabricBlockSettings.copyOf(Blocks.CHISELED_STONE_BRICKS).strength(SimpleBlockSets.STONE_STRENGTH).sounds(BlockSoundGroup.STONE)));
    public static final Block CUT_POLISHED_ANDESITE = registerBlock("cut_polished_andesite",
            new Block(FabricBlockSettings.copyOf(Blocks.CHISELED_STONE_BRICKS).strength(SimpleBlockSets.STONE_STRENGTH).sounds(BlockSoundGroup.STONE)));
    
    public static final Block CHISELED_GRANITE_BRICKS = registerBlock("chiseled_granite_bricks",
            new Block(FabricBlockSettings.copyOf(Blocks.CHISELED_STONE_BRICKS).strength(SimpleBlockSets.STONE_STRENGTH).sounds(BlockSoundGroup.STONE)));
    public static final Block CUT_POLISHED_GRANITE = registerBlock("cut_polished_granite",
            new Block(FabricBlockSettings.copyOf(Blocks.CHISELED_STONE_BRICKS).strength(SimpleBlockSets.STONE_STRENGTH).sounds(BlockSoundGroup.STONE)));

    public static final Block CHISELED_DIORITE_BRICKS = registerBlock("chiseled_diorite_bricks",
            new Block(FabricBlockSettings.copyOf(Blocks.CHISELED_STONE_BRICKS).strength(SimpleBlockSets.STONE_STRENGTH).sounds(BlockSoundGroup.STONE)));
    public static final Block CUT_POLISHED_DIORITE = registerBlock("cut_polished_diorite",
            new Block(FabricBlockSettings.copyOf(Blocks.CHISELED_STONE_BRICKS).strength(SimpleBlockSets.STONE_STRENGTH).sounds(BlockSoundGroup.STONE)));

    public static final Block DRY_DIRT = registerBlock("dry_dirt",
            new Block(FabricBlockSettings.copyOf(Blocks.DIRT).strength(DIRT_STRENGTH).sounds(BlockSoundGroup.GRAVEL)));
    public static final Block MORDOR_DIRT = registerBlock("mordor_dirt",
            new Block(FabricBlockSettings.copyOf(Blocks.DIRT).strength(DIRT_STRENGTH).sounds(BlockSoundGroup.GRAVEL)));

    public static final Block REED_BLOCK = registerBlock("reed_block",
            new HayBlock(FabricBlockSettings.copyOf(Blocks.HAY_BLOCK).strength(DIRT_STRENGTH).sounds(BlockSoundGroup.GRASS)));
    public static final Block REED_STAIRS = registerBlock("reed_stairs",
            new StairsBlock(REED_BLOCK.getDefaultState(), FabricBlockSettings.copyOf(Blocks.HAY_BLOCK).strength(DIRT_STRENGTH).sounds(BlockSoundGroup.GRASS)));
    public static final Block REED_SLAB = registerBlock("reed_slab",
            new SlabBlock(FabricBlockSettings.copyOf(Blocks.HAY_BLOCK).strength(DIRT_STRENGTH).sounds(BlockSoundGroup.GRASS)));
    public static final Block REED_WALL = registerBlock("reed_wall",
            new WallBlock(FabricBlockSettings.copyOf(Blocks.HAY_BLOCK).strength(DIRT_STRENGTH).sounds(BlockSoundGroup.GRASS)));
    public static final Block STRAW_BLOCK = registerBlock("straw_block",
            new HayBlock(FabricBlockSettings.copyOf(Blocks.HAY_BLOCK).strength(DIRT_STRENGTH).sounds(BlockSoundGroup.GRASS)));
    public static final Block STRAW_STAIRS = registerBlock("straw_stairs",
            new StairsBlock(STRAW_BLOCK.getDefaultState(), FabricBlockSettings.copyOf(Blocks.HAY_BLOCK).strength(DIRT_STRENGTH).sounds(BlockSoundGroup.GRASS)));
    public static final Block STRAW_SLAB = registerBlock("straw_slab",
            new SlabBlock(FabricBlockSettings.copyOf(Blocks.HAY_BLOCK).strength(DIRT_STRENGTH).sounds(BlockSoundGroup.GRASS)));
    public static final Block STRAW_WALL = registerBlock("straw_wall",
            new WallBlock(FabricBlockSettings.copyOf(Blocks.HAY_BLOCK).strength(DIRT_STRENGTH).sounds(BlockSoundGroup.GRASS)));

    public static final Block MITHRIL_ORE = registerBlock("mithril_ore",
            new Block(FabricBlockSettings.copyOf(Blocks.STONE).strength(4f).requiresTool()));
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
            new HayBlock(FabricBlockSettings.copyOf(Blocks.CUT_COPPER).sounds(BlockSoundGroup.COPPER)));
    public static final Block CUT_LEAD_STAIRS = registerBlock("cut_lead_stairs",
            new StairsBlock(REED_BLOCK.getDefaultState(), FabricBlockSettings.copyOf(Blocks.CUT_COPPER).sounds(BlockSoundGroup.COPPER)));
    public static final Block CUT_LEAD_SLAB = registerBlock("cut_lead_slab",
            new SlabBlock(FabricBlockSettings.copyOf(Blocks.CUT_COPPER).sounds(BlockSoundGroup.COPPER)));

    public static final Block STONE_TRAPDOOR = registerBlock("stone_trapdoor",
            new TrapdoorBlock(FabricBlockSettings.copyOf(Blocks.OAK_TRAPDOOR).strength(SimpleBlockSets.STONE_STRENGTH).sounds(BlockSoundGroup.STONE), BlockSetType.STONE));
    public static final Block BLACKSTONE_TRAPDOOR = registerBlock("blackstone_trapdoor",
            new TrapdoorBlock(FabricBlockSettings.copyOf(Blocks.OAK_TRAPDOOR).strength(SimpleBlockSets.STONE_STRENGTH).sounds(BlockSoundGroup.STONE), BlockSetType.POLISHED_BLACKSTONE));

    public static final Block SILVES_BARS = registerBlock("silver_bars",
            new PaneBlock(FabricBlockSettings.copyOf(Blocks.IRON_BARS).sounds(BlockSoundGroup.METAL)));

    public static final Block RIVER_SAND = registerBlock("river_sand",
            new FallingBlock(FabricBlockSettings.copyOf(Blocks.SAND).sounds(BlockSoundGroup.SAND)));


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
