package net.sevenstars.middleearth.client;

import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.client.color.world.BiomeColors;
import net.minecraft.world.biome.FoliageColors;
import net.minecraft.world.biome.GrassColors;
import net.sevenstars.middleearth.block.registration.ModBlocks;
import net.sevenstars.middleearth.block.registration.ModNatureBlocks;
import net.sevenstars.middleearth.block.registration.WoodBlockSets;

public class BlockColorsME {

    public static void initializeBlockColors() {
        ColorProviderRegistry.BLOCK.register((state, view, pos, tintIndex) -> 0x677006, WoodBlockSets.BEECH_SET.leaves);
        ColorProviderRegistry.BLOCK.register((state, view, pos, tintIndex) -> 0x758f28, WoodBlockSets.LARCH_SET.leaves);
        ColorProviderRegistry.BLOCK.register((state, view, pos, tintIndex) -> 0x628842, WoodBlockSets.CHESTNUT_SET.leaves);
        ColorProviderRegistry.BLOCK.register((state, view, pos, tintIndex) -> 0x46684a, WoodBlockSets.FIR_SET.leaves);
        ColorProviderRegistry.BLOCK.register((state, view, pos, tintIndex) -> 0x3f5f3f, WoodBlockSets.HOLLY_SET.leaves);
        ColorProviderRegistry.BLOCK.register((state, view, pos, tintIndex) -> 0xffe45a, ModNatureBlocks.FLOWERING_MALLORN_LEAVES);
        ColorProviderRegistry.BLOCK.register((state, view, pos, tintIndex) -> 0xffe45a, WoodBlockSets.MALLORN_SET.leaves);
        ColorProviderRegistry.BLOCK.register((state, view, pos, tintIndex) -> 0x9c802a, WoodBlockSets.MAPLE_SET.leaves);
        ColorProviderRegistry.BLOCK.register((state, view, pos, tintIndex) -> 0x0e260c, WoodBlockSets.MIRKWOOD_SET.leaves);
        ColorProviderRegistry.BLOCK.register((state, view, pos, tintIndex) -> 0x6c8031, WoodBlockSets.PALM_SET.leaves);
        ColorProviderRegistry.BLOCK.register((state, view, pos, tintIndex) -> 0x41461c, WoodBlockSets.PINE_SET.leaves);
        ColorProviderRegistry.BLOCK.register((state, view, pos, tintIndex) -> 0x324931, WoodBlockSets.BLACK_PINE_SET.leaves);
        ColorProviderRegistry.BLOCK.register((state, view, pos, tintIndex) -> 0x415730, WoodBlockSets.WILLOW_SET.leaves);

        ColorProviderRegistry.BLOCK.register((state, view, pos, tintIndex) -> 0x876b00, ModNatureBlocks.DRY_LARCH_LEAVES);
        ColorProviderRegistry.BLOCK.register((state, view, pos, tintIndex) -> 0x5b4f2c, ModNatureBlocks.DRY_PINE_LEAVES);
        ColorProviderRegistry.BLOCK.register((state, view, pos, tintIndex) -> 0x244324, ModNatureBlocks.LEBETHRON_LEAVES);
        ColorProviderRegistry.BLOCK.register((state, view, pos, tintIndex) -> 0x925121, ModNatureBlocks.ORANGE_MAPLE_LEAVES);
        ColorProviderRegistry.BLOCK.register((state, view, pos, tintIndex) -> 0x913720, ModNatureBlocks.RED_MAPLE_LEAVES);
        ColorProviderRegistry.BLOCK.register((state, view, pos, tintIndex) -> 0x926821, ModNatureBlocks.YELLOW_MAPLE_LEAVES);

        ColorProviderRegistry.BLOCK.register((state, view, pos, tintIndex) -> {
                    if (view == null || pos == null) {
                        return GrassColors.getDefaultColor();
                    }
                    return BiomeColors.getGrassColor(view, pos);
                }, ModNatureBlocks.WILD_GRASS, ModNatureBlocks.LARGE_BUSH, ModNatureBlocks.GRASS_TUFT, ModNatureBlocks.WHEATGRASS, ModNatureBlocks.BRACKEN,
                ModBlocks.GRASSY_DIRT, ModBlocks.GRASSY_DIRT_SLAB, ModBlocks.GRASSY_DIRT_STAIRS,
                ModBlocks.LOAM_GRASS_BLOCK, ModBlocks.PEAT_GRASS_BLOCK, ModBlocks.SILT_GRASS_BLOCK, ModNatureBlocks.DUCKWEED,
                ModBlocks.GRASSY_LOAM, ModBlocks.GRASSY_LOAM_SLAB, ModBlocks.GRASSY_LOAM_STAIRS,
                ModBlocks.GRASSY_PEAT, ModBlocks.GRASSY_PEAT_SLAB, ModBlocks.GRASSY_PEAT_STAIRS,
                ModBlocks.GRASSY_SILT, ModBlocks.GRASSY_SILT_SLAB, ModBlocks.GRASSY_SILT_STAIRS,
                ModBlocks.PEBBLED_GRASS, ModBlocks.PEBBLED_GRASS_SLAB, ModBlocks.PEBBLED_GRASS_STAIRS,
                ModBlocks.TURF, ModBlocks.TURF_SLAB, ModBlocks.TURF_STAIRS, ModBlocks.TURF_VERTICAL_SLAB,
                ModNatureBlocks.FOREST_MOSS, ModNatureBlocks.FOREST_MOSS_BLOCK, ModNatureBlocks.FOREST_MOSS_CARPET,
                ModNatureBlocks.CLOVERS, ModNatureBlocks.MEADOWGRASS);

        ColorProviderRegistry.BLOCK.register((state, view, pos, tintIndex) -> {
            if (view == null || pos == null) {
                return FoliageColors.DEFAULT;
            }
            return BiomeColors.getFoliageColor(view, pos);
        }, ModNatureBlocks.FALLEN_LEAVES);

        ColorProviderRegistry.BLOCK.register((state, view, pos, tintIndex) ->  view != null && pos != null ? -14647248 : -9321636,
                ModNatureBlocks.SMALL_LILY_PADS, ModNatureBlocks.LILY_PADS);
    }
}
