package net.sevenstars.middleearth.mixin.client;

import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.render.model.BakedModelManager;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.client.ModTexturedRenderLayers;
import net.sevenstars.middleearth.registries.AtlasesME;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashMap;
import java.util.Map;


@Mixin(BakedModelManager.class)
public class BakedModelManagerMixin{

    @Mutable
    @Shadow @Final
    private static Map<Identifier, Identifier> LAYERS_TO_LOADERS;

    @Inject(method = "<init>", at=@At("HEAD"))
    private static void addNewAtlas(TextureManager textureManager, BlockColors colorMap, int mipmap, CallbackInfo ci)
    {
        HashMap<Identifier, Identifier> map = new HashMap<>(LAYERS_TO_LOADERS);
        map.put(ModTexturedRenderLayers.CHARACTER_SKIN_ATLAS_TEXTURE, AtlasesME.CHARACTER_SKINS);
        map.put(ModTexturedRenderLayers.CHARACTER_EYES_ATLAS_TEXTURE, AtlasesME.CHARACTER_EYES);
        map.put(ModTexturedRenderLayers.CHARACTER_HAIRS_ATLAS_TEXTURE, AtlasesME.CHARACTER_HAIRS);
        map.put(ModTexturedRenderLayers.CHARACTER_CLOTHES_ATLAS_TEXTURE, AtlasesME.CHARACTER_CLOTHES);

        /*
         * Load custom sprites' atlas. Used for direct render animations.
         */
        map.put(Identifier.of(MiddleEarth.MOD_ID, "sprites"), Identifier.of(MiddleEarth.MOD_ID, "sprites"));

        LAYERS_TO_LOADERS = map;
    }
}
