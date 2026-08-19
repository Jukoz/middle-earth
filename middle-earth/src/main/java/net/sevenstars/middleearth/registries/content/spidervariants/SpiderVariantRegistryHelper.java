package net.sevenstars.middleearth.registries.content.spidervariants;

import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.entity.spider.SpiderVariant;

public class SpiderVariantRegistryHelper {
    private static final String TEXTURE_PATH = "entities/spiders/";
    private static final String ENTITY_NAME = "_shelobite_";


    public static SpiderVariant.SpiderAssetInfo createAssetInfos(String textureName){
        return new SpiderVariant.SpiderAssetInfo(
            MiddleEarth.of(TEXTURE_PATH + textureName + ENTITY_NAME + "larva"),
            MiddleEarth.of(TEXTURE_PATH + textureName + ENTITY_NAME + "scuttler"),
            MiddleEarth.of(TEXTURE_PATH + textureName + "_spawn_of_shelob"));
    }
}
