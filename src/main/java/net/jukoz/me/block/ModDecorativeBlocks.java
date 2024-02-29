package net.jukoz.me.block;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.block.special.StoneChairBlock;
import net.jukoz.me.block.special.WoodChairBlock;
import net.jukoz.me.block.special.StoolBlock;
import net.jukoz.me.block.special.TableBlock;
import net.jukoz.me.block.special.alloyfurnace.AlloyFurnace;
import net.jukoz.me.block.special.artisantable.ArtisanTable;
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
    public static final Block ARTISAN_TABLE = registerBlock("artisan_table",
            new ArtisanTable(FabricBlockSettings.copyOf(Blocks.STONE).strength(1.65f).requiresTool()));

    public static final Block WOOD_PILE = registerBlock("wood_pile",
            new WoodPileBlock(FabricBlockSettings.copyOf(Blocks.OAK_PLANKS).strength(1.0f).nonOpaque()));

    public static final Block LEAD_GLASS = registerBlockWithItem("lead_glass",
            new GlassBlock(FabricBlockSettings.copyOf(Blocks.GLASS)));
    public static final Block BLACK_STAINED_LEAD_GLASS = registerBlockWithItem("black_stained_lead_glass",
            new GlassBlock(FabricBlockSettings.copyOf(Blocks.GLASS)));
    public static final Block BLUE_STAINED_LEAD_GLASS = registerBlockWithItem("blue_stained_lead_glass",
            new GlassBlock(FabricBlockSettings.copyOf(Blocks.GLASS)));
    public static final Block BROWN_STAINED_LEAD_GLASS = registerBlockWithItem("brown_stained_lead_glass",
            new GlassBlock(FabricBlockSettings.copyOf(Blocks.GLASS)));
    public static final Block CYAN_STAINED_LEAD_GLASS = registerBlockWithItem("cyan_stained_lead_glass",
            new GlassBlock(FabricBlockSettings.copyOf(Blocks.GLASS)));
    public static final Block GRAY_STAINED_LEAD_GLASS = registerBlockWithItem("gray_stained_lead_glass",
            new GlassBlock(FabricBlockSettings.copyOf(Blocks.GLASS)));
    public static final Block GREEN_STAINED_LEAD_GLASS = registerBlockWithItem("green_stained_lead_glass",
            new GlassBlock(FabricBlockSettings.copyOf(Blocks.GLASS)));
    public static final Block LIGHT_BLUE_STAINED_LEAD_GLASS = registerBlockWithItem("light_blue_stained_lead_glass",
            new GlassBlock(FabricBlockSettings.copyOf(Blocks.GLASS)));
    public static final Block LIGHT_GRAY_STAINED_LEAD_GLASS = registerBlockWithItem("light_gray_stained_lead_glass",
            new GlassBlock(FabricBlockSettings.copyOf(Blocks.GLASS)));
    public static final Block LIME_STAINED_LEAD_GLASS = registerBlockWithItem("lime_stained_lead_glass",
            new GlassBlock(FabricBlockSettings.copyOf(Blocks.GLASS)));
    public static final Block MAGENTA_STAINED_LEAD_GLASS = registerBlockWithItem("magenta_stained_lead_glass",
            new GlassBlock(FabricBlockSettings.copyOf(Blocks.GLASS)));
    public static final Block ORANGE_STAINED_LEAD_GLASS = registerBlockWithItem("orange_stained_lead_glass",
            new GlassBlock(FabricBlockSettings.copyOf(Blocks.GLASS)));
    public static final Block PINK_STAINED_LEAD_GLASS = registerBlockWithItem("pink_stained_lead_glass",
            new GlassBlock(FabricBlockSettings.copyOf(Blocks.GLASS)));
    public static final Block PURPLE_STAINED_LEAD_GLASS = registerBlockWithItem("purple_stained_lead_glass",
            new GlassBlock(FabricBlockSettings.copyOf(Blocks.GLASS)));
    public static final Block RED_STAINED_LEAD_GLASS = registerBlockWithItem("red_stained_lead_glass",
            new GlassBlock(FabricBlockSettings.copyOf(Blocks.GLASS)));
    public static final Block WHITE_STAINED_LEAD_GLASS = registerBlockWithItem("white_stained_lead_glass",
            new GlassBlock(FabricBlockSettings.copyOf(Blocks.GLASS)));
    public static final Block YELLOW_STAINED_LEAD_GLASS = registerBlockWithItem("yellow_stained_lead_glass",
            new GlassBlock(FabricBlockSettings.copyOf(Blocks.GLASS)));
    public static final Block LEAD_GLASS_PANE = registerBlockWithItem("lead_glass_pane",
            new PaneBlock(FabricBlockSettings.copyOf(Blocks.GLASS_PANE)));
    public static final Block BLACK_STAINED_LEAD_GLASS_PANE = registerBlockWithItem("black_stained_lead_glass_pane",
            new PaneBlock(FabricBlockSettings.copyOf(Blocks.GLASS_PANE)));
    public static final Block BLUE_STAINED_LEAD_GLASS_PANE = registerBlockWithItem("blue_stained_lead_glass_pane",
            new PaneBlock(FabricBlockSettings.copyOf(Blocks.GLASS_PANE)));
    public static final Block BROWN_STAINED_LEAD_GLASS_PANE = registerBlockWithItem("brown_stained_lead_glass_pane",
            new PaneBlock(FabricBlockSettings.copyOf(Blocks.GLASS_PANE)));
    public static final Block CYAN_STAINED_LEAD_GLASS_PANE = registerBlockWithItem("cyan_stained_lead_glass_pane",
            new PaneBlock(FabricBlockSettings.copyOf(Blocks.GLASS_PANE)));
    public static final Block GRAY_STAINED_LEAD_GLASS_PANE = registerBlockWithItem("gray_stained_lead_glass_pane",
            new PaneBlock(FabricBlockSettings.copyOf(Blocks.GLASS_PANE)));
    public static final Block GREEN_STAINED_LEAD_GLASS_PANE = registerBlockWithItem("green_stained_lead_glass_pane",
            new PaneBlock(FabricBlockSettings.copyOf(Blocks.GLASS_PANE)));
    public static final Block LIGHT_BLUE_STAINED_LEAD_GLASS_PANE = registerBlockWithItem("light_blue_stained_lead_glass_pane",
            new PaneBlock(FabricBlockSettings.copyOf(Blocks.GLASS_PANE)));
    public static final Block LIGHT_GRAY_STAINED_LEAD_GLASS_PANE = registerBlockWithItem("light_gray_stained_lead_glass_pane",
            new PaneBlock(FabricBlockSettings.copyOf(Blocks.GLASS_PANE)));
    public static final Block LIME_STAINED_LEAD_GLASS_PANE = registerBlockWithItem("lime_stained_lead_glass_pane",
            new PaneBlock(FabricBlockSettings.copyOf(Blocks.GLASS_PANE)));
    public static final Block MAGENTA_STAINED_LEAD_GLASS_PANE = registerBlockWithItem("magenta_stained_lead_glass_pane",
            new PaneBlock(FabricBlockSettings.copyOf(Blocks.GLASS_PANE)));
    public static final Block ORANGE_STAINED_LEAD_GLASS_PANE = registerBlockWithItem("orange_stained_lead_glass_pane",
            new PaneBlock(FabricBlockSettings.copyOf(Blocks.GLASS_PANE)));
    public static final Block PINK_STAINED_LEAD_GLASS_PANE = registerBlockWithItem("pink_stained_lead_glass_pane",
            new PaneBlock(FabricBlockSettings.copyOf(Blocks.GLASS_PANE)));
    public static final Block PURPLE_STAINED_LEAD_GLASS_PANE = registerBlockWithItem("purple_stained_lead_glass_pane",
            new PaneBlock(FabricBlockSettings.copyOf(Blocks.GLASS_PANE)));
    public static final Block RED_STAINED_LEAD_GLASS_PANE = registerBlockWithItem("red_stained_lead_glass_pane",
            new PaneBlock(FabricBlockSettings.copyOf(Blocks.GLASS_PANE)));
    public static final Block WHITE_STAINED_LEAD_GLASS_PANE = registerBlockWithItem("white_stained_lead_glass_pane",
            new PaneBlock(FabricBlockSettings.copyOf(Blocks.GLASS_PANE)));
    public static final Block YELLOW_STAINED_LEAD_GLASS_PANE = registerBlockWithItem("yellow_stained_lead_glass_pane",
            new PaneBlock(FabricBlockSettings.copyOf(Blocks.GLASS_PANE)));

    //region VANILLA FURNITURE
    public static final Block STONE_STOOL = registerBlockWithItem("stone_stool",
            new StoolBlock(FabricBlockSettings.copyOf(Blocks.STONE).requiresTool()));
    public static final Block STONE_TABLE = registerBlockWithItem("stone_table",
            new TableBlock(FabricBlockSettings.copyOf(Blocks.STONE).requiresTool()));
    public static final Block STONE_CHAIR = registerBlockWithItem("stone_chair",
            new StoneChairBlock(FabricBlockSettings.copyOf(Blocks.STONE).requiresTool()));

    public static final Block CALCITE_STOOL = registerBlockWithItem("calcite_stool",
            new StoolBlock(FabricBlockSettings.copyOf(Blocks.CALCITE).requiresTool()));
    public static final Block CALCITE_TABLE = registerBlockWithItem("calcite_table",
            new TableBlock(FabricBlockSettings.copyOf(Blocks.CALCITE).requiresTool()));
    public static final Block CALCITE_CHAIR = registerBlockWithItem("calcite_chair",
            new StoneChairBlock(FabricBlockSettings.copyOf(Blocks.CALCITE).requiresTool()));

    public static final Block ANDESITE_STOOL = registerBlockWithItem("andesite_stool",
            new StoolBlock(FabricBlockSettings.copyOf(Blocks.ANDESITE).requiresTool()));
    public static final Block ANDESITE_TABLE = registerBlockWithItem("andesite_table",
            new TableBlock(FabricBlockSettings.copyOf(Blocks.ANDESITE).requiresTool()));
    public static final Block ANDESITE_CHAIR = registerBlockWithItem("andesite_chair",
            new StoneChairBlock(FabricBlockSettings.copyOf(Blocks.ANDESITE).requiresTool()));

    public static final Block GRANITE_STOOL = registerBlockWithItem("granite_stool",
            new StoolBlock(FabricBlockSettings.copyOf(Blocks.GRANITE).requiresTool()));
    public static final Block GRANITE_TABLE = registerBlockWithItem("granite_table",
            new TableBlock(FabricBlockSettings.copyOf(Blocks.GRANITE).requiresTool()));
    public static final Block GRANITE_CHAIR = registerBlockWithItem("granite_chair",
            new StoneChairBlock(FabricBlockSettings.copyOf(Blocks.GRANITE).requiresTool()));

    public static final Block DIORITE_STOOL = registerBlockWithItem("diorite_stool",
            new StoolBlock(FabricBlockSettings.copyOf(Blocks.DIORITE).requiresTool()));
    public static final Block DIORITE_TABLE = registerBlockWithItem("diorite_table",
            new TableBlock(FabricBlockSettings.copyOf(Blocks.DIORITE).requiresTool()));
    public static final Block DIORITE_CHAIR = registerBlockWithItem("diorite_chair",
            new StoneChairBlock(FabricBlockSettings.copyOf(Blocks.DIORITE).requiresTool()));

    public static final Block DEEPSLATE_STOOL = registerBlockWithItem("deepslate_stool",
            new StoolBlock(FabricBlockSettings.copyOf(Blocks.DEEPSLATE).requiresTool()));
    public static final Block DEEPSLATE_TABLE = registerBlockWithItem("deepslate_table",
            new TableBlock(FabricBlockSettings.copyOf(Blocks.DEEPSLATE).requiresTool()));
    public static final Block DEEPSLATE_CHAIR = registerBlockWithItem("deepslate_chair",
            new StoneChairBlock(FabricBlockSettings.copyOf(Blocks.DEEPSLATE).requiresTool()));

    public static final Block BLACKSTONE_STOOL = registerBlockWithItem("blackstone_stool",
            new StoolBlock(FabricBlockSettings.copyOf(Blocks.BLACKSTONE).requiresTool()));
    public static final Block BLACKSTONE_TABLE = registerBlockWithItem("blackstone_table",
            new TableBlock(FabricBlockSettings.copyOf(Blocks.BLACKSTONE).requiresTool()));
    public static final Block BLACKSTONE_CHAIR = registerBlockWithItem("blackstone_chair",
            new StoneChairBlock(FabricBlockSettings.copyOf(Blocks.BLACKSTONE).requiresTool()));

    public static final Block BASALT_STOOL = registerBlockWithItem("basalt_stool",
            new StoolBlock(FabricBlockSettings.copyOf(Blocks.BASALT).requiresTool()));
    public static final Block BASALT_TABLE = registerBlockWithItem("basalt_table",
            new TableBlock(FabricBlockSettings.copyOf(Blocks.BASALT).requiresTool()));
    public static final Block BASALT_CHAIR = registerBlockWithItem("basalt_chair",
            new StoneChairBlock(FabricBlockSettings.copyOf(Blocks.BASALT).requiresTool()));

    public static final Block TUFF_STOOL = registerBlockWithItem("tuff_stool",
            new StoolBlock(FabricBlockSettings.copyOf(Blocks.TUFF).requiresTool()));
    public static final Block TUFF_TABLE = registerBlockWithItem("tuff_table",
            new TableBlock(FabricBlockSettings.copyOf(Blocks.TUFF).requiresTool()));
    public static final Block TUFF_CHAIR = registerBlockWithItem("tuff_chair",
            new StoneChairBlock(FabricBlockSettings.copyOf(Blocks.TUFF).requiresTool()));
    
    
    public static final Block OAK_STOOL = registerBlockWithItem("oak_stool",
            new StoolBlock(FabricBlockSettings.copyOf(Blocks.OAK_PLANKS)));
    public static final Block OAK_TABLE = registerBlockWithItem("oak_table",
            new TableBlock(FabricBlockSettings.copyOf(Blocks.OAK_PLANKS)));
    public static final Block OAK_CHAIR = registerBlockWithItem("oak_chair",
            new WoodChairBlock(FabricBlockSettings.copyOf(Blocks.OAK_PLANKS)));

    public static final Block SPRUCE_STOOL = registerBlockWithItem("spruce_stool",
            new StoolBlock(FabricBlockSettings.copyOf(Blocks.SPRUCE_PLANKS)));
    public static final Block SPRUCE_TABLE = registerBlockWithItem("spruce_table",
            new TableBlock(FabricBlockSettings.copyOf(Blocks.SPRUCE_PLANKS)));
    public static final Block SPRUCE_CHAIR = registerBlockWithItem("spruce_chair",
            new WoodChairBlock(FabricBlockSettings.copyOf(Blocks.SPRUCE_PLANKS)));

    public static final Block BIRCH_STOOL = registerBlockWithItem("birch_stool",
            new StoolBlock(FabricBlockSettings.copyOf(Blocks.BIRCH_PLANKS)));
    public static final Block BIRCH_TABLE = registerBlockWithItem("birch_table",
            new TableBlock(FabricBlockSettings.copyOf(Blocks.BIRCH_PLANKS)));
    public static final Block BIRCH_CHAIR = registerBlockWithItem("birch_chair",
            new WoodChairBlock(FabricBlockSettings.copyOf(Blocks.BIRCH_PLANKS)));

    public static final Block JUNGLE_STOOL = registerBlockWithItem("jungle_stool",
            new StoolBlock(FabricBlockSettings.copyOf(Blocks.JUNGLE_PLANKS)));
    public static final Block JUNGLE_TABLE = registerBlockWithItem("jungle_table",
            new TableBlock(FabricBlockSettings.copyOf(Blocks.JUNGLE_PLANKS)));
    public static final Block JUNGLE_CHAIR = registerBlockWithItem("jungle_chair",
            new WoodChairBlock(FabricBlockSettings.copyOf(Blocks.JUNGLE_PLANKS)));

    public static final Block ACACIA_STOOL = registerBlockWithItem("acacia_stool",
            new StoolBlock(FabricBlockSettings.copyOf(Blocks.ACACIA_PLANKS)));
    public static final Block ACACIA_TABLE = registerBlockWithItem("acacia_table",
            new TableBlock(FabricBlockSettings.copyOf(Blocks.ACACIA_PLANKS)));
    public static final Block ACACIA_CHAIR = registerBlockWithItem("acacia_chair",
            new WoodChairBlock(FabricBlockSettings.copyOf(Blocks.ACACIA_PLANKS).nonOpaque()));

    public static final Block DARK_OAK_STOOL = registerBlockWithItem("dark_oak_stool",
            new StoolBlock(FabricBlockSettings.copyOf(Blocks.DARK_OAK_PLANKS)));
    public static final Block DARK_OAK_TABLE = registerBlockWithItem("dark_oak_table",
            new TableBlock(FabricBlockSettings.copyOf(Blocks.DARK_OAK_PLANKS)));
    public static final Block DARK_OAK_CHAIR = registerBlockWithItem("dark_oak_chair",
            new WoodChairBlock(FabricBlockSettings.copyOf(Blocks.DARK_OAK_PLANKS).nonOpaque()));


    public static final Block MANGROVE_STOOL = registerBlockWithItem("mangrove_stool",
            new StoolBlock(FabricBlockSettings.copyOf(Blocks.MANGROVE_PLANKS)));
    public static final Block MANGROVE_TABLE = registerBlockWithItem("mangrove_table",
            new TableBlock(FabricBlockSettings.copyOf(Blocks.MANGROVE_PLANKS)));
    public static final Block MANGROVE_CHAIR = registerBlockWithItem("mangrove_chair",
            new WoodChairBlock(FabricBlockSettings.copyOf(Blocks.MANGROVE_PLANKS).nonOpaque()));

    public static final Block CHERRY_STOOL = registerBlockWithItem("cherry_stool",
            new StoolBlock(FabricBlockSettings.copyOf(Blocks.CHERRY_PLANKS)));
    public static final Block CHERRY_TABLE = registerBlockWithItem("cherry_table",
            new TableBlock(FabricBlockSettings.copyOf(Blocks.CHERRY_PLANKS)));
    public static final Block CHERRY_CHAIR = registerBlockWithItem("cherry_chair",
            new WoodChairBlock(FabricBlockSettings.copyOf(Blocks.CHERRY_PLANKS).nonOpaque()));

    public static final Block BAMBOO_STOOL = registerBlockWithItem("bamboo_stool",
            new StoolBlock(FabricBlockSettings.copyOf(Blocks.BAMBOO_PLANKS)));
    public static final Block BAMBOO_TABLE = registerBlockWithItem("bamboo_table",
            new TableBlock(FabricBlockSettings.copyOf(Blocks.BAMBOO_PLANKS)));
    public static final Block BAMBOO_CHAIR = registerBlockWithItem("bamboo_chair",
            new WoodChairBlock(FabricBlockSettings.copyOf(Blocks.BAMBOO_PLANKS).nonOpaque()));

    public static final Block CRIMSON_STOOL = registerBlockWithItem("crimson_stool",
            new StoolBlock(FabricBlockSettings.copyOf(Blocks.CRIMSON_PLANKS)));
    public static final Block CRIMSON_TABLE = registerBlockWithItem("crimson_table",
            new TableBlock(FabricBlockSettings.copyOf(Blocks.CRIMSON_PLANKS)));
    public static final Block CRIMSON_CHAIR = registerBlockWithItem("crimson_chair",
            new WoodChairBlock(FabricBlockSettings.copyOf(Blocks.CRIMSON_PLANKS).nonOpaque()));

    public static final Block WARPED_STOOL = registerBlockWithItem("warped_stool",
            new StoolBlock(FabricBlockSettings.copyOf(Blocks.WARPED_PLANKS)));
    public static final Block WARPED_TABLE = registerBlockWithItem("warped_table",
            new TableBlock(FabricBlockSettings.copyOf(Blocks.WARPED_PLANKS)));
    public static final Block WARPED_CHAIR = registerBlockWithItem("warped_chair",
            new WoodChairBlock(FabricBlockSettings.copyOf(Blocks.WARPED_PLANKS).nonOpaque()));
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
