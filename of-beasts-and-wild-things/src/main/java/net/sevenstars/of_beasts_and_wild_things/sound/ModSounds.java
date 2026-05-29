package net.sevenstars.of_beasts_and_wild_things.sound;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.sevenstars.of_beasts_and_wild_things.OfBeastsAndWildThings;

public class ModSounds {
    public static SoundEvent DEER_DEATH = registerSoundEvent("deer_death");
    public static SoundEvent DEER_GRUNT = registerSoundEvent("deer_grunt");
    public static SoundEvent DEER_IDLE = registerSoundEvent("deer_idle");

    private static SoundEvent registerSoundEvent(String name) {
        Identifier id = Identifier.of(OfBeastsAndWildThings.MOD_ID, name);
        return Registry.register(Registries.SOUND_EVENT, id, SoundEvent.of(id));
    }

    public static void registerModSounds() {
        OfBeastsAndWildThings.LOGGER.logDebugMsg("Registering Mod SoundEvents for " + OfBeastsAndWildThings.MOD_ID);
    }
}
