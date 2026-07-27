package net.sevenstars.middleearth.datageneration.content.models;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.world.item.Item;

public class SimpleArtefactModels {

    public record Artefact(Item artefact, Boolean dualModel) {}


    public static List<Artefact> artefacts = new ArrayList<>(){

    };

}
