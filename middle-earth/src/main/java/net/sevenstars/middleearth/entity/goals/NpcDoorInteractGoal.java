package net.sevenstars.middleearth.entity.goals;

import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.OpenDoorGoal;
import net.minecraft.world.level.block.state.BlockState;
import net.sevenstars.middleearth.block.registration.ModBlocks;

public class NpcDoorInteractGoal extends OpenDoorGoal {
    public NpcDoorInteractGoal(Mob mob, boolean delayedClose) {
        super(mob, delayedClose);
    }

    @Override
    protected void setOpen(boolean open) {
        BlockState blockState = this.mob.level().getBlockState(this.doorPos);
        if(blockState.is(ModBlocks.TREATED_STEEL_DOOR))
            return;
        super.setOpen(open);
    }
}
