package net.jukoz.me.resource;

import net.jukoz.me.resource.data.Alignment;
import net.jukoz.me.utils.LoggerUtil;

public class PlayerData {
    public int alignment = 0;
    public int faction = 0;
    public int subfaction = 0;

    /**
     * Will give the alignment in Enum format
     * @return the alignment in Enum format, with GOOD as default value.
     */
    public Alignment getAlignment(){
        if(alignment < Alignment.values().length)
            return Alignment.values()[alignment];
        return Alignment.GOOD; // Default value
    }
}
