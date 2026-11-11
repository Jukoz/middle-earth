package net.sevenstars.middleearth.resources.datas.biome_events.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.resources.datas.npc_types.NpcType;

import java.util.ArrayList;
import java.util.List;

public class PartyData {
    public static class Fields {
        public static final String MEMBERS = "members";
        public static final String NPC_TYPE = "npc_type";
        public static final String MINIMUM_AMOUNT = "minimum_amount";
        public static final String MAXIMUM_AMOUNT = "maximum_amount";
    }

    public static final Codec<PartyData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.list(Member.CODEC).fieldOf(Fields.MEMBERS).forGetter(PartyData::getMembers)
    ).apply(instance, PartyData::new));

    private final List<Member> members;

    private PartyData(List<Member> members) {
        this.members = new ArrayList<>(members);
    }

    public static PartyData create() {
        return new PartyData(List.of());
    }

    public PartyData add(RegistryKey<NpcType> npcType, int minimumAmount, int maximumAmount) {
        this.members.add(new Member(npcType.getValue(), minimumAmount, maximumAmount));
        return this;
    }

    public List<Member> getMembers() {
        return members;
    }

    public record Member(Identifier npcType, int minimumAmount, int maximumAmount) {
        public static final Codec<Member> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                Identifier.CODEC.fieldOf(Fields.NPC_TYPE).forGetter(Member::npcType),
                Codec.INT.fieldOf(Fields.MINIMUM_AMOUNT).forGetter(Member::minimumAmount),
                Codec.INT.fieldOf(Fields.MAXIMUM_AMOUNT).forGetter(Member::maximumAmount)
        ).apply(instance, Member::new));
    }
}
