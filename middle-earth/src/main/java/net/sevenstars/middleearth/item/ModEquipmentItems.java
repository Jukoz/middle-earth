package net.sevenstars.middleearth.item;

import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.block.ModBlocks;
import net.sevenstars.middleearth.datageneration.content.models.SimpleDyeableItemModel;
import net.sevenstars.middleearth.datageneration.content.models.SimpleItemModel;
import net.sevenstars.middleearth.item.dataComponents.CapeDataComponent;
import net.sevenstars.middleearth.item.dataComponents.CustomDyeableDataComponent;
import net.sevenstars.middleearth.item.dataComponents.HoodDataComponent;
import net.sevenstars.middleearth.item.items.armor.*;
import net.sevenstars.middleearth.item.items.armor.artefact.CustomArtefactHelmetItem;
import net.sevenstars.middleearth.item.utils.ModItemGroups;
import net.sevenstars.middleearth.item.utils.armor.ModArmorMaterials;
import net.sevenstars.middleearth.item.utils.armor.capes.ModCapes;
import net.sevenstars.middleearth.item.utils.armor.hoods.ModHoods;
import net.sevenstars.middleearth.utils.ModFactions;
import net.sevenstars.middleearth.utils.ModSubFactions;
import net.minecraft.item.AnimalArmorItem;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

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
            (settings) -> new HoodHelmetItem(settings, ModArmorMaterials.FABRIC_T0, ModFactions.NONE), new Item.Settings()
                    .component(ModDataComponentTypes.HOOD_DATA, HoodDataComponent.newHood(ModHoods.HOOD))
                    .component(ModDataComponentTypes.DYE_DATA, new CustomDyeableDataComponent(5394247)));
    public static final Item TALL_HOOD = registerDyeableHood("tall_hood",
            (settings) -> new HoodHelmetItem(settings, ModArmorMaterials.FABRIC_T0, ModFactions.NONE), new Item.Settings()
                    .component(ModDataComponentTypes.HOOD_DATA, HoodDataComponent.newHood(ModHoods.TALL_HOOD))
                    .component(ModDataComponentTypes.DYE_DATA, new CustomDyeableDataComponent(5394247)));

    public static final Item BLACK_FUR_HOOD = registerDyeableHood("black_fur_hood",
            (settings) -> new HoodHelmetItem(settings, ModArmorMaterials.FUR_T0, ModFactions.NONE), new Item.Settings()
                    .component(ModDataComponentTypes.HOOD_DATA, HoodDataComponent.newHood(ModHoods.BLACK_FUR_HOOD))
                    .component(ModDataComponentTypes.DYE_DATA, new CustomDyeableDataComponent(5394247)));
    public static final Item BROWN_FUR_HOOD = registerDyeableHood("brown_fur_hood",
            (settings) -> new HoodHelmetItem(settings, ModArmorMaterials.FUR_T0, ModFactions.NONE), new Item.Settings()
                    .component(ModDataComponentTypes.HOOD_DATA, HoodDataComponent.newHood(ModHoods.BROWN_FUR_HOOD))
                    .component(ModDataComponentTypes.DYE_DATA, new CustomDyeableDataComponent(5394247)));
    public static final Item GRAY_FUR_HOOD = registerDyeableHood("gray_fur_hood",
            (settings) -> new HoodHelmetItem(settings, ModArmorMaterials.FUR_T0, ModFactions.NONE), new Item.Settings()
                    .component(ModDataComponentTypes.HOOD_DATA, HoodDataComponent.newHood(ModHoods.GRAY_FUR_HOOD))
                    .component(ModDataComponentTypes.DYE_DATA, new CustomDyeableDataComponent(5394247)));
    public static final Item TAN_FUR_HOOD = registerDyeableHood("tan_fur_hood",
            (settings) -> new HoodHelmetItem(settings, ModArmorMaterials.FUR_T0, ModFactions.NONE), new Item.Settings()
                    .component(ModDataComponentTypes.HOOD_DATA, HoodDataComponent.newHood(ModHoods.TAN_FUR_HOOD))
                    .component(ModDataComponentTypes.DYE_DATA, new CustomDyeableDataComponent(5394247)));
    public static final Item WHITE_FUR_HOOD = registerDyeableHood("white_fur_hood",
            (settings) -> new HoodHelmetItem(settings, ModArmorMaterials.FUR_T0, ModFactions.NONE), new Item.Settings()
                    .component(ModDataComponentTypes.HOOD_DATA, HoodDataComponent.newHood(ModHoods.WHITE_FUR_HOOD))
                    .component(ModDataComponentTypes.DYE_DATA, new CustomDyeableDataComponent(5394247)));

    //capes
    public static final Item CAPE = registerDyeableCape("cape",
            (settings) -> new CapeChestplateItem(settings, ModArmorMaterials.FABRIC_T0, ModFactions.NONE), new Item.Settings()
                    .component(ModDataComponentTypes.CAPE_DATA, CapeDataComponent.newCape(ModCapes.CAPE))
                    .component(ModDataComponentTypes.DYE_DATA, new CustomDyeableDataComponent(5394247)));
    public static final Item SURCOAT = registerDyeableCape("surcoat",
            (settings) -> new CapeChestplateItem(settings, ModArmorMaterials.FABRIC_T0, ModFactions.NONE), new Item.Settings()
                    .component(ModDataComponentTypes.CAPE_DATA, CapeDataComponent.newCape(ModCapes.SURCOAT))
                    .component(ModDataComponentTypes.DYE_DATA, new CustomDyeableDataComponent(5394247)));
    public static final Item CLOAK = registerDyeableCape("cloak",
            (settings) -> new CapeChestplateItem(settings, ModArmorMaterials.FABRIC_T0, ModFactions.NONE), new Item.Settings()
                    .component(ModDataComponentTypes.CAPE_DATA, CapeDataComponent.newCape(ModCapes.CLOAK))
                    .component(ModDataComponentTypes.DYE_DATA, new CustomDyeableDataComponent(5394247)));
    public static final Item WANDERER_ROBES = registerDyeableCape("wanderer_robes",
            (settings) -> new CapeChestplateItem(settings, ModArmorMaterials.FABRIC_T0, ModFactions.NONE), new Item.Settings()
                    .component(ModDataComponentTypes.CAPE_DATA, CapeDataComponent.newCape(ModCapes.WANDERER_ROBES))
                    .component(ModDataComponentTypes.DYE_DATA, new CustomDyeableDataComponent(5394247)));

    public static final Item BLACK_FUR_CLOAK = registerDyeableCape("black_fur_cloak",
            (settings) -> new CapeChestplateItem(settings, ModArmorMaterials.FUR_T0, ModFactions.NONE), new Item.Settings()
                    .component(ModDataComponentTypes.CAPE_DATA, CapeDataComponent.newCape(ModCapes.BLACK_FUR_CLOAK))
                    .component(ModDataComponentTypes.DYE_DATA, new CustomDyeableDataComponent(5394247)));
    public static final Item BROWN_FUR_CLOAK = registerDyeableCape("brown_fur_cloak",
            (settings) -> new CapeChestplateItem(settings, ModArmorMaterials.FUR_T0, ModFactions.NONE), new Item.Settings()
                    .component(ModDataComponentTypes.CAPE_DATA, CapeDataComponent.newCape(ModCapes.BROWN_FUR_CLOAK))
                    .component(ModDataComponentTypes.DYE_DATA, new CustomDyeableDataComponent(5394247)));
    public static final Item GRAY_FUR_CLOAK = registerDyeableCape("gray_fur_cloak",
            (settings) -> new CapeChestplateItem(settings, ModArmorMaterials.FUR_T0, ModFactions.NONE), new Item.Settings()
                    .component(ModDataComponentTypes.CAPE_DATA, CapeDataComponent.newCape(ModCapes.GRAY_FUR_CLOAK))
                    .component(ModDataComponentTypes.DYE_DATA, new CustomDyeableDataComponent(5394247)));
    public static final Item TAN_FUR_CLOAK = registerDyeableCape("tan_fur_cloak",
            (settings) -> new CapeChestplateItem(settings, ModArmorMaterials.FUR_T0, ModFactions.NONE), new Item.Settings()
                    .component(ModDataComponentTypes.CAPE_DATA, CapeDataComponent.newCape(ModCapes.TAN_FUR_CLOAK))
                    .component(ModDataComponentTypes.DYE_DATA, new CustomDyeableDataComponent(5394247)));
    public static final Item WHITE_FUR_CLOAK = registerDyeableCape("white_fur_cloak",
            (settings) -> new CapeChestplateItem(settings, ModArmorMaterials.FUR_T0, ModFactions.NONE), new Item.Settings()
                    .component(ModDataComponentTypes.CAPE_DATA, CapeDataComponent.newCape(ModCapes.WHITE_FUR_CLOAK))
                    .component(ModDataComponentTypes.DYE_DATA, new CustomDyeableDataComponent(5394247)));

    public static final Item BLACK_FUR = registerCape("black_fur",
            (settings) -> new CapeChestplateItem(settings, ModArmorMaterials.FUR_T0, ModFactions.NONE), new Item.Settings()
                    .component(ModDataComponentTypes.CAPE_DATA, CapeDataComponent.newCape(ModCapes.BLACK_FUR)));
    public static final Item BROWN_FUR = registerCape("brown_fur",
            (settings) -> new CapeChestplateItem(settings, ModArmorMaterials.FUR_T0, ModFactions.NONE), new Item.Settings()
                    .component(ModDataComponentTypes.CAPE_DATA, CapeDataComponent.newCape(ModCapes.BROWN_FUR)));
    public static final Item GRAY_FUR = registerCape("gray_fur",
            (settings) -> new CapeChestplateItem(settings, ModArmorMaterials.FUR_T0, ModFactions.NONE), new Item.Settings()
                    .component(ModDataComponentTypes.CAPE_DATA, CapeDataComponent.newCape(ModCapes.GRAY_FUR)));
    public static final Item TAN_FUR = registerCape("tan_fur",
            (settings) -> new CapeChestplateItem(settings, ModArmorMaterials.FUR_T0, ModFactions.NONE), new Item.Settings()
                    .component(ModDataComponentTypes.CAPE_DATA, CapeDataComponent.newCape(ModCapes.TAN_FUR)));
    public static final Item WHITE_FUR = registerCape("white_fur",
            (settings) -> new CapeChestplateItem(settings, ModArmorMaterials.FUR_T0, ModFactions.NONE), new Item.Settings()
                    .component(ModDataComponentTypes.CAPE_DATA, CapeDataComponent.newCape(ModCapes.WHITE_FUR)));

    public static final Item STRAW_HAT = registerCustomModelArmorPiece("straw_hat",
            (settings) -> new CustomHelmetItem(ModArmorMaterials.STRAW_T1, settings, ModFactions.NONE), new Item.Settings());

    public static final Item WOVEN_HAT = registerCustomModelArmorPiece("woven_hat",
            (settings) -> new CustomHelmetItem(ModArmorMaterials.WOOL_T1, settings, ModFactions.NONE), new Item.Settings());

    public static final Item BYCOCKET = registerDyeableArmorPiece("bycocket",
            (settings) -> new CustomHelmetItem(ModArmorMaterials.WOOL_T1, settings, ModFactions.NONE), new Item.Settings()
                    .component(ModDataComponentTypes.DYE_DATA, new CustomDyeableDataComponent(-6265536)));

    public static final Item WANDERER_HAT = registerDyeableCustomModelArmorPiece("wanderer_hat",
            (settings) -> new CustomHelmetItem(ModArmorMaterials.FABRIC_T1, settings, ModFactions.NONE), new Item.Settings()
                    .component(ModDataComponentTypes.DYE_DATA, new CustomDyeableDataComponent(5394247)));

    public static final Item ARMING_COAT = registerDyeableArmorPiece("arming_coat",
            (settings) -> new CustomChestplateItem(ModArmorMaterials.FABRIC_T1, settings, ModFactions.NONE), new Item.Settings()
                    .component(ModDataComponentTypes.DYE_DATA, new CustomDyeableDataComponent(15256475)));

    public static final Item ARMING_SKIRT = registerDyeableArmorPiece("arming_skirt",
            (settings) -> new CustomLeggingsItem(ModArmorMaterials.FABRIC_T1, settings, ModFactions.NONE), new Item.Settings()
                    .component(ModDataComponentTypes.DYE_DATA, new CustomDyeableDataComponent(15256475)));

    public static final Item SHOES = registerArmorPiece("shoes",
            (settings) -> new CustomBootsItem(ModArmorMaterials.FABRIC_T1, settings, ModFactions.NONE), new Item.Settings());

    public static final Item WORK_SHOES = registerArmorPiece("work_shoes",
            (settings) -> new CustomBootsItem(ModArmorMaterials.FABRIC_T1, settings, ModFactions.NONE), new Item.Settings());

    public static final Item LEATHER_SKULLCAP = registerDyeableArmorPiece("leather_skullcap",
            (settings) -> new CustomHelmetItem(ModArmorMaterials.LEATHER_T2, settings, ModFactions.NONE), new Item.Settings()
                    .component(ModDataComponentTypes.DYE_DATA, new CustomDyeableDataComponent(-6265536)));

    public static final Item GAMBESON_CAP = registerDyeableArmorPiece("gambeson_cap",
            (settings) -> new CustomHelmetItem(ModArmorMaterials.LEATHER_T2, settings, ModFactions.NONE), new Item.Settings()
                    .component(ModDataComponentTypes.DYE_DATA, new CustomDyeableDataComponent(15256475)));
    public static final Item GAMBESON_COWL = registerDyeableArmorPiece("gambeson_cowl",
            (settings) -> new CustomHelmetItem(ModArmorMaterials.LEATHER_T2, settings, ModFactions.NONE), new Item.Settings()
                    .component(ModDataComponentTypes.DYE_DATA, new CustomDyeableDataComponent( 15256475)));
    public static final Item KETTLE_HAT = registerCustomModelArmorPiece("kettle_hat",
            (settings) -> new CustomHelmetItem(ModArmorMaterials.IRON_T2, settings, ModFactions.NONE), new Item.Settings());

    public static final Item GAMBESON = registerDyeableArmorPiece("gambeson",
            (settings) -> new CustomChestplateItem(ModArmorMaterials.LEATHER_T2, settings, ModFactions.NONE), new Item.Settings()
                    .component(ModDataComponentTypes.DYE_DATA, new CustomDyeableDataComponent(15256475)));

    public static final Item LEATHER_VEST = registerDyeableArmorPiece("leather_vest",
            (settings) -> new CustomChestplateItem(ModArmorMaterials.LEATHER_T2, settings, ModFactions.NONE), new Item.Settings()
                    .component(ModDataComponentTypes.DYE_DATA, new CustomDyeableDataComponent(-6265536)));

    public static final Item LEATHER_SCALE_VEST = registerDyeableArmorPiece("leather_scale_vest",
            (settings) -> new CustomChestplateItem(ModArmorMaterials.LEATHER_T2, settings, ModFactions.NONE), new Item.Settings()
                    .component(ModDataComponentTypes.DYE_DATA, new CustomDyeableDataComponent(-6265536)));

    public static final Item STURDY_BOOTS = registerArmorPiece("sturdy_boots",
            (settings) -> new CustomBootsItem(ModArmorMaterials.LEATHER_T2, settings, ModFactions.NONE), new Item.Settings());
    public static final Item TRAVELLING_BOOTS = registerArmorPiece("travelling_boots",
            (settings) -> new CustomBootsItem(ModArmorMaterials.LEATHER_T2, settings, ModFactions.NONE), new Item.Settings());
    public static final Item HIGH_CUT_BOOTS = registerArmorPiece("high_cut_boots",
            (settings) -> new CustomBootsItem(ModArmorMaterials.LEATHER_T2, settings, ModFactions.NONE), new Item.Settings());

    public static final Item MAIL_COIF = registerArmorPiece("mail_coif",
            (settings) -> new CustomHelmetItem(ModArmorMaterials.IRON_T3, settings, ModFactions.NONE), new Item.Settings());
    public static final Item CLOSED_MAIL_COIF = registerArmorPiece("closed_mail_coif",
            (settings) -> new CustomHelmetItem(ModArmorMaterials.IRON_T3, settings, ModFactions.NONE), new Item.Settings());

    public static final Item KETTLE_HAT_WITH_COIF = registerCustomModelArmorPiece("kettle_hat_with_coif",
            (settings) -> new CustomHelmetItem(ModArmorMaterials.IRON_T3, settings, ModFactions.NONE), new Item.Settings());
    public static final Item KETTLE_HAT_WITH_CLOSED_COIF = registerCustomModelArmorPiece("kettle_hat_with_closed_coif",
            (settings) -> new CustomHelmetItem(ModArmorMaterials.IRON_T3, settings, ModFactions.NONE), new Item.Settings());

    public static final Item OPEN_FACE_HELMET = registerArmorPiece("open_face_helmet",
            (settings) -> new CustomHelmetItem(ModArmorMaterials.IRON_T3, settings, ModFactions.NONE), new Item.Settings());

    public static final Item MAIL_HAUBERK = registerArmorPiece("mail_hauberk",
            (settings) -> new CustomChestplateItem(ModArmorMaterials.IRON_T3, settings, ModFactions.NONE), new Item.Settings());
    public static final Item MAIL_SHIRT = registerArmorPiece("mail_shirt",
            (settings) -> new CustomChestplateItem(ModArmorMaterials.IRON_T3, settings, ModFactions.NONE), new Item.Settings());

    public static final Item MAIL_SKIRT = registerArmorPiece("mail_skirt",
            (settings) -> new CustomLeggingsItem(ModArmorMaterials.IRON_T3, settings, ModFactions.NONE), new Item.Settings());

    public static final Item SALLET = registerCustomModelArmorPiece("sallet",
            (settings) -> new CustomHelmetItem(ModArmorMaterials.STEEL_T4, settings, ModFactions.NONE), new Item.Settings());
    //endregion

    //region HOBBITS
    public static final Item SHIRRIFF_HAT = registerDyeableCustomModelArmorPiece("shirriff_hat",
            (settings) -> new CustomHelmetItem(ModArmorMaterials.LEATHER_T1, settings, ModFactions.SHIRE), new Item.Settings()
                    .component(ModDataComponentTypes.DYE_DATA, new CustomDyeableDataComponent(3440180)));
    //endregion

    //region MEN
    //region GONDOR
    public static final Item GONDORIAN_BOOTS = registerArmorPiece("gondorian_boots",
            (settings) -> new CustomBootsItem(ModArmorMaterials.LEATHER_T2, settings, ModFactions.GONDOR), new Item.Settings());

    public static final Item GONDORIAN_CABASSET_HELMET = registerCustomModelArmorPiece("gondorian_cabasset_helmet",
            (settings) -> new CustomHelmetItem(ModArmorMaterials.IRON_T3, settings, ModFactions.GONDOR), new Item.Settings());

    public static final Item GONDORIAN_MAIL_HAUBERK = registerArmorPiece("gondorian_mail_hauberk",
            (settings) -> new CustomChestplateItem(ModArmorMaterials.IRON_T3, settings, ModFactions.GONDOR), new Item.Settings());
    public static final Item GONDORIAN_TABBARD = registerArmorPiece("gondorian_tabbard",
            (settings) -> new CustomChestplateItem(ModArmorMaterials.IRON_T3, settings, ModFactions.GONDOR), new Item.Settings());
    public static final Item GONDORIAN_LEATHER_CUIRASS = registerDyeableArmorPiece("gondorian_leather_cuirass",
            (settings) -> new CustomChestplateItem(ModArmorMaterials.IRON_T3, settings, ModFactions.GONDOR), new Item.Settings()
                    .component(ModDataComponentTypes.DYE_DATA, new CustomDyeableDataComponent(-6265536)));
    public static final Item GONDORIAN_LEATHER_CHESTPLATE = registerDyeableArmorPiece("gondorian_leather_chestplate",
            (settings) -> new CustomChestplateItem(ModArmorMaterials.IRON_T3, settings, ModFactions.GONDOR), new Item.Settings()
                    .component(ModDataComponentTypes.DYE_DATA, new CustomDyeableDataComponent(-6265536)));

    public static final Item GONDORIAN_MAIL_COAT = registerArmorPiece("gondorian_mail_coat",
            (settings) -> new CustomLeggingsItem(ModArmorMaterials.IRON_T3, settings, ModFactions.GONDOR), new Item.Settings());

    public static final Item GONDORIAN_SOLDIER_HELMET = registerCustomModelArmorPiece("gondorian_soldier_helmet",
            (settings) -> new CustomHelmetItem(ModArmorMaterials.STEEL_T4, settings, ModFactions.GONDOR), new Item.Settings());

    public static final Item GONDORIAN_SOLDIER_CHESTPLATE = registerArmorPiece("gondorian_soldier_chestplate",
            (settings) -> new CustomChestplateItem(ModArmorMaterials.STEEL_T4, settings, ModFactions.GONDOR), new Item.Settings());

    public static final Item GONDORIAN_PLATE_HELMET = registerCustomModelArmorPiece("gondorian_plate_helmet",
            (settings) -> new CustomHelmetItem(ModArmorMaterials.STEEL_T5, settings, ModFactions.GONDOR), new Item.Settings());
    public static final Item GONDORIAN_PLATE_CHESTPLATE = registerCustomModelArmorPiece("gondorian_plate_chestplate",
            (settings) -> new CustomChestplateItem(ModArmorMaterials.STEEL_T5, settings, ModFactions.GONDOR), new Item.Settings());
    public static final Item GONDORIAN_PLATE_LEGGINGS = registerArmorPiece("gondorian_plate_leggings",
            (settings) -> new CustomLeggingsItem(ModArmorMaterials.STEEL_T5, settings, ModFactions.GONDOR), new Item.Settings());
    public static final Item GONDORIAN_PLATE_BOOTS = registerArmorPiece("gondorian_plate_boots",
            (settings) -> new CustomBootsItem(ModArmorMaterials.STEEL_T5, settings, ModFactions.GONDOR), new Item.Settings());

    public static final Item GONDORIAN_CAPTAIN_HELMET = registerCustomModelArmorPiece("gondorian_captain_helmet",
            (settings) -> new CustomHelmetItem(ModArmorMaterials.STEEL_T5, settings, ModFactions.GONDOR), new Item.Settings());

    public static final Item GONDORIAN_KINGS_GUARD_HELMET = registerCustomModelArmorPiece("gondorian_kings_guard_helmet",
            (settings) -> new CustomHelmetItem(ModArmorMaterials.STEEL_T5, settings, ModFactions.GONDOR), new Item.Settings());
    public static final Item GONDORIAN_KINGS_GUARD_CHESTKPLATE = registerCustomModelArmorPiece("gondorian_kings_guard_chestplate",
            (settings) -> new CustomChestplateItem(ModArmorMaterials.STEEL_T5, settings, ModFactions.GONDOR), new Item.Settings()
                    .component(ModDataComponentTypes.CAPE_DATA, CapeDataComponent.newCape(ModCapes.GONDORIAN_KINGS_GUARD_CAPE)));
    public static final Item GONDORIAN_KINGS_GUARD_LEGGINGS = registerArmorPiece("gondorian_kings_guard_leggings",
            (settings) -> new CustomLeggingsItem(ModArmorMaterials.STEEL_T5, settings, ModFactions.GONDOR), new Item.Settings());
    public static final Item GONDORIAN_KINGS_GUARD_BOOTS = registerArmorPiece("gondorian_kings_guard_boots",
            (settings) -> new CustomBootsItem(ModArmorMaterials.STEEL_T5, settings, ModFactions.GONDOR), new Item.Settings());

    public static final Item GONDORIAN_CITADEL_GUARD_HELMET = registerCustomModelArmorPiece("gondorian_citadel_guard_helmet",
            (settings) -> new CustomHelmetItem(ModArmorMaterials.STEEL_T5, settings, ModFactions.GONDOR), new Item.Settings()
                    .component(ModDataComponentTypes.HOOD_DATA, HoodDataComponent.newHoodwithState(true, ModHoods.GONDORIAN_CITADEL_GUARD_HOOD)));
    public static final Item GONDORIAN_CITADEL_GUARD_CHESTPLATE = registerArmorPiece("gondorian_citadel_guard_chestplate",
            (settings) -> new CustomChestplateItem(ModArmorMaterials.STEEL_T5, settings, ModFactions.GONDOR), new Item.Settings()
                    .component(ModDataComponentTypes.CAPE_DATA, CapeDataComponent.newCape(ModCapes.GONDORIAN_CITADEL_GUARD_CAPE)));
    public static final Item GONDORIAN_CITADEL_GUARD_LEGGINGS = registerArmorPiece("gondorian_citadel_guard_leggings",
            (settings) -> new CustomLeggingsItem(ModArmorMaterials.STEEL_T5, settings, ModFactions.GONDOR), new Item.Settings());
    public static final Item GONDORIAN_CITADEL_GUARD_BOOTS = registerArmorPiece("gondorian_citadel_guard_boots",
            (settings) -> new CustomBootsItem(ModArmorMaterials.STEEL_T5, settings, ModFactions.GONDOR), new Item.Settings());

    public static final Item GONDORIAN_FOUNTAIN_GUARD_HELMET = registerCustomModelArmorPiece("gondorian_fountain_guard_helmet",
            (settings) -> new CustomHelmetItem(ModArmorMaterials.STEEL_T5, settings, ModFactions.GONDOR), new Item.Settings());
    public static final Item GONDORIAN_FOUNTAIN_GUARD_CHESTPLATE = registerCustomModelArmorPiece("gondorian_fountain_guard_chestplate",
            (settings) -> new CustomChestplateItem(ModArmorMaterials.STEEL_T5, settings, ModFactions.GONDOR), new Item.Settings()
                    .component(ModDataComponentTypes.CAPE_DATA, CapeDataComponent.newCape(ModCapes.GONDORIAN_FOUNTAIN_GUARD_CAPE)));
    public static final Item GONDORIAN_FOUNTAIN_GUARD_LEGGINGS = registerArmorPiece("gondorian_fountain_guard_leggings",
            (settings) -> new CustomLeggingsItem(ModArmorMaterials.STEEL_T5, settings, ModFactions.GONDOR), new Item.Settings());
    public static final Item GONDORIAN_FOUNTAIN_GUARD_BOOTS = registerArmorPiece("gondorian_fountain_guard_boots",
            (settings) -> new CustomBootsItem(ModArmorMaterials.STEEL_T5, settings, ModFactions.GONDOR), new Item.Settings());

    public static final Item GONDORIAN_CITADEL_GUARD_HOOD = registerHood("gondorian_citadel_guard_hood",
            (settings) -> new HoodHelmetItem(settings, ModArmorMaterials.FABRIC_T0, ModFactions.GONDOR), new Item.Settings()
                    .component(ModDataComponentTypes.HOOD_DATA, HoodDataComponent.newHoodwithState(true, ModHoods.GONDORIAN_CITADEL_GUARD_HOOD)));

    public static final Item GONDORIAN_CAPTAIN_CAPE = registerCape("gondorian_captain_cape",
            (settings) -> new CapeChestplateItem(settings, ModArmorMaterials.FABRIC_T0, ModFactions.GONDOR), new Item.Settings()
                    .component(ModDataComponentTypes.CAPE_DATA, CapeDataComponent.newCape(ModCapes.GONDORIAN_CAPTAIN_CAPE)));
    public static final Item GONDORIAN_HERO_CAPE = registerCape("gondorian_hero_cape",
            (settings) -> new CapeChestplateItem(settings, ModArmorMaterials.FABRIC_T0, ModFactions.GONDOR), new Item.Settings()
                    .component(ModDataComponentTypes.CAPE_DATA, CapeDataComponent.newCape(ModCapes.GONDORIAN_HERO_CAPE)));
    public static final Item GONDORIAN_KINGS_GUARD_CAPE = registerCape("gondorian_kings_guard_cape",
            (settings) -> new CapeChestplateItem(settings, ModArmorMaterials.FABRIC_T0, ModFactions.GONDOR), new Item.Settings()
                    .component(ModDataComponentTypes.CAPE_DATA, CapeDataComponent.newCape(ModCapes.GONDORIAN_KINGS_GUARD_CAPE)));
    public static final Item GONDORIAN_CITADEL_GUARD_CAPE = registerCape("gondorian_citadel_guard_cape",
            (settings) -> new CapeChestplateItem(settings, ModArmorMaterials.FABRIC_T0, ModFactions.GONDOR), new Item.Settings()
                    .component(ModDataComponentTypes.CAPE_DATA, CapeDataComponent.newCape(ModCapes.GONDORIAN_CITADEL_GUARD_CAPE)));
    public static final Item GONDORIAN_FOUNTAIN_GUARD_CAPE = registerCape("gondorian_fountain_guard_cape",
            (settings) -> new CapeChestplateItem(settings, ModArmorMaterials.FABRIC_T0, ModFactions.GONDOR), new Item.Settings()
                    .component(ModDataComponentTypes.CAPE_DATA, CapeDataComponent.newCape(ModCapes.GONDORIAN_FOUNTAIN_GUARD_CAPE)));

    public static final Item GONDORIAN_HORSE_ARMOR = registerGeneratedItem("gondorian_horse_armor",
            (settings) -> new CustomHorseArmorItem(ModArmorMaterials.GONDORIAN_HORSE_ARMOR.material(), AnimalArmorItem.Type.EQUESTRIAN, false, settings, ModFactions.GONDOR), new Item.Settings().maxCount(1));

    //endregion

    //region ROHAN
    public static final Item ROHIRRIC_REINFORCED_COAT = registerDyeableArmorPiece("rohirric_reinforced_coat",
            (settings) -> new CustomChestplateItem(ModArmorMaterials.FABRIC_T1, settings, ModFactions.ROHAN), new Item.Settings()
                    .component(ModDataComponentTypes.DYE_DATA, new CustomDyeableDataComponent( 15256475))
                    .component(ModDataComponentTypes.CAPE_DATA, CapeDataComponent.newCape(ModCapes.ROHIRRIC_CAPE)));

    public static final Item ROHIRRIC_LEATHER_HELMET = registerArmorPiece("rohirric_leather_helmet",
            (settings) -> new CustomHelmetItem(ModArmorMaterials.LEATHER_T2, settings, ModFactions.ROHAN), new Item.Settings());
    public static final Item ROHIRRIC_REINFORCED_LEATHER_HELMET = registerCustomModelArmorPiece("rohirric_reinforced_leather_helmet",
            (settings) -> new CustomHelmetItem(ModArmorMaterials.LEATHER_T2, settings, ModFactions.ROHAN), new Item.Settings());
    public static final Item ROHIRRIC_ORNAMENTED_LEATHER_HELMET = registerArmorPiece("rohirric_ornamented_leather_helmet",
            (settings) -> new CustomHelmetItem(ModArmorMaterials.LEATHER_T2, settings, ModFactions.ROHAN), new Item.Settings());

    public static final Item ROHIRRIC_LEATHER_VEST = registerDyeableArmorPiece("rohirric_leather_vest",
            (settings) -> new CustomChestplateItem(ModArmorMaterials.LEATHER_T2, settings, ModFactions.ROHAN), new Item.Settings()
                    .component(ModDataComponentTypes.DYE_DATA, new CustomDyeableDataComponent(15256475))
                    .component(ModDataComponentTypes.CAPE_DATA, CapeDataComponent.newCape(ModCapes.ROHIRRIC_CAPE)));
    public static final Item ROHIRRIC_LEATHER_SCALE_VEST = registerDyeableArmorPiece("rohirric_leather_scale_vest",
            (settings) -> new CustomChestplateItem(ModArmorMaterials.LEATHER_T2, settings, ModFactions.ROHAN), new Item.Settings()
                    .component(ModDataComponentTypes.DYE_DATA, new CustomDyeableDataComponent(15256475))
                    .component(ModDataComponentTypes.CAPE_DATA, CapeDataComponent.newCape(ModCapes.ROHIRRIC_CAPE)));
    public static final Item ROHIRRIC_GAMBESON = registerDyeableArmorPiece("rohirric_gambeson",
            (settings) -> new CustomChestplateItem(ModArmorMaterials.LEATHER_T2, settings, ModFactions.ROHAN), new Item.Settings()
                    .component(ModDataComponentTypes.DYE_DATA, new CustomDyeableDataComponent(15256475))
                    .component(ModDataComponentTypes.CAPE_DATA, CapeDataComponent.newCape(ModCapes.ROHIRRIC_CAPE)));

    public static final Item ROHIRRIC_MILITIA_HELMET = registerArmorPiece("rohirric_militia_helmet",
            (settings) -> new CustomHelmetItem(ModArmorMaterials.IRON_T3, settings, ModFactions.ROHAN), new Item.Settings());
    public static final Item ROHIRRIC_BRACED_MILITIA_HELMET = registerCustomModelArmorPiece("rohirric_braced_militia_helmet",
            (settings) -> new CustomHelmetItem(ModArmorMaterials.IRON_T3, settings, ModFactions.ROHAN), new Item.Settings());
    public static final Item ROHIRRIC_REINFORCED_MILITIA_HELMET = registerArmorPiece("rohirric_reinforced_militia_helmet",
            (settings) -> new CustomHelmetItem(ModArmorMaterials.IRON_T3, settings, ModFactions.ROHAN), new Item.Settings());
    public static final Item ROHIRRIC_ORNAMENTED_MILITIA_HELMET = registerCustomModelArmorPiece("rohirric_ornamented_militia_helmet",
            (settings) -> new CustomHelmetItem(ModArmorMaterials.IRON_T3, settings, ModFactions.ROHAN), new Item.Settings());

    public static final Item ROHIRRIC_REINFORCED_LEATHER_VEST = registerDyeableArmorPiece("rohirric_reinforced_leather_vest",
            (settings) -> new CustomChestplateItem(ModArmorMaterials.IRON_T3, settings, ModFactions.ROHAN), new Item.Settings()
                    .component(ModDataComponentTypes.DYE_DATA, new CustomDyeableDataComponent(15256475))
                    .component(ModDataComponentTypes.CAPE_DATA, CapeDataComponent.newCape(ModCapes.ROHIRRIC_CAPE)));
    public static final Item ROHIRRIC_BRACED_MAIL_SHIRT = registerDyeableArmorPiece("rohirric_braced_mail_shirt",
            (settings) -> new CustomChestplateItem(ModArmorMaterials.IRON_T3, settings, ModFactions.ROHAN), new Item.Settings()
                    .component(ModDataComponentTypes.DYE_DATA, new CustomDyeableDataComponent(15256475))
                    .component(ModDataComponentTypes.CAPE_DATA, CapeDataComponent.newCape(ModCapes.ROHIRRIC_CAPE)));
    public static final Item ROHIRRIC_REINFORCED_LEATHER_SCALE_VEST = registerArmorPiece("rohirric_reinforced_leather_scale_vest",
            (settings) -> new CustomChestplateItem(ModArmorMaterials.IRON_T3, settings, ModFactions.ROHAN), new Item.Settings()
                    .component(ModDataComponentTypes.CAPE_DATA, CapeDataComponent.newCape(ModCapes.ROHIRRIC_CAPE)));

    public static final Item ROHIRRIC_SOLDIER_HELMET = registerArmorPiece("rohirric_soldier_helmet",
            (settings) -> new CustomHelmetItem(ModArmorMaterials.STEEL_T4, settings, ModFactions.ROHAN), new Item.Settings());
    public static final Item ROHIRRIC_ORNAMENTED_SOLDIER_HELMET = registerCustomModelArmorPiece("rohirric_ornamented_soldier_helmet",
            (settings) -> new CustomHelmetItem(ModArmorMaterials.STEEL_T4, settings, ModFactions.ROHAN), new Item.Settings());

    public static final Item ROHIRRIC_ROYAL_GUARD_HELMET = registerCustomModelArmorPiece("rohirric_royal_guard_helmet",
            (settings) -> new CustomHelmetItem(ModArmorMaterials.STEEL_T4, settings, ModFactions.ROHAN), new Item.Settings());

    public static final Item ROHIRRIC_SCALE_HAUBERK = registerArmorPiece("rohirric_scale_hauberk",
            (settings) -> new CustomChestplateItem(ModArmorMaterials.STEEL_T4, settings, ModFactions.ROHAN), new Item.Settings());
    public static final Item ROHIRRIC_ORNAMENTED_SCALE_HAUBERK = registerArmorPiece("rohirric_ornamented_scale_hauberk",
            (settings) -> new CustomChestplateItem(ModArmorMaterials.STEEL_T4, settings, ModFactions.ROHAN), new Item.Settings()
                    .component(ModDataComponentTypes.CAPE_DATA, CapeDataComponent.newCape(ModCapes.ROHIRRIC_ROYAL_GUARD_CAPE)));

    public static final Item ROHIRRIC_SCALE_JACKET = registerArmorPiece("rohirric_scale_jacket",
            (settings) -> new CustomLeggingsItem(ModArmorMaterials.STEEL_T4, settings, ModFactions.ROHAN), new Item.Settings());

    public static final Item EORLING_MARSHAL_HELMET = registerCustomModelArmorPiece("eorling_marshal_helmet",
            (settings) -> new CustomHelmetItem(ModArmorMaterials.STEEL_T5, settings, ModFactions.ROHAN), new Item.Settings());
    public static final Item EORLING_MARSHAL_CHESTPLATE = registerArmorPiece("eorling_marshal_chestplate",
            (settings) -> new CustomChestplateItem(ModArmorMaterials.STEEL_T5, settings, ModFactions.ROHAN), new Item.Settings()
                    .component(ModDataComponentTypes.CAPE_DATA, CapeDataComponent.newCape(ModCapes.EORLING_MARSHAL_CAPE)));
    public static final Item EORLING_MARSHAL_LEGGINGS = registerArmorPiece("eorling_marshal_leggings",
            (settings) -> new CustomLeggingsItem(ModArmorMaterials.STEEL_T5, settings, ModFactions.ROHAN), new Item.Settings());
    public static final Item EORLING_MARSHAL_BOOTS = registerArmorPiece("eorling_marshal_boots",
            (settings) -> new CustomBootsItem(ModArmorMaterials.STEEL_T5, settings, ModFactions.ROHAN), new Item.Settings());

    public static final Item HORSE_LORD_HELMET = registerCustomModelArmorPiece("horse_lord_helmet",
            (settings) -> new CustomHelmetItem(ModArmorMaterials.STEEL_T5, settings, ModFactions.ROHAN), new Item.Settings());
    public static final Item HORSE_LORD_CHESTPLATE = registerArmorPiece("horse_lord_chestplate",
            (settings) -> new CustomChestplateItem(ModArmorMaterials.STEEL_T5, settings, ModFactions.ROHAN), new Item.Settings()
                    .component(ModDataComponentTypes.CAPE_DATA, CapeDataComponent.newCape(ModCapes.HORSE_LORD_CAPE)));
    public static final Item HORSE_LORD_LEGGINGS = registerArmorPiece("horse_lord_leggings",
            (settings) -> new CustomLeggingsItem(ModArmorMaterials.STEEL_T5, settings, ModFactions.ROHAN), new Item.Settings());
    public static final Item HORSE_LORD_BOOTS = registerArmorPiece("horse_lord_boots",
            (settings) -> new CustomBootsItem(ModArmorMaterials.STEEL_T5, settings, ModFactions.ROHAN), new Item.Settings());

    public static final Item ROHIRRIC_CAPE = registerCape("rohirric_cape",
            (settings) -> new CapeChestplateItem(settings, ModArmorMaterials.FABRIC_T0, ModFactions.ROHAN), new Item.Settings()
                    .component(ModDataComponentTypes.CAPE_DATA, CapeDataComponent.newCape(ModCapes.ROHIRRIC_CAPE)));
    public static final Item ROHIRRIC_ROYAL_GUARD_CAPE = registerCape("rohirric_royal_guard_cape",
            (settings) -> new CapeChestplateItem(settings, ModArmorMaterials.FABRIC_T0, ModFactions.ROHAN), new Item.Settings()
                    .component(ModDataComponentTypes.CAPE_DATA, CapeDataComponent.newCape(ModCapes.ROHIRRIC_ROYAL_GUARD_CAPE)));
    public static final Item EORLING_MARSHAL_CAPE = registerCape("eorling_marshal_cape",
            (settings) -> new CapeChestplateItem(settings, ModArmorMaterials.FABRIC_T0, ModFactions.ROHAN), new Item.Settings()
                    .component(ModDataComponentTypes.CAPE_DATA, CapeDataComponent.newCape(ModCapes.EORLING_MARSHAL_CAPE)));
    public static final Item HORSE_LORD_CAPE = registerCape("horse_lord_cape",
            (settings) -> new CapeChestplateItem(settings, ModArmorMaterials.FABRIC_T0, ModFactions.ROHAN), new Item.Settings()
                    .component(ModDataComponentTypes.CAPE_DATA, CapeDataComponent.newCape(ModCapes.HORSE_LORD_CAPE)));

    public static final Item ROHIRRIC_HORSE_ARMOR = registerGeneratedItem("rohirric_horse_armor",
            (settings) -> new CustomHorseArmorItem(ModArmorMaterials.ROHIRRIC_HORSE_ARMOR.material(), AnimalArmorItem.Type.EQUESTRIAN, false, settings, ModFactions.ROHAN), new Item.Settings().maxCount(1));
    //endregion

    //region DALE
    public static final Item DALISH_ARMING_COAT_BLACK_FUR = registerDyeableArmorPiece("dalish_arming_coat_black_fur",
            (settings) -> new CustomChestplateItem(ModArmorMaterials.FABRIC_T1, settings, ModFactions.DALE), new Item.Settings()
                    .component(ModDataComponentTypes.DYE_DATA, new CustomDyeableDataComponent(7296407)));
    public static final Item DALISH_ARMING_COAT_BROWN_FUR = registerDyeableArmorPiece("dalish_arming_coat_brown_fur",
            (settings) -> new CustomChestplateItem(ModArmorMaterials.FABRIC_T1, settings, ModFactions.DALE), new Item.Settings()
                    .component(ModDataComponentTypes.DYE_DATA, new CustomDyeableDataComponent(7296407)));
    public static final Item DALISH_ARMING_COAT_TAN_FUR = registerDyeableArmorPiece("dalish_arming_coat_tan_fur",
            (settings) -> new CustomChestplateItem(ModArmorMaterials.FABRIC_T1, settings, ModFactions.DALE), new Item.Settings()
                    .component(ModDataComponentTypes.DYE_DATA, new CustomDyeableDataComponent(7296407)));
    public static final Item DALISH_HEYDAY_ARMING_COAT = registerArmorPiece("dalish_heyday_arming_coat",
            (settings) -> new CustomChestplateItem(ModArmorMaterials.FABRIC_T1, settings, ModFactions.DALE), new Item.Settings());

    public static final Item DALISH_BOOTS = registerArmorPiece("dalish_boots",
            (settings) -> new CustomBootsItem(ModArmorMaterials.LEATHER_T2, settings, ModFactions.DALE), new Item.Settings());

    public static final Item DALISH_HELMET = registerCustomModelArmorPiece("dalish_helmet",
            (settings) -> new CustomHelmetItem(ModArmorMaterials.IRON_T3, settings, ModFactions.DALE), new Item.Settings());
    public static final Item DALISH_HELMET_BLACK_FUR = registerCustomModelArmorPiece("dalish_helmet_black_fur",
            (settings) -> new CustomHelmetItem(ModArmorMaterials.IRON_T3, settings, ModFactions.DALE), new Item.Settings());
    public static final Item DALISH_HELMET_BROWN_FUR = registerCustomModelArmorPiece("dalish_helmet_brown_fur",
            (settings) -> new CustomHelmetItem(ModArmorMaterials.IRON_T3, settings, ModFactions.DALE), new Item.Settings());
    public static final Item DALISH_HELMET_TAN_FUR = registerCustomModelArmorPiece("dalish_helmet_tan_fur",
            (settings) -> new CustomHelmetItem(ModArmorMaterials.IRON_T3, settings, ModFactions.DALE), new Item.Settings());

    public static final Item DALISH_MAIL_HAUBERK = registerArmorPiece("dalish_mail_hauberk",
            (settings) -> new CustomChestplateItem(ModArmorMaterials.IRON_T3, settings, ModFactions.DALE), new Item.Settings());

    public static final Item DALISH_MAIL_COAT = registerArmorPiece("dalish_mail_coat",
            (settings) -> new CustomLeggingsItem(ModArmorMaterials.IRON_T3, settings, ModFactions.DALE), new Item.Settings());

    public static final Item DALISH_BURGONET = registerCustomModelArmorPiece("dalish_burgonet",
            (settings) -> new CustomHelmetItem(ModArmorMaterials.STEEL_T4, settings, ModFactions.DALE), new Item.Settings());

    public static final Item DALISH_SCALE_HAUBERK = registerArmorPiece("dalish_scale_hauberk",
            (settings) -> new CustomChestplateItem(ModArmorMaterials.STEEL_T4, settings, ModFactions.DALE), new Item.Settings());

    public static final Item DALISH_HEYDAY_HELMET = registerCustomModelArmorPiece("dalish_heyday_helmet",
            (settings) -> new CustomHelmetItem(ModArmorMaterials.STEEL_T4, settings, ModFactions.DALE), new Item.Settings());
    public static final Item DALISH_HEYDAY_CHESTPLATE = registerArmorPiece("dalish_heyday_chestplate",
            (settings) -> new CustomChestplateItem(ModArmorMaterials.STEEL_T4, settings, ModFactions.DALE), new Item.Settings()
                    .component(ModDataComponentTypes.CAPE_DATA, CapeDataComponent.newCape(ModCapes.DALISH_HEYDAY_CLOAK)));
    public static final Item DALISH_HEYDAY_MAIL_COAT = registerArmorPiece("dalish_heyday_mail_coat",
            (settings) -> new CustomLeggingsItem(ModArmorMaterials.STEEL_T4, settings, ModFactions.DALE), new Item.Settings());
    public static final Item DALISH_HEYDAY_BOOTS = registerArmorPiece("dalish_heyday_boots",
            (settings) -> new CustomBootsItem(ModArmorMaterials.STEEL_T4, settings, ModFactions.DALE), new Item.Settings());

    public static final Item BARDING_SOLDIER_HELMET = registerCustomModelArmorPiece("barding_soldier_helmet",
            (settings) -> new CustomHelmetItem(ModArmorMaterials.STEEL_T5, settings, ModFactions.DALE), new Item.Settings());
    public static final Item BARDING_SOLDIER_CHESTPLATE = registerArmorPiece("barding_soldier_chestplate",
            (settings) -> new CustomChestplateItem(ModArmorMaterials.STEEL_T5, settings, ModFactions.DALE), new Item.Settings());
    public static final Item BARDING_MAIL_SKIRT = registerArmorPiece("barding_mail_skirt",
            (settings) -> new CustomLeggingsItem(ModArmorMaterials.STEEL_T5, settings, ModFactions.DALE), new Item.Settings());
    public static final Item BARDING_PLATED_BOOTS = registerArmorPiece("barding_plated_boots",
            (settings) -> new CustomBootsItem(ModArmorMaterials.STEEL_T5, settings, ModFactions.DALE), new Item.Settings());

    public static final Item BARDING_SERGEANT_HELMET = registerCustomModelArmorPiece("barding_sergeant_helmet",
            (settings) -> new CustomHelmetItem(ModArmorMaterials.STEEL_T5, settings, ModFactions.DALE), new Item.Settings());
    public static final Item BARDING_SERGEANT_CHESTPLATE = registerArmorPiece("barding_sergeant_chestplate",
            (settings) -> new CustomChestplateItem(ModArmorMaterials.STEEL_T5, settings, ModFactions.DALE), new Item.Settings()
                    .component(ModDataComponentTypes.CAPE_DATA, CapeDataComponent.newCape(ModCapes.BARDING_SERGEANT_CAPE)));

    public static final Item BARDING_SURCOAT = registerCape("barding_surcoat",
            (settings) -> new CapeChestplateItem(settings, ModArmorMaterials.FABRIC_T0, ModFactions.DALE), new Item.Settings()
                    .component(ModDataComponentTypes.CAPE_DATA, CapeDataComponent.newCape(ModCapes.BARDING_SURCOAT)));
    public static final Item DALISH_HEYDAY_CLOAK = registerCape("dalish_heyday_cloak",
            (settings) -> new CapeChestplateItem(settings, ModArmorMaterials.FABRIC_T0, ModFactions.DALE), new Item.Settings()
                    .component(ModDataComponentTypes.CAPE_DATA, CapeDataComponent.newCape(ModCapes.DALISH_HEYDAY_CLOAK)));
    public static final Item BARDING_SERGEANT_CAPE = registerCape("barding_sergeant_cape",
            (settings) -> new CapeChestplateItem(settings, ModArmorMaterials.FABRIC_T0, ModFactions.DALE), new Item.Settings()
                    .component(ModDataComponentTypes.CAPE_DATA, CapeDataComponent.newCape(ModCapes.BARDING_SERGEANT_CAPE)));

    public static final Item DALISH_HORSE_ARMOR = registerGeneratedItem("dalish_horse_armor",
            (settings) -> new CustomHorseArmorItem(ModArmorMaterials.DALISH_HORSE_ARMOR.material(), AnimalArmorItem.Type.EQUESTRIAN, false, settings, ModFactions.DALE), new Item.Settings().maxCount(1));

    //endregion

    //endregion

    //region DWARVES

    //region GENERIC
    public static final Item DWARVEN_MINER_HELMET = registerDyeableCustomModelArmorPiece("dwarven_miner_helmet",
            (settings) -> new CustomHelmetItem(ModArmorMaterials.LEATHER_T2, settings, ModFactions.NONE), new Item.Settings()
                    .component(ModDataComponentTypes.DYE_DATA, new CustomDyeableDataComponent(7098174)));

    public static final Item DWARVEN_GAMBESON = registerDyeableArmorPiece("dwarven_gambeson",
            (settings) -> new CustomChestplateItem(ModArmorMaterials.LEATHER_T2, settings, ModFactions.NONE), new Item.Settings()
                    .component(ModDataComponentTypes.DYE_DATA, new CustomDyeableDataComponent(11572859)));

    public static final Item DWARVEN_MAIL_COIF = registerArmorPiece("dwarven_mail_coif",
            (settings) -> new CustomHelmetItem(ModArmorMaterials.IRON_T3, settings, ModFactions.NONE), new Item.Settings());

    public static final Item DWARVEN_MAIL_HAUBERK = registerArmorPiece("dwarven_mail_hauberk",
            (settings) -> new CustomChestplateItem(ModArmorMaterials.IRON_T3, settings, ModFactions.NONE), new Item.Settings());
    public static final Item DWARVEN_MINER_GAMBESON = registerDyeableArmorPiece("dwarven_miner_gambeson",
            (settings) -> new CustomChestplateItem(ModArmorMaterials.IRON_T3, settings, ModFactions.NONE), new Item.Settings()
                    .component(ModDataComponentTypes.DYE_DATA, new CustomDyeableDataComponent(11572859)));

    public static final Item DWARVEN_MAIL_COAT = registerArmorPiece("dwarven_mail_coat",
            (settings) -> new CustomLeggingsItem(ModArmorMaterials.IRON_T3, settings, ModFactions.NONE), new Item.Settings());

    public static final Item DWARVEN_MAIL_CHAUSSES = registerArmorPiece("dwarven_mail_chausses",
            (settings) -> new CustomBootsItem(ModArmorMaterials.IRON_T3, settings, ModFactions.NONE), new Item.Settings());

    public static final Item DWARVEN_BOOTS = registerArmorPiece("dwarven_boots",
            (settings) -> new CustomBootsItem(ModArmorMaterials.IRON_T3, settings, ModFactions.NONE), new Item.Settings());

    public static final Item DWARVEN_SCALE_HAUBERK = registerArmorPiece("dwarven_scale_hauberk",
            (settings) -> new CustomChestplateItem(ModArmorMaterials.DWARVEN_STEEL_T4, settings, ModFactions.NONE), new Item.Settings());

    public static final Item DWARVEN_SCALE_COAT = registerArmorPiece("dwarven_scale_coat",
            (settings) -> new CustomLeggingsItem(ModArmorMaterials.DWARVEN_STEEL_T4, settings, ModFactions.NONE), new Item.Settings());

    public static final Item DWARVEN_REINFORCED_BOOTS = registerArmorPiece("dwarven_reinforced_boots",
            (settings) -> new CustomBootsItem(ModArmorMaterials.DWARVEN_STEEL_T4, settings, ModFactions.NONE), new Item.Settings());
    //endregion

    //region LONGBEARDS
    public static final Item LONGBEARD_SEGMENTED_HELMET = registerArmorPiece("longbeard_segmented_helmet",
            (settings) -> new CustomHelmetItem(ModArmorMaterials.BRONZE_T2, settings, ModFactions.LONGBEARDS), new Item.Settings());

    public static final Item LONGBEARD_LEATHER_HAUBERK = registerDyeableArmorPiece("longbeard_leather_hauberk",
            (settings) -> new CustomChestplateItem(ModArmorMaterials.LEATHER_T2, settings, ModFactions.LONGBEARDS), new Item.Settings()
                    .component(ModDataComponentTypes.DYE_DATA, new CustomDyeableDataComponent(5914168)));
    public static final Item LONGBEARD_WANDERER_COAT = registerDyeableArmorPiece("longbeard_wanderer_coat",
            (settings) -> new CustomChestplateItem(ModArmorMaterials.LEATHER_T2, settings, ModFactions.LONGBEARDS), new Item.Settings()
                    .component(ModDataComponentTypes.DYE_DATA, new CustomDyeableDataComponent(5914168)));

    public static final Item LONGBEARD_LEATHER_LEGGINGS = registerDyeableArmorPiece("longbeard_leather_leggings",
            (settings) -> new CustomLeggingsItem(ModArmorMaterials.LEATHER_T2, settings, ModFactions.LONGBEARDS), new Item.Settings()
                    .component(ModDataComponentTypes.DYE_DATA, new CustomDyeableDataComponent(5914168)));

    public static final Item LONGBEARD_PARTISAN_OUTFIT = registerDyeableArmorPiece("longbeard_partisan_outfit",
            (settings) -> new CustomChestplateItem(ModArmorMaterials.IRON_T3, settings, ModFactions.LONGBEARDS), new Item.Settings()
                    .component(ModDataComponentTypes.DYE_DATA, new CustomDyeableDataComponent(5914168)));
    public static final Item LONGBEARD_REINFORCED_LEATHER_HAUBERK = registerDyeableArmorPiece("longbeard_reinforced_leather_hauberk",
            (settings) -> new CustomChestplateItem(ModArmorMaterials.IRON_T3, settings, ModFactions.LONGBEARDS), new Item.Settings()
                    .component(ModDataComponentTypes.DYE_DATA, new CustomDyeableDataComponent(5914168)));

    //region EREBOR
    public static final Item EREBOR_LEATHER_HELMET = registerDyeableArmorPiece("erebor_leather_helmet",
            (settings) -> new CustomHelmetItem(ModArmorMaterials.IRON_T2, settings, ModSubFactions.EREBOR), new Item.Settings()
                    .component(ModDataComponentTypes.DYE_DATA, new CustomDyeableDataComponent(7098174)));
    public static final Item EREBOR_BRACED_LEATHER_HELMET = registerDyeableArmorPiece("erebor_braced_leather_helmet",
            (settings) -> new CustomHelmetItem(ModArmorMaterials.IRON_T2, settings, ModSubFactions.EREBOR), new Item.Settings()
                    .component(ModDataComponentTypes.DYE_DATA, new CustomDyeableDataComponent(6309430)));
    public static final Item EREBOR_NASAL_LEATHER_HELMET = registerDyeableArmorPiece("erebor_nasal_leather_helmet",
            (settings) -> new CustomHelmetItem(ModArmorMaterials.IRON_T2, settings, ModSubFactions.EREBOR), new Item.Settings()
                    .component(ModDataComponentTypes.DYE_DATA, new CustomDyeableDataComponent(7098174)));

    public static final Item EREBOR_LEATHER_LEGGINGS = registerDyeableArmorPiece("erebor_leather_leggings",
            (settings) -> new CustomLeggingsItem(ModArmorMaterials.LEATHER_T2, settings, ModSubFactions.EREBOR), new Item.Settings()
                    .component(ModDataComponentTypes.DYE_DATA, new CustomDyeableDataComponent(5914168)));

    public static final Item EREBOR_MAIL_COIF = registerArmorPiece("erebor_mail_coif",
            (settings) -> new CustomHelmetItem(ModArmorMaterials.IRON_T3, settings, ModSubFactions.EREBOR), new Item.Settings());
    public static final Item EREBOR_GILDED_MAIL_COIF = registerArmorPiece("erebor_gilded_mail_coif",
            (settings) -> new CustomHelmetItem(ModArmorMaterials.IRON_T3, settings, ModSubFactions.EREBOR), new Item.Settings());
    public static final Item EREBOR_HELMET = registerArmorPiece("erebor_helmet",
            (settings) -> new CustomHelmetItem(ModArmorMaterials.IRON_T3, settings, ModSubFactions.EREBOR), new Item.Settings());

    public static final Item EREBOR_MAIL_HAUBERK = registerArmorPiece("erebor_mail_hauberk",
            (settings) -> new CustomChestplateItem(ModArmorMaterials.IRON_T3, settings, ModSubFactions.EREBOR), new Item.Settings());
    public static final Item EREBOR_PADDED_MAIL_HAUBERK = registerDyeableArmorPiece("erebor_padded_mail_hauberk",
            (settings) -> new CustomChestplateItem(ModArmorMaterials.IRON_T3, settings, ModSubFactions.EREBOR), new Item.Settings()
                    .component(ModDataComponentTypes.DYE_DATA, new CustomDyeableDataComponent(3687263)));

    public static final Item EREBOR_MAIL_COAT = registerArmorPiece("erebor_mail_coat",
            (settings) -> new CustomLeggingsItem(ModArmorMaterials.IRON_T3, settings, ModSubFactions.EREBOR), new Item.Settings());
    public static final Item EREBOR_MAIL_LEGGINGS = registerArmorPiece("erebor_mail_leggings",
            (settings) -> new CustomLeggingsItem(ModArmorMaterials.IRON_T3, settings, ModSubFactions.EREBOR), new Item.Settings());

    public static final Item EREBOR_MAIL_CHAUSSES = registerArmorPiece("erebor_mail_chausses",
            (settings) -> new CustomBootsItem(ModArmorMaterials.IRON_T3, settings, ModSubFactions.EREBOR), new Item.Settings());
    public static final Item EREBOR_BOOTS = registerArmorPiece("erebor_boots",
            (settings) -> new CustomBootsItem(ModArmorMaterials.IRON_T3, settings, ModSubFactions.EREBOR), new Item.Settings());

    public static final Item RAVENHILL_HELMET = registerArmorPiece("ravenhill_helmet",
            (settings) -> new CustomHelmetItem(ModArmorMaterials.DWARVEN_STEEL_T4, settings, ModSubFactions.EREBOR), new Item.Settings());

    public static final Item RAVENHILL_GILDED_HELMET = registerArmorPiece("ravenhill_gilded_helmet",
            (settings) -> new CustomHelmetItem(ModArmorMaterials.DWARVEN_STEEL_T4, settings, ModSubFactions.EREBOR), new Item.Settings());

    public static final Item EREBOR_SCALE_HAUBERK = registerArmorPiece("erebor_scale_hauberk",
            (settings) -> new CustomChestplateItem(ModArmorMaterials.DWARVEN_STEEL_T4, settings, ModSubFactions.EREBOR), new Item.Settings());
    public static final Item EREBOR_LONG_COAT = registerDyeableArmorPiece("erebor_long_coat",
            (settings) -> new CustomChestplateItem(ModArmorMaterials.DWARVEN_STEEL_T4, settings, ModSubFactions.EREBOR), new Item.Settings()
                    .component(ModDataComponentTypes.DYE_DATA, new CustomDyeableDataComponent(5914168)));
    public static final Item EREBOR_BRAWLER_CHESTPLATE = registerDyeableArmorPiece("erebor_brawler_chestplate",
            (settings) -> new CustomChestplateItem(ModArmorMaterials.DWARVEN_STEEL_T4, settings, ModSubFactions.EREBOR), new Item.Settings()
                    .component(ModDataComponentTypes.DYE_DATA, new CustomDyeableDataComponent(3687263)));
    public static final Item EREBOR_REINFORCED_LEATHER_HAUBERK = registerDyeableArmorPiece("erebor_reinforced_leather_hauberk",
            (settings) -> new CustomChestplateItem(ModArmorMaterials.DWARVEN_STEEL_T4, settings, ModSubFactions.EREBOR), new Item.Settings()
                    .component(ModDataComponentTypes.DYE_DATA, new CustomDyeableDataComponent(5914168)));
    public static final Item EREBOR_REINFORCED_COAT = registerDyeableArmorPiece("erebor_reinforced_coat",
            (settings) -> new CustomChestplateItem(ModArmorMaterials.DWARVEN_STEEL_T4, settings, ModSubFactions.EREBOR), new Item.Settings()
                    .component(ModDataComponentTypes.DYE_DATA, new CustomDyeableDataComponent(3687263)));
    public static final Item RAVENHILL_REINFORCED_MAIL_HAUBERK = registerArmorPiece("ravenhill_reinforced_mail_hauberk",
            (settings) -> new CustomChestplateItem(ModArmorMaterials.DWARVEN_STEEL_T4, settings, ModSubFactions.EREBOR), new Item.Settings());

    public static final Item EREBOR_SCALE_COAT = registerArmorPiece("erebor_scale_coat",
            (settings) -> new CustomLeggingsItem(ModArmorMaterials.DWARVEN_STEEL_T4, settings, ModSubFactions.EREBOR), new Item.Settings());
    public static final Item EREBOR_SCALE_LEGGINGS = registerArmorPiece("erebor_scale_leggings",
            (settings) -> new CustomLeggingsItem(ModArmorMaterials.DWARVEN_STEEL_T4, settings, ModSubFactions.EREBOR), new Item.Settings());

    public static final Item RAVENHILL_BOOTS = registerArmorPiece("ravenhill_boots",
            (settings) -> new CustomBootsItem(ModArmorMaterials.DWARVEN_STEEL_T4, settings, ModSubFactions.EREBOR), new Item.Settings());

    public static final Item EREBOR_GUARD_HELMET = registerArmorPiece("erebor_guard_helmet",
            (settings) -> new CustomHelmetItem(ModArmorMaterials.DWARVEN_STEEL_T5, settings, ModSubFactions.EREBOR), new Item.Settings());
    public static final Item EREBOR_CAPTAIN_HELMET = registerCustomModelArmorPiece("erebor_captain_helmet",
            (settings) -> new CustomHelmetItem(ModArmorMaterials.DWARVEN_STEEL_T5, settings, ModSubFactions.EREBOR), new Item.Settings());

    public static final Item EREBOR_PLATE_HELMET = registerCustomModelArmorPiece("erebor_plate_helmet",
            (settings) -> new CustomHelmetItem(ModArmorMaterials.DWARVEN_STEEL_T5, settings, ModSubFactions.EREBOR), new Item.Settings());
    public static final Item EREBOR_PLATE_CHESTPLATE = registerCustomModelArmorPiece("erebor_plate_chestplate",
            (settings) -> new CustomChestplateItem(ModArmorMaterials.DWARVEN_STEEL_T5, settings, ModSubFactions.EREBOR), new Item.Settings());
    public static final Item EREBOR_PLATE_LEGGINGS = registerArmorPiece("erebor_plate_leggings",
            (settings) -> new CustomLeggingsItem(ModArmorMaterials.DWARVEN_STEEL_T5, settings, ModSubFactions.EREBOR), new Item.Settings());
    public static final Item EREBOR_PLATE_BOOTS = registerArmorPiece("erebor_plate_boots",
            (settings) -> new CustomBootsItem(ModArmorMaterials.DWARVEN_STEEL_T5, settings, ModSubFactions.EREBOR), new Item.Settings());

    public static final Item RAVENHILL_WATCHWARDEN_HELMET = registerArmorPiece("ravenhill_watchwarden_helmet",
            (settings) -> new CustomHelmetItem(ModArmorMaterials.DWARVEN_STEEL_T5, settings, ModSubFactions.EREBOR), new Item.Settings());
    public static final Item RAVENHILL_WATCHWARDEN_CHESTPLATE = registerCustomModelArmorPiece("ravenhill_watchwarden_chestplate",
            (settings) -> new CustomChestplateItem(ModArmorMaterials.DWARVEN_STEEL_T5, settings, ModSubFactions.EREBOR), new Item.Settings());
    public static final Item RAVENHILL_WATCHWARDEN_LEGGINGS = registerArmorPiece("ravenhill_watchwarden_leggings",
            (settings) -> new CustomLeggingsItem(ModArmorMaterials.DWARVEN_STEEL_T5, settings, ModSubFactions.EREBOR), new Item.Settings());
    public static final Item RAVENHILL_WATCHWARDEN_BOOTS = registerArmorPiece("ravenhill_watchwarden_boots",
            (settings) -> new CustomBootsItem(ModArmorMaterials.DWARVEN_STEEL_T5, settings, ModSubFactions.EREBOR), new Item.Settings());

    public static final Item RAVENHILL_SENTINEL_HELMET = registerCustomModelArmorPiece("ravenhill_sentinel_helmet",
            (settings) -> new CustomHelmetItem(ModArmorMaterials.DWARVEN_STEEL_T5, settings, ModSubFactions.EREBOR), new Item.Settings());
    public static final Item RAVENHILL_SENTINEL_CHESTPLATE = registerCustomModelArmorPiece("ravenhill_sentinel_chestplate",
            (settings) -> new CustomChestplateItem(ModArmorMaterials.DWARVEN_STEEL_T5, settings, ModSubFactions.EREBOR), new Item.Settings()
                    .component(ModDataComponentTypes.CAPE_DATA, CapeDataComponent.newCape(ModCapes.RAVENHILL_SENTINEL_CAPE)));
    public static final Item RAVENHILL_SENTINEL_LEGGINGS = registerArmorPiece("ravenhill_sentinel_leggings",
            (settings) -> new CustomLeggingsItem(ModArmorMaterials.DWARVEN_STEEL_T5, settings, ModSubFactions.EREBOR), new Item.Settings());
    public static final Item RAVENHILL_SENTINEL_BOOTS = registerArmorPiece("ravenhill_sentinel_boots",
            (settings) -> new CustomBootsItem(ModArmorMaterials.DWARVEN_STEEL_T5, settings, ModSubFactions.EREBOR), new Item.Settings());

    public static final Item EREBOR_GATEWARDEN_HELMET = registerCustomModelArmorPiece("erebor_gatewarden_helmet",
            (settings) -> new CustomHelmetItem(ModArmorMaterials.DWARVEN_STEEL_T5, settings, ModSubFactions.EREBOR), new Item.Settings());
    public static final Item EREBOR_GATEWARDEN_CHESTPLATE = registerCustomModelArmorPiece("erebor_gatewarden_chestplate",
            (settings) -> new CustomChestplateItem(ModArmorMaterials.DWARVEN_STEEL_T5, settings, ModSubFactions.EREBOR), new Item.Settings()
                    .component(ModDataComponentTypes.CAPE_DATA, CapeDataComponent.newCape(ModCapes.EREBOR_CAPE)));
    public static final Item EREBOR_GATEWARDEN_LEGGINGS = registerArmorPiece("erebor_gatewarden_leggings",
            (settings) -> new CustomLeggingsItem(ModArmorMaterials.DWARVEN_STEEL_T5, settings, ModSubFactions.EREBOR), new Item.Settings());
    public static final Item EREBOR_GATEWARDEN_BOOTS = registerArmorPiece("erebor_gatewarden_boots",
            (settings) -> new CustomBootsItem(ModArmorMaterials.DWARVEN_STEEL_T5, settings, ModSubFactions.EREBOR), new Item.Settings());

    public static final Item EREBOR_CAPE = registerCape("erebor_cape",
            (settings) -> new CapeChestplateItem(settings, ModArmorMaterials.FABRIC_T0, ModSubFactions.EREBOR), new Item.Settings()
                    .component(ModDataComponentTypes.CAPE_DATA, CapeDataComponent.newCape(ModCapes.EREBOR_CAPE)));
    public static final Item RAVENHILL_SENTINEL_CAPE = registerCape("ravenhill_sentinel_cape",
            (settings) -> new CapeChestplateItem(settings, ModArmorMaterials.FABRIC_T0, ModSubFactions.EREBOR), new Item.Settings()
                    .component(ModDataComponentTypes.CAPE_DATA, CapeDataComponent.newCape(ModCapes.RAVENHILL_SENTINEL_CAPE)));
    //endregion

    //endregion

    //endregion

    //region ELVES
    //region GENERIC

    public static final Item ELVEN_ARMING_COAT = registerDyeableArmorPiece("elven_arming_coat",
            (settings) -> new CustomChestplateItem(ModArmorMaterials.FABRIC_T1, settings, ModFactions.NONE), new Item.Settings()
                    .component(ModDataComponentTypes.DYE_DATA, new CustomDyeableDataComponent(4805220)));

    public static final Item ELVEN_ARMING_SKIRT = registerDyeableArmorPiece("elven_arming_skirt",
            (settings) -> new CustomLeggingsItem(ModArmorMaterials.FABRIC_T1, settings, ModFactions.NONE), new Item.Settings()
                    .component(ModDataComponentTypes.DYE_DATA, new CustomDyeableDataComponent(4805220)));
    public static final Item ELVEN_OPEN_ARMING_SKIRT = registerDyeableArmorPiece("elven_open_arming_skirt",
            (settings) -> new CustomLeggingsItem(ModArmorMaterials.FABRIC_T1, settings, ModFactions.NONE), new Item.Settings()
                    .component(ModDataComponentTypes.DYE_DATA, new CustomDyeableDataComponent(4805220)));

    public static final Item ELVEN_BOOTS = registerArmorPiece("elven_boots",
            (settings) -> new CustomBootsItem(ModArmorMaterials.LEATHER_T2, settings, ModFactions.NONE), new Item.Settings());

    public static final Item ELVEN_MAIL_COIF = registerArmorPiece("elven_mail_coif",
            (settings) -> new CustomHelmetItem(ModArmorMaterials.IRON_T3, settings, ModFactions.NONE), new Item.Settings());

    public static final Item ELVEN_MAIL_HAUBERK = registerArmorPiece("elven_mail_hauberk",
            (settings) -> new CustomChestplateItem(ModArmorMaterials.IRON_T3, settings, ModFactions.NONE), new Item.Settings());

    public static final Item ELVEN_MAIL_SKIRT = registerArmorPiece("elven_mail_skirt",
            (settings) -> new CustomLeggingsItem(ModArmorMaterials.IRON_T3, settings, ModFactions.NONE), new Item.Settings());
    //endregion

    //region LOTHLORIEN
    public static final Item LORIEN_DIADEM = registerCustomModelArmorPiece("lorien_diadem",
            (settings) -> new CustomHelmetItem(ModArmorMaterials.BRONZE_T1, settings, ModFactions.LOTHLORIEN), new Item.Settings());

    public static final Item LORIEN_LEATHER_HELMET = registerCustomModelArmorPiece("lorien_leather_helmet",
            (settings) -> new CustomHelmetItem(ModArmorMaterials.LEATHER_T2, settings, ModFactions.LOTHLORIEN), new Item.Settings());

    public static final Item LORIEN_ARMING_COAT = registerArmorPiece("lorien_arming_coat",
            (settings) -> new CustomChestplateItem(ModArmorMaterials.LEATHER_T2, settings, ModFactions.LOTHLORIEN), new Item.Settings());

    public static final Item LORIEN_ARMING_SKIRT = registerArmorPiece("lorien_arming_skirt",
            (settings) -> new CustomLeggingsItem(ModArmorMaterials.LEATHER_T2, settings, ModFactions.LOTHLORIEN), new Item.Settings());

    public static final Item LORIEN_MAIL_COIF_DIADEM = registerCustomModelArmorPiece("lorien_mail_coif_diadem",
            (settings) -> new CustomHelmetItem(ModArmorMaterials.IRON_T3, settings, ModFactions.LOTHLORIEN), new Item.Settings());
    public static final Item LORIEN_SHORT_MAIL_COIF_DIADEM = registerCustomModelArmorPiece("lorien_short_mail_coif_diadem",
            (settings) -> new CustomHelmetItem(ModArmorMaterials.IRON_T3, settings, ModFactions.LOTHLORIEN), new Item.Settings());

    public static final Item LORIEN_MAIL_HAUBERK = registerArmorPiece("lorien_mail_hauberk",
            (settings) -> new CustomChestplateItem(ModArmorMaterials.IRON_T3, settings, ModFactions.LOTHLORIEN), new Item.Settings());
    public static final Item LORIEN_MARCHWARDEN_MAIL_HAUBERK = registerArmorPiece("lorien_marchwarden_mail_hauberk",
            (settings) -> new CustomChestplateItem(ModArmorMaterials.IRON_T3, settings, ModFactions.LOTHLORIEN), new Item.Settings()
                    .component(ModDataComponentTypes.CAPE_DATA, CapeDataComponent.newCape(ModCapes.LORIEN_MARCHWARDEN_CAPE)));

    public static final Item LORIEN_SOLDIER_HELMET = registerCustomModelArmorPiece("lorien_soldier_helmet",
            (settings) -> new CustomHelmetItem(ModArmorMaterials.ELVEN_STEEL_T4, settings, ModFactions.LOTHLORIEN), new Item.Settings());

    public static final Item LORIEN_SOLDIER_MAIL_HAUBERK = registerArmorPiece("lorien_soldier_mail_hauberk",
            (settings) -> new CustomChestplateItem(ModArmorMaterials.ELVEN_STEEL_T4, settings, ModFactions.LOTHLORIEN), new Item.Settings());
    public static final Item LORIEN_SOLDIER_SCALE_HAUBERK = registerArmorPiece("lorien_soldier_scale_hauberk",
            (settings) -> new CustomChestplateItem(ModArmorMaterials.ELVEN_STEEL_T4, settings, ModFactions.LOTHLORIEN), new Item.Settings());

    public static final Item LORIEN_SCALE_COAT = registerArmorPiece("lorien_scale_coat",
            (settings) -> new CustomLeggingsItem(ModArmorMaterials.ELVEN_STEEL_T4, settings, ModFactions.LOTHLORIEN), new Item.Settings());

    public static final Item GALADHRIM_HELMET = registerCustomModelArmorPiece("galadhrim_helmet",
            (settings) -> new CustomHelmetItem(ModArmorMaterials.ELVEN_STEEL_T5, settings, ModFactions.LOTHLORIEN), new Item.Settings()
                    .component(ModDataComponentTypes.HOOD_DATA, HoodDataComponent.newHood(ModHoods.GALADHRIM_HOOD)));
    public static final Item GALADHRIM_CHESTPLATE = registerArmorPiece("galadhrim_chestplate",
            (settings) -> new CustomChestplateItem(ModArmorMaterials.ELVEN_STEEL_T5, settings, ModFactions.LOTHLORIEN), new Item.Settings()
                    .component(ModDataComponentTypes.CAPE_DATA, CapeDataComponent.newCape(ModCapes.GALADHRIM_CAPE)));
    public static final Item GALADHRIM_LEGGINGS = registerArmorPiece("galadhrim_leggings",
            (settings) -> new CustomLeggingsItem(ModArmorMaterials.ELVEN_STEEL_T5, settings, ModFactions.LOTHLORIEN), new Item.Settings());
    public static final Item GALADHRIM_BOOTS = registerArmorPiece("galadhrim_boots",
            (settings) -> new CustomBootsItem(ModArmorMaterials.ELVEN_STEEL_T5, settings, ModFactions.LOTHLORIEN), new Item.Settings());

    public static final Item GALADHRIM_LORD_HELMET = registerCustomModelArmorPiece("galadhrim_lord_helmet",
            (settings) -> new CustomHelmetItem(ModArmorMaterials.ELVEN_STEEL_T5, settings, ModFactions.LOTHLORIEN), new Item.Settings());
    public static final Item GALADHRIM_LORD_CHESTPLATE = registerArmorPiece("galadhrim_lord_chestplate",
            (settings) -> new CustomChestplateItem(ModArmorMaterials.ELVEN_STEEL_T5, settings, ModFactions.LOTHLORIEN), new Item.Settings()
                    .component(ModDataComponentTypes.CAPE_DATA, CapeDataComponent.newCape(ModCapes.GALADHRIM_LORD_SURCOAT)));
    public static final Item GALADHRIM_LORD_LEGGINGS = registerArmorPiece("galadhrim_lord_leggings",
            (settings) -> new CustomLeggingsItem(ModArmorMaterials.ELVEN_STEEL_T5, settings, ModFactions.LOTHLORIEN), new Item.Settings());
    public static final Item GALADHRIM_LORD_BOOTS = registerArmorPiece("galadhrim_lord_boots",
            (settings) -> new CustomBootsItem(ModArmorMaterials.ELVEN_STEEL_T5, settings, ModFactions.LOTHLORIEN), new Item.Settings());

    public static final Item LORIEN_MARCHWARDEN_HOOD = registerHood("lorien_marchwarden_hood",
            (settings) -> new HoodHelmetItem(settings, ModArmorMaterials.FABRIC_T0, ModFactions.LOTHLORIEN), new Item.Settings()
                    .component(ModDataComponentTypes.HOOD_DATA, HoodDataComponent.newHood(ModHoods.LORIEN_MARCHWARDEN_HOOD)));
    public static final Item GALADHRIM_HOOD = registerHood("galadhrim_hood",
            (settings) -> new HoodHelmetItem(settings, ModArmorMaterials.FABRIC_T0, ModFactions.LOTHLORIEN), new Item.Settings()
                    .component(ModDataComponentTypes.HOOD_DATA, HoodDataComponent.newHood(ModHoods.GALADHRIM_HOOD)));

    public static final Item LORIEN_MARCHWARDEN_CAPE = registerCape("lorien_marchwarden_cape",
            (settings) -> new CapeChestplateItem(settings, ModArmorMaterials.FABRIC_T0, ModFactions.LOTHLORIEN), new Item.Settings()
                    .component(ModDataComponentTypes.CAPE_DATA, CapeDataComponent.newCape(ModCapes.LORIEN_MARCHWARDEN_CAPE)));
    public static final Item GALADHRIM_CAPE = registerCape("galadhrim_cape",
            (settings) -> new CapeChestplateItem(settings, ModArmorMaterials.FABRIC_T0, ModFactions.LOTHLORIEN), new Item.Settings()
                    .component(ModDataComponentTypes.CAPE_DATA, CapeDataComponent.newCape(ModCapes.GALADHRIM_CAPE)));
    public static final Item GALADHRIM_LORD_SURCOAT = registerCape("galadhrim_lord_surcoat",
            (settings) -> new CapeChestplateItem(settings, ModArmorMaterials.FABRIC_T0, ModFactions.LOTHLORIEN), new Item.Settings()
                    .component(ModDataComponentTypes.CAPE_DATA, CapeDataComponent.newCape(ModCapes.GALADHRIM_LORD_SURCOAT)));

    public static final Item LORIEN_HORSE_ARMOR = registerGeneratedItem("lorien_horse_armor",
            (settings) -> new CustomHorseArmorItem(ModArmorMaterials.LORIEN_HORSE_ARMOR.material(), AnimalArmorItem.Type.EQUESTRIAN, false, settings, ModFactions.LOTHLORIEN), new Item.Settings().maxCount(1));
    //endregion
    //endregion

    //region ORCS

    //region GENERIC
    public static final Item ORCISH_LEATHER_STRAP = registerDyeableArmorPiece("orcish_leather_strap",
            (settings) -> new CustomChestplateItem(ModArmorMaterials.LEATHER_T1, settings, ModFactions.NONE), new Item.Settings()
                    .component(ModDataComponentTypes.DYE_DATA, new CustomDyeableDataComponent(4865076)));

    public static final Item ORCISH_SANDALS = registerDyeableArmorPiece("orcish_sandals",
            (settings) -> new CustomBootsItem(ModArmorMaterials.FABRIC_T1, settings, ModFactions.NONE), new Item.Settings()
                    .component(ModDataComponentTypes.DYE_DATA, new CustomDyeableDataComponent(4865076)));

    public static final Item RUSTED_ORCISH_MAIL_COIF = registerArmorPiece("rusted_orcish_mail_coif",
            (settings) -> new CustomHelmetItem(ModArmorMaterials.IRON_T2, settings, ModFactions.NONE), new Item.Settings());

    public static final Item ORCISH_LEATHER_CHESTPLATE = registerDyeableArmorPiece("orcish_leather_chestplate",
            (settings) -> new CustomChestplateItem(ModArmorMaterials.LEATHER_T2, settings, ModFactions.NONE), new Item.Settings()
                    .component(ModDataComponentTypes.DYE_DATA, new CustomDyeableDataComponent(4865076)));
    public static final Item ORCISH_LEATHER_SCALE_VEST = registerDyeableArmorPiece("orcish_leather_scale_vest",
            (settings) -> new CustomChestplateItem(ModArmorMaterials.LEATHER_T2, settings, ModFactions.NONE), new Item.Settings()
                    .component(ModDataComponentTypes.DYE_DATA, new CustomDyeableDataComponent(4865076)));
    public static final Item RUSTED_ORCISH_MAIL_HAUBERK = registerArmorPiece("rusted_orcish_mail_hauberk",
            (settings) -> new CustomChestplateItem(ModArmorMaterials.IRON_T2, settings, ModFactions.NONE), new Item.Settings());
    public static final Item RUSTED_ORCISH_MAIL_SHIRT = registerArmorPiece("rusted_orcish_mail_shirt",
            (settings) -> new CustomChestplateItem(ModArmorMaterials.IRON_T2, settings, ModFactions.NONE), new Item.Settings());

    public static final Item ORCISH_LEATHER_SKIRT = registerDyeableArmorPiece("orcish_leather_skirt",
            (settings) -> new CustomLeggingsItem(ModArmorMaterials.LEATHER_T2, settings, ModFactions.NONE), new Item.Settings()
                    .component(ModDataComponentTypes.DYE_DATA, new CustomDyeableDataComponent(4865076)));
    public static final Item ORCISH_LEG_BRACER = registerDyeableArmorPiece("orcish_leg_bracer",
            (settings) -> new CustomLeggingsItem(ModArmorMaterials.LEATHER_T2, settings, ModFactions.NONE), new Item.Settings()
                    .component(ModDataComponentTypes.DYE_DATA, new CustomDyeableDataComponent(4865076)));
    public static final Item RUSTED_ORCISH_MAIL_COAT = registerArmorPiece("rusted_orcish_mail_coat",
            (settings) -> new CustomLeggingsItem(ModArmorMaterials.IRON_T2, settings, ModFactions.NONE), new Item.Settings());
    public static final Item RUSTED_ORCISH_REINFORCED_LEATHER_SKIRT = registerDyeableArmorPiece("rusted_orcish_reinforced_leather_skirt",
            (settings) -> new CustomLeggingsItem(ModArmorMaterials.IRON_T2, settings, ModFactions.NONE), new Item.Settings()
                    .component(ModDataComponentTypes.DYE_DATA, new CustomDyeableDataComponent(4865076)));
    public static final Item RUSTED_ORCISH_REINFORCED_STRIP_LEATHER_SKIRT = registerDyeableArmorPiece("rusted_orcish_reinforced_strip_leather_skirt",
            (settings) -> new CustomLeggingsItem(ModArmorMaterials.IRON_T2, settings, ModFactions.NONE), new Item.Settings()
                    .component(ModDataComponentTypes.DYE_DATA, new CustomDyeableDataComponent(4865076)));
    public static final Item ORCISH_STRIP_LEATHER_SKIRT = registerDyeableArmorPiece("orcish_strip_leather_skirt",
            (settings) -> new CustomLeggingsItem(ModArmorMaterials.LEATHER_T2, settings, ModFactions.NONE), new Item.Settings()
                    .component(ModDataComponentTypes.DYE_DATA, new CustomDyeableDataComponent(4865076)));

    public static final Item ORCISH_BLACK_FUR_BOOTS = registerDyeableArmorPiece("orcish_black_fur_boots",
            (settings) -> new CustomBootsItem(ModArmorMaterials.LEATHER_T2, settings, ModFactions.NONE), new Item.Settings()
                    .component(ModDataComponentTypes.DYE_DATA, new CustomDyeableDataComponent(4865076)));
    public static final Item ORCISH_BROWN_FUR_BOOTS = registerDyeableArmorPiece("orcish_brown_fur_boots",
            (settings) -> new CustomBootsItem(ModArmorMaterials.LEATHER_T2, settings, ModFactions.NONE), new Item.Settings()
                    .component(ModDataComponentTypes.DYE_DATA, new CustomDyeableDataComponent(4865076)));
    public static final Item ORCISH_GRAY_FUR_BOOTS = registerDyeableArmorPiece("orcish_gray_fur_boots",
            (settings) -> new CustomBootsItem(ModArmorMaterials.LEATHER_T2, settings, ModFactions.NONE), new Item.Settings()
                    .component(ModDataComponentTypes.DYE_DATA, new CustomDyeableDataComponent(4865076)));
    public static final Item ORCISH_TAN_FUR_BOOTS = registerDyeableArmorPiece("orcish_tan_fur_boots",
            (settings) -> new CustomBootsItem(ModArmorMaterials.LEATHER_T2, settings, ModFactions.NONE), new Item.Settings()
                    .component(ModDataComponentTypes.DYE_DATA, new CustomDyeableDataComponent(4865076)));
    public static final Item ORCISH_WHITE_FUR_BOOTS = registerDyeableArmorPiece("orcish_white_fur_boots",
            (settings) -> new CustomBootsItem(ModArmorMaterials.LEATHER_T2, settings, ModFactions.NONE), new Item.Settings()
                    .component(ModDataComponentTypes.DYE_DATA, new CustomDyeableDataComponent(4865076)));

    public static final Item ORCISH_BRACED_HELMET = registerCustomModelArmorPiece("orcish_braced_helmet",
            (settings) -> new CustomHelmetItem(ModArmorMaterials.IRON_T3, settings, ModFactions.NONE), new Item.Settings());
    public static final Item ORCISH_HELMET = registerArmorPiece("orcish_helmet",
            (settings) -> new CustomHelmetItem(ModArmorMaterials.IRON_T3, settings, ModFactions.NONE), new Item.Settings());
    public static final Item ORCISH_MAIL_COIF = registerArmorPiece("orcish_mail_coif",
            (settings) -> new CustomHelmetItem(ModArmorMaterials.IRON_T3, settings, ModFactions.NONE), new Item.Settings());

    public static final Item ORCISH_MAIL_HAUBERK = registerArmorPiece("orcish_mail_hauberk",
            (settings) -> new CustomChestplateItem(ModArmorMaterials.IRON_T3, settings, ModFactions.NONE), new Item.Settings());
    public static final Item ORCISH_MAIL_SHIRT = registerArmorPiece("orcish_mail_shirt",
            (settings) -> new CustomChestplateItem(ModArmorMaterials.IRON_T3, settings, ModFactions.NONE), new Item.Settings());

    public static final Item ORCISH_MAIL_COAT = registerArmorPiece("orcish_mail_coat",
            (settings) -> new CustomLeggingsItem(ModArmorMaterials.IRON_T3, settings, ModFactions.NONE), new Item.Settings());
    public static final Item ORCISH_REINFORCED_LEATHER_SKIRT = registerDyeableArmorPiece("orcish_reinforced_leather_skirt",
            (settings) -> new CustomLeggingsItem(ModArmorMaterials.IRON_T3, settings, ModFactions.NONE), new Item.Settings()
                    .component(ModDataComponentTypes.DYE_DATA, new CustomDyeableDataComponent(4865076)));
    public static final Item ORCISH_REINFORCED_STRIP_LEATHER_SKIRT = registerDyeableArmorPiece("orcish_reinforced_strip_leather_skirt",
            (settings) -> new CustomLeggingsItem(ModArmorMaterials.IRON_T3, settings, ModFactions.NONE), new Item.Settings()
                    .component(ModDataComponentTypes.DYE_DATA, new CustomDyeableDataComponent(4865076)));

    public static final Item ORCISH_SALLET = registerCustomModelArmorPiece("orcish_sallet",
            (settings) -> new CustomHelmetItem(ModArmorMaterials.BURZUM_STEEL_T4, settings, ModFactions.NONE), new Item.Settings());

    public static final Item ORCISH_BELLY_PLATE_CHESTPLATE = registerDyeableArmorPiece("orcish_belly_plate_chestplate",
            (settings) -> new CustomChestplateItem(ModArmorMaterials.BURZUM_STEEL_T4, settings, ModFactions.NONE), new Item.Settings()
                    .component(ModDataComponentTypes.DYE_DATA, new CustomDyeableDataComponent(4865076)));
    public static final Item ORCISH_SCALE_VEST = registerDyeableArmorPiece("orcish_scale_vest",
            (settings) -> new CustomChestplateItem(ModArmorMaterials.BURZUM_STEEL_T4, settings, ModFactions.NONE), new Item.Settings()
                    .component(ModDataComponentTypes.DYE_DATA, new CustomDyeableDataComponent(4865076)));

    public static final Item ORCISH_REINFORCED_LEG_BRACER = registerArmorPiece("orcish_reinforced_leg_bracer",
            (settings) -> new CustomLeggingsItem(ModArmorMaterials.BURZUM_STEEL_T4, settings, ModFactions.NONE), new Item.Settings());

    public static final Item ORCISH_BRACED_SANDALS = registerDyeableArmorPiece("orcish_braced_sandals",
            (settings) -> new CustomBootsItem(ModArmorMaterials.BURZUM_STEEL_T4, settings, ModFactions.NONE), new Item.Settings()
                    .component(ModDataComponentTypes.DYE_DATA, new CustomDyeableDataComponent(4865076)));

    public static final Item ORCISH_CAPE = registerCape("orcish_cape",
            (settings) -> new CapeChestplateItem(settings, ModArmorMaterials.FABRIC_T0, ModFactions.NONE), new Item.Settings()
                    .component(ModDataComponentTypes.CAPE_DATA, CapeDataComponent.newCape(ModCapes.ORCISH_CAPE)));
    public static final Item ORCISH_LONG_CAPE = registerCape("orcish_long_cape",
            (settings) -> new CapeChestplateItem(settings, ModArmorMaterials.FABRIC_T0, ModFactions.NONE), new Item.Settings()
                    .component(ModDataComponentTypes.CAPE_DATA, CapeDataComponent.newCape(ModCapes.ORCISH_LONG_CAPE)));
    public static final Item ORCISH_SHOULDERS = registerDyeableCape("orcish_shoulders",
            (settings) -> new CapeChestplateItem(settings, ModArmorMaterials.FABRIC_T0, ModFactions.NONE), new Item.Settings()
                    .component(ModDataComponentTypes.CAPE_DATA, CapeDataComponent.newCape(ModCapes.ORCISH_SHOULDERS))
                    .component(ModDataComponentTypes.DYE_DATA, new CustomDyeableDataComponent(5186845)));

    public static final Item ORCISH_BLACK_FUR_SURCOAT_WITH_BONE = registerCape("orcish_black_fur_surcoat_with_bone",
            (settings) -> new CapeChestplateItem(settings, ModArmorMaterials.FUR_T0, ModFactions.NONE), new Item.Settings()
                    .component(ModDataComponentTypes.CAPE_DATA, CapeDataComponent.newCape(ModCapes.ORCISH_BLACK_FUR_SURCOAT_WITH_BONE)));
    public static final Item ORCISH_BROWN_FUR_SURCOAT_WITH_BONE = registerCape("orcish_brown_fur_surcoat_with_bone",
            (settings) -> new CapeChestplateItem(settings, ModArmorMaterials.FUR_T0, ModFactions.NONE), new Item.Settings()
                    .component(ModDataComponentTypes.CAPE_DATA, CapeDataComponent.newCape(ModCapes.ORCISH_BROWN_FUR_SURCOAT_WITH_BONE)));
    public static final Item ORCISH_GRAY_FUR_SURCOAT_WITH_BONE = registerCape("orcish_gray_fur_surcoat_with_bone",
            (settings) -> new CapeChestplateItem(settings, ModArmorMaterials.FUR_T0, ModFactions.NONE), new Item.Settings()
                    .component(ModDataComponentTypes.CAPE_DATA, CapeDataComponent.newCape(ModCapes.ORCISH_GRAY_FUR_SURCOAT_WITH_BONE)));
    public static final Item ORCISH_TAN_FUR_SURCOAT_WITH_BONE = registerCape("orcish_tan_fur_surcoat_with_bone",
            (settings) -> new CapeChestplateItem(settings, ModArmorMaterials.FUR_T0, ModFactions.NONE), new Item.Settings()
                    .component(ModDataComponentTypes.CAPE_DATA, CapeDataComponent.newCape(ModCapes.ORCISH_TAN_FUR_SURCOAT_WITH_BONE)));
    public static final Item ORCISH_WHITE_FUR_SURCOAT_WITH_BONE = registerCape("orcish_white_fur_surcoat_with_bone",
            (settings) -> new CapeChestplateItem(settings, ModArmorMaterials.FUR_T0, ModFactions.NONE), new Item.Settings()
                    .component(ModDataComponentTypes.CAPE_DATA, CapeDataComponent.newCape(ModCapes.ORCISH_WHITE_FUR_SURCOAT_WITH_BONE)));
    //endregion

    //region MORDOR
    public static final Item RUSTED_MORDOR_HELMET = registerArmorPiece("rusted_mordor_helmet",
            (settings) -> new CustomHelmetItem(ModArmorMaterials.CRUDE_T2, settings, ModFactions.MORDOR), new Item.Settings());
    public static final Item RUSTED_MORDOR_KETTLE_HAT = registerCustomModelArmorPiece("rusted_mordor_kettle_hat",
            (settings) -> new CustomHelmetItem(ModArmorMaterials.CRUDE_T2, settings, ModFactions.MORDOR), new Item.Settings());
    public static final Item RUSTED_MORDOR_KETTLE_HAT_WITH_COIF = registerCustomModelArmorPiece("rusted_mordor_kettle_hat_with_coif",
            (settings) -> new CustomHelmetItem(ModArmorMaterials.CRUDE_T2, settings, ModFactions.MORDOR), new Item.Settings());
    public static final Item RUSTED_MORDOR_NASAL_HELMET = registerArmorPiece("rusted_mordor_nasal_helmet",
            (settings) -> new CustomHelmetItem(ModArmorMaterials.CRUDE_T2, settings, ModFactions.MORDOR), new Item.Settings());

    public static final Item MORDOR_LEATHER_CUIRASS = registerDyeableArmorPiece("mordor_leather_cuirass",
            (settings) -> new CustomChestplateItem(ModArmorMaterials.LEATHER_T2, settings, ModFactions.MORDOR), new Item.Settings()
                    .component(ModDataComponentTypes.DYE_DATA, new CustomDyeableDataComponent(5186845)));
    public static final Item MORDOR_PAINTED_LEATHER_CUIRASS = registerDyeableArmorPiece("mordor_painted_leather_cuirass",
            (settings) -> new CustomChestplateItem(ModArmorMaterials.LEATHER_T2, settings, ModFactions.MORDOR), new Item.Settings()
                    .component(ModDataComponentTypes.DYE_DATA, new CustomDyeableDataComponent(5186845)));

    public static final Item MORDOR_HELMET = registerArmorPiece("mordor_helmet",
            (settings) -> new CustomHelmetItem(ModArmorMaterials.CRUDE_T3, settings, ModFactions.MORDOR), new Item.Settings());
    public static final Item MORDOR_KETTLE_HAT = registerCustomModelArmorPiece("mordor_kettle_hat",
            (settings) -> new CustomHelmetItem(ModArmorMaterials.CRUDE_T3, settings, ModFactions.MORDOR), new Item.Settings());
    public static final Item MORDOR_KETTLE_HAT_WITH_COIF = registerCustomModelArmorPiece("mordor_kettle_hat_with_coif",
            (settings) -> new CustomHelmetItem(ModArmorMaterials.CRUDE_T3, settings, ModFactions.MORDOR), new Item.Settings());
    public static final Item MORDOR_NASAL_HELMET = registerArmorPiece("mordor_nasal_helmet",
            (settings) -> new CustomHelmetItem(ModArmorMaterials.CRUDE_T3, settings, ModFactions.MORDOR), new Item.Settings());
    public static final Item RUSTED_MORDOR_CREST_HELMET = registerCustomModelArmorPiece("rusted_mordor_crest_helmet",
            (settings) -> new CustomHelmetItem(ModArmorMaterials.BURZUM_STEEL_T3, settings, ModFactions.MORDOR), new Item.Settings());
    public static final Item RUSTED_MORDOR_MANDIBLE_HELMET = registerArmorPiece("rusted_mordor_mandible_helmet",
            (settings) -> new CustomHelmetItem(ModArmorMaterials.BURZUM_STEEL_T3, settings, ModFactions.MORDOR), new Item.Settings());
    public static final Item RUSTED_MORDOR_SALLET = registerCustomModelArmorPiece("rusted_mordor_sallet",
            (settings) -> new CustomHelmetItem(ModArmorMaterials.BURZUM_STEEL_T3, settings, ModFactions.MORDOR), new Item.Settings());

    public static final Item RUSTED_MORDOR_CHESTPLATE = registerArmorPiece("rusted_mordor_chestplate",
            (settings) -> new CustomChestplateItem(ModArmorMaterials.BURZUM_STEEL_T3, settings, ModFactions.MORDOR), new Item.Settings());
    public static final Item RUSTED_MORDOR_CUIRASS = registerDyeableArmorPiece("rusted_mordor_cuirass",
            (settings) -> new CustomChestplateItem(ModArmorMaterials.BURZUM_STEEL_T3, settings, ModFactions.MORDOR), new Item.Settings()
                    .component(ModDataComponentTypes.DYE_DATA, new CustomDyeableDataComponent(5186845)));
    public static final Item RUSTED_MORDOR_GORGET_HAUBERK = registerArmorPiece("rusted_mordor_gorget_hauberk",
            (settings) -> new CustomChestplateItem(ModArmorMaterials.BURZUM_STEEL_T3, settings, ModFactions.MORDOR), new Item.Settings());
    public static final Item RUSTED_MORDOR_REINFORCED_COAT = registerDyeableArmorPiece("rusted_mordor_reinforced_coat",
            (settings) -> new CustomChestplateItem(ModArmorMaterials.BURZUM_STEEL_T3, settings, ModFactions.MORDOR), new Item.Settings()
                    .component(ModDataComponentTypes.DYE_DATA, new CustomDyeableDataComponent(5186845)));

    public static final Item RUSTED_MORDOR_PLATE_BOOTS = registerArmorPiece("rusted_mordor_plate_boots",
            (settings) -> new CustomBootsItem(ModArmorMaterials.BURZUM_STEEL_T3, settings, ModFactions.MORDOR), new Item.Settings());

    public static final Item MORDOR_CREST_HELMET = registerCustomModelArmorPiece("mordor_crest_helmet",
            (settings) -> new CustomHelmetItem(ModArmorMaterials.BURZUM_STEEL_T4, settings, ModFactions.MORDOR), new Item.Settings());
    public static final Item MORDOR_MANDIBLE_HELMET = registerArmorPiece("mordor_mandible_helmet",
            (settings) -> new CustomHelmetItem(ModArmorMaterials.BURZUM_STEEL_T4, settings, ModFactions.MORDOR), new Item.Settings());
    public static final Item MORDOR_SALLET = registerCustomModelArmorPiece("mordor_sallet",
            (settings) -> new CustomHelmetItem(ModArmorMaterials.BURZUM_STEEL_T4, settings, ModFactions.MORDOR), new Item.Settings());
    public static final Item RUSTED_MORDOR_GREAT_HELMET = registerCustomModelArmorPiece("rusted_mordor_great_helmet",
            (settings) -> new CustomHelmetItem(ModArmorMaterials.BURZUM_STEEL_T4, settings, ModFactions.MORDOR), new Item.Settings());
    public static final Item RUSTED_MORDOR_SNOUT_HELMET = registerCustomModelArmorPiece("rusted_mordor_snout_helmet",
            (settings) -> new CustomHelmetItem(ModArmorMaterials.BURZUM_STEEL_T4, settings, ModFactions.MORDOR), new Item.Settings());

    public static final Item MORDOR_CHESTPLATE = registerArmorPiece("mordor_chestplate",
            (settings) -> new CustomChestplateItem(ModArmorMaterials.BURZUM_STEEL_T4, settings, ModFactions.MORDOR), new Item.Settings());
    public static final Item MORDOR_CUIRASS = registerDyeableArmorPiece("mordor_cuirass",
            (settings) -> new CustomChestplateItem(ModArmorMaterials.BURZUM_STEEL_T4, settings, ModFactions.MORDOR), new Item.Settings()
                    .component(ModDataComponentTypes.DYE_DATA, new CustomDyeableDataComponent(5186845)));
    public static final Item MORDOR_DEGRADED_GONDORIAN_CHESTPLATE = registerDyeableArmorPiece("mordor_degraded_gondorian_chestplate",
            (settings) -> new CustomChestplateItem(ModArmorMaterials.BURZUM_STEEL_T4, settings, ModFactions.MORDOR), new Item.Settings()
                    .component(ModDataComponentTypes.DYE_DATA, new CustomDyeableDataComponent(5186845)));
    public static final Item MORDOR_GORGET_HAUBERK = registerArmorPiece("mordor_gorget_hauberk",
            (settings) -> new CustomChestplateItem(ModArmorMaterials.BURZUM_STEEL_T4, settings, ModFactions.MORDOR), new Item.Settings());
    public static final Item MORDOR_PAINTED_CUIRASS = registerDyeableArmorPiece("mordor_painted_cuirass",
            (settings) -> new CustomChestplateItem(ModArmorMaterials.BURZUM_STEEL_T4, settings, ModFactions.MORDOR), new Item.Settings()
                    .component(ModDataComponentTypes.DYE_DATA, new CustomDyeableDataComponent(5186845)));
    public static final Item MORDOR_REINFORCED_COAT = registerDyeableArmorPiece("mordor_reinforced_coat",
            (settings) -> new CustomChestplateItem(ModArmorMaterials.BURZUM_STEEL_T4, settings, ModFactions.MORDOR), new Item.Settings()
                    .component(ModDataComponentTypes.DYE_DATA, new CustomDyeableDataComponent(5186845)));

    public static final Item MORDOR_SCALE_COAT = registerArmorPiece("mordor_scale_coat",
            (settings) -> new CustomLeggingsItem(ModArmorMaterials.BURZUM_STEEL_T4, settings, ModFactions.MORDOR), new Item.Settings());

    public static final Item MORDOR_PLATE_BOOTS = registerArmorPiece("mordor_plate_boots",
            (settings) -> new CustomBootsItem(ModArmorMaterials.BURZUM_STEEL_T4, settings, ModFactions.MORDOR), new Item.Settings());

    public static final Item MORDOR_GREAT_HELMET = registerCustomModelArmorPiece("mordor_great_helmet",
            (settings) -> new CustomHelmetItem(ModArmorMaterials.BURZUM_STEEL_T5, settings, ModFactions.MORDOR), new Item.Settings());
    public static final Item MORDOR_SNOUT_HELMET = registerCustomModelArmorPiece("mordor_snout_helmet",
            (settings) -> new CustomHelmetItem(ModArmorMaterials.BURZUM_STEEL_T5, settings, ModFactions.MORDOR), new Item.Settings());

    public static final Item BLACK_URUK_PLATE_HELMET = registerArmorPiece("black_uruk_plate_helmet",
            (settings) -> new CustomHelmetItem(ModArmorMaterials.BURZUM_STEEL_T5, settings, ModFactions.MORDOR), new Item.Settings());
    public static final Item BLACK_URUK_FACE_PLATE_HELMET = registerArmorPiece("black_uruk_face_plate_helmet",
            (settings) -> new CustomHelmetItem(ModArmorMaterials.BURZUM_STEEL_T5, settings, ModFactions.MORDOR), new Item.Settings());
    public static final Item BLACK_URUK_PLATE_CHESTPLATE = registerCustomModelArmorPiece("black_uruk_plate_chestplate",
            (settings) -> new CustomChestplateItem(ModArmorMaterials.BURZUM_STEEL_T5, settings, ModFactions.MORDOR), new Item.Settings());
    public static final Item BLACK_URUK_PLATE_LEGGINGS = registerArmorPiece("black_uruk_plate_leggings",
            (settings) -> new CustomLeggingsItem(ModArmorMaterials.BURZUM_STEEL_T5, settings, ModFactions.MORDOR), new Item.Settings());
    public static final Item BLACK_URUK_PLATE_BOOTS = registerArmorPiece("black_uruk_plate_boots",
            (settings) -> new CustomBootsItem(ModArmorMaterials.BURZUM_STEEL_T5, settings, ModFactions.MORDOR), new Item.Settings());

    public static final Item BLACK_URUK_COMMANDER_HELMET = registerCustomModelArmorPiece("black_uruk_commander_helmet",
            (settings) -> new CustomHelmetItem(ModArmorMaterials.BURZUM_STEEL_T5, settings, ModFactions.MORDOR), new Item.Settings());
    public static final Item BLACK_URUK_COMMANDER_CHESTPLATE = registerCustomModelArmorPiece("black_uruk_commander_chestplate",
            (settings) -> new CustomChestplateItem(ModArmorMaterials.BURZUM_STEEL_T5, settings, ModFactions.MORDOR), new Item.Settings());

    public static final Item MORDOR_BLACK_NUMENOREAN_PLATE_HELMET = registerCustomModelArmorPiece("mordor_black_numenorean_plate_helmet",
            (settings) -> new CustomHelmetItem(ModArmorMaterials.STEEL_T5, settings, ModSubFactions.MORDOR_BLACK_NUMENOREANS), new Item.Settings());
    public static final Item MORDOR_BLACK_NUMENOREAN_PLATE_CHESTPLATE = registerCustomModelArmorPiece("mordor_black_numenorean_plate_chestplate",
            (settings) -> new CustomChestplateItem(ModArmorMaterials.STEEL_T5, settings, ModSubFactions.MORDOR_BLACK_NUMENOREANS), new Item.Settings()
                    .component(ModDataComponentTypes.CAPE_DATA, CapeDataComponent.newCape(ModCapes.MORDOR_BLACK_NUMENOREAN_CAPE)));
    public static final Item MORDOR_BLACK_NUMENOREAN_PLATE_LEGGINGS = registerArmorPiece("mordor_black_numenorean_plate_leggings",
            (settings) -> new CustomLeggingsItem(ModArmorMaterials.STEEL_T5, settings, ModSubFactions.MORDOR_BLACK_NUMENOREANS), new Item.Settings());
    public static final Item MORDOR_BLACK_NUMENOREAN_PLATE_BOOTS = registerArmorPiece("mordor_black_numenorean_plate_boots",
            (settings) -> new CustomBootsItem(ModArmorMaterials.STEEL_T5, settings, ModSubFactions.MORDOR_BLACK_NUMENOREANS), new Item.Settings());

    public static final Item MORDOR_BLACK_NUMENOREAN_CAPE = registerCape("mordor_black_numenorean_cape",
            (settings) -> new CapeChestplateItem(settings, ModArmorMaterials.FABRIC_T0, ModSubFactions.MORDOR_BLACK_NUMENOREANS), new Item.Settings()
                    .component(ModDataComponentTypes.CAPE_DATA, CapeDataComponent.newCape(ModCapes.MORDOR_BLACK_NUMENOREAN_CAPE)));

    public static final Item NAZGUL_HOOD = registerHood("nazgul_hood",
            (settings) -> new HoodHelmetItem(settings, ModArmorMaterials.FABRIC_T0, ModFactions.MORDOR), new Item.Settings()
                    .component(ModDataComponentTypes.HOOD_DATA, HoodDataComponent.newHood(ModHoods.NAZGUL_HOOD)));
    public static final Item NAZGUL_ROBES = registerCape("nazgul_robes",
            (settings) -> new CapeChestplateItem(settings, ModArmorMaterials.FABRIC_T0, ModFactions.MORDOR), new Item.Settings()
                    .component(ModDataComponentTypes.CAPE_DATA, CapeDataComponent.newCape(ModCapes.NAZGUL_ROBES)));

    public static final Item RINGWRAITH_MAIL_HAUBERK = registerArmorPiece("ringwraith_mail_hauberk",
            (settings) -> new CustomChestplateItem(ModArmorMaterials.STEEL_T5, settings, ModFactions.MORDOR), new Item.Settings());
    public static final Item RINGWRAITH_LEGGINGS = registerArmorPiece("ringwraith_leggings",
            (settings) -> new CustomLeggingsItem(ModArmorMaterials.STEEL_T5, settings, ModFactions.MORDOR), new Item.Settings());
    public static final Item RINGWRAITH_BOOTS = registerArmorPiece("ringwraith_boots",
            (settings) -> new CustomBootsItem(ModArmorMaterials.STEEL_T5, settings, ModFactions.MORDOR), new Item.Settings());
    //endregion

    //region ISENGARD
    public static final Item URUK_HAI_LEATHER_SCOUT_CAP = registerCustomModelArmorPiece("uruk_hai_leather_scout_cap",
            (settings) -> new CustomHelmetItem(ModArmorMaterials.LEATHER_T2, settings, ModFactions.ISENGARD), new Item.Settings());

    public static final Item URUK_HAI_LEATHER_CHESTPLATE = registerArmorPiece("uruk_hai_leather_chestplate",
            (settings) -> new CustomChestplateItem(ModArmorMaterials.LEATHER_T2, settings, ModFactions.ISENGARD), new Item.Settings());
    public static final Item URUK_HAI_PAINTED_LEATHER_CHESTPLATE = registerArmorPiece("uruk_hai_painted_leather_chestplate",
            (settings) -> new CustomChestplateItem(ModArmorMaterials.LEATHER_T2, settings, ModFactions.ISENGARD), new Item.Settings());
    public static final Item URUK_HAI_LEATHER_HAUBERK = registerArmorPiece("uruk_hai_leather_hauberk",
            (settings) -> new CustomChestplateItem(ModArmorMaterials.LEATHER_T2, settings, ModFactions.ISENGARD), new Item.Settings());

    public static final Item URUK_HAI_BOOTS = registerArmorPiece("uruk_hai_boots",
            (settings) -> new CustomBootsItem(ModArmorMaterials.LEATHER_T2, settings, ModFactions.ISENGARD), new Item.Settings());

    public static final Item URUK_HAI_LIGHT_HELMET = registerCustomModelArmorPiece("uruk_hai_light_helmet",
            (settings) -> new CustomHelmetItem(ModArmorMaterials.CRUDE_T3, settings, ModFactions.ISENGARD), new Item.Settings());
    public static final Item URUK_HAI_PAINTED_LIGHT_HELMET = registerCustomModelArmorPiece("uruk_hai_painted_light_helmet",
            (settings) -> new CustomHelmetItem(ModArmorMaterials.CRUDE_T3, settings, ModFactions.ISENGARD), new Item.Settings());
    public static final Item URUK_HAI_REINFORCED_HELMET = registerCustomModelArmorPiece("uruk_hai_reinforced_helmet",
            (settings) -> new CustomHelmetItem(ModArmorMaterials.CRUDE_T3, settings, ModFactions.ISENGARD), new Item.Settings());
    public static final Item URUK_HAI_PAINTED_REINFORCED_HELMET = registerCustomModelArmorPiece("uruk_hai_painted_reinforced_helmet",
            (settings) -> new CustomHelmetItem(ModArmorMaterials.CRUDE_T3, settings, ModFactions.ISENGARD), new Item.Settings());

    public static final Item URUK_HAI_MAIL_HAUBERK = registerArmorPiece("uruk_hai_mail_hauberk",
            (settings) -> new CustomChestplateItem(ModArmorMaterials.CRUDE_T3, settings, ModFactions.ISENGARD), new Item.Settings());

    public static final Item URUK_HAI_MAIL_COAT = registerArmorPiece("uruk_hai_mail_coat",
            (settings) -> new CustomLeggingsItem(ModArmorMaterials.CRUDE_T3, settings, ModFactions.ISENGARD), new Item.Settings());
    public static final Item URUK_HAI_MAIL_SKIRT = registerArmorPiece("uruk_hai_mail_skirt",
            (settings) -> new CustomLeggingsItem(ModArmorMaterials.CRUDE_T3, settings, ModFactions.ISENGARD), new Item.Settings());

    public static final Item URUK_HAI_SOLDIER_HELMET = registerCustomModelArmorPiece("uruk_hai_soldier_helmet",
            (settings) -> new CustomHelmetItem(ModArmorMaterials.BURZUM_STEEL_T4, settings, ModFactions.ISENGARD), new Item.Settings());
    public static final Item URUK_HAI_PAINTED_SOLDIER_HELMET = registerCustomModelArmorPiece("uruk_hai_painted_soldier_helmet",
            (settings) -> new CustomHelmetItem(ModArmorMaterials.BURZUM_STEEL_T4, settings, ModFactions.ISENGARD), new Item.Settings());

    public static final Item URUK_HAI_CUIRASS = registerArmorPiece("uruk_hai_cuirass",
            (settings) -> new CustomChestplateItem(ModArmorMaterials.BURZUM_STEEL_T4, settings, ModFactions.ISENGARD), new Item.Settings());

    public static final Item URUK_HAI_PLATE_HELMET = registerCustomModelArmorPiece("uruk_hai_plate_helmet",
            (settings) -> new CustomHelmetItem(ModArmorMaterials.BURZUM_STEEL_T5, settings, ModFactions.ISENGARD), new Item.Settings());
    public static final Item URUK_HAI_PAINTED_PLATE_HELMET = registerCustomModelArmorPiece("uruk_hai_painted_plate_helmet",
            (settings) -> new CustomHelmetItem(ModArmorMaterials.BURZUM_STEEL_T5, settings, ModFactions.ISENGARD), new Item.Settings());
    public static final Item URUK_HAI_SAPPER_HELMET = registerCustomModelArmorPiece("uruk_hai_sapper_helmet",
            (settings) -> new CustomHelmetItem(ModArmorMaterials.BURZUM_STEEL_T5, settings, ModFactions.ISENGARD), new Item.Settings());
    public static final Item URUK_HAI_PAINTED_SAPPER_HELMET = registerCustomModelArmorPiece("uruk_hai_painted_sapper_helmet",
            (settings) -> new CustomHelmetItem(ModArmorMaterials.BURZUM_STEEL_T5, settings, ModFactions.ISENGARD), new Item.Settings());
    public static final Item URUK_HAI_BERSERKER_HELMET = registerCustomModelArmorPiece("uruk_hai_berserker_helmet",
            (settings) -> new CustomHelmetItem(ModArmorMaterials.BURZUM_STEEL_T5, settings, ModFactions.ISENGARD), new Item.Settings());
    public static final Item URUK_HAI_PAINTED_BERSERKER_HELMET = registerCustomModelArmorPiece("uruk_hai_painted_berserker_helmet",
            (settings) -> new CustomHelmetItem(ModArmorMaterials.BURZUM_STEEL_T5, settings, ModFactions.ISENGARD), new Item.Settings());
    public static final Item URUK_HAI_COMMANDER_HELMET = registerCustomModelArmorPiece("uruk_hai_commander_helmet",
            (settings) -> new CustomHelmetItem(ModArmorMaterials.BURZUM_STEEL_T5, settings, ModFactions.ISENGARD), new Item.Settings());
    public static final Item URUK_HAI_PAINTED_COMMANDER_HELMET = registerCustomModelArmorPiece("uruk_hai_painted_commander_helmet",
            (settings) -> new CustomHelmetItem(ModArmorMaterials.BURZUM_STEEL_T5, settings, ModFactions.ISENGARD), new Item.Settings());
    public static final Item URUK_HAI_PLATE_CHESTPLATE = registerCustomModelArmorPiece("uruk_hai_plate_chestplate",
            (settings) -> new CustomChestplateItem(ModArmorMaterials.BURZUM_STEEL_T5, settings, ModFactions.ISENGARD), new Item.Settings());
    public static final Item URUK_HAI_PLATE_LEGGINGS = registerArmorPiece("uruk_hai_plate_leggings",
            (settings) -> new CustomLeggingsItem(ModArmorMaterials.BURZUM_STEEL_T5, settings, ModFactions.ISENGARD), new Item.Settings());
    public static final Item URUK_HAI_PLATE_BOOTS = registerArmorPiece("uruk_hai_plate_boots",
            (settings) -> new CustomBootsItem(ModArmorMaterials.BURZUM_STEEL_T5, settings, ModFactions.ISENGARD), new Item.Settings());

    public static final Item ORTHANC_GUARD_HELMET = registerCustomModelArmorPiece("orthanc_guard_helmet",
            (settings) -> new CustomHelmetItem(ModArmorMaterials.STEEL_T5, settings, ModFactions.ISENGARD), new Item.Settings());
    public static final Item ORTHANC_COMMANDER_HELMET = registerCustomModelArmorPiece("orthanc_commander_helmet",
            (settings) -> new CustomHelmetItem(ModArmorMaterials.STEEL_T5, settings, ModFactions.ISENGARD), new Item.Settings());
    public static final Item ORTHANC_GUARD_CHESTPLATE = registerArmorPiece("orthanc_guard_chestplate",
            (settings) -> new CustomChestplateItem(ModArmorMaterials.STEEL_T5, settings, ModFactions.ISENGARD), new Item.Settings()
                    .component(ModDataComponentTypes.CAPE_DATA, CapeDataComponent.newCape(ModCapes.ORTHANC_GUARD_CAPE)));
    public static final Item ORTHANC_GUARD_LEGGINGS = registerArmorPiece("orthanc_guard_leggings",
            (settings) -> new CustomLeggingsItem(ModArmorMaterials.STEEL_T5, settings, ModFactions.ISENGARD), new Item.Settings());
    public static final Item ORTHANC_GUARD_BOOTS = registerArmorPiece("orthanc_guard_boots",
            (settings) -> new CustomBootsItem(ModArmorMaterials.STEEL_T5, settings, ModFactions.ISENGARD), new Item.Settings());

    public static final Item ORTHANC_GUARD_CAPE = registerCape("orthanc_guard_cape",
            (settings) -> new CapeChestplateItem(settings, ModArmorMaterials.FABRIC_T0, ModFactions.ISENGARD), new Item.Settings()
                    .component(ModDataComponentTypes.CAPE_DATA, CapeDataComponent.newCape(ModCapes.ORTHANC_GUARD_CAPE)));
    //endregion

    //region MISTY MOUNTAIN GOBLINS
    public static final Item GUNDABAD_BONE_PAULDRON = registerDyeableArmorPiece("gundabad_bone_pauldron",
            (settings) -> new CustomChestplateItem(ModArmorMaterials.LEATHER_T1, settings, ModSubFactions.GUNDABAD), new Item.Settings()
                    .component(ModDataComponentTypes.DYE_DATA, new CustomDyeableDataComponent(3944757)));

    public static final Item GUNDABAD_SEEKER_HELMET = registerDyeableCustomModelArmorPiece("gundabad_seeker_helmet",
            (settings) -> new CustomHelmetItem(ModArmorMaterials.CRUDE_T2, settings, ModSubFactions.GUNDABAD), new Item.Settings()
                    .component(ModDataComponentTypes.DYE_DATA, new CustomDyeableDataComponent(3944757)));

    public static final Item GUNDABAD_LEATHER_SCALE_COAT = registerDyeableArmorPiece("gundabad_leather_scale_coat",
            (settings) -> new CustomChestplateItem(ModArmorMaterials.LEATHER_T2, settings, ModSubFactions.GUNDABAD), new Item.Settings()
                    .component(ModDataComponentTypes.DYE_DATA, new CustomDyeableDataComponent(3944757)));

    public static final Item GUNDABAD_LEATHER_LEGGINGS = registerDyeableArmorPiece("gundabad_leather_leggings",
            (settings) -> new CustomLeggingsItem(ModArmorMaterials.LEATHER_T2, settings, ModSubFactions.GUNDABAD), new Item.Settings()
                    .component(ModDataComponentTypes.DYE_DATA, new CustomDyeableDataComponent(3944757)));

    public static final Item GUNDABAD_LACED_BOOTS = registerDyeableArmorPiece("gundabad_laced_boots",
            (settings) -> new CustomBootsItem(ModArmorMaterials.LEATHER_T2, settings, ModSubFactions.GUNDABAD), new Item.Settings()
                    .component(ModDataComponentTypes.DYE_DATA, new CustomDyeableDataComponent(3944757)));

    public static final Item GUNDABAD_SKULLCAP_HELMET = registerDyeableCustomModelArmorPiece("gundabad_skullcap_helmet",
            (settings) -> new CustomHelmetItem(ModArmorMaterials.CRUDE_T3, settings, ModSubFactions.GUNDABAD), new Item.Settings()
                    .component(ModDataComponentTypes.DYE_DATA, new CustomDyeableDataComponent(3944757)));
    public static final Item GUNDABAD_MAIL_COIF = registerArmorPiece("gundabad_mail_coif",
            (settings) -> new CustomHelmetItem(ModArmorMaterials.CRUDE_T3, settings, ModSubFactions.GUNDABAD), new Item.Settings());

    public static final Item GUNDABAD_MAIL_HAUBERK = registerArmorPiece("gundabad_mail_hauberk",
            (settings) -> new CustomChestplateItem(ModArmorMaterials.CRUDE_T3, settings, ModSubFactions.GUNDABAD), new Item.Settings());

    public static final Item GUNDABAD_MAIL_COAT = registerDyeableArmorPiece("gundabad_mail_coat",
            (settings) -> new CustomLeggingsItem(ModArmorMaterials.CRUDE_T3, settings, ModSubFactions.GUNDABAD), new Item.Settings()
                    .component(ModDataComponentTypes.DYE_DATA, new CustomDyeableDataComponent(3944757)));

    public static final Item GUNDABAD_SCREECHER_HELMET = registerDyeableCustomModelArmorPiece("gundabad_screecher_helmet",
            (settings) -> new CustomHelmetItem(ModArmorMaterials.BURZUM_STEEL_T4, settings, ModSubFactions.GUNDABAD), new Item.Settings()
                    .component(ModDataComponentTypes.DYE_DATA, new CustomDyeableDataComponent(3944757)));
    public static final Item GUNDABAD_SOLDIER_HELMET = registerDyeableCustomModelArmorPiece("gundabad_soldier_helmet",
            (settings) -> new CustomHelmetItem(ModArmorMaterials.BURZUM_STEEL_T4, settings, ModSubFactions.GUNDABAD), new Item.Settings()
                    .component(ModDataComponentTypes.DYE_DATA, new CustomDyeableDataComponent(3944757)));

    public static final Item GUNDABAD_REINFORCED_LEATHER_SCALE_COAT = registerDyeableArmorPiece("gundabad_reinforced_leather_scale_coat",
            (settings) -> new CustomChestplateItem(ModArmorMaterials.BURZUM_STEEL_T4, settings, ModSubFactions.GUNDABAD), new Item.Settings()
                    .component(ModDataComponentTypes.DYE_DATA, new CustomDyeableDataComponent(3944757)));
    public static final Item GUNDABAD_REINFORCED_LEATHER_VEST = registerDyeableArmorPiece("gundabad_reinforced_leather_vest",
            (settings) -> new CustomChestplateItem(ModArmorMaterials.BURZUM_STEEL_T4, settings, ModSubFactions.GUNDABAD), new Item.Settings()
                    .component(ModDataComponentTypes.DYE_DATA, new CustomDyeableDataComponent(3944757)));

    public static final Item GUNDABAD_CAPTAIN_HELMET = registerDyeableCustomModelArmorPiece("gundabad_captain_helmet",
            (settings) -> new CustomHelmetItem(ModArmorMaterials.BURZUM_STEEL_T5, settings, ModSubFactions.GUNDABAD), new Item.Settings()
                    .component(ModDataComponentTypes.DYE_DATA, new CustomDyeableDataComponent(3944757)));

    public static final Item GUNDABAD_HOBGOBLIN_PLATE_CRESTED_HELMET = registerCustomModelArmorPiece("gundabad_hobgoblin_plate_crested_helmet",
            (settings) -> new CustomHelmetItem(ModArmorMaterials.BURZUM_STEEL_T5, settings, ModSubFactions.GUNDABAD), new Item.Settings());
    public static final Item GUNDABAD_HOBGOBLIN_PLATE_LARGE_CREST_HELMET = registerCustomModelArmorPiece("gundabad_hobgoblin_plate_large_crest_helmet",
            (settings) -> new CustomHelmetItem(ModArmorMaterials.BURZUM_STEEL_T5, settings, ModSubFactions.GUNDABAD), new Item.Settings());
    public static final Item GUNDABAD_HOBGOBLIN_PLATE_LONG_HORN_HELMET = registerCustomModelArmorPiece("gundabad_hobgoblin_plate_long_horn_helmet",
            (settings) -> new CustomHelmetItem(ModArmorMaterials.BURZUM_STEEL_T5, settings, ModSubFactions.GUNDABAD), new Item.Settings());
    public static final Item GUNDABAD_HOBGOBLIN_PLATE_SMALL_HORN_HELMET = registerCustomModelArmorPiece("gundabad_hobgoblin_plate_small_horn_helmet",
            (settings) -> new CustomHelmetItem(ModArmorMaterials.BURZUM_STEEL_T5, settings, ModSubFactions.GUNDABAD), new Item.Settings());
    public static final Item GUNDABAD_HOBGOBLIN_PLATE_CHESTPLATE = registerCustomModelArmorPiece("gundabad_hobgoblin_plate_chestplate",
            (settings) -> new CustomChestplateItem(ModArmorMaterials.BURZUM_STEEL_T5, settings, ModSubFactions.GUNDABAD), new Item.Settings());
    public static final Item GUNDABAD_HOBGOBLIN_MAIL_COAT = registerArmorPiece("gundabad_hobgoblin_mail_coat",
            (settings) -> new CustomLeggingsItem(ModArmorMaterials.BURZUM_STEEL_T5, settings, ModSubFactions.GUNDABAD), new Item.Settings());
    public static final Item GUNDABAD_HOBGOBLIN_PLATED_BOOTS = registerArmorPiece("gundabad_hobgoblin_plated_boots",
            (settings) -> new CustomBootsItem(ModArmorMaterials.BURZUM_STEEL_T5, settings, ModSubFactions.GUNDABAD), new Item.Settings());

    public static final Item GUNDABAD_HOBGOBLIN_PLATE_TROPHY_CHESTPLATE = registerCustomModelArmorPiece("gundabad_hobgoblin_plate_trophy_chestplate",
            (settings) -> new CustomChestplateItem(ModArmorMaterials.BURZUM_STEEL_T5, settings, ModSubFactions.GUNDABAD), new Item.Settings());
    //endregion

    //endregion

    // GENERIC
    // Warg
    public static final Item WARG_MORDOR_PLATE_ARMOR = registerGeneratedItem("warg_mordor_plate_armor",
            (settings) -> new CustomAnimalArmorItem(ModArmorMaterials.PLATE, "_mordor", CustomAnimalArmorItem.Type.WARG, false, settings, ModFactions.MORDOR), new Item.Settings());
    public static final Item WARG_GUNDABAD_PLATE_ARMOR = registerGeneratedItem("warg_gundabad_plate_armor",
            (settings) -> new CustomAnimalArmorItem(ModArmorMaterials.PLATE, "_gundabad", CustomAnimalArmorItem.Type.WARG, false, settings, ModSubFactions.GUNDABAD), new Item.Settings());
    public static final Item WARG_ISENGARD_PLATE_ARMOR = registerGeneratedItem("warg_isengard_plate_armor",
            (settings) -> new CustomAnimalArmorItem(ModArmorMaterials.PLATE, "_isengard", CustomAnimalArmorItem.Type.WARG, false, settings, ModFactions.ISENGARD), new Item.Settings());
    public static final Item WARG_MORDOR_MAIL_ARMOR = registerDyeableArmorPiece("warg_mordor_mail_armor",
            (settings) -> new CustomAnimalArmorItem(ModArmorMaterials.MAIL, "_mordor", CustomAnimalArmorItem.Type.WARG, true, settings, ModFactions.MORDOR), new Item.Settings()
                    .component(ModDataComponentTypes.DYE_DATA, new CustomDyeableDataComponent(4865076)));
    public static final Item WARG_LEATHER_ARMOR = registerDyeableArmorPiece("warg_leather_armor",
            (settings) -> new CustomAnimalArmorItem(ModArmorMaterials.LEATHER, null, CustomAnimalArmorItem.Type.WARG, false, settings, ModFactions.MORDOR), new Item.Settings()
                    .component(ModDataComponentTypes.DYE_DATA, new CustomDyeableDataComponent(4865076)));
    public static final Item WARG_REINFORCED_LEATHER_ARMOR = registerDyeableArmorPiece("warg_reinforced_leather_armor",
            (settings) -> new CustomAnimalArmorItem(ModArmorMaterials.LEATHER, "_reinforced", CustomAnimalArmorItem.Type.WARG, true, settings, ModFactions.MORDOR), new Item.Settings()
                    .component(ModDataComponentTypes.DYE_DATA, new CustomDyeableDataComponent(4865076)));

    // Broadhoof Goat
    public static final Item BROADHOOF_GOAT_PADDED_ARMOR = registerDyeableArmorPiece("broadhoof_goat_padded_armor",
            (settings) -> new CustomAnimalArmorItem(ModArmorMaterials.LEATHER, null, CustomAnimalArmorItem.Type.BROADHOOF_GOAT, true, settings, ModSubFactions.EREBOR), new Item.Settings()
                    .component(ModDataComponentTypes.DYE_DATA, new CustomDyeableDataComponent(15256475)));
    public static final Item BROADHOOF_GOAT_ORNAMENTED_PADDED_ARMOR = registerDyeableArmorPiece("broadhoof_goat_ornamented_padded_armor",
            (settings) -> new CustomAnimalArmorItem(ModArmorMaterials.LEATHER, "_ornamented", CustomAnimalArmorItem.Type.BROADHOOF_GOAT, true, settings, ModSubFactions.EREBOR), new Item.Settings()
                    .component(ModDataComponentTypes.DYE_DATA, new CustomDyeableDataComponent(15256475)));
    public static final Item BROADHOOF_GOAT_PLATE_ARMOR = registerGeneratedItem("broadhoof_goat_plate_armor",
            (settings) -> new CustomAnimalArmorItem(ModArmorMaterials.PLATE, null, CustomAnimalArmorItem.Type.BROADHOOF_GOAT, false, settings, ModSubFactions.EREBOR), new Item.Settings());

    public static final Item HELMET_OF_HELM_HAMMERHAND = registerCustomModelArmorPiece("helmet_of_helm_hammerhand",
            (settings) -> new CustomArtefactHelmetItem(ModArmorMaterials.STEEL_T5, settings, ModFactions.ROHAN), new Item.Settings());

    private static Item registerHood(String name, Function<Item.Settings, Item> factory, Item.Settings settings) {
        Item item = (Item)factory.apply(settings.registryKey(ModBlocks.keyOfItem(name)));
        ModItemGroups.EQUIPMENT_CONTENTS.add(item.getDefaultStack());
        SimpleItemModel.items.add(item);
        hoods.add(item);
        return Registry.register(Registries.ITEM, ModBlocks.keyOfItem(name), item);
    }

    private static Item registerDyeableHood(String name, Function<Item.Settings, Item> factory, Item.Settings settings) {
        Item item = (Item)factory.apply(settings.registryKey(ModBlocks.keyOfItem(name)));
        ModItemGroups.EQUIPMENT_CONTENTS.add(item.getDefaultStack());
        SimpleDyeableItemModel.items.add(item);
        hoods.add(item);
        return Registry.register(Registries.ITEM, ModBlocks.keyOfItem(name), item);
    }

    private static Item registerCape(String name, Function<Item.Settings, Item> factory, Item.Settings settings) {
        Item item = (Item)factory.apply(settings.registryKey(ModBlocks.keyOfItem(name)));
        ModItemGroups.EQUIPMENT_CONTENTS.add(item.getDefaultStack());
        SimpleItemModel.items.add(item);
        capes.add(item);
        return Registry.register(Registries.ITEM, ModBlocks.keyOfItem(name), item);
    }

    private static Item registerDyeableCape(String name, Function<Item.Settings, Item> factory, Item.Settings settings) {
        Item item = (Item)factory.apply(settings.registryKey(ModBlocks.keyOfItem(name)));
        ModItemGroups.EQUIPMENT_CONTENTS.add(item.getDefaultStack());
        SimpleDyeableItemModel.items.add(item);
        capes.add(item);
        return Registry.register(Registries.ITEM, ModBlocks.keyOfItem(name), item);
    }

    private static Item registerGeneratedItem(String name, Function<Item.Settings, Item> factory, Item.Settings settings) {
        Item item = (Item)factory.apply(settings.registryKey(ModBlocks.keyOfItem(name)));
        ModItemGroups.EQUIPMENT_CONTENTS.add(item.getDefaultStack());
        SimpleItemModel.items.add(item);
        return Registry.register(Registries.ITEM, ModBlocks.keyOfItem(name), item);
    }

    private static Item registerDyeableArmorPiece(String name, Function<Item.Settings, Item> factory, Item.Settings settings) {
        Item item = (Item)factory.apply(settings.registryKey(ModBlocks.keyOfItem(name)));
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
        return Registry.register(Registries.ITEM, ModBlocks.keyOfItem(name), item);
    }

    private static Item registerArmorPiece(String name, Function<Item.Settings, Item> factory, Item.Settings settings) {
        Item item = (Item)factory.apply(settings.registryKey(ModBlocks.keyOfItem(name)));
        ModItemGroups.EQUIPMENT_CONTENTS.add(item.getDefaultStack());
        SimpleItemModel.items.add(item);
        switch (item){
            case CustomHelmetItem helmetItem -> armorPiecesListHelmets.add(helmetItem);
            case CustomChestplateItem chestplateItem -> armorPiecesListChestplates.add(chestplateItem);
            case CustomLeggingsItem leggingsItem -> armorPiecesListLeggings.add(leggingsItem);
            case CustomBootsItem bootsItem -> armorPiecesListBoots.add(bootsItem);
            default -> throw new IllegalStateException("Unexpected value: " + item);
        }
        return Registry.register(Registries.ITEM, ModBlocks.keyOfItem(name), item);
    }

    private static Item registerCustomModelArmorPiece(String name, Function<Item.Settings, Item> factory, Item.Settings settings) {
        Item item = (Item)factory.apply(settings.registryKey(ModBlocks.keyOfItem(name)));
        ModItemGroups.EQUIPMENT_CONTENTS.add(item.getDefaultStack());
        SimpleItemModel.items.add(item);
        return Registry.register(Registries.ITEM, ModBlocks.keyOfItem(name), item);
    }

    private static Item registerDyeableCustomModelArmorPiece(String name, Function<Item.Settings, Item> factory, Item.Settings settings) {
        Item item = (Item)factory.apply(settings.registryKey(ModBlocks.keyOfItem(name)));
        ModItemGroups.EQUIPMENT_CONTENTS.add(item.getDefaultStack());
        SimpleDyeableItemModel.items.add(item);
        return Registry.register(Registries.ITEM, ModBlocks.keyOfItem(name), item);
    }

    public static void registerModItems() {
        MiddleEarth.LOGGER.logDebugMsg("Registering Mod Equipment Items for " + MiddleEarth.MOD_ID);
    }
}
