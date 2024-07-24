package net.jukoz.me.item.utils;

import com.google.common.base.Suppliers;
import net.jukoz.me.block.StoneBlockSets;
import net.jukoz.me.item.ModResourceItems;
import net.minecraft.block.Block;
import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.TagKey;

import java.util.function.Supplier;

public enum ModToolMaterials implements ToolMaterial
{
    JADE(BlockTags.INCORRECT_FOR_STONE_TOOL, 256, 4.0f, 1.0f, 5, () -> Ingredient.ofItems(StoneBlockSets.JADEITE.base())),
    BRONZE(BlockTags.INCORRECT_FOR_IRON_TOOL, 200, 5.0f, 1.0f, 5, () -> Ingredient.ofItems(ModResourceItems.BRONZE_INGOT)),
    SLAG(BlockTags.INCORRECT_FOR_IRON_TOOL, 200, 5.0f, 1.0f, 7, () -> Ingredient.ofItems(ModResourceItems.SLAG_SCRAP)),
    STEEL(BlockTags.INCORRECT_FOR_DIAMOND_TOOL, 750, 7.0f, 2.0f, 10, () -> Ingredient.ofItems(ModResourceItems.STEEL_INGOT)),
    URUK_STEEL(BlockTags.INCORRECT_FOR_DIAMOND_TOOL, 750, 7.0f, 2.0f, 10, () -> Ingredient.ofItems(ModResourceItems.URUK_STEEL_INGOT)),
    ELVEN_STEEL(BlockTags.INCORRECT_FOR_DIAMOND_TOOL, 750, 7.0f, 2.0f, 10, () -> Ingredient.ofItems(ModResourceItems.ELVEN_STEEL_INGOT)),
    DWARVEN_STEEL(BlockTags.INCORRECT_FOR_DIAMOND_TOOL, 750, 7.0f, 2.0f, 10, () -> Ingredient.ofItems(ModResourceItems.DWARVEN_STEEL_INGOT)),
    MORGUL_STEEL(BlockTags.INCORRECT_FOR_DIAMOND_TOOL, 800, 7.5f, 2.0f, 12, () -> Ingredient.ofItems(ModResourceItems.MORGUL_STEEL_INGOT)),
    MITHRIL(BlockTags.INCORRECT_FOR_NETHERITE_TOOL, 2031, 9.0f, 2.0f, 15, () -> Ingredient.ofItems(ModResourceItems.MITHRIL_INGOT));

    private final TagKey<Block> inverseTag;
    private final int itemDurability;
    private final float miningSpeed;
    private final float attackDamage;
    private final int enchantability;
    private final Supplier<Ingredient> repairIngredient;

    private ModToolMaterials(TagKey<Block> inverseTag, int itemDurability, float miningSpeed, float attackDamage, int enchantability, Supplier<Ingredient> repairIngredient) {
        this.inverseTag = inverseTag;
        this.itemDurability = itemDurability;
        this.miningSpeed = miningSpeed;
        this.attackDamage = attackDamage;
        this.enchantability = enchantability;
        this.repairIngredient = Suppliers.memoize(repairIngredient::get);
    }

    @Override
    public TagKey<Block> getInverseTag() {
        return this.inverseTag;
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
    public int getEnchantability() {
        return this.enchantability;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return this.repairIngredient.get();
    }
}