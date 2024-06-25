package net.jukoz.me.entity.beasts.warg;

import net.jukoz.me.MiddleEarth;
import net.jukoz.me.entity.beasts.BeastEntity;
import net.jukoz.me.entity.dwarves.longbeards.LongbeardDwarfEntity;
import net.jukoz.me.entity.elves.galadhrim.GaladhrimElfEntity;
import net.jukoz.me.entity.goals.*;
import net.jukoz.me.entity.hobbits.shire.ShireHobbitEntity;
import net.jukoz.me.entity.humans.bandit.BanditHumanEntity;
import net.jukoz.me.entity.humans.gondor.GondorHumanEntity;
import net.jukoz.me.entity.humans.rohan.RohanHumanEntity;
import net.jukoz.me.item.ModEquipmentItems;
import net.jukoz.me.item.ModFoodItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AbstractDonkeyEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.List;

public class WargEntity extends BeastEntity {
    private boolean hasCharged = false;
    public WargEntity(EntityType<? extends AbstractDonkeyEntity> entityType, World world) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder setAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.4f)
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 30.0)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 0.2)
                .add(EntityAttributes.GENERIC_ATTACK_SPEED, 1.0f)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 38.0f)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 7.0f)
                .add(EntityAttributes.GENERIC_JUMP_STRENGTH, 1.0f);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(1, new SwimGoal(this));
        this.goalSelector.add(2, new BeastSitGoal(this));
        this.goalSelector.add(4, new MeleeAttackGoal(this, 1.1f, false));
        this.goalSelector.add(5, new ChargeAttackGoal(this, maxChargeCooldown()));
        this.goalSelector.add(6, new BeastFollowOwnerGoal(this, 1.0, 10.0f, 2.0f, false));
        this.goalSelector.add(7, new WanderAroundFarGoal(this, 1.0));
        this.goalSelector.add(8, new LookAtEntityGoal(this, PlayerEntity.class, 6.0f));
        this.goalSelector.add(9, new LookAroundGoal(this));
        this.targetSelector.add(1, new BeastTrackOwnerAttackerGoal((BeastEntity) this));
        this.targetSelector.add(2, new BeastAttackWithOwnerGoal((BeastEntity)this));
        this.targetSelector.add(3, new RevengeGoal(this, new Class[0]));
        this.targetSelector.add(4, new TargetPlayerGoal(this));
        this.targetSelector.add(4, new ActiveTargetGoal<>(this, GaladhrimElfEntity.class, true));
        this.targetSelector.add(5, new ActiveTargetGoal<>(this, LongbeardDwarfEntity.class, true));
        this.targetSelector.add(6, new ActiveTargetGoal<>(this, GondorHumanEntity.class, true));
        this.targetSelector.add(7, new ActiveTargetGoal<>(this, RohanHumanEntity.class, true));
        this.targetSelector.add(8, new ActiveTargetGoal<>(this, BanditHumanEntity.class, true));
        this.targetSelector.add(9, new ActiveTargetGoal<>(this, ShireHobbitEntity.class, true));
    }

    @Override
    public void tick() {
        super.tick();

        if(this.isCharging()) {
            if(!chargeAnimationState.isRunning()) {
                this.chargeAnimationState.start(this.age);
            }

            List<Entity> entities = this.getWorld().getOtherEntities(this, this.getBoundingBox().expand(0.2f, 0.0, 0.2f));

            for(Entity entity : entities) {
                if(entity.getUuid() != this.getOwnerUuid() && entity != this && !this.getPassengerList().contains(entity)) {
                    entity.damage(entity.getDamageSources().mobAttack(this), 10.0f);
                    if(entity.hasVehicle()) {
                        entity.dismountVehicle();
                    }

                    this.setCharging(false);
                    this.setHasCharged(false);
                }
            }

            if(this.chargeTimeout <= maxChargeCooldown() - 10 && !this.isInAir()) {
                this.setCharging(false);
                this.setHasCharged(false);
            }
        }
    }


    public boolean isCommandItem(ItemStack stack) {
        return stack.isIn(TagKey.of(RegistryKeys.ITEM, new Identifier(MiddleEarth.MOD_ID, "bones")));
    }

    @Override
    public void chargeAttack() {
        if(!this.hasCharged()) {
            this.setHasCharged(true);
            if(!this.isTame() && !this.getWorld().isClient) {
                if(targetDir == Vec3d.ZERO && this.getTarget() != null) {
                    targetDir = new Vec3d( this.getTarget().getBlockPos().getX() - this.getBlockPos().getX(),
                            this.getTarget().getBlockPos().getY() - this.getBlockPos().getY(),
                            this.getTarget().getBlockPos().getZ() - this.getBlockPos().getZ());
                }
                this.setYaw((float) Math.toDegrees(Math.atan2(-targetDir.x, targetDir.z)));
                this.setVelocity(targetDir.multiply(1,0,1).normalize().add(0,0.6,0).multiply(0.7f));
            }
            else if (this.getWorld().isClient) {
                this.setHasCharged(true);
                this.setVelocity(this.getRotationVector().multiply(1,0,1).normalize().add(0,0.35,0).multiply(1.3f));
            }
        }
    }

    @Override
    public boolean hasArmorSlot() {
        return true;
    }

    @Override
    public boolean isHorseArmor(ItemStack stack) {
        return stack.isOf(ModEquipmentItems.WARG_ARMOR);
    }

    public boolean hasCharged() {
        return hasCharged;
    }

    public void setHasCharged(boolean hasCharged) {
        this.hasCharged = hasCharged;
    }

    @Override
    public Item getBondingItem() {
        return Items.MUTTON;
    }

    @Override
    public int chargeDuration() {
        return 50;
    }

    @Override
    public int maxChargeCooldown() {
        return 50;
    }
}
