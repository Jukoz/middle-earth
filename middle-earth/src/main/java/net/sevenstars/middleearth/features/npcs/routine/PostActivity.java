package net.sevenstars.middleearth.features.npcs.routine;

import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.sevenstars.middleearth.block.special.structureManager.marker.MarkerKind;
import net.sevenstars.middleearth.features.npcs.classes.data.RoutineData;
import net.sevenstars.middleearth.resources.datas.structure_features.StructureFeatureData;
import org.jetbrains.annotations.Nullable;

import static net.sevenstars.middleearth.features.npcs.routine.NpcRoutine.REST;
import static net.sevenstars.middleearth.features.npcs.routine.NpcRoutine.REST_VARIATION;

class PostActivity extends RoutineActivity {
    private static final int POST_TIME = 20 * 60;
    private static final int POST_TIME_VARIATION = 20 * 60;

    private @Nullable StructureFeatureData.Marker claimed = null;

    PostActivity(NpcRoutine routine) {
        super(routine);
    }

    // Null once claimed, else why there's no post
    @Nullable String claimPost(ServerWorld world, BlockPos home) {
        boolean sentry = routine.isSentry();
        MarkerKind[] kinds = sentry ? new MarkerKind[]{MarkerKind.LOOKOUT} : new MarkerKind[]{MarkerKind.ENTRANCE, MarkerKind.GUARD_POST};
        claimed = routine.site.claimPost(world, npc.getUuid(), home, kinds);
        if(claimed != null)
            return null;
        String post = sentry ? "lookout" : "post";
        for(MarkerKind kind : kinds)
            if(!routine.site.getMarkers(kind).isEmpty())
                return "every " + post + " is taken";
        return "no " + post + " marked in this build";
    }

    @Override
    boolean begin(ServerWorld world, RoutineData.Activity picked, BlockPos home) {
        if(claimed == null)
            return false;
        routine.spot = claimed.pos();
        routine.facing = claimed.facing();
        return true;
    }

    @Override
    void arrive(ServerWorld world, Random random, long time) {
        routine.phaseEnd = time + POST_TIME + random.nextInt(POST_TIME_VARIATION);
        routine.lookTarget = routine.facingTarget();
        if(routine.lookTarget == null) {
            double awayX = npc.getX() - routine.site.getCenterX();
            double awayZ = npc.getZ() - routine.site.getCenterZ();
            routine.lookTarget = new Vec3d(npc.getX() + awayX, npc.getEyeY(), npc.getZ() + awayZ);
        }
    }

    @Override
    void work(ServerWorld world, Random random, boolean done, long time) {
        if(!done) {
            routine.showProp();
            return;
        }
        if(routine.onDuty(world) && routine.isWatched(world))
            routine.phaseEnd = time + POST_TIME + random.nextInt(POST_TIME_VARIATION);
        else
            routine.finish(world, REST + random.nextInt(REST_VARIATION));
    }
}
