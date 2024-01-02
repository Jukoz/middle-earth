package net.jukoz.me.mixin;

import net.minecraft.server.integrated.IntegratedServerLoader;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(IntegratedServerLoader.class)
public class IntegratedServerLoaderMixin {
    @ModifyVariable(method = "tryLoad" ,at = @At("HEAD"), index = 4, argsOnly = true)
    private static boolean removeAdviceOnCreation(boolean original) {
        return true;
    }

    @ModifyVariable(method = "start(Lnet/minecraft/client/gui/screen/Screen;Ljava/lang/String;ZZ)V",
            at = @At("HEAD"), index = 4, argsOnly = true)
    private boolean removeAdviceOnLoad(boolean original) {
        return false;
    }
}
