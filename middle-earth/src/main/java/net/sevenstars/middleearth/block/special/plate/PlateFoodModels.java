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

    public static void addVanillaModel(String name) {
        plateModels.put(Identifier.ofVanilla(name), Identifier.of(MiddleEarth.MOD_ID, "plate_" + name));
    }
    public static void addModel(String name) {
        plateModels.put(Identifier.of(MiddleEarth.MOD_ID, name), Identifier.of(MiddleEarth.MOD_ID, "plate_" + name));
    }

    private static void initMap() {
        plateModels = new HashMap<>();
        addVanillaModel("apple");
        addVanillaModel("bowl");
        addVanillaModel("pumpkin_pie");
        addVanillaModel("rabbit_stew");
        addVanillaModel("beetroot_soup");
        addModel("cram");
        addModel("berry_pie");
        addModel("meat_skewer");
        addModel("cooked_meat_skewer");
        addModel("vegetable_skewer");
        addModel("cooked_vegetable_skewer");
    }

    static {
        initMap();
    }
}
