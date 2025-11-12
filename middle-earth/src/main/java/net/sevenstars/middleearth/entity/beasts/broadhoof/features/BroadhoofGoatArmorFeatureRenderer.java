package net.sevenstars.middleearth.entity.beasts.broadhoof.features;

import net.minecraft.client.render.entity.equipment.EquipmentModel;
import net.minecraft.client.render.entity.equipment.EquipmentRenderer;
import net.minecraft.client.render.entity.model.LoadedEntityModels;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.EquippableComponent;
import net.minecraft.registry.RegistryKey;
import net.sevenstars.middleearth.entity.beasts.broadhoof.BroadhoofGoatEntityRenderState;
import net.sevenstars.middleearth.entity.beasts.broadhoof.BroadhoofGoatModel;
import net.sevenstars.middleearth.entity.ModEntityModelLayers;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;

public class BroadhoofGoatArmorFeatureRenderer extends FeatureRenderer<BroadhoofGoatEntityRenderState, BroadhoofGoatModel> {

    private final BroadhoofGoatArmorModel model;
    private final EquipmentRenderer equipmentRenderer;

    public BroadhoofGoatArmorFeatureRenderer(FeatureRendererContext<BroadhoofGoatEntityRenderState, BroadhoofGoatModel> context, LoadedEntityModels loader, EquipmentRenderer equipmentRenderer) {
        super(context);
        this.equipmentRenderer = equipmentRenderer;
        this.model = new BroadhoofGoatArmorModel(loader.getModelPart(ModEntityModelLayers.BROADHOOF_GOAT_ARMOR));
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, BroadhoofGoatEntityRenderState state, float limbAngle, float limbDistance) {
        ItemStack itemStack = state.armor;
        EquippableComponent equippableComponent = (EquippableComponent)itemStack.get(DataComponentTypes.EQUIPPABLE);
        if (equippableComponent != null && !equippableComponent.assetId().isEmpty()) {
            model.setAngles(state);
            this.equipmentRenderer.render(EquipmentModel.LayerType.HORSE_BODY, (RegistryKey)equippableComponent.assetId().get(), model, itemStack, matrices, vertexConsumers, light);
        }
    }
}
