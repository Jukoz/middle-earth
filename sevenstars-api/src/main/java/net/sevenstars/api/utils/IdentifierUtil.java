package net.sevenstars.api.utils;

import net.minecraft.util.Identifier;
import net.sevenstars.api.SevenStarsApi;

public class IdentifierUtil {
    public static Identifier getIdentifierFromString(String id){
        if(id == null)
            return null;
        if(id.contains(":") && id.split(":").length == 2){
            return Identifier.of(id.split(":")[0], id.split(":")[1]);
        }
        return Identifier.of(SevenStarsApi.MOD_ID, id + "_error");
    }

    public static Identifier build(String key, String name) {
        return Identifier.of(key, name);
    }

    public static Identifier buildAggregate(String key, String... names) {
        return build(key, createAggregateValue('.', names));
    }

    public static String createAggregateValue(char character, String... names){
        if(names.length == 0)
            return "not_enough_parameters";
        if(names.length == 1)
            return names[0];

        StringBuilder fullValue = new StringBuilder();
        for(int i = 0; i < names.length; i++){
            fullValue.append(names[i]);
            if(i < names.length - 1)
                fullValue.append(character);
        }
        return fullValue.toString();
    }
}
