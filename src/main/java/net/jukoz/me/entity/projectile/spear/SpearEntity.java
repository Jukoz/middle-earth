package net.jukoz.me.entity.projectile.spear;

import net.jukoz.me.entity.ModEntities;
import net.jukoz.me.item.ModWeaponItems;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class SpearEntity extends PersistentProjectileEntity {
    private float damage;
    private static final TrackedData<ItemStack> ITEM_STACK_DATA = DataTracker.registerData(SpearEntity.class, TrackedDataHandlerRegistry.ITEM_STACK);

    public SpearEntity(EntityType<? extends SpearEntity> entityType, World world) {
        super(entityType, world);
    }

    public SpearEntity(World world, ItemStack itemStack, LivingEntity owner, float dmg) {
        super(ModEntities.SPEAR, owner, world, itemStack, null);
        dataTracker.set(ITEM_STACK_DATA, itemStack);
        this.damage = dmg;
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);
        builder.add(ITEM_STACK_DATA, getDefaultItemStack());
    }

    public ItemStack getTrackedItemStackData() {
        return this.dataTracker.get(ITEM_STACK_DATA);
    }

    protected SoundEvent getHitSound() {
        return SoundEvents.ITEM_TRIDENT_HIT;
    }

    @Override
    protected boolean tryPickup(PlayerEntity player) {
        return super.tryPickup(player) || this.isNoClip() && this.isOwner(player) && player.getInventory().insertStack(this.asItemStack());
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        ServerWorld serverWorld;
        Entity entity = entityHitResult.getEntity();
        float f = 8.0f;
        Entity entity2 = this.getOwner();
        DamageSource damageSource = this.getDamageSources().trident(this, entity2 == null ? this : entity2);
        World world = this.getWorld();

        if (entity.damage(damageSource, f)) {
            if (entity.getType() == EntityType.ENDERMAN) {
                return;
            }
            if (world instanceof ServerWorld) {
                serverWorld = (ServerWorld)world;
                EnchantmentHelper.onTargetDamaged(serverWorld, entity, damageSource, this.getWeaponStack());
            }
            if (entity instanceof LivingEntity livingEntity) {
                this.knockback(livingEntity, damageSource);
                this.onHit(livingEntity);
            }
        }
        this.setVelocity(this.getVelocity().multiply(-0.01, -0.1, -0.01));
        this.playSound(SoundEvents.ITEM_TRIDENT_HIT, 1.0f, 1.0f);
    }

    protected void knockback(LivingEntity target, DamageSource source) {
        float f;
        World world;
        if (this.getItemStack() != null && (world = this.getWorld()) instanceof ServerWorld) {
            ServerWorld serverWorld = (ServerWorld)world;
            f = EnchantmentHelper.modifyKnockback(serverWorld, this.getItemStack(), target, source, 0.0f);
        } else {
            f = 0.0f;
        }
        double d = f;
        double e = Math.max(0.0, 1.0 - target.getAttributeValue(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE));
        Vec3d velocity = this.getVelocity();
        velocity = velocity.multiply(1.0, 0.0, 1.0);
        velocity = velocity.normalize();
        Vec3d vec3d = velocity.multiply(1.45 * e + d * 1.5 * e);
        if (vec3d.lengthSquared() > 0.0) {
            target.addVelocity(vec3d.x, 0.15, vec3d.z);
        }

    }

    @Override
    protected ItemStack getDefaultItemStack() {
        return ModWeaponItems.WOODEN_SPEAR.getDefaultStack();
    }

    protected float getDragInWater() {
        return 0.8f;
    }

    public boolean shouldRender(double cameraX, double cameraY, double cameraZ) {
        return true;
    }
}
