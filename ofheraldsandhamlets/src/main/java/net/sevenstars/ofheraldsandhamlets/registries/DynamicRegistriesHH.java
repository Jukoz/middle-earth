package net.sevenstars.ofheraldsandhamlets.registries;

import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.event.registry.DynamicRegistries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryBuilder;
import net.minecraft.registry.RegistryKey;
import net.sevenstars.ofheraldsandhamlets.OfHeraldsAndHamlets;
import net.sevenstars.ofheraldsandhamlets.datageneration.providers.BannerProvider;
import net.sevenstars.ofheraldsandhamlets.datageneration.providers.DispositionProvider;
import net.sevenstars.ofheraldsandhamlets.datageneration.providers.FactionProvider;
import net.sevenstars.ofheraldsandhamlets.datageneration.providers.SpawnProvider;
import net.sevenstars.ofheraldsandhamlets.dtos.disposition.Disposition;
import net.sevenstars.ofheraldsandhamlets.dtos.faction.Faction;
import net.sevenstars.ofheraldsandhamlets.dtos.banner.Banner;
import net.sevenstars.ofheraldsandhamlets.dtos.spawn.Spawn;
import net.sevenstars.ofheraldsandhamlets.registries.custom.BannerRegistryHH;
import net.sevenstars.ofheraldsandhamlets.registries.custom.DispositionRegistryHH;
import net.sevenstars.ofheraldsandhamlets.registries.custom.FactionRegistryHH;
import net.sevenstars.ofheraldsandhamlets.registries.custom.SpawnRegistryHH;

public class DynamicRegistriesHH {
    public static final RegistryKey<Registry<Disposition>> DISPOSITION = of("disposition");
    public static final RegistryKey<Registry<Faction>> FACTION = of("faction");
    public static final RegistryKey<Registry<Banner>> BANNER = of("banner");
    public static final RegistryKey<Registry<Spawn>> SPAWN = of("spawn");

    public static void register() {
        OfHeraldsAndHamlets.logRegistryMsg("Dynamic Registries");
        DynamicRegistries.registerSynced(DISPOSITION, Disposition.CODEC);
        DynamicRegistries.registerSynced(FACTION, Faction.CODEC);
        DynamicRegistries.registerSynced(BANNER, Banner.CODEC);
        DynamicRegistries.registerSynced(SPAWN, Spawn.CODEC);
    }

    public static void prepareBoostrap(RegistryBuilder registryBuilder) {
        registryBuilder.addRegistry(DISPOSITION, DispositionRegistryHH::bootstrap);
        registryBuilder.addRegistry(FACTION, FactionRegistryHH::bootstrap);
        registryBuilder.addRegistry(BANNER, BannerRegistryHH::bootstrap);
        registryBuilder.addRegistry(SPAWN, SpawnRegistryHH::bootstrap);
    }

    public static void addProviders(FabricDataGenerator.Pack pack) {
        pack.addProvider(DispositionProvider::new);
        pack.addProvider(FactionProvider::new);
        pack.addProvider(BannerProvider::new);
        pack.addProvider(SpawnProvider::new);
    }


    public static <T> RegistryKey<Registry<T>> of(String registry) {
        return RegistryKey.ofRegistry(OfHeraldsAndHamlets.id(registry));
    }
}
