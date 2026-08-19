package net.sevenstars.middleearth.compat;

import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import me.shedaniel.rei.api.client.registry.screen.ScreenRegistry;
import me.shedaniel.rei.api.common.util.EntryStacks;
import me.shedaniel.rei.forge.REIPluginClient;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.sevenstars.middleearth.block.registration.ModDecorativeBlocks;
import net.sevenstars.middleearth.compat.artisantable.ArtisanTableCategory;
import net.sevenstars.middleearth.compat.artisantable.ArtisanTableDisplay;
import net.sevenstars.middleearth.compat.forge.AlloyingCategory;
import net.sevenstars.middleearth.compat.forge.AlloyingDisplay;
import net.sevenstars.middleearth.gui.artisantable.ArtisanTableScreen;
import net.sevenstars.middleearth.gui.forge.ForgeAlloyingScreen;
import net.sevenstars.middleearth.recipe.AlloyingRecipe;
import net.sevenstars.middleearth.recipe.ArtisanRecipe;

@OnlyIn(Dist.CLIENT)
@REIPluginClient
public final class REIClientPluginME implements REIClientPlugin {
    @Override
    public void registerCategories(CategoryRegistry registry) {
        registry.add(new ArtisanTableCategory());
        registry.add(new AlloyingCategory());
        registry.addWorkstations(
                REICommonPluginME.ARTISAN_TABLE_CATEGORY,
                EntryStacks.of(ModDecorativeBlocks.ARTISAN_TABLE)
        );
        registry.addWorkstations(
                REICommonPluginME.FORGE_CATEGORY,
                EntryStacks.of(ModDecorativeBlocks.FORGE)
        );
    }

    @Override
    public void registerDisplays(DisplayRegistry registry) {
        registry.registerRecipeFiller(
                ArtisanRecipe.class,
                ArtisanRecipe.Type.INSTANCE,
                holder -> new ArtisanTableDisplay(holder.value())
        );
        registry.registerRecipeFiller(
                AlloyingRecipe.class,
                AlloyingRecipe.Type.INSTANCE,
                holder -> holder.value().output.contains("nugget")
                        ? null
                        : new AlloyingDisplay(holder.value())
        );
    }

    @Override
    public void registerScreens(ScreenRegistry registry) {
        registry.registerClickArea(
                screen -> new Rectangle(75, 30, 20, 30),
                ArtisanTableScreen.class,
                REICommonPluginME.ARTISAN_TABLE_CATEGORY
        );
        registry.registerClickArea(
                screen -> new Rectangle(75, 30, 20, 30),
                ForgeAlloyingScreen.class,
                REICommonPluginME.FORGE_CATEGORY
        );
    }
}
