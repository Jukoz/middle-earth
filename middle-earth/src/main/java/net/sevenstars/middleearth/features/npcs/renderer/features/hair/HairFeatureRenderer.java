package net.sevenstars.middleearth.features.npcs.renderer.features.hair;

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
public class HairFeatureRenderer extends FeatureRenderer<NpcEntityRenderState, NpcEntityModel> {
    private final EntityModel<NpcEntityRenderState> hairModel;
    private final SpriteAtlasTexture characterTexturesAtlas;

    public HairFeatureRenderer(FeatureRendererContext<NpcEntityRenderState, NpcEntityModel> context, LoadedEntityModels loader) {
        super(context);
        this.hairModel = new HairModel(loader.getModelPart(EntityModelLayersME.NPC_ENTITY_HAIR));
        characterTexturesAtlas = AtlasesME.getAtlasFromPath(TexturedRenderLayersME.CHARACTER_ATLAS_TEXTURES);
    }

    @Override
    public void render(MatrixStack matrices, OrderedRenderCommandQueue queue, int light, NpcEntityRenderState state, float limbAngle, float limbDistance) {
        boolean isSimplified = ClientConfigME.ENABLE_SIMPLIFIED_CHARACTER_RENDERING && state.simplifiedSkinId != null;
        Identifier hairAddonTextureId = (isSimplified) ? state.simplifiedHairAddonId : MiddleEarth.appendPrefix(state.hairAddonId, AtlasesME.HAIR_PREFIX);
        Identifier beardAddonTextureId =  (isSimplified) ? null : MiddleEarth.appendPrefix(state.beardAddonId, AtlasesME.HAIR_PREFIX);


        // TODO : Disable the beard based on the helmet
        /*
        if (entity.getEquippedStack(EquipmentSlot.HEAD).isIn(TagK°.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "helmet_hides_dwarf_beard")))){
         */

        if(hairAddonTextureId == null && beardAddonTextureId == null){
            return;
        }

        int overlay = state.hurt ? getOverlay(state, 0f) : OverlayTexture.DEFAULT_UV;

        if(hairAddonTextureId != null && state.canShowHair){
            Sprite sprite = characterTexturesAtlas.getSprite(hairAddonTextureId);
            RenderUtil.renderModel(hairModel, state, matrices, queue, TexturedRenderLayersME.getCharacterTexturesRenderLayer(), light, overlay, sprite);
        }
        if(beardAddonTextureId != null && state.canShowBeard){
            Sprite sprite = characterTexturesAtlas.getSprite(beardAddonTextureId);
            RenderUtil.renderModel(hairModel, state, matrices, queue, TexturedRenderLayersME.getCharacterTexturesRenderLayer(), light, overlay, sprite);
        }
    }

    public static int getOverlay(LivingEntityRenderState state, float whiteOverlayProgress) {
        return OverlayTexture.packUv(OverlayTexture.getU(whiteOverlayProgress), OverlayTexture.getV(state.hurt));
    }
}
