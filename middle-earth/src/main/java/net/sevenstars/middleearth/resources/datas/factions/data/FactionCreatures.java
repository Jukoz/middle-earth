package net.sevenstars.middleearth.resources.datas.factions.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.entity.EntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.MiddleEarth;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

// Entries are entity ids or #entity type tags
public class FactionCreatures {
    private static final String TAG_PREFIX = "#";

    public static final Codec<FactionCreatures> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.STRING.listOf().optionalFieldOf("hostile", List.of()).forGetter(creatures -> creatures.hostile),
            Codec.STRING.listOf().optionalFieldOf("friendly", List.of()).forGetter(creatures -> creatures.friendly)
    ).apply(instance, FactionCreatures::new));

    public static final FactionCreatures NONE = new FactionCreatures(List.of(), List.of());

    private final List<String> hostile;
    private final List<String> friendly;
    private final Matcher hostileMatcher;
    private final Matcher friendlyMatcher;

    public FactionCreatures(List<String> hostile, List<String> friendly) {
        this.hostile = List.copyOf(hostile);
        this.friendly = List.copyOf(friendly);
        this.hostileMatcher = new Matcher(hostile);
        this.friendlyMatcher = new Matcher(friendly);
    }

    public boolean isHostile(EntityType<?> type) {
        return hostileMatcher.matches(type);
    }

    public boolean isFriendly(EntityType<?> type) {
        return friendlyMatcher.matches(type);
    }

    public boolean isEmpty() {
        return hostile.isEmpty() && friendly.isEmpty();
    }

    private static class Matcher {
        private final Set<Identifier> ids = new HashSet<>();
        private final List<TagKey<EntityType<?>>> tags = new ArrayList<>();

        Matcher(List<String> entries) {
            for(String entry : entries) {
                boolean tag = entry.startsWith(TAG_PREFIX);
                Identifier id = Identifier.tryParse(tag ? entry.substring(TAG_PREFIX.length()) : entry);
                if(id == null)
                    MiddleEarth.LOGGER.logError("Faction creatures can't use '" + entry + "', it is not a valid identifier");
                else if(tag)
                    tags.add(TagKey.of(RegistryKeys.ENTITY_TYPE, id));
                else
                    ids.add(id);
            }
        }

        boolean matches(EntityType<?> type) {
            if(!ids.isEmpty() && ids.contains(Registries.ENTITY_TYPE.getId(type)))
                return true;
            for(TagKey<EntityType<?>> tag : tags) {
                if(type.isIn(tag))
                    return true;
            }
            return false;
        }
    }
}
