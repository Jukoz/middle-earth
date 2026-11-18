package net.sevenstars.middleearth.features.npcs.renderer.features.ear;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.LoadedEntityModels;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.client.RenderUtil;
import net.sevenstars.middleearth.client.TexturedRenderLayersME;
import net.sevenstars.middleearth.config.ClientConfigME;
import net.sevenstars.middleearth.entity.EntityModelLayersME;
import net.sevenstars.middleearth.features.npcs.renderer.NpcEntityModel;
import net.sevenstars.middleearth.features.npcs.renderer.NpcEntityRenderState;
import net.sevenstars.middleearth.registries.AtlasesME;

@Environment(EnvType.CLIENT)
public class EarFeatureRenderer extends FeatureRenderer<NpcEntityRenderState, NpcEntityModel> {
    private final EarModel earModel;
    private final SpriteAtlasTexture characterTexturesAtlas;

    public EarFeatureRenderer(FeatureRendererContext<NpcEntityRenderState, NpcEntityModel> context, LoadedEntityModels loader) {
        super(context);
        this.earModel = new EarModel(loader.getModelPart(EntityModelLayersME.NPC_ENTITY_EAR));
        characterTexturesAtlas = AtlasesME.getAtlasFromPath(TexturedRenderLayersME.CHARACTER_ATLAS_TEXTURES);
    }

    @Override
    public void render(MatrixStack matrices, OrderedRenderCommandQueue queue, int light, NpcEntityRenderState state, float limbAngle, float limbDistance) {
        boolean isSimplified = ClientConfigME.ENABLE_SIMPLIFIED_CHARACTER_RENDERING && state.simplifiedSkinId != null;
        Identifier earId =  (isSimplified) ? state.simplifiedEarId : MiddleEarth.appendPrefix(state.earId, AtlasesME.SKIN_PREFIX);

        if(!state.canShowEars)
            return;


        int overlay = state.hurt ? getOverlay(state, 0f) : OverlayTexture.DEFAULT_UV;
        if(earId != null){
            Sprite sprite = characterTexturesAtlas.getSprite(earId);
            RenderUtil.renderModel(earModel, state, matrices, queue, TexturedRenderLayersME.getCharacterTexturesRenderLayer(), light, overlay, sprite);
        }
    }

    public static int getOverlay(LivingEntityRenderState state, float whiteOverlayProgress) {
        return OverlayTexture.packUv(OverlayTexture.getU(whiteOverlayProgress), OverlayTexture.getV(state.hurt));
    }
}
