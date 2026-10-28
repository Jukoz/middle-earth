package net.sevenstars.middleearth.network.packets.server2client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.gui.onboarding.onboarding_faction.OnboardingFactionScreenController;
import net.sevenstars.api.network.contexts.ClientPacketContext;
import net.sevenstars.api.network.packets.ServerToClientPacket;
import net.sevenstars.middleearth.resources.datas.attributes.AttributePoolElement;

public class PacketForceOnboardingScreen extends ServerToClientPacket<PacketForceOnboardingScreen> {
    public static final Id<PacketForceOnboardingScreen> ID = new Id<>(Identifier.of(MiddleEarth.MOD_ID, "packet_force_onboarding_screen"));
    public static final PacketCodec<RegistryByteBuf, PacketForceOnboardingScreen> CODEC = PacketCodec.tuple(
            PacketCodecs.FLOAT, p -> p.delayOnTeleportationConfirm,
            PacketCodecs.NBT_COMPOUND, p -> p.attributeList,
            PacketForceOnboardingScreen::new
    );
    private final float delayOnTeleportationConfirm;
    private final NbtCompound attributeList;

    public PacketForceOnboardingScreen(float delayOnTeleportationConfirm, NbtCompound attributeList) {
        this.delayOnTeleportationConfirm = delayOnTeleportationConfirm;
        this.attributeList = attributeList;
    }

    public PacketForceOnboardingScreen(float delayOnTeleportationConfirm, PlayerEntity player) {
        this.delayOnTeleportationConfirm = delayOnTeleportationConfirm;
        this.attributeList = AttributePoolElement.createAttributeNbtListFromPlayer(player);
    }

    @Override
    public Id<PacketForceOnboardingScreen> getId() {
        return ID;
    }

    @Override
    public PacketCodec<RegistryByteBuf, PacketForceOnboardingScreen> streamCodec() {
        return CODEC;
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void process(ClientPacketContext context) {
        float delay = delayOnTeleportationConfirm;
        if(context.player().isInCreativeMode())
            delay = 0;

        PlayerEntity player = context.player();
        var controller = new OnboardingFactionScreenController(player.getWorld(), delay, AttributePoolElement.obtainAttributeList(attributeList));
        controller.open();
    }
}
