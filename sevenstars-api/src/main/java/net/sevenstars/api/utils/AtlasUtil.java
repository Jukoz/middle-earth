package net.sevenstars.api.utils;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.texture.AtlasManager;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.util.Identifier;
import net.sevenstars.api.SevenStarsApi;

import java.util.ArrayList;
import java.util.List;

public class AtlasUtil {
    private static List<AtlasManager.Metadata> atlases;

    public static void injectAtlas(Identifier key, Identifier value){
        if(atlases == null)
            atlases = new ArrayList<>();
        atlases.add(new AtlasManager.Metadata(key, value, false));
    }

    public static List<AtlasManager.Metadata> getAtlases() {
        if(atlases == null){
            return new ArrayList<>();
        }
        return atlases;
    }

    @Environment(EnvType.CLIENT)
    public static Sprite getAtlasSprite(Identifier atlasId, Identifier spriteId) {
        MinecraftClient client = MinecraftClient.getInstance();
        SpriteAtlasTexture atlasTexture = client.getAtlasManager().getAtlasTexture(atlasId);
        if(atlasTexture == null){
            SevenStarsApi.logger().logError("Could not find atlas texture for " + atlasId);
            return null;
        }
        Sprite foundSprite = atlasTexture.getSprite(spriteId);
        if(foundSprite == null){
            SevenStarsApi.logger().logError("Could not find atlas sprite for " + spriteId + " in " + atlasId);
        }
        return foundSprite;
    }
}
