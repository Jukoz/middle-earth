package net.sevenstars.middleearth.client;

import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.client.color.world.BiomeColors;
import net.minecraft.world.biome.FoliageColors;
import net.minecraft.world.biome.GrassColors;
import net.sevenstars.middleearth.block.registration.BlockRegistryME;
import net.sevenstars.middleearth.block.registration.NatureBlockRegistryME;
import net.sevenstars.middleearth.block.registration.WoodBlockSetRegistryME;

public class BlockColorsME {

    public static void initializeBlockColors() {
        ColorProviderRegistry.BLOCK.register((state, view, pos, tintIndex) -> 0x677006, WoodBlockSetRegistryME.BEECH_SET.leaves);
        ColorProviderRegistry.BLOCK.register((state, view, pos, tintIndex) -> 0x758f28, WoodBlockSetRegistryME.LARCH_SET.leaves);
        ColorProviderRegistry.BLOCK.register((state, view, pos, tintIndex) -> 0x628842, WoodBlockSetRegistryME.CHESTNUT_SET.leaves);
        ColorProviderRegistry.BLOCK.register((state, view, pos, tintIndex) -> 0x46684a, WoodBlockSetRegistryME.FIR_SET.leaves);
        ColorProviderRegistry.BLOCK.register((state, view, pos, tintIndex) -> 0x3f5f3f, WoodBlockSetRegistryME.HOLLY_SET.leaves);
        ColorProviderRegistry.BLOCK.register((state, view, pos, tintIndex) -> 0xffe45a, NatureBlockRegistryME.FLOWERING_MALLORN_LEAVES);
        ColorProviderRegistry.BLOCK.register((state, view, pos, tintIndex) -> 0xffe45a, WoodBlockSetRegistryME.MALLORN_SET.leaves);
        ColorProviderRegistry.BLOCK.register((state, view, pos, tintIndex) -> 0x9c802a, WoodBlockSetRegistryME.MAPLE_SET.leaves);
        ColorProviderRegistry.BLOCK.register((state, view, pos, tintIndex) -> 0x0e260c, WoodBlockSetRegistryME.MIRKWOOD_SET.leaves);
        ColorProviderRegistry.BLOCK.register((state, view, pos, tintIndex) -> 0x6c8031, WoodBlockSetRegistryME.PALM_SET.leaves);
        ColorProviderRegistry.BLOCK.register((state, view, pos, tintIndex) -> 0x41461c, WoodBlockSetRegistryME.PINE_SET.leaves);
        ColorProviderRegistry.BLOCK.register((state, view, pos, tintIndex) -> 0x324931, WoodBlockSetRegistryME.BLACK_PINE_SET.leaves);
        ColorProviderRegistry.BLOCK.register((state, view, pos, tintIndex) -> 0x415730, WoodBlockSetRegistryME.WILLOW_SET.leaves);

        ColorProviderRegistry.BLOCK.register((state, view, pos, tintIndex) -> 0x876b00, NatureBlockRegistryME.DRY_LARCH_LEAVES);
        ColorProviderRegistry.BLOCK.register((state, view, pos, tintIndex) -> 0x5b4f2c, NatureBlockRegistryME.DRY_PINE_LEAVES);
        ColorProviderRegistry.BLOCK.register((state, view, pos, tintIndex) -> 0x244324, NatureBlockRegistryME.LEBETHRON_LEAVES);
        ColorProviderRegistry.BLOCK.register((state, view, pos, tintIndex) -> 0x925121, NatureBlockRegistryME.ORANGE_MAPLE_LEAVES);
        ColorProviderRegistry.BLOCK.register((state, view, pos, tintIndex) -> 0x913720, NatureBlockRegistryME.RED_MAPLE_LEAVES);
        ColorProviderRegistry.BLOCK.register((state, view, pos, tintIndex) -> 0x926821, NatureBlockRegistryME.YELLOW_MAPLE_LEAVES);

        ColorProviderRegistry.BLOCK.register((state, view, pos, tintIndex) -> {
                    if (view == null || pos == null) {
                        return GrassColors.getDefaultColor();
                    }
                    return BiomeColors.getGrassColor(view, pos);
                }, NatureBlockRegistryME.WILD_GRASS, NatureBlockRegistryME.LARGE_BUSH, NatureBlockRegistryME.GRASS_TUFT, NatureBlockRegistryME.WHEATGRASS,
                NatureBlockRegistryME.BRACKEN, NatureBlockRegistryME.GIANT_BUTTERBUR,
                BlockRegistryME.GRASSY_DIRT, BlockRegistryME.GRASSY_DIRT_SLAB, BlockRegistryME.GRASSY_DIRT_STAIRS,
                BlockRegistryME.CHALKSOIL_GRASS_BLOCK, BlockRegistryME.LOAM_GRASS_BLOCK, BlockRegistryME.PEAT_GRASS_BLOCK, BlockRegistryME.SILT_GRASS_BLOCK,
                BlockRegistryME.GRASSY_CHALKSOIL, BlockRegistryME.GRASSY_CHALKSOIL_SLAB, BlockRegistryME.GRASSY_CHALKSOIL_STAIRS,
                BlockRegistryME.GRASSY_LOAM, BlockRegistryME.GRASSY_LOAM_SLAB, BlockRegistryME.GRASSY_LOAM_STAIRS,
                BlockRegistryME.GRASSY_PEAT, BlockRegistryME.GRASSY_PEAT_SLAB, BlockRegistryME.GRASSY_PEAT_STAIRS,
                BlockRegistryME.GRASSY_SILT, BlockRegistryME.GRASSY_SILT_SLAB, BlockRegistryME.GRASSY_SILT_STAIRS,
                BlockRegistryME.PEBBLED_GRASS, BlockRegistryME.PEBBLED_GRASS_SLAB, BlockRegistryME.PEBBLED_GRASS_STAIRS,
                BlockRegistryME.TURF, BlockRegistryME.TURF_SLAB, BlockRegistryME.TURF_STAIRS, BlockRegistryME.TURF_VERTICAL_SLAB,
                NatureBlockRegistryME.FOREST_MOSS, NatureBlockRegistryME.FOREST_MOSS_BLOCK, NatureBlockRegistryME.FOREST_MOSS_CARPET,
                NatureBlockRegistryME.DUCKWEED, NatureBlockRegistryME.CLOVERS, NatureBlockRegistryME.MEADOWGRASS,
                NatureBlockRegistryME.SPARSE_GRASS, NatureBlockRegistryME.NETTLES, NatureBlockRegistryME.THISTLE,
                NatureBlockRegistryME.SMALL_LILY_PADS, NatureBlockRegistryME.SMALL_FLOWERING_LILY_PADS,
                NatureBlockRegistryME.LILY_PADS, NatureBlockRegistryME.FLOWERING_LILY_PADS,
                NatureBlockRegistryME.LARGE_LILY_PAD, NatureBlockRegistryME.LARGE_FLOWERING_LILY_PAD);

        ColorProviderRegistry.BLOCK.register((state, view, pos, tintIndex) -> {
            if (view == null || pos == null) {
                return FoliageColors.DEFAULT;
            }
            return BiomeColors.getFoliageColor(view, pos);
        }, NatureBlockRegistryME.FALLEN_LEAVES, WoodBlockSetRegistryME.BEECH_SET.leaves);
    }
}
