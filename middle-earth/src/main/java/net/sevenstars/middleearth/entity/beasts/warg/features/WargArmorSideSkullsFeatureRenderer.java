package net.sevenstars.middleearth.entity.beasts.warg.features;

import net.minecraft.client.render.entity.equipment.EquipmentRenderer;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.EquippableComponent;
import net.minecraft.item.equipment.EquipmentModel;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.entity.beasts.warg.WargEntity;
import net.sevenstars.middleearth.entity.beasts.warg.WargEntityRenderState;
import net.sevenstars.middleearth.entity.beasts.warg.WargModel;
import net.sevenstars.middleearth.entity.model.ModEntityModelLayers;
import net.sevenstars.middleearth.item.ModDataComponentTypes;
import net.sevenstars.middleearth.item.items.armor.CustomAnimalArmorItem;
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
import net.minecraft.util.Identifier;

public class WargArmorSideSkullsFeatureRenderer extends FeatureRenderer<WargEntityRenderState, WargModel> {
    private final WargArmorSideAddonsModel model;
    private final EquipmentRenderer equipmentRenderer;

    public WargArmorSideSkullsFeatureRenderer(FeatureRendererContext<WargEntityRenderState, WargModel> context, EntityModelLoader loader, EquipmentRenderer equipmentRenderer) {
        super(context);
        this.model = new WargArmorSideAddonsModel(loader.getModelPart(ModEntityModelLayers.WARG_ARMOR_ADDONS_SIDE_SKULL));
        this.equipmentRenderer = equipmentRenderer;
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, WargEntityRenderState state, float limbAngle, float limbDistance) {
        ItemStack itemStack = state.armor;
        EquippableComponent equippableComponent = (EquippableComponent)itemStack.get(DataComponentTypes.EQUIPPABLE);
        if (equippableComponent != null && !equippableComponent.model().isEmpty()) {
            WargArmorSideAddonsModel armorModel = this.model;
            Identifier identifier = (Identifier)equippableComponent.model().get();
            armorModel.setAngles(state);
            this.equipmentRenderer.render(EquipmentModel.LayerType.HORSE_BODY, identifier, armorModel, itemStack, matrices, vertexConsumers, light);
        }
    }
}
