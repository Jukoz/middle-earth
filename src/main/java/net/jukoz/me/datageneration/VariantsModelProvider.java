package net.jukoz.me.datageneration;

import net.fabricmc.fabric.api.client.model.ExtraModelProvider;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.datageneration.content.models.SimpleBigItemModel;
import net.jukoz.me.datageneration.content.models.SimpleSpearModel;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.item.Item;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;

import java.util.function.Consumer;

public class VariantsModelProvider implements ExtraModelProvider {

    @Override
    public void provideExtraModels(ResourceManager manager, Consumer<Identifier> out) {
        for(Item item : SimpleBigItemModel.items) {
            out.accept(getInventoryModelIdentifierVariant(item));
        }

        for(Item item : SimpleSpearModel.items) {
            out.accept(getInventoryModelIdentifierVariant(item));
        }
    }

    public static ModelIdentifier getInventoryModelIdentifierVariant(Item item) {
        return new ModelIdentifier(
                MiddleEarth.MOD_ID, VariantsModelProvider.getKey(item.getTranslationKey()) + "_inventory", "inventory");
    }

    public static String getKey(String path) {
        return path.substring(path.lastIndexOf('.') + 1).trim();
    }
}
