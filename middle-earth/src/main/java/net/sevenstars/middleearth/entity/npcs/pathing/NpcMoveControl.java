package net.sevenstars.middleearth.entity.npcs.pathing;

import net.minecraft.entity.ai.control.MoveControl;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

// Vanilla move control jumps whenever the mob's feet are inside a block with any shape, and the jump lasts until it lands
// with the heading held the whole time. On a ladder that makes a climb that can't be steered and can't go down, and in a
// doorway of open shutters it makes a hop at every step. On a board this control steers the npc toward where it's
// going, climbs only to go up and lets go to come down
public class NpcMoveControl extends MoveControl {
    // Height differences below this count as level
    private static final double LEVEL = 0.1;
    // Degrees, as in the game's own move control
    private static final float TURN = 90;
    // Between the angle atan2 gives and a yaw, as in the game's own move control
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
        // A ladder lying on the floor still counts as climbable, but the npc just walks over it
        if(entity.isClimbing() && up < -LEVEL && !entity.isOnGround()) {
            entity.setForwardSpeed(0);
            return;
        }
        entity.setMovementSpeed((float) (speed * entity.getAttributeValue(EntityAttributes.MOVEMENT_SPEED)));
        if(entity.isClimbing() && up > LEVEL)
            entity.getJumpControl().setActive();
    }

    // On a ladder, or with the feet in a block that holds an open trapdoor's board
    private boolean isOnBoard() {
        if(entity.isClimbing())
            return true;
        BlockPos feet = entity.getBlockPos();
        return NpcPathNodeMaker.isOpenBoard(entity.getEntityWorld().getBlockState(feet));
    }
}
