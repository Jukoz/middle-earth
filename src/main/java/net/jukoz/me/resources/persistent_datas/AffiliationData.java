package net.jukoz.me.resources.persistent_datas;

import net.jukoz.me.MiddleEarth;
import net.jukoz.me.resources.datas.Alignment;
import net.minecraft.util.Identifier;

public class AffiliationData {
    public Alignment alignment;
    public Identifier faction;
    public Identifier subfaction;
    public Identifier spawnId;

    public AffiliationData(String alignment, String faction, String subfaction, String spawnId) {
        this.alignment = Alignment.valueOf(alignment);
        this.faction = Identifier.of(MiddleEarth.MOD_ID, faction);
        this.subfaction = Identifier.of(MiddleEarth.MOD_ID, subfaction);
        this.spawnId = Identifier.of(MiddleEarth.MOD_ID, spawnId);
    }


    public Alignment getAlignment(){
        return alignment;
    }

    @Override
    public String toString() {
        return "AffiliationData{Alignment= " + getAlignment().toString() + "; Faction= " + faction + "; Subfaction= " + subfaction + "; Spawn= " + spawnId + ";}";
    }
}
