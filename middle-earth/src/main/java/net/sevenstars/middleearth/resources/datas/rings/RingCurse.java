package net.sevenstars.middleearth.resources.datas.rings;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.text.TextColor;
import net.minecraft.util.StringIdentifiable;

/** A curse in the {@code middle-earth:ring_curse} registry: minor or major, and how often it is rolled. */
public record RingCurse(Severity severity, int weight) {
    public static final Codec<RingCurse> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Severity.CODEC.fieldOf("severity").forGetter(RingCurse::severity),
            Codec.intRange(1, Integer.MAX_VALUE).optionalFieldOf("weight", 1).forGetter(RingCurse::weight)
    ).apply(instance, RingCurse::new));

    public enum Severity implements StringIdentifiable {
        MINOR("minor", 0xC86E5A),
        MAJOR("major", 0x9E1B1B);

        public static final Codec<Severity> CODEC = StringIdentifiable.createCodec(Severity::values);

        private final String name;
        private final TextColor color;

        Severity(String name, int color) {
            this.name = name;
            this.color = TextColor.fromRgb(color);
        }

        public TextColor color() {
            return color;
        }

        @Override
        public String asString() {
            return name;
        }
    }
}
