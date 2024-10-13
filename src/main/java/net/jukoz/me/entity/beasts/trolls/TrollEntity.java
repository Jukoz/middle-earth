package net.jukoz.me.entity.beasts.trolls;

import net.jukoz.me.MiddleEarth;
import net.jukoz.me.entity.ModEntities;
import net.jukoz.me.entity.beasts.AbstractBeastEntity;
import net.jukoz.me.entity.dwarves.longbeards.LongbeardDwarfEntity;
import net.jukoz.me.entity.elves.galadhrim.GaladhrimElfEntity;
import net.jukoz.me.entity.goals.*;
import net.jukoz.me.entity.hobbits.shire.ShireHobbitEntity;
import net.jukoz.me.entity.humans.bandit.BanditHumanEntity;
import net.jukoz.me.entity.humans.gondor.GondorHumanEntity;
import net.jukoz.me.entity.humans.rohan.RohanHumanEntity;
import net.jukoz.me.entity.projectile.boulder.BoulderEntity;
import net.jukoz.me.item.ModFoodItems;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.*;
import net.minecraft.world.World;

import java.util.List;

public class TrollEntity extends AbstractBeastEntity {
    private int throwCooldown = 100;
    public final AnimationState throwingAnimationState = new AnimationState();

    private int throwingAnimationTimeout = 0;
    private int bondingTries = 0;
    private int bondingTimeout = 0;

    public static final TrackedData<Boolean> THROWING = DataTracker.registerData(TrollEntity.class, TrackedDataHandlerRegistry.BOOLEAN);


    /* Temporary disabled until next update
    @Override
    public boolean hasArmorSlot() {
        return false;
    }*/

    public TrollEntity(EntityType<? extends TrollEntity> entityType, World world) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder setAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.35f)
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 120.0)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 0.6)
                .add(EntityAttributes.GENERIC_ATTACK_SPEED, 0.9)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 28.0)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 10.0)
                .add(EntityAttributes.GENERIC_JUMP_STRENGTH, 0.0);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(1, new SwimGoal(this));
        this.goalSelector.add(2, new BeastSitGoal(this));
        this.goalSelector.add(3, new MeleeAttackGoal(this, 0.9f, false));
        this.goalSelector.add(4, new ChargeAttackGoal(this, maxChargeCooldown()));
        this.goalSelector.add(5, new WanderAroundFarGoal(this, 1.0));
        this.goalSelector.add(6, new LookAtEntityGoal(this, PlayerEntity.class, 6.0f));
        this.goalSelector.add(7, new LookAroundGoal(this));
        this.targetSelector.add(1, new BeastTrackOwnerAttackerGoal((AbstractBeastEntity) this));
        this.targetSelector.add(2, new BeastAttackWithOwnerGoal((AbstractBeastEntity)this));
        this.targetSelector.add(3, new RevengeGoal(this, new Class[0]));
        this.targetSelector.add(4, new TargetPlayerGoal(this));
        this.targetSelector.add(4, new ActiveTargetGoal<>(this, GaladhrimElfEntity.class, true));
        this.targetSelector.add(5, new ActiveTargetGoal<>(this, LongbeardDwarfEntity.class, true));
        this.targetSelector.add(6, new ActiveTargetGoal<>(this, GondorHumanEntity.class, true));
        this.targetSelector.add(7, new ActiveTargetGoal<>(this, RohanHumanEntity.class, true));
        this.targetSelector.add(8, new ActiveTargetGoal<>(this, BanditHumanEntity.class, true));
        this.targetSelector.add(9, new ActiveTargetGoal<>(this, ShireHobbitEntity.class, true));
    }

    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);
        builder.add(THROWING, false);
    }

    @Override
    public void onTrackedDataSet(TrackedData<?> data) {
        if(!this.firstUpdate && THROWING.equals(data)) {
            this.throwCooldown = this.throwCooldown == 0 ? 200 : this.throwCooldown;
        }
        super.onTrackedDataSet(data);
    }

    @Override
    protected void setupAnimationStates() {
        if (this.idleAnimationTimeout <= 0) {
            this.idleAnimationTimeout = this.random.nextInt(40) + 80;
            this.idleAnimationState.start(this.age);
        } else {
            --this.idleAnimationTimeout;
        }
        if(this.isSitting()) {
            this.sittingAnimationState.startIfNotRunning(this.age);
        }
        else {
            this.sittingAnimationState.stop();
        }

        if(this.isThrowing() && this.throwingAnimationTimeout <= 0) {
            this.throwingAnimationTimeout = 100;
            this.throwingAnimationState.start(this.age);
        }else {
            --this.throwingAnimationTimeout;
        }
        if(isThrowing()) {
            this.setMovementSpeed(0);
        }

        if(!this.isThrowing()) {
            this.throwingAnimationState.stop();
        }
    }

    @Override
    public void tick() {
        super.tick();

        if(this.getTarget() != null) {
            this.getLookControl().lookAt(this.getTarget());
        }

        if(this.isCharging()) {
            chargeAttack();
            if(!chargeAnimationState.isRunning()) {
                this.chargeAnimationState.start(this.age);
            }
        }

        if(throwCooldown == 0 && this.getTarget() != null && !isCharging()) {
            if(this.squaredDistanceTo(this.getTarget()) >= 25 && !this.hasPassengers() && canThrow()) {
                this.setThrowing(true);
                throwCooldown = 200;
            }
        }
        if(this.isThrowing() && canThrow()) {
            this.setVelocity(Vec3d.ZERO);
            if(throwCooldown <= 180) {
                throwAttack();
            }
        }
        if(throwCooldown > 0) {
            --this.throwCooldown;
        }

        if (!this.getWorld().isClient) {
            if(this.bondingTimeout > 0) {
                this.bondingTimeout--;
            }
        }
    }

    @Override
    protected float getSaddledSpeed(PlayerEntity controllingPlayer) {
        return (float)this.getAttributeValue(EntityAttributes.GENERIC_MOVEMENT_SPEED) * 0.5f;
    }

    public boolean isCommandItem(ItemStack stack) {
        return stack.isIn(TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "bones")));
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putBoolean("ChestedTroll", this.hasChest());
        if (this.hasChest()) {
            NbtList nbtList = new NbtList();
            for(int i = 2; i < this.items.size(); ++i) {
                ItemStack itemStack = this.items.getStack(i);
                if (!itemStack.isEmpty()) {
                    NbtCompound nbtCompound = new NbtCompound();
                    nbtCompound.putByte("Slot", (byte)i);
                    nbtList.add(itemStack.encode(this.getRegistryManager(), nbtCompound));
                }
            }
            nbt.put("Items", nbtList);
        }
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.setHasChest(nbt.getBoolean("ChestedTroll"));
        this.onChestedStatusChanged();
        if (this.hasChest()) {
            NbtList nbtList = nbt.getList("Items", 10);

            for(int i = 0; i < nbtList.size(); ++i) {
                NbtCompound nbtCompound = nbtList.getCompound(i);
                int j = nbtCompound.getByte("Slot") & 255;
                if (j >= 2 && j < this.items.size()) {
                    this.items.setStack(j, ItemStack.fromNbt(getRegistryManager(), nbtCompound).orElse(ItemStack.EMPTY));
                }
            }
        }
        if (nbt.contains("SaddleItem", 10)) {
            ItemStack itemStack = (ItemStack)ItemStack.fromNbt(this.getRegistryManager(), nbt.getCompound("SaddleItem")).orElse(ItemStack.EMPTY);
            if (itemStack.isOf(Items.SADDLE)) {
                this.items.setStack(0, itemStack);
            }
        }

        this.updateSaddledFlag();
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
        }
        this.playSound(SoundEvents.ENTITY_HOGLIN_ATTACK, 1.5f, 0.8f);
        return bl;
    }

    @Override
    public boolean shouldAttackWhenMounted() {
        return true;
    }

    public boolean canThrow() {
        return !this.isSitting();
    }

    public void setThrowing(boolean throwing) {
        this.dataTracker.set(THROWING, throwing);
    }

    public boolean isThrowing() {
        return this.dataTracker.get(THROWING);
    }

    @Override
    public int chargeDuration() {
        return 25;
    }

    @Override
    public boolean isBondingItem(ItemStack itemStack) {
        return false;
    }

    public int getBondingTimeout() {
        return bondingTimeout;
    }
    public void setBondingTimeout(int bondingTimeout) {
        this.bondingTimeout = bondingTimeout;
    }

    @Override
    public void tryBonding(PlayerEntity player) {

        if(player.isCreative()) {
            tameBeast(player);
            this.getWorld().sendEntityStatus(this, EntityStatuses.ADD_POSITIVE_PLAYER_REACTION_PARTICLES);
            this.setChargeTimeout(0);
        }
        else if(this.getBondingTimeout() <= 0) {
            if(random.nextFloat() <= 0.4f) {
                this.bondingTries++;
                if(bondingTries == 3) {
                    tameBeast(player);
                    this.getWorld().sendEntityStatus(this, EntityStatuses.ADD_POSITIVE_PLAYER_REACTION_PARTICLES);
                    this.setChargeTimeout(0);
                }
            }
            player.getStackInHand(player.getActiveHand()).decrement(1);
            this.setBondingTimeout(40);

        }
    }

    public void throwAttack() {
        Entity target = this.getTarget();
        if(target != null && !this.getWorld().isClient) {
            this.setThrowing(false);

            Vec3d rotationVec = this.getRotationVec(1.0f);
            BoulderEntity boulder = new BoulderEntity(ModEntities.BOULDER, this, this.getWorld());
            double x = target.getX() - this.getX();
            double y = target.getBodyY(0.3333333333333333) - boulder.getY();
            double z = target.getZ() - this.getZ();
            double c = Math.sqrt(x * x + z * z);

            boulder.setPosition(this.getX() + rotationVec.x * 2.0f, this.getBodyY(0.75f), boulder.getZ() + rotationVec.z * 2.0f);
            boulder.setVelocity(x * 0.8d, y + c * 0.3d , z * 0.8d, 1.0f, 8 - this.getWorld().getDifficulty().getId() * 4);
            if(boulder != null) {
                this.getWorld().spawnEntity(boulder);
            }
        }
    }

    @Override
    public void chargeAttack() {
        List<Entity> entities = this.getWorld().getOtherEntities(this, this.getBoundingBox().expand(0.2f, 0.0, 0.2f));

        if(!this.isTame() && !this.getWorld().isClient) {
            if(targetDir == Vec3d.ZERO && this.getTarget() != null) {
                targetDir = new Vec3d( this.getTarget().getBlockPos().getX() - this.getBlockPos().getX(),
                        this.getTarget().getBlockPos().getY() - this.getBlockPos().getY(),
                        this.getTarget().getBlockPos().getZ() - this.getBlockPos().getZ());
            }
            this.setYaw((float) Math.toDegrees(Math.atan2(-targetDir.x, targetDir.z)));
            this.setVelocity(targetDir.multiply(1,0,1).normalize().multiply(1.0d - ((double)(this.chargeTimeout - (maxChargeCooldown() - chargeDuration())) / chargeDuration())).add(0, this.getVelocity().y, 0));
        }
        else if (this.getWorld().isClient) {
            this.setVelocity(this.getRotationVector().multiply(1,0,1).normalize().multiply(1.0d - ((double)(this.chargeTimeout - (maxChargeCooldown() - chargeDuration())) / chargeDuration())).add(0, this.getVelocity().y, 0));
        }

        for(Entity entity : entities) {
            if(entity.getUuid() != this.getOwnerUuid() && entity != this && !this.getPassengerList().contains(entity)) {
                entity.damage(entity.getDamageSources().mobAttack(this), 16.0f);
            }
        }
        this.getWorld().addParticle(ParticleTypes.EXPLOSION, this.getX(), this.getY(), this.getZ(), 0, 0, 0);
        this.chargeAnimationState.startIfNotRunning(this.age);
    }
}