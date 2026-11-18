package net.sevenstars.middleearth.features.npcs.routine;

import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.sevenstars.middleearth.features.npcs.classes.data.RoutineData;

import static net.sevenstars.middleearth.features.npcs.routine.NpcRoutine.SPOT_RANGE;
import static net.sevenstars.middleearth.features.npcs.routine.NpcRoutine.itemName;

class FishingActivity extends RoutineActivity {
    private static final int FISHING_TIME = 20 * 30;
    private static final int FISHING_TIME_VARIATION = 20 * 30;

    // One-in-N per look while working; fish escape is rolled once per catch
    private static final int FISH_ESCAPE_ONE_IN = 5;
    private static final int FISH_SPLASH_ONE_IN = 12;

    private static final float SPLASH_SOUND_VOLUME = 0.2f;

    private static final int SPLASH_PARTICLES = 6;
    private static final double SPLASH_HEIGHT = 0.5;
    private static final double SPLASH_SPREAD = 0.2;

    FishingActivity(NpcRoutine routine) {
        super(routine);
    }

    @Override
    boolean begin(ServerWorld world, RoutineData.Activity picked, BlockPos home) {
        Random random = npc.getRandom();
        int index = pickNear(routine.site.getShoreStands(), home, SPOT_RANGE + routine.site.getExtent(), random);
        if(index < 0)
            return routine.cannotStart(picked, "no shore within reach of home");
        routine.spot = routine.site.getShoreStands().get(index);
        routine.lookTarget = Vec3d.ofCenter(routine.site.getShoreWater(index));
        return true;
    }

    @Override
    void arrive(ServerWorld world, Random random, long time) {
        routine.phaseEnd = time + FISHING_TIME + random.nextInt(FISHING_TIME_VARIATION);
    }

    @Override
    void work(ServerWorld world, Random random, boolean done, long time) {
        if(done) {
            if(random.nextInt(FISH_ESCAPE_ONE_IN) != 0) {
                routine.carried = random.nextBoolean() ? Items.COD : Items.SALMON;
                routine.did("caught " + itemName(routine.carried));
            } else
                routine.abandoned("caught nothing");
            routine.startReturn(world);
        } else if(routine.lookTarget != null && random.nextInt(FISH_SPLASH_ONE_IN) == 0) {
            npc.swingHand(Hand.MAIN_HAND);
            world.spawnParticles(ParticleTypes.SPLASH, routine.lookTarget.x, routine.lookTarget.y + SPLASH_HEIGHT, routine.lookTarget.z, SPLASH_PARTICLES, SPLASH_SPREAD, 0, SPLASH_SPREAD, 0);
            world.playSound(null, routine.lookTarget.x, routine.lookTarget.y, routine.lookTarget.z, SoundEvents.ENTITY_FISHING_BOBBER_SPLASH, SoundCategory.NEUTRAL, SPLASH_SOUND_VOLUME, 1f);
        }
    }
}
