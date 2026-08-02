package net.sevenstars.middleearth.mixin.client;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.sevenstars.middleearth.client.ItemModelRenderStateClient;
import net.sevenstars.middleearth.config.ModClientConfigs;
import net.sevenstars.middleearth.world.dimension.ModDimensions;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ItemRenderer.class)
public abstract class ItemRendererMixin {
    @ModifyExpressionValue(
            method = "renderStatic(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/ItemDisplayContext;ZLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;Lnet/minecraft/world/level/Level;III)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/renderer/entity/ItemRenderer;getModel(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/level/Level;Lnet/minecraft/world/entity/LivingEntity;I)Lnet/minecraft/client/resources/model/BakedModel;"
            )
    )
    private BakedModel middleEarth$resolveItemModel(
            BakedModel original,
            LivingEntity entity,
            ItemStack stack,
            ItemDisplayContext displayContext,
            boolean leftHand,
            PoseStack poseStack,
            MultiBufferSource bufferSource,
            Level level
    ) {
        return ItemModelRenderStateClient.resolve(original, stack, displayContext, level, entity);
    }

    @ModifyVariable(
            method = "getArmorFoilBuffer",
            at = @At("HEAD"),
            argsOnly = true,
            index = 2
    )
    private static boolean middleEarth$disableArmorFoilInMiddleEarth(boolean hasFoil) {
        return shouldRenderFoil(hasFoil);
    }

    @ModifyVariable(
            method = {"getFoilBuffer", "getFoilBufferDirect"},
            at = @At("HEAD"),
            argsOnly = true,
            index = 3
    )
    private static boolean middleEarth$disableItemFoilInMiddleEarth(boolean hasFoil) {
        return shouldRenderFoil(hasFoil);
    }

    private static boolean shouldRenderFoil(boolean hasFoil) {
        Minecraft minecraft = Minecraft.getInstance();
        return hasFoil && (!ModClientConfigs.DISABLE_GLINT
                || minecraft.level == null
                || !ModDimensions.isInMiddleEarth(minecraft.level));
    }
}
