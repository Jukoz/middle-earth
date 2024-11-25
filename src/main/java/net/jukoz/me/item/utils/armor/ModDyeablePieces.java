package net.jukoz.me.item.utils.armor;

import net.jukoz.me.item.ModEquipmentItems;
import net.jukoz.me.item.utils.armor.capes.ModCapes;
import net.jukoz.me.item.utils.armor.hoods.ModHoods;
import net.minecraft.item.Item;

import java.util.HashMap;

public class ModDyeablePieces {

    public static HashMap<Item, Boolean> dyeablePieces = new HashMap<>();
    public static HashMap<ModCapes, Boolean> dyeableCapes = new HashMap<>();
    public static HashMap<ModHoods, Boolean> dyeableHoods = new HashMap<>();

    public static void addDyeablePieces() {
        dyeablePieces.put(ModEquipmentItems.BYCOCKET, false);
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

        dyeablePieces.put(ModEquipmentItems.ROHIRRIC_COAT, true);
        dyeablePieces.put(ModEquipmentItems.ROHIRRIC_REINFORCED_COAT, true);
        dyeablePieces.put(ModEquipmentItems.ROHIRRIC_LEATHER_VEST, true);
        dyeablePieces.put(ModEquipmentItems.ROHIRRIC_LEATHER_SCALE_VEST, true);
        dyeablePieces.put(ModEquipmentItems.ROHIRRIC_GAMBESON, true);
        dyeablePieces.put(ModEquipmentItems.ROHIRRIC_MAIL_SHIRT, true);
        dyeablePieces.put(ModEquipmentItems.ROHIRRIC_MAIL_SHIRT_OPEN, true);

        dyeablePieces.put(ModEquipmentItems.DALISH_ARMING_COAT_BLACK_FUR, true);
        dyeablePieces.put(ModEquipmentItems.DALISH_ARMING_COAT_BROWN_FUR, true);
        dyeablePieces.put(ModEquipmentItems.DALISH_ARMING_COAT_TAN_FUR, true);

        dyeablePieces.put(ModEquipmentItems.ELVEN_ARMING_COAT, true);
        dyeablePieces.put(ModEquipmentItems.ELVEN_ARMING_SKIRT, true);
        dyeablePieces.put(ModEquipmentItems.ELVEN_OPEN_ARMING_SKIRT, true);
        dyeablePieces.put(ModEquipmentItems.BROADHOOF_GOAT_PADDED_ARMOR, true);
        dyeablePieces.put(ModEquipmentItems.BROADHOOF_GOAT_ORNAMENTED_PADDED_ARMOR, true);

        dyeableCapes.put(ModCapes.BASE_CAPE, true);
        dyeableCapes.put(ModCapes.BASE_CLOAK, false);
        dyeableCapes.put(ModCapes.SURCOAT, true);
        dyeableCapes.put(ModCapes.BLACK_FUR_CLOAK, true);
        dyeableCapes.put(ModCapes.BROWN_FUR_CLOAK, true);
        dyeableCapes.put(ModCapes.GRAY_FUR_CLOAK, true);
        dyeableCapes.put(ModCapes.TAN_FUR_CLOAK, true);
        dyeableCapes.put(ModCapes.WHITE_FUR_CLOAK, true);

        dyeableHoods.put(ModHoods.BASE_HOOD, true);
        dyeableHoods.put(ModHoods.BLACK_FUR_HOOD, true);
        dyeableHoods.put(ModHoods.BROWN_FUR_HOOD, true);
        dyeableHoods.put(ModHoods.GRAY_FUR_HOOD, true);
        dyeableHoods.put(ModHoods.TAN_FUR_HOOD, true);
        dyeableHoods.put(ModHoods.WHITE_FUR_HOOD, true);
    }
}
