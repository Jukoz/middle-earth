package net.sevenstars.middleearth.resources.datas.texture_presets;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.registry.entry.RegistryElementCodec;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.registries.DynamicRegistriesME;
import net.sevenstars.middleearth.resources.datas.common.CharacterPatternTypes;

import java.util.Optional;

public class CharacterTexturePattern {

    public static final Codec<CharacterTexturePattern> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
            Identifier.CODEC.fieldOf("asset_id").forGetter(CharacterTexturePattern::getIdentifier),
            Codec.STRING.fieldOf("category").forGetter(CharacterTexturePattern::getCategoryString),
            Codec.BOOL.optionalFieldOf("has_addon").forGetter(CharacterTexturePattern::hasAddonOptional))
            .apply(instance, CharacterTexturePattern::new));

    public static final PacketCodec<RegistryByteBuf, CharacterTexturePattern> PACKET_CODEC;
    public static final Codec<RegistryEntry<CharacterTexturePattern>> ENTRY_CODEC;
    public static final PacketCodec<RegistryByteBuf, RegistryEntry<CharacterTexturePattern>> ENTRY_PACKET_CODEC;

    private final Identifier assetId;
    private final CharacterPatternTypes patternType;

    private Boolean hasAddon;
    public CharacterTexturePattern(Identifier assetId, String type, Optional<Boolean> hasAddon){
        this.assetId = assetId;
        this.patternType = CharacterPatternTypes.valueOf(type);
        this.hasAddon = hasAddon.orElse(false);
    }

    public CharacterTexturePattern(Identifier assetId, String type, Boolean hasAddon){
        this.assetId = assetId;
        this.patternType = CharacterPatternTypes.valueOf(type.toUpperCase());
        this.hasAddon = hasAddon;
    }

    public CharacterTexturePattern(Identifier id, CharacterPatternTypes type, Boolean hasAddon){
        this(id, type);
        this.hasAddon = hasAddon;
    }
    public CharacterTexturePattern(Identifier id, CharacterPatternTypes type){
        this.assetId = id;
        this.patternType = type;
    }
    public Identifier getIdentifier() {
        return assetId;
    }

    public CharacterPatternTypes getPatternType(){
        return this.patternType;
    }

    public String getCategoryString() {
        return this.patternType.name().toUpperCase();
    }

    private Optional<Boolean> hasAddonOptional() {
        if(hasAddon == null || !hasAddon)
            return Optional.empty();
        return Optional.of(true);
    }
    public Boolean hasAddonRawValue() {
        return hasAddon;
    }


    static {
        PACKET_CODEC = PacketCodec.tuple(
                Identifier.PACKET_CODEC,
                CharacterTexturePattern::getIdentifier,
                PacketCodecs.STRING,
                CharacterTexturePattern::getCategoryString,
                PacketCodecs.BOOLEAN,
                CharacterTexturePattern::hasAddonRawValue,
                CharacterTexturePattern::new);
        ENTRY_CODEC = RegistryElementCodec.of(DynamicRegistriesME.SKIN_PATTERN, CODEC);
        ENTRY_PACKET_CODEC = PacketCodecs.registryEntry(DynamicRegistriesME.SKIN_PATTERN, PACKET_CODEC);
    }
}
