package net.sevenstars.middleearth.registries.content.spidervariants;

import net.minecraft.entity.spawn.SpawnConditionSelectors;
import net.minecraft.registry.*;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.entity.spider.SpiderVariant;
import net.sevenstars.middleearth.registries.DynamicRegistriesME;
import net.sevenstars.middleearth.utils.IdentifierUtil;

public class SpiderVariantsRegistry {
    private static final RegistryKey<Registry<SpiderVariant>> SPIDER_VARIANTS_KEY = DynamicRegistriesME.SPIDER_VARIANTS;

    public static final RegistryKey<SpiderVariant> MIRKWOOD = DynamicRegistriesME.of(SPIDER_VARIANTS_KEY, IdentifierUtil.build("mirkwood"));
    public static final RegistryKey<SpiderVariant> CAVE = DynamicRegistriesME.of(SPIDER_VARIANTS_KEY, IdentifierUtil.build("cave"));
    public static final RegistryKey<SpiderVariant> MORDOR = DynamicRegistriesME.of(SPIDER_VARIANTS_KEY, IdentifierUtil.build("mordor"));
    public static final RegistryKey<SpiderVariant> DEFAULT = MIRKWOOD;


    public static void bootstrap(Registerable<SpiderVariant> context) {
        RegistryEntryLookup<SpiderVariant> registryEntryLookup = context.getRegistryLookup(SPIDER_VARIANTS_KEY);

        register(context, registryEntryLookup, MIRKWOOD, new SpiderVariant(
            SpiderVariantRegistryHelper.createAssetInfos("mirkwood"),
            SpawnConditionSelectors.createFallback(0)));
        register(context, registryEntryLookup, CAVE, new SpiderVariant(
            SpiderVariantRegistryHelper.createAssetInfos("blind"),
            SpiderVariantRegistryHelper.createSpawnConditions(context, TagKey.of(RegistryKeys.BIOME, Identifier.of(MiddleEarth.MOD_ID, "is_cave")), 1)));
        register(context, registryEntryLookup, MORDOR, new SpiderVariant(
            SpiderVariantRegistryHelper.createAssetInfos("mordor"),
            SpiderVariantRegistryHelper.createSpawnConditions(context, TagKey.of(RegistryKeys.BIOME, Identifier.of(MiddleEarth.MOD_ID, "is_mordor")), 2)));
    }

    private static void register(Registerable<SpiderVariant> context, RegistryEntryLookup<SpiderVariant> registryEntryLookup, RegistryKey<SpiderVariant> registryKey, SpiderVariant element){
        DynamicRegistriesME.register(context, registryEntryLookup, registryKey, element);
        // [LANG datagen]
        // None
    }

}
