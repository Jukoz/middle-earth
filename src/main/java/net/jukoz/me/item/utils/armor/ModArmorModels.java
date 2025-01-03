package net.jukoz.me.item.utils.armor;

import net.jukoz.me.client.model.equipment.chest.*;
import net.jukoz.me.client.model.equipment.head.helmets.*;
import net.jukoz.me.item.ModEquipmentItems;
import net.jukoz.me.item.utils.armor.capes.ModCapeModels;
import net.jukoz.me.item.utils.armor.capes.ModCapes;
import net.jukoz.me.item.utils.armor.hoods.ModHoodModels;
import net.jukoz.me.item.utils.armor.hoods.ModHoods;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.util.StringIdentifiable;

public class ModArmorModels {

    public enum ModHelmetModels{
        STRAW_HAT(ModEquipmentItems.STRAW_HAT, new HatArmorAddonModel<>(HatArmorAddonModel.getTexturedModelData().createModel())),
        WOVEN_HAT(ModEquipmentItems.WOVEN_HAT, new HatArmorAddonModel<>(HatArmorAddonModel.getTexturedModelData().createModel())),
        WANDERER_HAT(ModEquipmentItems.WANDERER_HAT, new WizardHatModel<>(WizardHatModel.getTexturedModelData().createModel())),
        KETTLE_HAT(ModEquipmentItems.KETTLE_HAT, new KettleHatArmorAddonModel<>(KettleHatArmorAddonModel.getTexturedModelData().createModel())),
        KETTLE_HAT_WITH_COIF(ModEquipmentItems.KETTLE_HAT_WITH_COIF, new KettleHatArmorAddonModel<>(KettleHatArmorAddonModel.getTexturedModelData().createModel())),
        KETTLE_HAT_WITH_CLOSED_COIF(ModEquipmentItems.KETTLE_HAT_WITH_CLOSED_COIF, new KettleHatArmorAddonModel<>(KettleHatArmorAddonModel.getTexturedModelData().createModel())),
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

        DWARVEN_MINER_HELMET(ModEquipmentItems.DWARVEN_MINER_HELMET, new DwarvenMinerHelmetModel<>(DwarvenMinerHelmetModel.getTexturedModelData().createModel())),

        EREBOR_CAPTAIN_HELMET(ModEquipmentItems.EREBOR_CAPTAIN_HELMET, new EreborHornsHelmetModel<>(EreborHornsHelmetModel.getTexturedModelData().createModel())),

        EREBOR_PLATE_HELMET(ModEquipmentItems.EREBOR_PLATE_HELMET, new EreborHelmetModel<>(EreborHelmetModel.getTexturedModelData().createModel())),
        RAVENHILL_SENTINEL_HELMET(ModEquipmentItems.RAVENHILL_SENTINEL_HELMET, new EreborWingsHelmetModel<>(EreborWingsHelmetModel.getTexturedModelData().createModel())),
        EREBOR_GATEWARDEN_HELMET(ModEquipmentItems.EREBOR_GATEWARDEN_HELMET, new EreborHelmetModel<>(EreborHelmetModel.getTexturedModelData().createModel())),

        LORIEN_DIADEM(ModEquipmentItems.LORIEN_DIADEM, new LorienHelmetArmorAddonModel<>(LorienHelmetArmorAddonModel.getTexturedModelData().createModel())),
        LORIEN_LEATHER_HELMET(ModEquipmentItems.LORIEN_LEATHER_HELMET, new LorienHelmetArmorAddonModel<>(LorienHelmetArmorAddonModel.getTexturedModelData().createModel())),
        LORIEN_MAIL_COIF_DIADEM(ModEquipmentItems.LORIEN_MAIL_COIF_DIADEM, new LorienHelmetArmorAddonModel<>(LorienHelmetArmorAddonModel.getTexturedModelData().createModel())),
        LORIEN_SHORT_MAIL_COIF_DIADEM(ModEquipmentItems.LORIEN_SHORT_MAIL_COIF_DIADEM, new LorienHelmetArmorAddonModel<>(LorienHelmetArmorAddonModel.getTexturedModelData().createModel())),
        LORIEN_SOLDIER_HELMET(ModEquipmentItems.LORIEN_SOLDIER_HELMET, new LorienHelmetArmorAddonModel<>(LorienHelmetArmorAddonModel.getTexturedModelData().createModel())),
        GALADHRIM_HELMET(ModEquipmentItems.GALADHRIM_HELMET, new LorienHelmetArmorAddonModel<>(LorienHelmetArmorAddonModel.getTexturedModelData().createModel())),
        GALADHRIM_LORD_HELMET(ModEquipmentItems.GALADHRIM_LORD_HELMET, new LorienHelmetArmorAddonModel<>(LorienHelmetArmorAddonModel.getTexturedModelData().createModel())),

        ORCISH_BRACED_HELMET(ModEquipmentItems.ORCISH_BRACED_HELMET, new OrcSpikeHelmetModel<>(OrcSpikeHelmetModel.getTexturedModelData().createModel())),
        ORCISH_SALLET(ModEquipmentItems.ORCISH_SALLET, new SalletHelmetAddonModel<>(SalletHelmetAddonModel.getTexturedModelData().createModel())),

        MORDOR_CREST_HELMET(ModEquipmentItems.MORDOR_CREST_HELMET, new MordorOrcHelmetModel<>(MordorOrcHelmetModel.getTexturedModelData().createModel())),
        RUSTED_MORDOR_CREST_HELMET(ModEquipmentItems.RUSTED_MORDOR_CREST_HELMET, new MordorOrcHelmetModel<>(MordorOrcHelmetModel.getTexturedModelData().createModel())),
        MORDOR_GREAT_HELMET(ModEquipmentItems.MORDOR_GREAT_HELMET, new MordorOrcHelmetModel<>(MordorOrcHelmetModel.getTexturedModelData().createModel())),
        RUSTED_MORDOR_GREAT_HELMET(ModEquipmentItems.RUSTED_MORDOR_GREAT_HELMET, new MordorOrcHelmetModel<>(MordorOrcHelmetModel.getTexturedModelData().createModel())),
        MORDOR_KETTLE_HAT(ModEquipmentItems.MORDOR_KETTLE_HAT, new KettleHatArmorAddonModel<>(KettleHatArmorAddonModel.getTexturedModelData().createModel())),
        RUSTED_MORDOR_KETTLE_HAT(ModEquipmentItems.RUSTED_MORDOR_KETTLE_HAT, new KettleHatArmorAddonModel<>(KettleHatArmorAddonModel.getTexturedModelData().createModel())),
        MORDOR_KETTLE_HAT_WITH_COIF(ModEquipmentItems.MORDOR_KETTLE_HAT_WITH_COIF, new KettleHatArmorAddonModel<>(KettleHatArmorAddonModel.getTexturedModelData().createModel())),
        RUSTED_MORDOR_KETTLE_HAT_WITH_COIF(ModEquipmentItems.RUSTED_MORDOR_KETTLE_HAT_WITH_COIF, new KettleHatArmorAddonModel<>(KettleHatArmorAddonModel.getTexturedModelData().createModel())),
        MORDOR_SALLET(ModEquipmentItems.MORDOR_SALLET, new SalletHelmetAddonModel<>(SalletHelmetAddonModel.getTexturedModelData().createModel())),
        RUSTED_MORDOR_SALLET(ModEquipmentItems.RUSTED_MORDOR_SALLET, new SalletHelmetAddonModel<>(SalletHelmetAddonModel.getTexturedModelData().createModel())),
        MORDOR_SNOUT_HELMET(ModEquipmentItems.MORDOR_SNOUT_HELMET, new MordorOrcHelmetModel<>(MordorOrcHelmetModel.getTexturedModelData().createModel())),
        RUSTED_MORDOR_SNOUT_HELMET(ModEquipmentItems.RUSTED_MORDOR_SNOUT_HELMET, new MordorOrcHelmetModel<>(MordorOrcHelmetModel.getTexturedModelData().createModel())),

        BLACK_URUK_COMMANDER_HELMET(ModEquipmentItems.BLACK_URUK_COMMANDER_HELMET, new HelmetSkullModel<>(HelmetSkullModel.getTexturedModelData().createModel())),

        MORDOR_BLACK_NUMENOREAN_PLATE_HELMET(ModEquipmentItems.MORDOR_BLACK_NUMENOREAN_PLATE_HELMET, new MordorBNHelmetModel<>(MordorBNHelmetModel.getTexturedModelData().createModel())),

        URUK_HAI_LEATHER_SCOUT_CAP(ModEquipmentItems.URUK_HAI_LEATHER_SCOUT_CAP, new IsenUrukHelmetModel<>(IsenUrukHelmetModel.getTexturedModelData().createModel())),
        URUK_HAI_LIGHT_HELMET(ModEquipmentItems.URUK_HAI_LIGHT_HELMET, new IsenUrukHelmetModel<>(IsenUrukHelmetModel.getTexturedModelData().createModel())),
        URUK_HAI_PAINTED_LIGHT_HELMET(ModEquipmentItems.URUK_HAI_PAINTED_LIGHT_HELMET, new IsenUrukHelmetModel<>(IsenUrukHelmetModel.getTexturedModelData().createModel())),
        URUK_HAI_REINFORCED_HELMET(ModEquipmentItems.URUK_HAI_REINFORCED_HELMET, new IsenUrukHelmetModel<>(IsenUrukHelmetModel.getTexturedModelData().createModel())),
        URUK_HAI_PAINTED_REINFORCED_HELMET(ModEquipmentItems.URUK_HAI_PAINTED_REINFORCED_HELMET, new IsenUrukHelmetModel<>(IsenUrukHelmetModel.getTexturedModelData().createModel())),
        URUK_HAI_SOLDIER_HELMET(ModEquipmentItems.URUK_HAI_SOLDIER_HELMET, new IsenUrukHelmetModel<>(IsenUrukHelmetModel.getTexturedModelData().createModel())),
        URUK_HAI_PAINTED_SOLDIER_HELMET(ModEquipmentItems.URUK_HAI_PAINTED_SOLDIER_HELMET, new IsenUrukHelmetModel<>(IsenUrukHelmetModel.getTexturedModelData().createModel())),
        URUK_HAI_PLATE_HELMET(ModEquipmentItems.URUK_HAI_PLATE_HELMET, new IsenUrukHelmetModel<>(IsenUrukHelmetModel.getTexturedModelData().createModel())),
        URUK_HAI_PLATE_PAINTED_HELMET(ModEquipmentItems.URUK_HAI_PAINTED_PLATE_HELMET, new IsenUrukHelmetModel<>(IsenUrukHelmetModel.getTexturedModelData().createModel())),
        URUK_HAI_SAPPER_HELMET(ModEquipmentItems.URUK_HAI_SAPPER_HELMET, new IsenUrukHelmetModel<>(IsenUrukHelmetModel.getTexturedModelData().createModel())),
        URUK_HAI_SAPPER_PAINTED_HELMET(ModEquipmentItems.URUK_HAI_PAINTED_SAPPER_HELMET, new IsenUrukHelmetModel<>(IsenUrukHelmetModel.getTexturedModelData().createModel())),
        URUK_HAI_BERSERKER_HELMET(ModEquipmentItems.URUK_HAI_BERSERKER_HELMET, new IsenUrukHelmetModel<>(IsenUrukHelmetModel.getTexturedModelData().createModel())),
        URUK_HAI_BERSERKER_PAINTED_HELMET(ModEquipmentItems.URUK_HAI_PAINTED_BERSERKER_HELMET, new IsenUrukHelmetModel<>(IsenUrukHelmetModel.getTexturedModelData().createModel())),
        URUK_HAI_COMMANDER_HELMET(ModEquipmentItems.URUK_HAI_COMMANDER_HELMET, new IsenUrukHelmetModel<>(IsenUrukHelmetModel.getTexturedModelData().createModel())),
        URUK_HAI_COMMANDER_PAINTED_HELMET(ModEquipmentItems.URUK_HAI_PAINTED_COMMANDER_HELMET, new IsenUrukHelmetModel<>(IsenUrukHelmetModel.getTexturedModelData().createModel())),

        ORTHANC_GUARD_HELMET(ModEquipmentItems.ORTHANC_GUARD_HELMET, new OrthancCommanderHelmetModel<>(OrthancCommanderHelmetModel.getTexturedModelData().createModel())),
        ORTHANC_COMMANDER_HELMET(ModEquipmentItems.ORTHANC_COMMANDER_HELMET, new OrthancCommanderHelmetModel<>(OrthancCommanderHelmetModel.getTexturedModelData().createModel())),

        GUNDABAD_CAPTAIN_HELMET(ModEquipmentItems.GUNDABAD_CAPTAIN_HELMET, new HobgoblinPlateHelmetModel<>(HobgoblinPlateHelmetModel.getTexturedModelData().createModel())),
        GUNDABAD_SCREECHER_HELMET(ModEquipmentItems.GUNDABAD_SCREECHER_HELMET, new HobgoblinPlateHelmetModel<>(HobgoblinPlateHelmetModel.getTexturedModelData().createModel())),
        GUNDABAD_SEEKER_HELMET(ModEquipmentItems.GUNDABAD_SEEKER_HELMET, new HobgoblinPlateHelmetModel<>(HobgoblinPlateHelmetModel.getTexturedModelData().createModel())),
        GUNDABAD_SKULLCAP_HELMET(ModEquipmentItems.GUNDABAD_SKULLCAP_HELMET, new HobgoblinPlateHelmetModel<>(HobgoblinPlateHelmetModel.getTexturedModelData().createModel())),
        GUNDABAD_SOLDIER_HELMET(ModEquipmentItems.GUNDABAD_SOLDIER_HELMET, new HobgoblinPlateHelmetModel<>(HobgoblinPlateHelmetModel.getTexturedModelData().createModel())),

        GUNDABAD_HOBGOBLIN_PLATE_CRESTED_HELMET(ModEquipmentItems.GUNDABAD_HOBGOBLIN_PLATE_CRESTED_HELMET, new HobgoblinPlateHelmetModel<>(HobgoblinPlateHelmetModel.getTexturedModelData().createModel())),
        GUNDABAD_HOBGOBLIN_PLATE_LARGE_CREST_HELMET(ModEquipmentItems.GUNDABAD_HOBGOBLIN_PLATE_LARGE_CREST_HELMET, new HobgoblinPlateHelmetModel<>(HobgoblinPlateHelmetModel.getTexturedModelData().createModel())),
        GUNDABAD_HOBGOBLIN_PLATE_LONG_HORN_HELMET(ModEquipmentItems.GUNDABAD_HOBGOBLIN_PLATE_LONG_HORN_HELMET, new HobgoblinPlateHelmetModel<>(HobgoblinPlateHelmetModel.getTexturedModelData().createModel())),
        GUNDABAD_HOBGOBLIN_PLATE_SMALL_HORN_HELMET(ModEquipmentItems.GUNDABAD_HOBGOBLIN_PLATE_SMALL_HORN_HELMET, new HobgoblinPlateHelmetModel<>(HobgoblinPlateHelmetModel.getTexturedModelData().createModel())),

        HELMET_OF_HELM_HAMMERHAND(ModEquipmentItems.HELMET_OF_HELM_HAMMERHAND, new HammerhandHelmModel<>(HammerhandHelmModel.getTexturedModelData().createModel())),
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

        MORDOR_BLACK_NUMENOREAN_PLATE_CHESTPLATE(ModEquipmentItems.MORDOR_BLACK_NUMENOREAN_PLATE_CHESTPLATE, new MordorBNChestplateModel<>(MordorBNChestplateModel.getTexturedModelData().createModel())),

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

    public enum ModCapePairedModels implements StringIdentifiable {
        CAPE(ModCapes.CAPE, ModCapeModels.MEDIUM_MODELS),
        SURCOAT(ModCapes.SURCOAT, ModCapeModels.SURCOAT_MODELS),
        CLOAK(ModCapes.CLOAK, ModCapeModels.WIDE_MODELS),
        WANDERER_ROBES(ModCapes.WANDERER_ROBES, ModCapeModels.SURCOAT_MODELS),

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
        DALISH_HEYDAY_CLOAK(ModCapes.DALISH_HEYDAY_CLOAK, ModCapeModels.FUR_MODELS),
        BARDING_SERGEANT_CAPE(ModCapes.BARDING_SERGEANT_CAPE, ModCapeModels.MEDIUM_MODELS),

        EREBOR_CAPE(ModCapes.EREBOR_CAPE, ModCapeModels.MEDIUM_MODELS),
        RAVENHILL_SENTINEL_CAPE(ModCapes.RAVENHILL_SENTINEL_CAPE, ModCapeModels.WIDE_MODELS),

        LORIEN_MARCHWARDEN_CAPE(ModCapes.LORIEN_MARCHWARDEN_CAPE, ModCapeModels.MEDIUM_MODELS),
        GALADHRIM_CAPE(ModCapes.GALADHRIM_CAPE, ModCapeModels.SLIM_MODELS),
        GALADHRIM_LORD_SURCOAT(ModCapes.GALADHRIM_LORD_SURCOAT, ModCapeModels.SURCOAT_MODELS),

        ORCISH_CAPE(ModCapes.ORCISH_CAPE, ModCapeModels.MEDIUM_MODELS),
        ORCISH_LONG_CAPE(ModCapes.ORCISH_LONG_CAPE, ModCapeModels.MEDIUM_MODELS),
        ORCISH_SHOULDERS(ModCapes.ORCISH_SHOULDERS, ModCapeModels.SURCOAT_MODELS),

        ORCISH_BLACK_FUR_SURCOAT_WITH_BONE(ModCapes.ORCISH_BLACK_FUR_SURCOAT_WITH_BONE, ModCapeModels.SURCOAT_FUR_MODELS),
        ORCISH_BROWN_FUR_SURCOAT_WITH_BONE(ModCapes.ORCISH_BROWN_FUR_SURCOAT_WITH_BONE, ModCapeModels.SURCOAT_FUR_MODELS),
        ORCISH_GRAY_FUR_SURCOAT_WITH_BONE(ModCapes.ORCISH_GRAY_FUR_SURCOAT_WITH_BONE, ModCapeModels.SURCOAT_FUR_MODELS),
        ORCISH_TAN_FUR_SURCOAT_WITH_BONE(ModCapes.ORCISH_TAN_FUR_SURCOAT_WITH_BONE, ModCapeModels.SURCOAT_FUR_MODELS),
        ORCISH_WHITE_FUR_SURCOAT_WITH_BONE(ModCapes.ORCISH_WHITE_FUR_SURCOAT_WITH_BONE, ModCapeModels.SURCOAT_FUR_MODELS),

        MORDOR_BLACK_NUMENOREAN_CAPE(ModCapes.MORDOR_BLACK_NUMENOREAN_CAPE, ModCapeModels.MEDIUM_MODELS),
        NAZGUL_ROBES(ModCapes.NAZGUL_ROBES, ModCapeModels.NAZGUL_ROBES_MODEL),

        ORTHANC_GUARD_CAPE(ModCapes.ORTHANC_GUARD_CAPE, ModCapeModels.MEDIUM_MODELS),
        ;

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
        HOOD(ModHoods.HOOD, ModHoodModels.REGULAR_MODELS),
        TALL_HOOD(ModHoods.TALL_HOOD, ModHoodModels.TALL_MODELS),

        BLACK_FUR_HOOD(ModHoods.BLACK_FUR_HOOD, ModHoodModels.FUR_MODELS),
        BROWN_FUR_HOOD(ModHoods.BROWN_FUR_HOOD, ModHoodModels.FUR_MODELS),
        GRAY_FUR_HOOD(ModHoods.GRAY_FUR_HOOD, ModHoodModels.FUR_MODELS),
        TAN_FUR_HOOD(ModHoods.TAN_FUR_HOOD, ModHoodModels.FUR_MODELS),
        WHITE_FUR_HOOD(ModHoods.WHITE_FUR_HOOD, ModHoodModels.FUR_MODELS),

        GONDORIAN_CITADEL_GUARD_HOOD(ModHoods.GONDORIAN_CITADEL_GUARD_HOOD, ModHoodModels.REGULAR_MODELS),

        LORIEN_MARCHWARDEN_HOOD(ModHoods.LORIEN_MARCHWARDEN_HOOD, ModHoodModels.REGULAR_MODELS),
        GALADHRIM_HOOD(ModHoods.GALADHRIM_HOOD, ModHoodModels.REGULAR_MODELS),
        NAZGUL_HOOD(ModHoods.NAZGUL_HOOD, ModHoodModels.NAZGUL_MODELS),
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
