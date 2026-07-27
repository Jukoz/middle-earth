package net.sevenstars.middleearth.registries.content.greathornvariants;

import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.entity.beasts.great_horn.GreatHornVariant;

public class GreatHornVariantRegistryHelper {
    private static final String TEXTURE_PATH = "entities/great_horn/";
    private static final String ENTITY_NAME = "_great_horn";

    public static GreatHornVariant.GreatHornAssetInfo createAssetInfos(String textureName){
        return new GreatHornVariant.GreatHornAssetInfo(
                MiddleEarth.of(TEXTURE_PATH + textureName + ENTITY_NAME));
    }
}
