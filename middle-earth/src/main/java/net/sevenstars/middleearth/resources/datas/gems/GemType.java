package net.sevenstars.middleearth.resources.datas.gems;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.text.TextColor;

import java.util.List;

/** A gem type entry in the {@code middle-earth:gem_type} registry. */
public record GemType(GemAspect aspect, TextColor color, List<String> inscriptionWords, int studyBand,
                      List<TextColor> sparkColors) {
    public static final Codec<GemType> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            GemAspect.CODEC.fieldOf("aspect").forGetter(GemType::aspect),
            TextColor.CODEC.fieldOf("color").forGetter(GemType::color),
            Codec.STRING.listOf().optionalFieldOf("inscription_words", List.of()).forGetter(GemType::inscriptionWords),
            Codec.intRange(1, 3).optionalFieldOf("study_band", 1).forGetter(GemType::studyBand),
            TextColor.CODEC.listOf().optionalFieldOf("spark_colors", List.of()).forGetter(GemType::sparkColors)
    ).apply(instance, GemType::new));

    public int sparkRgb(net.minecraft.util.math.random.Random random) {
        return sparkColors.isEmpty() ? color.getRgb() : sparkColors.get(random.nextInt(sparkColors.size())).getRgb();
    }
}
