package net.sevenstars.middleearth.gui.lapidarytable;

import net.sevenstars.middleearth.item.ResourceItemsME;
import net.sevenstars.middleearth.recipe.RecipeSerializerRegistryME;

import net.sevenstars.middleearth.gui.ScreenHandlerRegistryME;
import net.sevenstars.middleearth.block.registration.DecorativeBlockRegistryME;

import org.jetbrains.annotations.Nullable;

import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.item.DataComponentTypesME;
import net.sevenstars.middleearth.resources.datas.gems.GemType;
import net.sevenstars.middleearth.registries.content.gems.GemTypeRegistry;
import net.sevenstars.middleearth.item.items.gems.CutGemItem;
import net.sevenstars.middleearth.item.items.gems.GemCut;
import net.sevenstars.middleearth.item.items.gems.LapidaryToolsItem;
import net.sevenstars.middleearth.recipe.lapidary.LapidaryTools;
import net.sevenstars.middleearth.item.items.gems.RawGemItem;
import net.sevenstars.middleearth.block.special.lapidarytable.LapidaryTableBlockEntity;
import net.sevenstars.middleearth.recipe.lapidary.RefractionReading;
import net.sevenstars.middleearth.recipe.lapidary.GemKnowledge;
import net.sevenstars.middleearth.recipe.lapidary.GemStudies;
import net.sevenstars.middleearth.recipe.lapidary.StudyBand;
import net.sevenstars.middleearth.recipe.lapidary.StudyGrade;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.resource.featuretoggle.FeatureFlags;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;

/**
 * The Lapidary Table's screen: slots for a raw gem, paper and the finished notes, and the study minigame,
 * Refraction Reading. A study costs the gem's experience levels and one paper, both taken at the start (creative
 * players pay no levels but still need the paper), and writes up notes for the grade earned. The paper slot
 * belongs to the table's block entity, so paper stays in the table when the screen closes. A new study cannot start until those
 * notes have been taken out.
 * <p>
 * The server owns the game. The client sends its moves as button clicks (vanilla's ClickButton packet) and
 * reads the state back from the synced properties; it traces the beam itself with the same
 * {@link RefractionReading} maths to draw it.
 * <ul>
 *   <li>{@link #START}: start (or restart) a study; pays the gem's experience levels</li>
 *   <li>{@link #GEM_LEFT} / {@link #GEM_RIGHT}: turn the gem one step</li>
 *   <li>{@link #LOCK_RUNE} + rune: lock a rune the light is on</li>
 *   <li>{@link #GRAB_LAMP}: the player picked up the lamp; the next lamp move costs one move, the rest of
 *       that drag is free</li>
 *   <li>{@link #SET_LAMP} + position: move the lamp</li>
 *   <li>{@link #MODE_READING} / {@link #MODE_FACETING}: switch screens (not during a study or a cut)</li>
 *   <li>{@link #START_CUT} + cut: start cutting the Faceting gem into that cut; uses the water bucket</li>
 *   <li>{@link #FACET_BEGIN}: start grinding the next facet; {@link #FACET_RESULT} + score: its score</li>
 * </ul>
 * The Faceting slots and the cut's progress belong to the table's block entity, so a cut stays in the table
 * until it is finished.
 */
public class LapidaryScreenHandler extends ScreenHandler {
    public static final int START = 0;
    public static final int GEM_LEFT = 1;
    public static final int GEM_RIGHT = 2;
    public static final int GRAB_LAMP = 3;
    public static final int LOCK_RUNE = 10;
    public static final int SET_LAMP = 100;
    public static final int MODE_READING = 4;
    public static final int MODE_FACETING = 5;
    public static final int FACET_BEGIN = 6;
    public static final int START_CUT = 200;
    public static final int FACET_RESULT = 1000;
    public static final int MAX_RUNES = 5;

    public static final int READING = 0;
    public static final int FACETING = 1;

    public static final int STATE_IDLE = 0;
    public static final int STATE_STUDYING = 1;
    public static final int STATE_DONE = 2;

    // Synced property slots.
    private static final int P_STATE = 0;
    private static final int P_LAMP = 1;
    private static final int P_GEM = 2;
    private static final int P_MOVES = 3;
    private static final int P_PAR = 4;
    private static final int P_RUNE_COUNT = 5;
    private static final int P_RUNES = 6;
    private static final int P_LOCKED = P_RUNES + MAX_RUNES;
    private static final int P_GRADE = P_LOCKED + 1;
    private static final int P_MODE = P_GRADE + 1;
    private static final int PROPERTY_COUNT = P_MODE + 1;

    public static final int PAPER_SLOT_X = 170;
    public static final int GEM_SLOT_X = 196;
    public static final int NOTES_SLOT_X = 222;
    public static final int GEM_SLOT_Y = 22;
    /** The Faceting screen's three slots, water, gem and tools, centred on the parchment. */
    public static final int FACET_WATER_X = 170;
    public static final int FACET_GEM_X = 196;
    public static final int FACET_TOOLS_X = 222;
    private static final int GEM = 0;
    private static final int PAPER = 1;
    private static final int NOTES = 2;
    private static final int WATER_SLOT = 3;
    private static final int CUT_GEM_SLOT = 4;
    private static final int TOOLS_SLOT = 5;
    private static final int PLAYER_SLOTS = 6;
    public static final int INVENTORY_X = 44;
    public static final int INVENTORY_Y = 177;

    private final ScreenHandlerContext context;
    private final PlayerEntity player;
    private final Inventory gemInventory = new SimpleInventory(3) {
        @Override
        public void markDirty() {
            super.markDirty();
            onContentChanged(this);
        }
    };
    /** The table's paper: the block entity's inventory on the server, a stand-in on the client. */
    private final Inventory paperInventory;
    private final PropertyDelegate properties;
    /** The table's Faceting slots and cut progress: the block entity's on the server, stand-ins on the client. */
    private final Inventory facetingInventory;
    private final PropertyDelegate facetState;
    /** Server only: the table, for the cut's moves. */
    private final @Nullable LapidaryTableBlockEntity table;
    /** Server only: what the study is being played on, so changes to the slot can be noticed. */
    private double[][] outline;
    private double index;
    /** Server only: whether the current lamp drag has been paid for. */
    private boolean dragPaid;

    public LapidaryScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, ScreenHandlerContext.EMPTY, new SimpleInventory(1), new SimpleInventory(3),
            new ArrayPropertyDelegate(LapidaryTableBlockEntity.F_COUNT), null);
    }

    public LapidaryScreenHandler(int syncId, PlayerInventory playerInventory, ScreenHandlerContext context,
                                 LapidaryTableBlockEntity table) {
        this(syncId, playerInventory, context, table.getPaper(), table.getFaceting(), table.getFacetState(), table);
    }

    private LapidaryScreenHandler(int syncId, PlayerInventory playerInventory, ScreenHandlerContext context,
                                  Inventory paperInventory, Inventory facetingInventory, PropertyDelegate facetState,
                                  @Nullable LapidaryTableBlockEntity table) {
        super(ScreenHandlerRegistryME.LAPIDARY_SCREEN_HANDLER, syncId);
        this.context = context;
        this.paperInventory = paperInventory;
        this.facetingInventory = facetingInventory;
        this.facetState = facetState;
        this.table = table;
        this.player = playerInventory.player;
        this.properties = new ArrayPropertyDelegate(PROPERTY_COUNT);
        // A table with a cut in progress opens on it.
        properties.set(P_MODE, table != null && table.isCutActive() ? FACETING : READING);

        addSlot(new Slot(gemInventory, 0, GEM_SLOT_X, GEM_SLOT_Y) {
            @Override
            public boolean canInsert(ItemStack stack) {
                return stack.getItem() instanceof RawGemItem && stack.contains(DataComponentTypesME.GEM_TYPE);
            }

            @Override
            public int getMaxItemCount() {
                return 1;
            }

            @Override
            public boolean canTakeItems(PlayerEntity playerEntity) {
                // The gem stays put while it is being studied.
                return getState() != STATE_STUDYING;
            }

            @Override
            public boolean isEnabled() {
                return !isFaceting();
            }
        });
        addSlot(new Slot(paperInventory, 0, PAPER_SLOT_X, GEM_SLOT_Y) {
            @Override
            public boolean canInsert(ItemStack stack) {
                return stack.isOf(Items.PAPER);
            }

            @Override
            public boolean isEnabled() {
                return !isFaceting();
            }
        });
        addSlot(new Slot(gemInventory, NOTES, NOTES_SLOT_X, GEM_SLOT_Y) {
            @Override
            public boolean canInsert(ItemStack stack) {
                return false;
            }

            @Override
            public boolean isEnabled() {
                return !isFaceting();
            }
        });
        addSlot(new Slot(facetingInventory, LapidaryTableBlockEntity.WATER, FACET_WATER_X, GEM_SLOT_Y) {
            @Override
            public boolean canInsert(ItemStack stack) {
                return stack.isOf(Items.WATER_BUCKET);
            }

            @Override
            public int getMaxItemCount() {
                return 1;
            }

            @Override
            public boolean isEnabled() {
                return isFaceting();
            }
        });
        addSlot(new Slot(facetingInventory, LapidaryTableBlockEntity.CUT_GEM, FACET_GEM_X, GEM_SLOT_Y) {
            @Override
            public boolean canInsert(ItemStack stack) {
                return isFacetable(stack);
            }

            @Override
            public int getMaxItemCount() {
                return 1;
            }

            @Override
            public boolean canTakeItems(PlayerEntity playerEntity) {
                // The gem stays in the table until its cut is finished.
                return !isCutActive();
            }

            @Override
            public boolean isEnabled() {
                return isFaceting();
            }
        });
        addSlot(new Slot(facetingInventory, LapidaryTableBlockEntity.TOOLS, FACET_TOOLS_X, GEM_SLOT_Y) {
            @Override
            public boolean canInsert(ItemStack stack) {
                return stack.getItem() instanceof LapidaryToolsItem && !isCutActive();
            }

            @Override
            public int getMaxItemCount() {
                return 1;
            }

            @Override
            public boolean canTakeItems(PlayerEntity playerEntity) {
                // The tools stay installed until the cut is finished.
                return !isCutActive();
            }

            @Override
            public boolean isEnabled() {
                return isFaceting();
            }
        });
        addPlayerSlots(playerInventory, INVENTORY_X, INVENTORY_Y);
        addProperties(properties);
        addProperties(facetState);
    }

    // ---- State, readable on both sides ----

    public int getState() {
        return properties.get(P_STATE);
    }

    public int getLamp() {
        return properties.get(P_LAMP);
    }

    public int getGemStep() {
        return properties.get(P_GEM);
    }

    public int getMoves() {
        return properties.get(P_MOVES);
    }

    public int getPar() {
        return properties.get(P_PAR);
    }

    public int getRuneCount() {
        return properties.get(P_RUNE_COUNT);
    }

    public int getRune(int rune) {
        return properties.get(P_RUNES + rune);
    }

    public boolean isLocked(int rune) {
        return (properties.get(P_LOCKED) & (1 << rune)) != 0;
    }

    /** The grade of the last finished study in this screen, or null. */
    public @Nullable StudyGrade getLastGrade() {
        int grade = properties.get(P_GRADE);
        return grade > 0 ? StudyGrade.values()[grade - 1] : null;
    }

    public ItemStack getGem() {
        return gemInventory.getStack(GEM);
    }

    public boolean isFaceting() {
        return properties.get(P_MODE) == FACETING;
    }

    /** What may go in the Faceting gem slot: raw and cut gems (whether they can be cut yet is shown on screen). */
    public static boolean isFacetable(ItemStack stack) {
        return stack.contains(DataComponentTypesME.GEM_TYPE)
            && (stack.getItem() instanceof RawGemItem || stack.getItem() instanceof CutGemItem);
    }

    public ItemStack getCutGem() {
        return facetingInventory.getStack(LapidaryTableBlockEntity.CUT_GEM);
    }

    /** The installed tool set, or empty. */
    public ItemStack getTools() {
        return facetingInventory.getStack(LapidaryTableBlockEntity.TOOLS);
    }

    /** The highest quality value a cut on this table can reach with the installed tools. */
    public int getBenchCap() {
        return LapidaryTools.cap(getTools());
    }

    public boolean hasWater() {
        return facetingInventory.getStack(LapidaryTableBlockEntity.WATER).isOf(Items.WATER_BUCKET);
    }

    public boolean isCutActive() {
        return facetState.get(LapidaryTableBlockEntity.F_ACTIVE) != 0;
    }

    /** The cut in progress (only meaningful while {@link #isCutActive()}). */
    public GemCut getCut() {
        return GemCut.values()[Math.floorMod(facetState.get(LapidaryTableBlockEntity.F_CUT), GemCut.values().length)];
    }

    /** How many facets of the cut are done. */
    public int getFacet() {
        return facetState.get(LapidaryTableBlockEntity.F_FACET);
    }

    /** The scores of the finished facets, added up. */
    public int getScoreSum() {
        return facetState.get(LapidaryTableBlockEntity.F_SUM);
    }

    /** True while someone grinds a facet. */
    public boolean isFacetRunning() {
        return facetState.get(LapidaryTableBlockEntity.F_RUNNING) != 0;
    }

    public boolean hasPaper() {
        return paperInventory.getStack(0).isOf(Items.PAPER);
    }

    /** True while finished notes wait in the output slot; no new study can start until they are taken. */
    public boolean hasNotesWaiting() {
        return !gemInventory.getStack(NOTES).isEmpty();
    }

    /** What the gem in the slot plays as: its band and outline. Empty for anything that cannot be studied. */
    public record Setup(RegistryKey<GemType> gem, StudyBand band, double[][] outline) {
    }

    public static @Nullable Setup setup(ItemStack stack, @Nullable RegistryWrapper.WrapperLookup lookup) {
        RegistryKey<GemType> key = stack.get(DataComponentTypesME.GEM_TYPE);
        if (!(stack.getItem() instanceof RawGemItem) || key == null) {
            return null;
        }
        StudyBand band = StudyBand.of(GemTypeRegistry.get(lookup, key).map(GemType::studyBand).orElse(1));
        return new Setup(key, band, RefractionReading.outline(key.getValue(), band.facets()));
    }

    // ---- Moves from the client ----

    @Override
    public boolean onButtonClick(PlayerEntity player, int id) {
        if (id == MODE_READING || id == MODE_FACETING) {
            // No switching screens in the middle of a study or a cut.
            if (getState() == STATE_STUDYING || isCutActive()) {
                return false;
            }
            properties.set(P_MODE, id == MODE_FACETING ? FACETING : READING);
            return true;
        }
        if (isFaceting()) {
            return facetingClick(id);
        }
        if (id == START) {
            return start();
        }
        if (getState() != STATE_STUDYING) {
            return false;
        }
        if (id == GEM_LEFT || id == GEM_RIGHT) {
            properties.set(P_GEM, Math.floorMod(getGemStep() + (id == GEM_RIGHT ? -1 : 1), RefractionReading.STEPS));
            properties.set(P_MOVES, getMoves() + 1);
            dragPaid = false;
            return true;
        }
        if (id == GRAB_LAMP) {
            dragPaid = false;
            return true;
        }
        if (id >= SET_LAMP && id < SET_LAMP + RefractionReading.STEPS) {
            int lamp = id - SET_LAMP;
            if (lamp != getLamp() && !dragPaid) {
                properties.set(P_MOVES, getMoves() + 1);
                dragPaid = true;
            }
            properties.set(P_LAMP, lamp);
            return true;
        }
        if (id >= LOCK_RUNE && id < LOCK_RUNE + getRuneCount()) {
            dragPaid = false;
            return lock(id - LOCK_RUNE);
        }
        return false;
    }

    private boolean facetingClick(int id) {
        if (table == null) {
            return false;
        }
        if (id >= START_CUT && id < START_CUT + GemCut.values().length) {
            return table.startCut(GemCut.values()[id - START_CUT]);
        }
        if (id == FACET_BEGIN) {
            return table.beginFacet(player.getUuid());
        }
        if (id >= FACET_RESULT && id <= FACET_RESULT + 100) {
            return table.facetResult(player.getUuid(), id - FACET_RESULT);
        }
        return false;
    }

    private boolean start() {
        if (getState() == STATE_STUDYING || player.getWorld().isClient()) {
            return false;
        }
        Setup setup = setup(getGem(), player.getWorld().getRegistryManager());
        if (setup == null || hasNotesWaiting()) {
            return false;
        }
        int cost = setup.band().xpLevels();
        // Every study needs a sheet of paper, creative too; creative players pay no levels.
        if (!hasPaper() || (!player.isCreative() && player.experienceLevel < cost)) {
            return false;
        }
        if (!player.isCreative()) {
            player.addExperienceLevels(-cost);
        }
        paperInventory.removeStack(0, 1);
        paperInventory.markDirty();
        RefractionReading.Puzzle puzzle = RefractionReading.generate(player.getRandom().nextLong(), setup.outline(),
            setup.band().index(), setup.band().runes());
        outline = setup.outline();
        index = setup.band().index();
        properties.set(P_LAMP, puzzle.startLamp());
        properties.set(P_GEM, puzzle.startGem());
        properties.set(P_MOVES, 0);
        properties.set(P_PAR, puzzle.par());
        properties.set(P_RUNE_COUNT, puzzle.runes().length);
        for (int i = 0; i < MAX_RUNES; i++) {
            properties.set(P_RUNES + i, i < puzzle.runes().length ? puzzle.runes()[i] : 0);
        }
        properties.set(P_LOCKED, 0);
        dragPaid = false;
        properties.set(P_STATE, STATE_STUDYING);
        context.run((world, pos) -> world.playSound(null, pos, SoundEvents.ITEM_BOOK_PAGE_TURN, SoundCategory.BLOCKS,
            1.0F, 1.0F));
        return true;
    }

    private boolean lock(int rune) {
        if (isLocked(rune) || outline == null) {
            return false;
        }
        double exit = RefractionReading.trace(outline, index, getLamp(), getGemStep()).exitDegrees();
        if (!RefractionReading.lights(exit, getRune(rune))) {
            return false;
        }
        properties.set(P_LOCKED, properties.get(P_LOCKED) | (1 << rune));
        int locked = Integer.bitCount(properties.get(P_LOCKED));
        context.run((world, pos) -> world.playSound(null, pos, SoundEvents.BLOCK_AMETHYST_BLOCK_CHIME,
            SoundCategory.BLOCKS, 1.0F, 0.8F + 0.15F * locked));
        if (locked == getRuneCount()) {
            finish();
        }
        return true;
    }

    private void finish() {
        StudyGrade grade = StudyGrade.fromMovesOverPar(getMoves() - getPar(), getRuneCount());
        ItemStack gem = getGem();
        RegistryKey<GemType> key = gem.get(DataComponentTypesME.GEM_TYPE);
        gem.set(DataComponentTypesME.STUDY_GRADE, StudyGrade.best(gem.get(DataComponentTypesME.STUDY_GRADE), grade));
        gemInventory.markDirty();
        if (key != null) {
            gemInventory.setStack(NOTES, GemStudies.notes(key, grade));
            if (player instanceof ServerPlayerEntity serverPlayer) {
                GemKnowledge.record(serverPlayer, key, grade);
            }
        }
        properties.set(P_GRADE, grade.ordinal() + 1);
        properties.set(P_STATE, STATE_DONE);
        outline = null;
        context.run((world, pos) -> world.playSound(null, pos, SoundEvents.ENTITY_PLAYER_LEVELUP,
            SoundCategory.BLOCKS, 0.6F, 1.2F));
    }

    @Override
    public void onContentChanged(Inventory inventory) {
        super.onContentChanged(inventory);
        // A new gem in the slot clears the last result.
        if (inventory == gemInventory && getState() == STATE_DONE && getGem().isEmpty()) {
            properties.set(P_STATE, STATE_IDLE);
            properties.set(P_GRADE, 0);
        }
    }

    // ---- Usual screen handler duties ----

    @Override
    public ItemStack quickMove(PlayerEntity player, int slotIndex) {
        Slot slot = slots.get(slotIndex);
        if (!slot.hasStack() || !slot.canTakeItems(player)) {
            return ItemStack.EMPTY;
        }
        ItemStack stack = slot.getStack();
        ItemStack original = stack.copy();
        if (slotIndex < PLAYER_SLOTS) {
            if (!insertItem(stack, PLAYER_SLOTS, slots.size(), true)) {
                return ItemStack.EMPTY;
            }
            slot.onQuickTransfer(stack, original);
        } else if (isFaceting()) {
            Slot into = slots.get(stack.isOf(Items.WATER_BUCKET) ? WATER_SLOT
                : stack.getItem() instanceof LapidaryToolsItem ? TOOLS_SLOT : CUT_GEM_SLOT);
            if (into.hasStack() || !into.canInsert(stack)) {
                return ItemStack.EMPTY;
            }
            into.setStack(stack.split(1));
        } else if (stack.isOf(Items.PAPER)) {
            if (!insertItem(stack, PAPER, PAPER + 1, false)) {
                return ItemStack.EMPTY;
            }
        } else {
            Slot gemSlot = slots.get(GEM);
            if (gemSlot.hasStack() || !gemSlot.canInsert(stack)) {
                return ItemStack.EMPTY;
            }
            gemSlot.setStack(stack.split(1));
        }
        if (stack.isEmpty()) {
            slot.setStack(ItemStack.EMPTY);
        } else {
            slot.markDirty();
        }
        slot.onTakeItem(player, stack);
        return original;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return canUse(context, player, DecorativeBlockRegistryME.LAPIDARY_TABLE);
    }

    @Override
    public void onClosed(PlayerEntity player) {
        super.onClosed(player);
        // Like the enchanting table: the gem and notes go back to the player (the paper stays in the table).
        // An unfinished study is lost.
        context.run((world, pos) -> dropInventory(player, gemInventory));
        // A facet being ground when the screen closes scores 0; the rest of the cut waits in the table.
        if (table != null) {
            table.abandonFacet(player.getUuid());
        }
    }
}
