package net.sevenstars.middleearth.light;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import it.unimi.dsi.fastutil.longs.Long2LongOpenHashMap;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ShapeContext;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkSectionPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import net.sevenstars.middleearth.block.registration.DecorativeBlockRegistryME;
import net.sevenstars.middleearth.block.special.beamlight.BeamLightBlock;
import org.jetbrains.annotations.Nullable;

/**
 * Moving light, done the only way the game allows: invisible {@link BeamLightBlock}s where the light falls,
 * placed on the server so every player sees them. Whoever places one must refresh it; one left unrefreshed for
 * a second removes itself (also after a restart), so nothing is ever left behind.
 */
public final class BeamLights {
    /** Ticks a light may go without a refresh before it goes out. */
    private static final long STALE_TICKS = 20;
    /**
     * How the lights change: sent to players, but without the shape updates to the blocks beside them, so a plant
     * that cannot bear bright light (a mushroom) is not made to check its footing and break.
     */
    private static final int FLAGS = Block.NOTIFY_LISTENERS | Block.FORCE_STATE;
    /** Along a beam: a light every this many blocks, where something solid is within {@link #PATH_NEAR} blocks. */
    private static final int PATH_STEP = 2;
    private static final int PATH_NEAR = 3;
    /** How much dimmer the lights along a beam are than its spot. */
    public static final int PATH_DIMMER = 3;

    private static final Map<RegistryKey<World>, Long2LongOpenHashMap> FRESH = new HashMap<>();
    private static final Map<UUID, Held> HELD = new HashMap<>();
    /** Each carried flame's flicker: its light now, and when it next changes (world time). */
    private static final Map<UUID, long[]> FLICKER = new HashMap<>();

    private record Held(RegistryKey<World> world, @Nullable BlockPos spot, @Nullable BlockPos glow, List<BlockPos> path) {
    }

    private BeamLights() {
    }

    public static void register() {
        ServerTickEvents.END_WORLD_TICK.register(BeamLights::tickPlayers);
    }

    private static Long2LongOpenHashMap fresh(World world) {
        return FRESH.computeIfAbsent(world.getRegistryKey(), key -> new Long2LongOpenHashMap());
    }

    public static boolean isFresh(ServerWorld world, BlockPos pos) {
        Long2LongOpenHashMap map = fresh(world);
        return map.containsKey(pos.asLong()) && world.getTime() - map.get(pos.asLong()) <= STALE_TICKS;
    }

    private static boolean loaded(World world, BlockPos pos) {
        return world.isChunkLoaded(ChunkSectionPos.getSectionCoord(pos.getX()), ChunkSectionPos.getSectionCoord(pos.getZ()));
    }

    private static boolean free(BlockState state) {
        return state.isAir() || state.getBlock() instanceof BeamLightBlock;
    }

    /** Lights {@code pos} at {@code level} (or keeps a brighter light there) and marks it fresh. */
    public static boolean place(ServerWorld world, BlockPos pos, int level) {
        if (!loaded(world, pos)) {
            return false;
        }
        BlockState state = world.getBlockState(pos);
        Block light = DecorativeBlockRegistryME.BEAM_LIGHT;
        if (state.isOf(light)) {
            if (state.get(BeamLightBlock.LEVEL) < level) {
                world.setBlockState(pos, state.with(BeamLightBlock.LEVEL, level), FLAGS);
            }
        } else if (state.isAir()) {
            world.setBlockState(pos, light.getDefaultState().with(BeamLightBlock.LEVEL, level), FLAGS);
            world.scheduleBlockTick(pos, light, BeamLightBlock.CHECK_TICKS);
        } else {
            return false;
        }
        fresh(world).put(pos.asLong(), world.getTime());
        return true;
    }

    /**
     * The most light the lamps' own light blocks can give {@code pos}: each one's level less its distance
     * (light falls one level a block, at best in a straight run).
     */
    public static int lampLightAt(ServerWorld world, BlockPos pos) {
        int best = 0;
        BlockPos.Mutable at = new BlockPos.Mutable();
        for (long key : fresh(world).keySet()) {
            at.set(key);
            int distance = at.getManhattanDistance(pos);
            if (distance >= 15) {
                continue;
            }
            BlockState state = world.getBlockState(at);
            if (state.getBlock() instanceof BeamLightBlock) {
                best = Math.max(best, state.get(BeamLightBlock.LEVEL) - distance);
            }
        }
        return best;
    }

    /**
     * A naked flame's light: a little below its full light, now one level, now another, changing every second
     * or three (2-3 levels down for a torch, 1 for a weak light like a candle).
     */
    private static int flicker(ServerWorld world, UUID holder, int level) {
        long now = world.getTime();
        long[] state = FLICKER.get(holder);
        if (state == null || now >= state[1] || now < state[1] - 60) {
            int dip = level >= 8 ? 2 + world.getRandom().nextInt(2) : world.getRandom().nextInt(2);
            state = new long[]{Math.max(1, level - dip), now + 20 + world.getRandom().nextInt(41)};
            FLICKER.put(holder, state);
        }
        return (int) state[0];
    }

    /** Lowers one of our lights to {@code level} (placing only ever brightens). */
    private static void dimTo(ServerWorld world, BlockPos pos, int level) {
        BlockState state = world.getBlockState(pos);
        if (state.isOf(DecorativeBlockRegistryME.BEAM_LIGHT) && state.get(BeamLightBlock.LEVEL) > level) {
            world.setBlockState(pos, state.with(BeamLightBlock.LEVEL, level), FLAGS);
        }
    }

    /** Puts out the light at {@code pos}, if one of ours is there. */
    public static void remove(ServerWorld world, BlockPos pos) {
        fresh(world).remove(pos.asLong());
        if (loaded(world, pos) && world.getBlockState(pos).isOf(DecorativeBlockRegistryME.BEAM_LIGHT)) {
            world.setBlockState(pos, Blocks.AIR.getDefaultState(), FLAGS);
        }
    }

    /** Where a beam lands: the hit point, or the end of its reach in the open. */
    public static Vec3d beamEnd(World world, Vec3d from, Vec3d direction, double range) {
        Vec3d to = from.add(direction.multiply(range));
        BlockHitResult hit = world.raycast(new RaycastContext(from, to, RaycastContext.ShapeType.COLLIDER,
            RaycastContext.FluidHandling.NONE, ShapeContext.absent()));
        return hit.getType() == HitResult.Type.MISS ? to : hit.getPos();
    }

    /**
     * The block a beam lights: the open block just in front of where it lands, stepping back along the beam if
     * that one is taken. Null if nothing near the end is free or loaded.
     */
    public static @Nullable BlockPos beamSpot(World world, Vec3d from, Vec3d direction, double range) {
        if (!loaded(world, BlockPos.ofFloored(from)) || !loaded(world, BlockPos.ofFloored(from.add(direction.multiply(range))))) {
            return null;
        }
        Vec3d end = beamEnd(world, from, direction, range);
        for (int step = 0; step < 4; step++) {
            BlockPos pos = BlockPos.ofFloored(end.subtract(direction.multiply(0.3 + step * 0.6)));
            if (free(world.getBlockState(pos))) {
                return pos;
            }
        }
        return null;
    }

    /**
     * The open blocks a beam lights on its way, short of its spot: one every {@link #PATH_STEP} blocks, where a
     * wall, the ground or anything else solid is within {@link #PATH_NEAR} blocks to be lit (none in the open air).
     */
    public static List<BlockPos> beamPath(World world, Vec3d from, Vec3d direction, double range) {
        List<BlockPos> path = new ArrayList<>();
        double length = beamEnd(world, from, direction, range).distanceTo(from);
        for (double along = PATH_STEP; along < length - 1.5; along += PATH_STEP) {
            BlockPos pos = BlockPos.ofFloored(from.add(direction.multiply(along)));
            if (loaded(world, pos) && free(world.getBlockState(pos)) && nearSolid(world, pos) && !path.contains(pos)) {
                path.add(pos);
            }
        }
        return path;
    }

    private static boolean nearSolid(World world, BlockPos pos) {
        BlockPos.Mutable at = new BlockPos.Mutable();
        for (Direction side : Direction.values()) {
            for (int i = 1; i <= PATH_NEAR; i++) {
                at.set(pos).move(side, i);
                if (!world.getBlockState(at).getCollisionShape(world, at).isEmpty()) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Moves a beam's lights along its way from {@code before} to {@code now}: puts out those it no longer passes
     * (but not those in {@code keep}, its own other lights), lights the new ones at {@code level}. Returns the lit.
     */
    public static List<BlockPos> movePath(ServerWorld world, List<BlockPos> before, List<BlockPos> now, int level,
                                          Collection<BlockPos> keep) {
        for (BlockPos pos : before) {
            if (!now.contains(pos) && !keep.contains(pos)) {
                remove(world, pos);
            }
        }
        List<BlockPos> lit = new ArrayList<>();
        for (BlockPos pos : now) {
            if (keep.contains(pos)) {
                continue;
            }
            if (place(world, pos, level)) {
                lit.add(pos);
            }
        }
        return lit;
    }

    /** Every player's off-hand light: the beam's spot and the glow at the holder, moved as they move. */
    private static void tickPlayers(ServerWorld world) {
        LampDazzle.tick(world);
        for (ServerPlayerEntity player : world.getPlayers()) {
            HeldLight light = HeldLights.held(player);
            BlockPos spot = null;
            BlockPos glow = null;
            List<BlockPos> path = List.of();
            int spotLevel = 0;
            int glowLevel = 0;
            Vec3d eye = player.getEyePos();
            if (light instanceof HeldLight.Beam beam) {
                if ((world.getTime() + player.getId()) % LampDazzle.CHECK_TICKS == 0) {
                    LampDazzle.beam(world, player, eye, player.getRotationVec(1.0F), beam.range());
                }
                spot = beamSpot(world, eye, player.getRotationVec(1.0F), beam.range());
                path = beamPath(world, eye, player.getRotationVec(1.0F), beam.range());
                spotLevel = beam.spotLevel();
                glowLevel = beam.glowLevel();
            } else if (light instanceof HeldLight.Glow lantern) {
                glowLevel = lantern.flame() ? flicker(world, player.getUuid(), lantern.level()) : lantern.level();
            }
            if (!(light instanceof HeldLight.Glow lantern) || !lantern.flame()) {
                FLICKER.remove(player.getUuid());
            }
            if (glowLevel > 0 && free(world.getBlockState(BlockPos.ofFloored(eye)))) {
                glow = BlockPos.ofFloored(eye);
            }
            Held before = HELD.get(player.getUuid());
            if (before != null && before.world() == world.getRegistryKey()) {
                if (before.spot() != null && !before.spot().equals(spot) && !before.spot().equals(glow)) {
                    remove(world, before.spot());
                }
                if (before.glow() != null && !before.glow().equals(glow) && !before.glow().equals(spot)) {
                    remove(world, before.glow());
                }
            }
            if (before != null && before.world() == world.getRegistryKey()) {
                List<BlockPos> keep = new ArrayList<>(2);
                if (spot != null) {
                    keep.add(spot);
                }
                if (glow != null) {
                    keep.add(glow);
                }
                List<BlockPos> old = new ArrayList<>(before.path());
                old.removeIf(pos -> pos.equals(before.spot()) || pos.equals(before.glow()));
                path = movePath(world, old, path, Math.max(1, spotLevel - PATH_DIMMER), keep);
                // A light that was this beam's spot and is now on its way dims to the way's light.
                for (BlockPos pos : path) {
                    if (pos.equals(before.spot())) {
                        dimTo(world, pos, Math.max(1, spotLevel - PATH_DIMMER));
                    }
                }
            } else {
                path = movePath(world, List.of(), path, Math.max(1, spotLevel - PATH_DIMMER), List.of());
            }
            if (spot != null && !place(world, spot, spotLevel)) {
                spot = null;
            }
            if (glow != null && !glow.equals(spot) && !place(world, glow, glowLevel)) {
                glow = null;
            }
            if (glow != null && !glow.equals(spot) && before != null && glow.equals(before.glow())) {
                dimTo(world, glow, glowLevel);
            }
            if (spot == null && glow == null && path.isEmpty()) {
                HELD.remove(player.getUuid());
            } else {
                HELD.put(player.getUuid(), new Held(world.getRegistryKey(), spot, glow, path));
            }
        }
    }
}
