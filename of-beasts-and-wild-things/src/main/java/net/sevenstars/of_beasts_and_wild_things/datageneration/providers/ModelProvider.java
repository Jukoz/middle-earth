package net.sevenstars.of_beasts_and_wild_things.datageneration.providers;

import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.sevenstars.of_beasts_and_wild_things.OfBeastsAndWildThings;
import net.sevenstars.of_beasts_and_wild_things.datageneration.models.SimpleItemModels;

public class ModelProvider extends ItemModelProvider {
    public ModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, OfBeastsAndWildThings.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        for (Item item : SimpleItemModels.items) {
            basicItem(item);
        }
    }
}
