package net.sevenstars.middleearth.network.packets.server2client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.sevenstars.api.network.contexts.ClientPacketContext;
import net.sevenstars.api.network.packets.ServerToClientPacket;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.config.ClientConfigME;

public class PacketConfigAction extends ServerToClientPacket<PacketConfigAction> {
    public static final Id<PacketConfigAction> ID = new Id<>(MiddleEarth.id("packet_config_action"));
    public static final PacketCodec<RegistryByteBuf, PacketConfigAction> CODEC = PacketCodec.tuple(
            PacketCodecs.BOOLEAN, p -> p.reset,
            PacketConfigAction::new
    );

    private final boolean reset;

    public PacketConfigAction(boolean reset) {
        this.reset = reset;
    }

    @Override
    public Id<PacketConfigAction> getId() {
        return ID;
    }

    @Override
    public PacketCodec<RegistryByteBuf, PacketConfigAction> streamCodec() {
        return CODEC;
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void process(ClientPacketContext context) {
        if (reset) {
            ClientConfigME.reset();
        } else {
            ClientConfigME.reload();
        }
    }
}
