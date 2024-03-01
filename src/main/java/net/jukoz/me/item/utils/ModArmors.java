package net.jukoz.me.item.utils;

import net.jukoz.me.client.model.equipment.chest.*;
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

    RUSTY_KETTLE_HAT("rusty_kettle_hat", new KettleHatArmorModel<>(KettleHatArmorModel.getTexturedModelData().createModel(), "rusty_kettle_hat"), null,
            false, true, false, false, false, ModEquipmentItems.RUSTY_KETTLE_HAT),

    KETTLE_HAT("kettle_hat", new KettleHatArmorModel<>(KettleHatArmorModel.getTexturedModelData().createModel(), "kettle_hat"), null,
            false, true, false, false, false, ModEquipmentItems.KETTLE_HAT),

    NAZGUL_CLOAK("nazgul_cloak", null, null,
            false, true, true, false, false, ModEquipmentItems.NAZGUL_CLOAK_HOOD, ModEquipmentItems.NAZGUL_CLOAK, ModEquipmentItems.NAZGUL_PANTS, ModEquipmentItems.NAZGUL_BOOTS),

    HOBBIT_SHIRRIFF_HAT_GREEN("hobbit_shirrifff_hat_green", new HobbitShirriffHatArmorModel<>(HobbitShirriffHatArmorModel.getTexturedModelData().createModel(), "green"), null,
            false, false, false, false, false, ModEquipmentItems.HOBBIT_SHIRRIFF_HAT_GREEN),
    HOBBIT_SHIRRIFF_HAT_BROWN("hobbit_shirrifff_hat_brown", new HobbitShirriffHatArmorModel<>(HobbitShirriffHatArmorModel.getTexturedModelData().createModel(), "brown"), null,
            false, false, false, false, false, ModEquipmentItems.HOBBIT_SHIRRIFF_HAT_BROWN),
    
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

    EREBOR_MAIL_ARMOR("erebor_mail", null, null,
            true, true, true, false, false, ModEquipmentItems.EREBOR_MAIL_HELMET, ModEquipmentItems.EREBOR_MAIL_CHESTPLATE, ModEquipmentItems.EREBOR_MAIL_LEGGINGS, ModEquipmentItems.EREBOR_MAIL_BOOTS),

    EREBOR_SCALE_ARMOR("erebor_scale", null, new EreborPouchChestplateArmorModel<>(EreborPouchChestplateArmorModel.getTexturedModelData().createModel()),
            true, true, false, false, false, ModEquipmentItems.EREBOR_SCALE_HELMET, ModEquipmentItems.EREBOR_SCALE_CHESTPLATE, ModEquipmentItems.EREBOR_SCALE_LEGGINGS, ModEquipmentItems.EREBOR_SCALE_BOOTS),

    EREBOR_PLATE_ARMOR("erebor_plate", null, new EreborPouchChestplateArmorModel<>(EreborPouchChestplateArmorModel.getTexturedModelData().createModel()),
            true, true, false, false, false, ModEquipmentItems.EREBOR_PLATE_HELMET, ModEquipmentItems.EREBOR_PLATE_CHESTPLATE, ModEquipmentItems.EREBOR_PLATE_LEGGINGS, ModEquipmentItems.EREBOR_PLATE_BOOTS),
    
    EREBOR_COMMANDER_ARMOR("erebor_commander", new EreborCommanderHelmetArmorModel<>(EreborCommanderHelmetArmorModel.getTexturedModelData().createModel()), null,
            true, true, true, false, false, ModEquipmentItems.EREBOR_COMMANDER_HELMET, ModEquipmentItems.EREBOR_COMMANDER_CHESTPLATE, ModEquipmentItems.EREBOR_COMMANDER_LEGGINGS, ModEquipmentItems.EREBOR_COMMANDER_BOOTS),
    
    LORIEN_MAIL_ARMOR("lorien_mail", new LorienHelmetArmorModel<>(LorienHelmetArmorModel.getTexturedModelData().createModel(), "mail"), null,
            true, true, true, true, false, ModEquipmentItems.LORIEN_MAIL_HELMET, ModEquipmentItems.LORIEN_MAIL_CHESTPLATE, ModEquipmentItems.LORIEN_MAIL_LEGGINGS, ModEquipmentItems.LORIEN_MAIL_BOOTS),

    LORIEN_SCALE_ARMOR("lorien_scale", new LorienHelmetArmorModel<>(LorienHelmetArmorModel.getTexturedModelData().createModel(), "scale"), null,
            true, true, true, false, false, ModEquipmentItems.LORIEN_SCALE_HELMET, ModEquipmentItems.LORIEN_SCALE_CHESTPLATE, ModEquipmentItems.LORIEN_SCALE_LEGGINGS, ModEquipmentItems.LORIEN_SCALE_BOOTS),

    LORIEN_PLATE_ARMOR("lorien_plate", new LorienHelmetArmorModel<>(LorienHelmetArmorModel.getTexturedModelData().createModel(), "plate"), null,
            true, true, true, true, false, ModEquipmentItems.LORIEN_PLATE_HELMET, ModEquipmentItems.LORIEN_PLATE_CHESTPLATE, ModEquipmentItems.LORIEN_PLATE_LEGGINGS, ModEquipmentItems.LORIEN_PLATE_BOOTS),
    
    MORDOR_ORC_MAIL_ARMOR("mordor_orc_mail", null, null,
            true, true, false, false, false, ModEquipmentItems.MORDOR_ORC_MAIL_HELMET, ModEquipmentItems.MORDOR_ORC_MAIL_CHESTPLATE, ModEquipmentItems.MORDOR_ORC_MAIL_LEGGINGS, ModEquipmentItems.MORDOR_ORC_MAIL_BOOTS),

    MORDOR_BLACK_URUK_SCALE_ARMOR("mordor_black_uruk_scale", null, null,
            true, true, false, false, false, ModEquipmentItems.MORDOR_BLACK_URUK_SCALE_HELMET, ModEquipmentItems.MORDOR_BLACK_URUK_SCALE_CHESTPLATE, ModEquipmentItems.MORDOR_BLACK_URUK_SCALE_LEGGINGS, ModEquipmentItems.MORDOR_BLACK_URUK_SCALE_BOOTS),

    MORDOR_BLACK_URUK_PLATE_ARMOR("mordor_black_uruk_plate", null, null,
            true, true, false, false, false, ModEquipmentItems.MORDOR_BLACK_URUK_PLATE_HELMET, ModEquipmentItems.MORDOR_BLACK_URUK_PLATE_CHESTPLATE, ModEquipmentItems.MORDOR_BLACK_URUK_PLATE_LEGGINGS, ModEquipmentItems.MORDOR_BLACK_URUK_PLATE_BOOTS),

    MORDOR_BLACK_URUK_COMMANDER_ARMOR("mordor_black_uruk_commander", new MordorUrukCommanderHelmetArmorModel<>(MordorUrukCommanderHelmetArmorModel.getTexturedModelData().createModel()), null,
            true, true, false, false, false, ModEquipmentItems.MORDOR_BLACK_URUK_COMMANDER_HELMET),

    MISTY_GOBLIN_MAIL_ARMOR("misty_goblin_mail", null, null,
            true, true, false, false, false, ModEquipmentItems.MISTY_GOBLIN_MAIL_HELMET, ModEquipmentItems.MISTY_GOBLIN_MAIL_CHESTPLATE, ModEquipmentItems.MISTY_GOBLIN_MAIL_LEGGINGS, ModEquipmentItems.MISTY_GOBLIN_MAIL_BOOTS),
    
    MISTY_HOBGOBLIN_SCALE_ARMOR("misty_hobgoblin_scale",  new MistyUrukScaleHelmetArmorModel<>(MistyUrukScaleHelmetArmorModel.getTexturedModelData().createModel()), new MistyUrukScaleChestplateArmorModel<>(MistyUrukScaleChestplateArmorModel.getTexturedModelData().createModel()),
            true, true, false, false, false, ModEquipmentItems.MISTY_HOBGOBLIN_SCALE_HELMET, ModEquipmentItems.MISTY_HOBGOBLIN_SCALE_CHESTPLATE, ModEquipmentItems.MISTY_HOBGOBLIN_SCALE_LEGGINGS, ModEquipmentItems.MISTY_HOBGOBLIN_SCALE_BOOTS),

    MISTY_HOBGOBLIN_PLATE_ARMOR("misty_hobgoblin_plate",new MistyUrukPlateHelmetArmorModel<>(MistyUrukPlateHelmetArmorModel.getTexturedModelData().createModel()), new MistyUrukPlateChestplateArmorModel<>(MistyUrukPlateChestplateArmorModel.getTexturedModelData().createModel()),
            true, true, false, false, false, ModEquipmentItems.MISTY_HOBGOBLIN_PLATE_HELMET, ModEquipmentItems.MISTY_HOBGOBLIN_PLATE_CHESTPLATE, ModEquipmentItems.MISTY_HOBGOBLIN_PLATE_LEGGINGS, ModEquipmentItems.MISTY_HOBGOBLIN_PLATE_BOOTS),

    MISTY_HOBGOBLIN_COMMANDER_ARMOR("misty_hobgoblin_commander",null, new MistyUrukCommanderChestplateArmorModel<>(MistyUrukCommanderChestplateArmorModel.getTexturedModelData().createModel()),
            true, true, false, false, false, ModEquipmentItems.MISTY_HOBGOBLIN_COMMANDER_CHESTPLATE),
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
