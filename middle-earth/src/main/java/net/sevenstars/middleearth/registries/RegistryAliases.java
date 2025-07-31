package net.sevenstars.middleearth.registries;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

import java.util.ArrayList;
import java.util.List;

public class RegistryAliases {
    public record Alias(Registry registry, String name) {}
    public record ManualAlias(Registry registry, String oldName, String newName) {}

    public static List<Alias> aliases = new ArrayList<>(){
        {
        }
    };

    public static List<ManualAlias> manualAliases = new ArrayList<>(){
        {
            add(new ManualAlias(Registries.ITEM, "strawberry", "strawberries"));

            add(new ManualAlias(Registries.BLOCK, "mossy_gneiss_tiles", "gneiss_tiles"));
            add(new ManualAlias(Registries.BLOCK, "mossy_gneiss_tiles_slab", "gneiss_tile_slab"));
            add(new ManualAlias(Registries.BLOCK, "mossy_gneiss_tiles_stairs", "gneiss_tile_stairs"));
            add(new ManualAlias(Registries.BLOCK, "mossy_gneiss_tiles_vertical_slab", "gneiss_tile_vertical_slab"));
            add(new ManualAlias(Registries.BLOCK, "mossy_gneiss_tiles_wall", "gneiss_tile_wall"));

            add(new ManualAlias(Registries.ITEM, "mossy_gneiss_tiles", "gneiss_tiles"));
            add(new ManualAlias(Registries.ITEM, "mossy_gneiss_tiles_slab", "gneiss_tile_slab"));
            add(new ManualAlias(Registries.ITEM, "mossy_gneiss_tiles_stairs", "gneiss_tile_stairs"));
            add(new ManualAlias(Registries.ITEM, "mossy_gneiss_tiles_vertical_slab", "gneiss_tile_vertical_slab"));
            add(new ManualAlias(Registries.ITEM, "mossy_gneiss_tiles_wall", "gneiss_tile_wall"));

            add(new ManualAlias(Registries.BLOCK, "ashen_stone", "ashenstone"));
            add(new ManualAlias(Registries.ITEM, "ashen_stone", "ashenstone"));
        }
    };
}
