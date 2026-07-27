package net.sevenstars.middleearth.entity;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.api.registries.RegistrationBridge;

public class EntityAttributesME {
    public static final Holder<Attribute> POWDERED_SNOW_IMMUNITY;
    public static final Holder<Attribute> DELVERS_FEAR_STRENGTH;
    public static final Holder<Attribute> CLIMBING_STRENGTH;
    public static final Holder<Attribute> DETECTION_RANGE;
    public static final Holder<Attribute> WIDTH_SCALE;

    private static Holder<Attribute> register(String name, double defaultValue, double minValue , double MaxValue, boolean tracked) {
        ResourceLocation id = MiddleEarth.of(name);
        Attribute attribute = new RangedAttribute(id.toLanguageKey(), defaultValue, minValue, MaxValue).setSyncable(tracked);
        return RegistrationBridge.registerForHolder(BuiltInRegistries.ATTRIBUTE, id, attribute);
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
