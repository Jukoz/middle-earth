package net.sevenstars.middleearth.dev;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.command.argument.BlockPosArgumentType;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.entity.ai.pathing.PathNode;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtIo;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.structure.StructureTemplate;
import net.minecraft.text.Text;
import net.minecraft.util.WorldSavePath;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.sevenstars.middleearth.entity.npcs.NpcEntity;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

// Records every distinct task an npc gave up on, with what's needed to reproduce it. That's the seed and location, the
// npc, its target, its path and the surrounding blocks as a structure template. Kept in run/npc-failures/. Also adds
// /npcpath, which asks an npc's own pathfinder for a route to a block
public final class NpcFailureRecorder {
    private static final Gson GSON = new Gson();
    // Margin around the npc and its target so the route around a building is in the snapshot too
    private static final int MARGIN = 12;
    private static final int MAXIMUM_ACROSS = 72;
    private static final int MAXIMUM_HEIGHT = 32;
    // Targets further than this are left out of the snapshot, so it only covers the npc's surroundings
    private static final int TARGET_IN_SNAPSHOT = 48;
    private static final int MAXIMUM_RECORDS = 600;
    private static final int MAXIMUM_NODES = 48;
    private static final int MAXIMUM_RANGE = 4;
    private static final int MAXIMUM_BUDGET = 16;
    private static final int DEFAULT_RANGE = 0;
    private static final int DEFAULT_BUDGET = 1;
    private static final long TICKS_PER_DAY = 24000;
    private static final String FAILURES_FOLDER = "npc-failures";
    private static final String FAILURES_FILE = "failures.jsonl";
    private static final String SNAPSHOT_EXTENSION = ".nbt";
    private static final Set<String> SEEN = new HashSet<>();
    private static int recorded = 0;

    private NpcFailureRecorder() {
    }

    public static void record(NpcEntity npc, String who, String task, String phase, String why, @Nullable BlockPos target,
                              @Nullable BlockPos bed, @Nullable BlockPos home, @Nullable Path path) {
        if(!(npc.getWorld() instanceof ServerWorld world) || recorded >= MAXIMUM_RECORDS)
            return;
        BlockPos pos = npc.getBlockPos();
        // The same npc kind giving up the same way at the same spot counts as one failure, however often it happens
        String key = who + "|" + task + "|" + why.replaceAll("-?\\d+", "#") + "|" + (pos.getX() >> 1) + "," + pos.getY() + "," + (pos.getZ() >> 1);
        if(!SEEN.add(key))
            return;
        recorded++;
        // Uses the save folder name because worlds copied from one another keep the first one's level name
        String world_name = String.valueOf(world.getServer().getSavePath(WorldSavePath.ROOT).normalize().getFileName()).replaceAll("[^A-Za-z0-9_-]", "_");
        String id = "%s-%d-%s".formatted(world_name, world.getTime(), Integer.toHexString(key.hashCode()));
        java.nio.file.Path dir = FabricLoader.getInstance().getGameDir().resolve(FAILURES_FOLDER);

        BlockPos other = target != null && target.isWithinDistance(pos, TARGET_IN_SNAPSHOT) ? target : pos;
        int minX = Math.min(pos.getX(), other.getX()) - MARGIN;
        int minY = Math.min(pos.getY(), other.getY()) - MARGIN;
        int minZ = Math.min(pos.getZ(), other.getZ()) - MARGIN;
        int sizeX = Math.min(Math.abs(pos.getX() - other.getX()) + 2 * MARGIN + 1, MAXIMUM_ACROSS);
        int sizeY = Math.min(Math.abs(pos.getY() - other.getY()) + 2 * MARGIN + 1, MAXIMUM_HEIGHT);
        int sizeZ = Math.min(Math.abs(pos.getZ() - other.getZ()) + 2 * MARGIN + 1, MAXIMUM_ACROSS);
        BlockPos origin = new BlockPos(minX, minY, minZ);

        JsonObject out = new JsonObject();
        out.addProperty("id", id);
        out.addProperty("world", world_name);
        out.addProperty("seed", world.getSeed());
        out.addProperty("dimension", world.getRegistryKey().getValue().toString());
        out.addProperty("gameTime", world.getTime());
        out.addProperty("dayTime", world.getTimeOfDay() % TICKS_PER_DAY);
        out.addProperty("who", who);
        out.addProperty("uuid", npc.getUuidAsString());
        out.addProperty("type", npc.getNpcClass() == null ? "?" : npc.getNpcClass().getId().toString());
        out.addProperty("role", npc.getJobId() == null ? null : npc.getJobId().toString());
        out.addProperty("task", task);
        out.addProperty("phase", phase);
        out.addProperty("why", why);
        JsonArray at = new JsonArray();
        at.add(npc.getX());
        at.add(npc.getY());
        at.add(npc.getZ());
        out.add("pos", at);
        out.addProperty("yaw", npc.getYaw());
        out.addProperty("onGround", npc.isOnGround());
        out.addProperty("inWater", npc.isTouchingWater());
        out.addProperty("width", npc.getWidth());
        out.addProperty("height", npc.getHeight());
        out.add("target", block(target));
        out.add("bed", block(bed));
        out.add("bedStand", block(npc.getBedStand()));
        out.add("home", block(home));
        out.add("store", block(npc.getStore()));
        out.add("manager", block(npc.getStructureManagerHostPos()));
        if(path != null) {
            JsonObject route = new JsonObject();
            route.addProperty("length", path.getLength());
            route.addProperty("index", path.getCurrentNodeIndex());
            route.addProperty("reachesTarget", path.reachesTarget());
            JsonArray nodes = new JsonArray();
            for(int i = 0; i < Math.min(path.getLength(), MAXIMUM_NODES); i++) {
                PathNode node = path.getNode(i);
                JsonArray n = new JsonArray();
                n.add(node.x);
                n.add(node.y);
                n.add(node.z);
                n.add(node.type.name().toLowerCase());
                nodes.add(n);
            }
            route.add("nodes", nodes);
            out.add("path", route);
        }
        out.add("snapshotOrigin", block(origin));
        JsonArray size = new JsonArray();
        size.add(sizeX);
        size.add(sizeY);
        size.add(sizeZ);
        out.add("snapshotSize", size);
        out.addProperty("snapshot", id + SNAPSHOT_EXTENSION);
        try {
            Files.createDirectories(dir);
            StructureTemplate template = new StructureTemplate();
            template.saveFromWorld(world, origin, new Vec3i(sizeX, sizeY, sizeZ), false, List.of());
            NbtIo.writeCompressed(template.writeNbt(new NbtCompound()), dir.resolve(id + SNAPSHOT_EXTENSION));
            Files.writeString(dir.resolve(FAILURES_FILE), GSON.toJson(out) + "\n", StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch(IOException ignored) {
        }
    }

    private static @Nullable JsonArray block(@Nullable BlockPos pos) {
        if(pos == null)
            return null;
        JsonArray out = new JsonArray();
        out.add(pos.getX());
        out.add(pos.getY());
        out.add(pos.getZ());
        return out;
    }

    // /npcpath <npc> <x y z> [range] prints the route the npc's pathfinder finds to a block, node by node
    public static void registerCommand() {
        CommandRegistrationCallback.EVENT.register((dispatcher, access, environment) -> dispatcher.register(CommandManager.literal("npcpath")
                .requires(source -> source.hasPermissionLevel(2))
                .then(CommandManager.argument("npc", EntityArgumentType.entity())
                        .then(CommandManager.argument("to", BlockPosArgumentType.blockPos())
                                .executes(context -> path(context.getSource(), EntityArgumentType.getEntity(context, "npc"), BlockPosArgumentType.getBlockPos(context, "to"), DEFAULT_RANGE, DEFAULT_BUDGET))
                                .then(CommandManager.argument("range", IntegerArgumentType.integer(0, MAXIMUM_RANGE))
                                        .executes(context -> path(context.getSource(), EntityArgumentType.getEntity(context, "npc"),
                                                BlockPosArgumentType.getBlockPos(context, "to"), IntegerArgumentType.getInteger(context, "range"), DEFAULT_BUDGET))
                                        .then(CommandManager.argument("budget", IntegerArgumentType.integer(1, MAXIMUM_BUDGET))
                                                .executes(context -> path(context.getSource(), EntityArgumentType.getEntity(context, "npc"),
                                                        BlockPosArgumentType.getBlockPos(context, "to"), IntegerArgumentType.getInteger(context, "range"), IntegerArgumentType.getInteger(context, "budget")))))))));
    }

    private static int path(ServerCommandSource source, Entity entity, BlockPos to, int range, int budget) {
        if(!(entity instanceof NpcEntity npc)) {
            source.sendError(Text.literal("not an npc"));
            return 0;
        }
        npc.getNavigation().setRangeMultiplier(budget);
        Path path = npc.getNavigation().findPathTo(to, range);
        npc.getNavigation().resetRangeMultiplier();
        if(path == null) {
            source.sendFeedback(() -> Text.literal("no path at all"), false);
            return 0;
        }
        PathNode end = path.getEnd();
        source.sendFeedback(() -> Text.literal("path of %d nodes, reaches the target: %s, ends at %s".formatted(path.getLength(), path.reachesTarget(),
                end == null ? "?" : end.x + "," + end.y + "," + end.z)), false);
        StringBuilder nodes = new StringBuilder();
        for(int i = 0; i < path.getLength(); i++) {
            PathNode node = path.getNode(i);
            nodes.append(node.x).append(',').append(node.y).append(',').append(node.z).append(' ').append(node.type.name().toLowerCase()).append(i + 1 < path.getLength() ? " | " : "");
        }
        source.sendFeedback(() -> Text.literal(nodes.toString()), false);
        return path.reachesTarget() ? 1 : 0;
    }
}
