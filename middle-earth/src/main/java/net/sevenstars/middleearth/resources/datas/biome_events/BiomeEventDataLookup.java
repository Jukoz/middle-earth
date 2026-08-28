package net.sevenstars.middleearth.resources.datas.biome_events;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.config.ServerConfigME;
import net.sevenstars.middleearth.entity.npcs.NpcEntity;
import net.sevenstars.middleearth.registries.DynamicRegistriesME;
import net.sevenstars.middleearth.registries.content.biomevents.BiomeEventRegistry;
import net.sevenstars.middleearth.world.dimension.DimensionRegistryME;

import java.util.*;


public class BiomeEventDataLookup {
    public static HashMap<EntityType<?>, Set<UUID>> entities = new HashMap<>();

    public static BiomeEventData.ContextualizedBiomeData findNpcDataForBiome(World world, RegistryEntry<Biome> biome, NpcEntity entity) {
        Identifier biomeEventId = Identifier.of(biome.getIdAsString());
        BiomeEventData eventData = world.getRegistryManager().getOrThrow(DynamicRegistriesME.BIOME_EVENT).get(biomeEventId);
        if(eventData != null){
            var foundNpcData = eventData.findNpcData(world, entity);
            if(foundNpcData == null && eventData.getSpawnDefaultWhenUnmet()){
                eventData = world.getRegistryManager().getOrThrow(DynamicRegistriesME.BIOME_EVENT).get(BiomeEventRegistry.DEFAULT);
                foundNpcData = eventData.findNpcData(world, entity);
            }
            return foundNpcData;
        }
        else {
            eventData = world.getRegistryManager().getOrThrow(DynamicRegistriesME.BIOME_EVENT).get(BiomeEventRegistry.DEFAULT);
            if(eventData != null)
                return eventData.findNpcData(world, entity);
        }
        return null;
    }

    public static BiomeEventData.ContextualizedBiomeData findNpcDataForStructure(World world, Identifier structure, NpcEntity entity) {
        BiomeEventData eventData = world.getRegistryManager().getOrThrow(DynamicRegistriesME.STRUCTURE_EVENT).get(structure);
        if(eventData != null){
            var foundNpcData = eventData.findNpcData(world, entity);
            if(foundNpcData == null && eventData.getSpawnDefaultWhenUnmet()){
                eventData = world.getRegistryManager().getOrThrow(DynamicRegistriesME.STRUCTURE_EVENT).get(BiomeEventRegistry.DEFAULT);
                foundNpcData = eventData.findNpcData(world, entity);
            }
            return foundNpcData;
        }
        else {
            eventData = world.getRegistryManager().getOrThrow(DynamicRegistriesME.STRUCTURE_EVENT).get(BiomeEventRegistry.DEFAULT);
            if(eventData != null)
                return eventData.findNpcData(world, entity);
        }
        return null;
    }

    public static boolean canEntitySpawn(ServerWorld world, RegistryEntry<Biome> biome, BlockPos pos, EntityType<?> type, Random random) {
        RegistryEntry.Reference<BiomeEventData> dataRef = world.getRegistryManager().getOrThrow(DynamicRegistriesME.BIOME_EVENT).getEntry(MiddleEarth.ofId(biome.getIdAsString())).orElse(null);
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
        if (!entity.getWorld().getRegistryKey().equals(DimensionRegistryME.ME_WORLD_KEY)) {
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
