package net.sevenstars.ofhallsandheralds.registries.custom;

import net.minecraft.registry.RegistryKey;
import net.sevenstars.api.registries.DynamicRegistriesAPI;
import net.sevenstars.ofhallsandheralds.OfHallsAndHeralds;
import net.sevenstars.ofhallsandheralds.dtos.FactionPalette;
import net.sevenstars.ofhallsandheralds.registries.DynamicRegistriesHH;

public class FactionPaletteRegistryHH extends DynamicRegistriesAPI<FactionPalette> {
    public static final RegistryKey<FactionPalette> NONE = of("none");


    private static RegistryKey<FactionPalette> of(String idPath) {
        return RegistryKey.of(DynamicRegistriesHH.FACTION_PALETTE, OfHallsAndHeralds.id(idPath));
    }
}
