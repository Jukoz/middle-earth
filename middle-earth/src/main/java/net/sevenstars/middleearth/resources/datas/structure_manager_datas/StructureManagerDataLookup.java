package net.sevenstars.middleearth.resources.datas.structure_manager_datas;

import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import net.sevenstars.middleearth.registries.DynamicRegistriesME;
import net.sevenstars.middleearth.resources.StructureManagerDatasME;

import java.util.Optional;

public class StructureManagerDataLookup {
    public static Optional<StructureManagerData> getStructureManagerData(World world, Identifier id) {
        return world.getRegistryManager().getOrThrow(DynamicRegistriesME.STRUCTURE_MANAGER_DATA).getOptionalValue(id);
    }
}
