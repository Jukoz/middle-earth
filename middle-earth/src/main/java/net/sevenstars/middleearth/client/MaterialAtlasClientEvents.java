package net.sevenstars.middleearth.client;

import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterMaterialAtlasesEvent;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.registries.AtlasesME;

@EventBusSubscriber(modid = MiddleEarth.NEOFORGE_MOD_ID, value = Dist.CLIENT)
public final class MaterialAtlasClientEvents {
    private MaterialAtlasClientEvents() {
    }

    @SubscribeEvent
    private static void registerMaterialAtlases(RegisterMaterialAtlasesEvent event) {
        event.register(
                ModTexturedRenderLayers.CHARACTER_ATLAS_TEXTURES,
                AtlasesME.CHARACTER_TEXTURES
        );
        ResourceLocation sprites = MiddleEarth.of("sprites");
        event.register(sprites, sprites);
    }
}
