package net.sevenstars.ofhallsandheralds.network.packet;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.sevenstars.api.network.contexts.ClientPacketContext;
import net.sevenstars.api.network.packets.ServerToClientPacket;
import net.sevenstars.ofhallsandheralds.OfHallsAndHeralds;
import net.sevenstars.ofhallsandheralds.persistentdatas.reputation.ReputationPersistentData;

public class PlayerPersistentReputationDataPacket extends ServerToClientPacket<PlayerPersistentReputationDataPacket> {
    public static final CustomPayload.Id<PlayerPersistentReputationDataPacket> ID =  new Id<>(OfHallsAndHeralds.id("persistent_reputation_data"));
    public static final PacketCodec<RegistryByteBuf, PlayerPersistentReputationDataPacket> PACKET_CODEC = PacketCodec.tuple(
            ReputationPersistentData.PACKET_CODEC, p -> p.data,
            PlayerPersistentReputationDataPacket::new
    );

    private final ReputationPersistentData data;

    public PlayerPersistentReputationDataPacket(ReputationPersistentData data) {
        this.data = data;
    }

    @Override
    public CustomPayload.Id<PlayerPersistentReputationDataPacket> getId() {
        return ID;
    }

    @Override
    public PacketCodec<RegistryByteBuf, PlayerPersistentReputationDataPacket> streamCodec() {
        return PACKET_CODEC;
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void process(ClientPacketContext context) {
        PlayerEntity player = context.player();
        // TODO : Do stuff in the front-end if needed
    }
}
