package net.sevenstars.middleearth.block.special.plate;

import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.MiddleEarth;

import java.util.HashMap;

public class PlateFoodModels {
    public static HashMap<Identifier, Identifier> plateModels;

    public static Identifier getPlateIdentifier(Identifier itemIdentifier) {
        if(plateModels.containsKey(itemIdentifier)) {
            return plateModels.get(itemIdentifier);
        }
        return itemIdentifier;
    }

    static {
        plateModels = new HashMap<>();
        plateModels.put(Identifier.ofVanilla("apple"), Identifier.of(MiddleEarth.MOD_ID, "plate_apple"));
    }
}
