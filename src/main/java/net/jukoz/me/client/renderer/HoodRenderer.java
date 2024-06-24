package net.jukoz.me.client.renderer;

import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.MiddleEarthClient;
import net.jukoz.me.client.model.equipment.CustomHelmetModel;
import net.jukoz.me.client.model.equipment.head.CloakHoodModel;
import net.jukoz.me.item.ModDataComponentTypes;
import net.jukoz.me.item.dataComponents.HoodDataComponent;
import net.jukoz.me.item.items.HoodHelmetItem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class HoodRenderer implements ArmorRenderer {

    private CustomHelmetModel<LivingEntity> customHelmetModel;
    private CloakHoodModel<LivingEntity> hoodModel;

    public HoodRenderer() {
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, ItemStack stack, LivingEntity entity, EquipmentSlot slot, int light, BipedEntityModel<LivingEntity> contextModel) {
        this.hoodModel = new CloakHoodModel<>(MinecraftClient.getInstance().getEntityModelLoader().getModelPart(MiddleEarthClient.HOOD_MODEL_LAYER));

        HoodHelmetItem item = (HoodHelmetItem)stack.getItem();
        boolean dyeable = false;
        String texture = "textures/models/armor/" + Registries.ITEM.getId(stack.getItem()).getPath() + ".png";

        if (slot == EquipmentSlot.HEAD) {

            if(stack.isIn(TagKey.of(RegistryKeys.ITEM, new Identifier(MiddleEarth.MOD_ID, "dyeable")))) {
                dyeable = true;
            }

            HoodDataComponent hoodDataComponent = stack.get(ModDataComponentTypes.HOOD_DATA);

            if(hoodDataComponent != null) {
                if (hoodDataComponent.enabled()) {
                    contextModel.copyBipedStateTo(hoodModel);
                    hoodModel.setVisible(false);
                    hoodModel.hat.visible = true;
                    if(hoodDataComponent.down()){
                        ModArmorRenderer.renderArmor(matrices, vertexConsumers, light, stack, hoodModel, new Identifier(MiddleEarth.MOD_ID, "textures/models/armor/" + hoodDataComponent.hood().toLowerCase() + "_down.png"), false);
                    } else {
                        ModArmorRenderer.renderArmor(matrices, vertexConsumers, light, stack, hoodModel, new Identifier(MiddleEarth.MOD_ID, "textures/models/armor/" + hoodDataComponent.hood().toLowerCase() + ".png"), false);
                    }
                }
            }
        }
    }

}
