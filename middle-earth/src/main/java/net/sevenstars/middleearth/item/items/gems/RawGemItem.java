package net.sevenstars.middleearth.item.items.gems;

import net.minecraft.component.type.TooltipDisplayComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.item.DataComponentTypesME;
import net.sevenstars.middleearth.recipe.lapidary.StudyGrade;

import java.util.function.Consumer;

/**
 * A raw, uncut gem as it drops from the gem ore. One item per gem type (it carries its
 * {@link DataComponentTypesME#GEM_TYPE} by default), so raw gems of a type stack.
 * Counts as tier 1 as an Inscription Table catalyst.
 */
public class RawGemItem extends Item {
    public RawGemItem(Settings settings) {
        super(settings);
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, TooltipDisplayComponent displayComponent,
                              Consumer<Text> tooltip, TooltipType type) {
        super.appendTooltip(stack, context, displayComponent, tooltip, type);
        StudyGrade grade = stack.get(DataComponentTypesME.STUDY_GRADE);
        tooltip.accept(grade != null
                ? Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".raw_gem.studied", Text.translatable(grade.translationKey())
                        .formatted(grade.formatting())).formatted(Formatting.GRAY)
                : Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".raw_gem.unstudied").formatted(Formatting.DARK_GRAY));
        GemTooltips.appendDetails(stack, tooltip, Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".raw_gem.tier")
                .formatted(Formatting.DARK_GRAY));
    }

    /**
     * Shared gem tooltip lines. What the player knows (the aspect, the study notes) lives on the client player,
     * so the client fills in {@link #details}; on a dedicated server it stays a no-op.
     */
    public static final class GemTooltips {
        /** Writes the details shown while Shift is held, or the "hold Shift" hint; set by the client. */
        public static Details details = (stack, tooltip, extra) -> {
        };

        private GemTooltips() {
        }

        public static void appendDetails(ItemStack stack, Consumer<Text> tooltip, Text... extra) {
            details.append(stack, tooltip, extra);
        }

        @FunctionalInterface
        public interface Details {
            void append(ItemStack stack, Consumer<Text> tooltip, Text[] extra);
        }
    }
}
