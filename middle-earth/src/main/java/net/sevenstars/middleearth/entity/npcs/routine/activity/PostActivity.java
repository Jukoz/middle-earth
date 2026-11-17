package net.sevenstars.middleearth.entity.npcs.routine.activity;

import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.sevenstars.middleearth.block.special.structureManager.marker.MarkerKind;
import net.sevenstars.middleearth.resources.datas.structure_features.StructureFeatureData;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.sevenstars.middleearth.registries.content.npcjobs.NpcJobs;
import net.sevenstars.middleearth.resources.datas.npc_classes.data.RoutineData;
import net.sevenstars.middleearth.entity.npcs.routine.NpcRoutine;

import static net.sevenstars.middleearth.entity.npcs.routine.NpcRoutine.REST;
import static net.sevenstars.middleearth.entity.npcs.routine.NpcRoutine.REST_VARIATION;

public class PostActivity extends RoutineActivity {
    private static final int POST_TIME = 20 * 60;
    private static final int POST_TIME_VARIATION = 20 * 60;

    public PostActivity(NpcRoutine engine) {
        super(engine);
    }

    @Override
    public boolean begin(ServerWorld world, RoutineData.Activity picked, BlockPos home) {
        beginPost(world, picked, home, NpcJobs.SENTRY.getValue().equals(npc.getJobId()));
        return true;
    }

    private void beginPost(ServerWorld world, RoutineData.Activity picked, BlockPos home, boolean sentry) {
        engine.spot = home;
        MarkerKind[] kinds = sentry ? new MarkerKind[]{MarkerKind.LOOKOUT} : new MarkerKind[]{MarkerKind.ENTRANCE, MarkerKind.GUARD_POST};
        StructureFeatureData.Marker post = engine.site.claimPost(world, npc.getUuid(), home, kinds);
        if(post != null) {
            engine.spot = post.pos();
            engine.facing = post.facing();
        } else {
            boolean marked = false;
            for(MarkerKind kind : kinds)
                marked |= !engine.site.getMarkers(kind).isEmpty();
            engine.fallsBack(picked, marked ? "every post is taken, stands at its nest" : "no post marked in this build, stands at its nest");
        }
    }

    @Override
    public void arrive(ServerWorld world, Random random, long time) {
        engine.phaseEnd = time + POST_TIME + random.nextInt(POST_TIME_VARIATION);
        // A marked post says where to look. The rest watch the land outside
        engine.lookTarget = engine.facingTarget();
        if(engine.lookTarget == null) {
            double awayX = npc.getX() - engine.site.getCenterX();
            double awayZ = npc.getZ() - engine.site.getCenterZ();
            engine.lookTarget = new Vec3d(npc.getX() + awayX, npc.getEyeY(), npc.getZ() + awayZ);
        }
    }

    @Override
    public void work(ServerWorld world, Random random, boolean done, long time) {
        if(!done) {
            engine.showProp();
            return;
        }
        // The guard isn't relieved while its shift lasts and there's somebody to see it standing there
        if(engine.onDuty(world) && engine.isWatched(world))
            engine.phaseEnd = time + POST_TIME + random.nextInt(POST_TIME_VARIATION);
        else
            engine.finish(world, REST + random.nextInt(REST_VARIATION));
    }
}
