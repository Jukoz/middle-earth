package net.jukoz.me.client.screens.utils.widgets;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.datafixers.types.Func;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.entity.ModEntities;
import net.jukoz.me.entity.beasts.trolls.TrollEntity;
import net.jukoz.me.entity.beasts.trolls.snow.SnowTrollEntity;
import net.jukoz.me.entity.beasts.trolls.stone.StoneTrollEntity;
import net.jukoz.me.entity.dwarves.longbeards.LongbeardDwarfEntity;
import net.jukoz.me.entity.elves.galadhrim.GaladhrimElfEntity;
import net.jukoz.me.entity.hobbits.shire.ShireHobbitEntity;
import net.jukoz.me.entity.humans.bandit.BanditHumanEntity;
import net.jukoz.me.entity.humans.gondor.GondorHumanEntity;
import net.jukoz.me.entity.orcs.mordor.MordorOrcEntity;
import net.jukoz.me.entity.snail.SnailEntity;
import net.jukoz.me.entity.uruks.mordor.MordorBlackUrukEntity;
import net.jukoz.me.item.items.weapons.ReachWeaponItem;
import net.jukoz.me.resources.datas.Alignment;
import net.jukoz.me.resources.datas.Race;
import net.jukoz.me.resources.datas.faction.Faction;
import net.jukoz.me.resources.datas.faction.utils.FactionNpcPreviewData;
import net.jukoz.me.utils.LoggerUtil;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.render.DiffuseLighting;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class PlayableNpcPreviewWidget extends ModWidget{
    private static final Identifier NPC_PREVIEW = Identifier.of(MiddleEarth.MOD_ID,"textures/gui/widget/npc_preview_widget.png");

    private static final int MINIMAL_MARGIN = 4;
    private static final float DEFAULT_ANGLE = 145f; // 210f;
    private static final float SMOOTH_THRESHOLD = 15000;
    private static final float SMOOTH_SPEED_MODIFIER = 2.5f ;
    private static final float STEP_SPEED = 45;
    private BanditHumanEntity defaultEntity;
    private LongbeardDwarfEntity dwarfEntity;
    private GondorHumanEntity humanEntity;
    private MordorOrcEntity orcEntity;
    private MordorBlackUrukEntity urukEntity;
    private GaladhrimElfEntity elfEntity;
    private ShireHobbitEntity hobbitEntity;
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


    public PlayableNpcPreviewWidget(World world){
        humanEntity = new GondorHumanEntity(ModEntities.GONDORIAN_SOLDIER, world);
        humanEntity.setAiDisabled(true);

        dwarfEntity = new LongbeardDwarfEntity(ModEntities.LONGBEARD_SOLDIER, world);
        dwarfEntity.setAiDisabled(true);

        elfEntity = new GaladhrimElfEntity(ModEntities.LORIEN_SOLDIER, world);
        elfEntity.setAiDisabled(true);

        orcEntity = new MordorOrcEntity(ModEntities.MORDOR_ORC_SOLDIER, world);
        orcEntity.setAiDisabled(true);

        urukEntity = new MordorBlackUrukEntity(ModEntities.MORDOR_BLACK_URUK_SOLDIER, world);
        urukEntity.setAiDisabled(true);

        hobbitEntity = new ShireHobbitEntity(ModEntities.HOBBIT_CIVILIAN, world);
        hobbitEntity.setAiDisabled(true);

        defaultEntity = new BanditHumanEntity(ModEntities.BANDIT_MILITIA, world);

        ButtonWidget.PressAction leftButtonAction = button -> {
            addAngle();
            setCurrentButton(true);
        };

        ButtonWidget.PressAction resetButtonAction = button -> {
            currentAngle = DEFAULT_ANGLE;
        };

        ButtonWidget.PressAction rightButtonAction = button -> {
            reduceAngle();
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
        if(canRotateSmoothly()){
            currentAngle += 1 * SMOOTH_SPEED_MODIFIER;
        } else {
            currentAngle += STEP_SPEED;
        }
    }

    private void reduceAngle(){
        if(canRotateSmoothly()){
            currentAngle -= 1 * SMOOTH_SPEED_MODIFIER;
        } else {
            currentAngle -= STEP_SPEED;
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
        isLeftButton = isLeft;
        if(isLeft){
            this.currentButtonClicked = leftButton;
        } else {
            this.currentButtonClicked = rightButton;
        }
        this.tickHoldingStart = 0;
    }

    public void updateEntity(FactionNpcPreviewData data, Race race) {
        updateEntityRace(race);
        updateEquipment(data);
    }

    public void updateToDefaultEntity() {
        this.entity = defaultEntity;
    }

    private void updateEquipment(FactionNpcPreviewData data){
        if(data == null) {
            this.entity = null;
            return;
        }
        if(this.entity == null) return;

        this.entity.bodyYaw = currentAngle;
        this.entity.setPitch(0f);
        this.entity.headYaw = this.entity.getBodyYaw();
        this.entity.prevHeadYaw = this.entity.getBodyYaw();

        this.entity.equipStack(EquipmentSlot.HEAD, data.get(EquipmentSlot.HEAD));
        this.entity.equipStack(EquipmentSlot.CHEST, data.get(EquipmentSlot.CHEST));
        this.entity.equipStack(EquipmentSlot.LEGS, data.get(EquipmentSlot.LEGS));
        this.entity.equipStack(EquipmentSlot.FEET, data.get(EquipmentSlot.FEET));

        ItemStack mainHandItem = data.get(EquipmentSlot.MAINHAND);
        ItemStack offhandItem = data.get(EquipmentSlot.OFFHAND);

        if(mainHandItem != null && mainHandItem.getItem() instanceof ReachWeaponItem reachWeaponItem && reachWeaponItem.type.twoHanded){
            this.entity.equipStack(EquipmentSlot.MAINHAND, mainHandItem);
            this.entity.equipStack(EquipmentSlot.OFFHAND, new ItemStack(Items.AIR));
        } else if(offhandItem != null && offhandItem.getItem() instanceof ReachWeaponItem reachWeaponItem && reachWeaponItem.type.twoHanded){
            this.entity.equipStack(EquipmentSlot.MAINHAND, offhandItem);
            this.entity.equipStack(EquipmentSlot.OFFHAND, new ItemStack(Items.AIR));
        } else {
            this.entity.equipStack(EquipmentSlot.MAINHAND, mainHandItem);
            this.entity.equipStack(EquipmentSlot.OFFHAND, offhandItem);
        }
    }

    private void updateEntityRace(Race race) {
        this.entity =
                switch ( race )
                {
                    case Race.HUMAN -> humanEntity;
                    case Race.DWARF -> dwarfEntity;
                    case Race.ORC -> orcEntity;
                    case Race.URUK -> urukEntity;
                    case Race.ELF -> elfEntity;
                    case Race.HOBBIT -> hobbitEntity;
                    default -> defaultEntity;
                };
    }

    public void drawCenteredAnchoredBottom(DrawContext context, int centerX, int endY) {
        float size = 35f;
        int x = centerX;
        int y = endY;

        DiffuseLighting.disableGuiDepthLighting();
        DiffuseLighting.disableForLevel();
        if(this.entity == null) return;

        if(currentButtonClicked != null){
            if(isEnterKeyPressed || isMouseOver(currentButtonClicked.getWidth(), currentButtonClicked.getHeight(), currentButtonClicked.getX(), currentButtonClicked.getY())) {
                tickHoldingStart += MinecraftClient.getInstance().inGameHud.getTicks();
                if(canRotateSmoothly())
                    currentButtonClicked.onPress();
            }
            else{
                resetCurrentButton();
                LoggerUtil.logDebugMsg("Was out of button reach");
            }
        }

        // TODO : Find a way to display the entity behind the buttons.
        InventoryScreen.drawEntity(context, x, y - 9, size, VECTOR, ENTITY_ROTATION, (Quaternionf)null, this.entity);
        int horizontalMargin = MINIMAL_MARGIN + 1;

        if(leftButton.active){
            int width = leftButton.getWidth();
            int height = leftButton.getHeight();
            boolean isMouseOver = isMouseOver(width, height, x - width - horizontalMargin, y - MINIMAL_MARGIN);
            context.drawTexture(NPC_PREVIEW,
                    x - width - horizontalMargin, y - MINIMAL_MARGIN,
                    0, (currentButtonClicked != null && isLeftButton) ? 18
                            : (leftButton.isFocused() || isMouseOver) ? 9 : 0,
                    width, height
            );
            if(leftButton.isFocused() && getFocusEnabled()){
                context.drawTexture(NPC_PREVIEW,
                        x - width - horizontalMargin, y - MINIMAL_MARGIN,
                        0, 27,
                        width, height
                );
            }

            leftButton.setPosition(x - width - horizontalMargin, y - MINIMAL_MARGIN);
        }

        if(resetButton.active){
            int width = resetButton.getWidth();
            int height = resetButton.getHeight();
            boolean isMouseOver = isMouseOver(width, height, x - (width / 2), y - MINIMAL_MARGIN + 2);

            context.drawTexture(NPC_PREVIEW,
                    x - 3, y - MINIMAL_MARGIN + 2,
                    28, (resetButton.isFocused() || isMouseOver) ? 6 : 0,
                    width, height
            );
            if(resetButton.isFocused() && getFocusEnabled()){
                context.drawTexture(NPC_PREVIEW,
                        x - 3, y - MINIMAL_MARGIN + 2,
                        28, 12,
                        width, height
                );
            }
            resetButton.setPosition(x - (width / 2), y - 2);
        }

        if(rightButton.active){
            int width = rightButton.getWidth();
            int height = rightButton.getHeight();
            boolean isMouseOver = isMouseOver(width, height, x + horizontalMargin, y - MINIMAL_MARGIN);

            context.drawTexture(NPC_PREVIEW,
                    x + horizontalMargin, y - MINIMAL_MARGIN,
                    14, (currentButtonClicked != null && !isLeftButton) ? 18
                        : (rightButton.isFocused() || isMouseOver) ? 9 : 0,
                    width, height
            );
            if(rightButton.isFocused() && getFocusEnabled()){
                context.drawTexture(NPC_PREVIEW,
                        x + horizontalMargin, y - MINIMAL_MARGIN,
                        14, 27,
                        width, height
                );
            }
            rightButton.setPosition(x + horizontalMargin, y - MINIMAL_MARGIN);
        }

        this.entity.bodyYaw = currentAngle;
        this.entity.setPitch(0f);
        this.entity.headYaw = this.entity.getBodyYaw();
        this.entity.prevHeadYaw = this.entity.getBodyYaw();
    }


    private boolean canRotateSmoothly(){
        return tickHoldingStart >= SMOOTH_THRESHOLD;
    }

    private void resetCurrentButton(){
        currentButtonClicked = null;
        tickHoldingStart = 0;
        isEnterKeyPressed = false;
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
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
        return true;
    }

    @Override
    public boolean keyReleased(int keyCode, int scanCode, int modifiers) {
        resetCurrentButton();
        return true;
    }

    static {
        VECTOR = new Vector3f(0, 0, 0);
        // Vanilla values from SmithingScreen
        ENTITY_ROTATION = (new Quaternionf()).rotationXYZ(0.43633232F, 0.0F, 3.1415927F);
    }
}
