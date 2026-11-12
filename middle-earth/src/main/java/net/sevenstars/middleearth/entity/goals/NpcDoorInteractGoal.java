package net.sevenstars.middleearth.entity.goals;

import net.minecraft.block.BlockState;
import net.minecraft.entity.ai.goal.DoorInteractGoal;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.math.Box;
import net.sevenstars.middleearth.block.registration.BlockRegistryME;
import net.sevenstars.middleearth.entity.npcs.NpcEntity;

// Opens a door on the way and shuts it afterwards, but never on somebody else who is still going through
public class NpcDoorInteractGoal extends DoorInteractGoal {
    // In ticks
    private static final int PASSING_TIME = 20;
    private static final int MAXIMUM_HOLD_TIME = 20 * 10;
    private static final int DOORWAY_CHECK_INTERVAL = 5;
    private static final double DOORWAY_REACH = 1.25;
    private static final double OWN_REACH = 2;

    private final boolean delayedClose;
    private int ticksLeft;
    private int heldTicks;
    private boolean doorwayBusy;

    public NpcDoorInteractGoal(MobEntity mob, boolean delayedClose) {
        super(mob);
        this.delayedClose = delayedClose;
    }

    @Override
    public boolean shouldContinue() {
        if(!delayedClose || heldTicks >= MAXIMUM_HOLD_TIME)
            return false;
        if(ticksLeft > 0 && super.shouldContinue())
            return true;
        // Still squeezing through itself, or others are in the doorway
        boolean stillPassing = super.shouldContinue() && mob.squaredDistanceTo(doorPos.getX() + 0.5, mob.getY(), doorPos.getZ() + 0.5) < OWN_REACH * OWN_REACH;
        return stillPassing || doorwayBusy;
    }

    @Override
    public void start() {
        this.ticksLeft = PASSING_TIME;
        this.heldTicks = 0;
        this.doorwayBusy = false;
        this.setDoorOpen(true);
        if(mob instanceof NpcEntity npc)
            npc.setPassingDoor(true);
    }

    @Override
    public void stop() {
        this.setDoorOpen(false);
        if(mob instanceof NpcEntity npc)
            npc.setPassingDoor(false);
    }

    @Override
    public void tick() {
        ticksLeft--;
        heldTicks++;
        if(heldTicks % DOORWAY_CHECK_INTERVAL == 0) {
            Box doorway = new Box(doorPos).expand(DOORWAY_REACH, 0, DOORWAY_REACH);
            doorwayBusy = !mob.getWorld().getEntitiesByClass(NpcEntity.class, doorway, other -> other != mob && other.isAlive()).isEmpty();
            // Someone may have shut it in the meantime
            if(!isDoorOpen())
                setDoorOpen(true);
        }
        super.tick();
    }

    @Override
    protected void setDoorOpen(boolean open) {
        BlockState blockState = this.mob.getEntityWorld().getBlockState(this.doorPos);
        if(blockState.isOf(BlockRegistryME.TREATED_STEEL_DOOR))
            return;
        super.setDoorOpen(open);
    }
}
