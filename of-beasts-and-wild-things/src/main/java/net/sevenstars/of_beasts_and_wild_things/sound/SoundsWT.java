package net.sevenstars.of_beasts_and_wild_things.sound;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.sevenstars.of_beasts_and_wild_things.OfBeastsAndWildThings;
import net.sevenstars.api.registries.RegistrationBridge;

public class SoundsWT {

    // DEER
    public static SoundEvent DEER_DEATH = registerSoundEvent("deer_death");
    public static SoundEvent DEER_GRUNT = registerSoundEvent("deer_grunt");
    public static SoundEvent DEER_IDLE = registerSoundEvent("deer_idle");

    // SWAN
    public static SoundEvent SWAN_IDLE = registerSoundEvent("swan_idle");
    public static SoundEvent SWAN_HURT = registerSoundEvent("swan_hurt");
    public static SoundEvent SWAN_STEP = registerSoundEvent("swan_step");
    public static SoundEvent SWAN_DEATH = registerSoundEvent("swan_death");

    private static SoundEvent registerSoundEvent(String name) {
        ResourceLocation id = ResourceLocation.fromNamespaceAndPath(OfBeastsAndWildThings.MOD_ID, name);
        return RegistrationBridge.register(BuiltInRegistries.SOUND_EVENT, id, SoundEvent.createVariableRangeEvent(id));
    }

    public static void registerModSounds() {
        OfBeastsAndWildThings.LOGGER.logDebugMsg("Registering Mod SoundEvents for " + OfBeastsAndWildThings.MOD_ID);
    }
}
