package net.jukoz.me.client.model.equipment.head.hoods.unarmored;

import net.jukoz.me.client.model.equipment.head.hoods.CloakHoodModel;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.entity.LivingEntity;

public class UnarmoredFurHoodDownModel<T extends LivingEntity> extends CloakHoodModel<T> {

    public UnarmoredFurHoodDownModel(ModelPart root) {
        super(root);
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();

        ModelPartData hat = modelPartData.addChild(EntityModelPartNames.HAT, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        /*
        ModelPartData hood = hat.addChild("hood", ModelPartBuilder.create()
                .uv(96, 0).cuboid(-4.0F, -6.0F, 0.75F, 8.0F, 8.0F, 8.0F, new Dilation(1.3F))
                .uv(64, 0).cuboid(-4.0F, -5.91F, 0.75F, 8.0F, 8.0F, 8.0F, new Dilation(1.0F)), ModelTransform.of(0.0F, 0.0F, -6.0F, -1.1345F, 0.0F, 0.0F));

        ModelPartData hoodFurs = hood.addChild("hood_furs", ModelPartBuilder.create(), ModelTransform.of(-0.144F, 4.2857F, -1.716F, -1.5708F, 0.0F, 0.0F));

        hoodFurs.addChild("top", ModelPartBuilder.create().uv(112, 29).cuboid(-2.872F, -11.2857F, -5.3F, 6.0F, 1.0F, 2.0F, new Dilation(0.5F))
                .uv(112, 26).cuboid(-2.856F, -11.2857F, -5.3F, 6.0F, 1.0F, 2.0F, new Dilation(0.6F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        hoodFurs.addChild("bottom", ModelPartBuilder.create().uv(112, 20).cuboid(-2.75F, -2.2857F, -5.3F, 6.0F, 1.0F, 2.0F, new Dilation(0.5F))
                .uv(112, 23).cuboid(-2.718F, -2.2857F, -5.3F, 6.0F, 1.0F, 2.0F, new Dilation(0.6F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        hoodFurs.addChild("left", ModelPartBuilder.create().uv(100, 20).cuboid(3.6F, -11.2857F, -5.3F, 1.0F, 8.0F, 2.0F, new Dilation(0.5F))
                .uv(94, 20).cuboid(3.6F, -11.2857F, -5.3F, 1.0F, 8.0F, 2.0F, new Dilation(0.6F)), ModelTransform.pivot(0.25F, 0.0F, 0.0F));

        hoodFurs.addChild("right", ModelPartBuilder.create().uv(100, 20).mirrored().cuboid(-4.35F, -11.2857F, -5.3F, 1.0F, 8.0F, 2.0F, new Dilation(0.5F)).mirrored(false)
                .uv(106, 20).cuboid(-4.35F, -11.2857F, -5.3F, 1.0F, 8.0F, 2.0F, new Dilation(0.6F)), ModelTransform.pivot(-0.25F, 0.0F, 0.0F));
        */

        ModelPartData cape_armored = hat.addChild("hood", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, -1.0F, 0.016F));

        ModelPartData Hood2 = cape_armored.addChild("Hood2", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, -0.0F, 0.0F));

        ModelPartData FurHood2 = Hood2.addChild("FurHood2", ModelPartBuilder.create(), ModelTransform.of(0.0F, -4.5F, -3.5F, 1.5708F, 0.0F, 0.0F));

        ModelPartData down2 = FurHood2.addChild("down2", ModelPartBuilder.create().uv(96, 0).cuboid(-4.0F, -6.0F, 0.75F, 8.0F, 8.0F, 8.0F, new Dilation(1.3F))
                .uv(64, 0).cuboid(-4.0F, -5.91F, 0.75F, 8.0F, 8.0F, 8.0F, new Dilation(1.0F)), ModelTransform.of(0.0F, 0.0F, -6.0F, -1.1345F, 0.0F, 0.0F));

        ModelPartData hooddown2 = down2.addChild("hooddown2", ModelPartBuilder.create(), ModelTransform.of(-0.144F, 4.2857F, -1.716F, -1.5708F, 0.0F, 0.0F));

        ModelPartData top4 = hooddown2.addChild("top4", ModelPartBuilder.create().uv(112, 29).cuboid(-2.872F, -11.2857F, -5.3F, 6.0F, 1.0F, 2.0F, new Dilation(0.5F))
                .uv(112, 26).cuboid(-2.856F, -11.2857F, -5.3F, 6.0F, 1.0F, 2.0F, new Dilation(0.6F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData bottom4 = hooddown2.addChild("bottom4", ModelPartBuilder.create().uv(112, 20).cuboid(-2.75F, -2.2857F, -5.3F, 6.0F, 1.0F, 2.0F, new Dilation(0.5F))
                .uv(112, 23).cuboid(-2.718F, -2.2857F, -5.3F, 6.0F, 1.0F, 2.0F, new Dilation(0.6F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData left5 = hooddown2.addChild("left5", ModelPartBuilder.create().uv(100, 20).cuboid(3.6F, -11.2857F, -5.3F, 1.0F, 8.0F, 2.0F, new Dilation(0.5F))
                .uv(94, 20).cuboid(3.6F, -11.2857F, -5.3F, 1.0F, 8.0F, 2.0F, new Dilation(0.6F)), ModelTransform.pivot(0.25F, 0.0F, 0.0F));

        ModelPartData right5 = hooddown2.addChild("right5", ModelPartBuilder.create().uv(100, 20).mirrored().cuboid(-4.35F, -11.2857F, -5.3F, 1.0F, 8.0F, 2.0F, new Dilation(0.5F)).mirrored(false)
                .uv(106, 20).cuboid(-4.35F, -11.2857F, -5.3F, 1.0F, 8.0F, 2.0F, new Dilation(0.6F)), ModelTransform.pivot(-0.25F, 0.0F, 0.0F));

        modelPartData.addChild(EntityModelPartNames.HEAD, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        modelPartData.addChild(EntityModelPartNames.BODY, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        modelPartData.addChild(EntityModelPartNames.RIGHT_ARM, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(EntityModelPartNames.LEFT_ARM, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        modelPartData.addChild(EntityModelPartNames.RIGHT_LEG, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(EntityModelPartNames.LEFT_LEG, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        return TexturedModelData.of(modelData, 128, 128);
    }
}
