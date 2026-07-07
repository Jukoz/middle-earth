package net.sevenstars.middleearth.resources.datas.factions.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.resources.datas.common.AffinityLevel;
import net.sevenstars.middleearth.resources.datas.factions.Faction;

public class InitialDiplomacy {

    public static final Codec<InitialDiplomacy> CODEC = RecordCodecBuilder.create(instance -> instance.group(
        Codec.STRING.fieldOf("faction").forGetter(InitialDiplomacy::getFactionIdentifierForNbt),
        Codec.STRING.fieldOf("affinity").forGetter(InitialDiplomacy::getAffinityForNbt)
    ).apply(instance, InitialDiplomacy::new));

    Identifier factionId;
    AffinityLevel affinity;

    public InitialDiplomacy(String factionId, String affinity) {
        this.factionId = MiddleEarth.fetchId(factionId);
        this.affinity = AffinityLevel.valueOf(affinity);
    }

    public InitialDiplomacy(RegistryKey<Faction> factionRegistryKey, AffinityLevel affinity) {
        this.factionId = factionRegistryKey.getValue();
        this.affinity = affinity;
    }

    public InitialDiplomacy(NbtElement nbtElement) {
        NbtCompound compound = nbtElement.asCompound().get();
        compound.get("faction");
    }

    public String getFactionIdentifierForNbt() {
        return factionId.toString();
    }

    private String getAffinityForNbt() {
        return this.affinity.toString();
    }

    public NbtElement getNbt() {
        NbtCompound compound = new NbtCompound();
        compound.putString("faction", getFactionIdentifierForNbt());
        compound.putString("affinity", getAffinityForNbt());
        return compound;
    }

    public boolean isHostileToward(Identifier faction) {
        if(faction.equals(factionId)){
            return affinity == AffinityLevel.HOSTILE;
        }
        return false;
    }
}
