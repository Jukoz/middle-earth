package net.sevenstars.middleearth.entity.beasts.cave_troll;

import net.minecraft.entity.AnimationState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.world.World;
import net.sevenstars.middleearth.entity.beasts.AbstractBeastEntity;
import net.sevenstars.middleearth.entity.beasts.trolls.TrollEntity;

public class CaveTrollEntity extends AbstractBeastEntity {
    public final AnimationState sleepingAnimationState = new AnimationState();

    public CaveTrollEntity(EntityType<? extends AbstractBeastEntity> entityType, World world) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder setAttributes() {
        return AnimalEntity.createAnimalAttributes()
                .add(EntityAttributes.MOVEMENT_SPEED, 0.35f)
                .add(EntityAttributes.MAX_HEALTH, 180.0)
                .add(EntityAttributes.KNOCKBACK_RESISTANCE, 0.8)
                .add(EntityAttributes.ATTACK_SPEED, 0.65)
                .add(EntityAttributes.FOLLOW_RANGE, 28.0)
                .add(EntityAttributes.ATTACK_DAMAGE, 14.0)
                .add(EntityAttributes.JUMP_STRENGTH, 0.0);
    }

    @Override
    protected void setupAnimationStates() {

    }

    @Override
    public void tickMovement() {
        super.tickMovement();

        World world = this.getWorld();
        if(world.isClient) {
            if(world.isNight()) {
                this.sleepingAnimationState.startIfNotRunning(this.age);
            }
            else {
                this.sleepingAnimationState.stop();
            }
        }
    }
}
