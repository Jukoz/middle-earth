package net.jesteur.me.datageneration;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.jesteur.me.datageneration.content.models.SimpleBlockModel;
import net.jesteur.me.datageneration.content.models.SimpleSlabModel;
import net.jesteur.me.datageneration.content.models.SimpleStairModel;
import net.jesteur.me.datageneration.content.models.SimpleWallModel;
import net.jesteur.me.datageneration.content.multi.WoodMulti;
import net.jesteur.me.datageneration.content.tags.MineablePickaxe;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.client.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public class ModelProvider extends FabricModelProvider {

    public ModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {

        for (Block block : SimpleBlockModel.blocks) {
            blockStateModelGenerator.registerSimpleCubeAll(block);
        }

        for (SimpleSlabModel.Slab block : SimpleSlabModel.blocks) {
            Identifier id = ModelIds.getBlockModelId(block.block());
            Block slab = block.slab();

            TexturedModel texturedModel = TexturedModel.CUBE_ALL.get(block.block());
            Identifier bottom = Models.SLAB.upload(slab, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);
            Identifier top = Models.SLAB_TOP.upload(slab, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);

            blockStateModelGenerator.blockStateCollector.accept(BlockStateModelGenerator.createSlabBlockState(
                    slab,
                    bottom, top, id));
        }

        for (SimpleStairModel.Stair stair : SimpleStairModel.blocks) {

            TexturedModel texturedModel = TexturedModel.CUBE_ALL.get(stair.block());

            Identifier inner = Models.INNER_STAIRS.upload(stair.stairs(), texturedModel.getTextures(), blockStateModelGenerator.modelCollector);
            Identifier regular = Models.STAIRS.upload(stair.stairs(), texturedModel.getTextures(), blockStateModelGenerator.modelCollector);
            Identifier outer = Models.OUTER_STAIRS.upload(stair.stairs(), texturedModel.getTextures(), blockStateModelGenerator.modelCollector);


            blockStateModelGenerator.blockStateCollector.accept(BlockStateModelGenerator.createStairsBlockState(
                    stair.stairs(), inner, regular, outer));

        }

        for (SimpleWallModel.Wall wall : SimpleWallModel.blocks) {

            TexturedModel texturedModel = TexturedModel.CUBE_ALL.get(wall.block());

            Models.WALL_INVENTORY.upload(wall.wall(), texturedModel.getTextures(), blockStateModelGenerator.modelCollector);
            Identifier post = Models.TEMPLATE_WALL_POST.upload(wall.wall(), texturedModel.getTextures(), blockStateModelGenerator.modelCollector);
            Identifier low = Models.TEMPLATE_WALL_SIDE.upload(wall.wall(), texturedModel.getTextures(), blockStateModelGenerator.modelCollector);
            Identifier tall = Models.TEMPLATE_WALL_SIDE_TALL.upload(wall.wall(), texturedModel.getTextures(), blockStateModelGenerator.modelCollector);


            blockStateModelGenerator.blockStateCollector.accept(BlockStateModelGenerator.createWallBlockState(
                    wall.wall(),
                    post,
                    low,
                    tall
            ));
        }

        for (WoodMulti.WoodRecord wood : WoodMulti.woods) {
            blockStateModelGenerator.registerSingleton(wood.wood(), TexturedModel.CUBE_ALL.get(wood.log()).getTextures(), Models.CUBE_ALL);
        }

    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        for (SimpleWallModel.Wall wall : SimpleWallModel.blocks) {
            Identifier id = Registries.BLOCK.getId(wall.wall());
            itemModelGenerator.register(wall.wall().asItem(), new Model(Optional.of(id.withPath("block/" + id.getPath() + "_inventory")), Optional.empty()));
        }
        for (WoodMulti.WoodRecord wood : WoodMulti.woods) {
            Identifier id = Registries.BLOCK.getId(wood.wood());
            itemModelGenerator.register(wood.wood().asItem(), new Model(Optional.of(id.withPath("block/" + id.getPath())), Optional.empty()));
        }
    }
}
