package net.jukoz.me.datageneration;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.block.ModBlocks;
import net.jukoz.me.block.ModDecorativeBlocks;
import net.jukoz.me.block.ModNatureBlocks;
import net.jukoz.me.block.MushroomBlockSets;
import net.jukoz.me.block.crop.*;
import net.jukoz.me.datageneration.content.CustomItemModels;
import net.jukoz.me.datageneration.content.MEModels;
import net.jukoz.me.datageneration.content.models.*;
import net.jukoz.me.item.ModEquipmentItems;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.data.client.*;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;

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

        for (SimpleBlockModel.ChiseledBlock block : SimpleBlockModel.chiseledBlocksTopBottom) {
            blockStateModelGenerator.registerAxisRotated(block.base(), TexturedModel.END_FOR_TOP_CUBE_COLUMN, TexturedModel.END_FOR_TOP_CUBE_COLUMN_HORIZONTAL);
        }

        for (Block wood : SimpleBlockModel.woodBlocks) {
            TextureMap textureMap = new TextureMap().put(TextureKey.ALL, new
                    Identifier(MiddleEarth.MOD_ID,"block/" + Registries.BLOCK.getId(wood).getPath().replaceAll("_wood", "_log")));
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

        for (SimpleSlabModel.Slab block : SimpleSlabModel.vanillaSlabs) {
            Identifier id = ModelIds.getBlockModelId(block.origin());
            Block slab = block.slab();

            TexturedModel texturedModel = TexturedModel.CUBE_ALL.get(block.origin());
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

        for (SimpleWallModel.Wall block : SimpleWallModel.strippedWalls) {
            TexturedModel texturedModel = TexturedModel.getCubeAll(new Identifier(MiddleEarth.MOD_ID, "block/" + Registries.BLOCK.getId(block.block()).getPath().replaceAll("_wood", "_log")));
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
            TexturedModel texturedModel = TexturedModel.getCubeAll(new Identifier(MiddleEarth.MOD_ID, "block/" + Registries.BLOCK.getId(block.block()).getPath().replaceAll("_wood", "_log")));
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

            Identifier open = Models.TEMPLATE_CUSTOM_FENCE_GATE_OPEN.upload(fenceGate, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);
            Identifier closed = Models.TEMPLATE_CUSTOM_FENCE_GATE.upload(fenceGate, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);
            Identifier openWall = Models.TEMPLATE_CUSTOM_FENCE_GATE_WALL_OPEN.upload(fenceGate, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);
            Identifier closedWall = Models.TEMPLATE_CUSTOM_FENCE_GATE_WALL.upload(fenceGate, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);

            blockStateModelGenerator.blockStateCollector.accept(BlockStateModelGenerator
                    .createFenceGateBlockState(fenceGate, open, closed, openWall, closedWall, false));
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
            blockStateModelGenerator.registerTrapdoor(trapdoor.trapdoor());
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
            registerVerticalSlabModelBlockStates(blockStateModelGenerator, verticalSlab.verticalSlab(), Registries.BLOCK.getId(verticalSlab.block()).getPath());
        }

        for (SimpleLayersModel.Layers block : SimpleLayersModel.layers) {
            registerLayers(blockStateModelGenerator, block.layers(), block.origin());
        }

        for (SimpleLayersModel.Layers block : SimpleLayersModel.vanillaLayers) {
            registerLayers(blockStateModelGenerator, block.layers(), block.origin());
        }

        for(SimplePaneModel.Pane pane : SimplePaneModel.panes){
            registerLeadGlassPane(blockStateModelGenerator, pane.glass(), pane.pane());
        }

        for(Block block : SimpleWoodStoolModel.stools){
            registerWoodStoolModelBlockStates(blockStateModelGenerator, block,
                    new Identifier(MiddleEarth.MOD_ID, "block/stripped_" + Registries.BLOCK.getId(block).getPath().replaceAll("_stool", "_log")),
                    new Identifier(MiddleEarth.MOD_ID, "block/" + Registries.BLOCK.getId(block).getPath().replaceAll("_stool", "_planks")));
        }


        for(Block block : SimpleStoneStoolModel.stools){
            registerStoneStoolModelBlockStates(blockStateModelGenerator, block,
                    new Identifier(MiddleEarth.MOD_ID, "block/" +
                            Registries.BLOCK.getId(block).getPath().replaceAll("_stool", "")));
        }
        for(SimpleStoneStoolModel.VanillaStool stool : SimpleStoneStoolModel.vanillaStools){
            String id = "block/" + Registries.BLOCK.getId(stool.origin()).getPath();
            if(stool.origin() == Blocks.BASALT) id += "_side";
            registerStoneStoolModelBlockStates(blockStateModelGenerator, stool.base(),
                    new Identifier("minecraft", id));
        }

        for(Block block : SimpleStoneTableModel.tables){
            registerStoneTableModelBlockStates(blockStateModelGenerator, block, new Identifier(MiddleEarth.MOD_ID, "block/" + Registries.BLOCK.getId(block).getPath().replaceAll("_table", "")));
        }
        for(SimpleStoneTableModel.VanillaTable stool : SimpleStoneTableModel.vanillaTables) {
            String id = "block/" + Registries.BLOCK.getId(stool.origin()).getPath();
            if(stool.origin() == Blocks.BASALT) id += "_side";
            registerStoneTableModelBlockStates(blockStateModelGenerator, stool.base(), new Identifier("minecraft", id));
        }

        for(Block block : SimpleStoneChairModel.chairs){
            registerStoneChairModelBlockStates(blockStateModelGenerator, block, new Identifier(MiddleEarth.MOD_ID, "block/" + Registries.BLOCK.getId(block).getPath().replaceAll("_chair", "")));
        }
        for(SimpleStoneChairModel.VanillaChair stool : SimpleStoneChairModel.vanillaChairs){
            String id = "block/" + Registries.BLOCK.getId(stool.origin()).getPath();
            if(stool.origin() == Blocks.BASALT) id += "_side";
            registerStoneChairModelBlockStates(blockStateModelGenerator, stool.base(), new Identifier("minecraft", id));
        }

        for(Block block : SimpleWoodTableModel.tables){
            registerWoodTableModelBlockStates(blockStateModelGenerator, block,
                    new Identifier(MiddleEarth.MOD_ID, "block/stripped_" + Registries.BLOCK.getId(block).getPath().replaceAll("_table", "_log")),
                    new Identifier(MiddleEarth.MOD_ID, "block/" + Registries.BLOCK.getId(block).getPath().replaceAll("_table", "_planks")));
        }

        for(Block block : SimpleWoodChairModel.chairs){
            registerWoodChairModelBlockStates(blockStateModelGenerator, block,
                    new Identifier(MiddleEarth.MOD_ID, "block/stripped_" + Registries.BLOCK.getId(block).getPath().replaceAll("_chair", "_log")),
                    new Identifier(MiddleEarth.MOD_ID, "block/" + Registries.BLOCK.getId(block).getPath().replaceAll("_chair", "_planks")));
        }


        for(Block block : SimpleFanModel.grassLikeFans){
            registerFanModel(blockStateModelGenerator, block);
        }

        registerWoodStoolModelBlockStates(blockStateModelGenerator, MushroomBlockSets.MUSHROOM.stool(),
                new Identifier("minecraft", "block/mushroom_stem"),
                new Identifier(MiddleEarth.MOD_ID, "block/" + Registries.BLOCK.getId(MushroomBlockSets.MUSHROOM.stool()).getPath().replaceAll("_stool", "_planks")));
        registerWoodStoolModelBlockStates(blockStateModelGenerator, MushroomBlockSets.DARK_MUSHROOM.stool(),
                new Identifier(MiddleEarth.MOD_ID, "block/" + Registries.BLOCK.getId(MushroomBlockSets.DARK_MUSHROOM.stool()).getPath().replaceAll("_stool", "_stem")),
                new Identifier(MiddleEarth.MOD_ID, "block/" + Registries.BLOCK.getId(MushroomBlockSets.DARK_MUSHROOM.stool()).getPath().replaceAll("_stool", "_planks")));
        registerWoodStoolModelBlockStates(blockStateModelGenerator, MushroomBlockSets.GRAY_MUSHROOM.stool(),
                new Identifier(MiddleEarth.MOD_ID, "block/" + Registries.BLOCK.getId(MushroomBlockSets.GRAY_MUSHROOM.stool()).getPath().replaceAll("_stool", "_stem")),
                new Identifier(MiddleEarth.MOD_ID, "block/" + Registries.BLOCK.getId(MushroomBlockSets.GRAY_MUSHROOM.stool()).getPath().replaceAll("_stool", "_planks")));


        for(SimpleWoodStoolModel.VanillaStool stool : SimpleWoodStoolModel.vanillaStools) {
            String id = Registries.BLOCK.getId(stool.origin()).getPath();
            String baseTextureId = id.substring(0, id.lastIndexOf("_")) + "_planks";
            baseTextureId = baseTextureId.replaceAll("stripped_", "");
            registerWoodStoolModelBlockStates(blockStateModelGenerator, stool.base(),
                    new Identifier("minecraft", "block/" + id),
                    new Identifier("minecraft", "block/" + baseTextureId));
        }

        for(SimpleWoodTableModel.VanillaTable table : SimpleWoodTableModel.vanillaTables) {
            String id = Registries.BLOCK.getId(table.origin()).getPath();
            String baseTextureId = id.substring(0, id.lastIndexOf("_")) + "_planks";
            baseTextureId = baseTextureId.replaceAll("stripped_", "");// We replace the suffix (_log or _stem) by _planks
            registerWoodTableModelBlockStates(blockStateModelGenerator, table.base(),
                    new Identifier("minecraft", "block/" + id),
                    new Identifier("minecraft", "block/" + baseTextureId));
        }

        for(SimpleWoodChairModel.VanillaChair chair : SimpleWoodChairModel.vanillaChairs) {
            String id = Registries.BLOCK.getId(chair.origin()).getPath();
            String baseTextureId = id.substring(0, id.lastIndexOf("_")) + "_planks";
            baseTextureId = baseTextureId.replaceAll("stripped_", "");
            registerWoodChairModelBlockStates(blockStateModelGenerator, chair.base(),
                    new Identifier("minecraft", "block/" + id),
                    new Identifier("minecraft", "block/" + baseTextureId));
        }

        registerWoodChairModelBlockStates(blockStateModelGenerator, MushroomBlockSets.MUSHROOM.chair(),
                new Identifier("minecraft", "block/mushroom_stem"),
                new Identifier(MiddleEarth.MOD_ID, "block/" + Registries.BLOCK.getId(MushroomBlockSets.MUSHROOM.chair()).getPath().replaceAll("_chair", "_planks")));
        registerWoodChairModelBlockStates(blockStateModelGenerator, MushroomBlockSets.DARK_MUSHROOM.chair(),
                new Identifier(MiddleEarth.MOD_ID, "block/" + Registries.BLOCK.getId(MushroomBlockSets.DARK_MUSHROOM.chair()).getPath().replaceAll("_chair", "_stem")),
                new Identifier(MiddleEarth.MOD_ID, "block/" + Registries.BLOCK.getId(MushroomBlockSets.DARK_MUSHROOM.chair()).getPath().replaceAll("_chair", "_planks")));
        registerWoodChairModelBlockStates(blockStateModelGenerator, MushroomBlockSets.GRAY_MUSHROOM.chair(),
                new Identifier(MiddleEarth.MOD_ID, "block/" + Registries.BLOCK.getId(MushroomBlockSets.GRAY_MUSHROOM.chair()).getPath().replaceAll("_chair", "_stem")),
                new Identifier(MiddleEarth.MOD_ID, "block/" + Registries.BLOCK.getId(MushroomBlockSets.GRAY_MUSHROOM.chair()).getPath().replaceAll("_chair", "_planks")));


        registerWoodTableModelBlockStates(blockStateModelGenerator, MushroomBlockSets.MUSHROOM.table(),
                new Identifier("minecraft", "block/mushroom_stem"),
                new Identifier(MiddleEarth.MOD_ID, "block/" + Registries.BLOCK.getId(MushroomBlockSets.MUSHROOM.table()).getPath().replaceAll("_table", "_planks")));
        registerWoodTableModelBlockStates(blockStateModelGenerator, MushroomBlockSets.DARK_MUSHROOM.table(),
                new Identifier(MiddleEarth.MOD_ID, "block/" + Registries.BLOCK.getId(MushroomBlockSets.DARK_MUSHROOM.table()).getPath().replaceAll("_table", "_stem")),
                new Identifier(MiddleEarth.MOD_ID, "block/" + Registries.BLOCK.getId(MushroomBlockSets.DARK_MUSHROOM.table()).getPath().replaceAll("_table", "_planks")));
        registerWoodTableModelBlockStates(blockStateModelGenerator, MushroomBlockSets.GRAY_MUSHROOM.table(),
                new Identifier(MiddleEarth.MOD_ID, "block/" + Registries.BLOCK.getId(MushroomBlockSets.GRAY_MUSHROOM.table()).getPath().replaceAll("_table", "_stem")),
                new Identifier(MiddleEarth.MOD_ID, "block/" + Registries.BLOCK.getId(MushroomBlockSets.GRAY_MUSHROOM.table()).getPath().replaceAll("_table", "_planks")));


        for(SimpleVerticalSlabModel.VerticalSlab verticalSlab : SimpleVerticalSlabModel.vanillaVerticalSlabs) {
            String id = String.valueOf(Registries.BLOCK.getId(verticalSlab.block()));
            id = id.substring(id.lastIndexOf(":") + 1);

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
            registerVanillaVerticalSlabModelBlockStates(blockStateModelGenerator, verticalSlab.verticalSlab(), id);
        }

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
    }

    public final void registerFanModel(BlockStateModelGenerator blockStateCollector, Block coralFanBlock) {
        TexturedModel texturedModel = TexturedModel.CORAL_FAN.get(coralFanBlock);
        Identifier identifier = texturedModel.upload(coralFanBlock, blockStateCollector.modelCollector);
        blockStateCollector.blockStateCollector.accept(blockStateCollector.createSingletonBlockState(coralFanBlock, identifier));
        blockStateCollector.registerItemModel(coralFanBlock);
    }

    public void registerVanillaVerticalSlabModelBlockStates(BlockStateModelGenerator blockStateModelGenerator, Block block, String texture){
        blockStateModelGenerator.blockStateCollector.accept(VariantsBlockStateSupplier.create(block,
                BlockStateVariant.create().put(VariantSettings.MODEL, MEModels.VERTICAL_SLAB.upload(block,
                        TextureMap.of(TextureKey.ALL,new Identifier("minecraft","block/" + texture)),
                        blockStateModelGenerator.modelCollector)).put(VariantSettings.UVLOCK, true)).coordinate(BlockStateModelGenerator.createNorthDefaultHorizontalRotationStates()));
    }

    public void registerVerticalSlabModelBlockStates(BlockStateModelGenerator blockStateModelGenerator, Block block , String texture){
        blockStateModelGenerator.blockStateCollector.accept(VariantsBlockStateSupplier.create(block,
                BlockStateVariant.create().put(VariantSettings.MODEL, MEModels.VERTICAL_SLAB.upload(block,
                        TextureMap.of(TextureKey.ALL, new Identifier(MiddleEarth.MOD_ID, "block/" + texture)),
                        blockStateModelGenerator.modelCollector)).put(VariantSettings.UVLOCK, true)).coordinate(BlockStateModelGenerator.createNorthDefaultHorizontalRotationStates()));
    }

    public void registerWoodStoolModelBlockStates(BlockStateModelGenerator blockStateModelGenerator, Block block, Identifier logTexture, Identifier plankTexture){
        blockStateModelGenerator.blockStateCollector.accept(VariantsBlockStateSupplier.create(block,
                BlockStateVariant.create().put(VariantSettings.MODEL, MEModels.WOOD_STOOL.upload(block,
                        (new TextureMap()).put(TextureKey.TOP, plankTexture)
                                .put(TextureKey.SIDE, logTexture)
                                .put(TextureKey.PARTICLE, logTexture),
                        blockStateModelGenerator.modelCollector)).put(VariantSettings.UVLOCK, false))
                .coordinate(BlockStateModelGenerator.createNorthDefaultHorizontalRotationStates()));
    }

    public void registerWoodTableModelBlockStates(BlockStateModelGenerator blockStateModelGenerator, Block block, Identifier logTexture, Identifier plankTexture){
        blockStateModelGenerator.blockStateCollector.accept(VariantsBlockStateSupplier.create(block,
                        BlockStateVariant.create().put(VariantSettings.MODEL, MEModels.WOOD_TABLE.upload(block,
                                (new TextureMap()).put(TextureKey.TOP, plankTexture)
                                        .put(TextureKey.SIDE, logTexture)
                                        .put(TextureKey.PARTICLE, logTexture),
                                blockStateModelGenerator.modelCollector)).put(VariantSettings.UVLOCK, false)));
    }

    public void registerWoodChairModelBlockStates(BlockStateModelGenerator blockStateModelGenerator, Block block, Identifier logTexture, Identifier plankTexture){
        blockStateModelGenerator.blockStateCollector.accept(VariantsBlockStateSupplier.create(block,
                        BlockStateVariant.create().put(VariantSettings.MODEL, MEModels.WOOD_CHAIR.upload(block,
                                (new TextureMap()).put(TextureKey.TOP, plankTexture)
                                        .put(TextureKey.SIDE, logTexture)
                                        .put(TextureKey.PARTICLE, logTexture),
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

    private void registerLayers(BlockStateModelGenerator blockStateModelGenerator, Block layers, Block origin) {
        TextureMap textureMap = TextureMap.all(new Identifier("minecraft", Registries.BLOCK.getId(layers).getPath().replaceAll("_layers", "")));
        blockStateModelGenerator.blockStateCollector.accept(VariantsBlockStateSupplier.create(layers).coordinate(BlockStateVariantMap.create(Properties.LAYERS).register((height) -> {
            BlockStateVariant var10000 = BlockStateVariant.create();
            VariantSetting var10001 = VariantSettings.MODEL;
            Identifier var2;
            if (height < 8) {
                Block var10002 = layers;
                int var10003 = height;
                var2 = ModelIds.getBlockSubModelId(var10002, "_height" + var10003 * 2);
            } else {
                var2 = new Identifier("minecraft", "block/" + Registries.BLOCK.getId(origin).getPath());
            }
            return var10000.put(var10001, var2);
        })));
        blockStateModelGenerator.registerParentedItemModel(layers, ModelIds.getBlockSubModelId(layers, "_height2"));
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        for (SimpleWallModel.Wall wall : SimpleWallModel.blocks) {
            Identifier id = Registries.BLOCK.getId(wall.wall());
            itemModelGenerator.register(wall.wall().asItem(), new Model(Optional.of(id.withPath("block/" + id.getPath() + "_inventory")), Optional.empty()));
        }

        for (Item item : SimpleItemModel.items) {
            itemModelGenerator.register(item, Models.GENERATED);
        }

        for (Item item : SimpleHandheldItemModel.items) {
            itemModelGenerator.register(item, Models.HANDHELD);
        }

        for (Item item : SimpleBigItemModel.items) {
            itemModelGenerator.register(item, CustomItemModels.BIG_WEAPON);
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
        itemModelGenerator.registerArmor(((ArmorItem) ModEquipmentItems.FUR_CLOAK));
        itemModelGenerator.registerArmor(((ArmorItem) ModEquipmentItems.FUR_CLOAK_HOOD));
        
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
}
