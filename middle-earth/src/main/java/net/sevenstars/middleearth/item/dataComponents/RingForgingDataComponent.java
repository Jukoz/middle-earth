package net.sevenstars.middleearth.item.dataComponents;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.math.MathHelper;
import net.sevenstars.middleearth.item.items.gems.GemCut;
import net.sevenstars.middleearth.registries.DynamicRegistriesME;
import net.sevenstars.middleearth.resources.datas.rings.RingCurse;
import net.sevenstars.middleearth.resources.datas.rings.RingMetal;
import net.sevenstars.middleearth.resources.datas.rings.RingPolarity;
import net.sevenstars.middleearth.resources.datas.rings.RingTheme;
import net.sevenstars.middleearth.resources.datas.rings.RingWord;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/** A ring's forging progress: purity, step qualities, polarity, Fëa, theme, inscribed words, set gem and curses. */
public record RingForgingDataComponent(int purity, Optional<Integer> shaping, Optional<Integer> inscription,
                                       int polarity, int fea, Optional<RegistryKey<RingTheme>> theme,
                                       List<Inscribed> words, Optional<Gem> gem,
                                       List<RegistryKey<RingCurse>> curses) {
    private static final Codec<Integer> PERCENT = Codec.intRange(0, 100);
    public static final int FEA_PER_SLOT = 5;

    public static final Codec<RingForgingDataComponent> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            PERCENT.fieldOf("purity").forGetter(RingForgingDataComponent::purity),
            PERCENT.optionalFieldOf("shaping").forGetter(RingForgingDataComponent::shaping),
            PERCENT.optionalFieldOf("inscription").forGetter(RingForgingDataComponent::inscription),
            Codec.intRange(RingPolarity.MIN, RingPolarity.MAX).optionalFieldOf("polarity", 0).forGetter(RingForgingDataComponent::polarity),
            Codec.intRange(0, Integer.MAX_VALUE).optionalFieldOf("fea", 0).forGetter(RingForgingDataComponent::fea),
            RegistryKey.createCodec(DynamicRegistriesME.RING_THEME).optionalFieldOf("theme").forGetter(RingForgingDataComponent::theme),
            Inscribed.CODEC.listOf().optionalFieldOf("words", List.of()).forGetter(RingForgingDataComponent::words),
            Gem.CODEC.optionalFieldOf("gem").forGetter(RingForgingDataComponent::gem),
            RegistryKey.createCodec(DynamicRegistriesME.RING_CURSE).listOf().optionalFieldOf("curses", List.of()).forGetter(RingForgingDataComponent::curses)
    ).apply(instance, RingForgingDataComponent::new));

    public static final PacketCodec<ByteBuf, RingForgingDataComponent> PACKET_CODEC = PacketCodec.tuple(
            PacketCodecs.VAR_INT, RingForgingDataComponent::purity,
            PacketCodecs.optional(PacketCodecs.VAR_INT), RingForgingDataComponent::shaping,
            PacketCodecs.optional(PacketCodecs.VAR_INT), RingForgingDataComponent::inscription,
            PacketCodecs.VAR_INT, RingForgingDataComponent::polarity,
            PacketCodecs.VAR_INT, RingForgingDataComponent::fea,
            PacketCodecs.optional(RegistryKey.createPacketCodec(DynamicRegistriesME.RING_THEME)), RingForgingDataComponent::theme,
            Inscribed.PACKET_CODEC.collect(PacketCodecs.toList()), RingForgingDataComponent::words,
            PacketCodecs.optional(Gem.PACKET_CODEC), RingForgingDataComponent::gem,
            RegistryKey.createPacketCodec(DynamicRegistriesME.RING_CURSE).collect(PacketCodecs.toList()), RingForgingDataComponent::curses,
            RingForgingDataComponent::new);

    public RingForgingDataComponent {
        purity = MathHelper.clamp(purity, 0, 100);
        polarity = MathHelper.clamp(polarity, RingPolarity.MIN, RingPolarity.MAX);
        fea = Math.max(0, fea);
        words = List.copyOf(words);
        curses = List.copyOf(curses);
    }

    public static RingForgingDataComponent refined(int purity, int polarity, int fea) {
        return new RingForgingDataComponent(purity, Optional.empty(), Optional.empty(), polarity, fea, Optional.empty(),
                List.of(), Optional.empty(), List.of());
    }

    /** Average of purity and every step quality reached so far. */
    public int quality() {
        int sum = purity;
        int count = 1;
        for (Optional<Integer> step : List.of(shaping, inscription, gem.map(Gem::quality))) {
            if (step.isPresent()) {
                sum += step.get();
                count++;
            }
        }
        return Math.round((float) sum / count);
    }

    public RingPolarity band() {
        return RingPolarity.of(polarity);
    }

    public int cursePotency() {
        return 100 - quality();
    }

    public int slotsUsed() {
        return words.stream().mapToInt(Inscribed::slots).sum() + (gem.isPresent() ? 1 : 0);
    }

    public boolean hasWord(RegistryKey<RingWord> word) {
        return words.stream().anyMatch(inscribed -> inscribed.word().equals(word));
    }

    public RingForgingDataComponent withShaping(RegistryKey<RingTheme> themeKey, RingTheme ringTheme, int quality, int feaAdded) {
        return new RingForgingDataComponent(purity, Optional.of(quality), inscription, polarity + ringTheme.polarity(),
                fea + feaAdded, Optional.of(themeKey), words, gem, curses);
    }

    public RingForgingDataComponent withWord(RegistryKey<RingWord> word, int slots) {
        List<Inscribed> inscribed = new ArrayList<>(words);
        inscribed.add(new Inscribed(word, slots));
        return new RingForgingDataComponent(purity, shaping, inscription, polarity, fea + slots * FEA_PER_SLOT, theme,
                inscribed, gem, curses);
    }

    public RingForgingDataComponent withInscription(int quality) {
        return new RingForgingDataComponent(purity, shaping, Optional.of(quality), polarity, fea, theme, words, gem, curses);
    }

    public RingForgingDataComponent withGem(Gem setGem) {
        return new RingForgingDataComponent(purity, shaping, inscription, polarity + setGem.cut().polarity(),
                fea + FEA_PER_SLOT, theme, words, Optional.of(setGem), curses);
    }

    public RingForgingDataComponent withCurses(List<RegistryKey<RingCurse>> rolled) {
        return new RingForgingDataComponent(purity, shaping, inscription, polarity, fea, theme, words, gem, rolled);
    }

    /** A word on the ring and the slots of Fëa invested in it (5 levels each), which set its strength. */
    public record Inscribed(RegistryKey<RingWord> word, int slots) {
        public static final Codec<Inscribed> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                RegistryKey.createCodec(DynamicRegistriesME.RING_WORD).fieldOf("word").forGetter(Inscribed::word),
                Codec.intRange(1, RingMetal.MAX_SLOTS).fieldOf("slots").forGetter(Inscribed::slots)
        ).apply(instance, Inscribed::new));

        public static final PacketCodec<ByteBuf, Inscribed> PACKET_CODEC = PacketCodec.tuple(
                RegistryKey.createPacketCodec(DynamicRegistriesME.RING_WORD), Inscribed::word,
                PacketCodecs.VAR_INT, Inscribed::slots,
                Inscribed::new);

        public int fea() {
            return slots * FEA_PER_SLOT;
        }
    }

    public record Gem(GemCut cut, int quality) {
        private static final Codec<GemCut> CUT_CODEC = Codec.STRING.comapFlatMap(
                id -> Arrays.stream(GemCut.values()).filter(cut -> cut.id().equals(id)).findFirst()
                        .map(com.mojang.serialization.DataResult::success)
                        .orElseGet(() -> com.mojang.serialization.DataResult.error(() -> "Unknown gem cut: " + id)),
                GemCut::id);

        public static final Codec<Gem> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                CUT_CODEC.fieldOf("cut").forGetter(Gem::cut),
                PERCENT.fieldOf("quality").forGetter(Gem::quality)
        ).apply(instance, Gem::new));

        public static final PacketCodec<ByteBuf, Gem> PACKET_CODEC = PacketCodec.tuple(
                PacketCodecs.indexed(i -> GemCut.values()[i], GemCut::ordinal), Gem::cut,
                PacketCodecs.VAR_INT, Gem::quality,
                Gem::new);
    }
}
