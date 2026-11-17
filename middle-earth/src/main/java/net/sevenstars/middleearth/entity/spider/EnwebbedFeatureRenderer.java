package net.sevenstars.middleearth.entity.spider;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.entity.equipment.EquipmentRenderer;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.*;
import net.minecraft.client.render.entity.state.BipedEntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.sevenstars.middleearth.client.RenderUtil;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.client.renderer.ArmedEntityRenderStateAccess;
import net.sevenstars.middleearth.entity.EntityModelLayersME;

@Environment(EnvType.CLIENT)
public class EnwebbedFeatureRenderer <S extends BipedEntityRenderState, M extends EntityModel<S>> extends FeatureRenderer<S, M> {
    private static final Identifier TEXTURE = Identifier.of(MiddleEarth.MOD_ID, "textures/entities/spiders/enwebbed.png");

    private final EnwebbedModel model;

    public EnwebbedFeatureRenderer(FeatureRendererContext<S, M> context, LoadedEntityModels loader, EquipmentRenderer equipmentRenderer) {
        super(context);
        this.model = new EnwebbedModel(loader.getModelPart(EntityModelLayersME.ENWEBBED));
    }

    public void render(MatrixStack matrixStack, OrderedRenderCommandQueue queue, int i, S bipedEntityRenderState, float f, float g) {
        ArmedEntityRenderStateAccess renderStateAccess = ((ArmedEntityRenderStateAccess)bipedEntityRenderState);
        if(renderStateAccess.isRestrained()) {
            EnwebbedModel entityModel = this.model;
            RenderUtil.renderTranslucentTexture(entityModel, bipedEntityRenderState, matrixStack, queue, TEXTURE, i, OverlayTexture.DEFAULT_UV);
        }
    }
}
