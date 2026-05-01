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
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.block.special.forge.MetalTypes;
import net.sevenstars.middleearth.item.ResourceItemsME;
import net.sevenstars.middleearth.network.packets.C2S.ForgeModeSwitchPacket;
import net.sevenstars.middleearth.network.packets.C2S.ForgeOutputPacket;

import java.util.List;

public class ForgeAlloyingScreen extends HandledScreen<ForgeAlloyingScreenHandler> {
    private static final Identifier TEXTURE = Identifier.of(MiddleEarth.MOD_ID, "textures/gui/forge.png");

    private static final Identifier EXTRACT_BUTTON = Identifier.of(MiddleEarth.MOD_ID, "extract");
    private static final Identifier EXTRACT_BUTTON_FOCUSED = Identifier.of(MiddleEarth.MOD_ID, "extract_focused");
    private static final ButtonTextures EXTRACT_BUTTON_TEXTURES = new ButtonTextures(EXTRACT_BUTTON, EXTRACT_BUTTON_FOCUSED);

    private static final Identifier LEFT_CYCLE_EXTRACT_BUTTON = Identifier.of(MiddleEarth.MOD_ID, "left_cycle_arrow");
    private static final Identifier LEFT_CYCLE_EXTRACT_BUTTON_FOCUSED = Identifier.of(MiddleEarth.MOD_ID, "left_cycle_arrow_focused");
    private static final ButtonTextures LEFT_CYCLE_EXTRACT_BUTTON_TEXTURES = new ButtonTextures(LEFT_CYCLE_EXTRACT_BUTTON, LEFT_CYCLE_EXTRACT_BUTTON_FOCUSED);

    private static final Identifier RIGHT_CYCLE_EXTRACT_BUTTON = Identifier.of(MiddleEarth.MOD_ID, "right_cycle_arrow");
    private static final Identifier RIGHT_CYCLE_EXTRACT_BUTTON_FOCUSED = Identifier.of(MiddleEarth.MOD_ID, "right_cycle_arrow_focused");
    private static final ButtonTextures RIGHT_CYCLE_EXTRACT_BUTTON_TEXTURES = new ButtonTextures(RIGHT_CYCLE_EXTRACT_BUTTON, RIGHT_CYCLE_EXTRACT_BUTTON_FOCUSED);

    private static final Identifier ALLOYING_SWITCH_BUTTON = Identifier.of(MiddleEarth.MOD_ID, "alloying_mode");
    private static final Identifier ALLOYING_SWITCH_BUTTON_FOCUSED = Identifier.of(MiddleEarth.MOD_ID, "alloying_mode_highlighted");
    private static final ButtonTextures ALLOYING_SWITCH_BUTTON_TEXTURES = new ButtonTextures(ALLOYING_SWITCH_BUTTON, ALLOYING_SWITCH_BUTTON_FOCUSED);

    private static final int PROGRESS_ARROW_SIZE = 24;
    private static final int COOKING_FIRE_SIZE = 14;
    private static final int LIQUID_HEIGHT = 60;

    private static final int TEXTURE_SIZE = 256;

    public TexturedButtonWidget extractButton;
    public ToggleButtonWidget leftExtractCycleButton;
    public ToggleButtonWidget rightExtractCycleButton;

    public TexturedButtonWidget modeSwitchButton;

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

        this.leftExtractCycleButton = new ToggleButtonWidget(x + 132, y + 56, 7 ,11, true);
        this.leftExtractCycleButton.setTextures(LEFT_CYCLE_EXTRACT_BUTTON_TEXTURES);
        this.leftExtractCycleButton.visible = false;

        this.extractButton = new TexturedButtonWidget(x + 141, y + 52, 20 ,20, EXTRACT_BUTTON_TEXTURES, (button)-> {
            int amount = 0;
            switch (outputMode){
                case 1 -> amount = 16;
                case 2 -> amount = 144;
                case 3 -> amount = 288;
                case 4 -> amount = 432;
            }

            ClientPlayNetworking.send(new ForgeOutputPacket(amount, handler.getPos().getX(),handler.getPos().getY(),handler.getPos().getZ()));
            }, Text.translatable("button." + MiddleEarth.MOD_ID + ".extract_metal"));

        this.extractButton.setTooltip(Tooltip.of(Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".forge_output_mode" + this.outputMode)));

        this.modeSwitchButton = new TexturedButtonWidget(x + 13, y + 48, 20 ,24, ALLOYING_SWITCH_BUTTON_TEXTURES, (button)-> {
            ClientPlayNetworking.send(new ForgeModeSwitchPacket(handler.getPos().getX(),handler.getPos().getY(),handler.getPos().getZ()));
        }, Text.translatable("button." + MiddleEarth.MOD_ID + ".switch_mode"));

        this.modeSwitchButton.setTooltip(Tooltip.of(Text.translatable("tooltip." + MiddleEarth.MOD_ID +".forge_mode_switch_alloying")));

        this.rightExtractCycleButton = new ToggleButtonWidget(x + 163, y + 56, 7,11, true);
        this.rightExtractCycleButton.setTextures(RIGHT_CYCLE_EXTRACT_BUTTON_TEXTURES);
        this.rightExtractCycleButton.visible = false;

        addDrawableChild(leftExtractCycleButton);
        addDrawableChild(extractButton);
        addDrawableChild(rightExtractCycleButton);

        addDrawableChild(modeSwitchButton);
    }

    @Override
    protected void handledScreenTick() {
        super.handledScreenTick();
        heatingMode = handler.heatingMode();
        /*
        else if(handler.heatingMode() != heatingMode) {
            heatingMode = handler.heatingMode();
            this.close();
        }*/

        this.leftExtractCycleButton.visible = true;
        this.rightExtractCycleButton.visible = true;
        this.extractButton.visible = true;

        if(handler.checkMaxOutput() == 4 && outputMode >= 4){
            outputMode = 4;
        }
        if(handler.checkMaxOutput() == 3 && outputMode >= 3){
            outputMode = 3;
        }
        if(handler.checkMaxOutput() == 2 && outputMode >= 2){
            outputMode = 2;
        }
        if(handler.checkMaxOutput() == 1 && outputMode >= 1){
            outputMode = 1;
        }
        if(handler.checkMaxOutput() == 0 && outputMode >= 1){
            outputMode = 0;
        }
        if(handler.checkMaxOutput() >= 1 && outputMode == 0){
            outputMode = 1;
        }
        if(handler.checkMaxOutput() <= 1){
            this.leftExtractCycleButton.visible = false;
            this.rightExtractCycleButton.visible = false;
        } else {
            this.leftExtractCycleButton.visible = true;
            this.rightExtractCycleButton.visible = true;
        }

        this.extractButton.setTooltip(Tooltip.of(Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".forge_output_mode" + this.outputMode)));
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (this.leftExtractCycleButton.mouseClicked(mouseX, mouseY, button)) {
            if(outputMode == 1){
                outputMode = handler.checkMaxOutput();
            } else if(outputMode > 1){
                outputMode--;
            }

            this.extractButton.setTooltip(Tooltip.of(Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".forge_output_mode" + this.outputMode)));
            return true;
        }

        if (this.rightExtractCycleButton.mouseClicked(mouseX, mouseY, button)) {
            if(outputMode == handler.checkMaxOutput()){
                outputMode = 1;
            } else if(outputMode < 4){
                outputMode++;
            }

            this.extractButton.setTooltip(Tooltip.of(Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".forge_output_mode" + this.outputMode)));
            return true;
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;

        context.drawTexture(RenderPipelines.GUI_TEXTURED, TEXTURE, this.x, this.y, 0, 0, this.backgroundWidth, this.backgroundHeight + 6, TEXTURE_SIZE, TEXTURE_SIZE);

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
            context.drawTexture(RenderPipelines.GUI_TEXTURED, TEXTURE, x + 87, y + 15, 176, 14, (int) (handler.getScaledProgress() * PROGRESS_ARROW_SIZE), 17, TEXTURE_SIZE, TEXTURE_SIZE);
        }
    }

    private void renderLiquidStorage(DrawContext context, int x, int y) {
        int storedLiquid = (int) (handler.getScaledStoredLiquid() * LIQUID_HEIGHT);
        context.drawTexture(RenderPipelines.GUI_TEXTURED, TEXTURE, x + 113, y + 71 - storedLiquid, 177, 114 - storedLiquid, 16, storedLiquid, TEXTURE_SIZE ,TEXTURE_SIZE);
    }

    private void renderLiquidStorageTooltip(DrawContext context, int mouseX, int mouseY) {
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;

        if (mouseX >= x + 113 && mouseX <= x + 128 && mouseY >= y + 12 && mouseY <= y + 71){
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

        ItemStack itemstack;
        switch (outputMode){
            case 0:
                context.drawTexture(RenderPipelines.GUI_TEXTURED, TEXTURE, x + 140, y + 51, 177, 115,22, 22, TEXTURE_SIZE ,TEXTURE_SIZE);
                break;
            case 1:
                itemstack = new ItemStack(Items.IRON_NUGGET);
                context.drawItem(itemstack, x + 143, y + 54);
                break;
            case 2:
                itemstack = new ItemStack(Items.IRON_INGOT);
                context.drawItem(itemstack, x + 143, y + 54);
                break;
            case 3:
                itemstack = new ItemStack(ResourceItemsME.ROD);
                context.drawItem(itemstack, x + 143, y + 54);
                break;
            case 4:
                itemstack = new ItemStack(ResourceItemsME.LARGE_ROD);
                context.drawItem(itemstack, x + 143, y + 54);
                break;
        }
    }
}
