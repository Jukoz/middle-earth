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

import java.util.function.Supplier;

public enum ModArmorMaterials implements ArmorMaterial {

    GAMBESON("gambeson", 15, new int[] { 2, 5, 4, 2},
            0, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0.5f, 0.1F,
            () -> Ingredient.ofItems(Items.LEATHER), Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".generic"), null),

    CLOAK("cloak", 7, new int[] { 1, 3, 0, 0}, 0,
            SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0, 0.0F,
            () -> Ingredient.ofItems(Items.LEATHER), Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".generic"), null),

    TUNIC_CLOAK("tunic_cloak", 10, new int[] { 0, 4, 0, 0}, 0,
            SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0, 0.0F,
            () -> Ingredient.ofItems(Items.LEATHER), Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".generic"), null),

    FUR_CLOAK("fur_cloak", 7, new int[] { 1, 3, 0, 0},
            0, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0, 0.0F,
            () -> Ingredient.ofItems(Items.IRON_INGOT), Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".generic"), null),

    CHAINMAIL_FUR_CLOAK("chainmail_fur_cloak", 15, new int[] { 0, 5, 0, 0},
            0, SoundEvents.ITEM_ARMOR_EQUIP_CHAIN, 0, 0.0F,
            () -> Ingredient.ofItems(Items.IRON_INGOT), Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".generic"), null),

    NAZGUL_CLOAK("nazgul_cloak", 15, new int[] { 2, 5, 4, 2},
            0, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0, 0.0F,
            () -> Ingredient.ofItems(ModResourceItems.MORGUL_STEEL_INGOT), Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".mordor"), null),

    ROHAN_MAIL_ARMOR("rohan_mail", 15, new int[] { 2, 5, 4, 2},
            0, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0.5f, 0.1F,
            () -> Ingredient.ofItems(Items.IRON_INGOT), Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".rohan"), null),

    ROHAN_SCALE_ARMOR("rohan_scale", 25, new int[] { 2, 6, 5, 2},
            0, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 1, 0.1F,
            () -> Ingredient.ofItems(ModResourceItems.STEEL_INGOT), Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".rohan"), null),

    DURINS_FOLK_MAIL_ARMOR("durins_folk_mail", 15, new int[] { 2, 5, 4, 2},
            0, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0.5f, 0.1F,
            () -> Ingredient.ofItems(Items.IRON_INGOT), Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".longbeards"), Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".erebor")),

    DURINS_FOLK_SCALE_ARMOR("durins_folk_scale", 25, new int[] { 2, 6, 5, 2},
            0, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 1, 0.1F,
            () -> Ingredient.ofItems(ModResourceItems.DWARVEN_STEEL_INGOT), Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".longbeards"), Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".erebor")),

    DURINS_FOLK_PLATE_ARMOR("durins_folk_plate", 25, new int[] { 2, 6, 5, 2},
            0, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 1, 0.1F,
            () -> Ingredient.ofItems(ModResourceItems.DWARVEN_STEEL_INGOT), Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".longbeards"), Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".erebor")),

    DURINS_FOLK_COMMANDER_ARMOR("durins_folk_commander", 25, new int[] { 2, 6, 5, 2},
            0, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 1, 0.1F,
            () -> Ingredient.ofItems(ModResourceItems.DWARVEN_STEEL_INGOT), Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".longbeards"), Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".erebor")),

    LOTHLORIEN_MAIL_ARMOR("lothlorien_mail", 15, new int[] { 2, 5, 4, 2},
            0, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0.5f, 0.1F,
            () -> Ingredient.ofItems(Items.IRON_INGOT), Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".lothlorien"), null),

    LOTHLORIEN_SCALE_ARMOR("lothlorien_scale", 25, new int[] { 2, 6, 5, 2},
            0, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 1, 0.1F,
            () -> Ingredient.ofItems(ModResourceItems.ELVEN_STEEL_INGOT), Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".lothlorien"), null),

    LOTHLORIEN_PLATE_ARMOR("lothlorien_plate", 25, new int[] { 2, 6, 5, 2},
            0, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 1, 0.1F,
            () -> Ingredient.ofItems(ModResourceItems.ELVEN_STEEL_INGOT), Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".lothlorien"), null),

    MORDOR_URUK_PLATE_ARMOR("mordor_uruk_plate", 25, new int[] { 3, 7, 6, 3},
            0, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 2, 0.1F,
            () -> Ingredient.ofItems(ModResourceItems.URUK_STEEL_INGOT), Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".mordor"), null),

    MISTY_ORC_MAIL_ARMOR("misty_orc_mail", 15, new int[] { 2, 5, 4, 2},
            0, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0.5f, 0.1F,
            () -> Ingredient.ofItems(ModResourceItems.ORC_STEEL_INGOT), Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".misty_orcs"), Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".gundabad")),

    MISTY_URUK_SCALE_ARMOR("misty_uruk_scale", 25, new int[] { 2, 6, 5, 2},
            0, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 1, 0.1F,
            () -> Ingredient.ofItems(ModResourceItems.URUK_STEEL_INGOT), Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".misty_orcs"), Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".gundabad")),

    MISTY_URUK_PLATE_ARMOR("misty_uruk_plate", 25, new int[] { 3, 7, 6, 3},
            0, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 2, 0.1F,
            () -> Ingredient.ofItems(ModResourceItems.URUK_STEEL_INGOT), Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".misty_orcs"), Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".gundabad")),
    ;

    private final String name;
    private final int durabilityMultiplier;
    private final int[] protectionAmounts;
    private final int enchantability;
    private final SoundEvent equipSound;
    private final float toughness;
    private final float knockbackResistance;
    private final Supplier<Ingredient> repairIngredient;

    private final MutableText faction;
    private final MutableText subFaction;

    private static final int[] BASE_DURABILITY = { 11, 16, 15, 13 };

    ModArmorMaterials(String name, int durabilityMultiplier, int[] protectionAmounts,
                      int enchantability, SoundEvent equipSound, float toughness,
                      float knockbackResistance, Supplier<Ingredient> repairIngredient, MutableText faction, MutableText subFaction) {
        this.name = name;
        this.durabilityMultiplier = durabilityMultiplier;
        this.protectionAmounts = protectionAmounts;
        this.enchantability = enchantability;
        this.equipSound = equipSound;
        this.toughness = toughness;
        this.knockbackResistance = knockbackResistance;
        this.repairIngredient = repairIngredient;

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
}
