package net.jesteur.me.entity.crab;

import net.jesteur.me.entity.ModEntities;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.control.MoveControl;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.ai.pathing.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.*;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class CrabEntity extends AnimalEntity {

    private static final Ingredient BREEDING_INGREDIENT
            = Ingredient.ofItems(new ItemConvertible[]{Items.SEA_PICKLE, Items.SALMON, Items.COD });

    public CrabEntity(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
        this.moveControl = new CrabMoveControl(this);

    }

    protected void initGoals() {
        this.goalSelector.add(1, new EscapeDangerGoal(this, 1.25));
        this.goalSelector.add(2, new AnimalMateGoal(this, 1.0));
        this.goalSelector.add(3, new TemptGoal(this, 1.2, BREEDING_INGREDIENT, false));
        this.goalSelector.add(4, new FollowParentGoal(this, 1.1));

        //this.goalSelector.add(6, new LookAtEntityGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.add(7, new LookAroundGoal(this));
        this.goalSelector.add(9, new WanderAroundGoal(this, 1.0F));
        this.goalSelector.add(10, new FleeEntityGoal(this, DolphinEntity.class, 8.0F, 1.0, 1.4));
    }

    public static DefaultAttributeContainer.Builder createCrabAttributes() {
        return MobEntity.createMobAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 10.0).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.25);
    }

    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_SALMON_AMBIENT;
    }

    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_SALMON_HURT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_SALMON_DEATH;
    }
    protected SoundEvent getSwimSound() {
        return SoundEvents.ENTITY_TURTLE_SWIM;
    }

    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(SoundEvents.ENTITY_SPIDER_STEP, 0.15F, 2.0F);
    }

    @Nullable
    public CrabEntity createChild(ServerWorld serverWorld, PassiveEntity passiveEntity) {
        return (CrabEntity) ModEntities.CRAB.create(serverWorld);
    }

    public boolean isBreedingItem(ItemStack stack) {
        return BREEDING_INGREDIENT.test(stack);
    }

    public CrabVariant getVariant() {
        return CrabVariant.byId(this.getId());
    }


    public boolean canBreatheInWater() {
        return true;
    }

    public boolean isPushedByFluids() {
        return false;
    }



    static class CrabMoveControl extends MoveControl {
        private final CrabEntity crab;

        CrabMoveControl(CrabEntity crab) {
            super(crab);
            this.crab = crab;
        }

        private void updateVelocity() {
            if (this.crab.isTouchingWater()) {
                this.crab.setVelocity(this.crab.getVelocity().add(0.0, 0.005, 0.0));

                if (this.crab.isBaby()) {
                    this.crab.setMovementSpeed(Math.max(this.crab.getMovementSpeed() / 3.0F, 0.06F));
                }
            } else if (this.crab.isOnGround()) {
                this.crab.setMovementSpeed(Math.max(this.crab.getMovementSpeed() / 2.0F, 0.2F));
            }

        }

        public void tick() {
            this.updateVelocity();
            if (this.state == State.MOVE_TO && !this.crab.getNavigation().isIdle()) {
                double d = this.targetX - this.crab.getX();
                double e = this.targetY - this.crab.getY();
                double f = this.targetZ - this.crab.getZ();
                double g = Math.sqrt(d * d + e * e + f * f);
                if (g < 9.999999747378752E-6) {
                    this.entity.setMovementSpeed(0.0F);
                } else {
                    e /= g;
                    float h = (float)(MathHelper.atan2(f, d) * 57.2957763671875) - 90.0F;
                    this.crab.setYaw(this.wrapDegrees(this.crab.getYaw(), h, 90.0F));
                    this.crab.bodyYaw = this.crab.getYaw();
                    float i = (float)(this.speed * this.crab.getAttributeValue(EntityAttributes.GENERIC_MOVEMENT_SPEED));
                    this.crab.setMovementSpeed(MathHelper.lerp(0.125F, this.crab.getMovementSpeed(), i));
                    this.crab.setVelocity(this.crab.getVelocity().add(0.0, (double)this.crab.getMovementSpeed() * e * 0.1, 0.0));
                }
            } else {
                this.crab.setMovementSpeed(0.0F);
            }
        }
    }


    protected EntityNavigation createNavigation(World world) {
        return new CrabSwimNavigation(this, world);
    }

    static class CrabSwimNavigation extends AmphibiousSwimNavigation {
        CrabSwimNavigation(CrabEntity owner, World world) {
            super(owner, world);
        }

        public boolean isValidPosition(BlockPos pos) {
            MobEntity var3 = this.entity;
            if (var3 instanceof TurtleEntity turtleEntity) {
                return this.world.getBlockState(pos).isOf(Blocks.WATER);
            }

            return !this.world.getBlockState(pos.down()).isAir();
        }
    }

}
