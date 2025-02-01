package net.sevenstars.middleearth.item.utils;

import com.google.common.base.Suppliers;
import net.sevenstars.middleearth.item.ModResourceItems;
import net.minecraft.block.Block;
import net.minecraft.item.Items;
import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.TagKey;
import net.sevenstars.middleearth.recipe.ModTags;

import java.util.function.Supplier;

public class ModToolMaterials {
    public static final ToolMaterial BRONZE = new ToolMaterial(BlockTags.INCORRECT_FOR_IRON_TOOL, 200, 5.0f, 1.0f, 5, ModTags.BRONZE_TOOL_MATERIALS);
    public static final ToolMaterial CRUDE = new ToolMaterial(BlockTags.INCORRECT_FOR_IRON_TOOL, 200, 5.0f, 1.0f, 5, ModTags.CRUDE_TOOL_MATERIALS);
    public static final ToolMaterial STEEL = new ToolMaterial(BlockTags.INCORRECT_FOR_DIAMOND_TOOL, 750, 7.0f, 2.0f, 10, ModTags.STEEL_TOOL_MATERIALS);
    public static final ToolMaterial NOBLE_STEEL = new ToolMaterial(BlockTags.INCORRECT_FOR_DIAMOND_TOOL, 1500, 7.0f, 2.0f, 10, ModTags.STEEL_TOOL_MATERIALS);
    public static final ToolMaterial BURZUM_STEEL = new ToolMaterial(BlockTags.INCORRECT_FOR_DIAMOND_TOOL, 750, 7.0f, 2.0f, 10, ModTags.BURZUM_STEEL_TOOL_MATERIALS);
    public static final ToolMaterial NOBLE_BURZUM_STEEL = new ToolMaterial(BlockTags.INCORRECT_FOR_DIAMOND_TOOL, 1500, 7.0f, 2.0f, 10, ModTags.BURZUM_STEEL_TOOL_MATERIALS);
    public static final ToolMaterial EDHEL_STEEL = new ToolMaterial(BlockTags.INCORRECT_FOR_DIAMOND_TOOL, 750, 7.0f, 2.0f, 10, ModTags.EDHEL_STEEL_TOOL_MATERIALS);
    public static final ToolMaterial NOBLE_EDHEL_STEEL = new ToolMaterial(BlockTags.INCORRECT_FOR_DIAMOND_TOOL, 1500, 7.0f, 2.0f, 10, ModTags.EDHEL_STEEL_TOOL_MATERIALS);
    public static final ToolMaterial KHAZAD_STEEL = new ToolMaterial(BlockTags.INCORRECT_FOR_DIAMOND_TOOL, 750, 7.0f, 2.0f, 10, ModTags.KHAZAD_STEEL_TOOL_MATERIALS);
    public static final ToolMaterial NOBLE_KHAZAD_STEEL = new ToolMaterial(BlockTags.INCORRECT_FOR_DIAMOND_TOOL, 1500, 7.0f, 2.0f, 10, ModTags.KHAZAD_STEEL_TOOL_MATERIALS);
    public static final ToolMaterial MITHRIL = new ToolMaterial(BlockTags.INCORRECT_FOR_NETHERITE_TOOL, 2031, 9.0f, 1.5f, 15, ModTags.MITHRIL_TOOL_MATERIALS);

    public static final ToolMaterial MORGUL_KNIFE = new ToolMaterial(BlockTags.INCORRECT_FOR_DIAMOND_TOOL, 2, 7.0f, 2.0f, 10, ModTags.BURZUM_STEEL_TOOL_MATERIALS);

    public static final ToolMaterial COPPER_HAMMER = new ToolMaterial(BlockTags.INCORRECT_FOR_WOODEN_TOOL, 16, 7.0f, 2.0f, 10, ModTags.COPPER_TOOL_MATERIALS);

    public static final ToolMaterial STEEL_HAMMER = new ToolMaterial(BlockTags.INCORRECT_FOR_WOODEN_TOOL, 256, 7.0f, 2.0f, 10, ModTags.STEEL_TOOL_MATERIALS);
    public static final ToolMaterial NOBLE_STEEL_HAMMER = new ToolMaterial(BlockTags.INCORRECT_FOR_WOODEN_TOOL, 512, 7.0f, 2.0f, 10, ModTags.STEEL_TOOL_MATERIALS);

    public static final ToolMaterial KHAZAD_STEEL_HAMMER = new ToolMaterial(BlockTags.INCORRECT_FOR_WOODEN_TOOL, 256, 7.0f, 2.0f, 10, ModTags.KHAZAD_STEEL_TOOL_MATERIALS);
    public static final ToolMaterial KHAZAD_NOBLE_STEEL_HAMMER = new ToolMaterial(BlockTags.INCORRECT_FOR_WOODEN_TOOL, 512, 7.0f, 2.0f, 10, ModTags.KHAZAD_STEEL_TOOL_MATERIALS);

    public static final ToolMaterial EDHEL_STEEL_HAMMER = new ToolMaterial(BlockTags.INCORRECT_FOR_WOODEN_TOOL, 256, 7.0f, 2.0f, 10, ModTags.EDHEL_STEEL_TOOL_MATERIALS);
    public static final ToolMaterial EDHEL_NOBLE_STEEL_HAMMER = new ToolMaterial(BlockTags.INCORRECT_FOR_WOODEN_TOOL, 512, 7.0f, 2.0f, 10, ModTags.EDHEL_STEEL_TOOL_MATERIALS);

    public static final ToolMaterial BURZUM_STEEL_HAMMER = new ToolMaterial(BlockTags.INCORRECT_FOR_WOODEN_TOOL, 256, 7.0f, 2.0f, 10, ModTags.BURZUM_STEEL_TOOL_MATERIALS);
    public static final ToolMaterial BURZUM_NOBLE_STEEL_HAMMER = new ToolMaterial(BlockTags.INCORRECT_FOR_WOODEN_TOOL, 512, 7.0f, 2.0f, 10, ModTags.BURZUM_STEEL_TOOL_MATERIALS);

    public static final ToolMaterial MITHRIL_HAMMER = new ToolMaterial(BlockTags.INCORRECT_FOR_WOODEN_TOOL, 1024, 7.0f, 2.0f, 10, ModTags.MITHRIL_TOOL_MATERIALS);
    ;
}