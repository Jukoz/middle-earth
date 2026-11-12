package net.sevenstars.middleearth.recipe.lapidary;

import net.minecraft.item.Item;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.item.ResourceItemsME;
import org.jetbrains.annotations.Nullable;

/**
 * The two tiers of lapidary upgrades. A Lapidary Table takes four upgrades: lenses, a grinding wheel and a lamp
 * set on the table, and a running pump beside it. A bare table cuts to {@link #BARE_CAP}; each upgrade raises
 * that a little, and a full set of four opens the next quality:
 * <ul>
 *     <li>every slot Masterwork or better: Masterwork, {@link #MASTERWORK_CAP} plus a little for each Relic upgrade;</li>
 *     <li>every slot Relic: Relic, up to 100.</li>
 * </ul>
 * Each tier's upgrades are learned from its own scroll (lost knowledge).
 */
public enum LapidaryTools {
    MASTERWORK("masterwork", GemQuality.MASTERWORK),
    RELIC("relic", GemQuality.RELIC);

    /** The best quality value a bare table cuts to. */
    public static final int BARE_CAP = 71;
    /** What each upgrade adds before the set is complete. */
    public static final int UPGRADE_STEP = 2;
    /** A full set of Masterwork-or-better upgrades. */
    public static final int MASTERWORK_CAP = 85;
    /** Each Relic upgrade in a set that is not all Relic adds this much, in halves. */
    public static final int RELIC_HALF_STEPS = 3;

    /** The four upgrades, in the order an empty hand takes them off the table (the pump is never on it). */
    public enum Slot {
        LENSES, WHEEL, LAMP, PUMP
    }

    private final String name;
    private final GemQuality limit;

    LapidaryTools(String name, GemQuality limit) {
        this.name = name;
        this.limit = limit;
    }

    public String id() {
        return name;
    }

    /** The best quality a full set of this tier opens. */
    public GemQuality limit() {
        return limit;
    }

    /** This tier's upgrade for {@code slot}. */
    public Item upgrade(Slot slot) {
        boolean relic = this == RELIC;
        return switch (slot) {
            case LENSES -> relic ? ResourceItemsME.EXQUISITE_FOCUSING_LENSES : ResourceItemsME.REFINED_FOCUSING_LENSES;
            case WHEEL -> relic ? ResourceItemsME.KHAZAD_GRINDING_WHEEL : ResourceItemsME.ENHANCED_GRINDING_WHEEL;
            case LAMP -> relic ? ResourceItemsME.MIRDAIN_BEAM_LAMP : ResourceItemsME.CITRINE_BULLSEYE_LAMP;
            case PUMP -> relic ? ResourceItemsME.NUMENOREAN_PUMP : ResourceItemsME.STANDARD_PUMP;
        };
    }

    /** The tier of an upgrade item, or null for anything else. */
    public static @Nullable LapidaryTools tierOf(Item item) {
        for (LapidaryTools tier : values()) {
            for (Slot slot : Slot.values()) {
                if (tier.upgrade(slot) == item) {
                    return tier;
                }
            }
        }
        return null;
    }

    /** Which slot an upgrade item fills, or null for anything else. */
    public static @Nullable Slot slotOf(Item item) {
        for (LapidaryTools tier : values()) {
            for (Slot slot : Slot.values()) {
                if (tier.upgrade(slot) == item) {
                    return slot;
                }
            }
        }
        return null;
    }

    /** The tier a table reaches with these upgrades (one per {@link Slot}, null for none): null below a full set. */
    public static @Nullable LapidaryTools reached(@Nullable LapidaryTools[] upgrades) {
        LapidaryTools reached = RELIC;
        for (LapidaryTools tier : upgrades) {
            if (tier == null) {
                return null;
            }
            if (tier == MASTERWORK) {
                reached = MASTERWORK;
            }
        }
        return reached;
    }

    /** The highest quality value a cut can reach with these upgrades (one per {@link Slot}, null for none). */
    public static int cap(@Nullable LapidaryTools[] upgrades) {
        LapidaryTools reached = reached(upgrades);
        if (reached == RELIC) {
            return 100;
        }
        int count = 0;
        int relics = 0;
        for (LapidaryTools tier : upgrades) {
            count += tier != null ? 1 : 0;
            relics += tier == RELIC ? 1 : 0;
        }
        return reached == MASTERWORK ? MASTERWORK_CAP + relics * RELIC_HALF_STEPS / 2 : BARE_CAP + count * UPGRADE_STEP;
    }

    public String translationKey() {
        return "lapidary_tools." + MiddleEarth.MOD_ID + "." + name;
    }
}
