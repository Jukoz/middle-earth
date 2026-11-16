package net.sevenstars.middleearth.recipe.lapidary;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.math.MathHelper;
import net.sevenstars.middleearth.item.DataComponentTypesME;
import net.sevenstars.middleearth.recipe.CraftQuality;
import net.sevenstars.middleearth.item.ResourceItemsME;
import net.sevenstars.middleearth.item.items.gems.CutGemItem;
import net.sevenstars.middleearth.item.items.gems.GemCut;
import net.sevenstars.middleearth.item.items.gems.RawGemItem;
import net.sevenstars.middleearth.resources.datas.gems.GemType;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;

/** Rules of the faceting minigame, shared by server and client. */
public final class Faceting {
    public static final float FACET_SECONDS = 6.0F;

    private static final int[] FACETS_BY_TIER = {3, 4, 5, 6, 8};
    private static final double[] ZONE_MAX_HALF = {0.17, 0.145, 0.13, 0.115, 0.10};
    private static final double[] ZONE_MIN_HALF = {0.17, 0.066, 0.059, 0.052, 0.045};
    private static final double[] WANDER = {0.10, 0.14, 0.18, 0.24, 0.30};
    private static final double[] ZONE_SPEED = {0.16, 0.20, 0.23, 0.25, 0.27};
    private static final double[] GRADE_EASE = {0.0, 0.08, 0.16, 0.25};

    public enum Pattern {
        DRIFT,
        JITTER,
        PULSE
    }

    public record Zone(double center, double halfWidth) {
        public boolean contains(double marker) {
            return Math.abs(marker - center) <= halfWidth;
        }
    }

    private Faceting() {
    }

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

    public static int quality(ItemStack stack) {
        Integer value = stack.get(DataComponentTypesME.GEM_QUALITY);
        return value == null ? 0 : value;
    }

    public static Pattern pattern(GemCut cut, int facet) {
        int indexInTier = cutsOfTier(cut.tier()).indexOf(cut);
        Pattern[] patterns = Pattern.values();
        return patterns[Math.floorMod(facet * (indexInTier + 1) + indexInTier, patterns.length)];
    }

    /** Zone centre and half-width at a time: the cut's sweep pattern plus wander and a breathing width, eased by grade and eyewear. */
    public static Zone zone(GemCut cut, int facet, @Nullable StudyGrade grade, double eyewearEase, double seconds) {
        int tier = cut.tier() - 1;
        double ease = (grade == null ? 0 : GRADE_EASE[grade.ordinal()]) + eyewearEase;
        double maxHalf = ZONE_MAX_HALF[tier] * (1 + ease);
        double minHalf = ZONE_MIN_HALF[tier] * (1 + ease);
        double halfWidth = maxHalf;
        double speed = ZONE_SPEED[tier] * (1 - ease);
        double phase = (cut.ordinal() * 7 + facet * 13) % 23 / 23.0 * Math.PI * 2;
        double amplitude = 0.5 - halfWidth - 0.02;
        double w = speed * Math.PI / (2 * amplitude);
        double sweep = switch (pattern(cut, facet)) {
            case DRIFT -> Math.sin(w * seconds + phase);
            case JITTER -> 0.7 * Math.sin(w * 0.8 * seconds + phase) + 0.3 * Math.sin(w * 2.9 * seconds + phase * 2);
            case PULSE -> Math.sin(w * 0.7 * seconds + phase);
        };
        double wander = 0.6 * Math.sin(w * 1.73 * seconds + phase * 3 + 1.1) + 0.4 * Math.sin(w * 3.37 * seconds + phase * 5 + 2.3);
        double center = 0.5 + amplitude * ((1 - WANDER[tier]) * sweep + WANDER[tier] * wander);
        double breath = pattern(cut, facet) == Pattern.PULSE
            ? 0.5 + 0.5 * Math.sin(w * 1.9 * seconds + phase)
            : 0.5 + 0.5 * (0.6 * Math.sin(w * 1.1 * seconds + phase * 2) + 0.4 * Math.sin(w * 2.3 * seconds + phase * 4 + 0.7));
        halfWidth = minHalf + (maxHalf - minHalf) * MathHelper.clamp(breath, 0, 1);
        return new Zone(MathHelper.clamp(center, halfWidth, 1 - halfWidth), halfWidth);
    }

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
        out.set(DataComponentTypes.MAX_DAMAGE, CraftQuality.of(value).charges(cut.charges()));
        return out;
    }
}
