package net.sevenstars.middleearth.resources.datas.rings;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.Block;
import net.minecraft.registry.Registries;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.text.TextColor;
import net.sevenstars.middleearth.registries.DynamicRegistriesME;

import java.util.List;

/** A ring theme in the {@code middle-earth:ring_theme} registry: the anvils that sing it, its polarity shift and its word tag. */
public record RingTheme(List<Block> anvils, int polarity, TextColor color, TagKey<RingWord> words) {
    public static final Codec<RingTheme> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Registries.BLOCK.getCodec().listOf().fieldOf("anvils").forGetter(RingTheme::anvils),
            Codec.intRange(RingPolarity.MIN, RingPolarity.MAX).fieldOf("polarity").forGetter(RingTheme::polarity),
            TextColor.CODEC.fieldOf("color").forGetter(RingTheme::color),
            TagKey.codec(DynamicRegistriesME.RING_WORD).fieldOf("words").forGetter(RingTheme::words)
    ).apply(instance, RingTheme::new));
}
