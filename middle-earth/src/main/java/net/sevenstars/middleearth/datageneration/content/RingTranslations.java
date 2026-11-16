package net.sevenstars.middleearth.datageneration.content;

import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.resources.datas.rings.RingPolarity;

import java.util.LinkedHashMap;
import java.util.Map;

/** English text for ring forging. */
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
        put("tooltip." + ID + ".ring.theme", "Theme: %s");
        put("tooltip." + ID + ".ring.word", "Inscribed: %s (%s Fëa)");
        put("tooltip." + ID + ".ring.curse", "Curse: %s");

        put("ring_theme." + ID + ".sindarin", "Sindarin");
        put("ring_theme." + ID + ".khuzdul", "Khuzdul");
        put("ring_theme." + ID + ".adunaic", "Adûnaic");
        put("ring_theme." + ID + ".black_speech", "Black Speech");

        put("ring_word." + ID + ".nourishment", "Nourishment");
        put("ring_word." + ID + ".mortality", "Mortality");
        put("ring_word." + ID + ".spirit", "Spirit");
        put("ring_word." + ID + ".sight", "Sight");
        put("ring_word." + ID + ".command", "Command");
        put("ring_word." + ID + ".courage", "Courage");
        put("ring_word." + ID + ".presence", "Presence");
        put("ring_word." + ID + ".might", "Might");
        put("ring_word." + ID + ".wisdom", "Wisdom");
        put("ring_word." + ID + ".luck", "Luck");
        put("ring_word." + ID + ".flame", "Flame");
        put("ring_word." + ID + ".water", "Water");
        put("ring_word." + ID + ".sky", "Sky");
        put("ring_word." + ID + ".succor", "Succor");
        put("ring_word." + ID + ".earth", "Earth");
        put("ring_word." + ID + ".endurance", "Endurance");
        put("ring_word." + ID + ".swiftness", "Swiftness");
        put("ring_word." + ID + ".kinship", "Kinship");
        put("ring_word." + ID + ".preservation", "Preservation");
        put("ring_word." + ID + ".nature", "Nature");

        put("ring_curse." + ID + ".sleepless", "Sleepless");
        put("ring_curse." + ID + ".gnawing_hunger", "Gnawing Hunger");
        put("ring_curse." + ID + ".heavy_tread", "Heavy Tread");
        put("ring_curse." + ID + ".tarnished_luck", "Tarnished Luck");
        put("ring_curse." + ID + ".brittle_touch", "Brittle Touch");
        put("ring_curse." + ID + ".murmuring", "Murmuring");
        put("ring_curse." + ID + ".cold_blood", "Cold Blood");
        put("ring_curse." + ID + ".beast_shunned", "Beast-Shunned");
        put("ring_curse." + ID + ".unsteady_hand", "Unsteady Hand");
        put("ring_curse." + ID + ".marked", "Marked");
        put("ring_curse." + ID + ".binding", "Binding");
        put("ring_curse." + ID + ".withering", "Withering");
        put("ring_curse." + ID + ".fading", "Fading");
        put("ring_curse." + ID + ".sunbane", "Sunbane");
        put("ring_curse." + ID + ".fea_leak", "Fëa Leak");
        put("ring_curse." + ID + ".beacon", "Beacon");
        put("ring_curse." + ID + ".faithless", "Faithless");
        put("ring_curse." + ID + ".treacherous", "Treacherous");
        put("ring_curse." + ID + ".grasping_shadow", "Grasping Shadow");
        put("ring_curse." + ID + ".stretched_thin", "Stretched Thin");
    }
}
