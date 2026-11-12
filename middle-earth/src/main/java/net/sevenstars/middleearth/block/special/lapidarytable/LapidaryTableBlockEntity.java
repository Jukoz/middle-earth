package net.sevenstars.middleearth.block.special.lapidarytable;

import net.sevenstars.middleearth.block.registration.BlockEntityRegistryME;
import net.sevenstars.middleearth.item.items.gems.GemCut;
import net.sevenstars.middleearth.recipe.lapidary.Faceting;
import net.sevenstars.middleearth.recipe.lapidary.LapidaryTools;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.text.Text;
import net.minecraft.util.math.Direction;
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
 * <p>
 * The upgrades set on the table (lenses, grinding wheel, lamp and pump) are kept here and shown by the renderer.
 * Together they set the best quality the table cuts to ({@link LapidaryTools#cap}). A fitted pump draws from its
 * water vessel under the study half ({@link #getWater}, filled with water buckets): each cut takes one bucket's
 * worth instead of a water bucket in the Faceting slot.
 */
public class LapidaryTableBlockEntity extends BlockEntity {
    /** One grinding sound per turn of the handle (the handle turns once a second). */
    private static final int SOUND_INTERVAL = 20;
    /** The shortest real time the server accepts for a facet, a little under {@link Faceting#FACET_SECONDS}. */
    private static final long MIN_FACET_MILLIS = (long) (Faceting.FACET_SECONDS * 1000) - 400;

    public static final int WATER = 0;
    public static final int CUT_GEM = 1;

    // Synced faceting properties (see getFacetState).
    public static final int F_ACTIVE = 0;
    public static final int F_CUT = 1;
    public static final int F_FACET = 2;
    public static final int F_SUM = 3;
    public static final int F_RUNNING = 4;
    /** The best quality value the table cuts to, with its upgrades. */
    public static final int F_CAP = 5;
    /** Each fitted upgrade's tier (0 none, 1 Masterwork, 2 Relic), packed: wheel + 3 * lenses + 9 * lamp. */
    public static final int F_UPGRADES = 6;
    /** The tier of the pump working the table: 0 none, 1 Standard, 2 Numenorean. */
    public static final int F_PUMP = 7;
    /** The water in the pump's vessel, in buckets. */
    public static final int F_WATER = 8;
    public static final int F_COUNT = 9;
    /** Where a working pump's water leaves its nozzle over the wheel (model pixels, drawn facing north). */
    public static final float NOZZLE_X = 7.5F, NOZZLE_Y = 13.4F, NOZZLE_Z = 12.5F;

    private int soundTimer;
    private final SimpleInventory paper = new SimpleInventory(1) {
        @Override
        public void markDirty() {
            super.markDirty();
            LapidaryTableBlockEntity.this.markDirty();
        }
    };
    /** The upgrades set on the table, by {@link LapidaryTools.Slot} (lenses, wheel, lamp, pump). */
    private final SimpleInventory upgrades = new SimpleInventory(LapidaryTools.Slot.values().length) {
        @Override
        public void markDirty() {
            super.markDirty();
            LapidaryTableBlockEntity.this.markDirty();
        }
    };
    /** The gem on the table's study pedestal (saved, synced to clients): it stays there between visits. */
    private ItemStack studyGem = ItemStack.EMPTY;
    private final SimpleInventory faceting = new SimpleInventory(2) {
        @Override
        public void markDirty() {
            super.markDirty();
            LapidaryTableBlockEntity.this.markDirty();
            showCutGem();
        }
    };
    /** The gem last sent to clients for the dop (server side), so they are only told when it changes. */
    private ItemStack shownCutGem = ItemStack.EMPTY;

    /** Buckets of water in the pump's vessel (saved, synced to clients); lost when the pump comes off. */
    private int water;

    // The cut in progress (saved).
    private boolean active;
    private int cut;
    private int facet;
    private int scoreSum;
    /** A facet was being ground when the world was saved: it scores 0 on the next tick. */
    private boolean abandonPending;
    // The facet being ground (not saved).
    private @Nullable UUID facetPlayer;
    /** Who has each half's screen open (not saved): one player at each half. */
    private @Nullable UUID studyUser, facetUser;
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
                case F_CAP -> benchCap();
                case F_UPGRADES -> {
                    LapidaryTools[] tiers = upgradeTiers();
                    yield tierNumber(tiers[LapidaryTools.Slot.WHEEL.ordinal()])
                        + 3 * tierNumber(tiers[LapidaryTools.Slot.LENSES.ordinal()])
                        + 9 * tierNumber(tiers[LapidaryTools.Slot.LAMP.ordinal()]);
                }
                case F_PUMP -> tierNumber(pumpTier());
                case F_WATER -> water;
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

    /** The Faceting slots: {@link #WATER} and {@link #CUT_GEM}. */
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

    /**
     * Starts cutting the gem into {@code chosen}: a fitted pump draws a bucket's worth from its vessel, otherwise
     * it uses the water bucket; {@code player} pays the cut's tier in experience levels (see {@link #levelCost}).
     * False if the cut cannot start.
     */
    public boolean startCut(GemCut chosen, PlayerEntity player) {
        ItemStack gem = faceting.getStack(CUT_GEM);
        boolean pumped = pumpTier() != null;
        if (active || chosen.tier() != Faceting.nextTier(gem)
            || (pumped ? water <= 0 : !faceting.getStack(WATER).isOf(Items.WATER_BUCKET))
            || !canPay(player, chosen)) {
            return false;
        }
        if (!player.isCreative()) {
            player.addExperienceLevels(-levelCost(chosen));
        }
        if (pumped) {
            setWater(water - 1);
        } else {
            faceting.setStack(WATER, new ItemStack(Items.BUCKET));
        }
        active = true;
        cut = chosen.ordinal();
        facet = 0;
        scoreSum = 0;
        facetPlayer = null;
        markDirty();
        if (world != null && !pumped) {
            world.playSound(null, pos, SoundEvents.ITEM_BUCKET_EMPTY, SoundCategory.BLOCKS, 0.8F, 1.0F);
        }
        return true;
    }

    /** The experience levels a cut costs, paid as it starts: its tier (1 to 5). */
    public static int levelCost(GemCut cut) {
        return cut.tier();
    }

    /** Whether {@code player} can pay for {@code cut} (always, in creative). */
    public static boolean canPay(PlayerEntity player, GemCut cut) {
        return player.isCreative() || player.experienceLevel >= levelCost(cut);
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

    /**
     * Who is at one half of the table (the faceting half, or the study half), if not {@code player}: someone else
     * whose screen of this table is open on that half. A user who left (logged out, or their screen closed or moved
     * to the other half without word) no longer holds it.
     */
    public @Nullable net.minecraft.server.network.ServerPlayerEntity otherUser(net.minecraft.entity.player.PlayerEntity player,
                                                                              boolean faceting) {
        UUID user = faceting ? facetUser : studyUser;
        if (user == null || user.equals(player.getUuid()) || world == null || world.getServer() == null) {
            return null;
        }
        net.minecraft.server.network.ServerPlayerEntity holder = world.getServer().getPlayerManager().getPlayer(user);
        if (holder == null || !(holder.currentScreenHandler instanceof net.sevenstars.middleearth.gui.lapidarytable.LapidaryScreenHandler handler)
            || !handler.isFor(this) || handler.isFaceting() != faceting) {
            if (faceting) {
                facetUser = null;
            } else {
                studyUser = null;
            }
            return null;
        }
        return holder;
    }

    /** {@code player} is at one half of the table (letting go of the other, if they held it). */
    public void claim(UUID player, boolean faceting) {
        release(player);
        if (faceting) {
            facetUser = player;
        } else {
            studyUser = player;
        }
    }

    /** {@code player} closed the table's screen. */
    public void release(UUID player) {
        if (player.equals(facetUser)) {
            facetUser = null;
        }
        if (player.equals(studyUser)) {
            studyUser = null;
        }
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

    /** The highest quality value a cut on this table can reach, set by its upgrades. */
    public int benchCap() {
        return LapidaryTools.cap(upgradeTiers());
    }

    // ---- Upgrades ----

    private static int tierNumber(@Nullable LapidaryTools tier) {
        return tier == null ? 0 : tier.ordinal() + 1;
    }

    /** The tier in each {@link LapidaryTools.Slot}, null where there is none. */
    public @Nullable LapidaryTools[] upgradeTiers() {
        LapidaryTools[] tiers = new LapidaryTools[LapidaryTools.Slot.values().length];
        for (int i = 0; i < upgrades.size(); i++) {
            tiers[i] = LapidaryTools.tierOf(upgrades.getStack(i).getItem());
        }
        return tiers;
    }

    /** What is set in {@code slot}, or empty. */
    public ItemStack upgrade(LapidaryTools.Slot slot) {
        return upgrades.getStack(slot.ordinal());
    }

    /** The fitted pump's tier, or null without one. */
    public @Nullable LapidaryTools pumpTier() {
        return LapidaryTools.tierOf(upgrades.getStack(LapidaryTools.Slot.PUMP.ordinal()).getItem());
    }

    /** How many buckets the fitted pump's vessel holds: 5 in the bucket, 10 in the Relic basin; 0 without a pump. */
    public int waterCapacity() {
        LapidaryTools tier = pumpTier();
        return tier == null ? 0 : tier == LapidaryTools.RELIC ? 10 : 5;
    }

    /** Buckets of water in the pump's vessel. */
    public int getWater() {
        return water;
    }

    private void setWater(int buckets) {
        water = MathHelper.clamp(buckets, 0, waterCapacity());
        markDirty();
        if (world != null) {
            world.updateListeners(pos, getCachedState(), getCachedState(), Block.NOTIFY_LISTENERS);
        }
    }

    /**
     * Pours the water bucket in {@code player}'s {@code hand} into the pump's vessel. False if there is no pump (the
     * table's screen opens as usual); a full vessel says so and takes nothing.
     */
    public boolean pourWater(PlayerEntity player, net.minecraft.util.Hand hand) {
        if (pumpTier() == null) {
            return false;
        }
        if (water >= waterCapacity()) {
            player.sendMessage(Text.translatable("message.middle-earth.lapidary_table.basin_full"), true);
            return true;
        }
        setWater(water + 1);
        if (!player.isCreative()) {
            player.setStackInHand(hand, new ItemStack(Items.BUCKET));
        }
        if (world != null) {
            world.playSound(null, pos, SoundEvents.ITEM_BUCKET_EMPTY, SoundCategory.BLOCKS, 1.0F, 1.0F);
        }
        return true;
    }

    /**
     * Sets the upgrade in {@code stack} on the table, giving back a lower one it replaces. False (with a word to
     * the player) if it cannot go on.
     */
    public boolean install(PlayerEntity player, ItemStack stack) {
        LapidaryTools tier = LapidaryTools.tierOf(stack.getItem());
        LapidaryTools.Slot slot = LapidaryTools.slotOf(stack.getItem());
        if (tier == null || slot == null) {
            return false;
        }
        if (active) {
            player.sendMessage(Text.translatable("message.middle-earth.lapidary_table.cutting"), true);
            return false;
        }
        ItemStack old = upgrades.getStack(slot.ordinal());
        LapidaryTools oldTier = LapidaryTools.tierOf(old.getItem());
        if (oldTier != null && oldTier.ordinal() >= tier.ordinal()) {
            player.sendMessage(Text.translatable("message.middle-earth.lapidary_table.has_upgrade"), true);
            return false;
        }
        upgrades.setStack(slot.ordinal(), stack.splitUnlessCreative(1, player));
        if (slot == LapidaryTools.Slot.PUMP) {
            water = 0; // a new pump starts dry: the old one's water goes with it
        }
        if (!old.isEmpty()) {
            player.getInventory().offerOrDrop(old);
        }
        upgradesChanged(SoundEvents.ENTITY_ITEM_FRAME_ADD_ITEM);
        return true;
    }

    /**
     * Takes the first upgrade off the table (lenses, then wheel, lamp, pump) and gives it to {@code player}. The
     * pump's water is lost with it.
     */
    public boolean removeUpgrade(PlayerEntity player) {
        if (active) {
            player.sendMessage(Text.translatable("message.middle-earth.lapidary_table.cutting"), true);
            return false;
        }
        for (int i = 0; i < upgrades.size(); i++) {
            ItemStack stack = upgrades.getStack(i);
            if (!stack.isEmpty()) {
                upgrades.setStack(i, ItemStack.EMPTY);
                if (i == LapidaryTools.Slot.PUMP.ordinal()) {
                    water = 0;
                }
                player.getInventory().offerOrDrop(stack);
                upgradesChanged(SoundEvents.ENTITY_ITEM_FRAME_REMOVE_ITEM);
                return true;
            }
        }
        return false;
    }

    private void upgradesChanged(net.minecraft.sound.SoundEvent sound) {
        markDirty();
        if (world != null) {
            world.playSound(null, pos, sound, SoundCategory.BLOCKS, 1.0F, 1.0F);
            world.updateListeners(pos, getCachedState(), getCachedState(), Block.NOTIFY_LISTENERS);
        }
    }

    /** The gem in the Faceting slot, shown on the dop over the wheel. */
    public ItemStack getCutGem() {
        return faceting.getStack(CUT_GEM);
    }

    /** Tells clients when the gem on the dop changes. */
    private void showCutGem() {
        ItemStack gem = faceting.getStack(CUT_GEM);
        if (world != null && !world.isClient && !ItemStack.areEqual(gem, shownCutGem)) {
            shownCutGem = gem.copy();
            world.updateListeners(pos, getCachedState(), getCachedState(), Block.NOTIFY_LISTENERS);
        }
    }

    // ---- The gem on the plinth ----

    /** The gem being studied, shown on the plinth. */
    public ItemStack getStudyGem() {
        return studyGem;
    }

    /** Shows {@code gem} on the plinth (empty to clear it). */
    public void setStudyGem(ItemStack gem) {
        if (ItemStack.areEqual(gem, studyGem)) {
            return;
        }
        studyGem = gem.copyWithCount(Math.min(1, gem.getCount()));
        markDirty();
        if (world != null) {
            world.updateListeners(pos, getCachedState(), getCachedState(), Block.NOTIFY_LISTENERS);
        }
    }

    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt(RegistryWrapper.WrapperLookup registries) {
        return createNbt(registries);
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
            ItemScatterer.spawn(world, pos, upgrades);
            ItemScatterer.spawn(world, pos.getX(), pos.getY(), pos.getZ(), studyGem);
        }
    }

    @Override
    protected void writeData(WriteView view) {
        super.writeData(view);
        Inventories.writeData(view, paper.getHeldStacks());
        Inventories.writeData(view.get("Faceting"), faceting.getHeldStacks());
        Inventories.writeData(view.get("Upgrades"), upgrades.getHeldStacks());
        if (!studyGem.isEmpty()) {
            view.put("StudyGem", ItemStack.CODEC, studyGem);
        }
        view.putInt("Water", water);
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
        upgrades.getHeldStacks().clear();
        Inventories.readData(view.getReadView("Upgrades"), upgrades.getHeldStacks());
        studyGem = view.read("StudyGem", ItemStack.CODEC).orElse(ItemStack.EMPTY);
        water = MathHelper.clamp(view.getInt("Water", 0), 0, waterCapacity());
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

    /**
     * The study candle burns while a gem rests on the pedestal, and a fitted pump's water drips onto the wheel while
     * it grinds.
     */
    public static void clientTick(World world, BlockPos pos, BlockState state, LapidaryTableBlockEntity table) {
        Direction facing = state.get(LapidaryTableBlock.FACING);
        if (!table.studyGem.isEmpty() && world.getRandom().nextInt(4) == 0) {
            // The wick, on the study half (model x 16..32).
            spawn(world, pos, facing, 24, 17.5, 13, net.minecraft.particle.ParticleTypes.SMALL_FLAME);
        }
        if (state.get(LapidaryTableBlock.CUTTING) && world.getRandom().nextInt(3) == 0 && table.pumpTier() != null) {
            spawn(world, pos, facing, NOZZLE_X,
                NOZZLE_Y,
                NOZZLE_Z,
                net.minecraft.particle.ParticleTypes.FALLING_WATER);
        }
    }

    /** A particle at a point of the table's model (pixels, drawn facing north), turned for {@code facing}. */
    private static void spawn(World world, BlockPos pos, Direction facing, double mx, double my, double mz,
                              net.minecraft.particle.ParticleEffect particle) {
        double lx = mx / 16 - 0.5, lz = mz / 16 - 0.5;
        double x = switch (facing) {
            case EAST -> -lz;
            case SOUTH -> -lx;
            case WEST -> lz;
            default -> lx;
        };
        double z = switch (facing) {
            case EAST -> lx;
            case SOUTH -> -lz;
            case WEST -> -lx;
            default -> lz;
        };
        world.addParticleClient(particle, pos.getX() + 0.5 + x, pos.getY() + my / 16, pos.getZ() + 0.5 + z, 0, 0, 0);
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
