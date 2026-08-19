package net.sevenstars.middleearth.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerPlayerGameMode;
import net.minecraft.world.level.block.state.BlockState;
import net.sevenstars.middleearth.event.ModEvents;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ServerPlayerGameMode.class)
public abstract class ServerPlayerGameModeMixin {
    @Shadow
    protected ServerLevel level;

    @Shadow
    @Final
    protected ServerPlayer player;

    @Unique
    private BlockPos middleEarth$breakPos;

    @Unique
    private BlockState middleEarth$breakState;

    @Inject(method = "destroyBlock", at = @At("HEAD"))
    private void middleEarth$captureBrokenBlock(
            BlockPos pos,
            CallbackInfoReturnable<Boolean> callback
    ) {
        middleEarth$breakPos = pos.immutable();
        middleEarth$breakState = level.getBlockState(pos);
    }

    @Inject(method = "destroyBlock", at = @At("RETURN"))
    private void middleEarth$afterBlockBreak(
            BlockPos pos,
            CallbackInfoReturnable<Boolean> callback
    ) {
        try {
            if (callback.getReturnValueZ()
                    && pos.equals(middleEarth$breakPos)
                    && middleEarth$breakState != null) {
                ModEvents.afterBlockBreak(player, middleEarth$breakPos, middleEarth$breakState);
            }
        } finally {
            middleEarth$breakPos = null;
            middleEarth$breakState = null;
        }
    }
}
