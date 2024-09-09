package net.jukoz.me.item;

import net.jukoz.me.MiddleEarth;
import net.jukoz.me.datageneration.content.models.SimpleBigItemModel;
import net.jukoz.me.datageneration.content.models.SimpleBowItemModel;
import net.jukoz.me.datageneration.content.models.SimpleHandheldItemModel;
import net.jukoz.me.datageneration.content.models.SimpleSpearModel;
import net.jukoz.me.item.items.weapons.*;
import net.jukoz.me.item.items.weapons.ranged.CustomBowWeaponItem;
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
            new CustomSwordWeaponItem(ModToolMaterials.JADE), false);
    public static final Item BRONZE_SWORD = registerItemWithModel("bronze_sword",
            new CustomSwordWeaponItem(ModToolMaterials.BRONZE), false);
    public static final Item SLAG_FALCHION = registerItemWithModel("slag_falchion",
            new CustomSwordWeaponItem(ModToolMaterials.SLAG), true);
    public static final Item STEEL_SWORD = registerItemWithModel("steel_sword",
            new CustomSwordWeaponItem(ModToolMaterials.STEEL), false);

    public static final Item ORC_STEEL_SWORD = registerItemWithModel("orc_steel_sword",
            new CustomSwordWeaponItem(ModToolMaterials.SLAG), false);
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
    public static final Item SLAG_DAGGER = registerItemWithModel("slag_dagger",
            new CustomDaggerWeaponItem(ModToolMaterials.SLAG), false);
    public static final Item IRON_DAGGER = registerItemWithModel("iron_dagger",
            new CustomDaggerWeaponItem(ToolMaterials.IRON), false);
    public static final Item GOLDEN_DAGGER = registerItemWithModel("golden_dagger",
            new CustomDaggerWeaponItem(ToolMaterials.GOLD), false);
    public static final Item DIAMOND_DAGGER = registerItemWithModel("diamond_dagger",
            new CustomDaggerWeaponItem(ToolMaterials.DIAMOND), false);
    public static final Item NETHERITE_DAGGER = registerItemWithModel("netherite_dagger",
            new CustomDaggerWeaponItem(ToolMaterials.NETHERITE), false);

    public static final Item SLAG_LONGBLADE = registerItemWithModel("slag_longblade",
            new CustomLongswordWeaponItem(ModToolMaterials.SLAG), true);

    public static final Item WOODEN_SPEAR = registerItemWithSpearModel("wooden_spear",
            new CustomSpearWeaponItem(ToolMaterials.WOOD));
    public static final Item STONE_SPEAR = registerItemWithSpearModel("stone_spear",
            new CustomSpearWeaponItem(ToolMaterials.STONE));
    public static final Item JADE_SPEAR = registerItemWithSpearModel("jade_spear",
            new CustomSpearWeaponItem(ModToolMaterials.JADE));
    public static final Item BRONZE_SPEAR = registerItemWithSpearModel("bronze_spear",
            new CustomSpearWeaponItem(ModToolMaterials.BRONZE));
    public static final Item IRON_SPEAR = registerItemWithSpearModel("iron_spear",
            new CustomSpearWeaponItem(ToolMaterials.IRON));
    public static final Item STEEL_SPEAR = registerItemWithSpearModel("steel_spear",
            new CustomSpearWeaponItem(ModToolMaterials.STEEL));
    public static final Item ORC_STEEL_SPEAR = registerItemWithSpearModel("orc_steel_spear",
            new CustomSpearWeaponItem(ModToolMaterials.SLAG));
    public static final Item BURZUM_STEEL_SPEAR = registerItemWithSpearModel("burzul_steel_spear",
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
    //endregion

    //region MEN

    //region GONDOR
    public static final Item GONDORIAN_SWORD = registerItemWithModel("gondorian_sword",
            new CustomSwordWeaponItem(ModToolMaterials.STEEL, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".gondor")), true);
    public static final Item GONDORIAN_NOBLE_SWORD = registerItemWithModel("gondorian_noble_sword",
            new CustomSwordWeaponItem(ModToolMaterials.NOBLE_STEEL, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".gondor")), true);
    public static final Item GONDORIAN_LONGSWORD = registerItemWithModel("gondorian_longsword",
            new CustomLongswordWeaponItem(ModToolMaterials.STEEL, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".gondor")), true);
    public static final Item GONDORIAN_NOBLE_LONGSWORD = registerItemWithModel("gondorian_noble_longsword",
            new CustomLongswordWeaponItem(ModToolMaterials.NOBLE_STEEL, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".gondor")), true);
    public static final Item GONDORIAN_DAGGER = registerItemWithModel("gondorian_dagger",
            new CustomDaggerWeaponItem(ModToolMaterials.STEEL, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".gondor")), false);
    public static final Item GONDORIAN_NOBLE_DAGGER = registerItemWithModel("gondorian_noble_dagger",
            new CustomDaggerWeaponItem(ModToolMaterials.NOBLE_STEEL, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".gondor")), false);
    public static final Item GONDORIAN_AXE = registerItemWithModel("gondorian_axe",
            new CustomAxeWeaponItem(ModToolMaterials.STEEL, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".gondor")), true);
    public static final Item GONDORIAN_NOBLE_AXE = registerItemWithModel("gondorian_noble_axe",
            new CustomAxeWeaponItem(ModToolMaterials.NOBLE_STEEL, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".gondor")), true);
    public static final Item GONDORIAN_SPEAR = registerItemWithSpearModel("gondorian_spear",
            new CustomSpearWeaponItem(ModToolMaterials.STEEL, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".gondor")));
    public static final Item GONDORIAN_NOBLE_SPEAR = registerItemWithSpearModel("gondorian_noble_spear",
            new CustomSpearWeaponItem(ModToolMaterials.NOBLE_STEEL, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".gondor")));

    public static final Item GONDORIAN_BOW = registerBowItem("gondorian_bow",
            new CustomBowWeaponItem(Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".gondor"), new Item.Settings().maxDamage(640)));
    //endregion

    //region ROHAN
    public static final Item ROHIRRIC_SWORD = registerItemWithModel("rohirric_sword",
            new CustomSwordWeaponItem(ModToolMaterials.STEEL, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".rohan")), true);
    public static final Item ROHIRRIC_NOBLE_SWORD = registerItemWithModel("rohirric_noble_sword",
            new CustomSwordWeaponItem(ModToolMaterials.NOBLE_STEEL, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".rohan")), true);
    public static final Item ROHIRRIC_LONGSWORD = registerItemWithModel("rohirric_longsword",
            new CustomLongswordWeaponItem(ModToolMaterials.STEEL, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".rohan")), true);
    public static final Item ROHIRRIC_NOBLE_LONGSWORD = registerItemWithModel("rohirric_noble_longsword",
            new CustomLongswordWeaponItem(ModToolMaterials.NOBLE_STEEL, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".rohan")), true);
    public static final Item ROHIRRIC_DAGGER = registerItemWithModel("rohirric_dagger",
            new CustomDaggerWeaponItem(ModToolMaterials.STEEL, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".rohan")), false);
    public static final Item ROHIRRIC_NOBLE_DAGGER = registerItemWithModel("rohirric_noble_dagger",
            new CustomDaggerWeaponItem(ModToolMaterials.NOBLE_STEEL, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".rohan")), false);
    public static final Item ROHIRRIC_AXE = registerItemWithModel("rohirric_axe",
            new CustomAxeWeaponItem(ModToolMaterials.STEEL, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".rohan")), true);
    public static final Item ROHIRRIC_NOBLE_AXE = registerItemWithModel("rohirric_noble_axe",
            new CustomAxeWeaponItem(ModToolMaterials.NOBLE_STEEL, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".rohan")), true);
    public static final Item ROHIRRIC_SPEAR = registerItemWithSpearModel("rohirric_spear",
            new CustomSpearWeaponItem(ModToolMaterials.STEEL, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".rohan")));
    public static final Item ROHIRRIC_NOBLE_SPEAR = registerItemWithSpearModel("rohirric_noble_spear",
            new CustomSpearWeaponItem(ModToolMaterials.NOBLE_STEEL, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".rohan")));

    public static final Item ROHIRRIC_BOW = registerBowItem("rohirric_bow",
            new CustomBowWeaponItem(Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".rohan"), new Item.Settings().maxDamage(640)));
    //endregion

    //region DALE
    public static final Item DALISH_SWORD = registerItemWithModel("dalish_sword",
            new CustomSwordWeaponItem(ModToolMaterials.STEEL, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".dale")), true);
    public static final Item DALISH_HEYDAY_SWORD = registerItemWithModel("dalish_heyday_sword",
            new CustomSwordWeaponItem(ModToolMaterials.STEEL, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".dale")), true);
    public static final Item DALISH_NOBLE_SWORD = registerItemWithModel("dalish_noble_sword",
            new CustomSwordWeaponItem(ModToolMaterials.NOBLE_STEEL, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".dale")), true);
    public static final Item DALISH_HEYDAY_SCIMITAR = registerItemWithModel("dalish_heyday_scimitar",
            new CustomSwordWeaponItem(ModToolMaterials.STEEL, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".dale")), true);
    public static final Item DALISH_LONGSWORD = registerItemWithModel("dalish_longsword",
            new CustomLongswordWeaponItem(ModToolMaterials.STEEL, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".dale")), true);
    public static final Item DALISH_HEYDAY_LONGSWORD = registerItemWithModel("dalish_heyday_longsword",
            new CustomLongswordWeaponItem(ModToolMaterials.STEEL, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".dale")), true);
    public static final Item DALISH_NOBLE_LONGSWORD = registerItemWithModel("dalish_noble_longsword",
            new CustomLongswordWeaponItem(ModToolMaterials.NOBLE_STEEL, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".dale")), true);
    public static final Item DALISH_DAGGER = registerItemWithModel("dalish_dagger",
            new CustomDaggerWeaponItem(ModToolMaterials.STEEL, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".dale")), false);
    public static final Item DALISH_HEYDAY_DAGGER = registerItemWithModel("dalish_heyday_dagger",
            new CustomDaggerWeaponItem(ModToolMaterials.STEEL, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".dale")), false);
    public static final Item DALISH_NOBLE_DAGGER = registerItemWithModel("dalish_noble_dagger",
            new CustomDaggerWeaponItem(ModToolMaterials.NOBLE_STEEL, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".dale")), false);
    public static final Item DALISH_AXE = registerItemWithModel("dalish_axe",
            new CustomAxeWeaponItem(ModToolMaterials.STEEL, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".dale")), true);
    public static final Item DALISH_HEYDAY_AXE = registerItemWithModel("dalish_heyday_axe",
            new CustomAxeWeaponItem(ModToolMaterials.STEEL, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".dale")), true);
    public static final Item DALISH_NOBLE_AXE = registerItemWithModel("dalish_noble_axe",
            new CustomAxeWeaponItem(ModToolMaterials.NOBLE_STEEL, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".dale")), true);
    //endregion

    //endregion

    //region LOTHLORIEN
    public static final Item LORIEN_SWORD = registerItemWithModel("lorien_sword",
            new CustomSwordWeaponItem(ModToolMaterials.EDHEL_STEEL, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".lothlorien")), true);
    public static final Item LORIEN_NOBLE_SWORD = registerItemWithModel("lorien_noble_sword",
            new CustomSwordWeaponItem(ModToolMaterials.NOBLE_EDHEL_STEEL, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".lothlorien")), true);
    public static final Item LORIEN_GLAIVE = registerItemWithModel("lorien_glaive",
            new CustomLongswordWeaponItem(ModToolMaterials.EDHEL_STEEL, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".lothlorien")), true);
    public static final Item LORIEN_NOBLE_GLAIVE = registerItemWithModel("lorien_noble_glaive",
            new CustomLongswordWeaponItem(ModToolMaterials.NOBLE_EDHEL_STEEL, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".lothlorien")), true);
    public static final Item LORIEN_DAGGER = registerItemWithModel("lorien_dagger",
            new CustomDaggerWeaponItem(ModToolMaterials.EDHEL_STEEL, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".lothlorien")), false);
    public static final Item LORIEN_NOBLE_DAGGER = registerItemWithModel("lorien_noble_dagger",
            new CustomDaggerWeaponItem(ModToolMaterials.NOBLE_EDHEL_STEEL, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".lothlorien")), false);
    public static final Item LORIEN_AXE = registerItemWithModel("lorien_axe",
            new CustomAxeWeaponItem(ModToolMaterials.EDHEL_STEEL, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".lothlorien")), true);
    public static final Item LORIEN_NOBLE_AXE = registerItemWithModel("lorien_noble_axe",
            new CustomAxeWeaponItem(ModToolMaterials.NOBLE_EDHEL_STEEL, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".lothlorien")), true);
    public static final Item LORIEN_SPEAR = registerItemWithSpearModel("lorien_spear",
            new CustomSpearWeaponItem(ModToolMaterials.EDHEL_STEEL, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".lothlorien")));
    public static final Item LORIEN_NOBLE_SPEAR = registerItemWithSpearModel("lorien_noble_spear",
            new CustomSpearWeaponItem(ModToolMaterials.NOBLE_EDHEL_STEEL, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".lothlorien")));

    public static final Item LORIEN_BOW = registerBowItem("lorien_bow",
            new CustomBowWeaponItem(Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".lothlorien"), new Item.Settings().maxDamage(640)));
    //endregion

    //region EREBOR
    public static final Item EREBOR_SWORD = registerItemWithModel("erebor_sword",
            new CustomSwordWeaponItem(ModToolMaterials.KHAZAD_STEEL, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".erebor")), true);
    public static final Item EREBOR_NOBLE_SWORD = registerItemWithModel("erebor_noble_sword",
            new CustomSwordWeaponItem(ModToolMaterials.NOBLE_KHAZAD_STEEL, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".erebor")), true);
    public static final Item EREBOR_LONGSWORD = registerItemWithModel("erebor_longsword",
            new CustomLongswordWeaponItem(ModToolMaterials.KHAZAD_STEEL, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".erebor")), true);
    public static final Item EREBOR_NOBLE_LONGSWORD = registerItemWithModel("erebor_noble_longsword",
            new CustomLongswordWeaponItem(ModToolMaterials.NOBLE_KHAZAD_STEEL, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".erebor")), true);
    public static final Item EREBOR_DAGGER = registerItemWithModel("erebor_dagger",
            new CustomDaggerWeaponItem(ModToolMaterials.KHAZAD_STEEL, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".erebor")), false);
    public static final Item EREBOR_NOBLE_DAGGER = registerItemWithModel("erebor_noble_dagger",
            new CustomDaggerWeaponItem(ModToolMaterials.NOBLE_KHAZAD_STEEL, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".erebor")), false);
    public static final Item EREBOR_AXE = registerItemWithModel("erebor_axe",
            new CustomAxeWeaponItem(ModToolMaterials.EDHEL_STEEL, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".erebor")), true);
    public static final Item EREBOR_NOBLE_AXE = registerItemWithModel("erebor_noble_axe",
            new CustomAxeWeaponItem(ModToolMaterials.NOBLE_EDHEL_STEEL, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".erebor")), true);
    //endregion

    //region ISENGARD
    public static final Item URUK_HAI_SWORD = registerItemWithModel("uruk_hai_sword",
            new CustomSwordWeaponItem(ModToolMaterials.BURZUM_STEEL, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".isengard")), true);
    public static final Item URUK_HAI_ELITE_SCIMITAR = registerItemWithModel("uruk_hai_elite_scimitar",
            new CustomSwordWeaponItem(ModToolMaterials.NOBLE_BURZUM_STEEL, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".isengard")), true);
    public static final Item URUK_HAI_WARBLADE = registerItemWithModel("uruk_hai_warblade",
            new CustomLongswordWeaponItem(ModToolMaterials.BURZUM_STEEL, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".isengard")), true);
    public static final Item URUK_HAI_ELITE_WARBLADE = registerItemWithModel("uruk_hai_elite_warblade",
            new CustomLongswordWeaponItem(ModToolMaterials.NOBLE_BURZUM_STEEL, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".isengard")), true);
    public static final Item URUK_HAI_DAGGER = registerItemWithModel("uruk_hai_dagger",
            new CustomDaggerWeaponItem(ModToolMaterials.BURZUM_STEEL, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".isengard")), false);
    public static final Item URUK_HAI_ELITE_KNIFE = registerItemWithModel("uruk_hai_elite_knife",
            new CustomDaggerWeaponItem(ModToolMaterials.NOBLE_BURZUM_STEEL, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".isengard")), false);
    public static final Item URUK_HAI_AXE = registerItemWithModel("uruk_hai_axe",
            new CustomAxeWeaponItem(ModToolMaterials.EDHEL_STEEL, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".isengard")), true);
    public static final Item URUK_HAI_ELITE_AXE = registerItemWithModel("uruk_hai_elite_axe",
            new CustomAxeWeaponItem(ModToolMaterials.NOBLE_EDHEL_STEEL, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".isengard")), true);
    public static final Item URUK_HAI_SPEAR = registerItemWithSpearModel("uruk_hai_spear",
            new CustomSpearWeaponItem(ModToolMaterials.BURZUM_STEEL, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".isengard")));
    public static final Item URUK_HAI_ELITE_SPEAR = registerItemWithSpearModel("uruk_hai_elite_spear",
            new CustomSpearWeaponItem(ModToolMaterials.NOBLE_BURZUM_STEEL, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".isengard")));
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
        LoggerUtil.logDebugMsg("Registering Mod Weapon Items for " + MiddleEarth.MOD_ID);
    }
}
