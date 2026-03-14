package net.sevenstars.middleearth.resources.datas.races.data.npctextures;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.registry.entry.RegistryElementCodec;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.registries.DynamicRegistriesME;
import net.sevenstars.middleearth.resources.CharacterPatternsME;

import java.util.Optional;

public class NpcTexturePattern {

    public static final Codec<NpcTexturePattern> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
            Identifier.CODEC.fieldOf("asset_id").forGetter(NpcTexturePattern::getIdentifier),
            Codec.STRING.fieldOf("category").forGetter(NpcTexturePattern::getCategoryString),
            Codec.BOOL.optionalFieldOf("has_addon").forGetter(NpcTexturePattern::hasAddonOptional))
            .apply(instance, NpcTexturePattern::new));

    public static final PacketCodec<RegistryByteBuf, NpcTexturePattern> PACKET_CODEC;
    public static final Codec<RegistryEntry<NpcTexturePattern>> ENTRY_CODEC;
    public static final PacketCodec<RegistryByteBuf, RegistryEntry<NpcTexturePattern>> ENTRY_PACKET_CODEC;

    private Identifier assetId;
    private NpcTextureType category;
    private Boolean hasAddon;
    public NpcTexturePattern(Identifier assetId, String category, Optional<Boolean> hasAddon){
        this.assetId = assetId;
        this.category = NpcTextureType.valueOf(category);
        hasAddon.ifPresent(aBoolean -> this.hasAddon = aBoolean);
        if(hasAddon.isPresent()){
            this.hasAddon = hasAddon.get();
        } else {
            this.hasAddon = false;
        }
    }

    public NpcTexturePattern(Identifier assetId, String type, Boolean hasAddon){
        this.assetId = assetId;
        this.category = NpcTextureType.valueOf(type.toUpperCase());
        this.hasAddon = hasAddon;
    }

    public NpcTexturePattern(Identifier id, NpcTextureType category, Boolean hasAddon){
        this(id, category);
        this.hasAddon = hasAddon;
    }
    public NpcTexturePattern(Identifier id, NpcTextureType category){
        this.assetId = id;
        this.category = category;
    }
    public Identifier getIdentifier() {
        return assetId;
    }

    public NpcTextureType getCategory(){
        return this.category;
    }

    public String getCategoryString() {
        return this.category.name().toUpperCase();
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
                NpcTexturePattern::getIdentifier,
                PacketCodecs.STRING,
                NpcTexturePattern::getCategoryString,
                PacketCodecs.BOOLEAN,
                NpcTexturePattern::hasAddonRawValue,
                NpcTexturePattern::new);
        ENTRY_CODEC = RegistryElementCodec.of(DynamicRegistriesME.SKIN_PATTERN, CODEC);
        ENTRY_PACKET_CODEC = PacketCodecs.registryEntry(DynamicRegistriesME.SKIN_PATTERN, PACKET_CODEC);
    }
}
