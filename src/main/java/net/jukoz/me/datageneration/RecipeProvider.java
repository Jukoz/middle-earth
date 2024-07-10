package net.jukoz.me.datageneration;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.block.*;
import net.jukoz.me.datageneration.content.models.*;
import net.jukoz.me.item.ModFoodItems;
import net.jukoz.me.item.ModResourceItems;
import net.jukoz.me.item.ModToolItems;
import net.jukoz.me.recipe.*;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.data.DataOutput;
import net.minecraft.data.server.recipe.*;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.recipe.*;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;

import java.util.concurrent.CompletableFuture;


public class RecipeProvider extends net.minecraft.data.server.recipe.RecipeProvider {


    public RecipeProvider(DataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookupFuture) {
        super(output, registryLookupFuture);
    }

    @Override
    public void generate(RecipeExporter exporter) {

        //region STONE RECIPES
        for (StoneBlockSets.SimpleBlockSetMain record : StoneBlockSets.setsMain) {
            if(record.toString().contains("mossy_")){
                createMossyRecipe(exporter, record.source(), record.base());
            } else if(record.toString().contains("cracked_")) {
                createSmeltingRecipe(exporter, record.source().asItem(), record.base().asItem());
            } else if(record.toString().contains("cobbled_")) {
                offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, record.base(), record.source(), 1);
            } else if(record.source() != null){
                createBrickRecipe(exporter, record.source().asItem(), record.base(), 4);
                offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, record.base(), record.source(), 1);
                offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, record.slab(), record.source(), 2);
                offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, record.verticalSlab(), record.source(),2);
                offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, record.stairs(), record.source());
                offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, record.wall(), record.source());
            }

            createButtonRecipe(exporter, record.base().asItem(), record.button());
            createPressurePlateRecipe(exporter, record.base().asItem(), record.pressurePlate());
            createSlabsRecipe(exporter, record.base(), record.slab());
            offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, record.slab(), record.base(), 2);
            createStairsRecipe(exporter, record.base(), record.stairs());
            offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, record.stairs(), record.base(), 1);
            createWallsRecipe(exporter, record.base(), record.wall());
            offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, record.wall(), record.base(), 1);
            createTrapdoorRecipe(exporter, record.base(), record.trapdoor());
            createStoneStoolRecipe(exporter, record.base().asItem(), record.stool());
            createStoneTableRecipe(exporter, record.base().asItem(), record.table());
            createStoneChairRecipe(exporter, record.base().asItem(), record.chair());
        }

        for (StoneBlockSets.SimpleBlockSet record : StoneBlockSets.sets) {
            if(record.toString().contains("mossy_")){
                createMossyRecipe(exporter, record.source(), record.base());
            } else if(record.toString().contains("cracked_") || record.toString().contains("smooth_")){
                createSmeltingRecipe(exporter, record.source().asItem(), record.base().asItem());
            } else if(record.toString().contains("cobbled_") || record.toString().contains("cobblestone")) {
                offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, record.base(), record.source(), 1);
                createSmeltingRecipeIdentifier(exporter, record.base().asItem(), record.source().asItem());
            } else if(record.source() != null){
                createBrickRecipe(exporter, record.source().asItem(), record.base(), 4);
                offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, record.base(), record.source(), 1);
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
            createBrickRecipe(exporter, record.strippedLog().asItem(), record.strippedWood(), 3);
            createWallsRecipe(exporter, record.wood(), record.woodWall());
            createWallsRecipe(exporter, record.strippedWood(), record.strippedWoodWall());
            createFenceRecipe(exporter, record.planks().asItem(), record.planksFence());
            createFenceRecipe(exporter, record.wood().asItem(), record.woodFence());
            createFenceRecipe(exporter, record.strippedWood().asItem(), record.strippedWoodFence());
            createSlabsRecipe(exporter, record.planks(), record.planksSlab());
            createSlabsRecipe(exporter, record.wood(), record.woodSlab());
            createSlabsRecipe(exporter, record.strippedWood(), record.strippedWoodSlab());
            createStairsRecipe(exporter, record.planks(), record.planksStairs());
            createStairsRecipe(exporter, record.wood(), record.woodStairs());
            createStairsRecipe(exporter, record.strippedWood(), record.strippedWoodStairs());
            createDoorRecipe(exporter, record.planks(), record.door());
            createTrapdoorRecipe(exporter, record.planks(), record.trapdoor());
            createWoodStoolRecipe(exporter, record.planksSlab().asItem(), record.strippedWoodFence().asItem(), record.stool());
            createWoodTableRecipe(exporter, record.planksSlab().asItem(), record.strippedWoodFence().asItem(), record.table());
            createWoodChairRecipe(exporter, record.planksSlab().asItem(), record.strippedWoodFence().asItem(), record.chair());

            ShapelessRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, record.planks(), 4)
                    .input(record.log())
                    .criterion(FabricRecipeProvider.hasItem(record.log()),
                            FabricRecipeProvider.conditionsFromItem(record.planks()))
                    .offerTo(exporter);

            ShapelessRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, record.planks(), 4)
                    .input(record.wood())
                    .criterion(FabricRecipeProvider.hasItem(record.wood()),
                            FabricRecipeProvider.conditionsFromItem(record.planks()))
                    .offerTo(exporter, Identifier.of(MiddleEarth.MOD_ID,Registries.BLOCK.getId(record.planks()).getPath() + "_from_wood"));

            ShapelessRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, record.planks(), 4)
                    .input(record.strippedLog())
                    .criterion(FabricRecipeProvider.hasItem(record.strippedLog()),
                            FabricRecipeProvider.conditionsFromItem(record.planks()))
                    .offerTo(exporter, Identifier.of(MiddleEarth.MOD_ID,Registries.BLOCK.getId(record.planks()).getPath() + "_from_stripped_log"));

            ShapelessRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, record.planks(), 4)
                    .input(record.strippedWood())
                    .criterion(FabricRecipeProvider.hasItem(record.strippedWood()),
                            FabricRecipeProvider.conditionsFromItem(record.planks()))
                    .offerTo(exporter, Identifier.of(MiddleEarth.MOD_ID,Registries.BLOCK.getId(record.planks()).getPath() + "_from_stripped_wood"));

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

            createButtonRecipe(exporter, record.planks().asItem(), record.button());
            createPressurePlateRecipe(exporter, record.planks().asItem(), record.pressurePlate());
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
            createWoodStoolRecipe(exporter, record.planksSlab().asItem(), record.stemFence().asItem(), record.stool());
            createWoodTableRecipe(exporter, record.planksSlab().asItem(), record.stemFence().asItem(), record.table());
            createWoodChairRecipe(exporter, record.planksSlab().asItem(), record.stemFence().asItem(), record.chair());

            createFenceRecipe(exporter, record.planks().asItem(), record.planksFence());
            createFenceRecipe(exporter, record.stem().asItem(), record.stemFence());

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

            createButtonRecipe(exporter, record.planks().asItem(), record.button());
            createPressurePlateRecipe(exporter, record.planks().asItem(), record.pressurePlate());
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
                    offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, record.stairs(), record.wall(), 1);
                }
            }
            createSlabsRecipe(exporter, record.block(), record.slab());
            createStairsRecipe(exporter, record.block(), record.stairs());
            createWallsRecipe(exporter, record.block(), record.wall());
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

        for(SimpleVerticalSlabModel.VerticalSlab verticalSlab : SimpleVerticalSlabModel.strippedVerticalSlabs) {
            createVerticalSlabsRecipe(exporter, verticalSlab.slab(), verticalSlab.verticalSlab());
            createSlabsFromVerticalRecipe(exporter, verticalSlab.verticalSlab(), verticalSlab.slab());
            if(!verticalSlab.block().toString().contains("planks") || !verticalSlab.block().toString().contains("shingles")){
                offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, verticalSlab.verticalSlab(), verticalSlab.block(),2);
            }
        }

        for(SimpleVerticalSlabModel.VerticalSlab verticalSlab : SimpleVerticalSlabModel.woodVerticalSlabs) {
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

        for(SimpleVerticalSlabModel.VerticalSlab verticalSlab : SimpleVerticalSlabModel.vanillaWoodVerticalSlabs) {
            createVerticalSlabsRecipe(exporter, verticalSlab.slab(), verticalSlab.verticalSlab());
            createSlabsFromVerticalRecipe(exporter, verticalSlab.verticalSlab(), verticalSlab.slab());
            if(!verticalSlab.block().toString().contains("planks") || !verticalSlab.block().toString().contains("shingles")){
                offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, verticalSlab.verticalSlab(), verticalSlab.block(),2);
            }
        }

        for(SimpleVerticalSlabModel.VerticalSlab verticalSlab : SimpleVerticalSlabModel.vanillaStrippedVerticalSlabs) {
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
                createSmeltingRecipe(exporter, pillar.origin().asItem(), pillar.base().asItem());
            } else {
                createPillarRecipe(exporter,pillar.origin(), pillar.base(), 3);
                offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, pillar.base().asItem(), pillar.origin());
            }
        }

        for(SimpleBlockModel.ChiseledBlock block : SimpleBlockModel.chiseledBlocks) {
            createChiseledRecipe(exporter, block.origin(), block.base(), 1);
        }
        for(SimpleBlockModel.ChiseledPolishedBlock block : SimpleBlockModel.chiseledPolishedBlocksTopBottom) {
            createChiseledRecipe(exporter, block.origin(), block.base(), 1);
        }
        for(SimpleBlockModel.ChiseledBlock block : SimpleBlockModel.chiseledMainBlockTopBottom) {
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

        for(SimpleSlabModel.Slab slab : SimpleSlabModel.vanillaWoodSlabs){
            createSlabsRecipe(exporter, slab.origin(), slab.slab());
        }

        for(SimpleSlabModel.Slab slab : SimpleSlabModel.vanillaStrippedSlab){
            createSlabsRecipe(exporter, slab.origin(), slab.slab());
        }

        for(SimpleStairModel.Stair stair : SimpleStairModel.vanillaStairs){
            createStairsRecipe(exporter, stair.origin(), stair.stairs());
        }

        for(SimpleStairModel.Stair stair : SimpleStairModel.vanillaWoodStairs){
            createStairsRecipe(exporter, stair.origin(), stair.stairs());
        }

        for(SimpleStairModel.Stair stair : SimpleStairModel.vanillaStrippedStairs){
            createStairsRecipe(exporter, stair.origin(), stair.stairs());
        }

        for(SimpleWallModel.Wall wall : SimpleWallModel.vanillaWalls){
            createWallsRecipe(exporter, wall.block(), wall.wall());
        }

        for(SimpleWallModel.Wall wall : SimpleWallModel.vanillaStrippedWalls){
            createWallsRecipe(exporter, wall.block(), wall.wall());
        }

        for(SimpleWallModel.Wall wall : SimpleWallModel.vanillaWoodWalls){
            createWallsRecipe(exporter, wall.block(), wall.wall());
        }

        for(SimpleFenceModel.Fence fence : SimpleFenceModel.vanillaStrippedFences){
            createFenceRecipe(exporter, fence.block().asItem(), fence.fence());
        }

        for(SimpleFenceModel.Fence fence : SimpleFenceModel.vanillaWoodFences){
            createFenceRecipe(exporter, fence.block().asItem(), fence.fence());
        }

        for(SimplePaneModel.Pane pane : SimplePaneModel.panes){
            createPaneRecipe(exporter, pane.glass().asItem(), pane.pane(), 16);
        }

        for(SimpleTrapDoorModel.Trapdoor trapdoor : SimpleTrapDoorModel.vanillaStoneTrapdoors){
            createTrapdoorRecipe(exporter, trapdoor.block(), trapdoor.trapdoor());
        }

        for(SimpleWoodStoolModel.VanillaStool stool : SimpleWoodStoolModel.vanillaStools){
            createWoodStoolRecipe(exporter, stool.planks().asItem(), stool.legs().asItem(), stool.base());
        }

        for(SimpleWoodTableModel.VanillaTable table : SimpleWoodTableModel.vanillaTables){
            createWoodTableRecipe(exporter, table.planks().asItem(), table.legs().asItem(), table.base());
        }

        for(SimpleWoodChairModel.VanillaChair chair : SimpleWoodChairModel.vanillaChairs){
            createWoodChairRecipe(exporter, chair.planks().asItem(), chair.legs().asItem(), chair.base());
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
        createLayerRecipe(exporter, ModBlocks.BLACK_SAND.asItem(), ModBlocks.BLACK_SAND_LAYER);
        createLayerRecipe(exporter, ModBlocks.WHITE_SAND.asItem(), ModBlocks.WHITE_SAND_LAYER);
        createLayerRecipe(exporter, ModBlocks.ASHEN_SAND.asItem(), ModBlocks.ASHEN_SAND_LAYER);
        createLayerRecipe(exporter, ModBlocks.ASHEN_GRAVEL.asItem(), ModBlocks.ASHEN_GRAVEL_LAYER);

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

        createStairsRecipe(exporter, ModBlocks.GRASSY_DIRT, ModBlocks.GRASSY_DIRT_STAIRS);
        createSlabsRecipe(exporter, ModBlocks.GRASSY_DIRT, ModBlocks.GRASSY_DIRT_SLAB);

        createStairsRecipe(exporter, ModBlocks.TURF, ModBlocks.TURF_STAIRS);
        createSlabsRecipe(exporter, ModBlocks.TURF, ModBlocks.TURF_SLAB);
        
        createStairsRecipe(exporter, ModBlocks.DRY_DIRT, ModBlocks.DRY_DIRT_STAIRS);
        createSlabsRecipe(exporter, ModBlocks.DRY_DIRT, ModBlocks.DRY_DIRT_SLAB);

        createStairsRecipe(exporter, ModBlocks.ASHEN_DIRT, ModBlocks.ASHEN_DIRT_STAIRS);
        createSlabsRecipe(exporter, ModBlocks.ASHEN_DIRT, ModBlocks.ASHEN_DIRT_SLAB);

        createStairsRecipe(exporter, ModBlocks.COBBLY_ASHEN_DIRT, ModBlocks.COBBLY_ASHEN_DIRT_STAIRS);
        createSlabsRecipe(exporter, ModBlocks.COBBLY_ASHEN_DIRT, ModBlocks.COBBLY_ASHEN_DIRT_SLAB);

        createStairsRecipe(exporter, ModBlocks.COBBLY_DIRT, ModBlocks.COBBLY_DIRT_STAIRS);
        createSlabsRecipe(exporter, ModBlocks.COBBLY_DIRT, ModBlocks.COBBLY_DIRT_SLAB);

        createPaneRecipe(exporter, ModResourceItems.SILVER_INGOT, ModBlocks.SILVERS_BARS, 6);

        createDyeableBlockRecipe(exporter, Blocks.STONE, Blocks.ICE.asItem(), StoneBlockSets.FROZEN_STONE.base(), 8);
        createDyeableBlockRecipe(exporter, Blocks.COBBLESTONE, Blocks.ICE.asItem(), StoneBlockSets.FROZEN_COBBLESTONE.base(), 8);
        createDyeableBlockRecipe(exporter, Blocks.STONE_BRICKS, Blocks.ICE.asItem(), StoneBlockSets.FROZEN_BRICKS.base(), 8);
        createDyeableBlockRecipe(exporter, StoneBlockSets.STONE_TILES.base(), Blocks.ICE.asItem(), StoneBlockSets.FROZEN_TILES.base(), 8);

        createBrickRecipe(exporter, ModResourceItems.CITRINE_SHARD, ModBlocks.CITRINE_BLOCK, 1);
        createFilledRecipe(exporter, Items.GLOWSTONE, ModBlocks.GLOWSTONE_BLOCK, 1);
        createBrickRecipe(exporter, ModResourceItems.QUARTZ_SHARD, ModBlocks.QUARTZ_BLOCK, 1);
        createBrickRecipe(exporter, ModResourceItems.RED_AGATE_SHARD, ModBlocks.RED_AGATE_BLOCK, 1);

        offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, Blocks.BRICKS, StoneBlockSets.OLD_BRICKS.base());


        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.WHITE_DAUB_HOBBIT_WINDOW, 4)
                .pattern("WBW")
                .pattern("BGB")
                .pattern("WBW")
                .input('W', StoneBlockSets.WHITE_DAUB.base())
                .input('G', Blocks.GLASS)
                .input('B', Items.BRICK)
                .criterion(FabricRecipeProvider.hasItem(StoneBlockSets.WHITE_DAUB.base()),
                        FabricRecipeProvider.conditionsFromItem(StoneBlockSets.WHITE_DAUB.base()))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.YELLOW_DAUB_HOBBIT_WINDOW, 4)
                .pattern("WBW")
                .pattern("BGB")
                .pattern("WBW")
                .input('W', StoneBlockSets.YELLOW_DAUB.base())
                .input('G', Blocks.GLASS)
                .input('B', Items.BRICK)
                .criterion(FabricRecipeProvider.hasItem(StoneBlockSets.YELLOW_DAUB.base()),
                        FabricRecipeProvider.conditionsFromItem(StoneBlockSets.YELLOW_DAUB.base()))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.MEDGON_CARVED_WINDOW, 2)
                .pattern("EEE")
                .pattern("EGE")
                .pattern("EEE")
                .input('E', StoneBlockSets.MEDGON.base())
                .input('G', Blocks.GLASS)
                .criterion(FabricRecipeProvider.hasItem(StoneBlockSets.MEDGON.base()),
                        FabricRecipeProvider.conditionsFromItem(StoneBlockSets.MEDGON.base()))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.LEAD_GLASS, 4)
                .pattern("LGL")
                .pattern("GLG")
                .pattern("LGL")
                .input('L', ModResourceItems.LEAD_ROD)
                .input('G', Blocks.GLASS)
                .criterion(FabricRecipeProvider.hasItem(ModResourceItems.LEAD_ROD),
                        FabricRecipeProvider.conditionsFromItem(ModResourceItems.LEAD_ROD))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.ROPE, 3)
                .pattern("SS")
                .pattern("SS")
                .pattern("SS")
                .input('S', Items.STRING)
                .criterion(FabricRecipeProvider.hasItem(Items.STRING),
                        FabricRecipeProvider.conditionsFromItem(Items.STRING))
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

        createBucketRecipe(exporter, Items.IRON_INGOT, Items.BUCKET);
        //createBucketRecipe(exporter, ModResourceItems.MITHRIL_INGOT, ModToolItems.MITHRIL_BUCKET);

        createMetalsRecipe(exporter, ModResourceItems.TIN_NUGGET, ModResourceItems.TIN_INGOT, ModResourceItems.TIN_ROD, ModBlocks.TIN_BLOCK);
        createMetalsRecipe(exporter, ModResourceItems.LEAD_NUGGET, ModResourceItems.LEAD_INGOT, ModResourceItems.LEAD_ROD, ModBlocks.LEAD_BLOCK);
        createMetalsRecipe(exporter, ModResourceItems.SILVER_NUGGET, ModResourceItems.SILVER_INGOT, ModResourceItems.SILVER_ROD, ModBlocks.SILVER_BLOCK);
        createMetalsRecipe(exporter, ModResourceItems.MITHRIL_NUGGET, ModResourceItems.MITHRIL_INGOT, ModResourceItems.MITHRIL_ROD, ModBlocks.MITHRIL_BLOCK);

        createMetalsRecipe(exporter, ModResourceItems.BRONZE_NUGGET, ModResourceItems.BRONZE_INGOT, ModResourceItems.BRONZE_ROD, ModBlocks.BRONZE_BLOCK);
        createMetalsRecipe(exporter, ModResourceItems.ORC_STEEL_NUGGET, ModResourceItems.ORC_STEEL_INGOT, ModResourceItems.ORC_STEEL_ROD, ModBlocks.ORC_STEEL_BLOCK);
        createMetalsRecipe(exporter, ModResourceItems.URUK_STEEL_NUGGET, ModResourceItems.URUK_STEEL_INGOT, ModResourceItems.URUK_STEEL_ROD, ModBlocks.URUK_STEEL_BLOCK);
        createMetalsRecipe(exporter, ModResourceItems.STEEL_NUGGET, ModResourceItems.STEEL_INGOT, ModResourceItems.STEEL_ROD, ModBlocks.STEEL_BLOCK);
        createMetalsRecipe(exporter, ModResourceItems.ELVEN_STEEL_NUGGET, ModResourceItems.ELVEN_STEEL_INGOT, ModResourceItems.ELVEN_STEEL_ROD, ModBlocks.ELVEN_STEEL_BLOCK);
        createMetalsRecipe(exporter, ModResourceItems.DWARVEN_STEEL_NUGGET, ModResourceItems.DWARVEN_STEEL_INGOT, ModResourceItems.DWARVEN_STEEL_ROD, ModBlocks.DWARVEN_STEEL_BLOCK);
        createMetalsRecipeNoBlock(exporter, ModResourceItems.MORGUL_STEEL_NUGGET, ModResourceItems.MORGUL_STEEL_INGOT, ModResourceItems.MORGUL_STEEL_ROD);

        createRodRecipe(exporter, Items.COPPER_INGOT, ModResourceItems.COPPER_ROD);
        createRodRecipe(exporter, Items.GOLD_INGOT, ModResourceItems.GOLD_ROD);
        createRodRecipe(exporter, Items.IRON_INGOT, ModResourceItems.IRON_ROD);
        //endregion

        //region SEEDS
        createSeedsRecipe(exporter, ModFoodItems.TOMATO, ModResourceItems.TOMATO_SEEDS);
        createSeedsRecipe(exporter, ModFoodItems.BELL_PEPPER, ModResourceItems.BELL_PEPPER_SEEDS);
        createSeedsRecipe(exporter, ModFoodItems.CUCUMBER, ModResourceItems.CUCUMBER_SEEDS);
        createSeedsRecipe(exporter, ModFoodItems.LETTUCE, ModResourceItems.LETTUCE_SEEDS);
        createSeedsRecipe(exporter, ModResourceItems.PIPEWEED, ModResourceItems.PIPEWEED_SEEDS);
        createSeedsRecipe(exporter, ModResourceItems.FLAX, ModResourceItems.FLAX_SEEDS);
        //endregion

        //region FOOD
        createCookedFoodRecipes(exporter, ModFoodItems.RAW_CRAB_CLAW, ModFoodItems.COOKED_CRAB_CLAW);
        createCookedFoodRecipes(exporter, ModFoodItems.RAW_DUCK, ModFoodItems.COOKED_DUCK);
        createCookedFoodRecipes(exporter, ModFoodItems.RAW_GOOSE, ModFoodItems.COOKED_GOOSE);
        createCookedFoodRecipes(exporter, ModFoodItems.RAW_HORSE, ModFoodItems.COOKED_HORSE);
        createCookedFoodRecipes(exporter, ModFoodItems.RAW_RAT, ModFoodItems.COOKED_RAT);
        createCookedFoodRecipes(exporter, ModFoodItems.RAW_SWAN, ModFoodItems.COOKED_SWAN);
        createCookedFoodRecipes(exporter, ModFoodItems.MEAT_SKEWER, ModFoodItems.COOKED_MEAT_SKEWER);
        createCookedFoodRecipes(exporter, ModFoodItems.RAT_SKEWER, ModFoodItems.COOKED_RAT_SKEWER);
        createCookedFoodRecipes(exporter, ModFoodItems.VEGETABLE_SKEWER, ModFoodItems.COOKED_VEGETABLE_SKEWER);
        createCookedFoodRecipes(exporter, Items.EGG, ModFoodItems.BOILED_EGG);
        //endregion

        createHelmetRecipe(exporter, ModResourceItems.IRON_CHAINMAIL, Items.CHAINMAIL_HELMET);
        createChestplateRecipe(exporter, ModResourceItems.IRON_CHAINMAIL, Items.CHAINMAIL_CHESTPLATE);
        createLeggingsRecipe(exporter, ModResourceItems.IRON_CHAINMAIL, Items.CHAINMAIL_LEGGINGS);
        createBootsRecipe(exporter, ModResourceItems.IRON_CHAINMAIL, Items.CHAINMAIL_BOOTS);

        ComplexRecipeJsonBuilder.create(CustomArmorDyeRecipe::new).offerTo(exporter, "custom_armor_dye");
        ComplexRecipeJsonBuilder.create(ArmorHoodRecipe::new).offerTo(exporter, "custom_armor_hood");
        ComplexRecipeJsonBuilder.create(ArmorHoodRemovalRecipe::new).offerTo(exporter, "custom_armor_hood_removal");
        ComplexRecipeJsonBuilder.create(ArmorCapeRecipe::new).offerTo(exporter, "custom_armor_cape");
        ComplexRecipeJsonBuilder.create(ArmorCapeRemovalRecipe::new).offerTo(exporter, "custom_armor_cape_removal");
    }

    //region BLOCK RECIPE METHODS

    private void createBrickRecipe(RecipeExporter exporter, Item input, Block output, int count) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, output, count)
                .pattern("ll")
                .pattern("ll")
                .input('l', input)
                .criterion(FabricRecipeProvider.hasItem(input),
                        FabricRecipeProvider.conditionsFromItem(input))
                .offerTo(exporter);
    }

    private void createPillarRecipe(RecipeExporter exporter, Block input, Block output, int count) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, output, count)
                .pattern("l")
                .pattern("l")
                .pattern("l")
                .input('l', input)
                .criterion(FabricRecipeProvider.hasItem(input),
                        FabricRecipeProvider.conditionsFromItem(input))
                .offerTo(exporter);
    }

    private void createChiseledRecipe(RecipeExporter exporter, Block input, Block output, int count) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, output, count)
                .pattern("l")
                .pattern("l")
                .input('l', input)
                .criterion(FabricRecipeProvider.hasItem(input),
                        FabricRecipeProvider.conditionsFromItem(input))
                .offerTo(exporter);
    }

    private void createCutPolishedRecipe(RecipeExporter exporter, Block input, Block output, int count) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, output, count)
                .pattern("l")
                .pattern("l")
                .input('l', input)
                .criterion(FabricRecipeProvider.hasItem(input),
                        FabricRecipeProvider.conditionsFromItem(input))
                .offerTo(exporter);
    }

    private void createMossyRecipe(RecipeExporter exporter, Block input, Block output) {
        ShapelessRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, output, 1)
                .input(input)
                .input(Items.VINE)
                .criterion(FabricRecipeProvider.hasItem(input),
                        FabricRecipeProvider.conditionsFromItem(input))
                .offerTo(exporter, Identifier.of(MiddleEarth.MOD_ID,Registries.BLOCK.getId(output).getPath() + "_vine"));

        ShapelessRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, output, 1)
                .input(input)
                .input(Blocks.MOSS_BLOCK)
                .criterion(FabricRecipeProvider.hasItem(input),
                        FabricRecipeProvider.conditionsFromItem(input))
                .offerTo(exporter, Identifier.of(MiddleEarth.MOD_ID,Registries.BLOCK.getId(output).getPath() + "_moss"));
    }

    private void createSmeltingRecipe(RecipeExporter exporter, Item input, Item output) {
        CookingRecipeJsonBuilder.createSmelting(Ingredient.ofItems(input),RecipeCategory.BUILDING_BLOCKS, output, 0.1f, 200)
                .criterion(FabricRecipeProvider.hasItem(input),
                        FabricRecipeProvider.conditionsFromItem(input))
                .offerTo(exporter);
    }

    private void createSmeltingRecipeIdentifier(RecipeExporter exporter, Item input, Item output) {
        CookingRecipeJsonBuilder.createSmelting(Ingredient.ofItems(input),RecipeCategory.BUILDING_BLOCKS, output, 0.1f, 200)
                .criterion(FabricRecipeProvider.hasItem(input),
                        FabricRecipeProvider.conditionsFromItem(input))
                .offerTo(exporter, Identifier.of(MiddleEarth.MOD_ID, Registries.ITEM.getId(output).getPath() + "_from_smelting"));
    }

    private void createStairsRecipe(RecipeExporter exporter, Block input, Block output) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, output, 4)
                .pattern("l  ")
                .pattern("ll ")
                .pattern("lll")
                .input('l', input)
                .criterion(FabricRecipeProvider.hasItem(input),
                        FabricRecipeProvider.conditionsFromItem(input))
                .offerTo(exporter);
    }

    private void createSlabsRecipe(RecipeExporter exporter, Block input, Block output) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, output, 6)
                .pattern("lll")
                .input('l', input)
                .criterion(FabricRecipeProvider.hasItem(input),
                        FabricRecipeProvider.conditionsFromItem(input))
                .offerTo(exporter);
    }

    private void createSlabsFromVerticalRecipe(RecipeExporter exporter, Block input, Block output) {
        ShapelessRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, output, 1)
                .input(input)
                .criterion(FabricRecipeProvider.hasItem(input),
                        FabricRecipeProvider.conditionsFromItem(input))
                .offerTo(exporter, Identifier.of(MiddleEarth.MOD_ID,Registries.BLOCK.getId(input).getPath() + "_from_vertical"));
    }

    private void createVerticalSlabsRecipe(RecipeExporter exporter, Block input, Block output) {
        ShapelessRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, output, 1)
                .input(input)
                .criterion(FabricRecipeProvider.hasItem(input),
                        FabricRecipeProvider.conditionsFromItem(input))
                .offerTo(exporter);
    }

    private void createDoorRecipe(RecipeExporter exporter, Block input, Block output) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, output, 3)
                .pattern("ll")
                .pattern("ll")
                .pattern("ll")
                .input('l', input)
                .criterion(FabricRecipeProvider.hasItem(input),
                        FabricRecipeProvider.conditionsFromItem(input))
                .offerTo(exporter);
    }
    private void createTrapdoorRecipe(RecipeExporter exporter, Block input, Block output) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, output, 2)
                .pattern("lll")
                .pattern("lll")
                .input('l', input)
                .criterion(FabricRecipeProvider.hasItem(input),
                        FabricRecipeProvider.conditionsFromItem(input))
                .offerTo(exporter);
    }

    private void createWallsRecipe(RecipeExporter exporter, Block input, Block output) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, output, 6)
                .pattern("lll")
                .pattern("lll")
                .input('l', input)
                .criterion(FabricRecipeProvider.hasItem(input),
                        FabricRecipeProvider.conditionsFromItem(input))
                .offerTo(exporter);
    }

    private void createDyeableBlockRecipe(RecipeExporter exporter, Block blockInput, Item dyeItem, Block output, int count) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, output, count)
                .pattern("BBB")
                .pattern("BDB")
                .pattern("BBB")
                .input('B', blockInput)
                .input('D', dyeItem)
                .criterion(FabricRecipeProvider.hasItem(blockInput),
                        FabricRecipeProvider.conditionsFromItem(blockInput))
                .offerTo(exporter, Identifier.of(MiddleEarth.MOD_ID,Registries.BLOCK.getId(output).getPath() + "_alt"));
    }

    private void createPaneRecipe(RecipeExporter exporter, Item blockInput, Block output, int count) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, output, count)
                .pattern("BBB")
                .pattern("BBB")
                .input('B', blockInput)
                .criterion(FabricRecipeProvider.hasItem(blockInput),
                        FabricRecipeProvider.conditionsFromItem(blockInput))
                .offerTo(exporter);
    }

    private void createWoodStoolRecipe(RecipeExporter exporter, Item inputSlab, Item inputLegs, Block output) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, output, 3)
                .pattern("PPP")
                .pattern("S S")
                .input('P', inputSlab)
                .input('S', inputLegs)
                .criterion(FabricRecipeProvider.hasItem(inputSlab),
                        FabricRecipeProvider.conditionsFromItem(inputSlab))
                .offerTo(exporter);
    }

    private void createWoodTableRecipe(RecipeExporter exporter, Item inputPlanks, Item inputLegs, Block output) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, output, 3)
                .pattern("PPP")
                .pattern("S S")
                .pattern("S S")
                .input('P', inputPlanks)
                .input('S', inputLegs)
                .criterion(FabricRecipeProvider.hasItem(inputPlanks),
                        FabricRecipeProvider.conditionsFromItem(inputPlanks))
                .offerTo(exporter);
    }

    private void createWoodChairRecipe(RecipeExporter exporter, Item inputPlanks, Item inputLegs, Block output) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, output, 3)
                .pattern("S  ")
                .pattern("PPP")
                .pattern("S S")
                .input('P', inputPlanks)
                .input('S', inputLegs)
                .criterion(FabricRecipeProvider.hasItem(inputPlanks),
                        FabricRecipeProvider.conditionsFromItem(inputPlanks))
                .offerTo(exporter);
    }

    private void createStoneStoolRecipe(RecipeExporter exporter, Item input, Block output) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, output, 1)
                .pattern("SSS")
                .pattern("S S")
                .input('S', input)
                .criterion(FabricRecipeProvider.hasItem(input),
                        FabricRecipeProvider.conditionsFromItem(input))
                .offerTo(exporter);
    }

    private void createStoneTableRecipe(RecipeExporter exporter, Item input, Block output) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, output, 1)
                .pattern("SSS")
                .pattern(" S ")
                .pattern(" S ")
                .input('S', input)
                .criterion(FabricRecipeProvider.hasItem(input),
                        FabricRecipeProvider.conditionsFromItem(input))
                .offerTo(exporter);
    }

    private void createStoneChairRecipe(RecipeExporter exporter, Item input, Block output) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, output, 1)
                .pattern("S  ")
                .pattern("SSS")
                .pattern("SSS")
                .input('S', input)
                .criterion(FabricRecipeProvider.hasItem(input),
                        FabricRecipeProvider.conditionsFromItem(input))
                .offerTo(exporter);
    }

    private void createLayerRecipe(RecipeExporter exporter, Item input, Block output) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, output, 6)
                .pattern("BBB")
                .input('B', input)
                .criterion(FabricRecipeProvider.hasItem(input),
                        FabricRecipeProvider.conditionsFromItem(input))
                .offerTo(exporter);
    }

    private void createButtonRecipe(RecipeExporter exporter, Item input, Block output) {
        ShapelessRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, output, 1)
                .input(input, 1)
                .criterion(FabricRecipeProvider.hasItem(input),
                        FabricRecipeProvider.conditionsFromItem(input))
                .offerTo(exporter);
    }

    private void createPressurePlateRecipe(RecipeExporter exporter, Item input, Block output) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, output, 2)
                .pattern("BB")
                .input('B', input)
                .criterion(FabricRecipeProvider.hasItem(input),
                        FabricRecipeProvider.conditionsFromItem(input))
                .offerTo(exporter);
    }

    private void createFenceRecipe(RecipeExporter exporter, Item input, Block output) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, output, 3)
                .pattern("lsl")
                .pattern("lsl")
                .input('l', input)
                .input('s', Items.STICK)
                .criterion(FabricRecipeProvider.hasItem(input),
                        FabricRecipeProvider.conditionsFromItem(input))
                .criterion(FabricRecipeProvider.hasItem(Items.STICK),
                        FabricRecipeProvider.conditionsFromItem(Items.STICK))
                .offerTo(exporter);
    }





    //endregion

    //region ITEM RECIPE METHODS
    private void createSeedsRecipe(RecipeExporter exporter, Item input, Item output) {
        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, output, 1)
                .input(input)
                .criterion(FabricRecipeProvider.hasItem(input),
                        FabricRecipeProvider.conditionsFromItem(input))
                .offerTo(exporter);
    }

    private void createPickaxeRecipe(RecipeExporter exporter, Item inputRod, Item inputMaterial, Item output) {
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

    private void createAxeRecipe(RecipeExporter exporter, Item inputRod, Item inputMaterial, Item output) {
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

    private void createShovelRecipe(RecipeExporter exporter, Item inputRod, Item inputMaterial, Item output) {
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

    private void createHoeRecipe(RecipeExporter exporter, Item inputRod, Item inputMaterial, Item output) {
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

    private void createBucketRecipe(RecipeExporter exporter, Item inputMaterial, Item output) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, output, 1)
                .pattern("M M")
                .pattern(" M ")
                .input('M', inputMaterial)
                .criterion(FabricRecipeProvider.hasItem(inputMaterial),
                        FabricRecipeProvider.conditionsFromItem(inputMaterial))
                .offerTo(exporter);
    }


    private void createToolSetRecipes(RecipeExporter exporter, Item inputRod, Item inputMaterial, Item outputPickaxe,  Item outputAxe, Item outputShovel, Item outputHoe) {
        createPickaxeRecipe(exporter, inputRod, inputMaterial, outputPickaxe);
        createAxeRecipe(exporter, inputRod, inputMaterial, outputAxe);
        createShovelRecipe(exporter, inputRod, inputMaterial, outputShovel);
        createHoeRecipe(exporter, inputRod, inputMaterial, outputHoe);
    }
    private void createBootsRecipe(RecipeExporter exporter, Item inputMaterial, Item output) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, output, 1)
                .pattern("M M")
                .pattern("M M")
                .input('M', inputMaterial)
                .criterion(FabricRecipeProvider.hasItem(inputMaterial),
                        FabricRecipeProvider.conditionsFromItem(inputMaterial))
                .offerTo(exporter);
    }
    private void createLeggingsRecipe(RecipeExporter exporter, Item inputMaterial, Item output) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, output, 1)
                .pattern("MMM")
                .pattern("M M")
                .pattern("M M")
                .input('M', inputMaterial)
                .criterion(FabricRecipeProvider.hasItem(inputMaterial),
                        FabricRecipeProvider.conditionsFromItem(inputMaterial))
                .offerTo(exporter);
    }
        private void createChestplateRecipe(RecipeExporter exporter, Item inputMaterial, Item output) {
            ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, output, 1)
                    .pattern("M M")
                    .pattern("MMM")
                    .pattern("MMM")
                    .input('M', inputMaterial)
                    .criterion(FabricRecipeProvider.hasItem(inputMaterial),
                            FabricRecipeProvider.conditionsFromItem(inputMaterial))
                    .offerTo(exporter);
        }
        private void createHelmetRecipe(RecipeExporter exporter, Item inputMaterial, Item output) {
            ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, output, 1)
                    .pattern("MMM")
                    .pattern("M M")
                    .input('M', inputMaterial)
                    .criterion(FabricRecipeProvider.hasItem(inputMaterial),
                            FabricRecipeProvider.conditionsFromItem(inputMaterial))
                    .offerTo(exporter);
        }

    private void createRodRecipe(RecipeExporter exporter, Item ingot, Item rod) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, rod, 4)
                .pattern("I")
                .pattern("I")
                .input('I', ingot)
                .criterion(FabricRecipeProvider.hasItem(ingot),
                        FabricRecipeProvider.conditionsFromItem(ingot))
                .offerTo(exporter);
    }

    private void createCookedFoodRecipes(RecipeExporter exporter, Item rawFood, Item cookedFood) {
        net.minecraft.data.server.recipe.RecipeProvider.offerFoodCookingRecipe(exporter, "smelting", RecipeSerializer.SMELTING, SmeltingRecipe::new, 200, rawFood, cookedFood, 0.35f);
        net.minecraft.data.server.recipe.RecipeProvider.offerFoodCookingRecipe(exporter, "smoking", RecipeSerializer.SMOKING, SmokingRecipe::new, 100, rawFood, cookedFood, 0.35f);
        net.minecraft.data.server.recipe.RecipeProvider.offerFoodCookingRecipe(exporter, "campfire_cooking", RecipeSerializer.CAMPFIRE_COOKING, CampfireCookingRecipe::new, 600, rawFood, cookedFood, 0.35f);
    }

    private void createMetalsRecipe(RecipeExporter exporter, Item nugget, Item ingot, Item rod, Block block) {
        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, ingot, 1)
                .input(nugget, 9)
                .criterion(FabricRecipeProvider.hasItem(nugget),
                        FabricRecipeProvider.conditionsFromItem(nugget))
                .offerTo(exporter, Identifier.of(MiddleEarth.MOD_ID, Registries.ITEM.getId(ingot).getPath() + "_from_nuggets"));

        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, nugget, 9)
                .input(ingot)
                .criterion(FabricRecipeProvider.hasItem(ingot),
                        FabricRecipeProvider.conditionsFromItem(ingot))
                .offerTo(exporter, Identifier.of(MiddleEarth.MOD_ID, Registries.ITEM.getId(nugget).getPath() + "_from_ingot"));

        createRodRecipe(exporter, ingot, rod);

        createFilledRecipe(exporter, ingot, block, 1);

        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, ingot, 9)
                .input(block)
                .criterion(FabricRecipeProvider.hasItem(block),
                        FabricRecipeProvider.conditionsFromItem(block))
                .offerTo(exporter, Identifier.of(MiddleEarth.MOD_ID, Registries.ITEM.getId(ingot).getPath() + "_from_block"));
    }

    private void createMetalsRecipeNoBlock(RecipeExporter exporter, Item nugget, Item ingot, Item rod) {
        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, ingot, 1)
                .input(nugget, 9)
                .criterion(FabricRecipeProvider.hasItem(nugget),
                        FabricRecipeProvider.conditionsFromItem(nugget))
                .offerTo(exporter, Identifier.of(MiddleEarth.MOD_ID, Registries.ITEM.getId(ingot).getPath() + "_from_nuggets"));

        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, nugget, 9)
                .input(ingot)
                .criterion(FabricRecipeProvider.hasItem(ingot),
                        FabricRecipeProvider.conditionsFromItem(ingot))
                .offerTo(exporter, Identifier.of(MiddleEarth.MOD_ID, Registries.ITEM.getId(nugget).getPath() + "_from_ingot"));

        createRodRecipe(exporter, ingot, rod);
    }


    //endregion

    private void createFilledRecipe(RecipeExporter exporter, Item input, Block output, int count) {
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