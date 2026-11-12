package net.sevenstars.middleearth.world.features.underground.gems;

import net.minecraft.block.Block;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.GenerationSettings;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.ConfiguredFeatures;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.minecraft.world.gen.feature.PlacedFeatures;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.block.special.gems.GemKind;
import net.sevenstars.middleearth.world.gen.FeatureRegistryME;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Where gems hide: each gem lives in the rock beside the clusters of one kind of host ore, in a depth band, and the
 * racial gems only under their people's lands (surface biome tags data/middle-earth/tags/worldgen/biome/gem_region).
 * Gems per chunk (when the chunk has the host ore): sapphire, ruby and beryl 2-3; the regional gems 1-2; adamant
 * 0-1. The features run in the
 * underground decoration step of every cave biome, after the ores they look for.
 */
public class GemOreFeatureRegistryME {
    private record Placement(GemKind gem, int minY, int maxY, int minPerChunk, int maxPerChunk, String region, List<String> hosts) {
    }

    private static final List<String> COAL_HOSTS = List.of("middle-earth:ashen_coal_ore", "minecraft:coal_ore",
            "middle-earth:khagalaban_coal_ore", "middle-earth:ironstone_coal_ore", "middle-earth:limestone_coal_ore",
            "middle-earth:calcite_coal_ore", "middle-earth:slate_coal_ore", "middle-earth:chalk_coal_ore",
            "middle-earth:gabbro_coal_ore", "middle-earth:travertine_coal_ore");

    private static final Map<String, Placement> PLACEMENTS = new LinkedHashMap<>();

    static {
        add("adamant", new Placement(GemKind.ADAMANT, -64, -48, 0, 1, null, List.of("minecraft:magma_block")));
        add("ruby", new Placement(GemKind.RUBY, -50, -25, 2, 3, null,
                List.of("middle-earth:nurgon_silver_ore", "middle-earth:medgon_silver_ore")));
        add("sapphire", new Placement(GemKind.SAPPHIRE, -32, -1, 2, 3, null, List.of("middle-earth:nurgon_iron_ore")));
        add("beryl", new Placement(GemKind.BERYL, -48, -16, 2, 3, null,
                List.of("middle-earth:nurgon_gold_ore", "middle-earth:medgon_gold_ore")));
        add("hadorite", new Placement(GemKind.HADORITE, -24, -1, 1, 2, "men", List.of("middle-earth:nurgon_lead_ore")));
        add("opal", new Placement(GemKind.OPAL, -64, -32, 1, 2, "dwarves", List.of("middle-earth:medgon_iron_ore")));
        add("onyx", new Placement(GemKind.ONYX, 42, 59, 1, 2, "morgul", COAL_HOSTS));
        // The northern strongholds' plain coal is denser than Mordor's ashen coal, so they get a narrower band.
        add("onyx_north", new Placement(GemKind.ONYX, 44, 57, 1, 2, "morgul_north", COAL_HOSTS));
        add("moonstone", new Placement(GemKind.MOONSTONE, 40, 64, 1, 2, "elves", List.of("middle-earth:tin_ore",
                "middle-earth:limestone_tin_ore", "middle-earth:calcite_tin_ore", "middle-earth:chalk_tin_ore",
                "middle-earth:travertine_tin_ore", "middle-earth:slate_tin_ore", "middle-earth:gabbro_tin_ore",
                "middle-earth:khagalaban_tin_ore", "middle-earth:ironstone_tin_ore")));
    }

    public static final Map<String, RegistryKey<ConfiguredFeature<?, ?>>> CONFIGURED = new LinkedHashMap<>();
    public static final Map<String, RegistryKey<PlacedFeature>> PLACED = new LinkedHashMap<>();

    static {
        for (String name : PLACEMENTS.keySet()) {
            CONFIGURED.put(name, RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, MiddleEarth.id("gem_ore_" + name)));
            PLACED.put(name, RegistryKey.of(RegistryKeys.PLACED_FEATURE, MiddleEarth.id("gem_ore_" + name)));
        }
    }

    private static void add(String name, Placement placement) {
        PLACEMENTS.put(name, placement);
    }

    public static TagKey<Biome> regionTag(String region) {
        return TagKey.of(RegistryKeys.BIOME, MiddleEarth.id("gem_region/" + region));
    }

    public static void bootstrapConfigured(Registerable<ConfiguredFeature<?, ?>> context) {
        PLACEMENTS.forEach((name, p) -> {
            List<Block> hosts = p.hosts().stream().map(id -> Registries.BLOCK.get(Identifier.of(id))).toList();
            ConfiguredFeatures.register(context, CONFIGURED.get(name), FeatureRegistryME.GEM_ORE,
                    new GemOreFeatureConfig(p.gem(), hosts, p.minY(), p.maxY(), p.minPerChunk(), p.maxPerChunk(),
                            Optional.ofNullable(p.region()).map(GemOreFeatureRegistryME::regionTag)));
        });
    }

    public static void bootstrapPlaced(Registerable<PlacedFeature> context) {
        RegistryEntryLookup<ConfiguredFeature<?, ?>> lookup = context.getRegistryLookup(RegistryKeys.CONFIGURED_FEATURE);
        PLACEMENTS.keySet().forEach(name ->
                PlacedFeatures.register(context, PLACED.get(name), lookup.getOrThrow(CONFIGURED.get(name)),
                        List.of(net.minecraft.world.gen.placementmodifier.BiomePlacementModifier.of())));
    }

    /** Adds every gem placement to a cave biome, after its ores. */
    public static void addTo(GenerationSettings.LookupBackedBuilder generationSettings) {
        PLACED.values().forEach(key -> generationSettings.feature(GenerationStep.Feature.UNDERGROUND_DECORATION, key));
    }

    /** The placement names, e.g. for a locate command. */
    public static List<String> names() {
        return Arrays.asList(PLACEMENTS.keySet().toArray(String[]::new));
    }
}
