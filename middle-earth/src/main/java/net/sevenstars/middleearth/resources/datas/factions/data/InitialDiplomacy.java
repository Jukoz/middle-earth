package net.sevenstars.middleearth.resources.datas.factions.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.resources.datas.common.AffinityLevel;
import net.sevenstars.middleearth.resources.datas.factions.Faction;

public class InitialDiplomacy {

    public static final Codec<InitialDiplomacy> CODEC = RecordCodecBuilder.create(instance -> instance.group(
        Codec.STRING.fieldOf("faction").forGetter(InitialDiplomacy::getFactionIdentifierForNbt),
        Codec.STRING.fieldOf("affinity").forGetter(InitialDiplomacy::getAffinityForNbt)
    ).apply(instance, InitialDiplomacy::new));

    ResourceLocation factionId;
    AffinityLevel affinity;

    public InitialDiplomacy(String factionId, String affinity) {
        this.factionId = MiddleEarth.fetchId(factionId);
        this.affinity = AffinityLevel.valueOf(affinity);
    }

    public InitialDiplomacy(ResourceKey<Faction> factionRegistryKey, AffinityLevel affinity) {
        this.factionId = factionRegistryKey.location();
        this.affinity = affinity;
    }

    public InitialDiplomacy(Tag nbtElement) {
        CompoundTag compound = (CompoundTag) nbtElement;
        compound.get("faction");
    }

    public String getFactionIdentifierForNbt() {
        return factionId.toString();
    }

    private String getAffinityForNbt() {
        return this.affinity.toString();
    }

    public Tag getNbt() {
        CompoundTag compound = new CompoundTag();
        compound.putString("faction", getFactionIdentifierForNbt());
        compound.putString("affinity", getAffinityForNbt());
        return compound;
    }

    public boolean isHostileToward(ResourceLocation faction) {
        if(faction.equals(factionId)){
            return affinity == AffinityLevel.HOSTILE;
        }
        return false;
    }
}
