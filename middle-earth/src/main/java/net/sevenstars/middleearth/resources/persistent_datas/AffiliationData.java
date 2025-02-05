package net.sevenstars.middleearth.resources.persistent_datas;

import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.exceptions.FactionIdentifierException;
import net.sevenstars.middleearth.resources.datas.Disposition;
import net.sevenstars.middleearth.resources.datas.factions.Faction;
import net.sevenstars.middleearth.resources.datas.factions.FactionLookup;
import net.sevenstars.middleearth.resources.datas.factions.data.SpawnData;
import net.sevenstars.middleearth.world.dimension.ModDimensions;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class AffiliationData {
    public Disposition disposition;
    public Identifier faction;
    public Identifier spawnId;

    public AffiliationData(String disposition, Identifier factionId, Identifier spawnId) {
        this.disposition = Disposition.valueOf(disposition);
        this.faction = factionId;
        this.spawnId = spawnId;
    }


    public Disposition getDisposition(){
        return disposition;
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
            MiddleEarth.LOGGER.logError("AffiliationData::getSpawnMiddleEarthCoordinate - Faction couldn't be found <%s>".formatted(faction));
            return null;
        }
    }

    @Override
    public String toString() {
        return "Disposition=" + getDisposition().toString() + ";\nFaction=" + faction + ";\nSpawn=" + spawnId + ";";
    }
}
