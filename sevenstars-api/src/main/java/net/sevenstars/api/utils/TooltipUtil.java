package net.sevenstars.api.utils;

import net.minecraft.text.OrderedText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class TooltipUtil {
    private static final int TOOLTIP_LINE_LIMIT_DEFAULT = 10;

    private static final int TOOLTIP_COLOR_WHITE = 0xffffff;
    private static final int TOOLTIP_COLOR_BLUE = 0x14a4ec;
    private static final int TOOLTIP_COLOR_OFF_WHITE = 0x919090;

    public Collection<? extends OrderedText> getLimitedTooltipList(int currentIndex, List<Identifier> items, String translationKeyBase) {
        return getLimitedTooltipList(currentIndex, items, translationKeyBase, TOOLTIP_LINE_LIMIT_DEFAULT);
    }

    public Collection<? extends OrderedText> getLimitedTooltipList(int currentIndex, List<Identifier> items, String translationKeyBase, int lineLimit) {
        List<OrderedText> textList = new ArrayList<>();

        int itemSize = items.size();

        int startIndex = 0;
        int endIndex = itemSize - 1;

        if(itemSize > lineLimit)
        {
            startIndex = Math.max(0, Math.min(currentIndex - lineLimit / 2, endIndex - lineLimit));
            endIndex = Math.min(itemSize - 1, startIndex + lineLimit);
        }

        if(startIndex > 0)
            textList.add(Text.literal(" •••").withColor(TOOLTIP_COLOR_OFF_WHITE).asOrderedText());

        for(int i = startIndex; i <= endIndex; i++){
            Identifier identifier = items.get(i);
            String translationKey = translationKeyBase + "." + identifier.getPath();
            if (identifier.equals(items.get(currentIndex))){
                textList.add(Text.literal(">").withColor(TOOLTIP_COLOR_BLUE).append(Text.translatable(translationKey).withColor(TOOLTIP_COLOR_WHITE)).asOrderedText());
            } else {
                textList.add(Text.literal(" ").append(Text.translatable(translationKey)).withColor(TOOLTIP_COLOR_OFF_WHITE).asOrderedText());
            }
        }

        if(endIndex > 0 && endIndex != itemSize - 1)
            textList.add(Text.literal(" •••").withColor(TOOLTIP_COLOR_OFF_WHITE).asOrderedText());

        return textList;
    }
}
