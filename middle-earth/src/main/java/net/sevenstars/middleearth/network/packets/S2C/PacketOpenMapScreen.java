package net.sevenstars.middleearth.network.packets.S2C;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.gui.map.MapScreenController;
import net.sevenstars.middleearth.network.contexts.ClientPacketContext;
import net.sevenstars.middleearth.network.packets.ServerToClientPacket;

public class PacketOpenMapScreen extends ServerToClientPacket<PacketOpenMapScreen> {
    public static final Id<PacketOpenMapScreen> ID = new Id<>(MiddleEarth.of("packet_open_map_screen"));
    public static final PacketCodec<RegistryByteBuf, PacketOpenMapScreen> CODEC = PacketCodec.tuple(
            PacketCodecs.BOOLEAN, p -> p.canTeleport,
            PacketOpenMapScreen::new
    );
    private final boolean canTeleport;

    public PacketOpenMapScreen(boolean canTeleport) {
        this.canTeleport = canTeleport;
    }
    @Override
    public Id<PacketOpenMapScreen> getId() {
        return ID;
    }

    @Override
    public PacketCodec<RegistryByteBuf, PacketOpenMapScreen> streamCodec() {
        return CODEC;
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void process(ClientPacketContext context) {
        PlayerEntity player = context.player();
        MapScreenController controller = new MapScreenController(player.getWorld(), player);
        controller.open(canTeleport);
    }
}
