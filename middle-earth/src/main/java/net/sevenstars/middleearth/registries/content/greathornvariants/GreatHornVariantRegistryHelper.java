package net.sevenstars.middleearth.registries.content.greathornvariants;

import net.minecraft.entity.spawn.BiomeSpawnCondition;
import net.minecraft.entity.spawn.SpawnConditionSelectors;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntryList;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.AssetInfo;
import net.minecraft.world.biome.Biome;
import net.sevenstars.middleearth.entity.beasts.great_horn.GreatHornVariant;
import net.sevenstars.middleearth.utils.IdentifierUtil;

public class GreatHornVariantRegistryHelper {
    private static final String TEXTURE_PATH = "entities/great_horn/";
    private static final String ENTITY_NAME = "_great_horn";

    public static GreatHornVariant.GreatHornAssetInfo createAssetInfos(String textureName){
        return new GreatHornVariant.GreatHornAssetInfo(
                new AssetInfo(IdentifierUtil.build(TEXTURE_PATH + textureName + ENTITY_NAME)));
    }

    public static SpawnConditionSelectors createSpawnConditions(Registerable<GreatHornVariant> registry, TagKey<Biome> biomeTag, int priority) {
        return createSpawnConditions(registry.getRegistryLookup(RegistryKeys.BIOME).getOrThrow(biomeTag), priority);
    }

    public static SpawnConditionSelectors createSpawnConditions(RegistryEntryList<Biome> requiredBiomes, int priority) {
        return SpawnConditionSelectors.createSingle(new BiomeSpawnCondition(requiredBiomes), priority);
    }
}
