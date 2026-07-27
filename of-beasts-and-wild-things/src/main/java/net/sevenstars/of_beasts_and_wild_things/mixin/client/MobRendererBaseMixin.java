package net.sevenstars.of_beasts_and_wild_things.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.sevenstars.of_beasts_and_wild_things.compat.farm.client.VanillaFarmAnimalRendererBridge;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(MobRenderer.class)
public abstract class MobRendererBaseMixin<T extends Mob, M extends EntityModel<T>>
        extends LivingEntityRenderer<T, M> implements VanillaFarmAnimalRendererBridge {
    protected MobRendererBaseMixin(
            EntityRendererProvider.Context context,
            M model,
            float shadowRadius
    ) {
        super(context, model, shadowRadius);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void wildThings$renderBase(
            LivingEntity entity,
            float entityYaw,
            float partialTick,
            PoseStack poseStack,
            MultiBufferSource buffer,
            int packedLight
    ) {
        super.render((T) entity, entityYaw, partialTick, poseStack, buffer, packedLight);
    }
}
