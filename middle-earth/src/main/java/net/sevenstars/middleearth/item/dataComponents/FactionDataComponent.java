package net.sevenstars.middleearth.item.dataComponents;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.component.ComponentsAccess;
import net.minecraft.item.Item;
import net.minecraft.item.tooltip.TooltipAppender;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.registry.RegistryKey;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.exceptions.FactionIdentifierException;
import net.sevenstars.middleearth.registries.DynamicRegistriesME;
import net.sevenstars.middleearth.resources.datas.factions.Faction;
import net.sevenstars.middleearth.utils.ModColors;

import java.util.function.Consumer;

public record FactionDataComponent(Identifier factionId) implements TooltipAppender {
    private static final Codec<FactionDataComponent> BASE_CODEC = RecordCodecBuilder.create((instance) -> {
        return instance.group(Identifier.CODEC.fieldOf("faction").forGetter(FactionDataComponent::factionId))
                .apply(instance, FactionDataComponent::new);
    });
    public static final Codec<FactionDataComponent> CODEC = Codec.withAlternative(BASE_CODEC, Identifier.CODEC, FactionDataComponent::new);

    public static final PacketCodec<RegistryByteBuf, FactionDataComponent> PACKET_CODEC =
            PacketCodec.tuple(Identifier.PACKET_CODEC, FactionDataComponent::factionId, FactionDataComponent::new);

    @Override
    public void appendTooltip(Item.TooltipContext context, Consumer<Text> textConsumer, TooltipType type, ComponentsAccess components) {
        try{
            Faction faction = context.getRegistryLookup().getOrThrow(DynamicRegistriesME.FACTION).getOrThrow(RegistryKey.of(DynamicRegistriesME.FACTION, this.factionId)).value();
            Faction parent = faction.getParentFaction(context.getRegistryLookup());
            if (parent != null){
                appendFaction(textConsumer, parent);
                appendSubfaction(textConsumer, faction);
            } else {
                appendFaction(textConsumer, faction);
            }
        } catch (Exception e){
            textConsumer.accept(Text.translatable(FactionIdentifierException.KEY, this.factionId)
                    .withColor(ModColors.ALERT.color));
        }
    }

    private void appendFaction(Consumer<Text> textConsumer, Faction faction){
        textConsumer.accept(Text.translatable("tooltip.%s.faction".formatted(MiddleEarth.MOD_ID)).formatted(Formatting.GOLD)
                .append(Text.translatable(faction.getId().toTranslationKey("faction")).formatted(Formatting.WHITE)));
    }

    private void appendSubfaction(Consumer<Text> textConsumer, Faction faction){
        textConsumer.accept(Text.translatable("tooltip.%s.sub_faction".formatted(MiddleEarth.MOD_ID)).formatted(Formatting.GRAY)
                .append(Text.translatable(faction.getId().toTranslationKey("faction")).formatted(Formatting.WHITE)));
    }

    @Override
    public Identifier factionId() {
        return factionId;
    }
}