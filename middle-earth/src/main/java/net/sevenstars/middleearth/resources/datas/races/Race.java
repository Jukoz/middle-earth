package net.sevenstars.middleearth.resources.datas.races;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.entity.npcs.NpcEntity;
import net.sevenstars.middleearth.resources.datas.common.RaceType;
import net.sevenstars.middleearth.resources.datas.attributes.AttributePool;
import net.sevenstars.middleearth.resources.datas.common.EntityCategories;

import java.util.*;

public class Race {
    private static final String ID_FIELD = "id";
    private static final String TYPE_FIELD = "type";
    private static final String BASE_ATTRIBUTE_FIELD = "base_attributes";
    private static final String CATEGORY_BASED_ATTRIBUTE_FIELD = "category_based_attributes";
    private static final String COMMAND_JOIN_FIELD = "command_join";
    private static final String COMMAND_LEAVE_FIELD = "command_leave";

    public static final Codec<Race> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.STRING.fieldOf(ID_FIELD).forGetter(Race::getIdValue),
            Codec.STRING.fieldOf(TYPE_FIELD).forGetter(Race::getRaceTypeValue),
            CompoundTag.CODEC.fieldOf(BASE_ATTRIBUTE_FIELD).forGetter(Race::getBaseAttributePoolNbt),
            CompoundTag.CODEC.fieldOf(CATEGORY_BASED_ATTRIBUTE_FIELD).forGetter(Race::getCategoryBasedAttributePool),
            Codec.list(Codec.STRING, 0, 5).optionalFieldOf(COMMAND_JOIN_FIELD).forGetter(Race::getJoinCommands),
            Codec.list(Codec.STRING, 0, 5).optionalFieldOf(COMMAND_LEAVE_FIELD).forGetter(Race::getLeaveCommands)
    ).apply(instance, Race::new));

    private final ResourceLocation id;
    private final RaceType raceType;
    private final String translatableKey;
    private final AttributePool baseAttributePool;
    private final HashMap<EntityCategories, AttributePool> categoryBasedAttributePool;
    private final List<String> joinCommands;
    private final List<String> leaveCommands;


    public Race(String id, String raceTypeValue, CompoundTag baseAttributes, CompoundTag categoryBasedAttributes, Optional<List<String>> joinCommands, Optional<List<String>> leaveCommands){
        // Create id
        this.id = MiddleEarth.fetchId(id);
        this.translatableKey = "race.".concat(this.id.toLanguageKey());
        // Create model
        this.raceType = RaceType.valueOf(raceTypeValue.toUpperCase());
        // Attribute Datas
        this.baseAttributePool = new AttributePool(baseAttributes);
        this.categoryBasedAttributePool = new HashMap<>();
        // new AttributePool(categoryBasedAttributes);
        for(var category : EntityCategories.values()){
            if(categoryBasedAttributes.contains(category.name())){
                this.categoryBasedAttributePool.put(category, new AttributePool(categoryBasedAttributes.getCompound(category.name())));
            }
        }

        // Join commands
        this.joinCommands = new ArrayList<>();
        joinCommands.ifPresent(this.joinCommands::addAll);
        // Leave commands
        this.leaveCommands = new ArrayList<>();
        leaveCommands.ifPresent(this.leaveCommands::addAll);
    }

    public Race(ResourceLocation id, RaceType raceType, AttributePool baseAttributePool, HashMap<EntityCategories, AttributePool> categoryBasedAttributePool, List<String> joinCommands, List<String> leaveCommands) {
        this.id = id;
        this.raceType = raceType;
        this.translatableKey = "race.".concat(this.id.toLanguageKey());
        this.baseAttributePool = baseAttributePool;
        this.categoryBasedAttributePool = categoryBasedAttributePool;
        this.joinCommands = joinCommands;
        this.leaveCommands = leaveCommands;
    }

    public ResourceLocation getId() {
        return id;
    }

    private String getIdValue() {
        return this.id.toString();
    }

    private String getRaceTypeValue() {
        return raceType.toString().toUpperCase();
    }

    private CompoundTag getBaseAttributePoolNbt() {
        if(baseAttributePool == null)
            return null;
        return baseAttributePool.getNbt();
    }
    public AttributePool getBaseAttributePool() {
        return baseAttributePool;
    }

    private CompoundTag getCategoryBasedAttributePool() {
        if(categoryBasedAttributePool == null)
            return null;
        var nbt = new CompoundTag();
        for(var category : categoryBasedAttributePool.keySet()){
            nbt.put(category.name(), categoryBasedAttributePool.get(category).getNbt());
        }
        return nbt;
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

    public MutableComponent getFullName() {
        return Component.translatable(translatableKey);
    }

    public void applyPlayerAttributes(Player playerEntity){
        baseAttributePool.apply(playerEntity);
    }

    public void reverseAttributes(Player playerEntity){
        AttributePool.reverse(playerEntity);
    }

    public String getTranslatableKey() {
        return translatableKey;
    }

    public RaceType getRaceType() {
        return raceType;
    }

    public void applyNpcAttributes(NpcEntity npcEntity) {
        AttributePool.reverse(npcEntity);
        baseAttributePool.apply(npcEntity);
        categoryBasedAttributePool.get(EntityCategories.SHARED).apply(npcEntity);
        EntityCategories category = npcEntity.getNpcCategory();
        if(category != null)
            categoryBasedAttributePool.get(category).apply(npcEntity);
    }
}
