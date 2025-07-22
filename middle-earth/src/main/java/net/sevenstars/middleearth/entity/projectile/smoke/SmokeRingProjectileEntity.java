package net.sevenstars.middleearth.entity.projectile.smoke;

import net.minecraft.entity.*;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.sevenstars.middleearth.utils.ModCollisionUtils;

public class SmokeRingProjectileEntity extends ProjectileEntity {
    public static final int MAX_LIFESPAN_TICKS = 40;

    private static final double INITIAL_SPEED = 0.5;
    private static final double ENTITY_BOX_EXPANSION = 1.0;
    private static final float ENTITY_COLLISION_MARGIN = 0.3F;

    private transient boolean isFadingOut = false;

    public SmokeRingProjectileEntity(
            EntityType<? extends SmokeRingProjectileEntity> type,
            World world) {
        super(type, world);
        this.setNoGravity(true);
    }

    public SmokeRingProjectileEntity(
            EntityType<? extends SmokeRingProjectileEntity> type,
            World world,
            LivingEntity owner) {
        this(type, world);
        this.setOwner(owner);
        this.setPosition(owner.getX(), owner.getEyeY() - 0.1, owner.getZ());

        Vec3d velocity = owner.getRotationVec(1.0F).normalize().multiply(INITIAL_SPEED);
        this.setVelocity(velocity);
    }

    @Override
    public void tick() {
        super.tick();
        this.checkCollision();
        this.checkLifespan();
    }

    @Override
    protected void onEntityHit(EntityHitResult hitResult) {
        super.onEntityHit(hitResult);
    }

    @Override
    protected void onCollision(HitResult hitResult) {
        super.onCollision(hitResult);
        this.triggerFadeOut();
    }

    @Override
    public boolean damage(ServerWorld world, DamageSource source, float amount) {
        return false;
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        // Not needed yet
    }

    @Override
    protected void readCustomData(ReadView view) {
        super.readCustomData(view);
    }

    @Override
    protected void writeCustomData(WriteView view) {
        super.writeCustomData(view);
    }

    private void checkLifespan() {
        if (this.age >= MAX_LIFESPAN_TICKS) {
            this.discard();
        }
    }

    private void triggerFadeOut() {
        if (isFadingOut) return;

        isFadingOut = true;
        this.age = MAX_LIFESPAN_TICKS - 3;

        this.setVelocity(Vec3d.ZERO);
        this.setPosition(this.getX(), this.getY(), this.getZ());
    }

    private void checkCollision() {
        if (isFadingOut) return;

        // Simplified collision checks
        if (checkEntityCollision() || checkBlockCollision()) {
            return;
        }

        this.move(MovementType.SELF, this.getVelocity());
    }

    private boolean checkEntityCollision() {
        Vec3d start = this.getPos();
        Vec3d end = this.getPos().add(this.getVelocity());

        // Get entity collision result
        EntityHitResult hit = ProjectileUtil.getEntityCollision(this.getWorld(),
                this,
                start,
                end,
                this.getBoundingBox().stretch(this.getVelocity()).expand(ENTITY_BOX_EXPANSION),
                this::canHit,
                ENTITY_COLLISION_MARGIN);

        if (hit != null) {
            this.setPosition(hit.getPos());
            this.onCollision(hit);
            return true;
        }

        return false;
    }

    private boolean checkBlockCollision() {
        return ModCollisionUtils.checkBlockFanCollision(this.getWorld(),
                this.getBoundingBox(),
                this.getVelocity(),
                this,
                this::onCollision);
    }
}
