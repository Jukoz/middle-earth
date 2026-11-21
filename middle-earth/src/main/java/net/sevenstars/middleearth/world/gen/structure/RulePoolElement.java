package net.sevenstars.middleearth.world.gen.structure;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.structure.StructureLiquidSettings;
import net.minecraft.structure.StructureTemplate;
import net.minecraft.structure.pool.SinglePoolElement;
import net.minecraft.structure.pool.StructurePool;
import net.minecraft.structure.pool.StructurePoolElementType;
import net.minecraft.structure.processor.StructureProcessorList;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.MiddleEarth;

import java.util.Optional;

// A single pool element with placement rules, counted across the whole structure by name:
// max_count caps it, priority tries it before the random pick (with max_count 1 a village always gets one
// where it fits), min_depth and max_depth limit how far from the start piece it may go
public class RulePoolElement extends SinglePoolElement {
    public static final MapCodec<RulePoolElement> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            locationGetter(), processorsGetter(), projectionGetter(), overrideLiquidSettingsGetter(),
            Codec.STRING.fieldOf("name").forGetter(element -> element.name),
            Codec.INT.optionalFieldOf("max_count").forGetter(element -> element.maxCount),
            Codec.BOOL.optionalFieldOf("priority", false).forGetter(element -> element.priority),
            Codec.INT.optionalFieldOf("min_depth", 0).forGetter(element -> element.minDepth),
            Codec.INT.optionalFieldOf("max_depth").forGetter(element -> element.maxDepth)
    ).apply(instance, RulePoolElement::new));

    public static final StructurePoolElementType<RulePoolElement> TYPE = () -> CODEC;

    private final String name;
    private final Optional<Integer> maxCount;
    private final boolean priority;
    private final int minDepth;
    private final Optional<Integer> maxDepth;

    protected RulePoolElement(Either<Identifier, StructureTemplate> location, RegistryEntry<StructureProcessorList> processors, StructurePool.Projection projection,
                              Optional<StructureLiquidSettings> overrideLiquidSettings, String name, Optional<Integer> maxCount, boolean priority, int minDepth, Optional<Integer> maxDepth) {
        super(location, processors, projection, overrideLiquidSettings);
        this.name = name;
        this.maxCount = maxCount;
        this.priority = priority;
        this.minDepth = minDepth;
        this.maxDepth = maxDepth;
    }

    public static void register() {
        Registry.register(Registries.STRUCTURE_POOL_ELEMENT, Identifier.of(MiddleEarth.MOD_ID, "rule_pool_element"), TYPE);
    }

    public String getName() {
        return name;
    }

    public boolean isPriority() {
        return priority;
    }

    public boolean isFull(int placed) {
        return maxCount.isPresent() && placed >= maxCount.get();
    }

    public boolean allowsDepth(int depth) {
        return depth >= minDepth && (maxDepth.isEmpty() || depth <= maxDepth.get());
    }

    @Override
    public StructurePoolElementType<?> getType() {
        return TYPE;
    }

    @Override
    public String toString() {
        return "Rule[" + name + " " + location + "]";
    }
}
