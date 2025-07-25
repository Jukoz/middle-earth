package net.sevenstars.middleearth.gui.utils.widgets.searchbar;

import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.MutableText;
import net.minecraft.util.Identifier;

public class SearchBarResult {
    private MutableText text;
    private SearchBarResultType type;
    private Identifier targetIdentifier;
    private ButtonWidget.PressAction action;

    public SearchBarResult(MutableText text, Identifier targetIdentifier, SearchBarResultType type, ButtonWidget.PressAction action){
        this.text = text;
        this.targetIdentifier = targetIdentifier;
        this.type = type;
        this.action = action;
    }

    public MutableText getText(){
        return text;
    }

    public Identifier getTargetIdentifier(){
        return targetIdentifier;
    }

    public SearchBarResultType getType(){
        return type;
    }

    public ButtonWidget.PressAction getAction(){
        return action;
    }
}
