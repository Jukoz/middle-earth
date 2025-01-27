package net.sevenstars.middleearth.mixin;

import net.sevenstars.middleearth.block.ModDecorativeBlocks;
import net.sevenstars.middleearth.entity.ModEntities;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Block.class)
public abstract class BlockMixin {
    @Unique private static final float RANDOM_FLYING_BLOCK = 0.34f;
    @Unique private static final float DISCARD_DISTANCE = 3;
    @Unique private static final float FORCE = 80;
    @Unique private static final float VERTICAL_MULTIPLIER = 10;

    @Shadow protected abstract Block asBlock();


    @Inject(at = @At("HEAD"), method = "onDestroyedByExplosion")
    private void onDestroyedByExplosion(World world, BlockPos pos, Explosion explosion, CallbackInfo ci) {
        if(!explosion.shouldDestroy()) return;
        Block block = this.asBlock();

        if(explosion.getEntity() == null || explosion.getEntity().getType() == ModEntities.FIRE_OF_ORTHANC) {
            if(block != Blocks.TNT && block != ModDecorativeBlocks.FIRE_OF_ORTHANC) {
                if(Math.random() < RANDOM_FLYING_BLOCK) {
                    float distance = (float) pos.getSquaredDistance(explosion.getPosition());
                    if(distance < explosion.getPower() / DISCARD_DISTANCE) return;

                    FallingBlockEntity fallingBlockEntity = FallingBlockEntity.spawnFromBlock(world, pos, block.getDefaultState());
                    fallingBlockEntity.dropItem = false;
                    fallingBlockEntity.setDestroyedOnLanding();
                    Vec3d velocity = pos.toCenterPos().subtract(explosion.getPosition()).normalize();
                    float factor = FORCE / distance;
                    velocity.multiply(factor);
                    velocity.add(0, VERTICAL_MULTIPLIER * factor, 0);
                    fallingBlockEntity.setVelocity(velocity);
                }
            }
        }
    }
}
