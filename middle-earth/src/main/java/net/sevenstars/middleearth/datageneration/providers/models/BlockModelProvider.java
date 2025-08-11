package net.sevenstars.middleearth.datageneration.providers.models;

import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.enums.BlockFace;
import net.minecraft.block.enums.DoorHinge;
import net.minecraft.block.enums.Thickness;
import net.minecraft.client.data.*;
import net.minecraft.client.render.item.tint.GrassTintSource;
import net.minecraft.client.render.model.json.ModelVariant;
import net.minecraft.client.render.model.json.ModelVariantOperator;
import net.minecraft.client.render.model.json.WeightedVariant;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.AxisRotation;
import net.minecraft.util.math.Direction;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.block.registration.ModBlocks;
import net.sevenstars.middleearth.block.registration.ModDecorativeBlocks;
import net.sevenstars.middleearth.block.registration.ModNatureBlocks;
import net.sevenstars.middleearth.block.special.LargeDoorBlock;
import net.sevenstars.middleearth.block.special.RocksBlock;
import net.sevenstars.middleearth.block.special.crop.*;
import net.sevenstars.middleearth.block.special.doors.*;
import net.sevenstars.middleearth.block.special.verticalSlabs.VerticalSlabBlock;
import net.sevenstars.middleearth.block.special.verticalSlabs.VerticalSlabShape;
import net.sevenstars.middleearth.datageneration.content.MEModels;
import net.sevenstars.middleearth.datageneration.content.models.*;

import java.util.Objects;

import static net.minecraft.client.data.BlockStateModelGenerator.*;

public class BlockModelProvider extends FabricModelProvider {

    public static final ModelVariantOperator UV_LOCK = ModelVariantOperator.UV_LOCK.withValue(true);
    public static final ModelVariantOperator ROTATE_X_90 = ModelVariantOperator.ROTATION_X.withValue(AxisRotation.R90);
    ;
    public static final ModelVariantOperator ROTATE_X_180 = ModelVariantOperator.ROTATION_X.withValue(AxisRotation.R180);
    public static final ModelVariantOperator ROTATE_X_270 = ModelVariantOperator.ROTATION_X.withValue(AxisRotation.R270);
    public static final ModelVariantOperator ROTATE_Y_90 = ModelVariantOperator.ROTATION_Y.withValue(AxisRotation.R90);
    public static final ModelVariantOperator ROTATE_Y_180 = ModelVariantOperator.ROTATION_Y.withValue(AxisRotation.R180);
    public static final ModelVariantOperator ROTATE_Y_270 = ModelVariantOperator.ROTATION_Y.withValue(AxisRotation.R270);

    public BlockModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public String getName() {
        return "BlockModelProvider";
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {

        for (Block block : SimpleBlockModel.blocks) {
            blockStateModelGenerator.registerSimpleCubeAll(block);
        }

        for (SimpleBlockModel.ChiseledBlock block : SimpleBlockModel.chiseledMainBlockTopBottom) {
            blockStateModelGenerator.registerAxisRotated(block.base(), TexturedModel.END_FOR_TOP_CUBE_COLUMN, TexturedModel.END_FOR_TOP_CUBE_COLUMN_HORIZONTAL);
        }

        for (SimpleBlockModel.ChiseledBlock block : SimpleBlockModel.chiseledBlocksTopBottom) {
            blockStateModelGenerator.registerAxisRotated(block.base(), TexturedModel.END_FOR_TOP_CUBE_COLUMN, TexturedModel.END_FOR_TOP_CUBE_COLUMN_HORIZONTAL);
        }

        for (SimpleBlockModel.ChiseledPolishedBlock block : SimpleBlockModel.chiseledPolishedBlocksTopBottom) {
            blockStateModelGenerator.registerAxisRotated(block.base(), TexturedModel.END_FOR_TOP_CUBE_COLUMN, TexturedModel.END_FOR_TOP_CUBE_COLUMN_HORIZONTAL);
        }

        for (SimpleBlockModel.ChiseledPolishedBlock block : SimpleBlockModel.chiseledTilesBlocksTopBottom) {
            blockStateModelGenerator.registerAxisRotated(block.base(), TexturedModel.END_FOR_TOP_CUBE_COLUMN, TexturedModel.END_FOR_TOP_CUBE_COLUMN_HORIZONTAL);
        }

        for (SimpleBlockModel.ChiseledPolishedBlock block : SimpleBlockModel.chiseledSmoothBlocksTopBottom) {
            blockStateModelGenerator.registerAxisRotated(block.base(), TexturedModel.END_FOR_TOP_CUBE_COLUMN, TexturedModel.END_FOR_TOP_CUBE_COLUMN_HORIZONTAL);
        }

        for (Block wood : SimpleBlockModel.woodBlocks) {
            TextureMap textureMap = new TextureMap().put(TextureKey.ALL,
                    Identifier.of(MiddleEarth.MOD_ID, "block/" + Registries.BLOCK.getId(wood).getPath().replaceAll("_wood", "_log").replaceAll("_hyphae", "_stem").replaceAll("treated_log", "treated_wood")));
            WeightedVariant identifier = createWeightedVariant(Models.CUBE_COLUMN.upload(wood, textureMap, blockStateModelGenerator.modelCollector));
            blockStateModelGenerator.blockStateCollector.accept(BlockStateModelGenerator.createAxisRotatedBlockState(wood, identifier));
        }

        for (SimpleBlockModel.ChiseledPolishedBlock block : SimpleBlockModel.chiseledPolishedBlocks) {
            blockStateModelGenerator.registerSimpleCubeAll(block.base());
        }

        for (SimplePillarModel.Pillar block : SimplePillarModel.blocks) {
            blockStateModelGenerator.registerAxisRotated(block.base(), TexturedModel.END_FOR_TOP_CUBE_COLUMN, TexturedModel.END_FOR_TOP_CUBE_COLUMN_HORIZONTAL);
        }

        for (SimplePillarModel.StonePillar block : SimplePillarModel.stonePillars) {
            blockStateModelGenerator.registerAxisRotated(block.base(), TexturedModel.END_FOR_TOP_CUBE_COLUMN, TexturedModel.END_FOR_TOP_CUBE_COLUMN_HORIZONTAL);
        }

        for (SimplePillarModel.StonePillar block : SimplePillarModel.carvedWindows) {
            TextureMap textureMap;
            if (block.origin() == Blocks.BASALT || block.origin() == Blocks.DEEPSLATE){
                textureMap = TextureMap.sideEnd(TextureMap.getId(block.base()), TextureMap.getSubId(block.origin(), "_top"));
            } else {
                textureMap = TextureMap.sideEnd(TextureMap.getId(block.base()), TextureMap.getId(block.origin()));
            }
            WeightedVariant weightedVariant = createWeightedVariant(Models.CUBE_COLUMN.upload(block.base(), textureMap, blockStateModelGenerator.modelCollector));
            blockStateModelGenerator.blockStateCollector.accept(createSingletonBlockState(block.base(), weightedVariant));
        }

        for (SimpleSlabModel.Slab block : SimpleSlabModel.slabs) {
            WeightedVariant id = createWeightedVariant(ModelIds.getBlockModelId(block.origin()));
            Block slab = block.slab();

            TexturedModel texturedModel = TexturedModel.CUBE_ALL.get(block.origin());
            if (block.origin() == Blocks.BASALT || block.origin() == Blocks.POLISHED_BASALT) {
                texturedModel = TexturedModel.getCubeAll(Identifier.of("minecraft", "block/" + Registries.BLOCK.getId(block.origin()).getPath() + "_side"));
            }
            WeightedVariant bottom = createWeightedVariant(Models.SLAB.upload(slab, texturedModel.getTextures(), blockStateModelGenerator.modelCollector));
            WeightedVariant top = createWeightedVariant(Models.SLAB_TOP.upload(slab, texturedModel.getTextures(), blockStateModelGenerator.modelCollector));

            blockStateModelGenerator.blockStateCollector.accept(BlockStateModelGenerator
                    .createSlabBlockState(slab, bottom, top, id));
        }

        for (SimpleSlabModel.Slab block : SimpleSlabModel.woodSlabs) {
            WeightedVariant id = createWeightedVariant(ModelIds.getBlockModelId(block.origin()));
            Block slab = block.slab();

            String modid = Registries.BLOCK.getId(block.origin()).getNamespace();

            TexturedModel texturedModel = TexturedModel.getCubeAll(Identifier.of(modid, "block/" + Registries.BLOCK.getId(block.origin()).getPath().replaceAll("_wood", "_log").replaceAll("_hyphae", "_stem").replaceAll("treated_log", "treated_wood")));
            WeightedVariant bottom = createWeightedVariant(Models.SLAB.upload(slab, texturedModel.getTextures(), blockStateModelGenerator.modelCollector));
            WeightedVariant top = createWeightedVariant(Models.SLAB_TOP.upload(slab, texturedModel.getTextures(), blockStateModelGenerator.modelCollector));

            blockStateModelGenerator.blockStateCollector.accept(BlockStateModelGenerator
                    .createSlabBlockState(slab, bottom, top, id));
        }

        for (SimpleSlabModel.Slab block : SimpleSlabModel.strippedSlabs) {
            WeightedVariant id = createWeightedVariant(ModelIds.getBlockModelId(block.origin()));
            Block slab = block.slab();

            String modid = Registries.BLOCK.getId(block.origin()).getNamespace();

            TexturedModel texturedModel = TexturedModel.getCubeAll(Identifier.of(modid, "block/" + Registries.BLOCK.getId(block.origin()).getPath().replaceAll("_wood", "_log").replaceAll("_hyphae", "_stem").replaceAll("treated_log", "treated_wood")));
            WeightedVariant bottom = createWeightedVariant(Models.SLAB.upload(slab, texturedModel.getTextures(), blockStateModelGenerator.modelCollector));
            WeightedVariant top = createWeightedVariant(Models.SLAB_TOP.upload(slab, texturedModel.getTextures(), blockStateModelGenerator.modelCollector));

            blockStateModelGenerator.blockStateCollector.accept(BlockStateModelGenerator
                    .createSlabBlockState(slab, bottom, top, id));
        }

        for (SimpleSlabModel.Slab block : SimpleSlabModel.vanillaSlabs) {
            WeightedVariant id = createWeightedVariant(ModelIds.getBlockModelId(block.origin()));
            Block slab = block.slab();

            TexturedModel texturedModel = TexturedModel.getCubeAll(Identifier.of("minecraft", "block/" + Registries.BLOCK.getId(block.origin()).getPath().replaceAll("_wood", "_log").replaceAll("_hyphae", "_stem").replaceAll("treated_log", "treated_wood")));
            WeightedVariant bottom = createWeightedVariant(Models.SLAB.upload(slab, texturedModel.getTextures(), blockStateModelGenerator.modelCollector));
            WeightedVariant top = createWeightedVariant(Models.SLAB_TOP.upload(slab, texturedModel.getTextures(), blockStateModelGenerator.modelCollector));

            blockStateModelGenerator.blockStateCollector.accept(BlockStateModelGenerator
                    .createSlabBlockState(slab, bottom, top, id));
        }

        for (SimpleSlabModel.Slab block : SimpleSlabModel.vanillaWoodSlabs) {
            WeightedVariant id = createWeightedVariant(ModelIds.getBlockModelId(block.origin()));
            Block slab = block.slab();

            TexturedModel texturedModel = TexturedModel.getCubeAll(Identifier.of("minecraft", "block/" + Registries.BLOCK.getId(block.origin()).getPath().replaceAll("_wood", "_log").replaceAll("_hyphae", "_stem").replaceAll("treated_log", "treated_wood")));
            WeightedVariant bottom = createWeightedVariant(Models.SLAB.upload(slab, texturedModel.getTextures(), blockStateModelGenerator.modelCollector));
            WeightedVariant top = createWeightedVariant(Models.SLAB_TOP.upload(slab, texturedModel.getTextures(), blockStateModelGenerator.modelCollector));

            blockStateModelGenerator.blockStateCollector.accept(BlockStateModelGenerator
                    .createSlabBlockState(slab, bottom, top, id));
        }

        for (SimpleSlabModel.Slab block : SimpleSlabModel.vanillaStrippedSlab) {
            WeightedVariant id = createWeightedVariant(ModelIds.getBlockModelId(block.origin()));
            Block slab = block.slab();

            TexturedModel texturedModel = TexturedModel.getCubeAll(Identifier.of("minecraft", "block/" + Registries.BLOCK.getId(block.origin()).getPath().replaceAll("_wood", "_log").replaceAll("_hyphae", "_stem").replaceAll("treated_log", "treated_wood")));
            WeightedVariant bottom = createWeightedVariant(Models.SLAB.upload(slab, texturedModel.getTextures(), blockStateModelGenerator.modelCollector));
            WeightedVariant top = createWeightedVariant(Models.SLAB_TOP.upload(slab, texturedModel.getTextures(), blockStateModelGenerator.modelCollector));

            blockStateModelGenerator.blockStateCollector.accept(BlockStateModelGenerator
                    .createSlabBlockState(slab, bottom, top, id));
        }

        for (SimpleStairModel.Stair block : SimpleStairModel.stairs) {
            TexturedModel texturedModel = TexturedModel.CUBE_ALL.get(block.origin());
            if (block.origin() == Blocks.BASALT || block.origin() == Blocks.POLISHED_BASALT) {
                texturedModel = TexturedModel.getCubeAll(Identifier.of("minecraft", "block/" + Registries.BLOCK.getId(block.origin()).getPath() + "_side"));
            }
            Block stairs = block.stairs();

            WeightedVariant inner = createWeightedVariant(Models.INNER_STAIRS.upload(stairs, texturedModel.getTextures(), blockStateModelGenerator.modelCollector));
            WeightedVariant regular = createWeightedVariant(Models.STAIRS.upload(stairs, texturedModel.getTextures(), blockStateModelGenerator.modelCollector));
            WeightedVariant outer = createWeightedVariant(Models.OUTER_STAIRS.upload(stairs, texturedModel.getTextures(), blockStateModelGenerator.modelCollector));

            blockStateModelGenerator.blockStateCollector.accept(BlockStateModelGenerator
                    .createStairsBlockState(stairs, inner, regular, outer));
        }

        for (SimpleStairModel.Stair block : SimpleStairModel.woodStairs) {
            String modid = Registries.BLOCK.getId(block.origin()).getNamespace();

            TexturedModel texturedModel = TexturedModel.getCubeAll(Identifier.of(modid, "block/" + Registries.BLOCK.getId(block.origin()).getPath().replaceAll("_wood", "_log").replaceAll("_hyphae", "_stem").replaceAll("treated_log", "treated_wood")));
            Block stairs = block.stairs();

            WeightedVariant inner = createWeightedVariant(Models.INNER_STAIRS.upload(stairs, texturedModel.getTextures(), blockStateModelGenerator.modelCollector));
            WeightedVariant regular = createWeightedVariant(Models.STAIRS.upload(stairs, texturedModel.getTextures(), blockStateModelGenerator.modelCollector));
            WeightedVariant outer = createWeightedVariant(Models.OUTER_STAIRS.upload(stairs, texturedModel.getTextures(), blockStateModelGenerator.modelCollector));

            blockStateModelGenerator.blockStateCollector.accept(BlockStateModelGenerator
                    .createStairsBlockState(stairs, inner, regular, outer));
        }

        for (SimpleStairModel.Stair block : SimpleStairModel.strippedStairs) {
            String modid = Registries.BLOCK.getId(block.origin()).getNamespace();

            TexturedModel texturedModel = TexturedModel.getCubeAll(Identifier.of(modid, "block/" + Registries.BLOCK.getId(block.origin()).getPath().replaceAll("_wood", "_log").replaceAll("_hyphae", "_stem").replaceAll("treated_log", "treated_wood")));
            Block stairs = block.stairs();

            WeightedVariant inner = createWeightedVariant(Models.INNER_STAIRS.upload(stairs, texturedModel.getTextures(), blockStateModelGenerator.modelCollector));
            WeightedVariant regular = createWeightedVariant(Models.STAIRS.upload(stairs, texturedModel.getTextures(), blockStateModelGenerator.modelCollector));
            WeightedVariant outer = createWeightedVariant(Models.OUTER_STAIRS.upload(stairs, texturedModel.getTextures(), blockStateModelGenerator.modelCollector));

            blockStateModelGenerator.blockStateCollector.accept(BlockStateModelGenerator
                    .createStairsBlockState(stairs, inner, regular, outer));
        }

        for (SimpleStairModel.Stair block : SimpleStairModel.vanillaWoodStairs) {
            TexturedModel texturedModel = TexturedModel.getCubeAll(Identifier.of("minecraft", "block/" + Registries.BLOCK.getId(block.origin()).getPath().replaceAll("_wood", "_log").replaceAll("_hyphae", "_stem").replaceAll("treated_log", "treated_wood")));
            Block stairs = block.stairs();

            WeightedVariant inner = createWeightedVariant(Models.INNER_STAIRS.upload(stairs, texturedModel.getTextures(), blockStateModelGenerator.modelCollector));
            WeightedVariant regular = createWeightedVariant(Models.STAIRS.upload(stairs, texturedModel.getTextures(), blockStateModelGenerator.modelCollector));
            WeightedVariant outer = createWeightedVariant(Models.OUTER_STAIRS.upload(stairs, texturedModel.getTextures(), blockStateModelGenerator.modelCollector));

            blockStateModelGenerator.blockStateCollector.accept(BlockStateModelGenerator
                    .createStairsBlockState(stairs, inner, regular, outer));
        }

        for (SimpleStairModel.Stair block : SimpleStairModel.vanillaStrippedStairs) {
            TexturedModel texturedModel = TexturedModel.getCubeAll(Identifier.of("minecraft", "block/" + Registries.BLOCK.getId(block.origin()).getPath().replaceAll("_wood", "_log").replaceAll("_hyphae", "_stem").replaceAll("treated_log", "treated_wood")));
            Block stairs = block.stairs();

            WeightedVariant inner = createWeightedVariant(Models.INNER_STAIRS.upload(stairs, texturedModel.getTextures(), blockStateModelGenerator.modelCollector));
            WeightedVariant regular = createWeightedVariant(Models.STAIRS.upload(stairs, texturedModel.getTextures(), blockStateModelGenerator.modelCollector));
            WeightedVariant outer = createWeightedVariant(Models.OUTER_STAIRS.upload(stairs, texturedModel.getTextures(), blockStateModelGenerator.modelCollector));

            blockStateModelGenerator.blockStateCollector.accept(BlockStateModelGenerator
                    .createStairsBlockState(stairs, inner, regular, outer));
        }

        for (SimpleStairModel.Stair block : SimpleStairModel.vanillaStairs) {
            TexturedModel texturedModel = TexturedModel.CUBE_ALL.get(block.origin());
            Block stairs = block.stairs();

            WeightedVariant inner = createWeightedVariant(Models.INNER_STAIRS.upload(stairs, texturedModel.getTextures(), blockStateModelGenerator.modelCollector));
            WeightedVariant regular = createWeightedVariant(Models.STAIRS.upload(stairs, texturedModel.getTextures(), blockStateModelGenerator.modelCollector));
            WeightedVariant outer = createWeightedVariant(Models.OUTER_STAIRS.upload(stairs, texturedModel.getTextures(), blockStateModelGenerator.modelCollector));

            blockStateModelGenerator.blockStateCollector.accept(BlockStateModelGenerator
                    .createStairsBlockState(stairs, inner, regular, outer));
        }

        for (SimpleWallModel.Wall block : SimpleWallModel.blocks) {
            TexturedModel texturedModel = TexturedModel.getCubeAll(Identifier.of(
                    Registries.BLOCK.getId(block.block()).getNamespace(), "block/" + Registries.BLOCK.getId(block.block()).getPath().replaceAll("_wood","_log").replaceAll("treated_log", "treated_wood")));
            if (block.block() == Blocks.BASALT || block.block() == Blocks.POLISHED_BASALT) {
                texturedModel = TexturedModel.getCubeAll(Identifier.of("minecraft", "block/" + Registries.BLOCK.getId(block.block()).getPath() + "_side"));
            }

            if (block.block() == Blocks.CRIMSON_HYPHAE || block.block() == Blocks.WARPED_HYPHAE) {
                texturedModel = TexturedModel.getCubeAll(Identifier.of("minecraft", "block/" + Registries.BLOCK.getId(block.block()).getPath().replaceAll("_hyphae", "_stem")));
            }

            Block wall = block.wall();

            Identifier inventory = Models.WALL_INVENTORY.upload(wall, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);

            blockStateModelGenerator.registerParentedItemModel(wall,inventory);

            WeightedVariant post = createWeightedVariant(Models.TEMPLATE_WALL_POST.upload(wall, texturedModel.getTextures(), blockStateModelGenerator.modelCollector));
            WeightedVariant low = createWeightedVariant(Models.TEMPLATE_WALL_SIDE.upload(wall, texturedModel.getTextures(), blockStateModelGenerator.modelCollector));
            WeightedVariant tall = createWeightedVariant(Models.TEMPLATE_WALL_SIDE_TALL.upload(wall, texturedModel.getTextures(), blockStateModelGenerator.modelCollector));

            blockStateModelGenerator.blockStateCollector.accept(BlockStateModelGenerator
                    .createWallBlockState(wall, post, low, tall));
        }

        for (SimpleWallModel.Wall block : SimpleWallModel.vanillaWalls) {
            TexturedModel texturedModel;
            if (Registries.BLOCK.getId(block.block()).getPath().contains("waxed_") && Registries.BLOCK.getId(block.block()).getPath().contains("cut_copper")) {
                texturedModel = TexturedModel.getCubeAll(Identifier.of("minecraft", "block/" + Registries.BLOCK.getId(block.block()).getPath().replaceAll("waxed_", "")));
            } else {
                texturedModel = TexturedModel.getCubeAll(Identifier.of("minecraft", "block/" + Registries.BLOCK.getId(block.block()).getPath()));

            }
            Block wall = block.wall();

            Identifier inventory = Models.WALL_INVENTORY.upload(wall, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);

            blockStateModelGenerator.registerParentedItemModel(wall,inventory);

            WeightedVariant post = createWeightedVariant(Models.TEMPLATE_WALL_POST.upload(wall, texturedModel.getTextures(), blockStateModelGenerator.modelCollector));
            WeightedVariant low = createWeightedVariant(Models.TEMPLATE_WALL_SIDE.upload(wall, texturedModel.getTextures(), blockStateModelGenerator.modelCollector));
            WeightedVariant tall = createWeightedVariant(Models.TEMPLATE_WALL_SIDE_TALL.upload(wall, texturedModel.getTextures(), blockStateModelGenerator.modelCollector));


            blockStateModelGenerator.blockStateCollector.accept(BlockStateModelGenerator
                    .createWallBlockState(wall, post, low, tall));
        }

        for (SimpleWallModel.Wall block : SimpleWallModel.strippedWalls) {
            String modid = Registries.BLOCK.getId(block.block()).getNamespace();

            TexturedModel texturedModel = TexturedModel.getCubeAll(Identifier.of(modid, "block/" + Registries.BLOCK.getId(block.block()).getPath().replaceAll("_wood", "_log").replaceAll("_hyphae", "_stem").replaceAll("treated_log", "treated_wood")));
            Block wall = block.wall();

            Identifier inventory = Models.WALL_INVENTORY.upload(wall, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);

            blockStateModelGenerator.registerParentedItemModel(wall, inventory);

            WeightedVariant post = createWeightedVariant(Models.TEMPLATE_WALL_POST.upload(wall, texturedModel.getTextures(), blockStateModelGenerator.modelCollector));
            WeightedVariant low = createWeightedVariant(Models.TEMPLATE_WALL_SIDE.upload(wall, texturedModel.getTextures(), blockStateModelGenerator.modelCollector));
            WeightedVariant tall = createWeightedVariant(Models.TEMPLATE_WALL_SIDE_TALL.upload(wall, texturedModel.getTextures(), blockStateModelGenerator.modelCollector));

            blockStateModelGenerator.blockStateCollector.accept(BlockStateModelGenerator
                    .createWallBlockState(wall, post, low, tall));
        }

        for (SimpleWallModel.Wall block : SimpleWallModel.vanillaStrippedWalls) {
            TexturedModel texturedModel = TexturedModel.getCubeAll(Identifier.of("minecraft", "block/" + Registries.BLOCK.getId(block.block()).getPath().replaceAll("_wood", "_log").replaceAll("_hyphae", "_stem").replaceAll("treated_log", "treated_wood")));
            Block wall = block.wall();

            Identifier inventory = Models.WALL_INVENTORY.upload(wall, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);

            blockStateModelGenerator.registerParentedItemModel(wall, inventory);

            WeightedVariant post = createWeightedVariant(Models.TEMPLATE_WALL_POST.upload(wall, texturedModel.getTextures(), blockStateModelGenerator.modelCollector));
            WeightedVariant low = createWeightedVariant(Models.TEMPLATE_WALL_SIDE.upload(wall, texturedModel.getTextures(), blockStateModelGenerator.modelCollector));
            WeightedVariant tall = createWeightedVariant(Models.TEMPLATE_WALL_SIDE_TALL.upload(wall, texturedModel.getTextures(), blockStateModelGenerator.modelCollector));

            blockStateModelGenerator.blockStateCollector.accept(BlockStateModelGenerator
                    .createWallBlockState(wall, post, low, tall));
        }

        for (SimpleWallModel.Wall block : SimpleWallModel.vanillaWoodWalls) {
            TexturedModel texturedModel = TexturedModel.getCubeAll(Identifier.of("minecraft", "block/" + Registries.BLOCK.getId(block.block()).getPath().replaceAll("_wood", "_log").replaceAll("_hyphae", "_stem").replaceAll("treated_log", "treated_wood")));
            Block wall = block.wall();

            Identifier inventory = Models.WALL_INVENTORY.upload(wall, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);

            blockStateModelGenerator.registerParentedItemModel(wall, inventory);

            WeightedVariant post = createWeightedVariant(Models.TEMPLATE_WALL_POST.upload(wall, texturedModel.getTextures(), blockStateModelGenerator.modelCollector));
            WeightedVariant low = createWeightedVariant(Models.TEMPLATE_WALL_SIDE.upload(wall, texturedModel.getTextures(), blockStateModelGenerator.modelCollector));
            WeightedVariant tall = createWeightedVariant(Models.TEMPLATE_WALL_SIDE_TALL.upload(wall, texturedModel.getTextures(), blockStateModelGenerator.modelCollector));

            blockStateModelGenerator.blockStateCollector.accept(BlockStateModelGenerator
                    .createWallBlockState(wall, post, low, tall));
        }

        for (SimpleFenceModel.Fence block : SimpleFenceModel.blocks) {
            TexturedModel texturedModel = TexturedModel.getCubeAll(Identifier.of(
                    Registries.BLOCK.getId(block.block()).getNamespace(), "block/" + Registries.BLOCK.getId(block.block()).getPath().replaceAll("_wood","_log")));
            if (block.block() == Blocks.CRIMSON_HYPHAE || block.block() == Blocks.WARPED_HYPHAE) {
                texturedModel = TexturedModel.getCubeAll(Identifier.of("minecraft", "block/" + Registries.BLOCK.getId(block.block()).getPath().replaceAll("_hyphae", "_stem")));
            }
            Block fence = block.fence();

            WeightedVariant post = createWeightedVariant(Models.FENCE_POST.upload(fence, texturedModel.getTextures(), blockStateModelGenerator.modelCollector));
            WeightedVariant side = createWeightedVariant(Models.FENCE_SIDE.upload(fence, texturedModel.getTextures(), blockStateModelGenerator.modelCollector));
            Identifier inventory = Models.FENCE_INVENTORY.upload(fence, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);

            blockStateModelGenerator.blockStateCollector.accept(BlockStateModelGenerator
                    .createFenceBlockState(fence, post, side));

            blockStateModelGenerator.registerParentedItemModel(fence, inventory);
        }

        for (SimpleFenceModel.Fence block : SimpleFenceModel.strippedFences) {
            String modid = Registries.BLOCK.getId(block.block()).getNamespace();

            TexturedModel texturedModel = TexturedModel.getCubeAll(Identifier.of(modid, "block/" + Registries.BLOCK.getId(block.block()).getPath().replaceAll("_wood", "_log").replaceAll("_hyphae", "_stem").replaceAll("treated_log", "treated_wood")));
            Block fence = block.fence();

            WeightedVariant post = createWeightedVariant(Models.FENCE_POST.upload(fence, texturedModel.getTextures(), blockStateModelGenerator.modelCollector));
            WeightedVariant side = createWeightedVariant(Models.FENCE_SIDE.upload(fence, texturedModel.getTextures(), blockStateModelGenerator.modelCollector));
            Identifier inventory = Models.FENCE_INVENTORY.upload(fence, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);

            blockStateModelGenerator.blockStateCollector.accept(BlockStateModelGenerator
                    .createFenceBlockState(fence, post, side));

            blockStateModelGenerator.registerParentedItemModel(fence, inventory);
        }

        for (SimpleFenceModel.Fence block : SimpleFenceModel.vanillaStrippedFences) {
            TexturedModel texturedModel = TexturedModel.getCubeAll(Identifier.of("minecraft", "block/" + Registries.BLOCK.getId(block.block()).getPath().replaceAll("_wood", "_log").replaceAll("_hyphae", "_stem").replaceAll("treated_log", "treated_wood")));
            Block fence = block.fence();

            WeightedVariant post = createWeightedVariant(Models.FENCE_POST.upload(fence, texturedModel.getTextures(), blockStateModelGenerator.modelCollector));
            WeightedVariant side = createWeightedVariant(Models.FENCE_SIDE.upload(fence, texturedModel.getTextures(), blockStateModelGenerator.modelCollector));
            Identifier inventory = Models.FENCE_INVENTORY.upload(fence, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);

            blockStateModelGenerator.blockStateCollector.accept(BlockStateModelGenerator
                    .createFenceBlockState(fence, post, side));

            blockStateModelGenerator.registerParentedItemModel(fence, inventory);
        }

        for (SimpleFenceModel.Fence block : SimpleFenceModel.vanillaWoodFences) {
            TexturedModel texturedModel = TexturedModel.getCubeAll(Identifier.of("minecraft", "block/" + Registries.BLOCK.getId(block.block()).getPath().replaceAll("_wood", "_log").replaceAll("_hyphae", "_stem").replaceAll("treated_log", "treated_wood")));
            Block fence = block.fence();

            WeightedVariant post = createWeightedVariant(Models.FENCE_POST.upload(fence, texturedModel.getTextures(), blockStateModelGenerator.modelCollector));
            WeightedVariant side = createWeightedVariant(Models.FENCE_SIDE.upload(fence, texturedModel.getTextures(), blockStateModelGenerator.modelCollector));
            Identifier inventory = Models.FENCE_INVENTORY.upload(fence, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);

            blockStateModelGenerator.blockStateCollector.accept(BlockStateModelGenerator
                    .createFenceBlockState(fence, post, side));

            blockStateModelGenerator.registerParentedItemModel(fence, inventory);
        }

        for (SimpleFenceGateModel.FenceGate block : SimpleFenceGateModel.blocks) {
            TexturedModel texturedModel = TexturedModel.CUBE_ALL.get(block.block());
            Block fenceGate = block.fenceGate();

            WeightedVariant open = createWeightedVariant(Models.TEMPLATE_FENCE_GATE_OPEN.upload(fenceGate, texturedModel.getTextures(), blockStateModelGenerator.modelCollector));
            WeightedVariant closed = createWeightedVariant(Models.TEMPLATE_FENCE_GATE.upload(fenceGate, texturedModel.getTextures(), blockStateModelGenerator.modelCollector));
            WeightedVariant openWall = createWeightedVariant(Models.TEMPLATE_FENCE_GATE_WALL_OPEN.upload(fenceGate, texturedModel.getTextures(), blockStateModelGenerator.modelCollector));
            WeightedVariant closedWall = createWeightedVariant(Models.TEMPLATE_FENCE_GATE_WALL.upload(fenceGate, texturedModel.getTextures(), blockStateModelGenerator.modelCollector));

            blockStateModelGenerator.blockStateCollector.accept(BlockStateModelGenerator
                    .createFenceGateBlockState(fenceGate, open, closed, openWall, closedWall, true));
        }

        for (SimpleButtonModel.Button block : SimpleButtonModel.buttons) {
            TexturedModel texturedModel = TexturedModel.CUBE_ALL.get(block.block());
            if (block.block() == Blocks.BASALT || block.block() == Blocks.POLISHED_BASALT) {
                texturedModel = TexturedModel.getCubeAll(Identifier.of("minecraft", "block/" + Registries.BLOCK.getId(block.block()).getPath() + "_side"));
            }
            Block button = block.button();

            WeightedVariant unpressed = createWeightedVariant(Models.BUTTON.upload(button, texturedModel.getTextures(), blockStateModelGenerator.modelCollector));
            WeightedVariant pressed = createWeightedVariant(Models.BUTTON_PRESSED.upload(button, texturedModel.getTextures(), blockStateModelGenerator.modelCollector));

            Identifier inventory = Models.BUTTON_INVENTORY.upload(button, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);

            blockStateModelGenerator.blockStateCollector.accept(BlockStateModelGenerator
                    .createButtonBlockState(button, unpressed, pressed));

            blockStateModelGenerator.registerParentedItemModel(button, inventory);
        }

        for (SimplePressurePlateModel.PressurePlate block : SimplePressurePlateModel.pressurePlates) {
            TexturedModel texturedModel = TexturedModel.CUBE_ALL.get(block.block());
            if (block.block() == Blocks.BASALT || block.block() == Blocks.POLISHED_BASALT) {
                texturedModel = TexturedModel.getCubeAll(Identifier.of("minecraft", "block/" + Registries.BLOCK.getId(block.block()).getPath() + "_side"));
            }
            Block pressurePlate = block.pressurePlate();

            WeightedVariant up = createWeightedVariant(Models.PRESSURE_PLATE_UP.upload(pressurePlate, texturedModel.getTextures(), blockStateModelGenerator.modelCollector));
            WeightedVariant down = createWeightedVariant(Models.PRESSURE_PLATE_DOWN.upload(pressurePlate, texturedModel.getTextures(), blockStateModelGenerator.modelCollector));

            blockStateModelGenerator.blockStateCollector.accept(BlockStateModelGenerator
                    .createPressurePlateBlockState(pressurePlate, up, down));
        }

        for (SimpleTrapDoorModel.Trapdoor trapdoor : SimpleTrapDoorModel.trapdoors) {
            registerTrapdoor(blockStateModelGenerator, trapdoor.trapdoor(), trapdoor.block(), trapdoor.orientable());
        }

        for (SimpleLadderModel.Ladder ladder : SimpleLadderModel.ladders) {
            registerOrientableThickLadder(blockStateModelGenerator, ladder.ladder());
        }

        for (SimpleLadderModel.Ladder ladder : SimpleLadderModel.vanillaLadders) {
            registerOrientableThickLadder(blockStateModelGenerator, ladder.ladder());
        }

        for (SimpleDoorModel.Door door : SimpleDoorModel.doors) {
            blockStateModelGenerator.registerDoor(door.door());
        }

        //TODO find out why some blocks are null here
        for (Block block : TintableCrossModel.notTintedBlocks) {
            if (block != null)
                blockStateModelGenerator.registerTintableCross(block, BlockStateModelGenerator.CrossType.NOT_TINTED);
        }

        blockStateModelGenerator.registerPlantPart(ModNatureBlocks.GLOWWORM_WEBBING, ModNatureBlocks.GLOWWORM_MAIN, BlockStateModelGenerator.CrossType.NOT_TINTED);
        blockStateModelGenerator.registerPlantPart(ModNatureBlocks.MIRKWOOD_VINES, ModNatureBlocks.MIRKWOOD_VINES_PLANT, BlockStateModelGenerator.CrossType.NOT_TINTED);
        blockStateModelGenerator.registerItemModel(ModNatureBlocks.GLOWWORM_WEBBING);
        blockStateModelGenerator.registerItemModel(ModNatureBlocks.MIRKWOOD_VINES);

        for (Block block : TintableCrossModel.tintedBlocks) {
            blockStateModelGenerator.registerTintableCrossBlockState(block, BlockStateModelGenerator.CrossType.TINTED);
            blockStateModelGenerator.registerTintedItemModel(block, blockStateModelGenerator.uploadBlockItemModel(block.asItem(), block), new GrassTintSource());
        }

        for (Block block : TintableCrossModel.grassLikeBlocks) {
            blockStateModelGenerator.registerTintableCross(block, BlockStateModelGenerator.CrossType.NOT_TINTED);
        }

        for (Block block : TintableCrossModel.largePlants) {
            registerLargePlant(blockStateModelGenerator, block);
        }

        registerTintableLargePlant(blockStateModelGenerator, ModNatureBlocks.LARGE_BUSH);

        for (Block block : SimpleFlowerBedModel.flowerBeds) {
            blockStateModelGenerator.registerFlowerbed(block);
        }

        for (SimpleFlowerPotModel.FlowerPot flowerPot : SimpleFlowerPotModel.pots) {
            registerFlowerPotPlant(blockStateModelGenerator, flowerPot.plant(), flowerPot.pottedPlant(), BlockStateModelGenerator.CrossType.NOT_TINTED);
        }

        for (Block block : SimpleDoubleBlockModel.doubleBlocks) {
            registerDoubleBlock(blockStateModelGenerator, block, BlockStateModelGenerator.CrossType.NOT_TINTED);
        }

        for (Block block : SimpleDoubleBlockModel.doubleBlocksItems) {
            registerDoubleBlock(blockStateModelGenerator, block, BlockStateModelGenerator.CrossType.NOT_TINTED);
        }

        for (Block block : SimpleMushroomBlockModel.mushroomBlocks) {
            blockStateModelGenerator.registerMushroomBlock(block);
        }

        for (SimpleVerticalSlabModel.VerticalSlab verticalSlab : SimpleVerticalSlabModel.verticalSlabs) {
            String id = Registries.BLOCK.getId(verticalSlab.block()).getPath();
            if (verticalSlab.block() == Blocks.BASALT || verticalSlab.block() == Blocks.POLISHED_BASALT) {
                id = id + "_side";
            }
            registerVerticalSlabModelBlockStates(blockStateModelGenerator, verticalSlab.verticalSlab(), verticalSlab.block(), id);
        }

        for (SimpleVerticalSlabModel.VerticalSlab verticalSlab : SimpleVerticalSlabModel.woodVerticalSlabs) {
            registerVerticalSlabModelBlockStates(blockStateModelGenerator, verticalSlab.verticalSlab(), verticalSlab.block(),
                    Registries.BLOCK.getId(verticalSlab.block()).getPath().replaceAll("_wood", "_log").replaceAll("_hyphae", "_stem").replaceAll("treated_log", "treated_wood"));
        }

        for (SimpleVerticalSlabModel.VerticalSlab verticalSlab : SimpleVerticalSlabModel.strippedVerticalSlabs) {
            registerVerticalSlabModelBlockStates(blockStateModelGenerator, verticalSlab.verticalSlab(), verticalSlab.block(),
                    Registries.BLOCK.getId(verticalSlab.block()).getPath().replaceAll("_wood", "_log").replaceAll("_hyphae", "_stem").replaceAll("treated_log", "treated_wood"));
        }

        for (SimpleVerticalSlabModel.VerticalSlab verticalSlab : SimpleVerticalSlabModel.plansVerticalSlabs) {
            registerVerticalSlabModelBlockStates(blockStateModelGenerator, verticalSlab.verticalSlab(), verticalSlab.block(), Registries.BLOCK.getId(verticalSlab.block()).getPath());
        }

        for (SimpleLayersModel.Layers block : SimpleLayersModel.layers) {
            registerLayers(blockStateModelGenerator, block.layers(), block.origin(), false);
        }

        for (SimpleLayersModel.Layers block : SimpleLayersModel.vanillaLayers) {
            registerLayers(blockStateModelGenerator, block.layers(), block.origin(), true);
        }

        for (SimplePaneModel.Pane pane : SimplePaneModel.panes) {
            registerGlassAndPane(blockStateModelGenerator, pane.glass(), pane.pane());
        }

        for(Block block : SimpleWoodStoolModel.stools){
            registerWoodStoolModelBlockStates(blockStateModelGenerator, block);
        }

        for(Block block : SimpleWoodBenchModel.benchs){
            registerWoodBenchModelBlockStates(blockStateModelGenerator, block);
        }

        for(SimpleStoneStoolModel.Stool stool : SimpleStoneStoolModel.stools){
            String id = "block/" + Registries.BLOCK.getId(stool.base()).getPath();
            if (stool.base() == Blocks.BASALT) id += "_side";
            registerStoneStoolModelBlockStates(blockStateModelGenerator, stool.stool(),
                    Identifier.of(Registries.BLOCK.getId(stool.base()).getNamespace(), id));
        }

        for(SimpleStoneTableModel.Table table : SimpleStoneTableModel.tables) {
            String id = "block/" + Registries.BLOCK.getId(table.base()).getPath();
            if(table.base() == Blocks.BASALT) id += "_side";
            registerStoneTableModelBlockStates(blockStateModelGenerator, table.table(), Identifier.of(Registries.BLOCK.getId(table.base()).getNamespace(), id));
        }

        for(SimpleStoneChairModel.Chair chair : SimpleStoneChairModel.chairs){
            String id = "block/" + Registries.BLOCK.getId(chair.base()).getPath();
            if(chair.base() == Blocks.BASALT) id += "_side";
            registerStoneChairModelBlockStates(blockStateModelGenerator, chair.chair(), Identifier.of(Registries.BLOCK.getId(chair.base()).getNamespace(), id));
        }

        for(Block block : SimpleWoodTableModel.tables){
            registerWoodTableModelBlockStates(blockStateModelGenerator, block);
        }

        for(Block block : SimpleWoodChairModel.chairs){
            registerWoodChairModelBlockStates(blockStateModelGenerator, block);
        }

        for(Block block : SimpleFanModel.grassLikeFans){
            registerFanModel(blockStateModelGenerator, block);
        }

        for (SimpleRocksModel.Rocks rocks : SimpleRocksModel.rocks) {
            registerRocksBlock(blockStateModelGenerator, rocks.rocks(), rocks.block());
        }

        for (SimpleRocksModel.Rocks rocks : SimpleRocksModel.vanillaRocks) {
            registerRocksBlock(blockStateModelGenerator, rocks.rocks(), rocks.block());
        }

        for(SimpleWoodStoolModel.VanillaStool stool : SimpleWoodStoolModel.vanillaStools) {
            registerWoodStoolModelBlockStates(blockStateModelGenerator, stool.base());
        }

        for(SimpleWoodBenchModel.VanillaBench bench : SimpleWoodBenchModel.vanillaBenchs) {
            registerWoodBenchModelBlockStates(blockStateModelGenerator, bench.base());
        }

        for(SimpleWoodTableModel.VanillaTable table : SimpleWoodTableModel.vanillaTables) {
            registerWoodTableModelBlockStates(blockStateModelGenerator, table.base());
        }

        for(SimpleWoodChairModel.VanillaChair chair : SimpleWoodChairModel.vanillaChairs) {
            registerWoodChairModelBlockStates(blockStateModelGenerator, chair.base());
        }


        for (SimpleVerticalSlabModel.VerticalSlab verticalSlab : SimpleVerticalSlabModel.vanillaVerticalSlabs) {
            String id = String.valueOf(Registries.BLOCK.getId(verticalSlab.block()));
            id = id.substring(id.lastIndexOf(":") + 1);

            if (verticalSlab.block() == Blocks.SANDSTONE || verticalSlab.block() == Blocks.RED_SANDSTONE || verticalSlab.block() == Blocks.CUT_SANDSTONE || verticalSlab.block() == Blocks.CUT_RED_SANDSTONE) {
                String topId = id + "_top";
                String bottomId = id + "_bottom";
                if (verticalSlab.block() == Blocks.CUT_SANDSTONE || verticalSlab.block() == Blocks.CUT_RED_SANDSTONE) {
                    topId = topId.substring(topId.indexOf("_") + 1);
                    bottomId = bottomId.substring(bottomId.indexOf("_") + 1);
                }
                registerColumnVerticalSlabModelBlockStates(blockStateModelGenerator, verticalSlab.verticalSlab(), verticalSlab.block(), "minecraft", topId, bottomId, id);
            } else {
                if (verticalSlab.block() == Blocks.SMOOTH_RED_SANDSTONE
                        || verticalSlab.block() == Blocks.SMOOTH_SANDSTONE) {
                    id += "_top";
                    id = id.substring(id.indexOf("_") + 1);
                } else if (verticalSlab.block() == Blocks.QUARTZ_BLOCK) {
                    id += "_side";
                } else if (verticalSlab.block() == Blocks.SMOOTH_QUARTZ) {
                    id = "quartz_block_bottom";
                } else if (verticalSlab.block() == Blocks.WAXED_CUT_COPPER
                        || verticalSlab.block() == Blocks.WAXED_EXPOSED_CUT_COPPER
                        || verticalSlab.block() == Blocks.WAXED_WEATHERED_CUT_COPPER
                        || verticalSlab.block() == Blocks.WAXED_OXIDIZED_CUT_COPPER) {
                    id = id.substring(id.indexOf("_") + 1);
                }
                registerVanillaVerticalSlabModelBlockStates(blockStateModelGenerator, verticalSlab.verticalSlab(), verticalSlab.block(), id);
            }
        }

        for (SimpleVerticalSlabModel.VerticalSlab verticalSlab : SimpleVerticalSlabModel.columnVerticalSlabs) {
            Identifier identifier = Registries.BLOCK.getId(verticalSlab.verticalSlab());
            String sidePath = identifier.getPath().replaceAll("_vertical_slab", "");

            Identifier identifier2 = Registries.BLOCK.getId(verticalSlab.verticalSlab()).withSuffixedPath("_top");
            String topBottomPath = identifier2.getPath().replaceAll("_vertical_slab", "");
            topBottomPath = topBottomPath.replaceAll("_carved_window_top", "");

            registerColumnVerticalSlabModelBlockStates(blockStateModelGenerator, verticalSlab.verticalSlab(), verticalSlab.block(),
                    identifier.getNamespace(), topBottomPath, topBottomPath, sidePath);
        }

        for (SimpleWallModel.Wall wall : SimpleWallModel.columnWalls) {
            Identifier identifier = Registries.BLOCK.getId(wall.wall());
            String sidePath = identifier.getPath().replaceAll("_wall", "");

            Identifier identifier2 = Registries.BLOCK.getId(wall.wall()).withSuffixedPath("_top");
            String topBottomPath = identifier2.getPath().replaceAll("_wall", "");

            registerColumnWallModelBlockStates(blockStateModelGenerator, wall.wall(), wall.block(),
                    identifier.getNamespace(), topBottomPath, topBottomPath, sidePath);
        }

        for (SimpleVerticalSlabModel.VerticalSlab verticalSlab : SimpleVerticalSlabModel.vanillaWoodVerticalSlabs) {
            String id = Registries.BLOCK.getId(verticalSlab.block()).getPath();
            String baseTextureId = id.substring(0, id.lastIndexOf("_")) + "_log";
            baseTextureId = baseTextureId.replaceAll("_wood", "_log").replaceAll("_hyphae", "_stem").replaceAll("treated_log", "treated_wood");
            registerVanillaVerticalSlabModelBlockStates(blockStateModelGenerator, verticalSlab.verticalSlab(), verticalSlab.block(), baseTextureId);
        }
        for (SimpleVerticalSlabModel.VerticalSlab verticalSlab : SimpleVerticalSlabModel.vanillaStrippedVerticalSlabs) {
            String id = Registries.BLOCK.getId(verticalSlab.block()).getPath();
            String baseTextureId = id.substring(0, id.lastIndexOf("_")) + "_log";
            baseTextureId = baseTextureId.replaceAll("_wood", "_log").replaceAll("_hyphae", "_stem").replaceAll("treated_log", "treated_wood");
            registerVanillaVerticalSlabModelBlockStates(blockStateModelGenerator, verticalSlab.verticalSlab(), verticalSlab.block(), baseTextureId);
        }

        SimpleTopWaterModel.topWaterBlocks.forEach(block -> {
            registerTopWaterblock(blockStateModelGenerator, block);
        });

        // Crops
        blockStateModelGenerator.registerCrop(ModNatureBlocks.BELL_PEPPER_CROP, BellpepperCropBlock.AGE, 0, 1, 2, 3, 4);
        blockStateModelGenerator.registerCrop(ModNatureBlocks.CUCUMBER_CROP, CucumberCropBlock.AGE, 0, 1, 2, 3);
        blockStateModelGenerator.registerCrop(ModNatureBlocks.FLAX_CROP, FlaxCropBlock.AGE, 0, 1, 2, 3);
        blockStateModelGenerator.registerCrop(ModNatureBlocks.GARLIC_CROP, GarlicCropBlock.AGE, 0, 1, 2, 3);
        blockStateModelGenerator.registerCrop(ModNatureBlocks.LEEK_CROP, LeekCropBlock.AGE, 0, 1, 2, 3);
        blockStateModelGenerator.registerCrop(ModNatureBlocks.LETTUCE_CROP, LettuceCropBlock.AGE, 0, 1, 2, 3);
        blockStateModelGenerator.registerCrop(ModNatureBlocks.ONION_CROP, OnionCropBlock.AGE, 0, 1, 2, 3);

        //CLUSTERS
        blockStateModelGenerator.registerAmethyst(ModBlocks.GLOWSTONE_CLUSTER);
        blockStateModelGenerator.registerAmethyst(ModBlocks.SMALL_GLOWSTONE_BUD);
        blockStateModelGenerator.registerAmethyst(ModBlocks.MEDIUM_GLOWSTONE_BUD);
        blockStateModelGenerator.registerAmethyst(ModBlocks.LARGE_GLOWSTONE_BUD);
        blockStateModelGenerator.registerAmethyst(ModBlocks.RED_AGATE_CLUSTER);
        blockStateModelGenerator.registerAmethyst(ModBlocks.SMALL_RED_AGATE_BUD);
        blockStateModelGenerator.registerAmethyst(ModBlocks.MEDIUM_RED_AGATE_BUD);
        blockStateModelGenerator.registerAmethyst(ModBlocks.LARGE_RED_AGATE_BUD);
        blockStateModelGenerator.registerAmethyst(ModBlocks.CITRINE_CLUSTER);
        blockStateModelGenerator.registerAmethyst(ModBlocks.SMALL_CITRINE_BUD);
        blockStateModelGenerator.registerAmethyst(ModBlocks.MEDIUM_CITRINE_BUD);
        blockStateModelGenerator.registerAmethyst(ModBlocks.LARGE_CITRINE_BUD);
        blockStateModelGenerator.registerAmethyst(ModBlocks.QUARTZ_CLUSTER);
        blockStateModelGenerator.registerAmethyst(ModBlocks.SMALL_QUARTZ_BUD);
        blockStateModelGenerator.registerAmethyst(ModBlocks.MEDIUM_QUARTZ_BUD);
        blockStateModelGenerator.registerAmethyst(ModBlocks.LARGE_QUARTZ_BUD);

        registerLargeDoor(blockStateModelGenerator, (LargeDoorBlock) ModDecorativeBlocks.BLUE_HOBBIT_DOOR, LargeDoor2x2.PART);
        registerLargeDoor(blockStateModelGenerator, (LargeDoorBlock) ModDecorativeBlocks.GREEN_HOBBIT_DOOR, LargeDoor2x2.PART);
        registerLargeDoor(blockStateModelGenerator, (LargeDoorBlock) ModDecorativeBlocks.LIGHT_BLUE_HOBBIT_DOOR, LargeDoor2x2.PART);
        registerLargeDoor(blockStateModelGenerator, (LargeDoorBlock) ModDecorativeBlocks.RED_HOBBIT_DOOR, LargeDoor2x2.PART);
        registerLargeDoor(blockStateModelGenerator, (LargeDoorBlock) ModDecorativeBlocks.YELLOW_HOBBIT_DOOR, LargeDoor2x2.PART);
        registerLargeDoor(blockStateModelGenerator, (LargeDoorBlock) ModDecorativeBlocks.LARCH_HOBBIT_DOOR, LargeDoor2x2.PART);
        registerLargeDoor(blockStateModelGenerator, (LargeDoorBlock) ModDecorativeBlocks.SPRUCE_HOBBIT_DOOR, LargeDoor2x2.PART);

        registerLargeDoor(blockStateModelGenerator, (LargeDoorBlock) ModDecorativeBlocks.TALL_BLACK_PINE_DOOR, LargeDoor3x1.PART);

        registerLargeDoor(blockStateModelGenerator, (LargeDoorBlock) ModDecorativeBlocks.OAK_STABLE_DOOR, LargeDoor4x2.PART);
        registerLargeDoor(blockStateModelGenerator, (LargeDoorBlock) ModDecorativeBlocks.REINFORCED_SPRUCE_DOOR, LargeDoor4x2.PART);
        registerLargeDoor(blockStateModelGenerator, (LargeDoorBlock) ModDecorativeBlocks.REINFORCED_BLACK_PINE_DOOR, LargeDoor4x2.PART);
        registerLargeDoor(blockStateModelGenerator, (LargeDoorBlock) ModDecorativeBlocks.RICKETY_SIMPLE_LARCH_DOOR, LargeDoor4x2.PART);
        registerLargeDoor(blockStateModelGenerator, (LargeDoorBlock) ModDecorativeBlocks.SIMPLE_LARCH_GATE, LargeDoor4x2.PART);
        registerLargeDoor(blockStateModelGenerator, (LargeDoorBlock) ModDecorativeBlocks.SPRUCE_STABLE_DOOR, LargeDoor4x2.PART);

        registerLargeDoor(blockStateModelGenerator, (LargeDoorBlock) ModDecorativeBlocks.LARGE_STURDY_DOOR, LargeDoor5x3.PART);

        registerLargeDoor(blockStateModelGenerator, (LargeDoorBlock) ModDecorativeBlocks.LARGE_BEECH_FENCE_GATE, LargeDoor1x2.PART);

        registerLargeDoor(blockStateModelGenerator, (LargeDoorBlock) ModDecorativeBlocks.GREAT_GONDORIAN_GATE, LargeDoor10x5.PART);

        registerLargeDoor(blockStateModelGenerator, (LargeDoorBlock) ModDecorativeBlocks.GREAT_DWARVEN_GATE, LargeDoor5x2.PART);
        registerLargeDoor(blockStateModelGenerator, (LargeDoorBlock) ModDecorativeBlocks.VARNISHED_DWARVEN_DOOR, LargeDoor4x2.PART);
        registerLargeDoor(blockStateModelGenerator, (LargeDoorBlock) ModDecorativeBlocks.RUINED_DWARVEN_DOOR, LargeDoor4x2.PART);
        registerThickLargeDoor(blockStateModelGenerator, (LargeDoorBlock) ModDecorativeBlocks.HIDDEN_DWARVEN_DOOR, LargeThickDoor3x2.PART);

        registerLargeDoor(blockStateModelGenerator, (LargeDoorBlock) ModDecorativeBlocks.GREAT_ELVEN_GATE, LargeDoor6x2.PART);

        registerLargeDoor(blockStateModelGenerator, (LargeDoorBlock) ModDecorativeBlocks.GREAT_ORCISH_GATE, LargeDoor10x4.PART);

        registerPaneModel(blockStateModelGenerator, ModBlocks.NET);

        registerPaneModel(blockStateModelGenerator, ModBlocks.GILDED_BARS);

        registerPaneModel(blockStateModelGenerator, ModBlocks.COPPER_BARS);
        registerPaneModel(blockStateModelGenerator, ModBlocks.EXPOSED_COPPER_BARS);
        registerPaneModel(blockStateModelGenerator, ModBlocks.WEATHERED_COPPER_BARS);
        registerPaneModel(blockStateModelGenerator, ModBlocks.OXIDIZED_COPPER_BARS);

        registerPaneModel(blockStateModelGenerator, ModBlocks.WAXED_COPPER_BARS);
        registerPaneModel(blockStateModelGenerator, ModBlocks.WAXED_EXPOSED_COPPER_BARS);
        registerPaneModel(blockStateModelGenerator, ModBlocks.WAXED_WEATHERED_COPPER_BARS);
        registerPaneModel(blockStateModelGenerator, ModBlocks.WAXED_OXIDIZED_COPPER_BARS);

        registerPaneModel(blockStateModelGenerator, ModBlocks.BRONZE_BARS);
        registerPaneModel(blockStateModelGenerator, ModBlocks.CRUDE_BARS);
        registerPaneModel(blockStateModelGenerator, ModBlocks.TREATED_STEEL_BARS);
        registerPaneModel(blockStateModelGenerator, ModBlocks.SILVER_BARS);

        registerOrientableThickLadder(blockStateModelGenerator, ModDecorativeBlocks.ROPE_LADDER);

        blockStateModelGenerator.registerMultifaceBlock(ModNatureBlocks.AZALEA_FLOWER_GROWTH);
        blockStateModelGenerator.registerMultifaceBlock(ModNatureBlocks.DRY_GROWTH);
        blockStateModelGenerator.registerMultifaceBlock(ModNatureBlocks.FROZEN_GROWTH);
        blockStateModelGenerator.registerMultifaceBlock(ModNatureBlocks.GREEN_GROWTH);
        blockStateModelGenerator.registerMultifaceBlock(ModNatureBlocks.IVY_GROWTH);
        blockStateModelGenerator.registerMultifaceBlock(ModNatureBlocks.LILAC_FLOWER_GROWTH);
        blockStateModelGenerator.registerMultifaceBlock(ModNatureBlocks.PINK_FLOWER_GROWTH);
        blockStateModelGenerator.registerMultifaceBlock(ModNatureBlocks.RED_FLOWER_GROWTH);
        blockStateModelGenerator.registerMultifaceBlock(ModNatureBlocks.WHITE_FLOWER_GROWTH);
        blockStateModelGenerator.registerMultifaceBlock(ModNatureBlocks.YELLOW_FLOWER_GROWTH);

        blockStateModelGenerator.registerMultifaceBlock(ModNatureBlocks.WEBBING);

        blockStateModelGenerator.registerMultifaceBlock(ModNatureBlocks.MOSS);
        blockStateModelGenerator.registerMultifaceBlock(ModNatureBlocks.FOREST_MOSS);
        blockStateModelGenerator.registerMultifaceBlock(ModNatureBlocks.CORRUPTED_MOSS);

        blockStateModelGenerator.registerMultifaceBlock(ModNatureBlocks.MORGUL_IVY);

        blockStateModelGenerator.registerMultifaceBlock(ModNatureBlocks.STICKY_SNOW);
        blockStateModelGenerator.registerMultifaceBlock(ModNatureBlocks.STICKY_ICE);

        registerPointedBlock(blockStateModelGenerator, ModBlocks.POINTED_DOLOMITE);
        registerPointedBlock(blockStateModelGenerator, ModBlocks.POINTED_GALONN);
        registerPointedBlock(blockStateModelGenerator, ModBlocks.POINTED_LIMESTONE);
        registerPointedBlock(blockStateModelGenerator, ModBlocks.POINTED_IZHERABAN);
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {}

    public final void registerFanModel(BlockStateModelGenerator blockStateCollector, Block coralFanBlock) {
        TexturedModel texturedModel = TexturedModel.CORAL_FAN.get(coralFanBlock);
        Identifier identifier = texturedModel.upload(coralFanBlock, blockStateCollector.modelCollector);
        WeightedVariant weightedVariant = createWeightedVariant(identifier);
        blockStateCollector.blockStateCollector.accept(BlockStateModelGenerator.createSingletonBlockState(coralFanBlock, weightedVariant));
        blockStateCollector.registerItemModel(coralFanBlock);
    }

    public final void registerTintableLargePlant(BlockStateModelGenerator blockStateModelGenerator, Block plantBlock) {
        Identifier identifier = MEModels.TINTED_LARGE_PLANT.upload(plantBlock, TextureMap.of(TextureKey.ALL, Identifier.of(Registries.BLOCK.getId(plantBlock).getNamespace(), "block/" + Registries.BLOCK.getId(plantBlock).getPath())), blockStateModelGenerator.modelCollector);
        WeightedVariant weightedVariant = createWeightedVariant(identifier);
        blockStateModelGenerator.blockStateCollector.accept(BlockStateModelGenerator.createSingletonBlockState(plantBlock, weightedVariant));
        blockStateModelGenerator.registerTintedItemModel(plantBlock, blockStateModelGenerator.uploadBlockItemModel(plantBlock.asItem(), plantBlock), new GrassTintSource());
    }

    public final void registerLargePlant(BlockStateModelGenerator blockStateModelGenerator, Block plantBlock) {
        Identifier identifier = MEModels.LARGE_PLANT.upload(plantBlock, TextureMap.of(TextureKey.ALL,Identifier.of(Registries.BLOCK.getId(plantBlock).getNamespace(), "block/" + Registries.BLOCK.getId(plantBlock).getPath())), blockStateModelGenerator.modelCollector);
        WeightedVariant weightedVariant = createWeightedVariant(identifier);
        blockStateModelGenerator.blockStateCollector.accept(BlockStateModelGenerator.createSingletonBlockState(plantBlock, weightedVariant));
        blockStateModelGenerator.registerItemModel(plantBlock.asItem());
    }

    public final void registerFlowerPotPlant(BlockStateModelGenerator blockStateModelGenerator, Block plantBlock, Block flowerPotBlock, BlockStateModelGenerator.CrossType tintType) {
        TextureMap textureMap = TextureMap.plant(plantBlock);
        Identifier identifier = tintType.getFlowerPotCrossModel().upload(flowerPotBlock, textureMap, blockStateModelGenerator.modelCollector);
        WeightedVariant weightedVariant = createWeightedVariant(identifier);
        blockStateModelGenerator.blockStateCollector.accept(BlockStateModelGenerator.createSingletonBlockState(flowerPotBlock, weightedVariant));
    }

    public void registerVanillaVerticalSlabModelBlockStates(BlockStateModelGenerator blockStateModelGenerator, Block block, Block origin, String slabPath) {
        Identifier fullBlockId = ModelIds.getBlockModelId(origin);

        WeightedVariant variantId = createWeightedVariant(MEModels.VERTICAL_SLAB.upload(block,
                TextureMap.of(TextureKey.ALL, Identifier.of("minecraft", "block/" + slabPath)),
                blockStateModelGenerator.modelCollector));

        WeightedVariant inner = createWeightedVariant(MEModels.VERTICAL_SLAB_INNER.upload(block, TextureMap.of(TextureKey.ALL, Identifier.of("minecraft", "block/" + slabPath)), blockStateModelGenerator.modelCollector));
        WeightedVariant outer = createWeightedVariant(MEModels.VERTICAL_SLAB_OUTER.upload(block, TextureMap.of(TextureKey.ALL, Identifier.of("minecraft", "block/" + slabPath)), blockStateModelGenerator.modelCollector));

        registerVerticalSlab(blockStateModelGenerator, block, fullBlockId, variantId, inner, outer);
    }

    public void registerVerticalSlabModelBlockStates(BlockStateModelGenerator blockStateModelGenerator, Block verticalSlab, Block block, String slabPath) {
        Identifier fullBlockId = ModelIds.getBlockModelId(block);

        WeightedVariant variantId = createWeightedVariant(MEModels.VERTICAL_SLAB.upload(verticalSlab,
                TextureMap.of(TextureKey.ALL, Identifier.of(Registries.BLOCK.getId(block).getNamespace(), "block/" + slabPath)),
                blockStateModelGenerator.modelCollector));

        WeightedVariant inner = createWeightedVariant(MEModels.VERTICAL_SLAB_INNER.upload(verticalSlab, TextureMap.of(TextureKey.ALL, Identifier.of(Registries.BLOCK.getId(block).getNamespace(), "block/" + slabPath)), blockStateModelGenerator.modelCollector));
        WeightedVariant outer = createWeightedVariant(MEModels.VERTICAL_SLAB_OUTER.upload(verticalSlab, TextureMap.of(TextureKey.ALL, Identifier.of(Registries.BLOCK.getId(block).getNamespace(), "block/" + slabPath)), blockStateModelGenerator.modelCollector));

        registerVerticalSlab(blockStateModelGenerator, verticalSlab, fullBlockId, variantId, inner, outer);
    }

    public void registerColumnWallModelBlockStates(BlockStateModelGenerator blockStateModelGenerator, Block block, Block origin,
                                                           String modId, String topTexturePath, String bottomTexturePath, String sideTexturePath) {
        String modIdTopBottom = modId;
        if (sideTexturePath.contains("deepslate_carved_window")){
            modIdTopBottom = "minecraft";
        }

        if (sideTexturePath.contains("basalt_carved_window")){
            topTexturePath = topTexturePath.concat("_top");
            modIdTopBottom = "minecraft";
        }

        //TODO redo later cause this shit bad
        if (Objects.equals(topTexturePath, "stone") ||
                Objects.equals(topTexturePath, "blackstone") ||
                Objects.equals(topTexturePath, "tuff")){
            modIdTopBottom = "minecraft";
        }

        if (Objects.equals(sideTexturePath, "chiseled_tuff") ||
                Objects.equals(sideTexturePath, "chiseled_tuff_bricks")){
            modId = "minecraft";
            modIdTopBottom = "minecraft";
        }

        if (Objects.equals(sideTexturePath, "chiseled_stone_bricks") ||
                Objects.equals(sideTexturePath, "chiseled_deepslate")||
                Objects.equals(sideTexturePath, "chiseled_polished_blackstone")){
            modId = "minecraft";
            modIdTopBottom = "minecraft";

            topTexturePath = sideTexturePath;
            bottomTexturePath = sideTexturePath;
        }

        Identifier sideTexture = Identifier.of(modId, "block/" + sideTexturePath);

        WeightedVariant post = createWeightedVariant(MEModels.COLUMN_WALL_POST.upload(block, (new TextureMap())
                        .put(TextureKey.TOP, Identifier.of(modIdTopBottom, "block/" + topTexturePath))
                        .put(TextureKey.BOTTOM, Identifier.of(modIdTopBottom, "block/" + bottomTexturePath))
                        .put(TextureKey.WALL, sideTexture)
                        .put(TextureKey.PARTICLE, sideTexture),
                blockStateModelGenerator.modelCollector));

        WeightedVariant low = createWeightedVariant(MEModels.COLUMN_WALL_SIDE.upload(block, (new TextureMap())
                        .put(TextureKey.TOP, Identifier.of(modIdTopBottom, "block/" + topTexturePath))
                        .put(TextureKey.BOTTOM, Identifier.of(modIdTopBottom, "block/" + bottomTexturePath))
                        .put(TextureKey.WALL, sideTexture)
                        .put(TextureKey.PARTICLE, sideTexture),
                blockStateModelGenerator.modelCollector));

        WeightedVariant tall = createWeightedVariant(MEModels.COLUMN_WALL_SIDE_TALL.upload(block, (new TextureMap())
                        .put(TextureKey.TOP, Identifier.of(modIdTopBottom, "block/" + topTexturePath))
                        .put(TextureKey.BOTTOM, Identifier.of(modIdTopBottom, "block/" + bottomTexturePath))
                        .put(TextureKey.WALL, sideTexture)
                        .put(TextureKey.PARTICLE, sideTexture),
                blockStateModelGenerator.modelCollector));

        blockStateModelGenerator.blockStateCollector.accept(BlockStateModelGenerator
                .createWallBlockState(block, post, low, tall));

        Identifier inventory = MEModels.COLUMN_WALL_INVENTORY.upload(block, (new TextureMap())
                .put(TextureKey.TOP, Identifier.of(modIdTopBottom, "block/" + topTexturePath))
                .put(TextureKey.BOTTOM, Identifier.of(modIdTopBottom, "block/" + bottomTexturePath))
                .put(TextureKey.WALL, sideTexture)
                .put(TextureKey.PARTICLE, sideTexture), blockStateModelGenerator.modelCollector);

        blockStateModelGenerator.registerParentedItemModel(block, inventory);
    }

    public void registerColumnVerticalSlabModelBlockStates(BlockStateModelGenerator blockStateModelGenerator, Block block, Block origin,
                                                           String modId, String topTexturePath, String bottomTexturePath, String sideTexturePath) {
        Identifier fullBlockId = ModelIds.getBlockModelId(origin);
        String modIdTopBottom = modId;
        if (sideTexturePath.contains("deepslate_carved_window")){
            modIdTopBottom = "minecraft";
        }

        if (sideTexturePath.contains("basalt_carved_window")){
            topTexturePath = topTexturePath.concat("_top");
            bottomTexturePath = bottomTexturePath.concat("_top");
            modIdTopBottom = "minecraft";
        }

        if (Objects.equals(topTexturePath, "stone") ||
                Objects.equals(topTexturePath, "blackstone") ||
                Objects.equals(topTexturePath, "tuff")){
            modIdTopBottom = "minecraft";
        }

        if (Objects.equals(sideTexturePath, "chiseled_tuff") ||
                Objects.equals(sideTexturePath, "chiseled_tuff_bricks")){
            modId = "minecraft";
            modIdTopBottom = "minecraft";
        }

        if (Objects.equals(sideTexturePath, "chiseled_stone_bricks") ||
                Objects.equals(sideTexturePath, "chiseled_deepslate")||
                Objects.equals(sideTexturePath, "chiseled_polished_blackstone")){
            modId = "minecraft";
            modIdTopBottom = "minecraft";

            topTexturePath = sideTexturePath;
            bottomTexturePath = sideTexturePath;
        }

        Identifier sideTexture = Identifier.of(modId, "block/" + sideTexturePath);

        WeightedVariant variantId = createWeightedVariant(MEModels.VERTICAL_COLUMN_SLAB.upload(block, (new TextureMap())
                        .put(TextureKey.TOP, Identifier.of(modIdTopBottom, "block/" + topTexturePath))
                        .put(TextureKey.BOTTOM, Identifier.of(modIdTopBottom, "block/" + bottomTexturePath))
                        .put(TextureKey.SIDE, sideTexture)
                        .put(TextureKey.PARTICLE, sideTexture),
                blockStateModelGenerator.modelCollector));

        WeightedVariant inner = createWeightedVariant(MEModels.VERTICAL_COLUMN_SLAB_INNER.upload(block, (new TextureMap())
                        .put(TextureKey.TOP, Identifier.of(modIdTopBottom, "block/" + topTexturePath))
                        .put(TextureKey.BOTTOM, Identifier.of(modIdTopBottom, "block/" + bottomTexturePath))
                        .put(TextureKey.SIDE, sideTexture)
                        .put(TextureKey.PARTICLE, sideTexture),
                blockStateModelGenerator.modelCollector));

        WeightedVariant outer = createWeightedVariant(MEModels.VERTICAL_COLUMN_SLAB_OUTER.upload(block, (new TextureMap())
                        .put(TextureKey.TOP, Identifier.of(modIdTopBottom, "block/" + topTexturePath))
                        .put(TextureKey.BOTTOM, Identifier.of(modIdTopBottom, "block/" + bottomTexturePath))
                        .put(TextureKey.SIDE, sideTexture)
                        .put(TextureKey.PARTICLE, sideTexture),
                blockStateModelGenerator.modelCollector));
        registerVerticalSlab(blockStateModelGenerator, block, fullBlockId, variantId, inner, outer);
    }

    private void registerVerticalSlab(BlockStateModelGenerator blockStateModelGenerator, Block block, Identifier fullBlock, WeightedVariant regular, WeightedVariant inner, WeightedVariant outer) {
        WeightedVariant fullBlockVariant;
        if (Registries.BLOCK.getId(block).getPath().contains("waxed_") && Registries.BLOCK.getId(block).getPath().contains("copper")) {
            fullBlockVariant = createWeightedVariant(Identifier.ofVanilla(fullBlock.getPath().replaceAll("waxed_", "")));
        }  else if (fullBlock.getNamespace().contains("minecraft")) {
            fullBlockVariant = createWeightedVariant(Identifier.ofVanilla(fullBlock.getPath()));
        }else {
            fullBlockVariant = createWeightedVariant(Identifier.of(MiddleEarth.MOD_ID, fullBlock.getPath()));
        }

        VariantsBlockModelDefinitionCreator blockstate = VariantsBlockModelDefinitionCreator.of(block).with(
                BlockStateVariantMap.models(Properties.HORIZONTAL_FACING, VerticalSlabBlock.DOUBLE, VerticalSlabBlock.SHAPE)
                        .register(Direction.EAST, false, VerticalSlabShape.STRAIGHT, regular.apply(ROTATE_Y_90).apply(UV_LOCK))
                        .register(Direction.WEST, false, VerticalSlabShape.STRAIGHT, regular.apply(ROTATE_Y_270).apply(UV_LOCK))
                        .register(Direction.SOUTH, false, VerticalSlabShape.STRAIGHT, regular.apply(ROTATE_Y_180).apply(UV_LOCK))
                        .register(Direction.NORTH, false, VerticalSlabShape.STRAIGHT, regular.apply(UV_LOCK))
                        .register(Direction.EAST, false, VerticalSlabShape.OUTER_RIGHT, outer.apply(ROTATE_Y_180).apply(UV_LOCK))
                        .register(Direction.WEST, false, VerticalSlabShape.OUTER_RIGHT, outer.apply(UV_LOCK))
                        .register(Direction.SOUTH, false, VerticalSlabShape.OUTER_RIGHT, outer.apply(ROTATE_Y_270).apply(UV_LOCK))
                        .register(Direction.NORTH, false, VerticalSlabShape.OUTER_RIGHT, outer.apply(ROTATE_Y_90).apply(UV_LOCK))
                        .register(Direction.EAST, false, VerticalSlabShape.OUTER_LEFT, outer.apply(ROTATE_Y_90).apply(UV_LOCK))
                        .register(Direction.WEST, false, VerticalSlabShape.OUTER_LEFT, outer.apply(ROTATE_Y_270).apply(UV_LOCK))
                        .register(Direction.SOUTH, false, VerticalSlabShape.OUTER_LEFT, outer.apply(ROTATE_Y_180).apply(UV_LOCK))
                        .register(Direction.NORTH, false, VerticalSlabShape.OUTER_LEFT, outer.apply(UV_LOCK))
                        .register(Direction.EAST, false, VerticalSlabShape.INNER_RIGHT, inner.apply(ROTATE_Y_180).apply(UV_LOCK))
                        .register(Direction.WEST, false, VerticalSlabShape.INNER_RIGHT, inner.apply(UV_LOCK))
                        .register(Direction.SOUTH, false, VerticalSlabShape.INNER_RIGHT, inner.apply(ROTATE_Y_270).apply(UV_LOCK))
                        .register(Direction.NORTH, false, VerticalSlabShape.INNER_RIGHT, inner.apply(ROTATE_Y_90).apply(UV_LOCK))
                        .register(Direction.EAST, false, VerticalSlabShape.INNER_LEFT, inner.apply(ROTATE_Y_90).apply(UV_LOCK))
                        .register(Direction.WEST, false, VerticalSlabShape.INNER_LEFT, inner.apply(ROTATE_Y_270).apply(UV_LOCK))
                        .register(Direction.SOUTH, false, VerticalSlabShape.INNER_LEFT, inner.apply(ROTATE_Y_180).apply(UV_LOCK))
                        .register(Direction.NORTH, false, VerticalSlabShape.INNER_LEFT, inner.apply(UV_LOCK))

                        .register(Direction.EAST, true, VerticalSlabShape.STRAIGHT, fullBlockVariant.apply(UV_LOCK))
                        .register(Direction.WEST, true, VerticalSlabShape.STRAIGHT, fullBlockVariant.apply(UV_LOCK))
                        .register(Direction.SOUTH, true, VerticalSlabShape.STRAIGHT, fullBlockVariant.apply(UV_LOCK))
                        .register(Direction.NORTH, true, VerticalSlabShape.STRAIGHT, fullBlockVariant.apply(UV_LOCK))
                        .register(Direction.EAST, true, VerticalSlabShape.OUTER_RIGHT, fullBlockVariant.apply(UV_LOCK))
                        .register(Direction.WEST, true, VerticalSlabShape.OUTER_RIGHT, fullBlockVariant.apply(UV_LOCK))
                        .register(Direction.SOUTH, true, VerticalSlabShape.OUTER_RIGHT, fullBlockVariant.apply(UV_LOCK))
                        .register(Direction.NORTH, true, VerticalSlabShape.OUTER_RIGHT, fullBlockVariant.apply(UV_LOCK))
                        .register(Direction.EAST, true, VerticalSlabShape.OUTER_LEFT, fullBlockVariant.apply(UV_LOCK))
                        .register(Direction.WEST, true, VerticalSlabShape.OUTER_LEFT, fullBlockVariant.apply(UV_LOCK))
                        .register(Direction.SOUTH, true, VerticalSlabShape.OUTER_LEFT, fullBlockVariant.apply(UV_LOCK))
                        .register(Direction.NORTH, true, VerticalSlabShape.OUTER_LEFT, fullBlockVariant.apply(UV_LOCK))
                        .register(Direction.EAST, true, VerticalSlabShape.INNER_RIGHT, fullBlockVariant.apply(UV_LOCK))
                        .register(Direction.WEST, true, VerticalSlabShape.INNER_RIGHT, fullBlockVariant.apply(UV_LOCK))
                        .register(Direction.SOUTH, true, VerticalSlabShape.INNER_RIGHT, fullBlockVariant.apply(UV_LOCK))
                        .register(Direction.NORTH, true, VerticalSlabShape.INNER_RIGHT, fullBlockVariant.apply(UV_LOCK))
                        .register(Direction.EAST, true, VerticalSlabShape.INNER_LEFT, fullBlockVariant.apply(UV_LOCK))
                        .register(Direction.WEST, true, VerticalSlabShape.INNER_LEFT, fullBlockVariant.apply(UV_LOCK))
                        .register(Direction.SOUTH, true, VerticalSlabShape.INNER_LEFT, fullBlockVariant.apply(UV_LOCK))
                        .register(Direction.NORTH, true, VerticalSlabShape.INNER_LEFT, fullBlockVariant.apply(UV_LOCK)));

        blockStateModelGenerator.registerParentedItemModel(block, ModelIds.getBlockModelId(block));
        blockStateModelGenerator.blockStateCollector.accept(blockstate);
    }

    public void registerWoodStoolModelBlockStates(BlockStateModelGenerator blockStateModelGenerator, Block block){
        Identifier texture = Identifier.of(MiddleEarth.MOD_ID, "block/" + Registries.BLOCK.getId(block).getPath().replaceAll("stool", "chair"));
        WeightedVariant weightedVariant = BlockStateModelGenerator.createWeightedVariant(MEModels.WOOD_STOOL.upload(block,
                new TextureMap().put(TextureKey.ALL, texture).put(TextureKey.PARTICLE, texture), blockStateModelGenerator.modelCollector));

        VariantsBlockModelDefinitionCreator blockstate = VariantsBlockModelDefinitionCreator.of(block).with(
                BlockStateVariantMap.models(Properties.HORIZONTAL_FACING)
                                .register(Direction.NORTH, weightedVariant)
                                .register(Direction.EAST, weightedVariant.apply(ROTATE_Y_90))
                                .register(Direction.SOUTH, weightedVariant.apply(ROTATE_Y_180))
                                .register(Direction.WEST, weightedVariant.apply(ROTATE_Y_270)));

        blockStateModelGenerator.registerParentedItemModel(block, ModelIds.getBlockModelId(block));
        blockStateModelGenerator.blockStateCollector.accept(blockstate);
    }

    public void registerWoodBenchModelBlockStates(BlockStateModelGenerator blockStateModelGenerator, Block block){
        Identifier texture = Identifier.of(MiddleEarth.MOD_ID, "block/" + Registries.BLOCK.getId(block).getPath());
        WeightedVariant weightedVariant = BlockStateModelGenerator.createWeightedVariant(MEModels.WOOD_BENCH.upload(block,
                new TextureMap().put(TextureKey.ALL, texture).put(TextureKey.PARTICLE, texture), blockStateModelGenerator.modelCollector));

        VariantsBlockModelDefinitionCreator blockstate = VariantsBlockModelDefinitionCreator.of(block).with(
                BlockStateVariantMap.models(Properties.HORIZONTAL_FACING)
                        .register(Direction.NORTH, weightedVariant)
                        .register(Direction.EAST, weightedVariant.apply(ROTATE_Y_90))
                        .register(Direction.SOUTH, weightedVariant.apply(ROTATE_Y_180))
                        .register(Direction.WEST, weightedVariant.apply(ROTATE_Y_270)));

        blockStateModelGenerator.registerParentedItemModel(block, ModelIds.getBlockModelId(block));
        blockStateModelGenerator.blockStateCollector.accept(blockstate);
    }

    public void registerWoodTableModelBlockStates(BlockStateModelGenerator blockStateModelGenerator, Block block){
        Identifier texture = Identifier.of(MiddleEarth.MOD_ID, "block/" + Registries.BLOCK.getId(block).getPath());
        WeightedVariant weightedVariant = BlockStateModelGenerator.createWeightedVariant(MEModels.WOOD_TABLE.upload(block,
                new TextureMap().put(TextureKey.ALL, texture).put(TextureKey.PARTICLE, texture), blockStateModelGenerator.modelCollector));

        blockStateModelGenerator.registerParentedItemModel(block, ModelIds.getBlockModelId(block));
        blockStateModelGenerator.blockStateCollector.accept(BlockStateModelGenerator.createSingletonBlockState(block, weightedVariant));
    }

    public void registerWoodChairModelBlockStates(BlockStateModelGenerator blockStateModelGenerator, Block block){
        Identifier texture = Identifier.of(MiddleEarth.MOD_ID, "block/" + Registries.BLOCK.getId(block).getPath());
        WeightedVariant weightedVariant = BlockStateModelGenerator.createWeightedVariant(MEModels.WOOD_CHAIR.upload(block,
                new TextureMap().put(TextureKey.ALL, texture).put(TextureKey.PARTICLE, texture), blockStateModelGenerator.modelCollector));

        VariantsBlockModelDefinitionCreator blockstate = VariantsBlockModelDefinitionCreator.of(block).with(
                BlockStateVariantMap.models(Properties.HORIZONTAL_FACING)
                        .register(Direction.NORTH, weightedVariant)
                        .register(Direction.EAST, weightedVariant.apply(ROTATE_Y_90))
                        .register(Direction.SOUTH, weightedVariant.apply(ROTATE_Y_180))
                        .register(Direction.WEST, weightedVariant.apply(ROTATE_Y_270)));

        blockStateModelGenerator.registerParentedItemModel(block, ModelIds.getBlockModelId(block));
        blockStateModelGenerator.blockStateCollector.accept(blockstate);
    }

    public void registerStoneStoolModelBlockStates(BlockStateModelGenerator blockStateModelGenerator, Block block, Identifier texture) {
        WeightedVariant weightedVariant = BlockStateModelGenerator.createWeightedVariant(MEModels.STONE_STOOL.upload(block,
                new TextureMap().put(TextureKey.ALL, texture).put(TextureKey.PARTICLE, texture), blockStateModelGenerator.modelCollector));

        VariantsBlockModelDefinitionCreator blockstate = VariantsBlockModelDefinitionCreator.of(block).with(
                BlockStateVariantMap.models(Properties.HORIZONTAL_FACING)
                        .register(Direction.NORTH, weightedVariant)
                        .register(Direction.EAST, weightedVariant.apply(ROTATE_Y_90))
                        .register(Direction.SOUTH, weightedVariant.apply(ROTATE_Y_180))
                        .register(Direction.WEST, weightedVariant.apply(ROTATE_Y_270)));

        blockStateModelGenerator.registerParentedItemModel(block, ModelIds.getBlockModelId(block));
        blockStateModelGenerator.blockStateCollector.accept(blockstate);
    }

    public void registerStoneTableModelBlockStates(BlockStateModelGenerator blockStateModelGenerator, Block block, Identifier texture) {
        WeightedVariant weightedVariant = BlockStateModelGenerator.createWeightedVariant(MEModels.STONE_TABLE.upload(block,
                new TextureMap().put(TextureKey.ALL, texture).put(TextureKey.PARTICLE, texture), blockStateModelGenerator.modelCollector));

        blockStateModelGenerator.registerParentedItemModel(block, ModelIds.getBlockModelId(block));
        blockStateModelGenerator.blockStateCollector.accept(BlockStateModelGenerator.createSingletonBlockState(block, weightedVariant));
    }

    public void registerStoneChairModelBlockStates(BlockStateModelGenerator blockStateModelGenerator, Block block, Identifier texture) {
        WeightedVariant weightedVariant = BlockStateModelGenerator.createWeightedVariant(MEModels.STONE_CHAIR.upload(block,
                new TextureMap().put(TextureKey.ALL, texture).put(TextureKey.PARTICLE, texture), blockStateModelGenerator.modelCollector));

        VariantsBlockModelDefinitionCreator blockstate = VariantsBlockModelDefinitionCreator.of(block).with(
                BlockStateVariantMap.models(Properties.HORIZONTAL_FACING)
                        .register(Direction.NORTH, weightedVariant)
                        .register(Direction.EAST, weightedVariant.apply(ROTATE_Y_90))
                        .register(Direction.SOUTH, weightedVariant.apply(ROTATE_Y_180))
                        .register(Direction.WEST, weightedVariant.apply(ROTATE_Y_270)));

        blockStateModelGenerator.registerParentedItemModel(block, ModelIds.getBlockModelId(block));
        blockStateModelGenerator.blockStateCollector.accept(blockstate);
    }

    public final void registerDoubleBlock(BlockStateModelGenerator blockStateModelGenerator, Block doubleBlock, BlockStateModelGenerator.CrossType tintType) {
        blockStateModelGenerator.registerItemModel(doubleBlock, "_top");
        WeightedVariant identifier = createWeightedVariant(blockStateModelGenerator.createSubModel(doubleBlock, "_top", tintType.getCrossModel(), TextureMap::cross));
        WeightedVariant identifier2 = createWeightedVariant(blockStateModelGenerator.createSubModel(doubleBlock, "_bottom", tintType.getCrossModel(), TextureMap::cross));
        blockStateModelGenerator.registerDoubleBlock(doubleBlock, identifier, identifier2);
    }

    public final void registerPointedBlock(BlockStateModelGenerator blockStateModelGenerator, Block pointedBlock) {
        Identifier inventory = Models.GENERATED.upload(pointedBlock.asItem(), TextureMap.layer0(TextureMap.getId(pointedBlock.asItem())), blockStateModelGenerator.modelCollector);
        blockStateModelGenerator.registerItemModel(pointedBlock.asItem(), inventory);

        BlockStateVariantMap.DoubleProperty<WeightedVariant, Direction, Thickness> doubleProperty = BlockStateVariantMap.models(Properties.VERTICAL_DIRECTION, Properties.THICKNESS);
        Thickness[] var2 = Thickness.values();
        int var3 = var2.length;

        int var4;
        Thickness thickness;
        for(var4 = 0; var4 < var3; ++var4) {
            thickness = var2[var4];
            doubleProperty.register(Direction.UP, thickness, getPointedVariant(blockStateModelGenerator, Direction.UP, thickness, pointedBlock));
        }

        var2 = Thickness.values();
        var3 = var2.length;

        for(var4 = 0; var4 < var3; ++var4) {
            thickness = var2[var4];
            doubleProperty.register(Direction.DOWN, thickness, getPointedVariant(blockStateModelGenerator, Direction.DOWN, thickness, pointedBlock));
        }

        blockStateModelGenerator.blockStateCollector.accept(VariantsBlockModelDefinitionCreator.of(pointedBlock).with(doubleProperty));
    }

    public final WeightedVariant getPointedVariant(BlockStateModelGenerator blockStateModelGenerator, Direction direction, Thickness thickness, Block pointedBlock) {
        String var10000 = direction.asString();
        String string = "_" + var10000 + "_" + thickness.asString();
        TextureMap textureMap = TextureMap.cross(TextureMap.getSubId(pointedBlock, string));
        return createWeightedVariant(Models.POINTED_DRIPSTONE.upload(pointedBlock, string, textureMap, blockStateModelGenerator.modelCollector));
    }

    public final void registerLargeDoor(BlockStateModelGenerator blockStateModelGenerator, LargeDoorBlock largeDoor, IntProperty part) {
        var statesMap = BlockStateVariantMap.models(Properties.HORIZONTAL_FACING, Properties.OPEN, Properties.DOOR_HINGE, part);
        int rot = 0;
        for (int i = 0; i < largeDoor.getDoorWidth() * largeDoor.getDoorHeight(); i++) {
            for (int k = 2; k < 6; k++) {
                rot = switch (k) {
                    case 2 -> 0;
                    case 3 -> 180;
                    case 4 -> 270;
                    case 5 -> 90;
                    default -> rot;
                };

                WeightedVariant weightedVariantLeft = createWeightedVariant(Identifier.of(MiddleEarth.MOD_ID, "block/" + Registries.BLOCK.getId(largeDoor).getPath() + "_left_" + i));
                WeightedVariant weightedVariantLeftOpen = createWeightedVariant(Identifier.of(MiddleEarth.MOD_ID, "block/" + Registries.BLOCK.getId(largeDoor).getPath() + "_left_open_" + i));
                WeightedVariant weightedVarianRight = createWeightedVariant(Identifier.of(MiddleEarth.MOD_ID, "block/" + Registries.BLOCK.getId(largeDoor).getPath() + "_right_" + i));
                WeightedVariant weightedVarianRightOpen = createWeightedVariant(Identifier.of(MiddleEarth.MOD_ID, "block/" + Registries.BLOCK.getId(largeDoor).getPath() + "_right_open_" + i));

                statesMap.register(Direction.byIndex(k), false, DoorHinge.LEFT, i,
                        weightedVariantLeft.apply(ModelVariantOperator.ROTATION_Y.withValue(AxisRotation.valueOf("R" + rot))));

                statesMap.register(Direction.byIndex(k), true, DoorHinge.LEFT, i,
                        weightedVariantLeftOpen.apply(ModelVariantOperator.ROTATION_Y.withValue(AxisRotation.valueOf("R" + rot))));

                statesMap.register(Direction.byIndex(k), false, DoorHinge.RIGHT, i,
                        weightedVarianRight.apply(ModelVariantOperator.ROTATION_Y.withValue(AxisRotation.valueOf("R" + rot))));

                statesMap.register(Direction.byIndex(k), true, DoorHinge.RIGHT, i,
                        weightedVarianRightOpen.apply(ModelVariantOperator.ROTATION_Y.withValue(AxisRotation.valueOf("R" + rot))));

                if (k == 2) {
                    MEModels.LARGE_DOOR_LEFT.upload(largeDoor, "_left_" + i,
                            (new TextureMap()).put(TextureKey.ALL, Identifier.of(MiddleEarth.MOD_ID, "block/" + Registries.BLOCK.getId(largeDoor).getPath() + "_" + i))
                                    .put(TextureKey.PARTICLE, Identifier.of(MiddleEarth.MOD_ID, "block/" + Registries.BLOCK.getId(largeDoor).getPath() + "_" + i)),
                            blockStateModelGenerator.modelCollector);

                    MEModels.LARGE_DOOR_LEFT_OPEN.upload(largeDoor, "_left_open_" + i,
                            (new TextureMap()).put(TextureKey.ALL, Identifier.of(MiddleEarth.MOD_ID, "block/" + Registries.BLOCK.getId(largeDoor).getPath() + "_" + i))
                                    .put(TextureKey.PARTICLE, Identifier.of(MiddleEarth.MOD_ID, "block/" + Registries.BLOCK.getId(largeDoor).getPath() + "_" + i)),
                            blockStateModelGenerator.modelCollector);

                    MEModels.LARGE_DOOR_RIGHT.upload(largeDoor, "_right_" + i,
                            (new TextureMap()).put(TextureKey.ALL, Identifier.of(MiddleEarth.MOD_ID, "block/" + Registries.BLOCK.getId(largeDoor).getPath() + "_" + i))
                                    .put(TextureKey.PARTICLE, Identifier.of(MiddleEarth.MOD_ID, "block/" + Registries.BLOCK.getId(largeDoor).getPath() + "_" + i)),
                            blockStateModelGenerator.modelCollector);

                    MEModels.LARGE_DOOR_RIGHT_OPEN.upload(largeDoor, "_right_open_" + i,
                            (new TextureMap()).put(TextureKey.ALL, Identifier.of(MiddleEarth.MOD_ID, "block/" + Registries.BLOCK.getId(largeDoor).getPath() + "_" + i))
                                    .put(TextureKey.PARTICLE, Identifier.of(MiddleEarth.MOD_ID, "block/" + Registries.BLOCK.getId(largeDoor).getPath() + "_" + i)),
                            blockStateModelGenerator.modelCollector);
                }
            }
        }
        VariantsBlockModelDefinitionCreator blockstate = VariantsBlockModelDefinitionCreator.of(largeDoor).with(statesMap);
        blockStateModelGenerator.blockStateCollector.accept(blockstate);
    }

    public final void registerThickLargeDoor(BlockStateModelGenerator blockStateModelGenerator, LargeDoorBlock largeDoor, IntProperty part) {
        var statesMap = BlockStateVariantMap.models(Properties.HORIZONTAL_FACING, Properties.OPEN, Properties.DOOR_HINGE, part);
        int rot = 0;
        for (int i = 0; i < largeDoor.getDoorWidth() * largeDoor.getDoorHeight(); i++) {
            for (int k = 2; k < 6; k++) {
                rot = switch (k) {
                    case 2 -> 0;
                    case 3 -> 180;
                    case 4 -> 270;
                    case 5 -> 90;
                    default -> rot;
                };

                WeightedVariant weightedVariantLeft = createWeightedVariant(Identifier.of(MiddleEarth.MOD_ID, "block/" + Registries.BLOCK.getId(largeDoor).getPath() + "_left_" + i));
                WeightedVariant weightedVariantLeftOpen = createWeightedVariant(Identifier.of(MiddleEarth.MOD_ID, "block/" + Registries.BLOCK.getId(largeDoor).getPath() + "_left_open_" + i));
                WeightedVariant weightedVarianRight = createWeightedVariant(Identifier.of(MiddleEarth.MOD_ID, "block/" + Registries.BLOCK.getId(largeDoor).getPath() + "_right_" + i));
                WeightedVariant weightedVarianRightOpen = createWeightedVariant(Identifier.of(MiddleEarth.MOD_ID, "block/" + Registries.BLOCK.getId(largeDoor).getPath() + "_right_open_" + i));

                statesMap.register(Direction.byIndex(k), false, DoorHinge.LEFT, i,
                        weightedVariantLeft.apply(ModelVariantOperator.ROTATION_Y.withValue(AxisRotation.valueOf("R" + rot))));

                statesMap.register(Direction.byIndex(k), true, DoorHinge.LEFT, i,
                        weightedVariantLeftOpen.apply(ModelVariantOperator.ROTATION_Y.withValue(AxisRotation.valueOf("R" + rot))));

                statesMap.register(Direction.byIndex(k), false, DoorHinge.RIGHT, i,
                        weightedVarianRight.apply(ModelVariantOperator.ROTATION_Y.withValue(AxisRotation.valueOf("R" + rot))));

                statesMap.register(Direction.byIndex(k), true, DoorHinge.RIGHT, i,
                        weightedVarianRightOpen.apply(ModelVariantOperator.ROTATION_Y.withValue(AxisRotation.valueOf("R" + rot))));

                if (k == 2) {
                    MEModels.LARGE_THICK_DOOR_LEFT.upload(largeDoor, "_left_" + i,
                            (new TextureMap()).put(TextureKey.ALL, Identifier.of(MiddleEarth.MOD_ID, "block/" + Registries.BLOCK.getId(largeDoor).getPath() + "_" + i))
                                    .put(TextureKey.PARTICLE, Identifier.of(MiddleEarth.MOD_ID, "block/" + Registries.BLOCK.getId(largeDoor).getPath() + "_" + i)),
                            blockStateModelGenerator.modelCollector);

                    MEModels.LARGE_THICK_DOOR_LEFT_OPEN.upload(largeDoor, "_left_open_" + i,
                            (new TextureMap()).put(TextureKey.ALL, Identifier.of(MiddleEarth.MOD_ID, "block/" + Registries.BLOCK.getId(largeDoor).getPath() + "_" + i))
                                    .put(TextureKey.PARTICLE, Identifier.of(MiddleEarth.MOD_ID, "block/" + Registries.BLOCK.getId(largeDoor).getPath() + "_" + i)),
                            blockStateModelGenerator.modelCollector);

                    MEModels.LARGE_THICK_DOOR_RIGHT.upload(largeDoor, "_right_" + i,
                            (new TextureMap()).put(TextureKey.ALL, Identifier.of(MiddleEarth.MOD_ID, "block/" + Registries.BLOCK.getId(largeDoor).getPath() + "_" + i))
                                    .put(TextureKey.PARTICLE, Identifier.of(MiddleEarth.MOD_ID, "block/" + Registries.BLOCK.getId(largeDoor).getPath() + "_" + i)),
                            blockStateModelGenerator.modelCollector);

                    MEModels.LARGE_THICK_DOOR_RIGHT_OPEN.upload(largeDoor, "_right_open_" + i,
                            (new TextureMap()).put(TextureKey.ALL, Identifier.of(MiddleEarth.MOD_ID, "block/" + Registries.BLOCK.getId(largeDoor).getPath() + "_" + i))
                                    .put(TextureKey.PARTICLE, Identifier.of(MiddleEarth.MOD_ID, "block/" + Registries.BLOCK.getId(largeDoor).getPath() + "_" + i)),
                            blockStateModelGenerator.modelCollector);
                }
            }
        }

        VariantsBlockModelDefinitionCreator blockstate = VariantsBlockModelDefinitionCreator.of(largeDoor).with(statesMap);
        blockStateModelGenerator.blockStateCollector.accept(blockstate);
    }


    public final void registerGlassAndPane(BlockStateModelGenerator blockStateModelGenerator, Block glass, Block glassPane) {
        TextureMap textureMap;
        if (Registries.BLOCK.getId(glassPane).getPath().contains("lead_glass")){
            textureMap = TextureMap.paneAndTopForEdge(glass, ModDecorativeBlocks.LEAD_GLASS_PANE);
        } else {
            textureMap = TextureMap.paneAndTopForEdge(glass, glassPane);
        }
        WeightedVariant weightedVariant = createWeightedVariant(Models.TEMPLATE_GLASS_PANE_POST.upload(glassPane, textureMap, blockStateModelGenerator.modelCollector));
        WeightedVariant weightedVariant2 = createWeightedVariant(Models.TEMPLATE_GLASS_PANE_SIDE.upload(glassPane, textureMap, blockStateModelGenerator.modelCollector));
        WeightedVariant weightedVariant3 = createWeightedVariant(Models.TEMPLATE_GLASS_PANE_SIDE_ALT.upload(glassPane, textureMap, blockStateModelGenerator.modelCollector));
        WeightedVariant weightedVariant4 = createWeightedVariant(Models.TEMPLATE_GLASS_PANE_NOSIDE.upload(glassPane, textureMap, blockStateModelGenerator.modelCollector));
        WeightedVariant weightedVariant5 = createWeightedVariant(Models.TEMPLATE_GLASS_PANE_NOSIDE_ALT.upload(glassPane, textureMap, blockStateModelGenerator.modelCollector));
        Item item = glassPane.asItem();

        blockStateModelGenerator.registerItemModel(item, blockStateModelGenerator.uploadBlockItemModel(item, glass));
        blockStateModelGenerator.blockStateCollector.accept(MultipartBlockModelDefinitionCreator.create(glassPane).with(weightedVariant).with(createMultipartConditionBuilder().put(Properties.NORTH, true), weightedVariant2).with(createMultipartConditionBuilder().put(Properties.EAST, true), weightedVariant2.apply(ROTATE_Y_90)).with(createMultipartConditionBuilder().put(Properties.SOUTH, true), weightedVariant3).with(createMultipartConditionBuilder().put(Properties.WEST, true), weightedVariant3.apply(ROTATE_Y_90)).with(createMultipartConditionBuilder().put(Properties.NORTH, false), weightedVariant4).with(createMultipartConditionBuilder().put(Properties.EAST, false), weightedVariant5).with(createMultipartConditionBuilder().put(Properties.SOUTH, false), weightedVariant5.apply(ROTATE_Y_90)).with(createMultipartConditionBuilder().put(Properties.WEST, false), weightedVariant4.apply(ROTATE_Y_270)));
    }

    public final void registerPaneModel(BlockStateModelGenerator blockStateModelGenerator, Block pane) {
        TextureMap textureMap = TextureMap.paneAndTopForEdge(pane, pane);
        WeightedVariant weightedVariant = createWeightedVariant(Models.TEMPLATE_GLASS_PANE_POST.upload(pane, textureMap, blockStateModelGenerator.modelCollector));
        WeightedVariant weightedVariant2 = createWeightedVariant(Models.TEMPLATE_GLASS_PANE_SIDE.upload(pane, textureMap, blockStateModelGenerator.modelCollector));
        WeightedVariant weightedVariant3 = createWeightedVariant(Models.TEMPLATE_GLASS_PANE_SIDE_ALT.upload(pane, textureMap, blockStateModelGenerator.modelCollector));
        WeightedVariant weightedVariant4 = createWeightedVariant(Models.TEMPLATE_GLASS_PANE_NOSIDE.upload(pane, textureMap, blockStateModelGenerator.modelCollector));
        WeightedVariant weightedVariant5 = createWeightedVariant(Models.TEMPLATE_GLASS_PANE_NOSIDE_ALT.upload(pane, textureMap, blockStateModelGenerator.modelCollector));
        Item item = pane.asItem();
        blockStateModelGenerator.registerItemModel(item, blockStateModelGenerator.uploadBlockItemModel(item, pane));
        blockStateModelGenerator.blockStateCollector.accept(MultipartBlockModelDefinitionCreator.create(pane).with(weightedVariant).with(createMultipartConditionBuilder().put(Properties.NORTH, true), weightedVariant2).with(createMultipartConditionBuilder().put(Properties.EAST, true), weightedVariant2.apply(ROTATE_Y_90)).with(createMultipartConditionBuilder().put(Properties.SOUTH, true), weightedVariant3).with(createMultipartConditionBuilder().put(Properties.WEST, true), weightedVariant3.apply(ROTATE_Y_90)).with(createMultipartConditionBuilder().put(Properties.NORTH, false), weightedVariant4).with(createMultipartConditionBuilder().put(Properties.EAST, false), weightedVariant5).with(createMultipartConditionBuilder().put(Properties.SOUTH, false), weightedVariant5.apply(ROTATE_Y_90)).with(createMultipartConditionBuilder().put(Properties.WEST, false), weightedVariant4.apply(ROTATE_Y_270)));
    }

    private void registerLayers(BlockStateModelGenerator blockStateModelGenerator, Block layers, Block origin, Boolean isVanilla) {
        TextureMap textureMap = TextureMap.all(origin);
        WeightedVariant weightedVariant;
        if (!isVanilla){
            weightedVariant = createWeightedVariant(Models.CUBE_ALL.upload(origin, textureMap, blockStateModelGenerator.modelCollector));
        } else {
            weightedVariant = createWeightedVariant(Identifier.ofVanilla(Registries.BLOCK.getId(origin).getPath()));
        }
        blockStateModelGenerator.blockStateCollector.accept(VariantsBlockModelDefinitionCreator.of(layers).with(BlockStateVariantMap.models(Properties.LAYERS).generate((integer) -> {
            WeightedVariant var2;
            if (integer < 8) {
                int var10001 = integer;
                var2 = createWeightedVariant(Identifier.of(MiddleEarth.MOD_ID, "block/" + Registries.BLOCK.getId(origin).getPath() +  "_layer_height" + var10001 * 2));
            } else {
                var2 = weightedVariant;
            }

            return var2;
        })));
        if (!isVanilla){
            blockStateModelGenerator.blockStateCollector.accept(BlockStateModelGenerator.createSingletonBlockState(origin, weightedVariant));
        }
        blockStateModelGenerator.registerParentedItemModel(layers, ModelIds.getBlockSubModelId(layers, "_height2"));
    }

    private void registerTopWaterblock(BlockStateModelGenerator blockStateModelGenerator, Block block) {
        blockStateModelGenerator.registerItemModel(block);
        ModelVariant modelVariant = createModelVariant(ModelIds.getBlockModelId(block));
        blockStateModelGenerator.blockStateCollector.accept(VariantsBlockModelDefinitionCreator.of(block, modelWithYRotation(modelVariant)));
    }

    public void registerRocksBlock(BlockStateModelGenerator blockStateModelGenerator, Block rocksBlock, Block origin) {
        Identifier id = Identifier.of(Registries.BLOCK.getId(origin).getNamespace(), "block/" + Registries.BLOCK.getId(origin).getPath());
        if (origin == Blocks.BASALT || origin == Blocks.POLISHED_BASALT) {
            id = id.withSuffixedPath("_side");
        }
        WeightedVariant stage0 = createWeightedVariant(MEModels.ROCKS_STAGE_0.upload(rocksBlock,
                TextureMap.of(TextureKey.ALL, id),
                blockStateModelGenerator.modelCollector));
        WeightedVariant stage1 = createWeightedVariant(MEModels.ROCKS_STAGE_1.upload(rocksBlock,
                TextureMap.of(TextureKey.ALL, id),
                blockStateModelGenerator.modelCollector));
        WeightedVariant stage2 = createWeightedVariant(MEModels.ROCKS_STAGE_2.upload(rocksBlock,
                TextureMap.of(TextureKey.ALL, id),
                blockStateModelGenerator.modelCollector));
        WeightedVariant stage3 = createWeightedVariant(MEModels.ROCKS_STAGE_3.upload(rocksBlock,
                TextureMap.of(TextureKey.ALL, id),
                blockStateModelGenerator.modelCollector));

        VariantsBlockModelDefinitionCreator blockstate = VariantsBlockModelDefinitionCreator.of(rocksBlock)
                .with(BlockStateVariantMap.models(Properties.HORIZONTAL_FACING, RocksBlock.STAGE)
                        .register(Direction.EAST, 0, stage0.apply(ROTATE_Y_90).apply(UV_LOCK))
                        .register(Direction.WEST, 0, stage0.apply(ROTATE_Y_270).apply(UV_LOCK))
                        .register(Direction.SOUTH, 0, stage0.apply(ROTATE_Y_180).apply(UV_LOCK))
                        .register(Direction.NORTH, 0, stage0.apply(UV_LOCK))

                        .register(Direction.EAST, 1, stage1.apply(ROTATE_Y_90).apply(UV_LOCK))
                        .register(Direction.WEST, 1, stage1.apply(ROTATE_Y_270).apply(UV_LOCK))
                        .register(Direction.SOUTH, 1, stage1.apply(ROTATE_Y_180).apply(UV_LOCK))
                        .register(Direction.NORTH, 1, stage1.apply(UV_LOCK))

                        .register(Direction.EAST, 2, stage2.apply(ROTATE_Y_90).apply(UV_LOCK))
                        .register(Direction.WEST, 2, stage2.apply(ROTATE_Y_270).apply(UV_LOCK))
                        .register(Direction.SOUTH, 2, stage2.apply(ROTATE_Y_180).apply(UV_LOCK))
                        .register(Direction.NORTH, 2, stage2.apply(UV_LOCK))

                        .register(Direction.EAST, 3, stage3.apply(ROTATE_Y_90).apply(UV_LOCK))
                        .register(Direction.WEST, 3, stage3.apply(ROTATE_Y_270).apply(UV_LOCK))
                        .register(Direction.SOUTH, 3, stage3.apply(ROTATE_Y_180).apply(UV_LOCK))
                        .register(Direction.NORTH, 3, stage3.apply(UV_LOCK)));

        blockStateModelGenerator.registerParentedItemModel(rocksBlock, ModelIds.getBlockModelId(rocksBlock));
        blockStateModelGenerator.blockStateCollector.accept(blockstate);
    }

    public void registerTrapdoor(BlockStateModelGenerator blockStateModelGenerator, Block trapdoorBlock, Block block, boolean orientable) {
        TextureMap textureMap;

        String modid = Registries.BLOCK.getId(block).getNamespace();

        WeightedVariant identifier;
        Identifier identifier2;
        WeightedVariant identifier3;

        if (orientable){
            textureMap = TextureMap.texture(Identifier.of(modid, "block/" + Registries.BLOCK.getId(trapdoorBlock).getPath()));
            identifier = createWeightedVariant(Models.TEMPLATE_ORIENTABLE_TRAPDOOR_TOP.upload(trapdoorBlock, textureMap, blockStateModelGenerator.modelCollector));
            identifier2 = Models.TEMPLATE_ORIENTABLE_TRAPDOOR_BOTTOM.upload(trapdoorBlock, textureMap, blockStateModelGenerator.modelCollector);
            identifier3 = createWeightedVariant(Models.TEMPLATE_ORIENTABLE_TRAPDOOR_OPEN.upload(trapdoorBlock, textureMap, blockStateModelGenerator.modelCollector));

            blockStateModelGenerator.blockStateCollector.accept(BlockStateModelGenerator.createOrientableTrapdoorBlockState(trapdoorBlock, identifier, createWeightedVariant(identifier2), identifier3));
        } else {
            if (block == Blocks.BASALT) {
                textureMap = TextureMap.texture(Identifier.of(modid, "block/" + Registries.BLOCK.getId(block).getPath() + "_side"));
            } else {
                textureMap = TextureMap.texture(Identifier.of(modid, "block/" + Registries.BLOCK.getId(block).getPath().replaceAll("_trapdoor", "")));
            }
            identifier = createWeightedVariant(Models.TEMPLATE_TRAPDOOR_TOP.upload(trapdoorBlock, textureMap, blockStateModelGenerator.modelCollector));
            identifier2 = Models.TEMPLATE_TRAPDOOR_BOTTOM.upload(trapdoorBlock, textureMap, blockStateModelGenerator.modelCollector);
            identifier3 = createWeightedVariant(Models.TEMPLATE_TRAPDOOR_OPEN.upload(trapdoorBlock, textureMap, blockStateModelGenerator.modelCollector));

            blockStateModelGenerator.blockStateCollector.accept(BlockStateModelGenerator.createTrapdoorBlockState(trapdoorBlock, identifier, createWeightedVariant(identifier2), identifier3));

        }
        blockStateModelGenerator.registerParentedItemModel(trapdoorBlock, identifier2);
    }

    public void registerOrientableThickLadder(BlockStateModelGenerator blockStateModelGenerator, Block ladderBlock) {
        Identifier texture = Identifier.of(MiddleEarth.MOD_ID, "block/" + Registries.BLOCK.getId(ladderBlock).getPath());

        WeightedVariant weightedVariant = createWeightedVariant(MEModels.THICK_LADDER.upload(ladderBlock, TextureMap.of(TextureKey.ALL, texture), blockStateModelGenerator.modelCollector));

        VariantsBlockModelDefinitionCreator blockstate = VariantsBlockModelDefinitionCreator.of(ladderBlock)
                .with(BlockStateVariantMap.models(Properties.BLOCK_FACE, Properties.HORIZONTAL_FACING)
                .register(BlockFace.FLOOR, Direction.NORTH, weightedVariant)
                .register(BlockFace.FLOOR, Direction.EAST, weightedVariant.apply(ROTATE_Y_90))
                .register(BlockFace.FLOOR, Direction.SOUTH, weightedVariant.apply(ROTATE_Y_180))
                .register(BlockFace.FLOOR, Direction.WEST, weightedVariant.apply(ROTATE_Y_270))

                .register(BlockFace.WALL, Direction.NORTH, weightedVariant.apply(ROTATE_X_90))
                .register(BlockFace.WALL, Direction.EAST, weightedVariant.apply(ROTATE_X_90).apply(ROTATE_Y_90))
                .register(BlockFace.WALL, Direction.SOUTH, weightedVariant.apply(ROTATE_X_90).apply(ROTATE_Y_180))
                .register(BlockFace.WALL, Direction.WEST, weightedVariant.apply(ROTATE_X_90).apply(ROTATE_Y_270))

                .register(BlockFace.CEILING, Direction.SOUTH, weightedVariant.apply(ROTATE_X_180))
                .register(BlockFace.CEILING, Direction.WEST, weightedVariant.apply(ROTATE_X_180).apply(ROTATE_Y_90))
                .register(BlockFace.CEILING, Direction.NORTH, weightedVariant.apply(ROTATE_X_180).apply(ROTATE_Y_180))
                .register(BlockFace.CEILING, Direction.EAST, weightedVariant.apply(ROTATE_X_180).apply(ROTATE_Y_270)));

        blockStateModelGenerator.registerParentedItemModel(ladderBlock, ModelIds.getBlockModelId(ladderBlock));
        blockStateModelGenerator.blockStateCollector.accept(blockstate);
    }
}
