package net.sevenstars.middleearth.features.npcs.routine;

import net.minecraft.block.BlockState;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.sevenstars.middleearth.features.npcs.NpcEntity;
import net.sevenstars.middleearth.features.npcs.classes.data.RoutineData;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static net.sevenstars.middleearth.features.npcs.routine.NpcRoutine.REST;
import static net.sevenstars.middleearth.features.npcs.routine.NpcRoutine.REST_VARIATION;

abstract class RoutineActivity {
    private static final int CRACK_PARTICLES = 4;
    private static final double CRACK_SPREAD = 0.25;
    private static final double CRACK_SPEED = 0.05;

    final NpcRoutine routine;
    final NpcEntity npc;

    RoutineActivity(NpcRoutine routine) {
        this.routine = routine;
        this.npc = routine.npc;
    }

    // Whether the site has what the activity needs
    boolean isSupportedBy(RoutineSite site, RoutineData.Activity kind) {
        return true;
    }

    // Offered off duty too
    boolean isSocial(RoutineData.Activity kind) {
        return false;
    }

    // Checked whether on duty or not
    boolean canBeDone(ServerWorld world, RoutineData.Activity kind, BlockPos home) {
        return true;
    }

    // Checked on duty only
    boolean isPossible(ServerWorld world, RoutineData routineData, BlockPos home, long time) {
        return true;
    }

    boolean isOutdoors() {
        return false;
    }

    boolean canBeMounted() {
        return false;
    }

    // Walking off from the spot isn't leaving the work
    boolean isTiedToSpot() {
        return true;
    }

    boolean isInCompany() {
        return false;
    }

    // Sets out from a village to forage, hunt, fish or chop
    boolean isOuting() {
        return false;
    }

    boolean isFarming() {
        return false;
    }

    // True if picking it only looks for company and starts nothing
    boolean searchesOnly(ServerWorld world) {
        return false;
    }

    // What it turns into when it can't be started as picked
    RoutineData.Activity fallback(ServerWorld world, RoutineData.Activity picked, BlockPos home) {
        return picked;
    }

    @Nullable Item prop(RoutineData.Activity kind) {
        return null;
    }

    String label(RoutineData.Activity kind) {
        return kind.asString();
    }

    String workingNote() {
        return "";
    }

    // True if the failure is only logged and the round goes on
    boolean skipOnGiveUp(ServerWorld world, String why) {
        return false;
    }

    boolean canSkipStretch() {
        return false;
    }

    void skipStretch() {
    }

    void held(long time) {
    }

    // Torch in the dark, for those who work without a tool
    @Nullable Item torchProp() {
        return routine.isDark() ? Items.TORCH : null;
    }

    // False if it couldn't start and was put on hold
    boolean begin(ServerWorld world, RoutineData.Activity picked, BlockPos home) {
        return true;
    }

    // True if the arrival went on something else and the work doesn't start yet
    boolean beforeWork(ServerWorld world, Random random, long time) {
        return false;
    }

    void arrive(ServerWorld world, Random random, long time) {
    }

    boolean leaveIfHidden(ServerWorld world, Random random, @Nullable BlockPos worked) {
        if(worked == null || routine.canSee(world, worked))
            return false;
        routine.abandoned("what it was to work at, " + worked.toShortString() + ", is behind a wall");
        routine.finish(world, REST + random.nextInt(REST_VARIATION));
        return true;
    }

    void work(ServerWorld world, Random random, boolean done, long time) {
    }

    static int pickNear(List<BlockPos> spots, BlockPos home, int range, Random random) {
        int start = random.nextInt(Math.max(1, spots.size()));
        for(int i = 0; i < spots.size(); i++) {
            int index = (start + i) % spots.size();
            if(spots.get(index).isWithinDistance(home, range))
                return index;
        }
        return -1;
    }

    void cracks(ServerWorld world, BlockPos block) {
        BlockState state = world.getBlockState(block);
        if(!state.isAir())
            world.spawnParticles(new BlockStateParticleEffect(ParticleTypes.BLOCK, state), block.getX() + 0.5, block.getY() + 0.5, block.getZ() + 0.5, CRACK_PARTICLES, CRACK_SPREAD, CRACK_SPREAD, CRACK_SPREAD, CRACK_SPEED);
    }
}
