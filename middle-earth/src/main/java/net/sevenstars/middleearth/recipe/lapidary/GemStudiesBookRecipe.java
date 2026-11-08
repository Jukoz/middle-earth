package net.sevenstars.middleearth.recipe.lapidary;

import net.sevenstars.middleearth.item.items.gems.GemNotesItem;

import net.sevenstars.middleearth.item.ResourceItemsME;
import net.sevenstars.middleearth.recipe.RecipeSerializerRegistryME;

import java.util.HashMap;
import java.util.Map;

import net.sevenstars.middleearth.item.DataComponentTypesME;
import net.sevenstars.middleearth.resources.datas.gems.GemType;

import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialCraftingRecipe;
import net.minecraft.recipe.book.CraftingRecipeCategory;
import net.minecraft.recipe.input.CraftingRecipeInput;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

/**
 * Player Book + any number of gem notes and lapidary scrolls, anywhere in a crafting grid: the book comes back
 * with each gem's entry raised to the best of the notes' grades, and a page for each scroll. The notes and
 * scrolls are used up. The recipe only matches when at least one of them teaches the book something new, so
 * nothing is wasted.
 */
public class GemStudiesBookRecipe extends SpecialCraftingRecipe {
    public GemStudiesBookRecipe(CraftingRecipeCategory category) {
        super(category);
    }

    @Override
    public boolean matches(CraftingRecipeInput input, World world) {
        return !craft(input, null).isEmpty();
    }

    @Override
    public ItemStack craft(CraftingRecipeInput input, RegistryWrapper.WrapperLookup registries) {
        ItemStack book = ItemStack.EMPTY;
        Map<Identifier, StudyGrade> learned = new HashMap<>();
        java.util.Set<String> scrolls = new java.util.LinkedHashSet<>();
        for (ItemStack stack : input.getStacks()) {
            if (stack.isEmpty()) {
                continue;
            }
            if (GemStudies.isPlayerBook(stack)) {
                if (!book.isEmpty()) {
                    return ItemStack.EMPTY;
                }
                book = stack;
            } else if (stack.getItem() instanceof GemNotesItem notes) {
                RegistryKey<GemType> gem = stack.get(DataComponentTypesME.GEM_TYPE);
                if (gem == null) {
                    return ItemStack.EMPTY;
                }
                learned.merge(gem.getValue(), notes.getGrade(), StudyGrade::best);
            } else if (stack.getItem() instanceof net.sevenstars.middleearth.item.items.gems.LapidaryScrollItem scroll) {
                scrolls.add(scroll.tools().id());
            } else {
                return ItemStack.EMPTY;
            }
        }
        if (book.isEmpty() || (learned.isEmpty() && scrolls.isEmpty())) {
            return ItemStack.EMPTY;
        }
        Map<Identifier, StudyGrade> studies = new HashMap<>(book.getOrDefault(DataComponentTypesME.GEM_STUDIES, Map.of()));
        boolean improved = false;
        for (Map.Entry<Identifier, StudyGrade> entry : learned.entrySet()) {
            StudyGrade before = studies.get(entry.getKey());
            if (before == null || !before.atLeast(entry.getValue())) {
                studies.put(entry.getKey(), entry.getValue());
                improved = true;
            }
        }
        java.util.List<String> pages = new java.util.ArrayList<>(book.getOrDefault(DataComponentTypesME.LAPIDARY_PAGES, java.util.List.of()));
        for (String scroll : scrolls) {
            if (!pages.contains(scroll)) {
                pages.add(scroll);
                improved = true;
            }
        }
        if (!improved) {
            return ItemStack.EMPTY;
        }
        ItemStack result = book.copyWithCount(1);
        result.set(DataComponentTypesME.GEM_STUDIES, Map.copyOf(studies));
        if (!pages.isEmpty()) {
            result.set(DataComponentTypesME.LAPIDARY_PAGES, java.util.List.copyOf(pages));
        }
        return result;
    }

    @Override
    public RecipeSerializer<GemStudiesBookRecipe> getSerializer() {
        return RecipeSerializerRegistryME.GEM_STUDIES_BOOK;
    }
}
