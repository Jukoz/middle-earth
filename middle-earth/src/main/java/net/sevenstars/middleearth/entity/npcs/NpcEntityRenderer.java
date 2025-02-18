package net.sevenstars.middleearth.entity.npcs;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.BipedEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EntityPose;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.math.ColorHelper;
import net.minecraft.util.math.Direction;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.client.ModTexturedRenderLayers;
import net.sevenstars.middleearth.entity.ModEntityModelLayers;
import net.sevenstars.middleearth.entity.npcs.features.beards.NpcEntityBeardFeatureRenderer;
import net.sevenstars.middleearth.resources.datas.npctextures.NpcTexture;
import net.sevenstars.middleearth.resources.datas.npctextures.NpcTextureType;
import org.jetbrains.annotations.Nullable;

import java.util.Iterator;
import java.util.function.Function;

public class NpcEntityRenderer extends BipedEntityRenderer<NpcEntity, NpcEntityRenderState, NpcEntityModel> {
    private static final String PATH = "textures/npc_skin_textures/";
    private int currentRenderStep = 0;

    private final Function<NpcEntityRenderer.NpcTextureKeys, Sprite> sprites;
    private final SpriteAtlasTexture atlasTexture;
    public NpcEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new NpcEntityModel(context.getPart(ModEntityModelLayers.NPC)), 0.7f);

        this.addFeature(new NpcEntityBeardFeatureRenderer(this, context.getEntityModels()));

        MinecraftClient client = MinecraftClient.getInstance();
        atlasTexture = client.getBakedModelManager().getAtlas(ModTexturedRenderLayers.NPC_SKIN_TEXTURES_ATLAS_TEXTURE);

        this.sprites = Util.memoize((key) -> {
            assert atlasTexture != null;
            return atlasTexture.getSprite(key.getTexture());
        });
    }

    // region RenderState
    @Override
    public NpcEntityRenderState createRenderState() {
        return new NpcEntityRenderState();
    }

    @Override
    public void updateRenderState(NpcEntity npcEntity, NpcEntityRenderState npcEntityRenderState, float f) {
        super.updateRenderState(npcEntity, npcEntityRenderState, f);
        npcEntityRenderState.skinTextureIdentifier = npcEntity.getSkinTextureIdentifier();
    }

    // endregion


    // region Layered Texture Renderer
    @Nullable
    @Override
    protected RenderLayer getRenderLayer(NpcEntityRenderState state, boolean showBody, boolean translucent, boolean showOutline) {
        Identifier identifier = this.getTexture(state);
        if (translucent) {
            return RenderLayer.getItemEntityTranslucentCull(identifier);
        } else if (showBody) {
            return this.model.getLayer(identifier);
        } else {
            return showOutline ? RenderLayer.getOutline(identifier) : null;
        }
    }

    @Override
    public void render(NpcEntityRenderState state, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        currentRenderStep = 0;
        int maximumRenderStep = 3;

        Text customName = state.customName;
        if(customName != null){
            String value = customName.getString();
            switch (value){
                case "Dev_0":
                    maximumRenderStep = 1;
                    break;
                case "Dev_1":
                    maximumRenderStep = 2;
                    break;
                default:
                    break;
            }
        }

        matrices.push();
        if (state.isInPose(EntityPose.SLEEPING)) {
            Direction direction = state.sleepingDirection;
            if (direction != null) {
                float f = state.standingEyeHeight - 0.1F;
                matrices.translate((float)(-direction.getOffsetX()) * f, 0.0F, (float)(-direction.getOffsetZ()) * f);
            }
        }

        float g = state.baseScale;
        matrices.scale(g, g, g);
        this.setupTransforms(state, matrices, state.bodyYaw, g);
        matrices.scale(-1.0F, -1.0F, 1.0F);
        this.scale(state, matrices);
        matrices.translate(0.0F, -1.501F, 0.0F);

        this.model.setAngles(state);
        boolean bl = this.isVisible(state);
        boolean bl2 = !bl && !state.invisibleToPlayer;

        for(int run = 0; run < maximumRenderStep; run ++){
            RenderLayer renderLayer = this.getRenderLayer(state, bl, bl2, state.hasOutline);
            VertexConsumer vertexConsumer = vertexConsumers.getBuffer(renderLayer);
            int k = bl2 ? 654311423 : -1;
            int l = ColorHelper.mix(k, this.getMixColor(state));

            if (renderLayer != null) {
                if(currentRenderStep ==0){
                    if(MinecraftClient.getInstance().world != null){
                        Identifier id = Identifier.of(state.skinTextureIdentifier.getNamespace(), "npc_skin_textures/" + state.skinTextureIdentifier.getPath());
                        var sprite = atlasTexture.getSprite(id);

                        vertexConsumer = vertexConsumers.getBuffer(ModTexturedRenderLayers.getNpcSkinTexturesRenderLayer());
                        VertexConsumer newLayerVertexConsumer = sprite.getTextureSpecificVertexConsumer(vertexConsumer);
                        model.render(matrices, newLayerVertexConsumer, light, OverlayTexture.DEFAULT_UV, l);
                    }
                }
                else {
                    model.render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV, l);
                }
                currentRenderStep++;
            }
        }


        if (this.shouldRenderFeatures(state)) {
            Iterator var15 = this.features.iterator();

            while(var15.hasNext() && currentRenderStep < maximumRenderStep) {
                FeatureRenderer<NpcEntityRenderState, NpcEntityModel> featureRenderer = (FeatureRenderer)var15.next();
                featureRenderer.render(matrices, vertexConsumers, light, state, state.yawDegrees, state.pitch);
                currentRenderStep++;
            }
        }

        matrices.pop();
    }

    @Override
    public Identifier getTexture(NpcEntityRenderState state) {
        return switch (currentRenderStep) {
            case 1 -> Identifier.of(MiddleEarth.MOD_ID, PATH + "brown_eyes.png");
            case 2 -> Identifier.of(MiddleEarth.MOD_ID, PATH + "hair_test.png");
            default -> Identifier.of(MiddleEarth.MOD_ID, PATH + "brown_eyes.png");
        };
    }


    @Environment(EnvType.CLIENT)
    static record NpcTextureKeys(NpcTexture npcTexture, NpcTextureType type) {
        NpcTextureKeys(NpcTexture npcTexture, NpcTextureType type) {
            this.npcTexture = npcTexture;
            this.type = type;
        }

        public Identifier getTexture() {
            Identifier patternIdentifier = (this.npcTexture.pattern().value()).getIdentifier();
            String assetName = ((this.npcTexture.material().value())).getIdentifier().getPath();
            return patternIdentifier.withPath((path) -> {
                //return "npc_skin_textures/entity/" + this.type.asString() + "/" + path + "_" + string;
                return "npc_skin_textures/" + path + "_" + assetName;
            });
        }

        public NpcTexture npcTexture() {
            return this.npcTexture;
        }

        public NpcTextureType type() {
            return this.type;
        }
    }
}
