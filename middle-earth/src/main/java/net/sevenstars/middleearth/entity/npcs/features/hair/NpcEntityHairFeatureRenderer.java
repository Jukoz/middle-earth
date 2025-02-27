package net.sevenstars.middleearth.entity.npcs.features.hair;

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
public class NpcEntityHairFeatureRenderer extends FeatureRenderer<NpcEntityRenderState, NpcEntityModel> {
    private final EntityModel<NpcEntityRenderState> beardModel;
    private final SpriteAtlasTexture hairAtlasTexture;

    public NpcEntityHairFeatureRenderer(FeatureRendererContext<NpcEntityRenderState, NpcEntityModel> context, LoadedEntityModels loader) {
        super(context);
        this.beardModel = new HairModel(loader.getModelPart(ModEntityModelLayers.NPC_BEARD_BRAIDED));
        MinecraftClient client = MinecraftClient.getInstance();
        hairAtlasTexture = client.getBakedModelManager().getAtlas(ModTexturedRenderLayers.NPC_HAIR_TEXTURES_ATLAS_TEXTURE);

    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, NpcEntityRenderState state, float limbAngle, float limbDistance) {
        Identifier addonTextureId = state.hairAddonTextureIdentifier;

        EntityModel<NpcEntityRenderState> entityModel = beardModel;

        if(addonTextureId == null){
            entityModel.getRootPart().visible = false;
            return;
        } else if (!entityModel.getRootPart().visible){
            entityModel.getRootPart().visible = true;
        }

        entityModel.setAngles(state);


        Identifier id = Identifier.of(addonTextureId.getNamespace(), "npc_hair_textures/" + addonTextureId.getPath());
        VertexConsumer vertexConsumer = vertexConsumers.getBuffer(ModTexturedRenderLayers.getNpcHairTexturesRenderLayer());
        Sprite sprite = hairAtlasTexture.getSprite(id);

        if(sprite != null){
            VertexConsumer newLayerVertexConsumer = sprite.getTextureSpecificVertexConsumer(vertexConsumer);
            entityModel.render(matrices, newLayerVertexConsumer, light, OverlayTexture.DEFAULT_UV, 0xffffffff);
        }
    }
}
