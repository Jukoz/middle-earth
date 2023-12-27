package net.jukoz.me.datageneration;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.block.ModBlocks;
import net.jukoz.me.block.ModNatureBlocks;
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
        for (SimpleBlockModel.ChiseledBlock block : SimpleBlockModel.chiseledBlocks) {
            blockStateModelGenerator.registerSimpleCubeAll(block.base());
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

        for (SimpleSlabModel.Slab block : SimpleSlabModel.blocks) {
            Identifier id = ModelIds.getBlockModelId(block.block());
            Block slab = block.slab();

            TexturedModel texturedModel = TexturedModel.CUBE_ALL.get(block.block());
            Identifier bottom = Models.SLAB.upload(slab, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);
            Identifier top = Models.SLAB_TOP.upload(slab, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);

            blockStateModelGenerator.blockStateCollector.accept(BlockStateModelGenerator
                    .createSlabBlockState(slab, bottom, top, id));
        }

        for (SimpleStairModel.Stair block : SimpleStairModel.blocks) {
            TexturedModel texturedModel = TexturedModel.CUBE_ALL.get(block.block());
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

        for(SimpleVerticalSlabModel.VerticalSlab verticalSlab : SimpleVerticalSlabModel.blocks){
            if (verticalSlab.vanilla() && verticalSlab.block().getName().toString().contains("planks")){
                blockStateModelGenerator.blockStateCollector.accept(VariantsBlockStateSupplier.create(verticalSlab.verticalSlab(),
                        BlockStateVariant.create().put(VariantSettings.MODEL, MEModels.VERTICAL_SLAB.upload(verticalSlab.verticalSlab(),
                                TextureMap.of(TextureKey.ALL,new Identifier("minecraft","block/" +
                                        Registries.BLOCK.getId(verticalSlab.verticalSlab()).getPath().replaceAll("_vertical_slab","_planks"))),
                                blockStateModelGenerator.modelCollector)).put(VariantSettings.UVLOCK, true)).coordinate(BlockStateModelGenerator.createNorthDefaultHorizontalRotationStates()));
            } else if (!verticalSlab.vanilla() && verticalSlab.block().getName().toString().contains("planks")){
                blockStateModelGenerator.blockStateCollector.accept(VariantsBlockStateSupplier.create(verticalSlab.verticalSlab(),
                        BlockStateVariant.create().put(VariantSettings.MODEL, MEModels.VERTICAL_SLAB.upload(verticalSlab.verticalSlab(),
                                TextureMap.of(TextureKey.ALL,new Identifier(MiddleEarth.MOD_ID,"block/" +
                                        Registries.BLOCK.getId(verticalSlab.verticalSlab()).getPath().replaceAll("_vertical_slab","_planks"))),
                                blockStateModelGenerator.modelCollector)).put(VariantSettings.UVLOCK, true)).coordinate(BlockStateModelGenerator.createNorthDefaultHorizontalRotationStates()));
            } else if (!verticalSlab.vanilla() && (verticalSlab.block().getName().toString().contains("reed") || verticalSlab.block().getName().toString().contains("straw"))){
                blockStateModelGenerator.blockStateCollector.accept(VariantsBlockStateSupplier.create(verticalSlab.verticalSlab(),
                        BlockStateVariant.create().put(VariantSettings.MODEL, MEModels.VERTICAL_SLAB.upload(verticalSlab.verticalSlab(),
                                TextureMap.of(TextureKey.ALL,new Identifier(MiddleEarth.MOD_ID,"block/" +
                                        Registries.BLOCK.getId(verticalSlab.verticalSlab()).getPath().replaceAll("_vertical_slab","_block"))),
                                blockStateModelGenerator.modelCollector)).put(VariantSettings.UVLOCK, true)).coordinate(BlockStateModelGenerator.createNorthDefaultHorizontalRotationStates()));
            }else if (!verticalSlab.vanilla()){
                blockStateModelGenerator.blockStateCollector.accept(VariantsBlockStateSupplier.create(verticalSlab.verticalSlab(),
                        BlockStateVariant.create().put(VariantSettings.MODEL, MEModels.VERTICAL_SLAB.upload(verticalSlab.verticalSlab(),
                                TextureMap.of(TextureKey.ALL,new Identifier(MiddleEarth.MOD_ID,"block/" +
                                        Registries.BLOCK.getId(verticalSlab.verticalSlab()).getPath().replaceAll("_vertical_slab",""))),
                                blockStateModelGenerator.modelCollector)).put(VariantSettings.UVLOCK, true)).coordinate(BlockStateModelGenerator.createNorthDefaultHorizontalRotationStates()));
            }
        }

        for(SimplePaneModel.Pane pane : SimplePaneModel.panes){
            registerLeadGlassPane(blockStateModelGenerator, pane.glass(), pane.pane());
        }

        registerVerticalSlabModelBlockStates(blockStateModelGenerator, ModBlocks.STONE_VERTICAL_SLAB, "stone");
        registerVerticalSlabModelBlockStates(blockStateModelGenerator, ModBlocks.COBBLESTONE_VERTICAL_SLAB, "cobblestone");
        registerVerticalSlabModelBlockStates(blockStateModelGenerator, ModBlocks.MOSSY_COBBLESTONE_VERTICAL_SLAB, "mossy_cobblestone");
        registerVerticalSlabModelBlockStates(blockStateModelGenerator, ModBlocks.SMOOTH_STONE_VERTICAL_SLAB, "smooth_stone");
        registerVerticalSlabModelBlockStates(blockStateModelGenerator, ModBlocks.STONE_BRICK_VERTICAL_SLAB, "stone_bricks");
        registerVerticalSlabModelBlockStates(blockStateModelGenerator, ModBlocks.MOSSY_STONE_BRICK_VERTICAL_SLAB, "mossy_stone_bricks");
        registerVerticalSlabModelBlockStates(blockStateModelGenerator, ModBlocks.GRANITE_VERTICAL_SLAB, "granite");
        registerVerticalSlabModelBlockStates(blockStateModelGenerator, ModBlocks.POLISHED_GRANITE_VERTICAL_SLAB, "polished_granite");
        registerVerticalSlabModelBlockStates(blockStateModelGenerator, ModBlocks.DIORITE_VERTICAL_SLAB, "diorite");
        registerVerticalSlabModelBlockStates(blockStateModelGenerator, ModBlocks.POLISHED_DIORITE_VERTICAL_SLAB, "polished_diorite");
        registerVerticalSlabModelBlockStates(blockStateModelGenerator, ModBlocks.ANDESITE_VERTICAL_SLAB, "andesite");
        registerVerticalSlabModelBlockStates(blockStateModelGenerator, ModBlocks.POLISHED_ANDESITE_VERTICAL_SLAB, "polished_andesite");
        registerVerticalSlabModelBlockStates(blockStateModelGenerator, ModBlocks.COBBLED_DEEPSLATE_VERTICAL_SLAB, "cobbled_deepslate");
        registerVerticalSlabModelBlockStates(blockStateModelGenerator, ModBlocks.POLISHED_DEEPSLATE_VERTICAL_SLAB, "polished_deepslate");
        registerVerticalSlabModelBlockStates(blockStateModelGenerator, ModBlocks.DEEPSLATE_BRICK_VERTICAL_SLAB, "deepslate_bricks");
        registerVerticalSlabModelBlockStates(blockStateModelGenerator, ModBlocks.DEEPSLATE_TILE_VERTICAL_SLAB, "deepslate_tiles");
        registerVerticalSlabModelBlockStates(blockStateModelGenerator, ModBlocks.BRICK_VERTICAL_SLAB, "bricks");
        registerVerticalSlabModelBlockStates(blockStateModelGenerator, ModBlocks.MUD_BRICK_VERTICAL_SLAB, "mud_bricks");
        registerVerticalSlabModelBlockStates(blockStateModelGenerator, ModBlocks.SANDSTONE_VERTICAL_SLAB, "sandstone");
        registerVerticalSlabModelBlockStates(blockStateModelGenerator, ModBlocks.SMOOTH_SANDSTONE_VERTICAL_SLAB, "sandstone_top");
        registerVerticalSlabModelBlockStates(blockStateModelGenerator, ModBlocks.CUT_SANDSTONE_VERTICAL_SLAB, "cut_sandstone");
        registerVerticalSlabModelBlockStates(blockStateModelGenerator, ModBlocks.RED_SANDSTONE_VERTICAL_SLAB, "red_sandstone");
        registerVerticalSlabModelBlockStates(blockStateModelGenerator, ModBlocks.SMOOTH_RED_SANDSTONE_VERTICAL_SLAB, "red_sandstone_top");
        registerVerticalSlabModelBlockStates(blockStateModelGenerator, ModBlocks.CUT_RED_SANDSTONE_VERTICAL_SLAB, "cut_red_sandstone");
        registerVerticalSlabModelBlockStates(blockStateModelGenerator, ModBlocks.PRISMARINE_VERTICAL_SLAB, "prismarine");
        registerVerticalSlabModelBlockStates(blockStateModelGenerator, ModBlocks.PRISMARINE_BRICK_VERTICAL_SLAB, "prismarine_bricks");
        registerVerticalSlabModelBlockStates(blockStateModelGenerator, ModBlocks.DARK_PRISMARINE_VERTICAL_SLAB, "dark_prismarine");
        registerVerticalSlabModelBlockStates(blockStateModelGenerator, ModBlocks.NETHER_BRICK_VERTICAL_SLAB, "nether_bricks");
        registerVerticalSlabModelBlockStates(blockStateModelGenerator, ModBlocks.RED_NETHER_BRICK_VERTICAL_SLAB, "red_nether_bricks");
        registerVerticalSlabModelBlockStates(blockStateModelGenerator, ModBlocks.BLACKSTONE_VERTICAL_SLAB, "blackstone");
        registerVerticalSlabModelBlockStates(blockStateModelGenerator, ModBlocks.POLISHED_BLACKSTONE_VERTICAL_SLAB, "polished_blackstone");
        registerVerticalSlabModelBlockStates(blockStateModelGenerator, ModBlocks.POLISHED_BLACKSTONE_BRICK_VERTICAL_SLAB, "polished_blackstone_bricks");
        registerVerticalSlabModelBlockStates(blockStateModelGenerator, ModBlocks.END_STONE_BRICK_VERTICAL_SLAB, "end_stone_bricks");
        registerVerticalSlabModelBlockStates(blockStateModelGenerator, ModBlocks.PURPUR_VERTICAL_SLAB, "purpur_block");
        registerVerticalSlabModelBlockStates(blockStateModelGenerator, ModBlocks.QUARTZ_VERTICAL_SLAB, "quartz_block_side");
        registerVerticalSlabModelBlockStates(blockStateModelGenerator, ModBlocks.SMOOTH_QUARTZ_VERTICAL_SLAB, "quartz_block_bottom");
        registerVerticalSlabModelBlockStates(blockStateModelGenerator, ModBlocks.CUT_COPPER_VERTICAL_SLAB, "cut_copper");
        registerVerticalSlabModelBlockStates(blockStateModelGenerator, ModBlocks.EXPOSED_CUT_COPPER_VERTICAL_SLAB, "exposed_cut_copper");
        registerVerticalSlabModelBlockStates(blockStateModelGenerator, ModBlocks.WEATHERED_CUT_COPPER_VERTICAL_SLAB, "weathered_cut_copper");
        registerVerticalSlabModelBlockStates(blockStateModelGenerator, ModBlocks.OXIDIZED_CUT_COPPER_VERTICAL_SLAB, "oxidized_cut_copper");
        registerVerticalSlabModelBlockStates(blockStateModelGenerator, ModBlocks.WAXED_CUT_COPPER_VERTICAL_SLAB, "cut_copper");
        registerVerticalSlabModelBlockStates(blockStateModelGenerator, ModBlocks.WAXED_EXPOSED_CUT_COPPER_VERTICAL_SLAB, "exposed_cut_copper");
        registerVerticalSlabModelBlockStates(blockStateModelGenerator, ModBlocks.WAXED_WEATHERED_CUT_COPPER_VERTICAL_SLAB, "weathered_cut_copper");
        registerVerticalSlabModelBlockStates(blockStateModelGenerator, ModBlocks.WAXED_OXIDIZED_CUT_COPPER_VERTICAL_SLAB, "oxidized_cut_copper");

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

    public void registerVerticalSlabModelBlockStates(BlockStateModelGenerator blockStateModelGenerator, Block block, String texture){
        blockStateModelGenerator.blockStateCollector.accept(VariantsBlockStateSupplier.create(block,
                BlockStateVariant.create().put(VariantSettings.MODEL, MEModels.VERTICAL_SLAB.upload(block,
                        TextureMap.of(TextureKey.ALL,new Identifier("minecraft","block/" + texture)),
                        blockStateModelGenerator.modelCollector)).put(VariantSettings.UVLOCK, true)).coordinate(BlockStateModelGenerator.createNorthDefaultHorizontalRotationStates()));
    }

    public final void registerDoubleBlock(BlockStateModelGenerator blockStateModelGenerator, Block doubleBlock, BlockStateModelGenerator.TintType tintType) {
        blockStateModelGenerator.registerItemModel(doubleBlock.asItem());
        Identifier identifier = blockStateModelGenerator.createSubModel(doubleBlock, "_top", tintType.getCrossModel(), TextureMap::cross);
        Identifier identifier2 = blockStateModelGenerator.createSubModel(doubleBlock, "_bottom", tintType.getCrossModel(), TextureMap::cross);
        blockStateModelGenerator.registerDoubleBlock(doubleBlock, identifier, identifier2);
    }

    public final void registerLeadGlassPane(BlockStateModelGenerator blockStateModelGenerator, Block glass, Block glassPane) {
        blockStateModelGenerator.registerSimpleCubeAll(glass);
        TextureMap textureMap = TextureMap.paneAndTopForEdge(glass, ModBlocks.LEAD_GLASS_PANE);
        Identifier identifier = Models.TEMPLATE_GLASS_PANE_POST.upload(glassPane, textureMap, blockStateModelGenerator.modelCollector);
        Identifier identifier2 = Models.TEMPLATE_GLASS_PANE_SIDE.upload(glassPane, textureMap, blockStateModelGenerator.modelCollector);
        Identifier identifier3 = Models.TEMPLATE_GLASS_PANE_SIDE_ALT.upload(glassPane, textureMap, blockStateModelGenerator.modelCollector);
        Identifier identifier4 = Models.TEMPLATE_GLASS_PANE_NOSIDE.upload(glassPane, textureMap, blockStateModelGenerator.modelCollector);
        Identifier identifier5 = Models.TEMPLATE_GLASS_PANE_NOSIDE_ALT.upload(glassPane, textureMap, blockStateModelGenerator.modelCollector);
        Item item = glassPane.asItem();
        Models.GENERATED.upload(ModelIds.getItemModelId(item), TextureMap.layer0(glass), blockStateModelGenerator.modelCollector);
        blockStateModelGenerator.blockStateCollector.accept(MultipartBlockStateSupplier.create(glassPane).with(BlockStateVariant.create().put(VariantSettings.MODEL, identifier)).with(When.create().set(Properties.NORTH, true), BlockStateVariant.create().put(VariantSettings.MODEL, identifier2)).with(When.create().set(Properties.EAST, true), BlockStateVariant.create().put(VariantSettings.MODEL, identifier2).put(VariantSettings.Y, VariantSettings.Rotation.R90)).with(When.create().set(Properties.SOUTH, true), BlockStateVariant.create().put(VariantSettings.MODEL, identifier3)).with(When.create().set(Properties.WEST, true), BlockStateVariant.create().put(VariantSettings.MODEL, identifier3).put(VariantSettings.Y, VariantSettings.Rotation.R90)).with(When.create().set(Properties.NORTH, false), BlockStateVariant.create().put(VariantSettings.MODEL, identifier4)).with(When.create().set(Properties.EAST, false), BlockStateVariant.create().put(VariantSettings.MODEL, identifier5)).with(When.create().set(Properties.SOUTH, false), BlockStateVariant.create().put(VariantSettings.MODEL, identifier5).put(VariantSettings.Y, VariantSettings.Rotation.R90)).with(When.create().set(Properties.WEST, false), BlockStateVariant.create().put(VariantSettings.MODEL, identifier4).put(VariantSettings.Y, VariantSettings.Rotation.R270)));
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
                itemModelGenerator.register(item, "_pulling_" + i, Models.GENERATED);
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
