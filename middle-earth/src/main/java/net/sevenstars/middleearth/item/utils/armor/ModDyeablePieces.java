package net.sevenstars.middleearth.item.utils.armor;

import net.sevenstars.middleearth.item.ModEquipmentItems;
import net.sevenstars.middleearth.item.utils.armor.capes.ModCapes;
import net.sevenstars.middleearth.item.utils.armor.hoods.ModHoods;
import net.minecraft.item.Item;

import java.util.HashMap;

public class ModDyeablePieces {

    public static HashMap<Item, Boolean> dyeablePieces = new HashMap<>();
    public static HashMap<ModCapes, Boolean> dyeableCapes = new HashMap<>();
    public static HashMap<ModHoods, Boolean> dyeableHoods = new HashMap<>();

    public static void addDyeablePieces() {
        dyeablePieces.put(ModEquipmentItems.BYCOCKET, false);
        dyeablePieces.put(ModEquipmentItems.WANDERER_HAT, true);
        dyeablePieces.put(ModEquipmentItems.ARMING_COAT, true);
        dyeablePieces.put(ModEquipmentItems.ARMING_SKIRT, true);
        dyeablePieces.put(ModEquipmentItems.LEATHER_SKULLCAP, false);
        dyeablePieces.put(ModEquipmentItems.GAMBESON_CAP, false);
        dyeablePieces.put(ModEquipmentItems.GAMBESON_COWL, true);
        dyeablePieces.put(ModEquipmentItems.GAMBESON, true);
        dyeablePieces.put(ModEquipmentItems.LEATHER_VEST, false);
        dyeablePieces.put(ModEquipmentItems.LEATHER_SCALE_VEST, true);

        dyeablePieces.put(ModEquipmentItems.SHIRRIFF_HAT, true);

        dyeablePieces.put(ModEquipmentItems.GONDORIAN_LEATHER_CUIRASS, true);
        dyeablePieces.put(ModEquipmentItems.GONDORIAN_LEATHER_CHESTPLATE, true);

        dyeablePieces.put(ModEquipmentItems.ROHIRRIC_REINFORCED_COAT, true);
        dyeablePieces.put(ModEquipmentItems.ROHIRRIC_LEATHER_VEST, true);
        dyeablePieces.put(ModEquipmentItems.ROHIRRIC_LEATHER_SCALE_VEST, true);
        dyeablePieces.put(ModEquipmentItems.ROHIRRIC_GAMBESON, true);
        dyeablePieces.put(ModEquipmentItems.ROHIRRIC_REINFORCED_LEATHER_VEST, true);
        dyeablePieces.put(ModEquipmentItems.ROHIRRIC_BRACED_MAIL_SHIRT, true);

        dyeablePieces.put(ModEquipmentItems.DALISH_ARMING_COAT_BLACK_FUR, true);
        dyeablePieces.put(ModEquipmentItems.DALISH_ARMING_COAT_BROWN_FUR, true);
        dyeablePieces.put(ModEquipmentItems.DALISH_ARMING_COAT_TAN_FUR, true);

        dyeablePieces.put(ModEquipmentItems.ELVEN_ARMING_COAT, true);
        dyeablePieces.put(ModEquipmentItems.ELVEN_ARMING_SKIRT, true);
        dyeablePieces.put(ModEquipmentItems.ELVEN_OPEN_ARMING_SKIRT, true);

        dyeablePieces.put(ModEquipmentItems.DWARVEN_MINER_HELMET, true);
        dyeablePieces.put(ModEquipmentItems.DWARVEN_GAMBESON, true);
        dyeablePieces.put(ModEquipmentItems.DWARVEN_MINER_GAMBESON, true);

        dyeablePieces.put(ModEquipmentItems.LONGBEARD_WANDERER_COAT, true);
        dyeablePieces.put(ModEquipmentItems.LONGBEARD_LEATHER_HAUBERK, true);
        dyeablePieces.put(ModEquipmentItems.LONGBEARD_PARTISAN_OUTFIT, true);
        dyeablePieces.put(ModEquipmentItems.LONGBEARD_REINFORCED_LEATHER_HAUBERK, true);
        dyeablePieces.put(ModEquipmentItems.LONGBEARD_LEATHER_LEGGINGS, true);

        dyeablePieces.put(ModEquipmentItems.EREBOR_LEATHER_HELMET, true);
        dyeablePieces.put(ModEquipmentItems.EREBOR_BRACED_LEATHER_HELMET, true);
        dyeablePieces.put(ModEquipmentItems.EREBOR_NASAL_LEATHER_HELMET, true);
        dyeablePieces.put(ModEquipmentItems.EREBOR_BRAWLER_CHESTPLATE, true);
        dyeablePieces.put(ModEquipmentItems.EREBOR_LONG_COAT, true);
        dyeablePieces.put(ModEquipmentItems.EREBOR_PADDED_MAIL_HAUBERK, true);
        dyeablePieces.put(ModEquipmentItems.EREBOR_REINFORCED_COAT, true);
        dyeablePieces.put(ModEquipmentItems.EREBOR_REINFORCED_LEATHER_HAUBERK, true);
        dyeablePieces.put(ModEquipmentItems.EREBOR_LEATHER_LEGGINGS, true);

        dyeablePieces.put(ModEquipmentItems.ORCISH_BELLY_PLATE_CHESTPLATE, true);
        dyeablePieces.put(ModEquipmentItems.ORCISH_LEATHER_CHESTPLATE, true);
        dyeablePieces.put(ModEquipmentItems.ORCISH_LEATHER_SCALE_VEST, true);
        dyeablePieces.put(ModEquipmentItems.ORCISH_LEATHER_STRAP, false);
        dyeablePieces.put(ModEquipmentItems.ORCISH_SCALE_VEST, true);

        dyeablePieces.put(ModEquipmentItems.ORCISH_LEATHER_SKIRT, true);
        dyeablePieces.put(ModEquipmentItems.ORCISH_LEG_BRACER, true);
        dyeablePieces.put(ModEquipmentItems.ORCISH_REINFORCED_LEATHER_SKIRT, true);
        dyeablePieces.put(ModEquipmentItems.RUSTED_ORCISH_REINFORCED_LEATHER_SKIRT, true);
        dyeablePieces.put(ModEquipmentItems.ORCISH_REINFORCED_STRIP_LEATHER_SKIRT, true);
        dyeablePieces.put(ModEquipmentItems.RUSTED_ORCISH_REINFORCED_STRIP_LEATHER_SKIRT, true);
        dyeablePieces.put(ModEquipmentItems.ORCISH_STRIP_LEATHER_SKIRT, true);

        dyeablePieces.put(ModEquipmentItems.ORCISH_BLACK_FUR_BOOTS, true);
        dyeablePieces.put(ModEquipmentItems.ORCISH_BROWN_FUR_BOOTS, true);
        dyeablePieces.put(ModEquipmentItems.ORCISH_GRAY_FUR_BOOTS, true);
        dyeablePieces.put(ModEquipmentItems.ORCISH_TAN_FUR_BOOTS, true);
        dyeablePieces.put(ModEquipmentItems.ORCISH_WHITE_FUR_BOOTS, true);
        dyeablePieces.put(ModEquipmentItems.ORCISH_BRACED_SANDALS, true);
        dyeablePieces.put(ModEquipmentItems.ORCISH_SANDALS, false);

        dyeablePieces.put(ModEquipmentItems.MORDOR_CUIRASS, true);
        dyeablePieces.put(ModEquipmentItems.RUSTED_MORDOR_CUIRASS, true);
        dyeablePieces.put(ModEquipmentItems.MORDOR_DEGRADED_GONDORIAN_CHESTPLATE, true);
        dyeablePieces.put(ModEquipmentItems.MORDOR_LEATHER_CUIRASS, true);
        dyeablePieces.put(ModEquipmentItems.MORDOR_PAINTED_CUIRASS, true);
        dyeablePieces.put(ModEquipmentItems.MORDOR_PAINTED_LEATHER_CUIRASS, true);
        dyeablePieces.put(ModEquipmentItems.MORDOR_REINFORCED_COAT, true);
        dyeablePieces.put(ModEquipmentItems.RUSTED_MORDOR_REINFORCED_COAT, true);

        dyeablePieces.put(ModEquipmentItems.GUNDABAD_BONE_PAULDRON, true);
        dyeablePieces.put(ModEquipmentItems.GUNDABAD_SEEKER_HELMET, true);
        dyeablePieces.put(ModEquipmentItems.GUNDABAD_LEATHER_SCALE_COAT, true);
        dyeablePieces.put(ModEquipmentItems.GUNDABAD_LEATHER_LEGGINGS, true);
        dyeablePieces.put(ModEquipmentItems.GUNDABAD_LACED_BOOTS, true);
        dyeablePieces.put(ModEquipmentItems.GUNDABAD_SKULLCAP_HELMET, true);
        dyeablePieces.put(ModEquipmentItems.GUNDABAD_MAIL_COAT, true);
        dyeablePieces.put(ModEquipmentItems.GUNDABAD_SCREECHER_HELMET, true);
        dyeablePieces.put(ModEquipmentItems.GUNDABAD_SOLDIER_HELMET, true);
        dyeablePieces.put(ModEquipmentItems.GUNDABAD_REINFORCED_LEATHER_SCALE_COAT, true);
        dyeablePieces.put(ModEquipmentItems.GUNDABAD_REINFORCED_LEATHER_VEST, true);
        dyeablePieces.put(ModEquipmentItems.GUNDABAD_CAPTAIN_HELMET, true);

        dyeablePieces.put(ModEquipmentItems.BROADHOOF_GOAT_PADDED_ARMOR, true);
        dyeablePieces.put(ModEquipmentItems.BROADHOOF_GOAT_ORNAMENTED_PADDED_ARMOR, true);
        dyeablePieces.put(ModEquipmentItems.WARG_LEATHER_ARMOR, true);
        dyeablePieces.put(ModEquipmentItems.WARG_REINFORCED_LEATHER_ARMOR, true);
        dyeablePieces.put(ModEquipmentItems.WARG_MORDOR_MAIL_ARMOR, true);

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

        dyeablePieces.put(ModEquipmentItems.BROADHOOF_GOAT_PADDED_ARMOR, true);
        dyeablePieces.put(ModEquipmentItems.BROADHOOF_GOAT_ORNAMENTED_PADDED_ARMOR, true);
    }
}
