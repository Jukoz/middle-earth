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

/** An uncut gem, one item per gem type; a tier 1 inscription catalyst. */
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

    public static final class GemTooltips {
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
