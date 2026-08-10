package net.sevenstars.middleearth.entity.goals;

import net.minecraft.block.BlockState;
import net.minecraft.entity.ai.goal.LongDoorInteractGoal;
import net.minecraft.entity.mob.MobEntity;
import net.sevenstars.middleearth.block.registration.ModBlocks;

public class NpcDoorInteractGoal extends LongDoorInteractGoal {
    public NpcDoorInteractGoal(MobEntity mob, boolean delayedClose) {
        super(mob, delayedClose);
    }

    @Override
    protected void setDoorOpen(boolean open) {
        BlockState blockState = this.mob.getWorld().getBlockState(this.doorPos);
        if(blockState.isOf(ModBlocks.TREATED_STEEL_DOOR))
            return;
        super.setDoorOpen(open);
    }
}
