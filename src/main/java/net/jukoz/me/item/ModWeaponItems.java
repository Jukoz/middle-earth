package net.jukoz.me.item;

import net.jukoz.me.MiddleEarth;
import net.jukoz.me.datageneration.content.models.SimpleBigItemModel;
import net.jukoz.me.datageneration.content.models.SimpleBowItemModel;
import net.jukoz.me.datageneration.content.models.SimpleHandheldItemModel;
import net.jukoz.me.datageneration.content.models.SimpleSpearModel;
import net.jukoz.me.item.items.CustomAxeWeaponItem;
import net.jukoz.me.item.items.CustomBowWeaponItem;
import net.jukoz.me.item.items.CustomSwordWeaponItem;
import net.jukoz.me.item.items.ReachWeaponItem;
import net.jukoz.me.item.utils.ModItemGroups;
import net.jukoz.me.item.utils.ModToolMaterials;
import net.jukoz.me.item.utils.ModWeaponTypes;
import net.jukoz.me.utils.LoggerUtil;
import net.minecraft.item.*;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModWeaponItems {

    //region GENERIC
    public static final Item JADE_SWORD = registerItemWithModel("jade_sword",
            new ReachWeaponItem(ModToolMaterials.JADE, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".generic"), ModWeaponTypes.SWORD), false);
    public static final Item BRONZE_SWORD = registerItemWithModel("bronze_sword",
            new ReachWeaponItem(ModToolMaterials.BRONZE, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".generic"), ModWeaponTypes.SWORD), false);
    public static final Item STEEL_SWORD = registerItemWithModel("steel_sword",
            new ReachWeaponItem(ModToolMaterials.STEEL, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".generic"), ModWeaponTypes.SWORD), false);
    public static final Item ORC_STEEL_SWORD = registerItemWithModel("orc_steel_sword",
            new ReachWeaponItem(ModToolMaterials.ORC_STEEL, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".generic"), ModWeaponTypes.SWORD), false);
    public static final Item URUK_STEEL_SWORD = registerItemWithModel("uruk_steel_sword",
            new ReachWeaponItem(ModToolMaterials.URUK_STEEL, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".generic"), ModWeaponTypes.SWORD), false);
    public static final Item ELVEN_STEEL_SWORD = registerItemWithModel("elven_steel_sword",
            new ReachWeaponItem(ModToolMaterials.ELVEN_STEEL, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".generic"), ModWeaponTypes.SWORD), false);
    public static final Item DWARVEN_STEEL_SWORD = registerItemWithModel("dwarven_steel_sword",
            new ReachWeaponItem(ModToolMaterials.DWARVEN_STEEL, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".generic"), ModWeaponTypes.SWORD), false);

    public static final Item WOODEN_DAGGER = registerItemWithModel("wooden_dagger",
            new ReachWeaponItem(ToolMaterials.WOOD, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".generic"), ModWeaponTypes.DAGGER), false);
    public static final Item STONE_DAGGER = registerItemWithModel("stone_dagger",
            new ReachWeaponItem(ToolMaterials.STONE, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".generic"), ModWeaponTypes.DAGGER), false);
    public static final Item BRONZE_DAGGER = registerItemWithModel("bronze_dagger",
            new ReachWeaponItem(ModToolMaterials.BRONZE, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".generic"), ModWeaponTypes.DAGGER), false);
    public static final Item IRON_DAGGER = registerItemWithModel("iron_dagger",
            new ReachWeaponItem(ToolMaterials.IRON, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".generic"), ModWeaponTypes.DAGGER), false);
    public static final Item GOLDEN_DAGGER = registerItemWithModel("golden_dagger",
            new ReachWeaponItem(ToolMaterials.GOLD, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".generic"), ModWeaponTypes.DAGGER), false);
    public static final Item DIAMOND_DAGGER = registerItemWithModel("diamond_dagger",
            new ReachWeaponItem(ToolMaterials.DIAMOND, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".generic"), ModWeaponTypes.DAGGER), false);
    public static final Item NETHERITE_DAGGER = registerItemWithModel("netherite_dagger",
            new ReachWeaponItem(ToolMaterials.NETHERITE, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".generic"), ModWeaponTypes.DAGGER), false);

    public static final Item WOODEN_SPEAR = registerItemWithSpearModel("wooden_spear",
            new ReachWeaponItem(ToolMaterials.WOOD, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".generic"), ModWeaponTypes.SPEAR));
    public static final Item STONE_SPEAR = registerItemWithSpearModel("stone_spear",
            new ReachWeaponItem(ToolMaterials.STONE, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".generic"), ModWeaponTypes.SPEAR));
    public static final Item JADE_SPEAR = registerItemWithSpearModel("jade_spear",
            new ReachWeaponItem(ModToolMaterials.JADE, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".generic"), ModWeaponTypes.SPEAR));
    public static final Item BRONZE_SPEAR = registerItemWithSpearModel("bronze_spear",
            new ReachWeaponItem(ModToolMaterials.BRONZE, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".generic"), ModWeaponTypes.SPEAR));
    public static final Item IRON_SPEAR = registerItemWithSpearModel("iron_spear",
            new ReachWeaponItem(ToolMaterials.IRON, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".generic"), ModWeaponTypes.SPEAR));
    public static final Item STEEL_SPEAR = registerItemWithSpearModel("steel_spear",
            new ReachWeaponItem(ModToolMaterials.STEEL, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".generic"), ModWeaponTypes.SPEAR));
    public static final Item ORC_STEEL_SPEAR = registerItemWithSpearModel("orc_steel_spear",
            new ReachWeaponItem(ModToolMaterials.ORC_STEEL, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".generic"), ModWeaponTypes.SPEAR));
    public static final Item URUK_STEEL_SPEAR = registerItemWithSpearModel("uruk_steel_spear",
            new ReachWeaponItem(ModToolMaterials.URUK_STEEL, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".generic"), ModWeaponTypes.SPEAR));
    public static final Item ELVEN_STEEL_SPEAR = registerItemWithSpearModel("elven_steel_spear",
            new ReachWeaponItem(ModToolMaterials.ELVEN_STEEL, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".generic"), ModWeaponTypes.SPEAR));
    public static final Item DWARVEN_STEEL_SPEAR = registerItemWithSpearModel("dwarven_steel_spear",
            new ReachWeaponItem(ModToolMaterials.DWARVEN_STEEL, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".generic"), ModWeaponTypes.SPEAR));
    public static final Item GOLDEN_SPEAR = registerItemWithSpearModel("golden_spear",
            new ReachWeaponItem(ToolMaterials.GOLD, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".generic"), ModWeaponTypes.SPEAR));
    public static final Item DIAMOND_SPEAR = registerItemWithSpearModel("diamond_spear",
            new ReachWeaponItem(ToolMaterials.DIAMOND, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".generic"), ModWeaponTypes.SPEAR));
    public static final Item NETHERITE_SPEAR = registerItemWithSpearModel("netherite_spear",
            new ReachWeaponItem(ToolMaterials.NETHERITE, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".generic"), ModWeaponTypes.SPEAR));
    //endregion
    
    //region MEN

    //region GONDOR
    public static final Item GONDORIAN_SWORD = registerItemWithModel("gondorian_sword",
            new CustomSwordWeaponItem(ModToolMaterials.STEEL, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".gondor"), ModWeaponTypes.SWORD), true);
    public static final Item GONDORIAN_NOBLE_SWORD = registerItemWithModel("gondorian_noble_sword",
            new CustomSwordWeaponItem(ModToolMaterials.NOBLE_STEEL, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".gondor"), ModWeaponTypes.SWORD), true);
    public static final Item GONDORIAN_LONGSWORD = registerItemWithModel("gondorian_longsword",
            new ReachWeaponItem(ModToolMaterials.STEEL, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".gondor"), ModWeaponTypes.LONGSWORD), true);
    public static final Item GONDORIAN_NOBLE_LONGSWORD = registerItemWithModel("gondorian_noble_longsword",
            new ReachWeaponItem(ModToolMaterials.NOBLE_STEEL, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".gondor"), ModWeaponTypes.LONGSWORD), true);
    public static final Item GONDORIAN_DAGGER = registerItemWithModel("gondorian_dagger",
            new ReachWeaponItem(ModToolMaterials.STEEL, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".gondor"), ModWeaponTypes.DAGGER), false);
    public static final Item GONDORIAN_NOBLE_DAGGER = registerItemWithModel("gondorian_noble_dagger",
            new ReachWeaponItem(ModToolMaterials.NOBLE_STEEL, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".gondor"), ModWeaponTypes.DAGGER), false);
    public static final Item GONDORIAN_AXE = registerItemWithModel("gondorian_axe",
            new CustomAxeWeaponItem(ModToolMaterials.STEEL, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".gondor"), ModWeaponTypes.AXE), true);
    public static final Item GONDORIAN_NOBLE_AXE = registerItemWithModel("gondorian_noble_axe",
            new CustomAxeWeaponItem(ModToolMaterials.NOBLE_STEEL, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".gondor"), ModWeaponTypes.AXE), true);
    public static final Item GONDORIAN_SPEAR = registerItemWithSpearModel("gondorian_spear",
            new ReachWeaponItem(ModToolMaterials.STEEL, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".gondor"), ModWeaponTypes.SPEAR));
    public static final Item GONDORIAN_NOBLE_SPEAR = registerItemWithSpearModel("gondorian_noble_spear",
            new ReachWeaponItem(ModToolMaterials.NOBLE_STEEL, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".gondor"), ModWeaponTypes.SPEAR));

    public static final Item GONDORIAN_BOW = registerBowItem("gondorian_bow",
            new CustomBowWeaponItem(Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".gondor"), new Item.Settings().maxDamage(640)));
    //endregion

    //region ROHAN
    public static final Item ROHIRRIC_SWORD = registerItemWithModel("rohirric_sword",
            new CustomSwordWeaponItem(ModToolMaterials.STEEL, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".rohan"), ModWeaponTypes.SWORD), true);
    public static final Item ROHIRRIC_NOBLE_SWORD = registerItemWithModel("rohirric_noble_sword",
            new CustomSwordWeaponItem(ModToolMaterials.NOBLE_STEEL, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".rohan"), ModWeaponTypes.SWORD), true);
    public static final Item ROHIRRIC_LONGSWORD = registerItemWithModel("rohirric_longsword",
            new ReachWeaponItem(ModToolMaterials.STEEL, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".rohan"), ModWeaponTypes.LONGSWORD), true);
    public static final Item ROHIRRIC_NOBLE_LONGSWORD = registerItemWithModel("rohirric_noble_longsword",
            new ReachWeaponItem(ModToolMaterials.NOBLE_STEEL, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".rohan"), ModWeaponTypes.LONGSWORD), true);
    public static final Item ROHIRRIC_DAGGER = registerItemWithModel("rohirric_dagger",
            new ReachWeaponItem(ModToolMaterials.STEEL, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".rohan"), ModWeaponTypes.DAGGER), false);
    public static final Item ROHIRRIC_NOBLE_DAGGER = registerItemWithModel("rohirric_noble_dagger",
            new ReachWeaponItem(ModToolMaterials.NOBLE_STEEL, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".rohan"), ModWeaponTypes.DAGGER), false);
    public static final Item ROHIRRIC_AXE = registerItemWithModel("rohirric_axe",
            new CustomAxeWeaponItem(ModToolMaterials.STEEL, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".rohan"), ModWeaponTypes.AXE), true);
    public static final Item ROHIRRIC_NOBLE_AXE = registerItemWithModel("rohirric_noble_axe",
            new CustomAxeWeaponItem(ModToolMaterials.NOBLE_STEEL, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".rohan"), ModWeaponTypes.AXE), true);
    public static final Item ROHIRRIC_SPEAR = registerItemWithSpearModel("rohirric_spear",
            new ReachWeaponItem(ModToolMaterials.STEEL, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".rohan"), ModWeaponTypes.SPEAR));
    public static final Item ROHIRRIC_NOBLE_SPEAR = registerItemWithSpearModel("rohirric_noble_spear",
            new ReachWeaponItem(ModToolMaterials.NOBLE_STEEL, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".rohan"), ModWeaponTypes.SPEAR));

    public static final Item ROHIRRIC_BOW = registerBowItem("rohirric_bow",
            new CustomBowWeaponItem(Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".rohan"), new Item.Settings().maxDamage(640)));
    //endregion

    //region DALE
    public static final Item DALISH_SWORD = registerItemWithModel("dalish_sword",
            new CustomSwordWeaponItem(ModToolMaterials.STEEL, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".dale"), ModWeaponTypes.SWORD), true);
    public static final Item DALISH_HEYDAY_SWORD = registerItemWithModel("dalish_heyday_sword",
            new CustomSwordWeaponItem(ModToolMaterials.STEEL, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".dale"), ModWeaponTypes.SWORD), true);
    public static final Item DALISH_NOBLE_SWORD = registerItemWithModel("dalish_noble_sword",
            new CustomSwordWeaponItem(ModToolMaterials.NOBLE_STEEL, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".dale"), ModWeaponTypes.SWORD), true);
    public static final Item DALISH_HEYDAY_SCIMITAR = registerItemWithModel("dalish_heyday_scimitar",
            new CustomSwordWeaponItem(ModToolMaterials.STEEL, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".dale"), ModWeaponTypes.SWORD), true);
    public static final Item DALISH_LONGSWORD = registerItemWithModel("dalish_longsword",
            new ReachWeaponItem(ModToolMaterials.STEEL, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".dale"), ModWeaponTypes.LONGSWORD), true);
    public static final Item DALISH_HEYDAY_LONGSWORD = registerItemWithModel("dalish_heyday_longsword",
            new ReachWeaponItem(ModToolMaterials.STEEL, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".dale"), ModWeaponTypes.LONGSWORD), true);
    public static final Item DALISH_NOBLE_LONGSWORD = registerItemWithModel("dalish_noble_longsword",
            new ReachWeaponItem(ModToolMaterials.NOBLE_STEEL, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".dale"), ModWeaponTypes.LONGSWORD), true);
    public static final Item DALISH_DAGGER = registerItemWithModel("dalish_dagger",
            new ReachWeaponItem(ModToolMaterials.STEEL, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".dale"), ModWeaponTypes.DAGGER), false);
    public static final Item DALISH_HEYDAY_DAGGER = registerItemWithModel("dalish_heyday_dagger",
            new ReachWeaponItem(ModToolMaterials.STEEL, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".dale"), ModWeaponTypes.DAGGER), false);
    public static final Item DALISH_NOBLE_DAGGER = registerItemWithModel("dalish_noble_dagger",
            new ReachWeaponItem(ModToolMaterials.NOBLE_STEEL, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".dale"), ModWeaponTypes.DAGGER), false);
    public static final Item DALISH_AXE = registerItemWithModel("dalish_axe",
            new CustomAxeWeaponItem(ModToolMaterials.STEEL, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".dale"), ModWeaponTypes.AXE), true);
    public static final Item DALISH_HEYDAY_AXE = registerItemWithModel("dalish_heyday_axe",
            new CustomAxeWeaponItem(ModToolMaterials.STEEL, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".dale"), ModWeaponTypes.AXE), true);
    public static final Item DALISH_NOBLE_AXE = registerItemWithModel("dalish_noble_axe",
            new CustomAxeWeaponItem(ModToolMaterials.NOBLE_STEEL, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".dale"), ModWeaponTypes.AXE), true);
    //endregion
    
    //endregion

    //region LOTHLORIEN
    public static final Item LORIEN_SWORD = registerItemWithModel("lorien_sword",
            new CustomSwordWeaponItem(ModToolMaterials.ELVEN_STEEL, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".lothlorien"), ModWeaponTypes.SWORD), true);
    public static final Item LORIEN_NOBLE_SWORD = registerItemWithModel("lorien_noble_sword",
            new CustomSwordWeaponItem(ModToolMaterials.NOBLE_ELVEN_STEEL, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".lothlorien"), ModWeaponTypes.SWORD), true);
    public static final Item LORIEN_GLAIVE = registerItemWithModel("lorien_glaive",
            new ReachWeaponItem(ModToolMaterials.ELVEN_STEEL, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".lothlorien"), ModWeaponTypes.LONGSWORD), true);
    public static final Item LORIEN_NOBLE_GLAIVE = registerItemWithModel("lorien_noble_glaive",
            new ReachWeaponItem(ModToolMaterials.NOBLE_ELVEN_STEEL, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".lothlorien"), ModWeaponTypes.LONGSWORD), true);
    public static final Item LORIEN_DAGGER = registerItemWithModel("lorien_dagger",
            new ReachWeaponItem(ModToolMaterials.ELVEN_STEEL, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".lothlorien"), ModWeaponTypes.DAGGER), false);
    public static final Item LORIEN_NOBLE_DAGGER = registerItemWithModel("lorien_noble_dagger",
            new ReachWeaponItem(ModToolMaterials.NOBLE_ELVEN_STEEL, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".lothlorien"), ModWeaponTypes.DAGGER), false);
    public static final Item LORIEN_AXE = registerItemWithModel("lorien_axe",
            new CustomAxeWeaponItem(ModToolMaterials.ELVEN_STEEL, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".lothlorien"), ModWeaponTypes.AXE), true);
    public static final Item LORIEN_NOBLE_AXE = registerItemWithModel("lorien_noble_axe",
            new CustomAxeWeaponItem(ModToolMaterials.NOBLE_ELVEN_STEEL, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".lothlorien"), ModWeaponTypes.AXE), true);
    public static final Item LORIEN_SPEAR = registerItemWithSpearModel("lorien_spear",
            new ReachWeaponItem(ModToolMaterials.ELVEN_STEEL, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".lothlorien"), ModWeaponTypes.SPEAR));
    public static final Item LORIEN_NOBLE_SPEAR = registerItemWithSpearModel("lorien_noble_spear",
            new ReachWeaponItem(ModToolMaterials.NOBLE_ELVEN_STEEL, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".lothlorien"), ModWeaponTypes.SPEAR));
    
    public static final Item LORIEN_BOW = registerBowItem("lorien_bow",
            new CustomBowWeaponItem(Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".lothlorien"), new Item.Settings().maxDamage(640)));
    //endregion

    //region EREBOR
    public static final Item EREBOR_SWORD = registerItemWithModel("erebor_sword",
            new CustomSwordWeaponItem(ModToolMaterials.DWARVEN_STEEL, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".erebor"), ModWeaponTypes.SWORD), true);
    public static final Item EREBOR_NOBLE_SWORD = registerItemWithModel("erebor_noble_sword",
            new CustomSwordWeaponItem(ModToolMaterials.NOBLE_DWARVEN_STEEL, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".erebor"), ModWeaponTypes.SWORD), true);
    public static final Item EREBOR_LONGSWORD = registerItemWithModel("erebor_longsword",
            new ReachWeaponItem(ModToolMaterials.DWARVEN_STEEL, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".erebor"), ModWeaponTypes.LONGSWORD), true);
    public static final Item EREBOR_NOBLE_LONGSWORD = registerItemWithModel("erebor_noble_longsword",
            new ReachWeaponItem(ModToolMaterials.NOBLE_DWARVEN_STEEL, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".erebor"), ModWeaponTypes.LONGSWORD), true);
    public static final Item EREBOR_DAGGER = registerItemWithModel("erebor_dagger",
            new ReachWeaponItem(ModToolMaterials.DWARVEN_STEEL, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".erebor"), ModWeaponTypes.DAGGER), false);
    public static final Item EREBOR_NOBLE_DAGGER = registerItemWithModel("erebor_noble_dagger",
            new ReachWeaponItem(ModToolMaterials.NOBLE_DWARVEN_STEEL, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".erebor"), ModWeaponTypes.DAGGER), false);
    public static final Item EREBOR_AXE = registerItemWithModel("erebor_axe",
            new CustomAxeWeaponItem(ModToolMaterials.ELVEN_STEEL, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".erebor"), ModWeaponTypes.AXE), true);
    public static final Item EREBOR_NOBLE_AXE = registerItemWithModel("erebor_noble_axe",
            new CustomAxeWeaponItem(ModToolMaterials.NOBLE_ELVEN_STEEL, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".erebor"), ModWeaponTypes.AXE), true);
    //endregion

    //region ISENGARD
    public static final Item URUK_HAI_SWORD = registerItemWithModel("uruk_hai_sword",
            new CustomSwordWeaponItem(ModToolMaterials.URUK_STEEL, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".isengard"), ModWeaponTypes.SWORD), true);
    public static final Item URUK_HAI_ELITE_SCIMITAR = registerItemWithModel("uruk_hai_elite_scimitar",
            new CustomSwordWeaponItem(ModToolMaterials.NOBLE_URUK_STEEL, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".isengard"), ModWeaponTypes.SWORD), true);
    public static final Item URUK_HAI_WARBLADE = registerItemWithModel("uruk_hai_warblade",
            new ReachWeaponItem(ModToolMaterials.URUK_STEEL, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".isengard"), ModWeaponTypes.LONGSWORD), true);
    public static final Item URUK_HAI_ELITE_WARBLADE = registerItemWithModel("uruk_hai_elite_warblade",
            new ReachWeaponItem(ModToolMaterials.NOBLE_URUK_STEEL, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".isengard"), ModWeaponTypes.LONGSWORD), true);
    public static final Item URUK_HAI_DAGGER = registerItemWithModel("uruk_hai_dagger",
            new ReachWeaponItem(ModToolMaterials.URUK_STEEL, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".isengard"), ModWeaponTypes.DAGGER), false);
    public static final Item URUK_HAI_ELITE_KNIFE = registerItemWithModel("uruk_hai_elite_knife",
            new ReachWeaponItem(ModToolMaterials.NOBLE_URUK_STEEL, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".isengard"), ModWeaponTypes.DAGGER), false);
    public static final Item URUK_HAI_AXE = registerItemWithModel("uruk_hai_axe",
            new CustomAxeWeaponItem(ModToolMaterials.ELVEN_STEEL, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".isengard"), ModWeaponTypes.AXE), true);
    public static final Item URUK_HAI_ELITE_AXE = registerItemWithModel("uruk_hai_elite_axe",
            new CustomAxeWeaponItem(ModToolMaterials.NOBLE_ELVEN_STEEL, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".isengard"), ModWeaponTypes.AXE), true);
    public static final Item URUK_HAI_SPEAR = registerItemWithSpearModel("uruk_hai_spear",
            new ReachWeaponItem(ModToolMaterials.URUK_STEEL, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".isengard"), ModWeaponTypes.SPEAR));
    public static final Item URUK_HAI_ELITE_SPEAR = registerItemWithSpearModel("uruk_hai_elite_spear",
            new ReachWeaponItem(ModToolMaterials.NOBLE_URUK_STEEL, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".isengard"), ModWeaponTypes.SPEAR));
    //endregion

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

    public static void registerModItems() {
        LoggerUtil.getInstance().logDebugMsg("Registering Mod Weapon Items for " + MiddleEarth.MOD_ID);
    }
}
