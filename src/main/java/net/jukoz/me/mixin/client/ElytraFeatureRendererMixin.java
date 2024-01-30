package net.jukoz.me.mixin.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.client.model.equipment.CloakCapeModel;
import net.jukoz.me.item.ModEquipmentItems;
import net.jukoz.me.utils.IntToRGB;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.ElytraFeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.*;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


import java.awt.*;

@Environment(EnvType.CLIENT)
@Mixin(ElytraFeatureRenderer.class)
public abstract class ElytraFeatureRendererMixin<T extends LivingEntity, M extends EntityModel<T>> extends FeatureRenderer<T, M> {
    private static final Identifier CLOAK_CAPE_TEXTURE = new Identifier(MiddleEarth.MOD_ID, "textures/models/armor/cloak_features.png");
    private static final Identifier FUR_CLOAK_CAPE_TEXTURE = new Identifier(MiddleEarth.MOD_ID, "textures/models/armor/fur_cloak_features.png");
    private static final Identifier NAZGUL_CLOAK_CAPE_TEXTURE = new Identifier(MiddleEarth.MOD_ID, "textures/models/armor/nazgul_cloak_features.png");

    private final CloakCapeModel<T> cloakCapeModel = new CloakCapeModel<>(CloakCapeModel.getTexturedModelData().createModel());

    public ElytraFeatureRendererMixin(FeatureRendererContext<T, M> context) {
        super(context);
    }

    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    private void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, T entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch, CallbackInfo info) {
    {
        ItemStack itemStack = entity.getEquippedStack(EquipmentSlot.CHEST);
        Item item = itemStack.getItem();
        // Dyeable cloak
        if (item == ModEquipmentItems.CLOAK || item == ModEquipmentItems.TUNIC_CLOAK) {
            Color rgb = IntToRGB.ex(((DyeableItem)itemStack.getItem()).getColor(itemStack));
            renderCape(CLOAK_CAPE_TEXTURE, itemStack, matrices, vertexConsumers, light, entity, limbAngle, limbDistance,
                    animationProgress, headYaw, headPitch, rgb.getRed()/255f, rgb.getGreen()/255f, rgb.getBlue()/255f);
            info.cancel();
        }
        // Fur cloak
        else if (item == ModEquipmentItems.FUR_CLOAK || item == ModEquipmentItems.CHAINMAIL_FUR_CLOAK) {
            renderCape(FUR_CLOAK_CAPE_TEXTURE, itemStack, matrices, vertexConsumers, light, entity, limbAngle, limbDistance, animationProgress, headYaw, headPitch);
            info.cancel();
        }
        else if (item == ModEquipmentItems.NAZGUL_CLOAK) {
            renderCape(NAZGUL_CLOAK_CAPE_TEXTURE, itemStack, matrices, vertexConsumers, light, entity, limbAngle, limbDistance, animationProgress, headYaw, headPitch);
            info.cancel();
        }
    }}

    private void renderCape(Identifier renderLayer, ItemStack itemStack, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        renderCape(renderLayer, itemStack, matrices, vertexConsumers, light, entity, limbAngle, limbDistance, animationProgress, headYaw, headPitch, 1.0f, 1.0f, 1.0f);
    }

    private void renderCape(Identifier renderLayer, ItemStack itemStack, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch, float r, float g, float b) {
        matrices.push();
        this.getContextModel().copyStateTo(this.cloakCapeModel);
        this.cloakCapeModel.setAngles(entity, limbAngle, limbDistance, animationProgress, headYaw, headPitch);
        VertexConsumer vertexConsumer = ItemRenderer.getArmorGlintConsumer(vertexConsumers, RenderLayer.getArmorCutoutNoCull(renderLayer), false, itemStack.hasGlint());
        this.cloakCapeModel.render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV, r, g, b, 1.0F);
        matrices.pop();
    }
}
