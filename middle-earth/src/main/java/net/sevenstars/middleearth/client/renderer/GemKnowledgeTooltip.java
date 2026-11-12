package net.sevenstars.middleearth.client.renderer;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKey;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.item.DataComponentTypesME;
import net.sevenstars.middleearth.item.items.gems.RawGemItem;
import net.sevenstars.middleearth.recipe.lapidary.GemKnowledge;
import net.sevenstars.middleearth.recipe.lapidary.StudyGrade;
import net.sevenstars.middleearth.registries.content.gems.GemTypeRegistry;
import net.sevenstars.middleearth.resources.datas.gems.GemType;

import java.util.function.Consumer;

/**
 * The Shift details on raw and cut gem tooltips, from what the player knows of the gem type (the best study
 * grade they ever reached): the aspect once they reached Thorough, the stack's own extra lines, then host rock,
 * depth, region and lore as far as their study goes. Without Shift only a hint is shown.
 */
@Environment(EnvType.CLIENT)
public final class GemKnowledgeTooltip {
    private static final String[] INFO = {"host", "depth", "region", "lore"};
    /** How wide a gem info line may run before it wraps, in pixels. */
    private static final int MAX_WIDTH = 180;

    private GemKnowledgeTooltip() {
    }

    public static void register() {
        RawGemItem.GemTooltips.details = GemKnowledgeTooltip::append;
    }

    private static void append(ItemStack stack, Consumer<Text> tooltip, Text[] extra) {
        RegistryKey<GemType> gem = stack.get(DataComponentTypesME.GEM_TYPE);
        MinecraftClient client = MinecraftClient.getInstance();
        if (gem == null || client.player == null) {
            return;
        }
        if (!Screen.hasShiftDown()) {
            tooltip.accept(Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".gem_info.hold_shift").formatted(Formatting.DARK_GRAY));
            return;
        }
        StudyGrade best = GemKnowledge.best(client.player, gem);
        // The aspect is only revealed once the player has studied this gem type to Thorough.
        if (best != null && best.atLeast(StudyGrade.THOROUGH) && client.world != null) {
            GemTypeRegistry.get(client.world.getRegistryManager(), gem).ifPresent(gemType -> tooltip.accept(
                    Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".gem_aspect",
                                    Text.translatable(gemType.aspect().translationKey()))
                            .styled(style -> style.withColor(gemType.color()))));
        }
        // The exact quality value only shows once the gem type is fully studied.
        Integer quality = stack.get(DataComponentTypesME.GEM_QUALITY);
        if (quality != null && best == StudyGrade.MASTERFUL) {
            tooltip.accept(Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".cut_gem.quality_value", quality)
                .formatted(Formatting.GRAY));
        }
        for (Text line : extra) {
            tooltip.accept(line);
        }
        if (best == null) {
            tooltip.accept(Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".gem_info.unknown",
                    Text.translatable(GemTypeRegistry.translationKey(gem))).formatted(Formatting.DARK_GRAY));
            return;
        }
        String prefix = "gem_info." + gem.getValue().getNamespace() + "." + gem.getValue().getPath() + ".";
        for (int i = 0; i <= best.ordinal(); i++) {
            String key = prefix + INFO[i];
            // Lore is optional: gems without a lore line just show nothing for Masterful.
            if (I18n.hasTranslation(key)) {
                Formatting[] style = i == 3 ? new Formatting[] {Formatting.DARK_PURPLE, Formatting.ITALIC}
                        : new Formatting[] {Formatting.GRAY};
                wrap(client, I18n.translate(key), tooltip, style);
            }
        }
    }

    /** Long lines (lore, depth) go onto as many tooltip lines as they need, so the tooltip stays narrow. */
    private static void wrap(MinecraftClient client, String line, Consumer<Text> tooltip, Formatting... style) {
        for (var part : client.textRenderer.getTextHandler().wrapLines(line, MAX_WIDTH, net.minecraft.text.Style.EMPTY)) {
            tooltip.accept(Text.literal(part.getString()).formatted(style));
        }
    }
}
