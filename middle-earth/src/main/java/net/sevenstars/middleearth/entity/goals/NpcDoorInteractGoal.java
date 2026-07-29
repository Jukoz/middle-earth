package net.sevenstars.middleearth.entity.goals;

import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.DoorInteractGoal;
import net.minecraft.world.level.block.state.BlockState;
import net.sevenstars.middleearth.block.registration.ModBlocks;

public class NpcDoorInteractGoal extends DoorInteractGoal {
    private static final int MAX_OPEN_TICKS = 100;
    private static final int RETRY_COOLDOWN_TICKS = 100;

    private int openTicks;
    private long retryAfterTick;

    public NpcDoorInteractGoal(Mob mob) {
        super(mob);
    }

    @Override
    public boolean canUse() {
        return this.mob.level().getGameTime() >= this.retryAfterTick && super.canUse();
    }

    @Override
    public boolean canContinueToUse() {
        return this.openTicks < MAX_OPEN_TICKS && super.canContinueToUse();
    }

    @Override
    public void start() {
        super.start();
        this.openTicks = 0;
        this.setOpen(true);
    }

    @Override
    public void tick() {
        this.openTicks++;
        super.tick();
    }

    @Override
    public void stop() {
        this.setOpen(false);
        if (this.openTicks >= MAX_OPEN_TICKS) {
            this.mob.getNavigation().stop();
            this.retryAfterTick = this.mob.level().getGameTime() + RETRY_COOLDOWN_TICKS;
        }
    }

    @Override
    protected void setOpen(boolean open) {
        BlockState blockState = this.mob.level().getBlockState(this.doorPos);
        if(blockState.is(ModBlocks.TREATED_STEEL_DOOR))
            return;
        super.setOpen(open);
    }
}
