/*
 * Decompiled with CFR 0.2.0 (FabricMC d28b102d).
 */
package net.jukoz.me.entity.barrow_wights;

import net.jukoz.me.entity.beasts.trolls.TrollEntity;
import net.jukoz.me.entity.dwarves.longbeards.LongbeardDwarfEntity;
import net.jukoz.me.entity.elves.galadhrim.GaladhrimElfEntity;
import net.jukoz.me.entity.hobbits.shire.ShireHobbitEntity;
import net.jukoz.me.entity.humans.gondor.GondorHumanEntity;
import net.jukoz.me.entity.humans.rohan.RohanHumanEntity;
import net.jukoz.me.entity.nazguls.NazgulEntity;
import net.jukoz.me.entity.orcs.misties.MistyGoblinEntity;
import net.jukoz.me.entity.orcs.mordor.MordorOrcEntity;
import net.jukoz.me.entity.spider.MirkwoodSpiderEntity;
import net.jukoz.me.entity.uruks.misties.MistyHobgoblinEntity;
import net.jukoz.me.entity.uruks.mordor.MordorBlackUrukEntity;
import net.jukoz.me.statusEffects.ModStatusEffects;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

public class BarrowWightEntity extends HostileEntity {
    private static final int MAX_HEALTH = 40;
    private static final float MOVEMENT_SPEED = 0.6f;
    private static final float KNOCKBACK_RESISTANCE = 1.0f;
    private static final float ATTACK_KNOCKBACK = 1.2f;
    private static final int ATTACK_DAMAGE = 3;
    private static final TrackedData<Boolean> CAN_SCREAM;
    private static final TrackedData<Integer> SCREAMING_TIME;
    public static final String LAST_SCREAM_TIME_KEY = "ScreamDelayTime";
    private int lastScreamTime;
    private static final int SCREAM_DELAY = 150;
    private static final int SCREAM_EFFECT_DURATION = 100;

    public static final int SCREAM_ACTION_TIME = 35;

    public BarrowWightEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }
    public static DefaultAttributeContainer.Builder setAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, MOVEMENT_SPEED)
                .add(EntityAttributes.GENERIC_MAX_HEALTH, MAX_HEALTH)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, KNOCKBACK_RESISTANCE)
                .add(EntityAttributes.GENERIC_ATTACK_SPEED, 0.85)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 28.0)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, ATTACK_DAMAGE)
                .add(EntityAttributes.GENERIC_ATTACK_KNOCKBACK, ATTACK_KNOCKBACK);
    }

    protected void initGoals() {
        this.goalSelector.add(1, new SwimGoal(this));

        this.goalSelector.add(2, new MeleeAttackGoal(this, MOVEMENT_SPEED , false));
        this.goalSelector.add(3, new WanderAroundFarGoal(this, MOVEMENT_SPEED * 0.8f));
        this.goalSelector.add(4, new LookAtEntityGoal(this, LivingEntity.class, 32.0F));
        this.goalSelector.add(5, new LookAroundGoal(this));

        int i = 0;
        this.targetSelector.add(++i, new RevengeGoal(this));
        this.targetSelector.add(++i, new ActiveTargetGoal<>(this, PlayerEntity.class, true));
        this.targetSelector.add(++i, new ActiveTargetGoal<>(this, TrollEntity.class, true));
        this.targetSelector.add(++i, new ActiveTargetGoal<>(this, MordorBlackUrukEntity.class, true));
        this.targetSelector.add(++i, new ActiveTargetGoal<>(this, MistyHobgoblinEntity.class, true));
        this.targetSelector.add(++i, new ActiveTargetGoal<>(this, MordorOrcEntity.class, true));
        this.targetSelector.add(++i, new ActiveTargetGoal<>(this, MistyGoblinEntity.class, true));
        this.targetSelector.add(++i, new ActiveTargetGoal<>(this, MirkwoodSpiderEntity.class, true));
        this.targetSelector.add(++i, new ActiveTargetGoal<>(this, NazgulEntity.class, true));
        this.targetSelector.add(++i, new ActiveTargetGoal<>(this, GondorHumanEntity.class, true));
        this.targetSelector.add(++i, new ActiveTargetGoal<>(this, RohanHumanEntity.class, true));
        this.targetSelector.add(++i, new ActiveTargetGoal<>(this, GaladhrimElfEntity.class, true));
        this.targetSelector.add(++i, new ActiveTargetGoal<>(this, LongbeardDwarfEntity.class, true));
        this.targetSelector.add(++i, new ActiveTargetGoal<>(this, ShireHobbitEntity.class, true));
    }

    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);
        builder.add(CAN_SCREAM, true);
        builder.add(SCREAMING_TIME, 0);
    }

    public boolean canScream() {
        return this.getDataTracker().get(CAN_SCREAM);
    }
    public Integer getScreamingActionTime() {
        return this.getDataTracker().get(SCREAMING_TIME);
    }

    public void setCanScream(boolean canScream) {
        if(!canScream) {
            this.lastScreamTime = SCREAM_DELAY;
        }
        this.dataTracker.set(CAN_SCREAM, canScream);
    }

    private void setScreamedTime(int time) {
        this.lastScreamTime = time;
    }

    public void setScreamingActionTime(int screamingTime) {
        this.dataTracker.set(SCREAMING_TIME, screamingTime);
    }

    @Override
    public void tick() {
        if (!this.getWorld().isClient && this.isAlive() && !this.isAiDisabled()) {
            LivingEntity target = getTarget();

            int screamingTime = this.getScreamingActionTime();
            if(screamingTime > 0 ){
                screamingTime --;
                this.setScreamingActionTime(screamingTime);

                if(this.getScreamingActionTime() <= 0){
                    if(target != null && target.isPlayer()){
                        int value = Random.create().nextBetweenExclusive(0, 100);
                        if(value < 5){
                            target.sendMessage(Text.literal("The barrows says BOO!!!!"));
                        }
                        target.addStatusEffect(new StatusEffectInstance(ModStatusEffects.HALLUCINATION, SCREAM_EFFECT_DURATION), this);
                        target.addStatusEffect(new StatusEffectInstance(StatusEffects.DARKNESS, SCREAM_EFFECT_DURATION), this);
                    }

                    this.setScreamingActionTime(-1);
                }
            }
            if (!this.canScream()) {
                --this.lastScreamTime;
                if (this.lastScreamTime < 0) {
                    this.setCanScream(true);
                }
            }
            if(this.getTarget() == null){
                this.setCanScream(false);
            } else if(this.canScream()) {
                tryToScream();
            }
        }
        super.tick();
    }

    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putInt(LAST_SCREAM_TIME_KEY, !this.canScream() ? this.lastScreamTime : -1);
    }

    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        if (nbt.contains(LAST_SCREAM_TIME_KEY, 99) && nbt.getInt(LAST_SCREAM_TIME_KEY) > -1) {
            this.setScreamedTime(nbt.getInt(LAST_SCREAM_TIME_KEY));
        }
    }

    protected void tryToScream() {
        LivingEntity target = getTarget();

        if(target == null) return;
        if(target.distanceTo(this) > 25) return;
        if(target.distanceTo(this) < 5) return;

        if(target.hasStatusEffect(ModStatusEffects.HALLUCINATION)) return;

        this.setScreamingActionTime(SCREAM_ACTION_TIME);
        this.setCanScream(false);
    }

    public boolean canFreeze() {
        return false;
    }

    protected SoundEvent getAmbientSound() {
        return SoundEvents.BLOCK_SCULK_SHRIEKER_SHRIEK;
    }

    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.BLOCK_SCULK_SHRIEKER_SHRIEK;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.BLOCK_SCULK_SHRIEKER_BREAK;
    }

    SoundEvent getStepSound() {
        return SoundEvents.ENTITY_SKELETON_STEP;
    }

    @Override
    public float getSoundPitch() {
        return 0.1f;
    }

    @Override
    protected void dropEquipment(ServerWorld world, DamageSource source, boolean causedByPlayer) {
        super.dropEquipment(world, source, causedByPlayer);
        Entity entity = source.getAttacker();
        if (entity instanceof CreeperEntity creeperEntity) {
            if (creeperEntity.shouldDropHead()) {
                creeperEntity.onHeadDropped();
                this.dropItem(Items.SKELETON_SKULL);
            }
        }
    }

    public BarrowWightVariant getVariant() {
        return BarrowWightVariant.BASIC;
    }

    static {
        CAN_SCREAM = DataTracker.registerData(BarrowWightEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
        SCREAMING_TIME = DataTracker.registerData(BarrowWightEntity.class, TrackedDataHandlerRegistry.INTEGER);
    }
}