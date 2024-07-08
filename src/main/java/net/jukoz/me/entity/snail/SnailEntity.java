package net.jukoz.me.entity.snail;

import net.jukoz.me.MiddleEarth;
import net.jukoz.me.entity.ModEntities;
import net.minecraft.block.*;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.MoveToTargetPosGoal;
import net.minecraft.entity.ai.goal.WanderAroundFarGoal;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.ai.pathing.SpiderNavigation;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

public class SnailEntity extends AnimalEntity {

    public static final int CLIMBING_TIME_TRANSITION = 12;
    private static final TrackedData<Byte> SNAIL_FLAGS;
    private int climbingTicks = 0;
    int moreCropsTicks;

    public SnailEntity(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new SnailEatCropGoal(this));
        this.goalSelector.add(1, new WanderAroundFarGoal(this, 0.8));
    }

    public static DefaultAttributeContainer.Builder createSnailAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 2)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.05f)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1)
                .add(EntityAttributes.GENERIC_ARMOR, 0.5f);
    }

    public SnailVariant getVariant() {
        return SnailVariant.byId(this.getId());
    }
    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return ModEntities.SNAIL.create(world);
    }


    static class SnailEatCropGoal
            extends MoveToTargetPosGoal {
        private final SnailEntity snail;
        private boolean wantsCrops;
        private boolean hasTarget;

        public SnailEatCropGoal(SnailEntity snail) {
            super(snail, 1.0f, 16);
            this.snail = snail;
        }

        @Override
        public boolean canStart() {
            if (this.cooldown <= 0) {
                if (!this.snail.getWorld().getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING)) {
                    return false;
                }
                this.hasTarget = false;
                this.wantsCrops = this.snail.wantsCrops();
            }
            return super.canStart();
        }

        @Override
        public boolean shouldContinue() {
            return this.hasTarget && super.shouldContinue();
        }

        @Override
        public void tick() {
            super.tick();
            this.snail.getLookControl().lookAt((double)this.targetPos.getX() + 0.5, this.targetPos.getY() + 1, (double)this.targetPos.getZ() + 0.5, 10.0f, this.snail.getMaxLookPitchChange());
            if (this.hasReached()) {
                World world = this.snail.getWorld();
                BlockPos blockPos = this.targetPos.up();
                BlockState blockState = world.getBlockState(blockPos);
                Block block = blockState.getBlock();
                if (this.hasTarget && block.getDefaultState().isIn(BlockTags.CROPS)) {
                    world.setBlockState(blockPos, Blocks.AIR.getDefaultState(), Block.NOTIFY_LISTENERS);
                    world.breakBlock(blockPos, true, this.snail);
                    this.snail.onEatingGrass();
                    this.snail.moreCropsTicks = 1200;
                }
                this.hasTarget = false;
                this.cooldown = 10;
            }
        }

        @Override
        protected boolean isTargetPos(WorldView world, BlockPos pos) {
            BlockState blockState = world.getBlockState(pos);
            if (blockState.isOf(Blocks.FARMLAND) && this.wantsCrops && !this.hasTarget && (blockState = world.getBlockState(pos.up())).isIn(BlockTags.CROPS)) {
                this.hasTarget = true;
                return true;
            }

            return false;
        }
    }

    boolean wantsCrops() {
        return this.moreCropsTicks <= 0;
    }

    @Override
    protected void mobTick() {
        if (this.moreCropsTicks > 0) {
            this.moreCropsTicks -= this.random.nextInt(3);
            if (this.moreCropsTicks < 0) {
                this.moreCropsTicks = 0;
            }
        }
        super.mobTick();
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_SLIME_DEATH;
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_SLIME_HURT;
    }

    @Override
    public void jump() {
        // Snail is not able to jump
    }

    protected EntityNavigation createNavigation(World world) {
        return new SpiderNavigation(this, world);
    }

    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);
        builder.add(SNAIL_FLAGS, (byte)0);
    }

    @Override
    public void onEatingGrass() {
        super.onEatingGrass();
        if (this.isBaby()) {
            this.growUp(60);
        }
        if(random.nextDouble() <= 0.15D) {
            World world = this.getWorld();
            SnailEntity snailSpawn = ((EntityType<SnailEntity>) EntityType.get(MiddleEarth.MOD_ID + ":snail").get()).create(world);
            snailSpawn.updatePosition(this.getX(), this.getY(), this.getZ());
            world.spawnEntity(snailSpawn);
        }
    }

    public void tick() {
        super.tick();
        if (!this.getWorld().isClient) {
            this.setClimbingWall(this.horizontalCollision);
        }
        if(isClimbing()) {
            this.setVelocity(0, 0.01, 0);
        }
    }

    @Override
    public void tickMovement() {
        super.tickMovement();
        if(isClimbingWall()) {

            this.climbingTicks = Math.min(CLIMBING_TIME_TRANSITION, this.climbingTicks + 1);
        } else {
            this.climbingTicks = Math.max(0, this.climbingTicks - 1);
        }
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return false;
    }

    public boolean isClimbing() {
        return this.isClimbingWall();
    }

    public boolean isClimbingWall() {
        return (this.dataTracker.get(SNAIL_FLAGS) & 1) != 0;
    }

    public boolean isCollidingWall() {
        return this.horizontalCollision;
    }

    public void setClimbingWall(boolean climbing) {
        byte b = (Byte)this.dataTracker.get(SNAIL_FLAGS);
        if (climbing) {
            b = (byte)(b | 1);
        } else {
            b &= -2;
        }

        this.dataTracker.set(SNAIL_FLAGS, b);
    }

    public int getClimbingTicks() {
        return this.climbingTicks;
    }

    static {
        SNAIL_FLAGS = DataTracker.registerData(SnailEntity.class, TrackedDataHandlerRegistry.BYTE);
    }
}
