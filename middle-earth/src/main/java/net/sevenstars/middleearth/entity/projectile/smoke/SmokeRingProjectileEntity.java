package net.sevenstars.middleearth.entity.projectile.smoke;

import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.sevenstars.middleearth.utils.ModCollisionUtils;

public class SmokeRingProjectileEntity extends Projectile {
    public static final int MAX_LIFESPAN_TICKS = 40;
    public static final int FAILED_MAX_LIFESPAN_TICKS = 14;
    private static final EntityDataAccessor<Boolean> FAILED = SynchedEntityData.defineId(SmokeRingProjectileEntity.class, EntityDataSerializers.BOOLEAN);
    private static final double ENTITY_BOX_EXPANSION = 1.0;
    private static final float ENTITY_COLLISION_MARGIN = 0.3F;

    private transient boolean isFadingOut = false;

    public SmokeRingProjectileEntity(
            EntityType<? extends SmokeRingProjectileEntity> type,
            Level world) {
        super(type, world);
        this.setNoGravity(true);
    }

    @Override
    public void tick() {
        super.tick();
        this.checkCollision();
        this.checkLifespan();
    }

    @Override
    protected void onHitEntity(EntityHitResult hitResult) {
        super.onHitEntity(hitResult);
    }

    @Override
    protected void onHit(HitResult hitResult) {
        super.onHit(hitResult);
        this.triggerFadeOut();
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        return false;
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        builder.define(FAILED, false);
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag view) {
        super.readAdditionalSaveData(view);
        this.setFailed(view.getBoolean("Failed"));
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag view) {
        super.addAdditionalSaveData(view);
        view.putBoolean("Failed", this.isFailed());
    }

    private void checkLifespan() {
        if (this.tickCount >= this.getMaxLifespanTicks()) {
            this.discard();
        }
    }

    private void triggerFadeOut() {
        if (isFadingOut) return;

        isFadingOut = true;
        this.tickCount = this.getMaxLifespanTicks() - 3;

        this.setDeltaMovement(Vec3.ZERO);
        this.setPos(this.getX(), this.getY(), this.getZ());
    }

    private void checkCollision() {
        if (isFadingOut) return;

        if (this.isFailed()) {
            this.move(MoverType.SELF, this.getDeltaMovement());
            return;
        }

        if (checkEntityCollision() || checkBlockCollision()) {
            return;
        }

        this.move(MoverType.SELF, this.getDeltaMovement());
    }

    private boolean checkEntityCollision() {
        Vec3 start = this.position();
        Vec3 end = this.position().add(this.getDeltaMovement());

        EntityHitResult hit = ProjectileUtil.getEntityHitResult(this.level(),
                this,
                start,
                end,
                this.getBoundingBox().expandTowards(this.getDeltaMovement()).inflate(ENTITY_BOX_EXPANSION),
                this::canHitEntity,
                ENTITY_COLLISION_MARGIN);

        if (hit != null) {
            this.setPos(hit.getLocation());
            this.onHit(hit);
            return true;
        }

        return false;
    }

    private boolean checkBlockCollision() {
        return ModCollisionUtils.checkBlockFanCollision(this.level(),
                this.getBoundingBox(),
                this.getDeltaMovement(),
                this,
                this::onHit);
    }

    public void setFailed(boolean failed) {
        this.entityData.set(FAILED, failed);
    }

    public boolean isFailed() {
        return this.entityData.get(FAILED);
    }

    public int getMaxLifespanTicks() {
        return this.isFailed() ? FAILED_MAX_LIFESPAN_TICKS : MAX_LIFESPAN_TICKS;
    }
}
