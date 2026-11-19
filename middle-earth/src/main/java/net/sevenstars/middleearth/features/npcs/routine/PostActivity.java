package net.sevenstars.middleearth.features.npcs.routine;

import net.minecraft.item.Item;
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
    private static final MarkerKind[] POST_KINDS = {MarkerKind.ENTRANCE, MarkerKind.GUARD_POST};
    private static final int POST_TIME = 20 * 60;
    private static final int POST_TIME_VARIATION = 20 * 60;

    private @Nullable StructureFeatureData.Marker claimed = null;

    PostActivity(NpcRoutine routine) {
        super(routine);
    }

    @Override
    boolean canBeMounted() {
        return true;
    }

    @Override
    RoutineData.Activity fallback(ServerWorld world, RoutineData.Activity picked, BlockPos home) {
        String noPost = claimPost(world, home);
        if(noPost == null)
            return picked;
        routine.fallsBack(picked, noPost + ", patrols instead");
        return RoutineData.Activity.PATROL;
    }

    @Override
    @Nullable Item prop(RoutineData.Activity kind) {
        return torchProp();
    }

    @Override
    String label(RoutineData.Activity kind) {
        return "on guard";
    }

    // Null once claimed, else why there's no post
    @Nullable String claimPost(ServerWorld world, BlockPos home) {
        claimed = routine.site.claimPost(world, npc.getUuid(), home, POST_KINDS);
        if(claimed != null)
            return null;
        for(MarkerKind kind : POST_KINDS)
            if(!routine.site.getMarkers(kind).isEmpty())
                return "every post is taken";
        return "no post marked in this build";
    }

    @Override
    boolean begin(ServerWorld world, RoutineData.Activity picked, BlockPos home) {
        if(claimed == null)
            return false;
        routine.setSpot(claimed.pos());
        routine.setFacing(claimed.facing());
        return true;
    }

    @Override
    void arrive(ServerWorld world, Random random, long time) {
        routine.setPhaseEnd(time + POST_TIME + random.nextInt(POST_TIME_VARIATION));
        routine.setLookTarget(routine.facingTarget());
        if(routine.lookTarget() == null) {
            double awayX = npc.getX() - routine.site.getCenterX();
            double awayZ = npc.getZ() - routine.site.getCenterZ();
            routine.setLookTarget(new Vec3d(npc.getX() + awayX, npc.getEyeY(), npc.getZ() + awayZ));
        }
    }

    @Override
    void work(ServerWorld world, Random random, boolean done, long time) {
        if(!done) {
            routine.showProp();
            return;
        }
        if(routine.onDuty(world) && routine.isWatched(world))
            routine.setPhaseEnd(time + POST_TIME + random.nextInt(POST_TIME_VARIATION));
        else
            routine.finish(world, REST + random.nextInt(REST_VARIATION));
    }
}
