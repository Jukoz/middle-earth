package net.sevenstars.middleearth.item.dataComponents;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.MinecraftClient;
import net.minecraft.component.ComponentsAccess;
import net.minecraft.item.Item;
import net.minecraft.item.tooltip.TooltipAppender;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.MiddleEarthClient;
import net.sevenstars.middleearth.exceptions.FactionIdentifierException;
import net.sevenstars.middleearth.resources.FactionsME;
import net.sevenstars.middleearth.resources.datas.factions.Faction;
import net.sevenstars.middleearth.resources.datas.factions.FactionLookup;
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

    public FactionDataComponent(Identifier factionId) {
        this.factionId = factionId;
    }

    @Override
    public void appendTooltip(Item.TooltipContext context, Consumer<Text> textConsumer, TooltipType type, ComponentsAccess components) {
        try {
            assert MinecraftClient.getInstance().world != null;
            Faction faction = FactionLookup.getFactionById(MinecraftClient.getInstance().world, this.factionId);
            Faction parent = faction.getParentFaction(MinecraftClient.getInstance().world);
            if (parent != null){
                textConsumer.accept(Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".faction")
                        .append(Text.translatable("faction." + MiddleEarth.MOD_ID + "." + parent.getName())));
                textConsumer.accept(Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".sub_faction")
                        .append(Text.translatable("faction." + MiddleEarth.MOD_ID + "." + faction.getName())));
            } else {
                textConsumer.accept(Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".faction")
                        .append(Text.translatable("faction." + MiddleEarth.MOD_ID + "." + faction.getName())));
            }
        } catch (FactionIdentifierException e) {
            textConsumer.accept(Text.translatable("exception." + MiddleEarth.MOD_ID + ".faction_identifier", this.factionId)
                    .withColor(ModColors.ALERT.color));
        }
    }

    @Override
    public Identifier factionId() {
        return factionId;
    }
}