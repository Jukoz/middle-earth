package net.sevenstars.middleearth.features.npcs.routine;

import net.minecraft.item.Item;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.block.BlockState;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.sevenstars.middleearth.block.special.structureManager.marker.MarkerKind;
import net.sevenstars.middleearth.features.npcs.classes.data.RoutineData;
import net.sevenstars.middleearth.resources.datas.structure_features.StructureFeatureData;
import org.jetbrains.annotations.Nullable;

import static net.sevenstars.middleearth.features.npcs.routine.NpcRoutine.isLoaded;
import static net.sevenstars.middleearth.features.npcs.routine.NpcRoutine.itemName;
import static net.sevenstars.middleearth.features.npcs.routine.StationActivity.WORK_RANGE;

class MiningActivity extends RoutineActivity {
    private static final int MINING_TIME = 20 * 40;
    private static final int MINING_TIME_VARIATION = 20 * 20;

    private static final int MINE_SWING_ONE_IN = 3;

    private static final float MINE_SOUND_VOLUME = 0.8f;
    private static final float MINE_SOUND_PITCH = 0.8f;
    private static final float MINE_SOUND_PITCH_VARIATION = 0.2f;

    private @Nullable BlockPos rockFace = null;

    MiningActivity(NpcRoutine routine) {
        super(routine);
    }

    @Override
    boolean isSupportedBy(RoutineSite site, RoutineData.Activity kind) {
        return !site.getMarkers(MarkerKind.MINE_FACE).isEmpty();
    }

    @Override
    @Nullable Item prop(RoutineData.Activity kind) {
        return npc.getMainHandStack().isIn(ItemTags.PICKAXES) ? null : Items.IRON_PICKAXE;
    }

    @Override
    String label(RoutineData.Activity kind) {
        return "mining";
    }

    @Override
    boolean begin(ServerWorld world, RoutineData.Activity picked, BlockPos home) {
        // The marker is where the miner stands, facing the rock
        StructureFeatureData.Marker nearest = null;
        for(StructureFeatureData.Marker marker : routine.site.getMarkers(MarkerKind.MINE_FACE)) {
            if(marker.pos().isWithinDistance(home, WORK_RANGE) && (nearest == null || marker.pos().getSquaredDistance(npc.getBlockPos()) < nearest.pos().getSquaredDistance(npc.getBlockPos())))
                nearest = marker;
        }
        if(nearest == null)
            return routine.cannotStart(picked, isSupportedBy(routine.site, picked) ? "no mine face within reach of home" : "no mine face marked in this build");
        rockFace = nearest.pos().offset(nearest.facing());
        if(!isLoaded(world, rockFace) || world.getBlockState(rockFace).getBlock().asItem() == Items.AIR)
            return routine.cannotStart(picked, "nothing to mine at the face " + rockFace.toShortString());
        routine.setSpot(nearest.pos());
        routine.setLookTarget(Vec3d.ofCenter(rockFace));
        return true;
    }

    @Override
    boolean beforeWork(ServerWorld world, Random random, long time) {
        return leaveIfHidden(world, random, rockFace);
    }

    @Override
    void arrive(ServerWorld world, Random random, long time) {
        routine.setPhaseEnd(time + MINING_TIME + random.nextInt(MINING_TIME_VARIATION));
    }

    @Override
    void work(ServerWorld world, Random random, boolean done, long time) {
        if(done) {
            BlockState rock = rockFace == null ? null : world.getBlockState(rockFace);
            routine.setCarried(rock == null || rock.isAir() ? null : rock.getBlock().asItem());
            if(routine.carried() == Items.AIR)
                routine.setCarried(null);
            if(routine.carried() == null)
                routine.abandoned("the rock face was gone");
            else
                routine.did("carries " + itemName(routine.carried()) + " from the face at " + rockFace.toShortString());
            routine.startReturn(world);
        } else if(rockFace != null && random.nextInt(MINE_SWING_ONE_IN) == 0) {
            npc.swingHand(Hand.MAIN_HAND);
            world.playSound(null, rockFace, world.getBlockState(rockFace).getSoundGroup().getHitSound(), SoundCategory.BLOCKS, MINE_SOUND_VOLUME, MINE_SOUND_PITCH + random.nextFloat() * MINE_SOUND_PITCH_VARIATION);
            cracks(world, rockFace);
        }
    }
}
