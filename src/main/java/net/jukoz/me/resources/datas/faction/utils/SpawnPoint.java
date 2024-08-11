package net.jukoz.me.resources.datas.faction.utils;

import net.jukoz.me.utils.Factions;
import org.joml.Vector3i;

public class SpawnPoint {
    // TODO : implement
    public final Factions FACTION;
    public final Vector3i WORLD_SPAWN;
    public final Vector3i MAP_COORDINATE;

    /**
     * Spawn point constructor
     * @param factions Linked faction
     * @param worldCoordinate World coordinate, where the player will spawn physically and for the map display
     */
    public SpawnPoint(Factions factions, Vector3i worldCoordinate){
        this(factions, worldCoordinate, worldCoordinate);
    }

    /**
     * Spawn point constructor
     * @param factions Linked faction
     * @param worldCoordinate World coordinate, where the player will spawn physically
     * @param mapWorldCoordinate World coordinate for the map display
     */
    public SpawnPoint(Factions factions, Vector3i worldCoordinate, Vector3i mapWorldCoordinate){
        this.FACTION = factions;
        this.WORLD_SPAWN = worldCoordinate;
        this.MAP_COORDINATE = mapWorldCoordinate;
    }
}
