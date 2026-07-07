package net.sevenstars.middleearth.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Direction;
import net.minecraft.world.WorldView;
import net.sevenstars.middleearth.block.registration.ModDecorativeBlocks;
import net.sevenstars.middleearth.entity.EntitiesME;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.explosion.Explosion;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Block.class)
public abstract class BlockMixin {
    @Unique private static final float RANDOM_FLYING_BLOCK = 0.34f;
    @Unique private static final float DISCARD_DISTANCE = 3;
    @Unique private static final float FORCE = 80;
    @Unique private static final float VERTICAL_MULTIPLIER = 10;

    @Shadow protected abstract Block asBlock();


    @Inject(at = @At("HEAD"), method = "onDestroyedByExplosion")
    private void onDestroyedByExplosion(ServerWorld world, BlockPos pos, Explosion explosion, CallbackInfo ci) {
        //TODO fix this
        //if(!explosion.shouldDestroy()) return;
        Block block = this.asBlock();

        if(explosion.getEntity() == null || explosion.getEntity().getType() == EntitiesME.FIRE_OF_ORTHANC) {
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

    @Inject(method = "sideCoversSmallSquare", at = @At("RETURN"), cancellable = true)
    private static void sideCoversSmallSquare(WorldView world, BlockPos pos, Direction side, CallbackInfoReturnable<Boolean> cir) {
        BlockState blockState = world.getBlockState(pos);
        if(blockState.getBlock() == ModDecorativeBlocks.ROPE) cir.setReturnValue(true);
    }
}
