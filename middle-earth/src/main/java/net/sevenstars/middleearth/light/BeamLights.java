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

/** Server side of moving light: places and refreshes {@link BeamLightBlock}s for held lights and beams. */
public final class BeamLights {
    private static final long STALE_TICKS = 20;
    private static final int FLAGS = Block.NOTIFY_LISTENERS | Block.FORCE_STATE;
    private static final int PATH_STEP = 2;
    private static final int PATH_NEAR = 3;
    public static final int PATH_DIMMER = 3;

    private static final Map<RegistryKey<World>, Long2LongOpenHashMap> FRESH = new HashMap<>();
    private static final Map<UUID, Held> HELD = new HashMap<>();
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

    /** Brightest lamp light reaching a position, falling off one level per block (Manhattan). */
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

    /** Flame light dips a level or two below its level, changing every 1-3 seconds. */
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

    private static void dimTo(ServerWorld world, BlockPos pos, int level) {
        BlockState state = world.getBlockState(pos);
        if (state.isOf(DecorativeBlockRegistryME.BEAM_LIGHT) && state.get(BeamLightBlock.LEVEL) > level) {
            world.setBlockState(pos, state.with(BeamLightBlock.LEVEL, level), FLAGS);
        }
    }

    public static void remove(ServerWorld world, BlockPos pos) {
        fresh(world).remove(pos.asLong());
        if (loaded(world, pos) && world.getBlockState(pos).isOf(DecorativeBlockRegistryME.BEAM_LIGHT)) {
            world.setBlockState(pos, Blocks.AIR.getDefaultState(), FLAGS);
        }
    }

    public static Vec3d beamEnd(World world, Vec3d from, Vec3d direction, double range) {
        Vec3d to = from.add(direction.multiply(range));
        BlockHitResult hit = world.raycast(new RaycastContext(from, to, RaycastContext.ShapeType.COLLIDER,
            RaycastContext.FluidHandling.NONE, ShapeContext.absent()));
        return hit.getType() == HitResult.Type.MISS ? to : hit.getPos();
    }

    /** The open block just before the beam's hit face, where its light goes. */
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

    /** Dimmer lights every few blocks along the beam, only where it passes near solid blocks. */
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

    private static void tickPlayers(ServerWorld world) {
        LampDazzle.tick(world);
        boolean ambient = HeldLights.ambientLight(world.getServer());
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
            } else if (light instanceof HeldLight.Glow lantern && ambient) {
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
