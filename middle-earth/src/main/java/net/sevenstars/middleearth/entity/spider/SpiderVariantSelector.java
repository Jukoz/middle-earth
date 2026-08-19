package net.sevenstars.middleearth.entity.spider;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.registries.DynamicRegistriesME;
import net.sevenstars.middleearth.registries.content.spidervariants.SpiderVariantRegistry;

public final class SpiderVariantSelector {
    private static final TagKey<Biome> CAVE_BIOMES =
            TagKey.create(Registries.BIOME, MiddleEarth.of("is_cave"));
    private static final TagKey<Biome> MORDOR_BIOMES =
            TagKey.create(Registries.BIOME, MiddleEarth.of("is_mordor"));

    private SpiderVariantSelector() {}

    public static Holder<SpiderVariant> select(ServerLevelAccessor world, BlockPos pos) {
        var biome = world.getBiome(pos);
        var key = biome.is(MORDOR_BIOMES)
                ? SpiderVariantRegistry.MORDOR
                : biome.is(CAVE_BIOMES)
                ? SpiderVariantRegistry.CAVE
                : SpiderVariantRegistry.MIRKWOOD;
        return world.registryAccess()
                .registryOrThrow(DynamicRegistriesME.SPIDER_VARIANTS)
                .getHolderOrThrow(key);
    }
}
