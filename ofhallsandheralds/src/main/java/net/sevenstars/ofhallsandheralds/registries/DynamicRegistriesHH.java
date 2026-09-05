package net.sevenstars.ofhallsandheralds.registries;

import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.event.registry.DynamicRegistries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryBuilder;
import net.minecraft.registry.RegistryKey;
import net.sevenstars.ofhallsandheralds.OfHallsAndHeralds;
import net.sevenstars.ofhallsandheralds.datageneration.providers.BannerProvider;
import net.sevenstars.ofhallsandheralds.datageneration.providers.FactionProvider;
import net.sevenstars.ofhallsandheralds.datageneration.providers.SpawnProvider;
import net.sevenstars.ofhallsandheralds.dtos.faction.Faction;
import net.sevenstars.ofhallsandheralds.dtos.banner.Banner;
import net.sevenstars.ofhallsandheralds.dtos.spawn.Spawn;
import net.sevenstars.ofhallsandheralds.registries.custom.BannerRegistryHH;
import net.sevenstars.ofhallsandheralds.registries.custom.FactionRegistryHH;
import net.sevenstars.ofhallsandheralds.registries.custom.SpawnRegistryHH;

public class DynamicRegistriesHH {
    public static final RegistryKey<Registry<Faction>> FACTION = of("faction");
    public static final RegistryKey<Registry<Banner>> BANNER = of("banner");
    public static final RegistryKey<Registry<Spawn>> SPAWN = of("spawn");

    public static void register() {
        OfHallsAndHeralds.logRegistryMsg("Dynamic Registries");
        DynamicRegistries.registerSynced(FACTION, Faction.CODEC);
        DynamicRegistries.registerSynced(BANNER, Banner.CODEC);
        DynamicRegistries.registerSynced(SPAWN, Spawn.CODEC);
    }

    public static void prepareBoostrap(RegistryBuilder registryBuilder) {
        registryBuilder.addRegistry(FACTION, FactionRegistryHH::bootstrap);
        registryBuilder.addRegistry(BANNER, BannerRegistryHH::bootstrap);
        registryBuilder.addRegistry(SPAWN, SpawnRegistryHH::bootstrap);
    }

    public static void addProviders(FabricDataGenerator.Pack pack) {
        pack.addProvider(FactionProvider::new);
        pack.addProvider(BannerProvider::new);
        pack.addProvider(SpawnProvider::new);
    }


    public static <T> RegistryKey<Registry<T>> of(String registry) {
        return RegistryKey.ofRegistry(OfHallsAndHeralds.id(registry));
    }
}
