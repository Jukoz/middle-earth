package net.sevenstars.of_beasts_and_wild_things.datageneration;

import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.client.data.BlockStateModelGenerator;
import net.minecraft.client.data.ItemModelGenerator;
import net.minecraft.item.Item;
import net.sevenstars.of_beasts_and_wild_things.datageneration.content.CustomItemModels;
import net.sevenstars.of_beasts_and_wild_things.datageneration.models.SimpleSpawnEggItemModel;

public class ModelProvider extends FabricModelProvider {
    public ModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {

    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        for (Item item : SimpleSpawnEggItemModel.items) {
            itemModelGenerator.register(item, CustomItemModels.TEMPLATE_SPAWN_EGG);
        }
    }
}
