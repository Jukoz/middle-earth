package net.jesteur.me.world.dimension;

import net.jesteur.me.MiddleEarth;
import net.jesteur.me.world.chunkgen.MiddleEarthChunkGenerator;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionOptions;

public class ModDimensions {
    public static final String PATH = "middle-earth";

    public static final RegistryKey<DimensionOptions> DIMENSION_KEY =
            RegistryKey.of(RegistryKeys.DIMENSION, new Identifier(MiddleEarth.MOD_ID, PATH));

    public static RegistryKey<World> WORLD_KEY =
            RegistryKey.of(RegistryKeys.WORLD, DIMENSION_KEY.getValue());

    public static void register() {
        Registry.register(Registries.CHUNK_GENERATOR, new Identifier(MiddleEarth.MOD_ID, PATH), MiddleEarthChunkGenerator.CODEC);
        WORLD_KEY = RegistryKey.of(RegistryKeys.WORLD, new Identifier(MiddleEarth.MOD_ID, PATH));

        MiddleEarth.LOGGER.debug("Registering ModDimensions for " + MiddleEarth.MOD_ID);
    }
}
