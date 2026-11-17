package net.sevenstars.middleearth.entity.npcs.routine.activity;

import net.minecraft.block.BlockState;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.sevenstars.middleearth.block.special.structureManager.marker.MarkerKind;
import net.sevenstars.middleearth.resources.datas.structure_features.StructureFeatureData;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.sevenstars.middleearth.resources.datas.npc_classes.data.RoutineData;
import org.jetbrains.annotations.Nullable;
import net.sevenstars.middleearth.entity.npcs.routine.NpcRoutine;

import static net.sevenstars.middleearth.entity.npcs.routine.NpcRoutine.REST;
import static net.sevenstars.middleearth.entity.npcs.routine.NpcRoutine.REST_VARIATION;
import static net.sevenstars.middleearth.entity.npcs.routine.NpcRoutine.isLoaded;
import static net.sevenstars.middleearth.entity.npcs.routine.NpcRoutine.itemName;
import static net.sevenstars.middleearth.entity.npcs.routine.activity.StationActivity.WORK_RANGE;

public class MiningActivity extends RoutineActivity {
    private static final int MINING_TIME = 20 * 40;
    private static final int MINING_TIME_VARIATION = 20 * 20;

    private static final int MINE_SWING_ONE_IN = 3;

    private static final float MINE_SOUND_VOLUME = 0.8f;
    private static final float MINE_SOUND_PITCH = 0.8f;
    private static final float MINE_SOUND_PITCH_VARIATION = 0.2f;

    private @Nullable BlockPos rockFace = null;

    public MiningActivity(NpcRoutine engine) {
        super(engine);
    }

    @Override
    public boolean begin(ServerWorld world, RoutineData.Activity picked, BlockPos home) {
        // The marker is where the miner stands, and it faces the rock
        StructureFeatureData.Marker nearest = null;
        for(StructureFeatureData.Marker marker : engine.site.getMarkers(MarkerKind.MINE_FACE)) {
            if(marker.pos().isWithinDistance(home, WORK_RANGE) && (nearest == null || marker.pos().getSquaredDistance(npc.getBlockPos()) < nearest.pos().getSquaredDistance(npc.getBlockPos())))
                nearest = marker;
        }
        if(nearest == null)
            return engine.cannotStart(picked, engine.site.supports(picked) ? "no mine face within reach of home" : "no mine face marked in this build");
        rockFace = nearest.pos().offset(nearest.facing());
        if(!isLoaded(world, rockFace) || world.getBlockState(rockFace).getBlock().asItem() == Items.AIR)
            return engine.cannotStart(picked, "nothing to mine at the face " + rockFace.toShortString());
        engine.spot = nearest.pos();
        engine.lookTarget = Vec3d.ofCenter(rockFace);
        return true;
    }

    @Override
    public boolean beforeWork(ServerWorld world, Random random, long time) {
        BlockPos worked = rockFace;
        if(worked != null && !engine.canSee(world, worked)) {
            engine.abandoned("what it was to work at, " + worked.toShortString() + ", is behind a wall");
            engine.finish(world, REST + random.nextInt(REST_VARIATION));
            return true;
        }
        return false;
    }

    @Override
    public void arrive(ServerWorld world, Random random, long time) {
        engine.phaseEnd = time + MINING_TIME + random.nextInt(MINING_TIME_VARIATION);
    }

    @Override
    public void work(ServerWorld world, Random random, boolean done, long time) {
        if(done) {
            BlockState rock = rockFace == null ? null : world.getBlockState(rockFace);
            engine.carried = rock == null || rock.isAir() ? null : rock.getBlock().asItem();
            if(engine.carried == Items.AIR)
                engine.carried = null;
            if(engine.carried == null)
                engine.abandoned("the rock face was gone");
            else
                engine.did("carries " + itemName(engine.carried) + " from the face at " + rockFace.toShortString());
            engine.startReturn(world);
        } else if(rockFace != null && random.nextInt(MINE_SWING_ONE_IN) == 0) {
            npc.swingHand(Hand.MAIN_HAND);
            world.playSound(null, rockFace, world.getBlockState(rockFace).getSoundGroup().getHitSound(), SoundCategory.BLOCKS, MINE_SOUND_VOLUME, MINE_SOUND_PITCH + random.nextFloat() * MINE_SOUND_PITCH_VARIATION);
            cracks(world, rockFace);
        }
    }
}
