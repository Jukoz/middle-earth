package net.sevenstars.middleearth.entity.beasts.warg.features;

import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.entity.equipment.EquipmentRenderer;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.LoadedEntityModels;
import net.minecraft.client.util.math.MatrixStack;
import net.sevenstars.middleearth.client.RenderUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.entity.EntityModelLayersME;
import net.sevenstars.middleearth.entity.beasts.warg.WargEntityRenderState;
import net.sevenstars.middleearth.entity.beasts.warg.WargModel;
import net.sevenstars.middleearth.item.EquipmentItemsME;

public class WargArmorSpineFeatureRenderer extends FeatureRenderer<WargEntityRenderState, WargModel> {
    private static final Identifier BONE_TEXTURE = Identifier.of(MiddleEarth.MOD_ID, "textures/entities/warg/feature/warg_armor_bone_spine_addon.png");
    private static final Identifier MORDOR_TEXTURE = Identifier.of(MiddleEarth.MOD_ID, "textures/entities/warg/feature/warg_armor_mordor_spine_addon.png");
    private final WargArmorBaseAddonsModel model;

    public WargArmorSpineFeatureRenderer(FeatureRendererContext<WargEntityRenderState, WargModel> context, LoadedEntityModels loader, EquipmentRenderer equipmentRenderer) {
        super(context);
        this.model = new WargArmorBaseAddonsModel(loader.getModelPart(EntityModelLayersME.WARG_ARMOR_ADDONS_SPINE));
    }

    @Override
    public void render(MatrixStack matrices, OrderedRenderCommandQueue queue, int light, WargEntityRenderState state, float limbAngle, float limbDistance) {
        ItemStack itemStack = state.armor;
        if(itemStack.isOf(EquipmentItemsME.WARG_REINFORCED_LEATHER_ARMOR)) {
            RenderUtil.renderArmorTexture(model, state, matrices, queue, BONE_TEXTURE, itemStack.hasGlint(), light);
        }
        else if(itemStack.isOf(EquipmentItemsME.WARG_MORDOR_PLATE_ARMOR) || itemStack.isOf(EquipmentItemsME.WARG_MORDOR_MAIL_ARMOR)) {
            RenderUtil.renderArmorTexture(model, state, matrices, queue, MORDOR_TEXTURE, itemStack.hasGlint(), light);
        }
    }
}
