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

    HOBBIT_SHIRRIFF_HAT("hobbit_shirriff_hat", 1, SoundEvents.ITEM_ARMOR_EQUIP_IRON,
            () -> Ingredient.ofItems(Items.LEATHER), Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".shire"), null),

    GAMBESON("gambeson", 2, SoundEvents.ITEM_ARMOR_EQUIP_IRON,
            () -> Ingredient.ofItems(Items.LEATHER), Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".generic"), null),

    CLOAK("cloak", 1, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER,
            () -> Ingredient.ofItems(Items.LEATHER), Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".generic"), null),

    TUNIC_CLOAK("tunic_cloak", 1, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER,
            () -> Ingredient.ofItems(Items.LEATHER), Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".generic"), null),

    FUR_CLOAK("fur_cloak", 1, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER,
            () -> Ingredient.ofItems(Items.IRON_INGOT), Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".generic"), null),

    CHAINMAIL_FUR_CLOAK("chainmail_fur_cloak", 3, SoundEvents.ITEM_ARMOR_EQUIP_CHAIN,
            () -> Ingredient.ofItems(Items.IRON_INGOT), Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".generic"), null),

    RUSTY_KETTLE_HAT("rusty_kettle_hat", 2, SoundEvents.ITEM_ARMOR_EQUIP_IRON,
            () -> Ingredient.ofItems(Items.IRON_INGOT), Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".generic"), null),

    KETTLE_HAT("kettle_hat", 2, SoundEvents.ITEM_ARMOR_EQUIP_IRON,
            () -> Ingredient.ofItems(Items.IRON_INGOT), Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".generic"), null),

    NAZGUL_CLOAK("nazgul_cloak", 3, SoundEvents.ITEM_ARMOR_EQUIP_IRON,
            () -> Ingredient.ofItems(ModResourceItems.MORGUL_STEEL_INGOT), Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".mordor"), null),

    GONDORIAN_MAIL_ARMOR("gondorian_mail", 3, SoundEvents.ITEM_ARMOR_EQUIP_IRON,
            () -> Ingredient.ofItems(Items.IRON_INGOT), Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".gondor"), null),

    GONDORIAN_REINFORCED_MAIL_ARMOR("gondorian_reinforced_mail", 4, SoundEvents.ITEM_ARMOR_EQUIP_IRON,
            () -> Ingredient.ofItems(Items.IRON_INGOT), Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".gondor"), null),

    GONDORIAN_PLATE_ARMOR("gondorian_plate", 5, SoundEvents.ITEM_ARMOR_EQUIP_IRON,
            () -> Ingredient.ofItems(ModResourceItems.STEEL_INGOT), Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".gondor"), null),

    GONDORIAN_CITADEL_GUARD_ARMOR("gondorian_citadel_guard", 5, SoundEvents.ITEM_ARMOR_EQUIP_IRON,
            () -> Ingredient.ofItems(ModResourceItems.STEEL_INGOT), Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".gondor"), null),

    GONDORIAN_FOUNTAIN_GUARD_ARMOR("gondorian_fountain_guard", 5, SoundEvents.ITEM_ARMOR_EQUIP_IRON,
            () -> Ingredient.ofItems(ModResourceItems.STEEL_INGOT), Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".gondor"), null),

    ROHIRRIC_MAIL_ARMOR("rohirric_mail", 3, SoundEvents.ITEM_ARMOR_EQUIP_IRON,
            () -> Ingredient.ofItems(Items.IRON_INGOT), Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".rohan"), null),

    ROHIRRIC_SCALE_ARMOR("rohirric_scale", 4, SoundEvents.ITEM_ARMOR_EQUIP_IRON,
            () -> Ingredient.ofItems(Items.IRON_INGOT), Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".rohan"), null),

    ROHIRRIC_ROYAL_GUARD_ARMOR("rohirric_royal_guard", 5, SoundEvents.ITEM_ARMOR_EQUIP_IRON,
            () -> Ingredient.ofItems(ModResourceItems.STEEL_INGOT), Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".rohan"), null),

    EORLING_MARSHALL_ARMOR("eorling_marshall", 5, SoundEvents.ITEM_ARMOR_EQUIP_IRON,
            () -> Ingredient.ofItems(ModResourceItems.STEEL_INGOT), Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".rohan"), null),

    HORSE_LORD_ARMOR("horse_lord", 5, SoundEvents.ITEM_ARMOR_EQUIP_IRON,
            () -> Ingredient.ofItems(ModResourceItems.STEEL_INGOT), Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".rohan"), null),

    DWARVEN_PARTISAN("dwarven_partisan", 2, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER,
            () -> Ingredient.ofItems(Items.LEATHER), Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".generic"), Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".dwarven")),

    EREBOR_MAIL_ARMOR("erebor_mail", 3, SoundEvents.ITEM_ARMOR_EQUIP_IRON,
            () -> Ingredient.ofItems(Items.IRON_INGOT), Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".longbeards"), Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".erebor")),

    EREBOR_SCALE_ARMOR("erebor_scale", 4, SoundEvents.ITEM_ARMOR_EQUIP_IRON,
            () -> Ingredient.ofItems(ModResourceItems.DWARVEN_STEEL_INGOT), Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".longbeards"), Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".erebor")),

    EREBOR_PLATE_ARMOR("erebor_plate", 5, SoundEvents.ITEM_ARMOR_EQUIP_IRON,
            () -> Ingredient.ofItems(ModResourceItems.DWARVEN_STEEL_INGOT), Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".longbeards"), Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".erebor")),

    EREBOR_COMMANDER_ARMOR("erebor_commander", 5, SoundEvents.ITEM_ARMOR_EQUIP_IRON,
            () -> Ingredient.ofItems(ModResourceItems.DWARVEN_STEEL_INGOT), Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".longbeards"), Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".erebor")),

    LORIEN_MAIL_ARMOR("lorien_mail", 3, SoundEvents.ITEM_ARMOR_EQUIP_IRON,
            () -> Ingredient.ofItems(Items.IRON_INGOT), Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".lothlorien"), null),

    LORIEN_SCALE_ARMOR("lorien_scale", 4, SoundEvents.ITEM_ARMOR_EQUIP_IRON,
            () -> Ingredient.ofItems(ModResourceItems.ELVEN_STEEL_INGOT), Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".lothlorien"), null),

    LORIEN_PLATE_ARMOR("lorien_plate", 5, SoundEvents.ITEM_ARMOR_EQUIP_IRON,
            () -> Ingredient.ofItems(ModResourceItems.ELVEN_STEEL_INGOT), Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".lothlorien"), null),

    LORIEN_COMMANDER_ARMOR("lorien_commander", 5, SoundEvents.ITEM_ARMOR_EQUIP_IRON,
            () -> Ingredient.ofItems(ModResourceItems.ELVEN_STEEL_INGOT), Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".lothlorien"), null),

    MORDOR_ORC_MAIL_ARMOR("mordor_orc_mail", 3, SoundEvents.ITEM_ARMOR_EQUIP_IRON,
            () -> Ingredient.ofItems(ModResourceItems.ORC_STEEL_INGOT), Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".mordor"), null),

    MORDOR_BLACK_URUK_SCALE_ARMOR("mordor_black_uruk_scale",  4, SoundEvents.ITEM_ARMOR_EQUIP_IRON,
            () -> Ingredient.ofItems(ModResourceItems.URUK_STEEL_INGOT), Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".mordor"), null),

    MORDOR_BLACK_URUK_PLATE_ARMOR("mordor_black_uruk_plate", 5, SoundEvents.ITEM_ARMOR_EQUIP_IRON,
            () -> Ingredient.ofItems(ModResourceItems.URUK_STEEL_INGOT), Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".mordor"), null),

    MORDOR_BLACK_URUK_COMMANDER_ARMOR("mordor_black_uruk_commander", 5, SoundEvents.ITEM_ARMOR_EQUIP_IRON,
            () -> Ingredient.ofItems(ModResourceItems.URUK_STEEL_INGOT), Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".mordor"), null),

    MISTY_GOBLIN_MAIL_ARMOR("misty_goblin_mail", 3, SoundEvents.ITEM_ARMOR_EQUIP_IRON,
            () -> Ingredient.ofItems(ModResourceItems.ORC_STEEL_INGOT), Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".misty_orcs"), null),

    MISTY_HOBGOBLIN_SCALE_ARMOR("misty_hobgoblin_scale", 4, SoundEvents.ITEM_ARMOR_EQUIP_IRON,
            () -> Ingredient.ofItems(ModResourceItems.URUK_STEEL_INGOT), Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".misty_orcs"), Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".gundabad")),

    MISTY_HOBGOBLIN_PLATE_ARMOR("misty_hobgoblin_plate", 5, SoundEvents.ITEM_ARMOR_EQUIP_IRON,
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
                if(name.contains("rusty")){
                    this.durabilityMultiplier = 9;
                }else{
                    this.durabilityMultiplier = 11;
                }
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
