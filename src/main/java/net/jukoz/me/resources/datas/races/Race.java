package net.jukoz.me.resources.datas.races;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.jukoz.me.entity.ModEntities;
import net.jukoz.me.entity.NpcEntity;
import net.jukoz.me.entity.dwarves.longbeards.LongbeardDwarfEntity;
import net.jukoz.me.entity.elves.galadhrim.GaladhrimElfEntity;
import net.jukoz.me.entity.hobbits.shire.ShireHobbitEntity;
import net.jukoz.me.entity.humans.bandit.BanditHumanEntity;
import net.jukoz.me.entity.humans.gondor.GondorHumanEntity;
import net.jukoz.me.entity.orcs.mordor.MordorOrcEntity;
import net.jukoz.me.entity.uruks.mordor.MordorBlackUrukEntity;
import net.jukoz.me.resources.datas.RaceType;
import net.jukoz.me.resources.datas.races.data.AttributeData;
import net.jukoz.me.utils.IdentifierUtil;
import net.jukoz.me.utils.LoggerUtil;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Race {
    public static final Codec<Race> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.STRING.fieldOf("id").forGetter(Race::getIdValue),
            Codec.STRING.fieldOf("type").forGetter(Race::getRaceTypeValue),
            NbtCompound.CODEC.fieldOf("attributes").forGetter(Race::getAttributeDatas),
            Codec.list(Codec.STRING, 0, 5).optionalFieldOf("command_join").forGetter(Race::getJoinCommands),
            Codec.list(Codec.STRING, 0, 5).optionalFieldOf("command_leave").forGetter(Race::getLeaveCommands)
    ).apply(instance, Race::new));

    private final Identifier id;
    private final RaceType raceType;
    private final String translatableKey;
    private final AttributeData attributeData;
    private List<String> joinCommands;
    private List<String> leaveCommands;

    public Race(String id, String raceTypeValue, NbtCompound attributes, Optional<List<String>> joinCommands, Optional<List<String>> leaveCommands){
        // Create id
        this.id = IdentifierUtil.getIdentifierFromString(id);
        this.translatableKey = "race.".concat(this.id.toTranslationKey());
        // Create model
        this.raceType = RaceType.valueOf(raceTypeValue.toUpperCase());
        // Attribute Datas
        this.attributeData = new AttributeData(attributes);
        // Join commands
        this.joinCommands = new ArrayList<>();
        joinCommands.ifPresent(nbtCompound -> this.joinCommands.addAll(nbtCompound));
        // Leave commands
        this.leaveCommands = new ArrayList<>();
        leaveCommands.ifPresent(nbtCompound -> this.leaveCommands.addAll(nbtCompound));
    }

    public Race(Identifier id, RaceType raceType, AttributeData attributeData, List<String> joinCommands, List<String> leaveCommands) {
        this.id = id;
        this.raceType = raceType;
        this.translatableKey = "race.".concat(this.id.toTranslationKey());
        this.attributeData = attributeData;
        this.joinCommands = joinCommands;
        this.leaveCommands = leaveCommands;
    }

    public Identifier getId() {
        return id;
    }
    private String getIdValue() {
        return this.id.toString();
    }
    private String getRaceTypeValue() {
        return raceType.toString().toUpperCase();
    }
    private NbtCompound getAttributeDatas() {
        if(attributeData == null)
            return null;
        return attributeData.getNbt();
    }
    public Optional<List<String>> getJoinCommands() {
        if(this.joinCommands == null)
            return Optional.empty();
        return Optional.of(this.joinCommands);
    }
    public Optional<List<String>> getLeaveCommands() {
        if(this.leaveCommands == null)
            return Optional.empty();
        return Optional.of(this.leaveCommands);
    }

    public MutableText getFullName() {
        return Text.translatable(translatableKey);
    }

    public LivingEntity getModel(World world) {
        NpcEntity entity;
        switch (raceType){
            case RaceType.HUMAN:
                entity = new GondorHumanEntity(ModEntities.GONDORIAN_SOLDIER, world);
                break;
            case RaceType.DWARF:
                entity = new LongbeardDwarfEntity(ModEntities.LONGBEARD_SOLDIER, world);
                break;
            case RaceType.HOBBIT:
                entity = new ShireHobbitEntity(ModEntities.HOBBIT_SHIRRIFF, world);
                break;
            case RaceType.ELF:
                entity = new GaladhrimElfEntity(ModEntities.LORIEN_LEADER, world);
                break;
            case RaceType.ORC:
                entity = new MordorOrcEntity(ModEntities.MORDOR_ORC_SOLDIER, world);
                break;
            case RaceType.URUK:
                entity = new MordorBlackUrukEntity(ModEntities.MORDOR_BLACK_URUK_SOLDIER, world);
                break;
            default:
                entity = new BanditHumanEntity(ModEntities.BANDIT_SOLDIER, world);
                break;
        }
        entity.setAiDisabled(true);
        return entity;
    }

    public void applyAttributes(ServerPlayerEntity playerEntity){
        attributeData.ApplyAll(playerEntity);
        playerEntity.heal(playerEntity.getMaxHealth());
    }

    public void reverseAttributes(ServerPlayerEntity playerEntity){
        attributeData.ReverseAll(playerEntity);
        playerEntity.heal(playerEntity.getMaxHealth());
    }

    public String getTranslatableKey() {
        return translatableKey;
    }

    public RaceType getRaceType() {
        return raceType;
    }
}
