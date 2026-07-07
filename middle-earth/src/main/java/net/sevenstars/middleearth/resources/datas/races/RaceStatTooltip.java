package net.sevenstars.middleearth.resources.datas.races;

import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.resources.datas.attributes.AttributeModifierElement;
import net.sevenstars.middleearth.resources.datas.attributes.AttributePool;
import net.sevenstars.middleearth.resources.datas.attributes.AttributePoolElement;

import java.util.ArrayList;
import java.util.List;

public class RaceStatTooltip {
    private static final String betterSign = "▲";
    private static final String equalSign = "=";
    private static final String worstSign = "▼";
    private static final String removedSign = "Х";
    private static final String additionSign = "+";
    private static final String continuationSign = "▶";
    private static final String listStart = "●";

    private static List<Text> tooltipText;

    public static void draw(Race race, LivingEntity entity, DrawContext context, TextRenderer renderer, int x, int y, List<AttributePoolElement> playerAttributes, boolean detailed){
        tooltipText = new ArrayList<>();
        addRaceName(race);
        addAttributeListHeader();

        boolean hasAttribute = false;
        var registry = entity.getWorld().getRegistryManager().getOptional(RegistryKeys.ATTRIBUTE).get();
        List<AttributePoolElement> nextBoundAttributes = race.getBaseAttributePool().getPool();
        for (AttributePoolElement currentEntityAttribute : playerAttributes){
            hasAttribute = addAttributeSection(entity, registry, currentEntityAttribute, nextBoundAttributes, detailed) || hasAttribute;
        }
        if(!hasAttribute){
            addNoAttributeChanges();
        }

        context.drawTooltip(renderer, tooltipText, x, y);
    }

    private static void addRaceName(Race race) {
        tooltipText.add(race.getFullName().formatted(Formatting.BOLD).formatted(Formatting.WHITE));
    }

    private static void addAttributeListHeader() {
        tooltipText.add(Text.translatable(MiddleEarth.of("attribute_header").toTranslationKey("race_tooltip")).formatted(Formatting.GRAY));
    }

    private static void addNoAttributeChanges() {
        tooltipText.add(Text.translatable(MiddleEarth.of("no_attribute_change").toTranslationKey("race_tooltip")).formatted(Formatting.DARK_GRAY));
    }

    private static boolean addAttributeSection(LivingEntity entity, Registry<EntityAttribute> registry, AttributePoolElement currentEntityAttribute, List<AttributePoolElement> nextBoundAttributes, boolean detailed) {
        Identifier attributeId = currentEntityAttribute.getIdentifier();
        double currentValue = round(currentEntityAttribute.getValue());
        double attributeDefaultValue = round(AttributePool.getDefaultAttributeValue(attributeId, entity));
        double nextValue = attributeDefaultValue;

        AttributePoolElement linkedAttribute = nextBoundAttributes.stream().filter(attribute -> attributeId.equals(attribute.getIdentifier()))
                .findFirst().orElse(null);

        Formatting signFormatting = Formatting.GRAY;
        Formatting textFormatting = Formatting.GRAY;
        String sign = equalSign;

        boolean buffIsReversed = registry.getEntry(attributeId).get().isIn(TagKey.of(RegistryKeys.ATTRIBUTE, MiddleEarth.of("is_buff_reversed")));

        // Compare modifiers
        List<Text> modifierTexts = new ArrayList<>();

        boolean hasModifiers = false;

        List<AttributeModifierElement> currentModifiers = currentEntityAttribute.getModifiers().stream().filter(modifier -> !modifier.getIdentifier().getPath().contains("creative")).toList();
        List<AttributeModifierElement> nextModifiers = linkedAttribute != null
                ? linkedAttribute.getModifiers().stream().filter(modifier -> !modifier.getIdentifier().getPath().contains("creative")).toList()
                : new ArrayList<>();

        if(!currentModifiers.isEmpty() || !nextModifiers.isEmpty()){
            List<AttributeModifierElement> removedModifiersList = new ArrayList<>();
            List<AttributeModifierElement> nextModifiersList = new ArrayList<>();

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
                    nextModifiersList.add(currentModifier);
                } else {
                    removedModifiersList.add(currentModifier);
                }
            }

            if(linkedAttribute != null){
                nextValue = linkedAttribute.getValue();

                for (var nextModifier : nextModifiersList) {
                    nextValue += switch (nextModifier.getOperation()){
                        case ADD_VALUE -> nextModifier.getValue();
                        case ADD_MULTIPLIED_BASE -> round(linkedAttribute.getValue() * nextModifier.getValue());
                        case ADD_MULTIPLIED_TOTAL -> round(nextValue * nextModifier.getValue());
                    };
                }
            }

            String spacing = "   ";

            for (var nextModifier : nextModifiersList){
                MutableText modifierLine = Text.literal(spacing + listStart).formatted(Formatting.GOLD);
                modifierLine.append(Text.literal(" " + additionSign + " ").formatted(Formatting.GREEN));
                modifierLine.append(Text.translatable("attribute.modifiers." + nextModifier.getIdentifier().getPath()).formatted(Formatting.GRAY));
                modifierLine.append(Text.literal(" ["+ nextModifier.getValue() + " " + continuationSign + " " + nextModifier.getValue() + "] ").formatted(Formatting.WHITE));
                modifierLine.append(Text.literal(" (").formatted(Formatting.GRAY));
                modifierLine.append(Text.translatable("attribute.operations." + nextModifier.getOperation().toString().toLowerCase()).formatted(Formatting.GRAY));
                modifierLine.append(Text.literal(")").formatted(Formatting.GRAY));
                modifierTexts.add(modifierLine);
            }

            for (var removedModifier : removedModifiersList){
                MutableText modifierLine = Text.literal(spacing + listStart).formatted(Formatting.GOLD);
                modifierLine.append(Text.literal(" " + removedSign + " ").formatted(Formatting.DARK_GRAY));
                modifierLine.append(Text.translatable("attribute.modifiers." + removedModifier.getIdentifier().getPath()).formatted(Formatting.DARK_GRAY));
                modifierLine.append(Text.literal(" ["+ removedModifier.getValue() + "] ").formatted(Formatting.WHITE));
                modifierLine.append(Text.literal(" (").formatted(Formatting.DARK_GRAY));
                modifierLine.append(Text.translatable("attribute.operations." + removedModifier.getOperation().toString().toLowerCase()).formatted(Formatting.DARK_GRAY));
                modifierLine.append(Text.literal(")").formatted(Formatting.DARK_GRAY));
                modifierTexts.add(modifierLine);
            }
        }

        // The new race does not include that attribute
        if(linkedAttribute == null) {
            if(attributeDefaultValue == currentValue){
                return false;
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
            if(nextValue < currentValue){
                if(buffIsReversed){
                    sign = betterSign;
                    signFormatting = Formatting.GREEN;
                    textFormatting = Formatting.GREEN;
                } else {
                    sign = worstSign;
                    signFormatting = Formatting.RED;
                    textFormatting = Formatting.RED;
                }
            } else if(nextValue > currentValue){
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
        if(!detailed){
            tooltipText.add(newCustomLine);
        } else {
            double difference;
            if(linkedAttribute == null){
                newCustomLine.append(Text.literal(" "));
                newCustomLine.append(Text.literal("["+ currentValue +" " + continuationSign + " "+ attributeDefaultValue +"]").formatted(Formatting.WHITE));
                difference = attributeDefaultValue - currentValue;
            } else {
                newCustomLine.append(Text.literal(" "));
                double newValue = round(nextValue);
                newCustomLine.append(Text.literal("[" + currentValue +" " + continuationSign + " "+ newValue +"]").formatted(Formatting.WHITE));
                difference = newValue - currentValue;
            }
            newCustomLine.append(Text.literal(" "));
            String differencePrefix = (difference > 0) ? "+" : "";

            newCustomLine.append(Text.literal("(" + differencePrefix + round(difference) + ")").formatted(Formatting.GRAY));

            tooltipText.add(newCustomLine);
            if(hasModifiers){
                tooltipText.addAll(modifierTexts);
            }
        }
        return true;
    }


    private static double round(double value){
        return Math.round(value * 1000) / 1000.0;
    }
}
