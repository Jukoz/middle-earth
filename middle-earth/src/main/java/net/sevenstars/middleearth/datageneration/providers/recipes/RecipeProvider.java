package net.sevenstars.middleearth.datageneration.providers.recipes;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.data.recipes.SpecialRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CampfireCookingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.minecraft.world.item.crafting.SmokingRecipe;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
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

public class RecipeProvider extends net.minecraft.data.recipes.RecipeProvider {

    private static final int INGOT_LIQUID_VALUE = 144;
    private HolderLookup.RegistryLookup<Item> itemLookup;
    private RecipeOutput recipeOutput;

    public RecipeProvider(PackOutput recipeOutput, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(recipeOutput, registriesFuture);
    }

    @Override
    protected void buildRecipes(RecipeOutput recipeOutput, HolderLookup.Provider registries) {
        this.recipeOutput = recipeOutput;
        this.itemLookup = registries.lookupOrThrow(Registries.ITEM);
        HolderLookup.RegistryLookup<Item> itemLookup = this.itemLookup;
                //region STONE RECIPES
                for (StoneBlockSetBuilder record : StoneBlockSets.stoneSetsList) {
                    if(record.hasMossy) {
                        createStoneSetRecipes(record.mossyCobblestoneBlocks);
                        createStoneSetRecipes(record.mossyBrickBlocks);
                        createStoneSetRecipes(record.mossyPolishedBlocks);
                        createStoneSetRecipes(record.mossyPillarBlocks);
                        createStoneSetRecipes(record.mossyTileBlocks);
                        createStoneSetRecipes(record.mossySmoothBlocks);
                        if(record.mossyCobblestoneBlocks != null && record.cobblestoneBlocks != null
                                && !isVanillaBlock(record.mossyCobblestoneBlocks.base())) {
                            createMossyRecipe(recipeOutput, record.cobblestoneBlocks.base(), record.mossyCobblestoneBlocks.base());
                        }
                        if(record.mossyBrickBlocks != null && record.brickBlocks != null
                                && !isVanillaBlock(record.mossyBrickBlocks.base())) {
                            createMossyRecipe(recipeOutput, record.brickBlocks.base(), record.mossyBrickBlocks.base());
                        }
                        if(record.mossyPillarBlocks != null && record.pillarBlocks != null
                                && !isVanillaBlock(record.mossyPillarBlocks.base())) {
                            createMossyRecipe(recipeOutput, record.pillarBlocks.base(), record.mossyPillarBlocks.base());
                        }
                        if(record.mossyPolishedBlocks != null && record.polishedBlocks != null
                                && !isVanillaBlock(record.mossyPolishedBlocks.base())) {
                            createMossyRecipe(recipeOutput, record.polishedBlocks.base(), record.mossyPolishedBlocks.base());
                        }
                        if(record.mossyTileBlocks != null && record.tileBlocks != null
                                && !isVanillaBlock(record.mossyTileBlocks.base())) {
                            createMossyRecipe(recipeOutput, record.tileBlocks.base(), record.mossyTileBlocks.base());
                        }
                        if(record.mossySmoothBlocks != null && record.smoothBlocks != null
                                && !isVanillaBlock(record.mossySmoothBlocks.base())) {
                            createMossyRecipe(recipeOutput, record.smoothBlocks.base(), record.mossySmoothBlocks.base());
                        }
                    }
                    if(record.hasCracked) {
                        if(record.crackedBrickBlocks != null && record.brickBlocks != null) {
                            createStoneSetRecipes(record.crackedBrickBlocks);
                            if(!isVanillaBlock(record.crackedBrickBlocks.base())) {
                                oreSmelting(List.of(record.brickBlocks.base()), RecipeCategory.BUILDING_BLOCKS,
                                        record.crackedBrickBlocks.base(), 0.1f, 200, "cracked_bricks");
                            }
                        }
                        if(record.crackedPillarBlocks != null && record.pillarBlocks != null) {
                            createStoneSetRecipes(record.crackedPillarBlocks);
                            if(!isVanillaBlock(record.crackedPillarBlocks.base())) {
                                oreSmelting(List.of(record.pillarBlocks.base()), RecipeCategory.BUILDING_BLOCKS,
                                        record.crackedPillarBlocks.base(), 0.1f, 200, "cracked_bricks");
                            }
                        }
                        if(record.crackedPolishedBlocks != null && record.polishedBlocks != null) {
                            createStoneSetRecipes(record.crackedPolishedBlocks);
                            if(!isVanillaBlock(record.crackedPolishedBlocks.base())) {
                                oreSmelting(List.of(record.polishedBlocks.base()), RecipeCategory.BUILDING_BLOCKS,
                                        record.crackedPolishedBlocks.base(), 0.1f, 200, "cracked_bricks");
                            }
                        }
                        if(record.crackedTileBlocks != null && record.tileBlocks != null) {
                            createStoneSetRecipes(record.crackedTileBlocks);
                            if(!isVanillaBlock(record.crackedTileBlocks.base())) {
                                oreSmelting(List.of(record.tileBlocks.base()), RecipeCategory.BUILDING_BLOCKS,
                                        record.crackedTileBlocks.base(), 0.1f, 200, "cracked_bricks");
                            }
                        }
                        if(record.crackedSmoothBlocks != null && record.smoothBlocks != null) {
                            createStoneSetRecipes(record.crackedSmoothBlocks);
                            if(!isVanillaBlock(record.crackedSmoothBlocks.base())) {
                                oreSmelting(List.of(record.smoothBlocks.base()), RecipeCategory.BUILDING_BLOCKS,
                                        record.crackedSmoothBlocks.base(), 0.1f, 200, "cracked_bricks");
                            }
                        }
                    }

                    if(record.cobblestoneBlocks != null && record.baseBlocks != null) {
                        if(!isVanillaBlock(record.cobblestoneBlocks.base())) {
                            stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, record.cobblestoneBlocks.base(), record.baseBlocks.base(), 1);
                        }
                        if(!isVanillaBlock(record.baseBlocks.base())) {
                            oreSmelting(List.of(record.cobblestoneBlocks.base()), RecipeCategory.BUILDING_BLOCKS,
                                    record.baseBlocks.base(), 0.1f, 200, "blocks");
                        } else if(!isVanillaBlock(record.cobblestoneBlocks.base())) {
                            createCobbledBaseSmeltingRecipe(recipeOutput,
                                    record.cobblestoneBlocks.base(), record.baseBlocks.base());
                        }


                        if(record.brickworkBlocks != null) {
                            createBrickworkBlockRecipe(recipeOutput, record.cobblestoneBlocks.base(), GenericBlockSets.STUCCO.blockSet.base(), record.brickworkBlocks.base());
                        }
                    }

                    if (record.smoothBlocks != null && record.tileBlocks != null
                            && !isVanillaBlock(record.tileBlocks.base())) {
                        createBrickRecipe(recipeOutput, record.smoothBlocks.base().asItem(),
                                record.tileBlocks.base(), 4);
                    }

                    if (record.baseBlocks != null) {
                        if(record.brickBlocks != null && !isVanillaBlock(record.brickBlocks.base())) {
                            createBrickRecipe(recipeOutput, record.baseBlocks.base().asItem(), record.brickBlocks.base(), 4);
                            stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, record.brickBlocks.base(), record.baseBlocks.base(), 1);
                        }
                        if(record.pillarBlocks != null && !isVanillaBlock(record.pillarBlocks.base())) {
                            createPillarRecipe(recipeOutput, record.baseBlocks.base(), record.pillarBlocks.base(), 3);
                            stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, record.pillarBlocks.base(), record.baseBlocks.base(), 1);
                        }
                        if(record.polishedBlocks != null && !isVanillaBlock(record.polishedBlocks.base())) {
                            createBrickRecipe(recipeOutput, record.baseBlocks.base().asItem(), record.polishedBlocks.base(), 4);
                            stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, record.polishedBlocks.base(), record.baseBlocks.base(), 1);
                        }
                        if(record.smoothBlocks != null && !isVanillaBlock(record.smoothBlocks.base())) {
                            createSmeltingRecipe(recipeOutput, record.baseBlocks.base().asItem(), record.smoothBlocks.base().asItem());
                        }
                        if(record.chiseledBlocks != null && !isVanillaBlock(record.chiseledBlocks.base())) {
                            createChiseledRecipe(recipeOutput, record.baseBlocks.base(), record.chiseledBlocks.base(), 2);
                            stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, record.chiseledBlocks.base(), record.baseBlocks.base(), 1);
                        }
                        if(record.chiseledBricksBlocks != null && record.brickBlocks != null
                                && !isVanillaBlock(record.chiseledBricksBlocks.base())) {
                            createChiseledRecipe(recipeOutput, record.brickBlocks.base(), record.chiseledBricksBlocks.base(), 2);
                            stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, record.chiseledBricksBlocks.base(), record.brickBlocks.base(), 1);
                        }
                        if(record.chiseledPolishedBlocks != null && record.polishedBlocks != null
                                && !isVanillaBlock(record.chiseledPolishedBlocks.base())) {
                            createChiseledRecipe(recipeOutput, record.polishedBlocks.base(), record.chiseledPolishedBlocks.base(), 2);
                            stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, record.chiseledPolishedBlocks.base(), record.polishedBlocks.base(), 1);
                        }
                        if(record.chiseledSmoothBlocks != null && record.smoothBlocks != null
                                && !isVanillaBlock(record.chiseledSmoothBlocks.base())) {
                            createChiseledRecipe(recipeOutput, record.smoothBlocks.base(), record.chiseledSmoothBlocks.base(), 2);
                            stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, record.chiseledSmoothBlocks.base(), record.smoothBlocks.base(), 1);
                        }
                        if(record.chiseledTilesBlocks != null && record.tileBlocks != null
                                && !isVanillaBlock(record.chiseledTilesBlocks.base())) {
                            createChiseledRecipe(recipeOutput, record.tileBlocks.base(), record.chiseledTilesBlocks.base(), 2);
                            stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, record.chiseledTilesBlocks.base(), record.tileBlocks.base(), 1);
                        }
                        if(record.oldBlocks != null && !isVanillaBlock(record.oldBlocks.base())) {
                            createCenterSurroundRecipe(recipeOutput, record.baseBlocks.base().asItem(), ResourceItemsME.ASH, record.oldBlocks.base().asItem(), 8);
                        }

                        if(!isVanillaBlock(record.baseBlocks.trapdoor())) {
                            createFilledRecipe(recipeOutput, record.baseBlocks.base().asItem(), record.baseBlocks.trapdoor(), 3);
                            stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, record.baseBlocks.trapdoor(), record.baseBlocks.base());
                        }
                        if(!isVanillaBlock(record.baseBlocks.pressurePlate())) {
                            createPressurePlateRecipe(recipeOutput, record.baseBlocks.base(), record.baseBlocks.pressurePlate());
                        }
                        if(!isVanillaBlock(record.baseBlocks.rocks())) {
                            stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, record.baseBlocks.rocks(), record.baseBlocks.base(), 4);
                        }
                        if(!isVanillaBlock(record.baseBlocks.button())) {
                            createButtonRecipe(recipeOutput, record.baseBlocks.base(), record.baseBlocks.button());
                        }

                        createStoneStoolRecipe(recipeOutput, record.baseBlocks.base().asItem(), record.baseBlocks.stool());
                        createStoneTableRecipe(recipeOutput, record.baseBlocks.base().asItem(), record.baseBlocks.table());
                        createStoneChairRecipe(recipeOutput, record.baseBlocks.base().asItem(), record.baseBlocks.chair());
                    }

                    createStoneSetRecipes(record.baseBlocks);
                    createStoneSetRecipes(record.cobblestoneBlocks);
                    createStoneSetRecipes(record.brickBlocks);
                    createStoneSetRecipes(record.tileBlocks);
                    createStoneSetRecipes(record.smoothBlocks);
                    createStoneSetRecipes(record.polishedBlocks);
                    createStoneSetRecipes(record.brickworkBlocks);
                    createStoneSetRecipes(record.pillarBlocks);
                    createStoneSetRecipes(record.oldBlocks);
                    createStoneSetRecipes(record.chiseledBlocks);
                    createStoneSetRecipes(record.chiseledBricksBlocks);
                    createStoneSetRecipes(record.chiseledTilesBlocks);
                    createStoneSetRecipes(record.chiseledPolishedBlocks);
                    createStoneSetRecipes(record.chiseledSmoothBlocks);

                    if(record.carvedWindows != null && record.baseBlocks != null) {
                        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, record.carvedWindows.block(), 4)
                                .pattern("EEE")
                                .pattern("EGE")
                                .pattern("EEE")
                                .define('E', record.baseBlocks.base())
                                .define('G', Items.GLASS)
                                .unlockedBy(getHasName(record.baseBlocks.base()),
                                        has(record.baseBlocks.base()))
                                .save(recipeOutput);
                        createPaneRecipe(recipeOutput, record.carvedWindows.block().asItem(),
                                record.carvedWindows.verticalSlab(), 12);
                    }
                }
                //endregion

                //region WOOD RECIPES
                for (WoodBlockSetBuilder record : WoodBlockSets.woodSetsList) {
                    if(record.logBlocks != null) {
                        if(!isVanillaBlock(record.logBlocks.wood())) {
                            createBrickRecipe(recipeOutput, record.logBlocks.log().asItem(), record.logBlocks.wood(), 3);
                        }
                        if(!isVanillaBlock(record.logBlocks.wall())) {
                            wall(RecipeCategory.BUILDING_BLOCKS, record.logBlocks.wall(), record.logBlocks.wood());
                        }
                        if(!isVanillaBlock(record.logBlocks.fence())) {
                            createFenceRecipe(recipeOutput, record.logBlocks.wood().asItem(), record.logBlocks.fence());
                        }
                        if(!isVanillaBlock(record.logBlocks.slab())) {
                            slab(RecipeCategory.BUILDING_BLOCKS, record.logBlocks.slab(), record.logBlocks.wood());
                        }
                        if(!isVanillaBlock(record.logBlocks.stairs())) {
                            createStairsRecipe(recipeOutput, record.logBlocks.wood(), record.logBlocks.stairs());
                        }

                        if(!isVanillaBlock(record.planksBlocks.base())) {
                            ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, record.planksBlocks.base(), 4)
                                    .requires(record.logBlocks.log())
                                    .unlockedBy(getHasName(record.logBlocks.log()),
                                            has(record.planksBlocks.base()))
                                    .save(recipeOutput);

                            ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, record.planksBlocks.base(), 4)
                                    .requires(record.logBlocks.wood())
                                    .unlockedBy(getHasName(record.logBlocks.wood()),
                                            has(record.planksBlocks.base()))
                                    .save(recipeOutput, String.valueOf(ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, BuiltInRegistries.BLOCK.getKey(record.planksBlocks.base()).getPath() + "_from_wood")));
                        }

                        if(!isVanillaBlock(record.logBlocks.verticalSlab())) {
                            createVerticalSlabsRecipe(recipeOutput, record.logBlocks.slab(), record.logBlocks.verticalSlab());
                        }
                        if(!isVanillaBlock(record.logBlocks.slab())) {
                            createSlabsFromVerticalRecipe(recipeOutput, record.logBlocks.verticalSlab(), record.logBlocks.slab());
                        }
                    } else if(record.mushroomStemBlocks != null) {
                        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, record.planksBlocks.base(), 4)
                                .requires(record.mushroomStemBlocks.stem())
                                .unlockedBy(getHasName(record.mushroomStemBlocks.stem()),
                                        has(record.planksBlocks.base()))
                                .save(recipeOutput);

                        wall(RecipeCategory.BUILDING_BLOCKS, record.mushroomStemBlocks.wall(), record.mushroomStemBlocks.stem());
                        createFenceRecipe(recipeOutput, record.mushroomStemBlocks.stem().asItem(), record.mushroomStemBlocks.fence());
                        slab(RecipeCategory.BUILDING_BLOCKS, record.mushroomStemBlocks.slab(), record.mushroomStemBlocks.stem());
                        createVerticalSlabsRecipe(recipeOutput, record.mushroomStemBlocks.slab(), record.mushroomStemBlocks.verticalSlab());
                        createSlabsFromVerticalRecipe(recipeOutput, record.mushroomStemBlocks.verticalSlab(), record.mushroomStemBlocks.slab());
                        createStairsRecipe(recipeOutput, record.mushroomStemBlocks.stem(), record.mushroomStemBlocks.stairs());
                    }

                    if(record.strippedLogBlocks != null) {
                        if(!isVanillaBlock(record.strippedLogBlocks.wood())) {
                            createBrickRecipe(recipeOutput, record.strippedLogBlocks.log().asItem(), record.strippedLogBlocks.wood(),  3);
                        }
                        if(!isVanillaBlock(record.strippedLogBlocks.wall())) {
                            wall(RecipeCategory.BUILDING_BLOCKS, record.strippedLogBlocks.wall(), record.strippedLogBlocks.wood());
                        }
                        if(!isVanillaBlock(record.strippedLogBlocks.fence())) {
                            createFenceRecipe(recipeOutput, record.strippedLogBlocks.wood().asItem(), record.strippedLogBlocks.fence());
                        }
                        if(!isVanillaBlock(record.strippedLogBlocks.slab())) {
                            slab(RecipeCategory.BUILDING_BLOCKS, record.strippedLogBlocks.slab(), record.strippedLogBlocks.wood());
                        }
                        if(!isVanillaBlock(record.strippedLogBlocks.verticalSlab())) {
                            createVerticalSlabsRecipe(recipeOutput, record.strippedLogBlocks.slab(), record.strippedLogBlocks.verticalSlab());
                        }
                        if(!isVanillaBlock(record.strippedLogBlocks.slab())) {
                            createSlabsFromVerticalRecipe(recipeOutput, record.strippedLogBlocks.verticalSlab(), record.strippedLogBlocks.slab());
                        }
                        if(!isVanillaBlock(record.strippedLogBlocks.stairs())) {
                            createStairsRecipe(recipeOutput, record.strippedLogBlocks.wood(), record.strippedLogBlocks.stairs());
                        }

                        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, record.planksBlocks.base(), 4)
                                .requires(record.strippedLogBlocks.log())
                                .unlockedBy(getHasName(record.strippedLogBlocks.log()),
                                        has(record.planksBlocks.base()))
                                .save(recipeOutput, String.valueOf(ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, BuiltInRegistries.BLOCK.getKey(record.planksBlocks.base()).getPath() + "_from_stripped_log")));

                        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, record.planksBlocks.base(), 4)
                                .requires(record.strippedLogBlocks.wood())
                                .unlockedBy(getHasName(record.strippedLogBlocks.wood()),
                                        has(record.planksBlocks.base()))
                                .save(recipeOutput, String.valueOf(ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, BuiltInRegistries.BLOCK.getKey(record.planksBlocks.base()).getPath() + "_from_stripped_wood")));
                    }

                    if(!isVanillaBlock(record.planksBlocks.fence())) {
                        createFenceRecipe(recipeOutput, record.planksBlocks.base().asItem(), record.planksBlocks.fence());
                    }
                    if(!isVanillaBlock(record.planksBlocks.slab())) {
                        slab(RecipeCategory.BUILDING_BLOCKS, record.planksBlocks.slab(), record.planksBlocks.base());
                    }

                    if(record.shinglesBlocks != null) {
                        createShinglesRecipe(recipeOutput, record.planksBlocks.base(), record.shinglesBlocks.base());
                        createRegularSetRecipes(record.shinglesBlocks);
                    }
                    if(record.roofingBlocks != null) {
                        createRoofingRecipe(recipeOutput, record.planksBlocks.slab(), record.roofingBlocks.base());
                        createRegularSetRecipes(record.roofingBlocks);
                    }

                    if(!isVanillaBlock(record.planksBlocks.verticalSlab())) {
                        createVerticalSlabsRecipe(recipeOutput, record.planksBlocks.slab(), record.planksBlocks.verticalSlab());
                    }
                    createSlabsFromVerticalRecipe(recipeOutput, record.planksBlocks.verticalSlab(), record.planksBlocks.slab());

                    if(!isVanillaBlock(record.planksBlocks.stairs())) {
                        createStairsRecipe(recipeOutput, record.planksBlocks.base(), record.planksBlocks.stairs());
                    }

                    if(record.redstoneBlocks != null) {
                        if(!isVanillaBlock(record.redstoneBlocks.door())) {
                            createDoorRecipe(recipeOutput, record.planksBlocks.base(), record.redstoneBlocks.door());
                        }
                        if(!isVanillaBlock(record.redstoneBlocks.trapdoor())) {
                            createTrapdoorRecipe(recipeOutput, record.planksBlocks.base(), record.redstoneBlocks.trapdoor());
                        }
                        if(!isVanillaBlock(record.redstoneBlocks.button())) {
                            createButtonRecipe(recipeOutput, record.planksBlocks.base(), record.redstoneBlocks.button());
                        }
                        if(!isVanillaBlock(record.redstoneBlocks.pressurePlate())) {
                            createPressurePlateRecipe(recipeOutput, record.planksBlocks.base(), record.redstoneBlocks.pressurePlate());
                        }
                    }

                    if(record.furnitureBlocks != null) {
                        createWoodStoolRecipe(recipeOutput, record.planksBlocks.base().asItem(), record.furnitureBlocks.stool());
                        createWoodBenchRecipe(recipeOutput, record.planksBlocks.base().asItem(), record.furnitureBlocks.bench());
                        createWoodTableRecipe(recipeOutput, record.planksBlocks.base().asItem(), record.furnitureBlocks.table());
                        createWoodChairRecipe(recipeOutput, record.planksBlocks.base().asItem(), record.furnitureBlocks.chair());
                        createWoodLadderRecipe(recipeOutput, record.planksBlocks.base().asItem(), record.furnitureBlocks.ladder());
                    }

                    if(!isVanillaBlock(record.planksBlocks.gate())) {
                        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, record.planksBlocks.gate(), 1)
                                .pattern("sls")
                                .pattern("sls")
                                .define('l', record.planksBlocks.base())
                                .define('s', Items.STICK)
                                .unlockedBy(getHasName(record.planksBlocks.base()),
                                        has(record.planksBlocks.base()))
                                .unlockedBy(getHasName(Items.STICK),
                                        has(Items.STICK))
                                .save(recipeOutput);
                    }

                }
                //endregion

                for(GenericBlockSetBuilder set : GenericBlockSets.genericSetsList) {
                    if(!set.setName.contains("wood") && !set.setName.contains("thatch") && !set.setName.contains("reed")) {
                        createStoneSetRecipes(set.blockSet);
                    } else if (set.setName.contains("thatch") || set.setName.contains("reed")) {
                        createRegularSetRecipes(set.blockSet);
                    }
                }
                for (SimpleBlockSetBuilder set : GenericBlockSets.simpleSetsList) {
                    createGenericRecipes(set);
                }

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
                    createVerticalSlabsRecipe(recipeOutput, verticalSlab.slab(), verticalSlab.verticalSlab());
                    createSlabsFromVerticalRecipe(recipeOutput, verticalSlab.verticalSlab(), verticalSlab.slab());
                }

                for (SimpleVerticalSlabModel.VerticalSlab verticalSlab : SimpleVerticalSlabModel.vanillaWoodVerticalSlabs) {
                    createVerticalSlabsRecipe(recipeOutput, verticalSlab.slab(), verticalSlab.verticalSlab());
                    createSlabsFromVerticalRecipe(recipeOutput, verticalSlab.verticalSlab(), verticalSlab.slab());
                }

                for (SimpleVerticalSlabModel.VerticalSlab verticalSlab : SimpleVerticalSlabModel.vanillaStrippedVerticalSlabs) {
                    createVerticalSlabsRecipe(recipeOutput, verticalSlab.slab(), verticalSlab.verticalSlab());
                    createSlabsFromVerticalRecipe(recipeOutput, verticalSlab.verticalSlab(), verticalSlab.slab());
                }

                for (SimplePillarModel.StonePillar pillar : SimplePillarModel.stonePillars) {
                    if (pillar.toString().contains("mossy_")) {
                        createMossyRecipe(recipeOutput, pillar.origin(), pillar.base());
                    } else if (pillar.toString().contains("cracked_")) {
                        createSmeltingRecipe(recipeOutput, pillar.origin().asItem(), pillar.base().asItem());
                    } else {
                        createPillarRecipe(recipeOutput, pillar.origin(), pillar.base(), 3);
                        stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, pillar.base().asItem(), pillar.origin());
                    }
                }

                for (SimpleBlockModel.ChiseledPolishedBlock block : SimpleBlockModel.chiseledPolishedBlocksTopBottom) {
                    createChiseledRecipe(recipeOutput, block.origin(), block.base(), 1);
                }
                for (SimpleBlockModel.ChiseledBlock block : SimpleBlockModel.chiseledMainBlockTopBottom) {
                    createChiseledRecipe(recipeOutput, block.origin(), block.base(), 1);
                }
                for (SimpleBlockModel.ChiseledBlock block : SimpleBlockModel.chiseledBlocksTopBottom) {
                    createChiseledRecipe(recipeOutput, block.origin(), block.base(), 1);
                }
                for (SimpleBlockModel.ChiseledPolishedBlock block : SimpleBlockModel.chiseledPolishedBlocks) {
                    createCutPolishedRecipe(recipeOutput, block.origin(), block.base(), 1);
                }
                for (SimpleBlockModel.ChiseledPolishedBlock block : SimpleBlockModel.chiseledTilesBlocksTopBottom) {
                    createCutPolishedRecipe(recipeOutput, block.origin(), block.base(), 1);
                }
                for (SimpleBlockModel.ChiseledPolishedBlock block : SimpleBlockModel.chiseledSmoothBlocksTopBottom) {
                    createCutPolishedRecipe(recipeOutput, block.origin(), block.base(), 1);
                }

                for (SimpleSlabModel.Slab slab : SimpleSlabModel.vanillaSlabs) {
                    slab(RecipeCategory.BUILDING_BLOCKS, slab.slab(), slab.origin());
                }

                for (SimpleSlabModel.Slab slab : SimpleSlabModel.vanillaWoodSlabs) {
                    slab(RecipeCategory.BUILDING_BLOCKS, slab.slab(), slab.origin());
                }

                for (SimpleSlabModel.Slab slab : SimpleSlabModel.vanillaStrippedSlab) {
                    slab(RecipeCategory.BUILDING_BLOCKS, slab.slab(), slab.origin());
                }

                for (SimpleStairModel.Stair stair : SimpleStairModel.vanillaStairs) {
                    createStairsRecipe(recipeOutput, stair.origin(), stair.stairs());
                }

                for (SimpleStairModel.Stair stair : SimpleStairModel.vanillaWoodStairs) {
                    createStairsRecipe(recipeOutput, stair.origin(), stair.stairs());
                }

                for (SimpleStairModel.Stair stair : SimpleStairModel.vanillaStrippedStairs) {
                    createStairsRecipe(recipeOutput, stair.origin(), stair.stairs());
                }

                for (SimpleWallModel.Wall wall : SimpleWallModel.vanillaWalls) {
                    wall(RecipeCategory.BUILDING_BLOCKS, wall.wall(), wall.block());
                }

                for (SimpleWallModel.Wall wall : SimpleWallModel.vanillaStrippedWalls) {
                    wall(RecipeCategory.BUILDING_BLOCKS, wall.wall(), wall.block());
                }

                for (SimpleWallModel.Wall wall : SimpleWallModel.vanillaWoodWalls) {
                    wall(RecipeCategory.BUILDING_BLOCKS, wall.wall(), wall.block());
                }

                for (SimpleFenceModel.Fence fence : SimpleFenceModel.vanillaStrippedFences) {
                    createFenceRecipe(recipeOutput, fence.block().asItem(), fence.fence());
                }

                for (SimpleFenceModel.Fence fence : SimpleFenceModel.vanillaWoodFences) {
                    createFenceRecipe(recipeOutput, fence.block().asItem(), fence.fence());
                }

                for (SimplePaneModel.Pane pane : SimplePaneModel.panes) {
                    createPaneRecipe(recipeOutput, pane.glass().asItem(), pane.pane(), 16);
                }

                for (SimpleWoodStoolModel.VanillaStool stool : SimpleWoodStoolModel.vanillaStools) {
                    createWoodStoolRecipe(recipeOutput, stool.planks().asItem(), stool.base());
                }

                for (SimpleWoodBenchModel.VanillaBench bench : SimpleWoodBenchModel.vanillaBenchs) {
                    createWoodBenchRecipe(recipeOutput, bench.planks().asItem(), bench.base());
                }

                for (SimpleWoodTableModel.VanillaTable table : SimpleWoodTableModel.vanillaTables) {
                    createWoodTableRecipe(recipeOutput, table.planks().asItem(), table.base());
                }

                for (SimpleWoodChairModel.VanillaChair chair : SimpleWoodChairModel.vanillaChairs) {
                    createWoodChairRecipe(recipeOutput, chair.planks().asItem(), chair.base());
                }

                for (SimpleLadderModel.Ladder ladder : SimpleLadderModel.vanillaLadders) {
                    createWoodLadderRecipe(recipeOutput, ladder.block().asItem(), ladder.ladder());
                }

                for (SimpleRocksModel.Rocks rock : SimpleRocksModel.vanillaRocks) {
                    stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, rock.rocks(), rock.block(), 4);
                }

                //endregion

                //region MANUAL BLOCK RECIPES
                createCenterSurroundRecipe(recipeOutput, ModDecorativeBlocks.LEAD_GLASS.asItem(), Items.BLACK_DYE, ModDecorativeBlocks.BLACK_STAINED_LEAD_GLASS.asItem(), 8);
                createCenterSurroundRecipe(recipeOutput, ModDecorativeBlocks.LEAD_GLASS.asItem(), Items.BLUE_DYE, ModDecorativeBlocks.BLUE_STAINED_LEAD_GLASS.asItem(), 8);
                createCenterSurroundRecipe(recipeOutput, ModDecorativeBlocks.LEAD_GLASS.asItem(), Items.BROWN_DYE, ModDecorativeBlocks.BROWN_STAINED_LEAD_GLASS.asItem(), 8);
                createCenterSurroundRecipe(recipeOutput, ModDecorativeBlocks.LEAD_GLASS.asItem(), Items.CYAN_DYE, ModDecorativeBlocks.CYAN_STAINED_LEAD_GLASS.asItem(), 8);
                createCenterSurroundRecipe(recipeOutput, ModDecorativeBlocks.LEAD_GLASS.asItem(), Items.GRAY_DYE, ModDecorativeBlocks.GRAY_STAINED_LEAD_GLASS.asItem(), 8);
                createCenterSurroundRecipe(recipeOutput, ModDecorativeBlocks.LEAD_GLASS.asItem(), Items.GREEN_DYE, ModDecorativeBlocks.GREEN_STAINED_LEAD_GLASS.asItem(), 8);
                createCenterSurroundRecipe(recipeOutput, ModDecorativeBlocks.LEAD_GLASS.asItem(), Items.LIGHT_BLUE_DYE, ModDecorativeBlocks.LIGHT_BLUE_STAINED_LEAD_GLASS.asItem(), 8);
                createCenterSurroundRecipe(recipeOutput, ModDecorativeBlocks.LEAD_GLASS.asItem(), Items.LIGHT_GRAY_DYE, ModDecorativeBlocks.LIGHT_GRAY_STAINED_LEAD_GLASS.asItem(), 8);
                createCenterSurroundRecipe(recipeOutput, ModDecorativeBlocks.LEAD_GLASS.asItem(), Items.LIME_DYE, ModDecorativeBlocks.LIME_STAINED_LEAD_GLASS.asItem(), 8);
                createCenterSurroundRecipe(recipeOutput, ModDecorativeBlocks.LEAD_GLASS.asItem(), Items.MAGENTA_DYE, ModDecorativeBlocks.MAGENTA_STAINED_LEAD_GLASS.asItem(), 8);
                createCenterSurroundRecipe(recipeOutput, ModDecorativeBlocks.LEAD_GLASS.asItem(), Items.ORANGE_DYE, ModDecorativeBlocks.ORANGE_STAINED_LEAD_GLASS.asItem(), 8);
                createCenterSurroundRecipe(recipeOutput, ModDecorativeBlocks.LEAD_GLASS.asItem(), Items.PINK_DYE, ModDecorativeBlocks.PINK_STAINED_LEAD_GLASS.asItem(), 8);
                createCenterSurroundRecipe(recipeOutput, ModDecorativeBlocks.LEAD_GLASS.asItem(), Items.PURPLE_DYE, ModDecorativeBlocks.PURPLE_STAINED_LEAD_GLASS.asItem(), 8);
                createCenterSurroundRecipe(recipeOutput, ModDecorativeBlocks.LEAD_GLASS.asItem(), Items.RED_DYE, ModDecorativeBlocks.RED_STAINED_LEAD_GLASS.asItem(), 8);
                createCenterSurroundRecipe(recipeOutput, ModDecorativeBlocks.LEAD_GLASS.asItem(), Items.WHITE_DYE, ModDecorativeBlocks.WHITE_STAINED_LEAD_GLASS.asItem(), 8);
                createCenterSurroundRecipe(recipeOutput, ModDecorativeBlocks.LEAD_GLASS.asItem(), Items.YELLOW_DYE, ModDecorativeBlocks.YELLOW_STAINED_LEAD_GLASS.asItem(), 8);

                createLayerRecipe(recipeOutput, Blocks.GRAVEL.asItem(), ModBlocks.GRAVEL_LAYER);
                createLayerRecipe(recipeOutput, Blocks.SAND.asItem(), ModBlocks.SAND_LAYER);
                createLayerRecipe(recipeOutput, ModBlocks.BLACK_SAND.asItem(), ModBlocks.BLACK_SAND_LAYER);
                createLayerRecipe(recipeOutput, ModBlocks.WHITE_SAND.asItem(), ModBlocks.WHITE_SAND_LAYER);
                createLayerRecipe(recipeOutput, ModBlocks.ASHEN_SAND.asItem(), ModBlocks.ASHEN_SAND_LAYER);
                createLayerRecipe(recipeOutput, ModBlocks.ASHEN_GRAVEL.asItem(), ModBlocks.ASHEN_GRAVEL_LAYER);
                createLayerRecipe(recipeOutput, ModBlocks.SKELETAL_PILE.asItem(), ModBlocks.SKELETAL_PILE_LAYER);
                createLayerRecipe(recipeOutput, ModBlocks.WASTE_PILE.asItem(), ModBlocks.WASTE_PILE_LAYER);


                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, StoneBlockSets.TRAVERTINE_SET.baseBlocks.base(), 4)
                        .pattern("CS")
                        .pattern("SC")
                        .define('C', Blocks.CALCITE)
                        .define('S', Blocks.SANDSTONE)
                        .unlockedBy(getHasName(Blocks.CALCITE),
                                has(Blocks.CALCITE))
                        .save(recipeOutput);

                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, GenericBlockSets.REED_THATCH.blockSet.base(), 1)
                        .pattern("RR")
                        .pattern("RR")
                        .define('R', ResourceItemsME.REEDS)
                        .unlockedBy(getHasName(ResourceItemsME.REEDS),
                                has(ResourceItemsME.REEDS))
                        .save(recipeOutput);

                createStairsRecipe(recipeOutput, ModBlocks.GRASSY_DIRT, ModBlocks.GRASSY_DIRT_STAIRS);
                slab(RecipeCategory.BUILDING_BLOCKS, ModBlocks.GRASSY_DIRT_SLAB, ModBlocks.GRASSY_DIRT);

                createStairsRecipe(recipeOutput, ModBlocks.PEBBLED_GRASS, ModBlocks.PEBBLED_GRASS_STAIRS);
                slab(RecipeCategory.BUILDING_BLOCKS, ModBlocks.PEBBLED_GRASS_SLAB, ModBlocks.PEBBLED_GRASS);

                createStairsRecipe(recipeOutput, ModBlocks.TURF, ModBlocks.TURF_STAIRS);
                slab(RecipeCategory.BUILDING_BLOCKS, ModBlocks.TURF_SLAB, ModBlocks.TURF);
                createVerticalSlabsRecipe(recipeOutput, ModBlocks.TURF, ModBlocks.TURF_VERTICAL_SLAB);
                createSlabsFromVerticalRecipe(recipeOutput, ModBlocks.TURF_VERTICAL_SLAB, ModBlocks.TURF_SLAB);

                createStairsRecipe(recipeOutput, ModBlocks.MIRE, ModBlocks.MIRE_STAIRS);
                slab(RecipeCategory.BUILDING_BLOCKS, ModBlocks.MIRE_SLAB, ModBlocks.MIRE);

                createStairsRecipe(recipeOutput, ModBlocks.CHALKSOIL, ModBlocks.CHALKSOIL_STAIRS);
                slab(RecipeCategory.BUILDING_BLOCKS, ModBlocks.CHALKSOIL_SLAB, ModBlocks.CHALKSOIL);
                createStairsRecipe(recipeOutput, ModBlocks.GRASSY_CHALKSOIL, ModBlocks.GRASSY_CHALKSOIL_STAIRS);
                slab(RecipeCategory.BUILDING_BLOCKS, ModBlocks.GRASSY_CHALKSOIL_SLAB, ModBlocks.GRASSY_CHALKSOIL);
                createStairsRecipe(recipeOutput, ModBlocks.COARSE_CHALKSOIL, ModBlocks.COARSE_CHALKSOIL_STAIRS);
                slab(RecipeCategory.BUILDING_BLOCKS, ModBlocks.COARSE_CHALKSOIL_SLAB, ModBlocks.COARSE_CHALKSOIL);

                createStairsRecipe(recipeOutput, ModBlocks.LOAM, ModBlocks.LOAM_STAIRS);
                slab(RecipeCategory.BUILDING_BLOCKS, ModBlocks.LOAM_SLAB, ModBlocks.LOAM);
                createStairsRecipe(recipeOutput, ModBlocks.GRASSY_LOAM, ModBlocks.GRASSY_LOAM_STAIRS);
                slab(RecipeCategory.BUILDING_BLOCKS, ModBlocks.GRASSY_LOAM_SLAB, ModBlocks.GRASSY_LOAM);
                createStairsRecipe(recipeOutput, ModBlocks.COARSE_LOAM, ModBlocks.COARSE_LOAM_STAIRS);
                slab(RecipeCategory.BUILDING_BLOCKS, ModBlocks.COARSE_LOAM_SLAB, ModBlocks.COARSE_LOAM);

                createStairsRecipe(recipeOutput, ModBlocks.PEAT, ModBlocks.PEAT_STAIRS);
                slab(RecipeCategory.BUILDING_BLOCKS, ModBlocks.PEAT_SLAB, ModBlocks.PEAT);
                createStairsRecipe(recipeOutput, ModBlocks.GRASSY_PEAT, ModBlocks.GRASSY_PEAT_STAIRS);
                slab(RecipeCategory.BUILDING_BLOCKS, ModBlocks.GRASSY_PEAT_SLAB, ModBlocks.GRASSY_PEAT);
                createStairsRecipe(recipeOutput, ModBlocks.COARSE_PEAT, ModBlocks.COARSE_PEAT_STAIRS);
                slab(RecipeCategory.BUILDING_BLOCKS, ModBlocks.COARSE_PEAT_SLAB, ModBlocks.COARSE_PEAT);

                createStairsRecipe(recipeOutput, ModBlocks.SILT, ModBlocks.SILT_STAIRS);
                slab(RecipeCategory.BUILDING_BLOCKS, ModBlocks.SILT_SLAB, ModBlocks.SILT);
                createStairsRecipe(recipeOutput, ModBlocks.GRASSY_SILT, ModBlocks.GRASSY_SILT_STAIRS);
                slab(RecipeCategory.BUILDING_BLOCKS, ModBlocks.GRASSY_SILT_SLAB, ModBlocks.GRASSY_SILT);
                createStairsRecipe(recipeOutput, ModBlocks.COARSE_SILT, ModBlocks.COARSE_SILT_STAIRS);
                slab(RecipeCategory.BUILDING_BLOCKS, ModBlocks.COARSE_SILT_SLAB, ModBlocks.COARSE_SILT);

                createStairsRecipe(recipeOutput, ModBlocks.DRY_DIRT, ModBlocks.DRY_DIRT_STAIRS);
                slab(RecipeCategory.BUILDING_BLOCKS, ModBlocks.DRY_DIRT_SLAB, ModBlocks.DRY_DIRT);

                createStairsRecipe(recipeOutput, ModBlocks.FOUL_DIRT, ModBlocks.FOUL_DIRT_STAIRS);
                slab(RecipeCategory.BUILDING_BLOCKS, ModBlocks.FOUL_DIRT_SLAB, ModBlocks.FOUL_DIRT);

                createStairsRecipe(recipeOutput, ModBlocks.DIRTY_ROOTS, ModBlocks.DIRTY_ROOTS_STAIRS);
                slab(RecipeCategory.BUILDING_BLOCKS, ModBlocks.DIRTY_ROOTS_SLAB, ModBlocks.DIRTY_ROOTS);

                createStairsRecipe(recipeOutput, ModBlocks.ASHEN_DIRT, ModBlocks.ASHEN_DIRT_STAIRS);
                slab(RecipeCategory.BUILDING_BLOCKS, ModBlocks.ASHEN_DIRT_SLAB, ModBlocks.ASHEN_DIRT);

                createStairsRecipe(recipeOutput, ModBlocks.COBBLY_ASHEN_DIRT, ModBlocks.COBBLY_ASHEN_DIRT_STAIRS);
                slab(RecipeCategory.BUILDING_BLOCKS, ModBlocks.COBBLY_ASHEN_DIRT_SLAB, ModBlocks.COBBLY_ASHEN_DIRT);

                createStairsRecipe(recipeOutput, ModBlocks.COBBLY_DIRT, ModBlocks.COBBLY_DIRT_STAIRS);
                slab(RecipeCategory.BUILDING_BLOCKS, ModBlocks.COBBLY_DIRT_SLAB, ModBlocks.COBBLY_DIRT);

                createStairsRecipe(recipeOutput, ModBlocks.SNOWY_DIRT, ModBlocks.SNOWY_DIRT_STAIRS);
                slab(RecipeCategory.BUILDING_BLOCKS, ModBlocks.SNOWY_DIRT_SLAB, ModBlocks.SNOWY_DIRT);

                createPaneRecipe(recipeOutput, Blocks.WHITE_WOOL.asItem(), ModBlocks.NET, 16);

                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.COPPER_BARS, 16)
                        .pattern("IBI")
                        .pattern("IBI")
                        .define('I', Items.COPPER_INGOT)
                        .define('B', Items.CUT_COPPER)
                        .unlockedBy(getHasName(Items.CUT_COPPER),
                                has(Items.CUT_COPPER))
                        .save(recipeOutput);

                createBrickRecipe(recipeOutput, ResourceItemsME.CITRINE_SHARD, ModBlocks.CITRINE_BLOCK, 1);
                createFilledRecipe(recipeOutput, Items.GLOWSTONE, ModBlocks.GLOWSTONE_BLOCK, 1);
                createBrickRecipe(recipeOutput, ResourceItemsME.QUARTZ_SHARD, ModBlocks.QUARTZ_BLOCK, 1);
                createBrickRecipe(recipeOutput, ResourceItemsME.RED_AGATE_SHARD, ModBlocks.RED_AGATE_BLOCK, 1);

                stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, Blocks.BRICKS, GenericBlockSets.OLD_BRICKS.blockSet.base());

                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.WHITE_DAUB_HOBBIT_WINDOW, 4)
                        .pattern("WBW")
                        .pattern("BGB")
                        .pattern("WBW")
                        .define('W', GenericBlockSets.WHITE_DAUB.blockSet.base())
                        .define('G', Items.GLASS)
                        .define('B', Items.BRICK)
                        .unlockedBy(getHasName(GenericBlockSets.WHITE_DAUB.blockSet.base()),
                                has(GenericBlockSets.WHITE_DAUB.blockSet.base()))
                        .save(recipeOutput);

                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.YELLOW_DAUB_HOBBIT_WINDOW, 4)
                        .pattern("WBW")
                        .pattern("BGB")
                        .pattern("WBW")
                        .define('W', GenericBlockSets.YELLOW_DAUB.blockSet.base())
                        .define('G', Items.GLASS)
                        .define('B', Items.BRICK)
                        .unlockedBy(getHasName(GenericBlockSets.YELLOW_DAUB.blockSet.base()),
                                has(GenericBlockSets.YELLOW_DAUB.blockSet.base()))
                        .save(recipeOutput);

                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.PLASTER_HOBBIT_WINDOW, 4)
                        .pattern("WBW")
                        .pattern("BGB")
                        .pattern("WBW")
                        .define('W', GenericBlockSets.PLASTER.blockSet.base())
                        .define('G', Items.GLASS)
                        .define('B', Items.BRICK)
                        .unlockedBy(getHasName(GenericBlockSets.PLASTER.blockSet.base()),
                                has(GenericBlockSets.PLASTER.blockSet.base()))
                        .save(recipeOutput);

                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.SIMPLE_OAK_WINDOW, 8)
                        .pattern("EEE")
                        .pattern("EGE")
                        .pattern("EEE")
                        .define('E', Blocks.OAK_LOG)
                        .define('G', ResourceItemsME.LEAD_NUGGET)
                        .unlockedBy(getHasName(Blocks.OAK_LOG),
                                has(Blocks.OAK_LOG))
                        .save(recipeOutput);

                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, StoneBlockSets.DRYSTONE_SET.carvedWindows.block(), 2)
                        .pattern("EEE")
                        .pattern("EGE")
                        .pattern("EEE")
                        .define('E', StoneBlockSets.DRYSTONE_SET.cobblestoneBlocks.base())
                        .define('G', Items.GLASS)
                        .unlockedBy(getHasName(StoneBlockSets.DRYSTONE_SET.cobblestoneBlocks.base()),
                                has(StoneBlockSets.DRYSTONE_SET.cobblestoneBlocks.base()))
                        .save(recipeOutput);
                createPaneRecipe(recipeOutput, StoneBlockSets.DRYSTONE_SET.carvedWindows.block().asItem(), StoneBlockSets.DRYSTONE_SET.carvedWindows.verticalSlab(), 12);

                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.LEAD_GLASS, 4)
                        .pattern("LGL")
                        .pattern("GLG")
                        .pattern("LGL")
                        .define('L', ResourceItemsME.LEAD_NUGGET)
                        .define('G', Items.GLASS)
                        .unlockedBy(getHasName(ResourceItemsME.ROD),
                                has(ResourceItemsME.ROD))
                        .save(recipeOutput);

                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.ROPE, 3)
                        .pattern("SS")
                        .pattern("SS")
                        .pattern("SS")
                        .define('S', Items.STRING)
                        .unlockedBy(getHasName(Items.STRING),
                                has(Items.STRING))
                        .save(recipeOutput);

                createBrickRecipe(recipeOutput, ResourceItemsME.ASH, ModBlocks.ASH_BLOCK, 1);
                createBrickRecipe(recipeOutput, ModBlocks.ASH_BLOCK.asItem(), Blocks.TUFF, 1);

                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, StoneBlockSets.ASHENSTONE_SET.baseBlocks.base(), 4)
                        .pattern("AS")
                        .pattern("SA")
                        .define('A', ModBlocks.ASH_BLOCK)
                        .define('S', Blocks.STONE)
                        .unlockedBy(getHasName(ModBlocks.ASH_BLOCK),
                                has(ModBlocks.ASH_BLOCK))
                        .save(recipeOutput);

                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, StoneBlockSets.GILDED_GREEN_TUFF_SET.baseBlocks.base(), 5)
                        .pattern("TNT")
                        .pattern("NTN")
                        .pattern("TNT")
                        .define('T', StoneBlockSets.GREEN_TUFF_SET.baseBlocks.base())
                        .define('N', Items.GOLD_NUGGET)
                        .unlockedBy(getHasName(StoneBlockSets.GREEN_TUFF_SET.baseBlocks.base()),
                                has(StoneBlockSets.GREEN_TUFF_SET.baseBlocks.base()))
                        .save(recipeOutput);

                createGildedStoneRecipe(
                        StoneBlockSets.GREEN_TUFF_SET.chiseledBricksBlocks.base(),
                        StoneBlockSets.GILDED_GREEN_TUFF_SET.chiseledBricksBlocks.base());
                createGildedStoneRecipe(
                        StoneBlockSets.GREEN_TUFF_SET.chiseledTilesBlocks.base(),
                        StoneBlockSets.GILDED_GREEN_TUFF_SET.chiseledTilesBlocks.base());
                createGildedStoneRecipe(
                        StoneBlockSets.GREEN_TUFF_SET.chiseledSmoothBlocks.base(),
                        StoneBlockSets.GILDED_GREEN_TUFF_SET.chiseledSmoothBlocks.base());
                createGildedStoneRecipe(
                        StoneBlockSets.GREEN_TUFF_SET.chiseledPolishedBlocks.base(),
                        StoneBlockSets.GILDED_GREEN_TUFF_SET.chiseledPolishedBlocks.base());

                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, StoneBlockSets.BURZUM_GABBRO_SET.chiseledBlocks.base(), 5)
                        .pattern("TNT")
                        .pattern("NTN")
                        .pattern("TNT")
                        .define('T', StoneBlockSets.GABBRO_SET.baseBlocks.base())
                        .define('N', ResourceItemsME.BURZUM_STEEL_NUGGET)
                        .unlockedBy(getHasName(StoneBlockSets.GABBRO_SET.baseBlocks.base()),
                                has(StoneBlockSets.GABBRO_SET.baseBlocks.base()))
                        .save(recipeOutput);

                createBrickRecipe(recipeOutput, StoneBlockSets.BURZUM_GABBRO_SET.chiseledBlocks.base().asItem(), StoneBlockSets.BURZUM_GABBRO_SET.chiseledBricksBlocks.base(), 4);

                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, StoneBlockSets.BURZUM_GABBRO_SET.chiseledSmoothBlocks.base(), 5)
                        .pattern("TNT")
                        .pattern("NTN")
                        .pattern("TNT")
                        .define('T', StoneBlockSets.GABBRO_SET.smoothBlocks.base())
                        .define('N', ResourceItemsME.BURZUM_STEEL_NUGGET)
                        .unlockedBy(getHasName(StoneBlockSets.GABBRO_SET.smoothBlocks.base()),
                                has(StoneBlockSets.GABBRO_SET.smoothBlocks.base()))
                        .save(recipeOutput);

                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, StoneBlockSets.BURZUM_GABBRO_SET.chiseledPolishedBlocks.base(), 5)
                        .pattern("TNT")
                        .pattern("NTN")
                        .pattern("TNT")
                        .define('T', StoneBlockSets.GABBRO_SET.polishedBlocks.base())
                        .define('N', ResourceItemsME.BURZUM_STEEL_NUGGET)
                        .unlockedBy(getHasName(StoneBlockSets.GABBRO_SET.polishedBlocks.base()),
                                has(StoneBlockSets.GABBRO_SET.polishedBlocks.base()))
                        .save(recipeOutput);

                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, StoneBlockSets.BURZUM_GABBRO_SET.chiseledTilesBlocks.base(), 5)
                        .pattern("TNT")
                        .pattern("NTN")
                        .pattern("TNT")
                        .define('T', StoneBlockSets.GABBRO_SET.tileBlocks.base())
                        .define('N', ResourceItemsME.BURZUM_STEEL_NUGGET)
                        .unlockedBy(getHasName(StoneBlockSets.GABBRO_SET.tileBlocks.base()),
                                has(StoneBlockSets.GABBRO_SET.tileBlocks.base()))
                        .save(recipeOutput);

                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.WATTLE_TRAPDOOR, 2)
                        .pattern("PLP")
                        .pattern("PLP")
                        .define('P', TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .define('L', ResourceItemsME.LEAD_NUGGET)
                        .unlockedBy(getHasName(ResourceItemsME.LEAD_NUGGET),
                                has(ResourceItemsME.LEAD_NUGGET))
                        .save(recipeOutput);

                createDyeableItemRecipe(recipeOutput, ModBlocks.WATTLE_TRAPDOOR, Items.RED_DYE, ModBlocks.RED_WATTLE_TRAPDOOR);
                createDyeableItemRecipe(recipeOutput, ModBlocks.WATTLE_TRAPDOOR, Items.GREEN_DYE, ModBlocks.GREEN_WATTLE_TRAPDOOR);
                createDyeableItemRecipe(recipeOutput, ModBlocks.WATTLE_TRAPDOOR, Items.BROWN_DYE, ModBlocks.DARK_WATTLE_TRAPDOOR);
                createDyeableItemRecipe(recipeOutput, ModBlocks.WATTLE_TRAPDOOR, Items.BLACK_DYE, ModBlocks.BLACK_WATTLE_TRAPDOOR);

                //createBrickworkBlockRecipe(exporter, StoneBlockSets.STONE_SET.tileBlocks.base(), GenericBlockSets.STUCCO.blockSet.base(), StoneBlockSets.STONE_SET.brickworkBlocks.base());
                //createBrickworkBlockRecipe(exporter, StoneBlockSets.CALCITE_SET.tileBlocks.base(), GenericBlockSets.STUCCO.blockSet.base(), StoneBlockSets.CALCITE_SET.brickworkBlocks.base());
                //createBrickworkBlockRecipe(exporter, Blocks.DEEPSLATE_TILES, GenericBlockSets.STUCCO.blockSet.base(), StoneBlockSets.DEEPSLATE_SET.brickworkBlocks.base());
                //createBrickworkBlockRecipe(exporter, StoneBlockSets.BASALT_SET.tileBlocks.base(), GenericBlockSets.STUCCO.blockSet.base(), StoneBlockSets.BASALT_SET.brickworkBlocks.base());
                //createBrickworkBlockRecipe(exporter, StoneBlockSets.ANDESITE_SET.tileBlocks.base(), GenericBlockSets.STUCCO.blockSet.base(),StoneBlockSets.ANDESITE_SET.brickworkBlocks.base());
                //createBrickworkBlockRecipe(exporter, StoneBlockSets.DIORITE_SET.tileBlocks.base(), GenericBlockSets.STUCCO.blockSet.base(), StoneBlockSets.DIORITE_SET.brickworkBlocks.base());
                //createBrickworkBlockRecipe(exporter, StoneBlockSets.GRANITE_SET.tileBlocks.base(), GenericBlockSets.STUCCO.blockSet.base(), StoneBlockSets.GRANITE_SET.brickworkBlocks.base());

                createBrickRecipe(recipeOutput, StoneBlockSets.QUARTZITE_SET.brickBlocks.base().asItem(),
                        StoneBlockSets.QUARTZITE_SET.tileBlocks.base(), 4);
                createBrickRecipe(recipeOutput, GenericBlockSets.PACKED_MIRE.blockSet.base().asItem(),
                        GenericBlockSets.MIRE_BRICKS.blockSet.base(), 4);
                createMossyRecipe(recipeOutput, GenericBlockSets.MIXED_STONES.blockSet.base(),
                        GenericBlockSets.MOSSY_MIXED_STONES.blockSet.base());
                oreSmelting(List.of(GenericBlockSets.MIXED_STONES.blockSet.base()),
                        RecipeCategory.BUILDING_BLOCKS,
                        GenericBlockSets.CRACKED_MIXED_STONES.blockSet.base(),
                        0.1F, 200, "cracked_bricks");

                createBrickRecipe(recipeOutput, Blocks.BRICKS.asItem(), GenericBlockSets.CLAY_TILING.blockSet.base(), 4);
                createCenterSurroundRecipe(recipeOutput, GenericBlockSets.CLAY_TILING.blockSet.base().asItem(), Items.BLACK_DYE, GenericBlockSets.BLACK_CLAY_TILING.blockSet.base().asItem(), 8);
                createCenterSurroundRecipe(recipeOutput, GenericBlockSets.CLAY_TILING.blockSet.base().asItem(), Items.BLUE_DYE, GenericBlockSets.BLUE_CLAY_TILING.blockSet.base().asItem(), 8);
                createCenterSurroundRecipe(recipeOutput, GenericBlockSets.CLAY_TILING.blockSet.base().asItem(), Items.BROWN_DYE, GenericBlockSets.BROWN_CLAY_TILING.blockSet.base().asItem(), 8);
                createCenterSurroundRecipe(recipeOutput, GenericBlockSets.CLAY_TILING.blockSet.base().asItem(), Items.CYAN_DYE, GenericBlockSets.CYAN_CLAY_TILING.blockSet.base().asItem(), 8);
                createCenterSurroundRecipe(recipeOutput, GenericBlockSets.CLAY_TILING.blockSet.base().asItem(), Items.GRAY_DYE, GenericBlockSets.GRAY_CLAY_TILING.blockSet.base().asItem(), 8);
                createCenterSurroundRecipe(recipeOutput, GenericBlockSets.CLAY_TILING.blockSet.base().asItem(), Items.GREEN_DYE, GenericBlockSets.GREEN_CLAY_TILING.blockSet.base().asItem(), 8);
                createCenterSurroundRecipe(recipeOutput, GenericBlockSets.CLAY_TILING.blockSet.base().asItem(), Items.LIGHT_BLUE_DYE, GenericBlockSets.LIGHT_BLUE_CLAY_TILING.blockSet.base().asItem(), 8);
                createCenterSurroundRecipe(recipeOutput, GenericBlockSets.CLAY_TILING.blockSet.base().asItem(), Items.LIGHT_GRAY_DYE, GenericBlockSets.LIGHT_GRAY_CLAY_TILING.blockSet.base().asItem(), 8);
                createCenterSurroundRecipe(recipeOutput, GenericBlockSets.CLAY_TILING.blockSet.base().asItem(), Items.LIME_DYE, GenericBlockSets.LIME_CLAY_TILING.blockSet.base().asItem(), 8);
                createCenterSurroundRecipe(recipeOutput, GenericBlockSets.CLAY_TILING.blockSet.base().asItem(), Items.MAGENTA_DYE, GenericBlockSets.MAGENTA_CLAY_TILING.blockSet.base().asItem(), 8);
                createCenterSurroundRecipe(recipeOutput, GenericBlockSets.CLAY_TILING.blockSet.base().asItem(), Items.ORANGE_DYE, GenericBlockSets.ORANGE_CLAY_TILING.blockSet.base().asItem(), 8);
                createCenterSurroundRecipe(recipeOutput, GenericBlockSets.CLAY_TILING.blockSet.base().asItem(), Items.PINK_DYE, GenericBlockSets.PINK_CLAY_TILING.blockSet.base().asItem(), 8);
                createCenterSurroundRecipe(recipeOutput, GenericBlockSets.CLAY_TILING.blockSet.base().asItem(), Items.PURPLE_DYE, GenericBlockSets.PURPLE_CLAY_TILING.blockSet.base().asItem(), 8);
                createCenterSurroundRecipe(recipeOutput, GenericBlockSets.CLAY_TILING.blockSet.base().asItem(), Items.RED_DYE, GenericBlockSets.RED_CLAY_TILING.blockSet.base().asItem(), 8);
                createCenterSurroundRecipe(recipeOutput, GenericBlockSets.CLAY_TILING.blockSet.base().asItem(), Items.WHITE_DYE, GenericBlockSets.WHITE_CLAY_TILING.blockSet.base().asItem(), 8);
                createCenterSurroundRecipe(recipeOutput, GenericBlockSets.CLAY_TILING.blockSet.base().asItem(), Items.YELLOW_DYE, GenericBlockSets.YELLOW_CLAY_TILING.blockSet.base().asItem(), 8);

                //createBrickworkBlockRecipe(exporter, StoneBlockSets.DOLOMITE_SET.tileBlocks.base(), GenericBlockSets.STUCCO.blockSet.base(), StoneBlockSets.DOLOMITE_SET.brickworkBlocks.base());
                //createBrickworkBlockRecipe(exporter, StoneBlockSets.HEMATITE_SET.tileBlocks.base(), GenericBlockSets.STUCCO.blockSet.base(), StoneBlockSets.HEMATITE_SET.brickworkBlocks.base());
                //createBrickworkBlockRecipe(exporter, StoneBlockSets.GNEISS_SET.tileBlocks.base(), GenericBlockSets.STUCCO.blockSet.base(), StoneBlockSets.GNEISS_SET.brickworkBlocks.base());
                //createBrickworkBlockRecipe(exporter, StoneBlockSets.IZHERABAN_SET.tileBlocks.base(), GenericBlockSets.STUCCO.blockSet.base(), StoneBlockSets.IZHERABAN_SET.brickworkBlocks.base());
                //createBrickworkBlockRecipe(exporter, StoneBlockSets.LIMESTONE_SET.tileBlocks.base(), GenericBlockSets.STUCCO.blockSet.base(), StoneBlockSets.LIMESTONE_SET.brickworkBlocks.base());
                //createBrickworkBlockRecipe(exporter, StoneBlockSets.GALONN_SET.tileBlocks.base(), GenericBlockSets.STUCCO.blockSet.base(), StoneBlockSets.GALONN_SET.brickworkBlocks.base());
                //createBrickworkBlockRecipe(exporter, StoneBlockSets.GABBRO_SET.brickBlocks.base(), GenericBlockSets.STUCCO.blockSet.base(), StoneBlockSets.GABBRO_SET.brickworkBlocks.base());
                //createBrickworkBlockRecipe(exporter, StoneBlockSets.TUFF_SET.tileBlocks.base(), GenericBlockSets.STUCCO.blockSet.base(), StoneBlockSets.TUFF_SET.brickworkBlocks.base());
                //createBrickworkBlockRecipe(exporter, StoneBlockSets.BLACKSTONE_SET.tileBlocks.base(), GenericBlockSets.STUCCO.blockSet.base(), StoneBlockSets.BLACKSTONE_SET.brickworkBlocks.base());
                createBrickworkBlockRecipe(recipeOutput, StoneBlockSets.TAN_CLAY.brickBlocks.base(), GenericBlockSets.PLASTER.blockSet.base(), StoneBlockSets.TAN_CLAY.brickworkBlocks.base());
                createBrickworkBlockRecipe(recipeOutput, GenericBlockSets.MIXED_STONES.blockSet.base(), GenericBlockSets.STUCCO.blockSet.base(), GenericBlockSets.MIXED_STONES_BRICKWORK.blockSet.base());
                //createBrickworkBlockRecipe(exporter, StoneBlockSets.MEDGON_SET.baseBlocks.base(), GenericBlockSets.STUCCO.blockSet.base(), StoneBlockSets.MEDGON_SET.brickworkBlocks.base());

                createCenterSurroundRecipe(recipeOutput, Items.BRICK, Items.BLUE_DYE, GenericBlockSets.BLUE_ROOF_TILES.blockSet.base().asItem(), 2);
                createCenterSurroundRecipe(recipeOutput, GenericBlockSets.BLUE_ROOF_TILES.blockSet.base().asItem(), Items.WHITE_DYE, GenericBlockSets.LIGHT_BLUE_ROOF_TILES.blockSet.base().asItem(), 8);
                createCenterSurroundRecipe(recipeOutput, GenericBlockSets.BLUE_ROOF_TILES.blockSet.base().asItem(), Items.LIGHT_GRAY_DYE, GenericBlockSets.BRIGHT_BLUE_ROOF_TILES.blockSet.base().asItem(), 8);
                createCenterSurroundRecipe(recipeOutput, GenericBlockSets.BLUE_ROOF_TILES.blockSet.base().asItem(), Items.GRAY_DYE, GenericBlockSets.OFF_BLUE_ROOF_TILES.blockSet.base().asItem(), 8);
                createCenterSurroundRecipe(recipeOutput, GenericBlockSets.BLUE_ROOF_TILES.blockSet.base().asItem(), Items.BLACK_DYE, GenericBlockSets.DARK_BLUE_ROOF_TILES.blockSet.base().asItem(), 8);

                createCenterSurroundRecipe(recipeOutput, Items.BRICK, Items.BROWN_DYE, GenericBlockSets.BROWN_ROOF_TILES.blockSet.base().asItem(), 2);
                createCenterSurroundRecipe(recipeOutput, GenericBlockSets.BROWN_ROOF_TILES.blockSet.base().asItem(), Items.GRAY_DYE, GenericBlockSets.OFF_BROWN_ROOF_TILES.blockSet.base().asItem(), 8);
                createCenterSurroundRecipe(recipeOutput, GenericBlockSets.BROWN_ROOF_TILES.blockSet.base().asItem(), Items.BLACK_DYE, GenericBlockSets.DARK_BROWN_ROOF_TILES.blockSet.base().asItem(), 8);

                createCenterSurroundRecipe(recipeOutput, Items.BRICK, Items.CYAN_DYE, GenericBlockSets.CYAN_ROOF_TILES.blockSet.base().asItem(), 2);
                createCenterSurroundRecipe(recipeOutput, GenericBlockSets.CYAN_ROOF_TILES.blockSet.base().asItem(), Items.WHITE_DYE, GenericBlockSets.LIGHT_CYAN_ROOF_TILES.blockSet.base().asItem(), 8);
                createCenterSurroundRecipe(recipeOutput, GenericBlockSets.CYAN_ROOF_TILES.blockSet.base().asItem(), Items.LIGHT_GRAY_DYE, GenericBlockSets.BRIGHT_CYAN_ROOF_TILES.blockSet.base().asItem(), 8);
                createCenterSurroundRecipe(recipeOutput, GenericBlockSets.CYAN_ROOF_TILES.blockSet.base().asItem(), Items.GRAY_DYE, GenericBlockSets.OFF_CYAN_ROOF_TILES.blockSet.base().asItem(), 8);
                createCenterSurroundRecipe(recipeOutput, GenericBlockSets.CYAN_ROOF_TILES.blockSet.base().asItem(), Items.BLACK_DYE, GenericBlockSets.DARK_CYAN_ROOF_TILES.blockSet.base().asItem(), 8);

                createCenterSurroundRecipe(recipeOutput, Items.BRICK, Items.GRAY_DYE, GenericBlockSets.GRAY_ROOF_TILES.blockSet.base().asItem(), 2);
                createCenterSurroundRecipe(recipeOutput, GenericBlockSets.GRAY_ROOF_TILES.blockSet.base().asItem(), Items.WHITE_DYE, GenericBlockSets.LIGHT_GRAY_ROOF_TILES.blockSet.base().asItem(), 8);
                createCenterSurroundRecipe(recipeOutput, GenericBlockSets.GRAY_ROOF_TILES.blockSet.base().asItem(), Items.GRAY_DYE, GenericBlockSets.OFF_GRAY_ROOF_TILES.blockSet.base().asItem(), 8);
                createCenterSurroundRecipe(recipeOutput, GenericBlockSets.GRAY_ROOF_TILES.blockSet.base().asItem(), Items.BLACK_DYE, GenericBlockSets.DARK_GRAY_ROOF_TILES.blockSet.base().asItem(), 8);

                createCenterSurroundRecipe(recipeOutput, Items.BRICK, Items.GREEN_DYE, GenericBlockSets.GREEN_ROOF_TILES.blockSet.base().asItem(), 2);
                createCenterSurroundRecipe(recipeOutput, GenericBlockSets.GREEN_ROOF_TILES.blockSet.base().asItem(), Items.WHITE_DYE, GenericBlockSets.LIGHT_GREEN_ROOF_TILES.blockSet.base().asItem(), 8);
                createCenterSurroundRecipe(recipeOutput, GenericBlockSets.GREEN_ROOF_TILES.blockSet.base().asItem(), Items.LIGHT_GRAY_DYE, GenericBlockSets.BRIGHT_GREEN_ROOF_TILES.blockSet.base().asItem(), 8);
                createCenterSurroundRecipe(recipeOutput, GenericBlockSets.GREEN_ROOF_TILES.blockSet.base().asItem(), Items.GRAY_DYE, GenericBlockSets.OFF_GREEN_ROOF_TILES.blockSet.base().asItem(), 8);
                createCenterSurroundRecipe(recipeOutput, GenericBlockSets.GREEN_ROOF_TILES.blockSet.base().asItem(), Items.BLACK_DYE, GenericBlockSets.DARK_GREEN_ROOF_TILES.blockSet.base().asItem(), 8);

                createCenterSurroundRecipe(recipeOutput, Items.BRICK, Items.RED_DYE, GenericBlockSets.RED_ROOF_TILES.blockSet.base().asItem(), 2);
                createCenterSurroundRecipe(recipeOutput, GenericBlockSets.RED_ROOF_TILES.blockSet.base().asItem(), Items.WHITE_DYE, GenericBlockSets.LIGHT_RED_ROOF_TILES.blockSet.base().asItem(), 8);
                createCenterSurroundRecipe(recipeOutput, GenericBlockSets.RED_ROOF_TILES.blockSet.base().asItem(), Items.LIGHT_GRAY_DYE, GenericBlockSets.BRIGHT_RED_ROOF_TILES.blockSet.base().asItem(), 8);
                createCenterSurroundRecipe(recipeOutput, GenericBlockSets.RED_ROOF_TILES.blockSet.base().asItem(), Items.GRAY_DYE, GenericBlockSets.OFF_RED_ROOF_TILES.blockSet.base().asItem(), 8);
                createCenterSurroundRecipe(recipeOutput, GenericBlockSets.RED_ROOF_TILES.blockSet.base().asItem(), Items.BLACK_DYE, GenericBlockSets.DARK_RED_ROOF_TILES.blockSet.base().asItem(), 8);

                createCenterSurroundRecipe(recipeOutput, Items.BRICK, Items.YELLOW_DYE, GenericBlockSets.YELLOW_ROOF_TILES.blockSet.base().asItem(), 2);
                createCenterSurroundRecipe(recipeOutput, GenericBlockSets.YELLOW_ROOF_TILES.blockSet.base().asItem(), Items.WHITE_DYE, GenericBlockSets.LIGHT_YELLOW_ROOF_TILES.blockSet.base().asItem(), 8);
                createCenterSurroundRecipe(recipeOutput, GenericBlockSets.YELLOW_ROOF_TILES.blockSet.base().asItem(), Items.LIGHT_GRAY_DYE, GenericBlockSets.BRIGHT_YELLOW_ROOF_TILES.blockSet.base().asItem(), 8);
                createCenterSurroundRecipe(recipeOutput, GenericBlockSets.YELLOW_ROOF_TILES.blockSet.base().asItem(), Items.GRAY_DYE, GenericBlockSets.OFF_YELLOW_ROOF_TILES.blockSet.base().asItem(), 8);
                createCenterSurroundRecipe(recipeOutput, GenericBlockSets.YELLOW_ROOF_TILES.blockSet.base().asItem(), Items.BLACK_DYE, GenericBlockSets.DARK_YELLOW_ROOF_TILES.blockSet.base().asItem(), 8);

                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, StoneBlockSets.TAN_CLAY.brickBlocks.base(), 5)
                        .pattern(" B ")
                        .pattern("BPB")
                        .pattern(" B ")
                        .define('P', GenericBlockSets.PLASTER.blockSet.base())
                        .define('B', Items.BRICKS)
                        .unlockedBy(getHasName(GenericBlockSets.PLASTER.blockSet.base()),
                                has(GenericBlockSets.PLASTER.blockSet.base()))
                        .save(recipeOutput);
                //endregion

                //region SMITHING
                createDaggerRecipeTag(recipeOutput, Items.STICK, TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")), WeaponItemsME.WOODEN_DAGGER);
                createDaggerRecipeTag(recipeOutput, Items.STICK, TagKey.create(Registries.ITEM, ResourceLocation.parse("stone_tool_materials")), WeaponItemsME.STONE_DAGGER);
                createDaggerRecipe(recipeOutput, Items.STICK, Items.DIAMOND, WeaponItemsME.DIAMOND_DAGGER);

                createSpearRecipeTag(recipeOutput, Items.STICK, TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")), WeaponItemsME.WOODEN_SPEAR);
                createSpearRecipeTag(recipeOutput, Items.STICK, TagKey.create(Registries.ITEM, ResourceLocation.parse("stone_tool_materials")), WeaponItemsME.STONE_SPEAR);
                createSpearRecipe(recipeOutput, Items.STICK, Items.DIAMOND, WeaponItemsME.DIAMOND_SPEAR);

                createToolSetRecipes(recipeOutput, Items.STICK, ResourceItemsME.BRONZE_INGOT, ToolItemsME.BRONZE_PICKAXE, ToolItemsME.BRONZE_AXE, ToolItemsME.BRONZE_SHOVEL, ToolItemsME.BRONZE_HOE);

                createToolSetRecipes(recipeOutput, Items.STICK, ResourceItemsME.CRUDE_INGOT, ToolItemsME.CRUDE_PICKAXE, ToolItemsME.CRUDE_AXE, ToolItemsME.CRUDE_SHOVEL, ToolItemsME.CRUDE_HOE);

                ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, WeaponItemsME.WEAVER_STING, 1)
                        .pattern("  S")
                        .pattern(" S ")
                        .pattern("W  ")
                        .define('S', ResourceItemsME.SPIDER_STINGER)
                        .define('W', Items.STICK)
                        .unlockedBy(getHasName(ResourceItemsME.SPIDER_STINGER),
                                has(ResourceItemsME.SPIDER_STINGER))
                        .save(recipeOutput);

                ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ResourceItemsME.FABRIC, 2)
                        .pattern("sss")
                        .pattern("sss")
                        .define('s', Items.STRING)
                        .unlockedBy(getHasName(Items.STRING),
                                has(Items.STRING))
                        .save(recipeOutput);

                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, DecorativeItemsME.REINFORCED_SCAFFOLDING, 6)
                        .pattern("LCL")
                        .pattern("S S")
                        .pattern("T T")
                        .define('L', TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c","stripped_logs")))
                        .define('C', GenericBlockSets.CANVAS.blockSet.base())
                        .define('T', ResourceItemsME.TIN_INGOT)
                        .define('S', Items.STICK)
                        .unlockedBy(getHasName(ResourceItemsME.TIN_INGOT),
                                has(ResourceItemsME.TIN_INGOT))
                        .save(recipeOutput, "reinforced_scaffolding");

                //region CANVAS
                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, GenericBlockSets.CANVAS.blockSet.base(), 3)
                        .pattern("FF")
                        .pattern("FF")
                        .define('F', ResourceItemsME.FABRIC)
                        .unlockedBy(getHasName(ResourceItemsME.FABRIC),
                                has(ResourceItemsME.FABRIC))
                        .save(recipeOutput);

                createCenterSurroundRecipe(recipeOutput, GenericBlockSets.CANVAS.blockSet.base().asItem(), Items.WHITE_DYE, GenericBlockSets.WHITE_CANVAS.blockSet.base().asItem(), 8);
                createCenterSurroundRecipe(recipeOutput, GenericBlockSets.CANVAS.blockSet.base().asItem(), Items.BLACK_DYE, GenericBlockSets.BLACK_CANVAS.blockSet.base().asItem(), 8);
                createCenterSurroundRecipe(recipeOutput, GenericBlockSets.CANVAS.blockSet.base().asItem(), Items.BLUE_DYE, GenericBlockSets.BLUE_CANVAS.blockSet.base().asItem(), 8);
                createCenterSurroundRecipe(recipeOutput, GenericBlockSets.CANVAS.blockSet.base().asItem(), Items.BROWN_DYE, GenericBlockSets.BROWN_CANVAS.blockSet.base().asItem(), 8);
                createCenterSurroundRecipe(recipeOutput, GenericBlockSets.CANVAS.blockSet.base().asItem(), Items.CYAN_DYE, GenericBlockSets.CYAN_CANVAS.blockSet.base().asItem(), 8);
                createCenterSurroundRecipe(recipeOutput, GenericBlockSets.CANVAS.blockSet.base().asItem(), Items.GRAY_DYE, GenericBlockSets.GRAY_CANVAS.blockSet.base().asItem(), 8);
                createCenterSurroundRecipe(recipeOutput, GenericBlockSets.CANVAS.blockSet.base().asItem(), Items.GREEN_DYE, GenericBlockSets.GREEN_CANVAS.blockSet.base().asItem(), 8);
                createCenterSurroundRecipe(recipeOutput, GenericBlockSets.CANVAS.blockSet.base().asItem(), Items.LIGHT_BLUE_DYE, GenericBlockSets.LIGHT_BLUE_CANVAS.blockSet.base().asItem(), 8);
                createCenterSurroundRecipe(recipeOutput, GenericBlockSets.CANVAS.blockSet.base().asItem(), Items.LIGHT_GRAY_DYE, GenericBlockSets.LIGHT_GRAY_CANVAS.blockSet.base().asItem(), 8);
                createCenterSurroundRecipe(recipeOutput, GenericBlockSets.CANVAS.blockSet.base().asItem(), Items.MAGENTA_DYE, GenericBlockSets.MAGENTA_CANVAS.blockSet.base().asItem(), 8);
                createCenterSurroundRecipe(recipeOutput, GenericBlockSets.CANVAS.blockSet.base().asItem(), Items.ORANGE_DYE, GenericBlockSets.ORANGE_CANVAS.blockSet.base().asItem(), 8);
                createCenterSurroundRecipe(recipeOutput, GenericBlockSets.CANVAS.blockSet.base().asItem(), Items.PINK_DYE, GenericBlockSets.PINK_CANVAS.blockSet.base().asItem(), 8);
                createCenterSurroundRecipe(recipeOutput, GenericBlockSets.CANVAS.blockSet.base().asItem(), Items.PURPLE_DYE, GenericBlockSets.PURPLE_CANVAS.blockSet.base().asItem(), 8);
                createCenterSurroundRecipe(recipeOutput, GenericBlockSets.CANVAS.blockSet.base().asItem(), Items.RED_DYE, GenericBlockSets.RED_CANVAS.blockSet.base().asItem(), 8);
                createCenterSurroundRecipe(recipeOutput, GenericBlockSets.CANVAS.blockSet.base().asItem(), Items.YELLOW_DYE, GenericBlockSets.YELLOW_CANVAS.blockSet.base().asItem(), 8);

                createCenterSurroundRecipe(recipeOutput, GenericBlockSets.BLUE_CANVAS.blockSet.base().asItem(), Items.GRAY_DYE, GenericBlockSets.DARK_BLUE_CANVAS.blockSet.base().asItem(), 8);
                createCenterSurroundRecipe(recipeOutput, GenericBlockSets.BROWN_CANVAS.blockSet.base().asItem(), Items.GRAY_DYE, GenericBlockSets.DARK_BROWN_CANVAS.blockSet.base().asItem(), 8);
                createCenterSurroundRecipe(recipeOutput, GenericBlockSets.GRAY_CANVAS.blockSet.base().asItem(), Items.GRAY_DYE, GenericBlockSets.DARK_GRAY_CANVAS.blockSet.base().asItem(), 8);
                createCenterSurroundRecipe(recipeOutput, GenericBlockSets.GREEN_CANVAS.blockSet.base().asItem(), Items.GRAY_DYE, GenericBlockSets.DARK_GREEN_CANVAS.blockSet.base().asItem(), 8);
                createCenterSurroundRecipe(recipeOutput, GenericBlockSets.RED_CANVAS.blockSet.base().asItem(), Items.GRAY_DYE, GenericBlockSets.DARK_RED_CANVAS.blockSet.base().asItem(), 8);
                createCenterSurroundRecipe(recipeOutput, GenericBlockSets.YELLOW_CANVAS.blockSet.base().asItem(), Items.GRAY_DYE, GenericBlockSets.DARK_YELLOW_CANVAS.blockSet.base().asItem(), 8);
                //endregion

                createBucketRecipe(recipeOutput, Items.IRON_INGOT, Items.BUCKET);

                createMetalsRecipe(recipeOutput, ResourceItemsME.TIN_NUGGET, ResourceItemsME.TIN_INGOT, ModBlocks.TIN_BLOCK);
                createMetalsRecipe(recipeOutput, ResourceItemsME.LEAD_NUGGET, ResourceItemsME.LEAD_INGOT, ModBlocks.LEAD_BLOCK);
                createMetalsRecipe(recipeOutput, ResourceItemsME.SILVER_NUGGET, ResourceItemsME.SILVER_INGOT, ModBlocks.SILVER_BLOCK);
                createMetalsRecipe(recipeOutput, ResourceItemsME.MITHRIL_NUGGET, ResourceItemsME.MITHRIL_INGOT, ModBlocks.MITHRIL_BLOCK);

                createMetalsRecipe(recipeOutput, ResourceItemsME.BRONZE_NUGGET, ResourceItemsME.BRONZE_INGOT, ModBlocks.BRONZE_BLOCK);
                createMetalsRecipe(recipeOutput, ResourceItemsME.CRUDE_NUGGET, ResourceItemsME.CRUDE_INGOT, ModBlocks.CRUDE_BLOCK);
                createMetalsRecipe(recipeOutput, ResourceItemsME.BURZUM_STEEL_NUGGET, ResourceItemsME.BURZUM_STEEL_INGOT, ModBlocks.BURZUM_STEEL_BLOCK);
                createMetalsRecipe(recipeOutput, ResourceItemsME.STEEL_NUGGET, ResourceItemsME.STEEL_INGOT, ModBlocks.STEEL_BLOCK);
                createMetalsRecipe(recipeOutput, ResourceItemsME.EDHEL_STEEL_NUGGET, ResourceItemsME.EDHEL_STEEL_INGOT, ModBlocks.EDHEL_STEEL_BLOCK);
                createMetalsRecipe(recipeOutput, ResourceItemsME.KHAZAD_STEEL_NUGGET, ResourceItemsME.KHAZAD_STEEL_INGOT, ModBlocks.KHAZAD_STEEL_BLOCK);

                ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ResourceItemsME.ADAMANT, 9)
                        .requires(ModBlocks.ADAMANT_BLOCK)
                        .unlockedBy(getHasName(ModBlocks.ADAMANT_BLOCK),
                                has(ModBlocks.ADAMANT_BLOCK))
                        .save(recipeOutput);

                ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ResourceItemsME.RUBY, 9)
                        .requires(ModBlocks.RUBY_BLOCK)
                        .unlockedBy(getHasName(ModBlocks.RUBY_BLOCK),
                                has(ModBlocks.RUBY_BLOCK))
                        .save(recipeOutput);

                ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ResourceItemsME.SAPPHIRE, 9)
                        .requires(ModBlocks.SAPPHIRE_BLOCK)
                        .unlockedBy(getHasName(ModBlocks.SAPPHIRE_BLOCK),
                                has(ModBlocks.SAPPHIRE_BLOCK))
                        .save(recipeOutput);
                //endregion

                //region SEEDS
                createSeedsRecipe(recipeOutput, FoodItemsME.TOMATO, ResourceItemsME.TOMATO_SEEDS);
                createSeedsRecipe(recipeOutput, FoodItemsME.BELL_PEPPER, ResourceItemsME.BELL_PEPPER_SEEDS);
                createSeedsRecipe(recipeOutput, FoodItemsME.CUCUMBER, ResourceItemsME.CUCUMBER_SEEDS);
                createSeedsRecipe(recipeOutput, FoodItemsME.LETTUCE, ResourceItemsME.LETTUCE_SEEDS);
                createSeedsRecipe(recipeOutput, ResourceItemsME.PIPEWEED, ResourceItemsME.PIPEWEED_SEEDS);
                createSeedsRecipe(recipeOutput, ResourceItemsME.FLAX, ResourceItemsME.FLAX_SEEDS);
                //endregion

                //region FOOD
                createCookedFoodRecipes(recipeOutput, FoodItemsME.RAW_HORSE, FoodItemsME.COOKED_HORSE);
                createCookedFoodRecipes(recipeOutput, FoodItemsME.MEAT_SKEWER, FoodItemsME.COOKED_MEAT_SKEWER);
                createCookedFoodRecipes(recipeOutput, FoodItemsME.VEGETABLE_SKEWER, FoodItemsME.COOKED_VEGETABLE_SKEWER);
                createCookedFoodRecipes(recipeOutput, Items.EGG, FoodItemsME.BOILED_EGG);
                //endregion


                SpecialRecipeBuilder.special(HelmetAttachmentRecipe::new).save(recipeOutput, "custom_armor_hood");
                SpecialRecipeBuilder.special(HelmetAttachmentRemovalRecipe::new).save(recipeOutput, "custom_armor_hood_removal");
                SpecialRecipeBuilder.special(BackAttachmentRecipe::new).save(recipeOutput, "custom_armor_cape");
                SpecialRecipeBuilder.special(BackAttachmentRemovalRecipe::new).save(recipeOutput, "custom_armor_cape_removal");
                SpecialRecipeBuilder.special(MountArmorAddonRemovalRecipe::new).save(recipeOutput, "custom_mount_armor_addon_removal");
                SpecialRecipeBuilder.special(MountArmorSideSkullAddonRecipe::new).save(recipeOutput, "custom_mount_armor_side_skull_addon");
                SpecialRecipeBuilder.special(MountArmorTopSkullAddonRecipe::new).save(recipeOutput, "custom_mount_armor_top_skull_addon");

                //region Alloying
                AlloyRecipeJsonBuilder.createAlloyRecipe(itemLookup, RecipeCategory.MISC, "bronze", INGOT_LIQUID_VALUE * 4,  4)
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "copper")))
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "copper")))
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "copper")))
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "tin")))
                        .unlockedBy(getHasName(Items.COPPER_INGOT),
                                has(Items.COPPER_INGOT))
                        .save(recipeOutput, String.valueOf(ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "bronze" + "_from_alloying")));

                AlloyRecipeJsonBuilder.createAlloyRecipe(itemLookup, RecipeCategory.MISC, "crude", INGOT_LIQUID_VALUE * 3, 3)
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "copper")))
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "copper")))
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "tin")))
                        .input(ResourceItemsME.ASH)
                        .unlockedBy(getHasName(Items.COPPER_INGOT),
                                has(Items.COPPER_INGOT))
                        .save(recipeOutput, String.valueOf(ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "crude" + "_from_alloying")));

                AlloyRecipeJsonBuilder.createAlloyRecipe(itemLookup, RecipeCategory.MISC, "steel", INGOT_LIQUID_VALUE * 3, 3)
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "iron")))
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "iron")))
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "iron")))
                        .input(Items.COAL)
                        .unlockedBy(getHasName(Items.IRON_INGOT),
                                has(Items.IRON_INGOT))
                        .save(recipeOutput, String.valueOf(ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "steel" + "_from_alloying_tags")));

                AlloyRecipeJsonBuilder.createAlloyRecipe(itemLookup, RecipeCategory.MISC, "khazad_steel", INGOT_LIQUID_VALUE * 3, 3)
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "iron")))
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "iron")))
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "lead")))
                        .input(Items.COAL)
                        .unlockedBy(getHasName(Items.IRON_INGOT),
                                has(Items.IRON_INGOT))
                        .save(recipeOutput, String.valueOf(ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "khazad_steel" + "_from_alloying_tags")));

                AlloyRecipeJsonBuilder.createAlloyRecipe(itemLookup, RecipeCategory.MISC, "edhel_steel", INGOT_LIQUID_VALUE * 3, 3)
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "iron")))
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "iron")))
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "iron")))
                        .input(ResourceItemsME.SILVER_NUGGET)
                        .unlockedBy(getHasName(Items.IRON_INGOT),
                                has(Items.IRON_INGOT))
                        .save(recipeOutput, String.valueOf(ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "edhel_steel" + "_from_alloying_tags")));

                AlloyRecipeJsonBuilder.createAlloyRecipe(itemLookup, RecipeCategory.MISC, "burzum_steel", INGOT_LIQUID_VALUE * 3, 3)
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "iron")))
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "iron")))
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "lead")))
                        .input(ResourceItemsME.ASH)
                        .unlockedBy(getHasName(Items.IRON_INGOT),
                                has(Items.IRON_INGOT))
                        .save(recipeOutput, String.valueOf(ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "burzum_steel" + "_from_alloying_tags")));

                AlloyRecipeJsonBuilder.createAlloyRecipe(itemLookup, RecipeCategory.MISC, "chicken_nugget", INGOT_LIQUID_VALUE, 1)
                        .input(Items.CHICKEN)
                        .input(Items.WHEAT)
                        .input(Items.EGG)
                        .input(FoodItemsME.GARLIC)
                        .unlockedBy(getHasName(ResourceItemsME.PTEROSAUR_NUGGET),
                                has(ResourceItemsME.PTEROSAUR_NUGGET))
                        .save(recipeOutput, String.valueOf(ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "chicken_nugget" + "_from_alloying")));


                HotMetalsModel.nuggets.forEach(nugget -> {
                    //createMeltRecipe(exporter, nugget, Registries.ITEM.getId(nugget).getPath().replace("_nugget", ""), INGOT_LIQUID_VALUE / 9);
                });
                HotMetalsModel.shapesTag.forEach(shape -> {
                    createAnvilShapingRecipeTag(recipeOutput, shape.tagKey(), shape.output(), shape.amount());
                });
                HotMetalsModel.shapesItem.forEach(shape -> {
                    createAnvilShapingRecipeItem(recipeOutput, shape.item(), shape.output(), shape.amount());
                });

                createMeltBulkRecipeTag(recipeOutput, TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "copper")), "copper");
                createMeltBulkRecipeTag(recipeOutput, TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "tin")), "tin");

                createMeltBulkRecipe(recipeOutput, ResourceItemsME.BRONZE_INGOT, "bronze");
                createMeltBulkRecipe(recipeOutput, ResourceItemsME.CRUDE_INGOT, "crude");

                createMeltBulkRecipeTag(recipeOutput, TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "lead")), "lead");
                createMeltBulkRecipeTag(recipeOutput, TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "silver")), "silver");
                createMeltBulkRecipeTag(recipeOutput, TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "iron")), "iron");
                createMeltBulkRecipeTag(recipeOutput, TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "gold")), "gold");

                createMeltBulkRecipe(recipeOutput, ResourceItemsME.STEEL_INGOT, "steel");
                createMeltBulkRecipe(recipeOutput, ResourceItemsME.KHAZAD_STEEL_INGOT, "khazad_steel");
                createMeltBulkRecipe(recipeOutput, ResourceItemsME.EDHEL_STEEL_INGOT, "edhel_steel");
                createMeltBulkRecipe(recipeOutput, ResourceItemsME.BURZUM_STEEL_INGOT, "burzum_steel");

                createMeltBulkRecipeTag(recipeOutput, TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "mithril")), "mithril");

                createMeltBulkRecipe(recipeOutput, Items.NETHERITE_INGOT, "netherite");

                createAnvilRecipe(recipeOutput, ModBlocks.STEEL_BLOCK.asItem(), ResourceItemsME.STEEL_INGOT, DecorativeItemsME.TREATED_ANVIL);
                createAnvilRecipe(recipeOutput, ModBlocks.KHAZAD_STEEL_BLOCK.asItem(), ResourceItemsME.KHAZAD_STEEL_INGOT, DecorativeItemsME.DWARVEN_TREATED_ANVIL);
                createAnvilRecipe(recipeOutput, ModBlocks.EDHEL_STEEL_BLOCK.asItem(), ResourceItemsME.EDHEL_STEEL_INGOT, DecorativeItemsME.ELVEN_TREATED_ANVIL);
                createAnvilRecipe(recipeOutput, ModBlocks.BURZUM_STEEL_BLOCK.asItem(), ResourceItemsME.BURZUM_STEEL_INGOT, DecorativeItemsME.ORCISH_TREATED_ANVIL);

                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, DecorativeItemsME.BELLOWS, 1)
                        .pattern(" PS")
                        .pattern("PFF")
                        .pattern("TPS")
                        .define('P', TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .define('S', Items.STICK)
                        .define('F', Items.LEATHER)
                        .define('T', ResourceItemsME.TIN_INGOT)
                        .unlockedBy(getHasName(ResourceItemsME.TIN_INGOT),
                                has(ResourceItemsME.TIN_INGOT))
                        .save(recipeOutput);

                createWattleRecipes(recipeOutput, Items.BRICKS,
                        ModBlocks.WATTLE_AND_BRICK, ModBlocks.WATTLE_AND_BRICK_CROSS, ModBlocks.WATTLE_AND_BRICK_RIGHT,
                        ModBlocks.WATTLE_AND_BRICK_LEFT, ModBlocks.WATTLE_AND_BRICK_PILLAR, ModBlocks.WATTLE_AND_BRICK_DIAMOND);

                createWattleRecipes(recipeOutput, GenericBlockSets.WHITE_DAUB.blockSet.base().asItem(),
                        ModBlocks.WATTLE_AND_WHITE_DAUB, ModBlocks.WATTLE_AND_WHITE_DAUB_CROSS, ModBlocks.WATTLE_AND_WHITE_DAUB_RIGHT,
                        ModBlocks.WATTLE_AND_WHITE_DAUB_LEFT, ModBlocks.WATTLE_AND_WHITE_DAUB_PILLAR, ModBlocks.WATTLE_AND_WHITE_DAUB_DIAMOND);

                createWattleRecipes(recipeOutput, GenericBlockSets.DARK_DAUB.blockSet.base().asItem(),
                        ModBlocks.DARK_WATTLE_AND_DARK_DAUB, ModBlocks.DARK_WATTLE_AND_DARK_DAUB_CROSS, ModBlocks.DARK_WATTLE_AND_DARK_DAUB_RIGHT,
                        ModBlocks.DARK_WATTLE_AND_DARK_DAUB_LEFT, ModBlocks.DARK_WATTLE_AND_DARK_DAUB_PILLAR, ModBlocks.DARK_WATTLE_AND_DARK_DAUB_DIAMOND);

                createWattleRecipes(recipeOutput, GenericBlockSets.YELLOW_DAUB.blockSet.base().asItem(),
                        ModBlocks.WATTLE_AND_YELLOW_DAUB, ModBlocks.WATTLE_AND_YELLOW_DAUB_CROSS, ModBlocks.WATTLE_AND_YELLOW_DAUB_RIGHT,
                        ModBlocks.WATTLE_AND_YELLOW_DAUB_LEFT, ModBlocks.WATTLE_AND_YELLOW_DAUB_PILLAR, ModBlocks.WATTLE_AND_YELLOW_DAUB_DIAMOND);

                createCenterSurroundRecipe(recipeOutput, ModBlocks.WATTLE_AND_WHITE_DAUB.asItem(), Items.BLACK_DYE, ModBlocks.BLACK_WATTLE_AND_WHITE_DAUB.asItem(), 8);
                createCenterSurroundRecipe(recipeOutput, ModBlocks.WATTLE_AND_WHITE_DAUB_CROSS.asItem(), Items.BLACK_DYE, ModBlocks.BLACK_WATTLE_AND_WHITE_DAUB_CROSS.asItem(), 8);
                createCenterSurroundRecipe(recipeOutput, ModBlocks.WATTLE_AND_WHITE_DAUB_RIGHT.asItem(), Items.BLACK_DYE, ModBlocks.BLACK_WATTLE_AND_WHITE_DAUB_RIGHT.asItem(), 8);
                createCenterSurroundRecipe(recipeOutput, ModBlocks.WATTLE_AND_WHITE_DAUB_LEFT.asItem(), Items.BLACK_DYE, ModBlocks.BLACK_WATTLE_AND_WHITE_DAUB_LEFT.asItem(), 8);
                createCenterSurroundRecipe(recipeOutput, ModBlocks.WATTLE_AND_WHITE_DAUB_PILLAR.asItem(), Items.BLACK_DYE, ModBlocks.BLACK_WATTLE_AND_WHITE_DAUB_PILLAR.asItem(), 8);
                createCenterSurroundRecipe(recipeOutput, ModBlocks.WATTLE_AND_WHITE_DAUB_DIAMOND.asItem(), Items.BLACK_DYE, ModBlocks.BLACK_WATTLE_AND_WHITE_DAUB_DIAMOND.asItem(), 8);

                createCenterSurroundRecipe(recipeOutput, ModBlocks.WATTLE_AND_WHITE_DAUB.asItem(), Items.GREEN_DYE, ModBlocks.GREEN_WATTLE_AND_WHITE_DAUB.asItem(), 8);
                createCenterSurroundRecipe(recipeOutput, ModBlocks.WATTLE_AND_WHITE_DAUB_CROSS.asItem(), Items.GREEN_DYE, ModBlocks.GREEN_WATTLE_AND_WHITE_DAUB_CROSS.asItem(), 8);
                createCenterSurroundRecipe(recipeOutput, ModBlocks.WATTLE_AND_WHITE_DAUB_RIGHT.asItem(), Items.GREEN_DYE, ModBlocks.GREEN_WATTLE_AND_WHITE_DAUB_RIGHT.asItem(), 8);
                createCenterSurroundRecipe(recipeOutput, ModBlocks.WATTLE_AND_WHITE_DAUB_LEFT.asItem(), Items.GREEN_DYE, ModBlocks.GREEN_WATTLE_AND_WHITE_DAUB_LEFT.asItem(), 8);
                createCenterSurroundRecipe(recipeOutput, ModBlocks.WATTLE_AND_WHITE_DAUB_PILLAR.asItem(), Items.GREEN_DYE, ModBlocks.GREEN_WATTLE_AND_WHITE_DAUB_PILLAR.asItem(), 8);
                createCenterSurroundRecipe(recipeOutput, ModBlocks.WATTLE_AND_WHITE_DAUB_DIAMOND.asItem(), Items.GREEN_DYE, ModBlocks.GREEN_WATTLE_AND_WHITE_DAUB_DIAMOND.asItem(), 8);

                createCenterSurroundRecipe(recipeOutput, ModBlocks.WATTLE_AND_WHITE_DAUB.asItem(), Items.RED_DYE, ModBlocks.RED_WATTLE_AND_WHITE_DAUB.asItem(), 8);
                createCenterSurroundRecipe(recipeOutput, ModBlocks.WATTLE_AND_WHITE_DAUB_CROSS.asItem(), Items.RED_DYE, ModBlocks.RED_WATTLE_AND_WHITE_DAUB_CROSS.asItem(), 8);
                createCenterSurroundRecipe(recipeOutput, ModBlocks.WATTLE_AND_WHITE_DAUB_RIGHT.asItem(), Items.RED_DYE, ModBlocks.RED_WATTLE_AND_WHITE_DAUB_RIGHT.asItem(), 8);
                createCenterSurroundRecipe(recipeOutput, ModBlocks.WATTLE_AND_WHITE_DAUB_LEFT.asItem(), Items.RED_DYE, ModBlocks.RED_WATTLE_AND_WHITE_DAUB_LEFT.asItem(), 8);
                createCenterSurroundRecipe(recipeOutput, ModBlocks.WATTLE_AND_WHITE_DAUB_PILLAR.asItem(), Items.RED_DYE, ModBlocks.RED_WATTLE_AND_WHITE_DAUB_PILLAR.asItem(), 8);
                createCenterSurroundRecipe(recipeOutput, ModBlocks.WATTLE_AND_WHITE_DAUB_DIAMOND.asItem(), Items.RED_DYE, ModBlocks.RED_WATTLE_AND_WHITE_DAUB_DIAMOND.asItem(), 8);

                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.BRONZE_BARS, 16)
                        .pattern("SSS")
                        .pattern("SSS")
                        .define('S', ResourceItemsME.BRONZE_INGOT)
                        .unlockedBy(getHasName(ResourceItemsME.BRONZE_INGOT),
                                has(ResourceItemsME.BRONZE_INGOT))
                        .save(recipeOutput);
                
                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.CRUDE_BARS, 16)
                        .pattern("SSS")
                        .pattern("SSS")
                        .define('S', ResourceItemsME.CRUDE_INGOT)
                        .unlockedBy(getHasName(ResourceItemsME.CRUDE_INGOT),
                                has(ResourceItemsME.CRUDE_INGOT))
                        .save(recipeOutput);

                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.TREATED_STEEL_BARS, 16)
                        .pattern("SSS")
                        .pattern("SSS")
                        .define('S', TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "steel_ingots")))
                        .unlockedBy(getHasName(ResourceItemsME.STEEL_INGOT),
                                has(ResourceItemsME.STEEL_INGOT))
                        .save(recipeOutput);

                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.BURZUM_BARS, 16)
                        .pattern("SSS")
                        .pattern("SSS")
                        .define('S', ResourceItemsME.BURZUM_STEEL_INGOT)
                        .unlockedBy(getHasName(ResourceItemsME.BURZUM_STEEL_INGOT),
                                has(ResourceItemsME.BURZUM_STEEL_INGOT))
                        .save(recipeOutput);


                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.AGED_WOOD_WINDOW, 4)
                        .pattern("AAA")
                        .pattern("AGA")
                        .pattern("AAA")
                        .define('A', GenericBlockSets.AGED_WOOD_PLANKS.blockSet.base())
                        .define('G', Items.GLASS)
                        .unlockedBy(getHasName(GenericBlockSets.AGED_WOOD_PLANKS.blockSet.base()),
                                has(GenericBlockSets.AGED_WOOD_PLANKS.blockSet.base()))
                        .save(recipeOutput);

                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.AGED_WOOD_TRAPDOOR, 2)
                        .pattern("WWW")
                        .pattern("WWW")
                        .define('W', GenericBlockSets.AGED_WOOD_PLANKS.blockSet.base())
                        .unlockedBy(getHasName(GenericBlockSets.AGED_WOOD_PLANKS.blockSet.base()),
                                has(GenericBlockSets.AGED_WOOD_PLANKS.blockSet.base()))
                        .save(recipeOutput);

                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.BRONZE_TRAPDOOR, 2)
                        .pattern("NSN")
                        .pattern("NSN")
                        .define('S', ResourceItemsME.BRONZE_INGOT)
                        .define('N', ResourceItemsME.BRONZE_NUGGET)
                        .unlockedBy(getHasName(ResourceItemsME.BRONZE_INGOT),
                                has(ResourceItemsME.BRONZE_INGOT))
                        .save(recipeOutput);

                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.CRUDE_TRAPDOOR, 2)
                        .pattern("NSN")
                        .pattern("NSN")
                        .define('S', ResourceItemsME.CRUDE_INGOT)
                        .define('N', ResourceItemsME.CRUDE_NUGGET)
                        .unlockedBy(getHasName(ResourceItemsME.CRUDE_INGOT),
                                has(ResourceItemsME.CRUDE_INGOT))
                        .save(recipeOutput);

                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.TREATED_STEEL_TRAPDOOR, 2)
                        .pattern("NSN")
                        .pattern("NSN")
                        .define('S', TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "steel_ingots")))
                        .define('N', TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "steel_nuggets")))
                        .unlockedBy(getHasName(ResourceItemsME.STEEL_INGOT),
                                has(ResourceItemsME.STEEL_INGOT))
                        .save(recipeOutput);

                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.AGED_WOOD_DOOR, 3)
                        .pattern("SS")
                        .pattern("SS")
                        .pattern("SS")
                        .define('S', GenericBlockSets.AGED_WOOD_PLANKS.blockSet.base())
                        .unlockedBy(getHasName(GenericBlockSets.AGED_WOOD_PLANKS.blockSet.base()),
                                has(GenericBlockSets.AGED_WOOD_PLANKS.blockSet.base()))
                        .save(recipeOutput);

                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.BRONZE_DOOR, 3)
                        .pattern("SS")
                        .pattern("SS")
                        .pattern("SS")
                        .define('S', ResourceItemsME.BRONZE_INGOT)
                        .unlockedBy(getHasName(ResourceItemsME.BRONZE_INGOT),
                                has(ResourceItemsME.BRONZE_INGOT))
                        .save(recipeOutput);

                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.CRUDE_DOOR, 3)
                        .pattern("SS")
                        .pattern("SS")
                        .pattern("SS")
                        .define('S', ResourceItemsME.CRUDE_INGOT)
                        .unlockedBy(getHasName(ResourceItemsME.CRUDE_INGOT),
                                has(ResourceItemsME.CRUDE_INGOT))
                        .save(recipeOutput);

                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.TREATED_STEEL_DOOR, 3)
                        .pattern("SS")
                        .pattern("SS")
                        .pattern("SS")
                        .define('S', TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "steel_ingots")))
                        .unlockedBy(getHasName(ResourceItemsME.STEEL_INGOT),
                                has(ResourceItemsME.STEEL_INGOT))
                        .save(recipeOutput);

                createPaneRecipe(recipeOutput, ResourceItemsME.SILVER_INGOT, ModBlocks.SILVER_BARS, 16);
                createPaneRecipe(recipeOutput, Items.GOLD_INGOT, ModBlocks.GILDED_BARS, 16);

                createCenterSurroundRecipe(recipeOutput, GenericBlockSets.WHITE_DAUB.blockSet.base().asItem(), Items.BLACK_DYE, GenericBlockSets.DARK_DAUB.blockSet.base().asItem(), 8);
                createCenterSurroundRecipe(recipeOutput, GenericBlockSets.WHITE_DAUB.blockSet.base().asItem(), Items.YELLOW_DYE, GenericBlockSets.YELLOW_DAUB.blockSet.base().asItem(), 8);

                //region TREATED_WOOD
                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, GenericBlockSets.TREATED_WOOD.blockSet.base(), 6)
                        .pattern("PPP")
                        .pattern("PHP")
                        .pattern("PPP")
                        .define('P', TagKey.create(Registries.ITEM, ResourceLocation.parse("logs")))
                        .define('H', Items.HONEYCOMB)
                        .unlockedBy(getHasName(Items.HONEYCOMB),
                                has(Items.HONEYCOMB))
                        .save(recipeOutput);

                createBrickRecipe(recipeOutput, GenericBlockSets.TREATED_WOOD.blockSet.base().asItem(), GenericBlockSets.TREATED_WOOD_BEAM.blockSet.base(), 3);
                createBrickRecipe(recipeOutput, GenericBlockSets.TREATED_WOOD_BEAM.blockSet.base().asItem(),
                        GenericBlockSets.TREATED_WOOD_TILING.blockSet.base(), 4);

                ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, GenericBlockSets.TREATED_WOOD_PLANKS.blockSet.base(), 4)
                        .requires(GenericBlockSets.TREATED_WOOD.blockSet.base())
                        .unlockedBy(getHasName(GenericBlockSets.TREATED_WOOD.blockSet.base()),
                                has(GenericBlockSets.TREATED_WOOD.blockSet.base()))
                        .save(recipeOutput);

                createBrickRecipe(recipeOutput, GenericBlockSets.TREATED_WOOD_PLANKS.blockSet.base().asItem(), GenericBlockSets.TREATED_WOOD_PANELS.blockSet.base(), 4);
                createGenericRecipes(GenericBlockSets.TREATED_WOOD);
                createGenericRecipes(GenericBlockSets.TREATED_WOOD_PLANKS);
                createGenericRecipes(GenericBlockSets.TREATED_WOOD_BEAM);
                createGenericRecipes(GenericBlockSets.TREATED_WOOD_PANELS);
                createGenericRecipes(GenericBlockSets.TREATED_WOOD_TILING);
                createGenericRecipes(GenericBlockSets.TREATED_WOOD_CARVED_BEAM);

                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, GenericBlockSets.TREATED_WOOD_CARVED_BEAM.blockSet.base(), 1)
                        .pattern("S")
                        .pattern("S")
                        .define('S', GenericBlockSets.TREATED_WOOD_BEAM.blockSet.slab())
                        .unlockedBy(getHasName(GenericBlockSets.TREATED_WOOD_BEAM.blockSet.slab()),
                                has(GenericBlockSets.TREATED_WOOD_BEAM.blockSet.slab()))
                        .save(recipeOutput);

                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.TREATED_WOOD_ROPE_FENCE, 3)
                        .pattern("WRW")
                        .pattern("WRW")
                        .define('W', GenericBlockSets.TREATED_WOOD.blockSet.base())
                        .define('R', ModDecorativeBlocks.ROPE)
                        .unlockedBy(getHasName(GenericBlockSets.TREATED_WOOD.blockSet.base()),
                                has(GenericBlockSets.TREATED_WOOD.blockSet.base()))
                        .save(recipeOutput);
                //endregion

                //region AGED_WOOD
                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, GenericBlockSets.AGED_WOOD.blockSet.base(), 6)
                        .pattern("PPP")
                        .pattern("PAP")
                        .pattern("PPP")
                        .define('P', TagKey.create(Registries.ITEM, ResourceLocation.parse("logs")))
                        .define('A', ResourceItemsME.ASH)
                        .unlockedBy(getHasName(ResourceItemsME.ASH),
                                has(ResourceItemsME.ASH))
                        .save(recipeOutput);

                createBrickRecipe(recipeOutput, GenericBlockSets.AGED_WOOD.blockSet.base().asItem(), GenericBlockSets.AGED_WOOD_BEAM.blockSet.base(), 3);
                createGenericRecipes(GenericBlockSets.AGED_WOOD);
                createGenericRecipes(GenericBlockSets.AGED_WOOD_PLANKS);
                createGenericRecipes(GenericBlockSets.AGED_WOOD_BEAM);
                createGenericRecipes(GenericBlockSets.AGED_WOOD_PANELS);
                createGenericRecipes(GenericBlockSets.AGED_WOOD_BOARDS);
                createGenericRecipes(GenericBlockSets.AGED_WOOD_CARVING);
                createGenericRecipes(GenericBlockSets.AGED_WOOD_FISH_CARVING);
                createGenericRecipes(GenericBlockSets.AGED_WOOD_CARVED_BEAM);
                createGenericRecipes(GenericBlockSets.AGED_WOOD_KNOTTED_BEAM);
                createShinglesRecipe(recipeOutput, GenericBlockSets.AGED_WOOD_PLANKS.blockSet.base(), GenericBlockSets.AGED_WOOD_SHINGLES.blockSet.base());
                createGenericRecipes(GenericBlockSets.AGED_WOOD_SHINGLES);

                ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, GenericBlockSets.AGED_WOOD_PLANKS.blockSet.base(), 4)
                        .requires(GenericBlockSets.AGED_WOOD.blockSet.base())
                        .unlockedBy(getHasName(GenericBlockSets.AGED_WOOD.blockSet.base()),
                                has(GenericBlockSets.AGED_WOOD.blockSet.base()))
                        .save(recipeOutput, String.valueOf(ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, BuiltInRegistries.BLOCK.getKey(GenericBlockSets.AGED_WOOD_PLANKS.blockSet.base()).getPath() + "_from_wood")));

                createBrickRecipe(recipeOutput, GenericBlockSets.AGED_WOOD_PLANKS.blockSet.base().asItem(), GenericBlockSets.AGED_WOOD_PANELS.blockSet.base(), 4);

                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, GenericBlockSets.AGED_WOOD_CARVING.blockSet.base(), 1)
                        .pattern("S")
                        .pattern("S")
                        .define('S', GenericBlockSets.AGED_WOOD_BEAM.blockSet.slab())
                        .unlockedBy(getHasName(GenericBlockSets.AGED_WOOD_BEAM.blockSet.slab()),
                                has(GenericBlockSets.AGED_WOOD_BEAM.blockSet.slab()))
                        .save(recipeOutput);

                createBrickRecipe(recipeOutput, GenericBlockSets.AGED_WOOD_PANELS.blockSet.base().asItem(), GenericBlockSets.AGED_WOOD_BOARDS.blockSet.base(), 4);

                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, GenericBlockSets.AGED_WOOD_FISH_CARVING.blockSet.base(), 1)
                        .pattern("S")
                        .pattern("S")
                        .define('S', GenericBlockSets.AGED_WOOD_CARVING.blockSet.slab())
                        .unlockedBy(getHasName(GenericBlockSets.AGED_WOOD_CARVING.blockSet.slab()),
                                has(GenericBlockSets.AGED_WOOD_CARVING.blockSet.slab()))
                        .save(recipeOutput);

                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, GenericBlockSets.AGED_WOOD_CARVED_BEAM.blockSet.base(), 3)
                        .pattern("P")
                        .pattern("P")
                        .pattern("P")
                        .define('P', GenericBlockSets.AGED_WOOD_BEAM.blockSet.base())
                        .unlockedBy(getHasName(GenericBlockSets.AGED_WOOD_BEAM.blockSet.base()),
                                has(GenericBlockSets.AGED_WOOD_BEAM.blockSet.base()))
                        .save(recipeOutput);

                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, GenericBlockSets.AGED_WOOD_KNOTTED_BEAM.blockSet.base(), 6)
                        .pattern("PW")
                        .pattern("WP")
                        .pattern("PW")
                        .define('W', GenericBlockSets.AGED_WOOD.blockSet.base())
                        .define('P', GenericBlockSets.AGED_WOOD_BEAM.blockSet.base())
                        .unlockedBy(getHasName(GenericBlockSets.AGED_WOOD_BEAM.blockSet.base()),
                                has(GenericBlockSets.AGED_WOOD_BEAM.blockSet.base()))
                        .save(recipeOutput);
                //endregion

                //region AGED_WOOD_REDDISH
                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, GenericBlockSets.AGED_WOOD_REDDISH_BEAM.blockSet.base(), 8)
                        .pattern("PPP")
                        .pattern("PRP")
                        .pattern("PPP")
                        .define('P', GenericBlockSets.AGED_WOOD_BEAM.blockSet.base())
                        .define('R', Items.RED_DYE)
                        .unlockedBy(getHasName(GenericBlockSets.AGED_WOOD_BEAM.blockSet.base()),
                                has(GenericBlockSets.AGED_WOOD_BEAM.blockSet.base()))
                        .save(recipeOutput);

                createGenericRecipes(GenericBlockSets.AGED_WOOD_REDDISH_BEAM);
                 //endregion

                //region AGED_WOOD_GILDED_CARVED_PILLAR
                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, GenericBlockSets.AGED_WOOD_GILDED_CARVED_PILLAR.blockSet.base(), 8)
                        .pattern("PPP")
                        .pattern("PGP")
                        .pattern("PPP")
                        .define('P', GenericBlockSets.AGED_WOOD_BEAM.blockSet.base())
                        .define('G', Items.GOLD_NUGGET)
                        .unlockedBy(getHasName(GenericBlockSets.AGED_WOOD_BEAM.blockSet.base()),
                                has(GenericBlockSets.AGED_WOOD_BEAM.blockSet.base()))
                        .save(recipeOutput);

                createGenericRecipes(GenericBlockSets.AGED_WOOD_GILDED_CARVED_PILLAR);
                 //endregion

                //region AGED_WOOD_GILDED_CARVED_PILLAR
                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, GenericBlockSets.AGED_WOOD_GILDED_CARVING.blockSet.base(), 8)
                        .pattern("PPP")
                        .pattern("PGP")
                        .pattern("PPP")
                        .define('P', GenericBlockSets.AGED_WOOD_CARVING.blockSet.base())
                        .define('G', Items.GOLD_NUGGET)
                        .unlockedBy(getHasName(GenericBlockSets.AGED_WOOD_CARVING.blockSet.base()),
                                has(GenericBlockSets.AGED_WOOD_CARVING.blockSet.base()))
                        .save(recipeOutput);

                createGenericRecipes(GenericBlockSets.AGED_WOOD_GILDED_CARVING);
                 //endregion

                //region AGED_WOOD_GILDED_CARVED_PILLAR
                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, GenericBlockSets.AGED_WOOD_GILDED_HORSES.blockSet.base(), 8)
                        .pattern("PPP")
                        .pattern("PGP")
                        .pattern("PPP")
                        .define('P', GenericBlockSets.AGED_WOOD_FISH_CARVING.blockSet.base())
                        .define('G', Items.GOLD_NUGGET)
                        .unlockedBy(getHasName(GenericBlockSets.AGED_WOOD_FISH_CARVING.blockSet.base()),
                                has(GenericBlockSets.AGED_WOOD_FISH_CARVING.blockSet.base()))
                        .save(recipeOutput);

                createGenericRecipes(GenericBlockSets.AGED_WOOD_GILDED_HORSES);
                 //endregion

                //region AGED_WOOD_GILDED_CARVED_PILLAR
                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, GenericBlockSets.AGED_WOOD_GILDED_TRIM.blockSet.base(), 8)
                        .pattern("PPP")
                        .pattern("PGP")
                        .pattern("PPP")
                        .define('P', GenericBlockSets.AGED_WOOD_PANELS.blockSet.base())
                        .define('G', Items.GOLD_NUGGET)
                        .unlockedBy(getHasName(GenericBlockSets.AGED_WOOD_PANELS.blockSet.base()),
                                has(GenericBlockSets.AGED_WOOD_PANELS.blockSet.base()))
                        .save(recipeOutput);

                createGenericRecipes(GenericBlockSets.AGED_WOOD_GILDED_TRIM);
                 //endregion

                ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.OLD_SKULL)
                        .requires(Items.SKELETON_SKULL)
                        .requires(ResourceItemsME.ASH)
                        .unlockedBy(getHasName(Items.SKELETON_SKULL), has(Items.SKELETON_SKULL))
                        .save(recipeOutput);

                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.SKELETON)
                        .pattern("BSB")
                        .pattern(" B ")
                        .pattern("B B")
                        .define('B', TagKey.create(Registries.ITEM, MiddleEarth.of("bones")))
                        .define('S', ModDecorativeBlocks.OLD_SKULL)
                        .unlockedBy(getHasName(ModDecorativeBlocks.OLD_SKULL), has(ModDecorativeBlocks.OLD_SKULL))
                        .save(recipeOutput);

                createCombinedItemRecipe(recipeOutput, Blocks.SKELETON_SKULL, ItemTags.CANDLES, ModDecorativeBlocks.SKULL_CANDLE);

                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.CANDLESTICK, 1)
                        .pattern("C")
                        .pattern("S")
                        .pattern("S")
                        .define('C', TagKey.create(Registries.ITEM, ResourceLocation.parse("candles")))
                        .define('S', ResourceItemsME.STEEL_NUGGET)
                        .unlockedBy(getHasName(Items.CANDLE),
                                has(Items.CANDLE))
                        .save(recipeOutput);
                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.CANDLE_HOLDER, 1)
                        .pattern("C ")
                        .pattern("SS")
                        .define('C', TagKey.create(Registries.ITEM, ResourceLocation.parse("candles")))
                        .define('S', ResourceItemsME.STEEL_NUGGET)
                        .unlockedBy(getHasName(Items.CANDLE),
                                has(Items.CANDLE))
                        .save(recipeOutput);

                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.CERAMIC_LAMP, 1)
                        .pattern("T ")
                        .pattern("BB")
                        .define('T', Items.TORCH)
                        .define('B', Items.BRICK)
                        .unlockedBy(getHasName(Items.BRICK),
                                has(Items.BRICK))
                        .save(recipeOutput);

                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.CANDLE_HEAP, 1)
                        .pattern("CCC")
                        .pattern("CCC")
                        .define('C', TagKey.create(Registries.ITEM, ResourceLocation.parse("candles")))
                        .unlockedBy(getHasName(Items.CANDLE),
                                has(Items.CANDLE))
                        .save(recipeOutput);

                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.SMALL_CHANDELIER)
                        .pattern(" N ")
                        .pattern("CNC")
                        .pattern("N N")
                        .define('C', ItemTags.CANDLES)
                        .define('N', TagKey.create(Registries.ITEM,
                                ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "steel_nuggets")))
                        .unlockedBy(getHasName(Items.CANDLE), has(Items.CANDLE))
                        .save(recipeOutput);
                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.CHANDELIER)
                        .pattern(" N ")
                        .pattern("CHC")
                        .pattern("N N")
                        .define('C', ItemTags.CANDLES)
                        .define('H', ModDecorativeBlocks.SMALL_CHANDELIER)
                        .define('N', TagKey.create(Registries.ITEM,
                                ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "steel_nuggets")))
                        .unlockedBy(getHasName(Items.CANDLE), has(Items.CANDLE))
                        .save(recipeOutput);

                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.SMALL_BRONZE_CHANDELIER)
                        .pattern(" N ")
                        .pattern("CNC")
                        .pattern("N N")
                        .define('C', ItemTags.CANDLES)
                        .define('N', ResourceItemsME.BRONZE_NUGGET)
                        .unlockedBy(getHasName(Items.CANDLE), has(Items.CANDLE))
                        .save(recipeOutput);
                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.BRONZE_CHANDELIER)
                        .pattern(" N ")
                        .pattern("CHC")
                        .pattern("N N")
                        .define('C', ItemTags.CANDLES)
                        .define('H', ModDecorativeBlocks.SMALL_BRONZE_CHANDELIER)
                        .define('N', ResourceItemsME.BRONZE_NUGGET)
                        .unlockedBy(getHasName(Items.CANDLE), has(Items.CANDLE))
                        .save(recipeOutput);

                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.STONE_LECTERN.asItem(), 1)
                        .pattern("SSS")
                        .pattern(" B ")
                        .pattern(" S ")
                        .define('S', Items.STONE)
                        .define('B', Items.BOOKSHELF)
                        .unlockedBy(getHasName(Items.BOOKSHELF),
                                has(Items.BOOKSHELF))
                        .save(recipeOutput);
                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.CHISELED_DOLOMITE_BOOKSHELF, 1)
                        .pattern("DDD")
                        .pattern("SSS")
                        .pattern("DDD")
                        .define('D', StoneBlockSets.DOLOMITE_SET.baseBlocks.base())
                        .define('S', StoneBlockSets.DOLOMITE_SET.baseBlocks.slab())
                        .unlockedBy(getHasName(Items.BOOKSHELF),
                                has(Items.BOOKSHELF))
                        .save(recipeOutput);

                createStatueRecipe(recipeOutput, Blocks.POLISHED_BASALT, Blocks.BASALT, StoneBlockSets.BASALT_SET.baseBlocks.wall(), ModDecorativeBlocks.BASALT_STATUE);
                createStatueRecipe(recipeOutput, StoneBlockSets.CALCITE_SET.polishedBlocks.base(), Blocks.CALCITE, StoneBlockSets.CALCITE_SET.baseBlocks.wall(), ModDecorativeBlocks.CALCITE_STATUE);
                createStatueRecipe(recipeOutput, StoneBlockSets.DEEPSLATE_SET.polishedBlocks.base(), Blocks.DEEPSLATE, StoneBlockSets.DEEPSLATE_SET.baseBlocks.wall(), ModDecorativeBlocks.DEEPSLATE_STATUE);
                createStatueRecipe(recipeOutput, StoneBlockSets.DIORITE_SET.polishedBlocks.base(), Blocks.DIORITE, Blocks.DIORITE_WALL, ModDecorativeBlocks.DIORITE_STATUE);
                createStatueRecipe(recipeOutput, StoneBlockSets.GABBRO_SET.polishedBlocks.base(), StoneBlockSets.GABBRO_SET.baseBlocks.base(), StoneBlockSets.GABBRO_SET.baseBlocks.wall(), ModDecorativeBlocks.GABBRO_STATUE);
                createStatueRecipe(recipeOutput, StoneBlockSets.GALONN_SET.polishedBlocks.base(), StoneBlockSets.GALONN_SET.baseBlocks.base(), StoneBlockSets.GALONN_SET.baseBlocks.wall(), ModDecorativeBlocks.GALONN_STATUE);
                createStatueRecipe(recipeOutput, StoneBlockSets.KHAGALABAN_SET.polishedBlocks.base(), StoneBlockSets.KHAGALABAN_SET.baseBlocks.base(), StoneBlockSets.KHAGALABAN_SET.baseBlocks.wall(), ModDecorativeBlocks.KHAGALABAN_STATUE);
                createStatueRecipe(recipeOutput, StoneBlockSets.PUMICE_SET.baseBlocks.base(), StoneBlockSets.PUMICE_SET.baseBlocks.base(), StoneBlockSets.PUMICE_SET.baseBlocks.wall(), ModDecorativeBlocks.PUMICE_STATUE);
                createStatueRecipe(recipeOutput, Blocks.POLISHED_TUFF, Blocks.TUFF, Blocks.TUFF_WALL, ModDecorativeBlocks.TUFF_STATUE);
                createStatueRecipe(recipeOutput, StoneBlockSets.ZIGILABAN_SET.baseBlocks.base(), StoneBlockSets.ZIGILABAN_SET.baseBlocks.base(), StoneBlockSets.ZIGILABAN_SET.baseBlocks.wall(), ModDecorativeBlocks.ZIGILABAN_STATUE);

                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.CERAMIC_PLATE, 1)
                        .pattern("BB")
                        .define('B', Items.BRICK)
                        .unlockedBy(getHasName(Items.BRICK),
                                has(Items.BRICK))
                        .save(recipeOutput);
                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.ROTTEN_PLATE, 4)
                        .pattern("RR")
                        .define('R', WoodBlockSets.ROTTEN_SET.logBlocks.log())
                        .unlockedBy(getHasName(WoodBlockSets.ROTTEN_SET.logBlocks.log()),
                                has(WoodBlockSets.ROTTEN_SET.logBlocks.log()))
                        .save(recipeOutput);
                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.SILVER_PLATE, 1)
                        .pattern("SS")
                        .define('S', ResourceItemsME.SILVER_INGOT)
                        .unlockedBy(getHasName(ResourceItemsME.SILVER_INGOT),
                                has(ResourceItemsME.SILVER_INGOT))
                        .save(recipeOutput);

                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.MEDGON_SPIKE, 1)
                        .pattern("M  ")
                        .pattern("MM ")
                        .pattern("PMP")
                        .define('M', StoneBlockSets.MEDGON_SET.baseBlocks.base())
                        .define('P', StoneBlockSets.MEDGON_SET.polishedBlocks.base())
                        .unlockedBy(getHasName(StoneBlockSets.MEDGON_SET.baseBlocks.base()),
                                has(StoneBlockSets.MEDGON_SET.baseBlocks.base()))
                        .save(recipeOutput);

                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.BURZUM_SPIKES, 4)
                        .pattern(" N ")
                        .pattern("NBN")
                        .pattern("BBB")
                        .define('B', ResourceItemsME.BURZUM_STEEL_INGOT)
                        .define('N', ResourceItemsME.BURZUM_STEEL_NUGGET)
                        .unlockedBy(getHasName(ResourceItemsME.BURZUM_STEEL_INGOT),
                                has(ResourceItemsME.BURZUM_STEEL_INGOT))
                        .save(recipeOutput);

                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.TAPPER, 1)
                        .pattern(" S ")
                        .pattern("LBL")
                        .pattern(" L ")
                        .define('S', ResourceItemsME.STEEL_NUGGET)
                        .define('L', ItemTags.LOGS)
                        .define('B', Items.BUCKET)
                        .unlockedBy(getHasName(Items.BUCKET),
                                has(Items.BUCKET));

                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.ORCISH_DRUM, 1)
                        .pattern("SLS")
                        .pattern("W W")
                        .pattern(" W ")
                        .define('S', Items.STICK)
                        .define('W', ItemTags.LOGS)
                        .define('L', Items.LEATHER)
                        .unlockedBy(getHasName(Items.LEATHER),
                                has(Items.LEATHER))
                        .save(recipeOutput);

                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.WATTLE_AND_BRICK_WINDOW, 4)
                        .pattern("BSB")
                        .pattern("SGS")
                        .pattern("BSB")
                        .define('B', Items.BRICKS)
                        .define('G', Items.GLASS)
                        .define('S', Items.STICK)
                        .unlockedBy(getHasName(Items.BRICKS),
                                has(Items.BRICKS))
                        .save(recipeOutput);

                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.WATTLE_FRAMED_WINDOW, 2)
                        .pattern("SSS")
                        .pattern("SGS")
                        .pattern("SSS")
                        .define('G', Items.GLASS)
                        .define('S', Items.STICK)
                        .unlockedBy(getHasName(Items.STICK),
                                has(Items.STICK))
                        .save(recipeOutput);

                createCenterSurroundRecipe(recipeOutput, ModDecorativeBlocks.WATTLE_FRAMED_WINDOW.asItem(), Items.GRAY_DYE, ModDecorativeBlocks.DARK_WATTLE_FRAMED_WINDOW.asItem(), 8);
                createCenterSurroundRecipe(recipeOutput, ModDecorativeBlocks.WATTLE_FRAMED_WINDOW.asItem(), Items.BLACK_DYE, ModDecorativeBlocks.BLACK_WATTLE_FRAMED_WINDOW.asItem(), 8);
                createCenterSurroundRecipe(recipeOutput, ModDecorativeBlocks.WATTLE_FRAMED_WINDOW.asItem(), Items.GREEN_DYE, ModDecorativeBlocks.GREEN_WATTLE_FRAMED_WINDOW.asItem(), 8);
                createCenterSurroundRecipe(recipeOutput, ModDecorativeBlocks.WATTLE_FRAMED_WINDOW.asItem(), Items.RED_DYE, ModDecorativeBlocks.RED_WATTLE_FRAMED_WINDOW.asItem(), 8);
                createCenterSurroundRecipe(recipeOutput, ModDecorativeBlocks.WATTLE_FRAMED_WINDOW.asItem(), Items.WHITE_DYE, ModDecorativeBlocks.WHITE_WATTLE_FRAMED_WINDOW.asItem(), 8);

                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.MUD_BRICK_ROUND_WINDOW, 4)
                        .pattern("MBM")
                        .pattern("BGB")
                        .pattern("MBM")
                        .define('M', Items.MUD_BRICKS)
                        .define('G', Items.GLASS)
                        .define('B', Items.BRICK)
                        .unlockedBy(getHasName(Items.BRICKS),
                                has(Items.BRICKS))
                        .save(recipeOutput);

                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.WHITE_DAUB_ROUND_WINDOW, 4)
                        .pattern("WSW")
                        .pattern("SGS")
                        .pattern("WSW")
                        .define('W', GenericBlockSets.WHITE_DAUB.blockSet.base())
                        .define('G', Items.GLASS)
                        .define('S', Items.STICK)
                        .unlockedBy(getHasName(GenericBlockSets.WHITE_DAUB.blockSet.base()),
                                has(GenericBlockSets.WHITE_DAUB.blockSet.base()))
                        .save(recipeOutput);

                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.YELLOW_DAUB_ROUND_WINDOW, 4)
                        .pattern("WSW")
                        .pattern("SGS")
                        .pattern("WSW")
                        .define('W', GenericBlockSets.YELLOW_DAUB.blockSet.base())
                        .define('G', Items.GLASS)
                        .define('S', Items.STICK)
                        .unlockedBy(getHasName(GenericBlockSets.YELLOW_DAUB.blockSet.base()),
                                has(GenericBlockSets.YELLOW_DAUB.blockSet.base()))
                        .save(recipeOutput);

                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.PLASTER_ROUND_WINDOW, 4)
                        .pattern("WSW")
                        .pattern("SGS")
                        .pattern("WSW")
                        .define('W', GenericBlockSets.PLASTER.blockSet.base())
                        .define('G', Items.GLASS)
                        .define('S', Items.STICK)
                        .unlockedBy(getHasName(GenericBlockSets.PLASTER.blockSet.base()),
                                has(GenericBlockSets.PLASTER.blockSet.base()))
                        .save(recipeOutput);

                stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, GenericBlockSets.CUT_BRONZE.blockSet.base(), ModBlocks.BRONZE_BLOCK, 4);
                stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, GenericBlockSets.CUT_CRUDE_PLATES.blockSet.base(), ModBlocks.CRUDE_BLOCK, 4);
                stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, GenericBlockSets.CUT_LEAD.blockSet.base(), ModBlocks.LEAD_BLOCK, 4);
                stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, GenericBlockSets.CUT_SILVER.blockSet.base(), ModBlocks.SILVER_BLOCK, 4);

                createCushionRecipe(recipeOutput, Blocks.BLUE_WOOL, ModDecorativeBlocks.BLUE_CUSHION);
                createDyeableItemRecipe(recipeOutput, ModDecorativeBlocks.BLUE_CUSHION, Items.GRAY_DYE, ModDecorativeBlocks.DARK_BLUE_CUSHION);
                createCushionRecipe(recipeOutput, Blocks.BROWN_WOOL, ModDecorativeBlocks.BROWN_CUSHION);
                createDyeableItemRecipe(recipeOutput, ModDecorativeBlocks.BROWN_CUSHION, Items.GRAY_DYE, ModDecorativeBlocks.DARK_BROWN_CUSHION);
                createCushionRecipe(recipeOutput, Blocks.GREEN_WOOL, ModDecorativeBlocks.GREEN_CUSHION);
                createDyeableItemRecipe(recipeOutput, ModDecorativeBlocks.GREEN_CUSHION, Items.GRAY_DYE, ModDecorativeBlocks.DARK_GREEN_CUSHION);
                createCushionRecipe(recipeOutput, Blocks.RED_WOOL, ModDecorativeBlocks.RED_CUSHION);
                createDyeableItemRecipe(recipeOutput, ModDecorativeBlocks.RED_CUSHION, Items.GRAY_DYE, ModDecorativeBlocks.DARK_RED_CUSHION);

                createSmallCurtainRecipe(recipeOutput, Blocks.BLACK_WOOL, ModDecorativeBlocks.SMALL_BLACK_CURTAIN);
                createSmallCurtainRecipe(recipeOutput, Blocks.BLUE_WOOL, ModDecorativeBlocks.SMALL_BLUE_CURTAIN);
                createSmallCurtainRecipe(recipeOutput, Blocks.BROWN_WOOL, ModDecorativeBlocks.SMALL_BROWN_CURTAIN);
                createDyeableItemRecipe(recipeOutput, ModDecorativeBlocks.SMALL_BLUE_CURTAIN, Items.GRAY_DYE, ModDecorativeBlocks.SMALL_DARK_BLUE_CURTAIN);
                createDyeableItemRecipe(recipeOutput, ModDecorativeBlocks.SMALL_BROWN_CURTAIN, Items.GRAY_DYE, ModDecorativeBlocks.SMALL_DARK_BROWN_CURTAIN);
                createDyeableItemRecipe(recipeOutput, ModDecorativeBlocks.SMALL_GREEN_CURTAIN, Items.GRAY_DYE, ModDecorativeBlocks.SMALL_DARK_GREEN_CURTAIN);
                createDyeableItemRecipe(recipeOutput, ModDecorativeBlocks.SMALL_RED_CURTAIN, Items.GRAY_DYE, ModDecorativeBlocks.SMALL_DARK_RED_CURTAIN);
                createSmallFancyCurtainRecipe(recipeOutput, Blocks.BLUE_WOOL, ModDecorativeBlocks.SMALL_FANCY_BLUE_CURTAIN);
                createSmallFancyCurtainRecipe(recipeOutput, Blocks.GREEN_WOOL, ModDecorativeBlocks.SMALL_FANCY_GREEN_CURTAIN);
                createSmallFancyCurtainRecipe(recipeOutput, Blocks.RED_WOOL, ModDecorativeBlocks.SMALL_FANCY_RED_CURTAIN);
                createSmallCurtainRecipe(recipeOutput, Blocks.GRAY_WOOL, ModDecorativeBlocks.SMALL_GRAY_CURTAIN);
                createSmallCurtainRecipe(recipeOutput, Blocks.GREEN_WOOL, ModDecorativeBlocks.SMALL_GREEN_CURTAIN);
                createSmallCurtainRecipe(recipeOutput, Blocks.PURPLE_WOOL, ModDecorativeBlocks.SMALL_PURPLE_CURTAIN);
                createSmallCurtainRecipe(recipeOutput, Blocks.RED_WOOL, ModDecorativeBlocks.SMALL_RED_CURTAIN);
                createSmallCurtainRecipe(recipeOutput, Blocks.WHITE_WOOL, ModDecorativeBlocks.SMALL_WHITE_CURTAIN);
                createSmallCurtainRecipe(recipeOutput, Blocks.YELLOW_WOOL, ModDecorativeBlocks.SMALL_YELLOW_CURTAIN);

                createCurtainRecipe(recipeOutput, Blocks.BLACK_WOOL, ModDecorativeBlocks.BLACK_CURTAIN);
                createCurtainRecipe(recipeOutput, Blocks.BLUE_WOOL, ModDecorativeBlocks.BLUE_CURTAIN);
                createCurtainRecipe(recipeOutput, Blocks.BROWN_WOOL, ModDecorativeBlocks.BROWN_CURTAIN);
                createDyeableItemRecipe(recipeOutput, ModDecorativeBlocks.BLUE_CURTAIN, Items.GRAY_DYE, ModDecorativeBlocks.DARK_BLUE_CURTAIN);
                createDyeableItemRecipe(recipeOutput, ModDecorativeBlocks.BROWN_CURTAIN, Items.GRAY_DYE, ModDecorativeBlocks.DARK_BROWN_CURTAIN);
                createDyeableItemRecipe(recipeOutput, ModDecorativeBlocks.GREEN_CURTAIN, Items.GRAY_DYE, ModDecorativeBlocks.DARK_GREEN_CURTAIN);
                createDyeableItemRecipe(recipeOutput, ModDecorativeBlocks.RED_CURTAIN, Items.GRAY_DYE, ModDecorativeBlocks.DARK_RED_CURTAIN);
                createFancyCurtainRecipe(recipeOutput, Blocks.BLUE_WOOL, ModDecorativeBlocks.FANCY_BLUE_CURTAIN);
                createFancyCurtainRecipe(recipeOutput, Blocks.GREEN_WOOL, ModDecorativeBlocks.FANCY_GREEN_CURTAIN);
                createFancyCurtainRecipe(recipeOutput, Blocks.RED_WOOL, ModDecorativeBlocks.FANCY_RED_CURTAIN);
                createCurtainRecipe(recipeOutput, Blocks.GRAY_WOOL, ModDecorativeBlocks.GRAY_CURTAIN);
                createCurtainRecipe(recipeOutput, Blocks.GREEN_WOOL, ModDecorativeBlocks.GREEN_CURTAIN);
                createCurtainRecipe(recipeOutput, Blocks.PURPLE_WOOL, ModDecorativeBlocks.PURPLE_CURTAIN);
                createCurtainRecipe(recipeOutput, Blocks.RED_WOOL, ModDecorativeBlocks.RED_CURTAIN);
                createCurtainRecipe(recipeOutput, Blocks.WHITE_WOOL, ModDecorativeBlocks.WHITE_CURTAIN);
                createCurtainRecipe(recipeOutput, Blocks.YELLOW_WOOL, ModDecorativeBlocks.YELLOW_CURTAIN);
                
                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.ROPE_LADDER, 3)
                        .pattern("R R")
                        .pattern("RSR")
                        .pattern("R R")
                        .define('R', ModDecorativeBlocks.ROPE)
                        .define('S', Items.STRING)
                        .unlockedBy(getHasName(ModDecorativeBlocks.ROPE),
                                has(ModDecorativeBlocks.ROPE))
                        .save(recipeOutput);

                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.FANCY_BED, 1)
                        .pattern("FFW")
                        .pattern("FFW")
                        .pattern("PPP")
                        .define('W', TagKey.create(Registries.ITEM, ResourceLocation.parse("wool")))
                        .define('F', ResourceItemsME.FABRIC)
                        .define('P', TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .unlockedBy(getHasName(ResourceItemsME.FABRIC),
                                has(ResourceItemsME.FABRIC))
                        .save(recipeOutput);

                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.STRAW_BED, 1)
                        .pattern("SSS")
                        .pattern("PPP")
                        .define('S', ResourceItemsME.STRAW)
                        .define('P', TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .unlockedBy(getHasName(ResourceItemsME.STRAW),
                                has(ResourceItemsME.STRAW))
                        .save(recipeOutput);

                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.FUR_BED, 1)
                        .pattern("FFF")
                        .pattern("PPP")
                        .define('F', ResourceItemsME.FUR)
                        .define('P', TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .unlockedBy(getHasName(ResourceItemsME.FUR),
                                has(ResourceItemsME.FUR))
                        .save(recipeOutput);

                createCenterSurroundRecipe(recipeOutput, ResourceItemsME.SILVER_NUGGET, Items.TORCH, DecorativeItemsME.SILVER_LANTERN, 1);
                createCenterSurroundRecipe(recipeOutput, ResourceItemsME.KHAZAD_STEEL_NUGGET, Items.TORCH, DecorativeItemsME.DWARVEN_LANTERN, 1);
                createCenterSurroundRecipe(recipeOutput, ResourceItemsME.EDHEL_STEEL_NUGGET, Items.TORCH, DecorativeItemsME.ELVEN_LANTERN, 1);
                createCenterSurroundRecipe(recipeOutput, ResourceItemsME.STEEL_NUGGET, Items.TORCH, DecorativeItemsME.TREATED_STEEL_LANTERN, 1);
                createCenterSurroundRecipe(recipeOutput, ResourceItemsME.CRUDE_NUGGET, Items.TORCH, DecorativeItemsME.CRUDE_LANTERN, 1);
                createCenterSurroundRecipe(recipeOutput, ResourceItemsME.LEAD_NUGGET, Items.TORCH, DecorativeItemsME.LEAD_LANTERN, 1);

                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, DecorativeItemsME.CRYSTAL_LAMP, 1)
                        .pattern("NGN")
                        .pattern("GLG")
                        .pattern("NIN")
                        .define('N', ResourceItemsME.BRONZE_NUGGET)
                        .define('I', ResourceItemsME.BRONZE_INGOT)
                        .define('L', TagKey.create(Registries.ITEM, ResourceLocation.parse("candles")))
                        .define('G', ResourceItemsME.QUARTZ_SHARD)
                        .unlockedBy(getHasName(ResourceItemsME.QUARTZ_SHARD),
                                has(ResourceItemsME.QUARTZ_SHARD))
                        .save(recipeOutput);

                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, DecorativeItemsME.SCONCE, 4)
                        .pattern("NTN")
                        .pattern(" I ")
                        .define('N', TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "steel_nuggets")))
                        .define('I', TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "steel_ingots")))
                        .define('T', Items.TORCH)
                        .unlockedBy(getHasName(Items.TORCH),
                                has(Items.TORCH))
                        .save(recipeOutput);

                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, DecorativeItemsME.GILDED_SCONCE, 4)
                        .pattern("NTN")
                        .pattern(" I ")
                        .define('N', Items.GOLD_NUGGET)
                        .define('I', Items.GOLD_INGOT)
                        .define('T', Items.TORCH)
                        .unlockedBy(getHasName(Items.TORCH),
                                has(Items.TORCH))
                        .save(recipeOutput);

                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, DecorativeItemsME.ORCISH_SCONCE, 2)
                        .pattern("NTN")
                        .pattern(" S ")
                        .define('N', ResourceItemsME.CRUDE_NUGGET)
                        .define('S', Items.STICK)
                        .define('T', Items.TORCH)
                        .unlockedBy(getHasName(Items.TORCH),
                                has(Items.TORCH))
                        .save(recipeOutput);

                createWoodStoolRecipe(recipeOutput, GenericBlockSets.TREATED_WOOD_PLANKS.blockSet.base().asItem(), ModDecorativeBlocks.TREATED_WOOD_STOOL);
                createWoodBenchRecipe(recipeOutput, GenericBlockSets.TREATED_WOOD_PLANKS.blockSet.base().asItem(), ModDecorativeBlocks.TREATED_WOOD_BENCH);
                createWoodTableRecipe(recipeOutput, GenericBlockSets.TREATED_WOOD_PLANKS.blockSet.base().asItem(), ModDecorativeBlocks.TREATED_WOOD_TABLE);
                createWoodChairRecipe(recipeOutput, GenericBlockSets.TREATED_WOOD_PLANKS.blockSet.base().asItem(), ModDecorativeBlocks.TREATED_WOOD_CHAIR);
                createWoodLadderRecipe(recipeOutput, GenericBlockSets.TREATED_WOOD_PLANKS.blockSet.base().asItem(), ModDecorativeBlocks.TREATED_WOOD_LADDER);

                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.LARCH_HOBBIT_DOOR, 1)
                        .pattern("LLL")
                        .pattern("LSL")
                        .pattern("LLL")
                        .define('S', TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "steel_ingots")))
                        .define('L', WoodBlockSets.LARCH_SET.planksBlocks.base())
                        .unlockedBy(getHasName(WoodBlockSets.LARCH_SET.planksBlocks.base()),
                                has(WoodBlockSets.LARCH_SET.planksBlocks.base()))
                        .save(recipeOutput);

                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.SPRUCE_HOBBIT_DOOR, 1)
                        .pattern("LSL")
                        .pattern("SLL")
                        .pattern("LSL")
                        .define('S', TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "steel_ingots")))
                        .define('L', Items.SPRUCE_PLANKS)
                        .unlockedBy(getHasName(Items.SPRUCE_PLANKS),
                                has(Items.SPRUCE_PLANKS))
                        .save(recipeOutput);

                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.BLUE_HOBBIT_DOOR, 1)
                        .pattern(" BG")
                        .pattern("BDG")
                        .pattern(" BG")
                        .define('D', ModDecorativeBlocks.LARCH_HOBBIT_DOOR)
                        .define('B', Items.BLUE_DYE)
                        .define('G', Items.GOLD_NUGGET)
                        .unlockedBy(getHasName(ModDecorativeBlocks.LARCH_HOBBIT_DOOR),
                                has(ModDecorativeBlocks.LARCH_HOBBIT_DOOR))
                        .save(recipeOutput);

                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.GREEN_HOBBIT_DOOR, 1)
                        .pattern(" BG")
                        .pattern("BDG")
                        .pattern(" BG")
                        .define('D', ModDecorativeBlocks.LARCH_HOBBIT_DOOR)
                        .define('B', Items.GREEN_DYE)
                        .define('G', Items.GOLD_NUGGET)
                        .unlockedBy(getHasName(ModDecorativeBlocks.LARCH_HOBBIT_DOOR),
                                has(ModDecorativeBlocks.LARCH_HOBBIT_DOOR))
                        .save(recipeOutput);

                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.LIGHT_BLUE_HOBBIT_DOOR, 1)
                        .pattern(" B ")
                        .pattern("BDB")
                        .pattern(" B ")
                        .define('D', ModDecorativeBlocks.LARCH_HOBBIT_DOOR)
                        .define('B', Items.LIGHT_BLUE_DYE)
                        .unlockedBy(getHasName(ModDecorativeBlocks.LARCH_HOBBIT_DOOR),
                                has(ModDecorativeBlocks.LARCH_HOBBIT_DOOR))
                        .save(recipeOutput);

                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.RED_HOBBIT_DOOR, 1)
                        .pattern(" BG")
                        .pattern("BDG")
                        .pattern(" BG")
                        .define('D', ModDecorativeBlocks.LARCH_HOBBIT_DOOR)
                        .define('B', Items.RED_DYE)
                        .define('G', Items.GOLD_NUGGET)
                        .unlockedBy(getHasName(ModDecorativeBlocks.LARCH_HOBBIT_DOOR),
                                has(ModDecorativeBlocks.LARCH_HOBBIT_DOOR))
                        .save(recipeOutput);

                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.YELLOW_HOBBIT_DOOR, 1)
                        .pattern(" BG")
                        .pattern("BDG")
                        .pattern(" BG")
                        .define('D', ModDecorativeBlocks.LARCH_HOBBIT_DOOR)
                        .define('B', Items.YELLOW_DYE)
                        .define('G', Items.GOLD_NUGGET)
                        .unlockedBy(getHasName(ModDecorativeBlocks.LARCH_HOBBIT_DOOR),
                                has(ModDecorativeBlocks.LARCH_HOBBIT_DOOR))
                        .save(recipeOutput);

                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.TALL_BLACK_PINE_DOOR, 1)
                        .pattern("SP")
                        .pattern("PP")
                        .pattern("SP")
                        .define('S', TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "steel_nuggets")))
                        .define('P', WoodBlockSets.BLACK_PINE_SET.planksBlocks.base())
                        .unlockedBy(getHasName(WoodBlockSets.BLACK_PINE_SET.planksBlocks.base()),
                                has(WoodBlockSets.BLACK_PINE_SET.planksBlocks.base()))
                        .save(recipeOutput);
                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.TALL_FIR_DOOR, 1)
                        .pattern("SP")
                        .pattern("PP")
                        .pattern("SP")
                        .define('S', TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "steel_nuggets")))
                        .define('P', WoodBlockSets.FIR_SET.planksBlocks.base())
                        .unlockedBy(getHasName(WoodBlockSets.FIR_SET.planksBlocks.base()),
                                has(WoodBlockSets.FIR_SET.planksBlocks.base()))
                        .save(recipeOutput);

                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.OAK_STABLE_DOOR, 1)
                        .pattern("SPP")
                        .pattern("PPP")
                        .pattern("SPP")
                        .define('S', TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "steel_nuggets")))
                        .define('P', Items.OAK_PLANKS)
                        .unlockedBy(getHasName(Items.OAK_PLANKS),
                                has(Items.OAK_PLANKS))
                        .save(recipeOutput);

                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.REINFORCED_BLACK_PINE_DOOR, 1)
                        .pattern("SPP")
                        .pattern("SPS")
                        .pattern("SPP")
                        .define('S', TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "steel_ingots")))
                        .define('P', WoodBlockSets.BLACK_PINE_SET.planksBlocks.base())
                        .unlockedBy(getHasName(WoodBlockSets.BLACK_PINE_SET.planksBlocks.base()),
                                has(WoodBlockSets.BLACK_PINE_SET.planksBlocks.base()))
                        .save(recipeOutput);

                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.REINFORCED_SPRUCE_DOOR, 1)
                        .pattern("SPP")
                        .pattern("SPS")
                        .pattern("SPP")
                        .define('S', TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "steel_ingots")))
                        .define('P', Items.SPRUCE_PLANKS)
                        .unlockedBy(getHasName(Items.SPRUCE_PLANKS),
                                has(Items.SPRUCE_PLANKS))
                        .save(recipeOutput);

                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.SIMPLE_LARCH_GATE, 1)
                        .pattern("SPP")
                        .pattern("PPP")
                        .pattern("SPP")
                        .define('S', TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "steel_nuggets")))
                        .define('P', WoodBlockSets.LARCH_SET.planksBlocks.base())
                        .unlockedBy(getHasName(WoodBlockSets.LARCH_SET.planksBlocks.base()),
                                has(WoodBlockSets.LARCH_SET.planksBlocks.base()))
                        .save(recipeOutput);

                stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.RICKETY_SIMPLE_LARCH_DOOR, ModDecorativeBlocks.SIMPLE_LARCH_GATE);

                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.SPRUCE_STABLE_DOOR, 1)
                        .pattern("SPP")
                        .pattern("PPP")
                        .pattern("SPP")
                        .define('S', TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "steel_nuggets")))
                        .define('P', Items.SPRUCE_PLANKS)
                        .unlockedBy(getHasName(Items.SPRUCE_PLANKS),
                                has(Items.SPRUCE_PLANKS))
                        .save(recipeOutput);

                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.LARGE_STURDY_DOOR, 1)
                        .pattern("SPP")
                        .pattern("PPP")
                        .pattern("SPP")
                        .define('S', TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "steel_ingots")))
                        .define('P', TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .unlockedBy(getHasName(Items.OAK_PLANKS),
                                has(Items.OAK_PLANKS))
                        .save(recipeOutput);

                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.LARGE_BEECH_FENCE_GATE, 1)
                        .pattern("FF")
                        .define('F', WoodBlockSets.BEECH_SET.planksBlocks.gate())
                        .unlockedBy(getHasName(WoodBlockSets.BEECH_SET.planksBlocks.gate()),
                                has(WoodBlockSets.BEECH_SET.planksBlocks.gate()))
                        .save(recipeOutput);

                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.GREAT_GONDORIAN_GATE, 1)
                        .pattern("LCL")
                        .pattern("CCS")
                        .pattern("LCL")
                        .define('L', WoodBlockSets.BLACK_LEBETHRON_SET.planksBlocks.base())
                        .define('C', Items.OXIDIZED_COPPER)
                        .define('S', ResourceItemsME.STEEL_INGOT)
                        .unlockedBy(getHasName(Items.OXIDIZED_COPPER),
                                has(Items.OXIDIZED_COPPER))
                        .save(recipeOutput);

                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.GREAT_DWARVEN_GATE, 1)
                        .pattern("BTB")
                        .pattern("BTS")
                        .pattern("BTB")
                        .define('B', ResourceItemsME.BRONZE_INGOT)
                        .define('T', GenericBlockSets.TREATED_WOOD.blockSet.base())
                        .define('S', ResourceItemsME.KHAZAD_STEEL_INGOT)
                        .unlockedBy(getHasName(ResourceItemsME.BRONZE_INGOT),
                                has(ResourceItemsME.BRONZE_INGOT))
                        .save(recipeOutput);

                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.VARNISHED_DWARVEN_DOOR, 1)
                        .pattern("TNT")
                        .pattern("TTS")
                        .pattern("TNT")
                        .define('N', ResourceItemsME.STEEL_NUGGET)
                        .define('T', GenericBlockSets.TREATED_WOOD.blockSet.base())
                        .define('S', ResourceItemsME.KHAZAD_STEEL_INGOT)
                        .unlockedBy(getHasName(ResourceItemsME.BRONZE_INGOT),
                                has(ResourceItemsME.BRONZE_INGOT))
                        .save(recipeOutput);

                stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.RUINED_DWARVEN_DOOR, ModDecorativeBlocks.VARNISHED_DWARVEN_DOOR);

                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.HIDDEN_DWARVEN_DOOR, 1)
                        .pattern("SSG")
                        .pattern("GDL")
                        .pattern("DSS")
                        .define('L', Items.LEVER)
                        .define('G', StoneBlockSets.DOLOMITE_SET.smoothBlocks.base())
                        .define('D', StoneBlockSets.DOLOMITE_SET.baseBlocks.base())
                        .define('S', Items.STONE)
                        .unlockedBy(getHasName(StoneBlockSets.DOLOMITE_SET.smoothBlocks.base()),
                                has(StoneBlockSets.DOLOMITE_SET.smoothBlocks.base()))
                        .save(recipeOutput);

                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.GREAT_ELVEN_GATE, 1)
                        .pattern("BTB")
                        .pattern("BTS")
                        .pattern("BTB")
                        .define('B', Items.CYAN_DYE)
                        .define('T', GenericBlockSets.TREATED_WOOD.blockSet.base())
                        .define('S', ResourceItemsME.EDHEL_STEEL_INGOT)
                        .unlockedBy(getHasName(GenericBlockSets.TREATED_WOOD.blockSet.base()),
                                has(GenericBlockSets.TREATED_WOOD.blockSet.base()))
                        .save(recipeOutput);

                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.GREAT_ORCISH_GATE, 1)
                        .pattern("SSS")
                        .pattern("SNS")
                        .pattern("NNN")
                        .define('N', ModBlocks.BURZUM_STEEL_BLOCK)
                        .define('S', ResourceItemsME.BURZUM_STEEL_INGOT)
                        .unlockedBy(getHasName(ResourceItemsME.BURZUM_STEEL_INGOT),
                                has(ResourceItemsME.BURZUM_STEEL_INGOT))
                        .save(recipeOutput);

                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.TURF, 4)
                        .pattern("MM")
                        .pattern("MD")
                        .define('M', Items.MOSS_BLOCK)
                        .define('D', Items.DIRT)
                        .unlockedBy(getHasName(Items.MOSS_BLOCK),
                                has(Items.MOSS_BLOCK))
                        .save(recipeOutput);

                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS,
                                ModNatureBlocks.CORRUPTED_MOSS_CARPET, 3)
                        .pattern("MM")
                        .define('M', ModNatureBlocks.CORRUPTED_MOSS_BLOCK)
                        .unlockedBy(getHasName(ModNatureBlocks.CORRUPTED_MOSS_BLOCK),
                                has(ModNatureBlocks.CORRUPTED_MOSS_BLOCK))
                        .save(recipeOutput);

                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS,
                                ModNatureBlocks.FOREST_MOSS_CARPET, 3)
                        .pattern("MM")
                        .define('M', ModNatureBlocks.FOREST_MOSS_BLOCK)
                        .unlockedBy(getHasName(ModNatureBlocks.FOREST_MOSS_BLOCK),
                                has(ModNatureBlocks.FOREST_MOSS_BLOCK))
                        .save(recipeOutput);

                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.GRASSY_DIRT, 4)
                        .pattern("DM")
                        .pattern("MD")
                        .define('M', Items.MOSS_BLOCK)
                        .define('D', Items.DIRT)
                        .unlockedBy(getHasName(Items.MOSS_BLOCK),
                                has(Items.MOSS_BLOCK))
                        .save(recipeOutput);

                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.GRASSY_CHALKSOIL, 4)
                        .pattern("DM")
                        .pattern("MD")
                        .define('M', Items.MOSS_BLOCK)
                        .define('D', ModBlocks.CHALKSOIL)
                        .unlockedBy(getHasName(Items.MOSS_BLOCK),
                                has(Items.MOSS_BLOCK))
                        .save(recipeOutput);

                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.GRASSY_SILT, 4)
                        .pattern("DM")
                        .pattern("MD")
                        .define('M', Items.MOSS_BLOCK)
                        .define('D', ModBlocks.SILT)
                        .unlockedBy(getHasName(Items.MOSS_BLOCK),
                                has(Items.MOSS_BLOCK))
                        .save(recipeOutput);

                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.GRASSY_LOAM, 4)
                        .pattern("DM")
                        .pattern("MD")
                        .define('M', Items.MOSS_BLOCK)
                        .define('D', ModBlocks.LOAM)
                        .unlockedBy(getHasName(Items.MOSS_BLOCK),
                                has(Items.MOSS_BLOCK))
                        .save(recipeOutput);

                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.GRASSY_PEAT, 4)
                        .pattern("DM")
                        .pattern("MD")
                        .define('M', Items.MOSS_BLOCK)
                        .define('D', ModBlocks.LOAM)
                        .unlockedBy(getHasName(Items.MOSS_BLOCK),
                                has(Items.MOSS_BLOCK))
                        .save(recipeOutput);

                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.PEBBLED_GRASS, 4)
                        .pattern("DM")
                        .pattern("MD")
                        .define('M', Items.MOSS_BLOCK)
                        .define('D', TagKey.create(Registries.ITEM, ResourceLocation.parse("stone_crafting_materials")))
                        .unlockedBy(getHasName(Items.MOSS_BLOCK),
                                has(Items.MOSS_BLOCK))
                        .save(recipeOutput);

                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.WASTE_PILE, 8)
                        .pattern("DDD")
                        .pattern("DWD")
                        .pattern("DDD")
                        .define('W', Items.ROTTEN_FLESH)
                        .define('D', ItemTagsME.DIRT)
                        .unlockedBy(getHasName(Items.MOSS_BLOCK),
                                has(Items.MOSS_BLOCK))
                        .save(recipeOutput);

                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.SKELETAL_PILE, 8)
                        .pattern("DDD")
                        .pattern("DBD")
                        .pattern("DDD")
                        .define('B', ItemTagsME.BONES)
                        .define('D', ModBlocks.WASTE_PILE)
                        .unlockedBy(getHasName(ModBlocks.WASTE_PILE),
                                has(ModBlocks.WASTE_PILE))
                        .save(recipeOutput);

                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.FOUL_DIRT, 8)
                        .pattern("DDD")
                        .pattern("DAD")
                        .pattern("DDD")
                        .define('A', ResourceItemsME.ASH)
                        .define('D', ModBlocks.WASTE_PILE)
                        .unlockedBy(getHasName(ModBlocks.WASTE_PILE),
                                has(ModBlocks.WASTE_PILE))
                        .save(recipeOutput);

                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.SNOWY_DIRT, 4)
                        .pattern("DS")
                        .pattern("SD")
                        .define('D', Items.DIRT)
                        .define('S', Items.SNOW_BLOCK)
                        .unlockedBy(getHasName(Items.DIRT),
                                has(Items.DIRT))
                        .save(recipeOutput);

                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, StoneBlockSets.DRYSTONE_SET.cobblestoneBlocks.base(), 2)
                        .pattern("CC")
                        .define('C', TagKey.create(Registries.ITEM, ResourceLocation.parse("stone_crafting_materials")))
                        .unlockedBy(getHasName(Items.COBBLESTONE),
                                has(Items.COBBLESTONE))
                        .save(recipeOutput);
                //createMossyRecipe(exporter, StoneBlockSets.DRYSTONE_SET.cobblestoneBlocks.base(), StoneBlockSets.DRYSTONE_SET.mossyCobblestoneBlocks.base());
                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, GenericBlockSets.FRAMED_DRYSTONE.blockSet.base(), 1)
                        .pattern(" S ")
                        .pattern("SCS")
                        .pattern(" S ")
                        .define('S', Items.STICK)
                        .define('C', TagKey.create(Registries.ITEM, ResourceLocation.parse("stone_crafting_materials")))
                        .unlockedBy(getHasName(StoneBlockSets.DRYSTONE_SET.cobblestoneBlocks.base()),
                                has(StoneBlockSets.DRYSTONE_SET.cobblestoneBlocks.base()))
                        .save(recipeOutput);

                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.COBBLY_DIRT, 4)
                        .pattern("DC")
                        .pattern("CD")
                        .define('D', Items.DIRT)
                        .define('C', TagKey.create(Registries.ITEM, ResourceLocation.parse("stone_crafting_materials")))
                        .unlockedBy(getHasName(Items.DIRT),
                                has(Items.DIRT))
                        .save(recipeOutput);

                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.COBBLY_ASHEN_DIRT, 4)
                        .pattern("DC")
                        .pattern("CD")
                        .define('D', ModBlocks.ASHEN_DIRT)
                        .define('C', StoneBlockSets.ASHENSTONE_SET.cobblestoneBlocks.base())
                        .unlockedBy(getHasName(Items.DIRT),
                                has(Items.DIRT))
                        .save(recipeOutput);

                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.DIRTY_ROOTS, 2)
                        .pattern(" R ")
                        .pattern("RDR")
                        .pattern(" R ")
                        .define('D', Items.ROOTED_DIRT)
                        .define('R', Items.HANGING_ROOTS)
                        .unlockedBy(getHasName(Items.ROOTED_DIRT),
                                has(Items.ROOTED_DIRT))
                        .save(recipeOutput);

                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.WATERING_CAN, 1)
                        .pattern(" N ")
                        .pattern("NII")
                        .pattern(" II")
                        .define('N', ResourceItemsME.TIN_NUGGET)
                        .define('I', ResourceItemsME.TIN_INGOT)
                        .unlockedBy(getHasName(ResourceItemsME.TIN_INGOT),
                                has(ResourceItemsME.TIN_INGOT))
                        .save(recipeOutput);

                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.WOODEN_BUCKET, 1)
                        .pattern(" R ")
                        .pattern("P P")
                        .pattern(" P ")
                        .define('R', ModDecorativeBlocks.ROPE)
                        .define('P', TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .unlockedBy(getHasName(ModDecorativeBlocks.ROPE),
                                has(ModDecorativeBlocks.ROPE))
                        .save(recipeOutput);

                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.CRUDE_ROD, 1)
                        .pattern("S")
                        .pattern("S")
                        .pattern("S")
                        .define('S', ResourceItemsME.CRUDE_INGOT)
                        .unlockedBy(getHasName(ResourceItemsME.CRUDE_INGOT),
                                has(ResourceItemsME.CRUDE_INGOT))
                        .save(recipeOutput);

                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.TREATED_STEEL_ROD, 1)
                        .pattern("S")
                        .pattern("S")
                        .pattern("S")
                        .define('S', TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "steel_ingots")))
                        .unlockedBy(getHasName(ResourceItemsME.STEEL_INGOT),
                                has(ResourceItemsME.STEEL_INGOT))
                        .save(recipeOutput);

                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, Items.CHAIN, 4)
                        .pattern("N")
                        .pattern("I")
                        .pattern("N")
                        .define('N', TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "steel_ingots")))
                        .define('I', TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "steel_nuggets")))
                        .unlockedBy(getHasName(ResourceItemsME.STEEL_INGOT),
                                has(ResourceItemsME.STEEL_INGOT))
                        .save(recipeOutput, String.valueOf(ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, BuiltInRegistries.ITEM.getKey(Items.CHAIN).getPath() + "_alt")));

                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.BRONZE_CHAIN, 4)
                        .pattern("N")
                        .pattern("I")
                        .pattern("N")
                        .define('N', ResourceItemsME.BRONZE_NUGGET)
                        .define('I', ResourceItemsME.BRONZE_INGOT)
                        .unlockedBy(getHasName(ResourceItemsME.BRONZE_INGOT),
                                has(ResourceItemsME.STEEL_INGOT))
                        .save(recipeOutput);

                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.BRONZE_BROAD_CHAIN, 8)
                        .pattern("NN")
                        .pattern("II")
                        .pattern("NN")
                        .define('N', ResourceItemsME.BRONZE_NUGGET)
                        .define('I', ResourceItemsME.BRONZE_INGOT)
                        .unlockedBy(getHasName(ResourceItemsME.BRONZE_INGOT),
                                has(ResourceItemsME.STEEL_INGOT))
                        .save(recipeOutput);

                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.CRUDE_CHAIN, 4)
                        .pattern("N")
                        .pattern("I")
                        .pattern("N")
                        .define('N', ResourceItemsME.CRUDE_NUGGET)
                        .define('I', ResourceItemsME.CRUDE_INGOT)
                        .unlockedBy(getHasName(ResourceItemsME.CRUDE_INGOT),
                                has(ResourceItemsME.STEEL_INGOT))
                        .save(recipeOutput);

                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.CRUDE_BROAD_CHAIN, 8)
                        .pattern("NN")
                        .pattern("II")
                        .pattern("NN")
                        .define('N', ResourceItemsME.CRUDE_NUGGET)
                        .define('I', ResourceItemsME.CRUDE_INGOT)
                        .unlockedBy(getHasName(ResourceItemsME.CRUDE_INGOT),
                                has(ResourceItemsME.STEEL_INGOT))
                        .save(recipeOutput);

                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.SPIKY_CHAIN, 4)
                        .pattern(" N ")
                        .pattern("NIN")
                        .pattern(" N ")
                        .define('I', TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "steel_ingots")))
                        .define('N', TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "steel_nuggets")))
                        .unlockedBy(getHasName(ResourceItemsME.STEEL_INGOT),
                                has(ResourceItemsME.STEEL_INGOT))
                        .save(recipeOutput);

                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ResourceItemsME.DWARVEN_KEY, 1)
                        .pattern("IN")
                        .define('N', ResourceItemsME.KHAZAD_STEEL_NUGGET)
                        .define('I', ResourceItemsME.KHAZAD_STEEL_INGOT)
                        .unlockedBy(getHasName(ResourceItemsME.KHAZAD_STEEL_INGOT),
                                has(ResourceItemsME.KHAZAD_STEEL_INGOT))
                        .save(recipeOutput);

                ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, ModBlocks.EMBERS, 1)
                        .requires(Items.MAGMA_BLOCK, 1)
                        .requires(ResourceItemsME.ASH, 1)
                        .unlockedBy(getHasName(Items.MAGMA_BLOCK),
                                has(Items.MAGMA_BLOCK))
                        .save(recipeOutput);

                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.CHIMNEY, 2)
                        .pattern(" B ")
                        .pattern(" B ")
                        .pattern("PPP")
                        .define('B', Items.BRICKS)
                        .define('P', StoneBlockSets.DOLOMITE_SET.polishedBlocks.base())
                        .unlockedBy(getHasName(Items.BRICKS),
                                has(Items.BRICKS))
                        .save(recipeOutput);

                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.BIG_BRAZIER, 2)
                        .pattern("B B")
                        .pattern("BCB")
                        .pattern("SSS")
                        .define('B', ModBlocks.TREATED_STEEL_BARS)
                        .define('C', Items.CAMPFIRE)
                        .define('S', TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "steel_ingots")))
                        .unlockedBy(getHasName(Items.CAMPFIRE),
                                has(Items.CAMPFIRE))
                        .save(recipeOutput);

                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.GILDED_BIG_BRAZIER, 2)
                        .pattern("B B")
                        .pattern("BCB")
                        .pattern("SSS")
                        .define('B', ModBlocks.GILDED_BARS)
                        .define('C', Items.CAMPFIRE)
                        .define('S', Items.GOLD_INGOT)
                        .unlockedBy(getHasName(Items.CAMPFIRE),
                                has(Items.CAMPFIRE))
                        .save(recipeOutput);

                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.SMALL_BRAZIER, 2)
                        .pattern("BCB")
                        .pattern("SSS")
                        .define('B', ModBlocks.TREATED_STEEL_BARS)
                        .define('C', Items.CAMPFIRE)
                        .define('S', TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "steel_ingots")))
                        .unlockedBy(getHasName(Items.CAMPFIRE),
                                has(Items.CAMPFIRE))
                        .save(recipeOutput);

                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.GILDED_SMALL_BRAZIER, 2)
                        .pattern("BCB")
                        .pattern("SSS")
                        .define('B', ModBlocks.GILDED_BARS)
                        .define('C', Items.CAMPFIRE)
                        .define('S', Items.GOLD_INGOT)
                        .unlockedBy(getHasName(Items.CAMPFIRE),
                                has(Items.CAMPFIRE))
                        .save(recipeOutput);

                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.FIRE_BOWL, 2)
                        .pattern("SCS")
                        .pattern("SSS")
                        .define('C', Items.CAMPFIRE)
                        .define('S', TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "steel_ingots")))
                        .unlockedBy(getHasName(Items.CAMPFIRE),
                                has(Items.CAMPFIRE))
                        .save(recipeOutput);

                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.BONFIRE, 1)
                        .pattern(" L ")
                        .pattern("LCL")
                        .define('C', Items.CAMPFIRE)
                        .define('L', TagKey.create(Registries.ITEM, ResourceLocation.parse("logs")))
                        .unlockedBy(getHasName(Items.CAMPFIRE),
                                has(Items.CAMPFIRE))
                        .save(recipeOutput);

                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.GROUND_BOOK, 1)
                        .pattern("BSR")
                        .define('B', Items.BOOK)
                        .define('S', Items.STRING)
                        .define('R', Items.RED_DYE)
                        .unlockedBy(getHasName(Items.BOOK),
                                has(Items.BOOK))
                        .save(recipeOutput);

                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.DWARVEN_GROUND_BOOK, 1)
                        .pattern("BG")
                        .define('B', Items.BOOK)
                        .define('G', Items.GOLD_NUGGET)
                        .unlockedBy(getHasName(Items.BOOK),
                                has(Items.BOOK))
                        .save(recipeOutput);

                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.SMALL_CRATE, 1)
                        .pattern("SSS")
                        .pattern("PPP")
                        .define('S', TagKey.create(Registries.ITEM, ResourceLocation.parse("wooden_slabs")))
                        .define('P', TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .unlockedBy(getHasName(Items.OAK_PLANKS),
                                has(Items.OAK_PLANKS))
                        .save(recipeOutput);

                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.THIN_BARREL, 1)
                        .pattern("VSV")
                        .pattern("V V")
                        .pattern("VSV")
                        .define('S', TagKey.create(Registries.ITEM, ResourceLocation.parse("wooden_slabs")))
                        .define('V', TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "wooden_vertical_slabs")))
                        .unlockedBy(getHasName(Items.OAK_SLAB),
                                has(Items.OAK_SLAB))
                        .save(recipeOutput);

                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.LARCH_COFFER, 1)
                        .pattern("TLT")
                        .pattern("L L")
                        .pattern("LLL")
                        .define('T', ResourceItemsME.TIN_NUGGET)
                        .define('L', WoodBlockSets.LARCH_SET.planksBlocks.base())
                        .unlockedBy(getHasName(WoodBlockSets.LARCH_SET.planksBlocks.base()),
                                has(WoodBlockSets.LARCH_SET.planksBlocks.base()))
                        .save(recipeOutput);
                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.PINE_COFFER, 1)
                        .pattern("TLT")
                        .pattern("L L")
                        .pattern("LLL")
                        .define('T', ResourceItemsME.TIN_NUGGET)
                        .define('L', WoodBlockSets.PINE_SET.planksBlocks.base())
                        .unlockedBy(getHasName(WoodBlockSets.PINE_SET.planksBlocks.base()),
                                has(WoodBlockSets.PINE_SET.planksBlocks.base()))
                        .save(recipeOutput);
                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.SPRUCE_COFFER, 1)
                        .pattern("TLT")
                        .pattern("L L")
                        .pattern("LLL")
                        .define('T', ResourceItemsME.TIN_NUGGET)
                        .define('L', Blocks.SPRUCE_PLANKS)
                        .unlockedBy(getHasName(Blocks.SPRUCE_PLANKS),
                                has(Blocks.SPRUCE_PLANKS))
                        .save(recipeOutput);
                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.FIR_COFFER, 1)
                        .pattern("TLT")
                        .pattern("L L")
                        .pattern("LLL")
                        .define('T', ResourceItemsME.TIN_NUGGET)
                        .define('L', WoodBlockSets.FIR_SET.planksBlocks.base())
                        .unlockedBy(getHasName(WoodBlockSets.FIR_SET.planksBlocks.base()),
                                has(WoodBlockSets.FIR_SET.planksBlocks.base()))
                        .save(recipeOutput);
                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.BEECH_COFFER, 1)
                        .pattern("TLT")
                        .pattern("L L")
                        .pattern("LLL")
                        .define('T', ResourceItemsME.TIN_NUGGET)
                        .define('L', WoodBlockSets.BEECH_SET.planksBlocks.base())
                        .unlockedBy(getHasName(WoodBlockSets.BEECH_SET.planksBlocks.base()),
                                has(WoodBlockSets.BEECH_SET.planksBlocks.base()))
                        .save(recipeOutput);
                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.CHESTNUT_COFFER, 1)
                        .pattern("TLT")
                        .pattern("L L")
                        .pattern("LLL")
                        .define('T', ResourceItemsME.TIN_NUGGET)
                        .define('L', WoodBlockSets.CHESTNUT_SET.planksBlocks.base())
                        .unlockedBy(getHasName(WoodBlockSets.CHESTNUT_SET.planksBlocks.base()),
                                has(WoodBlockSets.CHESTNUT_SET.planksBlocks.base()))
                        .save(recipeOutput);
                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.OAK_COFFER, 1)
                        .pattern("TLT")
                        .pattern("L L")
                        .pattern("LLL")
                        .define('T', ResourceItemsME.TIN_NUGGET)
                        .define('L', WoodBlockSets.OAK_SET.planksBlocks.base())
                        .unlockedBy(getHasName(WoodBlockSets.OAK_SET.planksBlocks.base()),
                                has(WoodBlockSets.OAK_SET.planksBlocks.base()))
                        .save(recipeOutput);
                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.WILLOW_COFFER, 1)
                        .pattern("TLT")
                        .pattern("L L")
                        .pattern("LLL")
                        .define('T', ResourceItemsME.TIN_NUGGET)
                        .define('L', WoodBlockSets.WILLOW_SET.planksBlocks.base())
                        .unlockedBy(getHasName(WoodBlockSets.WILLOW_SET.planksBlocks.base()),
                                has(WoodBlockSets.WILLOW_SET.planksBlocks.base()))
                        .save(recipeOutput);

                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.SACK, 1)
                        .pattern("C C")
                        .pattern("CRC")
                        .pattern("CCC")
                        .define('C', GenericBlockSets.CANVAS.blockSet.base())
                        .define('R', ModNatureBlocks.RESIN_CLUMP)
                        .unlockedBy(getHasName(ModNatureBlocks.RESIN_CLUMP),
                                has(ModNatureBlocks.RESIN_CLUMP))
                        .save(recipeOutput);

                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, Items.BELL, 1)
                        .pattern("VSV")
                        .pattern("VGV")
                        .define('S', Items.STICK)
                        .define('V', StoneBlockSets.STONE_SET.baseBlocks.verticalSlab())
                        .define('G', Items.GOLD_INGOT)
                        .unlockedBy(getHasName(Items.GOLD_INGOT),
                                has(Items.GOLD_INGOT))
                        .save(recipeOutput);

                ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, ModNatureBlocks.STICKY_SNOW, 8)
                        .requires(Items.SNOWBALL, 8)
                        .requires(Items.WATER_BUCKET, 1)
                        .unlockedBy(getHasName(Items.SNOWBALL),
                                has(Items.SNOWBALL))
                        .save(recipeOutput);

                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModNatureBlocks.STICKY_ICE, 4)
                        .pattern("II")
                        .pattern("II")
                        .define('I', Items.ICE)
                        .unlockedBy(getHasName(Items.ICE),
                                has(Items.ICE))
                        .save(recipeOutput);

                createBannerPatternRecipe(recipeOutput, ResourceItemsME.PIPEWEED, ResourceItemsME.PIPEWEED_BANNER_PATTERN);
                createBannerPatternRecipe(recipeOutput, ModNatureBlocks.LEBETHRON_SAPLING.asItem(), ResourceItemsME.GONDOR_BANNER_PATTERN);
                createBannerPatternRecipe(recipeOutput, ModNatureBlocks.MALLORN_SAPLING.asItem(), ResourceItemsME.LOTHLORIEN_BANNER_PATTERN);
                createBannerPatternRecipe(recipeOutput, Items.MAGMA_BLOCK, ResourceItemsME.MORDOR_BANNER_PATTERN);
                createBannerPatternRecipe(recipeOutput, Items.HAY_BLOCK, ResourceItemsME.ROHAN_BANNER_PATTERN);
                createBannerPatternRecipe(recipeOutput, Items.BONE, ResourceItemsME.MISTY_MOUNTAINS_ORCS_BANNER_PATTERN);
                createBannerPatternRecipe(recipeOutput, Items.BONE_BLOCK, ResourceItemsME.GOBLIN_SKULL_BANNER_PATTERN);
                createBannerPatternRecipe(recipeOutput, Items.SKELETON_SKULL, ResourceItemsME.SCREECHING_SKULL_BANNER_PATTERN);
                createBannerPatternRecipe(recipeOutput, Items.WHITE_DYE, ResourceItemsME.ISENGARD_BANNER_PATTERN);
                createBannerPatternRecipe(recipeOutput, ToolItemsME.DWARVEN_SMITHING_HAMMER, ResourceItemsME.ANVIL_BANNER_PATTERN);
                createBannerPatternRecipe(recipeOutput, ResourceItemsME.BRONZE_INGOT, ResourceItemsME.BELL_BANNER_PATTERN);
                createBannerPatternRecipe(recipeOutput, Items.GOLD_NUGGET, ResourceItemsME.DWARF_CROWN_BANNER_PATTERN);
                createBannerPatternRecipe(recipeOutput, Items.SPIDER_EYE, ResourceItemsME.SPIDER_BANNER_PATTERN);
                createBannerPatternRecipe(recipeOutput, Items.BOW, ResourceItemsME.BOW_BANNER_PATTERN);
                createBannerPatternRecipe(recipeOutput, Items.OAK_LEAVES, ResourceItemsME.OAK_LEAF_BANNER_PATTERN);

                createBrickRecipe(recipeOutput, ModBlocks.POINTED_DOLOMITE.asItem(), StoneBlockSets.DOLOMITE_SET.baseBlocks.base(), 1);
                createBrickRecipe(recipeOutput, ModBlocks.POINTED_GALONN.asItem(), StoneBlockSets.GALONN_SET.baseBlocks.base(), 1);
                createBrickRecipe(recipeOutput, ModBlocks.POINTED_IZHERABAN.asItem(), StoneBlockSets.IZHERABAN_SET.baseBlocks.base(), 1);
                createBrickRecipe(recipeOutput, ModBlocks.POINTED_LIMESTONE.asItem(), StoneBlockSets.LIMESTONE_SET.baseBlocks.base(), 1);

                SimpleCookingRecipeBuilder.smoking(Ingredient.of(ItemTags.PLANKS), RecipeCategory.BUILDING_BLOCKS, WoodBlockSets.SCORCHED_SET.planksBlocks.base(), 0.0f, 100)
                        .unlockedBy(getHasName(Items.OAK_PLANKS),
                                has(Items.OAK_PLANKS)).save(recipeOutput, String.valueOf(ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, BuiltInRegistries.BLOCK.getKey(WoodBlockSets.SCORCHED_SET.planksBlocks.base()).getPath() + "_from_smoking")));
                SimpleCookingRecipeBuilder.smoking(Ingredient.of(ItemTags.LOGS), RecipeCategory.BUILDING_BLOCKS, WoodBlockSets.SCORCHED_SET.logBlocks.log(), 0.0f, 100)
                        .unlockedBy(getHasName(Items.OAK_LOG),
                                has(Items.OAK_LOG)).save(recipeOutput, String.valueOf(ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, BuiltInRegistries.BLOCK.getKey(WoodBlockSets.SCORCHED_SET.logBlocks.log()).getPath() + "_from_smoking")));

                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModNatureBlocks.SHORT_ICICLES, 4)
                        .pattern("III")
                        .pattern(" I ")
                        .define('I', Items.ICE)
                        .unlockedBy(getHasName(Items.ICE),
                                has(Items.ICE))
                        .save(recipeOutput);

                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModNatureBlocks.DROOPING_ICICLES, 4)
                        .pattern("III")
                        .pattern("III")
                        .pattern(" I ")
                        .define('I', Items.ICE)
                        .unlockedBy(getHasName(Items.ICE),
                                has(Items.ICE))
                        .save(recipeOutput);

                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, Items.BUCKET, 1)
                        .pattern("T T")
                        .pattern("T T")
                        .pattern(" T ")
                        .define('T', ResourceItemsME.TIN_INGOT)
                        .unlockedBy(getHasName(ResourceItemsME.TIN_INGOT),
                                has(ResourceItemsME.TIN_INGOT))
                        .save(recipeOutput, String.valueOf(ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, BuiltInRegistries.ITEM.getKey(Items.BUCKET).getPath() + "_alt")));

                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, Items.CAULDRON, 1)
                        .pattern("T T")
                        .pattern("T T")
                        .pattern("TBT")
                        .define('T', ResourceItemsME.TIN_INGOT)
                        .define('B', ModBlocks.TIN_BLOCK)
                        .unlockedBy(getHasName(ResourceItemsME.TIN_INGOT),
                                has(ResourceItemsME.TIN_INGOT))
                        .save(recipeOutput, String.valueOf(ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, BuiltInRegistries.ITEM.getKey(Items.CAULDRON).getPath() + "_alt")));

                createCenterSurroundRecipe(recipeOutput, Blocks.TUFF.asItem(), Items.RAW_COPPER, StoneBlockSets.GREEN_TUFF_SET.baseBlocks.base().asItem(), 8);
                createCenterSurroundRecipe(recipeOutput, StoneBlockSets.SLATE_SET.baseBlocks.base().asItem(), Items.RAW_COPPER, StoneBlockSets.KHAGALABAN_SET.baseBlocks.base().asItem(), 8);
                createCenterSurroundRecipe(recipeOutput, Blocks.TUFF.asItem(), Items.IRON_NUGGET, StoneBlockSets.IRONSTONE_SET.baseBlocks.base().asItem(), 8);

                stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.BROWN_JUG, Items.CLAY);
                stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.LARGE_JUG, Items.CLAY);
                stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.GRAY_POT, Items.CLAY);

                stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.BROWN_JAR, Items.CLAY);
                stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.CLAY_JAR, Items.CLAY);
                stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.GRAY_JAR, Items.CLAY);

                stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.AMPHORA, Items.CLAY);
                stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.BROWN_AMPHORA, Items.CLAY);
                stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.GRAY_VASE, Items.CLAY);

                stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.BROWN_FAT_POT, Items.CLAY);
                stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.FAT_POT, Items.CLAY);
                stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.GRAY_FAT_POT, Items.CLAY);

                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.POT_OF_GOLD, 1)
                        .pattern(" G ")
                        .pattern("GGG")
                        .pattern(" P ")
                        .define('P', ModDecorativeBlocks.FAT_POT)
                        .define('G', ResourceItemsME.GOLD_COIN)
                        .unlockedBy(getHasName(ResourceItemsME.GOLD_COIN),
                                has(ResourceItemsME.GOLD_COIN))
                        .save(recipeOutput);

                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModNatureBlocks.AZALEA_FLOWER_GROWTH.asItem(), 8)
                        .pattern("lll")
                        .pattern("lll")
                        .define('l', Items.FLOWERING_AZALEA_LEAVES)
                        .unlockedBy(getHasName(Items.FLOWERING_AZALEA_LEAVES),
                                has(Items.FLOWERING_AZALEA_LEAVES))
                        .save(recipeOutput);
                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModNatureBlocks.DRY_GROWTH.asItem(), 4)
                        .pattern("sss")
                        .pattern("sss")
                        .define('s', Items.STICK)
                        .unlockedBy(getHasName(Items.STICK),
                                has(Items.STICK))
                        .save(recipeOutput);
                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModNatureBlocks.THORNY_GROWTH.asItem(), 6)
                        .pattern("sls")
                        .pattern("sls")
                        .define('s', Items.STICK)
                        .define('l', FoodItemsME.TOUGH_BERRIES)
                        .unlockedBy(getHasName(FoodItemsME.TOUGH_BERRIES),
                                has(FoodItemsME.TOUGH_BERRIES))
                        .save(recipeOutput);
                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModNatureBlocks.GREEN_GROWTH.asItem(), 8)
                        .pattern("lll")
                        .pattern("lll")
                        .define('l', TagKey.create(Registries.ITEM, ResourceLocation.parse("leaves")))
                        .unlockedBy(getHasName(Items.OAK_LEAVES),
                                has(Items.OAK_LEAVES))
                        .save(recipeOutput);
                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModNatureBlocks.IVY_GROWTH.asItem(), 6)
                        .pattern("sls")
                        .pattern("sls")
                        .define('s', Items.STICK)
                        .define('l', TagKey.create(Registries.ITEM, ResourceLocation.parse("leaves")))
                        .unlockedBy(getHasName(Items.OAK_LEAVES),
                                has(Items.OAK_LEAVES))
                        .save(recipeOutput);
                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModNatureBlocks.LILAC_FLOWER_GROWTH.asItem(), 8)
                        .pattern("lfl")
                        .pattern("lfl")
                        .define('f', Items.LILAC)
                        .define('l', TagKey.create(Registries.ITEM, ResourceLocation.parse("leaves")))
                        .unlockedBy(getHasName(Items.OAK_LEAVES),
                                has(Items.OAK_LEAVES))
                        .save(recipeOutput);
                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModNatureBlocks.PINK_FLOWER_GROWTH.asItem(), 8)
                        .pattern("lfl")
                        .pattern("lfl")
                        .define('f', ModNatureBlocks.PINK_FLOWERS)
                        .define('l', TagKey.create(Registries.ITEM, ResourceLocation.parse("leaves")))
                        .unlockedBy(getHasName(ModNatureBlocks.PINK_FLOWERS),
                                has(ModNatureBlocks.PINK_FLOWERS))
                        .save(recipeOutput);
                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModNatureBlocks.RED_FLOWER_GROWTH.asItem(), 8)
                        .pattern("lfl")
                        .pattern("lfl")
                        .define('f', ModNatureBlocks.RED_FLOWERS)
                        .define('l', TagKey.create(Registries.ITEM, ResourceLocation.parse("leaves")))
                        .unlockedBy(getHasName(ModNatureBlocks.RED_FLOWERS),
                                has(ModNatureBlocks.RED_FLOWERS))
                        .save(recipeOutput);
                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModNatureBlocks.WHITE_FLOWER_GROWTH.asItem(), 8)
                        .pattern("lfl")
                        .pattern("lfl")
                        .define('f', ModNatureBlocks.WHITE_FLOWERS)
                        .define('l', TagKey.create(Registries.ITEM, ResourceLocation.parse("leaves")))
                        .unlockedBy(getHasName(ModNatureBlocks.WHITE_FLOWERS),
                                has(ModNatureBlocks.WHITE_FLOWERS))
                        .save(recipeOutput);
                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModNatureBlocks.YELLOW_FLOWER_GROWTH.asItem(), 8)
                        .pattern("lfl")
                        .pattern("lfl")
                        .define('f', ModNatureBlocks.YELLOW_FLOWERS)
                        .define('l', TagKey.create(Registries.ITEM, ResourceLocation.parse("leaves")))
                        .unlockedBy(getHasName(ModNatureBlocks.YELLOW_FLOWERS),
                                has(ModNatureBlocks.YELLOW_FLOWERS))
                        .save(recipeOutput);

                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModNatureBlocks.FROZEN_GROWTH.asItem(), 8)
                        .pattern("sis")
                        .pattern("sis")
                        .define('i', ModNatureBlocks.STICKY_SNOW)
                        .define('s', ModNatureBlocks.DRY_GROWTH)
                        .unlockedBy(getHasName(ModNatureBlocks.DRY_GROWTH),
                                has(ModNatureBlocks.DRY_GROWTH))
                        .save(recipeOutput);

                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.GOLDEN_CHALICE, 1)
                        .pattern("I")
                        .pattern("N")
                        .pattern("N")
                        .define('I', Items.GOLD_INGOT)
                        .define('N', Items.GOLD_NUGGET)
                        .unlockedBy(getHasName(Items.GOLD_INGOT),
                                has(Items.GOLD_INGOT))
                        .save(recipeOutput);

                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.COPPER_TREASURE_HEAP_LAYER, 1)
                        .pattern("NNN")
                        .define('N', ResourceItemsME.COPPER_COIN)
                        .unlockedBy(getHasName(ResourceItemsME.COPPER_COIN),
                                has(ResourceItemsME.COPPER_COIN))
                        .save(recipeOutput);
                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.SILVER_TREASURE_HEAP_LAYER, 1)
                        .pattern("NNN")
                        .define('N', ResourceItemsME.SILVER_COIN)
                        .unlockedBy(getHasName(ResourceItemsME.SILVER_COIN),
                                has(ResourceItemsME.SILVER_COIN))
                        .save(recipeOutput);
                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.GOLD_TREASURE_HEAP_LAYER, 1)
                        .pattern("NNN")
                        .define('N', ResourceItemsME.GOLD_COIN)
                        .unlockedBy(getHasName(ResourceItemsME.GOLD_COIN),
                                has(ResourceItemsME.GOLD_COIN))
                        .save(recipeOutput);

                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.COPPER_COIN_PILE, 1)
                        .pattern("NN")
                        .pattern("NN")
                        .define('N', ResourceItemsME.COPPER_COIN)
                        .unlockedBy(getHasName(ResourceItemsME.COPPER_COIN),
                                has(ResourceItemsME.COPPER_COIN))
                        .save(recipeOutput);

                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.SILVER_COIN_PILE, 1)
                        .pattern("NN")
                        .pattern("NN")
                        .define('N', ResourceItemsME.SILVER_COIN)
                        .unlockedBy(getHasName(ResourceItemsME.SILVER_COIN),
                                has(ResourceItemsME.SILVER_COIN))
                        .save(recipeOutput);

                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.GOLD_COIN_PILE, 1)
                        .pattern("NN")
                        .pattern("NN")
                        .define('N', ResourceItemsME.GOLD_COIN)
                        .unlockedBy(getHasName(ResourceItemsME.GOLD_COIN),
                                has(ResourceItemsME.GOLD_COIN))
                        .save(recipeOutput);

                ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, ResourceItemsME.COPPER_COIN, 3)
                        .requires(ModDecorativeBlocks.COPPER_TREASURE_HEAP_LAYER)
                        .unlockedBy(getHasName(ModDecorativeBlocks.COPPER_TREASURE_HEAP_LAYER),
                                has(ModDecorativeBlocks.COPPER_TREASURE_HEAP_LAYER))
                        .save(recipeOutput, String.valueOf(ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "copper_coin_from_treasure")));

                ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, ResourceItemsME.SILVER_COIN, 3)
                        .requires(ModDecorativeBlocks.SILVER_TREASURE_HEAP_LAYER)
                        .unlockedBy(getHasName(ModDecorativeBlocks.SILVER_TREASURE_HEAP_LAYER),
                                has(ModDecorativeBlocks.SILVER_TREASURE_HEAP_LAYER))
                        .save(recipeOutput, String.valueOf(ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "silver_coin_from_treasure")));

                ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, ResourceItemsME.GOLD_COIN, 3)
                        .requires(ModDecorativeBlocks.GOLD_TREASURE_HEAP_LAYER)
                        .unlockedBy(getHasName(ModDecorativeBlocks.GOLD_TREASURE_HEAP_LAYER),
                                has(ModDecorativeBlocks.GOLD_TREASURE_HEAP_LAYER))
                        .save(recipeOutput, String.valueOf(ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "gold_nugget_from_treasure")));

                ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, ResourceItemsME.COPPER_COIN, 4)
                        .requires(ModDecorativeBlocks.COPPER_COIN_PILE)
                        .unlockedBy(getHasName(ModDecorativeBlocks.COPPER_COIN_PILE),
                                has(ModDecorativeBlocks.COPPER_COIN_PILE))
                        .save(recipeOutput, String.valueOf(ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "copper_coin_from_pile")));

                ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, ResourceItemsME.SILVER_COIN, 4)
                        .requires(ModDecorativeBlocks.SILVER_COIN_PILE)
                        .unlockedBy(getHasName(ModDecorativeBlocks.SILVER_COIN_PILE),
                                has(ModDecorativeBlocks.SILVER_COIN_PILE))
                        .save(recipeOutput, String.valueOf(ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "silver_coin_from_pile")));

                ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, ResourceItemsME.GOLD_COIN, 4)
                        .requires(ModDecorativeBlocks.GOLD_COIN_PILE)
                        .unlockedBy(getHasName(ModDecorativeBlocks.GOLD_COIN_PILE),
                                has(ModDecorativeBlocks.GOLD_COIN_PILE))
                        .save(recipeOutput, String.valueOf(ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "gold_nugget_from_pile")));

                ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, ModNatureBlocks.SHORT_BULRUSH, 2)
                        .requires(ModNatureBlocks.TALL_BULRUSH)
                        .unlockedBy(getHasName(ModNatureBlocks.TALL_BULRUSH),
                                has(ModNatureBlocks.TALL_BULRUSH))
                        .save(recipeOutput);

                ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, ModNatureBlocks.SHORT_REEDS, 2)
                        .requires(ResourceItemsME.REEDS)
                        .unlockedBy(getHasName(ResourceItemsME.REEDS),
                                has(ResourceItemsME.REEDS))
                        .save(recipeOutput);

                ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, ModNatureBlocks.SHORT_DEAD_RUSHES, 2)
                        .requires(ModNatureBlocks.DEAD_RUSHES)
                        .unlockedBy(getHasName(ModNatureBlocks.DEAD_RUSHES),
                                has(ModNatureBlocks.DEAD_RUSHES))
                        .save(recipeOutput);

                ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, ModNatureBlocks.SHORT_RUSHES, 2)
                        .requires(ModNatureBlocks.RUSHES)
                        .unlockedBy(getHasName(ModNatureBlocks.RUSHES),
                                has(ModNatureBlocks.RUSHES))
                        .save(recipeOutput);

                ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, ModNatureBlocks.SHORT_CATTAILS, 2)
                        .requires(ModNatureBlocks.TALL_CATTAILS)
                        .unlockedBy(getHasName(ModNatureBlocks.TALL_CATTAILS),
                                has(ModNatureBlocks.TALL_CATTAILS))
                        .save(recipeOutput);

                ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, ResourceItemsME.GOLD_COIN, 4)
                        .requires(ModDecorativeBlocks.POT_OF_GOLD)
                        .unlockedBy(getHasName(ModDecorativeBlocks.POT_OF_GOLD),
                                has(ModDecorativeBlocks.POT_OF_GOLD))
                        .save(recipeOutput, String.valueOf(ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "gold_from_pot_of_gold")));

                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, WeaponItemsME.HELD_BANNER, 1)
                        .pattern("WWW")
                        .pattern("WWW")
                        .pattern("WSW")
                        .define('W', TagKey.create(Registries.ITEM, ResourceLocation.parse("wool")))
                        .define('S', Items.STICK)
                        .unlockedBy(getHasName(ResourceItemsME.GOLD_COIN),
                                has(ResourceItemsME.GOLD_COIN))
                        .save(recipeOutput);

                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, StoneBlockSets.SLATE_SET.baseBlocks.base(), 4)
                        .pattern("DS")
                        .pattern("SD")
                        .define('D', Items.DEEPSLATE)
                        .define('S', Items.STONE)
                        .unlockedBy(getHasName(Items.DEEPSLATE),
                                has(Items.DEEPSLATE))
                        .save(recipeOutput);

                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, StoneBlockSets.BLUE_TUFF_SET.baseBlocks.base(), 4)
                        .pattern("TG")
                        .pattern("GT")
                        .define('T', Items.TUFF)
                        .define('G', StoneBlockSets.KHAGALABAN_SET.baseBlocks.base())
                        .unlockedBy(getHasName(StoneBlockSets.KHAGALABAN_SET.baseBlocks.base()),
                                has(StoneBlockSets.KHAGALABAN_SET.baseBlocks.base()))
                        .save(recipeOutput);

                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, StoneBlockSets.HEMATITE_SET.baseBlocks.base(), 4)
                        .pattern("SI")
                        .pattern("IS")
                        .define('S', Items.STONE)
                        .define('I', StoneBlockSets.IRONSTONE_SET.baseBlocks.base())
                        .unlockedBy(getHasName(StoneBlockSets.IRONSTONE_SET.baseBlocks.base()),
                                has(StoneBlockSets.IRONSTONE_SET.baseBlocks.base()))
                        .save(recipeOutput);

                createSmokingRecipe(recipeOutput, Items.SHORT_GRASS, ModNatureBlocks.SCORCHED_GRASS.asItem());
                createSmokingRecipe(recipeOutput, ModNatureBlocks.GRASS_TUFT.asItem(), ModNatureBlocks.SCORCHED_TUFT.asItem());
                createSmokingRecipe(recipeOutput, ModNatureBlocks.GREEN_SHRUB.asItem(), ModNatureBlocks.SCORCHED_SHRUB.asItem());
                //endregion

                //region SMOKING-ONLY
                createSmokingRecipe(recipeOutput, ResourceItemsME.PIPEWEED, ResourceItemsME.DRIED_PIPEWEED);
                //endregion

                SpecialRecipeBuilder.special(CustomItemDecorationRecipe::new).save(recipeOutput, "custom_shield_decoration");
            }

            //region Refactored Methods            
            private void createStoneSetRecipes(BlockRecordTypes.RegularSet base) {
                if (base != null) {
                    if(!isVanillaBlock(base.slab())) {
                        slab(RecipeCategory.BUILDING_BLOCKS, base.slab(), base.base());
                        stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, base.slab(), base.base(), 2);
                    }
                    if(!isVanillaBlock(base.verticalSlab())) {
                        createVerticalSlabsRecipe(recipeOutput, base.slab(), base.verticalSlab());
                        createSlabsFromVerticalRecipe(recipeOutput, base.verticalSlab(), base.slab());
                        stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, base.verticalSlab(), base.base(), 2);
                    }
                    if(!isVanillaBlock(base.stairs())) {
                        createStairsRecipe(recipeOutput, base.base(), base.stairs());
                        stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, base.stairs(), base.base());
                    }
                    if(!isVanillaBlock(base.wall())) {
                        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, base.wall(), 6)
                                .pattern("lll")
                                .pattern("lll")
                                .define('l', base.base())
                                .unlockedBy(getHasName(base.base()),
                                        has(base.base()))
                                .save(recipeOutput);
                        stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, base.wall(), base.base());
                    }
                }
            }

            private void createStoneSetRecipes(BlockRecordTypes.BaseStoneSet base) {
                if (base != null) {
                    if(!isVanillaBlock(base.slab())) {
                        slab(RecipeCategory.BUILDING_BLOCKS, base.slab(), base.base());
                        stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, base.slab(), base.base(), 2);
                    }
                    if(!isVanillaBlock(base.verticalSlab())) {
                        createVerticalSlabsRecipe(recipeOutput, base.slab(), base.verticalSlab());
                        createSlabsFromVerticalRecipe(recipeOutput, base.verticalSlab(), base.slab());
                        stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, base.verticalSlab(), base.base(), 2);
                    }
                    if(!isVanillaBlock(base.stairs())) {
                        createStairsRecipe(recipeOutput, base.base(), base.stairs());
                        stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, base.stairs(), base.base());
                    }
                    if(!isVanillaBlock(base.wall())) {
                        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, base.wall(), 6)
                                .pattern("lll")
                                .pattern("lll")
                                .define('l', base.base())
                                .unlockedBy(getHasName(base.base()),
                                        has(base.base()))
                                .save(recipeOutput);
                        stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, base.wall(), base.base());
                    }
                }
            }

            private void createStoneSetRecipes(BlockRecordTypes.PillarSet base) {
                if (base != null) {
                    if(!isVanillaBlock(base.verticalSlab())) {
                        stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, base.verticalSlab(), base.base(), 2);
                    }
                    if(!isVanillaBlock(base.wall())) {
                        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, base.wall(), 6)
                                .pattern("lll")
                                .pattern("lll")
                                .define('l', base.base())
                                .unlockedBy(getHasName(base.base()),
                                        has(base.base()))
                                .save(recipeOutput);
                        stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, base.wall(), base.base());
                    }
                }
            }

            private boolean isVanillaBlock(Block block) {
                return BuiltInRegistries.BLOCK.getKey(block).getNamespace().equals(ResourceLocation.DEFAULT_NAMESPACE);
            }
            private void createChiseledStoneSetRecipes(BlockRecordTypes.PillarSet base) {
                if (base != null) {
                    stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, base.verticalSlab(), base.base(), 2);
                    ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, base.wall(), 6)
                            .pattern("lll")
                            .pattern("lll")
                            .define('l', base.base())
                            .unlockedBy(getHasName(base.base()),
                                    has(base.base()))
                            .save(recipeOutput);
                    stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, base.wall(), base.base());
                }
            }

            //endregion

            //region BLOCK RECIPE METHODS
            private void createBrickRecipe(RecipeOutput exporter, Item input, Block recipeOutput, int count) {
                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, recipeOutput, count)
                        .pattern("ll")
                        .pattern("ll")
                        .define('l', input)
                        .unlockedBy(getHasName(input),
                                has(input))
                        .save(exporter);
            }

            private void createPillarRecipe(RecipeOutput exporter, Block input, Block recipeOutput, int count) {
                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, recipeOutput, count)
                        .pattern("l")
                        .pattern("l")
                        .pattern("l")
                        .define('l', input)
                        .unlockedBy(getHasName(input),
                                has(input))
                        .save(exporter);
            }

            private void createChiseledRecipe(RecipeOutput exporter, Block input, Block recipeOutput, int count) {
                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, recipeOutput, count)
                        .pattern("l")
                        .pattern("l")
                        .define('l', input)
                        .unlockedBy(getHasName(input),
                                has(input))
                        .save(exporter);
            }

            private void createCutPolishedRecipe(RecipeOutput exporter, Block input, Block recipeOutput, int count) {
                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, recipeOutput, count)
                        .pattern("l")
                        .pattern("l")
                        .define('l', input)
                        .unlockedBy(getHasName(input),
                                has(input))
                        .save(exporter);
            }

            private void createMossyRecipe(RecipeOutput exporter, Block input, Block recipeOutput) {
                ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, recipeOutput, 1)
                        .requires(input)
                        .requires(Items.VINE)
                        .unlockedBy(getHasName(input),
                                has(input))
                        .save(exporter, String.valueOf(ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, BuiltInRegistries.BLOCK.getKey(recipeOutput).getPath() + "_vine")));

                ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, recipeOutput, 1)
                        .requires(input)
                        .requires(Blocks.MOSS_BLOCK)
                        .unlockedBy(getHasName(input),
                                has(input))
                        .save(exporter, String.valueOf(ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, BuiltInRegistries.BLOCK.getKey(recipeOutput).getPath() + "_moss")));
            }

            private void createSmeltingRecipe(RecipeOutput exporter, Item input, Item recipeOutput) {
                SimpleCookingRecipeBuilder.smelting(Ingredient.of(input), RecipeCategory.BUILDING_BLOCKS, recipeOutput, 0.1f, 200)
                        .unlockedBy(getHasName(input),
                                has(input))
                        .save(exporter);
            }

            private void createSmeltingRecipeIdentifier(RecipeOutput exporter, Item input, Item recipeOutput) {
                SimpleCookingRecipeBuilder.smelting(Ingredient.of(input), RecipeCategory.BUILDING_BLOCKS, recipeOutput, 0.1f, 200)
                        .unlockedBy(getHasName(input),
                                has(input))
                        .save(exporter, String.valueOf(ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, BuiltInRegistries.ITEM.getKey(recipeOutput).getPath() + "_from_smelting")));
            }

            private void createCobbledBaseSmeltingRecipe(RecipeOutput exporter, Block input, Block recipeOutput) {
                String inputPath = BuiltInRegistries.BLOCK.getKey(input).getPath();
                String outputPath = BuiltInRegistries.BLOCK.getKey(recipeOutput).getPath();
                SimpleCookingRecipeBuilder.smelting(Ingredient.of(input), RecipeCategory.BUILDING_BLOCKS,
                                recipeOutput, 0.1f, 200)
                        .unlockedBy(getHasName(input), has(input))
                        .save(exporter, String.valueOf(MiddleEarth.of(inputPath + "_to_" + outputPath)));
            }

            private void createMeltBulkRecipe(RecipeOutput exporter, Item input, String recipeOutput) {
                createMeltRecipe(exporter, input, recipeOutput, 1, INGOT_LIQUID_VALUE);
                createMeltRecipe(exporter, input, recipeOutput, 2, INGOT_LIQUID_VALUE);
                createMeltRecipe(exporter, input, recipeOutput, 3, INGOT_LIQUID_VALUE);
                createMeltRecipe(exporter, input, recipeOutput, 4, INGOT_LIQUID_VALUE);
            }

            private void createMeltRecipe(RecipeOutput exporter, Item input, String recipeOutput, int ingots, int amount) {
                switch (ingots) {
                    case 1 -> AlloyRecipeJsonBuilder.createAlloyRecipe(this.itemLookup, RecipeCategory.MISC, recipeOutput, amount, 0)
                            .input(input)
                            .unlockedBy(getHasName(input),
                                    has(input))
                            .save(exporter, String.valueOf(ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, recipeOutput + "_from_melting_1_" + BuiltInRegistries.ITEM.getKey(input).getPath())));
                    case 2 -> AlloyRecipeJsonBuilder.createAlloyRecipe(this.itemLookup, RecipeCategory.MISC, recipeOutput, amount * 2, 0)
                            .input(input)
                            .input(input)
                            .unlockedBy(getHasName(input),
                                    has(input))
                            .save(exporter, String.valueOf(ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, recipeOutput + "_from_melting_2_" + BuiltInRegistries.ITEM.getKey(input).getPath())));
                    case 3 -> AlloyRecipeJsonBuilder.createAlloyRecipe(this.itemLookup, RecipeCategory.MISC, recipeOutput, amount * 3,0)
                            .input(input)
                            .input(input)
                            .input(input)
                            .unlockedBy(getHasName(input),
                                    has(input))
                            .save(exporter, String.valueOf(ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, recipeOutput + "_from_melting_3_" + BuiltInRegistries.ITEM.getKey(input).getPath())));
                    case 4 -> AlloyRecipeJsonBuilder.createAlloyRecipe(this.itemLookup, RecipeCategory.MISC, recipeOutput, amount * 4, 0)
                            .input(input)
                            .input(input)
                            .input(input)
                            .input(input)
                            .unlockedBy(getHasName(input),
                                    has(input))
                            .save(exporter, String.valueOf(ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, recipeOutput + "_from_melting_4_" + BuiltInRegistries.ITEM.getKey(input).getPath())));
                }
            }

            private void createMeltBulkRecipeTag(RecipeOutput exporter, TagKey input, String recipeOutput) {
                createMeltRecipeTag(exporter, input, recipeOutput, 1, INGOT_LIQUID_VALUE);
                createMeltRecipeTag(exporter, input, recipeOutput, 2, INGOT_LIQUID_VALUE);
                createMeltRecipeTag(exporter, input, recipeOutput, 3, INGOT_LIQUID_VALUE);
                createMeltRecipeTag(exporter, input, recipeOutput, 4, INGOT_LIQUID_VALUE);
            }

            private void createMeltRecipeTag(RecipeOutput exporter, TagKey input, String recipeOutput, int ingots, int amount) {
                switch (ingots) {
                    case 1 -> AlloyRecipeJsonBuilder.createAlloyRecipe(this.itemLookup, RecipeCategory.MISC, recipeOutput, amount, 0)
                            .input(input)
                            .unlockedBy(getHasName(DecorativeItemsME.FORGE),
                                    has(DecorativeItemsME.FORGE))
                            .save(exporter, String.valueOf(ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, recipeOutput + "_from_melting_1_" + input.location().getPath())));
                    case 2 -> AlloyRecipeJsonBuilder.createAlloyRecipe(this.itemLookup, RecipeCategory.MISC, recipeOutput, amount * 2, 0)
                            .input(input)
                            .input(input)
                            .unlockedBy(getHasName(DecorativeItemsME.FORGE),
                                    has(DecorativeItemsME.FORGE))
                            .save(exporter, String.valueOf(ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, recipeOutput + "_from_melting_2_" + input.location().getPath())));
                    case 3 -> AlloyRecipeJsonBuilder.createAlloyRecipe(this.itemLookup, RecipeCategory.MISC, recipeOutput, amount * 3, 0)
                            .input(input)
                            .input(input)
                            .input(input)
                            .unlockedBy(getHasName(DecorativeItemsME.FORGE),
                                    has(DecorativeItemsME.FORGE))
                            .save(exporter, String.valueOf(ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, recipeOutput + "_from_melting_3_" + input.location().getPath())));
                    case 4 -> AlloyRecipeJsonBuilder.createAlloyRecipe(this.itemLookup, RecipeCategory.MISC, recipeOutput, amount * 4, 0)
                            .input(input)
                            .input(input)
                            .input(input)
                            .input(input)
                            .unlockedBy(getHasName(DecorativeItemsME.FORGE),
                                    has(DecorativeItemsME.FORGE))
                            .save(exporter, String.valueOf(ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, recipeOutput + "_from_melting_4_" + input.location().getPath())));
                }
            }

            private void createAnvilShapingRecipeTag(RecipeOutput exporter, TagKey input, Item recipeOutput, int amount) {
                AnvilShapingRecipeJsonBuilder.createAnvilShapingRecipe(this.itemLookup, RecipeCategory.MISC, recipeOutput, amount)
                        .input(input)
                        .unlockedBy(getHasName(Items.COPPER_INGOT),
                                has(Items.COPPER_INGOT))
                        .save(exporter);
            }

            private void createAnvilShapingRecipeItem(RecipeOutput exporter, Item input, Item recipeOutput, int amount) {
                AnvilShapingRecipeJsonBuilder.createAnvilShapingRecipe(this.itemLookup, RecipeCategory.MISC, recipeOutput, amount)
                        .input(input)
                        .unlockedBy(getHasName(Items.COPPER_INGOT),
                                has(Items.COPPER_INGOT))
                        .save(exporter);
            }

            private void createAnvilRecipe(RecipeOutput exporter, Item inputBlock, Item inputIngot, Item recipeOutput) {
                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, recipeOutput, 1)
                        .pattern("IBI")
                        .pattern(" I ")
                        .pattern("LLL")
                        .define('I', inputIngot)
                        .define('B', inputBlock)
                        .define('L', TagKey.create(Registries.ITEM, ResourceLocation.parse("logs")))
                        .unlockedBy(getHasName(inputIngot),
                                has(inputIngot))
                        .save(exporter);
            }

            private void createGenericRecipes(GenericBlockSetBuilder set) {
                slab(RecipeCategory.BUILDING_BLOCKS, set.blockSet.slab().asItem(), set.blockSet.base().asItem());
                createVerticalSlabsRecipe(recipeOutput, set.blockSet.slab(), set.blockSet.verticalSlab());
                createSlabsFromVerticalRecipe(recipeOutput, set.blockSet.verticalSlab(), set.blockSet.slab());
                createStairsRecipe(recipeOutput, set.blockSet.base(), set.blockSet.stairs());
                wall(RecipeCategory.BUILDING_BLOCKS, set.blockSet.wall(), set.blockSet.base());
            }

            private void createGenericRecipes(SimpleBlockSetBuilder set) {
                slab(RecipeCategory.BUILDING_BLOCKS, set.blockSet.slab().asItem(), set.blockSet.base().asItem());
                createVerticalSlabsRecipe(recipeOutput, set.blockSet.slab(), set.blockSet.verticalSlab());
                createSlabsFromVerticalRecipe(recipeOutput, set.blockSet.verticalSlab(), set.blockSet.slab());
                createStairsRecipe(recipeOutput, set.blockSet.base(), set.blockSet.stairs());
            }

            private void createGildedStoneRecipe(Block input, Block output) {
                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, output, 5)
                        .pattern("TNT")
                        .pattern("NTN")
                        .pattern("TNT")
                        .define('T', input)
                        .define('N', Items.GOLD_NUGGET)
                        .unlockedBy(getHasName(input), has(input))
                        .save(recipeOutput);
            }

            private void createRegularSetRecipes(BlockRecordTypes.RegularSet set) {
                slab(RecipeCategory.BUILDING_BLOCKS, set.slab().asItem(), set.base().asItem());
                createVerticalSlabsRecipe(recipeOutput, set.slab(), set.verticalSlab());
                createSlabsFromVerticalRecipe(recipeOutput, set.verticalSlab(), set.slab());
                createStairsRecipe(recipeOutput, set.base(), set.stairs());
                wall(RecipeCategory.BUILDING_BLOCKS, set.wall(), set.base());
            }

            private void createStairsRecipe(RecipeOutput exporter, Block input, Block recipeOutput) {
                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, recipeOutput, 4)
                        .pattern("l  ")
                        .pattern("ll ")
                        .pattern("lll")
                        .define('l', input)
                        .unlockedBy(getHasName(input),
                                has(input))
                        .save(exporter);
            }

            private void createSlabsFromVerticalRecipe(RecipeOutput exporter, Block input, Block recipeOutput) {
                ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, recipeOutput, 1)
                        .requires(input)
                        .unlockedBy(getHasName(input),
                                has(input))
                        .save(exporter, String.valueOf(ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, BuiltInRegistries.BLOCK.getKey(input).getPath() + "_from_vertical")));
            }

            private void createVerticalSlabsRecipe(RecipeOutput exporter, Block input, Block recipeOutput) {
                ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, recipeOutput, 1)
                        .requires(input)
                        .unlockedBy(getHasName(input),
                                has(input))
                        .save(exporter);
            }

            private void createShinglesRecipe(RecipeOutput exporter, Block input, Block recipeOutput) {
                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, recipeOutput, 7)
                        .pattern(" w ")
                        .pattern("www")
                        .pattern("www")
                        .define('w', input)
                        .unlockedBy(getHasName(input),
                                has(input))
                        .save(exporter);
            }

            private void createRoofingRecipe(RecipeOutput exporter, Block input, Block recipeOutput) {
                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, recipeOutput, 2)
                        .pattern(" w ")
                        .pattern("www")
                        .define('w', input)
                        .unlockedBy(getHasName(input),
                                has(input))
                        .save(exporter);
            }

            private void createDoorRecipe(RecipeOutput exporter, Block input, Block recipeOutput) {
                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, recipeOutput, 3)
                        .pattern("ll")
                        .pattern("ll")
                        .pattern("ll")
                        .define('l', input)
                        .unlockedBy(getHasName(input),
                                has(input))
                        .save(exporter);
            }

            private void createTrapdoorRecipe(RecipeOutput exporter, Block input, Block recipeOutput) {
                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, recipeOutput, 2)
                        .pattern("lll")
                        .pattern("lll")
                        .define('l', input)
                        .unlockedBy(getHasName(input),
                                has(input))
                        .save(exporter);
            }

            private void createCenterSurroundRecipe(RecipeOutput exporter, Item surroundInput, Item centerItem, Item recipeOutput, int count) {
                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, recipeOutput, count)
                        .pattern("BBB")
                        .pattern("BDB")
                        .pattern("BBB")
                        .define('B', surroundInput)
                        .define('D', centerItem)
                        .unlockedBy(getHasName(surroundInput),
                                has(surroundInput))
                        .save(exporter, String.valueOf(ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, BuiltInRegistries.ITEM.getKey(recipeOutput).getPath() + "_alt")));
            }

            private void createDyeableItemRecipe(RecipeOutput exporter, Block blockInput, Item dyeItem, Block recipeOutput) {
                ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, recipeOutput, 1)
                        .requires(blockInput)
                        .requires(dyeItem)
                        .unlockedBy(getHasName(blockInput),
                                has(blockInput))
                        .save(exporter);
            }

            private void createCombinedItemRecipe(RecipeOutput exporter, Block blockInput, TagKey<Item> addition, Block recipeOutput) {
                ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, recipeOutput, 1)
                        .requires(blockInput)
                        .requires(addition)
                        .unlockedBy(getHasName(blockInput),
                                has(blockInput))
                        .save(exporter);
            }

            private void createPaneRecipe(RecipeOutput exporter, Item blockInput, Block recipeOutput, int count) {
                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, recipeOutput, count)
                        .pattern("BBB")
                        .pattern("BBB")
                        .define('B', blockInput)
                        .unlockedBy(getHasName(blockInput),
                                has(blockInput))
                        .save(exporter);
            }

            private void createWoodStoolRecipe(RecipeOutput exporter, Item inputPlanks, Block recipeOutput) {
                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, recipeOutput, 3)
                        .pattern("PP")
                        .pattern("SS")
                        .define('P', inputPlanks)
                        .define('S', Items.STICK)
                        .unlockedBy(getHasName(inputPlanks),
                                has(inputPlanks))
                        .save(exporter);
            }

            private void createWoodBenchRecipe(RecipeOutput exporter, Item inputPlanks, Block recipeOutput) {
                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, recipeOutput, 3)
                        .pattern("PPP")
                        .pattern("S S")
                        .define('P', inputPlanks)
                        .define('S', Items.STICK)
                        .unlockedBy(getHasName(inputPlanks),
                                has(inputPlanks))
                        .save(exporter);
            }

            private void createWoodTableRecipe(RecipeOutput exporter, Item inputPlanks, Block recipeOutput) {
                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, recipeOutput, 3)
                        .pattern("PPP")
                        .pattern("S S")
                        .pattern("S S")
                        .define('P', inputPlanks)
                        .define('S', Items.STICK)
                        .unlockedBy(getHasName(inputPlanks),
                                has(inputPlanks))
                        .save(exporter);
            }

            private void createWoodChairRecipe(RecipeOutput exporter, Item inputPlanks, Block recipeOutput) {
                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, recipeOutput, 3)
                        .pattern("P  ")
                        .pattern("PPP")
                        .pattern("S S")
                        .define('P', inputPlanks)
                        .define('S', Items.STICK)
                        .unlockedBy(getHasName(inputPlanks),
                                has(inputPlanks))
                        .save(exporter);
            }

            private void createWoodLadderRecipe(RecipeOutput exporter, Item inputPlanks, Block recipeOutput) {
                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, recipeOutput, 3)
                        .pattern("P P")
                        .pattern("PSP")
                        .pattern("P P")
                        .define('P', inputPlanks)
                        .define('S', Items.STICK)
                        .unlockedBy(getHasName(inputPlanks),
                                has(inputPlanks))
                        .save(exporter);
            }

            private void createStoneStoolRecipe(RecipeOutput exporter, Item input, Block recipeOutput) {
                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, recipeOutput, 1)
                        .pattern("SSS")
                        .pattern("S S")
                        .define('S', input)
                        .unlockedBy(getHasName(input),
                                has(input))
                        .save(exporter);
            }

            private void createStoneTableRecipe(RecipeOutput exporter, Item input, Block recipeOutput) {
                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, recipeOutput, 1)
                        .pattern("SSS")
                        .pattern(" S ")
                        .pattern(" S ")
                        .define('S', input)
                        .unlockedBy(getHasName(input),
                                has(input))
                        .save(exporter);
            }

            private void createStoneChairRecipe(RecipeOutput exporter, Item input, Block recipeOutput) {
                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, recipeOutput, 1)
                        .pattern("S  ")
                        .pattern("SSS")
                        .pattern("SSS")
                        .define('S', input)
                        .unlockedBy(getHasName(input),
                                has(input))
                        .save(exporter);
            }

            private void createLayerRecipe(RecipeOutput exporter, Item input, Block recipeOutput) {
                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, recipeOutput, 6)
                        .pattern("BBB")
                        .define('B', input)
                        .unlockedBy(getHasName(input),
                                has(input))
                        .save(exporter);
            }

            private void createButtonRecipe(RecipeOutput exporter, Block input, Block recipeOutput) {
                ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, recipeOutput, 1)
                        .requires(input, 1)
                        .unlockedBy(getHasName(input),
                                has(input))
                        .save(exporter);
            }

            private void createPressurePlateRecipe(RecipeOutput exporter, Block input, Block recipeOutput) {
                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, recipeOutput, 1)
                        .pattern("BB")
                        .define('B', input)
                        .unlockedBy(getHasName(input),
                                has(input))
                        .save(exporter);
            }

            private void createFenceRecipe(RecipeOutput exporter, Item input, Block recipeOutput) {
                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, recipeOutput, 3)
                        .pattern("lsl")
                        .pattern("lsl")
                        .define('l', input)
                        .define('s', Items.STICK)
                        .unlockedBy(getHasName(input),
                                has(input))
                        .unlockedBy(getHasName(Items.STICK),
                                has(Items.STICK))
                        .save(exporter);
            }

            private void createGildedBlockRecipe(RecipeOutput exporter, Block input, Block recipeOutput) {
                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, recipeOutput, 1)
                        .pattern(" N ")
                        .pattern("NBN")
                        .pattern(" N ")
                        .define('B', input)
                        .define('N', Items.GOLD_NUGGET)
                        .unlockedBy(getHasName(input),
                                has(input))
                        .save(exporter);
            }

            private void createBrickworkBlockRecipe(RecipeOutput exporter, Block input, Block inputBinder, Block recipeOutput) {
                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, recipeOutput, 2)
                        .pattern("SB")
                        .define('S', inputBinder)
                        .define('B', input)
                        .unlockedBy(getHasName(input),
                                has(input))
                        .save(exporter);
            }

            private void createWattleRecipes(RecipeOutput exporter, Item input, Block outputBase,
                                             Block outputCross, Block outputRight, Block outputLeft, Block outputPillar, Block outputDiamond) {
                createBaseWattleRecipe(exporter, input, outputBase);
                createCrossWattleRecipe(exporter, input, outputCross);
                createRightWattleRecipe(exporter, input, outputRight);
                createLeftWattleRecipe(exporter, input, outputLeft);
                createPillarWattleRecipe(exporter, input, outputPillar);
                createDiamondWattleRecipe(exporter, input, outputDiamond);
            }

            private void createBaseWattleRecipe(RecipeOutput exporter, Item input, Block recipeOutput) {
                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, recipeOutput, 1)
                        .pattern(" S ")
                        .pattern("SDS")
                        .pattern(" S ")
                        .define('S', Items.STICK)
                        .define('D', input)
                        .unlockedBy(getHasName(input),
                                has(input))
                        .save(exporter);
            }

            private void createCrossWattleRecipe(RecipeOutput exporter, Item input, Block recipeOutput) {
                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, recipeOutput, 4)
                        .pattern("SDS")
                        .pattern("DSD")
                        .pattern("SDS")
                        .define('S', Items.STICK)
                        .define('D', input)
                        .unlockedBy(getHasName(input),
                                has(input))
                        .save(exporter);
            }

            private void createRightWattleRecipe(RecipeOutput exporter, Item input, Block recipeOutput) {
                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, recipeOutput, 6)
                        .pattern("DDS")
                        .pattern("DSD")
                        .pattern("SDD")
                        .define('S', Items.STICK)
                        .define('D', input)
                        .unlockedBy(getHasName(input),
                                has(input))
                        .save(exporter);
            }

            private void createLeftWattleRecipe(RecipeOutput exporter, Item input, Block recipeOutput) {
                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, recipeOutput, 6)
                        .pattern("SDD")
                        .pattern("DSD")
                        .pattern("DDS")
                        .define('S', Items.STICK)
                        .define('D', input)
                        .unlockedBy(getHasName(input),
                                has(input))
                        .save(exporter);
            }

            private void createPillarWattleRecipe(RecipeOutput exporter, Item input, Block recipeOutput) {
                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, recipeOutput, 6)
                        .pattern("DSD")
                        .pattern("DSD")
                        .pattern("DSD")
                        .define('S', Items.STICK)
                        .define('D', input)
                        .unlockedBy(getHasName(input),
                                has(input))
                        .save(exporter);
            }

            private void createDiamondWattleRecipe(RecipeOutput exporter, Item input, Block recipeOutput) {
                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, recipeOutput, 5)
                        .pattern("DSD")
                        .pattern("SDS")
                        .pattern("DSD")
                        .define('S', Items.STICK)
                        .define('D', input)
                        .unlockedBy(getHasName(input),
                                has(input))
                        .save(exporter);
            }

            private void createStatueRecipe(RecipeOutput exporter, Block polishedInput, Block stoneInput, Block wallInput, Block recipeOutput) {
                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, recipeOutput, 1)
                        .pattern("WSW")
                        .pattern("WSW")
                        .pattern("WPW")
                        .define('W', wallInput)
                        .define('S', stoneInput)
                        .define('P', polishedInput)
                        .unlockedBy(getHasName(polishedInput),
                                has(polishedInput))
                        .save(exporter);
            }

            private void createCushionRecipe(RecipeOutput exporter, Block woolBlock, Block recipeOutput) {
                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, recipeOutput, 1)
                        .pattern("WW")
                        .pattern("PP")
                        .define('W', woolBlock)
                        .define('P', TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .unlockedBy(getHasName(woolBlock),
                                has(woolBlock))
                        .save(exporter);
            }

            private void createSmallCurtainRecipe(RecipeOutput exporter, Block woolBlock, Block recipeOutput) {
                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, recipeOutput, 2)
                        .pattern("SSS")
                        .pattern("W W")
                        .define('W', woolBlock)
                        .define('S', Items.STICK)
                        .unlockedBy(getHasName(woolBlock),
                                has(woolBlock))
                        .save(exporter);
            }
            private void createSmallFancyCurtainRecipe(RecipeOutput exporter, Block woolBlock, Block recipeOutput) {
                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, recipeOutput, 2)
                        .pattern("SGS")
                        .pattern("W W")
                        .define('W', woolBlock)
                        .define('S', Items.STICK)
                        .define('G', Items.GOLD_NUGGET)
                        .unlockedBy(getHasName(woolBlock),
                                has(woolBlock))
                        .save(exporter);
            }

            private void createCurtainRecipe(RecipeOutput exporter, Block woolBlock, Block recipeOutput) {
                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, recipeOutput, 4)
                        .pattern("SSS")
                        .pattern("W W")
                        .pattern("W W")
                        .define('W', woolBlock)
                        .define('S', Items.STICK)
                        .unlockedBy(getHasName(woolBlock),
                                has(woolBlock))
                        .save(exporter);
            }
            private void createFancyCurtainRecipe(RecipeOutput exporter, Block woolBlock, Block recipeOutput) {
                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, recipeOutput, 4)
                        .pattern("SGS")
                        .pattern("W W")
                        .pattern("W W")
                        .define('W', woolBlock)
                        .define('G', Items.GOLD_NUGGET)
                        .define('S', Items.STICK)
                        .unlockedBy(getHasName(woolBlock),
                                has(woolBlock))
                        .save(exporter);
            }

            private void createBannerPatternRecipe(RecipeOutput exporter, Item input, Item recipeOutput) {
                ShapedRecipeBuilder.shaped(RecipeCategory.MISC, recipeOutput, 1)
                        .pattern("PF")
                        .pattern("BI")
                        .define('I', input)
                        .define('B', Items.BLACK_DYE)
                        .define('F', Items.FEATHER)
                        .define('P', Items.PAPER)
                        .unlockedBy(getHasName(Items.PAPER),
                                has(Items.PAPER))
                        .save(exporter);
            }
            //endregion

            //region ITEM RECIPE METHODS
            private void createSeedsRecipe(RecipeOutput exporter, Item input, Item recipeOutput) {
                ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, recipeOutput, 1)
                        .requires(input)
                        .unlockedBy(getHasName(input),
                                has(input))
                        .save(exporter);
            }

            private void createPickaxeRecipe(RecipeOutput exporter, Item inputRod, Item inputMaterial, Item recipeOutput) {
                ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, recipeOutput, 1)
                        .pattern("MMM")
                        .pattern(" R ")
                        .pattern(" R ")
                        .define('M', inputMaterial)
                        .define('R', inputRod)
                        .unlockedBy(getHasName(inputMaterial),
                                has(inputMaterial))
                        .save(exporter);
            }

            private void createAxeRecipe(RecipeOutput exporter, Item inputRod, Item inputMaterial, Item recipeOutput) {
                ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, recipeOutput, 1)
                        .pattern("MM ")
                        .pattern("MR ")
                        .pattern(" R ")
                        .define('M', inputMaterial)
                        .define('R', inputRod)
                        .unlockedBy(getHasName(inputMaterial),
                                has(inputMaterial))
                        .save(exporter);
            }

            private void createShovelRecipe(RecipeOutput exporter, Item inputRod, Item inputMaterial, Item recipeOutput) {
                ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, recipeOutput, 1)
                        .pattern(" M ")
                        .pattern(" R ")
                        .pattern(" R ")
                        .define('M', inputMaterial)
                        .define('R', inputRod)
                        .unlockedBy(getHasName(inputMaterial),
                                has(inputMaterial))
                        .save(exporter);
            }

            private void createHoeRecipe(RecipeOutput exporter, Item inputRod, Item inputMaterial, Item recipeOutput) {
                ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, recipeOutput, 1)
                        .pattern("MM ")
                        .pattern(" R ")
                        .pattern(" R ")
                        .define('M', inputMaterial)
                        .define('R', inputRod)
                        .unlockedBy(getHasName(inputMaterial),
                                has(inputMaterial))
                        .save(exporter);
            }

            private void createSwordRecipe(RecipeOutput exporter, Item inputRod, Item inputMaterial, Item recipeOutput) {
                ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, recipeOutput, 1)
                        .pattern(" M ")
                        .pattern(" M ")
                        .pattern(" R ")
                        .define('M', inputMaterial)
                        .define('R', inputRod)
                        .unlockedBy(getHasName(inputMaterial),
                                has(inputMaterial))
                        .save(exporter);
            }

            private void createDaggerRecipe(RecipeOutput exporter, Item inputRod, Item inputMaterial, Item recipeOutput) {
                ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, recipeOutput, 1)
                        .pattern(" M ")
                        .pattern(" R ")
                        .define('M', inputMaterial)
                        .define('R', inputRod)
                        .unlockedBy(getHasName(inputMaterial),
                                has(inputMaterial))
                        .save(exporter);
            }

            private void createDaggerRecipeTag(RecipeOutput exporter, Item inputRod, TagKey inputMaterial, Item recipeOutput) {
                ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, recipeOutput, 1)
                        .pattern(" M ")
                        .pattern(" R ")
                        .define('M', inputMaterial)
                        .define('R', inputRod)
                        .unlockedBy(getHasName(Items.OAK_PLANKS),
                                has(Items.OAK_PLANKS))
                        .save(exporter);
            }

            private void createSpearRecipe(RecipeOutput exporter, Item inputRod, Item inputMaterial, Item recipeOutput) {
                ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, recipeOutput, 1)
                        .pattern("  M")
                        .pattern(" R ")
                        .pattern("R  ")
                        .define('M', inputMaterial)
                        .define('R', inputRod)
                        .unlockedBy(getHasName(inputMaterial),
                                has(inputMaterial))
                        .save(exporter);
            }

            private void createSpearRecipeTag(RecipeOutput exporter, Item inputRod, TagKey inputMaterial, Item recipeOutput) {
                ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, recipeOutput, 1)
                        .pattern("  M")
                        .pattern(" R ")
                        .pattern("R  ")
                        .define('M', inputMaterial)
                        .define('R', inputRod)
                        .unlockedBy(getHasName(Items.OAK_PLANKS),
                                has(Items.OAK_PLANKS))
                        .save(exporter);
            }

            private void createBucketRecipe(RecipeOutput exporter, Item inputMaterial, Item recipeOutput) {
                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, recipeOutput, 1)
                        .pattern("M M")
                        .pattern(" M ")
                        .define('M', inputMaterial)
                        .unlockedBy(getHasName(inputMaterial),
                                has(inputMaterial))
                        .save(exporter);
            }

            private void createToolSetRecipes(RecipeOutput exporter, Item inputRod, Item inputMaterial, Item outputPickaxe, Item outputAxe, Item outputShovel, Item outputHoe) {
                createPickaxeRecipe(exporter, inputRod, inputMaterial, outputPickaxe);
                createAxeRecipe(exporter, inputRod, inputMaterial, outputAxe);
                createShovelRecipe(exporter, inputRod, inputMaterial, outputShovel);
                createHoeRecipe(exporter, inputRod, inputMaterial, outputHoe);
            }

            private void createCookedFoodRecipes(RecipeOutput exporter, Item rawFood, Item cookedFood) {
                simpleCookingRecipe(recipeOutput, "smelting", RecipeSerializer.SMELTING_RECIPE, SmeltingRecipe::new, 200, rawFood, cookedFood, 0.35f);
                simpleCookingRecipe(recipeOutput, "smoking", RecipeSerializer.SMOKING_RECIPE, SmokingRecipe::new, 100, rawFood, cookedFood, 0.35f);
                simpleCookingRecipe(recipeOutput, "campfire_cooking", RecipeSerializer.CAMPFIRE_COOKING_RECIPE, CampfireCookingRecipe::new, 600, rawFood, cookedFood, 0.35f);
            }

            private void createSmokingRecipe(RecipeOutput exporter, Item rawFood, Item cookedFood) {
                simpleCookingRecipe(recipeOutput, "smoking", RecipeSerializer.SMOKING_RECIPE, SmokingRecipe::new, 100, rawFood, cookedFood, 0.35f);
            }

            private void createMetalsRecipe(RecipeOutput exporter, Item nugget, Item ingot, Block block) {
                ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ingot, 1)
                        .requires(nugget, 9)
                        .unlockedBy(getHasName(nugget),
                                has(nugget))
                        .save(exporter, String.valueOf(ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, BuiltInRegistries.ITEM.getKey(ingot).getPath() + "_from_nuggets")));

                ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, nugget, 9)
                        .requires(ingot)
                        .unlockedBy(getHasName(ingot),
                                has(ingot))
                        .save(exporter, String.valueOf(ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, BuiltInRegistries.ITEM.getKey(nugget).getPath() + "_from_ingot")));

                createFilledRecipe(exporter, ingot, block, 1);

                ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ingot, 9)
                        .requires(block)
                        .unlockedBy(getHasName(block),
                                has(block))
                        .save(exporter, String.valueOf(ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, BuiltInRegistries.ITEM.getKey(ingot).getPath() + "_from_block")));
            }
            //endregion

    private void createFilledRecipe(RecipeOutput exporter, Item input, Block recipeOutput, int count) {
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, recipeOutput, count)
                .pattern("lll")
                .pattern("lll")
                .pattern("lll")
                .define('l', input)
                .unlockedBy(getHasName(input),
                        has(input))
                .save(exporter);
    }

    private void slab(RecipeCategory category, ItemLike result, ItemLike ingredient) {
        net.minecraft.data.recipes.RecipeProvider.slab(recipeOutput, category, result, ingredient);
    }

    private void wall(RecipeCategory category, ItemLike result, ItemLike ingredient) {
        net.minecraft.data.recipes.RecipeProvider.wall(recipeOutput, category, result, ingredient);
    }

    private void stonecutterResultFromBase(
            RecipeCategory category,
            ItemLike result,
            ItemLike ingredient
    ) {
        net.minecraft.data.recipes.RecipeProvider.stonecutterResultFromBase(
                recipeOutput,
                category,
                result,
                ingredient
        );
    }

    private void stonecutterResultFromBase(
            RecipeCategory category,
            ItemLike result,
            ItemLike ingredient,
            int count
    ) {
        net.minecraft.data.recipes.RecipeProvider.stonecutterResultFromBase(
                recipeOutput,
                category,
                result,
                ingredient,
                count
        );
    }

    private void oreSmelting(
            List<? extends ItemLike> ingredients,
            RecipeCategory category,
            ItemLike result,
            float experience,
            int cookingTime,
            String group
    ) {
        net.minecraft.data.recipes.RecipeProvider.oreSmelting(
                recipeOutput,
                List.copyOf(ingredients),
                category,
                result,
                experience,
                cookingTime,
                group
        );
    }

    private void oreBlasting(
            List<? extends ItemLike> ingredients,
            RecipeCategory category,
            ItemLike result,
            float experience,
            int cookingTime,
            String group
    ) {
        net.minecraft.data.recipes.RecipeProvider.oreBlasting(
                recipeOutput,
                List.copyOf(ingredients),
                category,
                result,
                experience,
                cookingTime,
                group
        );
    }
}
