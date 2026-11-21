package net.sevenstars.middleearth.features.npcs.renderer.features.feet;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EntityModel;
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
public class FeetFeatureRenderer extends FeatureRenderer<NpcEntityRenderState, NpcEntityModel> {
    private final EntityModel<NpcEntityRenderState> feetModel;
    private final SpriteAtlasTexture characterTexturesAtlas;

    public FeetFeatureRenderer(FeatureRendererContext<NpcEntityRenderState, NpcEntityModel> context, LoadedEntityModels loader) {
        super(context);
        this.feetModel = new FeetModel(loader.getModelPart(EntityModelLayersME.NPC_ENTITY_FEET));
        characterTexturesAtlas = AtlasesME.getAtlasFromPath(TexturedRenderLayersME.CHARACTER_ATLAS_TEXTURES);
    }

    @Override
    public void render(MatrixStack matrices, OrderedRenderCommandQueue queue, int light, NpcEntityRenderState state, float limbAngle, float limbDistance) {
        boolean isSimplified = ClientConfigME.ENABLE_SIMPLIFIED_CHARACTER_RENDERING && state.simplifiedSkinId != null;
        Identifier feetId =  (isSimplified) ? state.simplifiedFeetId : MiddleEarth.appendPrefix(state.feetId, AtlasesME.SKIN_PREFIX);

        if(!state.canShowFeet)
            return;


        int overlay = state.hurt ? getOverlay(state, 0f) : OverlayTexture.DEFAULT_UV;

        if(feetId != null){
            Sprite sprite = characterTexturesAtlas.getSprite(feetId);
            RenderUtil.renderModel(feetModel, state, matrices, queue, TexturedRenderLayersME.getCharacterTexturesRenderLayer(), light, overlay, sprite);
        }
    }

    public static int getOverlay(LivingEntityRenderState state, float whiteOverlayProgress) {
        return OverlayTexture.packUv(OverlayTexture.getU(whiteOverlayProgress), OverlayTexture.getV(state.hurt));
    }
}
