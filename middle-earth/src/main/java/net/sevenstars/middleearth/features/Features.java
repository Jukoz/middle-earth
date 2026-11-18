package net.sevenstars.middleearth.features;

import net.sevenstars.middleearth.common.feature.Feature;
import net.sevenstars.middleearth.features.smithing.Smithing;
import net.sevenstars.middleearth.features.cooking.Cooking;
import net.sevenstars.middleearth.features.storage.Storage;
import net.sevenstars.middleearth.features.inscription.Inscription;
import net.sevenstars.middleearth.features.artisan.Artisan;
import net.sevenstars.middleearth.features.farming.Farming;

import java.util.List;

public class Features {
    // This order is the creative tab order
    public static final List<Feature> ALL = List.of(
            new Smithing(),
            new Cooking(),
            new Storage(),
            new Inscription(),
            new Artisan(),
            new Farming()
    );
}
