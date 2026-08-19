package net.sevenstars.middleearth.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.sevenstars.middleearth.client.renderer.armor.ArmorRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HumanoidArmorLayer.class)
public abstract class HumanoidArmorLayerMixin<T extends LivingEntity, M extends HumanoidModel<T>, A extends HumanoidModel<T>>
        extends RenderLayer<T, M> {
    protected HumanoidArmorLayerMixin(RenderLayerParent<T, M> renderer) {
        super(renderer);
    }

    @Inject(
            method = "renderArmorPiece(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/entity/EquipmentSlot;ILnet/minecraft/client/model/HumanoidModel;FFFFFF)V",
            at = @At("HEAD"),
            cancellable = true
    )
    private void middleEarth$renderCustomArmor(PoseStack poseStack, MultiBufferSource bufferSource,
                                                T entity, EquipmentSlot slot, int light, A contextModel,
                                                float limbSwing, float limbSwingAmount, float partialTick,
                                                float ageInTicks, float netHeadYaw, float headPitch,
                                                CallbackInfo ci) {
        ItemStack stack = entity.getItemBySlot(slot);
        if (!(stack.getItem() instanceof ArmorItem armorItem) || armorItem.getEquipmentSlot() != slot) {
            return;
        }

        ArmorRenderer renderer = ArmorRenderer.get(stack.getItem());
        if (renderer == null) {
            return;
        }

        this.getParentModel().copyPropertiesTo(contextModel);
        renderer.render(poseStack, bufferSource, stack, entity, slot, light,
                (HumanoidModel<LivingEntity>) (HumanoidModel<?>) contextModel);
        ci.cancel();
    }
}
