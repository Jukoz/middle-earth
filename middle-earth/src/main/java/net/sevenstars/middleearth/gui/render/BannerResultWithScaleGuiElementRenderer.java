package net.sevenstars.middleearth.gui.render;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.model.Model;
import net.minecraft.client.gui.render.SpecialGuiElementRenderer;
import net.minecraft.client.render.DiffuseLighting;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayers;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BannerBlockEntityRenderer;
import net.minecraft.client.render.command.OrderedRenderCommandQueueImpl;
import net.minecraft.client.render.command.RenderDispatcher;
import net.minecraft.client.render.model.ModelBaker;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Unit;
import net.sevenstars.middleearth.gui.render.states.BannerResultWithScaleGuiElementRenderState;

@Environment(EnvType.CLIENT)
public class BannerResultWithScaleGuiElementRenderer extends InstancedGuiElementRenderer<BannerResultWithScaleGuiElementRenderState> {
    public BannerResultWithScaleGuiElementRenderer(VertexConsumerProvider.Immediate immediate) {
        super(immediate);
    }

    public Class<BannerResultWithScaleGuiElementRenderState> getElementClass() {
        return BannerResultWithScaleGuiElementRenderState.class;
    }

    protected void render(BannerResultWithScaleGuiElementRenderState bannerResultWithStateGuiElementRenderState, MatrixStack matrixStack) {
        MinecraftClient.getInstance().gameRenderer.getDiffuseLighting().setShaderLights(DiffuseLighting.Type.ITEMS_FLAT);
        matrixStack.translate(0.0F, 0.25F, 0.0F);
        RenderDispatcher renderDispatcher = MinecraftClient.getInstance().gameRenderer.getEntityRenderDispatcher();
        OrderedRenderCommandQueueImpl queue = renderDispatcher.getQueue();
        BannerBlockEntityRenderer.renderCanvas(
                MinecraftClient.getInstance().getAtlasManager(),
                matrixStack,
                queue,
                15728880,
                OverlayTexture.DEFAULT_UV,
                new Model.SinglePartModel(bannerResultWithStateGuiElementRenderState.flag(), RenderLayers::entitySolid),
                Unit.INSTANCE,
                ModelBaker.BANNER_BASE,
                true,
                bannerResultWithStateGuiElementRenderState.baseColor(),
                bannerResultWithStateGuiElementRenderState.resultBannerPatterns(),
                false,
                null,
                0);
        renderDispatcher.render();
    }

    protected String getName() {
        return "banner result";
    }
}
