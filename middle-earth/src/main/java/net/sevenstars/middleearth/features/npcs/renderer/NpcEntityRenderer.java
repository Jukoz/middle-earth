package net.sevenstars.middleearth.features.npcs.renderer;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.*;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.entity.BipedEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.feature.ArmorFeatureRenderer;
import net.minecraft.client.render.entity.feature.ElytraFeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.HeadFeatureRenderer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.EquipmentModelData;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.render.state.CameraRenderState;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.consume.UseAction;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import org.joml.Matrix4f;
import net.minecraft.util.math.Vec3d;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.client.TexturedRenderLayersME;
import net.sevenstars.middleearth.client.RenderUtil;
import net.sevenstars.middleearth.config.ClientConfigME;
import net.sevenstars.middleearth.entity.EntityModelLayersME;
import net.sevenstars.middleearth.features.npcs.NpcEntity;
import net.sevenstars.middleearth.features.npcs.data.NpcTextureData;
import net.sevenstars.middleearth.features.npcs.renderer.features.ear.EarFeatureRenderer;
import net.sevenstars.middleearth.features.npcs.renderer.features.feet.FeetFeatureRenderer;
import net.sevenstars.middleearth.features.npcs.renderer.features.hair.HairFeatureRenderer;
import net.sevenstars.middleearth.features.npcs.renderer.features.nose.NoseFeatureRenderer;
import net.sevenstars.middleearth.item.DataComponentTypesME;
import net.sevenstars.middleearth.registries.AtlasesME;
import net.sevenstars.middleearth.registries.CharacterClothesRegistryME;
import net.sevenstars.middleearth.utils.ItemTagsME;
import org.jetbrains.annotations.Nullable;

public class NpcEntityRenderer extends BipedEntityRenderer<NpcEntity, NpcEntityRenderState, NpcEntityModel> {
    private final SpriteAtlasTexture characterTextureAtlas;
    public final static int HURT_COLOR = 0xff7e75;

    public final static int LIGHT_LEVEL_EMISSIVE_EYES = 8;
    public final static int BLINKING_INTERVAL = 80;
    public final static int BLINKING_DURATION = 3;

    // In NpcEntity.Talk order
    private static final Identifier SPEECH_BUBBLES = Identifier.of(MiddleEarth.MOD_ID, "textures/entity/npc/speech_bubbles.png");
    private static final int BUBBLES = 4;
    // In blocks
    private static final float BUBBLE_SIZE = 0.6f;
    private static final float BUBBLE_GAP = 0.2f;
    private static final float LABEL_ROOM = 0.6f;
    private static final float SEATED_DROP = 0.5f;
    private static final float BUBBLE_DISTANCE = 32f;
    // In ticks
    private static final float BUBBLE_POP = 3f;
    public static final float GESTURE_EASE = 4f;
    // Radians of phase per tick
    public static final float LAUGH_SHAKE_SPEED = 1.3f;


    public NpcEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new NpcEntityModel(context.getPart(EntityModelLayersME.NPC)), 0.7f);

        this.features.removeIf(x -> x.getClass() == ElytraFeatureRenderer.class);
        this.features.removeIf(x -> x.getClass() == HeadFeatureRenderer.class);

        EquipmentModelData<BipedEntityModel<NpcEntityRenderState>> armorModels = EquipmentModelData.mapToEntityModel(
                EntityModelLayers.PLAYER_EQUIPMENT, context.getEntityModels(), BipedEntityModel::new);

        this.addFeature(new ArmorFeatureRenderer<>(this, armorModels, armorModels, context.getEquipmentRenderer()));
        this.addFeature(new HairFeatureRenderer(this, context.getEntityModels()));
        this.addFeature(new EarFeatureRenderer(this, context.getEntityModels()));
        this.addFeature(new NoseFeatureRenderer(this, context.getEntityModels()));
        this.addFeature(new FeetFeatureRenderer(this, context.getEntityModels()));

        characterTextureAtlas = AtlasesME.getAtlasFromPath(TexturedRenderLayersME.CHARACTER_ATLAS_TEXTURES);

        this.shadowRadius = 0.5f;
    }

    // region [RenderState]
    @Override
    public NpcEntityRenderState createRenderState() {
        return new NpcEntityRenderState();
    }

    public static float getLOD(Vec3d entityPos) {
        MinecraftClient client = MinecraftClient.getInstance();
        Camera camera = client.gameRenderer.getCamera();
        return (float)camera.getCameraPos().distanceTo(entityPos);
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void updateRenderState(NpcEntity npcEntity, NpcEntityRenderState npcEntityRenderState, float tickDelta) {
        if(!npcEntity.getEntityWorld().isClient())
            return;

        super.updateRenderState(npcEntity, npcEntityRenderState, tickDelta);
        NpcTextureData npcTextureData = npcEntity.retrieveNpcTextureData();
        float currentLightLevel = npcEntity.getEntityWorld().getLightLevel(npcEntity.getBlockPos());

        npcEntityRenderState.pose = npcEntity.getPose();

        npcEntityRenderState.leftArmPose = getArmPose(npcEntity, npcEntity.getStackInHand(Hand.OFF_HAND), Hand.OFF_HAND);
        npcEntityRenderState.rightArmPose = getArmPose(npcEntity, npcEntity.getStackInHand(Hand.MAIN_HAND), Hand.MAIN_HAND);

        npcEntityRenderState.widthScale = npcEntity.getWidthScale();

        npcEntityRenderState.simplifiedSkinId = npcTextureData.getSimplifiedSkin();
        npcEntityRenderState.simplifiedEarId = npcTextureData.getSimplifiedEar();
        npcEntityRenderState.simplifiedFeetId = npcTextureData.getSimplifiedFeet();
        npcEntityRenderState.simplifiedHairAddonId = npcTextureData.getSimplifiedHair();
        npcEntityRenderState.simplifiedNoseId = npcTextureData.getSimplifiedNose();

        if(!ClientConfigME.ENABLE_SIMPLIFIED_CHARACTER_RENDERING || npcEntityRenderState.simplifiedSkinId == null){
            npcEntityRenderState.skinId = npcTextureData.get(NpcRenderedPart.BODY);
            npcEntityRenderState.feetId = npcTextureData.get(NpcRenderedPart.FEET);
            npcEntityRenderState.headId = npcTextureData.get(NpcRenderedPart.HEAD);
            npcEntityRenderState.earId = npcTextureData.get(NpcRenderedPart.EAR);
            npcEntityRenderState.noseId = npcTextureData.get(NpcRenderedPart.NOSE);
            npcEntityRenderState.eyesId = npcTextureData.get(NpcRenderedPart.EYE);
            npcEntityRenderState.eyesEmissiveId = npcTextureData.get(NpcRenderedPart.EYE_EMISSIVE);
            npcEntityRenderState.haveEmissiveEyes = npcTextureData.isEyeEmissive() && currentLightLevel <= LIGHT_LEVEL_EMISSIVE_EYES;
            npcEntityRenderState.eyebrowId = npcTextureData.get(NpcRenderedPart.EYEBROW);
            npcEntityRenderState.scarId = npcTextureData.get(NpcRenderedPart.SCAR);
            npcEntityRenderState.beardId = npcTextureData.get(NpcRenderedPart.BEARD);
            npcEntityRenderState.beardAddonId = npcTextureData.get(NpcRenderedPart.BEARD_ADDON);
            npcEntityRenderState.hairId = npcTextureData.get(NpcRenderedPart.HAIR);
            npcEntityRenderState.hairAddonId = npcTextureData.get(NpcRenderedPart.HAIR_ADDON);

            npcEntityRenderState.clothingBase = npcTextureData.get(NpcRenderedPart.CLOTHING_BASE);
            npcEntityRenderState.clothingOver = npcTextureData.get(NpcRenderedPart.CLOTHING_OVER);
            npcEntityRenderState.clothingExtra = npcTextureData.get(NpcRenderedPart.CLOTHING_EXTRA);

            long age = npcEntity.age;
            npcEntityRenderState.blinking = (0 + age) % BLINKING_INTERVAL >= BLINKING_INTERVAL - BLINKING_DURATION;
        }
        ItemStack currentHelmet = npcEntity.getEquippedStack(EquipmentSlot.HEAD);
        if(currentHelmet == null || currentHelmet.isEmpty()){
            npcEntityRenderState.canShowBeard = true;
            npcEntityRenderState.canShowHair = true;
            npcEntityRenderState.canShowEars = true;
        } else {
            var hasAttachment = currentHelmet.get(DataComponentTypesME.HELMET_ATTACHMENT_DATA);
            boolean hasHoodDown = hasAttachment == null || hasAttachment.down();

            npcEntityRenderState.canShowEars = currentHelmet.isIn(ItemTagsME.CHARACTER_HELMET_SHOW_EARS) && hasHoodDown;
            npcEntityRenderState.canShowBeard = !currentHelmet.isIn(ItemTagsME.CHARACTER_HELMET_HIDE_BEARD);
            npcEntityRenderState.canShowHair = !currentHelmet.isIn(ItemTagsME.CHARACTER_HELMET_HIDE_HAIR) && hasHoodDown;
        }
        ItemStack currentShoes = npcEntity.getEquippedStack(EquipmentSlot.FEET);
        npcEntityRenderState.canShowFeet = currentShoes == null || currentShoes.isEmpty();
        npcEntityRenderState.LOD = getLOD(npcEntity.getEntityPos());
        npcEntityRenderState.talk = npcEntity.getTalk();
        npcEntityRenderState.talkAge = npcEntity.getTalkAge(tickDelta);
        nodHead(npcEntityRenderState);
    }

    // In degrees. In the state, not the model, as hair, ears and nose turn by its pitch
    private static void nodHead(NpcEntityRenderState state) {
        float time = state.talkAge;
        float ease = Math.min(1f, time / GESTURE_EASE);
        switch (state.talk) {
            case NOD -> state.pitch += ease * 14f * Math.max(0f, MathHelper.sin(time * 0.4f));
            case SAY -> state.pitch += ease * 3f * MathHelper.sin(time * 0.6f);
            case ASK -> state.pitch -= ease * 7f;
            case EXCLAIM -> state.pitch -= ease * 9f;
            case LAUGH -> state.pitch += ease * (5f * MathHelper.sin(time * LAUGH_SHAKE_SPEED) - 10f);
            case NONE -> {}
        }
    }
    // endregion

    // region [Layered Texture Renderer]
    @Nullable
    @Override
    protected RenderLayer getRenderLayer(NpcEntityRenderState state, boolean showBody, boolean translucent, boolean showOutline) {
        Identifier identifier = this.getTexture(state);
        if (translucent) {
            return RenderLayers.entityTranslucent(identifier);
        } else if (showBody) {
            return this.model.getLayer(identifier);
        } else {
            return showOutline ? RenderLayers.outlineNoCull(identifier) : null;
        }
    }

    @Override
    public void render(NpcEntityRenderState state, MatrixStack matrices, OrderedRenderCommandQueue queue, CameraRenderState cameraState) {
        int light = state.light;
        boolean simplified = ClientConfigME.ENABLE_SIMPLIFIED_CHARACTER_RENDERING && state.simplifiedSkinId != null;

        if(!simplified && (state.skinId == null || state.headId == null || state.eyesId == null))
            return;


        matrices.push();
        if (state.isInPose(EntityPose.SLEEPING)) {
            Direction direction = state.sleepingDirection;
            if (direction != null) {
                float f = state.standingEyeHeight - 0.1F;
                matrices.translate((float)(-direction.getOffsetX()) * f, 0.0F, (float)(-direction.getOffsetZ()) * f);
            }
        }
        else if (state.hasVehicle) {
            matrices.translate(0, -0.5F, 0);
        }

        float g = state.baseScale;
        float widthScale = state.widthScale;

        matrices.scale(g * widthScale, g, g * widthScale);
        this.setupTransforms(state, matrices, state.bodyYaw, g);
        matrices.scale(-widthScale, -1.0f, widthScale);
        this.scale(state, matrices);
        matrices.translate(0.0f, -1.501f, 0.0f);

        this.model.setAngles(state);
        int overlay = state.hurt ? getOverlay(state, this.getAnimationCounter(state)) : OverlayTexture.DEFAULT_UV;

        if(simplified){
            renderTexture(matrices, queue, state, TexturedRenderLayersME.getCharacterTexturesRenderLayer(), state.simplifiedSkinId, light, overlay);
        } else {
            renderComplexVersion(matrices, queue, light, overlay, state);
        }

        if (this.shouldRenderFeatures(state) && state.LOD < ClientConfigME.LOD_NPC_ARMOR_DISTANCE) {
            for (FeatureRenderer<NpcEntityRenderState, NpcEntityModel> feature : this.features) {
                if (feature instanceof EarFeatureRenderer)
                    if ((state.simplifiedEarId == null && state.earId == null) || state.LOD > ClientConfigME.LOD_NPC_FEATURES_DISTANCE)
                        continue;
                if (feature instanceof NoseFeatureRenderer)
                    if ((state.simplifiedNoseId == null && state.noseId == null) || state.LOD > ClientConfigME.LOD_NPC_FEATURES_DISTANCE)
                        continue;
                if (feature instanceof HairFeatureRenderer)
                    if ((state.simplifiedHairAddonId == null && state.hairAddonId == null && state.beardAddonId == null)
                            || state.LOD > ClientConfigME.LOD_NPC_FEATURES_DISTANCE)
                        continue;
                if(feature instanceof FeetFeatureRenderer)
                    if((state.simplifiedFeetId == null && state.feetId == null) || state.LOD > ClientConfigME.LOD_NPC_FEATURES_DISTANCE)
                        continue;
                feature.render(matrices, queue, light, state, state.relativeHeadYaw, state.pitch);
            }
        }

        matrices.pop();

        if ((state).displayName != null) {
            this.renderLabelIfPresent(state, matrices, queue, cameraState);
        }
        if(state.talk.isSpoken() && state.LOD < BUBBLE_DISTANCE)
            renderBubble(state, matrices, queue, cameraState);
    }

    // Fully lit so it reads at night
    private void renderBubble(NpcEntityRenderState state, MatrixStack matrices, OrderedRenderCommandQueue queue, CameraRenderState cameraState) {
        float size = BUBBLE_SIZE * Math.min(1f, state.talkAge / BUBBLE_POP);
        float half = size / 2f;
        matrices.push();
        matrices.translate(0f, state.height + BUBBLE_GAP - (state.hasVehicle ? SEATED_DROP : 0f) + (state.displayName != null ? LABEL_ROOM : 0f), 0f);
        matrices.multiply(cameraState.orientation);
        float left = state.talk.bubble() / (float) BUBBLES;
        float right = left + 1f / BUBBLES;
        queue.submitCustom(matrices, RenderLayers.text(SPEECH_BUBBLES), (entry, vertexConsumer) -> {
            Matrix4f matrix = entry.getPositionMatrix();
            vertexConsumer.vertex(matrix, -half, 0f, 0f).color(0xFFFFFFFF).texture(left, 1f).light(LightmapTextureManager.MAX_LIGHT_COORDINATE);
            vertexConsumer.vertex(matrix, half, 0f, 0f).color(0xFFFFFFFF).texture(right, 1f).light(LightmapTextureManager.MAX_LIGHT_COORDINATE);
            vertexConsumer.vertex(matrix, half, size, 0f).color(0xFFFFFFFF).texture(right, 0f).light(LightmapTextureManager.MAX_LIGHT_COORDINATE);
            vertexConsumer.vertex(matrix, -half, size, 0f).color(0xFFFFFFFF).texture(left, 0f).light(LightmapTextureManager.MAX_LIGHT_COORDINATE);
        });
        matrices.pop();
    }

    private void renderComplexVersion(MatrixStack matrices, OrderedRenderCommandQueue queue, int light, int overlay, NpcEntityRenderState state) {
        RenderLayer layer = TexturedRenderLayersME.getCharacterTexturesRenderLayer();

        // Will always be shown
        renderTexture(matrices, queue, state, layer, MiddleEarth.appendPrefix(state.skinId, AtlasesME.SKIN_PREFIX), light, overlay);

        renderTexture(matrices, queue, state, layer, MiddleEarth.appendPrefix(state.headId, AtlasesME.SKIN_PREFIX), light, overlay);

        if(!state.blinking){
            renderTexture(matrices, queue, state, layer, MiddleEarth.appendPrefix(state.eyesId, AtlasesME.EYE_PREFIX), light, overlay);
        }
        // Optionally shown, only if the value is present
        if(state.eyebrowId != null)
            renderTexture(matrices, queue, state, layer, MiddleEarth.appendPrefix(state.eyebrowId, AtlasesME.HAIR_PREFIX), light, overlay);

        if(state.scarId != null)
            renderTexture(matrices, queue, state, layer, MiddleEarth.appendPrefix(state.scarId, AtlasesME.SKIN_PREFIX), light, overlay);

        if(state.beardId != null)
            renderTexture(matrices, queue, state, layer, MiddleEarth.appendPrefix(state.beardId, AtlasesME.HAIR_PREFIX), light, overlay);

        if(state.clothingBase == null && state.clothingOver == null && state.clothingExtra == null){
            renderTexture(matrices, queue, state, layer, MiddleEarth.appendPrefix(CharacterClothesRegistryME.Base.THONG_BROWN, AtlasesME.CLOTHES_BASE_PREFIX), light, overlay);
        }
        else {
            if(state.clothingBase != null)
                renderTexture(matrices, queue, state, layer, MiddleEarth.appendPrefix(state.clothingBase, AtlasesME.CLOTHES_BASE_PREFIX), light, overlay);

            if(state.clothingOver != null)
                renderTexture(matrices, queue, state, layer, MiddleEarth.appendPrefix(state.clothingOver, AtlasesME.CLOTHES_OVER_PREFIX), light, overlay);

            if(state.clothingExtra != null)
                renderTexture(matrices, queue, state, layer, MiddleEarth.appendPrefix(state.clothingExtra, AtlasesME.CLOTHES_EXTRA_PREFIX), light, overlay);
        }

        if(state.hairId != null)
            renderTexture(matrices, queue, state, layer, MiddleEarth.appendPrefix(state.hairId, AtlasesME.HAIR_PREFIX), light, overlay);

        if(!state.blinking && state.haveEmissiveEyes){
            renderTexture(matrices, queue, state, TexturedRenderLayersME.getCharacterTexturesEmissiveRenderLayer(), MiddleEarth.appendPrefix(state.eyesEmissiveId, AtlasesME.EYE_PREFIX), light, overlay);
        }
    }

    private void renderTexture(MatrixStack matrices, OrderedRenderCommandQueue queue, NpcEntityRenderState state, RenderLayer layer, Identifier textureId, int light, int overlay){
        RenderUtil.renderAtlasTexture(characterTextureAtlas, model, state, matrices, queue, layer, textureId, light, overlay);
    }

    @Override
    protected int getMixColor(NpcEntityRenderState state) {
        if(state.hurt)
            return HURT_COLOR;
        return super.getMixColor(state);
    }

    @Override
    public Identifier getTexture(NpcEntityRenderState state) {
        // Made custom in the render method
        return null;
    }

    private BipedEntityModel.ArmPose getArmPose(NpcEntity npc, ItemStack stack, Hand hand) {
        if(npc.isAiming()){
            return BipedEntityModel.ArmPose.BOW_AND_ARROW;
        }

        if (stack.isEmpty()) {
            return BipedEntityModel.ArmPose.EMPTY;
        }
        if (!npc.handSwinging && (stack.isOf(Items.CROSSBOW) || stack.isIn(ItemTagsME.CROSSBOW))) {
            if(CrossbowItem.isCharged(stack)) {
                return BipedEntityModel.ArmPose.CROSSBOW_HOLD;
            }
            else if(npc.isCharging()) {
                return BipedEntityModel.ArmPose.CROSSBOW_CHARGE;
            }
        }
        if (npc.getActiveHand() == hand && npc.getItemUseTimeLeft() > 0) {
            UseAction useAction = stack.getUseAction();
            if (useAction == UseAction.BLOCK || npc.isBlocking()) {
                return BipedEntityModel.ArmPose.BLOCK;
            }
            if (useAction == UseAction.BOW) {
                return BipedEntityModel.ArmPose.BOW_AND_ARROW;
            }
            if (useAction == UseAction.SPEAR || useAction == UseAction.TRIDENT) {
                return BipedEntityModel.ArmPose.THROW_TRIDENT;
            }
            if (useAction == UseAction.CROSSBOW) {
                return BipedEntityModel.ArmPose.CROSSBOW_CHARGE;
            }
            if (useAction == UseAction.SPYGLASS) {
                return BipedEntityModel.ArmPose.SPYGLASS;
            }
            if (useAction == UseAction.TOOT_HORN) {
                return BipedEntityModel.ArmPose.TOOT_HORN;
            }
            if (useAction == UseAction.BRUSH) {
                return BipedEntityModel.ArmPose.BRUSH;
            }
        }
        return BipedEntityModel.ArmPose.ITEM;
    }
    // endregion
}
