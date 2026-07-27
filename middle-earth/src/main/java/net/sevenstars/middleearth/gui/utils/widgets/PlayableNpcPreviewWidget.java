package net.sevenstars.middleearth.gui.utils.widgets;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.entity.npcs.NpcEntity;
import net.sevenstars.middleearth.entity.npcs.initializer.NpcEntityInitializerClient;
import net.sevenstars.middleearth.entity.npcs.initializer.NpcEntityBuilder;
import net.sevenstars.middleearth.resources.datas.races.Race;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;

public class PlayableNpcPreviewWidget extends ModWidget{
    private static final ResourceLocation NPC_PREVIEW = ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID,"textures/gui/widget/npc_preview_widget.png");

    private static final int TICK_DELAY = 10;
    private static final int MINIMAL_MARGIN = 4;
    private static final float DEFAULT_ANGLE = 145f; // 210f;
    private static final float SMOOTH_SPEED_MODIFIER = 0.5f ;
    private static final float STEP_SPEED = 35;

    private LivingEntity entity;
    private static final Quaternionf ENTITY_ROTATION;
    private static final Vector3f VECTOR;
    private float currentAngle = DEFAULT_ANGLE ; // 210f;
    private final Button leftButton;
    private final Button rightButton;
    private final Button resetButton;

    private Button currentButtonClicked;
    private boolean isLeftButton = false;
    private float tickHoldingStart = 0;
    private boolean isEnterKeyPressed = false;
    public boolean haveDoneStep = false;

    public boolean haveBeenInitialized;

    public PlayableNpcPreviewWidget(){
        Button.OnPress leftButtonAction = button -> {
            reduceAngle();
            setCurrentButton(true);
        };
        haveBeenInitialized = false;
        Button.OnPress resetButtonAction = button -> {
            currentAngle = DEFAULT_ANGLE;
        };

        Button.OnPress rightButtonAction = button -> {
            addAngle();
            setCurrentButton(false);
        };

        leftButton = Button.builder(Component.nullToEmpty("left_button"), leftButtonAction).build();
        leftButton.setSize(14, 9);

        resetButton = Button.builder(Component.nullToEmpty("reset_button"), resetButtonAction).build();
        resetButton.setSize(6,6);

        rightButton = Button.builder(Component.nullToEmpty("right_button"), rightButtonAction).build();
        rightButton.setSize(14, 9);
    }

    private void addAngle(){
        if(!haveDoneStep){
            haveDoneStep = true;
            currentAngle = this.entity.getVisualRotationYInDegrees() - STEP_SPEED;
        }
        if(canRotateSmoothly()){
            currentAngle -= SMOOTH_SPEED_MODIFIER;
        }
    }

    private void reduceAngle(){
        if(!haveDoneStep){
            haveDoneStep =true;
            currentAngle = this.entity.getVisualRotationYInDegrees() + STEP_SPEED;
        }
        if(canRotateSmoothly()){
            currentAngle += SMOOTH_SPEED_MODIFIER;
        }
    }

    public List<Button> getButtons(){
        ArrayList<Button> listOfButtons = new ArrayList<>();
        listOfButtons.add(leftButton);
        listOfButtons.add(resetButton);
        listOfButtons.add(rightButton);

        return listOfButtons;
    }

    private void setCurrentButton(boolean isLeft){
        if(currentButtonClicked != null) return;

        if(tickHoldingStart == 0)
            this.tickHoldingStart = Minecraft.getInstance().gui.getGuiTicks();

        isLeftButton = isLeft;
        if(isLeft){
            this.currentButtonClicked = leftButton;
        } else {
            this.currentButtonClicked = rightButton;
        }
    }

    public void updateEntity(ResourceLocation npcDataIdentifier, Race race, ClientLevel world, boolean deactivatedAI) {
        if(world != null)
            haveBeenInitialized = true;

        NpcEntity npcEntity = new NpcEntityBuilder(world, null)
                .withNpcType(npcDataIdentifier)
                .forceBuild();
        npcEntity.prepare();
        npcEntity.setNoAi(deactivatedAI);

        npcEntity.setYBodyRot(currentAngle);
        npcEntity.setXRot(0f);
        npcEntity.yHeadRotO =npcEntity.getVisualRotationYInDegrees();
        npcEntity.yHeadRot = npcEntity.getVisualRotationYInDegrees();

        NpcEntityInitializerClient.initializeNpcEntity(world, npcEntity);

        this.entity = npcEntity;
    }

    public void drawCenteredAnchoredBottom(GuiGraphics context, int centerX, int endY) {
        float size = 35f;
        int x = centerX;
        int y = endY;

        if(this.entity == null) return;

        if(currentButtonClicked != null){
            if(tickHoldingStart > 0 && (isEnterKeyPressed || isMouseOver(currentButtonClicked.getWidth(), currentButtonClicked.getHeight(), currentButtonClicked.getX(), currentButtonClicked.getY()))) {
                if(canRotateSmoothly())
                    currentButtonClicked.onPress();
            }
            else{
                resetCurrentButton();
            }
        }

        int entityY = y + 85;
        InventoryScreen.renderEntityInInventory(
                context,
                x,
                entityY,
                35.0F,
                VECTOR,
                ENTITY_ROTATION,
                new Quaternionf(),
                entity
        );

        int horizontalMargin = MINIMAL_MARGIN + 1;

        if(leftButton.active){
            int width = leftButton.getWidth();
            int height = leftButton.getHeight();
            boolean isMouseOver = isMouseOver(width, height, x - width - horizontalMargin, y - MINIMAL_MARGIN);

            context.blit(NPC_PREVIEW,
                    x - width - horizontalMargin, y - MINIMAL_MARGIN, 0, (currentButtonClicked != null && isLeftButton) ? 18 : (leftButton.isFocused() || isMouseOver) ? 9 : 0,
                    width, height, 256, 256);

            if(leftButton.isFocused() && getFocusEnabled()){
                context.blit(NPC_PREVIEW,
                        x - width - horizontalMargin, y - MINIMAL_MARGIN, 0, 27,
                        width, height, 256, 256);
            }

            leftButton.setPosition(x - width - horizontalMargin, y - MINIMAL_MARGIN);
        }

        if(resetButton.active){
            int width = resetButton.getWidth();
            int height = resetButton.getHeight();
            boolean isMouseOver = isMouseOver(width, height, x - (width / 2), y - MINIMAL_MARGIN + 2);

            context.blit(NPC_PREVIEW,
                    x - 3, y - MINIMAL_MARGIN + 2, 28, (resetButton.isFocused() || isMouseOver) ? 6 : 0,
                    width, height, 256, 256);

            if(resetButton.isFocused() && getFocusEnabled()){
                context.blit(NPC_PREVIEW,
                        x - 3, y - MINIMAL_MARGIN + 2, 28, 12,
                        width, height, 256, 256);
            }
            resetButton.setPosition(x - (width / 2), y - 2);
        }

        if(rightButton.active){
            int width = rightButton.getWidth();
            int height = rightButton.getHeight();
            boolean isMouseOver = isMouseOver(width, height, x + horizontalMargin, y - MINIMAL_MARGIN);

            context.blit(NPC_PREVIEW,
                    x + horizontalMargin, y - MINIMAL_MARGIN, 14, (currentButtonClicked != null && !isLeftButton) ? 18 : (rightButton.isFocused() || isMouseOver) ? 9 : 0,
                    width, height, 256, 256);
            if(rightButton.isFocused() && getFocusEnabled()){
                context.blit(NPC_PREVIEW,
                        x + horizontalMargin, y - MINIMAL_MARGIN, 14, 27,
                        width, height, 256, 256);
            }
            rightButton.setPosition(x + horizontalMargin, y - MINIMAL_MARGIN);
        }

        this.entity.setYBodyRot(currentAngle);
        this.entity.setXRot(0f);
        this.entity.yHeadRot = this.entity.getVisualRotationYInDegrees();
        this.entity.yHeadRotO = this.entity.getVisualRotationYInDegrees();
    }


    private boolean canRotateSmoothly(){
        var minimumThreshold = tickHoldingStart + TICK_DELAY;
        var currentHudTime = Minecraft.getInstance().gui.getGuiTicks();
        return currentHudTime > minimumThreshold;
    }

    private void resetCurrentButton(){
        currentButtonClicked = null;
        tickHoldingStart = 0;
        isEnterKeyPressed = false;
        haveDoneStep = false;
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        resetCurrentButton();
        return super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public boolean keyReleased(int keyCode, int scanCode, int modifiers) {
        resetCurrentButton();
        return true;
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if(keyCode == 257){
            if((leftButton.isFocused() || rightButton.isFocused())){
                if(!isEnterKeyPressed){
                    isEnterKeyPressed = true;
                } else{
                    return false;
                }
            }
        }
        return false;
    }

    static {
        VECTOR = new Vector3f(0, 0, 0);
        // Vanilla values from SmithingScreen
        ENTITY_ROTATION = (new Quaternionf()).rotationXYZ(0.43633232F, 0.0F, 3.1415927F);
    }
}
