package net.jukoz.me.resources.persistent_datas;

import net.jukoz.me.exceptions.FactionIdentifierException;
import net.jukoz.me.resources.datas.factions.FactionLookup;
import net.jukoz.me.resources.datas.races.Race;
import net.jukoz.me.resources.datas.races.RaceLookup;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

public class PlayerData {
    private AffiliationData affiliationData;
    private Identifier race;
    private BlockPos overworldSpawnCoordinates;

    public PlayerData(){
        this.affiliationData = null;
    }
    public void setRace(Identifier raceId){
        this.race = raceId;
    }
    public Identifier getRace(){
        return this.race;
    }

    public void setAffiliationData(AffiliationData affiliationData){
        this.affiliationData = affiliationData;
    }

    public boolean hasAffilition(){
        return affiliationData != null;
    }
    public AffiliationData getAffiliationData(){
        return affiliationData;
    }

    @Override
    public String toString() {
        String text = "";
        if(hasAffilition())
            text += affiliationData+"\n";
        if(race != null)
            text += "Race="+race+"\n";
        if(overworldSpawnCoordinates != null)
            text += "Overworld="+overworldSpawnCoordinates+"\n";

        if(!text.equals(""))
            return text;
        else
            return "No Data";
    }

    public void setOverworldSpawn(BlockPos overworldSpawnCoordinate) {
        this.overworldSpawnCoordinates = overworldSpawnCoordinate;
    }
    public BlockPos getOverworldSpawnCoordinates() {
        return overworldSpawnCoordinates;
    }

    public void clearData() {
        this.affiliationData = null;
        this.overworldSpawnCoordinates = null;
    }

    public boolean setSpawnMiddleEarthId(Identifier foundId) throws FactionIdentifierException {
        if(hasAffilition()){
            if(FactionLookup.findFactionById(affiliationData.faction).getSpawnData().getSpawnList().containsKey(foundId)){
                affiliationData.spawnId = foundId;
                return true;
            }
        }
        return false;
    }
}
