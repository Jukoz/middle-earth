package net.jukoz.me.item.utils;

import net.jukoz.me.client.model.equipment.chest.CustomChestplateModel;
import net.jukoz.me.client.model.equipment.chest.MistyUrukPlateChestplateArmorModel;
import net.jukoz.me.client.model.equipment.chest.MistyUrukScaleChestplateArmorModel;
import net.jukoz.me.client.model.equipment.head.*;
import net.jukoz.me.item.ModEquipmentItems;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemConvertible;

public enum ModArmors {
    GAMBESON("gambeson", null, null,
            false, false, false, ModEquipmentItems.GAMBESON),

    CLOAK("cloak", null, null,
            false, false, true, ModEquipmentItems.CLOAK, ModEquipmentItems.CLOAK_HOOD),

    TUNIC_CLOAK("tunic_cloak", null, null,
            false, false, true, ModEquipmentItems.TUNIC_CLOAK),

    FUR_CLOAK("fur_cloak", null, null,
            false, true, false, ModEquipmentItems.FUR_CLOAK, ModEquipmentItems.FUR_CLOAK_HOOD),

    CHAINMAIL_FUR_CLOAK("chainmail_fur_cloak", null, null,
            false, true, false, ModEquipmentItems.CHAINMAIL_FUR_CLOAK),

    NAZGUL_CLOAK("nazgul_cloak", null, null,
            false, true, false, ModEquipmentItems.NAZGUL_CLOAK_HOOD, ModEquipmentItems.NAZGUL_CLOAK, ModEquipmentItems.NAZGUL_PANTS, ModEquipmentItems.NAZGUL_BOOTS),
    
    ROHAN_MAIL_ARMOR("rohan_mail", null, null,
            true, false, false, ModEquipmentItems.ROHAN_MAIL_HELMET, ModEquipmentItems.ROHAN_MAIL_CHESTPLATE, ModEquipmentItems.ROHAN_MAIL_LEGGINGS, ModEquipmentItems.ROHAN_MAIL_BOOTS),

    ROHAN_SCALE_ARMOR("rohan_scale", new RohanScaleHelmetArmorModel<>(RohanScaleHelmetArmorModel.getTexturedModelData().createModel()), null,
            true, false, false, ModEquipmentItems.ROHAN_SCALE_HELMET, ModEquipmentItems.ROHAN_SCALE_CHESTPLATE, ModEquipmentItems.ROHAN_SCALE_LEGGINGS, ModEquipmentItems.ROHAN_SCALE_BOOTS),
    
    LONGBEARD_MAIL_ARMOR("longbeard_mail", null, null,
            false, false, false, ModEquipmentItems.LONGBEARD_MAIL_HELMET, ModEquipmentItems.LONGBEARD_MAIL_CHESTPLATE, ModEquipmentItems.LONGBEARD_MAIL_LEGGINGS, ModEquipmentItems.LONGBEARD_MAIL_BOOTS),

    LONGBEARD_SCALE_ARMOR("longbeard_scale", null, null,
            false, false, false, ModEquipmentItems.LONGBEARD_SCALE_HELMET, ModEquipmentItems.LONGBEARD_SCALE_CHESTPLATE, ModEquipmentItems.LONGBEARD_SCALE_LEGGINGS, ModEquipmentItems.LONGBEARD_SCALE_BOOTS),

    LONGBEARD_PLATE_ARMOR("longbeard_plate", null, null,
            true, false, false, ModEquipmentItems.LONGBEARD_PLATE_HELMET, ModEquipmentItems.LONGBEARD_PLATE_CHESTPLATE, ModEquipmentItems.LONGBEARD_PLATE_LEGGINGS, ModEquipmentItems.LONGBEARD_PLATE_BOOTS),
    
    LONGBEARD_COMMANDER_ARMOR("longbeard_commander", null, null,
            true, false, false, ModEquipmentItems.LONGBEARD_COMMANDER_HELMET, ModEquipmentItems.LONGBEARD_COMMANDER_CHESTCOMMANDER, ModEquipmentItems.LONGBEARD_COMMANDER_LEGGINGS, ModEquipmentItems.LONGBEARD_COMMANDER_BOOTS),
    
    LOTHLORIEN_MAIL_ARMOR("lothlorien_mail", null, null,
            false, false, false, ModEquipmentItems.LOTHLORIEN_MAIL_HELMET, ModEquipmentItems.LOTHLORIEN_MAIL_CHESTPLATE, ModEquipmentItems.LOTHLORIEN_MAIL_LEGGINGS, ModEquipmentItems.LOTHLORIEN_MAIL_BOOTS),

    LOTHLORIEN_SCALE_ARMOR("lothlorien_scale", new LothlorienHelmetArmorModel<>(LothlorienHelmetArmorModel.getTexturedModelData().createModel(), "scale"), null,
            false, false, false, ModEquipmentItems.LOTHLORIEN_SCALE_HELMET, ModEquipmentItems.LOTHLORIEN_SCALE_CHESTPLATE, ModEquipmentItems.LOTHLORIEN_SCALE_LEGGINGS, ModEquipmentItems.LOTHLORIEN_SCALE_BOOTS),

    LOTHLORIEN_PLATE_ARMOR("lothlorien_plate", new LothlorienHelmetArmorModel<>(LothlorienHelmetArmorModel.getTexturedModelData().createModel(), "plate"), null,
            true, true, false, ModEquipmentItems.LOTHLORIEN_PLATE_HELMET, ModEquipmentItems.LOTHLORIEN_PLATE_CHESTPLATE, ModEquipmentItems.LOTHLORIEN_PLATE_LEGGINGS, ModEquipmentItems.LOTHLORIEN_PLATE_BOOTS),

    MORDOR_URUK_PLATE_ARMOR("mordor_uruk_plate", new MordorUrukPlateHelmetArmorModel<>(MordorUrukPlateHelmetArmorModel.getTexturedModelData().createModel()), null,
            false, false, false, ModEquipmentItems.MORDOR_URUK_PLATE_HELMET, ModEquipmentItems.MORDOR_URUK_PLATE_CHESTPLATE, ModEquipmentItems.MORDOR_URUK_PLATE_LEGGINGS, ModEquipmentItems.MORDOR_URUK_PLATE_BOOTS),

    MISTY_ORC_MAIL_ARMOR("misty_orc_mail", null, null,
            false, false, false, ModEquipmentItems.MISTY_ORC_MAIL_HELMET, ModEquipmentItems.MISTY_ORC_MAIL_CHESTPLATE, ModEquipmentItems.MISTY_ORC_MAIL_LEGGINGS, ModEquipmentItems.MISTY_ORC_MAIL_BOOTS),
    
    MISTY_URUK_SCALE_ARMOR("misty_uruk_scale",  new MistyUrukScaleHelmetArmorModel<>(MistyUrukScaleHelmetArmorModel.getTexturedModelData().createModel()), new MistyUrukScaleChestplateArmorModel<>(MistyUrukScaleChestplateArmorModel.getTexturedModelData().createModel()),
            false, false, false, ModEquipmentItems.MISTY_URUK_SCALE_HELMET, ModEquipmentItems.MISTY_URUK_SCALE_CHESTPLATE, ModEquipmentItems.MISTY_URUK_SCALE_LEGGINGS, ModEquipmentItems.MISTY_URUK_SCALE_BOOTS),

    MISTY_URUK_PLATE_ARMOR("misty_uruk_plate",new MistyUrukPlateHelmetArmorModel<>(MistyUrukPlateHelmetArmorModel.getTexturedModelData().createModel()), new MistyUrukPlateChestplateArmorModel<>(MistyUrukPlateChestplateArmorModel.getTexturedModelData().createModel()),
            false, false, false, ModEquipmentItems.MISTY_URUK_PLATE_HELMET, ModEquipmentItems.MISTY_URUK_PLATE_CHESTPLATE, ModEquipmentItems.MISTY_URUK_PLATE_LEGGINGS, ModEquipmentItems.MISTY_URUK_PLATE_BOOTS),
    ;

    private final String name;
    private final CustomHelmetModel<LivingEntity> helmetModel;
    private final CustomChestplateModel<LivingEntity> chestPlateModel;
    private final ItemConvertible[] items;
    private final boolean hasCape;
    private final boolean hasHood;
    private final boolean dyeable;

    ModArmors(String name, CustomHelmetModel<LivingEntity> helmetModel, CustomChestplateModel<LivingEntity> chestPlateModel,
              boolean hasCape, boolean hasHood, boolean dyeable, ItemConvertible... items) {
        this.name = name;
        this.helmetModel = helmetModel;
        this.chestPlateModel = chestPlateModel;
        this.hasCape = hasCape;
        this.hasHood = hasHood;
        this.dyeable = dyeable;
        this.items = items;
    }

    public String getSimpleName() {
        return this.name;
    }

    public CustomHelmetModel<LivingEntity> getHelmetModel(){
        return this.helmetModel;
    }

    public CustomChestplateModel<LivingEntity> getChestPlateModel(){
        return this.chestPlateModel;
    }

    public ItemConvertible[] getItems(){
        return this.items;
    }

    public boolean hasCape(){
        return this.hasCape;
    }

    public boolean hasHood(){
        return this.hasHood;
    }

    public boolean isDyeable(){
        return this.dyeable;
    }
}
