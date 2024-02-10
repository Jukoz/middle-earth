package net.jukoz.me.entity.beasts.trolls;

import net.jukoz.me.MiddleEarth;
import net.jukoz.me.entity.ModEntities;
import net.jukoz.me.entity.beasts.BeastEntity;
import net.jukoz.me.entity.dwarves.durin.DurinDwarfEntity;
import net.jukoz.me.entity.elves.galadhrim.GaladhrimElfEntity;
import net.jukoz.me.entity.goals.*;
import net.jukoz.me.entity.hobbits.shire.ShireHobbitEntity;
import net.jukoz.me.entity.projectile.boulder.BoulderEntity;
import net.jukoz.me.item.ModFoodItems;
import net.jukoz.me.item.items.TrollArmorItem;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AbstractDonkeyEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.*;
import net.minecraft.world.World;

// TODO Fix laws of gravity
// TODO Have slowly accelerate for charge
// TODO Update Model and Animations
public class TrollEntity extends BeastEntity {
    private int throwCooldown = 100;
    public final AnimationState throwingAnimationState = new AnimationState();
    private int throwingAnimationTimeout = 0;
    public static final TrackedData<Boolean> THROWING = DataTracker.registerData(TrollEntity.class, TrackedDataHandlerRegistry.BOOLEAN);


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
        this.goalSelector.add(2, new BeastSitGoal(this));
        this.goalSelector.add(3, new ChargeAttackGoal(this, 400));
        this.goalSelector.add(3, new MeleeAttackGoal(this, 0.9f, false));
        this.goalSelector.add(4, new BeastFollowOwnerGoal(this, 1.0, 10.0f, 2.0f, false));
        this.goalSelector.add(5, new WanderAroundFarGoal(this, 1.0));
        this.goalSelector.add(6, new LookAtEntityGoal(this, PlayerEntity.class, 6.0f));
        this.goalSelector.add(7, new LookAroundGoal(this));
        this.targetSelector.add(1, new BeastTrackOwnerAttackerGoal((BeastEntity) this));
        this.targetSelector.add(2, new BeastAttackWithOwnerGoal((BeastEntity)this));
        this.targetSelector.add(3, new TargetPlayerGoal(this));
        this.targetSelector.add(4, new ActiveTargetGoal<>(this, GaladhrimElfEntity.class, true));
        this.targetSelector.add(5, new ActiveTargetGoal<>(this, DurinDwarfEntity.class, true));
        this.targetSelector.add(6, new ActiveTargetGoal<>(this, ShireHobbitEntity.class, true));
    }

    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(THROWING, false);
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
        super.setupAnimationStates();

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
            if(this.squaredDistanceTo(this.getTarget()) >= 25 && !this.hasPassengers()) {
                this.setThrowing(true);
                throwCooldown = 200;
            }
        }
        if(this.isThrowing()) {
            this.setVelocity(Vec3d.ZERO);
            if(!this.isOnGround()) {
                this.setThrowing(false);
            }
            if(throwCooldown <= 180) {
                throwAttack();
            }
        }
        if(throwCooldown > 0) {
            --this.throwCooldown;
        }

        if (this.getWorld().isClient) {
            setupAnimationStates();
        }
    }

    public boolean isCommandItem(ItemStack stack) {
        return stack.isIn(TagKey.of(RegistryKeys.ITEM, new Identifier(MiddleEarth.MOD_ID, "bones")));
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

    public void setThrowing(boolean throwing) {
        this.dataTracker.set(THROWING, throwing);
    }

    public boolean isThrowing() {
        return this.dataTracker.get(THROWING);
    }

    @Override
    public Item getBondingItem() {
        return ModFoodItems.COOKED_HORSE;
    }

    @Override
    public boolean isHorseArmor(ItemStack item) {
        return item.getItem() instanceof TrollArmorItem;
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
            if(boulder != null) {
                this.getWorld().spawnEntity(boulder);
            }
        }
    }
}