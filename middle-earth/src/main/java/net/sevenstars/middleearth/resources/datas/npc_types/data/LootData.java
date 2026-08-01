package net.sevenstars.middleearth.resources.datas.npc_types.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.server.world.ServerWorld;

import java.util.Optional;

public class LootData {
    public static class Fields {
        public static final String BASE_EXPERIENCE = "base";
        public static final String RANDOM_ADDITION = "random_addition";
    }

    public static final Codec<LootData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.INT.fieldOf(Fields.BASE_EXPERIENCE).forGetter(LootData::getBaseExperience),
            Codec.INT.optionalFieldOf(Fields.RANDOM_ADDITION).forGetter(LootData::getRandomAddition)
    ).apply(instance, LootData::new));

    private Integer baseExperience;
    private Integer randomAddition;

    private LootData(int base, Optional<Integer> randomAddition) {
        this.baseExperience = base;
        this.randomAddition = randomAddition.orElse(null);
    }
    public LootData(int base, int randomAddition) {
        this.baseExperience = base;
        this.randomAddition = randomAddition;
    }
    public LootData(int base) {
        this.baseExperience = base;
        this.randomAddition = null;
    }

    private Integer getBaseExperience() {
        return baseExperience;
    }
    private Optional<Integer> getRandomAddition() {
        return Optional.ofNullable(randomAddition);
    }

    public int getExperience(ServerWorld world) {
        int random = getRandomAddition().orElse(0);
        if(random == 0) {
            return baseExperience;
        }
        return baseExperience + world.getRandom().nextInt(random);
    }
}
