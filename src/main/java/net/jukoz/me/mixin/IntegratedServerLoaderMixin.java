package net.jukoz.me.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.resource.ResourcePackManager;
import net.minecraft.server.SaveLoader;
import net.minecraft.server.integrated.IntegratedServerLoader;
import net.minecraft.world.level.storage.LevelStorage;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(IntegratedServerLoader.class)
public abstract class IntegratedServerLoaderMixin {
    @ModifyVariable(method = "tryLoad" ,at = @At("HEAD"), index = 4, argsOnly = true)
    private static boolean removeAdviceOnCreation(boolean original) {
        return true;
    }

    @ModifyVariable( method = "start(Lnet/minecraft/world/level/storage/LevelStorage$Session;Lcom/mojang/serialization/Dynamic;ZLjava/lang/Runnable;)V",
            at = @At("HEAD"), index = 3, argsOnly = true)
    private boolean removeAdviceOnLoad(boolean original) {
        return false;
    }

    @Shadow protected abstract void start(LevelStorage.Session session, SaveLoader saveLoader, ResourcePackManager dataPackManager, Runnable onCancel);

    @Redirect(method = "checkBackupAndStart", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/server/integrated/IntegratedServerLoader;showBackupPromptScreen(Lnet/minecraft/world/level/storage/LevelStorage$Session;ZLjava/lang/Runnable;Ljava/lang/Runnable;)V"))
    private void checkBackupAndStart(IntegratedServerLoader instance, LevelStorage.Session session, boolean customized,
                                     Runnable callback, Runnable onCancel, @Local SaveLoader saveLoader,
                                     @Local ResourcePackManager dataPackManager) {
        this.start(session, saveLoader, dataPackManager, onCancel);
    }
}
