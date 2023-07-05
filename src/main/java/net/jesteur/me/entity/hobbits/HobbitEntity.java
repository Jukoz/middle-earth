package net.jesteur.me.entity.hobbits;

import net.jesteur.me.entity.orcs.mordor.MordorOrcEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

public class HobbitEntity extends HostileEntity {

    public HobbitEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder setAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.3f)
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 20.0)
                .add(EntityAttributes.GENERIC_ATTACK_SPEED, 1.0)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 48.0)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 1.0);
    }

    @Override
    protected void initGoals() {
        int i = 0;
        this.goalSelector.add(++i, new SwimGoal(this));
        this.goalSelector.add(++i, new FleeEntityGoal<MordorOrcEntity>(this, MordorOrcEntity.class, 16.0f, 1.0, 1.2));
        this.goalSelector.add(++i, new WanderAroundFarGoal(this, 1.0));
        this.goalSelector.add(++i, new LookAtEntityGoal(this, PlayerEntity.class, 6.0f));
        this.goalSelector.add(++i, new LookAroundGoal(this));
    }

    public HobbitVariant getVariant() {
        return HobbitVariant.byId(this.getId());
    }
}
