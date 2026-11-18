package net.sevenstars.middleearth.resources.datas.rings;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.sevenstars.middleearth.resources.datas.gems.GemAspect;

import java.util.List;

/** An inscription word in the {@code middle-earth:ring_word} registry: its type and race tags and the fewest slots it can be inscribed with. */
public record RingWord(GemAspect aspect, List<GemAspect> races, int minSlots) {
    public static final Codec<RingWord> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            GemAspect.CODEC.fieldOf("aspect").forGetter(RingWord::aspect),
            GemAspect.CODEC.listOf().optionalFieldOf("races", List.of()).forGetter(RingWord::races),
            Codec.intRange(1, RingMetal.MAX_SLOTS).optionalFieldOf("min_slots", 1).forGetter(RingWord::minSlots)
    ).apply(instance, RingWord::new));
}
