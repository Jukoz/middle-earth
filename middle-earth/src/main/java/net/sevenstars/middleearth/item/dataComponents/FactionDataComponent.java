package net.sevenstars.middleearth.item.dataComponents;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.component.ComponentsAccess;
import net.minecraft.item.Item;
import net.minecraft.item.tooltip.TooltipAppender;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.text.Text;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.resources.datas.factions.Faction;

import java.util.function.Consumer;

public record FactionDataComponent(Faction faction) implements TooltipAppender {
    private static final Codec<FactionDataComponent> BASE_CODEC = RecordCodecBuilder.create((instance) -> {
        return instance.group(Faction.CODEC.fieldOf("faction").forGetter(FactionDataComponent::faction))
                .apply(instance, FactionDataComponent::new);
    });
    public static final Codec<FactionDataComponent> CODEC = Codec.withAlternative(BASE_CODEC, Faction.CODEC, FactionDataComponent::new);

    //public static final PacketCodec<ByteBuf, FactionDataComponent> PACKET_CODEC =

    public FactionDataComponent(Faction faction) {
        this.faction = faction;
    }

    @Override
    public void appendTooltip(Item.TooltipContext context, Consumer<Text> textConsumer, TooltipType type, ComponentsAccess components) {
        textConsumer.accept(Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".faction")
                .append(Text.translatable("tooltip." + MiddleEarth.MOD_ID + "." + faction.getName())));
    }

    @Override
    public Faction faction() {
        return faction;
    }
}
