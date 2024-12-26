package net.jukoz.me.item;

import net.jukoz.me.MiddleEarth;
import net.jukoz.me.datageneration.content.models.SimpleBigItemModel;
import net.jukoz.me.datageneration.content.models.SimpleBowItemModel;
import net.jukoz.me.datageneration.content.models.SimpleHandheldItemModel;
import net.jukoz.me.datageneration.content.models.SimpleSpearModel;
import net.jukoz.me.item.items.HeldBannerItem;
import net.jukoz.me.item.items.shields.ArtefactCustomShieldItem;
import net.jukoz.me.item.items.shields.CustomBannerShieldItem;
import net.jukoz.me.item.items.shields.CustomShieldItem;
import net.jukoz.me.item.items.shields.CustomSiegeShieldItem;
import net.jukoz.me.item.items.weapons.*;
import net.jukoz.me.item.items.weapons.artefacts.*;
import net.jukoz.me.item.items.weapons.ranged.CustomBowWeaponItem;
import net.jukoz.me.item.utils.ModItemGroups;
import net.jukoz.me.item.utils.ModShieldTypes;
import net.jukoz.me.item.utils.ModToolMaterials;
import net.jukoz.me.utils.LoggerUtil;
import net.jukoz.me.utils.ModFactions;
import net.jukoz.me.utils.ModSubFactions;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.ToolMaterials;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

public class ModWeaponItems {

    public static List<Item> shields = new ArrayList<>();

    //region GENERIC
    public static final Item JADE_SWORD = registerItemWithModel("jade_sword",
            new CustomSwordWeaponItem(ModToolMaterials.JADE), false);
    
    public static final Item BRONZE_SWORD = registerItemWithModel("bronze_sword",
            new CustomSwordWeaponItem(ModToolMaterials.BRONZE), false);
    public static final Item CRUDE_FALCHION = registerItemWithModel("crude_falchion",
            new CustomSwordWeaponItem(ModToolMaterials.CRUDE), true);
    
    public static final Item STEEL_SWORD = registerItemWithModel("steel_sword",
            new CustomSwordWeaponItem(ModToolMaterials.STEEL), false);
    public static final Item BURZUM_STEEL_SWORD = registerItemWithModel("burzum_steel_sword",
            new CustomSwordWeaponItem(ModToolMaterials.BURZUM_STEEL), false);
    public static final Item EDHEL_STEEL_SWORD = registerItemWithModel("edhel_steel_sword",
            new CustomSwordWeaponItem(ModToolMaterials.EDHEL_STEEL), false);
    public static final Item KHAZAD_STEEL_SWORD = registerItemWithModel("khazad_steel_sword",
            new CustomSwordWeaponItem(ModToolMaterials.KHAZAD_STEEL), false);

    public static final Item WOODEN_DAGGER = registerItemWithModel("wooden_dagger",
            new CustomDaggerWeaponItem(ToolMaterials.WOOD), false);
    public static final Item STONE_DAGGER = registerItemWithModel("stone_dagger",
            new CustomDaggerWeaponItem(ToolMaterials.STONE), false);
    public static final Item BRONZE_DAGGER = registerItemWithModel("bronze_dagger",
            new CustomDaggerWeaponItem(ModToolMaterials.BRONZE), false);
    public static final Item CRUDE_DAGGER = registerItemWithModel("crude_dagger",
            new CustomDaggerWeaponItem(ModToolMaterials.CRUDE), false);
    public static final Item IRON_DAGGER = registerItemWithModel("iron_dagger",
            new CustomDaggerWeaponItem(ToolMaterials.IRON), false);

    public static final Item GOLDEN_DAGGER = registerItemWithModel("golden_dagger",
            new CustomDaggerWeaponItem(ToolMaterials.GOLD), false);
    public static final Item DIAMOND_DAGGER = registerItemWithModel("diamond_dagger",
            new CustomDaggerWeaponItem(ToolMaterials.DIAMOND), false);
    public static final Item NETHERITE_DAGGER = registerItemWithModel("netherite_dagger",
            new CustomDaggerWeaponItem(ToolMaterials.NETHERITE), false);

    public static final Item CRUDE_LONGBLADE = registerItemWithModel("crude_longblade",
            new CustomLongswordWeaponItem(ModToolMaterials.CRUDE), true);

    public static final Item WOODEN_SPEAR = registerItemWithSpearModel("wooden_spear",
            new CustomSpearWeaponItem(ToolMaterials.WOOD));
    public static final Item STONE_SPEAR = registerItemWithSpearModel("stone_spear",
            new CustomSpearWeaponItem(ToolMaterials.STONE));
    public static final Item JADE_SPEAR = registerItemWithSpearModel("jade_spear",
            new CustomSpearWeaponItem(ModToolMaterials.JADE));

    public static final Item BRONZE_SPEAR = registerItemWithSpearModel("bronze_spear",
            new CustomSpearWeaponItem(ModToolMaterials.BRONZE));
    public static final Item CRUDE_SPEAR = registerItemWithSpearModel("crude_spear",
            new CustomSpearWeaponItem(ModToolMaterials.CRUDE));

    public static final Item IRON_SPEAR = registerItemWithSpearModel("iron_spear",
            new CustomSpearWeaponItem(ToolMaterials.IRON));

    public static final Item STEEL_SPEAR = registerItemWithSpearModel("steel_spear",
            new CustomSpearWeaponItem(ModToolMaterials.STEEL));
    public static final Item BURZUM_STEEL_SPEAR = registerItemWithSpearModel("burzum_steel_spear",
            new CustomSpearWeaponItem(ModToolMaterials.BURZUM_STEEL));
    public static final Item EDHEL_STEEL_SPEAR = registerItemWithSpearModel("edhel_steel_spear",
            new CustomSpearWeaponItem(ModToolMaterials.EDHEL_STEEL));
    public static final Item KHAZAD_STEEL_SPEAR = registerItemWithSpearModel("khazad_steel_spear",
            new CustomSpearWeaponItem(ModToolMaterials.KHAZAD_STEEL));
    
    public static final Item GOLDEN_SPEAR = registerItemWithSpearModel("golden_spear",
            new CustomSpearWeaponItem(ToolMaterials.GOLD));
    public static final Item DIAMOND_SPEAR = registerItemWithSpearModel("diamond_spear",
            new CustomSpearWeaponItem(ToolMaterials.DIAMOND));
    public static final Item NETHERITE_SPEAR = registerItemWithSpearModel("netherite_spear",
            new CustomSpearWeaponItem(ToolMaterials.NETHERITE));

    public static final Item ROUND_SHIELD = registerShield("round_shield",
            new CustomBannerShieldItem(ModShieldTypes.MEDIUM_SHIELD, ModFactions.NONE));
    public static final Item HEATER_SHIELD = registerShield("heater_shield",
            new CustomBannerShieldItem(ModShieldTypes.MEDIUM_SHIELD, ModFactions.NONE));
    public static final Item KITE_SHIELD = registerShield("kite_shield",
            new CustomBannerShieldItem(ModShieldTypes.MEDIUM_SHIELD, ModFactions.NONE));
    //endregion

    //region MEN

    //region GONDOR
    public static final Item GONDORIAN_SWORD = registerItemWithModel("gondorian_sword",
            new CustomSwordWeaponItem(ModToolMaterials.STEEL, ModFactions.GONDOR), true);
    public static final Item GONDORIAN_NOBLE_SWORD = registerItemWithModel("gondorian_noble_sword",
            new CustomSwordWeaponItem(ModToolMaterials.NOBLE_STEEL, ModFactions.GONDOR), true);
    public static final Item GONDORIAN_LONGSWORD = registerItemWithModel("gondorian_longsword",
            new CustomLongswordWeaponItem(ModToolMaterials.STEEL, ModFactions.GONDOR), true);
    public static final Item GONDORIAN_NOBLE_LONGSWORD = registerItemWithModel("gondorian_noble_longsword",
            new CustomLongswordWeaponItem(ModToolMaterials.NOBLE_STEEL, ModFactions.GONDOR), true);
    public static final Item GONDORIAN_DAGGER = registerItemWithModel("gondorian_dagger",
            new CustomDaggerWeaponItem(ModToolMaterials.STEEL, ModFactions.GONDOR), false);
    public static final Item GONDORIAN_NOBLE_DAGGER = registerItemWithModel("gondorian_noble_dagger",
            new CustomDaggerWeaponItem(ModToolMaterials.NOBLE_STEEL, ModFactions.GONDOR), false);
    public static final Item GONDORIAN_AXE = registerItemWithModel("gondorian_axe",
            new CustomAxeWeaponItem(ModToolMaterials.STEEL, ModFactions.GONDOR), true);
    public static final Item GONDORIAN_NOBLE_AXE = registerItemWithModel("gondorian_noble_axe",
            new CustomAxeWeaponItem(ModToolMaterials.NOBLE_STEEL, ModFactions.GONDOR), true);
    public static final Item GONDORIAN_SPEAR = registerItemWithSpearModel("gondorian_spear",
            new CustomSpearWeaponItem(ModToolMaterials.STEEL, ModFactions.GONDOR));
    public static final Item GONDORIAN_FOUNTAIN_GUARD_SPEAR = registerItemWithSpearModel("gondorian_fountain_guard_spear",
            new CustomSpearWeaponItem(ModToolMaterials.NOBLE_STEEL, ModFactions.GONDOR));
    public static final Item GONDORIAN_NOBLE_SPEAR = registerItemWithSpearModel("gondorian_noble_spear",
            new CustomSpearWeaponItem(ModToolMaterials.NOBLE_STEEL, ModFactions.GONDOR));

    public static final Item GONDORIAN_BOW = registerBowItem("gondorian_bow",
            new CustomBowWeaponItem(ModFactions.GONDOR, new Item.Settings().maxDamage(640)));

    public static final Item GONDORIAN_SHIELD = registerShield("gondorian_shield",
            new CustomShieldItem(ModShieldTypes.MEDIUM_SHIELD, ModFactions.GONDOR));
    public static final Item GONDORIAN_TOWER_SHIELD = registerShield("gondorian_tower_shield",
            new CustomShieldItem(ModShieldTypes.MEDIUM_SHIELD, ModFactions.GONDOR));
    public static final Item GONDORIAN_KINGS_GUARD_TOWER_SHIELD = registerShield("gondorian_kings_guard_tower_shield",
            new CustomShieldItem(ModShieldTypes.MEDIUM_SHIELD, ModFactions.GONDOR));
    public static final Item LAST_ALLIANCE_HEILOOM_TOWER_SHIELD = registerShield("last_alliance_heirloom_tower_shield",
            new CustomShieldItem(ModShieldTypes.MEDIUM_SHIELD, ModFactions.GONDOR));
    public static final Item GONDORIAN_HERO_SHIELD = registerShield("gondorian_hero_shield",
            new CustomShieldItem(ModShieldTypes.MEDIUM_SHIELD, ModFactions.GONDOR));
    public static final Item GONDORIAN_KNIGHT_SHIELD = registerShield("gondorian_knight_shield",
            new CustomShieldItem(ModShieldTypes.HEAVY_SHIELD, ModFactions.GONDOR));
    public static final Item GONDORIAN_ORNAMENTED_KNIGHT_SHIELD = registerShield("gondorian_ornamented_knight_shield",
            new CustomShieldItem(ModShieldTypes.HEAVY_SHIELD, ModFactions.GONDOR));
    //endregion

    //region ROHAN
    public static final Item ROHIRRIC_SWORD = registerItemWithModel("rohirric_sword",
            new CustomSwordWeaponItem(ModToolMaterials.STEEL, ModFactions.ROHAN), true);
    public static final Item ROHIRRIC_NOBLE_SWORD = registerItemWithModel("rohirric_noble_sword",
            new CustomSwordWeaponItem(ModToolMaterials.NOBLE_STEEL, ModFactions.ROHAN), true);
    public static final Item ROHIRRIC_LONGSWORD = registerItemWithModel("rohirric_longsword",
            new CustomLongswordWeaponItem(ModToolMaterials.STEEL, ModFactions.ROHAN), true);
    public static final Item ROHIRRIC_NOBLE_LONGSWORD = registerItemWithModel("rohirric_noble_longsword",
            new CustomLongswordWeaponItem(ModToolMaterials.NOBLE_STEEL, ModFactions.ROHAN), true);
    public static final Item ROHIRRIC_DAGGER = registerItemWithModel("rohirric_dagger",
            new CustomDaggerWeaponItem(ModToolMaterials.STEEL, ModFactions.ROHAN), false);
    public static final Item ROHIRRIC_NOBLE_DAGGER = registerItemWithModel("rohirric_noble_dagger",
            new CustomDaggerWeaponItem(ModToolMaterials.NOBLE_STEEL, ModFactions.ROHAN), false);
    public static final Item ROHIRRIC_AXE = registerItemWithModel("rohirric_axe",
            new CustomAxeWeaponItem(ModToolMaterials.STEEL, ModFactions.ROHAN), true);
    public static final Item ROHIRRIC_NOBLE_AXE = registerItemWithModel("rohirric_noble_axe",
            new CustomAxeWeaponItem(ModToolMaterials.NOBLE_STEEL, ModFactions.ROHAN), true);
    public static final Item ROHIRRIC_SPEAR = registerItemWithSpearModel("rohirric_spear",
            new CustomSpearWeaponItem(ModToolMaterials.STEEL, ModFactions.ROHAN));
    public static final Item ROHIRRIC_NOBLE_SPEAR = registerItemWithSpearModel("rohirric_noble_spear",
            new CustomSpearWeaponItem(ModToolMaterials.NOBLE_STEEL, ModFactions.ROHAN));

    public static final Item ROHIRRIC_BOW = registerBowItem("rohirric_bow",
            new CustomBowWeaponItem(ModFactions.ROHAN, new Item.Settings().maxDamage(640)));

    public static final Item ROHIRRIC_SHIELD = registerShield("rohirric_shield",
            new CustomShieldItem(ModShieldTypes.MEDIUM_SHIELD, ModFactions.ROHAN));
    public static final Item ROHIRRIC_BUCKING_HORSE_SHIELD = registerShield("rohirric_bucking_horse_shield",
            new CustomShieldItem(ModShieldTypes.MEDIUM_SHIELD, ModFactions.ROHAN));
    public static final Item ROHIRRIC_GALLOPING_HORSE_SHIELD = registerShield("rohirric_galloping_horse_shield",
            new CustomShieldItem(ModShieldTypes.MEDIUM_SHIELD, ModFactions.ROHAN));
    public static final Item ROHIRRIC_HORSE_HEAD_SHIELD = registerShield("rohirric_horse_head_shield",
            new CustomShieldItem(ModShieldTypes.MEDIUM_SHIELD, ModFactions.ROHAN));
    public static final Item ROHIRRIC_PLAINSMAN_SHIELD = registerShield("rohirric_plainsman_shield",
            new CustomShieldItem(ModShieldTypes.MEDIUM_SHIELD, ModFactions.ROHAN));
    public static final Item ROHIRRIC_TWIN_HORSES_SHIELD = registerShield("rohirric_twin_horses_shield",
            new CustomShieldItem(ModShieldTypes.MEDIUM_SHIELD, ModFactions.ROHAN));
    public static final Item ROHIRRIC_EORLING_SHIELD = registerShield("rohirric_eorling_shield",
            new CustomShieldItem(ModShieldTypes.HEAVY_SHIELD, ModFactions.ROHAN));
    public static final Item ROHIRRIC_ORNAMENTED_SHIELD = registerShield("rohirric_ornamented_shield",
            new CustomShieldItem(ModShieldTypes.HEAVY_SHIELD, ModFactions.ROHAN));
    public static final Item ROHIRRIC_ROYAL_GUARD_SHIELD = registerShield("rohirric_royal_guard_shield",
            new CustomShieldItem(ModShieldTypes.HEAVY_SHIELD, ModFactions.ROHAN));
    //endregion

    //region DALE
    public static final Item DALISH_SWORD = registerItemWithModel("dalish_sword",
            new CustomSwordWeaponItem(ModToolMaterials.STEEL, ModFactions.DALE), true);
    public static final Item DALISH_HEYDAY_SWORD = registerItemWithModel("dalish_heyday_sword",
            new CustomSwordWeaponItem(ModToolMaterials.STEEL, ModFactions.DALE), true);
    public static final Item DALISH_NOBLE_SWORD = registerItemWithModel("dalish_noble_sword",
            new CustomSwordWeaponItem(ModToolMaterials.NOBLE_STEEL, ModFactions.DALE), true);
    public static final Item DALISH_LONGSWORD = registerItemWithModel("dalish_longsword",
            new CustomLongswordWeaponItem(ModToolMaterials.STEEL, ModFactions.DALE), true);
    public static final Item DALISH_HEYDAY_LONGSWORD = registerItemWithModel("dalish_heyday_longsword",
            new CustomLongswordWeaponItem(ModToolMaterials.STEEL, ModFactions.DALE), true);
    public static final Item DALISH_HEYDAY_SCIMITAR = registerItemWithModel("dalish_heyday_scimitar",
            new CustomLongswordWeaponItem(ModToolMaterials.STEEL, ModFactions.DALE), true);
    public static final Item DALISH_NOBLE_LONGSWORD = registerItemWithModel("dalish_noble_longsword",
            new CustomLongswordWeaponItem(ModToolMaterials.NOBLE_STEEL, ModFactions.DALE), true);
    public static final Item DALISH_DAGGER = registerItemWithModel("dalish_dagger",
            new CustomDaggerWeaponItem(ModToolMaterials.STEEL, ModFactions.DALE), false);
    public static final Item DALISH_HEYDAY_DAGGER = registerItemWithModel("dalish_heyday_dagger",
            new CustomDaggerWeaponItem(ModToolMaterials.STEEL, ModFactions.DALE), false);
    public static final Item DALISH_NOBLE_DAGGER = registerItemWithModel("dalish_noble_dagger",
            new CustomDaggerWeaponItem(ModToolMaterials.NOBLE_STEEL, ModFactions.DALE), false);
    public static final Item DALISH_AXE = registerItemWithModel("dalish_axe",
            new CustomAxeWeaponItem(ModToolMaterials.STEEL, ModFactions.DALE), true);
    public static final Item DALISH_HEYDAY_AXE = registerItemWithModel("dalish_heyday_axe",
            new CustomAxeWeaponItem(ModToolMaterials.STEEL, ModFactions.DALE), true);
    public static final Item DALISH_NOBLE_AXE = registerItemWithModel("dalish_noble_axe",
            new CustomAxeWeaponItem(ModToolMaterials.NOBLE_STEEL, ModFactions.DALE), true);
    public static final Item DALISH_SPEAR = registerItemWithSpearModel("dalish_spear",
            new CustomSpearWeaponItem(ModToolMaterials.STEEL, ModFactions.DALE));
    public static final Item DALISH_HEYDAY_SPEAR = registerItemWithSpearModel("dalish_heyday_spear",
            new CustomSpearWeaponItem(ModToolMaterials.STEEL, ModFactions.DALE));
    public static final Item DALISH_NOBLE_SPEAR = registerItemWithSpearModel("dalish_noble_spear",
            new CustomSpearWeaponItem(ModToolMaterials.NOBLE_STEEL, ModFactions.DALE));

    public static final Item DALISH_BLUE_OVAL_SHIELD = registerShield("dalish_blue_oval_shield",
            new CustomShieldItem(ModShieldTypes.MEDIUM_SHIELD, ModFactions.DALE));
    public static final Item DALISH_BARDING_OVAL_SHIELD = registerShield("dalish_barding_oval_shield",
            new CustomShieldItem(ModShieldTypes.MEDIUM_SHIELD, ModFactions.DALE));

    public static final Item DALISH_BLUE_BRACED_SHIELD = registerShield("dalish_blue_braced_shield",
            new CustomShieldItem(ModShieldTypes.MEDIUM_SHIELD, ModFactions.DALE));
    public static final Item DALISH_BARDING_BRACED_SHIELD = registerShield("dalish_barding_braced_shield",
            new CustomShieldItem(ModShieldTypes.MEDIUM_SHIELD, ModFactions.DALE));

    public static final Item DALISH_HEAVY_SHIELD = registerShield("dalish_heavy_shield",
            new CustomShieldItem(ModShieldTypes.HEAVY_SHIELD, ModFactions.DALE));
    public static final Item DALISH_BARDING_HEAVY_SHIELD = registerShield("dalish_barding_heavy_shield",
            new CustomShieldItem(ModShieldTypes.HEAVY_SHIELD, ModFactions.DALE));
    public static final Item DALISH_ROYAL_HEAVY_SHIELD = registerShield("dalish_royal_heavy_shield",
            new CustomShieldItem(ModShieldTypes.HEAVY_SHIELD, ModFactions.DALE));
    public static final Item DALISH_ROYAL_ROUND_SHIELD = registerShield("dalish_royal_round_shield",
            new CustomShieldItem(ModShieldTypes.HEAVY_SHIELD, ModFactions.DALE));
    public static final Item DALISH_HEYDAY_ROUND_SHIELD = registerShield("dalish_heyday_round_shield",
            new CustomShieldItem(ModShieldTypes.HEAVY_SHIELD, ModFactions.DALE));
    //endregion

    //endregion

    //region LOTHLORIEN
    public static final Item LORIEN_SWORD = registerItemWithModel("lorien_sword",
            new CustomSwordWeaponItem(ModToolMaterials.EDHEL_STEEL, ModFactions.LOTHLORIEN), true);
    public static final Item LORIEN_NOBLE_SWORD = registerItemWithModel("lorien_noble_sword",
            new CustomSwordWeaponItem(ModToolMaterials.NOBLE_EDHEL_STEEL, ModFactions.LOTHLORIEN), true);
    public static final Item LORIEN_GLAIVE = registerItemWithModel("lorien_glaive",
            new CustomLongswordWeaponItem(ModToolMaterials.EDHEL_STEEL, ModFactions.LOTHLORIEN), true);
    public static final Item LORIEN_NOBLE_GLAIVE = registerItemWithModel("lorien_noble_glaive",
            new CustomLongswordWeaponItem(ModToolMaterials.NOBLE_EDHEL_STEEL, ModFactions.LOTHLORIEN), true);
    public static final Item LORIEN_DAGGER = registerItemWithModel("lorien_dagger",
            new CustomDaggerWeaponItem(ModToolMaterials.EDHEL_STEEL, ModFactions.LOTHLORIEN), false);
    public static final Item LORIEN_NOBLE_DAGGER = registerItemWithModel("lorien_noble_dagger",
            new CustomDaggerWeaponItem(ModToolMaterials.NOBLE_EDHEL_STEEL, ModFactions.LOTHLORIEN), false);
    public static final Item LORIEN_AXE = registerItemWithModel("lorien_axe",
            new CustomAxeWeaponItem(ModToolMaterials.EDHEL_STEEL, ModFactions.LOTHLORIEN), true);
    public static final Item LORIEN_NOBLE_AXE = registerItemWithModel("lorien_noble_axe",
            new CustomAxeWeaponItem(ModToolMaterials.NOBLE_EDHEL_STEEL, ModFactions.LOTHLORIEN), true);
    public static final Item LORIEN_SPEAR = registerItemWithSpearModel("lorien_spear",
            new CustomSpearWeaponItem(ModToolMaterials.EDHEL_STEEL, ModFactions.LOTHLORIEN));
    public static final Item LORIEN_NOBLE_SPEAR = registerItemWithSpearModel("lorien_noble_spear",
            new CustomSpearWeaponItem(ModToolMaterials.NOBLE_EDHEL_STEEL, ModFactions.LOTHLORIEN));
    
    public static final Item LORIEN_BOW = registerBowItem("lorien_bow",
            new CustomBowWeaponItem(ModFactions.LOTHLORIEN, new Item.Settings().maxDamage(640)));

    public static final Item LORIEN_SHIELD = registerShield("lorien_shield",
            new CustomShieldItem(ModShieldTypes.MEDIUM_SHIELD, ModFactions.LOTHLORIEN));
    public static final Item LORIEN_LAURELS_SHIELD = registerShield("lorien_laurels_shield",
            new CustomShieldItem(ModShieldTypes.MEDIUM_SHIELD, ModFactions.LOTHLORIEN));
    public static final Item LORIEN_MALLORN_SHIELD = registerShield("lorien_mallorn_shield",
            new CustomShieldItem(ModShieldTypes.MEDIUM_SHIELD, ModFactions.LOTHLORIEN));
    public static final Item GALADHRIM_SHIELD = registerShield("galadhrim_shield",
            new CustomShieldItem(ModShieldTypes.HEAVY_SHIELD, ModFactions.LOTHLORIEN));
    public static final Item GALADHRIM_LORD_SHIELD = registerShield("galadhrim_lord_shield",
            new CustomShieldItem(ModShieldTypes.HEAVY_SHIELD, ModFactions.LOTHLORIEN));
    //endregion

    //region EREBOR
    public static final Item EREBOR_SWORD = registerItemWithModel("erebor_sword",
            new CustomSwordWeaponItem(ModToolMaterials.KHAZAD_STEEL, ModSubFactions.EREBOR), true);
    public static final Item EREBOR_NOBLE_SWORD = registerItemWithModel("erebor_noble_sword",
            new CustomSwordWeaponItem(ModToolMaterials.NOBLE_KHAZAD_STEEL, ModSubFactions.EREBOR), true);
    public static final Item EREBOR_LONGSWORD = registerItemWithModel("erebor_longsword",
            new CustomLongswordWeaponItem(ModToolMaterials.KHAZAD_STEEL, ModSubFactions.EREBOR), true);
    public static final Item EREBOR_NOBLE_LONGSWORD = registerItemWithModel("erebor_noble_longsword",
            new CustomLongswordWeaponItem(ModToolMaterials.NOBLE_KHAZAD_STEEL, ModSubFactions.EREBOR), true);
    public static final Item EREBOR_DAGGER = registerItemWithModel("erebor_dagger",
            new CustomDaggerWeaponItem(ModToolMaterials.KHAZAD_STEEL, ModSubFactions.EREBOR), false);
    public static final Item EREBOR_NOBLE_DAGGER = registerItemWithModel("erebor_noble_dagger",
            new CustomDaggerWeaponItem(ModToolMaterials.NOBLE_KHAZAD_STEEL, ModSubFactions.EREBOR), false);
    public static final Item EREBOR_AXE = registerItemWithModel("erebor_axe",
            new CustomAxeWeaponItem(ModToolMaterials.KHAZAD_STEEL, ModSubFactions.EREBOR), true);
    public static final Item EREBOR_NOBLE_AXE = registerItemWithModel("erebor_noble_axe",
            new CustomAxeWeaponItem(ModToolMaterials.NOBLE_KHAZAD_STEEL, ModSubFactions.EREBOR), true);
    public static final Item EREBOR_SPEAR = registerItemWithSpearModel("erebor_spear",
            new CustomSpearWeaponItem(ModToolMaterials.KHAZAD_STEEL, ModSubFactions.EREBOR));
    public static final Item EREBOR_NOBLE_SPEAR = registerItemWithSpearModel("erebor_noble_spear",
            new CustomSpearWeaponItem(ModToolMaterials.NOBLE_KHAZAD_STEEL, ModSubFactions.EREBOR));

    public static final Item EREBOR_SHIELD = registerShield("erebor_shield",
            new CustomShieldItem(ModShieldTypes.MEDIUM_SHIELD, ModSubFactions.EREBOR));
    public static final Item EREBOR_CROSS_SHIELD = registerShield("erebor_cross_shield",
            new CustomShieldItem(ModShieldTypes.MEDIUM_SHIELD, ModSubFactions.EREBOR));
    public static final Item EREBOR_PLATED_SHIELD = registerShield("erebor_plated_shield",
            new CustomShieldItem(ModShieldTypes.MEDIUM_SHIELD, ModSubFactions.EREBOR));
    public static final Item EREBOR_ORNAMENTED_SHIELD = registerShield("erebor_ornamented_shield",
            new CustomShieldItem(ModShieldTypes.MEDIUM_SHIELD, ModSubFactions.EREBOR));
    public static final Item EREBOR_REINFORCED_SHIELD = registerShield("erebor_reinforced_shield",
            new CustomShieldItem(ModShieldTypes.MEDIUM_SHIELD, ModSubFactions.EREBOR));

    public static final Item EREBOR_BUCKLER_SHIELD = registerShield("erebor_buckler_shield",
            new CustomShieldItem(ModShieldTypes.HEAVY_SHIELD, ModSubFactions.EREBOR));
    public static final Item EREBOR_TOWER_SHIELD = registerShield("erebor_tower_shield",
            new CustomShieldItem(ModShieldTypes.HEAVY_SHIELD, ModSubFactions.EREBOR));
    public static final Item EREBOR_REINFORCED_TOWER_SHIELD = registerShield("erebor_reinforced_tower_shield",
            new CustomShieldItem(ModShieldTypes.HEAVY_SHIELD, ModSubFactions.EREBOR));
    public static final Item EREBOR_ORNAMENTED_TOWER_SHIELD = registerShield("erebor_ornamented_tower_shield",
            new CustomShieldItem(ModShieldTypes.HEAVY_SHIELD, ModSubFactions.EREBOR));
    public static final Item RAVENHILL_TOWER_SHIELD = registerShield("ravenhill_tower_shield",
            new CustomShieldItem(ModShieldTypes.HEAVY_SHIELD, ModSubFactions.EREBOR));
    public static final Item RAVENHILL_REINFORCED_TOWER_SHIELD = registerShield("ravenhill_reinforced_tower_shield",
            new CustomShieldItem(ModShieldTypes.HEAVY_SHIELD, ModSubFactions.EREBOR));
    public static final Item RAVENHILL_ORNAMENTED_TOWER_SHIELD = registerShield("ravenhill_ornamented_tower_shield",
            new CustomShieldItem(ModShieldTypes.HEAVY_SHIELD, ModSubFactions.EREBOR));
    //endregion

    public static final Item ORC_SWORD = registerItemWithModel("orc_sword",
            new CustomSwordWeaponItem(ModToolMaterials.BURZUM_STEEL), true);
    public static final Item ORC_BROADBLADE = registerItemWithModel("orc_broadblade",
            new CustomLongswordWeaponItem(ModToolMaterials.BURZUM_STEEL), true);
    public static final Item ORC_KNIFE = registerItemWithModel("orc_knife",
            new CustomDaggerWeaponItem(ModToolMaterials.BURZUM_STEEL), false);
    public static final Item ORC_AXE = registerItemWithModel("orc_axe",
            new CustomAxeWeaponItem(ModToolMaterials.BURZUM_STEEL), true);
    public static final Item ORC_SPEAR = registerItemWithSpearModel("orc_spear",
            new CustomSpearWeaponItem(ModToolMaterials.BURZUM_STEEL));
    //region MORDOR
    public static final Item MORDOR_FALCHION = registerItemWithModel("mordor_falchion",
            new CustomSwordWeaponItem(ModToolMaterials.BURZUM_STEEL, ModFactions.MORDOR), true);
    public static final Item MORDOR_ELITE_CLEAVER = registerItemWithModel("mordor_elite_cleaver",
            new CustomSwordWeaponItem(ModToolMaterials.NOBLE_BURZUM_STEEL, ModFactions.MORDOR), true);
    public static final Item MORDOR_SCIMITAR = registerItemWithModel("mordor_scimitar",
            new CustomLongswordWeaponItem(ModToolMaterials.BURZUM_STEEL, ModFactions.MORDOR), true);
    public static final Item MORDOR_ELITE_WARBLADE = registerItemWithModel("mordor_elite_warblade",
            new CustomLongswordWeaponItem(ModToolMaterials.NOBLE_BURZUM_STEEL, ModFactions.MORDOR), true);
    public static final Item MORDOR_DAGGER = registerItemWithModel("mordor_dagger",
            new CustomDaggerWeaponItem(ModToolMaterials.BURZUM_STEEL, ModFactions.MORDOR), false);
    public static final Item MORDOR_ELITE_DAGGER = registerItemWithModel("mordor_elite_dagger",
            new CustomDaggerWeaponItem(ModToolMaterials.NOBLE_BURZUM_STEEL, ModFactions.MORDOR), false);
    public static final Item MORDOR_AXE = registerItemWithModel("mordor_axe",
            new CustomAxeWeaponItem(ModToolMaterials.BURZUM_STEEL, ModFactions.MORDOR), true);
    public static final Item MORDOR_ELITE_AXE = registerItemWithModel("mordor_elite_axe",
            new CustomAxeWeaponItem(ModToolMaterials.NOBLE_BURZUM_STEEL, ModFactions.MORDOR), true);
    public static final Item MORDOR_SPEAR = registerItemWithSpearModel("mordor_spear",
            new CustomSpearWeaponItem(ModToolMaterials.BURZUM_STEEL, ModFactions.MORDOR));
    public static final Item MORDOR_ELITE_SPEAR = registerItemWithSpearModel("mordor_elite_spear",
            new CustomSpearWeaponItem(ModToolMaterials.NOBLE_BURZUM_STEEL, ModFactions.MORDOR));

    public static final Item BLACK_NUMENOREAN_SWORD = registerItemWithModel("black_numenorean_sword",
            new CustomSwordWeaponItem(ModToolMaterials.NOBLE_STEEL, ModSubFactions.MORDOR_BLACK_NUMENOREANS), true);
    public static final Item BLACK_NUMENOREAN_LONGSWORD = registerItemWithModel("black_numenorean_longsword",
            new CustomLongswordWeaponItem(ModToolMaterials.NOBLE_STEEL, ModSubFactions.MORDOR_BLACK_NUMENOREANS), true);
    public static final Item BLACK_NUMENOREAN_DAGGER = registerItemWithModel("black_numenorean_dagger",
            new CustomDaggerWeaponItem(ModToolMaterials.NOBLE_STEEL, ModSubFactions.MORDOR_BLACK_NUMENOREANS), false);
    public static final Item BLACK_NUMENOREAN_AXE = registerItemWithModel("black_numenorean_axe",
            new CustomAxeWeaponItem(ModToolMaterials.NOBLE_STEEL, ModSubFactions.MORDOR_BLACK_NUMENOREANS), true);
    public static final Item BLACK_NUMENOREAN_SPEAR = registerItemWithSpearModel("black_numenorean_spear",
            new CustomSpearWeaponItem(ModToolMaterials.NOBLE_STEEL, ModSubFactions.MORDOR_BLACK_NUMENOREANS));

    public static final Item MORDOR_WOODEN_SHIELD = registerShield("mordor_wooden_shield",
            new CustomShieldItem(ModShieldTypes.LIGHT_SHIELD, ModFactions.MORDOR));
    public static final Item MORDOR_PAINTED_WOODEN_SHIELD = registerShield("mordor_painted_wooden_shield",
            new CustomShieldItem(ModShieldTypes.LIGHT_SHIELD, ModFactions.MORDOR));

    public static final Item MORDOR_ROUND_SHIELD = registerShield("mordor_round_shield",
            new CustomShieldItem(ModShieldTypes.MEDIUM_SHIELD, ModFactions.MORDOR));
    public static final Item MORDOR_BLACK_ROUND_SHIELD = registerShield("mordor_black_round_shield",
            new CustomShieldItem(ModShieldTypes.MEDIUM_SHIELD, ModFactions.MORDOR));
    public static final Item MORDOR_PAINTED_ROUND_SHIELD = registerShield("mordor_painted_round_shield",
            new CustomShieldItem(ModShieldTypes.MEDIUM_SHIELD, ModFactions.MORDOR));

    public static final Item MORDOR_BRACED_SHIELD = registerShield("mordor_braced_shield",
            new CustomShieldItem(ModShieldTypes.MEDIUM_SHIELD, ModFactions.MORDOR));
    public static final Item MORDOR_BLACK_BRACED_SHIELD = registerShield("mordor_black_braced_shield",
            new CustomShieldItem(ModShieldTypes.MEDIUM_SHIELD, ModFactions.MORDOR));
    public static final Item MORDOR_PAINTED_BRACED_SHIELD = registerShield("mordor_painted_braced_shield",
            new CustomShieldItem(ModShieldTypes.MEDIUM_SHIELD, ModFactions.MORDOR));

    public static final Item MORDOR_LARGE_SHIELD = registerShield("mordor_large_shield",
            new CustomShieldItem(ModShieldTypes.MEDIUM_SHIELD, ModFactions.MORDOR));
    public static final Item MORDOR_BLACK_LARGE_SHIELD = registerShield("mordor_black_large_shield",
            new CustomShieldItem(ModShieldTypes.MEDIUM_SHIELD, ModFactions.MORDOR));
    public static final Item MORDOR_PAINTED_LARGE_SHIELD = registerShield("mordor_painted_large_shield",
            new CustomShieldItem(ModShieldTypes.MEDIUM_SHIELD, ModFactions.MORDOR));

    public static final Item GONDORIAN_CONVERTED_SHIELD = registerShield("gondorian_converted_shield",
            new CustomShieldItem(ModShieldTypes.MEDIUM_SHIELD, ModFactions.MORDOR));

    public static final Item MORDOR_HEAVY_SHIELD = registerShield("mordor_heavy_shield",
            new CustomShieldItem(ModShieldTypes.HEAVY_SHIELD, ModFactions.MORDOR));
    public static final Item MORDOR_PAINTED_EAVY_SHIELD = registerShield("mordor_painted_heavy_shield",
            new CustomShieldItem(ModShieldTypes.HEAVY_SHIELD, ModFactions.MORDOR));
    public static final Item BLACK_NUMENOREAN_TOWER_SHIELD = registerShield("black_numenorean_tower_shield",
            new CustomShieldItem(ModShieldTypes.MEDIUM_SHIELD, ModFactions.MORDOR));
    //endregion

    //region ISENGARD
    public static final Item ISENGARD_ORC_CLEAVER = registerItemWithModel("isengard_orc_cleaver",
            new CustomSwordWeaponItem(ModToolMaterials.BURZUM_STEEL, ModFactions.ISENGARD), true);
    public static final Item URUK_HAI_FALCHION = registerItemWithModel("uruk_hai_falchion",
            new CustomSwordWeaponItem(ModToolMaterials.NOBLE_BURZUM_STEEL, ModFactions.ISENGARD), true);
    public static final Item ISENGARD_ORC_WARBLADE = registerItemWithModel("isengard_orc_warblade",
            new CustomLongswordWeaponItem(ModToolMaterials.BURZUM_STEEL, ModFactions.ISENGARD), true);
    public static final Item URUK_HAI_WARBLADE = registerItemWithModel("uruk_hai_warblade",
            new CustomLongswordWeaponItem(ModToolMaterials.NOBLE_BURZUM_STEEL, ModFactions.ISENGARD), true);
    public static final Item ISENGARD_ORC_DAGGER = registerItemWithModel("isengard_orc_dagger",
            new CustomDaggerWeaponItem(ModToolMaterials.BURZUM_STEEL, ModFactions.ISENGARD), false);
    public static final Item URUK_HAI_KNIFE = registerItemWithModel("uruk_hai_knife",
            new CustomDaggerWeaponItem(ModToolMaterials.NOBLE_BURZUM_STEEL, ModFactions.ISENGARD), false);
    public static final Item ISENGARD_ORC_AXE = registerItemWithModel("isengard_orc_axe",
            new CustomAxeWeaponItem(ModToolMaterials.BURZUM_STEEL, ModFactions.ISENGARD), true);
    public static final Item URUK_HAI_AXE = registerItemWithModel("uruk_hai_axe",
            new CustomAxeWeaponItem(ModToolMaterials.NOBLE_BURZUM_STEEL, ModFactions.ISENGARD), true);
    public static final Item ISENGARD_ORC_SPEAR = registerItemWithSpearModel("isengard_orc_spear",
            new CustomSpearWeaponItem(ModToolMaterials.BURZUM_STEEL, ModFactions.ISENGARD));
    public static final Item URUK_HAI_SPEAR = registerItemWithSpearModel("uruk_hai_spear",
            new CustomSpearWeaponItem(ModToolMaterials.NOBLE_BURZUM_STEEL, ModFactions.ISENGARD));

    public static final Item URUK_HAI_HEATER_SHIELD = registerShield("uruk_hai_heater_shield",
            new CustomShieldItem(ModShieldTypes.MEDIUM_SHIELD, ModFactions.ISENGARD));

    public static final Item URUK_HAI_SHIELD = registerShield("uruk_hai_shield",
            new CustomShieldItem(ModShieldTypes.HEAVY_SHIELD, ModFactions.ISENGARD));
    public static final Item URUK_HAI_WHITE_HAND_SHIELD = registerShield("uruk_hai_white_hand_shield",
            new CustomShieldItem(ModShieldTypes.HEAVY_SHIELD, ModFactions.ISENGARD));
    public static final Item URUK_HAI_WHITE_PALMPRINT_SHIELD = registerShield("uruk_hai_white_palmprint_shield",
            new CustomShieldItem(ModShieldTypes.HEAVY_SHIELD, ModFactions.ISENGARD));
    public static final Item URUK_HAI_S_RUNE_SHIELD = registerShield("uruk_hai_s_rune_shield",
            new CustomShieldItem(ModShieldTypes.HEAVY_SHIELD, ModFactions.ISENGARD));

    public static final Item URUK_HAI_SIEGE_SHIELD = registerShield("uruk_hai_siege_shield",
            new CustomSiegeShieldItem(ModShieldTypes.HEAVY_SHIELD, ModFactions.ISENGARD));

    public static final Item MISTY_MOUNTAINS_SHIELD = registerShield("misty_mountains_shield",
            new CustomShieldItem(ModShieldTypes.MEDIUM_SHIELD, ModFactions.MISTY_MOUNTAINS_GOBLINS));
    //endregion

    //region MISTIES

    //region GUNDABAD
    public static final Item GUNDABAD_FALCHION = registerItemWithModel("gundabad_falchion",
            new CustomSwordWeaponItem(ModToolMaterials.BURZUM_STEEL, ModSubFactions.GUNDABAD), true);
    public static final Item GUNDABAD_ELITE_CLEAVER = registerItemWithModel("gundabad_elite_cleaver",
            new CustomSwordWeaponItem(ModToolMaterials.NOBLE_BURZUM_STEEL, ModSubFactions.GUNDABAD), true);
    public static final Item GUNDABAD_WARBLADE = registerItemWithModel("gundabad_warblade",
            new CustomLongswordWeaponItem(ModToolMaterials.BURZUM_STEEL, ModSubFactions.GUNDABAD), true);
    public static final Item GUNDABAD_ELITE_SCIMITAR = registerItemWithModel("gundabad_elite_scimitar",
            new CustomLongswordWeaponItem(ModToolMaterials.NOBLE_BURZUM_STEEL, ModSubFactions.GUNDABAD), true);
    public static final Item GUNDABAD_SHANK = registerItemWithModel("gundabad_shank",
            new CustomDaggerWeaponItem(ModToolMaterials.BURZUM_STEEL, ModSubFactions.GUNDABAD), false);
    public static final Item GUNDABAD_ELITE_DAGGER = registerItemWithModel("gundabad_elite_dagger",
            new CustomDaggerWeaponItem(ModToolMaterials.NOBLE_BURZUM_STEEL, ModSubFactions.GUNDABAD), false);
    public static final Item GUNDABAD_AXE = registerItemWithModel("gundabad_axe",
            new CustomAxeWeaponItem(ModToolMaterials.BURZUM_STEEL, ModSubFactions.GUNDABAD), true);
    public static final Item GUNDABAD_ELITE_BATTLEAXE = registerItemWithModel("gundabad_elite_battleaxe",
            new CustomAxeWeaponItem(ModToolMaterials.NOBLE_BURZUM_STEEL, ModSubFactions.GUNDABAD), true);
    public static final Item GUNDABAD_SPEAR = registerItemWithSpearModel("gundabad_spear",
            new CustomSpearWeaponItem(ModToolMaterials.BURZUM_STEEL, ModSubFactions.GUNDABAD));
    public static final Item GUNDABAD_ELITE_SPEAR = registerItemWithSpearModel("gundabad_elite_spear",
            new CustomSpearWeaponItem(ModToolMaterials.NOBLE_BURZUM_STEEL, ModSubFactions.GUNDABAD));

    public static final Item GUNDABAD_WOODEN_SHIELD = registerShield("gundabad_wooden_shield",
            new CustomShieldItem(ModShieldTypes.LIGHT_SHIELD, ModSubFactions.GUNDABAD));
    public static final Item GUNDABAD_PAINTED_WOODEN_SHIELD = registerShield("gundabad_painted_wooden_shield",
            new CustomShieldItem(ModShieldTypes.LIGHT_SHIELD, ModSubFactions.GUNDABAD));
    public static final Item GUNDABAD_GREAT_EYE_PAINTED_WOODEN_SHIELD = registerShield("gundabad_great_eye_painted_wooden_shield",
            new CustomShieldItem(ModShieldTypes.LIGHT_SHIELD, ModSubFactions.GUNDABAD));
    public static final Item GUNDABAD_PEAKS_PAINTED_WOODEN_SHIELD = registerShield("gundabad_peaks_painted_wooden_shield",
            new CustomShieldItem(ModShieldTypes.LIGHT_SHIELD, ModSubFactions.GUNDABAD));

    public static final Item GUNDABAD_REINFORCED_SHIELD = registerShield("gundabad_reinforced_shield",
            new CustomShieldItem(ModShieldTypes.MEDIUM_SHIELD, ModSubFactions.GUNDABAD));

    public static final Item GUNDABAD_HEAVY_SHIELD = registerShield("gundabad_heavy_shield",
            new CustomShieldItem(ModShieldTypes.HEAVY_SHIELD, ModSubFactions.GUNDABAD));
    //endregion

    //endregion

    //region ARTEFACTS

    public static final Item DAGAMARTH = registerItemWithModel("dagamarth",
            new ArtefactCustomSwordWeaponItem(ModToolMaterials.NOBLE_STEEL), true);
    public static final Item HERUGRIM = registerItemWithModel("herugrim",
            new ArtefactCustomSwordWeaponItem(ModToolMaterials.NOBLE_STEEL), true);

    public static final Item MACE_OF_SAURON = registerItemWithModel("mace_of_sauron",
            new ArtefactCustomAxeWeaponItem(ModToolMaterials.NOBLE_STEEL), true);

    public static final Item ANGUIREL = registerItemWithModel("anguirel",
            new ArtefactCustomLongswordWeaponItem(ModToolMaterials.NOBLE_STEEL), true);
    public static final Item GLAMDRING = registerArtefactNoModel("glamdring",
            new ArtefactCustomGlowingLongswordWeaponItem(ModToolMaterials.NOBLE_STEEL), false, true, true);
    public static final Item LONG_FORGOTTEN_LONGSWORD = registerItemWithModel("long_forgotten_longsword",
            new ArtefactCustomLongswordWeaponItem(ModToolMaterials.NOBLE_STEEL), true);
    public static final Item LONGSWORD_OF_ELDER_KINGS = registerItemWithModel("longsword_of_elder_kings",
            new ArtefactCustomLongswordWeaponItem(ModToolMaterials.NOBLE_STEEL), true);
    public static final Item NARSIL = registerArtefactNoModel("narsil",
            new ArtefactCustomLongswordWeaponItem(ModToolMaterials.NOBLE_STEEL), true, true, false);
    public static final Item NOLDORIN_LONGSWORD = registerItemWithModel("noldorin_longsword",
            new ArtefactCustomLongswordWeaponItem(ModToolMaterials.NOBLE_STEEL), true);
    public static final Item ORCRIST = registerArtefactNoModel("orcrist",
            new ArtefactCustomGlowingLongswordWeaponItem(ModToolMaterials.NOBLE_STEEL), false, true, true);

    public static final Item BARROW_BLADE = registerItemWithModel("barrow_blade",
            new MorgulKnifeItem(ModToolMaterials.NOBLE_STEEL), false);
    public static final Item MORGUL_KNIFE = registerArtefactNoModel("morgul_knife",
            new MorgulKnifeItem(ModToolMaterials.MORGUL_KNIFE), false, false, false);
    public static final Item STING = registerArtefactNoModel("sting",
            new ArtefactCustomGlowingDaggerWeaponItem(ModToolMaterials.NOBLE_STEEL), false, false, true);

    public static final Item AEGLOS = registerItemWithSpearModel("aeglos",
            new ArtefactCustomSpearWeaponItem(ModToolMaterials.NOBLE_STEEL));

    public static final Item ANORTHANN = registerShield("anorthann",
            new ArtefactCustomShieldItem(ModShieldTypes.HEAVY_SHIELD, ModFactions.NONE));
    public static final Item CUTHANN = registerShield("cuthann",
            new ArtefactCustomShieldItem(ModShieldTypes.HEAVY_SHIELD, ModFactions.NONE));
    public static final Item SHIELD_OF_DURINS_GUARD = registerShield("shield_of_durins_guard",
            new ArtefactCustomShieldItem(ModShieldTypes.HEAVY_SHIELD, ModFactions.NONE));
    public static final Item SHIELD_OF_THE_KING_UNDER_THE_MOUNTAIN = registerShield("shield_of_the_king_under_the_mountain",
            new ArtefactCustomShieldItem(ModShieldTypes.HEAVY_SHIELD, ModFactions.NONE));
    //endregion

    public static final Item HELD_BANNER = registerArtefactNoModel("held_banner",
            new HeldBannerItem(new Item.Settings().maxCount(1)), false, false, false);


    private static Item registerItemWithModel(String name, Item item, boolean isDualModel) {
        ModItemGroups.WEAPONS_CONTENTS.add(item.getDefaultStack());
        if(isDualModel) {
            SimpleBigItemModel.items.add(item);
        } else {
            SimpleHandheldItemModel.items.add(item);
        }
        return Items.register(Identifier.of(MiddleEarth.MOD_ID, name), item);
    }

    private static Item registerItemWithSpearModel(String name, Item item) {
        ModItemGroups.WEAPONS_CONTENTS.add(item.getDefaultStack());
        SimpleSpearModel.items.add(item);

        return Items.register(Identifier.of(MiddleEarth.MOD_ID, name), item);
    }

    private static Item registerBowItem(String name, Item item) {
        ModItemGroups.WEAPONS_CONTENTS.add(item.getDefaultStack());
        SimpleBowItemModel.items.add(item);
        return Items.register(Identifier.of(MiddleEarth.MOD_ID, name), item);
    }

    private static Item registerArtefactNoModel(String name, Item item, boolean brokenModel, boolean isDualModel, boolean glowy) {
        ModItemGroups.WEAPONS_CONTENTS.add(item.getDefaultStack());
        if(brokenModel){
            SimpleBigItemModel.artefactsBroken.add(item);
        }
        if (isDualModel){
            SimpleBigItemModel.artefacts.add(item);
        }
        if (glowy){
            SimpleBigItemModel.artefactsGlowing.add(item);
        }
        return Items.register(Identifier.of(MiddleEarth.MOD_ID, name), item);
    }

    private static Item registerShield(String name, Item item) {
        ModItemGroups.WEAPONS_CONTENTS.add(item.getDefaultStack());
        shields.add(item);
        return Registry.register(Registries.ITEM, Identifier.of(MiddleEarth.MOD_ID, name), item);
    }

    public static void registerModItems() {
        LoggerUtil.logDebugMsg("Registering Mod Weapon Items for " + MiddleEarth.MOD_ID);
    }
}
