package net.jesteur.me.utils;

import net.minecraft.util.math.Vec3d;

public class IntToRGB {
    public static Vec3d ex(int color){

        // Extract red, green, and blue components
        int red = (color >> 16) & 0xFF;
        int green = (color >> 8) & 0xFF;
        int blue = color & 0xFF;

        // Convert to Vec3d if needed (usually for shaders)
        double redDouble = (double) red / 255.0;
        double greenDouble = (double) green / 255.0;
        double blueDouble = (double) blue / 255.0;

        Vec3d rgbVec3d = new Vec3d(redDouble, greenDouble, blueDouble);
        return rgbVec3d;
    }
}
