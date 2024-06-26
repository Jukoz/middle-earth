package net.jukoz.me.compat;

import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import me.shedaniel.rei.api.client.registry.screen.ScreenRegistry;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.jukoz.me.block.ModDecorativeBlocks;
import net.jukoz.me.compat.alloyFurnace.AlloyFurnaceCategory;
import net.jukoz.me.compat.alloyFurnace.AlloyFurnaceDisplay;
import net.jukoz.me.compat.artisanTable.ArtisanTableCategory;
import net.jukoz.me.compat.artisanTable.ArtisanTableDisplay;
import net.jukoz.me.gui.alloyfurnace.AlloyFurnaceScreen;
import net.jukoz.me.gui.artisantable.ArtisanTableScreen;
import net.jukoz.me.recipe.AlloyRecipe;
import net.jukoz.me.recipe.ArtisanRecipe;

public class ModREIClientPlugin implements REIClientPlugin {

    @Override
    public void registerCategories(CategoryRegistry registry) {
        registry.add(new ArtisanTableCategory());
        registry.add(new AlloyFurnaceCategory());

        registry.addWorkstations(ArtisanTableCategory.ARTISAN_TABLE, EntryStacks.of(ModDecorativeBlocks.ARTISAN_TABLE));
        registry.addWorkstations(AlloyFurnaceCategory.ALLOY_FURNACE, EntryStacks.of(ModDecorativeBlocks.FORGE));
    }

    @Override
    public void registerDisplays(DisplayRegistry registry) {
        registry.registerRecipeFiller(ArtisanRecipe.class, ArtisanRecipe.Type.INSTANCE, ArtisanTableDisplay::new);
        registry.registerRecipeFiller(AlloyRecipe.class, AlloyRecipe.Type.INSTANCE, AlloyFurnaceDisplay::new);
    }

    @Override
    public void registerScreens(ScreenRegistry registry) {
        registry.registerClickArea(screen -> new Rectangle(75, 30, 20, 30), ArtisanTableScreen.class, ArtisanTableCategory.ARTISAN_TABLE);
        registry.registerClickArea(screen -> new Rectangle(75, 30, 20, 30), AlloyFurnaceScreen.class, AlloyFurnaceCategory.ALLOY_FURNACE);
    }
}
