package net.jukoz.me.datageneration.content.models;

import net.jukoz.me.item.ModWeaponItems;
import net.minecraft.item.Item;

import java.util.ArrayList;
import java.util.List;

public class SimpleBigItemModel {
    public static List<Item> items = new ArrayList<>() {
        {

        }
    };
    /**
        Generic items/not weapoons. Pipes- etc. For in GUI 2d sprite- not in GUI 3d model as json.
    */
    public static List<Item> genericItems = new ArrayList<>(){

    };

    public static List<Item> artefacts = new ArrayList<>() {
        {
        }
    };

    public static List<Item> artefactsBroken = new ArrayList<>() {
        {
        }
    };

    public static List<Item> artefactsGlowing = new ArrayList<>() {
        {
        }
    };

}
