package net.sevenstars.middleearth.block.special;

import net.minecraft.block.BlockState;
import net.minecraft.block.GlowLichenBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCollisionHandler;
import net.minecraft.entity.LivingEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class ThornyGrowthBlock extends GlowLichenBlock {
    public ThornyGrowthBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity, EntityCollisionHandler handler) {
        if (entity instanceof LivingEntity && world instanceof ServerWorld serverWorld) {
            Vec3d movement = entity.isControlledByPlayer() ? entity.getMovement() : entity.getLastRenderPos().subtract(entity.getPos());
            if (movement.horizontalLengthSquared() > 0.0) {
                double d = Math.abs(movement.getX());
                double e = Math.abs(movement.getZ());
                if (d >= 0.003000000026077032 || e >= 0.003000000026077032) {
                    entity.damage(serverWorld, world.getDamageSources().sweetBerryBush(), 1.0F);
                }
            }
        }
    }
}
