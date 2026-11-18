package net.sevenstars.middleearth.mixin.client;

import net.minecraft.client.render.Camera;
import net.minecraft.client.render.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

/** Exposes the world and hand field of view. */
@Mixin(GameRenderer.class)
public interface GameRendererFovInvoker {
    @Invoker("getFov")
    float middleearth$getFov(Camera camera, float tickProgress, boolean changingFov);
}
