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
import net.jukoz.me.item.utils.*;
import net.jukoz.me.utils.LoggerUtil;
import net.jukoz.me.utils.ModFactions;
import net.jukoz.me.utils.ModSubFactions;
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
    //Cloaks
    public static final Item HOOD = registerGeneratedItem("hood",
            new HoodHelmetItem(new Item.Settings()
                    .component(ModDataComponentTypes.HOOD_DATA, HoodDataComponent.newHood(ModHoods.BASE_HOOD))));
    public static final Item CAPE = registerGeneratedItem("cape",
            new CapeChestplateItem(new Item.Settings()
                    .component(ModDataComponentTypes.CAPE_DATA, CapeDataComponent.newCape(ModCapes.BASE_CAPE))));

    //Fur cloaks
    public static final Item FUR_HOOD = registerGeneratedItem("fur_hood",
            new HoodHelmetItem(new Item.Settings()
                    .component(ModDataComponentTypes.HOOD_DATA, HoodDataComponent.newHood(ModHoods.FUR_HOOD))));
    public static final Item FUR_CLOAK = registerGeneratedItem("fur_cloak",
            new CapeChestplateItem(new Item.Settings()
                    .component(ModDataComponentTypes.CAPE_DATA, CapeDataComponent.newCape(ModCapes.FUR_CLOAK))));

    public static final Item STRAW_HAT = registerArmorPiece("straw_hat",
            new CustomHelmetItem(ModArmorMaterials.STRAW_T1, ArmorItem.Type.HELMET, new Item.Settings(),
                    ModFactions.NONE, new HatArmorAddonModel(HatArmorAddonModel.getTexturedModelData().createModel())));

    public static final Item WOVEN_HAT = registerArmorPiece("woven_hat",
            new CustomHelmetItem(ModArmorMaterials.LEATHER_T1, ArmorItem.Type.HELMET, new Item.Settings(),
                    ModFactions.NONE, new HatArmorAddonModel(HatArmorAddonModel.getTexturedModelData().createModel())));

    public static final Item BYCOCKET = registerDyeableArmorPiece("bycocket",
            new CustomHelmetItem(ModArmorMaterials.LEATHER_T1, ArmorItem.Type.HELMET, new Item.Settings()
                    .component(ModDataComponentTypes.DYE_DATA, CustomDyeableDataComponent.withOverlay(false, -6265536)),
                    ModFactions.NONE));

    public static final Item ARMING_COAT = registerDyeableArmorPiece("arming_coat",
            new CustomChestplateItem(ModArmorMaterials.LEATHER_T1, ArmorItem.Type.CHESTPLATE, new Item.Settings()
                    .component(ModDataComponentTypes.DYE_DATA, CustomDyeableDataComponent.withOverlay(true, 15256475)),
                    ModFactions.NONE));

    public static final Item ARMING_SKIRT = registerDyeableArmorPiece("arming_skirt",
            new CustomLeggingsItem(ModArmorMaterials.LEATHER_T1, ArmorItem.Type.LEGGINGS, new Item.Settings()
                    .component(ModDataComponentTypes.DYE_DATA, CustomDyeableDataComponent.withOverlay(true, 15256475)),
                    ModFactions.NONE));

    public static final Item SHOES = registerArmorPiece("shoes",
            new CustomBootsItem(ModArmorMaterials.LEATHER_T1, ArmorItem.Type.BOOTS, new Item.Settings(),
                    ModFactions.NONE));

    public static final Item WORK_SHOES = registerArmorPiece("work_shoes",
            new CustomBootsItem(ModArmorMaterials.LEATHER_T1, ArmorItem.Type.BOOTS, new Item.Settings(),
                    ModFactions.NONE));

    public static final Item LEATHER_CAP = registerDyeableArmorPiece("leather_cap",
            new CustomHelmetItem(ModArmorMaterials.LEATHER_T2, ArmorItem.Type.HELMET, new Item.Settings()
                    .component(ModDataComponentTypes.DYE_DATA, CustomDyeableDataComponent.withOverlay(false, 15256475)),
                    ModFactions.NONE));

    public static final Item GAMBESON_CAP = registerDyeableArmorPiece("gambeson_cap",
            new CustomHelmetItem(ModArmorMaterials.LEATHER_T2, ArmorItem.Type.HELMET, new Item.Settings()
                    .component(ModDataComponentTypes.DYE_DATA, CustomDyeableDataComponent.withOverlay(false, 15256475)),
                    ModFactions.NONE));
    public static final Item GAMBESON_COWL = registerDyeableArmorPiece("gambeson_cowl",
            new CustomHelmetItem(ModArmorMaterials.LEATHER_T2, ArmorItem.Type.HELMET, new Item.Settings()
                    .component(ModDataComponentTypes.DYE_DATA, CustomDyeableDataComponent.withOverlay(true, 15256475)),
                    ModFactions.NONE));

    public static final Item GAMBESON = registerDyeableArmorPiece("gambeson",
            new CustomChestplateItem(ModArmorMaterials.LEATHER_T2, ArmorItem.Type.CHESTPLATE, new Item.Settings()
                    .component(ModDataComponentTypes.DYE_DATA, CustomDyeableDataComponent.withOverlay(true, 15256475)),
                    ModFactions.NONE));

    public static final Item LEATHER_VEST = registerDyeableArmorPiece("leather_vest",
            new CustomChestplateItem(ModArmorMaterials.LEATHER_T2, ArmorItem.Type.CHESTPLATE, new Item.Settings()
                    .component(ModDataComponentTypes.DYE_DATA, CustomDyeableDataComponent.withOverlay(false, -6265536)),
                    ModFactions.NONE));

    public static final Item LEATHER_SCALE_VEST = registerDyeableArmorPiece("leather_scale_vest",
            new CustomChestplateItem(ModArmorMaterials.LEATHER_T2, ArmorItem.Type.CHESTPLATE, new Item.Settings()
                    .component(ModDataComponentTypes.DYE_DATA, CustomDyeableDataComponent.withOverlay(true, -6265536)),
                    ModFactions.NONE));

    public static final Item STURDY_BOOTS = registerArmorPiece("sturdy_boots",
            new CustomBootsItem(ModArmorMaterials.LEATHER_T2, ArmorItem.Type.BOOTS, new Item.Settings(),
                    ModFactions.NONE));
    public static final Item TRAVELLING_BOOTS = registerArmorPiece("travelling_boots",
            new CustomBootsItem(ModArmorMaterials.LEATHER_T2, ArmorItem.Type.BOOTS, new Item.Settings(),
                    ModFactions.NONE));
    public static final Item HIGH_CUT_BOOTS = registerArmorPiece("high_cut_boots",
            new CustomBootsItem(ModArmorMaterials.LEATHER_T2, ArmorItem.Type.BOOTS, new Item.Settings(),
                    ModFactions.NONE));

    public static final Item CHAIN_COIF = registerArmorPiece("chain_coif",
            new CustomHelmetItem(ModArmorMaterials.IRON_T3, ArmorItem.Type.HELMET, new Item.Settings(),
                    ModFactions.NONE));

    public static final Item KETTLE_HAT = registerArmorPiece("kettle_hat",
            new CustomHelmetItem(ModArmorMaterials.IRON_T3, ArmorItem.Type.HELMET, new Item.Settings(),
                    ModFactions.NONE,
                    new KettleHatArmorAddonModel(KettleHatArmorAddonModel.getTexturedModelData().createModel())));

    public static final Item OPEN_FACE_HELMET = registerArmorPiece("open_face_helmet",
            new CustomHelmetItem(ModArmorMaterials.IRON_T3, ArmorItem.Type.HELMET, new Item.Settings(),
                    ModFactions.NONE));

    public static final Item CHAIN_HAUBERK = registerArmorPiece("chain_hauberk",
            new CustomChestplateItem(ModArmorMaterials.IRON_T3, ArmorItem.Type.CHESTPLATE, new Item.Settings(),
                    ModFactions.NONE));

    public static final Item CHAIN_SKIRT = registerArmorPiece("chain_skirt",
            new CustomLeggingsItem(ModArmorMaterials.IRON_T3, ArmorItem.Type.LEGGINGS, new Item.Settings(),
                    ModFactions.NONE));

    public static final Item SALLET = registerArmorPiece("sallet",
            new CustomHelmetItem(ModArmorMaterials.STEEL_T4, ArmorItem.Type.HELMET, new Item.Settings(),
                    ModFactions.NONE,
                    new SalletHelmetAddonModel(SalletHelmetAddonModel.getTexturedModelData().createModel())));
    //endregion

    //region HOBBITS
    public static final Item SHIRRIFF_HAT = registerDyeableArmorPiece("shirriff_hat",
            new CustomHelmetItem(ModArmorMaterials.LEATHER_T1, ArmorItem.Type.HELMET, new Item.Settings()
                    .component(ModDataComponentTypes.DYE_DATA, CustomDyeableDataComponent.withOverlay(true, -6265536)),
                    ModFactions.SHIRE,
                    new HatArmorAddonModel(HatArmorAddonModel.getTexturedModelData().createModel())));

    //endregion

    //region MEN
    //region GONDOR
    public static final Item GONDORIAN_BOOTS = registerArmorPiece("gondorian_boots",
            new CustomBootsItem(ModArmorMaterials.LEATHER_T2, ArmorItem.Type.BOOTS, new Item.Settings(),
                    ModFactions.GONDOR));

    public static final Item GONDORIAN_CABASSET_HELMET = registerArmorPiece("gondorian_cabasset_helmet",
            new CustomHelmetItem(ModArmorMaterials.IRON_T3, ArmorItem.Type.HELMET, new Item.Settings(),
                    ModFactions.GONDOR,
                    new GondorianHelmetModel(GondorianHelmetModel.getTexturedModelData().createModel())));

    public static final Item GONDORIAN_TABBARD = registerArmorPiece("gondorian_tabbard",
            new CustomChestplateItem(ModArmorMaterials.IRON_T3, ArmorItem.Type.CHESTPLATE, new Item.Settings(),
                    ModFactions.GONDOR));

    public static final Item GONDORIAN_LEATHER_CUIRASS = registerDyeableArmorPiece("gondorian_leather_cuirass",
            new CustomChestplateItem(ModArmorMaterials.IRON_T3, ArmorItem.Type.CHESTPLATE, new Item.Settings()
                    .component(ModDataComponentTypes.DYE_DATA, CustomDyeableDataComponent.withOverlay(true, -6265536)),
                    ModFactions.GONDOR));
    public static final Item GONDORIAN_LEATHER_CHESTPLATE = registerDyeableArmorPiece("gondorian_leather_chestplate",
            new CustomChestplateItem(ModArmorMaterials.IRON_T3, ArmorItem.Type.CHESTPLATE, new Item.Settings()
                    .component(ModDataComponentTypes.DYE_DATA, CustomDyeableDataComponent.withOverlay(true, -6265536)),
                    ModFactions.GONDOR));

    public static final Item GONDORIAN_CHAIN_COAT = registerArmorPiece("gondorian_chain_coat",
            new CustomLeggingsItem(ModArmorMaterials.IRON_T3, ArmorItem.Type.LEGGINGS, new Item.Settings(),
                    ModFactions.GONDOR));

    public static final Item GONDORIAN_SOLDIER_HELMET = registerArmorPiece("gondorian_soldier_helmet",
            new CustomHelmetItem(ModArmorMaterials.STEEL_T4, ArmorItem.Type.HELMET, new Item.Settings(),
                    ModFactions.GONDOR,
                    new GondorianHelmetModel(GondorianHelmetModel.getTexturedModelData().createModel())));

    public static final Item GONDORIAN_SOLDIER_CHESTPLATE = registerArmorPiece("gondorian_soldier_chestplate",
            new CustomChestplateItem(ModArmorMaterials.STEEL_T4, ArmorItem.Type.CHESTPLATE, new Item.Settings(),
                    ModFactions.GONDOR));

    public static final Item GONDORIAN_PLATE_HELMET = registerArmorPiece("gondorian_plate_helmet",
            new CustomHelmetItem(ModArmorMaterials.STEEL_T5, ArmorItem.Type.HELMET, new Item.Settings(),
                    ModFactions.GONDOR,
                    new GondorianHelmetModel(GondorianHelmetModel.getTexturedModelData().createModel())));
    public static final Item GONDORIAN_PLATE_CHESTPLATE = registerArmorPiece("gondorian_plate_chestplate",
            new CustomChestplateItem(ModArmorMaterials.STEEL_T5, ArmorItem.Type.CHESTPLATE, new Item.Settings(),
                    ModFactions.GONDOR,
                    new HaltChestplateModel(HaltChestplateModel.getTexturedModelData().createModel())));
    public static final Item GONDORIAN_PLATE_LEGGINGS = registerArmorPiece("gondorian_plate_leggings",
            new CustomLeggingsItem(ModArmorMaterials.STEEL_T5, ArmorItem.Type.LEGGINGS, new Item.Settings(),
                    ModFactions.GONDOR));
    public static final Item GONDORIAN_PLATE_BOOTS = registerArmorPiece("gondorian_plate_boots",
            new CustomBootsItem(ModArmorMaterials.STEEL_T5, ArmorItem.Type.BOOTS, new Item.Settings(),
                    ModFactions.GONDOR));

    public static final Item GONDORIAN_CAPTAIN_HELMET = registerArmorPiece("gondorian_captain_helmet",
            new CustomHelmetItem(ModArmorMaterials.STEEL_T5, ArmorItem.Type.HELMET, new Item.Settings(),
                    ModFactions.GONDOR,
                    new GondorianHelmetModel(GondorianHelmetModel.getTexturedModelData().createModel())));

    public static final Item GONDORIAN_KINGS_GUARD_HELMET = registerArmorPiece("gondorian_kings_guard_helmet",
            new CustomHelmetItem(ModArmorMaterials.STEEL_T5, ArmorItem.Type.HELMET, new Item.Settings(),
                    ModFactions.GONDOR,
                    new GondorianHelmetModel(GondorianHelmetModel.getTexturedModelData().createModel())));
    public static final Item GONDORIAN_KINGS_GUARD_CHESTKPLATE = registerArmorPiece("gondorian_kings_guard_chestplate",
            new CustomChestplateItem(ModArmorMaterials.STEEL_T5, ArmorItem.Type.CHESTPLATE, new Item.Settings()
                        .component(ModDataComponentTypes.CAPE_DATA, CapeDataComponent.newCape(ModCapes.GONDORIAN_KINGS_GUARD_CAPE)),
                    ModFactions.GONDOR,
                    new HaltChestplateModel(HaltChestplateModel.getTexturedModelData().createModel())));
    public static final Item GONDORIAN_KINGS_GUARD_LEGGINGS = registerArmorPiece("gondorian_kings_guard_leggings",
            new CustomLeggingsItem(ModArmorMaterials.STEEL_T5, ArmorItem.Type.LEGGINGS, new Item.Settings(),
                    ModFactions.GONDOR));
    public static final Item GONDORIAN_KINGS_GUARD_BOOTS = registerArmorPiece("gondorian_kings_guard_boots",
            new CustomBootsItem(ModArmorMaterials.STEEL_T5, ArmorItem.Type.BOOTS, new Item.Settings(),
                    ModFactions.GONDOR));

    public static final Item GONDORIAN_CITADEL_GUARD_HELMET = registerArmorPiece("gondorian_citadel_guard_helmet",
            new CustomHelmetItem(ModArmorMaterials.STEEL_T5, ArmorItem.Type.HELMET, new Item.Settings(),
                    ModFactions.GONDOR,
                    new GondorianHelmetModel(GondorianHelmetModel.getTexturedModelData().createModel())));
    public static final Item GONDORIAN_CITADEL_GUARD_CHESTPLATE = registerArmorPiece("gondorian_citadel_guard_chestplate",
            new CustomChestplateItem(ModArmorMaterials.STEEL_T5, ArmorItem.Type.CHESTPLATE, new Item.Settings()
                    .component(ModDataComponentTypes.CAPE_DATA, CapeDataComponent.newCape(ModCapes.GONDORIAN_CITADEL_GUARD_CAPE)),
                    ModFactions.GONDOR));
    public static final Item GONDORIAN_CITADEL_GUARD_LEGGINGS = registerArmorPiece("gondorian_citadel_guard_leggings",
            new CustomLeggingsItem(ModArmorMaterials.STEEL_T5, ArmorItem.Type.LEGGINGS, new Item.Settings(),
                    ModFactions.GONDOR));
    public static final Item GONDORIAN_CITADEL_GUARD_BOOTS = registerArmorPiece("gondorian_citadel_guard_boots",
            new CustomBootsItem(ModArmorMaterials.STEEL_T5, ArmorItem.Type.BOOTS, new Item.Settings(),
                    ModFactions.GONDOR));

    public static final Item GONDORIAN_FOUNTAIN_GUARD_HELMET = registerArmorPiece("gondorian_fountain_guard_helmet",
            new CustomHelmetItem(ModArmorMaterials.STEEL_T5, ArmorItem.Type.HELMET, new Item.Settings(),
                    ModFactions.GONDOR,
                    new GondorianHelmetModel(GondorianHelmetModel.getTexturedModelData().createModel())));
    public static final Item GONDORIAN_FOUNTAIN_GUARD_CHESTPLATE = registerArmorPiece("gondorian_fountain_guard_chestplate",
            new CustomChestplateItem(ModArmorMaterials.STEEL_T5, ArmorItem.Type.CHESTPLATE, new Item.Settings()
                    .component(ModDataComponentTypes.CAPE_DATA, CapeDataComponent.newCape(ModCapes.GONDORIAN_FOUNTAIN_GUARD_CAPE)),
                    ModFactions.GONDOR,
                    new HaltChestplateModel(HaltChestplateModel.getTexturedModelData().createModel())));
    public static final Item GONDORIAN_FOUNTAIN_GUARD_LEGGINGS = registerArmorPiece("gondorian_fountain_guard_leggings",
            new CustomLeggingsItem(ModArmorMaterials.STEEL_T5, ArmorItem.Type.LEGGINGS, new Item.Settings(),
                    ModFactions.GONDOR));
    public static final Item GONDORIAN_FOUNTAIN_GUARD_BOOTS = registerArmorPiece("gondorian_fountain_guard_boots",
            new CustomBootsItem(ModArmorMaterials.STEEL_T5, ArmorItem.Type.BOOTS, new Item.Settings(),
                    ModFactions.GONDOR));

    public static final Item GONDORIAN_SHIELD = registerItem("gondorian_shield",
            new CustomShieldItem(ModShieldTypes.MEDIUM_SHIELD, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".gondor")));
    public static final Item GONDORIAN_TOWER_SHIELD = registerItem("gondorian_tower_shield",
            new CustomShieldItem(ModShieldTypes.MEDIUM_SHIELD, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".gondor")));
    public static final Item GONDORIAN_KINGS_GUARD_TOWER_SHIELD = registerItem("gondorian_kings_guard_tower_shield",
            new CustomShieldItem(ModShieldTypes.MEDIUM_SHIELD, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".gondor")));
    public static final Item LAST_ALLIANCE_HEILOOM_TOWER_SHIELD = registerItem("last_alliance_heirloom_tower_shield",
            new CustomShieldItem(ModShieldTypes.MEDIUM_SHIELD, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".gondor")));
    public static final Item GONDORIAN_HERO_SHIELD = registerItem("gondorian_hero_shield",
            new CustomShieldItem(ModShieldTypes.MEDIUM_SHIELD, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".gondor")));
    public static final Item GONDORIAN_KNIGHT_SHIELD = registerItem("gondorian_knight_shield",
            new CustomShieldItem(ModShieldTypes.HEAVY_SHIELD, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".gondor")));
    public static final Item GONDORIAN_ORNAMENTED_KNIGHT_SHIELD = registerItem("gondorian_ornamented_knight_shield",
            new CustomShieldItem(ModShieldTypes.HEAVY_SHIELD, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".gondor")));

    public static final Item GONDORIAN_HORSE_ARMOR = registerGeneratedItem("gondorian_horse_armor",
            new CustomHorseArmorItem(ArmorMaterials.IRON, AnimalArmorItem.Type.EQUESTRIAN, false, new Item.Settings().maxCount(1), Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".gondor")));

    //endregion

    //region ROHAN
    public static final Item ROHIRRIC_COAT = registerDyeableArmorPiece("rohirric_coat",
            new CustomChestplateItem(ModArmorMaterials.LEATHER_T1, ArmorItem.Type.CHESTPLATE, new Item.Settings()
                    .component(ModDataComponentTypes.DYE_DATA, CustomDyeableDataComponent.withOverlay(true, 15256475))
                    .component(ModDataComponentTypes.CAPE_DATA, CapeDataComponent.newCape(ModCapes.ROHIRRIC_CAPE)),
                    ModFactions.ROHAN));
    public static final Item ROHIRRIC_REINFORCED_COAT = registerDyeableArmorPiece("rohirric_reinforced_coat",
            new CustomChestplateItem(ModArmorMaterials.LEATHER_T1, ArmorItem.Type.CHESTPLATE, new Item.Settings()
                    .component(ModDataComponentTypes.DYE_DATA, CustomDyeableDataComponent.withOverlay(true, 15256475))
                    .component(ModDataComponentTypes.CAPE_DATA, CapeDataComponent.newCape(ModCapes.ROHIRRIC_CAPE)),
                    ModFactions.ROHAN));

    public static final Item ROHIRRIC_LEATHER_HELMET = registerArmorPiece("rohirric_leather_helmet",
            new CustomHelmetItem(ModArmorMaterials.LEATHER_T2, ArmorItem.Type.HELMET, new Item.Settings(),
                    ModFactions.ROHAN));
    public static final Item ROHIRRIC_REIINFORCED_LEATHER_HELMET = registerArmorPiece("rohirric_reinforced_leather_helmet",
            new CustomHelmetItem(ModArmorMaterials.LEATHER_T2, ArmorItem.Type.HELMET, new Item.Settings(),
                    ModFactions.ROHAN,
                    new RohirricHelmetArmorAddonModel(RohirricHelmetArmorAddonModel.getTexturedModelData().createModel())));
    public static final Item ROHIRRIC_ORNEMENTED_LEATHER_HELMET = registerArmorPiece("rohirric_ornemented_leather_helmet",
            new CustomHelmetItem(ModArmorMaterials.LEATHER_T2, ArmorItem.Type.HELMET, new Item.Settings(),
                    ModFactions.ROHAN));

    public static final Item ROHIRRIC_LEATHER_VEST = registerDyeableArmorPiece("rohirric_leather_vest",
            new CustomChestplateItem(ModArmorMaterials.LEATHER_T2, ArmorItem.Type.CHESTPLATE, new Item.Settings()
                    .component(ModDataComponentTypes.DYE_DATA, CustomDyeableDataComponent.withOverlay(true, 15256475))
                    .component(ModDataComponentTypes.CAPE_DATA, CapeDataComponent.newCape(ModCapes.ROHIRRIC_CAPE)),
                    ModFactions.ROHAN));
    public static final Item ROHIRRIC_LEATHER_SCALE_VEST = registerDyeableArmorPiece("rohirric_leather_scale_vest",
            new CustomChestplateItem(ModArmorMaterials.LEATHER_T2, ArmorItem.Type.CHESTPLATE, new Item.Settings()
                    .component(ModDataComponentTypes.DYE_DATA, CustomDyeableDataComponent.withOverlay(true, 15256475))
                    .component(ModDataComponentTypes.CAPE_DATA, CapeDataComponent.newCape(ModCapes.ROHIRRIC_CAPE)),
                    ModFactions.ROHAN));
    public static final Item ROHIRRIC_GAMBESON = registerDyeableArmorPiece("rohirric_gambeson",
            new CustomChestplateItem(ModArmorMaterials.LEATHER_T2, ArmorItem.Type.CHESTPLATE, new Item.Settings()
                    .component(ModDataComponentTypes.DYE_DATA, CustomDyeableDataComponent.withOverlay(true, 15256475))
                    .component(ModDataComponentTypes.CAPE_DATA, CapeDataComponent.newCape(ModCapes.ROHIRRIC_CAPE)),
                    ModFactions.ROHAN));

    public static final Item ROHIRRIC_MILITIA_HELMET = registerArmorPiece("rohirric_militia_helmet",
            new CustomHelmetItem(ModArmorMaterials.IRON_T3, ArmorItem.Type.HELMET, new Item.Settings(),
                    ModFactions.ROHAN));
    public static final Item ROHIRRIC_BRACED_MILITIA_HELMET = registerArmorPiece("rohirric_braced_militia_helmet",
            new CustomHelmetItem(ModArmorMaterials.IRON_T3, ArmorItem.Type.HELMET, new Item.Settings(),
                    ModFactions.ROHAN,
                    new RohirricHelmetArmorAddonModel(RohirricHelmetArmorAddonModel.getTexturedModelData().createModel())));
    public static final Item ROHIRRIC_REINFORCED_MILITIA_HELMET = registerArmorPiece("rohirric_reinforced_militia_helmet",
            new CustomHelmetItem(ModArmorMaterials.IRON_T3, ArmorItem.Type.HELMET, new Item.Settings(),
                    ModFactions.ROHAN));
    public static final Item ROHIRRIC_ORNEMENTED_MILITIA_HELMET = registerArmorPiece("rohirric_ornemented_militia_helmet",
            new CustomHelmetItem(ModArmorMaterials.IRON_T3, ArmorItem.Type.HELMET, new Item.Settings(),
                    ModFactions.ROHAN,
                    new RohirricHelmetArmorAddonModel(RohirricHelmetArmorAddonModel.getTexturedModelData().createModel())));

    public static final Item ROHIRRIC_MAIL_SHIRT = registerDyeableArmorPiece("rohirric_mail_shirt",
            new CustomChestplateItem(ModArmorMaterials.IRON_T3, ArmorItem.Type.CHESTPLATE, new Item.Settings()
                    .component(ModDataComponentTypes.DYE_DATA, CustomDyeableDataComponent.withOverlay(true, 15256475))
                    .component(ModDataComponentTypes.CAPE_DATA, CapeDataComponent.newCape(ModCapes.ROHIRRIC_CAPE)),
                    ModFactions.ROHAN));
    public static final Item ROHIRRIC_MAIL_SHIRT_OPEN = registerDyeableArmorPiece("rohirric_mail_shirt_open",
            new CustomChestplateItem(ModArmorMaterials.IRON_T3, ArmorItem.Type.CHESTPLATE, new Item.Settings()
                    .component(ModDataComponentTypes.DYE_DATA, CustomDyeableDataComponent.withOverlay(true, 15256475))
                    .component(ModDataComponentTypes.CAPE_DATA, CapeDataComponent.newCape(ModCapes.ROHIRRIC_CAPE)),
                    ModFactions.ROHAN));
    public static final Item ROHIRRIC_MAIL_HAUBERK = registerArmorPiece("rohirric_mail_hauberk",
            new CustomChestplateItem(ModArmorMaterials.IRON_T3, ArmorItem.Type.CHESTPLATE, new Item.Settings()
                    .component(ModDataComponentTypes.CAPE_DATA, CapeDataComponent.newCape(ModCapes.ROHIRRIC_CAPE)),
                    ModFactions.ROHAN));

    public static final Item ROHIRRIC_SOLDIER_HELMET = registerArmorPiece("rohirric_soldier_helmet",
            new CustomHelmetItem(ModArmorMaterials.STEEL_T4, ArmorItem.Type.HELMET, new Item.Settings(),
                    ModFactions.ROHAN));
    public static final Item ROHIRRIC_ORNEMENTED_SOLDIER_HELMET = registerArmorPiece("rohirric_ornemented_soldier_helmet",
            new CustomHelmetItem(ModArmorMaterials.STEEL_T4, ArmorItem.Type.HELMET, new Item.Settings(),
                    ModFactions.ROHAN,
                    new RohirricHelmetArmorAddonModel(RohirricHelmetArmorAddonModel.getTexturedModelData().createModel())));

    public static final Item ROHIRRIC_ROYAL_GUARD_HELMET = registerArmorPiece("rohirric_royal_guard_helmet",
            new CustomHelmetItem(ModArmorMaterials.STEEL_T4, ArmorItem.Type.HELMET, new Item.Settings(),
                    ModFactions.ROHAN,
                    new RohirricHelmetArmorAddonModel(RohirricHelmetArmorAddonModel.getTexturedModelData().createModel())));

    public static final Item ROHIRRIC_SCALE_HAUBERK = registerArmorPiece("rohirric_scale_hauberk",
            new CustomChestplateItem(ModArmorMaterials.STEEL_T4, ArmorItem.Type.CHESTPLATE, new Item.Settings(),
                    ModFactions.ROHAN));
    public static final Item ROHIRRIC_ORNEMENTED_SCALE_HAUBERK = registerArmorPiece("rohirric_ornemented_scale_hauberk",
            new CustomChestplateItem(ModArmorMaterials.STEEL_T4, ArmorItem.Type.CHESTPLATE, new Item.Settings(),
                    ModFactions.ROHAN));

    public static final Item ROHIRRIC_SCALE_JACKET = registerArmorPiece("rohirric_scale_jacket",
            new CustomLeggingsItem(ModArmorMaterials.STEEL_T4, ArmorItem.Type.LEGGINGS, new Item.Settings(),
                    ModFactions.ROHAN));

    public static final Item EORLING_MARSHAL_HELMET = registerArmorPiece("eorling_marshal_helmet",
            new CustomHelmetItem(ModArmorMaterials.STEEL_T5, ArmorItem.Type.HELMET, new Item.Settings(),
                    ModFactions.ROHAN,
                    new RohirricHelmetArmorAddonModel(RohirricHelmetArmorAddonModel.getTexturedModelData().createModel())));
    public static final Item EORLING_MARSHAL_CHESTPLATE = registerArmorPiece("eorling_marshal_chestplate",
            new CustomChestplateItem(ModArmorMaterials.STEEL_T5, ArmorItem.Type.CHESTPLATE, new Item.Settings()
                    .component(ModDataComponentTypes.CAPE_DATA, CapeDataComponent.newCape(ModCapes.EORLING_MARHSAL_CAPE)),
                    ModFactions.ROHAN));
    public static final Item EORLING_MARSHAL_LEGGINGS = registerArmorPiece("eorling_marshal_leggings",
            new CustomLeggingsItem(ModArmorMaterials.STEEL_T5, ArmorItem.Type.LEGGINGS, new Item.Settings(),
                    ModFactions.ROHAN));
    public static final Item EORLING_MARSHAL_BOOTS = registerArmorPiece("eorling_marshal_boots",
            new CustomBootsItem(ModArmorMaterials.STEEL_T5, ArmorItem.Type.BOOTS, new Item.Settings(),
                    ModFactions.ROHAN));

    public static final Item HORSE_LORD_HELMET = registerArmorPiece("horse_lord_helmet",
            new CustomHelmetItem(ModArmorMaterials.STEEL_T5, ArmorItem.Type.HELMET, new Item.Settings(),
                    ModFactions.ROHAN,
                    new RohirricHelmetArmorAddonModel(RohirricHelmetArmorAddonModel.getTexturedModelData().createModel())));
    public static final Item HORSE_LORD_CHESTPLATE = registerArmorPiece("horse_lord_chestplate",
            new CustomChestplateItem(ModArmorMaterials.STEEL_T5, ArmorItem.Type.CHESTPLATE, new Item.Settings()
                    .component(ModDataComponentTypes.CAPE_DATA, CapeDataComponent.newCape(ModCapes.HORSE_LORD_CAPE)),
                    ModFactions.ROHAN));
    public static final Item HORSE_LORD_LEGGINGS = registerArmorPiece("horse_lord_leggings",
            new CustomLeggingsItem(ModArmorMaterials.STEEL_T5, ArmorItem.Type.LEGGINGS, new Item.Settings(),
                    ModFactions.ROHAN));
    public static final Item HORSE_LORD_BOOTS = registerArmorPiece("horse_lord_boots",
            new CustomBootsItem(ModArmorMaterials.STEEL_T5, ArmorItem.Type.BOOTS, new Item.Settings(),
                    ModFactions.ROHAN));

    public static final Item ROHIRRIC_SHIELD = registerItem("rohirric_shield",
            new CustomShieldItem(ModShieldTypes.MEDIUM_SHIELD, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".rohan")));
    public static final Item ROHIRRIC_BUCKING_HORSE_SHIELD = registerItem("rohirric_bucking_horse_shield",
            new CustomShieldItem(ModShieldTypes.MEDIUM_SHIELD, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".rohan")));
    public static final Item ROHIRRIC_HORSE_SHIELD = registerItem("rohirric_horse_shield",
            new CustomShieldItem(ModShieldTypes.MEDIUM_SHIELD, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".rohan")));
    public static final Item ROHIRRIC_PLAINSMAN_SHIELD = registerItem("rohirric_plainsman_shield",
            new CustomShieldItem(ModShieldTypes.MEDIUM_SHIELD, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".rohan")));
    public static final Item ROHIRRIC_TWIN_HORSES_SHIELD = registerItem("rohirric_twin_horses_shield",
            new CustomShieldItem(ModShieldTypes.MEDIUM_SHIELD, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".rohan")));
    public static final Item ROHIRRIC_EORLING_SHIELD = registerItem("rohirric_eorling_shield",
            new CustomShieldItem(ModShieldTypes.HEAVY_SHIELD, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".rohan")));
    public static final Item ROHIRRIC_ORNAMENTED_SHIELD = registerItem("rohirric_ornamented_shield",
            new CustomShieldItem(ModShieldTypes.HEAVY_SHIELD, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".rohan")));
    public static final Item ROHIRRIC_ROYAL_GUARD_SHIELD = registerItem("rohirric_royal_guard_shield",
            new CustomShieldItem(ModShieldTypes.HEAVY_SHIELD, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".rohan")));

    public static final Item ROHIRRIC_HORSE_ARMOR = registerGeneratedItem("rohirric_horse_armor",
            new CustomHorseArmorItem(ArmorMaterials.IRON, AnimalArmorItem.Type.EQUESTRIAN, false, new Item.Settings().maxCount(1), Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".rohan")));
    //endregion

    //region DALE
    public static final Item DALISH_ARMING_COAT_BLACK_FUR = registerDyeableArmorPiece("dalish_arming_coat_black_fur",
            new CustomChestplateItem(ModArmorMaterials.LEATHER_T1, ArmorItem.Type.CHESTPLATE, new Item.Settings()
                    .component(ModDataComponentTypes.DYE_DATA, CustomDyeableDataComponent.withOverlay(true, 7296407)),
                    ModFactions.DALE));
    public static final Item DALISH_ARMING_COAT_BROWN_FUR = registerDyeableArmorPiece("dalish_arming_coat_brown_fur",
            new CustomChestplateItem(ModArmorMaterials.LEATHER_T1, ArmorItem.Type.CHESTPLATE, new Item.Settings()
                    .component(ModDataComponentTypes.DYE_DATA, CustomDyeableDataComponent.withOverlay(true, 7296407)),
                    ModFactions.DALE));
    public static final Item DALISH_ARMING_COAT_TAN_FUR = registerDyeableArmorPiece("dalish_arming_coat_tan_fur",
            new CustomChestplateItem(ModArmorMaterials.LEATHER_T1, ArmorItem.Type.CHESTPLATE, new Item.Settings()
                    .component(ModDataComponentTypes.DYE_DATA, CustomDyeableDataComponent.withOverlay(true, 7296407)),
                    ModFactions.DALE));
    public static final Item DALISH_HEYDAY_ARMING_COAT = registerArmorPiece("dalish_heyday_arming_coat",
            new CustomChestplateItem(ModArmorMaterials.LEATHER_T1, ArmorItem.Type.CHESTPLATE, new Item.Settings(),
                    ModFactions.DALE));

    public static final Item DALISH_BOOTS = registerArmorPiece("dalish_boots",
            new CustomBootsItem(ModArmorMaterials.LEATHER_T2, ArmorItem.Type.BOOTS, new Item.Settings(),
                    ModFactions.DALE));

    public static final Item DALISH_HELMET = registerArmorPiece("dalish_helmet",
            new CustomHelmetItem(ModArmorMaterials.IRON_T3, ArmorItem.Type.HELMET, new Item.Settings(),
                    ModFactions.DALE,
                    new DaleTallSpikeHelmetModel(DaleTallSpikeHelmetModel.getTexturedModelData().createModel())));
    public static final Item DALISH_HELMET_BLACK_FUR = registerArmorPiece("dalish_helmet_black_fur",
            new CustomHelmetItem(ModArmorMaterials.IRON_T3, ArmorItem.Type.HELMET, new Item.Settings(),
                    ModFactions.DALE,
                    new DaleTallSpikeHelmetModel(DaleTallSpikeHelmetModel.getTexturedModelData().createModel())));
    public static final Item DALISH_HELMET_BROWN_FUR = registerArmorPiece("dalish_helmet_brown_fur",
            new CustomHelmetItem(ModArmorMaterials.IRON_T3, ArmorItem.Type.HELMET, new Item.Settings(),
                    ModFactions.DALE,
                    new DaleTallSpikeHelmetModel(DaleTallSpikeHelmetModel.getTexturedModelData().createModel())));
    public static final Item DALISH_HELMET_TAN_FUR = registerArmorPiece("dalish_helmet_tan_fur",
            new CustomHelmetItem(ModArmorMaterials.IRON_T3, ArmorItem.Type.HELMET, new Item.Settings(),
                    ModFactions.DALE,
                    new DaleTallSpikeHelmetModel(DaleTallSpikeHelmetModel.getTexturedModelData().createModel())));

    public static final Item DALISH_CHAIN_HAUBERK = registerArmorPiece("dalish_chain_hauberk",
            new CustomChestplateItem(ModArmorMaterials.IRON_T3, ArmorItem.Type.CHESTPLATE, new Item.Settings(),
                    ModFactions.DALE));

    public static final Item DALISH_CHAIN_COAT = registerArmorPiece("dalish_chain_coat",
            new CustomLeggingsItem(ModArmorMaterials.IRON_T3, ArmorItem.Type.LEGGINGS, new Item.Settings(),
                    ModFactions.DALE));

    public static final Item DALISH_BURGONET = registerArmorPiece("dalish_burgonet",
            new CustomHelmetItem(ModArmorMaterials.STEEL_T4, ArmorItem.Type.HELMET, new Item.Settings(),
                    ModFactions.DALE,
                    new DaleHelmetModel(DaleHelmetModel.getTexturedModelData().createModel())));

    public static final Item DALISH_SCALE_HAUBERK = registerArmorPiece("dalish_scale_hauberk",
            new CustomChestplateItem(ModArmorMaterials.STEEL_T4, ArmorItem.Type.CHESTPLATE, new Item.Settings(),
                    ModFactions.DALE));

    public static final Item DALISH_HEYDAY_HELMET = registerArmorPiece("dalish_heyday_helmet",
            new CustomHelmetItem(ModArmorMaterials.STEEL_T4, ArmorItem.Type.HELMET, new Item.Settings(),
                    ModFactions.DALE,
                    new DaleTallSpikeHelmetModel(DaleTallSpikeHelmetModel.getTexturedModelData().createModel())));
    public static final Item DALISH_HEYDAY_CHESTPLATE = registerArmorPiece("dalish_heyday_chestplate",
            new CustomChestplateItem(ModArmorMaterials.STEEL_T4, ArmorItem.Type.CHESTPLATE, new Item.Settings()
                    .component(ModDataComponentTypes.CAPE_DATA, CapeDataComponent.newCape(ModCapes.DALISH_HEYDAY_CAPE)),
                    ModFactions.DALE));
    public static final Item DALISH_HEYDAY_CHAIN_COAT = registerArmorPiece("dalish_heyday_chain_coat",
            new CustomLeggingsItem(ModArmorMaterials.STEEL_T4, ArmorItem.Type.LEGGINGS, new Item.Settings(),
                    ModFactions.DALE));
    public static final Item DALISH_HEYDAY_BOOTS = registerArmorPiece("dalish_heyday_boots",
            new CustomBootsItem(ModArmorMaterials.STEEL_T4, ArmorItem.Type.BOOTS, new Item.Settings(),
                    ModFactions.DALE));

    public static final Item BARDING_SOLDIER_HELMET = registerArmorPiece("barding_soldier_helmet",
            new CustomHelmetItem(ModArmorMaterials.STEEL_T5, ArmorItem.Type.HELMET, new Item.Settings(),
                    ModFactions.DALE,
                    new DaleHelmetModel(DaleHelmetModel.getTexturedModelData().createModel())));
    public static final Item BARDING_SOLDIER_CHESTPLATE = registerArmorPiece("barding_soldier_chestplate",
            new CustomChestplateItem(ModArmorMaterials.STEEL_T5, ArmorItem.Type.CHESTPLATE, new Item.Settings(),
                    ModFactions.DALE));

    public static final Item BARDING_CHAIN_SKIRT = registerArmorPiece("barding_chain_skirt",
            new CustomLeggingsItem(ModArmorMaterials.STEEL_T5, ArmorItem.Type.LEGGINGS, new Item.Settings(),
                    ModFactions.DALE));
    public static final Item BARDING_PLATED_BOOTS = registerArmorPiece("barding_plated_boots",
            new CustomBootsItem(ModArmorMaterials.STEEL_T5, ArmorItem.Type.BOOTS, new Item.Settings(),
                    ModFactions.DALE));

    public static final Item BARDING_SERGEANT_HELMET = registerArmorPiece("barding_sergeant_helmet",
            new CustomHelmetItem(ModArmorMaterials.STEEL_T5, ArmorItem.Type.HELMET, new Item.Settings(),
                    ModFactions.DALE,
                    new DaleHelmetModel(DaleHelmetModel.getTexturedModelData().createModel())));
    public static final Item BARDING_SERGEANT_CHESTPLATE = registerArmorPiece("barding_sergeant_chestplate",
            new CustomChestplateItem(ModArmorMaterials.STEEL_T5, ArmorItem.Type.CHESTPLATE, new Item.Settings()
                    .component(ModDataComponentTypes.CAPE_DATA, CapeDataComponent.newCape(ModCapes.BARDING_SERGEANT_CAPE)),
                    ModFactions.DALE));
    //endregion

    //endregion

    //region DWARVES

    //region GENERIC

    public static final Item DWARVEN_PARTISAN_CHESTPLATE = registerArmorPiece("dwarven_partisan_chestplate",
            new CustomChestplateItem(ModArmorMaterials.LEATHER_T2, ArmorItem.Type.CHESTPLATE, new Item.Settings(),
                    ModFactions.NONE));
    public static final Item DWARVEN_PARTISAN_LEGGINGS = registerArmorPiece("dwarven_partisan_leggings",
            new CustomLeggingsItem(ModArmorMaterials.LEATHER_T2, ArmorItem.Type.LEGGINGS, new Item.Settings(),
                    ModFactions.NONE));

    //endregion

    //region EREBOR

    public static final Item EREBOR_PLATE_HELMET = registerArmorPiece("erebor_plate_helmet",
            new CustomHelmetItem(ModArmorMaterials.DWARVEN_STEEL_T5, ArmorItem.Type.HELMET, new Item.Settings(),
                    ModSubFactions.EREBOR,
                    new EreborHelmetModel(EreborHelmetModel.getTexturedModelData().createModel())));
    public static final Item EREBOR_PLATE_CHESTPLATE = registerArmorPiece("erebor_plate_chestplate",
            new CustomChestplateItem(ModArmorMaterials.DWARVEN_STEEL_T5, ArmorItem.Type.CHESTPLATE, new Item.Settings(),
                    ModSubFactions.EREBOR,
                    new HaltChestplateModel(HaltChestplateModel.getTexturedModelData().createModel())));
    public static final Item EREBOR_PLATE_LEGGINGS = registerArmorPiece("erebor_plate_leggings",
            new CustomLeggingsItem(ModArmorMaterials.DWARVEN_STEEL_T5, ArmorItem.Type.LEGGINGS, new Item.Settings(),
                    ModSubFactions.EREBOR));
    public static final Item EREBOR_PLATE_BOOTS = registerArmorPiece("erebor_plate_boots",
            new CustomBootsItem(ModArmorMaterials.DWARVEN_STEEL_T5, ArmorItem.Type.BOOTS, new Item.Settings(),
                    ModSubFactions.EREBOR));

    public static final Item RAVENHILL_WATCHWARDEN_HELMET = registerArmorPiece("ravenhill_watchwarden_helmet",
            new CustomHelmetItem(ModArmorMaterials.DWARVEN_STEEL_T5, ArmorItem.Type.HELMET, new Item.Settings(),
                    ModSubFactions.EREBOR));
    public static final Item RAVENHILL_WATCHWARDEN_CHESTPLATE = registerArmorPiece("ravenhill_watchwarden_chestplate",
            new CustomChestplateItem(ModArmorMaterials.DWARVEN_STEEL_T5, ArmorItem.Type.CHESTPLATE, new Item.Settings(),
                    ModSubFactions.EREBOR,
                    new HaltChestplateModel(HaltChestplateModel.getTexturedModelData().createModel())));
    public static final Item RAVENHILL_WATCHWARDEN_LEGGINGS = registerArmorPiece("ravenhill_watchwarden_leggings",
            new CustomLeggingsItem(ModArmorMaterials.DWARVEN_STEEL_T5, ArmorItem.Type.LEGGINGS, new Item.Settings(),
                    ModSubFactions.EREBOR));
    public static final Item RAVENHILL_WATCHWARDEN_BOOTS = registerArmorPiece("ravenhill_watchwarden_boots",
            new CustomBootsItem(ModArmorMaterials.DWARVEN_STEEL_T5, ArmorItem.Type.BOOTS, new Item.Settings(),
                    ModSubFactions.EREBOR));

    public static final Item RAVENHILL_SENTINEL_HELMET = registerArmorPiece("ravenhill_sentinel_helmet",
            new CustomHelmetItem(ModArmorMaterials.DWARVEN_STEEL_T5, ArmorItem.Type.HELMET, new Item.Settings(),
                    ModSubFactions.EREBOR,
                    new EreborWingsHelmetModel(EreborWingsHelmetModel.getTexturedModelData().createModel())));
    public static final Item RAVENHILL_SENTINEL_CHESTPLATE = registerArmorPiece("ravenhill_sentinel_chestplate",
            new CustomChestplateItem(ModArmorMaterials.DWARVEN_STEEL_T5, ArmorItem.Type.CHESTPLATE, new Item.Settings()
                    .component(ModDataComponentTypes.CAPE_DATA, CapeDataComponent.newCape(ModCapes.RAVENHILL_SENTINEL_CAPE)),
                    ModSubFactions.EREBOR,
                    new HaltChestplateModel(HaltChestplateModel.getTexturedModelData().createModel())));
    public static final Item RAVENHILL_SENTINEL_LEGGINGS = registerArmorPiece("ravenhill_sentinel_leggings",
            new CustomLeggingsItem(ModArmorMaterials.DWARVEN_STEEL_T5, ArmorItem.Type.LEGGINGS, new Item.Settings(),
                    ModSubFactions.EREBOR));
    public static final Item RAVENHILL_SENTINEL_BOOTS = registerArmorPiece("ravenhill_sentinel_boots",
            new CustomBootsItem(ModArmorMaterials.DWARVEN_STEEL_T5, ArmorItem.Type.BOOTS, new Item.Settings(),
                    ModSubFactions.EREBOR));

    public static final Item EREBOR_GATEWARDEN_HELMET = registerArmorPiece("erebor_gatewarden_helmet",
            new CustomHelmetItem(ModArmorMaterials.DWARVEN_STEEL_T5, ArmorItem.Type.HELMET, new Item.Settings(),
                    ModSubFactions.EREBOR,
                    new EreborHelmetModel(EreborHelmetModel.getTexturedModelData().createModel())));
    public static final Item EREBOR_GATEWARDEN_CHESTPLATE = registerArmorPiece("erebor_gatewarden_chestplate",
            new CustomChestplateItem(ModArmorMaterials.DWARVEN_STEEL_T5, ArmorItem.Type.CHESTPLATE, new Item.Settings()
                    .component(ModDataComponentTypes.CAPE_DATA, CapeDataComponent.newCape(ModCapes.EREBOR_CAPE)),
                    ModSubFactions.EREBOR,
                    new HaltChestplateModel(HaltChestplateModel.getTexturedModelData().createModel())));
    public static final Item EREBOR_GATEWARDEN_LEGGINGS = registerArmorPiece("erebor_gatewarden_leggings",
            new CustomLeggingsItem(ModArmorMaterials.DWARVEN_STEEL_T5, ArmorItem.Type.LEGGINGS, new Item.Settings(),
                    ModSubFactions.EREBOR));
    public static final Item EREBOR_GATEWARDEN_BOOTS = registerArmorPiece("erebor_gatewarden_boots",
            new CustomBootsItem(ModArmorMaterials.DWARVEN_STEEL_T5, ArmorItem.Type.BOOTS, new Item.Settings(),
                    ModSubFactions.EREBOR));

    public static final Item LONGBEARD_SHIELD = registerItem("longbeard_shield",
            new CustomShieldItem(ModShieldTypes.MEDIUM_SHIELD, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".longbeards")));

    //endregion

    //endregion

    //region ELVES
    //region GENERIC

    public static final Item ELVEN_ARMING_COAT = registerDyeableArmorPiece("elven_arming_coat",
            new CustomChestplateItem(ModArmorMaterials.LEATHER_T1, ArmorItem.Type.CHESTPLATE, new Item.Settings()
                    .component(ModDataComponentTypes.DYE_DATA, CustomDyeableDataComponent.withOverlay(true, 4805220)),
                    ModFactions.NONE));

    public static final Item ELVEN_ARMING_SKIRT = registerDyeableArmorPiece("elven_arming_skirt",
            new CustomLeggingsItem(ModArmorMaterials.LEATHER_T1, ArmorItem.Type.LEGGINGS, new Item.Settings()
                    .component(ModDataComponentTypes.DYE_DATA, CustomDyeableDataComponent.withOverlay(true, 4805220)),
                    ModFactions.NONE));
    public static final Item ELVEN_OPEN_ARMING_SKIRT = registerDyeableArmorPiece("elven_open_arming_skirt",
            new CustomLeggingsItem(ModArmorMaterials.LEATHER_T1, ArmorItem.Type.LEGGINGS, new Item.Settings()
                    .component(ModDataComponentTypes.DYE_DATA, CustomDyeableDataComponent.withOverlay(true, 4805220)),
                    ModFactions.NONE));

    public static final Item ELVEN_BOOTS = registerArmorPiece("elven_boots",
            new CustomBootsItem(ModArmorMaterials.LEATHER_T2, ArmorItem.Type.BOOTS, new Item.Settings(),
                    ModFactions.NONE));

    public static final Item ELVEN_CHAIN_COIF = registerArmorPiece("elven_chain_coif",
            new CustomHelmetItem(ModArmorMaterials.IRON_T3, ArmorItem.Type.HELMET, new Item.Settings(),
                    ModFactions.NONE));

    public static final Item ELVEN_CHAIN_HAUBERK = registerArmorPiece("elven_chain_hauberk",
            new CustomChestplateItem(ModArmorMaterials.IRON_T3, ArmorItem.Type.CHESTPLATE, new Item.Settings(),
                    ModFactions.NONE));

    public static final Item ELVEN_CHAIN_SKIRT = registerArmorPiece("elven_chain_skirt",
            new CustomLeggingsItem(ModArmorMaterials.IRON_T3, ArmorItem.Type.LEGGINGS, new Item.Settings(),
                    ModFactions.NONE));
    //endregion

    //region LOTHLORIEN
    public static final Item LORIEN_DIADEM = registerArmorPiece("lorien_diadem",
            new CustomHelmetItem(ModArmorMaterials.LEATHER_T1, ArmorItem.Type.HELMET, new Item.Settings(),
                    ModFactions.LOTHLORIEN,
                    new LorienHelmetArmorAddonModel(LorienHelmetArmorAddonModel.getTexturedModelData().createModel())));

    public static final Item LORIEN_LEATHER_HELMET = registerArmorPiece("lorien_leather_helmet",
            new CustomHelmetItem(ModArmorMaterials.LEATHER_T2, ArmorItem.Type.HELMET, new Item.Settings(),
                    ModFactions.LOTHLORIEN,
                    new LorienHelmetArmorAddonModel(LorienHelmetArmorAddonModel.getTexturedModelData().createModel())));

    public static final Item LORIEN_ARMING_COAT = registerArmorPiece("lorien_arming_coat",
            new CustomChestplateItem(ModArmorMaterials.LEATHER_T2, ArmorItem.Type.CHESTPLATE, new Item.Settings(),
                    ModFactions.LOTHLORIEN));

    public static final Item LORIEN_ARMING_SKIRT = registerArmorPiece("lorien_arming_skirt",
            new CustomLeggingsItem(ModArmorMaterials.LEATHER_T2, ArmorItem.Type.LEGGINGS, new Item.Settings(),
                    ModFactions.LOTHLORIEN));

    public static final Item LORIEN_CHAIN_COIF_DIADEM = registerArmorPiece("lorien_chain_coif_diadem",
            new CustomHelmetItem(ModArmorMaterials.IRON_T3, ArmorItem.Type.HELMET, new Item.Settings(),
                    ModFactions.LOTHLORIEN,
                    new LorienHelmetArmorAddonModel(LorienHelmetArmorAddonModel.getTexturedModelData().createModel())));
    public static final Item LORIEN_SHORT_CHAIN_COIF_DIADEM = registerArmorPiece("lorien_short_chain_coif_diadem",
            new CustomHelmetItem(ModArmorMaterials.IRON_T3, ArmorItem.Type.HELMET, new Item.Settings(),
                    ModFactions.LOTHLORIEN,
                    new LorienHelmetArmorAddonModel(LorienHelmetArmorAddonModel.getTexturedModelData().createModel())));

    public static final Item LORIEN_CHAIN_HAUBERK = registerArmorPiece("lorien_chain_hauberk",
            new CustomChestplateItem(ModArmorMaterials.IRON_T3, ArmorItem.Type.CHESTPLATE, new Item.Settings(),
                    ModFactions.LOTHLORIEN));
    public static final Item LORIEN_MARCHWARDEN_CHAIN_HAUBERK = registerArmorPiece("lorien_marchwarden_chain_hauberk",
            new CustomChestplateItem(ModArmorMaterials.IRON_T3, ArmorItem.Type.CHESTPLATE, new Item.Settings()
                    .component(ModDataComponentTypes.CAPE_DATA, CapeDataComponent.newCape(ModCapes.LORIEN_MARCHWARDEN_CAPE)),
                    ModFactions.LOTHLORIEN));

    public static final Item LORIEN_SOLDIER_HELMET = registerArmorPiece("lorien_soldier_helmet",
            new CustomHelmetItem(ModArmorMaterials.ELVEN_STEEL_T4, ArmorItem.Type.HELMET, new Item.Settings(),
                    ModFactions.LOTHLORIEN,
                    new LorienHelmetArmorAddonModel(LorienHelmetArmorAddonModel.getTexturedModelData().createModel())));

    public static final Item LORIEN_SOLDIER_CHAIN_HAUBERK = registerArmorPiece("lorien_soldier_chain_hauberk",
            new CustomChestplateItem(ModArmorMaterials.ELVEN_STEEL_T4, ArmorItem.Type.CHESTPLATE, new Item.Settings(),
                    ModFactions.LOTHLORIEN));
    public static final Item LORIEN_SOLDIER_SCALE_HAUBERK = registerArmorPiece("lorien_soldier_scale_hauberk",
            new CustomChestplateItem(ModArmorMaterials.ELVEN_STEEL_T4, ArmorItem.Type.CHESTPLATE, new Item.Settings(),
                    ModFactions.LOTHLORIEN));

    public static final Item LORIEN_SCALE_COAT = registerArmorPiece("lorien_scale_coat",
            new CustomLeggingsItem(ModArmorMaterials.ELVEN_STEEL_T4, ArmorItem.Type.LEGGINGS, new Item.Settings(),
                    ModFactions.LOTHLORIEN));

    public static final Item GALADHRIM_HELMET = registerArmorPiece("galadhrim_helmet",
            new CustomHelmetItem(ModArmorMaterials.ELVEN_STEEL_T5, ArmorItem.Type.HELMET, new Item.Settings()
                    .component(ModDataComponentTypes.HOOD_DATA, HoodDataComponent.newHood(ModHoods.GALADHRIM_HOOD)),
                    ModFactions.LOTHLORIEN,
                    new LorienHelmetArmorAddonModel(LorienHelmetArmorAddonModel.getTexturedModelData().createModel())));
    public static final Item GALADHRIM_CHESTPLATE = registerArmorPiece("galadhrim_chestplate",
            new CustomChestplateItem(ModArmorMaterials.ELVEN_STEEL_T5, ArmorItem.Type.CHESTPLATE, new Item.Settings()
                    .component(ModDataComponentTypes.CAPE_DATA, CapeDataComponent.newCape(ModCapes.GALADDHRIM_CAPE)),
                    ModFactions.LOTHLORIEN));
    public static final Item GALADHRIM_LEGGINGS = registerArmorPiece("galadhrim_leggings",
            new CustomLeggingsItem(ModArmorMaterials.ELVEN_STEEL_T5, ArmorItem.Type.LEGGINGS, new Item.Settings(),
                    ModFactions.LOTHLORIEN));
    public static final Item GALADHRIM_BOOTS = registerArmorPiece("galadhrim_boots",
            new CustomBootsItem(ModArmorMaterials.ELVEN_STEEL_T5, ArmorItem.Type.BOOTS, new Item.Settings(),
                    ModFactions.LOTHLORIEN));

    public static final Item GALADHRIM_LORD_HELMET = registerArmorPiece("galadhrim_lord_helmet",
            new CustomHelmetItem(ModArmorMaterials.ELVEN_STEEL_T5, ArmorItem.Type.HELMET, new Item.Settings()
                    .component(ModDataComponentTypes.CAPE_DATA, CapeDataComponent.newCape(ModCapes.GALADHRIM_LORD_CAPE)),
                    ModFactions.LOTHLORIEN,
                    new LorienHelmetArmorAddonModel(LorienHelmetArmorAddonModel.getTexturedModelData().createModel())));
    public static final Item GALADHRIM_LORD_CHESTPLATE = registerArmorPiece("galadhrim_lord_chestplate",
            new CustomChestplateItem(ModArmorMaterials.ELVEN_STEEL_T5, ArmorItem.Type.CHESTPLATE, new Item.Settings()
                    .component(ModDataComponentTypes.CAPE_DATA, CapeDataComponent.newCape(ModCapes.GALADHRIM_LORD_CAPE)),
                    ModFactions.LOTHLORIEN));
    public static final Item GALADHRIM_LORD_LEGGINGS = registerArmorPiece("galadhrim_lord_leggings",
            new CustomLeggingsItem(ModArmorMaterials.ELVEN_STEEL_T5, ArmorItem.Type.LEGGINGS, new Item.Settings(),
                    ModFactions.LOTHLORIEN));
    public static final Item GALADHRIM_LORD_BOOTS = registerArmorPiece("galadhrim_lord_boots",
            new CustomBootsItem(ModArmorMaterials.ELVEN_STEEL_T5, ArmorItem.Type.BOOTS, new Item.Settings(),
                    ModFactions.LOTHLORIEN));

    public static final Item LORIEN_SHIELD = registerItem("lorien_shield",
            new CustomShieldItem(ModShieldTypes.MEDIUM_SHIELD, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".lothlorien")));

    public static final Item LORIEN_HORSE_ARMOR = registerGeneratedItem("lorien_horse_armor",
            new CustomHorseArmorItem(ArmorMaterials.IRON, AnimalArmorItem.Type.EQUESTRIAN, false, new Item.Settings().maxCount(1), Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".lothlorien")));

    //endregion
    //endregion

    //region ORCS

    //region GENERIC
    public static final Item ORC_MAIL_COIF = registerRustyArmorPiece("orc_mail_coif",
            new CustomHelmetItem(ModArmorMaterials.SLAG_T3, ArmorItem.Type.HELMET, new Item.Settings(),
                    ModFactions.NONE));
    public static final Item ORC_MAIL_HAUBERK = registerRustyArmorPiece("orc_mail_hauberk",
            new CustomChestplateItem(ModArmorMaterials.SLAG_T3, ArmorItem.Type.CHESTPLATE, new Item.Settings(),
                    ModFactions.NONE));
    public static final Item ORC_MAIL_COAT = registerRustyArmorPiece("orc_mail_coat",
            new CustomLeggingsItem(ModArmorMaterials.SLAG_T3, ArmorItem.Type.LEGGINGS, new Item.Settings(),
                    ModFactions.NONE));

    public static final Item ORC_SALLET = registerRustyArmorPiece("orc_sallet",
            new CustomHelmetItem(ModArmorMaterials.SLAG_T4, ArmorItem.Type.HELMET, new Item.Settings(),
                    ModFactions.NONE,
                    new SalletHelmetAddonModel(SalletHelmetAddonModel.getTexturedModelData().createModel())));

    public static final Item ORC_GORGET_HAUBERK = registerRustyArmorPiece("orc_gorget_hauberk",
            new CustomChestplateItem(ModArmorMaterials.SLAG_T4, ArmorItem.Type.CHESTPLATE, new Item.Settings(),
                    ModFactions.NONE));

    public static final Item ORC_PLATE_BOOTS = registerRustyArmorPiece("orc_plate_boots",
            new CustomBootsItem(ModArmorMaterials.SLAG_T4, ArmorItem.Type.BOOTS, new Item.Settings(),
                    ModFactions.NONE));
    //endregion

    //region MORDOR
    public static final Item MORDOR_ORC_CHESTPLATE = registerRustyArmorPiece("mordor_orc_chestplate",
            new CustomChestplateItem(ModArmorMaterials.URUK_STEEL_T5, ArmorItem.Type.CHESTPLATE, new Item.Settings(),
                    ModFactions.MORDOR));

    public static final Item BLACK_URUK_PLATE_HELMET = registerArmorPiece("black_uruk_plate_helmet",
            new CustomHelmetItem(ModArmorMaterials.URUK_STEEL_T5, ArmorItem.Type.HELMET, new Item.Settings(),
                    ModFactions.MORDOR));
    public static final Item BLACK_URUK_PLATE_CHESTPLATE = registerArmorPiece("black_uruk_plate_chestplate",
            new CustomChestplateItem(ModArmorMaterials.URUK_STEEL_T5, ArmorItem.Type.CHESTPLATE, new Item.Settings(),
                    ModFactions.MORDOR,
                    new HaltChestplateModel(HaltChestplateModel.getTexturedModelData().createModel())));
    public static final Item BLACK_URUK_PLATE_LEGGINGS = registerArmorPiece("black_uruk_plate_leggings",
            new CustomLeggingsItem(ModArmorMaterials.URUK_STEEL_T5, ArmorItem.Type.LEGGINGS, new Item.Settings(),
                    ModFactions.MORDOR));
    public static final Item BLACK_URUK_PLATE_BOOTS = registerArmorPiece("black_uruk_plate_boots",
            new CustomBootsItem(ModArmorMaterials.URUK_STEEL_T5, ArmorItem.Type.BOOTS, new Item.Settings(),
                    ModFactions.MORDOR));

    public static final Item BLACK_URUK_COMMANDER_HELMET = registerArmorPiece("black_uruk_commander_helmet",
            new CustomHelmetItem(ModArmorMaterials.URUK_STEEL_T5, ArmorItem.Type.HELMET, new Item.Settings(),
                    ModFactions.MORDOR,
                    new HelmetSkullModel(HelmetSkullModel.getTexturedModelData().createModel())));
    public static final Item BLACK_URUK_COMMANDER_CHESTPLATE = registerArmorPiece("black_uruk_commander_chestplate",
            new CustomChestplateItem(ModArmorMaterials.URUK_STEEL_T5, ArmorItem.Type.CHESTPLATE, new Item.Settings(),
                    ModFactions.MORDOR,
                    new HaltChestplateModel(HaltChestplateModel.getTexturedModelData().createModel())));

    public static final Item MORDOR_SHIELD = registerItem("mordor_shield",
            new CustomShieldItem(ModShieldTypes.MEDIUM_SHIELD, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".mordor")));

    public static final Item NAZGUL_CLOAK_HOOD = registerArmorPiece("nazgul_cloak_hood",
            new CustomHelmetItem(ModArmorMaterials.MORGUL_STEEL_T3, ArmorItem.Type.HELMET, new Item.Settings(),
                    ModFactions.MORDOR));
    public static final Item NAZGUL_CLOAK = registerArmorPiece("nazgul_cloak",
            new CustomChestplateItem(ModArmorMaterials.MORGUL_STEEL_T3, ArmorItem.Type.CHESTPLATE, new Item.Settings(),
                    ModFactions.MORDOR));
    public static final Item NAZGUL_PANTS = registerArmorPiece("nazgul_pants",
            new CustomLeggingsItem(ModArmorMaterials.MORGUL_STEEL_T3, ArmorItem.Type.LEGGINGS, new Item.Settings(),
                    ModFactions.MORDOR));
    public static final Item NAZGUL_BOOTS = registerArmorPiece("nazgul_boots",
            new CustomBootsItem(ModArmorMaterials.MORGUL_STEEL_T3, ArmorItem.Type.BOOTS, new Item.Settings(),
                    ModFactions.MORDOR));

    //endregion

    //region ISENGARD
    public static final Item URUK_HAI_LEATHER_SCOUT_CAP = registerArmorPiece("uruk_hai_leather_scout_cap",
            new CustomHelmetItem(ModArmorMaterials.LEATHER_T2, ArmorItem.Type.HELMET, new Item.Settings(),
                    ModFactions.ISENGARD,
                    new IsenUrukHelmetModel(IsenUrukHelmetModel.getTexturedModelData().createModel())));

    public static final Item URUK_HAI_PLATE_HELMET = registerArmorPiece("uruk_hai_plate_helmet",
            new CustomHelmetItem(ModArmorMaterials.URUK_STEEL_T5, ArmorItem.Type.HELMET, new Item.Settings(),
                    ModFactions.ISENGARD,
                    new IsenUrukHelmetModel(IsenUrukHelmetModel.getTexturedModelData().createModel())));
    public static final Item URUK_HAI_PLATE_PAINTED_HELMET = registerArmorPiece("uruk_hai_plate_painted_helmet",
            new CustomHelmetItem(ModArmorMaterials.URUK_STEEL_T5, ArmorItem.Type.HELMET, new Item.Settings(),
                    ModFactions.ISENGARD,
                    new IsenUrukHelmetModel(IsenUrukHelmetModel.getTexturedModelData().createModel())));
    public static final Item URUK_HAI_SAPPER_HELMET = registerArmorPiece("uruk_hai_sapper_helmet",
            new CustomHelmetItem(ModArmorMaterials.URUK_STEEL_T5, ArmorItem.Type.HELMET, new Item.Settings(),
                    ModFactions.ISENGARD,
                    new IsenUrukHelmetModel(IsenUrukHelmetModel.getTexturedModelData().createModel())));
    public static final Item URUK_HAI_SAPPER_PAINTED_HELMET = registerArmorPiece("uruk_hai_sapper_painted_helmet",
            new CustomHelmetItem(ModArmorMaterials.URUK_STEEL_T5, ArmorItem.Type.HELMET, new Item.Settings(),
                    ModFactions.ISENGARD,
                    new IsenUrukHelmetModel(IsenUrukHelmetModel.getTexturedModelData().createModel())));
    public static final Item URUK_HAI_BERSERKER_HELMET = registerArmorPiece("uruk_hai_berserker_helmet",
            new CustomHelmetItem(ModArmorMaterials.URUK_STEEL_T5, ArmorItem.Type.HELMET, new Item.Settings(),
                    ModFactions.ISENGARD,
                    new IsenUrukHelmetModel(IsenUrukHelmetModel.getTexturedModelData().createModel())));
    public static final Item URUK_HAI_BERSERKER_PAINTED_HELMET = registerArmorPiece("uruk_hai_berserker_painted_helmet",
            new CustomHelmetItem(ModArmorMaterials.URUK_STEEL_T5, ArmorItem.Type.HELMET, new Item.Settings(),
                    ModFactions.ISENGARD,
                    new IsenUrukHelmetModel(IsenUrukHelmetModel.getTexturedModelData().createModel())));
    public static final Item URUK_HAI_COMMANDER_HELMET = registerArmorPiece("uruk_hai_commander_helmet",
            new CustomHelmetItem(ModArmorMaterials.URUK_STEEL_T5, ArmorItem.Type.HELMET, new Item.Settings(),
                    ModFactions.ISENGARD,
                    new IsenUrukHelmetModel(IsenUrukHelmetModel.getTexturedModelData().createModel())));
    public static final Item URUK_HAI_COMMANDER_PAINTED_HELMET = registerArmorPiece("uruk_hai_commander_painted_helmet",
            new CustomHelmetItem(ModArmorMaterials.URUK_STEEL_T5, ArmorItem.Type.HELMET, new Item.Settings(),
                    ModFactions.ISENGARD,
                    new IsenUrukHelmetModel(IsenUrukHelmetModel.getTexturedModelData().createModel())));
    public static final Item URUK_HAI_PLATE_CHESTPLATE = registerArmorPiece("uruk_hai_plate_chestplate",
            new CustomChestplateItem(ModArmorMaterials.URUK_STEEL_T5, ArmorItem.Type.CHESTPLATE, new Item.Settings(),
                    ModFactions.ISENGARD,
                    new HaltChestplateModel(HaltChestplateModel.getTexturedModelData().createModel())));
    public static final Item URUK_HAI_PLATE_LEGGINGS = registerArmorPiece("uruk_hai_plate_leggings",
            new CustomLeggingsItem(ModArmorMaterials.URUK_STEEL_T5, ArmorItem.Type.LEGGINGS, new Item.Settings(),
                    ModFactions.ISENGARD));
    public static final Item URUK_HAI_PLATE_BOOTS = registerArmorPiece("uruk_hai_plate_boots",
            new CustomBootsItem(ModArmorMaterials.URUK_STEEL_T5, ArmorItem.Type.BOOTS, new Item.Settings(),
                    ModFactions.ISENGARD));

    //endregion

    //region MISTY MOUNTAINS ORCS

    public static final Item MISTY_MOUNTAINS_SHIELD = registerItem("misty_mountains_shield",
            new CustomShieldItem(ModShieldTypes.MEDIUM_SHIELD, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".misty_orcs")));

    /*public static final Item STEEL_TROLL_ARMOR = registerGeneratedItem("steel_troll_armor",
            new TrollArmorItem(10, "steel", new Item.Settings().maxCount(1)));*/

    //endregion

    //endregion

    private static Item registerItem(String name, Item item) {
        ModItemGroups.EQUIPMENT_CONTENTS.add(item.getDefaultStack());
        return Registry.register(Registries.ITEM, Identifier.of(MiddleEarth.MOD_ID, name), item);
    }

    private static Item registerGeneratedItem(String name, Item item) {
        ModItemGroups.EQUIPMENT_CONTENTS.add(item.getDefaultStack());
        SimpleItemModel.items.add(item);
        return Registry.register(Registries.ITEM, Identifier.of(MiddleEarth.MOD_ID, name), item);
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
        return Registry.register(Registries.ITEM, Identifier.of(MiddleEarth.MOD_ID, name), item);
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
        return Registry.register(Registries.ITEM, Identifier.of(MiddleEarth.MOD_ID, name), item);
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
        return Registry.register(Registries.ITEM, Identifier.of(MiddleEarth.MOD_ID, name), item);
    }

    public static void registerModItems() {
        LoggerUtil.getInstance().logDebugMsg("Registering Mod Equipment Items for " + MiddleEarth.MOD_ID);
    }
}
