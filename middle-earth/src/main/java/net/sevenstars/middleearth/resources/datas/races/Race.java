package net.sevenstars.middleearth.resources.datas.races;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.entity.ModEntities;
import net.sevenstars.middleearth.entity.npcs.NpcEntity;
import net.sevenstars.middleearth.resources.datas.RaceType;
import net.sevenstars.middleearth.resources.datas.attributes.AttributePool;
import net.sevenstars.middleearth.resources.datas.attributes.AttributePoolElement;
import net.sevenstars.middleearth.resources.datas.races.data.EntityCategory;
import net.sevenstars.middleearth.utils.IdentifierUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class Race {
    public static final Codec<Race> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.STRING.fieldOf("id").forGetter(Race::getIdValue),
            Codec.STRING.fieldOf("type").forGetter(Race::getRaceTypeValue),
            NbtCompound.CODEC.fieldOf("player_attributes").forGetter(Race::getPlayerAttributePool),
            NbtCompound.CODEC.fieldOf("npc_attributes").forGetter(Race::getNpcAttributePool),
            Codec.list(Codec.STRING, 0, 5).optionalFieldOf("command_join").forGetter(Race::getJoinCommands),
            Codec.list(Codec.STRING, 0, 5).optionalFieldOf("command_leave").forGetter(Race::getLeaveCommands)
    ).apply(instance, Race::new));

    private final Identifier id;
    private final RaceType raceType;
    private final String translatableKey;
    private final AttributePool playerAttributePool;
    private final HashMap<EntityCategory, AttributePool> npcAttributePools;
    private List<String> joinCommands;
    private List<String> leaveCommands;


    public Race(String id, String raceTypeValue, NbtCompound playerAttributes, NbtCompound npcAttributes, Optional<List<String>> joinCommands, Optional<List<String>> leaveCommands){
        // Create id
        this.id = IdentifierUtil.getIdentifierFromString(id);
        this.translatableKey = "race.".concat(this.id.toTranslationKey());
        // Create model
        this.raceType = RaceType.valueOf(raceTypeValue.toUpperCase());
        // Attribute Datas
        this.playerAttributePool = new AttributePool(playerAttributes);
        this.npcAttributePools = new HashMap<>();
        // new AttributePool(npcAttributes);
        for(var category : EntityCategory.values()){
            if(npcAttributes.contains(category.name())){
                this.npcAttributePools.put(category, new AttributePool(npcAttributes.getCompound(category.name()).get()));
            }
        }

        // Join commands
        this.joinCommands = new ArrayList<>();
        joinCommands.ifPresent(nbtCompound -> this.joinCommands.addAll(nbtCompound));
        // Leave commands
        this.leaveCommands = new ArrayList<>();
        leaveCommands.ifPresent(nbtCompound -> this.leaveCommands.addAll(nbtCompound));
    }

    public Race(Identifier id, RaceType raceType, AttributePool playerAttributePool, HashMap<EntityCategory, AttributePool> npcAttributePools, List<String> joinCommands, List<String> leaveCommands) {
        this.id = id;
        this.raceType = raceType;
        this.translatableKey = "race.".concat(this.id.toTranslationKey());
        this.playerAttributePool = playerAttributePool;
        this.npcAttributePools = npcAttributePools;
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
    private NbtCompound getPlayerAttributePool() {
        if(playerAttributePool == null)
            return null;
        return playerAttributePool.getNbt();
    }
    private NbtCompound getNpcAttributePool() {
        if(npcAttributePools == null)
            return null;
        var nbt = new NbtCompound();
        for(var category : npcAttributePools.keySet()){
            nbt.put(category.name(), npcAttributePools.get(category).getNbt());
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

    public MutableText getFullName() {
        return Text.translatable(translatableKey);
    }

    public LivingEntity getModel(World world) {
        NpcEntity entity = switch (raceType) {
            case RaceType.HUMAN -> new NpcEntity(ModEntities.NPC, world);
            case RaceType.DWARF -> new NpcEntity(ModEntities.NPC, world);
            case RaceType.HOBBIT -> new NpcEntity(ModEntities.NPC, world);
            case RaceType.ELF -> new NpcEntity(ModEntities.NPC, world);
            case RaceType.ORC -> new NpcEntity(ModEntities.NPC, world);
            case RaceType.URUK -> new NpcEntity(ModEntities.NPC, world);
            default -> new NpcEntity(ModEntities.NPC, world);
        };
        entity.setAiDisabled(true);
        return entity;
    }

    public void applyPlayerAttributes(PlayerEntity playerEntity){
        playerAttributePool.apply(playerEntity);
    }
    public void reverseAttributes(PlayerEntity playerEntity){
        AttributePool.reverse(playerEntity);
    }

    public String getTranslatableKey() {
        return translatableKey;
    }

    public RaceType getRaceType() {
        return raceType;
    }

    public void drawTooltip(LivingEntity entity, DrawContext context, TextRenderer renderer, int x, int y){
        List<Text> texts = new ArrayList<>();
        texts.add(getFullName());
        texts.add(Text.translatable("race_tooltip.%s.attribute_header".formatted(MiddleEarth.MOD_ID)).formatted(Formatting.UNDERLINE));
        List<AttributePoolElement> elements = playerAttributePool.getPool();
        for(var element : elements){
            double value = element.getValue();
            Double attributeValue = playerAttributePool.getEntityCurrentAttributeValue(entity, element.getIdentifier());
            if(attributeValue == null){
                MiddleEarth.LOGGER.logWarn("Can't find attribute in player : %s".formatted(element.getIdentifier()));
                continue;
            }
            double difference = value - attributeValue;

            // Round them
            value = Math.round(value * 1000) / 1000.0;
            difference = Math.round(difference * 1000) / 1000.0;

            String differenceChar = (difference > 0) ? "+" : "";
            Formatting white = Formatting.WHITE;
            Formatting differenceColor = (difference < 0) ? Formatting.RED : (difference > 0) ? Formatting.GREEN : white;
            if(playerAttributePool.isBuffReversed(element.getIdentifier())){
                differenceColor = (difference < 0) ? Formatting.GREEN : (difference > 0) ? Formatting.RED : white;
            }
            MutableText rawValue = Text.literal(String.valueOf(value)).formatted(white);
            MutableText valueText = rawValue.append(Text.literal(" (").formatted(white).append(Text.literal(differenceChar + difference).formatted(differenceColor).append(Text.literal(") ").formatted(white))));
            texts.add(valueText.append(Text.translatable("attribute.name."+element.getIdentifier().getPath()).formatted(Formatting.WHITE)));
        }
        context.drawTooltip(renderer, texts, x, y);
    }

    public void applyNpcAttributes(NpcEntity npcEntity) {
        AttributePool.reverse(npcEntity);
        npcAttributePools.get(EntityCategory.SHARED).apply(npcEntity);
        npcAttributePools.get(npcEntity.getNpcData().category).apply(npcEntity);
    }
}
