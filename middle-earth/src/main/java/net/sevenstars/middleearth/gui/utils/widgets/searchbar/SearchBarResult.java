package net.sevenstars.middleearth.gui.utils.widgets.searchbar;

import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;

public class SearchBarResult {
    private MutableComponent text;
    private SearchBarResultType type;
    private ResourceLocation targetIdentifier;
    private Button.OnPress action;

    public SearchBarResult(MutableComponent text, ResourceLocation targetIdentifier, SearchBarResultType type, Button.OnPress action){
        this.text = text;
        this.targetIdentifier = targetIdentifier;
        this.type = type;
        this.action = action;
    }

    public MutableComponent getText(){
        return text;
    }

    public ResourceLocation getTargetIdentifier(){
        return targetIdentifier;
    }

    public SearchBarResultType getType(){
        return type;
    }

    public Button.OnPress getAction(){
        return action;
    }
}
