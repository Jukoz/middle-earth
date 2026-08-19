package net.sevenstars.middleearth.compat;

import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.DisplaySerializerRegistry;
import me.shedaniel.rei.api.common.plugins.REIServerPlugin;
import me.shedaniel.rei.forge.REIPluginCommon;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.compat.artisantable.ArtisanTableDisplay;
import net.sevenstars.middleearth.compat.forge.AlloyingDisplay;

@REIPluginCommon
public final class REICommonPluginME implements REIServerPlugin {
    public static final CategoryIdentifier<ArtisanTableDisplay> ARTISAN_TABLE_CATEGORY =
            CategoryIdentifier.of(MiddleEarth.MOD_ID, "artisan_table");
    public static final CategoryIdentifier<AlloyingDisplay> FORGE_CATEGORY =
            CategoryIdentifier.of(MiddleEarth.MOD_ID, "forge");

    @Override
    public void registerDisplaySerializer(DisplaySerializerRegistry registry) {
        registry.register(ARTISAN_TABLE_CATEGORY, ArtisanTableDisplay.SERIALIZER);
        registry.register(FORGE_CATEGORY, AlloyingDisplay.SERIALIZER);
    }
}
