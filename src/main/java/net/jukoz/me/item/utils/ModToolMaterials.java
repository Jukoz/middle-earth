package net.jukoz.me.item.utils;

import com.google.common.base.Suppliers;
import net.jukoz.me.block.StoneBlockSets;
import net.jukoz.me.item.ModResourceItems;
import net.minecraft.block.Block;
import net.minecraft.item.Items;
import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.TagKey;

import java.util.function.Supplier;

public enum ModToolMaterials implements ToolMaterial
{
    BRONZE(BlockTags.INCORRECT_FOR_IRON_TOOL, 200, 5.0f, 1.0f, 5, () -> Ingredient.ofItems(ModResourceItems.BRONZE_INGOT)),
    CRUDE(BlockTags.INCORRECT_FOR_IRON_TOOL, 200, 5.0f, 1.0f, 5, () -> Ingredient.ofItems(ModResourceItems.CRUDE_INGOT)),
    STEEL(BlockTags.INCORRECT_FOR_DIAMOND_TOOL, 750, 7.0f, 2.0f, 10, () -> Ingredient.ofItems(ModResourceItems.STEEL_INGOT)),
    NOBLE_STEEL(BlockTags.INCORRECT_FOR_DIAMOND_TOOL, 1500, 7.0f, 2.0f, 10, () -> Ingredient.ofItems(ModResourceItems.STEEL_INGOT)),
    BURZUM_STEEL(BlockTags.INCORRECT_FOR_DIAMOND_TOOL, 750, 7.0f, 2.0f, 10, () -> Ingredient.ofItems(ModResourceItems.BURZUM_STEEL_INGOT)),
    NOBLE_BURZUM_STEEL(BlockTags.INCORRECT_FOR_DIAMOND_TOOL, 1500, 7.0f, 2.0f, 10, () -> Ingredient.ofItems(ModResourceItems.BURZUM_STEEL_INGOT)),
    EDHEL_STEEL(BlockTags.INCORRECT_FOR_DIAMOND_TOOL, 750, 7.0f, 2.0f, 10, () -> Ingredient.ofItems(ModResourceItems.EDHEL_STEEL_INGOT)),
    NOBLE_EDHEL_STEEL(BlockTags.INCORRECT_FOR_DIAMOND_TOOL, 1500, 7.0f, 2.0f, 10, () -> Ingredient.ofItems(ModResourceItems.EDHEL_STEEL_INGOT)),
    KHAZAD_STEEL(BlockTags.INCORRECT_FOR_DIAMOND_TOOL, 750, 7.0f, 2.0f, 10, () -> Ingredient.ofItems(ModResourceItems.KHAZAD_STEEL_INGOT)),
    NOBLE_KHAZAD_STEEL(BlockTags.INCORRECT_FOR_DIAMOND_TOOL, 1500, 7.0f, 2.0f, 10, () -> Ingredient.ofItems(ModResourceItems.KHAZAD_STEEL_INGOT)),
    MITHRIL(BlockTags.INCORRECT_FOR_NETHERITE_TOOL, 2031, 9.0f, 1.5f, 15, () -> Ingredient.ofItems(ModResourceItems.MITHRIL_INGOT)),

    MORGUL_KNIFE(BlockTags.INCORRECT_FOR_DIAMOND_TOOL, 2, 7.0f, 2.0f, 10, () -> Ingredient.ofItems(ModResourceItems.STEEL_INGOT)),

    COPPER_HAMMER(BlockTags.INCORRECT_FOR_WOODEN_TOOL, 16, 7.0f, 2.0f, 10, () -> Ingredient.ofItems(Items.COPPER_INGOT)),
    
    STEEL_HAMMER(BlockTags.INCORRECT_FOR_WOODEN_TOOL, 256, 7.0f, 2.0f, 10, () -> Ingredient.ofItems(ModResourceItems.STEEL_INGOT)),
    NOBLE_STEEL_HAMMER(BlockTags.INCORRECT_FOR_WOODEN_TOOL, 512, 7.0f, 2.0f, 10, () -> Ingredient.ofItems(ModResourceItems.STEEL_INGOT)),

    KHAZAD_STEEL_HAMMER(BlockTags.INCORRECT_FOR_WOODEN_TOOL, 256, 7.0f, 2.0f, 10, () -> Ingredient.ofItems(ModResourceItems.KHAZAD_STEEL_INGOT)),
    KHAZAD_NOBLE_STEEL_HAMMER(BlockTags.INCORRECT_FOR_WOODEN_TOOL, 512, 7.0f, 2.0f, 10, () -> Ingredient.ofItems(ModResourceItems.KHAZAD_STEEL_INGOT)),

    EDHEL_STEEL_HAMMER(BlockTags.INCORRECT_FOR_WOODEN_TOOL, 256, 7.0f, 2.0f, 10, () -> Ingredient.ofItems(ModResourceItems.EDHEL_STEEL_INGOT)),
    EDHEL_NOBLE_STEEL_HAMMER(BlockTags.INCORRECT_FOR_WOODEN_TOOL, 512, 7.0f, 2.0f, 10, () -> Ingredient.ofItems(ModResourceItems.EDHEL_STEEL_INGOT)),

    BURZUM_STEEL_HAMMER(BlockTags.INCORRECT_FOR_WOODEN_TOOL, 256, 7.0f, 2.0f, 10, () -> Ingredient.ofItems(ModResourceItems.BURZUM_STEEL_INGOT)),
    BURZUM_NOBLE_STEEL_HAMMER(BlockTags.INCORRECT_FOR_WOODEN_TOOL, 512, 7.0f, 2.0f, 10, () -> Ingredient.ofItems(ModResourceItems.BURZUM_STEEL_INGOT)),

    MITHRIL_HAMMER(BlockTags.INCORRECT_FOR_WOODEN_TOOL, 1024, 7.0f, 2.0f, 10, () -> Ingredient.ofItems(ModResourceItems.MITHRIL_INGOT)),
    ;

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