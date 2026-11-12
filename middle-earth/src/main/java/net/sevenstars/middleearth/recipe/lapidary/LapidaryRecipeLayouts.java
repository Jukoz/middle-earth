package net.sevenstars.middleearth.recipe.lapidary;

import java.util.List;
import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.item.equipment.trim.ArmorTrim;
import net.minecraft.item.equipment.trim.ArmorTrimMaterial;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.item.utils.SmithingTrimPatternsME;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.registry.tag.TagKey;
import net.sevenstars.middleearth.block.registration.BlockRegistryME;
import net.sevenstars.middleearth.block.registration.DecorativeBlockRegistryME;
import net.sevenstars.middleearth.block.registration.StoneBlockSetRegistryME;
import net.sevenstars.middleearth.item.ResourceItemsME;

/**
 * The shapes of the lapidary recipes, in one place: the recipe datagen builds the recipes from them and the Player
 * Book draws its crafting diagrams from them, so the two never disagree.
 */
public final class LapidaryRecipeLayouts {
    /** What goes in one square of a recipe. */
    public sealed interface Part permits OfItem, OfTag, Metal {
    }

    public record OfItem(Item item) implements Part {
    }

    public record OfTag(TagKey<Item> tag) implements Part {
    }

    /** A generic rod or plate of one metal: the metal is the item's trim material. */
    public record Metal(Item shape, String metal) implements Part {
    }

    /** One shaped recipe: its art ("masterwork" or "relic"), what it makes and how. */
    public record Layout(String art, Item output, int count, List<String> pattern, Map<Character, Part> key) {
    }

    private LapidaryRecipeLayouts() {
    }

    /** The trim that makes a generic rod or plate of {@code metal}. */
    public static ArmorTrim trim(RegistryWrapper.WrapperLookup registries, String metal) {
        boolean vanilla = metal.equals("iron") || metal.equals("gold") || metal.equals("copper");
        RegistryKey<ArmorTrimMaterial> material = RegistryKey.of(RegistryKeys.TRIM_MATERIAL,
            vanilla ? Identifier.ofVanilla(metal) : Identifier.of(MiddleEarth.MOD_ID, metal));
        return new ArmorTrim(registries.getOrThrow(RegistryKeys.TRIM_MATERIAL).getOrThrow(material),
            registries.getOrThrow(RegistryKeys.TRIM_PATTERN).getOrThrow(SmithingTrimPatternsME.SMITHING_PART));
    }

    private static Part item(ItemConvertible item) {
        return new OfItem(item.asItem());
    }

    private static Part metal(Item shape, String metal) {
        return new Metal(shape, metal);
    }

    private static List<Layout> masterworkGlass() {
        return List.of(
            gemGlass(LapidaryTools.MASTERWORK, ResourceItemsME.CITRINE_GLASS, BlockRegistryME.CITRINE_BLOCK),
            gemGlass(LapidaryTools.MASTERWORK, ResourceItemsME.AMETHYST_GLASS, Items.AMETHYST_BLOCK),
            gemGlass(LapidaryTools.MASTERWORK, ResourceItemsME.RED_AGATE_GLASS, BlockRegistryME.RED_AGATE_BLOCK),
            gemGlass(LapidaryTools.MASTERWORK, ResourceItemsME.QUARTZ_GLASS, Items.QUARTZ_BLOCK));
    }

    private static List<Layout> relicGlass() {
        return List.of(
            gemGlass(LapidaryTools.RELIC, ResourceItemsME.ADAMANT_GLASS, BlockRegistryME.ADAMANT_BLOCK),
            gemGlass(LapidaryTools.RELIC, ResourceItemsME.RUBY_GLASS, BlockRegistryME.RUBY_BLOCK),
            gemGlass(LapidaryTools.RELIC, ResourceItemsME.SAPPHIRE_GLASS, BlockRegistryME.SAPPHIRE_BLOCK),
            gemGlass(LapidaryTools.RELIC, ResourceItemsME.BERYL_GLASS, BlockRegistryME.BERYL_BLOCK));
    }

    /** Gem glass: the crystal block among glowstone dust, two pieces. */
    private static Layout gemGlass(LapidaryTools tools, Item glass, ItemConvertible crystal) {
        return new Layout(tools.id(), glass, 2, List.of(" G ", "GBG", " G "),
            Map.of('G', item(Items.GLOWSTONE_DUST), 'B', item(crystal)));
    }

    /** Focusing lenses: the four gem glasses held in loops bent from three rods of the metal. */
    private static Layout lenses(LapidaryTools tools, Item lenses, String metal, List<Layout> glass) {
        return new Layout(tools.id(), lenses, 1, List.of("ARB", " R ", "CRD"), Map.of(
            'A', item(glass.get(0).output()), 'B', item(glass.get(1).output()),
            'C', item(glass.get(2).output()), 'D', item(glass.get(3).output()),
            'R', metal(ResourceItemsME.ROD, metal)));
    }

    /** The gem glass of a tool set's lenses, in the lenses' order. */
    public static List<Layout> gemGlass(LapidaryTools tools) {
        return tools == LapidaryTools.MASTERWORK ? masterworkGlass() : relicGlass();
    }

    /** Every lapidary recipe of a tier: its gem glass and its four upgrades. */
    public static List<Layout> all(LapidaryTools tools) {
        return tools == LapidaryTools.MASTERWORK ? masterwork() : relic();
    }

    /** The recipe that makes {@code output}, or null. */
    public static Layout of(Item output) {
        for (LapidaryTools tools : LapidaryTools.values()) {
            for (Layout layout : all(tools)) {
                if (layout.output() == output) {
                    return layout;
                }
            }
        }
        return null;
    }

    private static List<Layout> masterwork() {
        String art = LapidaryTools.MASTERWORK.id();
        Block nurgon = StoneBlockSetRegistryME.NURGON_SET.smoothBlocks.base();
        List<Layout> glass = masterworkGlass();
        return List.of(
            glass.get(0), glass.get(1), glass.get(2), glass.get(3),
            lenses(LapidaryTools.MASTERWORK, ResourceItemsME.REFINED_FOCUSING_LENSES, "gold", glass),
            new Layout(art, ResourceItemsME.ENHANCED_GRINDING_WHEEL, 1, List.of("NSN", "SRS", "NSN"), Map.of(
                'N', item(ResourceItemsME.KHAZAD_STEEL_NUGGET), 'S', item(nurgon),
                'R', metal(ResourceItemsME.ROD, "steel"))),
            new Layout(art, ResourceItemsME.CITRINE_BULLSEYE_LAMP, 1, List.of("ESE", "RCG", "ERE"), Map.of(
                'E', metal(ResourceItemsME.ARMOR_PLATE, "gold"), 'S', metal(ResourceItemsME.SHIELD_BORDER, "gold"),
                'R', metal(ResourceItemsME.ROD, "gold"), 'C', item(Items.CANDLE),
                'G', item(ResourceItemsME.CITRINE_GLASS))),
            // A bellows on a steel plate, steel rods for its pipes, and its wooden bucket.
            new Layout(art, ResourceItemsME.STANDARD_PUMP, 1, List.of(" PR", "RLR", "WP "), Map.of(
                'P', metal(ResourceItemsME.ARMOR_PLATE, "steel"), 'R', metal(ResourceItemsME.ROD, "steel"),
                'L', item(DecorativeBlockRegistryME.BELLOWS), 'W', item(DecorativeBlockRegistryME.WOODEN_BUCKET))));
    }

    private static List<Layout> relic() {
        String art = LapidaryTools.RELIC.id();
        Block medgon = StoneBlockSetRegistryME.MEDGON_SET.smoothBlocks.base();
        List<Layout> glass = relicGlass();
        return List.of(
            glass.get(0), glass.get(1), glass.get(2), glass.get(3),
            lenses(LapidaryTools.RELIC, ResourceItemsME.EXQUISITE_FOCUSING_LENSES, "mithril", glass),
            new Layout(art, ResourceItemsME.KHAZAD_GRINDING_WHEEL, 1, List.of("NMN", "ORO", "NMN"), Map.of(
                'N', item(ResourceItemsME.MITHRIL_NUGGET), 'M', item(medgon),
                'O', item(Items.OBSIDIAN), 'R', metal(ResourceItemsME.ROD, "mithril"))),
            new Layout(art, ResourceItemsME.MIRDAIN_BEAM_LAMP, 1, List.of("SRE", "RCA", "EEE"), Map.of(
                'S', metal(ResourceItemsME.SHIELD_BORDER, "mithril"), 'R', metal(ResourceItemsME.ROD, "mithril"),
                'C', item(DecorativeBlockRegistryME.CANDLE_HOLDER), 'A', item(ResourceItemsME.ADAMANT_GLASS),
                'E', metal(ResourceItemsME.ARMOR_PLATE, "edhel_steel"))),
            // The Standard Pump refitted in mithril, its basin a mithril plate.
            new Layout(art, ResourceItemsME.NUMENOREAN_PUMP, 1, List.of(" NR", "RSR", "PN "), Map.of(
                'N', item(ResourceItemsME.MITHRIL_NUGGET), 'R', metal(ResourceItemsME.ROD, "mithril"),
                'S', item(ResourceItemsME.STANDARD_PUMP), 'P', metal(ResourceItemsME.ARMOR_PLATE, "mithril"))));
    }
}
