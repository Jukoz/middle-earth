package net.sevenstars.middleearth.compat.recipeviewer;

import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.enchantment.Enchantment;
import net.sevenstars.middleearth.block.special.forge.MetalTypes;
import net.sevenstars.middleearth.item.ResourceItemsME;
import net.sevenstars.middleearth.recipe.AlloyingRecipe;
import net.sevenstars.middleearth.recipe.inscription.InscriptionRecipe;
import net.sevenstars.middleearth.recipe.inscription.InscriptionWordBank;

import java.util.ArrayList;
import java.util.List;

public final class RecipeViewerHelper {
    public static final int METAL_UNITS_PER_INGOT = 144;
    private static final List<Item> INSCRIPTION_CATALYSTS = List.of(
            Items.LAPIS_LAZULI,
            Items.EMERALD,
            ResourceItemsME.RUBY,
            ResourceItemsME.SAPPHIRE,
            ResourceItemsME.ADAMANT
    );

    private RecipeViewerHelper() {
    }

    public static ItemStack getAlloyOutput(AlloyingRecipe recipe) {
        MetalTypes metal = MetalTypes.fromValue(recipe.getAlloyResult());
        if (metal == null || metal.getIngot() == null) {
            return ItemStack.EMPTY;
        }

        return new ItemStack(metal.getIngot(), getIngotEquivalent(recipe));
    }

    public static int getIngotEquivalent(AlloyingRecipe recipe) {
        return recipe.getAmount() / METAL_UNITS_PER_INGOT;
    }

    public static Ingredient getInscriptionCatalyst(InscriptionRecipe recipe) {
        List<Item> matches = new ArrayList<>();
        for (Item catalyst : INSCRIPTION_CATALYSTS) {
            List<String> availableWords = new ArrayList<>(InscriptionWordBank.wordBank.get(null));
            availableWords.addAll(InscriptionWordBank.wordBank.get(catalyst));
            if (availableWords.containsAll(recipe.inputWords)) {
                matches.add(catalyst);
            }
        }
        return matches.isEmpty()
                ? Ingredient.EMPTY
                : Ingredient.of(matches.toArray(Item[]::new));
    }

    public static ItemStack getInscriptionOutput(InscriptionRecipe recipe) {
        ItemStack result = new ItemStack(Items.ENCHANTED_BOOK);
        result.enchant(recipe.enchant, recipe.level);
        result.set(DataComponents.CUSTOM_NAME, Component.translatable(
                "recipe.middle-earth.applies_enchantment",
                Enchantment.getFullname(recipe.enchant, recipe.level)
        ));
        return result;
    }
}
