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
import net.sevenstars.api.enums.LangCategory;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.item.utils.armor.ArmorMaterialsME;

import java.util.function.Consumer;

public record ArmorTierDataComponent(ArmorMaterialsME.Tiers tier) implements TooltipAppender {

    private static final Codec<ArmorTierDataComponent> BASE_CODEC = RecordCodecBuilder.create((instance) -> {
        return instance.group(ArmorMaterialsME.Tiers.CODEC.fieldOf("tier").forGetter(ArmorTierDataComponent::tier))
                .apply(instance, ArmorTierDataComponent::new);
    });
    public static final Codec<ArmorTierDataComponent> CODEC  = Codec.withAlternative(BASE_CODEC, ArmorMaterialsME.Tiers.CODEC, (enabled) -> {
        return new ArmorTierDataComponent(ArmorMaterialsME.Tiers.BASIC);
    });
    public static final PacketCodec<ByteBuf, ArmorTierDataComponent> PACKET_CODEC  = PacketCodec.tuple(ArmorMaterialsME.Tiers.PACKET_CODEC, ArmorTierDataComponent::tier, ArmorTierDataComponent::new);
    ;

    public ArmorTierDataComponent(ArmorMaterialsME.Tiers tier){
        this.tier = tier;
    }

    @Override
    public void appendTooltip(Item.TooltipContext context, Consumer<Text> textConsumer, TooltipType type, ComponentsAccess components) {
        textConsumer.accept(Text.translatable(
                MiddleEarth.rawTranslationKeyWithModId(LangCategory.TOOLTIP, tier.getName())
        ).withColor(tier.getColor()));
    }

    @Override
    public ArmorMaterialsME.Tiers tier() {
        return tier;
    }
}
