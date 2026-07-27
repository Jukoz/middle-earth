package net.sevenstars.middleearth.mixin.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.sevenstars.middleearth.config.ModClientConfigs;
import net.sevenstars.middleearth.world.dimension.ModDimensions;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ItemRenderer.class)
public abstract class ItemRendererMixin {
    @ModifyVariable(
            method = "getArmorFoilBuffer",
            at = @At("HEAD"),
            argsOnly = true,
            index = 2
    )
    private static boolean middleEarth$disableArmorFoilInMiddleEarth(boolean hasFoil) {
        return shouldRenderFoil(hasFoil);
    }

    @ModifyVariable(
            method = {"getFoilBuffer", "getFoilBufferDirect"},
            at = @At("HEAD"),
            argsOnly = true,
            index = 3
    )
    private static boolean middleEarth$disableItemFoilInMiddleEarth(boolean hasFoil) {
        return shouldRenderFoil(hasFoil);
    }

    private static boolean shouldRenderFoil(boolean hasFoil) {
        Minecraft minecraft = Minecraft.getInstance();
        return hasFoil && (!ModClientConfigs.DISABLE_GLINT
                || minecraft.level == null
                || !ModDimensions.isInMiddleEarth(minecraft.level));
    }
}
