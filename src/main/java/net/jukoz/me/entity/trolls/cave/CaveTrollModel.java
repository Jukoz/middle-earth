package net.jukoz.me.entity.trolls.cave;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.jukoz.me.entity.balrog.BalrogAnimations;
import net.jukoz.me.entity.trolls.TrollAnimations;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.CrossbowPosing;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Arm;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;

@Environment(value= EnvType.CLIENT)
public class CaveTrollModel extends SinglePartEntityModel<CaveTrollEntity> {
    private final ModelPart root;
    private final ModelPart body;
    private final ModelPart head;
    private final ModelPart boneJaw;
    private final ModelPart legRight;
    private final ModelPart legLeft;
    private final ModelPart armLeft;
    private final ModelPart armRight;

    private static final float ROTATION_SPEED = 0.8f;

    public CaveTrollModel(ModelPart root) {
        this.root = root;
        this.body = root.getChild("body");
        this.head = root.getChild("head");
        this.boneJaw = head.getChild("boneJaw");
        this.legRight = root.getChild("legRight");
        this.legLeft = root.getChild("legLeft");
        this.armLeft = root.getChild("armLeft");
        this.armRight = root.getChild("armRight");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData body = modelPartData.addChild("body", ModelPartBuilder.create().uv(66, 33).cuboid(-9.0F, -35.0F, -7.0F, 18.0F, 3.0F, 14.0F, new Dilation(0.0F))
                .uv(0, 0).cuboid(-14.0F, -32.0F, -7.0F, 28.0F, 16.0F, 17.0F, new Dilation(0.0F))
                .uv(0, 33).cuboid(-12.0F, -16.0F, -9.0F, 24.0F, 16.0F, 18.0F, new Dilation(0.0F))
                .uv(84, 122).cuboid(-10.0F, -29.0F, -10.0F, 20.0F, 13.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 1.0F, 0.0F));

        ModelPartData head = modelPartData.addChild("head", ModelPartBuilder.create().uv(0, 67).cuboid(-7.5F, -7.8F, -16.0F, 15.0F, 8.0F, 15.0F, new Dilation(0.0F))
                .uv(118, 0).cuboid(-6.5F, -7.8F, -18.0F, 13.0F, 5.0F, 2.0F, new Dilation(0.0F))
                .uv(0, 0).cuboid(-2.5F, -2.8F, -17.0F, 5.0F, 3.0F, 1.0F, new Dilation(0.0F))
                .uv(116, 107).cuboid(-6.0F, 0.2F, -16.0F, 12.0F, 3.0F, 12.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -26.0F, -6.0F));

        ModelPartData boneJaw = head.addChild("boneJaw", ModelPartBuilder.create().uv(84, 50).cuboid(-6.0F, 0.75F, -13.0F, 12.0F, 3.0F, 14.0F, new Dilation(0.0F))
                .uv(117, 91).cuboid(-5.5F, -1.25F, -12.5F, 11.0F, 2.0F, 13.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 2.45F, -5.0F));

        ModelPartData legLeft = modelPartData.addChild("legLeft", ModelPartBuilder.create().uv(106, 67).cuboid(-5.5F, -2.0F, -5.5F, 11.0F, 13.0F, 11.0F, new Dilation(0.0F)), ModelTransform.pivot(5.5F, 0.0F, 0.0F));

        ModelPartData kneeLeft = legLeft.addChild("kneeLeft", ModelPartBuilder.create().uv(0, 118).cuboid(3.6F, -13.0F, -5.0F, 10.0F, 13.0F, 10.0F, new Dilation(0.0F))
                .uv(73, 0).cuboid(3.6F, -5.0F, -7.0F, 10.0F, 5.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(-8.6F, 24.0F, 0.0F));

        ModelPartData legRight = modelPartData.addChild("legRight", ModelPartBuilder.create().uv(84, 95).cuboid(-5.5F, -2.0F, -5.5F, 11.0F, 13.0F, 11.0F, new Dilation(0.0F)), ModelTransform.pivot(-5.5F, 0.0F, 0.0F));

        ModelPartData kneeRight = legRight.addChild("kneeRight", ModelPartBuilder.create().uv(45, 67).cuboid(-5.0F, 19.0F, -7.0F, 10.0F, 5.0F, 2.0F, new Dilation(0.0F))
                .uv(118, 23).cuboid(-5.0F, 11.0F, -5.0F, 10.0F, 13.0F, 10.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData armRight = modelPartData.addChild("armRight", ModelPartBuilder.create().uv(60, 67).cuboid(-11.0F, -4.0F, -6.0F, 11.0F, 16.0F, 12.0F, new Dilation(0.0F)), ModelTransform.pivot(-14.0F, -26.0F, 1.0F));

        ModelPartData forearmRight = armRight.addChild("forearmRight", ModelPartBuilder.create().uv(90, 0).cuboid(-11.5F, 9.2F, -5.5F, 9.0F, 23.0F, 10.0F, new Dilation(0.0F)), ModelTransform.pivot(1.5F, 2.8F, 0.5F));

        ModelPartData armLeft = modelPartData.addChild("armLeft", ModelPartBuilder.create().uv(0, 90).cuboid(0.0F, -4.0F, -6.0F, 11.0F, 16.0F, 12.0F, new Dilation(0.0F)), ModelTransform.pivot(14.0F, -26.0F, 1.0F));

        ModelPartData forearmLeft = armLeft.addChild("forearmLeft", ModelPartBuilder.create().uv(46, 95).cuboid(-4.5F, 0.2F, -5.5F, 9.0F, 23.0F, 10.0F, new Dilation(0.0F)), ModelTransform.pivot(5.5F, 11.8F, 0.5F));
        return TexturedModelData.of(modelData, 256, 256);
    }

    @Override
    public void setAngles(CaveTrollEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.getPart().traverse().forEach(ModelPart::resetTransform);
        this.setHeadAngles(netHeadYaw, headPitch);

        this.animateMovement(TrollAnimations.MOVING, limbSwing, limbSwingAmount, 2f, 2.5f);
        this.updateAnimation(entity.idleAnimationState, TrollAnimations.IDLE, ageInTicks, 1f);
        this.updateAnimation(entity.attackAnimationState, TrollAnimations.ATTACK, ageInTicks, 1f);
        this.updateAnimation(entity.chargeAnimationState, TrollAnimations.CHARGING, ageInTicks, 1f);
    }

    private void setHeadAngles(float headYaw, float headPitch) {
        headYaw = MathHelper.clamp(headYaw, -30.0F, 30.0F);
        headPitch = MathHelper.clamp(headPitch, -25.0F, 40.0F);

        this.head.yaw = headYaw * 0.017453292F;
        this.head.pitch = headPitch * 0.017453292F;
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
        root.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
    }

    @Override
    public ModelPart getPart() {
        return this.root;
    }
}