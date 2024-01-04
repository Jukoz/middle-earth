package net.jukoz.me.entity.trolls;

import net.jukoz.me.entity.dwarves.durin.DurinDwarfEntity;
import net.jukoz.me.entity.elves.galadhrim.GaladhrimElfEntity;
import net.jukoz.me.entity.hobbits.shire.ShireHobbitEntity;
import net.jukoz.me.item.ModEquipmentItems;
import net.jukoz.me.item.items.TrollArmorItem;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AbstractHorseEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.StackReference;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.EntityView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

//TODO Tamable with horse meat
//TODO Inventory with saddle, armour and couple slots of storage
//TODO Charge with rider
//TODO Redo attack animation
//TODO Custom Saddle
//TODO Troll Armour
//TODO Troll Weapon

//TODO Brain
//TODO Throw attack
//TODO Charge attack
//TODO Sleeping


public class TrollEntity extends AbstractHorseEntity {
    public static final int ATTACK_COOLDOWN = 10;
    public static final float RESISTANCE = 0.15f;
    private int attackTicksLeft = 0;

    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState attackAnimationState = new AnimationState();
    private int idleAnimationTimeout = 0;

    private static final TrackedData<Boolean> CHEST = DataTracker.registerData(TrollEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final UUID TROLL_ARMOR_BONUS_ID = UUID.fromString("667E1665-8B10-40C8-8F9D-CF9B1667F295");


    public TrollEntity(EntityType<? extends AbstractHorseEntity> entityType, World world) {
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

    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(CHEST, false);
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
        if (this.getWorld().isClient) {
            setupAnimationStates();
        }
    }

    @Override
    protected float getJumpVelocity() {
        return 0.5f * this.getJumpVelocityMultiplier() + this.getJumpBoostVelocityModifier();
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(1, new SwimGoal(this));
        this.goalSelector.add(2, new MeleeAttackGoal(this, 0.9f, false));
        this.goalSelector.add(3, new HorseBondWithPlayerGoal(this, 1.2));
        this.goalSelector.add(4, new WanderAroundFarGoal(this, 1.0));
        this.goalSelector.add(5, new LookAtEntityGoal(this, PlayerEntity.class, 6.0f));
        this.goalSelector.add(6, new LookAroundGoal(this));
        this.targetSelector.add(1, new ActiveTargetGoal<>(this, PlayerEntity.class, true));
        this.targetSelector.add(2, new ActiveTargetGoal<>(this, GaladhrimElfEntity.class, true));
        this.targetSelector.add(3, new ActiveTargetGoal<>(this, DurinDwarfEntity.class, true));
        this.targetSelector.add(4, new ActiveTargetGoal<>(this, ShireHobbitEntity.class, true));
    }

    //region MOUNTING


    @Override
    public void saddle(@Nullable SoundCategory sound) {
        this.items.setStack(0, new ItemStack(ModEquipmentItems.BEAST_CHAINS));
    }

    public double getMountedHeightOffset() {
        float f = Math.min(0.25F, this.limbAnimator.getSpeed());
        float g = this.limbAnimator.getPos();
        return (double)this.getHeight() - 0.19 + (double)(0.12F * MathHelper.cos(g * 1.5F) * 2.0F * f);
    }

    public boolean hasChest() {
        return (Boolean)this.dataTracker.get(CHEST);
    }

    public void setHasChest(boolean hasChest) {
        this.dataTracker.set(CHEST, hasChest);
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
            if (!itemStack.isEmpty() && this.isTrollArmor(itemStack)) {
                this.items.setStack(1, itemStack);
            }
        }

        if (nbt.contains("SaddleItem", 10)) {
            ItemStack itemStack = ItemStack.fromNbt(nbt.getCompound("SaddleItem"));
            if (itemStack.isOf(ModEquipmentItems.BEAST_CHAINS)) {
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
            if (this.isTrollArmor(stack)) {
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


    public boolean isTrollArmor(ItemStack item) {
        return item.getItem() instanceof TrollArmorItem;
    }

    @Override
    public void onInventoryChanged(Inventory sender) {
        ItemStack itemStack = this.getArmorType();
        super.onInventoryChanged(sender);
        ItemStack itemStack2 = this.getArmorType();
        if (this.age > 20 && this.isTrollArmor(itemStack2) && itemStack != itemStack2) {
            this.playSound(SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0.5F, 1.0F);
        }
    }

    @Override
    public StackReference getStackReference(int mappedIndex) {
        return mappedIndex == 499 ? new StackReference() {
            public ItemStack get() {
                return TrollEntity.this.hasChest() ? new ItemStack(Items.CHEST) : ItemStack.EMPTY;
            }

            public boolean set(ItemStack stack) {
                if (stack.isEmpty()) {
                    if (TrollEntity.this.hasChest()) {
                        TrollEntity.this.setHasChest(false);
                        TrollEntity.this.onChestedStatusChanged();
                    }

                    return true;
                } else if (stack.isOf(Items.CHEST)) {
                    if (!TrollEntity.this.hasChest()) {
                        TrollEntity.this.setHasChest(true);
                        TrollEntity.this.onChestedStatusChanged();
                    }

                    return true;
                } else {
                    return false;
                }
            }
        } : super.getStackReference(mappedIndex);
    }


    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        boolean bl = !this.isBaby() && this.isTame() && player.shouldCancelInteraction();
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
            }

            return super.interactMob(player, hand);
        } else {
            return super.interactMob(player, hand);
        }
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
