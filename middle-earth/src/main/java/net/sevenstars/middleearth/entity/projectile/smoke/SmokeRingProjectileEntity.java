package net.sevenstars.middleearth.entity.projectile.smoke;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class SmokeRingProjectileEntity extends ProjectileEntity {
    public static final int MAX_LIFESPAN_TICKS = 40;

    public SmokeRingProjectileEntity(EntityType<? extends SmokeRingProjectileEntity> type, World world) {
        super(type, world);
        this.setNoGravity(true);
        this.setOwner(owner);
    }

    public SmokeRingProjectileEntity(EntityType<? extends SmokeRingProjectileEntity> type, World world, LivingEntity owner) {
        this(type, world);
        this.setPosition(owner.getX(), owner.getEyeY() - 0.1, owner.getZ());

        Vec3d velocity = owner.getRotationVec(1.0F).normalize().multiply(0.5);
        this.setVelocity(velocity);
    }


    @Override
    public void tick() {
        super.tick();

        this.move(MovementType.SELF, this.getVelocity());

        if (this.age > MAX_LIFESPAN_TICKS) {
            this.discard();
        }
    }

    @Override
    public boolean damage(ServerWorld world, DamageSource source, float amount) {
        return false;
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        //Not needed yet
    }

    @Override
    protected void readCustomData(ReadView view) {
        //Not needed yet
    }

    @Override
    protected void writeCustomData(WriteView view) {
        //Not needed yet
    }
}
