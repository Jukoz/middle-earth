package net.sevenstars.middleearth.resources.datas.biome_events.data;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.entity.EntityTypeTest;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.AABB;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.block.special.structureManager.features.StructureManagerService;
import net.sevenstars.middleearth.entity.EntitiesME;
import net.sevenstars.middleearth.entity.npcs.NpcEntity;

import java.util.ArrayList;
import java.util.function.Predicate;

public class SpawnEventDataUtil {
    private static final int MAX_QUERY_DISTANCE = 512;
    private static final int MAX_ENTITY_THRESHOLD = 256;
    private static final int MAX_SPACE_AXIS = 16;
    private static final ThreadLocal<ArrayList<Entity>> ENTITY_QUERY_BUFFER =
            ThreadLocal.withInitial(() -> new ArrayList<>(32));

    // # Comparators
    static boolean compareId(NpcEntity entity, ResourceLocation npcTypeToCompare) {
        if(entity == null)
            return false;
        ResourceLocation entityId = entity.getNpcTypeIdentifier();
        if(entityId == null || npcTypeToCompare == null)
            return false;
        return MiddleEarth.compareId(entityId, npcTypeToCompare);
    }

    static boolean compareEntitiesByType(LivingEntity entity, ResourceLocation entityType) {
        if(entity == null)
            return false;
        return MiddleEarth.compareId(BuiltInRegistries.ENTITY_TYPE.getKey(entity.getType()), entityType);
    }

    // Conditions
    static boolean meetEntityThresholdRequirements(WildSpawnEventData data, Level world, BlockPos pos) {
        EntityType<?> targetEntityType = BuiltInRegistries.ENTITY_TYPE.get(data.getEntityType());
        if (targetEntityType == null) {
            return false;
        }

        int sameEntityDistance = Math.clamp(
                data.getSameEntityLimitDistance().orElse(256), 0, MAX_QUERY_DISTANCE);
        int sameEntityAmount = Math.clamp(
                data.getSameEntityLimitAmount().orElse(10), 0, MAX_ENTITY_THRESHOLD);
        boolean sameEntitySurfaceOnly = data.getSameEntitySurfaceOnly().orElse(false);

        double sameEntityDiameter = sameEntityDistance * 2.0D;
        AABB searchBox = AABB.ofSize(
                pos.getCenter(), sameEntityDiameter, sameEntityDiameter, sameEntityDiameter);
        if (sameEntityAmount == 0 || hasAtLeast(world, searchBox, entity ->
                entity.getType() == targetEntityType
                        && (!sameEntitySurfaceOnly || isSurface(world, entity.blockPosition())), sameEntityAmount)) {
            return false;
        }

        boolean hasNpcTypeLimit = targetEntityType == EntitiesME.NPC && data.getNpcType(null) != null;
        if (!hasNpcTypeLimit) {
            return true;
        }

        int sameNpcTypeAmount = Math.clamp(
                data.getSameNpcTypeLimitAmount().orElse(5), 0, MAX_ENTITY_THRESHOLD);
        int sameNpcTypeDistance = Math.clamp(
                data.getSameNpcTypeLimitDistance().orElse(128), 0, MAX_QUERY_DISTANCE);
        boolean sameNpcTypeSurfaceOnly = data.getSameNpcTypeSurfaceOnly().orElse(false);
        if (sameNpcTypeAmount == 0) {
            return false;
        }
        double sameNpcTypeDiameter = sameNpcTypeDistance * 2.0D;
        AABB npcSearchBox = AABB.ofSize(
                pos.getCenter(), sameNpcTypeDiameter, sameNpcTypeDiameter, sameNpcTypeDiameter);
        ResourceLocation npcType = data.getNpcType(null);
        return !hasAtLeast(world, npcSearchBox, entity ->
                entity instanceof NpcEntity npc
                        && SpawnEventDataUtil.compareId(npc, npcType)
                        && (!sameNpcTypeSurfaceOnly || isSurface(world, entity.blockPosition())),
                sameNpcTypeAmount);
    }

    private static boolean hasAtLeast(
            Level world, AABB searchBox, Predicate<Entity> predicate, int threshold
    ) {
        ArrayList<Entity> matches = ENTITY_QUERY_BUFFER.get();
        matches.clear();
        try {
            world.getEntities(
                    EntityTypeTest.forClass(Entity.class), searchBox, predicate, matches, threshold);
            return matches.size() >= threshold;
        } finally {
            matches.clear();
        }
    }

    static boolean meetsStructureManagerClearance(WildSpawnEventData data, Level world, BlockPos pos) {
        int structureManagerDistance = data.getStructureManagerRadiusAvoidance().orElse(64);
        return !StructureManagerService.isClose(world, pos, structureManagerDistance);
    }

    static boolean meetLightLevelRequirement(WildSpawnEventData data, Level world, BlockPos pos) {
        int currentLightLevel = world.getMaxLocalRawBrightness(pos);
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

    static boolean meetEnvironmentRequirements(WildSpawnEventData data, Level world, BlockPos pos) {
        boolean requireSky = data.getSkyRequirement().orElse(false);
        boolean requireUnderground = data.getUndergroundRequirement().orElse(false);

        if (requireSky && !isSurface(world, pos)) {
            return false;
        }

        if (requireUnderground && !isUnderground(world, pos)) {
            return false;
        }

        return true;
    }

    public static boolean isSurface(Level world, BlockPos pos) {
        int surfaceY = world.getHeight(
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                pos.getX(),
                pos.getZ()
        );
        return pos.getY() >= surfaceY - 1 && pos.getY() <= surfaceY + 2;
    }
    public static boolean isUnderground(Level world, BlockPos pos) {
        int surfaceY = world.getHeight(
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                pos.getX(),
                pos.getZ()
        );
        return pos.getY() < surfaceY - 2;
    }

    static boolean meetNightTimeRequirement(WildSpawnEventData data, Level world) {
        boolean requireNight = data.getNightRequirement().orElse(false);
        return !requireNight || world.isNight();
    }

    private static boolean meetMinimumSpaceRequirement(WildSpawnEventData data, Level world, BlockPos blockPos) {
        Vec3i size = data.getMinimumSpaceCubeSize().orElse(null);
        if(size == null)
            return true;

        int sizeX = Math.clamp(size.getX(), 1, MAX_SPACE_AXIS);
        int sizeY = Math.clamp(size.getY(), 1, MAX_SPACE_AXIS);
        int sizeZ = Math.clamp(size.getZ(), 1, MAX_SPACE_AXIS);
        BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();
        for (int x = 0; x < sizeX; x++) {
            for (int y = 0; y < sizeY; y++) {
                for (int z = 0; z < sizeZ; z++) {
                    mutable.setWithOffset(blockPos, x, y, z);
                    if (!world.getBlockState(mutable).getCollisionShape(world, mutable).isEmpty()) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public static boolean isConsideredForSpawning(WildSpawnEventData data, ResourceLocation id, Level world, BlockPos blockPos) {
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
        if(!meetsStructureManagerClearance(data, world, blockPos))
            return false;
        return meetEntityThresholdRequirements(data, world, blockPos);
    }

}
