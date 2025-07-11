package net.sevenstars.of_beasts_and_wild_things.datageneration.providers;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.sevenstars.of_beasts_and_wild_things.OfBeastsAndWildThings;
import net.sevenstars.of_beasts_and_wild_things.entity.ModEntities;

import java.util.concurrent.CompletableFuture;

public class EntityTagProvider extends FabricTagProvider.EntityTypeTagProvider {

    public EntityTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
        var swan_prey = valueLookupBuilder(TagKey.of(RegistryKeys.ENTITY_TYPE, Identifier.of(OfBeastsAndWildThings.MOD_ID, "swan_prey")));

        swan_prey.add(ModEntities.SNAIL);
        swan_prey.add(EntityType.TADPOLE);
    }
}
