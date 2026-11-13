package net.sevenstars.middleearth.recipe.lapidary;

import net.minecraft.advancement.PlacedAdvancement;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.registries.content.gems.GemTypeRegistry;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * The order of the Lapidary tab's branches, top to bottom: one chain per gem, in the gem types' order, then the lost
 * arts' scrolls (Masterwork, then Relic) at the bottom. The game keeps an advancement's children in no fixed order,
 * so without this the branches would land anywhere, and differently from one launch to the next.
 */
public final class LapidaryAdvancementOrder {
    private static final Identifier ROOT = MiddleEarth.id("lapidary/root");
    private static final String TOOLS = "tools";

    private LapidaryAdvancementOrder() {
    }

    /** {@code children} of {@code parent}, put in order if {@code parent} is the Lapidary root; else as they are. */
    public static Iterable<PlacedAdvancement> children(PlacedAdvancement parent, Iterable<PlacedAdvancement> children) {
        if (!ROOT.equals(parent.getAdvancementEntry().id())) {
            return children;
        }
        List<PlacedAdvancement> sorted = new ArrayList<>();
        children.forEach(sorted::add);
        sorted.sort(Comparator.<PlacedAdvancement>comparingInt(child -> rank(child.getAdvancementEntry().id()))
            .thenComparing(child -> child.getAdvancementEntry().id().toString()));
        return sorted;
    }

    /** A branch's place: the gems in their order, anything else after them, the lost arts last in their own order. */
    private static int rank(Identifier id) {
        String[] parts = id.getPath().split("/");
        int gems = GemTypeRegistry.ALL.size();
        if (parts.length < 3) {
            return gems;
        }
        if (parts[1].equals(TOOLS)) {
            for (LapidaryTools tools : LapidaryTools.values()) {
                if (tools.id().equals(parts[2])) {
                    return gems + 1 + tools.ordinal();
                }
            }
            return gems + 1 + LapidaryTools.values().length;
        }
        for (int i = 0; i < gems; i++) {
            if (GemTypeRegistry.ALL.get(i).getValue().getPath().equals(parts[1])) {
                return i;
            }
        }
        return gems;
    }
}
