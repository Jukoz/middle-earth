package net.sevenstars.middleearth.gui.render;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.DiffuseLighting;
import net.minecraft.client.render.LightmapTextureManager;
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
    private ModelPart cachedFlag;
    private Model.SinglePartModel flagModel;

    public BannerResultWithScaleGuiElementRenderer(VertexConsumerProvider.Immediate immediate) {
        super(immediate);
    }

    public Class<BannerResultWithScaleGuiElementRenderState> getElementClass() {
        return BannerResultWithScaleGuiElementRenderState.class;
    }

    protected void render(BannerResultWithScaleGuiElementRenderState state, MatrixStack matrixStack) {
        if (cachedFlag != state.flag()) {
            cachedFlag = state.flag();
            flagModel = new Model.SinglePartModel(cachedFlag, RenderLayers::entitySolid);
        }
        MinecraftClient.getInstance().gameRenderer.getDiffuseLighting().setShaderLights(DiffuseLighting.Type.ITEMS_FLAT);
        matrixStack.translate(0.0F, 0.25F, 0.0F);
        RenderDispatcher renderDispatcher = MinecraftClient.getInstance().gameRenderer.getEntityRenderDispatcher();
        OrderedRenderCommandQueueImpl queue = renderDispatcher.getQueue();
        BannerBlockEntityRenderer.renderCanvas(
                MinecraftClient.getInstance().getAtlasManager(),
                matrixStack,
                queue,
                LightmapTextureManager.MAX_LIGHT_COORDINATE,
                OverlayTexture.DEFAULT_UV,
                flagModel,
                Unit.INSTANCE,
                ModelBaker.BANNER_BASE,
                true,
                state.baseColor(),
                state.resultBannerPatterns(),
                false,
                null,
                0);
        renderDispatcher.render();
    }

    protected String getName() {
        return "banner result";
    }
}
