package net.sevenstars.middleearth.entity.goals;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.DoorInteractGoal;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.phys.AABB;
import net.sevenstars.middleearth.block.registration.ModBlocks;
import net.sevenstars.middleearth.entity.npcs.NpcEntity;

public class NpcDoorInteractGoal extends DoorInteractGoal {
    private static final int MAX_OPEN_TICKS = 100;
    private static final int RETRY_COOLDOWN_TICKS = 100;

    private int openTicks;
    private long retryAfterTick;
    private boolean passedDoor;
    private AABB doorwayBounds;

    public NpcDoorInteractGoal(Mob mob) {
        super(mob);
    }

    @Override
    public boolean canUse() {
        return this.mob.level().getGameTime() >= this.retryAfterTick && super.canUse();
    }

    @Override
    public boolean canContinueToUse() {
        this.passedDoor |= !super.canContinueToUse();
        boolean insideDoorway = this.isInsideDoorway();
        return this.passedDoor ? insideDoorway : this.openTicks < MAX_OPEN_TICKS || insideDoorway;
    }

    @Override
    public void start() {
        super.start();
        this.openTicks = 0;
        this.passedDoor = false;
        this.doorwayBounds = this.createDoorwayBounds();
        if (this.mob instanceof NpcEntity npc) {
            npc.beginDoorTraversal();
        }
        this.setOpen(true);
    }

    @Override
    public void tick() {
        this.openTicks++;
        super.tick();
    }

    @Override
    public void stop() {
        if (!this.isInsideDoorway()) {
            this.setOpen(false);
        }
        if (this.mob instanceof NpcEntity npc) {
            npc.endDoorTraversal();
        }
        if (this.openTicks >= MAX_OPEN_TICKS) {
            this.retryAfterTick = this.mob.level().getGameTime() + RETRY_COOLDOWN_TICKS;
        }
        this.doorwayBounds = null;
    }

    private boolean isInsideDoorway() {
        return this.doorwayBounds != null
                && this.mob.getBoundingBox().intersects(this.doorwayBounds);
    }

    private AABB createDoorwayBounds() {
        BlockPos basePos = this.doorPos;
        BlockState state = this.mob.level().getBlockState(basePos);
        if (state.hasProperty(DoorBlock.HALF)
                && state.getValue(DoorBlock.HALF) == DoubleBlockHalf.UPPER) {
            basePos = basePos.below();
        }
        return new AABB(
                basePos.getX() - 0.05D,
                basePos.getY() - 0.05D,
                basePos.getZ() - 0.05D,
                basePos.getX() + 1.05D,
                basePos.getY() + 2.05D,
                basePos.getZ() + 1.05D
        );
    }

    @Override
    protected void setOpen(boolean open) {
        BlockState blockState = this.mob.level().getBlockState(this.doorPos);
        if(blockState.is(ModBlocks.TREATED_STEEL_DOOR))
            return;
        super.setOpen(open);
    }
}
