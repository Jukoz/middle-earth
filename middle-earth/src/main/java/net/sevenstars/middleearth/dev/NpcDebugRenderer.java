package net.sevenstars.middleearth.dev;

import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexRendering;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.entity.ai.pathing.PathNode;
import net.minecraft.server.integrated.IntegratedServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.sevenstars.middleearth.entity.goals.NpcRoutineGoal;
import net.sevenstars.middleearth.entity.npcs.NpcEntity;
import org.joml.Vector3f;

import java.util.List;

// Draws what the npcs around the camera are after: path, routine spot, patrol ring, prey, bed, leader
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

    public static void register() {
        NpcFailureRecorder.registerCommand();
        WorldRenderEvents.AFTER_ENTITIES.register(context -> {
            try {
                render(context.matrixStack() == null ? new MatrixStack() : context.matrixStack(),
                        context.consumers().getBuffer(RenderLayer.getLines()), context.camera().getPos(),
                        context.tickCounter().getTickProgress(false));
            } catch (RuntimeException ignored) {
                // The server world is read from the render thread, a torn read only costs one frame
            }
        });
    }

    private static void render(MatrixStack matrices, VertexConsumer lines, Vec3d camera, float tickProgress) {
        MinecraftClient client = MinecraftClient.getInstance();
        IntegratedServer server = client.getServer();
        if(server == null || client.world == null)
            return;
        ServerWorld world = server.getWorld(client.world.getRegistryKey());
        if(world == null)
            return;
        matrices.push();
        matrices.translate(-camera.x, -camera.y, -camera.z);
        for(Entity seen : client.world.getEntities()) {
            if(!(seen instanceof NpcEntity) || seen.squaredDistanceTo(camera) > RANGE * RANGE)
                continue;
            if(!(world.getEntity(seen.getUuid()) instanceof NpcEntity npc))
                continue;
            Vec3d feet = seen.getLerpedPos(tickProgress).add(0, 0.15, 0);
            Vec3d chest = feet.add(0, seen.getHeight() * 0.6, 0);
            drawPath(matrices, lines, npc, feet);
            NpcRoutineGoal routine = npc.routineGoal;
            if(routine != null && routine.debugActive()) {
                int color = switch (routine.debugActivity()) {
                    case FISH -> FISH; case HUNT -> HUNT; case FORAGE -> FORAGE; case PATROL -> PATROL; case VISIT, TABLE -> MEET; case WORK, FARM, CHOP, MINE -> FORAGE; case POST, ESCORT, INSPECT, FOLLOW -> PATROL; case COURT -> MEET;
                };
                if(routine.debugActivity() == net.sevenstars.middleearth.resources.datas.npc_types.data.RoutineData.Activity.PATROL)
                    drawRing(matrices, lines, routine.debugRing(), routine.debugRingIndex());
                if(routine.debugReturning()) {
                    BlockPos home = routine.debugToBed() && npc.getAssignedBed() != null ? npc.getAssignedBed()
                            : routine.debugCarried() != null && npc.getStore() != null ? npc.getStore() : npc.getRoutineHome();
                    if(home != null) {
                        line(matrices, lines, chest, Vec3d.ofCenter(home), HOME);
                        box(matrices, lines, home, HOME);
                    }
                } else {
                    line(matrices, lines, chest, Vec3d.ofCenter(routine.debugSpot()), color);
                    box(matrices, lines, routine.debugSpot(), color);
                }
                if(routine.debugLookTarget() != null)
                    line(matrices, lines, seen.getLerpedPos(tickProgress).add(0, seen.getStandingEyeHeight(), 0), routine.debugLookTarget(), LOOK);
                if(routine.debugCompanion() != null)
                    line(matrices, lines, chest, routine.debugCompanion().getPos().add(0, 0.8, 0), MEET);
            }
            LivingEntity prey = npc.getHuntTarget() != null ? npc.getHuntTarget() : npc.getTarget();
            if(prey != null)
                line(matrices, lines, chest, prey.getPos().add(0, prey.getHeight() * 0.5, 0), HUNT);
            if(npc.getPartyLeader() != null && world.getEntity(npc.getPartyLeader()) instanceof LivingEntity leader)
                line(matrices, lines, chest, leader.getPos().add(0, leader.getHeight() * 0.6, 0), LEADER);
            if(npc.getTravelDestination() != null) {
                Vec3d toward = Vec3d.ofCenter(npc.getTravelDestination()).subtract(chest);
                line(matrices, lines, chest, chest.add(toward.length() > 24 ? toward.normalize().multiply(24) : toward), LEADER);
            }
            if(npc.isSleeping() && npc.getAssignedBed() != null)
                box(matrices, lines, npc.getAssignedBed(), HOME);
        }
        matrices.pop();
    }

    private static void drawPath(MatrixStack matrices, VertexConsumer lines, NpcEntity npc, Vec3d feet) {
        Path path = npc.getNavigation().getCurrentPath();
        if(path == null || path.isFinished())
            return;
        Vec3d from = feet;
        for(int i = path.getCurrentNodeIndex(); i < path.getLength(); i++) {
            PathNode node = path.getNode(i);
            Vec3d to = new Vec3d(node.x + 0.5, node.y + 0.15, node.z + 0.5);
            line(matrices, lines, from, to, PATH);
            from = to;
        }
        Box end = Box.of(from, 0.4, 0.4, 0.4);
        VertexRendering.drawBox(matrices, lines, end, 1f, 1f, 1f, 1f);
    }

    private static void drawRing(MatrixStack matrices, VertexConsumer lines, List<BlockPos> ring, int current) {
        for(int i = 0; i < ring.size(); i++) {
            Vec3d from = Vec3d.ofBottomCenter(ring.get(i)).add(0, 0.3, 0);
            Vec3d to = Vec3d.ofBottomCenter(ring.get((i + 1) % ring.size())).add(0, 0.3, 0);
            line(matrices, lines, from, to, PATROL);
            if(i == current)
                box(matrices, lines, ring.get(i), 0xFFFFFFFF);
        }
    }

    private static void line(MatrixStack matrices, VertexConsumer lines, Vec3d from, Vec3d to, int color) {
        VertexRendering.drawVector(matrices, lines, new Vector3f((float) from.x, (float) from.y, (float) from.z), to.subtract(from), color);
    }

    private static void box(MatrixStack matrices, VertexConsumer lines, BlockPos pos, int color) {
        VertexRendering.drawBox(matrices, lines, new Box(pos).expand(0.02),
                (color >> 16 & 255) / 255f, (color >> 8 & 255) / 255f, (color & 255) / 255f, 1f);
    }
}
