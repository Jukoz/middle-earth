package net.sevenstars.middleearth.features.npcs.ai;

import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.FuzzyTargeting;
import net.minecraft.entity.ai.NoPenaltyTargeting;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Heightmap;
import net.sevenstars.middleearth.features.npcs.NpcEntity;
import net.sevenstars.middleearth.utils.SpawnUtil;
import org.jetbrains.annotations.Nullable;

final class TravelTargets {
    // In blocks
    private static final int STROLL_RADIUS = 10;
    private static final int WATER_STROLL_RADIUS = 15;
    private static final int WANDER_HEIGHT = 7;
    private static final float FAR_PROBABILITY = 0.001F;

    private TravelTargets() {
    }

    static @Nullable Vec3d wander(NpcEntity npc) {
        if(npc.isTouchingWater()) {
            Vec3d target = FuzzyTargeting.find(npc, WATER_STROLL_RADIUS, WANDER_HEIGHT);
            return target == null ? NoPenaltyTargeting.find(npc, STROLL_RADIUS, WANDER_HEIGHT) : target;
        }
        if(npc.getRandom().nextFloat() >= FAR_PROBABILITY)
            return FuzzyTargeting.find(npc, STROLL_RADIUS, WANDER_HEIGHT);
        return NoPenaltyTargeting.find(npc, STROLL_RADIUS, WANDER_HEIGHT);
    }

    static @Nullable Vec3d groundAt(NpcEntity npc, double x, double z) {
        if(!(npc.getEntityWorld() instanceof ServerWorld world))
            return null;
        int blockX = MathHelper.floor(x);
        int blockZ = MathHelper.floor(z);
        if(!world.isChunkLoaded(blockX >> 4, blockZ >> 4))
            return null;
        BlockPos pos = new BlockPos(blockX, world.getTopY(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, blockX, blockZ), blockZ);
        if(!world.getFluidState(pos).isEmpty() || !world.getFluidState(pos.down()).isEmpty())
            return null;
        if(!SpawnUtil.canSpawn(pos, world, SpawnReason.EVENT))
            return null;
        return Vec3d.ofBottomCenter(pos);
    }

    static Vec3d direction(float heading) {
        float radians = heading * MathHelper.RADIANS_PER_DEGREE;
        return new Vec3d(-MathHelper.sin(radians), 0, MathHelper.cos(radians));
    }
}
