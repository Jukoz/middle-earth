package net.sevenstars.middleearth.features;

import net.sevenstars.middleearth.common.feature.ClientFeature;
import net.sevenstars.middleearth.features.smithing.SmithingClient;
import net.sevenstars.middleearth.features.cooking.CookingClient;
import net.sevenstars.middleearth.features.storage.StorageClient;
import net.sevenstars.middleearth.features.inscription.InscriptionClient;
import net.sevenstars.middleearth.features.artisan.ArtisanClient;

import java.util.List;

public class ClientFeatures {
    public static final List<ClientFeature> ALL = List.of(
            new SmithingClient(),
            new CookingClient(),
            new StorageClient(),
            new InscriptionClient(),
            new ArtisanClient()
    );
}
