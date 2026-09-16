package net.sevenstars.ofhallsandheralds.registries.custom;

import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderLayers;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;
import net.sevenstars.ofhallsandheralds.OfHallsAndHeralds;

public class EntityModelLayersRegistryHH {
    private static final String MAIN = "main";

    public static final Identifier GENERIC_FACTION_ATLAS_TEXTURE = AtlasRegistryHH.getAtlasPath(AtlasRegistryHH.GENERIC_FACTIONS);
    public static final RenderLayer GENERIC_FACTION_RENDER_LAYER = RenderLayers.armorCutoutNoCull(GENERIC_FACTION_ATLAS_TEXTURE);

    private static EntityModelLayer registerEntityModelLayer(String registryName, TexturedModelData modelData) {
        EntityModelLayer entityModelLayer = new EntityModelLayer(OfHallsAndHeralds.id(registryName), MAIN);
        EntityModelLayerRegistry.registerModelLayer(entityModelLayer, () -> modelData);
        return entityModelLayer;
    }

    public static RenderLayer getGenericFactionRenderLayer() {
        return GENERIC_FACTION_RENDER_LAYER;
    }
}
