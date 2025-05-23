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
import net.minecraft.util.math.ColorHelper;
import net.sevenstars.middleearth.client.ModTexturedRenderLayers;
import net.sevenstars.middleearth.entity.ModEntityModelLayers;
import net.sevenstars.middleearth.entity.npcs.NpcEntityModel;
import net.sevenstars.middleearth.entity.npcs.NpcEntityRenderState;
import net.sevenstars.middleearth.entity.npcs.NpcEntityRenderer;


@Environment(EnvType.CLIENT)
public class HairFeatureRenderer extends FeatureRenderer<NpcEntityRenderState, NpcEntityModel> {
    private final EntityModel<NpcEntityRenderState> hairModel;
    private final SpriteAtlasTexture hairAtlasTexture;

    public HairFeatureRenderer(FeatureRendererContext<NpcEntityRenderState, NpcEntityModel> context, LoadedEntityModels loader) {
        super(context);
        this.hairModel = new HairModel(loader.getModelPart(ModEntityModelLayers.NPC_ENTITY_HAIR));
        MinecraftClient client = MinecraftClient.getInstance();
        hairAtlasTexture = client.getBakedModelManager().getAtlas(ModTexturedRenderLayers.NPC_HAIR_TEXTURES_ATLAS_TEXTURE);
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, NpcEntityRenderState state, float limbAngle, float limbDistance) {
        Identifier hairAddonTextureId = state.hairAddonId;
        Identifier beardAddonTextureId = state.beardAddonId;

        EntityModel<NpcEntityRenderState> entityModel = hairModel;

        // TODO : Disable the beard based on the helmet
        /*
                if (entity.getEquippedStack(EquipmentSlot.HEAD).isIn(TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "helmet_hides_dwarf_beard")))){
         */

        if(hairAddonTextureId == null && beardAddonTextureId == null){
            entityModel.getRootPart().visible = false;
            return;
        } else if (!entityModel.getRootPart().visible){
            entityModel.getRootPart().visible = true;
        }

        entityModel.setAngles(state);

        VertexConsumer vertexConsumer = vertexConsumers.getBuffer(ModTexturedRenderLayers.getNpcHairTexturesRenderLayer());

        boolean bl = !state.invisible;
        boolean bl2 = !bl && !state.invisibleToPlayer;
        int k = bl2 ? 654311423 : -1;
        int color = ColorHelper.mix(k, (state.hurt) ? NpcEntityRenderer.HURT_COLOR : -1);

        if(hairAddonTextureId != null)
            render(entityModel, vertexConsumer, matrices, light, hairAddonTextureId, color);
        if(beardAddonTextureId != null)
            render(entityModel, vertexConsumer, matrices, light, beardAddonTextureId, color);
    }

    private void render(EntityModel<NpcEntityRenderState> entityModel, VertexConsumer vertexConsumer, MatrixStack matrices, int light, Identifier baseIdentifier, int color){
        Identifier id = Identifier.of(baseIdentifier.getNamespace(), "npc_hair_textures/" + baseIdentifier.getPath());
        Sprite sprite = hairAtlasTexture.getSprite(id);

        if(sprite != null){
            VertexConsumer newLayerVertexConsumer = sprite.getTextureSpecificVertexConsumer(vertexConsumer);
            entityModel.render(matrices, newLayerVertexConsumer, light, OverlayTexture.DEFAULT_UV, color);
        }
    }
}
