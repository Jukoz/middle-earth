package net.sevenstars.middleearth.entity.beasts.warg.features;

import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.equipment.EquipmentRenderer;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.client.render.entity.model.LoadedEntityModels;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.entity.beasts.warg.WargEntityRenderState;
import net.sevenstars.middleearth.entity.beasts.warg.WargModel;
import net.sevenstars.middleearth.entity.EntityModelLayersME;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.util.math.MatrixStack;
import net.sevenstars.middleearth.client.RenderUtil;

public class WargSaddleFeatureRenderer extends FeatureRenderer<WargEntityRenderState, WargModel> {
    private final WargSaddleModel model;

    public WargSaddleFeatureRenderer(FeatureRendererContext<WargEntityRenderState, WargModel> context, LoadedEntityModels loader, EquipmentRenderer equipmentRenderer) {
        super(context);
        this.model = new WargSaddleModel(loader.getModelPart(EntityModelLayersME.WARG_SADDLE));

        // Only the saddle is drawn, so everything else stays hidden (renders are deferred, the old per-render toggling no longer works)
        ModelPart body = this.model.getRootPart().getChild("root").getChild(EntityModelPartNames.BODY);
        ModelPart upperBody = body.getChild("upper_body");
        ModelPart bodyNoLegs = upperBody.getChild("body_no_legs");
        bodyNoLegs.getChild("body_fur").visible = false;
        bodyNoLegs.getChild("main_body").visible = false;
        bodyNoLegs.getChild(EntityModelPartNames.HEAD).visible = false;
        bodyNoLegs.getChild("wait_tail").visible = false;
        upperBody.getChild("right_front_leg").visible = false;
        upperBody.getChild("left_front_leg").visible = false;
        body.getChild("back_body").visible = false;
    }

    @Override
    public void render(MatrixStack matrices, OrderedRenderCommandQueue queue, int light, WargEntityRenderState state, float limbAngle, float limbDistance) {
        ItemStack itemStack = state.saddle;
        boolean hasArmor = !state.armor.isEmpty();
        if(!itemStack.isEmpty()) {
            RenderUtil.renderArmorTexture(model, state, matrices, queue, Identifier.of(MiddleEarth.MOD_ID, "textures/entities/warg/feature/warg_saddle.png"), itemStack.hasGlint(), light);
        }
    }
}