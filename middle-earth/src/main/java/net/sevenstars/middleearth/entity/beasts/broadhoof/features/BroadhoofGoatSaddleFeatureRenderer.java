package net.sevenstars.middleearth.entity.beasts.broadhoof.features;

import net.minecraft.client.render.entity.equipment.EquipmentModel;
import net.minecraft.client.render.entity.equipment.EquipmentRenderer;
import net.minecraft.client.render.entity.model.LoadedEntityModels;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.EquippableComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKey;
import net.sevenstars.middleearth.entity.beasts.broadhoof.BroadhoofGoatEntityRenderState;
import net.sevenstars.middleearth.entity.beasts.broadhoof.BroadhoofGoatModel;
import net.sevenstars.middleearth.entity.model.ModEntityModelLayers;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.util.math.MatrixStack;

public class BroadhoofGoatSaddleFeatureRenderer extends FeatureRenderer<BroadhoofGoatEntityRenderState, BroadhoofGoatModel> {
    private final BroadhoofGoatSaddleModel model;
    private final EquipmentRenderer equipmentRenderer;

    public BroadhoofGoatSaddleFeatureRenderer(FeatureRendererContext<BroadhoofGoatEntityRenderState, BroadhoofGoatModel> context, LoadedEntityModels loader, EquipmentRenderer equipmentRenderer) {
        super(context);
        this.model = new BroadhoofGoatSaddleModel(loader.getModelPart(ModEntityModelLayers.BROADHOOF_GOAT_SADDLE));
        this.equipmentRenderer = equipmentRenderer;
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, BroadhoofGoatEntityRenderState state, float limbAngle, float limbDistance) {
        ItemStack itemStack = state.armor;
        EquippableComponent equippableComponent = (EquippableComponent)itemStack.get(DataComponentTypes.EQUIPPABLE);
        if (equippableComponent != null && !equippableComponent.assetId().isEmpty()) {
            BroadhoofGoatSaddleModel saddleModel = this.model;
            saddleModel.setAngles(state);
            this.equipmentRenderer.render(EquipmentModel.LayerType.HORSE_BODY, (RegistryKey)equippableComponent.assetId().get(), saddleModel, itemStack, matrices, vertexConsumers, light);
        }
    }
}
