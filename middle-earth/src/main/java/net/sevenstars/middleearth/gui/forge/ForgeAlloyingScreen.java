package net.sevenstars.middleearth.gui.forge;

import com.google.common.collect.Lists;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ButtonTextures;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.TexturedButtonWidget;
import net.minecraft.client.gui.widget.ToggleButtonWidget;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.block.special.forge.MetalTypes;
import net.sevenstars.middleearth.network.packets.C2S.ForgeModeSwitchPacket;
import net.sevenstars.middleearth.network.packets.C2S.ForgeOutputPacket;

import java.util.List;

public class ForgeAlloyingScreen extends HandledScreen<ForgeAlloyingScreenHandler> {
    private static final Identifier TEXTURE = MiddleEarth.ofPath( "textures", "gui", "forge.png");

    private static final Identifier EXTRACT_BUTTON = MiddleEarth.of("extract");
    private static final Identifier EXTRACT_BUTTON_DISABLED = MiddleEarth.of("extract_disabled");
    private static final Identifier EXTRACT_BUTTON_FOCUSED = MiddleEarth.of("extract_focused");
    private static final ButtonTextures EXTRACT_BUTTON_TEXTURES = new ButtonTextures(EXTRACT_BUTTON, EXTRACT_BUTTON_DISABLED, EXTRACT_BUTTON_FOCUSED);

    private static final Identifier LEFT_CYCLE_EXTRACT_BUTTON = MiddleEarth.of("left_cycle_arrow");
    private static final Identifier LEFT_CYCLE_EXTRACT_BUTTON_FOCUSED = MiddleEarth.of("left_cycle_arrow_focused");
    private static final ButtonTextures LEFT_CYCLE_EXTRACT_BUTTON_TEXTURES = new ButtonTextures(LEFT_CYCLE_EXTRACT_BUTTON, LEFT_CYCLE_EXTRACT_BUTTON_FOCUSED);

    private static final Identifier RIGHT_CYCLE_EXTRACT_BUTTON = MiddleEarth.of("right_cycle_arrow");
    private static final Identifier RIGHT_CYCLE_EXTRACT_BUTTON_FOCUSED = MiddleEarth.of("right_cycle_arrow_focused");
    private static final ButtonTextures RIGHT_CYCLE_EXTRACT_BUTTON_TEXTURES = new ButtonTextures(RIGHT_CYCLE_EXTRACT_BUTTON, RIGHT_CYCLE_EXTRACT_BUTTON_FOCUSED);

    private static final Identifier ALLOYING_SWITCH_BUTTON = MiddleEarth.of("alloying_mode");
    private static final Identifier ALLOYING_SWITCH_BUTTON_FOCUSED = MiddleEarth.of("alloying_mode_highlighted");
    private static final ButtonTextures ALLOYING_SWITCH_BUTTON_TEXTURES = new ButtonTextures(ALLOYING_SWITCH_BUTTON, ALLOYING_SWITCH_BUTTON_FOCUSED);
    private static final Identifier HEATING_SWITCH_BUTTON = MiddleEarth.of("heating_mode");
    private static final Identifier HEATING_SWITCH_BUTTON_FOCUSED = MiddleEarth.of("heating_mode_highlighted");
    private static final ButtonTextures HEATING_SWITCH_BUTTON_TEXTURES = new ButtonTextures(HEATING_SWITCH_BUTTON, HEATING_SWITCH_BUTTON_FOCUSED);

    private static final int PROGRESS_ARROW_SIZE = 27;
    private static final int COOKING_FIRE_SIZE = 14;
    private static final int LIQUID_HEIGHT = 26;

    private static final int EXTRACT_BUTTON_ITEM_X = 136;
    private static final int EXTRACT_BUTTON_ITEM_Y = 18;
    private static final int EXTRACT_BUTTON_ITEM_U = 235;

    private static final int TEXTURE_SIZE = 256;

    public TexturedButtonWidget extractButton;
    public ToggleButtonWidget leftExtractCycleButton;
    public ToggleButtonWidget rightExtractCycleButton;

    public TexturedButtonWidget modeSwitchToAlloyButton;
    public TexturedButtonWidget modeSwitchToHeatingButton;

    private int outputMode = 0;
    private boolean heatingMode = true;

    public ForgeAlloyingScreen(ForgeAlloyingScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Override
    protected void init() {
        super.init();
        titleX = (backgroundWidth - textRenderer.getWidth(title)) / 2;
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;
        this.heatingMode = handler.heatingMode();

        this.leftExtractCycleButton = new ToggleButtonWidget(x + 121, y + 24, 7 ,11, true);
        this.leftExtractCycleButton.setTextures(LEFT_CYCLE_EXTRACT_BUTTON_TEXTURES);
        this.leftExtractCycleButton.visible = false;

        this.rightExtractCycleButton = new ToggleButtonWidget(x + 162, y + 24, 7,11, true);
        this.rightExtractCycleButton.setTextures(RIGHT_CYCLE_EXTRACT_BUTTON_TEXTURES);
        this.rightExtractCycleButton.visible = false;

        this.extractButton = new TexturedButtonWidget(x + 131, y + 12, 28 ,36, EXTRACT_BUTTON_TEXTURES, (button)-> {
            int amount = 0;
            switch (outputMode){
                case 1 -> amount = 16;
                case 2 -> amount = 144;
                case 3, 4 -> amount = 288;
                case 5 -> amount = 432;
            }

            ClientPlayNetworking.send(new ForgeOutputPacket(amount, handler.getPos().getX(),handler.getPos().getY(),handler.getPos().getZ(), outputMode));
            }, Text.translatable("button." + MiddleEarth.MOD_ID + ".extract_metal")
        );

        if(this.outputMode == 0 && handler.checkMaxOutput() > 0) {
            this.extractButton.setTooltip(Tooltip.of(Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".forge_output_mode_await")));
        } else {
            setExtractButtonTooltip();
        }

        this.modeSwitchToAlloyButton = new TexturedButtonWidget(x + 23, y + 68, 10 ,10, HEATING_SWITCH_BUTTON_TEXTURES, (button)-> {
            ClientPlayNetworking.send(new ForgeModeSwitchPacket(handler.getPos().getX(),handler.getPos().getY(),handler.getPos().getZ()));
            this.modeSwitchToAlloyButton.visible = false;
            this.modeSwitchToAlloyButton.active = false;
            this.modeSwitchToHeatingButton.visible = true;
            this.modeSwitchToHeatingButton.active = true;
        }, Text.translatable("button." + MiddleEarth.MOD_ID + ".switch_mode"));
        this.modeSwitchToHeatingButton = new TexturedButtonWidget(x + 23, y + 60, 10 ,10, ALLOYING_SWITCH_BUTTON_TEXTURES, (button)-> {
            ClientPlayNetworking.send(new ForgeModeSwitchPacket(handler.getPos().getX(),handler.getPos().getY(),handler.getPos().getZ()));
            this.modeSwitchToHeatingButton.visible = false;
            this.modeSwitchToHeatingButton.active = false;
            this.modeSwitchToAlloyButton.visible = true;
            this.modeSwitchToAlloyButton.active = true;
        }, Text.translatable("button." + MiddleEarth.MOD_ID + ".switch_mode"));

        this.modeSwitchToHeatingButton.setTooltip(Tooltip.of(Text.translatable("tooltip." + MiddleEarth.MOD_ID +".forge_mode_switch_heating")));
        this.modeSwitchToAlloyButton.setTooltip(Tooltip.of(Text.translatable("tooltip." + MiddleEarth.MOD_ID +".forge_mode_switch_alloying")));

        addDrawableChild(leftExtractCycleButton);
        addDrawableChild(extractButton);
        addDrawableChild(rightExtractCycleButton);

        addDrawableChild(modeSwitchToHeatingButton);
        addDrawableChild(modeSwitchToAlloyButton);
    }

    @Override
    protected void handledScreenTick() {
        super.handledScreenTick();
        heatingMode = handler.heatingMode();
        updateSwitchState();

        this.leftExtractCycleButton.visible = true;
        this.rightExtractCycleButton.visible = true;
        this.extractButton.visible = true;

        if(handler.checkMaxOutput() == 4 && outputMode >= 5){
            outputMode = 5;
        }
        if(handler.checkMaxOutput() == 3 && outputMode >= 4){
            outputMode = 4;
        }
        if(handler.checkMaxOutput() == 2 && outputMode >= 2){
            outputMode = 2;
        }
        if(handler.checkMaxOutput() == 1 && outputMode >= 1){
            outputMode = 1;
        }
        if(handler.checkMaxOutput() == 0 && outputMode >= 1) {
            outputMode = 0;
        }

        extractButton.active = handler.checkMaxOutput() > 0 && outputMode != 0;

        if(handler.checkMaxOutput() <= 1){
            this.leftExtractCycleButton.visible = false;
            this.rightExtractCycleButton.visible = false;
        } else {
            this.leftExtractCycleButton.visible = true;
            this.rightExtractCycleButton.visible = true;
        }

        if(this.outputMode == 0 && handler.checkMaxOutput() > 0) {
            this.extractButton.setTooltip(Tooltip.of(Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".forge_output_mode_await")));
        } else {
            setExtractButtonTooltip();
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (this.leftExtractCycleButton.mouseClicked(mouseX, mouseY, button)) {
            if(outputMode == 1){
                outputMode = handler.checkMaxOutput();
            } else if(outputMode > 1){
                outputMode--;
            }

            setExtractButtonTooltip();
            return true;
        }

        if (this.rightExtractCycleButton.mouseClicked(mouseX, mouseY, button)) {
            if(outputMode == handler.checkMaxOutput()){
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
        this.extractButton.setTooltip(Tooltip.of(Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".forge_output_mode" + id)));
    }

    protected void updateSwitchState() {
        if(!handler.heatingMode()) {
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
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;

        context.drawTexture(RenderPipelines.GUI_TEXTURED, TEXTURE, this.x, this.y, 0, 0, this.backgroundWidth, this.backgroundHeight + 6, TEXTURE_SIZE, TEXTURE_SIZE);

        context.drawTexture(RenderPipelines.GUI_TEXTURED, TEXTURE, this.x + 26, this.y + 61, 209, 116, 4, 16, TEXTURE_SIZE, TEXTURE_SIZE);

        renderProgressArrow(context, x, y);
        renderLiquidStorage(context, x, y);
    }

    private void renderProgressArrow(DrawContext context, int x, int y) {
        if(heatingMode) {
            context.drawTexture(RenderPipelines.GUI_TEXTURED, TEXTURE, x + 36, y + 45, 202, 0, COOKING_FIRE_SIZE, COOKING_FIRE_SIZE, TEXTURE_SIZE, TEXTURE_SIZE);
            if(handler.isCooking()) {
                int cookingTime = (int) (handler.getScaledCooking() * COOKING_FIRE_SIZE);
                context.drawTexture(RenderPipelines.GUI_TEXTURED, TEXTURE, x + 36, y + COOKING_FIRE_SIZE + 46 - cookingTime, 202, 15 + COOKING_FIRE_SIZE - cookingTime, COOKING_FIRE_SIZE, cookingTime, TEXTURE_SIZE, TEXTURE_SIZE);
            }
        } else {
            context.drawTexture(RenderPipelines.GUI_TEXTURED, TEXTURE, x + 33, y + 45, 218, 0, 20, 13, TEXTURE_SIZE, TEXTURE_SIZE);
            if(handler.isCooking()) {
                int cookingTime = (int) (handler.getScaledCooking() * 15);
                context.drawTexture(RenderPipelines.GUI_TEXTURED, TEXTURE, x + 33, y + 59 - cookingTime, 218, 15 + 14 - cookingTime, 20, cookingTime, TEXTURE_SIZE, TEXTURE_SIZE);
            }
        }

        if(handler.isCrafting()) {
            context.drawTexture(RenderPipelines.GUI_TEXTURED, TEXTURE, x + 90, y + 16, 212, 83, 7, (int) (handler.getScaledProgress() * PROGRESS_ARROW_SIZE), TEXTURE_SIZE, TEXTURE_SIZE);
        }
    }

    private void renderLiquidStorage(DrawContext context, int x, int y) {
        int storedLiquid = (int) (handler.getScaledStoredLiquid() * LIQUID_HEIGHT);
        context.drawTexture(RenderPipelines.GUI_TEXTURED, TEXTURE, x + 73, y + 77 - storedLiquid, 211, 76 - storedLiquid, 20, storedLiquid, TEXTURE_SIZE ,TEXTURE_SIZE);
    }

    private void renderLiquidStorageTooltip(DrawContext context, int mouseX, int mouseY) {
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;

        if (mouseX >= x + 73 && mouseX <= x + 93 && mouseY >= y + 52 && mouseY <= y + 77){
            MetalTypes metal = MetalTypes.getValue(handler.getCurrentMetal());
            if(metal != MetalTypes.EMPTY){
                context.drawOrderedTooltip(this.client.textRenderer, Lists.transform(
                        List.of(Text.translatable("tooltip." + MiddleEarth.MOD_ID +".liquid_" + metal.asString().toLowerCase()).withColor(metal.getColor()),
                                Text.literal(handler.getStoredLiquid() / 144  + " ").append(Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".ingots_number")),
                                Text.literal(handler.getStoredLiquid() % 144 / 16  + " ").append(Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".nuggets_number"))
                        ), Text::asOrderedText), mouseX, mouseY);
            }
        }
    }

    @Override
    protected void drawForeground(DrawContext context, int mouseX, int mouseY) {
        context.drawText(this.textRenderer, this.title, this.titleX, this.titleY, -12566464, false);
        context.drawText(this.textRenderer, this.playerInventoryTitle, this.playerInventoryTitleX, this.playerInventoryTitleY + 7, -12566464, false);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderBackground(context, mouseX,mouseY,delta);
        super.render(context, mouseX, mouseY, delta);
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;
        drawMouseoverTooltip(context, mouseX, mouseY);
        renderLiquidStorageTooltip(context, mouseX, mouseY);

        int v = switch (outputMode) {
            case 1 -> 111;
            case 2 -> 32;
            case 3 -> 85;
            case 4 -> 137;
            case 5 -> 59;
            default -> -1;
        };
        if(v >= 0) context.drawTexture(RenderPipelines.GUI_TEXTURED, TEXTURE, x + EXTRACT_BUTTON_ITEM_X, y + EXTRACT_BUTTON_ITEM_Y,
                EXTRACT_BUTTON_ITEM_U, v,18, 24, TEXTURE_SIZE ,TEXTURE_SIZE);
        else {
            int u = 0;
            if (handler.checkMaxOutput() > 0) {
                u = 16;
            }
            context.drawTexture(RenderPipelines.GUI_TEXTURED, TEXTURE, x + EXTRACT_BUTTON_ITEM_X + 2, y + EXTRACT_BUTTON_ITEM_Y + 4,
                    204 + u, 30,14, 16, TEXTURE_SIZE ,TEXTURE_SIZE);
            context.drawTexture(RenderPipelines.GUI_TEXTURED, TEXTURE, x + 102, y + 57,
                    178, 21,22, 15, TEXTURE_SIZE ,TEXTURE_SIZE);
        }
    }
}
