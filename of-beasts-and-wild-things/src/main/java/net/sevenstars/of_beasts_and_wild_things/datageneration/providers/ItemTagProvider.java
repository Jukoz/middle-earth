package net.sevenstars.of_beasts_and_wild_things.datageneration.providers;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.sevenstars.of_beasts_and_wild_things.OfBeastsAndWildThings;

import java.util.concurrent.CompletableFuture;

public class ItemTagProvider extends ItemTagsProvider {

    public ItemTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture,
                           CompletableFuture<TagsProvider.TagLookup<Block>> blockTags,
                           ExistingFileHelper existingFileHelper) {
        super(output, registriesFuture, blockTags, OfBeastsAndWildThings.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider wrapperLookup) {
        TagKey<Item> swan_food = TagKey.create(Registries.ITEM, OfBeastsAndWildThings.of("swan_food"));

        tag(swan_food).add(Items.TADPOLE_BUCKET);
    }
}
