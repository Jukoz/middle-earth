package net.jesteur.me.item.utils;

import net.fabricmc.yarn.constants.MiningLevels;
import net.jesteur.me.item.ModRessourceItems;
import net.minecraft.item.Items;
import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.util.Lazy;

import java.util.function.Supplier;

public enum ModToolMaterials implements ToolMaterial
{
    DWARVEN(MiningLevels.DIAMOND, 800, 8.0f, 2.5f, 15, () -> Ingredient.ofItems(Items.IRON_INGOT)),
    ELVEN(MiningLevels.DIAMOND, 700, 7.5f, 2.5f, 15, () -> Ingredient.ofItems(Items.IRON_INGOT)),
    MORGUL(MiningLevels.IRON, 666, 7f, 2.5f, 14, () -> Ingredient.ofItems(ModRessourceItems.MORGUL_INGOT)),
    MORDOR_ORC(MiningLevels.IRON, 550, 6.5f, 2.0f, 14, () -> Ingredient.ofItems(Items.IRON_INGOT));

    private final int miningLevel;
    private final int itemDurability;
    private final float miningSpeed;
    private final float attackDamage;
    private final int enchantability;
    private final Lazy<Ingredient> repairIngredient;

    private ModToolMaterials(int miningLevel, int itemDurability, float miningSpeed, float attackDamage, int enchantability, Supplier<Ingredient> repairIngredient) {
        this.miningLevel = miningLevel;
        this.itemDurability = itemDurability;
        this.miningSpeed = miningSpeed;
        this.attackDamage = attackDamage;
        this.enchantability = enchantability;
        this.repairIngredient = new Lazy<Ingredient>(repairIngredient);
    }

    @Override
    public int getDurability() {
        return this.itemDurability;
    }

    @Override
    public float getMiningSpeedMultiplier() {
        return this.miningSpeed;
    }

    @Override
    public float getAttackDamage() {
        return this.attackDamage;
    }

    @Override
    public int getMiningLevel() {
        return this.miningLevel;
    }

    @Override
    public int getEnchantability() {
        return this.enchantability;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return this.repairIngredient.get();
    }
}