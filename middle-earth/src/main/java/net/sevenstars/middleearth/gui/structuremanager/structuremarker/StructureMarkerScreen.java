package net.sevenstars.middleearth.gui.structuremanager.structuremarker;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.registry.Registries;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.block.special.structureManager.marker.MarkerKind;
import net.sevenstars.middleearth.block.special.structureManager.marker.StructureMarkerBlockEntity;
import net.sevenstars.middleearth.gui.structuremanager.ChoiceListWidget;
import net.sevenstars.middleearth.gui.structuremanager.ChoiceListWidget.Choice;

import java.util.ArrayList;
import java.util.List;

@Environment(value = EnvType.CLIENT)
public class StructureMarkerScreen extends HandledScreen<StructureMarkerScreenHandler> {
    private static final int KIND_WIDTH = 120;
    private static final int VALUE_WIDTH = 160;
    private static final int LIST_HEIGHT = 150;
    private static final int ROW_HEIGHT = 16;
    private static final int GAP = 10;
    private static final int BUTTON_WIDTH = 80;
    private static final int BUTTON_HEIGHT = 20;
    private static final int STEP_WIDTH = 20;
    private static final int COUNT_WIDTH = 30;
    private static final int OPTION_WIDTH = 110;
    private static final int LABEL_COLOR = 0xFFA0A0A0;
    private static final int TEXT_COLOR = 0xFFFFFFFF;
    private static final int BIG_STEP = 8;

    private ChoiceListWidget kindList;
    private ChoiceListWidget valueList;
    private ButtonWidget minusButton;
    private ButtonWidget plusButton;
    private ButtonWidget respawnsButton;
    private ButtonWidget facingButton;
    private int count;
    private boolean respawns;
    private Direction facing;
    private MarkerKind shownKind;
    private int countX;
    private int countY;

    public StructureMarkerScreen(StructureMarkerScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Override
    protected void init() {
        super.init();
        StructureMarkerScreenData data = handler.getData();
        count = Math.clamp(data.count(), 1, StructureMarkerBlockEntity.MAXIMUM_COUNT);
        respawns = data.respawns();
        facing = data.facing();

        int left = width / 2 - (KIND_WIDTH + GAP + VALUE_WIDTH) / 2;
        int top = height / 2 - LIST_HEIGHT / 2 - BUTTON_HEIGHT;

        List<Choice> kinds = new ArrayList<>();
        for(MarkerKind kind : MarkerKind.values())
            kinds.add(new Choice(kind.asString(), ChoiceListWidget.readable(kind.asString())));
        kindList = new ChoiceListWidget(KIND_WIDTH, LIST_HEIGHT, left, top, ROW_HEIGHT);
        kindList.setChoices(kinds, data.kind().asString());
        addDrawableChild(kindList);

        valueList = new ChoiceListWidget(VALUE_WIDTH, LIST_HEIGHT, left + KIND_WIDTH + GAP, top, ROW_HEIGHT);
        addDrawableChild(valueList.newSearchField(textRenderer));
        addDrawableChild(valueList);

        int optionTop = top + LIST_HEIGHT + GAP;
        countX = left + STEP_WIDTH;
        countY = optionTop + (BUTTON_HEIGHT - textRenderer.fontHeight) / 2;
        minusButton = addDrawableChild(ButtonWidget.builder(Text.literal("-"), button -> count = Math.max(1, count - step()))
                .dimensions(left, optionTop, STEP_WIDTH, BUTTON_HEIGHT).build());
        plusButton = addDrawableChild(ButtonWidget.builder(Text.literal("+"), button -> count = Math.min(StructureMarkerBlockEntity.MAXIMUM_COUNT, count + step()))
                .dimensions(left + STEP_WIDTH + COUNT_WIDTH, optionTop, STEP_WIDTH, BUTTON_HEIGHT).build());
        respawnsButton = addDrawableChild(ButtonWidget.builder(respawnsText(), button -> {
            respawns = !respawns;
            button.setMessage(respawnsText());
        }).dimensions(left + 2 * STEP_WIDTH + COUNT_WIDTH + GAP, optionTop, OPTION_WIDTH, BUTTON_HEIGHT).build());
        facingButton = addDrawableChild(ButtonWidget.builder(facingText(), button -> {
            facing = facing.rotateYClockwise();
            button.setMessage(facingText());
        }).dimensions(left + 2 * STEP_WIDTH + COUNT_WIDTH + 2 * GAP + OPTION_WIDTH, optionTop, OPTION_WIDTH, BUTTON_HEIGHT).build());

        int buttonTop = optionTop + BUTTON_HEIGHT + GAP;
        int buttons = data.isNew() ? 2 : 3;
        int buttonLeft = width / 2 - (buttons * BUTTON_WIDTH + (buttons - 1) * GAP) / 2;
        addDrawableChild(ButtonWidget.builder(Text.translatable("ui.%s.structure_marker.save".formatted(MiddleEarth.MOD_ID)), button -> {
            MarkerKind kind = selectedKind();
            boolean spawnsNpc = kind == MarkerKind.SPAWN_NPC;
            boolean spawnsAnimal = kind == MarkerKind.SPAWN_ANIMAL;
            handler.save(kind, spawnsNpc || spawnsAnimal ? valueList.selectedValue() : "", spawnsNpc || spawnsAnimal ? count : 1, spawnsAnimal && respawns, facing);
            close();
        }).dimensions(buttonLeft, buttonTop, BUTTON_WIDTH, BUTTON_HEIGHT).build());
        if(!data.isNew()) {
            buttonLeft += BUTTON_WIDTH + GAP;
            addDrawableChild(ButtonWidget.builder(Text.translatable("ui.%s.structure_marker.remove".formatted(MiddleEarth.MOD_ID)), button -> {
                handler.remove();
                close();
            }).dimensions(buttonLeft, buttonTop, BUTTON_WIDTH, BUTTON_HEIGHT).build());
        }
        addDrawableChild(ButtonWidget.builder(ScreenTexts.CANCEL, button -> close())
                .dimensions(buttonLeft + BUTTON_WIDTH + GAP, buttonTop, BUTTON_WIDTH, BUTTON_HEIGHT).build());

        shownKind = null;
        refresh(data.value());
    }

    private int step() {
        return hasShiftDown() ? BIG_STEP : 1;
    }

    private MarkerKind selectedKind() {
        String selected = kindList.selectedValue();
        for(MarkerKind kind : MarkerKind.values()) {
            if(kind.asString().equals(selected))
                return kind;
        }
        return MarkerKind.GUARD_POST;
    }

    // What the middle list holds and which options show depend on the kind
    private void refresh(String current) {
        MarkerKind kind = selectedKind();
        if(kind != shownKind) {
            shownKind = kind;
            List<Choice> choices = new ArrayList<>();
            if(kind == MarkerKind.SPAWN_NPC) {
                for(String nestKind : handler.getData().nestKinds())
                    choices.add(new Choice(nestKind, ChoiceListWidget.readable(nestKind)));
            } else if(kind == MarkerKind.SPAWN_ANIMAL) {
                for(String animal : handler.getData().animals())
                    choices.add(new Choice(animal, animalName(animal)));
            }
            valueList.setChoices(choices, current);
        }
        boolean spawns = kind == MarkerKind.SPAWN_NPC || kind == MarkerKind.SPAWN_ANIMAL;
        valueList.active = spawns;
        minusButton.visible = spawns;
        plusButton.visible = spawns;
        respawnsButton.visible = kind == MarkerKind.SPAWN_ANIMAL;
    }

    private Text respawnsText() {
        return Text.translatable("ui.%s.structure_marker.respawns".formatted(MiddleEarth.MOD_ID), respawns ? ScreenTexts.ON : ScreenTexts.OFF);
    }

    private Text facingText() {
        return Text.translatable("ui.%s.structure_marker.facing".formatted(MiddleEarth.MOD_ID), ChoiceListWidget.readable(facing.asString()));
    }

    private static Text animalName(String id) {
        Identifier identifier = Identifier.tryParse(id);
        return identifier == null ? Text.literal(id) : Registries.ENTITY_TYPE.get(identifier).getName();
    }

    @Override
    protected void drawBackground(DrawContext context, float deltaTicks, int mouseX, int mouseY) {
    }

    @Override
    protected void drawForeground(DrawContext context, int mouseX, int mouseY) {
    }

    // A container screen closes on the inventory key, which is a letter somebody searching wants to type
    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if(keyCode != GLFW.GLFW_KEY_ESCAPE && getFocused() instanceof TextFieldWidget field && field.isFocused()) {
            field.keyPressed(keyCode, scanCode, modifiers);
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    // A container screen keeps the wheel to itself, and the lists never scrolled. It goes to the list under the mouse
    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
        for(ChoiceListWidget list : new ChoiceListWidget[]{kindList, valueList}) {
            if(list != null && list.isMouseOver(mouseX, mouseY))
                return list.mouseScrolled(mouseX, mouseY, horizontalAmount, verticalAmount);
        }
        return super.mouseScrolled(mouseX, mouseY, horizontalAmount, verticalAmount);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
        refresh("");
        renderBackground(context, mouseX, mouseY, deltaTicks);
        super.render(context, mouseX, mouseY, deltaTicks);
        int labelY = valueList.labelY(textRenderer);
        context.drawCenteredTextWithShadow(textRenderer, Text.translatable("ui.%s.structure_marker.kind_label".formatted(MiddleEarth.MOD_ID)),
                kindList.getX() + KIND_WIDTH / 2, labelY, LABEL_COLOR);
        context.drawCenteredTextWithShadow(textRenderer, Text.translatable("ui.%s.structure_marker.value_label".formatted(MiddleEarth.MOD_ID)),
                valueList.getX() + VALUE_WIDTH / 2, labelY, LABEL_COLOR);
        if(minusButton.visible)
            context.drawCenteredTextWithShadow(textRenderer, Text.literal(Integer.toString(count)), countX + COUNT_WIDTH / 2, countY, TEXT_COLOR);
    }
}
