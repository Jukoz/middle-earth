package net.sevenstars.middleearth.light;

import java.util.HashMap;
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
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import net.sevenstars.middleearth.block.registration.DecorativeBlockRegistryME;
import net.sevenstars.middleearth.block.special.beamlamp.BeamLightBlock;
import org.jetbrains.annotations.Nullable;

/**
 * Moving light, done the only way the game allows: invisible {@link BeamLightBlock}s where the light falls,
 * placed on the server so every player sees them. Whoever places one must refresh it; one left unrefreshed for
 * a second removes itself (also after a restart), so nothing is ever left behind.
 */
public final class BeamLights {
    /** Ticks a light may go without a refresh before it goes out. */
    private static final long STALE_TICKS = 20;

    private static final Map<RegistryKey<World>, Long2LongOpenHashMap> FRESH = new HashMap<>();
    private static final Map<UUID, Held> HELD = new HashMap<>();
    /** Each carried flame's flicker: its light now, and when it next changes (world time). */
    private static final Map<UUID, long[]> FLICKER = new HashMap<>();

    private record Held(RegistryKey<World> world, @Nullable BlockPos spot, @Nullable BlockPos glow) {
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
                world.setBlockState(pos, state.with(BeamLightBlock.LEVEL, level), Block.NOTIFY_LISTENERS);
            }
        } else if (state.isAir()) {
            world.setBlockState(pos, light.getDefaultState().with(BeamLightBlock.LEVEL, level), Block.NOTIFY_LISTENERS);
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
            world.setBlockState(pos, state.with(BeamLightBlock.LEVEL, level), Block.NOTIFY_LISTENERS);
        }
    }

    /** Puts out the light at {@code pos}, if one of ours is there. */
    public static void remove(ServerWorld world, BlockPos pos) {
        fresh(world).remove(pos.asLong());
        if (loaded(world, pos) && world.getBlockState(pos).isOf(DecorativeBlockRegistryME.BEAM_LIGHT)) {
            world.setBlockState(pos, Blocks.AIR.getDefaultState(), Block.NOTIFY_LISTENERS);
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

    /** Every player's off-hand light: the beam's spot and the glow at the holder, moved as they move. */
    private static void tickPlayers(ServerWorld world) {
        LampDazzle.tick(world);
        for (ServerPlayerEntity player : world.getPlayers()) {
            HeldLight light = HeldLights.held(player);
            BlockPos spot = null;
            BlockPos glow = null;
            int spotLevel = 0;
            int glowLevel = 0;
            Vec3d eye = player.getEyePos();
            if (light instanceof HeldLight.Beam beam) {
                if ((world.getTime() + player.getId()) % LampDazzle.CHECK_TICKS == 0) {
                    LampDazzle.beam(world, player, eye, player.getRotationVec(1.0F), beam.range());
                }
                spot = beamSpot(world, eye, player.getRotationVec(1.0F), beam.range());
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
            if (spot != null && !place(world, spot, spotLevel)) {
                spot = null;
            }
            if (glow != null && !glow.equals(spot) && !place(world, glow, glowLevel)) {
                glow = null;
            }
            if (glow != null && !glow.equals(spot) && before != null && glow.equals(before.glow())) {
                dimTo(world, glow, glowLevel);
            }
            if (spot == null && glow == null) {
                HELD.remove(player.getUuid());
            } else {
                HELD.put(player.getUuid(), new Held(world.getRegistryKey(), spot, glow));
            }
        }
    }
}
