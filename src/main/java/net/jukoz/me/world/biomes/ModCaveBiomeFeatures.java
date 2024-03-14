package net.jukoz.me.world.biomes;

import net.jukoz.me.world.features.underground.CavesPlacedFeatures;
import net.minecraft.world.biome.GenerationSettings;
import net.minecraft.world.gen.GenerationStep;

public class ModCaveBiomeFeatures {

    public static void addRedAgateGeode(GenerationSettings.LookupBackedBuilder builder) {
        builder.feature(GenerationStep.Feature.LOCAL_MODIFICATIONS, CavesPlacedFeatures.RED_AGATE_GEODE);
    }

}
