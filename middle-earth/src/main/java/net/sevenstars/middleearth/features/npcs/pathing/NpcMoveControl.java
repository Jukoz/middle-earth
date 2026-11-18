package net.sevenstars.middleearth.features.npcs.pathing;

import net.minecraft.entity.ai.control.MoveControl;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

// Vanilla jumps whenever the feet are in a shaped block: ladders can't be steered or descended,
// and open-shutter doorways get a hop every step. This climbs only to go up
public class NpcMoveControl extends MoveControl {
    private static final double LEVEL = 0.1;
    // Degrees
    private static final float TURN = 90;
    private static final float YAW_OFFSET = 90;

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
            float yaw = (float) (MathHelper.atan2(towardsZ, towardsX) * MathHelper.DEGREES_PER_RADIAN) - YAW_OFFSET;
            entity.setYaw(wrapDegrees(entity.getYaw(), yaw, TURN));
        }
        // A ladder lying on the floor counts as climbable, but the npc just walks over it
        if(entity.isClimbing() && up < -LEVEL && !entity.isOnGround()) {
            entity.setForwardSpeed(0);
            return;
        }
        entity.setMovementSpeed((float) (speed * entity.getAttributeValue(EntityAttributes.MOVEMENT_SPEED)));
        if(entity.isClimbing() && up > LEVEL)
            entity.getJumpControl().setActive();
    }

    private boolean isOnBoard() {
        if(entity.isClimbing())
            return true;
        BlockPos feet = entity.getBlockPos();
        return NpcPathNodeMaker.isOpenBoard(entity.getEntityWorld().getBlockState(feet));
    }
}
