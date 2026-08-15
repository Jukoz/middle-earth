package net.sevenstars.middleearth.network.packets.C2S;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.network.contexts.ServerPacketContext;
import net.sevenstars.middleearth.network.handlers.OnboardingReturnResult;
import net.sevenstars.middleearth.network.handlers.OnboardingServerHandler;
import net.sevenstars.middleearth.network.packets.ClientToServerPacket;
import net.sevenstars.middleearth.network.packets.S2C.PacketReturnToOverworldResult;


public class PacketTeleportToCurrentOverworldSpawn extends ClientToServerPacket<PacketTeleportToCurrentOverworldSpawn> {
    public static final Type<PacketTeleportToCurrentOverworldSpawn> ID = new Type<>(ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "packet_teleport_to_current_overworld_spawn"));
    public static final StreamCodec<RegistryFriendlyByteBuf, PacketTeleportToCurrentOverworldSpawn> CODEC = StreamCodec.composite(
            ByteBufCodecs.BOOL, PacketTeleportToCurrentOverworldSpawn::offHand,
            PacketTeleportToCurrentOverworldSpawn::new
    );
    private final boolean offHand;

    public PacketTeleportToCurrentOverworldSpawn() {
        this(false);
    }

    public PacketTeleportToCurrentOverworldSpawn(boolean offHand) {
        this.offHand = offHand;
    }

    @Override
    public Type<PacketTeleportToCurrentOverworldSpawn> type() {
        return ID;
    }

    @Override
    public StreamCodec<RegistryFriendlyByteBuf, PacketTeleportToCurrentOverworldSpawn> streamCodec() {
        return CODEC;
    }



    @Override
    public void process(ServerPacketContext context) {
        var result = OnboardingReturnResult.failure(
                OnboardingReturnResult.Status.INTERNAL_ERROR
        );
        try{
            result = OnboardingServerHandler.returnToOverworld(
                    context.player(),
                    offHand ? InteractionHand.OFF_HAND : InteractionHand.MAIN_HAND
            );
        } catch (Exception e){
            MiddleEarth.LOGGER.logError("PacketTeleportToCurrentOverworldSpawn::Apply - Tried applying the return to overworld packet",e);
        }
        context.connection().sendPacketToClient(
                new PacketReturnToOverworldResult(result),
                context.player()
        );
    }

    public boolean offHand() {
        return offHand;
    }
}
