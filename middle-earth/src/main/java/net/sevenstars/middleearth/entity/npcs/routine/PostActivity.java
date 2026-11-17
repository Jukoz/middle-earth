package net.sevenstars.middleearth.entity.npcs.routine;

import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.sevenstars.middleearth.block.special.structureManager.marker.MarkerKind;
import net.sevenstars.middleearth.resources.datas.npc_classes.data.RoutineData;
import net.sevenstars.middleearth.resources.datas.structure_features.StructureFeatureData;

import static net.sevenstars.middleearth.entity.npcs.routine.NpcRoutine.REST;
import static net.sevenstars.middleearth.entity.npcs.routine.NpcRoutine.REST_VARIATION;

class PostActivity extends RoutineActivity {
    private static final int POST_TIME = 20 * 60;
    private static final int POST_TIME_VARIATION = 20 * 60;

    PostActivity(NpcRoutine routine) {
        super(routine);
    }

    @Override
    boolean begin(ServerWorld world, RoutineData.Activity picked, BlockPos home) {
        routine.spot = home;
        MarkerKind[] kinds = routine.isSentry() ? new MarkerKind[]{MarkerKind.LOOKOUT} : new MarkerKind[]{MarkerKind.ENTRANCE, MarkerKind.GUARD_POST};
        StructureFeatureData.Marker post = routine.site.claimPost(world, npc.getUuid(), home, kinds);
        if(post != null) {
            routine.spot = post.pos();
            routine.facing = post.facing();
        } else {
            boolean marked = false;
            for(MarkerKind kind : kinds)
                marked |= !routine.site.getMarkers(kind).isEmpty();
            routine.fallsBack(picked, marked ? "every post is taken, stands at its nest" : "no post marked in this build, stands at its nest");
        }
        return true;
    }

    @Override
    void arrive(ServerWorld world, Random random, long time) {
        routine.phaseEnd = time + POST_TIME + random.nextInt(POST_TIME_VARIATION);
        // A marked post says where to look. The rest watch the land outside
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
        // The guard isn't relieved while its shift lasts and there's somebody to see it standing there
        if(routine.onDuty(world) && routine.isWatched(world))
            routine.phaseEnd = time + POST_TIME + random.nextInt(POST_TIME_VARIATION);
        else
            routine.finish(world, REST + random.nextInt(REST_VARIATION));
    }
}
