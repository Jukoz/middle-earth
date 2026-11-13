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
import net.sevenstars.middleearth.registries.content.rings.RingMetalRegistry;
import net.sevenstars.middleearth.resources.datas.rings.RingMetal;

import java.util.Optional;
import java.util.function.Consumer;

/**
 * A ring of power in the making: one item for every metal, built up over the whole of its forging. Its metal is
 * the {@link DataComponentTypesME#RING_METAL} component, what it has been through the
 * {@link DataComponentTypesME#RING_FORGING} component, and a set gem's type the {@link DataComponentTypesME#GEM_TYPE}
 * component. It is finished once every one of its metal's slots is filled.
 * <p>
 * Hot rings: a ring can be heated in the forge (and quenched in a water cauldron like other hot metal) only while
 * nothing has been inscribed on it and no gem set in it; it can be inscribed or adorned only once it is shaped and
 * quenched.
 */
public class RingItem extends Item {
    private static final String KEY = "item." + MiddleEarth.MOD_ID + ".ring";

    /** The ring's statistics under its tooltip; set on the client (shown while Shift is held). */
    public static Details details = (stack, context, tooltip) -> {
    };

    public RingItem(Settings settings) {
        super(settings.maxCount(1));
    }

    /** "<Metal> Ring", e.g. "Gold Ring"; "Ring" without a metal. */
    @Override
    public Text getName(ItemStack stack) {
        RegistryKey<RingMetal> metal = stack.get(DataComponentTypesME.RING_METAL);
        return metal == null ? Text.translatable(KEY)
                : Text.translatable(KEY + ".metal", Text.translatable(RingMetalRegistry.translationKey(metal)));
    }

    /** Whether every slot of the ring's metal is filled, so it can be worn. */
    public static boolean isFinished(ItemStack stack, RegistryWrapper.WrapperLookup lookup) {
        RingForgingDataComponent forging = stack.get(DataComponentTypesME.RING_FORGING);
        Optional<RingMetal> metal = RingMetalRegistry.get(lookup, stack.get(DataComponentTypesME.RING_METAL));
        return forging != null && metal.isPresent() && forging.slotsUsed() >= metal.get().slots();
    }

    public static boolean isHot(ItemStack stack) {
        TemperatureDataComponent temperature = stack.get(DataComponentTypesME.TEMPERATURE_DATA);
        return temperature != null && temperature.temperature() > 0;
    }

    /** Whether the forge may heat this ring: a ring with nothing inscribed on it and no gem set in it. */
    public static boolean canHeat(ItemStack stack) {
        RingForgingDataComponent forging = stack.get(DataComponentTypesME.RING_FORGING);
        return stack.getItem() instanceof RingItem && forging != null
                && forging.inscription().isEmpty() && forging.gem().isEmpty();
    }

    /** Whether the ring may be inscribed or have a gem set: it has been shaped in a theme and quenched. */
    public static boolean canInscribe(ItemStack stack) {
        RingForgingDataComponent forging = stack.get(DataComponentTypesME.RING_FORGING);
        return stack.getItem() instanceof RingItem && forging != null && forging.shaping().isPresent() && !isHot(stack);
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
