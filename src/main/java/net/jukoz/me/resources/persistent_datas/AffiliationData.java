package net.jukoz.me.resources.persistent_datas;

import net.jukoz.me.MiddleEarth;
import net.jukoz.me.exceptions.FactionIdentifierException;
import net.jukoz.me.resources.datas.Alignment;
import net.jukoz.me.resources.datas.factions.Faction;
import net.jukoz.me.resources.datas.factions.FactionLookup;
import net.jukoz.me.utils.LoggerUtil;
import net.jukoz.me.world.dimension.ModDimensions;
import net.jukoz.me.world.map.MiddleEarthMapUtils;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.joml.Vector2d;
import org.joml.Vector2i;

public class AffiliationData {
    public Alignment alignment;
    public Identifier faction;
    public Identifier spawnId;

    public AffiliationData(String alignment, String factionIdPath, String spawnIdPath) {
        this.alignment = Alignment.valueOf(alignment);
        this.faction = Identifier.of(MiddleEarth.MOD_ID, factionIdPath);
        this.spawnId = Identifier.of(MiddleEarth.MOD_ID, spawnIdPath);
    }


    public Alignment getAlignment(){
        return alignment;
    }

    public Vec3d getSpawnMiddleEarthCoordinate(World world){
        try{
            Faction foundFaction = FactionLookup.getFactionById(world,faction);
            LoggerUtil.logDebugMsg("MiddleEarth spawn coordinate : " + spawnId.toString());
            Vector2d dynamicSpawnFound = foundFaction.getSpawnData().findDynamicSpawn(spawnId);
            // Return dynamic spawn with dimension height
            if(dynamicSpawnFound != null){
                dynamicSpawnFound = MiddleEarthMapUtils.getInstance().getWorldCoordinateFromInitialMap(dynamicSpawnFound.x, dynamicSpawnFound.y);
                double y = ModDimensions.getDimensionHeight((int) dynamicSpawnFound.x, (int)dynamicSpawnFound.y).y;
                return new Vec3d(dynamicSpawnFound.x, y, dynamicSpawnFound.y);
            }
            // Return custom spawn
            return foundFaction.getSpawnData().findCustomSpawn(spawnId);
        } catch (FactionIdentifierException e){
            LoggerUtil.logError("AffiliationData::getSpawnMiddleEarthCoordinate", e);
            return null;
        }
    }

    @Override
    public String toString() {
        return "Alignment=" + getAlignment().toString() + ";\nFaction=" + faction + ";\nSpawn=" + spawnId + ";";
    }
}
