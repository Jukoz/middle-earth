package net.jesteur.me.block;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.jesteur.me.MiddleEarth;
import net.jesteur.me.item.utils.ModItemGroups;
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

    public static final Block CHISELED_BLUE_ROCK_BRICKS = registerBlock("chiseled_blue_rock_bricks",
            new Block(FabricBlockSettings.copyOf(Blocks.CHISELED_STONE_BRICKS).strength(SimpleBlockSets.STONE_STRENGTH).sounds(BlockSoundGroup.STONE)));
    public static final Block CHISELED_CALCITE = registerBlock("chiseled_calcite",
            new Block(FabricBlockSettings.copyOf(Blocks.CHISELED_STONE_BRICKS).strength(SimpleBlockSets.STONE_STRENGTH).sounds(BlockSoundGroup.STONE)));
    public static final Block CHISELED_LIMESTONE_BRICKS = registerBlock("chiseled_limestone_bricks",
            new Block(FabricBlockSettings.copyOf(Blocks.CHISELED_STONE_BRICKS).strength(SimpleBlockSets.STONE_STRENGTH).sounds(BlockSoundGroup.STONE)));

    public static final Block DRY_DIRT = registerBlock("dry_dirt",
            new Block(FabricBlockSettings.copyOf(Blocks.DIRT).strength(DIRT_STRENGTH).sounds(BlockSoundGroup.GRAVEL)));
    public static final Block MORDOR_DIRT = registerBlock("mordor_dirt",
            new Block(FabricBlockSettings.copyOf(Blocks.DIRT).strength(DIRT_STRENGTH).sounds(BlockSoundGroup.GRAVEL)));

    public static final Block REEDS_BLOCK = registerBlock("reeds_block",
            new HayBlock(FabricBlockSettings.copyOf(Blocks.HAY_BLOCK).strength(DIRT_STRENGTH).sounds(BlockSoundGroup.GRASS)));
    public static final Block STRAW_BLOCK = registerBlock("straw_block",
            new HayBlock(FabricBlockSettings.copyOf(Blocks.HAY_BLOCK).strength(DIRT_STRENGTH).sounds(BlockSoundGroup.GRASS)));

    public static final Block MITHRIL_BLOCK = registerBlock("mithril_block",
            new Block(FabricBlockSettings.copyOf(Blocks.IRON_BLOCK).strength(6f).requiresTool()));
    public static final Block MITHRIL_ORE = registerBlock("mithril_ore",
            new Block(FabricBlockSettings.copyOf(Blocks.STONE).strength(4f).requiresTool()));

    public static final Block STONE_TRAPDOOR = registerBlock("stone_trapdoor",
            new TrapdoorBlock(FabricBlockSettings.copyOf(Blocks.OAK_TRAPDOOR).strength(SimpleBlockSets.STONE_STRENGTH).sounds(BlockSoundGroup.STONE), BlockSetType.STONE));
    public static final Block BLACKSTONE_TRAPDOOR = registerBlock("blackstone_trapdoor",
            new TrapdoorBlock(FabricBlockSettings.copyOf(Blocks.OAK_TRAPDOOR).strength(SimpleBlockSets.STONE_STRENGTH).sounds(BlockSoundGroup.STONE), BlockSetType.POLISHED_BLACKSTONE));

    public static Block registerBlock(String name, Block block) {
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, new Identifier(MiddleEarth.MOD_ID, name), block);
    }

    static Item registerBlockItem(String name, Block block) {
        var item =  Registry.register(Registries.ITEM, new Identifier(MiddleEarth.MOD_ID, name),
                new BlockItem(block, new FabricItemSettings()));
        Item.BLOCK_ITEMS.put(block, item);
        ModItemGroups.BLOCKS_CONTENTS.add(item.getDefaultStack());
        return item;
    }

    public static void registerModBlocks() {
        MiddleEarth.LOGGER.debug("Registering ModBlocks for " + MiddleEarth.MOD_ID);
    }
}
