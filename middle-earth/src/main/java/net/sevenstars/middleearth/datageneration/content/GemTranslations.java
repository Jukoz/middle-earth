package net.sevenstars.middleearth.datageneration.content;

import net.sevenstars.middleearth.MiddleEarth;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * English text for the gem rework (gem types, aspects, cuts, gem tooltips), added by the LanguageProvider.
 * Item and block names come from their ids like every other Middle-earth entry.
 */
public class GemTranslations {
    private static final String ID = MiddleEarth.MOD_ID;

    public static final Map<String, String> ENTRIES = new LinkedHashMap<>();

    private static void put(String key, String value) {
        ENTRIES.put(key, value);
    }

    static {
        for (String gem : new String[]{"adamant", "ruby", "sapphire", "beryl", "hadorite", "opal", "onyx", "moonstone"}) {
            put("gem_type." + ID + "." + gem, Character.toUpperCase(gem.charAt(0)) + gem.substring(1));
        }
        put("gem_aspect." + ID + ".attack", "Attack");
        put("gem_aspect." + ID + ".defense", "Defense");
        put("gem_aspect." + ID + ".utility", "Utility");
        put("gem_aspect." + ID + ".speed", "Speed");
        put("gem_aspect." + ID + ".men", "Edain");
        put("gem_aspect." + ID + ".dwarves", "Dwarven");
        put("gem_aspect." + ID + ".elves", "Elven");
        put("gem_aspect." + ID + ".morgul", "Morgul");

        put("block." + ID + ".gem_ore", "Gem Ore");
        put("tooltip." + ID + ".gem_aspect", "Aspect: %s");
        put("tooltip." + ID + ".raw_gem.tier", "Uncut (counts as tier 1)");
        put("item." + ID + ".cut_gem.name", "%s Cut %s");
        put("item." + ID + ".cut_gem.no_type", "Gem");
        put("tooltip." + ID + ".cut_gem.tier", "Tier %s");
        put("tooltip." + ID + ".cut_gem.charges", "Charges: %s / %s");

        put("gem_cut." + ID + ".round", "Round");
        put("gem_cut." + ID + ".sphere", "Sphere");
        put("gem_cut." + ID + ".oval", "Oval");
        put("gem_cut." + ID + ".hexagon", "Hexagon");
        put("gem_cut." + ID + ".octagon", "Octagon");
        put("gem_cut." + ID + ".pentagon", "Pentagon");
        put("gem_cut." + ID + ".rhombus", "Rhombus");
        put("gem_cut." + ID + ".pear", "Pear");
        put("gem_cut." + ID + ".tapered", "Tapered");
        put("gem_cut." + ID + ".pointed_lozenge", "Pointed Lozenge");
        put("gem_cut." + ID + ".emerald", "Emerald");
        put("gem_cut." + ID + ".asscher", "Asscher");
        put("gem_cut." + ID + ".marquis", "Marquis");
        put("gem_cut." + ID + ".brilliant", "Brilliant");
        put("gem_cut." + ID + ".radiant", "Radiant");
        put("gem_cut." + ID + ".briolette", "Briolette");
    }

    // Lapidary Table, gem studies, Gem Studies chapter and Lapidary advancements (generated from the playground).
    static {
        put("study_grade.middle-earth.rough_notes", "Rough Notes");
        put("study_grade.middle-earth.careful", "Careful");
        put("study_grade.middle-earth.thorough", "Thorough");
        put("study_grade.middle-earth.masterful", "Masterful");
        put("tooltip.middle-earth.raw_gem.studied", "Studied: %s");
        put("tooltip.middle-earth.raw_gem.unstudied", "Unstudied");
        put("tooltip.middle-earth.gem_info.hold_shift", "Hold Shift for details");
        put("tooltip.middle-earth.gem_info.unknown", "You know nothing of %s yet");
        put("advancement.middle-earth.lapidary.root.title", "Lapidary");
        put("advancement.middle-earth.lapidary.root.description", "Study raw gems at a Lapidary Table to learn their secrets");
        put("advancement.middle-earth.lapidary.grade_title", "%s: %s");
        put("advancement.middle-earth.lapidary.grade.rough_notes", "Rough Notes");
        put("advancement.middle-earth.lapidary.grade.careful", "Careful Notes");
        put("advancement.middle-earth.lapidary.grade.thorough", "Thorough Notes");
        put("advancement.middle-earth.lapidary.grade.masterful", "Masterful Notes");
        put("advancement.middle-earth.lapidary.mastered", "You have mastered the study of %s");
        put("gem_info.middle-earth.adamant.host", "Host rock: Magma");
        put("gem_info.middle-earth.adamant.depth", "Depth: Near the uttermost foundations of stone, where the rock runs hot.");
        put("gem_info.middle-earth.adamant.region", "Region: All of Middle-earth");
        put("gem_info.middle-earth.adamant.lore", "Nenya, the Ring of Water, was set with adamant. Galadriel kept it in Lothlórien.");
        put("gem_info.middle-earth.ruby.host", "Host rock: Silver ore");
        put("gem_info.middle-earth.ruby.depth", "Depth: In the Núrgon and Medgon, far under the living earth.");
        put("gem_info.middle-earth.ruby.region", "Region: All of Middle-earth");
        put("gem_info.middle-earth.ruby.lore", "Narya, the Ring of Fire, was set with a ruby. Círdan gave it to Gandalf.");
        put("gem_info.middle-earth.sapphire.host", "Host rock: Iron ore");
        put("gem_info.middle-earth.sapphire.depth", "Depth: In the Núrgon, where the roots of the mountains begin.");
        put("gem_info.middle-earth.sapphire.region", "Region: All of Middle-earth");
        put("gem_info.middle-earth.sapphire.lore", "Vilya, the Ring of Air and mightiest of the Three, was set with a sapphire. Elrond kept it.");
        put("gem_info.middle-earth.beryl.host", "Host rock: Gold ore");
        put("gem_info.middle-earth.beryl.depth", "Depth: In the Núrgon and Medgon, down among the roots of the mountains.");
        put("gem_info.middle-earth.beryl.region", "Region: All of Middle-earth");
        put("gem_info.middle-earth.hadorite.host", "Host rock: Lead ore");
        put("gem_info.middle-earth.hadorite.depth", "Depth: In the upper Núrgon, within the first of the deep delvings.");
        put("gem_info.middle-earth.hadorite.region", "Region: The lands of Men: Gondor, Rohan, Dale, the Vales of Anduin, Rhovanion and old Arnor");
        put("gem_info.middle-earth.opal.host", "Host rock: Iron ore");
        put("gem_info.middle-earth.opal.depth", "Depth: Far down in the Medgon, in the deepest delvings of the Dwarves.");
        put("gem_info.middle-earth.opal.region", "Region: The Dwarven mountains: Erebor, the Iron Hills, the Blue Mountains, the Grey Mountains and the Misty Mountains above Moria");
        put("gem_info.middle-earth.onyx.host", "Host rock: Coal ore");
        put("gem_info.middle-earth.onyx.depth", "Depth: In shallow coal seams, not far beneath the turf.");
        put("gem_info.middle-earth.onyx.region", "Region: Mordor and Nurn, Dol Guldur, Gundabad and Angmar");
        put("gem_info.middle-earth.moonstone.host", "Host rock: Tin ore");
        put("gem_info.middle-earth.moonstone.depth", "Depth: In tin veins under the soil, almost within reach of daylight.");
        put("gem_info.middle-earth.moonstone.region", "Region: The Elven realms: Lothlórien, the Woodland Realm, Lindon and Eregion");
        put("container.middle-earth.lapidary_table", "Lapidary Table");
        put("message.middle-earth.lapidary_table.in_use", "%s is using the Lapidary.");
        put("gui.middle-earth.lapidary.study", "Study");
        put("gui.middle-earth.lapidary.study_again", "Study Again");
        put("gui.middle-earth.lapidary.cost", "%s levels");
        put("gui.middle-earth.lapidary.place_gem", "Place a raw gem to study it");
        put("gui.middle-earth.lapidary.xp_cost", "XP Cost: %s Levels");
        put("gui.middle-earth.lapidary.xp_cost_one", "XP Cost: %s Level");
        put("gui.middle-earth.lapidary.runes", "Runes %s/%s");
        put("gui.middle-earth.lapidary.moves", "Moves %s");
        put("gui.middle-earth.lapidary.turn_left", "Turn gem left");
        put("gui.middle-earth.lapidary.mode.reading", "Refraction Reading");
        put("gui.middle-earth.lapidary.mode.faceting", "Faceting");
        put("gem_quality.middle-earth.mundane", "Mundane");
        put("gem_quality.middle-earth.fine", "Fine");
        put("gem_quality.middle-earth.exceptional", "Exceptional");
        put("gem_quality.middle-earth.masterwork", "Masterwork");
        put("gem_quality.middle-earth.relic", "Relic");
        put("tooltip.middle-earth.cut_gem.quality", "Quality: %s");
        put("tooltip.middle-earth.cut_gem.quality_value", "Quality value: %s%%");
        put("gui.middle-earth.lapidary.faceting.place_gem", "Place a studied raw gem or a cut gem to facet it.");
        put("gui.middle-earth.lapidary.faceting.unstudied", "Study this gem with Refraction Reading before cutting it.");
        put("gui.middle-earth.lapidary.faceting.final", "A tier 5 gem cannot be cut any further.");
        put("gui.middle-earth.lapidary.faceting.choose", "Pick a tier %s cut");
        put("gui.middle-earth.lapidary.faceting.cut", "Cut");
        put("gui.middle-earth.lapidary.faceting.grind", "Grind Facet");
        put("gui.middle-earth.lapidary.faceting.facets", "%s facets");
        put("gui.middle-earth.lapidary.faceting.water", "Needs water");
        put("gui.middle-earth.lapidary.faceting.facet", "Facet %s of %s");
        put("gui.middle-earth.lapidary.faceting.ready", "Ready...");
        put("gui.middle-earth.lapidary.faceting.hold", "Hold mouse or Space; keep the line in the green.");
        put("gui.middle-earth.lapidary.faceting.score", "Average: %s%%");
        put("gui.middle-earth.lapidary.faceting.cap", "Bench limit:");
        put("gui.middle-earth.lapidary.faceting.done", "Cut as %s");
        put("gui.middle-earth.lapidary.turn_right", "Turn gem right");
        put("gui.middle-earth.lapidary.help.1", "Refraction Reading: light every glowing rune on the rim.");
        put("gui.middle-earth.lapidary.help.2", "Drag the candle round its groove, or nudge it with Left / Right.");
        put("gui.middle-earth.lapidary.help.3", "Turn the gem: drag it, scroll, press A / D or use the arrows.");
        put("gui.middle-earth.lapidary.help.4", "Click a rune while the light is on it to lock it.");
        put("item.middle-earth.gem_notes.any_gem", "Gem");
        put("item.middle-earth.rough_notes.gem", "Rough %s Notes");
        put("item.middle-earth.careful_notes.gem", "Careful %s Notes");
        put("item.middle-earth.thorough_notes.gem", "Thorough %s Notes");
        put("item.middle-earth.masterful_notes.gem", "Masterful %s Notes");
        put("tooltip.middle-earth.gem_notes.bind", "Bind into a Player Book at a crafting table");
        put("gui.middle-earth.lapidary.paper", "1 paper");
        put("gui.middle-earth.lapidary.take_notes", "Take the notes");
        put("gui.middle-earth.lapidary.needs_paper", "Needs paper");
        put("screen.middle-earth.gem_studies", "Gem Studies");
        put("screen.middle-earth.gem_studies.notes", "Notes: %s");
        put("screen.middle-earth.gem_studies.none", "No notes on this gem yet. Study a raw one at a Lapidary Table and bind the notes into this book.");
        put("screen.middle-earth.gem_studies.no_book", "Hold your Player Book to read its Gem Studies.");
        put("screen.middle-earth.gem_studies.pick", "Choose a gem to read its notes.");
        put("gui.middle-earth.lapidary.help.5", "Each candle drag and each gem step is a move: fewer moves, better grade.");
        put("tooltip.middle-earth.gem_notes.covers.rough_notes", "Covers: host rock");
        put("tooltip.middle-earth.gem_notes.covers.careful", "Covers: host rock, depth");
        put("tooltip.middle-earth.gem_notes.covers.thorough", "Covers: host rock, depth, region");
        put("tooltip.middle-earth.gem_notes.covers.masterful", "Covers: host rock, depth, region, lore");
        put("gui.middle-earth.lapidary.help.6", "Each study uses 1 paper and writes up notes for your Player Book.");
        put("gui.middle-earth.lapidary.aim", "≤ %s moves");
        put("gui.middle-earth.lapidary.studied", "Studied:");
        put("lapidary_tools.middle-earth.masterwork", "Masterwork Lapidary Upgrades");
        put("lapidary_tools.middle-earth.relic", "Relic Lapidary Upgrades");
        put("tooltip.middle-earth.eyewear_lenses.refined", "Refined Lenses: faceting 2% easier");
        put("tooltip.middle-earth.eyewear_lenses.exquisite", "Exquisite Lenses: faceting 4% easier");
        put("message.middle-earth.lapidary_table.basin_full", "The water is full");
        put("gui.middle-earth.lapidary.faceting.basin", "Water: %s / %s buckets");
        put("gui.middle-earth.lapidary.faceting.basin.fill", "Use a water bucket on the table to fill it");
        put("message.middle-earth.lapidary_table.cutting", "Not while a gem is being cut");
        put("message.middle-earth.lapidary_table.has_upgrade", "The table already has one as good or better");
        put("tooltip.middle-earth.lapidary_scroll.teaches", "Teaches: %s");
        put("tooltip.middle-earth.lapidary_scroll.read", "Use to read; bind into your Player Book to keep");
        put("message.middle-earth.lapidary_scroll.learned", "You have learned the lost art of the %s");
        put("message.middle-earth.lapidary_scroll.known", "You already know the art of the %s");
        put("screen.middle-earth.gem_studies.arts", "Lapidary Arts");
        put("screen.middle-earth.gem_studies.arts.parts", "Parts:");
        put("screen.middle-earth.gem_studies.arts.missing", "This page is missing. Bind the lost writing on this art into your Player Book to keep its page here.");
        put("screen.middle-earth.gem_studies.arts.masterwork_lapidary_tools", "Set the lenses, wheel, lamp and pump on a Lapidary Table (sneak and use them on it). Each raises the best cut a little; with all four, Masterwork or better, cuts can reach Masterwork.");
        put("screen.middle-earth.gem_studies.arts.relic_lapidary_tools", "Each Relic upgrade raises a Masterwork table's best cut a little more; with all four Relic, cuts can reach Relic.");
        put("screen.middle-earth.gem_studies.arts.refined_focusing_lenses", "Gem glass in gold. Also fits Glasses or a Dwarven Monocle.");
        put("screen.middle-earth.gem_studies.arts.enhanced_grinding_wheel", "Nurgon bound in khazad steel.");
        put("screen.middle-earth.gem_studies.arts.citrine_bullseye_lamp", "A candle in gold, shining through citrine.");
        put("screen.middle-earth.gem_studies.arts.standard_pump", "A foot bellows under the table, drawing from a bucket of 5 water buckets: fill it with a water bucket.");
        put("screen.middle-earth.gem_studies.arts.exquisite_focusing_lenses", "Gem glass in mithril. Also fits Glasses or a Dwarven Monocle.");
        put("screen.middle-earth.gem_studies.arts.khazad_grinding_wheel", "Medgon and obsidian bound in mithril.");
        put("screen.middle-earth.gem_studies.arts.mirdain_beam_lamp", "Candlelight gathered through adamant.");
        put("screen.middle-earth.gem_studies.arts.numenorean_pump", "A Standard Pump refitted in mithril, drawing from a basin of 10 water buckets.");
        put("advancement.middle-earth.lapidary.tools.masterwork", "Bright as Starlight in a Sunless Hall");
        put("advancement.middle-earth.lapidary.tools.masterwork.description", "Read the Lost Master's Study of Gems");
        put("advancement.middle-earth.lapidary.tools.relic", "Of the Gwaith-i-Mirdain");
        put("advancement.middle-earth.lapidary.tools.relic.description", "Read the Ancient Eregion Gemcraft Folio");
        // The focused lamps as blocks, and as off-hand lamps.
        put("block.middle-earth.citrine_bullseye_lamp", "Citrine Bullseye Lamp");
        put("block.middle-earth.mirdain_beam_lamp", "Mirdain Beam Lamp");
        put("block.middle-earth.beam_light", "Beam Light");
        // Lapidary arts item names: from their ids, with a few overrides.
        java.util.Map<String, String> lapidaryNames = java.util.Map.of("numenorean_pump", "Númenórean Pump", "masterwork_lapidary_scroll", "Lost Master's Study of Gems", "relic_lapidary_scroll", "Ancient Eregion Gemcraft Folio");
        for (net.minecraft.item.Item item : net.sevenstars.middleearth.item.ResourceItemsME.LAPIDARY_ITEMS) {
            String path = net.minecraft.registry.Registries.ITEM.getId(item).getPath();
            String name = lapidaryNames.get(path);
            if (name == null) {
                StringBuilder words = new StringBuilder();
                for (String word : path.split("_")) {
                    words.append(words.isEmpty() ? "" : " ").append(Character.toUpperCase(word.charAt(0))).append(word.substring(1));
                }
                name = words.toString();
            }
            put(item.getTranslationKey(), name);
        }
    }
}
