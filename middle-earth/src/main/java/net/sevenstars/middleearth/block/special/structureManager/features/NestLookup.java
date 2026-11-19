package net.sevenstars.middleearth.block.special.structureManager.features;

import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import net.sevenstars.middleearth.block.special.beds.BedTag;
import net.sevenstars.middleearth.registries.DynamicRegistriesME;
import net.sevenstars.middleearth.resources.datas.structure_manager_datas.SpawnNestNodeData;
import net.sevenstars.middleearth.resources.datas.structure_manager_datas.StructureManagerData;
import net.sevenstars.middleearth.resources.datas.structure_manager_datas.StructureManagerDataLookup;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public class NestLookup {
    // The world's places. Datagen and the report have no world and use the static pools instead
    public static Iterable<StructureManagerData> places(DynamicRegistryManager registries) {
        Optional<Registry<StructureManagerData>> places = registries.getOptional(DynamicRegistriesME.STRUCTURE_MANAGER_DATA);
        return places.isPresent() ? places.get() : List.of();
    }

    public static @Nullable StructureManagerData getStructureManagerData(World world, Identifier structureManagerDataId){
        if(world == null)
            return null;
        return StructureManagerDataLookup.getStructureManagerData(world, structureManagerDataId).orElse(null);
    }

    public static @Nullable SpawnNestNodeData findNest(World world, Identifier nestId) {
        for(StructureManagerData place : places(world.getRegistryManager())) {
            SpawnNestNodeData nest = place.findSpawnNest(nestId);
            if(nest != null)
                return nest;
        }
        return null;
    }

    // A bed's own place, else the manager's if it has none
    public static @Nullable SpawnNestNodeData nestFor(World world, BedTag tag, Optional<Identifier> managerPlace) {
        Identifier place = tag.place().or(() -> managerPlace).orElse(null);
        StructureManagerData data = place == null ? null : getStructureManagerData(world, place);
        return data == null ? null : data.findPeopleNest(tag.nestKind());
    }

    // A full nest id, or just the kind (older markers), looked up in the manager's place
    public static @Nullable SpawnNestNodeData nestForMarker(World world, String value, Optional<Identifier> managerPlace) {
        if(value.indexOf(Identifier.NAMESPACE_SEPARATOR) >= 0) {
            Identifier nestId = Identifier.tryParse(value);
            SpawnNestNodeData nest = nestId == null ? null : findNest(world, nestId);
            return nest != null && nest.isPeople() ? nest : null;
        }
        StructureManagerData data = managerPlace.map(place -> getStructureManagerData(world, place)).orElse(null);
        return data == null ? null : data.findPeopleNest(value);
    }
}
