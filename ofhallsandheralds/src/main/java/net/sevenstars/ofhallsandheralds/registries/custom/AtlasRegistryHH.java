package net.sevenstars.ofhallsandheralds.registries.custom;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.util.Identifier;
import net.sevenstars.api.registries.AtlasRegistryiesAPI;
import net.sevenstars.ofhallsandheralds.OfHallsAndHeralds;

public class AtlasRegistryHH {
    public static final Identifier GENERIC_FACTIONS = OfHallsAndHeralds.id("generic_factions");

    public static Identifier getAtlasPath(Identifier atlasIdentifier) {
        return OfHallsAndHeralds.ofPath("textures", "atlas", String.format("%s.png", atlasIdentifier.getPath()));
    }

    @Environment(EnvType.CLIENT)
    public static SpriteAtlasTexture getAtlasFromPath(Identifier atlasPath) {
        MinecraftClient client = MinecraftClient.getInstance();
        return (SpriteAtlasTexture) client.getTextureManager().getTexture(atlasPath);
    }

    public static void registerAtlas(){
        AtlasRegistryiesAPI.injectAtlas(EntityModelLayersRegistryHH.GENERIC_FACTION_ATLAS_TEXTURE, GENERIC_FACTIONS);
    }
}
