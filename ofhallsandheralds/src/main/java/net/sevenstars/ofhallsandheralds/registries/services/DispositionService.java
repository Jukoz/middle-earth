package net.sevenstars.ofhallsandheralds.registries.services;

import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import net.sevenstars.ofhallsandheralds.dtos.disposition.Disposition;
import net.sevenstars.ofhallsandheralds.registries.DynamicRegistriesHH;

public class DispositionService {
    public static RegistryKey<Disposition> createKey(Identifier key){
        return RegistryKey.of(DynamicRegistriesHH.DISPOSITION, key);
    }

    public static Disposition fetchDisposition(World world, RegistryKey<Disposition> dispositionRegistryKey){
        return world.getRegistryManager().getOrThrow(DynamicRegistriesHH.DISPOSITION).get(dispositionRegistryKey);
    }
}
