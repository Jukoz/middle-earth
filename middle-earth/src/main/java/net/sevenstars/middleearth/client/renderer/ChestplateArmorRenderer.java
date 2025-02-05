package net.sevenstars.middleearth.client.renderer;

import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.MiddleEarthClient;
import net.sevenstars.middleearth.client.model.equipment.CustomChestplateModel;
import net.sevenstars.middleearth.client.model.equipment.chest.ChestplateAddonModel;
import net.sevenstars.middleearth.client.model.equipment.chest.capes.CloakCapeModel;
import net.sevenstars.middleearth.item.ModDataComponentTypes;
import net.sevenstars.middleearth.item.dataComponents.CapeDataComponent;
import net.sevenstars.middleearth.item.items.armor.CustomChestplateItem;
import net.sevenstars.middleearth.item.utils.armor.ModArmorModels;
import net.sevenstars.middleearth.item.utils.armor.ModDyeablePieces;
import net.sevenstars.middleearth.recipe.ModTags;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

public class ChestplateArmorRenderer implements ArmorRenderer {

    private CustomChestplateModel<LivingEntity> customChestplateModel;
    private ChestplateAddonModel<LivingEntity> capeModel;
    private ChestplateAddonModel<LivingEntity> chestplateModel;

    public ChestplateArmorRenderer() {
    }

    public ChestplateArmorRenderer(ChestplateAddonModel<LivingEntity> chestplateModel) {
        this.chestplateModel = chestplateModel;
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, ItemStack stack, LivingEntity entity, EquipmentSlot slot, int light, BipedEntityModel<LivingEntity> contextModel) {
        this.customChestplateModel = new CustomChestplateModel<>(MinecraftClient.getInstance().getEntityModelLoader().getModelPart(MiddleEarthClient.CUSTOM_ARMOR_CHESTPLATE));
        this.capeModel = new CloakCapeModel<>(MinecraftClient.getInstance().getEntityModelLoader().getModelPart(MiddleEarthClient.CAPE_MODEL_LAYER));

        CustomChestplateItem item = (CustomChestplateItem)stack.getItem();
        boolean dyeable = false;

        if (slot == EquipmentSlot.CHEST) {
            contextModel.copyBipedStateTo(customChestplateModel);
            customChestplateModel.setVisible(false);
            customChestplateModel.body.visible = true;
            customChestplateModel.rightArm.visible = true;
            customChestplateModel.leftArm.visible = true;
            customChestplateModel.rightLeg.visible = true;
            customChestplateModel.leftLeg.visible = true;

            if (stack.isIn(ModTags.DYEABLE)) {
                dyeable = true;
            }

            String texture = "textures/models/armor/" + Registries.ITEM.getId(stack.getItem()).getPath() + ".png";
            ModArmorRenderer.renderArmor(matrices, vertexConsumers, light, stack, customChestplateModel, Identifier.of(MiddleEarth.MOD_ID, texture), dyeable);

            if (this.chestplateModel != null) {
                contextModel.copyBipedStateTo(this.chestplateModel);
                this.chestplateModel.setVisible(false);
                this.chestplateModel.body.visible = true;
                this.chestplateModel.rightArm.visible = true;
                this.chestplateModel.leftArm.visible = true;
                ModArmorRenderer.renderArmor(matrices, vertexConsumers, light, stack, this.chestplateModel, Identifier.of(MiddleEarth.MOD_ID, texture.replaceAll("_chestplate.png", "_addition.png")), dyeable);
            }

            CapeDataComponent capeDataComponent = stack.get(ModDataComponentTypes.CAPE_DATA);
            if (capeDataComponent != null) {
                this.capeModel = ModArmorModels.ModCapePairedModels.valueOf(capeDataComponent.cape().getName().toUpperCase()).getModel().getArmoredModel();
                contextModel.copyBipedStateTo(capeModel);
                capeModel.setVisible(false);
                capeModel.body.visible = true;
                capeModel.rightArm.visible = true;
                capeModel.leftArm.visible = true;
                capeModel.rightLeg.visible = true;
                capeModel.leftLeg.visible = true;
                capeModel.setAngles(entity, entity.limbAnimator.getPos(), entity.limbAnimator.getSpeed(), (float) entity.age + MinecraftClient.getInstance().getRenderTickCounter().getTickDelta(true), contextModel.head.yaw, contextModel.head.roll);
                if (ModDyeablePieces.dyeableCapes.containsKey(capeDataComponent.getCape())) {
                    CapeRenderer.renderDyeableCape(matrices, vertexConsumers, light, stack, capeModel, Identifier.of(MiddleEarth.MOD_ID, "textures/models/cape/" + capeDataComponent.cape().getName() + ".png"), true);
                    if (ModDyeablePieces.dyeableCapes.get(capeDataComponent.cape()).booleanValue()){
                        ArmorRenderer.renderPart(matrices, vertexConsumers, light, stack, capeModel, Identifier.of(MiddleEarth.MOD_ID, "textures/models/cape/" + capeDataComponent.cape().getName() + "_overlay.png"));
                    }
                } else {
                    ArmorRenderer.renderPart(matrices, vertexConsumers, light, stack, capeModel, Identifier.of(MiddleEarth.MOD_ID, "textures/models/cape/" + capeDataComponent.cape().getName() + ".png"));
                }}
        }
    }
}
