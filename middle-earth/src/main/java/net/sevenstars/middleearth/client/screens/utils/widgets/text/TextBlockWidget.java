package net.sevenstars.middleearth.client.screens.utils.widgets.text;

import net.sevenstars.middleearth.client.screens.utils.widgets.ModWidget;
import net.sevenstars.middleearth.utils.resources.FileUtils;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.resource.language.LanguageManager;
import net.minecraft.text.Text;
import net.minecraft.util.Colors;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TextBlockWidget extends ModWidget {
    private int width, height;
    private int startX, startY;
    private TextAlignment textAlignment;
    private boolean isJustified = false;
    private TextRenderer textRenderer;
    private final int spaceWidth;
    private final boolean hasNoSpace;

    private List<List<Word>> wordsPerLine = new ArrayList<>();

    public TextBlockWidget(int startX, int startY, int width, int height){
        this.startX = startX;
        this.startY = startY;
        this.width = width;
        this.height = height;
        this.textAlignment = TextAlignment.LEFT;
        this.textRenderer = this.client.textRenderer;
        this.spaceWidth = this.textRenderer.getWidth(" ");

        // some languages don't use space to split words,
        // the old method would have judged the description text as a whole big word
        // with super long width, which resulted in not rendering any texts
        LanguageManager languageManager = this.client.getLanguageManager();
        String currentLanguage = languageManager.getLanguage();
        String[] noSpaceLanguages = {"ja_jp", "lzh", "th_th", "zh_cn", "zh_tw"};
        this.hasNoSpace = Arrays.asList(noSpaceLanguages).contains(currentLanguage) && FileUtils.isLanguageFileExist(currentLanguage);
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

    public List<Text> setText(List<Text> texts){
        if(texts == null)
            return texts;
        wordsPerLine.clear();
        List<Text> textOverflow = new ArrayList<>();
        int currentHeight = 0;

        int _spaceWidth = this.hasNoSpace ? 0 : this.spaceWidth;
        for(Text text : texts){ // Parse through texts (1 text is 1 paragraph)
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
                currentHeight += textRenderer.fontHeight;
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

    public void draw(DrawContext context, boolean showLimit, boolean showBorders){
        if(showBorders) {
            context.drawVerticalLine(startX, startY, startY + height, Colors.RED);
            context.drawVerticalLine(startX + width, startY, startY + height, Colors.RED);
            context.drawHorizontalLine(startX, startX + width, startY, Colors.RED);
            context.drawHorizontalLine(startX, startX + width, startY + height, Colors.RED);
        }
        draw(context, showLimit);
    }

    public void draw(DrawContext context, boolean showTextLimit){
        int currentHeight = 0;
        for(List<Word> words : wordsPerLine){
            if (showTextLimit && !words.isEmpty() && currentHeight + textRenderer.fontHeight > height){
                drawTextLimitLine(context, currentHeight);
            } else {
                drawTextLine(context, words, currentHeight, false);
            }
            currentHeight += textRenderer.fontHeight;
        }
    }

    private Text createTextFromWords(List<Word> words) {
        StringBuilder textContentBuilder = new StringBuilder();
        for(Word word : words) {
            textContentBuilder.append(word.content);
            if(word != words.getLast())
                textContentBuilder.append(" ");
        }
        return Text.literal(textContentBuilder.toString());
    }

    private void drawTextLimitLine(DrawContext context, int currentStart) {
        Text text = Text.of("...");
        context.drawText(textRenderer, text,
                startX + (width / 2) - (textRenderer.getWidth(text) / 2), startY + currentStart, Colors.RED, false);
    }

    private void drawTextLine(DrawContext context, List<Word> currentLine, int currentStart, boolean isEnd) {
        if (isJustified && !isEnd) {
            Text text = getJustifiedTextFromList(currentLine);
            Text lastWordText = Text.literal(currentLine.getLast().content);
            context.drawText(textRenderer, text,
                    startX, startY + currentStart, 0, false);
            context.drawText(textRenderer, lastWordText,
                    startX + width - textRenderer.getWidth(lastWordText), startY + currentStart, 0, false);
        } else {
            Text text = getTextFromList(currentLine);
            // draw alignment
            int x = switch (textAlignment) {
                case LEFT -> startX;
                case CENTER -> startX + (width / 2) - (textRenderer.getWidth(text) / 2);
                case RIGHT -> startX + (width - textRenderer.getWidth(text));
            };

            context.drawText(textRenderer, text,
                    x, startY + currentStart, 0, false);
        }
    }

    private Text getJustifiedTextFromList(List<Word> currentLine) {
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
        return Text.literal(stringBuilder.toString());
    }

    private Text getTextFromList(List<Word> currentLine) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Word word : currentLine) {
            stringBuilder.append(word.content);
            if (word != currentLine.getLast())
                if (!hasNoSpace)
                    stringBuilder.append(" ");
        }
        return Text.literal(stringBuilder.toString());
    }

    private List<Word> getWordListFromText(Text textToSplit, boolean hasNoSpace){
        List<Word> words = new ArrayList<>();
        if (hasNoSpace) {
            for (char character : textToSplit.getString().toCharArray()) {
                String word = String.valueOf(character);
                words.add(new Word(word, textRenderer.getWidth(word)));
            }
        } else {
            for (String splittedWord : textToSplit.getString().split(" ")){
                words.add(new Word(splittedWord, textRenderer.getWidth(splittedWord)));
            }
        }
        return words;
    }
}
