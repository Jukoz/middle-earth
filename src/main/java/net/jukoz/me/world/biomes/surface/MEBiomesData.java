package net.jukoz.me.world.biomes.surface;

import net.jukoz.me.block.StoneBlockSets;
import net.jukoz.me.block.ModBlocks;
import net.jukoz.me.world.biomes.MEBiomeKeys;
import net.jukoz.me.world.biomes.caves.CaveType;
import net.minecraft.block.Blocks;
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
    public static List<RegistryKey<Biome>> frozenBiomes = new ArrayList<>();
    public static List<RegistryKey<Biome>> wastePondBiomes = new ArrayList<>();
    public static List<RegistryKey<Biome>> mirkwoodSwampBiomes = new ArrayList<>();
    public static List<RegistryKey<Biome>> oasisBiomes = new ArrayList<>();

    public static MEBiome defaultBiome;
    public static MEBiome frozenPond;
    public static MEBiome oasis;
    public static MEBiome pond;
    public static MEBiome wastePond;
    public static MEBiome mirkwoodSwamp;


    public static void addBiome(Color color, MEBiome biome) {
        biome.color = color;
        biomes.add(biome);
    }

    public static MEBiome getBiomeByColor(Integer rgb){
        try{
            return biomes.stream().filter(x -> x.color.getRGB() == rgb).findFirst().get();
        } catch (Exception e){
            System.out.println("MeBiomes::No registered biome has %s for color".formatted(rgb));
        }
        return null;
    }

    public static MEBiome getBiomeById(Short id){
        try{
            return biomes.get(id);
        } catch (Exception e){
            System.out.println("MeBiomes::No registered biome has %s for id".formatted(id));
        }
        return null;
    }

    public static Integer getColorByBiomeId(Short id){
        try{
            return biomes.get(id).color.getRGB();
        } catch (Exception e){
            System.out.println("MeBiomes::No registered biome has %s for id".formatted(id));
        }
        return null;
    }

    public static void loadBiomes() {
        defaultBiome = new MEBiome(-13, MEBiomeKeys.OCEAN, Blocks.SAND, Blocks.STONE, Blocks.STONE);
        frozenPond = new MEBiome(-10, MEBiomeKeys.FROZEN_POND,  Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.STONE, CaveType.FOROD);
        oasis = new MEBiome(-10, MEBiomeKeys.OASIS,  Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.STONE, CaveType.HARAD);
        pond = new MEBiome(-10, MEBiomeKeys.POND,  Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.STONE);
        mirkwoodSwamp = new MEBiome(-10, MEBiomeKeys.MIRKWOOD_SWAMP, Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.STONE);
        wastePond = new MEBiome(-10, MEBiomeKeys.WASTE_POND, ModBlocks.ASHEN_DIRT, ModBlocks.ASHEN_DIRT, Blocks.STONE);

        // Water Biomes :
        addBiome(new Color(55, 90, 195), defaultBiome);
        addBiome(new Color(104, 168, 222), oasis);
        addBiome(new Color(104, 168, 222), frozenPond);
        addBiome(new Color(110, 154, 218), pond);
        addBiome(new Color(89, 136, 129), mirkwoodSwamp);
        addBiome(new Color(75, 108, 143), wastePond);

        addBiome(new Color(101, 123, 243), new MEBiome(-11, MEBiomeKeys.FROZEN_OCEAN, Blocks.GRAVEL, StoneBlockSets.FROZEN_STONE.base(), Blocks.STONE, CaveType.FOROD));
        addBiome(new Color(69, 92, 228), new MEBiome(-10, MEBiomeKeys.LONG_LAKE, Blocks.SAND, Blocks.STONE, Blocks.STONE));
        addBiome(new Color(79, 91, 161), new MEBiome(-8, MEBiomeKeys.NURN_RIVER, ModBlocks.ASHEN_DIRT, ModBlocks.ASHEN_DIRT, Blocks.STONE));
        addBiome(new Color(88, 94, 130), new MEBiome(-11, MEBiomeKeys.NURN_SEA, ModBlocks.ASHEN_DIRT, ModBlocks.ASHEN_DIRT, Blocks.STONE));
        addBiome(new Color(75, 106, 199), new MEBiome(-9, MEBiomeKeys.OCEAN_COAST, Blocks.SAND, Blocks.STONE, Blocks.STONE));
        addBiome(new Color(83, 129, 186), new MEBiome(-8, MEBiomeKeys.RIVER, ModBlocks.RIVER_SAND, Blocks.STONE, Blocks.STONE));
        addBiome(new Color(66, 97, 157), new MEBiome(-11, MEBiomeKeys.SEA_OF_RHUN, Blocks.SAND, Blocks.STONE, Blocks.STONE));



        addBiome(new Color(156, 207, 113), new MEBiome(4, MEBiomeKeys.ANDUIN_VALES, Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.STONE));
        addBiome(new Color(79, 187, 73), new MEBiome(4, MEBiomeKeys.ANORIEN, Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.CALCITE, Blocks.STONE));
        addBiome(new Color(96, 171, 89), new MEBiome(6, MEBiomeKeys.BARROW_DOWNS, Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.STONE));
        addBiome(new Color(38, 207, 94), new MEBiome(4, MEBiomeKeys.BELFALAS, Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.CALCITE, Blocks.STONE));
        addBiome(new Color(35, 173, 81), new MEBiome(37, MEBiomeKeys.BELFALAS_HILLS, Blocks.GRASS_BLOCK, Blocks.CALCITE, Blocks.CALCITE, Blocks.STONE));
        addBiome(new Color(200, 209, 255), new MEBiome(51, MEBiomeKeys.BLUE_MOUNTAINS, StoneBlockSets.GONLUIN.base(), StoneBlockSets.GONLUIN.base(), StoneBlockSets.GONLUIN.base(), Blocks.STONE));
        addBiome(new Color(178, 183, 210), new MEBiome(33, MEBiomeKeys.BLUE_MOUNTAINS_FOOTHILLS, StoneBlockSets.GONLUIN.base(), StoneBlockSets.GONLUIN.base(), StoneBlockSets.GONLUIN.base(), Blocks.STONE));
        addBiome(new Color(193, 188, 131), new MEBiome(6, MEBiomeKeys.CORSAIR_COASTS, Blocks.GRASS_BLOCK, Blocks.SANDSTONE, Blocks.STONE, CaveType.HARAD));
        addBiome(new Color(132, 164, 78), new MEBiome(4, MEBiomeKeys.DALE, Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.STONE));
        addBiome(new Color(18, 26, 19), new MEBiome(6, MEBiomeKeys.DARK_MIRKWOOD, Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.STONE));
        addBiome(new Color(26, 45, 28), new MEBiome(5, MEBiomeKeys.DARK_MIRKWOOD_EDGE, Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.STONE));
        addBiome(new Color(166, 191, 114), new MEBiome(4, MEBiomeKeys.DARK_ANDUIN_VALES, Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.STONE));
        addBiome(new Color(44, 39, 51), new MEBiome(4, MEBiomeKeys.DOL_GULDUR, Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.STONE));
        addBiome(new Color(82, 146, 80), new MEBiome(4, MEBiomeKeys.DORWINION, Blocks.GRASS_BLOCK, Blocks.DIRT, StoneBlockSets.LIMESTONE.base(), Blocks.STONE));
        addBiome(new Color(93, 113, 92), new MEBiome(34, MEBiomeKeys.DORWINION_HILLS, Blocks.STONE, Blocks.STONE, StoneBlockSets.LIMESTONE.base()));
        addBiome(new Color(132, 137, 124), new MEBiome(35, MEBiomeKeys.DUNLAND_FOOTHILLS, Blocks.STONE, Blocks.STONE, Blocks.STONE));
        addBiome(new Color(140, 150, 84), new MEBiome(4, MEBiomeKeys.EASTERN_RHOVANION, Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.STONE));
        addBiome(new Color(120, 107, 84), new MEBiome(37, MEBiomeKeys.EMYN_MUIL, Blocks.STONE, Blocks.STONE, Blocks.STONE));
        addBiome(new Color(117, 164, 109), new MEBiome(6, MEBiomeKeys.ENEDWAITH, Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.STONE));
        addBiome(new Color(168, 168, 170), new MEBiome(38, MEBiomeKeys.LONELY_MOUNTAIN, Blocks.STONE, Blocks.STONE, Blocks.STONE));
        addBiome(new Color(185, 185, 187), new MEBiome(29, MEBiomeKeys.LONELY_MOUNTAIN_FOOTHILLS, Blocks.STONE, Blocks.STONE, Blocks.STONE));
        addBiome(new Color(151, 151, 153), new MEBiome(52, MEBiomeKeys.LONELY_MOUNTAIN_PEAKS, Blocks.STONE, Blocks.STONE, Blocks.STONE));
        addBiome(new Color(106, 155, 104), new MEBiome(4, MEBiomeKeys.EREGION, Blocks.GRASS_BLOCK, Blocks.DIRT, StoneBlockSets.LIMESTONE.base(), Blocks.STONE));
        addBiome(new Color(171, 193, 128), new MEBiome(4, MEBiomeKeys.ERIADOR, Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.STONE));
        addBiome(new Color(16, 156, 109), new MEBiome(4, MEBiomeKeys.ETHIR_ANDUIN, Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.STONE));
        addBiome(new Color(54, 75, 12), new MEBiome(6, MEBiomeKeys.FANGORN, Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.STONE));
        addBiome(new Color(236, 236, 236), new MEBiome(14, MEBiomeKeys.FORODWAITH, Blocks.SNOW_BLOCK, Blocks.SNOW_BLOCK, StoneBlockSets.FROZEN_STONE.base(), CaveType.FOROD));
        addBiome(new Color(91, 189, 85), new MEBiome(4, MEBiomeKeys.GONDOR, Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.CALCITE, Blocks.STONE));
        addBiome(new Color(92, 147, 92), new MEBiome(6, MEBiomeKeys.GREY_PLAINS, Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.STONE));
        addBiome(new Color(92, 78, 78), new MEBiome(42, MEBiomeKeys.GREY_MOUNTAINS, Blocks.STONE, Blocks.STONE, Blocks.STONE));
        addBiome(new Color(204, 196, 113), new MEBiome(7, MEBiomeKeys.HARAD, Blocks.GRASS_BLOCK, Blocks.SANDSTONE, Blocks.STONE, CaveType.HARAD));
        addBiome(new Color(237, 229, 145), new MEBiome(9, MEBiomeKeys.HARAD_DESERT, Blocks.SAND, Blocks.SAND, Blocks.SANDSTONE, CaveType.HARAD));
        addBiome(new Color(180, 214, 121), new MEBiome(6, MEBiomeKeys.HARONDOR, Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.STONE));
        addBiome(new Color(156, 187, 94), new MEBiome(35, MEBiomeKeys.HILLS_OF_ELVENDIM, Blocks.GRASS_BLOCK, Blocks.STONE, Blocks.STONE));
        addBiome(new Color(174, 144, 141), new MEBiome(41, MEBiomeKeys.IRON_HILLS, Blocks.STONE, Blocks.STONE, Blocks.STONE));
        addBiome(new Color(189, 170, 168), new MEBiome(21, MEBiomeKeys.IRON_HILLS_FOOTHILLS, Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.STONE));
        addBiome(new Color(90, 159, 90), new MEBiome(9, MEBiomeKeys.IRON_HILLS_FRONTIER, Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.STONE));
        addBiome(new Color(4, 117, 42), new MEBiome(5, MEBiomeKeys.ITHILIEN, Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.CALCITE, Blocks.STONE));
        addBiome(new Color(51, 100, 67), new MEBiome(5, MEBiomeKeys.ITHILIEN_WASTES, Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.CALCITE, Blocks.STONE));
        addBiome(new Color(164, 255, 164), new MEBiome(7, MEBiomeKeys.LAMEDON, Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.CALCITE, Blocks.STONE));
        addBiome(new Color(66, 220, 56), new MEBiome(4, MEBiomeKeys.LEBENNIN, Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.CALCITE, Blocks.STONE));
        addBiome(new Color(67, 193, 125), new MEBiome(4, MEBiomeKeys.LINDON, Blocks.GRASS_BLOCK, Blocks.DIRT, StoneBlockSets.LIMESTONE.base(), Blocks.STONE));
        addBiome(new Color(205, 206, 96), new MEBiome(4, MEBiomeKeys.LORIEN_EDGE, Blocks.GRASS_BLOCK, Blocks.DIRT, StoneBlockSets.LIMESTONE.base(), Blocks.STONE));
        addBiome(new Color(221, 216, 28), new MEBiome(5, MEBiomeKeys.LOTHLORIEN, Blocks.GRASS_BLOCK, Blocks.DIRT, StoneBlockSets.LIMESTONE.base(), Blocks.STONE));
        addBiome(new Color(145, 164, 109), new MEBiome(4, MEBiomeKeys.MINHIRIATH, Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.STONE));
        addBiome(new Color(10, 54, 15), new MEBiome(6, MEBiomeKeys.MIRKWOOD, Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.STONE));
        addBiome(new Color(18, 73, 24), new MEBiome(5, MEBiomeKeys.MIRKWOOD_EDGE, Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.STONE));
        addBiome(new Color(74, 107, 78), new MEBiome(24, MEBiomeKeys.MIRKWOOD_FOOTHILLS, Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.STONE));
        addBiome(new Color(76, 85, 77), new MEBiome(42, MEBiomeKeys.MIRKWOOD_MOUNTAINS, Blocks.STONE, Blocks.STONE, Blocks.STONE));
        addBiome(new Color(129, 131, 125), new MEBiome(35, MEBiomeKeys.MISTY_FOOTHILLS, Blocks.STONE, Blocks.STONE, Blocks.STONE, CaveType.MISTIES));
        addBiome(new Color(121, 121, 121), new MEBiome(53, MEBiomeKeys.MISTY_MOUNTAINS, Blocks.SNOW_BLOCK, Blocks.STONE, Blocks.STONE, CaveType.MISTIES));
        addBiome(new Color(60, 42, 42), new MEBiome(6, MEBiomeKeys.MORDOR, StoneBlockSets.ASHEN_STONE.base(), StoneBlockSets.ASHEN_STONE.base(), StoneBlockSets.ASHEN_STONE.base(), CaveType.ASHEN));
        addBiome(new Color(36, 31, 31), new MEBiome(47, MEBiomeKeys.MORDOR_MOUNTAINS, StoneBlockSets.ASHEN_STONE.base(), StoneBlockSets.ASHEN_STONE.base(), StoneBlockSets.ASHEN_STONE.base(), CaveType.ASHEN));
        addBiome(new Color(47, 42, 42), new MEBiome(30, MEBiomeKeys.MORDOR_MOUNTAINS_FOOTHILLS, StoneBlockSets.ASHEN_STONE.base(), StoneBlockSets.ASHEN_STONE.base(), StoneBlockSets.ASHEN_STONE.base(), CaveType.ASHEN));
        addBiome(new Color(88, 82, 71), new MEBiome(3, MEBiomeKeys.MORDOR_WASTES, Blocks.GRASS_BLOCK, ModBlocks.ASHEN_DIRT, Blocks.STONE, CaveType.ASHEN));
        addBiome(new Color(43, 158, 120), new MEBiome(4, MEBiomeKeys.NINDALF, Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.STONE));
        addBiome(new Color(177, 188, 154), new MEBiome(34, MEBiomeKeys.NORTH_DOWNS, Blocks.GRASS_BLOCK, Blocks.STONE, Blocks.STONE, CaveType.MISTIES));
        addBiome(new Color(106, 127, 62), new MEBiome(5, MEBiomeKeys.NORTHERN_DUNLAND, Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.STONE));
        addBiome(new Color(160, 165, 149), new MEBiome(6, MEBiomeKeys.NORTHERN_WASTELANDS, Blocks.GRASS_BLOCK, Blocks.DIRT, StoneBlockSets.FROZEN_STONE.base(), Blocks.STONE, CaveType.FOROD));
        addBiome(new Color(74, 77, 51), new MEBiome(5, MEBiomeKeys.NURN, Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.STONE));
        addBiome(new Color(79, 91, 161), new MEBiome(-21, MEBiomeKeys.NURN_RIVER, ModBlocks.ASHEN_DIRT, ModBlocks.ASHEN_DIRT, Blocks.STONE));
        addBiome(new Color(88, 94, 130), new MEBiome(-17, MEBiomeKeys.NURN_SEA, ModBlocks.ASHEN_DIRT, ModBlocks.ASHEN_DIRT, Blocks.STONE));
        addBiome(new Color(75, 106, 199), new MEBiome(-12, MEBiomeKeys.OCEAN_COAST, Blocks.SAND, Blocks.STONE, Blocks.STONE));
        addBiome(new Color(73, 82, 53), new MEBiome(6, MEBiomeKeys.OLD_ANGMAR, Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.STONE));
        addBiome(new Color(200, 238, 128), new MEBiome(4, MEBiomeKeys.OLD_ARTHEDAIN, Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.STONE));
        addBiome(new Color(156, 185, 97), new MEBiome(4, MEBiomeKeys.OLD_CARDOLAN, Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.STONE));
        addBiome(new Color(115, 135, 74), new MEBiome(5, MEBiomeKeys.OLD_RHUDAUR, Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.STONE));
        addBiome(new Color(172, 176, 113), new MEBiome(4, MEBiomeKeys.RHUN, Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.STONE));
        addBiome(new Color(76, 202, 95), new MEBiome(5, MEBiomeKeys.RIVENDELL, Blocks.GRASS_BLOCK, Blocks.DIRT, StoneBlockSets.LIMESTONE.base(), Blocks.STONE));
        addBiome(new Color(137, 171, 142), new MEBiome(31, MEBiomeKeys.RIVENDELL_FOOTHILLS, Blocks.STONE, Blocks.STONE, Blocks.STONE));
        addBiome(new Color(183, 229, 102), new MEBiome(4, MEBiomeKeys.ROHAN, Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.STONE));
        addBiome(new Color(84, 217, 70), new MEBiome(4, MEBiomeKeys.SHIRE, Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.STONE));
        addBiome(new Color(75, 184, 64), new MEBiome(4, MEBiomeKeys.SHIRE_EDGE, Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.STONE));
        addBiome(new Color(61, 152, 52), new MEBiome(5, MEBiomeKeys.SHIRE_WOODS, Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.STONE));
        addBiome(new Color(154, 147, 57), new MEBiome(4, MEBiomeKeys.SOUTHEAST_RHOVANION, Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.STONE));
        addBiome(new Color(121, 186, 111), new MEBiome(4, MEBiomeKeys.SOUTHERN_DUNLAND, Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.STONE));
        addBiome(new Color(206, 179, 156), new MEBiome(4, MEBiomeKeys.SOUTHERN_FOROCHEL, Blocks.GRASS_BLOCK, Blocks.DIRT, StoneBlockSets.FROZEN_STONE.base(), Blocks.STONE, CaveType.FOROD));
        addBiome(new Color(126, 149, 79), new MEBiome(4, MEBiomeKeys.THE_ANGLE, Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.STONE));
        addBiome(new Color(48, 109, 42), new MEBiome(7, MEBiomeKeys.THE_OLD_FOREST, Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.STONE));
        addBiome(new Color(152, 174, 113), new MEBiome(4, MEBiomeKeys.THE_WOLD, Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.STONE));
        addBiome(new Color(156, 150, 150), new MEBiome(13, MEBiomeKeys.TOLFALAS, Blocks.GRASS_BLOCK, Blocks.STONE, Blocks.STONE));
        addBiome(new Color(40, 66, 42), new MEBiome(8, MEBiomeKeys.TROLLSHAWS, Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.STONE));
        addBiome(new Color(225, 192, 133), new MEBiome(7, MEBiomeKeys.UMBAR, Blocks.GRASS_BLOCK, Blocks.SANDSTONE, Blocks.STONE, CaveType.HARAD));
        addBiome(new Color(242, 255, 240), new MEBiome(51, MEBiomeKeys.WHITE_MOUNTAINS, Blocks.CALCITE, Blocks.CALCITE, Blocks.CALCITE, Blocks.STONE));
        addBiome(new Color(219, 245, 215), new MEBiome(31, MEBiomeKeys.WHITE_MOUNTAINS_FOOTHILLS, Blocks.GRASS_BLOCK, Blocks.CALCITE, Blocks.CALCITE, Blocks.STONE));
        addBiome(new Color(22, 102, 31), new MEBiome(5, MEBiomeKeys.WOODLAND_REALM, Blocks.GRASS_BLOCK, Blocks.DIRT, StoneBlockSets.LIMESTONE.base(), Blocks.STONE));

        waterBiomes.add(MEBiomeKeys.FROZEN_POND);
        waterBiomes.add(MEBiomeKeys.FROZEN_OCEAN);
        waterBiomes.add(MEBiomeKeys.LONG_LAKE);
        waterBiomes.add(MEBiomeKeys.NURN_RIVER);
        waterBiomes.add(MEBiomeKeys.NURN_SEA);
        waterBiomes.add(MEBiomeKeys.OCEAN);
        waterBiomes.add(MEBiomeKeys.OCEAN_COAST);
        waterBiomes.add(MEBiomeKeys.RIVER);
        waterBiomes.add(MEBiomeKeys.SEA_OF_RHUN);

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

        wastePondBiomes.add(MEBiomeKeys.MORDOR);
        wastePondBiomes.add(MEBiomeKeys.MORDOR_MOUNTAINS);
        wastePondBiomes.add(MEBiomeKeys.MORDOR_WASTES);
        wastePondBiomes.add(MEBiomeKeys.NURN);
    }

    public static MEBiome getBiomeByKey(RegistryEntry<Biome> biome) {
        return biomes.stream().filter(
                b -> b.biome.getValue().toString().equalsIgnoreCase(biome.getKey().get().getValue().toString()))
                .findFirst().get();
    }
}
