package net.sevenstars.ofhallsandheralds.network.payload;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.sevenstars.api.network.contexts.ClientPacketContext;
import net.sevenstars.api.network.packets.ServerToClientPacket;
import net.sevenstars.ofhallsandheralds.OfHallsAndHeralds;
import net.sevenstars.ofhallsandheralds.persistentdatas.PlayerPersistentData;

public class PlayerPersistentDataPacket extends ServerToClientPacket<PlayerPersistentDataPacket> {
    public static final CustomPayload.Id<PlayerPersistentDataPacket> ID =  new Id<>(OfHallsAndHeralds.id("persistent_player_data"));
    public static final PacketCodec<RegistryByteBuf, PlayerPersistentDataPacket> PACKET_CODEC = PacketCodec.tuple(
            PlayerPersistentData.PACKET_CODEC, p -> p.data,
            PlayerPersistentDataPacket::new
    );

    private final PlayerPersistentData data;

    public PlayerPersistentDataPacket(PlayerPersistentData data) {
        this.data = data;
    }

    @Override
    public CustomPayload.Id<PlayerPersistentDataPacket> getId() {
        return ID;
    }

    @Override
    public PacketCodec<RegistryByteBuf, PlayerPersistentDataPacket> streamCodec() {
        return PACKET_CODEC;
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void process(ClientPacketContext context) {
        PlayerEntity player = context.player();
        //controller.open();
    }
}
