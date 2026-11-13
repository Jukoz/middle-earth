package net.sevenstars.middleearth.mixin.client;

import net.minecraft.client.render.Camera;
import net.minecraft.client.render.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

/** The world's and the hand's field of view, which differ, to place things drawn in one where they show in the other. */
@Mixin(GameRenderer.class)
public interface GameRendererFovInvoker {
    @Invoker("getFov")
    float middleearth$getFov(Camera camera, float tickProgress, boolean changingFov);
}
