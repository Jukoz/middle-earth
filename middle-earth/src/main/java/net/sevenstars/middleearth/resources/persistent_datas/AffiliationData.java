package net.sevenstars.middleearth.resources.persistent_datas;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.exceptions.FactionIdentifierException;
import net.sevenstars.middleearth.resources.datas.common.DispositionType;
import net.sevenstars.middleearth.resources.datas.factions.Faction;
import net.sevenstars.middleearth.resources.datas.factions.FactionLookup;
import net.sevenstars.middleearth.resources.datas.factions.data.SpawnData;

public class AffiliationData {
    public DispositionType dispositionType;
    public ResourceLocation faction;
    public ResourceLocation spawnId;

    public AffiliationData(String disposition, ResourceLocation factionId, ResourceLocation spawnId) {
        this.dispositionType = DispositionType.valueOf(disposition);
        this.faction = factionId;
        this.spawnId = spawnId;
    }


    public DispositionType getDisposition(){
        return dispositionType;
    }

    public Vec3 getSpawnMiddleEarthCoordinate(Level world){
        try{
            Faction foundFaction = FactionLookup.getFactionById(world,faction);
            SpawnData spawnData = foundFaction.getSpawnData().findSpawn(spawnId);
            BlockPos blockpos = spawnData.getBlockPos();
            return blockpos.getCenter();
        } catch (FactionIdentifierException e){
            MiddleEarth.LOGGER.logError("AffiliationData::getSpawnMiddleEarthCoordinate - PlayerFactionPayload couldn't be found <%s>".formatted(faction));
            return null;
        }
    }

    @Override
    public String toString() {
        return "Disposition=" + getDisposition().toString() + ";\nPlayerFactionPayload=" + faction + ";\nSpawn=" + spawnId + ";";
    }
}
