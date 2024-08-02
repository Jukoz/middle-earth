package net.jukoz.me.resources.persistent_datas;

import net.jukoz.me.network.packets.AffiliationPacket;
import net.jukoz.me.resources.datas.Alignment;

public class AffiliationData {
    public int alignment;
    public int faction;
    public int subfaction;

    public AffiliationData(Alignment alignment, int faction, int subfaction) {
        this(alignment.ordinal(), faction, subfaction);
    }

    public AffiliationData(int alignment, int faction, int subfaction) {
        this.alignment = alignment;
        this.faction = faction;
        this.subfaction = subfaction;
    }

    /**
     * Will give the alignment in Enum format
     * @return the alignment in Enum format, with GOOD as default value.
     */
    public Alignment getAlignment(){
        if(alignment < Alignment.values().length)
            return Alignment.values()[alignment];
        return Alignment.GOOD; // Default value
    }

    @Override
    public String toString() {
        return "AffiliationData{Alignment= " + getAlignment().toString() + "; Faction= " + faction + "; Subfaction= " + subfaction + ";}";
    }
}
