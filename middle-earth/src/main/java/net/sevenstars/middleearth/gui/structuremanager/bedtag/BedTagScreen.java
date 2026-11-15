package net.sevenstars.middleearth.gui.structuremanager.bedtag;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.registry.Registry;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.block.special.beds.BedOccupant;
import net.sevenstars.middleearth.gui.structuremanager.ChoiceListWidget;
import net.sevenstars.middleearth.gui.structuremanager.ChoiceListWidget.Choice;
import net.sevenstars.middleearth.registries.DynamicRegistriesME;
import net.sevenstars.middleearth.registries.content.npcjobs.NpcJobs;
import net.sevenstars.middleearth.resources.datas.npc_classes.NpcClass;
import net.sevenstars.middleearth.resources.datas.npc_classes.data.StationKind;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

// One question, who lives here, answered from a list of those the place can house. The nest and the job behind each answer
// are the server's to know. Only a cell asks a second one: who is held in it
@Environment(value = EnvType.CLIENT)
public class BedTagScreen extends HandledScreen<BedTagScreenHandler> {
    private static final int LIST_WIDTH = 200;
    private static final int CLASS_WIDTH = 150;
    private static final int LIST_HEIGHT = 140;
    private static final int ROW_HEIGHT = 16;
    private static final int GAP = 10;
    private static final int LINE_GAP = 2;
    private static final int BUTTON_WIDTH = 90;
    private static final int BUTTON_HEIGHT = 20;
    private static final int LABEL_COLOR = 0xFFA0A0A0;
    private static final int TEXT_COLOR = 0xFFFFFFFF;
    // How many of the classes that may be born are named before the rest are left out
    private static final int NAMED_CLASSES = 3;
    private static final List<String> VILLAGER_NESTS = List.of("civilian", "peasant");
    // The value of the bed's own tag where the place has no such occupant to offer
    private static final String KEPT = "kept";

    private final List<BedOccupant> occupants = new ArrayList<>();
    private ChoiceListWidget occupantList;
    private ChoiceListWidget classList;
    private TextFieldWidget classSearch;

    public BedTagScreen(BedTagScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Override
    protected void init() {
        super.init();
        BedTagScreenData data = handler.getData();
        int left = width / 2 - (LIST_WIDTH + GAP + CLASS_WIDTH) / 2;
        int top = height / 2 - LIST_HEIGHT / 2 - BUTTON_HEIGHT;

        occupants.clear();
        occupants.addAll(data.occupants());
        occupants.sort(Comparator.comparing((BedOccupant occupant) -> occupant.group().ordinal())
                .thenComparing(occupant -> !isVillager(occupant)).thenComparing(occupant -> label(occupant).getString()));
        List<Choice> choices = new ArrayList<>();
        choices.add(new Choice("", ui("nobody")));
        BedOccupant.Group group = null;
        for(int i = 0; i < occupants.size(); i++) {
            BedOccupant occupant = occupants.get(i);
            if(occupant.group() != group) {
                group = occupant.group();
                choices.add(Choice.header(ui(switch (group) {
                    case COMMON -> "common";
                    case FIGHTERS -> "fighters";
                    case OTHER -> "other";
                })));
            }
            choices.add(new Choice(Integer.toString(i), label(occupant)));
        }
        String current = currentChoice(data);
        // A bed tagged with what this place does not offer keeps its tag unless something else is picked
        if(current.equals(KEPT))
            choices.add(new Choice(KEPT, ui("kept", data.nestKind() + (data.job().isEmpty() ? "" : " / " + data.job()))));
        occupantList = new ChoiceListWidget(LIST_WIDTH, LIST_HEIGHT, left, top, ROW_HEIGHT);
        occupantList.setChoices(choices, current);
        addDrawableChild(occupantList.newSearchField(textRenderer));
        addDrawableChild(occupantList);

        List<Choice> classes = new ArrayList<>();
        classes.add(new Choice("", ui("anyone")));
        Registry<NpcClass> registry = MinecraftClient.getInstance().getNetworkHandler().getRegistryManager().getOptional(DynamicRegistriesME.NPC_CLASS).orElse(null);
        if(registry != null) {
            List<Identifier> ids = new ArrayList<>(registry.getIds());
            ids.sort(Comparator.naturalOrder());
            for(Identifier id : ids)
                classes.add(new Choice(id.toString(), className(id)));
        }
        classList = new ChoiceListWidget(CLASS_WIDTH, LIST_HEIGHT, left + LIST_WIDTH + GAP, top, ROW_HEIGHT);
        classList.setChoices(classes, data.npcClass());
        classSearch = addDrawableChild(classList.newSearchField(textRenderer));
        addDrawableChild(classList);

        int buttonTop = top + LIST_HEIGHT + GAP + 2 * (textRenderer.fontHeight + LINE_GAP);
        addDrawableChild(ButtonWidget.builder(ui("save"), button -> save(false))
                .dimensions(width / 2 - BUTTON_WIDTH / 2 - GAP - BUTTON_WIDTH, buttonTop, BUTTON_WIDTH, BUTTON_HEIGHT).build());
        addDrawableChild(ButtonWidget.builder(ui("reset_links"), button -> save(true))
                .dimensions(width / 2 - BUTTON_WIDTH / 2, buttonTop, BUTTON_WIDTH, BUTTON_HEIGHT).build());
        addDrawableChild(ButtonWidget.builder(ScreenTexts.CANCEL, button -> close())
                .dimensions(width / 2 + BUTTON_WIDTH / 2 + GAP, buttonTop, BUTTON_WIDTH, BUTTON_HEIGHT).build());
    }

    // The occupant the bed's tag stands for. A tag with no job is the one its nest gives by itself
    private String currentChoice(BedTagScreenData data) {
        if(data.nestKind().isEmpty())
            return "";
        int ofNest = -1;
        for(int i = 0; i < occupants.size(); i++) {
            BedOccupant occupant = occupants.get(i);
            if(!occupant.nestKind().equals(data.nestKind()))
                continue;
            if(occupant.job().equals(data.job()))
                return Integer.toString(i);
            if(ofNest < 0 && NpcJobs.forNestKind(occupant.nestKind()).map(Identifier::toString).orElse("").equals(occupant.job()))
                ofNest = i;
        }
        return data.job().isEmpty() && ofNest >= 0 ? Integer.toString(ofNest) : KEPT;
    }

    private @Nullable BedOccupant picked() {
        try {
            int index = Integer.parseInt(occupantList.selectedValue());
            return index >= 0 && index < occupants.size() ? occupants.get(index) : null;
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private void save(boolean resetLinks) {
        BedTagScreenData data = handler.getData();
        BedOccupant occupant = picked();
        if(occupant != null)
            handler.save(occupant.nestKind(), occupant.job(), occupant.group() == BedOccupant.Group.OTHER ? classList.selectedValue() : "", resetLinks);
        else if(occupantList.selectedValue().equals(KEPT))
            handler.save(data.nestKind(), data.job(), data.npcClass(), resetLinks);
        else
            handler.save("", "", "", resetLinks);
        close();
    }

    private static boolean isVillager(BedOccupant occupant) {
        return occupant.job().isEmpty() && VILLAGER_NESTS.contains(occupant.nestKind());
    }

    // By the trade or the duty where there is one, and otherwise by the kind of nest
    private static Text label(BedOccupant occupant) {
        if(isVillager(occupant))
            return ui("villager");
        Identifier job = occupant.job().isEmpty() ? null : Identifier.tryParse(occupant.job());
        return ChoiceListWidget.readable(job != null ? job.getPath() : occupant.nestKind());
    }

    private static Text className(Identifier id) {
        return Text.translatable(id.toTranslationKey("npc_type"));
    }

    private static MutableText ui(String key, Object... arguments) {
        return Text.translatable("ui.%s.bed_tag.%s".formatted(MiddleEarth.MOD_ID, key), arguments);
    }

    // Who the pick spawns, in words: the classes of the nest, or the one named for a cell
    private Text born(@Nullable BedOccupant occupant) {
        if(occupant == null)
            return occupantList.selectedValue().equals(KEPT) ? Text.empty() : ui("nobody_born");
        MutableText who = Text.empty();
        String named = occupant.group() == BedOccupant.Group.OTHER ? classList.selectedValue() : "";
        Identifier namedId = named.isEmpty() ? null : Identifier.tryParse(named);
        if(namedId != null) {
            who.append(className(namedId));
        } else {
            for(int i = 0; i < Math.min(NAMED_CLASSES, occupant.classes().size()); i++)
                who.append(i == 0 ? "" : ", ").append(className(occupant.classes().get(i)));
            if(occupant.classes().size() > NAMED_CLASSES)
                who.append(", ...");
        }
        Identifier job = occupant.job().isEmpty() ? null : Identifier.tryParse(occupant.job());
        return job == null ? ui("born", who) : ui("born_working", who, ChoiceListWidget.readable(job.getPath()));
    }

    private Text worksAt(@Nullable BedOccupant occupant) {
        Identifier job = occupant == null || occupant.job().isEmpty() ? null : Identifier.tryParse(occupant.job());
        if(job == null)
            return Text.empty();
        List<StationKind> kinds = NpcJobs.stationKinds(job);
        if(kinds.isEmpty())
            return Text.empty();
        MutableText stations = Text.empty();
        for(int i = 0; i < kinds.size(); i++)
            stations.append(i == 0 ? "" : ", ").append(ChoiceListWidget.readable(kinds.get(i).asString()));
        return ui("works_at", stations);
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
        for(ChoiceListWidget list : new ChoiceListWidget[]{occupantList, classList}) {
            if(list != null && list.visible && list.isMouseOver(mouseX, mouseY))
                return list.mouseScrolled(mouseX, mouseY, horizontalAmount, verticalAmount);
        }
        return super.mouseScrolled(mouseX, mouseY, horizontalAmount, verticalAmount);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
        BedOccupant occupant = picked();
        // Only a cell is asked who is held in it
        boolean held = occupant != null && occupant.group() == BedOccupant.Group.OTHER;
        classList.visible = held;
        classSearch.visible = held;
        renderBackground(context, mouseX, mouseY, deltaTicks);
        super.render(context, mouseX, mouseY, deltaTicks);
        context.drawCenteredTextWithShadow(textRenderer, ui("title"), occupantList.getX() + LIST_WIDTH / 2, occupantList.labelY(textRenderer), LABEL_COLOR);
        if(held)
            context.drawCenteredTextWithShadow(textRenderer, ui("held"), classList.getX() + CLASS_WIDTH / 2, classList.labelY(textRenderer), LABEL_COLOR);
        int lineY = occupantList.getBottom() + GAP / 2;
        context.drawTextWithShadow(textRenderer, born(occupant), occupantList.getX(), lineY, TEXT_COLOR);
        context.drawTextWithShadow(textRenderer, worksAt(occupant), occupantList.getX(), lineY + textRenderer.fontHeight + LINE_GAP, LABEL_COLOR);
    }
}
