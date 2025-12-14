package net.sevenstars.middleearth.utils;

import net.sevenstars.middleearth.MiddleEarth;
import net.minecraft.util.Identifier;

public class IdentifierUtil {
    public static Identifier getIdentifierFromString(String id){
        if(id.contains(":") && id.split(":").length == 2){
            return Identifier.of(id.split(":")[0], id.split(":")[1]);
        } else {
            return Identifier.of(MiddleEarth.MOD_ID, id);
        }
    }

    public static Identifier build(String name) {
        return Identifier.of(MiddleEarth.MOD_ID, name);
    }

    public static Identifier buildAggregate(String... names) {
        return build(createAggregateValue(names));
    }

    public static String createAggregateValue(String... names){
        if(names.length == 0)
            return "not_enough_parameters";
        if(names.length == 1)
            return names[0];

        StringBuilder fullValue = new StringBuilder();
        for(int i = 0; i < names.length; i++){
            fullValue.append(names[i]);
            if(i < names.length - 1)
                fullValue.append(".");
        }
        return fullValue.toString();
    }
}
