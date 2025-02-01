package net.sevenstars.middleearth.entity.beasts.warg.features;

import net.minecraft.client.render.entity.equipment.EquipmentRenderer;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.EquippableComponent;
import net.minecraft.item.equipment.EquipmentModel;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.entity.beasts.broadhoof.features.BroadhoofGoatArmorModel;
import net.sevenstars.middleearth.entity.beasts.warg.WargEntity;
import net.sevenstars.middleearth.entity.beasts.warg.WargEntityRenderState;
import net.sevenstars.middleearth.entity.beasts.warg.WargModel;
import net.sevenstars.middleearth.entity.model.ModEntityModelLayers;
import net.sevenstars.middleearth.item.dataComponents.CustomDyeableDataComponent;
import net.sevenstars.middleearth.item.items.armor.CustomAnimalArmorItem;
import net.sevenstars.middleearth.recipe.ModTags;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EntityModelLoader;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class WargArmorFeatureRenderer extends FeatureRenderer<WargEntityRenderState, WargModel> {
    private final WargArmorModel model;
    private final EquipmentRenderer equipmentRenderer;

    public WargArmorFeatureRenderer(FeatureRendererContext<WargEntityRenderState, WargModel> context, EntityModelLoader loader, EquipmentRenderer equipmentRenderer) {
        super(context);
        this.model = new WargArmorModel(loader.getModelPart(ModEntityModelLayers.WARG_ARMOR));
        this.equipmentRenderer = equipmentRenderer;
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, WargEntityRenderState state, float limbAngle, float limbDistance) {
        ItemStack itemStack = state.armor;
        EquippableComponent equippableComponent = (EquippableComponent)itemStack.get(DataComponentTypes.EQUIPPABLE);
        if (equippableComponent != null && !equippableComponent.model().isEmpty()) {
            WargArmorModel armorModel = this.model;
            Identifier identifier = (Identifier)equippableComponent.model().get();
            armorModel.setAngles(state);
            this.equipmentRenderer.render(EquipmentModel.LayerType.HORSE_BODY, identifier, armorModel, itemStack, matrices, vertexConsumers, light);
        }
    }
}
