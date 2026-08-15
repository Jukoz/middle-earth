package net.sevenstars.middleearth.resources.datas.biome_events;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.entity.npcs.NpcEntity;
import net.sevenstars.middleearth.registries.DynamicRegistriesME;
import net.sevenstars.middleearth.registries.content.biomevents.BiomeEventRegistry;


public class BiomeEventDataLookup {
    public static BiomeEventData.ContextualizedBiomeData findNpcDataForBiome(Level world, Holder<Biome> biome, NpcEntity entity) {
        ResourceLocation biomeEventId = ResourceLocation.parse(biome.getRegisteredName());
        Registry<BiomeEventData> registry = world.registryAccess().registryOrThrow(DynamicRegistriesME.BIOME_EVENT);
        BiomeEventData eventData = registry.get(biomeEventId);
        if(eventData != null){
            var foundNpcData = eventData.findNpcData(world, entity);
            if(foundNpcData == null && eventData.getSpawnDefaultWhenUnmet()){
                eventData = registry.get(BiomeEventRegistry.DEFAULT);
                foundNpcData = eventData == null ? null : eventData.findNpcData(world, entity);
            }
            return foundNpcData;
        }
        else {
            eventData = registry.get(BiomeEventRegistry.DEFAULT);
            if(eventData != null)
                return eventData.findNpcData(world, entity);
        }
        return null;
    }

    public static BiomeEventData.ContextualizedBiomeData findNpcDataForStructure(Level world, ResourceLocation structure, NpcEntity entity) {
        Registry<BiomeEventData> registry = world.registryAccess().registryOrThrow(DynamicRegistriesME.STRUCTURE_EVENT);
        BiomeEventData eventData = registry.get(structure);
        if(eventData != null){
            var foundNpcData = eventData.findNpcData(world, entity);
            if(foundNpcData == null && eventData.getSpawnDefaultWhenUnmet()){
                eventData = registry.get(BiomeEventRegistry.DEFAULT);
                foundNpcData = eventData == null ? null : eventData.findNpcData(world, entity);
            }
            return foundNpcData;
        }
        else {
            eventData = registry.get(BiomeEventRegistry.DEFAULT);
            if(eventData != null)
                return eventData.findNpcData(world, entity);
        }
        return null;
    }

    public static boolean canEntitySpawn(Level world, Holder<Biome> biome, BlockPos pos, EntityType<?> type, RandomSource random) {
        return !(world instanceof ServerLevel serverLevel)
                || canEntitySpawn(serverLevel, biome, pos, type, random);
    }

    public static boolean canEntitySpawn(ServerLevel world, Holder<Biome> biome, BlockPos pos, EntityType<?> type, RandomSource random) {
        BiomeEventData data = world.registryAccess().registryOrThrow(DynamicRegistriesME.BIOME_EVENT)
                .get(MiddleEarth.fetchId(biome.getRegisteredName()));
        if(data == null)
            return true;
        return data.canSpawn(type, world, pos, random);
    }
}
