package net.sevenstars.middleearth.entity.npcs.features.beards;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.LoadedEntityModels;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.entity.ModEntityModelLayers;
import net.sevenstars.middleearth.entity.npcs.NpcEntityModel;
import net.sevenstars.middleearth.entity.npcs.NpcEntityRenderState;


@Environment(EnvType.CLIENT)
public class NpcEntityBeardFeatureRenderer extends FeatureRenderer<NpcEntityRenderState, NpcEntityModel> {
    private static final Identifier TEXTURE = Identifier.of(MiddleEarth.MOD_ID,"textures/entities/npcs/beards/black_beard.png");
    private final EntityModel<NpcEntityRenderState> beardModel;

    public NpcEntityBeardFeatureRenderer(FeatureRendererContext<NpcEntityRenderState, NpcEntityModel> context, LoadedEntityModels loader) {
        super(context);
        this.beardModel = new BeardModel(loader.getModelPart(ModEntityModelLayers.NPC_BEARD_BRAIDED));
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, NpcEntityRenderState state, float limbAngle, float limbDistance) {
        EntityModel<NpcEntityRenderState> entityModel = beardModel;
        entityModel.setAngles(state);

        render(entityModel, TEXTURE, matrices , vertexConsumers, light, state, 0xffffff);
    }
}
