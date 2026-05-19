package net.sevenstars.middleearth.entity.npcs.renderer.features.hair;

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
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.client.ModTexturedRenderLayers;
import net.sevenstars.middleearth.config.ModClientConfigs;
import net.sevenstars.middleearth.entity.EntityModelLayersME;
import net.sevenstars.middleearth.entity.npcs.renderer.NpcEntityModel;
import net.sevenstars.middleearth.entity.npcs.renderer.NpcEntityRenderState;
import net.sevenstars.middleearth.registries.AtlasesME;


@Environment(EnvType.CLIENT)
public class HairFeatureRenderer extends FeatureRenderer<NpcEntityRenderState, NpcEntityModel> {
    private final EntityModel<NpcEntityRenderState> hairModel;
    private final SpriteAtlasTexture characterTexturesAtlas;

    public HairFeatureRenderer(FeatureRendererContext<NpcEntityRenderState, NpcEntityModel> context, LoadedEntityModels loader) {
        super(context);
        this.hairModel = new HairModel(loader.getModelPart(EntityModelLayersME.NPC_ENTITY_HAIR));
        characterTexturesAtlas = AtlasesME.getAtlasFromPath(ModTexturedRenderLayers.CHARACTER_ATLAS_TEXTURES);
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, NpcEntityRenderState state, float limbAngle, float limbDistance) {
        boolean isSimplified = ModClientConfigs.ENABLE_SIMPLIFIED_CHARACTER_RENDERING;

        Identifier hairAddonTextureId = state.hairAddonId;
        Identifier beardAddonTextureId = state.beardAddonId;

        EntityModel<NpcEntityRenderState> entityModel = hairModel;

        // TODO : Disable the beard based on the helmet
        /*
                if (entity.getEquippedStack(EquipmentSlot.HEAD).isIn(TagK°.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "helmet_hides_dwarf_beard")))){
         */

        if(!isSimplified){
            if(hairAddonTextureId == null && beardAddonTextureId == null){
                entityModel.getRootPart().visible = false;
                return;
            } else if (!entityModel.getRootPart().visible){
                entityModel.getRootPart().visible = true;
            }
        }
        entityModel.setAngles(state);

        VertexConsumer vertexConsumer = vertexConsumers.getBuffer(ModTexturedRenderLayers.getCharacterTexturesRenderLayer());

        int overlay = state.hurt ? getOverlay(state, 0f) : OverlayTexture.DEFAULT_UV;

        if(isSimplified){
            Sprite sprite = characterTexturesAtlas.getSprite(state.simplifiedHairAddonId);
            renderModel(sprite, matrices, vertexConsumer, light, overlay);
        } else {
            if(hairAddonTextureId != null && state.canShowHair){
                Sprite sprite = characterTexturesAtlas.getSprite(MiddleEarth.ofPrefix(state.hairAddonId, AtlasesME.HAIR_PREFIX));
                renderModel(sprite, matrices, vertexConsumer, light, overlay);
            }
            if(beardAddonTextureId != null && state.canShowBeard){
                Sprite sprite = characterTexturesAtlas.getSprite(MiddleEarth.ofPrefix(state.beardAddonId, AtlasesME.HAIR_PREFIX));
                renderModel(sprite, matrices, vertexConsumer, light, overlay);
            }
        }
    }

    private void renderModel(Sprite sprite, MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay){
        if(sprite != null){
            VertexConsumer newLayerVertexConsumer = sprite.getTextureSpecificVertexConsumer(vertexConsumer);
            hairModel.render(matrices, newLayerVertexConsumer, light, overlay);
        }
    }

    public static int getOverlay(LivingEntityRenderState state, float whiteOverlayProgress) {
        return OverlayTexture.packUv(OverlayTexture.getU(whiteOverlayProgress), OverlayTexture.getV(state.hurt));
    }
}
