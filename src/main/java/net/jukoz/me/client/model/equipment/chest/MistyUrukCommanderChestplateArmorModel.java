package net.jukoz.me.client.model.equipment.chest;

import net.jukoz.me.MiddleEarth;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;

public class MistyUrukCommanderChestplateArmorModel<T extends LivingEntity> extends CustomChestplateModel<T> {

    public final ModelPart shoulderAddon;

    public MistyUrukCommanderChestplateArmorModel(ModelPart root) {
        super(root);
        shoulderAddon = root.getChild("right_arm").getChild("shoulder_addon");

        CHESTPLATE_ADDON_TEXTURE = new Identifier(MiddleEarth.MOD_ID, "textures/models/armor/misty_hobgoblin_commander_chestplate_addon.png");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();

        modelPartData.addChild(EntityModelPartNames.HAT, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(EntityModelPartNames.HEAD, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData body = modelPartData.addChild(EntityModelPartNames.BODY, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData skull2 = body.addChild("skull2", ModelPartBuilder.create(), ModelTransform.of(-3.0F, 8.0F, 0.0F, 0.2986F, 0.0651F, -0.5138F));
        skull2.addChild("cube_r5", ModelPartBuilder.create().uv(0, 0).mirrored().cuboid(-2.5671F, -3.0474F, 2.0F, 8.0F, 8.0F, 8.0F, new Dilation(-1.2F)).mirrored(false), ModelTransform.of(8.0F, -28.0F, 7.0F, -0.3491F, 0.0F, 0.3054F));
        skull2.addChild("cube_r6", ModelPartBuilder.create().uv(0, 0).mirrored().cuboid(-4.0F, -4.0F, -4.0F, 8.0F, 8.0F, 8.0F, new Dilation(-1.0F)).mirrored(false), ModelTransform.of(6.332F, -18.3648F, 10.1039F, -0.4181F, 0.5692F, 0.0704F));

        ModelPartData cloth2 = skull2.addChild("cloth2", ModelPartBuilder.create(), ModelTransform.of(-6.0F, 0.0F, 0.0F, 0.3011F, 0.0522F, 0.1388F));
        cloth2.addChild("cube_r7", ModelPartBuilder.create().uv(0, 16).mirrored().cuboid(0.1819F, 2.4589F, 5.1122F, 2.0F, 9.0F, 0.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(8.0F, -18.5F, 12.0F, -0.6114F, 0.1727F, 0.3707F));

        ModelPartData spears2 = skull2.addChild("spears2", ModelPartBuilder.create(), ModelTransform.of(0.0F, -0.5F, 3.0F, 0.0F, 0.0F, 0.1309F));
        spears2.addChild("cube_r8", ModelPartBuilder.create().uv(32, 0).mirrored().cuboid(1.4329F, -23.6652F, 2.6678F, 2.0F, 35.0F, 2.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(0.0F, -10.0F, 0.0F, -0.3491F, 0.0F, 0.1745F));

        ModelPartData skull3 = body.addChild("skull3", ModelPartBuilder.create(), ModelTransform.of(3.0F, 8.0F, 0.0F, 0.2986F, -0.0651F, 0.5138F));
        skull3.addChild("cube_r9", ModelPartBuilder.create().uv(0, 0).cuboid(-5.4329F, -3.0474F, 2.0F, 8.0F, 8.0F, 8.0F, new Dilation(-0.8F)), ModelTransform.of(-8.0F, -28.0F, 7.0F, -0.3491F, 0.0F, -0.3054F));
        skull3.addChild("cube_r10", ModelPartBuilder.create().uv(0, 0).cuboid(-4.0F, -4.0F, -4.0F, 8.0F, 8.0F, 8.0F, new Dilation(-1.5F)), ModelTransform.of(-6.332F, -18.3648F, 10.1039F, -0.4181F, -0.5692F, -0.0704F));

        ModelPartData cloth = skull3.addChild("cloth", ModelPartBuilder.create(), ModelTransform.of(6.0F, 0.0F, 0.0F, 0.3011F, -0.0522F, -0.1388F));
        cloth.addChild("cube_r11", ModelPartBuilder.create().uv(0, 16).cuboid(-2.1819F, 1.4589F, 5.1122F, 2.0F, 9.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(-8.0F, -18.5F, 12.0F, -0.6114F, -0.1727F, -0.3707F));

        ModelPartData spears3 = skull3.addChild("spears3", ModelPartBuilder.create(), ModelTransform.of(0.0F, -0.5F, 3.0F, 0.0F, 0.0F, -0.1309F));
        spears3.addChild("cube_r12", ModelPartBuilder.create().uv(32, 0).cuboid(-3.4329F, -23.6652F, 2.6678F, 2.0F, 35.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -10.0F, 0.0F, -0.3491F, 0.0F, -0.1745F));

        ModelPartData right_arm = modelPartData.addChild(EntityModelPartNames.RIGHT_ARM, ModelPartBuilder.create(),
                ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        right_arm.addChild("shoulder_addon", ModelPartBuilder.create()
                .uv(0, 0).cuboid(-3.0F, -2.0F, -2.0F, 3.0F, 5.0F, 0, new Dilation(0.0F)),
                ModelTransform.pivot(-3.0F, -4.0F, 2.0F));

        modelPartData.addChild(EntityModelPartNames.LEFT_ARM, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        modelPartData.addChild(EntityModelPartNames.RIGHT_LEG, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(EntityModelPartNames.LEFT_LEG, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        return TexturedModelData.of(modelData, 64, 64);
    }

    @Override
    public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {

    }
}