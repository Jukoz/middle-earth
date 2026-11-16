package net.sevenstars.middleearth.resources.datas.rings;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.text.TextColor;
import net.sevenstars.middleearth.block.special.forge.MetalTypes;

/** A ring metal entry in the {@code middle-earth:ring_metal} registry: its forge metal, tooltip colour and slot count. */
public record RingMetal(MetalTypes metal, TextColor color, int slots) {
    public static final int MAX_SLOTS = 4;

    public static final Codec<RingMetal> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            MetalTypes.CODEC.fieldOf("metal").forGetter(RingMetal::metal),
            TextColor.CODEC.fieldOf("color").forGetter(RingMetal::color),
            Codec.intRange(1, MAX_SLOTS).fieldOf("slots").forGetter(RingMetal::slots)
    ).apply(instance, RingMetal::new));
}
