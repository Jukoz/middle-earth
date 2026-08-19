package net.sevenstars.middleearth.network.packets.S2C;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.network.packets.ServerToClientPacket;

public class InscriptionEnchantInfoPacket extends ServerToClientPacket<InscriptionEnchantInfoPacket> {
    public static final Type<InscriptionEnchantInfoPacket> ID = new Type<>(MiddleEarth.of("inscription_enchant_info_packet"));
    public static final StreamCodec<RegistryFriendlyByteBuf, InscriptionEnchantInfoPacket> CODEC = StreamCodec.composite(
            ByteBufCodecs.INT, p -> p.containerId,
            ByteBufCodecs.INT, p -> p.selectionRevision,
            ByteBufCodecs.STRING_UTF8, p -> p.enchant,
            ByteBufCodecs.INT, p -> p.level,
            ByteBufCodecs.INT, p -> p.maxLevel,
            ByteBufCodecs.BYTE_ARRAY, p -> p.words,
            InscriptionEnchantInfoPacket::new
    );

    private final int containerId;
    private final int selectionRevision;
    private final String enchant;
    private final int level;
    private final int maxLevel;
    private final byte[] words;

    public InscriptionEnchantInfoPacket(int containerId, int selectionRevision, String enchant, int level, int maxLevel, byte[] words) {
        this.containerId = containerId;
        this.selectionRevision = selectionRevision;
        this.enchant = enchant;
        this.level = level;
        this.maxLevel = maxLevel;
        this.words = words.clone();
    }

    @Override
    public Type<InscriptionEnchantInfoPacket> type() {
        return ID;
    }

    @Override
    public StreamCodec<RegistryFriendlyByteBuf, InscriptionEnchantInfoPacket> streamCodec() {
        return CODEC;
    }

    public String enchant() {
        return enchant;
    }

    public int containerId() {
        return containerId;
    }

    public int selectionRevision() {
        return selectionRevision;
    }

    public int level() {
        return level;
    }

    public int maxLevel() {
        return maxLevel;
    }

    public byte[] words() {
        return words;
    }
}
