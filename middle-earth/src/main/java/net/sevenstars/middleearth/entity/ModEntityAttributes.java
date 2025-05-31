package net.sevenstars.middleearth.entity;

import net.minecraft.entity.attribute.ClampedEntityAttribute;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.MiddleEarth;

public class ModEntityAttributes {
    public static final RegistryEntry<EntityAttribute> POWDERED_SNOW_IMMUNITY;
    public static final RegistryEntry<EntityAttribute> DELVERS_FEAR_STRENGTH;

    private static RegistryEntry<EntityAttribute> register(String name, double defaultValue, double minValue , double MaxValue, boolean tracked) {
        Identifier id = Identifier.of(MiddleEarth.MOD_ID, name);
        EntityAttribute attribute = new ClampedEntityAttribute(id.toTranslationKey(), defaultValue, minValue, MaxValue).setTracked(tracked);
        return Registry.registerReference(Registries.ATTRIBUTE, Identifier.of(MiddleEarth.MOD_ID, name),attribute);
    }

    public static void register() {
        MiddleEarth.LOGGER.logDebugMsg("Registering Mod attributes");
    }

    static {
        POWDERED_SNOW_IMMUNITY = register("powdered_snow_immunity", 0.0, 0.0, 1.0, true);
        DELVERS_FEAR_STRENGTH = register("delvers_fear_strength", 4 * 60 * 5 /* 5 minutes in seconds */ , 0.0, Double.MAX_VALUE, true);
    }
}
