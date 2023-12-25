package net.jukoz.me.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.datageneration.content.models.SimpleBigItemModel;
import net.jukoz.me.datageneration.content.models.SimpleBowItemModel;
import net.jukoz.me.datageneration.content.models.SimpleHandheldItemModel;
import net.jukoz.me.item.items.RangeWeaponItem;
import net.jukoz.me.item.utils.ModItemGroups;
import net.jukoz.me.item.utils.ModToolMaterials;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.Arrays;
import java.util.List;

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

    private static final float DAGGER_REACH = 3.5f;
    private static final float TWO_HAND_REACH = 5.5f;
    private static final float PIKE_REACH = 6.5f;


    public static final Item ANDUIN_SWORD = registerItemWithModel("anduin_sword",
            new SwordItem(ToolMaterials.IRON, SWORD_DAMAGE, SWORD_ATTACKS_SPEED, new FabricItemSettings()), false);
    public static final Item ANDUIN_DAGGER = registerItemWithModel("anduin_dagger",
            new RangeWeaponItem(ToolMaterials.IRON, DAGGER_DAMAGE, DAGGER_ATTACKS_SPEED, DAGGER_REACH, new FabricItemSettings()), false);
    public static final Item ANDUIN_BATTLEAXE = registerItemWithModel("anduin_battleaxe",
            new AxeItem(ToolMaterials.IRON, BATTLEAXE_DAMAGE, BATTLEAXE_ATTACKS_SPEED, new FabricItemSettings()), true);
    public static final Item ANDUIN_POLEARM = registerItemWithModel("anduin_polearm",
            new RangeWeaponItem(ToolMaterials.IRON, BATTLEAXE_DAMAGE, POLEARM_ATTACKS_SPEED, PIKE_REACH, new FabricItemSettings()), true);
    public static final Item ANDUIN_SPEAR = registerItemWithModel("anduin_spear",
            new RangeWeaponItem(ToolMaterials.IRON, SPEAR_DAMAGE, SPEAR_ATTACKS_SPEED, TWO_HAND_REACH, new FabricItemSettings()), true);
    public static final Item ANDUIN_PIKE = registerItemWithModel("anduin_pike",
            new RangeWeaponItem(ToolMaterials.IRON, PIKE_DAMAGE, PIKE_ATTACKS_SPEED, PIKE_REACH, new FabricItemSettings()), true);

    public static final Item BRONZE_SWORD = registerItemWithModel("bronze_sword",
            new SwordItem(ModToolMaterials.BRONZE, SWORD_DAMAGE, SWORD_ATTACKS_SPEED, new FabricItemSettings()), false);
    public static final Item BRONZE_DAGGER = registerItemWithModel("bronze_dagger",
            new RangeWeaponItem(ModToolMaterials.BRONZE, DAGGER_DAMAGE, DAGGER_ATTACKS_SPEED, DAGGER_REACH, new FabricItemSettings()), false);
    public static final Item BRONZE_SPEAR = registerItemWithModel("bronze_spear",
            new RangeWeaponItem(ModToolMaterials.BRONZE, SPEAR_DAMAGE, SPEAR_ATTACKS_SPEED, TWO_HAND_REACH, new FabricItemSettings()), true);
    public static final Item BRONZE_PIKE = registerItemWithModel("bronze_pike",
            new RangeWeaponItem(ModToolMaterials.BRONZE, PIKE_DAMAGE, PIKE_ATTACKS_SPEED, PIKE_REACH, new FabricItemSettings()), true);

    public static final Item DALISH_SWORD = registerItemWithModel("dalish_sword",
            new SwordItem(ToolMaterials.IRON, SWORD_DAMAGE, SWORD_ATTACKS_SPEED, new FabricItemSettings()), false);
    public static final Item DALISH_DAGGER = registerItemWithModel("dalish_dagger",
            new RangeWeaponItem(ToolMaterials.IRON, DAGGER_DAMAGE, DAGGER_ATTACKS_SPEED, DAGGER_REACH, new FabricItemSettings()), false);
    public static final Item DALISH_POLEARM = registerItemWithModel("dalish_polearm",
            new RangeWeaponItem(ToolMaterials.IRON, BATTLEAXE_DAMAGE, POLEARM_ATTACKS_SPEED, PIKE_REACH, new FabricItemSettings()), true);
    public static final Item DALISH_SPEAR = registerItemWithModel("dalish_spear",
            new RangeWeaponItem(ToolMaterials.IRON, SPEAR_DAMAGE, SPEAR_ATTACKS_SPEED, TWO_HAND_REACH, new FabricItemSettings()), true);
    public static final Item DALISH_TRIDENT = registerItemWithModel("dalish_trident",
            new RangeWeaponItem(ToolMaterials.IRON, SPEAR_DAMAGE, SPEAR_ATTACKS_SPEED, TWO_HAND_REACH, new FabricItemSettings()), true);
    public static final Item DALISH_PIKE = registerItemWithModel("dalish_pike",
            new RangeWeaponItem(ToolMaterials.IRON, PIKE_DAMAGE, PIKE_ATTACKS_SPEED, PIKE_REACH, new FabricItemSettings()), true);
    public static final Item DALISH_BOW = registerBowItem("dalish_bow",
            new BowItem(new FabricItemSettings().maxDamage(640)));

    public static final Item DUNLAND_CLUB = registerItemWithModel("dunland_club",
            new SwordItem(ToolMaterials.IRON, SWORD_DAMAGE, SWORD_ATTACKS_SPEED, new FabricItemSettings()), false);
    public static final Item DUNLAND_DAGGER = registerItemWithModel("dunland_dagger",
            new RangeWeaponItem(ToolMaterials.IRON, DAGGER_DAMAGE, DAGGER_ATTACKS_SPEED, DAGGER_REACH, new FabricItemSettings()), false);
    public static final Item DUNLAND_SPEAR = registerItemWithModel("dunland_spear",
            new RangeWeaponItem(ToolMaterials.IRON, SPEAR_DAMAGE, SPEAR_ATTACKS_SPEED, TWO_HAND_REACH, new FabricItemSettings()), true);
    public static final Item DUNLAND_PITCHFORK = registerItemWithModel("dunland_pitchfork",
            new RangeWeaponItem(ToolMaterials.IRON, SPEAR_DAMAGE, SPEAR_ATTACKS_SPEED, TWO_HAND_REACH, new FabricItemSettings()), true);
    public static final Item DUNLAND_PIKE = registerItemWithModel("dunland_pike",
            new RangeWeaponItem(ToolMaterials.IRON, PIKE_DAMAGE, PIKE_ATTACKS_SPEED, PIKE_REACH, new FabricItemSettings()), true);

    public static final Item DWARVEN_SWORD = registerItemWithModel("dwarven_sword",
            new SwordItem(ModToolMaterials.DWARVEN, SWORD_DAMAGE, SWORD_ATTACKS_SPEED, new FabricItemSettings()), false);
    public static final Item DWARVEN_DAGGER = registerItemWithModel("dwarven_dagger",
            new RangeWeaponItem(ModToolMaterials.DWARVEN, DAGGER_DAMAGE, DAGGER_ATTACKS_SPEED, DAGGER_REACH, new FabricItemSettings()), false);
    public static final Item DWARVEN_BATTLEAXE = registerItemWithModel("dwarven_battleaxe",
            new AxeItem(ModToolMaterials.DWARVEN, BATTLEAXE_DAMAGE, BATTLEAXE_ATTACKS_SPEED, new FabricItemSettings()), true);
    public static final Item DWARVEN_SPEAR = registerItemWithModel("dwarven_spear",
            new RangeWeaponItem(ModToolMaterials.DWARVEN, SPEAR_DAMAGE, SPEAR_ATTACKS_SPEED, TWO_HAND_REACH, new FabricItemSettings()), true);
    public static final Item DWARVEN_PIKE = registerItemWithModel("dwarven_pike",
            new RangeWeaponItem(ModToolMaterials.DWARVEN, PIKE_DAMAGE, PIKE_ATTACKS_SPEED, PIKE_REACH, new FabricItemSettings()), true);

    public static final Item GONDOR_SWORD = registerItemWithModel("gondorian_sword",
            new SwordItem(ToolMaterials.IRON, SWORD_DAMAGE, SWORD_ATTACKS_SPEED, new FabricItemSettings()), false);
    public static final Item GONDOR_LONGSWORD = registerItemWithModel("gondorian_longsword",
            new RangeWeaponItem(ToolMaterials.IRON, SWORD_DAMAGE, SWORD_ATTACKS_SPEED - 0.2f, TWO_HAND_REACH, new FabricItemSettings()), true);
    public static final Item GONDOR_DAGGER = registerItemWithModel("gondorian_dagger",
            new RangeWeaponItem(ToolMaterials.IRON, DAGGER_DAMAGE, DAGGER_ATTACKS_SPEED, DAGGER_REACH, new FabricItemSettings()), false);
    public static final Item GONDOR_BATTLEAXE = registerItemWithModel("gondorian_battleaxe",
            new AxeItem(ToolMaterials.IRON, BATTLEAXE_DAMAGE, BATTLEAXE_ATTACKS_SPEED, new FabricItemSettings()), true);
    public static final Item GONDOR_SPEAR = registerItemWithModel("gondorian_spear",
            new RangeWeaponItem(ToolMaterials.IRON, SPEAR_DAMAGE, SPEAR_ATTACKS_SPEED, TWO_HAND_REACH, new FabricItemSettings()), true);
    public static final Item GONDOR_PIKE = registerItemWithModel("gondorian_pike",
            new RangeWeaponItem(ToolMaterials.IRON, PIKE_DAMAGE, PIKE_ATTACKS_SPEED, PIKE_REACH, new FabricItemSettings()), true);
    public static final Item GONDOR_BOW = registerBowItem("gondorian_bow",
            new BowItem(new FabricItemSettings().maxDamage(640)));

    public static final Item GUNDABAD_SCIMITAR = registerItemWithModel("gundabad_scimitar",
            new SwordItem(ModToolMaterials.ORCISH, SWORD_DAMAGE, SWORD_ATTACKS_SPEED, new FabricItemSettings()), false);
     public static final Item GUNDABAD_FALCHION = registerItemWithModel("gundabad_falchion",
            new SwordItem(ModToolMaterials.ORCISH, SWORD_DAMAGE, SWORD_ATTACKS_SPEED, new FabricItemSettings()), false);
    public static final Item GUNDABAD_DAGGER = registerItemWithModel("gundabad_dagger",
            new RangeWeaponItem(ModToolMaterials.ORCISH, DAGGER_DAMAGE, DAGGER_ATTACKS_SPEED, DAGGER_REACH, new FabricItemSettings()), false);
    public static final Item GUNDABAD_SPEAR = registerItemWithModel("gundabad_spear",
            new RangeWeaponItem(ModToolMaterials.ORCISH, SPEAR_DAMAGE, SPEAR_ATTACKS_SPEED, TWO_HAND_REACH, new FabricItemSettings()), true);
    public static final Item GUNDABAD_BARDICHE = registerItemWithModel("gundabad_bardiche",
            new RangeWeaponItem(ModToolMaterials.ORCISH, BATTLEAXE_DAMAGE, POLEARM_ATTACKS_SPEED, PIKE_REACH, new FabricItemSettings()), true);

    public static final Item LORIEN_SWORD = registerItemWithModel("lorien_sword",
            new SwordItem(ModToolMaterials.ELVEN, SWORD_DAMAGE, SWORD_ATTACKS_SPEED, new FabricItemSettings()), false);
    public static final Item LORIEN_DAGGER = registerItemWithModel("lorien_dagger",
            new RangeWeaponItem(ModToolMaterials.ELVEN, DAGGER_DAMAGE, DAGGER_ATTACKS_SPEED, DAGGER_REACH, new FabricItemSettings()), false);
    public static final Item LORIEN_SPEAR = registerItemWithModel("lorien_spear",
            new RangeWeaponItem(ModToolMaterials.ELVEN, SPEAR_DAMAGE, SPEAR_ATTACKS_SPEED, TWO_HAND_REACH, new FabricItemSettings()), true);
    public static final Item LORIEN_BATTLESTAFF = registerItemWithModel("lorien_battlestaff",
            new RangeWeaponItem(ModToolMaterials.ELVEN, 0, SPEAR_ATTACKS_SPEED, PIKE_REACH, new FabricItemSettings()), true);
    public static final Item LORIEN_PIKE = registerItemWithModel("lorien_pike",
            new RangeWeaponItem(ModToolMaterials.ELVEN, PIKE_DAMAGE, PIKE_ATTACKS_SPEED, PIKE_REACH, new FabricItemSettings()), true);
    public static final Item LORIEN_BOW = registerBowItem("lorien_bow",
            new BowItem(new FabricItemSettings().maxDamage(640)));

    public static final Item MORDOR_ORC_SCIMITAR = registerItemWithModel("mordor_orc_scimitar",
            new SwordItem(ModToolMaterials.ORCISH, SWORD_DAMAGE, SWORD_ATTACKS_SPEED, new FabricItemSettings()), false);
    public static final Item MORDOR_ORC_SWORD = registerItemWithModel("mordor_orc_sword",
            new SwordItem(ModToolMaterials.ORCISH, SWORD_DAMAGE, SWORD_ATTACKS_SPEED, new FabricItemSettings()), false);
    public static final Item MORDOR_ORC_DAGGER = registerItemWithModel("mordor_orc_dagger",
            new RangeWeaponItem(ModToolMaterials.ORCISH, DAGGER_DAMAGE, DAGGER_ATTACKS_SPEED, DAGGER_REACH, new FabricItemSettings()), false);
    public static final Item MORDOR_ORC_BATTLEAXE = registerItemWithModel("mordor_orc_battleaxe",
            new AxeItem(ModToolMaterials.ORCISH, BATTLEAXE_DAMAGE, BATTLEAXE_ATTACKS_SPEED, new FabricItemSettings()), true);
    public static final Item MORDOR_ORC_PIKE = registerItemWithModel("mordor_orc_pike",
            new RangeWeaponItem(ModToolMaterials.ORCISH, PIKE_DAMAGE, PIKE_ATTACKS_SPEED, PIKE_REACH, new FabricItemSettings()), true);

    public static final Item MORGUL_BLADE = registerItemWithModel("morgul_blade",
            new SwordItem(ModToolMaterials.MORGUL, SWORD_DAMAGE, SWORD_ATTACKS_SPEED + 0.1f, new FabricItemSettings()), false);

    public static final Item ROHIRRIC_SWORD = registerItemWithModel("rohirric_sword",
            new SwordItem(ToolMaterials.IRON, SWORD_DAMAGE, SWORD_ATTACKS_SPEED, new FabricItemSettings()), false);
    public static final Item ROHIRRIC_LONGSWORD = registerItemWithModel("rohirric_longsword",
            new RangeWeaponItem(ToolMaterials.IRON, SWORD_DAMAGE, SWORD_ATTACKS_SPEED - 0.2f, TWO_HAND_REACH, new FabricItemSettings()), true);
    public static final Item ROHIRRIC_DAGGER = registerItemWithModel("rohirric_dagger",
            new RangeWeaponItem(ToolMaterials.IRON, DAGGER_DAMAGE, DAGGER_ATTACKS_SPEED, DAGGER_REACH, new FabricItemSettings()), false);
    public static final Item ROHIRRIC_BATTLEAXE = registerItemWithModel("rohirric_battleaxe",
            new AxeItem(ToolMaterials.IRON, BATTLEAXE_DAMAGE, BATTLEAXE_ATTACKS_SPEED, new FabricItemSettings()), true);
    public static final Item ROHIRRIC_SPEAR = registerItemWithModel("rohirric_spear",
            new RangeWeaponItem(ToolMaterials.IRON, SPEAR_DAMAGE, SPEAR_ATTACKS_SPEED, TWO_HAND_REACH, new FabricItemSettings()), true);
    public static final Item ROHIRRIC_PIKE = registerItemWithModel("rohirric_pike",
            new RangeWeaponItem(ToolMaterials.IRON, PIKE_DAMAGE, PIKE_ATTACKS_SPEED, PIKE_REACH, new FabricItemSettings()), true);
    public static final Item ROHIRRIC_BOW = registerBowItem("rohirric_bow",
            new BowItem(new FabricItemSettings().maxDamage(640)));

    public static final Item UMBAR_SCIMITAR = registerItemWithModel("umbar_scimitar",
            new SwordItem(ToolMaterials.IRON, SWORD_DAMAGE, SWORD_ATTACKS_SPEED, new FabricItemSettings()), false);
    public static final Item UMBAR_DAGGER = registerItemWithModel("umbar_dagger",
            new RangeWeaponItem(ToolMaterials.IRON, DAGGER_DAMAGE, DAGGER_ATTACKS_SPEED, DAGGER_REACH, new FabricItemSettings()), false);
    public static final Item UMBAR_MACE = registerItemWithModel("umbar_mace",
            new RangeWeaponItem(ToolMaterials.IRON, BATTLEAXE_DAMAGE, BATTLEAXE_ATTACKS_SPEED, 5, new FabricItemSettings()), false);
    public static final Item UMBAR_SPEAR = registerItemWithModel("umbar_spear",
            new RangeWeaponItem(ToolMaterials.IRON, SPEAR_DAMAGE, SPEAR_ATTACKS_SPEED, TWO_HAND_REACH, new FabricItemSettings()), true);
    public static final Item UMBAR_PIKE = registerItemWithModel("umbar_pike",
            new RangeWeaponItem(ToolMaterials.IRON, PIKE_DAMAGE, PIKE_ATTACKS_SPEED, PIKE_REACH, new FabricItemSettings()), true);
    public static final Item UMBAR_BOW = registerBowItem("umbar_bow",
            new BowItem(new FabricItemSettings().maxDamage(640)));

    public static final Item STING = registerItemWithModel("sting",
            new RangeWeaponItem(ToolMaterials.IRON, SWORD_DAMAGE, SWORD_ATTACKS_SPEED + 1, DAGGER_REACH, new FabricItemSettings()), false);


    public static final Item IRON_DAGGER = registerItemWithModel("iron_dagger",
            new RangeWeaponItem(ToolMaterials.IRON, DAGGER_DAMAGE, DAGGER_ATTACKS_SPEED - 0.1f, DAGGER_REACH, new FabricItemSettings()), false);
    public static final Item STONE_DAGGER = registerItemWithModel("stone_dagger",
            new RangeWeaponItem(ToolMaterials.STONE, DAGGER_DAMAGE, DAGGER_ATTACKS_SPEED, DAGGER_REACH, new FabricItemSettings()), false);
    public static final Item WOODEN_DAGGER = registerItemWithModel("wooden_dagger",
            new RangeWeaponItem(ToolMaterials.WOOD, DAGGER_DAMAGE, DAGGER_ATTACKS_SPEED, DAGGER_REACH, new FabricItemSettings()), false);

    private static Item registerItemWithModel(String name, Item item, boolean isDualModel) {
        ModItemGroups.WEAPONS_CONTENTS.add(item.getDefaultStack());
        if(isDualModel) {
            SimpleBigItemModel.items.add(item);
        } else {
            SimpleHandheldItemModel.items.add(item);
        }
        return Registry.register(Registries.ITEM, new Identifier(MiddleEarth.MOD_ID, name), item);
    }

    private static Item registerBowItem(String name, Item item) {
        ModItemGroups.WEAPONS_CONTENTS.add(item.getDefaultStack());
        SimpleBowItemModel.items.add(item);
        return Registry.register(Registries.ITEM, new Identifier(MiddleEarth.MOD_ID, name), item);
    }

    public static void registerModItems() {
        MiddleEarth.LOGGER.debug("Registering Mod Weapon Items for " + MiddleEarth.MOD_ID);
    }
}
