package net.sevenstars.middleearth.entity.beasts.warg.features;

import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.equipment.EquipmentModel;
import net.minecraft.client.render.entity.equipment.EquipmentRenderer;
import net.minecraft.client.render.entity.model.LoadedEntityModels;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.EquippableComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.entity.beasts.warg.WargEntityRenderState;
import net.sevenstars.middleearth.entity.beasts.warg.WargModel;
import net.sevenstars.middleearth.entity.ModEntityModelLayers;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.util.math.MatrixStack;

public class WargSaddleFeatureRenderer extends FeatureRenderer<WargEntityRenderState, WargModel> {
    private final WargSaddleModel model;

    public WargSaddleFeatureRenderer(FeatureRendererContext<WargEntityRenderState, WargModel> context, LoadedEntityModels loader, EquipmentRenderer equipmentRenderer) {
        super(context);
        this.model = new WargSaddleModel(loader.getModelPart(ModEntityModelLayers.WARG_SADDLE));
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, WargEntityRenderState state, float limbAngle, float limbDistance) {
        ItemStack itemStack = state.saddle;
        boolean hasArmor = !state.armor.isEmpty();
        if(!itemStack.isEmpty()) {
            VertexConsumer vertexConsumer = ItemRenderer.getArmorGlintConsumer(vertexConsumers, RenderLayer.getArmorCutoutNoCull(Identifier.of(MiddleEarth.MOD_ID, "textures/entities/warg/feature/warg_saddle.png")), itemStack.hasGlint());

            if(!hasArmor) {
                matrices.scale(0.9f, 0.9f, 0.9f);
                matrices.translate(0, 0.05f, 0);
            }

            model.setAngles(state);
            model.render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV);
        }
    }
}