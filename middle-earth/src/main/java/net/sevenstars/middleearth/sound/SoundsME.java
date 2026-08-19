package net.sevenstars.middleearth.sound;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.sevenstars.api.registries.RegistrationBridge;
import net.sevenstars.middleearth.MiddleEarth;

public class SoundsME {
    public static SoundEvent BELLOWS_PUSH = registerSoundEvent("bellows_push");

    public static SoundEvent CHISEL_ENCHANT = registerSoundEvent("chisel_enchant");
    public static SoundEvent CHISEL_HIT_FIRST = registerSoundEvent("chisel_hit_first");
    public static SoundEvent CHISEL_HIT_SECOND = registerSoundEvent("chisel_hit_second");
    public static SoundEvent CHISEL_HIT_THIRD = registerSoundEvent("chisel_hit_third");

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

    public static SoundEvent CAVE_TROLL_DEATH = registerSoundEvent("cave_troll_death");
    public static SoundEvent CAVE_TROLL_HURT = registerSoundEvent("cave_troll_hurt");
    public static SoundEvent CAVE_TROLL_IDLE = registerSoundEvent("cave_troll_idle");
    public static SoundEvent CAVE_TROLL_ROAR = registerSoundEvent("cave_troll_roar");
    public static SoundEvent CAVE_TROLL_STEP = registerSoundEvent("cave_troll_step");

    private static SoundEvent registerSoundEvent(String name) {
        ResourceLocation id = MiddleEarth.of(name);
        return RegistrationBridge.register(BuiltInRegistries.SOUND_EVENT, id, SoundEvent.createVariableRangeEvent(id));
    }

    public static void registerModSounds() {
        MiddleEarth.LOGGER.logDebugMsg("Registering Mod SoundEvents for " + MiddleEarth.MOD_ID);
    }
}
