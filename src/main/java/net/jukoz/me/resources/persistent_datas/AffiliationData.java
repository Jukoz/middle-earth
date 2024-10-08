package net.jukoz.me.resources.persistent_datas;

import net.jukoz.me.MiddleEarth;
import net.jukoz.me.exceptions.FactionIdentifierException;
import net.jukoz.me.resources.datas.Alignment;
import net.jukoz.me.resources.datas.factions.Faction;
import net.jukoz.me.resources.datas.factions.FactionLookup;
import net.jukoz.me.resources.datas.factions.data.SpawnData;
import net.jukoz.me.utils.LoggerUtil;
import net.jukoz.me.world.chunkgen.map.MiddleEarthHeightMap;
import net.jukoz.me.world.dimension.ModDimensions;
import net.jukoz.me.world.map.MiddleEarthMapUtils;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.joml.Vector2d;
import org.joml.Vector2i;

public class AffiliationData {
    public Alignment alignment;
    public Identifier faction;
    public Identifier spawnId;

    public AffiliationData(String alignment, Identifier factionId, Identifier spawnId) {
        this.alignment = Alignment.valueOf(alignment);
        this.faction = factionId;
        this.spawnId = spawnId;
    }


    public Alignment getAlignment(){
        return alignment;
    }

    public Vec3d getSpawnMiddleEarthCoordinate(World world){
        try{
            Faction foundFaction = FactionLookup.getFactionById(world,faction);
            SpawnData spawnData = foundFaction.getSpawnData().findSpawn(spawnId);
            BlockPos blockpos = spawnData.getBlockPos();
            if(!spawnData.isDynamic()){ // Return custom spawn coords
                return blockpos.toCenterPos();
            }
            int height = ModDimensions.getDimensionHeight(blockpos.getX(), blockpos.getZ()).y;
            blockpos = new BlockPos(blockpos.getX(), height, blockpos.getZ());
            return blockpos.toCenterPos();
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
