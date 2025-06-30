package net.sevenstars.middleearth.entity.npcs.features.ear;

import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.util.math.MathHelper;
import net.sevenstars.middleearth.entity.npcs.NpcEntityRenderState;

public class EarModel  extends EntityModel<NpcEntityRenderState> {

    public final ModelPart planeSideGroup;
    public final ModelPart planeAngledLeft;
    public final ModelPart planeAngledRight;

    public final ModelPart cubeSideGroup;
    public final ModelPart cubeLeft;
    public final ModelPart cubeRight;

    public final ModelPart planeFlatGroup;
    public final ModelPart planeFlatLeft;
    public final ModelPart planeFlatRight;


    public EarModel(ModelPart modelPart) {
        super(modelPart);

        this.planeSideGroup = modelPart.getChild("planeSideGroup");
        this.planeAngledLeft = this.planeSideGroup.getChild("planeAngledLeft");
        this.planeAngledRight = this.planeSideGroup.getChild("planeAngledRight");

        this.planeFlatGroup = modelPart.getChild("planeFlatGroup");
        this.planeFlatLeft = this.planeFlatGroup.getChild("planeFlatLeft");
        this.planeFlatRight = this.planeFlatGroup.getChild("planeFlatRight");

        this.cubeSideGroup = modelPart.getChild("cubeSideGroup");
        this.cubeRight = this.cubeSideGroup.getChild("cubeRight");
        this.cubeLeft = this.cubeSideGroup.getChild("cubeLeft");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();

        ModelPartData cubes = modelPartData.addChild("cubeSideGroup", ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));
        cubes.addChild("cubeLeft", ModelPartBuilder.create().uv(12, 0).cuboid(-5.0f, -4f, -1f, 1, 2, 1, Dilation.NONE), ModelTransform.origin(0.0F, 0.0F, 0.0F));
        cubes.addChild("cubeRight", ModelPartBuilder.create().uv(12, 0).cuboid(4.0f, -4f, -1f, 1, 2, 1, Dilation.NONE), ModelTransform.origin(0.0F, 0.0F, 0.0F));

        ModelPartData planeSides = modelPartData.addChild("planeSideGroup", ModelPartBuilder.create(), ModelTransform.origin(0.0F, -4f, -2.0f));
        planeSides.addChild("planeAngledRight", ModelPartBuilder.create().uv(0, 2).cuboid(0f,-5.0f,0f, 0, 5, 2, Dilation.NONE).mirrored(), ModelTransform.origin(-4f, 0.0f, 0.0F));
        planeSides.addChild("planeAngledLeft", ModelPartBuilder.create().uv(0, 2).cuboid(0f,-5.0f,0f, 0, 5, 2, Dilation.NONE), ModelTransform.origin(4f, 0.0F, 0.0F));

        ModelPartData planeFlatSides = modelPartData.addChild("planeFlatGroup", ModelPartBuilder.create(), ModelTransform.origin(0.0F, -3f, -1.0f));
        planeFlatSides.addChild("planeFlatLeft", ModelPartBuilder.create().uv(0, 10).cuboid(0f,-5.0f,0f, 3, 7, 0, Dilation.NONE), ModelTransform.origin(4f, 0.0F, 0.0F));
        planeFlatSides.addChild("planeFlatRight", ModelPartBuilder.create().uv(0, 10).cuboid(0f,-5.0f,0f, 3, 7, 0, Dilation.NONE).mirrored(), ModelTransform.origin(-4f, 0.0f, 0.0F));

        return TexturedModelData.of(modelData, 32, 32);
    }

    @Override
    public void setAngles(NpcEntityRenderState state) {
        super.setAngles(state);

        // Taken from BipedEntityModel.class
        float f = state.leaningPitch;
        boolean bl = state.isGliding;

        this.planeFlatGroup.yaw = state.relativeHeadYaw * 0.017453292F;
        this.planeFlatGroup.pitch = state.pitch * 0.017453292F;

        this.cubeSideGroup.yaw = state.relativeHeadYaw * 0.017453292F;
        this.cubeSideGroup.pitch = state.pitch * 0.017453292F;

        this.planeSideGroup.yaw = state.relativeHeadYaw * 0.017453292F;
        this.planeSideGroup.pitch = state.pitch * 0.017453292F;

        this.planeAngledLeft.yaw = (float)Math.toRadians(8);
        this.planeAngledLeft.roll = (float)Math.toRadians(20);
        this.planeAngledLeft.pitch = (float)Math.toRadians(-30);

        this.planeAngledRight.yaw = (float)Math.toRadians(-8);
        this.planeAngledRight.roll = (float)Math.toRadians(-20);
        this.planeAngledRight.pitch = (float)Math.toRadians(-30);


        this.planeFlatLeft.yaw = (float)Math.toRadians(-20);
        this.planeFlatRight.yaw = (float)Math.toRadians(-160);

        /*
        this.planeRight.yaw = -1.5708f;
        this.planeRight.pitch = 0.349066f;
        this.planeRight.roll = -0.785398f;
         */


        if (bl) {
            this.cubeSideGroup.pitch = -0.7853982F;
            this.planeSideGroup.pitch = -0.7853982F;
            this.planeFlatGroup.pitch = -0.7853982F;
        } else if (f > 0.0F) {
            this.cubeSideGroup.pitch = MathHelper.lerpAngleRadians(f, this.cubeSideGroup.pitch, -0.7853982F);
            this.planeSideGroup.pitch = MathHelper.lerpAngleRadians(f, this.planeSideGroup.pitch, -0.7853982F);
            this.planeFlatGroup.pitch = MathHelper.lerpAngleRadians(f, this.planeFlatGroup.pitch, -0.7853982F);
        }
    }
}
