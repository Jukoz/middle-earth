package net.sevenstars.middleearth.gui.utils.widgets.text;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.resources.language.LanguageManager;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.CommonColors;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.gui.utils.widgets.ModWidget;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TextBlockWidget extends ModWidget {
    private int width, height;
    private int startX, startY;
    private TextAlignment textAlignment;
    private boolean isJustified = false;
    private Font textRenderer;
    private final int spaceWidth;
    private final boolean hasNoSpace;

    private List<List<Word>> wordsPerLine = new ArrayList<>();
    private List<Component> rawTexts = new ArrayList<>();

    public TextBlockWidget(int startX, int startY, int width, int height){
        this.startX = startX;
        this.startY = startY;
        this.width = width;
        this.height = height;
        this.textAlignment = TextAlignment.LEFT;
        this.textRenderer = this.client.font;
        this.spaceWidth = this.textRenderer.width(" ");

        // some languages don't use space to split words,
        // the old method would have judged the description text as a whole big word
        // with super long width, which resulted in not rendering any texts
        LanguageManager languageManager = this.client.getLanguageManager();
        String currentLanguage = languageManager.getSelected();
        String[] noSpaceLanguages = {"ja_jp", "lzh", "th_th", "zh_cn", "zh_tw"};
        ResourceLocation languageFile = MiddleEarth.of("lang/%s.json".formatted(currentLanguage));
        this.hasNoSpace = Arrays.asList(noSpaceLanguages).contains(currentLanguage)
                && this.client.getResourceManager().getResource(languageFile).isPresent();
    }

    public TextBlockWidget setStartY(int newStartY){
        this.startY = newStartY;
        return this;
    }
    public TextBlockWidget setStartX(int newStartX){
        this.startX = newStartX;
        return this;
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

    public List<Component> setText(List<Component> texts){
        if(texts == null)
            return texts;
        rawTexts = texts;
        wordsPerLine.clear();
        List<Component> textOverflow = new ArrayList<>();
        int currentHeight = 0;

        int _spaceWidth = this.hasNoSpace ? 0 : this.spaceWidth;
        for(Component text : texts){ // Parse through texts (1 text is 1 paragraph)
            boolean isManipulated = false;  // Simple variable to check if the input was manipulated
            List<Word> textWords = getWordListFromText(text, this.hasNoSpace); // The text split in words
            while(!textWords.isEmpty() && currentHeight < height){ // Parse the input until it reaches the end of it or the height limit
                isManipulated = true;
                List<Word> currentLineWords = new ArrayList<>();
                int currentWidth = 0;
                while (currentWidth < width){
                    Word chosenWord = textWords.getFirst();
                    if(currentWidth + chosenWord.width + _spaceWidth > width){
                        break;
                    }

                    currentLineWords.add(chosenWord);
                    currentWidth += chosenWord.width + _spaceWidth;
                    textWords.remove(chosenWord);
                    if(textWords.isEmpty()){
                        break;
                    } else if(currentWidth > width){
                        break;
                    }
                }
                wordsPerLine.add(currentLineWords);
                currentHeight += textRenderer.lineHeight;
            }
            if (!textWords.isEmpty()) {
                if (isManipulated) // If the text has content and was manipulated, create new text from leftovers
                    textOverflow.add(createTextFromWords(textWords));
                else // If not, simply add the whole text
                    textOverflow.add(text);
            }
        }
        return textOverflow;
    }

    public void draw(GuiGraphics context, boolean showLimit, boolean showBorders){
        if(showBorders) {
            context.vLine(startX, startY, startY + height, CommonColors.RED);
            context.vLine(startX + width, startY, startY + height, CommonColors.RED);
            context.hLine(startX, startX + width, startY, CommonColors.RED);
            context.hLine(startX, startX + width, startY + height, CommonColors.RED);
        }
        draw(context, showLimit);
    }

    public void draw(GuiGraphics context, boolean showTextLimit){
        int currentHeight = 0;
        for(List<Word> words : wordsPerLine){
            if (showTextLimit && !words.isEmpty() && currentHeight + textRenderer.lineHeight > height){
                drawTextLimitLine(context, currentHeight);
            } else {
                drawTextLine(context, words, currentHeight, false);
            }
            currentHeight += textRenderer.lineHeight;
        }
    }

    private Component createTextFromWords(List<Word> words) {
        StringBuilder textContentBuilder = new StringBuilder();
        for(Word word : words) {
            textContentBuilder.append(word.content);
            if(word != words.getLast())
                textContentBuilder.append(" ");
        }
        return Component.literal(textContentBuilder.toString());
    }

    private void drawTextLimitLine(GuiGraphics context, int currentStart) {
        Component text = Component.nullToEmpty("...");
        context.drawString(textRenderer, text,
                startX + (width / 2) - (textRenderer.width(text) / 2), startY + currentStart, CommonColors.RED, false);
    }

    private void drawTextLine(GuiGraphics context, List<Word> currentLine, int currentStart, boolean isEnd) {
        if (isJustified && !isEnd) {
            Component text = getJustifiedTextFromList(currentLine);
            Component lastWordText = Component.literal(currentLine.getLast().content);
            context.drawString(textRenderer, text,
                    startX, startY + currentStart, CommonColors.BLACK, false);
            context.drawString(textRenderer, lastWordText,
                    startX + width - textRenderer.width(lastWordText), startY + currentStart, CommonColors.BLACK, false);
        } else {
            Component text = getTextFromList(currentLine);
            // draw alignment
            int x = switch (textAlignment) {
                case LEFT -> startX;
                case CENTER -> startX + (width / 2) - (textRenderer.width(text) / 2);
                case RIGHT -> startX + (width - textRenderer.width(text));
            };

            context.drawString(textRenderer, text,
                    x, startY + currentStart, CommonColors.BLACK, false);
        }
    }

    private Component getJustifiedTextFromList(List<Word> currentLine) {
        int currentWidth = 0;
        int _spaceWidth = this.hasNoSpace ? 0 : this.spaceWidth;
        for(Word word : currentLine){
            currentWidth += word.width;
            if(word != currentLine.getLast())
                currentWidth += _spaceWidth;
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

        for(int i = 0; i < currentLine.size() - 1; i++){
            stringBuilder.append(currentLine.get(i).content);
            if (i < currentLine.size() - 1) {
                int extraSpaceAmount = 0;
                if (i < spaces.size())
                    extraSpaceAmount = spaces.get(i);
                if (!hasNoSpace)
                    stringBuilder.append(" ".repeat(1 + extraSpaceAmount));
            }
        }
        return Component.literal(stringBuilder.toString());
    }

    private Component getTextFromList(List<Word> currentLine) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Word word : currentLine) {
            stringBuilder.append(word.content);
            if (word != currentLine.getLast())
                if (!hasNoSpace)
                    stringBuilder.append(" ");
        }
        return Component.literal(stringBuilder.toString());
    }

    private List<Word> getWordListFromText(Component textToSplit, boolean hasNoSpace){
        List<Word> words = new ArrayList<>();
        if (hasNoSpace) {
            for (char character : textToSplit.getString().toCharArray()) {
                String word = String.valueOf(character);
                words.add(new Word(word, textRenderer.width(word)));
            }
        } else {
            for (String splittedWord : textToSplit.getString().split(" ")){
                words.add(new Word(splittedWord, textRenderer.width(splittedWord)));
            }
        }
        return words;
    }

    public List<Component> getValue() {
        return rawTexts;
    }
}
