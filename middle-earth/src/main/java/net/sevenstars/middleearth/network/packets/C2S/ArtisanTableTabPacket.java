package net.sevenstars.middleearth.network.packets.C2S;

import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.gui.artisantable.ArtisanTableScreenHandler;
import net.sevenstars.middleearth.network.contexts.ServerPacketContext;
import net.sevenstars.middleearth.network.packets.ClientToServerPacket;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public class ArtisanTableTabPacket extends ClientToServerPacket<ArtisanTableTabPacket> {
    public static final Id<ArtisanTableTabPacket> ID = new Id<>(Identifier.of(MiddleEarth.MOD_ID, "artisan_table_tab_packet"));
    public static final PacketCodec<RegistryByteBuf, ArtisanTableTabPacket> CODEC = PacketCodec.tuple(
            PacketCodecs.STRING, p -> p.shapeId,
            PacketCodecs.INTEGER, p -> p.syncId,
            ArtisanTableTabPacket::new
    );

    public String getShapeId() {
        return shapeId;
    }

    public int getSyncId() {
        return syncId;
    }

    private final String shapeId;
    private final int syncId;

    public ArtisanTableTabPacket(String shapeId, int syncId) {
        this.shapeId = shapeId;
        this.syncId = syncId;
    }

    @Override
    public Id<ArtisanTableTabPacket> getId() {
        return ID;
    }

    @Override
    public PacketCodec<RegistryByteBuf, ArtisanTableTabPacket> streamCodec() {
        return CODEC;
    }

    @Override
    public void process(ServerPacketContext context) {
        try{
            context.player().getServer().execute(() -> {
                ServerPlayerEntity player = context.player();
                ScreenHandler screenHandler = player.currentScreenHandler;
                if (screenHandler.syncId == this.syncId && screenHandler instanceof ArtisanTableScreenHandler artisanTableScreenHandler) {
                    artisanTableScreenHandler.changeTab(shapeId);
                }
            });
        }catch (Exception e){
            MiddleEarth.LOGGER.logError("Artisan Table Packet error: ", e);
        }
    }
}
