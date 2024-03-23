package net.jukoz.me.entity.goose;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Dynamic;
import net.jukoz.me.entity.ModEntities;
import net.jukoz.me.entity.goals.BirdFlightGoal;
import net.minecraft.block.BlockState;
import net.minecraft.block.LeavesBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.ai.FuzzyTargeting;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.entity.ai.brain.sensor.SensorType;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.ai.pathing.BirdNavigation;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.mob.PiglinActivity;
import net.minecraft.entity.mob.PiglinBrain;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;

public class GooseEntity extends AnimalEntity {

    public static final Ingredient BREEDING_INGREDIENT
            = Ingredient.ofItems(Items.GRASS, Items.WHEAT_SEEDS);
    static final Predicate<ItemEntity> PICKABLE_DROP_FILTER;
    public float flapProgress;
    public float maxWingDeviation;
    public float prevMaxWingDeviation;
    public float prevFlapProgress;
    private float flapSpeed = 1.0F;
    private float field_28640 = 1.0F;

    //protected static final ImmutableList<SensorType<? extends Sensor<? super GooseEntity>>> SENSORS;
    //protected static final ImmutableList MEMORY_MODULES;

    public GooseEntity(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
        this.setCanPickUpLoot(true);
    }
/*
    protected Brain.Profile<GooseEntity> createBrainProfile() {
        return Brain.createProfile(MEMORY_MODULES, SENSORS);
    }

    protected Brain<?> deserializeBrain(Dynamic<?> dynamic) {
        return GooseBrain.create(this, this.createBrainProfile().deserialize(dynamic));
    }

    public Brain<GooseEntity> getBrain() {
        return (Brain<GooseEntity>) super.getBrain();
    }

    protected void mobTick() {
        this.getWorld().getProfiler().push("gooseBrain");
        this.getBrain().tick((ServerWorld)this.getWorld(), this);
        this.getWorld().getProfiler().pop();
        this.getWorld().getProfiler().push("gooseActivityUpdate");
        GooseBrain.updateActivities(this);
        this.getWorld().getProfiler().pop();
        super.mobTick();
    }*/

    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));

        this.targetSelector.add(1, new RevengeGoal(this, new Class[0]));

        this.goalSelector.add(2, new MeleeAttackGoal(this, 0.9f, false));
        this.goalSelector.add(3, new EscapeDangerGoal(this, 1.15));
        this.goalSelector.add(4, new AnimalMateGoal(this, 1.0));
        this.goalSelector.add(5, new TemptGoal(this, 1.1, BREEDING_INGREDIENT, false));

        this.goalSelector.add(6, new FollowParentGoal(this, 1.05));
        this.goalSelector.add(7, new GooseEntity.PickupItemGoal());

        this.goalSelector.add(8, new LookAtEntityGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.add(9, new LookAroundGoal(this));
        this.goalSelector.add(10, new WanderAroundGoal(this, 1.0F));

        this.goalSelector.add(12, new FleeEntityGoal<>(this, WolfEntity.class, 8.0F, 0.9, 1.2));

        //this.goalSelector.add(13, new BirdFlightGoal(this));
    }


    public static DefaultAttributeContainer.Builder createGooseAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 8.0)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.4)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 0.75)
                .add(EntityAttributes.GENERIC_ATTACK_SPEED, 1.0)
                .add(EntityAttributes.GENERIC_FLYING_SPEED, 0.4000000059604645);
    }

    public void handleStatus(byte status) {
        if (status == 45) {
            ItemStack itemStack = this.getEquippedStack(EquipmentSlot.MAINHAND);
            if (!itemStack.isEmpty()) {
                for(int i = 0; i < 8; ++i) {
                    Vec3d vec3d = (new Vec3d(((double)this.random.nextFloat() - 0.5) * 0.1, Math.random() * 0.1 + 0.1, 0.0)).rotateX(-this.getPitch() * 0.017453292F).rotateY(-this.getYaw() * 0.017453292F);
                    this.getWorld().addParticle(new ItemStackParticleEffect(ParticleTypes.ITEM, itemStack), this.getX() + this.getRotationVector().x / 2.0, this.getY(), this.getZ() + this.getRotationVector().z / 2.0, vec3d.x, vec3d.y + 0.05, vec3d.z);
                }
            }
        } else {
            super.handleStatus(status);
        }

    }

    public boolean canEquip(ItemStack stack) {
        EquipmentSlot equipmentSlot = MobEntity.getPreferredEquipmentSlot(stack);
        if (!this.getEquippedStack(equipmentSlot).isEmpty()) {
            return false;
        } else {
            return equipmentSlot == EquipmentSlot.MAINHAND && super.canEquip(stack);
        }
    }

    public boolean canPickupItem(ItemStack stack) {
        if(!this.isBaby()) {
            Item item = stack.getItem();
            ItemStack itemStack = this.getEquippedStack(EquipmentSlot.MAINHAND);
            return itemStack.isEmpty();
        }
        return false;
    }

    private void dropItem(ItemStack stack) {
        ItemEntity itemEntity = new ItemEntity(this.getWorld(), this.getX(), this.getY(), this.getZ(), stack);
        this.getWorld().spawnEntity(itemEntity);
    }

    protected void loot(ItemEntity item) {
        ItemStack itemStack = item.getStack();
        if (this.canPickupItem(itemStack)) {
            int i = itemStack.getCount();
            if (i > 1) {
                this.dropItem(itemStack.split(i - 1));
            }

            this.triggerItemPickedUpByEntityCriteria(item);
            this.equipStack(EquipmentSlot.MAINHAND, itemStack.split(1));
            this.updateDropChances(EquipmentSlot.MAINHAND);
            this.sendPickup(item, itemStack.getCount());
            item.discard();
        }

    }

    protected void drop(DamageSource source) {
        ItemStack itemStack = this.getEquippedStack(EquipmentSlot.MAINHAND);
        if (!itemStack.isEmpty()) {
            this.dropStack(itemStack);
            this.equipStack(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
        }

        super.drop(source);
    }

    protected EntityNavigation createNavigation(World world) {
        BirdNavigation birdNavigation = new BirdNavigation(this, world);
        birdNavigation.setCanPathThroughDoors(true);
        birdNavigation.setCanSwim(true);
        birdNavigation.setCanEnterOpenDoors(true);
        return birdNavigation;
    }

    @Override
    public void tickMovement() {
        super.tickMovement();
        this.flapWings();
    }

    private void flapWings() {
        this.prevFlapProgress = this.flapProgress;
        this.prevMaxWingDeviation = this.maxWingDeviation;
        this.maxWingDeviation += (float)(!this.isOnGround() && !this.hasVehicle() ? 4 : -1) * 0.3F;
        this.maxWingDeviation = MathHelper.clamp(this.maxWingDeviation, 0.0F, 1.0F);
        if (!this.isOnGround() && this.flapSpeed < 1.0F) {
            this.flapSpeed = 1.0F;
        }

        this.flapSpeed *= 0.9F;
        Vec3d vec3d = this.getVelocity();
        if (!this.isOnGround() && vec3d.y < 0.0) {
            this.setVelocity(vec3d.multiply(1.0, 0.6, 1.0));
        }

        this.flapProgress += this.flapSpeed * 2.0F;
    }

    protected boolean isFlappingWings() {
        return this.speed > this.field_28640;
    }

    protected void addFlapEffects() {
        this.playSound(SoundEvents.ENTITY_PARROT_FLY, 0.15F, 1.0F);
        this.field_28640 = this.speed + this.maxWingDeviation / 2.0F;
    }

    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_CHICKEN_AMBIENT;
    }

    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_CHICKEN_HURT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_CHICKEN_DEATH;
    }
    protected SoundEvent getSwimSound() {
        return SoundEvents.ENTITY_TURTLE_SWIM;
    }

    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(SoundEvents.ENTITY_CHICKEN_STEP, 0.15F, 2.0F);
    }

    @Nullable
    public GooseEntity createChild(ServerWorld serverWorld, PassiveEntity passiveEntity) {
        return (GooseEntity) ModEntities.GOOSE.create(serverWorld);
    }

    public boolean isBreedingItem(ItemStack stack) {
        return BREEDING_INGREDIENT.test(stack);
    }

    public GooseVariant getVariant() {
        return GooseVariant.byId(this.getId());
    }


    public boolean canBreatheInWater() {
        return false;
    }

    public boolean isPushedByFluids() {
        return true;
    }

    @Override
    public boolean handleFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource) {
        return false;
    }

    class PickupItemGoal extends Goal {
        public PickupItemGoal() {
            this.setControls(EnumSet.of(Control.MOVE));
        }

        public boolean canStart() {
            if (!GooseEntity.this.getEquippedStack(EquipmentSlot.MAINHAND).isEmpty()) {
                return false;
            } else if (GooseEntity.this.getTarget() == null && GooseEntity.this.getAttacker() == null) {
                if (GooseEntity.this.getRandom().nextInt(toGoalTicks(10)) != 0) {
                    return false;
                } else {
                    List<ItemEntity> list = GooseEntity.this.getWorld().getEntitiesByClass(ItemEntity.class, GooseEntity.this.getBoundingBox().expand(8.0, 8.0, 8.0), GooseEntity.PICKABLE_DROP_FILTER);
                    return !list.isEmpty() && GooseEntity.this.getEquippedStack(EquipmentSlot.MAINHAND).isEmpty();
                }
            } else {
                return false;
            }
        }

        public void tick() {
            List<ItemEntity> list = GooseEntity.this.getWorld().getEntitiesByClass(ItemEntity.class, GooseEntity.this.getBoundingBox().expand(8.0, 8.0, 8.0), GooseEntity.PICKABLE_DROP_FILTER);
            ItemStack itemStack = GooseEntity.this.getEquippedStack(EquipmentSlot.MAINHAND);
            if (itemStack.isEmpty() && !list.isEmpty()) {
                GooseEntity.this.getNavigation().startMovingTo((Entity)list.get(0), 1.2000000476837158);
            }

        }

        public void start() {
            List<ItemEntity> list = GooseEntity.this.getWorld().getEntitiesByClass(ItemEntity.class, GooseEntity.this.getBoundingBox().expand(8.0, 8.0, 8.0), GooseEntity.PICKABLE_DROP_FILTER);
            if (!list.isEmpty()) {
                GooseEntity.this.getNavigation().startMovingTo((Entity)list.get(0), 1.2000000476837158);
            }

        }
    }

    static {
        PICKABLE_DROP_FILTER = (item) -> {
            return !item.cannotPickup() && item.isAlive();
        };
    /*
        SENSORS = ImmutableList.of(SensorType.NEAREST_LIVING_ENTITIES,
                SensorType.HURT_BY,
                SensorType.IS_IN_WATER);

        MEMORY_MODULES = ImmutableList.of(MemoryModuleType.LOOK_TARGET,
                MemoryModuleType.MOBS,
                MemoryModuleType.VISIBLE_MOBS,
                MemoryModuleType.WALK_TARGET,
                MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE,
                MemoryModuleType.PATH,
                MemoryModuleType.BREED_TARGET,
                MemoryModuleType.ATTACK_TARGET,
                MemoryModuleType.TEMPTING_PLAYER,
                MemoryModuleType.TEMPTATION_COOLDOWN_TICKS,
                MemoryModuleType.IS_TEMPTED,
                MemoryModuleType.HURT_BY,
                MemoryModuleType.HURT_BY_ENTITY,
                MemoryModuleType.NEAREST_ATTACKABLE,
                MemoryModuleType.IS_IN_WATER,
                MemoryModuleType.IS_PANICKING,
                MemoryModuleType.UNREACHABLE_TONGUE_TARGETS);
        */
    }
}
