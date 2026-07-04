package net.sevenstars.middleearth.resources.datas.races;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.entity.npcs.NpcEntity;
import net.sevenstars.middleearth.resources.datas.common.RaceType;
import net.sevenstars.middleearth.resources.datas.attributes.AttributePool;
import net.sevenstars.middleearth.resources.datas.attributes.AttributePoolElement;
import net.sevenstars.middleearth.resources.datas.common.EntityCategories;

import javax.swing.text.html.Option;
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
            NbtCompound.CODEC.fieldOf(BASE_ATTRIBUTE_FIELD).forGetter(Race::getBaseAttributePool),
            NbtCompound.CODEC.fieldOf(CATEGORY_BASED_ATTRIBUTE_FIELD).forGetter(Race::getCategoryBasedAttributePool),
            Codec.list(Codec.STRING, 0, 5).optionalFieldOf(COMMAND_JOIN_FIELD).forGetter(Race::getJoinCommands),
            Codec.list(Codec.STRING, 0, 5).optionalFieldOf(COMMAND_LEAVE_FIELD).forGetter(Race::getLeaveCommands)
    ).apply(instance, Race::new));

    private final Identifier id;
    private final RaceType raceType;
    private final String translatableKey;
    private final AttributePool baseAttributePool;
    private final HashMap<EntityCategories, AttributePool> categoryBasedAttributePool;
    private final List<String> joinCommands;
    private final List<String> leaveCommands;


    public Race(String id, String raceTypeValue, NbtCompound baseAttributes, NbtCompound categoryBasedAttributes, Optional<List<String>> joinCommands, Optional<List<String>> leaveCommands){
        // Create id
        this.id = MiddleEarth.fetchId(id);
        this.translatableKey = "race.".concat(this.id.toTranslationKey());
        // Create model
        this.raceType = RaceType.valueOf(raceTypeValue.toUpperCase());
        // Attribute Datas
        this.baseAttributePool = new AttributePool(baseAttributes);
        this.categoryBasedAttributePool = new HashMap<>();
        // new AttributePool(categoryBasedAttributes);
        for(var category : EntityCategories.values()){
            if(categoryBasedAttributes.contains(category.name())){
                this.categoryBasedAttributePool.put(category, new AttributePool(categoryBasedAttributes.getCompound(category.name()).get()));
            }
        }

        // Join commands
        this.joinCommands = new ArrayList<>();
        joinCommands.ifPresent(this.joinCommands::addAll);
        // Leave commands
        this.leaveCommands = new ArrayList<>();
        leaveCommands.ifPresent(this.leaveCommands::addAll);
    }

    public Race(Identifier id, RaceType raceType, AttributePool baseAttributePool, HashMap<EntityCategories, AttributePool> categoryBasedAttributePool, List<String> joinCommands, List<String> leaveCommands) {
        this.id = id;
        this.raceType = raceType;
        this.translatableKey = "race.".concat(this.id.toTranslationKey());
        this.baseAttributePool = baseAttributePool;
        this.categoryBasedAttributePool = categoryBasedAttributePool;
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

    private NbtCompound getBaseAttributePool() {
        if(baseAttributePool == null)
            return null;
        return baseAttributePool.getNbt();
    }

    private NbtCompound getCategoryBasedAttributePool() {
        if(categoryBasedAttributePool == null)
            return null;
        var nbt = new NbtCompound();
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

    public MutableText getFullName() {
        return Text.translatable(translatableKey);
    }

    public void applyPlayerAttributes(PlayerEntity playerEntity){
        baseAttributePool.apply(playerEntity);
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

    public void drawTooltip(LivingEntity entity, DrawContext context, TextRenderer renderer, int x, int y, boolean detailed){
        List<Text> textLines = new ArrayList<>();
        /// Race name
        textLines.add(getFullName().formatted(Formatting.BOLD).formatted(Formatting.WHITE));
        /// Attribute List Header
        textLines.add(Text.translatable("race_tooltip.%s.attribute_header".formatted(MiddleEarth.MOD_ID)).formatted(Formatting.GRAY));

        Set<EntityAttributeInstance> allEntityAttributes = entity.getAttributes().getTracked();
        List<AttributePoolElement> attributesToCompare = baseAttributePool.getPool();

        String betterSign = "▲";
        String equalSign = "=";
        String worstSign = "▼";
        String removedSign = "-";
        String additionSign = "+";
        String continuationSign = "➤"; //▶
        String listStart = "✦"; //•, ●, 〢, |၊

        for(EntityAttributeInstance attributeInstance : allEntityAttributes){
            Identifier attributeId = MiddleEarth.fetchId(attributeInstance.getAttribute().getIdAsString());

            double currentBaseValue = round(attributeInstance.getBaseValue());
            double currentValue = round(attributeInstance.getValue());

            double attributeDefaultValue = round(AttributePool.getDefaultAttributeValue(attributeId ,entity));

            AttributePoolElement linkedAttribute = attributesToCompare.stream().filter(attribute -> attributeId.equals(attribute.getIdentifier()))
                                                                               .findFirst().orElse(null);


            Formatting signFormatting = Formatting.GRAY;
            Formatting textFormatting = Formatting.GRAY;
            String sign = equalSign;

            boolean buffIsReversed = attributeInstance.getAttribute().isIn(TagKey.of(RegistryKeys.ATTRIBUTE, MiddleEarth.of("is_buff_reversed")));

            // The new race does not include that attribute
            if(linkedAttribute == null) {
                if(attributeDefaultValue == currentBaseValue){
                    continue;
                }
                else if(attributeDefaultValue == 0){
                    if(buffIsReversed){
                        sign = additionSign;
                        signFormatting = Formatting.YELLOW;
                        textFormatting = Formatting.YELLOW;
                    } else {
                        sign = removedSign;
                        signFormatting = Formatting.DARK_GRAY;
                        textFormatting = Formatting.DARK_GRAY;
                    }
                } else if(attributeDefaultValue > currentBaseValue){
                    if(buffIsReversed){
                        sign = worstSign;
                        signFormatting = Formatting.RED;
                        textFormatting = Formatting.RED;
                    } else {
                        sign = betterSign;
                        signFormatting = Formatting.GREEN;
                        textFormatting = Formatting.GREEN;
                    }
                } else if(attributeDefaultValue < currentBaseValue){
                    if(buffIsReversed){
                        sign = betterSign;
                        signFormatting = Formatting.GREEN;
                        textFormatting = Formatting.GREEN;
                    } else {
                        sign = worstSign;
                        signFormatting = Formatting.RED;
                        textFormatting = Formatting.RED;
                    }
                }
            } else {
                double linkedAttributeValue = linkedAttribute.getValue();
                if(linkedAttributeValue < currentBaseValue){
                    if(buffIsReversed){
                        sign = betterSign;
                        signFormatting = Formatting.GREEN;
                        textFormatting = Formatting.GREEN;
                    } else {
                        sign = worstSign;
                        signFormatting = Formatting.RED;
                        textFormatting = Formatting.RED;
                    }
                } else if(linkedAttributeValue > currentBaseValue){
                    if(buffIsReversed){
                        sign = worstSign;
                        signFormatting = Formatting.RED;
                        textFormatting = Formatting.RED;
                    } else {
                        sign = betterSign;
                        signFormatting = Formatting.GREEN;
                        textFormatting = Formatting.GREEN;
                    }
                }
            }

            MutableText newCustomLine = Text.literal(sign).formatted(signFormatting);
            newCustomLine.append(Text.literal(" "));
            newCustomLine.append(Text.translatable("attribute.name." + attributeId.getPath()).formatted(textFormatting));

            if(!detailed){
                textLines.add(newCustomLine);
                continue;
            }

            double difference;
            if(linkedAttribute == null){
                newCustomLine.append(Text.literal(" "));
                newCustomLine.append(Text.literal("["+ currentBaseValue +" ➤ "+ attributeDefaultValue +"]").formatted(Formatting.WHITE));
                difference = attributeDefaultValue - currentBaseValue;
            } else {
                newCustomLine.append(Text.literal(" "));
                double newValue = round(linkedAttribute.getValue());
                newCustomLine.append(Text.literal("[" + currentBaseValue +" " + continuationSign + " "+ newValue +"]").formatted(Formatting.WHITE));
                difference = newValue - currentBaseValue;
            }
            newCustomLine.append(Text.literal(" "));
            String differencePrefix = (difference > 0) ? "+" : "";

            newCustomLine.append(Text.literal("(" + differencePrefix + round(difference) + ")").formatted(Formatting.GRAY));

            textLines.add(newCustomLine);
        }
            /*

        Formatting defaultColor = Formatting.WHITE;
        Formatting unchangedColor = Formatting.DARK_GRAY;
        Formatting betterColor = Formatting.GREEN;
        Formatting worseColor = Formatting.RED;

        /// Parses the full list of attributes
        for(var raceAttribute : raceAttributes){
            /// Obtain current entity attribute for the selected attribute
            EntityAttributeInstance currAttributeInst = baseAttributePool.getEntityCurrentAttributeValue(entity, raceAttribute.getIdentifier());
            if(currAttributeInst == null){
                MiddleEarth.LOGGER.logWarn("Can't find attribute in player : %s".formatted(raceAttribute.getIdentifier()));
                continue;
            }

            double currAttributeValue = currAttributeInst.getValue();

            /// Compare value
            double baseValueDifference = Math.round((raceAttribute.getValue() - currAttributeValue) * 1000) / 1000.0;

            ///  Compare Modifiers
            ArrayList<EntityAttributeModifier> currAttributeModifiers = new ArrayList<>(currAttributeInst.getModifiers().stream().toList());
            EntityAttributeModifier raceAttributeModifier = (raceAttribute.hasModifiers())
                    ? null //new EntityAttributeModifier(raceAttribute.get(), raceAttribute.getModifierValue(), EntityAttributeModifier.Operation.valueOf(raceAttribute.getModifierType()))
                    : null;


            ///  Build line
            MutableText line = Text.literal(String.valueOf(raceAttribute.getValue()));

            Formatting valueComparisonColor = unchangedColor;
            String prefix = "";
            if(baseValueDifference < 0){
                valueComparisonColor = worseColor;
            }
            else if(baseValueDifference > 0){
                valueComparisonColor = betterColor;
                prefix = "+";
            }

            line.append(Text.literal(" ("));
            line.append(Text.literal(prefix + baseValueDifference).formatted(valueComparisonColor));
            line.append(Text.literal(") "));

            line.append(Text.translatable("attribute.name." + raceAttribute.getIdentifier().getPath()));

            if(!currAttributeModifiers.isEmpty() || raceAttributeModifier != null)
            {
                line.append(Text.literal("*").formatted(Formatting.GOLD));
                textLines.add(line);

                for(EntityAttributeModifier modifier : currAttributeModifiers){
                    MutableText modifierLine = Text.literal(" > ").formatted(Formatting.GOLD);
                    modifierLine.append(Text.translatable("attribute.modifiers." + modifier.id().getPath()).formatted(unchangedColor));
                    modifierLine.append(" (");

                    baseValueDifference = Math.round((modifier.value() - 0) * 1000) / 1000.0;
                    Formatting modifierComparisonColor = unchangedColor;
                    if(baseValueDifference < 0){
                        modifierComparisonColor = worseColor;
                    }
                    else if(baseValueDifference > 0){
                        modifierComparisonColor = betterColor;
                    }

                    modifierLine.append(Text.literal("*" + modifier.value()).formatted(modifierComparisonColor));
                    modifierLine.append(")");
                    textLines.add(modifierLine);
                }
            } else {
                textLines.add(line);
            }


            /*
             // Round them

             String differenceChar = (difference > 0) ? "+" : "";
             Formatting white = Formatting.WHITE;
             Formatting differenceColor = (difference < 0) ? Formatting.RED : (difference > 0) ? Formatting.GREEN : white;
             if(baseAttributePool.isBuffReversed(currentEntityAttribute.getIdentifier())){
                 differenceColor = (difference < 0) ? Formatting.GREEN : (difference > 0) ? Formatting.RED : white;
             }
             MutableText rawValue = Text.literal(String.valueOf(nextAttributeValue)).formatted(white);
             MutableText valueText = rawValue.append(Text.literal(" (").formatted(white).append(Text.literal(differenceChar + difference).formatted(differenceColor).append(Text.literal(") ").formatted(white))));
        }
        */
        context.drawTooltip(renderer, textLines, x, y);
    }

    private static double round(double value){
       return Math.round(value * 1000) / 1000.0;
    }

    public void applyNpcAttributes(NpcEntity npcEntity) {
        AttributePool.reverse(npcEntity);
        baseAttributePool.apply(npcEntity);
        categoryBasedAttributePool.get(EntityCategories.SHARED).apply(npcEntity);
        categoryBasedAttributePool.get(npcEntity.getNpcCategory()).apply(npcEntity);
    }
}
