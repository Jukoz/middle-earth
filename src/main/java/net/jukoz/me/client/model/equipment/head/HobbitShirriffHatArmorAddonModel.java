package net.jukoz.me.client.model.equipment.head;

import net.jukoz.me.MiddleEarth;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;

public class HobbitShirriffHatArmorAddonModel<T extends LivingEntity> extends HelmetAddonModel<T> {

    public final ModelPart shirriffHat;

    public HobbitShirriffHatArmorAddonModel(ModelPart root) {
        super(root);
        shirriffHat = root.getChild("head").getChild("shirriff_hat");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();

        modelPartData.addChild(EntityModelPartNames.HAT, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData head = modelPartData.addChild(EntityModelPartNames.HEAD, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        head.addChild("shirriff_hat", ModelPartBuilder.create()
                .uv(-6, 0).cuboid(-8.0F, -8.9F, -8.0F, 16.0F, 0.0F, 16.0F, new Dilation(0.0F))
                .uv(0, 16).cuboid(-4.0F, -12.4F, -4.0F, 8.0F, 3.0F, 8.0F, new Dilation(0.5F))
                .uv(32, 16).cuboid(-4.0F, -12.7F, -4.0F, 8.0F, 3.0F, 8.0F, new Dilation(0.6F)),
                ModelTransform.pivot(0.0F, 4.0F, 0.0F));

        head.addChild("feather", ModelPartBuilder.create().uv(0, 30).cuboid(-0.1F, -0.4F, -6.5F, 0.0F, 8.0F, 10.0F, new Dilation(1.0F)), ModelTransform.of(4.1F, -13.6F, 4.4F, 0.1327F, 0.0379F, 0.0433F));


        modelPartData.addChild(EntityModelPartNames.BODY, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(EntityModelPartNames.RIGHT_ARM, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(EntityModelPartNames.LEFT_ARM, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        modelPartData.addChild(EntityModelPartNames.RIGHT_LEG, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(EntityModelPartNames.LEFT_LEG, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        return TexturedModelData.of(modelData, 64, 64);
    }

    @Override
    public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {

    }
}