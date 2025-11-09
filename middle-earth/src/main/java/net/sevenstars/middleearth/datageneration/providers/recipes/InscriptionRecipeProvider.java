package net.sevenstars.middleearth.datageneration.providers.recipes;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.data.recipe.RecipeExporter;
import net.minecraft.data.recipe.RecipeGenerator;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.datageneration.custom.InscriptionRecipeJsonBuilder;
import net.sevenstars.middleearth.item.ToolItemsME;

import java.util.concurrent.CompletableFuture;

public class InscriptionRecipeProvider extends RecipeProvider{
    private final CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup;

    public InscriptionRecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);

        this.registryLookup = registriesFuture;
    }

    public RegistryWrapper.Impl<Enchantment> getEnchantmentRegistry(){
        RegistryWrapper.Impl<Enchantment> enchantmentImpl;

        try {
            enchantmentImpl = this.registryLookup.get().getOrThrow(RegistryKeys.ENCHANTMENT);
        } catch (Exception ignored) {
            throw new IllegalStateException("Data generation without registries failed!");
        }
        return enchantmentImpl;
    }

    public RegistryEntry<Enchantment> getEnchantment(RegistryKey<Enchantment> enchantmentRegistryKey){
        return getEnchantmentRegistry().getOrThrow(enchantmentRegistryKey);
    }

    @Override
    public String getName() {
        return "InscriptionRecipes";
    }

    @Override
    protected RecipeGenerator getRecipeGenerator(RegistryWrapper.WrapperLookup wrapperLookup, RecipeExporter recipeExporter) {
        return new RecipeGenerator(wrapperLookup, recipeExporter) {
            @Override
            public void generate() {
                RegistryWrapper.Impl<Item> itemLookup = registries.getOrThrow(RegistryKeys.ITEM);

                InscriptionRecipeJsonBuilder.createInscriptionRecipe(itemLookup, RecipeCategory.MISC, getEnchantment(Enchantments.UNBREAKING), 1, Ingredient.ofItem(ToolItemsME.IRON_CHISEL))
                        .addWord("resilient")
                        .addWord("blessing")
                        .criterion(hasItem(Items.LAPIS_LAZULI),
                                conditionsFromItem(Items.LAPIS_LAZULI))
                        .offerTo(exporter, String.valueOf(Identifier.of(MiddleEarth.MOD_ID, "inscription_unbreaking_1")));
            }
        };
    }

}
