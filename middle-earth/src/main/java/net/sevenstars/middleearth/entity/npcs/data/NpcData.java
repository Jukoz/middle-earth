package net.sevenstars.middleearth.entity.npcs.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.util.Identifier;
import net.minecraft.world.ServerWorldAccess;
import net.sevenstars.middleearth.resources.MiddleEarthFactions;
import net.sevenstars.middleearth.resources.MiddleEarthRaces;
import net.sevenstars.middleearth.resources.datas.factions.Faction;
import net.sevenstars.middleearth.resources.datas.races.Race;
import net.sevenstars.middleearth.resources.datas.races.data.EntityCategory;

public class NpcData {
    public final Identifier raceId;
    public final Identifier factionId;
    public final EntityCategory category;


    public static final Codec<NpcData> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
        Identifier.CODEC.fieldOf("faction").orElse(null).forGetter((data) -> data.factionId),
        Identifier.CODEC.fieldOf("race").orElse(null).forGetter((data) -> data.raceId),
        Codec.STRING.fieldOf("category").orElse(null).forGetter((data) -> data.category.toString())
    ).apply(instance, NpcData::new));

    public static final PacketCodec<RegistryByteBuf, NpcData> PACKET_CODEC;


    public NpcData() {
        this.factionId = null;
        this.raceId = null;
        this.category = EntityCategory.SHARED;
    }

    public NpcData(Identifier factionId, Identifier raceId, String category) {
        this.factionId = factionId;
        this.raceId = raceId;
        this.category = EntityCategory.valueOf(category.toUpperCase());
    }

    public NpcData(Identifier factionId, Identifier raceId, EntityCategory category) {
        this.factionId = factionId;
        this.raceId = raceId;
        this.category = category;
    }

    public Race getRaceValue(ServerWorldAccess world) {
        return world.getRegistryManager().getOrThrow(MiddleEarthRaces.KEY).get(raceId);
    }

    public Faction getFactionValue(ServerWorldAccess world) {
        return world.getRegistryManager().getOrThrow(MiddleEarthFactions.KEY).get(factionId);
    }

    static {
        PACKET_CODEC = PacketCodec.tuple(
                Identifier.PACKET_CODEC, (data) -> data.factionId,
                Identifier.PACKET_CODEC, (data) -> data.raceId,
                PacketCodecs.STRING, (data) -> data.category.toString(),
                NpcData::new);
    }
}
