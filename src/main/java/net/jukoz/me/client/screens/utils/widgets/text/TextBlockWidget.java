package net.jukoz.me.client.screens.utils.widgets.text;

import net.jukoz.me.client.screens.utils.widgets.ModWidget;
import net.jukoz.me.utils.LoggerUtil;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;
import net.minecraft.util.Colors;

import java.util.ArrayList;
import java.util.List;

public class TextBlockWidget extends ModWidget {
    private int width, height;
    private int startX, startY;
    private TextAlignment textAlignment;
    private boolean isJustified = false;
    private TextRenderer textRenderer;
    private final int spaceWidth;

    public TextBlockWidget(int startX, int startY, int width, int height){
        this.startX = startX;
        this.startY = startY;
        this.width = width;
        this.height = height;
        this.textAlignment = TextAlignment.LEFT;
        this.textRenderer = this.client.textRenderer;
        this.spaceWidth = this.textRenderer.getWidth(" ");
    }

    public TextBlockWidget setAlignment(TextAlignment alignment){
        this.textAlignment = alignment;
        return this;
    }
    public TextBlockWidget setJustified(){
        this.isJustified = true;
        return this;
    }
    public TextBlockWidget setUnjustified(){
        this.isJustified = false;
        return this;
    }
    public List<Text> draw(DrawContext context, List<Text> texts, boolean showLimit, boolean showBorders){
            if(showBorders) {
                context.drawVerticalLine(startX, startY, startY + height, Colors.RED);
                context.drawVerticalLine(startX + width, startY, startY + height, Colors.RED);
                context.drawHorizontalLine(startX, startX + width, startY, Colors.RED);
                context.drawHorizontalLine(startX, startX + width, startY + height, Colors.RED);
            }
            return draw(context, texts, showLimit);
    }

    public List<Text> draw(DrawContext context, List<Text> texts, boolean showTextLimit){
        if(texts != null){
            int currentHeight = 0;
            for(Text text : texts){
                List<Word> words = getWordListFromText(text);
                while(!words.isEmpty() && currentHeight < height){
                    List<Word> currentLine = new ArrayList<>();
                    int currentWidth = 0;
                    boolean forcedEnd = false;
                    while(currentWidth < width){
                        Word chosenWord = words.getFirst();
                        if(currentWidth + chosenWord.width + spaceWidth > width){
                            currentWidth = width;
                        } else {
                            currentLine.add(chosenWord);
                            currentWidth += chosenWord.width + spaceWidth;
                            words.remove(chosenWord);
                            if(words.isEmpty()){
                                currentWidth = width;
                                forcedEnd = true;
                            } else if(currentWidth > width){
                                currentWidth = width;
                            }
                        }
                    }
                    if(showTextLimit && !words.isEmpty() && currentHeight + textRenderer.fontHeight > height){
                        drawTextLimitLine(context, currentHeight);
                    } else {
                        drawTextLine(context, currentLine, currentHeight, forcedEnd);
                    }
                    currentHeight += textRenderer.fontHeight;
                }
            }
        }
        return texts; // Todo : Make it so it's the leftover texts
    }

    private void drawTextLimitLine(DrawContext context, int currentStart) {
        Text text = Text.of("...");
        context.drawText(textRenderer, text,
                startX + (width / 2) - (textRenderer.getWidth(text) / 2), startY + currentStart, Colors.RED, false);
    }

    private void drawTextLine(DrawContext context, List<Word> currentLine, int currentStart, boolean isEnd) {
        Text text = (isJustified && !isEnd) ? getJustifiedTextFromList(currentLine) : getTextFromList(currentLine);
        context.drawText(textRenderer, text,
                startX, startY + currentStart, 0, false);
    }

    private Text getJustifiedTextFromList(List<Word> currentLine) {
        int currentWidth = 0;
        for(Word word : currentLine){
            currentWidth += word.width;
            if(word != currentLine.getLast())
                currentWidth += spaceWidth;
        }
        StringBuilder stringBuilder = new StringBuilder();
        int spaceToFill = width - currentWidth;
        int spaceDifference = (spaceToFill - (spaceToFill % spaceWidth)) / spaceWidth;
        List<Integer> spaces = new ArrayList<>();
        while(spaceDifference > 0){
            for(int i = 0; i < currentLine.size() - 1 && spaceDifference > 0; i ++){
                if(i >= spaces.size()){
                    spaces.add(1);
                } else {
                    int alreadyPresentAmount = spaces.get(i);
                    spaces.set(i, alreadyPresentAmount + 1);
                }
                spaceDifference --;
            }
        }
        for(int i = 0; i < currentLine.size(); i++){
            stringBuilder.append(currentLine.get(i).content);
            if(i < currentLine.size() - 1){
                int extraSpaceAmount = 0;
                if(i < spaces.size())
                    extraSpaceAmount = spaces.get(i);
                stringBuilder.append(" ".repeat(1 + extraSpaceAmount));
            }
        }
        return Text.literal(stringBuilder.toString());
    }

    private Text getTextFromList(List<Word> currentLine) {
        StringBuilder stringBuilder = new StringBuilder();
        for(Word word : currentLine){
            stringBuilder.append(word.content).append(" ");
        }
        return Text.literal(stringBuilder.toString());
    }


    private List<Word> getWordListFromText(Text textToSplit){
        List<Word> words = new ArrayList<>();
        for (String splittedWord : textToSplit.getString().split(" ")){
            words.add(new Word(splittedWord, textRenderer.getWidth(splittedWord)));
        }
        return words;
    }
}
