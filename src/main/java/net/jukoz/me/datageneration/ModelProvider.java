package net.jukoz.me.datageneration;

import com.google.gson.JsonObject;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.block.*;
import net.jukoz.me.block.crop.*;
import net.jukoz.me.block.special.doors.*;
import net.jukoz.me.block.special.LargeDoorBlock;
import net.jukoz.me.block.special.verticalSlabs.VerticalSlabBlock;
import net.jukoz.me.block.special.verticalSlabs.VerticalSlabShape;
import net.jukoz.me.datageneration.content.CustomItemModels;
import net.jukoz.me.datageneration.content.MEModels;
import net.jukoz.me.datageneration.content.models.*;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.enums.BlockFace;
import net.minecraft.block.enums.DoorHinge;
import net.minecraft.data.client.*;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;

import java.util.Map;

import java.util.Optional;

public class ModelProvider extends FabricModelProvider {

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
            Identifier identifier = Models.CUBE_MIRRORED_ALL.upload(block, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);
            Identifier identifier2 = Models.CUBE_ALL.upload(block, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);

            blockStateModelGenerator.blockStateCollector.accept(BlockStateModelGenerator
                    .createBlockStateWithTwoModelAndRandomInversion(block, identifier, identifier2));
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
                    Identifier.of(MiddleEarth.MOD_ID,"block/" + Registries.BLOCK.getId(wood).getPath().replaceAll("_wood", "_log")));
            Identifier identifier = Models.CUBE_COLUMN.upload(wood, textureMap, blockStateModelGenerator.modelCollector);
            blockStateModelGenerator.blockStateCollector.accept(BlockStateModelGenerator.createAxisRotatedBlockState(wood, identifier));}

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
            Identifier id = ModelIds.getBlockModelId(block.origin());
            Block slab = block.slab();

            TexturedModel texturedModel = TexturedModel.CUBE_ALL.get(block.origin());
            Identifier bottom = Models.SLAB.upload(slab, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);
            Identifier top = Models.SLAB_TOP.upload(slab, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);

            blockStateModelGenerator.blockStateCollector.accept(BlockStateModelGenerator
                    .createSlabBlockState(slab, bottom, top, id));
        }

        for (SimpleSlabModel.Slab block : SimpleSlabModel.woodSlabs) {
            Identifier id = ModelIds.getBlockModelId(block.origin());
            Block slab = block.slab();

            TexturedModel texturedModel = TexturedModel.getCubeAll(Identifier.of(MiddleEarth.MOD_ID, "block/" + Registries.BLOCK.getId(block.origin()).getPath().replaceAll("_wood", "_log")));
            Identifier bottom = Models.SLAB.upload(slab, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);
            Identifier top = Models.SLAB_TOP.upload(slab, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);

            blockStateModelGenerator.blockStateCollector.accept(BlockStateModelGenerator
                    .createSlabBlockState(slab, bottom, top, id));
        }

        for (SimpleSlabModel.Slab block : SimpleSlabModel.strippedSlabs) {
            Identifier id = ModelIds.getBlockModelId(block.origin());
            Block slab = block.slab();

            TexturedModel texturedModel = TexturedModel.getCubeAll(Identifier.of(MiddleEarth.MOD_ID, "block/" + Registries.BLOCK.getId(block.origin()).getPath().replaceAll("_wood", "_log")));
            Identifier bottom = Models.SLAB.upload(slab, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);
            Identifier top = Models.SLAB_TOP.upload(slab, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);

            blockStateModelGenerator.blockStateCollector.accept(BlockStateModelGenerator
                    .createSlabBlockState(slab, bottom, top, id));
        }

        for (SimpleSlabModel.Slab block : SimpleSlabModel.vanillaSlabs) {
            Identifier id = ModelIds.getBlockModelId(block.origin());
            Block slab = block.slab();

            TexturedModel texturedModel = TexturedModel.getCubeAll(Identifier.of("minecraft", "block/" + Registries.BLOCK.getId(block.origin()).getPath().replaceAll("_wood", "_log")));
            Identifier bottom = Models.SLAB.upload(slab, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);
            Identifier top = Models.SLAB_TOP.upload(slab, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);

            blockStateModelGenerator.blockStateCollector.accept(BlockStateModelGenerator
                    .createSlabBlockState(slab, bottom, top, id));
        }

        for (SimpleSlabModel.Slab block : SimpleSlabModel.vanillaWoodSlabs) {
            Identifier id = ModelIds.getBlockModelId(block.origin());
            Block slab = block.slab();

            TexturedModel texturedModel = TexturedModel.getCubeAll(Identifier.of("minecraft", "block/" + Registries.BLOCK.getId(block.origin()).getPath().replaceAll("_wood", "_log")));
            Identifier bottom = Models.SLAB.upload(slab, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);
            Identifier top = Models.SLAB_TOP.upload(slab, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);

            blockStateModelGenerator.blockStateCollector.accept(BlockStateModelGenerator
                    .createSlabBlockState(slab, bottom, top, id));
        }

        for (SimpleSlabModel.Slab block : SimpleSlabModel.vanillaStrippedSlab) {
            Identifier id = ModelIds.getBlockModelId(block.origin());
            Block slab = block.slab();

            TexturedModel texturedModel = TexturedModel.getCubeAll(Identifier.of("minecraft", "block/" + Registries.BLOCK.getId(block.origin()).getPath().replaceAll("_wood", "_log")));
            Identifier bottom = Models.SLAB.upload(slab, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);
            Identifier top = Models.SLAB_TOP.upload(slab, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);

            blockStateModelGenerator.blockStateCollector.accept(BlockStateModelGenerator
                    .createSlabBlockState(slab, bottom, top, id));
        }

        for (SimpleStairModel.Stair block : SimpleStairModel.stairs) {
            TexturedModel texturedModel = TexturedModel.CUBE_ALL.get(block.origin());
            Block stairs = block.stairs();

            Identifier inner = Models.INNER_STAIRS.upload(stairs, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);
            Identifier regular = Models.STAIRS.upload(stairs, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);
            Identifier outer = Models.OUTER_STAIRS.upload(stairs, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);

            blockStateModelGenerator.blockStateCollector.accept(BlockStateModelGenerator
                    .createStairsBlockState(stairs, inner, regular, outer));
        }

        for (SimpleStairModel.Stair block : SimpleStairModel.woodStairs) {
            TexturedModel texturedModel = TexturedModel.getCubeAll(Identifier.of(MiddleEarth.MOD_ID, "block/" + Registries.BLOCK.getId(block.origin()).getPath().replaceAll("_wood", "_log")));
            Block stairs = block.stairs();

            Identifier inner = Models.INNER_STAIRS.upload(stairs, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);
            Identifier regular = Models.STAIRS.upload(stairs, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);
            Identifier outer = Models.OUTER_STAIRS.upload(stairs, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);

            blockStateModelGenerator.blockStateCollector.accept(BlockStateModelGenerator
                    .createStairsBlockState(stairs, inner, regular, outer));
        }

        for (SimpleStairModel.Stair block : SimpleStairModel.strippedStairs) {
            TexturedModel texturedModel = TexturedModel.getCubeAll(Identifier.of(MiddleEarth.MOD_ID, "block/" + Registries.BLOCK.getId(block.origin()).getPath().replaceAll("_wood", "_log")));
            Block stairs = block.stairs();

            Identifier inner = Models.INNER_STAIRS.upload(stairs, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);
            Identifier regular = Models.STAIRS.upload(stairs, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);
            Identifier outer = Models.OUTER_STAIRS.upload(stairs, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);

            blockStateModelGenerator.blockStateCollector.accept(BlockStateModelGenerator
                    .createStairsBlockState(stairs, inner, regular, outer));
        }

        for (SimpleStairModel.Stair block : SimpleStairModel.vanillaWoodStairs) {
            TexturedModel texturedModel = TexturedModel.getCubeAll(Identifier.of("minecraft", "block/" + Registries.BLOCK.getId(block.origin()).getPath().replaceAll("_wood", "_log")));
            Block stairs = block.stairs();

            Identifier inner = Models.INNER_STAIRS.upload(stairs, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);
            Identifier regular = Models.STAIRS.upload(stairs, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);
            Identifier outer = Models.OUTER_STAIRS.upload(stairs, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);

            blockStateModelGenerator.blockStateCollector.accept(BlockStateModelGenerator
                    .createStairsBlockState(stairs, inner, regular, outer));
        }

        for (SimpleStairModel.Stair block : SimpleStairModel.vanillaStrippedStairs) {
            TexturedModel texturedModel = TexturedModel.getCubeAll(Identifier.of("minecraft", "block/" + Registries.BLOCK.getId(block.origin()).getPath().replaceAll("_wood", "_log")));
            Block stairs = block.stairs();

            Identifier inner = Models.INNER_STAIRS.upload(stairs, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);
            Identifier regular = Models.STAIRS.upload(stairs, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);
            Identifier outer = Models.OUTER_STAIRS.upload(stairs, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);

            blockStateModelGenerator.blockStateCollector.accept(BlockStateModelGenerator
                    .createStairsBlockState(stairs, inner, regular, outer));
        }

        for (SimpleStairModel.Stair block : SimpleStairModel.vanillaStairs) {
            TexturedModel texturedModel = TexturedModel.CUBE_ALL.get(block.origin());
            Block stairs = block.stairs();

            Identifier inner = Models.INNER_STAIRS.upload(stairs, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);
            Identifier regular = Models.STAIRS.upload(stairs, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);
            Identifier outer = Models.OUTER_STAIRS.upload(stairs, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);

            blockStateModelGenerator.blockStateCollector.accept(BlockStateModelGenerator
                    .createStairsBlockState(stairs, inner, regular, outer));
        }

        for (SimpleWallModel.Wall block : SimpleWallModel.blocks) {
            TexturedModel texturedModel = TexturedModel.CUBE_ALL.get(block.block());
            Block wall = block.wall();

            Models.WALL_INVENTORY.upload(wall, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);

            Identifier post = Models.TEMPLATE_WALL_POST.upload(wall, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);
            Identifier low = Models.TEMPLATE_WALL_SIDE.upload(wall, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);
            Identifier tall = Models.TEMPLATE_WALL_SIDE_TALL.upload(wall, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);

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

            Identifier post = Models.TEMPLATE_WALL_POST.upload(wall, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);
            Identifier low = Models.TEMPLATE_WALL_SIDE.upload(wall, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);
            Identifier tall = Models.TEMPLATE_WALL_SIDE_TALL.upload(wall, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);

            blockStateModelGenerator.blockStateCollector.accept(BlockStateModelGenerator
                    .createWallBlockState(wall, post, low, tall));
        }

        for (SimpleWallModel.Wall block : SimpleWallModel.strippedWalls) {
            TexturedModel texturedModel = TexturedModel.getCubeAll(Identifier.of(MiddleEarth.MOD_ID, "block/" + Registries.BLOCK.getId(block.block()).getPath().replaceAll("_wood", "_log")));
            Block wall = block.wall();

            Identifier inventory = Models.WALL_INVENTORY.upload(wall, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);

            blockStateModelGenerator.registerParentedItemModel(wall, inventory);

            Identifier post = Models.TEMPLATE_WALL_POST.upload(wall, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);
            Identifier low = Models.TEMPLATE_WALL_SIDE.upload(wall, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);
            Identifier tall = Models.TEMPLATE_WALL_SIDE_TALL.upload(wall, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);

            blockStateModelGenerator.blockStateCollector.accept(BlockStateModelGenerator
                    .createWallBlockState(wall, post, low, tall));
        }

        for (SimpleWallModel.Wall block : SimpleWallModel.vanillaStrippedWalls) {
            TexturedModel texturedModel = TexturedModel.getCubeAll(Identifier.of("minecraft", "block/" + Registries.BLOCK.getId(block.block()).getPath().replaceAll("_wood", "_log")));
            Block wall = block.wall();

            Identifier inventory = Models.WALL_INVENTORY.upload(wall, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);

            blockStateModelGenerator.registerParentedItemModel(wall, inventory);

            Identifier post = Models.TEMPLATE_WALL_POST.upload(wall, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);
            Identifier low = Models.TEMPLATE_WALL_SIDE.upload(wall, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);
            Identifier tall = Models.TEMPLATE_WALL_SIDE_TALL.upload(wall, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);

            blockStateModelGenerator.blockStateCollector.accept(BlockStateModelGenerator
                    .createWallBlockState(wall, post, low, tall));
        }

        for (SimpleWallModel.Wall block : SimpleWallModel.vanillaWoodWalls) {
            TexturedModel texturedModel = TexturedModel.getCubeAll(Identifier.of("minecraft", "block/" + Registries.BLOCK.getId(block.block()).getPath().replaceAll("_wood", "_log")));
            Block wall = block.wall();

            Identifier inventory = Models.WALL_INVENTORY.upload(wall, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);

            blockStateModelGenerator.registerParentedItemModel(wall, inventory);

            Identifier post = Models.TEMPLATE_WALL_POST.upload(wall, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);
            Identifier low = Models.TEMPLATE_WALL_SIDE.upload(wall, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);
            Identifier tall = Models.TEMPLATE_WALL_SIDE_TALL.upload(wall, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);

            blockStateModelGenerator.blockStateCollector.accept(BlockStateModelGenerator
                    .createWallBlockState(wall, post, low, tall));
        }

        for (SimpleFenceModel.Fence block : SimpleFenceModel.blocks) {
            TexturedModel texturedModel = TexturedModel.CUBE_ALL.get(block.block());
            Block fence = block.fence();

            Identifier post = Models.FENCE_POST.upload(fence, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);
            Identifier side = Models.FENCE_SIDE.upload(fence, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);
            Identifier inventory = Models.FENCE_INVENTORY.upload(fence, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);

            blockStateModelGenerator.blockStateCollector.accept(BlockStateModelGenerator
                    .createFenceBlockState(fence, post, side));

            blockStateModelGenerator.registerParentedItemModel(fence, inventory);
        }

        for (SimpleFenceModel.Fence block : SimpleFenceModel.strippedFences) {
            TexturedModel texturedModel = TexturedModel.getCubeAll(Identifier.of(MiddleEarth.MOD_ID, "block/" + Registries.BLOCK.getId(block.block()).getPath().replaceAll("_wood", "_log")));
            Block fence = block.fence();

            Identifier post = Models.FENCE_POST.upload(fence, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);
            Identifier side = Models.FENCE_SIDE.upload(fence, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);
            Identifier inventory = Models.FENCE_INVENTORY.upload(fence, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);

            blockStateModelGenerator.blockStateCollector.accept(BlockStateModelGenerator
                    .createFenceBlockState(fence, post, side));

            blockStateModelGenerator.registerParentedItemModel(fence, inventory);
        }

        for (SimpleFenceModel.Fence block : SimpleFenceModel.vanillaStrippedFences) {
            TexturedModel texturedModel = TexturedModel.getCubeAll(Identifier.of("minecraft", "block/" + Registries.BLOCK.getId(block.block()).getPath().replaceAll("_wood", "_log")));
            Block fence = block.fence();

            Identifier post = Models.FENCE_POST.upload(fence, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);
            Identifier side = Models.FENCE_SIDE.upload(fence, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);
            Identifier inventory = Models.FENCE_INVENTORY.upload(fence, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);

            blockStateModelGenerator.blockStateCollector.accept(BlockStateModelGenerator
                    .createFenceBlockState(fence, post, side));

            blockStateModelGenerator.registerParentedItemModel(fence, inventory);
        }

        for (SimpleFenceModel.Fence block : SimpleFenceModel.vanillaWoodFences) {
            TexturedModel texturedModel = TexturedModel.getCubeAll(Identifier.of("minecraft", "block/" + Registries.BLOCK.getId(block.block()).getPath().replaceAll("_wood", "_log")));
            Block fence = block.fence();

            Identifier post = Models.FENCE_POST.upload(fence, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);
            Identifier side = Models.FENCE_SIDE.upload(fence, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);
            Identifier inventory = Models.FENCE_INVENTORY.upload(fence, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);

            blockStateModelGenerator.blockStateCollector.accept(BlockStateModelGenerator
                    .createFenceBlockState(fence, post, side));

            blockStateModelGenerator.registerParentedItemModel(fence, inventory);
        }

        for (SimpleFenceGateModel.FenceGate block : SimpleFenceGateModel.blocks) {
            TexturedModel texturedModel = TexturedModel.CUBE_ALL.get(block.block());
            Block fenceGate = block.fenceGate();

            Identifier open = Models.TEMPLATE_FENCE_GATE_OPEN.upload(fenceGate, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);
            Identifier closed = Models.TEMPLATE_FENCE_GATE.upload(fenceGate, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);
            Identifier openWall = Models.TEMPLATE_FENCE_GATE_WALL_OPEN.upload(fenceGate, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);
            Identifier closedWall = Models.TEMPLATE_FENCE_GATE_WALL.upload(fenceGate, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);

            blockStateModelGenerator.blockStateCollector.accept(BlockStateModelGenerator
                    .createFenceGateBlockState(fenceGate, open, closed, openWall, closedWall, true));
        }

        for (SimpleButtonModel.Button block : SimpleButtonModel.buttons) {
            TexturedModel texturedModel = TexturedModel.CUBE_ALL.get(block.block());
            Block button = block.button();

            Identifier unpressed = Models.BUTTON.upload(button, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);
            Identifier pressed = Models.BUTTON_PRESSED.upload(button, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);
            Identifier inventory = Models.BUTTON_INVENTORY.upload(button, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);

            blockStateModelGenerator.blockStateCollector.accept(BlockStateModelGenerator
                    .createButtonBlockState(button, unpressed, pressed));

            blockStateModelGenerator.registerParentedItemModel(button, inventory);
        }

        for (SimplePressurePlateModel.PressurePlate block : SimplePressurePlateModel.pressurePlates) {
            TexturedModel texturedModel = TexturedModel.CUBE_ALL.get(block.block());
            Block pressurePlate = block.pressurePlate();

            Identifier up = Models.PRESSURE_PLATE_UP.upload(pressurePlate, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);
            Identifier down = Models.PRESSURE_PLATE_DOWN.upload(pressurePlate, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);

            blockStateModelGenerator.blockStateCollector.accept(BlockStateModelGenerator
                    .createPressurePlateBlockState(pressurePlate, up, down));
        }

        for (SimpleTrapDoorModel.Trapdoor trapdoor : SimpleTrapDoorModel.trapdoors) {
            blockStateModelGenerator.registerOrientableTrapdoor(trapdoor.trapdoor());
        }

        for (SimpleTrapDoorModel.Trapdoor trapdoor : SimpleTrapDoorModel.stoneTrapdoors) {
            registerStoneTrapdoor(blockStateModelGenerator, trapdoor.trapdoor());
        }

        for (SimpleTrapDoorModel.Trapdoor trapdoor : SimpleTrapDoorModel.vanillaStoneTrapdoors) {
            registerVanillaTrapdoor(blockStateModelGenerator, trapdoor.trapdoor());
        }

        for (SimpleLadderModel.Ladder trapdoor : SimpleLadderModel.ladders) {
            registerOrientableTrapdoorLadder(blockStateModelGenerator, trapdoor.ladder());
        }
        for (SimpleLadderModel.Ladder trapdoor : SimpleLadderModel.vanillaLadders) {
            registerOrientableTrapdoorLadder(blockStateModelGenerator, trapdoor.ladder());
        }

        for(SimpleDoorModel.Door door : SimpleDoorModel.doors){
            blockStateModelGenerator.registerDoor(door.door());
        }

        for(Block block : TintableCrossModel.notTintedBlocks) {
            if(block != null) blockStateModelGenerator.registerTintableCross(block, BlockStateModelGenerator.TintType.NOT_TINTED);

        }

        for(Block block : TintableCrossModel.tintedBlocks) {
            blockStateModelGenerator.registerTintableCross(block, BlockStateModelGenerator.TintType.TINTED);
        }

        for(Block block : TintableCrossModel.grassLikeBlocks) {
            blockStateModelGenerator.registerTintableCross(block, BlockStateModelGenerator.TintType.NOT_TINTED);
        }

        for(Block block : SimpleFlowerBedModel.flowerBeds) {
            blockStateModelGenerator.registerFlowerbed(block);
        }

        for(Block block : SimpleDoubleBlockModel.doubleBlocks){
            blockStateModelGenerator.registerDoubleBlock(block, BlockStateModelGenerator.TintType.NOT_TINTED);
        }

        for(Block block : SimpleDoubleBlockModel.doubleBlocksItems){
            registerDoubleBlock(blockStateModelGenerator, block, BlockStateModelGenerator.TintType.NOT_TINTED);
        }

        for(Block block : SimpleMushroomBlockModel.mushroomBlocks){
            blockStateModelGenerator.registerMushroomBlock(block);
        }

        for(SimpleVerticalSlabModel.VerticalSlab verticalSlab : SimpleVerticalSlabModel.verticalSlabs){
            registerVerticalSlabModelBlockStates(blockStateModelGenerator, verticalSlab.verticalSlab(), verticalSlab.block(), Registries.BLOCK.getId(verticalSlab.block()).getPath());
        }

        for(SimpleVerticalSlabModel.VerticalSlab verticalSlab : SimpleVerticalSlabModel.woodVerticalSlabs){
            registerVerticalSlabModelBlockStates(blockStateModelGenerator, verticalSlab.verticalSlab(), verticalSlab.block(), Registries.BLOCK.getId(verticalSlab.block()).getPath().replaceAll("_wood", "_log"));
        }

        for(SimpleVerticalSlabModel.VerticalSlab verticalSlab : SimpleVerticalSlabModel.strippedVerticalSlabs){
            registerVerticalSlabModelBlockStates(blockStateModelGenerator, verticalSlab.verticalSlab(), verticalSlab.block(), Registries.BLOCK.getId(verticalSlab.block()).getPath().replaceAll("_wood", "_log"));
        }

        for (SimpleLayersModel.Layers block : SimpleLayersModel.layers) {
            registerLayers(blockStateModelGenerator, block.layers(), block.origin(), false);
        }

        for (SimpleLayersModel.Layers block : SimpleLayersModel.vanillaLayers) {
            registerLayers(blockStateModelGenerator, block.layers(), block.origin(), true);
        }

        for(SimplePaneModel.Pane pane : SimplePaneModel.panes){
            registerLeadGlassPane(blockStateModelGenerator, pane.glass(), pane.pane());
        }

        for(Block block : SimpleWoodStoolModel.stools){
            registerWoodStoolModelBlockStates(blockStateModelGenerator, block);
        }

        for(Block block : SimpleWoodBenchModel.benchs){
            registerWoodBenchModelBlockStates(blockStateModelGenerator, block);
        }

        for(Block block : SimpleStoneStoolModel.stools){
            registerStoneStoolModelBlockStates(blockStateModelGenerator, block,
                    Identifier.of(MiddleEarth.MOD_ID, "block/" +
                            Registries.BLOCK.getId(block).getPath().replaceAll("_stool", "")));
        }

        for(SimpleStoneStoolModel.VanillaStool stool : SimpleStoneStoolModel.vanillaStools){
            String id = "block/" + Registries.BLOCK.getId(stool.origin()).getPath();
            if(stool.origin() == Blocks.BASALT) id += "_side";
            registerStoneStoolModelBlockStates(blockStateModelGenerator, stool.base(),
                    Identifier.of("minecraft", id));
        }

        for(Block block : SimpleStoneTableModel.tables){
            registerStoneTableModelBlockStates(blockStateModelGenerator, block, Identifier.of(MiddleEarth.MOD_ID, "block/" + Registries.BLOCK.getId(block).getPath().replaceAll("_table", "")));
        }

        for(SimpleStoneTableModel.VanillaTable table : SimpleStoneTableModel.vanillaTables) {
            String id = "block/" + Registries.BLOCK.getId(table.origin()).getPath();
            if(table.origin() == Blocks.BASALT) id += "_side";
            registerStoneTableModelBlockStates(blockStateModelGenerator, table.base(), Identifier.of("minecraft", id));
        }

        for(Block block : SimpleStoneChairModel.chairs){
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
        }

        registerWoodStoolModelBlockStates(blockStateModelGenerator, MushroomBlockSets.MUSHROOM.stool());
        registerWoodStoolModelBlockStates(blockStateModelGenerator, MushroomBlockSets.DARK_MUSHROOM.stool());
        registerWoodStoolModelBlockStates(blockStateModelGenerator, MushroomBlockSets.GRAY_MUSHROOM.stool());

        registerWoodBenchModelBlockStates(blockStateModelGenerator, MushroomBlockSets.MUSHROOM.bench());
        registerWoodBenchModelBlockStates(blockStateModelGenerator, MushroomBlockSets.DARK_MUSHROOM.bench());
        registerWoodBenchModelBlockStates(blockStateModelGenerator, MushroomBlockSets.GRAY_MUSHROOM.bench());


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

        registerWoodChairModelBlockStates(blockStateModelGenerator, MushroomBlockSets.MUSHROOM.chair());
        registerWoodChairModelBlockStates(blockStateModelGenerator, MushroomBlockSets.DARK_MUSHROOM.chair());
        registerWoodChairModelBlockStates(blockStateModelGenerator, MushroomBlockSets.GRAY_MUSHROOM.chair());


        registerWoodTableModelBlockStates(blockStateModelGenerator, MushroomBlockSets.MUSHROOM.table());
        registerWoodTableModelBlockStates(blockStateModelGenerator, MushroomBlockSets.DARK_MUSHROOM.table());
        registerWoodTableModelBlockStates(blockStateModelGenerator, MushroomBlockSets.GRAY_MUSHROOM.table());


        for(SimpleVerticalSlabModel.VerticalSlab verticalSlab : SimpleVerticalSlabModel.vanillaVerticalSlabs) {
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
            registerVanillaVerticalSlabModelBlockStates(blockStateModelGenerator, verticalSlab.verticalSlab(), verticalSlab.block(),  baseTextureId);
        }
        for(SimpleVerticalSlabModel.VerticalSlab verticalSlab : SimpleVerticalSlabModel.vanillaStrippedVerticalSlabs) {
            String id = Registries.BLOCK.getId(verticalSlab.block()).getPath();
            String baseTextureId = id.substring(0, id.lastIndexOf("_")) + "_log";
            baseTextureId = baseTextureId.replaceAll("_wood", "_log");
            registerVanillaVerticalSlabModelBlockStates(blockStateModelGenerator, verticalSlab.verticalSlab(), verticalSlab.block(), baseTextureId);
        }

        SimpleTopWaterModel.topWaterBlocks.forEach( block -> {
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
        registerLargeDoor(blockStateModelGenerator, (LargeDoorBlock) ModDecorativeBlocks.RED_HOBBIT_DOOR, LargeDoor2x2.PART);
        registerLargeDoor(blockStateModelGenerator, (LargeDoorBlock) ModDecorativeBlocks.YELLOW_HOBBIT_DOOR, LargeDoor2x2.PART);
        registerLargeDoor(blockStateModelGenerator, (LargeDoorBlock) ModDecorativeBlocks.LARCH_HOBBIT_DOOR, LargeDoor2x2.PART);
        registerLargeDoor(blockStateModelGenerator, (LargeDoorBlock) ModDecorativeBlocks.SPRUCE_HOBBIT_DOOR, LargeDoor2x2.PART);

        registerLargeDoor(blockStateModelGenerator, (LargeDoorBlock) ModDecorativeBlocks.REINFORCED_SPRUCE_DOOR, LargeDoor4x2.PART);

        registerLargeDoor(blockStateModelGenerator, (LargeDoorBlock) ModDecorativeBlocks.GREAT_GONDORIAN_GATE, LargeDoor10x5.PART);

        registerLargeDoor(blockStateModelGenerator, (LargeDoorBlock) ModDecorativeBlocks.GREAT_DWARVEN_GATE, LargeDoor5x2.PART);
        registerLargeDoor(blockStateModelGenerator, (LargeDoorBlock) ModDecorativeBlocks.VARNISHED_DWARVEN_DOOR, LargeDoor4x2.PART);
        registerThickLargeDoor(blockStateModelGenerator, (LargeDoorBlock) ModDecorativeBlocks.HIDDEN_DWARVEN_DOOR, LargeThickDoor3x2.PART);

        registerLargeDoor(blockStateModelGenerator, (LargeDoorBlock) ModDecorativeBlocks.GREAT_ELVEN_GATE, LargeDoor6x2.PART);

        registerLargeDoor(blockStateModelGenerator, (LargeDoorBlock) ModDecorativeBlocks.GREAT_ORCISH_GATE, LargeDoor10x4.PART);

        blockStateModelGenerator.registerAxisRotated(ModBlocks.GILDED_CHISELED_GREEN_TUFF, TexturedModel.END_FOR_TOP_CUBE_COLUMN, TexturedModel.END_FOR_TOP_CUBE_COLUMN_HORIZONTAL);
        blockStateModelGenerator.registerAxisRotated(ModBlocks.GILDED_CHISELED_GREEN_TUFF_BRICKS, TexturedModel.END_FOR_TOP_CUBE_COLUMN, TexturedModel.END_FOR_TOP_CUBE_COLUMN_HORIZONTAL);
        blockStateModelGenerator.registerAxisRotated(ModBlocks.GILDED_CHISELED_POLISHED_GREEN_TUFF, TexturedModel.END_FOR_TOP_CUBE_COLUMN, TexturedModel.END_FOR_TOP_CUBE_COLUMN_HORIZONTAL);
        blockStateModelGenerator.registerAxisRotated(ModBlocks.GILDED_CHISELED_GREEN_TUFF_TILES, TexturedModel.END_FOR_TOP_CUBE_COLUMN, TexturedModel.END_FOR_TOP_CUBE_COLUMN_HORIZONTAL);
        blockStateModelGenerator.registerAxisRotated(ModBlocks.GILDED_CHISELED_SMOOTH_GREEN_TUFF, TexturedModel.END_FOR_TOP_CUBE_COLUMN, TexturedModel.END_FOR_TOP_CUBE_COLUMN_HORIZONTAL);

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

        registerPaneModel(blockStateModelGenerator, ModBlocks.TREATED_STEEL_BARS);

        registerOrientableTrapdoorLadder(blockStateModelGenerator, ModDecorativeBlocks.ROPE_LADDER);
    }

    public final void registerFanModel(BlockStateModelGenerator blockStateCollector, Block coralFanBlock) {
        TexturedModel texturedModel = TexturedModel.CORAL_FAN.get(coralFanBlock);
        Identifier identifier = texturedModel.upload(coralFanBlock, blockStateCollector.modelCollector);
        blockStateCollector.blockStateCollector.accept(blockStateCollector.createSingletonBlockState(coralFanBlock, identifier));
        blockStateCollector.registerItemModel(coralFanBlock);
    }

    public void registerVanillaVerticalSlabModelBlockStates(BlockStateModelGenerator blockStateModelGenerator, Block block, Block origin, String slabPath){
        Identifier fullBlockId = ModelIds.getBlockModelId(origin);
        Identifier variantId = MEModels.VERTICAL_SLAB.upload(block,
                TextureMap.of(TextureKey.ALL,Identifier.of("minecraft","block/" + slabPath)),
                blockStateModelGenerator.modelCollector);

        Identifier inner = MEModels.VERTICAL_SLAB_INNER.upload(block, TextureMap.of(TextureKey.ALL, Identifier.of("minecraft", "block/" + slabPath)), blockStateModelGenerator.modelCollector);
        Identifier outer = MEModels.VERTICAL_SLAB_OUTER.upload(block, TextureMap.of(TextureKey.ALL, Identifier.of("minecraft", "block/" + slabPath)), blockStateModelGenerator.modelCollector);

        registerVerticalSlab(blockStateModelGenerator, block, fullBlockId, variantId, inner, outer);
    }

    public void registerVerticalSlabModelBlockStates(BlockStateModelGenerator blockStateModelGenerator, Block block, Block origin, String slabPath){
        Identifier fullBlockId = ModelIds.getBlockModelId(origin);
        Identifier variantId = MEModels.VERTICAL_SLAB.upload(block,
                TextureMap.of(TextureKey.ALL, Identifier.of(MiddleEarth.MOD_ID, "block/" + slabPath)),
                blockStateModelGenerator.modelCollector);

        Identifier inner = MEModels.VERTICAL_SLAB_INNER.upload(block, TextureMap.of(TextureKey.ALL, Identifier.of(MiddleEarth.MOD_ID, "block/" + slabPath)), blockStateModelGenerator.modelCollector);
        Identifier outer = MEModels.VERTICAL_SLAB_OUTER.upload(block, TextureMap.of(TextureKey.ALL, Identifier.of(MiddleEarth.MOD_ID, "block/" + slabPath)), blockStateModelGenerator.modelCollector);

        registerVerticalSlab(blockStateModelGenerator, block, fullBlockId, variantId, inner, outer);
    }

    public void registerColumnVerticalSlabModelBlockStates(BlockStateModelGenerator blockStateModelGenerator, Block block, Block origin,
                                                           String modId, String topTexturePath, String bottomTexturePath, String sideTexturePath){
        Identifier fullBlockId = ModelIds.getBlockModelId(origin);
        Identifier sideTexture = Identifier.of(modId, "block/" + sideTexturePath);

        Identifier variantId = MEModels.VERTICAL_COLUMN_SLAB.upload(block, (new TextureMap())
                        .put(TextureKey.TOP, Identifier.of(modId, "block/" + topTexturePath))
                        .put(TextureKey.BOTTOM, Identifier.of(modId, "block/" + bottomTexturePath))
                        .put(TextureKey.SIDE, sideTexture)
                        .put(TextureKey.PARTICLE, sideTexture),
                blockStateModelGenerator.modelCollector);

        Identifier inner = MEModels.VERTICAL_COLUMN_SLAB_INNER.upload(block, (new TextureMap())
                        .put(TextureKey.TOP, Identifier.of(modId, "block/" + topTexturePath))
                        .put(TextureKey.BOTTOM, Identifier.of(modId, "block/" + bottomTexturePath))
                        .put(TextureKey.SIDE, sideTexture)
                        .put(TextureKey.PARTICLE, sideTexture),
                blockStateModelGenerator.modelCollector);

        Identifier outer = MEModels.VERTICAL_COLUMN_SLAB_OUTER.upload(block, (new TextureMap())
                        .put(TextureKey.TOP, Identifier.of(modId, "block/" + topTexturePath))
                        .put(TextureKey.BOTTOM, Identifier.of(modId, "block/" + bottomTexturePath))
                        .put(TextureKey.SIDE, sideTexture)
                        .put(TextureKey.PARTICLE, sideTexture),
                blockStateModelGenerator.modelCollector);
        registerVerticalSlab(blockStateModelGenerator, block, fullBlockId, variantId, inner, outer);
    }

    private void registerVerticalSlab(BlockStateModelGenerator blockStateModelGenerator, Block block, Identifier fullBlock, Identifier regular, Identifier inner, Identifier outer) {
        if(Registries.BLOCK.getId(block).getPath().contains("waxed_") && Registries.BLOCK.getId(block).getPath().contains("copper")){
            fullBlock = Identifier.ofVanilla(fullBlock.getPath().replaceAll("waxed_", ""));
        }
        blockStateModelGenerator.blockStateCollector.accept(VariantsBlockStateSupplier.create(block).coordinate(BlockStateVariantMap
                .create(Properties.HORIZONTAL_FACING, VerticalSlabBlock.DOUBLE, VerticalSlabBlock.SHAPE)
                .register(Direction.EAST, false, VerticalSlabShape.STRAIGHT, BlockStateVariant.create().put(VariantSettings.MODEL, regular).put(VariantSettings.Y, VariantSettings.Rotation.R90).put(VariantSettings.UVLOCK, true))
                .register(Direction.WEST, false, VerticalSlabShape.STRAIGHT, BlockStateVariant.create().put(VariantSettings.MODEL, regular).put(VariantSettings.Y, VariantSettings.Rotation.R270).put(VariantSettings.UVLOCK, true))
                .register(Direction.SOUTH, false, VerticalSlabShape.STRAIGHT, BlockStateVariant.create().put(VariantSettings.MODEL, regular).put(VariantSettings.Y, VariantSettings.Rotation.R180).put(VariantSettings.UVLOCK, true))
                .register(Direction.NORTH, false, VerticalSlabShape.STRAIGHT, BlockStateVariant.create().put(VariantSettings.MODEL, regular).put(VariantSettings.UVLOCK, true))
                .register(Direction.EAST, false, VerticalSlabShape.OUTER_RIGHT, BlockStateVariant.create().put(VariantSettings.MODEL, outer).put(VariantSettings.Y, VariantSettings.Rotation.R180).put(VariantSettings.UVLOCK, true))
                .register(Direction.WEST, false, VerticalSlabShape.OUTER_RIGHT, BlockStateVariant.create().put(VariantSettings.MODEL, outer).put(VariantSettings.UVLOCK, true))
                .register(Direction.SOUTH, false, VerticalSlabShape.OUTER_RIGHT, BlockStateVariant.create().put(VariantSettings.MODEL, outer).put(VariantSettings.Y, VariantSettings.Rotation.R270).put(VariantSettings.UVLOCK, true))
                .register(Direction.NORTH, false, VerticalSlabShape.OUTER_RIGHT, BlockStateVariant.create().put(VariantSettings.MODEL, outer).put(VariantSettings.Y, VariantSettings.Rotation.R90).put(VariantSettings.UVLOCK, true))
                .register(Direction.EAST, false, VerticalSlabShape.OUTER_LEFT, BlockStateVariant.create().put(VariantSettings.MODEL, outer).put(VariantSettings.Y, VariantSettings.Rotation.R90).put(VariantSettings.UVLOCK, true))
                .register(Direction.WEST, false, VerticalSlabShape.OUTER_LEFT, BlockStateVariant.create().put(VariantSettings.MODEL, outer).put(VariantSettings.Y, VariantSettings.Rotation.R270).put(VariantSettings.UVLOCK, true))
                .register(Direction.SOUTH, false, VerticalSlabShape.OUTER_LEFT, BlockStateVariant.create().put(VariantSettings.MODEL, outer).put(VariantSettings.Y, VariantSettings.Rotation.R180).put(VariantSettings.UVLOCK, true))
                .register(Direction.NORTH, false, VerticalSlabShape.OUTER_LEFT, BlockStateVariant.create().put(VariantSettings.MODEL, outer).put(VariantSettings.UVLOCK, true))
                .register(Direction.EAST, false, VerticalSlabShape.INNER_RIGHT, BlockStateVariant.create().put(VariantSettings.MODEL, inner).put(VariantSettings.Y, VariantSettings.Rotation.R180).put(VariantSettings.UVLOCK, true))
                .register(Direction.WEST, false, VerticalSlabShape.INNER_RIGHT, BlockStateVariant.create().put(VariantSettings.MODEL, inner).put(VariantSettings.UVLOCK, true))
                .register(Direction.SOUTH,false,  VerticalSlabShape.INNER_RIGHT, BlockStateVariant.create().put(VariantSettings.MODEL, inner).put(VariantSettings.Y, VariantSettings.Rotation.R270).put(VariantSettings.UVLOCK, true))
                .register(Direction.NORTH, false, VerticalSlabShape.INNER_RIGHT, BlockStateVariant.create().put(VariantSettings.MODEL, inner).put(VariantSettings.Y, VariantSettings.Rotation.R90).put(VariantSettings.UVLOCK, true))
                .register(Direction.EAST, false, VerticalSlabShape.INNER_LEFT, BlockStateVariant.create().put(VariantSettings.MODEL, inner).put(VariantSettings.Y, VariantSettings.Rotation.R90).put(VariantSettings.UVLOCK, true))
                .register(Direction.WEST, false, VerticalSlabShape.INNER_LEFT, BlockStateVariant.create().put(VariantSettings.MODEL, inner).put(VariantSettings.Y, VariantSettings.Rotation.R270).put(VariantSettings.UVLOCK, true))
                .register(Direction.SOUTH, false, VerticalSlabShape.INNER_LEFT, BlockStateVariant.create().put(VariantSettings.MODEL, inner).put(VariantSettings.Y, VariantSettings.Rotation.R180).put(VariantSettings.UVLOCK, true))
                .register(Direction.NORTH, false, VerticalSlabShape.INNER_LEFT, BlockStateVariant.create().put(VariantSettings.MODEL, inner).put(VariantSettings.UVLOCK, true))

                .register(Direction.EAST, true, VerticalSlabShape.STRAIGHT, BlockStateVariant.create().put(VariantSettings.MODEL, fullBlock).put(VariantSettings.UVLOCK, true))
                .register(Direction.WEST, true, VerticalSlabShape.STRAIGHT, BlockStateVariant.create().put(VariantSettings.MODEL, fullBlock).put(VariantSettings.UVLOCK, true))
                .register(Direction.SOUTH, true, VerticalSlabShape.STRAIGHT, BlockStateVariant.create().put(VariantSettings.MODEL, fullBlock).put(VariantSettings.UVLOCK, true))
                .register(Direction.NORTH, true, VerticalSlabShape.STRAIGHT, BlockStateVariant.create().put(VariantSettings.MODEL, fullBlock).put(VariantSettings.UVLOCK, true))
                .register(Direction.EAST, true, VerticalSlabShape.OUTER_RIGHT, BlockStateVariant.create().put(VariantSettings.MODEL, fullBlock).put(VariantSettings.UVLOCK, true))
                .register(Direction.WEST, true, VerticalSlabShape.OUTER_RIGHT, BlockStateVariant.create().put(VariantSettings.MODEL, fullBlock).put(VariantSettings.UVLOCK, true))
                .register(Direction.SOUTH, true, VerticalSlabShape.OUTER_RIGHT, BlockStateVariant.create().put(VariantSettings.MODEL, fullBlock).put(VariantSettings.UVLOCK, true))
                .register(Direction.NORTH, true, VerticalSlabShape.OUTER_RIGHT, BlockStateVariant.create().put(VariantSettings.MODEL, fullBlock).put(VariantSettings.UVLOCK, true))
                .register(Direction.EAST, true, VerticalSlabShape.OUTER_LEFT, BlockStateVariant.create().put(VariantSettings.MODEL, fullBlock).put(VariantSettings.UVLOCK, true))
                .register(Direction.WEST, true, VerticalSlabShape.OUTER_LEFT, BlockStateVariant.create().put(VariantSettings.MODEL, fullBlock).put(VariantSettings.UVLOCK, true))
                .register(Direction.SOUTH, true, VerticalSlabShape.OUTER_LEFT, BlockStateVariant.create().put(VariantSettings.MODEL, fullBlock).put(VariantSettings.UVLOCK, true))
                .register(Direction.NORTH, true, VerticalSlabShape.OUTER_LEFT, BlockStateVariant.create().put(VariantSettings.MODEL, fullBlock).put(VariantSettings.UVLOCK, true))
                .register(Direction.EAST, true, VerticalSlabShape.INNER_RIGHT, BlockStateVariant.create().put(VariantSettings.MODEL, fullBlock).put(VariantSettings.UVLOCK, true))
                .register(Direction.WEST, true, VerticalSlabShape.INNER_RIGHT, BlockStateVariant.create().put(VariantSettings.MODEL, fullBlock).put(VariantSettings.UVLOCK, true))
                .register(Direction.SOUTH,true,  VerticalSlabShape.INNER_RIGHT, BlockStateVariant.create().put(VariantSettings.MODEL, fullBlock).put(VariantSettings.UVLOCK, true))
                .register(Direction.NORTH, true, VerticalSlabShape.INNER_RIGHT, BlockStateVariant.create().put(VariantSettings.MODEL, fullBlock).put(VariantSettings.UVLOCK, true))
                .register(Direction.EAST, true, VerticalSlabShape.INNER_LEFT, BlockStateVariant.create().put(VariantSettings.MODEL, fullBlock).put(VariantSettings.UVLOCK, true))
                .register(Direction.WEST, true, VerticalSlabShape.INNER_LEFT, BlockStateVariant.create().put(VariantSettings.MODEL, fullBlock).put(VariantSettings.UVLOCK, true))
                .register(Direction.SOUTH, true, VerticalSlabShape.INNER_LEFT, BlockStateVariant.create().put(VariantSettings.MODEL, fullBlock).put(VariantSettings.UVLOCK, true))
                .register(Direction.NORTH, true, VerticalSlabShape.INNER_LEFT, BlockStateVariant.create().put(VariantSettings.MODEL, fullBlock).put(VariantSettings.UVLOCK, true))));

    }

    public void registerWoodStoolModelBlockStates(BlockStateModelGenerator blockStateModelGenerator, Block block){
        Identifier texture = Identifier.of(MiddleEarth.MOD_ID, "block/" + Registries.BLOCK.getId(block).getPath().replaceAll("stool", "chair"));
        blockStateModelGenerator.blockStateCollector.accept(VariantsBlockStateSupplier.create(block,
                BlockStateVariant.create().put(VariantSettings.MODEL, MEModels.WOOD_STOOL.upload(block,
                        (new TextureMap()).put(TextureKey.ALL, texture)
                                .put(TextureKey.PARTICLE, texture),
                        blockStateModelGenerator.modelCollector)).put(VariantSettings.UVLOCK, false))
                .coordinate(BlockStateModelGenerator.createNorthDefaultHorizontalRotationStates()));
    }

    public void registerWoodBenchModelBlockStates(BlockStateModelGenerator blockStateModelGenerator, Block block){
        Identifier texture = Identifier.of(MiddleEarth.MOD_ID, "block/" + Registries.BLOCK.getId(block).getPath());
        blockStateModelGenerator.blockStateCollector.accept(VariantsBlockStateSupplier.create(block,
                        BlockStateVariant.create().put(VariantSettings.MODEL, MEModels.WOOD_BENCH.upload(block,
                                (new TextureMap()).put(TextureKey.ALL, texture)
                                        .put(TextureKey.PARTICLE, texture),
                                blockStateModelGenerator.modelCollector)).put(VariantSettings.UVLOCK, false))
                .coordinate(BlockStateModelGenerator.createNorthDefaultHorizontalRotationStates()));
    }

    public void registerWoodTableModelBlockStates(BlockStateModelGenerator blockStateModelGenerator, Block block){
        Identifier texture = Identifier.of(MiddleEarth.MOD_ID, "block/" + Registries.BLOCK.getId(block).getPath());
        blockStateModelGenerator.blockStateCollector.accept(VariantsBlockStateSupplier.create(block,
                        BlockStateVariant.create().put(VariantSettings.MODEL, MEModels.WOOD_TABLE.upload(block,
                                (new TextureMap()).put(TextureKey.ALL, texture)
                                        .put(TextureKey.PARTICLE, texture),
                                blockStateModelGenerator.modelCollector)).put(VariantSettings.UVLOCK, false)));
    }

    public void registerWoodChairModelBlockStates(BlockStateModelGenerator blockStateModelGenerator, Block block){
        Identifier texture = Identifier.of(MiddleEarth.MOD_ID, "block/" + Registries.BLOCK.getId(block).getPath());
        blockStateModelGenerator.blockStateCollector.accept(VariantsBlockStateSupplier.create(block,
                        BlockStateVariant.create().put(VariantSettings.MODEL, MEModels.WOOD_CHAIR.upload(block,
                                (new TextureMap()).put(TextureKey.ALL, texture)
                                        .put(TextureKey.PARTICLE, texture),
                                blockStateModelGenerator.modelCollector)).put(VariantSettings.UVLOCK, false))
                .coordinate(BlockStateModelGenerator.createNorthDefaultHorizontalRotationStates()));
    }

    public void registerStoneStoolModelBlockStates(BlockStateModelGenerator blockStateModelGenerator, Block block, Identifier texture){
        blockStateModelGenerator.blockStateCollector.accept(VariantsBlockStateSupplier.create(block,
                        BlockStateVariant.create().put(VariantSettings.MODEL, MEModels.STONE_STOOL.upload(block,
                                (new TextureMap()).put(TextureKey.ALL, texture)
                                        .put(TextureKey.PARTICLE, texture),
                                blockStateModelGenerator.modelCollector)).put(VariantSettings.UVLOCK, false))
                .coordinate(BlockStateModelGenerator.createNorthDefaultHorizontalRotationStates()));
    }

    public void registerStoneTableModelBlockStates(BlockStateModelGenerator blockStateModelGenerator, Block block, Identifier texture){
        blockStateModelGenerator.blockStateCollector.accept(VariantsBlockStateSupplier.create(block,
                BlockStateVariant.create().put(VariantSettings.MODEL, MEModels.STONE_TABLE.upload(block,
                        (new TextureMap()).put(TextureKey.ALL, texture)
                                .put(TextureKey.PARTICLE, texture),
                        blockStateModelGenerator.modelCollector)).put(VariantSettings.UVLOCK, false)));
    }

    public void registerStoneChairModelBlockStates(BlockStateModelGenerator blockStateModelGenerator, Block block, Identifier texture){
        blockStateModelGenerator.blockStateCollector.accept(VariantsBlockStateSupplier.create(block,
                        BlockStateVariant.create().put(VariantSettings.MODEL, MEModels.STONE_CHAIR.upload(block,
                                (new TextureMap()).put(TextureKey.ALL, texture)
                                        .put(TextureKey.PARTICLE, texture),
                                blockStateModelGenerator.modelCollector)).put(VariantSettings.UVLOCK, false))
                .coordinate(BlockStateModelGenerator.createNorthDefaultHorizontalRotationStates()));
    }

    public final void registerDoubleBlock(BlockStateModelGenerator blockStateModelGenerator, Block doubleBlock, BlockStateModelGenerator.TintType tintType) {
        blockStateModelGenerator.registerItemModel(doubleBlock.asItem());
        Identifier identifier = blockStateModelGenerator.createSubModel(doubleBlock, "_top", tintType.getCrossModel(), TextureMap::cross);
        Identifier identifier2 = blockStateModelGenerator.createSubModel(doubleBlock, "_bottom", tintType.getCrossModel(), TextureMap::cross);
        blockStateModelGenerator.registerDoubleBlock(doubleBlock, identifier, identifier2);
    }

    public final void registerLargeDoor(BlockStateModelGenerator blockStateModelGenerator, LargeDoorBlock largeDoor, IntProperty part){
        var statesMap = BlockStateVariantMap.create(Properties.HORIZONTAL_FACING, Properties.OPEN, Properties.DOOR_HINGE, part);
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

                statesMap.register(Direction.byId(k), false, DoorHinge.LEFT, i, BlockStateVariant.create()
                        .put(VariantSettings.MODEL, Identifier.of(MiddleEarth.MOD_ID,"block/" + Registries.BLOCK.getId(largeDoor).getPath() + "_left_" + i))
                        .put(VariantSettings.UVLOCK, false)
                        .put(VariantSettings.Y, VariantSettings.Rotation.valueOf("R" + rot)));

                statesMap.register(Direction.byId(k), true, DoorHinge.LEFT, i, BlockStateVariant.create()
                        .put(VariantSettings.MODEL,Identifier.of(MiddleEarth.MOD_ID, "block/" + Registries.BLOCK.getId(largeDoor).getPath() + "_left_open_" + i))
                        .put(VariantSettings.UVLOCK, false)
                        .put(VariantSettings.Y, VariantSettings.Rotation.valueOf("R" + rot)));

                statesMap.register(Direction.byId(k), false, DoorHinge.RIGHT, i, BlockStateVariant.create()
                        .put(VariantSettings.MODEL, Identifier.of(MiddleEarth.MOD_ID,"block/" + Registries.BLOCK.getId(largeDoor).getPath() + "_right_" + i))
                        .put(VariantSettings.UVLOCK, false)
                        .put(VariantSettings.Y, VariantSettings.Rotation.valueOf("R" + rot)));

                statesMap.register(Direction.byId(k), true, DoorHinge.RIGHT, i, BlockStateVariant.create()
                        .put(VariantSettings.MODEL, Identifier.of(MiddleEarth.MOD_ID,"block/" + Registries.BLOCK.getId(largeDoor).getPath() + "_right_open_" + i))
                        .put(VariantSettings.UVLOCK, false)
                        .put(VariantSettings.Y, VariantSettings.Rotation.valueOf("R" + rot)));

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
        blockStateModelGenerator.blockStateCollector.accept(VariantsBlockStateSupplier.create(largeDoor)
                .coordinate(statesMap));
    }

    public final void registerThickLargeDoor(BlockStateModelGenerator blockStateModelGenerator, LargeDoorBlock largeDoor, IntProperty part){
        var statesMap = BlockStateVariantMap.create(Properties.HORIZONTAL_FACING, Properties.OPEN, Properties.DOOR_HINGE, part);
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

                statesMap.register(Direction.byId(k), false, DoorHinge.LEFT, i, BlockStateVariant.create()
                        .put(VariantSettings.MODEL, Identifier.of(MiddleEarth.MOD_ID,"block/" + Registries.BLOCK.getId(largeDoor).getPath() + "_left_" + i))
                        .put(VariantSettings.UVLOCK, false)
                        .put(VariantSettings.Y, VariantSettings.Rotation.valueOf("R" + rot)));

                statesMap.register(Direction.byId(k), true, DoorHinge.LEFT, i, BlockStateVariant.create()
                        .put(VariantSettings.MODEL,Identifier.of(MiddleEarth.MOD_ID, "block/" + Registries.BLOCK.getId(largeDoor).getPath() + "_left_open_" + i))
                        .put(VariantSettings.UVLOCK, false)
                        .put(VariantSettings.Y, VariantSettings.Rotation.valueOf("R" + rot)));

                statesMap.register(Direction.byId(k), false, DoorHinge.RIGHT, i, BlockStateVariant.create()
                        .put(VariantSettings.MODEL, Identifier.of(MiddleEarth.MOD_ID,"block/" + Registries.BLOCK.getId(largeDoor).getPath() + "_right_" + i))
                        .put(VariantSettings.UVLOCK, false)
                        .put(VariantSettings.Y, VariantSettings.Rotation.valueOf("R" + rot)));

                statesMap.register(Direction.byId(k), true, DoorHinge.RIGHT, i, BlockStateVariant.create()
                        .put(VariantSettings.MODEL, Identifier.of(MiddleEarth.MOD_ID,"block/" + Registries.BLOCK.getId(largeDoor).getPath() + "_right_open_" + i))
                        .put(VariantSettings.UVLOCK, false)
                        .put(VariantSettings.Y, VariantSettings.Rotation.valueOf("R" + rot)));

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
        blockStateModelGenerator.blockStateCollector.accept(VariantsBlockStateSupplier.create(largeDoor)
                .coordinate(statesMap));
    }


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
        blockStateModelGenerator.blockStateCollector.accept(MultipartBlockStateSupplier.create(glassPane).with(BlockStateVariant.create().put(VariantSettings.MODEL, identifier)).with(When.create().set(Properties.NORTH, true), BlockStateVariant.create().put(VariantSettings.MODEL, identifier2)).with(When.create().set(Properties.EAST, true), BlockStateVariant.create().put(VariantSettings.MODEL, identifier2).put(VariantSettings.Y, VariantSettings.Rotation.R90)).with(When.create().set(Properties.SOUTH, true), BlockStateVariant.create().put(VariantSettings.MODEL, identifier3)).with(When.create().set(Properties.WEST, true), BlockStateVariant.create().put(VariantSettings.MODEL, identifier3).put(VariantSettings.Y, VariantSettings.Rotation.R90)).with(When.create().set(Properties.NORTH, false), BlockStateVariant.create().put(VariantSettings.MODEL, identifier4)).with(When.create().set(Properties.EAST, false), BlockStateVariant.create().put(VariantSettings.MODEL, identifier5)).with(When.create().set(Properties.SOUTH, false), BlockStateVariant.create().put(VariantSettings.MODEL, identifier5).put(VariantSettings.Y, VariantSettings.Rotation.R90)).with(When.create().set(Properties.WEST, false), BlockStateVariant.create().put(VariantSettings.MODEL, identifier4).put(VariantSettings.Y, VariantSettings.Rotation.R270)));
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
        blockStateModelGenerator.blockStateCollector.accept(MultipartBlockStateSupplier.create(pane).with(BlockStateVariant.create().put(VariantSettings.MODEL, identifier)).with(When.create().set(Properties.NORTH, true), BlockStateVariant.create().put(VariantSettings.MODEL, identifier2)).with(When.create().set(Properties.EAST, true), BlockStateVariant.create().put(VariantSettings.MODEL, identifier2).put(VariantSettings.Y, VariantSettings.Rotation.R90)).with(When.create().set(Properties.SOUTH, true), BlockStateVariant.create().put(VariantSettings.MODEL, identifier3)).with(When.create().set(Properties.WEST, true), BlockStateVariant.create().put(VariantSettings.MODEL, identifier3).put(VariantSettings.Y, VariantSettings.Rotation.R90)).with(When.create().set(Properties.NORTH, false), BlockStateVariant.create().put(VariantSettings.MODEL, identifier4)).with(When.create().set(Properties.EAST, false), BlockStateVariant.create().put(VariantSettings.MODEL, identifier5)).with(When.create().set(Properties.SOUTH, false), BlockStateVariant.create().put(VariantSettings.MODEL, identifier5).put(VariantSettings.Y, VariantSettings.Rotation.R90)).with(When.create().set(Properties.WEST, false), BlockStateVariant.create().put(VariantSettings.MODEL, identifier4).put(VariantSettings.Y, VariantSettings.Rotation.R270)));
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
    }

    public void registerVanillaTrapdoor(BlockStateModelGenerator blockStateModelGenerator, Block trapdoorBlock) {
        TextureMap textureMap;
        if(Registries.BLOCK.getId(trapdoorBlock).getPath().contains("basalt")){
            textureMap = TextureMap.texture(Identifier.of("block/" + Registries.BLOCK.getId(trapdoorBlock).getPath().replaceAll("_trapdoor", "_side")));
        } else {
            textureMap = TextureMap.texture(Identifier.of("block/" + Registries.BLOCK.getId(trapdoorBlock).getPath().replaceAll("_trapdoor", "")));
        }
        Identifier identifier = Models.TEMPLATE_TRAPDOOR_TOP.upload(trapdoorBlock, textureMap, blockStateModelGenerator.modelCollector);
        Identifier identifier2 = Models.TEMPLATE_TRAPDOOR_BOTTOM.upload(trapdoorBlock, textureMap, blockStateModelGenerator.modelCollector);
        Identifier identifier3 = Models.TEMPLATE_TRAPDOOR_OPEN.upload(trapdoorBlock, textureMap, blockStateModelGenerator.modelCollector);
        blockStateModelGenerator.blockStateCollector.accept(BlockStateModelGenerator.createTrapdoorBlockState(trapdoorBlock, identifier, identifier2, identifier3));
        blockStateModelGenerator.registerParentedItemModel(trapdoorBlock, identifier2);
    }

    public void registerOrientableTrapdoorLadder(BlockStateModelGenerator blockStateModelGenerator, Block ladderBlock) {
        Identifier texture = Identifier.of(MiddleEarth.MOD_ID,"block/" + Registries.BLOCK.getId(ladderBlock).getPath());
        blockStateModelGenerator.blockStateCollector.accept(VariantsBlockStateSupplier.create(ladderBlock,
                BlockStateVariant.create().put(VariantSettings.MODEL, ModelIds.getBlockModelId(ladderBlock)))
                .coordinate(BlockStateVariantMap.create(Properties.BLOCK_FACE, Properties.HORIZONTAL_FACING)
                        .register(BlockFace.FLOOR, Direction.NORTH, BlockStateVariant.create())
                        .register(BlockFace.FLOOR, Direction.EAST, BlockStateVariant.create().put(VariantSettings.Y, VariantSettings.Rotation.R90))
                        .register(BlockFace.FLOOR, Direction.SOUTH, BlockStateVariant.create().put(VariantSettings.Y, VariantSettings.Rotation.R180))
                        .register(BlockFace.FLOOR, Direction.WEST, BlockStateVariant.create().put(VariantSettings.Y, VariantSettings.Rotation.R270))
                        .register(BlockFace.WALL, Direction.NORTH, BlockStateVariant.create().put(VariantSettings.X, VariantSettings.Rotation.R90))
                        .register(BlockFace.WALL, Direction.EAST, BlockStateVariant.create().put(VariantSettings.X, VariantSettings.Rotation.R90).put(VariantSettings.Y, VariantSettings.Rotation.R90))
                        .register(BlockFace.WALL, Direction.SOUTH, BlockStateVariant.create().put(VariantSettings.X, VariantSettings.Rotation.R90).put(VariantSettings.Y, VariantSettings.Rotation.R180))
                        .register(BlockFace.WALL, Direction.WEST, BlockStateVariant.create().put(VariantSettings.X, VariantSettings.Rotation.R90).put(VariantSettings.Y, VariantSettings.Rotation.R270))
                        .register(BlockFace.CEILING, Direction.SOUTH, BlockStateVariant.create().put(VariantSettings.X, VariantSettings.Rotation.R180))
                        .register(BlockFace.CEILING, Direction.WEST, BlockStateVariant.create().put(VariantSettings.X, VariantSettings.Rotation.R180).put(VariantSettings.Y, VariantSettings.Rotation.R90))
                        .register(BlockFace.CEILING, Direction.NORTH, BlockStateVariant.create().put(VariantSettings.X, VariantSettings.Rotation.R180).put(VariantSettings.Y, VariantSettings.Rotation.R180))
                        .register(BlockFace.CEILING, Direction.EAST, BlockStateVariant.create().put(VariantSettings.X, VariantSettings.Rotation.R180).put(VariantSettings.Y, VariantSettings.Rotation.R270))));

        MEModels.THICK_LADDER.upload(ladderBlock, new TextureMap().put(TextureKey.TEXTURE, texture).put(TextureKey.PARTICLE,texture), blockStateModelGenerator.modelCollector);
    }

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

        for (Item item : SimpleBigItemModel.items) {
            itemModelGenerator.register(item, CustomItemModels.BIG_WEAPON);
            itemModelGenerator.register(item, "_inventory", Models.HANDHELD);
        }

        for (Item item : SimpleSpearModel.items) {
            itemModelGenerator.register(item, "_inventory", Models.HANDHELD);
        }

        for (Item item : SimpleBowItemModel.items) {
            for(int i = 0; i < 3; i++) {
                itemModelGenerator.register(item, "_pulling_" + i, CustomItemModels.BOW);
            }
        }

        for (Item item : SimpleSpawnEggItemModel.items) {
            itemModelGenerator.register(item, CustomItemModels.TEMPLATE_SPAWN_EGG);
        }
        
        // Dyeables needs to be done manually (because of layers)

        SimpleDyeableItemModel.items.forEach(item -> {
            registerDyeableArmor((ArmorItem) item, itemModelGenerator);
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
    }

    public final void registerDyeableArmor(ArmorItem armor, ItemModelGenerator itemModelGenerator) {
        Identifier identifier = ModelIds.getItemModelId(armor);
        Identifier identifier2 = TextureMap.getId(armor);
        Identifier identifier3 = TextureMap.getSubId(armor, "_overlay");
        Models.GENERATED_TWO_LAYERS.upload(identifier, TextureMap.layered(identifier2, identifier3), itemModelGenerator.writer, (id, textures) -> {
                    return createArmorJson(id, textures, armor.getMaterial());
                }
        );
    }

    public final JsonObject createArmorJson(Identifier id, Map<TextureKey, Identifier> textures, RegistryEntry<ArmorMaterial> armorMaterial) {
        return Models.GENERATED_TWO_LAYERS.createJson(id, textures);
    }
}
