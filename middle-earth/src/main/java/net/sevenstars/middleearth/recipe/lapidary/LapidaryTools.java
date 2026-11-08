package net.sevenstars.middleearth.recipe.lapidary;

import net.minecraft.item.ItemStack;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.item.items.gems.LapidaryToolsItem;
import org.jetbrains.annotations.Nullable;

/**
 * The tool sets installed in a Lapidary Table's tools slot. A bare bench cuts Exceptional at best; each tool set
 * raises the best quality a cut can reach. Each set is learned from its own scroll (lost knowledge).
 */
public enum LapidaryTools {
    MASTERWORK("masterwork", GemQuality.MASTERWORK),
    RELIC("relic", GemQuality.RELIC);

    private final String name;
    private final GemQuality limit;

    LapidaryTools(String name, GemQuality limit) {
        this.name = name;
        this.limit = limit;
    }

    public String id() {
        return name;
    }

    /** The best quality these tools allow. */
    public GemQuality limit() {
        return limit;
    }

    /** The highest quality value a cut can reach with these tools. */
    public int cap() {
        return limit.maxValue();
    }

    /** The tools in a stack, or null for anything else. */
    public static @Nullable LapidaryTools of(ItemStack stack) {
        return stack.getItem() instanceof LapidaryToolsItem tools ? tools.tools() : null;
    }

    /** The highest quality value a cut can reach with {@code tools} in the tools slot (empty: the bare bench). */
    public static int cap(ItemStack tools) {
        LapidaryTools installed = of(tools);
        return installed == null ? Faceting.BARE_BENCH_CAP : installed.cap();
    }

    public String translationKey() {
        return "lapidary_tools." + MiddleEarth.MOD_ID + "." + name;
    }
}
