package net.jukoz.me.entity.balrog;

import net.jukoz.me.entity.dwarves.durin.DurinDwarfEntity;
import net.jukoz.me.entity.elves.galadhrim.GaladhrimElfEntity;
import net.jukoz.me.entity.hobbits.shire.ShireHobbitEntity;
import net.jukoz.me.entity.beasts.trolls.TrollEntity;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class BalrogEntity extends HostileEntity {

    private int attackTicksLeft = 0;
    public static final int ATTACK_COOLDOWN = 10;

    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState attackAnimationState = new AnimationState();
    private int idleAnimationTimeout = 0;
    private int attackAnimationTimeout = 0;

    private Random randomRegen = new Random();

    public BalrogEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }

    protected void initGoals() {
        this.goalSelector.add(1, new SwimGoal(this));
        this.goalSelector.add(2, new RevengeGoal(this));
        this.goalSelector.add(3, new MeleeAttackGoal(this, 1.0f, true));
        this.goalSelector.add(4, new WanderAroundGoal(this, 1.0F));
        this.goalSelector.add(5, new LookAtEntityGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.add(6, new LookAroundGoal(this));

        this.targetSelector.add(1, new ActiveTargetGoal<>(this, PlayerEntity.class, true));
        this.targetSelector.add(2, new ActiveTargetGoal<>(this, GaladhrimElfEntity.class, true));
        this.targetSelector.add(3, new ActiveTargetGoal<>(this, DurinDwarfEntity.class, true));
        this.targetSelector.add(4, new ActiveTargetGoal<>(this, ShireHobbitEntity.class, true));

    }

    public static DefaultAttributeContainer.Builder createBalrogAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 300.0)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.4)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0)
                .add(EntityAttributes.GENERIC_ATTACK_SPEED, 1.0)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 40.0)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 25.0)
                .add(EntityAttributes.GENERIC_ARMOR, 10.0);
    }

    public TrollEntity.State getState() {
        if(this.attackTicksLeft > 0) {
            return TrollEntity.State.ATTACK;
        }
        else if (this.isAttacking()) {
            return TrollEntity.State.AGGRESSIVE;
        }
        else if(this.isInWalkTargetRange()) {
            return TrollEntity.State.WALKING;
        }
        return TrollEntity.State.NEUTRAL;
    }

    public int getAttackTicksLeft() {
        return this.attackTicksLeft;
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
                d = livingEntity.getAttributeValue(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE);
            } else {
                d = 0.0;
            }
            double e = Math.max(0.0, 1.0 - d);
            target.setVelocity(target.getVelocity().multiply(1f + (0.8f * e))); //.add(0.0, (double)0.1f * e, 0.0));
            this.applyDamageEffects(this, target);
        }
        this.playSound(SoundEvents.ENTITY_WARDEN_ATTACK_IMPACT, 1.5f, 0.8f);
        return bl;
    }

    private float getAttackDamage() {
        return (float)this.getAttributeValue(EntityAttributes.GENERIC_ATTACK_DAMAGE);
    }

    @Override
    public boolean disablesShield() {
        return true;
    }


    private void setupAnimationStates() {
        if (this.idleAnimationTimeout <= 0) {
            this.idleAnimationTimeout = this.random.nextInt(40) + 80;
            this.idleAnimationState.start(this.age);
        } else {
            --this.idleAnimationTimeout;
        }
    }

    @Override
    protected void updateLimbs(float posDelta) {
        float f = this.getPose() == EntityPose.STANDING ? Math.min(posDelta * 6.0f, 1.0f) : 0.0f;
        this.limbAnimator.updateLimbs(f, 0.2f);
    }

    @Override
    public void tick() {
        super.tick();
        if(this.getWorld().isClient){
            setupAnimationStates();
        }

        if(this.getHealth() < this.getMaxHealth() / 2){
            if(randomRegen.nextInt(100) <= 5){
                this.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 100));
            }
        }
    }

    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_WARDEN_AMBIENT;
    }

    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_WARDEN_HURT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_WARDEN_DEATH;
    }

    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(SoundEvents.ENTITY_WARDEN_STEP, 0.15F, 2.0F);
    }


    public BalrogVariant getVariant() {
        return BalrogVariant.byId(this.getId());
    }

    public boolean canBreatheInWater() {
        return false;
    }

    public boolean isPushedByFluids() {
        return false;
    }

    @Override
    public boolean handleFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource) {
        return fallDistance > 20;
    }

    @Override
    public void handleStatus(byte status) {
        if(status == 4){
            this.attackAnimationState.start(this.age);
        }
    }

    @Override
    public boolean isPersistent() {
        return true;
    }

    public enum State {
        NEUTRAL,
        WALKING,
        AGGRESSIVE,
        ATTACK;
    }
}
