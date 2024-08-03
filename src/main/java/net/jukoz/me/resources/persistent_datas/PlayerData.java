package net.jukoz.me.resources.persistent_datas;

import net.jukoz.me.resources.datas.Alignment;
import net.minecraft.util.math.BlockPos;

public class PlayerData {
    private AffiliationData affiliationData;
    private BlockPos overworldSpawnBlockpos;
    private BlockPos middleEarthSpawnBlockpos;

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
            return affiliationData.toString() + "_Overworld=" + overworldSpawnBlockpos.toString() + ";_MiddleEarth=" +middleEarthSpawnBlockpos.toString();
        else
            return "No Data";
    }

    public void setOverworldSpawn(BlockPos overworldSpawnBlockpos) {
        this.overworldSpawnBlockpos = overworldSpawnBlockpos;
    }
    public BlockPos getOverworldSpawnBlockpos() { return overworldSpawnBlockpos; }

    public void setMiddleEarthSpawn(BlockPos middleEarthSpawnBlockpos) {
        this.middleEarthSpawnBlockpos = middleEarthSpawnBlockpos;
    }
    public BlockPos getMiddleEarthSpawnBlockpos() { return middleEarthSpawnBlockpos; }

}
