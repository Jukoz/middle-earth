package net.jukoz.me.resources.persistent_datas;

import net.minecraft.util.math.BlockPos;
import org.joml.Vector3i;

public class PlayerData {
    private AffiliationData affiliationData;
    private BlockPos overworldSpawnCoordinates;
    private BlockPos middleEarthSpawnCoordinates;

    public PlayerData(){
        this.affiliationData = null;
    }
    public PlayerData(AffiliationData affiliationData){
        this.affiliationData = affiliationData;
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
        if(hasAffilition())
            return affiliationData + "_Overworld=" + overworldSpawnCoordinates + ";_MiddleEarth=" + middleEarthSpawnCoordinates;
        else
            return "No Data";
    }

    public void setOverworldSpawn(BlockPos overworldSpawnCoordinate) {
        this.overworldSpawnCoordinates = overworldSpawnCoordinate;
    }
    public BlockPos getOverworldSpawnCoordinates() {
        return overworldSpawnCoordinates;
    }

    public void setMiddleEarthSpawn(BlockPos middleEarthSpawnCoordinates) {
        this.middleEarthSpawnCoordinates = middleEarthSpawnCoordinates;
    }
    public BlockPos getMiddleEarthSpawnCoordinates() {
        return middleEarthSpawnCoordinates;
    }
}
