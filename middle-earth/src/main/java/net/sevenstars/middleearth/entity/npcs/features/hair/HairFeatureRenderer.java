package net.sevenstars.middleearth.entity.npcs.features.hair;

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
import net.minecraft.util.math.ColorHelper;
import net.sevenstars.middleearth.client.ModTexturedRenderLayers;
import net.sevenstars.middleearth.entity.ModEntityModelLayers;
import net.sevenstars.middleearth.entity.npcs.NpcEntityModel;
import net.sevenstars.middleearth.entity.npcs.NpcEntityRenderState;
import net.sevenstars.middleearth.entity.npcs.NpcEntityRenderer;
import net.sevenstars.middleearth.resources.AtlasesME;


@Environment(EnvType.CLIENT)
public class HairFeatureRenderer extends FeatureRenderer<NpcEntityRenderState, NpcEntityModel> {
    private final EntityModel<NpcEntityRenderState> hairModel;
    private final SpriteAtlasTexture hairAtlasTexture;

    public HairFeatureRenderer(FeatureRendererContext<NpcEntityRenderState, NpcEntityModel> context, LoadedEntityModels loader) {
        super(context);
        this.hairModel = new HairModel(loader.getModelPart(ModEntityModelLayers.NPC_ENTITY_HAIR));
        hairAtlasTexture = AtlasesME.getAtlasFromPath(ModTexturedRenderLayers.CHARACTER_HAIRS_ATLAS_TEXTURE);
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, NpcEntityRenderState state, float limbAngle, float limbDistance) {
        Identifier hairAddonTextureId = state.hairAddonId;
        Identifier beardAddonTextureId = state.beardAddonId;

        EntityModel<NpcEntityRenderState> entityModel = hairModel;

        // TODO : Disable the beard based on the helmet
        /*
                if (entity.getEquippedStack(EquipmentSlot.HEAD).isIn(TagK°.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "helmet_hides_dwarf_beard")))){
         */

        if(hairAddonTextureId == null && beardAddonTextureId == null){
            entityModel.getRootPart().visible = false;
            return;
        } else if (!entityModel.getRootPart().visible){
            entityModel.getRootPart().visible = true;
        }
        entityModel.setAngles(state);

        VertexConsumer vertexConsumer = vertexConsumers.getBuffer(ModTexturedRenderLayers.getCharacterHairsRenderLayer());

        int overlay = state.hurt ? getOverlay(state, 0f) : OverlayTexture.DEFAULT_UV;

        if(hairAddonTextureId != null && state.canShowHair){
            Sprite sprite = hairAtlasTexture.getSprite(AtlasesME.prefixAtlas(state.hairAddonId, AtlasesME.CHARACTER_HAIRS));
            renderModel(sprite, matrices, vertexConsumer, light, overlay);
        }
        if(beardAddonTextureId != null && state.canShowBeard){
            Sprite sprite = hairAtlasTexture.getSprite(AtlasesME.prefixAtlas(state.beardAddonId, AtlasesME.CHARACTER_HAIRS));
            renderModel(sprite, matrices, vertexConsumer, light, overlay);
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
