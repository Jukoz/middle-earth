package net.jukoz.me.network.packets.S2C;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.client.screens.FactionSelectionScreen;
import net.jukoz.me.client.screens.OnboardingSelectionScreen;
import net.jukoz.me.config.ModServerConfigs;
import net.jukoz.me.network.packets.C2S.OnboardingDetailFetchingPacket;
import net.jukoz.me.resources.StateSaverAndLoader;
import net.jukoz.me.world.dimension.ModDimensions;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public record OnboardingDetailParsedPacket(boolean havePlayerData, boolean canChangeFaction, boolean canReturnToOverworld) implements CustomPayload {
    public static final CustomPayload.Id<OnboardingDetailParsedPacket> ID = new CustomPayload.Id<>(Identifier.of(MiddleEarth.MOD_ID, "onboarding_details_parsed_packet"));
    public static final PacketCodec<RegistryByteBuf, OnboardingDetailParsedPacket> CODEC = PacketCodec.tuple(
            PacketCodecs.BOOL, OnboardingDetailParsedPacket::havePlayerData,
            PacketCodecs.BOOL, OnboardingDetailParsedPacket::canChangeFaction,
            PacketCodecs.BOOL, OnboardingDetailParsedPacket::canReturnToOverworld,
            OnboardingDetailParsedPacket::new
    );

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }

    public static void apply(OnboardingDetailParsedPacket packet, ClientPlayNetworking.Context context) {
        MinecraftClient client = context.client();
        client.execute(() -> {
            ClientWorld world = client.world;
            if(ModDimensions.isInOverworld(world)){ //
                if(!packet.havePlayerData){
                    client.setScreen(new FactionSelectionScreen());
                } else {
                    client.setScreen(new OnboardingSelectionScreen(ModServerConfigs.ENABLE_FACTION_RESET));
                }
            }
        });
    }
}
