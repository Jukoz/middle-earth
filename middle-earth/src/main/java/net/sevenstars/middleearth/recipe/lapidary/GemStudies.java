package net.sevenstars.middleearth.recipe.lapidary;

import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKey;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Language;
import net.sevenstars.middleearth.item.DataComponentTypesME;
import net.sevenstars.middleearth.item.ResourceItemsME;
import net.sevenstars.middleearth.resources.datas.gems.GemType;

import java.util.ArrayList;
import java.util.List;

/** What each study grade teaches and the Player Book component that keeps it. */
public final class GemStudies {
    private static final String[] INFO = {"host", "depth", "region", "lore"};

    private GemStudies() {
    }

    public static boolean isPlayerBook(ItemStack stack) {
        return stack.isOf(ResourceItemsME.PLAYER_BOOK);
    }

    public static ItemStack notes(RegistryKey<GemType> gem, StudyGrade grade) {
        ItemStack stack = new ItemStack(ResourceItemsME.GEM_NOTES.get(grade));
        stack.set(DataComponentTypesME.GEM_TYPE, gem);
        return stack;
    }

    public static List<Text> infoLines(RegistryKey<GemType> gem, StudyGrade grade) {
        List<Text> lines = new ArrayList<>();
        String prefix = "gem_info." + gem.getValue().getNamespace() + "." + gem.getValue().getPath() + ".";
        for (int i = 0; i <= grade.ordinal(); i++) {
            String key = prefix + INFO[i];
            if (Language.getInstance().hasTranslation(key)) {
                lines.add(i == 3 ? Text.translatable(key).formatted(Formatting.DARK_PURPLE, Formatting.ITALIC)
                        : Text.translatable(key).formatted(Formatting.GRAY));
            }
        }
        return lines;
    }
}
