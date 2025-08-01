package net.sevenstars.middleearth.entity.spider;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.equipment.EquipmentModel;
import net.minecraft.client.render.entity.equipment.EquipmentRenderer;
import net.minecraft.client.render.entity.feature.EnergySwirlOverlayFeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.*;
import net.minecraft.client.render.entity.state.BipedEntityRenderState;
import net.minecraft.client.render.entity.state.CreeperEntityRenderState;
import net.minecraft.client.render.entity.state.PlayerEntityRenderState;
import net.minecraft.client.util.SkinTextures;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.EquippableComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.item.equipment.EquipmentAsset;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.client.renderer.ArmedEntityRenderStateAccess;
import net.sevenstars.middleearth.entity.ModEntityModelLayers;
import org.jetbrains.annotations.Nullable;

@Environment(EnvType.CLIENT)
public class EnwebbedFeatureRenderer <S extends BipedEntityRenderState, M extends EntityModel<S>> extends FeatureRenderer<S, M> {
    private static final Identifier TEXTURE = Identifier.of(MiddleEarth.MOD_ID, "textures/entities/spiders/enwebbed.png");

    private final EnwebbedModel model;

    public EnwebbedFeatureRenderer(FeatureRendererContext<S, M> context, LoadedEntityModels loader, EquipmentRenderer equipmentRenderer) {
        super(context);
        this.model = new EnwebbedModel(loader.getModelPart(ModEntityModelLayers.ENWEBBED));
    }

    public void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, S bipedEntityRenderState, float f, float g) {
        ArmedEntityRenderStateAccess renderStateAccess = ((ArmedEntityRenderStateAccess)bipedEntityRenderState);
        if(renderStateAccess.isRestrained()) {
            EnwebbedModel entityModel = this.model;
            entityModel.setAngles(bipedEntityRenderState);
            VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(RenderLayer.getEntityTranslucent(TEXTURE));
            entityModel.render(matrixStack, vertexConsumer, i, OverlayTexture.DEFAULT_UV);
        }
    }
}
