package net.sevenstars.middleearth.network.packets.S2C;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.client.screens.ReturnConfirmationScreen;
import net.sevenstars.middleearth.network.contexts.ClientPacketContext;
import net.sevenstars.middleearth.network.handlers.OnboardingScreenHandler;
import net.sevenstars.middleearth.network.packets.ServerToClientPacket;
import net.sevenstars.middleearth.world.dimension.ModDimensions;
import net.minecraft.client.MinecraftClient;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.util.Identifier;

public class PacketOnboardingResult extends ServerToClientPacket<PacketOnboardingResult> {
    public static final Id<PacketOnboardingResult> ID = new Id<>(Identifier.of(MiddleEarth.MOD_ID, "packet_onboarding_result"));
    public static final PacketCodec<RegistryByteBuf, PacketOnboardingResult> CODEC = PacketCodec.tuple(
            PacketCodecs.BOOL, p -> p.havePlayerData,
            PacketCodecs.BOOL, p -> p.canChangeFaction,
            PacketCodecs.BOOL, p -> p.canReturnToOverworld,
            PacketCodecs.FLOAT, p -> p.delayOnTeleportationConfirm,
            PacketOnboardingResult::new
    );

    private final boolean havePlayerData;
    private final boolean canChangeFaction;
    private final boolean canReturnToOverworld;
    private final float delayOnTeleportationConfirm;

    public PacketOnboardingResult(boolean havePlayerData, boolean canChangeFaction, boolean canReturnToOverworld, float delayOnTeleportationConfirm) {
        this.havePlayerData = havePlayerData;
        this.canChangeFaction = canChangeFaction;
        this.canReturnToOverworld = canReturnToOverworld;
        this.delayOnTeleportationConfirm = delayOnTeleportationConfirm;
    }

    @Override
    public Id<PacketOnboardingResult> getId() {
        return ID;
    }

    @Override
    public PacketCodec<RegistryByteBuf, PacketOnboardingResult> streamCodec() {
        return CODEC;
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void process(ClientPacketContext context) {
        float delay = delayOnTeleportationConfirm;
        if(context.player().isInCreativeMode())
            delay = 0;
        if(ModDimensions.isInMiddleEarth(context.player().getWorld())){
            if(!canReturnToOverworld){
                return;
            }
            MinecraftClient client = MinecraftClient.getInstance();
            client.setScreen(new ReturnConfirmationScreen(delay));
        } else if(ModDimensions.isInOverworld(context.player().getWorld())){
            OnboardingScreenHandler.handle(context, havePlayerData, delay);
        }
    }
}
