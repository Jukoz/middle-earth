package net.sevenstars.middleearth.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.sevenstars.middleearth.block.registration.ModDecorativeBlocks;
import net.sevenstars.middleearth.entity.EntitiesME;
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


    @Inject(at = @At("HEAD"), method = "wasExploded(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/Explosion;)V")
    private void onDestroyedByExplosion(Level world, BlockPos pos, Explosion explosion, CallbackInfo ci) {
        if (!explosion.interactsWithBlocks()) {
            return;
        }
        Block block = this.asBlock();

        if(explosion.getDirectSourceEntity() == null || explosion.getDirectSourceEntity().getType() == EntitiesME.FIRE_OF_ORTHANC) {
            if(block != Blocks.TNT && block != ModDecorativeBlocks.FIRE_OF_ORTHANC) {
                if(world.getRandom().nextDouble() < RANDOM_FLYING_BLOCK) {
                    float distance = (float) pos.distToCenterSqr(explosion.center());
                    if(distance < explosion.radius() / DISCARD_DISTANCE) return;

                    FallingBlockEntity fallingBlockEntity = FallingBlockEntity.fall(world, pos, block.defaultBlockState());
                    fallingBlockEntity.dropItem = false;
                    fallingBlockEntity.disableDrop();
                    float factor = FORCE / distance;
                    Vec3 velocity = pos.getCenter()
                            .subtract(explosion.center())
                            .normalize()
                            .scale(factor)
                            .add(0, VERTICAL_MULTIPLIER * factor, 0);
                    fallingBlockEntity.setDeltaMovement(velocity);
                }
            }
        }
    }

    @Inject(method = "canSupportCenter(Lnet/minecraft/world/level/LevelReader;Lnet/minecraft/core/BlockPos;Lnet/minecraft/core/Direction;)Z", at = @At("RETURN"), cancellable = true)
    private static void canSupportCenter(
            LevelReader level,
            BlockPos pos,
            Direction direction,
            CallbackInfoReturnable<Boolean> cir
    ) {
        BlockState state = level.getBlockState(pos);
        if (state.is(ModDecorativeBlocks.ROPE)) {
            cir.setReturnValue(true);
        }
    }
}
