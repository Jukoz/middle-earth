package net.sevenstars.middleearth.compat;

import dev.architectury.utils.GameInstance;
import me.shedaniel.rei.api.common.display.DisplaySerializerRegistry;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.plugins.REICommonPlugin;
import me.shedaniel.rei.api.common.registry.display.ServerDisplayRegistry;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import net.minecraft.item.Items;
import net.minecraft.loot.entry.LootPoolEntryTypes;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.text.Text;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.compat.artisantable.ArtisanTableDisplay;
import net.sevenstars.middleearth.item.EquipmentItemsME;
import net.sevenstars.middleearth.item.ResourceItemsME;
import net.sevenstars.middleearth.recipe.ArtisanRecipe;
import net.sevenstars.middleearth.recipe.RecipesME;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class REICommonPluginME implements REICommonPlugin {

    @Override
    public void registerDisplaySerializer(DisplaySerializerRegistry registry) {
        REICommonPlugin.super.registerDisplaySerializer(registry);
        registry.register(MiddleEarth.of("artisan_table"), ArtisanTableDisplay.SERIALIZER);
    }

    @Override
    public void registerDisplays(ServerDisplayRegistry registry) {
        REICommonPlugin.super.registerDisplays(registry);

        //registry.beginRecipeFiller(ArtisanRecipe.class)
        //        .filterType(RecipesME.ARTISAN_TABLE_SUPPLIER.get())
        //        .fill(ArtisanTableDisplay::new);

        //registry.beginRecipeFiller(ArtisanRecipe.class).fill(ArtisanTableDisplay::new);

        //registry.beginRecipeFiller(ArtisanRecipe.class)
        //        .filterType(RecipesME.ARTISAN_TABLE_SUPPLIER.get())
        //        .filterWithReason((RecipeHolder<ArtisanRecipe> holder, DisplayAdditionReasons reasons) ->
        //                reasons.has(DisplayAdditionReason.RECIPE_MANAGER))
        //        .fill((RecipeHolder<ArtisanRecipe> holder) -> ArtisanTableDisplay.of(holder.value()));

        // Bad code... But it's the only one that works
        Collection<RecipeEntry<ArtisanRecipe>> recipeEntryList = GameInstance.getServer().getRecipeManager().getAllOfType(RecipesME.ARTISAN_TABLE_SUPPLIER.get());  //.values().parallelStream().sorted(RECIPE_COMPARATOR).toList();
        for(RecipeEntry<ArtisanRecipe> recipeEntry : recipeEntryList) {
            ArtisanRecipe artisanRecipe = recipeEntry.value();
            if(artisanRecipe.getOutput().getItem().getName().toString().contains("gondorian")) {
                int i = 2;
            }
            List<EntryIngredient> inputs = ArtisanTableDisplay.getInputs(artisanRecipe);
            registry.add(new ArtisanTableDisplay(inputs, List.of(EntryIngredients.of(artisanRecipe.getOutput()))));
        }

        registry.add(new ArtisanTableDisplay(List.of(EntryIngredients.of(ResourceItemsME.FABRIC), EntryIngredients.of(ResourceItemsME.FABRIC),
                EntryIngredients.of(Items.LEATHER), EntryIngredients.of(Items.LEATHER), EntryIngredients.of(Items.LEATHER),
                EntryIngredients.of(ResourceItemsME.FABRIC), EntryIngredients.of(Items.LEATHER), EntryIngredients.of(ResourceItemsME.FABRIC)),
                List.of(EntryIngredients.of(EquipmentItemsME.MORDOR_LEATHER_CUIRASS))));
    }
}
