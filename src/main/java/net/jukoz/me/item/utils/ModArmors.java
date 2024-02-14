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
            true, false, false, false, true, ModEquipmentItems.GAMBESON),

    CLOAK("cloak", null, null,
            false, true, true, false, true, ModEquipmentItems.CLOAK, ModEquipmentItems.CLOAK_HOOD),

    TUNIC_CLOAK("tunic_cloak", null, null,
            false, true, true, false, true, ModEquipmentItems.TUNIC_CLOAK),

    FUR_CLOAK("fur_cloak", null, null,
            false, true, true, false, false, ModEquipmentItems.FUR_CLOAK, ModEquipmentItems.FUR_CLOAK_HOOD),

    CHAINMAIL_FUR_CLOAK("chainmail_fur_cloak", null, null,
            false, true, true, true, false, ModEquipmentItems.CHAINMAIL_FUR_CLOAK),

    RUSTY_KETTLE_HELMET("rusty_kettle_helmet", new KettleHelmetArmorModel<>(KettleHelmetArmorModel.getTexturedModelData().createModel(), "rusty_kettle_helmet"), null,
            false, true, false, false, false, ModEquipmentItems.RUSTY_KETTLE_HELMET),

    KETTLE_HELMET("kettle_helmet", new KettleHelmetArmorModel<>(KettleHelmetArmorModel.getTexturedModelData().createModel(), "kettle_helmet"), null,
            false, true, false, false, false, ModEquipmentItems.KETTLE_HELMET),

    NAZGUL_CLOAK("nazgul_cloak", null, null,
            false, true, true, false, false, ModEquipmentItems.NAZGUL_CLOAK_HOOD, ModEquipmentItems.NAZGUL_CLOAK, ModEquipmentItems.NAZGUL_PANTS, ModEquipmentItems.NAZGUL_BOOTS),

    HOBBIT_SHIRRIF_HAT_GREEN("hobbit_shirrif_hat_green", new HobbitShirrifHatArmorModel<>(HobbitShirrifHatArmorModel.getTexturedModelData().createModel(), "green"), null,
            false, false, false, false, false, ModEquipmentItems.HOBBIT_SHIRRIF_HAT_GREEN),
    HOBBIT_SHIRRIF_HAT_BROWN("hobbit_shirrif_hat_brown", new HobbitShirrifHatArmorModel<>(HobbitShirrifHatArmorModel.getTexturedModelData().createModel(), "brown"), null,
            false, false, false, false, false, ModEquipmentItems.HOBBIT_SHIRRIF_HAT_BROWN),
    
    GONDORIAN_MAIL_ARMOR("gondorian_mail", new GondorianMailHelmetArmorModel<>(GondorianMailHelmetArmorModel.getTexturedModelData().createModel()), null,
            true, true, false, false, false, ModEquipmentItems.GONDORIAN_MAIL_HELMET, ModEquipmentItems.GONDORIAN_MAIL_CHESTPLATE, ModEquipmentItems.GONDORIAN_MAIL_LEGGINGS, ModEquipmentItems.GONDORIAN_MAIL_BOOTS),

    GONDORIAN_REINFORCED_MAIL_ARMOR("gondorian_reinforced_mail", new GondorianReinforcedMailHelmetArmorModel<>(GondorianReinforcedMailHelmetArmorModel.getTexturedModelData().createModel()), null,
            true, true, false, false, false, ModEquipmentItems.GONDORIAN_REINFORCED_MAIL_HELMET, ModEquipmentItems.GONDORIAN_REINFORCED_MAIL_CHESTPLATE, ModEquipmentItems.GONDORIAN_REINFORCED_MAIL_LEGGINGS, ModEquipmentItems.GONDORIAN_REINFORCED_MAIL_BOOTS),

    GONDORIAN_PLATE_ARMOR("gondorian_plate", new GondorianPlateHelmetArmorModel<>(GondorianPlateHelmetArmorModel.getTexturedModelData().createModel()), null,
            true, true, false, false, false, ModEquipmentItems.GONDORIAN_PLATE_HELMET, ModEquipmentItems.GONDORIAN_PLATE_CHESTPLATE, ModEquipmentItems.GONDORIAN_PLATE_LEGGINGS, ModEquipmentItems.GONDORIAN_PLATE_BOOTS),
    
    GONDORIAN_CITADEL_GUARD_ARMOR("gondorian_citadel_guard", new GondorianCitadelGuardrHelmetArmorModel<>(GondorianCitadelGuardrHelmetArmorModel.getTexturedModelData().createModel()), null,
            true, true, true, false, false, ModEquipmentItems.GONDORIAN_CITADEL_GUARD_HELMET, ModEquipmentItems.GONDORIAN_CITADEL_GUARD_CHESTPLATE, ModEquipmentItems.GONDORIAN_CITADEL_GUARD_LEGGINGS, ModEquipmentItems.GONDORIAN_CITADEL_GUARD_BOOTS),

    GONDORIAN_FOUNTAIN_GUARD_ARMOR("gondorian_fountain_guard", new GondorianFountainGuardrHelmetArmorModel<>(GondorianFountainGuardrHelmetArmorModel.getTexturedModelData().createModel()), null,
            true, true, true, false, false, ModEquipmentItems.GONDORIAN_FOUNTAIN_GUARD_HELMET, ModEquipmentItems.GONDORIAN_FOUNTAIN_GUARD_CHESTPLATE, ModEquipmentItems.GONDORIAN_FOUNTAIN_GUARD_LEGGINGS, ModEquipmentItems.GONDORIAN_FOUNTAIN_GUARD_BOOTS),

    ROHIRRIC_MAIL_ARMOR("rohirric_mail", null, null,
            true, true, true, false, false, ModEquipmentItems.ROHIRRIC_MAIL_HELMET, ModEquipmentItems.ROHIRRIC_MAIL_CHESTPLATE, ModEquipmentItems.ROHIRRIC_MAIL_LEGGINGS, ModEquipmentItems.ROHIRRIC_MAIL_BOOTS),

    ROHIRRIC_SCALE_ARMOR("rohirric_scale", new RohirricScaleHelmetArmorModel<>(RohirricScaleHelmetArmorModel.getTexturedModelData().createModel()), null,
            true, true, true, false, false, ModEquipmentItems.ROHIRRIC_SCALE_HELMET, ModEquipmentItems.ROHIRRIC_SCALE_CHESTPLATE, ModEquipmentItems.ROHIRRIC_SCALE_LEGGINGS, ModEquipmentItems.ROHIRRIC_SCALE_BOOTS),

    ROHIRRIC_PLATE_ARMOR("rohirric_plate", new RohirricPlateHelmetArmorModel<>(RohirricPlateHelmetArmorModel.getTexturedModelData().createModel()), null,
            true, true, false, false, false, ModEquipmentItems.ROHIRRIC_PLATE_HELMET, ModEquipmentItems.ROHIRRIC_PLATE_CHESTPLATE, ModEquipmentItems.ROHIRRIC_PLATE_LEGGINGS, ModEquipmentItems.ROHIRRIC_PLATE_BOOTS),

    LONGBEARD_MAIL_ARMOR("longbeard_mail", null, null,
            true, true, false, false, false, ModEquipmentItems.LONGBEARD_MAIL_HELMET, ModEquipmentItems.LONGBEARD_MAIL_CHESTPLATE, ModEquipmentItems.LONGBEARD_MAIL_LEGGINGS, ModEquipmentItems.LONGBEARD_MAIL_BOOTS),

    LONGBEARD_SCALE_ARMOR("longbeard_scale", null, null,
            true, true, false, false, false, ModEquipmentItems.LONGBEARD_SCALE_HELMET, ModEquipmentItems.LONGBEARD_SCALE_CHESTPLATE, ModEquipmentItems.LONGBEARD_SCALE_LEGGINGS, ModEquipmentItems.LONGBEARD_SCALE_BOOTS),

    LONGBEARD_PLATE_ARMOR("longbeard_plate", null, null,
            true, true, false, false, false, ModEquipmentItems.LONGBEARD_PLATE_HELMET, ModEquipmentItems.LONGBEARD_PLATE_CHESTPLATE, ModEquipmentItems.LONGBEARD_PLATE_LEGGINGS, ModEquipmentItems.LONGBEARD_PLATE_BOOTS),
    
    LONGBEARD_COMMANDER_ARMOR("longbeard_commander", null, null,
            true, true, false, false, false, ModEquipmentItems.LONGBEARD_COMMANDER_HELMET, ModEquipmentItems.LONGBEARD_COMMANDER_CHESTCOMMANDER, ModEquipmentItems.LONGBEARD_COMMANDER_LEGGINGS, ModEquipmentItems.LONGBEARD_COMMANDER_BOOTS),
    
    LORIEN_MAIL_ARMOR("lorien_mail", null, null,
            true, true, false, false, false, ModEquipmentItems.LORIEN_MAIL_HELMET, ModEquipmentItems.LORIEN_MAIL_CHESTPLATE, ModEquipmentItems.LORIEN_MAIL_LEGGINGS, ModEquipmentItems.LORIEN_MAIL_BOOTS),

    LORIEN_SCALE_ARMOR("lorien_scale", new LorienHelmetArmorModel<>(LorienHelmetArmorModel.getTexturedModelData().createModel(), "scale"), null,
            true, true, false, false, false, ModEquipmentItems.LORIEN_SCALE_HELMET, ModEquipmentItems.LORIEN_SCALE_CHESTPLATE, ModEquipmentItems.LORIEN_SCALE_LEGGINGS, ModEquipmentItems.LORIEN_SCALE_BOOTS),

    LORIEN_PLATE_ARMOR("lorien_plate", new LorienHelmetArmorModel<>(LorienHelmetArmorModel.getTexturedModelData().createModel(), "plate"), null,
            true, true, true, true, false, ModEquipmentItems.LORIEN_PLATE_HELMET, ModEquipmentItems.LORIEN_PLATE_CHESTPLATE, ModEquipmentItems.LORIEN_PLATE_LEGGINGS, ModEquipmentItems.LORIEN_PLATE_BOOTS),
    
    MORDOR_ORC_MAIL_ARMOR("mordor_orc_mail", null, null,
            true, true, false, false, false, ModEquipmentItems.MORDOR_ORC_MAIL_HELMET, ModEquipmentItems.MORDOR_ORC_MAIL_CHESTPLATE, ModEquipmentItems.MORDOR_ORC_MAIL_LEGGINGS, ModEquipmentItems.MORDOR_ORC_MAIL_BOOTS),

    MORDOR_URUK_SCALE_ARMOR("mordor_uruk_scale", null, null,
            true, true, false, false, false, ModEquipmentItems.MORDOR_URUK_SCALE_HELMET, ModEquipmentItems.MORDOR_URUK_SCALE_CHESTPLATE, ModEquipmentItems.MORDOR_URUK_SCALE_LEGGINGS, ModEquipmentItems.MORDOR_URUK_SCALE_BOOTS),

    MORDOR_URUK_PLATE_ARMOR("mordor_uruk_plate", new MordorUrukPlateHelmetArmorModel<>(MordorUrukPlateHelmetArmorModel.getTexturedModelData().createModel()), null,
            true, true, false, false, false, ModEquipmentItems.MORDOR_URUK_PLATE_HELMET, ModEquipmentItems.MORDOR_URUK_PLATE_CHESTPLATE, ModEquipmentItems.MORDOR_URUK_PLATE_LEGGINGS, ModEquipmentItems.MORDOR_URUK_PLATE_BOOTS),

    MISTY_ORC_MAIL_ARMOR("misty_orc_mail", null, null,
            true, true, false, false, false, ModEquipmentItems.MISTY_ORC_MAIL_HELMET, ModEquipmentItems.MISTY_ORC_MAIL_CHESTPLATE, ModEquipmentItems.MISTY_ORC_MAIL_LEGGINGS, ModEquipmentItems.MISTY_ORC_MAIL_BOOTS),
    
    MISTY_URUK_SCALE_ARMOR("misty_uruk_scale",  new MistyUrukScaleHelmetArmorModel<>(MistyUrukScaleHelmetArmorModel.getTexturedModelData().createModel()), new MistyUrukScaleChestplateArmorModel<>(MistyUrukScaleChestplateArmorModel.getTexturedModelData().createModel()),
            true, true, false, false, false, ModEquipmentItems.MISTY_URUK_SCALE_HELMET, ModEquipmentItems.MISTY_URUK_SCALE_CHESTPLATE, ModEquipmentItems.MISTY_URUK_SCALE_LEGGINGS, ModEquipmentItems.MISTY_URUK_SCALE_BOOTS),

    MISTY_URUK_PLATE_ARMOR("misty_uruk_plate",new MistyUrukPlateHelmetArmorModel<>(MistyUrukPlateHelmetArmorModel.getTexturedModelData().createModel()), new MistyUrukPlateChestplateArmorModel<>(MistyUrukPlateChestplateArmorModel.getTexturedModelData().createModel()),
            true, true, false, false, false, ModEquipmentItems.MISTY_URUK_PLATE_HELMET, ModEquipmentItems.MISTY_URUK_PLATE_CHESTPLATE, ModEquipmentItems.MISTY_URUK_PLATE_LEGGINGS, ModEquipmentItems.MISTY_URUK_PLATE_BOOTS),
    ;

    private final String name;
    private final CustomHelmetModel<LivingEntity> helmetModel;
    private final CustomChestplateModel<LivingEntity> chestPlateModel;
    private final ItemConvertible[] items;
    private final boolean hasInnerLayer;
    private final boolean hasVanillaArmorModel;
    private final boolean hasCape;
    private final boolean hasHood;
    private final boolean dyeable;

    ModArmors(String name, CustomHelmetModel<LivingEntity> helmetModel, CustomChestplateModel<LivingEntity> chestPlateModel,
              boolean hasInnerLayer, boolean hasVanillaArmorModel, boolean hasCape, boolean hasHood, boolean dyeable, ItemConvertible... items) {
        this.name = name;
        this.helmetModel = helmetModel;
        this.chestPlateModel = chestPlateModel;
        this.hasInnerLayer = hasInnerLayer;
        this.hasVanillaArmorModel = hasVanillaArmorModel;
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

    public boolean hasInnerLayer() {
        return hasInnerLayer;
    }

    public boolean hasVanillaArmorModel() {
        return hasVanillaArmorModel;
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
