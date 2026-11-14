package net.sevenstars.middleearth.resources.datas.biome_events.data;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.registry.Registries;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.Heightmap;
import net.minecraft.world.World;
import net.sevenstars.api.utils.IdentifierUtil;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.block.special.structureManager.features.StructureManagerService;
import net.sevenstars.middleearth.entity.EntitiesME;
import net.sevenstars.middleearth.entity.npcs.NpcEntity;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class SpawnEventDataUtil {
    // # Comparators
    static boolean compareId(NpcEntity entity, Identifier npcClassToCompare) {
        if(entity == null)
            return false;
        Identifier entityId = entity.getNpcClassIdentifier();
        if(entityId == null || npcClassToCompare == null)
            return false;
        return IdentifierUtil.compareId(entityId, npcClassToCompare);
    }

    static boolean compareEntitiesByType(LivingEntity entity, Identifier entityType) {
        if(entity == null)
            return false;
        return IdentifierUtil.compareId(Registries.ENTITY_TYPE.getId(entity.getType()), entityType);
    }

    // Conditions
    static boolean meetEntityThresholdRequirements(WildSpawnEventData data, World world, BlockPos pos) {
        EntityType<?> targetEntityType = Registries.ENTITY_TYPE.get(data.getEntityType());
        int sameEntityDistance = data.getSameEntityLimitDistance().orElse(256);
        int sameEntityAmount = data.getSameEntityLimitAmount().orElse(10);
        boolean sameEntitySurfaceOnly = data.getSameEntitySurfaceOnly().orElse(false);

        Box searchBox = Box.of(pos.toCenterPos(), sameEntityDistance, sameEntityDistance, sameEntityDistance);

        boolean hasNpcClassLimit = targetEntityType == EntitiesME.NPC && data.getNpcClass(null) != null;
        int sameNpcClassAmount = data.getSameNpcClassLimitAmount().orElse(5);
        int sameNpcClassDistance = data.getSameNpcClassLimitDistance().orElse(128);
        boolean sameNpcClassSurfaceOnly = data.getSameNpcClassSurfaceOnly().orElse(false);
        Box npcSearchBox = hasNpcClassLimit ? Box.of(pos.toCenterPos(), sameNpcClassDistance, sameNpcClassDistance, sameNpcClassDistance) : null;

        // Client worlds never decide a spawn
        if(!(world instanceof ServerWorld serverWorld))
            return true;
        if(SpawnCensus.count(serverWorld, searchBox, targetEntityType, null, sameEntitySurfaceOnly, sameEntityAmount) >= sameEntityAmount)
            return false;
        if(!hasNpcClassLimit)
            return true;
        if(sameNpcClassAmount <= 0)
            return false;
        return SpawnCensus.count(serverWorld, npcSearchBox, targetEntityType, data.getNpcClass(null), sameNpcClassSurfaceOnly, sameNpcClassAmount) < sameNpcClassAmount;
    }

    static boolean meetsStructureManagerClearance(WildSpawnEventData data, World world, BlockPos pos) {
        int structureManagerDistance = data.getStructureManagerRadiusAvoidance().orElse(64);
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

    static boolean meetEnvironmentRequirements(WildSpawnEventData data, World world, BlockPos pos) {
        boolean requireSky = data.getSkyRequirement().orElse(false);
        boolean requireUnderground = data.getUndergroundRequirement().orElse(false);

        boolean isSurface = isSurface(world, pos);
        boolean isUnderground = isUnderground(world, pos);

        if (requireSky && !isSurface) {
            return false;
        }

        if (requireUnderground && !isUnderground) {
            return false;
        }

        return true;
    }

    public static boolean isSurface(World world, BlockPos pos) {
        int surfaceY = world.getTopY(
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
                pos.getX(),
                pos.getZ()
        );
        return pos.getY() >= surfaceY - 1 && pos.getY() <= surfaceY + 2;
    }
    public static boolean isUnderground(World world, BlockPos pos) {
        int surfaceY = world.getTopY(
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
                pos.getX(),
                pos.getZ()
        );
        return pos.getY() < surfaceY - 2;
    }

    static boolean meetNightTimeRequirement(WildSpawnEventData data, World world) {
        boolean requireNight = data.getNightRequirement().orElse(false);
        return !requireNight || world.isNight();
    }

    private static boolean meetMinimumSpaceRequirement(WildSpawnEventData data, World world, BlockPos blockPos) {
        Vec3i size = data.getMinimumSpaceCubeSize().orElse(null);
        if(size == null)
            return true;
        BlockPos max = blockPos.add(size.getX() - 1, size.getY() - 1, size.getZ() - 1);
        for (BlockPos pos : BlockPos.iterate(blockPos, max)) {
            if (!world.getBlockState(pos).isSolidBlock(world, pos)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isConsideredForSpawning(WildSpawnEventData data, Identifier id, World world, BlockPos blockPos) {
        if(!data.getEntityType().equals(id))
            return false;
        if(!meetLightLevelRequirement(data, world, blockPos))
            return false;
        if(!meetWorldHeightRequirement(data, blockPos))
            return false;
        if(!meetMinimumSpaceRequirement(data, world, blockPos))
            return false;
        if(!meetEnvironmentRequirements(data, world, blockPos))
            return false;
        if(!meetNightTimeRequirement(data, world))
            return false;
        if(!meetEntityThresholdRequirements(data, world, blockPos))
            return false;
        if(!meetsStructureManagerClearance(data, world, blockPos))
            return false;
        return true;
    }

}
