package net.sevenstars.middleearth.registries;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.sevenstars.middleearth.MiddleEarth;

/**
 * Middle-earth mod atlases<br>
 */
public class AtlasesME {
    public static final ResourceLocation CHARACTER_TEXTURES = MiddleEarth.of("character_textures");

    public static final ResourceLocation SKIN_PREFIX = MiddleEarth.ofPath("character", "skins");
    public static final ResourceLocation HAIR_PREFIX = MiddleEarth.ofPath("character", "hairs");
    public static final ResourceLocation EYE_PREFIX = MiddleEarth.ofPath("character", "eyes");
    public static final ResourceLocation CLOTHES_BASE_PREFIX = MiddleEarth.ofPath("character", "clothes", "base");
    public static final ResourceLocation CLOTHES_OVER_PREFIX = MiddleEarth.ofPath("character", "clothes", "over");
    public static final ResourceLocation CLOTHES_EXTRA_PREFIX = MiddleEarth.ofPath("character", "clothes", "extra");

    public static ResourceLocation getAtlasPath(ResourceLocation atlasIdentifier) {
        return MiddleEarth.ofPath("textures", "atlas", String.format("%s.png", atlasIdentifier.getPath()));
    }

    @OnlyIn(Dist.CLIENT)
    public static TextureAtlas getAtlasFromPath(ResourceLocation atlasPath){
        Minecraft client = Minecraft.getInstance();
        return client.getModelManager().getAtlas(atlasPath);
    }
}
