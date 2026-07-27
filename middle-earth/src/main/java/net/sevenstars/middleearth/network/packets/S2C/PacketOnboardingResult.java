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

public class PacketOnboardingResult extends ServerToClientPacket<PacketOnboardingResult> {
    public static final Type<PacketOnboardingResult> ID = new Type<>(ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "packet_onboarding_result"));
    public static final StreamCodec<RegistryFriendlyByteBuf, PacketOnboardingResult> CODEC = StreamCodec.composite(
            ByteBufCodecs.BOOL, p -> p.havePlayerData,
            ByteBufCodecs.BOOL, p -> p.canChangeFaction,
            ByteBufCodecs.BOOL, p -> p.canReturnToOverworld,
            ByteBufCodecs.FLOAT, p -> p.delayOnTeleportationConfirm,
            ByteBufCodecs.COMPOUND_TAG, p -> p.attributeList,
            ByteBufCodecs.BOOL, p -> p.offHand,
            PacketOnboardingResult::new
    );

    private final boolean havePlayerData;
    private final boolean canChangeFaction;
    private final boolean canReturnToOverworld;
    private final float delayOnTeleportationConfirm;
    private final CompoundTag attributeList;
    private final boolean offHand;

    public PacketOnboardingResult(boolean havePlayerData, boolean canChangeFaction, boolean canReturnToOverworld, float delayOnTeleportationConfirm, CompoundTag attributeList, boolean offHand) {
        this.havePlayerData = havePlayerData;
        this.canChangeFaction = canChangeFaction;
        this.canReturnToOverworld = canReturnToOverworld;
        this.delayOnTeleportationConfirm = delayOnTeleportationConfirm;
        this.attributeList = attributeList;
        this.offHand = offHand;
    }

    public PacketOnboardingResult(boolean havePlayerData, boolean canChangeFaction, boolean canReturnToOverworld, float delayOnTeleportationConfirm, Player player, boolean offHand) {
        this(havePlayerData, canChangeFaction, canReturnToOverworld, delayOnTeleportationConfirm, AttributePoolElement.createAttributeNbtListFromPlayer(player), offHand);
    }

    @Override
    public Type<PacketOnboardingResult> type() {
        return ID;
    }

    @Override
    public StreamCodec<RegistryFriendlyByteBuf, PacketOnboardingResult> streamCodec() {
        return CODEC;
    }

    public boolean havePlayerData() {
        return havePlayerData;
    }

    public boolean canChangeFaction() {
        return canChangeFaction;
    }

    public boolean canReturnToOverworld() {
        return canReturnToOverworld;
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
