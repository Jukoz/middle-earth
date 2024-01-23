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

        for (SimpleButtonModel.Button block : SimpleButtonModel.blocks) {
            TexturedModel texturedModel = TexturedModel.CUBE_ALL.get(block.block());
            Block button = block.button();

            Identifier unpressed = Models.BUTTON.upload(button, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);
            Identifier pressed = Models.BUTTON_PRESSED.upload(button, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);
            Identifier inventory = Models.BUTTON_INVENTORY.upload(button, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);

            blockStateModelGenerator.blockStateCollector.accept(BlockStateModelGenerator
                    .createButtonBlockState(button, unpressed, pressed));

            blockStateModelGenerator.registerParentedItemModel(button, inventory);
        }

        for (SimplePressurePlateModel.PressurePlate block : SimplePressurePlateModel.blocks) {
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
                    new Identifier(MiddleEarth.MOD_ID, "block/" + Registries.BLOCK.getId(block).getPath().replaceAll("_stool", "_log")),
                    new Identifier(MiddleEarth.MOD_ID, "block/" + Registries.BLOCK.getId(block).getPath().replaceAll("_stool", "_planks")));
        }

        for(Block block : SimpleWoodTableModel.tables){
            registerWoodTableModelBlockStates(blockStateModelGenerator, block,
                    new Identifier(MiddleEarth.MOD_ID, "block/" + Registries.BLOCK.getId(block).getPath().replaceAll("_table", "_log")),
                    new Identifier(MiddleEarth.MOD_ID, "block/" + Registries.BLOCK.getId(block).getPath().replaceAll("_table", "_planks")));
        }

        for(Block block : SimpleWoodChairModel.chairs){
            registerWoodChairModelBlockStates(blockStateModelGenerator, block,
                    new Identifier(MiddleEarth.MOD_ID, "block/" + Registries.BLOCK.getId(block).getPath().replaceAll("_chair", "_log")),
                    new Identifier(MiddleEarth.MOD_ID, "block/" + Registries.BLOCK.getId(block).getPath().replaceAll("_chair", "_planks")));
        }

        for(Block block : SimpleStoneStoolModel.stools){
            registerStoneStoolModelBlockStates(blockStateModelGenerator, block, new Identifier(MiddleEarth.MOD_ID, "block/" + Registries.BLOCK.getId(block).getPath().replaceAll("_stool", "")));
        }

        for(Block block : SimpleStoneTableModel.tables){
            registerStoneTableModelBlockStates(blockStateModelGenerator, block, new Identifier(MiddleEarth.MOD_ID, "block/" + Registries.BLOCK.getId(block).getPath().replaceAll("_table", "")));
        }

        for(Block block : SimpleStoneChairModel.chairs){
            registerStoneChairModelBlockStates(blockStateModelGenerator, block, new Identifier(MiddleEarth.MOD_ID, "block/" + Registries.BLOCK.getId(block).getPath().replaceAll("_chair", "")));
        }

        for(Block block : SimpleFanModel.grassLikeFans){
            registerFanModel(blockStateModelGenerator, block);
        }

        registerStoneStoolModelBlockStates(blockStateModelGenerator, ModDecorativeBlocks.STONE_STOOL,
                new Identifier("minecraft", "block/" + Registries.BLOCK.getId(ModDecorativeBlocks.STONE_STOOL).getPath().replaceAll("_stool", "")));
        registerStoneStoolModelBlockStates(blockStateModelGenerator, ModDecorativeBlocks.CALCITE_STOOL,
                new Identifier("minecraft", "block/" + Registries.BLOCK.getId(ModDecorativeBlocks.CALCITE_STOOL).getPath().replaceAll("_stool", "")));
        registerStoneStoolModelBlockStates(blockStateModelGenerator, ModDecorativeBlocks.ANDESITE_STOOL,
                new Identifier("minecraft", "block/" + Registries.BLOCK.getId(ModDecorativeBlocks.ANDESITE_STOOL).getPath().replaceAll("_stool", "")));
        registerStoneStoolModelBlockStates(blockStateModelGenerator, ModDecorativeBlocks.GRANITE_STOOL,
                new Identifier("minecraft", "block/" + Registries.BLOCK.getId(ModDecorativeBlocks.GRANITE_STOOL).getPath().replaceAll("_stool", "")));
        registerStoneStoolModelBlockStates(blockStateModelGenerator, ModDecorativeBlocks.DIORITE_STOOL,
                new Identifier("minecraft", "block/" + Registries.BLOCK.getId(ModDecorativeBlocks.DIORITE_STOOL).getPath().replaceAll("_stool", "")));
        registerStoneStoolModelBlockStates(blockStateModelGenerator, ModDecorativeBlocks.DEEPSLATE_STOOL,
                new Identifier("minecraft", "block/" + Registries.BLOCK.getId(ModDecorativeBlocks.DEEPSLATE_STOOL).getPath().replaceAll("_stool", "")));
        registerStoneStoolModelBlockStates(blockStateModelGenerator, ModDecorativeBlocks.BLACKSTONE_STOOL,
                new Identifier("minecraft", "block/" + Registries.BLOCK.getId(ModDecorativeBlocks.BLACKSTONE_STOOL).getPath().replaceAll("_stool", "")));
        registerStoneStoolModelBlockStates(blockStateModelGenerator, ModDecorativeBlocks.BASALT_STOOL,
                new Identifier("minecraft", "block/" + Registries.BLOCK.getId(ModDecorativeBlocks.BASALT_STOOL).getPath().replaceAll("_stool", "_side")));
        registerStoneStoolModelBlockStates(blockStateModelGenerator, ModDecorativeBlocks.TUFF_STOOL,
                new Identifier("minecraft", "block/" + Registries.BLOCK.getId(ModDecorativeBlocks.TUFF_STOOL).getPath().replaceAll("_stool", "")));

        registerStoneTableModelBlockStates(blockStateModelGenerator, ModDecorativeBlocks.STONE_TABLE,
                new Identifier("minecraft", "block/" + Registries.BLOCK.getId(ModDecorativeBlocks.STONE_TABLE).getPath().replaceAll("_table", "")));
        registerStoneTableModelBlockStates(blockStateModelGenerator, ModDecorativeBlocks.CALCITE_TABLE,
                new Identifier("minecraft", "block/" + Registries.BLOCK.getId(ModDecorativeBlocks.CALCITE_TABLE).getPath().replaceAll("_table", "")));
        registerStoneTableModelBlockStates(blockStateModelGenerator, ModDecorativeBlocks.ANDESITE_TABLE,
                new Identifier("minecraft", "block/" + Registries.BLOCK.getId(ModDecorativeBlocks.ANDESITE_TABLE).getPath().replaceAll("_table", "")));
        registerStoneTableModelBlockStates(blockStateModelGenerator, ModDecorativeBlocks.GRANITE_TABLE,
                new Identifier("minecraft", "block/" + Registries.BLOCK.getId(ModDecorativeBlocks.GRANITE_TABLE).getPath().replaceAll("_table", "")));
        registerStoneTableModelBlockStates(blockStateModelGenerator, ModDecorativeBlocks.DIORITE_TABLE,
                new Identifier("minecraft", "block/" + Registries.BLOCK.getId(ModDecorativeBlocks.DIORITE_TABLE).getPath().replaceAll("_table", "")));
        registerStoneTableModelBlockStates(blockStateModelGenerator, ModDecorativeBlocks.DEEPSLATE_TABLE,
                new Identifier("minecraft", "block/" + Registries.BLOCK.getId(ModDecorativeBlocks.DEEPSLATE_TABLE).getPath().replaceAll("_table", "")));
        registerStoneTableModelBlockStates(blockStateModelGenerator, ModDecorativeBlocks.BLACKSTONE_TABLE,
                new Identifier("minecraft", "block/" + Registries.BLOCK.getId(ModDecorativeBlocks.BLACKSTONE_TABLE).getPath().replaceAll("_table", "")));
        registerStoneTableModelBlockStates(blockStateModelGenerator, ModDecorativeBlocks.BASALT_TABLE,
                new Identifier("minecraft", "block/" + Registries.BLOCK.getId(ModDecorativeBlocks.BASALT_TABLE).getPath().replaceAll("_table", "_side")));
        registerStoneTableModelBlockStates(blockStateModelGenerator, ModDecorativeBlocks.TUFF_TABLE,
                new Identifier("minecraft", "block/" + Registries.BLOCK.getId(ModDecorativeBlocks.TUFF_TABLE).getPath().replaceAll("_table", "")));
        
        registerStoneChairModelBlockStates(blockStateModelGenerator, ModDecorativeBlocks.STONE_CHAIR,
                new Identifier("minecraft", "block/" + Registries.BLOCK.getId(ModDecorativeBlocks.STONE_CHAIR).getPath().replaceAll("_chair", "")));
        registerStoneChairModelBlockStates(blockStateModelGenerator, ModDecorativeBlocks.CALCITE_CHAIR,
                new Identifier("minecraft", "block/" + Registries.BLOCK.getId(ModDecorativeBlocks.CALCITE_CHAIR).getPath().replaceAll("_chair", "")));
        registerStoneChairModelBlockStates(blockStateModelGenerator, ModDecorativeBlocks.ANDESITE_CHAIR,
                new Identifier("minecraft", "block/" + Registries.BLOCK.getId(ModDecorativeBlocks.ANDESITE_CHAIR).getPath().replaceAll("_chair", "")));
        registerStoneChairModelBlockStates(blockStateModelGenerator, ModDecorativeBlocks.GRANITE_CHAIR,
                new Identifier("minecraft", "block/" + Registries.BLOCK.getId(ModDecorativeBlocks.GRANITE_CHAIR).getPath().replaceAll("_chair", "")));
        registerStoneChairModelBlockStates(blockStateModelGenerator, ModDecorativeBlocks.DIORITE_CHAIR,
                new Identifier("minecraft", "block/" + Registries.BLOCK.getId(ModDecorativeBlocks.DIORITE_CHAIR).getPath().replaceAll("_chair", "")));
        registerStoneChairModelBlockStates(blockStateModelGenerator, ModDecorativeBlocks.DEEPSLATE_CHAIR,
                new Identifier("minecraft", "block/" + Registries.BLOCK.getId(ModDecorativeBlocks.DEEPSLATE_CHAIR).getPath().replaceAll("_chair", "")));
        registerStoneChairModelBlockStates(blockStateModelGenerator, ModDecorativeBlocks.BLACKSTONE_CHAIR,
                new Identifier("minecraft", "block/" + Registries.BLOCK.getId(ModDecorativeBlocks.BLACKSTONE_CHAIR).getPath().replaceAll("_chair", "")));
        registerStoneChairModelBlockStates(blockStateModelGenerator, ModDecorativeBlocks.BASALT_CHAIR,
                new Identifier("minecraft", "block/" + Registries.BLOCK.getId(ModDecorativeBlocks.BASALT_CHAIR).getPath().replaceAll("_chair", "_side")));
        registerStoneChairModelBlockStates(blockStateModelGenerator, ModDecorativeBlocks.TUFF_CHAIR,
                new Identifier("minecraft", "block/" + Registries.BLOCK.getId(ModDecorativeBlocks.TUFF_CHAIR).getPath().replaceAll("_chair", "")));

        registerWoodStoolModelBlockStates(blockStateModelGenerator, ModDecorativeBlocks.OAK_STOOL,
                new Identifier("minecraft", "block/" + Registries.BLOCK.getId(ModDecorativeBlocks.OAK_STOOL).getPath().replaceAll("_stool", "_log")),
                new Identifier("minecraft", "block/" + Registries.BLOCK.getId(ModDecorativeBlocks.OAK_STOOL).getPath().replaceAll("_stool", "_planks")));
        registerWoodStoolModelBlockStates(blockStateModelGenerator, ModDecorativeBlocks.SPRUCE_STOOL,
                new Identifier("minecraft", "block/" + Registries.BLOCK.getId(ModDecorativeBlocks.SPRUCE_STOOL).getPath().replaceAll("_stool", "_log")),
                new Identifier("minecraft", "block/" + Registries.BLOCK.getId(ModDecorativeBlocks.SPRUCE_STOOL).getPath().replaceAll("_stool", "_planks")));
        registerWoodStoolModelBlockStates(blockStateModelGenerator, ModDecorativeBlocks.BIRCH_STOOL,
                new Identifier("minecraft", "block/" + Registries.BLOCK.getId(ModDecorativeBlocks.BIRCH_STOOL).getPath().replaceAll("_stool", "_log")),
                new Identifier("minecraft", "block/" + Registries.BLOCK.getId(ModDecorativeBlocks.BIRCH_STOOL).getPath().replaceAll("_stool", "_planks")));
        registerWoodStoolModelBlockStates(blockStateModelGenerator, ModDecorativeBlocks.JUNGLE_STOOL,
                new Identifier("minecraft", "block/" + Registries.BLOCK.getId(ModDecorativeBlocks.JUNGLE_STOOL).getPath().replaceAll("_stool", "_log")),
                new Identifier("minecraft", "block/" + Registries.BLOCK.getId(ModDecorativeBlocks.JUNGLE_STOOL).getPath().replaceAll("_stool", "_planks")));
        registerWoodStoolModelBlockStates(blockStateModelGenerator, ModDecorativeBlocks.ACACIA_STOOL,
                new Identifier("minecraft", "block/" + Registries.BLOCK.getId(ModDecorativeBlocks.ACACIA_STOOL).getPath().replaceAll("_stool", "_log")),
                new Identifier("minecraft", "block/" + Registries.BLOCK.getId(ModDecorativeBlocks.ACACIA_STOOL).getPath().replaceAll("_stool", "_planks")));
        registerWoodStoolModelBlockStates(blockStateModelGenerator, ModDecorativeBlocks.DARK_OAK_STOOL,
                new Identifier("minecraft", "block/" + Registries.BLOCK.getId(ModDecorativeBlocks.DARK_OAK_STOOL).getPath().replaceAll("_stool", "_log")),
                new Identifier("minecraft", "block/" + Registries.BLOCK.getId(ModDecorativeBlocks.DARK_OAK_STOOL).getPath().replaceAll("_stool", "_planks")));
        registerWoodStoolModelBlockStates(blockStateModelGenerator, ModDecorativeBlocks.MANGROVE_STOOL,
                new Identifier("minecraft", "block/" + Registries.BLOCK.getId(ModDecorativeBlocks.MANGROVE_STOOL).getPath().replaceAll("_stool", "_log")),
                new Identifier("minecraft", "block/" + Registries.BLOCK.getId(ModDecorativeBlocks.MANGROVE_STOOL).getPath().replaceAll("_stool", "_planks")));
        registerWoodStoolModelBlockStates(blockStateModelGenerator, ModDecorativeBlocks.CHERRY_STOOL,
                new Identifier("minecraft", "block/" + Registries.BLOCK.getId(ModDecorativeBlocks.CHERRY_STOOL).getPath().replaceAll("_stool", "_log")),
                new Identifier("minecraft", "block/" + Registries.BLOCK.getId(ModDecorativeBlocks.CHERRY_STOOL).getPath().replaceAll("_stool", "_planks")));
        registerWoodStoolModelBlockStates(blockStateModelGenerator, ModDecorativeBlocks.BAMBOO_STOOL,
                new Identifier("minecraft", "block/" + Registries.BLOCK.getId(ModDecorativeBlocks.BAMBOO_STOOL).getPath().replaceAll("_stool", "_planks")),
                new Identifier("minecraft", "block/" + Registries.BLOCK.getId(ModDecorativeBlocks.BAMBOO_STOOL).getPath().replaceAll("_stool", "_planks")));
        registerWoodStoolModelBlockStates(blockStateModelGenerator, ModDecorativeBlocks.CRIMSON_STOOL,
                new Identifier("minecraft", "block/" + Registries.BLOCK.getId(ModDecorativeBlocks.CRIMSON_STOOL).getPath().replaceAll("_stool", "_stem")),
                new Identifier("minecraft", "block/" + Registries.BLOCK.getId(ModDecorativeBlocks.CRIMSON_STOOL).getPath().replaceAll("_stool", "_planks")));
        registerWoodStoolModelBlockStates(blockStateModelGenerator, ModDecorativeBlocks.WARPED_STOOL,
                new Identifier("minecraft", "block/" + Registries.BLOCK.getId(ModDecorativeBlocks.WARPED_STOOL).getPath().replaceAll("_stool", "_stem")),
                new Identifier("minecraft", "block/" + Registries.BLOCK.getId(ModDecorativeBlocks.WARPED_STOOL).getPath().replaceAll("_stool", "_planks")));

        registerWoodStoolModelBlockStates(blockStateModelGenerator, MushroomBlockSets.MUSHROOM.stool(),
                new Identifier("minecraft", "block/mushroom_stem"),
                new Identifier(MiddleEarth.MOD_ID, "block/" + Registries.BLOCK.getId(MushroomBlockSets.MUSHROOM.stool()).getPath().replaceAll("_stool", "_planks")));
        registerWoodStoolModelBlockStates(blockStateModelGenerator, MushroomBlockSets.DARK_MUSHROOM.stool(),
                new Identifier(MiddleEarth.MOD_ID, "block/" + Registries.BLOCK.getId(MushroomBlockSets.DARK_MUSHROOM.stool()).getPath().replaceAll("_stool", "_stem")),
                new Identifier(MiddleEarth.MOD_ID, "block/" + Registries.BLOCK.getId(MushroomBlockSets.DARK_MUSHROOM.stool()).getPath().replaceAll("_stool", "_planks")));
        registerWoodStoolModelBlockStates(blockStateModelGenerator, MushroomBlockSets.GRAY_MUSHROOM.stool(),
                new Identifier(MiddleEarth.MOD_ID, "block/" + Registries.BLOCK.getId(MushroomBlockSets.GRAY_MUSHROOM.stool()).getPath().replaceAll("_stool", "_stem")),
                new Identifier(MiddleEarth.MOD_ID, "block/" + Registries.BLOCK.getId(MushroomBlockSets.GRAY_MUSHROOM.stool()).getPath().replaceAll("_stool", "_planks")));

        registerWoodTableModelBlockStates(blockStateModelGenerator, ModDecorativeBlocks.OAK_TABLE,
                new Identifier("minecraft", "block/" + Registries.BLOCK.getId(ModDecorativeBlocks.OAK_TABLE).getPath().replaceAll("_table", "_log")),
                new Identifier("minecraft", "block/" + Registries.BLOCK.getId(ModDecorativeBlocks.OAK_TABLE).getPath().replaceAll("_table", "_planks")));
        registerWoodTableModelBlockStates(blockStateModelGenerator, ModDecorativeBlocks.SPRUCE_TABLE,
                new Identifier("minecraft", "block/" + Registries.BLOCK.getId(ModDecorativeBlocks.SPRUCE_TABLE).getPath().replaceAll("_table", "_log")),
                new Identifier("minecraft", "block/" + Registries.BLOCK.getId(ModDecorativeBlocks.SPRUCE_TABLE).getPath().replaceAll("_table", "_planks")));
        registerWoodTableModelBlockStates(blockStateModelGenerator, ModDecorativeBlocks.BIRCH_TABLE,
                new Identifier("minecraft", "block/" + Registries.BLOCK.getId(ModDecorativeBlocks.BIRCH_TABLE).getPath().replaceAll("_table", "_log")),
                new Identifier("minecraft", "block/" + Registries.BLOCK.getId(ModDecorativeBlocks.BIRCH_TABLE).getPath().replaceAll("_table", "_planks")));
        registerWoodTableModelBlockStates(blockStateModelGenerator, ModDecorativeBlocks.JUNGLE_TABLE,
                new Identifier("minecraft", "block/" + Registries.BLOCK.getId(ModDecorativeBlocks.JUNGLE_TABLE).getPath().replaceAll("_table", "_log")),
                new Identifier("minecraft", "block/" + Registries.BLOCK.getId(ModDecorativeBlocks.JUNGLE_TABLE).getPath().replaceAll("_table", "_planks")));
        registerWoodTableModelBlockStates(blockStateModelGenerator, ModDecorativeBlocks.ACACIA_TABLE,
                new Identifier("minecraft", "block/" + Registries.BLOCK.getId(ModDecorativeBlocks.ACACIA_TABLE).getPath().replaceAll("_table", "_log")),
                new Identifier("minecraft", "block/" + Registries.BLOCK.getId(ModDecorativeBlocks.ACACIA_TABLE).getPath().replaceAll("_table", "_planks")));
        registerWoodTableModelBlockStates(blockStateModelGenerator, ModDecorativeBlocks.DARK_OAK_TABLE,
                new Identifier("minecraft", "block/" + Registries.BLOCK.getId(ModDecorativeBlocks.DARK_OAK_TABLE).getPath().replaceAll("_table", "_log")),
                new Identifier("minecraft", "block/" + Registries.BLOCK.getId(ModDecorativeBlocks.DARK_OAK_TABLE).getPath().replaceAll("_table", "_planks")));
        registerWoodTableModelBlockStates(blockStateModelGenerator, ModDecorativeBlocks.MANGROVE_TABLE,
                new Identifier("minecraft", "block/" + Registries.BLOCK.getId(ModDecorativeBlocks.MANGROVE_TABLE).getPath().replaceAll("_table", "_log")),
                new Identifier("minecraft", "block/" + Registries.BLOCK.getId(ModDecorativeBlocks.MANGROVE_TABLE).getPath().replaceAll("_table", "_planks")));
        registerWoodTableModelBlockStates(blockStateModelGenerator, ModDecorativeBlocks.CHERRY_TABLE,
                new Identifier("minecraft", "block/" + Registries.BLOCK.getId(ModDecorativeBlocks.CHERRY_TABLE).getPath().replaceAll("_table", "_log")),
                new Identifier("minecraft", "block/" + Registries.BLOCK.getId(ModDecorativeBlocks.CHERRY_TABLE).getPath().replaceAll("_table", "_planks")));
        registerWoodTableModelBlockStates(blockStateModelGenerator, ModDecorativeBlocks.BAMBOO_TABLE,
                new Identifier("minecraft", "block/" + Registries.BLOCK.getId(ModDecorativeBlocks.BAMBOO_TABLE).getPath().replaceAll("_table", "_planks")),
                new Identifier("minecraft", "block/" + Registries.BLOCK.getId(ModDecorativeBlocks.BAMBOO_TABLE).getPath().replaceAll("_table", "_planks")));
        registerWoodTableModelBlockStates(blockStateModelGenerator, ModDecorativeBlocks.CRIMSON_TABLE,
                new Identifier("minecraft", "block/" + Registries.BLOCK.getId(ModDecorativeBlocks.CRIMSON_TABLE).getPath().replaceAll("_table", "_stem")),
                new Identifier("minecraft", "block/" + Registries.BLOCK.getId(ModDecorativeBlocks.CRIMSON_TABLE).getPath().replaceAll("_table", "_planks")));
        registerWoodTableModelBlockStates(blockStateModelGenerator, ModDecorativeBlocks.WARPED_TABLE,
                new Identifier("minecraft", "block/" + Registries.BLOCK.getId(ModDecorativeBlocks.WARPED_TABLE).getPath().replaceAll("_table", "_stem")),
                new Identifier("minecraft", "block/" + Registries.BLOCK.getId(ModDecorativeBlocks.WARPED_TABLE).getPath().replaceAll("_table", "_planks")));

        registerWoodChairModelBlockStates(blockStateModelGenerator, ModDecorativeBlocks.OAK_CHAIR,
                new Identifier("minecraft", "block/" + Registries.BLOCK.getId(ModDecorativeBlocks.OAK_CHAIR).getPath().replaceAll("_chair", "_log")),
                new Identifier("minecraft", "block/" + Registries.BLOCK.getId(ModDecorativeBlocks.OAK_CHAIR).getPath().replaceAll("_chair", "_planks")));
        registerWoodChairModelBlockStates(blockStateModelGenerator, ModDecorativeBlocks.SPRUCE_CHAIR,
                new Identifier("minecraft", "block/" + Registries.BLOCK.getId(ModDecorativeBlocks.SPRUCE_CHAIR).getPath().replaceAll("_chair", "_log")),
                new Identifier("minecraft", "block/" + Registries.BLOCK.getId(ModDecorativeBlocks.SPRUCE_CHAIR).getPath().replaceAll("_chair", "_planks")));
        registerWoodChairModelBlockStates(blockStateModelGenerator, ModDecorativeBlocks.BIRCH_CHAIR,
                new Identifier("minecraft", "block/" + Registries.BLOCK.getId(ModDecorativeBlocks.BIRCH_CHAIR).getPath().replaceAll("_chair", "_log")),
                new Identifier("minecraft", "block/" + Registries.BLOCK.getId(ModDecorativeBlocks.BIRCH_CHAIR).getPath().replaceAll("_chair", "_planks")));
        registerWoodChairModelBlockStates(blockStateModelGenerator, ModDecorativeBlocks.JUNGLE_CHAIR,
                new Identifier("minecraft", "block/" + Registries.BLOCK.getId(ModDecorativeBlocks.JUNGLE_CHAIR).getPath().replaceAll("_chair", "_log")),
                new Identifier("minecraft", "block/" + Registries.BLOCK.getId(ModDecorativeBlocks.JUNGLE_CHAIR).getPath().replaceAll("_chair", "_planks")));
        registerWoodChairModelBlockStates(blockStateModelGenerator, ModDecorativeBlocks.ACACIA_CHAIR,
                new Identifier("minecraft", "block/" + Registries.BLOCK.getId(ModDecorativeBlocks.ACACIA_CHAIR).getPath().replaceAll("_chair", "_log")),
                new Identifier("minecraft", "block/" + Registries.BLOCK.getId(ModDecorativeBlocks.ACACIA_CHAIR).getPath().replaceAll("_chair", "_planks")));
        registerWoodChairModelBlockStates(blockStateModelGenerator, ModDecorativeBlocks.DARK_OAK_CHAIR,
                new Identifier("minecraft", "block/" + Registries.BLOCK.getId(ModDecorativeBlocks.DARK_OAK_CHAIR).getPath().replaceAll("_chair", "_log")),
                new Identifier("minecraft", "block/" + Registries.BLOCK.getId(ModDecorativeBlocks.DARK_OAK_CHAIR).getPath().replaceAll("_chair", "_planks")));
        registerWoodChairModelBlockStates(blockStateModelGenerator, ModDecorativeBlocks.MANGROVE_CHAIR,
                new Identifier("minecraft", "block/" + Registries.BLOCK.getId(ModDecorativeBlocks.MANGROVE_CHAIR).getPath().replaceAll("_chair", "_log")),
                new Identifier("minecraft", "block/" + Registries.BLOCK.getId(ModDecorativeBlocks.MANGROVE_CHAIR).getPath().replaceAll("_chair", "_planks")));
        registerWoodChairModelBlockStates(blockStateModelGenerator, ModDecorativeBlocks.CHERRY_CHAIR,
                new Identifier("minecraft", "block/" + Registries.BLOCK.getId(ModDecorativeBlocks.CHERRY_CHAIR).getPath().replaceAll("_chair", "_log")),
                new Identifier("minecraft", "block/" + Registries.BLOCK.getId(ModDecorativeBlocks.CHERRY_CHAIR).getPath().replaceAll("_chair", "_planks")));
        registerWoodChairModelBlockStates(blockStateModelGenerator, ModDecorativeBlocks.BAMBOO_CHAIR,
                new Identifier("minecraft", "block/" + Registries.BLOCK.getId(ModDecorativeBlocks.BAMBOO_CHAIR).getPath().replaceAll("_chair", "_planks")),
                new Identifier("minecraft", "block/" + Registries.BLOCK.getId(ModDecorativeBlocks.BAMBOO_CHAIR).getPath().replaceAll("_chair", "_planks")));
        registerWoodChairModelBlockStates(blockStateModelGenerator, ModDecorativeBlocks.CRIMSON_CHAIR,
                new Identifier("minecraft", "block/" + Registries.BLOCK.getId(ModDecorativeBlocks.CRIMSON_CHAIR).getPath().replaceAll("_chair", "_stem")),
                new Identifier("minecraft", "block/" + Registries.BLOCK.getId(ModDecorativeBlocks.CRIMSON_CHAIR).getPath().replaceAll("_chair", "_planks")));
        registerWoodChairModelBlockStates(blockStateModelGenerator, ModDecorativeBlocks.WARPED_CHAIR,
                new Identifier("minecraft", "block/" + Registries.BLOCK.getId(ModDecorativeBlocks.WARPED_CHAIR).getPath().replaceAll("_chair", "_stem")),
                new Identifier("minecraft", "block/" + Registries.BLOCK.getId(ModDecorativeBlocks.WARPED_CHAIR).getPath().replaceAll("_chair", "_planks")));

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


        registerVanillaVerticalSlabModelBlockStates(blockStateModelGenerator, ModBlocks.STONE_VERTICAL_SLAB, "stone");
        registerVanillaVerticalSlabModelBlockStates(blockStateModelGenerator, ModBlocks.COBBLESTONE_VERTICAL_SLAB, "cobblestone");
        registerVanillaVerticalSlabModelBlockStates(blockStateModelGenerator, ModBlocks.MOSSY_COBBLESTONE_VERTICAL_SLAB, "mossy_cobblestone");
        registerVanillaVerticalSlabModelBlockStates(blockStateModelGenerator, ModBlocks.SMOOTH_STONE_VERTICAL_SLAB, "smooth_stone");
        registerVanillaVerticalSlabModelBlockStates(blockStateModelGenerator, ModBlocks.STONE_BRICK_VERTICAL_SLAB, "stone_bricks");
        registerVanillaVerticalSlabModelBlockStates(blockStateModelGenerator, ModBlocks.MOSSY_STONE_BRICK_VERTICAL_SLAB, "mossy_stone_bricks");
        registerVanillaVerticalSlabModelBlockStates(blockStateModelGenerator, ModBlocks.GRANITE_VERTICAL_SLAB, "granite");
        registerVanillaVerticalSlabModelBlockStates(blockStateModelGenerator, ModBlocks.POLISHED_GRANITE_VERTICAL_SLAB, "polished_granite");
        registerVanillaVerticalSlabModelBlockStates(blockStateModelGenerator, ModBlocks.DIORITE_VERTICAL_SLAB, "diorite");
        registerVanillaVerticalSlabModelBlockStates(blockStateModelGenerator, ModBlocks.POLISHED_DIORITE_VERTICAL_SLAB, "polished_diorite");
        registerVanillaVerticalSlabModelBlockStates(blockStateModelGenerator, ModBlocks.ANDESITE_VERTICAL_SLAB, "andesite");
        registerVanillaVerticalSlabModelBlockStates(blockStateModelGenerator, ModBlocks.POLISHED_ANDESITE_VERTICAL_SLAB, "polished_andesite");
        registerVanillaVerticalSlabModelBlockStates(blockStateModelGenerator, ModBlocks.COBBLED_DEEPSLATE_VERTICAL_SLAB, "cobbled_deepslate");
        registerVanillaVerticalSlabModelBlockStates(blockStateModelGenerator, ModBlocks.POLISHED_DEEPSLATE_VERTICAL_SLAB, "polished_deepslate");
        registerVanillaVerticalSlabModelBlockStates(blockStateModelGenerator, ModBlocks.DEEPSLATE_BRICK_VERTICAL_SLAB, "deepslate_bricks");
        registerVanillaVerticalSlabModelBlockStates(blockStateModelGenerator, ModBlocks.DEEPSLATE_TILE_VERTICAL_SLAB, "deepslate_tiles");
        registerVanillaVerticalSlabModelBlockStates(blockStateModelGenerator, ModBlocks.BRICK_VERTICAL_SLAB, "bricks");
        registerVanillaVerticalSlabModelBlockStates(blockStateModelGenerator, ModBlocks.MUD_BRICK_VERTICAL_SLAB, "mud_bricks");
        registerVanillaVerticalSlabModelBlockStates(blockStateModelGenerator, ModBlocks.SANDSTONE_VERTICAL_SLAB, "sandstone");
        registerVanillaVerticalSlabModelBlockStates(blockStateModelGenerator, ModBlocks.SMOOTH_SANDSTONE_VERTICAL_SLAB, "sandstone_top");
        registerVanillaVerticalSlabModelBlockStates(blockStateModelGenerator, ModBlocks.CUT_SANDSTONE_VERTICAL_SLAB, "cut_sandstone");
        registerVanillaVerticalSlabModelBlockStates(blockStateModelGenerator, ModBlocks.RED_SANDSTONE_VERTICAL_SLAB, "red_sandstone");
        registerVanillaVerticalSlabModelBlockStates(blockStateModelGenerator, ModBlocks.SMOOTH_RED_SANDSTONE_VERTICAL_SLAB, "red_sandstone_top");
        registerVanillaVerticalSlabModelBlockStates(blockStateModelGenerator, ModBlocks.CUT_RED_SANDSTONE_VERTICAL_SLAB, "cut_red_sandstone");
        registerVanillaVerticalSlabModelBlockStates(blockStateModelGenerator, ModBlocks.PRISMARINE_VERTICAL_SLAB, "prismarine");
        registerVanillaVerticalSlabModelBlockStates(blockStateModelGenerator, ModBlocks.PRISMARINE_BRICK_VERTICAL_SLAB, "prismarine_bricks");
        registerVanillaVerticalSlabModelBlockStates(blockStateModelGenerator, ModBlocks.DARK_PRISMARINE_VERTICAL_SLAB, "dark_prismarine");
        registerVanillaVerticalSlabModelBlockStates(blockStateModelGenerator, ModBlocks.NETHER_BRICK_VERTICAL_SLAB, "nether_bricks");
        registerVanillaVerticalSlabModelBlockStates(blockStateModelGenerator, ModBlocks.RED_NETHER_BRICK_VERTICAL_SLAB, "red_nether_bricks");
        registerVanillaVerticalSlabModelBlockStates(blockStateModelGenerator, ModBlocks.BLACKSTONE_VERTICAL_SLAB, "blackstone");
        registerVanillaVerticalSlabModelBlockStates(blockStateModelGenerator, ModBlocks.POLISHED_BLACKSTONE_VERTICAL_SLAB, "polished_blackstone");
        registerVanillaVerticalSlabModelBlockStates(blockStateModelGenerator, ModBlocks.POLISHED_BLACKSTONE_BRICK_VERTICAL_SLAB, "polished_blackstone_bricks");
        registerVanillaVerticalSlabModelBlockStates(blockStateModelGenerator, ModBlocks.END_STONE_BRICK_VERTICAL_SLAB, "end_stone_bricks");
        registerVanillaVerticalSlabModelBlockStates(blockStateModelGenerator, ModBlocks.PURPUR_VERTICAL_SLAB, "purpur_block");
        registerVanillaVerticalSlabModelBlockStates(blockStateModelGenerator, ModBlocks.QUARTZ_VERTICAL_SLAB, "quartz_block_side");
        registerVanillaVerticalSlabModelBlockStates(blockStateModelGenerator, ModBlocks.SMOOTH_QUARTZ_VERTICAL_SLAB, "quartz_block_bottom");
        registerVanillaVerticalSlabModelBlockStates(blockStateModelGenerator, ModBlocks.CUT_COPPER_VERTICAL_SLAB, "cut_copper");
        registerVanillaVerticalSlabModelBlockStates(blockStateModelGenerator, ModBlocks.EXPOSED_CUT_COPPER_VERTICAL_SLAB, "exposed_cut_copper");
        registerVanillaVerticalSlabModelBlockStates(blockStateModelGenerator, ModBlocks.WEATHERED_CUT_COPPER_VERTICAL_SLAB, "weathered_cut_copper");
        registerVanillaVerticalSlabModelBlockStates(blockStateModelGenerator, ModBlocks.OXIDIZED_CUT_COPPER_VERTICAL_SLAB, "oxidized_cut_copper");
        registerVanillaVerticalSlabModelBlockStates(blockStateModelGenerator, ModBlocks.WAXED_CUT_COPPER_VERTICAL_SLAB, "cut_copper");
        registerVanillaVerticalSlabModelBlockStates(blockStateModelGenerator, ModBlocks.WAXED_EXPOSED_CUT_COPPER_VERTICAL_SLAB, "exposed_cut_copper");
        registerVanillaVerticalSlabModelBlockStates(blockStateModelGenerator, ModBlocks.WAXED_WEATHERED_CUT_COPPER_VERTICAL_SLAB, "weathered_cut_copper");
        registerVanillaVerticalSlabModelBlockStates(blockStateModelGenerator, ModBlocks.WAXED_OXIDIZED_CUT_COPPER_VERTICAL_SLAB, "oxidized_cut_copper");

        registerVanillaVerticalSlabModelBlockStates(blockStateModelGenerator, ModBlocks.OAK_VERTICAL_SLAB, "oak_planks");
        registerVanillaVerticalSlabModelBlockStates(blockStateModelGenerator, ModBlocks.SPRUCE_VERTICAL_SLAB, "spruce_planks");
        registerVanillaVerticalSlabModelBlockStates(blockStateModelGenerator, ModBlocks.BIRCH_VERTICAL_SLAB, "birch_planks");
        registerVanillaVerticalSlabModelBlockStates(blockStateModelGenerator, ModBlocks.JUNGLE_VERTICAL_SLAB, "jungle_planks");
        registerVanillaVerticalSlabModelBlockStates(blockStateModelGenerator, ModBlocks.ACACIA_VERTICAL_SLAB, "acacia_planks");
        registerVanillaVerticalSlabModelBlockStates(blockStateModelGenerator, ModBlocks.DARK_OAK_VERTICAL_SLAB, "dark_oak_planks");
        registerVanillaVerticalSlabModelBlockStates(blockStateModelGenerator, ModBlocks.MANGROVE_VERTICAL_SLAB, "mangrove_planks");
        registerVanillaVerticalSlabModelBlockStates(blockStateModelGenerator, ModBlocks.CHERRY_VERTICAL_SLAB, "cherry_planks");
        registerVanillaVerticalSlabModelBlockStates(blockStateModelGenerator, ModBlocks.BAMBOO_VERTICAL_SLAB, "bamboo_planks");
        registerVanillaVerticalSlabModelBlockStates(blockStateModelGenerator, ModBlocks.CRIMSON_VERTICAL_SLAB, "crimson_planks");
        registerVanillaVerticalSlabModelBlockStates(blockStateModelGenerator, ModBlocks.WARPED_VERTICAL_SLAB, "warped_planks");

        registerVanillaVerticalSlabModelBlockStates(blockStateModelGenerator, ModBlocks.BLACK_WOOL_VERTICAL_SLAB, "black_wool");
        registerVanillaVerticalSlabModelBlockStates(blockStateModelGenerator, ModBlocks.BLUE_WOOL_VERTICAL_SLAB, "blue_wool");
        registerVanillaVerticalSlabModelBlockStates(blockStateModelGenerator, ModBlocks.BROWN_WOOL_VERTICAL_SLAB, "brown_wool");
        registerVanillaVerticalSlabModelBlockStates(blockStateModelGenerator, ModBlocks.CYAN_WOOL_VERTICAL_SLAB, "cyan_wool");
        registerVanillaVerticalSlabModelBlockStates(blockStateModelGenerator, ModBlocks.GRAY_WOOL_VERTICAL_SLAB, "gray_wool");
        registerVanillaVerticalSlabModelBlockStates(blockStateModelGenerator, ModBlocks.GREEN_WOOL_VERTICAL_SLAB, "green_wool");
        registerVanillaVerticalSlabModelBlockStates(blockStateModelGenerator, ModBlocks.LIGHT_BLUE_WOOL_VERTICAL_SLAB, "light_blue_wool");
        registerVanillaVerticalSlabModelBlockStates(blockStateModelGenerator, ModBlocks.LIGHT_GRAY_WOOL_VERTICAL_SLAB, "light_gray_wool");
        registerVanillaVerticalSlabModelBlockStates(blockStateModelGenerator, ModBlocks.LIME_WOOL_VERTICAL_SLAB, "lime_wool");
        registerVanillaVerticalSlabModelBlockStates(blockStateModelGenerator, ModBlocks.MAGENTA_WOOL_VERTICAL_SLAB, "magenta_wool");
        registerVanillaVerticalSlabModelBlockStates(blockStateModelGenerator, ModBlocks.ORANGE_WOOL_VERTICAL_SLAB, "orange_wool");
        registerVanillaVerticalSlabModelBlockStates(blockStateModelGenerator, ModBlocks.PINK_WOOL_VERTICAL_SLAB, "pink_wool");
        registerVanillaVerticalSlabModelBlockStates(blockStateModelGenerator, ModBlocks.PURPLE_WOOL_VERTICAL_SLAB, "purple_wool");
        registerVanillaVerticalSlabModelBlockStates(blockStateModelGenerator, ModBlocks.RED_WOOL_VERTICAL_SLAB, "red_wool");
        registerVanillaVerticalSlabModelBlockStates(blockStateModelGenerator, ModBlocks.WHITE_WOOL_VERTICAL_SLAB, "white_wool");
        registerVanillaVerticalSlabModelBlockStates(blockStateModelGenerator, ModBlocks.YELLOW_WOOL_VERTICAL_SLAB, "yellow_wool");

        // Crops
        blockStateModelGenerator.registerCrop(ModNatureBlocks.BELL_PEPPER_CROP, BellpepperCropBlock.AGE, 0, 1, 2, 3, 4);
        blockStateModelGenerator.registerCrop(ModNatureBlocks.CUCUMBER_CROP, CucumberCropBlock.AGE, 0, 1, 2, 3);
        blockStateModelGenerator.registerCrop(ModNatureBlocks.FLAX_CROP, FlaxCropBlock.AGE, 0, 1, 2, 3);
        blockStateModelGenerator.registerCrop(ModNatureBlocks.GARLIC_CROP, GarlicCropBlock.AGE, 0, 1, 2, 3);
        blockStateModelGenerator.registerCrop(ModNatureBlocks.LEEK_CROP, LeekCropBlock.AGE, 0, 1, 2, 3);
        blockStateModelGenerator.registerCrop(ModNatureBlocks.LETTUCE_CROP, LettuceCropBlock.AGE, 0, 1, 2, 3);
        blockStateModelGenerator.registerCrop(ModNatureBlocks.ONION_CROP, OnionCropBlock.AGE, 0, 1, 2, 3);
        
        //CLUSTERS
        blockStateModelGenerator.registerAmethyst(ModBlocks.SAPPHIRE_CLUSTER);
        blockStateModelGenerator.registerAmethyst(ModBlocks.SMALL_SAPPHIRE_BUD);
        blockStateModelGenerator.registerAmethyst(ModBlocks.MEDIUM_SAPPHIRE_BUD);
        blockStateModelGenerator.registerAmethyst(ModBlocks.LARGE_SAPPHIRE_BUD);
        blockStateModelGenerator.registerAmethyst(ModBlocks.RED_AGATE_CLUSTER);
        blockStateModelGenerator.registerAmethyst(ModBlocks.SMALL_RED_AGATE_BUD);
        blockStateModelGenerator.registerAmethyst(ModBlocks.MEDIUM_RED_AGATE_BUD);
        blockStateModelGenerator.registerAmethyst(ModBlocks.LARGE_RED_AGATE_BUD);
        blockStateModelGenerator.registerAmethyst(ModBlocks.CITRINE_CLUSTER);
        blockStateModelGenerator.registerAmethyst(ModBlocks.SMALL_CITRINE_BUD);
        blockStateModelGenerator.registerAmethyst(ModBlocks.MEDIUM_CITRINE_BUD);
        blockStateModelGenerator.registerAmethyst(ModBlocks.LARGE_CITRINE_BUD);
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
        itemModelGenerator.register(ModBlocks.SAPPHIRE_CLUSTER.asItem(), Models.GENERATED);
        itemModelGenerator.register(ModBlocks.SMALL_SAPPHIRE_BUD.asItem(), Models.GENERATED);
        itemModelGenerator.register(ModBlocks.MEDIUM_SAPPHIRE_BUD.asItem(), Models.GENERATED);
        itemModelGenerator.register(ModBlocks.LARGE_SAPPHIRE_BUD.asItem(), Models.GENERATED);
        itemModelGenerator.register(ModBlocks.RED_AGATE_CLUSTER.asItem(), Models.GENERATED);
        itemModelGenerator.register(ModBlocks.SMALL_RED_AGATE_BUD.asItem(), Models.GENERATED);
        itemModelGenerator.register(ModBlocks.MEDIUM_RED_AGATE_BUD.asItem(), Models.GENERATED);
        itemModelGenerator.register(ModBlocks.LARGE_RED_AGATE_BUD.asItem(), Models.GENERATED);
        itemModelGenerator.register(ModBlocks.CITRINE_CLUSTER.asItem(), Models.GENERATED);
        itemModelGenerator.register(ModBlocks.SMALL_CITRINE_BUD.asItem(), Models.GENERATED);
        itemModelGenerator.register(ModBlocks.MEDIUM_CITRINE_BUD.asItem(), Models.GENERATED);
        itemModelGenerator.register(ModBlocks.LARGE_CITRINE_BUD.asItem(), Models.GENERATED);
    }
}
