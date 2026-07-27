package net.sevenstars.middleearth.gui.forge;

import net.neoforged.neoforge.network.PacketDistributor;

import com.google.common.collect.Lists;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.StateSwitchingButton;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.block.special.forge.MetalTypes;
import net.sevenstars.middleearth.network.packets.C2S.ForgeModeSwitchPacket;
import net.sevenstars.middleearth.network.packets.C2S.ForgeOutputPacket;

import java.util.List;

public class ForgeAlloyingScreen extends AbstractContainerScreen<ForgeAlloyingScreenHandler> {
    private static final ResourceLocation TEXTURE = MiddleEarth.ofPath( "textures", "gui", "forge.png");

    private static final ResourceLocation EXTRACT_BUTTON = MiddleEarth.of("extract");
    private static final ResourceLocation EXTRACT_BUTTON_DISABLED = MiddleEarth.of("extract_disabled");
    private static final ResourceLocation EXTRACT_BUTTON_FOCUSED = MiddleEarth.of("extract_focused");
    private static final WidgetSprites EXTRACT_BUTTON_TEXTURES = new WidgetSprites(EXTRACT_BUTTON, EXTRACT_BUTTON_DISABLED, EXTRACT_BUTTON_FOCUSED);

    private static final ResourceLocation LEFT_CYCLE_EXTRACT_BUTTON = MiddleEarth.of("left_cycle_arrow");
    private static final ResourceLocation LEFT_CYCLE_EXTRACT_BUTTON_FOCUSED = MiddleEarth.of("left_cycle_arrow_focused");
    private static final WidgetSprites LEFT_CYCLE_EXTRACT_BUTTON_TEXTURES = new WidgetSprites(LEFT_CYCLE_EXTRACT_BUTTON, LEFT_CYCLE_EXTRACT_BUTTON_FOCUSED);

    private static final ResourceLocation RIGHT_CYCLE_EXTRACT_BUTTON = MiddleEarth.of("right_cycle_arrow");
    private static final ResourceLocation RIGHT_CYCLE_EXTRACT_BUTTON_FOCUSED = MiddleEarth.of("right_cycle_arrow_focused");
    private static final WidgetSprites RIGHT_CYCLE_EXTRACT_BUTTON_TEXTURES = new WidgetSprites(RIGHT_CYCLE_EXTRACT_BUTTON, RIGHT_CYCLE_EXTRACT_BUTTON_FOCUSED);

    private static final ResourceLocation ALLOYING_SWITCH_BUTTON = MiddleEarth.of("alloying_mode");
    private static final ResourceLocation ALLOYING_SWITCH_BUTTON_FOCUSED = MiddleEarth.of("alloying_mode_highlighted");
    private static final WidgetSprites ALLOYING_SWITCH_BUTTON_TEXTURES = new WidgetSprites(ALLOYING_SWITCH_BUTTON, ALLOYING_SWITCH_BUTTON_FOCUSED);
    private static final ResourceLocation HEATING_SWITCH_BUTTON = MiddleEarth.of("heating_mode");
    private static final ResourceLocation HEATING_SWITCH_BUTTON_FOCUSED = MiddleEarth.of("heating_mode_highlighted");
    private static final WidgetSprites HEATING_SWITCH_BUTTON_TEXTURES = new WidgetSprites(HEATING_SWITCH_BUTTON, HEATING_SWITCH_BUTTON_FOCUSED);

    private static final int PROGRESS_ARROW_SIZE = 27;
    private static final int COOKING_FIRE_SIZE = 14;
    public static final int LIQUID_HEIGHT = 26;

    private static final int EXTRACT_BUTTON_ITEM_X = 136;
    private static final int EXTRACT_BUTTON_ITEM_Y = 18;
    private static final int EXTRACT_BUTTON_ITEM_U = 235;

    private static final int TEXTURE_SIZE = 256;

    public ImageButton extractButton;
    public StateSwitchingButton leftExtractCycleButton;
    public StateSwitchingButton rightExtractCycleButton;

    public ImageButton modeSwitchToAlloyButton;
    public ImageButton modeSwitchToHeatingButton;

    private int outputMode = 0;
    private boolean heatingMode = true;

    public ForgeAlloyingScreen(ForgeAlloyingScreenHandler handler, Inventory inventory, Component title) {
        super(handler, inventory, title);
    }

    @Override
    protected void init() {
        super.init();
        titleLabelX = (imageWidth - font.width(title)) / 2;
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;
        this.heatingMode = menu.heatingMode();

        this.leftExtractCycleButton = new StateSwitchingButton(x + 121, y + 24, 7 ,11, true);
        this.leftExtractCycleButton.initTextureValues(LEFT_CYCLE_EXTRACT_BUTTON_TEXTURES);
        this.leftExtractCycleButton.visible = false;

        this.rightExtractCycleButton = new StateSwitchingButton(x + 162, y + 24, 7,11, true);
        this.rightExtractCycleButton.initTextureValues(RIGHT_CYCLE_EXTRACT_BUTTON_TEXTURES);
        this.rightExtractCycleButton.visible = false;

        this.extractButton = new ImageButton(x + 131, y + 12, 28 ,36, EXTRACT_BUTTON_TEXTURES, (button)-> {
            int amount = 0;
            switch (outputMode){
                case 1 -> amount = 16;
                case 2 -> amount = 144;
                case 3, 4 -> amount = 288;
                case 5 -> amount = 432;
            }

            PacketDistributor.sendToServer(new ForgeOutputPacket(amount, menu.getPos().getX(),menu.getPos().getY(),menu.getPos().getZ(), outputMode));
            }, Component.translatable("button." + MiddleEarth.MOD_ID + ".extract_metal")
        );

        if(this.outputMode == 0 && menu.checkMaxOutput() > 0) {
            this.extractButton.setTooltip(Tooltip.create(Component.translatable("tooltip." + MiddleEarth.MOD_ID + ".forge_output_mode_await")));
        } else {
            setExtractButtonTooltip();
        }

        this.modeSwitchToAlloyButton = new ImageButton(x + 23, y + 68, 10 ,10, HEATING_SWITCH_BUTTON_TEXTURES, (button)-> {
            PacketDistributor.sendToServer(new ForgeModeSwitchPacket(menu.getPos().getX(),menu.getPos().getY(),menu.getPos().getZ()));
            this.modeSwitchToAlloyButton.visible = false;
            this.modeSwitchToAlloyButton.active = false;
            this.modeSwitchToHeatingButton.visible = true;
            this.modeSwitchToHeatingButton.active = true;
        }, Component.translatable("button." + MiddleEarth.MOD_ID + ".switch_mode"));
        this.modeSwitchToHeatingButton = new ImageButton(x + 23, y + 60, 10 ,10, ALLOYING_SWITCH_BUTTON_TEXTURES, (button)-> {
            PacketDistributor.sendToServer(new ForgeModeSwitchPacket(menu.getPos().getX(),menu.getPos().getY(),menu.getPos().getZ()));
            this.modeSwitchToHeatingButton.visible = false;
            this.modeSwitchToHeatingButton.active = false;
            this.modeSwitchToAlloyButton.visible = true;
            this.modeSwitchToAlloyButton.active = true;
        }, Component.translatable("button." + MiddleEarth.MOD_ID + ".switch_mode"));

        this.modeSwitchToHeatingButton.setTooltip(Tooltip.create(Component.translatable("tooltip." + MiddleEarth.MOD_ID +".forge_mode_switch_heating")));
        this.modeSwitchToAlloyButton.setTooltip(Tooltip.create(Component.translatable("tooltip." + MiddleEarth.MOD_ID +".forge_mode_switch_alloying")));

        addRenderableWidget(leftExtractCycleButton);
        addRenderableWidget(extractButton);
        addRenderableWidget(rightExtractCycleButton);

        addRenderableWidget(modeSwitchToHeatingButton);
        addRenderableWidget(modeSwitchToAlloyButton);
    }

    @Override
    protected void containerTick() {
        super.containerTick();
        heatingMode = menu.heatingMode();
        updateSwitchState();

        this.leftExtractCycleButton.visible = true;
        this.rightExtractCycleButton.visible = true;
        this.extractButton.visible = true;

        if(menu.checkMaxOutput() == 4 && outputMode >= 5){
            outputMode = 5;
        }
        if(menu.checkMaxOutput() == 3 && outputMode >= 4){
            outputMode = 4;
        }
        if(menu.checkMaxOutput() == 2 && outputMode >= 2){
            outputMode = 2;
        }
        if(menu.checkMaxOutput() == 1 && outputMode >= 1){
            outputMode = 1;
        }
        if(menu.checkMaxOutput() == 0 && outputMode >= 1) {
            outputMode = 0;
        }

        extractButton.active = menu.checkMaxOutput() > 0 && outputMode != 0;

        if(menu.checkMaxOutput() <= 1){
            this.leftExtractCycleButton.visible = false;
            this.rightExtractCycleButton.visible = false;
        } else {
            this.leftExtractCycleButton.visible = true;
            this.rightExtractCycleButton.visible = true;
        }

        if(this.outputMode == 0 && menu.checkMaxOutput() > 0) {
            this.extractButton.setTooltip(Tooltip.create(Component.translatable("tooltip." + MiddleEarth.MOD_ID + ".forge_output_mode_await")));
        } else {
            setExtractButtonTooltip();
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (this.leftExtractCycleButton.mouseClicked(mouseX, mouseY, button)) {
            if(outputMode == 1){
                outputMode = menu.checkMaxOutput();
            } else if(outputMode > 1){
                outputMode--;
            }

            setExtractButtonTooltip();
            return true;
        }

        if (this.rightExtractCycleButton.mouseClicked(mouseX, mouseY, button)) {
            if(outputMode == menu.checkMaxOutput()){
                outputMode = 1;
            } else if(outputMode < 5){
                outputMode++;
            }

            setExtractButtonTooltip();
            return true;
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    protected void setExtractButtonTooltip() {
        int id = outputMode;
        if(outputMode == 4) id = 3;
        this.extractButton.setTooltip(Tooltip.create(Component.translatable("tooltip." + MiddleEarth.MOD_ID + ".forge_output_mode" + id)));
    }

    protected void updateSwitchState() {
        if(!menu.heatingMode()) {
            this.modeSwitchToHeatingButton.visible = false;
            this.modeSwitchToHeatingButton.active = false;
            this.modeSwitchToAlloyButton.visible = true;
            this.modeSwitchToAlloyButton.active = true;
        } else {
            this.modeSwitchToHeatingButton.visible = true;
            this.modeSwitchToHeatingButton.active = true;
            this.modeSwitchToAlloyButton.visible = false;
            this.modeSwitchToAlloyButton.active = false;
        }
    }

    @Override
    protected void renderBg(GuiGraphics context, float delta, int mouseX, int mouseY) {
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        context.blit(TEXTURE, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight + 6, TEXTURE_SIZE, TEXTURE_SIZE);

        context.blit(TEXTURE, this.leftPos + 26, this.topPos + 61, 209, 116, 4, 16, TEXTURE_SIZE, TEXTURE_SIZE);

        renderProgressArrow(context, x, y);
        renderLiquidStorage(context, x, y);
    }

    private void renderProgressArrow(GuiGraphics context, int x, int y) {
        if(heatingMode) {
            context.blit(TEXTURE, x + 36, y + 45, 202, 0, COOKING_FIRE_SIZE, COOKING_FIRE_SIZE, TEXTURE_SIZE, TEXTURE_SIZE);
            if(menu.isCooking()) {
                int cookingTime = (int) (menu.getScaledCooking() * COOKING_FIRE_SIZE);
                context.blit(TEXTURE, x + 36, y + COOKING_FIRE_SIZE + 46 - cookingTime, 202, 15 + COOKING_FIRE_SIZE - cookingTime, COOKING_FIRE_SIZE, cookingTime, TEXTURE_SIZE, TEXTURE_SIZE);
            }
        } else {
            context.blit(TEXTURE, x + 33, y + 45, 218, 0, 20, 13, TEXTURE_SIZE, TEXTURE_SIZE);
            if(menu.isCooking()) {
                int cookingTime = (int) (menu.getScaledCooking() * 15);
                context.blit(TEXTURE, x + 33, y + 59 - cookingTime, 218, 15 + 14 - cookingTime, 20, cookingTime, TEXTURE_SIZE, TEXTURE_SIZE);
            }
        }

        if(menu.isCrafting()) {
            context.blit(TEXTURE, x + 90, y + 16, 212, 83, 7, (int) (menu.getScaledProgress() * PROGRESS_ARROW_SIZE), TEXTURE_SIZE, TEXTURE_SIZE);
        }
    }

    private void renderLiquidStorage(GuiGraphics context, int x, int y) {
        int storedLiquid = (int) (menu.getScaledStoredLiquid() * LIQUID_HEIGHT);
        context.blit(TEXTURE, x + 73, y + 77 - storedLiquid, 211, 76 - storedLiquid, 20, storedLiquid, TEXTURE_SIZE ,TEXTURE_SIZE);
    }

    private void renderLiquidStorageTooltip(GuiGraphics context, int mouseX, int mouseY) {
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        if (mouseX >= x + 73 && mouseX <= x + 93 && mouseY >= y + 52 && mouseY <= y + 77){
            MetalTypes metal = MetalTypes.getValue(menu.getCurrentMetal());
            if(metal != MetalTypes.EMPTY){
                context.renderTooltip(this.minecraft.font, Lists.transform(
                        List.of(Component.translatable("tooltip." + MiddleEarth.MOD_ID +".liquid_" + metal.getSerializedName().toLowerCase()).withColor(metal.getColor()),
                                Component.literal(menu.getStoredLiquid() / 144  + " ").append(Component.translatable("tooltip." + MiddleEarth.MOD_ID + ".ingots_number")),
                                Component.literal(menu.getStoredLiquid() % 144 / 16  + " ").append(Component.translatable("tooltip." + MiddleEarth.MOD_ID + ".nuggets_number"))
                        ), Component::getVisualOrderText), mouseX, mouseY);
            }
        }
    }

    @Override
    protected void renderLabels(GuiGraphics context, int mouseX, int mouseY) {
        context.drawString(this.font, this.title, this.titleLabelX, this.titleLabelY, -12566464, false);
        context.drawString(this.font, this.playerInventoryTitle, this.inventoryLabelX, this.inventoryLabelY + 7, -12566464, false);
    }

    @Override
    public void render(GuiGraphics context, int mouseX, int mouseY, float delta) {
        renderBackground(context, mouseX,mouseY,delta);
        super.render(context, mouseX, mouseY, delta);
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;
        renderTooltip(context, mouseX, mouseY);
        renderLiquidStorageTooltip(context, mouseX, mouseY);

        int v = switch (outputMode) {
            case 1 -> 111;
            case 2 -> 32;
            case 3 -> 85;
            case 4 -> 137;
            case 5 -> 59;
            default -> -1;
        };
        if(v >= 0) context.blit(TEXTURE, x + EXTRACT_BUTTON_ITEM_X, y + EXTRACT_BUTTON_ITEM_Y,
                EXTRACT_BUTTON_ITEM_U, v,18, 24, TEXTURE_SIZE ,TEXTURE_SIZE);
        else {
            int u = 0;
            if (menu.checkMaxOutput() > 0) {
                u = 16;
            }
            context.blit(TEXTURE, x + EXTRACT_BUTTON_ITEM_X + 2, y + EXTRACT_BUTTON_ITEM_Y + 4,
                    204 + u, 30,14, 16, TEXTURE_SIZE ,TEXTURE_SIZE);
            context.blit(TEXTURE, x + 102, y + 57,
                    178, 21,22, 15, TEXTURE_SIZE ,TEXTURE_SIZE);
        }
    }
}
