package net.sevenstars.middleearth.entity.projectile.spear;

import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.sevenstars.middleearth.entity.EntitiesME;
import net.sevenstars.middleearth.item.WeaponItemsME;

public class SpearEntity extends AbstractArrow {
    private float damage;
    private static final EntityDataAccessor<ItemStack> ITEM_STACK_DATA = SynchedEntityData.defineId(SpearEntity.class, EntityDataSerializers.ITEM_STACK);

    public SpearEntity(EntityType<? extends SpearEntity> entityType, Level world) {
        super(entityType, world);
    }

    public SpearEntity(Level world, ItemStack itemStack, LivingEntity owner, float dmg) {
        super(EntitiesME.SPEAR, owner, world, itemStack, null);
        entityData.set(ITEM_STACK_DATA, itemStack);
        this.damage = dmg;
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(ITEM_STACK_DATA, getDefaultPickupItem());
    }

    public ItemStack getTrackedItemStackData() {
        return this.entityData.get(ITEM_STACK_DATA);
    }

    protected SoundEvent getDefaultHitGroundSoundEvent() {
        return SoundEvents.TRIDENT_HIT;
    }

    @Override
    protected boolean tryPickup(Player player) {
        return super.tryPickup(player) || this.isNoPhysics() && this.ownedBy(player) && player.getInventory().add(this.getPickupItem());
    }

    @Override
    protected void onHitEntity(EntityHitResult entityHitResult) {
        ServerLevel serverWorld;
        Entity entity = entityHitResult.getEntity();
        float f = 8.0f;
        Entity entity2 = this.getOwner();
        DamageSource damageSource = this.damageSources().trident(this, entity2 == null ? this : entity2);
        Level world = this.level();

        if (entity.hurt(damageSource, f)) {
            if (entity.getType() == EntityType.ENDERMAN) {
                return;
            }
            if (world instanceof ServerLevel) {
                serverWorld = (ServerLevel)world;
                EnchantmentHelper.doPostAttackEffectsWithItemSource(serverWorld, entity, damageSource, this.getWeaponItem());
            }
            if (entity instanceof LivingEntity livingEntity) {
                this.doKnockback(livingEntity, damageSource);
                this.doPostHurtEffects(livingEntity);
            }
        }
        this.setDeltaMovement(this.getDeltaMovement().multiply(-0.01, -0.1, -0.01));
        this.playSound(SoundEvents.TRIDENT_HIT, 1.0f, 1.0f);
    }

    protected void doKnockback(LivingEntity target, DamageSource source) {
        float f;
        Level world;
        if (this.getPickupItemStackOrigin() != null && (world = this.level()) instanceof ServerLevel) {
            ServerLevel serverWorld = (ServerLevel)world;
            f = EnchantmentHelper.modifyKnockback(serverWorld, this.getPickupItemStackOrigin(), target, source, 0.0f);
        } else {
            f = 0.0f;
        }
        double d = f;
        double e = Math.max(0.0, 1.0 - target.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE));
        Vec3 velocity = this.getDeltaMovement();
        velocity = velocity.multiply(1.0, 0.0, 1.0);
        velocity = velocity.normalize();
        Vec3 vec3d = velocity.scale(1.45 * e + d * 1.5 * e);
        if (vec3d.lengthSqr() > 0.0) {
            target.push(vec3d.x, 0.15, vec3d.z);
        }

    }

    @Override
    protected ItemStack getDefaultPickupItem() {
        return WeaponItemsME.WOODEN_SPEAR.getDefaultInstance();
    }

    protected float getWaterInertia() {
        return 0.8f;
    }

    public boolean shouldRender(double cameraX, double cameraY, double cameraZ) {
        return true;
    }
}
