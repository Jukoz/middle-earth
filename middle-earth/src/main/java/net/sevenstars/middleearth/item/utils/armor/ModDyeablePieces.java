package net.sevenstars.middleearth.item.utils.armor;

import net.sevenstars.middleearth.item.EquipmentItemsME;
import net.sevenstars.middleearth.item.utils.armor.capes.ModCapes;
import net.sevenstars.middleearth.item.utils.armor.hoods.ModHoods;
import net.minecraft.item.Item;

import java.util.HashMap;

public class ModDyeablePieces {

    public static HashMap<Item, Boolean> dyeablePieces = new HashMap<>();
    public static HashMap<ModCapes, Boolean> dyeableCapes = new HashMap<>();
    public static HashMap<ModHoods, Boolean> dyeableHoods = new HashMap<>();

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

        dyeableCapes.put(ModCapes.CAPE, true);
        dyeableCapes.put(ModCapes.CLOAK, false);
        dyeableCapes.put(ModCapes.SURCOAT, true);
        dyeableCapes.put(ModCapes.WANDERER_ROBES, true);
        dyeableCapes.put(ModCapes.BLACK_FUR_CLOAK, true);
        dyeableCapes.put(ModCapes.BROWN_FUR_CLOAK, true);
        dyeableCapes.put(ModCapes.GRAY_FUR_CLOAK, true);
        dyeableCapes.put(ModCapes.TAN_FUR_CLOAK, true);
        dyeableCapes.put(ModCapes.WHITE_FUR_CLOAK, true);

        dyeableCapes.put(ModCapes.ORCISH_SHOULDERS, false);

        dyeableHoods.put(ModHoods.HOOD, false);
        dyeableHoods.put(ModHoods.TALL_HOOD, false);
        dyeableHoods.put(ModHoods.BLACK_FUR_HOOD, true);
        dyeableHoods.put(ModHoods.BROWN_FUR_HOOD, true);
        dyeableHoods.put(ModHoods.GRAY_FUR_HOOD, true);
        dyeableHoods.put(ModHoods.TAN_FUR_HOOD, true);
        dyeableHoods.put(ModHoods.WHITE_FUR_HOOD, true);

        dyeablePieces.put(EquipmentItemsME.BROADHOOF_GOAT_PADDED_ARMOR, true);
        dyeablePieces.put(EquipmentItemsME.BROADHOOF_GOAT_ORNAMENTED_PADDED_ARMOR, true);
    }
}
