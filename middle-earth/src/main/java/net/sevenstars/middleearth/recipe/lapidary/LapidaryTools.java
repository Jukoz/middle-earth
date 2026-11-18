package net.sevenstars.middleearth.recipe.lapidary;

import net.minecraft.item.Item;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.item.ResourceItemsME;
import net.sevenstars.middleearth.recipe.CraftQuality;
import org.jetbrains.annotations.Nullable;

/** Lapidary table upgrade tiers and the quality caps they unlock. */
public enum LapidaryTools {
    MASTERWORK("masterwork", CraftQuality.MASTERWORK),
    RELIC("relic", CraftQuality.RELIC);

    public static final int BARE_CAP = 71;
    public static final int UPGRADE_STEP = 2;
    public static final int MASTERWORK_CAP = 85;
    public static final int RELIC_HALF_STEPS = 3;

    public enum Slot {
        LENSES, WHEEL, LAMP, PUMP
    }

    private final String name;
    private final CraftQuality limit;

    LapidaryTools(String name, CraftQuality limit) {
        this.name = name;
        this.limit = limit;
    }

    public String id() {
        return name;
    }

    public CraftQuality limit() {
        return limit;
    }

    public Item upgrade(Slot slot) {
        boolean relic = this == RELIC;
        return switch (slot) {
            case LENSES -> relic ? ResourceItemsME.EXQUISITE_FOCUSING_LENSES : ResourceItemsME.REFINED_FOCUSING_LENSES;
            case WHEEL -> relic ? ResourceItemsME.KHAZAD_GRINDING_WHEEL : ResourceItemsME.ENHANCED_GRINDING_WHEEL;
            case LAMP -> relic ? ResourceItemsME.MIRDAIN_BEAM_LAMP : ResourceItemsME.CITRINE_BULLSEYE_LAMP;
            case PUMP -> relic ? ResourceItemsME.NUMENOREAN_PUMP : ResourceItemsME.STANDARD_PUMP;
        };
    }

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

    /** The tier every one of the four upgrade slots meets. */
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

    /** Quality cap: bare cap plus a step per upgrade; a full Masterwork set adds half-steps per Relic piece; full Relic is 100. */
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
