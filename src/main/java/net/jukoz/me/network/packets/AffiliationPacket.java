package net.jukoz.me.network.packets;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.resources.StateSaverAndLoader;
import net.jukoz.me.resources.persistent_datas.AffiliationData;
import net.jukoz.me.resources.persistent_datas.PlayerData;
import net.jukoz.me.resources.datas.Alignment;
import net.jukoz.me.utils.LoggerUtil;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public record AffiliationPacket(int alignment, int faction, int subfaction) implements CustomPayload
{
    public static final CustomPayload.Id<AffiliationPacket> ID = new CustomPayload.Id<>(Identifier.of(MiddleEarth.MOD_ID, "affiliation_packet"));

    public static final PacketCodec<RegistryByteBuf, AffiliationPacket> CODEC = PacketCodec.tuple(
            PacketCodecs.INTEGER, AffiliationPacket::alignment,
            PacketCodecs.INTEGER, AffiliationPacket::faction,
            PacketCodecs.INTEGER, AffiliationPacket::subfaction,
            AffiliationPacket::new
    );

    public static void apply(AffiliationPacket packet, ServerPlayNetworking.Context context) {
        context.player().getServer().execute(() -> {
            MinecraftServer server = context.server();
            ServerPlayerEntity player = server.getPlayerManager().getPlayer(context.player().getUuid());

            StateSaverAndLoader serverState = StateSaverAndLoader.getServerState(server);

            PlayerData playerState = StateSaverAndLoader.getPlayerState(player);
            LoggerUtil.logDebugMsg(String.valueOf(serverState.getPlayerTotal(Alignment.GOOD)));

            AffiliationData affiliationData = new AffiliationData( packet.alignment, packet.faction, packet.subfaction);
            playerState.setAffiliationData(affiliationData);

            server.execute(() -> {
                ServerPlayNetworking.send(player, packet);
            });
        });
    }
    public static void apply(AffiliationPacket packet, ClientPlayNetworking.Context context) {
        context.client().execute(() -> {
            AffiliationData affiliationData = new AffiliationData(packet.alignment, packet.faction, packet.subfaction);

            PlayerData data = new PlayerData(affiliationData);
            context.client().player.sendMessage(Text.of("Data have been received > "+ data));
        });
    }

    @Override
    public CustomPayload.Id<? extends CustomPayload> getId()
    {
        return ID;
    }

    public Alignment getAlignment(){
        return Alignment.values()[alignment];
    }
}