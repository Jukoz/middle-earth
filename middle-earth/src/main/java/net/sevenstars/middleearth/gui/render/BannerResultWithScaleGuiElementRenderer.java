package net.sevenstars.middleearth.gui.render;

import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.vertex.PoseStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.blockentity.BannerRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.entity.BannerPatternLayers;

@OnlyIn(Dist.CLIENT)
public final class BannerResultWithScaleGuiElementRenderer {
    private BannerResultWithScaleGuiElementRenderer() {
    }

    public static void render(
            GuiGraphics graphics,
            ModelPart flag,
            DyeColor baseColor,
            BannerPatternLayers patterns,
            int x,
            int y,
            float scale
    ) {
        Lighting.setupForFlatItems();
        PoseStack pose = graphics.pose();
        pose.pushPose();
        pose.translate(x, y, 0.0F);
        pose.scale(scale, scale, 1.0F);
        pose.translate(0.5F, -0.5F, 0.5F);
        pose.scale(0.6666667F, 0.6666667F, -0.6666667F);
        flag.xRot = 0.0F;
        flag.y = -32.0F;
        BannerRenderer.renderPatterns(
                pose,
                graphics.bufferSource(),
                15728880,
                OverlayTexture.NO_OVERLAY,
                flag,
                ModelBakery.BANNER_BASE,
                true,
                baseColor,
                patterns
        );
        pose.popPose();
        graphics.flush();
        Lighting.setupFor3DItems();
    }
}
