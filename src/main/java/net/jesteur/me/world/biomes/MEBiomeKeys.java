package net.jesteur.me.world.biomes;

import net.jesteur.me.MiddleEarth;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;

public class MEBiomeKeys extends BiomeKeys {
    public static final RegistryKey<Biome> ANDUIN = MEBiomeKeys.register("anduin");
    public static final RegistryKey<Biome> BLUE_MOUNTAINS = MEBiomeKeys.register("blue_mountains");
    public static final RegistryKey<Biome> ERIADOR = MEBiomeKeys.register("eriador");
    public static final RegistryKey<Biome> FANGORN = MEBiomeKeys.register("fangorn");
    public static final RegistryKey<Biome> FORODWAITH = MEBiomeKeys.register("forodwaith");
    public static final RegistryKey<Biome> GONDOR = MEBiomeKeys.register("gondor");
    public static final RegistryKey<Biome> HARAD = MEBiomeKeys.register("harad");
    public static final RegistryKey<Biome> IRON_HILLS = MEBiomeKeys.register("iron_hills");
    public static final RegistryKey<Biome> LOTHLORIEN = MEBiomeKeys.register("lothlorien");
    public static final RegistryKey<Biome> MIRKWOOD = MEBiomeKeys.register("mirkwood");
    public static final RegistryKey<Biome> MISTY_MOUNTAINS = MEBiomeKeys.register("misty_mountains");
    public static final RegistryKey<Biome> MORDOR = MEBiomeKeys.register("mordor");
    public static final RegistryKey<Biome> MORDOR_MOUNTAINS = MEBiomeKeys.register("mordor_mountains");
    public static final RegistryKey<Biome> MORDOR_WASTES = MEBiomeKeys.register("mordor_wastes");
    public static final RegistryKey<Biome> NORTHEN_WASTELANDS = MEBiomeKeys.register("northen_wastelands");
    public static final RegistryKey<Biome> OCEAN = MEBiomeKeys.register("ocean");
    public static final RegistryKey<Biome> RHUN = MEBiomeKeys.register("rhun");
    public static final RegistryKey<Biome> RIVER = MEBiomeKeys.register("river");
    public static final RegistryKey<Biome> SHIRE = MEBiomeKeys.register("shire");
    public static final RegistryKey<Biome> WHITE_MOUNTAINS = MEBiomeKeys.register("white_mountains");
    private static RegistryKey<Biome> register(String name) {
        return RegistryKey.of(RegistryKeys.BIOME, new Identifier(MiddleEarth.MOD_ID, name));
    }

    public static void registerModBiomes() {
        MiddleEarth.LOGGER.debug("Registering ModBiomes for " + MiddleEarth.MOD_ID);
    }
}
