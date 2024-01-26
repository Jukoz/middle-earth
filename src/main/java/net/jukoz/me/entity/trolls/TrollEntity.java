package net.jukoz.me.entity.trolls;

import com.mojang.datafixers.types.templates.Tag;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.entity.ModEntities;
import net.jukoz.me.entity.dwarves.durin.DurinDwarfEntity;
import net.jukoz.me.entity.elves.galadhrim.GaladhrimElfEntity;
import net.jukoz.me.entity.goals.*;
import net.jukoz.me.entity.hobbits.shire.ShireHobbitEntity;
import net.jukoz.me.entity.projectile.boulder.BoulderEntity;
import net.jukoz.me.item.ModFoodItems;
import net.jukoz.me.item.items.TrollArmorItem;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.entity.ai.pathing.PathNode;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageSources;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.Angerable;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AbstractDonkeyEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.StackReference;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.*;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.Difficulty;
import net.minecraft.world.EntityView;
import net.minecraft.world.World;

import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class TrollEntity extends AbstractDonkeyEntity implements Saddleable {
    public static final int ATTACK_COOLDOWN = 10;
    public static final float RESISTANCE = 0.15f;
    private int attackTicksLeft = 0;
    private int chargeCooldown = 100;
    private int throwCooldown = 100;
    private Vec3d targetDir = Vec3d.ZERO;
    private boolean sitting;
    private PlayerEntity owner;

    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState attackAnimationState = new AnimationState();
    public final AnimationState chargeAnimationState = new AnimationState();
    public final AnimationState throwingAnimationState = new AnimationState();
    private int idleAnimationTimeout = 0;
    private int throwingAnimationTimeout = 0;

    private static final TrackedData<Boolean> CHEST = DataTracker.registerData(TrollEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    public static final TrackedData<Boolean> CHARGING = DataTracker.registerData(TrollEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    public static final TrackedData<Boolean> THROWING = DataTracker.registerData(TrollEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final UUID TROLL_ARMOR_BONUS_ID = UUID.fromString("667E1665-8B10-40C8-8F9D-CF9B1667F295");


    public TrollEntity(EntityType<? extends AbstractDonkeyEntity> entityType, World world) {
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
                .add(EntityAttributes.HORSE_JUMP_STRENGTH, 0.0);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(1, new SwimGoal(this));
        this.goalSelector.add(1, new BeastSitGoal(this));
        this.goalSelector.add(3, new ChargeAttackGoal(this, 400));
        this.goalSelector.add(3, new MeleeAttackGoal(this, 0.9f, false));
        this.goalSelector.add(4, new BeastFollowOwnerGoal(this, 1.0, 10.0f, 2.0f, false));
        this.goalSelector.add(5, new WanderAroundFarGoal(this, 1.0));
        this.goalSelector.add(6, new LookAtEntityGoal(this, PlayerEntity.class, 6.0f));
        this.goalSelector.add(7, new LookAroundGoal(this));
        this.targetSelector.add(1, new BeastTrackOwnerAttackerGoal(this));
        this.targetSelector.add(2, new BeastAttackWithOwnerGoal(this));
        this.targetSelector.add(3, new TargetPlayerGoal(this));
        this.targetSelector.add(4, new ActiveTargetGoal<>(this, GaladhrimElfEntity.class, true));
        this.targetSelector.add(5, new ActiveTargetGoal<>(this, DurinDwarfEntity.class, true));
        this.targetSelector.add(6, new ActiveTargetGoal<>(this, ShireHobbitEntity.class, true));
    }

    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(CHEST, false);
        this.dataTracker.startTracking(CHARGING, false);
        this.dataTracker.startTracking(THROWING, false);
    }

    @Override
    public void onTrackedDataSet(TrackedData<?> data) {
        if (!this.firstUpdate && CHARGING.equals(data)) {
            this.chargeCooldown = this.chargeCooldown == 0 ? 400 : this.chargeCooldown;
        }
        if(!this.firstUpdate && THROWING.equals(data)) {
            this.throwCooldown = this.throwCooldown == 0 ? 200 : this.throwCooldown;
        }
        super.onTrackedDataSet(data);
    }

    private void setupAnimationStates() {
        if (this.idleAnimationTimeout <= 0) {
            this.idleAnimationTimeout = this.random.nextInt(40) + 80;
            this.idleAnimationState.start(this.age);
        } else {
            --this.idleAnimationTimeout;
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
    protected void updateLimbs(float posDelta) {
        float f = this.getPose() == EntityPose.STANDING ? Math.min(posDelta * 6.0f, 1.0f) : 0.0f;
        this.limbAnimator.updateLimbs(f, 0.2f);
    }

    @Override
    public void tick() {
        super.tick();

        if(this.getTarget() != null) {
            this.getLookControl().lookAt(this.getTarget());
        }

        if(this.isCharging() && !isThrowing()) {
            chargeAttack();
            if(!chargeAnimationState.isRunning()) {
                this.chargeAnimationState.start(this.age);
            }
        }
        if(this.chargeCooldown <= 370 || !isCharging()) {
            this.setCharging(false);
            this.targetDir = Vec3d.ZERO;
        }
        if(!this.isCharging()) {
            this.chargeAnimationState.stop();
        }
        if(chargeCooldown > 0) {
            --this.chargeCooldown;
        }

        if(throwCooldown == 0 && this.getTarget() != null && !isCharging()) {
            if(this.distanceTo(this.getTarget()) >= 5) {
                this.setThrowing(true);
                throwCooldown = 200;
            }
        }
        if(this.isThrowing() && throwCooldown <= 180) {
            throwAttack();
        }
        if(throwCooldown > 0) {
            --this.throwCooldown;
        }

        if (this.getWorld().isClient) {
            setupAnimationStates();
        }
    }

    class TargetPlayerGoal
            extends ActiveTargetGoal<PlayerEntity> {
        public TargetPlayerGoal(TrollEntity mob) {
            super((MobEntity)mob, PlayerEntity.class, true);
        }

        @Override
        public boolean canStart() {
            if (TrollEntity.this.getWorld().getDifficulty() == Difficulty.PEACEFUL || TrollEntity.this.isTame()) {
                if(TrollEntity.this.getTarget() instanceof PlayerEntity) {
                    TrollEntity.this.setTarget(null);
                }

                return false;
            }
            return super.canStart();
        }

        @Override
        public boolean shouldContinue() {
            if(!TrollEntity.this.isTame()) {
                return super.shouldContinue();
            }
            return false;
        }
    }

    @Override
    protected void initAttributes(Random random) {

    }

    @Override
    protected float getJumpVelocity() {
        return 0.5f * this.getJumpVelocityMultiplier() + this.getJumpBoostVelocityModifier();
    }

    @Override
    public int getJumpCooldown() {
        return this.chargeCooldown;
    }

    public void setBeastOwner(PlayerEntity owner) {
        this.owner = owner;
    }

    public PlayerEntity getBeastOwner() {
        return this.owner;
    }

    //region MOUNTING


    private StackReference createInventoryStackReference(final int slot, final Predicate<ItemStack> predicate) {
        return new StackReference(){

            @Override
            public ItemStack get() {
                return TrollEntity.this.items.getStack(slot);
            }

            @Override
            public boolean set(ItemStack stack) {
                if (!predicate.test(stack)) {
                    return false;
                }
                TrollEntity.this.items.setStack(slot, stack);
                TrollEntity.this.updateSaddle();
                return true;
            }
        };
    }



    public double getMountedHeightOffset() {
        float f = Math.min(0.25F, this.limbAnimator.getSpeed());
        float g = this.limbAnimator.getPos();
        return (double)this.getHeight() - 0.19 + (double)(0.12F * MathHelper.cos(g * 1.5F) * 2.0F * f);
    }

    @Override
    protected Vec3d getControlledMovementInput(PlayerEntity controllingPlayer, Vec3d movementInput) {
        if (this.isOnGround() && this.jumpStrength == 0.0f && this.isAngry() && !this.jumping) {
            return Vec3d.ZERO;
        }
        float f = controllingPlayer.sidewaysSpeed * 0.5f;
        float g = controllingPlayer.forwardSpeed * 0.75f;
        if (g <= 0.0f) {
            g *= 0.25f;
        }
        return new Vec3d(f, 0.0, g);
    }

    public boolean hasChest() {
        return (Boolean)this.dataTracker.get(CHEST);
    }

    public void setHasChest(boolean hasChest) {
        this.dataTracker.set(CHEST, hasChest);
    }

    public boolean isSitting() {
        return this.sitting;
    }

    public void setSitting(boolean sitting) {
        this.sitting = sitting;
    }

    public boolean isCommandItem(ItemStack stack) {
        return stack.isIn(TagKey.of(RegistryKeys.ITEM, new Identifier(MiddleEarth.MOD_ID, "bones")));
    }

    protected int getInventorySize() {
        return this.hasChest() ? 17 : super.getInventorySize();
    }

    protected void dropInventory() {
        super.dropInventory();
        if (this.hasChest()) {
            if (!this.getWorld().isClient) {
                this.dropItem(Blocks.CHEST);
            }

            this.setHasChest(false);
        }

    }

    public ItemStack getArmorType() {
        return this.getEquippedStack(EquipmentSlot.CHEST);
    }


    private void equipArmor(ItemStack stack) {
        this.equipStack(EquipmentSlot.CHEST, stack);
        this.setEquipmentDropChance(EquipmentSlot.CHEST, 0.0F);
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
                    itemStack.writeNbt(nbtCompound);
                    nbtList.add(nbtCompound);
                }
            }
            nbt.put("Items", nbtList);
        }
        if (!this.items.getStack(1).isEmpty()) {
            nbt.put("ArmorItem", this.items.getStack(1).writeNbt(new NbtCompound()));
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
                    this.items.setStack(j, ItemStack.fromNbt(nbtCompound));
                }
            }
        }
        if (nbt.contains("ArmorItem", 10)) {
            ItemStack itemStack = ItemStack.fromNbt(nbt.getCompound("ArmorItem"));
            if (!itemStack.isEmpty() && this.isHorseArmor(itemStack)) {
                this.items.setStack(1, itemStack);
            }
        }

        if (nbt.contains("SaddleItem", 10)) {
            ItemStack itemStack = ItemStack.fromNbt(nbt.getCompound("SaddleItem"));
            if (itemStack.isOf(Items.SADDLE)) {
                this.items.setStack(0, itemStack);
            }
        }

        this.updateSaddle();
    }

    @Override
    protected void updateSaddle() {
        if (!this.getWorld().isClient) {
            super.updateSaddle();
            this.setArmorTypeFromStack(this.items.getStack(1));
            this.setEquipmentDropChance(EquipmentSlot.CHEST, 0.0F);
        }
    }

    private void setArmorTypeFromStack(ItemStack stack) {
        this.equipArmor(stack);
        if (!this.getWorld().isClient) {
            this.getAttributeInstance(EntityAttributes.GENERIC_ARMOR).removeModifier(TROLL_ARMOR_BONUS_ID);
            if (this.isHorseArmor(stack)) {
                int i = ((TrollArmorItem)stack.getItem()).getBonus();
                if (i != 0) {
                    this.getAttributeInstance(EntityAttributes.GENERIC_ARMOR).addTemporaryModifier(new EntityAttributeModifier(TROLL_ARMOR_BONUS_ID, "Troll armor bonus", (double)i, EntityAttributeModifier.Operation.ADDITION));
                }
            }
        }
    }

    @Override
    public boolean hasArmorSlot() {
        return true;
    }

    @Override
    public void onInventoryChanged(Inventory sender) {
        ItemStack itemStack = this.getArmorType();
        super.onInventoryChanged(sender);
        ItemStack itemStack2 = this.getArmorType();
        if (this.age > 20 && this.isHorseArmor(itemStack2) && itemStack != itemStack2) {
            this.playSound(SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0.5F, 1.0F);
        }
    }

    @Override
    public StackReference getStackReference(int mappedIndex) {
        int j;
        int i = mappedIndex - 400;
        if (i >= 0 && i < 2 && i < this.items.size()) {
            if (i == 0) {
                return this.createInventoryStackReference(i, stack -> stack.isEmpty() || stack.isOf(Items.SADDLE));
            }
            if (i == 1) {
                if (!this.hasArmorSlot()) {
                    return StackReference.EMPTY;
                }
                return this.createInventoryStackReference(i, stack -> stack.isEmpty() || this.isHorseArmor((ItemStack)stack));
            }
        }
        if ((j = mappedIndex - 500 + 2) >= 2 && j < this.items.size()) {
            return StackReference.of(this.items, j);
        }
        return super.getStackReference(mappedIndex);
    }

    // Charge Attack
    @Override
    protected void jump(float strength, Vec3d movementInput) {
        if(this.chargeCooldown <= 0) {
            this.setCharging(true);
            this.chargeCooldown = 400;
        }
    }

    public void setCharging(boolean charging) {
        this.dataTracker.set(CHARGING, charging);
    }

    public boolean isCharging() {
        return this.dataTracker.get(CHARGING);
    }

    public void setThrowing(boolean throwing) {
        this.dataTracker.set(THROWING, throwing);
    }

    public boolean isThrowing() {
        return this.dataTracker.get(THROWING);
    }

    @Override
    public void startJumping(int height) {
        this.playSound(SoundEvents.ENTITY_CAMEL_DASH, 1.0f, 1.0f);
        this.setCharging(true);
    }

    public void tryBonding(PlayerEntity player) {
        if(random.nextDouble() <= 0.1d) {

            this.setOwnerUuid(player.getUuid());
            this.setBeastOwner(player);
            this.setTame(true);
            if (player instanceof ServerPlayerEntity) {
                Criteria.TAME_ANIMAL.trigger((ServerPlayerEntity)player, this);
            }
            this.getWorld().sendEntityStatus(this, EntityStatuses.ADD_POSITIVE_PLAYER_REACTION_PARTICLES);

            this.chargeCooldown = 0;
        }
        else {
            this.getWorld().sendEntityStatus(this, EntityStatuses.ADD_NEGATIVE_PLAYER_REACTION_PARTICLES);
        }
    }

    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        boolean bl = !this.isBaby() && this.isTame() && player.shouldCancelInteraction();

        if(player.getStackInHand(hand).isOf(ModFoodItems.COOKED_HORSE)) {
            this.tryBonding(player);
            player.getStackInHand(hand).decrement(1);
        }

        if(this.isTame() && isCommandItem(player.getStackInHand(hand))) {
            this.setSitting(!isSitting());
        }

        if (!this.hasPassengers() && !bl) {
            ItemStack itemStack = player.getStackInHand(hand);
            if (!itemStack.isEmpty()) {
                if (this.isBreedingItem(itemStack)) {
                    return this.interactHorse(player, itemStack);
                }

                if (!this.isTame()) {
                    this.playAngrySound();
                    return ActionResult.success(this.getWorld().isClient);
                }

                if (!this.hasChest() && itemStack.isOf(Items.CHEST)) {
                    this.addChest(player, itemStack);
                    return ActionResult.success(this.getWorld().isClient);
                }
                if (this.hasArmorSlot() && this.isHorseArmor(itemStack) && !this.hasArmorInSlot()) {
                    this.equipHorseArmor(player, itemStack);
                    return ActionResult.success(this.getWorld().isClient);
                }
            }
        }
        if(this.isTame() && !isCommandItem(player.getStackInHand(hand))) {
            super.interactMob(player, hand);
        }
        return ActionResult.success(this.getWorld().isClient);
    }

    @Override
    public boolean isHorseArmor(ItemStack item) {
        return item.getItem() instanceof TrollArmorItem;
    }

    private void addChest(PlayerEntity player, ItemStack chest) {
        this.setHasChest(true);
        this.playAddChestSound();
        if (!player.getAbilities().creativeMode) {
            chest.decrement(1);
        }
        this.onChestedStatusChanged();
    }

    protected void playAddChestSound() {
        this.playSound(SoundEvents.ENTITY_DONKEY_CHEST, 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
    }

    public int getInventoryColumns() {
        return 5;
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(SoundEvents.ENTITY_WARDEN_STEP, 0.15F, 2.0F);
    }

    //endregion

    //region COMBAT

    public TrollEntity.State getState() {
        if(this.attackTicksLeft > 0) {
            return State.ATTACK;
        }
        else if (this.isAttacking()) {
            return State.AGGRESSIVE;
        }
        else if(this.isInWalkTargetRange()) {
            return State.WALKING;
        }
        return State.NEUTRAL;
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

    public int getChargeCooldown() {
        return this.chargeCooldown;
    }
    public void setChargeCooldown(int chargeCooldown) {
        this.chargeCooldown = chargeCooldown;
    }
    public int getThrowCooldown() {return this.throwCooldown;}
    public void setThrowCooldown(int throwCooldown) {this.throwCooldown = throwCooldown;}

    @Override
    public void tickMovement() {
        super.tickMovement();
        if (this.attackTicksLeft > 0) {
            --this.attackTicksLeft;
        }
    }

    public void chargeAttack() {
        List<Entity> entities = this.getWorld().getOtherEntities(this, this.getBoundingBox().expand(0.2f, 0.0, 0.2f));

        if(!this.isTame() && !this.getWorld().isClient) {
            if(targetDir == Vec3d.ZERO && this.getTarget() != null) {
                targetDir = new Vec3d( this.getTarget().getBlockPos().getX() - this.getBlockPos().getX(),
                        this.getTarget().getBlockPos().getY() - this.getBlockPos().getY(),
                        this.getTarget().getBlockPos().getZ() - this.getBlockPos().getZ());
            }
            this.setYaw((float) Math.toDegrees(Math.atan2(-targetDir.x, targetDir.z)));
            this.setVelocity(targetDir.multiply(1,0,1).normalize().multiply(1.0d));
            System.out.println(targetDir);
            System.out.println(this.getYaw());

        }
        else if (this.getWorld().isClient) {
            this.setVelocity(this.getRotationVector().multiply(1,0,1).normalize().multiply(1.0d));
        }

        for(Entity entity : entities) {
            if(entity.getUuid() != this.getOwnerUuid() && entity != this) {
                entity.damage(entity.getDamageSources().mobAttack(this), 16.0f);
            }
        }
        this.getWorld().addParticle(ParticleTypes.EXPLOSION, this.getX(), this.getY(), this.getZ(), 0, 0, 0);
        if(!chargeAnimationState.isRunning()) {
            this.chargeAnimationState.start(this.age);
        }
    }

    public void throwAttack() {

        Entity target = this.getTarget();
        if(target != null) {
            this.setThrowing(false);

            Vec3d rotationVec = this.getRotationVec(1.0f);
            BoulderEntity boulder = new BoulderEntity(ModEntities.BOULDER, this, this.getWorld());
            double x = target.getX() - this.getX();
            double y = target.getBodyY(0.3333333333333333) - boulder.getY();
            double z = target.getZ() - this.getZ();
            double c = Math.sqrt(x * x + z * z);

            boulder.setPosition(this.getX() + rotationVec.x * 2.0f, this.getBodyY(0.75f), boulder.getZ() + rotationVec.z * 2.0f);
            boulder.setVelocity(x * 0.8d, y + c * 0.3d , z * 0.8d, 1.0f, 8 - this.getWorld().getDifficulty().getId() * 4);
            this.getWorld().spawnEntity(boulder);
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
        this.playSound(SoundEvents.ENTITY_HOGLIN_ATTACK, 1.5f, 0.8f);
        return bl;
    }

    @Override
    public void handleStatus(byte status) {
        if (status == EntityStatuses.PLAY_ATTACK_SOUND) {
            this.attackTicksLeft = ATTACK_COOLDOWN;
        }
        if(status == 4){
            this.attackAnimationState.start(this.age);
        }
        if (status == EntityStatuses.ADD_POSITIVE_PLAYER_REACTION_PARTICLES) {
            this.spawnPlayerReactionParticles(true);
        } else if (status == EntityStatuses.ADD_NEGATIVE_PLAYER_REACTION_PARTICLES) {
            this.spawnPlayerReactionParticles(false);
        } else {
            super.handleStatus(status);
        }
    }

    private float getAttackDamage() {
        return (float)this.getAttributeValue(EntityAttributes.GENERIC_ATTACK_DAMAGE);
    }

    @Override
    public EntityView method_48926() {
        return null;
    }

    public enum State {
        NEUTRAL,
        WALKING,
        AGGRESSIVE,
        ATTACK;
    }

    //endregion
}
