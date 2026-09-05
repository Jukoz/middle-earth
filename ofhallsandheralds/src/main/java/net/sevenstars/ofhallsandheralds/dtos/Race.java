package net.sevenstars.ofhallsandheralds.dtos;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.Identifier;

import java.util.Optional;

public class Race {
    public static final Codec<Race> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Identifier.CODEC.optionalFieldOf("renderer").forGetter(Race::getRenderer))
            .apply(instance, Race::new));

    private Identifier renderer;

    public Race(Optional<Identifier> renderer) {
        this.renderer = renderer.orElse(null);
    }

    private Optional<Identifier> getRenderer() {
        return Optional.ofNullable(renderer);
    }
}
