package net.jukoz.me.sound;

import net.jukoz.me.MiddleEarth;
import net.jukoz.me.utils.LoggerUtil;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class ModSounds {
    public static SoundEvent NAZGUL_FADE = registerSoundEvent("nazgul_fade");
    public static SoundEvent NAZGUL_SCREAM = registerSoundEvent("nazgul_scream");

    private static SoundEvent registerSoundEvent(String name) {
        Identifier id = Identifier.of(MiddleEarth.MOD_ID, name);
        return Registry.register(Registries.SOUND_EVENT, id, SoundEvent.of(id));
    }

    public static void registerModSounds() {
        LoggerUtil.logDebugMsg("Registering Mod Entities for " + MiddleEarth.MOD_ID);
    }
}
