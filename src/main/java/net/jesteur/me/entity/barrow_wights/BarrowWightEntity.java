/*
 * Decompiled with CFR 0.2.0 (FabricMC d28b102d).
 */
package net.jesteur.me.entity.barrow_wights;

import com.mojang.logging.LogUtils;
import com.mojang.serialization.Dynamic;
import net.jesteur.me.entity.dwarves.durin.DurinDwarfEntity;
import net.jesteur.me.entity.elves.galadhrim.GaladhrimElfEntity;
import net.jesteur.me.entity.goals.FastPonceAtTargetGoal;
import net.jesteur.me.entity.hobbits.shire.ShireHobbitEntity;
import net.jesteur.me.entity.orcs.mordor.MordorOrcEntity;
import net.jesteur.me.statusEffects.Hallucination;
import net.jesteur.me.statusEffects.ModStatusEffects;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.SonicBoomTask;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.ai.pathing.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.server.network.DebugInfoSender;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import net.minecraft.world.event.listener.EntityGameEventHandler;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

import java.util.function.BiConsumer;

public class BarrowWightEntity extends HostileEntity {
    private static final int MAX_HEALTH = 15;
    private static final float MOVEMENT_SPEED = 0.55f;
    private static final float KNOCKBACK_RESISTANCE = 1.0f;
    private static final float ATTACK_KNOCKBACK = 1.2f;
    private static final int ATTACK_DAMAGE = 3;

    private static final TrackedData<Boolean> CAN_SCREAM;
    private static final TrackedData<Integer> SCREAMING_TIME;

    public static final String LAST_SCREAM_TIME_KEY = "ScreamDelayTime";

    private int lastScreamTime;
    private int screamActionTime;

    private static final int SCREAM_DELAY = 150;
    private static final int SCREAM_EFFECT_DURATION = 100;

    public static final int SCREAM_ACTION_TIME = 35;

    public AnimationState attackingAnimationState = new AnimationState();
    public AnimationState screamAnimationState = new AnimationState();

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

        this.targetSelector.add(1, new RevengeGoal(this));
        this.targetSelector.add(2, new ActiveTargetGoal<>(this, PlayerEntity.class, true));
        this.targetSelector.add(3, new ActiveTargetGoal<>(this, GaladhrimElfEntity.class, true));
        this.targetSelector.add(4, new ActiveTargetGoal<>(this, DurinDwarfEntity.class, true));
        this.targetSelector.add(5, new ActiveTargetGoal<>(this, ShireHobbitEntity.class, true));
        this.targetSelector.add(6, new ActiveTargetGoal<>(this, MordorOrcEntity.class, true));
    }

    protected void initDataTracker() {
        super.initDataTracker();
        this.getDataTracker().startTracking(CAN_SCREAM, true);
        this.getDataTracker().startTracking(SCREAMING_TIME, 0);

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

                if(target != null && target.isPlayer())
                    target.sendMessage(Text.literal("SCREAMING!!!" + screamingTime));

                if(this.getScreamingActionTime() <= 0){

                    if(target != null && target.isPlayer()){
                        target.sendMessage(Text.literal("Barrow Down - BOO!!!!"));
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

    public int screaming() {
        return screamActionTime;
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

    protected void dropEquipment(DamageSource source, int lootingMultiplier, boolean allowDrops) {
        super.dropEquipment(source, lootingMultiplier, allowDrops);
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