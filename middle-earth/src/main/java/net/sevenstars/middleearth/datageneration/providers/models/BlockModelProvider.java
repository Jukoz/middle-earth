package net.sevenstars.middleearth.datageneration.providers.models;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.blockstates.BlockStateGenerator;
import net.minecraft.data.models.blockstates.MultiPartGenerator;
import net.minecraft.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.data.models.blockstates.PropertyDispatch;
import net.minecraft.data.models.blockstates.Variant;
import net.minecraft.data.models.blockstates.VariantProperties;
import net.minecraft.data.models.model.DelegatedModel;
import net.minecraft.data.models.model.ModelLocationUtils;
import net.minecraft.data.models.model.ModelTemplate;
import net.minecraft.data.models.model.ModelTemplates;
import net.minecraft.data.models.model.TextureMapping;
import net.minecraft.data.models.model.TextureSlot;
import net.minecraft.data.models.model.TexturedModel;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.properties.AttachFace;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoorHingeSide;
import net.minecraft.world.level.block.state.properties.DripstoneThickness;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.block.registration.*;
import net.sevenstars.middleearth.block.special.LargeDoorBlock;
import net.sevenstars.middleearth.block.special.RocksBlock;
import net.sevenstars.middleearth.block.special.crop.*;
import net.sevenstars.middleearth.block.special.doors.*;
import net.sevenstars.middleearth.block.special.verticalSlabs.VerticalSlabBlock;
import net.sevenstars.middleearth.block.special.verticalSlabs.VerticalSlabShape;
import net.sevenstars.middleearth.datageneration.content.MEModels;
import net.sevenstars.middleearth.datageneration.content.models.*;
import net.sevenstars.middleearth.datageneration.content.tags.LeavesSets;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import static net.minecraft.data.models.blockstates.Condition.condition;

public class BlockModelProvider implements DataProvider {
    private static final Map<String, String> PALE_OAK_TEXTURE_FALLBACKS = Map.ofEntries(
            Map.entry("middle-earth:block/pale_oak_door_bottom", "minecraft:block/birch_door_bottom"),
            Map.entry("middle-earth:block/pale_oak_door_top", "minecraft:block/birch_door_top"),
            Map.entry("middle-earth:block/pale_oak_leaves", "minecraft:block/dark_oak_leaves"),
            Map.entry("middle-earth:block/pale_oak_log", "minecraft:block/birch_log"),
            Map.entry("middle-earth:block/pale_oak_log_top", "minecraft:block/birch_log_top"),
            Map.entry("middle-earth:block/pale_oak_planks", "minecraft:block/birch_planks"),
            Map.entry("middle-earth:block/pale_oak_trapdoor", "minecraft:block/birch_trapdoor"),
            Map.entry("middle-earth:block/stripped_pale_oak_log", "minecraft:block/stripped_birch_log"),
            Map.entry("middle-earth:block/stripped_pale_oak_log_top", "minecraft:block/stripped_birch_log_top")
    );
    private final PackOutput.PathProvider blockStatePathProvider;
    private final PackOutput.PathProvider modelPathProvider;
    private final Path authoredModelsRoot;

    public BlockModelProvider(PackOutput output) {
        this.blockStatePathProvider = output.createPathProvider(PackOutput.Target.RESOURCE_PACK, "blockstates");
        this.modelPathProvider = output.createPathProvider(PackOutput.Target.RESOURCE_PACK, "models");
        this.authoredModelsRoot = output.getOutputFolder()
                .resolveSibling("resources")
                .resolve("assets")
                .resolve(MiddleEarth.MOD_ID)
                .resolve("models");
    }

    @Override
    public String getName() {
        return "BlockModelProvider";
    }

    public void generateBlockStateModels(GenerationContext blockStateModelGenerator) {

        for (Block block : SimpleBlockModel.blocks) {
            blockStateModelGenerator.createTrivialCube(block);
        }

        for (Block block : LeavesSets.grayscaleLeaves) {
            blockStateModelGenerator.createTintedLeaves(block, TexturedModel.LEAVES, -12012264);
        }

        for (SimpleBlockModel.ChiseledBlock block : SimpleBlockModel.chiseledMainBlockTopBottom) {
            blockStateModelGenerator.createRotatedPillarWithHorizontalVariant(block.base(), TexturedModel.COLUMN_ALT, TexturedModel.COLUMN_HORIZONTAL_ALT);
        }

        for (SimpleBlockModel.ChiseledBlock block : SimpleBlockModel.chiseledBlocksTopBottom) {
            blockStateModelGenerator.createRotatedPillarWithHorizontalVariant(block.base(), TexturedModel.COLUMN_ALT, TexturedModel.COLUMN_HORIZONTAL_ALT);
        }

        for (SimpleBlockModel.ChiseledPolishedBlock block : SimpleBlockModel.chiseledPolishedBlocksTopBottom) {
            blockStateModelGenerator.createRotatedPillarWithHorizontalVariant(block.base(), TexturedModel.COLUMN_ALT, TexturedModel.COLUMN_HORIZONTAL_ALT);
        }

        for (SimpleBlockModel.ChiseledPolishedBlock block : SimpleBlockModel.chiseledTilesBlocksTopBottom) {
            blockStateModelGenerator.createRotatedPillarWithHorizontalVariant(block.base(), TexturedModel.COLUMN_ALT, TexturedModel.COLUMN_HORIZONTAL_ALT);
        }

        for (SimpleBlockModel.ChiseledPolishedBlock block : SimpleBlockModel.chiseledSmoothBlocksTopBottom) {
            blockStateModelGenerator.createRotatedPillarWithHorizontalVariant(block.base(), TexturedModel.COLUMN_ALT, TexturedModel.COLUMN_HORIZONTAL_ALT);
        }

        for (Block wood : SimpleBlockModel.woodBlocks) {
            TextureMapping textureMap = new TextureMapping().put(TextureSlot.ALL,
                    ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "block/" + BuiltInRegistries.BLOCK.getKey(wood).getPath().replaceAll("_wood", "_log").replaceAll("_hyphae", "_stem").replaceAll("treated_log", "treated_wood").replaceAll("aged_log", "aged_wood")));
            Variant identifier = plainVariant(ModelTemplates.CUBE_COLUMN.create(wood, textureMap, blockStateModelGenerator.modelOutput));
            blockStateModelGenerator.blockStateOutput.accept(BlockModelProvider.createAxisAlignedPillarBlock(wood, identifier));
        }

        for (SimpleBlockModel.ChiseledPolishedBlock block : SimpleBlockModel.chiseledPolishedBlocks) {
            blockStateModelGenerator.createTrivialCube(block.base());
        }

        for (SimplePillarModel.Pillar block : SimplePillarModel.blocks) {
            blockStateModelGenerator.createRotatedPillarWithHorizontalVariant(block.base(), TexturedModel.COLUMN_ALT, TexturedModel.COLUMN_HORIZONTAL_ALT);
        }

        for (SimplePillarModel.StonePillar block : SimplePillarModel.stonePillars) {
            blockStateModelGenerator.createRotatedPillarWithHorizontalVariant(block.base(), TexturedModel.COLUMN_ALT, TexturedModel.COLUMN_HORIZONTAL_ALT);
        }

        for (SimplePillarModel.StonePillar block : SimplePillarModel.carvedWindows) {
            TextureMapping textureMap;
            if (block.origin() == Blocks.BASALT || block.origin() == Blocks.DEEPSLATE){
                textureMap = TextureMapping.column(TextureMapping.getBlockTexture(block.base()), TextureMapping.getBlockTexture(block.origin(), "_top"));
            } else {
                textureMap = TextureMapping.column(TextureMapping.getBlockTexture(block.base()), TextureMapping.getBlockTexture(block.origin()));
            }
            Variant weightedVariant = plainVariant(ModelTemplates.CUBE_COLUMN.create(block.base(), textureMap, blockStateModelGenerator.modelOutput));
            blockStateModelGenerator.blockStateOutput.accept(createSimpleBlock(block.base(), weightedVariant));
        }

        for (SimpleSlabModel.Slab block : SimpleSlabModel.slabs) {
            Variant id = plainVariant(ModelLocationUtils.getModelLocation(block.origin()));
            Block slab = block.slab();

            TexturedModel texturedModel = TexturedModel.CUBE.get(block.origin());
            if (block.origin() == Blocks.BASALT || block.origin() == Blocks.POLISHED_BASALT) {
                texturedModel = TexturedModel.createAllSame(ResourceLocation.fromNamespaceAndPath("minecraft", "block/" + BuiltInRegistries.BLOCK.getKey(block.origin()).getPath() + "_side"));
            }
            Variant bottom = plainVariant(ModelTemplates.SLAB_BOTTOM.create(slab, texturedModel.getMapping(), blockStateModelGenerator.modelOutput));
            Variant top = plainVariant(ModelTemplates.SLAB_TOP.create(slab, texturedModel.getMapping(), blockStateModelGenerator.modelOutput));

            blockStateModelGenerator.blockStateOutput.accept(BlockModelProvider
                    .createSlab(slab, bottom, top, id));
        }

        for (SimpleSlabModel.Slab block : SimpleSlabModel.woodSlabs) {
            Variant id = plainVariant(ModelLocationUtils.getModelLocation(block.origin()));
            Block slab = block.slab();

            String modid = BuiltInRegistries.BLOCK.getKey(block.origin()).getNamespace();

            TexturedModel texturedModel = TexturedModel.createAllSame(ResourceLocation.fromNamespaceAndPath(modid, "block/" + BuiltInRegistries.BLOCK.getKey(block.origin()).getPath().replaceAll("_wood", "_log").replaceAll("_hyphae", "_stem").replaceAll("treated_log", "treated_wood").replaceAll("aged_log", "aged_wood")));
            Variant bottom = plainVariant(ModelTemplates.SLAB_BOTTOM.create(slab, texturedModel.getMapping(), blockStateModelGenerator.modelOutput));
            Variant top = plainVariant(ModelTemplates.SLAB_TOP.create(slab, texturedModel.getMapping(), blockStateModelGenerator.modelOutput));

            blockStateModelGenerator.blockStateOutput.accept(BlockModelProvider
                    .createSlab(slab, bottom, top, id));
        }

        for (SimpleSlabModel.Slab block : SimpleSlabModel.strippedSlabs) {
            Variant id = plainVariant(ModelLocationUtils.getModelLocation(block.origin()));
            Block slab = block.slab();

            String modid = BuiltInRegistries.BLOCK.getKey(block.origin()).getNamespace();

            TexturedModel texturedModel = TexturedModel.createAllSame(ResourceLocation.fromNamespaceAndPath(modid, "block/" + BuiltInRegistries.BLOCK.getKey(block.origin()).getPath().replaceAll("_wood", "_log").replaceAll("_hyphae", "_stem").replaceAll("treated_log", "treated_wood").replaceAll("aged_log", "aged_wood")));
            Variant bottom = plainVariant(ModelTemplates.SLAB_BOTTOM.create(slab, texturedModel.getMapping(), blockStateModelGenerator.modelOutput));
            Variant top = plainVariant(ModelTemplates.SLAB_TOP.create(slab, texturedModel.getMapping(), blockStateModelGenerator.modelOutput));

            blockStateModelGenerator.blockStateOutput.accept(BlockModelProvider
                    .createSlab(slab, bottom, top, id));
        }

        for (SimpleSlabModel.Slab block : SimpleSlabModel.vanillaSlabs) {
            Variant id = plainVariant(ModelLocationUtils.getModelLocation(block.origin()));
            Block slab = block.slab();

            TexturedModel texturedModel = TexturedModel.createAllSame(ResourceLocation.fromNamespaceAndPath("minecraft", "block/" + BuiltInRegistries.BLOCK.getKey(block.origin()).getPath().replaceAll("_wood", "_log").replaceAll("_hyphae", "_stem").replaceAll("treated_log", "treated_wood").replaceAll("aged_log", "aged_wood")));
            Variant bottom = plainVariant(ModelTemplates.SLAB_BOTTOM.create(slab, texturedModel.getMapping(), blockStateModelGenerator.modelOutput));
            Variant top = plainVariant(ModelTemplates.SLAB_TOP.create(slab, texturedModel.getMapping(), blockStateModelGenerator.modelOutput));

            blockStateModelGenerator.blockStateOutput.accept(BlockModelProvider
                    .createSlab(slab, bottom, top, id));
        }

        for (SimpleSlabModel.Slab block : SimpleSlabModel.vanillaWoodSlabs) {
            Variant id = plainVariant(ModelLocationUtils.getModelLocation(block.origin()));
            Block slab = block.slab();

            TexturedModel texturedModel = TexturedModel.createAllSame(ResourceLocation.fromNamespaceAndPath("minecraft", "block/" + BuiltInRegistries.BLOCK.getKey(block.origin()).getPath().replaceAll("_wood", "_log").replaceAll("_hyphae", "_stem").replaceAll("treated_log", "treated_wood").replaceAll("aged_log", "aged_wood")));
            Variant bottom = plainVariant(ModelTemplates.SLAB_BOTTOM.create(slab, texturedModel.getMapping(), blockStateModelGenerator.modelOutput));
            Variant top = plainVariant(ModelTemplates.SLAB_TOP.create(slab, texturedModel.getMapping(), blockStateModelGenerator.modelOutput));

            blockStateModelGenerator.blockStateOutput.accept(BlockModelProvider
                    .createSlab(slab, bottom, top, id));
        }

        for (SimpleSlabModel.Slab block : SimpleSlabModel.vanillaStrippedSlab) {
            Variant id = plainVariant(ModelLocationUtils.getModelLocation(block.origin()));
            Block slab = block.slab();

            TexturedModel texturedModel = TexturedModel.createAllSame(ResourceLocation.fromNamespaceAndPath("minecraft", "block/" + BuiltInRegistries.BLOCK.getKey(block.origin()).getPath().replaceAll("_wood", "_log").replaceAll("_hyphae", "_stem").replaceAll("treated_log", "treated_wood").replaceAll("aged_log", "aged_wood")));
            Variant bottom = plainVariant(ModelTemplates.SLAB_BOTTOM.create(slab, texturedModel.getMapping(), blockStateModelGenerator.modelOutput));
            Variant top = plainVariant(ModelTemplates.SLAB_TOP.create(slab, texturedModel.getMapping(), blockStateModelGenerator.modelOutput));

            blockStateModelGenerator.blockStateOutput.accept(BlockModelProvider
                    .createSlab(slab, bottom, top, id));
        }

        for (SimpleStairModel.Stair block : SimpleStairModel.stairs) {
            TexturedModel texturedModel = TexturedModel.CUBE.get(block.origin());
            if (block.origin() == Blocks.BASALT || block.origin() == Blocks.POLISHED_BASALT) {
                texturedModel = TexturedModel.createAllSame(ResourceLocation.fromNamespaceAndPath("minecraft", "block/" + BuiltInRegistries.BLOCK.getKey(block.origin()).getPath() + "_side"));
            }

            Block stairs = block.stairs();
            TextureMapping textureMap = texturedModel.getMapping();
            if(block.origin() == StoneBlockSets.DRYSTONE_SET.cobblestoneBlocks.base()) {
                textureMap = TextureMapping.top(block.origin());
                textureMap.put(TextureSlot.SIDE, BuiltInRegistries.BLOCK.getKey(block.origin()).withPath((path) -> "block/" + path));
                textureMap.put(TextureSlot.BOTTOM, BuiltInRegistries.BLOCK.getKey(block.origin()).withPath((path) -> "block/" + path));
            }

            Variant inner = plainVariant(ModelTemplates.STAIRS_INNER.create(stairs, textureMap, blockStateModelGenerator.modelOutput));
            Variant regular = plainVariant(ModelTemplates.STAIRS_STRAIGHT.create(stairs, textureMap, blockStateModelGenerator.modelOutput));
            Variant outer = plainVariant(ModelTemplates.STAIRS_OUTER.create(stairs, textureMap, blockStateModelGenerator.modelOutput));

            blockStateModelGenerator.blockStateOutput.accept(BlockModelProvider
                    .createStairs(stairs, inner, regular, outer));
        }

        for (SimpleStairModel.Stair block : SimpleStairModel.woodStairs) {
            String modid = BuiltInRegistries.BLOCK.getKey(block.origin()).getNamespace();

            TexturedModel texturedModel = TexturedModel.createAllSame(ResourceLocation.fromNamespaceAndPath(modid, "block/" + BuiltInRegistries.BLOCK.getKey(block.origin()).getPath().replaceAll("_wood", "_log").replaceAll("_hyphae", "_stem").replaceAll("treated_log", "treated_wood").replaceAll("aged_log", "aged_wood")));
            Block stairs = block.stairs();

            Variant inner = plainVariant(ModelTemplates.STAIRS_INNER.create(stairs, texturedModel.getMapping(), blockStateModelGenerator.modelOutput));
            Variant regular = plainVariant(ModelTemplates.STAIRS_STRAIGHT.create(stairs, texturedModel.getMapping(), blockStateModelGenerator.modelOutput));
            Variant outer = plainVariant(ModelTemplates.STAIRS_OUTER.create(stairs, texturedModel.getMapping(), blockStateModelGenerator.modelOutput));

            blockStateModelGenerator.blockStateOutput.accept(BlockModelProvider
                    .createStairs(stairs, inner, regular, outer));
        }

        for (SimpleStairModel.Stair block : SimpleStairModel.strippedStairs) {
            String modid = BuiltInRegistries.BLOCK.getKey(block.origin()).getNamespace();

            TexturedModel texturedModel = TexturedModel.createAllSame(ResourceLocation.fromNamespaceAndPath(modid, "block/" + BuiltInRegistries.BLOCK.getKey(block.origin()).getPath().replaceAll("_wood", "_log").replaceAll("_hyphae", "_stem").replaceAll("treated_log", "treated_wood").replaceAll("aged_log", "aged_wood")));
            Block stairs = block.stairs();

            Variant inner = plainVariant(ModelTemplates.STAIRS_INNER.create(stairs, texturedModel.getMapping(), blockStateModelGenerator.modelOutput));
            Variant regular = plainVariant(ModelTemplates.STAIRS_STRAIGHT.create(stairs, texturedModel.getMapping(), blockStateModelGenerator.modelOutput));
            Variant outer = plainVariant(ModelTemplates.STAIRS_OUTER.create(stairs, texturedModel.getMapping(), blockStateModelGenerator.modelOutput));

            blockStateModelGenerator.blockStateOutput.accept(BlockModelProvider
                    .createStairs(stairs, inner, regular, outer));
        }

        for (SimpleStairModel.Stair block : SimpleStairModel.vanillaWoodStairs) {
            TexturedModel texturedModel = TexturedModel.createAllSame(ResourceLocation.fromNamespaceAndPath("minecraft", "block/" + BuiltInRegistries.BLOCK.getKey(block.origin()).getPath().replaceAll("_wood", "_log").replaceAll("_hyphae", "_stem").replaceAll("treated_log", "treated_wood").replaceAll("aged_log", "aged_wood")));
            Block stairs = block.stairs();

            Variant inner = plainVariant(ModelTemplates.STAIRS_INNER.create(stairs, texturedModel.getMapping(), blockStateModelGenerator.modelOutput));
            Variant regular = plainVariant(ModelTemplates.STAIRS_STRAIGHT.create(stairs, texturedModel.getMapping(), blockStateModelGenerator.modelOutput));
            Variant outer = plainVariant(ModelTemplates.STAIRS_OUTER.create(stairs, texturedModel.getMapping(), blockStateModelGenerator.modelOutput));

            blockStateModelGenerator.blockStateOutput.accept(BlockModelProvider
                    .createStairs(stairs, inner, regular, outer));
        }

        for (SimpleStairModel.Stair block : SimpleStairModel.vanillaStrippedStairs) {
            TexturedModel texturedModel = TexturedModel.createAllSame(ResourceLocation.fromNamespaceAndPath("minecraft", "block/" + BuiltInRegistries.BLOCK.getKey(block.origin()).getPath().replaceAll("_wood", "_log").replaceAll("_hyphae", "_stem").replaceAll("treated_log", "treated_wood").replaceAll("aged_log", "aged_wood")));
            Block stairs = block.stairs();

            Variant inner = plainVariant(ModelTemplates.STAIRS_INNER.create(stairs, texturedModel.getMapping(), blockStateModelGenerator.modelOutput));
            Variant regular = plainVariant(ModelTemplates.STAIRS_STRAIGHT.create(stairs, texturedModel.getMapping(), blockStateModelGenerator.modelOutput));
            Variant outer = plainVariant(ModelTemplates.STAIRS_OUTER.create(stairs, texturedModel.getMapping(), blockStateModelGenerator.modelOutput));

            blockStateModelGenerator.blockStateOutput.accept(BlockModelProvider
                    .createStairs(stairs, inner, regular, outer));
        }

        for (SimpleStairModel.Stair block : SimpleStairModel.vanillaStairs) {
            TexturedModel texturedModel = TexturedModel.CUBE.get(block.origin());
            Block stairs = block.stairs();

            Variant inner = plainVariant(ModelTemplates.STAIRS_INNER.create(stairs, texturedModel.getMapping(), blockStateModelGenerator.modelOutput));
            Variant regular = plainVariant(ModelTemplates.STAIRS_STRAIGHT.create(stairs, texturedModel.getMapping(), blockStateModelGenerator.modelOutput));
            Variant outer = plainVariant(ModelTemplates.STAIRS_OUTER.create(stairs, texturedModel.getMapping(), blockStateModelGenerator.modelOutput));

            blockStateModelGenerator.blockStateOutput.accept(BlockModelProvider
                    .createStairs(stairs, inner, regular, outer));
        }

        for (SimpleWallModel.Wall block : SimpleWallModel.blocks) {
            TexturedModel texturedModel = TexturedModel.createAllSame(ResourceLocation.fromNamespaceAndPath(
                    BuiltInRegistries.BLOCK.getKey(block.block()).getNamespace(), "block/" + BuiltInRegistries.BLOCK.getKey(block.block()).getPath().replaceAll("_wood","_log").replaceAll("treated_log", "treated_wood").replaceAll("aged_log", "aged_wood")));
            if (block.block() == Blocks.BASALT || block.block() == Blocks.POLISHED_BASALT) {
                texturedModel = TexturedModel.createAllSame(ResourceLocation.fromNamespaceAndPath("minecraft", "block/" + BuiltInRegistries.BLOCK.getKey(block.block()).getPath() + "_side"));
            }

            if (block.block() == Blocks.CRIMSON_HYPHAE || block.block() == Blocks.WARPED_HYPHAE) {
                texturedModel = TexturedModel.createAllSame(ResourceLocation.fromNamespaceAndPath("minecraft", "block/" + BuiltInRegistries.BLOCK.getKey(block.block()).getPath().replaceAll("_hyphae", "_stem")));
            }

            Block wall = block.wall();

            ResourceLocation inventory = ModelTemplates.WALL_INVENTORY.create(wall, texturedModel.getMapping(), blockStateModelGenerator.modelOutput);

            blockStateModelGenerator.registerSimpleItemModel(wall,inventory);

            Variant post = plainVariant(ModelTemplates.WALL_POST.create(wall, texturedModel.getMapping(), blockStateModelGenerator.modelOutput));
            Variant low = plainVariant(ModelTemplates.WALL_LOW_SIDE.create(wall, texturedModel.getMapping(), blockStateModelGenerator.modelOutput));
            Variant tall = plainVariant(ModelTemplates.WALL_TALL_SIDE.create(wall, texturedModel.getMapping(), blockStateModelGenerator.modelOutput));

            blockStateModelGenerator.blockStateOutput.accept(BlockModelProvider
                    .createWall(wall, post, low, tall));
        }

        for (SimpleWallModel.Wall block : SimpleWallModel.vanillaWalls) {
            TexturedModel texturedModel;
            if (BuiltInRegistries.BLOCK.getKey(block.block()).getPath().contains("waxed_") && BuiltInRegistries.BLOCK.getKey(block.block()).getPath().contains("cut_copper")) {
                texturedModel = TexturedModel.createAllSame(ResourceLocation.fromNamespaceAndPath("minecraft", "block/" + BuiltInRegistries.BLOCK.getKey(block.block()).getPath().replaceAll("waxed_", "")));
            } else {
                texturedModel = TexturedModel.createAllSame(ResourceLocation.fromNamespaceAndPath("minecraft", "block/" + BuiltInRegistries.BLOCK.getKey(block.block()).getPath()));

            }
            Block wall = block.wall();

            ResourceLocation inventory = ModelTemplates.WALL_INVENTORY.create(wall, texturedModel.getMapping(), blockStateModelGenerator.modelOutput);

            blockStateModelGenerator.registerSimpleItemModel(wall,inventory);

            Variant post = plainVariant(ModelTemplates.WALL_POST.create(wall, texturedModel.getMapping(), blockStateModelGenerator.modelOutput));
            Variant low = plainVariant(ModelTemplates.WALL_LOW_SIDE.create(wall, texturedModel.getMapping(), blockStateModelGenerator.modelOutput));
            Variant tall = plainVariant(ModelTemplates.WALL_TALL_SIDE.create(wall, texturedModel.getMapping(), blockStateModelGenerator.modelOutput));


            blockStateModelGenerator.blockStateOutput.accept(BlockModelProvider
                    .createWall(wall, post, low, tall));
        }

        for (SimpleWallModel.Wall block : SimpleWallModel.strippedWalls) {
            String modid = BuiltInRegistries.BLOCK.getKey(block.block()).getNamespace();

            TexturedModel texturedModel = TexturedModel.createAllSame(ResourceLocation.fromNamespaceAndPath(modid, "block/" + BuiltInRegistries.BLOCK.getKey(block.block()).getPath().replaceAll("_wood", "_log").replaceAll("_hyphae", "_stem").replaceAll("treated_log", "treated_wood").replaceAll("aged_log", "aged_wood")));
            Block wall = block.wall();

            ResourceLocation inventory = ModelTemplates.WALL_INVENTORY.create(wall, texturedModel.getMapping(), blockStateModelGenerator.modelOutput);

            blockStateModelGenerator.registerSimpleItemModel(wall, inventory);

            Variant post = plainVariant(ModelTemplates.WALL_POST.create(wall, texturedModel.getMapping(), blockStateModelGenerator.modelOutput));
            Variant low = plainVariant(ModelTemplates.WALL_LOW_SIDE.create(wall, texturedModel.getMapping(), blockStateModelGenerator.modelOutput));
            Variant tall = plainVariant(ModelTemplates.WALL_TALL_SIDE.create(wall, texturedModel.getMapping(), blockStateModelGenerator.modelOutput));

            blockStateModelGenerator.blockStateOutput.accept(BlockModelProvider
                    .createWall(wall, post, low, tall));
        }

        for (SimpleWallModel.Wall block : SimpleWallModel.vanillaStrippedWalls) {
            TexturedModel texturedModel = TexturedModel.createAllSame(ResourceLocation.fromNamespaceAndPath("minecraft", "block/" + BuiltInRegistries.BLOCK.getKey(block.block()).getPath().replaceAll("_wood", "_log").replaceAll("_hyphae", "_stem").replaceAll("treated_log", "treated_wood").replaceAll("aged_log", "aged_wood")));
            Block wall = block.wall();

            ResourceLocation inventory = ModelTemplates.WALL_INVENTORY.create(wall, texturedModel.getMapping(), blockStateModelGenerator.modelOutput);

            blockStateModelGenerator.registerSimpleItemModel(wall, inventory);

            Variant post = plainVariant(ModelTemplates.WALL_POST.create(wall, texturedModel.getMapping(), blockStateModelGenerator.modelOutput));
            Variant low = plainVariant(ModelTemplates.WALL_LOW_SIDE.create(wall, texturedModel.getMapping(), blockStateModelGenerator.modelOutput));
            Variant tall = plainVariant(ModelTemplates.WALL_TALL_SIDE.create(wall, texturedModel.getMapping(), blockStateModelGenerator.modelOutput));

            blockStateModelGenerator.blockStateOutput.accept(BlockModelProvider
                    .createWall(wall, post, low, tall));
        }

        for (SimpleWallModel.Wall block : SimpleWallModel.vanillaWoodWalls) {
            TexturedModel texturedModel = TexturedModel.createAllSame(ResourceLocation.fromNamespaceAndPath("minecraft", "block/" + BuiltInRegistries.BLOCK.getKey(block.block()).getPath().replaceAll("_wood", "_log").replaceAll("_hyphae", "_stem").replaceAll("treated_log", "treated_wood").replaceAll("aged_log", "aged_wood")));
            Block wall = block.wall();

            ResourceLocation inventory = ModelTemplates.WALL_INVENTORY.create(wall, texturedModel.getMapping(), blockStateModelGenerator.modelOutput);

            blockStateModelGenerator.registerSimpleItemModel(wall, inventory);

            Variant post = plainVariant(ModelTemplates.WALL_POST.create(wall, texturedModel.getMapping(), blockStateModelGenerator.modelOutput));
            Variant low = plainVariant(ModelTemplates.WALL_LOW_SIDE.create(wall, texturedModel.getMapping(), blockStateModelGenerator.modelOutput));
            Variant tall = plainVariant(ModelTemplates.WALL_TALL_SIDE.create(wall, texturedModel.getMapping(), blockStateModelGenerator.modelOutput));

            blockStateModelGenerator.blockStateOutput.accept(BlockModelProvider
                    .createWall(wall, post, low, tall));
        }

        for (SimpleFenceModel.Fence block : SimpleFenceModel.blocks) {
            TexturedModel texturedModel = TexturedModel.createAllSame(ResourceLocation.fromNamespaceAndPath(
                    BuiltInRegistries.BLOCK.getKey(block.block()).getNamespace(), "block/" + BuiltInRegistries.BLOCK.getKey(block.block()).getPath().replaceAll("_wood","_log")));
            if (block.block() == Blocks.CRIMSON_HYPHAE || block.block() == Blocks.WARPED_HYPHAE) {
                texturedModel = TexturedModel.createAllSame(ResourceLocation.fromNamespaceAndPath("minecraft", "block/" + BuiltInRegistries.BLOCK.getKey(block.block()).getPath().replaceAll("_hyphae", "_stem")));
            }
            Block fence = block.fence();

            Variant post = plainVariant(ModelTemplates.FENCE_POST.create(fence, texturedModel.getMapping(), blockStateModelGenerator.modelOutput));
            Variant side = plainVariant(ModelTemplates.FENCE_SIDE.create(fence, texturedModel.getMapping(), blockStateModelGenerator.modelOutput));
            ResourceLocation inventory = ModelTemplates.FENCE_INVENTORY.create(fence, texturedModel.getMapping(), blockStateModelGenerator.modelOutput);

            blockStateModelGenerator.blockStateOutput.accept(BlockModelProvider
                    .createFence(fence, post, side));

            blockStateModelGenerator.registerSimpleItemModel(fence, inventory);
        }

        for (SimpleFenceModel.Fence block : SimpleFenceModel.strippedFences) {
            String modid = BuiltInRegistries.BLOCK.getKey(block.block()).getNamespace();

            TexturedModel texturedModel = TexturedModel.createAllSame(ResourceLocation.fromNamespaceAndPath(modid, "block/" + BuiltInRegistries.BLOCK.getKey(block.block()).getPath().replaceAll("_wood", "_log").replaceAll("_hyphae", "_stem").replaceAll("treated_log", "treated_wood").replaceAll("aged_log", "aged_wood")));
            Block fence = block.fence();

            Variant post = plainVariant(ModelTemplates.FENCE_POST.create(fence, texturedModel.getMapping(), blockStateModelGenerator.modelOutput));
            Variant side = plainVariant(ModelTemplates.FENCE_SIDE.create(fence, texturedModel.getMapping(), blockStateModelGenerator.modelOutput));
            ResourceLocation inventory = ModelTemplates.FENCE_INVENTORY.create(fence, texturedModel.getMapping(), blockStateModelGenerator.modelOutput);

            blockStateModelGenerator.blockStateOutput.accept(BlockModelProvider
                    .createFence(fence, post, side));

            blockStateModelGenerator.registerSimpleItemModel(fence, inventory);
        }

        for (SimpleFenceModel.Fence block : SimpleFenceModel.vanillaStrippedFences) {
            TexturedModel texturedModel = TexturedModel.createAllSame(ResourceLocation.fromNamespaceAndPath("minecraft", "block/" + BuiltInRegistries.BLOCK.getKey(block.block()).getPath().replaceAll("_wood", "_log").replaceAll("_hyphae", "_stem").replaceAll("treated_log", "treated_wood").replaceAll("aged_log", "aged_wood")));
            Block fence = block.fence();

            Variant post = plainVariant(ModelTemplates.FENCE_POST.create(fence, texturedModel.getMapping(), blockStateModelGenerator.modelOutput));
            Variant side = plainVariant(ModelTemplates.FENCE_SIDE.create(fence, texturedModel.getMapping(), blockStateModelGenerator.modelOutput));
            ResourceLocation inventory = ModelTemplates.FENCE_INVENTORY.create(fence, texturedModel.getMapping(), blockStateModelGenerator.modelOutput);

            blockStateModelGenerator.blockStateOutput.accept(BlockModelProvider
                    .createFence(fence, post, side));

            blockStateModelGenerator.registerSimpleItemModel(fence, inventory);
        }

        for (SimpleFenceModel.Fence block : SimpleFenceModel.vanillaWoodFences) {
            TexturedModel texturedModel = TexturedModel.createAllSame(ResourceLocation.fromNamespaceAndPath("minecraft", "block/" + BuiltInRegistries.BLOCK.getKey(block.block()).getPath().replaceAll("_wood", "_log").replaceAll("_hyphae", "_stem").replaceAll("treated_log", "treated_wood").replaceAll("aged_log", "aged_wood")));
            Block fence = block.fence();

            Variant post = plainVariant(ModelTemplates.FENCE_POST.create(fence, texturedModel.getMapping(), blockStateModelGenerator.modelOutput));
            Variant side = plainVariant(ModelTemplates.FENCE_SIDE.create(fence, texturedModel.getMapping(), blockStateModelGenerator.modelOutput));
            ResourceLocation inventory = ModelTemplates.FENCE_INVENTORY.create(fence, texturedModel.getMapping(), blockStateModelGenerator.modelOutput);

            blockStateModelGenerator.blockStateOutput.accept(BlockModelProvider
                    .createFence(fence, post, side));

            blockStateModelGenerator.registerSimpleItemModel(fence, inventory);
        }

        for (SimpleFenceGateModel.FenceGate block : SimpleFenceGateModel.blocks) {
            TexturedModel texturedModel = TexturedModel.CUBE.get(block.block());
            Block fenceGate = block.fenceGate();

            Variant open = plainVariant(ModelTemplates.FENCE_GATE_OPEN.create(fenceGate, texturedModel.getMapping(), blockStateModelGenerator.modelOutput));
            Variant closed = plainVariant(ModelTemplates.FENCE_GATE_CLOSED.create(fenceGate, texturedModel.getMapping(), blockStateModelGenerator.modelOutput));
            Variant openWall = plainVariant(ModelTemplates.FENCE_GATE_WALL_OPEN.create(fenceGate, texturedModel.getMapping(), blockStateModelGenerator.modelOutput));
            Variant closedWall = plainVariant(ModelTemplates.FENCE_GATE_WALL_CLOSED.create(fenceGate, texturedModel.getMapping(), blockStateModelGenerator.modelOutput));

            blockStateModelGenerator.blockStateOutput.accept(BlockModelProvider
                    .createFenceGate(fenceGate, open, closed, openWall, closedWall, true));
        }

        for (SimpleButtonModel.Button block : SimpleButtonModel.buttons) {
            TexturedModel texturedModel = TexturedModel.CUBE.get(block.block());
            if (block.block() == Blocks.BASALT || block.block() == Blocks.POLISHED_BASALT) {
                texturedModel = TexturedModel.createAllSame(ResourceLocation.fromNamespaceAndPath("minecraft", "block/" + BuiltInRegistries.BLOCK.getKey(block.block()).getPath() + "_side"));
            }
            Block button = block.button();

            Variant unpressed = plainVariant(ModelTemplates.BUTTON.create(button, texturedModel.getMapping(), blockStateModelGenerator.modelOutput));
            Variant pressed = plainVariant(ModelTemplates.BUTTON_PRESSED.create(button, texturedModel.getMapping(), blockStateModelGenerator.modelOutput));

            ResourceLocation inventory = ModelTemplates.BUTTON_INVENTORY.create(button, texturedModel.getMapping(), blockStateModelGenerator.modelOutput);

            blockStateModelGenerator.blockStateOutput.accept(BlockModelProvider
                    .createButton(button, unpressed, pressed));

            blockStateModelGenerator.registerSimpleItemModel(button, inventory);
        }

        for (SimplePressurePlateModel.PressurePlate block : SimplePressurePlateModel.pressurePlates) {
            TexturedModel texturedModel = TexturedModel.CUBE.get(block.block());
            if (block.block() == Blocks.BASALT || block.block() == Blocks.POLISHED_BASALT) {
                texturedModel = TexturedModel.createAllSame(ResourceLocation.fromNamespaceAndPath("minecraft", "block/" + BuiltInRegistries.BLOCK.getKey(block.block()).getPath() + "_side"));
            }
            Block pressurePlate = block.pressurePlate();

            Variant up = plainVariant(ModelTemplates.PRESSURE_PLATE_UP.create(pressurePlate, texturedModel.getMapping(), blockStateModelGenerator.modelOutput));
            Variant down = plainVariant(ModelTemplates.PRESSURE_PLATE_DOWN.create(pressurePlate, texturedModel.getMapping(), blockStateModelGenerator.modelOutput));

            blockStateModelGenerator.blockStateOutput.accept(BlockModelProvider
                    .createPressurePlate(pressurePlate, up, down));
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
            blockStateModelGenerator.createDoor(door.door());
        }

        //TODO find out why some blocks are null here
        for (Block block : TintableCrossModel.notTintedBlocks()) {
            if (block != null
                    && block != ModNatureBlocks.PALE_OAK_SAPLING
                    && block != ModNatureBlocks.BUSH
                    && block != ModNatureBlocks.FIREFLY_BUSH
                    && block != ModNatureBlocks.SHORT_DRY_GRASS
                    && block != ModNatureBlocks.TALL_DRY_GRASS)
                blockStateModelGenerator.createCrossBlockWithDefaultItem(block, GenerationContext.PlantType.NOT_TINTED);
        }
        registerCrossBlockWithTexture(
                blockStateModelGenerator,
                ModNatureBlocks.BUSH,
                TextureMapping.getBlockTexture(ModNatureBlocks.LARGE_BUSH),
                GenerationContext.PlantType.TINTED);
        registerCrossBlockWithTexture(
                blockStateModelGenerator,
                ModNatureBlocks.FIREFLY_BUSH,
                TextureMapping.getBlockTexture(ModNatureBlocks.TOUGH_BERRY_BUSH, "_stage3"));
        registerCrossBlockWithTexture(
                blockStateModelGenerator,
                ModNatureBlocks.SHORT_DRY_GRASS,
                TextureMapping.getBlockTexture(ModNatureBlocks.DEAD_HEATHER));
        registerCrossBlockWithTexture(
                blockStateModelGenerator,
                ModNatureBlocks.TALL_DRY_GRASS,
                TextureMapping.getBlockTexture(ModNatureBlocks.DEAD_HEATHER_BUSH));
        registerCrossBlockWithTexture(
                blockStateModelGenerator,
                ModNatureBlocks.PALE_OAK_SAPLING,
                TextureMapping.getBlockTexture(Blocks.DARK_OAK_SAPLING));

        blockStateModelGenerator.createGrowingPlant(ModNatureBlocks.GLOWWORM_WEBBING, ModNatureBlocks.GLOWWORM_MAIN, GenerationContext.PlantType.NOT_TINTED);
        blockStateModelGenerator.registerSimpleFlatItemModel(ModNatureBlocks.GLOWWORM_WEBBING);

        for (Block block : TintableCrossModel.tintedBlocks()) {
            blockStateModelGenerator.createCrossBlock(block, GenerationContext.PlantType.TINTED);
            blockStateModelGenerator.registerSimpleTintedItemModel(block, blockStateModelGenerator.createFlatItemModelWithBlockTexture(block.asItem(), block), null);
        }

        for (Block block : TintableCrossModel.grassLikeBlocks()) {
            blockStateModelGenerator.createCrossBlockWithDefaultItem(block, GenerationContext.PlantType.NOT_TINTED);
        }

        for (Block block : TintableCrossModel.largePlants()) {
            registerLargePlant(blockStateModelGenerator, block);
        }

        registerTintableLargePlant(blockStateModelGenerator, ModNatureBlocks.LARGE_BUSH);
        registerTintableLargePlant(blockStateModelGenerator, ModNatureBlocks.WILD_GRASS);

        for (Block block : SimpleFlowerBedModel.flowerBeds) {
            blockStateModelGenerator.createFlowerBed(block);
        }

        for (SimpleFlowerPotModel.FlowerPot flowerPot : SimpleFlowerPotModel.pots) {
            registerFlowerPotPlant(blockStateModelGenerator, flowerPot.plant(), flowerPot.pottedPlant(), GenerationContext.PlantType.NOT_TINTED);
        }

        for (Block block : SimpleDoubleBlockModel.doubleBlocks) {
            registerDoubleBlock(blockStateModelGenerator, block, GenerationContext.PlantType.NOT_TINTED);
        }

        for (Block block : SimpleDoubleBlockModel.doubleBlocksItems) {
            registerDoubleBlock(blockStateModelGenerator, block, GenerationContext.PlantType.NOT_TINTED);
        }

        for (Block block : SimpleMushroomBlockModel.mushroomBlocks) {
            blockStateModelGenerator.createMushroomBlock(block);
        }

        for (SimpleVerticalSlabModel.VerticalSlab verticalSlab : SimpleVerticalSlabModel.verticalSlabs) {
            String id = BuiltInRegistries.BLOCK.getKey(verticalSlab.block()).getPath();
            if (verticalSlab.block() == Blocks.BASALT || verticalSlab.block() == Blocks.POLISHED_BASALT) {
                id = id + "_side";
            }
            registerVerticalSlabModelBlockStates(blockStateModelGenerator, verticalSlab.verticalSlab(), verticalSlab.block(), id);
        }

        for (SimpleVerticalSlabModel.VerticalSlab verticalSlab : SimpleVerticalSlabModel.woodVerticalSlabs) {
            registerVerticalSlabModelBlockStates(blockStateModelGenerator, verticalSlab.verticalSlab(), verticalSlab.block(),
                    BuiltInRegistries.BLOCK.getKey(verticalSlab.block()).getPath().replaceAll("_wood", "_log").replaceAll("_hyphae", "_stem").replaceAll("treated_log", "treated_wood").replaceAll("aged_log", "aged_wood"));
        }

        for (SimpleVerticalSlabModel.VerticalSlab verticalSlab : SimpleVerticalSlabModel.strippedVerticalSlabs) {
            registerVerticalSlabModelBlockStates(blockStateModelGenerator, verticalSlab.verticalSlab(), verticalSlab.block(),
                    BuiltInRegistries.BLOCK.getKey(verticalSlab.block()).getPath().replaceAll("_wood", "_log").replaceAll("_hyphae", "_stem").replaceAll("treated_log", "treated_wood").replaceAll("aged_log", "aged_wood"));
        }

        for (SimpleVerticalSlabModel.VerticalSlab verticalSlab : SimpleVerticalSlabModel.plansVerticalSlabs) {
            registerVerticalSlabModelBlockStates(blockStateModelGenerator, verticalSlab.verticalSlab(), verticalSlab.block(), BuiltInRegistries.BLOCK.getKey(verticalSlab.block()).getPath());
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
            String id = "block/" + BuiltInRegistries.BLOCK.getKey(stool.base()).getPath();
            if (stool.base() == Blocks.BASALT) id += "_side";
            registerStoneStoolModelBlockStates(blockStateModelGenerator, stool.stool(),
                    ResourceLocation.fromNamespaceAndPath(BuiltInRegistries.BLOCK.getKey(stool.base()).getNamespace(), id));
        }

        for(SimpleStoneTableModel.Table table : SimpleStoneTableModel.tables) {
            String id = "block/" + BuiltInRegistries.BLOCK.getKey(table.base()).getPath();
            if(table.base() == Blocks.BASALT) id += "_side";
            registerStoneTableModelBlockStates(blockStateModelGenerator, table.table(), ResourceLocation.fromNamespaceAndPath(BuiltInRegistries.BLOCK.getKey(table.base()).getNamespace(), id));
        }

        for(SimpleStoneChairModel.Chair chair : SimpleStoneChairModel.chairs){
            String id = "block/" + BuiltInRegistries.BLOCK.getKey(chair.base()).getPath();
            if(chair.base() == Blocks.BASALT) id += "_side";
            registerStoneChairModelBlockStates(blockStateModelGenerator, chair.chair(), ResourceLocation.fromNamespaceAndPath(BuiltInRegistries.BLOCK.getKey(chair.base()).getNamespace(), id));
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
            String id = String.valueOf(BuiltInRegistries.BLOCK.getKey(verticalSlab.block()));
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
            ResourceLocation identifier = BuiltInRegistries.BLOCK.getKey(verticalSlab.verticalSlab());
            String sidePath = identifier.getPath().replaceAll("_vertical_slab", "");

            ResourceLocation identifier2 = BuiltInRegistries.BLOCK.getKey(verticalSlab.verticalSlab()).withSuffix("_top");
            String topBottomPath = identifier2.getPath().replaceAll("_vertical_slab", "");
            topBottomPath = topBottomPath.replaceAll("_carved_window_top", "");

            registerColumnVerticalSlabModelBlockStates(blockStateModelGenerator, verticalSlab.verticalSlab(), verticalSlab.block(),
                    identifier.getNamespace(), topBottomPath, topBottomPath, sidePath);
        }

        for (SimpleWallModel.Wall wall : SimpleWallModel.columnWalls) {
            ResourceLocation identifier = BuiltInRegistries.BLOCK.getKey(wall.wall());
            String sidePath = identifier.getPath().replaceAll("_wall", "");

            ResourceLocation identifier2 = BuiltInRegistries.BLOCK.getKey(wall.wall()).withSuffix("_top");
            String topBottomPath = identifier2.getPath().replaceAll("_wall", "");

            registerColumnWallModelBlockStates(blockStateModelGenerator, wall.wall(), wall.block(),
                    identifier.getNamespace(), topBottomPath, topBottomPath, sidePath);
        }

        for (SimpleVerticalSlabModel.VerticalSlab verticalSlab : SimpleVerticalSlabModel.vanillaWoodVerticalSlabs) {
            String id = BuiltInRegistries.BLOCK.getKey(verticalSlab.block()).getPath();
            String baseTextureId = id.substring(0, id.lastIndexOf("_")) + "_log";
            baseTextureId = baseTextureId.replaceAll("_wood", "_log").replaceAll("_hyphae", "_stem").replaceAll("treated_log", "treated_wood").replaceAll("aged_log", "aged_wood");
            registerVanillaVerticalSlabModelBlockStates(blockStateModelGenerator, verticalSlab.verticalSlab(), verticalSlab.block(), baseTextureId);
        }
        for (SimpleVerticalSlabModel.VerticalSlab verticalSlab : SimpleVerticalSlabModel.vanillaStrippedVerticalSlabs) {
            String id = BuiltInRegistries.BLOCK.getKey(verticalSlab.block()).getPath();
            String baseTextureId = id.substring(0, id.lastIndexOf("_")) + "_log";
            baseTextureId = baseTextureId.replaceAll("_wood", "_log").replaceAll("_hyphae", "_stem").replaceAll("treated_log", "treated_wood").replaceAll("aged_log", "aged_wood");
            registerVanillaVerticalSlabModelBlockStates(blockStateModelGenerator, verticalSlab.verticalSlab(), verticalSlab.block(), baseTextureId);
        }

        SimpleTopWaterModel.topWaterBlocks.forEach(block -> {
            registerTopWaterblock(blockStateModelGenerator, block);
        });

        // Crops
        blockStateModelGenerator.createCropBlock(ModNatureBlocks.BELL_PEPPER_CROP, BellpepperCropBlock.AGE, 0, 1, 2, 3, 4);
        blockStateModelGenerator.createCropBlock(ModNatureBlocks.CUCUMBER_CROP, CucumberCropBlock.AGE, 0, 1, 2, 3);
        blockStateModelGenerator.createCropBlock(ModNatureBlocks.FLAX_CROP, FlaxCropBlock.AGE, 0, 1, 2, 3);
        blockStateModelGenerator.createCropBlock(ModNatureBlocks.GARLIC_CROP, GarlicCropBlock.AGE, 0, 1, 2, 3);
        blockStateModelGenerator.createCropBlock(ModNatureBlocks.LEEK_CROP, LeekCropBlock.AGE, 0, 1, 2, 3);
        blockStateModelGenerator.createCropBlock(ModNatureBlocks.LETTUCE_CROP, LettuceCropBlock.AGE, 0, 1, 2, 3);
        blockStateModelGenerator.createCropBlock(ModNatureBlocks.ONION_CROP, OnionCropBlock.AGE, 0, 1, 2, 3);

        //CLUSTERS
        blockStateModelGenerator.createAmethystCluster(ModBlocks.GLOWSTONE_CLUSTER);
        blockStateModelGenerator.createAmethystCluster(ModBlocks.SMALL_GLOWSTONE_BUD);
        blockStateModelGenerator.createAmethystCluster(ModBlocks.MEDIUM_GLOWSTONE_BUD);
        blockStateModelGenerator.createAmethystCluster(ModBlocks.LARGE_GLOWSTONE_BUD);
        blockStateModelGenerator.createAmethystCluster(ModBlocks.RED_AGATE_CLUSTER);
        blockStateModelGenerator.createAmethystCluster(ModBlocks.SMALL_RED_AGATE_BUD);
        blockStateModelGenerator.createAmethystCluster(ModBlocks.MEDIUM_RED_AGATE_BUD);
        blockStateModelGenerator.createAmethystCluster(ModBlocks.LARGE_RED_AGATE_BUD);
        blockStateModelGenerator.createAmethystCluster(ModBlocks.CITRINE_CLUSTER);
        blockStateModelGenerator.createAmethystCluster(ModBlocks.SMALL_CITRINE_BUD);
        blockStateModelGenerator.createAmethystCluster(ModBlocks.MEDIUM_CITRINE_BUD);
        blockStateModelGenerator.createAmethystCluster(ModBlocks.LARGE_CITRINE_BUD);
        blockStateModelGenerator.createAmethystCluster(ModBlocks.QUARTZ_CLUSTER);
        blockStateModelGenerator.createAmethystCluster(ModBlocks.SMALL_QUARTZ_BUD);
        blockStateModelGenerator.createAmethystCluster(ModBlocks.MEDIUM_QUARTZ_BUD);
        blockStateModelGenerator.createAmethystCluster(ModBlocks.LARGE_QUARTZ_BUD);

        registerLargeDoor(blockStateModelGenerator, (LargeDoorBlock) ModDecorativeBlocks.BLUE_HOBBIT_DOOR, LargeDoor2x2.PART);
        registerLargeDoor(blockStateModelGenerator, (LargeDoorBlock) ModDecorativeBlocks.GREEN_HOBBIT_DOOR, LargeDoor2x2.PART);
        registerLargeDoor(blockStateModelGenerator, (LargeDoorBlock) ModDecorativeBlocks.LIGHT_BLUE_HOBBIT_DOOR, LargeDoor2x2.PART);
        registerLargeDoor(blockStateModelGenerator, (LargeDoorBlock) ModDecorativeBlocks.RED_HOBBIT_DOOR, LargeDoor2x2.PART);
        registerLargeDoor(blockStateModelGenerator, (LargeDoorBlock) ModDecorativeBlocks.YELLOW_HOBBIT_DOOR, LargeDoor2x2.PART);
        registerLargeDoor(blockStateModelGenerator, (LargeDoorBlock) ModDecorativeBlocks.LARCH_HOBBIT_DOOR, LargeDoor2x2.PART);
        registerLargeDoor(blockStateModelGenerator, (LargeDoorBlock) ModDecorativeBlocks.SPRUCE_HOBBIT_DOOR, LargeDoor2x2.PART);

        registerLargeDoor(blockStateModelGenerator, (LargeDoorBlock) ModDecorativeBlocks.TALL_BLACK_PINE_DOOR, LargeDoor3x1.PART);
        registerLargeDoor(blockStateModelGenerator, (LargeDoorBlock) ModDecorativeBlocks.TALL_FIR_DOOR, LargeDoor3x1.PART);

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
        registerPaneModel(blockStateModelGenerator, ModBlocks.BURZUM_BARS);
        registerPaneModel(blockStateModelGenerator, ModBlocks.SILVER_BARS);

        registerOrientableThickLadder(blockStateModelGenerator, ModDecorativeBlocks.ROPE_LADDER);

        blockStateModelGenerator.createMultiface(ModNatureBlocks.AZALEA_FLOWER_GROWTH);
        blockStateModelGenerator.createMultiface(ModNatureBlocks.DRY_GROWTH);
        blockStateModelGenerator.createMultiface(ModNatureBlocks.FROZEN_GROWTH);
        blockStateModelGenerator.createMultiface(ModNatureBlocks.GREEN_GROWTH);
        blockStateModelGenerator.createMultiface(ModNatureBlocks.IVY_GROWTH);
        blockStateModelGenerator.createMultiface(ModNatureBlocks.LILAC_FLOWER_GROWTH);
        blockStateModelGenerator.createMultiface(ModNatureBlocks.PINK_FLOWER_GROWTH);
        blockStateModelGenerator.createMultiface(ModNatureBlocks.RED_FLOWER_GROWTH);
        blockStateModelGenerator.createMultiface(ModNatureBlocks.THORNY_GROWTH);
        blockStateModelGenerator.createMultiface(ModNatureBlocks.WHITE_FLOWER_GROWTH);
        blockStateModelGenerator.createMultiface(ModNatureBlocks.YELLOW_FLOWER_GROWTH);

        blockStateModelGenerator.createMultiface(ModNatureBlocks.WEBBING);

        blockStateModelGenerator.createMultiface(ModNatureBlocks.MOSS);
        registerMultifaceBlock(blockStateModelGenerator, ModNatureBlocks.FOREST_MOSS);
        blockStateModelGenerator.createMultiface(ModNatureBlocks.CORRUPTED_MOSS);

        blockStateModelGenerator.createMultiface(ModNatureBlocks.MORGUL_IVY);

        blockStateModelGenerator.createMultiface(ModNatureBlocks.STICKY_SNOW);
        blockStateModelGenerator.createMultiface(ModNatureBlocks.STICKY_ICE);

        registerPointedBlock(blockStateModelGenerator, ModBlocks.POINTED_DOLOMITE);
        registerPointedBlock(blockStateModelGenerator, ModBlocks.POINTED_GALONN);
        registerPointedBlock(blockStateModelGenerator, ModBlocks.POINTED_LIMESTONE);
        registerPointedBlock(blockStateModelGenerator, ModBlocks.POINTED_IZHERABAN);

        registerHangingMoss(blockStateModelGenerator, ModNatureBlocks.WILLOW_VINES);

        registerHangingMoss(blockStateModelGenerator, ModNatureBlocks.MIRKWOOD_VINES);
        registerHangingMoss(blockStateModelGenerator, ModNatureBlocks.HANGING_WEBS);

        registerFarmland(blockStateModelGenerator, ModBlocks.CHALKSOIL, ModBlocks.CHALKSOIL_FARMLAND);
        registerFarmland(blockStateModelGenerator, ModBlocks.LOAM, ModBlocks.LOAM_FARMLAND);
        registerFarmland(blockStateModelGenerator, ModBlocks.PEAT, ModBlocks.PEAT_FARMLAND);
        registerFarmland(blockStateModelGenerator, ModBlocks.SILT, ModBlocks.SILT_FARMLAND);

        registerDirtPath(blockStateModelGenerator, ModBlocks.CHALKSOIL, ModBlocks.CHALKSOIL_PATH);
        registerDirtPath(blockStateModelGenerator, ModBlocks.LOAM, ModBlocks.LOAM_PATH);
        registerDirtPath(blockStateModelGenerator, ModBlocks.PEAT, ModBlocks.PEAT_PATH);
        registerDirtPath(blockStateModelGenerator, ModBlocks.SILT, ModBlocks.SILT_PATH);
    }

    public final void registerFanModel(GenerationContext blockStateCollector, Block coralFanBlock) {
        TexturedModel texturedModel = TexturedModel.CORAL_FAN.get(coralFanBlock);
        ResourceLocation identifier = texturedModel.create(coralFanBlock, blockStateCollector.modelOutput);
        Variant weightedVariant = plainVariant(identifier);
        blockStateCollector.blockStateOutput.accept(BlockModelProvider.createSimpleBlock(coralFanBlock, weightedVariant));
        blockStateCollector.registerSimpleFlatItemModel(coralFanBlock);
    }

    public final void registerTintableLargePlant(GenerationContext blockStateModelGenerator, Block plantBlock) {
        ResourceLocation identifier = MEModels.TINTED_LARGE_PLANT.create(plantBlock, TextureMapping.singleSlot(TextureSlot.ALL, ResourceLocation.fromNamespaceAndPath(BuiltInRegistries.BLOCK.getKey(plantBlock).getNamespace(), "block/" + BuiltInRegistries.BLOCK.getKey(plantBlock).getPath())), blockStateModelGenerator.modelOutput);
        Variant weightedVariant = plainVariant(identifier);
        blockStateModelGenerator.blockStateOutput.accept(BlockModelProvider.createSimpleBlock(plantBlock, weightedVariant));
        blockStateModelGenerator.registerSimpleTintedItemModel(plantBlock, ModelTemplates.FLAT_ITEM.create(ModelLocationUtils.getModelLocation(plantBlock.asItem()), TextureMapping.layer0(plantBlock.asItem()), blockStateModelGenerator.modelOutput), null);
    }

    public final void registerLargePlant(GenerationContext blockStateModelGenerator, Block plantBlock) {
        ResourceLocation identifier = MEModels.LARGE_PLANT.create(plantBlock, TextureMapping.singleSlot(TextureSlot.ALL,ResourceLocation.fromNamespaceAndPath(BuiltInRegistries.BLOCK.getKey(plantBlock).getNamespace(), "block/" + BuiltInRegistries.BLOCK.getKey(plantBlock).getPath())), blockStateModelGenerator.modelOutput);
        Variant weightedVariant = plainVariant(identifier);
        blockStateModelGenerator.blockStateOutput.accept(BlockModelProvider.createSimpleBlock(plantBlock, weightedVariant));
        blockStateModelGenerator.registerSimpleFlatItemModel(plantBlock.asItem());
    }

    public final void registerFlowerPotPlant(GenerationContext blockStateModelGenerator, Block plantBlock, Block flowerPotBlock, GenerationContext.PlantType tintType) {
        TextureMapping textureMap = TextureMapping.plant(plantBlock);
        ResourceLocation identifier = tintType.getCrossPot().create(flowerPotBlock, textureMap, blockStateModelGenerator.modelOutput);
        Variant weightedVariant = plainVariant(identifier);
        blockStateModelGenerator.blockStateOutput.accept(BlockModelProvider.createSimpleBlock(flowerPotBlock, weightedVariant));
    }

    public void registerVanillaVerticalSlabModelBlockStates(GenerationContext blockStateModelGenerator, Block block, Block origin, String slabPath) {
        ResourceLocation fullBlockId = ModelLocationUtils.getModelLocation(origin);

        Variant variantId = plainVariant(MEModels.VERTICAL_SLAB.create(block,
                TextureMapping.singleSlot(TextureSlot.ALL, ResourceLocation.fromNamespaceAndPath("minecraft", "block/" + slabPath)),
                blockStateModelGenerator.modelOutput));

        Variant inner = plainVariant(MEModels.VERTICAL_SLAB_INNER.create(block, TextureMapping.singleSlot(TextureSlot.ALL, ResourceLocation.fromNamespaceAndPath("minecraft", "block/" + slabPath)), blockStateModelGenerator.modelOutput));
        Variant outer = plainVariant(MEModels.VERTICAL_SLAB_OUTER.create(block, TextureMapping.singleSlot(TextureSlot.ALL, ResourceLocation.fromNamespaceAndPath("minecraft", "block/" + slabPath)), blockStateModelGenerator.modelOutput));

        registerVerticalSlab(blockStateModelGenerator, block, fullBlockId, variantId, inner, outer);
    }

    public void registerVerticalSlabModelBlockStates(GenerationContext blockStateModelGenerator, Block verticalSlab, Block block, String slabPath) {
        ResourceLocation fullBlockId = ModelLocationUtils.getModelLocation(block);

        Variant variantId = plainVariant(MEModels.VERTICAL_SLAB.create(verticalSlab,
                TextureMapping.singleSlot(TextureSlot.ALL, ResourceLocation.fromNamespaceAndPath(BuiltInRegistries.BLOCK.getKey(block).getNamespace(), "block/" + slabPath)),
                blockStateModelGenerator.modelOutput));

        Variant inner = plainVariant(MEModels.VERTICAL_SLAB_INNER.create(verticalSlab, TextureMapping.singleSlot(TextureSlot.ALL, ResourceLocation.fromNamespaceAndPath(BuiltInRegistries.BLOCK.getKey(block).getNamespace(), "block/" + slabPath)), blockStateModelGenerator.modelOutput));
        Variant outer = plainVariant(MEModels.VERTICAL_SLAB_OUTER.create(verticalSlab, TextureMapping.singleSlot(TextureSlot.ALL, ResourceLocation.fromNamespaceAndPath(BuiltInRegistries.BLOCK.getKey(block).getNamespace(), "block/" + slabPath)), blockStateModelGenerator.modelOutput));

        registerVerticalSlab(blockStateModelGenerator, verticalSlab, fullBlockId, variantId, inner, outer);
    }

    public void registerColumnWallModelBlockStates(GenerationContext blockStateModelGenerator, Block block, Block origin,
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

        ResourceLocation sideTexture = ResourceLocation.fromNamespaceAndPath(modId, "block/" + sideTexturePath);

        Variant post = plainVariant(MEModels.COLUMN_WALL_POST.create(block, (new TextureMapping())
                        .put(TextureSlot.TOP, ResourceLocation.fromNamespaceAndPath(modIdTopBottom, "block/" + topTexturePath))
                        .put(TextureSlot.BOTTOM, ResourceLocation.fromNamespaceAndPath(modIdTopBottom, "block/" + bottomTexturePath))
                        .put(TextureSlot.WALL, sideTexture)
                        .put(TextureSlot.PARTICLE, sideTexture),
                blockStateModelGenerator.modelOutput));

        Variant low = plainVariant(MEModels.COLUMN_WALL_SIDE.create(block, (new TextureMapping())
                        .put(TextureSlot.TOP, ResourceLocation.fromNamespaceAndPath(modIdTopBottom, "block/" + topTexturePath))
                        .put(TextureSlot.BOTTOM, ResourceLocation.fromNamespaceAndPath(modIdTopBottom, "block/" + bottomTexturePath))
                        .put(TextureSlot.WALL, sideTexture)
                        .put(TextureSlot.PARTICLE, sideTexture),
                blockStateModelGenerator.modelOutput));

        Variant tall = plainVariant(MEModels.COLUMN_WALL_SIDE_TALL.create(block, (new TextureMapping())
                        .put(TextureSlot.TOP, ResourceLocation.fromNamespaceAndPath(modIdTopBottom, "block/" + topTexturePath))
                        .put(TextureSlot.BOTTOM, ResourceLocation.fromNamespaceAndPath(modIdTopBottom, "block/" + bottomTexturePath))
                        .put(TextureSlot.WALL, sideTexture)
                        .put(TextureSlot.PARTICLE, sideTexture),
                blockStateModelGenerator.modelOutput));

        blockStateModelGenerator.blockStateOutput.accept(BlockModelProvider
                .createWall(block, post, low, tall));

        ResourceLocation inventory = MEModels.COLUMN_WALL_INVENTORY.create(block, (new TextureMapping())
                .put(TextureSlot.TOP, ResourceLocation.fromNamespaceAndPath(modIdTopBottom, "block/" + topTexturePath))
                .put(TextureSlot.BOTTOM, ResourceLocation.fromNamespaceAndPath(modIdTopBottom, "block/" + bottomTexturePath))
                .put(TextureSlot.WALL, sideTexture)
                .put(TextureSlot.PARTICLE, sideTexture), blockStateModelGenerator.modelOutput);

        blockStateModelGenerator.registerSimpleItemModel(block, inventory);
    }

    public void registerColumnVerticalSlabModelBlockStates(GenerationContext blockStateModelGenerator, Block block, Block origin,
                                                           String modId, String topTexturePath, String bottomTexturePath, String sideTexturePath) {
        ResourceLocation fullBlockId = ModelLocationUtils.getModelLocation(origin);
        String modIdTopBottom = modId;
        if (sideTexturePath.contains("deepslate_carved_window") || sideTexturePath.contains("calcite_carved_window")){
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

        ResourceLocation sideTexture = ResourceLocation.fromNamespaceAndPath(modId, "block/" + sideTexturePath);

        Variant variantId = plainVariant(MEModels.VERTICAL_COLUMN_SLAB.create(block, (new TextureMapping())
                        .put(TextureSlot.TOP, ResourceLocation.fromNamespaceAndPath(modIdTopBottom, "block/" + topTexturePath))
                        .put(TextureSlot.BOTTOM, ResourceLocation.fromNamespaceAndPath(modIdTopBottom, "block/" + bottomTexturePath))
                        .put(TextureSlot.SIDE, sideTexture)
                        .put(TextureSlot.PARTICLE, sideTexture),
                blockStateModelGenerator.modelOutput));

        Variant inner = plainVariant(MEModels.VERTICAL_COLUMN_SLAB_INNER.create(block, (new TextureMapping())
                        .put(TextureSlot.TOP, ResourceLocation.fromNamespaceAndPath(modIdTopBottom, "block/" + topTexturePath))
                        .put(TextureSlot.BOTTOM, ResourceLocation.fromNamespaceAndPath(modIdTopBottom, "block/" + bottomTexturePath))
                        .put(TextureSlot.SIDE, sideTexture)
                        .put(TextureSlot.PARTICLE, sideTexture),
                blockStateModelGenerator.modelOutput));

        Variant outer = plainVariant(MEModels.VERTICAL_COLUMN_SLAB_OUTER.create(block, (new TextureMapping())
                        .put(TextureSlot.TOP, ResourceLocation.fromNamespaceAndPath(modIdTopBottom, "block/" + topTexturePath))
                        .put(TextureSlot.BOTTOM, ResourceLocation.fromNamespaceAndPath(modIdTopBottom, "block/" + bottomTexturePath))
                        .put(TextureSlot.SIDE, sideTexture)
                        .put(TextureSlot.PARTICLE, sideTexture),
                blockStateModelGenerator.modelOutput));
        registerVerticalSlab(blockStateModelGenerator, block, fullBlockId, variantId, inner, outer);
    }

    private void registerVerticalSlab(GenerationContext blockStateModelGenerator, Block block, ResourceLocation fullBlock, Variant regular, Variant inner, Variant outer) {
        Variant fullBlockVariant;
        if (BuiltInRegistries.BLOCK.getKey(block).getPath().contains("waxed_") && BuiltInRegistries.BLOCK.getKey(block).getPath().contains("copper")) {
            fullBlockVariant = plainVariant(ResourceLocation.withDefaultNamespace(fullBlock.getPath().replaceAll("waxed_", "")));
        }  else if (fullBlock.getNamespace().contains("minecraft")) {
            fullBlockVariant = plainVariant(ResourceLocation.withDefaultNamespace(fullBlock.getPath()));
        }else {
            fullBlockVariant = plainVariant(ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, fullBlock.getPath()));
        }

        MultiVariantGenerator blockstate = MultiVariantGenerator.multiVariant(block).with(
                PropertyDispatch.properties(BlockStateProperties.HORIZONTAL_FACING, VerticalSlabBlock.DOUBLE, VerticalSlabBlock.SHAPE)
                        .select(Direction.EAST, false, VerticalSlabShape.STRAIGHT, copyVariant(regular).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90).with(VariantProperties.UV_LOCK, true))
                        .select(Direction.WEST, false, VerticalSlabShape.STRAIGHT, copyVariant(regular).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270).with(VariantProperties.UV_LOCK, true))
                        .select(Direction.SOUTH, false, VerticalSlabShape.STRAIGHT, copyVariant(regular).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180).with(VariantProperties.UV_LOCK, true))
                        .select(Direction.NORTH, false, VerticalSlabShape.STRAIGHT, copyVariant(regular).with(VariantProperties.UV_LOCK, true))
                        .select(Direction.EAST, false, VerticalSlabShape.OUTER_RIGHT, copyVariant(outer).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180).with(VariantProperties.UV_LOCK, true))
                        .select(Direction.WEST, false, VerticalSlabShape.OUTER_RIGHT, copyVariant(outer).with(VariantProperties.UV_LOCK, true))
                        .select(Direction.SOUTH, false, VerticalSlabShape.OUTER_RIGHT, copyVariant(outer).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270).with(VariantProperties.UV_LOCK, true))
                        .select(Direction.NORTH, false, VerticalSlabShape.OUTER_RIGHT, copyVariant(outer).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90).with(VariantProperties.UV_LOCK, true))
                        .select(Direction.EAST, false, VerticalSlabShape.OUTER_LEFT, copyVariant(outer).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90).with(VariantProperties.UV_LOCK, true))
                        .select(Direction.WEST, false, VerticalSlabShape.OUTER_LEFT, copyVariant(outer).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270).with(VariantProperties.UV_LOCK, true))
                        .select(Direction.SOUTH, false, VerticalSlabShape.OUTER_LEFT, copyVariant(outer).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180).with(VariantProperties.UV_LOCK, true))
                        .select(Direction.NORTH, false, VerticalSlabShape.OUTER_LEFT, copyVariant(outer).with(VariantProperties.UV_LOCK, true))
                        .select(Direction.EAST, false, VerticalSlabShape.INNER_RIGHT, copyVariant(inner).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180).with(VariantProperties.UV_LOCK, true))
                        .select(Direction.WEST, false, VerticalSlabShape.INNER_RIGHT, copyVariant(inner).with(VariantProperties.UV_LOCK, true))
                        .select(Direction.SOUTH, false, VerticalSlabShape.INNER_RIGHT, copyVariant(inner).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270).with(VariantProperties.UV_LOCK, true))
                        .select(Direction.NORTH, false, VerticalSlabShape.INNER_RIGHT, copyVariant(inner).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90).with(VariantProperties.UV_LOCK, true))
                        .select(Direction.EAST, false, VerticalSlabShape.INNER_LEFT, copyVariant(inner).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90).with(VariantProperties.UV_LOCK, true))
                        .select(Direction.WEST, false, VerticalSlabShape.INNER_LEFT, copyVariant(inner).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270).with(VariantProperties.UV_LOCK, true))
                        .select(Direction.SOUTH, false, VerticalSlabShape.INNER_LEFT, copyVariant(inner).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180).with(VariantProperties.UV_LOCK, true))
                        .select(Direction.NORTH, false, VerticalSlabShape.INNER_LEFT, copyVariant(inner).with(VariantProperties.UV_LOCK, true))

                        .select(Direction.EAST, true, VerticalSlabShape.STRAIGHT, copyVariant(fullBlockVariant).with(VariantProperties.UV_LOCK, true))
                        .select(Direction.WEST, true, VerticalSlabShape.STRAIGHT, copyVariant(fullBlockVariant).with(VariantProperties.UV_LOCK, true))
                        .select(Direction.SOUTH, true, VerticalSlabShape.STRAIGHT, copyVariant(fullBlockVariant).with(VariantProperties.UV_LOCK, true))
                        .select(Direction.NORTH, true, VerticalSlabShape.STRAIGHT, copyVariant(fullBlockVariant).with(VariantProperties.UV_LOCK, true))
                        .select(Direction.EAST, true, VerticalSlabShape.OUTER_RIGHT, copyVariant(fullBlockVariant).with(VariantProperties.UV_LOCK, true))
                        .select(Direction.WEST, true, VerticalSlabShape.OUTER_RIGHT, copyVariant(fullBlockVariant).with(VariantProperties.UV_LOCK, true))
                        .select(Direction.SOUTH, true, VerticalSlabShape.OUTER_RIGHT, copyVariant(fullBlockVariant).with(VariantProperties.UV_LOCK, true))
                        .select(Direction.NORTH, true, VerticalSlabShape.OUTER_RIGHT, copyVariant(fullBlockVariant).with(VariantProperties.UV_LOCK, true))
                        .select(Direction.EAST, true, VerticalSlabShape.OUTER_LEFT, copyVariant(fullBlockVariant).with(VariantProperties.UV_LOCK, true))
                        .select(Direction.WEST, true, VerticalSlabShape.OUTER_LEFT, copyVariant(fullBlockVariant).with(VariantProperties.UV_LOCK, true))
                        .select(Direction.SOUTH, true, VerticalSlabShape.OUTER_LEFT, copyVariant(fullBlockVariant).with(VariantProperties.UV_LOCK, true))
                        .select(Direction.NORTH, true, VerticalSlabShape.OUTER_LEFT, copyVariant(fullBlockVariant).with(VariantProperties.UV_LOCK, true))
                        .select(Direction.EAST, true, VerticalSlabShape.INNER_RIGHT, copyVariant(fullBlockVariant).with(VariantProperties.UV_LOCK, true))
                        .select(Direction.WEST, true, VerticalSlabShape.INNER_RIGHT, copyVariant(fullBlockVariant).with(VariantProperties.UV_LOCK, true))
                        .select(Direction.SOUTH, true, VerticalSlabShape.INNER_RIGHT, copyVariant(fullBlockVariant).with(VariantProperties.UV_LOCK, true))
                        .select(Direction.NORTH, true, VerticalSlabShape.INNER_RIGHT, copyVariant(fullBlockVariant).with(VariantProperties.UV_LOCK, true))
                        .select(Direction.EAST, true, VerticalSlabShape.INNER_LEFT, copyVariant(fullBlockVariant).with(VariantProperties.UV_LOCK, true))
                        .select(Direction.WEST, true, VerticalSlabShape.INNER_LEFT, copyVariant(fullBlockVariant).with(VariantProperties.UV_LOCK, true))
                        .select(Direction.SOUTH, true, VerticalSlabShape.INNER_LEFT, copyVariant(fullBlockVariant).with(VariantProperties.UV_LOCK, true))
                        .select(Direction.NORTH, true, VerticalSlabShape.INNER_LEFT, copyVariant(fullBlockVariant).with(VariantProperties.UV_LOCK, true)));

        blockStateModelGenerator.registerSimpleItemModel(block, ModelLocationUtils.getModelLocation(block));
        blockStateModelGenerator.blockStateOutput.accept(blockstate);
    }

    public void registerWoodStoolModelBlockStates(GenerationContext blockStateModelGenerator, Block block){
        ResourceLocation texture = ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "block/" + BuiltInRegistries.BLOCK.getKey(block).getPath().replaceAll("stool", "chair"));
        Variant weightedVariant = plainVariant(MEModels.WOOD_STOOL.create(block,
                new TextureMapping().put(TextureSlot.ALL, texture).put(TextureSlot.PARTICLE, texture), blockStateModelGenerator.modelOutput));

        MultiVariantGenerator blockstate = MultiVariantGenerator.multiVariant(block).with(
                PropertyDispatch.property(BlockStateProperties.HORIZONTAL_FACING)
                                .select(Direction.NORTH, weightedVariant)
                                .select(Direction.EAST, copyVariant(weightedVariant).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
                                .select(Direction.SOUTH, copyVariant(weightedVariant).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
                                .select(Direction.WEST, copyVariant(weightedVariant).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270)));

        blockStateModelGenerator.registerSimpleItemModel(block, ModelLocationUtils.getModelLocation(block));
        blockStateModelGenerator.blockStateOutput.accept(blockstate);
    }

    public void registerWoodBenchModelBlockStates(GenerationContext blockStateModelGenerator, Block block){
        ResourceLocation texture = ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "block/" + BuiltInRegistries.BLOCK.getKey(block).getPath());
        Variant weightedVariant = plainVariant(MEModels.WOOD_BENCH.create(block,
                new TextureMapping().put(TextureSlot.ALL, texture).put(TextureSlot.PARTICLE, texture), blockStateModelGenerator.modelOutput));

        MultiVariantGenerator blockstate = MultiVariantGenerator.multiVariant(block).with(
                PropertyDispatch.property(BlockStateProperties.HORIZONTAL_FACING)
                        .select(Direction.NORTH, weightedVariant)
                        .select(Direction.EAST, copyVariant(weightedVariant).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
                        .select(Direction.SOUTH, copyVariant(weightedVariant).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
                        .select(Direction.WEST, copyVariant(weightedVariant).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270)));

        blockStateModelGenerator.registerSimpleItemModel(block, ModelLocationUtils.getModelLocation(block));
        blockStateModelGenerator.blockStateOutput.accept(blockstate);
    }

    public void registerWoodTableModelBlockStates(GenerationContext blockStateModelGenerator, Block block){
        ResourceLocation texture = ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "block/" + BuiltInRegistries.BLOCK.getKey(block).getPath());
        Variant weightedVariant = plainVariant(MEModels.WOOD_TABLE.create(block,
                new TextureMapping().put(TextureSlot.ALL, texture).put(TextureSlot.PARTICLE, texture), blockStateModelGenerator.modelOutput));

        blockStateModelGenerator.registerSimpleItemModel(block, ModelLocationUtils.getModelLocation(block));
        blockStateModelGenerator.blockStateOutput.accept(BlockModelProvider.createSimpleBlock(block, weightedVariant));
    }

    public void registerWoodChairModelBlockStates(GenerationContext blockStateModelGenerator, Block block){
        ResourceLocation texture = ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "block/" + BuiltInRegistries.BLOCK.getKey(block).getPath());
        Variant weightedVariant = plainVariant(MEModels.WOOD_CHAIR.create(block,
                new TextureMapping().put(TextureSlot.ALL, texture).put(TextureSlot.PARTICLE, texture), blockStateModelGenerator.modelOutput));

        MultiVariantGenerator blockstate = MultiVariantGenerator.multiVariant(block).with(
                PropertyDispatch.property(BlockStateProperties.HORIZONTAL_FACING)
                        .select(Direction.NORTH, weightedVariant)
                        .select(Direction.EAST, copyVariant(weightedVariant).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
                        .select(Direction.SOUTH, copyVariant(weightedVariant).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
                        .select(Direction.WEST, copyVariant(weightedVariant).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270)));

        blockStateModelGenerator.registerSimpleItemModel(block, ModelLocationUtils.getModelLocation(block));
        blockStateModelGenerator.blockStateOutput.accept(blockstate);
    }

    public void registerStoneStoolModelBlockStates(GenerationContext blockStateModelGenerator, Block block, ResourceLocation texture) {
        Variant weightedVariant = plainVariant(MEModels.STONE_STOOL.create(block,
                new TextureMapping().put(TextureSlot.ALL, texture).put(TextureSlot.PARTICLE, texture), blockStateModelGenerator.modelOutput));

        MultiVariantGenerator blockstate = MultiVariantGenerator.multiVariant(block).with(
                PropertyDispatch.property(BlockStateProperties.HORIZONTAL_FACING)
                        .select(Direction.NORTH, weightedVariant)
                        .select(Direction.EAST, copyVariant(weightedVariant).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
                        .select(Direction.SOUTH, copyVariant(weightedVariant).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
                        .select(Direction.WEST, copyVariant(weightedVariant).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270)));

        blockStateModelGenerator.registerSimpleItemModel(block, ModelLocationUtils.getModelLocation(block));
        blockStateModelGenerator.blockStateOutput.accept(blockstate);
    }

    public void registerStoneTableModelBlockStates(GenerationContext blockStateModelGenerator, Block block, ResourceLocation texture) {
        Variant weightedVariant = plainVariant(MEModels.STONE_TABLE.create(block,
                new TextureMapping().put(TextureSlot.ALL, texture).put(TextureSlot.PARTICLE, texture), blockStateModelGenerator.modelOutput));

        blockStateModelGenerator.registerSimpleItemModel(block, ModelLocationUtils.getModelLocation(block));
        blockStateModelGenerator.blockStateOutput.accept(BlockModelProvider.createSimpleBlock(block, weightedVariant));
    }

    public void registerStoneChairModelBlockStates(GenerationContext blockStateModelGenerator, Block block, ResourceLocation texture) {
        Variant weightedVariant = plainVariant(MEModels.STONE_CHAIR.create(block,
                new TextureMapping().put(TextureSlot.ALL, texture).put(TextureSlot.PARTICLE, texture), blockStateModelGenerator.modelOutput));

        MultiVariantGenerator blockstate = MultiVariantGenerator.multiVariant(block).with(
                PropertyDispatch.property(BlockStateProperties.HORIZONTAL_FACING)
                        .select(Direction.NORTH, weightedVariant)
                        .select(Direction.EAST, copyVariant(weightedVariant).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
                        .select(Direction.SOUTH, copyVariant(weightedVariant).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
                        .select(Direction.WEST, copyVariant(weightedVariant).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270)));

        blockStateModelGenerator.registerSimpleItemModel(block, ModelLocationUtils.getModelLocation(block));
        blockStateModelGenerator.blockStateOutput.accept(blockstate);
    }

    public final void registerDoubleBlock(GenerationContext blockStateModelGenerator, Block doubleBlock, GenerationContext.PlantType tintType) {
        blockStateModelGenerator.registerSimpleFlatItemModel(doubleBlock, "_top");
        Variant identifier = plainVariant(blockStateModelGenerator.createSuffixedVariant(doubleBlock, "_top", tintType.getCross(), TextureMapping::cross));
        Variant identifier2 = plainVariant(blockStateModelGenerator.createSuffixedVariant(doubleBlock, "_bottom", tintType.getCross(), TextureMapping::cross));
        blockStateModelGenerator.createDoubleBlock(doubleBlock, identifier, identifier2);
    }

    public final void registerPointedBlock(GenerationContext blockStateModelGenerator, Block pointedBlock) {
        ResourceLocation inventory = ModelTemplates.FLAT_ITEM.create(ModelLocationUtils.getModelLocation(pointedBlock.asItem()), TextureMapping.layer0(TextureMapping.getItemTexture(pointedBlock.asItem())), blockStateModelGenerator.modelOutput);
        blockStateModelGenerator.registerSimpleItemModel(pointedBlock.asItem(), inventory);

        PropertyDispatch.C2<Direction, DripstoneThickness> doubleProperty = PropertyDispatch.properties(BlockStateProperties.VERTICAL_DIRECTION, BlockStateProperties.DRIPSTONE_THICKNESS);
        DripstoneThickness[] var2 = DripstoneThickness.values();
        int var3 = var2.length;

        int var4;
        DripstoneThickness thickness;
        for(var4 = 0; var4 < var3; ++var4) {
            thickness = var2[var4];
            doubleProperty.select(Direction.UP, thickness, getPointedVariant(blockStateModelGenerator, Direction.UP, thickness, pointedBlock));
        }

        var2 = DripstoneThickness.values();
        var3 = var2.length;

        for(var4 = 0; var4 < var3; ++var4) {
            thickness = var2[var4];
            doubleProperty.select(Direction.DOWN, thickness, getPointedVariant(blockStateModelGenerator, Direction.DOWN, thickness, pointedBlock));
        }

        blockStateModelGenerator.blockStateOutput.accept(MultiVariantGenerator.multiVariant(pointedBlock).with(doubleProperty));
    }

    public final Variant getPointedVariant(GenerationContext blockStateModelGenerator, Direction direction, DripstoneThickness thickness, Block pointedBlock) {
        String var10000 = direction.getSerializedName();
        String string = "_" + var10000 + "_" + thickness.getSerializedName();
        TextureMapping textureMap = TextureMapping.cross(TextureMapping.getBlockTexture(pointedBlock, string));
        return plainVariant(ModelTemplates.POINTED_DRIPSTONE.createWithSuffix(pointedBlock, string, textureMap, blockStateModelGenerator.modelOutput));
    }

    public final void registerLargeDoor(GenerationContext blockStateModelGenerator, LargeDoorBlock largeDoor, IntegerProperty part) {
        var statesMap = PropertyDispatch.properties(BlockStateProperties.HORIZONTAL_FACING, BlockStateProperties.OPEN, BlockStateProperties.DOOR_HINGE, part);
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

                Variant weightedVariantLeft = plainVariant(ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "block/" + BuiltInRegistries.BLOCK.getKey(largeDoor).getPath() + "_left_" + i));
                Variant weightedVariantLeftOpen = plainVariant(ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "block/" + BuiltInRegistries.BLOCK.getKey(largeDoor).getPath() + "_left_open_" + i));
                Variant weightedVarianRight = plainVariant(ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "block/" + BuiltInRegistries.BLOCK.getKey(largeDoor).getPath() + "_right_" + i));
                Variant weightedVarianRightOpen = plainVariant(ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "block/" + BuiltInRegistries.BLOCK.getKey(largeDoor).getPath() + "_right_open_" + i));

                statesMap.select(Direction.from3DDataValue(k), false, DoorHingeSide.LEFT, i,
                        copyVariant(weightedVariantLeft).with(VariantProperties.Y_ROT, VariantProperties.Rotation.valueOf("R" + rot)));

                statesMap.select(Direction.from3DDataValue(k), true, DoorHingeSide.LEFT, i,
                        copyVariant(weightedVariantLeftOpen).with(VariantProperties.Y_ROT, VariantProperties.Rotation.valueOf("R" + rot)));

                statesMap.select(Direction.from3DDataValue(k), false, DoorHingeSide.RIGHT, i,
                        copyVariant(weightedVarianRight).with(VariantProperties.Y_ROT, VariantProperties.Rotation.valueOf("R" + rot)));

                statesMap.select(Direction.from3DDataValue(k), true, DoorHingeSide.RIGHT, i,
                        copyVariant(weightedVarianRightOpen).with(VariantProperties.Y_ROT, VariantProperties.Rotation.valueOf("R" + rot)));

                if (k == 2) {
                    MEModels.LARGE_DOOR_LEFT.createWithSuffix(largeDoor, "_left_" + i,
                            (new TextureMapping()).put(TextureSlot.ALL, ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "block/" + BuiltInRegistries.BLOCK.getKey(largeDoor).getPath() + "_" + i))
                                    .put(TextureSlot.PARTICLE, ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "block/" + BuiltInRegistries.BLOCK.getKey(largeDoor).getPath() + "_" + i)),
                            blockStateModelGenerator.modelOutput);

                    MEModels.LARGE_DOOR_LEFT_OPEN.createWithSuffix(largeDoor, "_left_open_" + i,
                            (new TextureMapping()).put(TextureSlot.ALL, ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "block/" + BuiltInRegistries.BLOCK.getKey(largeDoor).getPath() + "_" + i))
                                    .put(TextureSlot.PARTICLE, ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "block/" + BuiltInRegistries.BLOCK.getKey(largeDoor).getPath() + "_" + i)),
                            blockStateModelGenerator.modelOutput);

                    MEModels.LARGE_DOOR_RIGHT.createWithSuffix(largeDoor, "_right_" + i,
                            (new TextureMapping()).put(TextureSlot.ALL, ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "block/" + BuiltInRegistries.BLOCK.getKey(largeDoor).getPath() + "_" + i))
                                    .put(TextureSlot.PARTICLE, ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "block/" + BuiltInRegistries.BLOCK.getKey(largeDoor).getPath() + "_" + i)),
                            blockStateModelGenerator.modelOutput);

                    MEModels.LARGE_DOOR_RIGHT_OPEN.createWithSuffix(largeDoor, "_right_open_" + i,
                            (new TextureMapping()).put(TextureSlot.ALL, ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "block/" + BuiltInRegistries.BLOCK.getKey(largeDoor).getPath() + "_" + i))
                                    .put(TextureSlot.PARTICLE, ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "block/" + BuiltInRegistries.BLOCK.getKey(largeDoor).getPath() + "_" + i)),
                            blockStateModelGenerator.modelOutput);
                }
            }
        }
        MultiVariantGenerator blockstate = MultiVariantGenerator.multiVariant(largeDoor).with(statesMap);
        blockStateModelGenerator.blockStateOutput.accept(blockstate);
        blockStateModelGenerator.registerSimpleFlatItemModel(largeDoor.asItem());
    }

    public final void registerThickLargeDoor(GenerationContext blockStateModelGenerator, LargeDoorBlock largeDoor, IntegerProperty part) {
        var statesMap = PropertyDispatch.properties(BlockStateProperties.HORIZONTAL_FACING, BlockStateProperties.OPEN, BlockStateProperties.DOOR_HINGE, part);
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

                Variant weightedVariantLeft = plainVariant(ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "block/" + BuiltInRegistries.BLOCK.getKey(largeDoor).getPath() + "_left_" + i));
                Variant weightedVariantLeftOpen = plainVariant(ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "block/" + BuiltInRegistries.BLOCK.getKey(largeDoor).getPath() + "_left_open_" + i));
                Variant weightedVarianRight = plainVariant(ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "block/" + BuiltInRegistries.BLOCK.getKey(largeDoor).getPath() + "_right_" + i));
                Variant weightedVarianRightOpen = plainVariant(ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "block/" + BuiltInRegistries.BLOCK.getKey(largeDoor).getPath() + "_right_open_" + i));

                statesMap.select(Direction.from3DDataValue(k), false, DoorHingeSide.LEFT, i,
                        copyVariant(weightedVariantLeft).with(VariantProperties.Y_ROT, VariantProperties.Rotation.valueOf("R" + rot)));

                statesMap.select(Direction.from3DDataValue(k), true, DoorHingeSide.LEFT, i,
                        copyVariant(weightedVariantLeftOpen).with(VariantProperties.Y_ROT, VariantProperties.Rotation.valueOf("R" + rot)));

                statesMap.select(Direction.from3DDataValue(k), false, DoorHingeSide.RIGHT, i,
                        copyVariant(weightedVarianRight).with(VariantProperties.Y_ROT, VariantProperties.Rotation.valueOf("R" + rot)));

                statesMap.select(Direction.from3DDataValue(k), true, DoorHingeSide.RIGHT, i,
                        copyVariant(weightedVarianRightOpen).with(VariantProperties.Y_ROT, VariantProperties.Rotation.valueOf("R" + rot)));

                if (k == 2) {
                    MEModels.LARGE_THICK_DOOR_LEFT.createWithSuffix(largeDoor, "_left_" + i,
                            (new TextureMapping()).put(TextureSlot.ALL, ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "block/" + BuiltInRegistries.BLOCK.getKey(largeDoor).getPath() + "_" + i))
                                    .put(TextureSlot.PARTICLE, ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "block/" + BuiltInRegistries.BLOCK.getKey(largeDoor).getPath() + "_" + i)),
                            blockStateModelGenerator.modelOutput);

                    MEModels.LARGE_THICK_DOOR_LEFT_OPEN.createWithSuffix(largeDoor, "_left_open_" + i,
                            (new TextureMapping()).put(TextureSlot.ALL, ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "block/" + BuiltInRegistries.BLOCK.getKey(largeDoor).getPath() + "_" + i))
                                    .put(TextureSlot.PARTICLE, ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "block/" + BuiltInRegistries.BLOCK.getKey(largeDoor).getPath() + "_" + i)),
                            blockStateModelGenerator.modelOutput);

                    MEModels.LARGE_THICK_DOOR_RIGHT.createWithSuffix(largeDoor, "_right_" + i,
                            (new TextureMapping()).put(TextureSlot.ALL, ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "block/" + BuiltInRegistries.BLOCK.getKey(largeDoor).getPath() + "_" + i))
                                    .put(TextureSlot.PARTICLE, ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "block/" + BuiltInRegistries.BLOCK.getKey(largeDoor).getPath() + "_" + i)),
                            blockStateModelGenerator.modelOutput);

                    MEModels.LARGE_THICK_DOOR_RIGHT_OPEN.createWithSuffix(largeDoor, "_right_open_" + i,
                            (new TextureMapping()).put(TextureSlot.ALL, ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "block/" + BuiltInRegistries.BLOCK.getKey(largeDoor).getPath() + "_" + i))
                                    .put(TextureSlot.PARTICLE, ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "block/" + BuiltInRegistries.BLOCK.getKey(largeDoor).getPath() + "_" + i)),
                            blockStateModelGenerator.modelOutput);
                }
            }
        }

        MultiVariantGenerator blockstate = MultiVariantGenerator.multiVariant(largeDoor).with(statesMap);
        blockStateModelGenerator.blockStateOutput.accept(blockstate);
        blockStateModelGenerator.registerSimpleFlatItemModel(largeDoor.asItem());
    }


    public final void registerGlassAndPane(GenerationContext blockStateModelGenerator, Block glass, Block glassPane) {
        TextureMapping textureMap;
        String blockId = BuiltInRegistries.BLOCK.getKey(glassPane).getPath();
        if (blockId.contains("lead_glass")){
            textureMap = TextureMapping.pane(glass, ModDecorativeBlocks.LEAD_GLASS_PANE);
        } else if (blockId.contains("plaster")){
            textureMap = paneAndTopForEdgeCustom(glass, GenericBlockSets.PLASTER.blockSet.base());
        } else if (blockId.contains("white_daub")){
            textureMap = paneAndTopForEdgeCustom(glass, GenericBlockSets.WHITE_DAUB.blockSet.base());
        } else if (blockId.contains("yellow_daub")){
            textureMap = paneAndTopForEdgeCustom(glass, GenericBlockSets.YELLOW_DAUB.blockSet.base());
        } else if (blockId.contains("mud_brick")){
            textureMap = paneAndTopForEdgeCustom(glass, Blocks.MUD_BRICKS);
        } else if (blockId.contains("brick")){
            textureMap = paneAndTopForEdgeCustom(glass, Blocks.BRICKS);
        } else if (blockId.contains("wattle") && !blockId.contains("black") && !blockId.contains("dark") && !blockId.contains("brick")){
            textureMap = paneAndTopForEdgeCustom(glass, glass);
        } else {
            textureMap = TextureMapping.pane(glass, glassPane);
        }
        Variant weightedVariant = plainVariant(ModelTemplates.STAINED_GLASS_PANE_POST.create(glassPane, textureMap, blockStateModelGenerator.modelOutput));
        Variant weightedVariant2 = plainVariant(ModelTemplates.STAINED_GLASS_PANE_SIDE.create(glassPane, textureMap, blockStateModelGenerator.modelOutput));
        Variant weightedVariant3 = plainVariant(ModelTemplates.STAINED_GLASS_PANE_SIDE_ALT.create(glassPane, textureMap, blockStateModelGenerator.modelOutput));
        Variant weightedVariant4 = plainVariant(ModelTemplates.STAINED_GLASS_PANE_NOSIDE.create(glassPane, textureMap, blockStateModelGenerator.modelOutput));
        Variant weightedVariant5 = plainVariant(ModelTemplates.STAINED_GLASS_PANE_NOSIDE_ALT.create(glassPane, textureMap, blockStateModelGenerator.modelOutput));
        Item item = glassPane.asItem();

        blockStateModelGenerator.registerSimpleItemModel(item, blockStateModelGenerator.createFlatItemModelWithBlockTexture(item, glass));
        blockStateModelGenerator.blockStateOutput.accept(MultiPartGenerator.multiPart(glassPane).with(weightedVariant).with(condition().term(BlockStateProperties.NORTH, true), weightedVariant2).with(condition().term(BlockStateProperties.EAST, true), copyVariant(weightedVariant2).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90)).with(condition().term(BlockStateProperties.SOUTH, true), weightedVariant3).with(condition().term(BlockStateProperties.WEST, true), copyVariant(weightedVariant3).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90)).with(condition().term(BlockStateProperties.NORTH, false), weightedVariant4).with(condition().term(BlockStateProperties.EAST, false), weightedVariant5).with(condition().term(BlockStateProperties.SOUTH, false), copyVariant(weightedVariant5).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90)).with(condition().term(BlockStateProperties.WEST, false), copyVariant(weightedVariant4).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270)));
    }

    public final void registerPaneModel(GenerationContext blockStateModelGenerator, Block pane) {
        TextureMapping textureMap = TextureMapping.pane(pane, pane);
        Variant weightedVariant = plainVariant(ModelTemplates.STAINED_GLASS_PANE_POST.create(pane, textureMap, blockStateModelGenerator.modelOutput));
        Variant weightedVariant2 = plainVariant(ModelTemplates.STAINED_GLASS_PANE_SIDE.create(pane, textureMap, blockStateModelGenerator.modelOutput));
        Variant weightedVariant3 = plainVariant(ModelTemplates.STAINED_GLASS_PANE_SIDE_ALT.create(pane, textureMap, blockStateModelGenerator.modelOutput));
        Variant weightedVariant4 = plainVariant(ModelTemplates.STAINED_GLASS_PANE_NOSIDE.create(pane, textureMap, blockStateModelGenerator.modelOutput));
        Variant weightedVariant5 = plainVariant(ModelTemplates.STAINED_GLASS_PANE_NOSIDE_ALT.create(pane, textureMap, blockStateModelGenerator.modelOutput));
        Item item = pane.asItem();
        blockStateModelGenerator.registerSimpleItemModel(item, blockStateModelGenerator.createFlatItemModelWithBlockTexture(item, pane));
        blockStateModelGenerator.blockStateOutput.accept(MultiPartGenerator.multiPart(pane).with(weightedVariant).with(condition().term(BlockStateProperties.NORTH, true), weightedVariant2).with(condition().term(BlockStateProperties.EAST, true), copyVariant(weightedVariant2).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90)).with(condition().term(BlockStateProperties.SOUTH, true), weightedVariant3).with(condition().term(BlockStateProperties.WEST, true), copyVariant(weightedVariant3).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90)).with(condition().term(BlockStateProperties.NORTH, false), weightedVariant4).with(condition().term(BlockStateProperties.EAST, false), weightedVariant5).with(condition().term(BlockStateProperties.SOUTH, false), copyVariant(weightedVariant5).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90)).with(condition().term(BlockStateProperties.WEST, false), copyVariant(weightedVariant4).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270)));
    }

    private void registerLayers(GenerationContext blockStateModelGenerator, Block layers, Block origin, Boolean isVanilla) {
        TextureMapping textureMap = TextureMapping.cube(origin);
        Variant weightedVariant;
        if (!isVanilla){
            weightedVariant = plainVariant(ModelTemplates.CUBE_ALL.create(origin, textureMap, blockStateModelGenerator.modelOutput));
        } else {
            weightedVariant = plainVariant(ResourceLocation.withDefaultNamespace(
                    "block/" + BuiltInRegistries.BLOCK.getKey(origin).getPath()
            ));
        }
        blockStateModelGenerator.blockStateOutput.accept(MultiVariantGenerator.multiVariant(layers).with(PropertyDispatch.property(BlockStateProperties.LAYERS).generate((integer) -> {
            Variant var2;
            if (integer < 8) {
                int var10001 = integer;
                var2 = plainVariant(ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "block/" + BuiltInRegistries.BLOCK.getKey(origin).getPath() +  "_layer_height" + var10001 * 2));
            } else {
                var2 = weightedVariant;
            }

            return var2;
        })));
        if (!isVanilla){
            blockStateModelGenerator.blockStateOutput.accept(BlockModelProvider.createSimpleBlock(origin, weightedVariant));
        }
        blockStateModelGenerator.registerSimpleItemModel(layers, ModelLocationUtils.getModelLocation(layers, "_height2"));
    }

    private void registerTopWaterblock(GenerationContext blockStateModelGenerator, Block block) {
        blockStateModelGenerator.registerSimpleFlatItemModel(block);
        Variant modelVariant = plainModel(ModelLocationUtils.getModelLocation(block));
        blockStateModelGenerator.blockStateOutput.accept(MultiVariantGenerator.multiVariant(block, createRotatedVariants(modelVariant)));
    }

    public void registerRocksBlock(GenerationContext blockStateModelGenerator, Block rocksBlock, Block origin) {
        ResourceLocation id = ResourceLocation.fromNamespaceAndPath(BuiltInRegistries.BLOCK.getKey(origin).getNamespace(), "block/" + BuiltInRegistries.BLOCK.getKey(origin).getPath());
        if (origin == Blocks.BASALT || origin == Blocks.POLISHED_BASALT) {
            id = id.withSuffix("_side");
        }
        Variant stage0 = plainVariant(MEModels.ROCKS_STAGE_0.create(rocksBlock,
                TextureMapping.singleSlot(TextureSlot.ALL, id),
                blockStateModelGenerator.modelOutput));
        Variant stage1 = plainVariant(MEModels.ROCKS_STAGE_1.create(rocksBlock,
                TextureMapping.singleSlot(TextureSlot.ALL, id),
                blockStateModelGenerator.modelOutput));
        Variant stage2 = plainVariant(MEModels.ROCKS_STAGE_2.create(rocksBlock,
                TextureMapping.singleSlot(TextureSlot.ALL, id),
                blockStateModelGenerator.modelOutput));
        Variant stage3 = plainVariant(MEModels.ROCKS_STAGE_3.create(rocksBlock,
                TextureMapping.singleSlot(TextureSlot.ALL, id),
                blockStateModelGenerator.modelOutput));

        MultiVariantGenerator blockstate = MultiVariantGenerator.multiVariant(rocksBlock)
                .with(PropertyDispatch.properties(BlockStateProperties.HORIZONTAL_FACING, RocksBlock.STAGE)
                        .select(Direction.EAST, 0, copyVariant(stage0).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90).with(VariantProperties.UV_LOCK, true))
                        .select(Direction.WEST, 0, copyVariant(stage0).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270).with(VariantProperties.UV_LOCK, true))
                        .select(Direction.SOUTH, 0, copyVariant(stage0).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180).with(VariantProperties.UV_LOCK, true))
                        .select(Direction.NORTH, 0, copyVariant(stage0).with(VariantProperties.UV_LOCK, true))

                        .select(Direction.EAST, 1, copyVariant(stage1).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90).with(VariantProperties.UV_LOCK, true))
                        .select(Direction.WEST, 1, copyVariant(stage1).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270).with(VariantProperties.UV_LOCK, true))
                        .select(Direction.SOUTH, 1, copyVariant(stage1).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180).with(VariantProperties.UV_LOCK, true))
                        .select(Direction.NORTH, 1, copyVariant(stage1).with(VariantProperties.UV_LOCK, true))

                        .select(Direction.EAST, 2, copyVariant(stage2).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90).with(VariantProperties.UV_LOCK, true))
                        .select(Direction.WEST, 2, copyVariant(stage2).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270).with(VariantProperties.UV_LOCK, true))
                        .select(Direction.SOUTH, 2, copyVariant(stage2).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180).with(VariantProperties.UV_LOCK, true))
                        .select(Direction.NORTH, 2, copyVariant(stage2).with(VariantProperties.UV_LOCK, true))

                        .select(Direction.EAST, 3, copyVariant(stage3).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90).with(VariantProperties.UV_LOCK, true))
                        .select(Direction.WEST, 3, copyVariant(stage3).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270).with(VariantProperties.UV_LOCK, true))
                        .select(Direction.SOUTH, 3, copyVariant(stage3).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180).with(VariantProperties.UV_LOCK, true))
                        .select(Direction.NORTH, 3, copyVariant(stage3).with(VariantProperties.UV_LOCK, true)));

        blockStateModelGenerator.registerSimpleItemModel(rocksBlock, ModelLocationUtils.getModelLocation(rocksBlock));
        blockStateModelGenerator.blockStateOutput.accept(blockstate);
    }

    public void registerTrapdoor(GenerationContext blockStateModelGenerator, Block trapdoorBlock, Block block, boolean orientable) {
        TextureMapping textureMap;

        String modid = BuiltInRegistries.BLOCK.getKey(block).getNamespace();

        Variant identifier;
        ResourceLocation identifier2;
        Variant identifier3;

        if (orientable){
            textureMap = TextureMapping.defaultTexture(ResourceLocation.fromNamespaceAndPath(modid, "block/" + BuiltInRegistries.BLOCK.getKey(trapdoorBlock).getPath()));
            identifier = plainVariant(ModelTemplates.ORIENTABLE_TRAPDOOR_TOP.create(trapdoorBlock, textureMap, blockStateModelGenerator.modelOutput));
            identifier2 = ModelTemplates.ORIENTABLE_TRAPDOOR_BOTTOM.create(trapdoorBlock, textureMap, blockStateModelGenerator.modelOutput);
            identifier3 = plainVariant(ModelTemplates.ORIENTABLE_TRAPDOOR_OPEN.create(trapdoorBlock, textureMap, blockStateModelGenerator.modelOutput));

            blockStateModelGenerator.blockStateOutput.accept(BlockModelProvider.createOrientableTrapdoor(trapdoorBlock, identifier, plainVariant(identifier2), identifier3));
        } else {
            if (block == Blocks.BASALT) {
                textureMap = TextureMapping.defaultTexture(ResourceLocation.fromNamespaceAndPath(modid, "block/" + BuiltInRegistries.BLOCK.getKey(block).getPath() + "_side"));
            } else {
                textureMap = TextureMapping.defaultTexture(ResourceLocation.fromNamespaceAndPath(modid, "block/" + BuiltInRegistries.BLOCK.getKey(block).getPath().replaceAll("_trapdoor", "")));
            }
            identifier = plainVariant(ModelTemplates.TRAPDOOR_TOP.create(trapdoorBlock, textureMap, blockStateModelGenerator.modelOutput));
            identifier2 = ModelTemplates.TRAPDOOR_BOTTOM.create(trapdoorBlock, textureMap, blockStateModelGenerator.modelOutput);
            identifier3 = plainVariant(ModelTemplates.TRAPDOOR_OPEN.create(trapdoorBlock, textureMap, blockStateModelGenerator.modelOutput));

            blockStateModelGenerator.blockStateOutput.accept(BlockModelProvider.createTrapdoor(trapdoorBlock, identifier, plainVariant(identifier2), identifier3));

        }
        blockStateModelGenerator.registerSimpleItemModel(trapdoorBlock, identifier2);
    }

    public void registerOrientableThickLadder(GenerationContext blockStateModelGenerator, Block ladderBlock) {
        ResourceLocation texture = ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "block/" + BuiltInRegistries.BLOCK.getKey(ladderBlock).getPath());

        Variant weightedVariant = plainVariant(MEModels.THICK_LADDER.create(ladderBlock, TextureMapping.singleSlot(TextureSlot.ALL, texture), blockStateModelGenerator.modelOutput));

        MultiVariantGenerator blockstate = MultiVariantGenerator.multiVariant(ladderBlock)
                .with(PropertyDispatch.properties(BlockStateProperties.ATTACH_FACE, BlockStateProperties.HORIZONTAL_FACING)
                .select(AttachFace.FLOOR, Direction.NORTH, weightedVariant)
                .select(AttachFace.FLOOR, Direction.EAST, copyVariant(weightedVariant).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
                .select(AttachFace.FLOOR, Direction.SOUTH, copyVariant(weightedVariant).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
                .select(AttachFace.FLOOR, Direction.WEST, copyVariant(weightedVariant).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))

                .select(AttachFace.WALL, Direction.NORTH, copyVariant(weightedVariant).with(VariantProperties.X_ROT, VariantProperties.Rotation.R90))
                .select(AttachFace.WALL, Direction.EAST, copyVariant(weightedVariant).with(VariantProperties.X_ROT, VariantProperties.Rotation.R90).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
                .select(AttachFace.WALL, Direction.SOUTH, copyVariant(weightedVariant).with(VariantProperties.X_ROT, VariantProperties.Rotation.R90).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
                .select(AttachFace.WALL, Direction.WEST, copyVariant(weightedVariant).with(VariantProperties.X_ROT, VariantProperties.Rotation.R90).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))

                .select(AttachFace.CEILING, Direction.SOUTH, copyVariant(weightedVariant).with(VariantProperties.X_ROT, VariantProperties.Rotation.R180))
                .select(AttachFace.CEILING, Direction.WEST, copyVariant(weightedVariant).with(VariantProperties.X_ROT, VariantProperties.Rotation.R180).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
                .select(AttachFace.CEILING, Direction.NORTH, copyVariant(weightedVariant).with(VariantProperties.X_ROT, VariantProperties.Rotation.R180).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
                .select(AttachFace.CEILING, Direction.EAST, copyVariant(weightedVariant).with(VariantProperties.X_ROT, VariantProperties.Rotation.R180).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270)));

        blockStateModelGenerator.registerSimpleItemModel(ladderBlock, ModelLocationUtils.getModelLocation(ladderBlock));
        blockStateModelGenerator.blockStateOutput.accept(blockstate);
    }

    private void registerFarmland(GenerationContext blockStateModelGenerator, Block dirtBlock, Block farmland) {
        TextureMapping textureMap = (new TextureMapping()).put(TextureSlot.DIRT, TextureMapping.getBlockTexture(dirtBlock)).put(TextureSlot.TOP, TextureMapping.getBlockTexture(farmland));
        TextureMapping textureMap2 = (new TextureMapping()).put(TextureSlot.DIRT, TextureMapping.getBlockTexture(dirtBlock)).put(TextureSlot.TOP, TextureMapping.getBlockTexture(farmland, "_moist"));
        Variant weightedVariant = plainVariant(ModelTemplates.FARMLAND.create(farmland, textureMap, blockStateModelGenerator.modelOutput));
        Variant weightedVariant2 = plainVariant(ModelTemplates.FARMLAND.create(TextureMapping.getBlockTexture(farmland, "_moist"), textureMap2, blockStateModelGenerator.modelOutput));
        blockStateModelGenerator.blockStateOutput.accept(MultiVariantGenerator.multiVariant(farmland).with(createEmptyOrFullDispatch(BlockStateProperties.MOISTURE, 7, weightedVariant2, weightedVariant)));
    }

    private void registerDirtPath(GenerationContext blockStateModelGenerator, Block dirtBlock, Block pathBlock) {
        TextureMapping textureMap = new TextureMapping()
                .put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(dirtBlock))
                .put(TextureSlot.TOP, TextureMapping.getBlockTexture(pathBlock).withSuffix("_top"))
                .put(TextureSlot.SIDE, TextureMapping.getBlockTexture(pathBlock).withSuffix("_side"))
                .put(TextureSlot.BOTTOM, TextureMapping.getBlockTexture(dirtBlock));
        Variant weightedVariant = plainVariant(MEModels.PATH_BLOCK.create(pathBlock, textureMap, blockStateModelGenerator.modelOutput));
        blockStateModelGenerator.blockStateOutput.accept(MultiVariantGenerator.multiVariant(pathBlock, weightedVariant));
    }

    public final void registerMultifaceBlock(GenerationContext blockStateModelGenerator, Block block) {
        blockStateModelGenerator.createMultifaceBlockStates(block);
    }

    private void registerCrossBlockWithTexture(GenerationContext context, Block block, ResourceLocation texture) {
        registerCrossBlockWithTexture(context, block, texture, GenerationContext.PlantType.NOT_TINTED);
    }

    private void registerCrossBlockWithTexture(
            GenerationContext context,
            Block block,
            ResourceLocation texture,
            GenerationContext.PlantType plantType
    ) {
        ResourceLocation model = plantType.getCross().create(
                block,
                TextureMapping.cross(texture),
                context.modelOutput);
        context.blockStateOutput.accept(createSimpleBlock(block, plainVariant(model)));
        ModelTemplates.FLAT_ITEM.create(
                ModelLocationUtils.getModelLocation(block.asItem()),
                TextureMapping.layer0(texture),
                context.modelOutput);
    }

    public final void registerHangingMoss(GenerationContext blockStateModelGenerator, Block block) {
        blockStateModelGenerator.registerSimpleFlatItemModel(block);
        blockStateModelGenerator.blockStateOutput.accept(MultiVariantGenerator.multiVariant(block).with(PropertyDispatch.property(booleanProperty(block, "tip")).generate((tip) -> {
            String string = tip ? "_tip" : "";
            TextureMapping textureMap = TextureMapping.crop(TextureMapping.getBlockTexture(block, string));
            return plainVariant(MEModels.CROP_VINE.createWithSuffix(block, string, textureMap, blockStateModelGenerator.modelOutput));
        })));
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cachedOutput) {
        Map<Block, BlockStateGenerator> blockStates = new HashMap<>();
        Map<ResourceLocation, Supplier<JsonElement>> models = new HashMap<>();
        Set<ResourceLocation> definedModels = new HashSet<>();
        Set<Item> skippedAutoModels = new HashSet<>();

        Consumer<BlockStateGenerator> blockStateOutput = generator -> {
            BlockStateGenerator previous = blockStates.put(generator.getBlock(), generator);
            if (previous != null) {
                throw new IllegalStateException("Duplicate blockstate definition for " + generator.getBlock());
            }
        };
        BiConsumer<ResourceLocation, Supplier<JsonElement>> modelOutput = (id, supplier) -> {
            if (!definedModels.add(id)) {
                throw new IllegalStateException("Duplicate model definition for " + id);
            }
            if (!hasAuthoredItemModel(id)) {
                models.put(id, supplier);
            }
        };

      generateBlockStateModels(new GenerationContext(blockStateOutput, modelOutput, skippedAutoModels::add));
      replacePaleOakBlockTextures(models);
      replacePaleOakDoorItemTexture(models);
      addAutomaticBlockItemModels(blockStates.keySet(), models, skippedAutoModels);

        return CompletableFuture.allOf(
                saveCollection(cachedOutput, blockStates,
                        block -> blockStatePathProvider.json(block.builtInRegistryHolder().key().location())),
                saveCollection(cachedOutput, models, modelPathProvider::json));
  }

  private static void replacePaleOakBlockTextures(
          Map<ResourceLocation, Supplier<JsonElement>> models
  ) {
      models.replaceAll((id, modelSupplier) -> {
          if (!id.getPath().contains("pale_oak")) {
              return modelSupplier;
          }
          return () -> {
              JsonElement model = modelSupplier.get().deepCopy();
              if (!model.isJsonObject()) {
                  return model;
              }
              JsonObject textures = model.getAsJsonObject().getAsJsonObject("textures");
              if (textures == null) {
                  return model;
              }
              Map<String, String> replacements = new HashMap<>();
              textures.entrySet().forEach(texture -> {
                  if (texture.getValue().isJsonPrimitive()) {
                      String fallback = PALE_OAK_TEXTURE_FALLBACKS.get(texture.getValue().getAsString());
                      if (fallback != null) {
                          replacements.put(texture.getKey(), fallback);
                      }
                  }
              });
              replacements.forEach(textures::addProperty);
              return model;
          };
      });
  }

  private static void replacePaleOakDoorItemTexture(
          Map<ResourceLocation, Supplier<JsonElement>> models
  ) {
      ResourceLocation modelId =
              ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "item/pale_oak_door");
      if (!models.containsKey(modelId)) {
          return;
      }

      models.put(modelId, () -> {
          JsonObject model = new JsonObject();
          model.addProperty("parent", "minecraft:item/generated");
          JsonObject textures = new JsonObject();
          textures.addProperty("layer0", "minecraft:item/birch_door");
          model.add("textures", textures);
          return model;
      });
  }

  private void addAutomaticBlockItemModels(
            Set<Block> blocks,
            Map<ResourceLocation, Supplier<JsonElement>> models,
            Set<Item> skippedAutoModels
    ) {
        for (Block block : blocks) {
            Item item = Item.BY_BLOCK.get(block);
            ResourceLocation itemModel = item == null ? null : ModelLocationUtils.getModelLocation(item);
            if (item != null && !skippedAutoModels.contains(item) && !hasAuthoredItemModel(itemModel)) {
                models.putIfAbsent(
                        itemModel,
                        new DelegatedModel(ModelLocationUtils.getModelLocation(block))
                );
            }
        }
    }

    private boolean hasAuthoredItemModel(ResourceLocation id) {
        return id != null
                && id.getNamespace().equals(MiddleEarth.MOD_ID)
                && id.getPath().startsWith("item/")
                && Files.isRegularFile(authoredModelsRoot.resolve(id.getPath() + ".json"));
    }

    private static <T> CompletableFuture<?> saveCollection(
            CachedOutput cachedOutput,
            Map<T, ? extends Supplier<JsonElement>> values,
            Function<T, Path> pathFactory
    ) {
        CompletableFuture<?>[] futures = values.entrySet().stream()
                .map(entry -> DataProvider.saveStable(cachedOutput, entry.getValue().get(), pathFactory.apply(entry.getKey())))
                .toArray(CompletableFuture[]::new);
        return CompletableFuture.allOf(futures);
    }

    private static Variant plainVariant(ResourceLocation model) {
        return Variant.variant().with(VariantProperties.MODEL, model);
    }

    private static Variant plainVariant(Variant variant) {
        return variant;
    }

    private static Variant plainModel(ResourceLocation model) {
        return plainVariant(model);
    }

    @SuppressWarnings("unchecked")
    private static net.minecraft.world.level.block.state.properties.Property<Boolean> booleanProperty(
            Block block,
            String name
    ) {
        return (net.minecraft.world.level.block.state.properties.Property<Boolean>) block.getStateDefinition()
                .getProperty(name);
    }

    private static ResourceLocation modelLocation(Variant variant) {
        return ResourceLocation.parse(variant.get().getAsJsonObject().get("model").getAsString());
    }

    private static BlockStateGenerator createSimpleBlock(Block block, Variant model) {
        return MultiVariantGenerator.multiVariant(block, model);
    }

    private static BlockStateGenerator createAxisAlignedPillarBlock(Block block, Variant model) {
        return invokeGeneratorStatic("createAxisAlignedPillarBlock", block, modelLocation(model));
    }

    private static BlockStateGenerator createSlab(Block block, Variant bottom, Variant top, Variant doubleSlab) {
        return invokeGeneratorStatic(
                "createSlab",
                block,
                modelLocation(bottom),
                modelLocation(top),
                modelLocation(doubleSlab)
        );
    }

    private static BlockStateGenerator createStairs(Block block, Variant inner, Variant straight, Variant outer) {
        return invokeGeneratorStatic(
                "createStairs",
                block,
                modelLocation(inner),
                modelLocation(straight),
                modelLocation(outer)
        );
    }

    private static BlockStateGenerator createWall(Block block, Variant post, Variant low, Variant tall) {
        return invokeGeneratorStatic(
                "createWall",
                block,
                modelLocation(post),
                modelLocation(low),
                modelLocation(tall)
        );
    }

    private static BlockStateGenerator createFence(Block block, Variant post, Variant side) {
        return invokeGeneratorStatic("createFence", block, modelLocation(post), modelLocation(side));
    }

    private static BlockStateGenerator createFenceGate(
            Block block,
            Variant open,
            Variant closed,
            Variant openWall,
            Variant closedWall,
            boolean uvLock
    ) {
        return invokeGeneratorStatic(
                "createFenceGate",
                block,
                modelLocation(open),
                modelLocation(closed),
                modelLocation(openWall),
                modelLocation(closedWall),
                uvLock
        );
    }

    private static BlockStateGenerator createButton(Block block, Variant unpressed, Variant pressed) {
        return invokeGeneratorStatic("createButton", block, modelLocation(unpressed), modelLocation(pressed));
    }

    private static BlockStateGenerator createPressurePlate(Block block, Variant up, Variant down) {
        return invokeGeneratorStatic("createPressurePlate", block, modelLocation(up), modelLocation(down));
    }

    private static BlockStateGenerator createOrientableTrapdoor(
            Block block,
            Variant bottom,
            Variant top,
            Variant open
    ) {
        return invokeGeneratorStatic(
                "createOrientableTrapdoor",
                block,
                modelLocation(bottom),
                modelLocation(top),
                modelLocation(open)
        );
    }

    private static BlockStateGenerator createTrapdoor(Block block, Variant bottom, Variant top, Variant open) {
        return invokeGeneratorStatic(
                "createTrapdoor",
                block,
                modelLocation(bottom),
                modelLocation(top),
                modelLocation(open)
        );
    }

    private static Variant[] createRotatedVariants(Variant model) {
        return invokeGeneratorStatic("createRotatedVariants", modelLocation(model));
    }

    private static <T extends Comparable<T>> PropertyDispatch createEmptyOrFullDispatch(
            net.minecraft.world.level.block.state.properties.Property<T> property,
            T fullValue,
            Variant fullModel,
            Variant emptyModel
    ) {
        return invokeGeneratorStatic(
                "createEmptyOrFullDispatch",
                property,
                fullValue,
                modelLocation(fullModel),
                modelLocation(emptyModel)
        );
    }

    @SuppressWarnings("unchecked")
    private static <T> T invokeGeneratorStatic(String name, Object... arguments) {
        return (T) invokeGenerator(null, name, arguments);
    }

    private static Object invokeGenerator(BlockModelGenerators target, String name, Object... arguments) {
        for (Method method : BlockModelGenerators.class.getDeclaredMethods()) {
            if (!method.getName().equals(name)
                    || method.getParameterCount() != arguments.length
                    || (target == null) != Modifier.isStatic(method.getModifiers())
                    || !parametersMatch(method.getParameterTypes(), arguments)) {
                continue;
            }

            try {
                method.setAccessible(true);
                return method.invoke(target, arguments);
            } catch (ReflectiveOperationException exception) {
                throw new IllegalStateException("Failed to invoke BlockModelGenerators." + name, exception);
            }
        }
        throw new IllegalStateException("No matching BlockModelGenerators." + name + " overload");
    }

    private static boolean parametersMatch(Class<?>[] parameterTypes, Object[] arguments) {
        for (int i = 0; i < parameterTypes.length; i++) {
            if (arguments[i] == null) {
                continue;
            }
            Class<?> parameterType = parameterTypes[i].isPrimitive()
                    ? primitiveWrapper(parameterTypes[i])
                    : parameterTypes[i];
            if (!parameterType.isInstance(arguments[i])) {
                return false;
            }
        }
        return true;
    }

    private static Class<?> primitiveWrapper(Class<?> type) {
        if (type == boolean.class) return Boolean.class;
        if (type == int.class) return Integer.class;
        if (type == long.class) return Long.class;
        if (type == float.class) return Float.class;
        if (type == double.class) return Double.class;
        if (type == byte.class) return Byte.class;
        if (type == short.class) return Short.class;
        if (type == char.class) return Character.class;
        return type;
    }

    public static final class GenerationContext extends BlockModelGenerators {
        public final Consumer<BlockStateGenerator> blockStateOutput;
        public final BiConsumer<ResourceLocation, Supplier<JsonElement>> modelOutput;

        private GenerationContext(
                Consumer<BlockStateGenerator> blockStateOutput,
                BiConsumer<ResourceLocation, Supplier<JsonElement>> modelOutput,
                Consumer<Item> skippedAutoModelsOutput
        ) {
            super(blockStateOutput, modelOutput, skippedAutoModelsOutput);
            this.blockStateOutput = blockStateOutput;
            this.modelOutput = modelOutput;
        }

        public void createTintedLeaves(Block block, TexturedModel.Provider model, int ignoredTint) {
            createTrivialBlock(block, model);
        }

        public void createRotatedPillarWithHorizontalVariant(
                Block block,
                TexturedModel.Provider vertical,
                TexturedModel.Provider horizontal
        ) {
            invokeGenerator(this, "createRotatedPillarWithHorizontalVariant", block, vertical, horizontal);
        }

        public void createDoor(Block block) {
            invokeGenerator(this, "createDoor", block);
        }

        public void createCrossBlockWithDefaultItem(Block block, PlantType tint) {
            invokeGenerator(this, "createCrossBlockWithDefaultItem", block, tint.vanillaValue());
        }

        public void createGrowingPlant(Block body, Block head, PlantType tint) {
            invokeGenerator(this, "createGrowingPlant", body, head, tint.vanillaValue());
        }

        public void createCrossBlock(Block block, PlantType tint) {
            invokeGenerator(this, "createCrossBlock", block, tint.vanillaValue());
        }

        public void createFlowerBed(Block block) {
            invokeGenerator(this, "createFlowerBed", block);
        }

        public void createMushroomBlock(Block block) {
            invokeGenerator(this, "createMushroomBlock", block);
        }

        public void createCropBlock(Block block, IntegerProperty ageProperty, int... ages) {
            invokeGenerator(this, "createCropBlock", block, ageProperty, ages);
        }

        public void createAmethystCluster(Block block) {
            invokeGenerator(this, "createAmethystCluster", block);
        }

        public void createMultiface(Block block) {
            invokeGenerator(this, "createMultiface", block);
        }

        public void createMultifaceBlockStates(Block block) {
            createMultiface(block);
        }

        public ResourceLocation createSuffixedVariant(
                Block block,
                String suffix,
                ModelTemplate model,
                Function<ResourceLocation, TextureMapping> textureFactory
        ) {
            return model.createWithSuffix(
                    block,
                    suffix,
                    textureFactory.apply(TextureMapping.getBlockTexture(block, suffix)),
                    modelOutput
            );
        }

        public void createDoubleBlock(Block block, Variant top, Variant bottom) {
            invokeGenerator(this, "createDoubleBlock", block, modelLocation(top), modelLocation(bottom));
        }

        public ResourceLocation createFlatItemModelWithBlockTexture(Item item, Block block) {
            return ModelTemplates.FLAT_ITEM.create(
                    ModelLocationUtils.getModelLocation(item),
                    TextureMapping.layer0(TextureMapping.getBlockTexture(block)),
                    modelOutput
            );
        }

        public void registerSimpleItemModel(Object itemOrBlock, ResourceLocation model) {
            Item item = itemOrBlock instanceof Block block ? block.asItem() : (Item) itemOrBlock;
            if (ModelLocationUtils.getModelLocation(item).equals(model)) {
                return;
            }
            modelOutput.accept(ModelLocationUtils.getModelLocation(item), new DelegatedModel(model));
        }

        public void registerSimpleTintedItemModel(
                Object itemOrBlock,
                ResourceLocation model,
                Object ignoredTint
        ) {
            Item item = itemOrBlock instanceof Block block ? block.asItem() : (Item) itemOrBlock;
            if (ModelLocationUtils.getModelLocation(item).equals(model)) {
                return;
            }
            registerSimpleItemModel(itemOrBlock, model);
        }

        public void registerSimpleFlatItemModel(Object itemOrBlock) {
            registerSimpleFlatItemModel(itemOrBlock, "");
        }

        public void registerSimpleFlatItemModel(Object itemOrBlock, String textureSuffix) {
            Item item = itemOrBlock instanceof Block block ? block.asItem() : (Item) itemOrBlock;
            ResourceLocation texture = itemOrBlock instanceof Block block
                    ? TextureMapping.getBlockTexture(block, textureSuffix)
                    : TextureMapping.getItemTexture(item, textureSuffix);
            ModelTemplates.FLAT_ITEM.create(
                    ModelLocationUtils.getModelLocation(item),
                    TextureMapping.layer0(texture),
                    modelOutput
            );
        }

        public enum PlantType {
            TINTED(ModelTemplates.TINTED_CROSS, ModelTemplates.TINTED_FLOWER_POT_CROSS),
            NOT_TINTED(ModelTemplates.CROSS, ModelTemplates.FLOWER_POT_CROSS);

            private final ModelTemplate cross;
            private final ModelTemplate crossPot;

            PlantType(ModelTemplate cross, ModelTemplate crossPot) {
                this.cross = cross;
                this.crossPot = crossPot;
            }

            public ModelTemplate getCross() {
                return cross;
            }

            public ModelTemplate getCrossPot() {
                return crossPot;
            }

            @SuppressWarnings({"rawtypes", "unchecked"})
            private Object vanillaValue() {
                try {
                    Class<? extends Enum> type = (Class<? extends Enum>) Class.forName(
                            "net.minecraft.data.models.BlockModelGenerators$TintState"
                    );
                    return Enum.valueOf(type, name());
                } catch (ClassNotFoundException exception) {
                    throw new IllegalStateException("Missing BlockModelGenerators.TintState", exception);
                }
            }
        }
    }

    private static Variant copyVariant(Variant variant) {
        return Variant.merge(variant, Variant.variant());
    }

    public static TextureMapping paneAndTopForEdgeCustom(Block block, Block top) {
        return (new TextureMapping()).put(TextureSlot.PANE, TextureMapping.getBlockTexture(block)).put(TextureSlot.EDGE, TextureMapping.getBlockTexture(top));
    }
}
