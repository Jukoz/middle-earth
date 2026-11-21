package net.sevenstars.middleearth.features.generation.caves;

import net.minecraft.world.chunk.Chunk;
import net.sevenstars.middleearth.features.generation.map.MiddleEarthMapRuntime;

// the caves just under the Misty Mountains: domed halls joined by smooth tunnels (see GalleryLayout);
// one of these holds the halls and tunnels that reach into a chunk
public final class Galleries {
    static final float WOBBLE = 0.14f;
    static final float WOBBLE_SCALE = 7;
    // the simplex noise stays within about plus or minus one; a margin on top for the shortcuts that skip it
    static final float NOISE_BOUND = 1.25f;
    static final float WOBBLE_BOUND = WOBBLE * NOISE_BOUND;

    public static final Galleries EMPTY = new Galleries(new Hall[0], new Tunnel[0]);

    private final Hall[] halls;
    private final Tunnel[] tunnels;
    private final int minY;
    private final int maxY;

    Galleries(Hall[] halls, Tunnel[] tunnels) {
        this.halls = halls;
        this.tunnels = tunnels;
        int lo = Integer.MAX_VALUE;
        int hi = Integer.MIN_VALUE;
        for (Hall hall : halls) {
            lo = Math.min(lo, hall.box.minY());
            hi = Math.max(hi, hall.box.maxY());
        }
        for (Tunnel tunnel : tunnels) {
            lo = Math.min(lo, tunnel.box.minY());
            hi = Math.max(hi, tunnel.box.maxY());
        }
        this.minY = lo;
        this.maxY = hi;
    }

    public static Galleries near(MistyCaves misty, MiddleEarthMapRuntime map, int startX, int startZ) {
        return GalleryLayout.of(misty, map).near(startX, startZ, false);
    }

    public static int layerFloor(MistyCaves misty, int x, int z) {
        return GalleryLayout.of(misty, MiddleEarthMapRuntime.getInstance()).layerFloor(x, z);
    }

    public static int zoneBottom(MistyCaves misty, int x, int z, int surface) {
        return GalleryLayout.of(misty, MiddleEarthMapRuntime.getInstance()).zoneBottom(x, z, surface);
    }

    public boolean isEmpty() {
        return halls.length == 0 && tunnels.length == 0;
    }

    public boolean carves(int x, int y, int z) {
        if (y < minY || y > maxY) return false;
        for (Hall hall : halls) {
            if (hall.carves(x, y, z)) return true;
        }
        for (Tunnel tunnel : tunnels) {
            if (tunnel.carves(x, y, z)) return true;
        }
        return false;
    }

    public boolean water(int x, int y, int z) {
        if (y < minY || y > maxY) return false;
        for (Hall hall : halls) {
            if (hall.water(x, y, z)) return true;
        }
        return false;
    }

    public void dress(Chunk chunk, BlockBuffer blocks) {
        int startX = chunk.getPos().getStartX();
        int startZ = chunk.getPos().getStartZ();
        for (Hall hall : halls) hall.dress(blocks, startX, startZ);
        for (Tunnel tunnel : tunnels) tunnel.dress(blocks, startX, startZ);
    }
}
