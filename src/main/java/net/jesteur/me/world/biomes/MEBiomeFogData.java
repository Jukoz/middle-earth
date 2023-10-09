package net.jesteur.me.world.biomes;

import net.minecraft.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import org.joml.Vector4f;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class MEBiomeFogData {
    public static Map<RegistryKey<Biome>, MEBiomeFogData> DATA;
    static {
        DATA = new HashMap<>();
        DATA.put(MEBiomeKeys.MISTY_MOUNTAINS, new MEBiomeFogData(-50f, 52f,new Color(0xa6adad)));
        DATA.put(MEBiomeKeys.DUNLAND_FOOTHILLS, new MEBiomeFogData(-25f, 96f, new Color(0xc2c4c4)));
        DATA.put(MEBiomeKeys.MIRKWOOD, new MEBiomeFogData(25f, 200f, new Color(0x49524d)));
        DATA.put(MEBiomeKeys.NORTHERN_DUNLAND, new MEBiomeFogData(10f, 200f, new Color(0x2e332e)));
    };

    public float fogStart;
    public float fogEnd;

    public Vector4f rgba;

    public MEBiomeFogData(float fogStart, float fogEnd, Color color) {
        this.fogStart = fogStart;
        this.fogEnd = fogEnd;
        this.rgba = getRGBA(color);
    }

    private Vector4f getRGBA(Color color){
        float r = (float)color.getRed() / 255f;
        float g = (float)color.getGreen() / 255f;
        float b = (float)color.getBlue() / 255f;
        float a = 0;
        return new Vector4f(r,g,b,a);
    }

}
