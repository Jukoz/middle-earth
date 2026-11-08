package net.sevenstars.middleearth.recipe.lapidary;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.math.MathHelper;
import net.sevenstars.middleearth.item.DataComponentTypesME;
import net.sevenstars.middleearth.item.ResourceItemsME;
import net.sevenstars.middleearth.item.items.gems.CutGemItem;
import net.sevenstars.middleearth.item.items.gems.GemCut;
import net.sevenstars.middleearth.item.items.gems.RawGemItem;
import net.sevenstars.middleearth.resources.datas.gems.GemType;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;

/**
 * Faceting, the Lapidary Table's cutting minigame: the rules shared by the server and the client.
 * <p>
 * A cut takes a gem exactly one tier up (a studied raw gem to tier 1, a cut gem to the next tier; tier 5 is
 * final) into a cut the player picks from that tier. The cut's facets are ground one at a time; each facet is a
 * sweet-spot bar: a zone drifts along the bar, changing speed and size, and the player holds to push the grind
 * marker up against its fall to keep it in the zone. A facet scores the share of its time spent in the zone
 * (0-100); the run scores the average, capped by the bench. Higher tiers grind more facets with a narrower,
 * faster zone; the gem's study grade widens and slows the zone (up to 25% for Masterful notes). Each cut has
 * its own order of zone patterns, so cuts in a tier play differently at the same difficulty.
 * <p>
 * The client runs the bar (it must be smooth) and reports each facet's score; the server checks that the facet
 * took its time and keeps everything else.
 */
public final class Faceting {
    /** How long one facet is ground. */
    public static final float FACET_SECONDS = 6.0F;
    /** Best quality value without lapidary tools: Exceptional is the most a bare bench allows. */
    public static final int BARE_BENCH_CAP = GemQuality.EXCEPTIONAL.maxValue();

    private static final int[] FACETS_BY_TIER = {3, 4, 5, 6, 8};
    /** Half the zone's width, as a share of the bar, per tier. */
    private static final double[] ZONE_HALF_WIDTH = {0.17, 0.15, 0.14, 0.135, 0.13};
    /** How fast the zone travels, in bars per second, per tier. */
    private static final double[] ZONE_SPEED = {0.16, 0.20, 0.23, 0.25, 0.27};
    /** How much easier the study grade makes the bar: Rough Notes, Careful, Thorough, Masterful. */
    private static final double[] GRADE_EASE = {0.0, 0.08, 0.16, 0.25};

    /** How the zone moves during a facet. */
    public enum Pattern {
        /** A smooth sweep back and forth. */
        DRIFT,
        /** A sweep with a quicker wobble on top, so it changes direction often. */
        JITTER,
        /** A slower sweep while the zone breathes wider and narrower. */
        PULSE
    }

    /** Where the zone is: its centre and half its width, both as shares of the bar. */
    public record Zone(double center, double halfWidth) {
        public boolean contains(double marker) {
            return Math.abs(marker - center) <= halfWidth;
        }
    }

    private Faceting() {
    }

    // ---- What can be cut ----

    /** The tier a cut would take this gem to, or 0 if it cannot be cut (not a gem, unstudied, or tier 5). */
    public static int nextTier(ItemStack stack) {
        if (!stack.contains(DataComponentTypesME.GEM_TYPE)) {
            return 0;
        }
        if (stack.getItem() instanceof RawGemItem) {
            return stack.contains(DataComponentTypesME.STUDY_GRADE) ? 1 : 0;
        }
        if (stack.getItem() instanceof CutGemItem cutGem) {
            return cutGem.getCut().tier() < 5 ? cutGem.getCut().tier() + 1 : 0;
        }
        return 0;
    }

    public static List<GemCut> cutsOfTier(int tier) {
        return Arrays.stream(GemCut.values()).filter(cut -> cut.tier() == tier).toList();
    }

    public static int facets(int tier) {
        return FACETS_BY_TIER[MathHelper.clamp(tier, 1, 5) - 1];
    }

    /** The gem's hidden quality value; gems never faceted count as 0. */
    public static int quality(ItemStack stack) {
        Integer value = stack.get(DataComponentTypesME.GEM_QUALITY);
        return value == null ? 0 : value;
    }

    // ---- The bar ----

    /** The zone pattern of a facet of a cut: each cut has its own order. */
    public static Pattern pattern(GemCut cut, int facet) {
        int indexInTier = cutsOfTier(cut.tier()).indexOf(cut);
        Pattern[] patterns = Pattern.values();
        return patterns[Math.floorMod(facet * (indexInTier + 1) + indexInTier, patterns.length)];
    }

    /** The zone at {@code seconds} into a facet, for this cut, facet and study grade. */
    public static Zone zone(GemCut cut, int facet, @Nullable StudyGrade grade, double seconds) {
        int tier = cut.tier() - 1;
        double ease = grade == null ? 0 : GRADE_EASE[grade.ordinal()];
        double halfWidth = ZONE_HALF_WIDTH[tier] * (1 + ease);
        double speed = ZONE_SPEED[tier] * (1 - ease);
        // A fixed phase per cut and facet, so each facet starts somewhere different.
        double phase = (cut.ordinal() * 7 + facet * 13) % 23 / 23.0 * Math.PI * 2;
        double amplitude = 0.5 - halfWidth - 0.02;
        // A sine of amplitude A at angular speed w averages 2Aw/pi bars per second.
        double w = speed * Math.PI / (2 * amplitude);
        double center = switch (pattern(cut, facet)) {
            case DRIFT -> 0.5 + amplitude * Math.sin(w * seconds + phase);
            case JITTER -> 0.5 + amplitude * 0.7 * Math.sin(w * 0.8 * seconds + phase)
                + amplitude * 0.3 * Math.sin(w * 2.9 * seconds + phase * 2);
            case PULSE -> 0.5 + amplitude * Math.sin(w * 0.7 * seconds + phase);
        };
        if (pattern(cut, facet) == Pattern.PULSE) {
            halfWidth *= 0.8 + 0.35 * Math.sin(w * 1.9 * seconds + phase);
        }
        return new Zone(MathHelper.clamp(center, halfWidth, 1 - halfWidth), halfWidth);
    }

    // ---- The result ----

    /**
     * The cut gem a finished run makes of {@code gem}: the chosen cut of the same gem type, keeping its study
     * grade. A raw gem takes the run's score as its quality; a re-cut averages it with the old quality. Charges
     * are the new tier's, full, with the quality's bonus.
     */
    public static ItemStack result(ItemStack gem, GemCut cut, int runScore) {
        RegistryKey<GemType> gemType = gem.get(DataComponentTypesME.GEM_TYPE);
        ItemStack out = ResourceItemsME.cutGem(cut, gemType);
        StudyGrade grade = gem.get(DataComponentTypesME.STUDY_GRADE);
        if (grade != null) {
            out.set(DataComponentTypesME.STUDY_GRADE, grade);
        }
        int value = gem.getItem() instanceof CutGemItem
            ? Math.round((quality(gem) + runScore) / 2.0F)
            : runScore;
        value = MathHelper.clamp(value, 0, 100);
        out.set(DataComponentTypesME.GEM_QUALITY, value);
        out.set(DataComponentTypes.MAX_DAMAGE, GemQuality.of(value).charges(cut.charges()));
        return out;
    }
}
