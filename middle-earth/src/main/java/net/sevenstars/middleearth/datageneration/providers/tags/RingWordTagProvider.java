package net.sevenstars.middleearth.datageneration.providers.tags;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.registry.RegistryWrapper;
import net.sevenstars.middleearth.registries.DynamicRegistriesME;
import net.sevenstars.middleearth.registries.content.rings.RingWordRegistry;
import net.sevenstars.middleearth.resources.datas.rings.RingWord;

import java.util.concurrent.CompletableFuture;

/** Which inscription words each ring theme allows. */
public class RingWordTagProvider extends FabricTagProvider<RingWord> {
    public RingWordTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, DynamicRegistriesME.RING_WORD, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup lookup) {
        RingWordRegistry.ALL.forEach(word -> RingWordRegistry.THEMES.get(word).forEach(tag -> builder(tag).add(word)));
    }
}
