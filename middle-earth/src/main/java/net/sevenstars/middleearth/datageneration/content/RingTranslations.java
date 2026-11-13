package net.sevenstars.middleearth.datageneration.content;

import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.resources.datas.rings.RingPolarity;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * English text for ring forging (ring metals, polarity bands, ring tooltips), added by the LanguageProvider.
 * The ring item's own name ("Ring") comes from its id like every other Middle-earth entry.
 */
public class RingTranslations {
    private static final String ID = MiddleEarth.MOD_ID;

    public static final Map<String, String> ENTRIES = new LinkedHashMap<>();

    private static void put(String key, String value) {
        ENTRIES.put(key, value);
    }

    static {
        put("ring_metal." + ID + ".copper", "Copper");
        put("ring_metal." + ID + ".iron", "Iron");
        put("ring_metal." + ID + ".silver", "Silver");
        put("ring_metal." + ID + ".gold", "Gold");
        put("ring_metal." + ID + ".mithril", "Mithril");

        put(RingPolarity.HALLOWED.translationKey(), "Hallowed");
        put(RingPolarity.PRESERVING.translationKey(), "Preserving");
        put(RingPolarity.STEADFAST.translationKey(), "Steadfast");
        put(RingPolarity.UNCLOUDED.translationKey(), "Unclouded");
        put(RingPolarity.UNBOUND.translationKey(), "Unbound");
        put(RingPolarity.WEARY.translationKey(), "Weary");
        put(RingPolarity.ENSNARING.translationKey(), "Ensnaring");
        put(RingPolarity.WRAITH_BINDING.translationKey(), "Wraith-Binding");
        put(RingPolarity.DOOM_WROUGHT.translationKey(), "Doom-Wrought");

        put("item." + ID + ".ring.metal", "%s Ring");
        put("tooltip." + ID + ".ring.quality", "Quality: %s");
        put("tooltip." + ID + ".ring.purity", "Purity: %s");
        put("tooltip." + ID + ".ring.polarity", "Polarity: %s %s");
        put("tooltip." + ID + ".ring.slots", "Slots: %s");
        put("tooltip." + ID + ".ring.gem", "Gem: %s");
        put("tooltip." + ID + ".ring.fea", "Fëa: %s");
        put("tooltip." + ID + ".ring.unfinished", "Unfinished: fill every slot to wear it");
    }
}
