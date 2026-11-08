package net.sevenstars.middleearth.block.special.lapidarytable;

import net.sevenstars.middleearth.block.registration.BlockEntityRegistryME;
import net.sevenstars.middleearth.item.items.gems.GemCut;
import net.sevenstars.middleearth.recipe.lapidary.Faceting;
import net.sevenstars.middleearth.recipe.lapidary.LapidaryTools;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.util.ItemScatterer;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

/**
 * The Lapidary Table's block entity. It keeps what stays in the table between visits: the paper, and the
 * Faceting slots (water and the gem being cut) with the progress of a cut. A cut, once started, stays in the
 * table until it is finished: closing the screen keeps the progress (a facet being ground when the screen
 * closes scores 0), and breaking the table finishes the cut with that run scored 0.
 * <p>
 * It also runs the cutting effects: while a facet is being ground the block is {@link LapidaryTableBlock#CUTTING},
 * so the wheel turns (client renderer) and the grinding sound plays.
 */
public class LapidaryTableBlockEntity extends BlockEntity {
    /** One grinding sound per turn of the handle (the handle turns once a second). */
    private static final int SOUND_INTERVAL = 20;
    /** The shortest real time the server accepts for a facet, a little under {@link Faceting#FACET_SECONDS}. */
    private static final long MIN_FACET_MILLIS = (long) (Faceting.FACET_SECONDS * 1000) - 400;

    public static final int WATER = 0;
    public static final int CUT_GEM = 1;
    /** The installed tool set: it raises the best quality the table can cut to. */
    public static final int TOOLS = 2;

    // Synced faceting properties (see getFacetState).
    public static final int F_ACTIVE = 0;
    public static final int F_CUT = 1;
    public static final int F_FACET = 2;
    public static final int F_SUM = 3;
    public static final int F_RUNNING = 4;
    public static final int F_COUNT = 5;

    private int soundTimer;
    private final SimpleInventory paper = new SimpleInventory(1) {
        @Override
        public void markDirty() {
            super.markDirty();
            LapidaryTableBlockEntity.this.markDirty();
        }
    };
    private final SimpleInventory faceting = new SimpleInventory(3) {
        @Override
        public void markDirty() {
            super.markDirty();
            LapidaryTableBlockEntity.this.markDirty();
        }
    };

    // The cut in progress (saved).
    private boolean active;
    private int cut;
    private int facet;
    private int scoreSum;
    /** A facet was being ground when the world was saved: it scores 0 on the next tick. */
    private boolean abandonPending;
    // The facet being ground (not saved).
    private @Nullable UUID facetPlayer;
    private long facetStartMillis;

    private final PropertyDelegate facetState = new PropertyDelegate() {
        @Override
        public int get(int index) {
            return switch (index) {
                case F_ACTIVE -> active ? 1 : 0;
                case F_CUT -> cut;
                case F_FACET -> facet;
                case F_SUM -> scoreSum;
                case F_RUNNING -> facetPlayer != null ? 1 : 0;
                default -> 0;
            };
        }

        @Override
        public void set(int index, int value) {
        }

        @Override
        public int size() {
            return F_COUNT;
        }
    };

    public LapidaryTableBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegistryME.LAPIDARY_TABLE, pos, state);
    }

    /** The table's paper slot, shared by every screen opened on this table. */
    public Inventory getPaper() {
        return paper;
    }

    /** The Faceting slots: {@link #WATER}, {@link #CUT_GEM} and {@link #TOOLS}. */
    public Inventory getFaceting() {
        return faceting;
    }

    /** The cut's progress for screens: {@link #F_ACTIVE}, {@link #F_CUT}, {@link #F_FACET}, ... */
    public PropertyDelegate getFacetState() {
        return facetState;
    }

    public boolean isCutActive() {
        return active;
    }

    // ---- Faceting ----

    /** Starts cutting the gem into {@code chosen}: uses the water bucket. False if the cut cannot start. */
    public boolean startCut(GemCut chosen) {
        ItemStack gem = faceting.getStack(CUT_GEM);
        if (active || chosen.tier() != Faceting.nextTier(gem) || !faceting.getStack(WATER).isOf(Items.WATER_BUCKET)) {
            return false;
        }
        faceting.setStack(WATER, new ItemStack(Items.BUCKET));
        active = true;
        cut = chosen.ordinal();
        facet = 0;
        scoreSum = 0;
        facetPlayer = null;
        markDirty();
        if (world != null) {
            world.playSound(null, pos, SoundEvents.ITEM_BUCKET_EMPTY, SoundCategory.BLOCKS, 0.8F, 1.0F);
        }
        return true;
    }

    /** {@code player} starts grinding the next facet. False if no cut is waiting for one. */
    public boolean beginFacet(UUID player) {
        if (!active || facetPlayer != null) {
            return false;
        }
        facetPlayer = player;
        facetStartMillis = System.currentTimeMillis();
        setCutting(true);
        return true;
    }

    /** The facet {@code player} was grinding scored {@code score}. Too quick a report scores 0. */
    public boolean facetResult(UUID player, int score) {
        if (!active || !player.equals(facetPlayer)) {
            return false;
        }
        boolean tookItsTime = System.currentTimeMillis() - facetStartMillis >= MIN_FACET_MILLIS;
        completeFacet(tookItsTime ? MathHelper.clamp(score, 0, 100) : 0);
        return true;
    }

    /** The screen of whoever was grinding closed mid-facet: the facet scores 0. */
    public void abandonFacet(UUID player) {
        if (active && player.equals(facetPlayer)) {
            completeFacet(0);
        }
    }

    private void completeFacet(int score) {
        facetPlayer = null;
        setCutting(false);
        scoreSum += score;
        facet++;
        markDirty();
        if (world != null) {
            world.playSound(null, pos, SoundEvents.BLOCK_AMETHYST_BLOCK_HIT, SoundCategory.BLOCKS, 0.8F,
                0.8F + 0.6F * score / 100.0F);
        }
        if (facet >= Faceting.facets(GemCut.values()[cut].tier())) {
            int run = Math.round(scoreSum / (float) facet);
            finishCut(Math.min(run, benchCap()), true);
        }
    }

    /** The highest quality value a cut on this table can reach, set by the installed tools. */
    public int benchCap() {
        return LapidaryTools.cap(faceting.getStack(TOOLS));
    }

    private void finishCut(int runScore, boolean blockStays) {
        ItemStack gem = faceting.getStack(CUT_GEM);
        if (!gem.isEmpty()) {
            faceting.setStack(CUT_GEM, Faceting.result(gem, GemCut.values()[cut], runScore));
        }
        active = false;
        facetPlayer = null;
        facet = 0;
        scoreSum = 0;
        markDirty();
        if (blockStays) {
            setCutting(false);
        }
        if (world != null) {
            world.playSound(null, pos, SoundEvents.BLOCK_AMETHYST_BLOCK_RESONATE, SoundCategory.BLOCKS, 1.0F, 1.2F);
        }
    }

    @Override
    public void onBlockReplaced(BlockPos pos, BlockState oldState) {
        // Breaking the table mid-cut is no retry: the cut finishes with this run scored 0.
        if (active) {
            finishCut(0, false);
        }
        if (world != null) {
            ItemScatterer.spawn(world, pos, paper);
            ItemScatterer.spawn(world, pos, faceting);
        }
    }

    @Override
    protected void writeData(WriteView view) {
        super.writeData(view);
        Inventories.writeData(view, paper.getHeldStacks());
        Inventories.writeData(view.get("Faceting"), faceting.getHeldStacks());
        view.putBoolean("CutActive", active);
        view.putInt("Cut", cut);
        view.putInt("Facet", facet);
        view.putInt("ScoreSum", scoreSum);
        view.putBoolean("FacetRunning", facetPlayer != null || abandonPending);
    }

    @Override
    protected void readData(ReadView view) {
        super.readData(view);
        paper.getHeldStacks().clear();
        Inventories.readData(view, paper.getHeldStacks());
        faceting.getHeldStacks().clear();
        Inventories.readData(view.getReadView("Faceting"), faceting.getHeldStacks());
        active = view.getBoolean("CutActive", false);
        cut = MathHelper.clamp(view.getInt("Cut", 0), 0, GemCut.values().length - 1);
        facet = view.getInt("Facet", 0);
        scoreSum = view.getInt("ScoreSum", 0);
        abandonPending = active && view.getBoolean("FacetRunning", false);
        facetPlayer = null;
    }

    /** Start or stop cutting: the blockstate carries it, so clients get it with the block update. */
    public void setCutting(boolean cutting) {
        if (world != null && getCachedState().get(LapidaryTableBlock.CUTTING) != cutting) {
            soundTimer = 0;
            world.setBlockState(pos, getCachedState().with(LapidaryTableBlock.CUTTING, cutting), Block.NOTIFY_ALL);
        }
    }

    public static void serverTick(World world, BlockPos pos, BlockState state, LapidaryTableBlockEntity table) {
        if (table.abandonPending) {
            table.abandonPending = false;
            table.completeFacet(0);
        }
        if (!state.get(LapidaryTableBlock.CUTTING)) {
            table.soundTimer = 0;
            return;
        }
        // The wheel only turns while a facet is being ground (e.g. a table saved turning by an older version).
        if (table.facetPlayer == null) {
            table.setCutting(false);
            return;
        }
        if (table.soundTimer-- <= 0) {
            table.soundTimer = SOUND_INTERVAL;
            world.playSound(null, pos, SoundEvents.BLOCK_GRINDSTONE_USE, SoundCategory.BLOCKS,
                0.5F, 0.9F + world.random.nextFloat() * 0.2F);
        }
    }
}
