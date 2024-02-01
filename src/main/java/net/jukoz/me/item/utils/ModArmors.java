package net.jukoz.me.item.utils;

import net.jukoz.me.client.model.equipment.CustomHelmetModel;
import net.jukoz.me.client.model.equipment.RohanScaleHelmetArmorModel;
import net.jukoz.me.item.ModEquipmentItems;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemConvertible;

public enum ModArmors {
    GAMBESON("gambeson", null, null,
            false, false, ModEquipmentItems.GAMBESON),

    CLOAK("cloak", null, null,
            false, true, ModEquipmentItems.CLOAK, ModEquipmentItems.CLOAK_HOOD),

    TUNIC_CLOAK("tunic_cloak", null, null,
            false, true, ModEquipmentItems.TUNIC_CLOAK),

    FUR_CLOAK("fur_cloak", null, null,
            false, false, ModEquipmentItems.FUR_CLOAK, ModEquipmentItems.FUR_CLOAK_HOOD),

    CHAINMAIL_FUR_CLOAK("chainmail_fur_cloak", null, null,
            false, false, ModEquipmentItems.CHAINMAIL_FUR_CLOAK),

    NAZGUL_CLOAK("nazgul_cloak", null, null,
            false, false, ModEquipmentItems.NAZGUL_CLOAK_HOOD, ModEquipmentItems.NAZGUL_CLOAK, ModEquipmentItems.NAZGUL_PANTS, ModEquipmentItems.NAZGUL_BOOTS),

    MORDOR_ORC_ARMOR("mordor_orc_armor", null, null,
            false, false, ModEquipmentItems.MORDOR_ORC_HELMET, ModEquipmentItems.MORDOR_ORC_CHESTPLATE, ModEquipmentItems.MORDOR_ORC_LEGGINGS, ModEquipmentItems.MORDOR_ORC_BOOTS),

    ROHAN_MAIL_ARMOR("rohan_mail", null, null,
            true, false, ModEquipmentItems.ROHAN_MAIL_HELMET, ModEquipmentItems.ROHAN_MAIL_CHESTPLATE, ModEquipmentItems.ROHAN_MAIL_LEGGINGS, ModEquipmentItems.ROHAN_MAIL_BOOTS),

    ROHAN_SCALE_ARMOR("rohan_scale", new RohanScaleHelmetArmorModel<>(RohanScaleHelmetArmorModel.getTexturedModelData().createModel()), null,
            true, false, ModEquipmentItems.ROHAN_SCALE_HELMET, ModEquipmentItems.ROHAN_SCALE_CHESTPLATE, ModEquipmentItems.ROHAN_SCALE_LEGGINGS, ModEquipmentItems.ROHAN_SCALE_BOOTS),
    ;

    private final String name;
    private final CustomHelmetModel<LivingEntity> helmetModel;
    private final CustomHelmetModel<LivingEntity> chestPlateModel;
    private final ItemConvertible[] items;
    private final boolean hasCape;
    private final boolean dyeable;

    ModArmors(String name, CustomHelmetModel<LivingEntity> helmetModel, CustomHelmetModel<LivingEntity> chestPlateModel,
              boolean hasCape, boolean dyeable, ItemConvertible... items) {
        this.name = name;
        this.helmetModel = helmetModel;
        this.chestPlateModel = chestPlateModel;
        this.hasCape = hasCape;
        this.dyeable = dyeable;
        this.items = items;
    }

    public String getSimpleName() {
        return this.name;
    }

    public CustomHelmetModel<LivingEntity> getHelmetModel(){
        return this.helmetModel;
    }

    public CustomHelmetModel<LivingEntity> getChestPlateModel(){
        return this.chestPlateModel;
    }

    public ItemConvertible[] getItems(){
        return this.items;
    }

    public boolean hasCape(){
        return this.hasCape;
    }

    public boolean isDyeable(){
        return this.dyeable;
    }
}
