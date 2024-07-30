package net.jukoz.me.network.packets;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.StateSaverAndLoader;
import net.jukoz.me.resource.PlayerData;
import net.jukoz.me.resource.data.Alignment;
import net.jukoz.me.world.dimension.ModDimensions;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.joml.Vector3i;

public record IdentityPacket(int alignment, int faction, int subfaction) implements CustomPayload
{
    public static final CustomPayload.Id<IdentityPacket> ID = new CustomPayload.Id<>(Identifier.of(MiddleEarth.MOD_ID, "identity_packet"));

    public static final PacketCodec<RegistryByteBuf, IdentityPacket> CODEC = PacketCodec.tuple(
            PacketCodecs.INTEGER, IdentityPacket::alignment,
            PacketCodecs.INTEGER, IdentityPacket::faction,
            PacketCodecs.INTEGER, IdentityPacket::subfaction,
            IdentityPacket::new
    );

    public static void apply(IdentityPacket packet, ServerPlayNetworking.Context context) {
        context.player().getServer().execute(() -> {
            StateSaverAndLoader serverState = StateSaverAndLoader.getServerState(context.server());
            serverState.setWorldSpawn = true;
            serverState.getPlayerTotal(Alignment.GOOD);


            PlayerData playerState = StateSaverAndLoader.getPlayerState(context.player());
            playerState.alignment = packet.alignment();
            playerState.faction = packet.faction;
            playerState.subfaction = packet.subfaction;

            // Send a packet to the client
            MinecraftServer server = context.server();

            ServerPlayerEntity playerEntity = server.getPlayerManager().getPlayer(context.player().getUuid());
            server.execute(() -> {
                ServerPlayNetworking.send(playerEntity, packet);
            });

            context.server().execute(() -> {
                ServerPlayerEntity entity = context.player();
                ServerPlayNetworking.send(entity, packet);
            });
        });
    }
    public static void apply(IdentityPacket packet, ClientPlayNetworking.Context context) {
        context.client().execute(() -> {
            context.client().player.sendMessage(Text.literal("Alignment: " + packet.getAlignment()));
            context.client().player.sendMessage(Text.literal("Faction: " + packet.faction));
            context.client().player.sendMessage(Text.literal("Alignment: " + packet.subfaction));
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