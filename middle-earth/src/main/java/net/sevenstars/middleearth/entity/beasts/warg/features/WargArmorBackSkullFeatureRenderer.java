package net.sevenstars.middleearth.entity.beasts.warg.features;

import net.minecraft.client.render.entity.equipment.EquipmentRenderer;
import net.minecraft.client.render.entity.model.LoadedEntityModels;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.entity.EntityModelLayersME;
import net.sevenstars.middleearth.entity.beasts.warg.WargEntityRenderState;
import net.sevenstars.middleearth.entity.beasts.warg.WargModel;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.util.math.MatrixStack;
import net.sevenstars.middleearth.client.RenderUtil;
import net.minecraft.item.ItemStack;
import net.sevenstars.middleearth.item.DataComponentTypesME;
import net.sevenstars.middleearth.item.EquipmentItemsME;
import net.sevenstars.middleearth.item.dataComponents.MountArmorAddonComponent;

public class WargArmorBackSkullFeatureRenderer extends FeatureRenderer<WargEntityRenderState, WargModel> {
    private static final Identifier TEXTURE = Identifier.of(MiddleEarth.MOD_ID, "textures/entities/warg/feature/warg_armor_addons.png");
    private final WargArmorTopAddonsModel model;

    public WargArmorBackSkullFeatureRenderer(FeatureRendererContext<WargEntityRenderState, WargModel> context, LoadedEntityModels loader, EquipmentRenderer equipmentRenderer) {
        super(context);
        this.model = new WargArmorTopAddonsModel(loader.getModelPart(EntityModelLayersME.WARG_ARMOR_ADDONS_BACK));
    }

    @Override
    public void render(MatrixStack matrices, OrderedRenderCommandQueue queue, int light, WargEntityRenderState state, float limbAngle, float limbDistance) {
        ItemStack itemStack = state.armor;

        MountArmorAddonComponent component = itemStack.get(DataComponentTypesME.MOUNT_ARMOR_DATA);

        if(component != null && component.topArmorAddon() && itemStack.isOf(EquipmentItemsME.WARG_GUNDABAD_PLATE_ARMOR)) {
            RenderUtil.renderArmorTexture(model, state, matrices, queue, TEXTURE, itemStack.hasGlint(), light);
        }
    }
}
