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
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.block.registration.*;
import net.sevenstars.middleearth.block.utils.BlockRecordTypes;
import net.sevenstars.middleearth.block.utils.setBuilders.GenericBlockSetBuilder;
import net.sevenstars.middleearth.block.utils.setBuilders.SimpleBlockSetBuilder;
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
                for (StoneBlockSetBuilder record : StoneBlockSetRegistryME.stoneSetsList) {
                    if(record.hasMossy) {
                        createStoneSetRecipes(record.mossyCobblestoneBlocks);
                        createStoneSetRecipes(record.mossyBrickBlocks);
                        createStoneSetRecipes(record.mossyPolishedBlocks);
                        createStoneSetRecipes(record.mossyPillarBlocks);
                        createStoneSetRecipes(record.mossyTileBlocks);
                        createStoneSetRecipes(record.mossySmoothBlocks);
                        if(record.mossyCobblestoneBlocks != null && record.cobblestoneBlocks != null) {
                            createMossyRecipe(exporter, record.cobblestoneBlocks.base(), record.mossyCobblestoneBlocks.base());
                        }
                        if(record.mossyBrickBlocks != null && record.brickBlocks != null) {
                            createMossyRecipe(exporter, record.brickBlocks.base(), record.mossyBrickBlocks.base());
                        }
                        if(record.mossyPillarBlocks != null && record.pillarBlocks != null) {
                            createMossyRecipe(exporter, record.pillarBlocks.base(), record.mossyPillarBlocks.base());
                        }
                        if(record.mossyPolishedBlocks != null && record.polishedBlocks != null) {
                            createMossyRecipe(exporter, record.polishedBlocks.base(), record.mossyPolishedBlocks.base());
                        }
                        if(record.mossyTileBlocks != null && record.tileBlocks != null) {
                            createMossyRecipe(exporter, record.tileBlocks.base(), record.mossyTileBlocks.base());
                        }
                        if(record.mossySmoothBlocks != null && record.smoothBlocks != null) {
                            createMossyRecipe(exporter, record.smoothBlocks.base(), record.mossySmoothBlocks.base());
                        }
                    }
                    if(record.hasCracked) {
                        if(record.crackedBrickBlocks != null && record.brickBlocks != null) {
                            createStoneSetRecipes(record.crackedBrickBlocks);
                            offerSmelting(List.of(record.brickBlocks.base()), RecipeCategory.BUILDING_BLOCKS,
                                    record.crackedBrickBlocks.base(), 0.1f, 200, "cracked_bricks");
                        }
                        if(record.crackedPillarBlocks != null && record.pillarBlocks != null) {
                            createStoneSetRecipes(record.crackedPillarBlocks);
                            offerSmelting(List.of(record.pillarBlocks.base()), RecipeCategory.BUILDING_BLOCKS,
                                    record.crackedPillarBlocks.base(), 0.1f, 200, "cracked_bricks");
                        }
                        if(record.crackedPolishedBlocks != null && record.polishedBlocks != null) {
                            createStoneSetRecipes(record.crackedPolishedBlocks);
                            offerSmelting(List.of(record.polishedBlocks.base()), RecipeCategory.BUILDING_BLOCKS,
                                    record.crackedPolishedBlocks.base(), 0.1f, 200, "cracked_bricks");
                        }
                        if(record.crackedTileBlocks != null && record.tileBlocks != null) {
                            createStoneSetRecipes(record.crackedTileBlocks);
                            offerSmelting(List.of(record.tileBlocks.base()), RecipeCategory.BUILDING_BLOCKS,
                                    record.crackedTileBlocks.base(), 0.1f, 200, "cracked_bricks");
                        }
                        if(record.crackedSmoothBlocks != null && record.smoothBlocks != null) {
                            createStoneSetRecipes(record.crackedSmoothBlocks);
                            offerSmelting(List.of(record.smoothBlocks.base()), RecipeCategory.BUILDING_BLOCKS,
                                    record.crackedSmoothBlocks.base(), 0.1f, 200, "cracked_bricks");
                        }
                    }

                    if(record.cobblestoneBlocks != null && record.baseBlocks != null) {
                        if(!record.hasVanillaCobble) {
                            offerStonecuttingRecipe(RecipeCategory.BUILDING_BLOCKS, record.cobblestoneBlocks.base(), record.baseBlocks.base(), 1);
                            offerSmelting(List.of(record.cobblestoneBlocks.base()), RecipeCategory.BUILDING_BLOCKS,
                                    record.baseBlocks.base(), 0.1f, 200, "blocks");
                        }

                        if(record.brickworkBlocks != null) {
                            createBrickworkBlockRecipe(exporter, record.cobblestoneBlocks.base(), GenericBlockSetRegistryME.STUCCO.blockSet.base(), record.brickworkBlocks.base());
                        }
                    }

                    if(record.smoothBlocks != null && record.tileBlocks != null) {
                        createBrickRecipe(exporter, record.smoothBlocks.base().asItem(), record.tileBlocks.base(), 4);
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
                        if(record.polishedBlocks != null && !record.hasVanillaPolished) {
                            createBrickRecipe(exporter, record.baseBlocks.base().asItem(), record.polishedBlocks.base(), 4);
                            offerStonecuttingRecipe(RecipeCategory.BUILDING_BLOCKS, record.polishedBlocks.base(), record.baseBlocks.base(), 1);
                        }
                        if(record.smoothBlocks != null) {
                            createSmeltingRecipe(exporter, record.baseBlocks.base().asItem(), record.smoothBlocks.base().asItem());
                        }
                        if(record.chiseledBlocks != null) {
                            createChiseledRecipe(exporter, record.baseBlocks.base(), record.chiseledBlocks.base(), 2);
                            offerStonecuttingRecipe(RecipeCategory.BUILDING_BLOCKS, record.chiseledBlocks.base(), record.baseBlocks.base(), 1);
                        }
                        if(record.chiseledBricksBlocks != null && record.brickBlocks != null) {
                            createChiseledRecipe(exporter, record.brickBlocks.base(), record.chiseledBricksBlocks.base(), 2);
                            offerStonecuttingRecipe(RecipeCategory.BUILDING_BLOCKS, record.chiseledBricksBlocks.base(), record.brickBlocks.base(), 1);
                        }
                        if(record.chiseledPolishedBlocks != null && record.polishedBlocks != null) {
                            createChiseledRecipe(exporter, record.polishedBlocks.base(), record.chiseledPolishedBlocks.base(), 2);
                            offerStonecuttingRecipe(RecipeCategory.BUILDING_BLOCKS, record.chiseledPolishedBlocks.base(), record.polishedBlocks.base(), 1);
                        }
                        if(record.chiseledSmoothBlocks != null && record.smoothBlocks != null) {
                            createChiseledRecipe(exporter, record.smoothBlocks.base(), record.chiseledSmoothBlocks.base(), 2);
                            offerStonecuttingRecipe(RecipeCategory.BUILDING_BLOCKS, record.chiseledSmoothBlocks.base(), record.smoothBlocks.base(), 1);
                        }
                        if(record.chiseledTilesBlocks != null && record.tileBlocks != null) {
                            createChiseledRecipe(exporter, record.tileBlocks.base(), record.chiseledTilesBlocks.base(), 2);
                            offerStonecuttingRecipe(RecipeCategory.BUILDING_BLOCKS, record.chiseledTilesBlocks.base(), record.tileBlocks.base(), 1);
                        }
                        if(record.oldBlocks != null) {
                            createCenterSurroundRecipe(exporter, record.baseBlocks.base().asItem(), ResourceItemsME.ASH, record.oldBlocks.base().asItem(), 8);
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

                    if(!record.isVanilla) createStoneSetRecipes(record.baseBlocks);
                    if(!record.hasVanillaCobble) createStoneSetRecipes(record.cobblestoneBlocks);
                    createStoneSetRecipes(record.brickBlocks);
                    createStoneSetRecipes(record.tileBlocks);
                    createStoneSetRecipes(record.smoothBlocks);
                    if(!record.hasVanillaPolished) createStoneSetRecipes(record.polishedBlocks);
                    createStoneSetRecipes(record.chiseledBlocks);
                    createStoneSetRecipes(record.chiseledBricksBlocks);
                    createStoneSetRecipes(record.chiseledTilesBlocks);
                    createStoneSetRecipes(record.chiseledPolishedBlocks);
                    createStoneSetRecipes(record.chiseledSmoothBlocks);
                    createStoneSetRecipes(record.brickworkBlocks);
                    createStoneSetRecipes(record.pillarBlocks);
                    createStoneSetRecipes(record.oldBlocks);

                    if(record.carvedWindows != null && record.baseBlocks != null) {
                        ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, record.carvedWindows.block(), 4)
                                .pattern("EEE")
                                .pattern("EGE")
                                .pattern("EEE")
                                .input('E', record.baseBlocks.base())
                                .input('G', Items.GLASS)
                                .criterion(hasItem(record.baseBlocks.base()),
                                        conditionsFromItem(record.baseBlocks.base()))
                                .offerTo(exporter);
                        createPaneRecipe(exporter, record.carvedWindows.block().asItem(), record.carvedWindows.verticalSlab(), 12);
                    }
                }
                //endregion

                //region WOOD RECIPES
                for (WoodBlockSetBuilder record : WoodBlockSetRegistryME.woodSetsList) {
                    if(record.logBlocks != null) {
                        offerWallRecipe(RecipeCategory.BUILDING_BLOCKS, record.logBlocks.wall(), record.logBlocks.wood());
                        createFenceRecipe(exporter, record.logBlocks.wood().asItem(), record.logBlocks.fence());
                        offerSlabRecipe(RecipeCategory.BUILDING_BLOCKS, record.logBlocks.slab(), record.logBlocks.wood());
                        createStairsRecipe(exporter, record.logBlocks.wood(), record.logBlocks.stairs());

                        createSlabsFromVerticalRecipe(exporter, record.logBlocks.verticalSlab(), record.logBlocks.slab());
                        createVerticalSlabsRecipe(exporter, record.logBlocks.slab(), record.logBlocks.verticalSlab());
                        createSlabsFromVerticalRecipe(exporter, record.planksBlocks.verticalSlab(), record.planksBlocks.slab());
                        createVerticalSlabsRecipe(exporter, record.planksBlocks.slab(), record.planksBlocks.verticalSlab());

                        if(!record.vanilla) {
                            createBrickRecipe(exporter, record.logBlocks.log().asItem(), record.logBlocks.wood(), 3);
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
                        }

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
                        createStairsRecipe(exporter, record.strippedLogBlocks.wood(), record.strippedLogBlocks.stairs());

                        createSlabsFromVerticalRecipe(exporter, record.strippedLogBlocks.verticalSlab(), record.strippedLogBlocks.slab());
                        createVerticalSlabsRecipe(exporter, record.strippedLogBlocks.slab(), record.strippedLogBlocks.verticalSlab());
                        // if(!record.vanilla)
                        //createSlabsFromVerticalRecipe(exporter, record.strippedLogBlocks.verticalSlab(), record.strippedLogBlocks.slab());
                        //createVerticalSlabsRecipe(exporter, record.strippedLogBlocks.slab(), record.strippedLogBlocks.verticalSlab());
                        //createSlabsFromVerticalRecipe(exporter, record.strippedLogBlocks.verticalSlab(), record.strippedLogBlocks.slab());
                        //createStairsRecipe(exporter, record.strippedLogBlocks.wood(), record.strippedLogBlocks.stairs());

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
                    if(record.roofingBlocks != null) {
                        createRoofingRecipe(exporter, record.planksBlocks.slab(), record.roofingBlocks.base());
                        createRegularSetRecipes(record.roofingBlocks);
                    }

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

                for(GenericBlockSetBuilder set : GenericBlockSetRegistryME.genericSetsList) {
                    if(!set.setName.contains("wood") && !set.setName.contains("thatch") && !set.setName.contains("reed")) {
                        createStoneSetRecipes(set.blockSet);
                    } else if (set.setName.contains("thatch") || set.setName.contains("reed")) {
                        createRegularSetRecipes(set.blockSet);
                    }
                }
                for(SimpleBlockSetBuilder set : GenericBlockSetRegistryME.simpleSetsList) {
                    createGenericRecipes(set);
                }

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
                createCenterSurroundRecipe(exporter, DecorativeBlockRegistryME.LEAD_GLASS.asItem(), Items.BLACK_DYE, DecorativeBlockRegistryME.BLACK_STAINED_LEAD_GLASS.asItem(), 8);
                createCenterSurroundRecipe(exporter, DecorativeBlockRegistryME.LEAD_GLASS.asItem(), Items.BLUE_DYE, DecorativeBlockRegistryME.BLUE_STAINED_LEAD_GLASS.asItem(), 8);
                createCenterSurroundRecipe(exporter, DecorativeBlockRegistryME.LEAD_GLASS.asItem(), Items.BROWN_DYE, DecorativeBlockRegistryME.BROWN_STAINED_LEAD_GLASS.asItem(), 8);
                createCenterSurroundRecipe(exporter, DecorativeBlockRegistryME.LEAD_GLASS.asItem(), Items.CYAN_DYE, DecorativeBlockRegistryME.CYAN_STAINED_LEAD_GLASS.asItem(), 8);
                createCenterSurroundRecipe(exporter, DecorativeBlockRegistryME.LEAD_GLASS.asItem(), Items.GRAY_DYE, DecorativeBlockRegistryME.GRAY_STAINED_LEAD_GLASS.asItem(), 8);
                createCenterSurroundRecipe(exporter, DecorativeBlockRegistryME.LEAD_GLASS.asItem(), Items.GREEN_DYE, DecorativeBlockRegistryME.GREEN_STAINED_LEAD_GLASS.asItem(), 8);
                createCenterSurroundRecipe(exporter, DecorativeBlockRegistryME.LEAD_GLASS.asItem(), Items.LIGHT_BLUE_DYE, DecorativeBlockRegistryME.LIGHT_BLUE_STAINED_LEAD_GLASS.asItem(), 8);
                createCenterSurroundRecipe(exporter, DecorativeBlockRegistryME.LEAD_GLASS.asItem(), Items.LIGHT_GRAY_DYE, DecorativeBlockRegistryME.LIGHT_GRAY_STAINED_LEAD_GLASS.asItem(), 8);
                createCenterSurroundRecipe(exporter, DecorativeBlockRegistryME.LEAD_GLASS.asItem(), Items.LIME_DYE, DecorativeBlockRegistryME.LIME_STAINED_LEAD_GLASS.asItem(), 8);
                createCenterSurroundRecipe(exporter, DecorativeBlockRegistryME.LEAD_GLASS.asItem(), Items.MAGENTA_DYE, DecorativeBlockRegistryME.MAGENTA_STAINED_LEAD_GLASS.asItem(), 8);
                createCenterSurroundRecipe(exporter, DecorativeBlockRegistryME.LEAD_GLASS.asItem(), Items.ORANGE_DYE, DecorativeBlockRegistryME.ORANGE_STAINED_LEAD_GLASS.asItem(), 8);
                createCenterSurroundRecipe(exporter, DecorativeBlockRegistryME.LEAD_GLASS.asItem(), Items.PINK_DYE, DecorativeBlockRegistryME.PINK_STAINED_LEAD_GLASS.asItem(), 8);
                createCenterSurroundRecipe(exporter, DecorativeBlockRegistryME.LEAD_GLASS.asItem(), Items.PURPLE_DYE, DecorativeBlockRegistryME.PURPLE_STAINED_LEAD_GLASS.asItem(), 8);
                createCenterSurroundRecipe(exporter, DecorativeBlockRegistryME.LEAD_GLASS.asItem(), Items.RED_DYE, DecorativeBlockRegistryME.RED_STAINED_LEAD_GLASS.asItem(), 8);
                createCenterSurroundRecipe(exporter, DecorativeBlockRegistryME.LEAD_GLASS.asItem(), Items.WHITE_DYE, DecorativeBlockRegistryME.WHITE_STAINED_LEAD_GLASS.asItem(), 8);
                createCenterSurroundRecipe(exporter, DecorativeBlockRegistryME.LEAD_GLASS.asItem(), Items.YELLOW_DYE, DecorativeBlockRegistryME.YELLOW_STAINED_LEAD_GLASS.asItem(), 8);

                createLayerRecipe(exporter, Blocks.GRAVEL.asItem(), BlockRegistryME.GRAVEL_LAYER);
                createLayerRecipe(exporter, Blocks.SAND.asItem(), BlockRegistryME.SAND_LAYER);
                createLayerRecipe(exporter, BlockRegistryME.BLACK_SAND.asItem(), BlockRegistryME.BLACK_SAND_LAYER);
                createLayerRecipe(exporter, BlockRegistryME.WHITE_SAND.asItem(), BlockRegistryME.WHITE_SAND_LAYER);
                createLayerRecipe(exporter, BlockRegistryME.ASHEN_SAND.asItem(), BlockRegistryME.ASHEN_SAND_LAYER);
                createLayerRecipe(exporter, BlockRegistryME.ASHEN_GRAVEL.asItem(), BlockRegistryME.ASHEN_GRAVEL_LAYER);
                createLayerRecipe(exporter, BlockRegistryME.SKELETAL_PILE.asItem(), BlockRegistryME.SKELETAL_PILE_LAYER);
                createLayerRecipe(exporter, BlockRegistryME.WASTE_PILE.asItem(), BlockRegistryME.WASTE_PILE_LAYER);


                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, StoneBlockSetRegistryME.TRAVERTINE_SET.baseBlocks.base(), 4)
                        .pattern("CS")
                        .pattern("SC")
                        .input('C', Blocks.CALCITE)
                        .input('S', Blocks.SANDSTONE)
                        .criterion(hasItem(Blocks.CALCITE),
                                conditionsFromItem(Blocks.CALCITE))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, GenericBlockSetRegistryME.REED_THATCH.blockSet.base(), 1)
                        .pattern("RR")
                        .pattern("RR")
                        .input('R', ResourceItemsME.REEDS)
                        .criterion(hasItem(ResourceItemsME.REEDS),
                                conditionsFromItem(ResourceItemsME.REEDS))
                        .offerTo(exporter);

                createStairsRecipe(exporter, BlockRegistryME.GRASSY_DIRT, BlockRegistryME.GRASSY_DIRT_STAIRS);
                offerSlabRecipe(RecipeCategory.BUILDING_BLOCKS, BlockRegistryME.GRASSY_DIRT_SLAB, BlockRegistryME.GRASSY_DIRT);

                createStairsRecipe(exporter, BlockRegistryME.PEBBLED_GRASS, BlockRegistryME.PEBBLED_GRASS_STAIRS);
                offerSlabRecipe(RecipeCategory.BUILDING_BLOCKS, BlockRegistryME.PEBBLED_GRASS_SLAB, BlockRegistryME.PEBBLED_GRASS);

                createStairsRecipe(exporter, BlockRegistryME.TURF, BlockRegistryME.TURF_STAIRS);
                offerSlabRecipe(RecipeCategory.BUILDING_BLOCKS, BlockRegistryME.TURF_SLAB, BlockRegistryME.TURF);
                createVerticalSlabsRecipe(exporter, BlockRegistryME.TURF, BlockRegistryME.TURF_VERTICAL_SLAB);
                createSlabsFromVerticalRecipe(exporter, BlockRegistryME.TURF_VERTICAL_SLAB, BlockRegistryME.TURF_SLAB);

                createStairsRecipe(exporter, BlockRegistryME.MIRE, BlockRegistryME.MIRE_STAIRS);
                offerSlabRecipe(RecipeCategory.BUILDING_BLOCKS, BlockRegistryME.MIRE_SLAB, BlockRegistryME.MIRE);

                createStairsRecipe(exporter, BlockRegistryME.CHALKSOIL, BlockRegistryME.CHALKSOIL_STAIRS);
                offerSlabRecipe(RecipeCategory.BUILDING_BLOCKS, BlockRegistryME.CHALKSOIL_SLAB, BlockRegistryME.CHALKSOIL);
                createStairsRecipe(exporter, BlockRegistryME.GRASSY_CHALKSOIL, BlockRegistryME.GRASSY_CHALKSOIL_STAIRS);
                offerSlabRecipe(RecipeCategory.BUILDING_BLOCKS, BlockRegistryME.GRASSY_CHALKSOIL_SLAB, BlockRegistryME.GRASSY_CHALKSOIL);
                createStairsRecipe(exporter, BlockRegistryME.COARSE_CHALKSOIL, BlockRegistryME.COARSE_CHALKSOIL_STAIRS);
                offerSlabRecipe(RecipeCategory.BUILDING_BLOCKS, BlockRegistryME.COARSE_CHALKSOIL_SLAB, BlockRegistryME.COARSE_CHALKSOIL);

                createStairsRecipe(exporter, BlockRegistryME.LOAM, BlockRegistryME.LOAM_STAIRS);
                offerSlabRecipe(RecipeCategory.BUILDING_BLOCKS, BlockRegistryME.LOAM_SLAB, BlockRegistryME.LOAM);
                createStairsRecipe(exporter, BlockRegistryME.GRASSY_LOAM, BlockRegistryME.GRASSY_LOAM_STAIRS);
                offerSlabRecipe(RecipeCategory.BUILDING_BLOCKS, BlockRegistryME.GRASSY_LOAM_SLAB, BlockRegistryME.GRASSY_LOAM);
                createStairsRecipe(exporter, BlockRegistryME.COARSE_LOAM, BlockRegistryME.COARSE_LOAM_STAIRS);
                offerSlabRecipe(RecipeCategory.BUILDING_BLOCKS, BlockRegistryME.COARSE_LOAM_SLAB, BlockRegistryME.COARSE_LOAM);

                createStairsRecipe(exporter, BlockRegistryME.PEAT, BlockRegistryME.PEAT_STAIRS);
                offerSlabRecipe(RecipeCategory.BUILDING_BLOCKS, BlockRegistryME.PEAT_SLAB, BlockRegistryME.PEAT);
                createStairsRecipe(exporter, BlockRegistryME.GRASSY_PEAT, BlockRegistryME.GRASSY_PEAT_STAIRS);
                offerSlabRecipe(RecipeCategory.BUILDING_BLOCKS, BlockRegistryME.GRASSY_PEAT_SLAB, BlockRegistryME.GRASSY_PEAT);
                createStairsRecipe(exporter, BlockRegistryME.COARSE_PEAT, BlockRegistryME.COARSE_PEAT_STAIRS);
                offerSlabRecipe(RecipeCategory.BUILDING_BLOCKS, BlockRegistryME.COARSE_PEAT_SLAB, BlockRegistryME.COARSE_PEAT);

                createStairsRecipe(exporter, BlockRegistryME.SILT, BlockRegistryME.SILT_STAIRS);
                offerSlabRecipe(RecipeCategory.BUILDING_BLOCKS, BlockRegistryME.SILT_SLAB, BlockRegistryME.SILT);
                createStairsRecipe(exporter, BlockRegistryME.GRASSY_SILT, BlockRegistryME.GRASSY_SILT_STAIRS);
                offerSlabRecipe(RecipeCategory.BUILDING_BLOCKS, BlockRegistryME.GRASSY_SILT_SLAB, BlockRegistryME.GRASSY_SILT);
                createStairsRecipe(exporter, BlockRegistryME.COARSE_SILT, BlockRegistryME.COARSE_SILT_STAIRS);
                offerSlabRecipe(RecipeCategory.BUILDING_BLOCKS, BlockRegistryME.COARSE_SILT_SLAB, BlockRegistryME.COARSE_SILT);

                createStairsRecipe(exporter, BlockRegistryME.DRY_DIRT, BlockRegistryME.DRY_DIRT_STAIRS);
                offerSlabRecipe(RecipeCategory.BUILDING_BLOCKS, BlockRegistryME.DRY_DIRT_SLAB, BlockRegistryME.DRY_DIRT);

                createStairsRecipe(exporter, BlockRegistryME.FOUL_DIRT, BlockRegistryME.FOUL_DIRT_STAIRS);
                offerSlabRecipe(RecipeCategory.BUILDING_BLOCKS, BlockRegistryME.FOUL_DIRT_SLAB, BlockRegistryME.FOUL_DIRT);

                createStairsRecipe(exporter, BlockRegistryME.DIRTY_ROOTS, BlockRegistryME.DIRTY_ROOTS_STAIRS);
                offerSlabRecipe(RecipeCategory.BUILDING_BLOCKS, BlockRegistryME.DIRTY_ROOTS_SLAB, BlockRegistryME.DIRTY_ROOTS);

                createStairsRecipe(exporter, BlockRegistryME.ASHEN_DIRT, BlockRegistryME.ASHEN_DIRT_STAIRS);
                offerSlabRecipe(RecipeCategory.BUILDING_BLOCKS, BlockRegistryME.ASHEN_DIRT_SLAB, BlockRegistryME.ASHEN_DIRT);

                createStairsRecipe(exporter, BlockRegistryME.COBBLY_ASHEN_DIRT, BlockRegistryME.COBBLY_ASHEN_DIRT_STAIRS);
                offerSlabRecipe(RecipeCategory.BUILDING_BLOCKS, BlockRegistryME.COBBLY_ASHEN_DIRT_SLAB, BlockRegistryME.COBBLY_ASHEN_DIRT);

                createStairsRecipe(exporter, BlockRegistryME.COBBLY_DIRT, BlockRegistryME.COBBLY_DIRT_STAIRS);
                offerSlabRecipe(RecipeCategory.BUILDING_BLOCKS, BlockRegistryME.COBBLY_DIRT_SLAB, BlockRegistryME.COBBLY_DIRT);

                createStairsRecipe(exporter, BlockRegistryME.SNOWY_DIRT, BlockRegistryME.SNOWY_DIRT_STAIRS);
                offerSlabRecipe(RecipeCategory.BUILDING_BLOCKS, BlockRegistryME.SNOWY_DIRT_SLAB, BlockRegistryME.SNOWY_DIRT);

                createPaneRecipe(exporter, Blocks.WHITE_WOOL.asItem(), BlockRegistryME.NET, 16);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, BlockRegistryME.COPPER_BARS, 16)
                        .pattern("IBI")
                        .pattern("IBI")
                        .input('I', Items.COPPER_INGOT)
                        .input('B', Items.CUT_COPPER)
                        .criterion(hasItem(Items.CUT_COPPER),
                                conditionsFromItem(Items.CUT_COPPER))
                        .offerTo(exporter);

                createBrickRecipe(exporter, ResourceItemsME.CITRINE_SHARD, BlockRegistryME.CITRINE_BLOCK, 1);
                createFilledRecipe(exporter, Items.GLOWSTONE, BlockRegistryME.GLOWSTONE_BLOCK, 1);
                createBrickRecipe(exporter, ResourceItemsME.QUARTZ_SHARD, BlockRegistryME.QUARTZ_BLOCK, 1);
                createBrickRecipe(exporter, ResourceItemsME.RED_AGATE_SHARD, BlockRegistryME.RED_AGATE_BLOCK, 1);

                offerStonecuttingRecipe(RecipeCategory.BUILDING_BLOCKS, Blocks.BRICKS, GenericBlockSetRegistryME.OLD_BRICKS.blockSet.base());

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, DecorativeBlockRegistryME.WHITE_DAUB_HOBBIT_WINDOW, 4)
                        .pattern("WBW")
                        .pattern("BGB")
                        .pattern("WBW")
                        .input('W', GenericBlockSetRegistryME.WHITE_DAUB.blockSet.base())
                        .input('G', Items.GLASS)
                        .input('B', Items.BRICK)
                        .criterion(hasItem(GenericBlockSetRegistryME.WHITE_DAUB.blockSet.base()),
                                conditionsFromItem(GenericBlockSetRegistryME.WHITE_DAUB.blockSet.base()))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, DecorativeBlockRegistryME.YELLOW_DAUB_HOBBIT_WINDOW, 4)
                        .pattern("WBW")
                        .pattern("BGB")
                        .pattern("WBW")
                        .input('W', GenericBlockSetRegistryME.YELLOW_DAUB.blockSet.base())
                        .input('G', Items.GLASS)
                        .input('B', Items.BRICK)
                        .criterion(hasItem(GenericBlockSetRegistryME.YELLOW_DAUB.blockSet.base()),
                                conditionsFromItem(GenericBlockSetRegistryME.YELLOW_DAUB.blockSet.base()))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, DecorativeBlockRegistryME.PLASTER_HOBBIT_WINDOW, 4)
                        .pattern("WBW")
                        .pattern("BGB")
                        .pattern("WBW")
                        .input('W', GenericBlockSetRegistryME.PLASTER.blockSet.base())
                        .input('G', Items.GLASS)
                        .input('B', Items.BRICK)
                        .criterion(hasItem(GenericBlockSetRegistryME.PLASTER.blockSet.base()),
                                conditionsFromItem(GenericBlockSetRegistryME.PLASTER.blockSet.base()))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, DecorativeBlockRegistryME.SIMPLE_OAK_WINDOW, 8)
                        .pattern("EEE")
                        .pattern("EGE")
                        .pattern("EEE")
                        .input('E', Blocks.OAK_LOG)
                        .input('G', ResourceItemsME.LEAD_NUGGET)
                        .criterion(hasItem(Blocks.OAK_LOG),
                                conditionsFromItem(Blocks.OAK_LOG))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, StoneBlockSetRegistryME.DRYSTONE_SET.carvedWindows.block(), 2)
                        .pattern("EEE")
                        .pattern("EGE")
                        .pattern("EEE")
                        .input('E', StoneBlockSetRegistryME.DRYSTONE_SET.cobblestoneBlocks.base())
                        .input('G', Items.GLASS)
                        .criterion(hasItem(StoneBlockSetRegistryME.DRYSTONE_SET.cobblestoneBlocks.base()),
                                conditionsFromItem(StoneBlockSetRegistryME.DRYSTONE_SET.cobblestoneBlocks.base()))
                        .offerTo(exporter);
                createPaneRecipe(exporter, StoneBlockSetRegistryME.DRYSTONE_SET.carvedWindows.block().asItem(), StoneBlockSetRegistryME.DRYSTONE_SET.carvedWindows.verticalSlab(), 12);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, DecorativeBlockRegistryME.LEAD_GLASS, 4)
                        .pattern("LGL")
                        .pattern("GLG")
                        .pattern("LGL")
                        .input('L', ResourceItemsME.LEAD_NUGGET)
                        .input('G', Items.GLASS)
                        .criterion(hasItem(ResourceItemsME.ROD),
                                conditionsFromItem(ResourceItemsME.ROD))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, DecorativeBlockRegistryME.ROPE, 3)
                        .pattern("SS")
                        .pattern("SS")
                        .pattern("SS")
                        .input('S', Items.STRING)
                        .criterion(hasItem(Items.STRING),
                                conditionsFromItem(Items.STRING))
                        .offerTo(exporter);

                createBrickRecipe(exporter, ResourceItemsME.ASH, BlockRegistryME.ASH_BLOCK, 1);
                createBrickRecipe(exporter, BlockRegistryME.ASH_BLOCK.asItem(), Blocks.TUFF, 1);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, StoneBlockSetRegistryME.ASHENSTONE_SET.baseBlocks.base(), 4)
                        .pattern("AS")
                        .pattern("SA")
                        .input('A', BlockRegistryME.ASH_BLOCK)
                        .input('S', Blocks.STONE)
                        .criterion(hasItem(BlockRegistryME.ASH_BLOCK),
                                conditionsFromItem(BlockRegistryME.ASH_BLOCK))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, StoneBlockSetRegistryME.GILDED_GREEN_TUFF_SET.baseBlocks.base(), 5)
                        .pattern("TNT")
                        .pattern("NTN")
                        .pattern("TNT")
                        .input('T', StoneBlockSetRegistryME.GREEN_TUFF_SET.baseBlocks.base())
                        .input('N', Items.GOLD_NUGGET)
                        .criterion(hasItem(StoneBlockSetRegistryME.GREEN_TUFF_SET.baseBlocks.base()),
                                conditionsFromItem(StoneBlockSetRegistryME.GREEN_TUFF_SET.baseBlocks.base()))
                        .offerTo(exporter);
                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, StoneBlockSetRegistryME.GILDED_GREEN_TUFF_SET.chiseledBricksBlocks.base(), 5)
                        .pattern("TNT")
                        .pattern("NTN")
                        .pattern("TNT")
                        .input('T', StoneBlockSetRegistryME.GREEN_TUFF_SET.chiseledBricksBlocks.base())
                        .input('N', Items.GOLD_NUGGET)
                        .criterion(hasItem(StoneBlockSetRegistryME.GREEN_TUFF_SET.chiseledBricksBlocks.base()),
                                conditionsFromItem(StoneBlockSetRegistryME.GREEN_TUFF_SET.chiseledBricksBlocks.base()))
                        .offerTo(exporter);
                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, StoneBlockSetRegistryME.GILDED_GREEN_TUFF_SET.chiseledTilesBlocks.base(), 5)
                        .pattern("TNT")
                        .pattern("NTN")
                        .pattern("TNT")
                        .input('T', StoneBlockSetRegistryME.GREEN_TUFF_SET.chiseledTilesBlocks.base())
                        .input('N', Items.GOLD_NUGGET)
                        .criterion(hasItem(StoneBlockSetRegistryME.GREEN_TUFF_SET.chiseledTilesBlocks.base()),
                                conditionsFromItem(StoneBlockSetRegistryME.GREEN_TUFF_SET.chiseledTilesBlocks.base()))
                        .offerTo(exporter);
                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, StoneBlockSetRegistryME.GILDED_GREEN_TUFF_SET.chiseledSmoothBlocks.base(), 5)
                        .pattern("TNT")
                        .pattern("NTN")
                        .pattern("TNT")
                        .input('T', StoneBlockSetRegistryME.GREEN_TUFF_SET.chiseledSmoothBlocks.base())
                        .input('N', Items.GOLD_NUGGET)
                        .criterion(hasItem(StoneBlockSetRegistryME.GREEN_TUFF_SET.chiseledSmoothBlocks.base()),
                                conditionsFromItem(StoneBlockSetRegistryME.GREEN_TUFF_SET.chiseledSmoothBlocks.base()))
                        .offerTo(exporter);
                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, StoneBlockSetRegistryME.GILDED_GREEN_TUFF_SET.chiseledPolishedBlocks.base(), 5)
                        .pattern("TNT")
                        .pattern("NTN")
                        .pattern("TNT")
                        .input('T', StoneBlockSetRegistryME.GREEN_TUFF_SET.chiseledPolishedBlocks.base())
                        .input('N', Items.GOLD_NUGGET)
                        .criterion(hasItem(StoneBlockSetRegistryME.GREEN_TUFF_SET.chiseledPolishedBlocks.base()),
                                conditionsFromItem(StoneBlockSetRegistryME.GREEN_TUFF_SET.chiseledPolishedBlocks.base()))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, StoneBlockSetRegistryME.BURZUM_GABBRO_SET.chiseledBlocks.base(), 5)
                        .pattern("TNT")
                        .pattern("NTN")
                        .pattern("TNT")
                        .input('T', StoneBlockSetRegistryME.GABBRO_SET.baseBlocks.base())
                        .input('N', ResourceItemsME.BURZUM_STEEL_NUGGET)
                        .criterion(hasItem(StoneBlockSetRegistryME.GABBRO_SET.baseBlocks.base()),
                                conditionsFromItem(StoneBlockSetRegistryME.GABBRO_SET.baseBlocks.base()))
                        .offerTo(exporter);
                //createStoneSetRecipes(StoneBlockSets.BURZUM_GABBRO_SET.chiseledBlocks);

                createBrickRecipe(exporter, StoneBlockSetRegistryME.BURZUM_GABBRO_SET.chiseledBlocks.base().asItem(), StoneBlockSetRegistryME.BURZUM_GABBRO_SET.chiseledBricksBlocks.base(), 4);
                //createStoneSetRecipes(StoneBlockSets.BURZUM_GABBRO_SET.chiseledBricksBlocks);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, StoneBlockSetRegistryME.BURZUM_GABBRO_SET.chiseledSmoothBlocks.base(), 5)
                        .pattern("TNT")
                        .pattern("NTN")
                        .pattern("TNT")
                        .input('T', StoneBlockSetRegistryME.GABBRO_SET.smoothBlocks.base())
                        .input('N', ResourceItemsME.BURZUM_STEEL_NUGGET)
                        .criterion(hasItem(StoneBlockSetRegistryME.GABBRO_SET.smoothBlocks.base()),
                                conditionsFromItem(StoneBlockSetRegistryME.GABBRO_SET.smoothBlocks.base()))
                        .offerTo(exporter);
                //createStoneSetRecipes(StoneBlockSets.BURZUM_GABBRO_SET.chiseledSmoothBlocks);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, StoneBlockSetRegistryME.BURZUM_GABBRO_SET.chiseledPolishedBlocks.base(), 5)
                        .pattern("TNT")
                        .pattern("NTN")
                        .pattern("TNT")
                        .input('T', StoneBlockSetRegistryME.GABBRO_SET.polishedBlocks.base())
                        .input('N', ResourceItemsME.BURZUM_STEEL_NUGGET)
                        .criterion(hasItem(StoneBlockSetRegistryME.GABBRO_SET.polishedBlocks.base()),
                                conditionsFromItem(StoneBlockSetRegistryME.GABBRO_SET.polishedBlocks.base()))
                        .offerTo(exporter);
                //createStoneSetRecipes(StoneBlockSets.BURZUM_GABBRO_SET.chiseledPolishedBlocks);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, StoneBlockSetRegistryME.BURZUM_GABBRO_SET.chiseledTilesBlocks.base(), 5)
                        .pattern("TNT")
                        .pattern("NTN")
                        .pattern("TNT")
                        .input('T', StoneBlockSetRegistryME.GABBRO_SET.tileBlocks.base())
                        .input('N', ResourceItemsME.BURZUM_STEEL_NUGGET)
                        .criterion(hasItem(StoneBlockSetRegistryME.GABBRO_SET.tileBlocks.base()),
                                conditionsFromItem(StoneBlockSetRegistryME.GABBRO_SET.tileBlocks.base()))
                        .offerTo(exporter);
                //createStoneSetRecipes(StoneBlockSets.BURZUM_GABBRO_SET.chiseledTilesBlocks);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, BlockRegistryME.WATTLE_TRAPDOOR, 2)
                        .pattern("PLP")
                        .pattern("PLP")
                        .input('P', TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input('L', ResourceItemsME.LEAD_NUGGET)
                        .criterion(hasItem(ResourceItemsME.LEAD_NUGGET),
                                conditionsFromItem(ResourceItemsME.LEAD_NUGGET))
                        .offerTo(exporter);

                createDyeableItemRecipe(exporter, BlockRegistryME.WATTLE_TRAPDOOR, Items.RED_DYE, BlockRegistryME.RED_WATTLE_TRAPDOOR);
                createDyeableItemRecipe(exporter, BlockRegistryME.WATTLE_TRAPDOOR, Items.GREEN_DYE, BlockRegistryME.GREEN_WATTLE_TRAPDOOR);
                createDyeableItemRecipe(exporter, BlockRegistryME.WATTLE_TRAPDOOR, Items.BROWN_DYE, BlockRegistryME.DARK_WATTLE_TRAPDOOR);
                createDyeableItemRecipe(exporter, BlockRegistryME.WATTLE_TRAPDOOR, Items.BLACK_DYE, BlockRegistryME.BLACK_WATTLE_TRAPDOOR);

                //createBrickworkBlockRecipe(exporter, StoneBlockSets.STONE_SET.tileBlocks.base(), GenericBlockSets.STUCCO.blockSet.base(), StoneBlockSets.STONE_SET.brickworkBlocks.base());
                //createBrickworkBlockRecipe(exporter, StoneBlockSets.CALCITE_SET.tileBlocks.base(), GenericBlockSets.STUCCO.blockSet.base(), StoneBlockSets.CALCITE_SET.brickworkBlocks.base());
                //createBrickworkBlockRecipe(exporter, Blocks.DEEPSLATE_TILES, GenericBlockSets.STUCCO.blockSet.base(), StoneBlockSets.DEEPSLATE_SET.brickworkBlocks.base());
                //createBrickworkBlockRecipe(exporter, StoneBlockSets.BASALT_SET.tileBlocks.base(), GenericBlockSets.STUCCO.blockSet.base(), StoneBlockSets.BASALT_SET.brickworkBlocks.base());
                //createBrickworkBlockRecipe(exporter, StoneBlockSets.ANDESITE_SET.tileBlocks.base(), GenericBlockSets.STUCCO.blockSet.base(),StoneBlockSets.ANDESITE_SET.brickworkBlocks.base());
                //createBrickworkBlockRecipe(exporter, StoneBlockSets.DIORITE_SET.tileBlocks.base(), GenericBlockSets.STUCCO.blockSet.base(), StoneBlockSets.DIORITE_SET.brickworkBlocks.base());
                //createBrickworkBlockRecipe(exporter, StoneBlockSets.GRANITE_SET.tileBlocks.base(), GenericBlockSets.STUCCO.blockSet.base(), StoneBlockSets.GRANITE_SET.brickworkBlocks.base());

                createBrickRecipe(exporter, StoneBlockSetRegistryME.QUARTZITE_SET.brickBlocks.base().asItem(), StoneBlockSetRegistryME.QUARTZITE_SET.tileBlocks.base(), 4);
                createBrickRecipe(exporter, GenericBlockSetRegistryME.PACKED_MIRE.blockSet.base().asItem(), GenericBlockSetRegistryME.MIRE_BRICKS.blockSet.base(), 4);

                createMossyRecipe(exporter, GenericBlockSetRegistryME.MIXED_STONES.blockSet.base(), GenericBlockSetRegistryME.MOSSY_MIXED_STONES.blockSet.base());
                offerSmelting(List.of(GenericBlockSetRegistryME.MIXED_STONES.blockSet.base()), RecipeCategory.BUILDING_BLOCKS,
                        GenericBlockSetRegistryME.CRACKED_MIXED_STONES.blockSet.base(), 0.1f, 200, "cracked_bricks");

                createBrickRecipe(exporter, Blocks.BRICKS.asItem(), GenericBlockSetRegistryME.CLAY_TILING.blockSet.base(), 4);
                createCenterSurroundRecipe(exporter, GenericBlockSetRegistryME.CLAY_TILING.blockSet.base().asItem(), Items.BLACK_DYE, GenericBlockSetRegistryME.BLACK_CLAY_TILING.blockSet.base().asItem(), 8);
                createCenterSurroundRecipe(exporter, GenericBlockSetRegistryME.CLAY_TILING.blockSet.base().asItem(), Items.BLUE_DYE, GenericBlockSetRegistryME.BLUE_CLAY_TILING.blockSet.base().asItem(), 8);
                createCenterSurroundRecipe(exporter, GenericBlockSetRegistryME.CLAY_TILING.blockSet.base().asItem(), Items.BROWN_DYE, GenericBlockSetRegistryME.BROWN_CLAY_TILING.blockSet.base().asItem(), 8);
                createCenterSurroundRecipe(exporter, GenericBlockSetRegistryME.CLAY_TILING.blockSet.base().asItem(), Items.CYAN_DYE, GenericBlockSetRegistryME.CYAN_CLAY_TILING.blockSet.base().asItem(), 8);
                createCenterSurroundRecipe(exporter, GenericBlockSetRegistryME.CLAY_TILING.blockSet.base().asItem(), Items.GRAY_DYE, GenericBlockSetRegistryME.GRAY_CLAY_TILING.blockSet.base().asItem(), 8);
                createCenterSurroundRecipe(exporter, GenericBlockSetRegistryME.CLAY_TILING.blockSet.base().asItem(), Items.GREEN_DYE, GenericBlockSetRegistryME.GREEN_CLAY_TILING.blockSet.base().asItem(), 8);
                createCenterSurroundRecipe(exporter, GenericBlockSetRegistryME.CLAY_TILING.blockSet.base().asItem(), Items.LIGHT_BLUE_DYE, GenericBlockSetRegistryME.LIGHT_BLUE_CLAY_TILING.blockSet.base().asItem(), 8);
                createCenterSurroundRecipe(exporter, GenericBlockSetRegistryME.CLAY_TILING.blockSet.base().asItem(), Items.LIGHT_GRAY_DYE, GenericBlockSetRegistryME.LIGHT_GRAY_CLAY_TILING.blockSet.base().asItem(), 8);
                createCenterSurroundRecipe(exporter, GenericBlockSetRegistryME.CLAY_TILING.blockSet.base().asItem(), Items.LIME_DYE, GenericBlockSetRegistryME.LIME_CLAY_TILING.blockSet.base().asItem(), 8);
                createCenterSurroundRecipe(exporter, GenericBlockSetRegistryME.CLAY_TILING.blockSet.base().asItem(), Items.MAGENTA_DYE, GenericBlockSetRegistryME.MAGENTA_CLAY_TILING.blockSet.base().asItem(), 8);
                createCenterSurroundRecipe(exporter, GenericBlockSetRegistryME.CLAY_TILING.blockSet.base().asItem(), Items.ORANGE_DYE, GenericBlockSetRegistryME.ORANGE_CLAY_TILING.blockSet.base().asItem(), 8);
                createCenterSurroundRecipe(exporter, GenericBlockSetRegistryME.CLAY_TILING.blockSet.base().asItem(), Items.PINK_DYE, GenericBlockSetRegistryME.PINK_CLAY_TILING.blockSet.base().asItem(), 8);
                createCenterSurroundRecipe(exporter, GenericBlockSetRegistryME.CLAY_TILING.blockSet.base().asItem(), Items.PURPLE_DYE, GenericBlockSetRegistryME.PURPLE_CLAY_TILING.blockSet.base().asItem(), 8);
                createCenterSurroundRecipe(exporter, GenericBlockSetRegistryME.CLAY_TILING.blockSet.base().asItem(), Items.RED_DYE, GenericBlockSetRegistryME.RED_CLAY_TILING.blockSet.base().asItem(), 8);
                createCenterSurroundRecipe(exporter, GenericBlockSetRegistryME.CLAY_TILING.blockSet.base().asItem(), Items.WHITE_DYE, GenericBlockSetRegistryME.WHITE_CLAY_TILING.blockSet.base().asItem(), 8);
                createCenterSurroundRecipe(exporter, GenericBlockSetRegistryME.CLAY_TILING.blockSet.base().asItem(), Items.YELLOW_DYE, GenericBlockSetRegistryME.YELLOW_CLAY_TILING.blockSet.base().asItem(), 8);

                //createBrickworkBlockRecipe(exporter, StoneBlockSets.DOLOMITE_SET.tileBlocks.base(), GenericBlockSets.STUCCO.blockSet.base(), StoneBlockSets.DOLOMITE_SET.brickworkBlocks.base());
                //createBrickworkBlockRecipe(exporter, StoneBlockSets.HEMATITE_SET.tileBlocks.base(), GenericBlockSets.STUCCO.blockSet.base(), StoneBlockSets.HEMATITE_SET.brickworkBlocks.base());
                //createBrickworkBlockRecipe(exporter, StoneBlockSets.GNEISS_SET.tileBlocks.base(), GenericBlockSets.STUCCO.blockSet.base(), StoneBlockSets.GNEISS_SET.brickworkBlocks.base());
                //createBrickworkBlockRecipe(exporter, StoneBlockSets.IZHERABAN_SET.tileBlocks.base(), GenericBlockSets.STUCCO.blockSet.base(), StoneBlockSets.IZHERABAN_SET.brickworkBlocks.base());
                //createBrickworkBlockRecipe(exporter, StoneBlockSets.LIMESTONE_SET.tileBlocks.base(), GenericBlockSets.STUCCO.blockSet.base(), StoneBlockSets.LIMESTONE_SET.brickworkBlocks.base());
                //createBrickworkBlockRecipe(exporter, StoneBlockSets.GALONN_SET.tileBlocks.base(), GenericBlockSets.STUCCO.blockSet.base(), StoneBlockSets.GALONN_SET.brickworkBlocks.base());
                //createBrickworkBlockRecipe(exporter, StoneBlockSets.GABBRO_SET.brickBlocks.base(), GenericBlockSets.STUCCO.blockSet.base(), StoneBlockSets.GABBRO_SET.brickworkBlocks.base());
                //createBrickworkBlockRecipe(exporter, StoneBlockSets.TUFF_SET.tileBlocks.base(), GenericBlockSets.STUCCO.blockSet.base(), StoneBlockSets.TUFF_SET.brickworkBlocks.base());
                //createBrickworkBlockRecipe(exporter, StoneBlockSets.BLACKSTONE_SET.tileBlocks.base(), GenericBlockSets.STUCCO.blockSet.base(), StoneBlockSets.BLACKSTONE_SET.brickworkBlocks.base());
                createBrickworkBlockRecipe(exporter, StoneBlockSetRegistryME.TAN_CLAY.brickBlocks.base(), GenericBlockSetRegistryME.PLASTER.blockSet.base(), StoneBlockSetRegistryME.TAN_CLAY.brickworkBlocks.base());
                createBrickworkBlockRecipe(exporter, GenericBlockSetRegistryME.MIXED_STONES.blockSet.base(), GenericBlockSetRegistryME.STUCCO.blockSet.base(), GenericBlockSetRegistryME.MIXED_STONES_BRICKWORK.blockSet.base());
                //createBrickworkBlockRecipe(exporter, StoneBlockSets.MEDGON_SET.baseBlocks.base(), GenericBlockSets.STUCCO.blockSet.base(), StoneBlockSets.MEDGON_SET.brickworkBlocks.base());

                createCenterSurroundRecipe(exporter, Items.BRICK, Items.BLUE_DYE, GenericBlockSetRegistryME.BLUE_ROOF_TILES.blockSet.base().asItem(), 2);
                createCenterSurroundRecipe(exporter, GenericBlockSetRegistryME.BLUE_ROOF_TILES.blockSet.base().asItem(), Items.WHITE_DYE, GenericBlockSetRegistryME.LIGHT_BLUE_ROOF_TILES.blockSet.base().asItem(), 8);
                createCenterSurroundRecipe(exporter, GenericBlockSetRegistryME.BLUE_ROOF_TILES.blockSet.base().asItem(), Items.LIGHT_GRAY_DYE, GenericBlockSetRegistryME.BRIGHT_BLUE_ROOF_TILES.blockSet.base().asItem(), 8);
                createCenterSurroundRecipe(exporter, GenericBlockSetRegistryME.BLUE_ROOF_TILES.blockSet.base().asItem(), Items.GRAY_DYE, GenericBlockSetRegistryME.OFF_BLUE_ROOF_TILES.blockSet.base().asItem(), 8);
                createCenterSurroundRecipe(exporter, GenericBlockSetRegistryME.BLUE_ROOF_TILES.blockSet.base().asItem(), Items.BLACK_DYE, GenericBlockSetRegistryME.DARK_BLUE_ROOF_TILES.blockSet.base().asItem(), 8);

                createCenterSurroundRecipe(exporter, Items.BRICK, Items.BROWN_DYE, GenericBlockSetRegistryME.BROWN_ROOF_TILES.blockSet.base().asItem(), 2);
                createCenterSurroundRecipe(exporter, GenericBlockSetRegistryME.BROWN_ROOF_TILES.blockSet.base().asItem(), Items.GRAY_DYE, GenericBlockSetRegistryME.OFF_BROWN_ROOF_TILES.blockSet.base().asItem(), 8);
                createCenterSurroundRecipe(exporter, GenericBlockSetRegistryME.BROWN_ROOF_TILES.blockSet.base().asItem(), Items.BLACK_DYE, GenericBlockSetRegistryME.DARK_BROWN_ROOF_TILES.blockSet.base().asItem(), 8);

                createCenterSurroundRecipe(exporter, Items.BRICK, Items.CYAN_DYE, GenericBlockSetRegistryME.CYAN_ROOF_TILES.blockSet.base().asItem(), 2);
                createCenterSurroundRecipe(exporter, GenericBlockSetRegistryME.CYAN_ROOF_TILES.blockSet.base().asItem(), Items.WHITE_DYE, GenericBlockSetRegistryME.LIGHT_CYAN_ROOF_TILES.blockSet.base().asItem(), 8);
                createCenterSurroundRecipe(exporter, GenericBlockSetRegistryME.CYAN_ROOF_TILES.blockSet.base().asItem(), Items.LIGHT_GRAY_DYE, GenericBlockSetRegistryME.BRIGHT_CYAN_ROOF_TILES.blockSet.base().asItem(), 8);
                createCenterSurroundRecipe(exporter, GenericBlockSetRegistryME.CYAN_ROOF_TILES.blockSet.base().asItem(), Items.GRAY_DYE, GenericBlockSetRegistryME.OFF_CYAN_ROOF_TILES.blockSet.base().asItem(), 8);
                createCenterSurroundRecipe(exporter, GenericBlockSetRegistryME.CYAN_ROOF_TILES.blockSet.base().asItem(), Items.BLACK_DYE, GenericBlockSetRegistryME.DARK_CYAN_ROOF_TILES.blockSet.base().asItem(), 8);

                createCenterSurroundRecipe(exporter, Items.BRICK, Items.GRAY_DYE, GenericBlockSetRegistryME.GRAY_ROOF_TILES.blockSet.base().asItem(), 2);
                createCenterSurroundRecipe(exporter, GenericBlockSetRegistryME.GRAY_ROOF_TILES.blockSet.base().asItem(), Items.WHITE_DYE, GenericBlockSetRegistryME.LIGHT_GRAY_ROOF_TILES.blockSet.base().asItem(), 8);
                createCenterSurroundRecipe(exporter, GenericBlockSetRegistryME.GRAY_ROOF_TILES.blockSet.base().asItem(), Items.GRAY_DYE, GenericBlockSetRegistryME.OFF_GRAY_ROOF_TILES.blockSet.base().asItem(), 8);
                createCenterSurroundRecipe(exporter, GenericBlockSetRegistryME.GRAY_ROOF_TILES.blockSet.base().asItem(), Items.BLACK_DYE, GenericBlockSetRegistryME.DARK_GRAY_ROOF_TILES.blockSet.base().asItem(), 8);

                createCenterSurroundRecipe(exporter, Items.BRICK, Items.GREEN_DYE, GenericBlockSetRegistryME.GREEN_ROOF_TILES.blockSet.base().asItem(), 2);
                createCenterSurroundRecipe(exporter, GenericBlockSetRegistryME.GREEN_ROOF_TILES.blockSet.base().asItem(), Items.WHITE_DYE, GenericBlockSetRegistryME.LIGHT_GREEN_ROOF_TILES.blockSet.base().asItem(), 8);
                createCenterSurroundRecipe(exporter, GenericBlockSetRegistryME.GREEN_ROOF_TILES.blockSet.base().asItem(), Items.LIGHT_GRAY_DYE, GenericBlockSetRegistryME.BRIGHT_GREEN_ROOF_TILES.blockSet.base().asItem(), 8);
                createCenterSurroundRecipe(exporter, GenericBlockSetRegistryME.GREEN_ROOF_TILES.blockSet.base().asItem(), Items.GRAY_DYE, GenericBlockSetRegistryME.OFF_GREEN_ROOF_TILES.blockSet.base().asItem(), 8);
                createCenterSurroundRecipe(exporter, GenericBlockSetRegistryME.GREEN_ROOF_TILES.blockSet.base().asItem(), Items.BLACK_DYE, GenericBlockSetRegistryME.DARK_GREEN_ROOF_TILES.blockSet.base().asItem(), 8);

                createCenterSurroundRecipe(exporter, Items.BRICK, Items.RED_DYE, GenericBlockSetRegistryME.RED_ROOF_TILES.blockSet.base().asItem(), 2);
                createCenterSurroundRecipe(exporter, GenericBlockSetRegistryME.RED_ROOF_TILES.blockSet.base().asItem(), Items.WHITE_DYE, GenericBlockSetRegistryME.LIGHT_RED_ROOF_TILES.blockSet.base().asItem(), 8);
                createCenterSurroundRecipe(exporter, GenericBlockSetRegistryME.RED_ROOF_TILES.blockSet.base().asItem(), Items.LIGHT_GRAY_DYE, GenericBlockSetRegistryME.BRIGHT_RED_ROOF_TILES.blockSet.base().asItem(), 8);
                createCenterSurroundRecipe(exporter, GenericBlockSetRegistryME.RED_ROOF_TILES.blockSet.base().asItem(), Items.GRAY_DYE, GenericBlockSetRegistryME.OFF_RED_ROOF_TILES.blockSet.base().asItem(), 8);
                createCenterSurroundRecipe(exporter, GenericBlockSetRegistryME.RED_ROOF_TILES.blockSet.base().asItem(), Items.BLACK_DYE, GenericBlockSetRegistryME.DARK_RED_ROOF_TILES.blockSet.base().asItem(), 8);

                createCenterSurroundRecipe(exporter, Items.BRICK, Items.YELLOW_DYE, GenericBlockSetRegistryME.YELLOW_ROOF_TILES.blockSet.base().asItem(), 2);
                createCenterSurroundRecipe(exporter, GenericBlockSetRegistryME.YELLOW_ROOF_TILES.blockSet.base().asItem(), Items.WHITE_DYE, GenericBlockSetRegistryME.LIGHT_YELLOW_ROOF_TILES.blockSet.base().asItem(), 8);
                createCenterSurroundRecipe(exporter, GenericBlockSetRegistryME.YELLOW_ROOF_TILES.blockSet.base().asItem(), Items.LIGHT_GRAY_DYE, GenericBlockSetRegistryME.BRIGHT_YELLOW_ROOF_TILES.blockSet.base().asItem(), 8);
                createCenterSurroundRecipe(exporter, GenericBlockSetRegistryME.YELLOW_ROOF_TILES.blockSet.base().asItem(), Items.GRAY_DYE, GenericBlockSetRegistryME.OFF_YELLOW_ROOF_TILES.blockSet.base().asItem(), 8);
                createCenterSurroundRecipe(exporter, GenericBlockSetRegistryME.YELLOW_ROOF_TILES.blockSet.base().asItem(), Items.BLACK_DYE, GenericBlockSetRegistryME.DARK_YELLOW_ROOF_TILES.blockSet.base().asItem(), 8);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, StoneBlockSetRegistryME.TAN_CLAY.brickBlocks.base(), 5)
                        .pattern(" B ")
                        .pattern("BPB")
                        .pattern(" B ")
                        .input('P', GenericBlockSetRegistryME.PLASTER.blockSet.base())
                        .input('B', Items.BRICKS)
                        .criterion(hasItem(GenericBlockSetRegistryME.PLASTER.blockSet.base()),
                                conditionsFromItem(GenericBlockSetRegistryME.PLASTER.blockSet.base()))
                        .offerTo(exporter);
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

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, DecorativeItemsME.REINFORCED_SCAFFOLDING, 6)
                        .pattern("LCL")
                        .pattern("S S")
                        .pattern("T T")
                        .input('L', TagKey.of(RegistryKeys.ITEM, Identifier.of("c","stripped_logs")))
                        .input('C', GenericBlockSetRegistryME.CANVAS.blockSet.base())
                        .input('T', ResourceItemsME.TIN_INGOT)
                        .input('S', Items.STICK)
                        .criterion(hasItem(ResourceItemsME.TIN_INGOT),
                                conditionsFromItem(ResourceItemsME.TIN_INGOT))
                        .offerTo(exporter, "reinforced_scaffolding");

                //region CANVAS
                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, GenericBlockSetRegistryME.CANVAS.blockSet.base(), 3)
                        .pattern("FF")
                        .pattern("FF")
                        .input('F', ResourceItemsME.FABRIC)
                        .criterion(hasItem(ResourceItemsME.FABRIC),
                                conditionsFromItem(ResourceItemsME.FABRIC))
                        .offerTo(exporter);

                createCenterSurroundRecipe(exporter, GenericBlockSetRegistryME.CANVAS.blockSet.base().asItem(), Items.WHITE_DYE, GenericBlockSetRegistryME.WHITE_CANVAS.blockSet.base().asItem(), 8);
                createCenterSurroundRecipe(exporter, GenericBlockSetRegistryME.CANVAS.blockSet.base().asItem(), Items.BLACK_DYE, GenericBlockSetRegistryME.BLACK_CANVAS.blockSet.base().asItem(), 8);
                createCenterSurroundRecipe(exporter, GenericBlockSetRegistryME.CANVAS.blockSet.base().asItem(), Items.BLUE_DYE, GenericBlockSetRegistryME.BLUE_CANVAS.blockSet.base().asItem(), 8);
                createCenterSurroundRecipe(exporter, GenericBlockSetRegistryME.CANVAS.blockSet.base().asItem(), Items.BROWN_DYE, GenericBlockSetRegistryME.BROWN_CANVAS.blockSet.base().asItem(), 8);
                createCenterSurroundRecipe(exporter, GenericBlockSetRegistryME.CANVAS.blockSet.base().asItem(), Items.CYAN_DYE, GenericBlockSetRegistryME.CYAN_CANVAS.blockSet.base().asItem(), 8);
                createCenterSurroundRecipe(exporter, GenericBlockSetRegistryME.CANVAS.blockSet.base().asItem(), Items.GRAY_DYE, GenericBlockSetRegistryME.GRAY_CANVAS.blockSet.base().asItem(), 8);
                createCenterSurroundRecipe(exporter, GenericBlockSetRegistryME.CANVAS.blockSet.base().asItem(), Items.GREEN_DYE, GenericBlockSetRegistryME.GREEN_CANVAS.blockSet.base().asItem(), 8);
                createCenterSurroundRecipe(exporter, GenericBlockSetRegistryME.CANVAS.blockSet.base().asItem(), Items.LIGHT_BLUE_DYE, GenericBlockSetRegistryME.LIGHT_BLUE_CANVAS.blockSet.base().asItem(), 8);
                createCenterSurroundRecipe(exporter, GenericBlockSetRegistryME.CANVAS.blockSet.base().asItem(), Items.LIGHT_GRAY_DYE, GenericBlockSetRegistryME.LIGHT_GRAY_CANVAS.blockSet.base().asItem(), 8);
                createCenterSurroundRecipe(exporter, GenericBlockSetRegistryME.CANVAS.blockSet.base().asItem(), Items.MAGENTA_DYE, GenericBlockSetRegistryME.MAGENTA_CANVAS.blockSet.base().asItem(), 8);
                createCenterSurroundRecipe(exporter, GenericBlockSetRegistryME.CANVAS.blockSet.base().asItem(), Items.ORANGE_DYE, GenericBlockSetRegistryME.ORANGE_CANVAS.blockSet.base().asItem(), 8);
                createCenterSurroundRecipe(exporter, GenericBlockSetRegistryME.CANVAS.blockSet.base().asItem(), Items.PINK_DYE, GenericBlockSetRegistryME.PINK_CANVAS.blockSet.base().asItem(), 8);
                createCenterSurroundRecipe(exporter, GenericBlockSetRegistryME.CANVAS.blockSet.base().asItem(), Items.PURPLE_DYE, GenericBlockSetRegistryME.PURPLE_CANVAS.blockSet.base().asItem(), 8);
                createCenterSurroundRecipe(exporter, GenericBlockSetRegistryME.CANVAS.blockSet.base().asItem(), Items.RED_DYE, GenericBlockSetRegistryME.RED_CANVAS.blockSet.base().asItem(), 8);
                createCenterSurroundRecipe(exporter, GenericBlockSetRegistryME.CANVAS.blockSet.base().asItem(), Items.YELLOW_DYE, GenericBlockSetRegistryME.YELLOW_CANVAS.blockSet.base().asItem(), 8);

                createCenterSurroundRecipe(exporter, GenericBlockSetRegistryME.BLUE_CANVAS.blockSet.base().asItem(), Items.GRAY_DYE, GenericBlockSetRegistryME.DARK_BLUE_CANVAS.blockSet.base().asItem(), 8);
                createCenterSurroundRecipe(exporter, GenericBlockSetRegistryME.BROWN_CANVAS.blockSet.base().asItem(), Items.GRAY_DYE, GenericBlockSetRegistryME.DARK_BROWN_CANVAS.blockSet.base().asItem(), 8);
                createCenterSurroundRecipe(exporter, GenericBlockSetRegistryME.GRAY_CANVAS.blockSet.base().asItem(), Items.GRAY_DYE, GenericBlockSetRegistryME.DARK_GRAY_CANVAS.blockSet.base().asItem(), 8);
                createCenterSurroundRecipe(exporter, GenericBlockSetRegistryME.GREEN_CANVAS.blockSet.base().asItem(), Items.GRAY_DYE, GenericBlockSetRegistryME.DARK_GREEN_CANVAS.blockSet.base().asItem(), 8);
                createCenterSurroundRecipe(exporter, GenericBlockSetRegistryME.RED_CANVAS.blockSet.base().asItem(), Items.GRAY_DYE, GenericBlockSetRegistryME.DARK_RED_CANVAS.blockSet.base().asItem(), 8);
                createCenterSurroundRecipe(exporter, GenericBlockSetRegistryME.YELLOW_CANVAS.blockSet.base().asItem(), Items.GRAY_DYE, GenericBlockSetRegistryME.DARK_YELLOW_CANVAS.blockSet.base().asItem(), 8);
                //endregion

                createBucketRecipe(exporter, Items.IRON_INGOT, Items.BUCKET);

                createMetalsRecipe(exporter, ResourceItemsME.TIN_NUGGET, ResourceItemsME.TIN_INGOT, BlockRegistryME.TIN_BLOCK);
                createMetalsRecipe(exporter, ResourceItemsME.LEAD_NUGGET, ResourceItemsME.LEAD_INGOT, BlockRegistryME.LEAD_BLOCK);
                createMetalsRecipe(exporter, ResourceItemsME.SILVER_NUGGET, ResourceItemsME.SILVER_INGOT, BlockRegistryME.SILVER_BLOCK);
                createMetalsRecipe(exporter, ResourceItemsME.MITHRIL_NUGGET, ResourceItemsME.MITHRIL_INGOT, BlockRegistryME.MITHRIL_BLOCK);

                createMetalsRecipe(exporter, ResourceItemsME.BRONZE_NUGGET, ResourceItemsME.BRONZE_INGOT, BlockRegistryME.BRONZE_BLOCK);
                createMetalsRecipe(exporter, ResourceItemsME.CRUDE_NUGGET, ResourceItemsME.CRUDE_INGOT, BlockRegistryME.CRUDE_BLOCK);
                createMetalsRecipe(exporter, ResourceItemsME.BURZUM_STEEL_NUGGET, ResourceItemsME.BURZUM_STEEL_INGOT, BlockRegistryME.BURZUM_STEEL_BLOCK);
                createMetalsRecipe(exporter, ResourceItemsME.STEEL_NUGGET, ResourceItemsME.STEEL_INGOT, BlockRegistryME.STEEL_BLOCK);
                createMetalsRecipe(exporter, ResourceItemsME.EDHEL_STEEL_NUGGET, ResourceItemsME.EDHEL_STEEL_INGOT, BlockRegistryME.EDHEL_STEEL_BLOCK);
                createMetalsRecipe(exporter, ResourceItemsME.KHAZAD_STEEL_NUGGET, ResourceItemsME.KHAZAD_STEEL_INGOT, BlockRegistryME.KHAZAD_STEEL_BLOCK);

                ShapelessRecipeJsonBuilder.create(this.itemLookup, RecipeCategory.MISC, ResourceItemsME.ADAMANT, 9)
                        .input(BlockRegistryME.ADAMANT_BLOCK)
                        .criterion(hasItem(BlockRegistryME.ADAMANT_BLOCK),
                                conditionsFromItem(BlockRegistryME.ADAMANT_BLOCK))
                        .offerTo(exporter);

                ShapelessRecipeJsonBuilder.create(this.itemLookup, RecipeCategory.MISC, ResourceItemsME.RUBY, 9)
                        .input(BlockRegistryME.RUBY_BLOCK)
                        .criterion(hasItem(BlockRegistryME.RUBY_BLOCK),
                                conditionsFromItem(BlockRegistryME.RUBY_BLOCK))
                        .offerTo(exporter);

                ShapelessRecipeJsonBuilder.create(this.itemLookup, RecipeCategory.MISC, ResourceItemsME.SAPPHIRE, 9)
                        .input(BlockRegistryME.SAPPHIRE_BLOCK)
                        .criterion(hasItem(BlockRegistryME.SAPPHIRE_BLOCK),
                                conditionsFromItem(BlockRegistryME.SAPPHIRE_BLOCK))
                        .offerTo(exporter);
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
                AlloyRecipeJsonBuilder.createAlloyRecipe(itemLookup, RecipeCategory.MISC, "bronze", INGOT_LIQUID_VALUE * 4,  4)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "copper")))
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "copper")))
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "copper")))
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "tin")))
                        .criterion(hasItem(Items.COPPER_INGOT),
                                conditionsFromItem(Items.COPPER_INGOT))
                        .offerTo(exporter, String.valueOf(Identifier.of(MiddleEarth.MOD_ID, "bronze" + "_from_alloying")));

                AlloyRecipeJsonBuilder.createAlloyRecipe(itemLookup, RecipeCategory.MISC, "crude", INGOT_LIQUID_VALUE * 3, 3)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "copper")))
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "copper")))
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "tin")))
                        .input(ResourceItemsME.ASH)
                        .criterion(hasItem(Items.COPPER_INGOT),
                                conditionsFromItem(Items.COPPER_INGOT))
                        .offerTo(exporter, String.valueOf(Identifier.of(MiddleEarth.MOD_ID, "crude" + "_from_alloying")));

                AlloyRecipeJsonBuilder.createAlloyRecipe(itemLookup, RecipeCategory.MISC, "steel", INGOT_LIQUID_VALUE * 3, 3)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "iron")))
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "iron")))
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "iron")))
                        .input(Items.COAL)
                        .criterion(hasItem(Items.IRON_INGOT),
                                conditionsFromItem(Items.IRON_INGOT))
                        .offerTo(exporter, String.valueOf(Identifier.of(MiddleEarth.MOD_ID, "steel" + "_from_alloying_tags")));

                AlloyRecipeJsonBuilder.createAlloyRecipe(itemLookup, RecipeCategory.MISC, "khazad_steel", INGOT_LIQUID_VALUE * 3, 3)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "iron")))
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "iron")))
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "lead")))
                        .input(Items.COAL)
                        .criterion(hasItem(Items.IRON_INGOT),
                                conditionsFromItem(Items.IRON_INGOT))
                        .offerTo(exporter, String.valueOf(Identifier.of(MiddleEarth.MOD_ID, "khazad_steel" + "_from_alloying_tags")));

                AlloyRecipeJsonBuilder.createAlloyRecipe(itemLookup, RecipeCategory.MISC, "edhel_steel", INGOT_LIQUID_VALUE * 3, 3)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "iron")))
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "iron")))
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "iron")))
                        .input(ResourceItemsME.SILVER_NUGGET)
                        .criterion(hasItem(Items.IRON_INGOT),
                                conditionsFromItem(Items.IRON_INGOT))
                        .offerTo(exporter, String.valueOf(Identifier.of(MiddleEarth.MOD_ID, "edhel_steel" + "_from_alloying_tags")));

                AlloyRecipeJsonBuilder.createAlloyRecipe(itemLookup, RecipeCategory.MISC, "burzum_steel", INGOT_LIQUID_VALUE * 3, 3)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "iron")))
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "iron")))
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "lead")))
                        .input(ResourceItemsME.ASH)
                        .criterion(hasItem(Items.IRON_INGOT),
                                conditionsFromItem(Items.IRON_INGOT))
                        .offerTo(exporter, String.valueOf(Identifier.of(MiddleEarth.MOD_ID, "burzum_steel" + "_from_alloying_tags")));

                AlloyRecipeJsonBuilder.createAlloyRecipe(itemLookup, RecipeCategory.MISC, "chicken_nugget", INGOT_LIQUID_VALUE, 1)
                        .input(Items.CHICKEN)
                        .input(Items.WHEAT)
                        .input(Items.EGG)
                        .input(FoodItemsME.GARLIC)
                        .criterion(hasItem(ResourceItemsME.PTEROSAUR_NUGGET),
                                conditionsFromItem(ResourceItemsME.PTEROSAUR_NUGGET))
                        .offerTo(exporter, String.valueOf(Identifier.of(MiddleEarth.MOD_ID, "chicken_nugget" + "_from_alloying")));


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

                createAnvilRecipe(exporter, BlockRegistryME.STEEL_BLOCK.asItem(), ResourceItemsME.STEEL_INGOT, DecorativeItemsME.TREATED_ANVIL);
                createAnvilRecipe(exporter, BlockRegistryME.KHAZAD_STEEL_BLOCK.asItem(), ResourceItemsME.KHAZAD_STEEL_INGOT, DecorativeItemsME.DWARVEN_TREATED_ANVIL);
                createAnvilRecipe(exporter, BlockRegistryME.EDHEL_STEEL_BLOCK.asItem(), ResourceItemsME.EDHEL_STEEL_INGOT, DecorativeItemsME.ELVEN_TREATED_ANVIL);
                createAnvilRecipe(exporter, BlockRegistryME.BURZUM_STEEL_BLOCK.asItem(), ResourceItemsME.BURZUM_STEEL_INGOT, DecorativeItemsME.ORCISH_TREATED_ANVIL);

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
                        BlockRegistryME.WATTLE_AND_BRICK, BlockRegistryME.WATTLE_AND_BRICK_CROSS, BlockRegistryME.WATTLE_AND_BRICK_RIGHT,
                        BlockRegistryME.WATTLE_AND_BRICK_LEFT, BlockRegistryME.WATTLE_AND_BRICK_PILLAR, BlockRegistryME.WATTLE_AND_BRICK_DIAMOND);

                createWattleRecipes(exporter, GenericBlockSetRegistryME.WHITE_DAUB.blockSet.base().asItem(),
                        BlockRegistryME.WATTLE_AND_WHITE_DAUB, BlockRegistryME.WATTLE_AND_WHITE_DAUB_CROSS, BlockRegistryME.WATTLE_AND_WHITE_DAUB_RIGHT,
                        BlockRegistryME.WATTLE_AND_WHITE_DAUB_LEFT, BlockRegistryME.WATTLE_AND_WHITE_DAUB_PILLAR, BlockRegistryME.WATTLE_AND_WHITE_DAUB_DIAMOND);

                createWattleRecipes(exporter, GenericBlockSetRegistryME.DARK_DAUB.blockSet.base().asItem(),
                        BlockRegistryME.DARK_WATTLE_AND_DARK_DAUB, BlockRegistryME.DARK_WATTLE_AND_DARK_DAUB_CROSS, BlockRegistryME.DARK_WATTLE_AND_DARK_DAUB_RIGHT,
                        BlockRegistryME.DARK_WATTLE_AND_DARK_DAUB_LEFT, BlockRegistryME.DARK_WATTLE_AND_DARK_DAUB_PILLAR, BlockRegistryME.DARK_WATTLE_AND_DARK_DAUB_DIAMOND);

                createWattleRecipes(exporter, GenericBlockSetRegistryME.YELLOW_DAUB.blockSet.base().asItem(),
                        BlockRegistryME.WATTLE_AND_YELLOW_DAUB, BlockRegistryME.WATTLE_AND_YELLOW_DAUB_CROSS, BlockRegistryME.WATTLE_AND_YELLOW_DAUB_RIGHT,
                        BlockRegistryME.WATTLE_AND_YELLOW_DAUB_LEFT, BlockRegistryME.WATTLE_AND_YELLOW_DAUB_PILLAR, BlockRegistryME.WATTLE_AND_YELLOW_DAUB_DIAMOND);

                createCenterSurroundRecipe(exporter, BlockRegistryME.WATTLE_AND_WHITE_DAUB.asItem(), Items.BLACK_DYE, BlockRegistryME.BLACK_WATTLE_AND_WHITE_DAUB.asItem(), 8);
                createCenterSurroundRecipe(exporter, BlockRegistryME.WATTLE_AND_WHITE_DAUB_CROSS.asItem(), Items.BLACK_DYE, BlockRegistryME.BLACK_WATTLE_AND_WHITE_DAUB_CROSS.asItem(), 8);
                createCenterSurroundRecipe(exporter, BlockRegistryME.WATTLE_AND_WHITE_DAUB_RIGHT.asItem(), Items.BLACK_DYE, BlockRegistryME.BLACK_WATTLE_AND_WHITE_DAUB_RIGHT.asItem(), 8);
                createCenterSurroundRecipe(exporter, BlockRegistryME.WATTLE_AND_WHITE_DAUB_LEFT.asItem(), Items.BLACK_DYE, BlockRegistryME.BLACK_WATTLE_AND_WHITE_DAUB_LEFT.asItem(), 8);
                createCenterSurroundRecipe(exporter, BlockRegistryME.WATTLE_AND_WHITE_DAUB_PILLAR.asItem(), Items.BLACK_DYE, BlockRegistryME.BLACK_WATTLE_AND_WHITE_DAUB_PILLAR.asItem(), 8);
                createCenterSurroundRecipe(exporter, BlockRegistryME.WATTLE_AND_WHITE_DAUB_DIAMOND.asItem(), Items.BLACK_DYE, BlockRegistryME.BLACK_WATTLE_AND_WHITE_DAUB_DIAMOND.asItem(), 8);

                createCenterSurroundRecipe(exporter, BlockRegistryME.WATTLE_AND_WHITE_DAUB.asItem(), Items.GREEN_DYE, BlockRegistryME.GREEN_WATTLE_AND_WHITE_DAUB.asItem(), 8);
                createCenterSurroundRecipe(exporter, BlockRegistryME.WATTLE_AND_WHITE_DAUB_CROSS.asItem(), Items.GREEN_DYE, BlockRegistryME.GREEN_WATTLE_AND_WHITE_DAUB_CROSS.asItem(), 8);
                createCenterSurroundRecipe(exporter, BlockRegistryME.WATTLE_AND_WHITE_DAUB_RIGHT.asItem(), Items.GREEN_DYE, BlockRegistryME.GREEN_WATTLE_AND_WHITE_DAUB_RIGHT.asItem(), 8);
                createCenterSurroundRecipe(exporter, BlockRegistryME.WATTLE_AND_WHITE_DAUB_LEFT.asItem(), Items.GREEN_DYE, BlockRegistryME.GREEN_WATTLE_AND_WHITE_DAUB_LEFT.asItem(), 8);
                createCenterSurroundRecipe(exporter, BlockRegistryME.WATTLE_AND_WHITE_DAUB_PILLAR.asItem(), Items.GREEN_DYE, BlockRegistryME.GREEN_WATTLE_AND_WHITE_DAUB_PILLAR.asItem(), 8);
                createCenterSurroundRecipe(exporter, BlockRegistryME.WATTLE_AND_WHITE_DAUB_DIAMOND.asItem(), Items.GREEN_DYE, BlockRegistryME.GREEN_WATTLE_AND_WHITE_DAUB_DIAMOND.asItem(), 8);

                createCenterSurroundRecipe(exporter, BlockRegistryME.WATTLE_AND_WHITE_DAUB.asItem(), Items.RED_DYE, BlockRegistryME.RED_WATTLE_AND_WHITE_DAUB.asItem(), 8);
                createCenterSurroundRecipe(exporter, BlockRegistryME.WATTLE_AND_WHITE_DAUB_CROSS.asItem(), Items.RED_DYE, BlockRegistryME.RED_WATTLE_AND_WHITE_DAUB_CROSS.asItem(), 8);
                createCenterSurroundRecipe(exporter, BlockRegistryME.WATTLE_AND_WHITE_DAUB_RIGHT.asItem(), Items.RED_DYE, BlockRegistryME.RED_WATTLE_AND_WHITE_DAUB_RIGHT.asItem(), 8);
                createCenterSurroundRecipe(exporter, BlockRegistryME.WATTLE_AND_WHITE_DAUB_LEFT.asItem(), Items.RED_DYE, BlockRegistryME.RED_WATTLE_AND_WHITE_DAUB_LEFT.asItem(), 8);
                createCenterSurroundRecipe(exporter, BlockRegistryME.WATTLE_AND_WHITE_DAUB_PILLAR.asItem(), Items.RED_DYE, BlockRegistryME.RED_WATTLE_AND_WHITE_DAUB_PILLAR.asItem(), 8);
                createCenterSurroundRecipe(exporter, BlockRegistryME.WATTLE_AND_WHITE_DAUB_DIAMOND.asItem(), Items.RED_DYE, BlockRegistryME.RED_WATTLE_AND_WHITE_DAUB_DIAMOND.asItem(), 8);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, BlockRegistryME.BRONZE_BARS, 16)
                        .pattern("SSS")
                        .pattern("SSS")
                        .input('S', ResourceItemsME.BRONZE_INGOT)
                        .criterion(hasItem(ResourceItemsME.BRONZE_INGOT),
                                conditionsFromItem(ResourceItemsME.BRONZE_INGOT))
                        .offerTo(exporter);
                
                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, BlockRegistryME.CRUDE_BARS, 16)
                        .pattern("SSS")
                        .pattern("SSS")
                        .input('S', ResourceItemsME.CRUDE_INGOT)
                        .criterion(hasItem(ResourceItemsME.CRUDE_INGOT),
                                conditionsFromItem(ResourceItemsME.CRUDE_INGOT))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, BlockRegistryME.TREATED_STEEL_BARS, 16)
                        .pattern("SSS")
                        .pattern("SSS")
                        .input('S', TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "steel_ingots")))
                        .criterion(hasItem(ResourceItemsME.STEEL_INGOT),
                                conditionsFromItem(ResourceItemsME.STEEL_INGOT))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, BlockRegistryME.BURZUM_BARS, 16)
                        .pattern("SSS")
                        .pattern("SSS")
                        .input('S', ResourceItemsME.BURZUM_STEEL_INGOT)
                        .criterion(hasItem(ResourceItemsME.BURZUM_STEEL_INGOT),
                                conditionsFromItem(ResourceItemsME.BURZUM_STEEL_INGOT))
                        .offerTo(exporter);


                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, DecorativeBlockRegistryME.AGED_WOOD_WINDOW, 4)
                        .pattern("AAA")
                        .pattern("AGA")
                        .pattern("AAA")
                        .input('A', GenericBlockSetRegistryME.AGED_WOOD_PLANKS.blockSet.base())
                        .input('G', Items.GLASS)
                        .criterion(hasItem(GenericBlockSetRegistryME.AGED_WOOD_PLANKS.blockSet.base()),
                                conditionsFromItem(GenericBlockSetRegistryME.AGED_WOOD_PLANKS.blockSet.base()))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, BlockRegistryME.AGED_WOOD_TRAPDOOR, 2)
                        .pattern("WWW")
                        .pattern("WWW")
                        .input('W', GenericBlockSetRegistryME.AGED_WOOD_PLANKS.blockSet.base())
                        .criterion(hasItem(GenericBlockSetRegistryME.AGED_WOOD_PLANKS.blockSet.base()),
                                conditionsFromItem(GenericBlockSetRegistryME.AGED_WOOD_PLANKS.blockSet.base()))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, BlockRegistryME.BRONZE_TRAPDOOR, 2)
                        .pattern("NSN")
                        .pattern("NSN")
                        .input('S', ResourceItemsME.BRONZE_INGOT)
                        .input('N', ResourceItemsME.BRONZE_NUGGET)
                        .criterion(hasItem(ResourceItemsME.BRONZE_INGOT),
                                conditionsFromItem(ResourceItemsME.BRONZE_INGOT))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, BlockRegistryME.CRUDE_TRAPDOOR, 2)
                        .pattern("NSN")
                        .pattern("NSN")
                        .input('S', ResourceItemsME.CRUDE_INGOT)
                        .input('N', ResourceItemsME.CRUDE_NUGGET)
                        .criterion(hasItem(ResourceItemsME.CRUDE_INGOT),
                                conditionsFromItem(ResourceItemsME.CRUDE_INGOT))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, BlockRegistryME.TREATED_STEEL_TRAPDOOR, 2)
                        .pattern("NSN")
                        .pattern("NSN")
                        .input('S', TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "steel_ingots")))
                        .input('N', TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "steel_nuggets")))
                        .criterion(hasItem(ResourceItemsME.STEEL_INGOT),
                                conditionsFromItem(ResourceItemsME.STEEL_INGOT))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, BlockRegistryME.AGED_WOOD_DOOR, 3)
                        .pattern("SS")
                        .pattern("SS")
                        .pattern("SS")
                        .input('S', GenericBlockSetRegistryME.AGED_WOOD_PLANKS.blockSet.base())
                        .criterion(hasItem(GenericBlockSetRegistryME.AGED_WOOD_PLANKS.blockSet.base()),
                                conditionsFromItem(GenericBlockSetRegistryME.AGED_WOOD_PLANKS.blockSet.base()))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, BlockRegistryME.BRONZE_DOOR, 3)
                        .pattern("SS")
                        .pattern("SS")
                        .pattern("SS")
                        .input('S', ResourceItemsME.BRONZE_INGOT)
                        .criterion(hasItem(ResourceItemsME.BRONZE_INGOT),
                                conditionsFromItem(ResourceItemsME.BRONZE_INGOT))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, BlockRegistryME.CRUDE_DOOR, 3)
                        .pattern("SS")
                        .pattern("SS")
                        .pattern("SS")
                        .input('S', ResourceItemsME.CRUDE_INGOT)
                        .criterion(hasItem(ResourceItemsME.CRUDE_INGOT),
                                conditionsFromItem(ResourceItemsME.CRUDE_INGOT))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, BlockRegistryME.TREATED_STEEL_DOOR, 3)
                        .pattern("SS")
                        .pattern("SS")
                        .pattern("SS")
                        .input('S', TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "steel_ingots")))
                        .criterion(hasItem(ResourceItemsME.STEEL_INGOT),
                                conditionsFromItem(ResourceItemsME.STEEL_INGOT))
                        .offerTo(exporter);

                createPaneRecipe(exporter, ResourceItemsME.SILVER_INGOT, BlockRegistryME.SILVER_BARS, 16);
                createPaneRecipe(exporter, Items.GOLD_INGOT, BlockRegistryME.GILDED_BARS, 16);

                createCenterSurroundRecipe(exporter, GenericBlockSetRegistryME.WHITE_DAUB.blockSet.base().asItem(), Items.BLACK_DYE, GenericBlockSetRegistryME.DARK_DAUB.blockSet.base().asItem(), 8);
                createCenterSurroundRecipe(exporter, GenericBlockSetRegistryME.WHITE_DAUB.blockSet.base().asItem(), Items.YELLOW_DYE, GenericBlockSetRegistryME.YELLOW_DAUB.blockSet.base().asItem(), 8);

                //region TREATED_WOOD
                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, GenericBlockSetRegistryME.TREATED_WOOD.blockSet.base(), 6)
                        .pattern("PPP")
                        .pattern("PHP")
                        .pattern("PPP")
                        .input('P', TagKey.of(RegistryKeys.ITEM, Identifier.of("logs")))
                        .input('H', Items.HONEYCOMB)
                        .criterion(hasItem(Items.HONEYCOMB),
                                conditionsFromItem(Items.HONEYCOMB))
                        .offerTo(exporter);

                createBrickRecipe(exporter, GenericBlockSetRegistryME.TREATED_WOOD.blockSet.base().asItem(), GenericBlockSetRegistryME.TREATED_WOOD_BEAM.blockSet.base(), 3);
                createBrickRecipe(exporter, GenericBlockSetRegistryME.TREATED_WOOD_BEAM.blockSet.base().asItem(), GenericBlockSetRegistryME.TREATED_WOOD_TILING.blockSet.base(), 4);

                ShapelessRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, GenericBlockSetRegistryME.TREATED_WOOD_PLANKS.blockSet.base(), 4)
                        .input(GenericBlockSetRegistryME.TREATED_WOOD.blockSet.base())
                        .criterion(hasItem(GenericBlockSetRegistryME.TREATED_WOOD.blockSet.base()),
                                conditionsFromItem(GenericBlockSetRegistryME.TREATED_WOOD.blockSet.base()))
                        .offerTo(exporter);

                createBrickRecipe(exporter, GenericBlockSetRegistryME.TREATED_WOOD_PLANKS.blockSet.base().asItem(), GenericBlockSetRegistryME.TREATED_WOOD_PANELS.blockSet.base(), 4);
                createGenericRecipes(GenericBlockSetRegistryME.TREATED_WOOD);
                createGenericRecipes(GenericBlockSetRegistryME.TREATED_WOOD_PLANKS);
                createGenericRecipes(GenericBlockSetRegistryME.TREATED_WOOD_BEAM);
                createGenericRecipes(GenericBlockSetRegistryME.TREATED_WOOD_PANELS);
                createGenericRecipes(GenericBlockSetRegistryME.TREATED_WOOD_TILING);
                createGenericRecipes(GenericBlockSetRegistryME.TREATED_WOOD_CARVED_BEAM);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, GenericBlockSetRegistryME.TREATED_WOOD_CARVED_BEAM.blockSet.base(), 1)
                        .pattern("S")
                        .pattern("S")
                        .input('S', GenericBlockSetRegistryME.TREATED_WOOD_BEAM.blockSet.slab())
                        .criterion(hasItem(GenericBlockSetRegistryME.TREATED_WOOD_BEAM.blockSet.slab()),
                                conditionsFromItem(GenericBlockSetRegistryME.TREATED_WOOD_BEAM.blockSet.slab()))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, BlockRegistryME.TREATED_WOOD_ROPE_FENCE, 3)
                        .pattern("WRW")
                        .pattern("WRW")
                        .input('W', GenericBlockSetRegistryME.TREATED_WOOD.blockSet.base())
                        .input('R', DecorativeBlockRegistryME.ROPE)
                        .criterion(hasItem(GenericBlockSetRegistryME.TREATED_WOOD.blockSet.base()),
                                conditionsFromItem(GenericBlockSetRegistryME.TREATED_WOOD.blockSet.base()))
                        .offerTo(exporter);
                //endregion

                //region AGED_WOOD
                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, GenericBlockSetRegistryME.AGED_WOOD.blockSet.base(), 6)
                        .pattern("PPP")
                        .pattern("PAP")
                        .pattern("PPP")
                        .input('P', TagKey.of(RegistryKeys.ITEM, Identifier.of("logs")))
                        .input('A', ResourceItemsME.ASH)
                        .criterion(hasItem(ResourceItemsME.ASH),
                                conditionsFromItem(ResourceItemsME.ASH))
                        .offerTo(exporter);

                createBrickRecipe(exporter, GenericBlockSetRegistryME.AGED_WOOD.blockSet.base().asItem(), GenericBlockSetRegistryME.AGED_WOOD_BEAM.blockSet.base(), 3);
                createGenericRecipes(GenericBlockSetRegistryME.AGED_WOOD);
                createGenericRecipes(GenericBlockSetRegistryME.AGED_WOOD_PLANKS);
                createGenericRecipes(GenericBlockSetRegistryME.AGED_WOOD_BEAM);
                createGenericRecipes(GenericBlockSetRegistryME.AGED_WOOD_PANELS);
                createGenericRecipes(GenericBlockSetRegistryME.AGED_WOOD_BOARDS);
                createGenericRecipes(GenericBlockSetRegistryME.AGED_WOOD_FISH_CARVING);
                createGenericRecipes(GenericBlockSetRegistryME.AGED_WOOD_CARVED_BEAM);
                createGenericRecipes(GenericBlockSetRegistryME.AGED_WOOD_KNOTTED_BEAM);
                createShinglesRecipe(exporter, GenericBlockSetRegistryME.AGED_WOOD_PLANKS.blockSet.base(), GenericBlockSetRegistryME.AGED_WOOD_SHINGLES.blockSet.base());
                createGenericRecipes(GenericBlockSetRegistryME.AGED_WOOD_SHINGLES);

                ShapelessRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, GenericBlockSetRegistryME.AGED_WOOD_PLANKS.blockSet.base(), 4)
                        .input(GenericBlockSetRegistryME.AGED_WOOD.blockSet.base())
                        .criterion(hasItem(GenericBlockSetRegistryME.AGED_WOOD.blockSet.base()),
                                conditionsFromItem(GenericBlockSetRegistryME.AGED_WOOD.blockSet.base()))
                        .offerTo(exporter, String.valueOf(Identifier.of(MiddleEarth.MOD_ID, Registries.BLOCK.getId(GenericBlockSetRegistryME.AGED_WOOD_PLANKS.blockSet.base()).getPath() + "_from_wood")));

                createBrickRecipe(exporter, GenericBlockSetRegistryME.AGED_WOOD_PLANKS.blockSet.base().asItem(), GenericBlockSetRegistryME.AGED_WOOD_PANELS.blockSet.base(), 4);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, GenericBlockSetRegistryME.AGED_WOOD_CARVING.blockSet.base(), 1)
                        .pattern("S")
                        .pattern("S")
                        .input('S', GenericBlockSetRegistryME.AGED_WOOD_BEAM.blockSet.slab())
                        .criterion(hasItem(GenericBlockSetRegistryME.AGED_WOOD_BEAM.blockSet.slab()),
                                conditionsFromItem(GenericBlockSetRegistryME.AGED_WOOD_BEAM.blockSet.slab()))
                        .offerTo(exporter);
                createGenericRecipes(GenericBlockSetRegistryME.AGED_WOOD_CARVING);

                createBrickRecipe(exporter, GenericBlockSetRegistryME.AGED_WOOD_PANELS.blockSet.base().asItem(), GenericBlockSetRegistryME.AGED_WOOD_BOARDS.blockSet.base(), 4);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, GenericBlockSetRegistryME.AGED_WOOD_FISH_CARVING.blockSet.base(), 1)
                        .pattern("S")
                        .pattern("S")
                        .input('S', GenericBlockSetRegistryME.AGED_WOOD_CARVING.blockSet.slab())
                        .criterion(hasItem(GenericBlockSetRegistryME.AGED_WOOD_CARVING.blockSet.slab()),
                                conditionsFromItem(GenericBlockSetRegistryME.AGED_WOOD_CARVING.blockSet.slab()))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, GenericBlockSetRegistryME.AGED_WOOD_CARVED_BEAM.blockSet.base(), 3)
                        .pattern("P")
                        .pattern("P")
                        .pattern("P")
                        .input('P', GenericBlockSetRegistryME.AGED_WOOD_BEAM.blockSet.base())
                        .criterion(hasItem(GenericBlockSetRegistryME.AGED_WOOD_BEAM.blockSet.base()),
                                conditionsFromItem(GenericBlockSetRegistryME.AGED_WOOD_BEAM.blockSet.base()))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, GenericBlockSetRegistryME.AGED_WOOD_KNOTTED_BEAM.blockSet.base(), 6)
                        .pattern("PW")
                        .pattern("WP")
                        .pattern("PW")
                        .input('W', GenericBlockSetRegistryME.AGED_WOOD.blockSet.base())
                        .input('P', GenericBlockSetRegistryME.AGED_WOOD_BEAM.blockSet.base())
                        .criterion(hasItem(GenericBlockSetRegistryME.AGED_WOOD_BEAM.blockSet.base()),
                                conditionsFromItem(GenericBlockSetRegistryME.AGED_WOOD_BEAM.blockSet.base()))
                        .offerTo(exporter);
                //endregion

                //region AGED_WOOD_REDDISH
                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, GenericBlockSetRegistryME.AGED_WOOD_REDDISH_BEAM.blockSet.base(), 8)
                        .pattern("PPP")
                        .pattern("PRP")
                        .pattern("PPP")
                        .input('P', GenericBlockSetRegistryME.AGED_WOOD_BEAM.blockSet.base())
                        .input('R', Items.RED_DYE)
                        .criterion(hasItem(GenericBlockSetRegistryME.AGED_WOOD_BEAM.blockSet.base()),
                                conditionsFromItem(GenericBlockSetRegistryME.AGED_WOOD_BEAM.blockSet.base()))
                        .offerTo(exporter);

                createGenericRecipes(GenericBlockSetRegistryME.AGED_WOOD_REDDISH_BEAM);
                 //endregion

                //region AGED_WOOD_GILDED_CARVED_PILLAR
                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, GenericBlockSetRegistryME.AGED_WOOD_GILDED_CARVED_PILLAR.blockSet.base(), 8)
                        .pattern("PPP")
                        .pattern("PGP")
                        .pattern("PPP")
                        .input('P', GenericBlockSetRegistryME.AGED_WOOD_BEAM.blockSet.base())
                        .input('G', Items.GOLD_NUGGET)
                        .criterion(hasItem(GenericBlockSetRegistryME.AGED_WOOD_BEAM.blockSet.base()),
                                conditionsFromItem(GenericBlockSetRegistryME.AGED_WOOD_BEAM.blockSet.base()))
                        .offerTo(exporter);

                createGenericRecipes(GenericBlockSetRegistryME.AGED_WOOD_GILDED_CARVED_PILLAR);
                 //endregion

                //region AGED_WOOD_GILDED_CARVED_PILLAR
                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, GenericBlockSetRegistryME.AGED_WOOD_GILDED_CARVING.blockSet.base(), 8)
                        .pattern("PPP")
                        .pattern("PGP")
                        .pattern("PPP")
                        .input('P', GenericBlockSetRegistryME.AGED_WOOD_CARVING.blockSet.base())
                        .input('G', Items.GOLD_NUGGET)
                        .criterion(hasItem(GenericBlockSetRegistryME.AGED_WOOD_CARVING.blockSet.base()),
                                conditionsFromItem(GenericBlockSetRegistryME.AGED_WOOD_CARVING.blockSet.base()))
                        .offerTo(exporter);

                createGenericRecipes(GenericBlockSetRegistryME.AGED_WOOD_GILDED_CARVING);
                 //endregion

                //region AGED_WOOD_GILDED_CARVED_PILLAR
                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, GenericBlockSetRegistryME.AGED_WOOD_GILDED_HORSES.blockSet.base(), 8)
                        .pattern("PPP")
                        .pattern("PGP")
                        .pattern("PPP")
                        .input('P', GenericBlockSetRegistryME.AGED_WOOD_FISH_CARVING.blockSet.base())
                        .input('G', Items.GOLD_NUGGET)
                        .criterion(hasItem(GenericBlockSetRegistryME.AGED_WOOD_FISH_CARVING.blockSet.base()),
                                conditionsFromItem(GenericBlockSetRegistryME.AGED_WOOD_FISH_CARVING.blockSet.base()))
                        .offerTo(exporter);

                createGenericRecipes(GenericBlockSetRegistryME.AGED_WOOD_GILDED_HORSES);
                 //endregion

                //region AGED_WOOD_GILDED_CARVED_PILLAR
                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, GenericBlockSetRegistryME.AGED_WOOD_GILDED_TRIM.blockSet.base(), 8)
                        .pattern("PPP")
                        .pattern("PGP")
                        .pattern("PPP")
                        .input('P', GenericBlockSetRegistryME.AGED_WOOD_PANELS.blockSet.base())
                        .input('G', Items.GOLD_NUGGET)
                        .criterion(hasItem(GenericBlockSetRegistryME.AGED_WOOD_PANELS.blockSet.base()),
                                conditionsFromItem(GenericBlockSetRegistryME.AGED_WOOD_PANELS.blockSet.base()))
                        .offerTo(exporter);

                createGenericRecipes(GenericBlockSetRegistryME.AGED_WOOD_GILDED_TRIM);
                 //endregion

                ShapelessRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, DecorativeBlockRegistryME.OLD_SKULL, 1)
                        .input(Items.SKELETON_SKULL)
                        .input(ResourceItemsME.ASH)
                        .criterion(hasItem(Items.SKELETON_SKULL),
                                conditionsFromItem(Items.SKELETON_SKULL))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, DecorativeBlockRegistryME.SKELETON, 1)
                        .pattern("BSB")
                        .pattern(" B ")
                        .pattern("B B")
                        .input('B', TagKey.of(RegistryKeys.ITEM, MiddleEarth.of("bones")))
                        .input('S', DecorativeBlockRegistryME.OLD_SKULL.asItem())
                        .criterion(hasItem(DecorativeBlockRegistryME.OLD_SKULL.asItem()),
                                conditionsFromItem(DecorativeBlockRegistryME.OLD_SKULL.asItem()))
                        .offerTo(exporter);

                createCombinedItemRecipe(exporter, Blocks.SKELETON_SKULL, ItemTags.CANDLES, DecorativeBlockRegistryME.SKULL_CANDLE);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, DecorativeBlockRegistryME.CANDLESTICK, 1)
                        .pattern("C")
                        .pattern("S")
                        .pattern("S")
                        .input('C', TagKey.of(RegistryKeys.ITEM, Identifier.of("candles")))
                        .input('S', ResourceItemsME.STEEL_NUGGET)
                        .criterion(hasItem(Items.CANDLE),
                                conditionsFromItem(Items.CANDLE))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, DecorativeBlockRegistryME.CANDLE_HOLDER, 1)
                        .pattern("C ")
                        .pattern("SS")
                        .input('C', TagKey.of(RegistryKeys.ITEM, Identifier.of("candles")))
                        .input('S', ResourceItemsME.STEEL_NUGGET)
                        .criterion(hasItem(Items.CANDLE),
                                conditionsFromItem(Items.CANDLE))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, DecorativeBlockRegistryME.CERAMIC_LAMP, 1)
                        .pattern("T ")
                        .pattern("BB")
                        .input('T', Items.TORCH)
                        .input('B', Items.BRICK)
                        .criterion(hasItem(Items.BRICK),
                                conditionsFromItem(Items.BRICK))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, DecorativeBlockRegistryME.CANDLE_HEAP, 1)
                        .pattern("CCC")
                        .pattern("CCC")
                        .input('C', TagKey.of(RegistryKeys.ITEM, Identifier.of("candles")))
                        .criterion(hasItem(Items.CANDLE),
                                conditionsFromItem(Items.CANDLE))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, DecorativeBlockRegistryME.SMALL_CHANDELIER, 1)
                        .pattern(" N ")
                        .pattern("CNC")
                        .pattern("N N")
                        .input('C', TagKey.of(RegistryKeys.ITEM, Identifier.of("candles")))
                        .input('N', TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "steel_nuggets")))
                        .criterion(hasItem(Items.CANDLE),
                                conditionsFromItem(Items.CANDLE))
                        .offerTo(exporter);
                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, DecorativeBlockRegistryME.CHANDELIER, 1)
                        .pattern(" N ")
                        .pattern("CHC")
                        .pattern("N N")
                        .input('C', TagKey.of(RegistryKeys.ITEM, Identifier.of("candles")))
                        .input('H', DecorativeBlockRegistryME.SMALL_CHANDELIER)
                        .input('N', TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "steel_nuggets")))
                        .criterion(hasItem(Items.CANDLE),
                                conditionsFromItem(Items.CANDLE))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, DecorativeBlockRegistryME.SMALL_BRONZE_CHANDELIER, 1)
                        .pattern(" N ")
                        .pattern("CNC")
                        .pattern("N N")
                        .input('C', TagKey.of(RegistryKeys.ITEM, Identifier.of("candles")))
                        .input('N', ResourceItemsME.BRONZE_NUGGET)
                        .criterion(hasItem(Items.CANDLE),
                                conditionsFromItem(Items.CANDLE))
                        .offerTo(exporter);
                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, DecorativeBlockRegistryME.BRONZE_CHANDELIER, 1)
                        .pattern(" N ")
                        .pattern("CHC")
                        .pattern("N N")
                        .input('C', TagKey.of(RegistryKeys.ITEM, Identifier.of("candles")))
                        .input('H', DecorativeBlockRegistryME.SMALL_BRONZE_CHANDELIER)
                        .input('N', ResourceItemsME.BRONZE_NUGGET)
                        .criterion(hasItem(Items.CANDLE),
                                conditionsFromItem(Items.CANDLE))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, DecorativeBlockRegistryME.STONE_LECTERN.asItem(), 1)
                        .pattern("SSS")
                        .pattern(" B ")
                        .pattern(" S ")
                        .input('S', Items.STONE)
                        .input('B', Items.BOOKSHELF)
                        .criterion(hasItem(Items.BOOKSHELF),
                                conditionsFromItem(Items.BOOKSHELF))
                        .offerTo(exporter);
                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, DecorativeBlockRegistryME.CHISELED_DOLOMITE_BOOKSHELF, 1)
                        .pattern("DDD")
                        .pattern("SSS")
                        .pattern("DDD")
                        .input('D', StoneBlockSetRegistryME.DOLOMITE_SET.baseBlocks.base())
                        .input('S', StoneBlockSetRegistryME.DOLOMITE_SET.baseBlocks.slab())
                        .criterion(hasItem(Items.BOOKSHELF),
                                conditionsFromItem(Items.BOOKSHELF))
                        .offerTo(exporter);

                createStatueRecipe(exporter, Blocks.POLISHED_BASALT, Blocks.BASALT, StoneBlockSetRegistryME.BASALT_SET.baseBlocks.wall(), DecorativeBlockRegistryME.BASALT_STATUE);
                createStatueRecipe(exporter, StoneBlockSetRegistryME.CALCITE_SET.polishedBlocks.base(), Blocks.CALCITE, StoneBlockSetRegistryME.CALCITE_SET.baseBlocks.wall(), DecorativeBlockRegistryME.CALCITE_STATUE);
                createStatueRecipe(exporter, StoneBlockSetRegistryME.DEEPSLATE_SET.polishedBlocks.base(), Blocks.DEEPSLATE, StoneBlockSetRegistryME.DEEPSLATE_SET.baseBlocks.wall(), DecorativeBlockRegistryME.DEEPSLATE_STATUE);
                createStatueRecipe(exporter, StoneBlockSetRegistryME.DIORITE_SET.polishedBlocks.base(), Blocks.DIORITE, Blocks.DIORITE_WALL, DecorativeBlockRegistryME.DIORITE_STATUE);
                createStatueRecipe(exporter, StoneBlockSetRegistryME.GABBRO_SET.polishedBlocks.base(), StoneBlockSetRegistryME.GABBRO_SET.baseBlocks.base(), StoneBlockSetRegistryME.GABBRO_SET.baseBlocks.wall(), DecorativeBlockRegistryME.GABBRO_STATUE);
                createStatueRecipe(exporter, StoneBlockSetRegistryME.GALONN_SET.polishedBlocks.base(), StoneBlockSetRegistryME.GALONN_SET.baseBlocks.base(), StoneBlockSetRegistryME.GALONN_SET.baseBlocks.wall(), DecorativeBlockRegistryME.GALONN_STATUE);
                createStatueRecipe(exporter, StoneBlockSetRegistryME.KHAGALABAN_SET.polishedBlocks.base(), StoneBlockSetRegistryME.KHAGALABAN_SET.baseBlocks.base(), StoneBlockSetRegistryME.KHAGALABAN_SET.baseBlocks.wall(), DecorativeBlockRegistryME.KHAGALABAN_STATUE);
                createStatueRecipe(exporter, StoneBlockSetRegistryME.PUMICE_SET.baseBlocks.base(), StoneBlockSetRegistryME.PUMICE_SET.baseBlocks.base(), StoneBlockSetRegistryME.PUMICE_SET.baseBlocks.wall(), DecorativeBlockRegistryME.PUMICE_STATUE);
                createStatueRecipe(exporter, Blocks.POLISHED_TUFF, Blocks.TUFF, Blocks.TUFF_WALL, DecorativeBlockRegistryME.TUFF_STATUE);
                createStatueRecipe(exporter, StoneBlockSetRegistryME.ZIGILABAN_SET.baseBlocks.base(), StoneBlockSetRegistryME.ZIGILABAN_SET.baseBlocks.base(), StoneBlockSetRegistryME.ZIGILABAN_SET.baseBlocks.wall(), DecorativeBlockRegistryME.ZIGILABAN_STATUE);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, DecorativeBlockRegistryME.CERAMIC_PLATE, 1)
                        .pattern("BB")
                        .input('B', Items.BRICK)
                        .criterion(hasItem(Items.BRICK),
                                conditionsFromItem(Items.BRICK))
                        .offerTo(exporter);
                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, DecorativeBlockRegistryME.ROTTEN_PLATE, 4)
                        .pattern("RR")
                        .input('R', WoodBlockSetRegistryME.ROTTEN_SET.logBlocks.log())
                        .criterion(hasItem(WoodBlockSetRegistryME.ROTTEN_SET.logBlocks.log()),
                                conditionsFromItem(WoodBlockSetRegistryME.ROTTEN_SET.logBlocks.log()))
                        .offerTo(exporter);
                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, DecorativeBlockRegistryME.SILVER_PLATE, 1)
                        .pattern("SS")
                        .input('S', ResourceItemsME.SILVER_INGOT)
                        .criterion(hasItem(ResourceItemsME.SILVER_INGOT),
                                conditionsFromItem(ResourceItemsME.SILVER_INGOT))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, DecorativeBlockRegistryME.MEDGON_SPIKE, 1)
                        .pattern("M  ")
                        .pattern("MM ")
                        .pattern("PMP")
                        .input('M', StoneBlockSetRegistryME.MEDGON_SET.baseBlocks.base())
                        .input('P', StoneBlockSetRegistryME.MEDGON_SET.polishedBlocks.base())
                        .criterion(hasItem(StoneBlockSetRegistryME.MEDGON_SET.baseBlocks.base()),
                                conditionsFromItem(StoneBlockSetRegistryME.MEDGON_SET.baseBlocks.base()))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, BlockRegistryME.BURZUM_SPIKES, 4)
                        .pattern(" N ")
                        .pattern("NBN")
                        .pattern("BBB")
                        .input('B', ResourceItemsME.BURZUM_STEEL_INGOT)
                        .input('N', ResourceItemsME.BURZUM_STEEL_NUGGET)
                        .criterion(hasItem(ResourceItemsME.BURZUM_STEEL_INGOT),
                                conditionsFromItem(ResourceItemsME.BURZUM_STEEL_INGOT))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, DecorativeBlockRegistryME.TAPPER, 1)
                        .pattern(" S ")
                        .pattern("LBL")
                        .pattern(" L ")
                        .input('S', ResourceItemsME.STEEL_NUGGET)
                        .input('L', ItemTags.LOGS)
                        .input('B', Items.BUCKET)
                        .criterion(hasItem(Items.BUCKET),
                                conditionsFromItem(Items.BUCKET));

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, DecorativeBlockRegistryME.ORCISH_DRUM, 1)
                        .pattern("SLS")
                        .pattern("W W")
                        .pattern(" W ")
                        .input('S', Items.STICK)
                        .input('W', ItemTags.LOGS)
                        .input('L', Items.LEATHER)
                        .criterion(hasItem(Items.LEATHER),
                                conditionsFromItem(Items.LEATHER))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, DecorativeBlockRegistryME.WATTLE_AND_BRICK_WINDOW, 4)
                        .pattern("BSB")
                        .pattern("SGS")
                        .pattern("BSB")
                        .input('B', Items.BRICKS)
                        .input('G', Items.GLASS)
                        .input('S', Items.STICK)
                        .criterion(hasItem(Items.BRICKS),
                                conditionsFromItem(Items.BRICKS))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, DecorativeBlockRegistryME.WATTLE_FRAMED_WINDOW, 2)
                        .pattern("SSS")
                        .pattern("SGS")
                        .pattern("SSS")
                        .input('G', Items.GLASS)
                        .input('S', Items.STICK)
                        .criterion(hasItem(Items.STICK),
                                conditionsFromItem(Items.STICK))
                        .offerTo(exporter);

                createCenterSurroundRecipe(exporter, DecorativeBlockRegistryME.WATTLE_FRAMED_WINDOW.asItem(), Items.GRAY_DYE, DecorativeBlockRegistryME.DARK_WATTLE_FRAMED_WINDOW.asItem(), 8);
                createCenterSurroundRecipe(exporter, DecorativeBlockRegistryME.WATTLE_FRAMED_WINDOW.asItem(), Items.BLACK_DYE, DecorativeBlockRegistryME.BLACK_WATTLE_FRAMED_WINDOW.asItem(), 8);
                createCenterSurroundRecipe(exporter, DecorativeBlockRegistryME.WATTLE_FRAMED_WINDOW.asItem(), Items.GREEN_DYE, DecorativeBlockRegistryME.GREEN_WATTLE_FRAMED_WINDOW.asItem(), 8);
                createCenterSurroundRecipe(exporter, DecorativeBlockRegistryME.WATTLE_FRAMED_WINDOW.asItem(), Items.RED_DYE, DecorativeBlockRegistryME.RED_WATTLE_FRAMED_WINDOW.asItem(), 8);
                createCenterSurroundRecipe(exporter, DecorativeBlockRegistryME.WATTLE_FRAMED_WINDOW.asItem(), Items.WHITE_DYE, DecorativeBlockRegistryME.WHITE_WATTLE_FRAMED_WINDOW.asItem(), 8);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, DecorativeBlockRegistryME.MUD_BRICK_ROUND_WINDOW, 4)
                        .pattern("MBM")
                        .pattern("BGB")
                        .pattern("MBM")
                        .input('M', Items.MUD_BRICKS)
                        .input('G', Items.GLASS)
                        .input('B', Items.BRICK)
                        .criterion(hasItem(Items.BRICKS),
                                conditionsFromItem(Items.BRICKS))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, DecorativeBlockRegistryME.WHITE_DAUB_ROUND_WINDOW, 4)
                        .pattern("WSW")
                        .pattern("SGS")
                        .pattern("WSW")
                        .input('W', GenericBlockSetRegistryME.WHITE_DAUB.blockSet.base())
                        .input('G', Items.GLASS)
                        .input('S', Items.STICK)
                        .criterion(hasItem(GenericBlockSetRegistryME.WHITE_DAUB.blockSet.base()),
                                conditionsFromItem(GenericBlockSetRegistryME.WHITE_DAUB.blockSet.base()))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, DecorativeBlockRegistryME.YELLOW_DAUB_ROUND_WINDOW, 4)
                        .pattern("WSW")
                        .pattern("SGS")
                        .pattern("WSW")
                        .input('W', GenericBlockSetRegistryME.YELLOW_DAUB.blockSet.base())
                        .input('G', Items.GLASS)
                        .input('S', Items.STICK)
                        .criterion(hasItem(GenericBlockSetRegistryME.YELLOW_DAUB.blockSet.base()),
                                conditionsFromItem(GenericBlockSetRegistryME.YELLOW_DAUB.blockSet.base()))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, DecorativeBlockRegistryME.PLASTER_ROUND_WINDOW, 4)
                        .pattern("WSW")
                        .pattern("SGS")
                        .pattern("WSW")
                        .input('W', GenericBlockSetRegistryME.PLASTER.blockSet.base())
                        .input('G', Items.GLASS)
                        .input('S', Items.STICK)
                        .criterion(hasItem(GenericBlockSetRegistryME.PLASTER.blockSet.base()),
                                conditionsFromItem(GenericBlockSetRegistryME.PLASTER.blockSet.base()))
                        .offerTo(exporter);

                offerStonecuttingRecipe(RecipeCategory.BUILDING_BLOCKS, GenericBlockSetRegistryME.CUT_BRONZE.blockSet.base(), BlockRegistryME.BRONZE_BLOCK, 4);
                offerStonecuttingRecipe(RecipeCategory.BUILDING_BLOCKS, GenericBlockSetRegistryME.CUT_CRUDE_PLATES.blockSet.base(), BlockRegistryME.CRUDE_BLOCK, 4);
                offerStonecuttingRecipe(RecipeCategory.BUILDING_BLOCKS, GenericBlockSetRegistryME.CUT_LEAD.blockSet.base(), BlockRegistryME.LEAD_BLOCK, 4);
                offerStonecuttingRecipe(RecipeCategory.BUILDING_BLOCKS, GenericBlockSetRegistryME.CUT_SILVER.blockSet.base(), BlockRegistryME.SILVER_BLOCK, 4);

                createCushionRecipe(exporter, Blocks.BLUE_WOOL, DecorativeBlockRegistryME.BLUE_CUSHION);
                createDyeableItemRecipe(exporter, DecorativeBlockRegistryME.BLUE_CUSHION, Items.GRAY_DYE, DecorativeBlockRegistryME.DARK_BLUE_CUSHION);
                createCushionRecipe(exporter, Blocks.BROWN_WOOL, DecorativeBlockRegistryME.BROWN_CUSHION);
                createDyeableItemRecipe(exporter, DecorativeBlockRegistryME.BROWN_CUSHION, Items.GRAY_DYE, DecorativeBlockRegistryME.DARK_BROWN_CUSHION);
                createCushionRecipe(exporter, Blocks.GREEN_WOOL, DecorativeBlockRegistryME.GREEN_CUSHION);
                createDyeableItemRecipe(exporter, DecorativeBlockRegistryME.GREEN_CUSHION, Items.GRAY_DYE, DecorativeBlockRegistryME.DARK_GREEN_CUSHION);
                createCushionRecipe(exporter, Blocks.RED_WOOL, DecorativeBlockRegistryME.RED_CUSHION);
                createDyeableItemRecipe(exporter, DecorativeBlockRegistryME.RED_CUSHION, Items.GRAY_DYE, DecorativeBlockRegistryME.DARK_RED_CUSHION);

                createSmallCurtainRecipe(exporter, Blocks.BLACK_WOOL, DecorativeBlockRegistryME.SMALL_BLACK_CURTAIN);
                createSmallCurtainRecipe(exporter, Blocks.BLUE_WOOL, DecorativeBlockRegistryME.SMALL_BLUE_CURTAIN);
                createSmallCurtainRecipe(exporter, Blocks.BROWN_WOOL, DecorativeBlockRegistryME.SMALL_BROWN_CURTAIN);
                createDyeableItemRecipe(exporter, DecorativeBlockRegistryME.SMALL_BLUE_CURTAIN, Items.GRAY_DYE, DecorativeBlockRegistryME.SMALL_DARK_BLUE_CURTAIN);
                createDyeableItemRecipe(exporter, DecorativeBlockRegistryME.SMALL_BROWN_CURTAIN, Items.GRAY_DYE, DecorativeBlockRegistryME.SMALL_DARK_BROWN_CURTAIN);
                createDyeableItemRecipe(exporter, DecorativeBlockRegistryME.SMALL_GREEN_CURTAIN, Items.GRAY_DYE, DecorativeBlockRegistryME.SMALL_DARK_GREEN_CURTAIN);
                createDyeableItemRecipe(exporter, DecorativeBlockRegistryME.SMALL_RED_CURTAIN, Items.GRAY_DYE, DecorativeBlockRegistryME.SMALL_DARK_RED_CURTAIN);
                createSmallFancyCurtainRecipe(exporter, Blocks.BLUE_WOOL, DecorativeBlockRegistryME.SMALL_FANCY_BLUE_CURTAIN);
                createSmallFancyCurtainRecipe(exporter, Blocks.GREEN_WOOL, DecorativeBlockRegistryME.SMALL_FANCY_GREEN_CURTAIN);
                createSmallFancyCurtainRecipe(exporter, Blocks.RED_WOOL, DecorativeBlockRegistryME.SMALL_FANCY_RED_CURTAIN);
                createSmallCurtainRecipe(exporter, Blocks.GRAY_WOOL, DecorativeBlockRegistryME.SMALL_GRAY_CURTAIN);
                createSmallCurtainRecipe(exporter, Blocks.GREEN_WOOL, DecorativeBlockRegistryME.SMALL_GREEN_CURTAIN);
                createSmallCurtainRecipe(exporter, Blocks.PURPLE_WOOL, DecorativeBlockRegistryME.SMALL_PURPLE_CURTAIN);
                createSmallCurtainRecipe(exporter, Blocks.RED_WOOL, DecorativeBlockRegistryME.SMALL_RED_CURTAIN);
                createSmallCurtainRecipe(exporter, Blocks.WHITE_WOOL, DecorativeBlockRegistryME.SMALL_WHITE_CURTAIN);
                createSmallCurtainRecipe(exporter, Blocks.YELLOW_WOOL, DecorativeBlockRegistryME.SMALL_YELLOW_CURTAIN);

                createCurtainRecipe(exporter, Blocks.BLACK_WOOL, DecorativeBlockRegistryME.BLACK_CURTAIN);
                createCurtainRecipe(exporter, Blocks.BLUE_WOOL, DecorativeBlockRegistryME.BLUE_CURTAIN);
                createCurtainRecipe(exporter, Blocks.BROWN_WOOL, DecorativeBlockRegistryME.BROWN_CURTAIN);
                createDyeableItemRecipe(exporter, DecorativeBlockRegistryME.BLUE_CURTAIN, Items.GRAY_DYE, DecorativeBlockRegistryME.DARK_BLUE_CURTAIN);
                createDyeableItemRecipe(exporter, DecorativeBlockRegistryME.BROWN_CURTAIN, Items.GRAY_DYE, DecorativeBlockRegistryME.DARK_BROWN_CURTAIN);
                createDyeableItemRecipe(exporter, DecorativeBlockRegistryME.GREEN_CURTAIN, Items.GRAY_DYE, DecorativeBlockRegistryME.DARK_GREEN_CURTAIN);
                createDyeableItemRecipe(exporter, DecorativeBlockRegistryME.RED_CURTAIN, Items.GRAY_DYE, DecorativeBlockRegistryME.DARK_RED_CURTAIN);
                createFancyCurtainRecipe(exporter, Blocks.BLUE_WOOL, DecorativeBlockRegistryME.FANCY_BLUE_CURTAIN);
                createFancyCurtainRecipe(exporter, Blocks.GREEN_WOOL, DecorativeBlockRegistryME.FANCY_GREEN_CURTAIN);
                createFancyCurtainRecipe(exporter, Blocks.RED_WOOL, DecorativeBlockRegistryME.FANCY_RED_CURTAIN);
                createCurtainRecipe(exporter, Blocks.GRAY_WOOL, DecorativeBlockRegistryME.GRAY_CURTAIN);
                createCurtainRecipe(exporter, Blocks.GREEN_WOOL, DecorativeBlockRegistryME.GREEN_CURTAIN);
                createCurtainRecipe(exporter, Blocks.PURPLE_WOOL, DecorativeBlockRegistryME.PURPLE_CURTAIN);
                createCurtainRecipe(exporter, Blocks.RED_WOOL, DecorativeBlockRegistryME.RED_CURTAIN);
                createCurtainRecipe(exporter, Blocks.WHITE_WOOL, DecorativeBlockRegistryME.WHITE_CURTAIN);
                createCurtainRecipe(exporter, Blocks.YELLOW_WOOL, DecorativeBlockRegistryME.YELLOW_CURTAIN);
                
                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, DecorativeBlockRegistryME.ROPE_LADDER, 3)
                        .pattern("R R")
                        .pattern("RSR")
                        .pattern("R R")
                        .input('R', DecorativeBlockRegistryME.ROPE)
                        .input('S', Items.STRING)
                        .criterion(hasItem(DecorativeBlockRegistryME.ROPE),
                                conditionsFromItem(DecorativeBlockRegistryME.ROPE))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, DecorativeBlockRegistryME.FANCY_BED, 1)
                        .pattern("FFW")
                        .pattern("FFW")
                        .pattern("PPP")
                        .input('W', TagKey.of(RegistryKeys.ITEM, Identifier.of("wool")))
                        .input('F', ResourceItemsME.FABRIC)
                        .input('P', TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .criterion(hasItem(ResourceItemsME.FABRIC),
                                conditionsFromItem(ResourceItemsME.FABRIC))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, DecorativeBlockRegistryME.STRAW_BED, 1)
                        .pattern("SSS")
                        .pattern("PPP")
                        .input('S', ResourceItemsME.STRAW)
                        .input('P', TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .criterion(hasItem(ResourceItemsME.STRAW),
                                conditionsFromItem(ResourceItemsME.STRAW))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, DecorativeBlockRegistryME.FUR_BED, 1)
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

                createWoodStoolRecipe(exporter, GenericBlockSetRegistryME.TREATED_WOOD_PLANKS.blockSet.base().asItem(), DecorativeBlockRegistryME.TREATED_WOOD_STOOL);
                createWoodBenchRecipe(exporter, GenericBlockSetRegistryME.TREATED_WOOD_PLANKS.blockSet.base().asItem(), DecorativeBlockRegistryME.TREATED_WOOD_BENCH);
                createWoodTableRecipe(exporter, GenericBlockSetRegistryME.TREATED_WOOD_PLANKS.blockSet.base().asItem(), DecorativeBlockRegistryME.TREATED_WOOD_TABLE);
                createWoodChairRecipe(exporter, GenericBlockSetRegistryME.TREATED_WOOD_PLANKS.blockSet.base().asItem(), DecorativeBlockRegistryME.TREATED_WOOD_CHAIR);
                createWoodLadderRecipe(exporter, GenericBlockSetRegistryME.TREATED_WOOD_PLANKS.blockSet.base().asItem(), DecorativeBlockRegistryME.TREATED_WOOD_LADDER);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, DecorativeBlockRegistryME.LARCH_HOBBIT_DOOR, 1)
                        .pattern("LLL")
                        .pattern("LSL")
                        .pattern("LLL")
                        .input('S', TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "steel_ingots")))
                        .input('L', WoodBlockSetRegistryME.LARCH_SET.planksBlocks.base())
                        .criterion(hasItem(WoodBlockSetRegistryME.LARCH_SET.planksBlocks.base()),
                                conditionsFromItem(WoodBlockSetRegistryME.LARCH_SET.planksBlocks.base()))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, DecorativeBlockRegistryME.SPRUCE_HOBBIT_DOOR, 1)
                        .pattern("LSL")
                        .pattern("SLL")
                        .pattern("LSL")
                        .input('S', TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "steel_ingots")))
                        .input('L', Items.SPRUCE_PLANKS)
                        .criterion(hasItem(Items.SPRUCE_PLANKS),
                                conditionsFromItem(Items.SPRUCE_PLANKS))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, DecorativeBlockRegistryME.BLUE_HOBBIT_DOOR, 1)
                        .pattern(" BG")
                        .pattern("BDG")
                        .pattern(" BG")
                        .input('D', DecorativeBlockRegistryME.LARCH_HOBBIT_DOOR)
                        .input('B', Items.BLUE_DYE)
                        .input('G', Items.GOLD_NUGGET)
                        .criterion(hasItem(DecorativeBlockRegistryME.LARCH_HOBBIT_DOOR),
                                conditionsFromItem(DecorativeBlockRegistryME.LARCH_HOBBIT_DOOR))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, DecorativeBlockRegistryME.GREEN_HOBBIT_DOOR, 1)
                        .pattern(" BG")
                        .pattern("BDG")
                        .pattern(" BG")
                        .input('D', DecorativeBlockRegistryME.LARCH_HOBBIT_DOOR)
                        .input('B', Items.GREEN_DYE)
                        .input('G', Items.GOLD_NUGGET)
                        .criterion(hasItem(DecorativeBlockRegistryME.LARCH_HOBBIT_DOOR),
                                conditionsFromItem(DecorativeBlockRegistryME.LARCH_HOBBIT_DOOR))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, DecorativeBlockRegistryME.LIGHT_BLUE_HOBBIT_DOOR, 1)
                        .pattern(" B ")
                        .pattern("BDB")
                        .pattern(" B ")
                        .input('D', DecorativeBlockRegistryME.LARCH_HOBBIT_DOOR)
                        .input('B', Items.LIGHT_BLUE_DYE)
                        .criterion(hasItem(DecorativeBlockRegistryME.LARCH_HOBBIT_DOOR),
                                conditionsFromItem(DecorativeBlockRegistryME.LARCH_HOBBIT_DOOR))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, DecorativeBlockRegistryME.RED_HOBBIT_DOOR, 1)
                        .pattern(" BG")
                        .pattern("BDG")
                        .pattern(" BG")
                        .input('D', DecorativeBlockRegistryME.LARCH_HOBBIT_DOOR)
                        .input('B', Items.RED_DYE)
                        .input('G', Items.GOLD_NUGGET)
                        .criterion(hasItem(DecorativeBlockRegistryME.LARCH_HOBBIT_DOOR),
                                conditionsFromItem(DecorativeBlockRegistryME.LARCH_HOBBIT_DOOR))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, DecorativeBlockRegistryME.YELLOW_HOBBIT_DOOR, 1)
                        .pattern(" BG")
                        .pattern("BDG")
                        .pattern(" BG")
                        .input('D', DecorativeBlockRegistryME.LARCH_HOBBIT_DOOR)
                        .input('B', Items.YELLOW_DYE)
                        .input('G', Items.GOLD_NUGGET)
                        .criterion(hasItem(DecorativeBlockRegistryME.LARCH_HOBBIT_DOOR),
                                conditionsFromItem(DecorativeBlockRegistryME.LARCH_HOBBIT_DOOR))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, DecorativeBlockRegistryME.TALL_BLACK_PINE_DOOR, 1)
                        .pattern("SP")
                        .pattern("PP")
                        .pattern("SP")
                        .input('S', TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "steel_nuggets")))
                        .input('P', WoodBlockSetRegistryME.BLACK_PINE_SET.planksBlocks.base())
                        .criterion(hasItem(WoodBlockSetRegistryME.BLACK_PINE_SET.planksBlocks.base()),
                                conditionsFromItem(WoodBlockSetRegistryME.BLACK_PINE_SET.planksBlocks.base()))
                        .offerTo(exporter);
                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, DecorativeBlockRegistryME.TALL_FIR_DOOR, 1)
                        .pattern("SP")
                        .pattern("PP")
                        .pattern("SP")
                        .input('S', TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "steel_nuggets")))
                        .input('P', WoodBlockSetRegistryME.FIR_SET.planksBlocks.base())
                        .criterion(hasItem(WoodBlockSetRegistryME.FIR_SET.planksBlocks.base()),
                                conditionsFromItem(WoodBlockSetRegistryME.FIR_SET.planksBlocks.base()))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, DecorativeBlockRegistryME.OAK_STABLE_DOOR, 1)
                        .pattern("SPP")
                        .pattern("PPP")
                        .pattern("SPP")
                        .input('S', TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "steel_nuggets")))
                        .input('P', Items.OAK_PLANKS)
                        .criterion(hasItem(Items.OAK_PLANKS),
                                conditionsFromItem(Items.OAK_PLANKS))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, DecorativeBlockRegistryME.REINFORCED_BLACK_PINE_DOOR, 1)
                        .pattern("SPP")
                        .pattern("SPS")
                        .pattern("SPP")
                        .input('S', TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "steel_ingots")))
                        .input('P', WoodBlockSetRegistryME.BLACK_PINE_SET.planksBlocks.base())
                        .criterion(hasItem(WoodBlockSetRegistryME.BLACK_PINE_SET.planksBlocks.base()),
                                conditionsFromItem(WoodBlockSetRegistryME.BLACK_PINE_SET.planksBlocks.base()))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, DecorativeBlockRegistryME.REINFORCED_SPRUCE_DOOR, 1)
                        .pattern("SPP")
                        .pattern("SPS")
                        .pattern("SPP")
                        .input('S', TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "steel_ingots")))
                        .input('P', Items.SPRUCE_PLANKS)
                        .criterion(hasItem(Items.SPRUCE_PLANKS),
                                conditionsFromItem(Items.SPRUCE_PLANKS))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, DecorativeBlockRegistryME.SIMPLE_LARCH_GATE, 1)
                        .pattern("SPP")
                        .pattern("PPP")
                        .pattern("SPP")
                        .input('S', TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "steel_nuggets")))
                        .input('P', WoodBlockSetRegistryME.LARCH_SET.planksBlocks.base())
                        .criterion(hasItem(WoodBlockSetRegistryME.LARCH_SET.planksBlocks.base()),
                                conditionsFromItem(WoodBlockSetRegistryME.LARCH_SET.planksBlocks.base()))
                        .offerTo(exporter);

                offerStonecuttingRecipe(RecipeCategory.BUILDING_BLOCKS, DecorativeBlockRegistryME.RICKETY_SIMPLE_LARCH_DOOR, DecorativeBlockRegistryME.SIMPLE_LARCH_GATE);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, DecorativeBlockRegistryME.SPRUCE_STABLE_DOOR, 1)
                        .pattern("SPP")
                        .pattern("PPP")
                        .pattern("SPP")
                        .input('S', TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "steel_nuggets")))
                        .input('P', Items.SPRUCE_PLANKS)
                        .criterion(hasItem(Items.SPRUCE_PLANKS),
                                conditionsFromItem(Items.SPRUCE_PLANKS))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, DecorativeBlockRegistryME.LARGE_STURDY_DOOR, 1)
                        .pattern("SPP")
                        .pattern("PPP")
                        .pattern("SPP")
                        .input('S', TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "steel_ingots")))
                        .input('P', TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .criterion(hasItem(Items.OAK_PLANKS),
                                conditionsFromItem(Items.OAK_PLANKS))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, DecorativeBlockRegistryME.LARGE_BEECH_FENCE_GATE, 1)
                        .pattern("FF")
                        .input('F', WoodBlockSetRegistryME.BEECH_SET.planksBlocks.gate())
                        .criterion(hasItem(WoodBlockSetRegistryME.BEECH_SET.planksBlocks.gate()),
                                conditionsFromItem(WoodBlockSetRegistryME.BEECH_SET.planksBlocks.gate()))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, DecorativeBlockRegistryME.GREAT_GONDORIAN_GATE, 1)
                        .pattern("LCL")
                        .pattern("CCS")
                        .pattern("LCL")
                        .input('L', WoodBlockSetRegistryME.BLACK_LEBETHRON_SET.planksBlocks.base())
                        .input('C', Items.OXIDIZED_COPPER)
                        .input('S', ResourceItemsME.STEEL_INGOT)
                        .criterion(hasItem(Items.OXIDIZED_COPPER),
                                conditionsFromItem(Items.OXIDIZED_COPPER))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, DecorativeBlockRegistryME.GREAT_DWARVEN_GATE, 1)
                        .pattern("BTB")
                        .pattern("BTS")
                        .pattern("BTB")
                        .input('B', ResourceItemsME.BRONZE_INGOT)
                        .input('T', GenericBlockSetRegistryME.TREATED_WOOD.blockSet.base())
                        .input('S', ResourceItemsME.KHAZAD_STEEL_INGOT)
                        .criterion(hasItem(ResourceItemsME.BRONZE_INGOT),
                                conditionsFromItem(ResourceItemsME.BRONZE_INGOT))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, DecorativeBlockRegistryME.VARNISHED_DWARVEN_DOOR, 1)
                        .pattern("TNT")
                        .pattern("TTS")
                        .pattern("TNT")
                        .input('N', ResourceItemsME.STEEL_NUGGET)
                        .input('T', GenericBlockSetRegistryME.TREATED_WOOD.blockSet.base())
                        .input('S', ResourceItemsME.KHAZAD_STEEL_INGOT)
                        .criterion(hasItem(ResourceItemsME.BRONZE_INGOT),
                                conditionsFromItem(ResourceItemsME.BRONZE_INGOT))
                        .offerTo(exporter);

                offerStonecuttingRecipe(RecipeCategory.BUILDING_BLOCKS, DecorativeBlockRegistryME.RUINED_DWARVEN_DOOR, DecorativeBlockRegistryME.VARNISHED_DWARVEN_DOOR);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, DecorativeBlockRegistryME.HIDDEN_DWARVEN_DOOR, 1)
                        .pattern("SSG")
                        .pattern("GDL")
                        .pattern("DSS")
                        .input('L', Items.LEVER)
                        .input('G', StoneBlockSetRegistryME.DOLOMITE_SET.smoothBlocks.base())
                        .input('D', StoneBlockSetRegistryME.DOLOMITE_SET.baseBlocks.base())
                        .input('S', Items.STONE)
                        .criterion(hasItem(StoneBlockSetRegistryME.DOLOMITE_SET.smoothBlocks.base()),
                                conditionsFromItem(StoneBlockSetRegistryME.DOLOMITE_SET.smoothBlocks.base()))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, DecorativeBlockRegistryME.GREAT_ELVEN_GATE, 1)
                        .pattern("BTB")
                        .pattern("BTS")
                        .pattern("BTB")
                        .input('B', Items.CYAN_DYE)
                        .input('T', GenericBlockSetRegistryME.TREATED_WOOD.blockSet.base())
                        .input('S', ResourceItemsME.EDHEL_STEEL_INGOT)
                        .criterion(hasItem(GenericBlockSetRegistryME.TREATED_WOOD.blockSet.base()),
                                conditionsFromItem(GenericBlockSetRegistryME.TREATED_WOOD.blockSet.base()))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, DecorativeBlockRegistryME.GREAT_ORCISH_GATE, 1)
                        .pattern("SSS")
                        .pattern("SNS")
                        .pattern("NNN")
                        .input('N', BlockRegistryME.BURZUM_STEEL_BLOCK)
                        .input('S', ResourceItemsME.BURZUM_STEEL_INGOT)
                        .criterion(hasItem(ResourceItemsME.BURZUM_STEEL_INGOT),
                                conditionsFromItem(ResourceItemsME.BURZUM_STEEL_INGOT))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, BlockRegistryME.TURF, 4)
                        .pattern("MM")
                        .pattern("MD")
                        .input('M', Items.MOSS_BLOCK)
                        .input('D', Items.DIRT)
                        .criterion(hasItem(Items.MOSS_BLOCK),
                                conditionsFromItem(Items.MOSS_BLOCK))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, NatureBlockRegistryME.CORRUPTED_MOSS_CARPET, 3)
                        .pattern("MM")
                        .input('M', NatureBlockRegistryME.CORRUPTED_MOSS_BLOCK)
                        .criterion(hasItem(NatureBlockRegistryME.CORRUPTED_MOSS_BLOCK),
                                conditionsFromItem(NatureBlockRegistryME.CORRUPTED_MOSS_BLOCK))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, NatureBlockRegistryME.FOREST_MOSS_CARPET, 3)
                        .pattern("MM")
                        .input('M', NatureBlockRegistryME.FOREST_MOSS_BLOCK)
                        .criterion(hasItem(NatureBlockRegistryME.FOREST_MOSS_BLOCK),
                                conditionsFromItem(NatureBlockRegistryME.FOREST_MOSS_BLOCK))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, BlockRegistryME.GRASSY_DIRT, 4)
                        .pattern("DM")
                        .pattern("MD")
                        .input('M', Items.MOSS_BLOCK)
                        .input('D', Items.DIRT)
                        .criterion(hasItem(Items.MOSS_BLOCK),
                                conditionsFromItem(Items.MOSS_BLOCK))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, BlockRegistryME.GRASSY_CHALKSOIL, 4)
                        .pattern("DM")
                        .pattern("MD")
                        .input('M', Items.MOSS_BLOCK)
                        .input('D', BlockRegistryME.CHALKSOIL)
                        .criterion(hasItem(Items.MOSS_BLOCK),
                                conditionsFromItem(Items.MOSS_BLOCK))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, BlockRegistryME.GRASSY_SILT, 4)
                        .pattern("DM")
                        .pattern("MD")
                        .input('M', Items.MOSS_BLOCK)
                        .input('D', BlockRegistryME.SILT)
                        .criterion(hasItem(Items.MOSS_BLOCK),
                                conditionsFromItem(Items.MOSS_BLOCK))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, BlockRegistryME.GRASSY_LOAM, 4)
                        .pattern("DM")
                        .pattern("MD")
                        .input('M', Items.MOSS_BLOCK)
                        .input('D', BlockRegistryME.LOAM)
                        .criterion(hasItem(Items.MOSS_BLOCK),
                                conditionsFromItem(Items.MOSS_BLOCK))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, BlockRegistryME.GRASSY_PEAT, 4)
                        .pattern("DM")
                        .pattern("MD")
                        .input('M', Items.MOSS_BLOCK)
                        .input('D', BlockRegistryME.LOAM)
                        .criterion(hasItem(Items.MOSS_BLOCK),
                                conditionsFromItem(Items.MOSS_BLOCK))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, BlockRegistryME.PEBBLED_GRASS, 4)
                        .pattern("DM")
                        .pattern("MD")
                        .input('M', Items.MOSS_BLOCK)
                        .input('D', TagKey.of(RegistryKeys.ITEM, Identifier.of("stone_crafting_materials")))
                        .criterion(hasItem(Items.MOSS_BLOCK),
                                conditionsFromItem(Items.MOSS_BLOCK))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, BlockRegistryME.WASTE_PILE, 8)
                        .pattern("DDD")
                        .pattern("DWD")
                        .pattern("DDD")
                        .input('W', Items.ROTTEN_FLESH)
                        .input('D', ItemTagsME.DIRT)
                        .criterion(hasItem(Items.MOSS_BLOCK),
                                conditionsFromItem(Items.MOSS_BLOCK))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, BlockRegistryME.SKELETAL_PILE, 8)
                        .pattern("DDD")
                        .pattern("DBD")
                        .pattern("DDD")
                        .input('B', ItemTagsME.BONES)
                        .input('D', BlockRegistryME.WASTE_PILE)
                        .criterion(hasItem(BlockRegistryME.WASTE_PILE),
                                conditionsFromItem(BlockRegistryME.WASTE_PILE))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, BlockRegistryME.FOUL_DIRT, 8)
                        .pattern("DDD")
                        .pattern("DAD")
                        .pattern("DDD")
                        .input('A', ResourceItemsME.ASH)
                        .input('D', BlockRegistryME.WASTE_PILE)
                        .criterion(hasItem(BlockRegistryME.WASTE_PILE),
                                conditionsFromItem(BlockRegistryME.WASTE_PILE))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, BlockRegistryME.SNOWY_DIRT, 4)
                        .pattern("DS")
                        .pattern("SD")
                        .input('D', Items.DIRT)
                        .input('S', Items.SNOW_BLOCK)
                        .criterion(hasItem(Items.DIRT),
                                conditionsFromItem(Items.DIRT))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, StoneBlockSetRegistryME.DRYSTONE_SET.cobblestoneBlocks.base(), 2)
                        .pattern("CC")
                        .input('C', TagKey.of(RegistryKeys.ITEM, Identifier.of("stone_crafting_materials")))
                        .criterion(hasItem(Items.COBBLESTONE),
                                conditionsFromItem(Items.COBBLESTONE))
                        .offerTo(exporter);
                //createMossyRecipe(exporter, StoneBlockSets.DRYSTONE_SET.cobblestoneBlocks.base(), StoneBlockSets.DRYSTONE_SET.mossyCobblestoneBlocks.base());
                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, GenericBlockSetRegistryME.FRAMED_DRYSTONE.blockSet.base(), 1)
                        .pattern(" S ")
                        .pattern("SCS")
                        .pattern(" S ")
                        .input('S', Items.STICK)
                        .input('C', TagKey.of(RegistryKeys.ITEM, Identifier.of("stone_crafting_materials")))
                        .criterion(hasItem(StoneBlockSetRegistryME.DRYSTONE_SET.cobblestoneBlocks.base()),
                                conditionsFromItem(StoneBlockSetRegistryME.DRYSTONE_SET.cobblestoneBlocks.base()))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, BlockRegistryME.COBBLY_DIRT, 4)
                        .pattern("DC")
                        .pattern("CD")
                        .input('D', Items.DIRT)
                        .input('C', TagKey.of(RegistryKeys.ITEM, Identifier.of("stone_crafting_materials")))
                        .criterion(hasItem(Items.DIRT),
                                conditionsFromItem(Items.DIRT))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, BlockRegistryME.COBBLY_ASHEN_DIRT, 4)
                        .pattern("DC")
                        .pattern("CD")
                        .input('D', BlockRegistryME.ASHEN_DIRT)
                        .input('C', StoneBlockSetRegistryME.ASHENSTONE_SET.cobblestoneBlocks.base())
                        .criterion(hasItem(Items.DIRT),
                                conditionsFromItem(Items.DIRT))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, BlockRegistryME.DIRTY_ROOTS, 2)
                        .pattern(" R ")
                        .pattern("RDR")
                        .pattern(" R ")
                        .input('D', Items.ROOTED_DIRT)
                        .input('R', Items.HANGING_ROOTS)
                        .criterion(hasItem(Items.ROOTED_DIRT),
                                conditionsFromItem(Items.ROOTED_DIRT))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, DecorativeBlockRegistryME.WATERING_CAN, 1)
                        .pattern(" N ")
                        .pattern("NII")
                        .pattern(" II")
                        .input('N', ResourceItemsME.TIN_NUGGET)
                        .input('I', ResourceItemsME.TIN_INGOT)
                        .criterion(hasItem(ResourceItemsME.TIN_INGOT),
                                conditionsFromItem(ResourceItemsME.TIN_INGOT))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, DecorativeBlockRegistryME.WOODEN_BUCKET, 1)
                        .pattern(" R ")
                        .pattern("P P")
                        .pattern(" P ")
                        .input('R', DecorativeBlockRegistryME.ROPE)
                        .input('P', TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .criterion(hasItem(DecorativeBlockRegistryME.ROPE),
                                conditionsFromItem(DecorativeBlockRegistryME.ROPE))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, DecorativeBlockRegistryME.CRUDE_ROD, 1)
                        .pattern("S")
                        .pattern("S")
                        .pattern("S")
                        .input('S', ResourceItemsME.CRUDE_INGOT)
                        .criterion(hasItem(ResourceItemsME.CRUDE_INGOT),
                                conditionsFromItem(ResourceItemsME.CRUDE_INGOT))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, DecorativeBlockRegistryME.TREATED_STEEL_ROD, 1)
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

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, DecorativeBlockRegistryME.BRONZE_CHAIN, 4)
                        .pattern("N")
                        .pattern("I")
                        .pattern("N")
                        .input('N', ResourceItemsME.BRONZE_NUGGET)
                        .input('I', ResourceItemsME.BRONZE_INGOT)
                        .criterion(hasItem(ResourceItemsME.BRONZE_INGOT),
                                conditionsFromItem(ResourceItemsME.STEEL_INGOT))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, DecorativeBlockRegistryME.BRONZE_BROAD_CHAIN, 8)
                        .pattern("NN")
                        .pattern("II")
                        .pattern("NN")
                        .input('N', ResourceItemsME.BRONZE_NUGGET)
                        .input('I', ResourceItemsME.BRONZE_INGOT)
                        .criterion(hasItem(ResourceItemsME.BRONZE_INGOT),
                                conditionsFromItem(ResourceItemsME.STEEL_INGOT))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, DecorativeBlockRegistryME.CRUDE_CHAIN, 4)
                        .pattern("N")
                        .pattern("I")
                        .pattern("N")
                        .input('N', ResourceItemsME.CRUDE_NUGGET)
                        .input('I', ResourceItemsME.CRUDE_INGOT)
                        .criterion(hasItem(ResourceItemsME.CRUDE_INGOT),
                                conditionsFromItem(ResourceItemsME.STEEL_INGOT))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, DecorativeBlockRegistryME.CRUDE_BROAD_CHAIN, 8)
                        .pattern("NN")
                        .pattern("II")
                        .pattern("NN")
                        .input('N', ResourceItemsME.CRUDE_NUGGET)
                        .input('I', ResourceItemsME.CRUDE_INGOT)
                        .criterion(hasItem(ResourceItemsME.CRUDE_INGOT),
                                conditionsFromItem(ResourceItemsME.STEEL_INGOT))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, DecorativeBlockRegistryME.SPIKY_CHAIN, 4)
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

                ShapelessRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, BlockRegistryME.EMBERS, 1)
                        .input(Items.MAGMA_BLOCK, 1)
                        .input(ResourceItemsME.ASH, 1)
                        .criterion(hasItem(Items.MAGMA_BLOCK),
                                conditionsFromItem(Items.MAGMA_BLOCK))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, DecorativeBlockRegistryME.CHIMNEY, 2)
                        .pattern(" B ")
                        .pattern(" B ")
                        .pattern("PPP")
                        .input('B', Items.BRICKS)
                        .input('P', StoneBlockSetRegistryME.DOLOMITE_SET.polishedBlocks.base())
                        .criterion(hasItem(Items.BRICKS),
                                conditionsFromItem(Items.BRICKS))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, DecorativeBlockRegistryME.BIG_BRAZIER, 2)
                        .pattern("B B")
                        .pattern("BCB")
                        .pattern("SSS")
                        .input('B', BlockRegistryME.TREATED_STEEL_BARS)
                        .input('C', Items.CAMPFIRE)
                        .input('S', TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "steel_ingots")))
                        .criterion(hasItem(Items.CAMPFIRE),
                                conditionsFromItem(Items.CAMPFIRE))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, DecorativeBlockRegistryME.GILDED_BIG_BRAZIER, 2)
                        .pattern("B B")
                        .pattern("BCB")
                        .pattern("SSS")
                        .input('B', BlockRegistryME.GILDED_BARS)
                        .input('C', Items.CAMPFIRE)
                        .input('S', Items.GOLD_INGOT)
                        .criterion(hasItem(Items.CAMPFIRE),
                                conditionsFromItem(Items.CAMPFIRE))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, DecorativeBlockRegistryME.SMALL_BRAZIER, 2)
                        .pattern("BCB")
                        .pattern("SSS")
                        .input('B', BlockRegistryME.TREATED_STEEL_BARS)
                        .input('C', Items.CAMPFIRE)
                        .input('S', TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "steel_ingots")))
                        .criterion(hasItem(Items.CAMPFIRE),
                                conditionsFromItem(Items.CAMPFIRE))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, DecorativeBlockRegistryME.GILDED_SMALL_BRAZIER, 2)
                        .pattern("BCB")
                        .pattern("SSS")
                        .input('B', BlockRegistryME.GILDED_BARS)
                        .input('C', Items.CAMPFIRE)
                        .input('S', Items.GOLD_INGOT)
                        .criterion(hasItem(Items.CAMPFIRE),
                                conditionsFromItem(Items.CAMPFIRE))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, DecorativeBlockRegistryME.FIRE_BOWL, 2)
                        .pattern("SCS")
                        .pattern("SSS")
                        .input('C', Items.CAMPFIRE)
                        .input('S', TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "steel_ingots")))
                        .criterion(hasItem(Items.CAMPFIRE),
                                conditionsFromItem(Items.CAMPFIRE))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, DecorativeBlockRegistryME.BONFIRE, 1)
                        .pattern(" L ")
                        .pattern("LCL")
                        .input('C', Items.CAMPFIRE)
                        .input('L', TagKey.of(RegistryKeys.ITEM, Identifier.of("logs")))
                        .criterion(hasItem(Items.CAMPFIRE),
                                conditionsFromItem(Items.CAMPFIRE))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, DecorativeBlockRegistryME.GROUND_BOOK, 1)
                        .pattern("BSR")
                        .input('B', Items.BOOK)
                        .input('S', Items.STRING)
                        .input('R', Items.RED_DYE)
                        .criterion(hasItem(Items.BOOK),
                                conditionsFromItem(Items.BOOK))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, DecorativeBlockRegistryME.DWARVEN_GROUND_BOOK, 1)
                        .pattern("BG")
                        .input('B', Items.BOOK)
                        .input('G', Items.GOLD_NUGGET)
                        .criterion(hasItem(Items.BOOK),
                                conditionsFromItem(Items.BOOK))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, DecorativeBlockRegistryME.SMALL_CRATE, 1)
                        .pattern("SSS")
                        .pattern("PPP")
                        .input('S', TagKey.of(RegistryKeys.ITEM, Identifier.of("wooden_slabs")))
                        .input('P', TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .criterion(hasItem(Items.OAK_PLANKS),
                                conditionsFromItem(Items.OAK_PLANKS))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, DecorativeBlockRegistryME.THIN_BARREL, 1)
                        .pattern("VSV")
                        .pattern("V V")
                        .pattern("VSV")
                        .input('S', TagKey.of(RegistryKeys.ITEM, Identifier.of("wooden_slabs")))
                        .input('V', TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "wooden_vertical_slabs")))
                        .criterion(hasItem(Items.OAK_SLAB),
                                conditionsFromItem(Items.OAK_SLAB))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, DecorativeBlockRegistryME.LARCH_COFFER, 1)
                        .pattern("TLT")
                        .pattern("L L")
                        .pattern("LLL")
                        .input('T', ResourceItemsME.TIN_NUGGET)
                        .input('L', WoodBlockSetRegistryME.LARCH_SET.planksBlocks.base())
                        .criterion(hasItem(WoodBlockSetRegistryME.LARCH_SET.planksBlocks.base()),
                                conditionsFromItem(WoodBlockSetRegistryME.LARCH_SET.planksBlocks.base()))
                        .offerTo(exporter);
                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, DecorativeBlockRegistryME.PINE_COFFER, 1)
                        .pattern("TLT")
                        .pattern("L L")
                        .pattern("LLL")
                        .input('T', ResourceItemsME.TIN_NUGGET)
                        .input('L', WoodBlockSetRegistryME.PINE_SET.planksBlocks.base())
                        .criterion(hasItem(WoodBlockSetRegistryME.PINE_SET.planksBlocks.base()),
                                conditionsFromItem(WoodBlockSetRegistryME.PINE_SET.planksBlocks.base()))
                        .offerTo(exporter);
                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, DecorativeBlockRegistryME.SPRUCE_COFFER, 1)
                        .pattern("TLT")
                        .pattern("L L")
                        .pattern("LLL")
                        .input('T', ResourceItemsME.TIN_NUGGET)
                        .input('L', Blocks.SPRUCE_PLANKS)
                        .criterion(hasItem(Blocks.SPRUCE_PLANKS),
                                conditionsFromItem(Blocks.SPRUCE_PLANKS))
                        .offerTo(exporter);
                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, DecorativeBlockRegistryME.FIR_COFFER, 1)
                        .pattern("TLT")
                        .pattern("L L")
                        .pattern("LLL")
                        .input('T', ResourceItemsME.TIN_NUGGET)
                        .input('L', WoodBlockSetRegistryME.FIR_SET.planksBlocks.base())
                        .criterion(hasItem(WoodBlockSetRegistryME.FIR_SET.planksBlocks.base()),
                                conditionsFromItem(WoodBlockSetRegistryME.FIR_SET.planksBlocks.base()))
                        .offerTo(exporter);
                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, DecorativeBlockRegistryME.BEECH_COFFER, 1)
                        .pattern("TLT")
                        .pattern("L L")
                        .pattern("LLL")
                        .input('T', ResourceItemsME.TIN_NUGGET)
                        .input('L', WoodBlockSetRegistryME.BEECH_SET.planksBlocks.base())
                        .criterion(hasItem(WoodBlockSetRegistryME.BEECH_SET.planksBlocks.base()),
                                conditionsFromItem(WoodBlockSetRegistryME.BEECH_SET.planksBlocks.base()))
                        .offerTo(exporter);
                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, DecorativeBlockRegistryME.CHESTNUT_COFFER, 1)
                        .pattern("TLT")
                        .pattern("L L")
                        .pattern("LLL")
                        .input('T', ResourceItemsME.TIN_NUGGET)
                        .input('L', WoodBlockSetRegistryME.CHESTNUT_SET.planksBlocks.base())
                        .criterion(hasItem(WoodBlockSetRegistryME.CHESTNUT_SET.planksBlocks.base()),
                                conditionsFromItem(WoodBlockSetRegistryME.CHESTNUT_SET.planksBlocks.base()))
                        .offerTo(exporter);
                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, DecorativeBlockRegistryME.OAK_COFFER, 1)
                        .pattern("TLT")
                        .pattern("L L")
                        .pattern("LLL")
                        .input('T', ResourceItemsME.TIN_NUGGET)
                        .input('L', WoodBlockSetRegistryME.OAK_SET.planksBlocks.base())
                        .criterion(hasItem(WoodBlockSetRegistryME.OAK_SET.planksBlocks.base()),
                                conditionsFromItem(WoodBlockSetRegistryME.OAK_SET.planksBlocks.base()))
                        .offerTo(exporter);
                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, DecorativeBlockRegistryME.WILLOW_COFFER, 1)
                        .pattern("TLT")
                        .pattern("L L")
                        .pattern("LLL")
                        .input('T', ResourceItemsME.TIN_NUGGET)
                        .input('L', WoodBlockSetRegistryME.WILLOW_SET.planksBlocks.base())
                        .criterion(hasItem(WoodBlockSetRegistryME.WILLOW_SET.planksBlocks.base()),
                                conditionsFromItem(WoodBlockSetRegistryME.WILLOW_SET.planksBlocks.base()))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, DecorativeBlockRegistryME.SACK, 1)
                        .pattern("C C")
                        .pattern("CRC")
                        .pattern("CCC")
                        .input('C', GenericBlockSetRegistryME.CANVAS.blockSet.base())
                        .input('R', Items.RESIN_CLUMP)
                        .criterion(hasItem(Items.RESIN_CLUMP),
                                conditionsFromItem(Items.RESIN_CLUMP))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, Items.BELL, 1)
                        .pattern("VSV")
                        .pattern("VGV")
                        .input('S', Items.STICK)
                        .input('V', StoneBlockSetRegistryME.STONE_SET.baseBlocks.verticalSlab())
                        .input('G', Items.GOLD_INGOT)
                        .criterion(hasItem(Items.GOLD_INGOT),
                                conditionsFromItem(Items.GOLD_INGOT))
                        .offerTo(exporter);

                ShapelessRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, NatureBlockRegistryME.STICKY_SNOW, 8)
                        .input(Items.SNOWBALL, 8)
                        .input(Items.WATER_BUCKET, 1)
                        .criterion(hasItem(Items.SNOWBALL),
                                conditionsFromItem(Items.SNOWBALL))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, NatureBlockRegistryME.STICKY_ICE, 4)
                        .pattern("II")
                        .pattern("II")
                        .input('I', Items.ICE)
                        .criterion(hasItem(Items.ICE),
                                conditionsFromItem(Items.ICE))
                        .offerTo(exporter);

                createBannerPatternRecipe(exporter, ResourceItemsME.PIPEWEED, ResourceItemsME.PIPEWEED_BANNER_PATTERN);
                createBannerPatternRecipe(exporter, NatureBlockRegistryME.LEBETHRON_SAPLING.asItem(), ResourceItemsME.GONDOR_BANNER_PATTERN);
                createBannerPatternRecipe(exporter, NatureBlockRegistryME.MALLORN_SAPLING.asItem(), ResourceItemsME.LOTHLORIEN_BANNER_PATTERN);
                createBannerPatternRecipe(exporter, Items.MAGMA_BLOCK, ResourceItemsME.MORDOR_BANNER_PATTERN);
                createBannerPatternRecipe(exporter, Items.HAY_BLOCK, ResourceItemsME.ROHAN_BANNER_PATTERN);
                createBannerPatternRecipe(exporter, Items.BONE, ResourceItemsME.MISTY_MOUNTAINS_ORCS_BANNER_PATTERN);
                createBannerPatternRecipe(exporter, Items.BONE_BLOCK, ResourceItemsME.GOBLIN_SKULL_BANNER_PATTERN);
                createBannerPatternRecipe(exporter, Items.SKELETON_SKULL, ResourceItemsME.SCREECHING_SKULL_BANNER_PATTERN);
                createBannerPatternRecipe(exporter, Items.WHITE_DYE, ResourceItemsME.ISENGARD_BANNER_PATTERN);
                createBannerPatternRecipe(exporter, ToolItemsME.DWARVEN_SMITHING_HAMMER, ResourceItemsME.ANVIL_BANNER_PATTERN);
                createBannerPatternRecipe(exporter, ResourceItemsME.BRONZE_INGOT, ResourceItemsME.BELL_BANNER_PATTERN);
                createBannerPatternRecipe(exporter, Items.GOLD_NUGGET, ResourceItemsME.DWARF_CROWN_BANNER_PATTERN);
                createBannerPatternRecipe(exporter, Items.SPIDER_EYE, ResourceItemsME.SPIDER_BANNER_PATTERN);
                createBannerPatternRecipe(exporter, Items.BOW, ResourceItemsME.BOW_BANNER_PATTERN);
                createBannerPatternRecipe(exporter, Items.OAK_LEAVES, ResourceItemsME.OAK_LEAF_BANNER_PATTERN);

                createBrickRecipe(exporter, BlockRegistryME.POINTED_DOLOMITE.asItem(), StoneBlockSetRegistryME.DOLOMITE_SET.baseBlocks.base(), 1);
                createBrickRecipe(exporter, BlockRegistryME.POINTED_GALONN.asItem(), StoneBlockSetRegistryME.GALONN_SET.baseBlocks.base(), 1);
                createBrickRecipe(exporter, BlockRegistryME.POINTED_IZHERABAN.asItem(), StoneBlockSetRegistryME.IZHERABAN_SET.baseBlocks.base(), 1);
                createBrickRecipe(exporter, BlockRegistryME.POINTED_LIMESTONE.asItem(), StoneBlockSetRegistryME.LIMESTONE_SET.baseBlocks.base(), 1);

                CookingRecipeJsonBuilder.createSmoking(Ingredient.ofTag(itemLookup.getOrThrow(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))), RecipeCategory.BUILDING_BLOCKS, WoodBlockSetRegistryME.SCORCHED_SET.planksBlocks.base(), 0.0f, 100)
                        .criterion(hasItem(Items.OAK_PLANKS),
                                conditionsFromItem(Items.OAK_PLANKS)).offerTo(exporter, String.valueOf(Identifier.of(MiddleEarth.MOD_ID, Registries.BLOCK.getId(WoodBlockSetRegistryME.SCORCHED_SET.planksBlocks.base()).getPath() + "_from_smoking")));
                CookingRecipeJsonBuilder.createSmoking(Ingredient.ofTag(itemLookup.getOrThrow(TagKey.of(RegistryKeys.ITEM, Identifier.of("logs")))), RecipeCategory.BUILDING_BLOCKS, WoodBlockSetRegistryME.SCORCHED_SET.logBlocks.log(), 0.0f, 100)
                        .criterion(hasItem(Items.OAK_LOG),
                                conditionsFromItem(Items.OAK_LOG)).offerTo(exporter, String.valueOf(Identifier.of(MiddleEarth.MOD_ID, Registries.BLOCK.getId(WoodBlockSetRegistryME.SCORCHED_SET.logBlocks.log()).getPath() + "_from_smoking")));

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, NatureBlockRegistryME.SHORT_ICICLES, 4)
                        .pattern("III")
                        .pattern(" I ")
                        .input('I', Items.ICE)
                        .criterion(hasItem(Items.ICE),
                                conditionsFromItem(Items.ICE))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, NatureBlockRegistryME.DROOPING_ICICLES, 4)
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
                        .input('B', BlockRegistryME.TIN_BLOCK)
                        .criterion(hasItem(ResourceItemsME.TIN_INGOT),
                                conditionsFromItem(ResourceItemsME.TIN_INGOT))
                        .offerTo(exporter, String.valueOf(Identifier.of(MiddleEarth.MOD_ID, Registries.ITEM.getId(Items.CAULDRON).getPath() + "_alt")));

                createCenterSurroundRecipe(exporter, Blocks.TUFF.asItem(), Items.RAW_COPPER, StoneBlockSetRegistryME.GREEN_TUFF_SET.baseBlocks.base().asItem(), 8);
                createCenterSurroundRecipe(exporter, StoneBlockSetRegistryME.SLATE_SET.baseBlocks.base().asItem(), Items.RAW_COPPER, StoneBlockSetRegistryME.KHAGALABAN_SET.baseBlocks.base().asItem(), 8);
                createCenterSurroundRecipe(exporter, Blocks.TUFF.asItem(), Items.IRON_NUGGET, StoneBlockSetRegistryME.IRONSTONE_SET.baseBlocks.base().asItem(), 8);

                offerStonecuttingRecipe(RecipeCategory.BUILDING_BLOCKS, DecorativeBlockRegistryME.BROWN_JUG, Items.CLAY);
                offerStonecuttingRecipe(RecipeCategory.BUILDING_BLOCKS, DecorativeBlockRegistryME.LARGE_JUG, Items.CLAY);
                offerStonecuttingRecipe(RecipeCategory.BUILDING_BLOCKS, DecorativeBlockRegistryME.GRAY_POT, Items.CLAY);

                offerStonecuttingRecipe(RecipeCategory.BUILDING_BLOCKS, DecorativeBlockRegistryME.BROWN_JAR, Items.CLAY);
                offerStonecuttingRecipe(RecipeCategory.BUILDING_BLOCKS, DecorativeBlockRegistryME.CLAY_JAR, Items.CLAY);
                offerStonecuttingRecipe(RecipeCategory.BUILDING_BLOCKS, DecorativeBlockRegistryME.GRAY_JAR, Items.CLAY);

                offerStonecuttingRecipe(RecipeCategory.BUILDING_BLOCKS, DecorativeBlockRegistryME.AMPHORA, Items.CLAY);
                offerStonecuttingRecipe(RecipeCategory.BUILDING_BLOCKS, DecorativeBlockRegistryME.BROWN_AMPHORA, Items.CLAY);
                offerStonecuttingRecipe(RecipeCategory.BUILDING_BLOCKS, DecorativeBlockRegistryME.GRAY_VASE, Items.CLAY);

                offerStonecuttingRecipe(RecipeCategory.BUILDING_BLOCKS, DecorativeBlockRegistryME.BROWN_FAT_POT, Items.CLAY);
                offerStonecuttingRecipe(RecipeCategory.BUILDING_BLOCKS, DecorativeBlockRegistryME.FAT_POT, Items.CLAY);
                offerStonecuttingRecipe(RecipeCategory.BUILDING_BLOCKS, DecorativeBlockRegistryME.GRAY_FAT_POT, Items.CLAY);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, DecorativeBlockRegistryME.POT_OF_GOLD, 1)
                        .pattern(" G ")
                        .pattern("GGG")
                        .pattern(" P ")
                        .input('P', DecorativeBlockRegistryME.FAT_POT)
                        .input('G', ResourceItemsME.GOLD_COIN)
                        .criterion(hasItem(ResourceItemsME.GOLD_COIN),
                                conditionsFromItem(ResourceItemsME.GOLD_COIN))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, NatureBlockRegistryME.AZALEA_FLOWER_GROWTH.asItem(), 8)
                        .pattern("lll")
                        .pattern("lll")
                        .input('l', Items.FLOWERING_AZALEA_LEAVES)
                        .criterion(hasItem(Items.FLOWERING_AZALEA_LEAVES),
                                conditionsFromItem(Items.FLOWERING_AZALEA_LEAVES))
                        .offerTo(exporter);
                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, NatureBlockRegistryME.DRY_GROWTH.asItem(), 4)
                        .pattern("sss")
                        .pattern("sss")
                        .input('s', Items.STICK)
                        .criterion(hasItem(Items.STICK),
                                conditionsFromItem(Items.STICK))
                        .offerTo(exporter);
                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, NatureBlockRegistryME.THORNY_GROWTH.asItem(), 6)
                        .pattern("sls")
                        .pattern("sls")
                        .input('s', Items.STICK)
                        .input('l', FoodItemsME.TOUGH_BERRIES)
                        .criterion(hasItem(FoodItemsME.TOUGH_BERRIES),
                                conditionsFromItem(FoodItemsME.TOUGH_BERRIES))
                        .offerTo(exporter);
                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, NatureBlockRegistryME.GREEN_GROWTH.asItem(), 8)
                        .pattern("lll")
                        .pattern("lll")
                        .input('l', TagKey.of(RegistryKeys.ITEM, Identifier.of("leaves")))
                        .criterion(hasItem(Items.OAK_LEAVES),
                                conditionsFromItem(Items.OAK_LEAVES))
                        .offerTo(exporter);
                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, NatureBlockRegistryME.IVY_GROWTH.asItem(), 6)
                        .pattern("sls")
                        .pattern("sls")
                        .input('s', Items.STICK)
                        .input('l', TagKey.of(RegistryKeys.ITEM, Identifier.of("leaves")))
                        .criterion(hasItem(Items.OAK_LEAVES),
                                conditionsFromItem(Items.OAK_LEAVES))
                        .offerTo(exporter);
                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, NatureBlockRegistryME.LILAC_FLOWER_GROWTH.asItem(), 8)
                        .pattern("lfl")
                        .pattern("lfl")
                        .input('f', Items.LILAC)
                        .input('l', TagKey.of(RegistryKeys.ITEM, Identifier.of("leaves")))
                        .criterion(hasItem(Items.OAK_LEAVES),
                                conditionsFromItem(Items.OAK_LEAVES))
                        .offerTo(exporter);
                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, NatureBlockRegistryME.PINK_FLOWER_GROWTH.asItem(), 8)
                        .pattern("lfl")
                        .pattern("lfl")
                        .input('f', NatureBlockRegistryME.PINK_FLOWERS)
                        .input('l', TagKey.of(RegistryKeys.ITEM, Identifier.of("leaves")))
                        .criterion(hasItem(NatureBlockRegistryME.PINK_FLOWERS),
                                conditionsFromItem(NatureBlockRegistryME.PINK_FLOWERS))
                        .offerTo(exporter);
                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, NatureBlockRegistryME.RED_FLOWER_GROWTH.asItem(), 8)
                        .pattern("lfl")
                        .pattern("lfl")
                        .input('f', NatureBlockRegistryME.RED_FLOWERS)
                        .input('l', TagKey.of(RegistryKeys.ITEM, Identifier.of("leaves")))
                        .criterion(hasItem(NatureBlockRegistryME.RED_FLOWERS),
                                conditionsFromItem(NatureBlockRegistryME.RED_FLOWERS))
                        .offerTo(exporter);
                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, NatureBlockRegistryME.WHITE_FLOWER_GROWTH.asItem(), 8)
                        .pattern("lfl")
                        .pattern("lfl")
                        .input('f', NatureBlockRegistryME.WHITE_FLOWERS)
                        .input('l', TagKey.of(RegistryKeys.ITEM, Identifier.of("leaves")))
                        .criterion(hasItem(NatureBlockRegistryME.WHITE_FLOWERS),
                                conditionsFromItem(NatureBlockRegistryME.WHITE_FLOWERS))
                        .offerTo(exporter);
                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, NatureBlockRegistryME.YELLOW_FLOWER_GROWTH.asItem(), 8)
                        .pattern("lfl")
                        .pattern("lfl")
                        .input('f', NatureBlockRegistryME.YELLOW_FLOWERS)
                        .input('l', TagKey.of(RegistryKeys.ITEM, Identifier.of("leaves")))
                        .criterion(hasItem(NatureBlockRegistryME.YELLOW_FLOWERS),
                                conditionsFromItem(NatureBlockRegistryME.YELLOW_FLOWERS))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, NatureBlockRegistryME.FROZEN_GROWTH.asItem(), 8)
                        .pattern("sis")
                        .pattern("sis")
                        .input('i', NatureBlockRegistryME.STICKY_SNOW)
                        .input('s', NatureBlockRegistryME.DRY_GROWTH)
                        .criterion(hasItem(NatureBlockRegistryME.DRY_GROWTH),
                                conditionsFromItem(NatureBlockRegistryME.DRY_GROWTH))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, DecorativeBlockRegistryME.GOLDEN_CHALICE, 1)
                        .pattern("I")
                        .pattern("N")
                        .pattern("N")
                        .input('I', Items.GOLD_INGOT)
                        .input('N', Items.GOLD_NUGGET)
                        .criterion(hasItem(Items.GOLD_INGOT),
                                conditionsFromItem(Items.GOLD_INGOT))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, DecorativeBlockRegistryME.COPPER_TREASURE_HEAP_LAYER, 1)
                        .pattern("NNN")
                        .input('N', ResourceItemsME.COPPER_COIN)
                        .criterion(hasItem(ResourceItemsME.COPPER_COIN),
                                conditionsFromItem(ResourceItemsME.COPPER_COIN))
                        .offerTo(exporter);
                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, DecorativeBlockRegistryME.SILVER_TREASURE_HEAP_LAYER, 1)
                        .pattern("NNN")
                        .input('N', ResourceItemsME.SILVER_COIN)
                        .criterion(hasItem(ResourceItemsME.SILVER_COIN),
                                conditionsFromItem(ResourceItemsME.SILVER_COIN))
                        .offerTo(exporter);
                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, DecorativeBlockRegistryME.GOLD_TREASURE_HEAP_LAYER, 1)
                        .pattern("NNN")
                        .input('N', ResourceItemsME.GOLD_COIN)
                        .criterion(hasItem(ResourceItemsME.GOLD_COIN),
                                conditionsFromItem(ResourceItemsME.GOLD_COIN))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, DecorativeBlockRegistryME.COPPER_COIN_PILE, 1)
                        .pattern("NN")
                        .pattern("NN")
                        .input('N', ResourceItemsME.COPPER_COIN)
                        .criterion(hasItem(ResourceItemsME.COPPER_COIN),
                                conditionsFromItem(ResourceItemsME.COPPER_COIN))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, DecorativeBlockRegistryME.SILVER_COIN_PILE, 1)
                        .pattern("NN")
                        .pattern("NN")
                        .input('N', ResourceItemsME.SILVER_COIN)
                        .criterion(hasItem(ResourceItemsME.SILVER_COIN),
                                conditionsFromItem(ResourceItemsME.SILVER_COIN))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, DecorativeBlockRegistryME.GOLD_COIN_PILE, 1)
                        .pattern("NN")
                        .pattern("NN")
                        .input('N', ResourceItemsME.GOLD_COIN)
                        .criterion(hasItem(ResourceItemsME.GOLD_COIN),
                                conditionsFromItem(ResourceItemsME.GOLD_COIN))
                        .offerTo(exporter);

                ShapelessRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, ResourceItemsME.COPPER_COIN, 3)
                        .input(DecorativeBlockRegistryME.COPPER_TREASURE_HEAP_LAYER)
                        .criterion(hasItem(DecorativeBlockRegistryME.COPPER_TREASURE_HEAP_LAYER),
                                conditionsFromItem(DecorativeBlockRegistryME.COPPER_TREASURE_HEAP_LAYER))
                        .offerTo(exporter, String.valueOf(Identifier.of(MiddleEarth.MOD_ID, "copper_coin_from_treasure")));

                ShapelessRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, ResourceItemsME.SILVER_COIN, 3)
                        .input(DecorativeBlockRegistryME.SILVER_TREASURE_HEAP_LAYER)
                        .criterion(hasItem(DecorativeBlockRegistryME.SILVER_TREASURE_HEAP_LAYER),
                                conditionsFromItem(DecorativeBlockRegistryME.SILVER_TREASURE_HEAP_LAYER))
                        .offerTo(exporter, String.valueOf(Identifier.of(MiddleEarth.MOD_ID, "silver_coin_from_treasure")));

                ShapelessRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, ResourceItemsME.GOLD_COIN, 3)
                        .input(DecorativeBlockRegistryME.GOLD_TREASURE_HEAP_LAYER)
                        .criterion(hasItem(DecorativeBlockRegistryME.GOLD_TREASURE_HEAP_LAYER),
                                conditionsFromItem(DecorativeBlockRegistryME.GOLD_TREASURE_HEAP_LAYER))
                        .offerTo(exporter, String.valueOf(Identifier.of(MiddleEarth.MOD_ID, "gold_nugget_from_treasure")));

                ShapelessRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, ResourceItemsME.COPPER_COIN, 4)
                        .input(DecorativeBlockRegistryME.COPPER_COIN_PILE)
                        .criterion(hasItem(DecorativeBlockRegistryME.COPPER_COIN_PILE),
                                conditionsFromItem(DecorativeBlockRegistryME.COPPER_COIN_PILE))
                        .offerTo(exporter, String.valueOf(Identifier.of(MiddleEarth.MOD_ID, "copper_coin_from_pile")));

                ShapelessRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, ResourceItemsME.SILVER_COIN, 4)
                        .input(DecorativeBlockRegistryME.SILVER_COIN_PILE)
                        .criterion(hasItem(DecorativeBlockRegistryME.SILVER_COIN_PILE),
                                conditionsFromItem(DecorativeBlockRegistryME.SILVER_COIN_PILE))
                        .offerTo(exporter, String.valueOf(Identifier.of(MiddleEarth.MOD_ID, "silver_coin_from_pile")));

                ShapelessRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, ResourceItemsME.GOLD_COIN, 4)
                        .input(DecorativeBlockRegistryME.GOLD_COIN_PILE)
                        .criterion(hasItem(DecorativeBlockRegistryME.GOLD_COIN_PILE),
                                conditionsFromItem(DecorativeBlockRegistryME.GOLD_COIN_PILE))
                        .offerTo(exporter, String.valueOf(Identifier.of(MiddleEarth.MOD_ID, "gold_nugget_from_pile")));

                ShapelessRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, NatureBlockRegistryME.SHORT_BULRUSH, 2)
                        .input(NatureBlockRegistryME.TALL_BULRUSH)
                        .criterion(hasItem(NatureBlockRegistryME.TALL_BULRUSH),
                                conditionsFromItem(NatureBlockRegistryME.TALL_BULRUSH))
                        .offerTo(exporter);

                ShapelessRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, NatureBlockRegistryME.SHORT_REEDS, 2)
                        .input(ResourceItemsME.REEDS)
                        .criterion(hasItem(ResourceItemsME.REEDS),
                                conditionsFromItem(ResourceItemsME.REEDS))
                        .offerTo(exporter);

                ShapelessRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, NatureBlockRegistryME.SHORT_DEAD_RUSHES, 2)
                        .input(NatureBlockRegistryME.DEAD_RUSHES)
                        .criterion(hasItem(NatureBlockRegistryME.DEAD_RUSHES),
                                conditionsFromItem(NatureBlockRegistryME.DEAD_RUSHES))
                        .offerTo(exporter);

                ShapelessRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, NatureBlockRegistryME.SHORT_RUSHES, 2)
                        .input(NatureBlockRegistryME.RUSHES)
                        .criterion(hasItem(NatureBlockRegistryME.RUSHES),
                                conditionsFromItem(NatureBlockRegistryME.RUSHES))
                        .offerTo(exporter);

                ShapelessRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, NatureBlockRegistryME.SHORT_CATTAILS, 2)
                        .input(NatureBlockRegistryME.TALL_CATTAILS)
                        .criterion(hasItem(NatureBlockRegistryME.TALL_CATTAILS),
                                conditionsFromItem(NatureBlockRegistryME.TALL_CATTAILS))
                        .offerTo(exporter);

                ShapelessRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, ResourceItemsME.GOLD_COIN, 4)
                        .input(DecorativeBlockRegistryME.POT_OF_GOLD)
                        .criterion(hasItem(DecorativeBlockRegistryME.POT_OF_GOLD),
                                conditionsFromItem(DecorativeBlockRegistryME.POT_OF_GOLD))
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

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, StoneBlockSetRegistryME.SLATE_SET.baseBlocks.base(), 4)
                        .pattern("DS")
                        .pattern("SD")
                        .input('D', Items.DEEPSLATE)
                        .input('S', Items.STONE)
                        .criterion(hasItem(Items.DEEPSLATE),
                                conditionsFromItem(Items.DEEPSLATE))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, StoneBlockSetRegistryME.BLUE_TUFF_SET.baseBlocks.base(), 4)
                        .pattern("TG")
                        .pattern("GT")
                        .input('T', Items.TUFF)
                        .input('G', StoneBlockSetRegistryME.KHAGALABAN_SET.baseBlocks.base())
                        .criterion(hasItem(StoneBlockSetRegistryME.KHAGALABAN_SET.baseBlocks.base()),
                                conditionsFromItem(StoneBlockSetRegistryME.KHAGALABAN_SET.baseBlocks.base()))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.BUILDING_BLOCKS, StoneBlockSetRegistryME.HEMATITE_SET.baseBlocks.base(), 4)
                        .pattern("SI")
                        .pattern("IS")
                        .input('S', Items.STONE)
                        .input('I', StoneBlockSetRegistryME.IRONSTONE_SET.baseBlocks.base())
                        .criterion(hasItem(StoneBlockSetRegistryME.IRONSTONE_SET.baseBlocks.base()),
                                conditionsFromItem(StoneBlockSetRegistryME.IRONSTONE_SET.baseBlocks.base()))
                        .offerTo(exporter);

                createSmokingRecipe(exporter, Items.SHORT_GRASS, NatureBlockRegistryME.SCORCHED_GRASS.asItem());
                createSmokingRecipe(exporter, NatureBlockRegistryME.GRASS_TUFT.asItem(), NatureBlockRegistryME.SCORCHED_TUFT.asItem());
                createSmokingRecipe(exporter, NatureBlockRegistryME.GREEN_SHRUB.asItem(), NatureBlockRegistryME.SCORCHED_SHRUB.asItem());
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
            private void createChiseledStoneSetRecipes(BlockRecordTypes.PillarSet base) {
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
                    case 1 -> AlloyRecipeJsonBuilder.createAlloyRecipe(this.itemLookup, RecipeCategory.MISC, output, amount, 0)
                            .input(input)
                            .criterion(hasItem(input),
                                    conditionsFromItem(input))
                            .offerTo(exporter, String.valueOf(Identifier.of(MiddleEarth.MOD_ID, output + "_from_melting_1_" + Registries.ITEM.getId(input).getPath())));
                    case 2 -> AlloyRecipeJsonBuilder.createAlloyRecipe(this.itemLookup, RecipeCategory.MISC, output, amount * 2, 0)
                            .input(input)
                            .input(input)
                            .criterion(hasItem(input),
                                    conditionsFromItem(input))
                            .offerTo(exporter, String.valueOf(Identifier.of(MiddleEarth.MOD_ID, output + "_from_melting_2_" + Registries.ITEM.getId(input).getPath())));
                    case 3 -> AlloyRecipeJsonBuilder.createAlloyRecipe(this.itemLookup, RecipeCategory.MISC, output, amount * 3,0)
                            .input(input)
                            .input(input)
                            .input(input)
                            .criterion(hasItem(input),
                                    conditionsFromItem(input))
                            .offerTo(exporter, String.valueOf(Identifier.of(MiddleEarth.MOD_ID, output + "_from_melting_3_" + Registries.ITEM.getId(input).getPath())));
                    case 4 -> AlloyRecipeJsonBuilder.createAlloyRecipe(this.itemLookup, RecipeCategory.MISC, output, amount * 4, 0)
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
                    case 1 -> AlloyRecipeJsonBuilder.createAlloyRecipe(this.itemLookup, RecipeCategory.MISC, output, amount, 0)
                            .input(input)
                            .criterion(hasItem(DecorativeItemsME.FORGE),
                                    conditionsFromItem(DecorativeItemsME.FORGE))
                            .offerTo(exporter, String.valueOf(Identifier.of(MiddleEarth.MOD_ID, output + "_from_melting_1_" + input.id().getPath())));
                    case 2 -> AlloyRecipeJsonBuilder.createAlloyRecipe(this.itemLookup, RecipeCategory.MISC, output, amount * 2, 0)
                            .input(input)
                            .input(input)
                            .criterion(hasItem(DecorativeItemsME.FORGE),
                                    conditionsFromItem(DecorativeItemsME.FORGE))
                            .offerTo(exporter, String.valueOf(Identifier.of(MiddleEarth.MOD_ID, output + "_from_melting_2_" + input.id().getPath())));
                    case 3 -> AlloyRecipeJsonBuilder.createAlloyRecipe(this.itemLookup, RecipeCategory.MISC, output, amount * 3, 0)
                            .input(input)
                            .input(input)
                            .input(input)
                            .criterion(hasItem(DecorativeItemsME.FORGE),
                                    conditionsFromItem(DecorativeItemsME.FORGE))
                            .offerTo(exporter, String.valueOf(Identifier.of(MiddleEarth.MOD_ID, output + "_from_melting_3_" + input.id().getPath())));
                    case 4 -> AlloyRecipeJsonBuilder.createAlloyRecipe(this.itemLookup, RecipeCategory.MISC, output, amount * 4, 0)
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
            private void createGenericRecipes(SimpleBlockSetBuilder set) {
                offerSlabRecipe(RecipeCategory.BUILDING_BLOCKS, set.blockSet.slab().asItem(), set.blockSet.base().asItem());
                createVerticalSlabsRecipe(exporter, set.blockSet.slab(), set.blockSet.verticalSlab());
                createSlabsFromVerticalRecipe(exporter, set.blockSet.verticalSlab(), set.blockSet.slab());
                createStairsRecipe(exporter, set.blockSet.base(), set.blockSet.stairs());
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

            private void createRoofingRecipe(RecipeExporter exporter, Block input, Block output) {
                ShapedRecipeJsonBuilder.create(this.itemLookup, RecipeCategory.BUILDING_BLOCKS, output, 2)
                        .pattern(" w ")
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
