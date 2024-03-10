package net.jukoz.me.datageneration;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.block.*;
import net.jukoz.me.datageneration.content.models.*;
import net.jukoz.me.item.ModFoodItems;
import net.jukoz.me.item.ModResourceItems;
import net.jukoz.me.item.ModToolItems;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.data.server.recipe.CookingRecipeJsonBuilder;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

import java.util.function.Consumer;

public class RecipeProvider extends net.minecraft.data.server.recipe.RecipeProvider {

    protected RecipeProvider(FabricDataOutput dataOutput) {
        super(dataOutput);
    }

    @Override
    public void generate(Consumer<RecipeJsonProvider> exporter) {

        //region STONE RECIPES
        for (StoneBlockSets.SimpleBlockSetMain record : StoneBlockSets.setsMain) {
            if(record.toString().contains("mossy_")){
                createMossyRecipe(exporter, record.source(), record.base());
            } else if(record.toString().contains("cracked_")){
                createCrackedRecipe(exporter, record.source().asItem(), record.base().asItem());
            } else if(record.toString().contains("cobbled_")) {
                offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, record.base(), record.source(), 1);
            } else if(record.source() != null){
                createBrickRecipe(exporter, record.source().asItem(), record.base(), 4);
                offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, record.base(), record.source(), 4);
                offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, record.slab(), record.source(), 2);
                offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, record.verticalSlab(), record.source(),2);
                offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, record.stairs(), record.source());
                offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, record.wall(), record.source());
            }

            createSlabsRecipe(exporter, record.base(), record.slab());
            offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, record.slab(), record.base(), 2);
            createStairsRecipe(exporter, record.base(), record.stairs());
            offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, record.stairs(), record.base(), 1);
            createWallsRecipe(exporter, record.base(), record.wall());
            offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, record.wall(), record.base(), 1);
            createStoneStoolRecipe(exporter, record.base().asItem(), record.stool());
            createStoneTableRecipe(exporter, record.base().asItem(), record.table());
            createStoneChairRecipe(exporter, record.base().asItem(), record.chair());
        }

        for (StoneBlockSets.SimpleBlockSet record : StoneBlockSets.sets) {
            if(record.toString().contains("mossy_")){
                createMossyRecipe(exporter, record.source(), record.base());
            } else if(record.toString().contains("cracked_")){
                createCrackedRecipe(exporter, record.source().asItem(), record.base().asItem());
            } else if(record.toString().contains("cobbled_")) {
                offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, record.base(), record.source(), 1);
            } else if(record.source() != null){
                createBrickRecipe(exporter, record.source().asItem(), record.base(), 4);
                offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, record.base(), record.source(), 4);
                offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, record.slab(), record.source(), 2);
                offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, record.verticalSlab(), record.source(),2);
                offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, record.stairs(), record.source());
                offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, record.wall(), record.source());
            }

            createSlabsRecipe(exporter, record.base(), record.slab());
            offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, record.slab(), record.base(), 2);
            createStairsRecipe(exporter, record.base(), record.stairs());
            offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, record.stairs(), record.base(), 1);
            createWallsRecipe(exporter, record.base(), record.wall());
            offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, record.wall(), record.base(), 1);
        }
        //endregion

        //region WOOD RECIPES
        for (WoodBlockSets.SimpleBlockSet record : WoodBlockSets.sets) {
            createBrickRecipe(exporter, record.log().asItem(), record.wood(), 3);
            createWallsRecipe(exporter, record.wood(), record.woodWall());
            createSlabsRecipe(exporter, record.planks(), record.planksSlab());
            createStairsRecipe(exporter, record.planks(), record.planksStairs());
            createDoorRecipe(exporter, record.planks(), record.door());
            createTrapdoorRecipe(exporter, record.planks(), record.trapdoor());
            createWoodStoolRecipe(exporter, record.planksSlab().asItem(), record.stool());
            createWoodTableRecipe(exporter, record.planksSlab().asItem(), record.table());
            createWoodChairRecipe(exporter, record.planksSlab().asItem(), record.chair());

            ShapelessRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, record.planks(), 4)
                    .input(record.log())
                    .criterion(FabricRecipeProvider.hasItem(record.log()),
                            FabricRecipeProvider.conditionsFromItem(record.planks()))
                    .offerTo(exporter);

            ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, record.planksFence(), 3)
                    .pattern("lsl")
                    .pattern("lsl")
                    .input('l', record.planks())
                    .input('s', Items.STICK)
                    .criterion(FabricRecipeProvider.hasItem(record.planks()),
                            FabricRecipeProvider.conditionsFromItem(record.planks()))
                    .criterion(FabricRecipeProvider.hasItem(Items.STICK),
                            FabricRecipeProvider.conditionsFromItem(Items.STICK))
                    .offerTo(exporter);

            ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, record.planksGate(), 1)
                    .pattern("sls")
                    .pattern("sls")
                    .input('l', record.planks())
                    .input('s', Items.STICK)
                    .criterion(FabricRecipeProvider.hasItem(record.planks()),
                            FabricRecipeProvider.conditionsFromItem(record.planks()))
                    .criterion(FabricRecipeProvider.hasItem(Items.STICK),
                            FabricRecipeProvider.conditionsFromItem(Items.STICK))
                    .offerTo(exporter);

            ShapelessRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, record.button(), 1)
                    .input(record.planks(), 1)
                    .criterion(FabricRecipeProvider.hasItem(record.planks()),
                            FabricRecipeProvider.conditionsFromItem(record.planks()))
                    .offerTo(exporter);
        }
        //endregion

        //region MUSHROOM RECIPES
        for (MushroomBlockSets.MushroomBlockSet record : MushroomBlockSets.sets) {

            if(record.stem() != null){
                createWallsRecipe(exporter, record.stem(), record.stemWall());


                ShapelessRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, record.planks(), 4)
                        .input(record.stem())
                        .criterion(FabricRecipeProvider.hasItem(record.stem()),
                                FabricRecipeProvider.conditionsFromItem(record.planks()))
                        .offerTo(exporter);
            }

            createSlabsRecipe(exporter, record.planks(), record.planksSlab());
            createStairsRecipe(exporter, record.planks(), record.planksStairs());
            createDoorRecipe(exporter, record.planks(), record.door());
            createTrapdoorRecipe(exporter, record.planks(), record.trapdoor());
            createWoodStoolRecipe(exporter, record.planksSlab().asItem(), record.stool());
            createWoodTableRecipe(exporter, record.planksSlab().asItem(), record.table());
            createWoodChairRecipe(exporter, record.planksSlab().asItem(), record.chair());

            ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, record.planksFence(), 3)
                    .pattern("lsl")
                    .pattern("lsl")
                    .input('l', record.planks())
                    .input('s', Items.STICK)
                    .criterion(FabricRecipeProvider.hasItem(record.planks()),
                            FabricRecipeProvider.conditionsFromItem(record.planks()))
                    .criterion(FabricRecipeProvider.hasItem(Items.STICK),
                            FabricRecipeProvider.conditionsFromItem(Items.STICK))
                    .offerTo(exporter);

            ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, record.planksGate(), 1)
                    .pattern("sls")
                    .pattern("sls")
                    .input('l', record.planks())
                    .input('s', Items.STICK)
                    .criterion(FabricRecipeProvider.hasItem(record.planks()),
                            FabricRecipeProvider.conditionsFromItem(record.planks()))
                    .criterion(FabricRecipeProvider.hasItem(Items.STICK),
                            FabricRecipeProvider.conditionsFromItem(Items.STICK))
                    .offerTo(exporter);

            ShapelessRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, record.button(), 1)
                    .input(record.planks(), 1)
                    .criterion(FabricRecipeProvider.hasItem(record.planks()),
                            FabricRecipeProvider.conditionsFromItem(record.planks()))
                    .offerTo(exporter);
        }
        //endregion

        //region ROOF RECIPES
        for (RoofBlockSets.RoofBlockSet record : RoofBlockSets.sets) {

            if(record.origin() != null){
                ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, record.block(), 7)
                        .pattern(" l ")
                        .pattern("lll")
                        .pattern("lll")
                        .input('l', record.origin())
                        .criterion(FabricRecipeProvider.hasItem(record.origin()),
                                FabricRecipeProvider.conditionsFromItem(record.origin()))
                        .offerTo(exporter);
                if(!record.block().toString().contains("shingles")){
                    offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, record.block(), record.origin(), 1);
                    offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, record.slab(), record.block(), 2);
                    offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, record.slab(), record.origin(), 2);
                    offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, record.stairs(), record.block(), 1);
                    offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, record.stairs(), record.origin(), 1);
                }
            }
            createSlabsRecipe(exporter, record.block(), record.slab());
            createStairsRecipe(exporter, record.block(), record.stairs());
        }
        //endregion

        //region BLOCK LIST SPECIFIC RECIPES
        for(SimpleVerticalSlabModel.VerticalSlab verticalSlab : SimpleVerticalSlabModel.verticalSlabs) {
            createVerticalSlabsRecipe(exporter, verticalSlab.slab(), verticalSlab.verticalSlab());
            createSlabsFromVerticalRecipe(exporter, verticalSlab.verticalSlab(), verticalSlab.slab());
            if(!verticalSlab.block().toString().contains("planks") || !verticalSlab.block().toString().contains("shingles")){
                offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, verticalSlab.verticalSlab(), verticalSlab.block(),2);
            }
        }

        for(SimpleVerticalSlabModel.VerticalSlab verticalSlab : SimpleVerticalSlabModel.vanillaVerticalSlabs) {
            createVerticalSlabsRecipe(exporter, verticalSlab.slab(), verticalSlab.verticalSlab());
            createSlabsFromVerticalRecipe(exporter, verticalSlab.verticalSlab(), verticalSlab.slab());
            if(!verticalSlab.block().toString().contains("planks") || !verticalSlab.block().toString().contains("shingles")){
                offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, verticalSlab.verticalSlab(), verticalSlab.block(),2);
            }
        }

        for(SimplePillarModel.StonePillar pillar : SimplePillarModel.stonePillars) {
            if(pillar.toString().contains("mossy_")){
                createMossyRecipe(exporter, pillar.origin(), pillar.base());
            } else if(pillar.toString().contains("cracked_")){
                createCrackedRecipe(exporter, pillar.origin().asItem(), pillar.base().asItem());
            } else {
                createPillarRecipe(exporter,pillar.origin(), pillar.base(), 3);
            }
        }

        for(SimpleBlockModel.ChiseledBlock block : SimpleBlockModel.chiseledBlocks) {
            createChiseledRecipe(exporter, block.origin(), block.base(), 1);
        }
        for(SimpleBlockModel.ChiseledBlock block : SimpleBlockModel.chiseledBlocksTopBottom) {
            createChiseledRecipe(exporter, block.origin(), block.base(), 1);
        }
        for(SimpleBlockModel.ChiseledPolishedBlock block : SimpleBlockModel.chiseledPolishedBlocks) {
            createCutPolishedRecipe(exporter, block.origin(), block.base(), 1);
        }

        for(SimpleSlabModel.Slab slab : SimpleSlabModel.vanillaSlabs){
            createSlabsRecipe(exporter, slab.origin(), slab.slab());
        }

        for(SimpleStairModel.Stair stair : SimpleStairModel.vanillaStairs){
            createStairsRecipe(exporter, stair.origin(), stair.stairs());
        }

        for(SimplePaneModel.Pane pane : SimplePaneModel.panes){
            createPaneRecipe(exporter, pane.glass().asItem(), pane.pane(), 16);
        }

        for(SimpleWoodStoolModel.VanillaStool stool : SimpleWoodStoolModel.vanillaStools){
            createWoodStoolRecipe(exporter, stool.origin().asItem(), stool.base());
        }

        for(SimpleWoodTableModel.VanillaTable table : SimpleWoodTableModel.vanillaTables){
            createWoodTableRecipe(exporter, table.origin().asItem(), table.base());
        }

        for(SimpleWoodChairModel.VanillaChair chair : SimpleWoodChairModel.vanillaChairs){
            createWoodChairRecipe(exporter, chair.origin().asItem(), chair.base());
        }

        for(SimpleStoneStoolModel.VanillaStool stool : SimpleStoneStoolModel.vanillaStools){
            createStoneStoolRecipe(exporter, stool.origin().asItem(), stool.base());
        }

        for(SimpleStoneTableModel.VanillaTable table : SimpleStoneTableModel.vanillaTables){
            createStoneTableRecipe(exporter, table.origin().asItem(), table.base());
        }

        for(SimpleStoneChairModel.VanillaChair chair : SimpleStoneChairModel.vanillaChairs){
            createStoneChairRecipe(exporter, chair.origin().asItem(), chair.base());
        }

        //endregion

        //region MANUAL BLOCK RECIPES
        createDyeableBlockRecipe(exporter, ModDecorativeBlocks.LEAD_GLASS, Items.BLACK_DYE, ModDecorativeBlocks.BLACK_STAINED_LEAD_GLASS, 8);
        createDyeableBlockRecipe(exporter, ModDecorativeBlocks.LEAD_GLASS, Items.BLUE_DYE, ModDecorativeBlocks.BLUE_STAINED_LEAD_GLASS, 8);
        createDyeableBlockRecipe(exporter, ModDecorativeBlocks.LEAD_GLASS, Items.BROWN_DYE, ModDecorativeBlocks.BROWN_STAINED_LEAD_GLASS, 8);
        createDyeableBlockRecipe(exporter, ModDecorativeBlocks.LEAD_GLASS, Items.CYAN_DYE, ModDecorativeBlocks.CYAN_STAINED_LEAD_GLASS, 8);
        createDyeableBlockRecipe(exporter, ModDecorativeBlocks.LEAD_GLASS, Items.GRAY_DYE, ModDecorativeBlocks.GRAY_STAINED_LEAD_GLASS, 8);
        createDyeableBlockRecipe(exporter, ModDecorativeBlocks.LEAD_GLASS, Items.GREEN_DYE, ModDecorativeBlocks.GREEN_STAINED_LEAD_GLASS, 8);
        createDyeableBlockRecipe(exporter, ModDecorativeBlocks.LEAD_GLASS, Items.LIGHT_BLUE_DYE, ModDecorativeBlocks.LIGHT_BLUE_STAINED_LEAD_GLASS, 8);
        createDyeableBlockRecipe(exporter, ModDecorativeBlocks.LEAD_GLASS, Items.LIGHT_GRAY_DYE, ModDecorativeBlocks.LIGHT_GRAY_STAINED_LEAD_GLASS, 8);
        createDyeableBlockRecipe(exporter, ModDecorativeBlocks.LEAD_GLASS, Items.LIME_DYE, ModDecorativeBlocks.LIME_STAINED_LEAD_GLASS, 8);
        createDyeableBlockRecipe(exporter, ModDecorativeBlocks.LEAD_GLASS, Items.MAGENTA_DYE, ModDecorativeBlocks.MAGENTA_STAINED_LEAD_GLASS, 8);
        createDyeableBlockRecipe(exporter, ModDecorativeBlocks.LEAD_GLASS, Items.ORANGE_DYE, ModDecorativeBlocks.ORANGE_STAINED_LEAD_GLASS, 8);
        createDyeableBlockRecipe(exporter, ModDecorativeBlocks.LEAD_GLASS, Items.PINK_DYE, ModDecorativeBlocks.PINK_STAINED_LEAD_GLASS, 8);
        createDyeableBlockRecipe(exporter, ModDecorativeBlocks.LEAD_GLASS, Items.PURPLE_DYE, ModDecorativeBlocks.PURPLE_STAINED_LEAD_GLASS, 8);
        createDyeableBlockRecipe(exporter, ModDecorativeBlocks.LEAD_GLASS, Items.RED_DYE, ModDecorativeBlocks.RED_STAINED_LEAD_GLASS, 8);
        createDyeableBlockRecipe(exporter, ModDecorativeBlocks.LEAD_GLASS, Items.WHITE_DYE, ModDecorativeBlocks.WHITE_STAINED_LEAD_GLASS, 8);
        createDyeableBlockRecipe(exporter, ModDecorativeBlocks.LEAD_GLASS, Items.YELLOW_DYE, ModDecorativeBlocks.YELLOW_STAINED_LEAD_GLASS, 8);

        createLayerRecipe(exporter, Blocks.GRAVEL.asItem(), ModBlocks.GRAVEL_LAYER);
        createLayerRecipe(exporter, Blocks.SAND.asItem(), ModBlocks.SAND_LAYER);



        offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, ModBlocks.CUT_LEAD, ModBlocks.LEAD_BLOCK, 4);
        offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, ModBlocks.CUT_LEAD_SLAB, ModBlocks.LEAD_BLOCK, 8);
        offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, ModBlocks.CUT_LEAD_STAIRS, ModBlocks.LEAD_BLOCK, 4);
        offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, ModBlocks.CUT_LEAD_SLAB, ModBlocks.CUT_LEAD, 2);
        offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, ModBlocks.CUT_LEAD_STAIRS, ModBlocks.CUT_LEAD);

        offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, ModBlocks.CUT_SILVER, ModBlocks.SILVER_BLOCK, 4);
        offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, ModBlocks.CUT_SILVER_SLAB, ModBlocks.SILVER_BLOCK, 8);
        offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, ModBlocks.CUT_SILVER_STAIRS, ModBlocks.SILVER_BLOCK, 4);
        offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, ModBlocks.CUT_SILVER_SLAB, ModBlocks.CUT_SILVER, 2);
        offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, ModBlocks.CUT_SILVER_STAIRS, ModBlocks.CUT_SILVER);

        createStairsRecipe(exporter, ModBlocks.REED_BLOCK, ModBlocks.REED_STAIRS);
        createSlabsRecipe(exporter, ModBlocks.REED_BLOCK, ModBlocks.REED_SLAB);
        createWallsRecipe(exporter, ModBlocks.REED_BLOCK, ModBlocks.REED_WALL);

        createStairsRecipe(exporter, ModBlocks.STRAW_BLOCK, ModBlocks.STRAW_STAIRS);
        createSlabsRecipe(exporter, ModBlocks.STRAW_BLOCK, ModBlocks.STRAW_SLAB);
        createWallsRecipe(exporter, ModBlocks.STRAW_BLOCK, ModBlocks.STRAW_WALL);

        createPaneRecipe(exporter, ModResourceItems.SILVER_INGOT, ModBlocks.SILVERS_BARS, 6);

        createTrapdoorRecipe(exporter, Blocks.STONE, ModBlocks.STONE_TRAPDOOR);
        createTrapdoorRecipe(exporter, Blocks.BLACKSTONE, ModBlocks.BLACKSTONE_TRAPDOOR);

        createDyeableBlockRecipe(exporter, Blocks.STONE, Blocks.ICE.asItem(), StoneBlockSets.FROZEN_STONE.base(), 8);
        createDyeableBlockRecipe(exporter, Blocks.COBBLESTONE, Blocks.ICE.asItem(), StoneBlockSets.FROZEN_COBBLESTONE.base(), 8);
        createDyeableBlockRecipe(exporter, Blocks.STONE_BRICKS, Blocks.ICE.asItem(), StoneBlockSets.FROZEN_BRICKS.base(), 8);
        createDyeableBlockRecipe(exporter, StoneBlockSets.STONE_TILES.base(), Blocks.ICE.asItem(), StoneBlockSets.FROZEN_TILES.base(), 8);

        createBrickRecipe(exporter, ModResourceItems.CITRINE_SHARD, ModBlocks.CITRINE_BLOCK, 1);
        createBrickRecipe(exporter, ModResourceItems.SAPPHIRE_SHARD, ModBlocks.SAPPHIRE_BLOCK, 1);
        createBrickRecipe(exporter, ModResourceItems.RED_AGATE_SHARD, ModBlocks.RED_AGATE_BLOCK, 1);

        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.LEAD_GLASS, 4)
                .pattern("LGL")
                .pattern("GLG")
                .pattern("LGL")
                .input('L', ModResourceItems.LEAD_ROD)
                .input('G', Blocks.GLASS)
                .criterion(FabricRecipeProvider.hasItem(ModResourceItems.LEAD_ROD),
                        FabricRecipeProvider.conditionsFromItem(ModResourceItems.LEAD_ROD))
                .offerTo(exporter);


        createBrickRecipe(exporter, ModResourceItems.ASH, ModBlocks.ASH_BLOCK, 1);
        createBrickRecipe(exporter, ModBlocks.ASH_BLOCK.asItem(), Blocks.TUFF, 1);


        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, StoneBlockSets.ASHEN_STONE.base(), 4)
                .pattern("AS")
                .pattern("SA")
                .input('A', ModBlocks.ASH_BLOCK)
                .input('S', Blocks.STONE)
                .criterion(FabricRecipeProvider.hasItem(ModBlocks.ASH_BLOCK),
                        FabricRecipeProvider.conditionsFromItem(ModBlocks.ASH_BLOCK))
                .offerTo(exporter);

        //endregion

        //region SMITHING
        createToolSetRecipes(exporter, Items.STICK, StoneBlockSets.JADEITE.base().asItem(), ModToolItems.JADE_PICKAXE, ModToolItems.JADE_AXE, ModToolItems.JADE_SHOVEL, ModToolItems.JADE_HOE);

        createToolSetRecipes(exporter, Items.STICK, ModResourceItems.BRONZE_INGOT, ModToolItems.BRONZE_PICKAXE, ModToolItems.BRONZE_AXE, ModToolItems.BRONZE_SHOVEL, ModToolItems.BRONZE_HOE);
        
        createToolSetRecipes(exporter, Items.STICK, ModResourceItems.ORC_STEEL_INGOT, ModToolItems.ORC_STEEL_PICKAXE, ModToolItems.ORC_STEEL_AXE, ModToolItems.ORC_STEEL_SHOVEL, ModToolItems.ORC_STEEL_HOE);
        
        createToolSetRecipes(exporter, Items.STICK, ModResourceItems.URUK_STEEL_INGOT, ModToolItems.URUK_STEEL_PICKAXE, ModToolItems.URUK_STEEL_AXE, ModToolItems.URUK_STEEL_SHOVEL, ModToolItems.URUK_STEEL_HOE);
        
        createToolSetRecipes(exporter, Items.STICK, ModResourceItems.STEEL_INGOT, ModToolItems.STEEL_PICKAXE, ModToolItems.STEEL_AXE, ModToolItems.STEEL_SHOVEL, ModToolItems.STEEL_HOE);
        
        createToolSetRecipes(exporter, Items.STICK, ModResourceItems.ELVEN_STEEL_INGOT, ModToolItems.ELVEN_STEEL_PICKAXE, ModToolItems.ELVEN_STEEL_AXE, ModToolItems.ELVEN_STEEL_SHOVEL, ModToolItems.ELVEN_STEEL_HOE);
        
        createToolSetRecipes(exporter, ModResourceItems.DWARVEN_STEEL_ROD, ModResourceItems.DWARVEN_STEEL_INGOT, ModToolItems.DWARVEN_STEEL_PICKAXE, ModToolItems.DWARVEN_STEEL_AXE, ModToolItems.DWARVEN_STEEL_SHOVEL, ModToolItems.DWARVEN_STEEL_HOE);

        createToolSetRecipes(exporter, ModResourceItems.DWARVEN_STEEL_ROD, ModResourceItems.MITHRIL_INGOT, ModToolItems.MITHRIL_PICKAXE, ModToolItems.MITHRIL_AXE, ModToolItems.MITHRIL_SHOVEL, ModToolItems.MITHRIL_HOE);

        createBucketRecipe(exporter, ModResourceItems.BRONZE_INGOT, ModToolItems.BRONZE_BUCKET);
        createBucketRecipe(exporter, ModResourceItems.MITHRIL_INGOT, ModToolItems.MITHRIL_BUCKET);

        createMetalsRecipe(exporter, ModResourceItems.TIN_NUGGET, ModResourceItems.TIN_INGOT, ModResourceItems.TIN_ROD);
        createMetalsRecipe(exporter, ModResourceItems.LEAD_NUGGET, ModResourceItems.LEAD_INGOT, ModResourceItems.LEAD_ROD);
        createMetalsRecipe(exporter, ModResourceItems.SILVER_NUGGET, ModResourceItems.SILVER_INGOT, ModResourceItems.SILVER_ROD);
        createMetalsRecipe(exporter, ModResourceItems.MITHRIL_NUGGET, ModResourceItems.MITHRIL_INGOT, ModResourceItems.MITHRIL_ROD);

        createMetalsRecipe(exporter, ModResourceItems.BRONZE_NUGGET, ModResourceItems.BRONZE_INGOT, ModResourceItems.BRONZE_ROD);
        createMetalsRecipe(exporter, ModResourceItems.ORC_STEEL_NUGGET, ModResourceItems.ORC_STEEL_INGOT, ModResourceItems.ORC_STEEL_ROD);
        createMetalsRecipe(exporter, ModResourceItems.URUK_STEEL_NUGGET, ModResourceItems.URUK_STEEL_INGOT, ModResourceItems.URUK_STEEL_ROD);
        createMetalsRecipe(exporter, ModResourceItems.STEEL_NUGGET, ModResourceItems.STEEL_INGOT, ModResourceItems.STEEL_ROD);
        createMetalsRecipe(exporter, ModResourceItems.ELVEN_STEEL_NUGGET, ModResourceItems.ELVEN_STEEL_INGOT, ModResourceItems.ELVEN_STEEL_ROD);
        createMetalsRecipe(exporter, ModResourceItems.DWARVEN_STEEL_NUGGET, ModResourceItems.DWARVEN_STEEL_INGOT, ModResourceItems.DWARVEN_STEEL_ROD);
        createMetalsRecipe(exporter, ModResourceItems.MORGUL_STEEL_NUGGET, ModResourceItems.MORGUL_STEEL_INGOT, ModResourceItems.MORGUL_STEEL_ROD);

        createRodRecipe(exporter, Items.COPPER_INGOT, ModResourceItems.COPPER_ROD);
        createRodRecipe(exporter, Items.GOLD_INGOT, ModResourceItems.GOLD_ROD);
        createRodRecipe(exporter, Items.IRON_INGOT, ModResourceItems.IRON_ROD);
        //endregion

        //region SEEDS
        createSeedsRecipe(exporter, ModFoodItems.TOMATO, ModResourceItems.TOMATO_SEEDS);
        createSeedsRecipe(exporter, ModFoodItems.BELL_PEPPER, ModResourceItems.BELL_PEPPER_SEEDS);
        createSeedsRecipe(exporter, ModFoodItems.CUCUMBER, ModResourceItems.CUCUMBER_SEEDS);
        createSeedsRecipe(exporter, ModFoodItems.LETTUCE, ModResourceItems.LETTUCE_SEEDS);

        //endregion
    }

    //region BLOCK RECIPE METHODS

    private void createBrickRecipe(Consumer<RecipeJsonProvider> exporter, Item input, Block output, int count) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, output, count)
                .pattern("ll")
                .pattern("ll")
                .input('l', input)
                .criterion(FabricRecipeProvider.hasItem(input),
                        FabricRecipeProvider.conditionsFromItem(input))
                .offerTo(exporter);
    }

    private void createPillarRecipe(Consumer<RecipeJsonProvider> exporter, Block input, Block output, int count) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, output, count)
                .pattern("l")
                .pattern("l")
                .pattern("l")
                .input('l', input)
                .criterion(FabricRecipeProvider.hasItem(input),
                        FabricRecipeProvider.conditionsFromItem(input))
                .offerTo(exporter);
    }

    private void createChiseledRecipe(Consumer<RecipeJsonProvider> exporter, Block input, Block output, int count) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, output, count)
                .pattern("l")
                .pattern("l")
                .input('l', input)
                .criterion(FabricRecipeProvider.hasItem(input),
                        FabricRecipeProvider.conditionsFromItem(input))
                .offerTo(exporter);
    }

    private void createCutPolishedRecipe(Consumer<RecipeJsonProvider> exporter, Block input, Block output, int count) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, output, count)
                .pattern("l")
                .pattern("l")
                .input('l', input)
                .criterion(FabricRecipeProvider.hasItem(input),
                        FabricRecipeProvider.conditionsFromItem(input))
                .offerTo(exporter);
    }

    private void createMossyRecipe(Consumer<RecipeJsonProvider> exporter, Block input, Block output) {
        ShapelessRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, output, 1)
                .input(input)
                .input(Items.VINE)
                .criterion(FabricRecipeProvider.hasItem(input),
                        FabricRecipeProvider.conditionsFromItem(input))
                .offerTo(exporter, new Identifier(MiddleEarth.MOD_ID,Registries.BLOCK.getId(output).getPath() + "_vine"));

        ShapelessRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, output, 1)
                .input(input)
                .input(Blocks.MOSS_BLOCK)
                .criterion(FabricRecipeProvider.hasItem(input),
                        FabricRecipeProvider.conditionsFromItem(input))
                .offerTo(exporter, new Identifier(MiddleEarth.MOD_ID,Registries.BLOCK.getId(output).getPath() + "_moss"));
    }

    private void createCrackedRecipe(Consumer<RecipeJsonProvider> exporter, Item input, Item output) {
        CookingRecipeJsonBuilder.createSmelting(Ingredient.ofItems(input),RecipeCategory.BUILDING_BLOCKS, output, 0.1f, 200)
                .criterion(FabricRecipeProvider.hasItem(input),
                        FabricRecipeProvider.conditionsFromItem(input))
                .offerTo(exporter);
    }

    private void createStairsRecipe(Consumer<RecipeJsonProvider> exporter, Block input, Block output) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, output, 4)
                .pattern("l  ")
                .pattern("ll ")
                .pattern("lll")
                .input('l', input)
                .criterion(FabricRecipeProvider.hasItem(input),
                        FabricRecipeProvider.conditionsFromItem(input))
                .offerTo(exporter);
    }

    private void createSlabsRecipe(Consumer<RecipeJsonProvider> exporter, Block input, Block output) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, output, 6)
                .pattern("lll")
                .input('l', input)
                .criterion(FabricRecipeProvider.hasItem(input),
                        FabricRecipeProvider.conditionsFromItem(input))
                .offerTo(exporter);
    }

    private void createSlabsFromVerticalRecipe(Consumer<RecipeJsonProvider> exporter, Block input, Block output) {
        ShapelessRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, output, 1)
                .input(input)
                .criterion(FabricRecipeProvider.hasItem(input),
                        FabricRecipeProvider.conditionsFromItem(input))
                .offerTo(exporter, new Identifier(MiddleEarth.MOD_ID,Registries.BLOCK.getId(input).getPath() + "_from_vertical"));
    }

    private void createVerticalSlabsRecipe(Consumer<RecipeJsonProvider> exporter, Block input, Block output) {
        ShapelessRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, output, 1)
                .input(input)
                .criterion(FabricRecipeProvider.hasItem(input),
                        FabricRecipeProvider.conditionsFromItem(input))
                .offerTo(exporter);
    }

    private void createDoorRecipe(Consumer<RecipeJsonProvider> exporter, Block input, Block output) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, output, 3)
                .pattern("ll")
                .pattern("ll")
                .pattern("ll")
                .input('l', input)
                .criterion(FabricRecipeProvider.hasItem(input),
                        FabricRecipeProvider.conditionsFromItem(input))
                .offerTo(exporter);
    }
    private void createTrapdoorRecipe(Consumer<RecipeJsonProvider> exporter, Block input, Block output) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, output, 2)
                .pattern("lll")
                .pattern("lll")
                .input('l', input)
                .criterion(FabricRecipeProvider.hasItem(input),
                        FabricRecipeProvider.conditionsFromItem(input))
                .offerTo(exporter);
    }

    private void createWallsRecipe(Consumer<RecipeJsonProvider> exporter, Block input, Block output) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, output, 6)
                .pattern("lll")
                .pattern("lll")
                .input('l', input)
                .criterion(FabricRecipeProvider.hasItem(input),
                        FabricRecipeProvider.conditionsFromItem(input))
                .offerTo(exporter);
    }

    private void createDyeableBlockRecipe(Consumer<RecipeJsonProvider> exporter, Block blockInput, Item dyeItem, Block output, int count) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, output, count)
                .pattern("BBB")
                .pattern("BDB")
                .pattern("BBB")
                .input('B', blockInput)
                .input('D', dyeItem)
                .criterion(FabricRecipeProvider.hasItem(blockInput),
                        FabricRecipeProvider.conditionsFromItem(blockInput))
                .offerTo(exporter, new Identifier(MiddleEarth.MOD_ID,Registries.BLOCK.getId(output).getPath() + "_alt"));
    }

    private void createPaneRecipe(Consumer<RecipeJsonProvider> exporter, Item blockInput, Block output, int count) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, output, count)
                .pattern("BBB")
                .pattern("BBB")
                .input('B', blockInput)
                .criterion(FabricRecipeProvider.hasItem(blockInput),
                        FabricRecipeProvider.conditionsFromItem(blockInput))
                .offerTo(exporter);
    }

    private void createWoodStoolRecipe(Consumer<RecipeJsonProvider> exporter, Item input, Block output) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, output, 1)
                .pattern("PPP")
                .pattern("S S")
                .input('P', input)
                .input('S', Items.STICK)
                .criterion(FabricRecipeProvider.hasItem(input),
                        FabricRecipeProvider.conditionsFromItem(input))
                .offerTo(exporter);
    }

    private void createWoodTableRecipe(Consumer<RecipeJsonProvider> exporter, Item input, Block output) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, output, 1)
                .pattern("PPP")
                .pattern("S S")
                .pattern("S S")
                .input('P', input)
                .input('S', Items.STICK)
                .criterion(FabricRecipeProvider.hasItem(input),
                        FabricRecipeProvider.conditionsFromItem(input))
                .offerTo(exporter);
    }

    private void createWoodChairRecipe(Consumer<RecipeJsonProvider> exporter, Item input, Block output) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, output, 1)
                .pattern("S  ")
                .pattern("PPP")
                .pattern("S S")
                .input('P', input)
                .input('S', Items.STICK)
                .criterion(FabricRecipeProvider.hasItem(input),
                        FabricRecipeProvider.conditionsFromItem(input))
                .offerTo(exporter);
    }

    private void createStoneStoolRecipe(Consumer<RecipeJsonProvider> exporter, Item input, Block output) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, output, 1)
                .pattern("SSS")
                .pattern("S S")
                .input('S', input)
                .criterion(FabricRecipeProvider.hasItem(input),
                        FabricRecipeProvider.conditionsFromItem(input))
                .offerTo(exporter);
    }

    private void createStoneTableRecipe(Consumer<RecipeJsonProvider> exporter, Item input, Block output) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, output, 1)
                .pattern("SSS")
                .pattern(" S ")
                .pattern(" S ")
                .input('S', input)
                .criterion(FabricRecipeProvider.hasItem(input),
                        FabricRecipeProvider.conditionsFromItem(input))
                .offerTo(exporter);
    }

    private void createStoneChairRecipe(Consumer<RecipeJsonProvider> exporter, Item input, Block output) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, output, 1)
                .pattern("S  ")
                .pattern("SSS")
                .pattern("SSS")
                .input('S', input)
                .criterion(FabricRecipeProvider.hasItem(input),
                        FabricRecipeProvider.conditionsFromItem(input))
                .offerTo(exporter);
    }

    private void createLayerRecipe(Consumer<RecipeJsonProvider> exporter, Item input, Block output) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, output, 6)
                .pattern("BBB")
                .input('B', input)
                .criterion(FabricRecipeProvider.hasItem(input),
                        FabricRecipeProvider.conditionsFromItem(input))
                .offerTo(exporter);
    }


    //endregion

    //region ITEM RECIPE METHODS
    private void createSeedsRecipe(Consumer<RecipeJsonProvider> exporter, Item input, Item output) {
        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, output, 1)
                .input(input)
                .criterion(FabricRecipeProvider.hasItem(input),
                        FabricRecipeProvider.conditionsFromItem(input))
                .offerTo(exporter);
    }

    private void createPickaxeRecipe(Consumer<RecipeJsonProvider> exporter, Item inputRod, Item inputMaterial, Item output) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, output, 1)
                .pattern("MMM")
                .pattern(" R ")
                .pattern(" R ")
                .input('M', inputMaterial)
                .input('R', inputRod)
                .criterion(FabricRecipeProvider.hasItem(inputMaterial),
                        FabricRecipeProvider.conditionsFromItem(inputMaterial))
                .offerTo(exporter);
    }

    private void createAxeRecipe(Consumer<RecipeJsonProvider> exporter, Item inputRod, Item inputMaterial, Item output) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, output, 1)
                .pattern("MM ")
                .pattern("MR ")
                .pattern(" R ")
                .input('M', inputMaterial)
                .input('R', inputRod)
                .criterion(FabricRecipeProvider.hasItem(inputMaterial),
                        FabricRecipeProvider.conditionsFromItem(inputMaterial))
                .offerTo(exporter);
    }

    private void createShovelRecipe(Consumer<RecipeJsonProvider> exporter, Item inputRod, Item inputMaterial, Item output) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, output, 1)
                .pattern(" M ")
                .pattern(" R ")
                .pattern(" R ")
                .input('M', inputMaterial)
                .input('R', inputRod)
                .criterion(FabricRecipeProvider.hasItem(inputMaterial),
                        FabricRecipeProvider.conditionsFromItem(inputMaterial))
                .offerTo(exporter);
    }

    private void createHoeRecipe(Consumer<RecipeJsonProvider> exporter, Item inputRod, Item inputMaterial, Item output) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, output, 1)
                .pattern("MM ")
                .pattern(" R ")
                .pattern(" R ")
                .input('M', inputMaterial)
                .input('R', inputRod)
                .criterion(FabricRecipeProvider.hasItem(inputMaterial),
                        FabricRecipeProvider.conditionsFromItem(inputMaterial))
                .offerTo(exporter);
    }

    private void createBucketRecipe(Consumer<RecipeJsonProvider> exporter, Item inputMaterial, Item output) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, output, 1)
                .pattern("M M")
                .pattern(" M ")
                .input('M', inputMaterial)
                .criterion(FabricRecipeProvider.hasItem(inputMaterial),
                        FabricRecipeProvider.conditionsFromItem(inputMaterial))
                .offerTo(exporter);
    }

    private void createToolSetRecipes(Consumer<RecipeJsonProvider> exporter, Item inputRod, Item inputMaterial, Item outputPickaxe,  Item outputAxe, Item outputShovel, Item outputHoe) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, outputPickaxe, 1)
                .pattern("MMM")
                .pattern(" R ")
                .pattern(" R ")
                .input('M', inputMaterial)
                .input('R', inputRod)
                .criterion(FabricRecipeProvider.hasItem(inputMaterial),
                        FabricRecipeProvider.conditionsFromItem(inputMaterial))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, outputAxe, 1)
                .pattern("MM")
                .pattern("MR")
                .pattern(" R")
                .input('M', inputMaterial)
                .input('R', inputRod)
                .criterion(FabricRecipeProvider.hasItem(inputMaterial),
                        FabricRecipeProvider.conditionsFromItem(inputMaterial))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, outputShovel, 1)
                .pattern("M")
                .pattern("R")
                .pattern("R")
                .input('M', inputMaterial)
                .input('R', inputRod)
                .criterion(FabricRecipeProvider.hasItem(inputMaterial),
                        FabricRecipeProvider.conditionsFromItem(inputMaterial))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, outputHoe, 1)
                .pattern("MM")
                .pattern(" R")
                .pattern(" R")
                .input('M', inputMaterial)
                .input('R', inputRod)
                .criterion(FabricRecipeProvider.hasItem(inputMaterial),
                        FabricRecipeProvider.conditionsFromItem(inputMaterial))
                .offerTo(exporter);
    }
    private void createRodRecipe(Consumer<RecipeJsonProvider> exporter, Item ingot, Item rod) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, rod, 4)
                .pattern("I")
                .pattern("I")
                .input('I', ingot)
                .criterion(FabricRecipeProvider.hasItem(ingot),
                        FabricRecipeProvider.conditionsFromItem(ingot))
                .offerTo(exporter);
    }

    private void createMetalsRecipe(Consumer<RecipeJsonProvider> exporter, Item nugget, Item ingot, Item rod) {
        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, ingot, 1)
                .input(nugget, 9)
                .criterion(FabricRecipeProvider.hasItem(nugget),
                        FabricRecipeProvider.conditionsFromItem(nugget))
                .offerTo(exporter, new Identifier(MiddleEarth.MOD_ID,Registries.ITEM.getId(ingot).getPath() + "_from_nuggets"));

        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, nugget, 9)
                .input(ingot)
                .criterion(FabricRecipeProvider.hasItem(ingot),
                        FabricRecipeProvider.conditionsFromItem(ingot))
                .offerTo(exporter, new Identifier(MiddleEarth.MOD_ID,Registries.ITEM.getId(nugget).getPath() + "_from_ingot"));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, rod, 4)
                .pattern("I")
                .pattern("I")
                .input('I', ingot)
                .criterion(FabricRecipeProvider.hasItem(ingot),
                        FabricRecipeProvider.conditionsFromItem(ingot))
                .offerTo(exporter);
    }
    //endregion

    private void createFilledRecipe(Consumer<RecipeJsonProvider> exporter, Item input, Block output, int count) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, output, count)
                .pattern("lll")
                .pattern("lll")
                .pattern("lll")
                .input('l', input)
                .criterion(FabricRecipeProvider.hasItem(input),
                        FabricRecipeProvider.conditionsFromItem(input))
                .offerTo(exporter);
    }
}