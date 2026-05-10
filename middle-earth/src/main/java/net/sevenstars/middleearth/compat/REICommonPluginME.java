package net.sevenstars.middleearth.compat;

import dev.architectury.utils.GameInstance;
import me.shedaniel.rei.api.common.display.DisplaySerializerRegistry;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.plugins.REICommonPlugin;
import me.shedaniel.rei.api.common.registry.display.ServerDisplayRegistry;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import net.minecraft.item.Items;
import net.minecraft.recipe.RecipeEntry;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.compat.artisantable.ArtisanTableDisplay;
import net.sevenstars.middleearth.compat.forge.AlloyingDisplay;
import net.sevenstars.middleearth.item.EquipmentItemsME;
import net.sevenstars.middleearth.item.ResourceItemsME;
import net.sevenstars.middleearth.recipe.AlloyingRecipe;
import net.sevenstars.middleearth.recipe.ArtisanRecipe;
import net.sevenstars.middleearth.recipe.RecipesME;

import java.util.Collection;
import java.util.List;

public class REICommonPluginME implements REICommonPlugin {

    @Override
    public void registerDisplaySerializer(DisplaySerializerRegistry registry) {
        REICommonPlugin.super.registerDisplaySerializer(registry);
        registry.register(MiddleEarth.of("artisan_table"), ArtisanTableDisplay.SERIALIZER);
        registry.register(MiddleEarth.of("forge"), AlloyingDisplay.SERIALIZER);
    }

    @Override
    public void registerDisplays(ServerDisplayRegistry registry) {
        REICommonPlugin.super.registerDisplays(registry);
        // Keep this evil code just in case, to avoid another trip in hell.
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
        Collection<RecipeEntry<ArtisanRecipe>> recipeEntryList = GameInstance.getServer().getRecipeManager().getAllOfType(RecipesME.ARTISAN_TABLE_SUPPLIER.get());
        for(RecipeEntry<ArtisanRecipe> recipeEntry : recipeEntryList) {
            ArtisanRecipe artisanRecipe = recipeEntry.value();
            List<EntryIngredient> inputs = ArtisanTableDisplay.getInputs(artisanRecipe);
            registry.add(new ArtisanTableDisplay(inputs, List.of(EntryIngredients.of(artisanRecipe.getOutput())), artisanRecipe.category));
        }

        Collection<RecipeEntry<AlloyingRecipe>> alloyRecipeEntryList = GameInstance.getServer().getRecipeManager().getAllOfType(RecipesME.FORGE);
        for(RecipeEntry<AlloyingRecipe> recipeEntry : alloyRecipeEntryList) {
            AlloyingRecipe alloyRecipe = recipeEntry.value();
            List<EntryIngredient> inputs = AlloyingDisplay.getInputs(alloyRecipe);
            registry.add(new AlloyingDisplay(inputs, alloyRecipe.output, alloyRecipe.amount));
        }
    }
}
