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
    public static final RegistryKey<Biome> BLUE_MOUNTAINS_FOOTHILLS = MEBiomeKeys.register("blue_mountains_foothills");
    public static final RegistryKey<Biome> CORSAIR_COASTS = MEBiomeKeys.register("corsair_coasts");
    public static final RegistryKey<Biome> DALE = MEBiomeKeys.register("dale");
    public static final RegistryKey<Biome> DORWINION_HILLS = MEBiomeKeys.register("dorwinion_hills");
    public static final RegistryKey<Biome> DUNLAND_FOOTHILLS = MEBiomeKeys.register("dunland_foothills");
    public static final RegistryKey<Biome> EASTERN_RHOVANION = MEBiomeKeys.register("eastern_rhovanion");
    public static final RegistryKey<Biome> ENEDWAITH = MEBiomeKeys.register("enedwaith");
    public static final RegistryKey<Biome> EREGION = MEBiomeKeys.register("eregion");
    public static final RegistryKey<Biome> ERIADOR = MEBiomeKeys.register("eriador");
    public static final RegistryKey<Biome> FANGORN = MEBiomeKeys.register("fangorn");
    public static final RegistryKey<Biome> FORODWAITH = MEBiomeKeys.register("forodwaith");
    public static final RegistryKey<Biome> FROZEN_OCEAN = MEBiomeKeys.register("frozen_ocean");
    public static final RegistryKey<Biome> GONDOR = MEBiomeKeys.register("gondor");
    public static final RegistryKey<Biome> GREY_PLAINS = MEBiomeKeys.register("grey_plains");
    public static final RegistryKey<Biome> HARAD = MEBiomeKeys.register("harad");
    public static final RegistryKey<Biome> HARAD_DESERT = MEBiomeKeys.register("harad_desert");
    public static final RegistryKey<Biome> HARONDOR = MEBiomeKeys.register("harondor");
    public static final RegistryKey<Biome> IRON_HILLS = MEBiomeKeys.register("iron_hills");
    public static final RegistryKey<Biome> LINDON = MEBiomeKeys.register("lindon");
    public static final RegistryKey<Biome> LONG_LAKE = MEBiomeKeys.register("long_lake");
    public static final RegistryKey<Biome> LORIEN_EDGE = MEBiomeKeys.register("lorien_edge");
    public static final RegistryKey<Biome> LOTHLORIEN = MEBiomeKeys.register("lothlorien");
    public static final RegistryKey<Biome> MILLPOND = MEBiomeKeys.register("millpond");
    public static final RegistryKey<Biome> MIRKWOOD = MEBiomeKeys.register("mirkwood");
    public static final RegistryKey<Biome> MISTY_MOUNTAINS = MEBiomeKeys.register("misty_mountains");
    public static final RegistryKey<Biome> MORDOR = MEBiomeKeys.register("mordor");
    public static final RegistryKey<Biome> MORDOR_MOUNTAINS = MEBiomeKeys.register("mordor_mountains");
    public static final RegistryKey<Biome> MORDOR_WASTES = MEBiomeKeys.register("mordor_wastes");
    public static final RegistryKey<Biome> NORTHERN_DUNLAND = MEBiomeKeys.register("northern_dunland");
    public static final RegistryKey<Biome> NORTHERN_WASTELANDS = MEBiomeKeys.register("northern_wastelands");
    public static final RegistryKey<Biome> NURN = MEBiomeKeys.register("nurn");
    public static final RegistryKey<Biome> NURN_SEA = MEBiomeKeys.register("nurn_sea");
    public static final RegistryKey<Biome> NURN_RIVER = MEBiomeKeys.register("nurn_river");
    public static final RegistryKey<Biome> OCEAN = MEBiomeKeys.register("ocean");
    public static final RegistryKey<Biome> OCEAN_COAST = MEBiomeKeys.register("ocean_coast");
    public static final RegistryKey<Biome> OLD_ANGMAR = MEBiomeKeys.register("old_angmar");
    public static final RegistryKey<Biome> OLD_ARTHEDAIN = MEBiomeKeys.register("old_arthedain");
    public static final RegistryKey<Biome> OLD_RHUDAUR = MEBiomeKeys.register("old_rhudaur");
    public static final RegistryKey<Biome> RHUN = MEBiomeKeys.register("rhun");
    public static final RegistryKey<Biome> RIVENDELL = MEBiomeKeys.register("rivendell");
    public static final RegistryKey<Biome> RIVENDELL_FOOTHILLS = MEBiomeKeys.register("rivendell_foothills");
    public static final RegistryKey<Biome> RIVER = MEBiomeKeys.register("river");
    public static final RegistryKey<Biome> ROHAN = MEBiomeKeys.register("rohan");
    public static final RegistryKey<Biome> SEA_OF_RHUN = MEBiomeKeys.register("shire");
    public static final RegistryKey<Biome> SHIRE = MEBiomeKeys.register("shire");
    public static final RegistryKey<Biome> SOUTHEAST_RHOVANION = MEBiomeKeys.register("southeast_rhovanion");
    public static final RegistryKey<Biome> SOUTHERN_DUNLAND = MEBiomeKeys.register("southern_dunland");
    public static final RegistryKey<Biome> SOUTHERN_FOROCHEL = MEBiomeKeys.register("southern_forochel");
    public static final RegistryKey<Biome> THE_ANGLE = MEBiomeKeys.register("the_angle");
    public static final RegistryKey<Biome> TOLFALAS = MEBiomeKeys.register("tolfalas");
    public static final RegistryKey<Biome> TROLLSHAWS = MEBiomeKeys.register("trollshaws");
    public static final RegistryKey<Biome> UMBAR = MEBiomeKeys.register("umbar");
    public static final RegistryKey<Biome> WHITE_MOUNTAINS = MEBiomeKeys.register("white_mountains");

    private static RegistryKey<Biome> register(String name) {
        return RegistryKey.of(RegistryKeys.BIOME, new Identifier(MiddleEarth.MOD_ID, name));
    }

    public static void registerModBiomes() {
        MiddleEarth.LOGGER.debug("Registering ModBiomes for " + MiddleEarth.MOD_ID);
    }
}
