package net.sevenstars.middleearth.resources.datas.rings;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.text.TextColor;

/**
 * A metal a ring can be refined from, an entry in the synced {@code middle-earth:ring_metal} dynamic registry
 * (see {@link net.sevenstars.middleearth.registries.content.rings.RingMetalRegistry}). A ring points at its metal
 * through the {@code middle-earth:ring_metal} data component; the ring's look comes from the metal's palette
 * ({@code textures/item/ring/palettes/<metal>.png}), named after the entry.
 *
 * @param color text colour for the metal in tooltips
 * @param slots how many inscription slots a ring of this metal has (a set gem takes one of them), 1 to 4
 */
public record RingMetal(TextColor color, int slots) {
    public static final int MAX_SLOTS = 4;

    public static final Codec<RingMetal> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            TextColor.CODEC.fieldOf("color").forGetter(RingMetal::color),
            Codec.intRange(1, MAX_SLOTS).fieldOf("slots").forGetter(RingMetal::slots)
    ).apply(instance, RingMetal::new));
}
