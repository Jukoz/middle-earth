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
        DATA.put(MEBiomeKeys.ERIADOR, new MEBiomeFogData(40f, 200f));
        DATA.put(MEBiomeKeys.BARROW_DOWNS, new MEBiomeFogData(15f, 80f));
        DATA.put(MEBiomeKeys.DARK_MIRKWOOD, new MEBiomeFogData(15f, 200f));
        DATA.put(MEBiomeKeys.DARK_MIRKWOOD_EDGE, new MEBiomeFogData(40f, 200f));
        DATA.put(MEBiomeKeys.DOL_GULDUR, new MEBiomeFogData(15f, 200f));
        DATA.put(MEBiomeKeys.DUNLAND_FOOTHILLS, new MEBiomeFogData(-25f, 96f));
        DATA.put(MEBiomeKeys.FANGORN, new MEBiomeFogData(15f, 170F));
        DATA.put(MEBiomeKeys.FORODWAITH, new MEBiomeFogData(10f, 148F));
        DATA.put(MEBiomeKeys.MIRKWOOD, new MEBiomeFogData(15f, 200f));
        DATA.put(MEBiomeKeys.MIRKWOOD_EDGE, new MEBiomeFogData(40f, 200f));
        DATA.put(MEBiomeKeys.MIRKWOOD_FOOTHILLS, new MEBiomeFogData(-5f, 125f));
        DATA.put(MEBiomeKeys.MISTY_MOUNTAINS, new MEBiomeFogData(-50f, 52f));
        DATA.put(MEBiomeKeys.NORTHERN_DUNLAND, new MEBiomeFogData(20f, 200f));
        DATA.put(MEBiomeKeys.THE_OLD_FOREST, new MEBiomeFogData(25f, 200F));
    };

    public float fogStart;
    public float fogEnd;

    public MEBiomeFogData(float fogStart, float fogEnd) {
        this.fogStart = fogStart;
        this.fogEnd = fogEnd;
    }

    private Vector4f getRGBA(Color color){
        float r = (float)color.getRed() / 255f;
        float g = (float)color.getGreen() / 255f;
        float b = (float)color.getBlue() / 255f;
        float a = 0;
        return new Vector4f(r,g,b,a);
    }

}
