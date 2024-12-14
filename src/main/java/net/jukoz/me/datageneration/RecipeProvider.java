package net.jukoz.me.datageneration;

import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.block.*;
import net.jukoz.me.datageneration.content.models.*;
import net.jukoz.me.datageneration.custom.AlloyRecipeJsonBuilder;
import net.jukoz.me.datageneration.custom.AnvilShapingRecipeJsonBuilder;
import net.jukoz.me.item.*;
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
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

import java.util.concurrent.CompletableFuture;

public class RecipeProvider extends net.minecraft.data.server.recipe.RecipeProvider {

    private final CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup;
    private static final int INGOT_LIQUID_VALUE = 144;

    public RecipeProvider(DataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookupFuture) {
        super(output, registryLookupFuture);

        this.registryLookup = registryLookupFuture;
    }

    @Override
    public void generate(RecipeExporter exporter) {

        //region STONE RECIPES
        for (StoneBlockSets.SimpleBlockSetMain record : StoneBlockSets.setsMain) {
            if(record.toString().contains("mossy_")) {
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

            createVerticalSlabsRecipe(exporter, record.slab(), record.verticalSlab());
            offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, record.verticalSlab(), record.base(), 2);
            offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, record.verticalSlab(), record.slab(), 1);
            createSlabsFromVerticalRecipe(exporter, record.verticalSlab(), record.slab());
            offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, record.slab(), record.verticalSlab(), 1);

            createStairsRecipe(exporter, record.base(), record.stairs());
            offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, record.stairs(), record.base(), 1);

            createWallsRecipe(exporter, record.base(), record.wall());
            offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, record.wall(), record.base(), 1);

            createTrapdoorRecipe(exporter, record.base(), record.trapdoor());

            offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, record.rocks(), record.base(), 4);

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
            createVerticalSlabsRecipe(exporter, record.slab(), record.verticalSlab());
            offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, record.verticalSlab(), record.base(), 2);
            offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, record.verticalSlab(), record.slab(), 1);
            createSlabsFromVerticalRecipe(exporter, record.verticalSlab(), record.slab());
            offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, record.slab(), record.verticalSlab(), 1);
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

            createVerticalSlabsRecipe(exporter, record.planksSlab(), record.planksVerticalSlab());
            createSlabsFromVerticalRecipe(exporter, record.planksVerticalSlab(), record.strippedWoodSlab());
            createVerticalSlabsRecipe(exporter, record.woodSlab(), record.woodVerticalSlab());
            createSlabsFromVerticalRecipe(exporter, record.woodVerticalSlab(), record.strippedWoodSlab());
            createVerticalSlabsRecipe(exporter, record.strippedWoodSlab(), record.strippedWoodVerticalSlab());
            createSlabsFromVerticalRecipe(exporter, record.strippedWoodVerticalSlab(), record.strippedWoodSlab());

            createStairsRecipe(exporter, record.planks(), record.planksStairs());
            createStairsRecipe(exporter, record.wood(), record.woodStairs());
            createStairsRecipe(exporter, record.strippedWood(), record.strippedWoodStairs());

            createDoorRecipe(exporter, record.planks(), record.door());
            createTrapdoorRecipe(exporter, record.planks(), record.trapdoor());

            createWoodStoolRecipe(exporter, record.planks().asItem(), record.stool());
            createWoodBenchRecipe(exporter, record.planks().asItem(), record.bench());
            createWoodTableRecipe(exporter, record.planks().asItem(), record.table());
            createWoodChairRecipe(exporter, record.planks().asItem(), record.chair());

            createWoodLadderRecipe(exporter, record.planks().asItem(), record.ladder());

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
            createVerticalSlabsRecipe(exporter, record.planksSlab(), record.planksVerticalSlab());
            createSlabsFromVerticalRecipe(exporter, record.planksVerticalSlab(), record.planksSlab());
            createStairsRecipe(exporter, record.planks(), record.planksStairs());
            createDoorRecipe(exporter, record.planks(), record.door());
            createTrapdoorRecipe(exporter, record.planks(), record.trapdoor());
            createWoodStoolRecipe(exporter, record.planks().asItem(), record.stool());
            createWoodBenchRecipe(exporter, record.planks().asItem(), record.bench());
            createWoodTableRecipe(exporter, record.planks().asItem(), record.table());
            createWoodChairRecipe(exporter, record.planks().asItem(), record.chair());

            createWoodLadderRecipe(exporter, record.planks().asItem(), record.ladder());

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
        for (OtherBlockSets.RoofBlockSet record : OtherBlockSets.sets) {

            if(record.origin() != null){
                ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, record.block(), 7)
                        .pattern(" l ")
                        .pattern("lll")
                        .pattern("lll")
                        .input('l', record.origin())
                        .criterion(FabricRecipeProvider.hasItem(record.origin()),
                                FabricRecipeProvider.conditionsFromItem(record.origin()))
                        .offerTo(exporter);
            }
            createSlabsRecipe(exporter, record.block(), record.slab());
            createVerticalSlabsRecipe(exporter, record.slab(), record.verticalSlab());
            createSlabsFromVerticalRecipe(exporter, record.verticalSlab(), record.slab());
            createStairsRecipe(exporter, record.block(), record.stairs());
            createWallsRecipe(exporter, record.block(), record.wall());
        }

        for (OtherBlockSets.MiscBlockSet record : OtherBlockSets.specialWoodSets) {

            if(record.origin() != null){
                ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, record.block(), 7)
                        .pattern(" l ")
                        .pattern("lll")
                        .pattern("lll")
                        .input('l', record.origin())
                        .criterion(FabricRecipeProvider.hasItem(record.origin()),
                                FabricRecipeProvider.conditionsFromItem(record.origin()))
                        .offerTo(exporter);
            }
            createSlabsRecipe(exporter, record.block(), record.slab());
            createVerticalSlabsRecipe(exporter, record.slab(), record.verticalSlab());
            createSlabsFromVerticalRecipe(exporter, record.verticalSlab(), record.slab());
            createStairsRecipe(exporter, record.block(), record.stairs());
        }
        //endregion

        //region BLOCK LIST SPECIFIC RECIPES
        for(SimpleVerticalSlabModel.VerticalSlab verticalSlab : SimpleVerticalSlabModel.vanillaVerticalSlabs) {
            createVerticalSlabsRecipe(exporter, verticalSlab.slab(), verticalSlab.verticalSlab());
            createSlabsFromVerticalRecipe(exporter, verticalSlab.verticalSlab(), verticalSlab.slab());
        }

        for(SimpleVerticalSlabModel.VerticalSlab verticalSlab : SimpleVerticalSlabModel.vanillaWoodVerticalSlabs) {
            createVerticalSlabsRecipe(exporter, verticalSlab.slab(), verticalSlab.verticalSlab());
            createSlabsFromVerticalRecipe(exporter, verticalSlab.verticalSlab(), verticalSlab.slab());
        }

        for(SimpleVerticalSlabModel.VerticalSlab verticalSlab : SimpleVerticalSlabModel.vanillaStrippedVerticalSlabs) {
            createVerticalSlabsRecipe(exporter, verticalSlab.slab(), verticalSlab.verticalSlab());
            createSlabsFromVerticalRecipe(exporter, verticalSlab.verticalSlab(), verticalSlab.slab());
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
        for(SimpleBlockModel.ChiseledPolishedBlock block : SimpleBlockModel.chiseledTilesBlocksTopBottom) {
            createCutPolishedRecipe(exporter, block.origin(), block.base(), 1);
        }
        for(SimpleBlockModel.ChiseledPolishedBlock block : SimpleBlockModel.chiseledSmoothBlocksTopBottom) {
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
            createWoodStoolRecipe(exporter, stool.planks().asItem(), stool.base());
        }

        for(SimpleWoodBenchModel.VanillaBench bench : SimpleWoodBenchModel.vanillaBenchs){
            createWoodBenchRecipe(exporter, bench.planks().asItem(), bench.base());
        }

        for(SimpleWoodTableModel.VanillaTable table : SimpleWoodTableModel.vanillaTables){
            createWoodTableRecipe(exporter, table.planks().asItem(), table.base());
        }

        for(SimpleWoodChairModel.VanillaChair chair : SimpleWoodChairModel.vanillaChairs){
            createWoodChairRecipe(exporter, chair.planks().asItem(), chair.base());
        }

        for(SimpleLadderModel.Ladder ladder : SimpleLadderModel.vanillaLadders){
            createWoodLadderRecipe(exporter, ladder.block().asItem(), ladder.ladder());
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

        for(SimpleRocksModel.Rocks rock : SimpleRocksModel.vanillaRocks){
            offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, rock.rocks(), rock.block(), 4);
        }

        //endregion

        //region MANUAL BLOCK RECIPES
        createCenterSurroundRecipe(exporter, ModDecorativeBlocks.LEAD_GLASS.asItem(), Items.BLACK_DYE, ModDecorativeBlocks.BLACK_STAINED_LEAD_GLASS.asItem(), 8);
        createCenterSurroundRecipe(exporter, ModDecorativeBlocks.LEAD_GLASS.asItem(), Items.BLUE_DYE, ModDecorativeBlocks.BLUE_STAINED_LEAD_GLASS.asItem(), 8);
        createCenterSurroundRecipe(exporter, ModDecorativeBlocks.LEAD_GLASS.asItem(), Items.BROWN_DYE, ModDecorativeBlocks.BROWN_STAINED_LEAD_GLASS.asItem(), 8);
        createCenterSurroundRecipe(exporter, ModDecorativeBlocks.LEAD_GLASS.asItem(), Items.CYAN_DYE, ModDecorativeBlocks.CYAN_STAINED_LEAD_GLASS.asItem(), 8);
        createCenterSurroundRecipe(exporter, ModDecorativeBlocks.LEAD_GLASS.asItem(), Items.GRAY_DYE, ModDecorativeBlocks.GRAY_STAINED_LEAD_GLASS.asItem(), 8);
        createCenterSurroundRecipe(exporter, ModDecorativeBlocks.LEAD_GLASS.asItem(), Items.GREEN_DYE, ModDecorativeBlocks.GREEN_STAINED_LEAD_GLASS.asItem(), 8);
        createCenterSurroundRecipe(exporter, ModDecorativeBlocks.LEAD_GLASS.asItem(), Items.LIGHT_BLUE_DYE, ModDecorativeBlocks.LIGHT_BLUE_STAINED_LEAD_GLASS.asItem(), 8);
        createCenterSurroundRecipe(exporter, ModDecorativeBlocks.LEAD_GLASS.asItem(), Items.LIGHT_GRAY_DYE, ModDecorativeBlocks.LIGHT_GRAY_STAINED_LEAD_GLASS.asItem(), 8);
        createCenterSurroundRecipe(exporter, ModDecorativeBlocks.LEAD_GLASS.asItem(), Items.LIME_DYE, ModDecorativeBlocks.LIME_STAINED_LEAD_GLASS.asItem(), 8);
        createCenterSurroundRecipe(exporter, ModDecorativeBlocks.LEAD_GLASS.asItem(), Items.MAGENTA_DYE, ModDecorativeBlocks.MAGENTA_STAINED_LEAD_GLASS.asItem(), 8);
        createCenterSurroundRecipe(exporter, ModDecorativeBlocks.LEAD_GLASS.asItem(), Items.ORANGE_DYE, ModDecorativeBlocks.ORANGE_STAINED_LEAD_GLASS.asItem(), 8);
        createCenterSurroundRecipe(exporter, ModDecorativeBlocks.LEAD_GLASS.asItem(), Items.PINK_DYE, ModDecorativeBlocks.PINK_STAINED_LEAD_GLASS.asItem(), 8);
        createCenterSurroundRecipe(exporter, ModDecorativeBlocks.LEAD_GLASS.asItem(), Items.PURPLE_DYE, ModDecorativeBlocks.PURPLE_STAINED_LEAD_GLASS.asItem(), 8);
        createCenterSurroundRecipe(exporter, ModDecorativeBlocks.LEAD_GLASS.asItem(), Items.RED_DYE, ModDecorativeBlocks.RED_STAINED_LEAD_GLASS.asItem(), 8);
        createCenterSurroundRecipe(exporter, ModDecorativeBlocks.LEAD_GLASS.asItem(), Items.WHITE_DYE, ModDecorativeBlocks.WHITE_STAINED_LEAD_GLASS.asItem(), 8);
        createCenterSurroundRecipe(exporter, ModDecorativeBlocks.LEAD_GLASS.asItem(), Items.YELLOW_DYE, ModDecorativeBlocks.YELLOW_STAINED_LEAD_GLASS.asItem(), 8);

        createLayerRecipe(exporter, Blocks.GRAVEL.asItem(), ModBlocks.GRAVEL_LAYER);
        createLayerRecipe(exporter, Blocks.SAND.asItem(), ModBlocks.SAND_LAYER);
        createLayerRecipe(exporter, ModBlocks.BLACK_SAND.asItem(), ModBlocks.BLACK_SAND_LAYER);
        createLayerRecipe(exporter, ModBlocks.WHITE_SAND.asItem(), ModBlocks.WHITE_SAND_LAYER);
        createLayerRecipe(exporter, ModBlocks.ASHEN_SAND.asItem(), ModBlocks.ASHEN_SAND_LAYER);
        createLayerRecipe(exporter, ModBlocks.ASHEN_GRAVEL.asItem(), ModBlocks.ASHEN_GRAVEL_LAYER);

        offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, ModBlocks.CUT_LEAD, ModBlocks.LEAD_BLOCK, 4);
        offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, ModBlocks.CUT_LEAD_SLAB, ModBlocks.LEAD_BLOCK, 8);
        offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, ModBlocks.CUT_LEAD_VERTICAL_SLAB, ModBlocks.LEAD_BLOCK, 8);
        offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, ModBlocks.CUT_LEAD_STAIRS, ModBlocks.LEAD_BLOCK, 4);
        offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, ModBlocks.CUT_LEAD_SLAB, ModBlocks.CUT_LEAD, 2);
        offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, ModBlocks.CUT_LEAD_STAIRS, ModBlocks.CUT_LEAD);

        offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, ModBlocks.CUT_SILVER, ModBlocks.SILVER_BLOCK, 4);
        offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, ModBlocks.CUT_SILVER_SLAB, ModBlocks.SILVER_BLOCK, 8);
        offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, ModBlocks.CUT_SILVER_VERTICAL_SLAB, ModBlocks.SILVER_BLOCK, 8);
        offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, ModBlocks.CUT_SILVER_STAIRS, ModBlocks.SILVER_BLOCK, 4);
        offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, ModBlocks.CUT_SILVER_SLAB, ModBlocks.CUT_SILVER, 2);
        offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, ModBlocks.CUT_SILVER_STAIRS, ModBlocks.CUT_SILVER);

        createStairsRecipe(exporter, ModBlocks.REED_BLOCK, ModBlocks.REED_STAIRS);
        createSlabsRecipe(exporter, ModBlocks.REED_BLOCK, ModBlocks.REED_SLAB);
        createVerticalSlabsRecipe(exporter, ModBlocks.REED_BLOCK, ModBlocks.REED_VERTICAL_SLAB);
        createWallsRecipe(exporter, ModBlocks.REED_BLOCK, ModBlocks.REED_WALL);

        createStairsRecipe(exporter, ModBlocks.STRAW_BLOCK, ModBlocks.STRAW_STAIRS);
        createSlabsRecipe(exporter, ModBlocks.STRAW_BLOCK, ModBlocks.STRAW_SLAB);
        createVerticalSlabsRecipe(exporter, ModBlocks.STRAW_BLOCK, ModBlocks.STRAW_VERTICAL_SLAB);
        createWallsRecipe(exporter, ModBlocks.STRAW_BLOCK, ModBlocks.STRAW_WALL);

        createStairsRecipe(exporter, ModBlocks.GRASSY_DIRT, ModBlocks.GRASSY_DIRT_STAIRS);
        createSlabsRecipe(exporter, ModBlocks.GRASSY_DIRT, ModBlocks.GRASSY_DIRT_SLAB);

        createStairsRecipe(exporter, ModBlocks.TURF, ModBlocks.TURF_STAIRS);
        createSlabsRecipe(exporter, ModBlocks.TURF, ModBlocks.TURF_SLAB);
        createVerticalSlabsRecipe(exporter, ModBlocks.TURF, ModBlocks.TURF_VERTICAL_SLAB);

        createStairsRecipe(exporter, ModBlocks.MIRE, ModBlocks.MIRE_STAIRS);
        createSlabsRecipe(exporter, ModBlocks.MIRE, ModBlocks.MIRE_SLAB);

        createStairsRecipe(exporter, ModBlocks.DRY_DIRT, ModBlocks.DRY_DIRT_STAIRS);
        createSlabsRecipe(exporter, ModBlocks.DRY_DIRT, ModBlocks.DRY_DIRT_SLAB);

        createStairsRecipe(exporter, ModBlocks.DIRTY_ROOTS, ModBlocks.DIRTY_ROOTS_STAIRS);
        createSlabsRecipe(exporter, ModBlocks.DIRTY_ROOTS, ModBlocks.DIRTY_ROOTS_SLAB);

        createStairsRecipe(exporter, ModBlocks.ASHEN_DIRT, ModBlocks.ASHEN_DIRT_STAIRS);
        createSlabsRecipe(exporter, ModBlocks.ASHEN_DIRT, ModBlocks.ASHEN_DIRT_SLAB);

        createStairsRecipe(exporter, ModBlocks.COBBLY_ASHEN_DIRT, ModBlocks.COBBLY_ASHEN_DIRT_STAIRS);
        createSlabsRecipe(exporter, ModBlocks.COBBLY_ASHEN_DIRT, ModBlocks.COBBLY_ASHEN_DIRT_SLAB);

        createStairsRecipe(exporter, ModBlocks.COBBLY_DIRT, ModBlocks.COBBLY_DIRT_STAIRS);
        createSlabsRecipe(exporter, ModBlocks.COBBLY_DIRT, ModBlocks.COBBLY_DIRT_SLAB);

        createPaneRecipe(exporter, Blocks.WHITE_WOOL.asItem(), ModBlocks.NET, 16);

        createPaneRecipe(exporter, Blocks.CUT_COPPER.asItem(), ModBlocks.COPPER_BARS, 16);

        createCenterSurroundRecipe(exporter, Blocks.STONE.asItem(), Blocks.ICE.asItem(), StoneBlockSets.FROZEN_STONE.base().asItem(), 8);
        createCenterSurroundRecipe(exporter, Blocks.COBBLESTONE.asItem(), Blocks.ICE.asItem(), StoneBlockSets.FROZEN_COBBLESTONE.base().asItem(), 8);
        createCenterSurroundRecipe(exporter, Blocks.STONE_BRICKS.asItem(), Blocks.ICE.asItem(), StoneBlockSets.FROZEN_BRICKS.base().asItem(), 8);
        createCenterSurroundRecipe(exporter, StoneBlockSets.STONE_TILES.base().asItem(), Blocks.ICE.asItem(), StoneBlockSets.FROZEN_TILES.base().asItem(), 8);

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
                .input('G', Items.GLASS)
                .input('B', Items.BRICK)
                .criterion(FabricRecipeProvider.hasItem(StoneBlockSets.WHITE_DAUB.base()),
                        FabricRecipeProvider.conditionsFromItem(StoneBlockSets.WHITE_DAUB.base()))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.YELLOW_DAUB_HOBBIT_WINDOW, 4)
                .pattern("WBW")
                .pattern("BGB")
                .pattern("WBW")
                .input('W', StoneBlockSets.YELLOW_DAUB.base())
                .input('G', Items.GLASS)
                .input('B', Items.BRICK)
                .criterion(FabricRecipeProvider.hasItem(StoneBlockSets.YELLOW_DAUB.base()),
                        FabricRecipeProvider.conditionsFromItem(StoneBlockSets.YELLOW_DAUB.base()))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.PLASTER_HOBBIT_WINDOW, 4)
                .pattern("WBW")
                .pattern("BGB")
                .pattern("WBW")
                .input('W', StoneBlockSets.PLASTER.base())
                .input('G', Items.GLASS)
                .input('B', Items.BRICK)
                .criterion(FabricRecipeProvider.hasItem(StoneBlockSets.PLASTER.base()),
                        FabricRecipeProvider.conditionsFromItem(StoneBlockSets.PLASTER.base()))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.MEDGON_CARVED_WINDOW, 2)
                .pattern("EEE")
                .pattern("EGE")
                .pattern("EEE")
                .input('E', StoneBlockSets.MEDGON.base())
                .input('G', Items.GLASS)
                .criterion(FabricRecipeProvider.hasItem(StoneBlockSets.MEDGON.base()),
                        FabricRecipeProvider.conditionsFromItem(StoneBlockSets.MEDGON.base()))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.GONLUIN_CARVED_WINDOW, 2)
                .pattern("EEE")
                .pattern("EGE")
                .pattern("EEE")
                .input('E', StoneBlockSets.GONLUIN.base())
                .input('G', Items.GLASS)
                .criterion(FabricRecipeProvider.hasItem(StoneBlockSets.GONLUIN.base()),
                        FabricRecipeProvider.conditionsFromItem(StoneBlockSets.GONLUIN.base()))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.TUFF_CARVED_WINDOW, 2)
                .pattern("EEE")
                .pattern("EGE")
                .pattern("EEE")
                .input('E', Blocks.TUFF)
                .input('G', Items.GLASS)
                .criterion(FabricRecipeProvider.hasItem(Blocks.TUFF),
                        FabricRecipeProvider.conditionsFromItem(Blocks.TUFF))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.IZHERABAN_CARVED_WINDOW, 2)
                .pattern("EEE")
                .pattern("EGE")
                .pattern("EEE")
                .input('E', StoneBlockSets.IZHERABAN.base())
                .input('G', Items.GLASS)
                .criterion(FabricRecipeProvider.hasItem(StoneBlockSets.IZHERABAN.base()),
                        FabricRecipeProvider.conditionsFromItem(StoneBlockSets.IZHERABAN.base()))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.LEAD_GLASS, 4)
                .pattern("LGL")
                .pattern("GLG")
                .pattern("LGL")
                .input('L', ModResourceItems.LEAD_NUGGET)
                .input('G', Items.GLASS)
                .criterion(FabricRecipeProvider.hasItem(ModResourceItems.ROD),
                        FabricRecipeProvider.conditionsFromItem(ModResourceItems.ROD))
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

        createGildedBlockRecipe(exporter, ModBlocks.CHISELED_GREEN_TUFF, ModBlocks.GILDED_CHISELED_GREEN_TUFF);
        createGildedBlockRecipe(exporter, ModBlocks.CHISELED_GREEN_TUFF_BRICKS, ModBlocks.GILDED_CHISELED_GREEN_TUFF_BRICKS);
        createGildedBlockRecipe(exporter, ModBlocks.CHISELED_POLISHED_GREEN_TUFF, ModBlocks.GILDED_CHISELED_POLISHED_GREEN_TUFF);
        createGildedBlockRecipe(exporter, ModBlocks.CHISELED_GREEN_TUFF_TILES, ModBlocks.GILDED_CHISELED_GREEN_TUFF_TILES);
        createGildedBlockRecipe(exporter, ModBlocks.CHISELED_SMOOTH_GREEN_TUFF, ModBlocks.GILDED_CHISELED_SMOOTH_GREEN_TUFF);

        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, StoneBlockSets.GILDED_GREEN_TUFF.base(), 5)
                .pattern("TNT")
                .pattern("NTN")
                .pattern("TNT")
                .input('T', StoneBlockSets.GREEN_TUFF.base())
                .input('N', Items.GOLD_NUGGET)
                .criterion(FabricRecipeProvider.hasItem(StoneBlockSets.GREEN_TUFF.base()),
                        FabricRecipeProvider.conditionsFromItem(StoneBlockSets.GREEN_TUFF.base()))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ModBlocks.WATTLE_TRAPDOOR, 2)
                .pattern("PLP")
                .pattern("PLP")
                .input('P', TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                .input('L', ModResourceItems.LEAD_NUGGET)
                .criterion(FabricRecipeProvider.hasItem(ModResourceItems.LEAD_NUGGET),
                        FabricRecipeProvider.conditionsFromItem(ModResourceItems.LEAD_NUGGET))
                .offerTo(exporter);

        createDyeableItemRecipe(exporter, ModBlocks.WATTLE_TRAPDOOR, Items.RED_DYE, ModBlocks.RED_WATTLE_TRAPDOOR);
        createDyeableItemRecipe(exporter, ModBlocks.WATTLE_TRAPDOOR, Items.GREEN_DYE, ModBlocks.GREEN_WATTLE_TRAPDOOR);
        createDyeableItemRecipe(exporter, ModBlocks.WATTLE_TRAPDOOR, Items.BROWN_DYE, ModBlocks.DARK_WATTLE_TRAPDOOR);
        createDyeableItemRecipe(exporter, ModBlocks.WATTLE_TRAPDOOR, Items.BLACK_DYE, ModBlocks.BLACK_WATTLE_TRAPDOOR);

        createBrickworkBlockRecipe(exporter, StoneBlockSets.DOLOMITE_TILES.base(), StoneBlockSets.STUCCO.base(), StoneBlockSets.DOLOMITE_BRICKWORK.base());
        createBrickworkBlockRecipe(exporter, StoneBlockSets.HEMATITE_TILES.base(), StoneBlockSets.STUCCO.base(), StoneBlockSets.HEMATITE_BRICKWORK.base());
        createBrickworkBlockRecipe(exporter, StoneBlockSets.GNEISS_TILES.base(), StoneBlockSets.STUCCO.base(), StoneBlockSets.GNEISS_BRICKWORK.base());
        createBrickworkBlockRecipe(exporter, StoneBlockSets.IZHERABAN_TILES.base(), StoneBlockSets.STUCCO.base(), StoneBlockSets.IZHERABAN_BRICKWORK.base());
        createBrickworkBlockRecipe(exporter, StoneBlockSets.LIMESTONE_TILES.base(), StoneBlockSets.STUCCO.base(), StoneBlockSets.LIMESTONE_BRICKWORK.base());
        createBrickworkBlockRecipe(exporter, StoneBlockSets.GABBRO_BRICKS.base(), StoneBlockSets.STUCCO.base(), StoneBlockSets.GABBRO_BRICKWORK.base());
        createBrickworkBlockRecipe(exporter, StoneBlockSets.ANDESITE_TILES.base(), StoneBlockSets.STUCCO.base(), StoneBlockSets.ANDESITE_BRICKWORK.base());
        createBrickworkBlockRecipe(exporter, StoneBlockSets.GRANITE_TILES.base(), StoneBlockSets.STUCCO.base(), StoneBlockSets.GRANITE_BRICKWORK.base());
        createBrickworkBlockRecipe(exporter, StoneBlockSets.DIORITE_TILES.base(), StoneBlockSets.STUCCO.base(), StoneBlockSets.DIORITE_BRICKWORK.base());
        createBrickworkBlockRecipe(exporter, StoneBlockSets.TAN_CLAY_BRICKS.base(), StoneBlockSets.PLASTER.base(), StoneBlockSets.TAN_CLAY_BRICKWORK.base());
        createBrickworkBlockRecipe(exporter, StoneBlockSets.MIXED_STONES.base(), StoneBlockSets.STUCCO.base(), StoneBlockSets.MIXED_STONES_BRICKWORK.base());

        createCenterSurroundRecipe(exporter, Items.BRICK, Items.BLUE_DYE, OtherBlockSets.BLUE_ROOF_TILES.block().asItem(), 8);
        createCenterSurroundRecipe(exporter, OtherBlockSets.BLUE_ROOF_TILES.block().asItem(), Items.WHITE_DYE, OtherBlockSets.LIGHT_BLUE_ROOF_TILES.block().asItem(), 8);
        createCenterSurroundRecipe(exporter, OtherBlockSets.BLUE_ROOF_TILES.block().asItem(), Items.LIGHT_GRAY_DYE, OtherBlockSets.BRIGHT_BLUE_ROOF_TILES.block().asItem(), 8);
        createCenterSurroundRecipe(exporter, OtherBlockSets.BLUE_ROOF_TILES.block().asItem(), Items.GRAY_DYE, OtherBlockSets.OFF_BLUE_ROOF_TILES.block().asItem(), 8);
        createCenterSurroundRecipe(exporter, OtherBlockSets.BLUE_ROOF_TILES.block().asItem(), Items.BLACK_DYE, OtherBlockSets.DARK_BLUE_ROOF_TILES.block().asItem(), 8);

        createCenterSurroundRecipe(exporter, Items.BRICK, Items.BROWN_DYE, OtherBlockSets.BROWN_ROOF_TILES.block().asItem(), 8);
        createCenterSurroundRecipe(exporter, OtherBlockSets.BROWN_ROOF_TILES.block().asItem(), Items.GRAY_DYE, OtherBlockSets.OFF_BROWN_ROOF_TILES.block().asItem(), 8);
        createCenterSurroundRecipe(exporter, OtherBlockSets.BROWN_ROOF_TILES.block().asItem(), Items.BLACK_DYE, OtherBlockSets.DARK_BROWN_ROOF_TILES.block().asItem(), 8);

        createCenterSurroundRecipe(exporter, Items.BRICK, Items.CYAN_DYE, OtherBlockSets.CYAN_ROOF_TILES.block().asItem(), 8);
        createCenterSurroundRecipe(exporter, OtherBlockSets.CYAN_ROOF_TILES.block().asItem(), Items.WHITE_DYE, OtherBlockSets.LIGHT_CYAN_ROOF_TILES.block().asItem(), 8);
        createCenterSurroundRecipe(exporter, OtherBlockSets.CYAN_ROOF_TILES.block().asItem(), Items.LIGHT_GRAY_DYE, OtherBlockSets.BRIGHT_CYAN_ROOF_TILES.block().asItem(), 8);
        createCenterSurroundRecipe(exporter, OtherBlockSets.CYAN_ROOF_TILES.block().asItem(), Items.GRAY_DYE, OtherBlockSets.OFF_CYAN_ROOF_TILES.block().asItem(), 8);
        createCenterSurroundRecipe(exporter, OtherBlockSets.CYAN_ROOF_TILES.block().asItem(), Items.BLACK_DYE, OtherBlockSets.DARK_CYAN_ROOF_TILES.block().asItem(), 8);

        createCenterSurroundRecipe(exporter, Items.BRICK, Items.GRAY_DYE, OtherBlockSets.GRAY_ROOF_TILES.block().asItem(), 8);
        createCenterSurroundRecipe(exporter, OtherBlockSets.GRAY_ROOF_TILES.block().asItem(), Items.WHITE_DYE, OtherBlockSets.LIGHT_GRAY_ROOF_TILES.block().asItem(), 8);
        createCenterSurroundRecipe(exporter, OtherBlockSets.GRAY_ROOF_TILES.block().asItem(), Items.GRAY_DYE, OtherBlockSets.OFF_GRAY_ROOF_TILES.block().asItem(), 8);
        createCenterSurroundRecipe(exporter, OtherBlockSets.GRAY_ROOF_TILES.block().asItem(), Items.BLACK_DYE, OtherBlockSets.DARK_GRAY_ROOF_TILES.block().asItem(), 8);

        createCenterSurroundRecipe(exporter, Items.BRICK, Items.GREEN_DYE, OtherBlockSets.GREEN_ROOF_TILES.block().asItem(), 8);
        createCenterSurroundRecipe(exporter, OtherBlockSets.GREEN_ROOF_TILES.block().asItem(), Items.WHITE_DYE, OtherBlockSets.LIGHT_GREEN_ROOF_TILES.block().asItem(), 8);
        createCenterSurroundRecipe(exporter, OtherBlockSets.GREEN_ROOF_TILES.block().asItem(), Items.LIGHT_GRAY_DYE, OtherBlockSets.BRIGHT_GREEN_ROOF_TILES.block().asItem(), 8);
        createCenterSurroundRecipe(exporter, OtherBlockSets.GREEN_ROOF_TILES.block().asItem(), Items.GRAY_DYE, OtherBlockSets.OFF_GREEN_ROOF_TILES.block().asItem(), 8);
        createCenterSurroundRecipe(exporter, OtherBlockSets.GREEN_ROOF_TILES.block().asItem(), Items.BLACK_DYE, OtherBlockSets.DARK_GREEN_ROOF_TILES.block().asItem(), 8);

        createCenterSurroundRecipe(exporter, Items.BRICK, Items.RED_DYE, OtherBlockSets.RED_ROOF_TILES.block().asItem(), 8);
        createCenterSurroundRecipe(exporter, OtherBlockSets.RED_ROOF_TILES.block().asItem(), Items.WHITE_DYE, OtherBlockSets.LIGHT_RED_ROOF_TILES.block().asItem(), 8);
        createCenterSurroundRecipe(exporter, OtherBlockSets.RED_ROOF_TILES.block().asItem(), Items.LIGHT_GRAY_DYE, OtherBlockSets.BRIGHT_RED_ROOF_TILES.block().asItem(), 8);
        createCenterSurroundRecipe(exporter, OtherBlockSets.RED_ROOF_TILES.block().asItem(), Items.GRAY_DYE, OtherBlockSets.OFF_RED_ROOF_TILES.block().asItem(), 8);
        createCenterSurroundRecipe(exporter, OtherBlockSets.RED_ROOF_TILES.block().asItem(), Items.BLACK_DYE, OtherBlockSets.DARK_RED_ROOF_TILES.block().asItem(), 8);

        createCenterSurroundRecipe(exporter, Items.BRICK, Items.YELLOW_DYE, OtherBlockSets.YELLOW_ROOF_TILES.block().asItem(), 8);
        createCenterSurroundRecipe(exporter, OtherBlockSets.YELLOW_ROOF_TILES.block().asItem(), Items.WHITE_DYE, OtherBlockSets.LIGHT_YELLOW_ROOF_TILES.block().asItem(), 8);
        createCenterSurroundRecipe(exporter, OtherBlockSets.YELLOW_ROOF_TILES.block().asItem(), Items.LIGHT_GRAY_DYE, OtherBlockSets.BRIGHT_YELLOW_ROOF_TILES.block().asItem(), 8);
        createCenterSurroundRecipe(exporter, OtherBlockSets.YELLOW_ROOF_TILES.block().asItem(), Items.GRAY_DYE, OtherBlockSets.OFF_YELLOW_ROOF_TILES.block().asItem(), 8);
        createCenterSurroundRecipe(exporter, OtherBlockSets.YELLOW_ROOF_TILES.block().asItem(), Items.BLACK_DYE, OtherBlockSets.DARK_YELLOW_ROOF_TILES.block().asItem(), 8);

        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, StoneBlockSets.TAN_CLAY_BRICKS.base(), 5)
                .pattern(" B ")
                .pattern("BPB")
                .pattern(" B ")
                .input('P', StoneBlockSets.PLASTER.base())
                .input('B', Items.BRICKS)
                .criterion(FabricRecipeProvider.hasItem(StoneBlockSets.PLASTER.base()),
                        FabricRecipeProvider.conditionsFromItem(StoneBlockSets.PLASTER.base()))
                .offerTo(exporter);
        //endregion

        //region SMITHING
        createDaggerRecipeTag(exporter, Items.STICK, TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")), ModWeaponItems.WOODEN_DAGGER);
        createDaggerRecipeTag(exporter, Items.STICK, TagKey.of(RegistryKeys.ITEM, Identifier.of("stone_tool_materials")), ModWeaponItems.STONE_DAGGER);
        createDaggerRecipe(exporter, Items.STICK, Items.DIAMOND, ModWeaponItems.DIAMOND_DAGGER);

        createSpearRecipeTag(exporter, Items.STICK, TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")), ModWeaponItems.WOODEN_SPEAR);
        createSpearRecipeTag(exporter, Items.STICK, TagKey.of(RegistryKeys.ITEM, Identifier.of("stone_tool_materials")), ModWeaponItems.STONE_SPEAR);
        createSpearRecipe(exporter, Items.STICK, Items.DIAMOND, ModWeaponItems.DIAMOND_SPEAR);

        createToolSetRecipes(exporter, Items.STICK, StoneBlockSets.JADEITE.base().asItem(), ModToolItems.JADE_PICKAXE, ModToolItems.JADE_AXE, ModToolItems.JADE_SHOVEL, ModToolItems.JADE_HOE);
        createSwordRecipe(exporter, Items.STICK, StoneBlockSets.JADEITE.base().asItem(), ModWeaponItems.JADE_SWORD);
        createSpearRecipe(exporter, Items.STICK, StoneBlockSets.JADEITE.base().asItem(), ModWeaponItems.JADE_SPEAR);

        createToolSetRecipes(exporter, Items.STICK, ModResourceItems.BRONZE_INGOT, ModToolItems.BRONZE_PICKAXE, ModToolItems.BRONZE_AXE, ModToolItems.BRONZE_SHOVEL, ModToolItems.BRONZE_HOE);

        createToolSetRecipes(exporter, Items.STICK, ModResourceItems.CRUDE_INGOT, ModToolItems.CRUDE_PICKAXE, ModToolItems.CRUDE_AXE, ModToolItems.CRUDE_SHOVEL, ModToolItems.CRUDE_HOE);

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModResourceItems.FABRIC, 2)
                .pattern("sss")
                .pattern("sss")
                .input('s', Items.STRING)
                .criterion(FabricRecipeProvider.hasItem(Items.STRING),
                        FabricRecipeProvider.conditionsFromItem(Items.STRING))
                .offerTo(exporter);

        createBucketRecipe(exporter, Items.IRON_INGOT, Items.BUCKET);

        createMetalsRecipe(exporter, ModResourceItems.TIN_NUGGET, ModResourceItems.TIN_INGOT, ModBlocks.TIN_BLOCK);
        createMetalsRecipe(exporter, ModResourceItems.LEAD_NUGGET, ModResourceItems.LEAD_INGOT, ModBlocks.LEAD_BLOCK);
        createMetalsRecipe(exporter, ModResourceItems.SILVER_NUGGET, ModResourceItems.SILVER_INGOT, ModBlocks.SILVER_BLOCK);
        createMetalsRecipe(exporter, ModResourceItems.MITHRIL_NUGGET, ModResourceItems.MITHRIL_INGOT, ModBlocks.MITHRIL_BLOCK);

        createMetalsRecipe(exporter, ModResourceItems.BRONZE_NUGGET, ModResourceItems.BRONZE_INGOT, ModBlocks.BRONZE_BLOCK);
        createMetalsRecipe(exporter, ModResourceItems.CRUDE_NUGGET, ModResourceItems.CRUDE_INGOT, ModBlocks.CRUDE_BLOCK);
        createMetalsRecipe(exporter, ModResourceItems.BURZUM_STEEL_NUGGET, ModResourceItems.BURZUM_STEEL_INGOT, ModBlocks.BURZUM_STEEL_BLOCK);
        createMetalsRecipe(exporter, ModResourceItems.STEEL_NUGGET, ModResourceItems.STEEL_INGOT, ModBlocks.STEEL_BLOCK);
        createMetalsRecipe(exporter, ModResourceItems.EDHEL_STEEL_NUGGET, ModResourceItems.EDHEL_STEEL_INGOT, ModBlocks.EDHEL_STEEL_BLOCK);
        createMetalsRecipe(exporter, ModResourceItems.KHAZAD_STEEL_NUGGET, ModResourceItems.KHAZAD_STEEL_INGOT, ModBlocks.KHAZAD_STEEL_BLOCK);
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
        createCookedFoodRecipes(exporter, ModFoodItems.RAW_VENISON, ModFoodItems.COOKED_VENISON);
        createCookedFoodRecipes(exporter, ModFoodItems.RAW_HORSE, ModFoodItems.COOKED_HORSE);
        createCookedFoodRecipes(exporter, ModFoodItems.RAW_SWAN, ModFoodItems.COOKED_SWAN);
        createCookedFoodRecipes(exporter, ModFoodItems.MEAT_SKEWER, ModFoodItems.COOKED_MEAT_SKEWER);
        createCookedFoodRecipes(exporter, ModFoodItems.VEGETABLE_SKEWER, ModFoodItems.COOKED_VEGETABLE_SKEWER);
        createCookedFoodRecipes(exporter, Items.EGG, ModFoodItems.BOILED_EGG);
        //endregion

        ComplexRecipeJsonBuilder.create(CustomArmorDyeRecipe::new).offerTo(exporter, "custom_armor_dye");
        ComplexRecipeJsonBuilder.create(ArmorHoodRecipe::new).offerTo(exporter, "custom_armor_hood");
        ComplexRecipeJsonBuilder.create(ArmorHoodRemovalRecipe::new).offerTo(exporter, "custom_armor_hood_removal");
        ComplexRecipeJsonBuilder.create(ArmorCapeRecipe::new).offerTo(exporter, "custom_armor_cape");
        ComplexRecipeJsonBuilder.create(ArmorCapeRemovalRecipe::new).offerTo(exporter, "custom_armor_cape_removal");

        //region Alloying
        AlloyRecipeJsonBuilder.createAlloyRecipe(RecipeCategory.MISC, "bronze", INGOT_LIQUID_VALUE * 4)
                .input(TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "copper")))
                .input(TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "copper")))
                .input(TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "copper")))
                .input(TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "tin")))
                .criterion(FabricRecipeProvider.hasItem(Items.COPPER_INGOT),
                        FabricRecipeProvider.conditionsFromItem(Items.COPPER_INGOT))
                .offerTo(exporter, Identifier.of(MiddleEarth.MOD_ID, "bronze" + "_from_alloying"));

        AlloyRecipeJsonBuilder.createAlloyRecipe(RecipeCategory.MISC, "crude", INGOT_LIQUID_VALUE * 3)
                .input(TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "copper")))
                .input(TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "copper")))
                .input(TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "tin")))
                .input(ModResourceItems.ASH)
                .criterion(FabricRecipeProvider.hasItem(Items.COPPER_INGOT),
                        FabricRecipeProvider.conditionsFromItem(Items.COPPER_INGOT))
                .offerTo(exporter, Identifier.of(MiddleEarth.MOD_ID, "crude" + "_from_alloying"));

        AlloyRecipeJsonBuilder.createAlloyRecipe(RecipeCategory.MISC, "steel", INGOT_LIQUID_VALUE * 3)
                .input(TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "iron")))
                .input(TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "iron")))
                .input(TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "iron")))
                .input(Items.COAL)
                .criterion(FabricRecipeProvider.hasItem(Items.IRON_INGOT),
                        FabricRecipeProvider.conditionsFromItem(Items.IRON_INGOT))
                .offerTo(exporter, Identifier.of(MiddleEarth.MOD_ID, "steel" + "_from_alloying_tags"));

        AlloyRecipeJsonBuilder.createAlloyRecipe(RecipeCategory.MISC, "khazad_steel", INGOT_LIQUID_VALUE * 3)
                .input(TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "iron")))
                .input(TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "iron")))
                .input(TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "lead")))
                .input(Items.COAL)
                .criterion(FabricRecipeProvider.hasItem(Items.IRON_INGOT),
                        FabricRecipeProvider.conditionsFromItem(Items.IRON_INGOT))
                .offerTo(exporter, Identifier.of(MiddleEarth.MOD_ID, "khazad_steel" + "_from_alloying_tags"));

        AlloyRecipeJsonBuilder.createAlloyRecipe(RecipeCategory.MISC, "edhel_steel", INGOT_LIQUID_VALUE * 3)
                .input(TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "iron")))
                .input(TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "iron")))
                .input(TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "iron")))
                .input(ModResourceItems.SILVER_NUGGET)
                .criterion(FabricRecipeProvider.hasItem(Items.IRON_INGOT),
                        FabricRecipeProvider.conditionsFromItem(Items.IRON_INGOT))
                .offerTo(exporter, Identifier.of(MiddleEarth.MOD_ID, "edhel_steel" + "_from_alloying_tags"));

        AlloyRecipeJsonBuilder.createAlloyRecipe(RecipeCategory.MISC, "burzum_steel", INGOT_LIQUID_VALUE * 3)
                .input(TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "iron")))
                .input(TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "iron")))
                .input(TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "lead")))
                .input(ModResourceItems.ASH)
                .criterion(FabricRecipeProvider.hasItem(Items.IRON_INGOT),
                        FabricRecipeProvider.conditionsFromItem(Items.IRON_INGOT))
                .offerTo(exporter, Identifier.of(MiddleEarth.MOD_ID, "burzum_steel" + "_from_alloying_tags"));

        HotMetalsModel.nuggets.forEach(nugget -> {
            createMeltRecipe(exporter, nugget, Registries.ITEM.getId(nugget).getPath().replace("_nugget", ""), INGOT_LIQUID_VALUE / 9);
        });
        HotMetalsModel.shapesTag.forEach(shape -> {
            createAnvilShapingRecipeTag(exporter, shape.tagKey(), shape.output(), shape.amount());
        });
        HotMetalsModel.shapesItem.forEach(shape -> {
            createAnvilShapingRecipeItem(exporter, shape.item(), shape.output(), shape.amount());
        });

        createMeltRecipeTag(exporter, TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "copper")), "copper", INGOT_LIQUID_VALUE);
        createMeltRecipeTag(exporter, TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "tin")), "tin", INGOT_LIQUID_VALUE);

        createMeltRecipe(exporter, ModResourceItems.BRONZE_INGOT, "bronze", INGOT_LIQUID_VALUE);
        createMeltRecipe(exporter, ModResourceItems.CRUDE_INGOT, "crude", INGOT_LIQUID_VALUE);

        createMeltRecipeTag(exporter, TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "lead")), "lead", INGOT_LIQUID_VALUE);
        createMeltRecipeTag(exporter, TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "silver")), "silver", INGOT_LIQUID_VALUE);
        createMeltRecipeTag(exporter, TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "iron")), "iron", INGOT_LIQUID_VALUE);
        createMeltRecipeTag(exporter, TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "gold")), "gold", INGOT_LIQUID_VALUE);

        createMeltRecipe(exporter, ModResourceItems.STEEL_INGOT, "steel", INGOT_LIQUID_VALUE);
        createMeltRecipe(exporter, ModResourceItems.KHAZAD_STEEL_INGOT, "khazad_steel", INGOT_LIQUID_VALUE);
        createMeltRecipe(exporter, ModResourceItems.EDHEL_STEEL_INGOT, "edhel_steel", INGOT_LIQUID_VALUE);
        createMeltRecipe(exporter, ModResourceItems.BURZUM_STEEL_INGOT, "burzum_steel", INGOT_LIQUID_VALUE);

        createMeltRecipeTag(exporter, TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "mithril")), "mithril", INGOT_LIQUID_VALUE);

        createMeltRecipe(exporter, Items.NETHERITE_INGOT, "netherite", INGOT_LIQUID_VALUE);

        createAnvilRecipe(exporter, ModBlocks.STEEL_BLOCK.asItem(), ModResourceItems.STEEL_INGOT, ModDecorativeItems.TREATED_ANVIL);
        createAnvilRecipe(exporter, ModBlocks.KHAZAD_STEEL_BLOCK.asItem(), ModResourceItems.KHAZAD_STEEL_INGOT, ModDecorativeItems.DWARVEN_TREATED_ANVIL);
        createAnvilRecipe(exporter, ModBlocks.EDHEL_STEEL_BLOCK.asItem(), ModResourceItems.EDHEL_STEEL_INGOT, ModDecorativeItems.ELVEN_TREATED_ANVIL);
        createAnvilRecipe(exporter, ModBlocks.BURZUM_STEEL_BLOCK.asItem(), ModResourceItems.BURZUM_STEEL_INGOT, ModDecorativeItems.ORCISH_TREATED_ANVIL);

        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ModDecorativeItems.BELLOWS, 1)
                .pattern(" PS")
                .pattern("PFF")
                .pattern("TPS")
                .input('P', TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                .input('S', Items.STICK)
                .input('F', ModResourceItems.FABRIC)
                .input('T', ModResourceItems.TIN_INGOT)
                .criterion(FabricRecipeProvider.hasItem(ModResourceItems.TIN_INGOT),
                        FabricRecipeProvider.conditionsFromItem(ModResourceItems.TIN_INGOT))
                .offerTo(exporter);

        createWattleRecipes(exporter, Items.BRICKS,
                ModBlocks.WATTLE_AND_BRICK, ModBlocks.WATTLE_AND_BRICK_CROSS, ModBlocks.WATTLE_AND_BRICK_RIGHT,
                ModBlocks.WATTLE_AND_BRICK_LEFT, ModBlocks.WATTLE_AND_BRICK_PILLAR, ModBlocks.WATTLE_AND_BRICK_DIAMOND);

        createWattleRecipes(exporter, StoneBlockSets.WHITE_DAUB.base().asItem(),
                ModBlocks.WATTLE_AND_WHITE_DAUB, ModBlocks.WATTLE_AND_WHITE_DAUB_CROSS, ModBlocks.WATTLE_AND_WHITE_DAUB_RIGHT,
                ModBlocks.WATTLE_AND_WHITE_DAUB_LEFT, ModBlocks.WATTLE_AND_WHITE_DAUB_PILLAR, ModBlocks.WATTLE_AND_WHITE_DAUB_DIAMOND);

        createWattleRecipes(exporter, StoneBlockSets.DARK_DAUB.base().asItem(),
                ModBlocks.DARK_WATTLE_AND_DARK_DAUB, ModBlocks.DARK_WATTLE_AND_DARK_DAUB_CROSS, ModBlocks.DARK_WATTLE_AND_DARK_DAUB_RIGHT,
                ModBlocks.DARK_WATTLE_AND_DARK_DAUB_LEFT, ModBlocks.DARK_WATTLE_AND_DARK_DAUB_PILLAR, ModBlocks.DARK_WATTLE_AND_DARK_DAUB_DIAMOND);

        createWattleRecipes(exporter, StoneBlockSets.YELLOW_DAUB.base().asItem(),
                ModBlocks.WATTLE_AND_YELLOW_DAUB, ModBlocks.WATTLE_AND_YELLOW_DAUB_CROSS, ModBlocks.WATTLE_AND_YELLOW_DAUB_RIGHT,
                ModBlocks.WATTLE_AND_YELLOW_DAUB_LEFT, ModBlocks.WATTLE_AND_YELLOW_DAUB_PILLAR, ModBlocks.WATTLE_AND_YELLOW_DAUB_DIAMOND);

        createCenterSurroundRecipe(exporter, ModBlocks.WATTLE_AND_WHITE_DAUB.asItem(), Items.BLACK_DYE, ModBlocks.BLACK_WATTLE_AND_WHITE_DAUB.asItem(), 8);
        createCenterSurroundRecipe(exporter, ModBlocks.WATTLE_AND_WHITE_DAUB_CROSS.asItem(), Items.BLACK_DYE, ModBlocks.BLACK_WATTLE_AND_WHITE_DAUB_CROSS.asItem(), 8);
        createCenterSurroundRecipe(exporter, ModBlocks.WATTLE_AND_WHITE_DAUB_RIGHT.asItem(), Items.BLACK_DYE, ModBlocks.BLACK_WATTLE_AND_WHITE_DAUB_RIGHT.asItem(), 8);
        createCenterSurroundRecipe(exporter, ModBlocks.WATTLE_AND_WHITE_DAUB_LEFT.asItem(), Items.BLACK_DYE, ModBlocks.BLACK_WATTLE_AND_WHITE_DAUB_LEFT.asItem(), 8);
        createCenterSurroundRecipe(exporter, ModBlocks.WATTLE_AND_WHITE_DAUB_PILLAR.asItem(), Items.BLACK_DYE, ModBlocks.BLACK_WATTLE_AND_WHITE_DAUB_PILLAR.asItem(), 8);
        createCenterSurroundRecipe(exporter, ModBlocks.WATTLE_AND_WHITE_DAUB_DIAMOND.asItem(), Items.BLACK_DYE, ModBlocks.BLACK_WATTLE_AND_WHITE_DAUB_DIAMOND.asItem(), 8);

        createCenterSurroundRecipe(exporter, ModBlocks.WATTLE_AND_WHITE_DAUB.asItem(), Items.GREEN_DYE, ModBlocks.GREEN_WATTLE_AND_WHITE_DAUB.asItem(), 8);
        createCenterSurroundRecipe(exporter, ModBlocks.WATTLE_AND_WHITE_DAUB_CROSS.asItem(), Items.GREEN_DYE, ModBlocks.GREEN_WATTLE_AND_WHITE_DAUB_CROSS.asItem(), 8);
        createCenterSurroundRecipe(exporter, ModBlocks.WATTLE_AND_WHITE_DAUB_RIGHT.asItem(), Items.GREEN_DYE, ModBlocks.GREEN_WATTLE_AND_WHITE_DAUB_RIGHT.asItem(), 8);
        createCenterSurroundRecipe(exporter, ModBlocks.WATTLE_AND_WHITE_DAUB_LEFT.asItem(), Items.GREEN_DYE, ModBlocks.GREEN_WATTLE_AND_WHITE_DAUB_LEFT.asItem(), 8);
        createCenterSurroundRecipe(exporter, ModBlocks.WATTLE_AND_WHITE_DAUB_PILLAR.asItem(), Items.GREEN_DYE, ModBlocks.GREEN_WATTLE_AND_WHITE_DAUB_PILLAR.asItem(), 8);
        createCenterSurroundRecipe(exporter, ModBlocks.WATTLE_AND_WHITE_DAUB_DIAMOND.asItem(), Items.GREEN_DYE, ModBlocks.GREEN_WATTLE_AND_WHITE_DAUB_DIAMOND.asItem(), 8);

        createCenterSurroundRecipe(exporter, ModBlocks.WATTLE_AND_WHITE_DAUB.asItem(), Items.RED_DYE, ModBlocks.RED_WATTLE_AND_WHITE_DAUB.asItem(), 8);
        createCenterSurroundRecipe(exporter, ModBlocks.WATTLE_AND_WHITE_DAUB_CROSS.asItem(), Items.RED_DYE, ModBlocks.RED_WATTLE_AND_WHITE_DAUB_CROSS.asItem(), 8);
        createCenterSurroundRecipe(exporter, ModBlocks.WATTLE_AND_WHITE_DAUB_RIGHT.asItem(), Items.RED_DYE, ModBlocks.RED_WATTLE_AND_WHITE_DAUB_RIGHT.asItem(), 8);
        createCenterSurroundRecipe(exporter, ModBlocks.WATTLE_AND_WHITE_DAUB_LEFT.asItem(), Items.RED_DYE, ModBlocks.RED_WATTLE_AND_WHITE_DAUB_LEFT.asItem(), 8);
        createCenterSurroundRecipe(exporter, ModBlocks.WATTLE_AND_WHITE_DAUB_PILLAR.asItem(), Items.RED_DYE, ModBlocks.RED_WATTLE_AND_WHITE_DAUB_PILLAR.asItem(), 8);
        createCenterSurroundRecipe(exporter, ModBlocks.WATTLE_AND_WHITE_DAUB_DIAMOND.asItem(), Items.RED_DYE, ModBlocks.RED_WATTLE_AND_WHITE_DAUB_DIAMOND.asItem(), 8);

        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ModBlocks.TREATED_STEEL_BARS, 16)
                .pattern("SSS")
                .pattern("SSS")
                .input('S', TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "steel_ingots")))
                .criterion(FabricRecipeProvider.hasItem(ModResourceItems.STEEL_INGOT),
                        FabricRecipeProvider.conditionsFromItem(ModResourceItems.STEEL_INGOT))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ModBlocks.TREATED_STEEL_TRAPDOOR, 2)
                .pattern("NSN")
                .pattern("NSN")
                .input('S', TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "steel_ingots")))
                .input('N', TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "steel_nuggets")))
                .criterion(FabricRecipeProvider.hasItem(ModResourceItems.STEEL_INGOT),
                        FabricRecipeProvider.conditionsFromItem(ModResourceItems.STEEL_INGOT))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ModBlocks.TREATED_STEEL_DOOR, 3)
                .pattern("SS")
                .pattern("SS")
                .pattern("SS")
                .input('S', TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "steel_ingots")))
                .criterion(FabricRecipeProvider.hasItem(ModResourceItems.STEEL_INGOT),
                        FabricRecipeProvider.conditionsFromItem(ModResourceItems.STEEL_INGOT))
                .offerTo(exporter);

        createPaneRecipe(exporter, ModResourceItems.SILVER_INGOT, ModBlocks.SILVERS_BARS, 16);
        createPaneRecipe(exporter, Items.GOLD_INGOT, ModBlocks.GILDED_BARS, 16);

        createCenterSurroundRecipe(exporter, StoneBlockSets.WHITE_DAUB.base().asItem(), Items.BLACK_DYE, StoneBlockSets.DARK_DAUB.base().asItem(), 8);
        createCenterSurroundRecipe(exporter, StoneBlockSets.WHITE_DAUB.base().asItem(), Items.YELLOW_DYE, StoneBlockSets.YELLOW_DAUB.base().asItem(), 8);

        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, OtherBlockSets.TREATED_WOOD.block(), 6)
                .pattern("PPP")
                .pattern("PHP")
                .pattern("PPP")
                .input('P', TagKey.of(RegistryKeys.ITEM, Identifier.of("logs")))
                .input('H', Items.HONEYCOMB)
                .criterion(FabricRecipeProvider.hasItem(Items.HONEYCOMB),
                        FabricRecipeProvider.conditionsFromItem(Items.HONEYCOMB))
                .offerTo(exporter);

        createBrickRecipe(exporter, OtherBlockSets.TREATED_WOOD.block().asItem(), OtherBlockSets.TREATED_WOOD_BEAM.block(), 3);

        ShapelessRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, OtherBlockSets.TREATED_WOOD_PLANKS.block(), 4)
                .input(OtherBlockSets.TREATED_WOOD.block())
                .criterion(FabricRecipeProvider.hasItem(OtherBlockSets.TREATED_WOOD.block()),
                        FabricRecipeProvider.conditionsFromItem(OtherBlockSets.TREATED_WOOD.block()))
                .offerTo(exporter);

        createBrickRecipe(exporter, OtherBlockSets.TREATED_WOOD_PLANKS.block().asItem(), OtherBlockSets.TREATED_WOOD_PANELS.block(), 4);

        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, OtherBlockSets.TREATED_WOOD_CARVED_BEAM.block(), 1)
                .pattern("S")
                .pattern("S")
                .input('S', OtherBlockSets.TREATED_WOOD_BEAM.slab())
                .criterion(FabricRecipeProvider.hasItem(OtherBlockSets.TREATED_WOOD_BEAM.slab()),
                        FabricRecipeProvider.conditionsFromItem(OtherBlockSets.TREATED_WOOD_BEAM.slab()))
                .offerTo(exporter);

        createBrickRecipe(exporter, OtherBlockSets.TREATED_WOOD_PANELS.block().asItem(), OtherBlockSets.TREATED_WOOD_TILING.block(), 4);

        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.CANDLE_HEAP, 1)
                .pattern("CCC")
                .pattern("CCC")
                .input('C', TagKey.of(RegistryKeys.ITEM, Identifier.of("candles")))
                .criterion(FabricRecipeProvider.hasItem(Items.CANDLE),
                        FabricRecipeProvider.conditionsFromItem(Items.CANDLE))
                .offerTo(exporter);

        createStatueRecipe(exporter, StoneBlockSets.POLISHED_CALCITE.base(), Blocks.CALCITE, ModBlocks.CALCITE_WALL, ModDecorativeBlocks.CALCITE_STATUE);
        createStatueRecipe(exporter, StoneBlockSets.POLISHED_GONLUIN.base(), StoneBlockSets.GONLUIN.base(), StoneBlockSets.GONLUIN.wall(), ModDecorativeBlocks.GONLUIN_STATUE);
        createStatueRecipe(exporter, Blocks.POLISHED_TUFF, Blocks.TUFF, Blocks.TUFF_WALL, ModDecorativeBlocks.TUFF_STATUE);

        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.WATTLE_AND_BRICK_WINDOW, 4)
                .pattern("BSB")
                .pattern("SGS")
                .pattern("BSB")
                .input('B', Items.BRICKS)
                .input('G', Items.GLASS)
                .input('S', Items.STICK)
                .criterion(FabricRecipeProvider.hasItem(Items.BRICKS),
                        FabricRecipeProvider.conditionsFromItem(Items.BRICKS))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.WATTLE_FRAMED_WINDOW, 2)
                .pattern("SSS")
                .pattern("SGS")
                .pattern("SSS")
                .input('G', Items.GLASS)
                .input('S', Items.STICK)
                .criterion(FabricRecipeProvider.hasItem(Items.STICK),
                        FabricRecipeProvider.conditionsFromItem(Items.STICK))
                .offerTo(exporter);

        createCenterSurroundRecipe(exporter, ModDecorativeBlocks.WATTLE_FRAMED_WINDOW.asItem(), Items.GRAY_DYE, ModDecorativeBlocks.DARK_WATTLE_FRAMED_WINDOW.asItem(), 8);
        createCenterSurroundRecipe(exporter, ModDecorativeBlocks.WATTLE_FRAMED_WINDOW.asItem(), Items.BLACK_DYE, ModDecorativeBlocks.BLACK_WATTLE_FRAMED_WINDOW.asItem(), 8);
        createCenterSurroundRecipe(exporter, ModDecorativeBlocks.WATTLE_FRAMED_WINDOW.asItem(), Items.GREEN_DYE, ModDecorativeBlocks.GREEN_WATTLE_FRAMED_WINDOW.asItem(), 8);
        createCenterSurroundRecipe(exporter, ModDecorativeBlocks.WATTLE_FRAMED_WINDOW.asItem(), Items.RED_DYE, ModDecorativeBlocks.RED_WATTLE_FRAMED_WINDOW.asItem(), 8);
        createCenterSurroundRecipe(exporter, ModDecorativeBlocks.WATTLE_FRAMED_WINDOW.asItem(), Items.WHITE_DYE, ModDecorativeBlocks.WHITE_WATTLE_FRAMED_WINDOW.asItem(), 8);

        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.MUD_BRICK_ROUND_WINDOW, 4)
                .pattern("MBM")
                .pattern("BGB")
                .pattern("MBM")
                .input('M', Items.MUD_BRICKS)
                .input('G', Items.GLASS)
                .input('B', Items.BRICK)
                .criterion(FabricRecipeProvider.hasItem(Items.BRICKS),
                        FabricRecipeProvider.conditionsFromItem(Items.BRICKS))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.WHITE_DAUB_ROUND_WINDOW, 4)
                .pattern("WSW")
                .pattern("SGS")
                .pattern("WSW")
                .input('W', StoneBlockSets.WHITE_DAUB.base())
                .input('G', Items.GLASS)
                .input('S', Items.STICK)
                .criterion(FabricRecipeProvider.hasItem(StoneBlockSets.WHITE_DAUB.base()),
                        FabricRecipeProvider.conditionsFromItem(StoneBlockSets.WHITE_DAUB.base()))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.YELLOW_DAUB_ROUND_WINDOW, 4)
                .pattern("WSW")
                .pattern("SGS")
                .pattern("WSW")
                .input('W', StoneBlockSets.YELLOW_DAUB.base())
                .input('G', Items.GLASS)
                .input('S', Items.STICK)
                .criterion(FabricRecipeProvider.hasItem(StoneBlockSets.YELLOW_DAUB.base()),
                        FabricRecipeProvider.conditionsFromItem(StoneBlockSets.YELLOW_DAUB.base()))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.PLASTER_ROUND_WINDOW, 4)
                .pattern("WSW")
                .pattern("SGS")
                .pattern("WSW")
                .input('W', StoneBlockSets.PLASTER.base())
                .input('G', Items.GLASS)
                .input('S', Items.STICK)
                .criterion(FabricRecipeProvider.hasItem(StoneBlockSets.PLASTER.base()),
                        FabricRecipeProvider.conditionsFromItem(StoneBlockSets.PLASTER.base()))
                .offerTo(exporter);

        createCushionRecipe(exporter, Blocks.BLUE_WOOL, ModDecorativeBlocks.BLUE_CUSHION);
        createDyeableItemRecipe(exporter, ModDecorativeBlocks.BLUE_CUSHION, Items.GRAY_DYE, ModDecorativeBlocks.DARK_BLUE_CUSHION);
        createCushionRecipe(exporter, Blocks.BROWN_WOOL, ModDecorativeBlocks.BROWN_CUSHION);
        createDyeableItemRecipe(exporter, ModDecorativeBlocks.BROWN_CUSHION, Items.GRAY_DYE, ModDecorativeBlocks.DARK_BROWN_CUSHION);
        createCushionRecipe(exporter, Blocks.GREEN_WOOL, ModDecorativeBlocks.GREEN_CUSHION);
        createDyeableItemRecipe(exporter, ModDecorativeBlocks.GREEN_CUSHION, Items.GRAY_DYE, ModDecorativeBlocks.DARK_GREEN_CUSHION);
        createCushionRecipe(exporter, Blocks.RED_WOOL, ModDecorativeBlocks.RED_CUSHION);
        createDyeableItemRecipe(exporter, ModDecorativeBlocks.RED_CUSHION, Items.GRAY_DYE, ModDecorativeBlocks.DARK_RED_CUSHION);

        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.ROPE_LADDER, 3)
                .pattern("R R")
                .pattern("RSR")
                .pattern("R R")
                .input('R', ModDecorativeBlocks.ROPE)
                .input('S', Items.STRING)
                .criterion(FabricRecipeProvider.hasItem(ModDecorativeBlocks.ROPE),
                        FabricRecipeProvider.conditionsFromItem(ModDecorativeBlocks.ROPE))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.FANCY_BED, 1)
                .pattern("FFW")
                .pattern("FFW")
                .pattern("PPP")
                .input('W', TagKey.of(RegistryKeys.ITEM, Identifier.of("wool")))
                .input('F', ModResourceItems.FABRIC)
                .input('P', TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                .criterion(FabricRecipeProvider.hasItem(ModResourceItems.FABRIC),
                        FabricRecipeProvider.conditionsFromItem(ModResourceItems.FABRIC))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.STRAW_BED, 1)
                .pattern("SSS")
                .pattern("PPP")
                .input('S', ModResourceItems.STRAW)
                .input('P', TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                .criterion(FabricRecipeProvider.hasItem(ModResourceItems.STRAW),
                        FabricRecipeProvider.conditionsFromItem(ModResourceItems.STRAW))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.FUR_BED, 1)
                .pattern("FFF")
                .pattern("PPP")
                .input('F', ModResourceItems.FUR)
                .input('P', TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                .criterion(FabricRecipeProvider.hasItem(ModResourceItems.FUR),
                        FabricRecipeProvider.conditionsFromItem(ModResourceItems.FUR))
                .offerTo(exporter);

        createCenterSurroundRecipe(exporter, ModResourceItems.SILVER_NUGGET, Items.TORCH, ModDecorativeItems.SILVER_LANTERN, 1);
        createCenterSurroundRecipe(exporter, ModResourceItems.KHAZAD_STEEL_NUGGET, Items.TORCH, ModDecorativeItems.DWARVEN_LANTERN, 1);
        createCenterSurroundRecipe(exporter, ModResourceItems.EDHEL_STEEL_NUGGET, Items.TORCH, ModDecorativeItems.ELVEN_LANTERN, 1);

        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ModDecorativeItems.CRYSTAL_LAMP, 1)
                .pattern("NGN")
                .pattern("GLG")
                .pattern("NIN")
                .input('N', ModResourceItems.BRONZE_NUGGET)
                .input('I', ModResourceItems.BRONZE_INGOT)
                .input('L', Items.GLOWSTONE)
                .input('G', Items.GLASS)
                .criterion(FabricRecipeProvider.hasItem(Items.GLOWSTONE),
                        FabricRecipeProvider.conditionsFromItem(Items.GLOWSTONE))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ModDecorativeItems.SCONCE, 4)
                .pattern("NTN")
                .pattern(" I ")
                .input('N', TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "steel_nuggets")))
                .input('I', TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "steel_ingots")))
                .input('T', Items.TORCH)
                .criterion(FabricRecipeProvider.hasItem(Items.TORCH),
                        FabricRecipeProvider.conditionsFromItem(Items.TORCH))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ModDecorativeItems.GILDED_SCONCE, 4)
                .pattern("NTN")
                .pattern(" I ")
                .input('N', Items.GOLD_NUGGET)
                .input('I', Items.GOLD_INGOT)
                .input('T', Items.TORCH)
                .criterion(FabricRecipeProvider.hasItem(Items.TORCH),
                        FabricRecipeProvider.conditionsFromItem(Items.TORCH))
                .offerTo(exporter);

        createWoodStoolRecipe(exporter, OtherBlockSets.TREATED_WOOD_PLANKS.block().asItem(), ModDecorativeBlocks.TREATED_WOOD_STOOL);
        createWoodBenchRecipe(exporter, OtherBlockSets.TREATED_WOOD_PLANKS.block().asItem(), ModDecorativeBlocks.TREATED_WOOD_BENCH);
        createWoodTableRecipe(exporter, OtherBlockSets.TREATED_WOOD_PLANKS.block().asItem(), ModDecorativeBlocks.TREATED_WOOD_TABLE);
        createWoodChairRecipe(exporter, OtherBlockSets.TREATED_WOOD_PLANKS.block().asItem(), ModDecorativeBlocks.TREATED_WOOD_CHAIR);
        createWoodLadderRecipe(exporter, OtherBlockSets.TREATED_WOOD_PLANKS.block().asItem(), ModDecorativeBlocks.TREATED_WOOD_LADDER);

        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.LARCH_HOBBIT_DOOR, 1)
                .pattern("LLL")
                .pattern("LSL")
                .pattern("LLL")
                .input('S', ModResourceItems.STEEL_INGOT)
                .input('L', WoodBlockSets.LARCH.planks())
                .criterion(FabricRecipeProvider.hasItem(WoodBlockSets.LARCH.planks()),
                        FabricRecipeProvider.conditionsFromItem(WoodBlockSets.LARCH.planks()))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.SPRUCE_HOBBIT_DOOR, 1)
                .pattern("LSL")
                .pattern("SLL")
                .pattern("LSL")
                .input('S', ModResourceItems.STEEL_INGOT)
                .input('L', Items.SPRUCE_PLANKS)
                .criterion(FabricRecipeProvider.hasItem(Items.SPRUCE_PLANKS),
                        FabricRecipeProvider.conditionsFromItem(Items.SPRUCE_PLANKS))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.BLUE_HOBBIT_DOOR, 1)
                .pattern(" B ")
                .pattern("BDG")
                .pattern(" B ")
                .input('D', ModDecorativeBlocks.LARCH_HOBBIT_DOOR)
                .input('B', Items.BLUE_DYE)
                .input('G', Items.GOLD_INGOT)
                .criterion(FabricRecipeProvider.hasItem(ModDecorativeBlocks.LARCH_HOBBIT_DOOR),
                        FabricRecipeProvider.conditionsFromItem(ModDecorativeBlocks.LARCH_HOBBIT_DOOR))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.GREEN_HOBBIT_DOOR, 1)
                .pattern(" B ")
                .pattern("BDG")
                .pattern(" B ")
                .input('D', ModDecorativeBlocks.LARCH_HOBBIT_DOOR)
                .input('B', Items.GREEN_DYE)
                .input('G', Items.GOLD_INGOT)
                .criterion(FabricRecipeProvider.hasItem(ModDecorativeBlocks.LARCH_HOBBIT_DOOR),
                        FabricRecipeProvider.conditionsFromItem(ModDecorativeBlocks.LARCH_HOBBIT_DOOR))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.RED_HOBBIT_DOOR, 1)
                .pattern(" B ")
                .pattern("BDG")
                .pattern(" B ")
                .input('D', ModDecorativeBlocks.LARCH_HOBBIT_DOOR)
                .input('B', Items.RED_DYE)
                .input('G', Items.GOLD_INGOT)
                .criterion(FabricRecipeProvider.hasItem(ModDecorativeBlocks.LARCH_HOBBIT_DOOR),
                        FabricRecipeProvider.conditionsFromItem(ModDecorativeBlocks.LARCH_HOBBIT_DOOR))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.YELLOW_HOBBIT_DOOR, 1)
                .pattern(" B ")
                .pattern("BDG")
                .pattern(" B ")
                .input('D', ModDecorativeBlocks.LARCH_HOBBIT_DOOR)
                .input('B', Items.YELLOW_DYE)
                .input('G', Items.GOLD_INGOT)
                .criterion(FabricRecipeProvider.hasItem(ModDecorativeBlocks.LARCH_HOBBIT_DOOR),
                        FabricRecipeProvider.conditionsFromItem(ModDecorativeBlocks.LARCH_HOBBIT_DOOR))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.REINFORCED_SPRUCE_DOOR, 1)
                .pattern("SPP")
                .pattern("SPS")
                .pattern("SPP")
                .input('S', ModResourceItems.STEEL_INGOT)
                .input('P', Items.SPRUCE_PLANKS)
                .criterion(FabricRecipeProvider.hasItem(Items.SPRUCE_PLANKS),
                        FabricRecipeProvider.conditionsFromItem(Items.SPRUCE_PLANKS))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.GREAT_GONDORIAN_GATE, 1)
                .pattern("LCL")
                .pattern("CCS")
                .pattern("LCL")
                .input('L', WoodBlockSets.BLACK_LEBETHRON.planks())
                .input('C', Items.OXIDIZED_COPPER)
                .input('S', ModResourceItems.STEEL_INGOT)
                .criterion(FabricRecipeProvider.hasItem(Items.OXIDIZED_COPPER),
                        FabricRecipeProvider.conditionsFromItem(Items.OXIDIZED_COPPER))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.GREAT_DWARVEN_GATE, 1)
                .pattern("BTB")
                .pattern("BTS")
                .pattern("BTB")
                .input('B', ModResourceItems.BRONZE_INGOT)
                .input('T', OtherBlockSets.TREATED_WOOD.block())
                .input('S', ModResourceItems.KHAZAD_STEEL_INGOT)
                .criterion(FabricRecipeProvider.hasItem(ModResourceItems.BRONZE_INGOT),
                        FabricRecipeProvider.conditionsFromItem(ModResourceItems.BRONZE_INGOT))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.VARNISHED_DWARVEN_DOOR, 1)
                .pattern("TNT")
                .pattern("TTS")
                .pattern("TNT")
                .input('N', ModResourceItems.STEEL_NUGGET)
                .input('T', OtherBlockSets.TREATED_WOOD.block())
                .input('S', ModResourceItems.KHAZAD_STEEL_INGOT)
                .criterion(FabricRecipeProvider.hasItem(ModResourceItems.BRONZE_INGOT),
                        FabricRecipeProvider.conditionsFromItem(ModResourceItems.BRONZE_INGOT))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.HIDDEN_DWARVEN_DOOR, 1)
                .pattern("SSG")
                .pattern("GDL")
                .pattern("DSS")
                .input('L', Items.LEVER)
                .input('G', StoneBlockSets.SMOOTH_DOLOMITE.base())
                .input('D', StoneBlockSets.DOLOMITE.base())
                .input('S', Items.STONE)
                .criterion(FabricRecipeProvider.hasItem(StoneBlockSets.SMOOTH_DOLOMITE.base()),
                        FabricRecipeProvider.conditionsFromItem(StoneBlockSets.SMOOTH_DOLOMITE.base()))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.GREAT_ELVEN_GATE, 1)
                .pattern("BTB")
                .pattern("BTS")
                .pattern("BTB")
                .input('B', Items.CYAN_DYE)
                .input('T', OtherBlockSets.TREATED_WOOD.block())
                .input('S', ModResourceItems.EDHEL_STEEL_INGOT)
                .criterion(FabricRecipeProvider.hasItem(OtherBlockSets.TREATED_WOOD.block()),
                        FabricRecipeProvider.conditionsFromItem(OtherBlockSets.TREATED_WOOD.block()))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.GREAT_ORCISH_GATE, 1)
                .pattern("SSS")
                .pattern("SNS")
                .pattern("NNN")
                .input('N', ModBlocks.BURZUM_STEEL_BLOCK)
                .input('S', ModResourceItems.BURZUM_STEEL_INGOT)
                .criterion(FabricRecipeProvider.hasItem(ModResourceItems.BURZUM_STEEL_INGOT),
                        FabricRecipeProvider.conditionsFromItem(ModResourceItems.BURZUM_STEEL_INGOT))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ModBlocks.TURF, 4)
                .pattern("MM")
                .pattern("MD")
                .input('M', Items.MOSS_BLOCK)
                .input('D', Items.DIRT)
                .criterion(FabricRecipeProvider.hasItem(Items.MOSS_BLOCK),
                        FabricRecipeProvider.conditionsFromItem(Items.MOSS_BLOCK))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ModBlocks.GRASSY_DIRT, 4)
                .pattern("DM")
                .pattern("MD")
                .input('M', Items.MOSS_BLOCK)
                .input('D', Items.DIRT)
                .criterion(FabricRecipeProvider.hasItem(Items.MOSS_BLOCK),
                        FabricRecipeProvider.conditionsFromItem(Items.MOSS_BLOCK))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ModBlocks.COBBLY_DIRT, 4)
                .pattern("DC")
                .pattern("CD")
                .input('D', Items.DIRT)
                .input('C', TagKey.of(RegistryKeys.ITEM, Identifier.of("stone_crafting_materials")))
                .criterion(FabricRecipeProvider.hasItem(Items.DIRT),
                        FabricRecipeProvider.conditionsFromItem(Items.DIRT))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ModBlocks.COBBLY_ASHEN_DIRT, 4)
                .pattern("DC")
                .pattern("CD")
                .input('D', ModBlocks.ASHEN_DIRT)
                .input('C', StoneBlockSets.ASHEN_COBBLESTONE.base())
                .criterion(FabricRecipeProvider.hasItem(Items.DIRT),
                        FabricRecipeProvider.conditionsFromItem(Items.DIRT))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ModBlocks.DIRTY_ROOTS, 2)
                .pattern(" R ")
                .pattern("RDR")
                .pattern(" R ")
                .input('D', Items.ROOTED_DIRT)
                .input('R', Items.HANGING_ROOTS)
                .criterion(FabricRecipeProvider.hasItem(Items.ROOTED_DIRT),
                        FabricRecipeProvider.conditionsFromItem(Items.ROOTED_DIRT))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.WATERING_CAN, 1)
                .pattern(" N ")
                .pattern("NII")
                .pattern(" II")
                .input('N', ModResourceItems.TIN_NUGGET)
                .input('I', ModResourceItems.TIN_INGOT)
                .criterion(FabricRecipeProvider.hasItem(ModResourceItems.TIN_INGOT),
                        FabricRecipeProvider.conditionsFromItem(ModResourceItems.TIN_INGOT))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.WOODEN_BUCKET, 1)
                .pattern(" R ")
                .pattern("P P")
                .pattern(" P ")
                .input('R', ModDecorativeBlocks.ROPE)
                .input('P', TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                .criterion(FabricRecipeProvider.hasItem(ModDecorativeBlocks.ROPE),
                        FabricRecipeProvider.conditionsFromItem(ModDecorativeBlocks.ROPE))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.TREATED_STEEL_ROD, 1)
                .pattern("S")
                .pattern("S")
                .pattern("S")
                .input('S', TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "steel_ingots")))
                .criterion(FabricRecipeProvider.hasItem(ModResourceItems.STEEL_INGOT),
                        FabricRecipeProvider.conditionsFromItem(ModResourceItems.STEEL_INGOT))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, Items.CHAIN, 4)
                .pattern("N")
                .pattern("I")
                .pattern("N")
                .input('N', TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "steel_ingots")))
                .input('I', TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "steel_nuggets")))
                .criterion(FabricRecipeProvider.hasItem(ModResourceItems.STEEL_INGOT),
                        FabricRecipeProvider.conditionsFromItem(ModResourceItems.STEEL_INGOT))
                .offerTo(exporter, Identifier.of(MiddleEarth.MOD_ID,Registries.ITEM.getId(Items.CHAIN).getPath() + "_alt"));

        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.BRONZE_CHAIN, 4)
                .pattern("N")
                .pattern("I")
                .pattern("N")
                .input('N', ModResourceItems.BRONZE_NUGGET)
                .input('I', ModResourceItems.BRONZE_INGOT)
                .criterion(FabricRecipeProvider.hasItem(ModResourceItems.BRONZE_INGOT),
                        FabricRecipeProvider.conditionsFromItem(ModResourceItems.STEEL_INGOT))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.BRONZE_BROAD_CHAIN, 8)
                .pattern("NN")
                .pattern("II")
                .pattern("NN")
                .input('N', ModResourceItems.BRONZE_NUGGET)
                .input('I', ModResourceItems.BRONZE_INGOT)
                .criterion(FabricRecipeProvider.hasItem(ModResourceItems.BRONZE_INGOT),
                        FabricRecipeProvider.conditionsFromItem(ModResourceItems.STEEL_INGOT))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.SPIKY_CHAIN, 4)
                .pattern(" N ")
                .pattern("NIN")
                .pattern(" N ")
                .input('I', TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "steel_ingots")))
                .input('N', TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "steel_nuggets")))
                .criterion(FabricRecipeProvider.hasItem(ModResourceItems.STEEL_INGOT),
                        FabricRecipeProvider.conditionsFromItem(ModResourceItems.STEEL_INGOT))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ModResourceItems.DWARVEN_KEY, 1)
                .pattern("IN")
                .input('N', ModResourceItems.KHAZAD_STEEL_NUGGET)
                .input('I', ModResourceItems.KHAZAD_STEEL_INGOT)
                .criterion(FabricRecipeProvider.hasItem(ModResourceItems.KHAZAD_STEEL_INGOT),
                        FabricRecipeProvider.conditionsFromItem(ModResourceItems.KHAZAD_STEEL_INGOT))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.CHIMNEY, 2)
                .pattern(" B ")
                .pattern(" B ")
                .pattern("PPP")
                .input('B', Items.BRICKS)
                .input('P', StoneBlockSets.POLISHED_DOLOMITE.base())
                .criterion(FabricRecipeProvider.hasItem(Items.BRICKS),
                        FabricRecipeProvider.conditionsFromItem(Items.BRICKS))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.BIG_BRAZIER, 2)
                .pattern("B B")
                .pattern("BCB")
                .pattern("SSS")
                .input('B', ModBlocks.TREATED_STEEL_BARS)
                .input('C', Items.CAMPFIRE)
                .input('S', TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "steel_ingots")))
                .criterion(FabricRecipeProvider.hasItem(Items.CAMPFIRE),
                        FabricRecipeProvider.conditionsFromItem(Items.CAMPFIRE))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.GILDED_BIG_BRAZIER, 2)
                .pattern("B B")
                .pattern("BCB")
                .pattern("SSS")
                .input('B', ModBlocks.GILDED_BARS)
                .input('C', Items.CAMPFIRE)
                .input('S', Items.GOLD_INGOT)
                .criterion(FabricRecipeProvider.hasItem(Items.CAMPFIRE),
                        FabricRecipeProvider.conditionsFromItem(Items.CAMPFIRE))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.SMALL_BRAZIER, 2)
                .pattern("BCB")
                .pattern("SSS")
                .input('B', ModBlocks.TREATED_STEEL_BARS)
                .input('C', Items.CAMPFIRE)
                .input('S', TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "steel_ingots")))
                .criterion(FabricRecipeProvider.hasItem(Items.CAMPFIRE),
                        FabricRecipeProvider.conditionsFromItem(Items.CAMPFIRE))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.GILDED_SMALL_BRAZIER, 2)
                .pattern("BCB")
                .pattern("SSS")
                .input('B', ModBlocks.GILDED_BARS)
                .input('C', Items.CAMPFIRE)
                .input('S', Items.GOLD_INGOT)
                .criterion(FabricRecipeProvider.hasItem(Items.CAMPFIRE),
                        FabricRecipeProvider.conditionsFromItem(Items.CAMPFIRE))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.FIRE_BOWL, 2)
                .pattern("SCS")
                .pattern("SSS")
                .input('C', Items.CAMPFIRE)
                .input('S', TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "steel_ingots")))
                .criterion(FabricRecipeProvider.hasItem(Items.CAMPFIRE),
                        FabricRecipeProvider.conditionsFromItem(Items.CAMPFIRE))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.BONFIRE, 1)
                .pattern(" L ")
                .pattern("LCL")
                .input('C', Items.CAMPFIRE)
                .input('L', TagKey.of(RegistryKeys.ITEM, Identifier.of("logs")))
                .criterion(FabricRecipeProvider.hasItem(Items.CAMPFIRE),
                        FabricRecipeProvider.conditionsFromItem(Items.CAMPFIRE))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.GROUND_BOOK, 1)
                .pattern("BSR")
                .input('B', Items.BOOK)
                .input('S', Items.STRING)
                .input('R', Items.RED_DYE)
                .criterion(FabricRecipeProvider.hasItem(Items.BOOK),
                        FabricRecipeProvider.conditionsFromItem(Items.BOOK))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.DWARVEN_GROUND_BOOK, 1)
                .pattern("BG")
                .input('B', Items.BOOK)
                .input('G', Items.GOLD_NUGGET)
                .criterion(FabricRecipeProvider.hasItem(Items.BOOK),
                        FabricRecipeProvider.conditionsFromItem(Items.BOOK))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.SMALL_CRATE, 1)
                .pattern("SSS")
                .pattern("PPP")
                .input('S', TagKey.of(RegistryKeys.ITEM, Identifier.of("wooden_slabs")))
                .input('P', TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                .criterion(FabricRecipeProvider.hasItem(Items.OAK_PLANKS),
                        FabricRecipeProvider.conditionsFromItem(Items.OAK_PLANKS))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.THIN_BARREL, 1)
                .pattern("VSV")
                .pattern("V V")
                .pattern("VSV")
                .input('S', TagKey.of(RegistryKeys.ITEM, Identifier.of("wooden_slabs")))
                .input('V', TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "wooden_vertical_slabs")))
                .criterion(FabricRecipeProvider.hasItem(Items.OAK_SLAB),
                        FabricRecipeProvider.conditionsFromItem(Items.OAK_SLAB))
                .offerTo(exporter);

        createBannerPatternRecipe(exporter, ModResourceItems.PIPEWEED, ModResourceItems.PIPEWEED_BANNER_PATTERN);
        createBannerPatternRecipe(exporter, ModNatureBlocks.LEBETHRON_SAPLING.asItem(), ModResourceItems.GONDOR_BANNER_PATTERN);
        createBannerPatternRecipe(exporter, WoodBlockSets.MALLORN.sapling().asItem(), ModResourceItems.LOTHLORIEN_BANNER_PATTERN);
        createBannerPatternRecipe(exporter, Items.MAGMA_BLOCK, ModResourceItems.MORDOR_BANNER_PATTERN);
        createBannerPatternRecipe(exporter, Items.HAY_BLOCK, ModResourceItems.ROHAN_BANNER_PATTERN);
        createBannerPatternRecipe(exporter, Items.BONE, ModResourceItems.MISTY_MOUNTAINS_ORCS_BANNER_PATTERN);
        createBannerPatternRecipe(exporter, Items.WHITE_DYE, ModResourceItems.ISENGARD_BANNER_PATTERN);
        createBannerPatternRecipe(exporter, ModToolItems.DWARVEN_SMITHING_HAMMER, ModResourceItems.ANVIL_BANNER_PATTERN);
        createBannerPatternRecipe(exporter, ModResourceItems.BRONZE_INGOT, ModResourceItems.BELL_BANNER_PATTERN);
        createBannerPatternRecipe(exporter, Items.GOLD_NUGGET, ModResourceItems.DWARF_CROWN_BANNER_PATTERN);
        createBannerPatternRecipe(exporter, Items.BOW, ModResourceItems.BOW_BANNER_PATTERN);

        createBrickRecipe(exporter, ModBlocks.POINTED_DOLOMITE.asItem(), StoneBlockSets.DOLOMITE.base(), 1);
        createBrickRecipe(exporter, ModBlocks.POINTED_GALONN.asItem(), StoneBlockSets.GALONN.base(), 1);
        createBrickRecipe(exporter, ModBlocks.POINTED_IZHERABAN.asItem(), StoneBlockSets.IZHERABAN.base(), 1);
        createBrickRecipe(exporter, ModBlocks.POINTED_LIMESTONE.asItem(), StoneBlockSets.LIMESTONE.base(), 1);
        //endregion

        ComplexRecipeJsonBuilder.create(CustomItemDecorationRecipe::new).offerTo(exporter, "custom_shield_decoration");
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

    private void createMeltRecipe(RecipeExporter exporter, Item input, String output, int amount) {
        AlloyRecipeJsonBuilder.createAlloyRecipe(RecipeCategory.MISC, output,amount)
                .input(input)
                .criterion(FabricRecipeProvider.hasItem(input),
                        FabricRecipeProvider.conditionsFromItem(input))
                .offerTo(exporter, Identifier.of(MiddleEarth.MOD_ID, output + "_from_melting_" + Registries.ITEM.getId(input).getPath()));
    }

    private void createMeltRecipeTag(RecipeExporter exporter, TagKey input, String output, int amount) {
        AlloyRecipeJsonBuilder.createAlloyRecipe(RecipeCategory.MISC, output,amount)
                .input(input)
                .criterion(FabricRecipeProvider.hasItem(ModDecorativeItems.FORGE),
                        FabricRecipeProvider.conditionsFromItem(ModDecorativeItems.FORGE))
                .offerTo(exporter, Identifier.of(MiddleEarth.MOD_ID, output + "_from_melting_" + input.id().getPath()));
    }

    private void createAnvilShapingRecipeTag(RecipeExporter exporter, TagKey input, Item output, int amount) {
        AnvilShapingRecipeJsonBuilder.createAnvilShapingRecipe(RecipeCategory.MISC, output, amount)
                .input(input)
                .criterion(FabricRecipeProvider.hasItem(Items.COPPER_INGOT),
                        FabricRecipeProvider.conditionsFromItem(Items.COPPER_INGOT))
                .offerTo(exporter);
    }

    private void createAnvilShapingRecipeItem(RecipeExporter exporter, Item input, Item output, int amount) {
        AnvilShapingRecipeJsonBuilder.createAnvilShapingRecipe(RecipeCategory.MISC, output, amount)
                .input(input)
                .criterion(FabricRecipeProvider.hasItem(Items.COPPER_INGOT),
                        FabricRecipeProvider.conditionsFromItem(Items.COPPER_INGOT))
                .offerTo(exporter);
    }

    private void createAnvilRecipe(RecipeExporter exporter, Item inputBlock, Item inputIngot, Item output) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, output, 1)
                .pattern("IBI")
                .pattern(" I ")
                .pattern("LLL")
                .input('I', inputIngot)
                .input('B', inputBlock)
                .input('L', TagKey.of(RegistryKeys.ITEM, Identifier.of("logs")))
                .criterion(FabricRecipeProvider.hasItem(inputIngot),
                        FabricRecipeProvider.conditionsFromItem(inputIngot))
                .offerTo(exporter);
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

    private void createCenterSurroundRecipe(RecipeExporter exporter, Item surroundInput, Item centerItem, Item output, int count) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, output, count)
                .pattern("BBB")
                .pattern("BDB")
                .pattern("BBB")
                .input('B', surroundInput)
                .input('D', centerItem)
                .criterion(FabricRecipeProvider.hasItem(surroundInput),
                        FabricRecipeProvider.conditionsFromItem(surroundInput))
                .offerTo(exporter, Identifier.of(MiddleEarth.MOD_ID,Registries.ITEM.getId(output).getPath() + "_alt"));
    }

    private void createDyeableItemRecipe(RecipeExporter exporter, Block blockInput, Item dyeItem, Block output) {
        ShapelessRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, output, 1)
                .input(blockInput)
                .input(dyeItem)
                .criterion(FabricRecipeProvider.hasItem(blockInput),
                        FabricRecipeProvider.conditionsFromItem(blockInput))
                .offerTo(exporter);
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

    private void createWoodStoolRecipe(RecipeExporter exporter, Item inputPlanks, Block output) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, output, 3)
                .pattern("PP")
                .pattern("SS")
                .input('P', inputPlanks)
                .input('S', Items.STICK)
                .criterion(FabricRecipeProvider.hasItem(inputPlanks),
                        FabricRecipeProvider.conditionsFromItem(inputPlanks))
                .offerTo(exporter);
    }

    private void createWoodBenchRecipe(RecipeExporter exporter, Item inputPlanks,  Block output) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, output, 3)
                .pattern("PPP")
                .pattern("S S")
                .input('P', inputPlanks)
                .input('S', Items.STICK)
                .criterion(FabricRecipeProvider.hasItem(inputPlanks),
                        FabricRecipeProvider.conditionsFromItem(inputPlanks))
                .offerTo(exporter);
    }

    private void createWoodTableRecipe(RecipeExporter exporter, Item inputPlanks, Block output) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, output, 3)
                .pattern("PPP")
                .pattern("S S")
                .pattern("S S")
                .input('P', inputPlanks)
                .input('S', Items.STICK)
                .criterion(FabricRecipeProvider.hasItem(inputPlanks),
                        FabricRecipeProvider.conditionsFromItem(inputPlanks))
                .offerTo(exporter);
    }

    private void createWoodChairRecipe(RecipeExporter exporter, Item inputPlanks, Block output) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, output, 3)
                .pattern("P  ")
                .pattern("PPP")
                .pattern("S S")
                .input('P', inputPlanks)
                .input('S', Items.STICK)
                .criterion(FabricRecipeProvider.hasItem(inputPlanks),
                        FabricRecipeProvider.conditionsFromItem(inputPlanks))
                .offerTo(exporter);
    }

    private void createWoodLadderRecipe(RecipeExporter exporter, Item inputPlanks, Block output) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, output, 3)
                .pattern("P P")
                .pattern("PSP")
                .pattern("P P")
                .input('P', inputPlanks)
                .input('S', Items.STICK)
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
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, output, 1)
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

    private void createGildedBlockRecipe(RecipeExporter exporter, Block input, Block output) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, output, 1)
                .pattern(" N ")
                .pattern("NBN")
                .pattern(" N ")
                .input('B', input)
                .input('N', Items.GOLD_NUGGET)
                .criterion(FabricRecipeProvider.hasItem(input),
                        FabricRecipeProvider.conditionsFromItem(input))
                .offerTo(exporter);
    }

    private void createBrickworkBlockRecipe(RecipeExporter exporter, Block input, Block inputBinder, Block output) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, output, 2)
                .pattern("SB")
                .input('S', inputBinder)
                .input('B', input)
                .criterion(FabricRecipeProvider.hasItem(input),
                        FabricRecipeProvider.conditionsFromItem(input))
                .offerTo(exporter);
    }

    private void createWattleRecipes(RecipeExporter exporter, Item input, Block outputBase,
                                        Block outputCross, Block outputRight, Block outputLeft, Block outputPillar, Block outputDiamond) {
        createBaseWattleRecipe(exporter, input, outputBase);
        createCrossWattleRecipe(exporter, input, outputCross);
        createRightWattleRecipe(exporter, input, outputRight);
        createLeftWattleRecipe(exporter, input, outputLeft);
        createPillarWattleRecipe(exporter, input, outputPillar);
        createDiamondWattleRecipe(exporter, input, outputDiamond);
    }

    private void createBaseWattleRecipe(RecipeExporter exporter, Item input, Block output) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, output, 1)
                .pattern(" S ")
                .pattern("SDS")
                .pattern(" S ")
                .input('S', Items.STICK)
                .input('D', input)
                .criterion(FabricRecipeProvider.hasItem(input),
                        FabricRecipeProvider.conditionsFromItem(input))
                .offerTo(exporter);
    }

    private void createCrossWattleRecipe(RecipeExporter exporter, Item input, Block output) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, output, 4)
                .pattern("SDS")
                .pattern("DSD")
                .pattern("SDS")
                .input('S', Items.STICK)
                .input('D', input)
                .criterion(FabricRecipeProvider.hasItem(input),
                        FabricRecipeProvider.conditionsFromItem(input))
                .offerTo(exporter);
    }

    private void createRightWattleRecipe(RecipeExporter exporter, Item input, Block output) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, output, 6)
                .pattern("DDS")
                .pattern("DSD")
                .pattern("SDD")
                .input('S', Items.STICK)
                .input('D', input)
                .criterion(FabricRecipeProvider.hasItem(input),
                        FabricRecipeProvider.conditionsFromItem(input))
                .offerTo(exporter);
    }

    private void createLeftWattleRecipe(RecipeExporter exporter, Item input, Block output) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, output, 6)
                .pattern("SDD")
                .pattern("DSD")
                .pattern("DDS")
                .input('S', Items.STICK)
                .input('D', input)
                .criterion(FabricRecipeProvider.hasItem(input),
                        FabricRecipeProvider.conditionsFromItem(input))
                .offerTo(exporter);
    }

    private void createPillarWattleRecipe(RecipeExporter exporter, Item input, Block output) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, output, 6)
                .pattern("DSD")
                .pattern("DSD")
                .pattern("DSD")
                .input('S', Items.STICK)
                .input('D', input)
                .criterion(FabricRecipeProvider.hasItem(input),
                        FabricRecipeProvider.conditionsFromItem(input))
                .offerTo(exporter);
    }

    private void createDiamondWattleRecipe(RecipeExporter exporter, Item input, Block output) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, output, 5)
                .pattern("DSD")
                .pattern("SDS")
                .pattern("DSD")
                .input('S', Items.STICK)
                .input('D', input)
                .criterion(FabricRecipeProvider.hasItem(input),
                        FabricRecipeProvider.conditionsFromItem(input))
                .offerTo(exporter);
    }

    private void createStatueRecipe(RecipeExporter exporter, Block polishedInput, Block stoneInput, Block wallInput, Block output) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, output, 1)
                .pattern("WSW")
                .pattern("WSW")
                .pattern("WPW")
                .input('W', wallInput)
                .input('S', stoneInput)
                .input('P', polishedInput)
                .criterion(FabricRecipeProvider.hasItem(polishedInput),
                        FabricRecipeProvider.conditionsFromItem(polishedInput))
                .offerTo(exporter);
    }

    private void createCushionRecipe(RecipeExporter exporter, Block woolBlock, Block output) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, output, 1)
                .pattern("WW")
                .pattern("PP")
                .input('W', woolBlock)
                .input('P', TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                .criterion(FabricRecipeProvider.hasItem(woolBlock),
                        FabricRecipeProvider.conditionsFromItem(woolBlock))
                .offerTo(exporter);
    }

    private void createBannerPatternRecipe(RecipeExporter exporter, Item input, Item output) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, output, 1)
                .pattern("PF")
                .pattern("BI")
                .input('I', input)
                .input('B', Items.BLACK_DYE)
                .input('F', Items.FEATHER)
                .input('P', Items.PAPER)
                .criterion(FabricRecipeProvider.hasItem(Items.PAPER),
                        FabricRecipeProvider.conditionsFromItem(Items.PAPER))
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

    private void createSwordRecipe(RecipeExporter exporter, Item inputRod, Item inputMaterial, Item output) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, output, 1)
                .pattern(" M ")
                .pattern(" M ")
                .pattern(" R ")
                .input('M', inputMaterial)
                .input('R', inputRod)
                .criterion(FabricRecipeProvider.hasItem(inputMaterial),
                        FabricRecipeProvider.conditionsFromItem(inputMaterial))
                .offerTo(exporter);
    }

    private void createDaggerRecipe(RecipeExporter exporter, Item inputRod, Item inputMaterial, Item output) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, output, 1)
                .pattern(" M ")
                .pattern(" R ")
                .input('M', inputMaterial)
                .input('R', inputRod)
                .criterion(FabricRecipeProvider.hasItem(inputMaterial),
                        FabricRecipeProvider.conditionsFromItem(inputMaterial))
                .offerTo(exporter);
    }

    private void createDaggerRecipeTag(RecipeExporter exporter, Item inputRod, TagKey inputMaterial, Item output) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, output, 1)
                .pattern(" M ")
                .pattern(" R ")
                .input('M', inputMaterial)
                .input('R', inputRod)
                .criterion(FabricRecipeProvider.hasItem(Items.OAK_PLANKS),
                        FabricRecipeProvider.conditionsFromItem(Items.OAK_PLANKS))
                .offerTo(exporter);
    }

    private void createSpearRecipe(RecipeExporter exporter, Item inputRod, Item inputMaterial, Item output) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, output, 1)
                .pattern("  M")
                .pattern(" R ")
                .pattern("R  ")
                .input('M', inputMaterial)
                .input('R', inputRod)
                .criterion(FabricRecipeProvider.hasItem(inputMaterial),
                        FabricRecipeProvider.conditionsFromItem(inputMaterial))
                .offerTo(exporter);
    }

    private void createSpearRecipeTag(RecipeExporter exporter, Item inputRod, TagKey inputMaterial, Item output) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, output, 1)
                .pattern("  M")
                .pattern(" R ")
                .pattern("R  ")
                .input('M', inputMaterial)
                .input('R', inputRod)
                .criterion(FabricRecipeProvider.hasItem(Items.OAK_PLANKS),
                        FabricRecipeProvider.conditionsFromItem(Items.OAK_PLANKS))
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

    private void createCookedFoodRecipes(RecipeExporter exporter, Item rawFood, Item cookedFood) {
        net.minecraft.data.server.recipe.RecipeProvider.offerFoodCookingRecipe(exporter, "smelting", RecipeSerializer.SMELTING, SmeltingRecipe::new, 200, rawFood, cookedFood, 0.35f);
        net.minecraft.data.server.recipe.RecipeProvider.offerFoodCookingRecipe(exporter, "smoking", RecipeSerializer.SMOKING, SmokingRecipe::new, 100, rawFood, cookedFood, 0.35f);
        net.minecraft.data.server.recipe.RecipeProvider.offerFoodCookingRecipe(exporter, "campfire_cooking", RecipeSerializer.CAMPFIRE_COOKING, CampfireCookingRecipe::new, 600, rawFood, cookedFood, 0.35f);
    }

    private void createMetalsRecipe(RecipeExporter exporter, Item nugget, Item ingot, Block block) {
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

        createFilledRecipe(exporter, ingot, block, 1);

        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, ingot, 9)
                .input(block)
                .criterion(FabricRecipeProvider.hasItem(block),
                        FabricRecipeProvider.conditionsFromItem(block))
                .offerTo(exporter, Identifier.of(MiddleEarth.MOD_ID, Registries.ITEM.getId(ingot).getPath() + "_from_block"));
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