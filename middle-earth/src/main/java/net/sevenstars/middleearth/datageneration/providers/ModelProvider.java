package net.sevenstars.middleearth.datageneration.providers;

import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.enums.BlockFace;
import net.minecraft.block.enums.DoorHinge;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.client.data.*;
import net.minecraft.client.render.model.json.ModelVariant;
import net.minecraft.client.render.model.json.ModelVariantOperator;
import net.minecraft.client.render.model.json.WeightedVariant;
import net.minecraft.item.Item;
import net.minecraft.item.equipment.ArmorMaterial;
import net.minecraft.item.equipment.EquipmentAsset;
import net.minecraft.item.equipment.EquipmentAssetKeys;
import net.minecraft.item.equipment.trim.ArmorTrimMaterial;
import net.minecraft.item.equipment.trim.ArmorTrimMaterials;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.AxisRotation;
import net.minecraft.util.math.Direction;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.block.ModBlocks;
import net.sevenstars.middleearth.block.ModDecorativeBlocks;
import net.sevenstars.middleearth.block.ModNatureBlocks;
import net.sevenstars.middleearth.block.crop.*;
import net.sevenstars.middleearth.block.special.LargeDoorBlock;
import net.sevenstars.middleearth.block.special.RocksBlock;
import net.sevenstars.middleearth.block.special.doors.*;
import net.sevenstars.middleearth.block.special.verticalSlabs.VerticalSlabBlock;
import net.sevenstars.middleearth.block.special.verticalSlabs.VerticalSlabShape;
import net.sevenstars.middleearth.datageneration.content.CustomItemModels;
import net.sevenstars.middleearth.datageneration.content.MEModels;
import net.sevenstars.middleearth.datageneration.content.models.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ModelProvider extends FabricModelProvider {

    public static final ModelVariantOperator UV_LOCK = ModelVariantOperator.UV_LOCK.withValue(true);
    public static final ModelVariantOperator ROTATE_X_90 = ModelVariantOperator.ROTATION_X.withValue(AxisRotation.R90);;
    public static final ModelVariantOperator ROTATE_X_180 = ModelVariantOperator.ROTATION_X.withValue(AxisRotation.R180);
    public static final ModelVariantOperator ROTATE_X_270 = ModelVariantOperator.ROTATION_X.withValue(AxisRotation.R270);
    public static final ModelVariantOperator ROTATE_Y_90 = ModelVariantOperator.ROTATION_Y.withValue(AxisRotation.R90);
    public static final ModelVariantOperator ROTATE_Y_180 = ModelVariantOperator.ROTATION_Y.withValue(AxisRotation.R180);
    public static final ModelVariantOperator ROTATE_Y_270 = ModelVariantOperator.ROTATION_Y.withValue(AxisRotation.R270);

    public ModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {

        for (Block block : SimpleBlockModel.blocks) {
            blockStateModelGenerator.registerSimpleCubeAll(block);
        }

        for (Block block : SimpleBlockModel.cobbleableStoneBlocks) {
            TexturedModel texturedModel = TexturedModel.CUBE_ALL.get(block);
            ModelVariant identifier = BlockStateModelGenerator.createModelVariant(Models.CUBE_MIRRORED_ALL.upload(block, texturedModel.getTextures(), blockStateModelGenerator.modelCollector));
            ModelVariant identifier2 = BlockStateModelGenerator.createModelVariant(Models.CUBE_ALL.upload(block, texturedModel.getTextures(), blockStateModelGenerator.modelCollector));

            blockStateModelGenerator.blockStateCollector.accept(VariantsBlockModelDefinitionCreator.of(block, BlockStateModelGenerator.modelWithMirroring(identifier, identifier2)));
        }

        for (SimpleBlockModel.ChiseledBlock block : SimpleBlockModel.chiseledBlocks) {
            blockStateModelGenerator.registerSimpleCubeAll(block.base());
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
                    Identifier.of(MiddleEarth.MOD_ID, "block/" + Registries.BLOCK.getId(wood).getPath().replaceAll("_wood", "_log")));
            WeightedVariant identifier = BlockStateModelGenerator.createWeightedVariant(Models.CUBE_COLUMN.upload(wood, textureMap, blockStateModelGenerator.modelCollector));
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

        for (SimpleSlabModel.Slab block : SimpleSlabModel.slabs) {
            WeightedVariant id = BlockStateModelGenerator.createWeightedVariant(ModelIds.getBlockModelId(block.origin()));
            Block slab = block.slab();

            TexturedModel texturedModel = TexturedModel.CUBE_ALL.get(block.origin());
            WeightedVariant bottom = BlockStateModelGenerator.createWeightedVariant(Models.SLAB.upload(slab, texturedModel.getTextures(), blockStateModelGenerator.modelCollector));
            WeightedVariant top = BlockStateModelGenerator.createWeightedVariant(Models.SLAB_TOP.upload(slab, texturedModel.getTextures(), blockStateModelGenerator.modelCollector));

            blockStateModelGenerator.blockStateCollector.accept(BlockStateModelGenerator
                    .createSlabBlockState(slab, bottom, top, id));
        }

        for (SimpleSlabModel.Slab block : SimpleSlabModel.woodSlabs) {
            WeightedVariant id = BlockStateModelGenerator.createWeightedVariant(ModelIds.getBlockModelId(block.origin()));
            Block slab = block.slab();

            TexturedModel texturedModel = TexturedModel.getCubeAll(Identifier.of(MiddleEarth.MOD_ID, "block/" + Registries.BLOCK.getId(block.origin()).getPath().replaceAll("_wood", "_log")));
            WeightedVariant bottom = BlockStateModelGenerator.createWeightedVariant(Models.SLAB.upload(slab, texturedModel.getTextures(), blockStateModelGenerator.modelCollector));
            WeightedVariant top = BlockStateModelGenerator.createWeightedVariant(Models.SLAB_TOP.upload(slab, texturedModel.getTextures(), blockStateModelGenerator.modelCollector));

            blockStateModelGenerator.blockStateCollector.accept(BlockStateModelGenerator
                    .createSlabBlockState(slab, bottom, top, id));
        }

        for (SimpleSlabModel.Slab block : SimpleSlabModel.strippedSlabs) {
            WeightedVariant id = BlockStateModelGenerator.createWeightedVariant(ModelIds.getBlockModelId(block.origin()));
            Block slab = block.slab();

            TexturedModel texturedModel = TexturedModel.getCubeAll(Identifier.of(MiddleEarth.MOD_ID, "block/" + Registries.BLOCK.getId(block.origin()).getPath().replaceAll("_wood", "_log")));
            WeightedVariant bottom = BlockStateModelGenerator.createWeightedVariant(Models.SLAB.upload(slab, texturedModel.getTextures(), blockStateModelGenerator.modelCollector));
            WeightedVariant top = BlockStateModelGenerator.createWeightedVariant(Models.SLAB_TOP.upload(slab, texturedModel.getTextures(), blockStateModelGenerator.modelCollector));

            blockStateModelGenerator.blockStateCollector.accept(BlockStateModelGenerator
                    .createSlabBlockState(slab, bottom, top, id));
        }

        for (SimpleSlabModel.Slab block : SimpleSlabModel.vanillaSlabs) {
            WeightedVariant id = BlockStateModelGenerator.createWeightedVariant(ModelIds.getBlockModelId(block.origin()));
            Block slab = block.slab();

            TexturedModel texturedModel = TexturedModel.getCubeAll(Identifier.of("minecraft", "block/" + Registries.BLOCK.getId(block.origin()).getPath().replaceAll("_wood", "_log")));
            WeightedVariant bottom = BlockStateModelGenerator.createWeightedVariant(Models.SLAB.upload(slab, texturedModel.getTextures(), blockStateModelGenerator.modelCollector));
            WeightedVariant top = BlockStateModelGenerator.createWeightedVariant(Models.SLAB_TOP.upload(slab, texturedModel.getTextures(), blockStateModelGenerator.modelCollector));

            blockStateModelGenerator.blockStateCollector.accept(BlockStateModelGenerator
                    .createSlabBlockState(slab, bottom, top, id));
        }

        for (SimpleSlabModel.Slab block : SimpleSlabModel.vanillaWoodSlabs) {
            WeightedVariant id = BlockStateModelGenerator.createWeightedVariant(ModelIds.getBlockModelId(block.origin()));
            Block slab = block.slab();

            TexturedModel texturedModel = TexturedModel.getCubeAll(Identifier.of("minecraft", "block/" + Registries.BLOCK.getId(block.origin()).getPath().replaceAll("_wood", "_log")));
            WeightedVariant bottom = BlockStateModelGenerator.createWeightedVariant(Models.SLAB.upload(slab, texturedModel.getTextures(), blockStateModelGenerator.modelCollector));
            WeightedVariant top = BlockStateModelGenerator.createWeightedVariant(Models.SLAB_TOP.upload(slab, texturedModel.getTextures(), blockStateModelGenerator.modelCollector));

            blockStateModelGenerator.blockStateCollector.accept(BlockStateModelGenerator
                    .createSlabBlockState(slab, bottom, top, id));
        }

        for (SimpleSlabModel.Slab block : SimpleSlabModel.vanillaStrippedSlab) {
            WeightedVariant id = BlockStateModelGenerator.createWeightedVariant(ModelIds.getBlockModelId(block.origin()));
            Block slab = block.slab();

            TexturedModel texturedModel = TexturedModel.getCubeAll(Identifier.of("minecraft", "block/" + Registries.BLOCK.getId(block.origin()).getPath().replaceAll("_wood", "_log")));
            WeightedVariant bottom = BlockStateModelGenerator.createWeightedVariant(Models.SLAB.upload(slab, texturedModel.getTextures(), blockStateModelGenerator.modelCollector));
            WeightedVariant top = BlockStateModelGenerator.createWeightedVariant(Models.SLAB_TOP.upload(slab, texturedModel.getTextures(), blockStateModelGenerator.modelCollector));

            blockStateModelGenerator.blockStateCollector.accept(BlockStateModelGenerator
                    .createSlabBlockState(slab, bottom, top, id));
        }

        for (SimpleStairModel.Stair block : SimpleStairModel.stairs) {
            TexturedModel texturedModel = TexturedModel.CUBE_ALL.get(block.origin());
            Block stairs = block.stairs();

            WeightedVariant inner = BlockStateModelGenerator.createWeightedVariant(Models.INNER_STAIRS.upload(stairs, texturedModel.getTextures(), blockStateModelGenerator.modelCollector));
            WeightedVariant regular = BlockStateModelGenerator.createWeightedVariant(Models.STAIRS.upload(stairs, texturedModel.getTextures(), blockStateModelGenerator.modelCollector));
            WeightedVariant outer = BlockStateModelGenerator.createWeightedVariant(Models.OUTER_STAIRS.upload(stairs, texturedModel.getTextures(), blockStateModelGenerator.modelCollector));

            blockStateModelGenerator.blockStateCollector.accept(BlockStateModelGenerator
                    .createStairsBlockState(stairs, inner, regular, outer));
        }

        for (SimpleStairModel.Stair block : SimpleStairModel.woodStairs) {
            TexturedModel texturedModel = TexturedModel.getCubeAll(Identifier.of(MiddleEarth.MOD_ID, "block/" + Registries.BLOCK.getId(block.origin()).getPath().replaceAll("_wood", "_log")));
            Block stairs = block.stairs();

            WeightedVariant inner = BlockStateModelGenerator.createWeightedVariant(Models.INNER_STAIRS.upload(stairs, texturedModel.getTextures(), blockStateModelGenerator.modelCollector));
            WeightedVariant regular = BlockStateModelGenerator.createWeightedVariant(Models.STAIRS.upload(stairs, texturedModel.getTextures(), blockStateModelGenerator.modelCollector));
            WeightedVariant outer = BlockStateModelGenerator.createWeightedVariant(Models.OUTER_STAIRS.upload(stairs, texturedModel.getTextures(), blockStateModelGenerator.modelCollector));

            blockStateModelGenerator.blockStateCollector.accept(BlockStateModelGenerator
                    .createStairsBlockState(stairs, inner, regular, outer));
        }

        for (SimpleStairModel.Stair block : SimpleStairModel.strippedStairs) {
            TexturedModel texturedModel = TexturedModel.getCubeAll(Identifier.of(MiddleEarth.MOD_ID, "block/" + Registries.BLOCK.getId(block.origin()).getPath().replaceAll("_wood", "_log")));
            Block stairs = block.stairs();

            WeightedVariant inner = BlockStateModelGenerator.createWeightedVariant(Models.INNER_STAIRS.upload(stairs, texturedModel.getTextures(), blockStateModelGenerator.modelCollector));
            WeightedVariant regular = BlockStateModelGenerator.createWeightedVariant(Models.STAIRS.upload(stairs, texturedModel.getTextures(), blockStateModelGenerator.modelCollector));
            WeightedVariant outer = BlockStateModelGenerator.createWeightedVariant(Models.OUTER_STAIRS.upload(stairs, texturedModel.getTextures(), blockStateModelGenerator.modelCollector));

            blockStateModelGenerator.blockStateCollector.accept(BlockStateModelGenerator
                    .createStairsBlockState(stairs, inner, regular, outer));
        }

        for (SimpleStairModel.Stair block : SimpleStairModel.vanillaWoodStairs) {
            TexturedModel texturedModel = TexturedModel.getCubeAll(Identifier.of("minecraft", "block/" + Registries.BLOCK.getId(block.origin()).getPath().replaceAll("_wood", "_log")));
            Block stairs = block.stairs();

            WeightedVariant inner = BlockStateModelGenerator.createWeightedVariant(Models.INNER_STAIRS.upload(stairs, texturedModel.getTextures(), blockStateModelGenerator.modelCollector));
            WeightedVariant regular = BlockStateModelGenerator.createWeightedVariant(Models.STAIRS.upload(stairs, texturedModel.getTextures(), blockStateModelGenerator.modelCollector));
            WeightedVariant outer = BlockStateModelGenerator.createWeightedVariant(Models.OUTER_STAIRS.upload(stairs, texturedModel.getTextures(), blockStateModelGenerator.modelCollector));

            blockStateModelGenerator.blockStateCollector.accept(BlockStateModelGenerator
                    .createStairsBlockState(stairs, inner, regular, outer));
        }

        for (SimpleStairModel.Stair block : SimpleStairModel.vanillaStrippedStairs) {
            TexturedModel texturedModel = TexturedModel.getCubeAll(Identifier.of("minecraft", "block/" + Registries.BLOCK.getId(block.origin()).getPath().replaceAll("_wood", "_log")));
            Block stairs = block.stairs();

            WeightedVariant inner = BlockStateModelGenerator.createWeightedVariant(Models.INNER_STAIRS.upload(stairs, texturedModel.getTextures(), blockStateModelGenerator.modelCollector));
            WeightedVariant regular = BlockStateModelGenerator.createWeightedVariant(Models.STAIRS.upload(stairs, texturedModel.getTextures(), blockStateModelGenerator.modelCollector));
            WeightedVariant outer = BlockStateModelGenerator.createWeightedVariant(Models.OUTER_STAIRS.upload(stairs, texturedModel.getTextures(), blockStateModelGenerator.modelCollector));

            blockStateModelGenerator.blockStateCollector.accept(BlockStateModelGenerator
                    .createStairsBlockState(stairs, inner, regular, outer));
        }

        for (SimpleStairModel.Stair block : SimpleStairModel.vanillaStairs) {
            TexturedModel texturedModel = TexturedModel.CUBE_ALL.get(block.origin());
            Block stairs = block.stairs();

            WeightedVariant inner = BlockStateModelGenerator.createWeightedVariant(Models.INNER_STAIRS.upload(stairs, texturedModel.getTextures(), blockStateModelGenerator.modelCollector));
            WeightedVariant regular = BlockStateModelGenerator.createWeightedVariant(Models.STAIRS.upload(stairs, texturedModel.getTextures(), blockStateModelGenerator.modelCollector));
            WeightedVariant outer = BlockStateModelGenerator.createWeightedVariant(Models.OUTER_STAIRS.upload(stairs, texturedModel.getTextures(), blockStateModelGenerator.modelCollector));

            blockStateModelGenerator.blockStateCollector.accept(BlockStateModelGenerator
                    .createStairsBlockState(stairs, inner, regular, outer));
        }

        for (SimpleWallModel.Wall block : SimpleWallModel.blocks) {
            TexturedModel texturedModel = TexturedModel.CUBE_ALL.get(block.block());
            Block wall = block.wall();

            Models.WALL_INVENTORY.upload(wall, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);

            WeightedVariant post = BlockStateModelGenerator.createWeightedVariant(Models.TEMPLATE_WALL_POST.upload(wall, texturedModel.getTextures(), blockStateModelGenerator.modelCollector));
            WeightedVariant low = BlockStateModelGenerator.createWeightedVariant(Models.TEMPLATE_WALL_SIDE.upload(wall, texturedModel.getTextures(), blockStateModelGenerator.modelCollector));
            WeightedVariant tall = BlockStateModelGenerator.createWeightedVariant(Models.TEMPLATE_WALL_SIDE_TALL.upload(wall, texturedModel.getTextures(), blockStateModelGenerator.modelCollector));

            blockStateModelGenerator.blockStateCollector.accept(BlockStateModelGenerator
                    .createWallBlockState(wall, post, low, tall));
        }

        for (SimpleWallModel.Wall block : SimpleWallModel.vanillaWalls) {
            TexturedModel texturedModel;
            if(Registries.BLOCK.getId(block.block()).getPath().contains("waxed_") && Registries.BLOCK.getId(block.block()).getPath().contains("cut_copper")){
                texturedModel = TexturedModel.getCubeAll(Identifier.of("minecraft", "block/" + Registries.BLOCK.getId(block.block()).getPath().replaceAll("waxed_", "")));
            } else {
                texturedModel = TexturedModel.getCubeAll(Identifier.of("minecraft", "block/" + Registries.BLOCK.getId(block.block()).getPath()));

            }
            Block wall = block.wall();

            Models.WALL_INVENTORY.upload(wall, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);

            WeightedVariant post = BlockStateModelGenerator.createWeightedVariant(Models.TEMPLATE_WALL_POST.upload(wall, texturedModel.getTextures(), blockStateModelGenerator.modelCollector));
            WeightedVariant low = BlockStateModelGenerator.createWeightedVariant(Models.TEMPLATE_WALL_SIDE.upload(wall, texturedModel.getTextures(), blockStateModelGenerator.modelCollector));
            WeightedVariant tall = BlockStateModelGenerator.createWeightedVariant(Models.TEMPLATE_WALL_SIDE_TALL.upload(wall, texturedModel.getTextures(), blockStateModelGenerator.modelCollector));

            blockStateModelGenerator.blockStateCollector.accept(BlockStateModelGenerator
                    .createWallBlockState(wall, post, low, tall));
        }

        for (SimpleWallModel.Wall block : SimpleWallModel.strippedWalls) {
            TexturedModel texturedModel = TexturedModel.getCubeAll(Identifier.of(MiddleEarth.MOD_ID, "block/" + Registries.BLOCK.getId(block.block()).getPath().replaceAll("_wood", "_log")));
            Block wall = block.wall();

            Identifier inventory = Models.WALL_INVENTORY.upload(wall, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);

            blockStateModelGenerator.registerParentedItemModel(wall, inventory);

            WeightedVariant post = BlockStateModelGenerator.createWeightedVariant(Models.TEMPLATE_WALL_POST.upload(wall, texturedModel.getTextures(), blockStateModelGenerator.modelCollector));
            WeightedVariant low = BlockStateModelGenerator.createWeightedVariant(Models.TEMPLATE_WALL_SIDE.upload(wall, texturedModel.getTextures(), blockStateModelGenerator.modelCollector));
            WeightedVariant tall = BlockStateModelGenerator.createWeightedVariant(Models.TEMPLATE_WALL_SIDE_TALL.upload(wall, texturedModel.getTextures(), blockStateModelGenerator.modelCollector));

            blockStateModelGenerator.blockStateCollector.accept(BlockStateModelGenerator
                    .createWallBlockState(wall, post, low, tall));
        }

        for (SimpleWallModel.Wall block : SimpleWallModel.vanillaStrippedWalls) {
            TexturedModel texturedModel = TexturedModel.getCubeAll(Identifier.of("minecraft", "block/" + Registries.BLOCK.getId(block.block()).getPath().replaceAll("_wood", "_log")));
            Block wall = block.wall();

            Identifier inventory = Models.WALL_INVENTORY.upload(wall, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);

            blockStateModelGenerator.registerParentedItemModel(wall, inventory);

            WeightedVariant post = BlockStateModelGenerator.createWeightedVariant(Models.TEMPLATE_WALL_POST.upload(wall, texturedModel.getTextures(), blockStateModelGenerator.modelCollector));
            WeightedVariant low = BlockStateModelGenerator.createWeightedVariant(Models.TEMPLATE_WALL_SIDE.upload(wall, texturedModel.getTextures(), blockStateModelGenerator.modelCollector));
            WeightedVariant tall = BlockStateModelGenerator.createWeightedVariant(Models.TEMPLATE_WALL_SIDE_TALL.upload(wall, texturedModel.getTextures(), blockStateModelGenerator.modelCollector));

            blockStateModelGenerator.blockStateCollector.accept(BlockStateModelGenerator
                    .createWallBlockState(wall, post, low, tall));
        }

        for (SimpleWallModel.Wall block : SimpleWallModel.vanillaWoodWalls) {
            TexturedModel texturedModel = TexturedModel.getCubeAll(Identifier.of("minecraft", "block/" + Registries.BLOCK.getId(block.block()).getPath().replaceAll("_wood", "_log")));
            Block wall = block.wall();

            Identifier inventory = Models.WALL_INVENTORY.upload(wall, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);

            blockStateModelGenerator.registerParentedItemModel(wall, inventory);

            WeightedVariant post = BlockStateModelGenerator.createWeightedVariant(Models.TEMPLATE_WALL_POST.upload(wall, texturedModel.getTextures(), blockStateModelGenerator.modelCollector));
            WeightedVariant low = BlockStateModelGenerator.createWeightedVariant(Models.TEMPLATE_WALL_SIDE.upload(wall, texturedModel.getTextures(), blockStateModelGenerator.modelCollector));
            WeightedVariant tall = BlockStateModelGenerator.createWeightedVariant(Models.TEMPLATE_WALL_SIDE_TALL.upload(wall, texturedModel.getTextures(), blockStateModelGenerator.modelCollector));

            blockStateModelGenerator.blockStateCollector.accept(BlockStateModelGenerator
                    .createWallBlockState(wall, post, low, tall));
        }

        for (SimpleFenceModel.Fence block : SimpleFenceModel.blocks) {
            TexturedModel texturedModel = TexturedModel.CUBE_ALL.get(block.block());
            Block fence = block.fence();

            WeightedVariant post = BlockStateModelGenerator.createWeightedVariant(Models.FENCE_POST.upload(fence, texturedModel.getTextures(), blockStateModelGenerator.modelCollector));
            WeightedVariant side = BlockStateModelGenerator.createWeightedVariant(Models.FENCE_SIDE.upload(fence, texturedModel.getTextures(), blockStateModelGenerator.modelCollector));
            Identifier inventory = Models.FENCE_INVENTORY.upload(fence, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);

            blockStateModelGenerator.blockStateCollector.accept(BlockStateModelGenerator
                    .createFenceBlockState(fence, post, side));

            blockStateModelGenerator.registerParentedItemModel(fence, inventory);
        }

        for (SimpleFenceModel.Fence block : SimpleFenceModel.strippedFences) {
            TexturedModel texturedModel = TexturedModel.getCubeAll(Identifier.of(MiddleEarth.MOD_ID, "block/" + Registries.BLOCK.getId(block.block()).getPath().replaceAll("_wood", "_log")));
            Block fence = block.fence();

            WeightedVariant post = BlockStateModelGenerator.createWeightedVariant(Models.FENCE_POST.upload(fence, texturedModel.getTextures(), blockStateModelGenerator.modelCollector));
            WeightedVariant side = BlockStateModelGenerator.createWeightedVariant(Models.FENCE_SIDE.upload(fence, texturedModel.getTextures(), blockStateModelGenerator.modelCollector));
            Identifier inventory = Models.FENCE_INVENTORY.upload(fence, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);

            blockStateModelGenerator.blockStateCollector.accept(BlockStateModelGenerator
                    .createFenceBlockState(fence, post, side));

            blockStateModelGenerator.registerParentedItemModel(fence, inventory);
        }

        for (SimpleFenceModel.Fence block : SimpleFenceModel.vanillaStrippedFences) {
            TexturedModel texturedModel = TexturedModel.getCubeAll(Identifier.of("minecraft", "block/" + Registries.BLOCK.getId(block.block()).getPath().replaceAll("_wood", "_log")));
            Block fence = block.fence();

            WeightedVariant post = BlockStateModelGenerator.createWeightedVariant(Models.FENCE_POST.upload(fence, texturedModel.getTextures(), blockStateModelGenerator.modelCollector));
            WeightedVariant side = BlockStateModelGenerator.createWeightedVariant(Models.FENCE_SIDE.upload(fence, texturedModel.getTextures(), blockStateModelGenerator.modelCollector));
            Identifier inventory = Models.FENCE_INVENTORY.upload(fence, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);

            blockStateModelGenerator.blockStateCollector.accept(BlockStateModelGenerator
                    .createFenceBlockState(fence, post, side));

            blockStateModelGenerator.registerParentedItemModel(fence, inventory);
        }

        for (SimpleFenceModel.Fence block : SimpleFenceModel.vanillaWoodFences) {
            TexturedModel texturedModel = TexturedModel.getCubeAll(Identifier.of("minecraft", "block/" + Registries.BLOCK.getId(block.block()).getPath().replaceAll("_wood", "_log")));
            Block fence = block.fence();

            WeightedVariant post = BlockStateModelGenerator.createWeightedVariant(Models.FENCE_POST.upload(fence, texturedModel.getTextures(), blockStateModelGenerator.modelCollector));
            WeightedVariant side = BlockStateModelGenerator.createWeightedVariant(Models.FENCE_SIDE.upload(fence, texturedModel.getTextures(), blockStateModelGenerator.modelCollector));
            Identifier inventory = Models.FENCE_INVENTORY.upload(fence, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);

            blockStateModelGenerator.blockStateCollector.accept(BlockStateModelGenerator
                    .createFenceBlockState(fence, post, side));

            blockStateModelGenerator.registerParentedItemModel(fence, inventory);
        }

        for (SimpleFenceGateModel.FenceGate block : SimpleFenceGateModel.blocks) {
            TexturedModel texturedModel = TexturedModel.CUBE_ALL.get(block.block());
            Block fenceGate = block.fenceGate();

            WeightedVariant open = BlockStateModelGenerator.createWeightedVariant(Models.TEMPLATE_FENCE_GATE_OPEN.upload(fenceGate, texturedModel.getTextures(), blockStateModelGenerator.modelCollector));
            WeightedVariant closed = BlockStateModelGenerator.createWeightedVariant(Models.TEMPLATE_FENCE_GATE.upload(fenceGate, texturedModel.getTextures(), blockStateModelGenerator.modelCollector));
            WeightedVariant openWall = BlockStateModelGenerator.createWeightedVariant(Models.TEMPLATE_FENCE_GATE_WALL_OPEN.upload(fenceGate, texturedModel.getTextures(), blockStateModelGenerator.modelCollector));
            WeightedVariant closedWall = BlockStateModelGenerator.createWeightedVariant(Models.TEMPLATE_FENCE_GATE_WALL.upload(fenceGate, texturedModel.getTextures(), blockStateModelGenerator.modelCollector));

            blockStateModelGenerator.blockStateCollector.accept(BlockStateModelGenerator
                    .createFenceGateBlockState(fenceGate, open, closed, openWall, closedWall, true));
        }

        for (SimpleButtonModel.Button block : SimpleButtonModel.buttons) {
            TexturedModel texturedModel = TexturedModel.CUBE_ALL.get(block.block());
            Block button = block.button();

            WeightedVariant unpressed = BlockStateModelGenerator.createWeightedVariant(Models.BUTTON.upload(button, texturedModel.getTextures(), blockStateModelGenerator.modelCollector));
            WeightedVariant pressed = BlockStateModelGenerator.createWeightedVariant(Models.BUTTON_PRESSED.upload(button, texturedModel.getTextures(), blockStateModelGenerator.modelCollector));
            Identifier inventory = Models.BUTTON_INVENTORY.upload(button, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);

            blockStateModelGenerator.blockStateCollector.accept(BlockStateModelGenerator
                    .createButtonBlockState(button, unpressed, pressed));

            blockStateModelGenerator.registerParentedItemModel(button, inventory);
        }

        for (SimplePressurePlateModel.PressurePlate block : SimplePressurePlateModel.pressurePlates) {
            TexturedModel texturedModel = TexturedModel.CUBE_ALL.get(block.block());
            Block pressurePlate = block.pressurePlate();

            WeightedVariant up = BlockStateModelGenerator.createWeightedVariant(Models.PRESSURE_PLATE_UP.upload(pressurePlate, texturedModel.getTextures(), blockStateModelGenerator.modelCollector));
            WeightedVariant down = BlockStateModelGenerator.createWeightedVariant(Models.PRESSURE_PLATE_DOWN.upload(pressurePlate, texturedModel.getTextures(), blockStateModelGenerator.modelCollector));

            blockStateModelGenerator.blockStateCollector.accept(BlockStateModelGenerator
                    .createPressurePlateBlockState(pressurePlate, up, down));
        }

        for (SimpleTrapDoorModel.Trapdoor trapdoor : SimpleTrapDoorModel.trapdoors) {
            blockStateModelGenerator.registerOrientableTrapdoor(trapdoor.trapdoor());
        }

        /*for (SimpleTrapDoorModel.Trapdoor trapdoor : SimpleTrapDoorModel.stoneTrapdoors) {
            registerStoneTrapdoor(blockStateModelGenerator, trapdoor.trapdoor());
        }*/

        for (SimpleTrapDoorModel.Trapdoor trapdoor : SimpleTrapDoorModel.vanillaStoneTrapdoors) {
            registerVanillaTrapdoor(blockStateModelGenerator, trapdoor.trapdoor());
        }

        /*for (SimpleLadderModel.Ladder trapdoor : SimpleLadderModel.ladders) {
            registerOrientableTrapdoorLadder(blockStateModelGenerator, trapdoor.ladder());
        }
        for (SimpleLadderModel.Ladder trapdoor : SimpleLadderModel.vanillaLadders) {
            registerOrientableTrapdoorLadder(blockStateModelGenerator, trapdoor.ladder());
        }*/

        for(SimpleDoorModel.Door door : SimpleDoorModel.doors){
            blockStateModelGenerator.registerDoor(door.door());
        }

        //TODO find out why some blocks are null here
        for (Block block : TintableCrossModel.notTintedBlocks) {
            if (block!= null) blockStateModelGenerator.registerTintableCross(block, BlockStateModelGenerator.CrossType.NOT_TINTED);
        }

        blockStateModelGenerator.registerPlantPart(ModNatureBlocks.GLOWWORM_WEBBING, ModNatureBlocks.GLOWWORM_MAIN, BlockStateModelGenerator.CrossType.NOT_TINTED);
        blockStateModelGenerator.registerPlantPart(ModNatureBlocks.MIRKWOOD_VINES, ModNatureBlocks.MIRKWOOD_VINES_PLANT, BlockStateModelGenerator.CrossType.NOT_TINTED);
        blockStateModelGenerator.registerItemModel(ModNatureBlocks.GLOWWORM_WEBBING);
        blockStateModelGenerator.registerItemModel(ModNatureBlocks.MIRKWOOD_VINES);

        for (Block block : TintableCrossModel.tintedBlocks) {
            blockStateModelGenerator.registerTintableCross(block, BlockStateModelGenerator.CrossType.TINTED);
        }

        for (Block block : TintableCrossModel.grassLikeBlocks) {
            blockStateModelGenerator.registerTintableCross(block, BlockStateModelGenerator.CrossType.NOT_TINTED);
        }

        for (Block block : SimpleFlowerBedModel.flowerBeds) {
            blockStateModelGenerator.registerFlowerbed(block);
        }

        for (SimpleFlowerPotModel.FlowerPot flowerPot : SimpleFlowerPotModel.pots) {
            registerFlowerPotPlant(blockStateModelGenerator, flowerPot.plant(), flowerPot.pottedPlant(), BlockStateModelGenerator.CrossType.NOT_TINTED);
        }

        for (Block block : SimpleDoubleBlockModel.doubleBlocks) {
            blockStateModelGenerator.registerDoubleBlock(block, BlockStateModelGenerator.CrossType.NOT_TINTED);
        }

        /*for (Block block : SimpleDoubleBlockModel.doubleBlocksItems) {
            registerDoubleBlock(blockStateModelGenerator, block, BlockStateModelGenerator.CrossType.NOT_TINTED);
        }*/

        for (Block block : SimpleMushroomBlockModel.mushroomBlocks) {
            blockStateModelGenerator.registerMushroomBlock(block);
        }

        for (SimpleVerticalSlabModel.VerticalSlab verticalSlab : SimpleVerticalSlabModel.verticalSlabs) {
            registerVerticalSlabModelBlockStates(blockStateModelGenerator, verticalSlab.verticalSlab(), verticalSlab.block(), Registries.BLOCK.getId(verticalSlab.block()).getPath());
        }

        for (SimpleVerticalSlabModel.VerticalSlab verticalSlab : SimpleVerticalSlabModel.woodVerticalSlabs) {
            registerVerticalSlabModelBlockStates(blockStateModelGenerator, verticalSlab.verticalSlab(), verticalSlab.block(), Registries.BLOCK.getId(verticalSlab.block()).getPath().replaceAll("_wood", "_log"));
        }

        for (SimpleVerticalSlabModel.VerticalSlab verticalSlab : SimpleVerticalSlabModel.strippedVerticalSlabs) {
            registerVerticalSlabModelBlockStates(blockStateModelGenerator, verticalSlab.verticalSlab(), verticalSlab.block(), Registries.BLOCK.getId(verticalSlab.block()).getPath().replaceAll("_wood", "_log"));
        }

        /*for (SimpleLayersModel.Layers block : SimpleLayersModel.layers) {
            registerLayers(blockStateModelGenerator, block.layers(), block.origin(), false);
        }

        for (SimpleLayersModel.Layers block : SimpleLayersModel.vanillaLayers) {
            registerLayers(blockStateModelGenerator, block.layers(), block.origin(), true);
        }

        for (SimplePaneModel.Pane pane : SimplePaneModel.panes) {
            registerLeadGlassPane(blockStateModelGenerator, pane.glass(), pane.pane());
        }

        for(Block block : SimpleWoodStoolModel.stools){
            registerWoodStoolModelBlockStates(blockStateModelGenerator, block);
        }

        for(Block block : SimpleWoodBenchModel.benchs){
            registerWoodBenchModelBlockStates(blockStateModelGenerator, block);
        }

        for (Block block : SimpleStoneStoolModel.stools) {
            registerStoneStoolModelBlockStates(blockStateModelGenerator, block,
                    Identifier.of(MiddleEarth.MOD_ID, "block/" +
                            Registries.BLOCK.getId(block).getPath().replaceAll("_stool", "")));
        }

        for(SimpleStoneStoolModel.VanillaStool stool : SimpleStoneStoolModel.vanillaStools){
            String id = "block/" + Registries.BLOCK.getId(stool.origin()).getPath();
            if (stool.origin() == Blocks.BASALT) id += "_side";
            registerStoneStoolModelBlockStates(blockStateModelGenerator, stool.base(),
                    Identifier.of("minecraft", id));
        }

        for (Block block : SimpleStoneTableModel.tables) {
            registerStoneTableModelBlockStates(blockStateModelGenerator, block, Identifier.of(MiddleEarth.MOD_ID, "block/" + Registries.BLOCK.getId(block).getPath().replaceAll("_table", "")));
        }
        for(SimpleStoneTableModel.VanillaTable table : SimpleStoneTableModel.vanillaTables) {
            String id = "block/" + Registries.BLOCK.getId(table.origin()).getPath();
            if(table.origin() == Blocks.BASALT) id += "_side";
            registerStoneTableModelBlockStates(blockStateModelGenerator, table.base(), Identifier.of("minecraft", id));
        }

        for (Block block : SimpleStoneChairModel.chairs) {
            registerStoneChairModelBlockStates(blockStateModelGenerator, block, Identifier.of(MiddleEarth.MOD_ID, "block/" + Registries.BLOCK.getId(block).getPath().replaceAll("_chair", "")));
        }

        for(SimpleStoneChairModel.VanillaChair chair : SimpleStoneChairModel.vanillaChairs){
            String id = "block/" + Registries.BLOCK.getId(chair.origin()).getPath();
            if(chair.origin() == Blocks.BASALT) id += "_side";
            registerStoneChairModelBlockStates(blockStateModelGenerator, chair.base(), Identifier.of("minecraft", id));
        }

        for(Block block : SimpleWoodTableModel.tables){
            registerWoodTableModelBlockStates(blockStateModelGenerator, block);
        }

        for(Block block : SimpleWoodChairModel.chairs){
            registerWoodChairModelBlockStates(blockStateModelGenerator, block);
        }

        for(Block block : SimpleFanModel.grassLikeFans){
            registerFanModel(blockStateModelGenerator, block);
        }*/

        for(SimpleRocksModel.Rocks rocks : SimpleRocksModel.rocks){
            registerRocksBlock(blockStateModelGenerator, rocks.rocks(), rocks.block());
        }

        for(SimpleRocksModel.Rocks rocks : SimpleRocksModel.vanillaRocks){
            registerRocksBlock(blockStateModelGenerator, rocks.rocks(), rocks.block());
        }
        /*
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
        }*/


        for (SimpleVerticalSlabModel.VerticalSlab verticalSlab : SimpleVerticalSlabModel.vanillaVerticalSlabs) {
            String id = String.valueOf(Registries.BLOCK.getId(verticalSlab.block()));
            id = id.substring(id.lastIndexOf(":") + 1);

            if(verticalSlab.block() == Blocks.SANDSTONE || verticalSlab.block() == Blocks.RED_SANDSTONE || verticalSlab.block() == Blocks.CUT_SANDSTONE || verticalSlab.block() == Blocks.CUT_RED_SANDSTONE) {
                String topId = id + "_top";
                String bottomId = id + "_bottom";
                if(verticalSlab.block() == Blocks.CUT_SANDSTONE || verticalSlab.block() == Blocks.CUT_RED_SANDSTONE) {
                    topId = topId.substring(topId.indexOf("_") + 1);
                    bottomId = bottomId.substring(bottomId.indexOf("_") + 1);
                }
                registerColumnVerticalSlabModelBlockStates(blockStateModelGenerator, verticalSlab.verticalSlab(), verticalSlab.block(), "minecraft", topId, bottomId, id);
            } else {
                    if(verticalSlab.block() == Blocks.SMOOTH_RED_SANDSTONE
                            || verticalSlab.block() == Blocks.SMOOTH_SANDSTONE) {
                        id += "_top";
                        id = id.substring(id.indexOf("_") + 1);
                    } else if(verticalSlab.block() == Blocks.QUARTZ_BLOCK) {
                        id += "_side";
                    } else if(verticalSlab.block() == Blocks.SMOOTH_QUARTZ) {
                        id = "quartz_block_bottom";
                    } else if(verticalSlab.block() == Blocks.WAXED_CUT_COPPER
                            || verticalSlab.block() == Blocks.WAXED_EXPOSED_CUT_COPPER
                            || verticalSlab.block() == Blocks.WAXED_WEATHERED_CUT_COPPER
                            || verticalSlab.block() == Blocks.WAXED_OXIDIZED_CUT_COPPER) {
                        id = id.substring(id.indexOf("_") + 1);
                    }
                    registerVanillaVerticalSlabModelBlockStates(blockStateModelGenerator, verticalSlab.verticalSlab(), verticalSlab.block(), id);
                }
        }

        for(SimpleVerticalSlabModel.VerticalSlab verticalSlab : SimpleVerticalSlabModel.vanillaWoodVerticalSlabs) {
            String id = Registries.BLOCK.getId(verticalSlab.block()).getPath();
            String baseTextureId = id.substring(0, id.lastIndexOf("_")) + "_log";
            baseTextureId = baseTextureId.replaceAll("_wood", "_log");
            registerVanillaVerticalSlabModelBlockStates(blockStateModelGenerator, verticalSlab.verticalSlab(), verticalSlab.block(), baseTextureId);
        }
        for (SimpleVerticalSlabModel.VerticalSlab verticalSlab : SimpleVerticalSlabModel.vanillaStrippedVerticalSlabs) {
            String id = Registries.BLOCK.getId(verticalSlab.block()).getPath();
            String baseTextureId = id.substring(0, id.lastIndexOf("_")) + "_log";
            baseTextureId = baseTextureId.replaceAll("_wood", "_log");
            registerVanillaVerticalSlabModelBlockStates(blockStateModelGenerator, verticalSlab.verticalSlab(), verticalSlab.block(), baseTextureId);
        }

        /*SimpleTopWaterModel.topWaterBlocks.forEach(block -> {
            registerTopWaterblock(blockStateModelGenerator, block);
        });*/

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

        registerLargeDoor(blockStateModelGenerator, (LargeDoorBlock) ModDecorativeBlocks.GREAT_GONDORIAN_GATE, LargeDoor10x5.PART);

        registerLargeDoor(blockStateModelGenerator, (LargeDoorBlock) ModDecorativeBlocks.GREAT_DWARVEN_GATE, LargeDoor5x2.PART);
        registerLargeDoor(blockStateModelGenerator, (LargeDoorBlock) ModDecorativeBlocks.VARNISHED_DWARVEN_DOOR, LargeDoor4x2.PART);
        registerLargeDoor(blockStateModelGenerator, (LargeDoorBlock) ModDecorativeBlocks.RUINED_DWARVEN_DOOR, LargeDoor4x2.PART);
        registerThickLargeDoor(blockStateModelGenerator, (LargeDoorBlock) ModDecorativeBlocks.HIDDEN_DWARVEN_DOOR, LargeThickDoor3x2.PART);

        registerLargeDoor(blockStateModelGenerator, (LargeDoorBlock) ModDecorativeBlocks.GREAT_ELVEN_GATE, LargeDoor6x2.PART);

        registerLargeDoor(blockStateModelGenerator, (LargeDoorBlock) ModDecorativeBlocks.GREAT_ORCISH_GATE, LargeDoor10x4.PART);

        blockStateModelGenerator.registerAxisRotated(ModBlocks.GILDED_CHISELED_GREEN_TUFF, TexturedModel.END_FOR_TOP_CUBE_COLUMN, TexturedModel.END_FOR_TOP_CUBE_COLUMN_HORIZONTAL);
        blockStateModelGenerator.registerAxisRotated(ModBlocks.GILDED_CHISELED_GREEN_TUFF_BRICKS, TexturedModel.END_FOR_TOP_CUBE_COLUMN, TexturedModel.END_FOR_TOP_CUBE_COLUMN_HORIZONTAL);
        blockStateModelGenerator.registerAxisRotated(ModBlocks.GILDED_CHISELED_POLISHED_GREEN_TUFF, TexturedModel.END_FOR_TOP_CUBE_COLUMN, TexturedModel.END_FOR_TOP_CUBE_COLUMN_HORIZONTAL);
        blockStateModelGenerator.registerAxisRotated(ModBlocks.GILDED_CHISELED_GREEN_TUFF_TILES, TexturedModel.END_FOR_TOP_CUBE_COLUMN, TexturedModel.END_FOR_TOP_CUBE_COLUMN_HORIZONTAL);
        blockStateModelGenerator.registerAxisRotated(ModBlocks.GILDED_CHISELED_SMOOTH_GREEN_TUFF, TexturedModel.END_FOR_TOP_CUBE_COLUMN, TexturedModel.END_FOR_TOP_CUBE_COLUMN_HORIZONTAL);

        /*registerPaneModel(blockStateModelGenerator, ModBlocks.NET);

        registerPaneModel(blockStateModelGenerator, ModBlocks.GILDED_BARS);

        registerPaneModel(blockStateModelGenerator, ModBlocks.COPPER_BARS);
        registerPaneModel(blockStateModelGenerator, ModBlocks.EXPOSED_COPPER_BARS);
        registerPaneModel(blockStateModelGenerator, ModBlocks.WEATHERED_COPPER_BARS);
        registerPaneModel(blockStateModelGenerator, ModBlocks.OXIDIZED_COPPER_BARS);

        registerPaneModel(blockStateModelGenerator, ModBlocks.WAXED_COPPER_BARS);
        registerPaneModel(blockStateModelGenerator, ModBlocks.WAXED_EXPOSED_COPPER_BARS);
        registerPaneModel(blockStateModelGenerator, ModBlocks.WAXED_WEATHERED_COPPER_BARS);
        registerPaneModel(blockStateModelGenerator, ModBlocks.WAXED_OXIDIZED_COPPER_BARS);

        registerPaneModel(blockStateModelGenerator, ModBlocks.TREATED_STEEL_BARS);

        registerPaneModel(blockStateModelGenerator, ModBlocks.SILVER_BARS);*/

        //registerOrientableTrapdoorLadder(blockStateModelGenerator, ModDecorativeBlocks.ROPE_LADDER);

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

        blockStateModelGenerator.registerMultifaceBlock(ModNatureBlocks.STICKY_ICE);
    }

    public final void registerFanModel(BlockStateModelGenerator blockStateCollector, Block coralFanBlock) {
        TexturedModel texturedModel = TexturedModel.CORAL_FAN.get(coralFanBlock);
        Identifier identifier = texturedModel.upload(coralFanBlock, blockStateCollector.modelCollector);
        WeightedVariant weightedVariant = BlockStateModelGenerator.createWeightedVariant(identifier);
        blockStateCollector.blockStateCollector.accept(BlockStateModelGenerator.createSingletonBlockState(coralFanBlock, weightedVariant));
        blockStateCollector.registerItemModel(coralFanBlock);
    }

    public final void registerFlowerPotPlant(BlockStateModelGenerator blockStateModelGenerator, Block plantBlock, Block flowerPotBlock, BlockStateModelGenerator.CrossType tintType) {
        TextureMap textureMap = TextureMap.plant(plantBlock);
        Identifier identifier = tintType.getFlowerPotCrossModel().upload(flowerPotBlock, textureMap, blockStateModelGenerator.modelCollector);
        WeightedVariant weightedVariant = BlockStateModelGenerator.createWeightedVariant(identifier);
        blockStateModelGenerator.blockStateCollector.accept(BlockStateModelGenerator.createSingletonBlockState(flowerPotBlock, weightedVariant));
    }

    public void registerVanillaVerticalSlabModelBlockStates(BlockStateModelGenerator blockStateModelGenerator, Block block, Block origin, String slabPath) {
        Identifier fullBlockId = ModelIds.getBlockModelId(origin);

        WeightedVariant variantId = BlockStateModelGenerator.createWeightedVariant(MEModels.VERTICAL_SLAB.upload(block,
                TextureMap.of(TextureKey.ALL, Identifier.of("minecraft", "block/" + slabPath)),
                blockStateModelGenerator.modelCollector));

        WeightedVariant inner = BlockStateModelGenerator.createWeightedVariant(MEModels.VERTICAL_SLAB_INNER.upload(block, TextureMap.of(TextureKey.ALL, Identifier.of("minecraft", "block/" + slabPath)), blockStateModelGenerator.modelCollector));
        WeightedVariant outer = BlockStateModelGenerator.createWeightedVariant(MEModels.VERTICAL_SLAB_OUTER.upload(block, TextureMap.of(TextureKey.ALL, Identifier.of("minecraft", "block/" + slabPath)), blockStateModelGenerator.modelCollector));

        registerVerticalSlab(blockStateModelGenerator, block, fullBlockId, variantId, inner, outer);
    }

    public void registerVerticalSlabModelBlockStates(BlockStateModelGenerator blockStateModelGenerator, Block block, Block origin, String slabPath) {
        Identifier fullBlockId = ModelIds.getBlockModelId(origin);
        WeightedVariant variantId = BlockStateModelGenerator.createWeightedVariant(MEModels.VERTICAL_SLAB.upload(block,
                TextureMap.of(TextureKey.ALL, Identifier.of(MiddleEarth.MOD_ID, "block/" + slabPath)),
                blockStateModelGenerator.modelCollector));

        WeightedVariant inner = BlockStateModelGenerator.createWeightedVariant(MEModels.VERTICAL_SLAB_INNER.upload(block, TextureMap.of(TextureKey.ALL, Identifier.of(MiddleEarth.MOD_ID, "block/" + slabPath)), blockStateModelGenerator.modelCollector));
        WeightedVariant outer = BlockStateModelGenerator.createWeightedVariant(MEModels.VERTICAL_SLAB_OUTER.upload(block, TextureMap.of(TextureKey.ALL, Identifier.of(MiddleEarth.MOD_ID, "block/" + slabPath)), blockStateModelGenerator.modelCollector));

        registerVerticalSlab(blockStateModelGenerator, block, fullBlockId, variantId, inner, outer);
    }

    public void registerColumnVerticalSlabModelBlockStates(BlockStateModelGenerator blockStateModelGenerator, Block block, Block origin,
                                                           String modId, String topTexturePath, String bottomTexturePath, String sideTexturePath) {
        Identifier fullBlockId = ModelIds.getBlockModelId(origin);
        Identifier sideTexture = Identifier.of(modId, "block/" + sideTexturePath);

        WeightedVariant variantId = BlockStateModelGenerator.createWeightedVariant(MEModels.VERTICAL_COLUMN_SLAB.upload(block, (new TextureMap())
                        .put(TextureKey.TOP, Identifier.of(modId, "block/" + topTexturePath))
                        .put(TextureKey.BOTTOM, Identifier.of(modId, "block/" + bottomTexturePath))
                        .put(TextureKey.SIDE, sideTexture)
                        .put(TextureKey.PARTICLE, sideTexture),
                blockStateModelGenerator.modelCollector));

        WeightedVariant inner = BlockStateModelGenerator.createWeightedVariant(MEModels.VERTICAL_COLUMN_SLAB_INNER.upload(block, (new TextureMap())
                        .put(TextureKey.TOP, Identifier.of(modId, "block/" + topTexturePath))
                        .put(TextureKey.BOTTOM, Identifier.of(modId, "block/" + bottomTexturePath))
                        .put(TextureKey.SIDE, sideTexture)
                        .put(TextureKey.PARTICLE, sideTexture),
                blockStateModelGenerator.modelCollector));

        WeightedVariant outer = BlockStateModelGenerator.createWeightedVariant(MEModels.VERTICAL_COLUMN_SLAB_OUTER.upload(block, (new TextureMap())
                        .put(TextureKey.TOP, Identifier.of(modId, "block/" + topTexturePath))
                        .put(TextureKey.BOTTOM, Identifier.of(modId, "block/" + bottomTexturePath))
                        .put(TextureKey.SIDE, sideTexture)
                        .put(TextureKey.PARTICLE, sideTexture),
                blockStateModelGenerator.modelCollector));
        registerVerticalSlab(blockStateModelGenerator, block, fullBlockId, variantId, inner, outer);
    }

    //TODO fix all that with new stuff
    private void registerVerticalSlab(BlockStateModelGenerator blockStateModelGenerator, Block block, Identifier fullBlock, WeightedVariant regular, WeightedVariant inner, WeightedVariant outer) {
        WeightedVariant fullBlockVariant;
        if(Registries.BLOCK.getId(block).getPath().contains("waxed_") && Registries.BLOCK.getId(block).getPath().contains("copper")){
            fullBlockVariant = BlockStateModelGenerator.createWeightedVariant(Identifier.ofVanilla(fullBlock.getPath().replaceAll("waxed_", "")));
        } else {
            fullBlockVariant = BlockStateModelGenerator.createWeightedVariant(Identifier.ofVanilla(fullBlock.getPath()));
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
                        .register(Direction.SOUTH,false,  VerticalSlabShape.INNER_RIGHT, inner.apply(ROTATE_Y_270).apply(UV_LOCK))
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
                        .register(Direction.SOUTH,true,  VerticalSlabShape.INNER_RIGHT, fullBlockVariant.apply(UV_LOCK))
                        .register(Direction.NORTH, true, VerticalSlabShape.INNER_RIGHT, fullBlockVariant.apply(UV_LOCK))
                        .register(Direction.EAST, true, VerticalSlabShape.INNER_LEFT, fullBlockVariant.apply(UV_LOCK))
                        .register(Direction.WEST, true, VerticalSlabShape.INNER_LEFT, fullBlockVariant.apply(UV_LOCK))
                        .register(Direction.SOUTH, true, VerticalSlabShape.INNER_LEFT, fullBlockVariant.apply(UV_LOCK))
                        .register(Direction.NORTH, true, VerticalSlabShape.INNER_LEFT, fullBlockVariant.apply(UV_LOCK)));

        blockStateModelGenerator.registerParentedItemModel(block, ModelIds.getBlockModelId(block));
        blockStateModelGenerator.blockStateCollector.accept(blockstate);
    }/*

    public void registerWoodStoolModelBlockStates(BlockStateModelGenerator blockStateModelGenerator, Block block){
        Identifier texture = Identifier.of(MiddleEarth.MOD_ID, "block/" + Registries.BLOCK.getId(block).getPath().replaceAll("stool", "chair"));
        blockStateModelGenerator.blockStateCollector.accept(VariantsBlockStateSupplier.create(block,
                MEModels.WOOD_STOOL.upload(block,
                        (new TextureMap()).put(TextureKey.ALL, texture)
                                .put(TextureKey.PARTICLE, texture),
                        blockStateModelGenerator.modelCollector)).put(VariantSettings.UVLOCK, false))
                .coordinate(BlockStateModelGenerator.createNorthDefaultHorizontalRotationStates()));
    }

    public void registerWoodBenchModelBlockStates(BlockStateModelGenerator blockStateModelGenerator, Block block){
        Identifier texture = Identifier.of(MiddleEarth.MOD_ID, "block/" + Registries.BLOCK.getId(block).getPath());
        blockStateModelGenerator.blockStateCollector.accept(VariantsBlockStateSupplier.create(block,
                        MEModels.WOOD_BENCH.upload(block,
                                (new TextureMap()).put(TextureKey.ALL, texture)
                                        .put(TextureKey.PARTICLE, texture),
                                blockStateModelGenerator.modelCollector)).put(VariantSettings.UVLOCK, false))
                .coordinate(BlockStateModelGenerator.createNorthDefaultHorizontalRotationStates()));
    }

    public void registerWoodTableModelBlockStates(BlockStateModelGenerator blockStateModelGenerator, Block block){
        Identifier texture = Identifier.of(MiddleEarth.MOD_ID, "block/" + Registries.BLOCK.getId(block).getPath());
        blockStateModelGenerator.blockStateCollector.accept(VariantsBlockStateSupplier.create(block,
                        MEModels.WOOD_TABLE.upload(block,
                                (new TextureMap()).put(TextureKey.ALL, texture)
                                        .put(TextureKey.PARTICLE, texture),
                                blockStateModelGenerator.modelCollector)).put(VariantSettings.UVLOCK, false)));
    }

    public void registerWoodChairModelBlockStates(BlockStateModelGenerator blockStateModelGenerator, Block block){
        Identifier texture = Identifier.of(MiddleEarth.MOD_ID, "block/" + Registries.BLOCK.getId(block).getPath());
        blockStateModelGenerator.blockStateCollector.accept(VariantsBlockStateSupplier.create(block,
                        MEModels.WOOD_CHAIR.upload(block,
                                (new TextureMap()).put(TextureKey.ALL, texture)
                                        .put(TextureKey.PARTICLE, texture),
                                blockStateModelGenerator.modelCollector)).put(VariantSettings.UVLOCK, false))
                .coordinate(BlockStateModelGenerator.createNorthDefaultHorizontalRotationStates()));
    }

    public void registerStoneStoolModelBlockStates(BlockStateModelGenerator blockStateModelGenerator, Block block, Identifier texture) {
        blockStateModelGenerator.blockStateCollector.accept(VariantsBlockStateSupplier.create(block,
                        MEModels.STONE_STOOL.upload(block,
                                (new TextureMap()).put(TextureKey.ALL, texture)
                                        .put(TextureKey.PARTICLE, texture),
                                blockStateModelGenerator.modelCollector)).put(VariantSettings.UVLOCK, false))
                .coordinate(BlockStateModelGenerator.createNorthDefaultHorizontalRotationStates()));
    }

    public void registerStoneTableModelBlockStates(BlockStateModelGenerator blockStateModelGenerator, Block block, Identifier texture) {
        blockStateModelGenerator.blockStateCollector.accept(VariantsBlockStateSupplier.create(block,
                MEModels.STONE_TABLE.upload(block,
                        (new TextureMap()).put(TextureKey.ALL, texture)
                                .put(TextureKey.PARTICLE, texture),
                        blockStateModelGenerator.modelCollector)).put(VariantSettings.UVLOCK, false)));
    }

    public void registerStoneChairModelBlockStates(BlockStateModelGenerator blockStateModelGenerator, Block block, Identifier texture) {
        blockStateModelGenerator.blockStateCollector.accept(VariantsBlockStateSupplier.create(block,
                        MEModels.STONE_CHAIR.upload(block,
                                (new TextureMap()).put(TextureKey.ALL, texture)
                                        .put(TextureKey.PARTICLE, texture),
                                blockStateModelGenerator.modelCollector)).put(VariantSettings.UVLOCK, false))
                .coordinate(BlockStateModelGenerator.createNorthDefaultHorizontalRotationStates()));
    }

    public final void registerDoubleBlock(BlockStateModelGenerator blockStateModelGenerator, Block doubleBlock, BlockStateModelGenerator.CrossType tintType) {
        blockStateModelGenerator.registerItemModel(doubleBlock.asItem());
        Identifier identifier = blockStateModelGenerator.createSubModel(doubleBlock, "_top", tintType.getCrossModel(), TextureMap::cross);
        Identifier identifier2 = blockStateModelGenerator.createSubModel(doubleBlock, "_bottom", tintType.getCrossModel(), TextureMap::cross);
        blockStateModelGenerator.registerDoubleBlock(doubleBlock, identifier, identifier2);
    }*/

    public final void registerLargeDoor(BlockStateModelGenerator blockStateModelGenerator, LargeDoorBlock largeDoor, IntProperty part){
        var statesMap = BlockStateVariantMap.models(Properties.HORIZONTAL_FACING, Properties.OPEN, Properties.DOOR_HINGE, part);
        int rot = 0;
        for (int i = 0; i < largeDoor.getDoorWidth() * largeDoor.getDoorHeight(); i++){
            for(int k = 2; k < 6; k++){
                rot = switch (k) {
                    case 2 -> 0;
                    case 3 -> 180;
                    case 4 -> 270;
                    case 5 -> 90;
                    default -> rot;
                };

                WeightedVariant weightedVariantLeft = BlockStateModelGenerator.createWeightedVariant(Identifier.of(MiddleEarth.MOD_ID,"block/" + Registries.BLOCK.getId(largeDoor).getPath() + "_left_" + i));
                WeightedVariant weightedVariantLeftOpen = BlockStateModelGenerator.createWeightedVariant(Identifier.of(MiddleEarth.MOD_ID,"block/" + Registries.BLOCK.getId(largeDoor).getPath() + "_left_open_" + i));
                WeightedVariant weightedVarianRight = BlockStateModelGenerator.createWeightedVariant(Identifier.of(MiddleEarth.MOD_ID,"block/" + Registries.BLOCK.getId(largeDoor).getPath() + "_right_" + i));
                WeightedVariant weightedVarianRightOpen = BlockStateModelGenerator.createWeightedVariant(Identifier.of(MiddleEarth.MOD_ID,"block/" + Registries.BLOCK.getId(largeDoor).getPath() + "_right_open_" + i));

                statesMap.register(Direction.byIndex(k), false, DoorHinge.LEFT, i,
                        weightedVariantLeft.apply(ModelVariantOperator.ROTATION_Y.withValue(AxisRotation.valueOf("R" + rot))));

                statesMap.register(Direction.byIndex(k), true, DoorHinge.LEFT, i,
                        weightedVariantLeftOpen.apply(ModelVariantOperator.ROTATION_Y.withValue(AxisRotation.valueOf("R" + rot))));

                statesMap.register(Direction.byIndex(k), false, DoorHinge.RIGHT, i,
                        weightedVarianRight.apply(ModelVariantOperator.ROTATION_Y.withValue(AxisRotation.valueOf("R" + rot))));

                statesMap.register(Direction.byIndex(k), true, DoorHinge.RIGHT, i,
                        weightedVarianRightOpen.apply(ModelVariantOperator.ROTATION_Y.withValue(AxisRotation.valueOf("R" + rot))));

                if (k == 2){
                    MEModels.LARGE_DOOR_LEFT.upload(largeDoor, "_left_" + i,
                            (new TextureMap()).put(TextureKey.ALL, Identifier.of(MiddleEarth.MOD_ID,"block/" + Registries.BLOCK.getId(largeDoor).getPath() + "_" + i))
                                    .put(TextureKey.PARTICLE, Identifier.of(MiddleEarth.MOD_ID,"block/" + Registries.BLOCK.getId(largeDoor).getPath() + "_" + i)),
                            blockStateModelGenerator.modelCollector);

                    MEModels.LARGE_DOOR_LEFT_OPEN.upload(largeDoor,"_left_open_" + i,
                            (new TextureMap()).put(TextureKey.ALL, Identifier.of(MiddleEarth.MOD_ID, "block/" +Registries.BLOCK.getId(largeDoor).getPath() + "_" + i))
                                    .put(TextureKey.PARTICLE, Identifier.of(MiddleEarth.MOD_ID, "block/" +Registries.BLOCK.getId(largeDoor).getPath() + "_" + i)),
                            blockStateModelGenerator.modelCollector);

                    MEModels.LARGE_DOOR_RIGHT.upload(largeDoor,"_right_" + i,
                            (new TextureMap()).put(TextureKey.ALL, Identifier.of(MiddleEarth.MOD_ID, "block/" +Registries.BLOCK.getId(largeDoor).getPath() + "_" + i))
                                    .put(TextureKey.PARTICLE, Identifier.of(MiddleEarth.MOD_ID, "block/" +Registries.BLOCK.getId(largeDoor).getPath() + "_" + i)),
                            blockStateModelGenerator.modelCollector);

                    MEModels.LARGE_DOOR_RIGHT_OPEN.upload(largeDoor,"_right_open_" + i,
                            (new TextureMap()).put(TextureKey.ALL, Identifier.of(MiddleEarth.MOD_ID, "block/" +Registries.BLOCK.getId(largeDoor).getPath() + "_" + i))
                                    .put(TextureKey.PARTICLE, Identifier.of(MiddleEarth.MOD_ID, "block/" +Registries.BLOCK.getId(largeDoor).getPath() + "_" + i)),
                            blockStateModelGenerator.modelCollector);
                }
            }
        }
        VariantsBlockModelDefinitionCreator blockstate = VariantsBlockModelDefinitionCreator.of(largeDoor).with(statesMap);
        blockStateModelGenerator.blockStateCollector.accept(blockstate);
    }

    public final void registerThickLargeDoor(BlockStateModelGenerator blockStateModelGenerator, LargeDoorBlock largeDoor, IntProperty part){
        var statesMap = BlockStateVariantMap.models(Properties.HORIZONTAL_FACING, Properties.OPEN, Properties.DOOR_HINGE, part);
        int rot = 0;
        for (int i = 0; i < largeDoor.getDoorWidth() * largeDoor.getDoorHeight(); i++){
            for(int k = 2; k < 6; k++){
                rot = switch (k) {
                    case 2 -> 0;
                    case 3 -> 180;
                    case 4 -> 270;
                    case 5 -> 90;
                    default -> rot;
                };

                WeightedVariant weightedVariantLeft = BlockStateModelGenerator.createWeightedVariant(Identifier.of(MiddleEarth.MOD_ID,"block/" + Registries.BLOCK.getId(largeDoor).getPath() + "_left_" + i));
                WeightedVariant weightedVariantLeftOpen = BlockStateModelGenerator.createWeightedVariant(Identifier.of(MiddleEarth.MOD_ID,"block/" + Registries.BLOCK.getId(largeDoor).getPath() + "_left_open_" + i));
                WeightedVariant weightedVarianRight = BlockStateModelGenerator.createWeightedVariant(Identifier.of(MiddleEarth.MOD_ID,"block/" + Registries.BLOCK.getId(largeDoor).getPath() + "_right_" + i));
                WeightedVariant weightedVarianRightOpen = BlockStateModelGenerator.createWeightedVariant(Identifier.of(MiddleEarth.MOD_ID,"block/" + Registries.BLOCK.getId(largeDoor).getPath() + "_right_open_" + i));

                statesMap.register(Direction.byIndex(k), false, DoorHinge.LEFT, i,
                        weightedVariantLeft.apply(ModelVariantOperator.ROTATION_Y.withValue(AxisRotation.valueOf("R" + rot))));

                statesMap.register(Direction.byIndex(k), true, DoorHinge.LEFT, i,
                        weightedVariantLeftOpen.apply(ModelVariantOperator.ROTATION_Y.withValue(AxisRotation.valueOf("R" + rot))));

                statesMap.register(Direction.byIndex(k), false, DoorHinge.RIGHT, i,
                        weightedVarianRight.apply(ModelVariantOperator.ROTATION_Y.withValue(AxisRotation.valueOf("R" + rot))));

                statesMap.register(Direction.byIndex(k), true, DoorHinge.RIGHT, i,
                        weightedVarianRightOpen.apply(ModelVariantOperator.ROTATION_Y.withValue(AxisRotation.valueOf("R" + rot))));

                if (k == 2){
                    MEModels.LARGE_THICK_DOOR_LEFT.upload(largeDoor, "_left_" + i,
                            (new TextureMap()).put(TextureKey.ALL, Identifier.of(MiddleEarth.MOD_ID,"block/" + Registries.BLOCK.getId(largeDoor).getPath() + "_" + i))
                                    .put(TextureKey.PARTICLE, Identifier.of(MiddleEarth.MOD_ID,"block/" + Registries.BLOCK.getId(largeDoor).getPath() + "_" + i)),
                            blockStateModelGenerator.modelCollector);

                    MEModels.LARGE_THICK_DOOR_LEFT_OPEN.upload(largeDoor,"_left_open_" + i,
                            (new TextureMap()).put(TextureKey.ALL, Identifier.of(MiddleEarth.MOD_ID, "block/" +Registries.BLOCK.getId(largeDoor).getPath() + "_" + i))
                                    .put(TextureKey.PARTICLE, Identifier.of(MiddleEarth.MOD_ID, "block/" +Registries.BLOCK.getId(largeDoor).getPath() + "_" + i)),
                            blockStateModelGenerator.modelCollector);

                    MEModels.LARGE_THICK_DOOR_RIGHT.upload(largeDoor,"_right_" + i,
                            (new TextureMap()).put(TextureKey.ALL, Identifier.of(MiddleEarth.MOD_ID, "block/" +Registries.BLOCK.getId(largeDoor).getPath() + "_" + i))
                                    .put(TextureKey.PARTICLE, Identifier.of(MiddleEarth.MOD_ID, "block/" +Registries.BLOCK.getId(largeDoor).getPath() + "_" + i)),
                            blockStateModelGenerator.modelCollector);

                    MEModels.LARGE_THICK_DOOR_RIGHT_OPEN.upload(largeDoor,"_right_open_" + i,
                            (new TextureMap()).put(TextureKey.ALL, Identifier.of(MiddleEarth.MOD_ID, "block/" +Registries.BLOCK.getId(largeDoor).getPath() + "_" + i))
                                    .put(TextureKey.PARTICLE, Identifier.of(MiddleEarth.MOD_ID, "block/" +Registries.BLOCK.getId(largeDoor).getPath() + "_" + i)),
                            blockStateModelGenerator.modelCollector);
                }
            }
        }

        VariantsBlockModelDefinitionCreator blockstate = VariantsBlockModelDefinitionCreator.of(largeDoor).with(statesMap);
        blockStateModelGenerator.blockStateCollector.accept(blockstate);
    }/*


    public final void registerLeadGlassPane(BlockStateModelGenerator blockStateModelGenerator, Block glass, Block glassPane) {
        blockStateModelGenerator.registerSimpleCubeAll(glass);
        TextureMap textureMap = TextureMap.paneAndTopForEdge(glass, ModDecorativeBlocks.LEAD_GLASS_PANE);
        Identifier identifier = Models.TEMPLATE_GLASS_PANE_POST.upload(glassPane, textureMap, blockStateModelGenerator.modelCollector);
        Identifier identifier2 = Models.TEMPLATE_GLASS_PANE_SIDE.upload(glassPane, textureMap, blockStateModelGenerator.modelCollector);
        Identifier identifier3 = Models.TEMPLATE_GLASS_PANE_SIDE_ALT.upload(glassPane, textureMap, blockStateModelGenerator.modelCollector);
        Identifier identifier4 = Models.TEMPLATE_GLASS_PANE_NOSIDE.upload(glassPane, textureMap, blockStateModelGenerator.modelCollector);
        Identifier identifier5 = Models.TEMPLATE_GLASS_PANE_NOSIDE_ALT.upload(glassPane, textureMap, blockStateModelGenerator.modelCollector);
        Item item = glassPane.asItem();
        Models.GENERATED.upload(ModelIds.getItemModelId(item), TextureMap.layer0(glass), blockStateModelGenerator.modelCollector);
        blockStateModelGenerator.blockStateCollector.accept(MultipartBlockStateSupplier.create(glassPane).with(identifier)).with(When.create().set(Properties.NORTH, true), identifier2)).with(When.create().set(Properties.EAST, true), identifier2.apply(ROTATE_Y_90)).with(When.create().set(Properties.SOUTH, true), identifier3)).with(When.create().set(Properties.WEST, true), identifier3.apply(ROTATE_Y_90)).with(When.create().set(Properties.NORTH, false), identifier4)).with(When.create().set(Properties.EAST, false), identifier5)).with(When.create().set(Properties.SOUTH, false), identifier5.apply(ROTATE_Y_90)).with(When.create().set(Properties.WEST, false), identifier4.apply(ROTATE_Y_270)));
    }

    public final void registerPaneModel(BlockStateModelGenerator blockStateModelGenerator, Block pane) {
        TextureMap textureMap = TextureMap.paneAndTopForEdge(pane, pane);
        Identifier identifier = Models.TEMPLATE_GLASS_PANE_POST.upload(pane, textureMap, blockStateModelGenerator.modelCollector);
        Identifier identifier2 = Models.TEMPLATE_GLASS_PANE_SIDE.upload(pane, textureMap, blockStateModelGenerator.modelCollector);
        Identifier identifier3 = Models.TEMPLATE_GLASS_PANE_SIDE_ALT.upload(pane, textureMap, blockStateModelGenerator.modelCollector);
        Identifier identifier4 = Models.TEMPLATE_GLASS_PANE_NOSIDE.upload(pane, textureMap, blockStateModelGenerator.modelCollector);
        Identifier identifier5 = Models.TEMPLATE_GLASS_PANE_NOSIDE_ALT.upload(pane, textureMap, blockStateModelGenerator.modelCollector);
        Item item = pane.asItem();
        Models.GENERATED.upload(ModelIds.getItemModelId(item), TextureMap.layer0(pane), blockStateModelGenerator.modelCollector);
        blockStateModelGenerator.blockStateCollector.accept(MultipartBlockStateSupplier.create(pane).with(identifier)).with(When.create().set(Properties.NORTH, true), identifier2)).with(When.create().set(Properties.EAST, true), identifier2.apply(ROTATE_Y_90)).with(When.create().set(Properties.SOUTH, true), identifier3)).with(When.create().set(Properties.WEST, true), identifier3.apply(ROTATE_Y_90)).with(When.create().set(Properties.NORTH, false), identifier4)).with(When.create().set(Properties.EAST, false), identifier5)).with(When.create().set(Properties.SOUTH, false), identifier5.apply(ROTATE_Y_90)).with(When.create().set(Properties.WEST, false), identifier4.apply(ROTATE_Y_270)));
    }

    private void registerLayers(BlockStateModelGenerator blockStateModelGenerator, Block layers, Block origin, Boolean isVanilla) {
        blockStateModelGenerator.blockStateCollector.accept(VariantsBlockStateSupplier.create(layers).coordinate(BlockStateVariantMap.create(Properties.LAYERS).register((height) -> {
            BlockStateVariant var10000 = BlockStateVariant.create();
            VariantSetting var10001 = VariantSettings.MODEL;
            Identifier var2;
            if (height < 8) {
                Block var10002 = layers;
                int var10003 = height;
                var2 = ModelIds.getBlockSubModelId(var10002, "_height" + var10003 * 2);
            } else if (isVanilla) {
                var2 = Identifier.of("minecraft", "block/" + Registries.BLOCK.getId(origin).getPath());
            } else {
                var2 = Identifier.of(MiddleEarth.MOD_ID, "block/" + Registries.BLOCK.getId(origin).getPath());
            }
            return var10000.put(var10001, var2);
        })));
        blockStateModelGenerator.registerParentedItemModel(layers, ModelIds.getBlockSubModelId(layers, "_height2"));
    }

    private void registerTopWaterblock(BlockStateModelGenerator blockStateModelGenerator, Block block) {
        blockStateModelGenerator.registerItemModel(block);
        blockStateModelGenerator.blockStateCollector.accept(BlockStateModelGenerator.createBlockStateWithRandomHorizontalRotations(block, ModelIds.getBlockModelId(block)));
    }

    public void registerStoneTrapdoor(BlockStateModelGenerator blockStateModelGenerator, Block trapdoorBlock) {
        TextureMap textureMap = TextureMap.texture(Identifier.of(MiddleEarth.MOD_ID, "block/" + Registries.BLOCK.getId(trapdoorBlock).getPath().replaceAll("_trapdoor", "")));
        Identifier identifier = Models.TEMPLATE_TRAPDOOR_TOP.upload(trapdoorBlock, textureMap, blockStateModelGenerator.modelCollector);
        Identifier identifier2 = Models.TEMPLATE_TRAPDOOR_BOTTOM.upload(trapdoorBlock, textureMap, blockStateModelGenerator.modelCollector);
        Identifier identifier3 = Models.TEMPLATE_TRAPDOOR_OPEN.upload(trapdoorBlock, textureMap, blockStateModelGenerator.modelCollector);
        blockStateModelGenerator.blockStateCollector.accept(BlockStateModelGenerator.createTrapdoorBlockState(trapdoorBlock, identifier, identifier2, identifier3));
        blockStateModelGenerator.registerParentedItemModel(trapdoorBlock, identifier2);
    }*/

    public void registerRocksBlock(BlockStateModelGenerator blockStateModelGenerator, Block rocksBlock, Block origin) {
        WeightedVariant stage0 = BlockStateModelGenerator.createWeightedVariant(MEModels.ROCKS_STAGE_0.upload(rocksBlock,
                TextureMap.of(TextureKey.ALL, Identifier.of(Registries.BLOCK.getId(origin).getNamespace(), "block/" + Registries.BLOCK.getId(origin).getPath())),
                blockStateModelGenerator.modelCollector));
        WeightedVariant stage1 = BlockStateModelGenerator.createWeightedVariant(MEModels.ROCKS_STAGE_1.upload(rocksBlock,
                TextureMap.of(TextureKey.ALL, Identifier.of(Registries.BLOCK.getId(origin).getNamespace(), "block/" + Registries.BLOCK.getId(origin).getPath())),
                blockStateModelGenerator.modelCollector));
        WeightedVariant stage2 = BlockStateModelGenerator.createWeightedVariant(MEModels.ROCKS_STAGE_2.upload(rocksBlock,
                TextureMap.of(TextureKey.ALL, Identifier.of(Registries.BLOCK.getId(origin).getNamespace(), "block/" + Registries.BLOCK.getId(origin).getPath())),
                blockStateModelGenerator.modelCollector));
        WeightedVariant stage3 = BlockStateModelGenerator.createWeightedVariant(MEModels.ROCKS_STAGE_3.upload(rocksBlock,
                TextureMap.of(TextureKey.ALL, Identifier.of(Registries.BLOCK.getId(origin).getNamespace(), "block/" + Registries.BLOCK.getId(origin).getPath())),
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

    public void registerVanillaTrapdoor(BlockStateModelGenerator blockStateModelGenerator, Block trapdoorBlock) {
        TextureMap textureMap;
        if (Registries.BLOCK.getId(trapdoorBlock).getPath().contains("basalt")) {
            textureMap = TextureMap.texture(Identifier.of("block/" + Registries.BLOCK.getId(trapdoorBlock).getPath().replaceAll("_trapdoor", "_side")));
        } else {
            textureMap = TextureMap.texture(Identifier.of("block/" + Registries.BLOCK.getId(trapdoorBlock).getPath().replaceAll("_trapdoor", "")));
        }
        Identifier identifier = Models.TEMPLATE_TRAPDOOR_TOP.upload(trapdoorBlock, textureMap, blockStateModelGenerator.modelCollector);
        Identifier identifier2 = Models.TEMPLATE_TRAPDOOR_BOTTOM.upload(trapdoorBlock, textureMap, blockStateModelGenerator.modelCollector);
        Identifier identifier3 = Models.TEMPLATE_TRAPDOOR_OPEN.upload(trapdoorBlock, textureMap, blockStateModelGenerator.modelCollector);
        //blockStateModelGenerator.blockStateCollector.accept(BlockStateModelGenerator.createTrapdoorBlockState(trapdoorBlock, identifier, identifier2, identifier3));
        blockStateModelGenerator.registerParentedItemModel(trapdoorBlock, identifier2);
    }


    //TODO to fix datagen of trapdoor
    /*public void registerOrientableTrapdoorLadder(BlockStateModelGenerator blockStateModelGenerator, Block ladderBlock) {
        Identifier texture = Identifier.of(MiddleEarth.MOD_ID,"block/" + Registries.BLOCK.getId(ladderBlock).getPath());
        blockStateModelGenerator.blockStateCollector.accept(VariantsBlockStateSupplier.create(ladderBlock,
                ModelIds.getBlockModelId(ladderBlock)))
                .coordinate(BlockStateVariantMap.create(Properties.BLOCK_FACE, Properties.HORIZONTAL_FACING)
                        .register(BlockFace.FLOOR, Direction.NORTH, BlockStateVariant.create())
                        .register(BlockFace.FLOOR, Direction.EAST, BlockStateVariant.create(.apply(ROTATE_Y_90))
                        .register(BlockFace.FLOOR, Direction.SOUTH, BlockStateVariant.create(.apply(ROTATE_Y_180))
                        .register(BlockFace.FLOOR, Direction.WEST, BlockStateVariant.create(.apply(ROTATE_Y_270))
                        .register(BlockFace.WALL, Direction.NORTH, BlockStateVariant.create().put(VariantSettings.X, VariantSettings.Rotation.R90))
                        .register(BlockFace.WALL, Direction.EAST, BlockStateVariant.create().put(VariantSettings.X, VariantSettings.Rotation.R90.apply(ROTATE_Y_90))
                        .register(BlockFace.WALL, Direction.SOUTH, BlockStateVariant.create().put(VariantSettings.X, VariantSettings.Rotation.R90.apply(ROTATE_Y_180))
                        .register(BlockFace.WALL, Direction.WEST, BlockStateVariant.create().put(VariantSettings.X, VariantSettings.Rotation.R90.apply(ROTATE_Y_270))
                        .register(BlockFace.CEILING, Direction.SOUTH, BlockStateVariant.create().put(VariantSettings.X, VariantSettings.Rotation.R180))
                        .register(BlockFace.CEILING, Direction.WEST, BlockStateVariant.create().put(VariantSettings.X, VariantSettings.Rotation.R180.apply(ROTATE_Y_90))
                        .register(BlockFace.CEILING, Direction.NORTH, BlockStateVariant.create().put(VariantSettings.X, VariantSettings.Rotation.R180.apply(ROTATE_Y_180))
                        .register(BlockFace.CEILING, Direction.EAST, BlockStateVariant.create().put(VariantSettings.X, VariantSettings.Rotation.R180.apply(ROTATE_Y_270))));

        MEModels.THICK_LADDER.upload(ladderBlock, new TextureMap().put(TextureKey.TEXTURE, texture).put(TextureKey.PARTICLE,texture), blockStateModelGenerator.modelCollector);
    }*/

    public static final Identifier TRIM_TYPE = Identifier.of("trim_type");
    private static final List<TrimMaterial> TRIM_MATERIALS = List.of(
            new TrimMaterial("quartz", ArmorTrimMaterials.QUARTZ, Map.of()),
            new TrimMaterial("iron", ArmorTrimMaterials.IRON, Map.of(EquipmentAssetKeys.IRON, "iron_darker")),
            new TrimMaterial("netherite", ArmorTrimMaterials.NETHERITE, Map.of(EquipmentAssetKeys.NETHERITE, "netherite_darker")),
            new TrimMaterial("redstone", ArmorTrimMaterials.REDSTONE, Map.of()),
            new TrimMaterial("copper", ArmorTrimMaterials.COPPER, Map.of()),
            new TrimMaterial("gold", ArmorTrimMaterials.GOLD, Map.of(EquipmentAssetKeys.GOLD, "gold_darker")),
            new TrimMaterial("emerald", ArmorTrimMaterials.EMERALD, Map.of()),
            new TrimMaterial("diamond", ArmorTrimMaterials.DIAMOND, Map.of(EquipmentAssetKeys.DIAMOND, "diamond_darker")),
            new TrimMaterial("lapis", ArmorTrimMaterials.LAPIS, Map.of()),
            new TrimMaterial("amethyst", ArmorTrimMaterials.AMETHYST, Map.of()),
            new TrimMaterial("resin", ArmorTrimMaterials.RESIN, Map.of()));

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        for (SimpleWallModel.Wall wall : SimpleWallModel.blocks) {
            Identifier id = Registries.BLOCK.getId(wall.wall());
            itemModelGenerator.register(wall.wall().asItem(), new Model(Optional.of(id.withPath("block/" + id.getPath() + "_inventory")), Optional.empty()));
        }

        for (SimpleWallModel.Wall wall : SimpleWallModel.vanillaWalls) {
            Identifier id = Registries.BLOCK.getId(wall.wall());
            itemModelGenerator.register(wall.wall().asItem(), new Model(Optional.of(id.withPath("block/" + id.getPath() + "_inventory")), Optional.empty()));
        }

        for (Item item : SimpleItemModel.items) {
            itemModelGenerator.register(item, Models.GENERATED);
        }

        for (Item item : SimpleHandheldItemModel.items) {
            itemModelGenerator.register(item, Models.HANDHELD);
        }

        for (Item item : SimpleDoorInventoryModel.items) {
            itemModelGenerator.register(item, Models.GENERATED);
        }

        //TODO update all the model gen to new stuff

        for (Item item : SimpleBigItemModel.items) {
            itemModelGenerator.register(item, CustomItemModels.BIG_WEAPON);
            //itemModelGenerator.register(item, "_inventory", Models.HANDHELD);
        }

        for (Item item : SimpleBigItemModel.bigBows) {
            /*itemModelGenerator.register(item, CustomItemModels.LONGBOW);
            for (int i = 0; i < 3; i++) {
                itemModelGenerator.registerSubModel(item, "_pulling_" + i, CustomItemModels.LONGBOW);
                itemModelGenerator.registerSubModel(item, "_pulling_" + i + "_inventory", Models.HANDHELD);
            }*/
        }

        for (Item item : SimpleBigItemModel.genericItems) {
            //itemModelGenerator.register(item, "_inventory", Models.HANDHELD);
        }

        for (Item item : HotMetalsModel.items) {
            //itemModelGenerator.register(item, "_hot", Models.GENERATED);
        }

        for (Item item : HotMetalsModel.ingots) {
            //Models.GENERATED.upload(ModelIds.getItemSubModelId(item, "_hot"), TextureMap.layer0(Identifier.of(MiddleEarth.MOD_ID, "item/ingot_hot")), itemModelGenerator.writer);
        }

        for (Item item : HotMetalsModel.nuggets) {
            //Models.GENERATED.upload(ModelIds.getItemSubModelId(item, "_hot"), TextureMap.layer0(Identifier.of(MiddleEarth.MOD_ID, "item/nugget_hot")), itemModelGenerator.writer);
        }

        for (Item item : SimpleSpearModel.items) {
            //itemModelGenerator.register(item, "_inventory", Models.HANDHELD);
        }

        for (Item item : SimpleBowItemModel.items) {
            for (int i = 0; i < 3; i++) {
                //itemModelGenerator.register(item, "_pulling_" + i, CustomItemModels.BOW);
            }
        }

        for (Item item : SimpleCrossbowItemModel.items) {
            for (int i = 0; i < 3; i++) {
                //itemModelGenerator.register(item, "_pulling_" + i, CustomItemModels.CROSSBOW);
            }
            //itemModelGenerator.register(item, "_charged", CustomItemModels.CROSSBOW);
        }

        for (Item item : SimpleSpawnEggItemModel.items) {
            itemModelGenerator.register(item, CustomItemModels.TEMPLATE_SPAWN_EGG);
        }

        // Dyeables needs to be done manually (because of layers)

        SimpleDyeableItemModel.items.forEach(item -> {
            registerDyeableArmor(item, itemModelGenerator);
        });

        // CLUSTERS
        itemModelGenerator.register(ModBlocks.QUARTZ_CLUSTER.asItem(), Models.GENERATED);
        itemModelGenerator.register(ModBlocks.SMALL_QUARTZ_BUD.asItem(), Models.GENERATED);
        itemModelGenerator.register(ModBlocks.MEDIUM_QUARTZ_BUD.asItem(), Models.GENERATED);
        itemModelGenerator.register(ModBlocks.LARGE_QUARTZ_BUD.asItem(), Models.GENERATED);
        itemModelGenerator.register(ModBlocks.RED_AGATE_CLUSTER.asItem(), Models.GENERATED);
        itemModelGenerator.register(ModBlocks.SMALL_RED_AGATE_BUD.asItem(), Models.GENERATED);
        itemModelGenerator.register(ModBlocks.MEDIUM_RED_AGATE_BUD.asItem(), Models.GENERATED);
        itemModelGenerator.register(ModBlocks.LARGE_RED_AGATE_BUD.asItem(), Models.GENERATED);
        itemModelGenerator.register(ModBlocks.CITRINE_CLUSTER.asItem(), Models.GENERATED);
        itemModelGenerator.register(ModBlocks.SMALL_CITRINE_BUD.asItem(), Models.GENERATED);
        itemModelGenerator.register(ModBlocks.MEDIUM_CITRINE_BUD.asItem(), Models.GENERATED);
        itemModelGenerator.register(ModBlocks.LARGE_CITRINE_BUD.asItem(), Models.GENERATED);
        itemModelGenerator.register(ModBlocks.GLOWSTONE_CLUSTER.asItem(), Models.GENERATED);
        itemModelGenerator.register(ModBlocks.SMALL_GLOWSTONE_BUD.asItem(), Models.GENERATED);
        itemModelGenerator.register(ModBlocks.MEDIUM_GLOWSTONE_BUD.asItem(), Models.GENERATED);
        itemModelGenerator.register(ModBlocks.LARGE_GLOWSTONE_BUD.asItem(), Models.GENERATED);

       /* registerPalettedItem(ModResourceItems.ROD, itemModelGenerator);
        registerPalettedItem(ModResourceItems.LARGE_ROD, itemModelGenerator);

        registerPalettedItem(ModResourceItems.PICKAXE_HEAD, itemModelGenerator);
        registerPalettedItem(ModResourceItems.AXE_HEAD, itemModelGenerator);
        registerPalettedItem(ModResourceItems.SHOVEL_HEAD, itemModelGenerator);
        registerPalettedItem(ModResourceItems.HOE_HEAD, itemModelGenerator);

        registerPalettedItem(ModResourceItems.BLADE, itemModelGenerator);
        registerPalettedItem(ModResourceItems.SHORT_BLADE, itemModelGenerator);
        registerPalettedItem(ModResourceItems.LONG_BLADE, itemModelGenerator);
        registerPalettedItem(ModResourceItems.SWORD_HILT, itemModelGenerator);

        registerPalettedItem(ModResourceItems.MAIL_RING, itemModelGenerator);
        registerPalettedItem(ModResourceItems.MAIL, itemModelGenerator);

        registerPalettedItem(ModResourceItems.SCALE, itemModelGenerator);
        registerPalettedItem(ModResourceItems.SCALE_MAIL, itemModelGenerator);

        registerPalettedItem(ModResourceItems.ARMOR_PLATE, itemModelGenerator);

        registerPalettedItem(ModResourceItems.HELMET_PLATE, itemModelGenerator);
        registerPalettedItem(ModResourceItems.SHIELD_BORDER, itemModelGenerator);
        registerPalettedItem(ModResourceItems.SHIELD_PLATE, itemModelGenerator);*/
    }

    public final void registerDyeableArmor(Item armor, ItemModelGenerator itemModelGenerator) {
        Identifier identifier = ModelIds.getItemModelId(armor);
        Identifier identifier2 = TextureMap.getId(armor);
        Identifier identifier3 = TextureMap.getSubId(armor, "_overlay");
        Models.GENERATED_TWO_LAYERS.upload(identifier, TextureMap.layered(identifier2, identifier3), itemModelGenerator.modelCollector);
    }

    /*public final void registerPalettedItem(Item item, ItemModelGenerator itemModelGenerator) {
        Identifier identifierItem = Identifier.of(MiddleEarth.MOD_ID, "item/" + Registries.ITEM.getId(item).getPath());

        Identifier identifier2 = TextureMap.getId(item);

        Models.GENERATED.upload(identifierItem, TextureMap.layer0(identifierItem), itemModelGenerator.modelCollector, (id, textures) -> this.registerPalettedItemJson(item, id, textures, itemModelGenerator));
        for (TrimMaterial trimMaterial : TRIM_MATERIALS) {

            String string;
            if (trimMaterial.name.contains("iron")) {
                string = trimMaterial.name + "_darker";
            } else {
                string = trimMaterial.name;
            }

            Identifier identifier4 = itemModelGenerator.suffixTrim(identifierItem, string);
            String string2 = Registries.ITEM.getId(item).getPath() + "_trim_" + string;
            Identifier identifier5 = Identifier.of(MiddleEarth.MOD_ID, string2).withPrefixedPath("trims/items/");

            itemModelGenerator.uploadArmor(identifier4, identifier2, identifier5);
        }
    }

    public final JsonObject registerPalettedItemJson(Item item, Identifier id, Map<TextureKey, Identifier> textures, ItemModelGenerator itemModelGenerator) {
        Identifier identifierItem = Identifier.of(MiddleEarth.MOD_ID, "item/" + Registries.ITEM.getId(item).getPath());

        JsonObject jsonObject = Models.GENERATED_TWO_LAYERS.createJson(identifierItem, textures);
        JsonArray jsonArray = new JsonArray();
        for (TrimMaterial trimMaterial : TRIM_MATERIALS) {
            JsonObject jsonObject2 = new JsonObject();
            JsonObject jsonObject3 = new JsonObject();
            jsonObject3.addProperty(TRIM_TYPE.getPath(), Float.valueOf(trimMaterial.itemModelIndex()));
            jsonObject2.add("predicate", jsonObject3);
            String string;
            if (trimMaterial.name.contains("iron")) {
                string = trimMaterial.name + "_darker";
            } else {
                string = trimMaterial.name;
            }
            jsonObject2.addProperty("model", itemModelGenerator.suffixTrim(id, string).toString());
            jsonArray.add(jsonObject2);
        }

        jsonObject.add("overrides", jsonArray);

        return jsonObject;
    }*/

    private static record TrimMaterial(String name, RegistryKey<ArmorTrimMaterial> materialKey, Map<RegistryKey<EquipmentAsset>, String> overrideArmorMaterials) {

        TrimMaterial(String name, RegistryKey<ArmorTrimMaterial> materialKey, Map<RegistryKey<EquipmentAsset>, String> overrideArmorMaterials) {
            this.name = name;
            this.materialKey = materialKey;
            this.overrideArmorMaterials = overrideArmorMaterials;
        }

        public String texture(RegistryKey<EquipmentAsset> equipmentKey) {
            return (String)this.overrideArmorMaterials.getOrDefault(equipmentKey, this.name);
        }

        public String name() {
            return this.name;
        }

        public RegistryKey<ArmorTrimMaterial> materialKey() {
            return this.materialKey;
        }

        public Map<RegistryKey<EquipmentAsset>, String> overrideArmorMaterials() {
            return this.overrideArmorMaterials;
        }
    }
    record ItemTrimMaterial(String name, float itemModelIndex,
                            Map<RegistryEntry<ArmorMaterial>, String> overrideArmorMaterials) {

        public String getAppliedName(RegistryEntry<ArmorMaterial> armorMaterial) {
            return this.overrideArmorMaterials.getOrDefault(armorMaterial, this.name);
        }
    }
}
