package net.sevenstars.of_beasts_and_wild_things.compat.farm;

import java.util.List;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.structure.Structure;

public record FarmAnimalVariantSelector(Optional<Condition> condition, int priority) {
    public FarmAnimalVariantSelector {
        condition = condition == null ? Optional.empty() : condition;
    }

    public boolean test(SpawnContext context) {
        return this.condition.map(value -> value.test(context)).orElse(true);
    }

    public record SpawnContext(ServerLevelAccessor level, BlockPos pos, Holder<Biome> biome) {
    }

    @FunctionalInterface
    public interface Condition {
        boolean test(SpawnContext context);
    }

    public record BiomeCondition(List<RegistryEntryMatcher<Biome>> biomes) implements Condition {
        public BiomeCondition {
            biomes = List.copyOf(biomes);
        }

        @Override
        public boolean test(SpawnContext context) {
            return this.biomes.stream().anyMatch(matcher -> matcher.test(context.biome()));
        }
    }

    public record StructureCondition(List<RegistryEntryMatcher<Structure>> structures) implements Condition {
        public StructureCondition {
            structures = List.copyOf(structures);
        }

        @Override
        public boolean test(SpawnContext context) {
            var level = context.level().getLevel();
            var structureManager = level.structureManager();
            return level.registryAccess().registryOrThrow(Registries.STRUCTURE).holders()
                    .filter(holder -> this.structures.stream().anyMatch(matcher -> matcher.test(holder)))
                    .anyMatch(holder -> structureManager.getStructureAt(context.pos(), holder.value()).isValid());
        }
    }

    public record MoonBrightnessCondition(double min, double max) implements Condition {
        @Override
        public boolean test(SpawnContext context) {
            var level = context.level().getLevel();
            int phase = level.dimensionType().moonPhase(level.getDayTime());
            float brightness = net.minecraft.world.level.dimension.DimensionType.MOON_BRIGHTNESS_PER_PHASE[phase];
            return brightness >= this.min && brightness <= this.max;
        }
    }

    public sealed interface RegistryEntryMatcher<T> permits RegistryEntryMatcher.Id, RegistryEntryMatcher.Tag {
        boolean test(Holder<T> holder);

        record Id<T>(ResourceKey<T> key) implements RegistryEntryMatcher<T> {
            @Override
            public boolean test(Holder<T> holder) {
                return holder.is(this.key);
            }
        }

        record Tag<T>(TagKey<T> key) implements RegistryEntryMatcher<T> {
            @Override
            public boolean test(Holder<T> holder) {
                return holder.is(this.key);
            }
        }
    }
}
