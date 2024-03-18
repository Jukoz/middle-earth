package net.jukoz.me.item.utils;

import net.fabricmc.yarn.constants.MiningLevels;
import net.jukoz.me.block.StoneBlockSets;
import net.jukoz.me.item.ModResourceItems;
import net.minecraft.item.Items;
import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.util.Lazy;

import java.util.function.Supplier;

public enum ModToolMaterials implements ToolMaterial
{
    JADE(MiningLevels.STONE, 256, 4.0f, 1.0f, 5, () -> Ingredient.ofItems(StoneBlockSets.JADEITE.base())),
    BRONZE(MiningLevels.IRON, 200, 5.0f, 1.0f, 5, () -> Ingredient.ofItems(ModResourceItems.BRONZE_INGOT)),
    ORC_STEEL(MiningLevels.DIAMOND, 500, 6.5f, 2.0f, 7, () -> Ingredient.ofItems(ModResourceItems.ORC_STEEL_INGOT)),
    STEEL(MiningLevels.DIAMOND, 750, 7.0f, 2.0f, 10, () -> Ingredient.ofItems(ModResourceItems.STEEL_INGOT)),
    URUK_STEEL(MiningLevels.DIAMOND, 750, 7.0f, 2.0f, 10, () -> Ingredient.ofItems(ModResourceItems.URUK_STEEL_INGOT)),
    ELVEN_STEEL(MiningLevels.DIAMOND, 750, 7.0f, 2.0f, 10, () -> Ingredient.ofItems(ModResourceItems.ELVEN_STEEL_INGOT)),
    DWARVEN_STEEL(MiningLevels.DIAMOND, 750, 7.0f, 2.0f, 10, () -> Ingredient.ofItems(ModResourceItems.DWARVEN_STEEL_INGOT)),
    MORGUL_STEEL(MiningLevels.DIAMOND, 800, 7.5f, 2.0f, 12, () -> Ingredient.ofItems(ModResourceItems.MORGUL_STEEL_INGOT)),
    MITHRIL(MiningLevels.NETHERITE, 2031, 9.0f, 2.0f, 15, () -> Ingredient.ofItems(ModResourceItems.MITHRIL_INGOT));

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