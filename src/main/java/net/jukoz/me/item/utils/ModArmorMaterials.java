package net.jukoz.me.item.utils;

import net.jukoz.me.MiddleEarth;
import net.jukoz.me.item.ModResourceItems;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Lazy;

import java.util.EnumMap;
import java.util.function.Supplier;

public enum ModArmorMaterials implements ArmorMaterial {

    HOBBIT_SHIRRIF_HAT("hobbit_shirrif_hat", 1, SoundEvents.ITEM_ARMOR_EQUIP_IRON,
            () -> Ingredient.ofItems(Items.LEATHER), Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".shire"), null),

    GAMBESON("gambeson", 11, SoundEvents.ITEM_ARMOR_EQUIP_IRON,
            () -> Ingredient.ofItems(Items.LEATHER), Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".generic"), null),

    CLOAK("cloak", 1, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER,
            () -> Ingredient.ofItems(Items.LEATHER), Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".generic"), null),

    TUNIC_CLOAK("tunic_cloak", 1, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER,
            () -> Ingredient.ofItems(Items.LEATHER), Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".generic"), null),

    FUR_CLOAK("fur_cloak", 1, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER,
            () -> Ingredient.ofItems(Items.IRON_INGOT), Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".generic"), null),

    CHAINMAIL_FUR_CLOAK("chainmail_fur_cloak", 3, SoundEvents.ITEM_ARMOR_EQUIP_CHAIN,
            () -> Ingredient.ofItems(Items.IRON_INGOT), Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".generic"), null),

    NAZGUL_CLOAK("nazgul_cloak", 3, SoundEvents.ITEM_ARMOR_EQUIP_IRON,
            () -> Ingredient.ofItems(ModResourceItems.MORGUL_STEEL_INGOT), Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".mordor"), null),

    GONDORIAN_MAIL_ARMOR("gondorian_mail", 3, SoundEvents.ITEM_ARMOR_EQUIP_IRON,
            () -> Ingredient.ofItems(Items.IRON_INGOT), Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".gondorian"), null),

    GONDORIAN_PLATE_ARMOR("gondorian_plate", 5, SoundEvents.ITEM_ARMOR_EQUIP_IRON,
            () -> Ingredient.ofItems(ModResourceItems.STEEL_INGOT), Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".gondorian"), null),

    GONDORIAN_CITADEL_GUARD_ARMOR("gondorian_citadel_guard", 5, SoundEvents.ITEM_ARMOR_EQUIP_IRON,
            () -> Ingredient.ofItems(ModResourceItems.STEEL_INGOT), Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".gondorian"), null),

    GONDORIAN_FOUNTAIN_GUARD_ARMOR("gondorian_fountain_guard", 5, SoundEvents.ITEM_ARMOR_EQUIP_IRON,
            () -> Ingredient.ofItems(ModResourceItems.STEEL_INGOT), Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".gondorian"), null),

    ROHIRRIC_MAIL_ARMOR("rohirric_mail", 3, SoundEvents.ITEM_ARMOR_EQUIP_IRON,
            () -> Ingredient.ofItems(Items.IRON_INGOT), Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".rohirric"), null),

    ROHIRRIC_SCALE_ARMOR("rohirric_scale", 4, SoundEvents.ITEM_ARMOR_EQUIP_IRON,
            () -> Ingredient.ofItems(ModResourceItems.STEEL_INGOT), Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".rohirric"), null),

    ROHIRRIC_PLATE_ARMOR("rohirric_plate", 5, SoundEvents.ITEM_ARMOR_EQUIP_IRON,
            () -> Ingredient.ofItems(ModResourceItems.STEEL_INGOT), Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".rohirric"), null),

    LONGBEARD_MAIL_ARMOR("longbeard_mail", 3, SoundEvents.ITEM_ARMOR_EQUIP_IRON,
            () -> Ingredient.ofItems(Items.IRON_INGOT), Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".longbeards"), Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".erebor")),

    LONGBEARD_SCALE_ARMOR("longbeard_scale", 4, SoundEvents.ITEM_ARMOR_EQUIP_IRON,
            () -> Ingredient.ofItems(ModResourceItems.DWARVEN_STEEL_INGOT), Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".longbeards"), Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".erebor")),

    LONGBEARD_PLATE_ARMOR("longbeard_plate", 5, SoundEvents.ITEM_ARMOR_EQUIP_IRON,
            () -> Ingredient.ofItems(ModResourceItems.DWARVEN_STEEL_INGOT), Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".longbeards"), Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".erebor")),

    LONGBEARD_COMMANDER_ARMOR("longbeard_commander", 5, SoundEvents.ITEM_ARMOR_EQUIP_IRON,
            () -> Ingredient.ofItems(ModResourceItems.DWARVEN_STEEL_INGOT), Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".longbeards"), Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".erebor")),

    LORIEN_MAIL_ARMOR("lorien_mail", 3, SoundEvents.ITEM_ARMOR_EQUIP_IRON,
            () -> Ingredient.ofItems(Items.IRON_INGOT), Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".lorien"), null),

    LORIEN_SCALE_ARMOR("lorien_scale", 4, SoundEvents.ITEM_ARMOR_EQUIP_IRON,
            () -> Ingredient.ofItems(ModResourceItems.ELVEN_STEEL_INGOT), Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".lorien"), null),

    LORIEN_PLATE_ARMOR("lorien_plate", 5, SoundEvents.ITEM_ARMOR_EQUIP_IRON,
            () -> Ingredient.ofItems(ModResourceItems.ELVEN_STEEL_INGOT), Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".lorien"), null),

    MORDOR_ORC_MAIL_ARMOR("mordor_orc_mail", 3, SoundEvents.ITEM_ARMOR_EQUIP_IRON,
            () -> Ingredient.ofItems(ModResourceItems.ORC_STEEL_INGOT), Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".mordor"), Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".gundabad")),

    MORDOR_URUK_SCALE_ARMOR("mordor_uruk_scale",  4, SoundEvents.ITEM_ARMOR_EQUIP_IRON,
            () -> Ingredient.ofItems(ModResourceItems.URUK_STEEL_INGOT), Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".mordor"), null),

    MORDOR_URUK_PLATE_ARMOR("mordor_uruk_plate", 5, SoundEvents.ITEM_ARMOR_EQUIP_IRON,
            () -> Ingredient.ofItems(ModResourceItems.URUK_STEEL_INGOT), Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".mordor"), null),

    MISTY_ORC_MAIL_ARMOR("misty_orc_mail", 3, SoundEvents.ITEM_ARMOR_EQUIP_IRON,
            () -> Ingredient.ofItems(ModResourceItems.ORC_STEEL_INGOT), Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".misty_orcs"), Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".gundabad")),

    MISTY_URUK_SCALE_ARMOR("misty_uruk_scale", 4, SoundEvents.ITEM_ARMOR_EQUIP_IRON,
            () -> Ingredient.ofItems(ModResourceItems.URUK_STEEL_INGOT), Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".misty_orcs"), Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".gundabad")),

    MISTY_URUK_PLATE_ARMOR("misty_uruk_plate", 5, SoundEvents.ITEM_ARMOR_EQUIP_IRON,
            () -> Ingredient.ofItems(ModResourceItems.URUK_STEEL_INGOT), Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".misty_orcs"), Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".gundabad")),
    ;

    private final String name;
    private final SoundEvent equipSound;
    private final Supplier<Ingredient> repairIngredient;
    private final int durabilityMultiplier;
    private final int[] protectionAmounts;
    private final int enchantability;
    private final float toughness;
    private final float knockbackResistance;

    private final int tier;

    private final MutableText faction;
    private final MutableText subFaction;

    private static final int[] BASE_DURABILITY = { 11, 16, 15, 13 };

    ModArmorMaterials(String name, int tier, SoundEvent equipSound, Supplier<Ingredient> repairIngredient, MutableText faction, MutableText subFaction) {
        this.name = name;
        this.equipSound = equipSound;
        this.repairIngredient = repairIngredient;
        this.tier = tier;

        switch (tier) {
            case 1 -> {
                this.durabilityMultiplier = 7;
                this.protectionAmounts = new int[]{1, 3, 2, 1};
                this.toughness = 0.0f;
                this.knockbackResistance = 0.0f;
                this.enchantability = 10;
            }
            case 2 -> {
                this.durabilityMultiplier = 11;
                this.protectionAmounts = new int[]{1, 4, 3, 1};
                this.toughness = 0.0f;
                this.knockbackResistance = 0.0f;
                this.enchantability = 10;
            }
            case 3 -> {
                this.durabilityMultiplier = 15;
                this.protectionAmounts = new int[]{2, 5, 4, 2};
                this.toughness = 0.5f;
                this.knockbackResistance = 0.0f;
                this.enchantability = 10;
            }
            case 4 -> {
                this.durabilityMultiplier = 25;
                this.protectionAmounts = new int[]{2, 6, 5, 2};
                this.toughness = 1.0f;
                this.knockbackResistance = 0.1f;
                this.enchantability = 10;
            }
            case 5 -> {
                this.durabilityMultiplier = 35;
                this.protectionAmounts = new int[]{3, 7, 6, 3};
                this.toughness = 2.0f;
                this.knockbackResistance = 0.1f;
                this.enchantability = 10;
            }
            default -> {
                this.durabilityMultiplier = 1;
                this.protectionAmounts = new int[]{1, 1, 1, 1};
                this.toughness = 0.0f;
                this.knockbackResistance = 0.0f;
                this.enchantability = 1;
            }
        }

        this.faction = faction;
        this.subFaction = subFaction;
    }

    @Override
    public int getDurability(ArmorItem.Type type) {
        return BASE_DURABILITY[type.ordinal()] * this.durabilityMultiplier;
    }

    @Override
    public int getProtection(ArmorItem.Type type) {
        return protectionAmounts[type.ordinal()];
    }

    @Override
    public int getEnchantability() {
        return this.enchantability;
    }

    @Override
    public SoundEvent getEquipSound() {
        return this.equipSound;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return this.repairIngredient.get();
    }

    @Override
    public String getName() {
        return MiddleEarth.MOD_ID + ":" + this.name;
    }

    @Override
    public float getToughness() {
        return this.toughness;
    }

    @Override
    public float getKnockbackResistance() {
        return this.knockbackResistance;
    }

    public MutableText getFaction() {
        return this.faction;
    }

    public MutableText getSubFaction() {
        return this.subFaction;
    }

    public int getTier() {
        return this.tier;
    }
}
