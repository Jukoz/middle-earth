package net.sevenstars.middleearth.resources.datas.races;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.resources.StateSaverAndLoader;
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
    private static final String modifierSpacing = "   ";

    private static List<Component> tooltipText;

    public static void draw(Race race, LivingEntity entity, GuiGraphics context, Font renderer, int x, int y, List<AttributePoolElement> playerAttributes, boolean detailed){
        tooltipText = new ArrayList<>();
        addRaceName(race);
        addAttributeListHeader();

        boolean hasAttribute = false;
        var registry = entity.level().registryAccess().registryOrThrow(Registries.ATTRIBUTE);
        List<AttributePoolElement> nextBoundAttributes = race.getBaseAttributePool().getPool();
        for (AttributePoolElement currentEntityAttribute : playerAttributes){
            hasAttribute = addAttributeLine(entity, registry, currentEntityAttribute, nextBoundAttributes, detailed) || hasAttribute;
        }
        if(!hasAttribute){
            addNoAttributeChanges();
        }

        context.renderComponentTooltip(renderer, tooltipText, x, y);
    }

    private static void addRaceName(Race race) {
        tooltipText.add(race.getFullName().withStyle(ChatFormatting.BOLD).withStyle(ChatFormatting.WHITE));
    }

    private static void addAttributeListHeader() {
        tooltipText.add(Component.translatable(MiddleEarth.of("attribute_header").toLanguageKey("race_tooltip")).withStyle(ChatFormatting.GRAY));
    }

    private static void addNoAttributeChanges() {
        tooltipText.add(Component.translatable(MiddleEarth.of("no_attribute_change").toLanguageKey("race_tooltip")).withStyle(ChatFormatting.DARK_GRAY));
    }

    private static boolean addAttributeLine(LivingEntity entity, Registry<Attribute> registry, AttributePoolElement currentEntityAttribute, List<AttributePoolElement> nextBoundAttributes, boolean detailed) {
        ResourceLocation attributeId = currentEntityAttribute.getIdentifier();
        boolean buffIsReversed = registry.getHolder(attributeId)
                .map(attribute -> attribute.is(TagKey.create(Registries.ATTRIBUTE, MiddleEarth.of("is_buff_reversed"))))
                .orElse(false);

        AttributePoolElement nextAttribute = nextBoundAttributes.stream().filter(attribute -> attributeId.equals(attribute.getIdentifier())).findFirst().orElse(null);

        double currentValue = round(currentEntityAttribute.getValue());
        double defaultValue = round(AttributePool.getDefaultAttributeValue(attributeId, entity));

        double expectedNextValue = nextAttribute == null ? defaultValue : nextAttribute.getValue();

        // Compare modifiers
        List<Component> modifierTexts = new ArrayList<>();

        List<AttributeModifierElement> currentModifiers = filterAttributeModifiers(currentEntityAttribute);
        List<AttributeModifierElement> nextBoundModifiers = filterAttributeModifiers(nextAttribute);

        if(!currentModifiers.isEmpty() || !nextBoundModifiers.isEmpty()){
            List<AttributeModifierElement> removedModifiersList = new ArrayList<>();
            List<AttributeModifierElement> willFollowInNextModifiersList = new ArrayList<>();


            List<AttributeModifierElement> unchangedModifierList = new ArrayList<>();
            List<AttributeModifierElement> modifiedModifierList = new ArrayList<>();
            List<AttributeModifierElement> addedModifiersList = new ArrayList<>();

            /// Compute the current value following the modifiers
            double currentTotal = currentValue;
            for(AttributeModifierElement currentModifier : currentModifiers){
                boolean willFollow = verifyIfModifierExistInList(currentModifier, nextBoundModifiers);
                double modifierValue = currentModifier.getValue();
                currentTotal += switch (currentModifier.getOperation()){
                    case ADD_VALUE -> modifierValue;
                    case ADD_MULTIPLIED_BASE -> round(currentValue * modifierValue);
                    case ADD_MULTIPLIED_TOTAL -> round(currentTotal * modifierValue);
                };

                if(!willFollow){
                    removedModifiersList.add(currentModifier);
                } else {
                    willFollowInNextModifiersList.add(currentModifier);
                }
            }
            currentValue = currentTotal;

            /// Compute the future value based on the bound modifiers
            if(nextAttribute != null){
                double nextTotal = nextAttribute.getValue();
                for(AttributeModifierElement nextModifier : nextBoundModifiers){
                    AttributeModifierElement linkedCurrentModifier = willFollowInNextModifiersList.stream().filter(x -> x.getIdentifier().equals(nextModifier.getIdentifier())).findAny().orElse(null);
                    if(linkedCurrentModifier != null){
                        if(linkedCurrentModifier.getValue() == nextModifier.getValue()){
                            unchangedModifierList.add(linkedCurrentModifier);
                        } else if(linkedCurrentModifier.getValue() != nextModifier.getValue()){
                            modifiedModifierList.add(linkedCurrentModifier);
                        }
                    } else {
                        addedModifiersList.add(nextModifier);
                    }
                    double modifierValue = nextModifier.getValue();
                    nextTotal += switch (nextModifier.getOperation()){
                        case ADD_VALUE -> modifierValue;
                        case ADD_MULTIPLIED_BASE -> round( nextAttribute.getValue() * modifierValue);
                        case ADD_MULTIPLIED_TOTAL -> round(nextTotal * modifierValue);
                    };
                }
                expectedNextValue = nextTotal;
            }

            for (var modifiedModifier : modifiedModifierList){
                String sign = betterSign;
                ChatFormatting formatting = ChatFormatting.GREEN;

                AttributeModifierElement nextModifier = nextBoundModifiers.stream().filter(x -> x.getIdentifier().equals(modifiedModifier.getIdentifier())).findAny().orElse(null);
                if(modifiedModifier.getValue() > nextModifier.getValue()){
                    sign = worstSign;
                    formatting = ChatFormatting.RED;
                }

                MutableComponent modifierLine = Component.literal(modifierSpacing + listStart).withStyle(ChatFormatting.GOLD);
                modifierLine.append(Component.literal(" " + sign + " ").withStyle(formatting));
                modifierLine.append(Component.translatable("attribute.modifiers." + modifiedModifier.getIdentifier().getPath()).withStyle(ChatFormatting.GRAY));
                modifierLine.append(Component.literal(" ["+ modifiedModifier.getValue() + " " + continuationSign + " " + nextModifier.getValue() + "] ").withStyle(ChatFormatting.WHITE));
                double difference = nextModifier.getValue() - modifiedModifier.getValue();
                String differencePrefix = (difference > 0) ? "+" : "";
                modifierLine.append(Component.literal("(" + differencePrefix + round(difference) + ")").withStyle(ChatFormatting.GRAY));
                modifierTexts.add(modifierLine);
            }

            for (var addedModifier : addedModifiersList){
                MutableComponent modifierLine = Component.literal(modifierSpacing + listStart).withStyle(ChatFormatting.GOLD);
                modifierLine.append(Component.literal(" " + additionSign + " ").withStyle(ChatFormatting.GREEN));
                modifierLine.append(Component.translatable("attribute.modifiers." + addedModifier.getIdentifier().getPath()).withStyle(ChatFormatting.GRAY));
                modifierLine.append(Component.literal(" ["+ addedModifier.getValue() + "] ").withStyle(ChatFormatting.WHITE));
                modifierTexts.add(modifierLine);
            }

            for (var unchangedModifier : unchangedModifierList){
                MutableComponent modifierLine = Component.literal(modifierSpacing + listStart).withStyle(ChatFormatting.GOLD);
                modifierLine.append(Component.literal(" " + equalSign + " ").withStyle(ChatFormatting.GRAY));
                modifierLine.append(Component.translatable("attribute.modifiers." + unchangedModifier.getIdentifier().getPath()).withStyle(ChatFormatting.GRAY));
                modifierLine.append(Component.literal(" ["+ unchangedModifier.getValue() + " " + continuationSign + " " + unchangedModifier.getValue() + "] ").withStyle(ChatFormatting.WHITE));
                modifierTexts.add(modifierLine);
            }

            for (var removedModifier : removedModifiersList){
                MutableComponent modifierLine = Component.literal(modifierSpacing + listStart).withStyle(ChatFormatting.GOLD);
                modifierLine.append(Component.literal(" " + removedSign + " ").withStyle(ChatFormatting.DARK_GRAY));
                modifierLine.append(Component.translatable("attribute.modifiers." + removedModifier.getIdentifier().getPath()).withStyle(ChatFormatting.DARK_GRAY));
                modifierLine.append(Component.literal(" ["+ removedModifier.getValue() + "] ").withStyle(ChatFormatting.WHITE));
                modifierTexts.add(modifierLine);
            }
        }

        // The new race does not include that attribute
        ChatFormatting signFormatting = ChatFormatting.GRAY;
        ChatFormatting textFormatting = ChatFormatting.GRAY;
        String sign = equalSign;
        if(nextAttribute == null) {
            if(defaultValue == currentValue){
                return false;
            }
            else if(defaultValue == 0){
                if(buffIsReversed){
                    sign = additionSign;
                    signFormatting = ChatFormatting.YELLOW;
                    textFormatting = ChatFormatting.YELLOW;
                } else {
                    sign = removedSign;
                    signFormatting = ChatFormatting.DARK_GRAY;
                    textFormatting = ChatFormatting.DARK_GRAY;
                }
            } else if(defaultValue > currentValue){
                if(buffIsReversed){
                    sign = worstSign;
                    signFormatting = ChatFormatting.RED;
                    textFormatting = ChatFormatting.RED;
                } else {
                    sign = betterSign;
                    signFormatting = ChatFormatting.GREEN;
                    textFormatting = ChatFormatting.GREEN;
                }
            } else if(defaultValue < currentValue){
                if(buffIsReversed){
                    sign = betterSign;
                    signFormatting = ChatFormatting.GREEN;
                    textFormatting = ChatFormatting.GREEN;
                } else {
                    sign = worstSign;
                    signFormatting = ChatFormatting.RED;
                    textFormatting = ChatFormatting.RED;
                }
            }
        } else {
            if(expectedNextValue < currentValue){
                if(buffIsReversed){
                    sign = betterSign;
                    signFormatting = ChatFormatting.GREEN;
                    textFormatting = ChatFormatting.GREEN;
                } else {
                    sign = worstSign;
                    signFormatting = ChatFormatting.RED;
                    textFormatting = ChatFormatting.RED;
                }
            } else if(expectedNextValue > currentValue){
                if(buffIsReversed){
                    sign = worstSign;
                    signFormatting = ChatFormatting.RED;
                    textFormatting = ChatFormatting.RED;
                } else {
                    sign = betterSign;
                    signFormatting = ChatFormatting.GREEN;
                    textFormatting = ChatFormatting.GREEN;
                }
            }
        }

        MutableComponent newCustomLine = Component.literal(sign).withStyle(signFormatting);
        newCustomLine.append(Component.literal(" "));
        newCustomLine.append(Component.translatable("attribute.name." + attributeId.getPath()).withStyle(textFormatting));
        if(!modifierTexts.isEmpty()){
            newCustomLine.append(Component.literal("*").withStyle(ChatFormatting.GOLD));
        }
        if(!detailed){
            tooltipText.add(newCustomLine);
        } else {
            double difference;
            if(nextAttribute == null){
                newCustomLine.append(Component.literal(" "));
                newCustomLine.append(Component.literal("["+ currentValue +" " + continuationSign + " "+ defaultValue +"]").withStyle(ChatFormatting.WHITE));
                difference = defaultValue - currentValue;
            } else {
                newCustomLine.append(Component.literal(" "));
                double newValue = round(expectedNextValue);
                newCustomLine.append(Component.literal("[" + currentValue +" " + continuationSign + " "+ newValue +"]").withStyle(ChatFormatting.WHITE));
                difference = newValue - currentValue;
            }
            newCustomLine.append(Component.literal(" "));
            String differencePrefix = (difference > 0) ? "+" : "";

            newCustomLine.append(Component.literal("(" + differencePrefix + round(difference) + ")").withStyle(ChatFormatting.GRAY));

            tooltipText.add(newCustomLine);
            tooltipText.addAll(modifierTexts);
        }
        return true;
    }

    private static boolean verifyIfModifierExistInList(AttributeModifierElement currentModifier, List<AttributeModifierElement> nextBoundModifiers) {
        if(nextBoundModifiers == null || nextBoundModifiers.isEmpty()){
            return false;
        }
        return nextBoundModifiers.stream().anyMatch(x -> x.getIdentifier().equals(currentModifier.getIdentifier()));
    }

    private static List<AttributeModifierElement> filterAttributeModifiers(AttributePoolElement attributePoolElement) {
        if(attributePoolElement == null)
            return new ArrayList<>();
        return attributePoolElement.getModifiers().stream().filter(modifier -> !modifier.getIdentifier().getPath().contains("creative")).toList();
    }


    private static double round(double value){
        return Math.round(value * 1000) / 1000.0;
    }
}
