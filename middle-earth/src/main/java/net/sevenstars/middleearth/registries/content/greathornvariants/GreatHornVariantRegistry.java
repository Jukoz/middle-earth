package net.sevenstars.middleearth.registries.content.greathornvariants;

import net.minecraft.core.HolderGetter;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.entity.beasts.great_horn.GreatHornVariant;
import net.sevenstars.middleearth.registries.DynamicRegistriesME;

public class GreatHornVariantRegistry {
    private static final ResourceKey<Registry<GreatHornVariant>> GREAT_HORN_VARIANTS_KEY = DynamicRegistriesME.GREAT_HORN_VARIANTS;

    public static final ResourceKey<GreatHornVariant> BROWN = DynamicRegistriesME.of(GREAT_HORN_VARIANTS_KEY, MiddleEarth.of("brown"));
    public static final ResourceKey<GreatHornVariant> TEMPERATE = DynamicRegistriesME.of(GREAT_HORN_VARIANTS_KEY, MiddleEarth.of("temperate"));
    public static final ResourceKey<GreatHornVariant> WARM = DynamicRegistriesME.of(GREAT_HORN_VARIANTS_KEY, MiddleEarth.of("warm"));
    public static final ResourceKey<GreatHornVariant> COLD = DynamicRegistriesME.of(GREAT_HORN_VARIANTS_KEY, MiddleEarth.of("cold"));
    public static final ResourceKey<GreatHornVariant> DEFAULT = BROWN;

    public static void bootstrap(BootstrapContext<GreatHornVariant> context) {
        HolderGetter<GreatHornVariant> registryEntryLookup = context.lookup(GREAT_HORN_VARIANTS_KEY);

        register(context, registryEntryLookup, BROWN, new GreatHornVariant(
                GreatHornVariantRegistryHelper.createAssetInfos("brown")));

        register(context, registryEntryLookup, TEMPERATE, new GreatHornVariant(
                GreatHornVariantRegistryHelper.createAssetInfos("temperate")));

        register(context, registryEntryLookup, WARM, new GreatHornVariant(
                GreatHornVariantRegistryHelper.createAssetInfos("warm")));

        register(context, registryEntryLookup, COLD, new GreatHornVariant(
                GreatHornVariantRegistryHelper.createAssetInfos("cold")));
    }

    private static void register(BootstrapContext<GreatHornVariant> context, HolderGetter<GreatHornVariant> registryEntryLookup, ResourceKey<GreatHornVariant> registryKey, GreatHornVariant element){
        DynamicRegistriesME.register(context, registryEntryLookup, registryKey, element);
        // [LANG datagen]
        // None
    }
}
