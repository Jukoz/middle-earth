package net.jukoz.me.client.renderer;

import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.MiddleEarthClient;
import net.jukoz.me.client.model.equipment.CustomHelmetModel;
import net.jukoz.me.client.model.equipment.head.helmets.HelmetAddonModel;
import net.jukoz.me.client.model.equipment.head.hoods.CloakHoodModel;
import net.jukoz.me.item.ModDataComponentTypes;
import net.jukoz.me.item.dataComponents.HoodDataComponent;
import net.jukoz.me.item.items.armor.HoodHelmetItem;
import net.jukoz.me.item.utils.armor.ModArmorModels;
import net.jukoz.me.recipe.ModTags;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public class HoodRenderer implements ArmorRenderer {

    private CustomHelmetModel<LivingEntity> customHelmetModel;
    private HelmetAddonModel<LivingEntity> hoodModel;

    public HoodRenderer() {
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, ItemStack stack, LivingEntity entity, EquipmentSlot slot, int light, BipedEntityModel<LivingEntity> contextModel) {
        this.hoodModel = new CloakHoodModel<>(MinecraftClient.getInstance().getEntityModelLoader().getModelPart(MiddleEarthClient.HOOD_MODEL_LAYER));

        HoodHelmetItem item = (HoodHelmetItem)stack.getItem();
        boolean dyeable = false;

        if (slot == EquipmentSlot.HEAD) {

            if(stack.isIn(ModTags.DYEABLE)) {
                dyeable = true;
            }

            HoodDataComponent hoodDataComponent = stack.get(ModDataComponentTypes.HOOD_DATA);

            if(hoodDataComponent != null) {
                Identifier texture;
                if (hoodDataComponent.down()){
                     texture = Identifier.of(MiddleEarth.MOD_ID, "textures/models/hood/" + hoodDataComponent.hood().getName().toLowerCase() + "_down.png");
                    this.hoodModel = ModArmorModels.ModHoodPairedModels.valueOf(hoodDataComponent.hood().getName().toUpperCase()).getModel().getUnarmoredDownModel();

                } else {
                    texture = Identifier.of(MiddleEarth.MOD_ID, "textures/models/hood/" + hoodDataComponent.hood().getName().toLowerCase() + ".png");
                    this.hoodModel = ModArmorModels.ModHoodPairedModels.valueOf(hoodDataComponent.hood().getName().toUpperCase()).getModel().getUnarmoredModel();
                }
                contextModel.copyBipedStateTo(hoodModel);
                hoodModel.setVisible(false);
                hoodModel.hat.visible = true;
                ModArmorRenderer.renderTranslucentPiece(matrices, vertexConsumers, light, stack, hoodModel, texture);
            }
        }
    }

}
