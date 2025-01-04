package net.jukoz.me.mixin;

import net.jukoz.me.world.dimension.ModDimensions;
import net.minecraft.server.world.ServerChunkManager;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.profiler.Profiler;
import net.minecraft.world.SpawnHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.List;

@Mixin(ServerChunkManager.class)
public class ServerChunkManagerMixin {

    /* // Keep this method in case of CPR for spawners chunk tick
    @Shadow private ServerWorld world;
    @Inject(method = "tickChunks", locals = LocalCapture.CAPTURE_FAILHARD, at= @At(value = "INVOKE", shift = At.Shift.AFTER,
            target = "Lnet/minecraft/util/Util;shuffle(Ljava/util/List;Lnet/minecraft/util/math/random/Random;)V"))
    public void tickChunks(CallbackInfo ci, long l, long m, Profiler profiler, List list, int i, SpawnHelper.Info info, boolean bl) {
        System.out.println("Can mob spawn: " + bl);
        boolean inMiddleEarth = ModDimensions.isInMiddleEarth(this.world);
        System.out.println("Is in Middle-earth: " + inMiddleEarth);
        if(bl && inMiddleEarth) {
            System.out.println("tick spawners");
            this.world.tickSpawners(false, false);
        }
    }*/
}
