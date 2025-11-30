package net.sevenstars.middleearth.item.utils.armor;

import net.sevenstars.middleearth.client.model.equipment.chest.*;
import net.sevenstars.middleearth.client.model.equipment.head.helmets.*;
import net.sevenstars.middleearth.client.model.equipment.head.helmets.dwarves.DwarvenMinerHelmetModel;
import net.sevenstars.middleearth.client.model.equipment.head.helmets.dwarves.EreborHelmetModel;
import net.sevenstars.middleearth.client.model.equipment.head.helmets.dwarves.EreborHornsHelmetModel;
import net.sevenstars.middleearth.client.model.equipment.head.helmets.dwarves.EreborWingsHelmetModel;
import net.sevenstars.middleearth.client.model.equipment.head.helmets.elves.EgladilSentinelHelmetModel;
import net.sevenstars.middleearth.client.model.equipment.head.helmets.elves.LorienHelmetModel;
import net.sevenstars.middleearth.client.model.equipment.head.helmets.elves.woodlandrealm.WardenGladeHelmetModel;
import net.sevenstars.middleearth.client.model.equipment.head.helmets.elves.woodlandrealm.WoodlandRealmRoyalGuardHelmetModel;
import net.sevenstars.middleearth.client.model.equipment.head.helmets.elves.woodlandrealm.WoodlandRealmSoldierHelmetModel;
import net.sevenstars.middleearth.client.model.equipment.head.helmets.humans.dale.DaleFeatherHelmetModel;
import net.sevenstars.middleearth.client.model.equipment.head.helmets.humans.dale.DaleHelmetModel;
import net.sevenstars.middleearth.client.model.equipment.head.helmets.humans.dale.DaleSpikeFurHelmetModel;
import net.sevenstars.middleearth.client.model.equipment.head.helmets.humans.dale.DaleSpikeHelmetModel;
import net.sevenstars.middleearth.client.model.equipment.head.helmets.humans.gondor.CitadelGuardHelmetModel;
import net.sevenstars.middleearth.client.model.equipment.head.helmets.humans.gondor.FountainGuardHelmetModel;
import net.sevenstars.middleearth.client.model.equipment.head.helmets.humans.gondor.KingsGuardHelmetModel;
import net.sevenstars.middleearth.client.model.equipment.head.helmets.humans.hobbits.ShirriffHatModel;
import net.sevenstars.middleearth.client.model.equipment.head.helmets.humans.rohan.HammerhandHelmModel;
import net.sevenstars.middleearth.client.model.equipment.head.helmets.humans.rohan.RohanHairHelmetModel;
import net.sevenstars.middleearth.client.model.equipment.head.helmets.humans.rohan.RohanHelmetModel;
import net.sevenstars.middleearth.client.model.equipment.head.helmets.orcs.MandibleHelmetModel;
import net.sevenstars.middleearth.client.model.equipment.head.helmets.orcs.OrcSpikeHelmetModel;
import net.sevenstars.middleearth.client.model.equipment.head.helmets.orcs.goblintown.*;
import net.sevenstars.middleearth.client.model.equipment.head.helmets.orcs.hobgoblins.*;
import net.sevenstars.middleearth.client.model.equipment.head.helmets.orcs.isengard.*;
import net.sevenstars.middleearth.client.model.equipment.head.helmets.orcs.mordor.MordorBNHelmetModel;
import net.sevenstars.middleearth.client.model.equipment.head.helmets.orcs.mordor.MordorSnoutHelmetModel;
import net.sevenstars.middleearth.client.model.equipment.head.helmets.orcs.mordor.MordorSpikyHelmetModel;
import net.sevenstars.middleearth.client.model.equipment.head.helmets.orcs.moria.*;
import net.sevenstars.middleearth.client.model.equipment.head.helmets.orcs.mordor.dolguldur.*;
import net.sevenstars.middleearth.item.EquipmentItemsME;
import net.sevenstars.middleearth.item.utils.armor.backAttachments.BackAttachmentModelsME;
import net.sevenstars.middleearth.item.utils.armor.backAttachments.BackAttachmentsME;
import net.sevenstars.middleearth.item.utils.armor.helmetAttachments.HelmetAttachmentModelsME;
import net.sevenstars.middleearth.item.utils.armor.helmetAttachments.HelmetAttachmentsME;
import net.minecraft.item.Item;
import net.minecraft.util.StringIdentifiable;

public class ArmorModelsME {

    public enum ModHelmetModels{
        STRAW_HAT(EquipmentItemsME.STRAW_HAT, new HatHelmetModel(HatHelmetModel.getTexturedModelData().createModel())),
        WOVEN_HAT(EquipmentItemsME.WOVEN_HAT, new HatHelmetModel(HatHelmetModel.getTexturedModelData().createModel())),
        WANDERER_HAT(EquipmentItemsME.WANDERER_HAT, new WizardHatModel(WizardHatModel.getTexturedModelData().createModel())),
        KETTLE_HAT(EquipmentItemsME.KETTLE_HAT, new KettleHelmetModel(KettleHelmetModel.getTexturedModelData().createModel())),
        KETTLE_HAT_WITH_COIF(EquipmentItemsME.KETTLE_HAT_WITH_COIF, new KettleHelmetModel(KettleHelmetModel.getTexturedModelData().createModel())),
        KETTLE_HAT_WITH_CLOSED_COIF(EquipmentItemsME.KETTLE_HAT_WITH_CLOSED_COIF, new KettleHelmetModel(KettleHelmetModel.getTexturedModelData().createModel())),
        COOKING_POT_HELMET(EquipmentItemsME.COOKING_POT_HELMET, new CookingPotHelmetModel(CookingPotHelmetModel.getTexturedModelData().createModel())),
        WINGED_HELMET(EquipmentItemsME.WINGED_HELMET, new WingedHelmetModel(WingedHelmetModel.getTexturedModelData().createModel())),
        SALLET(EquipmentItemsME.SALLET, new SalletHelmetModel(SalletHelmetModel.getTexturedModelData().createModel())),

        SHIRRIF_HAT(EquipmentItemsME.SHIRRIFF_HAT, new ShirriffHatModel(ShirriffHatModel.getTexturedModelData().createModel())),

        GONDORIAN_CABASSET_HELMET(EquipmentItemsME.GONDORIAN_CABASSET_HELMET, new EggHelmetModel(EggHelmetModel.getTexturedModelData().createModel())),
        GONDORIAN_SOLDIER_HELMET(EquipmentItemsME.GONDORIAN_SOLDIER_HELMET, new EggHelmetModel(EggHelmetModel.getTexturedModelData().createModel())),
        GONDORIAN_PLATE_HELMET(EquipmentItemsME.GONDORIAN_PLATE_HELMET, new EggHelmetModel(EggHelmetModel.getTexturedModelData().createModel())),
        GONDORIAN_CAPTAIN_HELMET(EquipmentItemsME.GONDORIAN_CAPTAIN_HELMET, new EggHelmetModel(EggHelmetModel.getTexturedModelData().createModel())),
        GONDORIAN_KINGS_GUARD_HELMET(EquipmentItemsME.GONDORIAN_KINGS_GUARD_HELMET, new KingsGuardHelmetModel(KingsGuardHelmetModel.getTexturedModelData().createModel())),
        GONDORIAN_CITADEL_GUARD_HELMET(EquipmentItemsME.GONDORIAN_CITADEL_GUARD_HELMET, new CitadelGuardHelmetModel(CitadelGuardHelmetModel.getTexturedModelData().createModel())),
        GONDORIAN_FOUNTAIN_GUARD_HELMET(EquipmentItemsME.GONDORIAN_FOUNTAIN_GUARD_HELMET, new FountainGuardHelmetModel(FountainGuardHelmetModel.getTexturedModelData().createModel())),

        ROHIRRIC_REINFORCED_LEATHER_HELMET(EquipmentItemsME.ROHIRRIC_REINFORCED_LEATHER_HELMET, new RohanHelmetModel(RohanHelmetModel.getTexturedModelData().createModel())),
        ROHIRRIC_BRACED_MILITIA_HELMET(EquipmentItemsME.ROHIRRIC_BRACED_MILITIA_HELMET, new RohanHelmetModel(RohanHelmetModel.getTexturedModelData().createModel())),
        ROHIRRIC_ORNAMENTED_MILITIA_HELMET(EquipmentItemsME.ROHIRRIC_ORNAMENTED_MILITIA_HELMET, new RohanHairHelmetModel(RohanHairHelmetModel.getTexturedModelData().createModel())),
        ROHIRRIC_ORNAMENTED_SOLDIER_HELMET(EquipmentItemsME.ROHIRRIC_ORNAMENTED_SOLDIER_HELMET, new RohanHelmetModel(RohanHelmetModel.getTexturedModelData().createModel())),
        ROHIRRIC_ROYAL_GUARD_HELMET(EquipmentItemsME.ROHIRRIC_ROYAL_GUARD_HELMET, new RohanHairHelmetModel(RohanHairHelmetModel.getTexturedModelData().createModel())),
        EORLING_MARSHAL_HELMET(EquipmentItemsME.EORLING_MARSHAL_HELMET, new RohanHairHelmetModel(RohanHairHelmetModel.getTexturedModelData().createModel())),
        HORSE_LORD_HELMET(EquipmentItemsME.HORSE_LORD_HELMET, new RohanHelmetModel(RohanHelmetModel.getTexturedModelData().createModel())),

        DALISH_HELMET(EquipmentItemsME.DALISH_HELMET, new DaleSpikeHelmetModel(DaleSpikeHelmetModel.getTexturedModelData().createModel())),
        DALISH_HELMET_BLACK_FUR(EquipmentItemsME.DALISH_HELMET_BLACK_FUR, new DaleSpikeFurHelmetModel(DaleSpikeFurHelmetModel.getTexturedModelData().createModel())),
        DALISH_HELMET_BROWN_FUR(EquipmentItemsME.DALISH_HELMET_BROWN_FUR, new DaleSpikeFurHelmetModel(DaleSpikeFurHelmetModel.getTexturedModelData().createModel())),
        DALISH_HELMET_TAN_FUR(EquipmentItemsME.DALISH_HELMET_TAN_FUR, new DaleSpikeFurHelmetModel(DaleSpikeFurHelmetModel.getTexturedModelData().createModel())),
        DALISH_BURGONET(EquipmentItemsME.DALISH_BURGONET, new DaleHelmetModel(DaleHelmetModel.getTexturedModelData().createModel())),
        DALISH_HEYDAY_HELMET(EquipmentItemsME.DALISH_HEYDAY_HELMET, new DaleSpikeFurHelmetModel(DaleSpikeFurHelmetModel.getTexturedModelData().createModel())),
        BARDING_SOLDIER_HELMET(EquipmentItemsME.BARDING_SOLDIER_HELMET, new DaleHelmetModel(DaleHelmetModel.getTexturedModelData().createModel())),
        BARDING_SERGEANT_HELMET(EquipmentItemsME.BARDING_SERGEANT_HELMET, new DaleFeatherHelmetModel(DaleFeatherHelmetModel.getTexturedModelData().createModel())),

        DWARVEN_MINER_HELMET(EquipmentItemsME.DWARVEN_MINER_HELMET, new DwarvenMinerHelmetModel(DwarvenMinerHelmetModel.getTexturedModelData().createModel())),

        EREBOR_CAPTAIN_HELMET(EquipmentItemsME.EREBOR_CAPTAIN_HELMET, new EreborHornsHelmetModel(EreborHornsHelmetModel.getTexturedModelData().createModel())),

        EREBOR_PLATE_HELMET(EquipmentItemsME.EREBOR_PLATE_HELMET, new EreborHelmetModel(EreborHelmetModel.getTexturedModelData().createModel())),
        RAVENHILL_SENTINEL_HELMET(EquipmentItemsME.RAVENHILL_SENTINEL_HELMET, new EreborWingsHelmetModel(EreborWingsHelmetModel.getTexturedModelData().createModel())),
        EREBOR_GATEWARDEN_HELMET(EquipmentItemsME.EREBOR_GATEWARDEN_HELMET, new EreborHelmetModel(EreborHelmetModel.getTexturedModelData().createModel())),

        LORIEN_DIADEM(EquipmentItemsME.LORIEN_DIADEM, new LorienHelmetModel(LorienHelmetModel.getTexturedModelData().createModel())),
        LORIEN_LEATHER_HELMET(EquipmentItemsME.LORIEN_LEATHER_HELMET, new LorienHelmetModel(LorienHelmetModel.getTexturedModelData().createModel())),
        LORIEN_MAIL_COIF_DIADEM(EquipmentItemsME.LORIEN_MAIL_COIF_DIADEM, new LorienHelmetModel(LorienHelmetModel.getTexturedModelData().createModel())),
        LORIEN_SHORT_MAIL_COIF_DIADEM(EquipmentItemsME.LORIEN_SHORT_MAIL_COIF_DIADEM, new LorienHelmetModel(LorienHelmetModel.getTexturedModelData().createModel())),
        LORIEN_SOLDIER_HELMET(EquipmentItemsME.LORIEN_SOLDIER_HELMET, new LorienHelmetModel(LorienHelmetModel.getTexturedModelData().createModel())),
        GALADHRIM_HELMET(EquipmentItemsME.GALADHRIM_HELMET, new LorienHelmetModel(LorienHelmetModel.getTexturedModelData().createModel())),
        EGLADIL_SENTINEL_HELMET(EquipmentItemsME.EGLADIL_SENTINEL_HELMET, new EgladilSentinelHelmetModel(EgladilSentinelHelmetModel.getTexturedModelData().createModel())),
        EGLADIL_COMMANDER_HELMET(EquipmentItemsME.EGLADIL_COMMANDER_HELMET, new EgladilSentinelHelmetModel(EgladilSentinelHelmetModel.getTexturedModelData().createModel())),
        GALADHRIM_LORD_HELMET(EquipmentItemsME.GALADHRIM_LORD_HELMET, new LorienHelmetModel(LorienHelmetModel.getTexturedModelData().createModel())),

        WOODLAND_REALM_ROYAL_GUARD(EquipmentItemsME.WOODLAND_REALM_ROYAL_GUARD_HELMET, new WoodlandRealmRoyalGuardHelmetModel(WoodlandRealmRoyalGuardHelmetModel.getTexturedModelData().createModel())),
        WOODLAND_REALM_SOLDIER(EquipmentItemsME.WOODLAND_REALM_SOLDIER_HELMET, new WoodlandRealmSoldierHelmetModel(WoodlandRealmSoldierHelmetModel.getTexturedModelData().createModel())),
        WARDEN_OF_THE_GLADE(EquipmentItemsME.WARDEN_OF_THE_GLADE_HELMET, new WardenGladeHelmetModel(WardenGladeHelmetModel.getTexturedModelData().createModel())),

        ORCISH_BRACED_HELMET(EquipmentItemsME.ORCISH_BRACED_HELMET, new OrcSpikeHelmetModel(OrcSpikeHelmetModel.getTexturedModelData().createModel())),
        ORCISH_SALLET(EquipmentItemsME.ORCISH_SALLET, new SalletHelmetModel(SalletHelmetModel.getTexturedModelData().createModel())),

        MORDOR_CREST_HELMET(EquipmentItemsME.MORDOR_CREST_HELMET, new MordorSpikyHelmetModel(MordorSpikyHelmetModel.getTexturedModelData().createModel())),
        RUSTED_MORDOR_CREST_HELMET(EquipmentItemsME.RUSTED_MORDOR_CREST_HELMET, new MordorSpikyHelmetModel(MordorSpikyHelmetModel.getTexturedModelData().createModel())),
        MORDOR_GREAT_HELMET(EquipmentItemsME.MORDOR_GREAT_HELMET, new MordorSpikyHelmetModel(MordorSpikyHelmetModel.getTexturedModelData().createModel())),
        RUSTED_MORDOR_GREAT_HELMET(EquipmentItemsME.RUSTED_MORDOR_GREAT_HELMET, new MordorSpikyHelmetModel(MordorSpikyHelmetModel.getTexturedModelData().createModel())),
        MORDOR_KETTLE_HAT(EquipmentItemsME.MORDOR_KETTLE_HAT, new KettleHelmetModel(KettleHelmetModel.getTexturedModelData().createModel())),
        RUSTED_MORDOR_KETTLE_HAT(EquipmentItemsME.RUSTED_MORDOR_KETTLE_HAT, new KettleHelmetModel(KettleHelmetModel.getTexturedModelData().createModel())),
        MORDOR_KETTLE_HAT_WITH_COIF(EquipmentItemsME.MORDOR_KETTLE_HAT_WITH_COIF, new KettleHelmetModel(KettleHelmetModel.getTexturedModelData().createModel())),
        RUSTED_MORDOR_KETTLE_HAT_WITH_COIF(EquipmentItemsME.RUSTED_MORDOR_KETTLE_HAT_WITH_COIF, new KettleHelmetModel(KettleHelmetModel.getTexturedModelData().createModel())),
        MORDOR_SALLET(EquipmentItemsME.MORDOR_SALLET, new SalletHelmetModel(SalletHelmetModel.getTexturedModelData().createModel())),
        RUSTED_MORDOR_SALLET(EquipmentItemsME.RUSTED_MORDOR_SALLET, new SalletHelmetModel(SalletHelmetModel.getTexturedModelData().createModel())),
        MORDOR_SNOUT_HELMET(EquipmentItemsME.MORDOR_SNOUT_HELMET, new MordorSnoutHelmetModel(MordorSnoutHelmetModel.getTexturedModelData().createModel())),
        RUSTED_MORDOR_SNOUT_HELMET(EquipmentItemsME.RUSTED_MORDOR_SNOUT_HELMET, new MordorSnoutHelmetModel(MordorSnoutHelmetModel.getTexturedModelData().createModel())),

        MORDOR_BLACK_NUMENOREAN_PLATE_HELMET(EquipmentItemsME.MORDOR_BLACK_NUMENOREAN_PLATE_HELMET, new MordorBNHelmetModel(MordorBNHelmetModel.getTexturedModelData().createModel())),

        DOL_GULDUR_EXECUTIONER_HOOD(EquipmentItemsME.DOL_GULDUR_EXECUTIONER_HOOD, new FlapHelmetModel(FlapHelmetModel.getTexturedModelData().createModel())),
        DOL_GULDUR_RAIDER_HELMET(EquipmentItemsME.DOL_GULDUR_RAIDER_HELMET, new DGRaiderHelmetModel(DGRaiderHelmetModel.getTexturedModelData().createModel())),
        WEATHERED_DOL_GULDUR_RAIDER_HELMET(EquipmentItemsME.WEATHERED_DOL_GULDUR_RAIDER_HELMET, new DGRaiderHelmetModel(DGRaiderHelmetModel.getTexturedModelData().createModel())),
        DOL_GULDUR_HUNTER_HELMET(EquipmentItemsME.DOL_GULDUR_HUNTER_HELMET, new DGHunterHelmetModel(DGHunterHelmetModel.getTexturedModelData().createModel())),
        BLACK_REAVER_HELMET(EquipmentItemsME.BLACK_REAVER_HELMET, new BlackReaverHelmetModel(BlackReaverHelmetModel.getTexturedModelData().createModel())),
        BLACK_CASTELLAN_HELMET(EquipmentItemsME.BLACK_CASTELLAN_HELMET, new BlackCastellanHelmetModel(BlackCastellanHelmetModel.getTexturedModelData().createModel())),
        GULDUR_MARAUDER_HELMET(EquipmentItemsME.DOL_GULDUR_MARAUDER_HELMET, new DGMarauderHelmetModel(DGMarauderHelmetModel.getTexturedModelData().createModel())),
        WEATHERED_DOL_GULDUR_MARAUDER_HELMET(EquipmentItemsME.WEATHERED_DOL_GULDUR_MARAUDER_HELMET, new DGMarauderHelmetModel(DGMarauderHelmetModel.getTexturedModelData().createModel())),
        DOL_GULDUR_STALKER_HELMET(EquipmentItemsME.DOL_GULDUR_STALKER_HELMET, new DGStalkerHelmetModel(DGStalkerHelmetModel.getTexturedModelData().createModel())),
        WEATHERED_DOL_GULDUR_STALKER_HELMET(EquipmentItemsME.WEATHERED_DOL_GULDUR_STALKER_HELMET, new DGStalkerHelmetModel(DGStalkerHelmetModel.getTexturedModelData().createModel())),

        URUK_HAI_LEATHER_SCOUT_CAP(EquipmentItemsME.URUK_HAI_LEATHER_SCOUT_CAP, new IsenUrukScoutHelmetModel(IsenUrukScoutHelmetModel.getTexturedModelData().createModel())),
        URUK_HAI_LIGHT_HELMET(EquipmentItemsME.URUK_HAI_LIGHT_HELMET, new IsenUrukCrestHelmetModel(IsenUrukCrestHelmetModel.getTexturedModelData().createModel())),
        URUK_HAI_PAINTED_LIGHT_HELMET(EquipmentItemsME.URUK_HAI_PAINTED_LIGHT_HELMET, new IsenUrukCrestHelmetModel(IsenUrukCrestHelmetModel.getTexturedModelData().createModel())),
        URUK_HAI_REINFORCED_HELMET(EquipmentItemsME.URUK_HAI_REINFORCED_HELMET, new IsenUrukReinforcedHelmetModel(IsenUrukReinforcedHelmetModel.getTexturedModelData().createModel())),
        URUK_HAI_PAINTED_REINFORCED_HELMET(EquipmentItemsME.URUK_HAI_PAINTED_REINFORCED_HELMET, new IsenUrukReinforcedHelmetModel(IsenUrukReinforcedHelmetModel.getTexturedModelData().createModel())),
        URUK_HAI_SOLDIER_HELMET(EquipmentItemsME.URUK_HAI_SOLDIER_HELMET, new IsenUrukSoldierHelmetModel(IsenUrukSoldierHelmetModel.getTexturedModelData().createModel())),
        URUK_HAI_PAINTED_SOLDIER_HELMET(EquipmentItemsME.URUK_HAI_PAINTED_SOLDIER_HELMET, new IsenUrukSoldierHelmetModel(IsenUrukSoldierHelmetModel.getTexturedModelData().createModel())),

        URUK_HAI_PLATE_HELMET(EquipmentItemsME.URUK_HAI_PLATE_HELMET, new IsenUrukPlateHelmetModel(IsenUrukPlateHelmetModel.getTexturedModelData().createModel())),
        URUK_HAI_PLATE_PAINTED_HELMET(EquipmentItemsME.URUK_HAI_PAINTED_PLATE_HELMET, new IsenUrukPlateHelmetModel(IsenUrukPlateHelmetModel.getTexturedModelData().createModel())),
        URUK_HAI_SAPPER_HELMET(EquipmentItemsME.URUK_HAI_SAPPER_HELMET, new IsenUrukSapperHelmetModel(IsenUrukSapperHelmetModel.getTexturedModelData().createModel())),
        URUK_HAI_SAPPER_PAINTED_HELMET(EquipmentItemsME.URUK_HAI_PAINTED_SAPPER_HELMET, new IsenUrukSapperHelmetModel(IsenUrukSapperHelmetModel.getTexturedModelData().createModel())),
        URUK_HAI_BERSERKER_HELMET(EquipmentItemsME.URUK_HAI_BERSERKER_HELMET, new IsenUrukHelmetModel(IsenUrukHelmetModel.getTexturedModelData().createModel())),
        URUK_HAI_BERSERKER_PAINTED_HELMET(EquipmentItemsME.URUK_HAI_PAINTED_BERSERKER_HELMET, new IsenUrukHelmetModel(IsenUrukHelmetModel.getTexturedModelData().createModel())),
        URUK_HAI_COMMANDER_HELMET(EquipmentItemsME.URUK_HAI_COMMANDER_HELMET, new IsenUrukCommanderHelmetModel(IsenUrukCommanderHelmetModel.getTexturedModelData().createModel())),
        URUK_HAI_COMMANDER_PAINTED_HELMET(EquipmentItemsME.URUK_HAI_PAINTED_COMMANDER_HELMET, new IsenUrukCommanderHelmetModel(IsenUrukCommanderHelmetModel.getTexturedModelData().createModel())),

        ORTHANC_GUARD_HELMET(EquipmentItemsME.ORTHANC_GUARD_HELMET, new OrthancGuardHelmetModel(OrthancGuardHelmetModel.getTexturedModelData().createModel())),
        ORTHANC_COMMANDER_HELMET(EquipmentItemsME.ORTHANC_COMMANDER_HELMET, new OrthancCommanderHelmetModel(OrthancCommanderHelmetModel.getTexturedModelData().createModel())),

        GUNDABAD_CAPTAIN_HELMET(EquipmentItemsME.GUNDABAD_CAPTAIN_HELMET, new HobgoblinCaptainHelmetModel(HobgoblinCaptainHelmetModel.getTexturedModelData().createModel())),
        GUNDABAD_SCREECHER_HELMET(EquipmentItemsME.GUNDABAD_SCREECHER_HELMET, new HobgoblinScreecherHelmetModel(HobgoblinScreecherHelmetModel.getTexturedModelData().createModel())),
        GUNDABAD_SEEKER_HELMET(EquipmentItemsME.GUNDABAD_SEEKER_HELMET, new HobgoblinSeekerHelmetModel(HobgoblinSeekerHelmetModel.getTexturedModelData().createModel())),
        GUNDABAD_SKULLCAP_HELMET(EquipmentItemsME.GUNDABAD_SKULLCAP_HELMET, new FlapHelmetModel(FlapHelmetModel.getTexturedModelData().createModel())),
        GUNDABAD_SOLDIER_HELMET(EquipmentItemsME.GUNDABAD_SOLDIER_HELMET, new HobgoblinCrestHelmetModel(HobgoblinCrestHelmetModel.getTexturedModelData().createModel())),

        GUNDABAD_HOBGOBLIN_PLATE_CRESTED_HELMET(EquipmentItemsME.GUNDABAD_HOBGOBLIN_PLATE_CRESTED_HELMET, new HobgoblinCrestedHelmetModel(HobgoblinCrestedHelmetModel.getTexturedModelData().createModel())),
        GUNDABAD_HOBGOBLIN_PLATE_LARGE_CREST_HELMET(EquipmentItemsME.GUNDABAD_HOBGOBLIN_PLATE_LARGE_CREST_HELMET, new HobgoblinLargeCrestHelmetModel(HobgoblinLargeCrestHelmetModel.getTexturedModelData().createModel())),
        GUNDABAD_HOBGOBLIN_PLATE_LONG_HORN_HELMET(EquipmentItemsME.GUNDABAD_HOBGOBLIN_PLATE_LONG_HORN_HELMET, new HobgoblinCrestHelmetModel(HobgoblinCrestHelmetModel.getTexturedModelData().createModel())),
        GUNDABAD_HOBGOBLIN_PLATE_SMALL_HORN_HELMET(EquipmentItemsME.GUNDABAD_HOBGOBLIN_PLATE_SMALL_HORN_HELMET, new HobgoblinSmallHornHelmetModel(HobgoblinSmallHornHelmetModel.getTexturedModelData().createModel())),

        MORIA_GOBLIN_SNAGA_NASAL_HELMET(EquipmentItemsME.MORIA_GOBLIN_SNAGA_NASAL_HELMET, new MoriaNasalHelmetModel(MoriaNasalHelmetModel.getTexturedModelData().createModel())),
        MORIA_GOBLIN_SNAGA_HELMET(EquipmentItemsME.MORIA_GOBLIN_SNAGA_HELMET, new MoriaSnagaHelmetModel(MoriaSnagaHelmetModel.getTexturedModelData().createModel())),
        MORIA_GOBLIN_CHARGER_HELMET(EquipmentItemsME.MORIA_GOBLIN_CHARGER_HELMET, new MoriaChargerHelmetModel(MoriaChargerHelmetModel.getTexturedModelData().createModel())),
        MORIA_RUINED_DWARVEN_HELMET(EquipmentItemsME.MORIA_RUINED_DWARVEN_HELMET, new MoriaNasalHelmetModel(MoriaNasalHelmetModel.getTexturedModelData().createModel())),
        MORIA_GOBLIN_BITER_HELMET(EquipmentItemsME.MORIA_GOBLIN_BITER_HELMET, new MoriaScreecherHelmetModel(MoriaScreecherHelmetModel.getTexturedModelData().createModel())),
        MORIA_GOBLIN_MANDIBLE_HELMET(EquipmentItemsME.MORIA_GOBLIN_MANDIBLE_HELMET, new MandibleHelmetModel(MandibleHelmetModel.getTexturedModelData().createModel())),
        MORIA_GOBLIN_CAPTAIN_HELMET(EquipmentItemsME.MORIA_GOBLIN_CAPTAIN_HELMET, new MoriaCaptainHelmetModel(MoriaCaptainHelmetModel.getTexturedModelData().createModel())),
        MORIA_GOBLIN_SCREECHER_HELMET(EquipmentItemsME.MORIA_GOBLIN_SCREECHER_HELMET, new MoriaScreecherHelmetModel(MoriaScreecherHelmetModel.getTexturedModelData().createModel())),
        MORIA_GOBLIN_CHIEF_HELMET(EquipmentItemsME.MORIA_GOBLIN_CHIEF_HELMET, new MoriaChiefHelmetModel(MoriaChiefHelmetModel.getTexturedModelData().createModel())),

        GOBLIN_KING_CROWN(EquipmentItemsME.GOBLIN_KING_CROWN, new GoblinKingCrownModel(GoblinKingCrownModel.getTexturedModelData().createModel())),
        GOBLIN_TOWN_CAP(EquipmentItemsME.GOBLIN_TOWN_CAP, new FlapHelmetModel(FlapHelmetModel.getTexturedModelData().createModel())),
        GOBLIN_TOWN_BONE_MANDIBLE_CAP(EquipmentItemsME.GOBLIN_TOWN_BONE_MANDIBLE_CAP, new GoblinTownBoneMandibleModel(GoblinTownBoneMandibleModel.getTexturedModelData().createModel())),
        GOBLIN_TOWN_CROSSBONES_HELMET(EquipmentItemsME.GOBLIN_TOWN_CROSSBONES_HELMET, new GoblinTownCrossbonesHelmetModel(GoblinTownCrossbonesHelmetModel.getTexturedModelData().createModel())),
        GOBLIN_TOWN_NASAL_HELMET(EquipmentItemsME.GOBLIN_TOWN_NASAL_HELMET, new MoriaSnagaHelmetModel(MoriaSnagaHelmetModel.getTexturedModelData().createModel())),
        GOBLIN_TOWN_SKULL_CAP(EquipmentItemsME.GOBLIN_TOWN_SKULL_CAP, new GoblinTownSkullHelmetModel(GoblinTownSkullHelmetModel.getTexturedModelData().createModel())),
        GOBLIN_TOWN_TUNNELER_HELMET(EquipmentItemsME.GOBLIN_TOWN_TUNNELER_HELMET, new GoblinTownTunnelerHelmetModel(GoblinTownTunnelerHelmetModel.getTexturedModelData().createModel())),
        GOBLIN_TOWN_SKULKER_GUARD_HELMET(EquipmentItemsME.GOBLIN_TOWN_SKULKER_GUARD_HELMET, new SkulkerGuardHelmetModel(SkulkerGuardHelmetModel.getTexturedModelData().createModel())),
        GOBLIN_TOWN_HEAVY_NASAL_HELMET(EquipmentItemsME.GOBLIN_TOWN_HEAVY_NASAL_HELMET, new GoblinTownNasalHelmetModel(GoblinTownNasalHelmetModel.getTexturedModelData().createModel())),
        GOBLIN_TOWN_MANDIBLE_HELMET(EquipmentItemsME.GOBLIN_TOWN_MANDIBLE_HELMET, new MandibleHelmetModel(MandibleHelmetModel.getTexturedModelData().createModel())),

        HELMET_OF_HELM_HAMMERHAND(EquipmentItemsME.HELMET_OF_HELM_HAMMERHAND, new HammerhandHelmModel(HammerhandHelmModel.getTexturedModelData().createModel())),
        ;

        private final Item item;
        private final HelmetAddonModel model;

        ModHelmetModels(Item item, HelmetAddonModel model){
            this.item = item;
            this.model = model;
        }

        public Item getItem() {
            return item;
        }

        public HelmetAddonModel getModel() {
            return model;
        }
    }

    public enum ModChestplateModels{
        GONDORIAN_PLATE_CHESTPLATE(EquipmentItemsME.GONDORIAN_PLATE_CHESTPLATE, new HaltChestplateModel(HaltChestplateModel.getTexturedModelData().createModel())),
        GONDORIAN_KINGS_GUARD_CHESTPLATE(EquipmentItemsME.GONDORIAN_KINGS_GUARD_CHESTKPLATE, new HaltChestplateModel(HaltChestplateModel.getTexturedModelData().createModel())),
        GONDORIAN_FOUNTAIN_GUARD_CHESTPLATE(EquipmentItemsME.GONDORIAN_FOUNTAIN_GUARD_CHESTPLATE, new HaltChestplateModel(HaltChestplateModel.getTexturedModelData().createModel())),

        EREBOR_PLATE_CHESTPLATE(EquipmentItemsME.EREBOR_PLATE_CHESTPLATE, new HaltChestplateModel(HaltChestplateModel.getTexturedModelData().createModel())),
        RAVENHILL_WATCHWARDEN_CHESTPLATE(EquipmentItemsME.RAVENHILL_WATCHWARDEN_CHESTPLATE, new HaltChestplateModel(HaltChestplateModel.getTexturedModelData().createModel())),
        RAVENHILL_SENTINEL_CHESTPLATE(EquipmentItemsME.RAVENHILL_SENTINEL_CHESTPLATE, new HaltChestplateModel(HaltChestplateModel.getTexturedModelData().createModel())),
        EREBOR_GATEWARDEN_CHESTPLATE(EquipmentItemsME.EREBOR_GATEWARDEN_CHESTPLATE, new HaltChestplateModel(HaltChestplateModel.getTexturedModelData().createModel())),

        BLACK_URUK_PLATE_CHESTPLATE(EquipmentItemsME.BLACK_URUK_PLATE_CHESTPLATE, new HaltChestplateModel(HaltChestplateModel.getTexturedModelData().createModel())),
        BLACK_URUK_COMMANDER_CHESTPLATE(EquipmentItemsME.BLACK_URUK_COMMANDER_CHESTPLATE, new HaltChestplateModel(HaltChestplateModel.getTexturedModelData().createModel())),

        MORDOR_BLACK_NUMENOREAN_PLATE_CHESTPLATE(EquipmentItemsME.MORDOR_BLACK_NUMENOREAN_PLATE_CHESTPLATE, new MordorBNChestplateModel(MordorBNChestplateModel.getTexturedModelData().createModel())),

        DOL_GULDUR_MARAUDER_CHESTPLATE(EquipmentItemsME.DOL_GULDUR_MARAUDER_CHESTPLATE, new DGMarauderChestplateModel(DGMarauderChestplateModel.getTexturedModelData().createModel())),
        WEATHERED_DOL_GULDUR_MARAUDER_CHESTPLATE(EquipmentItemsME.WEATHERED_DOL_GULDUR_MARAUDER_CHESTPLATE, new DGMarauderChestplateModel(DGMarauderChestplateModel.getTexturedModelData().createModel())),

        DOL_GULDUR_STALKER_CHESTPLATE(EquipmentItemsME.DOL_GULDUR_STALKER_CHESTPLATE, new DGStalkerChestplateModel(DGStalkerChestplateModel.getTexturedModelData().createModel())),
        WEATHERED_DOL_GULDUR_STALKER_CHESTPLATE(EquipmentItemsME.WEATHERED_DOL_GULDUR_STALKER_CHESTPLATE, new DGStalkerChestplateModel(DGStalkerChestplateModel.getTexturedModelData().createModel())),

        BLACK_CASTELLAN_CHESTPLATE(EquipmentItemsME.BLACK_CASTELLAN_CHESTPLATE, new BlackCastellanChestplateModel(BlackCastellanChestplateModel.getTexturedModelData().createModel())),

        URUK_HAI_PLATE_CHESTPLATE(EquipmentItemsME.URUK_HAI_PLATE_CHESTPLATE, new HaltChestplateModel(HaltChestplateModel.getTexturedModelData().createModel())),

        GUNDABAD_HOBGOBLIN_PLATE_CHESTPLATE(EquipmentItemsME.GUNDABAD_HOBGOBLIN_PLATE_CHESTPLATE, new HobgoblinPlateChestplateModel(HobgoblinPlateChestplateModel.getTexturedModelData().createModel())),

        MORIA_GOBLIN_SNAGA_CUIRASS(EquipmentItemsME.MORIA_GOBLIN_SNAGA_CUIRASS, new GoblinChestplateModel(GoblinChestplateModel.getTexturedModelData().createModel())),
        MORIA_GOBLIN_SNAGA_BELLY_PLATE(EquipmentItemsME.MORIA_GOBLIN_SNAGA_BELLY_PLATE, new GoblinChestplateModel(GoblinChestplateModel.getTexturedModelData().createModel())),
        MORIA_GOBLIN_HAUBERK(EquipmentItemsME.MORIA_GOBLIN_HAUBERK, new GoblinChestplateModel(GoblinChestplateModel.getTexturedModelData().createModel())),
        MORIA_RUINED_DWARVEN_HAUBERK(EquipmentItemsME.MORIA_RUINED_DWARVEN_HAUBERK, new GoblinChestplateModel(GoblinChestplateModel.getTexturedModelData().createModel())),
        MORIA_GOBLIN_REINFORCED_COAT(EquipmentItemsME.MORIA_GOBLIN_REINFORCED_COAT, new GoblinChestplateModel(GoblinChestplateModel.getTexturedModelData().createModel())),

        MORIA_GOBLIN_SCREECHER_CHESTPLATE(EquipmentItemsME.MORIA_GOBLIN_SCREECHER_CHESTPLATE, new GoblinChestplateModel(GoblinChestplateModel.getTexturedModelData().createModel())),
        MORIA_GOBLIN_CHIEF_CHESTPLATE(EquipmentItemsME.MORIA_GOBLIN_CHIEF_CHESTPLATE, new GoblinChestplateModel(GoblinChestplateModel.getTexturedModelData().createModel())),

        GOBLIN_TOWN_BONE_SCALE_COAT(EquipmentItemsME.GOBLIN_TOWN_BONE_SCALE_COAT, new GoblinChestplateModel(GoblinChestplateModel.getTexturedModelData().createModel())),
        GOBLIN_TOWN_CARAPACE_HARNESS(EquipmentItemsME.GOBLIN_TOWN_CARAPACE_HARNESS, new GoblinChestplateModel(GoblinChestplateModel.getTexturedModelData().createModel())),
        GOBLIN_TOWN_CRUDE_SCALE_CHESTPLATE(EquipmentItemsME.GOBLIN_TOWN_CRUDE_SCALE_CHESTPLATE, new GoblinChestplateModel(GoblinChestplateModel.getTexturedModelData().createModel())),
        GOBLIN_TOWN_REINFORCED_CARAPACE(EquipmentItemsME.GOBLIN_TOWN_REINFORCED_CARAPACE, new GoblinChestplateModel(GoblinChestplateModel.getTexturedModelData().createModel())),
        ;

        private final Item item;
        private final ChestplateAddonModel model;

        ModChestplateModels(Item item, ChestplateAddonModel model){
            this.item = item;
            this.model = model;
        }

        public Item getItem() {
            return item;
        }

        public ChestplateAddonModel getModel() {
            return model;
        }
    }

    public enum ModBackAttachmentPairedModels implements StringIdentifiable {
        CAPE(BackAttachmentsME.CAPE, BackAttachmentModelsME.MEDIUM_MODELS),
        SHOULDER_CAPE_LEFT(BackAttachmentsME.SHOULDER_CAPE_LEFT, BackAttachmentModelsME.WIDE_MODELS),
        SHOULDER_CAPE_RIGHT(BackAttachmentsME.SHOULDER_CAPE_RIGHT, BackAttachmentModelsME.WIDE_MODELS),
        SURCOAT(BackAttachmentsME.SURCOAT, BackAttachmentModelsME.SURCOAT_MODELS),
        CLOAK(BackAttachmentsME.CLOAK, BackAttachmentModelsME.WIDE_MODELS),
        WANDERER_ROBES(BackAttachmentsME.WANDERER_ROBES, BackAttachmentModelsME.SURCOAT_MODELS),
        AUTUMN_LEAF_CAPE(BackAttachmentsME.AUTUMN_LEAF_CAPE, BackAttachmentModelsME.MEDIUM_MODELS),

        BLACK_FUR_CLOAK(BackAttachmentsME.BLACK_FUR_CLOAK, BackAttachmentModelsME.FUR_MODELS),
        BROWN_FUR_CLOAK(BackAttachmentsME.BROWN_FUR_CLOAK, BackAttachmentModelsME.FUR_MODELS),
        GRAY_FUR_CLOAK(BackAttachmentsME.GRAY_FUR_CLOAK, BackAttachmentModelsME.FUR_MODELS),
        TAN_FUR_CLOAK(BackAttachmentsME.TAN_FUR_CLOAK, BackAttachmentModelsME.FUR_MODELS),
        WHITE_FUR_CLOAK(BackAttachmentsME.WHITE_FUR_CLOAK, BackAttachmentModelsME.FUR_MODELS),

        BLACK_FUR(BackAttachmentsME.BLACK_FUR, BackAttachmentModelsME.FUR_MODELS),
        BROWN_FUR(BackAttachmentsME.BROWN_FUR, BackAttachmentModelsME.FUR_MODELS),
        GRAY_FUR(BackAttachmentsME.GRAY_FUR, BackAttachmentModelsME.FUR_MODELS),
        TAN_FUR(BackAttachmentsME.TAN_FUR, BackAttachmentModelsME.FUR_MODELS),
        WHITE_FUR(BackAttachmentsME.WHITE_FUR, BackAttachmentModelsME.FUR_MODELS),

        GONDORIAN_CAPTAIN_CAPE(BackAttachmentsME.GONDORIAN_CAPTAIN_CAPE, BackAttachmentModelsME.MEDIUM_MODELS),
        GONDORIAN_HERO_CAPE(BackAttachmentsME.GONDORIAN_HERO_CAPE, BackAttachmentModelsME.MEDIUM_MODELS),
        GONDORIAN_KINGS_GUARD_CAPE(BackAttachmentsME.GONDORIAN_KINGS_GUARD_CAPE, BackAttachmentModelsME.MEDIUM_MODELS),
        GONDORIAN_CITADEL_GUARD_CAPE(BackAttachmentsME.GONDORIAN_CITADEL_GUARD_CAPE, BackAttachmentModelsME.MEDIUM_MODELS),
        GONDORIAN_FOUNTAIN_GUARD_CAPE(BackAttachmentsME.GONDORIAN_FOUNTAIN_GUARD_CAPE, BackAttachmentModelsME.WIDE_PAULDRON_MODELS),

        ROHIRRIC_CAPE(BackAttachmentsME.ROHIRRIC_CAPE, BackAttachmentModelsME.MEDIUM_MODELS),
        ROHIRRIC_ROYAL_GUARD_CAPE(BackAttachmentsME.ROHIRRIC_ROYAL_GUARD_CAPE, BackAttachmentModelsME.MEDIUM_MODELS),
        EORLING_MARSHAL_CAPE(BackAttachmentsME.EORLING_MARSHAL_CAPE, BackAttachmentModelsME.MEDIUM_MODELS),
        HORSE_LORD_CAPE(BackAttachmentsME.HORSE_LORD_CAPE, BackAttachmentModelsME.MEDIUM_MODELS),

        BARDING_SURCOAT(BackAttachmentsME.BARDING_SURCOAT, BackAttachmentModelsME.SURCOAT_MODELS),
        DALISH_HEYDAY_CLOAK(BackAttachmentsME.DALISH_HEYDAY_CLOAK, BackAttachmentModelsME.FUR_MODELS),
        BARDING_SERGEANT_CAPE(BackAttachmentsME.BARDING_SERGEANT_CAPE, BackAttachmentModelsME.MEDIUM_MODELS),

        EREBOR_CAPE(BackAttachmentsME.EREBOR_CAPE, BackAttachmentModelsME.MEDIUM_MODELS),
        RAVENHILL_SENTINEL_CAPE(BackAttachmentsME.RAVENHILL_SENTINEL_CAPE, BackAttachmentModelsME.WIDE_MODELS),

        SILVAN_LORD_CAPE(BackAttachmentsME.SILVAN_LORD_CAPE, BackAttachmentModelsME.MEDIUM_MODELS),

        LORIEN_MARCHWARDEN_CAPE(BackAttachmentsME.LORIEN_MARCHWARDEN_CAPE, BackAttachmentModelsME.MEDIUM_MODELS),
        GALADHRIM_CAPE(BackAttachmentsME.GALADHRIM_CAPE, BackAttachmentModelsME.SLIM_MODELS),
        EGLADIL_SENTINEL_CAPE(BackAttachmentsME.EGLADIL_SENTINEL_CAPE, BackAttachmentModelsME.MEDIUM_MODELS),
        GALADHRIM_LORD_SURCOAT(BackAttachmentsME.GALADHRIM_LORD_SURCOAT, BackAttachmentModelsME.SURCOAT_MODELS),
        GALADHRIM_LORD_CLOAK(BackAttachmentsME.GALADHRIM_LORD_CLOAK, BackAttachmentModelsME.WIDE_PAULDRON_MODELS),

        WOODLAND_REALM_ROYAL_GUARD_CAPE(BackAttachmentsME.WOODLAND_REALM_ROYAL_GUARD_CAPE, BackAttachmentModelsME.WIDE_PAULDRON_MODELS),
        WOODLAND_REALM_SOLDIER_CAPE(BackAttachmentsME.WOODLAND_REALM_SOLDIER_CAPE, BackAttachmentModelsME.MEDIUM_MODELS),
        WARDEN_OF_THE_GLADE_CAPE(BackAttachmentsME.WARDEN_OF_THE_GLADE_CAPE, BackAttachmentModelsME.MEDIUM_MODELS),

        ORCISH_CAPE(BackAttachmentsME.ORCISH_CAPE, BackAttachmentModelsME.MEDIUM_MODELS),
        ORCISH_LONG_CAPE(BackAttachmentsME.ORCISH_LONG_CAPE, BackAttachmentModelsME.MEDIUM_MODELS),
        ORCISH_SHOULDERS(BackAttachmentsME.ORCISH_SHOULDERS, BackAttachmentModelsME.SURCOAT_MODELS),

        ORCISH_BLACK_FUR_SURCOAT_WITH_BONE(BackAttachmentsME.ORCISH_BLACK_FUR_SURCOAT_WITH_BONE, BackAttachmentModelsME.SURCOAT_FUR_MODELS),
        ORCISH_BROWN_FUR_SURCOAT_WITH_BONE(BackAttachmentsME.ORCISH_BROWN_FUR_SURCOAT_WITH_BONE, BackAttachmentModelsME.SURCOAT_FUR_MODELS),
        ORCISH_GRAY_FUR_SURCOAT_WITH_BONE(BackAttachmentsME.ORCISH_GRAY_FUR_SURCOAT_WITH_BONE, BackAttachmentModelsME.SURCOAT_FUR_MODELS),
        ORCISH_TAN_FUR_SURCOAT_WITH_BONE(BackAttachmentsME.ORCISH_TAN_FUR_SURCOAT_WITH_BONE, BackAttachmentModelsME.SURCOAT_FUR_MODELS),
        ORCISH_WHITE_FUR_SURCOAT_WITH_BONE(BackAttachmentsME.ORCISH_WHITE_FUR_SURCOAT_WITH_BONE, BackAttachmentModelsME.SURCOAT_FUR_MODELS),

        MORDOR_BLACK_NUMENOREAN_CAPE(BackAttachmentsME.MORDOR_BLACK_NUMENOREAN_CAPE, BackAttachmentModelsME.MEDIUM_MODELS),
        NAZGUL_ROBES(BackAttachmentsME.NAZGUL_ROBES, BackAttachmentModelsME.NAZGUL_ROBES_MODEL),
        BLACK_CASTELLAN_CAPE(BackAttachmentsME.BLACK_CASTELLAN_CAPE, BackAttachmentModelsME.MEDIUM_MODELS),
        BLACK_REAVER_SHOULDER_CAPE(BackAttachmentsME.BLACK_REAVER_SHOULDER_CAPE, BackAttachmentModelsME.WIDE_MODELS),
        DOL_GULDUR_STALKER_CAPE(BackAttachmentsME.DOL_GULDUR_STALKER_CAPE, BackAttachmentModelsME.MEDIUM_MODELS),

        ORTHANC_GUARD_CAPE(BackAttachmentsME.ORTHANC_GUARD_CAPE, BackAttachmentModelsME.MEDIUM_MODELS),

        SKULL_SPIKES(BackAttachmentsME.SKULL_SPIKES, BackAttachmentModelsME.SKULL_SPIKES),
        ;

        private final BackAttachmentsME backAttachment;
        private final BackAttachmentModelsME models;


        ModBackAttachmentPairedModels(BackAttachmentsME backAttachment, BackAttachmentModelsME models){
            this.backAttachment = backAttachment;
            this.models = models;
        }

        public BackAttachmentModelsME getModel() {
            return models;
        }

        @Override
        public String asString() {
            return "";
        }
    }


    public enum ModHelmetAttachmentPairedModels implements StringIdentifiable {
        HOOD(HelmetAttachmentsME.HOOD, HelmetAttachmentModelsME.REGULAR_MODELS),
        TALL_HOOD(HelmetAttachmentsME.TALL_HOOD, HelmetAttachmentModelsME.TALL_MODELS),

        BLACK_FUR_HOOD(HelmetAttachmentsME.BLACK_FUR_HOOD, HelmetAttachmentModelsME.FUR_MODELS),
        BROWN_FUR_HOOD(HelmetAttachmentsME.BROWN_FUR_HOOD, HelmetAttachmentModelsME.FUR_MODELS),
        GRAY_FUR_HOOD(HelmetAttachmentsME.GRAY_FUR_HOOD, HelmetAttachmentModelsME.FUR_MODELS),
        TAN_FUR_HOOD(HelmetAttachmentsME.TAN_FUR_HOOD, HelmetAttachmentModelsME.FUR_MODELS),
        WHITE_FUR_HOOD(HelmetAttachmentsME.WHITE_FUR_HOOD, HelmetAttachmentModelsME.FUR_MODELS),

        GONDORIAN_CITADEL_GUARD_HOOD(HelmetAttachmentsME.GONDORIAN_CITADEL_GUARD_HOOD, HelmetAttachmentModelsME.REGULAR_MODELS),

        LORIEN_MARCHWARDEN_HOOD(HelmetAttachmentsME.LORIEN_MARCHWARDEN_HOOD, HelmetAttachmentModelsME.REGULAR_MODELS),
        GALADHRIM_HOOD(HelmetAttachmentsME.GALADHRIM_HOOD, HelmetAttachmentModelsME.REGULAR_MODELS),
        NAZGUL_HOOD(HelmetAttachmentsME.NAZGUL_HOOD, HelmetAttachmentModelsME.NAZGUL_MODELS),
        SKULL(HelmetAttachmentsME.SKULL, HelmetAttachmentModelsME.SKULL_MODELS),
        ;

        private final HelmetAttachmentsME helmetAttachment;
        private final HelmetAttachmentModelsME models;

        ModHelmetAttachmentPairedModels(HelmetAttachmentsME helmetAttachment, HelmetAttachmentModelsME models){
            this.helmetAttachment = helmetAttachment;
            this.models = models;
        }

        public HelmetAttachmentModelsME getModel() {
            return models;
        }

        @Override
        public String asString() {
            return "";
        }
    }
}
