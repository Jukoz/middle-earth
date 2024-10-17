package net.jukoz.me.item;

import net.jukoz.me.MiddleEarth;
import net.jukoz.me.datageneration.content.models.SimpleBigItemModel;
import net.jukoz.me.datageneration.content.models.SimpleBowItemModel;
import net.jukoz.me.datageneration.content.models.SimpleHandheldItemModel;
import net.jukoz.me.datageneration.content.models.SimpleSpearModel;
import net.jukoz.me.item.items.weapons.*;
import net.jukoz.me.item.items.weapons.artefacts.*;
import net.jukoz.me.item.items.weapons.ranged.CustomBowWeaponItem;
import net.jukoz.me.item.utils.ModItemGroups;
import net.jukoz.me.item.utils.ModToolMaterials;
import net.jukoz.me.utils.LoggerUtil;
import net.jukoz.me.utils.ModFactions;
import net.jukoz.me.utils.ModSubFactions;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.ToolMaterials;
import net.minecraft.util.Identifier;

import java.util.Optional;

public class ModWeaponItems {

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
            new CustomSpearWeaponItem(ModToolMaterials.BURZUM_STEEL, ModFactions.ISENGARD));
    public static final Item MORDOR_ELITE_SPEAR = registerItemWithSpearModel("mordor_elite_spear",
            new CustomSpearWeaponItem(ModToolMaterials.NOBLE_BURZUM_STEEL, ModFactions.ISENGARD));

    public static final Item BLACK_NUMENOREAN_SWORD = registerItemWithModel("black_numenorean_sword",
            new CustomSwordWeaponItem(ModToolMaterials.NOBLE_STEEL, ModFactions.MORDOR), true);
    public static final Item BLACK_NUMENOREAN_LONGSWORD = registerItemWithModel("black_numenorean_longsword",
            new CustomLongswordWeaponItem(ModToolMaterials.NOBLE_STEEL, ModFactions.MORDOR), true);
    public static final Item BLACK_NUMENOREAN_DAGGER = registerItemWithModel("black_numenorean_dagger",
            new CustomDaggerWeaponItem(ModToolMaterials.NOBLE_STEEL, ModFactions.MORDOR), false);
    public static final Item BLACK_NUMENOREAN_AXE = registerItemWithModel("black_numenorean_axe",
            new CustomAxeWeaponItem(ModToolMaterials.NOBLE_STEEL, ModFactions.MORDOR), true);
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
    //endregion

    //region ARTEFACTS
    public static final Item GLAMDRING = registerItemNoModel("glamdring",
            new ArtefactCustomGlowingLongswordWeaponItem(ModToolMaterials.NOBLE_STEEL), false, true, true);
    public static final Item NARSIL = registerItemNoModel("narsil",
            new ArtefactCustomLongswordWeaponItem(ModToolMaterials.NOBLE_STEEL), true, true, false);
    public static final Item ORCRIST = registerItemNoModel("orcrist",
            new ArtefactCustomGlowingLongswordWeaponItem(ModToolMaterials.NOBLE_STEEL), false, true, true);

    public static final Item MORGUL_KNIFE = registerItemNoModel("morgul_knife",
            new MorgulKnifeItem(ModToolMaterials.MORGUL_KNIFE), false, false, false);
    public static final Item STING = registerItemNoModel("sting",
            new ArtefactCustomGlowingDaggerWeaponItem(ModToolMaterials.NOBLE_STEEL), false, false, true);
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

    private static Item registerItemNoModel(String name, Item item, boolean brokenModel, boolean isDualModel, boolean glowy) {
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

    public static void registerModItems() {
        LoggerUtil.logDebugMsg("Registering Mod Weapon Items for " + MiddleEarth.MOD_ID);
    }
}
