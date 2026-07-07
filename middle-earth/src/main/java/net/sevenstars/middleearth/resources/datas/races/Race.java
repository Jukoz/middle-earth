package net.sevenstars.middleearth.resources.datas.races;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
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
import net.sevenstars.middleearth.resources.datas.attributes.AttributeModifierElement;
import net.sevenstars.middleearth.resources.datas.common.RaceType;
import net.sevenstars.middleearth.resources.datas.attributes.AttributePool;
import net.sevenstars.middleearth.resources.datas.attributes.AttributePoolElement;
import net.sevenstars.middleearth.resources.datas.common.EntityCategories;
import org.jetbrains.annotations.UnknownNullability;

import java.util.*;
import java.util.stream.Collectors;

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

    public void drawTooltip(LivingEntity entity, DrawContext context, TextRenderer renderer, int x, int y, List<AttributePoolElement> playerAttributes, boolean detailed){
        List<Text> textLines = new ArrayList<>();
        /// Race name
        textLines.add(getFullName().formatted(Formatting.BOLD).formatted(Formatting.WHITE));
        /// Attribute List Header
        textLines.add(Text.translatable(MiddleEarth.of("attribute_header").toTranslationKey("race_tooltip")).formatted(Formatting.GRAY));

        List<AttributePoolElement> racialAttributes = baseAttributePool.getPool();

        String betterSign = "▲";
        String equalSign = "=";
        String worstSign = "▼";
        String removedSign = "Х";
        String additionSign = "+";
        String continuationSign = "▶";
        String listStart = "●";

        boolean hasAttribute = false;
        var registry = entity.getWorld().getRegistryManager().getOptional(RegistryKeys.ATTRIBUTE).get();
        for(AttributePoolElement currentEntityAttribute : playerAttributes){
            Identifier attributeId = currentEntityAttribute.getIdentifier();
            double currentValue = round(currentEntityAttribute.getValue());

            double attributeDefaultValue = round(AttributePool.getDefaultAttributeValue(attributeId ,entity));

            AttributePoolElement linkedAttribute = racialAttributes.stream().filter(attribute -> attributeId.equals(attribute.getIdentifier()))
                                                                               .findFirst().orElse(null);

            Formatting signFormatting = Formatting.GRAY;
            Formatting textFormatting = Formatting.GRAY;
            String sign = equalSign;

            boolean buffIsReversed = registry.getEntry(attributeId).get().isIn(TagKey.of(RegistryKeys.ATTRIBUTE, MiddleEarth.of("is_buff_reversed")));

            // Compare modifiers
            List<Text> modifierTexts = new ArrayList<>();

            boolean hasModifiers = false;

            List<AttributeModifierElement> currentModifiers = currentEntityAttribute.getModifiers().stream().filter(modifier -> !modifier.getIdentifier().toString().contains("creative")).toList();
            List<AttributeModifierElement> nextModifiers = linkedAttribute != null
                    ? linkedAttribute.getModifiers().stream().filter(modifier -> !modifier.getIdentifier().toString().contains("creative")).toList()
                    : new ArrayList<>();

            if(!currentModifiers.isEmpty() || !nextModifiers.isEmpty()){
                List<EntityAttributeModifier> nextModifiersList = new ArrayList<>();
                List<EntityAttributeModifier> removedModifiersList = new ArrayList<>();

                hasModifiers = true;

                double newCurrentValue = currentValue;
                for (var currentModifier : currentModifiers) {
                    newCurrentValue += switch (currentModifier.getOperation()){
                        case ADD_VALUE -> currentModifier.getValue();
                        case ADD_MULTIPLIED_BASE -> round(currentValue * currentModifier.getValue());
                        case ADD_MULTIPLIED_TOTAL -> round(newCurrentValue * currentModifier.getValue());
                    };
                    currentValue = newCurrentValue;
                    boolean containModifierInNextList = !nextModifiers.stream().filter(nextModifier -> nextModifier.getIdentifier().toString().equals(currentModifier.getIdentifier().toString())).toList().isEmpty();
                    if(containModifierInNextList){
                        nextModifiersList.add(currentModifier.getEntityAttributeModifier());
                    } else {
                        removedModifiersList.add(currentModifier.getEntityAttributeModifier());
                    }
                }

                for (var nextModifier : nextModifiers) {
                    nextModifiersList.add(nextModifier.getEntityAttributeModifier());
                }

                String spacing = "   ";
                for (var nextModifier : nextModifiersList){
                    MutableText modifierLine = Text.literal(spacing + listStart).formatted(Formatting.GOLD);
                    modifierLine.append(Text.literal(" ").formatted(Formatting.WHITE));
                    modifierLine.append(Text.translatable("attribute.modifiers." + nextModifier.id().getPath()).formatted(Formatting.GRAY));
                    modifierLine.append(Text.literal("("+ nextModifier.operation() + " " + nextModifier.value()  + ")").formatted(Formatting.WHITE));

                    modifierTexts.add(modifierLine);
                }

                for (var removedModifier : removedModifiersList){
                    MutableText modifierLine = Text.literal(spacing + listStart).formatted(Formatting.GOLD);
                    modifierLine.append(Text.literal(" ").formatted(Formatting.WHITE));
                    modifierLine.append(Text.translatable("attribute.modifiers." + removedModifier.id().getPath()).formatted(Formatting.DARK_GRAY));
                    modifierLine.append(Text.literal("("+ removedModifier.operation() + " " + removedModifier.value()  + ")").formatted(Formatting.WHITE));

                    modifierTexts.add(modifierLine);
                }
            }

            /*

            if(currentValue != currentValue){
                currentValue = currentValue;
                hasModifiers = true;
                Set<EntityAttributeModifier> modifiers = attributeInstance.getModifiers();

                if(linkedAttribute != null && linkedAttribute.hasModifiers()){
                    double baseLinkedValue = linkedAttribute.getValue();
                    double newModifiedValue = linkedAttribute.getValue();
                    for(var linkedModifier : linkedAttribute.getModifiers()){
                        newModifiedValue += switch (linkedModifier.getOperation()){
                            case ADD_VALUE -> linkedModifier.getValue();
                            case ADD_MULTIPLIED_BASE -> baseLinkedValue * linkedModifier.getValue();
                            case ADD_MULTIPLIED_TOTAL -> newModifiedValue * linkedModifier.getValue();
                        };
                    }
                    MutableText modifierLine = Text.literal(listStart).formatted(Formatting.GOLD);
                    modifierLine.append(Text.translatable("attribute.modifiers." + "add_value"));
                    "new : " + linkedAttribute.getIdentifier().toString());
                    modifierLine.append(Text.literal(" "));
                    modifierLine.append(Text.literal("(" + newModifiedValue + ")"));
                    modifierTexts.add(modifierLine);
                }

                double baseLinkedValue = linkedAttribute.getValue();
                double newModifiedValue = linkedAttribute.getValue();
                for(var modifier : modifiers){

                    newModifiedValue += switch (modifier.operation()){
                        case ADD_VALUE -> modifier.value();
                        case ADD_MULTIPLIED_BASE -> baseLinkedValue * modifier.value();
                        case ADD_MULTIPLIED_TOTAL -> newModifiedValue * modifier.value();
                    };
                }
                MutableText modifierLine = Text.literal("current : " + linkedAttribute.getIdentifier().toString());
                modifierLine.append(Text.literal(" "));
                modifierLine.append(Text.literal("(" + newModifiedValue + ")"));
                modifierTexts.add(modifierLine);
             */

            // The new race does not include that attribute
            if(linkedAttribute == null) {
                if(attributeDefaultValue == currentValue){
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
                } else if(attributeDefaultValue > currentValue){
                    if(buffIsReversed){
                        sign = worstSign;
                        signFormatting = Formatting.RED;
                        textFormatting = Formatting.RED;
                    } else {
                        sign = betterSign;
                        signFormatting = Formatting.GREEN;
                        textFormatting = Formatting.GREEN;
                    }
                } else if(attributeDefaultValue < currentValue){
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
                if(linkedAttributeValue < currentValue){
                    if(buffIsReversed){
                        sign = betterSign;
                        signFormatting = Formatting.GREEN;
                        textFormatting = Formatting.GREEN;
                    } else {
                        sign = worstSign;
                        signFormatting = Formatting.RED;
                        textFormatting = Formatting.RED;
                    }
                } else if(linkedAttributeValue > currentValue){
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
            if(hasModifiers){
                newCustomLine.append(Text.literal("*").formatted(Formatting.GOLD));

                newCustomLine.append(Text.literal(" "));
            }
            hasAttribute = true;

            if(!detailed){
                textLines.add(newCustomLine);
                continue;
            }

            double difference;
            if(linkedAttribute == null){
                newCustomLine.append(Text.literal(" "));
                newCustomLine.append(Text.literal("["+ currentValue +" " + continuationSign + " "+ attributeDefaultValue +"]").formatted(Formatting.WHITE));
                difference = attributeDefaultValue - currentValue;
            } else {
                newCustomLine.append(Text.literal(" "));
                double newValue = round(linkedAttribute.getValue());
                newCustomLine.append(Text.literal("[" + currentValue +" " + continuationSign + " "+ newValue +"]").formatted(Formatting.WHITE));
                difference = newValue - currentValue;
            }
            newCustomLine.append(Text.literal(" "));
            String differencePrefix = (difference > 0) ? "+" : "";

            newCustomLine.append(Text.literal("(" + differencePrefix + round(difference) + ")").formatted(Formatting.GRAY));

            textLines.add(newCustomLine);
            if(hasModifiers){
                textLines.addAll(modifierTexts);
            }
        }

        if(!hasAttribute){
            textLines.add(Text.translatable(MiddleEarth.of("no_attribute_change").toTranslationKey("race_tooltip")).formatted(Formatting.DARK_GRAY));
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
