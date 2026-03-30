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
        Vec2f rotation = this.getControlledRotation(controllingPlayer);

        if(controllingPlayer.forwardSpeed > 0) {
            if((rotation.y + 5) < this.getYaw()) {
                this.setRotation(this.getYaw() - 5, rotation.x);
            }
            else if((rotation.y - 5) > this.getYaw()) {
                this.setRotation(this.getYaw() + 5, rotation.x);
            }
            this.lastYaw = this.bodyYaw = this.headYaw = this.getYaw();
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
