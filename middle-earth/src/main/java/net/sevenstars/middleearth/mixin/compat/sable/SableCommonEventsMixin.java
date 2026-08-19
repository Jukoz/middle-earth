package net.sevenstars.middleearth.mixin.compat.sable;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import net.sevenstars.middleearth.world.dimension.ModDimensions;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Pseudo
@Mixin(targets = "dev.ryanhcode.sable.SableCommonEvents", remap = false)
public abstract class SableCommonEventsMixin {
    @Inject(
            method = "handleBlockChange",
            at = @At("HEAD"),
            cancellable = true,
            require = 0,
            remap = false
    )
    private static void middleEarth$skipAsyncWorldgenBlockUpdates(
            ServerLevel level,
            LevelChunk chunk,
            int x,
            int y,
            int z,
            BlockState oldState,
            BlockState newState,
            CallbackInfo ci
    ) {
        if (level.dimension().equals(ModDimensions.ME_WORLD_KEY)
                && !level.getServer().isSameThread()) {
            ci.cancel();
        }
    }
}
