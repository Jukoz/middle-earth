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
import net.jukoz.me.item.utils.WeaponTypes;
import net.jukoz.me.utils.LoggerUtil;
import net.minecraft.item.*;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModWeaponItems {
    private static final int SWORD_DAMAGE = 3;
    private static final int DAGGER_DAMAGE = 2;
    private static final int BATTLEAXE_DAMAGE = 6;
    private static final int SPEAR_DAMAGE = 2;
    private static final int PIKE_DAMAGE = 3;

    private static final float SWORD_ATTACKS_SPEED = -2.3f;
    private static final float DAGGER_ATTACKS_SPEED = -0.7f;
    private static final float BATTLEAXE_ATTACKS_SPEED = -3f;
    private static final float POLEARM_ATTACKS_SPEED = -3.4f;
    private static final float SPEAR_ATTACKS_SPEED = -2.5f;
    private static final float PIKE_ATTACKS_SPEED = -2.7f;

    private static final float DAGGER_REACH = -1.0f;
    private static final float TWO_HAND_REACH = 1.0f;
    private static final float PIKE_REACH = 2.0f;

    public static final Item WOODEN_DAGGER = registerItemWithModel("wooden_dagger",
            new ReachWeaponItem(ToolMaterials.WOOD, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".generic"), WeaponTypes.DAGGER), false);

    public static final Item STONE_DAGGER = registerItemWithModel("stone_dagger",
            new ReachWeaponItem(ToolMaterials.STONE, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".generic"), WeaponTypes.DAGGER), false);

    public static final Item IRON_DAGGER = registerItemWithModel("iron_dagger",
            new ReachWeaponItem(ToolMaterials.IRON, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".generic"), WeaponTypes.DAGGER), false);

    public static final Item GONDORIAN_SWORD = registerItemWithModel("gondorian_sword",
            new CustomSwordWeaponItem(ModToolMaterials.STEEL, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".gondor"), WeaponTypes.SWORD), true);
    public static final Item GONDORIAN_NOBLE_SWORD = registerItemWithModel("gondorian_noble_sword",
            new CustomSwordWeaponItem(ModToolMaterials.NOBLE_STEEL, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".gondor"), WeaponTypes.SWORD), true);
    public static final Item GONDORIAN_LONGSWORD = registerItemWithModel("gondorian_longsword",
            new ReachWeaponItem(ModToolMaterials.STEEL, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".gondor"), WeaponTypes.LONGSWORD), true);
    public static final Item GONDORIAN_NOBLE_LONGSWORD = registerItemWithModel("gondorian_noble_longsword",
            new ReachWeaponItem(ModToolMaterials.NOBLE_STEEL, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".gondor"), WeaponTypes.LONGSWORD), true);
    public static final Item GONDORIAN_DAGGER = registerItemWithModel("gondorian_dagger",
            new ReachWeaponItem(ModToolMaterials.STEEL, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".gondor"), WeaponTypes.DAGGER), false);
    public static final Item GONDORIAN_NOBLE_DAGGER = registerItemWithModel("gondorian_noble_dagger",
            new ReachWeaponItem(ModToolMaterials.NOBLE_STEEL, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".gondor"), WeaponTypes.DAGGER), false);
    public static final Item GONDORIAN_AXE = registerItemWithModel("gondorian_axe",
            new CustomAxeWeaponItem(ModToolMaterials.STEEL, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".gondor"), WeaponTypes.AXE), true);
    public static final Item GONDORIAN_NOBLE_AXE = registerItemWithModel("gondorian_noble_axe",
            new CustomAxeWeaponItem(ModToolMaterials.NOBLE_STEEL, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".gondor"), WeaponTypes.AXE), true);
    public static final Item GONDORIAN_SPEAR = registerItemWithSpearModel("gondorian_spear",
            new ReachWeaponItem(ModToolMaterials.STEEL, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".gondor"), WeaponTypes.SPEAR));
    public static final Item GONDORIAN_NOBLE_SPEAR = registerItemWithSpearModel("gondorian_noble_spear",
            new ReachWeaponItem(ModToolMaterials.NOBLE_STEEL, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".gondor"), WeaponTypes.SPEAR));

    public static final Item GONDORIAN_BOW = registerBowItem("gondorian_bow",
            new CustomBowWeaponItem(Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".gondor"), new Item.Settings().maxDamage(640)));

    public static final Item ROHIRRIC_SWORD = registerItemWithModel("rohirric_sword",
            new CustomSwordWeaponItem(ModToolMaterials.STEEL, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".rohan"), WeaponTypes.SWORD), true);
    public static final Item ROHIRRIC_NOBLE_SWORD = registerItemWithModel("rohirric_noble_sword",
            new CustomSwordWeaponItem(ModToolMaterials.NOBLE_STEEL, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".rohan"), WeaponTypes.SWORD), true);
    public static final Item ROHIRRIC_LONGSWORD = registerItemWithModel("rohirric_longsword",
            new ReachWeaponItem(ModToolMaterials.STEEL, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".rohan"), WeaponTypes.LONGSWORD), true);
    public static final Item ROHIRRIC_NOBLE_LONGSWORD = registerItemWithModel("rohirric_noble_longsword",
            new ReachWeaponItem(ModToolMaterials.NOBLE_STEEL, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".rohan"), WeaponTypes.LONGSWORD), true);
    public static final Item ROHIRRIC_DAGGER = registerItemWithModel("rohirric_dagger",
            new ReachWeaponItem(ModToolMaterials.STEEL, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".rohan"), WeaponTypes.DAGGER), false);
    public static final Item ROHIRRIC_NOBLE_DAGGER = registerItemWithModel("rohirric_noble_dagger",
            new ReachWeaponItem(ModToolMaterials.NOBLE_STEEL, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".rohan"), WeaponTypes.DAGGER), false);
    public static final Item ROHIRRIC_AXE = registerItemWithModel("rohirric_axe",
            new CustomAxeWeaponItem(ModToolMaterials.STEEL, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".rohan"), WeaponTypes.AXE), true);
    public static final Item ROHIRRIC_NOBLE_AXE = registerItemWithModel("rohirric_noble_axe",
            new CustomAxeWeaponItem(ModToolMaterials.NOBLE_STEEL, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".rohan"), WeaponTypes.AXE), true);
    public static final Item ROHIRRIC_SPEAR = registerItemWithSpearModel("rohirric_spear",
            new ReachWeaponItem(ModToolMaterials.STEEL, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".rohan"), WeaponTypes.SPEAR));
    public static final Item ROHIRRIC_NOBLE_SPEAR = registerItemWithSpearModel("rohirric_noble_spear",
            new ReachWeaponItem(ModToolMaterials.NOBLE_STEEL, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".rohan"), WeaponTypes.SPEAR));

    public static final Item ROHIRRIC_BOW = registerBowItem("rohirric_bow",
            new CustomBowWeaponItem(Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".rohan"), new Item.Settings().maxDamage(640)));

    public static final Item LORIEN_SWORD = registerItemWithModel("lorien_sword",
            new CustomSwordWeaponItem(ModToolMaterials.ELVEN_STEEL, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".lothlorien"), WeaponTypes.SWORD), true);
    public static final Item LORIEN_NOBLE_SWORD = registerItemWithModel("lorien_noble_sword",
            new CustomSwordWeaponItem(ModToolMaterials.NOBLE_ELVEN_STEEL, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".lothlorien"), WeaponTypes.SWORD), true);
    public static final Item LORIEN_GLAIVE = registerItemWithModel("lorien_glaive",
            new ReachWeaponItem(ModToolMaterials.ELVEN_STEEL, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".lothlorien"), WeaponTypes.LONGSWORD), true);
    public static final Item LORIEN_NOBLE_GLAIVE = registerItemWithModel("lorien_noble_glaive",
            new ReachWeaponItem(ModToolMaterials.NOBLE_ELVEN_STEEL, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".lothlorien"), WeaponTypes.LONGSWORD), true);
    public static final Item LORIEN_DAGGER = registerItemWithModel("lorien_dagger",
            new ReachWeaponItem(ModToolMaterials.ELVEN_STEEL, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".lothlorien"), WeaponTypes.DAGGER), false);
    public static final Item LORIEN_NOBLE_DAGGER = registerItemWithModel("lorien_noble_dagger",
            new ReachWeaponItem(ModToolMaterials.NOBLE_ELVEN_STEEL, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".lothlorien"), WeaponTypes.DAGGER), false);
    public static final Item LORIEN_AXE = registerItemWithModel("lorien_axe",
            new CustomAxeWeaponItem(ModToolMaterials.ELVEN_STEEL, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".lothlorien"), WeaponTypes.AXE), true);
    public static final Item LORIEN_NOBLE_AXE = registerItemWithModel("lorien_noble_axe",
            new CustomAxeWeaponItem(ModToolMaterials.NOBLE_ELVEN_STEEL, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".lothlorien"), WeaponTypes.AXE), true);
    public static final Item LORIEN_SPEAR = registerItemWithSpearModel("lorien_spear",
            new ReachWeaponItem(ModToolMaterials.ELVEN_STEEL, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".lothlorien"), WeaponTypes.SPEAR));
    public static final Item LORIEN_NOBLE_SPEAR = registerItemWithSpearModel("lorien_noble_spear",
            new ReachWeaponItem(ModToolMaterials.NOBLE_ELVEN_STEEL, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".lothlorien"), WeaponTypes.SPEAR));
    
    public static final Item LORIEN_BOW = registerBowItem("lorien_bow",
            new CustomBowWeaponItem(Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".lothlorien"), new Item.Settings().maxDamage(640)));

    public static final Item EREBOR_SWORD = registerItemWithModel("erebor_sword",
            new CustomSwordWeaponItem(ModToolMaterials.DWARVEN_STEEL, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".erebor"), WeaponTypes.SWORD), true);
    public static final Item EREBOR_NOBLE_SWORD = registerItemWithModel("erebor_noble_sword",
            new CustomSwordWeaponItem(ModToolMaterials.NOBLE_DWARVEN_STEEL, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".erebor"), WeaponTypes.SWORD), true);
    public static final Item EREBOR_LONGSWORD = registerItemWithModel("erebor_longsword",
            new ReachWeaponItem(ModToolMaterials.DWARVEN_STEEL, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".erebor"), WeaponTypes.LONGSWORD), true);
    public static final Item EREBOR_NOBLE_LONGSWORD = registerItemWithModel("erebor_noble_longsword",
            new ReachWeaponItem(ModToolMaterials.NOBLE_DWARVEN_STEEL, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".erebor"), WeaponTypes.LONGSWORD), true);
    public static final Item EREBOR_DAGGER = registerItemWithModel("erebor_dagger",
            new ReachWeaponItem(ModToolMaterials.DWARVEN_STEEL, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".erebor"), WeaponTypes.DAGGER), false);
    public static final Item EREBOR_NOBLE_DAGGER = registerItemWithModel("erebor_noble_dagger",
            new ReachWeaponItem(ModToolMaterials.NOBLE_DWARVEN_STEEL, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".erebor"), WeaponTypes.DAGGER), false);

    public static final Item URUK_HAI_SWORD = registerItemWithModel("uruk_hai_sword",
            new CustomSwordWeaponItem(ModToolMaterials.URUK_STEEL, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".isengard"), WeaponTypes.SWORD), true);
    public static final Item URUK_HAI_ELITE_SCIMITAR = registerItemWithModel("uruk_hai_elite_scimitar",
            new CustomSwordWeaponItem(ModToolMaterials.NOBLE_URUK_STEEL, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".isengard"), WeaponTypes.SWORD), true);
    public static final Item URUK_HAI_WARBLADE = registerItemWithModel("uruk_hai_warblade",
            new ReachWeaponItem(ModToolMaterials.URUK_STEEL, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".isengard"), WeaponTypes.LONGSWORD), true);
    public static final Item URUK_HAI_ELITE_WARBLADE = registerItemWithModel("uruk_hai_elite_warblade",
            new ReachWeaponItem(ModToolMaterials.NOBLE_URUK_STEEL, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".isengard"), WeaponTypes.LONGSWORD), true);
    public static final Item URUK_HAI_DAGGER = registerItemWithModel("uruk_hai_dagger",
            new ReachWeaponItem(ModToolMaterials.URUK_STEEL, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".isengard"), WeaponTypes.DAGGER), false);
    public static final Item URUK_HAI_ELITE_KNIFE = registerItemWithModel("uruk_hai_elite_knife",
            new ReachWeaponItem(ModToolMaterials.NOBLE_URUK_STEEL, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".isengard"), WeaponTypes.DAGGER), false);
    public static final Item URUK_HAI_SPEAR = registerItemWithSpearModel("uruk_hai_spear",
            new ReachWeaponItem(ModToolMaterials.URUK_STEEL, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".isengard"), WeaponTypes.SPEAR));
    public static final Item URUK_HAI_ELITE_SPEAR = registerItemWithSpearModel("uruk_hai_elite_spear",
            new ReachWeaponItem(ModToolMaterials.NOBLE_URUK_STEEL, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".isengard"), WeaponTypes.SPEAR));


    private static Item registerItemWithModel(String name, Item item, boolean isDualModel) {
        ModItemGroups.WEAPONS_CONTENTS.add(item.getDefaultStack());
        if(isDualModel) {
            SimpleBigItemModel.items.add(item);
        } else {
            SimpleHandheldItemModel.items.add(item);
        }
        return Items.register(new Identifier(MiddleEarth.MOD_ID, name), item);
    }

    private static Item registerItemWithSpearModel(String name, Item item) {
        ModItemGroups.WEAPONS_CONTENTS.add(item.getDefaultStack());
        SimpleSpearModel.items.add(item);

        return Items.register(new Identifier(MiddleEarth.MOD_ID, name), item);
    }

    private static Item registerBowItem(String name, Item item) {
        ModItemGroups.WEAPONS_CONTENTS.add(item.getDefaultStack());
        SimpleBowItemModel.items.add(item);
        return Items.register(new Identifier(MiddleEarth.MOD_ID, name), item);
    }

    public static void registerModItems() {
        LoggerUtil.getInstance().logDebugMsg("Registering Mod Weapon Items for " + MiddleEarth.MOD_ID);
    }
}
