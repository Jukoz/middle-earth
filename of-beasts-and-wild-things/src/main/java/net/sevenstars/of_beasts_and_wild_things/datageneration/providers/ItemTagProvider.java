package net.sevenstars.of_beasts_and_wild_things.datageneration.providers;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.sevenstars.of_beasts_and_wild_things.OfBeastsAndWildThings;
import net.sevenstars.of_beasts_and_wild_things.entity.ModEntities;
import net.sevenstars.of_beasts_and_wild_things.item.ModItems;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ItemTagProvider extends FabricTagProvider.ItemTagProvider {

    public ItemTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
        TagKey<Item> crab_boiling_ingredients = TagKey.of(RegistryKeys.ITEM, Identifier.of(OfBeastsAndWildThings.MOD_ID, "crab_boiling_ingredients"));

        valueLookupBuilder(crab_boiling_ingredients).add(Items.CARROT, Items.GOLD_NUGGET, ModItems.SWAN_EGG);
    }
}
