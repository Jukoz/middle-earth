package net.jukoz.me.world.features.vegetation;

import net.jukoz.me.MiddleEarth;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.minecraft.world.gen.feature.PlacedFeatures;
import net.minecraft.world.gen.placementmodifier.BiomePlacementModifier;
import net.minecraft.world.gen.placementmodifier.PlacementModifier;
import net.minecraft.world.gen.placementmodifier.RarityFilterPlacementModifier;
import net.minecraft.world.gen.placementmodifier.SquarePlacementModifier;

import java.util.List;

public class ModVegetationPlacedFeatures {
    // region FOLIAGE
    public static final RegistryKey<PlacedFeature> PATCH_MALLOS = registerKey("patch_mallos");
    public static final RegistryKey<PlacedFeature> PATCH_STRAWBERRY_BUSH = registerKey("patch_strawberry_bush");
    public static final RegistryKey<PlacedFeature> PATCH_TOUGH_BERRY_BUSH = registerKey("patch_tough_berry_bush");
    public static final RegistryKey<PlacedFeature> PATCH_TOUGH_BERRY_BUSH_RARE = registerKey("patch_tough_berry_bush_rare");
    public static final RegistryKey<PlacedFeature> PATCH_MIRKWOOD = registerKey("patch_mirkwood");
    public static final RegistryKey<PlacedFeature> PATCH_MORDOR_LICHEN = registerKey("patch_mordor_lichen");
    public static final RegistryKey<PlacedFeature> PATCH_REEDS = registerKey("patch_reeds");
    // endregion

    // region WILD CROPS
    public static final RegistryKey<PlacedFeature> PATCH_WILD_BEETROOT = registerKey("patch_wild_beetroot");
    public static final RegistryKey<PlacedFeature> PATCH_WILD_BELL_PEPPER = registerKey("patch_wild_bell_pepper");
    public static final RegistryKey<PlacedFeature> PATCH_WILD_CARROT = registerKey("patch_wild_carrot");
    public static final RegistryKey<PlacedFeature> PATCH_WILD_CUCUMBER = registerKey("patch_wild_cucumber");
    public static final RegistryKey<PlacedFeature> PATCH_WILD_FLAX = registerKey("patch_wild_flax");
    public static final RegistryKey<PlacedFeature> PATCH_WILD_GARLIC = registerKey("patch_wild_garlic");
    public static final RegistryKey<PlacedFeature> PATCH_WILD_LEEK = registerKey("patch_wild_leek");
    public static final RegistryKey<PlacedFeature> PATCH_WILD_LETTUCE = registerKey("patch_wild_lettuce");
    public static final RegistryKey<PlacedFeature> PATCH_WILD_ONION = registerKey("patch_wild_onion");
    public static final RegistryKey<PlacedFeature> PATCH_WILD_PIPEWEED = registerKey("patch_wild_pipeweed");
    public static final RegistryKey<PlacedFeature> PATCH_WILD_POTATO = registerKey("patch_wild_potato");
    public static final RegistryKey<PlacedFeature> PATCH_WILD_TOMATO = registerKey("patch_wild_tomato");
    // endregion

    static PlacementModifier rare = PlacedFeatures.createCountExtraModifier(0, 0.1f, 1);
    static PlacementModifier veryRare = PlacedFeatures.createCountExtraModifier(0, 0.05f, 1);
    static PlacementModifier wildBushRarity = PlacedFeatures.createCountExtraModifier(0, 0.01f, 1);

    public static void bootstrap(Registerable<PlacedFeature> featureRegisterable) {
        RegistryEntryLookup<ConfiguredFeature<?, ?>> registryEntryLookup = featureRegisterable.getRegistryLookup(RegistryKeys.CONFIGURED_FEATURE);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> mock = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.MOCK);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> mallos = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_MALLOS);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> strawBerries = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_STRAWBERRY_BUSH);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> toughBerries = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_TOUGH_BERRY_BUSH);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> mirkwood = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_MIRKWOOD);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> mordorLichen = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_MORDOR_LICHEN);

        RegistryEntry.Reference<ConfiguredFeature<?, ?>> wildBeetroot = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_WILD_BEETROOT);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> wildBellPepper = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_WILD_BELL_PEPPER);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> wildCarrot = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_WILD_CARROT);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> wildCucumber = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_WILD_CUCUMBER);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> wildFlax = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_WILD_FLAX);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> wildGarlic = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_WILD_GARLIC);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> wildLeek = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_WILD_LEEK);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> wildLettuce = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_WILD_LETTUCE);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> wildOnion = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_WILD_ONION);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> wildPipeweed = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_WILD_PIPEWEED);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> wildPotato = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_WILD_POTATO);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> wildTomato = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_WILD_TOMATO);

        PlacedFeatures.register(featureRegisterable, PATCH_MALLOS, mallos, RarityFilterPlacementModifier.of(48), SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, PATCH_STRAWBERRY_BUSH, strawBerries, veryRare, SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, PATCH_TOUGH_BERRY_BUSH, toughBerries, RarityFilterPlacementModifier.of(48), SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, PATCH_TOUGH_BERRY_BUSH_RARE, toughBerries, RarityFilterPlacementModifier.of(320), SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, PATCH_MIRKWOOD, mirkwood, RarityFilterPlacementModifier.of(25), SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, PATCH_MORDOR_LICHEN, mordorLichen, RarityFilterPlacementModifier.of(25), SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, PATCH_REEDS, mock, RarityFilterPlacementModifier.of(25), SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());

        PlacedFeatures.register(featureRegisterable, PATCH_WILD_BEETROOT, wildBeetroot, wildBushRarity, SquarePlacementModifier.of(), PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, PATCH_WILD_BELL_PEPPER, wildBellPepper, wildBushRarity, SquarePlacementModifier.of(), PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, PATCH_WILD_CARROT, wildCarrot, wildBushRarity, SquarePlacementModifier.of(), PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, PATCH_WILD_CUCUMBER, wildCucumber, wildBushRarity, SquarePlacementModifier.of(), PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, PATCH_WILD_FLAX, wildFlax, wildBushRarity, SquarePlacementModifier.of(), PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, PATCH_WILD_GARLIC, wildGarlic, wildBushRarity, SquarePlacementModifier.of(), PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, PATCH_WILD_LEEK, wildLeek, wildBushRarity, SquarePlacementModifier.of(), PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, PATCH_WILD_LETTUCE, wildLettuce, wildBushRarity, SquarePlacementModifier.of(), PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, PATCH_WILD_ONION, wildOnion, wildBushRarity, SquarePlacementModifier.of(), PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, PATCH_WILD_PIPEWEED, wildPipeweed, wildBushRarity, SquarePlacementModifier.of(), PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, PATCH_WILD_POTATO, wildPotato, wildBushRarity, SquarePlacementModifier.of(), PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, PATCH_WILD_TOMATO, wildTomato, wildBushRarity, SquarePlacementModifier.of(), PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, BiomePlacementModifier.of());
    }

    public static RegistryKey<PlacedFeature> registerKey(String name) {
        return RegistryKey.of(RegistryKeys.PLACED_FEATURE, new Identifier(MiddleEarth.MOD_ID, name));
    }

    private static void register(Registerable<PlacedFeature> context, RegistryKey<PlacedFeature> key, RegistryEntry<ConfiguredFeature<?, ?>> configuration,
                                 List<PlacementModifier> modifiers) {
        context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
    }
}
