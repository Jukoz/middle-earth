package net.sevenstars.middleearth.datageneration.content.models;

import net.minecraft.block.Block;
import net.minecraft.item.Item;

import java.util.ArrayList;
import java.util.List;

public class SimpleArtefactModels {

    public record Artefact(Item artefact, Boolean dualModel) {}


    public static List<Artefact> artefacts = new ArrayList<>(){

    };

}
