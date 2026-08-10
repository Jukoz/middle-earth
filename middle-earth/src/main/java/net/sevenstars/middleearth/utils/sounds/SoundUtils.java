package net.sevenstars.middleearth.utils.sounds;

import net.minecraft.entity.Entity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.world.World;

public class SoundUtils {
    /**
     * Plays a sound at the given entity's location.
     *
     * @param world    The world in which to play the sound.
     * @param entity   The entity at whose position the sound will play.
     * @param sound    The sound event to play.
     * @param category The category of the sound.
     * @param volume   The volume of the sound.
     * @param pitch    The pitch of the sound.
     */
    public static void playSoundAtEntity(World world, Entity entity, SoundEvent sound, SoundCategory category, float volume, float pitch) {
        world.playSound(null, entity.getX(), entity.getY(), entity.getZ(), sound, category, volume, pitch);
    }

    /**
     * Plays a sound at the given entity's location.
     *
     * @param world    The world in which to play the sound.
     * @param entity   The entity at whose position the sound will play.
     * @param sound    The sound event to play.
     * @param category The category of the sound.
     * @param volume   The volume of the sound.
     */
    public static void playSoundAtEntity(World world, Entity entity, SoundEvent sound, SoundCategory category, float volume) {
        playSoundAtEntity(world, entity, sound, category, volume, 1.0F);
    }

    /**
     * Plays a sound at the given entity's location.
     *
     * @param world    The world in which to play the sound.
     * @param entity   The entity at whose position the sound will play.
     * @param sound    The sound event to play.
     * @param category The category of the sound.
     */
    public static void playSoundAtEntity(World world, Entity entity, SoundEvent sound, SoundCategory category) {
        playSoundAtEntity(world, entity, sound, category, 1.0F, 1.0F);
    }
}
