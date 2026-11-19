package net.sevenstars.middleearth.mixin.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.CloudRenderMode;
import net.minecraft.client.render.CloudRenderer;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.ColorHelper;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Heightmap;
import net.sevenstars.middleearth.features.generation.dimension.DimensionRegistryME;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// The cloud plane is flat, so it would be drawn through the caves and ravines that reach up past it inside the tall mountains
@Environment(EnvType.CLIENT)
@Mixin(CloudRenderer.class)
public class CloudRendererMixin {
    // Blocks under the highest solid block of the camera's column that count as underground, and the way back out is a little shorter,
    // so the clouds do not flicker at a cave mouth
    @Unique
    private static final double HIDE_BELOW = 4;
    @Unique
    private static final double SHOW_ABOVE = 1;
    // Change of the cloud opacity per tick
    @Unique
    private static final float FADE_SPEED = 0.04f;
    @Unique
    private static float visibility = 1;
    @Unique
    private static boolean hidden = false;
    @Unique
    private static double lastTime = Double.NaN;

    // Scales the opacity of the clouds by how far they have faded; the colour is argb
    @ModifyVariable(method = "renderClouds", at = @At("HEAD"), argsOnly = true, index = 1)
    private int fadeClouds(int color, int unused, CloudRenderMode mode, float cloudHeight, Vec3d cameraPos, long time, float tickProgress) {
        ClientWorld world = MinecraftClient.getInstance().world;
        if(world == null || !DimensionRegistryME.isInMiddleEarth(world)) {
            visibility = 1;
            hidden = false;
            return color;
        }
        double depth = world.getTopY(Heightmap.Type.MOTION_BLOCKING, MathHelper.floor(cameraPos.x), MathHelper.floor(cameraPos.z)) - cameraPos.y;
        hidden = hidden ? depth > SHOW_ABOVE : depth > HIDE_BELOW;
        double now = time + tickProgress;
        float step = Double.isNaN(lastTime) ? 1 : (float) MathHelper.clamp(now - lastTime, 0, 5) * FADE_SPEED;
        lastTime = now;
        visibility = hidden ? Math.max(0, visibility - step) : Math.min(1, visibility + step);
        if(visibility >= 1)
            return color;
        return ColorHelper.withAlpha(Math.round(ColorHelper.getAlpha(color) * visibility), color);
    }

    // Nothing to draw once they have faded out
    @Inject(method = "renderClouds", at = @At("HEAD"), cancellable = true)
    private void skipHiddenClouds(int color, CloudRenderMode mode, float cloudHeight, Vec3d cameraPos, long time, float tickProgress, CallbackInfo ci) {
        if(hidden && visibility <= 0)
            ci.cancel();
    }
}
