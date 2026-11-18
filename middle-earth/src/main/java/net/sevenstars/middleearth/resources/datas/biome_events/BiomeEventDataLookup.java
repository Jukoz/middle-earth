package net.sevenstars.middleearth.resources.datas.biome_events;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.sevenstars.middleearth.config.ServerConfigME;
import net.sevenstars.middleearth.features.npcs.NpcEntity;
import net.sevenstars.middleearth.registries.DynamicRegistriesME;
import net.sevenstars.middleearth.registries.content.biomevents.BiomeEventRegistry;
import net.sevenstars.middleearth.world.dimension.DimensionRegistryME;

import org.jetbrains.annotations.Nullable;

import java.util.*;


public class BiomeEventDataLookup {
    public static HashMap<EntityType<?>, Set<UUID>> entities = new HashMap<>();

    public static BiomeEventData.ContextualizedBiomeData findNpcDataForBiome(World world, RegistryEntry<Biome> biome, NpcEntity entity) {
        Identifier biomeEventId = biome.getKey().map(RegistryKey::getValue).orElse(null);
        return findNpcData(world, DynamicRegistriesME.BIOME_EVENT, biomeEventId, entity);
    }

    public static BiomeEventData.ContextualizedBiomeData findNpcDataForStructure(World world, Identifier structure, NpcEntity entity) {
        return findNpcData(world, DynamicRegistriesME.STRUCTURE_EVENT, structure, entity);
    }

    private static BiomeEventData.ContextualizedBiomeData findNpcData(World world, RegistryKey<Registry<BiomeEventData>> registryKey, @Nullable Identifier eventId, NpcEntity entity) {
        Registry<BiomeEventData> registry = world.getRegistryManager().getOrThrow(registryKey);
        BiomeEventData eventData = eventId == null ? null : registry.get(eventId);
        BiomeEventData.ContextualizedBiomeData found = eventData == null ? null : eventData.findNpcData(world, entity);
        if(found != null || eventData != null && !eventData.getSpawnDefaultWhenUnmet())
            return found;
        BiomeEventData defaultData = registry.get(BiomeEventRegistry.DEFAULT.getValue());
        return defaultData == null ? null : defaultData.findNpcData(world, entity);
    }

    public static boolean canEntitySpawn(ServerWorld world, RegistryEntry<Biome> biome, BlockPos pos, EntityType<?> type, Random random) {
        Identifier biomeEventId = biome.getKey().map(RegistryKey::getValue).orElse(null);
        RegistryEntry.Reference<BiomeEventData> dataRef = biomeEventId == null ? null
                : world.getRegistryManager().getOrThrow(DynamicRegistriesME.BIOME_EVENT).getEntry(biomeEventId).orElse(null);
        if(dataRef == null)
            return true;
        BiomeEventData data = dataRef.value();
        boolean canSpawn = data.canSpawn(type, world, pos, random);

        if(!canSpawn)
            return false;
        if(type.getSpawnGroup() != SpawnGroup.MONSTER)
            return true;

        if(!entities.containsKey(type)){
            entities.put(type, new HashSet<>());
        }
        return canSpawn(type);
    }

    public static boolean canSpawn(EntityType<?> type) {
        return entities.getOrDefault(type, Collections.emptySet()).size() < ServerConfigME.GLOBAL_MOB_CAP;
    }

    public static void addEntity(LivingEntity entity){
        if (!entity.getEntityWorld().getRegistryKey().equals(DimensionRegistryME.ME_WORLD_KEY)) {
            return;
        }

        EntityType<?> type = entity.getType();
        UUID uuid = entity.getUuid();
        if(type.getSpawnGroup() != SpawnGroup.MONSTER)
            return;
        if(entity instanceof MobEntity mobEntity && mobEntity.isPersistent()){
            return;
        }
        entities.computeIfAbsent(type, t -> new HashSet<>()).add(uuid);
    }

    public static void removeEntity(EntityType<?> type, UUID uuid){
        if(type.getSpawnGroup() != SpawnGroup.MONSTER) return;
        Set<UUID> set = entities.get(type);
        if (set == null) return;
        set.remove(uuid);
        if (set.isEmpty()) entities.remove(type);
    }
}
