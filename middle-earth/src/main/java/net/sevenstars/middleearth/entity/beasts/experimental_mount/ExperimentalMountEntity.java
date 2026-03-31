package net.sevenstars.middleearth.entity.beasts.experimental_mount;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.RideableInventory;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class ExperimentalMountEntity extends MobEntity {
    protected SimpleInventory items;

    public ExperimentalMountEntity(EntityType<? extends MobEntity> entityType, World world) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder setAttributes() {
        return AnimalEntity.createAnimalAttributes()
                .add(EntityAttributes.MOVEMENT_SPEED, 0.1f)
                .add(EntityAttributes.MAX_HEALTH, 20.0f)
                .add(EntityAttributes.ATTACK_DAMAGE, 10.0f)
                .add(EntityAttributes.SAFE_FALL_DISTANCE, 10.0f)
                .add(EntityAttributes.STEP_HEIGHT, 1.0f);
    }

    @Override
    protected ActionResult interactMob(PlayerEntity player, Hand hand) {
        if(!this.getWorld().isClient()) {
            if(player.getMainHandStack().isEmpty()) {
                putPlayerOnBack(player);
                return ActionResult.SUCCESS;
            }
        }

        return super.interactMob(player, hand);
    }

    protected void putPlayerOnBack(PlayerEntity player) {
        player.startRiding(this);
    }

    protected Vec2f getControlledRotation(LivingEntity controllingPassenger) {
        return new Vec2f(controllingPassenger.getPitch() * 0.5F, controllingPassenger.getYaw());
    }

    @Override
    protected void tickControlled(PlayerEntity controllingPlayer, Vec3d movementInput) {
        super.tickControlled(controllingPlayer, movementInput);
        mountRotation(controllingPlayer, 10.0f, 10.0f, 0);

        this.lastYaw = this.bodyYaw = this.headYaw = this.getYaw();

    }

    protected void mountRotation(PlayerEntity controllingPlayer, float cursorRotationSpeed, float inputRotationSpeed, float triggerAngle) {
        Vec2f rotation = this.getControlledRotation(controllingPlayer);

        if(cursorRotationSpeed == 0 && inputRotationSpeed == 0) { // Acts like a horse if both speeds are 0
            this.setRotation(rotation.y, rotation.x);
        }
        else {
            float newRotation = rotation.y % 360;   // Reduce variable to range of -360 to +360
            newRotation = newRotation < 180 ? newRotation : newRotation - 360;  // Change values from 180 to 360 to their negative counterpart
            newRotation = newRotation > -180 ? newRotation : newRotation + 360; // Change values from -180 to -360 to their positive counterpart

            float yaw = this.getYaw();
            yaw = yaw < 180 ? yaw : yaw - 360;  // Change values from 180 to 360 to their negative counterpart
            yaw = yaw > -180 ? yaw : yaw + 360; // Change values from -180 to -360 to their positive counterpart

            // newRotation and yaw are in a range from -180 to 180

            float difference = yaw - newRotation;
            if(difference > 180) difference -= 360;
            else if(difference < -180) difference += 360;   // Check for wrap-around of angles (e.g. -175 and 170)

            if(Math.abs(difference) > triggerAngle) { // Check if difference is greater than the cursors max Angle
                this.setRotation(yaw - cursorRotationSpeed * Math.signum(difference) * (Math.abs(difference) / 180), rotation.x);
            }


            if(controllingPlayer.sidewaysSpeed < 0) {
                this.setRotation(this.getYaw() + inputRotationSpeed, rotation.x);
            }
            else if(controllingPlayer.sidewaysSpeed > 0) {
                this.setRotation(this.getYaw() - inputRotationSpeed, rotation.x);
            }
        }
    }

    @Override
    protected Vec3d getControlledMovementInput(PlayerEntity controllingPlayer, Vec3d movementInput) {
        float f = controllingPlayer.sidewaysSpeed * 0.0F;
        float g = controllingPlayer.forwardSpeed;
        if (g <= 0.0F) {
            g *= 0.25F;
        }
        return new Vec3d(f, 0.0, g);
    }

    @Override
    protected float getSaddledSpeed(PlayerEntity controllingPlayer) {
        return (float)this.getAttributeValue(EntityAttributes.MOVEMENT_SPEED);
    }

    @Nullable
    @Override
    public LivingEntity getControllingPassenger() {
        return this.getFirstPassenger() instanceof PlayerEntity playerEntity
                ? playerEntity
                : super.getControllingPassenger();
    }
}
