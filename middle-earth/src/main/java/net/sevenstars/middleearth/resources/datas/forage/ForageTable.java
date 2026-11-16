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

// What an npc finds when foraging in a kind of land. A table applies to the biomes in a tag, or to biomes with a word
// in their name, such as every wood of Lothlorien whatever it is tagged as. Of the tables that fit a place, the highest
// priority wins
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
