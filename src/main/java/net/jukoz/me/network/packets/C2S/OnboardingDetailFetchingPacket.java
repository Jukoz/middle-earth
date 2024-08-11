package net.jukoz.me.network.packets.C2S;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.config.ModServerConfigs;
import net.jukoz.me.network.packets.S2C.OnboardingDetailParsedPacket;
import net.jukoz.me.resources.StateSaverAndLoader;
import net.jukoz.me.world.dimension.ModDimensions;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public record OnboardingDetailFetchingPacket(boolean havePlayerData, boolean canChangeFaction, boolean canReturnToOverworld) implements CustomPayload
{
    public static final CustomPayload.Id<OnboardingDetailFetchingPacket> ID = new CustomPayload.Id<>(Identifier.of(MiddleEarth.MOD_ID, "onboarding_details_fetching_packet"));
    public static final PacketCodec<RegistryByteBuf, OnboardingDetailFetchingPacket> CODEC = PacketCodec.tuple(
            PacketCodecs.BOOL, OnboardingDetailFetchingPacket::havePlayerData,
            PacketCodecs.BOOL, OnboardingDetailFetchingPacket::canChangeFaction,
            PacketCodecs.BOOL, OnboardingDetailFetchingPacket::canReturnToOverworld,
            OnboardingDetailFetchingPacket::new
    );

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }

    public static void apply(OnboardingDetailFetchingPacket packet, ServerPlayNetworking.Context context) {
        context.player().getServer().execute(() -> {
            MinecraftServer server = context.server();
            ServerPlayerEntity player = context.player();

            OnboardingDetailParsedPacket newPacket = new OnboardingDetailParsedPacket(
                    StateSaverAndLoader.getPlayerState(context.player()).hasAffilition(),
                    ModServerConfigs.ENABLE_FACTION_RESET,
                    ModServerConfigs.ENABLE_RETURN_TO_OVERWORLD
            );

            server.execute(() -> {
                if(ModServerConfigs.ENABLE_RETURN_TO_OVERWORLD && ModDimensions.isInMiddleEarth(player.getWorld())){
                    ModDimensions.teleportPlayerToOverworld(context.player());
                    return;
                }
                ServerPlayNetworking.send(player, newPacket);
            });
        });
    }
}
