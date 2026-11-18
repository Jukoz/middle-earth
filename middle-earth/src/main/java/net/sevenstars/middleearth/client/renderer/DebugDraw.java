package net.sevenstars.middleearth.client.renderer;

import net.minecraft.client.render.DrawStyle;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.debug.gizmo.GizmoDrawing;
import net.minecraft.world.debug.gizmo.TextGizmo;

// Colours are 0xAARRGGBB. Call it from a world render event
public class DebugDraw {
    private static final double BOX_MARGIN = 0.02;
    private static final double DASH_LENGTH = 0.4;
    private static final float LINE_WIDTH = 2.5f;
    private static final float TEXT_SCALE = 16f;

    public static void line(Vec3d from, Vec3d to, int color) {
        GizmoDrawing.line(from, to, color, LINE_WIDTH);
    }

    public static void dashedLine(Vec3d from, Vec3d to, int color) {
        Vec3d way = to.subtract(from);
        double length = way.length();
        if(length < DASH_LENGTH) {
            line(from, to, color);
            return;
        }
        Vec3d step = way.multiply(1 / length);
        for(double start = 0; start < length; start += DASH_LENGTH * 2)
            line(from.add(step.multiply(start)), from.add(step.multiply(Math.min(start + DASH_LENGTH, length))), color);
    }

    public static void box(BlockPos pos, int color) {
        box(new Box(pos).expand(BOX_MARGIN), color);
    }

    public static void box(Box box, int color) {
        GizmoDrawing.box(box, DrawStyle.stroked(color, LINE_WIDTH));
    }

    // Draws through blocks so walls don't cut it off
    public static void label(String text, Vec3d at, int color, float scale) {
        GizmoDrawing.text(text, at, TextGizmo.Style.centered(color).scaled(scale * TEXT_SCALE)).ignoreOcclusion();
    }
}
