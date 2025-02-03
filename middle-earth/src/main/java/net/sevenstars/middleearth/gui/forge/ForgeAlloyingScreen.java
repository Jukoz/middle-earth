package net.sevenstars.middleearth.gui.forge;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.render.RenderLayer;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.block.special.forge.MetalTypes;
import net.sevenstars.middleearth.item.ModResourceItems;
import net.sevenstars.middleearth.network.packets.C2S.ForgeOutputPacket;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ButtonTextures;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.TexturedButtonWidget;
import net.minecraft.client.gui.widget.ToggleButtonWidget;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

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

    private static final int PROGRESS_ARROW_SIZE = 24;
    private static final int COOKING_FIRE_SIZE = 14;
    private static final int LIQUID_HEIGHT = 60;

    public TexturedButtonWidget extractButton;
    public ToggleButtonWidget leftExtractCycleButton;
    public ToggleButtonWidget rightExtractCycleButton;

    private int outputMode = 0;
    private Boolean heatingMode = null;

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

        this.rightExtractCycleButton = new ToggleButtonWidget(x + 163, y + 56, 7,11, true);
        this.rightExtractCycleButton.setTextures(RIGHT_CYCLE_EXTRACT_BUTTON_TEXTURES);
        this.rightExtractCycleButton.visible = false;

        addDrawableChild(leftExtractCycleButton);
        addDrawableChild(extractButton);
        addDrawableChild(rightExtractCycleButton);
    }

    @Override
    protected void handledScreenTick() {
        super.handledScreenTick();
        if(heatingMode == null)
            heatingMode = handler.heatingMode();
        else if(handler.heatingMode() != heatingMode) {
            heatingMode = handler.heatingMode();
            this.close();
        }

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
        if(handler.checkMaxOutput() >= 1 && outputMode ==0){
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
        //RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);

        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;

        RenderSystem.setShaderTexture(0, TEXTURE);
        context.drawTexture(RenderLayer::getGuiTextured, TEXTURE, this.x, this.y, 0, 0, this.backgroundWidth, this.backgroundHeight, 256, 256);

        renderProgressArrow(context, x, y);
        renderLiquidStorage(context, x, y);
    }

    private void renderProgressArrow(DrawContext context, int x, int y) {
        if(handler.isCooking()) {
            int cookingTime = (int) (handler.getScaledCooking() * COOKING_FIRE_SIZE);
            context.drawTexture(RenderLayer::getGuiTextured, TEXTURE, x + 42, y + 50 - cookingTime, 176, COOKING_FIRE_SIZE - cookingTime, COOKING_FIRE_SIZE, cookingTime, 256, 256);
        }
        if(handler.isCrafting()) {
            context.drawTexture(RenderLayer::getGuiTextured, TEXTURE, x + 87, y + 15, 176, 14, (int) (handler.getScaledProgress() * PROGRESS_ARROW_SIZE), 17, 256, 256);
        }
    }

    private void renderLiquidStorage(DrawContext context, int x, int y) {
        int storedLiquid = (int) (handler.getScaledStoredLiquid() * LIQUID_HEIGHT);
        context.drawTexture(RenderLayer::getGuiTextured, TEXTURE, x + 113, y + 71 - storedLiquid, 177, 114 - storedLiquid, 16, storedLiquid, 256 ,256);
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

    private void renderModeTooltip(DrawContext context, int mouseX, int mouseY) {
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;

        if (mouseX >= x + 13 && mouseX <= x + 27 && mouseY >= y + 57 && mouseY <= y + 71){
            context.drawTooltip(this.client.textRenderer, Text.translatable("tooltip." + MiddleEarth.MOD_ID +".forge_mode_alloying"), mouseX, mouseY);
        }
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderBackground(context, mouseX,mouseY,delta);
        super.render(context, mouseX, mouseY, delta);
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;
        drawMouseoverTooltip(context, mouseX, mouseY);
        renderLiquidStorageTooltip(context, mouseX, mouseY);
        renderModeTooltip(context, mouseX, mouseY);

        ItemStack itemstack;
        switch (outputMode){
            case 0:
                context.drawTexture(RenderLayer::getGuiTextured, TEXTURE, x + 140, y + 51, 177, 115,22, 22, 256 ,256);
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
                itemstack = new ItemStack(ModResourceItems.ROD);
                context.drawItem(itemstack, x + 143, y + 54);
                break;
            case 4:
                itemstack = new ItemStack(ModResourceItems.LARGE_ROD);
                context.drawItem(itemstack, x + 143, y + 54);
                break;
        }
    }
}
