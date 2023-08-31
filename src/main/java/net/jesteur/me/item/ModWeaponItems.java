package net.jesteur.me.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.jesteur.me.MiddleEarth;
import net.jesteur.me.datageneration.content.models.SimpleBigItemModel;
import net.jesteur.me.datageneration.content.models.SimpleBowItemModel;
import net.jesteur.me.datageneration.content.models.SimpleHandheldItemModel;
import net.jesteur.me.item.items.SpearItem;
import net.jesteur.me.item.utils.ModItemGroups;
import net.jesteur.me.item.utils.ModToolMaterials;
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
    private static final float SPEAR_ATTACKS_SPEED = -2.5f;
    private static final float PIKE_ATTACKS_SPEED = -2.7f;

    public static final float DAGGER_RANGE = 2.5f;
    public static final float PIKE_RANGE = 8f;

    public static final Item ANDUIN_SWORD = registerItemWithModel("anduin_sword",
            new SwordItem(ToolMaterials.IRON, SWORD_DAMAGE, SWORD_ATTACKS_SPEED, new FabricItemSettings()), false);
    public static final Item ANDUIN_DAGGER = registerItemWithModel("anduin_dagger",
            new SwordItem(ToolMaterials.IRON, DAGGER_DAMAGE, DAGGER_ATTACKS_SPEED, new FabricItemSettings()), false);
    public static final Item ANDUIN_BATTLEAXE = registerItemWithModel("anduin_battleaxe",
            new AxeItem(ToolMaterials.IRON, BATTLEAXE_DAMAGE, BATTLEAXE_ATTACKS_SPEED, new FabricItemSettings()), true);
    public static final Item ANDUIN_POLEARM = registerItemWithModel("anduin_polearm",
            new AxeItem(ToolMaterials.IRON, BATTLEAXE_DAMAGE, BATTLEAXE_ATTACKS_SPEED, new FabricItemSettings()), true);
    public static final Item ANDUIN_SPEAR = registerItemWithModel("anduin_spear",
            new SpearItem(ToolMaterials.IRON, SPEAR_DAMAGE, SPEAR_ATTACKS_SPEED, new FabricItemSettings()), true);
    public static final Item ANDUIN_PIKE = registerItemWithModel("anduin_pike",
            new SwordItem(ToolMaterials.IRON, PIKE_DAMAGE, PIKE_ATTACKS_SPEED, new FabricItemSettings()), true);

    public static final Item DALISH_SWORD = registerItemWithModel("dalish_sword",
            new SwordItem(ToolMaterials.IRON, SWORD_DAMAGE, SWORD_ATTACKS_SPEED, new FabricItemSettings()), false);
    public static final Item DALISH_DAGGER = registerItemWithModel("dalish_dagger",
            new SwordItem(ToolMaterials.IRON, DAGGER_DAMAGE, DAGGER_ATTACKS_SPEED, new FabricItemSettings()), false);
    public static final Item DALISH_POLEARM = registerItemWithModel("dalish_polearm",
            new AxeItem(ToolMaterials.IRON, BATTLEAXE_DAMAGE, BATTLEAXE_ATTACKS_SPEED, new FabricItemSettings()), true);
    public static final Item DALISH_SPEAR = registerItemWithModel("dalish_spear",
            new SpearItem(ToolMaterials.IRON, SPEAR_DAMAGE, SPEAR_ATTACKS_SPEED, new FabricItemSettings()), true);
    public static final Item DALISH_PITCHFORK = registerItemWithModel("dalish_trident",
            new SwordItem(ToolMaterials.IRON, PIKE_DAMAGE, PIKE_ATTACKS_SPEED, new FabricItemSettings()), true);
    public static final Item DALISH_PIKE = registerItemWithModel("dalish_pike",
            new SwordItem(ToolMaterials.IRON, PIKE_DAMAGE, PIKE_ATTACKS_SPEED, new FabricItemSettings()), true);
    public static final Item DALISH_BOW = registerBowItem("dalish_bow",
            new BowItem(new FabricItemSettings().maxDamage(640)));

    public static final Item DUNLAND_CLUB = registerItemWithModel("dunland_club",
            new SwordItem(ToolMaterials.IRON, SWORD_DAMAGE, SWORD_ATTACKS_SPEED, new FabricItemSettings()), false);
    public static final Item DUNLAND_DAGGER = registerItemWithModel("dunland_dagger",
            new SwordItem(ToolMaterials.IRON, DAGGER_DAMAGE, DAGGER_ATTACKS_SPEED, new FabricItemSettings()), false);
    public static final Item DUNLAND_SPEAR = registerItemWithModel("dunland_spear",
            new SpearItem(ToolMaterials.IRON, SPEAR_DAMAGE, SPEAR_ATTACKS_SPEED, new FabricItemSettings()), true);
    public static final Item DUNLAND_PITCHFORK = registerItemWithModel("dunland_pitchfork",
            new SwordItem(ToolMaterials.IRON, PIKE_DAMAGE, PIKE_ATTACKS_SPEED, new FabricItemSettings()), true);
    public static final Item DUNLAND_PIKE = registerItemWithModel("dunland_pike",
            new SwordItem(ToolMaterials.IRON, PIKE_DAMAGE, PIKE_ATTACKS_SPEED, new FabricItemSettings()), true);

    public static final Item GONDOR_SWORD = registerItemWithModel("gondorian_sword",
            new SwordItem(ToolMaterials.IRON, SWORD_DAMAGE, SWORD_ATTACKS_SPEED, new FabricItemSettings()), false);
    public static final Item GONDOR_LONGSWORD = registerItemWithModel("gondorian_longsword",
            new SwordItem(ToolMaterials.IRON, SWORD_DAMAGE, SWORD_ATTACKS_SPEED, new FabricItemSettings()), true);
    public static final Item GONDOR_DAGGER = registerItemWithModel("gondorian_dagger",
            new SwordItem(ToolMaterials.IRON, DAGGER_DAMAGE, DAGGER_ATTACKS_SPEED, new FabricItemSettings()), false);
    public static final Item GONDOR_BATTLEAXE = registerItemWithModel("gondorian_battleaxe",
            new AxeItem(ToolMaterials.IRON, BATTLEAXE_DAMAGE, BATTLEAXE_ATTACKS_SPEED, new FabricItemSettings()), true);
    public static final Item GONDOR_SPEAR = registerItemWithModel("gondorian_spear",
            new SpearItem(ToolMaterials.IRON, SPEAR_DAMAGE, SPEAR_ATTACKS_SPEED, new FabricItemSettings()), true);
    public static final Item GONDOR_PIKE = registerItemWithModel("gondorian_pike",
            new SwordItem(ToolMaterials.IRON, PIKE_DAMAGE, PIKE_ATTACKS_SPEED, new FabricItemSettings()), true);
    public static final Item GONDOR_BOW = registerBowItem("gondorian_bow",
            new BowItem(new FabricItemSettings().maxDamage(640)));

    public static final Item LORIEN_SWORD = registerItemWithModel("lorien_sword",
            new SwordItem(ToolMaterials.IRON, SWORD_DAMAGE, SWORD_ATTACKS_SPEED, new FabricItemSettings()), false);
    public static final Item LORIEN_DAGGER = registerItemWithModel("lorien_dagger",
            new SwordItem(ToolMaterials.IRON, DAGGER_DAMAGE, DAGGER_ATTACKS_SPEED, new FabricItemSettings()), false);
    public static final Item LORIEN_SPEAR = registerItemWithModel("lorien_spear",
            new SpearItem(ToolMaterials.IRON, SPEAR_DAMAGE, SPEAR_ATTACKS_SPEED, new FabricItemSettings()), true);
    public static final Item LORIEN_BATTLESTAFF = registerItemWithModel("lorien_battlestaff",
            new SwordItem(ToolMaterials.IRON, SPEAR_DAMAGE, PIKE_ATTACKS_SPEED, new FabricItemSettings()), true);
    public static final Item LORIEN_PIKE = registerItemWithModel("lorien_pike",
            new SwordItem(ToolMaterials.IRON, PIKE_DAMAGE, PIKE_ATTACKS_SPEED, new FabricItemSettings()), true);
    public static final Item LORIEN_BOW = registerBowItem("lorien_bow",
            new BowItem(new FabricItemSettings().maxDamage(640)));

    public static final Item UMBAR_SCIMITAR = registerItemWithModel("umbar_scimitar",
            new SwordItem(ToolMaterials.IRON, SWORD_DAMAGE, SWORD_ATTACKS_SPEED, new FabricItemSettings()), false);
    public static final Item UMBAR_DAGGER = registerItemWithModel("umbar_dagger",
            new SwordItem(ToolMaterials.IRON, DAGGER_DAMAGE, DAGGER_ATTACKS_SPEED, new FabricItemSettings()), false);
    public static final Item UMBAR_SPEAR = registerItemWithModel("umbar_spear",
            new SpearItem(ToolMaterials.IRON, SPEAR_DAMAGE, SPEAR_ATTACKS_SPEED, new FabricItemSettings()), true);
    public static final Item UMBAR_PIKE = registerItemWithModel("umbar_pike",
            new SwordItem(ToolMaterials.IRON, PIKE_DAMAGE, PIKE_ATTACKS_SPEED, new FabricItemSettings()), true);
    public static final Item UMBAR_BOW = registerBowItem("umbar_bow",
            new BowItem(new FabricItemSettings().maxDamage(640)));

    public static final Item STING = registerItemWithModel("sting",
            new SwordItem(ToolMaterials.IRON, SWORD_DAMAGE, SWORD_ATTACKS_SPEED + 1, new FabricItemSettings()), false);

    public static final Item MORDOR_ORC_SWORD = registerItemWithModel("mordor_orc_sword",
            new SwordItem(ModToolMaterials.MORGUL, SWORD_DAMAGE, SWORD_ATTACKS_SPEED, new FabricItemSettings()), false);
    public static final Item DWARVEN_SWORD = registerItemWithModel("dwarven_sword",
            new SwordItem(ModToolMaterials.DWARVEN, SWORD_DAMAGE, SWORD_ATTACKS_SPEED, new FabricItemSettings()), false);
    private static final List<Item> pikes = Arrays.asList(GONDOR_PIKE);
    private static final List<Item> daggers = Arrays.asList(GONDOR_DAGGER);

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

    public static boolean isDagger(Item item) {
        return daggers.contains(item);
    }

    public static boolean isPike(Item item) {
        return pikes.contains(item);
    }
}
