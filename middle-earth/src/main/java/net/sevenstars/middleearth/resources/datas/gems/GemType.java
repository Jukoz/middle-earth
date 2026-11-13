package net.sevenstars.middleearth.resources.datas.gems;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.text.TextColor;

import java.util.List;

/**
 * A gem type entry in the synced {@code middle-earth:gem_type} dynamic registry
 * (see {@link net.sevenstars.middleearth.registries.content.gems.GemTypeRegistry}).
 * Items point at an entry through the {@code middle-earth:gem_type} data component.
 *
 * @param aspect           what the gem is aligned to (attack, defense, a people...)
 * @param color            text colour for the gem in tooltips
 * @param inscriptionWords words the gem adds at the Inscription Table when it is the catalyst, on top of the
 *                         shared pool (per gem type, because one cut gem item can hold any gem)
 * @param studyBand        how hard the gem is to study at the Lapidary Table, 1 to 3
 * @param sparkColors      the colours its ore's sparks take, one picked at random for each spark (a play of
 *                         colour, as opal's); empty for sparks in {@code color} alone
 */
public record GemType(GemAspect aspect, TextColor color, List<String> inscriptionWords, int studyBand,
                      List<TextColor> sparkColors) {
    public static final Codec<GemType> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            GemAspect.CODEC.fieldOf("aspect").forGetter(GemType::aspect),
            TextColor.CODEC.fieldOf("color").forGetter(GemType::color),
            Codec.STRING.listOf().optionalFieldOf("inscription_words", List.of()).forGetter(GemType::inscriptionWords),
            Codec.intRange(1, 3).optionalFieldOf("study_band", 1).forGetter(GemType::studyBand),
            TextColor.CODEC.listOf().optionalFieldOf("spark_colors", List.of()).forGetter(GemType::sparkColors)
    ).apply(instance, GemType::new));

    /** The colour of one spark off its ore: one of {@code sparkColors} at random, or {@code color} if none. */
    public int sparkRgb(net.minecraft.util.math.random.Random random) {
        return sparkColors.isEmpty() ? color.getRgb() : sparkColors.get(random.nextInt(sparkColors.size())).getRgb();
    }
}
