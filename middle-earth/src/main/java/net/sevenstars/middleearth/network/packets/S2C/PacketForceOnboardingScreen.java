package net.sevenstars.middleearth.network.packets.S2C;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.network.packets.ServerToClientPacket;
import net.sevenstars.middleearth.resources.datas.attributes.AttributePoolElement;

public class PacketForceOnboardingScreen extends ServerToClientPacket<PacketForceOnboardingScreen> {
    public static final Type<PacketForceOnboardingScreen> ID = new Type<>(ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "packet_force_onboarding_screen"));
    public static final StreamCodec<RegistryFriendlyByteBuf, PacketForceOnboardingScreen> CODEC = StreamCodec.composite(
            ByteBufCodecs.FLOAT, p -> p.delayOnTeleportationConfirm,
            ByteBufCodecs.COMPOUND_TAG, p -> p.attributeList,
            ByteBufCodecs.BOOL, p -> p.offHand,
            PacketForceOnboardingScreen::new
    );
    private final float delayOnTeleportationConfirm;
    private final CompoundTag attributeList;
    private final boolean offHand;

    public PacketForceOnboardingScreen(float delayOnTeleportationConfirm, CompoundTag attributeList, boolean offHand) {
        this.delayOnTeleportationConfirm = delayOnTeleportationConfirm;
        this.attributeList = attributeList;
        this.offHand = offHand;
    }

    public PacketForceOnboardingScreen(float delayOnTeleportationConfirm, Player player) {
        this(delayOnTeleportationConfirm, AttributePoolElement.createAttributeNbtListFromPlayer(player), false);
    }

    @Override
    public Type<PacketForceOnboardingScreen> type() {
        return ID;
    }

    @Override
    public StreamCodec<RegistryFriendlyByteBuf, PacketForceOnboardingScreen> streamCodec() {
        return CODEC;
    }

    public float delayOnTeleportationConfirm() {
        return delayOnTeleportationConfirm;
    }

    public CompoundTag attributeList() {
        return attributeList;
    }

    public boolean offHand() {
        return offHand;
    }
}
