package net.sevenstars.middleearth.item;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.Item;
import net.minecraft.item.ToolMaterial;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Rarity;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.block.registration.ModBlocks;
import net.sevenstars.middleearth.datageneration.content.TranslationEntries;
import net.sevenstars.middleearth.datageneration.content.models.*;
import net.sevenstars.middleearth.datageneration.content.tags.Bows;
import net.sevenstars.middleearth.datageneration.content.tags.Crossbows;
import net.sevenstars.middleearth.datageneration.content.tags.WeaponEnchants;
import net.sevenstars.middleearth.item.dataComponents.FactionDataComponent;
import net.sevenstars.middleearth.item.items.HeldBannerItem;
import net.sevenstars.middleearth.item.items.shields.ArtefactCustomShieldItem;
import net.sevenstars.middleearth.item.items.shields.CustomBannerShieldItem;
import net.sevenstars.middleearth.item.items.shields.CustomShieldItem;
import net.sevenstars.middleearth.item.items.shields.CustomSiegeShieldItem;
import net.sevenstars.middleearth.item.items.weapons.*;
import net.sevenstars.middleearth.item.items.weapons.artefacts.*;
import net.sevenstars.middleearth.item.items.weapons.ranged.CustomBowWeaponItem;
import net.sevenstars.middleearth.item.items.weapons.ranged.CustomCrossbowWeaponItem;
import net.sevenstars.middleearth.item.items.weapons.ranged.CustomLongbowWeaponItem;
import net.sevenstars.middleearth.item.items.weapons.utils.ArtefactUtils;
import net.sevenstars.middleearth.item.utils.ItemGroupsME;
import net.sevenstars.middleearth.item.utils.RangedWeaponTypesME;
import net.sevenstars.middleearth.item.utils.ShieldTypesME;
import net.sevenstars.middleearth.item.utils.ToolMaterialsME;
import net.sevenstars.middleearth.resources.FactionsME;
import net.sevenstars.middleearth.registries.RegistryAliases;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class WeaponItemsME {

    /**
     * Middle-earth mod Weapon Items registry, weapons, shields
     */

    public static List<Item> spears = new ArrayList<>();
    public static List<Item> shields = new ArrayList<>();

    //region GENERIC
    public static final Item WOODEN_DAGGER = registerItemWithModel("wooden_dagger",
            (settings) -> new CustomDaggerWeaponItem(ToolMaterial.WOOD, settings), new Item.Settings(), false);
    public static final Item WOODEN_SPEAR = registerItemWithSpearModel("wooden_spear",
            (settings) -> new CustomSpearWeaponItem(ToolMaterial.WOOD, settings), new Item.Settings());

    public static final Item STONE_DAGGER = registerItemWithModel("stone_dagger",
            (settings) -> new CustomDaggerWeaponItem(ToolMaterial.STONE, settings), new Item.Settings(), false);
    public static final Item STONE_SPEAR = registerItemWithSpearModel("stone_spear",
            (settings) -> new CustomSpearWeaponItem(ToolMaterial.STONE, settings), new Item.Settings());

    public static final Item BONE_CLEAVER = registerItemWithModel("bone_cleaver",
            (settings) -> new CustomSwordWeaponItem(ToolMaterialsME.BONE, settings), new Item.Settings(), true);
    public static final Item BONE_SCIMITAR = registerItemWithModel("bone_scimitar",
            (settings) -> new CustomLongswordWeaponItem(ToolMaterialsME.BONE, settings), new Item.Settings(), true);
    public static final Item BONE_SHANK = registerItemWithModel("bone_shank",
            (settings) -> new CustomDaggerWeaponItem(ToolMaterialsME.BONE, settings), new Item.Settings(), false);
    public static final Item BONE_AXE = registerItemWithModel("bone_axe",
            (settings) -> new CustomAxeWeaponItem(ToolMaterialsME.BONE, settings), new Item.Settings(), true);
    public static final Item BONE_SPEAR = registerItemWithSpearModel("bone_spear",
            (settings) -> new CustomSpearWeaponItem(ToolMaterialsME.BONE, settings), new Item.Settings());

    public static final Item BRONZE_SWORD = registerItemWithModel("bronze_sword",
            (settings) -> new CustomSwordWeaponItem(ToolMaterialsME.BRONZE, settings), new Item.Settings(), true);
    public static final Item BRONZE_DAGGER = registerItemWithModel("bronze_dagger",
            (settings) -> new CustomDaggerWeaponItem(ToolMaterialsME.BRONZE, settings), new Item.Settings(), false);
    public static final Item BRONZE_SPEAR = registerItemWithSpearModel("bronze_spear",
            (settings) -> new CustomSpearWeaponItem(ToolMaterialsME.BRONZE, settings), new Item.Settings());

    public static final Item CRUDE_FALCHION = registerItemWithModel("crude_falchion",
            (settings) -> new CustomSwordWeaponItem(ToolMaterialsME.CRUDE, settings), new Item.Settings(), true);
    public static final Item CRUDE_LONGBLADE = registerItemWithModel("crude_longblade",
            (settings) -> new CustomLongswordWeaponItem(ToolMaterialsME.CRUDE, settings), new Item.Settings(), true);
    public static final Item CRUDE_DAGGER = registerItemWithModel("crude_dagger",
            (settings) -> new CustomDaggerWeaponItem(ToolMaterialsME.CRUDE, settings), new Item.Settings(), false);
    public static final Item CRUDE_SPEAR = registerItemWithSpearModel("crude_spear",
            (settings) -> new CustomSpearWeaponItem(ToolMaterialsME.CRUDE, settings), new Item.Settings());

    public static final Item IRON_DAGGER = registerItemWithModel("iron_dagger",
            (settings) -> new CustomDaggerWeaponItem(ToolMaterial.IRON, settings), new Item.Settings(), false);
    public static final Item IRON_SPEAR = registerItemWithSpearModel("iron_spear",
            (settings) -> new CustomSpearWeaponItem(ToolMaterial.IRON, settings), new Item.Settings());

    public static final Item GOLDEN_DAGGER = registerItemWithModel("golden_dagger",
            (settings) -> new CustomDaggerWeaponItem(ToolMaterial.GOLD, settings), new Item.Settings(), false);
    public static final Item GOLDEN_SPEAR = registerItemWithSpearModel("golden_spear",
            (settings) -> new CustomSpearWeaponItem(ToolMaterial.GOLD, settings), new Item.Settings());

    public static final Item STEEL_SWORD = registerItemWithModel("steel_sword",
            (settings) -> new CustomSwordWeaponItem(ToolMaterialsME.STEEL, settings), new Item.Settings(), true);
    public static final Item STEEL_SPEAR = registerItemWithSpearModel("steel_spear",
            (settings) -> new CustomSpearWeaponItem(ToolMaterialsME.STEEL, settings), new Item.Settings());

    public static final Item BURZUM_STEEL_SWORD = registerItemWithModel("burzum_steel_sword",
            (settings) -> new CustomSwordWeaponItem(ToolMaterialsME.BURZUM_STEEL, settings), new Item.Settings(), true);
    public static final Item BURZUM_STEEL_SPEAR = registerItemWithSpearModel("burzum_steel_spear",
            (settings) -> new CustomSpearWeaponItem(ToolMaterialsME.BURZUM_STEEL, settings), new Item.Settings());

    public static final Item EDHEL_STEEL_SWORD = registerItemWithModel("edhel_steel_sword",
            (settings) -> new CustomSwordWeaponItem(ToolMaterialsME.EDHEL_STEEL, settings), new Item.Settings(), true);
    public static final Item EDHEL_STEEL_SPEAR = registerItemWithSpearModel("edhel_steel_spear",
            (settings) -> new CustomSpearWeaponItem(ToolMaterialsME.EDHEL_STEEL, settings), new Item.Settings());

    public static final Item KHAZAD_STEEL_SWORD = registerItemWithModel("khazad_steel_sword",
            (settings) -> new CustomSwordWeaponItem(ToolMaterialsME.KHAZAD_STEEL, settings), new Item.Settings(), true);
    public static final Item KHAZAD_STEEL_SPEAR = registerItemWithSpearModel("khazad_steel_spear",
            (settings) -> new CustomSpearWeaponItem(ToolMaterialsME.KHAZAD_STEEL, settings), new Item.Settings());

    public static final Item DIAMOND_DAGGER = registerItemWithModel("diamond_dagger",
            (settings) -> new CustomDaggerWeaponItem(ToolMaterial.DIAMOND, settings), new Item.Settings(), false);
    public static final Item DIAMOND_SPEAR = registerItemWithSpearModel("diamond_spear",
            (settings) -> new CustomSpearWeaponItem(ToolMaterial.DIAMOND, settings), new Item.Settings());

    public static final Item NETHERITE_DAGGER = registerItemWithModel("netherite_dagger",
            (settings) -> new CustomDaggerWeaponItem(ToolMaterial.NETHERITE, settings), new Item.Settings(), false);
    public static final Item NETHERITE_SPEAR = registerItemWithSpearModel("netherite_spear",
            (settings) -> new CustomSpearWeaponItem(ToolMaterial.NETHERITE, settings), new Item.Settings());

    public static final Item ROUND_SHIELD = registerBannerShield("round_shield",
            (settings) -> new CustomBannerShieldItem(ShieldTypesME.MEDIUM_SHIELD, settings), new Item.Settings());
    public static final Item HEATER_SHIELD = registerBannerShield("heater_shield",
            (settings) -> new CustomBannerShieldItem(ShieldTypesME.MEDIUM_SHIELD, settings), new Item.Settings());
    public static final Item KITE_SHIELD = registerBannerShield("kite_shield",
            (settings) -> new CustomBannerShieldItem(ShieldTypesME.MEDIUM_SHIELD, settings), new Item.Settings());
    //endregion

    //region MEN

    //region GONDOR
    public static final Item GONDORIAN_SWORD = registerItemWithModel("gondorian_sword",
            (settings) -> new CustomSwordWeaponItem(ToolMaterialsME.STEEL, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.GONDOR.getValue())), true);
    public static final Item GONDORIAN_LONGSWORD = registerItemWithModel("gondorian_longsword",
            (settings) -> new CustomLongswordWeaponItem(ToolMaterialsME.STEEL, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.GONDOR.getValue())), true);
    public static final Item GONDORIAN_DAGGER = registerItemWithModel("gondorian_dagger",
            (settings) -> new CustomDaggerWeaponItem(ToolMaterialsME.STEEL, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.GONDOR.getValue())), false);
    public static final Item GONDORIAN_AXE = registerItemWithModel("gondorian_axe",
            (settings) -> new CustomAxeWeaponItem(ToolMaterialsME.STEEL, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.GONDOR.getValue())), true);
    public static final Item GONDORIAN_SPEAR = registerItemWithSpearModel("gondorian_spear",
            (settings) -> new CustomSpearWeaponItem(ToolMaterialsME.STEEL, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.GONDOR.getValue())));

    public static final Item GONDORIAN_BOW = registerBowItem("gondorian_bow",
            (settings) -> new CustomBowWeaponItem(RangedWeaponTypesME.BOW, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.GONDOR.getValue())));
    public static final Item GONDORIAN_LONGBOW = registerBigBowItem("gondorian_longbow",
            (settings) -> new CustomLongbowWeaponItem(RangedWeaponTypesME.LONGBOW, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.GONDOR.getValue())));

    public static final Item GONDORIAN_NOBLE_SWORD = registerItemWithModel("gondorian_noble_sword",
            (settings) -> new CustomSwordWeaponItem(ToolMaterialsME.NOBLE_STEEL,settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.GONDOR.getValue())), true);
    public static final Item GONDORIAN_NOBLE_LONGSWORD = registerItemWithModel("gondorian_noble_longsword",
            (settings) -> new CustomLongswordWeaponItem(ToolMaterialsME.NOBLE_STEEL, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.GONDOR.getValue())), true);
    public static final Item GONDORIAN_NOBLE_DAGGER = registerItemWithModel("gondorian_noble_dagger",
            (settings) -> new CustomDaggerWeaponItem(ToolMaterialsME.NOBLE_STEEL, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.GONDOR.getValue())), false);
    public static final Item GONDORIAN_NOBLE_AXE = registerItemWithModel("gondorian_noble_axe",
            (settings) -> new CustomAxeWeaponItem(ToolMaterialsME.NOBLE_STEEL, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.GONDOR.getValue())), true);
    public static final Item GONDORIAN_NOBLE_SPEAR = registerItemWithSpearModel("gondorian_noble_spear",
            (settings) -> new CustomSpearWeaponItem(ToolMaterialsME.NOBLE_STEEL, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.GONDOR.getValue())));

    public static final Item GONDORIAN_NOBLE_LONGBOW = registerBigBowItem("gondorian_noble_longbow",
            (settings) -> new CustomLongbowWeaponItem(RangedWeaponTypesME.NOBLE_LONGBOW, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.GONDOR.getValue())));

    public static final Item GONDORIAN_FOUNTAIN_GUARD_SPEAR = registerItemWithSpearModel("gondorian_fountain_guard_spear",
            (settings) -> new CustomSpearWeaponItem(ToolMaterialsME.NOBLE_STEEL, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.GONDOR.getValue())));

    public static final Item GONDORIAN_SHIELD = registerShield("gondorian_shield",
            (settings) -> new CustomShieldItem(ShieldTypesME.MEDIUM_SHIELD,  settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.GONDOR.getValue())));
    public static final Item GONDORIAN_TOWER_SHIELD = registerShield("gondorian_tower_shield",
            (settings) -> new CustomShieldItem(ShieldTypesME.MEDIUM_SHIELD,  settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.GONDOR.getValue())));
    public static final Item GONDORIAN_KINGS_GUARD_TOWER_SHIELD = registerShield("gondorian_kings_guard_tower_shield",
            (settings) -> new CustomShieldItem(ShieldTypesME.MEDIUM_SHIELD,  settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.GONDOR.getValue())));
    public static final Item LAST_ALLIANCE_HEIRLOOM_TOWER_SHIELD = registerShield("last_alliance_heirloom_tower_shield",
            (settings) -> new CustomShieldItem(ShieldTypesME.MEDIUM_SHIELD,  settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.GONDOR.getValue())));
    public static final Item GONDORIAN_HERO_SHIELD = registerShield("gondorian_hero_shield",
            (settings) -> new CustomShieldItem(ShieldTypesME.MEDIUM_SHIELD,  settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.GONDOR.getValue())));
    public static final Item GONDORIAN_KNIGHT_SHIELD = registerShield("gondorian_knight_shield",
            (settings) -> new CustomShieldItem(ShieldTypesME.HEAVY_SHIELD,  settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.GONDOR.getValue())));
    public static final Item GONDORIAN_ORNAMENTED_KNIGHT_SHIELD = registerShield("gondorian_ornamented_knight_shield",
            (settings) -> new CustomShieldItem(ShieldTypesME.HEAVY_SHIELD,  settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.GONDOR.getValue())));
    //endregion

    //region ROHAN
    public static final Item ROHIRRIC_SWORD = registerItemWithModel("rohirric_sword",
            (settings) -> new CustomSwordWeaponItem(ToolMaterialsME.STEEL, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.ROHAN.getValue())), true);
    public static final Item ROHIRRIC_LONGSWORD = registerItemWithModel("rohirric_longsword",
            (settings) -> new CustomLongswordWeaponItem(ToolMaterialsME.STEEL, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.ROHAN.getValue())), true);
    public static final Item ROHIRRIC_DAGGER = registerItemWithModel("rohirric_dagger",
            (settings) -> new CustomDaggerWeaponItem(ToolMaterialsME.STEEL, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.ROHAN.getValue())), false);
    public static final Item ROHIRRIC_AXE = registerItemWithModel("rohirric_axe",
            (settings) -> new CustomAxeWeaponItem(ToolMaterialsME.STEEL, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.ROHAN.getValue())), true);
    public static final Item ROHIRRIC_SPEAR = registerItemWithSpearModel("rohirric_spear",
            (settings) -> new CustomSpearWeaponItem(ToolMaterialsME.STEEL, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.ROHAN.getValue())));

    public static final Item ROHIRRIC_BOW = registerBigBowItem("rohirric_bow",
            (settings) -> new CustomBowWeaponItem(RangedWeaponTypesME.BOW, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.ROHAN.getValue())));
    public static final Item ROHIRRIC_LONGBOW = registerBigBowItem("rohirric_longbow",
            (settings) -> new CustomLongbowWeaponItem(RangedWeaponTypesME.LONGBOW, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.ROHAN.getValue())));

    public static final Item ROHIRRIC_NOBLE_SWORD = registerItemWithModel("rohirric_noble_sword",
            (settings) -> new CustomSwordWeaponItem(ToolMaterialsME.NOBLE_STEEL, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.ROHAN.getValue())), true);
    public static final Item ROHIRRIC_NOBLE_LONGSWORD = registerItemWithModel("rohirric_noble_longsword",
            (settings) -> new CustomLongswordWeaponItem(ToolMaterialsME.NOBLE_STEEL, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.ROHAN.getValue())), true);
    public static final Item ROHIRRIC_NOBLE_DAGGER = registerItemWithModel("rohirric_noble_dagger",
            (settings) -> new CustomDaggerWeaponItem(ToolMaterialsME.NOBLE_STEEL, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.ROHAN.getValue())), false);
    public static final Item ROHIRRIC_NOBLE_AXE = registerItemWithModel("rohirric_noble_axe",
            (settings) -> new CustomAxeWeaponItem(ToolMaterialsME.NOBLE_STEEL, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.ROHAN.getValue())), true);
    public static final Item ROHIRRIC_NOBLE_SPEAR = registerItemWithSpearModel("rohirric_noble_spear",
            (settings) -> new CustomSpearWeaponItem(ToolMaterialsME.NOBLE_STEEL, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.ROHAN.getValue())));

    public static final Item ROHIRRIC_NOBLE_BOW = registerBigBowItem("rohirric_noble_bow",
            (settings) -> new CustomBowWeaponItem(RangedWeaponTypesME.NOBLE_BOW, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.ROHAN.getValue())));

    public static final Item ROHIRRIC_SHIELD = registerShield("rohirric_shield",
            (settings) -> new CustomShieldItem(ShieldTypesME.MEDIUM_SHIELD, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.ROHAN.getValue())));
    public static final Item ROHIRRIC_BUCKING_HORSE_SHIELD = registerShield("rohirric_bucking_horse_shield",
            (settings) -> new CustomShieldItem(ShieldTypesME.MEDIUM_SHIELD, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.ROHAN.getValue())));
    public static final Item ROHIRRIC_GALLOPING_HORSE_SHIELD = registerShield("rohirric_galloping_horse_shield",
            (settings) -> new CustomShieldItem(ShieldTypesME.MEDIUM_SHIELD, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.ROHAN.getValue())));
    public static final Item ROHIRRIC_HORSE_HEAD_SHIELD = registerShield("rohirric_horse_head_shield",
            (settings) -> new CustomShieldItem(ShieldTypesME.MEDIUM_SHIELD, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.ROHAN.getValue())));
    public static final Item ROHIRRIC_PLAINSMAN_SHIELD = registerShield("rohirric_plainsman_shield",
            (settings) -> new CustomShieldItem(ShieldTypesME.MEDIUM_SHIELD, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.ROHAN.getValue())));
    public static final Item ROHIRRIC_TWIN_HORSES_SHIELD = registerShield("rohirric_twin_horses_shield",
            (settings) -> new CustomShieldItem(ShieldTypesME.MEDIUM_SHIELD, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.ROHAN.getValue())));
    public static final Item ROHIRRIC_EORLING_SHIELD = registerShield("rohirric_eorling_shield",
            (settings) -> new CustomShieldItem(ShieldTypesME.HEAVY_SHIELD, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.ROHAN.getValue())));
    public static final Item ROHIRRIC_ORNAMENTED_SHIELD = registerShield("rohirric_ornamented_shield",
            (settings) -> new CustomShieldItem(ShieldTypesME.HEAVY_SHIELD, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.ROHAN.getValue())));
    public static final Item ROHIRRIC_ROYAL_GUARD_SHIELD = registerShield("rohirric_royal_guard_shield",
            (settings) -> new CustomShieldItem(ShieldTypesME.HEAVY_SHIELD, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.ROHAN.getValue())));
    //endregion

    //region DALE
    public static final Item DALISH_SWORD = registerItemWithModel("dalish_sword",
            (settings) -> new CustomSwordWeaponItem(ToolMaterialsME.STEEL, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.DALE.getValue())), true);
    public static final Item DALISH_LONGSWORD = registerItemWithModel("dalish_longsword",
            (settings) -> new CustomLongswordWeaponItem(ToolMaterialsME.STEEL, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.DALE.getValue())), true);
    public static final Item DALISH_DAGGER = registerItemWithModel("dalish_dagger",
            (settings) -> new CustomDaggerWeaponItem(ToolMaterialsME.STEEL, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.DALE.getValue())), false);
    public static final Item DALISH_AXE = registerItemWithModel("dalish_axe",
            (settings) -> new CustomAxeWeaponItem(ToolMaterialsME.STEEL, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.DALE.getValue())), true);
    public static final Item DALISH_SPEAR = registerItemWithSpearModel("dalish_spear",
            (settings) -> new CustomSpearWeaponItem(ToolMaterialsME.STEEL, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.DALE.getValue())));

    public static final Item DALISH_LONGBOW = registerBigBowItem("dalish_longbow",
            (settings) -> new CustomLongbowWeaponItem(RangedWeaponTypesME.LONGBOW, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.DALE.getValue())));

    public static final Item DALISH_NOBLE_SWORD = registerItemWithModel("dalish_noble_sword",
            (settings) -> new CustomSwordWeaponItem(ToolMaterialsME.NOBLE_STEEL, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.DALE.getValue())), true);
    public static final Item DALISH_NOBLE_LONGSWORD = registerItemWithModel("dalish_noble_longsword",
            (settings) -> new CustomLongswordWeaponItem(ToolMaterialsME.NOBLE_STEEL, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.DALE.getValue())), true);
    public static final Item DALISH_NOBLE_DAGGER = registerItemWithModel("dalish_noble_dagger",
            (settings) -> new CustomDaggerWeaponItem(ToolMaterialsME.NOBLE_STEEL, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.DALE.getValue())), false);
    public static final Item DALISH_NOBLE_AXE = registerItemWithModel("dalish_noble_axe",
            (settings) -> new CustomAxeWeaponItem(ToolMaterialsME.NOBLE_STEEL, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.DALE.getValue())), true);
    public static final Item DALISH_NOBLE_SPEAR = registerItemWithSpearModel("dalish_noble_spear",
            (settings) -> new CustomSpearWeaponItem(ToolMaterialsME.NOBLE_STEEL, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.DALE.getValue())));

    public static final Item DALISH_NOBLE_LONGBOW = registerBigBowItem("dalish_noble_longbow",
            (settings) -> new CustomLongbowWeaponItem(RangedWeaponTypesME.NOBLE_LONGBOW, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.DALE.getValue())));

    public static final Item DALISH_HEYDAY_SWORD = registerItemWithModel("dalish_heyday_sword",
            (settings) -> new CustomSwordWeaponItem(ToolMaterialsME.NOBLE_STEEL, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.DALE.getValue())), true);
    public static final Item DALISH_HEYDAY_LONGSWORD = registerItemWithModel("dalish_heyday_longsword",
            (settings) -> new CustomLongswordWeaponItem(ToolMaterialsME.NOBLE_STEEL, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.DALE.getValue())), true);
    public static final Item DALISH_HEYDAY_SCIMITAR = registerItemWithModel("dalish_heyday_scimitar",
            (settings) -> new CustomLongswordWeaponItem(ToolMaterialsME.NOBLE_STEEL, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.DALE.getValue())), true);
    public static final Item DALISH_HEYDAY_DAGGER = registerItemWithModel("dalish_heyday_dagger",
            (settings) -> new CustomDaggerWeaponItem(ToolMaterialsME.NOBLE_STEEL, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.DALE.getValue())), false);
    public static final Item DALISH_HEYDAY_AXE = registerItemWithModel("dalish_heyday_axe",
            (settings) -> new CustomAxeWeaponItem(ToolMaterialsME.NOBLE_STEEL, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.DALE.getValue())), true);
    public static final Item DALISH_HEYDAY_SPEAR = registerItemWithSpearModel("dalish_heyday_spear",
            (settings) -> new CustomSpearWeaponItem(ToolMaterialsME.NOBLE_STEEL, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.DALE.getValue())));

    public static final Item DALISH_HEYDAY_LONGBOW = registerBigBowItem("dalish_heyday_longbow",
            (settings) -> new CustomLongbowWeaponItem(RangedWeaponTypesME.NOBLE_LONGBOW, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.DALE.getValue())));

    public static final Item DALISH_BLUE_OVAL_SHIELD = registerShield("dalish_blue_oval_shield",
            (settings) -> new CustomShieldItem(ShieldTypesME.MEDIUM_SHIELD, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.DALE.getValue())));
    public static final Item DALISH_BARDING_OVAL_SHIELD = registerShield("dalish_barding_oval_shield",
            (settings) -> new CustomShieldItem(ShieldTypesME.MEDIUM_SHIELD, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.DALE.getValue())));

    public static final Item DALISH_BLUE_BRACED_SHIELD = registerShield("dalish_blue_braced_shield",
            (settings) -> new CustomShieldItem(ShieldTypesME.MEDIUM_SHIELD, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.DALE.getValue())));
    public static final Item DALISH_BARDING_BRACED_SHIELD = registerShield("dalish_barding_braced_shield",
            (settings) -> new CustomShieldItem(ShieldTypesME.MEDIUM_SHIELD, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.DALE.getValue())));

    public static final Item DALISH_HEAVY_SHIELD = registerShield("dalish_heavy_shield",
            (settings) -> new CustomShieldItem(ShieldTypesME.HEAVY_SHIELD, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.DALE.getValue())));
    public static final Item DALISH_BARDING_HEAVY_SHIELD = registerShield("dalish_barding_heavy_shield",
            (settings) -> new CustomShieldItem(ShieldTypesME.HEAVY_SHIELD, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.DALE.getValue())));
    public static final Item DALISH_ROYAL_HEAVY_SHIELD = registerShield("dalish_royal_heavy_shield",
            (settings) -> new CustomShieldItem(ShieldTypesME.HEAVY_SHIELD, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.DALE.getValue())));
    public static final Item DALISH_ROYAL_ROUND_SHIELD = registerShield("dalish_royal_round_shield",
            (settings) -> new CustomShieldItem(ShieldTypesME.HEAVY_SHIELD, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.DALE.getValue())));
    public static final Item DALISH_HEYDAY_ROUND_SHIELD = registerShield("dalish_heyday_round_shield",
            (settings) -> new CustomShieldItem(ShieldTypesME.HEAVY_SHIELD, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.DALE.getValue())));
    //endregion

    //endregion

    //region LOTHLORIEN
    public static final Item LORIEN_SWORD = registerItemWithModel("lorien_sword",
            (settings) -> new CustomSwordWeaponItem(ToolMaterialsME.EDHEL_STEEL,  settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.LOTHLORIEN.getValue())), true);
    public static final Item LORIEN_GLAIVE = registerItemWithModel("lorien_glaive",
            (settings) -> new CustomLongswordWeaponItem(ToolMaterialsME.EDHEL_STEEL,  settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.LOTHLORIEN.getValue())), true);
    public static final Item LORIEN_DAGGER = registerItemWithModel("lorien_dagger",
            (settings) -> new CustomDaggerWeaponItem(ToolMaterialsME.EDHEL_STEEL,  settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.LOTHLORIEN.getValue())), false);
    public static final Item LORIEN_AXE = registerItemWithModel("lorien_axe",
            (settings) -> new CustomAxeWeaponItem(ToolMaterialsME.EDHEL_STEEL,  settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.LOTHLORIEN.getValue())), true);
    public static final Item LORIEN_SPEAR = registerItemWithSpearModel("lorien_spear",
            (settings) -> new CustomSpearWeaponItem(ToolMaterialsME.EDHEL_STEEL,  settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.LOTHLORIEN.getValue())));

    public static final Item LORIEN_BOW = registerBigBowItem("lorien_bow",
            (settings) -> new CustomBowWeaponItem(RangedWeaponTypesME.BOW, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.LOTHLORIEN.getValue())));
    public static final Item LORIEN_LONGBOW = registerBigBowItem("lorien_longbow",
            (settings) -> new CustomLongbowWeaponItem(RangedWeaponTypesME.LONGBOW, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.LOTHLORIEN.getValue())));

    public static final Item LORIEN_NOBLE_SWORD = registerItemWithModel("lorien_noble_sword",
            (settings) -> new CustomSwordWeaponItem(ToolMaterialsME.NOBLE_EDHEL_STEEL,  settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.LOTHLORIEN.getValue())), true);
    public static final Item LORIEN_NOBLE_GLAIVE = registerItemWithModel("lorien_noble_glaive",
            (settings) -> new CustomLongswordWeaponItem(ToolMaterialsME.NOBLE_EDHEL_STEEL,  settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.LOTHLORIEN.getValue())), true);
    public static final Item LORIEN_NOBLE_DAGGER = registerItemWithModel("lorien_noble_dagger",
            (settings) -> new CustomDaggerWeaponItem(ToolMaterialsME.NOBLE_EDHEL_STEEL,  settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.LOTHLORIEN.getValue())), false);
    public static final Item LORIEN_NOBLE_AXE = registerItemWithModel("lorien_noble_axe",
            (settings) -> new CustomAxeWeaponItem(ToolMaterialsME.NOBLE_EDHEL_STEEL,  settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.LOTHLORIEN.getValue())), true);
    public static final Item LORIEN_NOBLE_SPEAR = registerItemWithSpearModel("lorien_noble_spear",
            (settings) -> new CustomSpearWeaponItem(ToolMaterialsME.NOBLE_EDHEL_STEEL,  settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.LOTHLORIEN.getValue())));

    public static final Item LORIEN_NOBLE_LONGBOW = registerBigBowItem("lorien_noble_longbow",
            (settings) -> new CustomLongbowWeaponItem(RangedWeaponTypesME.NOBLE_LONGBOW, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.LOTHLORIEN.getValue())));

    public static final Item LORIEN_SHIELD = registerShield("lorien_shield",
            (settings) -> new CustomShieldItem(ShieldTypesME.MEDIUM_SHIELD,  settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.LOTHLORIEN.getValue())));
    public static final Item LORIEN_LAURELS_SHIELD = registerShield("lorien_laurels_shield",
            (settings) -> new CustomShieldItem(ShieldTypesME.MEDIUM_SHIELD,  settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.LOTHLORIEN.getValue())));
    public static final Item LORIEN_MALLORN_SHIELD = registerShield("lorien_mallorn_shield",
            (settings) -> new CustomShieldItem(ShieldTypesME.MEDIUM_SHIELD,  settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.LOTHLORIEN.getValue())));
    public static final Item GALADHRIM_SHIELD = registerShield("galadhrim_shield",
            (settings) -> new CustomShieldItem(ShieldTypesME.HEAVY_SHIELD,  settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.LOTHLORIEN.getValue())));
    public static final Item GALADHRIM_LORD_SHIELD = registerShield("galadhrim_lord_shield",
            (settings) -> new CustomShieldItem(ShieldTypesME.HEAVY_SHIELD,  settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.LOTHLORIEN.getValue())));
    //endregion

    //region EREBOR
    public static final Item EREBOR_SWORD = registerItemWithModel("erebor_sword",
            (settings) -> new CustomSwordWeaponItem(ToolMaterialsME.KHAZAD_STEEL, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.LONGBEARDS_EREBOR.getValue())), true);
    public static final Item EREBOR_LONGSWORD = registerItemWithModel("erebor_longsword",
            (settings) -> new CustomLongswordWeaponItem(ToolMaterialsME.KHAZAD_STEEL, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.LONGBEARDS_EREBOR.getValue())), true);
    public static final Item EREBOR_DAGGER = registerItemWithModel("erebor_dagger",
            (settings) -> new CustomDaggerWeaponItem(ToolMaterialsME.KHAZAD_STEEL, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.LONGBEARDS_EREBOR.getValue())), false);
    public static final Item EREBOR_AXE = registerItemWithModel("erebor_axe",
            (settings) -> new CustomAxeWeaponItem(ToolMaterialsME.KHAZAD_STEEL, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.LONGBEARDS_EREBOR.getValue())), true);
    public static final Item EREBOR_SPEAR = registerItemWithSpearModel("erebor_spear",
            (settings) -> new CustomSpearWeaponItem(ToolMaterialsME.KHAZAD_STEEL, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.LONGBEARDS_EREBOR.getValue())));

    public static final Item EREBOR_BOW = registerBigBowItem("erebor_bow",
            (settings) -> new CustomBowWeaponItem(RangedWeaponTypesME.BOW, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.LONGBEARDS_EREBOR.getValue())));
    public static final Item EREBOR_CROSSBOW = registerCrossbowItem("erebor_crossbow",
            (settings) -> new CustomCrossbowWeaponItem(RangedWeaponTypesME.CROSSBOW, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.LONGBEARDS_EREBOR.getValue())));

    public static final Item EREBOR_NOBLE_SWORD = registerItemWithModel("erebor_noble_sword",
            (settings) -> new CustomSwordWeaponItem(ToolMaterialsME.NOBLE_KHAZAD_STEEL, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.LONGBEARDS_EREBOR.getValue())), true);
    public static final Item EREBOR_NOBLE_LONGSWORD = registerItemWithModel("erebor_noble_longsword",
            (settings) -> new CustomLongswordWeaponItem(ToolMaterialsME.NOBLE_KHAZAD_STEEL, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.LONGBEARDS_EREBOR.getValue())), true);
    public static final Item EREBOR_NOBLE_DAGGER = registerItemWithModel("erebor_noble_dagger",
            (settings) -> new CustomDaggerWeaponItem(ToolMaterialsME.NOBLE_KHAZAD_STEEL, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.LONGBEARDS_EREBOR.getValue())), false);
    public static final Item EREBOR_NOBLE_AXE = registerItemWithModel("erebor_noble_axe",
            (settings) -> new CustomAxeWeaponItem(ToolMaterialsME.NOBLE_KHAZAD_STEEL, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.LONGBEARDS_EREBOR.getValue())), true);
    public static final Item EREBOR_NOBLE_SPEAR = registerItemWithSpearModel("erebor_noble_spear",
            (settings) -> new CustomSpearWeaponItem(ToolMaterialsME.NOBLE_KHAZAD_STEEL, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.LONGBEARDS_EREBOR.getValue())));

    public static final Item EREBOR_NOBLE_BOW = registerBigBowItem("erebor_noble_bow",
            (settings) -> new CustomBowWeaponItem(RangedWeaponTypesME.NOBLE_BOW, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.LONGBEARDS_EREBOR.getValue())));
    public static final Item EREBOR_NOBLE_CROSSBOW = registerCrossbowItem("erebor_noble_crossbow",
            (settings) -> new CustomCrossbowWeaponItem(RangedWeaponTypesME.NOBLE_CROSSBOW, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.LONGBEARDS_EREBOR.getValue())));

    public static final Item EREBOR_SHIELD = registerShield("erebor_shield",
            (settings) -> new CustomShieldItem(ShieldTypesME.MEDIUM_SHIELD, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.LONGBEARDS_EREBOR.getValue())));
    public static final Item EREBOR_CROSS_SHIELD = registerShield("erebor_cross_shield",
            (settings) -> new CustomShieldItem(ShieldTypesME.MEDIUM_SHIELD, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.LONGBEARDS_EREBOR.getValue())));
    public static final Item EREBOR_PLATED_SHIELD = registerShield("erebor_plated_shield",
            (settings) -> new CustomShieldItem(ShieldTypesME.MEDIUM_SHIELD, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.LONGBEARDS_EREBOR.getValue())));
    public static final Item EREBOR_ORNAMENTED_SHIELD = registerShield("erebor_ornamented_shield",
            (settings) -> new CustomShieldItem(ShieldTypesME.MEDIUM_SHIELD, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.LONGBEARDS_EREBOR.getValue())));
    public static final Item EREBOR_REINFORCED_SHIELD = registerShield("erebor_reinforced_shield",
            (settings) -> new CustomShieldItem(ShieldTypesME.MEDIUM_SHIELD, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.LONGBEARDS_EREBOR.getValue())));

    public static final Item EREBOR_BUCKLER_SHIELD = registerShield("erebor_buckler_shield",
            (settings) -> new CustomShieldItem(ShieldTypesME.HEAVY_SHIELD, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.LONGBEARDS_EREBOR.getValue())));
    public static final Item EREBOR_TOWER_SHIELD = registerShield("erebor_tower_shield",
            (settings) -> new CustomShieldItem(ShieldTypesME.HEAVY_SHIELD, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.LONGBEARDS_EREBOR.getValue())));
    public static final Item EREBOR_REINFORCED_TOWER_SHIELD = registerShield("erebor_reinforced_tower_shield",
            (settings) -> new CustomShieldItem(ShieldTypesME.HEAVY_SHIELD, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.LONGBEARDS_EREBOR.getValue())));
    public static final Item EREBOR_ORNAMENTED_TOWER_SHIELD = registerShield("erebor_ornamented_tower_shield",
            (settings) -> new CustomShieldItem(ShieldTypesME.HEAVY_SHIELD, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.LONGBEARDS_EREBOR.getValue())));
    public static final Item RAVENHILL_TOWER_SHIELD = registerShield("ravenhill_tower_shield",
            (settings) -> new CustomShieldItem(ShieldTypesME.HEAVY_SHIELD, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.LONGBEARDS_EREBOR.getValue())));
    public static final Item RAVENHILL_REINFORCED_TOWER_SHIELD = registerShield("ravenhill_reinforced_tower_shield",
            (settings) -> new CustomShieldItem(ShieldTypesME.HEAVY_SHIELD, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.LONGBEARDS_EREBOR.getValue())));
    public static final Item RAVENHILL_ORNAMENTED_TOWER_SHIELD = registerShield("ravenhill_ornamented_tower_shield",
            (settings) -> new CustomShieldItem(ShieldTypesME.HEAVY_SHIELD, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.LONGBEARDS_EREBOR.getValue())));
    //endregion

    public static final Item ORC_SWORD = registerItemWithModel("orc_sword",
            (settings) -> new CustomSwordWeaponItem(ToolMaterialsME.BURZUM_STEEL, settings), new Item.Settings(), true);
    public static final Item ORC_BROADBLADE = registerItemWithModel("orc_broadblade",
            (settings) -> new CustomLongswordWeaponItem(ToolMaterialsME.BURZUM_STEEL, settings), new Item.Settings(), true);
    public static final Item ORC_KNIFE = registerItemWithModel("orc_knife",
            (settings) -> new CustomDaggerWeaponItem(ToolMaterialsME.BURZUM_STEEL, settings), new Item.Settings(), false);
    public static final Item ORC_AXE = registerItemWithModel("orc_axe",
            (settings) -> new CustomAxeWeaponItem(ToolMaterialsME.BURZUM_STEEL, settings), new Item.Settings(), true);
    public static final Item ORC_SPEAR = registerItemWithSpearModel("orc_spear",
            (settings) -> new CustomSpearWeaponItem(ToolMaterialsME.BURZUM_STEEL, settings), new Item.Settings());

    public static final Item ORCISH_BOW = registerBigBowItem("orcish_bow",
            (settings) -> new CustomBowWeaponItem(RangedWeaponTypesME.BOW, settings), new Item.Settings());

    public static final Item RUINED_DWARVEN_SHIELD = registerShield("ruined_dwarven_shield",
            (settings) -> new CustomShieldItem(ShieldTypesME.MEDIUM_SHIELD, settings), new Item.Settings());
    public static final Item RUINED_DWARVEN_CROSS_SHIELD = registerShield("ruined_dwarven_cross_shield",
            (settings) -> new CustomShieldItem(ShieldTypesME.MEDIUM_SHIELD, settings), new Item.Settings());
    public static final Item RUINED_DWARVEN_ORNAMENTED_SHIELD = registerShield("ruined_dwarven_ornamented_shield",
            (settings) -> new CustomShieldItem(ShieldTypesME.MEDIUM_SHIELD, settings), new Item.Settings());
    public static final Item RUINED_DWARVEN_REINFORCED_SHIELD = registerShield("ruined_dwarven_reinforced_shield",
            (settings) -> new CustomShieldItem(ShieldTypesME.MEDIUM_SHIELD, settings), new Item.Settings());

    public static final Item RUINED_DWARVEN_ORNAMENTED_TOWER_SHIELD = registerShield("ruined_dwarven_ornamented_tower_shield",
            (settings) -> new CustomShieldItem(ShieldTypesME.MEDIUM_SHIELD, settings), new Item.Settings());
    public static final Item RUINED_DWARVEN_REINFORCED_TOWER_SHIELD = registerShield("ruined_dwarven_reinforced_tower_shield",
            (settings) -> new CustomShieldItem(ShieldTypesME.MEDIUM_SHIELD, settings), new Item.Settings());

    //region MORDOR
    public static final Item MORDOR_FALCHION = registerItemWithModel("mordor_falchion",
            (settings) -> new CustomSwordWeaponItem(ToolMaterialsME.BURZUM_STEEL, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MORDOR.getValue())), true);
    public static final Item MORDOR_SCIMITAR = registerItemWithModel("mordor_scimitar",
            (settings) -> new CustomLongswordWeaponItem(ToolMaterialsME.BURZUM_STEEL, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MORDOR.getValue())), true);
    public static final Item MORDOR_DAGGER = registerItemWithModel("mordor_dagger",
            (settings) -> new CustomDaggerWeaponItem(ToolMaterialsME.BURZUM_STEEL, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MORDOR.getValue())), false);
    public static final Item MORDOR_AXE = registerItemWithModel("mordor_axe",
            (settings) -> new CustomAxeWeaponItem(ToolMaterialsME.BURZUM_STEEL, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MORDOR.getValue())), true);
    public static final Item MORDOR_SPEAR = registerItemWithSpearModel("mordor_spear",
            (settings) -> new CustomSpearWeaponItem(ToolMaterialsME.BURZUM_STEEL, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MORDOR.getValue())));

    public static final Item MORDOR_BOW = registerBigBowItem("mordor_bow",
            (settings) -> new CustomBowWeaponItem(RangedWeaponTypesME.BOW, settings), new Item.Settings());

    public static final Item MORDOR_ELITE_CLEAVER = registerItemWithModel("mordor_elite_cleaver",
            (settings) -> new CustomSwordWeaponItem(ToolMaterialsME.NOBLE_BURZUM_STEEL, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MORDOR.getValue())), true);
    public static final Item MORDOR_ELITE_WARBLADE = registerItemWithModel("mordor_elite_warblade",
            (settings) -> new CustomLongswordWeaponItem(ToolMaterialsME.NOBLE_BURZUM_STEEL, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MORDOR.getValue())), true);
    public static final Item MORDOR_ELITE_DAGGER = registerItemWithModel("mordor_elite_dagger",
            (settings) -> new CustomDaggerWeaponItem(ToolMaterialsME.NOBLE_BURZUM_STEEL, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MORDOR.getValue())), false);
    public static final Item MORDOR_ELITE_AXE = registerItemWithModel("mordor_elite_axe",
            (settings) -> new CustomAxeWeaponItem(ToolMaterialsME.NOBLE_BURZUM_STEEL, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MORDOR.getValue())), true);
    public static final Item MORDOR_ELITE_SPEAR = registerItemWithSpearModel("mordor_elite_spear",
            (settings) -> new CustomSpearWeaponItem(ToolMaterialsME.NOBLE_BURZUM_STEEL, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MORDOR.getValue())));

    public static final Item MORDOR_ELITE_LONGBOW = registerBigBowItem("mordor_elite_longbow",
            (settings) -> new CustomLongbowWeaponItem(RangedWeaponTypesME.NOBLE_LONGBOW, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MORDOR.getValue())));

    public static final Item BLACK_NUMENOREAN_SWORD = registerItemWithModel("black_numenorean_sword",
            (settings) -> new CustomSwordWeaponItem(ToolMaterialsME.NOBLE_STEEL, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MORDOR.getValue())), true);
    public static final Item BLACK_NUMENOREAN_LONGSWORD = registerItemWithModel("black_numenorean_longsword",
            (settings) -> new CustomLongswordWeaponItem(ToolMaterialsME.NOBLE_STEEL, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MORDOR.getValue())), true);
    public static final Item BLACK_NUMENOREAN_DAGGER = registerItemWithModel("black_numenorean_dagger",
            (settings) -> new CustomDaggerWeaponItem(ToolMaterialsME.NOBLE_STEEL, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MORDOR.getValue())), false);
    public static final Item BLACK_NUMENOREAN_AXE = registerItemWithModel("black_numenorean_axe",
            (settings) -> new CustomAxeWeaponItem(ToolMaterialsME.NOBLE_STEEL, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MORDOR.getValue())), true);
    public static final Item BLACK_NUMENOREAN_SPEAR = registerItemWithSpearModel("black_numenorean_spear",
            (settings) -> new CustomSpearWeaponItem(ToolMaterialsME.NOBLE_STEEL, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MORDOR.getValue())));

    public static final Item MORDOR_WOODEN_SHIELD = registerShield("mordor_wooden_shield",
            (settings) -> new CustomShieldItem(ShieldTypesME.LIGHT_SHIELD, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MORDOR.getValue())));
    public static final Item MORDOR_PAINTED_WOODEN_SHIELD = registerShield("mordor_painted_wooden_shield",
            (settings) -> new CustomShieldItem(ShieldTypesME.LIGHT_SHIELD, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MORDOR.getValue())));

    public static final Item MORDOR_ROUND_SHIELD = registerShield("mordor_round_shield",
            (settings) -> new CustomShieldItem(ShieldTypesME.MEDIUM_SHIELD, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MORDOR.getValue())));
    public static final Item MORDOR_BLACK_ROUND_SHIELD = registerShield("mordor_black_round_shield",
            (settings) -> new CustomShieldItem(ShieldTypesME.MEDIUM_SHIELD, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MORDOR.getValue())));
    public static final Item MORDOR_PAINTED_ROUND_SHIELD = registerShield("mordor_painted_round_shield",
            (settings) -> new CustomShieldItem(ShieldTypesME.MEDIUM_SHIELD, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MORDOR.getValue())));

    public static final Item MORDOR_BRACED_SHIELD = registerShield("mordor_braced_shield",
            (settings) -> new CustomShieldItem(ShieldTypesME.MEDIUM_SHIELD, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MORDOR.getValue())));
    public static final Item MORDOR_BLACK_BRACED_SHIELD = registerShield("mordor_black_braced_shield",
            (settings) -> new CustomShieldItem(ShieldTypesME.MEDIUM_SHIELD, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MORDOR.getValue())));
    public static final Item MORDOR_PAINTED_BRACED_SHIELD = registerShield("mordor_painted_braced_shield",
            (settings) -> new CustomShieldItem(ShieldTypesME.MEDIUM_SHIELD, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MORDOR.getValue())));

    public static final Item MORDOR_LARGE_SHIELD = registerShield("mordor_large_shield",
            (settings) -> new CustomShieldItem(ShieldTypesME.MEDIUM_SHIELD, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MORDOR.getValue())));
    public static final Item MORDOR_BLACK_LARGE_SHIELD = registerShield("mordor_black_large_shield",
            (settings) -> new CustomShieldItem(ShieldTypesME.MEDIUM_SHIELD, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MORDOR.getValue())));
    public static final Item MORDOR_PAINTED_LARGE_SHIELD = registerShield("mordor_painted_large_shield",
            (settings) -> new CustomShieldItem(ShieldTypesME.MEDIUM_SHIELD, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MORDOR.getValue())));

    public static final Item GONDORIAN_CONVERTED_SHIELD = registerShield("gondorian_converted_shield",
            (settings) -> new CustomShieldItem(ShieldTypesME.MEDIUM_SHIELD, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MORDOR.getValue())));

    public static final Item MORDOR_HEAVY_SHIELD = registerShield("mordor_heavy_shield",
            (settings) -> new CustomShieldItem(ShieldTypesME.HEAVY_SHIELD, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MORDOR.getValue())));
    public static final Item MORDOR_PAINTED_HEAVY_SHIELD = registerShield("mordor_painted_heavy_shield",
            (settings) -> new CustomShieldItem(ShieldTypesME.HEAVY_SHIELD, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MORDOR.getValue())));
    public static final Item BLACK_NUMENOREAN_TOWER_SHIELD = registerShield("black_numenorean_tower_shield",
            (settings) -> new CustomShieldItem(ShieldTypesME.MEDIUM_SHIELD, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MORDOR.getValue())));

    //region DOL GULDUR
    public static final Item DOL_GULDUR_MACHETE = registerItemWithModel("dol_guldur_machete",
            (settings) -> new CustomSwordWeaponItem(ToolMaterialsME.NOBLE_BURZUM_STEEL, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MORDOR.getValue())), true);
    public static final Item DOL_GULDUR_AXE = registerItemWithModel("dol_guldur_axe",
            (settings) -> new CustomAxeWeaponItem(ToolMaterialsME.NOBLE_BURZUM_STEEL, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MORDOR.getValue())), true);

    public static final Item DOL_GULDUR_PAVISE = registerShield("dol_guldur_pavise",
            (settings) -> new CustomShieldItem(ShieldTypesME.MEDIUM_SHIELD, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MORDOR.getValue())));

    public static final Item DOL_GULDUR_SHIELD = registerShield("dol_guldur_shield",
            (settings) -> new CustomShieldItem(ShieldTypesME.HEAVY_SHIELD, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MORDOR.getValue())));
    //endregion
    //endregion

    //region ISENGARD
    public static final Item ISENGARD_ORC_CLEAVER = registerItemWithModel("isengard_orc_cleaver",
            (settings) -> new CustomSwordWeaponItem(ToolMaterialsME.BURZUM_STEEL, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.ISENGARD.getValue())), true);
    public static final Item ISENGARD_ORC_WARBLADE = registerItemWithModel("isengard_orc_warblade",
            (settings) -> new CustomLongswordWeaponItem(ToolMaterialsME.BURZUM_STEEL, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.ISENGARD.getValue())), true);
    public static final Item ISENGARD_ORC_DAGGER = registerItemWithModel("isengard_orc_dagger",
            (settings) -> new CustomDaggerWeaponItem(ToolMaterialsME.BURZUM_STEEL, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.ISENGARD.getValue())), false);
    public static final Item ISENGARD_ORC_AXE = registerItemWithModel("isengard_orc_axe",
            (settings) -> new CustomAxeWeaponItem(ToolMaterialsME.BURZUM_STEEL, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.ISENGARD.getValue())), true);
    public static final Item ISENGARD_ORC_SPEAR = registerItemWithSpearModel("isengard_orc_spear",
            (settings) -> new CustomSpearWeaponItem(ToolMaterialsME.BURZUM_STEEL, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.ISENGARD.getValue())));

    public static final Item URUK_HAI_FALCHION = registerItemWithModel("uruk_hai_falchion",
            (settings) -> new CustomSwordWeaponItem(ToolMaterialsME.NOBLE_BURZUM_STEEL, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.ISENGARD.getValue())), true);
    public static final Item URUK_HAI_WARBLADE = registerItemWithModel("uruk_hai_warblade",
            (settings) -> new CustomLongswordWeaponItem(ToolMaterialsME.NOBLE_BURZUM_STEEL, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.ISENGARD.getValue())), true);
    public static final Item URUK_HAI_KNIFE = registerItemWithModel("uruk_hai_knife",
            (settings) -> new CustomDaggerWeaponItem(ToolMaterialsME.NOBLE_BURZUM_STEEL, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.ISENGARD.getValue())), false);
    public static final Item URUK_HAI_AXE = registerItemWithModel("uruk_hai_axe",
            (settings) -> new CustomAxeWeaponItem(ToolMaterialsME.NOBLE_BURZUM_STEEL, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.ISENGARD.getValue())), true);
    public static final Item URUK_HAI_SPEAR = registerItemWithSpearModel("uruk_hai_spear",
            (settings) -> new CustomSpearWeaponItem(ToolMaterialsME.NOBLE_BURZUM_STEEL, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.ISENGARD.getValue())));

    public static final Item URUK_HAI_BOW = registerBigBowItem("uruk_hai_bow",
            (settings) -> new CustomBowWeaponItem(RangedWeaponTypesME.NOBLE_BOW, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.ISENGARD.getValue())));
    public static final Item URUK_HAI_CROSSBOW = registerCrossbowItem("uruk_hai_crossbow",
            (settings) -> new CustomCrossbowWeaponItem(RangedWeaponTypesME.NOBLE_CROSSBOW, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.ISENGARD.getValue())));

    public static final Item URUK_HAI_HEATER_SHIELD = registerShield("uruk_hai_heater_shield",
            (settings) -> new CustomShieldItem(ShieldTypesME.MEDIUM_SHIELD, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.ISENGARD.getValue())));

    public static final Item URUK_HAI_SHIELD = registerShield("uruk_hai_shield",
            (settings) -> new CustomShieldItem(ShieldTypesME.HEAVY_SHIELD, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.ISENGARD.getValue())));
    public static final Item URUK_HAI_WHITE_HAND_SHIELD = registerShield("uruk_hai_white_hand_shield",
            (settings) -> new CustomShieldItem(ShieldTypesME.HEAVY_SHIELD, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.ISENGARD.getValue())));
    public static final Item URUK_HAI_WHITE_PALMPRINT_SHIELD = registerShield("uruk_hai_white_palmprint_shield",
            (settings) -> new CustomShieldItem(ShieldTypesME.HEAVY_SHIELD, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.ISENGARD.getValue())));
    public static final Item URUK_HAI_S_RUNE_SHIELD = registerShield("uruk_hai_s_rune_shield",
            (settings) -> new CustomShieldItem(ShieldTypesME.HEAVY_SHIELD, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.ISENGARD.getValue())));

    public static final Item URUK_HAI_SIEGE_SHIELD = registerShield("uruk_hai_siege_shield",
            (settings) -> new CustomSiegeShieldItem(ShieldTypesME.HEAVY_SHIELD, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.ISENGARD.getValue())));
    //endregion

    //region MISTIES

    //region GUNDABAD
    public static final Item GUNDABAD_FALCHION = registerItemWithModel("gundabad_falchion",
            (settings) -> new CustomSwordWeaponItem(ToolMaterialsME.BURZUM_STEEL, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MISTY_MOUNTAINS_GOBLINS.getValue())), true);
    public static final Item GUNDABAD_ELITE_CLEAVER = registerItemWithModel("gundabad_elite_cleaver",
            (settings) -> new CustomSwordWeaponItem(ToolMaterialsME.NOBLE_BURZUM_STEEL, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MISTY_MOUNTAINS_GOBLINS.getValue())), true);
    public static final Item GUNDABAD_WARBLADE = registerItemWithModel("gundabad_warblade",
            (settings) -> new CustomLongswordWeaponItem(ToolMaterialsME.BURZUM_STEEL, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MISTY_MOUNTAINS_GOBLINS.getValue())), true);
    public static final Item GUNDABAD_ELITE_SCIMITAR = registerItemWithModel("gundabad_elite_scimitar",
            (settings) -> new CustomLongswordWeaponItem(ToolMaterialsME.NOBLE_BURZUM_STEEL, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MISTY_MOUNTAINS_GOBLINS.getValue())), true);
    public static final Item GUNDABAD_SHANK = registerItemWithModel("gundabad_shank",
            (settings) -> new CustomDaggerWeaponItem(ToolMaterialsME.BURZUM_STEEL, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MISTY_MOUNTAINS_GOBLINS.getValue())), false);
    public static final Item GUNDABAD_ELITE_DAGGER = registerItemWithModel("gundabad_elite_dagger",
            (settings) -> new CustomDaggerWeaponItem(ToolMaterialsME.NOBLE_BURZUM_STEEL, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MISTY_MOUNTAINS_GOBLINS.getValue())), false);
    public static final Item GUNDABAD_AXE = registerItemWithModel("gundabad_axe",
            (settings) -> new CustomAxeWeaponItem(ToolMaterialsME.BURZUM_STEEL, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MISTY_MOUNTAINS_GOBLINS.getValue())), true);
    public static final Item GUNDABAD_ELITE_BATTLEAXE = registerItemWithModel("gundabad_elite_battleaxe",
            (settings) -> new CustomAxeWeaponItem(ToolMaterialsME.NOBLE_BURZUM_STEEL, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MISTY_MOUNTAINS_GOBLINS.getValue())), true);
    public static final Item GUNDABAD_SPEAR = registerItemWithSpearModel("gundabad_spear",
            (settings) -> new CustomSpearWeaponItem(ToolMaterialsME.BURZUM_STEEL, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MISTY_MOUNTAINS_GOBLINS.getValue())));
    public static final Item GUNDABAD_ELITE_SPEAR = registerItemWithSpearModel("gundabad_elite_spear",
            (settings) -> new CustomSpearWeaponItem(ToolMaterialsME.NOBLE_BURZUM_STEEL, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MISTY_MOUNTAINS_GOBLINS.getValue())));

    public static final Item GUNDABAD_BOW = registerBigBowItem("gundabad_bow",
            (settings) -> new CustomBowWeaponItem(RangedWeaponTypesME.BOW, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MISTY_MOUNTAINS_GOBLINS.getValue())));
    public static final Item GUNDABAD_CROSSBOW = registerCrossbowItem("gundabad_crossbow",
            (settings) -> new CustomCrossbowWeaponItem(RangedWeaponTypesME.CROSSBOW, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MISTY_MOUNTAINS_GOBLINS.getValue())));

    public static final Item GUNDABAD_WOODEN_SHIELD = registerShield("gundabad_wooden_shield",
            (settings) -> new CustomShieldItem(ShieldTypesME.LIGHT_SHIELD, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MISTY_MOUNTAINS_GOBLINS.getValue())));
    public static final Item GUNDABAD_PAINTED_WOODEN_SHIELD = registerShield("gundabad_painted_wooden_shield",
            (settings) -> new CustomShieldItem(ShieldTypesME.LIGHT_SHIELD, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MISTY_MOUNTAINS_GOBLINS.getValue())));
    public static final Item GUNDABAD_GREAT_EYE_PAINTED_WOODEN_SHIELD = registerShield("gundabad_great_eye_painted_wooden_shield",
            (settings) -> new CustomShieldItem(ShieldTypesME.LIGHT_SHIELD, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MISTY_MOUNTAINS_GOBLINS.getValue())));
    public static final Item GUNDABAD_PEAKS_PAINTED_WOODEN_SHIELD = registerShield("gundabad_peaks_painted_wooden_shield",
            (settings) -> new CustomShieldItem(ShieldTypesME.LIGHT_SHIELD, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MISTY_MOUNTAINS_GOBLINS.getValue())));

    public static final Item GUNDABAD_REINFORCED_SHIELD = registerShield("gundabad_reinforced_shield",
            (settings) -> new CustomShieldItem(ShieldTypesME.MEDIUM_SHIELD, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MISTY_MOUNTAINS_GOBLINS.getValue())));

    public static final Item GUNDABAD_HEAVY_SHIELD = registerShield("gundabad_heavy_shield",
            (settings) -> new CustomShieldItem(ShieldTypesME.HEAVY_SHIELD, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MISTY_MOUNTAINS_GOBLINS.getValue())));
    //endregion
    //endregion

    //region MORIA GOBS
    public static final Item MORIA_GOBLIN_FALCHION = registerItemWithModel("moria_goblin_falchion",
            (settings) -> new CustomSwordWeaponItem(ToolMaterialsME.STEEL, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MISTY_MOUNTAINS_GOBLINS.getValue())), true);
    public static final Item MORIA_GOBLIN_HOOKBLADE = registerItemWithModel("moria_goblin_hookblade",
            (settings) -> new CustomLongswordWeaponItem(ToolMaterialsME.STEEL, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MISTY_MOUNTAINS_GOBLINS.getValue())), true);
    public static final Item MORIA_GOBLIN_SHANK = registerItemWithModel("moria_goblin_shank",
            (settings) -> new CustomDaggerWeaponItem(ToolMaterialsME.STEEL, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MISTY_MOUNTAINS_GOBLINS.getValue())), false);
    public static final Item MORIA_GOBLIN_HOOKAXE = registerItemWithModel("moria_goblin_hookaxe",
            (settings) -> new CustomAxeWeaponItem(ToolMaterialsME.STEEL, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MISTY_MOUNTAINS_GOBLINS.getValue())), true);
    public static final Item MORIA_GOBLIN_SPEAR = registerItemWithSpearModel("moria_goblin_spear",
            (settings) -> new CustomSpearWeaponItem(ToolMaterialsME.STEEL, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MISTY_MOUNTAINS_GOBLINS.getValue())));

    public static final Item MORIA_GOBLIN_BOW = registerBigBowItem("moria_goblin_bow",
            (settings) -> new CustomBowWeaponItem(RangedWeaponTypesME.BOW, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MISTY_MOUNTAINS_GOBLINS.getValue())));

    public static final Item MORIA_GOBLINS_BUCKLER_SHIELD = registerShield("moria_goblins_buckler_shield",
            (settings) -> new CustomShieldItem(ShieldTypesME.MEDIUM_SHIELD, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MISTY_MOUNTAINS_GOBLINS.getValue())));

    public static final Item MORIA_GOBLINS_HEAVY_SHIELD = registerShield("moria_goblins_heavy_shield",
            (settings) -> new CustomShieldItem(ShieldTypesME.HEAVY_SHIELD, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MISTY_MOUNTAINS_GOBLINS.getValue())));
    //endregion

    //region GOBLIN TOWN
    public static final Item GOBLIN_TOWN_FALCHION = registerItemWithModel("goblin_town_falchion",
            (settings) -> new CustomSwordWeaponItem(ToolMaterialsME.CRUDE, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MISTY_MOUNTAINS_GOBLINS.getValue())), true);
    public static final Item GOBLIN_TOWN_SCIMITAR = registerItemWithModel("goblin_town_scimitar",
            (settings) -> new CustomLongswordWeaponItem(ToolMaterialsME.CRUDE, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MISTY_MOUNTAINS_GOBLINS.getValue())), true);
    public static final Item GOBLIN_TOWN_SHANK = registerItemWithModel("goblin_town_shank",
            (settings) -> new CustomDaggerWeaponItem(ToolMaterialsME.CRUDE, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MISTY_MOUNTAINS_GOBLINS.getValue())), false);
    public static final Item GOBLIN_TOWN_AXE = registerItemWithModel("goblin_town_axe",
            (settings) -> new CustomAxeWeaponItem(ToolMaterialsME.CRUDE, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MISTY_MOUNTAINS_GOBLINS.getValue())), true);
    public static final Item GOBLIN_TOWN_SPEAR = registerItemWithSpearModel("goblin_town_spear",
            (settings) -> new CustomSpearWeaponItem(ToolMaterialsME.CRUDE, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MISTY_MOUNTAINS_GOBLINS.getValue())));

    public static final Item GOBLIN_TOWN_BOW = registerBigBowItem("goblin_town_bow",
            (settings) -> new CustomBowWeaponItem(RangedWeaponTypesME.BOW, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MISTY_MOUNTAINS_GOBLINS.getValue())));

    public static final Item GOBLIN_TOWN_BONE_SHIELD = registerShield("goblin_town_bone_shield",
            (settings) -> new CustomShieldItem(ShieldTypesME.LIGHT_SHIELD, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MISTY_MOUNTAINS_GOBLINS.getValue())));

    public static final Item GOBLIN_TOWN_WOODEN_SHIELD = registerShield("goblin_town_wooden_shield",
            (settings) -> new CustomShieldItem(ShieldTypesME.MEDIUM_SHIELD, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MISTY_MOUNTAINS_GOBLINS.getValue())));
    public static final Item GOBLIN_BONE_TOWN_WOODEN_SHIELD = registerShield("goblin_town_bone_wooden_shield",
            (settings) -> new CustomShieldItem(ShieldTypesME.MEDIUM_SHIELD, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MISTY_MOUNTAINS_GOBLINS.getValue())));
    public static final Item GOBLIN_LEATHER_TOWN_WOODEN_SHIELD = registerShield("goblin_town_leather_wooden_shield",
            (settings) -> new CustomShieldItem(ShieldTypesME.MEDIUM_SHIELD, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MISTY_MOUNTAINS_GOBLINS.getValue())));

    public static final Item GOBLIN_LEATHER_HEAVY_SHIELD = registerShield("goblin_town_heavy_shield",
            (settings) -> new CustomShieldItem(ShieldTypesME.HEAVY_SHIELD, settings), new Item.Settings()
                    .component(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.MISTY_MOUNTAINS_GOBLINS.getValue())));
    //endregion

    //region TROLL WEAPONS
    public static final Item TROLL_MACE = registerItemNoModel("troll_mace",
            (settings) -> new TrollWeaponItem(ToolMaterialsME.BURZUM_STEEL, settings));
    //endregion

    //region ARTEFACTS
    public static final Item DAGAMARTH = registerArtefact("dagamarth",
            (settings) -> new ArtefactCustomSwordWeaponItem(ToolMaterialsME.NOBLE_STEEL, settings), new Item.Settings().rarity(Rarity.EPIC)
                    .component(DataComponentTypes.LORE, ArtefactUtils.getArtefactLore("dagamarth")), true);
    public static final Item HERUGRIM = registerArtefact("herugrim",
            (settings) -> new ArtefactCustomSwordWeaponItem(ToolMaterialsME.NOBLE_STEEL, settings), new Item.Settings().rarity(Rarity.EPIC)
                    .component(DataComponentTypes.LORE, ArtefactUtils.getArtefactLore("herugrim")), true);
    public static final Item NAZGUL_SWORD = registerArtefact("nazgul_sword",
            (settings) -> new ArtefactCustomSwordWeaponItem(ToolMaterialsME.NOBLE_STEEL, settings), new Item.Settings().rarity(Rarity.EPIC)
                    .component(DataComponentTypes.LORE, ArtefactUtils.getArtefactLore("nazgul_sword")), true);

    public static final Item HAMMER_OF_HELM_HAMMERHAND = registerArtefact("hammer_of_helm_hammerhand",
            (settings) -> new ArtefactCustomAxeWeaponItem(ToolMaterialsME.NOBLE_STEEL, settings), new Item.Settings().rarity(Rarity.EPIC)
                    .component(DataComponentTypes.LORE, ArtefactUtils.getArtefactLore("hammer_of_helm_hammerhand")), true);
    public static final Item MACE_OF_SAURON = registerArtefact("mace_of_sauron",
            (settings) -> new ArtefactCustomAxeWeaponItem(ToolMaterialsME.NOBLE_STEEL, settings), new Item.Settings().rarity(Rarity.EPIC)
                    .component(DataComponentTypes.LORE, ArtefactUtils.getArtefactLore("mace_of_sauron")), true);

    public static final Item ANGUIREL = registerArtefact("anguirel",
            (settings) -> new ArtefactCustomLongswordWeaponItem(ToolMaterialsME.NOBLE_STEEL, settings), new Item.Settings().rarity(Rarity.EPIC)
                    .component(DataComponentTypes.LORE, ArtefactUtils.getArtefactLore("anguirel")), true);
    public static final Item GLAMDRING = registerArtefact("glamdring",
            (settings) ->  new ArtefactCustomGlowingLongswordWeaponItem(ToolMaterialsME.NOBLE_STEEL, settings), new Item.Settings().rarity(Rarity.EPIC)
                    .component(DataComponentTypes.LORE, ArtefactUtils.getArtefactLore("glamdring")), true);
    public static final Item LONG_FORGOTTEN_LONGSWORD = registerArtefact("long_forgotten_longsword",
            (settings) ->  new ArtefactCustomLongswordWeaponItem(ToolMaterialsME.NOBLE_STEEL, settings), new Item.Settings().rarity(Rarity.EPIC)
                    .component(DataComponentTypes.LORE, ArtefactUtils.getArtefactLore("long_forgotten_longsword")), true);
    public static final Item LONGSWORD_OF_ELDER_KINGS = registerArtefact("longsword_of_elder_kings",
            (settings) -> new ArtefactCustomLongswordWeaponItem(ToolMaterialsME.NOBLE_STEEL, settings), new Item.Settings().rarity(Rarity.EPIC)
                    .component(DataComponentTypes.LORE, ArtefactUtils.getArtefactLore("longsword_of_elder_kings")), true);
    public static final Item NARSIL = registerArtefact("narsil",
            (settings) -> new ArtefactCustomLongswordWeaponItem(ToolMaterialsME.NOBLE_STEEL, settings), new Item.Settings().rarity(Rarity.EPIC)
                    .component(DataComponentTypes.LORE, ArtefactUtils.getArtefactLore("narsil")), true);
    public static final Item NOLDORIN_LONGSWORD = registerArtefact("noldorin_longsword",
            (settings) ->  new ArtefactCustomLongswordWeaponItem(ToolMaterialsME.NOBLE_STEEL, settings), new Item.Settings().rarity(Rarity.EPIC)
                    .component(DataComponentTypes.LORE, ArtefactUtils.getArtefactLore("noldorin_longsword")), true);
    public static final Item ORCRIST = registerArtefact("orcrist",
            (settings) -> new ArtefactCustomGlowingLongswordWeaponItem(ToolMaterialsME.NOBLE_STEEL, settings), new Item.Settings().rarity(Rarity.EPIC)
                    .component(DataComponentTypes.LORE, ArtefactUtils.getArtefactLore("orcrist")), true);

    public static final Item BARROW_BLADE = registerArtefact("barrow_blade",
            (settings) -> new ArtefactCustomDaggerWeaponItem(ToolMaterialsME.NOBLE_STEEL, settings), new Item.Settings().rarity(Rarity.EPIC)
                    .component(DataComponentTypes.LORE, ArtefactUtils.getArtefactLore("barrow_blade")), false);
    public static final Item MORGUL_KNIFE = registerArtefact("morgul_knife",
            (settings) -> new MorgulKnifeItem(ToolMaterialsME.MORGUL_KNIFE, settings), new Item.Settings().rarity(Rarity.EPIC)
                    .component(DataComponentTypes.LORE, ArtefactUtils.getArtefactLore("morgul_knife")), false);
    public static final Item STING = registerArtefact("sting",
            (settings) -> new ArtefactCustomGlowingDaggerWeaponItem(ToolMaterialsME.NOBLE_STEEL, settings), new Item.Settings().rarity(Rarity.EPIC)
                    .component(DataComponentTypes.LORE, ArtefactUtils.getArtefactLore("sting")), false);

    public static final Item AEGLOS = registerItemWithSpearModel("aeglos",
            (settings) -> new ArtefactCustomSpearWeaponItem(ToolMaterialsME.NOBLE_STEEL, settings), new Item.Settings().rarity(Rarity.EPIC)
                    .component(DataComponentTypes.LORE, ArtefactUtils.getArtefactLore("aeglos")));

    public static final Item ANORTHANN = registerShield("anorthann",
            (settings) -> new ArtefactCustomShieldItem(ShieldTypesME.HEAVY_SHIELD, settings), new Item.Settings().rarity(Rarity.EPIC)
                    .component(DataComponentTypes.LORE, ArtefactUtils.getArtefactLore("anorthann")));
    public static final Item CUTHANN = registerShield("cuthann",
            (settings) -> new ArtefactCustomShieldItem(ShieldTypesME.HEAVY_SHIELD, settings), new Item.Settings().rarity(Rarity.EPIC)
                    .component(DataComponentTypes.LORE, ArtefactUtils.getArtefactLore("cuthann")));
    public static final Item SHIELD_OF_DURINS_GUARD = registerShield("shield_of_durins_guard",
            (settings) -> new ArtefactCustomShieldItem(ShieldTypesME.HEAVY_SHIELD, settings), new Item.Settings().rarity(Rarity.EPIC)
                    .component(DataComponentTypes.LORE, ArtefactUtils.getArtefactLore("shield_of_durins_guard")));
    public static final Item SHIELD_OF_THE_KING_UNDER_THE_MOUNTAIN = registerShield("shield_of_the_king_under_the_mountain",
            (settings) -> new ArtefactCustomShieldItem(ShieldTypesME.HEAVY_SHIELD, settings), new Item.Settings().rarity(Rarity.EPIC)
                    .component(DataComponentTypes.LORE, ArtefactUtils.getArtefactLore("shield_of_the_king_under_the_mountain")));
    //endregion

    public static final Item HELD_BANNER = registerItemNoModel("held_banner",
            HeldBannerItem::new);

    private static Item registerItemWithModel(String name, Function<Item.Settings, Item> factory, Item.Settings settings, boolean isDualModel) {
        Item item = (Item)factory.apply(settings.registryKey(ModBlocks.keyOfItem(name)));
        ItemGroupsME.WEAPONS_CONTENTS.add(item.getDefaultStack());
        if(isDualModel) {
            SimpleBigItemModel.items.add(item);
        } else {
            SimpleHandheldItemModel.items.add(item);
        }
        return registerItem(item, name);
    }

    private static Item registerItemNoModel(String name, Function<Item.Settings, Item> factory) {
        Item item = (Item)factory.apply(new Item.Settings().registryKey(ModBlocks.keyOfItem(name)));
        ItemGroupsME.WEAPONS_CONTENTS.add(item.getDefaultStack());
        return registerItem(item, name);
    }

    private static Item registerItemWithSpearModel(String name, Function<Item.Settings, Item> factory, Item.Settings settings) {
        Item item = (Item)factory.apply(settings.registryKey(ModBlocks.keyOfItem(name)));
        ItemGroupsME.WEAPONS_CONTENTS.add(item.getDefaultStack());
        spears.add(item);
        SimpleSpearModel.items.add(item);
        return registerItem(item, name);
    }

    private static Item registerBowItem(String name, Function<Item.Settings, Item> factory, Item.Settings settings) {
        Item item = (Item)factory.apply(settings.registryKey(ModBlocks.keyOfItem(name)));
        ItemGroupsME.WEAPONS_CONTENTS.add(item.getDefaultStack());
        SimpleBowItemModel.items.add(item);
        Bows.bows.add(item);
        return registerItem(item, name);
    }

    private static Item registerBigBowItem(String name, Function<Item.Settings, Item> factory, Item.Settings settings) {
        Item item = (Item)factory.apply(settings.registryKey(ModBlocks.keyOfItem(name)));
        ItemGroupsME.WEAPONS_CONTENTS.add(item.getDefaultStack());
        SimpleBigItemModel.bigBows.add(item);
        Bows.bows.add(item);
        return registerItem(item, name);
    }

    private static Item registerCrossbowItem(String name, Function<Item.Settings, Item> factory, Item.Settings settings) {
        Item item = (Item)factory.apply(settings.registryKey(ModBlocks.keyOfItem(name)));
        ItemGroupsME.WEAPONS_CONTENTS.add(item.getDefaultStack());
        SimpleCrossbowItemModel.items.add(item);
        Crossbows.crossbows.add(item);
        return registerItem(item, name);
    }

    private static Item registerArtefact(String name, Function<Item.Settings, Item> factory, Item.Settings settings, boolean isDualModel) {
        Item item = (Item)factory.apply(settings.registryKey(ModBlocks.keyOfItem(name)));
        ItemGroupsME.WEAPONS_CONTENTS.add(item.getDefaultStack());
        SimpleArtefactModels.artefacts.add(new SimpleArtefactModels.Artefact(item, isDualModel));
        return registerItem(item, name);
    }

    private static Item registerBannerShield(String name, Function<Item.Settings, Item> factory, Item.Settings settings) {
        Item item = (Item)factory.apply(settings.registryKey(ModBlocks.keyOfItem(name)));
        ItemGroupsME.WEAPONS_CONTENTS.add(item.getDefaultStack());
        return registerItem(item, name);
    }

    private static Item registerShield(String name, Function<Item.Settings, Item> factory, Item.Settings settings) {
        Item item = (Item)factory.apply(settings.registryKey(ModBlocks.keyOfItem(name)));
        ItemGroupsME.WEAPONS_CONTENTS.add(item.getDefaultStack());
        shields.add(item);
        return registerItem(item, name);
    }

    private static Item registerItem(Item item, String name){
        TranslationEntries.itemEntries.add(item);
        if (item instanceof CustomLongswordWeaponItem || item instanceof CustomSwordWeaponItem){
            WeaponEnchants.swords.add(item);
        } else if (item instanceof CustomDaggerWeaponItem){
            WeaponEnchants.daggers.add(item);
        } else if (item instanceof CustomAxeWeaponItem){
            WeaponEnchants.axes.add(item);
        } else if (item instanceof CustomSpearWeaponItem){
            WeaponEnchants.sharpWeapons.add(item);
        }
        RegistryAliases.aliases.add(new RegistryAliases.Alias(Registries.ITEM, name));
        return Registry.register(Registries.ITEM, ModBlocks.keyOfItem(name), item);
    }

    public static void registerModItems() {
        MiddleEarth.LOGGER.logDebugMsg("Registering Mod Weapon Items for " + MiddleEarth.MOD_ID);
    }
}
