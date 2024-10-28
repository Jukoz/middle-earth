package net.jukoz.me.item.utils.armor;

import net.jukoz.me.client.model.equipment.chest.ChestplateAddonModel;
import net.jukoz.me.client.model.equipment.chest.HaltChestplateModel;
import net.jukoz.me.client.model.equipment.chest.HobgoblinPlateChestplateModel;
import net.jukoz.me.client.model.equipment.chest.HobgoblinTrophyPlateChestplateModel;
import net.jukoz.me.client.model.equipment.head.helmets.*;
import net.jukoz.me.item.ModEquipmentItems;
import net.jukoz.me.item.utils.armor.capes.ModCapeModels;
import net.jukoz.me.item.utils.armor.capes.ModCapes;
import net.jukoz.me.item.utils.armor.hoods.ModHoodModels;
import net.jukoz.me.item.utils.armor.hoods.ModHoodStates;
import net.jukoz.me.item.utils.armor.hoods.ModHoods;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.util.StringIdentifiable;

public class ModArmorModels {

    public enum ModHelmetModels{
        STRAW_HAT(ModEquipmentItems.STRAW_HAT, new HatArmorAddonModel<>(HatArmorAddonModel.getTexturedModelData().createModel())),
        WOVEN_HAT(ModEquipmentItems.WOVEN_HAT, new HatArmorAddonModel<>(HatArmorAddonModel.getTexturedModelData().createModel())),
        KETTLE_HAT(ModEquipmentItems.KETTLE_HAT, new KettleHatArmorAddonModel<>(KettleHatArmorAddonModel.getTexturedModelData().createModel())),
        SALLET(ModEquipmentItems.SALLET, new SalletHelmetAddonModel<>(SalletHelmetAddonModel.getTexturedModelData().createModel())),

        SHIRRIF_HAT(ModEquipmentItems.SHIRRIFF_HAT, new HatArmorAddonModel<>(HatArmorAddonModel.getTexturedModelData().createModel())),

        GONDORIAN_CABASSET_HELMET(ModEquipmentItems.GONDORIAN_CABASSET_HELMET, new GondorianHelmetModel<>(GondorianHelmetModel.getTexturedModelData().createModel())),
        GONDORIAN_SOLDIER_HELMET(ModEquipmentItems.GONDORIAN_SOLDIER_HELMET, new GondorianHelmetModel<>(GondorianHelmetModel.getTexturedModelData().createModel())),
        GONDORIAN_PLATE_HELMET(ModEquipmentItems.GONDORIAN_PLATE_HELMET, new GondorianHelmetModel<>(GondorianHelmetModel.getTexturedModelData().createModel())),
        GONDORIAN_CAPTAIN_HELMET(ModEquipmentItems.GONDORIAN_CAPTAIN_HELMET, new GondorianHelmetModel<>(GondorianHelmetModel.getTexturedModelData().createModel())),
        GONDORIAN_KINGS_GUARD_HELMET(ModEquipmentItems.GONDORIAN_KINGS_GUARD_HELMET, new GondorianHelmetModel<>(GondorianHelmetModel.getTexturedModelData().createModel())),
        GONDORIAN_CITADEL_GUARD_HELMET(ModEquipmentItems.GONDORIAN_CITADEL_GUARD_HELMET, new GondorianHelmetModel<>(GondorianHelmetModel.getTexturedModelData().createModel())),
        GONDORIAN_FOUNTAIN_GUARD_HELMET(ModEquipmentItems.GONDORIAN_FOUNTAIN_GUARD_HELMET, new GondorianHelmetModel<>(GondorianHelmetModel.getTexturedModelData().createModel())),

        ROHIRRIC_REINFORCED_LEATHER_HELMET(ModEquipmentItems.ROHIRRIC_REINFORCED_LEATHER_HELMET, new RohirricHelmetArmorAddonModel<>(RohirricHelmetArmorAddonModel.getTexturedModelData().createModel())),
        ROHIRRIC_BRACED_MILITIA_HELMET(ModEquipmentItems.ROHIRRIC_BRACED_MILITIA_HELMET, new RohirricHelmetArmorAddonModel<>(RohirricHelmetArmorAddonModel.getTexturedModelData().createModel())),
        ROHIRRIC_ORNAMENTED_MILITIA_HELMET(ModEquipmentItems.ROHIRRIC_ORNAMENTED_MILITIA_HELMET, new RohirricHelmetArmorAddonModel<>(RohirricHelmetArmorAddonModel.getTexturedModelData().createModel())),
        ROHIRRIC_ORNAMENTED_SOLDIER_HELMET(ModEquipmentItems.ROHIRRIC_ORNAMENTED_SOLDIER_HELMET, new RohirricHelmetArmorAddonModel<>(RohirricHelmetArmorAddonModel.getTexturedModelData().createModel())),
        ROHIRRIC_ROYAL_GUARD_HELMET(ModEquipmentItems.ROHIRRIC_ROYAL_GUARD_HELMET, new RohirricHelmetArmorAddonModel<>(RohirricHelmetArmorAddonModel.getTexturedModelData().createModel())),
        EORLING_MARSHAL_HELMET(ModEquipmentItems.EORLING_MARSHAL_HELMET, new RohirricHelmetArmorAddonModel<>(RohirricHelmetArmorAddonModel.getTexturedModelData().createModel())),
        HORSE_LORD_HELMET(ModEquipmentItems.HORSE_LORD_HELMET, new RohirricHelmetArmorAddonModel<>(RohirricHelmetArmorAddonModel.getTexturedModelData().createModel())),

        DALISH_HELMET(ModEquipmentItems.DALISH_HELMET, new DaleTallSpikeHelmetModel<>(DaleTallSpikeHelmetModel.getTexturedModelData().createModel())),
        DALISH_HELMET_BLACK_FUR(ModEquipmentItems.DALISH_HELMET_BLACK_FUR, new DaleTallSpikeHelmetModel<>(DaleTallSpikeHelmetModel.getTexturedModelData().createModel())),
        DALISH_HELMET_BROWN_FUR(ModEquipmentItems.DALISH_HELMET_BROWN_FUR, new DaleTallSpikeHelmetModel<>(DaleTallSpikeHelmetModel.getTexturedModelData().createModel())),
        DALISH_HELMET_TAN_FUR(ModEquipmentItems.DALISH_HELMET_TAN_FUR, new DaleTallSpikeHelmetModel<>(DaleTallSpikeHelmetModel.getTexturedModelData().createModel())),
        DALISH_BURGONET(ModEquipmentItems.DALISH_BURGONET, new DaleHelmetModel<>(DaleHelmetModel.getTexturedModelData().createModel())),
        DALISH_HEYDAY_HELMET(ModEquipmentItems.DALISH_HEYDAY_HELMET, new DaleTallSpikeHelmetModel<>(DaleTallSpikeHelmetModel.getTexturedModelData().createModel())),
        BARDING_SOLDIER_HELMET(ModEquipmentItems.BARDING_SOLDIER_HELMET, new DaleHelmetModel<>(DaleHelmetModel.getTexturedModelData().createModel())),
        BARDING_SERGEANT_HELMET(ModEquipmentItems.BARDING_SERGEANT_HELMET, new DaleHelmetModel<>(DaleHelmetModel.getTexturedModelData().createModel())),

        EREBOR_PLATE_HELMET(ModEquipmentItems.EREBOR_PLATE_HELMET, new EreborHelmetModel<>(EreborHelmetModel.getTexturedModelData().createModel())),
        RAVENHILL_SENTINEL_HELMET(ModEquipmentItems.RAVENHILL_SENTINEL_HELMET, new EreborWingsHelmetModel<>(EreborWingsHelmetModel.getTexturedModelData().createModel())),
        EREBOR_GATEWARDEN_HELMET(ModEquipmentItems.EREBOR_GATEWARDEN_HELMET, new EreborHelmetModel<>(EreborHelmetModel.getTexturedModelData().createModel())),

        LORIEN_DIADEM(ModEquipmentItems.LORIEN_DIADEM, new LorienHelmetArmorAddonModel<>(LorienHelmetArmorAddonModel.getTexturedModelData().createModel())),
        LORIEN_LEATHER_HELMET(ModEquipmentItems.LORIEN_LEATHER_HELMET, new LorienHelmetArmorAddonModel<>(LorienHelmetArmorAddonModel.getTexturedModelData().createModel())),
        LORIEN_CHAIN_COIF_DIADEM(ModEquipmentItems.LORIEN_CHAIN_COIF_DIADEM, new LorienHelmetArmorAddonModel<>(LorienHelmetArmorAddonModel.getTexturedModelData().createModel())),
        LORIEN_SHORT_CHAIN_COIF_DIADEM(ModEquipmentItems.LORIEN_SHORT_CHAIN_COIF_DIADEM, new LorienHelmetArmorAddonModel<>(LorienHelmetArmorAddonModel.getTexturedModelData().createModel())),
        LORIEN_SOLDIER_HELMET(ModEquipmentItems.LORIEN_SOLDIER_HELMET, new LorienHelmetArmorAddonModel<>(LorienHelmetArmorAddonModel.getTexturedModelData().createModel())),
        GALADHRIM_HELMET(ModEquipmentItems.GALADHRIM_HELMET, new LorienHelmetArmorAddonModel<>(LorienHelmetArmorAddonModel.getTexturedModelData().createModel())),
        GALADHRIM_LORD_HELMET(ModEquipmentItems.GALADHRIM_LORD_HELMET, new LorienHelmetArmorAddonModel<>(LorienHelmetArmorAddonModel.getTexturedModelData().createModel())),

        BLACK_URUK_COMMANDER_HELMET(ModEquipmentItems.BLACK_URUK_COMMANDER_HELMET, new HelmetSkullModel<>(HelmetSkullModel.getTexturedModelData().createModel())),

        URUK_HAI_LEATHER_SCOUT_CAP(ModEquipmentItems.URUK_HAI_LEATHER_SCOUT_CAP, new IsenUrukHelmetModel<>(IsenUrukHelmetModel.getTexturedModelData().createModel())),
        URUK_HAI_PLATE_HELMET(ModEquipmentItems.URUK_HAI_PLATE_HELMET, new IsenUrukHelmetModel<>(IsenUrukHelmetModel.getTexturedModelData().createModel())),
        URUK_HAI_PLATE_PAINTED_HELMET(ModEquipmentItems.URUK_HAI_PLATE_PAINTED_HELMET, new IsenUrukHelmetModel<>(IsenUrukHelmetModel.getTexturedModelData().createModel())),
        URUK_HAI_SAPPER_HELMET(ModEquipmentItems.URUK_HAI_SAPPER_HELMET, new IsenUrukHelmetModel<>(IsenUrukHelmetModel.getTexturedModelData().createModel())),
        URUK_HAI_SAPPER_PAINTED_HELMET(ModEquipmentItems.URUK_HAI_SAPPER_PAINTED_HELMET, new IsenUrukHelmetModel<>(IsenUrukHelmetModel.getTexturedModelData().createModel())),
        URUK_HAI_BERSERKER_HELMET(ModEquipmentItems.URUK_HAI_BERSERKER_HELMET, new IsenUrukHelmetModel<>(IsenUrukHelmetModel.getTexturedModelData().createModel())),
        URUK_HAI_BERSERKER_PAINTED_HELMET(ModEquipmentItems.URUK_HAI_BERSERKER_PAINTED_HELMET, new IsenUrukHelmetModel<>(IsenUrukHelmetModel.getTexturedModelData().createModel())),
        URUK_HAI_COMMANDER_HELMET(ModEquipmentItems.URUK_HAI_COMMANDER_HELMET, new IsenUrukHelmetModel<>(IsenUrukHelmetModel.getTexturedModelData().createModel())),
        URUK_HAI_COMMANDER_PAINTED_HELMET(ModEquipmentItems.URUK_HAI_COMMANDER_PAINTED_HELMET, new IsenUrukHelmetModel<>(IsenUrukHelmetModel.getTexturedModelData().createModel())),

        GUNDABAD_HOBGOBLIN_PLATE_CRESTED_HELMET(ModEquipmentItems.GUNDABAD_HOBGOBLIN_PLATE_CRESTED_HELMET, new HobgoblinPlateHelmetModel<>(HobgoblinPlateHelmetModel.getTexturedModelData().createModel())),
        GUNDABAD_HOBGOBLIN_PLATE_LARGE_CREST_HELMET(ModEquipmentItems.GUNDABAD_HOBGOBLIN_PLATE_LARGE_CREST_HELMET, new HobgoblinPlateHelmetModel<>(HobgoblinPlateHelmetModel.getTexturedModelData().createModel())),
        GUNDABAD_HOBGOBLIN_PLATE_LONG_HORN_HELMET(ModEquipmentItems.GUNDABAD_HOBGOBLIN_PLATE_LONG_HORN_HELMET, new HobgoblinPlateHelmetModel<>(HobgoblinPlateHelmetModel.getTexturedModelData().createModel())),
        GUNDABAD_HOBGOBLIN_PLATE_SMALL_HORN_HELMET(ModEquipmentItems.GUNDABAD_HOBGOBLIN_PLATE_SMALL_HORN_HELMET, new HobgoblinPlateHelmetModel<>(HobgoblinPlateHelmetModel.getTexturedModelData().createModel())),
        ;

        private final Item item;
        private final HelmetAddonModel<LivingEntity> model;

        ModHelmetModels(Item item, HelmetAddonModel<LivingEntity> model){
            this.item = item;
            this.model = model;
        }

        public Item getItem() {
            return item;
        }

        public HelmetAddonModel<LivingEntity> getModel() {
            return model;
        }
    }

    public enum ModRustyHelmetModels{
        MORDOR_ORC_SNOUT_HELMET(ModEquipmentItems.MORDOR_ORC_SNOUT_HELMET, new MordorOrcHelmetModel<>(MordorOrcHelmetModel.getTexturedModelData().createModel())),
        ORC_SALLET(ModEquipmentItems.ORC_SALLET, new SalletHelmetAddonModel<>(SalletHelmetAddonModel.getTexturedModelData().createModel())),
        ;

        private final Item item;
        private final HelmetAddonModel<LivingEntity> model;

        ModRustyHelmetModels(Item item, HelmetAddonModel<LivingEntity> model){
            this.item = item;
            this.model = model;
        }

        public Item getItem() {
            return item;
        }

        public HelmetAddonModel<LivingEntity> getModel() {
            return model;
        }
    }

    public enum ModChestplateModels{
        GONDORIAN_PLATE_CHESTPLATE(ModEquipmentItems.GONDORIAN_PLATE_CHESTPLATE, new HaltChestplateModel<>(HaltChestplateModel.getTexturedModelData().createModel())),
        GONDORIAN_KINGS_GUARD_CHESTPLATE(ModEquipmentItems.GONDORIAN_KINGS_GUARD_CHESTKPLATE, new HaltChestplateModel<>(HaltChestplateModel.getTexturedModelData().createModel())),
        GONDORIAN_FOUNTAIN_GUARD_CHESTPLATE(ModEquipmentItems.GONDORIAN_FOUNTAIN_GUARD_CHESTPLATE, new HaltChestplateModel<>(HaltChestplateModel.getTexturedModelData().createModel())),

        EREBOR_PLATE_CHESTPLATE(ModEquipmentItems.EREBOR_PLATE_CHESTPLATE, new HaltChestplateModel<>(HaltChestplateModel.getTexturedModelData().createModel())),
        RAVENHILL_WATCHWARDEN_CHESTPLATE(ModEquipmentItems.RAVENHILL_WATCHWARDEN_CHESTPLATE, new HaltChestplateModel<>(HaltChestplateModel.getTexturedModelData().createModel())),
        RAVENHILL_SENTINEL_CHESTPLATE(ModEquipmentItems.RAVENHILL_SENTINEL_CHESTPLATE, new HaltChestplateModel<>(HaltChestplateModel.getTexturedModelData().createModel())),
        EREBOR_GATEWARDEN_CHESTPLATE(ModEquipmentItems.EREBOR_GATEWARDEN_CHESTPLATE, new HaltChestplateModel<>(HaltChestplateModel.getTexturedModelData().createModel())),

        BLACK_URUK_PLATE_CHESTPLATE(ModEquipmentItems.BLACK_URUK_PLATE_CHESTPLATE, new HaltChestplateModel<>(HaltChestplateModel.getTexturedModelData().createModel())),
        BLACK_URUK_COMMANDER_CHESTPLATE(ModEquipmentItems.BLACK_URUK_COMMANDER_CHESTPLATE, new HaltChestplateModel<>(HaltChestplateModel.getTexturedModelData().createModel())),

        URUK_HAI_PLATE_CHESTPLATE(ModEquipmentItems.URUK_HAI_PLATE_CHESTPLATE, new HaltChestplateModel<>(HaltChestplateModel.getTexturedModelData().createModel())),

        GUNDABAD_HOBGOBLIN_PLATE_CHESTPLATE(ModEquipmentItems.GUNDABAD_HOBGOBLIN_PLATE_CHESTPLATE, new HobgoblinPlateChestplateModel<>(HobgoblinPlateChestplateModel.getTexturedModelData().createModel())),
        GUNDABAD_HOBGOBLIN_PLATE_TROPHY_CHESTPLATE(ModEquipmentItems.GUNDABAD_HOBGOBLIN_PLATE_TROPHY_CHESTPLATE, new HobgoblinTrophyPlateChestplateModel<>(HobgoblinTrophyPlateChestplateModel.getTexturedModelData().createModel())),
        ;

        private final Item item;
        private final ChestplateAddonModel<LivingEntity> model;

        ModChestplateModels(Item item, ChestplateAddonModel<LivingEntity> model){
            this.item = item;
            this.model = model;
        }

        public Item getItem() {
            return item;
        }

        public ChestplateAddonModel<LivingEntity> getModel() {
            return model;
        }
    }

    public enum ModRustyChestplateModels{
        ;

        private final Item item;
        private final ChestplateAddonModel<LivingEntity> model;

        ModRustyChestplateModels(Item item, ChestplateAddonModel<LivingEntity> model){
            this.item = item;
            this.model = model;
        }

        public Item getItem() {
            return item;
        }

        public ChestplateAddonModel<LivingEntity> getModel() {
            return model;
        }
    }

    public enum ModCapePairedModels implements StringIdentifiable {
        BASE_CAPE(ModCapes.BASE_CAPE, ModCapeModels.MEDIUM_MODELS),
        SURCOAT(ModCapes.SURCOAT, ModCapeModels.SURCOAT_MODELS),
        BASE_CLOAK(ModCapes.BASE_CLOAK, ModCapeModels.WIDE_MODELS),

        BLACK_FUR_CLOAK(ModCapes.BLACK_FUR_CLOAK, ModCapeModels.FUR_MODELS),
        BROWN_FUR_CLOAK(ModCapes.BROWN_FUR_CLOAK, ModCapeModels.FUR_MODELS),
        GRAY_FUR_CLOAK(ModCapes.GRAY_FUR_CLOAK, ModCapeModels.FUR_MODELS),
        TAN_FUR_CLOAK(ModCapes.TAN_FUR_CLOAK, ModCapeModels.FUR_MODELS),
        WHITE_FUR_CLOAK(ModCapes.WHITE_FUR_CLOAK, ModCapeModels.FUR_MODELS),

        BLACK_FUR(ModCapes.BLACK_FUR, ModCapeModels.FUR_MODELS),
        BROWN_FUR(ModCapes.BROWN_FUR, ModCapeModels.FUR_MODELS),
        GRAY_FUR(ModCapes.GRAY_FUR, ModCapeModels.FUR_MODELS),
        TAN_FUR(ModCapes.TAN_FUR, ModCapeModels.FUR_MODELS),
        WHITE_FUR(ModCapes.WHITE_FUR, ModCapeModels.FUR_MODELS),

        GONDORIAN_CAPTAIN_CAPE(ModCapes.GONDORIAN_CAPTAIN_CAPE, ModCapeModels.MEDIUM_MODELS),
        GONDORIAN_HERO_CAPE(ModCapes.GONDORIAN_HERO_CAPE, ModCapeModels.MEDIUM_MODELS),
        GONDORIAN_KINGS_GUARD_CAPE(ModCapes.GONDORIAN_KINGS_GUARD_CAPE, ModCapeModels.MEDIUM_MODELS),
        GONDORIAN_CITADEL_GUARD_CAPE(ModCapes.GONDORIAN_CITADEL_GUARD_CAPE, ModCapeModels.MEDIUM_MODELS),
        GONDORIAN_FOUNTAIN_GUARD_CAPE(ModCapes.GONDORIAN_FOUNTAIN_GUARD_CAPE, ModCapeModels.WIDE_MODELS),

        ROHIRRIC_CAPE(ModCapes.ROHIRRIC_CAPE, ModCapeModels.MEDIUM_MODELS),
        ROHIRRIC_ROYAL_GUARD_CAPE(ModCapes.ROHIRRIC_ROYAL_GUARD_CAPE, ModCapeModels.MEDIUM_MODELS),
        EORLING_MARSHAL_CAPE(ModCapes.EORLING_MARSHAL_CAPE, ModCapeModels.MEDIUM_MODELS),
        HORSE_LORD_CAPE(ModCapes.HORSE_LORD_CAPE, ModCapeModels.MEDIUM_MODELS),

        BARDING_SURCOAT(ModCapes.BARDING_SURCOAT, ModCapeModels.SURCOAT_MODELS),
        DALISH_HEYDAY_CAPE(ModCapes.DALISH_HEYDAY_CAPE, ModCapeModels.MEDIUM_MODELS),
        BARDING_SERGEANT_CAPE(ModCapes.BARDING_SERGEANT_CAPE, ModCapeModels.MEDIUM_MODELS),

        EREBOR_CAPE(ModCapes.EREBOR_CAPE, ModCapeModels.MEDIUM_MODELS),
        RAVENHILL_SENTINEL_CAPE(ModCapes.RAVENHILL_SENTINEL_CAPE, ModCapeModels.WIDE_MODELS),

        LORIEN_MARCHWARDEN_CAPE(ModCapes.LORIEN_MARCHWARDEN_CAPE, ModCapeModels.MEDIUM_MODELS),
        GALADHRIM_CAPE(ModCapes.GALADHRIM_CAPE, ModCapeModels.SLIM_MODELS),
        GALADHRIM_LORD_SURCOAT(ModCapes.GALADHRIM_LORD_SURCOAT, ModCapeModels.SURCOAT_MODELS),;

        private final ModCapes cape;
        private final ModCapeModels models;


        ModCapePairedModels(ModCapes cape, ModCapeModels models){
            this.cape = cape;
            this.models = models;
        }

        public ModCapeModels getModel() {
            return models;
        }

        @Override
        public String asString() {
            return "";
        }
    }


    public enum ModHoodPairedModels implements StringIdentifiable {
        BASE_HOOD(ModHoods.BASE_HOOD, ModHoodModels.REGULAR_MODELS),

        BLACK_FUR_HOOD(ModHoods.BLACK_FUR_HOOD, ModHoodModels.FUR_MODELS),
        BROWN_FUR_HOOD(ModHoods.BROWN_FUR_HOOD, ModHoodModels.FUR_MODELS),
        GRAY_FUR_HOOD(ModHoods.GRAY_FUR_HOOD, ModHoodModels.FUR_MODELS),
        TAN_FUR_HOOD(ModHoods.TAN_FUR_HOOD, ModHoodModels.FUR_MODELS),
        WHITE_FUR_HOOD(ModHoods.WHITE_FUR_HOOD, ModHoodModels.FUR_MODELS),

        GONDORIAN_CITADEL_GUARD_HOOD(ModHoods.GONDORIAN_CITADEL_GUARD_HOOD, ModHoodModels.REGULAR_MODELS),

        LORIEN_MARCHWARDEN_HOOD(ModHoods.LORIEN_MARCHWARDEN_HOOD, ModHoodModels.REGULAR_MODELS),
        GALADHRIM_HOOD(ModHoods.GALADHRIM_HOOD, ModHoodModels.REGULAR_MODELS)
        ;
        private final ModHoods hood;
        private final ModHoodModels models;


        ModHoodPairedModels(ModHoods hood, ModHoodModels models){
            this.hood = hood;
            this.models = models;
        }

        public ModHoodModels getModel() {
            return models;
        }

        @Override
        public String asString() {
            return "";
        }
    }
}
