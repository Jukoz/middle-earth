package net.sevenstars.middleearth.resources.datas.biome_events.data;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.block.special.structureManager.features.StructureManagerService;
import net.sevenstars.middleearth.entity.EntitiesME;
import net.sevenstars.middleearth.entity.npcs.NpcEntity;

import java.util.List;
import java.util.Optional;

public class SpawnEventDataUtil {
    // # Comparators
    static boolean compareId(NpcEntity entity, Identifier npcTypeToCompare) {
        if(entity == null)
            return false;
        Identifier entityId = entity.getNpcTypeIdentifier();
        if(entityId == null || npcTypeToCompare == null)
            return false;
        return MiddleEarth.compareId(entityId, npcTypeToCompare);
    }

    static boolean compareEntitiesByType(LivingEntity entity, Identifier entityType) {
        if(entity == null)
            return false;
        Identifier entityId = entityType;
        if(entityId == null)
            return false;
        return MiddleEarth.compareId(entityId, entityType);
    }

    // Conditions
    static boolean meetEntityThresholdRequirements(WildSpawnEventData data, World world, BlockPos pos) {
        // Default is 16 npcs in 64 blocks around
        int sameEntityDistance = data.getSameEntityLimitDistance().orElse(64);
        int sameEntityAmount = data.getSameEntityLimitAmount().orElse(16);

        Box sameEntityTypeBox = Box.of(pos.toCenterPos(),
                sameEntityDistance,
                sameEntityDistance,
                sameEntityDistance);

        List<Entity> sameEntities = world.getOtherEntities(null, sameEntityTypeBox,
                entity -> Registries.ENTITY_TYPE.getId(entity.getType()).equals(data.getEntityType())
                        && SpawnEventDataUtil.compareEntitiesByType((LivingEntity) entity, data.getEntityType()));

        if(sameEntities.size() > sameEntityAmount)
            return false;
        if(data.getNpcType(null) == null || !data.getEntityType().equals(Registries.ENTITY_TYPE.getId(EntitiesME.NPC)))
            return true;

        // Default is 1 npc of same type in 64 blocks around
        int sameNpcTypeDistance = data.getSameNpcTypeLimitDistance().orElse(64);
        int sameNpcTypeAmount = data.getSameNpcTypeLimitAmount().orElse(1);

        Box sameNpcTypeBox = Box.of(pos.toCenterPos(),
                sameNpcTypeDistance,
                sameNpcTypeDistance,
                sameNpcTypeDistance);

        List<NpcEntity> sameNpcType = world.getEntitiesByType(EntitiesME.NPC, sameNpcTypeBox, x -> SpawnEventDataUtil.compareId(x, data.getNpcType(null)));
        return sameNpcType.size() < sameNpcTypeAmount;
    }

    static boolean meetStructureManagerClearanceRequirementUnmet(WildSpawnEventData data, World world, BlockPos pos) {
        int structureManagerDistance = data.getStructureManagerRadiusAvoidance().orElse(16);
        return !StructureManagerService.isClose(world, pos, structureManagerDistance);
    }

    static boolean meetLightLevelRequirement(WildSpawnEventData data, World world, BlockPos pos) {
        int currentLightLevel = world.getLightLevel(pos);
        int minimumLight = data.getLightLevelMinimum().orElse(0);
        if(currentLightLevel < minimumLight)
            return false;
        int maximumLight = data.getLightLevelMaximum().orElse(Integer.MAX_VALUE);
        return currentLightLevel < maximumLight;
    }

    static boolean meetWorldHeightRequirement(WildSpawnEventData data, BlockPos pos) {
        int currentY = pos.getY();
        if(currentY < data.getShouldSpawnAbove().orElse(Integer.MIN_VALUE))
            return false;
        return currentY < data.getShouldSpawnBelow().orElse(Integer.MAX_VALUE);
    }

    static boolean meetSkyAccessRequirement(WildSpawnEventData data, World world, BlockPos pos) {
        boolean requireSky = data.getSkyRequirement().orElse(false);
        return !requireSky || world.isSkyVisible(pos);
    }

    static boolean meetUndergroundRequirement(WildSpawnEventData data, World world, BlockPos pos) {
        boolean requireUnderground = data.getUndergroundRequirement().orElse(false);
        return !requireUnderground || !world.isSkyVisible(pos);
    }

    static boolean meetNightTimeRequirement(WildSpawnEventData data, World world) {
        boolean requireNight = data.getNightRequirement().orElse(false);
        return !requireNight || world.isNight();
    }

    public static boolean isConsideredForSpawning(WildSpawnEventData data, Identifier id, World world, BlockPos blockPos) {
        if(!data.getEntityType().equals(id))
            return false;
        if(id.toString().contains("spawn") || id.toString().contains("troll")){
            var test= 1;
        }
        if(!meetLightLevelRequirement(data, world, blockPos))
            return false;
        if(!meetWorldHeightRequirement(data, blockPos))
            return false;
        if(!meetSkyAccessRequirement(data, world, blockPos))
            return false;
        if(!meetUndergroundRequirement(data, world, blockPos))
            return false;
        if(!meetNightTimeRequirement(data, world))
            return false;
        if(!meetEntityThresholdRequirements(data, world, blockPos))
            return false;
        if(!meetStructureManagerClearanceRequirementUnmet(data, world, blockPos))
            return false;

        return true;
    }
}
