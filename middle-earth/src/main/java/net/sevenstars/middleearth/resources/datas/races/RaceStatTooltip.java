package net.sevenstars.middleearth.resources.datas.races;

import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
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

    private static List<Text> tooltipText;

    public static void draw(Race race, LivingEntity entity, DrawContext context, TextRenderer renderer, int x, int y, List<AttributePoolElement> playerAttributes, boolean detailed){
        tooltipText = new ArrayList<>();
        addRaceName(race);
        addAttributeListHeader();

        boolean hasAttribute = false;
        var registry = entity.getWorld().getRegistryManager().getOptional(RegistryKeys.ATTRIBUTE).get();
        List<AttributePoolElement> nextBoundAttributes = race.getBaseAttributePool().getPool();
        for (AttributePoolElement currentEntityAttribute : playerAttributes){
            hasAttribute = addAttributeLine(entity, registry, currentEntityAttribute, nextBoundAttributes, detailed) || hasAttribute;
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

    private static boolean addAttributeLine(LivingEntity entity, Registry<EntityAttribute> registry, AttributePoolElement currentEntityAttribute, List<AttributePoolElement> nextBoundAttributes, boolean detailed) {
        Identifier attributeId = currentEntityAttribute.getIdentifier();
        boolean buffIsReversed = registry.getEntry(attributeId).get().isIn(TagKey.of(RegistryKeys.ATTRIBUTE, MiddleEarth.of("is_buff_reversed")));

        AttributePoolElement nextAttribute = nextBoundAttributes.stream().filter(attribute -> attributeId.equals(attribute.getIdentifier())).findFirst().orElse(null);

        double currentValue = round(currentEntityAttribute.getValue());
        double defaultValue = round(AttributePool.getDefaultAttributeValue(attributeId, entity));

        double expectedNextValue = nextAttribute == null ? defaultValue : nextAttribute.getValue();

        // Compare modifiers
        List<Text> modifierTexts = new ArrayList<>();

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
                Formatting formatting = Formatting.GREEN;

                AttributeModifierElement nextModifier = nextBoundModifiers.stream().filter(x -> x.getIdentifier().equals(modifiedModifier.getIdentifier())).findAny().orElse(null);
                if(modifiedModifier.getValue() > nextModifier.getValue()){
                    sign = worstSign;
                    formatting = Formatting.RED;
                }

                MutableText modifierLine = Text.literal(modifierSpacing + listStart).formatted(Formatting.GOLD);
                modifierLine.append(Text.literal(" " + sign + " ").formatted(formatting));
                modifierLine.append(Text.translatable("attribute.modifiers." + modifiedModifier.getIdentifier().getPath()).formatted(Formatting.GRAY));
                modifierLine.append(Text.literal(" ["+ modifiedModifier.getValue() + " " + continuationSign + " " + nextModifier.getValue() + "] ").formatted(Formatting.WHITE));
                double difference = nextModifier.getValue() - modifiedModifier.getValue();
                String differencePrefix = (difference > 0) ? "+" : "";
                modifierLine.append(Text.literal("(" + differencePrefix + round(difference) + ")").formatted(Formatting.GRAY));
                modifierTexts.add(modifierLine);
            }

            for (var addedModifier : addedModifiersList){
                MutableText modifierLine = Text.literal(modifierSpacing + listStart).formatted(Formatting.GOLD);
                modifierLine.append(Text.literal(" " + additionSign + " ").formatted(Formatting.GREEN));
                modifierLine.append(Text.translatable("attribute.modifiers." + addedModifier.getIdentifier().getPath()).formatted(Formatting.GRAY));
                modifierLine.append(Text.literal(" ["+ addedModifier.getValue() + "] ").formatted(Formatting.WHITE));
                modifierTexts.add(modifierLine);
            }

            for (var unchangedModifier : unchangedModifierList){
                MutableText modifierLine = Text.literal(modifierSpacing + listStart).formatted(Formatting.GOLD);
                modifierLine.append(Text.literal(" " + equalSign + " ").formatted(Formatting.GRAY));
                modifierLine.append(Text.translatable("attribute.modifiers." + unchangedModifier.getIdentifier().getPath()).formatted(Formatting.GRAY));
                modifierLine.append(Text.literal(" ["+ unchangedModifier.getValue() + " " + continuationSign + " " + unchangedModifier.getValue() + "] ").formatted(Formatting.WHITE));
                modifierTexts.add(modifierLine);
            }

            for (var removedModifier : removedModifiersList){
                MutableText modifierLine = Text.literal(modifierSpacing + listStart).formatted(Formatting.GOLD);
                modifierLine.append(Text.literal(" " + removedSign + " ").formatted(Formatting.DARK_GRAY));
                modifierLine.append(Text.translatable("attribute.modifiers." + removedModifier.getIdentifier().getPath()).formatted(Formatting.DARK_GRAY));
                modifierLine.append(Text.literal(" ["+ removedModifier.getValue() + "] ").formatted(Formatting.WHITE));
                modifierTexts.add(modifierLine);
            }
        }

        // The new race does not include that attribute
        Formatting signFormatting = Formatting.GRAY;
        Formatting textFormatting = Formatting.GRAY;
        String sign = equalSign;
        if(nextAttribute == null) {
            if(defaultValue == currentValue){
                return false;
            }
            else if(defaultValue == 0){
                if(buffIsReversed){
                    sign = additionSign;
                    signFormatting = Formatting.YELLOW;
                    textFormatting = Formatting.YELLOW;
                } else {
                    sign = removedSign;
                    signFormatting = Formatting.DARK_GRAY;
                    textFormatting = Formatting.DARK_GRAY;
                }
            } else if(defaultValue > currentValue){
                if(buffIsReversed){
                    sign = worstSign;
                    signFormatting = Formatting.RED;
                    textFormatting = Formatting.RED;
                } else {
                    sign = betterSign;
                    signFormatting = Formatting.GREEN;
                    textFormatting = Formatting.GREEN;
                }
            } else if(defaultValue < currentValue){
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
            if(expectedNextValue < currentValue){
                if(buffIsReversed){
                    sign = betterSign;
                    signFormatting = Formatting.GREEN;
                    textFormatting = Formatting.GREEN;
                } else {
                    sign = worstSign;
                    signFormatting = Formatting.RED;
                    textFormatting = Formatting.RED;
                }
            } else if(expectedNextValue > currentValue){
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
        if(!modifierTexts.isEmpty()){
            newCustomLine.append(Text.literal("*").formatted(Formatting.GOLD));
        }
        if(!detailed){
            tooltipText.add(newCustomLine);
        } else {
            double difference;
            if(nextAttribute == null){
                newCustomLine.append(Text.literal(" "));
                newCustomLine.append(Text.literal("["+ currentValue +" " + continuationSign + " "+ defaultValue +"]").formatted(Formatting.WHITE));
                difference = defaultValue - currentValue;
            } else {
                newCustomLine.append(Text.literal(" "));
                double newValue = round(expectedNextValue);
                newCustomLine.append(Text.literal("[" + currentValue +" " + continuationSign + " "+ newValue +"]").formatted(Formatting.WHITE));
                difference = newValue - currentValue;
            }
            newCustomLine.append(Text.literal(" "));
            String differencePrefix = (difference > 0) ? "+" : "";

            newCustomLine.append(Text.literal("(" + differencePrefix + round(difference) + ")").formatted(Formatting.GRAY));

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
