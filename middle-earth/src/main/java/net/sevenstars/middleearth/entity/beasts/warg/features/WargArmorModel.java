package net.sevenstars.middleearth.entity.beasts.warg.features;

import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartNames;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.sevenstars.middleearth.entity.beasts.warg.WargModel;

public class WargArmorModel extends WargModel {
        private final ModelPart warg;
        private final ModelPart head;
        public WargArmorModel(ModelPart root) {
                super(root);

                this.warg = root.getChild("root");
                this.head = warg.getChild(PartNames.BODY).getChild("upper_body").getChild("body_no_legs").getChild(PartNames.HEAD);
        }

        public static LayerDefinition getTexturedModelData() {
                MeshDefinition modelData = getArmorModelData();
                PartDefinition upper_body = modelData.getRoot().getChild("root").getChild(PartNames.BODY).getChild("upper_body");
                PartDefinition body_no_legs = upper_body.getChild("body_no_legs");
                PartDefinition body_armor = body_no_legs.getChild("body_fur").getChild("body_armor");
                PartDefinition head = body_no_legs.getChild(PartNames.HEAD);
                PartDefinition front_armor = body_armor.getChild("front_armor");

                front_armor.addOrReplaceChild("front_armor_cube", CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-7.0F, -6.4F, -6.0F, 14.0F, 12.0F, 12.0F, new CubeDeformation(0.3F))
                        .texOffs(52, 47).addBox(-4.0F, -6.5F, -6.0F, 11.0F, 8.0F, 12.0F, new CubeDeformation(0.5F)), PartPose.offset(0F, 0F, 0F));

                PartDefinition left_chains = front_armor.getChild("left_chains");
                left_chains.addOrReplaceChild("chains_r1", CubeListBuilder.create().texOffs(34, 69).addBox(-6.5F, -2.0F, 1.5F, 13.0F, 10.0F, 0.0F, new CubeDeformation(0.1F)), PartPose.offset(0.0F, 0.0F, 0.0F));

                PartDefinition right_chains = front_armor.getChild("right_chains");
                right_chains.addOrReplaceChild("chains_r2", CubeListBuilder.create().texOffs(34, 69).addBox(-6.5F, -3.0F, -1.5F, 13.0F, 10.0F, 0.0F, new CubeDeformation(0.1F)), PartPose.offset(0.0F, 0.0F, 0.0F));

                PartDefinition back_armor = body_no_legs.getChild("back_armor");
                back_armor.addOrReplaceChild("backlegplate_r1", CubeListBuilder.create().texOffs(0, 45).addBox(-8.2F, -6.5F, -5.0F, 15.0F, 9.0F, 11.0F, new CubeDeformation(0.7F)), PartPose.offsetAndRotation(-0.5F, 0.0F, 0.5F, 3.1416F, 0.0F, -3.1416F));
                back_armor.addOrReplaceChild("backarmor_r1", CubeListBuilder.create().texOffs(0, 24).addBox(-7.8F, -6.5F, -5.0F, 14.0F, 10.0F, 11.0F, new CubeDeformation(0.3F)), PartPose.offsetAndRotation(-0.7F, -0.9F, 0.5F, 3.1416F, 0.0F, -3.1416F));

                PartDefinition head_armor = head.getChild("head_armor");
                head_armor.addOrReplaceChild("Lbone_snout_r1", CubeListBuilder.create().texOffs(60, 67).addBox(-7.2076F, -0.8956F, -2.3986F, 11.0F, 6.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(6.4218F, 0.1634F, -4.6014F, -2.7477F, -0.1032F, -3.0907F));
                head_armor.addOrReplaceChild("Lbone_snout_r2", CubeListBuilder.create().texOffs(60, 67).addBox(-7.2076F, -0.8956F, 2.3986F, 11.0F, 6.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(6.4218F, 0.1634F, 3.6014F, 2.7477F, 0.1032F, -3.0907F));
                head_armor.addOrReplaceChild("underhelmet_r1", CubeListBuilder.create().texOffs(62, 4).addBox(-3.2F, -6.6623F, -5.3552F, 11.0F, 9.0F, 10.0F, new CubeDeformation(-0.3F)), PartPose.offsetAndRotation(5.859F, 2.9485F, -0.9F, -3.1416F, 0.0F, 3.1416F));
                head_armor.addOrReplaceChild("helmet_r1", CubeListBuilder.create().texOffs(50, 23).addBox(-3.2F, -6.2623F, -5.3552F, 11.0F, 13.0F, 11.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(5.859F, 0.9485F, -0.4F, -3.1416F, 0.0F, 3.1416F));

                PartDefinition snout2 = head_armor.getChild("snout2");
                snout2.addOrReplaceChild("snout_r1", CubeListBuilder.create().texOffs(0, 65).addBox(-6.2076F, 0.1044F, 1.6F, 13.0F, 5.0F, 4.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -3.1416F, 0.0F, 3.1416F));
                snout2.addOrReplaceChild("snout_r2", CubeListBuilder.create().texOffs(52, 18).addBox(-5.2076F, 3.2829F, 1.6F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -4.2F, 0.0F, -3.1416F, 0.0F, 3.1416F));

                return LayerDefinition.create(modelData, 128, 128);
        }

        public static MeshDefinition getArmorModelData() {
                MeshDefinition modelData = getEmptyModelData();
                PartDefinition upper_body = modelData.getRoot().getChild("root").getChild(PartNames.BODY).getChild("upper_body");
                PartDefinition body_no_legs = upper_body.getChild("body_no_legs");
                PartDefinition head = body_no_legs.getChild(PartNames.HEAD);
                PartDefinition body_armor = body_no_legs.getChild("body_fur").addOrReplaceChild("body_armor", CubeListBuilder.create(), PartPose.offset(-6.5F, -2.75F, 1.5F));

                PartDefinition front_armor = body_armor.addOrReplaceChild("front_armor", CubeListBuilder.create(), PartPose.offset(7.8F, 1.5F, -1.0F));

                PartDefinition left_chains = front_armor.addOrReplaceChild("left_chains", CubeListBuilder.create(), PartPose.offset(0.5F, -3.5F, 5.3F));
                PartDefinition right_chains = front_armor.addOrReplaceChild("right_chains", CubeListBuilder.create(), PartPose.offset(0.5F, -2.5F, -5.3F));

                PartDefinition back_armor = body_no_legs.addOrReplaceChild("back_armor", CubeListBuilder.create(), PartPose.offset(-13.7F, 1.25F, 0.5F));

                PartDefinition head_armor = head.addOrReplaceChild("head_armor", CubeListBuilder.create(), PartPose.offset(-0.1142F, 1.8638F, 0.5F));

                PartDefinition snout2 = head_armor.addOrReplaceChild("snout2", CubeListBuilder.create(), PartPose.offset(7.3218F, -3.3967F, 3.1F));

                return modelData;
        }

}
