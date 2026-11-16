package net.sevenstars.middleearth.gui.structuremanager.structuremarker;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.text.MutableText;
import net.minecraft.text.OrderedText;
import net.sevenstars.middleearth.gui.structuremanager.EditorPanel;
import java.util.Map;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.registry.Registries;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.block.special.beds.BedTag;
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
    private static final int KIND_WIDTH = 118;
    private static final int VALUE_WIDTH = 176;
    private static final int LEAST_LIST_HEIGHT = 56;
    private static final int MOST_LIST_HEIGHT = 130;
    private static final int ROW_HEIGHT = 14;
    // Tall enough for an item icon
    private static final int KIND_ROW_HEIGHT = 18;
    private static final int GAP = 6;
    private static final int BUTTON_WIDTH = 64;
    private static final int BUTTON_HEIGHT = 20;
    private static final int STEP_WIDTH = 20;
    private static final int COUNT_WIDTH = 30;
    private static final int OPTION_WIDTH = 96;
    private static final int BIG_STEP = 8;
    // Distance from the top of a list to its label, covering the label, the search box and the gaps between them
    private static final int LIST_HEAD = 30;
    private static final int SEARCH_HEAD = 18;
    // An icon for each kind so it can be recognized at a glance
    private static final Map<MarkerKind, Item> ICONS = Map.of(
            MarkerKind.GUARD_POST, Items.IRON_SWORD,
            MarkerKind.ENTRANCE, Items.OAK_DOOR,
            MarkerKind.LOOKOUT, Items.SPYGLASS,
            MarkerKind.LORD_SEAT, Items.GOLDEN_HELMET,
            MarkerKind.PATROL_POINT, Items.COMPASS,
            MarkerKind.MINE_FACE, Items.IRON_PICKAXE,
            MarkerKind.SPAWN_NPC, Items.PLAYER_HEAD,
            MarkerKind.SPAWN_ANIMAL, Items.SADDLE,
            MarkerKind.CELL_CORNER, Items.IRON_BARS,
            MarkerKind.OFF_LIMITS, Items.BARRIER);

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
    private TextFieldWidget valueSearch;
    private int windowX;
    private int windowY;
    private int windowWidth;
    private int windowHeight;
    private int listHeight;

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

        listHeight = Math.clamp(height - 150, LEAST_LIST_HEIGHT, MOST_LIST_HEIGHT);
        windowWidth = EditorPanel.PADDING * 2 + KIND_WIDTH + GAP + VALUE_WIDTH;
        windowHeight = EditorPanel.TITLE_HEIGHT + 1 + EditorPanel.PADDING + LIST_HEAD + listHeight + GAP + textRenderer.fontHeight + GAP + BUTTON_HEIGHT
                + EditorPanel.PADDING + EditorPanel.FOOTER_HEIGHT;
        windowX = (width - windowWidth) / 2;
        windowY = (height - windowHeight) / 2;
        int left = windowX + EditorPanel.PADDING;
        int top = windowY + EditorPanel.TITLE_HEIGHT + 1 + EditorPanel.PADDING + LIST_HEAD;

        List<Choice> kinds = new ArrayList<>();
        for(MarkerKind kind : MarkerKind.values())
            kinds.add(new Choice(kind.asString(), ChoiceListWidget.readable(kind.asString()), null, new ItemStack(ICONS.getOrDefault(kind, Items.AIR))));
        // It has no search box of its own, so it starts level with the search box of the list beside it
        kindList = new ChoiceListWidget(KIND_WIDTH, listHeight + SEARCH_HEAD, left, top - SEARCH_HEAD, KIND_ROW_HEIGHT);
        kindList.setChoices(kinds, data.kind().asString());
        addDrawableChild(kindList);

        valueList = new ChoiceListWidget(VALUE_WIDTH, listHeight, left + KIND_WIDTH + GAP, top, ROW_HEIGHT);
        valueSearch = addDrawableChild(valueList.newSearchField(textRenderer));
        addDrawableChild(valueList);

        int optionTop = top + listHeight + GAP + textRenderer.fontHeight + GAP;
        countX = left + STEP_WIDTH;
        countY = optionTop + (BUTTON_HEIGHT - textRenderer.fontHeight) / 2 + 1;
        minusButton = addDrawableChild(ButtonWidget.builder(Text.literal("-"), button -> count = Math.max(1, count - step())).tooltip(Tooltip.of(ui("count_tip")))
                .dimensions(left, optionTop, STEP_WIDTH, BUTTON_HEIGHT).build());
        plusButton = addDrawableChild(ButtonWidget.builder(Text.literal("+"), button -> count = Math.min(StructureMarkerBlockEntity.MAXIMUM_COUNT, count + step())).tooltip(Tooltip.of(ui("count_tip")))
                .dimensions(left + STEP_WIDTH + COUNT_WIDTH, optionTop, STEP_WIDTH, BUTTON_HEIGHT).build());
        int right = windowX + windowWidth - EditorPanel.PADDING;
        facingButton = addDrawableChild(ButtonWidget.builder(facingText(), button -> {
            facing = facing.rotateYClockwise();
            button.setMessage(facingText());
        }).tooltip(Tooltip.of(ui("facing_tip"))).dimensions(right - OPTION_WIDTH, optionTop, OPTION_WIDTH, BUTTON_HEIGHT).build());
        respawnsButton = addDrawableChild(ButtonWidget.builder(respawnsText(), button -> {
            respawns = !respawns;
            button.setMessage(respawnsText());
        }).tooltip(Tooltip.of(ui("respawns_tip"))).dimensions(right - 2 * OPTION_WIDTH - GAP, optionTop, OPTION_WIDTH, BUTTON_HEIGHT).build());

        int buttonTop = windowY + windowHeight - EditorPanel.FOOTER_HEIGHT + (EditorPanel.FOOTER_HEIGHT - BUTTON_HEIGHT) / 2 + 1;
        addDrawableChild(ButtonWidget.builder(ui("save"), button -> save()).dimensions(right - BUTTON_WIDTH, buttonTop, BUTTON_WIDTH, BUTTON_HEIGHT).build());
        addDrawableChild(ButtonWidget.builder(ScreenTexts.CANCEL, button -> close())
                .dimensions(right - 2 * BUTTON_WIDTH - GAP, buttonTop, BUTTON_WIDTH, BUTTON_HEIGHT).build());
        if(!data.isNew())
            addDrawableChild(ButtonWidget.builder(ui("remove"), button -> {
                handler.remove();
                close();
            }).dimensions(left, buttonTop, BUTTON_WIDTH, BUTTON_HEIGHT).build());

        shownKind = null;
        refresh(data.value());
    }

    private void save() {
        MarkerKind kind = selectedKind();
        boolean spawnsNpc = kind == MarkerKind.SPAWN_NPC;
        boolean spawnsAnimal = kind == MarkerKind.SPAWN_ANIMAL;
        handler.save(kind, spawnsNpc || spawnsAnimal ? valueList.selectedValue() : "", spawnsNpc || spawnsAnimal ? count : 1, spawnsAnimal && respawns, facing);
        close();
    }

    private static MutableText ui(String key, Object... arguments) {
        return Text.translatable("ui.%s.structure_marker.%s".formatted(MiddleEarth.MOD_ID, key), arguments);
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

    // The second list's contents and which options are shown depend on the kind
    private void refresh(String current) {
        MarkerKind kind = selectedKind();
        if(kind != shownKind) {
            shownKind = kind;
            List<Choice> choices = new ArrayList<>();
            if(kind == MarkerKind.SPAWN_NPC) {
                for(String nest : handler.getData().nests())
                    choices.add(new Choice(nest, nestName(nest)));
                // A marker placed before nests had full names stores only a kind, and its place comes from its manager
                if(!current.isEmpty() && !handler.getData().nests().contains(current))
                    choices.add(new Choice(current, ChoiceListWidget.readable(current)));
            } else if(kind == MarkerKind.SPAWN_ANIMAL) {
                for(String animal : handler.getData().animals())
                    choices.add(new Choice(animal, animalName(animal)));
            }
            valueList.setChoices(choices, current);
        }
        boolean spawns = kind == MarkerKind.SPAWN_NPC || kind == MarkerKind.SPAWN_ANIMAL;
        valueList.active = spawns;
        valueList.visible = spawns;
        valueSearch.visible = spawns;
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

    // Brigand dungeon: bandit
    private static Text nestName(String id) {
        Identifier nest = Identifier.tryParse(id);
        Identifier place = nest == null ? null : BedTag.peopleNests().get(nest);
        if(place == null)
            return Text.literal(id);
        return Text.translatable(place.toTranslationKey("structure_manager_data")).append(": ").append(ChoiceListWidget.readable(BedTag.nestKindOf(nest)));
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

    // A container screen closes on the inventory key, which is a letter the player may want to type in a search
    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if(keyCode == GLFW.GLFW_KEY_ENTER || keyCode == GLFW.GLFW_KEY_KP_ENTER) {
            save();
            return true;
        }
        if(keyCode != GLFW.GLFW_KEY_ESCAPE && getFocused() instanceof TextFieldWidget field && field.isFocused()) {
            field.keyPressed(keyCode, scanCode, modifiers);
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    // A container screen swallows the mouse wheel, so the lists never scrolled. Pass it to the list under the mouse
    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
        for(ChoiceListWidget list : new ChoiceListWidget[]{kindList, valueList}) {
            if(list != null && list.isMouseOver(mouseX, mouseY))
                return list.mouseScrolled(mouseX, mouseY, horizontalAmount, verticalAmount);
        }
        return super.mouseScrolled(mouseX, mouseY, horizontalAmount, verticalAmount);
    }

    // Don't dim the world behind the window. The player needs to see the build being marked
    @Override
    public void renderBackground(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
        refresh("");
        StructureMarkerScreenData data = handler.getData();
        MarkerKind kind = selectedKind();
        boolean spawns = valueList.visible;
        EditorPanel.window(context, textRenderer, windowX, windowY, windowWidth, windowHeight, ui(data.isNew() ? "title_new" : "title"),
                ui("about", data.pos().getX(), data.pos().getY(), data.pos().getZ()));
        EditorPanel.footer(context, windowX, windowY, windowWidth, windowHeight);
        // A kind with nothing to pick shows its description and a "nothing more to set" note where the second list would be
        if(!spawns) {
            EditorPanel.well(context, valueList.getX(), kindList.getY(), VALUE_WIDTH, kindList.getHeight());
            int line = kindList.getY() + GAP;
            for(OrderedText wrapped : textRenderer.wrapLines(ui("about_" + kind.asString()), VALUE_WIDTH - 2 * GAP)) {
                context.drawTextWithShadow(textRenderer, wrapped, valueList.getX() + GAP, line, EditorPanel.TEXT);
                line += textRenderer.fontHeight + 2;
            }
            line += GAP;
            for(OrderedText wrapped : textRenderer.wrapLines(ui("nothing_more"), VALUE_WIDTH - 2 * GAP)) {
                context.drawTextWithShadow(textRenderer, wrapped, valueList.getX() + GAP, line, EditorPanel.MUTED);
                line += textRenderer.fontHeight + 2;
            }
        }
        if(spawns)
            EditorPanel.well(context, countX, minusButton.getY() + 1, COUNT_WIDTH, BUTTON_HEIGHT - 2);
        super.render(context, mouseX, mouseY, deltaTicks);
        int labelY = valueList.labelY(textRenderer);
        EditorPanel.label(context, textRenderer, ui("kind_label"), kindList.getX(), labelY);
        if(spawns)
            EditorPanel.label(context, textRenderer, ui(kind == MarkerKind.SPAWN_NPC ? "value_people" : "value_animal"), valueList.getX(), labelY);
        if(spawns)
            context.drawTextWithShadow(textRenderer, ui("about_" + kind.asString()), kindList.getX(), kindList.getBottom() + GAP, EditorPanel.TEXT);
        if(spawns)
            context.drawCenteredTextWithShadow(textRenderer, Text.literal(Integer.toString(count)), countX + COUNT_WIDTH / 2, countY, EditorPanel.TEXT);
    }
}
