package net.sevenstars.middleearth.gui.utils.widgets;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.state.EntityRenderState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.entity.npcs.NpcEntity;
import net.sevenstars.middleearth.resources.datas.npcs.NpcUtil;
import net.sevenstars.middleearth.resources.datas.npcs.data.NpcGearData;
import net.sevenstars.middleearth.resources.datas.races.Race;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;

public class PlayableNpcPreviewWidget extends ModWidget{
    private static final Identifier NPC_PREVIEW = Identifier.of(MiddleEarth.MOD_ID,"textures/gui/widget/npc_preview_widget.png");

    private static final int TICK_DELAY = 10;
    private static final int MINIMAL_MARGIN = 4;
    private static final float DEFAULT_ANGLE = 145f; // 210f;
    private static final float SMOOTH_SPEED_MODIFIER = 0.5f ;
    private static final float STEP_SPEED = 35;

    private LivingEntity entity;
    private static final Quaternionf ENTITY_ROTATION;
    private static final Vector3f VECTOR;
    private float currentAngle = DEFAULT_ANGLE ; // 210f;
    private final ButtonWidget leftButton;
    private final ButtonWidget rightButton;
    private final ButtonWidget resetButton;

    private ButtonWidget currentButtonClicked;
    private boolean isLeftButton = false;
    private float tickHoldingStart = 0;
    private boolean isEnterKeyPressed = false;
    public boolean haveDoneStep = false;

    public boolean haveBeenInitialized;
    public PlayableNpcPreviewWidget(){
        ButtonWidget.PressAction leftButtonAction = button -> {
            reduceAngle();
            setCurrentButton(true);
        };
        haveBeenInitialized = false;
        ButtonWidget.PressAction resetButtonAction = button -> {
            currentAngle = DEFAULT_ANGLE;
        };

        ButtonWidget.PressAction rightButtonAction = button -> {
            addAngle();
            setCurrentButton(false);
        };

        leftButton = ButtonWidget.builder(Text.of("left_button"), leftButtonAction).build();
        leftButton.setDimensions(14, 9);

        resetButton = ButtonWidget.builder(Text.of("reset_button"), resetButtonAction).build();
        resetButton.setDimensions(6,6);

        rightButton = ButtonWidget.builder(Text.of("right_button"), rightButtonAction).build();
        rightButton.setDimensions(14, 9);
    }

    private void addAngle(){
        if(!haveDoneStep){
            haveDoneStep = true;
            currentAngle = this.entity.getBodyYaw() - STEP_SPEED;
        }
        if(canRotateSmoothly()){
            currentAngle -= SMOOTH_SPEED_MODIFIER;
        }
    }

    private void reduceAngle(){
        if(!haveDoneStep){
            haveDoneStep =true;
            currentAngle = this.entity.getBodyYaw() + STEP_SPEED;
        }
        if(canRotateSmoothly()){
            currentAngle += SMOOTH_SPEED_MODIFIER;
        }
    }

    public List<ButtonWidget> getButtons(){
        ArrayList<ButtonWidget> listOfButtons = new ArrayList<>();
        listOfButtons.add(leftButton);
        listOfButtons.add(resetButton);
        listOfButtons.add(rightButton);

        return listOfButtons;
    }

    private void setCurrentButton(boolean isLeft){
        if(currentButtonClicked != null) return;

        if(tickHoldingStart == 0)
            this.tickHoldingStart = MinecraftClient.getInstance().inGameHud.getTicks();

        isLeftButton = isLeft;
        if(isLeft){
            this.currentButtonClicked = leftButton;
        } else {
            this.currentButtonClicked = rightButton;
        }
    }

    public void updateEntity(NpcGearData data, Race race, World world) {
        if(world != null)
            haveBeenInitialized = true;

        updateEntityRace(race, world);
        updateEquipment(data);
    }
    public void setEntity(NpcEntity npcEntity) {
        this.entity = npcEntity;
    }


    private void updateEquipment(NpcGearData data){
        if(data == null) {
            this.entity = null;
            return;
        }
        if(this.entity == null) return;

        this.entity.setBodyYaw(currentAngle);
        this.entity.setPitch(0f);
        this.entity.headYaw = this.entity.getBodyYaw();
        this.entity.lastHeadYaw = this.entity.getBodyYaw();

        NpcUtil.equipAll(entity, data);
    }

    private void updateEntityRace(Race race, World world) {
        this.entity = race.getModel(world);
    }

    public void drawCenteredAnchoredBottom(DrawContext context, int centerX, int endY) {
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

        EntityRenderDispatcher entityRenderDispatcher = MinecraftClient.getInstance().getEntityRenderDispatcher();
        EntityRenderer<? super LivingEntity, ?> entityRenderer = entityRenderDispatcher.getRenderer(entity);
        EntityRenderState entityRenderState = entityRenderer.getAndUpdateRenderState(entity, 1.0F);
        entityRenderState.hitbox = null;

        int entityY = y + 50;
        context.addEntity(entityRenderState, 35f, VECTOR, ENTITY_ROTATION, new Quaternionf(), x - 20, entityY - 120, x + 20, entityY);

        int horizontalMargin = MINIMAL_MARGIN + 1;

        if(leftButton.active){
            int width = leftButton.getWidth();
            int height = leftButton.getHeight();
            boolean isMouseOver = isMouseOver(width, height, x - width - horizontalMargin, y - MINIMAL_MARGIN);

            context.drawTexture(RenderPipelines.GUI_TEXTURED, NPC_PREVIEW,
                    x - width - horizontalMargin, y - MINIMAL_MARGIN, 0, (currentButtonClicked != null && isLeftButton) ? 18 : (leftButton.isFocused() || isMouseOver) ? 9 : 0,
                    width, height, 256, 256);

            if(leftButton.isFocused() && getFocusEnabled()){
                context.drawTexture(RenderPipelines.GUI_TEXTURED, NPC_PREVIEW,
                        x - width - horizontalMargin, y - MINIMAL_MARGIN, 0, 27,
                        width, height, 256, 256);
            }

            leftButton.setPosition(x - width - horizontalMargin, y - MINIMAL_MARGIN);
        }

        if(resetButton.active){
            int width = resetButton.getWidth();
            int height = resetButton.getHeight();
            boolean isMouseOver = isMouseOver(width, height, x - (width / 2), y - MINIMAL_MARGIN + 2);

            context.drawTexture(RenderPipelines.GUI_TEXTURED, NPC_PREVIEW,
                    x - 3, y - MINIMAL_MARGIN + 2, 28, (resetButton.isFocused() || isMouseOver) ? 6 : 0,
                    width, height, 256, 256);

            if(resetButton.isFocused() && getFocusEnabled()){
                context.drawTexture(RenderPipelines.GUI_TEXTURED, NPC_PREVIEW,
                        x - 3, y - MINIMAL_MARGIN + 2, 28, 12,
                        width, height, 256, 256);
            }
            resetButton.setPosition(x - (width / 2), y - 2);
        }

        if(rightButton.active){
            int width = rightButton.getWidth();
            int height = rightButton.getHeight();
            boolean isMouseOver = isMouseOver(width, height, x + horizontalMargin, y - MINIMAL_MARGIN);

            context.drawTexture(RenderPipelines.GUI_TEXTURED, NPC_PREVIEW,
                    x + horizontalMargin, y - MINIMAL_MARGIN, 14, (currentButtonClicked != null && !isLeftButton) ? 18 : (rightButton.isFocused() || isMouseOver) ? 9 : 0,
                    width, height, 256, 256);
            if(rightButton.isFocused() && getFocusEnabled()){
                context.drawTexture(RenderPipelines.GUI_TEXTURED, NPC_PREVIEW,
                        x + horizontalMargin, y - MINIMAL_MARGIN, 14, 27,
                        width, height, 256, 256);
            }
            rightButton.setPosition(x + horizontalMargin, y - MINIMAL_MARGIN);
        }

        this.entity.setBodyYaw(currentAngle);
        this.entity.setPitch(0f);
        this.entity.headYaw = this.entity.getBodyYaw();
        this.entity.lastHeadYaw = this.entity.getBodyYaw();
    }


    private boolean canRotateSmoothly(){
        var minimumThreshold = tickHoldingStart + TICK_DELAY;
        var currentHudTime = MinecraftClient.getInstance().inGameHud.getTicks();
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
