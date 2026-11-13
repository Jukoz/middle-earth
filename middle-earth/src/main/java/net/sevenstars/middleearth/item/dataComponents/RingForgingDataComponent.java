package net.sevenstars.middleearth.item.dataComponents;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.util.math.MathHelper;
import net.sevenstars.middleearth.item.items.gems.GemCut;
import net.sevenstars.middleearth.resources.datas.rings.RingPolarity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * What a ring has been through so far, filled in step by step as it is forged: refined (its purity, which is also
 * its refining quality), shaped, inscribed, and adorned with a gem. Its metal is the
 * {@link net.sevenstars.middleearth.item.DataComponentTypesME#RING_METAL} component and a set gem's type the
 * {@link net.sevenstars.middleearth.item.DataComponentTypesME#GEM_TYPE} component, so the item model can pick its
 * palettes from them.
 *
 * @param purity      how pure the refined metal is, 0-100; the quality of the refining step
 * @param shaping     the quality of the shaping step, 0-100, once shaped
 * @param inscription the quality of the inscribing step, 0-100, once inscribed
 * @param polarity    from Hallowed (+100) to Twisted (-100), see {@link RingPolarity}
 * @param fea         the Fea (experience levels) poured into the ring so far
 * @param gem         the gem set into it, if any
 */
public record RingForgingDataComponent(int purity, Optional<Integer> shaping, Optional<Integer> inscription,
                                       int polarity, int fea, Optional<Gem> gem) {
    private static final Codec<Integer> PERCENT = Codec.intRange(0, 100);

    public static final Codec<RingForgingDataComponent> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            PERCENT.fieldOf("purity").forGetter(RingForgingDataComponent::purity),
            PERCENT.optionalFieldOf("shaping").forGetter(RingForgingDataComponent::shaping),
            PERCENT.optionalFieldOf("inscription").forGetter(RingForgingDataComponent::inscription),
            Codec.intRange(RingPolarity.MIN, RingPolarity.MAX).optionalFieldOf("polarity", 0).forGetter(RingForgingDataComponent::polarity),
            Codec.intRange(0, Integer.MAX_VALUE).optionalFieldOf("fea", 0).forGetter(RingForgingDataComponent::fea),
            Gem.CODEC.optionalFieldOf("gem").forGetter(RingForgingDataComponent::gem)
    ).apply(instance, RingForgingDataComponent::new));

    public static final PacketCodec<ByteBuf, RingForgingDataComponent> PACKET_CODEC = PacketCodec.tuple(
            PacketCodecs.VAR_INT, RingForgingDataComponent::purity,
            PacketCodecs.optional(PacketCodecs.VAR_INT), RingForgingDataComponent::shaping,
            PacketCodecs.optional(PacketCodecs.VAR_INT), RingForgingDataComponent::inscription,
            PacketCodecs.VAR_INT, RingForgingDataComponent::polarity,
            PacketCodecs.VAR_INT, RingForgingDataComponent::fea,
            PacketCodecs.optional(Gem.PACKET_CODEC), RingForgingDataComponent::gem,
            RingForgingDataComponent::new);

    public RingForgingDataComponent {
        purity = MathHelper.clamp(purity, 0, 100);
        polarity = MathHelper.clamp(polarity, RingPolarity.MIN, RingPolarity.MAX);
        fea = Math.max(0, fea);
    }

    /** A freshly refined ring: only its purity and the polarity its catalysts gave it. */
    public static RingForgingDataComponent refined(int purity, int polarity, int fea) {
        return new RingForgingDataComponent(purity, Optional.empty(), Optional.empty(), polarity, fea, Optional.empty());
    }

    /**
     * The ring's quality, 0-100: the average of every step done so far (refining, shaping, inscribing) and of its
     * gem's quality if it has one.
     */
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

    /** The share (0-100) of the ring's power that goes to its curses rather than its inscriptions: 100 - quality. */
    public int cursePotency() {
        return 100 - quality();
    }

    /** How many of the ring's inscription slots are taken so far (a set gem takes one). */
    public int slotsUsed() {
        return gem.isPresent() ? 1 : 0;
    }

    public RingForgingDataComponent withShaping(int quality, int polarityShift, int feaAdded) {
        return new RingForgingDataComponent(purity, Optional.of(quality), inscription, polarity + polarityShift,
                fea + feaAdded, gem);
    }

    public RingForgingDataComponent withGem(Gem setGem, int polarityShift, int feaAdded) {
        return new RingForgingDataComponent(purity, shaping, inscription, polarity + polarityShift, fea + feaAdded,
                Optional.of(setGem));
    }

    /**
     * A gem set into a ring: its cut and how well it was cut. Its gem type is the ring's
     * {@link net.sevenstars.middleearth.item.DataComponentTypesME#GEM_TYPE} component.
     */
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
