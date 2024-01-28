package net.jukoz.me.datageneration;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.datageneration.content.tags.*;
import net.jukoz.me.item.ModRessourceItems;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

import java.util.concurrent.CompletableFuture;

public class ItemTagProvider extends FabricTagProvider.ItemTagProvider {

    public ItemTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup arg) {
        var bones = getOrCreateTagBuilder(TagKey.of(RegistryKeys.ITEM, new Identifier(MiddleEarth.MOD_ID, "bones")));

        getOrCreateTagBuilder(TagKey.of(RegistryKeys.ITEM, new Identifier("minecraft", "planks"))).add(Planks.getItemPlanks().toArray(new Item[0]));

        getOrCreateTagBuilder(TagKey.of(RegistryKeys.ITEM, new Identifier("minecraft", "logs"))).add(Logs.getItemPlanks().toArray(new Item[0]));

        bones.add(Items.BONE);
        bones.add(ModRessourceItems.ORC_BONE);
        bones.add(ModRessourceItems.WARG_BONE);
    }
}
