package net.sevenstars.middleearth.network.packets.C2S;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.block.special.shapingAnvil.ShapingAnvilBlockEntity;
import net.sevenstars.middleearth.gui.artisantable.ArtisanTableScreenHandler;
import net.sevenstars.middleearth.network.contexts.ServerPacketContext;
import net.sevenstars.middleearth.network.packets.ClientToServerPacket;

public class ArtisanIndexPacket extends ClientToServerPacket<ArtisanIndexPacket> {
    public static final Id<ArtisanIndexPacket> ID = new Id<>(MiddleEarth.of("artisan_index_packet"));
    public static final PacketCodec<RegistryByteBuf, ArtisanIndexPacket> CODEC = PacketCodec.tuple(
            PacketCodecs.INTEGER, p -> p.index,
            PacketCodecs.INTEGER, p -> p.syncId,
            ArtisanIndexPacket::new
    );

    public int getIndex() {
        return index;
    }

    public int getSyncId() {
        return syncId;
    }

    private final int index;
    private final int syncId;

    public ArtisanIndexPacket(int index, int syncId) {
        this.index = index;
        this.syncId = syncId;
    }

    @Override
    public Id<ArtisanIndexPacket> getId() {
        return ID;
    }

    @Override
    public PacketCodec<RegistryByteBuf, ArtisanIndexPacket> streamCodec() {
        return CODEC;
    }

    @Override
    public void process(ServerPacketContext context) {
        try{
            context.player().getServer().execute(() -> {
                ServerPlayerEntity player = context.player();
                ScreenHandler screenHandler = player.currentScreenHandler;
                if (screenHandler.syncId == this.syncId && screenHandler instanceof ArtisanTableScreenHandler artisanTableScreenHandler) {
                    artisanTableScreenHandler.updateIndex(index);
                }
            });
        }catch (Exception e){
            MiddleEarth.LOGGER.logError("Artisan Table Packet error: ", e);
        }
    }
}
