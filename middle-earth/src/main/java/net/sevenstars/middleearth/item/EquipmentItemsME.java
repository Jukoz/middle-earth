package net.sevenstars.middleearth.item;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.DyedColorComponent;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Rarity;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.block.registration.ModBlocks;
import net.sevenstars.middleearth.datageneration.content.TranslationEntries;
import net.sevenstars.middleearth.datageneration.content.models.SimpleDyeableItemModel;
import net.sevenstars.middleearth.datageneration.content.models.SimpleItemModel;
import net.sevenstars.middleearth.datageneration.content.tags.ArmorTags;
import net.sevenstars.middleearth.item.dataComponents.BackAttachmentDataComponent;
import net.sevenstars.middleearth.item.dataComponents.FactionDataComponent;
import net.sevenstars.middleearth.item.dataComponents.HelmetAttachmentDataComponent;
import net.sevenstars.middleearth.item.items.armor.*;
import net.sevenstars.middleearth.item.items.armor.artefact.CustomArtefactHelmetItem;
import net.sevenstars.middleearth.item.items.weapons.utils.ArtefactUtils;
import net.sevenstars.middleearth.item.utils.ItemGroupsME;
import net.sevenstars.middleearth.item.utils.ItemSettingsME;
import net.sevenstars.middleearth.item.utils.armor.ArmorMaterialsME;
import net.sevenstars.middleearth.item.utils.armor.backAttachments.BackAttachmentsME;
import net.sevenstars.middleearth.item.utils.armor.helmetAttachments.HelmetAttachmentsME;
import net.sevenstars.middleearth.registries.RegistryAliases;
import net.sevenstars.middleearth.resources.FactionsME;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class EquipmentItemsME {

    /**
     * Middle-earth mod Equipment Items, armors, capes, hoods registry
     */

    public static List<Item> armorPiecesListHelmets = new ArrayList<>();
    public static List<Item> armorPiecesListChestplates = new ArrayList<>();
    public static List<Item> armorPiecesListLeggings = new ArrayList<>();
    public static List<Item> armorPiecesListBoots = new ArrayList<>();

    public static List<Item> backAttachments = new ArrayList<>();
    public static List<Item> helmetAtttachments = new ArrayList<>();

    //region GENERIC
    //Hoods
    public static final Item HOOD = registerDyeableHelmetAttachment("hood",
            (settings) -> new HelmetAttachmentItem(settings, ArmorMaterialsME.FABRIC_T0), new Item.Settings()
                    .component(DataComponentTypesME.HELMET_ATTACHMENT_DATA, HelmetAttachmentDataComponent.newHelmetAttachment(HelmetAttachmentsME.HOOD))
                    .component(DataComponentTypes.DYED_COLOR, new DyedColorComponent(5394247)));
    public static final Item TALL_HOOD = registerDyeableHelmetAttachment("tall_hood",
            (settings) -> new HelmetAttachmentItem(settings, ArmorMaterialsME.FABRIC_T0), new Item.Settings()
                    .component(DataComponentTypesME.HELMET_ATTACHMENT_DATA, HelmetAttachmentDataComponent.newHelmetAttachment(HelmetAttachmentsME.TALL_HOOD))
                    .component(DataComponentTypes.DYED_COLOR, new DyedColorComponent(5394247)));

    public static final Item BLACK_FUR_HOOD = registerDyeableHelmetAttachment("black_fur_hood",
            (settings) -> new HelmetAttachmentItem(settings, ArmorMaterialsME.FUR_T0), new Item.Settings()
                    .component(DataComponentTypesME.HELMET_ATTACHMENT_DATA, HelmetAttachmentDataComponent.newHelmetAttachment(HelmetAttachmentsME.BLACK_FUR_HOOD))
                    .component(DataComponentTypes.DYED_COLOR, new DyedColorComponent(5394247)));
    public static final Item BROWN_FUR_HOOD = registerDyeableHelmetAttachment("brown_fur_hood",
            (settings) -> new HelmetAttachmentItem(settings, ArmorMaterialsME.FUR_T0), new Item.Settings()
                    .component(DataComponentTypesME.HELMET_ATTACHMENT_DATA, HelmetAttachmentDataComponent.newHelmetAttachment(HelmetAttachmentsME.BROWN_FUR_HOOD))
                    .component(DataComponentTypes.DYED_COLOR, new DyedColorComponent(5394247)));
    public static final Item GRAY_FUR_HOOD = registerDyeableHelmetAttachment("gray_fur_hood",
            (settings) -> new HelmetAttachmentItem(settings, ArmorMaterialsME.FUR_T0), new Item.Settings()
                    .component(DataComponentTypesME.HELMET_ATTACHMENT_DATA, HelmetAttachmentDataComponent.newHelmetAttachment(HelmetAttachmentsME.GRAY_FUR_HOOD))
                    .component(DataComponentTypes.DYED_COLOR, new DyedColorComponent(5394247)));
    public static final Item TAN_FUR_HOOD = registerDyeableHelmetAttachment("tan_fur_hood",
            (settings) -> new HelmetAttachmentItem(settings, ArmorMaterialsME.FUR_T0), new Item.Settings()
                    .component(DataComponentTypesME.HELMET_ATTACHMENT_DATA, HelmetAttachmentDataComponent.newHelmetAttachment(HelmetAttachmentsME.TAN_FUR_HOOD))
                    .component(DataComponentTypes.DYED_COLOR, new DyedColorComponent(5394247)));
    public static final Item WHITE_FUR_HOOD = registerDyeableHelmetAttachment("white_fur_hood",
            (settings) -> new HelmetAttachmentItem(settings, ArmorMaterialsME.FUR_T0), new Item.Settings()
                    .component(DataComponentTypesME.HELMET_ATTACHMENT_DATA, HelmetAttachmentDataComponent.newHelmetAttachment(HelmetAttachmentsME.WHITE_FUR_HOOD))
                    .component(DataComponentTypes.DYED_COLOR, new DyedColorComponent(5394247)));

    //capes
    public static final Item CAPE = registerDyeableBackAttachment("cape",
            (settings) -> new BackAttachmentItem(settings, ArmorMaterialsME.FABRIC_T0), new Item.Settings()
                    .component(DataComponentTypesME.BACK_ATTACHMENT_DATA, BackAttachmentDataComponent.newBackAttachment(BackAttachmentsME.CAPE))
                    .component(DataComponentTypes.DYED_COLOR, new DyedColorComponent(5394247)));
    public static final Item SHOULDER_CAPE_LEFT = registerDyeableBackAttachment("shoulder_cape_left",
            (settings) -> new BackAttachmentItem(settings, ArmorMaterialsME.FABRIC_T0), new Item.Settings()
                    .component(DataComponentTypesME.BACK_ATTACHMENT_DATA, BackAttachmentDataComponent.newBackAttachment(BackAttachmentsME.SHOULDER_CAPE_LEFT))
                    .component(DataComponentTypes.DYED_COLOR, new DyedColorComponent(5394247)));
    public static final Item SHOULDER_CAPE_RIGHT = registerDyeableBackAttachment("shoulder_cape_right",
            (settings) -> new BackAttachmentItem(settings, ArmorMaterialsME.FABRIC_T0), new Item.Settings()
                    .component(DataComponentTypesME.BACK_ATTACHMENT_DATA, BackAttachmentDataComponent.newBackAttachment(BackAttachmentsME.SHOULDER_CAPE_RIGHT))
                    .component(DataComponentTypes.DYED_COLOR, new DyedColorComponent(5394247)));
    public static final Item SURCOAT = registerDyeableBackAttachment("surcoat",
            (settings) -> new BackAttachmentItem(settings, ArmorMaterialsME.FABRIC_T0), new Item.Settings()
                    .component(DataComponentTypesME.BACK_ATTACHMENT_DATA, BackAttachmentDataComponent.newBackAttachment(BackAttachmentsME.SURCOAT))
                    .component(DataComponentTypes.DYED_COLOR, new DyedColorComponent(5394247)));
    public static final Item CLOAK = registerDyeableBackAttachment("cloak",
            (settings) -> new BackAttachmentItem(settings, ArmorMaterialsME.FABRIC_T0), new Item.Settings()
                    .component(DataComponentTypesME.BACK_ATTACHMENT_DATA, BackAttachmentDataComponent.newBackAttachment(BackAttachmentsME.CLOAK))
                    .component(DataComponentTypes.DYED_COLOR, new DyedColorComponent(5394247)));
    public static final Item WANDERER_ROBES = registerDyeableBackAttachment("wanderer_robes",
            (settings) -> new BackAttachmentItem(settings, ArmorMaterialsME.FABRIC_T0), new Item.Settings()
                    .component(DataComponentTypesME.BACK_ATTACHMENT_DATA, BackAttachmentDataComponent.newBackAttachment(BackAttachmentsME.WANDERER_ROBES))
                    .component(DataComponentTypes.DYED_COLOR, new DyedColorComponent(5394247)));
    public static final Item LEAF_CAPE = registerBackAttachment("leaf_cape",
            (settings) -> new BackAttachmentItem(settings, ArmorMaterialsME.FABRIC_T0), new Item.Settings()
                    .component(DataComponentTypesME.BACK_ATTACHMENT_DATA, BackAttachmentDataComponent.newBackAttachment(BackAttachmentsME.LEAF_CAPE))
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.LOTHLORIEN.getValue())));
    public static final Item AUTUMN_LEAF_CAPE = registerBackAttachment("autumn_leaf_cape",
            (settings) -> new BackAttachmentItem(settings, ArmorMaterialsME.FABRIC_T0), new Item.Settings()
                    .component(DataComponentTypesME.BACK_ATTACHMENT_DATA, BackAttachmentDataComponent.newBackAttachment(BackAttachmentsME.AUTUMN_LEAF_CAPE))
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.LOTHLORIEN.getValue())));


    public static final Item BLACK_FUR_CLOAK = registerDyeableBackAttachment("black_fur_cloak",
            (settings) -> new BackAttachmentItem(settings, ArmorMaterialsME.FUR_T0), new Item.Settings()
                    .component(DataComponentTypesME.BACK_ATTACHMENT_DATA, BackAttachmentDataComponent.newBackAttachment(BackAttachmentsME.BLACK_FUR_CLOAK))
                    .component(DataComponentTypes.DYED_COLOR, new DyedColorComponent(5394247)));
    public static final Item BROWN_FUR_CLOAK = registerDyeableBackAttachment("brown_fur_cloak",
            (settings) -> new BackAttachmentItem(settings, ArmorMaterialsME.FUR_T0), new Item.Settings()
                    .component(DataComponentTypesME.BACK_ATTACHMENT_DATA, BackAttachmentDataComponent.newBackAttachment(BackAttachmentsME.BROWN_FUR_CLOAK))
                    .component(DataComponentTypes.DYED_COLOR, new DyedColorComponent(5394247)));
    public static final Item GRAY_FUR_CLOAK = registerDyeableBackAttachment("gray_fur_cloak",
            (settings) -> new BackAttachmentItem(settings, ArmorMaterialsME.FUR_T0), new Item.Settings()
                    .component(DataComponentTypesME.BACK_ATTACHMENT_DATA, BackAttachmentDataComponent.newBackAttachment(BackAttachmentsME.GRAY_FUR_CLOAK))
                    .component(DataComponentTypes.DYED_COLOR, new DyedColorComponent(5394247)));
    public static final Item TAN_FUR_CLOAK = registerDyeableBackAttachment("tan_fur_cloak",
            (settings) -> new BackAttachmentItem(settings, ArmorMaterialsME.FUR_T0), new Item.Settings()
                    .component(DataComponentTypesME.BACK_ATTACHMENT_DATA, BackAttachmentDataComponent.newBackAttachment(BackAttachmentsME.TAN_FUR_CLOAK))
                    .component(DataComponentTypes.DYED_COLOR, new DyedColorComponent(5394247)));
    public static final Item WHITE_FUR_CLOAK = registerDyeableBackAttachment("white_fur_cloak",
            (settings) -> new BackAttachmentItem(settings, ArmorMaterialsME.FUR_T0), new Item.Settings()
                    .component(DataComponentTypesME.BACK_ATTACHMENT_DATA, BackAttachmentDataComponent.newBackAttachment(BackAttachmentsME.WHITE_FUR_CLOAK))
                    .component(DataComponentTypes.DYED_COLOR, new DyedColorComponent(5394247)));

    public static final Item BLACK_FUR = registerBackAttachment("black_fur",
            (settings) -> new BackAttachmentItem(settings, ArmorMaterialsME.FUR_T0), new Item.Settings()
                    .component(DataComponentTypesME.BACK_ATTACHMENT_DATA, BackAttachmentDataComponent.newBackAttachment(BackAttachmentsME.BLACK_FUR)));
    public static final Item BROWN_FUR = registerBackAttachment("brown_fur",
            (settings) -> new BackAttachmentItem(settings, ArmorMaterialsME.FUR_T0), new Item.Settings()
                    .component(DataComponentTypesME.BACK_ATTACHMENT_DATA, BackAttachmentDataComponent.newBackAttachment(BackAttachmentsME.BROWN_FUR)));
    public static final Item GRAY_FUR = registerBackAttachment("gray_fur",
            (settings) -> new BackAttachmentItem(settings, ArmorMaterialsME.FUR_T0), new Item.Settings()
                    .component(DataComponentTypesME.BACK_ATTACHMENT_DATA, BackAttachmentDataComponent.newBackAttachment(BackAttachmentsME.GRAY_FUR)));
    public static final Item TAN_FUR = registerBackAttachment("tan_fur",
            (settings) -> new BackAttachmentItem(settings, ArmorMaterialsME.FUR_T0), new Item.Settings()
                    .component(DataComponentTypesME.BACK_ATTACHMENT_DATA, BackAttachmentDataComponent.newBackAttachment(BackAttachmentsME.TAN_FUR)));
    public static final Item WHITE_FUR = registerBackAttachment("white_fur",
            (settings) -> new BackAttachmentItem(settings, ArmorMaterialsME.FUR_T0), new Item.Settings()
                    .component(DataComponentTypesME.BACK_ATTACHMENT_DATA, BackAttachmentDataComponent.newBackAttachment(BackAttachmentsME.WHITE_FUR)));

    public static final Item STRAW_HAT = registerCustomModelArmorPiece("straw_hat",
            (settings) -> new CustomHelmetItem(ArmorMaterialsME.STRAW_T1, settings), new Item.Settings());

    public static final Item WOVEN_HAT = registerCustomModelArmorPiece("woven_hat",
            (settings) -> new CustomHelmetItem(ArmorMaterialsME.WOOL_T1, settings), new Item.Settings());

    public static final Item BYCOCKET = registerDyeableArmorPiece("bycocket",
            (settings) -> new CustomHelmetItem(ArmorMaterialsME.WOOL_T1, settings), new Item.Settings()
                    .component(DataComponentTypes.DYED_COLOR, new DyedColorComponent(-6265536)));

    public static final Item WANDERER_HAT = registerDyeableCustomModelArmorPiece("wanderer_hat",
            (settings) -> new CustomHelmetItem(ArmorMaterialsME.FABRIC_T1, settings), new Item.Settings()
                    .component(DataComponentTypes.DYED_COLOR, new DyedColorComponent(5394247)));

    public static final Item ARMING_COAT = registerDyeableArmorPiece("arming_coat",
            (settings) -> new CustomChestplateItem(ArmorMaterialsME.FABRIC_T1, settings), new Item.Settings()
                    .component(DataComponentTypes.DYED_COLOR, new DyedColorComponent(15256475)));

    public static final Item ARMING_SKIRT = registerDyeableArmorPiece("arming_skirt",
            (settings) -> new CustomLeggingsItem(ArmorMaterialsME.FABRIC_T1, settings), new Item.Settings()
                    .component(DataComponentTypes.DYED_COLOR, new DyedColorComponent(15256475)));

    public static final Item SHOES = registerArmorPiece("shoes",
            (settings) -> new CustomBootsItem(ArmorMaterialsME.FABRIC_T1, settings), new Item.Settings());

    public static final Item WORK_SHOES = registerArmorPiece("work_shoes",
            (settings) -> new CustomBootsItem(ArmorMaterialsME.FABRIC_T1, settings), new Item.Settings());

    public static final Item LEATHER_SKULLCAP = registerDyeableArmorPiece("leather_skullcap",
            (settings) -> new CustomHelmetItem(ArmorMaterialsME.LEATHER_T2, settings), new Item.Settings()
                    .component(DataComponentTypes.DYED_COLOR, new DyedColorComponent(-6265536)));

    public static final Item GAMBESON_CAP = registerDyeableArmorPiece("gambeson_cap",
            (settings) -> new CustomHelmetItem(ArmorMaterialsME.LEATHER_T2, settings), new Item.Settings()
                    .component(DataComponentTypes.DYED_COLOR, new DyedColorComponent(15256475)));
    public static final Item GAMBESON_COWL = registerDyeableArmorPiece("gambeson_cowl",
            (settings) -> new CustomHelmetItem(ArmorMaterialsME.LEATHER_T2, settings), new Item.Settings()
                    .component(DataComponentTypes.DYED_COLOR, new DyedColorComponent( 15256475)));
    public static final Item KETTLE_HAT = registerCustomModelArmorPiece("kettle_hat",
            (settings) -> new CustomHelmetItem(ArmorMaterialsME.IRON_T2, settings), new Item.Settings());

    public static final Item GAMBESON = registerDyeableArmorPiece("gambeson",
            (settings) -> new CustomChestplateItem(ArmorMaterialsME.LEATHER_T2, settings), new Item.Settings()
                    .component(DataComponentTypes.DYED_COLOR, new DyedColorComponent(15256475)));

    public static final Item LEATHER_VEST = registerDyeableArmorPiece("leather_vest",
            (settings) -> new CustomChestplateItem(ArmorMaterialsME.LEATHER_T2, settings), new Item.Settings()
                    .component(DataComponentTypes.DYED_COLOR, new DyedColorComponent(-6265536)));

    public static final Item LEATHER_SCALE_VEST = registerDyeableArmorPiece("leather_scale_vest",
            (settings) -> new CustomChestplateItem(ArmorMaterialsME.LEATHER_T2, settings), new Item.Settings()
                    .component(DataComponentTypes.DYED_COLOR, new DyedColorComponent(-6265536)));

    public static final Item STURDY_BOOTS = registerArmorPiece("sturdy_boots",
            (settings) -> new CustomBootsItem(ArmorMaterialsME.LEATHER_T2, settings), new Item.Settings());
    public static final Item TRAVELLING_BOOTS = registerArmorPiece("travelling_boots",
            (settings) -> new CustomBootsItem(ArmorMaterialsME.LEATHER_T2, settings), new Item.Settings());
    public static final Item HIGH_CUT_BOOTS = registerArmorPiece("high_cut_boots",
            (settings) -> new CustomBootsItem(ArmorMaterialsME.LEATHER_T2, settings), new Item.Settings());

    public static final Item COOKING_POT_HELMET = registerCustomModelArmorPiece("cooking_pot_helmet",
            (settings) -> new CustomHelmetItem(ArmorMaterialsME.IRON_T3, settings), new Item.Settings());
    public static final Item MAIL_COIF = registerArmorPiece("mail_coif",
            (settings) -> new CustomHelmetItem(ArmorMaterialsME.IRON_T3, settings), new Item.Settings());
    public static final Item CLOSED_MAIL_COIF = registerArmorPiece("closed_mail_coif",
            (settings) -> new CustomHelmetItem(ArmorMaterialsME.IRON_T3, settings), new Item.Settings());

    public static final Item KETTLE_HAT_WITH_COIF = registerCustomModelArmorPiece("kettle_hat_with_coif",
            (settings) -> new CustomHelmetItem(ArmorMaterialsME.IRON_T3, settings), new Item.Settings());
    public static final Item KETTLE_HAT_WITH_CLOSED_COIF = registerCustomModelArmorPiece("kettle_hat_with_closed_coif",
            (settings) -> new CustomHelmetItem(ArmorMaterialsME.IRON_T3, settings), new Item.Settings());

    public static final Item OPEN_FACE_HELMET = registerArmorPiece("open_face_helmet",
            (settings) -> new CustomHelmetItem(ArmorMaterialsME.IRON_T3, settings), new Item.Settings());

    public static final Item WINGED_HELMET = registerCustomModelArmorPiece("winged_helmet",
            (settings) -> new CustomHelmetItem(ArmorMaterialsME.IRON_T3, settings), new Item.Settings());

    public static final Item MAIL_HAUBERK = registerArmorPiece("mail_hauberk",
            (settings) -> new CustomChestplateItem(ArmorMaterialsME.IRON_T3, settings), new Item.Settings());
    public static final Item MAIL_SHIRT = registerArmorPiece("mail_shirt",
            (settings) -> new CustomChestplateItem(ArmorMaterialsME.IRON_T3, settings), new Item.Settings());

    public static final Item MAIL_SKIRT = registerArmorPiece("mail_skirt",
            (settings) -> new CustomLeggingsItem(ArmorMaterialsME.IRON_T3, settings), new Item.Settings());

    public static final Item SALLET = registerCustomModelArmorPiece("sallet",
            (settings) -> new CustomHelmetItem(ArmorMaterialsME.STEEL_T4, settings), new Item.Settings());
    //endregion

    //region HOBBITS
    public static final Item SHIRRIFF_HAT = registerDyeableCustomModelArmorPiece("shirriff_hat",
            (settings) -> new CustomHelmetItem(ArmorMaterialsME.LEATHER_T1, settings), new Item.Settings()
                    .component(DataComponentTypes.DYED_COLOR, new DyedColorComponent(3440180)));
    //endregion

    //region MEN
    //region GONDOR
    public static final Item GONDORIAN_BOOTS = registerArmorPiece("gondorian_boots",
            (settings) -> new CustomBootsItem(ArmorMaterialsME.LEATHER_T2, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.GONDOR.getValue())));

    public static final Item GONDORIAN_CABASSET_HELMET = registerCustomModelArmorPiece("gondorian_cabasset_helmet",
            (settings) -> new CustomHelmetItem(ArmorMaterialsME.IRON_T3, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.GONDOR.getValue())));

    public static final Item GONDORIAN_MAIL_HAUBERK = registerArmorPiece("gondorian_mail_hauberk",
            (settings) -> new CustomChestplateItem(ArmorMaterialsME.IRON_T3, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.GONDOR.getValue())));
    public static final Item GONDORIAN_TABBARD = registerArmorPiece("gondorian_tabbard",
            (settings) -> new CustomChestplateItem(ArmorMaterialsME.IRON_T3, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.GONDOR.getValue())));
    public static final Item GONDORIAN_LEATHER_CUIRASS = registerDyeableArmorPiece("gondorian_leather_cuirass",
            (settings) -> new CustomChestplateItem(ArmorMaterialsME.IRON_T3, settings), new Item.Settings()
                    .component(DataComponentTypes.DYED_COLOR, new DyedColorComponent(-6265536))
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.GONDOR.getValue())));
    public static final Item GONDORIAN_LEATHER_CHESTPLATE = registerDyeableArmorPiece("gondorian_leather_chestplate",
            (settings) -> new CustomChestplateItem(ArmorMaterialsME.IRON_T3, settings), new Item.Settings()
                    .component(DataComponentTypes.DYED_COLOR, new DyedColorComponent(-6265536))
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.GONDOR.getValue())));

    public static final Item GONDORIAN_MAIL_COAT = registerArmorPiece("gondorian_mail_coat",
            (settings) -> new CustomLeggingsItem(ArmorMaterialsME.IRON_T3, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.GONDOR.getValue())));

    public static final Item GONDORIAN_SOLDIER_HELMET = registerCustomModelArmorPiece("gondorian_soldier_helmet",
            (settings) -> new CustomHelmetItem(ArmorMaterialsME.STEEL_T4, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.GONDOR.getValue())));

    public static final Item GONDORIAN_SOLDIER_CHESTPLATE = registerArmorPiece("gondorian_soldier_chestplate",
            (settings) -> new CustomChestplateItem(ArmorMaterialsME.STEEL_T4, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.GONDOR.getValue())));

    public static final Item GONDORIAN_PLATE_HELMET = registerCustomModelArmorPiece("gondorian_plate_helmet",
            (settings) -> new CustomHelmetItem(ArmorMaterialsME.STEEL_T5, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.GONDOR.getValue())));
    public static final Item GONDORIAN_PLATE_CHESTPLATE = registerCustomModelArmorPiece("gondorian_plate_chestplate",
            (settings) -> new CustomChestplateItem(ArmorMaterialsME.STEEL_T5, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.GONDOR.getValue())));
    public static final Item GONDORIAN_PLATE_LEGGINGS = registerArmorPiece("gondorian_plate_leggings",
            (settings) -> new CustomLeggingsItem(ArmorMaterialsME.STEEL_T5, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.GONDOR.getValue())));
    public static final Item GONDORIAN_PLATE_BOOTS = registerArmorPiece("gondorian_plate_boots",
            (settings) -> new CustomBootsItem(ArmorMaterialsME.STEEL_T5, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.GONDOR.getValue())));

    public static final Item GONDORIAN_CAPTAIN_HELMET = registerCustomModelArmorPiece("gondorian_captain_helmet",
            (settings) -> new CustomHelmetItem(ArmorMaterialsME.STEEL_T5, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.GONDOR.getValue())));

    public static final Item GONDORIAN_KINGS_GUARD_HELMET = registerCustomModelArmorPiece("gondorian_kings_guard_helmet",
            (settings) -> new CustomHelmetItem(ArmorMaterialsME.STEEL_T5, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.GONDOR.getValue())));
    public static final Item GONDORIAN_KINGS_GUARD_CHESTKPLATE = registerCustomModelArmorPiece("gondorian_kings_guard_chestplate",
            (settings) -> new CustomChestplateItem(ArmorMaterialsME.STEEL_T5, settings), new Item.Settings()
                    .component(DataComponentTypesME.BACK_ATTACHMENT_DATA, BackAttachmentDataComponent.newBackAttachment(BackAttachmentsME.GONDORIAN_KINGS_GUARD_CAPE))
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.GONDOR.getValue())));
    public static final Item GONDORIAN_KINGS_GUARD_LEGGINGS = registerArmorPiece("gondorian_kings_guard_leggings",
            (settings) -> new CustomLeggingsItem(ArmorMaterialsME.STEEL_T5, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.GONDOR.getValue())));
    public static final Item GONDORIAN_KINGS_GUARD_BOOTS = registerArmorPiece("gondorian_kings_guard_boots",
            (settings) -> new CustomBootsItem(ArmorMaterialsME.STEEL_T5, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.GONDOR.getValue())));

    public static final Item GONDORIAN_CITADEL_GUARD_HELMET = registerCustomModelArmorPiece("gondorian_citadel_guard_helmet",
            (settings) -> new CustomHelmetItem(ArmorMaterialsME.STEEL_T5, settings), new Item.Settings()
                    .component(DataComponentTypesME.HELMET_ATTACHMENT_DATA, HelmetAttachmentDataComponent.newHelmetAttachmentwithState(true, HelmetAttachmentsME.GONDORIAN_CITADEL_GUARD_HOOD))
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.GONDOR.getValue())));
    public static final Item GONDORIAN_CITADEL_GUARD_CHESTPLATE = registerArmorPiece("gondorian_citadel_guard_chestplate",
            (settings) -> new CustomChestplateItem(ArmorMaterialsME.STEEL_T5, settings), new Item.Settings()
                    .component(DataComponentTypesME.BACK_ATTACHMENT_DATA, BackAttachmentDataComponent.newBackAttachment(BackAttachmentsME.GONDORIAN_CITADEL_GUARD_CAPE))
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.GONDOR.getValue())));
    public static final Item GONDORIAN_CITADEL_GUARD_LEGGINGS = registerArmorPiece("gondorian_citadel_guard_leggings",
            (settings) -> new CustomLeggingsItem(ArmorMaterialsME.STEEL_T5, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.GONDOR.getValue())));
    public static final Item GONDORIAN_CITADEL_GUARD_BOOTS = registerArmorPiece("gondorian_citadel_guard_boots",
            (settings) -> new CustomBootsItem(ArmorMaterialsME.STEEL_T5, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.GONDOR.getValue())));

    public static final Item GONDORIAN_FOUNTAIN_GUARD_HELMET = registerCustomModelArmorPiece("gondorian_fountain_guard_helmet",
            (settings) -> new CustomHelmetItem(ArmorMaterialsME.STEEL_T5, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.GONDOR.getValue())));
    public static final Item GONDORIAN_FOUNTAIN_GUARD_CHESTPLATE = registerCustomModelArmorPiece("gondorian_fountain_guard_chestplate",
            (settings) -> new CustomChestplateItem(ArmorMaterialsME.STEEL_T5, settings), new Item.Settings()
                    .component(DataComponentTypesME.BACK_ATTACHMENT_DATA, BackAttachmentDataComponent.newBackAttachment(BackAttachmentsME.GONDORIAN_FOUNTAIN_GUARD_CAPE))
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.GONDOR.getValue())));
    public static final Item GONDORIAN_FOUNTAIN_GUARD_LEGGINGS = registerArmorPiece("gondorian_fountain_guard_leggings",
            (settings) -> new CustomLeggingsItem(ArmorMaterialsME.STEEL_T5, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.GONDOR.getValue())));
    public static final Item GONDORIAN_FOUNTAIN_GUARD_BOOTS = registerArmorPiece("gondorian_fountain_guard_boots",
            (settings) -> new CustomBootsItem(ArmorMaterialsME.STEEL_T5, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.GONDOR.getValue())));

    public static final Item GONDORIAN_CITADEL_GUARD_HOOD = registerHelmetAttachment("gondorian_citadel_guard_hood",
            (settings) -> new HelmetAttachmentItem(settings, ArmorMaterialsME.FABRIC_T0), new Item.Settings()
                    .component(DataComponentTypesME.HELMET_ATTACHMENT_DATA, HelmetAttachmentDataComponent.newHelmetAttachmentwithState(true, HelmetAttachmentsME.GONDORIAN_CITADEL_GUARD_HOOD))
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.GONDOR.getValue())));

    public static final Item GONDORIAN_CAPTAIN_CAPE = registerBackAttachment("gondorian_captain_cape",
            (settings) -> new BackAttachmentItem(settings, ArmorMaterialsME.FABRIC_T0), new Item.Settings()
                    .component(DataComponentTypesME.BACK_ATTACHMENT_DATA, BackAttachmentDataComponent.newBackAttachment(BackAttachmentsME.GONDORIAN_CAPTAIN_CAPE))
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.GONDOR.getValue())));
    public static final Item GONDORIAN_HERO_CAPE = registerBackAttachment("gondorian_hero_cape",
            (settings) -> new BackAttachmentItem(settings, ArmorMaterialsME.FABRIC_T0), new Item.Settings()
                    .component(DataComponentTypesME.BACK_ATTACHMENT_DATA, BackAttachmentDataComponent.newBackAttachment(BackAttachmentsME.GONDORIAN_HERO_CAPE))
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.GONDOR.getValue())));
    public static final Item GONDORIAN_KINGS_GUARD_CAPE = registerBackAttachment("gondorian_kings_guard_cape",
            (settings) -> new BackAttachmentItem(settings, ArmorMaterialsME.FABRIC_T0), new Item.Settings()
                    .component(DataComponentTypesME.BACK_ATTACHMENT_DATA, BackAttachmentDataComponent.newBackAttachment(BackAttachmentsME.GONDORIAN_KINGS_GUARD_CAPE))
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.GONDOR.getValue())));
    public static final Item GONDORIAN_CITADEL_GUARD_CAPE = registerBackAttachment("gondorian_citadel_guard_cape",
            (settings) -> new BackAttachmentItem(settings, ArmorMaterialsME.FABRIC_T0), new Item.Settings()
                    .component(DataComponentTypesME.BACK_ATTACHMENT_DATA, BackAttachmentDataComponent.newBackAttachment(BackAttachmentsME.GONDORIAN_CITADEL_GUARD_CAPE))
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.GONDOR.getValue())));
    public static final Item GONDORIAN_FOUNTAIN_GUARD_CAPE = registerBackAttachment("gondorian_fountain_guard_cape",
            (settings) -> new BackAttachmentItem(settings, ArmorMaterialsME.FABRIC_T0), new Item.Settings()
                    .component(DataComponentTypesME.BACK_ATTACHMENT_DATA, BackAttachmentDataComponent.newBackAttachment(BackAttachmentsME.GONDORIAN_FOUNTAIN_GUARD_CAPE))
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.GONDOR.getValue())));

    public static final Item GONDORIAN_HORSE_ARMOR = registerGeneratedItem("gondorian_horse_armor",
            Item::new, new Item.Settings().horseArmor(ArmorMaterialsME.GONDORIAN_HORSE_ARMOR.material()).maxCount(1)
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.GONDOR.getValue())));
    //endregion

    //region ROHAN
    public static final Item ROHIRRIC_REINFORCED_COAT = registerDyeableArmorPiece("rohirric_reinforced_coat",
            (settings) -> new CustomChestplateItem(ArmorMaterialsME.FABRIC_T1, settings), new Item.Settings()
                    .component(DataComponentTypes.DYED_COLOR, new DyedColorComponent( 15256475))
                    .component(DataComponentTypesME.BACK_ATTACHMENT_DATA, BackAttachmentDataComponent.newBackAttachment(BackAttachmentsME.ROHIRRIC_CAPE))
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.ROHAN.getValue())));
    
    public static final Item ROHIRRIC_LEATHER_HELMET = registerArmorPiece("rohirric_leather_helmet",
            (settings) -> new CustomHelmetItem(ArmorMaterialsME.LEATHER_T2, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.ROHAN.getValue())));
    public static final Item ROHIRRIC_REINFORCED_LEATHER_HELMET = registerCustomModelArmorPiece("rohirric_reinforced_leather_helmet",
            (settings) -> new CustomHelmetItem(ArmorMaterialsME.LEATHER_T2, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.ROHAN.getValue())));
    public static final Item ROHIRRIC_ORNAMENTED_LEATHER_HELMET = registerArmorPiece("rohirric_ornamented_leather_helmet",
            (settings) -> new CustomHelmetItem(ArmorMaterialsME.LEATHER_T2, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.ROHAN.getValue())));

    public static final Item ROHIRRIC_LEATHER_VEST = registerDyeableArmorPiece("rohirric_leather_vest",
            (settings) -> new CustomChestplateItem(ArmorMaterialsME.LEATHER_T2, settings), new Item.Settings()
                    .component(DataComponentTypes.DYED_COLOR, new DyedColorComponent(15256475))
                    .component(DataComponentTypesME.BACK_ATTACHMENT_DATA, BackAttachmentDataComponent.newBackAttachment(BackAttachmentsME.ROHIRRIC_CAPE))
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.ROHAN.getValue())).component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.ROHAN.getValue())));
    public static final Item ROHIRRIC_LEATHER_SCALE_VEST = registerDyeableArmorPiece("rohirric_leather_scale_vest",
            (settings) -> new CustomChestplateItem(ArmorMaterialsME.LEATHER_T2, settings), new Item.Settings()
                    .component(DataComponentTypes.DYED_COLOR, new DyedColorComponent(15256475))
                    .component(DataComponentTypesME.BACK_ATTACHMENT_DATA, BackAttachmentDataComponent.newBackAttachment(BackAttachmentsME.ROHIRRIC_CAPE)).component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.ROHAN.getValue())));
    public static final Item ROHIRRIC_GAMBESON = registerDyeableArmorPiece("rohirric_gambeson",
            (settings) -> new CustomChestplateItem(ArmorMaterialsME.LEATHER_T2, settings), new Item.Settings()
                    .component(DataComponentTypes.DYED_COLOR, new DyedColorComponent(15256475))
                    .component(DataComponentTypesME.BACK_ATTACHMENT_DATA, BackAttachmentDataComponent.newBackAttachment(BackAttachmentsME.ROHIRRIC_CAPE)).component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.ROHAN.getValue())));

    public static final Item ROHIRRIC_MILITIA_HELMET = registerArmorPiece("rohirric_militia_helmet",
            (settings) -> new CustomHelmetItem(ArmorMaterialsME.IRON_T3, settings), new Item.Settings().component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.ROHAN.getValue())));
    public static final Item ROHIRRIC_BRACED_MILITIA_HELMET = registerCustomModelArmorPiece("rohirric_braced_militia_helmet",
            (settings) -> new CustomHelmetItem(ArmorMaterialsME.IRON_T3, settings), new Item.Settings().component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.ROHAN.getValue())));
    public static final Item ROHIRRIC_REINFORCED_MILITIA_HELMET = registerArmorPiece("rohirric_reinforced_militia_helmet",
            (settings) -> new CustomHelmetItem(ArmorMaterialsME.IRON_T3, settings), new Item.Settings().component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.ROHAN.getValue())));
    public static final Item ROHIRRIC_ORNAMENTED_MILITIA_HELMET = registerCustomModelArmorPiece("rohirric_ornamented_militia_helmet",
            (settings) -> new CustomHelmetItem(ArmorMaterialsME.IRON_T3, settings), new Item.Settings().component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.ROHAN.getValue())));

    public static final Item ROHIRRIC_REINFORCED_LEATHER_VEST = registerDyeableArmorPiece("rohirric_reinforced_leather_vest",
            (settings) -> new CustomChestplateItem(ArmorMaterialsME.IRON_T3, settings), new Item.Settings()
                    .component(DataComponentTypes.DYED_COLOR, new DyedColorComponent(15256475))
                    .component(DataComponentTypesME.BACK_ATTACHMENT_DATA, BackAttachmentDataComponent.newBackAttachment(BackAttachmentsME.ROHIRRIC_CAPE)).component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.ROHAN.getValue())));
    public static final Item ROHIRRIC_BRACED_MAIL_SHIRT = registerDyeableArmorPiece("rohirric_braced_mail_shirt",
            (settings) -> new CustomChestplateItem(ArmorMaterialsME.IRON_T3, settings), new Item.Settings()
                    .component(DataComponentTypes.DYED_COLOR, new DyedColorComponent(15256475))
                    .component(DataComponentTypesME.BACK_ATTACHMENT_DATA, BackAttachmentDataComponent.newBackAttachment(BackAttachmentsME.ROHIRRIC_CAPE)).component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.ROHAN.getValue())));

    public static final Item ROHIRRIC_REINFORCED_LEATHER_SCALE_VEST = registerArmorPiece("rohirric_reinforced_leather_scale_vest",
            (settings) -> new CustomChestplateItem(ArmorMaterialsME.IRON_T3, settings), new Item.Settings()
                    .component(DataComponentTypesME.BACK_ATTACHMENT_DATA, BackAttachmentDataComponent.newBackAttachment(BackAttachmentsME.ROHIRRIC_CAPE)).component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.ROHAN.getValue())));

    public static final Item ROHIRRIC_SOLDIER_HELMET = registerArmorPiece("rohirric_soldier_helmet",
            (settings) -> new CustomHelmetItem(ArmorMaterialsME.STEEL_T4, settings), new Item.Settings().component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.ROHAN.getValue())));
    public static final Item ROHIRRIC_ORNAMENTED_SOLDIER_HELMET = registerCustomModelArmorPiece("rohirric_ornamented_soldier_helmet",
            (settings) -> new CustomHelmetItem(ArmorMaterialsME.STEEL_T4, settings), new Item.Settings().component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.ROHAN.getValue())));

    public static final Item ROHIRRIC_ROYAL_GUARD_HELMET = registerCustomModelArmorPiece("rohirric_royal_guard_helmet",
            (settings) -> new CustomHelmetItem(ArmorMaterialsME.STEEL_T4, settings), new Item.Settings().component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.ROHAN.getValue())));

    public static final Item ROHIRRIC_SCALE_HAUBERK = registerArmorPiece("rohirric_scale_hauberk",
            (settings) -> new CustomChestplateItem(ArmorMaterialsME.STEEL_T4, settings), new Item.Settings().component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.ROHAN.getValue())));
    public static final Item ROHIRRIC_ORNAMENTED_SCALE_HAUBERK = registerArmorPiece("rohirric_ornamented_scale_hauberk",
            (settings) -> new CustomChestplateItem(ArmorMaterialsME.STEEL_T4, settings), new Item.Settings()
                    .component(DataComponentTypesME.BACK_ATTACHMENT_DATA, BackAttachmentDataComponent.newBackAttachment(BackAttachmentsME.ROHIRRIC_ROYAL_GUARD_CAPE)).component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.ROHAN.getValue())));

    public static final Item ROHIRRIC_SCALE_JACKET = registerArmorPiece("rohirric_scale_jacket",
            (settings) -> new CustomLeggingsItem(ArmorMaterialsME.STEEL_T4, settings), new Item.Settings().component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.ROHAN.getValue())));

    public static final Item EORLING_MARSHAL_HELMET = registerCustomModelArmorPiece("eorling_marshal_helmet",
            (settings) -> new CustomHelmetItem(ArmorMaterialsME.STEEL_T5, settings), new Item.Settings().component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.ROHAN.getValue())));
    public static final Item EORLING_MARSHAL_CHESTPLATE = registerArmorPiece("eorling_marshal_chestplate",
            (settings) -> new CustomChestplateItem(ArmorMaterialsME.STEEL_T5, settings), new Item.Settings()
                    .component(DataComponentTypesME.BACK_ATTACHMENT_DATA, BackAttachmentDataComponent.newBackAttachment(BackAttachmentsME.EORLING_MARSHAL_CAPE)).component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.ROHAN.getValue())));
    public static final Item EORLING_MARSHAL_LEGGINGS = registerArmorPiece("eorling_marshal_leggings",
            (settings) -> new CustomLeggingsItem(ArmorMaterialsME.STEEL_T5, settings), new Item.Settings().component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.ROHAN.getValue())));
    public static final Item EORLING_MARSHAL_BOOTS = registerArmorPiece("eorling_marshal_boots",
            (settings) -> new CustomBootsItem(ArmorMaterialsME.STEEL_T5, settings), new Item.Settings().component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.ROHAN.getValue())));

    public static final Item HORSE_LORD_HELMET = registerCustomModelArmorPiece("horse_lord_helmet",
            (settings) -> new CustomHelmetItem(ArmorMaterialsME.STEEL_T5, settings), new Item.Settings().component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.ROHAN.getValue())));
    public static final Item HORSE_LORD_CHESTPLATE = registerArmorPiece("horse_lord_chestplate",
            (settings) -> new CustomChestplateItem(ArmorMaterialsME.STEEL_T5, settings), new Item.Settings()
                    .component(DataComponentTypesME.BACK_ATTACHMENT_DATA, BackAttachmentDataComponent.newBackAttachment(BackAttachmentsME.HORSE_LORD_CAPE)).component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.ROHAN.getValue())));
    public static final Item HORSE_LORD_LEGGINGS = registerArmorPiece("horse_lord_leggings",
            (settings) -> new CustomLeggingsItem(ArmorMaterialsME.STEEL_T5, settings), new Item.Settings().component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.ROHAN.getValue())));
    public static final Item HORSE_LORD_BOOTS = registerArmorPiece("horse_lord_boots",
            (settings) -> new CustomBootsItem(ArmorMaterialsME.STEEL_T5, settings), new Item.Settings().component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.ROHAN.getValue())));

    public static final Item ROHIRRIC_CAPE = registerBackAttachment("rohirric_cape",
            (settings) -> new BackAttachmentItem(settings, ArmorMaterialsME.FABRIC_T0), new Item.Settings()
                    .component(DataComponentTypesME.BACK_ATTACHMENT_DATA, BackAttachmentDataComponent.newBackAttachment(BackAttachmentsME.ROHIRRIC_CAPE)).component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.ROHAN.getValue())));
    public static final Item ROHIRRIC_ROYAL_GUARD_CAPE = registerBackAttachment("rohirric_royal_guard_cape",
            (settings) -> new BackAttachmentItem(settings, ArmorMaterialsME.FABRIC_T0), new Item.Settings()
                    .component(DataComponentTypesME.BACK_ATTACHMENT_DATA, BackAttachmentDataComponent.newBackAttachment(BackAttachmentsME.ROHIRRIC_ROYAL_GUARD_CAPE)).component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.ROHAN.getValue())));
    public static final Item EORLING_MARSHAL_CAPE = registerBackAttachment("eorling_marshal_cape",
            (settings) -> new BackAttachmentItem(settings, ArmorMaterialsME.FABRIC_T0), new Item.Settings()
                    .component(DataComponentTypesME.BACK_ATTACHMENT_DATA, BackAttachmentDataComponent.newBackAttachment(BackAttachmentsME.EORLING_MARSHAL_CAPE)).component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.ROHAN.getValue())));
    public static final Item HORSE_LORD_CAPE = registerBackAttachment("horse_lord_cape",
            (settings) -> new BackAttachmentItem(settings, ArmorMaterialsME.FABRIC_T0), new Item.Settings()
                    .component(DataComponentTypesME.BACK_ATTACHMENT_DATA, BackAttachmentDataComponent.newBackAttachment(BackAttachmentsME.HORSE_LORD_CAPE)).component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.ROHAN.getValue())));

    public static final Item ROHIRRIC_HORSE_ARMOR = registerGeneratedItem("rohirric_horse_armor",
            Item::new, new Item.Settings().horseArmor(ArmorMaterialsME.ROHIRRIC_HORSE_ARMOR.material()).maxCount(1)
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.ROHAN.getValue())));
    //endregion

    //region DALE
    public static final Item DALISH_ARMING_COAT_BLACK_FUR = registerDyeableArmorPiece("dalish_arming_coat_black_fur",
            (settings) -> new CustomChestplateItem(ArmorMaterialsME.FABRIC_T1, settings), new Item.Settings()
                    .component(DataComponentTypes.DYED_COLOR, new DyedColorComponent(7296407))
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.DALE.getValue())));
    public static final Item DALISH_ARMING_COAT_BROWN_FUR = registerDyeableArmorPiece("dalish_arming_coat_brown_fur",
            (settings) -> new CustomChestplateItem(ArmorMaterialsME.FABRIC_T1, settings), new Item.Settings()
                    .component(DataComponentTypes.DYED_COLOR, new DyedColorComponent(7296407))
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.DALE.getValue())));
    public static final Item DALISH_ARMING_COAT_TAN_FUR = registerDyeableArmorPiece("dalish_arming_coat_tan_fur",
            (settings) -> new CustomChestplateItem(ArmorMaterialsME.FABRIC_T1, settings), new Item.Settings()
                    .component(DataComponentTypes.DYED_COLOR, new DyedColorComponent(7296407))
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.DALE.getValue())));
    public static final Item DALISH_HEYDAY_ARMING_COAT = registerArmorPiece("dalish_heyday_arming_coat",
            (settings) -> new CustomChestplateItem(ArmorMaterialsME.FABRIC_T1, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.DALE.getValue())));

    public static final Item DALISH_BOOTS = registerArmorPiece("dalish_boots",
            (settings) -> new CustomBootsItem(ArmorMaterialsME.LEATHER_T2, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.DALE.getValue())));

    public static final Item DALISH_HELMET = registerCustomModelArmorPiece("dalish_helmet",
            (settings) -> new CustomHelmetItem(ArmorMaterialsME.IRON_T3, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.DALE.getValue())));
    public static final Item DALISH_HELMET_BLACK_FUR = registerCustomModelArmorPiece("dalish_helmet_black_fur",
            (settings) -> new CustomHelmetItem(ArmorMaterialsME.IRON_T3, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.DALE.getValue())));
    public static final Item DALISH_HELMET_BROWN_FUR = registerCustomModelArmorPiece("dalish_helmet_brown_fur",
            (settings) -> new CustomHelmetItem(ArmorMaterialsME.IRON_T3, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.DALE.getValue())));
    public static final Item DALISH_HELMET_TAN_FUR = registerCustomModelArmorPiece("dalish_helmet_tan_fur",
            (settings) -> new CustomHelmetItem(ArmorMaterialsME.IRON_T3, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.DALE.getValue())));

    public static final Item DALISH_MAIL_HAUBERK = registerArmorPiece("dalish_mail_hauberk",
            (settings) -> new CustomChestplateItem(ArmorMaterialsME.IRON_T3, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.DALE.getValue())));

    public static final Item DALISH_MAIL_COAT = registerArmorPiece("dalish_mail_coat",
            (settings) -> new CustomLeggingsItem(ArmorMaterialsME.IRON_T3, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.DALE.getValue())));

    public static final Item DALISH_BURGONET = registerCustomModelArmorPiece("dalish_burgonet",
            (settings) -> new CustomHelmetItem(ArmorMaterialsME.STEEL_T4, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.DALE.getValue())));

    public static final Item DALISH_SCALE_HAUBERK = registerArmorPiece("dalish_scale_hauberk",
            (settings) -> new CustomChestplateItem(ArmorMaterialsME.STEEL_T4, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.DALE.getValue())));

    public static final Item DALISH_HEYDAY_HELMET = registerCustomModelArmorPiece("dalish_heyday_helmet",
            (settings) -> new CustomHelmetItem(ArmorMaterialsME.STEEL_T4, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.DALE.getValue())));
    public static final Item DALISH_HEYDAY_CHESTPLATE = registerArmorPiece("dalish_heyday_chestplate",
            (settings) -> new CustomChestplateItem(ArmorMaterialsME.STEEL_T4, settings), new Item.Settings()
                    .component(DataComponentTypesME.BACK_ATTACHMENT_DATA, BackAttachmentDataComponent.newBackAttachment(BackAttachmentsME.DALISH_HEYDAY_CLOAK))
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.DALE.getValue())));
    public static final Item DALISH_HEYDAY_MAIL_COAT = registerArmorPiece("dalish_heyday_mail_coat",
            (settings) -> new CustomLeggingsItem(ArmorMaterialsME.STEEL_T4, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.DALE.getValue())));
    public static final Item DALISH_HEYDAY_BOOTS = registerArmorPiece("dalish_heyday_boots",
            (settings) -> new CustomBootsItem(ArmorMaterialsME.STEEL_T4, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.DALE.getValue())));

    public static final Item BARDING_SOLDIER_HELMET = registerCustomModelArmorPiece("barding_soldier_helmet",
            (settings) -> new CustomHelmetItem(ArmorMaterialsME.STEEL_T5, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.DALE.getValue())));
    public static final Item BARDING_SOLDIER_CHESTPLATE = registerArmorPiece("barding_soldier_chestplate",
            (settings) -> new CustomChestplateItem(ArmorMaterialsME.STEEL_T5, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.DALE.getValue())));
    public static final Item BARDING_MAIL_SKIRT = registerArmorPiece("barding_mail_skirt",
            (settings) -> new CustomLeggingsItem(ArmorMaterialsME.STEEL_T5, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.DALE.getValue())));
    public static final Item BARDING_PLATED_BOOTS = registerArmorPiece("barding_plated_boots",
            (settings) -> new CustomBootsItem(ArmorMaterialsME.STEEL_T5, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.DALE.getValue())));

    public static final Item BARDING_SERGEANT_HELMET = registerCustomModelArmorPiece("barding_sergeant_helmet",
            (settings) -> new CustomHelmetItem(ArmorMaterialsME.STEEL_T5, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.DALE.getValue())));
    public static final Item BARDING_SERGEANT_CHESTPLATE = registerArmorPiece("barding_sergeant_chestplate",
            (settings) -> new CustomChestplateItem(ArmorMaterialsME.STEEL_T5, settings), new Item.Settings()
                    .component(DataComponentTypesME.BACK_ATTACHMENT_DATA, BackAttachmentDataComponent.newBackAttachment(BackAttachmentsME.BARDING_SERGEANT_CAPE))
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.DALE.getValue())));

    public static final Item BARDING_SURCOAT = registerBackAttachment("barding_surcoat",
            (settings) -> new BackAttachmentItem(settings, ArmorMaterialsME.FABRIC_T0), new Item.Settings()
                    .component(DataComponentTypesME.BACK_ATTACHMENT_DATA, BackAttachmentDataComponent.newBackAttachment(BackAttachmentsME.BARDING_SURCOAT))
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.DALE.getValue())));
    public static final Item DALISH_HEYDAY_CLOAK = registerBackAttachment("dalish_heyday_cloak",
            (settings) -> new BackAttachmentItem(settings, ArmorMaterialsME.FABRIC_T0), new Item.Settings()
                    .component(DataComponentTypesME.BACK_ATTACHMENT_DATA, BackAttachmentDataComponent.newBackAttachment(BackAttachmentsME.DALISH_HEYDAY_CLOAK))
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.DALE.getValue())));
    public static final Item BARDING_SERGEANT_CAPE = registerBackAttachment("barding_sergeant_cape",
            (settings) -> new BackAttachmentItem(settings, ArmorMaterialsME.FABRIC_T0), new Item.Settings()
                    .component(DataComponentTypesME.BACK_ATTACHMENT_DATA, BackAttachmentDataComponent.newBackAttachment(BackAttachmentsME.BARDING_SERGEANT_CAPE))
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.DALE.getValue())));

    public static final Item DALISH_HORSE_ARMOR = registerGeneratedItem("dalish_horse_armor",
            Item::new, new Item.Settings().horseArmor(ArmorMaterialsME.DALISH_HORSE_ARMOR.material()).maxCount(1)
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.DALE.getValue())));
    //endregion

    //endregion

    //region DWARVES

    //region GENERIC
    public static final Item DWARVEN_MINER_HELMET = registerDyeableCustomModelArmorPiece("dwarven_miner_helmet",
            (settings) -> new CustomHelmetItem(ArmorMaterialsME.LEATHER_T2, settings), new Item.Settings()
                    .component(DataComponentTypes.DYED_COLOR, new DyedColorComponent(7098174)));

    public static final Item DWARVEN_GAMBESON = registerDyeableArmorPiece("dwarven_gambeson",
            (settings) -> new CustomChestplateItem(ArmorMaterialsME.LEATHER_T2, settings), new Item.Settings()
                    .component(DataComponentTypes.DYED_COLOR, new DyedColorComponent(11572859)));

    public static final Item DWARVEN_MAIL_COIF = registerArmorPiece("dwarven_mail_coif",
            (settings) -> new CustomHelmetItem(ArmorMaterialsME.IRON_T3, settings), new Item.Settings());

    public static final Item DWARVEN_MAIL_HAUBERK = registerArmorPiece("dwarven_mail_hauberk",
            (settings) -> new CustomChestplateItem(ArmorMaterialsME.IRON_T3, settings), new Item.Settings());
    public static final Item DWARVEN_MINER_GAMBESON = registerDyeableArmorPiece("dwarven_miner_gambeson",
            (settings) -> new CustomChestplateItem(ArmorMaterialsME.IRON_T3, settings), new Item.Settings()
                    .component(DataComponentTypes.DYED_COLOR, new DyedColorComponent(11572859)));

    public static final Item DWARVEN_MAIL_COAT = registerArmorPiece("dwarven_mail_coat",
            (settings) -> new CustomLeggingsItem(ArmorMaterialsME.IRON_T3, settings), new Item.Settings());

    public static final Item DWARVEN_MAIL_CHAUSSES = registerArmorPiece("dwarven_mail_chausses",
            (settings) -> new CustomBootsItem(ArmorMaterialsME.IRON_T3, settings), new Item.Settings());

    public static final Item DWARVEN_BOOTS = registerArmorPiece("dwarven_boots",
            (settings) -> new CustomBootsItem(ArmorMaterialsME.IRON_T3, settings), new Item.Settings());

    public static final Item DWARVEN_SCALE_HAUBERK = registerArmorPiece("dwarven_scale_hauberk",
            (settings) -> new CustomChestplateItem(ArmorMaterialsME.DWARVEN_STEEL_T4, settings), new Item.Settings());

    public static final Item DWARVEN_SCALE_COAT = registerArmorPiece("dwarven_scale_coat",
            (settings) -> new CustomLeggingsItem(ArmorMaterialsME.DWARVEN_STEEL_T4, settings), new Item.Settings());

    public static final Item DWARVEN_REINFORCED_BOOTS = registerArmorPiece("dwarven_reinforced_boots",
            (settings) -> new CustomBootsItem(ArmorMaterialsME.DWARVEN_STEEL_T4, settings), new Item.Settings());
    //endregion

    //region LONGBEARDS
    public static final Item LONGBEARD_SEGMENTED_HELMET = registerArmorPiece("longbeard_segmented_helmet",
            (settings) -> new CustomHelmetItem(ArmorMaterialsME.BRONZE_T2, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.LONGBEARDS.getValue())));

    public static final Item LONGBEARD_LEATHER_HAUBERK = registerDyeableArmorPiece("longbeard_leather_hauberk",
            (settings) -> new CustomChestplateItem(ArmorMaterialsME.LEATHER_T2, settings), new Item.Settings()
                    .component(DataComponentTypes.DYED_COLOR, new DyedColorComponent(5914168))
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.LONGBEARDS.getValue())));
    public static final Item LONGBEARD_WANDERER_COAT = registerDyeableArmorPiece("longbeard_wanderer_coat",
            (settings) -> new CustomChestplateItem(ArmorMaterialsME.LEATHER_T2, settings), new Item.Settings()
                    .component(DataComponentTypes.DYED_COLOR, new DyedColorComponent(5914168))
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.LONGBEARDS.getValue())));

    public static final Item LONGBEARD_LEATHER_LEGGINGS = registerDyeableArmorPiece("longbeard_leather_leggings",
            (settings) -> new CustomLeggingsItem(ArmorMaterialsME.LEATHER_T2, settings), new Item.Settings()
                    .component(DataComponentTypes.DYED_COLOR, new DyedColorComponent(5914168))
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.LONGBEARDS.getValue())));

    public static final Item LONGBEARD_PARTISAN_OUTFIT = registerDyeableArmorPiece("longbeard_partisan_outfit",
            (settings) -> new CustomChestplateItem(ArmorMaterialsME.IRON_T3, settings), new Item.Settings()
                    .component(DataComponentTypes.DYED_COLOR, new DyedColorComponent(5914168))
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.LONGBEARDS.getValue())));
    public static final Item LONGBEARD_REINFORCED_LEATHER_HAUBERK = registerDyeableArmorPiece("longbeard_reinforced_leather_hauberk",
            (settings) -> new CustomChestplateItem(ArmorMaterialsME.IRON_T3, settings), new Item.Settings()
                    .component(DataComponentTypes.DYED_COLOR, new DyedColorComponent(5914168))
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.LONGBEARDS.getValue())));

    //region EREBOR
    public static final Item EREBOR_LEATHER_HELMET = registerDyeableArmorPiece("erebor_leather_helmet",
            (settings) -> new CustomHelmetItem(ArmorMaterialsME.IRON_T2, settings), new Item.Settings()
                    .component(DataComponentTypes.DYED_COLOR, new DyedColorComponent(7098174))
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.LONGBEARDS_EREBOR.getValue())));
    public static final Item EREBOR_BRACED_LEATHER_HELMET = registerDyeableArmorPiece("erebor_braced_leather_helmet",
            (settings) -> new CustomHelmetItem(ArmorMaterialsME.IRON_T2, settings), new Item.Settings()
                    .component(DataComponentTypes.DYED_COLOR, new DyedColorComponent(6309430))
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.LONGBEARDS_EREBOR.getValue())));
    public static final Item EREBOR_NASAL_LEATHER_HELMET = registerDyeableArmorPiece("erebor_nasal_leather_helmet",
            (settings) -> new CustomHelmetItem(ArmorMaterialsME.IRON_T2, settings), new Item.Settings()
                    .component(DataComponentTypes.DYED_COLOR, new DyedColorComponent(7098174))
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.LONGBEARDS_EREBOR.getValue())));

    public static final Item EREBOR_LEATHER_LEGGINGS = registerDyeableArmorPiece("erebor_leather_leggings",
            (settings) -> new CustomLeggingsItem(ArmorMaterialsME.LEATHER_T2, settings), new Item.Settings()
                    .component(DataComponentTypes.DYED_COLOR, new DyedColorComponent(5914168))
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.LONGBEARDS_EREBOR.getValue())));

    public static final Item EREBOR_MAIL_COIF = registerArmorPiece("erebor_mail_coif",
            (settings) -> new CustomHelmetItem(ArmorMaterialsME.IRON_T3, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.LONGBEARDS_EREBOR.getValue())));
    public static final Item EREBOR_GILDED_MAIL_COIF = registerArmorPiece("erebor_gilded_mail_coif",
            (settings) -> new CustomHelmetItem(ArmorMaterialsME.IRON_T3, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.LONGBEARDS_EREBOR.getValue())));
    public static final Item EREBOR_HELMET = registerArmorPiece("erebor_helmet",
            (settings) -> new CustomHelmetItem(ArmorMaterialsME.IRON_T3, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.LONGBEARDS_EREBOR.getValue())));

    public static final Item EREBOR_MAIL_HAUBERK = registerArmorPiece("erebor_mail_hauberk",
            (settings) -> new CustomChestplateItem(ArmorMaterialsME.IRON_T3, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.LONGBEARDS_EREBOR.getValue())));
    public static final Item EREBOR_PADDED_MAIL_HAUBERK = registerDyeableArmorPiece("erebor_padded_mail_hauberk",
            (settings) -> new CustomChestplateItem(ArmorMaterialsME.IRON_T3, settings), new Item.Settings()
                    .component(DataComponentTypes.DYED_COLOR, new DyedColorComponent(3687263))
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.LONGBEARDS_EREBOR.getValue())));

    public static final Item EREBOR_MAIL_COAT = registerArmorPiece("erebor_mail_coat",
            (settings) -> new CustomLeggingsItem(ArmorMaterialsME.IRON_T3, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.LONGBEARDS_EREBOR.getValue())));
    public static final Item EREBOR_MAIL_LEGGINGS = registerArmorPiece("erebor_mail_leggings",
            (settings) -> new CustomLeggingsItem(ArmorMaterialsME.IRON_T3, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.LONGBEARDS_EREBOR.getValue())));

    public static final Item EREBOR_MAIL_CHAUSSES = registerArmorPiece("erebor_mail_chausses",
            (settings) -> new CustomBootsItem(ArmorMaterialsME.IRON_T3, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.LONGBEARDS_EREBOR.getValue())));
    public static final Item EREBOR_BOOTS = registerArmorPiece("erebor_boots",
            (settings) -> new CustomBootsItem(ArmorMaterialsME.IRON_T3, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.LONGBEARDS_EREBOR.getValue())));

    public static final Item RAVENHILL_HELMET = registerArmorPiece("ravenhill_helmet",
            (settings) -> new CustomHelmetItem(ArmorMaterialsME.DWARVEN_STEEL_T4, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.LONGBEARDS_EREBOR.getValue())));

    public static final Item RAVENHILL_GILDED_HELMET = registerArmorPiece("ravenhill_gilded_helmet",
            (settings) -> new CustomHelmetItem(ArmorMaterialsME.DWARVEN_STEEL_T4, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.LONGBEARDS_EREBOR.getValue())));

    public static final Item EREBOR_SCALE_HAUBERK = registerArmorPiece("erebor_scale_hauberk",
            (settings) -> new CustomChestplateItem(ArmorMaterialsME.DWARVEN_STEEL_T4, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.LONGBEARDS_EREBOR.getValue())));
    public static final Item EREBOR_LONG_COAT = registerDyeableArmorPiece("erebor_long_coat",
            (settings) -> new CustomChestplateItem(ArmorMaterialsME.DWARVEN_STEEL_T4, settings), new Item.Settings()
                    .component(DataComponentTypes.DYED_COLOR, new DyedColorComponent(5914168))
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.LONGBEARDS_EREBOR.getValue())));
    public static final Item EREBOR_BRAWLER_CHESTPLATE = registerDyeableArmorPiece("erebor_brawler_chestplate",
            (settings) -> new CustomChestplateItem(ArmorMaterialsME.DWARVEN_STEEL_T4, settings), new Item.Settings()
                    .component(DataComponentTypes.DYED_COLOR, new DyedColorComponent(3687263))
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.LONGBEARDS_EREBOR.getValue())));
    public static final Item EREBOR_REINFORCED_LEATHER_HAUBERK = registerDyeableArmorPiece("erebor_reinforced_leather_hauberk",
            (settings) -> new CustomChestplateItem(ArmorMaterialsME.DWARVEN_STEEL_T4, settings), new Item.Settings()
                    .component(DataComponentTypes.DYED_COLOR, new DyedColorComponent(5914168))
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.LONGBEARDS_EREBOR.getValue())));
    public static final Item EREBOR_REINFORCED_COAT = registerDyeableArmorPiece("erebor_reinforced_coat",
            (settings) -> new CustomChestplateItem(ArmorMaterialsME.DWARVEN_STEEL_T4, settings), new Item.Settings()
                    .component(DataComponentTypes.DYED_COLOR, new DyedColorComponent(3687263))
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.LONGBEARDS_EREBOR.getValue())));
    public static final Item RAVENHILL_REINFORCED_MAIL_HAUBERK = registerArmorPiece("ravenhill_reinforced_mail_hauberk",
            (settings) -> new CustomChestplateItem(ArmorMaterialsME.DWARVEN_STEEL_T4, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.LONGBEARDS_EREBOR.getValue())));

    public static final Item EREBOR_SCALE_COAT = registerArmorPiece("erebor_scale_coat",
            (settings) -> new CustomLeggingsItem(ArmorMaterialsME.DWARVEN_STEEL_T4, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.LONGBEARDS_EREBOR.getValue())));
    public static final Item EREBOR_SCALE_LEGGINGS = registerArmorPiece("erebor_scale_leggings",
            (settings) -> new CustomLeggingsItem(ArmorMaterialsME.DWARVEN_STEEL_T4, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.LONGBEARDS_EREBOR.getValue())));

    public static final Item RAVENHILL_BOOTS = registerArmorPiece("ravenhill_boots",
            (settings) -> new CustomBootsItem(ArmorMaterialsME.DWARVEN_STEEL_T4, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.LONGBEARDS_EREBOR.getValue())));

    public static final Item EREBOR_GUARD_HELMET = registerArmorPiece("erebor_guard_helmet",
            (settings) -> new CustomHelmetItem(ArmorMaterialsME.DWARVEN_STEEL_T5, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.LONGBEARDS_EREBOR.getValue())));
    public static final Item EREBOR_CAPTAIN_HELMET = registerCustomModelArmorPiece("erebor_captain_helmet",
            (settings) -> new CustomHelmetItem(ArmorMaterialsME.DWARVEN_STEEL_T5, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.LONGBEARDS_EREBOR.getValue())));

    public static final Item EREBOR_PLATE_HELMET = registerCustomModelArmorPiece("erebor_plate_helmet",
            (settings) -> new CustomHelmetItem(ArmorMaterialsME.DWARVEN_STEEL_T5, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.LONGBEARDS_EREBOR.getValue())));
    public static final Item EREBOR_PLATE_CHESTPLATE = registerCustomModelArmorPiece("erebor_plate_chestplate",
            (settings) -> new CustomChestplateItem(ArmorMaterialsME.DWARVEN_STEEL_T5, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.LONGBEARDS_EREBOR.getValue())));
    public static final Item EREBOR_PLATE_LEGGINGS = registerArmorPiece("erebor_plate_leggings",
            (settings) -> new CustomLeggingsItem(ArmorMaterialsME.DWARVEN_STEEL_T5, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.LONGBEARDS_EREBOR.getValue())));
    public static final Item EREBOR_PLATE_BOOTS = registerArmorPiece("erebor_plate_boots",
            (settings) -> new CustomBootsItem(ArmorMaterialsME.DWARVEN_STEEL_T5, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.LONGBEARDS_EREBOR.getValue())));

    public static final Item RAVENHILL_WATCHWARDEN_HELMET = registerArmorPiece("ravenhill_watchwarden_helmet",
            (settings) -> new CustomHelmetItem(ArmorMaterialsME.DWARVEN_STEEL_T5, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.LONGBEARDS_EREBOR.getValue())));
    public static final Item RAVENHILL_WATCHWARDEN_CHESTPLATE = registerCustomModelArmorPiece("ravenhill_watchwarden_chestplate",
            (settings) -> new CustomChestplateItem(ArmorMaterialsME.DWARVEN_STEEL_T5, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.LONGBEARDS_EREBOR.getValue())));
    public static final Item RAVENHILL_WATCHWARDEN_LEGGINGS = registerArmorPiece("ravenhill_watchwarden_leggings",
            (settings) -> new CustomLeggingsItem(ArmorMaterialsME.DWARVEN_STEEL_T5, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.LONGBEARDS_EREBOR.getValue())));
    public static final Item RAVENHILL_WATCHWARDEN_BOOTS = registerArmorPiece("ravenhill_watchwarden_boots",
            (settings) -> new CustomBootsItem(ArmorMaterialsME.DWARVEN_STEEL_T5, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.LONGBEARDS_EREBOR.getValue())));

    public static final Item RAVENHILL_SENTINEL_HELMET = registerCustomModelArmorPiece("ravenhill_sentinel_helmet",
            (settings) -> new CustomHelmetItem(ArmorMaterialsME.DWARVEN_STEEL_T5, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.LONGBEARDS_EREBOR.getValue())));
    public static final Item RAVENHILL_SENTINEL_CHESTPLATE = registerCustomModelArmorPiece("ravenhill_sentinel_chestplate",
            (settings) -> new CustomChestplateItem(ArmorMaterialsME.DWARVEN_STEEL_T5, settings), new Item.Settings()
                    .component(DataComponentTypesME.BACK_ATTACHMENT_DATA, BackAttachmentDataComponent.newBackAttachment(BackAttachmentsME.RAVENHILL_SENTINEL_CAPE))
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.LONGBEARDS_EREBOR.getValue())));
    public static final Item RAVENHILL_SENTINEL_LEGGINGS = registerArmorPiece("ravenhill_sentinel_leggings",
            (settings) -> new CustomLeggingsItem(ArmorMaterialsME.DWARVEN_STEEL_T5, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.LONGBEARDS_EREBOR.getValue())));
    public static final Item RAVENHILL_SENTINEL_BOOTS = registerArmorPiece("ravenhill_sentinel_boots",
            (settings) -> new CustomBootsItem(ArmorMaterialsME.DWARVEN_STEEL_T5, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.LONGBEARDS_EREBOR.getValue())));

    public static final Item EREBOR_GATEWARDEN_HELMET = registerCustomModelArmorPiece("erebor_gatewarden_helmet",
            (settings) -> new CustomHelmetItem(ArmorMaterialsME.DWARVEN_STEEL_T5, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.LONGBEARDS_EREBOR.getValue())));
    public static final Item EREBOR_GATEWARDEN_CHESTPLATE = registerCustomModelArmorPiece("erebor_gatewarden_chestplate",
            (settings) -> new CustomChestplateItem(ArmorMaterialsME.DWARVEN_STEEL_T5, settings), new Item.Settings()
                    .component(DataComponentTypesME.BACK_ATTACHMENT_DATA, BackAttachmentDataComponent.newBackAttachment(BackAttachmentsME.EREBOR_CAPE))
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.LONGBEARDS_EREBOR.getValue())));
    public static final Item EREBOR_GATEWARDEN_LEGGINGS = registerArmorPiece("erebor_gatewarden_leggings",
            (settings) -> new CustomLeggingsItem(ArmorMaterialsME.DWARVEN_STEEL_T5, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.LONGBEARDS_EREBOR.getValue())));
    public static final Item EREBOR_GATEWARDEN_BOOTS = registerArmorPiece("erebor_gatewarden_boots",
            (settings) -> new CustomBootsItem(ArmorMaterialsME.DWARVEN_STEEL_T5, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.LONGBEARDS_EREBOR.getValue())));

    public static final Item EREBOR_CAPE = registerBackAttachment("erebor_cape",
            (settings) -> new BackAttachmentItem(settings, ArmorMaterialsME.FABRIC_T0), new Item.Settings()
                    .component(DataComponentTypesME.BACK_ATTACHMENT_DATA, BackAttachmentDataComponent.newBackAttachment(BackAttachmentsME.EREBOR_CAPE))
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.LONGBEARDS_EREBOR.getValue())));
    public static final Item RAVENHILL_SENTINEL_CAPE = registerBackAttachment("ravenhill_sentinel_cape",
            (settings) -> new BackAttachmentItem(settings, ArmorMaterialsME.FABRIC_T0), new Item.Settings()
                    .component(DataComponentTypesME.BACK_ATTACHMENT_DATA, BackAttachmentDataComponent.newBackAttachment(BackAttachmentsME.RAVENHILL_SENTINEL_CAPE))
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.LONGBEARDS_EREBOR.getValue())));

    public static final Item BROADHOOF_GOAT_PADDED_ARMOR = registerDyeableArmorPiece("broadhoof_goat_leather_armor",
            Item::new, ItemSettingsME.goatArmor(ArmorMaterialsME.BROADHOOF_GOAT_LEATHER_ARMOR.material())
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.LONGBEARDS_EREBOR.getValue())));

    public static final Item BROADHOOF_GOAT_ORNAMENTED_PADDED_ARMOR = registerDyeableArmorPiece("broadhoof_goat_ornamented_leather_armor",
            Item::new, ItemSettingsME.goatArmor(ArmorMaterialsME.BROADHOOF_GOAT_ORNAMENTED_LEATHER_ARMOR.material())
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.LONGBEARDS_EREBOR.getValue())));
    public static final Item BROADHOOF_GOAT_PLATE_ARMOR = registerGeneratedItem("broadhoof_goat_plate_armor",
            Item::new, ItemSettingsME.goatArmor(ArmorMaterialsME.BROADHOOF_GOAT_PLATE_ARMOR.material())
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.LONGBEARDS_EREBOR.getValue())));
    //endregion
    //endregion

    //endregion

    //region ELVES
    //region GENERIC
    public static final Item ELVEN_ARMING_COAT = registerDyeableArmorPiece("elven_arming_coat",
            (settings) -> new CustomChestplateItem(ArmorMaterialsME.FABRIC_T1, settings), new Item.Settings()
                    .component(DataComponentTypes.DYED_COLOR, new DyedColorComponent(4805220)));

    public static final Item ELVEN_ARMING_SKIRT = registerDyeableArmorPiece("elven_arming_skirt",
            (settings) -> new CustomLeggingsItem(ArmorMaterialsME.FABRIC_T1, settings), new Item.Settings()
                    .component(DataComponentTypes.DYED_COLOR, new DyedColorComponent(4805220)));
    public static final Item ELVEN_OPEN_ARMING_SKIRT = registerDyeableArmorPiece("elven_open_arming_skirt",
            (settings) -> new CustomLeggingsItem(ArmorMaterialsME.FABRIC_T1, settings), new Item.Settings()
                    .component(DataComponentTypes.DYED_COLOR, new DyedColorComponent(4805220)));

    public static final Item ELVEN_BOOTS = registerArmorPiece("elven_boots",
            (settings) -> new CustomBootsItem(ArmorMaterialsME.LEATHER_T2, settings), new Item.Settings());

    public static final Item ELVEN_MAIL_COIF = registerArmorPiece("elven_mail_coif",
            (settings) -> new CustomHelmetItem(ArmorMaterialsME.IRON_T3, settings), new Item.Settings());

    public static final Item ELVEN_MAIL_HAUBERK = registerArmorPiece("elven_mail_hauberk",
            (settings) -> new CustomChestplateItem(ArmorMaterialsME.IRON_T3, settings), new Item.Settings());

    public static final Item ELVEN_MAIL_SKIRT = registerArmorPiece("elven_mail_skirt",
            (settings) -> new CustomLeggingsItem(ArmorMaterialsME.IRON_T3, settings), new Item.Settings());

    public static final Item BRONZED_ELVEN_MAIL_COIF = registerArmorPiece("bronzed_elven_mail_coif",
            (settings) -> new CustomHelmetItem(ArmorMaterialsME.BRONZE_T2, settings), new Item.Settings());

    public static final Item BRONZED_ELVEN_MAIL_HAUBERK = registerArmorPiece("bronzed_elven_mail_hauberk",
            (settings) -> new CustomChestplateItem(ArmorMaterialsME.BRONZE_T2, settings), new Item.Settings());

    public static final Item BRONZED_ELVEN_MAIL_SKIRT = registerArmorPiece("bronzed_elven_mail_skirt",
            (settings) -> new CustomLeggingsItem(ArmorMaterialsME.BRONZE_T2, settings), new Item.Settings());

    public static final Item MANTLE_OF_YAVANNA = registerDyeableBackAttachment("mantle_of_yavanna",
            (settings) -> new MantleOfYavannaItem(settings, ArmorMaterialsME.FABRIC_T0), new Item.Settings().rarity(Rarity.EPIC)
                    .component(DataComponentTypesME.BACK_ATTACHMENT_DATA, BackAttachmentDataComponent.newBackAttachment(BackAttachmentsME.MANTLE_OF_YAVANNA))
                    .component(DataComponentTypes.DYED_COLOR, new DyedColorComponent(0x375729)));
    //endregion

    //region LOTHLORIEN
    public static final Item LORIEN_DIADEM = registerCustomModelArmorPiece("lorien_diadem",
            (settings) -> new CustomHelmetItem(ArmorMaterialsME.BRONZE_T1, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.LOTHLORIEN.getValue())));

    public static final Item LORIEN_LEATHER_HELMET = registerCustomModelArmorPiece("lorien_leather_helmet",
            (settings) -> new CustomHelmetItem(ArmorMaterialsME.LEATHER_T2, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.LOTHLORIEN.getValue())));

    public static final Item LORIEN_ARMING_COAT = registerArmorPiece("lorien_arming_coat",
            (settings) -> new CustomChestplateItem(ArmorMaterialsME.LEATHER_T2, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.LOTHLORIEN.getValue())));

    public static final Item LORIEN_ARMING_SKIRT = registerArmorPiece("lorien_arming_skirt",
            (settings) -> new CustomLeggingsItem(ArmorMaterialsME.LEATHER_T2, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.LOTHLORIEN.getValue())));

    public static final Item LORIEN_MAIL_COIF_DIADEM = registerCustomModelArmorPiece("lorien_mail_coif_diadem",
            (settings) -> new CustomHelmetItem(ArmorMaterialsME.IRON_T3, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.LOTHLORIEN.getValue())));
    public static final Item LORIEN_SHORT_MAIL_COIF_DIADEM = registerCustomModelArmorPiece("lorien_short_mail_coif_diadem",
            (settings) -> new CustomHelmetItem(ArmorMaterialsME.IRON_T3, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.LOTHLORIEN.getValue())));

    public static final Item LORIEN_MAIL_HAUBERK = registerArmorPiece("lorien_mail_hauberk",
            (settings) -> new CustomChestplateItem(ArmorMaterialsME.IRON_T3, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.LOTHLORIEN.getValue())));
    public static final Item LORIEN_MARCHWARDEN_MAIL_HAUBERK = registerArmorPiece("lorien_marchwarden_mail_hauberk",
            (settings) -> new CustomChestplateItem(ArmorMaterialsME.IRON_T3, settings), new Item.Settings()
                    .component(DataComponentTypesME.BACK_ATTACHMENT_DATA, BackAttachmentDataComponent.newBackAttachment(BackAttachmentsME.LORIEN_MARCHWARDEN_CAPE))
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.LOTHLORIEN.getValue())));

    public static final Item LORIEN_SOLDIER_HELMET = registerCustomModelArmorPiece("lorien_soldier_helmet",
            (settings) -> new CustomHelmetItem(ArmorMaterialsME.ELVEN_STEEL_T4, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.LOTHLORIEN.getValue())));

    public static final Item LORIEN_SOLDIER_MAIL_HAUBERK = registerArmorPiece("lorien_soldier_mail_hauberk",
            (settings) -> new CustomChestplateItem(ArmorMaterialsME.ELVEN_STEEL_T4, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.LOTHLORIEN.getValue())));
    public static final Item LORIEN_SOLDIER_SCALE_HAUBERK = registerArmorPiece("lorien_soldier_scale_hauberk",
            (settings) -> new CustomChestplateItem(ArmorMaterialsME.ELVEN_STEEL_T4, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.LOTHLORIEN.getValue())));

    public static final Item LORIEN_SCALE_COAT = registerArmorPiece("lorien_scale_coat",
            (settings) -> new CustomLeggingsItem(ArmorMaterialsME.ELVEN_STEEL_T4, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.LOTHLORIEN.getValue())));

    public static final Item GALADHRIM_HELMET = registerCustomModelArmorPiece("galadhrim_helmet",
            (settings) -> new CustomHelmetItem(ArmorMaterialsME.ELVEN_STEEL_T5, settings), new Item.Settings()
                    .component(DataComponentTypesME.HELMET_ATTACHMENT_DATA, HelmetAttachmentDataComponent.newHelmetAttachment(HelmetAttachmentsME.GALADHRIM_HOOD))
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.LOTHLORIEN.getValue())));
    public static final Item GALADHRIM_CHESTPLATE = registerArmorPiece("galadhrim_chestplate",
            (settings) -> new CustomChestplateItem(ArmorMaterialsME.ELVEN_STEEL_T5, settings), new Item.Settings()
                    .component(DataComponentTypesME.BACK_ATTACHMENT_DATA, BackAttachmentDataComponent.newBackAttachment(BackAttachmentsME.GALADHRIM_CAPE))
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.LOTHLORIEN.getValue())));
    public static final Item GALADHRIM_LEGGINGS = registerArmorPiece("galadhrim_leggings",
            (settings) -> new CustomLeggingsItem(ArmorMaterialsME.ELVEN_STEEL_T5, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.LOTHLORIEN.getValue())));
    public static final Item GALADHRIM_BOOTS = registerArmorPiece("galadhrim_boots",
            (settings) -> new CustomBootsItem(ArmorMaterialsME.ELVEN_STEEL_T5, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.LOTHLORIEN.getValue())));

    public static final Item EGLADIL_SENTINEL_HELMET = registerDyeableCustomModelArmorPiece("egladil_sentinel_helmet",
            (settings) -> new CustomHelmetItem(ArmorMaterialsME.ELVEN_STEEL_T5, settings), new Item.Settings()
                    .component(DataComponentTypes.DYED_COLOR, new DyedColorComponent(0x463463))
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.LOTHLORIEN.getValue())));
    public static final Item EGLADIL_SENTINEL_CHESTPLATE = registerDyeableArmorPiece("egladil_sentinel_chestplate",
            (settings) -> new CustomChestplateItem(ArmorMaterialsME.ELVEN_STEEL_T5, settings), new Item.Settings()
                    .component(DataComponentTypes.DYED_COLOR, new DyedColorComponent(0x6171AE))
                    .component(DataComponentTypesME.BACK_ATTACHMENT_DATA, BackAttachmentDataComponent.newBackAttachment(BackAttachmentsME.EGLADIL_SENTINEL_CAPE))
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.LOTHLORIEN.getValue())));
    public static final Item EGLADIL_SENTINEL_LEGGINGS = registerDyeableArmorPiece("egladil_sentinel_leggings",
            (settings) -> new CustomLeggingsItem(ArmorMaterialsME.ELVEN_STEEL_T5, settings), new Item.Settings()
                    .component(DataComponentTypes.DYED_COLOR, new DyedColorComponent(0x463463))
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.LOTHLORIEN.getValue())));
    public static final Item EGLADIL_SENTINEL_BOOTS = registerArmorPiece("egladil_sentinel_boots",
            (settings) -> new CustomBootsItem(ArmorMaterialsME.ELVEN_STEEL_T5, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.LOTHLORIEN.getValue())));

    public static final Item EGLADIL_COMMANDER_HELMET = registerCustomModelArmorPiece("egladil_commander_helmet",
            (settings) -> new CustomHelmetItem(ArmorMaterialsME.ELVEN_STEEL_T5, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.LOTHLORIEN.getValue())));

    public static final Item GALADHRIM_LORD_HELMET = registerCustomModelArmorPiece("galadhrim_lord_helmet",
            (settings) -> new CustomHelmetItem(ArmorMaterialsME.ELVEN_STEEL_T5, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.LOTHLORIEN.getValue())));
    public static final Item GALADHRIM_LORD_CHESTPLATE = registerArmorPiece("galadhrim_lord_chestplate",
            (settings) -> new CustomChestplateItem(ArmorMaterialsME.ELVEN_STEEL_T5, settings), new Item.Settings()
                    .component(DataComponentTypesME.BACK_ATTACHMENT_DATA, BackAttachmentDataComponent.newBackAttachment(BackAttachmentsME.GALADHRIM_LORD_SURCOAT))
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.LOTHLORIEN.getValue())));
    public static final Item GALADHRIM_LORD_LEGGINGS = registerArmorPiece("galadhrim_lord_leggings",
            (settings) -> new CustomLeggingsItem(ArmorMaterialsME.ELVEN_STEEL_T5, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.LOTHLORIEN.getValue())));
    public static final Item GALADHRIM_LORD_BOOTS = registerArmorPiece("galadhrim_lord_boots",
            (settings) -> new CustomBootsItem(ArmorMaterialsME.ELVEN_STEEL_T5, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.LOTHLORIEN.getValue())));

    public static final Item LORIEN_MARCHWARDEN_HOOD = registerHelmetAttachment("lorien_marchwarden_hood",
            (settings) -> new HelmetAttachmentItem(settings, ArmorMaterialsME.FABRIC_T0), new Item.Settings()
                    .component(DataComponentTypesME.HELMET_ATTACHMENT_DATA, HelmetAttachmentDataComponent.newHelmetAttachment(HelmetAttachmentsME.LORIEN_MARCHWARDEN_HOOD))
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.LOTHLORIEN.getValue())));
    public static final Item GALADHRIM_HOOD = registerHelmetAttachment("galadhrim_hood",
            (settings) -> new HelmetAttachmentItem(settings, ArmorMaterialsME.FABRIC_T0), new Item.Settings()
                    .component(DataComponentTypesME.HELMET_ATTACHMENT_DATA, HelmetAttachmentDataComponent.newHelmetAttachment(HelmetAttachmentsME.GALADHRIM_HOOD))
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.LOTHLORIEN.getValue())));

    public static final Item LORIEN_MARCHWARDEN_CAPE = registerBackAttachment("lorien_marchwarden_cape",
            (settings) -> new BackAttachmentItem(settings, ArmorMaterialsME.FABRIC_T0), new Item.Settings()
                    .component(DataComponentTypesME.BACK_ATTACHMENT_DATA, BackAttachmentDataComponent.newBackAttachment(BackAttachmentsME.LORIEN_MARCHWARDEN_CAPE))
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.LOTHLORIEN.getValue())));
    public static final Item GALADHRIM_CAPE = registerBackAttachment("galadhrim_cape",
            (settings) -> new BackAttachmentItem(settings, ArmorMaterialsME.FABRIC_T0), new Item.Settings()
                    .component(DataComponentTypesME.BACK_ATTACHMENT_DATA, BackAttachmentDataComponent.newBackAttachment(BackAttachmentsME.GALADHRIM_CAPE))
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.LOTHLORIEN.getValue())));
    public static final Item EGLADIL_SENTINEL_CAPE = registerBackAttachment("egladil_sentinel_cape",
            (settings) -> new BackAttachmentItem(settings, ArmorMaterialsME.FABRIC_T0), new Item.Settings()
                    .component(DataComponentTypesME.BACK_ATTACHMENT_DATA, BackAttachmentDataComponent.newBackAttachment(BackAttachmentsME.EGLADIL_SENTINEL_CAPE))
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.LOTHLORIEN.getValue())));
    public static final Item GALADHRIM_LORD_CLOAK = registerBackAttachment("galadhrim_lord_cloak",
            (settings) -> new BackAttachmentItem(settings, ArmorMaterialsME.FABRIC_T0), new Item.Settings()
                    .component(DataComponentTypesME.BACK_ATTACHMENT_DATA, BackAttachmentDataComponent.newBackAttachment(BackAttachmentsME.GALADHRIM_LORD_CLOAK))
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.LOTHLORIEN.getValue())));
    public static final Item GALADHRIM_LORD_SURCOAT = registerBackAttachment("galadhrim_lord_surcoat",
            (settings) -> new BackAttachmentItem(settings, ArmorMaterialsME.FABRIC_T0), new Item.Settings()
                    .component(DataComponentTypesME.BACK_ATTACHMENT_DATA, BackAttachmentDataComponent.newBackAttachment(BackAttachmentsME.GALADHRIM_LORD_SURCOAT))
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.LOTHLORIEN.getValue())));

    public static final Item LORIEN_HORSE_ARMOR = registerGeneratedItem("lorien_horse_armor",
            Item::new, new Item.Settings().horseArmor(ArmorMaterialsME.LORIEN_HORSE_ARMOR.material()).maxCount(1)
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.LOTHLORIEN.getValue())));
    //endregion

    //region WOODLAND_REALM
    public static final Item WOODLAND_REALM_ARMING_COAT = registerDyeableArmorPiece("woodland_realm_arming_coat",
            (settings) -> new CustomChestplateItem(ArmorMaterialsME.FABRIC_T1, settings), new Item.Settings()
                    .component(DataComponentTypes.DYED_COLOR, new DyedColorComponent(0x4a5110)));

    public static final Item WOODLAND_REALM_SILVER_TRIMMED_RANGER_HELMET = registerCustomModelArmorPiece("woodland_realm_silver_trimmed_ranger_helmet",
            (settings) -> new CustomHelmetItem(ArmorMaterialsME.LEATHER_T2, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.LOTHLORIEN.getValue())));

    public static final Item WOODLAND_REALM_ROYAL_GUARD_HELMET = registerDyeableCustomModelArmorPiece("woodland_realm_royal_guard_helmet",
            (settings) -> new CustomHelmetItem(ArmorMaterialsME.ELVEN_STEEL_T4, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.LOTHLORIEN.getValue()))
                    .component(DataComponentTypes.DYED_COLOR, new DyedColorComponent(0x532e29)));
    public static final Item WOODLAND_REALM_ROYAL_GUARD_HAUBERK = registerArmorPiece("woodland_realm_royal_guard_hauberk",
            (settings) -> new CustomChestplateItem(ArmorMaterialsME.ELVEN_STEEL_T4, settings), new Item.Settings()
                    .component(DataComponentTypesME.BACK_ATTACHMENT_DATA, BackAttachmentDataComponent.newBackAttachment(BackAttachmentsME.WOODLAND_REALM_ROYAL_GUARD_CAPE))
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.LOTHLORIEN.getValue())));
    public static final Item WOODLAND_REALM_ROYAL_GUARD_SCALE_SKIRT = registerArmorPiece("woodland_realm_royal_guard_scale_skirt",
            (settings) -> new CustomLeggingsItem(ArmorMaterialsME.ELVEN_STEEL_T4, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.LOTHLORIEN.getValue())));

    public static final Item WOODLAND_REALM_SOLDIER_HELMET = registerCustomModelArmorPiece("woodland_realm_soldier_helmet",
            (settings) -> new CustomHelmetItem(ArmorMaterialsME.ELVEN_STEEL_T5, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.LOTHLORIEN.getValue())));
    public static final Item WOODLAND_REALM_SOLDIER_CHESTPLATE = registerArmorPiece("woodland_realm_soldier_chestplate",
            (settings) -> new CustomChestplateItem(ArmorMaterialsME.ELVEN_STEEL_T5, settings), new Item.Settings()
                    .component(DataComponentTypesME.BACK_ATTACHMENT_DATA, BackAttachmentDataComponent.newBackAttachment(BackAttachmentsME.WOODLAND_REALM_SOLDIER_CAPE))
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.LOTHLORIEN.getValue())));
    public static final Item WOODLAND_REALM_SOLDIER_LEGGINGS = registerArmorPiece("woodland_realm_soldier_leggings",
            (settings) -> new CustomLeggingsItem(ArmorMaterialsME.ELVEN_STEEL_T5, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.LOTHLORIEN.getValue())));
    public static final Item WOODLAND_REALM_SOLDIER_BOOTS = registerArmorPiece("woodland_realm_soldier_boots",
            (settings) -> new CustomBootsItem(ArmorMaterialsME.ELVEN_STEEL_T5, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.LOTHLORIEN.getValue())));

    public static final Item WARDEN_OF_THE_GLADE_HELMET = registerDyeableCustomModelArmorPiece("warden_of_the_glade_helmet",
            (settings) -> new CustomHelmetItem(ArmorMaterialsME.ELVEN_STEEL_T5, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.LOTHLORIEN.getValue()))
                    .component(DataComponentTypes.DYED_COLOR, new DyedColorComponent(0x5c5445)));
    public static final Item WARDEN_OF_THE_GLADE_CHESTPLATE = registerArmorPiece("warden_of_the_glade_chestplate",
            (settings) -> new CustomChestplateItem(ArmorMaterialsME.ELVEN_STEEL_T5, settings), new Item.Settings()
                    .component(DataComponentTypesME.BACK_ATTACHMENT_DATA, BackAttachmentDataComponent.newBackAttachment(BackAttachmentsME.WARDEN_OF_THE_GLADE_CAPE))
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.LOTHLORIEN.getValue())));
    public static final Item WARDEN_OF_THE_GLADE_LEGGINGS = registerArmorPiece("warden_of_the_glade_leggings",
            (settings) -> new CustomLeggingsItem(ArmorMaterialsME.ELVEN_STEEL_T5, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.LOTHLORIEN.getValue())));
    public static final Item WARDEN_OF_THE_GLADE_BOOTS = registerArmorPiece("warden_of_the_glade_boots",
            (settings) -> new CustomBootsItem(ArmorMaterialsME.ELVEN_STEEL_T5, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.LOTHLORIEN.getValue())));

    public static final Item WARRIOR_OF_THE_NIGHTSHADE_HELMET = registerCustomModelArmorPiece("warrior_of_nightshade_helmet",
            (settings) -> new CustomHelmetItem(ArmorMaterialsME.ELVEN_STEEL_T5, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.LOTHLORIEN.getValue())));
    public static final Item WARRIOR_OF_THE_NIGHTSHADE_CHESTPLATE = registerArmorPiece("warrior_of_nightshade_chestplate",
            (settings) -> new CustomChestplateItem(ArmorMaterialsME.ELVEN_STEEL_T5, settings), new Item.Settings()
                    .component(DataComponentTypesME.BACK_ATTACHMENT_DATA, BackAttachmentDataComponent.newBackAttachment(BackAttachmentsME.WARRIOR_OF_NIGHTSHADE_CAPE))
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.LOTHLORIEN.getValue())));
    public static final Item WARRIOR_OF_THE_NIGHTSHADE_LEGGINGS = registerArmorPiece("warrior_of_nightshade_leggings",
            (settings) -> new CustomLeggingsItem(ArmorMaterialsME.ELVEN_STEEL_T5, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.LOTHLORIEN.getValue())));
    public static final Item WARRIOR_OF_THE_NIGHTSHADE_BOOTS = registerArmorPiece("warrior_of_nightshade_boots",
            (settings) -> new CustomBootsItem(ArmorMaterialsME.ELVEN_STEEL_T5, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.LOTHLORIEN.getValue())));

    public static final Item ERYN_GALEN_WATCHWARDEN_HELMET = registerCustomModelArmorPiece("eryn_galen_watchwarden_helmet",
            (settings) -> new CustomHelmetItem(ArmorMaterialsME.ELVEN_STEEL_T5, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.LOTHLORIEN.getValue())));
    public static final Item ERYN_GALEN_WATCHWARDEN_CHESTPLATE = registerArmorPiece("eryn_galen_watchwarden_chestplate",
            (settings) -> new CustomChestplateItem(ArmorMaterialsME.ELVEN_STEEL_T5, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.LOTHLORIEN.getValue())));
    public static final Item ERYN_GALEN_WATCHWARDEN_LEGGINGS = registerArmorPiece("eryn_galen_watchwarden_leggings",
            (settings) -> new CustomLeggingsItem(ArmorMaterialsME.ELVEN_STEEL_T5, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.LOTHLORIEN.getValue())));
    public static final Item ERYN_GALEN_WATCHWARDEN_BOOTS = registerArmorPiece("eryn_galen_watchwarden_boots",
            (settings) -> new CustomBootsItem(ArmorMaterialsME.ELVEN_STEEL_T5, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.LOTHLORIEN.getValue())));

    public static final Item OXIDISED_ERYN_GALEN_WATCHWARDEN_HELMET = registerCustomModelArmorPiece("oxidised_eryn_galen_watchwarden_helmet",
            (settings) -> new CustomHelmetItem(ArmorMaterialsME.ELVEN_STEEL_T5, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.LOTHLORIEN.getValue())));
    public static final Item OXIDISED_ERYN_GALEN_WATCHWARDEN_CHESTPLATE = registerArmorPiece("oxidised_eryn_galen_watchwarden_chestplate",
            (settings) -> new CustomChestplateItem(ArmorMaterialsME.ELVEN_STEEL_T5, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.LOTHLORIEN.getValue())));
    public static final Item OXIDISED_ERYN_GALEN_WATCHWARDEN_LEGGINGS = registerArmorPiece("oxidised_eryn_galen_watchwarden_leggings",
            (settings) -> new CustomLeggingsItem(ArmorMaterialsME.ELVEN_STEEL_T5, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.LOTHLORIEN.getValue())));
    public static final Item OXIDISED_ERYN_GALEN_WATCHWARDEN_BOOTS = registerArmorPiece("oxidised_eryn_galen_watchwarden_boots",
            (settings) -> new CustomBootsItem(ArmorMaterialsME.ELVEN_STEEL_T5, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.LOTHLORIEN.getValue())));

    public static final Item WOODLAND_REALM_COMMANDER_HELMET = registerCustomModelArmorPiece("woodland_realm_commander_helmet",
            (settings) -> new CustomHelmetItem(ArmorMaterialsME.ELVEN_STEEL_T5, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.LOTHLORIEN.getValue())));
    public static final Item WOODLAND_REALM_COMMANDER_CHESTPLATE = registerArmorPiece("woodland_realm_commander_chestplate",
            (settings) -> new CustomChestplateItem(ArmorMaterialsME.ELVEN_STEEL_T5, settings), new Item.Settings()
                    .component(DataComponentTypesME.BACK_ATTACHMENT_DATA, BackAttachmentDataComponent.newBackAttachment(BackAttachmentsME.WOODLAND_REALM_SOLDIER_CAPE))
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.LOTHLORIEN.getValue())));
    public static final Item WOODLAND_REALM_COMMANDER_LEGGINGS = registerArmorPiece("woodland_realm_commander_leggings",
            (settings) -> new CustomLeggingsItem(ArmorMaterialsME.ELVEN_STEEL_T5, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.LOTHLORIEN.getValue())));
    public static final Item WOODLAND_REALM_COMMANDER_BOOTS = registerArmorPiece("woodland_realm_commander_boots",
            (settings) -> new CustomBootsItem(ArmorMaterialsME.ELVEN_STEEL_T5, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.LOTHLORIEN.getValue())));

    public static final Item SILVAN_LORD_HELMET = registerCustomModelArmorPiece("silvan_lord_helmet",
            (settings) -> new CustomHelmetItem(ArmorMaterialsME.ELVEN_STEEL_T5, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.LOTHLORIEN.getValue())));
    public static final Item SILVAN_LORD_CHESTPLATE = registerArmorPiece("silvan_lord_chestplate",
            (settings) -> new CustomChestplateItem(ArmorMaterialsME.ELVEN_STEEL_T5, settings), new Item.Settings()
                    .component(DataComponentTypesME.BACK_ATTACHMENT_DATA, BackAttachmentDataComponent.newBackAttachment(BackAttachmentsME.SILVAN_LORD_CAPE))
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.LOTHLORIEN.getValue())));
    public static final Item SILVAN_LORD_LEGGINGS = registerArmorPiece("silvan_lord_leggings",
            (settings) -> new CustomLeggingsItem(ArmorMaterialsME.ELVEN_STEEL_T5, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.LOTHLORIEN.getValue())));
    public static final Item SILVAN_LORD_BOOTS = registerArmorPiece("silvan_lord_boots",
            (settings) -> new CustomBootsItem(ArmorMaterialsME.ELVEN_STEEL_T5, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.LOTHLORIEN.getValue())));

    public static final Item WOODLAND_REALM_SOLDIER_CAPE = registerBackAttachment("woodland_realm_soldier_cape",
            (settings) -> new BackAttachmentItem(settings, ArmorMaterialsME.FABRIC_T0), new Item.Settings()
                    .component(DataComponentTypesME.BACK_ATTACHMENT_DATA, BackAttachmentDataComponent.newBackAttachment(BackAttachmentsME.WOODLAND_REALM_SOLDIER_CAPE))
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.LOTHLORIEN.getValue())));
    public static final Item WOODLAND_REALM_ROYAL_GUARD_CAPE = registerBackAttachment("woodland_realm_royal_guard_cape",
            (settings) -> new BackAttachmentItem(settings, ArmorMaterialsME.FABRIC_T0), new Item.Settings()
                    .component(DataComponentTypesME.BACK_ATTACHMENT_DATA, BackAttachmentDataComponent.newBackAttachment(BackAttachmentsME.WOODLAND_REALM_ROYAL_GUARD_CAPE))
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.LOTHLORIEN.getValue())));
    public static final Item LORD_OF_THE_GREENWOOD_CAPE = registerBackAttachment("lord_of_the_greenwood_cape",
            (settings) -> new BackAttachmentItem(settings, ArmorMaterialsME.FABRIC_T0), new Item.Settings()
                    .component(DataComponentTypesME.BACK_ATTACHMENT_DATA, BackAttachmentDataComponent.newBackAttachment(BackAttachmentsME.LORD_OF_THE_GREENWOOD_CAPE))
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.LOTHLORIEN.getValue())));
    public static final Item WARDEN_OF_THE_GLADE_CAPE = registerBackAttachment("warden_of_the_glade_cape",
            (settings) -> new BackAttachmentItem(settings, ArmorMaterialsME.FABRIC_T0), new Item.Settings()
                    .component(DataComponentTypesME.BACK_ATTACHMENT_DATA, BackAttachmentDataComponent.newBackAttachment(BackAttachmentsME.WARDEN_OF_THE_GLADE_CAPE))
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.LOTHLORIEN.getValue())));
    public static final Item WARRIOR_OF_NIGHTSHADE_CAPE = registerBackAttachment("warrior_of_nightshade_cape",
            (settings) -> new BackAttachmentItem(settings, ArmorMaterialsME.FABRIC_T0), new Item.Settings()
                    .component(DataComponentTypesME.BACK_ATTACHMENT_DATA, BackAttachmentDataComponent.newBackAttachment(BackAttachmentsME.WARRIOR_OF_NIGHTSHADE_CAPE))
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.LOTHLORIEN.getValue())));
    public static final Item SILVAN_LORD_CAPE = registerBackAttachment("silvan_lord_cape",
            (settings) -> new BackAttachmentItem(settings, ArmorMaterialsME.FABRIC_T0), new Item.Settings()
                    .component(DataComponentTypesME.BACK_ATTACHMENT_DATA, BackAttachmentDataComponent.newBackAttachment(BackAttachmentsME.SILVAN_LORD_CAPE))
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.LOTHLORIEN.getValue())));

    public static final Item WOODLAND_REALM_CROWN = registerCustomModelArmorPiece("woodland_realm_crown",
            (settings) -> new WoodlandRealmCrownItem(ArmorMaterialsME.STRAW_T1, settings), new Item.Settings().rarity(Rarity.EPIC)
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.LOTHLORIEN.getValue())));
    //endregion

    //endregion

    //region ORCS

    //region GENERIC
    public static final Item ORCISH_LEATHER_STRAP = registerDyeableArmorPiece("orcish_leather_strap",
            (settings) -> new CustomChestplateItem(ArmorMaterialsME.LEATHER_T1, settings), new Item.Settings()
                    .component(DataComponentTypes.DYED_COLOR, new DyedColorComponent(4865076)));

    public static final Item ORCISH_SANDALS = registerDyeableArmorPiece("orcish_sandals",
            (settings) -> new CustomBootsItem(ArmorMaterialsME.FABRIC_T1, settings), new Item.Settings()
                    .component(DataComponentTypes.DYED_COLOR, new DyedColorComponent(4865076)));

    public static final Item RUSTED_ORCISH_MAIL_COIF = registerArmorPiece("rusted_orcish_mail_coif",
            (settings) -> new CustomHelmetItem(ArmorMaterialsME.IRON_T2, settings), new Item.Settings());

    public static final Item ORCISH_LEATHER_CHESTPLATE = registerDyeableArmorPiece("orcish_leather_chestplate",
            (settings) -> new CustomChestplateItem(ArmorMaterialsME.LEATHER_T2, settings), new Item.Settings()
                    .component(DataComponentTypes.DYED_COLOR, new DyedColorComponent(4865076)));
    public static final Item ORCISH_LEATHER_SCALE_VEST = registerDyeableArmorPiece("orcish_leather_scale_vest",
            (settings) -> new CustomChestplateItem(ArmorMaterialsME.LEATHER_T2, settings), new Item.Settings()
                    .component(DataComponentTypes.DYED_COLOR, new DyedColorComponent(4865076)));
    public static final Item RUSTED_ORCISH_MAIL_HAUBERK = registerArmorPiece("rusted_orcish_mail_hauberk",
            (settings) -> new CustomChestplateItem(ArmorMaterialsME.IRON_T2, settings), new Item.Settings());
    public static final Item RUSTED_ORCISH_MAIL_SHIRT = registerArmorPiece("rusted_orcish_mail_shirt",
            (settings) -> new CustomChestplateItem(ArmorMaterialsME.IRON_T2, settings), new Item.Settings());

    public static final Item ORCISH_LEATHER_SKIRT = registerDyeableArmorPiece("orcish_leather_skirt",
            (settings) -> new CustomLeggingsItem(ArmorMaterialsME.LEATHER_T2, settings), new Item.Settings()
                    .component(DataComponentTypes.DYED_COLOR, new DyedColorComponent(4865076)));
    public static final Item ORCISH_LEG_BRACER = registerDyeableArmorPiece("orcish_leg_bracer",
            (settings) -> new CustomLeggingsItem(ArmorMaterialsME.LEATHER_T2, settings), new Item.Settings()
                    .component(DataComponentTypes.DYED_COLOR, new DyedColorComponent(4865076)));
    public static final Item RUSTED_ORCISH_MAIL_COAT = registerArmorPiece("rusted_orcish_mail_coat",
            (settings) -> new CustomLeggingsItem(ArmorMaterialsME.IRON_T2, settings), new Item.Settings());
    public static final Item RUSTED_ORCISH_REINFORCED_LEATHER_SKIRT = registerDyeableArmorPiece("rusted_orcish_reinforced_leather_skirt",
            (settings) -> new CustomLeggingsItem(ArmorMaterialsME.IRON_T2, settings), new Item.Settings()
                    .component(DataComponentTypes.DYED_COLOR, new DyedColorComponent(4865076)));
    public static final Item RUSTED_ORCISH_REINFORCED_STRIP_LEATHER_SKIRT = registerDyeableArmorPiece("rusted_orcish_reinforced_strip_leather_skirt",
            (settings) -> new CustomLeggingsItem(ArmorMaterialsME.IRON_T2, settings), new Item.Settings()
                    .component(DataComponentTypes.DYED_COLOR, new DyedColorComponent(4865076)));
    public static final Item ORCISH_STRIP_LEATHER_SKIRT = registerDyeableArmorPiece("orcish_strip_leather_skirt",
            (settings) -> new CustomLeggingsItem(ArmorMaterialsME.LEATHER_T2, settings), new Item.Settings()
                    .component(DataComponentTypes.DYED_COLOR, new DyedColorComponent(4865076)));

    public static final Item ORCISH_BLACK_FUR_BOOTS = registerDyeableArmorPiece("orcish_black_fur_boots",
            (settings) -> new CustomBootsItem(ArmorMaterialsME.LEATHER_T2, settings), new Item.Settings()
                    .component(DataComponentTypes.DYED_COLOR, new DyedColorComponent(4865076)));
    public static final Item ORCISH_BROWN_FUR_BOOTS = registerDyeableArmorPiece("orcish_brown_fur_boots",
            (settings) -> new CustomBootsItem(ArmorMaterialsME.LEATHER_T2, settings), new Item.Settings()
                    .component(DataComponentTypes.DYED_COLOR, new DyedColorComponent(4865076)));
    public static final Item ORCISH_GRAY_FUR_BOOTS = registerDyeableArmorPiece("orcish_gray_fur_boots",
            (settings) -> new CustomBootsItem(ArmorMaterialsME.LEATHER_T2, settings), new Item.Settings()
                    .component(DataComponentTypes.DYED_COLOR, new DyedColorComponent(4865076)));
    public static final Item ORCISH_TAN_FUR_BOOTS = registerDyeableArmorPiece("orcish_tan_fur_boots",
            (settings) -> new CustomBootsItem(ArmorMaterialsME.LEATHER_T2, settings), new Item.Settings()
                    .component(DataComponentTypes.DYED_COLOR, new DyedColorComponent(4865076)));
    public static final Item ORCISH_WHITE_FUR_BOOTS = registerDyeableArmorPiece("orcish_white_fur_boots",
            (settings) -> new CustomBootsItem(ArmorMaterialsME.LEATHER_T2, settings), new Item.Settings()
                    .component(DataComponentTypes.DYED_COLOR, new DyedColorComponent(4865076)));

    public static final Item ORCISH_BRACED_HELMET = registerCustomModelArmorPiece("orcish_braced_helmet",
            (settings) -> new CustomHelmetItem(ArmorMaterialsME.IRON_T3, settings), new Item.Settings());
    public static final Item ORCISH_HELMET = registerArmorPiece("orcish_helmet",
            (settings) -> new CustomHelmetItem(ArmorMaterialsME.IRON_T3, settings), new Item.Settings());
    public static final Item ORCISH_MAIL_COIF = registerArmorPiece("orcish_mail_coif",
            (settings) -> new CustomHelmetItem(ArmorMaterialsME.IRON_T3, settings), new Item.Settings());

    public static final Item ORCISH_MAIL_HAUBERK = registerArmorPiece("orcish_mail_hauberk",
            (settings) -> new CustomChestplateItem(ArmorMaterialsME.IRON_T3, settings), new Item.Settings());
    public static final Item ORCISH_MAIL_SHIRT = registerArmorPiece("orcish_mail_shirt",
            (settings) -> new CustomChestplateItem(ArmorMaterialsME.IRON_T3, settings), new Item.Settings());

    public static final Item ORCISH_MAIL_COAT = registerArmorPiece("orcish_mail_coat",
            (settings) -> new CustomLeggingsItem(ArmorMaterialsME.IRON_T3, settings), new Item.Settings());
    public static final Item ORCISH_REINFORCED_LEATHER_SKIRT = registerDyeableArmorPiece("orcish_reinforced_leather_skirt",
            (settings) -> new CustomLeggingsItem(ArmorMaterialsME.IRON_T3, settings), new Item.Settings()
                    .component(DataComponentTypes.DYED_COLOR, new DyedColorComponent(4865076)));
    public static final Item ORCISH_REINFORCED_STRIP_LEATHER_SKIRT = registerDyeableArmorPiece("orcish_reinforced_strip_leather_skirt",
            (settings) -> new CustomLeggingsItem(ArmorMaterialsME.IRON_T3, settings), new Item.Settings()
                    .component(DataComponentTypes.DYED_COLOR, new DyedColorComponent(4865076)));

    public static final Item ORCISH_SALLET = registerCustomModelArmorPiece("orcish_sallet",
            (settings) -> new CustomHelmetItem(ArmorMaterialsME.BURZUM_STEEL_T4, settings), new Item.Settings());

    public static final Item ORCISH_BELLY_PLATE_CHESTPLATE = registerDyeableArmorPiece("orcish_belly_plate_chestplate",
            (settings) -> new CustomChestplateItem(ArmorMaterialsME.BURZUM_STEEL_T4, settings), new Item.Settings()
                    .component(DataComponentTypes.DYED_COLOR, new DyedColorComponent(4865076)));
    public static final Item ORCISH_SCALE_VEST = registerDyeableArmorPiece("orcish_scale_vest",
            (settings) -> new CustomChestplateItem(ArmorMaterialsME.BURZUM_STEEL_T4, settings), new Item.Settings()
                    .component(DataComponentTypes.DYED_COLOR, new DyedColorComponent(4865076)));

    public static final Item ORCISH_REINFORCED_LEG_BRACER = registerArmorPiece("orcish_reinforced_leg_bracer",
            (settings) -> new CustomLeggingsItem(ArmorMaterialsME.BURZUM_STEEL_T4, settings), new Item.Settings());

    public static final Item ORCISH_BRACED_SANDALS = registerDyeableArmorPiece("orcish_braced_sandals",
            (settings) -> new CustomBootsItem(ArmorMaterialsME.BURZUM_STEEL_T4, settings), new Item.Settings()
                    .component(DataComponentTypes.DYED_COLOR, new DyedColorComponent(4865076)));

    public static final Item ORCISH_CAPE = registerBackAttachment("orcish_cape",
            (settings) -> new BackAttachmentItem(settings, ArmorMaterialsME.FABRIC_T0), new Item.Settings()
                    .component(DataComponentTypesME.BACK_ATTACHMENT_DATA, BackAttachmentDataComponent.newBackAttachment(BackAttachmentsME.ORCISH_CAPE)));
    public static final Item ORCISH_LONG_CAPE = registerBackAttachment("orcish_long_cape",
            (settings) -> new BackAttachmentItem(settings, ArmorMaterialsME.FABRIC_T0), new Item.Settings()
                    .component(DataComponentTypesME.BACK_ATTACHMENT_DATA, BackAttachmentDataComponent.newBackAttachment(BackAttachmentsME.ORCISH_LONG_CAPE)));
    public static final Item ORCISH_SHOULDERS = registerDyeableBackAttachment("orcish_shoulders",
            (settings) -> new BackAttachmentItem(settings, ArmorMaterialsME.FABRIC_T0), new Item.Settings()
                    .component(DataComponentTypesME.BACK_ATTACHMENT_DATA, BackAttachmentDataComponent.newBackAttachment(BackAttachmentsME.ORCISH_SHOULDERS))
                    .component(DataComponentTypes.DYED_COLOR, new DyedColorComponent(5186845)));

    public static final Item ORCISH_BLACK_FUR_SURCOAT_WITH_BONE = registerBackAttachment("orcish_black_fur_surcoat_with_bone",
            (settings) -> new BackAttachmentItem(settings, ArmorMaterialsME.FUR_T0), new Item.Settings()
                    .component(DataComponentTypesME.BACK_ATTACHMENT_DATA, BackAttachmentDataComponent.newBackAttachment(BackAttachmentsME.ORCISH_BLACK_FUR_SURCOAT_WITH_BONE)));
    public static final Item ORCISH_BROWN_FUR_SURCOAT_WITH_BONE = registerBackAttachment("orcish_brown_fur_surcoat_with_bone",
            (settings) -> new BackAttachmentItem(settings, ArmorMaterialsME.FUR_T0), new Item.Settings()
                    .component(DataComponentTypesME.BACK_ATTACHMENT_DATA, BackAttachmentDataComponent.newBackAttachment(BackAttachmentsME.ORCISH_BROWN_FUR_SURCOAT_WITH_BONE)));
    public static final Item ORCISH_GRAY_FUR_SURCOAT_WITH_BONE = registerBackAttachment("orcish_gray_fur_surcoat_with_bone",
            (settings) -> new BackAttachmentItem(settings, ArmorMaterialsME.FUR_T0), new Item.Settings()
                    .component(DataComponentTypesME.BACK_ATTACHMENT_DATA, BackAttachmentDataComponent.newBackAttachment(BackAttachmentsME.ORCISH_GRAY_FUR_SURCOAT_WITH_BONE)));
    public static final Item ORCISH_TAN_FUR_SURCOAT_WITH_BONE = registerBackAttachment("orcish_tan_fur_surcoat_with_bone",
            (settings) -> new BackAttachmentItem(settings, ArmorMaterialsME.FUR_T0), new Item.Settings()
                    .component(DataComponentTypesME.BACK_ATTACHMENT_DATA, BackAttachmentDataComponent.newBackAttachment(BackAttachmentsME.ORCISH_TAN_FUR_SURCOAT_WITH_BONE)));
    public static final Item ORCISH_WHITE_FUR_SURCOAT_WITH_BONE = registerBackAttachment("orcish_white_fur_surcoat_with_bone",
            (settings) -> new BackAttachmentItem(settings, ArmorMaterialsME.FUR_T0), new Item.Settings()
                    .component(DataComponentTypesME.BACK_ATTACHMENT_DATA, BackAttachmentDataComponent.newBackAttachment(BackAttachmentsME.ORCISH_WHITE_FUR_SURCOAT_WITH_BONE)));

    public static final Item WARG_LEATHER_ARMOR = registerDyeableArmorPiece("warg_leather_armor",
            Item::new, ItemSettingsME.wargArmor(ArmorMaterialsME.WARG_LEATHER_ARMOR.material()));

    public static final Item WARG_REINFORCED_LEATHER_ARMOR = registerDyeableArmorPiece("warg_reinforced_leather_armor",
            Item::new, ItemSettingsME.wargArmor(ArmorMaterialsME.WARG_REINFORCED_LEATHER_ARMOR.material()));
    //endregion

    //region MORDOR
    public static final Item RUSTED_MORDOR_HELMET = registerArmorPiece("rusted_mordor_helmet",
            (settings) -> new CustomHelmetItem(ArmorMaterialsME.CRUDE_T2, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MORDOR.getValue())));
    public static final Item RUSTED_MORDOR_KETTLE_HAT = registerCustomModelArmorPiece("rusted_mordor_kettle_hat",
            (settings) -> new CustomHelmetItem(ArmorMaterialsME.CRUDE_T2, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MORDOR.getValue())));
    public static final Item RUSTED_MORDOR_KETTLE_HAT_WITH_COIF = registerCustomModelArmorPiece("rusted_mordor_kettle_hat_with_coif",
            (settings) -> new CustomHelmetItem(ArmorMaterialsME.CRUDE_T2, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MORDOR.getValue())));
    public static final Item RUSTED_MORDOR_NASAL_HELMET = registerArmorPiece("rusted_mordor_nasal_helmet",
            (settings) -> new CustomHelmetItem(ArmorMaterialsME.CRUDE_T2, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MORDOR.getValue())));

    public static final Item MORDOR_LEATHER_CUIRASS = registerDyeableArmorPiece("mordor_leather_cuirass",
            (settings) -> new CustomChestplateItem(ArmorMaterialsME.LEATHER_T2, settings), new Item.Settings()
                    .component(DataComponentTypes.DYED_COLOR, new DyedColorComponent(5186845))
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MORDOR.getValue())));
    public static final Item MORDOR_PAINTED_LEATHER_CUIRASS = registerDyeableArmorPiece("mordor_painted_leather_cuirass",
            (settings) -> new CustomChestplateItem(ArmorMaterialsME.LEATHER_T2, settings), new Item.Settings()
                    .component(DataComponentTypes.DYED_COLOR, new DyedColorComponent(5186845))
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MORDOR.getValue())));

    public static final Item MORDOR_HELMET = registerArmorPiece("mordor_helmet",
            (settings) -> new CustomHelmetItem(ArmorMaterialsME.CRUDE_T3, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MORDOR.getValue())));
    public static final Item MORDOR_KETTLE_HAT = registerCustomModelArmorPiece("mordor_kettle_hat",
            (settings) -> new CustomHelmetItem(ArmorMaterialsME.CRUDE_T3, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MORDOR.getValue())));
    public static final Item MORDOR_KETTLE_HAT_WITH_COIF = registerCustomModelArmorPiece("mordor_kettle_hat_with_coif",
            (settings) -> new CustomHelmetItem(ArmorMaterialsME.CRUDE_T3, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MORDOR.getValue())));
    public static final Item MORDOR_NASAL_HELMET = registerArmorPiece("mordor_nasal_helmet",
            (settings) -> new CustomHelmetItem(ArmorMaterialsME.CRUDE_T3, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MORDOR.getValue())));
    public static final Item RUSTED_MORDOR_CREST_HELMET = registerCustomModelArmorPiece("rusted_mordor_crest_helmet",
            (settings) -> new CustomHelmetItem(ArmorMaterialsME.BURZUM_STEEL_T3, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MORDOR.getValue())));
    public static final Item RUSTED_MORDOR_MANDIBLE_HELMET = registerArmorPiece("rusted_mordor_mandible_helmet",
            (settings) -> new CustomHelmetItem(ArmorMaterialsME.BURZUM_STEEL_T3, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MORDOR.getValue())));
    public static final Item RUSTED_MORDOR_SALLET = registerCustomModelArmorPiece("rusted_mordor_sallet",
            (settings) -> new CustomHelmetItem(ArmorMaterialsME.BURZUM_STEEL_T3, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MORDOR.getValue())));

    public static final Item RUSTED_MORDOR_CHESTPLATE = registerArmorPiece("rusted_mordor_chestplate",
            (settings) -> new CustomChestplateItem(ArmorMaterialsME.BURZUM_STEEL_T3, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MORDOR.getValue())));
    public static final Item RUSTED_MORDOR_CUIRASS = registerDyeableArmorPiece("rusted_mordor_cuirass",
            (settings) -> new CustomChestplateItem(ArmorMaterialsME.BURZUM_STEEL_T3, settings), new Item.Settings()
                    .component(DataComponentTypes.DYED_COLOR, new DyedColorComponent(5186845))
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MORDOR.getValue())));
    public static final Item RUSTED_MORDOR_GORGET_HAUBERK = registerArmorPiece("rusted_mordor_gorget_hauberk",
            (settings) -> new CustomChestplateItem(ArmorMaterialsME.BURZUM_STEEL_T3, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MORDOR.getValue())));
    public static final Item RUSTED_MORDOR_REINFORCED_COAT = registerDyeableArmorPiece("rusted_mordor_reinforced_coat",
            (settings) -> new CustomChestplateItem(ArmorMaterialsME.BURZUM_STEEL_T3, settings), new Item.Settings()
                    .component(DataComponentTypes.DYED_COLOR, new DyedColorComponent(5186845))
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MORDOR.getValue())));

    public static final Item RUSTED_MORDOR_PLATE_BOOTS = registerArmorPiece("rusted_mordor_plate_boots",
            (settings) -> new CustomBootsItem(ArmorMaterialsME.BURZUM_STEEL_T3, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MORDOR.getValue())));

    public static final Item MORDOR_CREST_HELMET = registerCustomModelArmorPiece("mordor_crest_helmet",
            (settings) -> new CustomHelmetItem(ArmorMaterialsME.BURZUM_STEEL_T4, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MORDOR.getValue())));
    public static final Item MORDOR_MANDIBLE_HELMET = registerArmorPiece("mordor_mandible_helmet",
            (settings) -> new CustomHelmetItem(ArmorMaterialsME.BURZUM_STEEL_T4, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MORDOR.getValue())));
    public static final Item MORDOR_SALLET = registerCustomModelArmorPiece("mordor_sallet",
            (settings) -> new CustomHelmetItem(ArmorMaterialsME.BURZUM_STEEL_T4, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MORDOR.getValue())));
    public static final Item RUSTED_MORDOR_GREAT_HELMET = registerCustomModelArmorPiece("rusted_mordor_great_helmet",
            (settings) -> new CustomHelmetItem(ArmorMaterialsME.BURZUM_STEEL_T4, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MORDOR.getValue())));
    public static final Item RUSTED_MORDOR_SNOUT_HELMET = registerCustomModelArmorPiece("rusted_mordor_snout_helmet",
            (settings) -> new CustomHelmetItem(ArmorMaterialsME.BURZUM_STEEL_T4, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MORDOR.getValue())));

    public static final Item MORDOR_CHESTPLATE = registerArmorPiece("mordor_chestplate",
            (settings) -> new CustomChestplateItem(ArmorMaterialsME.BURZUM_STEEL_T4, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MORDOR.getValue())));
    public static final Item MORDOR_CUIRASS = registerDyeableArmorPiece("mordor_cuirass",
            (settings) -> new CustomChestplateItem(ArmorMaterialsME.BURZUM_STEEL_T4, settings), new Item.Settings()
                    .component(DataComponentTypes.DYED_COLOR, new DyedColorComponent(5186845))
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MORDOR.getValue())));
    public static final Item MORDOR_DEGRADED_GONDORIAN_CHESTPLATE = registerDyeableArmorPiece("mordor_degraded_gondorian_chestplate",
            (settings) -> new CustomChestplateItem(ArmorMaterialsME.BURZUM_STEEL_T4, settings), new Item.Settings()
                    .component(DataComponentTypes.DYED_COLOR, new DyedColorComponent(5186845))
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MORDOR.getValue())));
    public static final Item MORDOR_GORGET_HAUBERK = registerArmorPiece("mordor_gorget_hauberk",
            (settings) -> new CustomChestplateItem(ArmorMaterialsME.BURZUM_STEEL_T4, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MORDOR.getValue())));
    public static final Item MORDOR_PAINTED_CUIRASS = registerDyeableArmorPiece("mordor_painted_cuirass",
            (settings) -> new CustomChestplateItem(ArmorMaterialsME.BURZUM_STEEL_T4, settings), new Item.Settings()
                    .component(DataComponentTypes.DYED_COLOR, new DyedColorComponent(5186845))
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MORDOR.getValue())));
    public static final Item MORDOR_REINFORCED_COAT = registerDyeableArmorPiece("mordor_reinforced_coat",
            (settings) -> new CustomChestplateItem(ArmorMaterialsME.BURZUM_STEEL_T4, settings), new Item.Settings()
                    .component(DataComponentTypes.DYED_COLOR, new DyedColorComponent(5186845))
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MORDOR.getValue())));

    public static final Item MORDOR_SCALE_COAT = registerArmorPiece("mordor_scale_coat",
            (settings) -> new CustomLeggingsItem(ArmorMaterialsME.BURZUM_STEEL_T4, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MORDOR.getValue())));

    public static final Item MORDOR_PLATE_BOOTS = registerArmorPiece("mordor_plate_boots",
            (settings) -> new CustomBootsItem(ArmorMaterialsME.BURZUM_STEEL_T4, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MORDOR.getValue())));

    public static final Item MORDOR_GREAT_HELMET = registerCustomModelArmorPiece("mordor_great_helmet",
            (settings) -> new CustomHelmetItem(ArmorMaterialsME.BURZUM_STEEL_T5, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MORDOR.getValue())));
    public static final Item MORDOR_SNOUT_HELMET = registerCustomModelArmorPiece("mordor_snout_helmet",
            (settings) -> new CustomHelmetItem(ArmorMaterialsME.BURZUM_STEEL_T5, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MORDOR.getValue())));

    public static final Item BLACK_URUK_PLATE_HELMET = registerArmorPiece("black_uruk_plate_helmet",
            (settings) -> new CustomHelmetItem(ArmorMaterialsME.BURZUM_STEEL_T5, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MORDOR.getValue())));
    public static final Item BLACK_URUK_FACE_PLATE_HELMET = registerArmorPiece("black_uruk_face_plate_helmet",
            (settings) -> new CustomHelmetItem(ArmorMaterialsME.BURZUM_STEEL_T5, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MORDOR.getValue())));
    public static final Item BLACK_URUK_PLATE_CHESTPLATE = registerCustomModelArmorPiece("black_uruk_plate_chestplate",
            (settings) -> new CustomChestplateItem(ArmorMaterialsME.BURZUM_STEEL_T5, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MORDOR.getValue())));
    public static final Item BLACK_URUK_PLATE_LEGGINGS = registerArmorPiece("black_uruk_plate_leggings",
            (settings) -> new CustomLeggingsItem(ArmorMaterialsME.BURZUM_STEEL_T5, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MORDOR.getValue())));
    public static final Item BLACK_URUK_PLATE_BOOTS = registerArmorPiece("black_uruk_plate_boots",
            (settings) -> new CustomBootsItem(ArmorMaterialsME.BURZUM_STEEL_T5, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MORDOR.getValue())));

    public static final Item BLACK_URUK_COMMANDER_HELMET = registerArmorPiece("black_uruk_commander_helmet",
            (settings) -> new CustomHelmetItem(ArmorMaterialsME.BURZUM_STEEL_T5, settings), new Item.Settings()
                    .component(DataComponentTypesME.HELMET_ATTACHMENT_DATA, HelmetAttachmentDataComponent.newHelmetAttachment(HelmetAttachmentsME.SKULL))
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MORDOR.getValue())));
    public static final Item BLACK_URUK_COMMANDER_CHESTPLATE = registerCustomModelArmorPiece("black_uruk_commander_chestplate",
            (settings) -> new CustomChestplateItem(ArmorMaterialsME.BURZUM_STEEL_T5, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MORDOR.getValue())));

    public static final Item BLACK_CASTELLAN_HELMET = registerCustomModelArmorPiece("black_castellan_helmet",
            (settings) -> new CustomHelmetItem(ArmorMaterialsME.BURZUM_STEEL_T5, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MORDOR.getValue())));
    public static final Item BLACK_CASTELLAN_CHESTPLATE = registerCustomModelArmorPiece("black_castellan_chestplate",
            (settings) -> new CustomChestplateItem(ArmorMaterialsME.STEEL_T5, settings), new Item.Settings()
                    .component(DataComponentTypesME.BACK_ATTACHMENT_DATA, BackAttachmentDataComponent.newBackAttachment(BackAttachmentsME.BLACK_CASTELLAN_CAPE))
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MORDOR.getValue())));
    public static final Item BLACK_CASTELLAN_LEGGINGS = registerArmorPiece("black_castellan_leggings",
            (settings) -> new CustomLeggingsItem(ArmorMaterialsME.STEEL_T5, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MORDOR.getValue())));
    public static final Item BLACK_CASTELLAN_BOOTS = registerArmorPiece("black_castellan_boots",
            (settings) -> new CustomBootsItem(ArmorMaterialsME.STEEL_T5, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MORDOR.getValue())));

    public static final Item MORDOR_BLACK_NUMENOREAN_PLATE_HELMET = registerCustomModelArmorPiece("mordor_black_numenorean_plate_helmet",
            (settings) -> new CustomHelmetItem(ArmorMaterialsME.STEEL_T5, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MORDOR.getValue())));
    public static final Item MORDOR_BLACK_NUMENOREAN_PLATE_CHESTPLATE = registerCustomModelArmorPiece("mordor_black_numenorean_plate_chestplate",
            (settings) -> new CustomChestplateItem(ArmorMaterialsME.STEEL_T5, settings), new Item.Settings()
                    .component(DataComponentTypesME.BACK_ATTACHMENT_DATA, BackAttachmentDataComponent.newBackAttachment(BackAttachmentsME.MORDOR_BLACK_NUMENOREAN_CAPE))
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MORDOR.getValue())));
    public static final Item MORDOR_BLACK_NUMENOREAN_PLATE_LEGGINGS = registerArmorPiece("mordor_black_numenorean_plate_leggings",
            (settings) -> new CustomLeggingsItem(ArmorMaterialsME.STEEL_T5, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MORDOR.getValue())));
    public static final Item MORDOR_BLACK_NUMENOREAN_PLATE_BOOTS = registerArmorPiece("mordor_black_numenorean_plate_boots",
            (settings) -> new CustomBootsItem(ArmorMaterialsME.STEEL_T5, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MORDOR.getValue())));

    public static final Item BLACK_CASTELLAN_CAPE = registerBackAttachment("black_castellan_cape",
            (settings) -> new BackAttachmentItem(settings, ArmorMaterialsME.FABRIC_T0), new Item.Settings()
                    .component(DataComponentTypesME.BACK_ATTACHMENT_DATA, BackAttachmentDataComponent.newBackAttachment(BackAttachmentsME.BLACK_CASTELLAN_CAPE))
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MORDOR.getValue())));
    public static final Item MORDOR_BLACK_NUMENOREAN_CAPE = registerBackAttachment("mordor_black_numenorean_cape",
            (settings) -> new BackAttachmentItem(settings, ArmorMaterialsME.FABRIC_T0), new Item.Settings()
                    .component(DataComponentTypesME.BACK_ATTACHMENT_DATA, BackAttachmentDataComponent.newBackAttachment(BackAttachmentsME.MORDOR_BLACK_NUMENOREAN_CAPE))
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MORDOR.getValue())));

    public static final Item NAZGUL_HOOD = registerHelmetAttachment("nazgul_hood",
            (settings) -> new HelmetAttachmentItem(settings, ArmorMaterialsME.FABRIC_T0), new Item.Settings()
                    .component(DataComponentTypesME.HELMET_ATTACHMENT_DATA, HelmetAttachmentDataComponent.newHelmetAttachment(HelmetAttachmentsME.NAZGUL_HOOD))
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MORDOR.getValue())));
    public static final Item NAZGUL_ROBES = registerBackAttachment("nazgul_robes",
            (settings) -> new BackAttachmentItem(settings, ArmorMaterialsME.FABRIC_T0), new Item.Settings()
                    .component(DataComponentTypesME.BACK_ATTACHMENT_DATA, BackAttachmentDataComponent.newBackAttachment(BackAttachmentsME.NAZGUL_ROBES))
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MORDOR.getValue())));

    public static final Item RINGWRAITH_MAIL_HAUBERK = registerArmorPiece("ringwraith_mail_hauberk",
            (settings) -> new CustomChestplateItem(ArmorMaterialsME.STEEL_T5, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MORDOR.getValue())));
    public static final Item RINGWRAITH_LEGGINGS = registerArmorPiece("ringwraith_leggings",
            (settings) -> new CustomLeggingsItem(ArmorMaterialsME.STEEL_T5, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MORDOR.getValue())));
    public static final Item RINGWRAITH_BOOTS = registerArmorPiece("ringwraith_boots",
            (settings) -> new CustomBootsItem(ArmorMaterialsME.STEEL_T5, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MORDOR.getValue())));

    public static final Item WARG_MORDOR_MAIL_ARMOR = registerDyeableArmorPiece("warg_mordor_mail_armor",
            Item::new, ItemSettingsME.wargArmor(ArmorMaterialsME.WARG_MORDOR_MAIL_ARMOR.material())
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MORDOR.getValue())));

    public static final Item WARG_MORDOR_PLATE_ARMOR = registerGeneratedItem("warg_mordor_plate_armor",
            Item::new, ItemSettingsME.wargArmor(ArmorMaterialsME.WARG_MORDOR_PLATE_ARMOR.material())
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MORDOR.getValue())));


    //region DOL GULDUR
    public static final Item DOL_GULDUR_JAILER_COLLAR = registerArmorPiece("dol_guldur_jailer_collar",
            (settings) -> new CustomHelmetItem(ArmorMaterialsME.CRUDE_T1, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MORDOR.getValue())));
    public static final Item WEATHERED_DOL_GULDUR_JAILER_COLLAR = registerArmorPiece("weathered_dol_guldur_jailer_collar",
            (settings) -> new CustomHelmetItem(ArmorMaterialsME.CRUDE_T1, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MORDOR.getValue())));

    public static final Item DOL_GULDUR_EXECUTIONER_HOOD = registerCustomModelArmorPiece("dol_guldur_executioner_hood",
            (settings) -> new CustomHelmetItem(ArmorMaterialsME.LEATHER_T2, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MORDOR.getValue())));

    public static final Item DOL_GULDUR_EXECUTIONER_CHESTPLATE = registerArmorPiece("dol_guldur_executioner_chestplate",
            (settings) -> new CustomChestplateItem(ArmorMaterialsME.CRUDE_T3, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MORDOR.getValue())));
    public static final Item WEATHERED_DOL_GULDUR_EXECUTIONER_CHESTPLATE = registerArmorPiece("weathered_dol_guldur_executioner_chestplate",
            (settings) -> new CustomChestplateItem(ArmorMaterialsME.CRUDE_T3, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MORDOR.getValue())));

    public static final Item DOL_GULDUR_PADDED_CHESTPLATE = registerArmorPiece("dol_guldur_padded_chestplate",
            (settings) -> new CustomChestplateItem(ArmorMaterialsME.CRUDE_T3, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MORDOR.getValue())));

    public static final Item DOL_GULDUR_HUNTER_HELMET = registerCustomModelArmorPiece("dol_guldur_hunter_helmet",
            (settings) -> new CustomHelmetItem(ArmorMaterialsME.BURZUM_STEEL_T4, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MORDOR.getValue())));

    public static final Item DOL_GULDUR_RAIDER_HELMET = registerCustomModelArmorPiece("dol_guldur_raider_helmet",
            (settings) -> new CustomHelmetItem(ArmorMaterialsME.BURZUM_STEEL_T4, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MORDOR.getValue())));
    public static final Item WEATHERED_DOL_GULDUR_RAIDER_HELMET = registerCustomModelArmorPiece("weathered_dol_guldur_raider_helmet",
            (settings) -> new CustomHelmetItem(ArmorMaterialsME.BURZUM_STEEL_T4, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MORDOR.getValue())));

    public static final Item DOL_GULDUR_RAIDER_CHESTPLATE = registerArmorPiece("dol_guldur_raider_chestplate",
            (settings) -> new CustomChestplateItem(ArmorMaterialsME.BURZUM_STEEL_T4, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MORDOR.getValue())));
    public static final Item WEATHERED_DOL_GULDUR_RAIDER_CHESTPLATE = registerArmorPiece("weathered_dol_guldur_raider_chestplate",
            (settings) -> new CustomChestplateItem(ArmorMaterialsME.BURZUM_STEEL_T4, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MORDOR.getValue())));

    public static final Item DOL_GULDUR_RAIDER_COAT = registerArmorPiece("dol_guldur_raider_coat",
            (settings) -> new CustomLeggingsItem(ArmorMaterialsME.BURZUM_STEEL_T4, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MORDOR.getValue())));

    public static final Item DOL_GULDUR_BOOTS = registerArmorPiece("dol_guldur_boots",
            (settings) -> new CustomBootsItem(ArmorMaterialsME.BURZUM_STEEL_T4, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MORDOR.getValue())));
    public static final Item WEATHERED_DOL_GULDUR_BOOTS = registerArmorPiece("weathered_dol_guldur_boots",
            (settings) -> new CustomBootsItem(ArmorMaterialsME.BURZUM_STEEL_T4, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MORDOR.getValue())));

    public static final Item DOL_GULDUR_MARAUDER_HELMET = registerCustomModelArmorPiece("dol_guldur_marauder_helmet",
            (settings) -> new CustomHelmetItem(ArmorMaterialsME.BURZUM_STEEL_T5, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MORDOR.getValue())));
    public static final Item DOL_GULDUR_MARAUDER_CHESTPLATE = registerCustomModelArmorPiece("dol_guldur_marauder_chestplate",
            (settings) -> new CustomChestplateItem(ArmorMaterialsME.STEEL_T5, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MORDOR.getValue())));
    public static final Item DOL_GULDUR_MARAUDER_LEGGINGS = registerArmorPiece("dol_guldur_marauder_leggings",
            (settings) -> new CustomLeggingsItem(ArmorMaterialsME.STEEL_T5, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MORDOR.getValue())));
    public static final Item DOL_GULDUR_MARAUDER_BOOTS = registerArmorPiece("dol_guldur_marauder_boots",
            (settings) -> new CustomBootsItem(ArmorMaterialsME.STEEL_T5, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MORDOR.getValue())));

    public static final Item WEATHERED_DOL_GULDUR_MARAUDER_HELMET = registerCustomModelArmorPiece("weathered_dol_guldur_marauder_helmet",
            (settings) -> new CustomHelmetItem(ArmorMaterialsME.BURZUM_STEEL_T5, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MORDOR.getValue())));
    public static final Item WEATHERED_DOL_GULDUR_MARAUDER_CHESTPLATE = registerCustomModelArmorPiece("weathered_dol_guldur_marauder_chestplate",
            (settings) -> new CustomChestplateItem(ArmorMaterialsME.STEEL_T5, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MORDOR.getValue())));
    public static final Item WEATHERED_DOL_GULDUR_MARAUDER_LEGGINGS = registerArmorPiece("weathered_dol_guldur_marauder_leggings",
            (settings) -> new CustomLeggingsItem(ArmorMaterialsME.STEEL_T5, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MORDOR.getValue())));
    public static final Item WEATHERED_DOL_GULDUR_MARAUDER_BOOTS = registerArmorPiece("weathered_dol_guldur_marauder_boots",
            (settings) -> new CustomBootsItem(ArmorMaterialsME.STEEL_T5, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MORDOR.getValue())));

    public static final Item DOL_GULDUR_STALKER_HELMET = registerDyeableCustomModelArmorPiece("dol_guldur_stalker_helmet",
            (settings) -> new CustomHelmetItem(ArmorMaterialsME.BURZUM_STEEL_T5, settings), new Item.Settings()
                    .component(DataComponentTypes.DYED_COLOR, new DyedColorComponent(0x3D3232))
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MORDOR.getValue())));
    public static final Item DOL_GULDUR_STALKER_CHESTPLATE = registerDyeableCustomModelArmorPiece("dol_guldur_stalker_chestplate",
            (settings) -> new CustomChestplateItem(ArmorMaterialsME.STEEL_T5, settings), new Item.Settings()
                    .component(DataComponentTypesME.BACK_ATTACHMENT_DATA, BackAttachmentDataComponent.newBackAttachment(BackAttachmentsME.DOL_GULDUR_STALKER_CAPE))
                    .component(DataComponentTypes.DYED_COLOR, new DyedColorComponent(0x3D3232))
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MORDOR.getValue())));
    public static final Item DOL_GULDUR_STALKER_LEGGINGS = registerArmorPiece("dol_guldur_stalker_leggings",
            (settings) -> new CustomLeggingsItem(ArmorMaterialsME.STEEL_T5, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MORDOR.getValue())));
    public static final Item DOL_GULDUR_STALKER_BOOTS = registerArmorPiece("dol_guldur_stalker_boots",
            (settings) -> new CustomBootsItem(ArmorMaterialsME.STEEL_T5, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MORDOR.getValue())));

    public static final Item WEATHERED_DOL_GULDUR_STALKER_HELMET = registerDyeableCustomModelArmorPiece("weathered_dol_guldur_stalker_helmet",
            (settings) -> new CustomHelmetItem(ArmorMaterialsME.BURZUM_STEEL_T5, settings), new Item.Settings()
                    .component(DataComponentTypes.DYED_COLOR, new DyedColorComponent(0x3D3232))
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MORDOR.getValue())));
    public static final Item WEATHERED_DOL_GULDUR_STALKER_CHESTPLATE = registerDyeableCustomModelArmorPiece("weathered_dol_guldur_stalker_chestplate",
            (settings) -> new CustomChestplateItem(ArmorMaterialsME.STEEL_T5, settings), new Item.Settings()
                    .component(DataComponentTypesME.BACK_ATTACHMENT_DATA, BackAttachmentDataComponent.newBackAttachment(BackAttachmentsME.DOL_GULDUR_STALKER_CAPE))
                    .component(DataComponentTypes.DYED_COLOR, new DyedColorComponent(0x3D3232))
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MORDOR.getValue())));
    public static final Item WEATHERED_DOL_GULDUR_STALKER_LEGGINGS = registerArmorPiece("weathered_dol_guldur_stalker_leggings",
            (settings) -> new CustomLeggingsItem(ArmorMaterialsME.STEEL_T5, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MORDOR.getValue())));
    public static final Item WEATHERED_DOL_GULDUR_STALKER_BOOTS = registerArmorPiece("weathered_dol_guldur_stalker_boots",
            (settings) -> new CustomBootsItem(ArmorMaterialsME.STEEL_T5, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MORDOR.getValue())));

    public static final Item BLACK_REAVER_HELMET = registerCustomModelArmorPiece("black_reaver_helmet",
            (settings) -> new CustomHelmetItem(ArmorMaterialsME.BURZUM_STEEL_T5, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MORDOR.getValue())));

    public static final Item BLACK_REAVER_SHOULDER_CAPE = registerBackAttachment("black_reaver_shoulder_cape",
            (settings) -> new BackAttachmentItem(settings, ArmorMaterialsME.FABRIC_T0), new Item.Settings()
                    .component(DataComponentTypesME.BACK_ATTACHMENT_DATA, BackAttachmentDataComponent.newBackAttachment(BackAttachmentsME.BLACK_REAVER_SHOULDER_CAPE))
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MORDOR.getValue())));
    public static final Item DOL_GULDUR_STALKER_CAPE = registerBackAttachment("dol_guldur_stalker_cape",
            (settings) -> new BackAttachmentItem(settings, ArmorMaterialsME.FABRIC_T0), new Item.Settings()
                    .component(DataComponentTypesME.BACK_ATTACHMENT_DATA, BackAttachmentDataComponent.newBackAttachment(BackAttachmentsME.DOL_GULDUR_STALKER_CAPE))
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MORDOR.getValue())));
    //endregion
    //endregion

    //region ISENGARD
    public static final Item URUK_HAI_LEATHER_SCOUT_CAP = registerCustomModelArmorPiece("uruk_hai_leather_scout_cap",
            (settings) -> new CustomHelmetItem(ArmorMaterialsME.LEATHER_T2, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.ISENGARD.getValue())));

    public static final Item URUK_HAI_LEATHER_CHESTPLATE = registerArmorPiece("uruk_hai_leather_chestplate",
            (settings) -> new CustomChestplateItem(ArmorMaterialsME.LEATHER_T2, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.ISENGARD.getValue())));
    public static final Item URUK_HAI_PAINTED_LEATHER_CHESTPLATE = registerArmorPiece("uruk_hai_painted_leather_chestplate",
            (settings) -> new CustomChestplateItem(ArmorMaterialsME.LEATHER_T2, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.ISENGARD.getValue())));
    public static final Item URUK_HAI_LEATHER_HAUBERK = registerArmorPiece("uruk_hai_leather_hauberk",
            (settings) -> new CustomChestplateItem(ArmorMaterialsME.LEATHER_T2, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.ISENGARD.getValue())));

    public static final Item URUK_HAI_BOOTS = registerArmorPiece("uruk_hai_boots",
            (settings) -> new CustomBootsItem(ArmorMaterialsME.LEATHER_T2, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.ISENGARD.getValue())));

    public static final Item URUK_HAI_LIGHT_HELMET = registerCustomModelArmorPiece("uruk_hai_light_helmet",
            (settings) -> new CustomHelmetItem(ArmorMaterialsME.CRUDE_T3, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.ISENGARD.getValue())));
    public static final Item URUK_HAI_PAINTED_LIGHT_HELMET = registerCustomModelArmorPiece("uruk_hai_painted_light_helmet",
            (settings) -> new CustomHelmetItem(ArmorMaterialsME.CRUDE_T3, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.ISENGARD.getValue())));
    public static final Item URUK_HAI_REINFORCED_HELMET = registerCustomModelArmorPiece("uruk_hai_reinforced_helmet",
            (settings) -> new CustomHelmetItem(ArmorMaterialsME.CRUDE_T3, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.ISENGARD.getValue())));
    public static final Item URUK_HAI_PAINTED_REINFORCED_HELMET = registerCustomModelArmorPiece("uruk_hai_painted_reinforced_helmet",
            (settings) -> new CustomHelmetItem(ArmorMaterialsME.CRUDE_T3, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.ISENGARD.getValue())));

    public static final Item URUK_HAI_MAIL_HAUBERK = registerArmorPiece("uruk_hai_mail_hauberk",
            (settings) -> new CustomChestplateItem(ArmorMaterialsME.CRUDE_T3, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.ISENGARD.getValue())));

    public static final Item URUK_HAI_MAIL_COAT = registerArmorPiece("uruk_hai_mail_coat",
            (settings) -> new CustomLeggingsItem(ArmorMaterialsME.CRUDE_T3, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.ISENGARD.getValue())));
    public static final Item URUK_HAI_MAIL_SKIRT = registerArmorPiece("uruk_hai_mail_skirt",
            (settings) -> new CustomLeggingsItem(ArmorMaterialsME.CRUDE_T3, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.ISENGARD.getValue())));

    public static final Item URUK_HAI_SOLDIER_HELMET = registerCustomModelArmorPiece("uruk_hai_soldier_helmet",
            (settings) -> new CustomHelmetItem(ArmorMaterialsME.BURZUM_STEEL_T4, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.ISENGARD.getValue())));
    public static final Item URUK_HAI_PAINTED_SOLDIER_HELMET = registerCustomModelArmorPiece("uruk_hai_painted_soldier_helmet",
            (settings) -> new CustomHelmetItem(ArmorMaterialsME.BURZUM_STEEL_T4, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.ISENGARD.getValue())));

    public static final Item URUK_HAI_CUIRASS = registerArmorPiece("uruk_hai_cuirass",
            (settings) -> new CustomChestplateItem(ArmorMaterialsME.BURZUM_STEEL_T4, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.ISENGARD.getValue())));

    public static final Item URUK_HAI_PLATE_HELMET = registerCustomModelArmorPiece("uruk_hai_plate_helmet",
            (settings) -> new CustomHelmetItem(ArmorMaterialsME.BURZUM_STEEL_T5, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.ISENGARD.getValue())));
    public static final Item URUK_HAI_PAINTED_PLATE_HELMET = registerCustomModelArmorPiece("uruk_hai_painted_plate_helmet",
            (settings) -> new CustomHelmetItem(ArmorMaterialsME.BURZUM_STEEL_T5, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.ISENGARD.getValue())));
    public static final Item URUK_HAI_SAPPER_HELMET = registerCustomModelArmorPiece("uruk_hai_sapper_helmet",
            (settings) -> new CustomHelmetItem(ArmorMaterialsME.BURZUM_STEEL_T5, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.ISENGARD.getValue())));
    public static final Item URUK_HAI_PAINTED_SAPPER_HELMET = registerCustomModelArmorPiece("uruk_hai_painted_sapper_helmet",
            (settings) -> new CustomHelmetItem(ArmorMaterialsME.BURZUM_STEEL_T5, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.ISENGARD.getValue())));
    public static final Item URUK_HAI_BERSERKER_HELMET = registerCustomModelArmorPiece("uruk_hai_berserker_helmet",
            (settings) -> new CustomHelmetItem(ArmorMaterialsME.BURZUM_STEEL_T5, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.ISENGARD.getValue())));
    public static final Item URUK_HAI_PAINTED_BERSERKER_HELMET = registerCustomModelArmorPiece("uruk_hai_painted_berserker_helmet",
            (settings) -> new CustomHelmetItem(ArmorMaterialsME.BURZUM_STEEL_T5, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.ISENGARD.getValue())));
    public static final Item URUK_HAI_COMMANDER_HELMET = registerCustomModelArmorPiece("uruk_hai_commander_helmet",
            (settings) -> new CustomHelmetItem(ArmorMaterialsME.BURZUM_STEEL_T5, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.ISENGARD.getValue())));
    public static final Item URUK_HAI_PAINTED_COMMANDER_HELMET = registerCustomModelArmorPiece("uruk_hai_painted_commander_helmet",
            (settings) -> new CustomHelmetItem(ArmorMaterialsME.BURZUM_STEEL_T5, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.ISENGARD.getValue())));
    public static final Item URUK_HAI_PLATE_CHESTPLATE = registerCustomModelArmorPiece("uruk_hai_plate_chestplate",
            (settings) -> new CustomChestplateItem(ArmorMaterialsME.BURZUM_STEEL_T5, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.ISENGARD.getValue())));
    public static final Item URUK_HAI_PLATE_LEGGINGS = registerArmorPiece("uruk_hai_plate_leggings",
            (settings) -> new CustomLeggingsItem(ArmorMaterialsME.BURZUM_STEEL_T5, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.ISENGARD.getValue())));
    public static final Item URUK_HAI_PLATE_BOOTS = registerArmorPiece("uruk_hai_plate_boots",
            (settings) -> new CustomBootsItem(ArmorMaterialsME.BURZUM_STEEL_T5, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.ISENGARD.getValue())));

    public static final Item ORTHANC_GUARD_HELMET = registerCustomModelArmorPiece("orthanc_guard_helmet",
            (settings) -> new CustomHelmetItem(ArmorMaterialsME.STEEL_T5, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.ISENGARD.getValue())));
    public static final Item ORTHANC_COMMANDER_HELMET = registerCustomModelArmorPiece("orthanc_commander_helmet",
            (settings) -> new CustomHelmetItem(ArmorMaterialsME.STEEL_T5, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.ISENGARD.getValue())));
    public static final Item ORTHANC_GUARD_CHESTPLATE = registerArmorPiece("orthanc_guard_chestplate",
            (settings) -> new CustomChestplateItem(ArmorMaterialsME.STEEL_T5, settings), new Item.Settings()
                    .component(DataComponentTypesME.BACK_ATTACHMENT_DATA, BackAttachmentDataComponent.newBackAttachment(BackAttachmentsME.ORTHANC_GUARD_CAPE))
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.ISENGARD.getValue())));
    public static final Item ORTHANC_GUARD_LEGGINGS = registerArmorPiece("orthanc_guard_leggings",
            (settings) -> new CustomLeggingsItem(ArmorMaterialsME.STEEL_T5, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.ISENGARD.getValue())));
    public static final Item ORTHANC_GUARD_BOOTS = registerArmorPiece("orthanc_guard_boots",
            (settings) -> new CustomBootsItem(ArmorMaterialsME.STEEL_T5, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.ISENGARD.getValue())));

    public static final Item ORTHANC_GUARD_CAPE = registerBackAttachment("orthanc_guard_cape",
            (settings) -> new BackAttachmentItem(settings, ArmorMaterialsME.FABRIC_T0), new Item.Settings()
                    .component(DataComponentTypesME.BACK_ATTACHMENT_DATA, BackAttachmentDataComponent.newBackAttachment(BackAttachmentsME.ORTHANC_GUARD_CAPE))
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.ISENGARD.getValue())));

    public static final Item WARG_ISENGARD_PLATE_ARMOR = registerGeneratedItem("warg_isengard_plate_armor",
            Item::new, ItemSettingsME.wargArmor(ArmorMaterialsME.WARG_MORDOR_MAIL_ARMOR.material())
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.ISENGARD.getValue())));
    //endregion

    //region HOBGOB TRIBES
    public static final Item GUNDABAD_BONE_PAULDRON = registerDyeableArmorPiece("gundabad_bone_pauldron",
            (settings) -> new CustomChestplateItem(ArmorMaterialsME.LEATHER_T1, settings), new Item.Settings()
                    .component(DataComponentTypes.DYED_COLOR, new DyedColorComponent(3944757))
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MISTY_MOUNTAINS_GOBLINS.getValue())));

    public static final Item GUNDABAD_SEEKER_HELMET = registerDyeableCustomModelArmorPiece("gundabad_seeker_helmet",
            (settings) -> new CustomHelmetItem(ArmorMaterialsME.CRUDE_T2, settings), new Item.Settings()
                    .component(DataComponentTypes.DYED_COLOR, new DyedColorComponent(3944757))
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MISTY_MOUNTAINS_GOBLINS.getValue())));

    public static final Item GUNDABAD_LEATHER_SCALE_COAT = registerDyeableArmorPiece("gundabad_leather_scale_coat",
            (settings) -> new CustomChestplateItem(ArmorMaterialsME.LEATHER_T2, settings), new Item.Settings()
                    .component(DataComponentTypes.DYED_COLOR, new DyedColorComponent(3944757))
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MISTY_MOUNTAINS_GOBLINS.getValue())));

    public static final Item GUNDABAD_LEATHER_LEGGINGS = registerDyeableArmorPiece("gundabad_leather_leggings",
            (settings) -> new CustomLeggingsItem(ArmorMaterialsME.LEATHER_T2, settings), new Item.Settings()
                    .component(DataComponentTypes.DYED_COLOR, new DyedColorComponent(3944757))
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MISTY_MOUNTAINS_GOBLINS.getValue())));

    public static final Item GUNDABAD_LACED_BOOTS = registerDyeableArmorPiece("gundabad_laced_boots",
            (settings) -> new CustomBootsItem(ArmorMaterialsME.LEATHER_T2, settings), new Item.Settings()
                    .component(DataComponentTypes.DYED_COLOR, new DyedColorComponent(3944757))
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MISTY_MOUNTAINS_GOBLINS.getValue())));

    public static final Item GUNDABAD_SKULLCAP_HELMET = registerDyeableCustomModelArmorPiece("gundabad_skullcap_helmet",
            (settings) -> new CustomHelmetItem(ArmorMaterialsME.CRUDE_T3, settings), new Item.Settings()
                    .component(DataComponentTypes.DYED_COLOR, new DyedColorComponent(3944757))
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MISTY_MOUNTAINS_GOBLINS.getValue())));
    public static final Item GUNDABAD_MAIL_COIF = registerArmorPiece("gundabad_mail_coif",
            (settings) -> new CustomHelmetItem(ArmorMaterialsME.CRUDE_T3, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MISTY_MOUNTAINS_GOBLINS.getValue())));

    public static final Item GUNDABAD_MAIL_HAUBERK = registerArmorPiece("gundabad_mail_hauberk",
            (settings) -> new CustomChestplateItem(ArmorMaterialsME.CRUDE_T3, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MISTY_MOUNTAINS_GOBLINS.getValue())));

    public static final Item GUNDABAD_MAIL_COAT = registerDyeableArmorPiece("gundabad_mail_coat",
            (settings) -> new CustomLeggingsItem(ArmorMaterialsME.CRUDE_T3, settings), new Item.Settings()
                    .component(DataComponentTypes.DYED_COLOR, new DyedColorComponent(3944757))
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MISTY_MOUNTAINS_GOBLINS.getValue())));

    public static final Item GUNDABAD_SCREECHER_HELMET = registerDyeableCustomModelArmorPiece("gundabad_screecher_helmet",
            (settings) -> new CustomHelmetItem(ArmorMaterialsME.BURZUM_STEEL_T4, settings), new Item.Settings()
                    .component(DataComponentTypes.DYED_COLOR, new DyedColorComponent(3944757))
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MISTY_MOUNTAINS_GOBLINS.getValue())));
    public static final Item GUNDABAD_SOLDIER_HELMET = registerDyeableCustomModelArmorPiece("gundabad_soldier_helmet",
            (settings) -> new CustomHelmetItem(ArmorMaterialsME.BURZUM_STEEL_T4, settings), new Item.Settings()
                    .component(DataComponentTypes.DYED_COLOR, new DyedColorComponent(3944757))
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MISTY_MOUNTAINS_GOBLINS.getValue())));

    public static final Item GUNDABAD_REINFORCED_LEATHER_SCALE_COAT = registerDyeableArmorPiece("gundabad_reinforced_leather_scale_coat",
            (settings) -> new CustomChestplateItem(ArmorMaterialsME.BURZUM_STEEL_T4, settings), new Item.Settings()
                    .component(DataComponentTypes.DYED_COLOR, new DyedColorComponent(3944757))
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MISTY_MOUNTAINS_GOBLINS.getValue())));
    public static final Item GUNDABAD_REINFORCED_LEATHER_VEST = registerDyeableArmorPiece("gundabad_reinforced_leather_vest",
            (settings) -> new CustomChestplateItem(ArmorMaterialsME.BURZUM_STEEL_T4, settings), new Item.Settings()
                    .component(DataComponentTypes.DYED_COLOR, new DyedColorComponent(3944757))
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MISTY_MOUNTAINS_GOBLINS.getValue())));

    public static final Item GUNDABAD_CAPTAIN_HELMET = registerDyeableCustomModelArmorPiece("gundabad_captain_helmet",
            (settings) -> new CustomHelmetItem(ArmorMaterialsME.BURZUM_STEEL_T5, settings), new Item.Settings()
                    .component(DataComponentTypes.DYED_COLOR, new DyedColorComponent(3944757))
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MISTY_MOUNTAINS_GOBLINS.getValue())));

    public static final Item GUNDABAD_HOBGOBLIN_PLATE_CRESTED_HELMET = registerCustomModelArmorPiece("gundabad_hobgoblin_plate_crested_helmet",
            (settings) -> new CustomHelmetItem(ArmorMaterialsME.BURZUM_STEEL_T5, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MISTY_MOUNTAINS_GOBLINS.getValue())));
    public static final Item GUNDABAD_HOBGOBLIN_PLATE_LARGE_CREST_HELMET = registerCustomModelArmorPiece("gundabad_hobgoblin_plate_large_crest_helmet",
            (settings) -> new CustomHelmetItem(ArmorMaterialsME.BURZUM_STEEL_T5, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MISTY_MOUNTAINS_GOBLINS.getValue())));
    public static final Item GUNDABAD_HOBGOBLIN_PLATE_LONG_HORN_HELMET = registerCustomModelArmorPiece("gundabad_hobgoblin_plate_long_horn_helmet",
            (settings) -> new CustomHelmetItem(ArmorMaterialsME.BURZUM_STEEL_T5, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MISTY_MOUNTAINS_GOBLINS.getValue())));
    public static final Item GUNDABAD_HOBGOBLIN_PLATE_SMALL_HORN_HELMET = registerCustomModelArmorPiece("gundabad_hobgoblin_plate_small_horn_helmet",
            (settings) -> new CustomHelmetItem(ArmorMaterialsME.BURZUM_STEEL_T5, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MISTY_MOUNTAINS_GOBLINS.getValue())));
    public static final Item GUNDABAD_HOBGOBLIN_PLATE_CHESTPLATE = registerCustomModelArmorPiece("gundabad_hobgoblin_plate_chestplate",
            (settings) -> new CustomChestplateItem(ArmorMaterialsME.BURZUM_STEEL_T5, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MISTY_MOUNTAINS_GOBLINS.getValue())));
    public static final Item GUNDABAD_HOBGOBLIN_MAIL_COAT = registerArmorPiece("gundabad_hobgoblin_mail_coat",
            (settings) -> new CustomLeggingsItem(ArmorMaterialsME.BURZUM_STEEL_T5, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MISTY_MOUNTAINS_GOBLINS.getValue())));
    public static final Item GUNDABAD_HOBGOBLIN_PLATED_BOOTS = registerArmorPiece("gundabad_hobgoblin_plated_boots",
            (settings) -> new CustomBootsItem(ArmorMaterialsME.BURZUM_STEEL_T5, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MISTY_MOUNTAINS_GOBLINS.getValue())));

    public static final Item WARG_GUNDABAD_PLATE_ARMOR = registerGeneratedItem("warg_gundabad_plate_armor",
            Item::new, ItemSettingsME.wargArmor(ArmorMaterialsME.WARG_GUNDABAD_PLATE_ARMOR.material())
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MISTY_MOUNTAINS_GOBLINS.getValue())));
    //endregion

    //region MORIA GOBS
    public static final Item MORIA_GOBLIN_SNAGA_NASAL_HELMET = registerDyeableCustomModelArmorPiece("moria_goblin_snaga_nasal_helmet",
            (settings) -> new CustomHelmetItem(ArmorMaterialsME.CRUDE_T2, settings), new Item.Settings()
                    .component(DataComponentTypes.DYED_COLOR, new DyedColorComponent(0x441E17))
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MISTY_MOUNTAINS_GOBLINS.getValue())));
    public static final Item MORIA_GOBLIN_SNAGA_HELMET = registerDyeableCustomModelArmorPiece("moria_goblin_snaga_helmet",
            (settings) -> new CustomHelmetItem(ArmorMaterialsME.CRUDE_T2, settings), new Item.Settings()
                    .component(DataComponentTypes.DYED_COLOR, new DyedColorComponent(0x441E17))
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MISTY_MOUNTAINS_GOBLINS.getValue())));

    public static final Item MORIA_GOBLIN_SNAGA_CUIRASS = registerCustomModelArmorPiece("moria_goblin_snaga_cuirass",
            (settings) -> new CustomChestplateItem(ArmorMaterialsME.CRUDE_T2, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MISTY_MOUNTAINS_GOBLINS.getValue())));
    public static final Item MORIA_GOBLIN_SNAGA_BELLY_PLATE = registerCustomModelArmorPiece("moria_goblin_snaga_belly_plate",
            (settings) -> new CustomChestplateItem(ArmorMaterialsME.CRUDE_T2, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MISTY_MOUNTAINS_GOBLINS.getValue())));

    public static final Item MORIA_GOBLIN_CLOTH_COAT = registerDyeableArmorPiece("moria_goblin_cloth_coat",
            (settings) -> new CustomLeggingsItem(ArmorMaterialsME.CRUDE_T2, settings), new Item.Settings()
                    .component(DataComponentTypes.DYED_COLOR, new DyedColorComponent(0x441E17))
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MISTY_MOUNTAINS_GOBLINS.getValue())));
    public static final Item MORIA_GOBLIN_SNAGA_SKIRT = registerArmorPiece("moria_goblin_snaga_skirt",
            (settings) -> new CustomLeggingsItem(ArmorMaterialsME.CRUDE_T2, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MISTY_MOUNTAINS_GOBLINS.getValue())));

    public static final Item MORIA_GOBLIN_CRUDE_BOOTS = registerArmorPiece("moria_goblin_crude_boots",
            (settings) -> new CustomBootsItem(ArmorMaterialsME.CRUDE_T2, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MISTY_MOUNTAINS_GOBLINS.getValue())));

    public static final Item MORIA_GOBLIN_CHARGER_HELMET = registerCustomModelArmorPiece("moria_goblin_charger_helmet",
            (settings) -> new CustomHelmetItem(ArmorMaterialsME.CRUDE_T3, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MISTY_MOUNTAINS_GOBLINS.getValue())));
    public static final Item MORIA_RUINED_DWARVEN_HELMET = registerCustomModelArmorPiece("moria_ruined_dwarven_helmet",
            (settings) -> new CustomHelmetItem(ArmorMaterialsME.CRUDE_T3, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MISTY_MOUNTAINS_GOBLINS.getValue())));

    public static final Item MORIA_GOBLIN_HAUBERK = registerDyeableCustomModelArmorPiece("moria_goblin_hauberk",
            (settings) -> new CustomChestplateItem(ArmorMaterialsME.CRUDE_T3, settings), new Item.Settings()
                    .component(DataComponentTypes.DYED_COLOR, new DyedColorComponent(0x441E17))
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MISTY_MOUNTAINS_GOBLINS.getValue())));
    public static final Item MORIA_RUINED_DWARVEN_HAUBERK = registerCustomModelArmorPiece("moria_ruined_dwarven_hauberk",
            (settings) -> new CustomChestplateItem(ArmorMaterialsME.CRUDE_T3, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MISTY_MOUNTAINS_GOBLINS.getValue())));

    public static final Item MORIA_GOBLIN_BITER_HELMET = registerCustomModelArmorPiece("moria_goblin_biter_helmet",
            (settings) -> new CustomHelmetItem(ArmorMaterialsME.BURZUM_STEEL_T4, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MISTY_MOUNTAINS_GOBLINS.getValue())));
    public static final Item MORIA_GOBLIN_MANDIBLE_HELMET = registerCustomModelArmorPiece("moria_goblin_mandible_helmet",
            (settings) -> new CustomHelmetItem(ArmorMaterialsME.BURZUM_STEEL_T4, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MISTY_MOUNTAINS_GOBLINS.getValue())));

    public static final Item MORIA_GOBLIN_REINFORCED_COAT = registerDyeableCustomModelArmorPiece("moria_goblin_reinforced_coat",
            (settings) -> new CustomChestplateItem(ArmorMaterialsME.BURZUM_STEEL_T4, settings), new Item.Settings()
                    .component(DataComponentTypes.DYED_COLOR, new DyedColorComponent(0x441E17))
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MISTY_MOUNTAINS_GOBLINS.getValue())));

    public static final Item MORIA_GOBLIN_SCREECHER_HELMET = registerCustomModelArmorPiece("moria_goblin_screecher_helmet",
            (settings) -> new CustomHelmetItem(ArmorMaterialsME.BURZUM_STEEL_T5, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MISTY_MOUNTAINS_GOBLINS.getValue())));
    public static final Item MORIA_GOBLIN_SCREECHER_CHESTPLATE = registerCustomModelArmorPiece("moria_goblin_screecher_chestplate",
            (settings) -> new CustomChestplateItem(ArmorMaterialsME.BURZUM_STEEL_T5, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MISTY_MOUNTAINS_GOBLINS.getValue())));
    public static final Item MORIA_GOBLIN_SCREECHER_LEGGINGS = registerDyeableArmorPiece("moria_goblin_screecher_leggings",
            (settings) -> new CustomLeggingsItem(ArmorMaterialsME.BURZUM_STEEL_T5, settings), new Item.Settings()
                    .component(DataComponentTypes.DYED_COLOR, new DyedColorComponent(0x441E17))
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MISTY_MOUNTAINS_GOBLINS.getValue())));
    public static final Item MORIA_GOBLIN_SCREECHER_BOOTS = registerArmorPiece("moria_goblin_screecher_boots",
            (settings) -> new CustomBootsItem(ArmorMaterialsME.BURZUM_STEEL_T5, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MISTY_MOUNTAINS_GOBLINS.getValue())));

    public static final Item MORIA_GOBLIN_CAPTAIN_HELMET = registerCustomModelArmorPiece("moria_goblin_captain_helmet",
            (settings) -> new CustomHelmetItem(ArmorMaterialsME.BURZUM_STEEL_T5, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MISTY_MOUNTAINS_GOBLINS.getValue())));

    public static final Item MORIA_GOBLIN_CHIEF_HELMET = registerCustomModelArmorPiece("moria_goblin_chief_helmet",
            (settings) -> new CustomHelmetItem(ArmorMaterialsME.BURZUM_STEEL_T5, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MISTY_MOUNTAINS_GOBLINS.getValue())));
    public static final Item MORIA_GOBLIN_CHIEF_CHESTPLATE = registerCustomModelArmorPiece("moria_goblin_chief_chestplate",
            (settings) -> new CustomChestplateItem(ArmorMaterialsME.BURZUM_STEEL_T5, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MISTY_MOUNTAINS_GOBLINS.getValue())));
    public static final Item MORIA_GOBLIN_CHIEF_LEGGINGS = registerArmorPiece("moria_goblin_chief_leggings",
            (settings) -> new CustomLeggingsItem(ArmorMaterialsME.BURZUM_STEEL_T5, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MISTY_MOUNTAINS_GOBLINS.getValue())));
    public static final Item MORIA_GOBLIN_CHIEF_BOOTS = registerArmorPiece("moria_goblin_chief_boots",
            (settings) -> new CustomBootsItem(ArmorMaterialsME.BURZUM_STEEL_T5, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MISTY_MOUNTAINS_GOBLINS.getValue())));
    //endregion

    //region GOBLIN TOWN
    public static final Item GOBLIN_KING_CROWN = registerCustomModelArmorPiece("goblin_king_crown",
            (settings) -> new CustomHelmetItem(ArmorMaterialsME.BONE_T0, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MISTY_MOUNTAINS_GOBLINS.getValue())));

    public static final Item GOBLIN_TOWN_CAP = registerCustomModelArmorPiece("goblin_town_cap",
            (settings) -> new CustomHelmetItem(ArmorMaterialsME.FABRIC_T1, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MISTY_MOUNTAINS_GOBLINS.getValue())));
    public static final Item GOBLIN_TOWN_BONE_MANDIBLE_CAP = registerCustomModelArmorPiece("goblin_town_bone_mandible_cap",
            (settings) -> new CustomHelmetItem(ArmorMaterialsME.BONE_T1, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MISTY_MOUNTAINS_GOBLINS.getValue())));
    public static final Item GOBLIN_TOWN_CROSSBONES_HELMET = registerCustomModelArmorPiece("goblin_town_crossbones_helmet",
            (settings) -> new CustomHelmetItem(ArmorMaterialsME.BONE_T1, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MISTY_MOUNTAINS_GOBLINS.getValue())));
    
    public static final Item GOBLIN_TOWN_BONE_STRAP = registerArmorPiece("goblin_town_bone_strap",
            (settings) -> new CustomChestplateItem(ArmorMaterialsME.BONE_T1, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MISTY_MOUNTAINS_GOBLINS.getValue())));
    public static final Item GOBLIN_TOWN_RIBCAGE = registerArmorPiece("goblin_town_ribcage",
            (settings) -> new CustomChestplateItem(ArmorMaterialsME.BONE_T1, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MISTY_MOUNTAINS_GOBLINS.getValue())));

    public static final Item GOBLIN_TOWN_LOINCLOTH = registerArmorPiece("goblin_town_loincloth",
            (settings) -> new CustomLeggingsItem(ArmorMaterialsME.FABRIC_T1, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MISTY_MOUNTAINS_GOBLINS.getValue())));

    public static final Item GOBLIN_TOWN_SANDALS = registerArmorPiece("goblin_town_sandals",
            (settings) -> new CustomBootsItem(ArmorMaterialsME.FABRIC_T1, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MISTY_MOUNTAINS_GOBLINS.getValue())));

    public static final Item GOBLIN_TOWN_NASAL_HELMET = registerCustomModelArmorPiece("goblin_town_nasal_helmet",
            (settings) -> new CustomHelmetItem(ArmorMaterialsME.CRUDE_T3, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MISTY_MOUNTAINS_GOBLINS.getValue())));
    public static final Item GOBLIN_TOWN_SKULL_CAP = registerCustomModelArmorPiece("goblin_town_skull_cap",
            (settings) -> new CustomHelmetItem(ArmorMaterialsME.CRUDE_T3, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MISTY_MOUNTAINS_GOBLINS.getValue())));
    public static final Item GOBLIN_TOWN_TUNNELER_HELMET = registerCustomModelArmorPiece("goblin_town_tunneler_helmet",
            (settings) -> new CustomHelmetItem(ArmorMaterialsME.CRUDE_T3, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MISTY_MOUNTAINS_GOBLINS.getValue())));

    public static final Item GOBLIN_TOWN_BONE_SCALE_COAT = registerCustomModelArmorPiece("goblin_town_bone_scale_coat",
            (settings) -> new CustomChestplateItem(ArmorMaterialsME.LEATHER_T2, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MISTY_MOUNTAINS_GOBLINS.getValue())));
    public static final Item GOBLIN_TOWN_CARAPACE_HARNESS = registerCustomModelArmorPiece("goblin_town_carapace_harness",
            (settings) -> new CustomChestplateItem(ArmorMaterialsME.LEATHER_T2, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MISTY_MOUNTAINS_GOBLINS.getValue())));
    public static final Item GOBLIN_TOWN_CRUDE_SCALE_CHESTPLATE = registerCustomModelArmorPiece("goblin_town_crude_scale_chestplate",
            (settings) -> new CustomChestplateItem(ArmorMaterialsME.LEATHER_T2, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MISTY_MOUNTAINS_GOBLINS.getValue())));

    public static final Item GOBLIN_TOWN_LEATHER_SKIRT = registerArmorPiece("goblin_town_leather_skirt",
            (settings) -> new CustomLeggingsItem(ArmorMaterialsME.LEATHER_T2, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MISTY_MOUNTAINS_GOBLINS.getValue())));

    public static final Item GOBLIN_TOWN_HEAVY_NASAL_HELMET = registerCustomModelArmorPiece("goblin_town_heavy_nasal_helmet",
            (settings) -> new CustomHelmetItem(ArmorMaterialsME.CRUDE_T3, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MISTY_MOUNTAINS_GOBLINS.getValue())));
    public static final Item GOBLIN_TOWN_MANDIBLE_HELMET = registerCustomModelArmorPiece("goblin_town_mandible_helmet",
            (settings) -> new CustomHelmetItem(ArmorMaterialsME.CRUDE_T3, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MISTY_MOUNTAINS_GOBLINS.getValue())));
    public static final Item GOBLIN_TOWN_SKULKER_GUARD_HELMET = registerCustomModelArmorPiece("goblin_town_skulker_guard_helmet",
            (settings) -> new CustomHelmetItem(ArmorMaterialsME.CRUDE_T3, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MISTY_MOUNTAINS_GOBLINS.getValue())));

    public static final Item GOBLIN_TOWN_BELLY_PLATE = registerArmorPiece("goblin_town_belly_plate",
            (settings) -> new CustomChestplateItem(ArmorMaterialsME.CRUDE_T3, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MISTY_MOUNTAINS_GOBLINS.getValue())));
    public static final Item GOBLIN_TOWN_REINFORCED_CARAPACE = registerCustomModelArmorPiece("goblin_town_reinforced_carapace",
            (settings) -> new CustomChestplateItem(ArmorMaterialsME.CRUDE_T3, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MISTY_MOUNTAINS_GOBLINS.getValue())));
    //endregion

    //endregion

    //endregion

    public static final Item HELMET_OF_HELM_HAMMERHAND = registerCustomModelArmorPiece("helmet_of_helm_hammerhand",
            (settings) -> new CustomArtefactHelmetItem(ArmorMaterialsME.STEEL_T5, settings), new Item.Settings().rarity(Rarity.EPIC)
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.ROHAN.getValue()))
                    .component(DataComponentTypes.LORE, ArtefactUtils.getArtefactLore("helmet_of_helm_hammerhand")));

    private static Item registerHelmetAttachment(String name, Function<Item.Settings, Item> factory, Item.Settings settings) {
        Item item = (Item)factory.apply(settings.registryKey(ModBlocks.keyOfItem(name)));
        ItemGroupsME.EQUIPMENT_CONTENTS.add(item.getDefaultStack());
        SimpleItemModel.items.add(item);
        helmetAtttachments.add(item);
        return registerItem(item, name);
    }

    private static Item registerDyeableHelmetAttachment(String name, Function<Item.Settings, Item> factory, Item.Settings settings) {
        Item item = (Item)factory.apply(settings.registryKey(ModBlocks.keyOfItem(name)));
        ItemGroupsME.EQUIPMENT_CONTENTS.add(item.getDefaultStack());
        SimpleDyeableItemModel.items.add(item);
        helmetAtttachments.add(item);
        return registerItem(item, name);
    }

    private static Item registerBackAttachment(String name, Function<Item.Settings, Item> factory, Item.Settings settings) {
        Item item = (Item)factory.apply(settings.registryKey(ModBlocks.keyOfItem(name)));
        ItemGroupsME.EQUIPMENT_CONTENTS.add(item.getDefaultStack());
        SimpleItemModel.items.add(item);
        backAttachments.add(item);
        return registerItem(item, name);
    }

    private static Item registerDyeableBackAttachment(String name, Function<Item.Settings, Item> factory, Item.Settings settings) {
        Item item = (Item)factory.apply(settings.registryKey(ModBlocks.keyOfItem(name)));
        ItemGroupsME.EQUIPMENT_CONTENTS.add(item.getDefaultStack());
        SimpleDyeableItemModel.items.add(item);
        backAttachments.add(item);
        return registerItem(item, name);
    }

    private static Item registerGeneratedItem(String name, Function<Item.Settings, Item> factory, Item.Settings settings) {
        Item item = (Item)factory.apply(settings.registryKey(ModBlocks.keyOfItem(name)));
        ItemGroupsME.EQUIPMENT_CONTENTS.add(item.getDefaultStack());
        SimpleItemModel.items.add(item);
        return registerItem(item, name);
    }

    private static Item registerDyeableArmorPiece(String name, Function<Item.Settings, Item> factory, Item.Settings settings) {
        Item item = (Item)factory.apply(settings.registryKey(ModBlocks.keyOfItem(name)));
        ItemGroupsME.EQUIPMENT_CONTENTS.add(item.getDefaultStack());
        SimpleDyeableItemModel.items.add(item);
        switch (item){
            case CustomHelmetItem helmetItem -> armorPiecesListHelmets.add(helmetItem);
            case CustomChestplateItem chestplateItem -> armorPiecesListChestplates.add(chestplateItem);
            case CustomLeggingsItem leggingsItem -> armorPiecesListLeggings.add(leggingsItem);
            case CustomBootsItem bootsItem -> armorPiecesListBoots.add(bootsItem);
            default -> {}
        }
        return registerItem(item, name);
    }

    private static Item registerArmorPiece(String name, Function<Item.Settings, Item> factory, Item.Settings settings) {
        Item item = (Item)factory.apply(settings.registryKey(ModBlocks.keyOfItem(name)));
        ItemGroupsME.EQUIPMENT_CONTENTS.add(item.getDefaultStack());
        SimpleItemModel.items.add(item);
        switch (item){
            case CustomHelmetItem helmetItem -> armorPiecesListHelmets.add(helmetItem);
            case CustomChestplateItem chestplateItem -> armorPiecesListChestplates.add(chestplateItem);
            case CustomLeggingsItem leggingsItem -> armorPiecesListLeggings.add(leggingsItem);
            case CustomBootsItem bootsItem -> armorPiecesListBoots.add(bootsItem);
            default -> throw new IllegalStateException("Unexpected value: " + item);
        }
        return registerItem(item, name);
    }

    private static Item registerCustomModelArmorPiece(String name, Function<Item.Settings, Item> factory, Item.Settings settings) {
        Item item = (Item)factory.apply(settings.registryKey(ModBlocks.keyOfItem(name)));
        ItemGroupsME.EQUIPMENT_CONTENTS.add(item.getDefaultStack());
        SimpleItemModel.items.add(item);
        return registerItem(item, name);
    }

    private static Item registerDyeableCustomModelArmorPiece(String name, Function<Item.Settings, Item> factory, Item.Settings settings) {
        Item item = (Item)factory.apply(settings.registryKey(ModBlocks.keyOfItem(name)));
        ItemGroupsME.EQUIPMENT_CONTENTS.add(item.getDefaultStack());
        SimpleDyeableItemModel.items.add(item);
        return registerItem(item, name);
    }

    private static Item registerItem(Item item, String name){
        TranslationEntries.itemEntries.add(item);
        if (item instanceof ArmorItem armorItem){
            switch (armorItem.getMaterial().tier()){
                case BASIC -> ArmorTags.basicArmors.add(item);
                case LIGHT -> ArmorTags.lightArmors.add(item);
                case MEDIUM -> ArmorTags.mediumArmors.add(item);
                case STURDY -> ArmorTags.sturdyArmors.add(item);
                case HEAVY -> ArmorTags.heavyArmors.add(item);
            }
        }
        RegistryAliases.aliases.add(new RegistryAliases.Alias(Registries.ITEM, name));
        return Registry.register(Registries.ITEM, ModBlocks.keyOfItem(name), item);
    }

    public static void registerModItems() {
        MiddleEarth.LOGGER.logDebugMsg("Registering Mod Equipment Items for " + MiddleEarth.MOD_ID);
    }
}
