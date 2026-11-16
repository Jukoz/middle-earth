package net.sevenstars.middleearth.resources.datas.biome_events.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.entity.EntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.resources.datas.npc_jobs.NpcJob;
import net.sevenstars.middleearth.resources.datas.npc_classes.NpcClass;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PartyData {
    public static class Fields {
        public static final String MEMBERS = "members";
        public static final String LEADER_JOB = "leader_role";
        public static final String NPC_CLASS = "npc_type";
        public static final String ENTITY_TYPE = "entity_type";
        public static final String JOB = "role";
        public static final String MINIMUM_AMOUNT = "minimum_amount";
        public static final String MAXIMUM_AMOUNT = "maximum_amount";
    }

    public static final Codec<PartyData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.list(Member.CODEC).fieldOf(Fields.MEMBERS).forGetter(PartyData::getMembers),
            Identifier.CODEC.optionalFieldOf(Fields.LEADER_JOB).forGetter(PartyData::getLeaderJob)
    ).apply(instance, PartyData::new));

    private final List<Member> members;
    private Optional<Identifier> leaderJob;

    private PartyData(List<Member> members, Optional<Identifier> leaderJob) {
        this.members = new ArrayList<>(members);
        this.leaderJob = leaderJob;
    }

    public static PartyData create() {
        return new PartyData(List.of(), Optional.empty());
    }

    // Sets the leader's job, such as the swineherd of the pigs behind him
    public PartyData ledBy(RegistryKey<NpcJob> job) {
        this.leaderJob = Optional.of(job.getValue());
        return this;
    }

    public Optional<Identifier> getLeaderJob() {
        return leaderJob;
    }

    public PartyData add(RegistryKey<NpcClass> npcClass, int minimumAmount, int maximumAmount) {
        this.members.add(new Member(Optional.of(npcClass.getValue()), Optional.empty(), Optional.empty(), minimumAmount, maximumAmount));
        return this;
    }

    public PartyData add(RegistryKey<NpcClass> npcClass, RegistryKey<NpcJob> job, int minimumAmount, int maximumAmount) {
        this.members.add(new Member(Optional.of(npcClass.getValue()), Optional.empty(), Optional.of(job.getValue()), minimumAmount, maximumAmount));
        return this;
    }

    // Animals the leader drives along
    public PartyData addAnimals(EntityType<?> entityType, int minimumAmount, int maximumAmount) {
        this.members.add(new Member(Optional.empty(), Optional.of(Registries.ENTITY_TYPE.getId(entityType)), Optional.empty(), minimumAmount, maximumAmount));
        return this;
    }

    public List<Member> getMembers() {
        return members;
    }

    public record Member(Optional<Identifier> npcClass, Optional<Identifier> entityType, Optional<Identifier> job, int minimumAmount, int maximumAmount) {
        public static final Codec<Member> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                Identifier.CODEC.optionalFieldOf(Fields.NPC_CLASS).forGetter(Member::npcClass),
                Identifier.CODEC.optionalFieldOf(Fields.ENTITY_TYPE).forGetter(Member::entityType),
                Identifier.CODEC.optionalFieldOf(Fields.JOB).forGetter(Member::job),
                Codec.INT.fieldOf(Fields.MINIMUM_AMOUNT).forGetter(Member::minimumAmount),
                Codec.INT.fieldOf(Fields.MAXIMUM_AMOUNT).forGetter(Member::maximumAmount)
        ).apply(instance, Member::new));
    }
}
