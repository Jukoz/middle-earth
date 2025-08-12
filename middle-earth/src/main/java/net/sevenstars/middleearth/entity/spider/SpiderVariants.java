package net.sevenstars.middleearth.entity.spider;

import net.fabricmc.fabric.api.event.registry.DynamicRegistries;
import net.minecraft.entity.passive.WolfVariant;
import net.minecraft.entity.spawn.BiomeSpawnCondition;
import net.minecraft.entity.spawn.SpawnConditionSelectors;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntryList;
import net.minecraft.registry.tag.BiomeTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.AssetInfo;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.entity.ModEntities;
import net.sevenstars.middleearth.entity.ModTrackedDataHandlerRegistry;
import net.sevenstars.middleearth.resources.datas.races.Race;

public class SpiderVariants {
	public final static String PATH = "spider_variants";
	public static final RegistryKey<Registry<SpiderVariant>> KEY = RegistryKey.ofRegistry(Identifier.of(MiddleEarth.MOD_ID, PATH));

	public static final RegistryKey<SpiderVariant> MIRKWOOD = of("mirkwood");
	public static final RegistryKey<SpiderVariant> CAVE = of("cave");
	public static final RegistryKey<SpiderVariant> DEFAULT = MIRKWOOD;

	private static RegistryKey<SpiderVariant> of(String id) {
		return RegistryKey.of(SpiderVariants.KEY, Identifier.of(MiddleEarth.MOD_ID, id));
	}

	private static void register(Registerable<SpiderVariant> registry, RegistryKey<SpiderVariant> key, String textureName, TagKey<Biome> biomeTag) {
		register(registry, key, textureName, createSpawnConditions(registry.getRegistryLookup(RegistryKeys.BIOME).getOrThrow(biomeTag)));
	}

	private static void register(Registerable<SpiderVariant> registry, RegistryKey<SpiderVariant> key, String textureName, SpawnConditionSelectors spawnConditions) {
		registry.register(key, new SpiderVariant(new AssetInfo(Identifier.of(MiddleEarth.MOD_ID, "entities/spiders/" + textureName)), spawnConditions));
	}

	private static SpawnConditionSelectors createSpawnConditions(RegistryEntryList<Biome> requiredBiomes) {
		return SpawnConditionSelectors.createSingle(new BiomeSpawnCondition(requiredBiomes), 1);
	}

	public static void bootstrap(Registerable<SpiderVariant> registry) {
		register(registry, MIRKWOOD, "mirkwood_shelobite_scuttler", SpawnConditionSelectors.createFallback(0));
		register(registry, CAVE, "blind_shelobite_scuttler", BiomeTags.IS_SAVANNA);
	}

	public static void register(){
		MiddleEarth.LOGGER.logDebugMsg("Registering Spider Variants for " + MiddleEarth.MOD_ID);
		DynamicRegistries.registerSynced(KEY, SpiderVariant.CODEC);
	}
}
