package net.sevenstars.middleearth.features.inscription;

import net.sevenstars.middleearth.common.feature.Feature;

public class Inscription implements Feature {
    @Override
    public void register() {
        InscriptionRecipes.register();
        Feature.load(InscriptionBlocks.class);
        InscriptionScreenHandlers.register();
        Feature.load(InscriptionItems.class);
    }
}
