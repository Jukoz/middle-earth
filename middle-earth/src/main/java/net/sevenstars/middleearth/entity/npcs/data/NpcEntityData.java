package net.sevenstars.middleearth.entity.npcs.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import net.sevenstars.middleearth.resources.FactionsME;
import net.sevenstars.middleearth.resources.NpcME;
import net.sevenstars.middleearth.resources.RacesME;
import net.sevenstars.middleearth.resources.datas.factions.Faction;
import net.sevenstars.middleearth.resources.datas.npcs.NpcData;
import net.sevenstars.middleearth.resources.datas.races.Race;
import net.sevenstars.middleearth.resources.datas.races.data.EntityCategory;

public class NpcEntityData {
    public final Identifier raceId;
    public final Identifier factionId;
    public final Identifier npcDataId;
    public final EntityCategory category;


    public static final Codec<NpcEntityData> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
        Identifier.CODEC.fieldOf("faction").orElse(null).forGetter((data) -> data.factionId),
        Identifier.CODEC.fieldOf("race").orElse(null).forGetter((data) -> data.raceId),
        Identifier.CODEC.fieldOf("data").orElse(null).forGetter((data) -> data.npcDataId),
        Codec.STRING.fieldOf("category").orElse(null).forGetter((data) -> data.category.toString())
    ).apply(instance, NpcEntityData::new));

    public static final PacketCodec<RegistryByteBuf, NpcEntityData> PACKET_CODEC;


    public NpcEntityData() {
        this.factionId = null;
        this.raceId = null;
        this.category = EntityCategory.SHARED;
        this.npcDataId = null;
    }

    public NpcEntityData(Identifier factionId, Identifier raceId, Identifier npcDataId, String category) {
        this.factionId = factionId;
        this.raceId = raceId;
        this.npcDataId = npcDataId;
        this.category = EntityCategory.valueOf(category.toUpperCase());
    }

    public NpcEntityData(Identifier factionId, Identifier raceId, Identifier npcDataId, EntityCategory category) {
        this.factionId = factionId;
        this.raceId = raceId;
        this.npcDataId = npcDataId;
        this.category = category;
    }

    public Race getRaceValue(World world) {
        return world.getRegistryManager().getOrThrow(RacesME.KEY).get(raceId);
    }

    public Faction getFactionValue(World world) {
        return world.getRegistryManager().getOrThrow(FactionsME.KEY).get(factionId);
    }
    public NpcData getNpcDataValue(World world) {
        return world.getRegistryManager().getOrThrow(NpcME.KEY).get(npcDataId);
    }
    static {
        PACKET_CODEC = PacketCodec.tuple(
                Identifier.PACKET_CODEC, (data) -> data.factionId,
                Identifier.PACKET_CODEC, (data) -> data.raceId,
                Identifier.PACKET_CODEC, (data) -> data.npcDataId,
                PacketCodecs.STRING, (data) -> data.category.toString(),
                NpcEntityData::new);
    }
}
