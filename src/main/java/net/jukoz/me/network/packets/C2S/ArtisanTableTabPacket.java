package net.jukoz.me.network.packets.C2S;

import net.jukoz.me.MiddleEarth;
import net.jukoz.me.block.special.shapingAnvil.AbstractShapingAnvilBlockEntity;
import net.jukoz.me.gui.artisantable.ArtisanTableScreenHandler;
import net.jukoz.me.network.contexts.ServerPacketContext;
import net.jukoz.me.network.packets.ClientToServerPacket;
import net.jukoz.me.utils.LoggerUtil;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.listener.ServerPlayPacketListener;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;

public class ArtisanTableTabPacket extends ClientToServerPacket<ArtisanTableTabPacket> {
    public static final Id<ArtisanTableTabPacket> ID = new Id<>(Identifier.of(MiddleEarth.MOD_ID, "artisan_table_tab_packet"));
    public static final PacketCodec<RegistryByteBuf, ArtisanTableTabPacket> CODEC = PacketCodec.tuple(
            PacketCodecs.INTEGER, p -> p.category,
            PacketCodecs.INTEGER, p -> p.tab,
            PacketCodecs.INTEGER, p -> p.syncId,
            ArtisanTableTabPacket::new
    );

    public int getCategory() {
        return category;
    }

    public int getTab() {
        return tab;
    }

    public int getSyncId() {
        return syncId;
    }

    private final int category;
    private final int tab;
    private final int syncId;

    public ArtisanTableTabPacket(int category, int tab, int syncId) {
        this.category = category;
        this.tab = tab;
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
                    artisanTableScreenHandler.changeTab();
                }
            });
        }catch (Exception e){
            LoggerUtil.logError("Artisan Table Packet error: ", e);
        }
    }
}
