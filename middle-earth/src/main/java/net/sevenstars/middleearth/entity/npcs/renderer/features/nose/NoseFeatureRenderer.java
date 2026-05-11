package net.sevenstars.middleearth.entity.npcs.renderer.features.nose;

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
import net.sevenstars.middleearth.entity.EntityModelLayersME;
import net.sevenstars.middleearth.entity.npcs.renderer.NpcEntityModel;
import net.sevenstars.middleearth.entity.npcs.renderer.NpcEntityRenderState;
import net.sevenstars.middleearth.registries.AtlasesME;

@Environment(EnvType.CLIENT)
public class NoseFeatureRenderer extends FeatureRenderer<NpcEntityRenderState, NpcEntityModel> {
    private final EntityModel<NpcEntityRenderState> noseModel;
    private final SpriteAtlasTexture skinAtlasTexture;

    public NoseFeatureRenderer(FeatureRendererContext<NpcEntityRenderState, NpcEntityModel> context, LoadedEntityModels loader) {
        super(context);
        this.noseModel = new NoseModel(loader.getModelPart(EntityModelLayersME.NPC_ENTITY_NOSE));
        skinAtlasTexture = AtlasesME.getAtlasFromPath(ModTexturedRenderLayers.CHARACTER_SKIN_ATLAS_TEXTURE);
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, NpcEntityRenderState state, float limbAngle, float limbDistance) {
        noseModel.setAngles(state);

        VertexConsumer vertexConsumer = vertexConsumers.getBuffer(ModTexturedRenderLayers.getCharacterSkinsRenderLayer());

        int overlay = state.hurt ? getOverlay(state, 0f) : OverlayTexture.DEFAULT_UV;

        Sprite sprite = skinAtlasTexture.getSprite(AtlasesME.prefixAtlas(state.noseId, AtlasesME.CHARACTER_SKINS));
        renderModel(sprite, matrices, vertexConsumer, light, overlay);
    }

    public static int getOverlay(LivingEntityRenderState state, float whiteOverlayProgress) {
        return OverlayTexture.packUv(OverlayTexture.getU(whiteOverlayProgress), OverlayTexture.getV(state.hurt));
    }

    private void renderModel(Sprite sprite, MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay){
        if(sprite != null){
            VertexConsumer newLayerVertexConsumer = sprite.getTextureSpecificVertexConsumer(vertexConsumer);
            noseModel.render(matrices, newLayerVertexConsumer, light, overlay);
        }
    }
}
