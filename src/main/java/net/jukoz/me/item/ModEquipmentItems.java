package net.jukoz.me.item;

import net.jukoz.me.MiddleEarth;
import net.jukoz.me.client.model.equipment.chest.*;
import net.jukoz.me.client.model.equipment.head.*;
import net.jukoz.me.datageneration.content.models.SimpleItemModel;
import net.jukoz.me.item.items.*;
import net.jukoz.me.item.utils.ModArmorMaterials;
import net.jukoz.me.item.utils.ModItemGroups;
import net.jukoz.me.utils.LoggerUtil;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

public class ModEquipmentItems {

    public static List<Item> armorPiecesList = new ArrayList<>();

    public static final Item GAMBESON_CAP = registerArmorPiece("gambeson_cap",
            new CustomHelmetItem(ModArmorMaterials.GAMBESON_CAP, ArmorItem.Type.HELMET, new Item.Settings(),
                    List.of(CustomHelmetItem.Customizations.DYEABLE), null));
    public static final Item GAMBESON_COWL = registerArmorPiece("gambeson_cowl",
            new CustomHelmetItem(ModArmorMaterials.GAMBESON_COWL, ArmorItem.Type.HELMET, new Item.Settings(),
                    List.of(CustomHelmetItem.Customizations.DYEABLE), null));
    public static final Item GAMBESON = registerArmorPiece("gambeson",
            new CustomChestplateItem(ModArmorMaterials.GAMBESON, ArmorItem.Type.CHESTPLATE, new Item.Settings(),
                    List.of(CustomChestplateItem.Customizations.DYEABLE), null));

    // Dyeable cloaks
    public static final Item CLOAK_HOOD = registerArmorPiece("cloak_hood",
            new CustomHelmetItem(ModArmorMaterials.CLOAK, ArmorItem.Type.HELMET, new Item.Settings(),
                    List.of(CustomHelmetItem.Customizations.HOOD), null));
    public static final Item CLOAK = registerArmorPiece("cloak",
            new CustomChestplateItem(ModArmorMaterials.CLOAK, ArmorItem.Type.CHESTPLATE, new Item.Settings(),
                    List.of(CustomChestplateItem.Customizations.CAPE), null));

    // Tunic cloak
    public static final Item TUNIC_CLOAK = registerArmorPiece("tunic_cloak",
            new CustomChestplateItem(ModArmorMaterials.TUNIC_CLOAK, ArmorItem.Type.CHESTPLATE, new Item.Settings(),
                    List.of(CustomChestplateItem.Customizations.CAPE), null));

    // Fur cloaks
    public static final Item FUR_CLOAK_HOOD = registerArmorPiece("fur_cloak_hood",
            new CustomHelmetItem(ModArmorMaterials.FUR_CLOAK, ArmorItem.Type.HELMET, new Item.Settings(),
                    List.of(CustomHelmetItem.Customizations.HOOD), null));
    public static final Item FUR_CLOAK = registerArmorPiece("fur_cloak",
            new CustomChestplateItem(ModArmorMaterials.FUR_CLOAK, ArmorItem.Type.CHESTPLATE, new Item.Settings(),
                    List.of(CustomChestplateItem.Customizations.CAPE), null));

    // Chainmail fur cloaks
    public static final Item CHAINMAIL_FUR_CLOAK = registerArmorPiece("chainmail_fur_cloak",
            new CustomChestplateItem(ModArmorMaterials.CHAINMAIL_FUR_CLOAK, ArmorItem.Type.CHESTPLATE, new Item.Settings(),
                    List.of(CustomChestplateItem.Customizations.CAPE), null));

    public static final Item RUSTY_KETTLE_HAT = registerArmorPiece("rusty_kettle_hat",
            new CustomHelmetItem(ModArmorMaterials.RUSTY_KETTLE_HAT, ArmorItem.Type.HELMET, new Item.Settings(),
                    null, new KettleHatArmorAddonModel(KettleHatArmorAddonModel.getTexturedModelData().createModel())));
    public static final Item KETTLE_HAT = registerArmorPiece("kettle_hat",
            new CustomHelmetItem(ModArmorMaterials.KETTLE_HAT, ArmorItem.Type.HELMET, new Item.Settings(),
                    null, new KettleHatArmorAddonModel(KettleHatArmorAddonModel.getTexturedModelData().createModel())));

    public static final Item HOBBIT_SHIRRIFF_HAT_GREEN = registerArmorPiece("hobbit_shirriff_hat_green",
            new CustomHelmetItem(ModArmorMaterials.HOBBIT_SHERRIF_HAT, ArmorItem.Type.HELMET, new Item.Settings(),
                    List.of(CustomHelmetItem.Customizations.FEATHER), new HobbitShirriffHatArmorAddonModel(HobbitShirriffHatArmorAddonModel.getTexturedModelData().createModel())));
    public static final Item HOBBIT_SHIRRIFF_HAT_BROWN = registerArmorPiece("hobbit_shirriff_hat_brown",
            new CustomHelmetItem(ModArmorMaterials.HOBBIT_SHERRIF_HAT, ArmorItem.Type.HELMET, new Item.Settings(),
                    List.of(CustomHelmetItem.Customizations.FEATHER), new HobbitShirriffHatArmorAddonModel(HobbitShirriffHatArmorAddonModel.getTexturedModelData().createModel())));

    //GONDOR
    public static final Item GONDORIAN_BOOTS = registerArmorPiece("gondorian_boots",
            new CustomBootsItem(ModArmorMaterials.GONDORIAN_BOOTS, ArmorItem.Type.BOOTS, new Item.Settings()));


    public static final Item GONDORIAN_CABASSET_HELMET = registerArmorPiece("gondorian_cabasset_helmet",
            new CustomHelmetItem(ModArmorMaterials.GONDORIAN_CABASSET, ArmorItem.Type.HELMET, new Item.Settings(),
                    null, new GondorianHelmetArmorAddonModel(GondorianHelmetArmorAddonModel.getTexturedModelData().createModel())));

    public static final Item GONDORIAN_LEATHER_CUIRASS = registerArmorPiece("gondorian_leather_cuirass",
            new CustomChestplateItem(ModArmorMaterials.GONDORIAN_LEATHER_CUIRASS, ArmorItem.Type.CHESTPLATE, new Item.Settings(),
                    List.of(CustomChestplateItem.Customizations.DYEABLE), null));
    public static final Item GONDORIAN_TABBARD = registerArmorPiece("gondorian_tabbard",
            new CustomChestplateItem(ModArmorMaterials.GONDORIAN_TABBARD, ArmorItem.Type.CHESTPLATE, new Item.Settings()));

    public static final Item GONDORIAN_CHAIN_COAT = registerArmorPiece("gondorian_chain_coat",
            new CustomLeggingsItem(ModArmorMaterials.GONDORIAN_CHAIN_COAT, ArmorItem.Type.LEGGINGS, new Item.Settings()));

    public static final Item GONDORIAN_SOLDIER_HELMET = registerArmorPiece("gondorian_soldier_helmet",
            new CustomHelmetItem(ModArmorMaterials.GONDORIAN_SOLDIER, ArmorItem.Type.HELMET, new Item.Settings(),
                    null, new GondorianHelmetArmorAddonModel(GondorianHelmetArmorAddonModel.getTexturedModelData().createModel())));

    public static final Item GONDORIAN_SOLDIER_CHESTPLATE = registerArmorPiece("gondorian_soldier_chestplate",
            new CustomChestplateItem(ModArmorMaterials.GONDORIAN_SOLDIER, ArmorItem.Type.CHESTPLATE, new Item.Settings()));

    public static final Item GONDORIAN_PLATE_HELMET = registerArmorPiece("gondorian_plate_helmet",
            new CustomHelmetItem(ModArmorMaterials.GONDORIAN_PLATE_ARMOR, ArmorItem.Type.HELMET, new Item.Settings(),
                    null, new GondorianHelmetArmorAddonModel(GondorianHelmetArmorAddonModel.getTexturedModelData().createModel())));
    public static final Item GONDORIAN_PLATE_CHESTPLATE = registerArmorPiece("gondorian_plate_chestplate",
            new CustomChestplateItem(ModArmorMaterials.GONDORIAN_PLATE_ARMOR, ArmorItem.Type.CHESTPLATE, new Item.Settings(),
                    null, new GondorCustomChestplateArmorAddonModel(GondorCustomChestplateArmorAddonModel.getTexturedModelData().createModel())));
    public static final Item GONDORIAN_PLATE_LEGGINGS = registerArmorPiece("gondorian_plate_leggings",
            new CustomLeggingsItem(ModArmorMaterials.GONDORIAN_PLATE_ARMOR, ArmorItem.Type.LEGGINGS, new Item.Settings()));
    public static final Item GONDORIAN_PLATE_BOOTS = registerArmorPiece("gondorian_plate_boots",
            new CustomBootsItem(ModArmorMaterials.GONDORIAN_PLATE_ARMOR, ArmorItem.Type.BOOTS, new Item.Settings()));

    public static final Item GONDORIAN_CAPTAIN_HELMET = registerArmorPiece("gondorian_captain_helmet",
            new CustomHelmetItem(ModArmorMaterials.GONDORIAN_CAPTAIN_ARMOR, ArmorItem.Type.HELMET, new Item.Settings(),
                    null, new GondorianHelmetArmorAddonModel(GondorianHelmetArmorAddonModel.getTexturedModelData().createModel())));

    public static final Item GONDORIAN_KINGS_GUARD_HELMET = registerArmorPiece("gondorian_kings_guard_helmet",
            new CustomHelmetItem(ModArmorMaterials.GONDORIAN_KINGS_GUARD_ARMOR, ArmorItem.Type.HELMET, new Item.Settings(),
                    null, new GondorianHelmetArmorAddonModel(GondorianHelmetArmorAddonModel.getTexturedModelData().createModel())));
    public static final Item GONDORIAN_KINGS_GUARD_CHESTKINGS_GUARD = registerArmorPiece("gondorian_kings_guard_chestplate",
            new CustomChestplateItem(ModArmorMaterials.GONDORIAN_KINGS_GUARD_ARMOR, ArmorItem.Type.CHESTPLATE, new Item.Settings(),
                    List.of(CustomChestplateItem.Customizations.CAPE), new GondorCustomChestplateArmorAddonModel(GondorCustomChestplateArmorAddonModel.getTexturedModelData().createModel())));
    public static final Item GONDORIAN_KINGS_GUARD_LEGGINGS = registerArmorPiece("gondorian_kings_guard_leggings",
            new CustomLeggingsItem(ModArmorMaterials.GONDORIAN_KINGS_GUARD_ARMOR, ArmorItem.Type.LEGGINGS, new Item.Settings()));
    public static final Item GONDORIAN_KINGS_GUARD_BOOTS = registerArmorPiece("gondorian_kings_guard_boots",
            new CustomBootsItem(ModArmorMaterials.GONDORIAN_KINGS_GUARD_ARMOR, ArmorItem.Type.BOOTS, new Item.Settings()));

    public static final Item GONDORIAN_CITADEL_GUARD_HELMET = registerArmorPiece("gondorian_citadel_guard_helmet",
            new CustomHelmetItem(ModArmorMaterials.GONDORIAN_CITADEL_GUARD_ARMOR, ArmorItem.Type.HELMET, new Item.Settings(),
                    null, new GondorianHelmetArmorAddonModel(GondorianHelmetArmorAddonModel.getTexturedModelData().createModel())));
    public static final Item GONDORIAN_CITADEL_GUARD_CHESTPLATE = registerArmorPiece("gondorian_citadel_guard_chestplate",
            new CustomChestplateItem(ModArmorMaterials.GONDORIAN_CITADEL_GUARD_ARMOR, ArmorItem.Type.CHESTPLATE, new Item.Settings(),
                    List.of(CustomChestplateItem.Customizations.CAPE), null));
    public static final Item GONDORIAN_CITADEL_GUARD_LEGGINGS = registerArmorPiece("gondorian_citadel_guard_leggings",
            new CustomLeggingsItem(ModArmorMaterials.GONDORIAN_CITADEL_GUARD_ARMOR, ArmorItem.Type.LEGGINGS, new Item.Settings()));
    public static final Item GONDORIAN_CITADEL_GUARD_BOOTS = registerArmorPiece("gondorian_citadel_guard_boots",
            new CustomBootsItem(ModArmorMaterials.GONDORIAN_CITADEL_GUARD_ARMOR, ArmorItem.Type.BOOTS, new Item.Settings()));
    
    public static final Item GONDORIAN_FOUNTAIN_GUARD_HELMET = registerArmorPiece("gondorian_fountain_guard_helmet",
            new CustomHelmetItem(ModArmorMaterials.GONDORIAN_FOUNTAIN_GUARD_ARMOR, ArmorItem.Type.HELMET, new Item.Settings(),
                    null, new GondorianHelmetArmorAddonModel(GondorianHelmetArmorAddonModel.getTexturedModelData().createModel())));
    public static final Item GONDORIAN_FOUNTAIN_GUARD_CHESTPLATE = registerArmorPiece("gondorian_fountain_guard_chestplate",
            new CustomChestplateItem(ModArmorMaterials.GONDORIAN_FOUNTAIN_GUARD_ARMOR, ArmorItem.Type.CHESTPLATE, new Item.Settings(),
                    List.of(CustomChestplateItem.Customizations.CAPE), new GondorCustomChestplateArmorAddonModel(GondorCustomChestplateArmorAddonModel.getTexturedModelData().createModel())));
    public static final Item GONDORIAN_FOUNTAIN_GUARD_LEGGINGS = registerArmorPiece("gondorian_fountain_guard_leggings",
            new CustomLeggingsItem(ModArmorMaterials.GONDORIAN_FOUNTAIN_GUARD_ARMOR, ArmorItem.Type.LEGGINGS, new Item.Settings()));
    public static final Item GONDORIAN_FOUNTAIN_GUARD_BOOTS = registerArmorPiece("gondorian_fountain_guard_boots",
            new CustomBootsItem(ModArmorMaterials.GONDORIAN_FOUNTAIN_GUARD_ARMOR, ArmorItem.Type.BOOTS, new Item.Settings()));

    public static final Item GONDORIAN_SHIELD = registerItem("gondorian_shield",
            new CustomShieldItem(new Item.Settings().maxCount(1).maxDamage(450), Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".gondor")));

    public static final Item GONDORIAN_HORSE_ARMOR = registerGeneratedItem("gondorian_horse_armor",
            new CustomHorseArmorItem(ArmorMaterials.IRON, AnimalArmorItem.Type.EQUESTRIAN, false, new Item.Settings().maxCount(1), Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".gondor")));

    //ROHAN
    public static final Item ROHIRRIC_SCALE_HELMET = registerArmorPiece("rohirric_scale_helmet",
            new CustomHelmetItem(ModArmorMaterials.ROHIRRIC_SCALE_ARMOR, ArmorItem.Type.HELMET, new Item.Settings()));
    public static final Item ROHIRRIC_SCALE_CHESTPLATE = registerArmorPiece("rohirric_scale_chestplate",
            new CustomChestplateItem(ModArmorMaterials.ROHIRRIC_SCALE_ARMOR, ArmorItem.Type.CHESTPLATE, new Item.Settings(),
                    List.of(CustomChestplateItem.Customizations.CAPE), null));
    public static final Item ROHIRRIC_SCALE_LEGGINGS = registerArmorPiece("rohirric_scale_leggings",
            new CustomLeggingsItem(ModArmorMaterials.ROHIRRIC_SCALE_ARMOR, ArmorItem.Type.LEGGINGS, new Item.Settings()));
    public static final Item ROHIRRIC_SCALE_BOOTS = registerArmorPiece("rohirric_scale_boots",
            new CustomBootsItem(ModArmorMaterials.ROHIRRIC_SCALE_ARMOR, ArmorItem.Type.BOOTS, new Item.Settings()));

    public static final Item ROHIRRIC_ROYAL_GUARD_HELMET = registerArmorPiece("rohirric_royal_guard_helmet",
            new CustomHelmetItem(ModArmorMaterials.ROHIRRIC_ROYAL_GUARD_ARMOR, ArmorItem.Type.HELMET, new Item.Settings(),
                    null, new RohirricHelmetArmorAddonModel(RohirricHelmetArmorAddonModel.getTexturedModelData().createModel())));
    public static final Item ROHIRRIC_ROYAL_GUARD_CHESTPLATE = registerArmorPiece("rohirric_royal_guard_chestplate",
            new CustomChestplateItem(ModArmorMaterials.ROHIRRIC_ROYAL_GUARD_ARMOR, ArmorItem.Type.CHESTPLATE, new Item.Settings(),
                    List.of(CustomChestplateItem.Customizations.CAPE), null));
    public static final Item ROHIRRIC_ROYAL_GUARD_LEGGINGS = registerArmorPiece("rohirric_royal_guard_leggings",
            new CustomLeggingsItem(ModArmorMaterials.ROHIRRIC_ROYAL_GUARD_ARMOR, ArmorItem.Type.LEGGINGS, new Item.Settings()));
    public static final Item ROHIRRIC_ROYAL_GUARD_BOOTS = registerArmorPiece("rohirric_royal_guard_boots",
            new CustomBootsItem(ModArmorMaterials.ROHIRRIC_ROYAL_GUARD_ARMOR, ArmorItem.Type.BOOTS, new Item.Settings()));

    public static final Item EORLING_MARSHAL_HELMET = registerArmorPiece("eorling_marshal_helmet",
            new CustomHelmetItem(ModArmorMaterials.EORLING_MARSHAL_ARMOR, ArmorItem.Type.HELMET, new Item.Settings(),
                    null, new RohirricHelmetArmorAddonModel(RohirricHelmetArmorAddonModel.getTexturedModelData().createModel())));
    public static final Item EORLING_MARSHAL_CHESTPLATE = registerArmorPiece("eorling_marshal_chestplate",
            new CustomChestplateItem(ModArmorMaterials.EORLING_MARSHAL_ARMOR, ArmorItem.Type.CHESTPLATE, new Item.Settings(),
                    List.of(CustomChestplateItem.Customizations.CAPE), null));
    public static final Item EORLING_MARSHAL_LEGGINGS = registerArmorPiece("eorling_marshal_leggings",
            new CustomLeggingsItem(ModArmorMaterials.EORLING_MARSHAL_ARMOR, ArmorItem.Type.LEGGINGS, new Item.Settings()));
    public static final Item EORLING_MARSHAL_BOOTS = registerArmorPiece("eorling_marshal_boots",
            new CustomBootsItem(ModArmorMaterials.EORLING_MARSHAL_ARMOR, ArmorItem.Type.BOOTS, new Item.Settings()));

    public static final Item HORSE_LORD_HELMET = registerArmorPiece("horse_lord_helmet",
            new CustomHelmetItem(ModArmorMaterials.HORSE_LORD_ARMOR, ArmorItem.Type.HELMET, new Item.Settings(),
                    null, new RohirricHelmetArmorAddonModel(RohirricHelmetArmorAddonModel.getTexturedModelData().createModel())));
    public static final Item HORSE_LORD_CHESTPLATE = registerArmorPiece("horse_lord_chestplate",
            new CustomChestplateItem(ModArmorMaterials.HORSE_LORD_ARMOR, ArmorItem.Type.CHESTPLATE, new Item.Settings(),
                    List.of(CustomChestplateItem.Customizations.CAPE), null));
    public static final Item HORSE_LORD_LEGGINGS = registerArmorPiece("horse_lord_leggings",
            new CustomLeggingsItem(ModArmorMaterials.HORSE_LORD_ARMOR, ArmorItem.Type.LEGGINGS, new Item.Settings()));
    public static final Item HORSE_LORD_BOOTS = registerArmorPiece("horse_lord_boots",
            new CustomBootsItem(ModArmorMaterials.HORSE_LORD_ARMOR, ArmorItem.Type.BOOTS, new Item.Settings()));

    public static final Item ROHIRRIC_SHIELD = registerItem("rohirric_shield",
            new CustomShieldItem(new Item.Settings().maxCount(1).maxDamage(450), Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".rohan")));

    public static final Item ROHIRRIC_HORSE_ARMOR = registerGeneratedItem("rohirric_horse_armor",
            new CustomHorseArmorItem(ArmorMaterials.IRON, AnimalArmorItem.Type.EQUESTRIAN, false, new Item.Settings().maxCount(1), Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".rohan")));


    //DWARVES
    public static final Item DWARVEN_PARTISAN_CHESTPLATE = registerArmorPiece("dwarven_partisan_chestplate",
            new CustomChestplateItem(ModArmorMaterials.DWARVEN_PARTISAN, ArmorItem.Type.CHESTPLATE, new Item.Settings(),
                    List.of(CustomChestplateItem.Customizations.CAPE), null));
    public static final Item DWARVEN_PARTISAN_LEGGINGS = registerArmorPiece("dwarven_partisan_leggings",
            new CustomLeggingsItem(ModArmorMaterials.DWARVEN_PARTISAN, ArmorItem.Type.LEGGINGS, new Item.Settings()));
    public static final Item DWARVEN_PARTISAN_BOOTS = registerArmorPiece("dwarven_partisan_boots",
            new CustomBootsItem(ModArmorMaterials.DWARVEN_PARTISAN, ArmorItem.Type.BOOTS, new Item.Settings()));

    //EREBOR
    public static final Item EREBOR_MAIL_HELMET = registerArmorPiece("erebor_mail_helmet",
            new CustomHelmetItem(ModArmorMaterials.EREBOR_MAIL_ARMOR, ArmorItem.Type.HELMET, new Item.Settings()));
    public static final Item EREBOR_MAIL_CHESTPLATE = registerArmorPiece("erebor_mail_chestplate",
            new CustomChestplateItem(ModArmorMaterials.EREBOR_MAIL_ARMOR, ArmorItem.Type.CHESTPLATE, new Item.Settings(),
                    List.of(CustomChestplateItem.Customizations.CAPE), null));
    public static final Item EREBOR_MAIL_LEGGINGS = registerArmorPiece("erebor_mail_leggings",
            new CustomLeggingsItem(ModArmorMaterials.EREBOR_MAIL_ARMOR, ArmorItem.Type.LEGGINGS, new Item.Settings()));
    public static final Item EREBOR_MAIL_BOOTS = registerArmorPiece("erebor_mail_boots",
            new CustomBootsItem(ModArmorMaterials.EREBOR_MAIL_ARMOR, ArmorItem.Type.BOOTS, new Item.Settings()));

    public static final Item EREBOR_SCALE_HELMET = registerArmorPiece("erebor_scale_helmet",
            new CustomHelmetItem(ModArmorMaterials.EREBOR_SCALE_ARMOR, ArmorItem.Type.HELMET, new Item.Settings()));
    public static final Item EREBOR_SCALE_CHESTPLATE = registerArmorPiece("erebor_scale_chestplate",
            new CustomChestplateItem(ModArmorMaterials.EREBOR_SCALE_ARMOR, ArmorItem.Type.CHESTPLATE, new Item.Settings(),
                    List.of(CustomChestplateItem.Customizations.POUCH), new EreborPouchChestplateArmorAddonModel(EreborPouchChestplateArmorAddonModel.getTexturedModelData().createModel())));
    public static final Item EREBOR_SCALE_LEGGINGS = registerArmorPiece("erebor_scale_leggings",
            new CustomLeggingsItem(ModArmorMaterials.EREBOR_SCALE_ARMOR, ArmorItem.Type.LEGGINGS, new Item.Settings()));
    public static final Item EREBOR_SCALE_BOOTS = registerArmorPiece("erebor_scale_boots",
            new CustomBootsItem(ModArmorMaterials.EREBOR_SCALE_ARMOR, ArmorItem.Type.BOOTS, new Item.Settings()));

    public static final Item EREBOR_PLATE_HELMET = registerArmorPiece("erebor_plate_helmet",
            new CustomHelmetItem(ModArmorMaterials.EREBOR_PLATE_ARMOR, ArmorItem.Type.HELMET, new Item.Settings()));
    public static final Item EREBOR_PLATE_CHESTPLATE = registerArmorPiece("erebor_plate_chestplate",
            new CustomChestplateItem(ModArmorMaterials.EREBOR_PLATE_ARMOR, ArmorItem.Type.CHESTPLATE, new Item.Settings(),
                    List.of(CustomChestplateItem.Customizations.POUCH), new EreborPouchChestplateArmorAddonModel(EreborPouchChestplateArmorAddonModel.getTexturedModelData().createModel())));
    public static final Item EREBOR_PLATE_LEGGINGS = registerArmorPiece("erebor_plate_leggings",
            new CustomLeggingsItem(ModArmorMaterials.EREBOR_PLATE_ARMOR, ArmorItem.Type.LEGGINGS, new Item.Settings()));
    public static final Item EREBOR_PLATE_BOOTS = registerArmorPiece("erebor_plate_boots",
            new CustomBootsItem(ModArmorMaterials.EREBOR_PLATE_ARMOR, ArmorItem.Type.BOOTS, new Item.Settings()));

    public static final Item EREBOR_COMMANDER_HELMET = registerArmorPiece("erebor_commander_helmet",
            new CustomHelmetItem(ModArmorMaterials.EREBOR_COMMANDER_ARMOR, ArmorItem.Type.HELMET, new Item.Settings(),
                    null, new EreborCommanderHelmetArmorAddonModel(EreborCommanderHelmetArmorAddonModel.getTexturedModelData().createModel())));
    public static final Item EREBOR_COMMANDER_CHESTPLATE = registerArmorPiece("erebor_commander_chestplate",
            new CustomChestplateItem(ModArmorMaterials.EREBOR_COMMANDER_ARMOR, ArmorItem.Type.CHESTPLATE, new Item.Settings(),
                    List.of(CustomChestplateItem.Customizations.CAPE), null));
    public static final Item EREBOR_COMMANDER_LEGGINGS = registerArmorPiece("erebor_commander_leggings",
            new CustomLeggingsItem(ModArmorMaterials.EREBOR_COMMANDER_ARMOR, ArmorItem.Type.LEGGINGS, new Item.Settings()));
    public static final Item EREBOR_COMMANDER_BOOTS = registerArmorPiece("erebor_commander_boots",
            new CustomBootsItem(ModArmorMaterials.EREBOR_COMMANDER_ARMOR, ArmorItem.Type.BOOTS, new Item.Settings()));

    public static final Item LONGBEARD_SHIELD = registerItem("longbeard_shield",
            new CustomShieldItem(new Item.Settings().maxCount(1).maxDamage(450), Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".longbeards")));


    //LOTHLORIEN
    public static final Item LORIEN_MAIL_HELMET = registerArmorPiece("lorien_mail_helmet",
            new CustomHelmetItem(ModArmorMaterials.LORIEN_MAIL_ARMOR, ArmorItem.Type.HELMET, new Item.Settings(),
                    List.of(CustomHelmetItem.Customizations.HOOD), new LorienHelmetArmorAddonModel(LorienHelmetArmorAddonModel.getTexturedModelData().createModel())));
    public static final Item LORIEN_MAIL_CHESTPLATE = registerArmorPiece("lorien_mail_chestplate",
            new CustomChestplateItem(ModArmorMaterials.LORIEN_MAIL_ARMOR, ArmorItem.Type.CHESTPLATE, new Item.Settings(),
                    List.of(CustomChestplateItem.Customizations.CAPE), null));
    public static final Item LORIEN_MAIL_LEGGINGS = registerArmorPiece("lorien_mail_leggings",
            new CustomLeggingsItem(ModArmorMaterials.LORIEN_MAIL_ARMOR, ArmorItem.Type.LEGGINGS, new Item.Settings()));
    public static final Item LORIEN_MAIL_BOOTS = registerArmorPiece("lorien_mail_boots",
            new CustomBootsItem(ModArmorMaterials.LORIEN_MAIL_ARMOR, ArmorItem.Type.BOOTS, new Item.Settings()));

    public static final Item LORIEN_SCALE_HELMET = registerArmorPiece("lorien_scale_helmet",
            new CustomHelmetItem(ModArmorMaterials.LORIEN_SCALE_ARMOR, ArmorItem.Type.HELMET, new Item.Settings(),
                    List.of(CustomHelmetItem.Customizations.HOOD), new LorienHelmetArmorAddonModel(LorienHelmetArmorAddonModel.getTexturedModelData().createModel())));
    public static final Item LORIEN_SCALE_CHESTPLATE = registerArmorPiece("lorien_scale_chestplate",
            new CustomChestplateItem(ModArmorMaterials.LORIEN_SCALE_ARMOR, ArmorItem.Type.CHESTPLATE, new Item.Settings(),
                    List.of(CustomChestplateItem.Customizations.CAPE), null));
    public static final Item LORIEN_SCALE_LEGGINGS = registerArmorPiece("lorien_scale_leggings",
            new CustomLeggingsItem(ModArmorMaterials.LORIEN_SCALE_ARMOR, ArmorItem.Type.LEGGINGS, new Item.Settings()));
    public static final Item LORIEN_SCALE_BOOTS = registerArmorPiece("lorien_scale_boots",
            new CustomBootsItem(ModArmorMaterials.LORIEN_SCALE_ARMOR, ArmorItem.Type.BOOTS, new Item.Settings()));

    public static final Item LORIEN_PLATE_HELMET = registerArmorPiece("lorien_plate_helmet",
            new CustomHelmetItem(ModArmorMaterials.LORIEN_PLATE_ARMOR, ArmorItem.Type.HELMET, new Item.Settings(),
                    List.of(CustomHelmetItem.Customizations.HOOD), new LorienHelmetArmorAddonModel(LorienHelmetArmorAddonModel.getTexturedModelData().createModel())));
    public static final Item LORIEN_PLATE_CHESTPLATE = registerArmorPiece("lorien_plate_chestplate",
            new CustomChestplateItem(ModArmorMaterials.LORIEN_PLATE_ARMOR, ArmorItem.Type.CHESTPLATE, new Item.Settings(),
                    List.of(CustomChestplateItem.Customizations.CAPE), null));
    public static final Item LORIEN_PLATE_LEGGINGS = registerArmorPiece("lorien_plate_leggings",
            new CustomLeggingsItem(ModArmorMaterials.LORIEN_PLATE_ARMOR, ArmorItem.Type.LEGGINGS, new Item.Settings()));
    public static final Item LORIEN_PLATE_BOOTS = registerArmorPiece("lorien_plate_boots",
            new CustomBootsItem(ModArmorMaterials.LORIEN_PLATE_ARMOR, ArmorItem.Type.BOOTS, new Item.Settings()));

    public static final Item LORIEN_COMMANDER_HELMET = registerArmorPiece("lorien_commander_helmet",
            new CustomHelmetItem(ModArmorMaterials.LORIEN_COMMANDER_ARMOR, ArmorItem.Type.HELMET, new Item.Settings(),
                    null, new LorienHelmetArmorAddonModel(LorienHelmetArmorAddonModel.getTexturedModelData().createModel())));
    public static final Item LORIEN_COMMANDER_CHESTPLATE = registerArmorPiece("lorien_commander_chestplate",
            new CustomChestplateItem(ModArmorMaterials.LORIEN_COMMANDER_ARMOR, ArmorItem.Type.CHESTPLATE, new Item.Settings(),
                    List.of(CustomChestplateItem.Customizations.CAPE), null));
    public static final Item LORIEN_COMMANDER_LEGGINGS = registerArmorPiece("lorien_commander_leggings",
            new CustomLeggingsItem(ModArmorMaterials.LORIEN_COMMANDER_ARMOR, ArmorItem.Type.LEGGINGS, new Item.Settings()));
    public static final Item LORIEN_COMMANDER_BOOTS = registerArmorPiece("lorien_commander_boots",
            new CustomBootsItem(ModArmorMaterials.LORIEN_COMMANDER_ARMOR, ArmorItem.Type.BOOTS, new Item.Settings()));

    public static final Item LORIEN_SHIELD = registerItem("lorien_shield",
            new CustomShieldItem(new Item.Settings().maxCount(1).maxDamage(450), Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".lothlorien")));

    public static final Item LORIEN_HORSE_ARMOR = registerGeneratedItem("lorien_horse_armor",
            new CustomHorseArmorItem(ArmorMaterials.IRON, AnimalArmorItem.Type.EQUESTRIAN, false, new Item.Settings().maxCount(1), Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".lothlorien")));

    //MORDOR
    public static final Item MORDOR_ORC_MAIL_HELMET = registerArmorPiece("mordor_orc_mail_helmet",
            new CustomHelmetItem(ModArmorMaterials.MORDOR_ORC_MAIL_ARMOR, ArmorItem.Type.HELMET, new Item.Settings()));
    public static final Item MORDOR_ORC_MAIL_CHESTPLATE = registerArmorPiece("mordor_orc_mail_chestplate",
            new CustomChestplateItem(ModArmorMaterials.MORDOR_ORC_MAIL_ARMOR, ArmorItem.Type.CHESTPLATE, new Item.Settings()));
    public static final Item MORDOR_ORC_MAIL_LEGGINGS = registerArmorPiece("mordor_orc_mail_leggings",
            new CustomLeggingsItem(ModArmorMaterials.MORDOR_ORC_MAIL_ARMOR, ArmorItem.Type.LEGGINGS, new Item.Settings()));
    public static final Item MORDOR_ORC_MAIL_BOOTS = registerArmorPiece("mordor_orc_mail_boots",
            new CustomBootsItem(ModArmorMaterials.MORDOR_ORC_MAIL_ARMOR, ArmorItem.Type.BOOTS, new Item.Settings()));

    public static final Item MORDOR_BLACK_URUK_SCALE_HELMET = registerArmorPiece("mordor_black_uruk_scale_helmet",
            new CustomHelmetItem(ModArmorMaterials.MORDOR_BLACK_URUK_SCALE_ARMOR, ArmorItem.Type.HELMET, new Item.Settings()));
    public static final Item MORDOR_BLACK_URUK_SCALE_CHESTPLATE = registerArmorPiece("mordor_black_uruk_scale_chestplate",
            new CustomChestplateItem(ModArmorMaterials.MORDOR_BLACK_URUK_SCALE_ARMOR, ArmorItem.Type.CHESTPLATE, new Item.Settings()));
    public static final Item MORDOR_BLACK_URUK_SCALE_LEGGINGS = registerArmorPiece("mordor_black_uruk_scale_leggings",
            new CustomLeggingsItem(ModArmorMaterials.MORDOR_BLACK_URUK_SCALE_ARMOR, ArmorItem.Type.LEGGINGS, new Item.Settings()));
    public static final Item MORDOR_BLACK_URUK_SCALE_BOOTS = registerArmorPiece("mordor_black_uruk_scale_boots",
            new CustomBootsItem(ModArmorMaterials.MORDOR_BLACK_URUK_SCALE_ARMOR, ArmorItem.Type.BOOTS, new Item.Settings()));

    public static final Item MORDOR_BLACK_URUK_PLATE_HELMET = registerArmorPiece("mordor_black_uruk_plate_helmet",
            new CustomHelmetItem(ModArmorMaterials.MORDOR_BLACK_URUK_PLATE_ARMOR, ArmorItem.Type.HELMET, new Item.Settings()));
    public static final Item MORDOR_BLACK_URUK_PLATE_CHESTPLATE = registerArmorPiece("mordor_black_uruk_plate_chestplate",
            new CustomChestplateItem(ModArmorMaterials.MORDOR_BLACK_URUK_PLATE_ARMOR, ArmorItem.Type.CHESTPLATE, new Item.Settings()));
    public static final Item MORDOR_BLACK_URUK_PLATE_LEGGINGS = registerArmorPiece("mordor_black_uruk_plate_leggings",
            new CustomLeggingsItem(ModArmorMaterials.MORDOR_BLACK_URUK_PLATE_ARMOR, ArmorItem.Type.LEGGINGS, new Item.Settings()));
    public static final Item MORDOR_BLACK_URUK_PLATE_BOOTS = registerArmorPiece("mordor_black_uruk_plate_boots",
            new CustomBootsItem(ModArmorMaterials.MORDOR_BLACK_URUK_PLATE_ARMOR, ArmorItem.Type.BOOTS, new Item.Settings()));

    public static final Item MORDOR_BLACK_URUK_COMMANDER_HELMET = registerArmorPiece("mordor_black_uruk_commander_helmet",
            new CustomHelmetItem(ModArmorMaterials.MORDOR_BLACK_URUK_PLATE_ARMOR, ArmorItem.Type.HELMET, new Item.Settings(),
                    List.of(CustomHelmetItem.Customizations.SKULL), new MordorBlackUrukCommanderHelmetArmorAddonModel(MordorBlackUrukCommanderHelmetArmorAddonModel.getTexturedModelData().createModel())));

    public static final Item MORDOR_SHIELD = registerItem("mordor_shield",
            new CustomShieldItem(new Item.Settings().maxCount(1).maxDamage(450), Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".mordor")));

    public static final Item NAZGUL_CLOAK_HOOD = registerArmorPiece("nazgul_cloak_hood",
            new CustomHelmetItem(ModArmorMaterials.NAZGUL_CLOAK, ArmorItem.Type.HELMET, new Item.Settings(),
                    List.of(CustomHelmetItem.Customizations.HOOD), null));
    public static final Item NAZGUL_CLOAK = registerArmorPiece("nazgul_cloak",
            new CustomChestplateItem(ModArmorMaterials.NAZGUL_CLOAK, ArmorItem.Type.CHESTPLATE, new Item.Settings(),
                    List.of(CustomChestplateItem.Customizations.CAPE), null));
    public static final Item NAZGUL_PANTS = registerArmorPiece("nazgul_pants",
            new CustomLeggingsItem(ModArmorMaterials.NAZGUL_CLOAK, ArmorItem.Type.LEGGINGS, new Item.Settings()));
    public static final Item NAZGUL_BOOTS = registerArmorPiece("nazgul_boots",
            new CustomBootsItem(ModArmorMaterials.NAZGUL_CLOAK, ArmorItem.Type.BOOTS, new Item.Settings()));

    //MISTY MOUNTAINS ORCS
    public static final Item MISTY_GOBLIN_MAIL_HELMET = registerArmorPiece("misty_goblin_mail_helmet",
            new CustomHelmetItem(ModArmorMaterials.MISTY_GOBLIN_MAIL_ARMOR, ArmorItem.Type.HELMET, new Item.Settings()));
    public static final Item MISTY_GOBLIN_MAIL_CHESTPLATE = registerArmorPiece("misty_goblin_mail_chestplate",
            new CustomChestplateItem(ModArmorMaterials.MISTY_GOBLIN_MAIL_ARMOR, ArmorItem.Type.CHESTPLATE, new Item.Settings()));
    public static final Item MISTY_GOBLIN_MAIL_LEGGINGS = registerArmorPiece("misty_goblin_mail_leggings",
            new CustomLeggingsItem(ModArmorMaterials.MISTY_GOBLIN_MAIL_ARMOR, ArmorItem.Type.LEGGINGS, new Item.Settings()));
    public static final Item MISTY_GOBLIN_MAIL_BOOTS = registerArmorPiece("misty_goblin_mail_boots",
            new CustomBootsItem(ModArmorMaterials.MISTY_GOBLIN_MAIL_ARMOR, ArmorItem.Type.BOOTS, new Item.Settings()));

    public static final Item MISTY_HOBGOBLIN_SCALE_HELMET = registerArmorPiece("misty_hobgoblin_scale_helmet",
            new CustomHelmetItem(ModArmorMaterials.MISTY_HOBGOBLIN_SCALE_ARMOR, ArmorItem.Type.HELMET, new Item.Settings(),
                    null, new MistyHobgoblinScaleHelmetArmorAddonModel(MistyHobgoblinScaleHelmetArmorAddonModel.getTexturedModelData().createModel())));
    public static final Item MISTY_HOBGOBLIN_SCALE_CHESTPLATE = registerArmorPiece("misty_hobgoblin_scale_chestplate",
            new CustomChestplateItem(ModArmorMaterials.MISTY_HOBGOBLIN_SCALE_ARMOR, ArmorItem.Type.CHESTPLATE, new Item.Settings(),
                    null, new MistyHobgoblinScaleChestplateArmorAddonModel(MistyHobgoblinScaleChestplateArmorAddonModel.getTexturedModelData().createModel())));
    public static final Item MISTY_HOBGOBLIN_SCALE_LEGGINGS = registerArmorPiece("misty_hobgoblin_scale_leggings",
            new CustomLeggingsItem(ModArmorMaterials.MISTY_HOBGOBLIN_SCALE_ARMOR, ArmorItem.Type.LEGGINGS, new Item.Settings()));
    public static final Item MISTY_HOBGOBLIN_SCALE_BOOTS = registerArmorPiece("misty_hobgoblin_scale_boots",
            new CustomBootsItem(ModArmorMaterials.MISTY_HOBGOBLIN_SCALE_ARMOR, ArmorItem.Type.BOOTS, new Item.Settings()));

    public static final Item MISTY_HOBGOBLIN_PLATE_HELMET = registerArmorPiece("misty_hobgoblin_plate_helmet",
            new CustomHelmetItem(ModArmorMaterials.MISTY_HOBGOBLIN_PLATE_ARMOR, ArmorItem.Type.HELMET, new Item.Settings(),
                    null, new MistyHobgoblinPlateHelmetArmorAddonModel(MistyHobgoblinPlateHelmetArmorAddonModel.getTexturedModelData().createModel())));
    public static final Item MISTY_HOBGOBLIN_PLATE_CHESTPLATE = registerArmorPiece("misty_hobgoblin_plate_chestplate",
            new CustomChestplateItem(ModArmorMaterials.MISTY_HOBGOBLIN_PLATE_ARMOR, ArmorItem.Type.CHESTPLATE, new Item.Settings(),
                    null, new MistyHobgoblinPlateChestplateArmorAddonModel(MistyHobgoblinPlateChestplateArmorAddonModel.getTexturedModelData().createModel())));
    public static final Item MISTY_HOBGOBLIN_PLATE_LEGGINGS = registerArmorPiece("misty_hobgoblin_plate_leggings",
            new CustomLeggingsItem(ModArmorMaterials.MISTY_HOBGOBLIN_PLATE_ARMOR, ArmorItem.Type.LEGGINGS, new Item.Settings()));
    public static final Item MISTY_HOBGOBLIN_PLATE_BOOTS = registerArmorPiece("misty_hobgoblin_plate_boots",
            new CustomBootsItem(ModArmorMaterials.MISTY_HOBGOBLIN_PLATE_ARMOR, ArmorItem.Type.BOOTS, new Item.Settings()));

    public static final Item MISTY_HOBGOBLIN_COMMANDER_HELMET = registerArmorPiece("misty_hobgoblin_commander_helmet",
            new CustomChestplateItem(ModArmorMaterials.MISTY_HOBGOBLIN_PLATE_ARMOR, ArmorItem.Type.HELMET, new Item.Settings(),
                    null, new MistyHobgoblinCommanderChestplateArmorAddonModel(MistyHobgoblinCommanderChestplateArmorAddonModel.getTexturedModelData().createModel())));
    public static final Item MISTY_HOBGOBLIN_COMMANDER_CHESTPLATE = registerArmorPiece("misty_hobgoblin_commander_chestplate",
            new CustomChestplateItem(ModArmorMaterials.MISTY_HOBGOBLIN_PLATE_ARMOR, ArmorItem.Type.CHESTPLATE, new Item.Settings(),
                    List.of(CustomChestplateItem.Customizations.IMPALED_SKULLS), new MistyHobgoblinCommanderChestplateArmorAddonModel(MistyHobgoblinCommanderChestplateArmorAddonModel.getTexturedModelData().createModel())));

    public static final Item MISTY_MOUNTAINS_SHIELD = registerItem("misty_mountains_shield",
            new CustomShieldItem(new Item.Settings().maxCount(1).maxDamage(450), Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".misty_orcs")));

    /*public static final Item STEEL_TROLL_ARMOR = registerGeneratedItem("steel_troll_armor",
            new TrollArmorItem(10, "steel", new Item.Settings().maxCount(1)));*/

    private static Item registerItem(String name, Item item) {
        ModItemGroups.EQUIPMENT_CONTENTS.add(item.getDefaultStack());
        return Registry.register(Registries.ITEM, new Identifier(MiddleEarth.MOD_ID, name), item);
    }

    private static Item registerGeneratedItem(String name, Item item) {
        ModItemGroups.EQUIPMENT_CONTENTS.add(item.getDefaultStack());
        SimpleItemModel.items.add(item);
        return Registry.register(Registries.ITEM, new Identifier(MiddleEarth.MOD_ID, name), item);
    }

    private static Item registerArmorPiece(String name, Item item) {
        ModItemGroups.EQUIPMENT_CONTENTS.add(item.getDefaultStack());
        SimpleItemModel.items.add(item);
        armorPiecesList.add(item);
        return Registry.register(Registries.ITEM, new Identifier(MiddleEarth.MOD_ID, name), item);
    }

    public static void registerModItems() {
        LoggerUtil.getInstance().logDebugMsg("Registering Mod Equipment Items for " + MiddleEarth.MOD_ID);
    }
}
