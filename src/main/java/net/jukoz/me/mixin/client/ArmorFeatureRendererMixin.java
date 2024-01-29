package net.jukoz.me.mixin.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.item.ModEquipmentItems;
import net.jukoz.me.model.equipment.CloakCapeModel;
import net.jukoz.me.model.equipment.RohanTier4ArmourModel;
import net.jukoz.me.utils.IntToRGB;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.ArmorFeatureRenderer;
import net.minecraft.client.render.entity.feature.ElytraFeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.*;
import net.minecraft.item.trim.ArmorTrim;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.awt.*;

@Environment(EnvType.CLIENT)
@Mixin(ArmorFeatureRenderer.class)
public abstract class ArmorFeatureRendererMixin<T extends LivingEntity, M extends BipedEntityModel<T>, A extends BipedEntityModel<T>> extends FeatureRenderer<T, M> {
    private static final Identifier ROHAN_4_HELMET_ADDON = new Identifier(MiddleEarth.MOD_ID, "textures/models/armor/rohan_scale_helmet_addon.png");
    private static final Identifier ROHAN_SCALE_LAYER_0 = new Identifier(MiddleEarth.MOD_ID, "textures/models/armor/rohan_scale_layer_0.png");

    private final RohanTier4ArmourModel<T> rohanTier4ArmourModel = new RohanTier4ArmourModel<>(RohanTier4ArmourModel.getTexturedModelData().createModel());

    public ArmorFeatureRendererMixin(FeatureRendererContext<T, M> context) {
        super(context);
    }

    @Inject(method = "render", at = @At("TAIL"), cancellable = true)
    private void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, T entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch, CallbackInfo info) {
        {
            ItemStack itemStack = entity.getEquippedStack(EquipmentSlot.CHEST);
            Item item = itemStack.getItem();

            if (item == ModEquipmentItems.ROHAN_SCALE_CHESTPLATE) {
                renderArmour(ROHAN_SCALE_LAYER_0, itemStack, matrices, vertexConsumers, light, entity, limbAngle, limbDistance, animationProgress, headYaw, headPitch);
                info.cancel();
            }
        }}

    private void renderArmour(Identifier renderLayer, ItemStack itemStack, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, T entity, float limbAngle,
                            float limbDistance, float animationProgress, float headYaw, float headPitch) {
        matrices.push();
        this.getContextModel().copyStateTo(this.rohanTier4ArmourModel);
        this.rohanTier4ArmourModel.setAngles(entity, limbAngle, limbDistance, animationProgress, headYaw, headPitch);
        VertexConsumer vertexConsumer = ItemRenderer.getArmorGlintConsumer(vertexConsumers, RenderLayer.getArmorCutoutNoCull(renderLayer), false, itemStack.hasGlint());
        this.rohanTier4ArmourModel.render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV, 1.0f, 1.0f, 1.0f, 1.0F);
        matrices.pop();
    }

}
