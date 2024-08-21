package net.jukoz.me.network.packets.c2s;

import net.jukoz.me.MiddleEarth;
import net.jukoz.me.network.contexts.ServerPacketContext;
import net.jukoz.me.network.packets.ClientToServerPacket;
import net.jukoz.me.resources.StateSaverAndLoader;
import net.jukoz.me.resources.persistent_datas.AffiliationData;
import net.jukoz.me.resources.persistent_datas.PlayerData;
import net.jukoz.me.utils.LoggerUtil;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public class  AffiliationPacket extends ClientToServerPacket<AffiliationPacket>
{
    public static final CustomPayload.Id<AffiliationPacket> ID = new CustomPayload.Id<>(Identifier.of(MiddleEarth.MOD_ID, "affiliation_packet"));

    public static final PacketCodec<RegistryByteBuf, AffiliationPacket> CODEC = PacketCodec.tuple(
            PacketCodecs.STRING, p -> p.alignmentName,
            PacketCodecs.STRING, p -> p.factionName,
            PacketCodecs.STRING, p -> p.spawnName,
            AffiliationPacket::new
    );

    private final String alignmentName;
    private final String factionName;
    private final String spawnName;


    public AffiliationPacket(String alignmentName, String factionName, String spawnName){
        this.alignmentName = alignmentName;
        this.factionName = factionName;
        this.spawnName = spawnName;
    }

    @Override
    public Id<AffiliationPacket> getId() {
        return ID;
    }

    @Override
    public PacketCodec<RegistryByteBuf, AffiliationPacket> streamCodec() {
        return CODEC;
    }

    @Override
    public void process(ServerPacketContext context) {
        context.player().getServer().execute(() -> {
            try{
                PlayerData playerState = StateSaverAndLoader.getPlayerState(context.player());

                AffiliationData affiliationData = new AffiliationData(alignmentName, factionName, spawnName);
                playerState.setAffiliationData(affiliationData);
            } catch (Exception e){
                LoggerUtil.logError("AffiliationPacket::Tried getting affiliation packet and couldn't fetch any.", e);
            }
        });
    }
}