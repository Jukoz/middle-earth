package net.jukoz.me.item;

import net.jukoz.me.MiddleEarth;
import net.jukoz.me.client.model.equipment.chest.*;
import net.jukoz.me.client.model.equipment.head.*;
import net.jukoz.me.datageneration.content.models.SimpleDyeableItemModel;
import net.jukoz.me.datageneration.content.models.SimpleItemModel;
import net.jukoz.me.item.dataComponents.CapeDataComponent;
import net.jukoz.me.item.dataComponents.CustomDyeableDataComponent;
import net.jukoz.me.item.dataComponents.HoodDataComponent;
import net.jukoz.me.item.items.*;
import net.jukoz.me.item.utils.ModArmorMaterials;
import net.jukoz.me.item.utils.ModCapes;
import net.jukoz.me.item.utils.ModHoods;
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

    public static List<Item> armorPiecesListHelmets = new ArrayList<>();
    public static List<Item> armorPiecesListChestplates = new ArrayList<>();
    public static List<Item> armorPiecesListLeggings = new ArrayList<>();
    public static List<Item> armorPiecesListBoots = new ArrayList<>();

    public static List<Item> armorPiecesListRustyHelmets = new ArrayList<>();
    public static List<Item> armorPiecesListRustyChestplates = new ArrayList<>();
    public static List<Item> armorPiecesListRustyLeggings = new ArrayList<>();
    public static List<Item> armorPiecesListRustyBoots = new ArrayList<>();

    //region GENERIC
    // Dyeable cloaks
    public static final Item CLOAK_HOOD = registerGeneratedItem("cloak_hood",
            new HoodHelmetItem(new Item.Settings()
                    .component(ModDataComponentTypes.HOOD_DATA, HoodDataComponent.newHood(ModHoods.BASE_HOOD))));
    public static final Item CLOAK = registerGeneratedItem("cloak",
            new CapeChestplateItem(new Item.Settings()
                    .component(ModDataComponentTypes.CAPE_DATA, CapeDataComponent.newCape(ModCapes.BASE_CAPE))));

    // Fur cloaks
    public static final Item FUR_CLOAK_HOOD = registerGeneratedItem("fur_cloak_hood",
            new HoodHelmetItem(new Item.Settings()
                    .component(ModDataComponentTypes.HOOD_DATA, HoodDataComponent.newHood(ModHoods.FUR_HOOD))));
    public static final Item FUR_CLOAK = registerGeneratedItem("fur_cloak",
            new CapeChestplateItem(new Item.Settings()
                    .component(ModDataComponentTypes.CAPE_DATA, CapeDataComponent.newCape(ModCapes.FUR_CLOAK))));

    public static final Item STRAW_HAT = registerArmorPiece("straw_hat",
            new CustomHelmetItem(ModArmorMaterials.STRAW_HAT, ArmorItem.Type.HELMET, new Item.Settings(),
                    new HatArmorAddonModel(HatArmorAddonModel.getTexturedModelData().createModel())));

    public static final Item WOVEN_HAT = registerArmorPiece("woven_hat",
            new CustomHelmetItem(ModArmorMaterials.WOVEN_HAT, ArmorItem.Type.HELMET, new Item.Settings(),
                    new HatArmorAddonModel(HatArmorAddonModel.getTexturedModelData().createModel())));

    public static final Item BYCOCKET = registerDyeableArmorPiece("bycocket",
            new CustomHelmetItem(ModArmorMaterials.WOVEN_HAT, ArmorItem.Type.HELMET, new Item.Settings()
                    .component(ModDataComponentTypes.DYE_DATA, CustomDyeableDataComponent.withOverlay(false, -6265536)),
                    null));

    public static final Item ARMING_COAT = registerDyeableArmorPiece("arming_coat",
            new CustomChestplateItem(ModArmorMaterials.GENERIC_T1, ArmorItem.Type.CHESTPLATE, new Item.Settings()
                    .component(ModDataComponentTypes.DYE_DATA, CustomDyeableDataComponent.withOverlay(true, 15256475)),
                    null));

    public static final Item ARMING_SKIRT = registerDyeableArmorPiece("arming_skirt",
            new CustomLeggingsItem(ModArmorMaterials.GENERIC_T1, ArmorItem.Type.LEGGINGS, new Item.Settings()
                    .component(ModDataComponentTypes.DYE_DATA, CustomDyeableDataComponent.withOverlay(true, 15256475))));

    public static final Item SHOES = registerArmorPiece("shoes",
            new CustomBootsItem(ModArmorMaterials.GENERIC_T1, ArmorItem.Type.BOOTS, new Item.Settings()));

    public static final Item WORK_SHOES = registerArmorPiece("work_shoes",
            new CustomBootsItem(ModArmorMaterials.GENERIC_T1, ArmorItem.Type.BOOTS, new Item.Settings()));

    public static final Item LEATHER_CAP = registerDyeableArmorPiece("leather_cap",
            new CustomHelmetItem(ModArmorMaterials.GAMBESON_CAP, ArmorItem.Type.HELMET, new Item.Settings()
                    .component(ModDataComponentTypes.DYE_DATA, CustomDyeableDataComponent.withOverlay(false, 15256475)),
                    null));

    public static final Item GAMBESON_CAP = registerDyeableArmorPiece("gambeson_cap",
            new CustomHelmetItem(ModArmorMaterials.GAMBESON_CAP, ArmorItem.Type.HELMET, new Item.Settings()
                    .component(ModDataComponentTypes.DYE_DATA, CustomDyeableDataComponent.withOverlay(false, 15256475)),
                    null));
    public static final Item GAMBESON_COWL = registerDyeableArmorPiece("gambeson_cowl",
            new CustomHelmetItem(ModArmorMaterials.GAMBESON_COWL, ArmorItem.Type.HELMET, new Item.Settings()
                    .component(ModDataComponentTypes.DYE_DATA, CustomDyeableDataComponent.withOverlay(true, 15256475)),
                    null));

    public static final Item GAMBESON = registerDyeableArmorPiece("gambeson",
            new CustomChestplateItem(ModArmorMaterials.GAMBESON, ArmorItem.Type.CHESTPLATE, new Item.Settings()
                    .component(ModDataComponentTypes.DYE_DATA, CustomDyeableDataComponent.withOverlay(true, 15256475)),
                    null));

    public static final Item LEATHER_VEST = registerDyeableArmorPiece("leather_vest",
            new CustomChestplateItem(ModArmorMaterials.GAMBESON, ArmorItem.Type.CHESTPLATE, new Item.Settings()
                    .component(ModDataComponentTypes.DYE_DATA, CustomDyeableDataComponent.withOverlay(false, -6265536)),
                    null));

    public static final Item LEATHER_SCALE_VEST = registerDyeableArmorPiece("leather_scale_vest",
            new CustomChestplateItem(ModArmorMaterials.GAMBESON, ArmorItem.Type.CHESTPLATE, new Item.Settings()
                    .component(ModDataComponentTypes.DYE_DATA, CustomDyeableDataComponent.withOverlay(true, -6265536)),
                    null));

    public static final Item STURDY_BOOTS = registerArmorPiece("sturdy_boots",
            new CustomBootsItem(ModArmorMaterials.STURDY_BOOTS, ArmorItem.Type.BOOTS, new Item.Settings()));
    public static final Item TRAVELLING_BOOTS = registerArmorPiece("travelling_boots",
            new CustomBootsItem(ModArmorMaterials.STURDY_BOOTS, ArmorItem.Type.BOOTS, new Item.Settings()));
    public static final Item HIGH_CUT_BOOTS = registerArmorPiece("high_cut_boots",
            new CustomBootsItem(ModArmorMaterials.STURDY_BOOTS, ArmorItem.Type.BOOTS, new Item.Settings()));

    public static final Item CHAIN_COIF = registerArmorPiece("chain_coif",
            new CustomHelmetItem(ModArmorMaterials.CHAIN_ARMOR, ArmorItem.Type.HELMET, new Item.Settings(),
                    null));

    public static final Item KETTLE_HAT = registerArmorPiece("kettle_hat",
            new CustomHelmetItem(ModArmorMaterials.KETTLE_HAT, ArmorItem.Type.HELMET, new Item.Settings(),
                    new KettleHatArmorAddonModel(KettleHatArmorAddonModel.getTexturedModelData().createModel())));

    public static final Item CHAIN_HAUBERK = registerArmorPiece("chain_hauberk",
            new CustomChestplateItem(ModArmorMaterials.CHAIN_ARMOR, ArmorItem.Type.CHESTPLATE, new Item.Settings(),
                    null));
    
    public static final Item CHAIN_SKIRT = registerArmorPiece("chain_skirt",
            new CustomLeggingsItem(ModArmorMaterials.CHAIN_ARMOR, ArmorItem.Type.LEGGINGS, new Item.Settings()));

    public static final Item OPEN_FACE_HELMET = registerArmorPiece("open_face_helmet",
            new CustomHelmetItem(ModArmorMaterials.OPEN_FACE, ArmorItem.Type.HELMET, new Item.Settings(),
                    null));
    public static final Item SALLET = registerArmorPiece("sallet",
            new CustomHelmetItem(ModArmorMaterials.OPEN_FACE, ArmorItem.Type.HELMET, new Item.Settings(),
                    new SalletHelmetAddonModel(SalletHelmetAddonModel.getTexturedModelData().createModel())));
    //endregion

    //region HOBBITS
    public static final Item SHIRRIFF_HAT = registerDyeableArmorPiece("shirriff_hat",
            new CustomHelmetItem(ModArmorMaterials.HOBBIT_SHIRRIFF_HAT, ArmorItem.Type.HELMET, new Item.Settings()
                    .component(ModDataComponentTypes.DYE_DATA, CustomDyeableDataComponent.withOverlay(true, -6265536)),
                    new HatArmorAddonModel(HatArmorAddonModel.getTexturedModelData().createModel())));

    //endregion

    //region MEN
    //region GONDOR
    public static final Item GONDORIAN_BOOTS = registerArmorPiece("gondorian_boots",
            new CustomBootsItem(ModArmorMaterials.GONDORIAN_BOOTS, ArmorItem.Type.BOOTS, new Item.Settings()));

    public static final Item GONDORIAN_LEATHER_CUIRASS = registerDyeableArmorPiece("gondorian_leather_cuirass",
            new CustomChestplateItem(ModArmorMaterials.GONDORIAN_LEATHER_CUIRASS, ArmorItem.Type.CHESTPLATE, new Item.Settings()
                    .component(ModDataComponentTypes.DYE_DATA, CustomDyeableDataComponent.withOverlay(true, -6265536)),
                    null));
    public static final Item GONDORIAN_LEATHER_CHESTPLATE = registerDyeableArmorPiece("gondorian_leather_chestplate",
            new CustomChestplateItem(ModArmorMaterials.GONDORIAN_LEATHER_CUIRASS, ArmorItem.Type.CHESTPLATE, new Item.Settings()
                    .component(ModDataComponentTypes.DYE_DATA, CustomDyeableDataComponent.withOverlay(true, -6265536)),
                    null));

    public static final Item GONDORIAN_CABASSET_HELMET = registerArmorPiece("gondorian_cabasset_helmet",
            new CustomHelmetItem(ModArmorMaterials.GONDORIAN_CABASSET, ArmorItem.Type.HELMET, new Item.Settings(),
                    new GondorianHelmetModel(GondorianHelmetModel.getTexturedModelData().createModel())));

    public static final Item GONDORIAN_TABBARD = registerArmorPiece("gondorian_tabbard",
            new CustomChestplateItem(ModArmorMaterials.GONDORIAN_TABBARD, ArmorItem.Type.CHESTPLATE, new Item.Settings()));

    public static final Item GONDORIAN_CHAIN_COAT = registerArmorPiece("gondorian_chain_coat",
            new CustomLeggingsItem(ModArmorMaterials.GONDORIAN_CHAIN_COAT, ArmorItem.Type.LEGGINGS, new Item.Settings()));

    public static final Item GONDORIAN_SOLDIER_HELMET = registerArmorPiece("gondorian_soldier_helmet",
            new CustomHelmetItem(ModArmorMaterials.GONDORIAN_SOLDIER, ArmorItem.Type.HELMET, new Item.Settings(),
                    new GondorianHelmetModel(GondorianHelmetModel.getTexturedModelData().createModel())));

    public static final Item GONDORIAN_SOLDIER_CHESTPLATE = registerArmorPiece("gondorian_soldier_chestplate",
            new CustomChestplateItem(ModArmorMaterials.GONDORIAN_SOLDIER, ArmorItem.Type.CHESTPLATE, new Item.Settings()));

    public static final Item GONDORIAN_PLATE_HELMET = registerArmorPiece("gondorian_plate_helmet",
            new CustomHelmetItem(ModArmorMaterials.GONDORIAN_PLATE_ARMOR, ArmorItem.Type.HELMET, new Item.Settings(),
                    new GondorianHelmetModel(GondorianHelmetModel.getTexturedModelData().createModel())));
    public static final Item GONDORIAN_PLATE_CHESTPLATE = registerArmorPiece("gondorian_plate_chestplate",
            new CustomChestplateItem(ModArmorMaterials.GONDORIAN_PLATE_ARMOR, ArmorItem.Type.CHESTPLATE, new Item.Settings(),
                    new HaltChestplateModel(HaltChestplateModel.getTexturedModelData().createModel())));
    public static final Item GONDORIAN_PLATE_LEGGINGS = registerArmorPiece("gondorian_plate_leggings",
            new CustomLeggingsItem(ModArmorMaterials.GONDORIAN_PLATE_ARMOR, ArmorItem.Type.LEGGINGS, new Item.Settings()));
    public static final Item GONDORIAN_PLATE_BOOTS = registerArmorPiece("gondorian_plate_boots",
            new CustomBootsItem(ModArmorMaterials.GONDORIAN_PLATE_ARMOR, ArmorItem.Type.BOOTS, new Item.Settings()));

    public static final Item GONDORIAN_CAPTAIN_HELMET = registerArmorPiece("gondorian_captain_helmet",
            new CustomHelmetItem(ModArmorMaterials.GONDORIAN_CAPTAIN_ARMOR, ArmorItem.Type.HELMET, new Item.Settings(),
                    new GondorianHelmetModel(GondorianHelmetModel.getTexturedModelData().createModel())));

    public static final Item GONDORIAN_KINGS_GUARD_HELMET = registerArmorPiece("gondorian_kings_guard_helmet",
            new CustomHelmetItem(ModArmorMaterials.GONDORIAN_KINGS_GUARD_ARMOR, ArmorItem.Type.HELMET, new Item.Settings(),
                    new GondorianHelmetModel(GondorianHelmetModel.getTexturedModelData().createModel())));
    public static final Item GONDORIAN_KINGS_GUARD_CHESTKPLATE = registerArmorPiece("gondorian_kings_guard_chestplate",
            new CustomChestplateItem(ModArmorMaterials.GONDORIAN_KINGS_GUARD_ARMOR, ArmorItem.Type.CHESTPLATE, new Item.Settings()
                        .component(ModDataComponentTypes.CAPE_DATA, CapeDataComponent.newCape(ModCapes.GONDORIAN_KINGS_GUARD_CAPE)),
                    new HaltChestplateModel(HaltChestplateModel.getTexturedModelData().createModel())));
    public static final Item GONDORIAN_KINGS_GUARD_LEGGINGS = registerArmorPiece("gondorian_kings_guard_leggings",
            new CustomLeggingsItem(ModArmorMaterials.GONDORIAN_KINGS_GUARD_ARMOR, ArmorItem.Type.LEGGINGS, new Item.Settings()));
    public static final Item GONDORIAN_KINGS_GUARD_BOOTS = registerArmorPiece("gondorian_kings_guard_boots",
            new CustomBootsItem(ModArmorMaterials.GONDORIAN_KINGS_GUARD_ARMOR, ArmorItem.Type.BOOTS, new Item.Settings()));

    public static final Item GONDORIAN_CITADEL_GUARD_HELMET = registerArmorPiece("gondorian_citadel_guard_helmet",
            new CustomHelmetItem(ModArmorMaterials.GONDORIAN_CITADEL_GUARD_ARMOR, ArmorItem.Type.HELMET, new Item.Settings(),
                    new GondorianHelmetModel(GondorianHelmetModel.getTexturedModelData().createModel())));
    public static final Item GONDORIAN_CITADEL_GUARD_CHESTPLATE = registerArmorPiece("gondorian_citadel_guard_chestplate",
            new CustomChestplateItem(ModArmorMaterials.GONDORIAN_CITADEL_GUARD_ARMOR, ArmorItem.Type.CHESTPLATE, new Item.Settings()
                    .component(ModDataComponentTypes.CAPE_DATA, CapeDataComponent.newCape(ModCapes.GONDORIAN_CITADEL_GUARD_CAPE)),
                    null));
    public static final Item GONDORIAN_CITADEL_GUARD_LEGGINGS = registerArmorPiece("gondorian_citadel_guard_leggings",
            new CustomLeggingsItem(ModArmorMaterials.GONDORIAN_CITADEL_GUARD_ARMOR, ArmorItem.Type.LEGGINGS, new Item.Settings()));
    public static final Item GONDORIAN_CITADEL_GUARD_BOOTS = registerArmorPiece("gondorian_citadel_guard_boots",
            new CustomBootsItem(ModArmorMaterials.GONDORIAN_CITADEL_GUARD_ARMOR, ArmorItem.Type.BOOTS, new Item.Settings()));
    
    public static final Item GONDORIAN_FOUNTAIN_GUARD_HELMET = registerArmorPiece("gondorian_fountain_guard_helmet",
            new CustomHelmetItem(ModArmorMaterials.GONDORIAN_FOUNTAIN_GUARD_ARMOR, ArmorItem.Type.HELMET, new Item.Settings(),
                    new GondorianHelmetModel(GondorianHelmetModel.getTexturedModelData().createModel())));
    public static final Item GONDORIAN_FOUNTAIN_GUARD_CHESTPLATE = registerArmorPiece("gondorian_fountain_guard_chestplate",
            new CustomChestplateItem(ModArmorMaterials.GONDORIAN_FOUNTAIN_GUARD_ARMOR, ArmorItem.Type.CHESTPLATE, new Item.Settings()
                    .component(ModDataComponentTypes.CAPE_DATA, CapeDataComponent.newCape(ModCapes.GONDORIAN_FOUNTAIN_GUARD_CAPE)),
                    new HaltChestplateModel(HaltChestplateModel.getTexturedModelData().createModel())));
    public static final Item GONDORIAN_FOUNTAIN_GUARD_LEGGINGS = registerArmorPiece("gondorian_fountain_guard_leggings",
            new CustomLeggingsItem(ModArmorMaterials.GONDORIAN_FOUNTAIN_GUARD_ARMOR, ArmorItem.Type.LEGGINGS, new Item.Settings()));
    public static final Item GONDORIAN_FOUNTAIN_GUARD_BOOTS = registerArmorPiece("gondorian_fountain_guard_boots",
            new CustomBootsItem(ModArmorMaterials.GONDORIAN_FOUNTAIN_GUARD_ARMOR, ArmorItem.Type.BOOTS, new Item.Settings()));

    public static final Item GONDORIAN_SHIELD = registerItem("gondorian_shield",
            new CustomShieldItem(new Item.Settings().maxCount(1).maxDamage(450), Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".gondor")));

    public static final Item GONDORIAN_HORSE_ARMOR = registerGeneratedItem("gondorian_horse_armor",
            new CustomHorseArmorItem(ArmorMaterials.IRON, AnimalArmorItem.Type.EQUESTRIAN, false, new Item.Settings().maxCount(1), Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".gondor")));

    //endregion

    //region ROHAN
    public static final Item ROHIRRIC_LEATHER_HELMET = registerArmorPiece("rohirric_leather_helmet",
            new CustomHelmetItem(ModArmorMaterials.ROHIRRIC_LEATHER, ArmorItem.Type.HELMET, new Item.Settings()));

    public static final Item ROHIRRIC_COAT = registerDyeableArmorPiece("rohirric_coat",
            new CustomChestplateItem(ModArmorMaterials.ROHIRRIC_LEATHER, ArmorItem.Type.CHESTPLATE, new Item.Settings()
                    .component(ModDataComponentTypes.DYE_DATA, CustomDyeableDataComponent.withOverlay(true, 15256475))
                    .component(ModDataComponentTypes.CAPE_DATA, CapeDataComponent.newCape(ModCapes.ROHIRRIC_CAPE)),
                    null));
    public static final Item ROHIRRIC_REINFORCED_COAT = registerDyeableArmorPiece("rohirric_reinforced_coat",
            new CustomChestplateItem(ModArmorMaterials.ROHIRRIC_LEATHER, ArmorItem.Type.CHESTPLATE, new Item.Settings()
                    .component(ModDataComponentTypes.DYE_DATA, CustomDyeableDataComponent.withOverlay(true, 15256475))
                    .component(ModDataComponentTypes.CAPE_DATA, CapeDataComponent.newCape(ModCapes.ROHIRRIC_CAPE)),
                    null));

    public static final Item ROHIRRIC_LEATHER_VEST = registerDyeableArmorPiece("rohirric_leather_vest",
            new CustomChestplateItem(ModArmorMaterials.ROHIRRIC_LEATHER, ArmorItem.Type.CHESTPLATE, new Item.Settings()
                    .component(ModDataComponentTypes.DYE_DATA, CustomDyeableDataComponent.withOverlay(true, 15256475))
                    .component(ModDataComponentTypes.CAPE_DATA, CapeDataComponent.newCape(ModCapes.ROHIRRIC_CAPE)),
                    null));
    public static final Item ROHIRRIC_LEATHER_SCALE_VEST = registerDyeableArmorPiece("rohirric_leather_scale_vest",
            new CustomChestplateItem(ModArmorMaterials.ROHIRRIC_LEATHER, ArmorItem.Type.CHESTPLATE, new Item.Settings()
                    .component(ModDataComponentTypes.DYE_DATA, CustomDyeableDataComponent.withOverlay(true, 15256475))
                    .component(ModDataComponentTypes.CAPE_DATA, CapeDataComponent.newCape(ModCapes.ROHIRRIC_CAPE)),
                    null));
    public static final Item ROHIRRIC_GAMBESON = registerDyeableArmorPiece("rohirric_gambeson",
            new CustomChestplateItem(ModArmorMaterials.ROHIRRIC_LEATHER, ArmorItem.Type.CHESTPLATE, new Item.Settings()
                    .component(ModDataComponentTypes.DYE_DATA, CustomDyeableDataComponent.withOverlay(true, 15256475))
                    .component(ModDataComponentTypes.CAPE_DATA, CapeDataComponent.newCape(ModCapes.ROHIRRIC_CAPE)),
                    null));

    public static final Item ROHIRRIC_MILITIA_HELMET = registerArmorPiece("rohirric_militia_helmet",
            new CustomHelmetItem(ModArmorMaterials.ROHIRRIC_MILITIA, ArmorItem.Type.HELMET, new Item.Settings()));

    public static final Item ROHIRRIC_MAIL_SHIRT = registerDyeableArmorPiece("rohirric_mail_shirt",
            new CustomChestplateItem(ModArmorMaterials.ROHIRRIC_MAIL, ArmorItem.Type.CHESTPLATE, new Item.Settings()
                    .component(ModDataComponentTypes.DYE_DATA, CustomDyeableDataComponent.withOverlay(true, 15256475))
                    .component(ModDataComponentTypes.CAPE_DATA, CapeDataComponent.newCape(ModCapes.ROHIRRIC_CAPE)),
                    null));
    public static final Item ROHIRRIC_MAIL_SHIRT_OPEN = registerDyeableArmorPiece("rohirric_mail_shirt_open",
            new CustomChestplateItem(ModArmorMaterials.ROHIRRIC_MAIL, ArmorItem.Type.CHESTPLATE, new Item.Settings()
                    .component(ModDataComponentTypes.DYE_DATA, CustomDyeableDataComponent.withOverlay(true, 15256475))
                    .component(ModDataComponentTypes.CAPE_DATA, CapeDataComponent.newCape(ModCapes.ROHIRRIC_CAPE)),
                    null));
    public static final Item ROHIRRIC_MAIL_HAUBERK = registerArmorPiece("rohirric_mail_hauberk",
            new CustomChestplateItem(ModArmorMaterials.ROHIRRIC_MAIL, ArmorItem.Type.CHESTPLATE, new Item.Settings()
                    .component(ModDataComponentTypes.CAPE_DATA, CapeDataComponent.newCape(ModCapes.ROHIRRIC_CAPE)),
                    null));

    public static final Item ROHIRRIC_SOLDIER_HELMET = registerArmorPiece("rohirric_soldier_helmet",
            new CustomHelmetItem(ModArmorMaterials.ROHIRRIC_SOLDIER, ArmorItem.Type.HELMET, new Item.Settings()));

    public static final Item ROHIRRIC_ROYAL_GUARD_HELMET = registerArmorPiece("rohirric_royal_guard_helmet",
            new CustomHelmetItem(ModArmorMaterials.ROHIRRIC_ROYAL_GUARD_ARMOR, ArmorItem.Type.HELMET, new Item.Settings(),
                    new RohirricHelmetArmorAddonModel(RohirricHelmetArmorAddonModel.getTexturedModelData().createModel())));
    public static final Item ROHIRRIC_ROYAL_GUARD_CHESTPLATE = registerArmorPiece("rohirric_royal_guard_chestplate",
            new CustomChestplateItem(ModArmorMaterials.ROHIRRIC_ROYAL_GUARD_ARMOR, ArmorItem.Type.CHESTPLATE, new Item.Settings()
                    .component(ModDataComponentTypes.CAPE_DATA, CapeDataComponent.newCape(ModCapes.ROHIRRIC_ROYAL_GUARD_CAPE)),
                    null));
    public static final Item ROHIRRIC_ROYAL_GUARD_LEGGINGS = registerArmorPiece("rohirric_royal_guard_leggings",
            new CustomLeggingsItem(ModArmorMaterials.ROHIRRIC_ROYAL_GUARD_ARMOR, ArmorItem.Type.LEGGINGS, new Item.Settings()));
    public static final Item ROHIRRIC_ROYAL_GUARD_BOOTS = registerArmorPiece("rohirric_royal_guard_boots",
            new CustomBootsItem(ModArmorMaterials.ROHIRRIC_ROYAL_GUARD_ARMOR, ArmorItem.Type.BOOTS, new Item.Settings()));

    public static final Item EORLING_MARSHAL_HELMET = registerArmorPiece("eorling_marshal_helmet",
            new CustomHelmetItem(ModArmorMaterials.EORLING_MARSHAL_ARMOR, ArmorItem.Type.HELMET, new Item.Settings(),
                    new RohirricHelmetArmorAddonModel(RohirricHelmetArmorAddonModel.getTexturedModelData().createModel())));
    public static final Item EORLING_MARSHAL_CHESTPLATE = registerArmorPiece("eorling_marshal_chestplate",
            new CustomChestplateItem(ModArmorMaterials.EORLING_MARSHAL_ARMOR, ArmorItem.Type.CHESTPLATE, new Item.Settings()
                    .component(ModDataComponentTypes.CAPE_DATA, CapeDataComponent.newCape(ModCapes.EORLING_MARHSAL_CAPE)),
                    null));
    public static final Item EORLING_MARSHAL_LEGGINGS = registerArmorPiece("eorling_marshal_leggings",
            new CustomLeggingsItem(ModArmorMaterials.EORLING_MARSHAL_ARMOR, ArmorItem.Type.LEGGINGS, new Item.Settings()));
    public static final Item EORLING_MARSHAL_BOOTS = registerArmorPiece("eorling_marshal_boots",
            new CustomBootsItem(ModArmorMaterials.EORLING_MARSHAL_ARMOR, ArmorItem.Type.BOOTS, new Item.Settings()));

    public static final Item HORSE_LORD_HELMET = registerArmorPiece("horse_lord_helmet",
            new CustomHelmetItem(ModArmorMaterials.HORSE_LORD_ARMOR, ArmorItem.Type.HELMET, new Item.Settings(),
                    new RohirricHelmetArmorAddonModel(RohirricHelmetArmorAddonModel.getTexturedModelData().createModel())));
    public static final Item HORSE_LORD_CHESTPLATE = registerArmorPiece("horse_lord_chestplate",
            new CustomChestplateItem(ModArmorMaterials.HORSE_LORD_ARMOR, ArmorItem.Type.CHESTPLATE, new Item.Settings()
                    .component(ModDataComponentTypes.CAPE_DATA, CapeDataComponent.newCape(ModCapes.HORSE_LORD_CAPE)),
                    null));
    public static final Item HORSE_LORD_LEGGINGS = registerArmorPiece("horse_lord_leggings",
            new CustomLeggingsItem(ModArmorMaterials.HORSE_LORD_ARMOR, ArmorItem.Type.LEGGINGS, new Item.Settings()));
    public static final Item HORSE_LORD_BOOTS = registerArmorPiece("horse_lord_boots",
            new CustomBootsItem(ModArmorMaterials.HORSE_LORD_ARMOR, ArmorItem.Type.BOOTS, new Item.Settings()));

    public static final Item ROHIRRIC_SHIELD = registerItem("rohirric_shield",
            new CustomShieldItem(new Item.Settings().maxCount(1).maxDamage(450), Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".rohan")));

    public static final Item ROHIRRIC_HORSE_ARMOR = registerGeneratedItem("rohirric_horse_armor",
            new CustomHorseArmorItem(ArmorMaterials.IRON, AnimalArmorItem.Type.EQUESTRIAN, false, new Item.Settings().maxCount(1), Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".rohan")));
    //endregion
    
    //region DALE
    public static final Item DALISH_BOOTS = registerArmorPiece("dalish_boots",
            new CustomBootsItem(ModArmorMaterials.DALE_T2, ArmorItem.Type.BOOTS, new Item.Settings()));

    public static final Item DALISH_CHAIN_HAUBERK = registerArmorPiece("dalish_chain_hauberk",
            new CustomChestplateItem(ModArmorMaterials.DALE_T3, ArmorItem.Type.CHESTPLATE, new Item.Settings(), null));

    public static final Item DALISH_CHAIN_COAT = registerArmorPiece("dalish_chain_coat",
            new CustomLeggingsItem(ModArmorMaterials.DALE_T3, ArmorItem.Type.LEGGINGS, new Item.Settings()));

    public static final Item DALISH_BURGONET = registerArmorPiece("dalish_burgonet",
            new CustomHelmetItem(ModArmorMaterials.DALE_T4, ArmorItem.Type.HELMET, new Item.Settings(),
                    new DaleHelmetModel(DaleHelmetModel.getTexturedModelData().createModel())));

    public static final Item DALISH_HEYDAY_HELMET = registerArmorPiece("dalish_heyday_helmet",
            new CustomHelmetItem(ModArmorMaterials.DALE_T4, ArmorItem.Type.HELMET, new Item.Settings(),
                    new DaleTallSpikeHelmetModel(DaleTallSpikeHelmetModel.getTexturedModelData().createModel())));
    public static final Item DALISH_HEYDAY_CHESTPLATE = registerArmorPiece("dalish_heyday_chestplate",
            new CustomChestplateItem(ModArmorMaterials.DALE_T4, ArmorItem.Type.CHESTPLATE, new Item.Settings()
                    .component(ModDataComponentTypes.CAPE_DATA, CapeDataComponent.newCape(ModCapes.DALISH_HEYDAY_CAPE)), null));
    public static final Item DALISH_HEYDAY_ARMING_COAT = registerArmorPiece("dalish_heyday_arming_coat",
            new CustomLeggingsItem(ModArmorMaterials.DALE_T4, ArmorItem.Type.LEGGINGS, new Item.Settings()));
    public static final Item DALISH_HEYDAY_BOOTS = registerArmorPiece("dalish_heyday_boots",
            new CustomBootsItem(ModArmorMaterials.DALE_T4, ArmorItem.Type.BOOTS, new Item.Settings()));

    public static final Item BARDING_SOLDIER_HELMET = registerArmorPiece("barding_soldier_helmet",
            new CustomHelmetItem(ModArmorMaterials.BARDING_SOLDIER, ArmorItem.Type.HELMET, new Item.Settings(),
                    new DaleHelmetModel(DaleHelmetModel.getTexturedModelData().createModel())));
    public static final Item BARDING_SOLDIER_CHESTPLATE = registerArmorPiece("barding_soldier_chestplate",
            new CustomChestplateItem(ModArmorMaterials.BARDING_SOLDIER, ArmorItem.Type.CHESTPLATE, new Item.Settings(), null));

    public static final Item BARDING_CHAIN_SKIRT = registerArmorPiece("barding_chain_skirt",
            new CustomLeggingsItem(ModArmorMaterials.BARDING_SOLDIER, ArmorItem.Type.LEGGINGS, new Item.Settings()));
    public static final Item DALISH_PLATED_BOOTS = registerArmorPiece("dalish_plated_boots",
            new CustomBootsItem(ModArmorMaterials.BARDING_SOLDIER, ArmorItem.Type.BOOTS, new Item.Settings()));

    public static final Item BARDING_SERGEANT_HELMET = registerArmorPiece("barding_sergeant_helmet",
            new CustomHelmetItem(ModArmorMaterials.BARDING_SOLDIER, ArmorItem.Type.HELMET, new Item.Settings(),
                    new DaleHelmetModel(DaleHelmetModel.getTexturedModelData().createModel())));
    public static final Item BARDING_SERGEANT_CHESTPLATE = registerArmorPiece("barding_sergeant_chestplate",
            new CustomChestplateItem(ModArmorMaterials.BARDING_SOLDIER, ArmorItem.Type.CHESTPLATE, new Item.Settings()
                    .component(ModDataComponentTypes.CAPE_DATA, CapeDataComponent.newCape(ModCapes.BARDING_SERGEANT_CAPE)), null));
    //endregion

    //endregion

    //region DWARVES

    //region GENERIC

    //endregion

    //region EREBOR

    public static final Item EREBOR_PLATE_HELMET = registerArmorPiece("erebor_plate_helmet",
            new CustomHelmetItem(ModArmorMaterials.EREBOR_PLATE_ARMOR, ArmorItem.Type.HELMET, new Item.Settings(),
                    new EreborHelmetModel(EreborHelmetModel.getTexturedModelData().createModel())));
    public static final Item EREBOR_PLATE_CHESTPLATE = registerArmorPiece("erebor_plate_chestplate",
            new CustomChestplateItem(ModArmorMaterials.EREBOR_PLATE_ARMOR, ArmorItem.Type.CHESTPLATE, new Item.Settings(),
                    new HaltChestplateModel(HaltChestplateModel.getTexturedModelData().createModel())));
    public static final Item EREBOR_PLATE_LEGGINGS = registerArmorPiece("erebor_plate_leggings",
            new CustomLeggingsItem(ModArmorMaterials.EREBOR_PLATE_ARMOR, ArmorItem.Type.LEGGINGS, new Item.Settings()));
    public static final Item EREBOR_PLATE_BOOTS = registerArmorPiece("erebor_plate_boots",
            new CustomBootsItem(ModArmorMaterials.EREBOR_PLATE_ARMOR, ArmorItem.Type.BOOTS, new Item.Settings()));

    public static final Item EREBOR_GATE_GUARD_HELMET = registerArmorPiece("erebor_gate_guard_helmet",
            new CustomHelmetItem(ModArmorMaterials.EREBOR_PLATE_ARMOR, ArmorItem.Type.HELMET, new Item.Settings(),
                    new EreborHelmetModel(EreborHelmetModel.getTexturedModelData().createModel())));
    public static final Item EREBOR_GATE_GUARD_CHESTPLATE = registerArmorPiece("erebor_gate_guard_chestplate",
            new CustomChestplateItem(ModArmorMaterials.EREBOR_PLATE_ARMOR, ArmorItem.Type.CHESTPLATE, new Item.Settings()
                    .component(ModDataComponentTypes.CAPE_DATA, CapeDataComponent.newCape(ModCapes.EREBOR_CAPE)),
                    new HaltChestplateModel(HaltChestplateModel.getTexturedModelData().createModel())));
    public static final Item EREBOR_GATE_GUARD_LEGGINGS = registerArmorPiece("erebor_gate_guard_leggings",
            new CustomLeggingsItem(ModArmorMaterials.EREBOR_PLATE_ARMOR, ArmorItem.Type.LEGGINGS, new Item.Settings()));
    public static final Item EREBOR_GATE_GUARD_BOOTS = registerArmorPiece("erebor_gate_guard_boots",
            new CustomBootsItem(ModArmorMaterials.EREBOR_PLATE_ARMOR, ArmorItem.Type.BOOTS, new Item.Settings()));
    
    public static final Item LONGBEARD_SHIELD = registerItem("longbeard_shield",
            new CustomShieldItem(new Item.Settings().maxCount(1).maxDamage(450), Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".longbeards")));

    //endregion

    //endregion

    //region ELVES
    //region GENERIC

    public static final Item ELVEN_ARMING_SKIRT = registerDyeableArmorPiece("elven_arming_skirt",
            new CustomLeggingsItem(ModArmorMaterials.ELVEN_T1, ArmorItem.Type.LEGGINGS, new Item.Settings()
                    .component(ModDataComponentTypes.DYE_DATA, CustomDyeableDataComponent.withOverlay(true, 15256475))));

    public static final Item ELVEN_BOOTS = registerArmorPiece("elven_boots",
            new CustomBootsItem(ModArmorMaterials.STURDY_BOOTS, ArmorItem.Type.BOOTS, new Item.Settings()));

    public static final Item ELVEN_CHAIN_COIF = registerArmorPiece("elven_chain_coif",
            new CustomHelmetItem(ModArmorMaterials.CHAIN_ARMOR, ArmorItem.Type.HELMET, new Item.Settings(),
                    null));

    public static final Item ELVEN_CHAIN_HAUBERK = registerArmorPiece("elven_chain_hauberk",
            new CustomChestplateItem(ModArmorMaterials.CHAIN_ARMOR, ArmorItem.Type.CHESTPLATE, new Item.Settings(),
                    null));

    public static final Item ELVEN_CHAIN_SKIRT = registerArmorPiece("elven_chain_skirt",
            new CustomLeggingsItem(ModArmorMaterials.CHAIN_ARMOR, ArmorItem.Type.LEGGINGS, new Item.Settings()));
    //endregion

    //region LOTHLORIEN
    public static final Item LORIEN_PLATE_HELMET = registerArmorPiece("lorien_plate_helmet",
            new CustomHelmetItem(ModArmorMaterials.LORIEN_PLATE_ARMOR, ArmorItem.Type.HELMET, new Item.Settings()
                    .component(ModDataComponentTypes.HOOD_DATA, HoodDataComponent.newHood(ModHoods.LORIEN_HOOD)),
                    new LorienHelmetArmorAddonModel(LorienHelmetArmorAddonModel.getTexturedModelData().createModel())));
    public static final Item LORIEN_PLATE_CHESTPLATE = registerArmorPiece("lorien_plate_chestplate",
            new CustomChestplateItem(ModArmorMaterials.LORIEN_PLATE_ARMOR, ArmorItem.Type.CHESTPLATE, new Item.Settings()
                    .component(ModDataComponentTypes.CAPE_DATA, CapeDataComponent.newCape(ModCapes.LORIEN_CAPE)),
                    null));
    public static final Item LORIEN_PLATE_LEGGINGS = registerArmorPiece("lorien_plate_leggings",
            new CustomLeggingsItem(ModArmorMaterials.LORIEN_PLATE_ARMOR, ArmorItem.Type.LEGGINGS, new Item.Settings()));
    public static final Item LORIEN_PLATE_BOOTS = registerArmorPiece("lorien_plate_boots",
            new CustomBootsItem(ModArmorMaterials.LORIEN_PLATE_ARMOR, ArmorItem.Type.BOOTS, new Item.Settings()));

    public static final Item LORIEN_COMMANDER_HELMET = registerArmorPiece("lorien_commander_helmet",
            new CustomHelmetItem(ModArmorMaterials.LORIEN_COMMANDER_ARMOR, ArmorItem.Type.HELMET, new Item.Settings()
                    .component(ModDataComponentTypes.CAPE_DATA, CapeDataComponent.newCape(ModCapes.LORIEN_COMMANDER_CAPE)),
                    new LorienHelmetArmorAddonModel(LorienHelmetArmorAddonModel.getTexturedModelData().createModel())));
    public static final Item LORIEN_COMMANDER_CHESTPLATE = registerArmorPiece("lorien_commander_chestplate",
            new CustomChestplateItem(ModArmorMaterials.LORIEN_COMMANDER_ARMOR, ArmorItem.Type.CHESTPLATE, new Item.Settings(),
                    null));
    public static final Item LORIEN_COMMANDER_LEGGINGS = registerArmorPiece("lorien_commander_leggings",
            new CustomLeggingsItem(ModArmorMaterials.LORIEN_COMMANDER_ARMOR, ArmorItem.Type.LEGGINGS, new Item.Settings()));
    public static final Item LORIEN_COMMANDER_BOOTS = registerArmorPiece("lorien_commander_boots",
            new CustomBootsItem(ModArmorMaterials.LORIEN_COMMANDER_ARMOR, ArmorItem.Type.BOOTS, new Item.Settings()));

    public static final Item LORIEN_SHIELD = registerItem("lorien_shield",
            new CustomShieldItem(new Item.Settings().maxCount(1).maxDamage(450), Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".lothlorien")));

    public static final Item LORIEN_HORSE_ARMOR = registerGeneratedItem("lorien_horse_armor",
            new CustomHorseArmorItem(ArmorMaterials.IRON, AnimalArmorItem.Type.EQUESTRIAN, false, new Item.Settings().maxCount(1), Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".lothlorien")));

    //endregion
    //endregion

    //region ORCS

    //region GENERIC
    public static final Item ORC_MAIL_COIF = registerRustyArmorPiece("orc_mail_coif",
            new CustomHelmetItem(ModArmorMaterials.ORC_MAIl_GENERIC, ArmorItem.Type.HELMET, new Item.Settings()));
    public static final Item ORC_MAIL_HAUBERK = registerRustyArmorPiece("orc_mail_hauberk",
            new CustomChestplateItem(ModArmorMaterials.ORC_MAIl_GENERIC, ArmorItem.Type.CHESTPLATE, new Item.Settings()));
    public static final Item ORC_MAIL_COAT = registerRustyArmorPiece("orc_mail_coat",
            new CustomLeggingsItem(ModArmorMaterials.ORC_MAIl_GENERIC, ArmorItem.Type.LEGGINGS, new Item.Settings()));

    public static final Item ORC_SALLET = registerRustyArmorPiece("orc_sallet",
            new CustomHelmetItem(ModArmorMaterials.ORC_T4_GENERIC, ArmorItem.Type.HELMET, new Item.Settings(),
                    new SalletHelmetAddonModel(SalletHelmetAddonModel.getTexturedModelData().createModel())));

    public static final Item ORC_GORGET_HAUBERK = registerRustyArmorPiece("orc_gorget_hauberk",
            new CustomChestplateItem(ModArmorMaterials.ORC_T4_GENERIC, ArmorItem.Type.CHESTPLATE, new Item.Settings()));

    public static final Item ORC_PLATE_BOOTS = registerRustyArmorPiece("orc_plate_boots",
            new CustomBootsItem(ModArmorMaterials.ORC_T4_GENERIC, ArmorItem.Type.BOOTS, new Item.Settings()));
    //endregion

    //region MORDOR
    public static final Item MORDOR_ORC_CHESTPLATE = registerRustyArmorPiece("mordor_orc_chestplate",
            new CustomChestplateItem(ModArmorMaterials.MORDOR_ORC_T5, ArmorItem.Type.CHESTPLATE, new Item.Settings()));

    public static final Item BLACK_URUK_PLATE_HELMET = registerArmorPiece("black_uruk_plate_helmet",
            new CustomHelmetItem(ModArmorMaterials.MORDOR_BLACK_URUK_PLATE_ARMOR, ArmorItem.Type.HELMET, new Item.Settings()));
    public static final Item BLACK_URUK_PLATE_CHESTPLATE = registerArmorPiece("black_uruk_plate_chestplate",
            new CustomChestplateItem(ModArmorMaterials.MORDOR_BLACK_URUK_PLATE_ARMOR, ArmorItem.Type.CHESTPLATE, new Item.Settings(),
                    new HaltChestplateModel(HaltChestplateModel.getTexturedModelData().createModel())));
    public static final Item BLACK_URUK_PLATE_LEGGINGS = registerArmorPiece("black_uruk_plate_leggings",
            new CustomLeggingsItem(ModArmorMaterials.MORDOR_BLACK_URUK_PLATE_ARMOR, ArmorItem.Type.LEGGINGS, new Item.Settings()));
    public static final Item BLACK_URUK_PLATE_BOOTS = registerArmorPiece("black_uruk_plate_boots",
            new CustomBootsItem(ModArmorMaterials.MORDOR_BLACK_URUK_PLATE_ARMOR, ArmorItem.Type.BOOTS, new Item.Settings()));

    public static final Item BLACK_URUK_COMMANDER_HELMET = registerArmorPiece("black_uruk_commander_helmet",
            new CustomHelmetItem(ModArmorMaterials.MORDOR_BLACK_URUK_PLATE_ARMOR, ArmorItem.Type.HELMET, new Item.Settings(),
                    new HelmetSkullModel(HelmetSkullModel.getTexturedModelData().createModel())));
    public static final Item BLACK_URUK_COMMANDER_CHESTPLATE = registerArmorPiece("black_uruk_commander_chestplate",
            new CustomChestplateItem(ModArmorMaterials.MORDOR_BLACK_URUK_PLATE_ARMOR, ArmorItem.Type.CHESTPLATE, new Item.Settings(),
                    new HaltChestplateModel(HaltChestplateModel.getTexturedModelData().createModel())));

    public static final Item MORDOR_SHIELD = registerItem("mordor_shield",
            new CustomShieldItem(new Item.Settings().maxCount(1).maxDamage(450), Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".mordor")));

    public static final Item NAZGUL_CLOAK_HOOD = registerArmorPiece("nazgul_cloak_hood",
            new CustomHelmetItem(ModArmorMaterials.NAZGUL_CLOAK, ArmorItem.Type.HELMET, new Item.Settings(),
                    null));
    public static final Item NAZGUL_CLOAK = registerArmorPiece("nazgul_cloak",
            new CustomChestplateItem(ModArmorMaterials.NAZGUL_CLOAK, ArmorItem.Type.CHESTPLATE, new Item.Settings(),
                    null));
    public static final Item NAZGUL_PANTS = registerArmorPiece("nazgul_pants",
            new CustomLeggingsItem(ModArmorMaterials.NAZGUL_CLOAK, ArmorItem.Type.LEGGINGS, new Item.Settings()));
    public static final Item NAZGUL_BOOTS = registerArmorPiece("nazgul_boots",
            new CustomBootsItem(ModArmorMaterials.NAZGUL_CLOAK, ArmorItem.Type.BOOTS, new Item.Settings()));

    //endregion

    //region MISTY MOUNTAINS ORCS

    public static final Item MISTY_MOUNTAINS_SHIELD = registerItem("misty_mountains_shield",
            new CustomShieldItem(new Item.Settings().maxCount(1).maxDamage(450), Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".misty_orcs")));

    /*public static final Item STEEL_TROLL_ARMOR = registerGeneratedItem("steel_troll_armor",
            new TrollArmorItem(10, "steel", new Item.Settings().maxCount(1)));*/

    //endregion

    //endregion

    private static Item registerItem(String name, Item item) {
        ModItemGroups.EQUIPMENT_CONTENTS.add(item.getDefaultStack());
        return Registry.register(Registries.ITEM, new Identifier(MiddleEarth.MOD_ID, name), item);
    }

    private static Item registerGeneratedItem(String name, Item item) {
        ModItemGroups.EQUIPMENT_CONTENTS.add(item.getDefaultStack());
        SimpleItemModel.items.add(item);
        return Registry.register(Registries.ITEM, new Identifier(MiddleEarth.MOD_ID, name), item);
    }

    private static Item registerDyeableArmorPiece(String name, Item item) {
        ModItemGroups.EQUIPMENT_CONTENTS.add(item.getDefaultStack());
        SimpleDyeableItemModel.items.add(item);
        switch (item){
            case CustomHelmetItem helmetItem -> armorPiecesListHelmets.add(helmetItem);
            case CustomChestplateItem chestplateItem -> armorPiecesListChestplates.add(chestplateItem);
            case CustomLeggingsItem leggingsItem -> armorPiecesListLeggings.add(leggingsItem);
            case CustomBootsItem bootsItem -> armorPiecesListBoots.add(bootsItem);
            default -> throw new IllegalStateException("Unexpected value: " + item);
        }
        return Registry.register(Registries.ITEM, new Identifier(MiddleEarth.MOD_ID, name), item);
    }

    private static Item registerRustyArmorPiece(String name, Item item) {
        ModItemGroups.EQUIPMENT_CONTENTS.add(item.getDefaultStack());
        SimpleItemModel.items.add(item);
        switch (item){
            case CustomHelmetItem helmetItem -> armorPiecesListRustyHelmets.add(helmetItem);
            case CustomChestplateItem chestplateItem -> armorPiecesListRustyChestplates.add(chestplateItem);
            case CustomLeggingsItem leggingsItem -> armorPiecesListRustyLeggings.add(leggingsItem);
            case CustomBootsItem bootsItem -> armorPiecesListRustyBoots.add(bootsItem);
            default -> throw new IllegalStateException("Unexpected value: " + item);
        }
        return Registry.register(Registries.ITEM, new Identifier(MiddleEarth.MOD_ID, name), item);
    }

    private static Item registerArmorPiece(String name, Item item) {
        ModItemGroups.EQUIPMENT_CONTENTS.add(item.getDefaultStack());
        SimpleItemModel.items.add(item);
        switch (item){
            case CustomHelmetItem helmetItem -> armorPiecesListHelmets.add(helmetItem);
            case CustomChestplateItem chestplateItem -> armorPiecesListChestplates.add(chestplateItem);
            case CustomLeggingsItem leggingsItem -> armorPiecesListLeggings.add(leggingsItem);
            case CustomBootsItem bootsItem -> armorPiecesListBoots.add(bootsItem);
            default -> throw new IllegalStateException("Unexpected value: " + item);
        }
        return Registry.register(Registries.ITEM, new Identifier(MiddleEarth.MOD_ID, name), item);
    }

    public static void registerModItems() {
        LoggerUtil.getInstance().logDebugMsg("Registering Mod Equipment Items for " + MiddleEarth.MOD_ID);
    }
}
