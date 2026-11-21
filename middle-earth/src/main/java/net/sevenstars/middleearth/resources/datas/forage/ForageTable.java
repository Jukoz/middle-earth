package net.sevenstars.middleearth.resources.datas.forage;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.world.biome.Biome;

import java.util.List;
import java.util.Optional;

// A table covers biomes in a tag or with a word in their name. Highest priority wins
public record ForageTable(Optional<TagKey<Biome>> tag, Optional<String> name, List<Item> items, int priority) {
    public static final Codec<ForageTable> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            TagKey.codec(RegistryKeys.BIOME).optionalFieldOf("tag").forGetter(ForageTable::tag),
            Codec.STRING.optionalFieldOf("name").forGetter(ForageTable::name),
            Codec.list(Registries.ITEM.getCodec()).fieldOf("items").forGetter(ForageTable::items),
            Codec.INT.optionalFieldOf("priority", 0).forGetter(ForageTable::priority)
    ).apply(instance, ForageTable::new));

    public boolean fits(RegistryEntry<Biome> biome) {
        if(tag.isPresent() && biome.isIn(tag.get()))
            return true;
        return name.isPresent() && biome.getKey().map(key -> key.getValue().getPath().contains(name.get())).orElse(false);
    }
}
