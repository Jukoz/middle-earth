package net.sevenstars.middleearth.registries;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.datageneration.providers.dynamic.CharacterAtlasTexturesProvider;

/**
 * Middle-earth mod atlases<br>
 */
public class AtlasesME {
    public static final Identifier CHARACTER_SKINS = MiddleEarth.of("character_skins");
    public static final Identifier CHARACTER_HAIRS = MiddleEarth.of("character_hairs");
    public static final Identifier CHARACTER_EYES = MiddleEarth.of("character_eyes");
    public static final Identifier CHARACTER_CLOTHES = MiddleEarth.of("character_clothes");

    public static Identifier prefixAtlas(Identifier sprite, Identifier atlas) {
        return sprite.withPrefixedPath(String.format("%s/", atlas.getPath()));
    }

    public static Identifier getAtlasPath(Identifier atlasIdentifier) {
        return Identifier.of(MiddleEarth.MOD_ID, String.format("textures/atlas/%s.png", atlasIdentifier.getPath()));
    }

    @Environment(EnvType.CLIENT)
    public static SpriteAtlasTexture getAtlasFromPath(Identifier atlasPath){
        MinecraftClient client = MinecraftClient.getInstance();
        return client.getBakedModelManager().getAtlas(atlasPath);
    }

    public static void addProviders(FabricDataGenerator.Pack pack) {
        pack.addProvider(CharacterAtlasTexturesProvider::new);
    }
}
