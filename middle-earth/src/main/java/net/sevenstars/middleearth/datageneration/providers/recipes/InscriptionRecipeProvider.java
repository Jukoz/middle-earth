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
import net.sevenstars.middleearth.enchantments.EnchantmentsME;
import net.sevenstars.middleearth.item.ResourceItemsME;
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

                //region UNBREAKING
                InscriptionRecipeJsonBuilder.createInscriptionRecipe(itemLookup, RecipeCategory.MISC,
                                getEnchantment(Enchantments.UNBREAKING), 1,
                                Ingredient.ofItem(ToolItemsME.IRON_CHISEL), 5)
                        .addWord("resilient")
                        .addWord("blessing")
                        .criterion(hasItem(Items.LAPIS_LAZULI),
                                conditionsFromItem(Items.LAPIS_LAZULI))
                        .offerTo(exporter, String.valueOf(Identifier.of(MiddleEarth.MOD_ID, "inscription_unbreaking_1")));

                InscriptionRecipeJsonBuilder.createInscriptionRecipe(itemLookup, RecipeCategory.MISC,
                                getEnchantment(Enchantments.UNBREAKING), 2,
                                Ingredient.ofItem(ToolItemsME.STEEL_CHISEL), 7)
                        .addWord("resilient")
                        .addWord("blessing")
                        .criterion(hasItem(Items.LAPIS_LAZULI),
                                conditionsFromItem(Items.LAPIS_LAZULI))
                        .offerTo(exporter, String.valueOf(Identifier.of(MiddleEarth.MOD_ID, "inscription_unbreaking_2")));

                InscriptionRecipeJsonBuilder.createInscriptionRecipe(itemLookup, RecipeCategory.MISC,
                                getEnchantment(Enchantments.UNBREAKING), 3,
                                Ingredient.ofItem(ToolItemsME.STEEL_CHISEL), 9)
                        .addWord("resilient")
                        .addWord("blessing")
                        .criterion(hasItem(Items.LAPIS_LAZULI),
                                conditionsFromItem(Items.LAPIS_LAZULI))
                        .offerTo(exporter, String.valueOf(Identifier.of(MiddleEarth.MOD_ID, "inscription_unbreaking_3")));
                //endregion

                //region SHARPNESS
                InscriptionRecipeJsonBuilder.createInscriptionRecipe(itemLookup, RecipeCategory.MISC,
                                getEnchantment(Enchantments.SHARPNESS), 1,
                                Ingredient.ofItem(ToolItemsME.IRON_CHISEL), 3)
                        .addWord("forceful")
                        .addWord("cutter")
                        .criterion(hasItem(ResourceItemsME.RUBY),
                                conditionsFromItem(ResourceItemsME.RUBY))
                        .offerTo(exporter, String.valueOf(Identifier.of(MiddleEarth.MOD_ID, "inscription_sharpness_1")));

                InscriptionRecipeJsonBuilder.createInscriptionRecipe(itemLookup, RecipeCategory.MISC,
                                getEnchantment(Enchantments.SHARPNESS), 2,
                                Ingredient.ofItem(ToolItemsME.IRON_CHISEL), 5)
                        .addWord("forceful")
                        .addWord("cutter")
                        .criterion(hasItem(ResourceItemsME.RUBY),
                                conditionsFromItem(ResourceItemsME.RUBY))
                        .offerTo(exporter, String.valueOf(Identifier.of(MiddleEarth.MOD_ID, "inscription_sharpness_2")));

                InscriptionRecipeJsonBuilder.createInscriptionRecipe(itemLookup, RecipeCategory.MISC,
                                getEnchantment(Enchantments.SHARPNESS), 3,
                                Ingredient.ofItem(ToolItemsME.STEEL_CHISEL), 7)
                        .addWord("forceful")
                        .addWord("cutter")
                        .criterion(hasItem(ResourceItemsME.RUBY),
                                conditionsFromItem(ResourceItemsME.RUBY))
                        .offerTo(exporter, String.valueOf(Identifier.of(MiddleEarth.MOD_ID, "inscription_sharpness_3")));

                InscriptionRecipeJsonBuilder.createInscriptionRecipe(itemLookup, RecipeCategory.MISC,
                                getEnchantment(Enchantments.SHARPNESS), 4,
                                Ingredient.ofItem(ToolItemsME.STEEL_CHISEL), 9)
                        .addWord("forceful")
                        .addWord("cutter")
                        .criterion(hasItem(ResourceItemsME.RUBY),
                                conditionsFromItem(ResourceItemsME.RUBY))
                        .offerTo(exporter, String.valueOf(Identifier.of(MiddleEarth.MOD_ID, "inscription_sharpness_4")));

                InscriptionRecipeJsonBuilder.createInscriptionRecipe(itemLookup, RecipeCategory.MISC,
                                getEnchantment(Enchantments.SHARPNESS), 5,
                                Ingredient.ofItem(ToolItemsME.MITHRIL_CHISEL), 11)
                        .addWord("forceful")
                        .addWord("cutter")
                        .criterion(hasItem(ResourceItemsME.RUBY),
                                conditionsFromItem(ResourceItemsME.RUBY))
                        .offerTo(exporter, String.valueOf(Identifier.of(MiddleEarth.MOD_ID, "inscription_sharpness_5")));
                //endregion

                //region POWER
                InscriptionRecipeJsonBuilder.createInscriptionRecipe(itemLookup, RecipeCategory.MISC,
                                getEnchantment(Enchantments.POWER), 1,
                                Ingredient.ofItem(ToolItemsME.IRON_CHISEL), 3)
                        .addWord("fierce")
                        .addWord("point")
                        .criterion(hasItem(ResourceItemsME.RUBY),
                                conditionsFromItem(ResourceItemsME.RUBY))
                        .offerTo(exporter, String.valueOf(Identifier.of(MiddleEarth.MOD_ID, "inscription_power_1")));

                InscriptionRecipeJsonBuilder.createInscriptionRecipe(itemLookup, RecipeCategory.MISC,
                                getEnchantment(Enchantments.POWER), 2,
                                Ingredient.ofItem(ToolItemsME.IRON_CHISEL), 5)
                        .addWord("fierce")
                        .addWord("point")
                        .criterion(hasItem(ResourceItemsME.RUBY),
                                conditionsFromItem(ResourceItemsME.RUBY))
                        .offerTo(exporter, String.valueOf(Identifier.of(MiddleEarth.MOD_ID, "inscription_power_2")));

                InscriptionRecipeJsonBuilder.createInscriptionRecipe(itemLookup, RecipeCategory.MISC,
                                getEnchantment(Enchantments.POWER), 3,
                                Ingredient.ofItem(ToolItemsME.STEEL_CHISEL), 7)
                        .addWord("fierce")
                        .addWord("point")
                        .criterion(hasItem(ResourceItemsME.RUBY),
                                conditionsFromItem(ResourceItemsME.RUBY))
                        .offerTo(exporter, String.valueOf(Identifier.of(MiddleEarth.MOD_ID, "inscription_power_3")));

                InscriptionRecipeJsonBuilder.createInscriptionRecipe(itemLookup, RecipeCategory.MISC,
                                getEnchantment(Enchantments.POWER), 4,
                                Ingredient.ofItem(ToolItemsME.STEEL_CHISEL), 9)
                        .addWord("fierce")
                        .addWord("point")
                        .criterion(hasItem(ResourceItemsME.RUBY),
                                conditionsFromItem(ResourceItemsME.RUBY))
                        .offerTo(exporter, String.valueOf(Identifier.of(MiddleEarth.MOD_ID, "inscription_power_4")));

                InscriptionRecipeJsonBuilder.createInscriptionRecipe(itemLookup, RecipeCategory.MISC,
                                getEnchantment(Enchantments.POWER), 5,
                                Ingredient.ofItem(ToolItemsME.MITHRIL_CHISEL), 11)
                        .addWord("fierce")
                        .addWord("point")
                        .criterion(hasItem(ResourceItemsME.RUBY),
                                conditionsFromItem(ResourceItemsME.RUBY))
                        .offerTo(exporter, String.valueOf(Identifier.of(MiddleEarth.MOD_ID, "inscription_power_5")));
                //endregion

                //region PUNCH
                InscriptionRecipeJsonBuilder.createInscriptionRecipe(itemLookup, RecipeCategory.MISC,
                                getEnchantment(Enchantments.PUNCH), 1,
                                Ingredient.ofItem(ToolItemsME.IRON_CHISEL), 5)
                        .addWord("forceful")
                        .addWord("draw")
                        .criterion(hasItem(ResourceItemsME.RUBY),
                                conditionsFromItem(ResourceItemsME.RUBY))
                        .offerTo(exporter, String.valueOf(Identifier.of(MiddleEarth.MOD_ID, "inscription_punch_1")));

                InscriptionRecipeJsonBuilder.createInscriptionRecipe(itemLookup, RecipeCategory.MISC,
                                getEnchantment(Enchantments.PUNCH), 2,
                                Ingredient.ofItem(ToolItemsME.STEEL_CHISEL), 7)
                        .addWord("forceful")
                        .addWord("draw")
                        .criterion(hasItem(ResourceItemsME.RUBY),
                                conditionsFromItem(ResourceItemsME.RUBY))
                        .offerTo(exporter, String.valueOf(Identifier.of(MiddleEarth.MOD_ID, "inscription_punch_2")));
                //endregion

                //region EFFICIENCY
                InscriptionRecipeJsonBuilder.createInscriptionRecipe(itemLookup, RecipeCategory.MISC,
                                getEnchantment(Enchantments.EFFICIENCY), 1,
                                Ingredient.ofItem(ToolItemsME.IRON_CHISEL), 3)
                        .addWord("swift")
                        .addWord("collector")
                        .criterion(hasItem(ResourceItemsME.ADAMANT),
                                conditionsFromItem(ResourceItemsME.ADAMANT))
                        .offerTo(exporter, String.valueOf(Identifier.of(MiddleEarth.MOD_ID, "inscription_efficiency_1")));

                InscriptionRecipeJsonBuilder.createInscriptionRecipe(itemLookup, RecipeCategory.MISC,
                                getEnchantment(Enchantments.EFFICIENCY), 2,
                                Ingredient.ofItem(ToolItemsME.IRON_CHISEL), 5)
                        .addWord("swift")
                        .addWord("collector")
                        .criterion(hasItem(ResourceItemsME.ADAMANT),
                                conditionsFromItem(ResourceItemsME.ADAMANT))
                        .offerTo(exporter, String.valueOf(Identifier.of(MiddleEarth.MOD_ID, "inscription_efficiency_2")));

                InscriptionRecipeJsonBuilder.createInscriptionRecipe(itemLookup, RecipeCategory.MISC,
                                getEnchantment(Enchantments.EFFICIENCY), 3,
                                Ingredient.ofItem(ToolItemsME.STEEL_CHISEL), 7)
                        .addWord("swift")
                        .addWord("collector")
                        .criterion(hasItem(ResourceItemsME.ADAMANT),
                                conditionsFromItem(ResourceItemsME.ADAMANT))
                        .offerTo(exporter, String.valueOf(Identifier.of(MiddleEarth.MOD_ID, "inscription_efficiency_3")));

                InscriptionRecipeJsonBuilder.createInscriptionRecipe(itemLookup, RecipeCategory.MISC,
                                getEnchantment(Enchantments.EFFICIENCY), 4,
                                Ingredient.ofItem(ToolItemsME.STEEL_CHISEL), 9)
                        .addWord("swift")
                        .addWord("collector")
                        .criterion(hasItem(ResourceItemsME.ADAMANT),
                                conditionsFromItem(ResourceItemsME.ADAMANT))
                        .offerTo(exporter, String.valueOf(Identifier.of(MiddleEarth.MOD_ID, "inscription_efficiency_4")));

                InscriptionRecipeJsonBuilder.createInscriptionRecipe(itemLookup, RecipeCategory.MISC,
                                getEnchantment(Enchantments.EFFICIENCY), 5,
                                Ingredient.ofItem(ToolItemsME.MITHRIL_CHISEL), 11)
                        .addWord("swift")
                        .addWord("collector")
                        .criterion(hasItem(ResourceItemsME.ADAMANT),
                                conditionsFromItem(ResourceItemsME.ADAMANT))
                        .offerTo(exporter, String.valueOf(Identifier.of(MiddleEarth.MOD_ID, "inscription_efficiency_5")));
                //endregion

                //region FORTUNE
                InscriptionRecipeJsonBuilder.createInscriptionRecipe(itemLookup, RecipeCategory.MISC,
                                getEnchantment(Enchantments.FORTUNE), 1,
                                Ingredient.ofItem(ToolItemsME.STEEL_CHISEL), 11)
                        .addWord("gifted")
                        .addWord("blessing")
                        .addWord("collector")
                        .criterion(hasItem(Items.EMERALD),
                                conditionsFromItem(Items.EMERALD))
                        .offerTo(exporter, String.valueOf(Identifier.of(MiddleEarth.MOD_ID, "inscription_fortune_1")));

                InscriptionRecipeJsonBuilder.createInscriptionRecipe(itemLookup, RecipeCategory.MISC,
                                getEnchantment(Enchantments.FORTUNE), 2,
                                Ingredient.ofItem(ToolItemsME.STEEL_CHISEL), 13)
                        .addWord("gifted")
                        .addWord("blessing")
                        .addWord("collector")
                        .criterion(hasItem(Items.EMERALD),
                                conditionsFromItem(Items.EMERALD))
                        .offerTo(exporter, String.valueOf(Identifier.of(MiddleEarth.MOD_ID, "inscription_fortune_2")));

                InscriptionRecipeJsonBuilder.createInscriptionRecipe(itemLookup, RecipeCategory.MISC,
                                getEnchantment(Enchantments.FORTUNE), 3,
                                Ingredient.ofItem(ToolItemsME.MITHRIL_CHISEL), 15)
                        .addWord("gifted")
                        .addWord("blessing")
                        .addWord("collector")
                        .criterion(hasItem(Items.EMERALD),
                                conditionsFromItem(Items.EMERALD))
                        .offerTo(exporter, String.valueOf(Identifier.of(MiddleEarth.MOD_ID, "inscription_fortune_3")));
                //endregion

                //region SILK TOUCH
                InscriptionRecipeJsonBuilder.createInscriptionRecipe(itemLookup, RecipeCategory.MISC,
                                getEnchantment(Enchantments.SILK_TOUCH), 1,
                                Ingredient.ofItem(ToolItemsME.STEEL_CHISEL), 10)
                        .addWord("careful")
                        .addWord("collector")
                        .criterion(hasItem(Items.EMERALD),
                                conditionsFromItem(Items.EMERALD))
                        .offerTo(exporter, String.valueOf(Identifier.of(MiddleEarth.MOD_ID, "inscription_silk_touch")));
                //endregion

                //region HEWING
                InscriptionRecipeJsonBuilder.createInscriptionRecipe(itemLookup, RecipeCategory.MISC,
                                getEnchantment(EnchantmentsME.HEWING), 1,
                                Ingredient.ofItem(ToolItemsME.IRON_CHISEL), 5)
                        .addWord("broad")
                        .addWord("collector")
                        .criterion(hasItem(Items.EMERALD),
                                conditionsFromItem(Items.EMERALD))
                        .offerTo(exporter, String.valueOf(Identifier.of(MiddleEarth.MOD_ID, "inscription_hewing_1")));

                InscriptionRecipeJsonBuilder.createInscriptionRecipe(itemLookup, RecipeCategory.MISC,
                                getEnchantment(EnchantmentsME.HEWING), 2,
                                Ingredient.ofItem(ToolItemsME.STEEL_CHISEL), 7)
                        .addWord("broad")
                        .addWord("collector")
                        .criterion(hasItem(Items.EMERALD),
                                conditionsFromItem(Items.EMERALD))
                        .offerTo(exporter, String.valueOf(Identifier.of(MiddleEarth.MOD_ID, "inscription_hewing_2")));

                InscriptionRecipeJsonBuilder.createInscriptionRecipe(itemLookup, RecipeCategory.MISC,
                                getEnchantment(EnchantmentsME.HEWING), 3,
                                Ingredient.ofItem(ToolItemsME.STEEL_CHISEL), 9)
                        .addWord("broad")
                        .addWord("collector")
                        .criterion(hasItem(Items.EMERALD),
                                conditionsFromItem(Items.EMERALD))
                        .offerTo(exporter, String.valueOf(Identifier.of(MiddleEarth.MOD_ID, "inscription_hewing_3")));
                //endregion
            }
        };
    }

}
