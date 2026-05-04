package net.sevenstars.middleearth.datageneration.providers.recipes;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.data.recipe.*;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.recipe.*;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.block.registration.*;
import net.sevenstars.middleearth.block.utils.BlockRecordTypes;
import net.sevenstars.middleearth.block.utils.StoneBlockTypes;
import net.sevenstars.middleearth.block.utils.setBuilders.GenericBlockSetBuilder;
import net.sevenstars.middleearth.block.utils.setBuilders.StoneBlockSetBuilder;
import net.sevenstars.middleearth.block.utils.setBuilders.WoodBlockSetBuilder;
import net.sevenstars.middleearth.datageneration.content.models.*;
import net.sevenstars.middleearth.datageneration.custom.AlloyRecipeJsonBuilder;
import net.sevenstars.middleearth.datageneration.custom.AnvilShapingRecipeJsonBuilder;
import net.sevenstars.middleearth.item.*;
import net.sevenstars.middleearth.recipe.*;
import net.sevenstars.middleearth.utils.ItemTagsME;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class RecipeProvider extends FabricRecipeProvider {

    private static final int INGOT_LIQUID_VALUE = 144;

    public RecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected RecipeGenerator getRecipeGenerator(RegistryWrapper.WrapperLookup wrapperLookup, RecipeExporter recipeExporter) {
        return new RecipeGenerator(wrapperLookup, recipeExporter) {

            final RegistryWrapper.Impl<Item> itemLookup = registries.getOrThrow(RegistryKeys.ITEM);

            @Override
            public void generate() {
                //region STONE RECIPES
                for (StoneBlockSetBuilder record : StoneBlockSets.stoneSetsList) {
                    if(record.hasMossy) {
                        if(record.mossyCobblestoneBlocks != null && record.cobblestoneBlocks != null) {
                            createMossyRecipe(exporter, record.mossyCobblestoneBlocks.base(), record.cobblestoneBlocks.base());
                        }
                        if(record.mossyBrickBlocks != null && record.brickBlocks != null) {
                            createMossyRecipe(exporter, record.mossyBrickBlocks.base(), record.brickBlocks.base());
                        }
                        if(record.mossyPillarBlocks != null && record.pillarBlocks != null) {
                            createMossyRecipe(exporter, record.mossyPillarBlocks.base(), record.pillarBlocks.base());
                        }
                        if(record.mossyPolishedBlocks != null && record.polishedBlocks != null) {
                            createMossyRecipe(exporter, record.mossyPolishedBlocks.base(), record.polishedBlocks.base());
                        }
                        if(record.mossyTileBlocks != null && record.tileBlocks != null) {
                            createMossyRecipe(exporter, record.mossyTileBlocks.base(), record.tileBlocks.base());
                        }
                        if(record.mossySmoothBlocks != null && record.smoothBlocks != null) {
                            createMossyRecipe(exporter, record.mossySmoothBlocks.base(), record.smoothBlocks.base());
                        }
                    }
                    if(record.hasCracked) {
                        if(record.crackedBrickBlocks != null && record.brickBlocks != null) {
                            offerSmelting(List.of(record.brickBlocks.base()), RecipeCategory.BUILDING_BLOCKS,
                                    record.crackedBrickBlocks.base(), 0.1f, 200, "cracked_bricks");
                        }
                        if(record.crackedPillarBlocks != null && record.pillarBlocks != null) {
                            offerSmelting(List.of(record.pillarBlocks.base()), RecipeCategory.BUILDING_BLOCKS,
                                    record.crackedPillarBlocks.base(), 0.1f, 200, "cracked_bricks");
                        }
                        if(record.crackedPolishedBlocks != null && record.polishedBlocks != null) {
                            offerSmelting(List.of(record.polishedBlocks.base()), RecipeCategory.BUILDING_BLOCKS,
                                    record.crackedPolishedBlocks.base(), 0.1f, 200, "cracked_bricks");
                        }
                        if(record.crackedTileBlocks != null && record.tileBlocks != null) {
                            offerSmelting(List.of(record.tileBlocks.base()), RecipeCategory.BUILDING_BLOCKS,
                                    record.crackedTileBlocks.base(), 0.1f, 200, "cracked_bricks");
                        }
                        if(record.crackedSmoothBlocks != null && record.smoothBlocks != null) {
                            offerSmelting(List.of(record.smoothBlocks.base()), RecipeCategory.BUILDING_BLOCKS,
                                    record.crackedSmoothBlocks.base(), 0.1f, 200, "cracked_bricks");
                        }
                    }

                    if(record.cobblestoneBlocks != null && record.baseBlocks != null) {
                        offerStonecuttingRecipe(RecipeCategory.BUILDING_BLOCKS, record.cobblestoneBlocks.base(), record.baseBlocks.base(), 1);
                    }

                    if (record.baseBlocks != null) {
                        if(record.brickBlocks != null) {
                            createBrickRecipe(exporter, record.baseBlocks.base().asItem(), record.brickBlocks.base(), 4);
                            offerStonecuttingRecipe(RecipeCategory.BUILDING_BLOCKS, record.brickBlocks.base(), record.baseBlocks.base(), 1);
                        }
                        if(record.pillarBlocks != null) {
                            createPillarRecipe(exporter, record.baseBlocks.base(), record.pillarBlocks.base(), 3);
                            offerStonecuttingRecipe(RecipeCategory.BUILDING_BLOCKS, record.pillarBlocks.base(), record.baseBlocks.base(), 1);
                        }
                        if(record.polishedBlocks != null) {
                            createBrickRecipe(exporter, record.baseBlocks.base().asItem(), record.polishedBlocks.base(), 4);
                            offerStonecuttingRecipe(RecipeCategory.BUILDING_BLOCKS, record.polishedBlocks.base(), record.baseBlocks.base(), 1);
                        }
                        if(record.chiseledBlocks != null) {
                            createChiseledRecipe(exporter, record.baseBlocks.slab(), record.chiseledBlocks.base(), 4);
                            offerStonecuttingRecipe(RecipeCategory.BUILDING_BLOCKS, record.chiseledBlocks.base(), record.baseBlocks.base(), 1);
                        }
                        if(record.smoothBlocks != null) {
                            createSmeltingRecipe(exporter, record.baseBlocks.base().asItem(), record.smoothBlocks.base().asItem());
                        }
                        createFilledRecipe(exporter, record.baseBlocks.base().asItem(), record.baseBlocks.trapdoor(), 3);
                        createPressurePlateRecipe(exporter, record.baseBlocks.base(), record.baseBlocks.pressurePlate());
                        offerStonecuttingRecipe(RecipeCategory.BUILDING_BLOCKS, record.baseBlocks.trapdoor(), record.baseBlocks.base());
                        offerStonecuttingRecipe(RecipeCategory.BUILDING_BLOCKS, record.baseBlocks.rocks(), record.baseBlocks.base(), 4);
                        createButtonRecipe(exporter, record.baseBlocks.base(), record.baseBlocks.button());

                        createStoneStoolRecipe(exporter, record.baseBlocks.base().asItem(), record.baseBlocks.stool());
                        createStoneTableRecipe(exporter, record.baseBlocks.base().asItem(), record.baseBlocks.table());
                        createStoneChairRecipe(exporter, record.baseBlocks.base().asItem(), record.baseBlocks.chair());
                    }
                    if(record.brickBlocks != null) {
                        if(record.tileBlocks != null) {
                            createBrickRecipe(exporter, record.brickBlocks.base().asItem(), record.tileBlocks.base(), 4);
                        }
                    }

                    createStoneSetRecipes(record.baseBlocks);
                    createStoneSetRecipes(record.cobblestoneBlocks);
                    createStoneSetRecipes(record.mossyCobblestoneBlocks);
                    createStoneSetRecipes(record.brickBlocks);
                    createStoneSetRecipes(record.tileBlocks);
                    createStoneSetRecipes(record.mossyTileBlocks);
                    createStoneSetRecipes(record.crackedTileBlocks);
                    createStoneSetRecipes(record.smoothBlocks);
                    createStoneSetRecipes(record.mossySmoothBlocks);
                    createStoneSetRecipes(record.crackedSmoothBlocks);
                    createStoneSetRecipes(record.polishedBlocks);
                    createStoneSetRecipes(record.mossyPolishedBlocks);
                    createStoneSetRecipes(record.crackedPolishedBlocks);
                    createStoneSetRecipes(record.brickworkBlocks);
                    createStoneSetRecipes(record.pillarBlocks);
                    createStoneSetRecipes(record.mossyPillarBlocks);
                    createStoneSetRecipes(record.crackedPillarBlocks);
                    createStoneSetRecipes(record.chiseledBlocks);
                    createStoneSetRecipes(record.chiseledBricksBlocks);
                    createStoneSetRecipes(record.chiseledPolishedBlocks);
                    createStoneSetRecipes(record.chiseledTilesBlocks);
                    createStoneSetRecipes(record.chiseledSmoothBlocks);
                }
                //endregion

                //region WOOD RECIPES
                for (WoodBlockSetBuilder record : WoodBlockSets.woodSetsList) {
                    if(record.logBlocks != null) {
                        createBrickRecipe(exporter, record.logBlocks.log().asItem(), record.logBlocks.wood(), 3);
                        offerWallRecipe(RecipeCategory.BUILDING_BLOCKS, record.logBlocks.wall(), record.logBlocks.wood());
                        createFenceRecipe(exporter, record.logBlocks.wood().asItem(), record.logBlocks.fence());
                        offerSlabRecipe(RecipeCategory.BUILDING_BLOCKS, record.logBlocks.slab(), record.logBlocks.wood());
                        createSlabsFromVerticalRecipe(exporter, record.logBlocks.slab(), record.logBlocks.slab());
                        createStairsRecipe(exporter, record.logBlocks.wood(), record.logBlocks.stairs());

                        ShapelessRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, record.planksBlocks.base(), 4)
                                .input(record.logBlocks.log())
                                .criterion(hasItem(record.logBlocks.log()),
                                        conditionsFromItem(record.planksBlocks.base()))
                                .offerTo(exporter);

                        ShapelessRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, record.planksBlocks.base(), 4)
                                .input(record.logBlocks.wood())
                                .criterion(hasItem(record.logBlocks.wood()),
                                        conditionsFromItem(record.planksBlocks.base()))
                                .offerTo(exporter, String.valueOf(Identifier.of(MiddleEarth.MOD_ID, Registries.BLOCK.getId(record.planksBlocks.base()).getPath() + "_from_wood")));
                    } else if(record.mushroomStemBlocks != null) {
                        ShapelessRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, record.planksBlocks.base(), 4)
                                .input(record.mushroomStemBlocks.stem())
                                .criterion(hasItem(record.mushroomStemBlocks.stem()),
                                        conditionsFromItem(record.planksBlocks.base()))
                                .offerTo(exporter);

                        offerWallRecipe(RecipeCategory.BUILDING_BLOCKS, record.mushroomStemBlocks.wall(), record.mushroomStemBlocks.stem());
                        createFenceRecipe(exporter, record.mushroomStemBlocks.stem().asItem(), record.mushroomStemBlocks.fence());
                        offerSlabRecipe(RecipeCategory.BUILDING_BLOCKS, record.mushroomStemBlocks.slab(), record.mushroomStemBlocks.stem());
                        createVerticalSlabsRecipe(exporter, record.mushroomStemBlocks.slab(), record.mushroomStemBlocks.verticalSlab());
                        createSlabsFromVerticalRecipe(exporter, record.mushroomStemBlocks.verticalSlab(), record.mushroomStemBlocks.slab());
                        createStairsRecipe(exporter, record.mushroomStemBlocks.stem(), record.mushroomStemBlocks.stairs());
                    }

                    if(record.strippedLogBlocks != null) {
                        createBrickRecipe(exporter, record.strippedLogBlocks.log().asItem(), record.strippedLogBlocks.wood(),  3);
                        offerWallRecipe(RecipeCategory.BUILDING_BLOCKS, record.strippedLogBlocks.wall(), record.strippedLogBlocks.wood());
                        createFenceRecipe(exporter, record.strippedLogBlocks.wood().asItem(), record.strippedLogBlocks.fence());
                        offerSlabRecipe(RecipeCategory.BUILDING_BLOCKS, record.strippedLogBlocks.slab(), record.strippedLogBlocks.wood());
                        if(!record.vanilla)createVerticalSlabsRecipe(exporter, record.strippedLogBlocks.slab(), record.strippedLogBlocks.verticalSlab());
                        createSlabsFromVerticalRecipe(exporter, record.strippedLogBlocks.verticalSlab(), record.strippedLogBlocks.slab());
                        createStairsRecipe(exporter, record.strippedLogBlocks.wood(), record.strippedLogBlocks.stairs());

                        ShapelessRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, record.planksBlocks.base(), 4)
                                .input(record.strippedLogBlocks.log())
                                .criterion(hasItem(record.strippedLogBlocks.log()),
                                        conditionsFromItem(record.planksBlocks.base()))
                                .offerTo(exporter, String.valueOf(Identifier.of(MiddleEarth.MOD_ID, Registries.BLOCK.getId(record.planksBlocks.base()).getPath() + "_from_stripped_log")));

                        ShapelessRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, record.planksBlocks.base(), 4)
                                .input(record.strippedLogBlocks.wood())
                                .criterion(hasItem(record.strippedLogBlocks.wood()),
                                        conditionsFromItem(record.planksBlocks.base()))
                                .offerTo(exporter, String.valueOf(Identifier.of(MiddleEarth.MOD_ID, Registries.BLOCK.getId(record.planksBlocks.base()).getPath() + "_from_stripped_wood")));
                    }

                    createFenceRecipe(exporter, record.planksBlocks.base().asItem(), record.planksBlocks.fence());
                    offerSlabRecipe(RecipeCategory.BUILDING_BLOCKS, record.planksBlocks.slab(), record.planksBlocks.base());

                    if(record.shinglesBlocks != null) {
                        createShinglesRecipe(exporter, record.planksBlocks.base(), record.shinglesBlocks.base());
                        createRegularSetRecipes(record.shinglesBlocks);
                    }

                    if(!record.vanilla)createVerticalSlabsRecipe(exporter, record.planksBlocks.slab(), record.planksBlocks.verticalSlab());
                    createSlabsFromVerticalRecipe(exporter, record.planksBlocks.verticalSlab(), record.planksBlocks.slab());
                    if(!record.vanilla && record.logBlocks != null)createVerticalSlabsRecipe(exporter, record.logBlocks.slab(), record.logBlocks.verticalSlab());

                    createStairsRecipe(exporter, record.planksBlocks.base(), record.planksBlocks.stairs());

                    if(record.redstoneBlocks != null) {
                        createDoorRecipe(exporter, record.planksBlocks.base(), record.redstoneBlocks.door());
                        createTrapdoorRecipe(exporter, record.planksBlocks.base(), record.redstoneBlocks.trapdoor());
                        createButtonRecipe(exporter, record.planksBlocks.base(), record.redstoneBlocks.button());
                        createPressurePlateRecipe(exporter, record.planksBlocks.base(), record.redstoneBlocks.pressurePlate());
                    }

                    if(record.furnitureBlocks != null) {
                        createWoodStoolRecipe(exporter, record.planksBlocks.base().asItem(), record.furnitureBlocks.stool());
                        createWoodBenchRecipe(exporter, record.planksBlocks.base().asItem(), record.furnitureBlocks.bench());
                        createWoodTableRecipe(exporter, record.planksBlocks.base().asItem(), record.furnitureBlocks.table());
                        createWoodChairRecipe(exporter, record.planksBlocks.base().asItem(), record.furnitureBlocks.chair());
                        createWoodLadderRecipe(exporter, record.planksBlocks.base().asItem(), record.furnitureBlocks.ladder());
                    }

                    ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, record.planksBlocks.gate(), 1)
                            .pattern("sls")
                            .pattern("sls")
                            .input('l', record.planksBlocks.base())
                            .input('s', Items.STICK)
                            .criterion(hasItem(record.planksBlocks.base()),
                                    conditionsFromItem(record.planksBlocks.base()))
                            .criterion(hasItem(Items.STICK),
                                    conditionsFromItem(Items.STICK))
                            .offerTo(exporter);

                }
                //endregion

                //region ROOF RECIPES
                /*for (GenericBlockSets.RoofBlockSet record : GenericBlockSets.sets) {

                    if (record.origin() != null) {
                        ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, record.block(), 7)
                                .pattern(" l ")
                                .pattern("lll")
                                .pattern("lll")
                                .input('l', record.origin())
                                .criterion(hasItem(record.origin()),
                                        conditionsFromItem(record.origin()))
                                .offerTo(exporter);
                    }
                    offerSlabRecipe(RecipeCategory.BUILDING_BLOCKS, record.slab(), record.block());
                    createVerticalSlabsRecipe(exporter, record.slab(), record.verticalSlab());
                    createSlabsFromVerticalRecipe(exporter, record.verticalSlab(), record.slab());
                    createStairsRecipe(exporter, record.block(), record.stairs());
                    offerWallRecipe(RecipeCategory.BUILDING_BLOCKS, record.wall(), record.block());
                }

                for (GenericBlockSets.MiscBlockSet record : GenericBlockSets.specialWoodSets) {
                    if (record.origin() != null) {
                        ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, record.block(), 7)
                                .pattern(" l ")
                                .pattern("lll")
                                .pattern("lll")
                                .input('l', record.origin())
                                .criterion(hasItem(record.origin()),
                                        conditionsFromItem(record.origin()))
                                .offerTo(exporter);
                    }
                    offerSlabRecipe(RecipeCategory.BUILDING_BLOCKS, record.slab(), record.block());
                    createVerticalSlabsRecipe(exporter, record.slab(), record.verticalSlab());
                    createSlabsFromVerticalRecipe(exporter, record.verticalSlab(), record.slab());
                    createStairsRecipe(exporter, record.block(), record.stairs());
                    offerWallRecipe(RecipeCategory.BUILDING_BLOCKS, record.wall(), record.block());
                }*/
                //endregion

                //region BLOCK LIST SPECIFIC RECIPES
                for (SimpleVerticalSlabModel.VerticalSlab verticalSlab : SimpleVerticalSlabModel.vanillaVerticalSlabs) {
                    createVerticalSlabsRecipe(exporter, verticalSlab.slab(), verticalSlab.verticalSlab());
                    createSlabsFromVerticalRecipe(exporter, verticalSlab.verticalSlab(), verticalSlab.slab());
                }

                for (SimpleVerticalSlabModel.VerticalSlab verticalSlab : SimpleVerticalSlabModel.vanillaWoodVerticalSlabs) {
                    createVerticalSlabsRecipe(exporter, verticalSlab.slab(), verticalSlab.verticalSlab());
                    createSlabsFromVerticalRecipe(exporter, verticalSlab.verticalSlab(), verticalSlab.slab());
                }

                for (SimpleVerticalSlabModel.VerticalSlab verticalSlab : SimpleVerticalSlabModel.vanillaStrippedVerticalSlabs) {
                    createVerticalSlabsRecipe(exporter, verticalSlab.slab(), verticalSlab.verticalSlab());
                    createSlabsFromVerticalRecipe(exporter, verticalSlab.verticalSlab(), verticalSlab.slab());
                }

                for (SimplePillarModel.StonePillar pillar : SimplePillarModel.stonePillars) {
                    if (pillar.toString().contains("mossy_")) {
                        createMossyRecipe(exporter, pillar.origin(), pillar.base());
                    } else if (pillar.toString().contains("cracked_")) {
                        createSmeltingRecipe(exporter, pillar.origin().asItem(), pillar.base().asItem());
                    } else {
                        createPillarRecipe(exporter, pillar.origin(), pillar.base(), 3);
                        offerStonecuttingRecipe(RecipeCategory.BUILDING_BLOCKS, pillar.base().asItem(), pillar.origin());
                    }
                }

                for (SimpleBlockModel.ChiseledPolishedBlock block : SimpleBlockModel.chiseledPolishedBlocksTopBottom) {
                    createChiseledRecipe(exporter, block.origin(), block.base(), 1);
                }
                for (SimpleBlockModel.ChiseledBlock block : SimpleBlockModel.chiseledMainBlockTopBottom) {
                    createChiseledRecipe(exporter, block.origin(), block.base(), 1);
                }
                for (SimpleBlockModel.ChiseledBlock block : SimpleBlockModel.chiseledBlocksTopBottom) {
                    createChiseledRecipe(exporter, block.origin(), block.base(), 1);
                }
                for (SimpleBlockModel.ChiseledPolishedBlock block : SimpleBlockModel.chiseledPolishedBlocks) {
                    createCutPolishedRecipe(exporter, block.origin(), block.base(), 1);
                }
                for (SimpleBlockModel.ChiseledPolishedBlock block : SimpleBlockModel.chiseledTilesBlocksTopBottom) {
                    createCutPolishedRecipe(exporter, block.origin(), block.base(), 1);
                }
                for (SimpleBlockModel.ChiseledPolishedBlock block : SimpleBlockModel.chiseledSmoothBlocksTopBottom) {
                    createCutPolishedRecipe(exporter, block.origin(), block.base(), 1);
                }

                for (SimpleSlabModel.Slab slab : SimpleSlabModel.vanillaSlabs) {
                    offerSlabRecipe(RecipeCategory.BUILDING_BLOCKS, slab.slab(), slab.origin());
                }

                for (SimpleSlabModel.Slab slab : SimpleSlabModel.vanillaWoodSlabs) {
                    offerSlabRecipe(RecipeCategory.BUILDING_BLOCKS, slab.slab(), slab.origin());
                }

                for (SimpleSlabModel.Slab slab : SimpleSlabModel.vanillaStrippedSlab) {
                    offerSlabRecipe(RecipeCategory.BUILDING_BLOCKS, slab.slab(), slab.origin());
                }

                for (SimpleStairModel.Stair stair : SimpleStairModel.vanillaStairs) {
                    createStairsRecipe(exporter, stair.origin(), stair.stairs());
                }

                for (SimpleStairModel.Stair stair : SimpleStairModel.vanillaWoodStairs) {
                    createStairsRecipe(exporter, stair.origin(), stair.stairs());
                }

                for (SimpleStairModel.Stair stair : SimpleStairModel.vanillaStrippedStairs) {
                    createStairsRecipe(exporter, stair.origin(), stair.stairs());
                }

                for (SimpleWallModel.Wall wall : SimpleWallModel.vanillaWalls) {
                    offerWallRecipe(RecipeCategory.BUILDING_BLOCKS, wall.wall(), wall.block());
                }

                for (SimpleWallModel.Wall wall : SimpleWallModel.vanillaStrippedWalls) {
                    offerWallRecipe(RecipeCategory.BUILDING_BLOCKS, wall.wall(), wall.block());
                }

                for (SimpleWallModel.Wall wall : SimpleWallModel.vanillaWoodWalls) {
                    offerWallRecipe(RecipeCategory.BUILDING_BLOCKS, wall.wall(), wall.block());
                }

                for (SimpleFenceModel.Fence fence : SimpleFenceModel.vanillaStrippedFences) {
                    createFenceRecipe(exporter, fence.block().asItem(), fence.fence());
                }

                for (SimpleFenceModel.Fence fence : SimpleFenceModel.vanillaWoodFences) {
                    createFenceRecipe(exporter, fence.block().asItem(), fence.fence());
                }

                for (SimplePaneModel.Pane pane : SimplePaneModel.panes) {
                    createPaneRecipe(exporter, pane.glass().asItem(), pane.pane(), 16);
                }

                for (SimpleWoodStoolModel.VanillaStool stool : SimpleWoodStoolModel.vanillaStools) {
                    createWoodStoolRecipe(exporter, stool.planks().asItem(), stool.base());
                }

                for (SimpleWoodBenchModel.VanillaBench bench : SimpleWoodBenchModel.vanillaBenchs) {
                    createWoodBenchRecipe(exporter, bench.planks().asItem(), bench.base());
                }

                for (SimpleWoodTableModel.VanillaTable table : SimpleWoodTableModel.vanillaTables) {
                    createWoodTableRecipe(exporter, table.planks().asItem(), table.base());
                }

                for (SimpleWoodChairModel.VanillaChair chair : SimpleWoodChairModel.vanillaChairs) {
                    createWoodChairRecipe(exporter, chair.planks().asItem(), chair.base());
                }

                for (SimpleLadderModel.Ladder ladder : SimpleLadderModel.vanillaLadders) {
                    createWoodLadderRecipe(exporter, ladder.block().asItem(), ladder.ladder());
                }

                for (SimpleRocksModel.Rocks rock : SimpleRocksModel.vanillaRocks) {
                    offerStonecuttingRecipe(RecipeCategory.BUILDING_BLOCKS, rock.rocks(), rock.block(), 4);
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
                createLayerRecipe(exporter, ModBlocks.SKELETAL_PILE.asItem(), ModBlocks.SKELETAL_PILE_LAYER);
                createLayerRecipe(exporter, ModBlocks.WASTE_PILE.asItem(), ModBlocks.WASTE_PILE_LAYER);

                createStairsRecipe(exporter, ModBlocks.GRASSY_DIRT, ModBlocks.GRASSY_DIRT_STAIRS);
                offerSlabRecipe(RecipeCategory.BUILDING_BLOCKS, ModBlocks.GRASSY_DIRT_SLAB, ModBlocks.GRASSY_DIRT);

                createStairsRecipe(exporter, ModBlocks.GRASSY_LOAM, ModBlocks.GRASSY_LOAM_STAIRS);
                offerSlabRecipe(RecipeCategory.BUILDING_BLOCKS, ModBlocks.GRASSY_LOAM_SLAB, ModBlocks.GRASSY_LOAM);

                createStairsRecipe(exporter, ModBlocks.GRASSY_SILT, ModBlocks.GRASSY_SILT_STAIRS);
                offerSlabRecipe(RecipeCategory.BUILDING_BLOCKS, ModBlocks.GRASSY_SILT_SLAB, ModBlocks.GRASSY_SILT);

                createStairsRecipe(exporter, ModBlocks.PEBBLED_GRASS, ModBlocks.PEBBLED_GRASS_STAIRS);
                offerSlabRecipe(RecipeCategory.BUILDING_BLOCKS, ModBlocks.PEBBLED_GRASS_SLAB, ModBlocks.PEBBLED_GRASS);

                createStairsRecipe(exporter, ModBlocks.TURF, ModBlocks.TURF_STAIRS);
                offerSlabRecipe(RecipeCategory.BUILDING_BLOCKS, ModBlocks.TURF_SLAB, ModBlocks.TURF);
                createVerticalSlabsRecipe(exporter, ModBlocks.TURF, ModBlocks.TURF_VERTICAL_SLAB);

                createStairsRecipe(exporter, ModBlocks.MIRE, ModBlocks.MIRE_STAIRS);
                offerSlabRecipe(RecipeCategory.BUILDING_BLOCKS, ModBlocks.MIRE_SLAB, ModBlocks.MIRE);

                createStairsRecipe(exporter, ModBlocks.CHALKSOIL, ModBlocks.CHALKSOIL_STAIRS);
                offerSlabRecipe(RecipeCategory.BUILDING_BLOCKS, ModBlocks.CHALKSOIL_SLAB, ModBlocks.CHALKSOIL);

                createStairsRecipe(exporter, ModBlocks.COARSE_CHALKSOIL, ModBlocks.COARSE_CHALKSOIL_STAIRS);
                offerSlabRecipe(RecipeCategory.BUILDING_BLOCKS, ModBlocks.COARSE_CHALKSOIL_SLAB, ModBlocks.COARSE_CHALKSOIL);

                createStairsRecipe(exporter, ModBlocks.LOAM, ModBlocks.LOAM_STAIRS);
                offerSlabRecipe(RecipeCategory.BUILDING_BLOCKS, ModBlocks.LOAM_SLAB, ModBlocks.LOAM);

                createStairsRecipe(exporter, ModBlocks.COARSE_LOAM, ModBlocks.COARSE_LOAM_STAIRS);
                offerSlabRecipe(RecipeCategory.BUILDING_BLOCKS, ModBlocks.COARSE_LOAM_SLAB, ModBlocks.COARSE_LOAM);

                createStairsRecipe(exporter, ModBlocks.PEAT, ModBlocks.PEAT_STAIRS);
                offerSlabRecipe(RecipeCategory.BUILDING_BLOCKS, ModBlocks.PEAT_SLAB, ModBlocks.PEAT);

                createStairsRecipe(exporter, ModBlocks.COARSE_PEAT, ModBlocks.COARSE_PEAT_STAIRS);
                offerSlabRecipe(RecipeCategory.BUILDING_BLOCKS, ModBlocks.COARSE_PEAT_SLAB, ModBlocks.COARSE_PEAT);

                createStairsRecipe(exporter, ModBlocks.SILT, ModBlocks.SILT_STAIRS);
                offerSlabRecipe(RecipeCategory.BUILDING_BLOCKS, ModBlocks.SILT_SLAB, ModBlocks.SILT);

                createStairsRecipe(exporter, ModBlocks.COARSE_SILT, ModBlocks.COARSE_SILT_STAIRS);
                offerSlabRecipe(RecipeCategory.BUILDING_BLOCKS, ModBlocks.COARSE_SILT_SLAB, ModBlocks.COARSE_SILT);

                createStairsRecipe(exporter, ModBlocks.DRY_DIRT, ModBlocks.DRY_DIRT_STAIRS);
                offerSlabRecipe(RecipeCategory.BUILDING_BLOCKS, ModBlocks.DRY_DIRT_SLAB, ModBlocks.DRY_DIRT);

                createStairsRecipe(exporter, ModBlocks.FOUL_DIRT, ModBlocks.FOUL_DIRT_STAIRS);
                offerSlabRecipe(RecipeCategory.BUILDING_BLOCKS, ModBlocks.FOUL_DIRT_SLAB, ModBlocks.FOUL_DIRT);

                createStairsRecipe(exporter, ModBlocks.DIRTY_ROOTS, ModBlocks.DIRTY_ROOTS_STAIRS);
                offerSlabRecipe(RecipeCategory.BUILDING_BLOCKS, ModBlocks.DIRTY_ROOTS_SLAB, ModBlocks.DIRTY_ROOTS);

                createStairsRecipe(exporter, ModBlocks.ASHEN_DIRT, ModBlocks.ASHEN_DIRT_STAIRS);
                offerSlabRecipe(RecipeCategory.BUILDING_BLOCKS, ModBlocks.ASHEN_DIRT_SLAB, ModBlocks.ASHEN_DIRT);

                createStairsRecipe(exporter, ModBlocks.COBBLY_ASHEN_DIRT, ModBlocks.COBBLY_ASHEN_DIRT_STAIRS);
                offerSlabRecipe(RecipeCategory.BUILDING_BLOCKS, ModBlocks.COBBLY_ASHEN_DIRT_SLAB, ModBlocks.COBBLY_ASHEN_DIRT);

                createStairsRecipe(exporter, ModBlocks.COBBLY_DIRT, ModBlocks.COBBLY_DIRT_STAIRS);
                offerSlabRecipe(RecipeCategory.BUILDING_BLOCKS, ModBlocks.COBBLY_DIRT_SLAB, ModBlocks.COBBLY_DIRT);

                createStairsRecipe(exporter, ModBlocks.SNOWY_DIRT, ModBlocks.SNOWY_DIRT_STAIRS);
                offerSlabRecipe(RecipeCategory.BUILDING_BLOCKS, ModBlocks.SNOWY_DIRT_SLAB, ModBlocks.SNOWY_DIRT);

                createPaneRecipe(exporter, Blocks.WHITE_WOOL.asItem(), ModBlocks.NET, 16);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModBlocks.COPPER_BARS, 16)
                        .pattern("IBI")
                        .pattern("IBI")
                        .input('I', Items.COPPER_INGOT)
                        .input('B', Items.CUT_COPPER)
                        .criterion(hasItem(Items.CUT_COPPER),
                                conditionsFromItem(Items.CUT_COPPER))
                        .offerTo(exporter);

                createBrickRecipe(exporter, ResourceItemsME.CITRINE_SHARD, ModBlocks.CITRINE_BLOCK, 1);
                createFilledRecipe(exporter, Items.GLOWSTONE, ModBlocks.GLOWSTONE_BLOCK, 1);
                createBrickRecipe(exporter, ResourceItemsME.QUARTZ_SHARD, ModBlocks.QUARTZ_BLOCK, 1);
                createBrickRecipe(exporter, ResourceItemsME.RED_AGATE_SHARD, ModBlocks.RED_AGATE_BLOCK, 1);

                /*offerStonecuttingRecipe(RecipeCategory.BUILDING_BLOCKS, Blocks.BRICKS, StoneBlockSets.OLD_BRICKS.base());

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.WHITE_DAUB_HOBBIT_WINDOW, 4)
                        .pattern("WBW")
                        .pattern("BGB")
                        .pattern("WBW")
                        .input('W', StoneBlockSets.WHITE_DAUB.base())
                        .input('G', Items.GLASS)
                        .input('B', Items.BRICK)
                        .criterion(hasItem(StoneBlockSets.WHITE_DAUB.base()),
                                conditionsFromItem(StoneBlockSets.WHITE_DAUB.base()))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.YELLOW_DAUB_HOBBIT_WINDOW, 4)
                        .pattern("WBW")
                        .pattern("BGB")
                        .pattern("WBW")
                        .input('W', StoneBlockSets.YELLOW_DAUB.base())
                        .input('G', Items.GLASS)
                        .input('B', Items.BRICK)
                        .criterion(hasItem(StoneBlockSets.YELLOW_DAUB.base()),
                                conditionsFromItem(StoneBlockSets.YELLOW_DAUB.base()))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.PLASTER_HOBBIT_WINDOW, 4)
                        .pattern("WBW")
                        .pattern("BGB")
                        .pattern("WBW")
                        .input('W', StoneBlockSets.PLASTER.base())
                        .input('G', Items.GLASS)
                        .input('B', Items.BRICK)
                        .criterion(hasItem(StoneBlockSets.PLASTER.base()),
                                conditionsFromItem(StoneBlockSets.PLASTER.base()))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.MEDGON_CARVED_WINDOW, 2)
                        .pattern("EEE")
                        .pattern("EGE")
                        .pattern("EEE")
                        .input('E', StoneBlockSets.MEDGON.base())
                        .input('G', Items.GLASS)
                        .criterion(hasItem(StoneBlockSets.MEDGON.base()),
                                conditionsFromItem(StoneBlockSets.MEDGON.base()))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.GONLUIN_CARVED_WINDOW, 2)
                        .pattern("EEE")
                        .pattern("EGE")
                        .pattern("EEE")
                        .input('E', StoneBlockSets.GONLUIN.base())
                        .input('G', Items.GLASS)
                        .criterion(hasItem(StoneBlockSets.GONLUIN.base()),
                                conditionsFromItem(StoneBlockSets.GONLUIN.base()))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.TUFF_CARVED_WINDOW, 2)
                        .pattern("EEE")
                        .pattern("EGE")
                        .pattern("EEE")
                        .input('E', Blocks.TUFF)
                        .input('G', Items.GLASS)
                        .criterion(hasItem(Blocks.TUFF),
                                conditionsFromItem(Blocks.TUFF))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.BLACKSTONE_CARVED_WINDOW, 2)
                        .pattern("EEE")
                        .pattern("EGE")
                        .pattern("EEE")
                        .input('E', Blocks.BLACKSTONE)
                        .input('G', Items.GLASS)
                        .criterion(hasItem(Blocks.BLACKSTONE),
                                conditionsFromItem(Blocks.BLACKSTONE))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.IZHERABAN_CARVED_WINDOW, 2)
                        .pattern("EEE")
                        .pattern("EGE")
                        .pattern("EEE")
                        .input('E', StoneBlockSets.IZHERABAN.base())
                        .input('G', Items.GLASS)
                        .criterion(hasItem(StoneBlockSets.IZHERABAN.base()),
                                conditionsFromItem(StoneBlockSets.IZHERABAN.base()))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.LEAD_GLASS, 4)
                        .pattern("LGL")
                        .pattern("GLG")
                        .pattern("LGL")
                        .input('L', ResourceItemsME.LEAD_NUGGET)
                        .input('G', Items.GLASS)
                        .criterion(hasItem(ResourceItemsME.ROD),
                                conditionsFromItem(ResourceItemsME.ROD))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.ROPE, 3)
                        .pattern("SS")
                        .pattern("SS")
                        .pattern("SS")
                        .input('S', Items.STRING)
                        .criterion(hasItem(Items.STRING),
                                conditionsFromItem(Items.STRING))
                        .offerTo(exporter);

                createBrickRecipe(exporter, ResourceItemsME.ASH, ModBlocks.ASH_BLOCK, 1);
                createBrickRecipe(exporter, ModBlocks.ASH_BLOCK.asItem(), Blocks.TUFF, 1);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, StoneBlockSets.ASHEN_STONE.base(), 4)
                        .pattern("AS")
                        .pattern("SA")
                        .input('A', ModBlocks.ASH_BLOCK)
                        .input('S', Blocks.STONE)
                        .criterion(hasItem(ModBlocks.ASH_BLOCK),
                                conditionsFromItem(ModBlocks.ASH_BLOCK))
                        .offerTo(exporter);

                createGildedBlockRecipe(exporter, ModBlocks.CHISELED_GREEN_TUFF, ModBlocks.GILDED_CHISELED_GREEN_TUFF);
                createGildedBlockRecipe(exporter, ModBlocks.CHISELED_GREEN_TUFF_BRICKS, ModBlocks.GILDED_CHISELED_GREEN_TUFF_BRICKS);
                createGildedBlockRecipe(exporter, ModBlocks.CHISELED_POLISHED_GREEN_TUFF, ModBlocks.GILDED_CHISELED_POLISHED_GREEN_TUFF);
                createGildedBlockRecipe(exporter, ModBlocks.CHISELED_GREEN_TUFF_TILES, ModBlocks.GILDED_CHISELED_GREEN_TUFF_TILES);
                createGildedBlockRecipe(exporter, ModBlocks.CHISELED_SMOOTH_GREEN_TUFF, ModBlocks.GILDED_CHISELED_SMOOTH_GREEN_TUFF);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, StoneBlockSets.GILDED_GREEN_TUFF.base(), 5)
                        .pattern("TNT")
                        .pattern("NTN")
                        .pattern("TNT")
                        .input('T', StoneBlockSets.GREEN_TUFF.base())
                        .input('N', Items.GOLD_NUGGET)
                        .criterion(hasItem(StoneBlockSets.GREEN_TUFF.base()),
                                conditionsFromItem(StoneBlockSets.GREEN_TUFF.base()))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModBlocks.WATTLE_TRAPDOOR, 2)
                        .pattern("PLP")
                        .pattern("PLP")
                        .input('P', TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input('L', ResourceItemsME.LEAD_NUGGET)
                        .criterion(hasItem(ResourceItemsME.LEAD_NUGGET),
                                conditionsFromItem(ResourceItemsME.LEAD_NUGGET))
                        .offerTo(exporter);

                createDyeableItemRecipe(exporter, ModBlocks.WATTLE_TRAPDOOR, Items.RED_DYE, ModBlocks.RED_WATTLE_TRAPDOOR);
                createDyeableItemRecipe(exporter, ModBlocks.WATTLE_TRAPDOOR, Items.GREEN_DYE, ModBlocks.GREEN_WATTLE_TRAPDOOR);
                createDyeableItemRecipe(exporter, ModBlocks.WATTLE_TRAPDOOR, Items.BROWN_DYE, ModBlocks.DARK_WATTLE_TRAPDOOR);
                createDyeableItemRecipe(exporter, ModBlocks.WATTLE_TRAPDOOR, Items.BLACK_DYE, ModBlocks.BLACK_WATTLE_TRAPDOOR);

                createBrickworkBlockRecipe(exporter, StoneBlockSets.STONE_TILES.base(), StoneBlockSets.STUCCO.base(), StoneBlockSets.STONE_BRICKWORK.base());
                createBrickworkBlockRecipe(exporter, StoneBlockSets.CALCITE_TILES.base(), StoneBlockSets.STUCCO.base(), StoneBlockSets.CALCITE_BRICKWORK.base());
                createBrickworkBlockRecipe(exporter, Blocks.DEEPSLATE_TILES, StoneBlockSets.STUCCO.base(), StoneBlockSets.DEEPSLATE_BRICKWORK.base());
                createBrickworkBlockRecipe(exporter, StoneBlockSets.BASALT_TILES.base(), StoneBlockSets.STUCCO.base(), StoneBlockSets.BASALT_BRICKWORK.base());
                createBrickworkBlockRecipe(exporter, StoneBlockSets.ANDESITE_TILES.base(), StoneBlockSets.STUCCO.base(), StoneBlockSets.ANDESITE_BRICKWORK.base());
                createBrickworkBlockRecipe(exporter, StoneBlockSets.GRANITE_TILES.base(), StoneBlockSets.STUCCO.base(), StoneBlockSets.GRANITE_BRICKWORK.base());
                createBrickworkBlockRecipe(exporter, StoneBlockSets.DIORITE_TILES.base(), StoneBlockSets.STUCCO.base(), StoneBlockSets.DIORITE_BRICKWORK.base());


                createBrickworkBlockRecipe(exporter, StoneBlockSets.DOLOMITE_TILES.base(), StoneBlockSets.STUCCO.base(), StoneBlockSets.DOLOMITE_BRICKWORK.base());
                createBrickworkBlockRecipe(exporter, StoneBlockSets.HEMATITE_TILES.base(), StoneBlockSets.STUCCO.base(), StoneBlockSets.HEMATITE_BRICKWORK.base());
                createBrickworkBlockRecipe(exporter, StoneBlockSets.GNEISS_TILES.base(), StoneBlockSets.STUCCO.base(), StoneBlockSets.GNEISS_BRICKWORK.base());
                createBrickworkBlockRecipe(exporter, StoneBlockSets.IZHERABAN_TILES.base(), StoneBlockSets.STUCCO.base(), StoneBlockSets.IZHERABAN_BRICKWORK.base());
                createBrickworkBlockRecipe(exporter, StoneBlockSets.LIMESTONE_TILES.base(), StoneBlockSets.STUCCO.base(), StoneBlockSets.LIMESTONE_BRICKWORK.base());
                createBrickworkBlockRecipe(exporter, StoneBlockSets.GALONN_TILES.base(), StoneBlockSets.STUCCO.base(), StoneBlockSets.GALONN_BRICKWORK.base());
                createBrickworkBlockRecipe(exporter, StoneBlockSets.GABBRO_BRICKS.base(), StoneBlockSets.STUCCO.base(), StoneBlockSets.GABBRO_BRICKWORK.base());
                createBrickworkBlockRecipe(exporter, StoneBlockSets.TUFF_TILES.base(), StoneBlockSets.STUCCO.base(), StoneBlockSets.TUFF_BRICKWORK.base());
                createBrickworkBlockRecipe(exporter, StoneBlockSets.BLACKSTONE_TILES.base(), StoneBlockSets.STUCCO.base(), StoneBlockSets.BLACKSTONE_BRICKWORK.base());
                createBrickworkBlockRecipe(exporter, StoneBlockSets.TAN_CLAY_BRICKS.base(), StoneBlockSets.PLASTER.base(), StoneBlockSets.TAN_CLAY_BRICKWORK.base());
                createBrickworkBlockRecipe(exporter, StoneBlockSets.MIXED_STONES.base(), StoneBlockSets.STUCCO.base(), StoneBlockSets.MIXED_STONES_BRICKWORK.base());
                createBrickworkBlockRecipe(exporter, StoneBlockSets.MEDGON.base(), StoneBlockSets.STUCCO.base(), StoneBlockSets.MEDGON_BRICKWORK.base());

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

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, StoneBlockSets.TAN_CLAY_BRICKS.base(), 5)
                        .pattern(" B ")
                        .pattern("BPB")
                        .pattern(" B ")
                        .input('P', StoneBlockSets.PLASTER.base())
                        .input('B', Items.BRICKS)
                        .criterion(hasItem(StoneBlockSets.PLASTER.base()),
                                conditionsFromItem(StoneBlockSets.PLASTER.base()))
                        .offerTo(exporter);*/
                //endregion

                //region SMITHING
                createDaggerRecipeTag(exporter, Items.STICK, TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")), WeaponItemsME.WOODEN_DAGGER);
                createDaggerRecipeTag(exporter, Items.STICK, TagKey.of(RegistryKeys.ITEM, Identifier.of("stone_tool_materials")), WeaponItemsME.STONE_DAGGER);
                createDaggerRecipe(exporter, Items.STICK, Items.DIAMOND, WeaponItemsME.DIAMOND_DAGGER);

                createSpearRecipeTag(exporter, Items.STICK, TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")), WeaponItemsME.WOODEN_SPEAR);
                createSpearRecipeTag(exporter, Items.STICK, TagKey.of(RegistryKeys.ITEM, Identifier.of("stone_tool_materials")), WeaponItemsME.STONE_SPEAR);
                createSpearRecipe(exporter, Items.STICK, Items.DIAMOND, WeaponItemsME.DIAMOND_SPEAR);

                createToolSetRecipes(exporter, Items.STICK, ResourceItemsME.BRONZE_INGOT, ToolItemsME.BRONZE_PICKAXE, ToolItemsME.BRONZE_AXE, ToolItemsME.BRONZE_SHOVEL, ToolItemsME.BRONZE_HOE);

                createToolSetRecipes(exporter, Items.STICK, ResourceItemsME.CRUDE_INGOT, ToolItemsME.CRUDE_PICKAXE, ToolItemsME.CRUDE_AXE, ToolItemsME.CRUDE_SHOVEL, ToolItemsME.CRUDE_HOE);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.WEAVER_STING, 1)
                        .pattern("  S")
                        .pattern(" S ")
                        .pattern("W  ")
                        .input('S', ResourceItemsME.SPIDER_STINGER)
                        .input('W', Items.STICK)
                        .criterion(hasItem(ResourceItemsME.SPIDER_STINGER),
                                conditionsFromItem(ResourceItemsME.SPIDER_STINGER))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.MISC, ResourceItemsME.FABRIC, 2)
                        .pattern("sss")
                        .pattern("sss")
                        .input('s', Items.STRING)
                        .criterion(hasItem(Items.STRING),
                                conditionsFromItem(Items.STRING))
                        .offerTo(exporter);

                createBucketRecipe(exporter, Items.IRON_INGOT, Items.BUCKET);

                createMetalsRecipe(exporter, ResourceItemsME.TIN_NUGGET, ResourceItemsME.TIN_INGOT, ModBlocks.TIN_BLOCK);
                createMetalsRecipe(exporter, ResourceItemsME.LEAD_NUGGET, ResourceItemsME.LEAD_INGOT, ModBlocks.LEAD_BLOCK);
                createMetalsRecipe(exporter, ResourceItemsME.SILVER_NUGGET, ResourceItemsME.SILVER_INGOT, ModBlocks.SILVER_BLOCK);
                createMetalsRecipe(exporter, ResourceItemsME.MITHRIL_NUGGET, ResourceItemsME.MITHRIL_INGOT, ModBlocks.MITHRIL_BLOCK);

                createMetalsRecipe(exporter, ResourceItemsME.BRONZE_NUGGET, ResourceItemsME.BRONZE_INGOT, ModBlocks.BRONZE_BLOCK);
                createMetalsRecipe(exporter, ResourceItemsME.CRUDE_NUGGET, ResourceItemsME.CRUDE_INGOT, ModBlocks.CRUDE_BLOCK);
                createMetalsRecipe(exporter, ResourceItemsME.BURZUM_STEEL_NUGGET, ResourceItemsME.BURZUM_STEEL_INGOT, ModBlocks.BURZUM_STEEL_BLOCK);
                createMetalsRecipe(exporter, ResourceItemsME.STEEL_NUGGET, ResourceItemsME.STEEL_INGOT, ModBlocks.STEEL_BLOCK);
                createMetalsRecipe(exporter, ResourceItemsME.EDHEL_STEEL_NUGGET, ResourceItemsME.EDHEL_STEEL_INGOT, ModBlocks.EDHEL_STEEL_BLOCK);
                createMetalsRecipe(exporter, ResourceItemsME.KHAZAD_STEEL_NUGGET, ResourceItemsME.KHAZAD_STEEL_INGOT, ModBlocks.KHAZAD_STEEL_BLOCK);
                //endregion

                //region SEEDS
                createSeedsRecipe(exporter, FoodItemsME.TOMATO, ResourceItemsME.TOMATO_SEEDS);
                createSeedsRecipe(exporter, FoodItemsME.BELL_PEPPER, ResourceItemsME.BELL_PEPPER_SEEDS);
                createSeedsRecipe(exporter, FoodItemsME.CUCUMBER, ResourceItemsME.CUCUMBER_SEEDS);
                createSeedsRecipe(exporter, FoodItemsME.LETTUCE, ResourceItemsME.LETTUCE_SEEDS);
                createSeedsRecipe(exporter, ResourceItemsME.PIPEWEED, ResourceItemsME.PIPEWEED_SEEDS);
                createSeedsRecipe(exporter, ResourceItemsME.FLAX, ResourceItemsME.FLAX_SEEDS);
                //endregion

                //region FOOD
                createCookedFoodRecipes(exporter, FoodItemsME.RAW_HORSE, FoodItemsME.COOKED_HORSE);
                createCookedFoodRecipes(exporter, FoodItemsME.MEAT_SKEWER, FoodItemsME.COOKED_MEAT_SKEWER);
                createCookedFoodRecipes(exporter, FoodItemsME.VEGETABLE_SKEWER, FoodItemsME.COOKED_VEGETABLE_SKEWER);
                createCookedFoodRecipes(exporter, Items.EGG, FoodItemsME.BOILED_EGG);
                //endregion


                ComplexRecipeJsonBuilder.create(HelmetAttachmentRecipe::new).offerTo(exporter, "custom_armor_hood");
                ComplexRecipeJsonBuilder.create(HelmetAttachmentRemovalRecipe::new).offerTo(exporter, "custom_armor_hood_removal");
                ComplexRecipeJsonBuilder.create(BackAttachmentRecipe::new).offerTo(exporter, "custom_armor_cape");
                ComplexRecipeJsonBuilder.create(BackAttachmentRemovalRecipe::new).offerTo(exporter, "custom_armor_cape_removal");
                ComplexRecipeJsonBuilder.create(MountArmorAddonRemovalRecipe::new).offerTo(exporter, "custom_mount_armor_addon_removal");
                ComplexRecipeJsonBuilder.create(MountArmorSideSkullAddonRecipe::new).offerTo(exporter, "custom_mount_armor_side_skull_addon");
                ComplexRecipeJsonBuilder.create(MountArmorTopSkullAddonRecipe::new).offerTo(exporter, "custom_mount_armor_top_skull_addon");

                //region Alloying
                AlloyRecipeJsonBuilder.createAlloyRecipe(itemLookup, RecipeCategory.MISC, "bronze", INGOT_LIQUID_VALUE * 4)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "copper")))
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "copper")))
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "copper")))
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "tin")))
                        .criterion(hasItem(Items.COPPER_INGOT),
                                conditionsFromItem(Items.COPPER_INGOT))
                        .offerTo(exporter, String.valueOf(Identifier.of(MiddleEarth.MOD_ID, "bronze" + "_from_alloying")));

                AlloyRecipeJsonBuilder.createAlloyRecipe(itemLookup, RecipeCategory.MISC, "crude", INGOT_LIQUID_VALUE * 3)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "copper")))
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "copper")))
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "tin")))
                        .input(ResourceItemsME.ASH)
                        .criterion(hasItem(Items.COPPER_INGOT),
                                conditionsFromItem(Items.COPPER_INGOT))
                        .offerTo(exporter, String.valueOf(Identifier.of(MiddleEarth.MOD_ID, "crude" + "_from_alloying")));

                AlloyRecipeJsonBuilder.createAlloyRecipe(itemLookup, RecipeCategory.MISC, "steel", INGOT_LIQUID_VALUE * 3)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "iron")))
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "iron")))
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "iron")))
                        .input(Items.COAL)
                        .criterion(hasItem(Items.IRON_INGOT),
                                conditionsFromItem(Items.IRON_INGOT))
                        .offerTo(exporter, String.valueOf(Identifier.of(MiddleEarth.MOD_ID, "steel" + "_from_alloying_tags")));

                AlloyRecipeJsonBuilder.createAlloyRecipe(itemLookup, RecipeCategory.MISC, "khazad_steel", INGOT_LIQUID_VALUE * 3)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "iron")))
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "iron")))
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "lead")))
                        .input(Items.COAL)
                        .criterion(hasItem(Items.IRON_INGOT),
                                conditionsFromItem(Items.IRON_INGOT))
                        .offerTo(exporter, String.valueOf(Identifier.of(MiddleEarth.MOD_ID, "khazad_steel" + "_from_alloying_tags")));

                AlloyRecipeJsonBuilder.createAlloyRecipe(itemLookup, RecipeCategory.MISC, "edhel_steel", INGOT_LIQUID_VALUE * 3)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "iron")))
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "iron")))
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "iron")))
                        .input(ResourceItemsME.SILVER_NUGGET)
                        .criterion(hasItem(Items.IRON_INGOT),
                                conditionsFromItem(Items.IRON_INGOT))
                        .offerTo(exporter, String.valueOf(Identifier.of(MiddleEarth.MOD_ID, "edhel_steel" + "_from_alloying_tags")));

                AlloyRecipeJsonBuilder.createAlloyRecipe(itemLookup, RecipeCategory.MISC, "burzum_steel", INGOT_LIQUID_VALUE * 3)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "iron")))
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "iron")))
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "lead")))
                        .input(ResourceItemsME.ASH)
                        .criterion(hasItem(Items.IRON_INGOT),
                                conditionsFromItem(Items.IRON_INGOT))
                        .offerTo(exporter, String.valueOf(Identifier.of(MiddleEarth.MOD_ID, "burzum_steel" + "_from_alloying_tags")));

                HotMetalsModel.nuggets.forEach(nugget -> {
                    //createMeltRecipe(exporter, nugget, Registries.ITEM.getId(nugget).getPath().replace("_nugget", ""), INGOT_LIQUID_VALUE / 9);
                });
                HotMetalsModel.shapesTag.forEach(shape -> {
                    createAnvilShapingRecipeTag(exporter, shape.tagKey(), shape.output(), shape.amount());
                });
                HotMetalsModel.shapesItem.forEach(shape -> {
                    createAnvilShapingRecipeItem(exporter, shape.item(), shape.output(), shape.amount());
                });

                createMeltBulkRecipeTag(exporter, TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "copper")), "copper");
                createMeltBulkRecipeTag(exporter, TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "tin")), "tin");

                createMeltBulkRecipe(exporter, ResourceItemsME.BRONZE_INGOT, "bronze");
                createMeltBulkRecipe(exporter, ResourceItemsME.CRUDE_INGOT, "crude");

                createMeltBulkRecipeTag(exporter, TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "lead")), "lead");
                createMeltBulkRecipeTag(exporter, TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "silver")), "silver");
                createMeltBulkRecipeTag(exporter, TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "iron")), "iron");
                createMeltBulkRecipeTag(exporter, TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "gold")), "gold");

                createMeltBulkRecipe(exporter, ResourceItemsME.STEEL_INGOT, "steel");
                createMeltBulkRecipe(exporter, ResourceItemsME.KHAZAD_STEEL_INGOT, "khazad_steel");
                createMeltBulkRecipe(exporter, ResourceItemsME.EDHEL_STEEL_INGOT, "edhel_steel");
                createMeltBulkRecipe(exporter, ResourceItemsME.BURZUM_STEEL_INGOT, "burzum_steel");

                createMeltBulkRecipeTag(exporter, TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "mithril")), "mithril");

                createMeltBulkRecipe(exporter, Items.NETHERITE_INGOT, "netherite");

                createAnvilRecipe(exporter, ModBlocks.STEEL_BLOCK.asItem(), ResourceItemsME.STEEL_INGOT, DecorativeItemsME.TREATED_ANVIL);
                createAnvilRecipe(exporter, ModBlocks.KHAZAD_STEEL_BLOCK.asItem(), ResourceItemsME.KHAZAD_STEEL_INGOT, DecorativeItemsME.DWARVEN_TREATED_ANVIL);
                createAnvilRecipe(exporter, ModBlocks.EDHEL_STEEL_BLOCK.asItem(), ResourceItemsME.EDHEL_STEEL_INGOT, DecorativeItemsME.ELVEN_TREATED_ANVIL);
                createAnvilRecipe(exporter, ModBlocks.BURZUM_STEEL_BLOCK.asItem(), ResourceItemsME.BURZUM_STEEL_INGOT, DecorativeItemsME.ORCISH_TREATED_ANVIL);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, DecorativeItemsME.BELLOWS, 1)
                        .pattern(" PS")
                        .pattern("PFF")
                        .pattern("TPS")
                        .input('P', TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input('S', Items.STICK)
                        .input('F', Items.LEATHER)
                        .input('T', ResourceItemsME.TIN_INGOT)
                        .criterion(hasItem(ResourceItemsME.TIN_INGOT),
                                conditionsFromItem(ResourceItemsME.TIN_INGOT))
                        .offerTo(exporter);

                createWattleRecipes(exporter, Items.BRICKS,
                        ModBlocks.WATTLE_AND_BRICK, ModBlocks.WATTLE_AND_BRICK_CROSS, ModBlocks.WATTLE_AND_BRICK_RIGHT,
                        ModBlocks.WATTLE_AND_BRICK_LEFT, ModBlocks.WATTLE_AND_BRICK_PILLAR, ModBlocks.WATTLE_AND_BRICK_DIAMOND);

                createWattleRecipes(exporter, GenericBlockSets.WHITE_DAUB.blockSet.base().asItem(),
                        ModBlocks.WATTLE_AND_WHITE_DAUB, ModBlocks.WATTLE_AND_WHITE_DAUB_CROSS, ModBlocks.WATTLE_AND_WHITE_DAUB_RIGHT,
                        ModBlocks.WATTLE_AND_WHITE_DAUB_LEFT, ModBlocks.WATTLE_AND_WHITE_DAUB_PILLAR, ModBlocks.WATTLE_AND_WHITE_DAUB_DIAMOND);

                createWattleRecipes(exporter, GenericBlockSets.DARK_DAUB.blockSet.base().asItem(),
                        ModBlocks.DARK_WATTLE_AND_DARK_DAUB, ModBlocks.DARK_WATTLE_AND_DARK_DAUB_CROSS, ModBlocks.DARK_WATTLE_AND_DARK_DAUB_RIGHT,
                        ModBlocks.DARK_WATTLE_AND_DARK_DAUB_LEFT, ModBlocks.DARK_WATTLE_AND_DARK_DAUB_PILLAR, ModBlocks.DARK_WATTLE_AND_DARK_DAUB_DIAMOND);

                createWattleRecipes(exporter, GenericBlockSets.YELLOW_DAUB.blockSet.base().asItem(),
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

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModBlocks.BRONZE_BARS, 16)
                        .pattern("SSS")
                        .pattern("SSS")
                        .input('S', ResourceItemsME.BRONZE_INGOT)
                        .criterion(hasItem(ResourceItemsME.BRONZE_INGOT),
                                conditionsFromItem(ResourceItemsME.BRONZE_INGOT))
                        .offerTo(exporter);
                
                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModBlocks.CRUDE_BARS, 16)
                        .pattern("SSS")
                        .pattern("SSS")
                        .input('S', ResourceItemsME.CRUDE_INGOT)
                        .criterion(hasItem(ResourceItemsME.CRUDE_INGOT),
                                conditionsFromItem(ResourceItemsME.CRUDE_INGOT))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModBlocks.TREATED_STEEL_BARS, 16)
                        .pattern("SSS")
                        .pattern("SSS")
                        .input('S', TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "steel_ingots")))
                        .criterion(hasItem(ResourceItemsME.STEEL_INGOT),
                                conditionsFromItem(ResourceItemsME.STEEL_INGOT))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModBlocks.AGED_WOOD_TRAPDOOR, 2)
                        .pattern("WWW")
                        .pattern("WWW")
                        .input('W', GenericBlockSets.AGED_WOOD_PLANKS.blockSet.base())
                        .criterion(hasItem(GenericBlockSets.AGED_WOOD_PLANKS.blockSet.base()),
                                conditionsFromItem(GenericBlockSets.AGED_WOOD_PLANKS.blockSet.base()))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModBlocks.BRONZE_TRAPDOOR, 2)
                        .pattern("NSN")
                        .pattern("NSN")
                        .input('S', ResourceItemsME.BRONZE_INGOT)
                        .input('N', ResourceItemsME.BRONZE_NUGGET)
                        .criterion(hasItem(ResourceItemsME.BRONZE_INGOT),
                                conditionsFromItem(ResourceItemsME.BRONZE_INGOT))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModBlocks.CRUDE_TRAPDOOR, 2)
                        .pattern("NSN")
                        .pattern("NSN")
                        .input('S', ResourceItemsME.CRUDE_INGOT)
                        .input('N', ResourceItemsME.CRUDE_NUGGET)
                        .criterion(hasItem(ResourceItemsME.CRUDE_INGOT),
                                conditionsFromItem(ResourceItemsME.CRUDE_INGOT))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModBlocks.TREATED_STEEL_TRAPDOOR, 2)
                        .pattern("NSN")
                        .pattern("NSN")
                        .input('S', TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "steel_ingots")))
                        .input('N', TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "steel_nuggets")))
                        .criterion(hasItem(ResourceItemsME.STEEL_INGOT),
                                conditionsFromItem(ResourceItemsME.STEEL_INGOT))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModBlocks.AGED_WOOD_DOOR, 3)
                        .pattern("SS")
                        .pattern("SS")
                        .pattern("SS")
                        .input('S', GenericBlockSets.AGED_WOOD_PLANKS.blockSet.base())
                        .criterion(hasItem(GenericBlockSets.AGED_WOOD_PLANKS.blockSet.base()),
                                conditionsFromItem(GenericBlockSets.AGED_WOOD_PLANKS.blockSet.base()))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModBlocks.BRONZE_DOOR, 3)
                        .pattern("SS")
                        .pattern("SS")
                        .pattern("SS")
                        .input('S', ResourceItemsME.BRONZE_INGOT)
                        .criterion(hasItem(ResourceItemsME.BRONZE_INGOT),
                                conditionsFromItem(ResourceItemsME.BRONZE_INGOT))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModBlocks.CRUDE_DOOR, 3)
                        .pattern("SS")
                        .pattern("SS")
                        .pattern("SS")
                        .input('S', ResourceItemsME.CRUDE_INGOT)
                        .criterion(hasItem(ResourceItemsME.CRUDE_INGOT),
                                conditionsFromItem(ResourceItemsME.CRUDE_INGOT))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModBlocks.TREATED_STEEL_DOOR, 3)
                        .pattern("SS")
                        .pattern("SS")
                        .pattern("SS")
                        .input('S', TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "steel_ingots")))
                        .criterion(hasItem(ResourceItemsME.STEEL_INGOT),
                                conditionsFromItem(ResourceItemsME.STEEL_INGOT))
                        .offerTo(exporter);

                createPaneRecipe(exporter, ResourceItemsME.SILVER_INGOT, ModBlocks.SILVER_BARS, 16);
                createPaneRecipe(exporter, Items.GOLD_INGOT, ModBlocks.GILDED_BARS, 16);

                createCenterSurroundRecipe(exporter, GenericBlockSets.WHITE_DAUB.blockSet.base().asItem(), Items.BLACK_DYE, GenericBlockSets.DARK_DAUB.blockSet.base().asItem(), 8);
                createCenterSurroundRecipe(exporter, GenericBlockSets.WHITE_DAUB.blockSet.base().asItem(), Items.YELLOW_DYE, GenericBlockSets.YELLOW_DAUB.blockSet.base().asItem(), 8);

                //region TREATED_WOOD
                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, GenericBlockSets.TREATED_WOOD.blockSet.base(), 6)
                        .pattern("PPP")
                        .pattern("PHP")
                        .pattern("PPP")
                        .input('P', TagKey.of(RegistryKeys.ITEM, Identifier.of("logs")))
                        .input('H', ResourceItemsME.ASH)
                        .criterion(hasItem(ResourceItemsME.ASH),
                                conditionsFromItem(ResourceItemsME.ASH))
                        .offerTo(exporter);

                createBrickRecipe(exporter, GenericBlockSets.TREATED_WOOD.blockSet.base().asItem(), GenericBlockSets.TREATED_WOOD_BEAM.blockSet.base(), 3);

                ShapelessRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, GenericBlockSets.TREATED_WOOD_PLANKS.blockSet.base(), 4)
                        .input(GenericBlockSets.TREATED_WOOD.blockSet.base())
                        .criterion(hasItem(GenericBlockSets.TREATED_WOOD.blockSet.base()),
                                conditionsFromItem(GenericBlockSets.TREATED_WOOD.blockSet.base()))
                        .offerTo(exporter);

                createBrickRecipe(exporter, GenericBlockSets.TREATED_WOOD_PLANKS.blockSet.base().asItem(), GenericBlockSets.TREATED_WOOD_PANELS.blockSet.base(), 4);
                createGenericRecipes(GenericBlockSets.TREATED_WOOD);
                createGenericRecipes(GenericBlockSets.TREATED_WOOD_PLANKS);
                createGenericRecipes(GenericBlockSets.TREATED_WOOD_BEAM);
                createGenericRecipes(GenericBlockSets.TREATED_WOOD_PANELS);
                createGenericRecipes(GenericBlockSets.TREATED_WOOD_TILING);
                createGenericRecipes(GenericBlockSets.TREATED_WOOD_CARVED_BEAM);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, GenericBlockSets.TREATED_WOOD_CARVED_BEAM.blockSet.base(), 1)
                        .pattern("S")
                        .pattern("S")
                        .input('S', GenericBlockSets.TREATED_WOOD_BEAM.blockSet.slab())
                        .criterion(hasItem(GenericBlockSets.TREATED_WOOD_BEAM.blockSet.slab()),
                                conditionsFromItem(GenericBlockSets.TREATED_WOOD_BEAM.blockSet.slab()))
                        .offerTo(exporter);
                //endregion

                //region AGED_WOOD
                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, GenericBlockSets.AGED_WOOD.blockSet.base(), 6)
                        .pattern("PPP")
                        .pattern("PAP")
                        .pattern("PPP")
                        .input('P', TagKey.of(RegistryKeys.ITEM, Identifier.of("logs")))
                        .input('A', ResourceItemsME.ASH)
                        .criterion(hasItem(ResourceItemsME.ASH),
                                conditionsFromItem(ResourceItemsME.ASH))
                        .offerTo(exporter);

                createBrickRecipe(exporter, GenericBlockSets.AGED_WOOD.blockSet.base().asItem(), GenericBlockSets.AGED_WOOD_BEAM.blockSet.base(), 3);
                createGenericRecipes(GenericBlockSets.AGED_WOOD);
                createGenericRecipes(GenericBlockSets.AGED_WOOD_PLANKS);
                createGenericRecipes(GenericBlockSets.AGED_WOOD_BEAM);
                createGenericRecipes(GenericBlockSets.AGED_WOOD_PANELS);
                createGenericRecipes(GenericBlockSets.AGED_WOOD_BOARDS);
                createGenericRecipes(GenericBlockSets.AGED_WOOD_FISH_CARVING);
                createShinglesRecipe(exporter, GenericBlockSets.AGED_WOOD_PLANKS.blockSet.base(), GenericBlockSets.AGED_WOOD_SHINGLES.blockSet.base());

                ShapelessRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, GenericBlockSets.AGED_WOOD_PLANKS.blockSet.base(), 4)
                        .input(GenericBlockSets.AGED_WOOD.blockSet.base())
                        .criterion(hasItem(GenericBlockSets.AGED_WOOD.blockSet.base()),
                                conditionsFromItem(GenericBlockSets.AGED_WOOD.blockSet.base()))
                        .offerTo(exporter, String.valueOf(Identifier.of(MiddleEarth.MOD_ID, Registries.BLOCK.getId(GenericBlockSets.AGED_WOOD_PLANKS.blockSet.base()).getPath() + "_from_wood")));

                createBrickRecipe(exporter, GenericBlockSets.AGED_WOOD_PLANKS.blockSet.base().asItem(), GenericBlockSets.AGED_WOOD_PANELS.blockSet.base(), 4);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, GenericBlockSets.AGED_WOOD_CARVING.blockSet.base(), 1)
                        .pattern("S")
                        .pattern("S")
                        .input('S', GenericBlockSets.AGED_WOOD_BEAM.blockSet.slab())
                        .criterion(hasItem(GenericBlockSets.AGED_WOOD_BEAM.blockSet.slab()),
                                conditionsFromItem(GenericBlockSets.AGED_WOOD_BEAM.blockSet.slab()))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, GenericBlockSets.AGED_WOOD_FISH_CARVING.blockSet.base(), 1)
                        .pattern("S")
                        .pattern("S")
                        .input('S', GenericBlockSets.AGED_WOOD_CARVING.blockSet.slab())
                        .criterion(hasItem(GenericBlockSets.AGED_WOOD_CARVING.blockSet.slab()),
                                conditionsFromItem(GenericBlockSets.AGED_WOOD_CARVING.blockSet.slab()))
                        .offerTo(exporter);

                createBrickRecipe(exporter, GenericBlockSets.AGED_WOOD_PANELS.blockSet.base().asItem(), GenericBlockSets.AGED_WOOD_BOARDS.blockSet.base(), 4);
                //endregion

                createCombinedItemRecipe(exporter, Blocks.SKELETON_SKULL, ItemTags.CANDLES, ModDecorativeBlocks.SKULL_CANDLE);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.CANDLESTICK, 1)
                        .pattern("C")
                        .pattern("S")
                        .pattern("S")
                        .input('C', TagKey.of(RegistryKeys.ITEM, Identifier.of("candles")))
                        .input('S', ResourceItemsME.STEEL_NUGGET)
                        .criterion(hasItem(Items.CANDLE),
                                conditionsFromItem(Items.CANDLE))
                        .offerTo(exporter);
                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.CANDLE_HOLDER, 1)
                        .pattern("C ")
                        .pattern("SS")
                        .input('C', TagKey.of(RegistryKeys.ITEM, Identifier.of("candles")))
                        .input('S', ResourceItemsME.STEEL_NUGGET)
                        .criterion(hasItem(Items.CANDLE),
                                conditionsFromItem(Items.CANDLE))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.CERAMIC_LAMP, 1)
                        .pattern("T ")
                        .pattern("BB")
                        .input('T', Items.TORCH)
                        .input('B', Items.BRICK)
                        .criterion(hasItem(Items.BRICK),
                                conditionsFromItem(Items.BRICK))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.CANDLE_HEAP, 1)
                        .pattern("CCC")
                        .pattern("CCC")
                        .input('C', TagKey.of(RegistryKeys.ITEM, Identifier.of("candles")))
                        .criterion(hasItem(Items.CANDLE),
                                conditionsFromItem(Items.CANDLE))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.STONE_LECTERN.asItem(), 1)
                        .pattern("SSS")
                        .pattern(" B ")
                        .pattern(" S ")
                        .input('S', Items.STONE)
                        .input('B', Items.BOOKSHELF)
                        .criterion(hasItem(Items.BOOKSHELF),
                                conditionsFromItem(Items.BOOKSHELF))
                        .offerTo(exporter);
                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.CHISELED_DOLOMITE_BOOKSHELF, 1)
                        .pattern("DDD")
                        .pattern("SSS")
                        .pattern("DDD")
                        .input('D', StoneBlockSets.DOLOMITE_SET.baseBlocks.base())
                        .input('S', StoneBlockSets.DOLOMITE_SET.baseBlocks.slab())
                        .criterion(hasItem(Items.BOOKSHELF),
                                conditionsFromItem(Items.BOOKSHELF))
                        .offerTo(exporter);

                createStatueRecipe(exporter, Blocks.POLISHED_BASALT, Blocks.BASALT, StoneBlockSets.BASALT_SET.baseBlocks.wall(), ModDecorativeBlocks.BASALT_STATUE);
                createStatueRecipe(exporter, StoneBlockSets.CALCITE_SET.polishedBlocks.base(), Blocks.CALCITE, StoneBlockSets.CALCITE_SET.baseBlocks.wall(), ModDecorativeBlocks.CALCITE_STATUE);
                createStatueRecipe(exporter, StoneBlockSets.DIORITE_SET.polishedBlocks.base(), Blocks.CALCITE, StoneBlockSets.CALCITE_SET.baseBlocks.wall(), ModDecorativeBlocks.DIORITE_STATUE);
                createStatueRecipe(exporter, StoneBlockSets.GALONN_SET.polishedBlocks.base(), StoneBlockSets.GALONN_SET.baseBlocks.base(), StoneBlockSets.GALONN_SET.baseBlocks.wall(), ModDecorativeBlocks.GALONN_STATUE);
                createStatueRecipe(exporter, StoneBlockSets.KHAGALABAN_SET.polishedBlocks.base(), StoneBlockSets.KHAGALABAN_SET.baseBlocks.base(), StoneBlockSets.KHAGALABAN_SET.baseBlocks.wall(), ModDecorativeBlocks.KHAGALABAN_STATUE);
                createStatueRecipe(exporter, StoneBlockSets.PUMICE_SET.baseBlocks.base(), StoneBlockSets.PUMICE_SET.baseBlocks.base(), StoneBlockSets.PUMICE_SET.baseBlocks.wall(), ModDecorativeBlocks.PUMICE_STATUE);
                createStatueRecipe(exporter, Blocks.POLISHED_TUFF, Blocks.TUFF, Blocks.TUFF_WALL, ModDecorativeBlocks.TUFF_STATUE);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.CERAMIC_PLATE, 1)
                        .pattern("BB")
                        .input('B', Items.BRICK)
                        .criterion(hasItem(Items.BRICK),
                                conditionsFromItem(Items.BRICK))
                        .offerTo(exporter);
                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.ROTTEN_PLATE, 4)
                        .pattern("RR")
                        .input('R', WoodBlockSets.ROTTEN_SET.logBlocks.log())
                        .criterion(hasItem(WoodBlockSets.ROTTEN_SET.logBlocks.log()),
                                conditionsFromItem(WoodBlockSets.ROTTEN_SET.logBlocks.log()))
                        .offerTo(exporter);
                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.SILVER_PLATE, 1)
                        .pattern("SS")
                        .input('S', ResourceItemsME.SILVER_INGOT)
                        .criterion(hasItem(ResourceItemsME.SILVER_INGOT),
                                conditionsFromItem(ResourceItemsME.SILVER_INGOT))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.MEDGON_SPIKE, 1)
                        .pattern("M  ")
                        .pattern("MM ")
                        .pattern("PMP")
                        .input('M', StoneBlockSets.MEDGON_SET.baseBlocks.base())
                        .input('P', StoneBlockSets.MEDGON_SET.polishedBlocks.base())
                        .criterion(hasItem(StoneBlockSets.MEDGON_SET.baseBlocks.base()),
                                conditionsFromItem(StoneBlockSets.MEDGON_SET.baseBlocks.base()))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.TAPPER, 1)
                        .pattern(" S ")
                        .pattern("LBL")
                        .pattern(" L ")
                        .input('S', ResourceItemsME.STEEL_NUGGET)
                        .input('L', ItemTags.LOGS)
                        .input('B', Items.BUCKET)
                        .criterion(hasItem(Items.BUCKET),
                                conditionsFromItem(Items.BUCKET));

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.ORCISH_DRUM, 1)
                        .pattern("SLS")
                        .pattern("W W")
                        .pattern(" W ")
                        .input('S', Items.STICK)
                        .input('W', ItemTags.LOGS)
                        .input('L', Items.LEATHER)
                        .criterion(hasItem(Items.LEATHER),
                                conditionsFromItem(Items.LEATHER))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.WATTLE_AND_BRICK_WINDOW, 4)
                        .pattern("BSB")
                        .pattern("SGS")
                        .pattern("BSB")
                        .input('B', Items.BRICKS)
                        .input('G', Items.GLASS)
                        .input('S', Items.STICK)
                        .criterion(hasItem(Items.BRICKS),
                                conditionsFromItem(Items.BRICKS))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.WATTLE_FRAMED_WINDOW, 2)
                        .pattern("SSS")
                        .pattern("SGS")
                        .pattern("SSS")
                        .input('G', Items.GLASS)
                        .input('S', Items.STICK)
                        .criterion(hasItem(Items.STICK),
                                conditionsFromItem(Items.STICK))
                        .offerTo(exporter);

                createCenterSurroundRecipe(exporter, ModDecorativeBlocks.WATTLE_FRAMED_WINDOW.asItem(), Items.GRAY_DYE, ModDecorativeBlocks.DARK_WATTLE_FRAMED_WINDOW.asItem(), 8);
                createCenterSurroundRecipe(exporter, ModDecorativeBlocks.WATTLE_FRAMED_WINDOW.asItem(), Items.BLACK_DYE, ModDecorativeBlocks.BLACK_WATTLE_FRAMED_WINDOW.asItem(), 8);
                createCenterSurroundRecipe(exporter, ModDecorativeBlocks.WATTLE_FRAMED_WINDOW.asItem(), Items.GREEN_DYE, ModDecorativeBlocks.GREEN_WATTLE_FRAMED_WINDOW.asItem(), 8);
                createCenterSurroundRecipe(exporter, ModDecorativeBlocks.WATTLE_FRAMED_WINDOW.asItem(), Items.RED_DYE, ModDecorativeBlocks.RED_WATTLE_FRAMED_WINDOW.asItem(), 8);
                createCenterSurroundRecipe(exporter, ModDecorativeBlocks.WATTLE_FRAMED_WINDOW.asItem(), Items.WHITE_DYE, ModDecorativeBlocks.WHITE_WATTLE_FRAMED_WINDOW.asItem(), 8);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.MUD_BRICK_ROUND_WINDOW, 4)
                        .pattern("MBM")
                        .pattern("BGB")
                        .pattern("MBM")
                        .input('M', Items.MUD_BRICKS)
                        .input('G', Items.GLASS)
                        .input('B', Items.BRICK)
                        .criterion(hasItem(Items.BRICKS),
                                conditionsFromItem(Items.BRICKS))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.WHITE_DAUB_ROUND_WINDOW, 4)
                        .pattern("WSW")
                        .pattern("SGS")
                        .pattern("WSW")
                        .input('W', GenericBlockSets.WHITE_DAUB.blockSet.base())
                        .input('G', Items.GLASS)
                        .input('S', Items.STICK)
                        .criterion(hasItem(GenericBlockSets.WHITE_DAUB.blockSet.base()),
                                conditionsFromItem(GenericBlockSets.WHITE_DAUB.blockSet.base()))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.YELLOW_DAUB_ROUND_WINDOW, 4)
                        .pattern("WSW")
                        .pattern("SGS")
                        .pattern("WSW")
                        .input('W', GenericBlockSets.YELLOW_DAUB.blockSet.base())
                        .input('G', Items.GLASS)
                        .input('S', Items.STICK)
                        .criterion(hasItem(GenericBlockSets.YELLOW_DAUB.blockSet.base()),
                                conditionsFromItem(GenericBlockSets.YELLOW_DAUB.blockSet.base()))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.PLASTER_ROUND_WINDOW, 4)
                        .pattern("WSW")
                        .pattern("SGS")
                        .pattern("WSW")
                        .input('W', GenericBlockSets.PLASTER.blockSet.base())
                        .input('G', Items.GLASS)
                        .input('S', Items.STICK)
                        .criterion(hasItem(GenericBlockSets.PLASTER.blockSet.base()),
                                conditionsFromItem(GenericBlockSets.PLASTER.blockSet.base()))
                        .offerTo(exporter);

                createCushionRecipe(exporter, Blocks.BLUE_WOOL, ModDecorativeBlocks.BLUE_CUSHION);
                createDyeableItemRecipe(exporter, ModDecorativeBlocks.BLUE_CUSHION, Items.GRAY_DYE, ModDecorativeBlocks.DARK_BLUE_CUSHION);
                createCushionRecipe(exporter, Blocks.BROWN_WOOL, ModDecorativeBlocks.BROWN_CUSHION);
                createDyeableItemRecipe(exporter, ModDecorativeBlocks.BROWN_CUSHION, Items.GRAY_DYE, ModDecorativeBlocks.DARK_BROWN_CUSHION);
                createCushionRecipe(exporter, Blocks.GREEN_WOOL, ModDecorativeBlocks.GREEN_CUSHION);
                createDyeableItemRecipe(exporter, ModDecorativeBlocks.GREEN_CUSHION, Items.GRAY_DYE, ModDecorativeBlocks.DARK_GREEN_CUSHION);
                createCushionRecipe(exporter, Blocks.RED_WOOL, ModDecorativeBlocks.RED_CUSHION);
                createDyeableItemRecipe(exporter, ModDecorativeBlocks.RED_CUSHION, Items.GRAY_DYE, ModDecorativeBlocks.DARK_RED_CUSHION);

                createSmallCurtainRecipe(exporter, Blocks.BLACK_WOOL, ModDecorativeBlocks.SMALL_BLACK_CURTAIN);
                createSmallCurtainRecipe(exporter, Blocks.BLUE_WOOL, ModDecorativeBlocks.SMALL_BLUE_CURTAIN);
                createSmallCurtainRecipe(exporter, Blocks.BROWN_WOOL, ModDecorativeBlocks.SMALL_BROWN_CURTAIN);
                createDyeableItemRecipe(exporter, ModDecorativeBlocks.SMALL_BLUE_CURTAIN, Items.GRAY_DYE, ModDecorativeBlocks.SMALL_DARK_BLUE_CURTAIN);
                createDyeableItemRecipe(exporter, ModDecorativeBlocks.SMALL_BROWN_CURTAIN, Items.GRAY_DYE, ModDecorativeBlocks.SMALL_DARK_BROWN_CURTAIN);
                createDyeableItemRecipe(exporter, ModDecorativeBlocks.SMALL_GREEN_CURTAIN, Items.GRAY_DYE, ModDecorativeBlocks.SMALL_DARK_GREEN_CURTAIN);
                createDyeableItemRecipe(exporter, ModDecorativeBlocks.SMALL_RED_CURTAIN, Items.GRAY_DYE, ModDecorativeBlocks.SMALL_DARK_RED_CURTAIN);
                createSmallFancyCurtainRecipe(exporter, Blocks.BLUE_WOOL, ModDecorativeBlocks.SMALL_FANCY_BLUE_CURTAIN);
                createSmallFancyCurtainRecipe(exporter, Blocks.GREEN_WOOL, ModDecorativeBlocks.SMALL_FANCY_GREEN_CURTAIN);
                createSmallFancyCurtainRecipe(exporter, Blocks.RED_WOOL, ModDecorativeBlocks.SMALL_FANCY_RED_CURTAIN);
                createSmallCurtainRecipe(exporter, Blocks.GRAY_WOOL, ModDecorativeBlocks.SMALL_GRAY_CURTAIN);
                createSmallCurtainRecipe(exporter, Blocks.GREEN_WOOL, ModDecorativeBlocks.SMALL_GREEN_CURTAIN);
                createSmallCurtainRecipe(exporter, Blocks.PURPLE_WOOL, ModDecorativeBlocks.SMALL_PURPLE_CURTAIN);
                createSmallCurtainRecipe(exporter, Blocks.RED_WOOL, ModDecorativeBlocks.SMALL_RED_CURTAIN);
                createSmallCurtainRecipe(exporter, Blocks.WHITE_WOOL, ModDecorativeBlocks.SMALL_WHITE_CURTAIN);
                createSmallCurtainRecipe(exporter, Blocks.YELLOW_WOOL, ModDecorativeBlocks.SMALL_YELLOW_CURTAIN);

                createCurtainRecipe(exporter, Blocks.BLACK_WOOL, ModDecorativeBlocks.BLACK_CURTAIN);
                createCurtainRecipe(exporter, Blocks.BLUE_WOOL, ModDecorativeBlocks.BLUE_CURTAIN);
                createCurtainRecipe(exporter, Blocks.BROWN_WOOL, ModDecorativeBlocks.BROWN_CURTAIN);
                createDyeableItemRecipe(exporter, ModDecorativeBlocks.BLUE_CURTAIN, Items.GRAY_DYE, ModDecorativeBlocks.DARK_BLUE_CURTAIN);
                createDyeableItemRecipe(exporter, ModDecorativeBlocks.BROWN_CURTAIN, Items.GRAY_DYE, ModDecorativeBlocks.DARK_BROWN_CURTAIN);
                createDyeableItemRecipe(exporter, ModDecorativeBlocks.GREEN_CURTAIN, Items.GRAY_DYE, ModDecorativeBlocks.DARK_GREEN_CURTAIN);
                createDyeableItemRecipe(exporter, ModDecorativeBlocks.RED_CURTAIN, Items.GRAY_DYE, ModDecorativeBlocks.DARK_RED_CURTAIN);
                createFancyCurtainRecipe(exporter, Blocks.BLUE_WOOL, ModDecorativeBlocks.FANCY_BLUE_CURTAIN);
                createFancyCurtainRecipe(exporter, Blocks.GREEN_WOOL, ModDecorativeBlocks.FANCY_GREEN_CURTAIN);
                createFancyCurtainRecipe(exporter, Blocks.RED_WOOL, ModDecorativeBlocks.FANCY_RED_CURTAIN);
                createCurtainRecipe(exporter, Blocks.GRAY_WOOL, ModDecorativeBlocks.GRAY_CURTAIN);
                createCurtainRecipe(exporter, Blocks.GREEN_WOOL, ModDecorativeBlocks.GREEN_CURTAIN);
                createCurtainRecipe(exporter, Blocks.PURPLE_WOOL, ModDecorativeBlocks.PURPLE_CURTAIN);
                createCurtainRecipe(exporter, Blocks.RED_WOOL, ModDecorativeBlocks.RED_CURTAIN);
                createCurtainRecipe(exporter, Blocks.WHITE_WOOL, ModDecorativeBlocks.WHITE_CURTAIN);
                createCurtainRecipe(exporter, Blocks.YELLOW_WOOL, ModDecorativeBlocks.YELLOW_CURTAIN);
                
                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.ROPE_LADDER, 3)
                        .pattern("R R")
                        .pattern("RSR")
                        .pattern("R R")
                        .input('R', ModDecorativeBlocks.ROPE)
                        .input('S', Items.STRING)
                        .criterion(hasItem(ModDecorativeBlocks.ROPE),
                                conditionsFromItem(ModDecorativeBlocks.ROPE))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.FANCY_BED, 1)
                        .pattern("FFW")
                        .pattern("FFW")
                        .pattern("PPP")
                        .input('W', TagKey.of(RegistryKeys.ITEM, Identifier.of("wool")))
                        .input('F', ResourceItemsME.FABRIC)
                        .input('P', TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .criterion(hasItem(ResourceItemsME.FABRIC),
                                conditionsFromItem(ResourceItemsME.FABRIC))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.STRAW_BED, 1)
                        .pattern("SSS")
                        .pattern("PPP")
                        .input('S', ResourceItemsME.STRAW)
                        .input('P', TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .criterion(hasItem(ResourceItemsME.STRAW),
                                conditionsFromItem(ResourceItemsME.STRAW))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.FUR_BED, 1)
                        .pattern("FFF")
                        .pattern("PPP")
                        .input('F', ResourceItemsME.FUR)
                        .input('P', TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .criterion(hasItem(ResourceItemsME.FUR),
                                conditionsFromItem(ResourceItemsME.FUR))
                        .offerTo(exporter);

                createCenterSurroundRecipe(exporter, ResourceItemsME.SILVER_NUGGET, Items.TORCH, DecorativeItemsME.SILVER_LANTERN, 1);
                createCenterSurroundRecipe(exporter, ResourceItemsME.KHAZAD_STEEL_NUGGET, Items.TORCH, DecorativeItemsME.DWARVEN_LANTERN, 1);
                createCenterSurroundRecipe(exporter, ResourceItemsME.EDHEL_STEEL_NUGGET, Items.TORCH, DecorativeItemsME.ELVEN_LANTERN, 1);
                createCenterSurroundRecipe(exporter, ResourceItemsME.STEEL_NUGGET, Items.TORCH, DecorativeItemsME.TREATED_STEEL_LANTERN, 1);
                createCenterSurroundRecipe(exporter, ResourceItemsME.CRUDE_NUGGET, Items.TORCH, DecorativeItemsME.CRUDE_LANTERN, 1);
                createCenterSurroundRecipe(exporter, ResourceItemsME.LEAD_NUGGET, Items.TORCH, DecorativeItemsME.LEAD_LANTERN, 1);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, DecorativeItemsME.CRYSTAL_LAMP, 1)
                        .pattern("NGN")
                        .pattern("GLG")
                        .pattern("NIN")
                        .input('N', ResourceItemsME.BRONZE_NUGGET)
                        .input('I', ResourceItemsME.BRONZE_INGOT)
                        .input('L', TagKey.of(RegistryKeys.ITEM, Identifier.of("candles")))
                        .input('G', ResourceItemsME.QUARTZ_SHARD)
                        .criterion(hasItem(ResourceItemsME.QUARTZ_SHARD),
                                conditionsFromItem(ResourceItemsME.QUARTZ_SHARD))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, DecorativeItemsME.SCONCE, 4)
                        .pattern("NTN")
                        .pattern(" I ")
                        .input('N', TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "steel_nuggets")))
                        .input('I', TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "steel_ingots")))
                        .input('T', Items.TORCH)
                        .criterion(hasItem(Items.TORCH),
                                conditionsFromItem(Items.TORCH))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, DecorativeItemsME.GILDED_SCONCE, 4)
                        .pattern("NTN")
                        .pattern(" I ")
                        .input('N', Items.GOLD_NUGGET)
                        .input('I', Items.GOLD_INGOT)
                        .input('T', Items.TORCH)
                        .criterion(hasItem(Items.TORCH),
                                conditionsFromItem(Items.TORCH))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, DecorativeItemsME.ORCISH_SCONCE, 2)
                        .pattern("NTN")
                        .pattern(" S ")
                        .input('N', ResourceItemsME.CRUDE_NUGGET)
                        .input('S', Items.STICK)
                        .input('T', Items.TORCH)
                        .criterion(hasItem(Items.TORCH),
                                conditionsFromItem(Items.TORCH))
                        .offerTo(exporter);

                createWoodStoolRecipe(exporter, GenericBlockSets.TREATED_WOOD_PLANKS.blockSet.base().asItem(), ModDecorativeBlocks.TREATED_WOOD_STOOL);
                createWoodBenchRecipe(exporter, GenericBlockSets.TREATED_WOOD_PLANKS.blockSet.base().asItem(), ModDecorativeBlocks.TREATED_WOOD_BENCH);
                createWoodTableRecipe(exporter, GenericBlockSets.TREATED_WOOD_PLANKS.blockSet.base().asItem(), ModDecorativeBlocks.TREATED_WOOD_TABLE);
                createWoodChairRecipe(exporter, GenericBlockSets.TREATED_WOOD_PLANKS.blockSet.base().asItem(), ModDecorativeBlocks.TREATED_WOOD_CHAIR);
                createWoodLadderRecipe(exporter, GenericBlockSets.TREATED_WOOD_PLANKS.blockSet.base().asItem(), ModDecorativeBlocks.TREATED_WOOD_LADDER);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.LARCH_HOBBIT_DOOR, 1)
                        .pattern("LLL")
                        .pattern("LSL")
                        .pattern("LLL")
                        .input('S', TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "steel_ingots")))
                        .input('L', WoodBlockSets.LARCH_SET.planksBlocks.base())
                        .criterion(hasItem(WoodBlockSets.LARCH_SET.planksBlocks.base()),
                                conditionsFromItem(WoodBlockSets.LARCH_SET.planksBlocks.base()))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.SPRUCE_HOBBIT_DOOR, 1)
                        .pattern("LSL")
                        .pattern("SLL")
                        .pattern("LSL")
                        .input('S', TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "steel_ingots")))
                        .input('L', Items.SPRUCE_PLANKS)
                        .criterion(hasItem(Items.SPRUCE_PLANKS),
                                conditionsFromItem(Items.SPRUCE_PLANKS))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.BLUE_HOBBIT_DOOR, 1)
                        .pattern(" BG")
                        .pattern("BDG")
                        .pattern(" BG")
                        .input('D', ModDecorativeBlocks.LARCH_HOBBIT_DOOR)
                        .input('B', Items.BLUE_DYE)
                        .input('G', Items.GOLD_NUGGET)
                        .criterion(hasItem(ModDecorativeBlocks.LARCH_HOBBIT_DOOR),
                                conditionsFromItem(ModDecorativeBlocks.LARCH_HOBBIT_DOOR))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.GREEN_HOBBIT_DOOR, 1)
                        .pattern(" BG")
                        .pattern("BDG")
                        .pattern(" BG")
                        .input('D', ModDecorativeBlocks.LARCH_HOBBIT_DOOR)
                        .input('B', Items.GREEN_DYE)
                        .input('G', Items.GOLD_NUGGET)
                        .criterion(hasItem(ModDecorativeBlocks.LARCH_HOBBIT_DOOR),
                                conditionsFromItem(ModDecorativeBlocks.LARCH_HOBBIT_DOOR))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.LIGHT_BLUE_HOBBIT_DOOR, 1)
                        .pattern(" B ")
                        .pattern("BDB")
                        .pattern(" B ")
                        .input('D', ModDecorativeBlocks.LARCH_HOBBIT_DOOR)
                        .input('B', Items.LIGHT_BLUE_DYE)
                        .criterion(hasItem(ModDecorativeBlocks.LARCH_HOBBIT_DOOR),
                                conditionsFromItem(ModDecorativeBlocks.LARCH_HOBBIT_DOOR))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.RED_HOBBIT_DOOR, 1)
                        .pattern(" BG")
                        .pattern("BDG")
                        .pattern(" BG")
                        .input('D', ModDecorativeBlocks.LARCH_HOBBIT_DOOR)
                        .input('B', Items.RED_DYE)
                        .input('G', Items.GOLD_NUGGET)
                        .criterion(hasItem(ModDecorativeBlocks.LARCH_HOBBIT_DOOR),
                                conditionsFromItem(ModDecorativeBlocks.LARCH_HOBBIT_DOOR))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.YELLOW_HOBBIT_DOOR, 1)
                        .pattern(" BG")
                        .pattern("BDG")
                        .pattern(" BG")
                        .input('D', ModDecorativeBlocks.LARCH_HOBBIT_DOOR)
                        .input('B', Items.YELLOW_DYE)
                        .input('G', Items.GOLD_NUGGET)
                        .criterion(hasItem(ModDecorativeBlocks.LARCH_HOBBIT_DOOR),
                                conditionsFromItem(ModDecorativeBlocks.LARCH_HOBBIT_DOOR))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.TALL_BLACK_PINE_DOOR, 1)
                        .pattern("SP")
                        .pattern("PP")
                        .pattern("SP")
                        .input('S', TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "steel_nuggets")))
                        .input('P', WoodBlockSets.BLACK_PINE_SET.planksBlocks.base())
                        .criterion(hasItem(WoodBlockSets.BLACK_PINE_SET.planksBlocks.base()),
                                conditionsFromItem(WoodBlockSets.BLACK_PINE_SET.planksBlocks.base()))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.OAK_STABLE_DOOR, 1)
                        .pattern("SPP")
                        .pattern("PPP")
                        .pattern("SPP")
                        .input('S', TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "steel_nuggets")))
                        .input('P', Items.OAK_PLANKS)
                        .criterion(hasItem(Items.OAK_PLANKS),
                                conditionsFromItem(Items.OAK_PLANKS))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.REINFORCED_BLACK_PINE_DOOR, 1)
                        .pattern("SPP")
                        .pattern("SPS")
                        .pattern("SPP")
                        .input('S', TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "steel_ingots")))
                        .input('P', WoodBlockSets.BLACK_PINE_SET.planksBlocks.base())
                        .criterion(hasItem(WoodBlockSets.BLACK_PINE_SET.planksBlocks.base()),
                                conditionsFromItem(WoodBlockSets.BLACK_PINE_SET.planksBlocks.base()))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.REINFORCED_SPRUCE_DOOR, 1)
                        .pattern("SPP")
                        .pattern("SPS")
                        .pattern("SPP")
                        .input('S', TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "steel_ingots")))
                        .input('P', Items.SPRUCE_PLANKS)
                        .criterion(hasItem(Items.SPRUCE_PLANKS),
                                conditionsFromItem(Items.SPRUCE_PLANKS))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.SIMPLE_LARCH_GATE, 1)
                        .pattern("SPP")
                        .pattern("PPP")
                        .pattern("SPP")
                        .input('S', TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "steel_nuggets")))
                        .input('P', WoodBlockSets.LARCH_SET.planksBlocks.base())
                        .criterion(hasItem(WoodBlockSets.LARCH_SET.planksBlocks.base()),
                                conditionsFromItem(WoodBlockSets.LARCH_SET.planksBlocks.base()))
                        .offerTo(exporter);

                offerStonecuttingRecipe(RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.RICKETY_SIMPLE_LARCH_DOOR, ModDecorativeBlocks.SIMPLE_LARCH_GATE);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.SPRUCE_STABLE_DOOR, 1)
                        .pattern("SPP")
                        .pattern("PPP")
                        .pattern("SPP")
                        .input('S', TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "steel_nuggets")))
                        .input('P', Items.SPRUCE_PLANKS)
                        .criterion(hasItem(Items.SPRUCE_PLANKS),
                                conditionsFromItem(Items.SPRUCE_PLANKS))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.LARGE_STURDY_DOOR, 1)
                        .pattern("SPP")
                        .pattern("PPP")
                        .pattern("SPP")
                        .input('S', TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "steel_ingots")))
                        .input('P', TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .criterion(hasItem(Items.OAK_PLANKS),
                                conditionsFromItem(Items.OAK_PLANKS))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.LARGE_BEECH_FENCE_GATE, 1)
                        .pattern("FF")
                        .input('F', WoodBlockSets.BEECH_SET.planksBlocks.gate())
                        .criterion(hasItem(WoodBlockSets.BEECH_SET.planksBlocks.gate()),
                                conditionsFromItem(WoodBlockSets.BEECH_SET.planksBlocks.gate()))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.GREAT_GONDORIAN_GATE, 1)
                        .pattern("LCL")
                        .pattern("CCS")
                        .pattern("LCL")
                        .input('L', WoodBlockSets.BLACK_LEBETHRON_SET.planksBlocks.base())
                        .input('C', Items.OXIDIZED_COPPER)
                        .input('S', ResourceItemsME.STEEL_INGOT)
                        .criterion(hasItem(Items.OXIDIZED_COPPER),
                                conditionsFromItem(Items.OXIDIZED_COPPER))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.GREAT_DWARVEN_GATE, 1)
                        .pattern("BTB")
                        .pattern("BTS")
                        .pattern("BTB")
                        .input('B', ResourceItemsME.BRONZE_INGOT)
                        .input('T', GenericBlockSets.TREATED_WOOD.blockSet.base())
                        .input('S', ResourceItemsME.KHAZAD_STEEL_INGOT)
                        .criterion(hasItem(ResourceItemsME.BRONZE_INGOT),
                                conditionsFromItem(ResourceItemsME.BRONZE_INGOT))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.VARNISHED_DWARVEN_DOOR, 1)
                        .pattern("TNT")
                        .pattern("TTS")
                        .pattern("TNT")
                        .input('N', ResourceItemsME.STEEL_NUGGET)
                        .input('T', GenericBlockSets.TREATED_WOOD.blockSet.base())
                        .input('S', ResourceItemsME.KHAZAD_STEEL_INGOT)
                        .criterion(hasItem(ResourceItemsME.BRONZE_INGOT),
                                conditionsFromItem(ResourceItemsME.BRONZE_INGOT))
                        .offerTo(exporter);

                offerStonecuttingRecipe(RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.RUINED_DWARVEN_DOOR, ModDecorativeBlocks.VARNISHED_DWARVEN_DOOR);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.HIDDEN_DWARVEN_DOOR, 1)
                        .pattern("SSG")
                        .pattern("GDL")
                        .pattern("DSS")
                        .input('L', Items.LEVER)
                        .input('G', StoneBlockSets.DOLOMITE_SET.smoothBlocks.base())
                        .input('D', StoneBlockSets.DOLOMITE_SET.baseBlocks.base())
                        .input('S', Items.STONE)
                        .criterion(hasItem(StoneBlockSets.DOLOMITE_SET.smoothBlocks.base()),
                                conditionsFromItem(StoneBlockSets.DOLOMITE_SET.smoothBlocks.base()))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.GREAT_ELVEN_GATE, 1)
                        .pattern("BTB")
                        .pattern("BTS")
                        .pattern("BTB")
                        .input('B', Items.CYAN_DYE)
                        .input('T', GenericBlockSets.TREATED_WOOD.blockSet.base())
                        .input('S', ResourceItemsME.EDHEL_STEEL_INGOT)
                        .criterion(hasItem(GenericBlockSets.TREATED_WOOD.blockSet.base()),
                                conditionsFromItem(GenericBlockSets.TREATED_WOOD.blockSet.base()))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.GREAT_ORCISH_GATE, 1)
                        .pattern("SSS")
                        .pattern("SNS")
                        .pattern("NNN")
                        .input('N', ModBlocks.BURZUM_STEEL_BLOCK)
                        .input('S', ResourceItemsME.BURZUM_STEEL_INGOT)
                        .criterion(hasItem(ResourceItemsME.BURZUM_STEEL_INGOT),
                                conditionsFromItem(ResourceItemsME.BURZUM_STEEL_INGOT))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModBlocks.TURF, 4)
                        .pattern("MM")
                        .pattern("MD")
                        .input('M', Items.MOSS_BLOCK)
                        .input('D', Items.DIRT)
                        .criterion(hasItem(Items.MOSS_BLOCK),
                                conditionsFromItem(Items.MOSS_BLOCK))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModBlocks.GRASSY_DIRT, 4)
                        .pattern("DM")
                        .pattern("MD")
                        .input('M', Items.MOSS_BLOCK)
                        .input('D', Items.DIRT)
                        .criterion(hasItem(Items.MOSS_BLOCK),
                                conditionsFromItem(Items.MOSS_BLOCK))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModBlocks.GRASSY_LOAM, 4)
                        .pattern("DM")
                        .pattern("MD")
                        .input('M', Items.MOSS_BLOCK)
                        .input('D', ModBlocks.LOAM)
                        .criterion(hasItem(Items.MOSS_BLOCK),
                                conditionsFromItem(Items.MOSS_BLOCK))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModBlocks.GRASSY_SILT, 4)
                        .pattern("DM")
                        .pattern("MD")
                        .input('M', Items.MOSS_BLOCK)
                        .input('D', ModBlocks.SILT)
                        .criterion(hasItem(Items.MOSS_BLOCK),
                                conditionsFromItem(Items.MOSS_BLOCK))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModBlocks.PEBBLED_GRASS, 4)
                        .pattern("DM")
                        .pattern("MD")
                        .input('M', Items.MOSS_BLOCK)
                        .input('D', TagKey.of(RegistryKeys.ITEM, Identifier.of("stone_crafting_materials")))
                        .criterion(hasItem(Items.MOSS_BLOCK),
                                conditionsFromItem(Items.MOSS_BLOCK))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModBlocks.SNOWY_DIRT, 4)
                        .pattern("DS")
                        .pattern("SD")
                        .input('D', Items.DIRT)
                        .input('S', Items.SNOW_BLOCK)
                        .criterion(hasItem(Items.DIRT),
                                conditionsFromItem(Items.DIRT))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModBlocks.COBBLY_DIRT, 4)
                        .pattern("DC")
                        .pattern("CD")
                        .input('D', Items.DIRT)
                        .input('C', TagKey.of(RegistryKeys.ITEM, Identifier.of("stone_crafting_materials")))
                        .criterion(hasItem(Items.DIRT),
                                conditionsFromItem(Items.DIRT))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModBlocks.COBBLY_ASHEN_DIRT, 4)
                        .pattern("DC")
                        .pattern("CD")
                        .input('D', ModBlocks.ASHEN_DIRT)
                        .input('C', StoneBlockSets.ASHENSTONE_SET.cobblestoneBlocks.base())
                        .criterion(hasItem(Items.DIRT),
                                conditionsFromItem(Items.DIRT))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModBlocks.DIRTY_ROOTS, 2)
                        .pattern(" R ")
                        .pattern("RDR")
                        .pattern(" R ")
                        .input('D', Items.ROOTED_DIRT)
                        .input('R', Items.HANGING_ROOTS)
                        .criterion(hasItem(Items.ROOTED_DIRT),
                                conditionsFromItem(Items.ROOTED_DIRT))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.WATERING_CAN, 1)
                        .pattern(" N ")
                        .pattern("NII")
                        .pattern(" II")
                        .input('N', ResourceItemsME.TIN_NUGGET)
                        .input('I', ResourceItemsME.TIN_INGOT)
                        .criterion(hasItem(ResourceItemsME.TIN_INGOT),
                                conditionsFromItem(ResourceItemsME.TIN_INGOT))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.WOODEN_BUCKET, 1)
                        .pattern(" R ")
                        .pattern("P P")
                        .pattern(" P ")
                        .input('R', ModDecorativeBlocks.ROPE)
                        .input('P', TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .criterion(hasItem(ModDecorativeBlocks.ROPE),
                                conditionsFromItem(ModDecorativeBlocks.ROPE))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.CRUDE_ROD, 1)
                        .pattern("S")
                        .pattern("S")
                        .pattern("S")
                        .input('S', ResourceItemsME.CRUDE_INGOT)
                        .criterion(hasItem(ResourceItemsME.CRUDE_INGOT),
                                conditionsFromItem(ResourceItemsME.CRUDE_INGOT))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.TREATED_STEEL_ROD, 1)
                        .pattern("S")
                        .pattern("S")
                        .pattern("S")
                        .input('S', TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "steel_ingots")))
                        .criterion(hasItem(ResourceItemsME.STEEL_INGOT),
                                conditionsFromItem(ResourceItemsME.STEEL_INGOT))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, Items.CHAIN, 4)
                        .pattern("N")
                        .pattern("I")
                        .pattern("N")
                        .input('N', TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "steel_ingots")))
                        .input('I', TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "steel_nuggets")))
                        .criterion(hasItem(ResourceItemsME.STEEL_INGOT),
                                conditionsFromItem(ResourceItemsME.STEEL_INGOT))
                        .offerTo(exporter, String.valueOf(Identifier.of(MiddleEarth.MOD_ID, Registries.ITEM.getId(Items.CHAIN).getPath() + "_alt")));

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.BRONZE_CHAIN, 4)
                        .pattern("N")
                        .pattern("I")
                        .pattern("N")
                        .input('N', ResourceItemsME.BRONZE_NUGGET)
                        .input('I', ResourceItemsME.BRONZE_INGOT)
                        .criterion(hasItem(ResourceItemsME.BRONZE_INGOT),
                                conditionsFromItem(ResourceItemsME.STEEL_INGOT))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.BRONZE_BROAD_CHAIN, 8)
                        .pattern("NN")
                        .pattern("II")
                        .pattern("NN")
                        .input('N', ResourceItemsME.BRONZE_NUGGET)
                        .input('I', ResourceItemsME.BRONZE_INGOT)
                        .criterion(hasItem(ResourceItemsME.BRONZE_INGOT),
                                conditionsFromItem(ResourceItemsME.STEEL_INGOT))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.CRUDE_CHAIN, 4)
                        .pattern("N")
                        .pattern("I")
                        .pattern("N")
                        .input('N', ResourceItemsME.CRUDE_NUGGET)
                        .input('I', ResourceItemsME.CRUDE_INGOT)
                        .criterion(hasItem(ResourceItemsME.CRUDE_INGOT),
                                conditionsFromItem(ResourceItemsME.STEEL_INGOT))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.CRUDE_BROAD_CHAIN, 8)
                        .pattern("NN")
                        .pattern("II")
                        .pattern("NN")
                        .input('N', ResourceItemsME.CRUDE_NUGGET)
                        .input('I', ResourceItemsME.CRUDE_INGOT)
                        .criterion(hasItem(ResourceItemsME.CRUDE_INGOT),
                                conditionsFromItem(ResourceItemsME.STEEL_INGOT))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.SPIKY_CHAIN, 4)
                        .pattern(" N ")
                        .pattern("NIN")
                        .pattern(" N ")
                        .input('I', TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "steel_ingots")))
                        .input('N', TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "steel_nuggets")))
                        .criterion(hasItem(ResourceItemsME.STEEL_INGOT),
                                conditionsFromItem(ResourceItemsME.STEEL_INGOT))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, ResourceItemsME.DWARVEN_KEY, 1)
                        .pattern("IN")
                        .input('N', ResourceItemsME.KHAZAD_STEEL_NUGGET)
                        .input('I', ResourceItemsME.KHAZAD_STEEL_INGOT)
                        .criterion(hasItem(ResourceItemsME.KHAZAD_STEEL_INGOT),
                                conditionsFromItem(ResourceItemsME.KHAZAD_STEEL_INGOT))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.CHIMNEY, 2)
                        .pattern(" B ")
                        .pattern(" B ")
                        .pattern("PPP")
                        .input('B', Items.BRICKS)
                        .input('P', StoneBlockSets.DOLOMITE_SET.polishedBlocks.base())
                        .criterion(hasItem(Items.BRICKS),
                                conditionsFromItem(Items.BRICKS))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.BIG_BRAZIER, 2)
                        .pattern("B B")
                        .pattern("BCB")
                        .pattern("SSS")
                        .input('B', ModBlocks.TREATED_STEEL_BARS)
                        .input('C', Items.CAMPFIRE)
                        .input('S', TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "steel_ingots")))
                        .criterion(hasItem(Items.CAMPFIRE),
                                conditionsFromItem(Items.CAMPFIRE))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.GILDED_BIG_BRAZIER, 2)
                        .pattern("B B")
                        .pattern("BCB")
                        .pattern("SSS")
                        .input('B', ModBlocks.GILDED_BARS)
                        .input('C', Items.CAMPFIRE)
                        .input('S', Items.GOLD_INGOT)
                        .criterion(hasItem(Items.CAMPFIRE),
                                conditionsFromItem(Items.CAMPFIRE))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.SMALL_BRAZIER, 2)
                        .pattern("BCB")
                        .pattern("SSS")
                        .input('B', ModBlocks.TREATED_STEEL_BARS)
                        .input('C', Items.CAMPFIRE)
                        .input('S', TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "steel_ingots")))
                        .criterion(hasItem(Items.CAMPFIRE),
                                conditionsFromItem(Items.CAMPFIRE))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.GILDED_SMALL_BRAZIER, 2)
                        .pattern("BCB")
                        .pattern("SSS")
                        .input('B', ModBlocks.GILDED_BARS)
                        .input('C', Items.CAMPFIRE)
                        .input('S', Items.GOLD_INGOT)
                        .criterion(hasItem(Items.CAMPFIRE),
                                conditionsFromItem(Items.CAMPFIRE))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.FIRE_BOWL, 2)
                        .pattern("SCS")
                        .pattern("SSS")
                        .input('C', Items.CAMPFIRE)
                        .input('S', TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "steel_ingots")))
                        .criterion(hasItem(Items.CAMPFIRE),
                                conditionsFromItem(Items.CAMPFIRE))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.BONFIRE, 1)
                        .pattern(" L ")
                        .pattern("LCL")
                        .input('C', Items.CAMPFIRE)
                        .input('L', TagKey.of(RegistryKeys.ITEM, Identifier.of("logs")))
                        .criterion(hasItem(Items.CAMPFIRE),
                                conditionsFromItem(Items.CAMPFIRE))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.GROUND_BOOK, 1)
                        .pattern("BSR")
                        .input('B', Items.BOOK)
                        .input('S', Items.STRING)
                        .input('R', Items.RED_DYE)
                        .criterion(hasItem(Items.BOOK),
                                conditionsFromItem(Items.BOOK))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.DWARVEN_GROUND_BOOK, 1)
                        .pattern("BG")
                        .input('B', Items.BOOK)
                        .input('G', Items.GOLD_NUGGET)
                        .criterion(hasItem(Items.BOOK),
                                conditionsFromItem(Items.BOOK))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.SMALL_CRATE, 1)
                        .pattern("SSS")
                        .pattern("PPP")
                        .input('S', TagKey.of(RegistryKeys.ITEM, Identifier.of("wooden_slabs")))
                        .input('P', TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .criterion(hasItem(Items.OAK_PLANKS),
                                conditionsFromItem(Items.OAK_PLANKS))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.THIN_BARREL, 1)
                        .pattern("VSV")
                        .pattern("V V")
                        .pattern("VSV")
                        .input('S', TagKey.of(RegistryKeys.ITEM, Identifier.of("wooden_slabs")))
                        .input('V', TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "wooden_vertical_slabs")))
                        .criterion(hasItem(Items.OAK_SLAB),
                                conditionsFromItem(Items.OAK_SLAB))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.LARCH_COFFER, 1)
                        .pattern("TLT")
                        .pattern("L L")
                        .pattern("LLL")
                        .input('T', ResourceItemsME.TIN_NUGGET)
                        .input('L', WoodBlockSets.LARCH_SET.planksBlocks.base())
                        .criterion(hasItem(WoodBlockSets.LARCH_SET.planksBlocks.base()),
                                conditionsFromItem(WoodBlockSets.LARCH_SET.planksBlocks.base()))
                        .offerTo(exporter);
                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.PINE_COFFER, 1)
                        .pattern("TLT")
                        .pattern("L L")
                        .pattern("LLL")
                        .input('T', ResourceItemsME.TIN_NUGGET)
                        .input('L', WoodBlockSets.PINE_SET.planksBlocks.base())
                        .criterion(hasItem(WoodBlockSets.PINE_SET.planksBlocks.base()),
                                conditionsFromItem(WoodBlockSets.PINE_SET.planksBlocks.base()))
                        .offerTo(exporter);
                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.SPRUCE_COFFER, 1)
                        .pattern("TLT")
                        .pattern("L L")
                        .pattern("LLL")
                        .input('T', ResourceItemsME.TIN_NUGGET)
                        .input('L', Blocks.SPRUCE_PLANKS)
                        .criterion(hasItem(Blocks.SPRUCE_PLANKS),
                                conditionsFromItem(Blocks.SPRUCE_PLANKS))
                        .offerTo(exporter);
                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.FIR_COFFER, 1)
                        .pattern("TLT")
                        .pattern("L L")
                        .pattern("LLL")
                        .input('T', ResourceItemsME.TIN_NUGGET)
                        .input('L', WoodBlockSets.FIR_SET.planksBlocks.base())
                        .criterion(hasItem(WoodBlockSets.FIR_SET.planksBlocks.base()),
                                conditionsFromItem(WoodBlockSets.FIR_SET.planksBlocks.base()))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, Items.BELL, 1)
                        .pattern("VSV")
                        .pattern("VGV")
                        .input('S', Items.STICK)
                        .input('V', StoneBlockSets.STONE_SET.baseBlocks.verticalSlab())
                        .input('G', Items.GOLD_INGOT)
                        .criterion(hasItem(Items.GOLD_INGOT),
                                conditionsFromItem(Items.GOLD_INGOT))
                        .offerTo(exporter);

                ShapelessRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModNatureBlocks.STICKY_SNOW, 8)
                        .input(Items.SNOWBALL, 8)
                        .input(Items.WATER_BUCKET, 1)
                        .criterion(hasItem(Items.SNOWBALL),
                                conditionsFromItem(Items.SNOWBALL))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModNatureBlocks.STICKY_ICE, 4)
                        .pattern("II")
                        .pattern("II")
                        .input('I', Items.ICE)
                        .criterion(hasItem(Items.ICE),
                                conditionsFromItem(Items.ICE))
                        .offerTo(exporter);

                createBannerPatternRecipe(exporter, ResourceItemsME.PIPEWEED, ResourceItemsME.PIPEWEED_BANNER_PATTERN);
                createBannerPatternRecipe(exporter, ModNatureBlocks.LEBETHRON_SAPLING.asItem(), ResourceItemsME.GONDOR_BANNER_PATTERN);
                //createBannerPatternRecipe(exporter, WoodBlockSets.MALLORN.sapling().asItem(), ResourceItemsME.LOTHLORIEN_BANNER_PATTERN);
                createBannerPatternRecipe(exporter, Items.MAGMA_BLOCK, ResourceItemsME.MORDOR_BANNER_PATTERN);
                createBannerPatternRecipe(exporter, Items.HAY_BLOCK, ResourceItemsME.ROHAN_BANNER_PATTERN);
                createBannerPatternRecipe(exporter, Items.BONE, ResourceItemsME.MISTY_MOUNTAINS_ORCS_BANNER_PATTERN);
                createBannerPatternRecipe(exporter, Items.WHITE_DYE, ResourceItemsME.ISENGARD_BANNER_PATTERN);
                createBannerPatternRecipe(exporter, ToolItemsME.DWARVEN_SMITHING_HAMMER, ResourceItemsME.ANVIL_BANNER_PATTERN);
                createBannerPatternRecipe(exporter, ResourceItemsME.BRONZE_INGOT, ResourceItemsME.BELL_BANNER_PATTERN);
                createBannerPatternRecipe(exporter, Items.GOLD_NUGGET, ResourceItemsME.DWARF_CROWN_BANNER_PATTERN);
                createBannerPatternRecipe(exporter, Items.SPIDER_EYE, ResourceItemsME.SPIDER_BANNER_PATTERN);
                createBannerPatternRecipe(exporter, Items.BOW, ResourceItemsME.BOW_BANNER_PATTERN);
                createBannerPatternRecipe(exporter, Items.OAK_LEAVES, ResourceItemsME.OAK_LEAF_BANNER_PATTERN);

                createBrickRecipe(exporter, ModBlocks.POINTED_DOLOMITE.asItem(), StoneBlockSets.DOLOMITE_SET.baseBlocks.base(), 1);
                createBrickRecipe(exporter, ModBlocks.POINTED_GALONN.asItem(), StoneBlockSets.GALONN_SET.baseBlocks.base(), 1);
                createBrickRecipe(exporter, ModBlocks.POINTED_IZHERABAN.asItem(), StoneBlockSets.IZHERABAN_SET.baseBlocks.base(), 1);
                createBrickRecipe(exporter, ModBlocks.POINTED_LIMESTONE.asItem(), StoneBlockSets.LIMESTONE_SET.baseBlocks.base(), 1);

                CookingRecipeJsonBuilder.createSmoking(Ingredient.ofTag(itemLookup.getOrThrow(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))), RecipeCategory.BUILDING_BLOCKS, WoodBlockSets.SCORCHED_SET.planksBlocks.base(), 0.35f, 100)
                        .criterion(hasItem(Items.OAK_PLANKS),
                                conditionsFromItem(Items.OAK_PLANKS)).offerTo(exporter, String.valueOf(Identifier.of(MiddleEarth.MOD_ID, Registries.BLOCK.getId(WoodBlockSets.SCORCHED_SET.planksBlocks.base()).getPath() + "_from_smoking")));
                CookingRecipeJsonBuilder.createSmoking(Ingredient.ofTag(itemLookup.getOrThrow(TagKey.of(RegistryKeys.ITEM, Identifier.of("logs")))), RecipeCategory.BUILDING_BLOCKS, WoodBlockSets.SCORCHED_SET.logBlocks.log(), 0.35f, 100)
                        .criterion(hasItem(Items.OAK_LOG),
                                conditionsFromItem(Items.OAK_LOG)).offerTo(exporter, String.valueOf(Identifier.of(MiddleEarth.MOD_ID, Registries.BLOCK.getId(WoodBlockSets.SCORCHED_SET.logBlocks.log()).getPath() + "_from_smoking")));

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModNatureBlocks.SHORT_ICICLES, 4)
                        .pattern("III")
                        .pattern(" I ")
                        .input('I', Items.ICE)
                        .criterion(hasItem(Items.ICE),
                                conditionsFromItem(Items.ICE))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModNatureBlocks.DROOPING_ICICLES, 4)
                        .pattern("III")
                        .pattern("III")
                        .pattern(" I ")
                        .input('I', Items.ICE)
                        .criterion(hasItem(Items.ICE),
                                conditionsFromItem(Items.ICE))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, Items.BUCKET, 1)
                        .pattern("T T")
                        .pattern("T T")
                        .pattern(" T ")
                        .input('T', ResourceItemsME.TIN_INGOT)
                        .criterion(hasItem(ResourceItemsME.TIN_INGOT),
                                conditionsFromItem(ResourceItemsME.TIN_INGOT))
                        .offerTo(exporter, String.valueOf(Identifier.of(MiddleEarth.MOD_ID, Registries.ITEM.getId(Items.BUCKET).getPath() + "_alt")));

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, Items.CAULDRON, 1)
                        .pattern("T T")
                        .pattern("T T")
                        .pattern("TBT")
                        .input('T', ResourceItemsME.TIN_INGOT)
                        .input('B', ModBlocks.TIN_BLOCK)
                        .criterion(hasItem(ResourceItemsME.TIN_INGOT),
                                conditionsFromItem(ResourceItemsME.TIN_INGOT))
                        .offerTo(exporter, String.valueOf(Identifier.of(MiddleEarth.MOD_ID, Registries.ITEM.getId(Items.CAULDRON).getPath() + "_alt")));

                createCenterSurroundRecipe(exporter, Blocks.TUFF.asItem(), Items.COPPER_INGOT, StoneBlockSets.GREEN_TUFF_SET.baseBlocks.base().asItem(), 8);

                offerStonecuttingRecipe(RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.BROWN_JUG, Items.CLAY);
                offerStonecuttingRecipe(RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.LARGE_JUG, Items.CLAY);
                offerStonecuttingRecipe(RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.GRAY_POT, Items.CLAY);

                offerStonecuttingRecipe(RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.BROWN_JAR, Items.CLAY);
                offerStonecuttingRecipe(RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.CLAY_JAR, Items.CLAY);
                offerStonecuttingRecipe(RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.GRAY_JAR, Items.CLAY);

                offerStonecuttingRecipe(RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.AMPHORA, Items.CLAY);
                offerStonecuttingRecipe(RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.BROWN_AMPHORA, Items.CLAY);
                offerStonecuttingRecipe(RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.GRAY_VASE, Items.CLAY);

                offerStonecuttingRecipe(RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.BROWN_FAT_POT, Items.CLAY);
                offerStonecuttingRecipe(RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.FAT_POT, Items.CLAY);
                offerStonecuttingRecipe(RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.GRAY_FAT_POT, Items.CLAY);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.POT_OF_GOLD, 1)
                        .pattern(" G ")
                        .pattern("GGG")
                        .pattern(" P ")
                        .input('P', ModDecorativeBlocks.FAT_POT)
                        .input('G', ResourceItemsME.GOLD_COIN)
                        .criterion(hasItem(ResourceItemsME.GOLD_COIN),
                                conditionsFromItem(ResourceItemsME.GOLD_COIN))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModNatureBlocks.AZALEA_FLOWER_GROWTH.asItem(), 8)
                        .pattern("lll")
                        .pattern("lll")
                        .input('l', Items.FLOWERING_AZALEA_LEAVES)
                        .criterion(hasItem(Items.FLOWERING_AZALEA_LEAVES),
                                conditionsFromItem(Items.FLOWERING_AZALEA_LEAVES))
                        .offerTo(exporter);
                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModNatureBlocks.DRY_GROWTH.asItem(), 4)
                        .pattern("sss")
                        .pattern("sss")
                        .input('s', Items.STICK)
                        .criterion(hasItem(Items.STICK),
                                conditionsFromItem(Items.STICK))
                        .offerTo(exporter);
                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModNatureBlocks.GREEN_GROWTH.asItem(), 8)
                        .pattern("lll")
                        .pattern("lll")
                        .input('l', TagKey.of(RegistryKeys.ITEM, Identifier.of("leaves")))
                        .criterion(hasItem(Items.OAK_LEAVES),
                                conditionsFromItem(Items.OAK_LEAVES))
                        .offerTo(exporter);
                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModNatureBlocks.IVY_GROWTH.asItem(), 6)
                        .pattern("sls")
                        .pattern("sls")
                        .input('s', Items.STICK)
                        .input('l', TagKey.of(RegistryKeys.ITEM, Identifier.of("leaves")))
                        .criterion(hasItem(Items.OAK_LEAVES),
                                conditionsFromItem(Items.OAK_LEAVES))
                        .offerTo(exporter);
                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModNatureBlocks.LILAC_FLOWER_GROWTH.asItem(), 8)
                        .pattern("lfl")
                        .pattern("lfl")
                        .input('f', Items.LILAC)
                        .input('l', TagKey.of(RegistryKeys.ITEM, Identifier.of("leaves")))
                        .criterion(hasItem(Items.OAK_LEAVES),
                                conditionsFromItem(Items.OAK_LEAVES))
                        .offerTo(exporter);
                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModNatureBlocks.PINK_FLOWER_GROWTH.asItem(), 8)
                        .pattern("lfl")
                        .pattern("lfl")
                        .input('f', ModNatureBlocks.PINK_FLOWERS)
                        .input('l', TagKey.of(RegistryKeys.ITEM, Identifier.of("leaves")))
                        .criterion(hasItem(ModNatureBlocks.PINK_FLOWERS),
                                conditionsFromItem(ModNatureBlocks.PINK_FLOWERS))
                        .offerTo(exporter);
                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModNatureBlocks.RED_FLOWER_GROWTH.asItem(), 8)
                        .pattern("lfl")
                        .pattern("lfl")
                        .input('f', ModNatureBlocks.RED_FLOWERS)
                        .input('l', TagKey.of(RegistryKeys.ITEM, Identifier.of("leaves")))
                        .criterion(hasItem(ModNatureBlocks.RED_FLOWERS),
                                conditionsFromItem(ModNatureBlocks.RED_FLOWERS))
                        .offerTo(exporter);
                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModNatureBlocks.WHITE_FLOWER_GROWTH.asItem(), 8)
                        .pattern("lfl")
                        .pattern("lfl")
                        .input('f', ModNatureBlocks.WHITE_FLOWERS)
                        .input('l', TagKey.of(RegistryKeys.ITEM, Identifier.of("leaves")))
                        .criterion(hasItem(ModNatureBlocks.WHITE_FLOWERS),
                                conditionsFromItem(ModNatureBlocks.WHITE_FLOWERS))
                        .offerTo(exporter);
                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModNatureBlocks.YELLOW_FLOWER_GROWTH.asItem(), 8)
                        .pattern("lfl")
                        .pattern("lfl")
                        .input('f', ModNatureBlocks.YELLOW_FLOWERS)
                        .input('l', TagKey.of(RegistryKeys.ITEM, Identifier.of("leaves")))
                        .criterion(hasItem(ModNatureBlocks.YELLOW_FLOWERS),
                                conditionsFromItem(ModNatureBlocks.YELLOW_FLOWERS))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModNatureBlocks.FROZEN_GROWTH.asItem(), 8)
                        .pattern("sis")
                        .pattern("sis")
                        .input('i', ModNatureBlocks.STICKY_SNOW)
                        .input('s', ModNatureBlocks.DRY_GROWTH)
                        .criterion(hasItem(ModNatureBlocks.DRY_GROWTH),
                                conditionsFromItem(ModNatureBlocks.DRY_GROWTH))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.GOLDEN_CHALICE, 1)
                        .pattern("I")
                        .pattern("N")
                        .pattern("N")
                        .input('I', Items.GOLD_INGOT)
                        .input('N', Items.GOLD_NUGGET)
                        .criterion(hasItem(Items.GOLD_INGOT),
                                conditionsFromItem(Items.GOLD_INGOT))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.COPPER_TREASURE_HEAP_LAYER, 1)
                        .pattern("NNN")
                        .input('N', ResourceItemsME.COPPER_COIN)
                        .criterion(hasItem(ResourceItemsME.COPPER_COIN),
                                conditionsFromItem(ResourceItemsME.COPPER_COIN))
                        .offerTo(exporter);
                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.SILVER_TREASURE_HEAP_LAYER, 1)
                        .pattern("NNN")
                        .input('N', ResourceItemsME.SILVER_COIN)
                        .criterion(hasItem(ResourceItemsME.SILVER_COIN),
                                conditionsFromItem(ResourceItemsME.SILVER_COIN))
                        .offerTo(exporter);
                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.GOLD_TREASURE_HEAP_LAYER, 1)
                        .pattern("NNN")
                        .input('N', ResourceItemsME.GOLD_COIN)
                        .criterion(hasItem(ResourceItemsME.GOLD_COIN),
                                conditionsFromItem(ResourceItemsME.GOLD_COIN))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.COPPER_COIN_PILE, 1)
                        .pattern("NN")
                        .pattern("NN")
                        .input('N', ResourceItemsME.COPPER_COIN)
                        .criterion(hasItem(ResourceItemsME.COPPER_COIN),
                                conditionsFromItem(ResourceItemsME.COPPER_COIN))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.SILVER_COIN_PILE, 1)
                        .pattern("NN")
                        .pattern("NN")
                        .input('N', ResourceItemsME.SILVER_COIN)
                        .criterion(hasItem(ResourceItemsME.SILVER_COIN),
                                conditionsFromItem(ResourceItemsME.SILVER_COIN))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.GOLD_COIN_PILE, 1)
                        .pattern("NN")
                        .pattern("NN")
                        .input('N', ResourceItemsME.GOLD_COIN)
                        .criterion(hasItem(ResourceItemsME.GOLD_COIN),
                                conditionsFromItem(ResourceItemsME.GOLD_COIN))
                        .offerTo(exporter);

                ShapelessRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, ResourceItemsME.COPPER_COIN, 3)
                        .input(ModDecorativeBlocks.COPPER_TREASURE_HEAP_LAYER)
                        .criterion(hasItem(ModDecorativeBlocks.COPPER_TREASURE_HEAP_LAYER),
                                conditionsFromItem(ModDecorativeBlocks.COPPER_TREASURE_HEAP_LAYER))
                        .offerTo(exporter, String.valueOf(Identifier.of(MiddleEarth.MOD_ID, "copper_coin_from_treasure")));

                ShapelessRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, ResourceItemsME.SILVER_COIN, 3)
                        .input(ModDecorativeBlocks.SILVER_TREASURE_HEAP_LAYER)
                        .criterion(hasItem(ModDecorativeBlocks.SILVER_TREASURE_HEAP_LAYER),
                                conditionsFromItem(ModDecorativeBlocks.SILVER_TREASURE_HEAP_LAYER))
                        .offerTo(exporter, String.valueOf(Identifier.of(MiddleEarth.MOD_ID, "silver_coin_from_treasure")));

                ShapelessRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, ResourceItemsME.GOLD_COIN, 3)
                        .input(ModDecorativeBlocks.GOLD_TREASURE_HEAP_LAYER)
                        .criterion(hasItem(ModDecorativeBlocks.GOLD_TREASURE_HEAP_LAYER),
                                conditionsFromItem(ModDecorativeBlocks.GOLD_TREASURE_HEAP_LAYER))
                        .offerTo(exporter, String.valueOf(Identifier.of(MiddleEarth.MOD_ID, "gold_nugget_from_treasure")));

                ShapelessRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, ResourceItemsME.COPPER_COIN, 4)
                        .input(ModDecorativeBlocks.COPPER_COIN_PILE)
                        .criterion(hasItem(ModDecorativeBlocks.COPPER_COIN_PILE),
                                conditionsFromItem(ModDecorativeBlocks.COPPER_COIN_PILE))
                        .offerTo(exporter, String.valueOf(Identifier.of(MiddleEarth.MOD_ID, "copper_coin_from_pile")));

                ShapelessRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, ResourceItemsME.SILVER_COIN, 4)
                        .input(ModDecorativeBlocks.SILVER_COIN_PILE)
                        .criterion(hasItem(ModDecorativeBlocks.SILVER_COIN_PILE),
                                conditionsFromItem(ModDecorativeBlocks.SILVER_COIN_PILE))
                        .offerTo(exporter, String.valueOf(Identifier.of(MiddleEarth.MOD_ID, "silver_coin_from_pile")));

                ShapelessRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, ResourceItemsME.GOLD_COIN, 4)
                        .input(ModDecorativeBlocks.GOLD_COIN_PILE)
                        .criterion(hasItem(ModDecorativeBlocks.GOLD_COIN_PILE),
                                conditionsFromItem(ModDecorativeBlocks.GOLD_COIN_PILE))
                        .offerTo(exporter, String.valueOf(Identifier.of(MiddleEarth.MOD_ID, "gold_nugget_from_pile")));

                ShapelessRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModNatureBlocks.SHORT_BULRUSH, 2)
                        .input(ModNatureBlocks.TALL_BULRUSH)
                        .criterion(hasItem(ModNatureBlocks.TALL_BULRUSH),
                                conditionsFromItem(ModNatureBlocks.TALL_BULRUSH))
                        .offerTo(exporter);

                ShapelessRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModNatureBlocks.SHORT_REEDS, 2)
                        .input(ResourceItemsME.REEDS)
                        .criterion(hasItem(ResourceItemsME.REEDS),
                                conditionsFromItem(ResourceItemsME.REEDS))
                        .offerTo(exporter);

                ShapelessRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModNatureBlocks.SHORT_DEAD_RUSHES, 2)
                        .input(ModNatureBlocks.DEAD_RUSHES)
                        .criterion(hasItem(ModNatureBlocks.DEAD_RUSHES),
                                conditionsFromItem(ModNatureBlocks.DEAD_RUSHES))
                        .offerTo(exporter);

                ShapelessRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModNatureBlocks.SHORT_RUSHES, 2)
                        .input(ModNatureBlocks.RUSHES)
                        .criterion(hasItem(ModNatureBlocks.RUSHES),
                                conditionsFromItem(ModNatureBlocks.RUSHES))
                        .offerTo(exporter);

                ShapelessRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModNatureBlocks.SHORT_CATTAILS, 2)
                        .input(ModNatureBlocks.TALL_CATTAILS)
                        .criterion(hasItem(ModNatureBlocks.TALL_CATTAILS),
                                conditionsFromItem(ModNatureBlocks.TALL_CATTAILS))
                        .offerTo(exporter);

                ShapelessRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, ResourceItemsME.GOLD_COIN, 4)
                        .input(ModDecorativeBlocks.POT_OF_GOLD)
                        .criterion(hasItem(ModDecorativeBlocks.POT_OF_GOLD),
                                conditionsFromItem(ModDecorativeBlocks.POT_OF_GOLD))
                        .offerTo(exporter, String.valueOf(Identifier.of(MiddleEarth.MOD_ID, "gold_from_pot_of_gold")));

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, WeaponItemsME.HELD_BANNER, 1)
                        .pattern("WWW")
                        .pattern("WWW")
                        .pattern("WSW")
                        .input('W', TagKey.of(RegistryKeys.ITEM, Identifier.of("wool")))
                        .input('S', Items.STICK)
                        .criterion(hasItem(ResourceItemsME.GOLD_COIN),
                                conditionsFromItem(ResourceItemsME.GOLD_COIN))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, StoneBlockSets.SLATE_SET.baseBlocks.base(), 4)
                        .pattern("DS")
                        .pattern("SD")
                        .input('D', Items.DEEPSLATE)
                        .input('S', Items.STONE)
                        .criterion(hasItem(Items.DEEPSLATE),
                                conditionsFromItem(Items.DEEPSLATE))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, StoneBlockSets.BLUE_TUFF_SET.baseBlocks.base(), 4)
                        .pattern("TG")
                        .pattern("GT")
                        .input('T', Items.TUFF)
                        .input('G', StoneBlockSets.KHAGALABAN_SET.baseBlocks.base())
                        .criterion(hasItem(StoneBlockSets.KHAGALABAN_SET.baseBlocks.base()),
                                conditionsFromItem(StoneBlockSets.KHAGALABAN_SET.baseBlocks.base()))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, StoneBlockSets.HEMATITE_SET.baseBlocks.base(), 4)
                        .pattern("SI")
                        .pattern("IS")
                        .input('S', Items.STONE)
                        .input('I', StoneBlockSets.IRONSTONE_SET.baseBlocks.base())
                        .criterion(hasItem(StoneBlockSets.IRONSTONE_SET.baseBlocks.base()),
                                conditionsFromItem(StoneBlockSets.IRONSTONE_SET.baseBlocks.base()))
                        .offerTo(exporter);

                createSmokingRecipe(exporter, Items.SHORT_GRASS, ModNatureBlocks.SCORCHED_GRASS.asItem());
                createSmokingRecipe(exporter, ModNatureBlocks.GRASS_TUFT.asItem(), ModNatureBlocks.SCORCHED_TUFT.asItem());
                createSmokingRecipe(exporter, ModNatureBlocks.GREEN_SHRUB.asItem(), ModNatureBlocks.SCORCHED_SHRUB.asItem());
                //endregion

                //region SMOKING-ONLY
                createSmokingRecipe(exporter, ResourceItemsME.PIPEWEED, ResourceItemsME.DRIED_PIPEWEED);
                //endregion

                ComplexRecipeJsonBuilder.create(CustomItemDecorationRecipe::new).offerTo(exporter, "custom_shield_decoration");
            }

            //region Refactored Methods
            private void createStoneSetRecipes(BlockRecordTypes.RegularSet base) {
                if (base != null) {
                    offerSlabRecipe(RecipeCategory.BUILDING_BLOCKS, base.slab(), base.base());
                    offerStonecuttingRecipe(RecipeCategory.BUILDING_BLOCKS, base.slab(), base.base(), 2);
                    createVerticalSlabsRecipe(exporter, base.slab(), base.verticalSlab());
                    createSlabsFromVerticalRecipe(exporter, base.verticalSlab(), base.slab());
                    offerStonecuttingRecipe(RecipeCategory.BUILDING_BLOCKS, base.verticalSlab(), base.base(), 2);
                    createStairsRecipe(exporter, base.base(), base.stairs());
                    offerStonecuttingRecipe(RecipeCategory.BUILDING_BLOCKS, base.stairs(), base.base());
                    ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, base.wall(), 6)
                            .pattern("lll")
                            .pattern("lll")
                            .input('l', base.base())
                            .criterion(hasItem(base.base()),
                                    conditionsFromItem(base.base()))
                            .offerTo(exporter);
                    offerStonecuttingRecipe(RecipeCategory.BUILDING_BLOCKS, base.wall(), base.base());
                }
            }

            private void createStoneSetRecipes(BlockRecordTypes.BaseStoneSet base) {
                if (base != null) {
                    offerSlabRecipe(RecipeCategory.BUILDING_BLOCKS, base.slab(), base.base());
                    offerStonecuttingRecipe(RecipeCategory.BUILDING_BLOCKS, base.slab(), base.base(), 2);
                    createVerticalSlabsRecipe(exporter, base.slab(), base.verticalSlab());
                    createSlabsFromVerticalRecipe(exporter, base.verticalSlab(), base.slab());
                    offerStonecuttingRecipe(RecipeCategory.BUILDING_BLOCKS, base.verticalSlab(), base.base(), 2);
                    createStairsRecipe(exporter, base.base(), base.stairs());
                    offerStonecuttingRecipe(RecipeCategory.BUILDING_BLOCKS, base.stairs(), base.base());
                    ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, base.wall(), 6)
                            .pattern("lll")
                            .pattern("lll")
                            .input('l', base.base())
                            .criterion(hasItem(base.base()),
                                    conditionsFromItem(base.base()))
                            .offerTo(exporter);
                    offerStonecuttingRecipe(RecipeCategory.BUILDING_BLOCKS, base.wall(), base.base());
                }
            }

            private void createStoneSetRecipes(BlockRecordTypes.PillarSet base) {
                if (base != null) {
                    offerStonecuttingRecipe(RecipeCategory.BUILDING_BLOCKS, base.verticalSlab(), base.base(), 2);
                    ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, base.wall(), 6)
                            .pattern("lll")
                            .pattern("lll")
                            .input('l', base.base())
                            .criterion(hasItem(base.base()),
                                    conditionsFromItem(base.base()))
                            .offerTo(exporter);
                    offerStonecuttingRecipe(RecipeCategory.BUILDING_BLOCKS, base.wall(), base.base());
                }
            }

            //endregion

            //region BLOCK RECIPE METHODS
            private void createBrickRecipe(RecipeExporter exporter, Item input, Block output, int count) {
                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, output, count)
                        .pattern("ll")
                        .pattern("ll")
                        .input('l', input)
                        .criterion(hasItem(input),
                                conditionsFromItem(input))
                        .offerTo(exporter);
            }

            private void createPillarRecipe(RecipeExporter exporter, Block input, Block output, int count) {
                ShapedRecipeJsonBuilder.create(this.itemLookup, RecipeCategory.BUILDING_BLOCKS, output, count)
                        .pattern("l")
                        .pattern("l")
                        .pattern("l")
                        .input('l', input)
                        .criterion(hasItem(input),
                                conditionsFromItem(input))
                        .offerTo(exporter);
            }

            private void createChiseledRecipe(RecipeExporter exporter, Block input, Block output, int count) {
                ShapedRecipeJsonBuilder.create(this.itemLookup, RecipeCategory.BUILDING_BLOCKS, output, count)
                        .pattern("l")
                        .pattern("l")
                        .input('l', input)
                        .criterion(hasItem(input),
                                conditionsFromItem(input))
                        .offerTo(exporter);
            }

            private void createCutPolishedRecipe(RecipeExporter exporter, Block input, Block output, int count) {
                ShapedRecipeJsonBuilder.create(this.itemLookup, RecipeCategory.BUILDING_BLOCKS, output, count)
                        .pattern("l")
                        .pattern("l")
                        .input('l', input)
                        .criterion(hasItem(input),
                                conditionsFromItem(input))
                        .offerTo(exporter);
            }

            private void createMossyRecipe(RecipeExporter exporter, Block input, Block output) {
                ShapelessRecipeJsonBuilder.create(this.itemLookup, RecipeCategory.BUILDING_BLOCKS, output, 1)
                        .input(input)
                        .input(Items.VINE)
                        .criterion(hasItem(input),
                                conditionsFromItem(input))
                        .offerTo(exporter, String.valueOf(Identifier.of(MiddleEarth.MOD_ID, Registries.BLOCK.getId(output).getPath() + "_vine")));

                ShapelessRecipeJsonBuilder.create(this.itemLookup, RecipeCategory.BUILDING_BLOCKS, output, 1)
                        .input(input)
                        .input(Blocks.MOSS_BLOCK)
                        .criterion(hasItem(input),
                                conditionsFromItem(input))
                        .offerTo(exporter, String.valueOf(Identifier.of(MiddleEarth.MOD_ID, Registries.BLOCK.getId(output).getPath() + "_moss")));
            }

            private void createSmeltingRecipe(RecipeExporter exporter, Item input, Item output) {
                CookingRecipeJsonBuilder.createSmelting(Ingredient.ofItems(input), RecipeCategory.BUILDING_BLOCKS, output, 0.1f, 200)
                        .criterion(hasItem(input),
                                conditionsFromItem(input))
                        .offerTo(exporter);
            }

            private void createSmeltingRecipeIdentifier(RecipeExporter exporter, Item input, Item output) {
                CookingRecipeJsonBuilder.createSmelting(Ingredient.ofItems(input), RecipeCategory.BUILDING_BLOCKS, output, 0.1f, 200)
                        .criterion(hasItem(input),
                                conditionsFromItem(input))
                        .offerTo(exporter, String.valueOf(Identifier.of(MiddleEarth.MOD_ID, Registries.ITEM.getId(output).getPath() + "_from_smelting")));
            }

            private void createMeltBulkRecipe(RecipeExporter exporter, Item input, String output) {
                createMeltRecipe(exporter, input, output, 1, INGOT_LIQUID_VALUE);
                createMeltRecipe(exporter, input, output, 2, INGOT_LIQUID_VALUE);
                createMeltRecipe(exporter, input, output, 3, INGOT_LIQUID_VALUE);
                createMeltRecipe(exporter, input, output, 4, INGOT_LIQUID_VALUE);
            }

            private void createMeltRecipe(RecipeExporter exporter, Item input, String output, int ingots, int amount) {
                switch (ingots) {
                    case 1 -> AlloyRecipeJsonBuilder.createAlloyRecipe(this.itemLookup, RecipeCategory.MISC, output, amount)
                            .input(input)
                            .criterion(hasItem(input),
                                    conditionsFromItem(input))
                            .offerTo(exporter, String.valueOf(Identifier.of(MiddleEarth.MOD_ID, output + "_from_melting_1_" + Registries.ITEM.getId(input).getPath())));
                    case 2 -> AlloyRecipeJsonBuilder.createAlloyRecipe(this.itemLookup, RecipeCategory.MISC, output, amount * 2)
                            .input(input)
                            .input(input)
                            .criterion(hasItem(input),
                                    conditionsFromItem(input))
                            .offerTo(exporter, String.valueOf(Identifier.of(MiddleEarth.MOD_ID, output + "_from_melting_2_" + Registries.ITEM.getId(input).getPath())));
                    case 3 -> AlloyRecipeJsonBuilder.createAlloyRecipe(this.itemLookup, RecipeCategory.MISC, output, amount * 3)
                            .input(input)
                            .input(input)
                            .input(input)
                            .criterion(hasItem(input),
                                    conditionsFromItem(input))
                            .offerTo(exporter, String.valueOf(Identifier.of(MiddleEarth.MOD_ID, output + "_from_melting_3_" + Registries.ITEM.getId(input).getPath())));
                    case 4 -> AlloyRecipeJsonBuilder.createAlloyRecipe(this.itemLookup, RecipeCategory.MISC, output, amount * 4)
                            .input(input)
                            .input(input)
                            .input(input)
                            .input(input)
                            .criterion(hasItem(input),
                                    conditionsFromItem(input))
                            .offerTo(exporter, String.valueOf(Identifier.of(MiddleEarth.MOD_ID, output + "_from_melting_4_" + Registries.ITEM.getId(input).getPath())));
                }
            }

            private void createMeltBulkRecipeTag(RecipeExporter exporter, TagKey input, String output) {
                createMeltRecipeTag(exporter, input, output, 1, INGOT_LIQUID_VALUE);
                createMeltRecipeTag(exporter, input, output, 2, INGOT_LIQUID_VALUE);
                createMeltRecipeTag(exporter, input, output, 3, INGOT_LIQUID_VALUE);
                createMeltRecipeTag(exporter, input, output, 4, INGOT_LIQUID_VALUE);
            }

            private void createMeltRecipeTag(RecipeExporter exporter, TagKey input, String output, int ingots, int amount) {
                switch (ingots) {
                    case 1 -> AlloyRecipeJsonBuilder.createAlloyRecipe(this.itemLookup, RecipeCategory.MISC, output, amount)
                            .input(input)
                            .criterion(hasItem(DecorativeItemsME.FORGE),
                                    conditionsFromItem(DecorativeItemsME.FORGE))
                            .offerTo(exporter, String.valueOf(Identifier.of(MiddleEarth.MOD_ID, output + "_from_melting_1_" + input.id().getPath())));
                    case 2 -> AlloyRecipeJsonBuilder.createAlloyRecipe(this.itemLookup, RecipeCategory.MISC, output, amount * 2)
                            .input(input)
                            .input(input)
                            .criterion(hasItem(DecorativeItemsME.FORGE),
                                    conditionsFromItem(DecorativeItemsME.FORGE))
                            .offerTo(exporter, String.valueOf(Identifier.of(MiddleEarth.MOD_ID, output + "_from_melting_2_" + input.id().getPath())));
                    case 3 -> AlloyRecipeJsonBuilder.createAlloyRecipe(this.itemLookup, RecipeCategory.MISC, output, amount * 3)
                            .input(input)
                            .input(input)
                            .input(input)
                            .criterion(hasItem(DecorativeItemsME.FORGE),
                                    conditionsFromItem(DecorativeItemsME.FORGE))
                            .offerTo(exporter, String.valueOf(Identifier.of(MiddleEarth.MOD_ID, output + "_from_melting_3_" + input.id().getPath())));
                    case 4 -> AlloyRecipeJsonBuilder.createAlloyRecipe(this.itemLookup, RecipeCategory.MISC, output, amount * 4)
                            .input(input)
                            .input(input)
                            .input(input)
                            .input(input)
                            .criterion(hasItem(DecorativeItemsME.FORGE),
                                    conditionsFromItem(DecorativeItemsME.FORGE))
                            .offerTo(exporter, String.valueOf(Identifier.of(MiddleEarth.MOD_ID, output + "_from_melting_4_" + input.id().getPath())));
                }
            }

            private void createAnvilShapingRecipeTag(RecipeExporter exporter, TagKey input, Item output, int amount) {
                AnvilShapingRecipeJsonBuilder.createAnvilShapingRecipe(this.itemLookup, RecipeCategory.MISC, output, amount)
                        .input(input)
                        .criterion(hasItem(Items.COPPER_INGOT),
                                conditionsFromItem(Items.COPPER_INGOT))
                        .offerTo(exporter);
            }

            private void createAnvilShapingRecipeItem(RecipeExporter exporter, Item input, Item output, int amount) {
                AnvilShapingRecipeJsonBuilder.createAnvilShapingRecipe(this.itemLookup, RecipeCategory.MISC, output, amount)
                        .input(input)
                        .criterion(hasItem(Items.COPPER_INGOT),
                                conditionsFromItem(Items.COPPER_INGOT))
                        .offerTo(exporter);
            }

            private void createAnvilRecipe(RecipeExporter exporter, Item inputBlock, Item inputIngot, Item output) {
                ShapedRecipeJsonBuilder.create(this.itemLookup, RecipeCategory.BUILDING_BLOCKS, output, 1)
                        .pattern("IBI")
                        .pattern(" I ")
                        .pattern("LLL")
                        .input('I', inputIngot)
                        .input('B', inputBlock)
                        .input('L', TagKey.of(RegistryKeys.ITEM, Identifier.of("logs")))
                        .criterion(hasItem(inputIngot),
                                conditionsFromItem(inputIngot))
                        .offerTo(exporter);
            }

            private void createGenericRecipes(GenericBlockSetBuilder set) {
                offerSlabRecipe(RecipeCategory.BUILDING_BLOCKS, set.blockSet.slab().asItem(), set.blockSet.base().asItem());
                createVerticalSlabsRecipe(exporter, set.blockSet.slab(), set.blockSet.verticalSlab());
                createSlabsFromVerticalRecipe(exporter, set.blockSet.verticalSlab(), set.blockSet.slab());
                createStairsRecipe(exporter, set.blockSet.base(), set.blockSet.stairs());
                offerWallRecipe(RecipeCategory.BUILDING_BLOCKS, set.blockSet.wall(), set.blockSet.base());
            }

            private void createRegularSetRecipes(BlockRecordTypes.RegularSet set) {
                offerSlabRecipe(RecipeCategory.BUILDING_BLOCKS, set.slab().asItem(), set.base().asItem());
                createVerticalSlabsRecipe(exporter, set.slab(), set.verticalSlab());
                createSlabsFromVerticalRecipe(exporter, set.verticalSlab(), set.slab());
                createStairsRecipe(exporter, set.base(), set.stairs());
                offerWallRecipe(RecipeCategory.BUILDING_BLOCKS, set.wall(), set.base());
            }

            private void createStairsRecipe(RecipeExporter exporter, Block input, Block output) {
                ShapedRecipeJsonBuilder.create(this.itemLookup, RecipeCategory.BUILDING_BLOCKS, output, 4)
                        .pattern("l  ")
                        .pattern("ll ")
                        .pattern("lll")
                        .input('l', input)
                        .criterion(hasItem(input),
                                conditionsFromItem(input))
                        .offerTo(exporter);
            }

            private void createSlabsFromVerticalRecipe(RecipeExporter exporter, Block input, Block output) {
                ShapelessRecipeJsonBuilder.create(this.itemLookup, RecipeCategory.BUILDING_BLOCKS, output, 1)
                        .input(input)
                        .criterion(hasItem(input),
                                conditionsFromItem(input))
                        .offerTo(exporter, String.valueOf(Identifier.of(MiddleEarth.MOD_ID, Registries.BLOCK.getId(input).getPath() + "_from_vertical")));
            }

            private void createVerticalSlabsRecipe(RecipeExporter exporter, Block input, Block output) {
                ShapelessRecipeJsonBuilder.create(this.itemLookup, RecipeCategory.BUILDING_BLOCKS, output, 1)
                        .input(input)
                        .criterion(hasItem(input),
                                conditionsFromItem(input))
                        .offerTo(exporter);
            }

            private void createShinglesRecipe(RecipeExporter exporter, Block input, Block output) {
                ShapedRecipeJsonBuilder.create(this.itemLookup, RecipeCategory.BUILDING_BLOCKS, output, 7)
                        .pattern(" w ")
                        .pattern("www")
                        .pattern("www")
                        .input('w', input)
                        .criterion(hasItem(input),
                                conditionsFromItem(input))
                        .offerTo(exporter);
            }

            private void createDoorRecipe(RecipeExporter exporter, Block input, Block output) {
                ShapedRecipeJsonBuilder.create(this.itemLookup, RecipeCategory.BUILDING_BLOCKS, output, 3)
                        .pattern("ll")
                        .pattern("ll")
                        .pattern("ll")
                        .input('l', input)
                        .criterion(hasItem(input),
                                conditionsFromItem(input))
                        .offerTo(exporter);
            }

            private void createTrapdoorRecipe(RecipeExporter exporter, Block input, Block output) {
                ShapedRecipeJsonBuilder.create(this.itemLookup, RecipeCategory.BUILDING_BLOCKS, output, 2)
                        .pattern("lll")
                        .pattern("lll")
                        .input('l', input)
                        .criterion(hasItem(input),
                                conditionsFromItem(input))
                        .offerTo(exporter);
            }

            private void createCenterSurroundRecipe(RecipeExporter exporter, Item surroundInput, Item centerItem, Item output, int count) {
                ShapedRecipeJsonBuilder.create(this.itemLookup, RecipeCategory.BUILDING_BLOCKS, output, count)
                        .pattern("BBB")
                        .pattern("BDB")
                        .pattern("BBB")
                        .input('B', surroundInput)
                        .input('D', centerItem)
                        .criterion(hasItem(surroundInput),
                                conditionsFromItem(surroundInput))
                        .offerTo(exporter, String.valueOf(Identifier.of(MiddleEarth.MOD_ID, Registries.ITEM.getId(output).getPath() + "_alt")));
            }

            private void createDyeableItemRecipe(RecipeExporter exporter, Block blockInput, Item dyeItem, Block output) {
                ShapelessRecipeJsonBuilder.create(this.itemLookup, RecipeCategory.BUILDING_BLOCKS, output, 1)
                        .input(blockInput)
                        .input(dyeItem)
                        .criterion(hasItem(blockInput),
                                conditionsFromItem(blockInput))
                        .offerTo(exporter);
            }

            private void createCombinedItemRecipe(RecipeExporter exporter, Block blockInput, TagKey<Item> addition, Block output) {
                ShapelessRecipeJsonBuilder.create(this.itemLookup, RecipeCategory.BUILDING_BLOCKS, output, 1)
                        .input(blockInput)
                        .input(addition)
                        .criterion(hasItem(blockInput),
                                conditionsFromItem(blockInput))
                        .offerTo(exporter);
            }

            private void createPaneRecipe(RecipeExporter exporter, Item blockInput, Block output, int count) {
                ShapedRecipeJsonBuilder.create(this.itemLookup, RecipeCategory.BUILDING_BLOCKS, output, count)
                        .pattern("BBB")
                        .pattern("BBB")
                        .input('B', blockInput)
                        .criterion(hasItem(blockInput),
                                conditionsFromItem(blockInput))
                        .offerTo(exporter);
            }

            private void createWoodStoolRecipe(RecipeExporter exporter, Item inputPlanks, Block output) {
                ShapedRecipeJsonBuilder.create(this.itemLookup, RecipeCategory.BUILDING_BLOCKS, output, 3)
                        .pattern("PP")
                        .pattern("SS")
                        .input('P', inputPlanks)
                        .input('S', Items.STICK)
                        .criterion(hasItem(inputPlanks),
                                conditionsFromItem(inputPlanks))
                        .offerTo(exporter);
            }

            private void createWoodBenchRecipe(RecipeExporter exporter, Item inputPlanks, Block output) {
                ShapedRecipeJsonBuilder.create(this.itemLookup, RecipeCategory.BUILDING_BLOCKS, output, 3)
                        .pattern("PPP")
                        .pattern("S S")
                        .input('P', inputPlanks)
                        .input('S', Items.STICK)
                        .criterion(hasItem(inputPlanks),
                                conditionsFromItem(inputPlanks))
                        .offerTo(exporter);
            }

            private void createWoodTableRecipe(RecipeExporter exporter, Item inputPlanks, Block output) {
                ShapedRecipeJsonBuilder.create(this.itemLookup, RecipeCategory.BUILDING_BLOCKS, output, 3)
                        .pattern("PPP")
                        .pattern("S S")
                        .pattern("S S")
                        .input('P', inputPlanks)
                        .input('S', Items.STICK)
                        .criterion(hasItem(inputPlanks),
                                conditionsFromItem(inputPlanks))
                        .offerTo(exporter);
            }

            private void createWoodChairRecipe(RecipeExporter exporter, Item inputPlanks, Block output) {
                ShapedRecipeJsonBuilder.create(this.itemLookup, RecipeCategory.BUILDING_BLOCKS, output, 3)
                        .pattern("P  ")
                        .pattern("PPP")
                        .pattern("S S")
                        .input('P', inputPlanks)
                        .input('S', Items.STICK)
                        .criterion(hasItem(inputPlanks),
                                conditionsFromItem(inputPlanks))
                        .offerTo(exporter);
            }

            private void createWoodLadderRecipe(RecipeExporter exporter, Item inputPlanks, Block output) {
                ShapedRecipeJsonBuilder.create(this.itemLookup, RecipeCategory.BUILDING_BLOCKS, output, 3)
                        .pattern("P P")
                        .pattern("PSP")
                        .pattern("P P")
                        .input('P', inputPlanks)
                        .input('S', Items.STICK)
                        .criterion(hasItem(inputPlanks),
                                conditionsFromItem(inputPlanks))
                        .offerTo(exporter);
            }

            private void createStoneStoolRecipe(RecipeExporter exporter, Item input, Block output) {
                ShapedRecipeJsonBuilder.create(this.itemLookup, RecipeCategory.BUILDING_BLOCKS, output, 1)
                        .pattern("SSS")
                        .pattern("S S")
                        .input('S', input)
                        .criterion(hasItem(input),
                                conditionsFromItem(input))
                        .offerTo(exporter);
            }

            private void createStoneTableRecipe(RecipeExporter exporter, Item input, Block output) {
                ShapedRecipeJsonBuilder.create(this.itemLookup, RecipeCategory.BUILDING_BLOCKS, output, 1)
                        .pattern("SSS")
                        .pattern(" S ")
                        .pattern(" S ")
                        .input('S', input)
                        .criterion(hasItem(input),
                                conditionsFromItem(input))
                        .offerTo(exporter);
            }

            private void createStoneChairRecipe(RecipeExporter exporter, Item input, Block output) {
                ShapedRecipeJsonBuilder.create(this.itemLookup, RecipeCategory.BUILDING_BLOCKS, output, 1)
                        .pattern("S  ")
                        .pattern("SSS")
                        .pattern("SSS")
                        .input('S', input)
                        .criterion(hasItem(input),
                                conditionsFromItem(input))
                        .offerTo(exporter);
            }

            private void createLayerRecipe(RecipeExporter exporter, Item input, Block output) {
                ShapedRecipeJsonBuilder.create(this.itemLookup, RecipeCategory.BUILDING_BLOCKS, output, 6)
                        .pattern("BBB")
                        .input('B', input)
                        .criterion(hasItem(input),
                                conditionsFromItem(input))
                        .offerTo(exporter);
            }

            private void createButtonRecipe(RecipeExporter exporter, Block input, Block output) {
                ShapelessRecipeJsonBuilder.create(this.itemLookup, RecipeCategory.BUILDING_BLOCKS, output, 1)
                        .input(input, 1)
                        .criterion(hasItem(input),
                                conditionsFromItem(input))
                        .offerTo(exporter);
            }

            private void createPressurePlateRecipe(RecipeExporter exporter, Block input, Block output) {
                ShapedRecipeJsonBuilder.create(this.itemLookup, RecipeCategory.BUILDING_BLOCKS, output, 1)
                        .pattern("BB")
                        .input('B', input)
                        .criterion(hasItem(input),
                                conditionsFromItem(input))
                        .offerTo(exporter);
            }

            private void createFenceRecipe(RecipeExporter exporter, Item input, Block output) {
                ShapedRecipeJsonBuilder.create(this.itemLookup, RecipeCategory.BUILDING_BLOCKS, output, 3)
                        .pattern("lsl")
                        .pattern("lsl")
                        .input('l', input)
                        .input('s', Items.STICK)
                        .criterion(hasItem(input),
                                conditionsFromItem(input))
                        .criterion(hasItem(Items.STICK),
                                conditionsFromItem(Items.STICK))
                        .offerTo(exporter);
            }

            private void createGildedBlockRecipe(RecipeExporter exporter, Block input, Block output) {
                ShapedRecipeJsonBuilder.create(this.itemLookup, RecipeCategory.BUILDING_BLOCKS, output, 1)
                        .pattern(" N ")
                        .pattern("NBN")
                        .pattern(" N ")
                        .input('B', input)
                        .input('N', Items.GOLD_NUGGET)
                        .criterion(hasItem(input),
                                conditionsFromItem(input))
                        .offerTo(exporter);
            }

            private void createBrickworkBlockRecipe(RecipeExporter exporter, Block input, Block inputBinder, Block output) {
                ShapedRecipeJsonBuilder.create(this.itemLookup, RecipeCategory.BUILDING_BLOCKS, output, 2)
                        .pattern("SB")
                        .input('S', inputBinder)
                        .input('B', input)
                        .criterion(hasItem(input),
                                conditionsFromItem(input))
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
                ShapedRecipeJsonBuilder.create(this.itemLookup, RecipeCategory.BUILDING_BLOCKS, output, 1)
                        .pattern(" S ")
                        .pattern("SDS")
                        .pattern(" S ")
                        .input('S', Items.STICK)
                        .input('D', input)
                        .criterion(hasItem(input),
                                conditionsFromItem(input))
                        .offerTo(exporter);
            }

            private void createCrossWattleRecipe(RecipeExporter exporter, Item input, Block output) {
                ShapedRecipeJsonBuilder.create(this.itemLookup, RecipeCategory.BUILDING_BLOCKS, output, 4)
                        .pattern("SDS")
                        .pattern("DSD")
                        .pattern("SDS")
                        .input('S', Items.STICK)
                        .input('D', input)
                        .criterion(hasItem(input),
                                conditionsFromItem(input))
                        .offerTo(exporter);
            }

            private void createRightWattleRecipe(RecipeExporter exporter, Item input, Block output) {
                ShapedRecipeJsonBuilder.create(this.itemLookup, RecipeCategory.BUILDING_BLOCKS, output, 6)
                        .pattern("DDS")
                        .pattern("DSD")
                        .pattern("SDD")
                        .input('S', Items.STICK)
                        .input('D', input)
                        .criterion(hasItem(input),
                                conditionsFromItem(input))
                        .offerTo(exporter);
            }

            private void createLeftWattleRecipe(RecipeExporter exporter, Item input, Block output) {
                ShapedRecipeJsonBuilder.create(this.itemLookup, RecipeCategory.BUILDING_BLOCKS, output, 6)
                        .pattern("SDD")
                        .pattern("DSD")
                        .pattern("DDS")
                        .input('S', Items.STICK)
                        .input('D', input)
                        .criterion(hasItem(input),
                                conditionsFromItem(input))
                        .offerTo(exporter);
            }

            private void createPillarWattleRecipe(RecipeExporter exporter, Item input, Block output) {
                ShapedRecipeJsonBuilder.create(this.itemLookup, RecipeCategory.BUILDING_BLOCKS, output, 6)
                        .pattern("DSD")
                        .pattern("DSD")
                        .pattern("DSD")
                        .input('S', Items.STICK)
                        .input('D', input)
                        .criterion(hasItem(input),
                                conditionsFromItem(input))
                        .offerTo(exporter);
            }

            private void createDiamondWattleRecipe(RecipeExporter exporter, Item input, Block output) {
                ShapedRecipeJsonBuilder.create(this.itemLookup, RecipeCategory.BUILDING_BLOCKS, output, 5)
                        .pattern("DSD")
                        .pattern("SDS")
                        .pattern("DSD")
                        .input('S', Items.STICK)
                        .input('D', input)
                        .criterion(hasItem(input),
                                conditionsFromItem(input))
                        .offerTo(exporter);
            }

            private void createStatueRecipe(RecipeExporter exporter, Block polishedInput, Block stoneInput, Block wallInput, Block output) {
                ShapedRecipeJsonBuilder.create(this.itemLookup, RecipeCategory.BUILDING_BLOCKS, output, 1)
                        .pattern("WSW")
                        .pattern("WSW")
                        .pattern("WPW")
                        .input('W', wallInput)
                        .input('S', stoneInput)
                        .input('P', polishedInput)
                        .criterion(hasItem(polishedInput),
                                conditionsFromItem(polishedInput))
                        .offerTo(exporter);
            }

            private void createCushionRecipe(RecipeExporter exporter, Block woolBlock, Block output) {
                ShapedRecipeJsonBuilder.create(this.itemLookup, RecipeCategory.BUILDING_BLOCKS, output, 1)
                        .pattern("WW")
                        .pattern("PP")
                        .input('W', woolBlock)
                        .input('P', TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .criterion(hasItem(woolBlock),
                                conditionsFromItem(woolBlock))
                        .offerTo(exporter);
            }

            private void createSmallCurtainRecipe(RecipeExporter exporter, Block woolBlock, Block output) {
                ShapedRecipeJsonBuilder.create(this.itemLookup, RecipeCategory.BUILDING_BLOCKS, output, 2)
                        .pattern("SSS")
                        .pattern("W W")
                        .input('W', woolBlock)
                        .input('S', Items.STICK)
                        .criterion(hasItem(woolBlock),
                                conditionsFromItem(woolBlock))
                        .offerTo(exporter);
            }
            private void createSmallFancyCurtainRecipe(RecipeExporter exporter, Block woolBlock, Block output) {
                ShapedRecipeJsonBuilder.create(this.itemLookup, RecipeCategory.BUILDING_BLOCKS, output, 2)
                        .pattern("SGS")
                        .pattern("W W")
                        .input('W', woolBlock)
                        .input('S', Items.STICK)
                        .input('G', Items.GOLD_NUGGET)
                        .criterion(hasItem(woolBlock),
                                conditionsFromItem(woolBlock))
                        .offerTo(exporter);
            }

            private void createCurtainRecipe(RecipeExporter exporter, Block woolBlock, Block output) {
                ShapedRecipeJsonBuilder.create(this.itemLookup, RecipeCategory.BUILDING_BLOCKS, output, 4)
                        .pattern("SSS")
                        .pattern("W W")
                        .pattern("W W")
                        .input('W', woolBlock)
                        .input('S', Items.STICK)
                        .criterion(hasItem(woolBlock),
                                conditionsFromItem(woolBlock))
                        .offerTo(exporter);
            }
            private void createFancyCurtainRecipe(RecipeExporter exporter, Block woolBlock, Block output) {
                ShapedRecipeJsonBuilder.create(this.itemLookup, RecipeCategory.BUILDING_BLOCKS, output, 4)
                        .pattern("SGS")
                        .pattern("W W")
                        .pattern("W W")
                        .input('W', woolBlock)
                        .input('G', Items.GOLD_NUGGET)
                        .input('S', Items.STICK)
                        .criterion(hasItem(woolBlock),
                                conditionsFromItem(woolBlock))
                        .offerTo(exporter);
            }

            private void createBannerPatternRecipe(RecipeExporter exporter, Item input, Item output) {
                ShapedRecipeJsonBuilder.create(this.itemLookup, RecipeCategory.MISC, output, 1)
                        .pattern("PF")
                        .pattern("BI")
                        .input('I', input)
                        .input('B', Items.BLACK_DYE)
                        .input('F', Items.FEATHER)
                        .input('P', Items.PAPER)
                        .criterion(hasItem(Items.PAPER),
                                conditionsFromItem(Items.PAPER))
                        .offerTo(exporter);
            }
            //endregion

            //region ITEM RECIPE METHODS
            private void createSeedsRecipe(RecipeExporter exporter, Item input, Item output) {
                ShapelessRecipeJsonBuilder.create(this.itemLookup, RecipeCategory.MISC, output, 1)
                        .input(input)
                        .criterion(hasItem(input),
                                conditionsFromItem(input))
                        .offerTo(exporter);
            }

            private void createPickaxeRecipe(RecipeExporter exporter, Item inputRod, Item inputMaterial, Item output) {
                ShapedRecipeJsonBuilder.create(this.itemLookup, RecipeCategory.TOOLS, output, 1)
                        .pattern("MMM")
                        .pattern(" R ")
                        .pattern(" R ")
                        .input('M', inputMaterial)
                        .input('R', inputRod)
                        .criterion(hasItem(inputMaterial),
                                conditionsFromItem(inputMaterial))
                        .offerTo(exporter);
            }

            private void createAxeRecipe(RecipeExporter exporter, Item inputRod, Item inputMaterial, Item output) {
                ShapedRecipeJsonBuilder.create(this.itemLookup, RecipeCategory.TOOLS, output, 1)
                        .pattern("MM ")
                        .pattern("MR ")
                        .pattern(" R ")
                        .input('M', inputMaterial)
                        .input('R', inputRod)
                        .criterion(hasItem(inputMaterial),
                                conditionsFromItem(inputMaterial))
                        .offerTo(exporter);
            }

            private void createShovelRecipe(RecipeExporter exporter, Item inputRod, Item inputMaterial, Item output) {
                ShapedRecipeJsonBuilder.create(this.itemLookup, RecipeCategory.TOOLS, output, 1)
                        .pattern(" M ")
                        .pattern(" R ")
                        .pattern(" R ")
                        .input('M', inputMaterial)
                        .input('R', inputRod)
                        .criterion(hasItem(inputMaterial),
                                conditionsFromItem(inputMaterial))
                        .offerTo(exporter);
            }

            private void createHoeRecipe(RecipeExporter exporter, Item inputRod, Item inputMaterial, Item output) {
                ShapedRecipeJsonBuilder.create(this.itemLookup, RecipeCategory.TOOLS, output, 1)
                        .pattern("MM ")
                        .pattern(" R ")
                        .pattern(" R ")
                        .input('M', inputMaterial)
                        .input('R', inputRod)
                        .criterion(hasItem(inputMaterial),
                                conditionsFromItem(inputMaterial))
                        .offerTo(exporter);
            }

            private void createSwordRecipe(RecipeExporter exporter, Item inputRod, Item inputMaterial, Item output) {
                ShapedRecipeJsonBuilder.create(this.itemLookup, RecipeCategory.COMBAT, output, 1)
                        .pattern(" M ")
                        .pattern(" M ")
                        .pattern(" R ")
                        .input('M', inputMaterial)
                        .input('R', inputRod)
                        .criterion(hasItem(inputMaterial),
                                conditionsFromItem(inputMaterial))
                        .offerTo(exporter);
            }

            private void createDaggerRecipe(RecipeExporter exporter, Item inputRod, Item inputMaterial, Item output) {
                ShapedRecipeJsonBuilder.create(this.itemLookup, RecipeCategory.COMBAT, output, 1)
                        .pattern(" M ")
                        .pattern(" R ")
                        .input('M', inputMaterial)
                        .input('R', inputRod)
                        .criterion(hasItem(inputMaterial),
                                conditionsFromItem(inputMaterial))
                        .offerTo(exporter);
            }

            private void createDaggerRecipeTag(RecipeExporter exporter, Item inputRod, TagKey inputMaterial, Item output) {
                ShapedRecipeJsonBuilder.create(this.itemLookup, RecipeCategory.COMBAT, output, 1)
                        .pattern(" M ")
                        .pattern(" R ")
                        .input('M', inputMaterial)
                        .input('R', inputRod)
                        .criterion(hasItem(Items.OAK_PLANKS),
                                conditionsFromItem(Items.OAK_PLANKS))
                        .offerTo(exporter);
            }

            private void createSpearRecipe(RecipeExporter exporter, Item inputRod, Item inputMaterial, Item output) {
                ShapedRecipeJsonBuilder.create(this.itemLookup, RecipeCategory.COMBAT, output, 1)
                        .pattern("  M")
                        .pattern(" R ")
                        .pattern("R  ")
                        .input('M', inputMaterial)
                        .input('R', inputRod)
                        .criterion(hasItem(inputMaterial),
                                conditionsFromItem(inputMaterial))
                        .offerTo(exporter);
            }

            private void createSpearRecipeTag(RecipeExporter exporter, Item inputRod, TagKey inputMaterial, Item output) {
                ShapedRecipeJsonBuilder.create(this.itemLookup, RecipeCategory.COMBAT, output, 1)
                        .pattern("  M")
                        .pattern(" R ")
                        .pattern("R  ")
                        .input('M', inputMaterial)
                        .input('R', inputRod)
                        .criterion(hasItem(Items.OAK_PLANKS),
                                conditionsFromItem(Items.OAK_PLANKS))
                        .offerTo(exporter);
            }

            private void createBucketRecipe(RecipeExporter exporter, Item inputMaterial, Item output) {
                ShapedRecipeJsonBuilder.create(this.itemLookup, RecipeCategory.BUILDING_BLOCKS, output, 1)
                        .pattern("M M")
                        .pattern(" M ")
                        .input('M', inputMaterial)
                        .criterion(hasItem(inputMaterial),
                                conditionsFromItem(inputMaterial))
                        .offerTo(exporter);
            }

            private void createToolSetRecipes(RecipeExporter exporter, Item inputRod, Item inputMaterial, Item outputPickaxe, Item outputAxe, Item outputShovel, Item outputHoe) {
                createPickaxeRecipe(exporter, inputRod, inputMaterial, outputPickaxe);
                createAxeRecipe(exporter, inputRod, inputMaterial, outputAxe);
                createShovelRecipe(exporter, inputRod, inputMaterial, outputShovel);
                createHoeRecipe(exporter, inputRod, inputMaterial, outputHoe);
            }

            private void createCookedFoodRecipes(RecipeExporter exporter, Item rawFood, Item cookedFood) {
                offerFoodCookingRecipe("smelting", RecipeSerializer.SMELTING, SmeltingRecipe::new, 200, rawFood, cookedFood, 0.35f);
                offerFoodCookingRecipe("smoking", RecipeSerializer.SMOKING, SmokingRecipe::new, 100, rawFood, cookedFood, 0.35f);
                offerFoodCookingRecipe("campfire_cooking", RecipeSerializer.CAMPFIRE_COOKING, CampfireCookingRecipe::new, 600, rawFood, cookedFood, 0.35f);
            }

            private void createSmokingRecipe(RecipeExporter exporter, Item rawFood, Item cookedFood) {
                offerFoodCookingRecipe("smoking", RecipeSerializer.SMOKING, SmokingRecipe::new, 100, rawFood, cookedFood, 0.35f);
            }

            private void createMetalsRecipe(RecipeExporter exporter, Item nugget, Item ingot, Block block) {
                ShapelessRecipeJsonBuilder.create(this.itemLookup, RecipeCategory.MISC, ingot, 1)
                        .input(nugget, 9)
                        .criterion(hasItem(nugget),
                                conditionsFromItem(nugget))
                        .offerTo(exporter, String.valueOf(Identifier.of(MiddleEarth.MOD_ID, Registries.ITEM.getId(ingot).getPath() + "_from_nuggets")));

                ShapelessRecipeJsonBuilder.create(this.itemLookup, RecipeCategory.MISC, nugget, 9)
                        .input(ingot)
                        .criterion(hasItem(ingot),
                                conditionsFromItem(ingot))
                        .offerTo(exporter, String.valueOf(Identifier.of(MiddleEarth.MOD_ID, Registries.ITEM.getId(nugget).getPath() + "_from_ingot")));

                createFilledRecipe(exporter, ingot, block, 1);

                ShapelessRecipeJsonBuilder.create(this.itemLookup, RecipeCategory.MISC, ingot, 9)
                        .input(block)
                        .criterion(hasItem(block),
                                conditionsFromItem(block))
                        .offerTo(exporter, String.valueOf(Identifier.of(MiddleEarth.MOD_ID, Registries.ITEM.getId(ingot).getPath() + "_from_block")));
            }
            //endregion

            private void createFilledRecipe(RecipeExporter exporter, Item input, Block output, int count) {
                ShapedRecipeJsonBuilder.create(this.itemLookup, RecipeCategory.BUILDING_BLOCKS, output, count)
                        .pattern("lll")
                        .pattern("lll")
                        .pattern("lll")
                        .input('l', input)
                        .criterion(hasItem(input),
                                conditionsFromItem(input))
                        .offerTo(exporter);
            }
        };

    }

    @Override
    public String getName() {
        return "RecipeProvider";
    }
}
