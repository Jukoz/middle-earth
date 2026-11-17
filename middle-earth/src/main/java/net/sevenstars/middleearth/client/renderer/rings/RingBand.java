package net.sevenstars.middleearth.client.renderer.rings;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.math.ColorHelper;
import net.minecraft.util.math.MathHelper;
import net.sevenstars.middleearth.item.DataComponentTypesME;
import net.sevenstars.middleearth.item.dataComponents.RingForgingDataComponent;
import net.sevenstars.middleearth.item.items.rings.RingSlot;
import net.sevenstars.middleearth.registries.content.gems.GemTypeRegistry;
import net.sevenstars.middleearth.registries.content.rings.RingMetalRegistry;
import net.sevenstars.middleearth.resources.datas.gems.GemType;
import org.jetbrains.annotations.Nullable;

/**
 * How a worn ring looks on the hand: metal-tinted band, optional gem pixel, and a polarity cast.
 * Hallowed rings (+6 and up) brighten and glow faintly, deeper bands more; corrupted rings (-6 and below) darken.
 */
@Environment(EnvType.CLIENT)
public record RingBand(int bandColor, int gemColor, boolean hasGem, int glow) {
    private static final int FALLBACK = 0xC0C0C0;

    public static @Nullable RingBand of(PlayerEntity player) {
        ItemStack worn = RingSlot.get(player);
        RingForgingDataComponent forging = worn.get(DataComponentTypesME.RING_FORGING);
        if (forging == null) {
            return null;
        }
        RegistryWrapper.WrapperLookup lookup = player.getRegistryManager();
        int band = RingMetalRegistry.get(lookup, worn.get(DataComponentTypesME.RING_METAL))
                .map(metal -> metal.color().getRgb()).orElse(FALLBACK);
        RegistryKey<GemType> gemType = worn.get(DataComponentTypesME.GEM_TYPE);
        boolean hasGem = forging.gem().isPresent();
        int gem = hasGem && gemType != null ? GemTypeRegistry.get(lookup, gemType).map(type -> type.color().getRgb()).orElse(0xFFFFFF) : 0xFFFFFF;

        int polarity = forging.polarity();
        float depth = Math.abs(polarity) / 100.0F;
        int glow = 0;
        if (polarity >= 6) {
            band = ColorHelper.lerp(0.3F * depth, band, 0xFFFFFF);
            glow = 6 + Math.round(9 * depth);
        } else if (polarity <= -6) {
            float keep = 1.0F - 0.45F * depth;
            band = scale(band, keep);
            gem = scale(gem, keep);
        }
        return new RingBand(0xFF000000 | band, 0xFF000000 | gem, hasGem, MathHelper.clamp(glow, 0, 15));
    }

    private static int scale(int rgb, float factor) {
        return ColorHelper.getArgb(0, Math.round(ColorHelper.getRed(rgb) * factor),
                Math.round(ColorHelper.getGreen(rgb) * factor), Math.round(ColorHelper.getBlue(rgb) * factor)) & 0xFFFFFF;
    }
}
