package net.sevenstars.middleearth.entity.npcs.features.nose;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.LoadedEntityModels;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.client.ModTexturedRenderLayers;
import net.sevenstars.middleearth.entity.ModEntityModelLayers;
import net.sevenstars.middleearth.entity.npcs.NpcEntityModel;
import net.sevenstars.middleearth.entity.npcs.NpcEntityRenderState;

@Environment(EnvType.CLIENT)
public class NoseFeatureRenderer extends FeatureRenderer<NpcEntityRenderState, NpcEntityModel> {
    private final EntityModel<NpcEntityRenderState> noseModel;
    private final SpriteAtlasTexture skinAtlasTexture;

    public NoseFeatureRenderer(FeatureRendererContext<NpcEntityRenderState, NpcEntityModel> context, LoadedEntityModels loader) {
        super(context);
        this.noseModel = new NoseModel(loader.getModelPart(ModEntityModelLayers.NPC_ENTITY_NOSE));
        MinecraftClient client = MinecraftClient.getInstance();

        skinAtlasTexture = client.getBakedModelManager().getAtlas(ModTexturedRenderLayers.NPC_SKIN_TEXTURES_ATLAS_TEXTURE);
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, NpcEntityRenderState state, float limbAngle, float limbDistance) {
        noseModel.setAngles(state);


        VertexConsumer vertexConsumer = vertexConsumers.getBuffer(ModTexturedRenderLayers.getNpcSkinTexturesRenderLayer());

        render(noseModel, vertexConsumer, matrices, light, state.noseTextureIdentifier);
    }

    private void render(EntityModel<NpcEntityRenderState> model, VertexConsumer vertexConsumer, MatrixStack matrices, int light, Identifier baseIdentifier){
        if(baseIdentifier == null) return;
        Identifier id = Identifier.of(baseIdentifier.getNamespace(), "npc_skin_textures/" + baseIdentifier.getPath());
        Sprite sprite = skinAtlasTexture.getSprite(id);

        VertexConsumer newLayerVertexConsumer = sprite.getTextureSpecificVertexConsumer(vertexConsumer);
        model.render(matrices, newLayerVertexConsumer, light, OverlayTexture.DEFAULT_UV, 0xffffffff);
    }
}
