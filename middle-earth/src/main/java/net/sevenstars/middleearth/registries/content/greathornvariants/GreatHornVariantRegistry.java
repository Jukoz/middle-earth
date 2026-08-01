package net.sevenstars.middleearth.registries.content.greathornvariants;

import net.minecraft.entity.spawn.SpawnConditionSelectors;
import net.minecraft.registry.*;
import net.minecraft.registry.tag.TagKey;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.entity.beasts.great_horn.GreatHornVariant;
import net.sevenstars.middleearth.registries.DynamicRegistriesME;

public class GreatHornVariantRegistry {
    private static final RegistryKey<Registry<GreatHornVariant>> GREAT_HORN_VARIANTS_KEY = DynamicRegistriesME.GREAT_HORN_VARIANTS;

    public static final RegistryKey<GreatHornVariant> BROWN = DynamicRegistriesME.of(GREAT_HORN_VARIANTS_KEY, MiddleEarth.of("brown"));
    public static final RegistryKey<GreatHornVariant> TEMPERATE = DynamicRegistriesME.of(GREAT_HORN_VARIANTS_KEY, MiddleEarth.of("temperate"));
    public static final RegistryKey<GreatHornVariant> WARM = DynamicRegistriesME.of(GREAT_HORN_VARIANTS_KEY, MiddleEarth.of("warm"));
    public static final RegistryKey<GreatHornVariant> COLD = DynamicRegistriesME.of(GREAT_HORN_VARIANTS_KEY, MiddleEarth.of("cold"));
    public static final RegistryKey<GreatHornVariant> DEFAULT = BROWN;

    public static void bootstrap(Registerable<GreatHornVariant> context) {
        RegistryEntryLookup<GreatHornVariant> registryEntryLookup = context.getRegistryLookup(GREAT_HORN_VARIANTS_KEY);

        register(context, registryEntryLookup, BROWN, new GreatHornVariant(
                GreatHornVariantRegistryHelper.createAssetInfos("brown"),
                SpawnConditionSelectors.createFallback(0)));

        register(context, registryEntryLookup, TEMPERATE, new GreatHornVariant(
                GreatHornVariantRegistryHelper.createAssetInfos("temperate"),
                SpawnConditionSelectors.createFallback(0)));

        register(context, registryEntryLookup, WARM, new GreatHornVariant(
                GreatHornVariantRegistryHelper.createAssetInfos("warm"),
                GreatHornVariantRegistryHelper.createSpawnConditions(context, TagKey.of(RegistryKeys.BIOME, MiddleEarth.of("spawns_warm_variant_great_horn")), 0)));

        register(context, registryEntryLookup, COLD, new GreatHornVariant(
                GreatHornVariantRegistryHelper.createAssetInfos("cold"),
                GreatHornVariantRegistryHelper.createSpawnConditions(context, TagKey.of(RegistryKeys.BIOME, MiddleEarth.of("spawns_cold_variant_great_horn")), 1)));
    }

    private static void register(Registerable<GreatHornVariant> context, RegistryEntryLookup<GreatHornVariant> registryEntryLookup, RegistryKey<GreatHornVariant> registryKey, GreatHornVariant element){
        DynamicRegistriesME.register(context, registryEntryLookup, registryKey, element);
        // [LANG datagen]
        // None
    }
}
