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
import net.sevenstars.middleearth.recipe.lapidary.LapidaryEyewear;
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
    /** The Faceting screen's two slots, water and gem, on the parchment. */
    public static final int FACET_WATER_X = 183;
    public static final int FACET_GEM_X = 209;
    private static final int GEM = 0;
    private static final int PAPER = 1;
    private static final int NOTES = 2;
    private static final int WATER_SLOT = 3;
    private static final int CUT_GEM_SLOT = 4;
    private static final int PLAYER_SLOTS = 5;
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
        this(syncId, playerInventory, ScreenHandlerContext.EMPTY, new SimpleInventory(1), new SimpleInventory(2),
            new ArrayPropertyDelegate(LapidaryTableBlockEntity.F_COUNT), null, false);
    }

    /** The screen of {@code table}, opened at its wheel (on Faceting) or at its study half (on the study). */
    public LapidaryScreenHandler(int syncId, PlayerInventory playerInventory, ScreenHandlerContext context,
                                 LapidaryTableBlockEntity table, boolean atWheel) {
        this(syncId, playerInventory, context, table.getPaper(), table.getFaceting(), table.getFacetState(), table, atWheel);
    }

    private LapidaryScreenHandler(int syncId, PlayerInventory playerInventory, ScreenHandlerContext context,
                                  Inventory paperInventory, Inventory facetingInventory, PropertyDelegate facetState,
                                  @Nullable LapidaryTableBlockEntity table, boolean atWheel) {
        super(ScreenHandlerRegistryME.LAPIDARY_SCREEN_HANDLER, syncId);
        this.context = context;
        this.paperInventory = paperInventory;
        this.facetingInventory = facetingInventory;
        this.facetState = facetState;
        this.table = table;
        this.player = playerInventory.player;
        this.properties = new ArrayPropertyDelegate(PROPERTY_COUNT);
        // The table opens on the half it was opened at: Faceting at the wheel, the study at the study half.
        properties.set(P_MODE, table != null && atWheel ? FACETING : READING);
        if (table != null && !player.getWorld().isClient()) {
            table.claim(player.getUuid(), atWheel);
        }

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
                // A fitted pump waters the wheel: the slot takes nothing (a bucket left in it can still come out).
                return stack.isOf(Items.WATER_BUCKET) && getPumpTier() == 0;
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
        addPlayerSlots(playerInventory, INVENTORY_X, INVENTORY_Y);
        addProperties(properties);
        addProperties(facetState);
        // A gem left on the study pedestal is still there.
        if (!atWheel) {
            takeStudyGem();
        }
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

    /** The highest quality value a cut on this table can reach with its upgrades. */
    public int getBenchCap() {
        return facetState.get(LapidaryTableBlockEntity.F_CAP);
    }

    /** The tier of the upgrade fitted in {@code slot} (lenses, wheel or lamp): 0 none, 1 Masterwork, 2 Relic. */
    public int getUpgradeTier(net.sevenstars.middleearth.recipe.lapidary.LapidaryTools.Slot slot) {
        int packed = facetState.get(LapidaryTableBlockEntity.F_UPGRADES);
        return switch (slot) {
            case WHEEL -> packed % 3;
            case LENSES -> packed / 3 % 3;
            case LAMP -> packed / 9 % 3;
            case PUMP -> getPumpTier();
        };
    }

    /** The tier of the pump fitted to the table: 0 none, 1 Standard, 2 Numenorean. */
    public int getPumpTier() {
        return facetState.get(LapidaryTableBlockEntity.F_PUMP);
    }

    /** Buckets of water in the fitted pump's vessel. */
    public int getWater() {
        return facetState.get(LapidaryTableBlockEntity.F_WATER);
    }

    /** How many buckets the fitted pump's vessel holds (0 without a pump). */
    public int getWaterCapacity() {
        int pump = getPumpTier();
        return pump == 2 ? 10 : pump == 1 ? 5 : 0;
    }

    /** Whether the wheel has water for a cut: water in the pump's vessel, or (without a pump) a water bucket. */
    public boolean hasWater() {
        return getPumpTier() > 0 ? getWater() > 0 : facetingInventory.getStack(LapidaryTableBlockEntity.WATER).isOf(Items.WATER_BUCKET);
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
            boolean faceting = id == MODE_FACETING;
            if (getState() == STATE_STUDYING || isCutActive() || faceting == isFaceting()) {
                return false;
            }
            // Each half has one player at a time: the other half has to be free.
            if (table != null && !player.getWorld().isClient()) {
                net.minecraft.server.network.ServerPlayerEntity other = table.otherUser(player, faceting);
                if (other != null) {
                    player.sendMessage(net.minecraft.text.Text.translatable("message.middle-earth.lapidary_table.in_use",
                        other.getDisplayName()), true);
                    return false;
                }
                if (faceting) {
                    leaveStudyGem();
                }
                table.claim(player.getUuid(), faceting);
            }
            properties.set(P_MODE, faceting ? FACETING : READING);
            if (!faceting) {
                takeStudyGem();
            }
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
            return table.startCut(GemCut.values()[id - START_CUT], player);
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
        // Lensed eyewear: one rune can be lit by moving the candle alone.
        RefractionReading.Puzzle puzzle = RefractionReading.generate(player.getRandom().nextLong(), setup.outline(),
            setup.band().index(), setup.band().runes(), LapidaryEyewear.wornTier(player) > 0);
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
        // The gem being studied sits on the table's plinth.
        if (inventory == gemInventory && table != null && !player.getWorld().isClient()) {
            table.setStudyGem(getGem());
        }
    }

    /** The study screen shows the table's pedestal gem in its slot (server side; the slot syncs). */
    private void takeStudyGem() {
        if (table != null && !player.getWorld().isClient() && !table.getStudyGem().isEmpty() && getGem().isEmpty()) {
            gemInventory.setStack(GEM, table.getStudyGem().copy());
        }
    }

    /** Leaving the study screen leaves its gem on the table's pedestal. */
    private void leaveStudyGem() {
        if (table != null && !player.getWorld().isClient()) {
            ItemStack gem = gemInventory.removeStack(GEM);
            table.setStudyGem(gem);
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
            Slot into = slots.get(stack.isOf(Items.WATER_BUCKET) ? WATER_SLOT : CUT_GEM_SLOT);
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

    /** Whether this is the screen of {@code table}. */
    public boolean isFor(LapidaryTableBlockEntity table) {
        return this.table == table;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return canUse(context, player, DecorativeBlockRegistryME.LAPIDARY_TABLE);
    }

    @Override
    public void onClosed(PlayerEntity player) {
        super.onClosed(player);
        // The gem stays on the table's study pedestal and the paper in the table; the notes go back to the player.
        // An unfinished study is lost.
        boolean studying = !isFaceting();
        ItemStack gem = table != null && !player.getWorld().isClient() ? gemInventory.removeStack(GEM) : ItemStack.EMPTY;
        context.run((world, pos) -> dropInventory(player, gemInventory));
        // A facet being ground when the screen closes scores 0; the rest of the cut waits in the table.
        if (table != null) {
            table.abandonFacet(player.getUuid());
            table.release(player.getUuid());
            if (!player.getWorld().isClient() && studying) {
                table.setStudyGem(gem);
            }
        }
    }
}
