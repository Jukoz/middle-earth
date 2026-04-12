package net.sevenstars.middleearth.entity.npcs.renderer.features.feet;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.LoadedEntityModels;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.math.MatrixStack;
import net.sevenstars.middleearth.client.ModTexturedRenderLayers;
import net.sevenstars.middleearth.entity.ModEntityModelLayers;
import net.sevenstars.middleearth.entity.npcs.renderer.NpcEntityModel;
import net.sevenstars.middleearth.entity.npcs.renderer.NpcEntityRenderState;
import net.sevenstars.middleearth.registries.AtlasesME;

@Environment(EnvType.CLIENT)
public class FeetFeatureRenderer extends FeatureRenderer<NpcEntityRenderState, NpcEntityModel> {
    private final EntityModel<NpcEntityRenderState> feetModel;
    private final SpriteAtlasTexture skinAtlasTexture;

    public FeetFeatureRenderer(FeatureRendererContext<NpcEntityRenderState, NpcEntityModel> context, LoadedEntityModels loader) {
        super(context);
        this.feetModel = new FeetModel(loader.getModelPart(ModEntityModelLayers.NPC_ENTITY_FEET));
        skinAtlasTexture = AtlasesME.getAtlasFromPath(ModTexturedRenderLayers.CHARACTER_SKIN_ATLAS_TEXTURE);
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, NpcEntityRenderState state, float limbAngle, float limbDistance) {
        feetModel.setAngles(state);

        if(!state.canShowFeet)
            return;

        VertexConsumer vertexConsumer = vertexConsumers.getBuffer(ModTexturedRenderLayers.getCharacterSkinsRenderLayer());

        int overlay = state.hurt ? getOverlay(state, 0f) : OverlayTexture.DEFAULT_UV;

        Sprite sprite = skinAtlasTexture.getSprite(AtlasesME.prefixAtlas(state.feetId, AtlasesME.CHARACTER_SKINS));
        renderModel(sprite, matrices, vertexConsumer, light, overlay);
    }

    public static int getOverlay(LivingEntityRenderState state, float whiteOverlayProgress) {
        return OverlayTexture.packUv(OverlayTexture.getU(whiteOverlayProgress), OverlayTexture.getV(state.hurt));
    }

    private void renderModel(Sprite sprite, MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay){
        if(sprite != null){
            VertexConsumer newLayerVertexConsumer = sprite.getTextureSpecificVertexConsumer(vertexConsumer);
            feetModel.render(matrices, newLayerVertexConsumer, light, overlay);
        }
    }
}
