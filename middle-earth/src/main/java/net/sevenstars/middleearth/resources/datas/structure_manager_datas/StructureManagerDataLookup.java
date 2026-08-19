package net.sevenstars.middleearth.resources.datas.structure_manager_datas;

import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.sevenstars.middleearth.registries.DynamicRegistriesME;

import java.util.Optional;

public class StructureManagerDataLookup {
    public static Optional<StructureManagerData> getStructureManagerData(Level world, ResourceLocation id) {
        return world.registryAccess()
                .lookupOrThrow(DynamicRegistriesME.STRUCTURE_MANAGER_DATA)
                .get(ResourceKey.create(DynamicRegistriesME.STRUCTURE_MANAGER_DATA, id))
                .map(holder -> holder.value());
    }
}
