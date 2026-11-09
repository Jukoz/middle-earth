package net.sevenstars.middleearth.item;

import net.minecraft.component.DataComponentTypes;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.block.registration.BlockRegistryME;
import net.sevenstars.middleearth.block.registration.NatureBlockRegistryME;
import net.sevenstars.middleearth.block.special.CustomWaterloggableTallPlantBlock;
import net.sevenstars.middleearth.datageneration.content.TranslationEntries;
import net.sevenstars.middleearth.entity.EntitiesME;
import net.sevenstars.middleearth.item.items.*;
import net.sevenstars.middleearth.item.items.weapons.ranged.LitPineconeItem;
import net.sevenstars.middleearth.item.items.weapons.ranged.PebbleItem;
import net.sevenstars.middleearth.item.items.weapons.ranged.PineconeItem;
import net.sevenstars.middleearth.item.utils.BannerPatternTagsME;
import net.sevenstars.middleearth.item.utils.ItemGroupsME;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.sevenstars.middleearth.registries.RegistryAliasesME;

import java.util.EnumMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import net.minecraft.registry.RegistryKey;
import net.sevenstars.middleearth.item.items.gems.CutGemItem;
import net.sevenstars.middleearth.item.items.gems.GemCut;
import net.sevenstars.middleearth.item.items.gems.RawGemItem;
import net.sevenstars.middleearth.registries.content.gems.GemTypeRegistry;
import net.sevenstars.middleearth.resources.datas.gems.GemType;

public class ResourceItemsME {

    /**
     * Middle-earth mod Resource Items registry
     */

    public static final Item MIDDLE_EARTH_MAP = registerItem("middle_earth_map",
            MiddleEarthMapItem::new, new Item.Settings().maxCount(1));

    public static final Item EMPTY_PHIAL = registerItem("empty_phial",
            EmptyPhialItem::new, new Item.Settings());
    public static final Item WATER_PHIAL = registerItem("water_phial",
            Item::new, new Item.Settings().maxCount(1));
    public static final Item STARLIGHT_PHIAL = registerItem("starlight_phial",
            StarlightPhialItem::new, new Item.Settings().maxCount(1));

    public static final Item PLAYER_BOOK = registerItem("player_book",
            PlayerBookItem::new, new Item.Settings().maxCount(1));

    public static final Item REINFORCED_BARREL = registerItem("reinforced_barrel",
            (settings) -> {
                return new BoatItem(EntitiesME.REINFORCED_BARREL, settings);
            }, new Item.Settings().maxCount(1));

    public static final Item RAW_TIN = registerItem("raw_tin",
            Item::new, new Item.Settings());
    public static final Item TIN_INGOT = registerItem("tin_ingot",
            Item::new, new Item.Settings());
    public static final Item TIN_NUGGET = registerItem("tin_nugget",
            Item::new, new Item.Settings());

    public static final Item RAW_LEAD = registerItem("raw_lead",
            Item::new, new Item.Settings());
    public static final Item LEAD_INGOT = registerItem("lead_ingot",
            Item::new, new Item.Settings());
    public static final Item LEAD_NUGGET = registerItem("lead_nugget",
            Item::new, new Item.Settings());

    public static final Item RAW_SILVER = registerItem("raw_silver",
            Item::new, new Item.Settings());
    public static final Item SILVER_INGOT = registerItem("silver_ingot",
            Item::new, new Item.Settings());
    public static final Item SILVER_NUGGET = registerItem("silver_nugget",
            Item::new, new Item.Settings());

    public static final Item BRONZE_INGOT = registerItem("bronze_ingot",
            Item::new, new Item.Settings());
    public static final Item BRONZE_NUGGET = registerItem("bronze_nugget",
            Item::new, new Item.Settings());

    public static final Item CRUDE_INGOT = registerItem("crude_ingot",
            Item::new, new Item.Settings());
    public static final Item CRUDE_NUGGET = registerItem("crude_nugget",
            Item::new, new Item.Settings());

    public static final Item BURZUM_STEEL_INGOT = registerItem("burzum_steel_ingot",
            SmithingMaterialItem::new, new Item.Settings());
    public static final Item BURZUM_STEEL_NUGGET = registerItem("burzum_steel_nugget",
            Item::new, new Item.Settings());

    public static final Item STEEL_INGOT = registerItem("steel_ingot",
            SmithingMaterialItem::new, new Item.Settings());
    public static final Item STEEL_NUGGET = registerItem("steel_nugget",
            Item::new, new Item.Settings());

    public static final Item EDHEL_STEEL_INGOT = registerItem("edhel_steel_ingot",
            SmithingMaterialItem::new, new Item.Settings());
    public static final Item EDHEL_STEEL_NUGGET = registerItem("edhel_steel_nugget",
            Item::new, new Item.Settings());

    public static final Item KHAZAD_STEEL_INGOT = registerItem("khazad_steel_ingot",
            SmithingMaterialItem::new, new Item.Settings());
    public static final Item KHAZAD_STEEL_NUGGET = registerItem("khazad_steel_nugget",
            Item::new, new Item.Settings());

    public static final Item RAW_MITHRIL = registerItem("raw_mithril",
            Item::new, new Item.Settings().fireproof());
    public static final Item MITHRIL_INGOT = registerItem("mithril_ingot",
            Item::new, new Item.Settings().fireproof());
    public static final Item MITHRIL_NUGGET = registerItem("mithril_nugget",
            Item::new, new Item.Settings().fireproof());

    // Gems: one raw gem per gem type (replaces the old adamant/ruby/sapphire items, aliased), and one cut gem
    // item per cut whose gem type is a component on the stack.
    public static final Item RAW_ADAMANT = registerRawGem(GemTypeRegistry.ADAMANT);
    public static final Item RAW_RUBY = registerRawGem(GemTypeRegistry.RUBY);
    public static final Item RAW_SAPPHIRE = registerRawGem(GemTypeRegistry.SAPPHIRE);
    public static final Item RAW_BERYL = registerRawGem(GemTypeRegistry.BERYL);
    public static final Item RAW_HADORITE = registerRawGem(GemTypeRegistry.HADORITE);
    public static final Item RAW_OPAL = registerRawGem(GemTypeRegistry.OPAL);
    public static final Item RAW_ONYX = registerRawGem(GemTypeRegistry.ONYX);
    public static final Item RAW_MOONSTONE = registerRawGem(GemTypeRegistry.MOONSTONE);

    /** Raw gem item per gem type, in creative tab order. */
    public static final Map<RegistryKey<GemType>, Item> RAW_GEMS = new LinkedHashMap<>();
    /** Cut gem item per cut. */
    public static final Map<GemCut, CutGemItem> CUT_GEMS = new EnumMap<>(GemCut.class);

    /** Notes written at the Lapidary Table, one item per study grade (the gem is a component); hidden from tabs. */
    public static final Map<net.sevenstars.middleearth.recipe.lapidary.StudyGrade, Item> GEM_NOTES = new EnumMap<>(net.sevenstars.middleearth.recipe.lapidary.StudyGrade.class);

    static {
        for (var grade : net.sevenstars.middleearth.recipe.lapidary.StudyGrade.values()) {
            GEM_NOTES.put(grade, registerHiddenItem(grade.notesId(),
                    settings -> new net.sevenstars.middleearth.item.items.gems.GemNotesItem(settings, grade), new Item.Settings().maxCount(16)));
        }
        List.of(RAW_ADAMANT, RAW_RUBY, RAW_SAPPHIRE, RAW_BERYL, RAW_HADORITE, RAW_OPAL, RAW_ONYX, RAW_MOONSTONE)
                .forEach(item -> RAW_GEMS.put(item.getComponents().get(DataComponentTypesME.GEM_TYPE), item));
        for (GemCut cut : GemCut.values()) {
            CUT_GEMS.put(cut, (CutGemItem) registerItem(cut.id() + "_cut_gem",
                    settings -> new CutGemItem(settings, cut), new Item.Settings()));
        }
    }


    // ---- The lost lapidary arts: Lapidary Tools and everything on the way to them (see LapidaryKnowledge). ----
    /** Every item of the lapidary arts, in creative tab order (their names are in GemTranslations). */
    public static final List<Item> LAPIDARY_ITEMS = new java.util.ArrayList<>();

    public static final Item CITRINE_GLASS = registerLapidary("citrine_glass", Item::new, new Item.Settings());
    public static final Item AMETHYST_GLASS = registerLapidary("amethyst_glass", Item::new, new Item.Settings());
    public static final Item RED_AGATE_GLASS = registerLapidary("red_agate_glass", Item::new, new Item.Settings());
    public static final Item QUARTZ_GLASS = registerLapidary("quartz_glass", Item::new, new Item.Settings());
    public static final Item ADAMANT_GLASS = registerLapidary("adamant_glass", Item::new, new Item.Settings());
    public static final Item RUBY_GLASS = registerLapidary("ruby_glass", Item::new, new Item.Settings());
    public static final Item SAPPHIRE_GLASS = registerLapidary("sapphire_glass", Item::new, new Item.Settings());
    public static final Item BERYL_GLASS = registerLapidary("beryl_glass", Item::new, new Item.Settings());

    public static final Item REFINED_FOCUSING_LENSES = registerLapidary("refined_focusing_lenses", Item::new, part(Rarity.UNCOMMON));
    public static final Item ENHANCED_GRINDING_WHEEL = registerLapidary("enhanced_grinding_wheel", Item::new, part(Rarity.UNCOMMON));
    public static final Item CITRINE_BULLSEYE_LAMP = registerLamp("citrine_bullseye_lamp", net.sevenstars.middleearth.block.registration.DecorativeBlockRegistryME.CITRINE_BULLSEYE_LAMP, Rarity.UNCOMMON);
    public static final Item WATER_PUMP = registerLapidary("water_pump", Item::new, part(Rarity.UNCOMMON));
    public static final Item EXQUISITE_FOCUSING_LENSES = registerLapidary("exquisite_focusing_lenses", Item::new, part(Rarity.RARE));
    public static final Item KHAZAD_GRINDING_WHEEL = registerLapidary("khazad_grinding_wheel", Item::new, part(Rarity.RARE));
    public static final Item MIRDAIN_BEAM_LAMP = registerLamp("mirdain_beam_lamp", net.sevenstars.middleearth.block.registration.DecorativeBlockRegistryME.MIRDAIN_BEAM_LAMP, Rarity.RARE);
    public static final Item NUMENOREAN_WATER_PUMP = registerLapidary("numenorean_water_pump", Item::new, part(Rarity.RARE));

    public static final Item MASTERWORK_LAPIDARY_TOOLS = registerLapidary("masterwork_lapidary_tools",
            settings -> new net.sevenstars.middleearth.item.items.gems.LapidaryToolsItem(settings,
                    net.sevenstars.middleearth.recipe.lapidary.LapidaryTools.MASTERWORK), part(Rarity.RARE));
    public static final Item RELIC_LAPIDARY_TOOLS = registerLapidary("relic_lapidary_tools",
            settings -> new net.sevenstars.middleearth.item.items.gems.LapidaryToolsItem(settings,
                    net.sevenstars.middleearth.recipe.lapidary.LapidaryTools.RELIC), part(Rarity.EPIC).fireproof());
    public static final Item MASTERWORK_LAPIDARY_SCROLL = registerLapidary("masterwork_lapidary_scroll",
            settings -> new net.sevenstars.middleearth.item.items.gems.LapidaryScrollItem(settings,
                    net.sevenstars.middleearth.recipe.lapidary.LapidaryTools.MASTERWORK), part(Rarity.RARE));
    public static final Item RELIC_LAPIDARY_SCROLL = registerLapidary("relic_lapidary_scroll",
            settings -> new net.sevenstars.middleearth.item.items.gems.LapidaryScrollItem(settings,
                    net.sevenstars.middleearth.recipe.lapidary.LapidaryTools.RELIC), part(Rarity.EPIC));

    public static final Item RED_AGATE_SHARD = registerItem("red_agate_shard",
            Item::new, new Item.Settings());
    public static final Item CITRINE_SHARD = registerItem("citrine_shard",
            Item::new, new Item.Settings());
    public static final Item QUARTZ_SHARD = registerItem("quartz_shard",
            Item::new, new Item.Settings());

    public static final Item DIRTY_BONE = registerItem("dirty_bone",
            Item::new, new Item.Settings());
    public static final Item FANG = registerItem("fang",
            Item::new, new Item.Settings());
    public static final Item SPIDER_STINGER = registerItem("spider_stinger",
            Item::new, new Item.Settings());

    public static final Item ASH = registerItem("ash",
            Item::new, new Item.Settings());

    public static final Item PEBBLE = registerItem("pebble",
            PebbleItem::new, new Item.Settings());
    public static final Item PINECONE = registerItem("pinecone",
            PineconeItem::new, new Item.Settings());
    public static final Item LIT_PINECONE = registerItem("lit_pinecone",
            LitPineconeItem::new, new Item.Settings().maxCount(16));

    public static final Item STRAW = registerItem("straw",
            Item::new, new Item.Settings());
    public static final Block REEDS = BlockRegistryME.registerBlock("reeds",
            (settings) -> new CustomWaterloggableTallPlantBlock(settings, false), AbstractBlock.Settings.copy(Blocks.TALL_GRASS).breakInstantly(), false, ItemGroupsME.RESOURCES_CONTENTS);
    public static final Item SWAN_FEATHER = registerItem("swan_feather",
            Item::new, new Item.Settings());

    public static final Item GLOWWORM_BOTTLE = registerItem("glowworm_bottle",
            GlowwormBottle::new, new Item.Settings().maxCount(1)
                    .food(new FoodComponent.Builder().nutrition(2).saturationModifier(0.3f).alwaysEdible().build()).recipeRemainder(Items.GLASS_BOTTLE));

    public static final Item COPPER_COIN = registerItem("copper_coin", Item::new, new Item.Settings());
    public static final Item SILVER_COIN = registerItem("silver_coin", Item::new, new Item.Settings());
    public static final Item GOLD_COIN = registerItem("gold_coin", Item::new, new Item.Settings());

    public static final Item FLAX = registerItem("flax",
            Item::new, new Item.Settings());
    public static final Item FLAX_SEEDS = registerItem("flax_seeds",
            (settings) -> new BlockItem(NatureBlockRegistryME.FLAX_CROP, settings), new Item.Settings());
    public static final Item PIPEWEED = registerItem("pipeweed",
            Item::new, new Item.Settings());
    public static final Item DRIED_PIPEWEED = registerItem("dried_pipeweed",
            Item::new, new Item.Settings());
    public static final Item PIPEWEED_SEEDS = registerItem("pipeweed_seeds",
            (settings) -> new BlockItem(NatureBlockRegistryME.PIPEWEED_CROP, settings), new Item.Settings());


    public static final Item TOMATO_SEEDS = registerItem("tomato_seeds",
            (settings) -> new BlockItem(NatureBlockRegistryME.TOMATO_CROP, settings), new Item.Settings());
    public static final Item BELL_PEPPER_SEEDS = registerItem("bell_pepper_seeds",
            (settings) -> new BlockItem(NatureBlockRegistryME.BELL_PEPPER_CROP, settings), new Item.Settings());
    public static final Item CUCUMBER_SEEDS = registerItem("cucumber_seeds",
            (settings) -> new BlockItem(NatureBlockRegistryME.CUCUMBER_CROP, settings), new Item.Settings());
    public static final Item LETTUCE_SEEDS = registerItem("lettuce_seeds",
            (settings) -> new BlockItem(NatureBlockRegistryME.LETTUCE_CROP, settings), new Item.Settings());

    public static final Item FUR = registerItem("fur",
            Item::new, new Item.Settings());
    public static final Item FABRIC = registerItem("fabric",
            Item::new, new Item.Settings());

    public static final Item ROD = registerItem("rod",
            Item::new, new Item.Settings().maxCount(16));
    public static final Item LARGE_ROD = registerItem("large_rod",
            Item::new, new Item.Settings().maxCount(16));

    public static final Item PICKAXE_HEAD = registerItem("pickaxe_head",
            SmithingMaterialItem::new, new Item.Settings().maxCount(16));
    public static final Item AXE_HEAD = registerItem("axe_head",
            SmithingMaterialItem::new, new Item.Settings().maxCount(16));
    public static final Item SHOVEL_HEAD = registerItem("shovel_head",
            SmithingMaterialItem::new, new Item.Settings().maxCount(16));
    public static final Item HOE_HEAD = registerItem("hoe_head",
            SmithingMaterialItem::new, new Item.Settings().maxCount(16));

    public static final Item SHORT_BLADE = registerItem("short_blade",
            SmithingMaterialItem::new, new Item.Settings().maxCount(16));
    public static final Item BLADE = registerItem("blade",
            SmithingMaterialItem::new, new Item.Settings().maxCount(16));
    public static final Item LONG_BLADE = registerItem("long_blade",
            SmithingMaterialItem::new, new Item.Settings().maxCount(16));
    public static final Item SWORD_HILT = registerItem("sword_hilt",
            SmithingMaterialItem::new, new Item.Settings().maxCount(16));

    public static final Item MAIL_RING = registerItem("mail_ring",
            SmithingMaterialItem::new, new Item.Settings().maxCount(64));
    public static final Item MAIL = registerItem("mail",
            Item::new, new Item.Settings().maxCount(16));
    public static final Item SCALE = registerItem("scale",
            Item::new, new Item.Settings().maxCount(64));
    public static final Item SCALE_MAIL = registerItem("scale_mail",
            SmithingMaterialItem::new, new Item.Settings().maxCount(16));
    public static final Item ARMOR_PLATE = registerItem("armor_plate",
            SmithingMaterialItem::new, new Item.Settings().maxCount(16));

    public static final Item HELMET_PLATE = registerItem("helmet_plate",
            SmithingMaterialItem::new, new Item.Settings().maxCount(16));
    public static final Item SHIELD_BORDER = registerItem("shield_border",
            SmithingMaterialItem::new, new Item.Settings().maxCount(16));
    public static final Item SHIELD_PLATE = registerItem("shield_plate",
            SmithingMaterialItem::new, new Item.Settings().maxCount(16));

    public static final Item BRIGAND_KEY = registerItem("brigand_key",
            Item::new, new Item.Settings());
    public static final Item DWARVEN_KEY = registerItem("dwarven_key",
            Item::new, new Item.Settings().maxCount(1));

    public static final Item PIPEWEED_BANNER_PATTERN = registerItem("pipeweed_banner_pattern",
            Item::new, new Item.Settings().maxCount(1).rarity(Rarity.RARE)
                    .component(DataComponentTypes.PROVIDES_BANNER_PATTERNS, BannerPatternTagsME.PIPEWEED_PATTERN_ITEM));
    public static final Item GONDOR_BANNER_PATTERN = registerItem("gondor_banner_pattern",
            Item::new, new Item.Settings().maxCount(1).rarity(Rarity.RARE)
                    .component(DataComponentTypes.PROVIDES_BANNER_PATTERNS, BannerPatternTagsME.GONDOR_PATTERN_ITEM));
    public static final Item ROHAN_BANNER_PATTERN = registerItem("rohan_banner_pattern",
            Item::new, new Item.Settings().maxCount(1).rarity(Rarity.RARE)
                    .component(DataComponentTypes.PROVIDES_BANNER_PATTERNS, BannerPatternTagsME.ROHAN_PATTERN_ITEM));
    public static final Item LOTHLORIEN_BANNER_PATTERN = registerItem("lothlorien_banner_pattern",
            Item::new, new Item.Settings().maxCount(1).rarity(Rarity.RARE)
                    .component(DataComponentTypes.PROVIDES_BANNER_PATTERNS, BannerPatternTagsME.LOTHLORIEN_PATTERN_ITEM));
    public static final Item MORDOR_BANNER_PATTERN = registerItem("mordor_banner_pattern",
            Item::new, new Item.Settings().maxCount(1).rarity(Rarity.RARE)
                    .component(DataComponentTypes.PROVIDES_BANNER_PATTERNS, BannerPatternTagsME.MORDOR_PATTERN_ITEM));
    public static final Item MISTY_MOUNTAINS_ORCS_BANNER_PATTERN = registerItem("misty_mountains_orcs_banner_pattern",
            Item::new, new Item.Settings().maxCount(1).rarity(Rarity.RARE)
                    .component(DataComponentTypes.PROVIDES_BANNER_PATTERNS, BannerPatternTagsME.MISTY_MOUNTAINS_ORCS_PATTERN_ITEM));
    public static final Item ISENGARD_BANNER_PATTERN = registerItem("isengard_banner_pattern",
            Item::new, new Item.Settings().maxCount(1).rarity(Rarity.RARE)
                    .component(DataComponentTypes.PROVIDES_BANNER_PATTERNS, BannerPatternTagsME.ISENGARD_PATTERN_ITEM));

    public static final Item SCREECHING_SKULL_BANNER_PATTERN = registerItem("screeching_skull_banner_pattern",
            Item::new, new Item.Settings().maxCount(1).rarity(Rarity.RARE)
                    .component(DataComponentTypes.PROVIDES_BANNER_PATTERNS, BannerPatternTagsME.SCREECHING_SKULL_PATTERN_ITEM));
    public static final Item GOBLIN_SKULL_BANNER_PATTERN = registerItem("goblin_skull_banner_pattern",
            Item::new, new Item.Settings().maxCount(1).rarity(Rarity.RARE)
                    .component(DataComponentTypes.PROVIDES_BANNER_PATTERNS, BannerPatternTagsME.GOBLIN_SKULL_PATTERN_ITEM));

    public static final Item ANVIL_BANNER_PATTERN = registerItem("anvil_banner_pattern",
            Item::new, new Item.Settings().maxCount(1).rarity(Rarity.RARE)
                    .component(DataComponentTypes.PROVIDES_BANNER_PATTERNS, BannerPatternTagsME.ANVIL_PATTERN_ITEM));
    public static final Item BELL_BANNER_PATTERN = registerItem("bell_banner_pattern",
            Item::new, new Item.Settings().maxCount(1).rarity(Rarity.RARE)
                    .component(DataComponentTypes.PROVIDES_BANNER_PATTERNS, BannerPatternTagsME.BELL_PATTERN_ITEM));
    public static final Item BOW_BANNER_PATTERN = registerItem("bow_banner_pattern",
            Item::new, new Item.Settings().maxCount(1).rarity(Rarity.RARE)
                    .component(DataComponentTypes.PROVIDES_BANNER_PATTERNS, BannerPatternTagsME.BOW_PATTERN_ITEM));

    public static final Item DWARF_CROWN_BANNER_PATTERN = registerItem("dwarf_crown_banner_pattern",
            Item::new, new Item.Settings().maxCount(1).rarity(Rarity.RARE)
                    .component(DataComponentTypes.PROVIDES_BANNER_PATTERNS, BannerPatternTagsME.DWARF_CROWN_PATTERN_ITEM));

    public static final Item SPIDER_BANNER_PATTERN = registerItem("spider_banner_pattern",
            Item::new, new Item.Settings().maxCount(1).rarity(Rarity.RARE)
                    .component(DataComponentTypes.PROVIDES_BANNER_PATTERNS, BannerPatternTagsME.SPIDER_PATTERN_ITEM));

    public static final Item GREAT_HORN_BANNER_PATTERN = registerItem("great_horn_banner_pattern",
            Item::new, new Item.Settings().maxCount(1).rarity(Rarity.RARE)
                    .component(DataComponentTypes.PROVIDES_BANNER_PATTERNS, BannerPatternTagsME.GREAT_HORN_PATTERN_ITEM));
    public static final Item OAK_LEAF_BANNER_PATTERN = registerItem("oak_leaf_banner_pattern",
            Item::new, new Item.Settings().maxCount(1).rarity(Rarity.RARE)
                    .component(DataComponentTypes.PROVIDES_BANNER_PATTERNS, BannerPatternTagsME.OAK_LEAF_PATTERN_ITEM));

    public static final Item ANTLERS_BANNER_PATTERN = registerItem("antlers_banner_pattern",
            Item::new, new Item.Settings().maxCount(1).rarity(Rarity.EPIC)
                    .component(DataComponentTypes.PROVIDES_BANNER_PATTERNS, BannerPatternTagsME.ANTLERS_PATTERN_ITEM));
    public static final Item DRAGON_BANNER_PATTERN = registerItem("dragon_banner_pattern",
            Item::new, new Item.Settings().maxCount(1).rarity(Rarity.EPIC)
                    .component(DataComponentTypes.PROVIDES_BANNER_PATTERNS, BannerPatternTagsME.DRAGON_PATTERN_ITEM));
    public static final Item SNAIL_BANNER_PATTERN = registerItem("snail_banner_pattern",
            Item::new, new Item.Settings().maxCount(1).rarity(Rarity.EPIC)
                    .component(DataComponentTypes.PROVIDES_BANNER_PATTERNS, BannerPatternTagsME.SNAIL_PATTERN_ITEM));

    public static final Item PTEROSAUR_NUGGET = registerHiddenItem("pterosaur_nugget",
            HotChickenFoodItem::new, new Item.Settings());
    public static final Item THERAPOD_NUGGET = registerHiddenItem("therapod_nugget",
            HotChickenFoodItem::new, new Item.Settings());
    public static final Item CERATOPSIAN_NUGGET = registerHiddenItem("ceratopsian_nugget",
            HotChickenFoodItem::new, new Item.Settings());
    public static final Item THYREOPHORAN_NUGGET = registerHiddenItem("thyreophoran_nugget",
            HotChickenFoodItem::new, new Item.Settings());
    public static final Item SAUROPOD_NUGGET = registerHiddenItem("sauropod_nugget",
            HotChickenFoodItem::new, new Item.Settings().maxCount(16));

    private static Item registerItem(String name, Function<Item.Settings, Item> factory, Item.Settings settings) {
        Item item = factory.apply(settings.registryKey(BlockRegistryME.keyOfItem(name)));
        ItemGroupsME.RESOURCES_CONTENTS.add(item.getDefaultStack());
        TranslationEntries.itemEntries.add(item);
        RegistryAliasesME.aliases.add(new RegistryAliasesME.Alias(Registries.ITEM, name));
        return Registry.register(Registries.ITEM, Identifier.of(MiddleEarth.MOD_ID, name), item);
    }
    /** A lapidary arts item: in the creative tab, named in GemTranslations rather than from its id. */
    private static Item registerLapidary(String name, Function<Item.Settings, Item> factory, Item.Settings settings) {
        Item item = factory.apply(settings.registryKey(BlockRegistryME.keyOfItem(name)));
        ItemGroupsME.RESOURCES_CONTENTS.add(item.getDefaultStack());
        LAPIDARY_ITEMS.add(item);
        return Registry.register(Registries.ITEM, Identifier.of(MiddleEarth.MOD_ID, name), item);
    }

    /** A focused lamp: a lapidary part that is also a placeable block and an off-hand lamp. */
    private static Item registerLamp(String name, net.minecraft.block.Block block, Rarity rarity) {
        Item item = registerLapidary(name, settings -> new net.sevenstars.middleearth.block.special.beamlamp.BeamLampItem(block, settings),
            part(rarity).component(DataComponentTypesME.LAMP_ON, false));
        Item.BLOCK_ITEMS.put(block, item);
        return item;
    }

    private static Item.Settings part(Rarity rarity) {
        return new Item.Settings().maxCount(1).rarity(rarity);
    }

    private static Item registerRawGem(RegistryKey<GemType> gemType) {
        return registerItem("raw_" + GemTypeRegistry.name(gemType), RawGemItem::new,
                new Item.Settings().component(DataComponentTypesME.GEM_TYPE, gemType));
    }

    /** A full-charge cut gem of the given cut and gem type. */
    public static ItemStack cutGem(GemCut cut, RegistryKey<GemType> gemType) {
        ItemStack stack = new ItemStack(CUT_GEMS.get(cut));
        stack.set(DataComponentTypesME.GEM_TYPE, gemType);
        return stack;
    }

    private static Item registerHiddenItem(String name, Function<Item.Settings, Item> factory, Item.Settings settings) {
        Item item = factory.apply(settings.registryKey(BlockRegistryME.keyOfItem(name)));
        TranslationEntries.itemEntries.add(item);
        RegistryAliasesME.aliases.add(new RegistryAliasesME.Alias(Registries.ITEM, name));
        return Registry.register(Registries.ITEM, Identifier.of(MiddleEarth.MOD_ID, name), item);
    }

    public static void registerModItems() {
        MiddleEarth.LOGGER.logDebugMsg("Registering Mod Resource Items for " + MiddleEarth.MOD_ID);
    }
}
