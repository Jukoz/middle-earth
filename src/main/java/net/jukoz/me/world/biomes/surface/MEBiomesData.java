package net.jukoz.me.world.biomes.surface;

import net.jukoz.me.utils.LoggerUtil;
import net.jukoz.me.world.biomes.MEBiomeDataConfigs;
import net.jukoz.me.world.biomes.MEBiomeKeys;
import net.jukoz.me.world.biomes.caves.CaveType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.world.biome.Biome;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Converts PNG pixel color to a BiomeKey reference.
 */
public class MEBiomesData {
    private static List<MEBiome> biomes = new ArrayList<>();
    public static List<RegistryKey<Biome>> waterBiomes = new ArrayList<>();
    public static List<RegistryKey<Biome>> riverbiomes = new ArrayList<>();
    public static List<RegistryKey<Biome>> frozenBiomes = new ArrayList<>();
    public static List<RegistryKey<Biome>> wastePondBiomes = new ArrayList<>();
    public static List<RegistryKey<Biome>> mirkwoodSwampBiomes = new ArrayList<>();
    public static List<RegistryKey<Biome>> oasisBiomes = new ArrayList<>();
    public static List<RegistryKey<Biome>> anduinWaterBiomes = new ArrayList<>();
    public static List<RegistryKey<Biome>> deadMarshesBiomes = new ArrayList<>();

    public static MEBiome defaultBiome;
    public static MEBiome frozenPond;
    public static MEBiome oasis;
    public static MEBiome pond;
    public static MEBiome greatRiver;
    public static MEBiome wastePond;
    public static MEBiome mirkwoodSwamp;
    public static MEBiome deadMarshes;
    public static MEBiome deadMarshesWater;

    public static void addBiome(Color color, MEBiome biome) {
        biome.color = color;
        biomes.add(biome);
    }

    public static MEBiome getBiomeByColor(Integer rgb) throws Exception{
        try{
            return biomes.stream().filter(x -> x.color.getRGB() == rgb).findFirst().get();
        } catch (Exception e){
            Color color = new Color(rgb);
            LoggerUtil.getInstance().logError("MeBiomes::No registered biome has %s for color".formatted(color.toString()));
            throw new Exception();
        }
    }

    public static MEBiome getBiomeById(Short id){
        try{
            return biomes.get(id);
        } catch (Exception e){
            LoggerUtil.getInstance().logError("MeBiomes::No registered biome has %s for id".formatted(id));
            return null;
        }
    }

    public static Integer getColorByBiomeId(Short id){
        try{
            return biomes.get(id).color.getRGB();
        } catch (Exception e){
            LoggerUtil.getInstance().logError("MeBiomes::No registered biome has %s for id".formatted(id));
        }
        return null;
    }

    public static void loadBiomes() {
        defaultBiome = new MEBiome(-17, MEBiomeKeys.OCEAN, MEBiomeDataConfigs.oceanModifier, MEBiomeDataConfigs.ocean, MEBiomeDataConfigs.stoneLayers);
        // Ponds
        frozenPond = new MEBiome(-10, MEBiomeKeys.FROZEN_POND, MEBiomeDataConfigs.riverModifier, MEBiomeDataConfigs.ocean, MEBiomeDataConfigs.stoneLayers, CaveType.FOROD);
        oasis = new MEBiome(-10, MEBiomeKeys.OASIS, MEBiomeDataConfigs.riverModifier, MEBiomeDataConfigs.beach, MEBiomeDataConfigs.sandstoneLayers, CaveType.HARAD);
        pond = new MEBiome(-10, MEBiomeKeys.POND, MEBiomeDataConfigs.riverModifier, MEBiomeDataConfigs.ocean, MEBiomeDataConfigs.stoneLayers);
        mirkwoodSwamp = new MEBiome(-10, MEBiomeKeys.MIRKWOOD_SWAMP, MEBiomeDataConfigs.riverModifier, MEBiomeDataConfigs.ocean, MEBiomeDataConfigs.stoneLayers);
        greatRiver = new MEBiome(-10, MEBiomeKeys.GREAT_RIVER, MEBiomeDataConfigs.riverModifier, MEBiomeDataConfigs.river, MEBiomeDataConfigs.stoneLayers);
        wastePond = new MEBiome(-10, MEBiomeKeys.WASTE_POND, MEBiomeDataConfigs.riverModifier, MEBiomeDataConfigs.ashenDirt, MEBiomeDataConfigs.ashenStoneLayers);
        deadMarshes = new MEBiome(0, MEBiomeKeys.DEAD_MARSHES, MEBiomeDataConfigs.plainsModifier.noiseModifier(0f), MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.stoneLayers);
        deadMarshesWater = new MEBiome(-10, MEBiomeKeys.DEAD_MARSHES_WATER, MEBiomeDataConfigs.riverModifier, MEBiomeDataConfigs.ashenDirt, MEBiomeDataConfigs.ashenStoneLayers);

        // Water Biomes :
        addBiome(new Color(55, 90, 195), defaultBiome);
        addBiome(new Color(104, 168, 222), oasis);
        addBiome(new Color(104, 168, 222), frozenPond);
        addBiome(new Color(110, 154, 218), pond);
        addBiome(new Color(75, 108, 143), wastePond);
        addBiome(new Color(90, 124, 161), deadMarshesWater);

        addBiome(new Color(78, 99, 122), new MEBiome(-4, MEBiomeKeys.EMYN_MUIL_POND, MEBiomeDataConfigs.emynMuilModifier, MEBiomeDataConfigs.emynMuil, MEBiomeDataConfigs.stoneLayers));
        addBiome(new Color(101, 123, 243), new MEBiome(-12, MEBiomeKeys.FROZEN_OCEAN, MEBiomeDataConfigs.oceanModifier, MEBiomeDataConfigs.ocean, MEBiomeDataConfigs.stoneLayers, CaveType.FOROD));
        addBiome(new Color(69, 92, 228), new MEBiome(-10, MEBiomeKeys.LONG_LAKE, MEBiomeDataConfigs.oceanModifier, MEBiomeDataConfigs.ocean, MEBiomeDataConfigs.stoneLayers));
        addBiome(new Color(72, 109, 114), new MEBiome(-7, MEBiomeKeys.MORGUL_RIVER, MEBiomeDataConfigs.riverModifier, MEBiomeDataConfigs.river, MEBiomeDataConfigs.ashenStoneLayers));
        addBiome(new Color(79, 91, 161), new MEBiome(-7, MEBiomeKeys.NURN_RIVER, MEBiomeDataConfigs.riverModifier, MEBiomeDataConfigs.river, MEBiomeDataConfigs.stoneLayers));
        addBiome(new Color(88, 94, 130), new MEBiome(-12, MEBiomeKeys.NURN_SEA, MEBiomeDataConfigs.oceanModifier, MEBiomeDataConfigs.ocean, MEBiomeDataConfigs.stoneLayers));
        addBiome(new Color(75, 106, 199), new MEBiome(-11, MEBiomeKeys.OCEAN_COAST, MEBiomeDataConfigs.oceanModifier, MEBiomeDataConfigs.ocean, MEBiomeDataConfigs.stoneLayers));
        addBiome(new Color(83, 129, 186), new MEBiome(-6, MEBiomeKeys.RIVER, MEBiomeDataConfigs.smallRiverModifier, MEBiomeDataConfigs.river, MEBiomeDataConfigs.stoneLayers));
        addBiome(new Color(77, 119, 137), new MEBiome(-6, MEBiomeKeys.FOREST_RIVER, MEBiomeDataConfigs.smallRiverModifier, MEBiomeDataConfigs.river, MEBiomeDataConfigs.stoneLayers));
        addBiome(new Color(99, 138, 186), greatRiver);
        addBiome(new Color(66, 97, 157), new MEBiome(-8, MEBiomeKeys.SEA_OF_RHUN, MEBiomeDataConfigs.oceanModifier, MEBiomeDataConfigs.ocean, MEBiomeDataConfigs.stoneLayers));
        addBiome(new Color(28, 107, 86), mirkwoodSwamp);
        addBiome(new Color(20, 79, 50), new MEBiome(3, MEBiomeKeys.MIRKWOOD_MARSHES, MEBiomeDataConfigs.landModifier, MEBiomeDataConfigs.mud, MEBiomeDataConfigs.stoneLayers));


        // Land Biomes :
        addBiome(new Color(156, 207, 113), new MEBiome(4, MEBiomeKeys.ANDUIN_VALES, MEBiomeDataConfigs.landModifier, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.stoneLayers));
        addBiome(new Color(71, 169, 66), new MEBiome(0, MEBiomeKeys.ANORIEN_RIVERSIDE, MEBiomeDataConfigs.plainsModifier.heightModifier(0.98f).noiseModifier(0.1f), MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.gondorLayers));
        addBiome(new Color(79, 187, 73), new MEBiome(10, MEBiomeKeys.ANORIEN, MEBiomeDataConfigs.plainsModifier, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.gondorLayers));
        addBiome(new Color(107, 205, 102), new MEBiome(19, MEBiomeKeys.ANORIEN_FOOTHILLS, MEBiomeDataConfigs.foothillModifier.heightModifier(0.28f), MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.gondorLayers));
        addBiome(new Color(96, 171, 89), new MEBiome(6, MEBiomeKeys.BARROW_DOWNS, MEBiomeDataConfigs.landModifier, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.stoneLayers));
        addBiome(new Color(38, 207, 94), new MEBiome(4, MEBiomeKeys.BELFALAS, MEBiomeDataConfigs.landModifier, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.gondorLayers));
        addBiome(new Color(35, 173, 81), new MEBiome(37, MEBiomeKeys.BELFALAS_HILLS, MEBiomeDataConfigs.mountainModifier, MEBiomeDataConfigs.whiteMountains, MEBiomeDataConfigs.gondorLayers));
        addBiome(new Color(178, 183, 210), new MEBiome(53, MEBiomeKeys.BLUE_MOUNTAINS_BASE, MEBiomeDataConfigs.bmModifier, MEBiomeDataConfigs.blueMountainsBase, MEBiomeDataConfigs.gonluinLayers));
        addBiome(new Color(200, 209, 255), new MEBiome(82, MEBiomeKeys.BLUE_MOUNTAINS, MEBiomeDataConfigs.bmModifier, MEBiomeDataConfigs.blueMountains, MEBiomeDataConfigs.gonluinLayers));
        addBiome(new Color(217, 224, 255), new MEBiome(97, MEBiomeKeys.BLUE_MOUNTAINS_PEAKS, MEBiomeDataConfigs.bmPeaksModifier, MEBiomeDataConfigs.blueMountainsPeaks, MEBiomeDataConfigs.gonluinLayers));
        addBiome(new Color(88, 82, 71), new MEBiome(4, MEBiomeKeys.BROWN_LANDS, MEBiomeDataConfigs.landModifier, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.stoneLayers));
        addBiome(new Color(193, 188, 131), new MEBiome(6, MEBiomeKeys.CORSAIR_COASTS, MEBiomeDataConfigs.landModifier, MEBiomeDataConfigs.nearHarad, MEBiomeDataConfigs.sandstoneLayers, CaveType.HARAD));
        addBiome(new Color(77, 69, 65), new MEBiome(4, MEBiomeKeys.DAGORLAD, MEBiomeDataConfigs.landModifier, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.stoneLayers));
        addBiome(new Color(132, 164, 78), new MEBiome(4, MEBiomeKeys.DALE, MEBiomeDataConfigs.landModifier, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.stoneLayers));
        addBiome(new Color(125, 176, 106), new MEBiome(0, MEBiomeKeys.DALE_RIVERSIDE, MEBiomeDataConfigs.landModifier.heightModifier(0.98f).noiseModifier(0.1f), MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.stoneLayers));
        addBiome(new Color(18, 26, 19), new MEBiome(6, MEBiomeKeys.DARK_MIRKWOOD, MEBiomeDataConfigs.mirkwoodModifier, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.stoneLayers));
        addBiome(new Color(26, 45, 28), new MEBiome(5, MEBiomeKeys.DARK_MIRKWOOD_EDGE, MEBiomeDataConfigs.landModifier, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.stoneLayers));
        addBiome(new Color(166, 191, 114), new MEBiome(4, MEBiomeKeys.DARK_ANDUIN_VALES, MEBiomeDataConfigs.landModifier, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.stoneLayers));
        addBiome(new Color(48, 94, 66), deadMarshes);
        addBiome(new Color(44, 39, 51), new MEBiome(11, MEBiomeKeys.DOL_GULDUR, MEBiomeDataConfigs.foothillModifier, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.stoneLayers));
        addBiome(new Color(53, 45, 66), new MEBiome(31, MEBiomeKeys.DOL_GULDUR_HILL, MEBiomeDataConfigs.mountainModifier, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.stoneLayers));
        addBiome(new Color(82, 146, 80), new MEBiome(4, MEBiomeKeys.DORWINION, MEBiomeDataConfigs.landModifier, MEBiomeDataConfigs.limeStoneHills, MEBiomeDataConfigs.limeStoneLayers));
        addBiome(new Color(93, 113, 92), new MEBiome(34, MEBiomeKeys.DORWINION_HILLS, MEBiomeDataConfigs.foothillModifier, MEBiomeDataConfigs.limeStoneHills, MEBiomeDataConfigs.limeStoneLayers));
        addBiome(new Color(132, 137, 124), new MEBiome(34, MEBiomeKeys.DUNLAND_FOOTHILLS, MEBiomeDataConfigs.foothillModifier, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.stoneLayers));
        addBiome(new Color(162, 197, 133), new MEBiome(4, MEBiomeKeys.EAST_BIGHT, MEBiomeDataConfigs.landModifier, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.stoneLayers));
        addBiome(new Color(101, 89, 80), new MEBiome(5, MEBiomeKeys.EASTERN_NURN,MEBiomeDataConfigs.landModifier, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.stoneLayers));
        addBiome(new Color(140, 150, 84), new MEBiome(4, MEBiomeKeys.EASTERN_RHOVANION, MEBiomeDataConfigs.landModifier, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.stoneLayers));
        addBiome(new Color(120, 107, 84), new MEBiome(57, MEBiomeKeys.EMYN_MUIL_CLIFFS, MEBiomeDataConfigs.emynMuilModifier, MEBiomeDataConfigs.emynMuil, MEBiomeDataConfigs.stoneLayers));
        addBiome(new Color(133, 122, 103), new MEBiome(74, MEBiomeKeys.EMYN_MUIL, MEBiomeDataConfigs.emynMuilModifier.heightModifier(0.32f), MEBiomeDataConfigs.emynMuil, MEBiomeDataConfigs.stoneLayers));
        addBiome(new Color(148, 137, 118), new MEBiome(94, MEBiomeKeys.EMYN_MUIL_PEAKS, MEBiomeDataConfigs.emynMuilModifier.heightModifier(0.53f), MEBiomeDataConfigs.emynMuil, MEBiomeDataConfigs.stoneLayers));
        addBiome(new Color(117, 164, 109), new MEBiome(6, MEBiomeKeys.ENEDWAITH, MEBiomeDataConfigs.landModifier, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.stoneLayers));
        addBiome(new Color(185, 185, 187), new MEBiome(60, MEBiomeKeys.LONELY_MOUNTAIN_BASE, MEBiomeDataConfigs.mountainModifier.heightModifier(0.55f), MEBiomeDataConfigs.lonelyMountain, MEBiomeDataConfigs.stoneLayers));
        addBiome(new Color(168, 168, 170), new MEBiome(73, MEBiomeKeys.LONELY_MOUNTAIN, MEBiomeDataConfigs.mountainModifier.heightModifier(0.65f), MEBiomeDataConfigs.lonelyMountain, MEBiomeDataConfigs.stoneLayers));
        addBiome(new Color(151, 151, 153), new MEBiome(100, MEBiomeKeys.LONELY_MOUNTAIN_PEAKS, MEBiomeDataConfigs.mountainModifier.heightModifier(0.76f), MEBiomeDataConfigs.lonelyMountainPeak, MEBiomeDataConfigs.stoneLayers));
        addBiome(new Color(90, 159, 90), new MEBiome(7, MEBiomeKeys.LONELY_MOUNTAIN_TAIGA, MEBiomeDataConfigs.landModifier, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.stoneLayers));
        addBiome(new Color(106, 155, 104), new MEBiome(4, MEBiomeKeys.EREGION, MEBiomeDataConfigs.landModifier, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.limeStoneLayers));
        addBiome(new Color(171, 193, 128), new MEBiome(4, MEBiomeKeys.ERIADOR, MEBiomeDataConfigs.landModifier, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.stoneLayers));
        addBiome(new Color(16, 156, 109), new MEBiome(4, MEBiomeKeys.ETHIR_ANDUIN, MEBiomeDataConfigs.landModifier, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.stoneLayers));
        addBiome(new Color(54, 75, 12), new MEBiome(6, MEBiomeKeys.FANGORN, MEBiomeDataConfigs.landModifier, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.stoneLayers));
        addBiome(new Color(121, 131, 112), new MEBiome(24, MEBiomeKeys.FANGORN_FOOTHILLS, MEBiomeDataConfigs.foothillModifier, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.stoneLayers));
        addBiome(new Color(236, 236, 236), new MEBiome(14, MEBiomeKeys.FORODWAITH, MEBiomeDataConfigs.landModifier, MEBiomeDataConfigs.forodwaith, MEBiomeDataConfigs.frozenLayers, CaveType.FOROD));
        addBiome(new Color(91, 189, 85), new MEBiome(4, MEBiomeKeys.GONDOR, MEBiomeDataConfigs.landModifier, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.gondorLayers));
        addBiome(new Color(56, 36, 36), new MEBiome(6, MEBiomeKeys.GORGOROTH, MEBiomeDataConfigs.plainsModifier, MEBiomeDataConfigs.mordor, MEBiomeDataConfigs.ashenStoneLayers, CaveType.ASHEN));
        addBiome(new Color(92, 147, 92), new MEBiome(6, MEBiomeKeys.GREY_PLAINS, MEBiomeDataConfigs.landModifier, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.stoneLayers));
        addBiome(new Color(125, 113, 113), new MEBiome(30, MEBiomeKeys.GREY_MOUNTAINS_BASE, MEBiomeDataConfigs.mountainModifier.heightModifier(0.24f), MEBiomeDataConfigs.greyMountains, MEBiomeDataConfigs.greyMountainsLayers));
        addBiome(new Color(110, 98, 98), new MEBiome(61, MEBiomeKeys.GREY_MOUNTAINS, MEBiomeDataConfigs.mountainModifier.heightModifier(0.355f), MEBiomeDataConfigs.greyMountains, MEBiomeDataConfigs.greyMountainsLayers));
        addBiome(new Color(99, 90, 90), new MEBiome(88, MEBiomeKeys.GREY_MOUNTAINS_PEAKS, MEBiomeDataConfigs.mountainModifier.heightModifier(0.59f).noiseModifier(1.3f), MEBiomeDataConfigs.greyMountains, MEBiomeDataConfigs.greyMountainsLayers));
        addBiome(new Color(93, 139, 93), new MEBiome(9, MEBiomeKeys.GUNDABAD_PLAINS, MEBiomeDataConfigs.landModifier, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.stoneLayers));
        addBiome(new Color(204, 196, 113), new MEBiome(7, MEBiomeKeys.HARAD, MEBiomeDataConfigs.landModifier, MEBiomeDataConfigs.nearHarad, MEBiomeDataConfigs.sandstoneLayers, CaveType.HARAD));
        addBiome(new Color(237, 229, 145), new MEBiome(9, MEBiomeKeys.HARAD_DESERT, MEBiomeDataConfigs.landModifier, MEBiomeDataConfigs.harad, MEBiomeDataConfigs.sandstoneLayers, CaveType.HARAD));
        addBiome(new Color(180, 214, 121), new MEBiome(6, MEBiomeKeys.HARONDOR, MEBiomeDataConfigs.landModifier, MEBiomeDataConfigs.nearHarad, MEBiomeDataConfigs.sandstoneLayers, CaveType.HARAD));
        addBiome(new Color(156, 187, 94), new MEBiome(35, MEBiomeKeys.HILLS_OF_EVENDIM, MEBiomeDataConfigs.foothillModifier, MEBiomeDataConfigs.stoneHills, MEBiomeDataConfigs.sandstoneLayers));
        addBiome(new Color(110, 161, 99), new MEBiome(8, MEBiomeKeys.IRON_HILLS_PLAINS, MEBiomeDataConfigs.landModifier, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.stoneLayers));
        addBiome(new Color(109, 175, 109), new MEBiome(25, MEBiomeKeys.IRON_FOOTHILLS, MEBiomeDataConfigs.foothillModifier, MEBiomeDataConfigs.ironHills, MEBiomeDataConfigs.stoneLayers));
        addBiome(new Color(189, 170, 168), new MEBiome(32, MEBiomeKeys.IRON_HILLS_BASE, MEBiomeDataConfigs.mountainModifier.heightModifier(0.48f), MEBiomeDataConfigs.ironHills, MEBiomeDataConfigs.stoneLayers));
        addBiome(new Color(174, 144, 141), new MEBiome(51, MEBiomeKeys.IRON_HILLS, MEBiomeDataConfigs.mountainModifier.heightModifier(0.57f), MEBiomeDataConfigs.ironHills, MEBiomeDataConfigs.stoneLayers));
        addBiome(new Color(173, 130, 127), new MEBiome(72, MEBiomeKeys.IRON_HILLS_PEAKS, MEBiomeDataConfigs.mountainModifier.heightModifier(0.68f), MEBiomeDataConfigs.ironHills, MEBiomeDataConfigs.stoneLayers));
        addBiome(new Color(97, 157, 89), new MEBiome(5, MEBiomeKeys.ISENGARD, MEBiomeDataConfigs.landModifier, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.stoneLayers));
        addBiome(new Color(106, 162, 98), new MEBiome(32, MEBiomeKeys.ISENGARD_HILL, MEBiomeDataConfigs.landModifier.heightModifier(0.51f), MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.stoneLayers));
        addBiome(new Color(4, 117, 42), new MEBiome(5, MEBiomeKeys.ITHILIEN, MEBiomeDataConfigs.landModifier, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.gondorLayers));
        addBiome(new Color(51, 100, 67), new MEBiome(5, MEBiomeKeys.ITHILIEN_WASTES, MEBiomeDataConfigs.landModifier, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.gondorLayers));
        addBiome(new Color(164, 255, 164), new MEBiome(7, MEBiomeKeys.LAMEDON, MEBiomeDataConfigs.landModifier, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.gondorLayers));
        addBiome(new Color(66, 220, 56), new MEBiome(4, MEBiomeKeys.LEBENNIN, MEBiomeDataConfigs.landModifier, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.gondorLayers));
        addBiome(new Color(67, 193, 125), new MEBiome(4, MEBiomeKeys.LINDON, MEBiomeDataConfigs.landModifier, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.limeStoneLayers));
        addBiome(new Color(67, 133, 100), new MEBiome(3, MEBiomeKeys.LONG_MARSHES, MEBiomeDataConfigs.landModifier, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.stoneLayers));
        addBiome(new Color(205, 206, 96), new MEBiome(5, MEBiomeKeys.LORIEN_EDGE, MEBiomeDataConfigs.landModifier, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.limeStoneLayers));
        addBiome(new Color(221, 216, 28), new MEBiome(6, MEBiomeKeys.LOTHLORIEN, MEBiomeDataConfigs.landModifier, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.limeStoneLayers));
        addBiome(new Color(145, 164, 109), new MEBiome(4, MEBiomeKeys.MINHIRIATH, MEBiomeDataConfigs.landModifier, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.stoneLayers));
        addBiome(new Color(10, 54, 15), new MEBiome(8, MEBiomeKeys.MIRKWOOD, MEBiomeDataConfigs.mirkwoodModifier, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.stoneLayers));
        addBiome(new Color(18, 73, 24), new MEBiome(5, MEBiomeKeys.MIRKWOOD_EDGE, MEBiomeDataConfigs.landModifier, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.stoneLayers));
        addBiome(new Color(46, 83, 50), new MEBiome(32, MEBiomeKeys.MIRKWOOD_FOOTHILLS, MEBiomeDataConfigs.foothillModifier.heightModifier(0.28f), MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.stoneLayers));
        addBiome(new Color(76, 85, 77), new MEBiome(56, MEBiomeKeys.MIRKWOOD_MOUNTAINS_BASE, MEBiomeDataConfigs.mountainModifier, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.stoneLayers));
        addBiome(new Color(90, 101, 91), new MEBiome(80, MEBiomeKeys.MIRKWOOD_MOUNTAINS, MEBiomeDataConfigs.mountainModifier, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.stoneLayers));
        addBiome(new Color(108, 122, 110), new MEBiome(120, MEBiomeKeys.MIRKWOOD_MOUNTAINS_PEAKS, MEBiomeDataConfigs.mountainModifier, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.stoneLayers));
        addBiome(new Color(129, 131, 125), new MEBiome(37, MEBiomeKeys.MISTY_MOUNTAINS_BASE, MEBiomeDataConfigs.mountainModifier.heightModifier(0.24f), MEBiomeDataConfigs.mistyMountains, MEBiomeDataConfigs.mistyMountainsLayers, CaveType.MISTIES));
        addBiome(new Color(121, 121, 121), new MEBiome(70, MEBiomeKeys.MISTY_MOUNTAINS, MEBiomeDataConfigs.mountainModifier.heightModifier(0.34f), MEBiomeDataConfigs.mistyMountains, MEBiomeDataConfigs.mistyMountainsLayers, CaveType.MISTIES));
        addBiome(new Color(110, 110, 110), new MEBiome(90, MEBiomeKeys.MISTY_MOUNTAINS_PEAKS, MEBiomeDataConfigs.mountainModifier.heightModifier(0.39f), MEBiomeDataConfigs.mistiesPeaks, MEBiomeDataConfigs.mistyMountainsLayers, CaveType.MISTIES));
        addBiome(new Color(60, 42, 42), new MEBiome(7, MEBiomeKeys.MORDOR, MEBiomeDataConfigs.landModifier, MEBiomeDataConfigs.mordor, MEBiomeDataConfigs.ashenStoneLayers, CaveType.ASHEN));
        addBiome(new Color(36, 31, 31), new MEBiome(73, MEBiomeKeys.MORDOR_MOUNTAINS, MEBiomeDataConfigs.mordorMountainModifier, MEBiomeDataConfigs.mordorMountainsPeaks, MEBiomeDataConfigs.ashenStoneLayers, CaveType.ASHEN));
        addBiome(new Color(45, 42, 42), new MEBiome(36, MEBiomeKeys.MORDOR_MOUNTAINS_FOOTHILLS, MEBiomeDataConfigs.mordorMountainModifier.heightModifier(0.33f), MEBiomeDataConfigs.mordorMountains, MEBiomeDataConfigs.ashenStoneLayers, CaveType.ASHEN));
        addBiome(new Color(26, 23, 23), new MEBiome(96, MEBiomeKeys.MORDOR_MOUNTAINS_PEAKS, MEBiomeDataConfigs.mordorMountainModifier.heightModifier(0.38f), MEBiomeDataConfigs.mordorMountainsPeaks, MEBiomeDataConfigs.ashenStoneLayers, CaveType.ASHEN));
        addBiome(new Color(88, 82, 71), new MEBiome(3, MEBiomeKeys.MORDOR_WASTES, MEBiomeDataConfigs.landModifier, MEBiomeDataConfigs.ashenDirt, MEBiomeDataConfigs.ashenStoneLayers, CaveType.ASHEN));
        addBiome(new Color(45, 58, 44), new MEBiome(3, MEBiomeKeys.MORGUL_VALE, MEBiomeDataConfigs.landModifier, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.ashenStoneLayers, CaveType.ASHEN));
        addBiome(new Color(99, 94, 94), new MEBiome(87, MEBiomeKeys.MOUNT_GUNDABAD, MEBiomeDataConfigs.mountainModifier.heightModifier(0.59f), MEBiomeDataConfigs.greyMountains, MEBiomeDataConfigs.greyMountainsLayers, CaveType.MISTIES));
        addBiome(new Color(96, 39, 13), new MEBiome(90, MEBiomeKeys.MOUNT_DOOM, MEBiomeDataConfigs.mordorMountainModifier.heightModifier(0.36f).noiseModifier(1.0f).expansionWeight(new byte[]{2, 3}), MEBiomeDataConfigs.mordorMountainsPeaks, MEBiomeDataConfigs.ashenStoneLayers, CaveType.ASHEN));
        addBiome(new Color(97, 155, 89), new MEBiome(5, MEBiomeKeys.NAN_CURUNIR, MEBiomeDataConfigs.landModifier, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.stoneLayers));
        addBiome(new Color(104, 146, 196), new MEBiome(-4, MEBiomeKeys.NEN_HITHOEL, MEBiomeDataConfigs.riverModifier, MEBiomeDataConfigs.ocean, MEBiomeDataConfigs.stoneLayers));
        addBiome(new Color(126, 158, 57), new MEBiome(4, MEBiomeKeys.NEN_HITHOEL_FOREST, MEBiomeDataConfigs.landModifier.heightModifier(0.13f), MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.stoneLayers));
        addBiome(new Color(142, 179, 64), new MEBiome(1, MEBiomeKeys.NEN_HITHOEL_SHORES, MEBiomeDataConfigs.landModifier.heightModifier(0.98f).noiseModifier(0.1f), MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.gondorLayers));
        addBiome(new Color(43, 158, 120), new MEBiome(4, MEBiomeKeys.NINDALF, MEBiomeDataConfigs.landModifier, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.stoneLayers));
        addBiome(new Color(177, 188, 154), new MEBiome(34, MEBiomeKeys.NORTH_DOWNS, MEBiomeDataConfigs.foothillModifier, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.stoneLayers));
        addBiome(new Color(106, 127, 62), new MEBiome(5, MEBiomeKeys.NORTHERN_DUNLAND,  MEBiomeDataConfigs.landModifier, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.stoneLayers));
        addBiome(new Color(26, 71, 49), new MEBiome(3, MEBiomeKeys.NORTHERN_MIRKWOOD_MARSHES, MEBiomeDataConfigs.landModifier, MEBiomeDataConfigs.mud, MEBiomeDataConfigs.stoneLayers));
        addBiome(new Color(16, 60, 37), new MEBiome(4, MEBiomeKeys.NORTHERN_MIRKWOOD_SWAMP,  MEBiomeDataConfigs.landModifier, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.stoneLayers));
        addBiome(new Color(160, 165, 149), new MEBiome(6, MEBiomeKeys.NORTHERN_WASTELANDS, MEBiomeDataConfigs.landModifier, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.frozenLayers, CaveType.FOROD));
        addBiome(new Color(74, 77, 51), new MEBiome(5, MEBiomeKeys.NURN,MEBiomeDataConfigs.landModifier, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.stoneLayers));
        addBiome(new Color(69, 59, 50), new MEBiome(6, MEBiomeKeys.NURN_EDGE,MEBiomeDataConfigs.landModifier, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.stoneLayers));
        addBiome(new Color(73, 82, 53), new MEBiome(6, MEBiomeKeys.OLD_ANGMAR, MEBiomeDataConfigs.landModifier, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.stoneLayers));
        addBiome(new Color(200, 238, 128), new MEBiome(4, MEBiomeKeys.OLD_ARTHEDAIN, MEBiomeDataConfigs.landModifier, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.stoneLayers));
        addBiome(new Color(193, 222, 140), new MEBiome(23, MEBiomeKeys.OLD_ARTHEDAIN_FOOTHILL, MEBiomeDataConfigs.foothillModifier.heightModifier(0.2f), MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.stoneLayers));
        addBiome(new Color(156, 185, 97), new MEBiome(4, MEBiomeKeys.OLD_CARDOLAN, MEBiomeDataConfigs.landModifier, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.stoneLayers));
        addBiome(new Color(115, 135, 74), new MEBiome(5, MEBiomeKeys.OLD_RHUDAUR, MEBiomeDataConfigs.landModifier, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.stoneLayers));
        addBiome(new Color(65, 160, 59), new MEBiome(4, MEBiomeKeys.OSGILIATH, MEBiomeDataConfigs.landModifier, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.gondorLayers));
        addBiome(new Color(172, 176, 113), new MEBiome(4, MEBiomeKeys.RHUN, MEBiomeDataConfigs.landModifier, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.stoneLayers));
        addBiome(new Color(155, 193, 160), new MEBiome(3, MEBiomeKeys.HIGH_MOOR_VALE, MEBiomeDataConfigs.landModifier.noiseModifier(0.1f).heightModifier(0.99f), MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.limeStoneLayers));
        addBiome(new Color(137, 171, 142), new MEBiome(36, MEBiomeKeys.HIGH_MOOR, MEBiomeDataConfigs.landModifier.noiseModifier(0.3f).heightModifier(0.66f), MEBiomeDataConfigs.limeStoneHills, MEBiomeDataConfigs.limeStoneLayers));
        addBiome(new Color(126, 157, 130), new MEBiome(43, MEBiomeKeys.HIGH_MOOR_HILLS, MEBiomeDataConfigs.landModifier.noiseModifier(0.4f).heightModifier(0.34f), MEBiomeDataConfigs.limeStoneMountains, MEBiomeDataConfigs.limeStoneLayers));
        addBiome(new Color(183, 229, 102), new MEBiome(4, MEBiomeKeys.ROHAN, MEBiomeDataConfigs.landModifier, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.stoneLayers));
        addBiome(new Color(149, 173, 95), new MEBiome(5, MEBiomeKeys.SARN_GEBIR_SHORES, MEBiomeDataConfigs.landModifier.heightModifier(0.98f).noiseModifier(0.1f), MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.gondorLayers));
        addBiome(new Color(136, 158, 87), new MEBiome(11, MEBiomeKeys.SARN_GEBIR_WILDLANDS, MEBiomeDataConfigs.landModifier.heightModifier(0.67f), MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.stoneLayers));
        addBiome(new Color(84, 217, 70), new MEBiome(6, MEBiomeKeys.SHIRE, MEBiomeDataConfigs.shireModifier, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.stoneLayers));
        addBiome(new Color(75, 184, 64), new MEBiome(6, MEBiomeKeys.SHIRE_EDGE, MEBiomeDataConfigs.shireModifier, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.stoneLayers));
        addBiome(new Color(61, 152, 52), new MEBiome(7, MEBiomeKeys.SHIRE_WOODS, MEBiomeDataConfigs.shireModifier, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.stoneLayers));
        addBiome(new Color(154, 147, 57), new MEBiome(4, MEBiomeKeys.SOUTHEAST_RHOVANION, MEBiomeDataConfigs.landModifier, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.stoneLayers));
        addBiome(new Color(121, 186, 111), new MEBiome(4, MEBiomeKeys.SOUTHERN_DUNLAND, MEBiomeDataConfigs.landModifier, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.stoneLayers));
        addBiome(new Color(206, 179, 156), new MEBiome(4, MEBiomeKeys.SOUTHERN_FOROCHEL, MEBiomeDataConfigs.landModifier, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.frozenLayers, CaveType.FOROD));
        addBiome(new Color(39, 37, 37), new MEBiome(73, MEBiomeKeys.SOUTHERN_EPHEL_DUATH, MEBiomeDataConfigs.mordorMountainModifier, MEBiomeDataConfigs.southernMordorMountainsPeaks, MEBiomeDataConfigs.ashenStoneLayers, CaveType.ASHEN));
        addBiome(new Color(49, 46, 46), new MEBiome(36, MEBiomeKeys.SOUTHERN_EPHEL_DUATH_BASE, MEBiomeDataConfigs.mordorMountainModifier.heightModifier(0.33f), MEBiomeDataConfigs.southernMordorMountains, MEBiomeDataConfigs.ashenStoneLayers, CaveType.ASHEN));
        addBiome(new Color(28, 26, 26), new MEBiome(97, MEBiomeKeys.SOUTHERN_EPHEL_DUATH_PEAKS, MEBiomeDataConfigs.mordorMountainModifier.heightModifier(0.38f), MEBiomeDataConfigs.southernMordorMountainsPeaks, MEBiomeDataConfigs.ashenStoneLayers, CaveType.ASHEN));
        addBiome(new Color(126, 149, 79), new MEBiome(4, MEBiomeKeys.THE_ANGLE, MEBiomeDataConfigs.landModifier, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.stoneLayers));
        addBiome(new Color(48, 109, 42), new MEBiome(7, MEBiomeKeys.THE_OLD_FOREST, MEBiomeDataConfigs.landModifier, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.stoneLayers));
        addBiome(new Color(152, 174, 113), new MEBiome(4, MEBiomeKeys.THE_WOLD, MEBiomeDataConfigs.landModifier, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.stoneLayers));
        addBiome(new Color(156, 150, 150), new MEBiome(13, MEBiomeKeys.TOLFALAS, MEBiomeDataConfigs.landModifier, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.stoneLayers));
        addBiome(new Color(58, 54, 46), new MEBiome(8, MEBiomeKeys.TOROGWAITH, MEBiomeDataConfigs.landModifier, MEBiomeDataConfigs.mordor, MEBiomeDataConfigs.ashenStoneLayers, CaveType.ASHEN));
        addBiome(new Color(40, 66, 42), new MEBiome(8, MEBiomeKeys.TROLLSHAWS, MEBiomeDataConfigs.landModifier, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.stoneLayers));
        addBiome(new Color(73, 46, 46), new MEBiome(5, MEBiomeKeys.UDUN, MEBiomeDataConfigs.landModifier, MEBiomeDataConfigs.mordor, MEBiomeDataConfigs.ashenStoneLayers, CaveType.ASHEN));
        addBiome(new Color(225, 192, 133), new MEBiome(7, MEBiomeKeys.UMBAR, MEBiomeDataConfigs.landModifier, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.stoneLayers, CaveType.HARAD));
        addBiome(new Color(24, 60, 28), new MEBiome(8, MEBiomeKeys.WEBBED_WOODS, MEBiomeDataConfigs.mirkwoodModifier, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.stoneLayers));
        addBiome(new Color(138, 134, 112), new MEBiome(8, MEBiomeKeys.WITHERED_HEATH, MEBiomeDataConfigs.landModifier, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.stoneLayers));
        addBiome(new Color(198, 237, 192), new MEBiome(50, MEBiomeKeys.WHITE_MOUNTAINS_BASE, MEBiomeDataConfigs.mountainModifier.noiseModifier(0.9f).heightModifier(0.27f), MEBiomeDataConfigs.whiteMountains, MEBiomeDataConfigs.gondorLayers));
        addBiome(new Color(219, 245, 215), new MEBiome(70, MEBiomeKeys.WHITE_MOUNTAINS, MEBiomeDataConfigs.mountainModifier.noiseModifier(1.05f).heightModifier(0.46f), MEBiomeDataConfigs.whiteMountains, MEBiomeDataConfigs.gondorLayers));
        addBiome(new Color(242, 255, 240), new MEBiome(95, MEBiomeKeys.WHITE_MOUNTAINS_PEAKS, MEBiomeDataConfigs.mountainModifier.heightModifier(0.57f), MEBiomeDataConfigs.whitePeaks, MEBiomeDataConfigs.gondorLayers));
        addBiome(new Color(22, 102, 31), new MEBiome(5, MEBiomeKeys.WOODLAND_REALM, MEBiomeDataConfigs.mountainModifier, MEBiomeDataConfigs.grassPlains, MEBiomeDataConfigs.limeStoneLayers));
        addBiome(new Color(49, 107, 46), new MEBiome(37, MEBiomeKeys.WOODLAND_FOOTHILLS, MEBiomeDataConfigs.mountainModifier.noiseModifier(0.47f).heightModifier(0.47f), MEBiomeDataConfigs.limeStoneHills, MEBiomeDataConfigs.limeStoneLayers));
        addBiome(new Color(79, 124, 76), new MEBiome(63, MEBiomeKeys.WOODLAND_HILLS, MEBiomeDataConfigs.mountainModifier.noiseModifier(0.62f).heightModifier(0.76f), MEBiomeDataConfigs.limeStoneHills, MEBiomeDataConfigs.limeStoneLayers));


        riverbiomes.add(MEBiomeKeys.RIVER);
        riverbiomes.add(MEBiomeKeys.NURN_RIVER);
        riverbiomes.add(MEBiomeKeys.FROZEN_RIVER);

        waterBiomes.add(MEBiomeKeys.EMYN_MUIL_POND);
        waterBiomes.add(MEBiomeKeys.DEAD_MARSHES_WATER);
        waterBiomes.add(MEBiomeKeys.FROZEN_POND);
        waterBiomes.add(MEBiomeKeys.FROZEN_OCEAN);
        waterBiomes.add(MEBiomeKeys.LONG_LAKE);
        waterBiomes.add(MEBiomeKeys.NURN_RIVER);
        waterBiomes.add(MEBiomeKeys.NURN_SEA);
        waterBiomes.add(MEBiomeKeys.OCEAN);
        waterBiomes.add(MEBiomeKeys.OCEAN_COAST);
        waterBiomes.add(MEBiomeKeys.RIVER);
        waterBiomes.add(MEBiomeKeys.SEA_OF_RHUN);

        anduinWaterBiomes.add(MEBiomeKeys.GREAT_RIVER);
        anduinWaterBiomes.add(MEBiomeKeys.NEN_HITHOEL);
        anduinWaterBiomes.add(MEBiomeKeys.NEN_HITHOEL_SHORES);
        anduinWaterBiomes.add(MEBiomeKeys.NEN_HITHOEL_FOREST);
        anduinWaterBiomes.add(MEBiomeKeys.EMYN_MUIL_CLIFFS);

        frozenBiomes.add(MEBiomeKeys.NORTHERN_WASTELANDS);
        frozenBiomes.add(MEBiomeKeys.SOUTHERN_FOROCHEL);
        frozenBiomes.add(MEBiomeKeys.FORODWAITH);

        mirkwoodSwampBiomes.add(MEBiomeKeys.DARK_MIRKWOOD);
        mirkwoodSwampBiomes.add(MEBiomeKeys.DARK_MIRKWOOD_EDGE);
        mirkwoodSwampBiomes.add(MEBiomeKeys.DARK_ANDUIN_VALES);
        mirkwoodSwampBiomes.add(MEBiomeKeys.DOL_GULDUR);
        mirkwoodSwampBiomes.add(MEBiomeKeys.MIRKWOOD);
        mirkwoodSwampBiomes.add(MEBiomeKeys.MIRKWOOD_EDGE);

        oasisBiomes.add(MEBiomeKeys.CORSAIR_COASTS);
        oasisBiomes.add(MEBiomeKeys.HARONDOR);
        oasisBiomes.add(MEBiomeKeys.HARAD);
        oasisBiomes.add(MEBiomeKeys.HARAD_DESERT);
        oasisBiomes.add(MEBiomeKeys.UMBAR);

        wastePondBiomes.add(MEBiomeKeys.DAGORLAD);
        wastePondBiomes.add(MEBiomeKeys.GORGOROTH);
        wastePondBiomes.add(MEBiomeKeys.MORDOR);
        wastePondBiomes.add(MEBiomeKeys.MORDOR_MOUNTAINS_FOOTHILLS);
        wastePondBiomes.add(MEBiomeKeys.SOUTHERN_EPHEL_DUATH_BASE);
        wastePondBiomes.add(MEBiomeKeys.MORDOR_WASTES);
        wastePondBiomes.add(MEBiomeKeys.MORGUL_RIVER);
        wastePondBiomes.add(MEBiomeKeys.MORGUL_VALE);
        wastePondBiomes.add(MEBiomeKeys.EASTERN_NURN);
        wastePondBiomes.add(MEBiomeKeys.NURN);
        wastePondBiomes.add(MEBiomeKeys.NURN_EDGE);
        wastePondBiomes.add(MEBiomeKeys.TOROGWAITH);
        wastePondBiomes.add(MEBiomeKeys.UDUN);

        deadMarshesBiomes.add(MEBiomeKeys.DEAD_MARSHES);
    }

    public static MEBiome getBiomeByKey(RegistryEntry<Biome> biome) {
        return biomes.stream().filter(
                b -> b.biome.getValue().toString().equalsIgnoreCase(biome.getKey().get().getValue().toString()))
                .findFirst().orElse(defaultBiome);
    }
}
