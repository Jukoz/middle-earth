package net.sevenstars.middleearth.compat;

import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import me.shedaniel.rei.api.client.registry.screen.ScreenRegistry;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.sevenstars.middleearth.block.registration.ModDecorativeBlocks;
import net.sevenstars.middleearth.compat.artisantable.ArtisanTableCategory;
import net.sevenstars.middleearth.compat.artisantable.ArtisanTableDisplay;
import net.sevenstars.middleearth.gui.artisantable.ArtisanTableScreen;
import net.sevenstars.middleearth.recipe.ArtisanRecipe;

public class REIClientPluginME implements REIClientPlugin {

    @Override
    public void registerCategories(CategoryRegistry registry) {
        registry.add(new ArtisanTableCategory());
        registry.addWorkstations(ArtisanTableCategory.ARTISAN_TABLE, EntryStacks.of(ModDecorativeBlocks.ARTISAN_TABLE));
    }

    @Override
    public void registerDisplays(DisplayRegistry registry) {
        REIClientPlugin.super.registerDisplays(registry);
        registry.beginFiller(ArtisanRecipe.class)
                .fill(ArtisanTableDisplay::new);
    }

    @Override
    public void registerScreens(ScreenRegistry registry) {
        REIClientPlugin.super.registerScreens(registry);
        registry.registerClickArea(screen -> new Rectangle(75, 30, 20, 30), ArtisanTableScreen.class, ArtisanTableCategory.ARTISAN_TABLE);
    }
}
