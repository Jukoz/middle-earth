package net.sevenstars.middleearth.gui.structuremanager.bedtag;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.sevenstars.middleearth.gui.structuremanager.EditorScreen;
import net.sevenstars.middleearth.network.packets.client2server.PacketBedTagUpdateRequest;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.item.ItemStack;
import net.minecraft.text.OrderedText;
import net.minecraft.util.math.BlockPos;
import net.sevenstars.middleearth.gui.structuremanager.EditorPanel;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.registry.DynamicRegistryManager;
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
import net.sevenstars.middleearth.features.npcs.jobs.NpcJobs;
import net.sevenstars.middleearth.features.npcs.classes.NpcClass;
import net.sevenstars.middleearth.features.npcs.classes.data.StationKind;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

// Asks who lives here. The place defaults to what neighbouring beds got; a cell asks who's held
@Environment(value = EnvType.CLIENT)
public class BedTagScreen extends EditorScreen<BedTagScreenHandler> {
    private static final int PLACE_WIDTH = 108;
    private static final int LIST_WIDTH = 128;
    private static final int CARD_WIDTH = 118;
    private static final int LEAST_LIST_HEIGHT = 70;
    private static final int MOST_LIST_HEIGHT = 150;
    private static final int ROW_HEIGHT = 14;
    private static final int GAP = 6;
    private static final int LINE_GAP = 2;
    private static final int BUTTON_WIDTH = 64;
    private static final int WIDE_BUTTON_WIDTH = 84;
    private static final int BUTTON_HEIGHT = 20;
    // Top of a list to its label, covering the label, search box and gaps
    private static final int LIST_HEAD = 30;
    private static final int SEARCH_HEAD = 18;
    private static final int HELD_CARD_HEIGHT = 46;
    // Classes named before the rest are left out
    private static final int NAMED_CLASSES = 5;
    private static final List<String> VILLAGER_NESTS = List.of("civilian", "peasant");
    // Value for the bed's existing tag when the place has no matching occupant
    private static final String KEPT = "kept";
    private static final String POOL_SUFFIX = " Pool";

    private final List<BedOccupant> occupants = new ArrayList<>();
    private ChoiceListWidget placeList;
    private ChoiceListWidget occupantList;
    private @Nullable String shownPlace;
    private ChoiceListWidget classList;
    private TextFieldWidget classSearch;
    private int windowX;
    private int windowY;
    private int windowWidth;
    private int windowHeight;
    private int listHeight;

    public BedTagScreen(BedTagScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Override
    protected void init() {
        super.init();
        BedTagScreenData data = handler.getData();
        listHeight = Math.clamp(height - 110, LEAST_LIST_HEIGHT, MOST_LIST_HEIGHT);
        windowWidth = EditorPanel.PADDING * 2 + PLACE_WIDTH + GAP + LIST_WIDTH + GAP + CARD_WIDTH;
        windowHeight = EditorPanel.TITLE_HEIGHT + 1 + EditorPanel.PADDING + LIST_HEAD + listHeight + EditorPanel.PADDING + EditorPanel.FOOTER_HEIGHT;
        windowX = (width - windowWidth) / 2;
        windowY = (height - windowHeight) / 2;
        int left = windowX + EditorPanel.PADDING;
        int top = windowY + EditorPanel.TITLE_HEIGHT + 1 + EditorPanel.PADDING + LIST_HEAD;

        occupants.clear();
        occupants.addAll(data.occupants());
        occupants.sort(Comparator.comparing((BedOccupant occupant) -> occupant.group().ordinal())
                .thenComparing(occupant -> !isVillager(occupant)).thenComparing(occupant -> label(occupant).getString()));
        List<Choice> places = new ArrayList<>();
        Set<Identifier> listed = new LinkedHashSet<>();
        occupants.forEach(occupant -> listed.add(occupant.place()));
        List<Identifier> sorted = new ArrayList<>(listed);
        sorted.sort(Comparator.comparing(place -> placeName(place).getString()));
        for(Identifier place : sorted)
            places.add(new Choice(place.toString(), placeName(place)));
        placeList = new ChoiceListWidget(PLACE_WIDTH, listHeight, left, top, ROW_HEIGHT);
        placeList.setChoices(places, data.place());
        addDrawableChild(placeList.newSearchField(textRenderer));
        addDrawableChild(placeList);

        occupantList = new ChoiceListWidget(LIST_WIDTH, listHeight, left + PLACE_WIDTH + GAP, top, ROW_HEIGHT);
        addDrawableChild(occupantList.newSearchField(textRenderer));
        addDrawableChild(occupantList);
        shownPlace = null;
        showPlace();

        List<Choice> classes = new ArrayList<>();
        classes.add(new Choice("", ui("anyone")));
        Registry<NpcClass> registry = MinecraftClient.getInstance().getNetworkHandler().getRegistryManager().getOptional(DynamicRegistriesME.NPC_CLASS).orElse(null);
        if(registry != null) {
            List<Identifier> ids = new ArrayList<>(registry.getIds());
            ids.sort(Comparator.naturalOrder());
            for(Identifier id : ids)
                classes.add(new Choice(id.toString(), className(id)));
        }
        int classTop = cardTop() + HELD_CARD_HEIGHT + GAP + SEARCH_HEAD;
        classList = new ChoiceListWidget(CARD_WIDTH, top + listHeight - classTop, cardX(), classTop, ROW_HEIGHT);
        classList.setChoices(classes, data.npcClass());
        classSearch = addDrawableChild(classList.newSearchField(textRenderer));
        addDrawableChild(classList);

        int buttonTop = windowY + windowHeight - EditorPanel.FOOTER_HEIGHT + (EditorPanel.FOOTER_HEIGHT - BUTTON_HEIGHT) / 2 + 1;
        int right = windowX + windowWidth - EditorPanel.PADDING;
        addDrawableChild(ButtonWidget.builder(ui("save"), button -> save(false)).tooltip(Tooltip.of(ui("save_tip")))
                .dimensions(right - BUTTON_WIDTH, buttonTop, BUTTON_WIDTH, BUTTON_HEIGHT).build());
        addDrawableChild(ButtonWidget.builder(ScreenTexts.CANCEL, button -> close())
                .dimensions(right - 2 * BUTTON_WIDTH - GAP, buttonTop, BUTTON_WIDTH, BUTTON_HEIGHT).build());
        addDrawableChild(ButtonWidget.builder(ui("reset_links"), button -> save(true)).tooltip(Tooltip.of(ui("reset_links_tip")))
                .dimensions(left, buttonTop, WIDE_BUTTON_WIDTH, BUTTON_HEIGHT).build());
    }

    private int cardX() {
        return windowX + EditorPanel.PADDING + PLACE_WIDTH + GAP + LIST_WIDTH + GAP;
    }

    private int cardTop() {
        return placeList.getY() - SEARCH_HEAD;
    }

    private void showPlace() {
        String place = placeList.selectedValue();
        if(place.equals(shownPlace))
            return;
        shownPlace = place;
        BedTagScreenData data = handler.getData();
        List<Choice> choices = new ArrayList<>();
        choices.add(new Choice("", ui("nobody"), ui("nobody_detail"), ItemStack.EMPTY));
        BedOccupant.Group group = null;
        for(int i = 0; i < occupants.size(); i++) {
            BedOccupant occupant = occupants.get(i);
            if(!occupant.place().toString().equals(place))
                continue;
            if(occupant.group() != group) {
                group = occupant.group();
                choices.add(Choice.header(ui(switch (group) {
                    case COMMON -> "common";
                    case FIGHTERS -> "fighters";
                    case NOBLES -> "nobles";
                    case OTHER -> "other";
                })));
            }
            choices.add(new Choice(Integer.toString(i), label(occupant), station(occupant), ItemStack.EMPTY));
        }
        String current = place.equals(data.place()) ? currentChoice(data) : data.nestKind().isEmpty() ? "" : KEPT;
        if(current.equals(KEPT))
            choices.add(new Choice(KEPT, ui("kept", data.nestKind() + (data.job().isEmpty() ? "" : " / " + data.job()))));
        occupantList.setChoices(choices, current);
    }

    private String currentChoice(BedTagScreenData data) {
        if(data.nestKind().isEmpty())
            return "";
        int ofNest = -1;
        for(int i = 0; i < occupants.size(); i++) {
            BedOccupant occupant = occupants.get(i);
            if(!occupant.nestKind().equals(data.nestKind()) || !occupant.place().toString().equals(data.place()))
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
            send(occupant.place().toString(), occupant.nestKind(), occupant.job(), occupant.group() == BedOccupant.Group.OTHER ? classList.selectedValue() : "", resetLinks);
        else if(occupantList.selectedValue().equals(KEPT))
            send("", data.nestKind(), data.job(), data.npcClass(), resetLinks);
        else
            send("", "", "", "", resetLinks);
        close();
    }

    private void send(String place, String nestKind, String job, String npcClass, boolean resetLinks) {
        ClientPlayNetworking.send(new PacketBedTagUpdateRequest(handler.getData().pos(), place, nestKind, job, npcClass, resetLinks));
    }

    private static boolean isVillager(BedOccupant occupant) {
        return occupant.job().isEmpty() && VILLAGER_NESTS.contains(occupant.nestKind());
    }

    // Nobles go by nest kind, since a captain, chief and marshal all do a lord's job
    private static Text label(BedOccupant occupant) {
        if(isVillager(occupant))
            return ui("villager");
        if(occupant.group() == BedOccupant.Group.NOBLES)
            return ChoiceListWidget.readable(occupant.nestKind());
        Identifier job = occupant.job().isEmpty() ? null : Identifier.tryParse(occupant.job());
        return ChoiceListWidget.readable(job != null ? job.getPath() : occupant.nestKind());
    }

    private static Text placeName(Identifier id) {
        String name = Text.translatable(id.toTranslationKey("structure_manager_data")).getString();
        return Text.literal(name.endsWith(POOL_SUFFIX) ? name.substring(0, name.length() - POOL_SUFFIX.length()) : name);
    }

    private static Text className(Identifier id) {
        return Text.translatable(id.toTranslationKey("npc_type"));
    }

    private static DynamicRegistryManager registries() {
        return MinecraftClient.getInstance().getNetworkHandler().getRegistryManager();
    }

    private static MutableText ui(String key, Object... arguments) {
        return Text.translatable("ui.%s.bed_tag.%s".formatted(MiddleEarth.MOD_ID, key), arguments);
    }

    // Shown as a block name, like cook ... campfire, because builders place blocks, not station kinds
    private static @Nullable Text station(BedOccupant occupant) {
        Identifier job = occupant.job().isEmpty() ? null : Identifier.tryParse(occupant.job());
        List<StationKind> kinds = job == null ? List.of() : NpcJobs.stationKinds(registries(), job);
        return kinds.isEmpty() ? null : Text.literal(kinds.getFirst().blocks().getFirst().replace('_', ' '));
    }

    private static Text blocksOf(StationKind kind) {
        MutableText blocks = Text.empty();
        List<String> names = kind.blocks();
        for(int i = 0; i < names.size(); i++)
            blocks.append(i == 0 ? Text.empty() : i == names.size() - 1 ? ui("card_or") : Text.literal(", ")).append(ChoiceListWidget.readable(names.get(i)));
        return blocks;
    }

    private void drawCard(DrawContext context, @Nullable BedOccupant occupant, int height) {
        int x = cardX();
        int y = cardTop();
        EditorPanel.well(context, x, y, CARD_WIDTH, height);
        context.enableScissor(x, y, x + CARD_WIDTH, y + height);
        int line = y + GAP - 2;
        int inner = CARD_WIDTH - 2 * GAP;
        if(occupant == null) {
            Text nobody = occupantList.selectedValue().equals(KEPT) ? ui("kept_card") : ui("nobody_born");
            for(OrderedText wrapped : textRenderer.wrapLines(nobody, inner)) {
                context.drawTextWithShadow(textRenderer, wrapped, x + GAP, line, EditorPanel.MUTED);
                line += textRenderer.fontHeight + LINE_GAP;
            }
            context.disableScissor();
            return;
        }
        line = heading(context, ui("card_born"), x, line);
        String named = occupant.group() == BedOccupant.Group.OTHER ? classList.selectedValue() : "";
        Identifier namedId = named.isEmpty() ? null : Identifier.tryParse(named);
        List<Text> born = new ArrayList<>();
        if(namedId != null) {
            born.add(className(namedId));
        } else {
            for(int i = 0; i < Math.min(NAMED_CLASSES, occupant.classes().size()); i++)
                born.add(className(occupant.classes().get(i)));
            if(occupant.classes().size() > NAMED_CLASSES)
                born.add(ui("card_more", occupant.classes().size() - NAMED_CLASSES));
        }
        for(Text who : born) {
            context.drawTextWithShadow(textRenderer, who, x + GAP, line, EditorPanel.TEXT);
            line += textRenderer.fontHeight + LINE_GAP;
        }
        Identifier job = occupant.job().isEmpty() ? null : Identifier.tryParse(occupant.job());
        if(job == null) {
            context.disableScissor();
            return;
        }
        line = heading(context, ui("card_job"), x, line + GAP - 2);
        context.drawTextWithShadow(textRenderer, ChoiceListWidget.readable(job.getPath()), x + GAP, line, EditorPanel.TEXT);
        line += textRenderer.fontHeight + LINE_GAP;
        List<StationKind> kinds = NpcJobs.stationKinds(registries(), job);
        if(!kinds.isEmpty()) {
            line = heading(context, ui("card_works_at"), x, line + GAP - 2);
            for(StationKind kind : kinds) {
                for(OrderedText wrapped : textRenderer.wrapLines(blocksOf(kind), inner)) {
                    context.drawTextWithShadow(textRenderer, wrapped, x + GAP, line, EditorPanel.TEXT);
                    line += textRenderer.fontHeight + LINE_GAP;
                }
            }
        }
        context.disableScissor();
    }

    private int heading(DrawContext context, Text text, int x, int y) {
        EditorPanel.label(context, textRenderer, text, x + GAP, y);
        return y + textRenderer.fontHeight + LINE_GAP;
    }

    @Override
    protected void submit() {
        save(false);
    }

    @Override
    protected ChoiceListWidget[] lists() {
        return new ChoiceListWidget[]{placeList, occupantList, classList};
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
        showPlace();
        BedOccupant occupant = picked();
        boolean held = occupant != null && occupant.group() == BedOccupant.Group.OTHER;
        classList.visible = held;
        classSearch.visible = held;
        BlockPos bed = handler.getData().pos();
        EditorPanel.window(context, textRenderer, windowX, windowY, windowWidth, windowHeight, ui("title"), ui("about", bed.getX(), bed.getY(), bed.getZ()));
        EditorPanel.footer(context, windowX, windowY, windowWidth, windowHeight);
        drawCard(context, occupant, held ? HELD_CARD_HEIGHT : SEARCH_HEAD + listHeight);
        super.render(context, mouseX, mouseY, deltaTicks);
        EditorPanel.label(context, textRenderer, ui("place"), placeList.getX(), placeList.labelY(textRenderer));
        EditorPanel.label(context, textRenderer, ui("type"), occupantList.getX(), occupantList.labelY(textRenderer));
        EditorPanel.label(context, textRenderer, ui(held ? "held" : "card"), cardX(), placeList.labelY(textRenderer));
    }
}
