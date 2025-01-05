package net.jukoz.me.block.special.fire_of_orthanc;

import net.jukoz.me.block.ModDecorativeBlocks;
import net.jukoz.me.entity.ModEntities;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class FireOfOrthancEntity extends Entity implements Ownable {
    private static final TrackedData<Integer> FUSE;
    private static final TrackedData<BlockState> BLOCK_STATE;
    private static final int DEFAULT_FUSE = 16;
    public static final float EXPLOSION_FORCE = 12.31f;
    protected boolean chainReaction = false;
    @Nullable
    private LivingEntity causingEntity;

    public FireOfOrthancEntity(EntityType<?> type, World world) {
        super(type, world);
    }

    public FireOfOrthancEntity(World world, double x, double y, double z, @Nullable LivingEntity igniter, boolean instant) {
        this(ModEntities.FIRE_OF_ORTHANC, world);
        this.setPosition(x, y, z);
        double d = world.random.nextDouble() * 6.2831854820251465;
        this.setVelocity(-Math.sin(d) * 0.02, 0.20000000298023224, -Math.cos(d) * 0.02);

        if(instant) {
            this.setFuse(6);
            chainReaction = true;
        }
        else this.setFuse(DEFAULT_FUSE);

        this.prevX = x;
        this.prevY = y;
        this.prevZ = z;
        this.causingEntity = igniter;
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        builder.add(FUSE, DEFAULT_FUSE);
        builder.add(BLOCK_STATE, ModDecorativeBlocks.FIRE_OF_ORTHANC.getDefaultState());
    }

    public void explode() {
        this.getWorld().createExplosion(this, this.getX(), this.getBodyY(0.0625), this.getZ(), EXPLOSION_FORCE, World.ExplosionSourceType.TNT);
    }

    @Override
    protected void readCustomDataFromNbt(NbtCompound nbt) {
        this.setFuse(nbt.getShort("fuse"));
        if (nbt.contains("block_state", 10)) {
            this.setBlockState(NbtHelper.toBlockState(this.getWorld().createCommandRegistryWrapper(RegistryKeys.BLOCK), nbt.getCompound("block_state")));
        }
    }

    @Override
    protected void writeCustomDataToNbt(NbtCompound nbt) {
        nbt.putShort("fuse", (short)this.getFuse());
        nbt.put("block_state", NbtHelper.fromBlockState(this.getBlockState()));
    }

    protected double getGravity() {
        return 0.04f;
    }

    public void tick() {
        this.applyGravity();
        this.move(MovementType.SELF, this.getVelocity());
        this.setVelocity(this.getVelocity().multiply(0.98));
        if (this.isOnGround()) {
            this.setVelocity(this.getVelocity().multiply(0.7, -0.5, 0.7));
        }

        int i = this.getFuse() - 1;
        this.setFuse(i);
        if (i <= 0) {
            this.discard();
            if (!this.getWorld().isClient) {
                this.explode();
            }
        } else {
            this.updateWaterState();
            if (this.getWorld().isClient && !chainReaction) {
                for(int j = 0; j < 4; j++) {
                    this.getWorld().addParticle(ParticleTypes.SMOKE, this.getX(), this.getY() + 0.8f, this.getZ(),
                            (Math.random() - 0.5f) * 0.3f, 0.4f, (Math.random() - 0.5f) * 0.5f);
                }
            }
        }
    }

    @Nullable
    @Override
    public Entity getOwner() {
        return causingEntity;
    }

    public void setFuse(int fuse) {
        this.dataTracker.set(FUSE, fuse);
    }

    public int getFuse() {
        return this.dataTracker.get(FUSE);
    }

    public void setBlockState(BlockState state) {
        this.dataTracker.set(BLOCK_STATE, state);
    }

    public BlockState getBlockState() {
        return this.dataTracker.get(BLOCK_STATE);
    }

    static {
        FUSE = DataTracker.registerData(FireOfOrthancEntity.class, TrackedDataHandlerRegistry.INTEGER);
        BLOCK_STATE = DataTracker.registerData(FireOfOrthancEntity.class, TrackedDataHandlerRegistry.BLOCK_STATE);
    }
}
