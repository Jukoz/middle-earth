package net.sevenstars.middleearth.entity.npcs.routine.activity;

import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.sevenstars.middleearth.resources.datas.npc_classes.data.RoutineData;
import net.sevenstars.middleearth.entity.npcs.routine.NpcRoutine;

import static net.sevenstars.middleearth.entity.npcs.routine.NpcRoutine.SPOT_RANGE;
import static net.sevenstars.middleearth.entity.npcs.routine.NpcRoutine.itemName;

public class FishingActivity extends RoutineActivity {
    private static final int FISHING_TIME = 20 * 30;
    private static final int FISHING_TIME_VARIATION = 20 * 30;

    // Each is a one-in-N chance per look while working, except the fish escape, which is rolled once per catch
    private static final int FISH_ESCAPE_ONE_IN = 5;
    private static final int FISH_SPLASH_ONE_IN = 12;

    private static final float SPLASH_SOUND_VOLUME = 0.2f;

    private static final int SPLASH_PARTICLES = 6;
    private static final double SPLASH_HEIGHT = 0.5;
    private static final double SPLASH_SPREAD = 0.2;

    public FishingActivity(NpcRoutine engine) {
        super(engine);
    }

    @Override
    public boolean begin(ServerWorld world, RoutineData.Activity picked, BlockPos home) {
        Random random = npc.getRandom();
        int index = pickNear(engine.site.getShoreStands(), home, SPOT_RANGE + engine.site.getExtent(), random);
        if(index < 0)
            return engine.cannotStart(picked, "no shore within reach of home");
        engine.spot = engine.site.getShoreStands().get(index);
        engine.lookTarget = Vec3d.ofCenter(engine.site.getShoreWater(index));
        return true;
    }

    @Override
    public void arrive(ServerWorld world, Random random, long time) {
        engine.phaseEnd = time + FISHING_TIME + random.nextInt(FISHING_TIME_VARIATION);
    }

    @Override
    public void work(ServerWorld world, Random random, boolean done, long time) {
        if(done) {
            if(random.nextInt(FISH_ESCAPE_ONE_IN) != 0) {
                engine.carried = random.nextBoolean() ? Items.COD : Items.SALMON;
                engine.did("caught " + itemName(engine.carried));
            } else
                engine.abandoned("caught nothing");
            engine.startReturn(world);
        } else if(engine.lookTarget != null && random.nextInt(FISH_SPLASH_ONE_IN) == 0) {
            npc.swingHand(Hand.MAIN_HAND);
            world.spawnParticles(ParticleTypes.SPLASH, engine.lookTarget.x, engine.lookTarget.y + SPLASH_HEIGHT, engine.lookTarget.z, SPLASH_PARTICLES, SPLASH_SPREAD, 0, SPLASH_SPREAD, 0);
            world.playSound(null, engine.lookTarget.x, engine.lookTarget.y, engine.lookTarget.z, SoundEvents.ENTITY_FISHING_BOBBER_SPLASH, SoundCategory.NEUTRAL, SPLASH_SOUND_VOLUME, 1f);
        }
    }
}
