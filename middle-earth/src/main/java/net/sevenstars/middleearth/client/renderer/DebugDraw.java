package net.sevenstars.middleearth.client.renderer;

import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.VertexRendering;
import net.minecraft.client.render.debug.DebugRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import org.joml.Vector3f;

// Draws the lines, boxes and labels for the debug views. Colours are 0xAARRGGBB
public class DebugDraw {
    private static final double BOX_MARGIN = 0.02;
    private static final double DASH_LENGTH = 0.4;

    public interface Drawing {
        void draw(MatrixStack matrices, VertexConsumer lines, Vec3d camera);
    }

    // Lines use world coordinates, so the matrices are translated to the camera first
    public static void lines(WorldRenderContext context, Drawing drawing) {
        MatrixStack matrices = context.matrixStack() == null ? new MatrixStack() : context.matrixStack();
        Vec3d camera = context.camera().getPos();
        matrices.push();
        matrices.translate(-camera.x, -camera.y, -camera.z);
        drawing.draw(matrices, context.consumers().getBuffer(RenderLayer.getLines()), camera);
        matrices.pop();
    }

    public static void line(MatrixStack matrices, VertexConsumer lines, Vec3d from, Vec3d to, int color) {
        VertexRendering.drawVector(matrices, lines, new Vector3f((float) from.x, (float) from.y, (float) from.z), to.subtract(from), color);
    }

    // Draws every other stretch of the line. Used for what was found automatically rather than set by hand
    public static void dashedLine(MatrixStack matrices, VertexConsumer lines, Vec3d from, Vec3d to, int color) {
        Vec3d way = to.subtract(from);
        double length = way.length();
        if(length < DASH_LENGTH) {
            line(matrices, lines, from, to, color);
            return;
        }
        Vec3d step = way.multiply(1 / length);
        for(double start = 0; start < length; start += DASH_LENGTH * 2)
            line(matrices, lines, from.add(step.multiply(start)), from.add(step.multiply(Math.min(start + DASH_LENGTH, length))), color);
    }

    public static void box(MatrixStack matrices, VertexConsumer lines, BlockPos pos, int color) {
        box(matrices, lines, new Box(pos).expand(BOX_MARGIN), color);
    }

    public static void box(MatrixStack matrices, VertexConsumer lines, Box box, int color) {
        VertexRendering.drawBox(matrices, lines, box, (color >> 16 & 255) / 255f, (color >> 8 & 255) / 255f, (color & 255) / 255f, 1f);
    }

    // Text in the world, drawn with matrices that are not translated to the camera. It draws through blocks so a wall
    // next to it doesn't cut it off
    public static void label(MatrixStack matrices, VertexConsumerProvider consumers, String text, Vec3d at, int color, float scale) {
        DebugRenderer.drawString(matrices, consumers, text, at.x, at.y, at.z, color, scale, true, 0f, true);
    }
}
