package net.sevenstars.middleearth.item.utils.armor;

import net.sevenstars.middleearth.item.EquipmentItemsME;
import net.sevenstars.middleearth.item.utils.armor.backAttachments.BackAttachmentsME;
import net.sevenstars.middleearth.item.utils.armor.helmetAttachments.HelmetAttachmentsME;
import net.minecraft.item.Item;

import java.util.HashMap;

public class DyeablePiecesME {

    public static HashMap<Item, Boolean> dyeablePieces = new HashMap<>();
    public static HashMap<BackAttachmentsME, Boolean> dyeableBackAttachments = new HashMap<>();
    public static HashMap<HelmetAttachmentsME, Boolean> dyeableHelmetAttachments = new HashMap<>();

    public static void addDyeablePieces() {
        dyeablePieces.put(EquipmentItemsME.BYCOCKET, false);
        dyeablePieces.put(EquipmentItemsME.WANDERER_HAT, true);
        dyeablePieces.put(EquipmentItemsME.ARMING_COAT, true);
        dyeablePieces.put(EquipmentItemsME.ARMING_SKIRT, true);
        dyeablePieces.put(EquipmentItemsME.LEATHER_SKULLCAP, false);
        dyeablePieces.put(EquipmentItemsME.GAMBESON_CAP, false);
        dyeablePieces.put(EquipmentItemsME.GAMBESON_COWL, true);
        dyeablePieces.put(EquipmentItemsME.GAMBESON, true);
        dyeablePieces.put(EquipmentItemsME.LEATHER_VEST, false);
        dyeablePieces.put(EquipmentItemsME.LEATHER_SCALE_VEST, true);

        dyeablePieces.put(EquipmentItemsME.SHIRRIFF_HAT, true);

        dyeablePieces.put(EquipmentItemsME.GONDORIAN_LEATHER_CUIRASS, true);
        dyeablePieces.put(EquipmentItemsME.GONDORIAN_LEATHER_CHESTPLATE, true);

        dyeablePieces.put(EquipmentItemsME.ROHIRRIC_REINFORCED_COAT, true);
        dyeablePieces.put(EquipmentItemsME.ROHIRRIC_LEATHER_VEST, true);
        dyeablePieces.put(EquipmentItemsME.ROHIRRIC_LEATHER_SCALE_VEST, true);
        dyeablePieces.put(EquipmentItemsME.ROHIRRIC_GAMBESON, true);
        dyeablePieces.put(EquipmentItemsME.ROHIRRIC_REINFORCED_LEATHER_VEST, true);
        dyeablePieces.put(EquipmentItemsME.ROHIRRIC_BRACED_MAIL_SHIRT, true);

        dyeablePieces.put(EquipmentItemsME.DALISH_ARMING_COAT_BLACK_FUR, true);
        dyeablePieces.put(EquipmentItemsME.DALISH_ARMING_COAT_BROWN_FUR, true);
        dyeablePieces.put(EquipmentItemsME.DALISH_ARMING_COAT_TAN_FUR, true);

        dyeablePieces.put(EquipmentItemsME.ELVEN_ARMING_COAT, true);
        dyeablePieces.put(EquipmentItemsME.ELVEN_ARMING_SKIRT, true);
        dyeablePieces.put(EquipmentItemsME.ELVEN_OPEN_ARMING_SKIRT, true);

        dyeablePieces.put(EquipmentItemsME.WARDEN_OF_THE_GLADE_HELMET, true);

        dyeablePieces.put(EquipmentItemsME.DWARVEN_MINER_HELMET, true);
        dyeablePieces.put(EquipmentItemsME.DWARVEN_GAMBESON, true);
        dyeablePieces.put(EquipmentItemsME.DWARVEN_MINER_GAMBESON, true);

        dyeablePieces.put(EquipmentItemsME.LONGBEARD_WANDERER_COAT, true);
        dyeablePieces.put(EquipmentItemsME.LONGBEARD_LEATHER_HAUBERK, true);
        dyeablePieces.put(EquipmentItemsME.LONGBEARD_PARTISAN_OUTFIT, true);
        dyeablePieces.put(EquipmentItemsME.LONGBEARD_REINFORCED_LEATHER_HAUBERK, true);
        dyeablePieces.put(EquipmentItemsME.LONGBEARD_LEATHER_LEGGINGS, true);

        dyeablePieces.put(EquipmentItemsME.EREBOR_LEATHER_HELMET, true);
        dyeablePieces.put(EquipmentItemsME.EREBOR_BRACED_LEATHER_HELMET, true);
        dyeablePieces.put(EquipmentItemsME.EREBOR_NASAL_LEATHER_HELMET, true);
        dyeablePieces.put(EquipmentItemsME.EREBOR_BRAWLER_CHESTPLATE, true);
        dyeablePieces.put(EquipmentItemsME.EREBOR_LONG_COAT, true);
        dyeablePieces.put(EquipmentItemsME.EREBOR_PADDED_MAIL_HAUBERK, true);
        dyeablePieces.put(EquipmentItemsME.EREBOR_REINFORCED_COAT, true);
        dyeablePieces.put(EquipmentItemsME.EREBOR_REINFORCED_LEATHER_HAUBERK, true);
        dyeablePieces.put(EquipmentItemsME.EREBOR_LEATHER_LEGGINGS, true);

        dyeablePieces.put(EquipmentItemsME.ORCISH_BELLY_PLATE_CHESTPLATE, true);
        dyeablePieces.put(EquipmentItemsME.ORCISH_LEATHER_CHESTPLATE, true);
        dyeablePieces.put(EquipmentItemsME.ORCISH_LEATHER_SCALE_VEST, true);
        dyeablePieces.put(EquipmentItemsME.ORCISH_LEATHER_STRAP, false);
        dyeablePieces.put(EquipmentItemsME.ORCISH_SCALE_VEST, true);

        dyeablePieces.put(EquipmentItemsME.ORCISH_LEATHER_SKIRT, true);
        dyeablePieces.put(EquipmentItemsME.ORCISH_LEG_BRACER, true);
        dyeablePieces.put(EquipmentItemsME.ORCISH_REINFORCED_LEATHER_SKIRT, true);
        dyeablePieces.put(EquipmentItemsME.RUSTED_ORCISH_REINFORCED_LEATHER_SKIRT, true);
        dyeablePieces.put(EquipmentItemsME.ORCISH_REINFORCED_STRIP_LEATHER_SKIRT, true);
        dyeablePieces.put(EquipmentItemsME.RUSTED_ORCISH_REINFORCED_STRIP_LEATHER_SKIRT, true);
        dyeablePieces.put(EquipmentItemsME.ORCISH_STRIP_LEATHER_SKIRT, true);

        dyeablePieces.put(EquipmentItemsME.ORCISH_BLACK_FUR_BOOTS, true);
        dyeablePieces.put(EquipmentItemsME.ORCISH_BROWN_FUR_BOOTS, true);
        dyeablePieces.put(EquipmentItemsME.ORCISH_GRAY_FUR_BOOTS, true);
        dyeablePieces.put(EquipmentItemsME.ORCISH_TAN_FUR_BOOTS, true);
        dyeablePieces.put(EquipmentItemsME.ORCISH_WHITE_FUR_BOOTS, true);
        dyeablePieces.put(EquipmentItemsME.ORCISH_BRACED_SANDALS, true);
        dyeablePieces.put(EquipmentItemsME.ORCISH_SANDALS, false);

        dyeablePieces.put(EquipmentItemsME.MORDOR_CUIRASS, true);
        dyeablePieces.put(EquipmentItemsME.RUSTED_MORDOR_CUIRASS, true);
        dyeablePieces.put(EquipmentItemsME.MORDOR_DEGRADED_GONDORIAN_CHESTPLATE, true);
        dyeablePieces.put(EquipmentItemsME.MORDOR_LEATHER_CUIRASS, true);
        dyeablePieces.put(EquipmentItemsME.MORDOR_PAINTED_CUIRASS, true);
        dyeablePieces.put(EquipmentItemsME.MORDOR_PAINTED_LEATHER_CUIRASS, true);
        dyeablePieces.put(EquipmentItemsME.MORDOR_REINFORCED_COAT, true);
        dyeablePieces.put(EquipmentItemsME.RUSTED_MORDOR_REINFORCED_COAT, true);

        dyeablePieces.put(EquipmentItemsME.GUNDABAD_BONE_PAULDRON, true);
        dyeablePieces.put(EquipmentItemsME.GUNDABAD_SEEKER_HELMET, true);
        dyeablePieces.put(EquipmentItemsME.GUNDABAD_LEATHER_SCALE_COAT, true);
        dyeablePieces.put(EquipmentItemsME.GUNDABAD_LEATHER_LEGGINGS, true);
        dyeablePieces.put(EquipmentItemsME.GUNDABAD_LACED_BOOTS, true);
        dyeablePieces.put(EquipmentItemsME.GUNDABAD_SKULLCAP_HELMET, true);
        dyeablePieces.put(EquipmentItemsME.GUNDABAD_MAIL_COAT, true);
        dyeablePieces.put(EquipmentItemsME.GUNDABAD_SCREECHER_HELMET, true);
        dyeablePieces.put(EquipmentItemsME.GUNDABAD_SOLDIER_HELMET, true);
        dyeablePieces.put(EquipmentItemsME.GUNDABAD_REINFORCED_LEATHER_SCALE_COAT, true);
        dyeablePieces.put(EquipmentItemsME.GUNDABAD_REINFORCED_LEATHER_VEST, true);
        dyeablePieces.put(EquipmentItemsME.GUNDABAD_CAPTAIN_HELMET, true);

        dyeablePieces.put(EquipmentItemsME.BROADHOOF_GOAT_PADDED_ARMOR, true);
        dyeablePieces.put(EquipmentItemsME.BROADHOOF_GOAT_ORNAMENTED_PADDED_ARMOR, true);
        dyeablePieces.put(EquipmentItemsME.WARG_LEATHER_ARMOR, true);
        dyeablePieces.put(EquipmentItemsME.WARG_REINFORCED_LEATHER_ARMOR, true);
        dyeablePieces.put(EquipmentItemsME.WARG_MORDOR_MAIL_ARMOR, true);

        dyeableBackAttachments.put(BackAttachmentsME.CAPE, true);
        dyeableBackAttachments.put(BackAttachmentsME.CLOAK, false);
        dyeableBackAttachments.put(BackAttachmentsME.SURCOAT, true);
        dyeableBackAttachments.put(BackAttachmentsME.WANDERER_ROBES, true);
        dyeableBackAttachments.put(BackAttachmentsME.BLACK_FUR_CLOAK, true);
        dyeableBackAttachments.put(BackAttachmentsME.BROWN_FUR_CLOAK, true);
        dyeableBackAttachments.put(BackAttachmentsME.GRAY_FUR_CLOAK, true);
        dyeableBackAttachments.put(BackAttachmentsME.TAN_FUR_CLOAK, true);
        dyeableBackAttachments.put(BackAttachmentsME.WHITE_FUR_CLOAK, true);

        dyeableBackAttachments.put(BackAttachmentsME.ORCISH_SHOULDERS, false);

        dyeableHelmetAttachments.put(HelmetAttachmentsME.HOOD, false);
        dyeableHelmetAttachments.put(HelmetAttachmentsME.TALL_HOOD, false);
        dyeableHelmetAttachments.put(HelmetAttachmentsME.BLACK_FUR_HOOD, true);
        dyeableHelmetAttachments.put(HelmetAttachmentsME.BROWN_FUR_HOOD, true);
        dyeableHelmetAttachments.put(HelmetAttachmentsME.GRAY_FUR_HOOD, true);
        dyeableHelmetAttachments.put(HelmetAttachmentsME.TAN_FUR_HOOD, true);
        dyeableHelmetAttachments.put(HelmetAttachmentsME.WHITE_FUR_HOOD, true);

        dyeablePieces.put(EquipmentItemsME.BROADHOOF_GOAT_PADDED_ARMOR, true);
        dyeablePieces.put(EquipmentItemsME.BROADHOOF_GOAT_ORNAMENTED_PADDED_ARMOR, true);
    }
}
