package net.sevenstars.middleearth.compat;

import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import me.shedaniel.rei.api.client.registry.screen.OverlayDecider;
import me.shedaniel.rei.api.client.registry.screen.ScreenRegistry;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.ActionResult;
import net.sevenstars.middleearth.block.registration.DecorativeBlockRegistryME;
import net.sevenstars.middleearth.compat.artisantable.ArtisanTableCategory;
import net.sevenstars.middleearth.compat.artisantable.ArtisanTableDisplay;
import net.sevenstars.middleearth.compat.forge.AlloyingCategory;
import net.sevenstars.middleearth.compat.forge.AlloyingDisplay;
import net.sevenstars.middleearth.gui.artisantable.ArtisanTableScreen;
import net.sevenstars.middleearth.gui.forge.ForgeAlloyingScreen;
import net.sevenstars.middleearth.gui.structuremanager.bedtag.BedTagScreen;
import net.sevenstars.middleearth.gui.structuremanager.structuremarker.StructureMarkerScreen;
import net.sevenstars.middleearth.recipe.AlloyingRecipe;
import net.sevenstars.middleearth.recipe.ArtisanRecipe;

@Environment(EnvType.CLIENT)
public class REIClientPluginME implements REIClientPlugin {

    @Override
    public void registerCategories(CategoryRegistry registry) {
        registry.add(new ArtisanTableCategory());
        registry.add(new AlloyingCategory());
        registry.addWorkstations(REICommonPluginME.ARTISAN_TABLE_CATEGORY, EntryStacks.of(DecorativeBlockRegistryME.ARTISAN_TABLE));
        registry.addWorkstations(REICommonPluginME.FORGE_CATEGORY, EntryStacks.of(DecorativeBlockRegistryME.FORGE));
    }

    @Override
    public void registerDisplays(DisplayRegistry registry) {
        REIClientPlugin.super.registerDisplays(registry);
        registry.beginFiller(ArtisanRecipe.class)
                .fill(ArtisanTableDisplay::new);
        registry.beginFiller(AlloyingRecipe.class)
                .fill(AlloyingDisplay::new);
    }

    @Override
    public void registerScreens(ScreenRegistry registry) {
        REIClientPlugin.super.registerScreens(registry);
        // These screens are for tagging beds and placing markers, not for looking up items
        registry.registerDecider(new OverlayDecider() {
            @Override
            public <R extends Screen> boolean isHandingScreen(Class<R> screen) {
                return BedTagScreen.class.isAssignableFrom(screen) || StructureMarkerScreen.class.isAssignableFrom(screen);
            }

            @Override
            public <R extends Screen> ActionResult shouldScreenBeOverlaid(R screen) {
                return ActionResult.FAIL;
            }
        });
        registry.registerClickArea(screen -> new Rectangle(75, 30, 20, 30), ArtisanTableScreen.class, REICommonPluginME.ARTISAN_TABLE_CATEGORY);
        registry.registerClickArea(screen -> new Rectangle(75, 30, 20, 30), ForgeAlloyingScreen.class, REICommonPluginME.FORGE_CATEGORY);
    }
}
