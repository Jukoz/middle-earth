package net.sevenstars.middleearth.item.utils;

import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import net.sevenstars.middleearth.utils.ItemTagsME;

public final class ToolMaterialsME {
    public static final Material BONE = material(BlockTags.INCORRECT_FOR_STONE_TOOL, 150, 4.0F, 0.0F, 4, ItemTagsME.BONE_TOOL_MATERIALS);
    public static final Material BRONZE = material(BlockTags.INCORRECT_FOR_IRON_TOOL, 200, 5.0F, 1.0F, 5, ItemTagsME.BRONZE_TOOL_MATERIALS);
    public static final Material CRUDE = material(BlockTags.INCORRECT_FOR_IRON_TOOL, 200, 5.0F, 1.0F, 5, ItemTagsME.CRUDE_TOOL_MATERIALS);
    public static final Material STEEL = material(BlockTags.INCORRECT_FOR_DIAMOND_TOOL, 1561, 7.0F, 2.0F, 10, ItemTagsME.STEEL_TOOL_MATERIALS);
    public static final Material BURZUM_STEEL = material(BlockTags.INCORRECT_FOR_DIAMOND_TOOL, 1561, 7.0F, 2.0F, 10, ItemTagsME.BURZUM_STEEL_TOOL_MATERIALS);
    public static final Material EDHEL_STEEL = material(BlockTags.INCORRECT_FOR_DIAMOND_TOOL, 1561, 7.0F, 2.0F, 10, ItemTagsME.EDHEL_STEEL_TOOL_MATERIALS);
    public static final Material KHAZAD_STEEL = material(BlockTags.INCORRECT_FOR_DIAMOND_TOOL, 1561, 7.0F, 2.0F, 10, ItemTagsME.KHAZAD_STEEL_TOOL_MATERIALS);
    public static final Material MITHRIL = material(BlockTags.INCORRECT_FOR_NETHERITE_TOOL, 2031, 10.0F, 0.0F, 15, ItemTagsME.MITHRIL_TOOL_MATERIALS);

    public static final Material WEAVER_STING = material(BlockTags.INCORRECT_FOR_STONE_TOOL, 1561, 4.0F, 1.0F, 5, ItemTagsME.SPIDER_TOOL_MATERIALS);
    public static final Material MORGUL_KNIFE = material(BlockTags.INCORRECT_FOR_DIAMOND_TOOL, 2, 7.0F, 2.0F, 10, ItemTagsME.BURZUM_STEEL_TOOL_MATERIALS);

    public static final Material COPPER_HAMMER = material(BlockTags.INCORRECT_FOR_WOODEN_TOOL, 32, 7.0F, 2.0F, 10, ItemTagsME.COPPER_TOOL_MATERIALS);
    public static final Material BRONZE_HAMMER = material(BlockTags.INCORRECT_FOR_WOODEN_TOOL, 128, 7.0F, 2.0F, 10, ItemTagsME.BRONZE_TOOL_MATERIALS);
    public static final Material CRUDE_HAMMER = material(BlockTags.INCORRECT_FOR_WOODEN_TOOL, 128, 7.0F, 2.0F, 10, ItemTagsME.CRUDE_TOOL_MATERIALS);
    public static final Material STEEL_HAMMER = material(BlockTags.INCORRECT_FOR_WOODEN_TOOL, 256, 7.0F, 2.0F, 10, ItemTagsME.STEEL_TOOL_MATERIALS);
    public static final Material NOBLE_STEEL_HAMMER = material(BlockTags.INCORRECT_FOR_WOODEN_TOOL, 512, 7.0F, 2.0F, 10, ItemTagsME.STEEL_TOOL_MATERIALS);
    public static final Material KHAZAD_STEEL_HAMMER = material(BlockTags.INCORRECT_FOR_WOODEN_TOOL, 256, 7.0F, 2.0F, 10, ItemTagsME.KHAZAD_STEEL_TOOL_MATERIALS);
    public static final Material KHAZAD_NOBLE_STEEL_HAMMER = material(BlockTags.INCORRECT_FOR_WOODEN_TOOL, 512, 7.0F, 2.0F, 10, ItemTagsME.KHAZAD_STEEL_TOOL_MATERIALS);
    public static final Material EDHEL_STEEL_HAMMER = material(BlockTags.INCORRECT_FOR_WOODEN_TOOL, 256, 7.0F, 2.0F, 10, ItemTagsME.EDHEL_STEEL_TOOL_MATERIALS);
    public static final Material EDHEL_NOBLE_STEEL_HAMMER = material(BlockTags.INCORRECT_FOR_WOODEN_TOOL, 512, 7.0F, 2.0F, 10, ItemTagsME.EDHEL_STEEL_TOOL_MATERIALS);
    public static final Material BURZUM_STEEL_HAMMER = material(BlockTags.INCORRECT_FOR_WOODEN_TOOL, 256, 7.0F, 2.0F, 10, ItemTagsME.BURZUM_STEEL_TOOL_MATERIALS);
    public static final Material BURZUM_NOBLE_STEEL_HAMMER = material(BlockTags.INCORRECT_FOR_WOODEN_TOOL, 512, 7.0F, 2.0F, 10, ItemTagsME.BURZUM_STEEL_TOOL_MATERIALS);
    public static final Material MITHRIL_HAMMER = material(BlockTags.INCORRECT_FOR_WOODEN_TOOL, 1024, 7.0F, 0.0F, 10, ItemTagsME.MITHRIL_TOOL_MATERIALS);

    private ToolMaterialsME() {
    }

    private static Material material(TagKey<Block> incorrectBlocks, int uses, float speed, float attackDamage,
                                     int enchantmentValue, TagKey<Item> repairItems) {
        return new Material(incorrectBlocks, uses, speed, attackDamage, enchantmentValue, repairItems);
    }

    public record Material(TagKey<Block> incorrectBlocks, int uses, float speed, float attackDamage,
                           int enchantmentValue, TagKey<Item> repairItems) implements Tier {
        @Override
        public int getUses() {
            return uses;
        }

        @Override
        public float getSpeed() {
            return speed;
        }

        @Override
        public float getAttackDamageBonus() {
            return attackDamage;
        }

        @Override
        public TagKey<Block> getIncorrectBlocksForDrops() {
            return incorrectBlocks;
        }

        @Override
        public int getEnchantmentValue() {
            return enchantmentValue;
        }

        @Override
        public Ingredient getRepairIngredient() {
            return Ingredient.of(repairItems);
        }
    }
}
