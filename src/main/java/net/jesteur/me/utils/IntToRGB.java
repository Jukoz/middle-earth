package net.jesteur.me.utils;

import net.minecraft.util.math.Vec3d;

import java.awt.*;

public class IntToRGB {
    public static Color ex(int color){
        int red = (color >> 16) & 0xFF;
        int green = (color >> 8) & 0xFF;
        int blue = color & 0xFF;

        Color rgbColor = new Color(red / 255.0f, green / 255.0f, blue / 255.0f);

        return rgbColor;
    }
}
