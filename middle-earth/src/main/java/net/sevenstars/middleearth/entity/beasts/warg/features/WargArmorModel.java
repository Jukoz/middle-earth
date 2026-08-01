package net.sevenstars.middleearth.entity.beasts.warg.features;

import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.sevenstars.middleearth.entity.beasts.warg.WargEntityRenderState;
import net.sevenstars.middleearth.entity.beasts.warg.WargModel;

public class WargArmorModel extends WargModel {
        private final ModelPart warg;
        private final ModelPart head;
        public WargArmorModel(ModelPart root) {
                super(root);

                this.warg = root.getChild("root");
                this.head = warg.getChild(EntityModelPartNames.BODY).getChild("upper_body").getChild("body_no_legs").getChild(EntityModelPartNames.HEAD);
        }

        public static TexturedModelData getTexturedModelData() {
                ModelData modelData = getArmorModelData();
                ModelPartData upper_body = modelData.getRoot().getChild("root").getChild(EntityModelPartNames.BODY).getChild("upper_body");
                ModelPartData body_no_legs = upper_body.getChild("body_no_legs");
                ModelPartData body_armor = body_no_legs.getChild("body_fur").getChild("body_armor");
                ModelPartData head = body_no_legs.getChild(EntityModelPartNames.HEAD);
                ModelPartData front_armor = body_armor.getChild("front_armor");

                front_armor.addChild("front_armor_cube", ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-7.0F, -6.4F, -6.0F, 14.0F, 12.0F, 12.0F, new Dilation(0.3F))
                        .uv(52, 47).cuboid(-4.0F, -6.5F, -6.0F, 11.0F, 8.0F, 12.0F, new Dilation(0.5F)), ModelTransform.origin(0F, 0F, 0F));

                ModelPartData left_chains = front_armor.getChild("left_chains");
                left_chains.addChild("chains_r1", ModelPartBuilder.create().uv(34, 69).cuboid(-6.5F, -2.0F, 1.5F, 13.0F, 10.0F, 0.0F, new Dilation(0.1F)), ModelTransform.origin(0.0F, 0.0F, 0.0F));

                ModelPartData right_chains = front_armor.getChild("right_chains");
                right_chains.addChild("chains_r2", ModelPartBuilder.create().uv(34, 69).cuboid(-6.5F, -3.0F, -1.5F, 13.0F, 10.0F, 0.0F, new Dilation(0.1F)), ModelTransform.origin(0.0F, 0.0F, 0.0F));

                ModelPartData back_armor = body_no_legs.getChild("back_armor");
                back_armor.addChild("backlegplate_r1", ModelPartBuilder.create().uv(0, 45).cuboid(-8.2F, -6.5F, -5.0F, 15.0F, 9.0F, 11.0F, new Dilation(0.7F)), ModelTransform.of(-0.5F, 0.0F, 0.5F, 3.1416F, 0.0F, -3.1416F));
                back_armor.addChild("backarmor_r1", ModelPartBuilder.create().uv(0, 24).cuboid(-7.8F, -6.5F, -5.0F, 14.0F, 10.0F, 11.0F, new Dilation(0.3F)), ModelTransform.of(-0.7F, -0.9F, 0.5F, 3.1416F, 0.0F, -3.1416F));

                ModelPartData head_armor = head.getChild("head_armor");
                head_armor.addChild("Lbone_snout_r1", ModelPartBuilder.create().uv(60, 67).cuboid(-7.2076F, -0.8956F, -2.3986F, 11.0F, 6.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(6.4218F, 0.1634F, -4.6014F, -2.7477F, -0.1032F, -3.0907F));
                head_armor.addChild("Lbone_snout_r2", ModelPartBuilder.create().uv(60, 67).cuboid(-7.2076F, -0.8956F, 2.3986F, 11.0F, 6.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(6.4218F, 0.1634F, 3.6014F, 2.7477F, 0.1032F, -3.0907F));
                head_armor.addChild("underhelmet_r1", ModelPartBuilder.create().uv(62, 4).cuboid(-3.2F, -6.6623F, -5.3552F, 11.0F, 9.0F, 10.0F, new Dilation(-0.3F)), ModelTransform.of(5.859F, 2.9485F, -0.9F, -3.1416F, 0.0F, 3.1416F));
                head_armor.addChild("helmet_r1", ModelPartBuilder.create().uv(50, 23).cuboid(-3.2F, -6.2623F, -5.3552F, 11.0F, 13.0F, 11.0F, new Dilation(-0.1F)), ModelTransform.of(5.859F, 0.9485F, -0.4F, -3.1416F, 0.0F, 3.1416F));

                ModelPartData snout2 = head_armor.getChild("snout2");
                snout2.addChild("snout_r1", ModelPartBuilder.create().uv(0, 65).cuboid(-6.2076F, 0.1044F, 1.6F, 13.0F, 5.0F, 4.0F, new Dilation(0.1F)), ModelTransform.of(0.0F, 0.0F, 0.0F, -3.1416F, 0.0F, 3.1416F));
                snout2.addChild("snout_r2", ModelPartBuilder.create().uv(52, 18).cuboid(-5.2076F, 3.2829F, 1.6F, 1.0F, 1.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -4.2F, 0.0F, -3.1416F, 0.0F, 3.1416F));

                return TexturedModelData.of(modelData, 128, 128);
        }

        public static ModelData getArmorModelData() {
                ModelData modelData = getEmptyModelData();
                ModelPartData upper_body = modelData.getRoot().getChild("root").getChild(EntityModelPartNames.BODY).getChild("upper_body");
                ModelPartData body_no_legs = upper_body.getChild("body_no_legs");
                ModelPartData head = body_no_legs.getChild(EntityModelPartNames.HEAD);
                ModelPartData body_armor = body_no_legs.getChild("body_fur").addChild("body_armor", ModelPartBuilder.create(), ModelTransform.origin(-6.5F, -2.75F, 1.5F));

                ModelPartData front_armor = body_armor.addChild("front_armor", ModelPartBuilder.create(), ModelTransform.origin(7.8F, 1.5F, -1.0F));

                ModelPartData left_chains = front_armor.addChild("left_chains", ModelPartBuilder.create(), ModelTransform.origin(0.5F, -3.5F, 5.3F));
                ModelPartData right_chains = front_armor.addChild("right_chains", ModelPartBuilder.create(), ModelTransform.origin(0.5F, -2.5F, -5.3F));

                ModelPartData back_armor = body_no_legs.addChild("back_armor", ModelPartBuilder.create(), ModelTransform.origin(-13.7F, 1.25F, 0.5F));

                ModelPartData head_armor = head.addChild("head_armor", ModelPartBuilder.create(), ModelTransform.origin(-0.1142F, 1.8638F, 0.5F));

                ModelPartData snout2 = head_armor.addChild("snout2", ModelPartBuilder.create(), ModelTransform.origin(7.3218F, -3.3967F, 3.1F));

                return modelData;
        }

}