package net.sevenstars.api.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.sevenstars.api.utils.IServerPlayerEntity;
import org.jetbrains.annotations.Nullable;

// TODO change private floats to attributes
public class AbstractMountEntity extends PathAwareEntity {
    protected SimpleInventory items;
    private final float cursorRotationSpeed = 0;
    private final float inputRotationSpeed = 0;
    private final float triggerAngle = 0;
    private final boolean canStrafe = true;


    protected AbstractMountEntity(EntityType<? extends PathAwareEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected ActionResult interactMob(PlayerEntity player, Hand hand) {
        ItemStack heldItem = player.getMainHandStack();

        if(!this.getWorld().isClient()) { // Server
            if(player.isSneaking()) { // Open Inventory
                this.openInventory(player);
                return ActionResult.SUCCESS;
            }
            else if(heldItem.isEmpty()) { // Ride entity by right-clicking empty hand
                putPlayerOnBack(player);
                return ActionResult.SUCCESS;
            }
        }

        return super.interactMob(player, hand);
    }

    protected void putPlayerOnBack(PlayerEntity player) { // Method to make player ride this entity
        player.startRiding(this);
    }

    //region Inventory
    public void openInventory(PlayerEntity playerEntity) {
        if(!this.getWorld().isClient) {
            IServerPlayerEntity player = (IServerPlayerEntity) playerEntity;

            player.openMountInventory(this, this.items);
        }
    }

    //endregion

    //region Tick-based methods
    @Override
    protected void tickControlled(PlayerEntity controllingPlayer, Vec3d movementInput) { // This method gets called every tick in which the entity is controlled by a player(!)
        super.tickControlled(controllingPlayer, movementInput);
        mountRotation(controllingPlayer, cursorRotationSpeed, inputRotationSpeed, triggerAngle);

        this.lastYaw = this.bodyYaw = this.headYaw = this.getYaw();
    }
    //endregion

    //region Turn radius implementation
    protected Vec2f getControlledRotation(LivingEntity controllingPassenger) { // Return a 2D-vector based on the controlling players pitch and yaw
        return new Vec2f(controllingPassenger.getPitch() * 0.5F, controllingPassenger.getYaw());
    }

    // Method to calculate and apply the rotation modified by the turn radius parameters
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

    // Modifies player movement input
    @Override
    protected Vec3d getControlledMovementInput(PlayerEntity controllingPlayer, Vec3d movementInput) {
        float sidewaysSpeed = this.canStrafe ? controllingPlayer.sidewaysSpeed * 0.5f : 0f;
        float forwardSpeed = controllingPlayer.forwardSpeed;
        if (forwardSpeed <= 0.0F) {
            forwardSpeed *= 0.25F;
        }
        return new Vec3d(sidewaysSpeed, 0.0, forwardSpeed);
    }
    //endregion

    @Override
    protected float getSaddledSpeed(PlayerEntity controllingPlayer) { // Get speed when mounted
        return (float)this.getAttributeValue(EntityAttributes.MOVEMENT_SPEED);
    }

    @Nullable
    @Override
    public LivingEntity getControllingPassenger() { // Get the passenger to control the entity
        return this.getFirstPassenger() instanceof PlayerEntity playerEntity
                ? playerEntity
                : super.getControllingPassenger();
    }

}
