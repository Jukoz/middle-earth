package net.sevenstars.of_beasts_and_wild_things.datageneration.providers;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.sevenstars.of_beasts_and_wild_things.OfBeastsAndWildThings;
import net.sevenstars.of_beasts_and_wild_things.entity.EntitiesWT;

import java.util.concurrent.CompletableFuture;

public class EntityTagProvider extends EntityTypeTagsProvider {

    public EntityTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture,
                             ExistingFileHelper existingFileHelper) {
        super(output, registriesFuture, OfBeastsAndWildThings.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider wrapperLookup) {
        tag(TagKey.create(Registries.ENTITY_TYPE, OfBeastsAndWildThings.of("swan_food")))
                .add(EntitiesWT.SNAIL)
                .add(EntityType.TADPOLE);
    }
}
