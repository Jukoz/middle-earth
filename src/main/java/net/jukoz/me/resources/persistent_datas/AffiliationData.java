package net.jukoz.me.resources.persistent_datas;

import net.jukoz.me.MiddleEarth;
import net.jukoz.me.resources.ModFactionRegistry;
import net.jukoz.me.resources.datas.Alignment;
import net.jukoz.me.resources.datas.faction.Faction;
import net.jukoz.me.world.dimension.ModDimensions;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import org.joml.Vector2i;

public class AffiliationData {
    public Alignment alignment;
    public Identifier faction;
    public Identifier spawnId;

    public AffiliationData(String alignment, String faction, String spawnId) {
        this.alignment = Alignment.valueOf(alignment);
        this.faction = Identifier.of(MiddleEarth.MOD_ID, faction);
        this.spawnId = Identifier.of(MiddleEarth.MOD_ID, spawnId);
    }


    public Alignment getAlignment(){
        return alignment;
    }

    public Vec3d getMiddleEarthSpawnCoordinate(){
        Faction foundFaction = ModFactionRegistry.findFactionById(faction);
        if(foundFaction == null) return null;
        Vector2i dynamicSpawnFound = foundFaction.getSpawnData().findDynamicSpawn(spawnId);
        // Return dynamic spawn with dimension height
        if(dynamicSpawnFound != null){
            double y = ModDimensions.getDimensionHeight(dynamicSpawnFound.x, dynamicSpawnFound.y).y;
            return new Vec3d(dynamicSpawnFound.x, y, dynamicSpawnFound.y);
        }
        // Return custom spawn
        return foundFaction.getSpawnData().findCustomSpawn(spawnId);
    }

    @Override
    public String toString() {
        return "AffiliationData{Alignment=" + getAlignment().toString() + "; Faction=" + faction + "; Spawn=" + spawnId + ";}";
    }
}
