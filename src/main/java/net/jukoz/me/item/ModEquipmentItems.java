package net.jukoz.me.item;

import net.jukoz.me.MiddleEarth;
import net.jukoz.me.datageneration.content.models.SimpleDyeableItemModel;
import net.jukoz.me.datageneration.content.models.SimpleItemModel;
import net.jukoz.me.item.dataComponents.CapeDataComponent;
import net.jukoz.me.item.dataComponents.CustomDyeableDataComponent;
import net.jukoz.me.item.dataComponents.HoodDataComponent;
import net.jukoz.me.item.items.armor.*;
import net.jukoz.me.item.items.shields.CustomShieldItem;
import net.jukoz.me.item.utils.ModItemGroups;
import net.jukoz.me.item.utils.ModShieldTypes;
import net.jukoz.me.item.utils.armor.ModArmorMaterials;
import net.jukoz.me.item.utils.armor.capes.ModCapes;
import net.jukoz.me.item.utils.armor.hoods.ModHoods;
import net.jukoz.me.utils.LoggerUtil;
import net.jukoz.me.utils.ModFactions;
import net.jukoz.me.utils.ModSubFactions;
import net.minecraft.item.AnimalArmorItem;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterials;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

public class ModEquipmentItems {

    public static List<Item> armorPiecesListHelmets = new ArrayList<>();
    public static List<Item> armorPiecesListChestplates = new ArrayList<>();
    public static List<Item> armorPiecesListLeggings = new ArrayList<>();
    public static List<Item> armorPiecesListBoots = new ArrayList<>();

    public static List<Item> capes = new ArrayList<>();
    public static List<Item> hoods = new ArrayList<>();

    //region GENERIC
    //Hoods
    public static final Item HOOD = registerDyeableHood("hood",
            new HoodHelmetItem(new Item.Settings()
                    .component(ModDataComponentTypes.HOOD_DATA, HoodDataComponent.newHood(ModHoods.HOOD))
                    .component(ModDataComponentTypes.DYE_DATA, new CustomDyeableDataComponent(5394247)),
                    ModFactions.NONE));

    public static final Item BLACK_FUR_HOOD = registerDyeableHood("black_fur_hood",
            new HoodHelmetItem(new Item.Settings()
                    .component(ModDataComponentTypes.HOOD_DATA, HoodDataComponent.newHood(ModHoods.BLACK_FUR_HOOD))
                    .component(ModDataComponentTypes.DYE_DATA, new CustomDyeableDataComponent(5394247)),
                    ModFactions.NONE));
    public static final Item BROWN_FUR_HOOD = registerDyeableHood("brown_fur_hood",
            new HoodHelmetItem(new Item.Settings()
                    .component(ModDataComponentTypes.HOOD_DATA, HoodDataComponent.newHood(ModHoods.BROWN_FUR_HOOD))
                    .component(ModDataComponentTypes.DYE_DATA, new CustomDyeableDataComponent(5394247)),
                    ModFactions.NONE));
    public static final Item GRAY_FUR_HOOD = registerDyeableHood("gray_fur_hood",
            new HoodHelmetItem(new Item.Settings()
                    .component(ModDataComponentTypes.HOOD_DATA, HoodDataComponent.newHood(ModHoods.GRAY_FUR_HOOD))
                    .component(ModDataComponentTypes.DYE_DATA, new CustomDyeableDataComponent(5394247)),
                    ModFactions.NONE));
    public static final Item TAN_FUR_HOOD = registerDyeableHood("tan_fur_hood",
            new HoodHelmetItem(new Item.Settings()
                    .component(ModDataComponentTypes.HOOD_DATA, HoodDataComponent.newHood(ModHoods.TAN_FUR_HOOD))
                    .component(ModDataComponentTypes.DYE_DATA, new CustomDyeableDataComponent(5394247)),
                    ModFactions.NONE));
    public static final Item WHITE_FUR_HOOD = registerDyeableHood("white_fur_hood",
            new HoodHelmetItem(new Item.Settings()
                    .component(ModDataComponentTypes.HOOD_DATA, HoodDataComponent.newHood(ModHoods.WHITE_FUR_HOOD))
                    .component(ModDataComponentTypes.DYE_DATA, new CustomDyeableDataComponent(5394247)),
                    ModFactions.NONE));

    //capes
    public static final Item CAPE = registerDyeableCape("cape",
            new CapeChestplateItem(new Item.Settings()
                    .component(ModDataComponentTypes.CAPE_DATA, CapeDataComponent.newCape(ModCapes.CAPE))
                    .component(ModDataComponentTypes.DYE_DATA, new CustomDyeableDataComponent(5394247)),
                    ModFactions.NONE));
    public static final Item SURCOAT = registerDyeableCape("surcoat",
            new CapeChestplateItem(new Item.Settings()
                    .component(ModDataComponentTypes.CAPE_DATA, CapeDataComponent.newCape(ModCapes.SURCOAT))
                    .component(ModDataComponentTypes.DYE_DATA, new CustomDyeableDataComponent(5394247)),
                    ModFactions.NONE));
    public static final Item CLOAK = registerDyeableCape("cloak",
            new CapeChestplateItem(new Item.Settings()
                    .component(ModDataComponentTypes.CAPE_DATA, CapeDataComponent.newCape(ModCapes.CLOAK))
                    .component(ModDataComponentTypes.DYE_DATA, new CustomDyeableDataComponent(5394247)),
                    ModFactions.NONE));

    public static final Item BLACK_FUR_CLOAK = registerDyeableCape("black_fur_cloak",
            new CapeChestplateItem(new Item.Settings()
                    .component(ModDataComponentTypes.CAPE_DATA, CapeDataComponent.newCape(ModCapes.BLACK_FUR_CLOAK))
                    .component(ModDataComponentTypes.DYE_DATA, new CustomDyeableDataComponent(5394247)),
                    ModFactions.NONE));
    public static final Item BROWN_FUR_CLOAK = registerDyeableCape("brown_fur_cloak",
            new CapeChestplateItem(new Item.Settings()
                    .component(ModDataComponentTypes.CAPE_DATA, CapeDataComponent.newCape(ModCapes.BROWN_FUR_CLOAK))
                    .component(ModDataComponentTypes.DYE_DATA, new CustomDyeableDataComponent(5394247)),
                    ModFactions.NONE));
    public static final Item GRAY_FUR_CLOAK = registerDyeableCape("gray_fur_cloak",
            new CapeChestplateItem(new Item.Settings()
                    .component(ModDataComponentTypes.CAPE_DATA, CapeDataComponent.newCape(ModCapes.GRAY_FUR_CLOAK))
                    .component(ModDataComponentTypes.DYE_DATA, new CustomDyeableDataComponent(5394247)),
                    ModFactions.NONE));
    public static final Item TAN_FUR_CLOAK = registerDyeableCape("tan_fur_cloak",
            new CapeChestplateItem(new Item.Settings()
                    .component(ModDataComponentTypes.CAPE_DATA, CapeDataComponent.newCape(ModCapes.TAN_FUR_CLOAK))
                    .component(ModDataComponentTypes.DYE_DATA, new CustomDyeableDataComponent(5394247)),
                    ModFactions.NONE));
    public static final Item WHITE_FUR_CLOAK = registerDyeableCape("white_fur_cloak",
            new CapeChestplateItem(new Item.Settings()
                    .component(ModDataComponentTypes.CAPE_DATA, CapeDataComponent.newCape(ModCapes.WHITE_FUR_CLOAK))
                    .component(ModDataComponentTypes.DYE_DATA, new CustomDyeableDataComponent(5394247)),
                    ModFactions.NONE));

    public static final Item BLACK_FUR = registerCape("black_fur",
            new CapeChestplateItem(new Item.Settings()
                    .component(ModDataComponentTypes.CAPE_DATA, CapeDataComponent.newCape(ModCapes.BLACK_FUR)),
                    ModFactions.NONE));
    public static final Item BROWN_FUR = registerCape("brown_fur",
            new CapeChestplateItem(new Item.Settings()
                    .component(ModDataComponentTypes.CAPE_DATA, CapeDataComponent.newCape(ModCapes.BROWN_FUR)),
                    ModFactions.NONE));
    public static final Item GRAY_FUR = registerCape("gray_fur",
            new CapeChestplateItem(new Item.Settings()
                    .component(ModDataComponentTypes.CAPE_DATA, CapeDataComponent.newCape(ModCapes.GRAY_FUR)),
                    ModFactions.NONE));
    public static final Item TAN_FUR = registerCape("tan_fur",
            new CapeChestplateItem(new Item.Settings()
                    .component(ModDataComponentTypes.CAPE_DATA, CapeDataComponent.newCape(ModCapes.TAN_FUR)),
                    ModFactions.NONE));
    public static final Item WHITE_FUR = registerCape("white_fur",
            new CapeChestplateItem(new Item.Settings()
                    .component(ModDataComponentTypes.CAPE_DATA, CapeDataComponent.newCape(ModCapes.WHITE_FUR)),
                    ModFactions.NONE));

    public static final Item STRAW_HAT = registerCustomModelArmorPiece("straw_hat",
            new CustomHelmetItem(ModArmorMaterials.STRAW_T1, new Item.Settings(),
                    ModFactions.NONE));

    public static final Item WOVEN_HAT = registerCustomModelArmorPiece("woven_hat",
            new CustomHelmetItem(ModArmorMaterials.LEATHER_T1, new Item.Settings(),
                    ModFactions.NONE));

    public static final Item BYCOCKET = registerDyeableArmorPiece("bycocket",
            new CustomHelmetItem(ModArmorMaterials.LEATHER_T1, new Item.Settings()
                    .component(ModDataComponentTypes.DYE_DATA, new CustomDyeableDataComponent(-6265536)),
                    ModFactions.NONE));

    public static final Item ARMING_COAT = registerDyeableArmorPiece("arming_coat",
            new CustomChestplateItem(ModArmorMaterials.LEATHER_T1, new Item.Settings()
                    .component(ModDataComponentTypes.DYE_DATA, new CustomDyeableDataComponent(15256475)),
                    ModFactions.NONE));

    public static final Item ARMING_SKIRT = registerDyeableArmorPiece("arming_skirt",
            new CustomLeggingsItem(ModArmorMaterials.LEATHER_T1, new Item.Settings()
                    .component(ModDataComponentTypes.DYE_DATA, new CustomDyeableDataComponent(15256475)),
                    ModFactions.NONE));

    public static final Item SHOES = registerArmorPiece("shoes",
            new CustomBootsItem(ModArmorMaterials.LEATHER_T1, new Item.Settings(),
                    ModFactions.NONE));

    public static final Item WORK_SHOES = registerArmorPiece("work_shoes",
            new CustomBootsItem(ModArmorMaterials.LEATHER_T1, new Item.Settings(),
                    ModFactions.NONE));

    public static final Item LEATHER_SKULLCAP = registerDyeableArmorPiece("leather_skullcap",
            new CustomHelmetItem(ModArmorMaterials.LEATHER_T2, new Item.Settings()
                    .component(ModDataComponentTypes.DYE_DATA, new CustomDyeableDataComponent(-6265536)),
                    ModFactions.NONE));

    public static final Item GAMBESON_CAP = registerDyeableArmorPiece("gambeson_cap",
            new CustomHelmetItem(ModArmorMaterials.LEATHER_T2, new Item.Settings()
                    .component(ModDataComponentTypes.DYE_DATA, new CustomDyeableDataComponent(15256475)),
                    ModFactions.NONE));
    public static final Item GAMBESON_COWL = registerDyeableArmorPiece("gambeson_cowl",
            new CustomHelmetItem(ModArmorMaterials.LEATHER_T2, new Item.Settings()
                    .component(ModDataComponentTypes.DYE_DATA, new CustomDyeableDataComponent( 15256475)),
                    ModFactions.NONE));

    public static final Item GAMBESON = registerDyeableArmorPiece("gambeson",
            new CustomChestplateItem(ModArmorMaterials.LEATHER_T2, new Item.Settings()
                    .component(ModDataComponentTypes.DYE_DATA, new CustomDyeableDataComponent(15256475)),
                    ModFactions.NONE));

    public static final Item LEATHER_VEST = registerDyeableArmorPiece("leather_vest",
            new CustomChestplateItem(ModArmorMaterials.LEATHER_T2, new Item.Settings()
                    .component(ModDataComponentTypes.DYE_DATA, new CustomDyeableDataComponent(-6265536)),
                    ModFactions.NONE));

    public static final Item LEATHER_SCALE_VEST = registerDyeableArmorPiece("leather_scale_vest",
            new CustomChestplateItem(ModArmorMaterials.LEATHER_T2, new Item.Settings()
                    .component(ModDataComponentTypes.DYE_DATA, new CustomDyeableDataComponent(-6265536)),
                    ModFactions.NONE));

    public static final Item STURDY_BOOTS = registerArmorPiece("sturdy_boots",
            new CustomBootsItem(ModArmorMaterials.LEATHER_T2, new Item.Settings(),
                    ModFactions.NONE));
    public static final Item TRAVELLING_BOOTS = registerArmorPiece("travelling_boots",
            new CustomBootsItem(ModArmorMaterials.LEATHER_T2, new Item.Settings(),
                    ModFactions.NONE));
    public static final Item HIGH_CUT_BOOTS = registerArmorPiece("high_cut_boots",
            new CustomBootsItem(ModArmorMaterials.LEATHER_T2, new Item.Settings(),
                    ModFactions.NONE));

    public static final Item CHAIN_COIF = registerArmorPiece("chain_coif",
            new CustomHelmetItem(ModArmorMaterials.IRON_T3, new Item.Settings(),
                    ModFactions.NONE));

    public static final Item KETTLE_HAT = registerCustomModelArmorPiece("kettle_hat",
            new CustomHelmetItem(ModArmorMaterials.IRON_T3, new Item.Settings(),
                    ModFactions.NONE));

    public static final Item OPEN_FACE_HELMET = registerArmorPiece("open_face_helmet",
            new CustomHelmetItem(ModArmorMaterials.IRON_T3, new Item.Settings(),
                    ModFactions.NONE));

    public static final Item CHAIN_HAUBERK = registerArmorPiece("chain_hauberk",
            new CustomChestplateItem(ModArmorMaterials.IRON_T3, new Item.Settings(),
                    ModFactions.NONE));

    public static final Item CHAIN_SKIRT = registerArmorPiece("chain_skirt",
            new CustomLeggingsItem(ModArmorMaterials.IRON_T3, new Item.Settings(),
                    ModFactions.NONE));

    public static final Item SALLET = registerCustomModelArmorPiece("sallet",
            new CustomHelmetItem(ModArmorMaterials.STEEL_T4, new Item.Settings(),
                    ModFactions.NONE));
    //endregion

    //region HOBBITS
    public static final Item SHIRRIFF_HAT = registerDyeableCustomModelArmorPiece("shirriff_hat",
            new CustomHelmetItem(ModArmorMaterials.LEATHER_T1, new Item.Settings()
                    .component(ModDataComponentTypes.DYE_DATA, new CustomDyeableDataComponent(-6265536)),
                    ModFactions.SHIRE));
    //endregion

    //region MEN
    //region GONDOR
    public static final Item GONDORIAN_BOOTS = registerArmorPiece("gondorian_boots",
            new CustomBootsItem(ModArmorMaterials.LEATHER_T2, new Item.Settings(),
                    ModFactions.GONDOR));

    public static final Item GONDORIAN_CABASSET_HELMET = registerCustomModelArmorPiece("gondorian_cabasset_helmet",
            new CustomHelmetItem(ModArmorMaterials.IRON_T3, new Item.Settings(),
                    ModFactions.GONDOR));

    public static final Item GONDORIAN_CHAIN_HAUBERK = registerArmorPiece("gondorian_chain_hauberk",
            new CustomChestplateItem(ModArmorMaterials.IRON_T3, new Item.Settings(),
                    ModFactions.GONDOR));
    public static final Item GONDORIAN_TABBARD = registerArmorPiece("gondorian_tabbard",
            new CustomChestplateItem(ModArmorMaterials.IRON_T3, new Item.Settings(),
                    ModFactions.GONDOR));
    public static final Item GONDORIAN_LEATHER_CUIRASS = registerDyeableArmorPiece("gondorian_leather_cuirass",
            new CustomChestplateItem(ModArmorMaterials.IRON_T3, new Item.Settings()
                    .component(ModDataComponentTypes.DYE_DATA, new CustomDyeableDataComponent(-6265536)),
                    ModFactions.GONDOR));
    public static final Item GONDORIAN_LEATHER_CHESTPLATE = registerDyeableArmorPiece("gondorian_leather_chestplate",
            new CustomChestplateItem(ModArmorMaterials.IRON_T3, new Item.Settings()
                    .component(ModDataComponentTypes.DYE_DATA, new CustomDyeableDataComponent(-6265536)),
                    ModFactions.GONDOR));

    public static final Item GONDORIAN_CHAIN_COAT = registerArmorPiece("gondorian_chain_coat",
            new CustomLeggingsItem(ModArmorMaterials.IRON_T3, new Item.Settings(),
                    ModFactions.GONDOR));

    public static final Item GONDORIAN_SOLDIER_HELMET = registerCustomModelArmorPiece("gondorian_soldier_helmet",
            new CustomHelmetItem(ModArmorMaterials.STEEL_T4, new Item.Settings(),
                    ModFactions.GONDOR));

    public static final Item GONDORIAN_SOLDIER_CHESTPLATE = registerArmorPiece("gondorian_soldier_chestplate",
            new CustomChestplateItem(ModArmorMaterials.STEEL_T4, new Item.Settings(),
                    ModFactions.GONDOR));

    public static final Item GONDORIAN_PLATE_HELMET = registerCustomModelArmorPiece("gondorian_plate_helmet",
            new CustomHelmetItem(ModArmorMaterials.STEEL_T5, new Item.Settings(),
                    ModFactions.GONDOR));
    public static final Item GONDORIAN_PLATE_CHESTPLATE = registerCustomModelArmorPiece("gondorian_plate_chestplate",
            new CustomChestplateItem(ModArmorMaterials.STEEL_T5, new Item.Settings(),
                    ModFactions.GONDOR));
    public static final Item GONDORIAN_PLATE_LEGGINGS = registerArmorPiece("gondorian_plate_leggings",
            new CustomLeggingsItem(ModArmorMaterials.STEEL_T5, new Item.Settings(),
                    ModFactions.GONDOR));
    public static final Item GONDORIAN_PLATE_BOOTS = registerArmorPiece("gondorian_plate_boots",
            new CustomBootsItem(ModArmorMaterials.STEEL_T5, new Item.Settings(),
                    ModFactions.GONDOR));

    public static final Item GONDORIAN_CAPTAIN_HELMET = registerCustomModelArmorPiece("gondorian_captain_helmet",
            new CustomHelmetItem(ModArmorMaterials.STEEL_T5, new Item.Settings(),
                    ModFactions.GONDOR));

    public static final Item GONDORIAN_KINGS_GUARD_HELMET = registerCustomModelArmorPiece("gondorian_kings_guard_helmet",
            new CustomHelmetItem(ModArmorMaterials.STEEL_T5, new Item.Settings(),
                    ModFactions.GONDOR));
    public static final Item GONDORIAN_KINGS_GUARD_CHESTKPLATE = registerCustomModelArmorPiece("gondorian_kings_guard_chestplate",
            new CustomChestplateItem(ModArmorMaterials.STEEL_T5, new Item.Settings()
                        .component(ModDataComponentTypes.CAPE_DATA, CapeDataComponent.newCape(ModCapes.GONDORIAN_KINGS_GUARD_CAPE)),
                    ModFactions.GONDOR));
    public static final Item GONDORIAN_KINGS_GUARD_LEGGINGS = registerArmorPiece("gondorian_kings_guard_leggings",
            new CustomLeggingsItem(ModArmorMaterials.STEEL_T5, new Item.Settings(),
                    ModFactions.GONDOR));
    public static final Item GONDORIAN_KINGS_GUARD_BOOTS = registerArmorPiece("gondorian_kings_guard_boots",
            new CustomBootsItem(ModArmorMaterials.STEEL_T5, new Item.Settings(),
                    ModFactions.GONDOR));

    public static final Item GONDORIAN_CITADEL_GUARD_HELMET = registerCustomModelArmorPiece("gondorian_citadel_guard_helmet",
            new CustomHelmetItem(ModArmorMaterials.STEEL_T5, new Item.Settings()
                    .component(ModDataComponentTypes.HOOD_DATA, HoodDataComponent.newHoodwithState(true, ModHoods.GONDORIAN_CITADEL_GUARD_HOOD)),
                    ModFactions.GONDOR));
    public static final Item GONDORIAN_CITADEL_GUARD_CHESTPLATE = registerArmorPiece("gondorian_citadel_guard_chestplate",
            new CustomChestplateItem(ModArmorMaterials.STEEL_T5, new Item.Settings()
                    .component(ModDataComponentTypes.CAPE_DATA, CapeDataComponent.newCape(ModCapes.GONDORIAN_CITADEL_GUARD_CAPE)),
                    ModFactions.GONDOR));
    public static final Item GONDORIAN_CITADEL_GUARD_LEGGINGS = registerArmorPiece("gondorian_citadel_guard_leggings",
            new CustomLeggingsItem(ModArmorMaterials.STEEL_T5, new Item.Settings(),
                    ModFactions.GONDOR));
    public static final Item GONDORIAN_CITADEL_GUARD_BOOTS = registerArmorPiece("gondorian_citadel_guard_boots",
            new CustomBootsItem(ModArmorMaterials.STEEL_T5, new Item.Settings(),
                    ModFactions.GONDOR));

    public static final Item GONDORIAN_FOUNTAIN_GUARD_HELMET = registerCustomModelArmorPiece("gondorian_fountain_guard_helmet",
            new CustomHelmetItem(ModArmorMaterials.STEEL_T5, new Item.Settings(),
                    ModFactions.GONDOR));
    public static final Item GONDORIAN_FOUNTAIN_GUARD_CHESTPLATE = registerCustomModelArmorPiece("gondorian_fountain_guard_chestplate",
            new CustomChestplateItem(ModArmorMaterials.STEEL_T5, new Item.Settings()
                    .component(ModDataComponentTypes.CAPE_DATA, CapeDataComponent.newCape(ModCapes.GONDORIAN_FOUNTAIN_GUARD_CAPE)),
                    ModFactions.GONDOR));
    public static final Item GONDORIAN_FOUNTAIN_GUARD_LEGGINGS = registerArmorPiece("gondorian_fountain_guard_leggings",
            new CustomLeggingsItem(ModArmorMaterials.STEEL_T5, new Item.Settings(),
                    ModFactions.GONDOR));
    public static final Item GONDORIAN_FOUNTAIN_GUARD_BOOTS = registerArmorPiece("gondorian_fountain_guard_boots",
            new CustomBootsItem(ModArmorMaterials.STEEL_T5, new Item.Settings(),
                    ModFactions.GONDOR));

    public static final Item GONDORIAN_CITADEL_GUARD_HOOD = registerHood("gondorian_citadel_guard_hood",
            new HoodHelmetItem(new Item.Settings()
                    .component(ModDataComponentTypes.HOOD_DATA, HoodDataComponent.newHoodwithState(true, ModHoods.GONDORIAN_CITADEL_GUARD_HOOD)),
                    ModFactions.GONDOR));

    public static final Item GONDORIAN_CAPTAIN_CAPE = registerCape("gondorian_captain_cape",
            new CapeChestplateItem(new Item.Settings()
                    .component(ModDataComponentTypes.CAPE_DATA, CapeDataComponent.newCape(ModCapes.GONDORIAN_CAPTAIN_CAPE)),
                    ModFactions.GONDOR));
    public static final Item GONDORIAN_HERO_CAPE = registerCape("gondorian_hero_cape",
            new CapeChestplateItem(new Item.Settings()
                    .component(ModDataComponentTypes.CAPE_DATA, CapeDataComponent.newCape(ModCapes.GONDORIAN_HERO_CAPE)),
                    ModFactions.GONDOR));
    public static final Item GONDORIAN_KINGS_GUARD_CAPE = registerCape("gondorian_kings_guard_cape",
            new CapeChestplateItem(new Item.Settings()
                    .component(ModDataComponentTypes.CAPE_DATA, CapeDataComponent.newCape(ModCapes.GONDORIAN_KINGS_GUARD_CAPE)),
                    ModFactions.GONDOR));
    public static final Item GONDORIAN_CITADEL_GUARD_CAPE = registerCape("gondorian_citadel_guard_cape",
            new CapeChestplateItem(new Item.Settings()
                    .component(ModDataComponentTypes.CAPE_DATA, CapeDataComponent.newCape(ModCapes.GONDORIAN_CITADEL_GUARD_CAPE)),
                    ModFactions.GONDOR));
    public static final Item GONDORIAN_FOUNTAIN_GUARD_CAPE = registerCape("gondorian_fountain_guard_cape",
            new CapeChestplateItem(new Item.Settings()
                    .component(ModDataComponentTypes.CAPE_DATA, CapeDataComponent.newCape(ModCapes.GONDORIAN_FOUNTAIN_GUARD_CAPE)),
                    ModFactions.GONDOR));

    public static final Item GONDORIAN_HORSE_ARMOR = registerGeneratedItem("gondorian_horse_armor",
            new CustomHorseArmorItem(ArmorMaterials.IRON, AnimalArmorItem.Type.EQUESTRIAN, false, new Item.Settings().maxCount(1), ModFactions.GONDOR));

    //endregion

    //region ROHAN
    public static final Item ROHIRRIC_COAT = registerDyeableArmorPiece("rohirric_coat",
            new CustomChestplateItem(ModArmorMaterials.LEATHER_T1, new Item.Settings()
                    .component(ModDataComponentTypes.DYE_DATA, new CustomDyeableDataComponent(15256475))
                    .component(ModDataComponentTypes.CAPE_DATA, CapeDataComponent.newCape(ModCapes.ROHIRRIC_CAPE)),
                    ModFactions.ROHAN));
    public static final Item ROHIRRIC_REINFORCED_COAT = registerDyeableArmorPiece("rohirric_reinforced_coat",
            new CustomChestplateItem(ModArmorMaterials.LEATHER_T1, new Item.Settings()
                    .component(ModDataComponentTypes.DYE_DATA, new CustomDyeableDataComponent( 15256475))
                    .component(ModDataComponentTypes.CAPE_DATA, CapeDataComponent.newCape(ModCapes.ROHIRRIC_CAPE)),
                    ModFactions.ROHAN));

    public static final Item ROHIRRIC_LEATHER_HELMET = registerArmorPiece("rohirric_leather_helmet",
            new CustomHelmetItem(ModArmorMaterials.LEATHER_T2, new Item.Settings(),
                    ModFactions.ROHAN));
    public static final Item ROHIRRIC_REINFORCED_LEATHER_HELMET = registerCustomModelArmorPiece("rohirric_reinforced_leather_helmet",
            new CustomHelmetItem(ModArmorMaterials.LEATHER_T2, new Item.Settings(),
                    ModFactions.ROHAN));
    public static final Item ROHIRRIC_ORNAMENTED_LEATHER_HELMET = registerArmorPiece("rohirric_ornamented_leather_helmet",
            new CustomHelmetItem(ModArmorMaterials.LEATHER_T2, new Item.Settings(),
                    ModFactions.ROHAN));

    public static final Item ROHIRRIC_LEATHER_VEST = registerDyeableArmorPiece("rohirric_leather_vest",
            new CustomChestplateItem(ModArmorMaterials.LEATHER_T2, new Item.Settings()
                    .component(ModDataComponentTypes.DYE_DATA, new CustomDyeableDataComponent(15256475))
                    .component(ModDataComponentTypes.CAPE_DATA, CapeDataComponent.newCape(ModCapes.ROHIRRIC_CAPE)),
                    ModFactions.ROHAN));
    public static final Item ROHIRRIC_LEATHER_SCALE_VEST = registerDyeableArmorPiece("rohirric_leather_scale_vest",
            new CustomChestplateItem(ModArmorMaterials.LEATHER_T2, new Item.Settings()
                    .component(ModDataComponentTypes.DYE_DATA, new CustomDyeableDataComponent(15256475))
                    .component(ModDataComponentTypes.CAPE_DATA, CapeDataComponent.newCape(ModCapes.ROHIRRIC_CAPE)),
                    ModFactions.ROHAN));
    public static final Item ROHIRRIC_GAMBESON = registerDyeableArmorPiece("rohirric_gambeson",
            new CustomChestplateItem(ModArmorMaterials.LEATHER_T2, new Item.Settings()
                    .component(ModDataComponentTypes.DYE_DATA, new CustomDyeableDataComponent(15256475))
                    .component(ModDataComponentTypes.CAPE_DATA, CapeDataComponent.newCape(ModCapes.ROHIRRIC_CAPE)),
                    ModFactions.ROHAN));

    public static final Item ROHIRRIC_MILITIA_HELMET = registerArmorPiece("rohirric_militia_helmet",
            new CustomHelmetItem(ModArmorMaterials.IRON_T3, new Item.Settings(),
                    ModFactions.ROHAN));
    public static final Item ROHIRRIC_BRACED_MILITIA_HELMET = registerCustomModelArmorPiece("rohirric_braced_militia_helmet",
            new CustomHelmetItem(ModArmorMaterials.IRON_T3, new Item.Settings(),
                    ModFactions.ROHAN));
    public static final Item ROHIRRIC_REINFORCED_MILITIA_HELMET = registerArmorPiece("rohirric_reinforced_militia_helmet",
            new CustomHelmetItem(ModArmorMaterials.IRON_T3, new Item.Settings(),
                    ModFactions.ROHAN));
    public static final Item ROHIRRIC_ORNAMENTED_MILITIA_HELMET = registerCustomModelArmorPiece("rohirric_ornamented_militia_helmet",
            new CustomHelmetItem(ModArmorMaterials.IRON_T3, new Item.Settings(),
                    ModFactions.ROHAN));

    public static final Item ROHIRRIC_MAIL_SHIRT = registerDyeableArmorPiece("rohirric_mail_shirt",
            new CustomChestplateItem(ModArmorMaterials.IRON_T3, new Item.Settings()
                    .component(ModDataComponentTypes.DYE_DATA, new CustomDyeableDataComponent(15256475))
                    .component(ModDataComponentTypes.CAPE_DATA, CapeDataComponent.newCape(ModCapes.ROHIRRIC_CAPE)),
                    ModFactions.ROHAN));
    public static final Item ROHIRRIC_MAIL_SHIRT_OPEN = registerDyeableArmorPiece("rohirric_mail_shirt_open",
            new CustomChestplateItem(ModArmorMaterials.IRON_T3, new Item.Settings()
                    .component(ModDataComponentTypes.DYE_DATA, new CustomDyeableDataComponent(15256475))
                    .component(ModDataComponentTypes.CAPE_DATA, CapeDataComponent.newCape(ModCapes.ROHIRRIC_CAPE)),
                    ModFactions.ROHAN));
    public static final Item ROHIRRIC_MAIL_HAUBERK = registerArmorPiece("rohirric_mail_hauberk",
            new CustomChestplateItem(ModArmorMaterials.IRON_T3, new Item.Settings()
                    .component(ModDataComponentTypes.CAPE_DATA, CapeDataComponent.newCape(ModCapes.ROHIRRIC_CAPE)),
                    ModFactions.ROHAN));

    public static final Item ROHIRRIC_SOLDIER_HELMET = registerArmorPiece("rohirric_soldier_helmet",
            new CustomHelmetItem(ModArmorMaterials.STEEL_T4, new Item.Settings(),
                    ModFactions.ROHAN));
    public static final Item ROHIRRIC_ORNAMENTED_SOLDIER_HELMET = registerCustomModelArmorPiece("rohirric_ornamented_soldier_helmet",
            new CustomHelmetItem(ModArmorMaterials.STEEL_T4, new Item.Settings(),
                    ModFactions.ROHAN));

    public static final Item ROHIRRIC_ROYAL_GUARD_HELMET = registerCustomModelArmorPiece("rohirric_royal_guard_helmet",
            new CustomHelmetItem(ModArmorMaterials.STEEL_T4, new Item.Settings(),
                    ModFactions.ROHAN));

    public static final Item ROHIRRIC_SCALE_HAUBERK = registerArmorPiece("rohirric_scale_hauberk",
            new CustomChestplateItem(ModArmorMaterials.STEEL_T4, new Item.Settings(),
                    ModFactions.ROHAN));
    public static final Item ROHIRRIC_ORNAMENTED_SCALE_HAUBERK = registerArmorPiece("rohirric_ornamented_scale_hauberk",
            new CustomChestplateItem(ModArmorMaterials.STEEL_T4, new Item.Settings()
                    .component(ModDataComponentTypes.CAPE_DATA, CapeDataComponent.newCape(ModCapes.ROHIRRIC_ROYAL_GUARD_CAPE)),
                    ModFactions.ROHAN));

    public static final Item ROHIRRIC_SCALE_JACKET = registerArmorPiece("rohirric_scale_jacket",
            new CustomLeggingsItem(ModArmorMaterials.STEEL_T4, new Item.Settings(),
                    ModFactions.ROHAN));

    public static final Item EORLING_MARSHAL_HELMET = registerCustomModelArmorPiece("eorling_marshal_helmet",
            new CustomHelmetItem(ModArmorMaterials.STEEL_T5, new Item.Settings(),
                    ModFactions.ROHAN));
    public static final Item EORLING_MARSHAL_CHESTPLATE = registerArmorPiece("eorling_marshal_chestplate",
            new CustomChestplateItem(ModArmorMaterials.STEEL_T5, new Item.Settings()
                    .component(ModDataComponentTypes.CAPE_DATA, CapeDataComponent.newCape(ModCapes.EORLING_MARSHAL_CAPE)),
                    ModFactions.ROHAN));
    public static final Item EORLING_MARSHAL_LEGGINGS = registerArmorPiece("eorling_marshal_leggings",
            new CustomLeggingsItem(ModArmorMaterials.STEEL_T5, new Item.Settings(),
                    ModFactions.ROHAN));
    public static final Item EORLING_MARSHAL_BOOTS = registerArmorPiece("eorling_marshal_boots",
            new CustomBootsItem(ModArmorMaterials.STEEL_T5, new Item.Settings(),
                    ModFactions.ROHAN));

    public static final Item HORSE_LORD_HELMET = registerCustomModelArmorPiece("horse_lord_helmet",
            new CustomHelmetItem(ModArmorMaterials.STEEL_T5, new Item.Settings(),
                    ModFactions.ROHAN));
    public static final Item HORSE_LORD_CHESTPLATE = registerArmorPiece("horse_lord_chestplate",
            new CustomChestplateItem(ModArmorMaterials.STEEL_T5, new Item.Settings()
                    .component(ModDataComponentTypes.CAPE_DATA, CapeDataComponent.newCape(ModCapes.HORSE_LORD_CAPE)),
                    ModFactions.ROHAN));
    public static final Item HORSE_LORD_LEGGINGS = registerArmorPiece("horse_lord_leggings",
            new CustomLeggingsItem(ModArmorMaterials.STEEL_T5, new Item.Settings(),
                    ModFactions.ROHAN));
    public static final Item HORSE_LORD_BOOTS = registerArmorPiece("horse_lord_boots",
            new CustomBootsItem(ModArmorMaterials.STEEL_T5, new Item.Settings(),
                    ModFactions.ROHAN));

    public static final Item ROHIRRIC_CAPE = registerCape("rohirric_cape",
            new CapeChestplateItem(new Item.Settings()
                    .component(ModDataComponentTypes.CAPE_DATA, CapeDataComponent.newCape(ModCapes.ROHIRRIC_CAPE)),
                    ModFactions.ROHAN));
    public static final Item ROHIRRIC_ROYAL_GUARD_CAPE = registerCape("rohirric_royal_guard_cape",
            new CapeChestplateItem(new Item.Settings()
                    .component(ModDataComponentTypes.CAPE_DATA, CapeDataComponent.newCape(ModCapes.ROHIRRIC_ROYAL_GUARD_CAPE)),
                    ModFactions.ROHAN));
    public static final Item EORLING_MARSHAL_CAPE = registerCape("eorling_marshal_cape",
            new CapeChestplateItem(new Item.Settings()
                    .component(ModDataComponentTypes.CAPE_DATA, CapeDataComponent.newCape(ModCapes.EORLING_MARSHAL_CAPE)),
                    ModFactions.ROHAN));
    public static final Item HORSE_LORD_CAPE = registerCape("horse_lord_cape",
            new CapeChestplateItem(new Item.Settings()
                    .component(ModDataComponentTypes.CAPE_DATA, CapeDataComponent.newCape(ModCapes.HORSE_LORD_CAPE)),
                    ModFactions.ROHAN));

    public static final Item ROHIRRIC_HORSE_ARMOR = registerGeneratedItem("rohirric_horse_armor",
            new CustomHorseArmorItem(ArmorMaterials.IRON, AnimalArmorItem.Type.EQUESTRIAN, false, new Item.Settings().maxCount(1), ModFactions.ROHAN));
    //endregion

    //region DALE
    public static final Item DALISH_ARMING_COAT_BLACK_FUR = registerDyeableArmorPiece("dalish_arming_coat_black_fur",
            new CustomChestplateItem(ModArmorMaterials.LEATHER_T1, new Item.Settings()
                    .component(ModDataComponentTypes.DYE_DATA, new CustomDyeableDataComponent(7296407)),
                    ModFactions.DALE));
    public static final Item DALISH_ARMING_COAT_BROWN_FUR = registerDyeableArmorPiece("dalish_arming_coat_brown_fur",
            new CustomChestplateItem(ModArmorMaterials.LEATHER_T1, new Item.Settings()
                    .component(ModDataComponentTypes.DYE_DATA, new CustomDyeableDataComponent(7296407)),
                    ModFactions.DALE));
    public static final Item DALISH_ARMING_COAT_TAN_FUR = registerDyeableArmorPiece("dalish_arming_coat_tan_fur",
            new CustomChestplateItem(ModArmorMaterials.LEATHER_T1, new Item.Settings()
                    .component(ModDataComponentTypes.DYE_DATA, new CustomDyeableDataComponent(7296407)),
                    ModFactions.DALE));
    public static final Item DALISH_HEYDAY_ARMING_COAT = registerArmorPiece("dalish_heyday_arming_coat",
            new CustomChestplateItem(ModArmorMaterials.LEATHER_T1, new Item.Settings(),
                    ModFactions.DALE));

    public static final Item DALISH_BOOTS = registerArmorPiece("dalish_boots",
            new CustomBootsItem(ModArmorMaterials.LEATHER_T2, new Item.Settings(),
                    ModFactions.DALE));

    public static final Item DALISH_HELMET = registerCustomModelArmorPiece("dalish_helmet",
            new CustomHelmetItem(ModArmorMaterials.IRON_T3, new Item.Settings(),
                    ModFactions.DALE));
    public static final Item DALISH_HELMET_BLACK_FUR = registerCustomModelArmorPiece("dalish_helmet_black_fur",
            new CustomHelmetItem(ModArmorMaterials.IRON_T3, new Item.Settings(),
                    ModFactions.DALE));
    public static final Item DALISH_HELMET_BROWN_FUR = registerCustomModelArmorPiece("dalish_helmet_brown_fur",
            new CustomHelmetItem(ModArmorMaterials.IRON_T3, new Item.Settings(),
                    ModFactions.DALE));
    public static final Item DALISH_HELMET_TAN_FUR = registerCustomModelArmorPiece("dalish_helmet_tan_fur",
            new CustomHelmetItem(ModArmorMaterials.IRON_T3, new Item.Settings(),
                    ModFactions.DALE));

    public static final Item DALISH_CHAIN_HAUBERK = registerArmorPiece("dalish_chain_hauberk",
            new CustomChestplateItem(ModArmorMaterials.IRON_T3, new Item.Settings(),
                    ModFactions.DALE));

    public static final Item DALISH_CHAIN_COAT = registerArmorPiece("dalish_chain_coat",
            new CustomLeggingsItem(ModArmorMaterials.IRON_T3, new Item.Settings(),
                    ModFactions.DALE));

    public static final Item DALISH_BURGONET = registerCustomModelArmorPiece("dalish_burgonet",
            new CustomHelmetItem(ModArmorMaterials.STEEL_T4, new Item.Settings(),
                    ModFactions.DALE));

    public static final Item DALISH_SCALE_HAUBERK = registerArmorPiece("dalish_scale_hauberk",
            new CustomChestplateItem(ModArmorMaterials.STEEL_T4, new Item.Settings(),
                    ModFactions.DALE));

    public static final Item DALISH_HEYDAY_HELMET = registerCustomModelArmorPiece("dalish_heyday_helmet",
            new CustomHelmetItem(ModArmorMaterials.STEEL_T4, new Item.Settings(),
                    ModFactions.DALE));
    public static final Item DALISH_HEYDAY_CHESTPLATE = registerArmorPiece("dalish_heyday_chestplate",
            new CustomChestplateItem(ModArmorMaterials.STEEL_T4, new Item.Settings()
                    .component(ModDataComponentTypes.CAPE_DATA, CapeDataComponent.newCape(ModCapes.DALISH_HEYDAY_CAPE)),
                    ModFactions.DALE));
    public static final Item DALISH_HEYDAY_CHAIN_COAT = registerArmorPiece("dalish_heyday_chain_coat",
            new CustomLeggingsItem(ModArmorMaterials.STEEL_T4, new Item.Settings(),
                    ModFactions.DALE));
    public static final Item DALISH_HEYDAY_BOOTS = registerArmorPiece("dalish_heyday_boots",
            new CustomBootsItem(ModArmorMaterials.STEEL_T4, new Item.Settings(),
                    ModFactions.DALE));

    public static final Item BARDING_SOLDIER_HELMET = registerCustomModelArmorPiece("barding_soldier_helmet",
            new CustomHelmetItem(ModArmorMaterials.STEEL_T5, new Item.Settings(),
                    ModFactions.DALE));
    public static final Item BARDING_SOLDIER_CHESTPLATE = registerArmorPiece("barding_soldier_chestplate",
            new CustomChestplateItem(ModArmorMaterials.STEEL_T5, new Item.Settings(),
                    ModFactions.DALE));

    public static final Item BARDING_CHAIN_SKIRT = registerArmorPiece("barding_chain_skirt",
            new CustomLeggingsItem(ModArmorMaterials.STEEL_T5, new Item.Settings(),
                    ModFactions.DALE));
    public static final Item BARDING_PLATED_BOOTS = registerArmorPiece("barding_plated_boots",
            new CustomBootsItem(ModArmorMaterials.STEEL_T5, new Item.Settings(),
                    ModFactions.DALE));

    public static final Item BARDING_SERGEANT_HELMET = registerCustomModelArmorPiece("barding_sergeant_helmet",
            new CustomHelmetItem(ModArmorMaterials.STEEL_T5, new Item.Settings(),
                    ModFactions.DALE));
    public static final Item BARDING_SERGEANT_CHESTPLATE = registerArmorPiece("barding_sergeant_chestplate",
            new CustomChestplateItem(ModArmorMaterials.STEEL_T5, new Item.Settings()
                    .component(ModDataComponentTypes.CAPE_DATA, CapeDataComponent.newCape(ModCapes.BARDING_SERGEANT_CAPE)),
                    ModFactions.DALE));

    public static final Item BARDING_SURCOAT = registerCape("barding_surcoat",
            new CapeChestplateItem(new Item.Settings()
                    .component(ModDataComponentTypes.CAPE_DATA, CapeDataComponent.newCape(ModCapes.BARDING_SURCOAT)),
                    ModFactions.DALE));
    public static final Item DALISH_HEYDAY_CAPE = registerCape("dalish_heyday_cape",
            new CapeChestplateItem(new Item.Settings()
                    .component(ModDataComponentTypes.CAPE_DATA, CapeDataComponent.newCape(ModCapes.DALISH_HEYDAY_CAPE)),
                    ModFactions.DALE));
    public static final Item BARDING_SERGEANT_CAPE = registerCape("barding_sergeant_cape",
            new CapeChestplateItem(new Item.Settings()
                    .component(ModDataComponentTypes.CAPE_DATA, CapeDataComponent.newCape(ModCapes.BARDING_SERGEANT_CAPE)),
                    ModFactions.DALE));
    //endregion

    //endregion

    //region DWARVES

    //region GENERIC

    public static final Item LONGBEARD_SEGMENTED_HELMET = registerArmorPiece("longbeard_segmented_helmet",
            new CustomHelmetItem(ModArmorMaterials.IRON_T3, new Item.Settings(),
                    ModFactions.LONGBEARDS));

    public static final Item DWARVEN_PARTISAN_CHESTPLATE = registerArmorPiece("dwarven_partisan_chestplate",
            new CustomChestplateItem(ModArmorMaterials.LEATHER_T2, new Item.Settings(),
                    ModFactions.NONE));
    public static final Item DWARVEN_PARTISAN_LEGGINGS = registerArmorPiece("dwarven_partisan_leggings",
            new CustomLeggingsItem(ModArmorMaterials.LEATHER_T2, new Item.Settings(),
                    ModFactions.NONE));

    //endregion

    //region EREBOR

    public static final Item EREBOR_PLATE_HELMET = registerCustomModelArmorPiece("erebor_plate_helmet",
            new CustomHelmetItem(ModArmorMaterials.DWARVEN_STEEL_T5, new Item.Settings(),
                    ModSubFactions.EREBOR));
    public static final Item EREBOR_PLATE_CHESTPLATE = registerCustomModelArmorPiece("erebor_plate_chestplate",
            new CustomChestplateItem(ModArmorMaterials.DWARVEN_STEEL_T5, new Item.Settings(),
                    ModSubFactions.EREBOR));
    public static final Item EREBOR_PLATE_LEGGINGS = registerArmorPiece("erebor_plate_leggings",
            new CustomLeggingsItem(ModArmorMaterials.DWARVEN_STEEL_T5, new Item.Settings(),
                    ModSubFactions.EREBOR));
    public static final Item EREBOR_PLATE_BOOTS = registerArmorPiece("erebor_plate_boots",
            new CustomBootsItem(ModArmorMaterials.DWARVEN_STEEL_T5, new Item.Settings(),
                    ModSubFactions.EREBOR));

    public static final Item RAVENHILL_WATCHWARDEN_HELMET = registerArmorPiece("ravenhill_watchwarden_helmet",
            new CustomHelmetItem(ModArmorMaterials.DWARVEN_STEEL_T5, new Item.Settings(),
                    ModSubFactions.EREBOR));
    public static final Item RAVENHILL_WATCHWARDEN_CHESTPLATE = registerCustomModelArmorPiece("ravenhill_watchwarden_chestplate",
            new CustomChestplateItem(ModArmorMaterials.DWARVEN_STEEL_T5, new Item.Settings(),
                    ModSubFactions.EREBOR));
    public static final Item RAVENHILL_WATCHWARDEN_LEGGINGS = registerArmorPiece("ravenhill_watchwarden_leggings",
            new CustomLeggingsItem(ModArmorMaterials.DWARVEN_STEEL_T5, new Item.Settings(),
                    ModSubFactions.EREBOR));
    public static final Item RAVENHILL_WATCHWARDEN_BOOTS = registerArmorPiece("ravenhill_watchwarden_boots",
            new CustomBootsItem(ModArmorMaterials.DWARVEN_STEEL_T5, new Item.Settings(),
                    ModSubFactions.EREBOR));

    public static final Item RAVENHILL_SENTINEL_HELMET = registerCustomModelArmorPiece("ravenhill_sentinel_helmet",
            new CustomHelmetItem(ModArmorMaterials.DWARVEN_STEEL_T5, new Item.Settings(),
                    ModSubFactions.EREBOR));
    public static final Item RAVENHILL_SENTINEL_CHESTPLATE = registerCustomModelArmorPiece("ravenhill_sentinel_chestplate",
            new CustomChestplateItem(ModArmorMaterials.DWARVEN_STEEL_T5, new Item.Settings()
                    .component(ModDataComponentTypes.CAPE_DATA, CapeDataComponent.newCape(ModCapes.RAVENHILL_SENTINEL_CAPE)),
                    ModSubFactions.EREBOR));
    public static final Item RAVENHILL_SENTINEL_LEGGINGS = registerArmorPiece("ravenhill_sentinel_leggings",
            new CustomLeggingsItem(ModArmorMaterials.DWARVEN_STEEL_T5, new Item.Settings(),
                    ModSubFactions.EREBOR));
    public static final Item RAVENHILL_SENTINEL_BOOTS = registerArmorPiece("ravenhill_sentinel_boots",
            new CustomBootsItem(ModArmorMaterials.DWARVEN_STEEL_T5, new Item.Settings(),
                    ModSubFactions.EREBOR));

    public static final Item EREBOR_GATEWARDEN_HELMET = registerCustomModelArmorPiece("erebor_gatewarden_helmet",
            new CustomHelmetItem(ModArmorMaterials.DWARVEN_STEEL_T5, new Item.Settings(),
                    ModSubFactions.EREBOR));
    public static final Item EREBOR_GATEWARDEN_CHESTPLATE = registerCustomModelArmorPiece("erebor_gatewarden_chestplate",
            new CustomChestplateItem(ModArmorMaterials.DWARVEN_STEEL_T5, new Item.Settings()
                    .component(ModDataComponentTypes.CAPE_DATA, CapeDataComponent.newCape(ModCapes.EREBOR_CAPE)),
                    ModSubFactions.EREBOR));
    public static final Item EREBOR_GATEWARDEN_LEGGINGS = registerArmorPiece("erebor_gatewarden_leggings",
            new CustomLeggingsItem(ModArmorMaterials.DWARVEN_STEEL_T5, new Item.Settings(),
                    ModSubFactions.EREBOR));
    public static final Item EREBOR_GATEWARDEN_BOOTS = registerArmorPiece("erebor_gatewarden_boots",
            new CustomBootsItem(ModArmorMaterials.DWARVEN_STEEL_T5, new Item.Settings(),
                    ModSubFactions.EREBOR));

    public static final Item EREBOR_CAPE = registerCape("erebor_cape",
            new CapeChestplateItem(new Item.Settings()
                    .component(ModDataComponentTypes.CAPE_DATA, CapeDataComponent.newCape(ModCapes.EREBOR_CAPE)),
                    ModSubFactions.EREBOR));
    public static final Item RAVENHILL_SENTINEL_CAPE = registerCape("ravenhill_sentinel_cape",
            new CapeChestplateItem(new Item.Settings()
                    .component(ModDataComponentTypes.CAPE_DATA, CapeDataComponent.newCape(ModCapes.RAVENHILL_SENTINEL_CAPE)),
                    ModSubFactions.EREBOR));
    //endregion

    //endregion

    //region ELVES
    //region GENERIC

    public static final Item ELVEN_ARMING_COAT = registerDyeableArmorPiece("elven_arming_coat",
            new CustomChestplateItem(ModArmorMaterials.LEATHER_T1, new Item.Settings()
                    .component(ModDataComponentTypes.DYE_DATA, new CustomDyeableDataComponent(4805220)),
                    ModFactions.NONE));

    public static final Item ELVEN_ARMING_SKIRT = registerDyeableArmorPiece("elven_arming_skirt",
            new CustomLeggingsItem(ModArmorMaterials.LEATHER_T1, new Item.Settings()
                    .component(ModDataComponentTypes.DYE_DATA, new CustomDyeableDataComponent(4805220)),
                    ModFactions.NONE));
    public static final Item ELVEN_OPEN_ARMING_SKIRT = registerDyeableArmorPiece("elven_open_arming_skirt",
            new CustomLeggingsItem(ModArmorMaterials.LEATHER_T1, new Item.Settings()
                    .component(ModDataComponentTypes.DYE_DATA, new CustomDyeableDataComponent(4805220)),
                    ModFactions.NONE));

    public static final Item ELVEN_BOOTS = registerArmorPiece("elven_boots",
            new CustomBootsItem(ModArmorMaterials.LEATHER_T2, new Item.Settings(),
                    ModFactions.NONE));

    public static final Item ELVEN_CHAIN_COIF = registerArmorPiece("elven_chain_coif",
            new CustomHelmetItem(ModArmorMaterials.IRON_T3, new Item.Settings(),
                    ModFactions.NONE));

    public static final Item ELVEN_CHAIN_HAUBERK = registerArmorPiece("elven_chain_hauberk",
            new CustomChestplateItem(ModArmorMaterials.IRON_T3, new Item.Settings(),
                    ModFactions.NONE));

    public static final Item ELVEN_CHAIN_SKIRT = registerArmorPiece("elven_chain_skirt",
            new CustomLeggingsItem(ModArmorMaterials.IRON_T3, new Item.Settings(),
                    ModFactions.NONE));
    //endregion

    //region LOTHLORIEN
    public static final Item LORIEN_DIADEM = registerCustomModelArmorPiece("lorien_diadem",
            new CustomHelmetItem(ModArmorMaterials.LEATHER_T1, new Item.Settings(),
                    ModFactions.LOTHLORIEN));

    public static final Item LORIEN_LEATHER_HELMET = registerCustomModelArmorPiece("lorien_leather_helmet",
            new CustomHelmetItem(ModArmorMaterials.LEATHER_T2, new Item.Settings(),
                    ModFactions.LOTHLORIEN));

    public static final Item LORIEN_ARMING_COAT = registerArmorPiece("lorien_arming_coat",
            new CustomChestplateItem(ModArmorMaterials.LEATHER_T2, new Item.Settings(),
                    ModFactions.LOTHLORIEN));

    public static final Item LORIEN_ARMING_SKIRT = registerArmorPiece("lorien_arming_skirt",
            new CustomLeggingsItem(ModArmorMaterials.LEATHER_T2, new Item.Settings(),
                    ModFactions.LOTHLORIEN));

    public static final Item LORIEN_CHAIN_COIF_DIADEM = registerCustomModelArmorPiece("lorien_chain_coif_diadem",
            new CustomHelmetItem(ModArmorMaterials.IRON_T3, new Item.Settings(),
                    ModFactions.LOTHLORIEN));
    public static final Item LORIEN_SHORT_CHAIN_COIF_DIADEM = registerCustomModelArmorPiece("lorien_short_chain_coif_diadem",
            new CustomHelmetItem(ModArmorMaterials.IRON_T3, new Item.Settings(),
                    ModFactions.LOTHLORIEN));

    public static final Item LORIEN_CHAIN_HAUBERK = registerArmorPiece("lorien_chain_hauberk",
            new CustomChestplateItem(ModArmorMaterials.IRON_T3, new Item.Settings(),
                    ModFactions.LOTHLORIEN));
    public static final Item LORIEN_MARCHWARDEN_CHAIN_HAUBERK = registerArmorPiece("lorien_marchwarden_chain_hauberk",
            new CustomChestplateItem(ModArmorMaterials.IRON_T3, new Item.Settings()
                    .component(ModDataComponentTypes.CAPE_DATA, CapeDataComponent.newCape(ModCapes.LORIEN_MARCHWARDEN_CAPE)),
                    ModFactions.LOTHLORIEN));

    public static final Item LORIEN_SOLDIER_HELMET = registerCustomModelArmorPiece("lorien_soldier_helmet",
            new CustomHelmetItem(ModArmorMaterials.ELVEN_STEEL_T4, new Item.Settings(),
                    ModFactions.LOTHLORIEN));

    public static final Item LORIEN_SOLDIER_CHAIN_HAUBERK = registerArmorPiece("lorien_soldier_chain_hauberk",
            new CustomChestplateItem(ModArmorMaterials.ELVEN_STEEL_T4, new Item.Settings(),
                    ModFactions.LOTHLORIEN));
    public static final Item LORIEN_SOLDIER_SCALE_HAUBERK = registerArmorPiece("lorien_soldier_scale_hauberk",
            new CustomChestplateItem(ModArmorMaterials.ELVEN_STEEL_T4, new Item.Settings(),
                    ModFactions.LOTHLORIEN));

    public static final Item LORIEN_SCALE_COAT = registerArmorPiece("lorien_scale_coat",
            new CustomLeggingsItem(ModArmorMaterials.ELVEN_STEEL_T4, new Item.Settings(),
                    ModFactions.LOTHLORIEN));

    public static final Item GALADHRIM_HELMET = registerCustomModelArmorPiece("galadhrim_helmet",
            new CustomHelmetItem(ModArmorMaterials.ELVEN_STEEL_T5, new Item.Settings()
                    .component(ModDataComponentTypes.HOOD_DATA, HoodDataComponent.newHood(ModHoods.GALADHRIM_HOOD)),
                    ModFactions.LOTHLORIEN));
    public static final Item GALADHRIM_CHESTPLATE = registerArmorPiece("galadhrim_chestplate",
            new CustomChestplateItem(ModArmorMaterials.ELVEN_STEEL_T5, new Item.Settings()
                    .component(ModDataComponentTypes.CAPE_DATA, CapeDataComponent.newCape(ModCapes.GALADHRIM_CAPE)),
                    ModFactions.LOTHLORIEN));
    public static final Item GALADHRIM_LEGGINGS = registerArmorPiece("galadhrim_leggings",
            new CustomLeggingsItem(ModArmorMaterials.ELVEN_STEEL_T5, new Item.Settings(),
                    ModFactions.LOTHLORIEN));
    public static final Item GALADHRIM_BOOTS = registerArmorPiece("galadhrim_boots",
            new CustomBootsItem(ModArmorMaterials.ELVEN_STEEL_T5, new Item.Settings(),
                    ModFactions.LOTHLORIEN));

    public static final Item GALADHRIM_LORD_HELMET = registerCustomModelArmorPiece("galadhrim_lord_helmet",
            new CustomHelmetItem(ModArmorMaterials.ELVEN_STEEL_T5, new Item.Settings()
                    .component(ModDataComponentTypes.CAPE_DATA, CapeDataComponent.newCape(ModCapes.GALADHRIM_LORD_SURCOAT)),
                    ModFactions.LOTHLORIEN));
    public static final Item GALADHRIM_LORD_CHESTPLATE = registerArmorPiece("galadhrim_lord_chestplate",
            new CustomChestplateItem(ModArmorMaterials.ELVEN_STEEL_T5, new Item.Settings()
                    .component(ModDataComponentTypes.CAPE_DATA, CapeDataComponent.newCape(ModCapes.GALADHRIM_LORD_SURCOAT)),
                    ModFactions.LOTHLORIEN));
    public static final Item GALADHRIM_LORD_LEGGINGS = registerArmorPiece("galadhrim_lord_leggings",
            new CustomLeggingsItem(ModArmorMaterials.ELVEN_STEEL_T5, new Item.Settings(),
                    ModFactions.LOTHLORIEN));
    public static final Item GALADHRIM_LORD_BOOTS = registerArmorPiece("galadhrim_lord_boots",
            new CustomBootsItem(ModArmorMaterials.ELVEN_STEEL_T5, new Item.Settings(),
                    ModFactions.LOTHLORIEN));

    public static final Item LORIEN_MARCHWARDEN_HOOD = registerHood("lorien_marchwarden_hood",
            new HoodHelmetItem(new Item.Settings()
                    .component(ModDataComponentTypes.HOOD_DATA, HoodDataComponent.newHood(ModHoods.LORIEN_MARCHWARDEN_HOOD)),
                    ModFactions.LOTHLORIEN));
    public static final Item GALADHRIM_HOOD = registerHood("galadhrim_hood",
            new HoodHelmetItem(new Item.Settings()
                    .component(ModDataComponentTypes.HOOD_DATA, HoodDataComponent.newHood(ModHoods.GALADHRIM_HOOD)),
                    ModFactions.LOTHLORIEN));

    public static final Item LORIEN_MARCHWARDEN_CAPE = registerCape("lorien_marchwarden_cape",
            new CapeChestplateItem(new Item.Settings()
                    .component(ModDataComponentTypes.CAPE_DATA, CapeDataComponent.newCape(ModCapes.LORIEN_MARCHWARDEN_CAPE)),
                    ModFactions.LOTHLORIEN));
    public static final Item GALADHRIM_CAPE = registerCape("galadhrim_cape",
            new CapeChestplateItem(new Item.Settings()
                    .component(ModDataComponentTypes.CAPE_DATA, CapeDataComponent.newCape(ModCapes.GALADHRIM_CAPE)),
                    ModFactions.LOTHLORIEN));
    public static final Item GALADHRIM_LORD_SURCOAT = registerCape("galadhrim_lord_surcoat",
            new CapeChestplateItem(new Item.Settings()
                    .component(ModDataComponentTypes.CAPE_DATA, CapeDataComponent.newCape(ModCapes.GALADHRIM_LORD_SURCOAT)),
                    ModFactions.LOTHLORIEN));

    public static final Item LORIEN_HORSE_ARMOR = registerGeneratedItem("lorien_horse_armor",
            new CustomHorseArmorItem(ArmorMaterials.IRON, AnimalArmorItem.Type.EQUESTRIAN, false, new Item.Settings().maxCount(1), ModFactions.LOTHLORIEN));

    //endregion
    //endregion

    //region ORCS

    //region GENERIC
    public static final Item ORC_MAIL_COIF = registerArmorPiece("orc_mail_coif",
            new CustomHelmetItem(ModArmorMaterials.CRUDE_T3, new Item.Settings(),
                    ModFactions.NONE));
    public static final Item ORC_MAIL_HAUBERK = registerArmorPiece("orc_mail_hauberk",
            new CustomChestplateItem(ModArmorMaterials.CRUDE_T3, new Item.Settings(),
                    ModFactions.NONE));
    public static final Item ORC_MAIL_COAT = registerArmorPiece("orc_mail_coat",
            new CustomLeggingsItem(ModArmorMaterials.CRUDE_T3, new Item.Settings(),
                    ModFactions.NONE));

    public static final Item ORC_SALLET = registerCustomModelArmorPiece("orc_sallet",
            new CustomHelmetItem(ModArmorMaterials.CRUDE_T4, new Item.Settings(),
                    ModFactions.NONE));

    public static final Item ORC_GORGET_HAUBERK = registerArmorPiece("orc_gorget_hauberk",
            new CustomChestplateItem(ModArmorMaterials.CRUDE_T4, new Item.Settings(),
                    ModFactions.NONE));

    public static final Item ORC_PLATE_BOOTS = registerArmorPiece("orc_plate_boots",
            new CustomBootsItem(ModArmorMaterials.CRUDE_T4, new Item.Settings(),
                    ModFactions.NONE));
    //endregion

    //region MORDOR
    public static final Item MORDOR_ORC_HELMET = registerArmorPiece("mordor_orc_helmet",
            new CustomHelmetItem(ModArmorMaterials.CRUDE_T2, new Item.Settings(),
                    ModFactions.MORDOR));
    public static final Item MORDOR_ORC_EYE_HELMET = registerArmorPiece("mordor_orc_eye_helmet",
            new CustomHelmetItem(ModArmorMaterials.CRUDE_T2, new Item.Settings(),
                    ModFactions.MORDOR));
    public static final Item MORDOR_ORC_GREAT_EYE_HELMET = registerArmorPiece("mordor_orc_great_eye_helmet",
            new CustomHelmetItem(ModArmorMaterials.CRUDE_T3, new Item.Settings(),
                    ModFactions.MORDOR));
    public static final Item MORDOR_ORC_MANDIBLE_HELMET = registerArmorPiece("mordor_orc_mandible_helmet",
            new CustomHelmetItem(ModArmorMaterials.CRUDE_T3, new Item.Settings(),
                    ModFactions.MORDOR));
    public static final Item MORDOR_ORC_OVERSIGHT_HELMET = registerArmorPiece("mordor_orc_oversight_helmet",
            new CustomHelmetItem(ModArmorMaterials.CRUDE_T4, new Item.Settings(),
                    ModFactions.MORDOR));
    public static final Item MORDOR_ORC_SNOUT_HELMET = registerCustomModelArmorPiece("mordor_orc_snout_helmet",
            new CustomHelmetItem(ModArmorMaterials.CRUDE_T4, new Item.Settings(),
                    ModFactions.MORDOR));

    public static final Item MORDOR_ORC_CHESTPLATE = registerArmorPiece("mordor_orc_chestplate",
            new CustomChestplateItem(ModArmorMaterials.BURZUM_STEEL_T4, new Item.Settings(),
                    ModFactions.MORDOR));

    public static final Item MORDOR_ORC_SCALE_COAT = registerArmorPiece("mordor_orc_scale_coat",
            new CustomLeggingsItem(ModArmorMaterials.CRUDE_T4, new Item.Settings(),
                    ModFactions.MORDOR));
    
    public static final Item BLACK_URUK_PLATE_HELMET = registerArmorPiece("black_uruk_plate_helmet",
            new CustomHelmetItem(ModArmorMaterials.BURZUM_STEEL_T5, new Item.Settings(),
                    ModFactions.MORDOR));
    public static final Item BLACK_FACE_URUK_PLATE_HELMET = registerArmorPiece("black_uruk_face_plate_helmet",
            new CustomHelmetItem(ModArmorMaterials.BURZUM_STEEL_T5, new Item.Settings(),
                    ModFactions.MORDOR));
    public static final Item BLACK_URUK_PLATE_CHESTPLATE = registerCustomModelArmorPiece("black_uruk_plate_chestplate",
            new CustomChestplateItem(ModArmorMaterials.BURZUM_STEEL_T5, new Item.Settings(),
                    ModFactions.MORDOR));
    public static final Item BLACK_URUK_PLATE_LEGGINGS = registerArmorPiece("black_uruk_plate_leggings",
            new CustomLeggingsItem(ModArmorMaterials.BURZUM_STEEL_T5, new Item.Settings(),
                    ModFactions.MORDOR));
    public static final Item BLACK_URUK_PLATE_BOOTS = registerArmorPiece("black_uruk_plate_boots",
            new CustomBootsItem(ModArmorMaterials.BURZUM_STEEL_T5, new Item.Settings(),
                    ModFactions.MORDOR));

    public static final Item BLACK_URUK_COMMANDER_HELMET = registerCustomModelArmorPiece("black_uruk_commander_helmet",
            new CustomHelmetItem(ModArmorMaterials.BURZUM_STEEL_T5, new Item.Settings(),
                    ModFactions.MORDOR));
    public static final Item BLACK_URUK_COMMANDER_CHESTPLATE = registerCustomModelArmorPiece("black_uruk_commander_chestplate",
            new CustomChestplateItem(ModArmorMaterials.BURZUM_STEEL_T5, new Item.Settings(),
                    ModFactions.MORDOR));

    public static final Item MORDOR_SHIELD = registerItem("mordor_shield",
            new CustomShieldItem(ModShieldTypes.MEDIUM_SHIELD, ModFactions.MORDOR));

    public static final Item NAZGUL_CLOAK_HOOD = registerArmorPiece("nazgul_cloak_hood",
            new CustomHelmetItem(ModArmorMaterials.STEEL_T3, new Item.Settings(),
                    ModFactions.MORDOR));
    public static final Item NAZGUL_CLOAK = registerArmorPiece("nazgul_cloak",
            new CustomChestplateItem(ModArmorMaterials.STEEL_T3, new Item.Settings(),
                    ModFactions.MORDOR));
    public static final Item NAZGUL_PANTS = registerArmorPiece("nazgul_pants",
            new CustomLeggingsItem(ModArmorMaterials.STEEL_T3, new Item.Settings(),
                    ModFactions.MORDOR));
    public static final Item NAZGUL_BOOTS = registerArmorPiece("nazgul_boots",
            new CustomBootsItem(ModArmorMaterials.STEEL_T3, new Item.Settings(),
                    ModFactions.MORDOR));
    //endregion

    //region ISENGARD
    public static final Item URUK_HAI_LEATHER_SCOUT_CAP = registerCustomModelArmorPiece("uruk_hai_leather_scout_cap",
            new CustomHelmetItem(ModArmorMaterials.LEATHER_T2, new Item.Settings(),
                    ModFactions.ISENGARD));

    public static final Item URUK_HAI_PLATE_HELMET = registerCustomModelArmorPiece("uruk_hai_plate_helmet",
            new CustomHelmetItem(ModArmorMaterials.BURZUM_STEEL_T5, new Item.Settings(),
                    ModFactions.ISENGARD));
    public static final Item URUK_HAI_PLATE_PAINTED_HELMET = registerCustomModelArmorPiece("uruk_hai_plate_painted_helmet",
            new CustomHelmetItem(ModArmorMaterials.BURZUM_STEEL_T5, new Item.Settings(),
                    ModFactions.ISENGARD));
    public static final Item URUK_HAI_SAPPER_HELMET = registerCustomModelArmorPiece("uruk_hai_sapper_helmet",
            new CustomHelmetItem(ModArmorMaterials.BURZUM_STEEL_T5, new Item.Settings(),
                    ModFactions.ISENGARD));
    public static final Item URUK_HAI_SAPPER_PAINTED_HELMET = registerCustomModelArmorPiece("uruk_hai_sapper_painted_helmet",
            new CustomHelmetItem(ModArmorMaterials.BURZUM_STEEL_T5, new Item.Settings(),
                    ModFactions.ISENGARD));
    public static final Item URUK_HAI_BERSERKER_HELMET = registerCustomModelArmorPiece("uruk_hai_berserker_helmet",
            new CustomHelmetItem(ModArmorMaterials.BURZUM_STEEL_T5, new Item.Settings(),
                    ModFactions.ISENGARD));
    public static final Item URUK_HAI_BERSERKER_PAINTED_HELMET = registerCustomModelArmorPiece("uruk_hai_berserker_painted_helmet",
            new CustomHelmetItem(ModArmorMaterials.BURZUM_STEEL_T5, new Item.Settings(),
                    ModFactions.ISENGARD));
    public static final Item URUK_HAI_COMMANDER_HELMET = registerCustomModelArmorPiece("uruk_hai_commander_helmet",
            new CustomHelmetItem(ModArmorMaterials.BURZUM_STEEL_T5, new Item.Settings(),
                    ModFactions.ISENGARD));
    public static final Item URUK_HAI_COMMANDER_PAINTED_HELMET = registerCustomModelArmorPiece("uruk_hai_commander_painted_helmet",
            new CustomHelmetItem(ModArmorMaterials.BURZUM_STEEL_T5, new Item.Settings(),
                    ModFactions.ISENGARD));
    public static final Item URUK_HAI_PLATE_CHESTPLATE = registerCustomModelArmorPiece("uruk_hai_plate_chestplate",
            new CustomChestplateItem(ModArmorMaterials.BURZUM_STEEL_T5, new Item.Settings(),
                    ModFactions.ISENGARD));
    public static final Item URUK_HAI_PLATE_LEGGINGS = registerArmorPiece("uruk_hai_plate_leggings",
            new CustomLeggingsItem(ModArmorMaterials.BURZUM_STEEL_T5, new Item.Settings(),
                    ModFactions.ISENGARD));
    public static final Item URUK_HAI_PLATE_BOOTS = registerArmorPiece("uruk_hai_plate_boots",
            new CustomBootsItem(ModArmorMaterials.BURZUM_STEEL_T5, new Item.Settings(),
                    ModFactions.ISENGARD));

    //endregion

    //region MISTY MOUNTAIN GOBLINS
    public static final Item HOBGOBLIN_CHAIN_HAUBERK = registerArmorPiece("hobgoblin_chain_hauberk",
            new CustomChestplateItem(ModArmorMaterials.BURZUM_STEEL_T3, new Item.Settings(),
                    ModFactions.MISTY_MOUNTAINS_GOBLINS));

    public static final Item GUNDABAD_CAPTAIN_HELMET = registerCustomModelArmorPiece("gundabad_captain_helmet",
            new CustomHelmetItem(ModArmorMaterials.BURZUM_STEEL_T5, new Item.Settings(),
                    ModSubFactions.GUNDABAD));
    public static final Item GUNDABAD_SCREECHER_HELMET = registerCustomModelArmorPiece("gundabad_screecher_helmet",
            new CustomHelmetItem(ModArmorMaterials.BURZUM_STEEL_T5, new Item.Settings(),
                    ModSubFactions.GUNDABAD));
    public static final Item GUNDABAD_SEEKER_HELMET = registerCustomModelArmorPiece("gundabad_seeker_helmet",
            new CustomHelmetItem(ModArmorMaterials.BURZUM_STEEL_T5, new Item.Settings(),
                    ModSubFactions.GUNDABAD));
    public static final Item GUNDABAD_SOLDIER_HELMET = registerCustomModelArmorPiece("gundabad_soldier_helmet",
            new CustomHelmetItem(ModArmorMaterials.BURZUM_STEEL_T5, new Item.Settings(),
                    ModSubFactions.GUNDABAD));
    public static final Item GUNDABAD_CHAIN_COIF = registerArmorPiece("gundabad_chain_coif",
            new CustomHelmetItem(ModArmorMaterials.BURZUM_STEEL_T5, new Item.Settings(),
                    ModSubFactions.GUNDABAD));
    public static final Item GUNDABAD_SKULLCAP_HELMET = registerCustomModelArmorPiece("gundabad_skullcap_helmet",
            new CustomHelmetItem(ModArmorMaterials.BURZUM_STEEL_T5, new Item.Settings(),
                    ModSubFactions.GUNDABAD));

    public static final Item GUNDABAD_BONE_PAULDRON = registerArmorPiece("gundabad_bone_pauldron",
            new CustomChestplateItem(ModArmorMaterials.BURZUM_STEEL_T5, new Item.Settings(),
                    ModSubFactions.GUNDABAD));
    public static final Item GUNDABAD_LEATHER_SCALE_COAT = registerArmorPiece("gundabad_leather_scale_coat",
            new CustomChestplateItem(ModArmorMaterials.BURZUM_STEEL_T5, new Item.Settings(),
                    ModSubFactions.GUNDABAD));
    public static final Item GUNDABAD_REINFORCED_LEATHER_SCALE_COAT = registerArmorPiece("gundabad_reinforced_leather_scale_coat",
            new CustomChestplateItem(ModArmorMaterials.BURZUM_STEEL_T5, new Item.Settings(),
                    ModSubFactions.GUNDABAD));
    public static final Item GUNDABAD_REINFORCED_LEATHER_VEST = registerArmorPiece("gundabad_reinforced_leather_vest",
            new CustomChestplateItem(ModArmorMaterials.BURZUM_STEEL_T5, new Item.Settings(),
                    ModSubFactions.GUNDABAD));

    public static final Item GUNDABAD_CHAIN_COAT = registerArmorPiece("gundabad_chain_coat",
            new CustomLeggingsItem(ModArmorMaterials.BURZUM_STEEL_T5, new Item.Settings(),
                    ModSubFactions.GUNDABAD));
    public static final Item GUNDABAD_LEATHER_LEGGINGS = registerArmorPiece("gundabad_leather_leggings",
            new CustomLeggingsItem(ModArmorMaterials.BURZUM_STEEL_T5, new Item.Settings(),
                    ModSubFactions.GUNDABAD));

    public static final Item GUNDABAD_LACED_BOOTS = registerArmorPiece("gundabad_laced_boots",
            new CustomBootsItem(ModArmorMaterials.BURZUM_STEEL_T5, new Item.Settings(),
                    ModSubFactions.GUNDABAD));
    public static final Item ORCISH_FUR_BOOTS = registerArmorPiece("orcish_fur_boots",
            new CustomBootsItem(ModArmorMaterials.BURZUM_STEEL_T5, new Item.Settings(),
                    ModSubFactions.GUNDABAD));
    
    public static final Item GUNDABAD_HOBGOBLIN_PLATE_CRESTED_HELMET = registerCustomModelArmorPiece("gundabad_hobgoblin_plate_crested_helmet",
            new CustomHelmetItem(ModArmorMaterials.BURZUM_STEEL_T5, new Item.Settings(),
                    ModSubFactions.GUNDABAD));
    public static final Item GUNDABAD_HOBGOBLIN_PLATE_LARGE_CREST_HELMET = registerCustomModelArmorPiece("gundabad_hobgoblin_plate_large_crest_helmet",
            new CustomHelmetItem(ModArmorMaterials.BURZUM_STEEL_T5, new Item.Settings(),
                    ModSubFactions.GUNDABAD));
    public static final Item GUNDABAD_HOBGOBLIN_PLATE_LONG_HORN_HELMET = registerCustomModelArmorPiece("gundabad_hobgoblin_plate_long_horn_helmet",
            new CustomHelmetItem(ModArmorMaterials.BURZUM_STEEL_T5, new Item.Settings(),
                    ModSubFactions.GUNDABAD));
    public static final Item GUNDABAD_HOBGOBLIN_PLATE_SMALL_HORN_HELMET = registerCustomModelArmorPiece("gundabad_hobgoblin_plate_small_horn_helmet",
            new CustomHelmetItem(ModArmorMaterials.BURZUM_STEEL_T5, new Item.Settings(),
                    ModSubFactions.GUNDABAD));
    public static final Item GUNDABAD_HOBGOBLIN_PLATE_CHESTPLATE = registerCustomModelArmorPiece("gundabad_hobgoblin_plate_chestplate",
            new CustomChestplateItem(ModArmorMaterials.BURZUM_STEEL_T5, new Item.Settings(),
                    ModSubFactions.GUNDABAD));
    public static final Item GUNDABAD_HOBGOBLIN_CHAIN_COAT = registerArmorPiece("gundabad_hobgoblin_chain_coat",
            new CustomLeggingsItem(ModArmorMaterials.BURZUM_STEEL_T5, new Item.Settings(),
                    ModSubFactions.GUNDABAD));
    public static final Item GUNDABAD_HOBGOBLIN_PLATED_BOOTS = registerArmorPiece("gundabad_hobgoblin_plated_boots",
            new CustomBootsItem(ModArmorMaterials.BURZUM_STEEL_T5, new Item.Settings(),
                    ModSubFactions.GUNDABAD));

    public static final Item GUNDABAD_HOBGOBLIN_PLATE_TROPHY_CHESTPLATE = registerCustomModelArmorPiece("gundabad_hobgoblin_plate_trophy_chestplate",
            new CustomChestplateItem(ModArmorMaterials.BURZUM_STEEL_T5, new Item.Settings(),
                    ModSubFactions.GUNDABAD));


    //endregion

    /*public static final Item STEEL_TROLL_ARMOR = registerGeneratedItem("steel_troll_armor",
            new TrollArmorItem(10, "steel", new Item.Settings().maxCount(1)));*/

    // GENERIC
    // Warg
    public static final Item WARG_MORDOR_PLATE_ARMOR = registerGeneratedItem("warg_mordor_plate_armor",
            new CustomAnimalArmorItem(ModArmorMaterials.PLATE, "_mordor", CustomAnimalArmorItem.Type.WARG, false, new Item.Settings().maxDamage(ArmorItem.Type.BODY.getMaxDamage(10)), ModFactions.MORDOR));
    public static final Item WARG_GUNDABAD_PLATE_ARMOR = registerGeneratedItem("warg_gundabad_plate_armor",
            new CustomAnimalArmorItem(ModArmorMaterials.PLATE, "_gundabad", CustomAnimalArmorItem.Type.WARG, false, new Item.Settings().maxDamage(ArmorItem.Type.BODY.getMaxDamage(10)), ModSubFactions.GUNDABAD));
    public static final Item WARG_MORDOR_MAIL_ARMOR = registerGeneratedItem("warg_mordor_mail_armor",
            new CustomAnimalArmorItem(ModArmorMaterials.MAIL, "_mordor", CustomAnimalArmorItem.Type.WARG, false, new Item.Settings().maxDamage(ArmorItem.Type.BODY.getMaxDamage(6)), ModFactions.MORDOR));
    public static final Item WARG_LEATHER_ARMOR = registerDyeableArmorPiece("warg_leather_armor",
            new CustomAnimalArmorItem(ModArmorMaterials.LEATHER, null, CustomAnimalArmorItem.Type.WARG, false, new Item.Settings().maxDamage(ArmorItem.Type.BODY.getMaxDamage(4)).component(ModDataComponentTypes.DYE_DATA, new CustomDyeableDataComponent(4865076)), ModFactions.MORDOR));
    public static final Item WARG_REINFORCED_LEATHER_ARMOR = registerDyeableArmorPiece("warg_reinforced_leather_armor",
            new CustomAnimalArmorItem(ModArmorMaterials.LEATHER, "_reinforced", CustomAnimalArmorItem.Type.WARG, true, new Item.Settings().maxDamage(ArmorItem.Type.BODY.getMaxDamage(4)).component(ModDataComponentTypes.DYE_DATA, new CustomDyeableDataComponent(4865076)), ModFactions.MORDOR));

    // Broadhoof Goat
    public static final Item BROADHOOF_GOAT_PADDED_ARMOR = registerDyeableArmorPiece("broadhoof_goat_padded_armor",
            new CustomAnimalArmorItem(ModArmorMaterials.LEATHER, null, CustomAnimalArmorItem.Type.BROADHOOF_GOAT, true, new Item.Settings().maxDamage(ArmorItem.Type.BODY.getMaxDamage(4)).component(ModDataComponentTypes.DYE_DATA, new CustomDyeableDataComponent(15256475)), ModSubFactions.EREBOR));
    public static final Item BROADHOOF_GOAT_ORNAMENTED_PADDED_ARMOR = registerDyeableArmorPiece("broadhoof_goat_ornamented_padded_armor",
            new CustomAnimalArmorItem(ModArmorMaterials.LEATHER, "_ornamented", CustomAnimalArmorItem.Type.BROADHOOF_GOAT, true, new Item.Settings().maxDamage(ArmorItem.Type.BODY.getMaxDamage(4)).component(ModDataComponentTypes.DYE_DATA, new CustomDyeableDataComponent(15256475)), ModSubFactions.EREBOR));
    public static final Item BROADHOOF_GOAT_PLATE_ARMOR = registerGeneratedItem("broadhoof_goat_plate_armor",
            new CustomAnimalArmorItem(ModArmorMaterials.PLATE, null, CustomAnimalArmorItem.Type.BROADHOOF_GOAT, false, new Item.Settings().maxDamage(ArmorItem.Type.BODY.getMaxDamage(10)), ModSubFactions.EREBOR));

    private static Item registerItem(String name, Item item) {
        ModItemGroups.EQUIPMENT_CONTENTS.add(item.getDefaultStack());
        return Registry.register(Registries.ITEM, Identifier.of(MiddleEarth.MOD_ID, name), item);
    }

    private static Item registerHood(String name, Item item) {
        ModItemGroups.EQUIPMENT_CONTENTS.add(item.getDefaultStack());
        SimpleItemModel.items.add(item);
        hoods.add(item);
        return Registry.register(Registries.ITEM, Identifier.of(MiddleEarth.MOD_ID, name), item);
    }

    private static Item registerDyeableHood(String name, Item item) {
        ModItemGroups.EQUIPMENT_CONTENTS.add(item.getDefaultStack());
        SimpleDyeableItemModel.items.add(item);
        hoods.add(item);
        return Registry.register(Registries.ITEM, Identifier.of(MiddleEarth.MOD_ID, name), item);
    }

    private static Item registerCape(String name, Item item) {
        ModItemGroups.EQUIPMENT_CONTENTS.add(item.getDefaultStack());
        SimpleItemModel.items.add(item);
        capes.add(item);
        return Registry.register(Registries.ITEM, Identifier.of(MiddleEarth.MOD_ID, name), item);
    }

    private static Item registerDyeableCape(String name, Item item) {
        ModItemGroups.EQUIPMENT_CONTENTS.add(item.getDefaultStack());
        SimpleDyeableItemModel.items.add(item);
        capes.add(item);
        return Registry.register(Registries.ITEM, Identifier.of(MiddleEarth.MOD_ID, name), item);
    }

    private static Item registerGeneratedItem(String name, Item item) {
        ModItemGroups.EQUIPMENT_CONTENTS.add(item.getDefaultStack());
        SimpleItemModel.items.add(item);
        return Registry.register(Registries.ITEM, Identifier.of(MiddleEarth.MOD_ID, name), item);
    }

    private static Item registerDyeableArmorPiece(String name, Item item) {
        ModItemGroups.EQUIPMENT_CONTENTS.add(item.getDefaultStack());
        SimpleDyeableItemModel.items.add(item);
        switch (item){
            case CustomHelmetItem helmetItem -> armorPiecesListHelmets.add(helmetItem);
            case CustomChestplateItem chestplateItem -> armorPiecesListChestplates.add(chestplateItem);
            case CustomLeggingsItem leggingsItem -> armorPiecesListLeggings.add(leggingsItem);
            case CustomBootsItem bootsItem -> armorPiecesListBoots.add(bootsItem);
            case CustomAnimalArmorItem animalArmorItem -> {}
            default -> throw new IllegalStateException("Unexpected value: " + item);
        }
        return Registry.register(Registries.ITEM, Identifier.of(MiddleEarth.MOD_ID, name), item);
    }

    private static Item registerArmorPiece(String name, Item item) {
        ModItemGroups.EQUIPMENT_CONTENTS.add(item.getDefaultStack());
        SimpleItemModel.items.add(item);
        switch (item){
            case CustomHelmetItem helmetItem -> armorPiecesListHelmets.add(helmetItem);
            case CustomChestplateItem chestplateItem -> armorPiecesListChestplates.add(chestplateItem);
            case CustomLeggingsItem leggingsItem -> armorPiecesListLeggings.add(leggingsItem);
            case CustomBootsItem bootsItem -> armorPiecesListBoots.add(bootsItem);
            default -> throw new IllegalStateException("Unexpected value: " + item);
        }
        return Registry.register(Registries.ITEM, Identifier.of(MiddleEarth.MOD_ID, name), item);
    }

    private static Item registerCustomModelArmorPiece(String name, Item item) {
        ModItemGroups.EQUIPMENT_CONTENTS.add(item.getDefaultStack());
        SimpleItemModel.items.add(item);
        return Registry.register(Registries.ITEM, Identifier.of(MiddleEarth.MOD_ID, name), item);
    }

    private static Item registerDyeableCustomModelArmorPiece(String name, Item item) {
        ModItemGroups.EQUIPMENT_CONTENTS.add(item.getDefaultStack());
        SimpleDyeableItemModel.items.add(item);
        return Registry.register(Registries.ITEM, Identifier.of(MiddleEarth.MOD_ID, name), item);
    }

    public static void registerModItems() {
        LoggerUtil.logDebugMsg("Registering Mod Equipment Items for " + MiddleEarth.MOD_ID);
    }
}
