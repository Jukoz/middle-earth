package net.jukoz.me.client.screens;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.client.screens.data.FactionNpcPreviewData;
import net.jukoz.me.entity.ModEntities;
import net.jukoz.me.entity.dwarves.longbeards.LongbeardDwarfEntity;
import net.jukoz.me.entity.elves.galadhrim.GaladhrimElfEntity;
import net.jukoz.me.entity.hobbits.shire.ShireHobbitEntity;
import net.jukoz.me.entity.humans.gondor.GondorHumanEntity;
import net.jukoz.me.entity.humans.gondor.GondorHumanModel;
import net.jukoz.me.entity.orcs.mordor.MordorOrcEntity;
import net.jukoz.me.item.ModEquipmentItems;
import net.jukoz.me.item.ModWeaponItems;
import net.jukoz.me.item.utils.ModBannerPatterns;
import net.jukoz.me.resource.data.Alignment;
import net.jukoz.me.resource.data.Race;
import net.jukoz.me.resource.data.faction.Faction;
import net.jukoz.me.resource.data.faction.ModFactions;
import net.jukoz.me.utils.Factions;
import net.jukoz.me.utils.LoggerUtil;
import net.minecraft.block.entity.BannerPattern;
import net.minecraft.block.entity.BannerPatterns;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.DiffuseLighting;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.block.entity.BannerBlockEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.model.ModelLoader;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.component.type.BannerPatternsComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import java.util.*;

@Environment(EnvType.CLIENT)
public class FactionSelectionScreen extends Screen {
    private static final Identifier FACTION_SELECTION_UI = Identifier.of(MiddleEarth.MOD_ID,"textures/gui/faction_selection.png");
    private static final Identifier FACTION_SELECTION_BANNER_UI = Identifier.of(MiddleEarth.MOD_ID,"textures/gui/faction_selection_banner.png");
    private static final Identifier FACTION_SELECTION_BUTTONS = Identifier.of(MiddleEarth.MOD_ID,"textures/gui/faction_selection_buttons.png");


    private static final Identifier MAP_BACKGROUND = Identifier.of(MiddleEarth.MOD_ID,"textures/gui/map_background.png");
    private static final Identifier MAP_UI_TEXTURE = Identifier.of(MiddleEarth.MOD_ID,"textures/gui/map_ui.png");
    private static final Identifier MAP_TEXTURE = Identifier.of(MiddleEarth.MOD_ID,"textures/map.png");
    private static final Text ONBOARDING_TEXT = Text.of("Onboarding");
    private static boolean debug = false;
    AbstractClientPlayerEntity player;
    private static final Quaternionf ENTITY_ROTATION;
    private static final Vector3f VECTOR;
    private static final int MINIMAL_MARGIN = 8;

    private static List<FactionNpcPreviewData> NPC_PREVIEWS = new ArrayList<>();

    private ModelPart bannerField;

    private int currentAlignementIndex = 0;
    private int currentFactionIndex = 0;
    private int currentSubFactionIndex = 0;

    private LongbeardDwarfEntity dwarfEntity;
    private GondorHumanEntity humanEntity;
    private MordorOrcEntity orcEntity;
    private GaladhrimElfEntity elfEntity;
    private ShireHobbitEntity hobbitEntity;

    @Nullable
    LivingEntity entity;
    Map<Alignment, List<Faction>> factions = new HashMap<>();

    public ButtonWidget alignmentButtonLeft;
    public ButtonWidget alignmentButtonRight;
    public ButtonWidget factionButtonLeft;
    public ButtonWidget factionButtonRight;
    public ButtonWidget subfactionButtonLeft;
    public ButtonWidget subfactionButtonRight;
    public ButtonWidget factionRandomizerButton;

    private int mouseX;
    private int mouseY;

    public FactionSelectionScreen() {
        super(ONBOARDING_TEXT);
    }
    @Override
    protected void init() {
        this.bannerField = this.client.getEntityModelLoader().getModelPart(EntityModelLayers.BANNER).getChild("flag");

        // Create faction list
        factions.put(Alignment.Good, ModFactions.getFactions(Alignment.Good));
        factions.put(Alignment.Neutral, ModFactions.getFactions(Alignment.Neutral));
        factions.put(Alignment.Evil,  ModFactions.getFactions(Alignment.Evil));
        // Initialize Buttons
        // Alignment
        ButtonWidget.PressAction alignmentActionLeft = new ButtonWidget.PressAction() {
            @Override
            public void onPress(ButtonWidget button) {
                currentAlignementIndex--;
                if(currentAlignementIndex < 0)
                    currentAlignementIndex = Alignment.values().length - 1;

                currentFactionIndex = 0;
                currentSubFactionIndex = 0;
                updatePreviewEquipment();
            }
        };

        ButtonWidget.PressAction alignmentActionRight = new ButtonWidget.PressAction() {
            @Override
            public void onPress(ButtonWidget button) {
                currentAlignementIndex++;
                if(currentAlignementIndex >= Alignment.values().length)
                    currentAlignementIndex = 0;
                currentFactionIndex = 0;
                currentSubFactionIndex = 0;
                updatePreviewEquipment();
            }
        };

        alignmentButtonLeft = ButtonWidget.builder(Text.of("Left alignment selection clicked"), alignmentActionLeft).build();
        alignmentButtonRight = ButtonWidget.builder(Text.of("Right alignment selection clicked"), alignmentActionRight).build();
        addDrawableChild(alignmentButtonLeft);
        addDrawableChild(alignmentButtonRight);

        // Faction
        ButtonWidget.PressAction factionActionLeft = new ButtonWidget.PressAction() {
            @Override
            public void onPress(ButtonWidget button) {
                currentFactionIndex--;
                if(currentFactionIndex < 0)
                    currentFactionIndex = factions.get(Alignment.values()[currentAlignementIndex]).size() - 1;
                currentSubFactionIndex = 0;
                updateFaction(factions.get(Alignment.values()[currentAlignementIndex]).get(currentFactionIndex));
                updatePreviewEquipment();
            }
        };

        ButtonWidget.PressAction factionActionRight = new ButtonWidget.PressAction() {
            @Override
            public void onPress(ButtonWidget button) {
                currentFactionIndex++;
                if(currentFactionIndex >= factions.get(Alignment.values()[currentAlignementIndex]).size())
                    currentFactionIndex = 0;
                updateFaction(factions.get(Alignment.values()[currentAlignementIndex]).get(currentFactionIndex));
                updatePreviewEquipment();
            }
        };

        factionButtonLeft = ButtonWidget.builder(Text.of("Left faction selection clicked"), factionActionLeft).build();
        factionButtonRight = ButtonWidget.builder(Text.of("Right faction selection clicked"), factionActionRight).build();
        addDrawableChild(factionButtonLeft);
        addDrawableChild(factionButtonRight);

        // Subfaction
        ButtonWidget.PressAction subfactionActionLeft = new ButtonWidget.PressAction() {
            @Override
            public void onPress(ButtonWidget button) {
                currentSubFactionIndex--;
                if(currentSubFactionIndex < 0)
                    currentSubFactionIndex = factions.get(Alignment.values()[currentAlignementIndex]).get(currentFactionIndex).getSubFactions().size() - 1;
                updatePreviewEquipment();
            }
        };

        ButtonWidget.PressAction subfactionActionRight = new ButtonWidget.PressAction() {
            @Override
            public void onPress(ButtonWidget button) {
                currentSubFactionIndex++;
                if(currentSubFactionIndex >= factions.get(Alignment.values()[currentAlignementIndex]).get(currentFactionIndex).getSubFactions().size())
                    currentSubFactionIndex = 0;
                updatePreviewEquipment();
            }
        };

        subfactionButtonLeft = ButtonWidget.builder(Text.of("Left subfaction selection clicked"), subfactionActionLeft).build();
        subfactionButtonRight = ButtonWidget.builder(Text.of("Right subfaction selection clicked"), subfactionActionRight).build();
        addDrawableChild(subfactionButtonLeft);
        addDrawableChild(subfactionButtonRight);

        ButtonWidget.PressAction factionRandomizer = new ButtonWidget.PressAction() {
            private int randomize(int tentativeLeft){
                Random random = new Random();
                // Alignment randomizer
                currentAlignementIndex = random.nextInt(Alignment.values().length);
                Alignment alignment = Alignment.values()[currentAlignementIndex];

                // Recursive trigger
                if(factions.get(alignment) == null || factions.get(alignment).isEmpty()){
                    if(tentativeLeft > 0){
                        return tentativeLeft + randomize(tentativeLeft - 1);
                    }
                }

                // Faction randomizer
                currentFactionIndex =
                        (factions.get(alignment) == null || factions.get(alignment).isEmpty())
                                ? 0
                                : random.nextInt(factions.get(alignment).size());
                Faction faction =
                        (factions.get(alignment) == null || factions.get(alignment).isEmpty() || currentFactionIndex >= factions.get(alignment).size())
                                ? null
                                : factions.get(alignment).get(currentFactionIndex);

                // Subfaction randomizer
                currentSubFactionIndex =
                        (faction == null || faction.getSubFactions() == null || faction.getSubFactions().isEmpty())
                                ? 0
                                : random.nextInt(faction.getSubFactions().size());
                return 0;
            }
            @Override
            public void onPress(ButtonWidget button) {
                this.randomize(5);
                updatePreviewEquipment();
            }
        };
        factionRandomizerButton = ButtonWidget.builder(Text.of("Faction randomizer clicked"), factionRandomizer).build();
        addDrawableChild(factionRandomizerButton);

        humanEntity = new GondorHumanEntity(ModEntities.GONDORIAN_SOLDIER, this.client.world);
        dwarfEntity = new LongbeardDwarfEntity(ModEntities.LONGBEARD_SOLDIER, this.client.world);
        elfEntity = new GaladhrimElfEntity(ModEntities.LORIEN_SOLDIER, this.client.world);
        orcEntity = new MordorOrcEntity(ModEntities.MORDOR_ORC_SOLDIER, this.client.world);
        hobbitEntity = new ShireHobbitEntity(ModEntities.HOBBIT_CIVILIAN, this.client.world);
        updatePreviewEquipment();
    }

    private void updateFaction(Faction faction) {
        currentSubFactionIndex = 0;
    }


    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        Entity cameraEntity = this.client.getCameraEntity();
        if (cameraEntity != null) {
            if (cameraEntity instanceof AbstractClientPlayerEntity abstractClientPlayerEntity) {
                this.mouseX = mouseX;
                this.mouseY = mouseY;
                this.player = abstractClientPlayerEntity;
                this.renderBackground(context, mouseX, mouseY, delta);
                int guiScale = this.client.options.getGuiScale().getValue();
                this.drawPanels(context, guiScale);
            } else {
                this.player = null;
            }
        }
    }

    protected void drawPanels(DrawContext context, int guiScale){
        int mainPanelWidth = 169;
        int mainPanelHeight = 207;

        drawFactionSelectionPanel(context, mainPanelWidth, mainPanelHeight, MINIMAL_MARGIN, mouseX, mouseY);
        drawInformationPanel(context, mainPanelWidth, mainPanelHeight);
        drawMapPanel(context, mainPanelWidth, mainPanelHeight, MINIMAL_MARGIN);
    }

    private void drawInformationPanel(DrawContext context, int mainPanelWidth, int mainPanelHeight) {
        int startX = (int) ((context.getScaledWindowWidth() / 2f) - (mainPanelWidth / 2f));
        int startY = (int) ((context.getScaledWindowHeight() / 2f) - (mainPanelHeight / 2f));

        context.drawTexture(FACTION_SELECTION_UI,
                startX,
                startY,
                0, 0,
                mainPanelWidth,
                mainPanelHeight
        );

        drawFactionBanner(context, startX + mainPanelWidth - 60, startY + 3);
    }

    private void drawFactionSelectionPanel(DrawContext context, int mainPanelWidth, int mainPanelHeight, int minimalMargin, int mouseX, int mouseY) {
        int endX = (int) ((context.getScaledWindowWidth() / 2f) - (mainPanelWidth / 2f) - minimalMargin);
        int startX = Math.max(minimalMargin, endX - mainPanelWidth);
        int startY = (int) ((context.getScaledWindowHeight() / 2f) - (mainPanelHeight / 2f));

        int widthX = endX - Math.max(minimalMargin, startX);

        int centerX = startX + (int) ((widthX / 2f));

        // Draw alignment option
        Alignment alignment = Alignment.values()[currentAlignementIndex];
        Faction faction = (!factions.get(alignment).isEmpty()) ? factions.get(alignment).get(currentFactionIndex) : null;
        Faction subFaction = (faction != null) ? faction.getSubfaction(currentSubFactionIndex) : null;

        boolean showbuttons = Alignment.values().length > 1;
        startY += drawSelectionWidget(context, 0,95, centerX, startY, alignment.getLangKey(), alignmentButtonLeft, alignmentButtonRight, showbuttons);
        showbuttons = (!factions.get(alignment).isEmpty()) ? factions.get(alignment).size() > 1 : false;
        startY += drawSelectionWidget(context, 0,114, centerX, startY, (faction == null) ? null : faction.getLangKey(), factionButtonLeft, factionButtonRight, showbuttons);
        showbuttons = (faction != null) ? (faction.getSubFactions() != null && faction.getSubFactions().size() > 1) : false;
        startY += drawSelectionWidget(context, 0,133, centerX, startY, (subFaction == null) ? null : subFaction.getLangKey(), subfactionButtonLeft, subfactionButtonRight, showbuttons);

        int endY = (int) ((context.getScaledWindowHeight() / 2f) - (mainPanelHeight / 2f)) + mainPanelHeight;
        drawNpcPreview(context, centerX, endY);
        drawFactionRandomizer(context, centerX, endY);
    }

    protected int drawSelectionWidget(DrawContext context, int uvX, int uvY, int centerX, int startY, String key, ButtonWidget buttonLeft, ButtonWidget buttonRight, boolean showButtons){
        int panelSizeX = 102;
        int panelSizeY = 18;
        int buttonSizeX = 9;
        int buttonSizeY = 13;
        int margin = 5;

        if(key == null) {
            buttonLeft.active = false;
            buttonRight.active = false;
            return panelSizeY + margin;
        }

        // Text Background
        context.drawTexture(FACTION_SELECTION_BUTTONS,
                (int) (centerX - (panelSizeX / 2f)),
                startY,
                0, uvY,
                panelSizeX,
                panelSizeY
        );

        // Dynamic text panel
        MutableText text = Text.translatable(key);
        text.asTruncatedString(16);
        context.drawText(textRenderer, text,
                (int)(centerX - textRenderer.getWidth(text) / 2f),
                startY + (int) ((panelSizeY / 2f) - (textRenderer.fontHeight / 2f)),
                0, false);

        if(!showButtons){
            buttonLeft.active = false;
            buttonRight.active = false;
            return panelSizeY + margin;
        }
        buttonLeft.active = true;
        buttonRight.active = true;

        //  Left button
        int leftButtonStartX = (int) (centerX - (panelSizeX / 2f) - buttonSizeX) - margin;
        int leftButtonStartY = (int) (startY + (panelSizeY / 2f) - (buttonSizeY / 2f));

        context.drawTexture(FACTION_SELECTION_BUTTONS,
                leftButtonStartX, leftButtonStartY,
                192, (isMouseOver(leftButtonStartX, buttonSizeX, leftButtonStartY, buttonSizeY)) ? 13 : 0,
                buttonSizeX, buttonSizeY
        );
        buttonRight.setDimensionsAndPosition(buttonSizeX, buttonSizeY, leftButtonStartX, leftButtonStartY);

        //  Right button
        int rightButtonStartX = (int) (centerX + (panelSizeX / 2f)) + margin;
        int rightButtonStartY = (int) (startY + (panelSizeY / 2f) - (buttonSizeY / 2f));

        context.drawTexture(FACTION_SELECTION_BUTTONS,
                rightButtonStartX, rightButtonStartY,
                201, (isMouseOver(rightButtonStartX, buttonSizeX, rightButtonStartY, buttonSizeY)) ? 13 : 0,
                buttonSizeX, buttonSizeY
        );
        buttonLeft.setDimensionsAndPosition(buttonSizeX, buttonSizeY, rightButtonStartX, rightButtonStartY);

        return panelSizeY + margin;
    }

    private boolean isMouseOver(int startX, int sizeX, int startY, int sizeY) {
        return mouseX >= startX && mouseX <= startX + sizeX
                && mouseY >= startY && mouseY <= startY + sizeY;
    }

    protected void drawFactionRandomizer(DrawContext context, int centerX, int endY) {
        int sizeX = 52;
        int sizeY = 18;
        int startX = (int) (centerX - (sizeX / 2f));
        int startY = endY - sizeY;

        context.drawTexture(FACTION_SELECTION_BUTTONS,
                startX,
                startY,
                103, (isMouseOver(startX, sizeX, startY, sizeY)) ? 92 : 74,
                sizeX,
                sizeY
        );
        factionRandomizerButton.setDimensionsAndPosition(sizeX, sizeY, startX, startY);
    }


    private void drawMapPanel(DrawContext context, int mainPanelWidth, int mainPanelHeight, int minimalMargin) {
        int startX = (int) ((context.getScaledWindowWidth() / 2f) + (mainPanelWidth / 2f)) + minimalMargin;
        int widthX = Math.min(context.getScaledWindowWidth() - startX, mainPanelWidth -minimalMargin);
        context.drawTexture(MAP_TEXTURE,
                startX,
                (int) ((context.getScaledWindowHeight() / 2f) - (mainPanelHeight / 2f)),
                0, 0,
                widthX -minimalMargin,
                widthX
        );

        int sizeX = 52;
        int sizeY = 18;

        context.drawTexture(FACTION_SELECTION_BUTTONS,
                (int)(startX + (widthX / 2f)) - (sizeX + 10),
                (int)((context.getScaledWindowHeight() / 2f) - (mainPanelHeight / 2f)) + mainPanelHeight - sizeY,
                103, 111,
                sizeX,
                sizeY
        );

        context.drawTexture(FACTION_SELECTION_BUTTONS,
                (int)(startX + (widthX / 2f)),
                (int)((context.getScaledWindowHeight() / 2f) - (mainPanelHeight / 2f)) + mainPanelHeight - sizeY,
                103, 19,
                sizeX,
                sizeY
        );
    }



    protected void setup() {

    }

    private void updatePreviewEquipment() {
        Alignment alignment = Alignment.values()[currentAlignementIndex];
        Faction faction = (!factions.get(alignment).isEmpty()) ? factions.get(alignment).get(currentFactionIndex) : null;
        Faction subFaction = (faction != null) ? faction.getSubfaction(currentSubFactionIndex) : null;
        updatePreviewEquipment(faction, subFaction);
    }

    private void updatePreviewEquipment(Faction faction, Faction subFaction){
        if(faction == null) {
            this.entity = null;
            return;
        }
        if(subFaction != null) faction = subFaction;

        updateEntityFromFaction(faction);

        if(this.entity == null) return;

        this.entity.bodyYaw = 210f;
        this.entity.setPitch(0f);
        this.entity.headYaw = this.entity.getBodyYaw();
        this.entity.prevHeadYaw = this.entity.getBodyYaw();

        this.entity.equipStack(EquipmentSlot.HEAD, new ItemStack(faction.getPreviewGearAt(EquipmentSlot.HEAD)));
        this.entity.equipStack(EquipmentSlot.CHEST, new ItemStack(faction.getPreviewGearAt(EquipmentSlot.CHEST)));
        this.entity.equipStack(EquipmentSlot.LEGS, new ItemStack(faction.getPreviewGearAt(EquipmentSlot.LEGS)));
        this.entity.equipStack(EquipmentSlot.FEET, new ItemStack(faction.getPreviewGearAt(EquipmentSlot.FEET)));
        this.entity.equipStack(EquipmentSlot.MAINHAND, new ItemStack(faction.getPreviewGearAt(EquipmentSlot.MAINHAND)));
        this.entity.equipStack(EquipmentSlot.OFFHAND, new ItemStack(faction.getPreviewGearAt(EquipmentSlot.OFFHAND)));
    }

    private void updateEntityFromFaction(Faction faction) {
        this.entity =
                switch ( faction.getRace() )
                {
                    case Race.Human -> humanEntity;
                    case Race.Dwarf -> dwarfEntity;
                    case Race.Orc -> orcEntity;
                    case Race.Elf -> elfEntity;
                    case Race.Hobbit -> hobbitEntity;
                    default -> humanEntity;
                };
    }

    private void drawNpcPreview(DrawContext context, float anchorX, float anchorY){
        float size = 50f;
        float x = anchorX;
        float y = anchorY - 18 - MINIMAL_MARGIN;

        DiffuseLighting.disableGuiDepthLighting();
        DiffuseLighting.disableForLevel();
        if(this.entity == null) return;
        InventoryScreen.drawEntity(context, x, y, size, VECTOR, ENTITY_ROTATION, (Quaternionf)null, this.entity);
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

    private void drawFactionBanner(DrawContext context, float startX, float startY){
        // TODO : Make it so day cycle doesn't affect brightness
        DiffuseLighting.disableGuiDepthLighting();

        float size = 32f;

        float x = startX;
        float y = startY;

        int borderMarginX = 2;
        int borderMarginY = 2;

        MatrixStack matrixStack = new MatrixStack();
        matrixStack.translate(x + borderMarginX + (size / 2f) + 4, y + borderMarginY, 0f);
        matrixStack.push();
        matrixStack.scale(-size, size, 0.1f);
        this.bannerField.pitch = 0.0F;

        List<RegistryEntry<BannerPattern>> list = new ArrayList<>();

        var bannerPatternRegistry = this.client.world.getRegistryManager().get(RegistryKeys.BANNER_PATTERN);

        list.add(bannerPatternRegistry.getEntry(BannerPatterns.CURLY_BORDER).get());
        list.add(bannerPatternRegistry.getEntry(BannerPatterns.GRADIENT_UP).get());
        list.add(bannerPatternRegistry.getEntry(BannerPatterns.TRIANGLE_BOTTOM).get());
        list.add(bannerPatternRegistry.getEntry(BannerPatterns.TRIANGLES_BOTTOM).get());
        list.add(bannerPatternRegistry.getEntry(ModBannerPatterns.DRAGON_BANNER_PATTERN).get());

        BannerPatternsComponent bannerPatternsComponent = new BannerPatternsComponent.Builder()
        .add(list.get(0), DyeColor.WHITE)
        .add(list.get(1), DyeColor.BROWN)
        .add(list.get(2), DyeColor.BLACK)
        .add(list.get(3), DyeColor.GREEN)
        .add(list.get(4), DyeColor.RED)
        .build();

        BannerBlockEntityRenderer.renderCanvas(matrixStack, context.getVertexConsumers(), 15728880, OverlayTexture.DEFAULT_UV, this.bannerField, ModelLoader.BANNER_BASE, true, DyeColor.BLUE, bannerPatternsComponent);
        matrixStack.pop();
        context.draw();
        DiffuseLighting.enableGuiDepthLighting();

        context.drawTexture(FACTION_SELECTION_BANNER_UI,
                (int) x - 2,
                (int) y - 2,
                0, 0,
                48,
                112
        );
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

    /*
    private void drawMap(DrawContext context, float anchorX, float anchorY, int guiScale){
        int size = (int) (400 * guiScaleModifier.get(guiScale));

        int margin = 4;

        context.drawTexture(MAP_BACKGROUND,
                (int) anchorX - ((size + margin) / 2),
                (int) anchorY - ((size + margin) / 2),
                0, 0,
                size + margin,
                size + margin,
                size + margin,
                size + margin
        );

        context.drawTexture(MAP_TEXTURE,
                (int) anchorX - (size / 2),
                (int) anchorY - (size / 2),
                0, 0,
                size,
                size,
                size,
                size
        );

        drawMapMarkers(context, (int) anchorX - ((float) size / 2) - margin, (int) anchorY - ((float) size / 2) - margin, size, guiScale);
    }

    private void drawMapMarkers(DrawContext context, float anchorX, float anchorY, int size, int guiScale) {
        // TODO : Clean this up to be data driven!
        int markerSize = (int) (8);
        int x = (int) anchorX;
        int y = (int) anchorY;

        int mapSize = 96000;
        Vector2f coord01 = new Vector2f(48000, 48000);
        coord01.x = ((float) size / mapSize * coord01.x);
        coord01.y = ((float) size / mapSize * coord01.y);

        Vector2f coord02 = new Vector2f(64700, 23000);
        coord02.x = ((float) size / mapSize * coord02.x);
        coord02.y = ((float) size / mapSize * coord02.y);

        Vector2f coord03 = new Vector2f(62000, 57000);
        coord03.x = ((float) size / mapSize * coord03.x);
        coord03.y = ((float) size / mapSize * coord03.y);

        context.drawTexture(MAP_UI_TEXTURE,
                (int) (x + (coord01.x)),
                (int) (y + (coord01.y)),
                54, 0,
                markerSize,
                markerSize
        );

        context.drawTexture(MAP_UI_TEXTURE,
                (int) (x + (coord02.x)),
                (int) (y + (coord02.y)),
                54, 8,
                markerSize,
                markerSize
        );

        context.drawTexture(MAP_UI_TEXTURE,
                (int) (x + (coord03.x)),
                (int) (y + (coord03.y)),
                54, 16,
                markerSize,
                markerSize
        );
    }

     */
}