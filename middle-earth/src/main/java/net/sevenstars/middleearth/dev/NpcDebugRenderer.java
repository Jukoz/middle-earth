package net.sevenstars.middleearth.dev;

import net.fabricmc.fabric.api.client.rendering.v1.world.WorldRenderEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.entity.ai.pathing.PathNode;
import net.minecraft.server.integrated.IntegratedServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.sevenstars.middleearth.client.renderer.DebugDraw;
import net.sevenstars.middleearth.entity.npcs.NpcEntity;
import net.sevenstars.middleearth.entity.npcs.routine.NpcRoutine;
import net.sevenstars.middleearth.resources.datas.npc_classes.data.RoutineData;

import java.util.List;

public class NpcDebugRenderer {
    private static final double RANGE = 80;
    private static final int PATH = 0xFFFFFFFF;
    private static final int FISH = 0xFF3FA9F5;
    private static final int HUNT = 0xFFE53935;
    private static final int FORAGE = 0xFF66BB6A;
    private static final int PATROL = 0xFFFFA726;
    private static final int MEET = 0xFFEC407A;
    private static final int HOME = 0xFF7E57C2;
    private static final int LOOK = 0xFF26C6DA;
    private static final int LEADER = 0xFFD4E157;
    private static final int RING_CURRENT = 0xFFFFFFFF;
    private static final double FEET_OFFSET = 0.15;
    private static final double CHEST_HEIGHT = 0.6;
    private static final double COMPANION_HEIGHT = 0.8;
    private static final double DIRECTION_LENGTH = 24;
    private static final double END_BOX_SIZE = 0.4;
    private static final double RING_HEIGHT = 0.3;
    private static final double PREY_HEIGHT = 0.5;

    public static void register() {
        WorldRenderEvents.END_EXTRACTION.register(context -> {
            try {
                float tickProgress = context.tickCounter().getTickProgress(false);
                render(context.camera().getCameraPos(), tickProgress);
            } catch (RuntimeException ignored) {
                // Server world read from the render thread; a torn read only glitches one frame
            }
        });
    }

    private static void render(Vec3d camera, float tickProgress) {
        MinecraftClient client = MinecraftClient.getInstance();
        IntegratedServer server = client.getServer();
        if(server == null || client.world == null)
            return;
        ServerWorld world = server.getWorld(client.world.getRegistryKey());
        if(world == null)
            return;
        for(Entity seen : client.world.getEntities()) {
            if(!(seen instanceof NpcEntity) || seen.squaredDistanceTo(camera) > RANGE * RANGE)
                continue;
            if(!(world.getEntity(seen.getUuid()) instanceof NpcEntity npc))
                continue;
            Vec3d feet = seen.getLerpedPos(tickProgress).add(0, FEET_OFFSET, 0);
            Vec3d chest = feet.add(0, seen.getHeight() * CHEST_HEIGHT, 0);
            drawPath(npc, feet);
            NpcRoutine routine = npc.getDayRoutine();
            if(routine.debugActive()) {
                int color = switch (routine.debugActivity()) {
                    case FISH -> FISH;
                    case HUNT -> HUNT;
                    case FORAGE, WORK, FARM, CHOP, MINE -> FORAGE;
                    case PATROL, POST, ESCORT, INSPECT, FOLLOW -> PATROL;
                    case VISIT, TABLE, COURT -> MEET;
                };
                if(routine.debugActivity() == RoutineData.Activity.PATROL)
                    drawRing(routine.debugRing(), routine.debugRingIndex());
                if(routine.debugReturning()) {
                    BlockPos home = routine.debugToBed() && npc.getAssignedBed() != null ? npc.getAssignedBed()
                            : routine.debugCarried() != null && npc.getStore() != null ? npc.getStore() : npc.getRoutineHome();
                    if(home != null) {
                        DebugDraw.line(chest, Vec3d.ofCenter(home), HOME);
                        DebugDraw.box(home, HOME);
                    }
                } else {
                    DebugDraw.line(chest, Vec3d.ofCenter(routine.debugSpot()), color);
                    DebugDraw.box(routine.debugSpot(), color);
                }
                if(routine.debugLookTarget() != null)
                    DebugDraw.line(seen.getLerpedPos(tickProgress).add(0, seen.getStandingEyeHeight(), 0), routine.debugLookTarget(), LOOK);
                if(routine.debugCompanion() != null)
                    DebugDraw.line(chest, routine.debugCompanion().getEntityPos().add(0, COMPANION_HEIGHT, 0), MEET);
            }
            LivingEntity prey = npc.getHuntTarget() != null ? npc.getHuntTarget() : npc.getTarget();
            if(prey != null)
                DebugDraw.line(chest, prey.getEntityPos().add(0, prey.getHeight() * PREY_HEIGHT, 0), HUNT);
            if(npc.getPartyLeader() != null && world.getEntity(npc.getPartyLeader()) instanceof LivingEntity leader)
                DebugDraw.line(chest, leader.getEntityPos().add(0, leader.getHeight() * CHEST_HEIGHT, 0), LEADER);
            if(npc.getTravelDestination() != null) {
                Vec3d toward = Vec3d.ofCenter(npc.getTravelDestination()).subtract(chest);
                DebugDraw.line(chest, chest.add(toward.length() > DIRECTION_LENGTH ? toward.normalize().multiply(DIRECTION_LENGTH) : toward), LEADER);
            }
            if(npc.isSleeping() && npc.getAssignedBed() != null)
                DebugDraw.box(npc.getAssignedBed(), HOME);
        }
    }

    private static void drawPath(NpcEntity npc, Vec3d feet) {
        Path path = npc.getNavigation().getCurrentPath();
        if(path == null || path.isFinished())
            return;
        Vec3d from = feet;
        for(int i = path.getCurrentNodeIndex(); i < path.getLength(); i++) {
            PathNode node = path.getNode(i);
            Vec3d to = new Vec3d(node.x + 0.5, node.y + FEET_OFFSET, node.z + 0.5);
            DebugDraw.line(from, to, PATH);
            from = to;
        }
        Box end = Box.of(from, END_BOX_SIZE, END_BOX_SIZE, END_BOX_SIZE);
        DebugDraw.box(end, PATH);
    }

    private static void drawRing(List<BlockPos> ring, int current) {
        for(int i = 0; i < ring.size(); i++) {
            Vec3d from = Vec3d.ofBottomCenter(ring.get(i)).add(0, RING_HEIGHT, 0);
            Vec3d to = Vec3d.ofBottomCenter(ring.get((i + 1) % ring.size())).add(0, RING_HEIGHT, 0);
            DebugDraw.line(from, to, PATROL);
            if(i == current)
                DebugDraw.box(ring.get(i), RING_CURRENT);
        }
    }
}
