package net.sevenstars.of_beasts_and_wild_things.entity.model;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.sevenstars.of_beasts_and_wild_things.OfBeastsAndWildThings;

public final class EntityModelLayersWT {
    public static final ModelLayerLocation SNAIL = layer("snail");
    public static final ModelLayerLocation PHEASANT = layer("pheasant");
    public static final ModelLayerLocation SWAN = layer("swan");
    public static final ModelLayerLocation SWAN_BABY = layer("swan_baby");
    public static final ModelLayerLocation DEER = layer("deer");

    private EntityModelLayersWT() {
    }

    private static ModelLayerLocation layer(String name) {
        return new ModelLayerLocation(OfBeastsAndWildThings.of(name), "main");
    }
}
