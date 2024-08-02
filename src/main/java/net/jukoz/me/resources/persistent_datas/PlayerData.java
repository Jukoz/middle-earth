package net.jukoz.me.resources.persistent_datas;

import net.jukoz.me.resources.datas.Alignment;

public class PlayerData {
    private AffiliationData affiliationData;

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
            return affiliationData.toString();
        else
            return "No Data";
    }
}
