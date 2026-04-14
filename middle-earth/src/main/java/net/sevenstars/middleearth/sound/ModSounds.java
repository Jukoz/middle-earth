package net.sevenstars.middleearth.sound;

import net.sevenstars.middleearth.MiddleEarth;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class ModSounds {
    public static SoundEvent BELLOWS_PUSH = registerSoundEvent("bellows_push");
    public static SoundEvent NAZGUL_FADE = registerSoundEvent("nazgul_fade");
    public static SoundEvent NAZGUL_SCREAM = registerSoundEvent("nazgul_scream");
    public static SoundEvent PIPE_EXHALE = registerSoundEvent("pipe_exhale");
    public static SoundEvent PIPE_REFILL = registerSoundEvent("pipe_refill");
    public static SoundEvent PIPE_IGNITE = registerSoundEvent( "pipe_ignite");
    public static SoundEvent PIPE_COUGH = registerSoundEvent("pipe_cough");

    public static SoundEvent ORC_DRUM = registerSoundEvent("orc_drum");

    public static SoundEvent GREAT_HORN_IDLE = registerSoundEvent("great_horn_idle");
    public static SoundEvent GREAT_HORN_CALL = registerSoundEvent("great_horn_call");
    public static SoundEvent GREAT_HORN_HURT = registerSoundEvent("great_horn_hurt");
    public static SoundEvent GREAT_HORN_DEATH = registerSoundEvent("great_horn_death");

    private static SoundEvent registerSoundEvent(String name) {
        Identifier id = Identifier.of(MiddleEarth.MOD_ID, name);
        return Registry.register(Registries.SOUND_EVENT, id, SoundEvent.of(id));
    }

    public static void registerModSounds() {
        MiddleEarth.LOGGER.logDebugMsg("Registering Mod SoundEvents for " + MiddleEarth.MOD_ID);
    }
}
