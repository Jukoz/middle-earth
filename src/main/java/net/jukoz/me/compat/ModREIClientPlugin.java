package net.jukoz.me.compat;

import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import me.shedaniel.rei.api.client.registry.screen.ScreenRegistry;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.jukoz.me.block.ModDecorativeBlocks;
import net.jukoz.me.compat.forge.ForgeCategory;
import net.jukoz.me.compat.forge.ForgeDisplay;
import net.jukoz.me.compat.artisanTable.ArtisanTableCategory;
import net.jukoz.me.compat.artisanTable.ArtisanTableDisplay;
import net.jukoz.me.gui.forge.ForgeScreen;
import net.jukoz.me.gui.artisantable.ArtisanTableScreen;
import net.jukoz.me.recipe.AlloyingRecipe;
import net.jukoz.me.recipe.ArtisanRecipe;

public class ModREIClientPlugin implements REIClientPlugin {

    @Override
    public void registerCategories(CategoryRegistry registry) {
        registry.add(new ArtisanTableCategory());
        registry.add(new ForgeCategory());

        registry.addWorkstations(ArtisanTableCategory.ARTISAN_TABLE, EntryStacks.of(ModDecorativeBlocks.ARTISAN_TABLE));
        registry.addWorkstations(ForgeCategory.FORGE, EntryStacks.of(ModDecorativeBlocks.FORGE));
    }

    @Override
    public void registerDisplays(DisplayRegistry registry) {
        registry.registerRecipeFiller(ArtisanRecipe.class, ArtisanRecipe.Type.INSTANCE, ArtisanTableDisplay::new);
        registry.registerRecipeFiller(AlloyingRecipe.class, AlloyingRecipe.Type.INSTANCE, ForgeDisplay::new);
    }

    @Override
    public void registerScreens(ScreenRegistry registry) {
        registry.registerClickArea(screen -> new Rectangle(75, 30, 20, 30), ArtisanTableScreen.class, ArtisanTableCategory.ARTISAN_TABLE);
        registry.registerClickArea(screen -> new Rectangle(75, 30, 20, 30), ForgeScreen.class, ForgeCategory.FORGE);
    }
}
