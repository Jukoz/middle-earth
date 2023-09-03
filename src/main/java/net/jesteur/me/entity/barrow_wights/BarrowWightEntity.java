package net.jesteur.me.entity.barrow_wights;

import net.jesteur.me.MiddleEarth;
import net.jesteur.me.utils.HallucinationData;
import net.jesteur.me.utils.IEntityDataSaver;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;

public class BarrowWightEntity extends HostileEntity {
    public static final int ATTACK_COOLDOWN = 10;
    public static final float RESISTANCE = 0.15f;
    private int attackTicksLeft = 0;
    private int screamTicksLeft = 0;

    AnimationState screamAnimationState = new AnimationState();

    public BarrowWightEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder setAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.3f)
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 12.0)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 0.25)
                .add(EntityAttributes.GENERIC_ATTACK_SPEED, 0.9)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 2.0)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 20.0);
    }
    @Override
    protected float getJumpVelocity() {
        return 0.5f * this.getJumpVelocityMultiplier() + this.getJumpBoostVelocityModifier();
    }

    @Override
    protected void initGoals() {
        int i = 0;
        this.goalSelector.add(++i, new SwimGoal(this));
        this.goalSelector.add(++i, new MeleeAttackGoal(this, 0.9f, false));
        this.goalSelector.add(++i, new WanderAroundFarGoal(this, 1.0));
        this.goalSelector.add(++i, new LookAtEntityGoal(this, PlayerEntity.class, 6.0f));
        this.goalSelector.add(++i, new LookAroundGoal(this));
        i = 0;
        this.targetSelector.add(++i, new ActiveTargetGoal<>(this, PlayerEntity.class, true));
    }

    public enum State {
        NEUTRAL,
        WALKING,
        AGGRESSIVE,
        ATTACK,
        SCREAM;
    }


    public BarrowWightEntity.State getState() {
        if(this.attackTicksLeft > 0) {
            return BarrowWightEntity.State.ATTACK;
        }
        else if (this.isAttacking()) {

            if( this.screamTicksLeft == 0 )
            {
                screamTicksLeft = 200;
                return State.SCREAM;        }

            else
                return BarrowWightEntity.State.AGGRESSIVE;
        }
        else if(this.isInWalkTargetRange()) {
            return BarrowWightEntity.State.WALKING;
        }
        return BarrowWightEntity.State.NEUTRAL;

    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        if(!source.equals(getDamageSources().drown()) && !source.equals(getDamageSources().lava())
                && !source.equals(getDamageSources().cramming()) && !source.equals(getDamageSources().magic())) {
            amount *= (1 - RESISTANCE);
        }

        return super.damage(source, amount);

    }

    public int getAttackTicksLeft() {
        return this.attackTicksLeft;
    }


    @Override
    public void tick(){
        if(getState() == State.SCREAM){
            this.screamAnimationState.start(this.age);
        }
        if(this.screamTicksLeft > 0 )
            this.screamTicksLeft--;
        super.tick();
    }

    @Override
    public void tickMovement() {


        super.tickMovement();
        if (this.attackTicksLeft > 0) {
            --this.attackTicksLeft;
        }
    }

    @Override
    public boolean tryAttack(Entity target) {
        this.attackTicksLeft = ATTACK_COOLDOWN;
        this.getWorld().sendEntityStatus(this, EntityStatuses.PLAY_ATTACK_SOUND);
        float f = this.getAttackDamage();
        float g = (int)f > 0 ? f / 2.0f + (float)this.random.nextInt((int)f) : f;
        boolean bl = target.damage(this.getDamageSources().mobAttack(this), g);
        if (bl) {
            double d;
            if (target instanceof LivingEntity) {
                LivingEntity livingEntity = (LivingEntity)target;
                if(target instanceof PlayerEntity) {
                    //PlayerEntity playerEntity = (PlayerEntity) livingEntity;
                    HallucinationData.addHallucination((IEntityDataSaver) livingEntity, 1);

                    System.out.println("HALLUCINATION: " + HallucinationData.readHallucination((IEntityDataSaver) livingEntity));
                }
                d = livingEntity.getAttributeValue(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE);
            } else {
                d = 0.0;
            }
            double e = Math.max(0.0, 1.0 - d);
            target.setVelocity(target.getVelocity().multiply(1f + (0.8f * e))); //.add(0.0, (double)0.1f * e, 0.0));
            this.applyDamageEffects(this, target);
        }
        this.playSound(SoundEvents.ENTITY_SLIME_ATTACK, 1.5f, 0.8f);
        return bl;
    }

    @Override
    public void handleStatus(byte status) {
        if (status == EntityStatuses.PLAY_ATTACK_SOUND) {
            this.attackTicksLeft = ATTACK_COOLDOWN;
        }
    }

    private float getAttackDamage() {
        return (float)this.getAttributeValue(EntityAttributes.GENERIC_ATTACK_DAMAGE);
    }

    public BarrowWightVariant getVariant() {
        return BarrowWightVariant.byId(this.getId());
    }



}
