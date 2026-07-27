package net.sevenstars.middleearth.item;

import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.api.registries.RegistrationBridge;
import net.sevenstars.middleearth.block.registration.ModBlocks;
import net.sevenstars.middleearth.block.registration.ModNatureBlocks;
import net.sevenstars.middleearth.block.special.CustomWaterloggableTallPlantBlock;
import net.sevenstars.middleearth.datageneration.content.TranslationEntries;
import net.sevenstars.middleearth.item.items.*;
import net.sevenstars.middleearth.item.items.weapons.ranged.LitPineconeItem;
import net.sevenstars.middleearth.item.items.weapons.ranged.PebbleItem;
import net.sevenstars.middleearth.item.items.weapons.ranged.PineconeItem;
import net.sevenstars.middleearth.item.utils.BannerPatternTagsME;
import net.sevenstars.middleearth.item.utils.ItemGroupsME;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BannerPatternItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.component.BundleContents;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.sevenstars.middleearth.registries.RegistryAliasesME;

import java.util.function.Function;
import java.util.List;

public class ResourceItemsME {

    /**
     * Middle-earth mod Resource Items registry
     */

    public static final Item MIDDLE_EARTH_MAP = registerItem("middle_earth_map",
            MiddleEarthMapItem::new, new Item.Properties().stacksTo(1));

    public static final Item EMPTY_PHIAL = registerItem("empty_phial",
            EmptyPhialItem::new, new Item.Properties());
    public static final Item WATER_PHIAL = registerItem("water_phial",
            Item::new, new Item.Properties().stacksTo(1));
    public static final Item STARLIGHT_PHIAL = registerItem("starlight_phial",
            StarlightPhialItem::new, new Item.Properties().stacksTo(1));

    public static final Item PLAYER_BOOK = registerItem("player_book",
            PlayerBookItem::new, new Item.Properties().stacksTo(1));

    public static final Item REINFORCED_BARREL = registerItem("reinforced_barrel",
            ReinforcedBarrelItem::new, new Item.Properties().stacksTo(1));

    public static final Item YELLOW_BUNDLE = registerBundle("yellow_bundle", DyeColor.YELLOW);
    public static final Item BROWN_BUNDLE = registerBundle("brown_bundle", DyeColor.BROWN);
    public static final Item GREEN_BUNDLE = registerBundle("green_bundle", DyeColor.GREEN);
    public static final Item BLUE_BUNDLE = registerBundle("blue_bundle", DyeColor.BLUE);
    public static final Item GRAY_BUNDLE = registerBundle("gray_bundle", DyeColor.GRAY);
    public static final Item LIGHT_GRAY_BUNDLE = registerBundle("light_gray_bundle", DyeColor.LIGHT_GRAY);
    public static final Item WHITE_BUNDLE = registerBundle("white_bundle", DyeColor.WHITE);
    public static final Item LIME_BUNDLE = registerBundle("lime_bundle", DyeColor.LIME);
    public static final Item LIGHT_BLUE_BUNDLE = registerBundle("light_blue_bundle", DyeColor.LIGHT_BLUE);
    public static final Item RED_BUNDLE = registerBundle("red_bundle", DyeColor.RED);
    public static final Item BLACK_BUNDLE = registerBundle("black_bundle", DyeColor.BLACK);
    public static final List<Item> COLORED_BUNDLES = List.of(
            YELLOW_BUNDLE,
            BROWN_BUNDLE,
            GREEN_BUNDLE,
            BLUE_BUNDLE,
            GRAY_BUNDLE,
            LIGHT_GRAY_BUNDLE,
            WHITE_BUNDLE,
            LIME_BUNDLE,
            LIGHT_BLUE_BUNDLE,
            RED_BUNDLE,
            BLACK_BUNDLE
    );

    public static final Item RAW_TIN = registerItem("raw_tin",
            Item::new, new Item.Properties());
    public static final Item TIN_INGOT = registerItem("tin_ingot",
            Item::new, new Item.Properties());
    public static final Item TIN_NUGGET = registerItem("tin_nugget",
            Item::new, new Item.Properties());

    public static final Item RAW_LEAD = registerItem("raw_lead",
            Item::new, new Item.Properties());
    public static final Item LEAD_INGOT = registerItem("lead_ingot",
            Item::new, new Item.Properties());
    public static final Item LEAD_NUGGET = registerItem("lead_nugget",
            Item::new, new Item.Properties());

    public static final Item RAW_SILVER = registerItem("raw_silver",
            Item::new, new Item.Properties());
    public static final Item SILVER_INGOT = registerItem("silver_ingot",
            Item::new, new Item.Properties());
    public static final Item SILVER_NUGGET = registerItem("silver_nugget",
            Item::new, new Item.Properties());

    public static final Item BRONZE_INGOT = registerItem("bronze_ingot",
            Item::new, new Item.Properties());
    public static final Item BRONZE_NUGGET = registerItem("bronze_nugget",
            Item::new, new Item.Properties());

    public static final Item CRUDE_INGOT = registerItem("crude_ingot",
            Item::new, new Item.Properties());
    public static final Item CRUDE_NUGGET = registerItem("crude_nugget",
            Item::new, new Item.Properties());

    public static final Item BURZUM_STEEL_INGOT = registerItem("burzum_steel_ingot",
            SmithingMaterialItem::new, new Item.Properties());
    public static final Item BURZUM_STEEL_NUGGET = registerItem("burzum_steel_nugget",
            Item::new, new Item.Properties());

    public static final Item STEEL_INGOT = registerItem("steel_ingot",
            SmithingMaterialItem::new, new Item.Properties());
    public static final Item STEEL_NUGGET = registerItem("steel_nugget",
            Item::new, new Item.Properties());

    public static final Item EDHEL_STEEL_INGOT = registerItem("edhel_steel_ingot",
            SmithingMaterialItem::new, new Item.Properties());
    public static final Item EDHEL_STEEL_NUGGET = registerItem("edhel_steel_nugget",
            Item::new, new Item.Properties());

    public static final Item KHAZAD_STEEL_INGOT = registerItem("khazad_steel_ingot",
            SmithingMaterialItem::new, new Item.Properties());
    public static final Item KHAZAD_STEEL_NUGGET = registerItem("khazad_steel_nugget",
            Item::new, new Item.Properties());

    public static final Item RAW_MITHRIL = registerItem("raw_mithril",
            Item::new, new Item.Properties().fireResistant());
    public static final Item MITHRIL_INGOT = registerItem("mithril_ingot",
            Item::new, new Item.Properties().fireResistant());
    public static final Item MITHRIL_NUGGET = registerItem("mithril_nugget",
            Item::new, new Item.Properties().fireResistant());

    public static final Item ADAMANT = registerItem("adamant",
            Item::new, new Item.Properties());
    public static final Item RUBY = registerItem("ruby",
            Item::new, new Item.Properties());
    public static final Item SAPPHIRE = registerItem("sapphire",
            Item::new, new Item.Properties());

    public static final Item RED_AGATE_SHARD = registerItem("red_agate_shard",
            Item::new, new Item.Properties());
    public static final Item CITRINE_SHARD = registerItem("citrine_shard",
            Item::new, new Item.Properties());
    public static final Item QUARTZ_SHARD = registerItem("quartz_shard",
            Item::new, new Item.Properties());

    public static final Item DIRTY_BONE = registerItem("dirty_bone",
            Item::new, new Item.Properties());
    public static final Item FANG = registerItem("fang",
            Item::new, new Item.Properties());
    public static final Item SPIDER_STINGER = registerItem("spider_stinger",
            Item::new, new Item.Properties());

    public static final Item ASH = registerItem("ash",
            Item::new, new Item.Properties());

    public static final Item PEBBLE = registerItem("pebble",
            PebbleItem::new, new Item.Properties());
    public static final Item PINECONE = registerItem("pinecone",
            PineconeItem::new, new Item.Properties());
    public static final Item LIT_PINECONE = registerItem("lit_pinecone",
            LitPineconeItem::new, new Item.Properties().stacksTo(16));

    public static final Item STRAW = registerItem("straw",
            Item::new, new Item.Properties());
    public static final Block REEDS = ModBlocks.registerBlock("reeds",
            (settings) -> new CustomWaterloggableTallPlantBlock(settings, false), BlockBehaviour.Properties.ofFullCopy(Blocks.TALL_GRASS).instabreak(), false, ItemGroupsME.RESOURCES_CONTENTS);
    public static final Item SWAN_FEATHER = registerItem("swan_feather",
            Item::new, new Item.Properties());

    public static final Item GLOWWORM_BOTTLE = registerItem("glowworm_bottle",
            GlowwormBottle::new, new Item.Properties().stacksTo(1)
                    .food(new FoodProperties.Builder().nutrition(2).saturationModifier(0.3f).alwaysEdible().build()).craftRemainder(Items.GLASS_BOTTLE));

    public static final Item COPPER_COIN = registerItem("copper_coin", Item::new, new Item.Properties());
    public static final Item SILVER_COIN = registerItem("silver_coin", Item::new, new Item.Properties());
    public static final Item GOLD_COIN = registerItem("gold_coin", Item::new, new Item.Properties());

    public static final Item FLAX = registerItem("flax",
            Item::new, new Item.Properties());
    public static final Item FLAX_SEEDS = registerItem("flax_seeds",
            (settings) -> new BlockItem(ModNatureBlocks.FLAX_CROP, settings), new Item.Properties());
    public static final Item PIPEWEED = registerItem("pipeweed",
            Item::new, new Item.Properties());
    public static final Item DRIED_PIPEWEED = registerItem("dried_pipeweed",
            Item::new, new Item.Properties());
    public static final Item PIPEWEED_SEEDS = registerItem("pipeweed_seeds",
            (settings) -> new BlockItem(ModNatureBlocks.PIPEWEED_CROP, settings), new Item.Properties());


    public static final Item TOMATO_SEEDS = registerItem("tomato_seeds",
            (settings) -> new BlockItem(ModNatureBlocks.TOMATO_CROP, settings), new Item.Properties());
    public static final Item BELL_PEPPER_SEEDS = registerItem("bell_pepper_seeds",
            (settings) -> new BlockItem(ModNatureBlocks.BELL_PEPPER_CROP, settings), new Item.Properties());
    public static final Item CUCUMBER_SEEDS = registerItem("cucumber_seeds",
            (settings) -> new BlockItem(ModNatureBlocks.CUCUMBER_CROP, settings), new Item.Properties());
    public static final Item LETTUCE_SEEDS = registerItem("lettuce_seeds",
            (settings) -> new BlockItem(ModNatureBlocks.LETTUCE_CROP, settings), new Item.Properties());

    public static final Item FUR = registerItem("fur",
            Item::new, new Item.Properties());
    public static final Item FABRIC = registerItem("fabric",
            Item::new, new Item.Properties());

    public static final Item ROD = registerItem("rod",
            Item::new, new Item.Properties().stacksTo(16));
    public static final Item LARGE_ROD = registerItem("large_rod",
            Item::new, new Item.Properties().stacksTo(16));

    public static final Item PICKAXE_HEAD = registerItem("pickaxe_head",
            SmithingMaterialItem::new, new Item.Properties().stacksTo(16));
    public static final Item AXE_HEAD = registerItem("axe_head",
            SmithingMaterialItem::new, new Item.Properties().stacksTo(16));
    public static final Item SHOVEL_HEAD = registerItem("shovel_head",
            SmithingMaterialItem::new, new Item.Properties().stacksTo(16));
    public static final Item HOE_HEAD = registerItem("hoe_head",
            SmithingMaterialItem::new, new Item.Properties().stacksTo(16));

    public static final Item SHORT_BLADE = registerItem("short_blade",
            SmithingMaterialItem::new, new Item.Properties().stacksTo(16));
    public static final Item BLADE = registerItem("blade",
            SmithingMaterialItem::new, new Item.Properties().stacksTo(16));
    public static final Item LONG_BLADE = registerItem("long_blade",
            SmithingMaterialItem::new, new Item.Properties().stacksTo(16));
    public static final Item SWORD_HILT = registerItem("sword_hilt",
            SmithingMaterialItem::new, new Item.Properties().stacksTo(16));

    public static final Item MAIL_RING = registerItem("mail_ring",
            SmithingMaterialItem::new, new Item.Properties().stacksTo(64));
    public static final Item MAIL = registerItem("mail",
            Item::new, new Item.Properties().stacksTo(16));
    public static final Item SCALE = registerItem("scale",
            Item::new, new Item.Properties().stacksTo(64));
    public static final Item SCALE_MAIL = registerItem("scale_mail",
            SmithingMaterialItem::new, new Item.Properties().stacksTo(16));
    public static final Item ARMOR_PLATE = registerItem("armor_plate",
            SmithingMaterialItem::new, new Item.Properties().stacksTo(16));

    public static final Item HELMET_PLATE = registerItem("helmet_plate",
            SmithingMaterialItem::new, new Item.Properties().stacksTo(16));
    public static final Item SHIELD_BORDER = registerItem("shield_border",
            SmithingMaterialItem::new, new Item.Properties().stacksTo(16));
    public static final Item SHIELD_PLATE = registerItem("shield_plate",
            SmithingMaterialItem::new, new Item.Properties().stacksTo(16));

    public static final Item BRIGAND_KEY = registerItem("brigand_key",
            Item::new, new Item.Properties());
    public static final Item DWARVEN_KEY = registerItem("dwarven_key",
            Item::new, new Item.Properties().stacksTo(1));

    public static final Item PIPEWEED_BANNER_PATTERN = registerItem("pipeweed_banner_pattern",
            (settings) -> new BannerPatternItem(BannerPatternTagsME.PIPEWEED_PATTERN_ITEM, settings),
            new Item.Properties().stacksTo(1).rarity(Rarity.RARE));
    public static final Item GONDOR_BANNER_PATTERN = registerItem("gondor_banner_pattern",
            (settings) -> new BannerPatternItem(BannerPatternTagsME.GONDOR_PATTERN_ITEM, settings),
            new Item.Properties().stacksTo(1).rarity(Rarity.RARE));
    public static final Item ROHAN_BANNER_PATTERN = registerItem("rohan_banner_pattern",
            (settings) -> new BannerPatternItem(BannerPatternTagsME.ROHAN_PATTERN_ITEM, settings),
            new Item.Properties().stacksTo(1).rarity(Rarity.RARE));
    public static final Item LOTHLORIEN_BANNER_PATTERN = registerItem("lothlorien_banner_pattern",
            (settings) -> new BannerPatternItem(BannerPatternTagsME.LOTHLORIEN_PATTERN_ITEM, settings),
            new Item.Properties().stacksTo(1).rarity(Rarity.RARE));
    public static final Item MORDOR_BANNER_PATTERN = registerItem("mordor_banner_pattern",
            (settings) -> new BannerPatternItem(BannerPatternTagsME.MORDOR_PATTERN_ITEM, settings),
            new Item.Properties().stacksTo(1).rarity(Rarity.RARE));
    public static final Item MISTY_MOUNTAINS_ORCS_BANNER_PATTERN = registerItem("misty_mountains_orcs_banner_pattern",
            (settings) -> new BannerPatternItem(BannerPatternTagsME.MISTY_MOUNTAINS_ORCS_PATTERN_ITEM, settings),
            new Item.Properties().stacksTo(1).rarity(Rarity.RARE));
    public static final Item ISENGARD_BANNER_PATTERN = registerItem("isengard_banner_pattern",
            (settings) -> new BannerPatternItem(BannerPatternTagsME.ISENGARD_PATTERN_ITEM, settings),
            new Item.Properties().stacksTo(1).rarity(Rarity.RARE));

    public static final Item SCREECHING_SKULL_BANNER_PATTERN = registerItem("screeching_skull_banner_pattern",
            (settings) -> new BannerPatternItem(BannerPatternTagsME.SCREECHING_SKULL_PATTERN_ITEM, settings),
            new Item.Properties().stacksTo(1).rarity(Rarity.RARE));
    public static final Item GOBLIN_SKULL_BANNER_PATTERN = registerItem("goblin_skull_banner_pattern",
            (settings) -> new BannerPatternItem(BannerPatternTagsME.GOBLIN_SKULL_PATTERN_ITEM, settings),
            new Item.Properties().stacksTo(1).rarity(Rarity.RARE));

    public static final Item ANVIL_BANNER_PATTERN = registerItem("anvil_banner_pattern",
            (settings) -> new BannerPatternItem(BannerPatternTagsME.ANVIL_PATTERN_ITEM, settings),
            new Item.Properties().stacksTo(1).rarity(Rarity.RARE));
    public static final Item BELL_BANNER_PATTERN = registerItem("bell_banner_pattern",
            (settings) -> new BannerPatternItem(BannerPatternTagsME.BELL_PATTERN_ITEM, settings),
            new Item.Properties().stacksTo(1).rarity(Rarity.RARE));
    public static final Item BOW_BANNER_PATTERN = registerItem("bow_banner_pattern",
            (settings) -> new BannerPatternItem(BannerPatternTagsME.BOW_PATTERN_ITEM, settings),
            new Item.Properties().stacksTo(1).rarity(Rarity.RARE));

    public static final Item DWARF_CROWN_BANNER_PATTERN = registerItem("dwarf_crown_banner_pattern",
            (settings) -> new BannerPatternItem(BannerPatternTagsME.DWARF_CROWN_PATTERN_ITEM, settings),
            new Item.Properties().stacksTo(1).rarity(Rarity.RARE));

    public static final Item SPIDER_BANNER_PATTERN = registerItem("spider_banner_pattern",
            (settings) -> new BannerPatternItem(BannerPatternTagsME.SPIDER_PATTERN_ITEM, settings),
            new Item.Properties().stacksTo(1).rarity(Rarity.RARE));

    public static final Item GREAT_HORN_BANNER_PATTERN = registerItem("great_horn_banner_pattern",
            (settings) -> new BannerPatternItem(BannerPatternTagsME.GREAT_HORN_PATTERN_ITEM, settings),
            new Item.Properties().stacksTo(1).rarity(Rarity.RARE));
    public static final Item OAK_LEAF_BANNER_PATTERN = registerItem("oak_leaf_banner_pattern",
            (settings) -> new BannerPatternItem(BannerPatternTagsME.OAK_LEAF_PATTERN_ITEM, settings),
            new Item.Properties().stacksTo(1).rarity(Rarity.RARE));

    public static final Item ANTLERS_BANNER_PATTERN = registerItem("antlers_banner_pattern",
            (settings) -> new BannerPatternItem(BannerPatternTagsME.ANTLERS_PATTERN_ITEM, settings),
            new Item.Properties().stacksTo(1).rarity(Rarity.EPIC));
    public static final Item DRAGON_BANNER_PATTERN = registerItem("dragon_banner_pattern",
            (settings) -> new BannerPatternItem(BannerPatternTagsME.DRAGON_PATTERN_ITEM, settings),
            new Item.Properties().stacksTo(1).rarity(Rarity.EPIC));
    public static final Item SNAIL_BANNER_PATTERN = registerItem("snail_banner_pattern",
            (settings) -> new BannerPatternItem(BannerPatternTagsME.SNAIL_PATTERN_ITEM, settings),
            new Item.Properties().stacksTo(1).rarity(Rarity.EPIC));

    public static final Item PTEROSAUR_NUGGET = registerHiddenItem("pterosaur_nugget",
            HotChickenFoodItem::new, new Item.Properties());
    public static final Item THERAPOD_NUGGET = registerHiddenItem("therapod_nugget",
            HotChickenFoodItem::new, new Item.Properties());
    public static final Item CERATOPSIAN_NUGGET = registerHiddenItem("ceratopsian_nugget",
            HotChickenFoodItem::new, new Item.Properties());
    public static final Item THYREOPHORAN_NUGGET = registerHiddenItem("thyreophoran_nugget",
            HotChickenFoodItem::new, new Item.Properties());
    public static final Item SAUROPOD_NUGGET = registerHiddenItem("sauropod_nugget",
            HotChickenFoodItem::new, new Item.Properties().stacksTo(16));

    private static Item registerItem(String name, Function<Item.Properties, Item> factory, Item.Properties settings) {
        Item item = factory.apply(settings);
        ItemGroupsME.RESOURCES_CONTENTS.add(item.getDefaultInstance());
        TranslationEntries.itemEntries.add(item);
        RegistryAliasesME.aliases.add(new RegistryAliasesME.Alias(BuiltInRegistries.ITEM, name));
        return RegistrationBridge.register(BuiltInRegistries.ITEM, ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, name), item);
    }
    private static Item registerHiddenItem(String name, Function<Item.Properties, Item> factory, Item.Properties settings) {
        Item item = factory.apply(settings);
        TranslationEntries.itemEntries.add(item);
        RegistryAliasesME.aliases.add(new RegistryAliasesME.Alias(BuiltInRegistries.ITEM, name));
        return RegistrationBridge.register(BuiltInRegistries.ITEM, ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, name), item);
    }

    private static Item registerBundle(String name, DyeColor color) {
        return registerItem(
                name,
                properties -> new ColoredBundleItem(color, properties),
                new Item.Properties()
                        .stacksTo(1)
                        .component(DataComponents.BUNDLE_CONTENTS, BundleContents.EMPTY)
        );
    }

    public static void registerModItems() {
        MiddleEarth.LOGGER.logDebugMsg("Registering Mod Resource Items for " + MiddleEarth.MOD_ID);
    }
}
