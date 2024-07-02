package net.jukoz.me.client.screens;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.client.screens.data.FactionNpcPreviewData;
import net.jukoz.me.entity.ModEntities;
import net.jukoz.me.entity.dwarves.longbeards.LongbeardDwarfEntity;
import net.jukoz.me.entity.humans.gondor.GondorHumanEntity;
import net.jukoz.me.item.ModEquipmentItems;
import net.jukoz.me.item.ModWeaponItems;
import net.jukoz.me.item.utils.ModBannerPatterns;
import net.jukoz.me.utils.Factions;
import net.jukoz.me.world.dimension.ModDimensions;
import net.minecraft.block.BannerBlock;
import net.minecraft.block.entity.BannerPattern;
import net.minecraft.block.entity.BannerPatterns;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.block.entity.BannerBlockEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.model.ModelLoader;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.component.type.BannerPatternsComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.screen.SmithingScreenHandler;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec2f;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import org.jetbrains.annotations.Nullable;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Environment(EnvType.CLIENT)
public class OnboardingScreen extends Screen {
    private static final Identifier WINDOW_TEXTURE = new Identifier(MiddleEarth.MOD_ID,"textures/gui/map_background.png");
    private static final Identifier MAP_UI_TEXTURE = new Identifier(MiddleEarth.MOD_ID,"textures/gui/map_ui.png");
    private static final Identifier MAP_TEXTURE = new Identifier(MiddleEarth.MOD_ID,"textures/map.png");
    private static final Text ONBOARDING_TEXT = Text.of("Onboarding");
    private static boolean debug = false;
    AbstractClientPlayerEntity player;
    private static final Quaternionf ENTITY_ROTATION;
    private static final Vector3f VECTOR;

    private static List<FactionNpcPreviewData> NPC_PREVIEWS = new ArrayList<>();

    private ModelPart bannerField;
    @Nullable
    private LongbeardDwarfEntity dwarfEntity;
    @Nullable
    private GondorHumanEntity gondorHumanEntity;
    @Nullable
    LivingEntity entity;

    public OnboardingScreen() {
        super(ONBOARDING_TEXT);
    }

    @Override
    protected void init() {
        setup();
        this.bannerField = this.client.getEntityModelLoader().getModelPart(EntityModelLayers.BANNER).getChild("flag");
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        Entity cameraEntity = this.client.getCameraEntity();
        if (cameraEntity != null) {
            if (cameraEntity instanceof AbstractClientPlayerEntity abstractClientPlayerEntity) {
                this.player = abstractClientPlayerEntity;

                this.renderBackground(context, mouseX, mouseY, delta);
                this.drawWindow(context);
            } else {
                this.player = null;
            }
        }
    }

    protected void setup() {
        ButtonWidget debugButton = ButtonWidget.builder(Text.literal("Debug"), button -> {
                    debug = !debug;
                })
                .dimensions(0, 0, 50, 50).build();
        addDrawableChild(debugButton);


        this.dwarfEntity = new LongbeardDwarfEntity(ModEntities.LONGBEARD_ELITE, this.client.world);
        this.gondorHumanEntity = new GondorHumanEntity(ModEntities.GONDORIAN_LEADER, this.client.world);

        FactionNpcPreviewData prevData = NPC_PREVIEWS.stream().filter(f -> f.FACTION == Factions.EREBOR).findFirst().get();
        this.entity = this.dwarfEntity;

        this.entity.bodyYaw = 210f;
        this.entity.setPitch(0f);
        this.entity.headYaw = this.entity.getBodyYaw();
        this.entity.prevHeadYaw = this.entity.getBodyYaw();

        this.entity.equipStack(EquipmentSlot.HEAD, prevData.HEAD);
        this.entity.equipStack(EquipmentSlot.CHEST, prevData.CHEST);
        this.entity.equipStack(EquipmentSlot.LEGS, prevData.LEGS);
        this.entity.equipStack(EquipmentSlot.FEET, prevData.FEET);
        this.entity.equipStack(EquipmentSlot.MAINHAND, prevData.MAIN_HAND);
        this.entity.equipStack(EquipmentSlot.OFFHAND, prevData.OFF_HAND);
    }

    public void drawWindow(DrawContext context) {
        RenderSystem.enableBlend();
        int xCenter = (context.getScaledWindowWidth() / 2);
        int yCenter = (context.getScaledWindowHeight() / 2);

        drawMap(context, xCenter, yCenter);

        drawNpcPreview(context, xCenter, yCenter);
        context.drawTexture(this.player.getSkinTextures().texture(),
                xCenter- 4,
                yCenter - 4,
                8, 8, 8, 8, 64, 64);
    }

    private void drawNpcPreview(DrawContext context, float anchorX, float anchorY){
        float size = 30f;
        float x = anchorX;
        float y = anchorY + (size / 2f);

        InventoryScreen.drawEntity(context, x, y, size, VECTOR, ENTITY_ROTATION, (Quaternionf)null, this.entity);
    }

    private void drawMap(DrawContext context, float anchorX, float anchorY){
        int size = 3000;
        float x = anchorX - (size / 2f);
        float y = anchorY - (size / 2f);

        // TODO : Fix the map and border texture, it repeats itself where it shouldn't.
        context.drawTexture(WINDOW_TEXTURE, (int) anchorX, (int) anchorY,
                0, 0,
                25,
                25,
                25,
                25
        );

        context.drawTexture(MAP_TEXTURE, (int) anchorX, (int) anchorY,
                0, 0,
                25,
                25,
                25,
                25
        );
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if(keyCode == 27){
            this.close();
            return true;
        }
        if (keyCode == 256) {
            this.close();
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    static {
        VECTOR = new Vector3f();
        // Vanilla values from SmithingScreen
        ENTITY_ROTATION = (new Quaternionf()).rotationXYZ(0.43633232F, 0.0F, 3.1415927F);

        NPC_PREVIEWS.add(new FactionNpcPreviewData(
                Factions.EREBOR,
                ModEquipmentItems.EREBOR_PLATE_HELMET,
                ModEquipmentItems.EREBOR_PLATE_CHESTPLATE,
                ModEquipmentItems.EREBOR_PLATE_LEGGINGS,
                ModEquipmentItems.EREBOR_PLATE_BOOTS,
                ModWeaponItems.LONGBEARD_SWORD,
                ModEquipmentItems.LONGBEARD_SHIELD
        ));
        NPC_PREVIEWS.add(new FactionNpcPreviewData(
                Factions.GONDOR,
                ModEquipmentItems.GONDORIAN_PLATE_HELMET,
                ModEquipmentItems.GONDORIAN_PLATE_CHESTPLATE,
                ModEquipmentItems.GONDORIAN_PLATE_LEGGINGS,
                ModEquipmentItems.GONDORIAN_PLATE_BOOTS,
                ModWeaponItems.GONDOR_LONGSWORD,
                ModEquipmentItems.GONDORIAN_SHIELD
        ));
    }

/**
 *     TODO : Waiting for banner fix
 *     private void drawFactionBanner(DrawContext context, float anchorX, float anchorY){
 *         float size = 30f;
 *         float x = anchorX + (size / 2f);
 *         float y = anchorY + (size / 2f);
 *
 *         MatrixStack matrixStack = new MatrixStack();
 *         matrixStack.push();
 *         matrixStack.translate((float)x + 0.5F, (float)(y + 16), 0.0F);
 *         matrixStack.scale(6.0F, -6.0F, 1.0F);
 *         matrixStack.translate(0.5F, 0.5F, 0.0F);
 *         matrixStack.translate(0.5F, 0.5F, 0.5F);
 *         float f = 0.6666667F;
 *         matrixStack.scale(0.6666667F, -0.6666667F, -0.6666667F);
 *         this.bannerField.pitch = 0.0F;
 *         this.bannerField.pivotY = -32.0F;
 *
 *
 *         BannerPatternsComponent bannerPatternsComponent = (new BannerPatternsComponent.Builder()).add(pattern, DyeColor.WHITE).build();
 *         BannerBlockEntityRenderer.renderCanvas(matrixStack, context.getVertexConsumers(), 15728880, OverlayTexture.DEFAULT_UV, this.bannerField, ModelLoader.BANNER_BASE, true, DyeColor.GRAY, bannerPatternsComponent);
 *         matrixStack.pop();
 *         context.draw();
 *     }
 */
}