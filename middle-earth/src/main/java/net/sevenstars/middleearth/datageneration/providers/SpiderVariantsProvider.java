package net.sevenstars.middleearth.datageneration.providers;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.minecraft.registry.RegistryWrapper;
import net.sevenstars.middleearth.entity.spider.SpiderVariants;

import java.util.concurrent.CompletableFuture;

public class SpiderVariantsProvider extends FabricDynamicRegistryProvider {
    public SpiderVariantsProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup registries, Entries entries) {
        entries.addAll(registries.getOrThrow(SpiderVariants.KEY));
    }

    @Override
    public String getName() {
        return "SpiderVariants";
    }
}
