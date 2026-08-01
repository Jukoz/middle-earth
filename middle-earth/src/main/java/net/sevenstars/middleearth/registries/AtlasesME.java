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
    public static final Identifier CHARACTER_TEXTURES = MiddleEarth.of("character_textures");

    public static final Identifier SKIN_PREFIX = MiddleEarth.ofPath("character", "skins");
    public static final Identifier HAIR_PREFIX = MiddleEarth.ofPath("character", "hairs");
    public static final Identifier EYE_PREFIX = MiddleEarth.ofPath("character", "eyes");
    public static final Identifier CLOTHES_BASE_PREFIX = MiddleEarth.ofPath("character", "clothes", "base");
    public static final Identifier CLOTHES_OVER_PREFIX = MiddleEarth.ofPath("character", "clothes", "over");
    public static final Identifier CLOTHES_EXTRA_PREFIX = MiddleEarth.ofPath("character", "clothes", "extra");

    public static Identifier getAtlasPath(Identifier atlasIdentifier) {
        return MiddleEarth.ofPath("textures", "atlas", String.format("%s.png", atlasIdentifier.getPath()));
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
