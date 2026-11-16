package net.sevenstars.middleearth.item.items.rings;

import net.minecraft.component.type.TooltipDisplayComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.item.DataComponentTypesME;
import net.sevenstars.middleearth.item.dataComponents.RingForgingDataComponent;
import net.sevenstars.middleearth.item.dataComponents.TemperatureDataComponent;
import net.minecraft.registry.entry.RegistryEntry;
import net.sevenstars.middleearth.registries.content.rings.RingMetalRegistry;
import net.sevenstars.middleearth.registries.content.rings.RingThemeRegistry;
import net.sevenstars.middleearth.resources.datas.rings.RingMetal;
import net.sevenstars.middleearth.resources.datas.rings.RingWord;

import java.util.Optional;
import java.util.function.Consumer;

/** The single ring item, built up through forging; heatable only while bare, inscribable once shaped and quenched. */
public class RingItem extends Item {
    private static final String KEY = "item." + MiddleEarth.MOD_ID + ".ring";

    public static Details details = (stack, context, tooltip) -> {
    };

    public RingItem(Settings settings) {
        super(settings.maxCount(1));
    }

    @Override
    public Text getName(ItemStack stack) {
        RegistryKey<RingMetal> metal = stack.get(DataComponentTypesME.RING_METAL);
        return metal == null ? Text.translatable(KEY)
                : Text.translatable(KEY + ".metal", Text.translatable(RingMetalRegistry.translationKey(metal)));
    }

    public static boolean isFinished(ItemStack stack, RegistryWrapper.WrapperLookup lookup) {
        RingForgingDataComponent forging = stack.get(DataComponentTypesME.RING_FORGING);
        Optional<RingMetal> metal = RingMetalRegistry.get(lookup, stack.get(DataComponentTypesME.RING_METAL));
        return forging != null && metal.isPresent() && forging.slotsUsed() >= metal.get().slots();
    }

    public static int freeSlots(ItemStack stack, RegistryWrapper.WrapperLookup lookup) {
        RingForgingDataComponent forging = stack.get(DataComponentTypesME.RING_FORGING);
        return forging == null ? 0 : RingMetalRegistry.get(lookup, stack.get(DataComponentTypesME.RING_METAL))
                .map(metal -> metal.slots() - forging.slotsUsed()).orElse(0);
    }

    public static boolean isHot(ItemStack stack) {
        TemperatureDataComponent temperature = stack.get(DataComponentTypesME.TEMPERATURE_DATA);
        return temperature != null && temperature.temperature() > 0;
    }

    public static boolean canHeat(ItemStack stack) {
        RingForgingDataComponent forging = stack.get(DataComponentTypesME.RING_FORGING);
        return stack.getItem() instanceof RingItem && forging != null
                && forging.inscription().isEmpty() && forging.words().isEmpty() && forging.gem().isEmpty();
    }

    public static boolean canInscribe(ItemStack stack) {
        RingForgingDataComponent forging = stack.get(DataComponentTypesME.RING_FORGING);
        return stack.getItem() instanceof RingItem && forging != null && forging.theme().isPresent()
                && forging.shaping().isPresent() && !isHot(stack);
    }

    /** The ring's theme allows the word, it is not on the ring yet, and the slots invested meet its minimum and fit. */
    public static boolean canInscribe(ItemStack stack, RegistryEntry<RingWord> word, int slots, RegistryWrapper.WrapperLookup lookup) {
        if (!canInscribe(stack)) {
            return false;
        }
        RingForgingDataComponent forging = stack.get(DataComponentTypesME.RING_FORGING);
        boolean allowed = RingThemeRegistry.get(lookup, forging.theme().get())
                .map(theme -> word.isIn(theme.value().words())).orElse(false);
        boolean repeated = word.getKey().map(forging::hasWord).orElse(false);
        return allowed && !repeated && slots >= word.value().minSlots() && slots <= freeSlots(stack, lookup);
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, TooltipDisplayComponent displayComponent,
                              Consumer<Text> tooltip, TooltipType type) {
        super.appendTooltip(stack, context, displayComponent, tooltip, type);
        RingForgingDataComponent forging = stack.get(DataComponentTypesME.RING_FORGING);
        if (forging == null) {
            return;
        }
        details.append(stack, context, tooltip);
        if (!isFinished(stack, context.getRegistryLookup())) {
            tooltip.accept(Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".ring.unfinished")
                    .formatted(Formatting.DARK_GRAY, Formatting.ITALIC));
        }
    }

    @FunctionalInterface
    public interface Details {
        void append(ItemStack stack, TooltipContext context, Consumer<Text> tooltip);
    }
}
