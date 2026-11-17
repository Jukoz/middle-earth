package net.sevenstars.middleearth.resources.datas.biome_events.data;

import net.minecraft.entity.EntityType;
import net.minecraft.registry.Registries;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.Heightmap;
import net.minecraft.world.World;
import net.sevenstars.middleearth.block.special.structureManager.features.StructureManagerService;
import net.sevenstars.middleearth.entity.EntitiesME;

public class SpawnEventDataUtil {
    // In blocks, the side length of the box searched around the spawn
    private static final int DEFAULT_SAME_ENTITY_DISTANCE = 256;
    private static final int DEFAULT_SAME_NPC_CLASS_DISTANCE = 128;
    private static final int DEFAULT_SAME_ENTITY_AMOUNT = 10;
    private static final int DEFAULT_SAME_NPC_CLASS_AMOUNT = 5;
    // In blocks
    private static final int DEFAULT_STRUCTURE_MANAGER_CLEARANCE = 64;
    // In blocks from the heightmap. A spawn within this range counts as on the surface
    private static final int SURFACE_DEPTH = 1;
    private static final int SURFACE_HEIGHT = 2;

    static boolean meetEntityThresholdRequirements(WildSpawnEventData data, World world, BlockPos pos) {
        // Client worlds never decide whether something spawns
        if(!(world instanceof ServerWorld serverWorld))
            return true;
        EntityType<?> targetEntityType = Registries.ENTITY_TYPE.get(data.getEntityType());
        int sameEntityDistance = data.getSameEntityLimitDistance().orElse(DEFAULT_SAME_ENTITY_DISTANCE);
        int sameEntityAmount = data.getSameEntityLimitAmount().orElse(DEFAULT_SAME_ENTITY_AMOUNT);
        boolean sameEntitySurfaceOnly = data.getSameEntitySurfaceOnly().orElse(false);
        Box searchBox = Box.of(pos.toCenterPos(), sameEntityDistance, sameEntityDistance, sameEntityDistance);
        if(SpawnCensus.count(serverWorld, searchBox, targetEntityType, null, sameEntitySurfaceOnly, sameEntityAmount) >= sameEntityAmount)
            return false;

        if(targetEntityType != EntitiesME.NPC || data.getNpcClass(null) == null)
            return true;
        int sameNpcClassAmount = data.getSameNpcClassLimitAmount().orElse(DEFAULT_SAME_NPC_CLASS_AMOUNT);
        // A limit of 0 still lets the first one spawn
        sameNpcClassAmount = Math.max(sameNpcClassAmount, 1);
        int sameNpcClassDistance = data.getSameNpcClassLimitDistance().orElse(DEFAULT_SAME_NPC_CLASS_DISTANCE);
        boolean sameNpcClassSurfaceOnly = data.getSameNpcClassSurfaceOnly().orElse(false);
        Box npcSearchBox = Box.of(pos.toCenterPos(), sameNpcClassDistance, sameNpcClassDistance, sameNpcClassDistance);
        return SpawnCensus.count(serverWorld, npcSearchBox, targetEntityType, data.getNpcClass(null), sameNpcClassSurfaceOnly, sameNpcClassAmount) < sameNpcClassAmount;
    }

    static boolean meetsStructureManagerClearance(WildSpawnEventData data, World world, BlockPos pos) {
        int structureManagerDistance = data.getStructureManagerRadiusAvoidance().orElse(DEFAULT_STRUCTURE_MANAGER_CLEARANCE);
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
        if(!requireSky && !requireUnderground)
            return true;
        int surfaceY = surfaceY(world, pos);
        if(requireSky && !isSurface(pos, surfaceY))
            return false;
        return !requireUnderground || isUnderground(pos, surfaceY);
    }

    private static int surfaceY(World world, BlockPos pos) {
        return world.getTopY(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, pos.getX(), pos.getZ());
    }

    private static boolean isSurface(BlockPos pos, int surfaceY) {
        return pos.getY() >= surfaceY - SURFACE_DEPTH && pos.getY() <= surfaceY + SURFACE_HEIGHT;
    }

    private static boolean isUnderground(BlockPos pos, int surfaceY) {
        return pos.getY() < surfaceY - SURFACE_HEIGHT;
    }

    public static boolean isSurface(World world, BlockPos pos) {
        return isSurface(pos, surfaceY(world, pos));
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
            if (world.getBlockState(pos).isSolidBlock(world, pos)) {
                return false;
            }
        }
        return true;
    }

    // Cheap tests come first because this is called for every entry of a biome on every spawn attempt
    public static boolean isConsideredForSpawning(WildSpawnEventData data, Identifier id, World world, BlockPos blockPos) {
        return data.getEntityType().equals(id)
                && meetWorldHeightRequirement(data, blockPos)
                && meetNightTimeRequirement(data, world)
                && meetLightLevelRequirement(data, world, blockPos)
                && meetEnvironmentRequirements(data, world, blockPos)
                && meetMinimumSpaceRequirement(data, world, blockPos)
                && meetEntityThresholdRequirements(data, world, blockPos)
                && meetsStructureManagerClearance(data, world, blockPos);
    }
}
