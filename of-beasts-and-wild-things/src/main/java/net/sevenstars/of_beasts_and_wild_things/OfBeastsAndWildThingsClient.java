package net.sevenstars.of_beasts_and_wild_things;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.sevenstars.of_beasts_and_wild_things.entity.ModEntities;
import net.sevenstars.of_beasts_and_wild_things.entity.model.ModEntityModels;
import net.sevenstars.of_beasts_and_wild_things.entity.snail.SnailEntityRenderer;

public class OfBeastsAndWildThingsClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {

        ModEntityModels.getModels();

        EntityRendererRegistry.register(ModEntities.SNAIL, SnailEntityRenderer::new);

    }

}
