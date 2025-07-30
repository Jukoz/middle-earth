package net.sevenstars.middleearth.registries;

import net.minecraft.registry.Registry;

import java.util.ArrayList;
import java.util.List;

public class RegistryAliases {
    public record Alias(Registry registry, String name) {}

    public static List<Alias> aliases = new ArrayList<>(){
        {

        }
    };
}
