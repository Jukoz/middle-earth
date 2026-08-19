package net.sevenstars.of_beasts_and_wild_things.client;

import com.mojang.blaze3d.platform.NativeImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimplePreparableReloadListener;
import net.minecraft.util.Mth;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.level.ColorResolver;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;

public final class DryFoliageColorResolver extends SimplePreparableReloadListener<int[]> {
    public static final DryFoliageColorResolver RELOAD_LISTENER = new DryFoliageColorResolver();
    public static final ColorResolver COLOR_RESOLVER = (biome, x, z) -> getColor(biome);
    public static final int FALLBACK_COLOR = 0xFF5C3C32;

    private static final ResourceLocation COLORMAP =
            ResourceLocation.withDefaultNamespace("textures/colormap/dry_foliage.png");
    private static final int[] NO_COLORMAP = new int[0];
    private static final Map<ResourceLocation, Integer> BIOME_OVERRIDES = Map.of(
            Biomes.SWAMP.location(), 8082228,
            Biomes.MANGROVE_SWAMP.location(), 8082228,
            Biomes.DARK_FOREST.location(), 8082228,
            ResourceLocation.withDefaultNamespace("pale_garden"), 10528412
    );

    private static volatile int[] pixels = NO_COLORMAP;

    private DryFoliageColorResolver() {
    }

    private static int getColor(Biome biome) {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.level != null) {
            ResourceLocation biomeId = minecraft.level.registryAccess()
                    .registryOrThrow(Registries.BIOME)
                    .getKey(biome);
            Integer override = BIOME_OVERRIDES.get(biomeId);
            if (override != null) {
                return override;
            }
        }

        Biome.ClimateSettings climate = biome.getModifiedClimateSettings();
        double temperature = Mth.clamp(climate.temperature(), 0.0F, 1.0F);
        double downfall = Mth.clamp(climate.downfall(), 0.0F, 1.0F);
        return sample(temperature, downfall);
    }

    private static int sample(double temperature, double downfall) {
        downfall *= temperature;
        int x = (int) ((1.0 - temperature) * 255.0);
        int y = (int) ((1.0 - downfall) * 255.0);
        int index = y << 8 | x;
        int[] currentPixels = pixels;
        return index >= currentPixels.length ? FALLBACK_COLOR : currentPixels[index];
    }

    @Override
    protected int[] prepare(ResourceManager resourceManager, ProfilerFiller profiler) {
        Resource resource = resourceManager.getResource(COLORMAP).orElse(null);
        if (resource == null) {
            return NO_COLORMAP;
        }

        try (InputStream input = resource.open(); NativeImage image = NativeImage.read(input)) {
            return image.makePixelArray();
        } catch (IOException exception) {
            throw new IllegalStateException("Failed to load dry foliage color texture", exception);
        }
    }

    @Override
    protected void apply(int[] reloadedPixels, ResourceManager resourceManager, ProfilerFiller profiler) {
        pixels = reloadedPixels;
    }
}
