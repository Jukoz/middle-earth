package net.jukoz.me.world.features.vegetation;

import net.jukoz.me.MiddleEarth;
import net.jukoz.me.block.ModNatureBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SweetBerryBushBlock;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.ConfiguredFeatures;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.SimpleBlockFeatureConfig;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;

import java.util.List;

public class ModVegetationConfiguredFeatures {
    public static final RegistryKey<ConfiguredFeature<?, ?>> PATCH_TOUGH_BERRY_BUSH = registerKey("patch_tough_berry_bush");
    public static final RegistryKey<ConfiguredFeature<?, ?>> PATCH_MALLOS = registerKey("patch_mallos");
    public static final RegistryKey<ConfiguredFeature<?, ?>> PATCH_WHITE_MUSHROOM = registerKey("patch_white_mushroom");
    public static final RegistryKey<ConfiguredFeature<?, ?>> PATCH_WHITE_MUSHROOM_TILLER = registerKey("patch_white_mushroom_tiller");

    public static void bootstrap(Registerable<ConfiguredFeature<?, ?>> featureRegisterable) {
        ConfiguredFeatures.register(featureRegisterable, PATCH_TOUGH_BERRY_BUSH, Feature.RANDOM_PATCH,
                ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockFeatureConfig(BlockStateProvider.of(ModNatureBlocks.TOUGH_BERRY_BUSH.getDefaultState()
                                .with(SweetBerryBushBlock.AGE, 0))), List.of(Blocks.GRASS_BLOCK)));

        ConfiguredFeatures.register(featureRegisterable, PATCH_MALLOS, Feature.RANDOM_PATCH,
                ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockFeatureConfig(BlockStateProvider.of(ModNatureBlocks.MALLOS))));

        ConfiguredFeatures.register(featureRegisterable, PATCH_WHITE_MUSHROOM, Feature.RANDOM_PATCH,
                ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockFeatureConfig(BlockStateProvider.of(ModNatureBlocks.WHITE_MUSHROOM))));
        ConfiguredFeatures.register(featureRegisterable, PATCH_WHITE_MUSHROOM_TILLER, Feature.RANDOM_PATCH,
                ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockFeatureConfig(BlockStateProvider.of(ModNatureBlocks.WHITE_MUSHROOM_TILLER))));
    }


    public static RegistryKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, new Identifier(MiddleEarth.MOD_ID, name));
    }
}
