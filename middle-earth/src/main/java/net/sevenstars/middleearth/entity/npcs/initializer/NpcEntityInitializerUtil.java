package net.sevenstars.middleearth.entity.npcs.initializer;

import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import net.sevenstars.middleearth.registries.DynamicRegistriesME;
import net.sevenstars.middleearth.resources.datas.npcs.NpcData;

import java.util.Optional;

public class NpcEntityInitializerUtil {
    public static boolean characterIdentifierExist(World world, Identifier unitIdentifier){
        DynamicRegistryManager registryManager = world.getRegistryManager();
        Optional<RegistryEntry.Reference<NpcData>> optionalEntry = registryManager.getOptional(DynamicRegistriesME.NPC).get().getEntry(unitIdentifier);
        return !optionalEntry.isEmpty();
    }
}
