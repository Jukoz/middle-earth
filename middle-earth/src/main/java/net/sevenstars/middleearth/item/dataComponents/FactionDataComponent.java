package net.sevenstars.middleearth.item.dataComponents;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.ChatFormatting;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipProvider;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.exceptions.FactionIdentifierException;
import net.sevenstars.middleearth.registries.DynamicRegistriesME;
import net.sevenstars.middleearth.resources.datas.factions.Faction;
import net.sevenstars.middleearth.utils.ModColors;

import java.util.function.Consumer;

public record FactionDataComponent(ResourceLocation factionId) implements TooltipProvider {
    private static final Codec<FactionDataComponent> BASE_CODEC = RecordCodecBuilder.create((instance) -> instance.group(ResourceLocation.CODEC.fieldOf("faction").forGetter(FactionDataComponent::getFactionId))
            .apply(instance, FactionDataComponent::new));
    public static final Codec<FactionDataComponent> CODEC = Codec.withAlternative(BASE_CODEC, ResourceLocation.CODEC, FactionDataComponent::new);

    public static final StreamCodec<RegistryFriendlyByteBuf, FactionDataComponent> PACKET_CODEC =
            StreamCodec.composite(ResourceLocation.STREAM_CODEC, FactionDataComponent::getFactionId, FactionDataComponent::new);

    public FactionDataComponent(ResourceKey<Faction> faction){
        this(faction.location());
    }

    @Override
    public void addToTooltip(Item.TooltipContext context, Consumer<Component> textConsumer, TooltipFlag type) {
        try{
            Faction faction = context.registries().lookupOrThrow(DynamicRegistriesME.FACTION).getOrThrow(ResourceKey.create(DynamicRegistriesME.FACTION, this.factionId)).value();
            Faction parent = faction.getParentFaction(context.registries());
            if (parent != null){
                appendFaction(textConsumer, parent);
                appendSubfaction(textConsumer, faction);
            } else {
                appendFaction(textConsumer, faction);
            }
        } catch (Exception e){
            textConsumer.accept(Component.translatable(FactionIdentifierException.KEY, this.factionId)
                    .withColor(ModColors.ALERT.color));
        }
    }

    private void appendFaction(Consumer<Component> textConsumer, Faction faction){
        textConsumer.accept(Component.translatable("tooltip.%s.faction".formatted(MiddleEarth.MOD_ID)).withStyle(ChatFormatting.GOLD)
                .append(Component.translatable(faction.getId().toLanguageKey("faction")).withStyle(ChatFormatting.WHITE)));
    }

    private void appendSubfaction(Consumer<Component> textConsumer, Faction faction){
        textConsumer.accept(Component.translatable("tooltip.%s.sub_faction".formatted(MiddleEarth.MOD_ID)).withStyle(ChatFormatting.GRAY)
                .append(Component.translatable(faction.getId().toLanguageKey("faction")).withStyle(ChatFormatting.WHITE)));
    }

    public ResourceLocation getFactionId(){
        return factionId;
    }
}
