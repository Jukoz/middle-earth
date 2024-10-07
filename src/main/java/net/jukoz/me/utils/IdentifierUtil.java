package net.jukoz.me.utils;

import net.jukoz.me.MiddleEarth;
import net.minecraft.util.Identifier;

public class IdentifierUtil {
    public static Identifier getIdentifierFromString(String id){
        if(id.contains(":") && id.split(":").length == 2){
            return Identifier.of(id.split(":")[0], id.split(":")[1]);
        } else {
            return Identifier.of(MiddleEarth.MOD_ID, id);
        }
    }
}
