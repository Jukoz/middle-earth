package net.jukoz.me.block;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.block.special.StoolBlock;
import net.jukoz.me.block.special.TableBlock;
import net.jukoz.me.block.special.alloyfurnace.AlloyFurnace;
import net.jukoz.me.block.special.toggeable_lights.DwarvenLanternBlock;
import net.jukoz.me.block.special.toggeable_lights.SilverLanternBlock;
import net.jukoz.me.block.special.toggeable_lights.WallDwarvenLanternBlock;
import net.jukoz.me.block.special.toggeable_lights.WallSilverLanternBlock;
import net.jukoz.me.block.special.wood_pile.WoodPileBlock;
import net.jukoz.me.item.utils.ModItemGroups;
import net.minecraft.block.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;

import java.util.function.ToIntFunction;

public class ModDecorativeBlocks {

    public static final Block SILVER_LANTERN = registerBlock("silver_lantern",
            new SilverLanternBlock(FabricBlockSettings.create().luminance(createLightLevelFromLitBlockState(15)).strength(1.0f)));
    public static final Block WALL_SILVER_LANTERN = registerBlock("silver_lantern_wall",
            new WallSilverLanternBlock(FabricBlockSettings.create().luminance(createLightLevelFromLitBlockState(15)).strength(1.0f)));

    public static final Block DWARVEN_LANTERN = registerBlock("dwarven_lantern",
            new DwarvenLanternBlock(FabricBlockSettings.create().luminance(createLightLevelFromLitBlockState(15)).strength(1.0f)));
    public static final Block WALL_DWARVEN_LANTERN = registerBlock("dwarven_lantern_wall",
            new WallDwarvenLanternBlock(FabricBlockSettings.create().luminance(createLightLevelFromLitBlockState(15)).strength(1.0f)));

    public static final Block ALLOY_FURNACE = registerBlock("alloy_furnace",
            new AlloyFurnace(FabricBlockSettings.copyOf(Blocks.STONE).strength(1.65f).requiresTool()));

    public static final Block WOOD_PILE = registerBlock("wood_pile",
            new WoodPileBlock(FabricBlockSettings.copyOf(Blocks.OAK_PLANKS).strength(1.0f).nonOpaque()));

    //region VANILLA FURNITURE
    public static final Block OAK_WOOD_STOOL = registerBlockWithItem("oak_wood_stool",
            new StoolBlock(FabricBlockSettings.copyOf(Blocks.OAK_PLANKS)));
    public static final Block SPRUCE_WOOD_STOOL = registerBlockWithItem("spruce_wood_stool",
            new StoolBlock(FabricBlockSettings.copyOf(Blocks.SPRUCE_PLANKS)));
    public static final Block BIRCH_WOOD_STOOL = registerBlockWithItem("birch_wood_stool",
            new StoolBlock(FabricBlockSettings.copyOf(Blocks.BIRCH_PLANKS)));
    public static final Block JUNGLE_WOOD_STOOL = registerBlockWithItem("jungle_wood_stool",
            new StoolBlock(FabricBlockSettings.copyOf(Blocks.JUNGLE_PLANKS)));
    public static final Block ACACIA_WOOD_STOOL = registerBlockWithItem("acacia_wood_stool",
            new StoolBlock(FabricBlockSettings.copyOf(Blocks.ACACIA_PLANKS)));
    public static final Block DARK_OAK_WOOD_STOOL = registerBlockWithItem("dark_oak_wood_stool",
            new StoolBlock(FabricBlockSettings.copyOf(Blocks.DARK_OAK_PLANKS)));
    public static final Block MANGROVE_WOOD_STOOL = registerBlockWithItem("mangrove_wood_stool",
            new StoolBlock(FabricBlockSettings.copyOf(Blocks.MANGROVE_PLANKS)));
    public static final Block CHERRY_WOOD_STOOL = registerBlockWithItem("cherry_wood_stool",
            new StoolBlock(FabricBlockSettings.copyOf(Blocks.CHERRY_PLANKS)));
    public static final Block BAMBOO_WOOD_STOOL = registerBlockWithItem("bamboo_wood_stool",
            new StoolBlock(FabricBlockSettings.copyOf(Blocks.BAMBOO_PLANKS)));
    public static final Block CRIMSON_WOOD_STOOL = registerBlockWithItem("crimson_wood_stool",
            new StoolBlock(FabricBlockSettings.copyOf(Blocks.CRIMSON_PLANKS)));
    public static final Block WARPED_WOOD_STOOL = registerBlockWithItem("warped_wood_stool",
            new StoolBlock(FabricBlockSettings.copyOf(Blocks.WARPED_PLANKS)));

    public static final Block OAK_WOOD_TABLE = registerBlockWithItem("oak_wood_table",
            new TableBlock(FabricBlockSettings.copyOf(Blocks.OAK_PLANKS)));
    public static final Block SPRUCE_WOOD_TABLE = registerBlockWithItem("spruce_wood_table",
            new TableBlock(FabricBlockSettings.copyOf(Blocks.SPRUCE_PLANKS)));
    public static final Block BIRCH_WOOD_TABLE = registerBlockWithItem("birch_wood_table",
            new TableBlock(FabricBlockSettings.copyOf(Blocks.BIRCH_PLANKS)));
    public static final Block JUNGLE_WOOD_TABLE = registerBlockWithItem("jungle_wood_table",
            new TableBlock(FabricBlockSettings.copyOf(Blocks.JUNGLE_PLANKS)));
    public static final Block ACACIA_WOOD_TABLE = registerBlockWithItem("acacia_wood_table",
            new TableBlock(FabricBlockSettings.copyOf(Blocks.ACACIA_PLANKS)));
    public static final Block DARK_OAK_WOOD_TABLE = registerBlockWithItem("dark_oak_wood_table",
            new TableBlock(FabricBlockSettings.copyOf(Blocks.DARK_OAK_PLANKS)));
    public static final Block MANGROVE_WOOD_TABLE = registerBlockWithItem("mangrove_wood_table",
            new TableBlock(FabricBlockSettings.copyOf(Blocks.MANGROVE_PLANKS)));
    public static final Block CHERRY_WOOD_TABLE = registerBlockWithItem("cherry_wood_table",
            new TableBlock(FabricBlockSettings.copyOf(Blocks.CHERRY_PLANKS)));
    public static final Block BAMBOO_WOOD_TABLE = registerBlockWithItem("bamboo_wood_table",
            new TableBlock(FabricBlockSettings.copyOf(Blocks.BAMBOO_PLANKS)));
    public static final Block CRIMSON_WOOD_TABLE = registerBlockWithItem("crimson_wood_table",
            new TableBlock(FabricBlockSettings.copyOf(Blocks.CRIMSON_PLANKS)));
    public static final Block WARPED_WOOD_TABLE = registerBlockWithItem("warped_wood_table",
            new TableBlock(FabricBlockSettings.copyOf(Blocks.WARPED_PLANKS)));
    //endregion

    public static Block registerBlock(String name, Block block) {
        Identifier identifier = new Identifier(MiddleEarth.MOD_ID, name);
        return Registry.register(Registries.BLOCK, identifier , block);
    }

    public static Block registerBlockWithItem(String name, Block block) {
        Identifier identifier = new Identifier(MiddleEarth.MOD_ID, name);
        ModBlocks.registerBlockItem(name, block);
        ModItemGroups.DECORATIVES_BLOCKS_CONTENT.add(block.asItem().getDefaultStack());
        return Registry.register(Registries.BLOCK, identifier , block);
    }

    private static ToIntFunction<BlockState> createLightLevelFromLitBlockState(int litLevel) {
        return (state) -> {
            return (Boolean)state.get(Properties.LIT) ? litLevel : 0;
        };
    }

    public static void registerModBlocks() {
        MiddleEarth.LOGGER.debug("Registering ModBlocks for " + MiddleEarth.MOD_ID);
    }
}
