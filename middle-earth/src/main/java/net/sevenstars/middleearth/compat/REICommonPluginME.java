package net.sevenstars.middleearth.compat;

import me.shedaniel.rei.api.common.display.DisplaySerializerRegistry;
import me.shedaniel.rei.api.common.plugins.REICommonPlugin;
import me.shedaniel.rei.api.common.registry.display.ServerDisplayRegistry;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import net.minecraft.item.Items;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.compat.artisantable.ArtisanTableDisplay;
import net.sevenstars.middleearth.item.EquipmentItemsME;
import net.sevenstars.middleearth.item.ResourceItemsME;
import net.sevenstars.middleearth.recipe.ArtisanRecipe;
import net.sevenstars.middleearth.recipe.RecipesME;

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
        registry.beginRecipeFiller(ArtisanRecipe.class)
                .filterType(RecipesME.ARTISAN_TABLE_SUPPLIER.get())
                .fill(ArtisanTableDisplay::new);

        registry.add(new ArtisanTableDisplay(List.of(EntryIngredients.of(ResourceItemsME.FABRIC), EntryIngredients.of(ResourceItemsME.FABRIC),
                EntryIngredients.of(Items.LEATHER), EntryIngredients.of(Items.LEATHER), EntryIngredients.of(Items.LEATHER),
                EntryIngredients.of(ResourceItemsME.FABRIC), EntryIngredients.of(Items.LEATHER), EntryIngredients.of(ResourceItemsME.FABRIC)),
                List.of(EntryIngredients.of(EquipmentItemsME.MORDOR_LEATHER_CUIRASS))));
    }
}
