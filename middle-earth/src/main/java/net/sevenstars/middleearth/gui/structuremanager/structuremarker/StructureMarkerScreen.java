package net.sevenstars.middleearth.gui.structuremanager.structuremarker;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.block.special.structureManager.marker.StructureMarkerBlockEntity;
import org.lwjgl.glfw.GLFW;

@Environment(value = EnvType.CLIENT)
public class StructureMarkerScreen extends HandledScreen<StructureMarkerScreenHandler> {
    private static final int WIDTH = 150;
    private static final int ROW_HEIGHT = 20;
    private static final int LABEL_GAP = 12;
    private static final int ROW_GAP = 6;
    private static final int LABEL_COLOR = 0xFFA0A0A0;
    private static final int MAXIMUM_COUNT_DIGITS = 2;

    private ButtonWidget kindButton;
    private ButtonWidget respawnsButton;
    private TextFieldWidget valueField;
    private TextFieldWidget countField;
    private int valueY;
    private int countY;

    public StructureMarkerScreen(StructureMarkerScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Override
    protected void init() {
        super.init();
        int left = width / 2 - WIDTH / 2;
        int top = height / 2 - 2 * ROW_HEIGHT - LABEL_GAP;
        kindButton = ButtonWidget.builder(kindText(), button -> {
            handler.selectNext();
            button.setMessage(kindText());
        }).dimensions(left, top, WIDTH, ROW_HEIGHT).build();
        addDrawableChild(kindButton);

        valueY = top + ROW_HEIGHT + ROW_GAP + LABEL_GAP;
        valueField = new TextFieldWidget(textRenderer, left, valueY, WIDTH, ROW_HEIGHT, Text.empty());
        valueField.setMaxLength(StructureMarkerBlockEntity.MAXIMUM_VALUE_LENGTH);
        valueField.setText(handler.getValue());
        valueField.setChangedListener(handler::setValue);
        addDrawableChild(valueField);

        countY = valueY + ROW_HEIGHT + ROW_GAP + LABEL_GAP;
        countField = new TextFieldWidget(textRenderer, left, countY, WIDTH, ROW_HEIGHT, Text.empty());
        countField.setMaxLength(MAXIMUM_COUNT_DIGITS);
        countField.setTextPredicate(text -> text.chars().allMatch(Character::isDigit));
        countField.setText(Integer.toString(handler.getCount()));
        countField.setChangedListener(text -> {
            if(!text.isEmpty())
                handler.setCount(Integer.parseInt(text));
        });
        addDrawableChild(countField);

        respawnsButton = ButtonWidget.builder(respawnsText(), button -> {
            handler.toggleRespawns();
            button.setMessage(respawnsText());
        }).dimensions(left, countY + ROW_HEIGHT + ROW_GAP, WIDTH, ROW_HEIGHT).build();
        addDrawableChild(respawnsButton);
    }

    private Text kindText() {
        return Text.translatable("ui.%s.structure_marker.kind".formatted(MiddleEarth.MOD_ID),
                Text.translatable("structure_marker.%s.%s".formatted(MiddleEarth.MOD_ID, handler.getKind().asString())));
    }

    private Text respawnsText() {
        return Text.translatable("ui.%s.structure_marker.respawns".formatted(MiddleEarth.MOD_ID), ScreenTexts.onOrOff(handler.getRespawns()));
    }

    // The letters typed in a field are also the key that closes the screen
    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if(keyCode == GLFW.GLFW_KEY_ESCAPE) {
            close();
            return true;
        }
        for(TextFieldWidget field : new TextFieldWidget[]{valueField, countField}) {
            if(field.keyPressed(keyCode, scanCode, modifiers) || field.isActive())
                return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    protected void drawBackground(DrawContext context, float deltaTicks, int mouseX, int mouseY) {
    }

    @Override
    protected void drawForeground(DrawContext context, int mouseX, int mouseY) {
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
        renderBackground(context, mouseX, mouseY, deltaTicks);
        super.render(context, mouseX, mouseY, deltaTicks);
        int left = width / 2 - WIDTH / 2;
        context.drawTextWithShadow(textRenderer, Text.translatable("ui.%s.structure_marker.value".formatted(MiddleEarth.MOD_ID)), left, valueY - LABEL_GAP, LABEL_COLOR);
        context.drawTextWithShadow(textRenderer, Text.translatable("ui.%s.structure_marker.count".formatted(MiddleEarth.MOD_ID)), left, countY - LABEL_GAP, LABEL_COLOR);
    }
}
