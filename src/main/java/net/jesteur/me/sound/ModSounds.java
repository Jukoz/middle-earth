package net.jesteur.me.sound;

import net.jesteur.me.MiddleEarth;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class ModSounds {
    public static SoundEvent NAZGUL_SCREAM = registerSoundEvent("nazgul_scream");

    private static SoundEvent registerSoundEvent(String name) {
        Identifier id = new Identifier(MiddleEarth.MOD_ID, name);
        return Registry.register(Registries.SOUND_EVENT, id, SoundEvent.of(id));
    }

    public static void registerModSounds() {
        MiddleEarth.LOGGER.debug("Registering Mod Entities for " + MiddleEarth.MOD_ID);
    }
}
