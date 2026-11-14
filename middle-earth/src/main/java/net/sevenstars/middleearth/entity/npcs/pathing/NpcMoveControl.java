package net.sevenstars.middleearth.entity.npcs.pathing;

import net.minecraft.block.BlockState;
import net.minecraft.block.TrapdoorBlock;
import net.minecraft.entity.ai.control.MoveControl;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

// The game's way of moving a mob jumps whenever its feet are inside a block with any shape, and a jump lasts until it is
// on the ground again, with the heading held all the while. On a ladder that is a climb that cannot be steered and cannot
// go down, and in a doorway of open shutters a hop at every step. On a board the npc is steered to where it is going,
// climbs only to go up and lets go to come down
public class NpcMoveControl extends MoveControl {
    // Below this much difference in height the way is level
    private static final double LEVEL = 0.1;
    private static final float TURN = 90;

    public NpcMoveControl(MobEntity entity) {
        super(entity);
    }

    @Override
    public void tick() {
        if((state != State.MOVE_TO && state != State.JUMPING) || !isOnBoard()) {
            super.tick();
            return;
        }
        state = State.WAIT;
        double towardsX = targetX - entity.getX();
        double towardsZ = targetZ - entity.getZ();
        double up = targetY - entity.getY();
        if(towardsX * towardsX + towardsZ * towardsZ > REACHED_DESTINATION_DISTANCE_SQUARED) {
            float yaw = (float) (MathHelper.atan2(towardsZ, towardsX) * MathHelper.DEGREES_PER_RADIAN) - 90;
            entity.setYaw(wrapDegrees(entity.getYaw(), yaw, TURN));
        }
        // A ladder laid on the floor is climbable all the same, and is only walked over
        if(entity.isClimbing() && up < -LEVEL && !entity.isOnGround()) {
            entity.setForwardSpeed(0);
            return;
        }
        entity.setMovementSpeed((float) (speed * entity.getAttributeValue(EntityAttributes.MOVEMENT_SPEED)));
        if(entity.isClimbing() && up > LEVEL)
            entity.getJumpControl().setActive();
    }

    // On a ladder, or with the feet in a block holding an open trapdoor's board
    private boolean isOnBoard() {
        if(entity.isClimbing())
            return true;
        BlockPos feet = entity.getBlockPos();
        BlockState state = entity.getWorld().getBlockState(feet);
        return state.getBlock() instanceof TrapdoorBlock && state.get(TrapdoorBlock.OPEN);
    }
}
