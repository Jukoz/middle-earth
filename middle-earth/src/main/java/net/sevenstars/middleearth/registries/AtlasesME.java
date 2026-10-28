package net.sevenstars.middleearth.registries;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.util.Identifier;
import net.sevenstars.api.registries.AtlasRegistryiesAPI;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.client.TexturedRenderLayersME;
import net.sevenstars.middleearth.datageneration.providers.dynamic.CharacterAtlasTexturesProvider;

/**
 * Middle-earth mod atlases<br>
 */
public class AtlasesME {
    public static final Identifier CHARACTER_TEXTURES = MiddleEarth.id("character_textures");

    public static final Identifier SKIN_PREFIX = MiddleEarth.idFilePath("character", "skins");
    public static final Identifier HAIR_PREFIX = MiddleEarth.idFilePath("character", "hairs");
    public static final Identifier EYE_PREFIX = MiddleEarth.idFilePath("character", "eyes");
    public static final Identifier CLOTHES_BASE_PREFIX = MiddleEarth.idFilePath("character", "clothes", "base");
    public static final Identifier CLOTHES_OVER_PREFIX = MiddleEarth.idFilePath("character", "clothes", "over");
    public static final Identifier CLOTHES_EXTRA_PREFIX = MiddleEarth.idFilePath("character", "clothes", "extra");

    public static Identifier getAtlasPath(Identifier atlasIdentifier) {
        return MiddleEarth.idFilePath("textures", "atlas", String.format("%s.png", atlasIdentifier.getPath()));
    }

    @Environment(EnvType.CLIENT)
    public static SpriteAtlasTexture getAtlasFromPath(Identifier atlasPath){
        MinecraftClient client = MinecraftClient.getInstance();
        return client.getBakedModelManager().getAtlas(atlasPath);
    }

    public static void addProviders(FabricDataGenerator.Pack pack) {
        pack.addProvider(CharacterAtlasTexturesProvider::new);
    }

    public static void registerAtlas(){
        AtlasRegistryiesAPI.injectAtlas(TexturedRenderLayersME.CHARACTER_ATLAS_TEXTURES, CHARACTER_TEXTURES);
        AtlasRegistryiesAPI.injectAtlas(MiddleEarth.id("sprites"), MiddleEarth.id("sprites"));
    }
}
