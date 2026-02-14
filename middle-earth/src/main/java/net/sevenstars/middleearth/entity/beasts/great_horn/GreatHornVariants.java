package net.sevenstars.middleearth.entity.beasts.great_horn;

import net.fabricmc.fabric.api.event.registry.DynamicRegistries;
import net.minecraft.entity.spawn.BiomeSpawnCondition;
import net.minecraft.entity.spawn.SpawnConditionSelectors;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntryList;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.AssetInfo;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;
import net.sevenstars.middleearth.MiddleEarth;

public class GreatHornVariants {
	public final static String KEY_PATH = "great_horn_variants";
	private static final String TEXTURE_PATH = "entities/great_horn/";
	private static final String ENTITY_NAME = "_great_horn";
	public static final RegistryKey<Registry<GreatHornVariant>> KEY = RegistryKey.ofRegistry(Identifier.of(MiddleEarth.MOD_ID, KEY_PATH));

	public static final RegistryKey<GreatHornVariant> BROWN = of("brown");
	public static final RegistryKey<GreatHornVariant> TEMPERATE = of("temperate");
	public static final RegistryKey<GreatHornVariant> WARM = of("warm");
	public static final RegistryKey<GreatHornVariant> COLD = of("cold");
	public static final RegistryKey<GreatHornVariant> DEFAULT = BROWN;

	private static RegistryKey<GreatHornVariant> of(String id) {
		return RegistryKey.of(GreatHornVariants.KEY, Identifier.of(MiddleEarth.MOD_ID, id));
	}

	private static void register(Registerable<GreatHornVariant> registry, RegistryKey<GreatHornVariant> key, String textureName, TagKey<Biome> biomeTag, int priority) {
		register(registry, key, textureName, createSpawnConditions(registry.getRegistryLookup(RegistryKeys.BIOME).getOrThrow(biomeTag), priority));
	}

	private static void register(Registerable<GreatHornVariant> registry, RegistryKey<GreatHornVariant> key, String textureName, SpawnConditionSelectors spawnConditions) {
		registry.register(key, new GreatHornVariant(new GreatHornVariant.GreatHornAssetInfo(
				new AssetInfo(Identifier.of(MiddleEarth.MOD_ID, TEXTURE_PATH + textureName + ENTITY_NAME))),
				spawnConditions));
	}

	private static SpawnConditionSelectors createSpawnConditions(RegistryEntryList<Biome> requiredBiomes, int priority) {
		return SpawnConditionSelectors.createSingle(new BiomeSpawnCondition(requiredBiomes), priority);
	}

	private static SpawnConditionSelectors createSpawnConditions(RegistryEntryList<Biome> requiredBiomes, int a, int priority) {
		return SpawnConditionSelectors.createSingle(new BiomeSpawnCondition(requiredBiomes), priority);
	}

	public static void bootstrap(Registerable<GreatHornVariant> registry) {
		register(registry, BROWN, "brown", SpawnConditionSelectors.createFallback(0));
		register(registry, TEMPERATE, "temperate", SpawnConditionSelectors.createFallback(0));
		register(registry, WARM, "warm", TagKey.of(RegistryKeys.BIOME, Identifier.of(MiddleEarth.MOD_ID, "spawns_warm_variant_great_horn")), 0);
		register(registry, COLD, "cold", TagKey.of(RegistryKeys.BIOME, Identifier.of(MiddleEarth.MOD_ID, "spawns_cold_variant_great_horn")), 0);
	}

	public static void register(){
		MiddleEarth.LOGGER.logDebugMsg("Registering Great Horn Variants for " + MiddleEarth.MOD_ID);
		DynamicRegistries.registerSynced(KEY, GreatHornVariant.CODEC);
	}
}
