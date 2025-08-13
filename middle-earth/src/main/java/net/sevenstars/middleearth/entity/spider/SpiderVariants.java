package net.sevenstars.middleearth.entity.spider;

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

public class SpiderVariants {
	public final static String KEY_PATH = "spider_variants";
	private static final String TEXTURE_PATH = "entities/spiders/";
	public static final RegistryKey<Registry<SpiderVariant>> KEY = RegistryKey.ofRegistry(Identifier.of(MiddleEarth.MOD_ID, KEY_PATH));

	public static final RegistryKey<SpiderVariant> MIRKWOOD = of("mirkwood");
	public static final RegistryKey<SpiderVariant> CAVE = of("cave");
	public static final RegistryKey<SpiderVariant> MORDOR = of("mordor");
	public static final RegistryKey<SpiderVariant> DEFAULT = MIRKWOOD;

	private static RegistryKey<SpiderVariant> of(String id) {
		return RegistryKey.of(SpiderVariants.KEY, Identifier.of(MiddleEarth.MOD_ID, id));
	}

	private static void register(Registerable<SpiderVariant> registry, RegistryKey<SpiderVariant> key, String textureName, TagKey<Biome> biomeTag, int priority) {
		register(registry, key, textureName, createSpawnConditions(registry.getRegistryLookup(RegistryKeys.BIOME).getOrThrow(biomeTag), priority));
	}

	private static void register(Registerable<SpiderVariant> registry, RegistryKey<SpiderVariant> key, String textureName, SpawnConditionSelectors spawnConditions) {
		registry.register(key, new SpiderVariant(new AssetInfo(Identifier.of(MiddleEarth.MOD_ID, TEXTURE_PATH + textureName)), spawnConditions));
	}

	private static SpawnConditionSelectors createSpawnConditions(RegistryEntryList<Biome> requiredBiomes, int priority) {
		return SpawnConditionSelectors.createSingle(new BiomeSpawnCondition(requiredBiomes), priority);
	}

	public static void bootstrap(Registerable<SpiderVariant> registry) {
		register(registry, MIRKWOOD, "mirkwood_shelobite_scuttler", SpawnConditionSelectors.createFallback(0));
		register(registry, CAVE, "blind_shelobite_scuttler", TagKey.of(RegistryKeys.BIOME, Identifier.of(MiddleEarth.MOD_ID, "is_cave")), 1);
		register(registry, MORDOR, "mordor_shelobite_scuttler", TagKey.of(RegistryKeys.BIOME, Identifier.of(MiddleEarth.MOD_ID, "is_mordor")), 2);
	}

	public static void register(){
		MiddleEarth.LOGGER.logDebugMsg("Registering Spider Variants for " + MiddleEarth.MOD_ID);
		DynamicRegistries.registerSynced(KEY, SpiderVariant.CODEC);
	}
}
