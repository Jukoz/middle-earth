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
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.exceptions.FactionIdentifierException;
import net.sevenstars.middleearth.utils.ColorsME;
import net.sevenstars.ofhallsandheralds.dtos.Faction;
import net.sevenstars.ofhallsandheralds.registries.DynamicRegistriesHH;
import net.sevenstars.ofhallsandheralds.registries.services.FactionService;

import java.util.Optional;
import java.util.function.Consumer;

public class FactionDataComponent implements TooltipAppender {
    private static final Codec<FactionDataComponent> BASE_CODEC = RecordCodecBuilder.create((instance) -> instance.group(
            RegistryKey.createCodec(DynamicRegistriesHH.FACTION).fieldOf("faction").forGetter(FactionDataComponent::getFactionKey))
            .apply(instance, FactionDataComponent::new));

    public static final Codec<FactionDataComponent> CODEC = Codec.withAlternative(BASE_CODEC, RegistryKey.createCodec(DynamicRegistriesHH.FACTION), FactionDataComponent::new);

    public static final PacketCodec<RegistryByteBuf, FactionDataComponent> PACKET_CODEC =
            PacketCodec.tuple(RegistryKey.createPacketCodec(DynamicRegistriesHH.FACTION), FactionDataComponent::getFactionKey, FactionDataComponent::new);

    private final RegistryKey<Faction> factionKey;

    public FactionDataComponent(RegistryKey<Faction> faction){
        this.factionKey = faction;
    }
    public FactionDataComponent(Identifier factionId){
        this.factionKey = FactionService.createKey(factionId);
    }

    @Override
    public void appendTooltip(Item.TooltipContext context, Consumer<Text> textConsumer, TooltipType type, ComponentsAccess components) {
        try{

            RegistryEntry<Faction> faction = FactionService.fetchFaction(context, this.factionKey.getValue());
            Optional<RegistryKey<Faction>> parent = faction.value().getOptionalParentFaction();
            if (parent.isPresent()) {
                appendFaction(textConsumer, parent.get());
                appendSubfaction(textConsumer, faction.getKey().orElseThrow());
            } else {
                appendFaction(textConsumer, faction.getKey().orElseThrow());
            }
        } catch (Exception e){
            textConsumer.accept(Text.translatable(FactionIdentifierException.KEY, this.factionKey)
                    .withColor(ColorsME.ALERT.color));
        }
    }

    private void appendFaction(Consumer<Text> textConsumer, RegistryKey<Faction> faction){
        textConsumer.accept(Text.translatable("tooltip.%s.faction".formatted(MiddleEarth.MOD_ID)).formatted(Formatting.GOLD)
                .append(Text.translatable(faction.getValue().toTranslationKey("faction")).formatted(Formatting.WHITE)));
    }

    private void appendSubfaction(Consumer<Text> textConsumer, RegistryKey<Faction> faction){
        textConsumer.accept(Text.translatable("tooltip.%s.sub_faction".formatted(MiddleEarth.MOD_ID)).formatted(Formatting.GRAY)
                .append(Text.translatable(faction.getValue().toTranslationKey("faction")).formatted(Formatting.WHITE)));
    }

    public RegistryKey<Faction> getFactionKey(){
        return factionKey;
    }
}