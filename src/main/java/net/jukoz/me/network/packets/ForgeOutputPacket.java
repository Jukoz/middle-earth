package net.jukoz.me.network.packets;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.block.special.forge.ForgeBlockEntity;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record ForgeOutputPacket(int amount, int entityX, int entityZ, int entityY) implements CustomPayload {
    public static final CustomPayload.Id<ForgeOutputPacket> ID = new CustomPayload.Id<>(Identifier.of(MiddleEarth.MOD_ID, "forge_output_packet"));
    public static final PacketCodec<RegistryByteBuf, ForgeOutputPacket> CODEC = PacketCodec.tuple(
            PacketCodecs.INTEGER, ForgeOutputPacket::amount,
            PacketCodecs.INTEGER, ForgeOutputPacket::entityX,
            PacketCodecs.INTEGER, ForgeOutputPacket::entityZ,
            PacketCodecs.INTEGER, ForgeOutputPacket::entityY,
            ForgeOutputPacket::new
    );

    public static void apply(ForgeOutputPacket packet, ServerPlayNetworking.Context context) {
        context.player().getServer().execute(() -> {
            ForgeBlockEntity.lowerAmount(packet, context);
        });
    }

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
