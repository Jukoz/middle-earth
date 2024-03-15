package net.jukoz.me.world;

public class MiddleEarthMapRuntime {
    private static MiddleEarthMapRuntime single_instance = null;

    public static synchronized MiddleEarthMapRuntime getInstance()
    {
        if (single_instance == null)
            single_instance = new MiddleEarthMapRuntime();

        return single_instance;
    }
}
