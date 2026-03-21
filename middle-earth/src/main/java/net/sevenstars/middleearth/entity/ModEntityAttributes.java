package net.sevenstars.middleearth.entity;

import net.minecraft.entity.attribute.ClampedEntityAttribute;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.utils.IdentifierUtil;

public class ModEntityAttributes {
    public static final RegistryEntry<EntityAttribute> POWDERED_SNOW_IMMUNITY;
    public static final RegistryEntry<EntityAttribute> DELVERS_FEAR_STRENGTH;
    public static final RegistryEntry<EntityAttribute> CLIMBING_STRENGTH;
    public static final RegistryEntry<EntityAttribute> DETECTION_RANGE;
    public static final RegistryEntry<EntityAttribute> WIDTH_SCALE;

    private static RegistryEntry<EntityAttribute> register(String name, double defaultValue, double minValue , double MaxValue, boolean tracked) {
        Identifier id = IdentifierUtil.build(name);
        EntityAttribute attribute = new ClampedEntityAttribute(id.toTranslationKey(), defaultValue, minValue, MaxValue).setTracked(tracked);
        return Registry.registerReference(Registries.ATTRIBUTE, id, attribute);
    }

    public static void register() {
        MiddleEarth.LOGGER.logDebugMsg("Registering Mod attributes");
    }

    static {
        POWDERED_SNOW_IMMUNITY = register("powdered_snow_immunity", 0.0, 0.0, 1.0, true);
        DELVERS_FEAR_STRENGTH = register("delvers_fear_strength", 0.0, 0.0, Double.MAX_VALUE, true);
        CLIMBING_STRENGTH = register("climbing_strength", 0.0, 0.0, Double.MAX_VALUE, true);
        DETECTION_RANGE = register("detection_range", 1.0, 0.1, 1.0, true);
        WIDTH_SCALE = register("width_scale", 1.0, 0.1, 2.0, true);
    }
}
