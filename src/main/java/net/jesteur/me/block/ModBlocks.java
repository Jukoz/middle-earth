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

@SuppressWarnings("unused")
public class ModBlocks {
    public static final float STONE_STRENGTH = 1.5f;
    public static final float WOOD_STRENGTH = 2f;
    public static final float DIRT_STRENGTH = 0.6f;
    public static final float SLAB_RESISTANCE = 6.0f; // From explosions

    // Gondor Bricks
    public static final Block GONDOR_BRICKS = registerBlock("gondor_bricks",
            new Block(FabricBlockSettings.of(Material.STONE).strength(STONE_STRENGTH).requiresTool()));
    public static final Block GONDOR_BRICKS_SLAB = registerBlock("gondor_bricks_slab",
            new SlabBlock(FabricBlockSettings.of(Material.STONE).strength(STONE_STRENGTH, SLAB_RESISTANCE).requiresTool()));
    public static final Block GONDOR_BRICKS_STAIRS = registerBlock("gondor_bricks_stairs",
            new StairsBlock(GONDOR_BRICKS.getDefaultState(), AbstractBlock.Settings.copy(GONDOR_BRICKS).strength(STONE_STRENGTH).requiresTool()));
    public static final Block GONDOR_BRICKS_WALL = registerBlock("gondor_bricks_wall",
            new WallBlock(AbstractBlock.Settings.copy(GONDOR_BRICKS).strength(STONE_STRENGTH).requiresTool()));

    // Blue Rock
    public static final Block BLUE_ROCK = registerBlock("blue_rock",
            new Block(FabricBlockSettings.of(Material.STONE).strength(STONE_STRENGTH, SLAB_RESISTANCE).requiresTool()));
    public static final Block BLUE_ROCK_SLAB = registerBlock("blue_rock_slab",
            new SlabBlock(FabricBlockSettings.of(Material.STONE).strength(STONE_STRENGTH).requiresTool()));

    // Ashen Rock
    public static final Block ASHEN_ROCK = registerBlock("ashen_rock",
            new Block(FabricBlockSettings.of(Material.STONE).strength(STONE_STRENGTH).requiresTool()));
    public static final Block ASHEN_ROCK_SLAB = registerBlock("ashen_rock_slab",
            new SlabBlock(FabricBlockSettings.of(Material.STONE).strength(STONE_STRENGTH, SLAB_RESISTANCE).requiresTool()));

    // Ashen Bricks
    public static final Block ASHEN_BRICKS = registerBlock("ashen_bricks",
            new Block(FabricBlockSettings.of(Material.STONE).strength(STONE_STRENGTH).requiresTool()));
    public static final Block ASHEN_BRICKS_SLAB = registerBlock("ashen_bricks_slab",
            new SlabBlock(FabricBlockSettings.of(Material.STONE).strength(STONE_STRENGTH, SLAB_RESISTANCE).requiresTool()));
    public static final Block ASHEN_BRICKS_STAIRS = registerBlock("ashen_bricks_stairs",
            new StairsBlock(ASHEN_BRICKS.getDefaultState(), AbstractBlock.Settings.copy(ASHEN_BRICKS).strength(STONE_STRENGTH).requiresTool()));
    public static final Block ASHEN_BRICKS_WALL = registerBlock("ashen_bricks_wall",
            new WallBlock(AbstractBlock.Settings.copy(ASHEN_BRICKS).strength(STONE_STRENGTH).requiresTool()));


    public static final Block MORDOR_DIRT = registerBlock("mordor_dirt",
            new Block(FabricBlockSettings.of(Material.SOLID_ORGANIC).strength(DIRT_STRENGTH).sounds(BlockSoundGroup.GRAVEL)));

    // Mallorn
    public static final Block MALLORN_LEAVES = registerBlock("mallorn_leaves",
            new LeavesBlock(FabricBlockSettings.copyOf(Blocks.OAK_LEAVES)));
    public static final Block MALLORN_LOG = registerBlock("mallorn_log",
            new PillarBlock(FabricBlockSettings.copyOf(Blocks.OAK_LOG).strength(WOOD_STRENGTH).requiresTool()
                    .sounds(BlockSoundGroup.WOOD)));
    public static final Block MALLORN_PLANKS = registerBlock("mallorn_planks",
            new Block(FabricBlockSettings.copyOf(Blocks.OAK_PLANKS).strength(WOOD_STRENGTH).requiresTool()
                    .sounds(BlockSoundGroup.WOOD)));
    public static final Block MALLORN_PLANKS_SLAB = registerBlock("mallorn_planks_slab",
            new SlabBlock(FabricBlockSettings.copyOf(Blocks.OAK_PLANKS).strength(WOOD_STRENGTH, SLAB_RESISTANCE).requiresTool().sounds(BlockSoundGroup.WOOD)));
    public static final Block MALLORN_PLANKS_STAIRS = registerBlock("mallorn_planks_stairs",
            new StairsBlock(MALLORN_PLANKS.getDefaultState(), AbstractBlock.Settings.copy(MALLORN_PLANKS).strength(WOOD_STRENGTH).requiresTool()));

    // Mithril
    public static final Block MITHRIL_BLOCK = registerBlock("mithril_block",
            new Block(FabricBlockSettings.of(Material.METAL).strength(6f).requiresTool()));
    public static final Block MITHRIL_ORE = registerBlock("mithril_ore",
            new Block(FabricBlockSettings.of(Material.STONE).strength(4f).requiresTool()));


    // Helper functions
    public static Block registerBlock(String name, Block block) {
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, new Identifier(MiddleEarth.MOD_ID, name), block);
    }

    private static Item registerBlockItem(String name, Block block) {
        ItemGroupEvents.modifyEntriesEvent(ModItemGroups.BLOCKS).register(entries -> entries.add(block));
        return Registry.register(Registries.ITEM, new Identifier(MiddleEarth.MOD_ID, name),
                new BlockItem(block, new FabricItemSettings()));
    }

    public static void registerModBlocks() {
        MiddleEarth.LOGGER.debug("Registering ModBlocks for " + MiddleEarth.MOD_ID);
    }
}
