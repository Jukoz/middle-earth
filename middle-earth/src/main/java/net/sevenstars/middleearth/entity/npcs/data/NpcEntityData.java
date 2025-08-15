package net.sevenstars.middleearth.entity.npcs.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.entity.EntityData;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import net.sevenstars.middleearth.resources.FactionsME;
import net.sevenstars.middleearth.resources.NpcME;
import net.sevenstars.middleearth.resources.datas.factions.Faction;
import net.sevenstars.middleearth.resources.datas.npcs.NpcData;
import net.sevenstars.middleearth.resources.datas.npcs.NpcDataLookup;
import net.sevenstars.middleearth.resources.datas.races.Race;
import net.sevenstars.middleearth.resources.datas.races.RaceLookup;
import net.sevenstars.middleearth.resources.datas.races.data.EntityCategory;

public class NpcEntityData implements EntityData {
    public Identifier factionId;
    public Identifier npcDataId;
    public EntityCategory category;

    public static final Codec<NpcEntityData> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
        Identifier.CODEC.fieldOf("faction").orElse(null).forGetter((data) -> data.factionId),
        Identifier.CODEC.fieldOf("data").orElse(null).forGetter((data) -> data.npcDataId),
        Codec.STRING.fieldOf("category").orElse(null).forGetter((data) -> data.category.toString())
    ).apply(instance, NpcEntityData::new));

    public static final PacketCodec<RegistryByteBuf, NpcEntityData> PACKET_CODEC;


    public NpcEntityData() {
        this.factionId = null;
        this.category = EntityCategory.SHARED;
        this.npcDataId = null;
    }

    public NpcEntityData(Identifier factionId, Identifier npcDataId, String category) {
        this.factionId = factionId;
        this.npcDataId = npcDataId;
        this.category = EntityCategory.valueOf(category.toUpperCase());
    }

    public NpcEntityData(Identifier factionId, Identifier npcDataId, EntityCategory category) {
        this.factionId = factionId;
        this.npcDataId = npcDataId;
        this.category = category;
    }

    public Faction getFactionValue(World world) {
        return world.getRegistryManager().getOrThrow(FactionsME.KEY).get(factionId);
    }
    public NpcData getNpcDataValue(World world) {
        return world.getRegistryManager().getOrThrow(NpcME.KEY).get(npcDataId);
    }
    public Race getRaceValue(World world){
        NpcData npcData = NpcDataLookup.getNpcData(world, this.npcDataId);
        return RaceLookup.getRace(world, npcData.getRace());
    }

    static {
        PACKET_CODEC = PacketCodec.tuple(
                Identifier.PACKET_CODEC, (data) -> data.factionId,
                Identifier.PACKET_CODEC, (data) -> data.npcDataId,
                PacketCodecs.STRING, (data) -> data.category.toString(),
                NpcEntityData::new);
    }
}
