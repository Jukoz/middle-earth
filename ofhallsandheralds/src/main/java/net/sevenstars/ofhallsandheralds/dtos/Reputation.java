package net.sevenstars.ofhallsandheralds.dtos;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.registry.RegistryKey;
import net.sevenstars.ofhallsandheralds.registries.DynamicRegistriesHH;

public class Reputation {
    public static final Codec<Reputation> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            RegistryKey.createCodec(DynamicRegistriesHH.FACTION).fieldOf("faction").forGetter(Reputation::getFaction),
            Codec.INT.fieldOf("current_level").forGetter(Reputation::getCurrentLevel),
            Codec.BOOL.fieldOf("discovered").forGetter(Reputation::discovered)
    ).apply(instance, Reputation::new));

    public static final PacketCodec<RegistryByteBuf, Reputation> PACKET_CODEC = PacketCodec.tuple(
        RegistryKey.createPacketCodec(DynamicRegistriesHH.FACTION), Reputation::getFaction,
        PacketCodecs.INTEGER, Reputation::getCurrentLevel,
        PacketCodecs.BOOLEAN, Reputation::discovered,
        Reputation::new
    );

    private RegistryKey<Faction> faction;
    private int currentLevel;
    private boolean discovered;

    public Reputation(RegistryKey<Faction> faction, int currentLevel, boolean discovered) {
        this.faction = faction;;
        this.currentLevel = currentLevel;
        this.discovered = discovered;
    }

    public Reputation(RegistryKey<Faction> faction) {
        this.faction = faction;;
        this.currentLevel = 0;
        this.discovered = false;
    }

    private Boolean discovered() {
        return discovered;
    }

    private Integer getCurrentLevel() {
        return currentLevel;
    }

    public RegistryKey<Faction> getFaction() {
        return faction;
    }

    public void decrease(int amount){
        this.currentLevel -= amount;
    }

    public void increase(int amount){
        this.currentLevel += amount;
    }

    public void discover() {
        this.discovered = true;
    }
}
