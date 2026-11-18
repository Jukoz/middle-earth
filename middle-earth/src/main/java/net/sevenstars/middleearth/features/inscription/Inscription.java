package net.sevenstars.middleearth.features.inscription;

public class Inscription {
    public static void register() {
        InscriptionRecipes.register();
        InscriptionBlocks.register();
        InscriptionScreenHandlers.register();
        InscriptionItems.register();
    }
}
