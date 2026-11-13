package net.sevenstars.middleearth.client.renderer;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKey;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.item.DataComponentTypesME;
import net.sevenstars.middleearth.item.dataComponents.RingForgingDataComponent;
import net.sevenstars.middleearth.item.items.rings.RingItem;
import net.sevenstars.middleearth.recipe.CraftQuality;
import net.sevenstars.middleearth.registries.content.gems.GemTypeRegistry;
import net.sevenstars.middleearth.registries.content.rings.RingMetalRegistry;
import net.sevenstars.middleearth.resources.datas.gems.GemType;
import net.sevenstars.middleearth.resources.datas.rings.RingPolarity;

import java.util.function.Consumer;

/**
 * The Shift details on a ring's tooltip: its quality, purity, polarity, slots, gem and Fëa. Without Shift only a
 * hint is shown.
 */
@Environment(EnvType.CLIENT)
public final class RingTooltip {
    private static final String TOOLTIP = "tooltip." + MiddleEarth.MOD_ID + ".ring.";

    private RingTooltip() {
    }

    public static void register() {
        RingItem.details = RingTooltip::append;
    }

    private static void append(ItemStack stack, Item.TooltipContext context, Consumer<Text> tooltip) {
        RingForgingDataComponent forging = stack.get(DataComponentTypesME.RING_FORGING);
        if (forging == null) {
            return;
        }
        if (!Screen.hasShiftDown()) {
            tooltip.accept(Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".gem_info.hold_shift").formatted(Formatting.DARK_GRAY));
            return;
        }
        CraftQuality quality = CraftQuality.of(forging.quality(), RingItem.isFinished(stack, context.getRegistryLookup()));
        tooltip.accept(line("quality", Text.translatable(quality.translationKey()).styled(style -> style.withColor(quality.color()))));
        tooltip.accept(line("purity", Text.literal(forging.purity() + "%").formatted(Formatting.WHITE)));
        RingPolarity band = forging.band();
        tooltip.accept(line("polarity", Text.translatable(band.translationKey()).styled(style -> style.withColor(band.color())),
                Text.literal((forging.polarity() > 0 ? "+" : "") + forging.polarity()).formatted(Formatting.DARK_GRAY)));
        RingMetalRegistry.get(context.getRegistryLookup(), stack.get(DataComponentTypesME.RING_METAL)).ifPresent(metal ->
                tooltip.accept(line("slots", Text.literal(forging.slotsUsed() + "/" + metal.slots()).formatted(Formatting.WHITE))));
        forging.gem().ifPresent(gem -> {
            RegistryKey<GemType> gemType = stack.get(DataComponentTypesME.GEM_TYPE);
            Text gemName = gemType != null ? Text.translatable(GemTypeRegistry.translationKey(gemType))
                    : Text.translatable("item." + MiddleEarth.MOD_ID + ".cut_gem.no_type");
            tooltip.accept(line("gem", Text.translatable("item." + MiddleEarth.MOD_ID + ".cut_gem.name",
                    Text.translatable(gem.cut().translationKey()), gemName).formatted(Formatting.WHITE)));
        });
        if (forging.fea() > 0) {
            tooltip.accept(line("fea", Text.literal(String.valueOf(forging.fea())).formatted(Formatting.WHITE)));
        }
    }

    private static MutableText line(String key, Object... args) {
        return Text.translatable(TOOLTIP + key, args).formatted(Formatting.GRAY);
    }
}
